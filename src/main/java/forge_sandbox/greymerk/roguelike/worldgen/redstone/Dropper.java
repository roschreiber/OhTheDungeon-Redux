package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.Dropper_Later;
//import net.minecraft.block.BlockDropper;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityDropper;

public class Dropper {

	static {
		ZoneWorld.registerSpecialBlock(Material.DROPPER);
	}

	public boolean generate(IWorldEditor editor, Cardinal dir, Coord pos) {

		MetaBlock container = new MetaBlock(Material.DROPPER);
		Directional state = (Directional) container.getState();
		state.setFacing(Cardinal.facing(dir));
		container.setState(state);
		container.set(editor, pos);
		return true;
	}

	public void add_later_chunk(Chunk chunk, Coord pos, int slot, ItemStack item) {
		int x = pos.getX() % 16;
		int y = pos.getY();
		int z = pos.getZ() % 16;
		if (x < 0)
			x = x + 16;
		if (z < 0)
			z = z + 16;

//		Bukkit.getLogger().info(pos.toString());
//		Bukkit.getLogger().info("1");
		Block te = chunk.getBlock(x, y, z);
		if (te == null)
			return;
//		Bukkit.getLogger().info("2");
		BlockState state = te.getState();
		if (state instanceof org.bukkit.block.Dropper) {
//			Bukkit.getLogger().info("3");
			org.bukkit.block.Dropper inv = (org.bukkit.block.Dropper) state;
			inv.getInventory().setItem(slot, item);
//			Bukkit.getLogger().info("4");
		}
	}

	public void add_later(IWorldEditor editor, Coord pos, int slot, ItemStack item) {
		Block te = editor.getBlock(pos);
		if (te == null)
			return;
		BlockState state = te.getState();
		if (state instanceof org.bukkit.block.Dropper) {
			org.bukkit.block.Dropper inv = (org.bukkit.block.Dropper) state;
			inv.getInventory().setItem(slot, item);
		}
	}

	public void add(IWorldEditor editor, Coord pos, int slot, ItemStack item) {
		if (!editor.isFakeWorld())
			this.add_later(editor, pos, slot, item);
		else
			editor.addLater(new Dropper_Later(this, editor, pos, slot, item));
	}
}
