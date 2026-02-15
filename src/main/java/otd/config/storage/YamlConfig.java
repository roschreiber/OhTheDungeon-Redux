package otd.config.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.redux.util.ConsoleManager;

// https://github.com/google/gson/blob/main/UserGuide.md
public class YamlConfig implements ConfigImpl {

    private final JavaPlugin plugin;
    private final File worldsDir;
    private final File globalFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public YamlConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.worldsDir = new File(plugin.getDataFolder(), "worlds");
        this.globalFile = new File(plugin.getDataFolder(), "global.yml");
    }

    @Override
    public void load() {
        if (!worldsDir.exists()) worldsDir.mkdirs();
    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public void setValue(String key, String val) {
    }

    @Override
    public void close() {
    }

    public boolean hasExistingConfig() {
        if (globalFile.exists()) return true;
        String[] files = worldsDir.exists() ? worldsDir.list() : null;
        return files != null && files.length > 0;
    }

    public void saveAll(WorldConfig wc) {
        try {
            saveToYaml(wc, globalFile, "dict");

            for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
                saveToYaml(entry.getValue(), getWorldFile(entry.getKey()), null);
            }
        } catch (Exception e) {
            ConsoleManager.logError("Failed to save YAML config");
        }
    }

    public WorldConfig loadAll() {
        WorldConfig wc = new WorldConfig();

        if (globalFile.exists()) {
            wc = loadFromYaml(wc, globalFile);
        }

        File[] files = worldsDir.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                String worldName = file.getName().replace(".yml", "");
                try {
                    SimpleWorldConfig swc = loadFromYaml(new SimpleWorldConfig(), file);
                    wc.dict.put(worldName, swc);
                } catch (Exception e) {
                    ConsoleManager.logError("Failed to load world: " + worldName);
                }
            }
        }

        return wc;
    }

    private void saveToYaml(Object obj, File file, String skipField) throws IOException {
        JsonObject json = gson.toJsonTree(obj).getAsJsonObject();
        if (skipField != null) json.remove(skipField);

        Map<String, Object> map = jsonToMap(json);
        YamlConfiguration yaml = new YamlConfiguration();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            yaml.set(entry.getKey(), entry.getValue());
        }
        yaml.save(file);
    }

    // gson.fromJson turns a JSON string back into a Java object
    @SuppressWarnings("unchecked")
    private <T> T loadFromYaml(T defaults, File file) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        Map<String, Object> flatValues = new LinkedHashMap<>();
        for (String key : yaml.getKeys(true)) {
            if (!yaml.isConfigurationSection(key)) {
                flatValues.put(key, yaml.get(key));
            }
        }

        JsonObject base = gson.toJsonTree(defaults).getAsJsonObject();
        applyFlatMapToJson(base, flatValues);

        return (T) gson.fromJson(base, defaults.getClass());
    }

    private Map<String, Object> jsonToMap(JsonObject json) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> e : json.entrySet()) {
            map.put(e.getKey(), toPlainJava(e.getValue()));
        }
        return map;
    }

    private Object toPlainJava(JsonElement el) {
        if (el.isJsonNull()) return null;
        if (el.isJsonObject()) return jsonToMap(el.getAsJsonObject());

        if (el.isJsonArray()) {
            List<Object> list = new ArrayList<>();
            for (JsonElement item : el.getAsJsonArray()) list.add(toPlainJava(item));
            return list;
        }

        JsonPrimitive p = el.getAsJsonPrimitive();
        if (p.isBoolean()) return p.getAsBoolean();
        if (p.isNumber()) {
            double d = p.getAsDouble();
            if (d == Math.floor(d) && !Double.isInfinite(d)) {
                long l = p.getAsLong();
                if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) return (int) l;
                return l;
            }
            return d;
        }
        return p.getAsString();
    }

    private void applyFlatMapToJson(JsonObject root, Map<String, Object> flatMap) {
        for (Map.Entry<String, Object> entry : flatMap.entrySet()) {
            String[] parts = entry.getKey().split("\\.");
            JsonObject current = root;

            for (int i = 0; i < parts.length - 1; i++) {
                if (!current.has(parts[i]) || !current.get(parts[i]).isJsonObject()) {
                    current.add(parts[i], new JsonObject());
                }
                current = current.getAsJsonObject(parts[i]);
            }

            current.add(parts[parts.length - 1], gson.toJsonTree(entry.getValue()));
        }
    }

    // Sanitize world names cuz of weird characters
    private File getWorldFile(String worldName) {
        String safeName = worldName.replaceAll("[^a-zA-Z0-9_\\-.]", "_");
        return new File(worldsDir, safeName + ".yml");
    }
}
