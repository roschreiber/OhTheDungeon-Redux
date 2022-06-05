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
package otd.dungeon.draylar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import otd.Main;
import otd.api.event.ChestEvent;
import otd.api.event.DungeonGeneratedEvent;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class BattleTowerSchematic {
	private static class BlockNode {
		public int[] pos;
		public int state;
	}

	private static class PaletteNode {
		public Map<String, String> Properties = new HashMap<>();
		public String Name;
	}

	private static class Json {
		public int[] size;
		public BlockNode[] blocks;
		public PaletteNode[] palette;
		public BlockData[] bd;
		public String[] blockData;
		@SuppressWarnings("unused")
		public int DataVersion;
	}

	private Json json;
	boolean is_top;
	boolean is_entrance;

	public int getYIncrement() {
		return json.size[1];
	}

	private void load(InputStream stream) throws IOException {
		InputStreamReader isr = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(isr);
		String line;

		StringBuilder stringBuilder = new StringBuilder();

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}
		reader.close();
		isr.close();

		json = (new Gson()).fromJson(stringBuilder.toString(), Json.class);
	}

	public BattleTowerSchematic(JavaPlugin plugin, String filename, int offset_x, int offset_z) {
		this(plugin, filename, false, false, offset_x, offset_z);
	}

	public BattleTowerSchematic(JavaPlugin plugin, String filename, boolean is_top, boolean is_entrance, int offset_x,
			int offset_z) {
		this.is_top = is_top;
		this.is_entrance = is_entrance;
		InputStream stream = plugin.getResource(filename);
		Bukkit.getLogger().log(Level.INFO, "Reading {0}", filename);
		try {
			load(stream);
		} catch (IOException e) {
			json = null;
		}

		if (json != null) {
			handleJson();
			finalBuild();
			this.offset_x_C = offset_x;
			this.offset_z_C = offset_z;
//            Bukkit.getLogger().log(Level.INFO, "Offset is [{0}, 0,{1}]", new Object[]{offset_x_C, offset_z_C});
			clean();
		}
	}

	public BattleTowerSchematic(InputStream stream, boolean is_top) {
		this.is_top = is_top;
		try {
			load(stream);
		} catch (IOException e) {
			json = null;
		}

		if (json != null) {
			handleJson();
			finalBuild();
			clean();
		}
	}

	public boolean isValid() {
		return json != null;
	}

	private void handleJson() {
		json.blockData = new String[json.palette.length];
		for (int i = 0; i < json.palette.length; i++) {
			PaletteNode pn = json.palette[i];
			json.blockData[i] = pn.Name;
			if (pn.Properties != null && pn.Properties.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, String> entry : pn.Properties.entrySet()) {
					sb.append(",");
					sb.append(entry.getKey()).append("=").append(entry.getValue());
				}
				sb.deleteCharAt(0);
				json.blockData[i] = pn.Name + "[" + sb.toString() + "]";
			}
		}
	}

	int offset_x_C = 8;
	int offset_z_C = 8;

	private void finalBuild() {
		json.bd = new BlockData[json.blockData.length];
		for (int i = 0; i < json.blockData.length; i++) {
			if (!json.blockData[i].split(":")[0].equals("minecraft"))
				json.bd[i] = Bukkit.createBlockData(Material.AIR);
			else if (json.blockData[i].contains("jigsaw")) {
				json.bd[i] = Bukkit.createBlockData(Material.AIR);
				for (int x = 0; x < json.blocks.length; x++) {
					if (json.blocks[x].state == i) {
						if (json.blocks[x].pos[1] == 0) {
							offset_x_C = json.blocks[x].pos[0];
							offset_z_C = json.blocks[x].pos[2];
						}
					}
				}
			} else {
				try {
					json.bd[i] = Bukkit.createBlockData(json.blockData[i]);
				} catch (IllegalArgumentException ex) {
					json.bd[i] = Bukkit.createBlockData(Material.AIR);
				}
			}
			Material type = json.bd[i].getMaterial();
			if (type == Material.BARRIER || type == Material.STRUCTURE_BLOCK || type == Material.JIGSAW) {
				json.bd[i] = Bukkit.createBlockData(Material.AIR);
			}
		}
	}

	private void clean() {
		json.blockData = null;
		json.palette = null;
	}

	private final static EntityType SPAWNER_MOB_LIST[] = { EntityType.CAVE_SPIDER, EntityType.SPIDER,
			EntityType.ENDERMITE, EntityType.VEX, EntityType.STRAY, EntityType.HUSK, EntityType.PILLAGER, };

	public boolean place(World world, int x, int y, int z, Random rand) {
		int ox = x, oz = z;
		x -= offset_x_C;
		z -= offset_z_C;
		for (BlockNode node : json.blocks) {
			int xx = x + node.pos[0];
			int yy = y + node.pos[1];
			int zz = z + node.pos[2];

			if (node.pos[0] == 15 && node.pos[1] == 0 && node.pos[2] == 8) {
				world.getBlockAt(xx, yy, zz).setType(Material.AIR);
			} else if (node.pos[0] == offset_x_C && node.pos[1] == 0 && node.pos[2] == offset_z_C) {
				world.getBlockAt(xx, yy, zz).setType(Material.BARREL);
				doLoot(world, rand, xx, yy, zz);
			} else if (node.pos[0] == offset_x_C && node.pos[1] == 6 && node.pos[2] == offset_z_C) {
				if (!this.is_entrance && !this.is_top) {
					world.getBlockAt(xx, yy, zz).setType(Material.SPAWNER);
					doSpawner(world, rand, xx, yy, zz);
				}
			} else {
				Block block = world.getBlockAt(xx, yy, zz);
				if (node.pos[1] == 0 && block.getType() == Material.BARRIER) {
					block.setType(Material.AIR);
				} else {
					block.setBlockData(json.bd[node.state], false);
				}
			}
		}

		Set<int[]> chunks0 = new HashSet<>();
		int cx = ox / 16, cz = oz / 16;
		chunks0.add(new int[] { cx - 1, cz - 1 });
		chunks0.add(new int[] { cx - 1, cz });
		chunks0.add(new int[] { cx - 1, cz + 1 });
		chunks0.add(new int[] { cx, cz - 1 });
		chunks0.add(new int[] { cx, cz });
		chunks0.add(new int[] { cx, cz + 1 });
		chunks0.add(new int[] { cx + 1, cz - 1 });
		chunks0.add(new int[] { cx + 1, cz });
		chunks0.add(new int[] { cx + 1, cz + 1 });

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			DungeonGeneratedEvent event = new DungeonGeneratedEvent(world, chunks0, DungeonType.Draylar, ox, y, oz);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}, 1L);
//        if(!this.is_top) placeLadder(world, rand, x + offset_x_C, y, z + offset_z_C);
		return true;
	}

	private final static BlockData LADDER_WEST = Bukkit.createBlockData("minecraft:ladder[facing=west]");
	private final static BlockData LADDER_EAST = Bukkit.createBlockData("minecraft:ladder[facing=east]");
	private final static BlockData LADDER_NORTH = Bukkit.createBlockData("minecraft:ladder[facing=north]");
	private final static BlockData LADDER_SOUTH = Bukkit.createBlockData("minecraft:ladder[facing=south]");

	@SuppressWarnings("unused")
	private void placeLadder(World world, Random rand, int x, int y, int z) {
		int dir = 0;// rand.nextInt(4);
		if (this.is_entrance)
			dir = 0;
		int max_y = json.size[2] - 1 + y;
		switch (dir) {
		case 0: {
			int offset_x = offset_x_C - 1; // 7;
			int offset_y = 2;
			int offset_z = 0;
			for (y = y + offset_y; y <= max_y; y++) {
				world.getBlockAt(x + offset_x, y, z + offset_z).setBlockData(LADDER_WEST, false);

				Block relative = world.getBlockAt(x + offset_x + 1, y, z + offset_z);
				if (relative.getType() == Material.AIR)
					relative.setType(Material.STONE, false);
			}
			world.getBlockAt(x + offset_x, y, z + offset_z).setType(Material.BARRIER, false);
			break;
		}
		case 1: {
			int offset_x = -7;
			int offset_y = 2;
			int offset_z = 0;
			for (y = y + offset_y; y <= max_y; y++) {
				world.getBlockAt(x + offset_x, y, z + offset_z).setBlockData(LADDER_EAST, false);

				Block relative = world.getBlockAt(x + offset_x - 1, y, z + offset_z);
				if (relative.getType() == Material.AIR)
					relative.setType(Material.STONE, false);

			}
			world.getBlockAt(x + offset_x, y, z + offset_z).setType(Material.BARRIER, false);
			break;
		}
		case 2: {
			int offset_x = 0;
			int offset_y = 2;
			int offset_z = 7;
			for (y = y + offset_y; y <= max_y; y++) {
				world.getBlockAt(x + offset_x, y, z + offset_z).setBlockData(LADDER_NORTH, false);
			}
			world.getBlockAt(x + offset_x, y, z + offset_z).setType(Material.BARRIER, false);
			break;
		}
		default: {
			int offset_x = 0;
			int offset_y = 2;
			int offset_z = -7;
			for (y = y + offset_y; y <= max_y; y++) {
				world.getBlockAt(x + offset_x, y, z + offset_z).setBlockData(LADDER_SOUTH, false);
			}
			world.getBlockAt(x + offset_x, y, z + offset_z).setType(Material.BARRIER, false);
			break;
		}
		}
	}

	private void doLoot(World world, Random rand, int x, int y, int z) {
		Block block = world.getBlockAt(x, y, z);
		BlockState state = block.getState();
		if (state instanceof Barrel) {
			Inventory inv = ((Barrel) state).getInventory();
			int count = LootTable.getRandomLootCount(rand);
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			if (swc != null && !swc.draylar_battletower.builtinLoot)
				count = 0;
			for (int i = 0; i < count; i++) {
				ItemStack item = LootTable.getRandomLootItem(rand);
				int index = rand.nextInt(inv.getSize());
				inv.setItem(index, item);
			}

			if (swc != null && swc.draylar_battletower.loots.size() > 0) {
				for (LootNode ln : swc.draylar_battletower.loots) {
					if (rand.nextDouble() < ln.chance) {
						ItemStack is = ln.getItem();
						int amount = ln.min + rand.nextInt(ln.max - ln.min + 1);
						is.setAmount(amount);
						inv.addItem(is);
					}
				}
			}

			ChestEvent event = new ChestEvent(DungeonType.Draylar, "", block.getLocation());
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}

	private void doSpawner(World world, Random rand, int x, int y, int z) {
		Block block = world.getBlockAt(x, y, z);
		if (!(block.getState() instanceof CreatureSpawner))
			return;
		CreatureSpawner tileentitymobspawner = ((CreatureSpawner) block.getState());
		tileentitymobspawner.setSpawnedType(SPAWNER_MOB_LIST[rand.nextInt(SPAWNER_MOB_LIST.length)]);
		tileentitymobspawner.update();
		SpawnerDecryAPI.setSpawnerDecry(block, Main.instance, DungeonType.Draylar, true);
	}

	public List<int[]> getLayer(int y) {
		List<int[]> result = new ArrayList<>();
		for (BlockNode node : json.blocks) {
			if (json.bd[node.state].getMaterial() == Material.AIR)
				continue;
			if (node.pos[1] == y)
				result.add(new int[] { node.pos[0], node.pos[1], node.pos[2] });
		}
		return result;
	}
}
