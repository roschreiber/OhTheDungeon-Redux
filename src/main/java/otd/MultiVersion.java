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

import org.bukkit.Material;
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

		/*if (Main.version == Version.V1_14_R1) {
			getNBTTagCompound = new GetNBTTagCompound114R1();
			getNBTTagList = new GetNBTTagList114R1();
			getPotential = new GetPotential114R1();
			equipHands = new EquipHands114R1();
			equipArmour = new EquipArmour114R1();
			getItem = new GetItem114R1();
			getRoguelike = new GetRoguelike114R1();
			generateLaterOrigin = new GenerateLaterOrigin114R1();
			getSpawnPotentials = new GetSpawnPotentials114R1();
			//get = new Get114R1();
			compoundParse = new CompoundParse114R1();
			listParse = new ListParse114R1();
			primitiveParse = new PrimitiveParse114R1();

		} else if (Main.version == Version.V1_15_R1) {
			getNBTTagCompound = new GetNBTTagCompound115R1();
			getNBTTagList = new GetNBTTagList115R1();
			getPotential = new GetPotential115R1();
			equipHands = new EquipHands115R1();
			equipArmour = new EquipArmour115R1();
			getItem = new GetItem115R1();
			getRoguelike = new GetRoguelike115R1();
			generateLaterOrigin = new GenerateLaterOrigin115R1();
			getSpawnPotentials = new GetSpawnPotentials115R1();
			//get = new Get115R1();
			compoundParse = new CompoundParse115R1();
			listParse = new ListParse115R1();
			primitiveParse = new PrimitiveParse115R1();

		} else if (Main.version == Version.V1_16_R3) {
			getNBTTagCompound = new GetNBTTagCompound116R3();
			getNBTTagList = new GetNBTTagList116R3();
			getPotential = new GetPotential116R3();
			equipHands = new EquipHands116R3();
			equipArmour = new EquipArmour116R3();
			getItem = new GetItem116R3();
			getRoguelike = new GetRoguelike116R3();
			generateLaterOrigin = new GenerateLaterOrigin116R3();
			getSpawnPotentials = new GetSpawnPotentials116R3();
			//get = new Get116R3();
			compoundParse = new CompoundParse116R3();
			listParse = new ListParse116R3();
			primitiveParse = new PrimitiveParse116R3();

		} else if (Main.version == Version.V1_17_R1) {
			getNBTTagCompound = new GetNBTTagCompound117R1();
			getNBTTagList = new GetNBTTagList117R1();
			getPotential = new GetPotential117R1();
			equipHands = new EquipHands117R1();
			equipArmour = new EquipArmour117R1();
			getItem = new GetItem117R1();
			getRoguelike = new GetRoguelike117R1();
			generateLaterOrigin = new GenerateLaterOrigin117R1();
			getSpawnPotentials = new GetSpawnPotentials117R1();
			//get = new Get117R1();
			compoundParse = new CompoundParse117R1();
			listParse = new ListParse117R1();
			primitiveParse = new PrimitiveParse117R1();

		} else if (Main.version == Version.V1_18_R2) {
			getNBTTagCompound = new GetNBTTagCompound118R2();
			getNBTTagList = new GetNBTTagList118R2();
			getPotential = new GetPotential118R2();
			equipHands = new EquipHands118R2();
			equipArmour = new EquipArmour118R2();
			getItem = new GetItem118R2();
			getRoguelike = new GetRoguelike118R2();
			generateLaterOrigin = new GenerateLaterOrigin118R2();
			getSpawnPotentials = new GetSpawnPotentials118R2();
			//get = new Get118R2();
			compoundParse = new CompoundParse118R2();
			listParse = new ListParse118R2();
			primitiveParse = new PrimitiveParse118R2();
			//spawnerLightRule = new SpawnerLightRule118R2();

		} else if (Main.version == Version.V1_19_R3) {
			getNBTTagCompound = new GetNBTTagCompound119R3();
			getNBTTagList = new GetNBTTagList119R3();
			getPotential = new GetPotential119R3();
			equipHands = new EquipHands119R3();
			equipArmour = new EquipArmour119R3();
			getItem = new GetItem119R3();
			getRoguelike = new GetRoguelike119R3();
			generateLaterOrigin = new GenerateLaterOrigin119R3();
			getSpawnPotentials = new GetSpawnPotentials119R3();
			//get = new Get119R3();
			compoundParse = new CompoundParse119R3();
			listParse = new ListParse119R3();
			primitiveParse = new PrimitiveParse119R3();
			//spawnerLightRule = new SpawnerLightRule119R3();

		} else if (Main.version == Version.V1_20_R4) {*/
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

	private static Boolean hasWaterCauldron = null;

	public static boolean hasWaterCauldron() {
		if (hasWaterCauldron != null)
			return hasWaterCauldron;
		try {
			Material.valueOf("CAULDRON");
			hasWaterCauldron = true;
		} catch (Exception ex) {
			hasWaterCauldron = false;
		}
		return hasWaterCauldron;
	}

}
