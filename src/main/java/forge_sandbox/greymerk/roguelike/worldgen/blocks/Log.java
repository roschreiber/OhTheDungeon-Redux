package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockLog;
//import net.minecraft.block.BlockNewLog;
//import net.minecraft.block.BlockOldLog;
//import net.minecraft.block.BlockPlanks;
//import net.minecraft.init.Blocks;

public class Log {

	public static MetaBlock get(Wood type, Cardinal dir) {

		MetaBlock log = new MetaBlock(Material.OAK_LOG);

		setType(log, type);

		if (dir == null) {
			return log;
		}

		switch (dir) {
		case UP:
		case DOWN:
			setAxis(log, Axis.Y);
			break;
		case EAST:
		case WEST:
			setAxis(log, Axis.X);
			break;
		case NORTH:
		case SOUTH:
			setAxis(log, Axis.Z);
			break;
		}

		return log;

	}

	private static void setAxis(MetaBlock log, Axis axis) {
		Orientable state = (Orientable) log.getState();
		state.setAxis(axis);
		log.setState(state);
	}

	public static MetaBlock getLog(Wood type) {
		return get(type, Cardinal.UP);
	}

	public static Material getBlockId(Wood type) {
		switch (type) {
		case OAK:
			return Material.OAK_LOG;
		case SPRUCE:
			return Material.SPRUCE_LOG;
		case BIRCH:
			return Material.BIRCH_LOG;
		case JUNGLE:
			return Material.JUNGLE_LOG;
		case ACACIA:
			return Material.ACACIA_LOG;
		case DARKOAK:
			return Material.DARK_OAK_LOG;
		default:
			return Material.OAK_LOG;
		}
	}

	private final static BlockData OAK_LOG = Bukkit.createBlockData(Material.OAK_LOG);
	private final static BlockData SPRUCE_LOG = Bukkit.createBlockData(Material.SPRUCE_LOG);
	private final static BlockData BIRCH_LOG = Bukkit.createBlockData(Material.BIRCH_LOG);
	private final static BlockData JUNGLE_LOG = Bukkit.createBlockData(Material.JUNGLE_LOG);
	private final static BlockData ACACIA_LOG = Bukkit.createBlockData(Material.ACACIA_LOG);
	private final static BlockData DARK_OAK_LOG = Bukkit.createBlockData(Material.DARK_OAK_LOG);

	public static void setType(MetaBlock log, Wood type) {
		switch (type) {
		case OAK:
			log.setState(OAK_LOG.clone());
			return;
		case SPRUCE:
			log.setState(SPRUCE_LOG.clone());
			return;
		case BIRCH:
			log.setState(BIRCH_LOG.clone());
			return;
		case JUNGLE:
			log.setState(JUNGLE_LOG.clone());
			return;
		case ACACIA:
			log.setState(ACACIA_LOG.clone());
			return;
		case DARKOAK:
			log.setState(DARK_OAK_LOG.clone());
			return;
		default:
			log.setState(OAK_LOG.clone());
			return;
		}
	}
}
