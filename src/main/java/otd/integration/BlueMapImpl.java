package otd.integration;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.BlueMapWorld;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;

import com.flowpowered.math.vector.Vector3d;

import otd.config.WorldConfig;
import otd.locate.DungeonLog;
import otd.locate.DungeonRecord;
import otd.redux.util.ConsoleManager;

public final class BlueMapImpl implements MapIntegration {

    private static final String MARKER_SET_ID = "otd-dungeons";
    private static final int NOMINAL_Y = 64;

    private volatile BlueMapAPI api;
    private volatile boolean enabled;

    @Override
    public String name() {
        return "BlueMap";
    }

    @Override
    public boolean isReady() {
        return Bukkit.getPluginManager().getPlugin("BlueMap") != null;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        if (!isReady()) return;
        if (!WorldConfig.wc.enable_bluemap_markers) return;

        BlueMapAPI.onEnable(loadedApi -> {
            this.api = loadedApi;
            this.enabled = true;
            try {
                reload(DungeonLog.all());
            } catch (Throwable t) {
                ConsoleManager.logWarning("BlueMap: failed to load initial dungeon markers: " + t.getMessage());
            }
        });
    }

    @Override
    public void addMarker(DungeonRecord r) {
        if (!enabled || api == null || r == null) return;
        World world = Bukkit.getWorld(r.world);
        if (world == null) return;
        try {
            POIMarker marker = buildMarker(r);
            String markerId = markerId(r);
            api.getWorld(world).ifPresent(bmWorld -> {
                for (BlueMapMap map : bmWorld.getMaps()) {
                    markerSet(map).getMarkers().put(markerId, marker);
                }
            });
        } catch (Throwable t) {
            ConsoleManager.logWarning("BlueMap: failed to add dungeon marker: " + t.getMessage());
        }
    }

    @Override
    public void reload(List<DungeonRecord> records) {
        if (!enabled || api == null || records == null) return;
        try {
            for (BlueMapWorld bmWorld : api.getWorlds()) {
                for (BlueMapMap map : bmWorld.getMaps()) {
                    map.getMarkerSets().remove(MARKER_SET_ID);
                }
            }
            for (DungeonRecord r : records) {
                if (r == null) continue;
                World world = Bukkit.getWorld(r.world);
                if (world == null) continue;
                POIMarker marker = buildMarker(r);
                String markerId = markerId(r);
                api.getWorld(world).ifPresent(bmWorld -> {
                    for (BlueMapMap map : bmWorld.getMaps()) {
                        markerSet(map).getMarkers().put(markerId, marker);
                    }
                });
            }
        } catch (Throwable t) {
            ConsoleManager.logWarning("BlueMap: failed to reload dungeon markers: " + t.getMessage());
        }
    }

    private MarkerSet markerSet(BlueMapMap map) {
        return map.getMarkerSets().computeIfAbsent(MARKER_SET_ID,
                k -> MarkerSet.builder().label("OTD Dungeons").toggleable(true).build());
    }

    private POIMarker buildMarker(DungeonRecord r) {
        return POIMarker.builder()
                .label(r.type)
                .detail(r.type + "<br>x=" + r.x + ", z=" + r.z)
                .position(new Vector3d(r.x, NOMINAL_Y, r.z))
                .build();
    }

    private String markerId(DungeonRecord r) {
        return "otd-" + r.world + "-" + r.x + "-" + r.z;
    }
}
