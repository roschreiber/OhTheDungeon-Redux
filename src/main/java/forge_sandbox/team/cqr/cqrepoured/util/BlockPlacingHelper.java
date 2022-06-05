package forge_sandbox.team.cqr.cqrepoured.util;

import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;
import otd.lib.async.AsyncWorldEditor;

public class BlockPlacingHelper {
	public static boolean setBlockStates(AsyncWorldEditor world, GeneratableDungeon dungeon, IBlockInfo blockInfo) {
		return blockInfo.place(world, dungeon);
	}

	public static boolean setBlockState(AsyncWorldEditor world, BlockPos pos, BlockData state) {
		world.setBlockData(pos.getX(), pos.getY(), pos.getZ(), state);
		return true;
	}

	@FunctionalInterface
	public interface IBlockInfo {

		boolean place(AsyncWorldEditor world, GeneratableDungeon dungeon);

	}
}
