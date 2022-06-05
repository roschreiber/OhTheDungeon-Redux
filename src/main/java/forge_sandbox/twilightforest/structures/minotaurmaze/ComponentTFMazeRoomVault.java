package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFTreasure;

public class ComponentTFMazeRoomVault extends ComponentTFMazeRoom {
	public ComponentTFMazeRoomVault() {
		super();
	}

	public ComponentTFMazeRoomVault(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);

		// specify a non-existant high spawn list value to stop actual monster spawns
		this.spawnListIndex = Integer.MAX_VALUE;
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// fill room with bricks
		fillWithBlocks(world, sbb, 0, 1, 0, 15, 4, 15, Blocks.maze_stone, AIR, false);
		fillWithBlocks(world, sbb, 0, 2, 0, 15, 3, 15, Blocks.maze_stone, AIR, false);

		// 4x4 room in the middle
		fillWithAir(world, sbb, 6, 2, 6, 9, 3, 9);

		// pressure plates, sand & tnt
		BlockData pessure_plate = Bukkit.createBlockData(Material.OAK_PRESSURE_PLATE);
		fillWithBlocks(world, sbb, 6, 2, 5, 9, 2, 5, pessure_plate, AIR, false);
		fillWithBlocks(world, sbb, 6, 2, 10, 9, 2, 10, pessure_plate, AIR, false);
		fillWithBlocks(world, sbb, 5, 2, 6, 5, 2, 9, pessure_plate, AIR, false);
		fillWithBlocks(world, sbb, 10, 2, 6, 10, 2, 9, pessure_plate, AIR, false);

		// unfair sand
		BlockData sand = Bukkit.createBlockData(Material.SAND);
		fillWithBlocks(world, sbb, 6, 4, 5, 9, 4, 5, sand, AIR, false);
		fillWithBlocks(world, sbb, 6, 4, 10, 9, 4, 10, sand, AIR, false);
		fillWithBlocks(world, sbb, 5, 4, 6, 5, 4, 9, sand, AIR, false);
		fillWithBlocks(world, sbb, 10, 4, 6, 10, 4, 9, sand, AIR, false);

		BlockData tnt = Bukkit.createBlockData(Material.TNT);
		fillWithBlocks(world, sbb, 6, 0, 5, 9, 0, 5, tnt, AIR, false);
		fillWithBlocks(world, sbb, 6, 0, 10, 9, 0, 10, tnt, AIR, false);
		fillWithBlocks(world, sbb, 5, 0, 6, 5, 0, 9, tnt, AIR, false);
		fillWithBlocks(world, sbb, 10, 0, 6, 10, 0, 9, tnt, AIR, false);

		// LEWTZ!
		this.setBlockState(world, Material.CHEST, 7, 2, 6, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 8, 2, 6, TFTreasure.labyrinth_vault, sbb);
		this.setBlockState(world, Material.CHEST, 8, 2, 9, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 7, 2, 9, TFTreasure.labyrinth_vault, sbb);
		this.setBlockState(world, Material.CHEST, 6, 2, 7, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 6, 2, 8, TFTreasure.labyrinth_vault, sbb);
		this.setBlockState(world, Material.CHEST, 9, 2, 8, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 9, 2, 7, TFTreasure.labyrinth_vault, sbb);

		// mazebreaker!

		return true;
	}
}
