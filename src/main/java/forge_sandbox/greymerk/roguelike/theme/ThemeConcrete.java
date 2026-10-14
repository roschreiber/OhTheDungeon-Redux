package forge_sandbox.greymerk.roguelike.theme;

import org.bukkit.Material;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.DoorType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemeConcrete extends ThemeBase {

	public ThemeConcrete() {

        BlockWeightedRandom walls = new BlockWeightedRandom();
        walls.addBlock(new MetaBlock(Material.GRAY_CONCRETE), 20);
        walls.addBlock(new MetaBlock(Material.LIGHT_GRAY_CONCRETE), 10);
        walls.addBlock(new MetaBlock(Material.CYAN_CONCRETE), 3);
        walls.addBlock(new MetaBlock(Material.STONE_BRICKS), 3);
        walls.addBlock(new MetaBlock(Material.CRACKED_STONE_BRICKS), 2);

        BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(new MetaBlock(Material.LIGHT_GRAY_CONCRETE), 16);
		floor.addBlock(new MetaBlock(Material.GRAY_CONCRETE), 8);
		floor.addBlock(new MetaBlock(Material.POLISHED_ANDESITE), 4);
		floor.addBlock(new MetaBlock(Material.GRAVEL), 1);

        MetaBlock pillar = new MetaBlock(Material.CYAN_CONCRETE);

        MetaStair stair = new MetaStair(Material.GRAY_CONCRETE_STAIRS);

        IDoor door = new Door(DoorType.IRON);

        this.primary = new BlockSet(floor, walls, stair, pillar, door);

        BlockWeightedRandom walls2 = new BlockWeightedRandom();
		walls2.addBlock(new MetaBlock(Material.LIGHT_GRAY_CONCRETE), 16);
		walls2.addBlock(new MetaBlock(Material.WHITE_CONCRETE), 6);
		walls2.addBlock(new MetaBlock(Material.STONE_BRICKS), 3);

        MetaBlock pillar2 = new MetaBlock(Material.GRAY_CONCRETE);

        MetaStair stair2 = new MetaStair(Material.LIGHT_GRAY_CONCRETE_STAIRS);

        this.secondary = new BlockSet(floor, walls2, stair2, pillar2, door);
	}
}
