package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.BrewingStand_Later;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityBrewingStand;

public enum BrewingStand {

	LEFT(0), MIDDLE(1), RIGHT(2), INGREDIENT(3), FUEL(4);

	static {
		ZoneWorld.registerSpecialBlock(Material.BREWING_STAND);
	}

	private int id;

	BrewingStand(int id) {
		this.id = id;
	}

	public static boolean generate(IWorldEditor editor, Coord pos) {
		MetaBlock stand = new MetaBlock(Material.BREWING_STAND);
		return stand.set(editor, pos);
	}

	public static boolean add_later_chunk(Chunk chunk, Coord pos, BrewingStand slot, ItemStack item) {

		int x = pos.getX() % 16;
		int y = pos.getY();
		int z = pos.getZ() % 16;
		if (x < 0)
			x = x + 16;
		if (z < 0)
			z = z + 16;

		Block block = chunk.getBlock(x, y, z);
		if (block.getType() == Material.BREWING_STAND) {
			org.bukkit.block.BrewingStand holder = (org.bukkit.block.BrewingStand) block.getState();
			holder.getInventory().setItem(slot.id, item);
		}
		return false;
	}

	public static boolean add_later(IWorldEditor editor, Coord pos, BrewingStand slot, ItemStack item) {
		Block block = editor.getBlock(pos);
		if (block.getType() == Material.BREWING_STAND) {
			org.bukkit.block.BrewingStand holder = (org.bukkit.block.BrewingStand) block.getState();
			holder.getInventory().setItem(slot.id, item);
		}
		return false;
//		TileEntityBrewingStand stand = get(editor, pos);
//		if(stand == null) return false;
//		stand.setInventorySlotContents(slot.id, item);
//		return true;

	}

	public static boolean add(IWorldEditor editor, Coord pos, BrewingStand slot, ItemStack item) {
		if (!editor.isFakeWorld())
			add_later(editor, pos, slot, item);
		else
			editor.addLater(new BrewingStand_Later(editor, pos, slot, item));

		return false;
	}
}
