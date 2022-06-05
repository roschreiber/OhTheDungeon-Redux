package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBase;
import otd.lib.async.AsyncWorldEditor;

public class RoomDecorPillar extends RoomDecorBlocksBase {
	@Override
	protected void makeSchematic() {

	}

	@Override
	public boolean wouldFit(BlockPos start, BlockFace side, Set<BlockPos> decoArea, Set<BlockPos> decoMap,
			CastleRoomBase room) {
		this.schematic = this.getSizedSchematic(room);
		return super.wouldFit(start, side, decoArea, decoMap, room);
	}

	@Override
	public void build(AsyncWorldEditor world, BlockStateGenArray genArray, CastleRoomBase room,
			DungeonRandomizedCastle dungeon, BlockPos start, BlockFace side, Set<BlockPos> decoMap) {
		this.schematic = this.getSizedSchematic(room);
		super.build(world, genArray, room, dungeon, start, side, decoMap);
	}

	private List<DecoBlockBase> getSizedSchematic(CastleRoomBase room) {
		List<DecoBlockBase> sizedSchematic = new ArrayList<>();
		int height = room.getDecorationLengthY();
		final BlockData lowerStairs = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
		final BlockData upperStairs;
		{
			BlockData bd = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			Stairs stair = (Stairs) bd;
			stair.setHalf(Half.TOP);
			upperStairs = stair;
		}
		BlockData stairs;
		final BlockStateGenArray.GenerationPhase genPhase = BlockStateGenArray.GenerationPhase.MAIN;

		{
			Stairs stair = (Stairs) lowerStairs.clone();
			stair.setFacing(BlockFace.SOUTH);
			stairs = stair;
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_LEFT);
			sizedSchematic.add(new DecoBlockBase(0, 0, 0, stair, genPhase));
		}
		sizedSchematic.add(new DecoBlockBase(1, 0, 0, stairs.clone(), genPhase));
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_RIGHT);
			sizedSchematic.add(new DecoBlockBase(2, 0, 0, stair, genPhase));
		}

		{
			Stairs stair = (Stairs) lowerStairs.clone();
			stair.setFacing(BlockFace.EAST);
			stairs = stair;
		}
		sizedSchematic.add(new DecoBlockBase(0, 0, 1, stairs.clone(), genPhase));

		{
			Stairs stair = (Stairs) lowerStairs.clone();
			stair.setFacing(BlockFace.WEST);
			stairs = stair;
		}
		sizedSchematic.add(new DecoBlockBase(2, 0, 1, stairs.clone(), genPhase));

		{
			Stairs stair = (Stairs) lowerStairs.clone();
			stair.setFacing(BlockFace.NORTH);
			stairs = stair;
		}

		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_RIGHT);
			sizedSchematic.add(new DecoBlockBase(0, 0, 2, stair, genPhase));
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setFacing(BlockFace.NORTH);
			sizedSchematic.add(new DecoBlockBase(1, 0, 2, stair, genPhase));

		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_LEFT);
			sizedSchematic.add(new DecoBlockBase(2, 0, 2, stair, genPhase));
		}

		for (int y = 0; y < height; y++) {
			sizedSchematic.add(new DecoBlockBase(1, y, 1, Bukkit.createBlockData(Material.STONE_BRICKS), genPhase));
		}

		{
			Stairs stair = (Stairs) upperStairs.clone();
			stair.setFacing(BlockFace.SOUTH);
			stairs = stair;
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_LEFT);
			sizedSchematic.add(new DecoBlockBase(0, (height - 1), 0, stair, genPhase));
		}
		sizedSchematic.add(new DecoBlockBase(1, (height - 1), 0, stairs.clone(), genPhase));
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_RIGHT);
			sizedSchematic.add(new DecoBlockBase(2, (height - 1), 0, stair, genPhase));
		}

		{
			Stairs stair = (Stairs) upperStairs.clone();
			stair.setFacing(BlockFace.EAST);
			stairs = stair;
		}
		sizedSchematic.add(new DecoBlockBase(0, (height - 1), 1, stairs.clone(), genPhase)); // opt

		{
			Stairs stair = (Stairs) upperStairs.clone();
			stair.setFacing(BlockFace.WEST);
			stairs = stair;
		}
		sizedSchematic.add(new DecoBlockBase(2, (height - 1), 1, stairs.clone(), genPhase)); // opt

		{
			Stairs stair = (Stairs) upperStairs.clone();
			stair.setFacing(BlockFace.NORTH);
			stairs = stair;
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_RIGHT);
			sizedSchematic.add(new DecoBlockBase(0, (height - 1), 2, stair, genPhase));
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setFacing(BlockFace.NORTH);
			sizedSchematic.add(new DecoBlockBase(1, (height - 1), 2, stair, genPhase));
		}
		{
			Stairs stair = (Stairs) stairs.clone();
			stair.setShape(Shape.INNER_LEFT);
			sizedSchematic.add(new DecoBlockBase(2, (height - 1), 2, stair, genPhase));
		}

		return sizedSchematic;
	}
}
