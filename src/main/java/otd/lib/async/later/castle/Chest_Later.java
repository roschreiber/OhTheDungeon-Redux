package otd.lib.async.later.castle;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.BlockPos;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.api.event.ChestEvent;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.util.OTDLoottables;
import otd.world.DungeonType;

public class Chest_Later extends Later {

	public int x, y, z;
	public BlockFace face;
	public List<LootNode> loots;
	public OTDLoottables lootTable;
	public String world;
	public Random random;

	private Chest_Later() {

	}

	public static Chest_Later getChest(AsyncWorldEditor world, BlockPos pos, BlockFace face, OTDLoottables lootTable,
			Random random) {
		Chest_Later later = new Chest_Later();
		later.x = pos.getX();
		later.y = pos.getY();
		later.z = pos.getZ();
		later.face = face;
		later.lootTable = lootTable;
		later.loots = OTDLoottables.getLoots(lootTable, random);
		later.world = world.getWorldName();
		later.random = random;

		return later;
	}

	@Override
	public Coord getPos() {
		return new Coord(x, y, z);
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
//		Bukkit.getLogger().info("" + x + "," + y + "," + z);
		int bx = x % 16;
		int by = y;
		int bz = z % 16;
		if (bx < 0)
			bx = bx + 16;
		if (bz < 0)
			bz = bz + 16;

		Block block = c.getBlock(bx, by, bz);
		{
			BlockData bd = Bukkit.createBlockData(Material.CHEST);
			Directional dir = (Directional) bd;
			dir.setFacing(face);
			block.setBlockData(dir, true);
		}

		boolean builtin = true;
		if (WorldConfig.wc.dict.containsKey(world) && !WorldConfig.wc.dict.get(world).castle.builtinLoot) {
			builtin = false;
		}

		if (!builtin) {
			loots.clear();
		}

		if (WorldConfig.wc.dict.containsKey(world)) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world);
			for (LootNode node : swc.castle.loots) {
				loots.add(node);
			}
		}

		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		for (LootNode ln : loots) {
			if (random.nextDouble() <= ln.chance) {
				ItemStack item = ln.getItem();
				int amount;
				if (ln.max == ln.min)
					amount = ln.max;
				else
					amount = ln.min + random.nextInt(ln.max - ln.min + 1);
				item.setAmount(amount);
				inv.setItem(random.nextInt(inv.getSize()), item);
			}
		}

		Location loc = new Location(c.getWorld(), x, y, z);
		ChestEvent event = new ChestEvent(DungeonType.Castle, "", loc);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
}
