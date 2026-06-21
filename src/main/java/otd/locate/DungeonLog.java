package otd.locate;

import otd.Main;
import otd.integration.MapMarkerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DungeonLog {

    private static final CopyOnWriteArrayList<DungeonRecord> list = new CopyOnWriteArrayList<>();

    private static final Pattern PATTERN =
            Pattern.compile("\\[(?<label>.+?) @ (?<world>.+?)\\] x=(?<x>-?\\d+), z=(?<z>-?\\d+)");

    private static final Pattern TIMESTAMP =
            Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})");

    private DungeonLog() {}

    public static Optional<DungeonRecord> parse(String line) {
        if (line == null) return Optional.empty();
        Matcher m = PATTERN.matcher(line);
        if (!m.find()) return Optional.empty();

        long time = 0L;
        Matcher tm = TIMESTAMP.matcher(line);
        if (tm.find()) {
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tm.group(1));
                time = d.getTime();
            } catch (Exception ex) {
                time = 0L;
            }
        }

        String label = m.group("label");
        String world = m.group("world");
        int x = Integer.parseInt(m.group("x"));
        int z = Integer.parseInt(m.group("z"));
        return Optional.of(new DungeonRecord(label, world, x, z, time));
    }

    public static void load() {
        File file = new File(Main.instance.getDataFolder(), "log.txt");
        list.clear();
        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                for (String line : lines) {
                    parse(line).ifPresent(list::add);
                }
            } catch (IOException ex) {
            }
        }
        MapMarkerService.reloadAll(all());
    }

    public static void record(DungeonRecord r) {
        list.add(r);
        MapMarkerService.addOne(r);
    }

    public static DungeonRecord nearest(String world, int x, int z, String typeFilter) {
        DungeonRecord best = null;
        long bestDist = Long.MAX_VALUE;
        String normFilter = (typeFilter == null) ? "" : normalize(typeFilter);
        for (DungeonRecord r : list) {
            if (!r.world.equals(world)) continue;
            if (!normFilter.isEmpty() && !normalize(r.type).contains(normFilter)) continue;
            long dx = (long) r.x - x;
            long dz = (long) r.z - z;
            long dist = dx * dx + dz * dz;
            if (dist < bestDist) {
                bestDist = dist;
                best = r;
            }
        }
        return best;
    }

    public static List<DungeonRecord> all() {
        return new ArrayList<>(list);
    }

    private static String normalize(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9]", "");
    }
}
