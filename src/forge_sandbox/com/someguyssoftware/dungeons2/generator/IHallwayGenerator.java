/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import java.util.List;
import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.graph.Wayline;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Level;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 6, 2016
 *
 */
public interface IHallwayGenerator {

    /**
     * 
     * @param world
     * @param random
     * @param wayline
     * @param rooms
     * @param hallways
     * @param theme
     * @param styleSheet
     * @param config
     */
    void generate(AsyncWorldEditor world, Random random, Level level, Wayline wayline, List<Room> hallways, Theme theme,
            StyleSheet styleSheet, LevelConfig config);
}
