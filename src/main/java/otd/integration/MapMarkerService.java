package otd.integration;

import org.bukkit.Bukkit;
import otd.locate.DungeonRecord;
import otd.redux.util.ConsoleManager;

import java.util.List;

public final class MapMarkerService {

    private static final MapIntegration[] all = new MapIntegration[]{
            new BlueMapImpl(), new SquaremapImpl()
    };

    private MapMarkerService() {
    }

    public static void enable() {
        for (MapIntegration impl : all) {
            try {
                if (impl.isReady()) {
                    impl.enable();
                    if (impl.isEnabled()) {
                        ConsoleManager.logInfo("Map integration enabled: " + impl.name());
                    }
                }
            } catch (Throwable t) {
                ConsoleManager.logWarning("Failed to enable map integration " + impl.name() + ": " + t.getMessage());
            }
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
