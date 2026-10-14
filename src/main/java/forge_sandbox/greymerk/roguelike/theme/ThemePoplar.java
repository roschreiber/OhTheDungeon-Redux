package forge_sandbox.greymerk.roguelike.theme;

import org.bukkit.Material;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemePoplar extends ThemeBase {

	public ThemePoplar() {

        BlockWeightedRandom walls = new BlockWeightedRandom();
        walls.addBlock(new MetaBlock(Material.POPLAR_PLANKS), 20);
        walls.addBlock(new MetaBlock(Material.STRIPPED_POPLAR_WOOD), 8);
        walls.addBlock(new MetaBlock(Material.POPLAR_WOOD), 4);
        walls.addBlock(new MetaBlock(Material.MOSSY_COBBLESTONE), 4);
        walls.addBlock(new MetaBlock(Material.COBBLESTONE), 3);

        BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(new MetaBlock(Material.POPLAR_PLANKS), 16);
		floor.addBlock(new MetaBlock(Material.COARSE_DIRT), 5);
		floor.addBlock(new MetaBlock(Material.PODZOL), 4);
		floor.addBlock(new MetaBlock(Material.ROOTED_DIRT), 2);
		floor.addBlock(new MetaBlock(Material.MOSSY_COBBLESTONE), 2);

        MetaBlock pillar = new MetaBlock(Material.POPLAR_LOG);

        MetaStair stair = new MetaStair(Material.POPLAR_STAIRS);

        IDoor door = new Door(new MetaBlock(Material.POPLAR_DOOR));

        this.primary = new BlockSet(floor, walls, stair, pillar, door);

        BlockWeightedRandom walls2 = new BlockWeightedRandom();
		walls2.addBlock(new MetaBlock(Material.STRIPPED_POPLAR_WOOD), 14);
		walls2.addBlock(new MetaBlock(Material.POPLAR_PLANKS), 8);
		walls2.addBlock(new MetaBlock(Material.MOSSY_STONE_BRICKS), 4);

        MetaBlock pillar2 = new MetaBlock(Material.STRIPPED_POPLAR_LOG);

        this.secondary = new BlockSet(floor, walls2, stair, pillar2, door);
	}
}
