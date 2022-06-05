package otd.lib.async.later.smoofy;

import org.bukkit.Chunk;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.async.later.roguelike.Later;

public class Furnace_Later extends Later {
	private int bx, by, bz;
	private ItemStack result;

	public Furnace_Later(int x, int y, int z, ItemStack result) {
		this.bx = x;
		this.by = y;
		this.bz = z;
		this.result = result;
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
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(c.getWorld().getName());

		boolean builtin = true;
		if (swc != null && !swc.smoofydungeon.builtinLoot)
			builtin = false;

		if (!builtin)
			return;

		int bx = this.bx % 16;
		int by = this.by + this.y;
		int bz = this.bz % 16;

		if (bx < 0)
			bx = bx + 16;
		if (bz < 0)
			bz = bz + 16;

		Furnace furnace = (Furnace) c.getBlock(bx, by, bz).getState();
		furnace.getInventory().setResult(result);
	}

}
