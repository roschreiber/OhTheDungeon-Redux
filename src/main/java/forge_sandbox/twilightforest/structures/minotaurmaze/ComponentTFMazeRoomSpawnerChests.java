package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFTreasure;

public class ComponentTFMazeRoomSpawnerChests extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomSpawnerChests() {
		super();
	}

	public ComponentTFMazeRoomSpawnerChests(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

		// 4 pillar enclosures
		placePillarEnclosure(world, sbb, 3, 3);
		placePillarEnclosure(world, sbb, 10, 3);
		placePillarEnclosure(world, sbb, 3, 10);
		placePillarEnclosure(world, sbb, 10, 10);

		// spawner
		setSpawner(world, 4, 2, 4, sbb, EntityType.ZOMBIE);

		// treasure
		this.placeTreasureAtCurrentPosition(world, rand, 4, 2, 11, TFTreasure.labyrinth_room, sbb);

		// treasure
		this.placeTreasureAtCurrentPosition(world, rand, 11, 2, 4, TFTreasure.labyrinth_room, sbb);

		// trap
		setBlockState(world, Material.OAK_PRESSURE_PLATE, 11, 1, 11, sbb);
		setBlockState(world, Material.TNT, 10, 0, 11, sbb);
		setBlockState(world, Material.TNT, 11, 0, 10, sbb);
		setBlockState(world, Material.TNT, 11, 0, 12, sbb);
		setBlockState(world, Material.TNT, 12, 0, 11, sbb);

		return true;
	}

	private void placePillarEnclosure(AsyncWorldEditor world, StructureBoundingBox sbb, int dx, int dz) {
		for (int y = 1; y < 5; y++) {
			final BlockData chiselledMazeBlock = Blocks.chiseled_maze_stone;
			setBlockState(world, chiselledMazeBlock, dx + 0, y, dz + 0, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 2, y, dz + 0, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 0, y, dz + 2, sbb);
			setBlockState(world, chiselledMazeBlock, dx + 2, y, dz + 2, sbb);
		}
		setBlockState(world, Material.OAK_PLANKS, dx + 1, 1, dz + 1, sbb);
		setBlockState(world, Material.OAK_PLANKS, dx + 1, 4, dz + 1, sbb);

		final BlockData defaultState = Bukkit.createBlockData(Material.OAK_STAIRS);

		setBlockState(world, getStairState(defaultState, BlockFace.NORTH, rotation, false), dx + 1, 1, dz + 0, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.WEST, rotation, false), dx + 0, 1, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.EAST, rotation, false), dx + 2, 1, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.SOUTH, rotation, false), dx + 1, 1, dz + 2, sbb);

		setBlockState(world, getStairState(defaultState, BlockFace.NORTH, rotation, true), dx + 1, 4, dz + 0, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.WEST, rotation, true), dx + 0, 4, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.EAST, rotation, true), dx + 2, 4, dz + 1, sbb);
		setBlockState(world, getStairState(defaultState, BlockFace.SOUTH, rotation, true), dx + 1, 4, dz + 2, sbb);

		setBlockState(world, Material.IRON_BARS, dx + 1, 2, dz + 0, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 0, 2, dz + 1, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 2, 2, dz + 1, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 1, 2, dz + 2, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 1, 3, dz + 0, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 0, 3, dz + 1, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 2, 3, dz + 1, sbb);
		setBlockState(world, Material.IRON_BARS, dx + 1, 3, dz + 2, sbb);

	}

}
