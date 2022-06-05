package otd.lib.async.later.smoofy;

import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.dungeon.dungeonmaze.util.ChestUtils;
import otd.lib.async.later.roguelike.Later;

public class Chest_Later extends Later {
	private int bx, by, bz;
	private Random rand;
	private List<ItemStack> items;

	public Chest_Later(int x, int y, int z, Random rand, List<ItemStack> items) {
		this.bx = x;
		this.by = y;
		this.bz = z;
		this.rand = rand;
		this.items = items;
	}

	@Override
	public Coord getPos() {
		return new Coord(bx, by, bz);
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		int bx = this.bx % 16;
		int by = this.by + this.y;
		int bz = this.bz % 16;

		if (bx < 0)
			bx = bx + 16;
		if (bz < 0)
			bz = bz + 16;

		Block chest = c.getBlock(bx, by, bz);
		ChestUtils.addItemsToChest(chest, items, true, rand, c.getWorld());
	}

}
