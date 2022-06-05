package forge_sandbox.twilightforest.structures;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;

/**
 * Created by Joseph on 7/16/2017.
 */
public class StructureTFHelper {

	public static final BlockData stoneSlab = getSlab(Material.STONE_SLAB);
	public static final BlockData stoneSlabTop = getSlabTop(Material.STONE_SLAB);
	public static final BlockData stoneSlabDouble;
	static {
		Slab slab = (Slab) Bukkit.createBlockData(Material.STONE_SLAB);
		slab.setType(Slab.Type.DOUBLE);
		stoneSlabDouble = slab;
	}

	public static final BlockData birchSlab = getSlab(Material.BIRCH_SLAB);
	public static final BlockData birchSlabTop = getSlabTop(Material.BIRCH_SLAB);
	public static final BlockData birchPlanks = Bukkit.createBlockData(Material.BIRCH_PLANKS);

	private static BlockData getSlabType(Material type, Slab.Type side) {
		Slab slab = (Slab) Bukkit.createBlockData(type);
		slab.setType(side);
		return slab;
	}

	public static BlockData getSlab(Material type) {
		return getSlabType(type, Slab.Type.BOTTOM);
	}

	public static BlockData getSlabTop(Material type) {
		return getSlabType(type, Slab.Type.TOP);
	}

	public static Material randomPlant(int i) {
		if (i < 4)
			return randomSapling(i);
		else
			return randomMushroom(i - 4);
	}

	public static Material randomSapling(int i) {
		i = i % 6;
		Material[] saplings = { Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING,
				Material.JUNGLE_SAPLING, Material.SPRUCE_SAPLING, Material.OAK_SAPLING, };
		return saplings[i];
	}

	public static Material randomMushroom(int i) {
		if (i == 0)
			return Material.RED_MUSHROOM;
		else
			return Material.BROWN_MUSHROOM;
	}

}
