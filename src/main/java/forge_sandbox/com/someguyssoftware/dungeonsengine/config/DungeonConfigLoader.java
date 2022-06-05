/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeonsengine.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.dungeonsengine.json.GenericDeserializer;
import forge_sandbox.com.someguyssoftware.gottschcore.json.JSMin;
import java.io.File;
import otd.Main;

/**
 * @author Mark Gottschling on Dec 18, 2018
 *
 */
public class DungeonConfigLoader {
	private static final String DUNGEON_CONFIGS_RESOURCE_DEFAULT_PATH = "dungeons2/dungeons/config/";
	private static final String DUNGEON_CONFIGS_RESOURCE_BUILTIN_PATH = "/dungeons2/dungeons/config/builtin/";
	private static final String DEFAULT_CONFIG_FILE = "default.json";
	private static final String DUNGEON_CONFIGS_FS_PATH = "dungeons";

	static {
		// create folder
		createFolder();

		// expose resource configs if not existing on file system
		exposeDungeonsConfigs();
	}

	/**
	 * 
	 */
	public DungeonConfigLoader() {
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<IDungeonConfig> loadAll() {
		List<IDungeonConfig> configs = new ArrayList<>(5);

		// load all the files under the folder
		File config = Main.instance.getDataFolder();
		File fpath = new File(config.toString() + File.separator + "forge_sandbox" + File.separator + "dungeons2"
				+ File.separator + DUNGEON_CONFIGS_FS_PATH);

		// check if path/folder exists
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		Path path = fpath.toPath().toAbsolutePath();

		try {
			Files.walk(path).filter(Files::isRegularFile).forEach(f -> {
				List<IDungeonConfig> dungeonConfig = null;
				try {
					if (f.toString().endsWith(".json")) {
						dungeonConfig = load(f);
						configs.addAll(dungeonConfig);
					} else {
						Dungeons2.log.debug("skipping invalid dungeon config json file -> {}",
								new Object[] { f.toString() });
					}
				} catch (Exception e) {
					Dungeons2.log.error("Unable to load dungeon config json file -> " + f.toString(), e);
				}
			});
		} catch (Exception e) {
			Dungeons2.log.error("Unable to read dungeon config file.", e);
		}

		return configs;
	}

	// TODO change to use loadFromResource()
	/**
	 * Load defaul dungeon/level config from resource path (mod jar file)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static IDungeonConfig loadDefault() throws Exception {
		IDungeonConfig config = null;

		// read json sheet in and minify it
		InputStream is = Main.instance.getResource(DUNGEON_CONFIGS_RESOURCE_DEFAULT_PATH + DEFAULT_CONFIG_FILE);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSMin minifier = new JSMin(is, out);
		minifier.jsmin();

		// out minified json into a json reader
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Reader reader = new InputStreamReader(in);
		JsonReader jsonReader = new JsonReader(reader);

		// create a gson builder
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(ILevelConfig.class, new GenericDeserializer(LevelConfig.class));
		builder.registerTypeAdapter(IChestConfig.class, new GenericDeserializer(ChestConfig.class));

		Gson gson = builder.create();

		// read minified json into gson and generate objects
		try {
			config = gson.fromJson(jsonReader, DungeonConfig.class);
		} catch (JsonIOException | JsonSyntaxException e) {
			throw new Exception("Unable to load default dungeon config.", e);
		} finally {
			// close objects
			try {
				jsonReader.close();
				in.close();
				out.close();
			} catch (IOException e) {
				Dungeons2.log.warn("Unable to close JSON Reader when reading built-in style sheet.");
			}
		}
		return config;
	}

	/**
	 * 
	 * @param resourcePath
	 * @return
	 * @throws Exception
	 */
	public static List<IDungeonConfig> loadFromResource(String resourcePath) throws Exception {
		// read json sheet in and minify it
		InputStream is = Main.instance.getResource(resourcePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSMin minifier = new JSMin(is, out);
		minifier.jsmin();

		// out minified json into a json reader
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Reader reader = new InputStreamReader(in);
		JsonReader jsonReader = new JsonReader(reader);

		// create a gson builder
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(IDungeonConfig.class, new GenericDeserializer(DungeonConfig.class));
		builder.registerTypeAdapter(ILevelConfig.class, new GenericDeserializer(LevelConfig.class));
		builder.registerTypeAdapter(IChestConfig.class, new GenericDeserializer(ChestConfig.class));

		Gson gson = builder.create();
		List<IDungeonConfig> configs = null;

		// read minified json into gson and generate objects
		try {
			Type listType = new TypeToken<List<IDungeonConfig>>() {
			}.getType();
			configs = gson.fromJson(jsonReader, listType);
		} catch (JsonIOException | JsonSyntaxException e) {
			throw new Exception("Unable to load default dungeon config.", e);
		} finally {
			// close objects
			try {
				jsonReader.close();
				in.close();
				out.close();
			} catch (IOException e) {
				Dungeons2.log.warn("Unable to close JSON Reader when reading built-in style sheet.");
			}
		}
		return configs;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<IDungeonConfig> load(Path path) throws Exception {
		Dungeons2.log.debug("loading dungeon config from path -> {}", new Object[] { path.toString() });

		InputStream is = new FileInputStream(path.toString());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSMin minifier = new JSMin(is, out);
		minifier.jsmin();

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Reader reader = new InputStreamReader(in);
		JsonReader jsonReader = new JsonReader(reader);

		// create a gson builder
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(IDungeonConfig.class, new GenericDeserializer(DungeonConfig.class));
		builder.registerTypeAdapter(ILevelConfig.class, new GenericDeserializer(LevelConfig.class));
		builder.registerTypeAdapter(IChestConfig.class, new GenericDeserializer(ChestConfig.class));

		Gson gson = builder.create();
		List<IDungeonConfig> config = null;
		try {
			Type listType = new TypeToken<List<IDungeonConfig>>() {
			}.getType();
			config = gson.fromJson(jsonReader, /* DungeonConfig.class */listType);
			for (IDungeonConfig c : config) {
				if (c.getBiomeWhiteList() != null)
					c.getBiomeWhiteList().replaceAll(String::toUpperCase);
				if (c.getBiomeBlackList() != null)
					c.getBiomeBlackList().replaceAll(String::toUpperCase);
				for (ILevelConfig lc : c.getLevelConfigs()) {
					if (lc.getChestCategories() != null)
						lc.getChestCategories().replaceAll(String::toUpperCase);
				}
				Dungeons2.log.debug("loaded dungeon config -> {}", new Object[] { c });
			}
		} catch (JsonIOException | JsonSyntaxException e) {
			throw new Exception("Unable to load dungeon config.", e);
		} finally {
			// close objects
			try {
				jsonReader.close();
				is.close();
				out.close();
			} catch (IOException e) {
				Dungeons2.log.warn("Unable to close JSON Reader when reading dungeon config.");
			}
		}
		return config;
	}

	/**
	 * 
	 */
	private static void createFolder() {

	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void exposeDungeonsConfigs() {
		Stream<Path> walk = null;
		Path folder = null;

		FileSystem fs = getResourceAsFileSystem(DUNGEON_CONFIGS_RESOURCE_BUILTIN_PATH);
		if (fs == null)
			return;

		try {
			// get the base path of the resource
			Path resourceBasePath = fs.getPath(DUNGEON_CONFIGS_RESOURCE_BUILTIN_PATH);
//            Dungeons2.log.debug("resource base path -> {}", resourceBasePath.toString());

			boolean isFirst = true;
			// proces all the files in the folder
			walk = Files.walk(resourceBasePath, 1);
			for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
				Path resourceFilePath = it.next();
//                Dungeons2.log.debug("dungeon config file -> {}", resourceFilePath.toString());
				// check the first file, which is actually the given directory itself
				if (isFirst) {
					File config = Main.instance.getDataFolder();
					File fpath = new File(config.toString() + File.separator + "forge_sandbox" + File.separator
							+ "dungeons2" + File.separator + DUNGEON_CONFIGS_FS_PATH);
					folder = fpath.toPath().toAbsolutePath();
				} else {
					// test if file exists on the file system
					Path dungeonConfigPath = Paths.get(folder.toString(), resourceFilePath.getFileName().toString())
							.toAbsolutePath();
					Dungeons2.log.debug("dungeonConfigPath -> {}", new Object[] { dungeonConfigPath.toString() });

					// skip if not a json file
					if (!dungeonConfigPath.toString().endsWith(".json")) {
						Dungeons2.log.debug("Skipping non-valid .json file -> {}",
								new Object[] { dungeonConfigPath.toString() });
						continue;
					}

					// if the file already exists
					if (Files.exists(dungeonConfigPath)) {
						Dungeons2.log.debug("comparing config versions...");
						// load resource configs
						List<IDungeonConfig> resourceConfigs = loadFromResource(
								resourceFilePath.toAbsolutePath().toString());

						// load FS configs
						List<IDungeonConfig> fsConfigs = load(dungeonConfigPath);
						// map FS
						Map<String, IDungeonConfig> fsConfigsMap = fsConfigs.stream()
								.collect(Collectors.toMap(IDungeonConfig::getName, Function.identity()));
						// TODO need to keep this map around so we don't have to reload the file

						// now process each config in resource configs
						boolean isCurrent = true;
						for (IDungeonConfig rc : resourceConfigs) {

							if (!isCurrent) {
								// rename fs file as .bak
								Files.move(dungeonConfigPath,
										Paths.get(folder.toString(), resourceFilePath.getFileName().toString() + ".bak")
												.toAbsolutePath(),
										StandardCopyOption.REPLACE_EXISTING);
								break;
							}
						}
					}

					if (Files.notExists(dungeonConfigPath)) {
						// copy from resource/classpath to file path
						InputStream is = Dungeons2.class.getResourceAsStream(resourceFilePath.toString());
						try (FileOutputStream fos = new FileOutputStream(dungeonConfigPath.toFile())) {
							byte[] buf = new byte[2048];
							int r;
							while ((r = is.read(buf)) != -1) {
								fos.write(buf, 0, r);
							}
						} catch (IOException e) {
							Dungeons2.log.error("Error exposing chestsheet resource to file system.");
						}
					}
				}
				isFirst = false;
			}
		} catch (Exception e) {
			Dungeons2.log.error("error:", e);
		} finally {
			// close the stream
			if (walk != null) {
				walk.close();
			}
		}

		// close the file system
		if (fs != null && fs.isOpen()) {
			try {
				fs.close();
			} catch (IOException e) {
				Dungeons2.log.debug("An error occurred attempting to close the FileSystem:", e);
			}
		}
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	private static FileSystem getResourceAsFileSystem(String location) {
		FileSystem fs = null;
		Map<String, String> env = new HashMap<>();
		URI uri = null;

		// get the asset resource folder that is unique to this mod
		URL url = Main.class.getResource(location);
		if (url == null) {
			Dungeons2.log.error("Unable to locate resource {}", new Object[] { location });
			return null;
		}

		// convert to a uri
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			Dungeons2.log.error("An error occurred during dungeon config processing:", e);
			return null;
		}

		// split the uri into 2 parts - jar path and folder path within jar
		String[] array = uri.toString().split("!");
		try {
			fs = FileSystems.newFileSystem(URI.create(array[0]), env);
		} catch (IOException e) {
			Dungeons2.log.error("An error occurred during dungeon config processing:", e);
			return null;
		}

		return fs;
	}
}
