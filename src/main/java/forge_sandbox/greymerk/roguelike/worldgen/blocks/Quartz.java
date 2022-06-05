package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.Orientable;
//import net.minecraft.block.BlockQuartz;
//import net.minecraft.init.Blocks;

public enum Quartz {

	SMOOTH, CHISELED, PILLAR;

	public static MetaBlock get(Quartz type) {
		@SuppressWarnings("unused")
		MetaBlock block;
		switch (type) {
		case CHISELED:
			block = new MetaBlock(Material.CHISELED_QUARTZ_BLOCK);
		case PILLAR:
			block = new MetaBlock(Material.QUARTZ_PILLAR);
		case SMOOTH:
		default:
			return new MetaBlock(Material.QUARTZ_BLOCK);
		}
	}

	public static MetaBlock getPillar(Cardinal dir) {
		MetaBlock block = new MetaBlock(Material.QUARTZ_PILLAR);
		switch (dir) {
		case EAST:
		case WEST:
			setAxis(block, Axis.X);
			break;
		case NORTH:
		case SOUTH:
			setAxis(block, Axis.Z);
			break;
		case UP:
		case DOWN:
		default:
			setAxis(block, Axis.Y);
			break;
		}

		return block;
	}

	private static void setAxis(MetaBlock block, Axis axis) {
		Orientable state = (Orientable) block.getState();
		state.setAxis(axis);
		block.setState(state);
	}

}
