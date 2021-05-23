/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.strategy.IRoomGenerationStrategy;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 28, 2016
 *
 */
public class EntranceRoomGenerator extends AbstractExteriorRoomGenerator {

    private IRoomGenerationStrategy roomGenerationStrategy;
    
    /**
     * Enforce that the room generator has to have a structure generator.
     * @param generator
     */
    public EntranceRoomGenerator(IRoomGenerationStrategy generator) {
        setGenerationStrategy(generator);
    }
    
    @Override
    public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
            ILevelConfig config) {
        // generate the room structure
        getGenerationStrategy().generate(world, random, room, theme, styleSheet, config);
        // build doorway
        buildDoorway(world, room);
    }
    
    @Deprecated
    @Override
    public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
            LevelConfig config) {
        Dungeons2.log.debug("Has Crenellation:" + room.hasCrenellation());
        Dungeons2.log.debug("Has Parapet:"+ room.hasParapet());
        Dungeons2.log.debug("Has Merlon:" + room.hasMerlon());
        Dungeons2.log.debug("Has Cornice:" + room.hasCornice());
        Dungeons2.log.debug("Has Plinth:" + room.hasPlinth());
        
        
        // generate the room structure
        getGenerationStrategy().generate(world, random, room, theme, styleSheet, config);
        
        /*
         *  add additional elements
         */
        
        // build doorway
        buildDoorway(world, room);
        
    }

    /**
     * @return the roomGenerationStrategy
     */
    @Override
    public IRoomGenerationStrategy getGenerationStrategy() {
        return roomGenerationStrategy;
    }

    /**
     * @param roomGenerationStrategy the roomGenerationStrategy to set
     */
    public final void setGenerationStrategy(IRoomGenerationStrategy roomGenerationStrategy) {
        this.roomGenerationStrategy = roomGenerationStrategy;
    }
}
