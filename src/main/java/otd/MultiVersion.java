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
import org.bukkit.entity.EntityType;

import otd.nms.CompoundParse;
import otd.nms.EquipArmour;
import otd.nms.EquipHands;
import otd.nms.GenerateLaterOrigin;
import otd.nms.Get;
import otd.nms.GetItem;
import otd.nms.GetNBTTagCompound;
import otd.nms.GetNBTTagList;
import otd.nms.GetPotential;
import otd.nms.GetRoguelike;
import otd.nms.GetSpawnPotentials;
import otd.nms.ListParse;
import otd.nms.PrimitiveParse;
import otd.nms.SpawnerLightRule;
import otd.nms.v1_14_R1.CompoundParse114R1;
import otd.nms.v1_14_R1.EquipArmour114R1;
import otd.nms.v1_14_R1.EquipHands114R1;
import otd.nms.v1_14_R1.GenerateLaterOrigin114R1;
import otd.nms.v1_14_R1.Get114R1;
import otd.nms.v1_14_R1.GetItem114R1;
import otd.nms.v1_14_R1.GetNBTTagCompound114R1;
import otd.nms.v1_14_R1.GetNBTTagList114R1;
import otd.nms.v1_14_R1.GetPotential114R1;
import otd.nms.v1_14_R1.GetRoguelike114R1;
import otd.nms.v1_14_R1.GetSpawnPotentials114R1;
import otd.nms.v1_14_R1.ListParse114R1;
import otd.nms.v1_14_R1.PrimitiveParse114R1;
import otd.nms.v1_15_R1.CompoundParse115R1;
import otd.nms.v1_15_R1.EquipArmour115R1;
import otd.nms.v1_15_R1.EquipHands115R1;
import otd.nms.v1_15_R1.GenerateLaterOrigin115R1;
import otd.nms.v1_15_R1.Get115R1;
import otd.nms.v1_15_R1.GetItem115R1;
import otd.nms.v1_15_R1.GetNBTTagCompound115R1;
import otd.nms.v1_15_R1.GetNBTTagList115R1;
import otd.nms.v1_15_R1.GetPotential115R1;
import otd.nms.v1_15_R1.GetRoguelike115R1;
import otd.nms.v1_15_R1.GetSpawnPotentials115R1;
import otd.nms.v1_15_R1.ListParse115R1;
import otd.nms.v1_15_R1.PrimitiveParse115R1;
import otd.nms.v1_16_R3.CompoundParse116R3;
import otd.nms.v1_16_R3.EquipArmour116R3;
import otd.nms.v1_16_R3.EquipHands116R3;
import otd.nms.v1_16_R3.GenerateLaterOrigin116R3;
import otd.nms.v1_16_R3.Get116R3;
import otd.nms.v1_16_R3.GetItem116R3;
import otd.nms.v1_16_R3.GetNBTTagCompound116R3;
import otd.nms.v1_16_R3.GetNBTTagList116R3;
import otd.nms.v1_16_R3.GetPotential116R3;
import otd.nms.v1_16_R3.GetRoguelike116R3;
import otd.nms.v1_16_R3.GetSpawnPotentials116R3;
import otd.nms.v1_16_R3.ListParse116R3;
import otd.nms.v1_16_R3.PrimitiveParse116R3;
import otd.nms.v1_17_R1.CompoundParse117R1;
import otd.nms.v1_17_R1.EquipArmour117R1;
import otd.nms.v1_17_R1.EquipHands117R1;
import otd.nms.v1_17_R1.GenerateLaterOrigin117R1;
import otd.nms.v1_17_R1.Get117R1;
import otd.nms.v1_17_R1.GetItem117R1;
import otd.nms.v1_17_R1.GetNBTTagCompound117R1;
import otd.nms.v1_17_R1.GetNBTTagList117R1;
import otd.nms.v1_17_R1.GetPotential117R1;
import otd.nms.v1_17_R1.GetRoguelike117R1;
import otd.nms.v1_17_R1.GetSpawnPotentials117R1;
import otd.nms.v1_17_R1.ListParse117R1;
import otd.nms.v1_17_R1.PrimitiveParse117R1;
import otd.nms.v1_18_R2.CompoundParse118R2;
import otd.nms.v1_18_R2.EquipArmour118R2;
import otd.nms.v1_18_R2.EquipHands118R2;
import otd.nms.v1_18_R2.GenerateLaterOrigin118R2;
import otd.nms.v1_18_R2.Get118R2;
import otd.nms.v1_18_R2.GetItem118R2;
import otd.nms.v1_18_R2.GetNBTTagCompound118R2;
import otd.nms.v1_18_R2.GetNBTTagList118R2;
import otd.nms.v1_18_R2.GetPotential118R2;
import otd.nms.v1_18_R2.GetRoguelike118R2;
import otd.nms.v1_18_R2.GetSpawnPotentials118R2;
import otd.nms.v1_18_R2.ListParse118R2;
import otd.nms.v1_18_R2.PrimitiveParse118R2;
import otd.nms.v1_18_R2.SpawnerLightRule118R2;
import otd.nms.v1_19_R1.CompoundParse119R1;
import otd.nms.v1_19_R1.EquipArmour119R1;
import otd.nms.v1_19_R1.EquipHands119R1;
import otd.nms.v1_19_R1.GenerateLaterOrigin119R1;
import otd.nms.v1_19_R1.Get119R1;
import otd.nms.v1_19_R1.GetItem119R1;
import otd.nms.v1_19_R1.GetNBTTagCompound119R1;
import otd.nms.v1_19_R1.GetNBTTagList119R1;
import otd.nms.v1_19_R1.GetPotential119R1;
import otd.nms.v1_19_R1.GetRoguelike119R1;
import otd.nms.v1_19_R1.GetSpawnPotentials119R1;
import otd.nms.v1_19_R1.ListParse119R1;
import otd.nms.v1_19_R1.PrimitiveParse119R1;
import otd.nms.v1_19_R1.SpawnerLightRule119R1;

/**
 *
 * @author
 */
public class MultiVersion {

	public static enum Version {
		V1_19_R1, V1_18_R2, V1_17_R1, V1_16_R3, V1_15_R1, V1_14_R1, UNKNOWN,
	};

	private static EntityType PIGZOMBIE = null;

	public static EntityType getPigZombie() {
		if (PIGZOMBIE == null) {
			try {
				PIGZOMBIE = EntityType.valueOf("ZOMBIFIED_PIGLIN");
			} catch (IllegalArgumentException ex) {
				PIGZOMBIE = EntityType.valueOf("PIG_ZOMBIE");
			}
		}
		return PIGZOMBIE;
	}

	public static boolean is119R1() {
		try {
			Class clazz = Class.forName("org.bukkit.craftbukkit.v1_19_R1.CraftWorld");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	public static boolean is118R2() {
		try {
			Class clazz = Class.forName("org.bukkit.craftbukkit.v1_18_R2.CraftWorld");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	public static boolean is117R1() {
		try {
			Class clazz = Class.forName("org.bukkit.craftbukkit.v1_17_R1.CraftWorld");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	public static boolean is116R3() {
		try {
			Class clazz = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	public static boolean is115() {
		try {
			Class clazz = Class.forName("net.minecraft.server.v1_15_R1.NBTTagCompound");
			return clazz != null;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

	public static boolean is114() {
		try {
			Class clazz = Class.forName("net.minecraft.server.v1_14_R1.NBTTagCompound");
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

	private static Boolean newPos = null;

	public static boolean hasExtendedPos() {
		if (newPos != null)
			return newPos;
		if (Main.version == Version.V1_18_R2 || Main.version == Version.V1_19_R1)
			newPos = true;
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
	public static Get get = null;
	public static CompoundParse compoundParse = null;
	public static ListParse listParse = null;
	public static PrimitiveParse primitiveParse = null;
	public static SpawnerLightRule spawnerLightRule = null;

	private static BiomeHelper biomeHelper = null;

	public static void init() {
		if (MultiVersion.has3DBiome()) {
			biomeHelper = new Biome3D();
		} else {
			biomeHelper = new Biome2D();
		}
		if (Main.version == Version.V1_14_R1) {
			getNBTTagCompound = new GetNBTTagCompound114R1();
			getNBTTagList = new GetNBTTagList114R1();
			getPotential = new GetPotential114R1();
			equipHands = new EquipHands114R1();
			equipArmour = new EquipArmour114R1();
			getItem = new GetItem114R1();
			getRoguelike = new GetRoguelike114R1();
			generateLaterOrigin = new GenerateLaterOrigin114R1();
			getSpawnPotentials = new GetSpawnPotentials114R1();
			get = new Get114R1();
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
			get = new Get115R1();
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
			get = new Get116R3();
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
			get = new Get117R1();
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
			get = new Get118R2();
			compoundParse = new CompoundParse118R2();
			listParse = new ListParse118R2();
			primitiveParse = new PrimitiveParse118R2();
			spawnerLightRule = new SpawnerLightRule118R2();

		} else if (Main.version == Version.V1_19_R1) {
			getNBTTagCompound = new GetNBTTagCompound119R1();
			getNBTTagList = new GetNBTTagList119R1();
			getPotential = new GetPotential119R1();
			equipHands = new EquipHands119R1();
			equipArmour = new EquipArmour119R1();
			getItem = new GetItem119R1();
			getRoguelike = new GetRoguelike119R1();
			generateLaterOrigin = new GenerateLaterOrigin119R1();
			getSpawnPotentials = new GetSpawnPotentials119R1();
			get = new Get119R1();
			compoundParse = new CompoundParse119R1();
			listParse = new ListParse119R1();
			primitiveParse = new PrimitiveParse119R1();
			spawnerLightRule = new SpawnerLightRule119R1();
		}
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

	private static class Biome2D implements BiomeHelper {
		@SuppressWarnings("deprecation")
		public Biome getBiome(World w, int x, int z) {
			return w.getBiome(x, z);
		}
	}

	public static Biome getBiome(World world, int x, int z) {
		return biomeHelper.getBiome(world, x, z);
	}

	public static int[] getWorldYRange() {
		if (Main.version == Version.V1_18_R2 || Main.version == Version.V1_19_R1) {
			return new int[] { -64, 320 };
		} else {
			return new int[] { 0, 256 };
		}
	}

	public static boolean spawnerNeedLightUpdate() {
		if (Main.version == Version.V1_18_R2)
			return true;
		if (Main.version == Version.V1_19_R1)
			return true;
		return false;
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
