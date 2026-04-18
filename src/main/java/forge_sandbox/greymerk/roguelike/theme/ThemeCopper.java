package forge_sandbox.greymerk.roguelike.theme;

import org.bukkit.Material;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;

public class ThemeCopper extends ThemeBase {

	public ThemeCopper() {

        BlockWeightedRandom walls = new BlockWeightedRandom();
        walls.addBlock(new MetaBlock(Material.WEATHERED_COPPER), 18);
        walls.addBlock(new MetaBlock(Material.OXIDIZED_COPPER), 18);
        walls.addBlock(new MetaBlock(Material.WEATHERED_CUT_COPPER), 14);
        walls.addBlock(new MetaBlock(Material.OXIDIZED_CUT_COPPER), 14);
        walls.addBlock(new MetaBlock(Material.WEATHERED_CHISELED_COPPER), 10);
        walls.addBlock(new MetaBlock(Material.OXIDIZED_CHISELED_COPPER), 10);
        walls.addBlock(new MetaBlock(Material.STONE_BRICKS), 6);
        walls.addBlock(new MetaBlock(Material.CRACKED_STONE_BRICKS), 4);
        walls.addBlock(new MetaBlock(Material.COBBLESTONE), 6);
        walls.addBlock(new MetaBlock(Material.GRAVEL), 2);

        BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(new MetaBlock(Material.WEATHERED_CUT_COPPER), 16);
		floor.addBlock(new MetaBlock(Material.OXIDIZED_CUT_COPPER), 16);
		floor.addBlock(new MetaBlock(Material.WEATHERED_COPPER), 8);
		floor.addBlock(new MetaBlock(Material.OXIDIZED_COPPER), 8);
		floor.addBlock(new MetaBlock(Material.STONE_BRICKS), 5);
		floor.addBlock(new MetaBlock(Material.COBBLESTONE), 3);
		floor.addBlock(new MetaBlock(Material.GRAVEL), 1);

        BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(new MetaBlock(Material.WEATHERED_CHISELED_COPPER), 14);
		pillar.addBlock(new MetaBlock(Material.OXIDIZED_CHISELED_COPPER), 14);
		pillar.addBlock(new MetaBlock(Material.WEATHERED_COPPER), 6);
		pillar.addBlock(new MetaBlock(Material.OXIDIZED_COPPER), 6);
		pillar.addBlock(new MetaBlock(Material.STONE_BRICKS), 3);

        MetaStair stair = new MetaStair(Material.WEATHERED_CUT_COPPER_STAIRS);

        this.primary = new BlockSet(floor, walls, stair, pillar);

        BlockWeightedRandom walls2 = new BlockWeightedRandom();
		walls2.addBlock(new MetaBlock(Material.WEATHERED_CUT_COPPER), 16);
		walls2.addBlock(new MetaBlock(Material.EXPOSED_CUT_COPPER), 8);
		walls2.addBlock(new MetaBlock(Material.WEATHERED_CHISELED_COPPER), 6);
		walls2.addBlock(new MetaBlock(Material.STONE_BRICKS), 5);
		walls2.addBlock(new MetaBlock(Material.CRACKED_STONE_BRICKS), 2);

        BlockWeightedRandom pillar2 = new BlockWeightedRandom();
		pillar2.addBlock(new MetaBlock(Material.WEATHERED_CHISELED_COPPER), 10);
		pillar2.addBlock(new MetaBlock(Material.EXPOSED_CHISELED_COPPER), 6);
		pillar2.addBlock(new MetaBlock(Material.STONE_BRICKS), 3);

        MetaStair stair2 = new MetaStair(Material.EXPOSED_CUT_COPPER_STAIRS);

        this.secondary = new BlockSet(floor, walls2, stair2, pillar2);
		// BlockStripes floor = new BlockStripes();
		// floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.BROWN));
		// floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.YELLOW));

		// MetaBlock walls = BlockType.get(BlockType.ENDER_BRICK);

		// MetaStair stair = new MetaStair(StairType.SANDSTONE);
		// MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);

		// this.primary = new BlockSet(floor, walls, stair, pillar);

		// this.secondary = new BlockSet(floor, BlockType.get(BlockType.SANDSTONE_CHISELED), stair, pillar);
	}
}
