package forge_sandbox.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.util.IWeighted;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class TreasureManager {

	public List<ITreasureChest> chests;

	public TreasureManager() {
		this.chests = new ArrayList<>();
	}

	public void add(ITreasureChest toAdd) {
		this.chests.add(toAdd);
	}

	public void addItemToAll(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount) {
		addItemToAll(rand, this.getChests(type, level), item, amount);
	}

	public void addItemToAll(Random rand, int level, IWeighted<ItemStack> item, int amount) {
		addItemToAll(rand, this.getChests(level), item, amount);
	}

	public void addItemToAll(Random rand, Treasure type, IWeighted<ItemStack> item, int amount) {
		addItemToAll(rand, this.getChests(type), item, amount);
	}

	private void addItemToAll(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount) {
		for (ITreasureChest chest : chests) {
			for (int i = 0; i < amount; ++i) {
				chest.setRandomEmptySlot(item.get(rand));
			}
		}
	}

	public List<Location> getAllChestPos(IWorldEditor editor) {
		World world = editor.getWorld();
		List<Location> res = new ArrayList<>();
		for (ITreasureChest chest : chests) {
			Coord pos = chest.getPos();
			Location loc = new Location(world, pos.getX(), pos.getY(), pos.getZ());
			res.add(loc);
		}
		return res;
	}

	public void addItem(Random rand, int level, IWeighted<ItemStack> item, int amount) {
		this.addItem(rand, getChests(level), item, amount);
	}

	public void addItem(Random rand, Treasure type, IWeighted<ItemStack> item, int amount) {
		this.addItem(rand, getChests(type), item, amount);
	}

	public void addItem(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount) {
		this.addItem(rand, getChests(type, level), item, amount);
	}

	private void addItem(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount) {
		if (chests.isEmpty())
			return;

		for (int i = 0; i < amount; ++i) {
			ITreasureChest chest = chests.get(rand.nextInt(chests.size()));
			chest.setRandomEmptySlot(item.get(rand));
		}
	}

	public List<ITreasureChest> getChests(Treasure type, int level) {
		ArrayList<ITreasureChest> c = new ArrayList<>();
		for (ITreasureChest chest : this.chests) {
			if (chest.getType() == type && chest.getLevel() == level)
				c.add(chest);
		}
		return c;
	}

	public List<ITreasureChest> getChests(Treasure type) {
		ArrayList<ITreasureChest> c = new ArrayList<>();
		for (ITreasureChest chest : this.chests) {
			if (chest.getType() == type)
				c.add(chest);
		}
		return c;
	}

	public List<ITreasureChest> getChests(int level) {
		ArrayList<ITreasureChest> c = new ArrayList<>();
		for (ITreasureChest chest : this.chests) {
			if (chest.getType() == Treasure.EMPTY)
				continue;
			if (chest.getLevel() == level)
				c.add(chest);
		}
		return c;
	}

	public List<ITreasureChest> getChests() {
		ArrayList<ITreasureChest> c = new ArrayList<>();
		for (ITreasureChest chest : this.chests) {
			if (chest.getType() != Treasure.EMPTY)
				c.add(chest);
		}
		return c;
	}
}
