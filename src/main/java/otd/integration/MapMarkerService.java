package otd.integration;

import org.bukkit.Bukkit;
import otd.locate.DungeonRecord;
import otd.redux.util.ConsoleManager;

import java.util.ArrayList;
import java.util.List;

public final class MapMarkerService {

    private static final List<MapIntegration> all = new ArrayList<>();

    private MapMarkerService() {
    }

    public static void enable() {
        all.clear();
        register("BlueMap");
        register("squaremap");
    }

    private static void register(String plugin) {
        if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
            ConsoleManager.logWarning(plugin + " not installed, will disable map markers for  " + plugin);
            return;
        }
        try {
            MapIntegration impl = plugin.equals("BlueMap") ? new BlueMapImpl() : new SquaremapImpl();
            all.add(impl);
            impl.enable();
            ConsoleManager.logInfo("Map integration enabled: " + impl.name());
        } catch (Throwable t) {
            ConsoleManager.logWarning("Failed to enable map integration " + plugin + ": " + t);
        }
    }

    public static void addOne(DungeonRecord r) {
        runOnMain(() -> {
            for (MapIntegration impl : all) {
                try {
                    if (impl.isEnabled()) {
                        impl.addMarker(r);
                    }
                } catch (Throwable t) {
                    ConsoleManager.logWarning("Map integration " + impl.name() + " failed to add marker: " + t.getMessage());
                }
            }
        });
    }

    public static void reloadAll(List<DungeonRecord> records) {
        runOnMain(() -> {
            for (MapIntegration impl : all) {
                try {
                    if (impl.isEnabled()) {
                        impl.reload(records);
                    }
                } catch (Throwable t) {
                    ConsoleManager.logWarning("Map integration " + impl.name() + " failed to reload markers: " + t.getMessage());
                }
            }
        });
    }

    private static void runOnMain(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Bukkit.getScheduler().runTask(otd.Main.instance, task);
        }
    }
}
