package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndShrooms extends ComponentTFMazeDeadEndRoots {

	public ComponentTFMazeDeadEndShrooms() {
		super();
	}

	public ComponentTFMazeDeadEndShrooms(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// no door

		// mycelium & mushrooms going back
		for (int x = 1; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				if (rand.nextInt(z + 2) > 0) {
					this.setBlockState(world, Blocks.mycelium, x, 0, z, sbb);
				}
				if (rand.nextInt(z + 2) > 0) {
					this.setBlockState(world, rand.nextBoolean() ? Blocks.red_mushroom : Blocks.brown_mushroom, x, 1, z,
							sbb);
				}
			}
		}

		// brackets?
		boolean mushFlag = rand.nextBoolean();
		BlockData mushType = mushFlag ? Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK)
				: Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);
		BlockData fullStem = Bukkit.createBlockData(Material.MUSHROOM_STEM);
		BlockData stem = fullStem.clone();
		{
			MultipleFacing mf = (MultipleFacing) stem;
			mf.setFace(BlockFace.DOWN, false);
			mf.setFace(BlockFace.UP, false);

			stem = mf;
		}
		int mushY = rand.nextInt(4) + 1;
		int mushZ = rand.nextInt(3) + 1;
		this.setBlockState(world, fullStem, 1, mushY - 1, mushZ, sbb);
		this.fillWithBlocks(world, sbb, 1, 1, mushZ, 1, mushY, mushZ, stem, AIR, false);
		this.fillWithBlocks(world, sbb, 1, mushY, mushZ - 1, 2, mushY, mushZ + 1, mushType, AIR, false);

		mushType = mushFlag ? Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK)
				: Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);
		mushY = rand.nextInt(4) + 1;
		mushZ = rand.nextInt(3) + 1;
		this.fillWithBlocks(world, sbb, 4, 1, mushZ, 4, mushY, mushZ, stem, AIR, false);
		this.fillWithBlocks(world, sbb, 3, mushY, mushZ - 1, 4, mushY, mushZ + 1, mushType, AIR, false);

		mushType = mushFlag ? Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK)
				: Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);
		mushY = rand.nextInt(4) + 1;
		int mushX = rand.nextInt(3) + 2;
		this.fillWithBlocks(world, sbb, mushX, 1, 4, mushX, mushY, 4, stem, AIR, false);
		this.fillWithBlocks(world, sbb, mushX - 1, mushY, 3, mushX + 1, mushY, 4, mushType, AIR, false);

		return true;
	}
}
