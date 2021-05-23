package forge_sandbox.com.someguyssoftware.dungeonsengine.chest;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeonsengine.config.IChestConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import org.bukkit.block.Block;
import org.bukkit.inventory.InventoryHolder;
import otd.lib.async.AsyncWorldEditor;

/**
 * 
 * @author Mark Gottschling on Jan 8, 2019
 *
 */
public interface ILootLoader {

    /**
     * 
     * @param world
     * @param inventory
     * @param config
     * @param random
     */
    void fill(AsyncWorldEditor world, InventoryHolder inventory, IChestConfig config, Random random);

    /**
     * 
     * @param world
     * @param random
     * @param entity
     * @param config
     */
    void fill(AsyncWorldEditor world, Random random, Block entity, IChestConfig config);

    /**
     * 
     * @param world
     * @param random
     * @param coords
     * @param config
     */
    void fill(AsyncWorldEditor world, Random random, ICoords coords, IChestConfig config);

}