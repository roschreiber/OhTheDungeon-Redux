package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFTreasure;

public class ComponentTFMazeDeadEndTrappedChest extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndTrappedChest() {
		super();
	}

	public ComponentTFMazeDeadEndTrappedChest(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);

		// specify a non-existant high spawn list value to stop actual monster spawns
		this.spawnListIndex = Integer.MAX_VALUE;
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// super.addComponentParts(world, rand, sbb);

		// dais
		this.setBlockState(world, Material.OAK_PLANKS, 2, 1, 4, sbb);
		this.setBlockState(world, Material.OAK_PLANKS, 3, 1, 4, sbb);
		this.setBlockState(world,
				getStairState(Bukkit.createBlockData(Material.OAK_STAIRS), BlockFace.NORTH, rotation, false), 2, 1, 3,
				sbb);
		this.setBlockState(world,
				getStairState(Bukkit.createBlockData(Material.OAK_STAIRS), BlockFace.NORTH, rotation, false), 3, 1, 3,
				sbb);

		// chest
		this.setBlockState(world, Material.TRAPPED_CHEST, 2, 2, 4, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 3, 2, 4, TFTreasure.labyrinth_deadend, true, sbb);

//        // torches
//        this.setBlockState(world, Blocks.TORCH, 0, 1, 3, 4, sbb);
//        this.setBlockState(world, Blocks.TORCH, 0, 4, 3, 4, sbb);

		// doorway w/ bars
		this.fillWithBlocks(world, sbb, 1, 1, 0, 4, 3, 1, Blocks.chiseled_maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 1, 4, 0, 4, 4, 1, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 2, 1, 0, 3, 3, 1, Blocks.iron_bars, AIR, false);

		// TNT!
		Material tnt = Material.TNT;
		this.setBlockState(world, tnt, 2, 0, 3, sbb);
		this.setBlockState(world, tnt, 3, 0, 3, sbb);
		this.setBlockState(world, tnt, 2, 0, 4, sbb);
		this.setBlockState(world, tnt, 3, 0, 4, sbb);

		return true;
	}

}
