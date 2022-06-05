package forge_sandbox.greymerk.roguelike.worldgen;

import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockStairs;
//import net.minecraft.block.BlockStairs.EnumHalf;
//import net.minecraft.block.state.IBlockState;

public class MetaStair extends MetaBlock implements IStair {

	public MetaStair(Material block) {
		super(block);
	}

	public MetaStair(MetaBlock block) {
		super(block);
	}

	public MetaStair(StairType type) {
		super(StairType.getBlock(type));
	}

	@Override
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown) {
		Stairs state = (Stairs) this.getState();
		state.setFacing(Cardinal.facing(dir));
		state.setHalf(upsideDown ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
		this.setState(state);
		return this;
	}

}
