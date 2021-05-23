/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import java.util.List;
import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.graph.Wayline;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 15, 2016
 *
 */
public interface IShaftGenerator {

    /**
     * 
     * @param world
     * @param random
     * @param level
     * @param wayline
     * @param theme
     * @param styleSheet
     * @param config
     */
    void generate(AsyncWorldEditor world, Random random, List<Room> rooms, Wayline wayline, Theme theme,
            StyleSheet styleSheet, LevelConfig config);
}
