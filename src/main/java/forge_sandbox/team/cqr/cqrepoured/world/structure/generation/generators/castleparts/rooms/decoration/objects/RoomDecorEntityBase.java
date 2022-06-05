package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBase;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.IRoomDecor;
import otd.lib.async.AsyncWorldEditor;

public abstract class RoomDecorEntityBase implements IRoomDecor {
	protected List<Vec3i> footprint; // Array of blockstates and their offsets

	protected RoomDecorEntityBase() {
		this.footprint = new ArrayList<>();
	}

	@Override
	public boolean wouldFit(BlockPos start, BlockFace side, Set<BlockPos> decoArea, Set<BlockPos> decoMap,
			CastleRoomBase room) {
		List<Vec3i> rotated = this.alignFootprint(this.footprint, side);

		for (Vec3i placement : rotated) {
			BlockPos pos = start.add(placement);
			if (!decoArea.contains(pos) || decoMap.contains(pos)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void build(AsyncWorldEditor world, BlockStateGenArray genArray, CastleRoomBase room,
			DungeonRandomizedCastle dungeon, BlockPos start, BlockFace side, Set<BlockPos> decoMap) {
		List<Vec3i> rotated = this.alignFootprint(this.footprint, side);

		for (Vec3i placement : rotated) {
			BlockPos pos = start.add(placement);
			decoMap.add(pos);
		}
		this.createEntityDecoration(world, start, genArray, side);
	}

	protected abstract void createEntityDecoration(AsyncWorldEditor world, BlockPos pos, BlockStateGenArray genArray,
			BlockFace side);

	protected List<Vec3i> alignFootprint(List<Vec3i> unrotated, BlockFace side) {
		List<Vec3i> result = new ArrayList<>();

		unrotated.forEach(v -> result.add(DungeonGenUtils.rotateVec3i(v, side)));

		return result;
	}
}
