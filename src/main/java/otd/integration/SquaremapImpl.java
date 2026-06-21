package otd.integration;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.World;

import otd.locate.DungeonLog;
import otd.locate.DungeonRecord;
import otd.redux.util.ConsoleManager;

public final class SquaremapImpl implements MapIntegration {

    private static final String LAYER_NAME = "OTD Dungeons";
    private static final String LAYER_KEY = "otd-dungeons";

    private volatile boolean enabled = false;

    private final Map<String, xyz.jpenilla.squaremap.api.SimpleLayerProvider> layers = new HashMap<>();

    @Override
    public String name() {
        return "squaremap";
    }

    @Override
    public boolean isReady() {
        return Bukkit.getPluginManager().getPlugin("squaremap") != null;
    }

    @Override
    public boolean isEnabled() {
        return enabled && isReady() && otd.config.WorldConfig.wc.enable_squaremap_markers;
    }

    @Override
    public void enable() {
        if (!isReady()) {
            return;
        }
        if (!otd.config.WorldConfig.wc.enable_squaremap_markers) {
            return;
        }
        try {
            xyz.jpenilla.squaremap.api.Squaremap api = xyz.jpenilla.squaremap.api.SquaremapProvider.get();

            layers.clear();
            for (World world : Bukkit.getWorlds()) {
                try {
                    xyz.jpenilla.squaremap.api.WorldIdentifier id =
                            xyz.jpenilla.squaremap.api.BukkitAdapter.worldIdentifier(world);
                    Optional<xyz.jpenilla.squaremap.api.MapWorld> mapWorld = api.getWorldIfEnabled(id);
                    if (mapWorld.isEmpty()) {
                        continue;
                    }

                    xyz.jpenilla.squaremap.api.SimpleLayerProvider provider =
                            xyz.jpenilla.squaremap.api.SimpleLayerProvider.builder(LAYER_NAME)
                                    .showControls(true)
                                    .defaultHidden(false)
                                    .layerPriority(5)
                                    .build();

                    xyz.jpenilla.squaremap.api.Registry<xyz.jpenilla.squaremap.api.LayerProvider> registry =
                            mapWorld.get().layerRegistry();
                    xyz.jpenilla.squaremap.api.Key key = xyz.jpenilla.squaremap.api.Key.of(LAYER_KEY);
                    if (registry.hasEntry(key)) {
                        registry.unregister(key);
                    }
                    registry.register(key, provider);
                    layers.put(world.getName().toLowerCase(), provider);
                } catch (Throwable t) {
                    ConsoleManager.logWarning("squaremap: failed to register layer for world "
                            + world.getName() + ": " + t.getMessage());
                }
            }

            enabled = true;
            ConsoleManager.logInfo("Loading squaremap support ...");
            reload(DungeonLog.all());
        } catch (Throwable t) {
            enabled = false;
            ConsoleManager.logWarning("squaremap: could not hook into squaremap API: " + t.getMessage());
        }
    }

    @Override
    public void addMarker(DungeonRecord r) {
        if (!isEnabled() || r == null) {
            return;
        }
        try {
            xyz.jpenilla.squaremap.api.SimpleLayerProvider provider = layers.get(r.world.toLowerCase());
            if (provider == null) {
                return;
            }
            provider.addMarker(markerKey(r), buildMarker(r));
        } catch (Throwable t) {
            ConsoleManager.logWarning("squaremap: failed to add marker: " + t.getMessage());
        }
    }

    @Override
    public void reload(List<DungeonRecord> records) {
        if (!isEnabled()) {
            return;
        }
        try {
            for (xyz.jpenilla.squaremap.api.SimpleLayerProvider provider : layers.values()) {
                provider.clearMarkers();
            }
            List<DungeonRecord> snapshot = records == null ? new ArrayList<>() : records;
            for (DungeonRecord r : snapshot) {
                if (r == null) {
                    continue;
                }
                xyz.jpenilla.squaremap.api.SimpleLayerProvider provider = layers.get(r.world.toLowerCase());
                if (provider == null) {
                    continue;
                }
                provider.addMarker(markerKey(r), buildMarker(r));
            }
        } catch (Throwable t) {
            ConsoleManager.logWarning("squaremap: failed to reload markers: " + t.getMessage());
        }
    }

    private static xyz.jpenilla.squaremap.api.Key markerKey(DungeonRecord r) {
        // Key only permits [a-z0-9-_]
        String raw = ("otd_" + r.world + "_" + r.x + "_" + r.z).toLowerCase().replaceAll("[^a-z0-9_]", "_");
        return xyz.jpenilla.squaremap.api.Key.of(raw);
    }

    private static xyz.jpenilla.squaremap.api.marker.Marker buildMarker(DungeonRecord r) {
        xyz.jpenilla.squaremap.api.Point point =
                xyz.jpenilla.squaremap.api.Point.of(r.x, r.z);
        xyz.jpenilla.squaremap.api.marker.Marker marker =
                xyz.jpenilla.squaremap.api.marker.Marker.circle(point, 8);
        marker.markerOptions(
                xyz.jpenilla.squaremap.api.marker.MarkerOptions.builder()
                        .strokeColor(new Color(0x9C27B0))
                        .fillColor(new Color(0x9C27B0))
                        .fillOpacity(0.4)
                        .clickTooltip(r.type + " (" + r.x + ", " + r.z + ")")
                        .hoverTooltip(r.type)
                        .build());
        return marker;
    }
}
