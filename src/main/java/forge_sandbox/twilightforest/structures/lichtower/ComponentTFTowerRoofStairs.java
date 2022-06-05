package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFTowerRoofStairs extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofStairs() {
		super();
	}

	public ComponentTFTowerRoofStairs(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);

		// always facing = 0. This roof cannot rotate, due to stair facing issues.
		this.setCoordBaseMode(BlockFace.SOUTH);

		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// just hang out at the very top of the tower
		makeCapBB(wing);

	}

	/**
	 * Makes a pyramid-shaped roof out of stairs
	 */
	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		BlockData birchSlab = Bukkit.createBlockData(Material.BIRCH_SLAB);
		{
			Slab slab = (Slab) birchSlab;
			slab.setType(Slab.Type.BOTTOM);
			birchSlab = slab;
		}
		BlockData birchPlanks = Bukkit.createBlockData(Material.BIRCH_PLANKS);

		BlockData birchStairsNorth = Bukkit.createBlockData(Material.BIRCH_STAIRS);
		{
			Directional dir = (Directional) birchStairsNorth;
			dir.setFacing(BlockFace.NORTH);
			birchStairsNorth = dir;
		}
		BlockData birchStairsSouth = Bukkit.createBlockData(Material.BIRCH_STAIRS);
		{
			Directional dir = (Directional) birchStairsNorth;
			dir.setFacing(BlockFace.SOUTH);
			birchStairsNorth = dir;
		}
		BlockData birchStairsEast = Bukkit.createBlockData(Material.BIRCH_STAIRS);
		{
			Directional dir = (Directional) birchStairsNorth;
			dir.setFacing(BlockFace.EAST);
			birchStairsNorth = dir;
		}
		BlockData birchStairsWest = Bukkit.createBlockData(Material.BIRCH_STAIRS);
		{
			Directional dir = (Directional) birchStairsNorth;
			dir.setFacing(BlockFace.WEST);
			birchStairsNorth = dir;
		}

		for (int y = 0; y <= height; y++) {
			int min = y;
			int max = size - y - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min) {
						if (z == min || z == max) {
							setBlockState(world, birchSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsWest, x, y, z, sbb);
						}
					} else if (x == max) {
						if (z == min || z == max) {
							setBlockState(world, birchSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsEast, x, y, z, sbb);
						}
					} else if (z == max) {
						setBlockState(world, birchStairsSouth, x, y, z, sbb);
					} else if (z == min) {
						setBlockState(world, birchStairsNorth, x, y, z, sbb);
					} else {
						setBlockState(world, birchPlanks, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}

}
