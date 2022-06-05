package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockTorch;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.EnumFacing;

public enum Torch {

	REDSTONE, WOODEN, REDSTONE_UNLIT;

	private final static BlockData TORCH = Bukkit.createBlockData(Material.TORCH);
	private final static BlockData REDSTONE_TORCH = Bukkit.createBlockData(Material.REDSTONE_TORCH);
	private final static BlockData REDSTONE_TORCH_OFF = Bukkit.createBlockData("minecraft:redstone_torch[lit=false]");

	public static void generate(IWorldEditor editor, Torch type, Cardinal dir, Coord pos) {

		BlockData name;

		switch (type) {
		case WOODEN:
			name = TORCH;
			break;
		case REDSTONE:
			name = REDSTONE_TORCH;
			break;
		case REDSTONE_UNLIT:
			name = REDSTONE_TORCH_OFF;
			break;
		default:
			name = TORCH;
			break;
		}

		MetaBlock torch = new MetaBlock(name.clone());
		if (dir == Cardinal.UP) {

		} else if (dir == Cardinal.DOWN) {

		} else {
			torch = new MetaBlock(Material.REDSTONE_WALL_TORCH);
			Directional dd = (Directional) torch.getState();
			dd.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
			torch.setState(dd);
		}
//		if(dir == Cardinal.UP){
//			torch.withProperty(BlockTorch.FACING, EnumFacing.UP);
//		} else if(dir == Cardinal.DOWN){
//			torch.withProperty(BlockTorch.FACING, EnumFacing.DOWN);
//		} else {
//			torch.withProperty(BlockTorch.FACING, Cardinal.facing(Cardinal.reverse(dir)));
//		}

		torch.set(editor, pos);

	}

}
