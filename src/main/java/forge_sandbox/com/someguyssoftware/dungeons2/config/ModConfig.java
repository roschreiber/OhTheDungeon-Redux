package forge_sandbox.com.someguyssoftware.dungeons2.config;

import java.io.File;
import otd.Main;

/**
 * 
 * @author Mark Gottschling on Jul 29, 2017
 *
 */
public class ModConfig {

	// enablements
	public static Boolean enableDungeons = true;
	public static Boolean enableSpawners = true;
	public static Boolean enableChests = true;
	public static Boolean supportOn = true;
	public static int decayMultiplier = 5;
	public static Boolean enableTreasure2Integration = true;
	public static int treasure2ChestProbability = 50;

	public static String dungeonsFolder = Main.instance.getDataFolder().toString() + File.separator + "forge_sandbox"
			+ File.separator + "dungeons2";
	public static String spawnSheetFile = Main.instance.getDataFolder().toString() + File.separator + "forge_sandbox"
			+ File.separator + "dungeons2" + File.separator + "spawnSheet.json";
	public static String chestSheetFile = Main.instance.getDataFolder().toString() + File.separator + "forge_sandbox"
			+ File.separator + "dungeons2" + File.separator + "chestSheet.json";
	public static String styleSheetFile = Main.instance.getDataFolder().toString() + File.separator + "forge_sandbox"
			+ File.separator + "dungeons2" + File.separator + "styleSheet.json";
}
