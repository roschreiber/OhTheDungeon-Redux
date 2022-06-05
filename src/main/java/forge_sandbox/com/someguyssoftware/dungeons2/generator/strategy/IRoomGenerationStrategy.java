package forge_sandbox.com.someguyssoftware.dungeons2.generator.strategy;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider.IDungeonsBlockProvider;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import otd.lib.async.AsyncWorldEditor;

public interface IRoomGenerationStrategy {

	/**
	 * 
	 * @param world
	 * @param random
	 * @param room
	 * @param theme
	 * @param styleSheet
	 * @param config
	 */
	public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
			LevelConfig config);

	public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
			ILevelConfig config);

	public IDungeonsBlockProvider getBlockProvider();
}