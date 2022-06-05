package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFTreasure;

public class ComponentTFMazeRoomBoss extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomBoss() {
		super();
	}

	public ComponentTFMazeRoomBoss(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// doorways
		if (this.getBlockStateFromPos(world, 7, 1, 0, sbb) == Material.AIR) {
			fillWithBlocks(world, sbb, 6, 1, 0, 9, 4, 0, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		}

		if (this.getBlockStateFromPos(world, 7, 1, 15, sbb) == Material.AIR) {
			fillWithBlocks(world, sbb, 6, 1, 15, 9, 4, 15, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		}

		if (this.getBlockStateFromPos(world, 0, 1, 7, sbb) == Material.AIR) {
			fillWithBlocks(world, sbb, 0, 1, 6, 0, 4, 9, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		}

		if (this.getBlockStateFromPos(world, 15, 1, 7, sbb) == Material.AIR) {
			fillWithBlocks(world, sbb, 15, 1, 6, 15, 4, 9, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		}

		// mycelium / small mushrooms on floor
		for (int x = 1; x < 14; x++) {
			for (int z = 1; z < 14; z++) {
				// calculate distance from middle
				int dist = (int) Math.round(7 / Math.sqrt((7.5 - x) * (7.5 - x) + (7.5 - z) * (7.5 - z)));
				boolean mycelium = rand.nextInt(dist + 1) > 0;
				boolean mushroom = rand.nextInt(dist) > 0;
				boolean mushRed = rand.nextBoolean();

				// make part of the floor mycelium
				if (mycelium) {
					this.setBlockState(world, Material.MYCELIUM, x, 0, z, sbb);
				}
				// add small mushrooms all over
				if (mushroom) {
					this.setBlockState(world, mushRed ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM, x, 1, z, sbb);
				}
			}
		}

		// mushroom chest shelves in corner
		final BlockData redMushroom = Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK);
		final BlockData brownMushroom = Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);

		fillWithBlocks(world, sbb, 1, 1, 1, 3, 1, 3, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 1, 2, 1, 1, 3, 4, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 2, 2, 1, 4, 3, 1, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 1, 4, 1, 3, 4, 3, redMushroom, AIR, false);
		placeTreasureAtCurrentPosition(world, rand, 3, 2, 3, TFTreasure.labyrinth_room, sbb);

		fillWithBlocks(world, sbb, 12, 1, 12, 14, 1, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 14, 2, 11, 14, 3, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 11, 2, 14, 14, 3, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 12, 4, 12, 14, 4, 14, redMushroom, AIR, false);
		placeTreasureAtCurrentPosition(world, rand, 12, 2, 12, TFTreasure.labyrinth_room, sbb);

		fillWithBlocks(world, sbb, 1, 1, 12, 3, 1, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 1, 2, 11, 1, 3, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 2, 2, 14, 4, 3, 14, redMushroom, AIR, false);
		fillWithBlocks(world, sbb, 1, 4, 12, 3, 4, 14, redMushroom, AIR, false);
		placeTreasureAtCurrentPosition(world, rand, 3, 2, 12, TFTreasure.labyrinth_room, sbb);

		fillWithBlocks(world, sbb, 12, 1, 1, 14, 1, 3, brownMushroom, AIR, false);
		fillWithBlocks(world, sbb, 11, 2, 1, 14, 3, 1, brownMushroom, AIR, false);
		fillWithBlocks(world, sbb, 14, 2, 2, 14, 3, 4, brownMushroom, AIR, false);
		fillWithBlocks(world, sbb, 12, 4, 1, 14, 4, 3, brownMushroom, AIR, false);
		placeTreasureAtCurrentPosition(world, rand, 12, 2, 3, TFTreasure.labyrinth_room, sbb);

		// a few more ceilingshrooms
		fillWithBlocks(world, sbb, 5, 4, 5, 7, 5, 7, brownMushroom, AIR, false);
		fillWithBlocks(world, sbb, 8, 4, 8, 10, 5, 10, redMushroom, AIR, false);

		// the moo-cen-mino-shrom-taur!
		// TODO : Boss
//        final IBlockState taurSpawner = TFBlocks.boss_spawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, BossVariant.MINOSHROOM);
//        setBlockStateRotated(world, taurSpawner, 7, 1, 7, Rotation.NONE, sbb);

		return true;
	}
}
