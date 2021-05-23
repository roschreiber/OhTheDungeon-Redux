/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.generator.strategy.IRoomGenerationStrategy;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Jul 30, 2016
 *
 */
public interface IRoomGenerator {

    /**
     * @param world
     * @param coords
     * @param room
     * @param layout
     * @param styleSheet
     */
    public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet, LevelConfig config);
    public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet, ILevelConfig config);
    public IRoomGenerationStrategy getGenerationStrategy();
}