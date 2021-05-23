package forge_sandbox.greymerk.roguelike.worldgen.blocks;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.Furnace_Later;
import otd.config.WorldConfig;
//import net.minecraft.block.BlockFurnace;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityFurnace;

public class Furnace {

	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
        
        static {
            ZoneWorld.registerSpecialBlock(Material.FURNACE);
        }
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, null, false, dir, pos);
	}
	
	public static void generate(IWorldEditor editor, boolean lit, Cardinal dir, Coord pos){
		generate(editor, null, lit, dir, pos);
	}
        
        public static void generate_later_chunk(Chunk chunk, ItemStack fuel, Coord pos) {
            int x = pos.getX() % 16;
            int y = pos.getY();
            int z = pos.getZ() % 16;
            if(x < 0) x = x + 16;
            if(z < 0) z = z + 16;  
            
            	Block te = chunk.getBlock(x, y, z);
                
                BlockState bs = te.getState();
                if(!(bs instanceof InventoryHolder)) return;
                InventoryHolder inv = (InventoryHolder) bs;
                inv.getInventory().setItem(FUEL_SLOT, fuel);
        }
        
        public static void generate_later(IWorldEditor editor, ItemStack fuel, Coord pos) {
		Block te = editor.getBlock(pos);
		if(te == null) return;
                
                BlockState bs = te.getState();
                if(!(bs instanceof InventoryHolder)) return;
                InventoryHolder inv = (InventoryHolder) bs;
                inv.getInventory().setItem(FUEL_SLOT, fuel);
        }
	
	public static void generate(IWorldEditor editor, ItemStack fuel, boolean lit, Cardinal dir, Coord pos){

		if(!editor.isFakeWorld() && !WorldConfig.wc.furniture) return;
		
		MetaBlock furnace = new MetaBlock(Material.FURNACE);
		
		if(lit){
                        org.bukkit.block.data.type.Furnace state = (org.bukkit.block.data.type.Furnace) furnace.getState();
                        state.setLit(true);
                        furnace.setState(state);
//			furnace = new MetaBlock(Blocks.LIT_FURNACE);
		}
		
                Directional state = (Directional) furnace.getState();
                state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
                		
		furnace.set(editor, pos);
		
		if(fuel == null) return;
		
		if(!editor.isFakeWorld()) generate_later(editor, fuel, pos);
                else editor.addLater(new Furnace_Later(editor, fuel, pos));
	}
}
