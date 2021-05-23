package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.Dispenser_Later;
//import net.minecraft.block.BlockDispenser;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityDispenser;

public class Dispenser {
    
    static {
        ZoneWorld.registerSpecialBlock(Material.DISPENSER);
    }
    
	private static void setDirection(MetaBlock block, BlockFace dir) {
            Directional state = (Directional) block.getState();
            state.setFacing(dir);
            block.setState(state);
        }
        
	public static boolean generate(IWorldEditor editor, Cardinal dir, Coord pos){

		MetaBlock container = new MetaBlock(Material.DISPENSER);
		setDirection(container, Cardinal.facing(dir));
		container.set(editor, pos);
		return true;
	}
        
        public static void add_later_chunk(Chunk chunk, Coord pos, int slot, ItemStack item) {
            
            int x = pos.getX() % 16;
            int y = pos.getY();
            int z = pos.getZ() % 16;
            if(x < 0) x = x + 16;
            if(z < 0) z = z + 16;  
            
		Block te = chunk.getBlock(x, y, z);
		if(te == null) return;
                BlockState state = te.getState();
                if(state instanceof InventoryHolder) {
                    InventoryHolder inv = (InventoryHolder) state;
                    inv.getInventory().setItem(slot, item);
                }

        }
        
        public static void add_later(IWorldEditor editor, Coord pos, int slot, ItemStack item) {
		Block te = editor.getBlock(pos);
		if(te == null) return;
                BlockState state = te.getState();
                if(state instanceof InventoryHolder) {
                    InventoryHolder inv = (InventoryHolder) state;
                    inv.getInventory().setItem(slot, item);
                }

        }
	
	public static void add(IWorldEditor editor, Coord pos, int slot, ItemStack item){
            if(!editor.isFakeWorld()) add_later(editor, pos, slot, item);
            else editor.addLater(new Dispenser_Later(editor, pos, slot, item));
	}
}
