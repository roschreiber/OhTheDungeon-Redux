package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.TrapDoor;
//import net.minecraft.block.BlockTrapDoor;
//import net.minecraft.init.Blocks;

public enum Trapdoor {

	OAK, IRON;

	private static void setDirection(MetaBlock block, BlockFace dir) {
		Directional state = (Directional) block.getState();
		state.setFacing(dir);
		block.setState(state);
	}

	private static void setHalf(MetaBlock block, Bisected.Half half) {
		TrapDoor state = (TrapDoor) block.getState();
		state.setHalf(half);
		block.setState(state);
	}

	private static void setOpen(MetaBlock block, boolean open) {
		TrapDoor state = (TrapDoor) block.getState();
		state.setOpen(open);
		block.setState(state);
	}

	public static MetaBlock get(Trapdoor type, Cardinal dir, boolean bottom, boolean open) {

		MetaBlock block;

		switch (type) {
		case OAK:
			block = new MetaBlock(Material.OAK_TRAPDOOR);
			break;
		case IRON:
			block = new MetaBlock(Material.IRON_TRAPDOOR);
			break;
		default:
			block = new MetaBlock(Material.OAK_TRAPDOOR);
			break;
		}

		setDirection(block, Cardinal.facing(dir));

		if (bottom) {
			setHalf(block, Bisected.Half.BOTTOM);
		}

		if (open) {
			setOpen(block, true);
		}

		return block;

	}

}
