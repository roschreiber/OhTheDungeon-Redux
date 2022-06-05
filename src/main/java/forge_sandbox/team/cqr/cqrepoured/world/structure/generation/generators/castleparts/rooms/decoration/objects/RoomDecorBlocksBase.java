package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBase;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.IRoomDecor;
import otd.lib.async.AsyncWorldEditor;

public abstract class RoomDecorBlocksBase implements IRoomDecor {

	protected List<DecoBlockBase> schematic; // Array of blockstates and their offsets

	protected RoomDecorBlocksBase() {
		this.schematic = new ArrayList<>();
		this.makeSchematic();
	}

	protected abstract void makeSchematic();

	@Override
	public boolean wouldFit(BlockPos start, BlockFace side, Set<BlockPos> decoArea, Set<BlockPos> decoMap,
			CastleRoomBase room) {
		List<DecoBlockBase> rotated = this.alignSchematic(side);

		for (DecoBlockBase placement : rotated) {
			BlockPos pos = start.add(placement.offset);
			if (!decoArea.contains(pos) || decoMap.contains(pos)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void build(AsyncWorldEditor world, BlockStateGenArray genArray, CastleRoomBase room,
			DungeonRandomizedCastle dungeon, BlockPos start, BlockFace side, Set<BlockPos> decoMap) {
		List<DecoBlockBase> rotated = this.alignSchematic(side);

		for (DecoBlockBase placement : rotated) {
			BlockPos pos = start.add(placement.offset);
			genArray.addBlockState(pos, placement.getState(side), placement.getGenPhase(),
					BlockStateGenArray.EnumPriority.MEDIUM);

			if (placement.getState(side).getMaterial() != Material.AIR) {
				decoMap.add(pos);
			}
		}
	}

	protected List<DecoBlockBase> alignSchematic(BlockFace side) {
		List<DecoBlockBase> result = new ArrayList<>();

		for (DecoBlockBase p : this.schematic) {
			result.add(
					new DecoBlockBase(DungeonGenUtils.rotateVec3i(p.offset, side), p.getState(side), p.getGenPhase()));
		}

		return result;
	}
}
