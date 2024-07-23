/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd;

import org.bukkit.World;
import org.bukkit.block.Biome;
import otd.nms.CompoundParse;
import otd.nms.EquipArmour;
import otd.nms.EquipHands;
import otd.nms.GenerateLaterOrigin;
import otd.nms.GetItem;
import otd.nms.GetNBTTagCompound;
import otd.nms.GetNBTTagList;
import otd.nms.GetPotential;
import otd.nms.GetRoguelike;
import otd.nms.GetSpawnPotentials;
import otd.nms.ListParse;
import otd.nms.PrimitiveParse;
import otd.nms.SpawnerLightRule;
import otd.nms.v1_20_R4.CompoundParse120R4;
import otd.nms.v1_20_R4.EquipArmour120R4;
import otd.nms.v1_20_R4.EquipHands120R4;
import otd.nms.v1_20_R4.GenerateLaterOrigin120R4;
import otd.nms.v1_20_R4.GetItem120R4;
import otd.nms.v1_20_R4.GetNBTTagCompound120R4;
import otd.nms.v1_20_R4.GetNBTTagList120R4;
import otd.nms.v1_20_R4.GetPotential120R4;
import otd.nms.v1_20_R4.GetRoguelike120R4;
import otd.nms.v1_20_R4.GetSpawnPotentials120R4;
import otd.nms.v1_20_R4.ListParse120R4;
import otd.nms.v1_20_R4.PrimitiveParse120R4;
import otd.nms.v1_20_R4.SpawnerLightRule120R4;

/**
 *
 * @author
 */
public class MultiVersion {

	public static enum Version {
		V1_21_R1, UNKNOWN
	};

	public static boolean is121R1() {
		try {
			Class clazz = Class.forName("org.bukkit.craftbukkit.v1_21_R1.CraftWorld");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	private static Boolean newBiome = null;

	public static boolean has3DBiome() {
		if (newBiome != null)
			return newBiome;
		try {
			org.bukkit.World.class.getMethod("getBiome", int.class, int.class, int.class);
			newBiome = true;
		} catch (NoSuchMethodException | SecurityException e) {
			newBiome = false;
		}
		return newBiome;
	}

	private static Boolean newPos = true;

	public static boolean hasExtendedPos() {
		return newPos;
	}

	public static int getBiomeVersion() {
		Biome[] biomes = Biome.values();
		for (Biome biome : biomes) {
			if (biome.toString().toUpperCase().equals("WINDSWEPT_FOREST"))
				return 2;
		}
		return 1;
	}

	public static GetNBTTagCompound getNBTTagCompound = null;
	public static GetNBTTagList getNBTTagList = null;
	public static GetPotential getPotential = null;
	public static EquipHands equipHands = null;
	public static EquipArmour equipArmour = null;
	public static GetItem getItem = null;
	public static GetRoguelike getRoguelike = null;
	public static GenerateLaterOrigin generateLaterOrigin = null;
	public static GetSpawnPotentials getSpawnPotentials = null;
	//public static Get get = null;
	public static CompoundParse compoundParse = null;
	public static ListParse listParse = null;
	public static PrimitiveParse primitiveParse = null;
	public static SpawnerLightRule spawnerLightRule = null;

	private static BiomeHelper biomeHelper = null;

	public static void init() {
		biomeHelper = new Biome3D();
		getNBTTagCompound = new GetNBTTagCompound120R4();
		getNBTTagList = new GetNBTTagList120R4();
		getPotential = new GetPotential120R4();
		equipHands = new EquipHands120R4();
		equipArmour = new EquipArmour120R4();
		getItem = new GetItem120R4();
		getRoguelike = new GetRoguelike120R4();
		generateLaterOrigin = new GenerateLaterOrigin120R4();
		getSpawnPotentials = new GetSpawnPotentials120R4();
		//get = new Get120R4();
		compoundParse = new CompoundParse120R4();
		listParse = new ListParse120R4();
		primitiveParse = new PrimitiveParse120R4();
		spawnerLightRule = new SpawnerLightRule120R4();
	}

	private static interface BiomeHelper {
		public Biome getBiome(World w, int x, int z);
	}

	private static class Biome3D implements BiomeHelper {
		public Biome getBiome(World w, int x, int z) {
			int y = w.getHighestBlockYAt(x, z);
			y--;
			y = y <= 0 ? 0 : y;
			return w.getBiome(x, y, z);
		}
	}

	public static Biome getBiome(World world, int x, int z) {
		return biomeHelper.getBiome(world, x, z);
	}

	public static int[] getWorldYRange() {
		return new int[] { -64, 320 };
	}

	public static boolean spawnerNeedLightUpdate() {
		return true;
	}

}
