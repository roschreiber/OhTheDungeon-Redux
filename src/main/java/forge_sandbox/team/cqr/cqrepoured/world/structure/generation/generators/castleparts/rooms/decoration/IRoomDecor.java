package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration;

import java.util.Set;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBase;
import otd.lib.async.AsyncWorldEditor;

public interface IRoomDecor {
	boolean wouldFit(BlockPos start, BlockFace side, Set<BlockPos> decoArea, Set<BlockPos> decoMap,
			CastleRoomBase room);

	void build(AsyncWorldEditor world, BlockStateGenArray genArray, CastleRoomBase room,
			DungeonRandomizedCastle dungeon, BlockPos start, BlockFace side, Set<BlockPos> decoMap);
}
