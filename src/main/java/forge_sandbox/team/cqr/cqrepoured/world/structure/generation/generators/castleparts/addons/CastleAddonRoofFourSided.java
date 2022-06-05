package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.addons;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleAddonRoofFourSided extends CastleAddonRoofBase {
	public CastleAddonRoofFourSided(BlockPos startPos, int sizeX, int sizeZ) {
		super(startPos, sizeX, sizeZ);
	}

	@Override
	public void generate(BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int roofX;
		int roofZ;
		int roofLenX;
		int roofLenZ;
		int underLenX = this.sizeX;
		int underLenZ = this.sizeZ;
		int x = this.startPos.getX();
		int y = this.startPos.getY();
		int z = this.startPos.getZ();

		do {
			// Add the foundation under the roof
			BlockData state = Bukkit.createBlockData(dungeon.getMainBlockState());
			if (underLenX > 0 && underLenZ > 0) {
				for (int i = 0; i < underLenX; i++) {
					genArray.addBlockState(new BlockPos(x + i, y, z), state, BlockStateGenArray.GenerationPhase.MAIN,
							BlockStateGenArray.EnumPriority.MEDIUM);
					genArray.addBlockState(new BlockPos(x + i, y, z + underLenZ - 1), state,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}
				for (int j = 0; j < underLenZ; j++) {
					genArray.addBlockState(new BlockPos(x, y, z + j), state, BlockStateGenArray.GenerationPhase.MAIN,
							BlockStateGenArray.EnumPriority.MEDIUM);
					genArray.addBlockState(new BlockPos(x + underLenX - 1, y, z + j), state,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}

			roofX = x - 1;
			roofZ = z - 1;
			roofLenX = underLenX + 2;
			roofLenZ = underLenZ + 2;

			// add the north row
			for (int i = 0; i < roofLenX; i++) {
				Stairs blockState = (Stairs) Bukkit.createBlockData(dungeon.getRoofBlockState());
				blockState.setFacing(BlockFace.SOUTH);

				// Apply properties to corner pieces
				if (i == 0) {
					blockState.setShape(Shape.INNER_LEFT);
				} else if (i == roofLenX - 1) {
					blockState.setShape(Shape.INNER_RIGHT);
				}

				genArray.addBlockState(new BlockPos(roofX + i, y, roofZ), blockState,
						BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
			}
			// add the south row
			for (int i = 0; i < roofLenX; i++) {
				Stairs blockState = (Stairs) Bukkit.createBlockData(dungeon.getRoofBlockState());
				blockState.setFacing(BlockFace.NORTH);

				// Apply properties to corner pieces
				if (i == 0) {
					blockState.setShape(Shape.INNER_RIGHT);
				} else if (i == roofLenX - 1) {
					blockState.setShape(Shape.INNER_LEFT);
				}

				genArray.addBlockState(new BlockPos(roofX + i, y, roofZ + roofLenZ - 1), blockState,
						BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
			}

			for (int i = 0; i < roofLenZ; i++) {
				Stairs blockState = (Stairs) Bukkit.createBlockData(dungeon.getRoofBlockState());
				blockState.setFacing(BlockFace.EAST);
				genArray.addBlockState(new BlockPos(roofX, y, roofZ + i), blockState,
						BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);

				blockState = (Stairs) Bukkit.createBlockData(dungeon.getRoofBlockState());
				blockState.setFacing(BlockFace.WEST);

				genArray.addBlockState(new BlockPos(roofX + roofLenX - 1, y, roofZ + i), blockState,
						BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
			}

			x++;
			y++;
			z++;
			underLenX -= 2;
			underLenZ -= 2;
		} while (underLenX >= 0 && underLenZ >= 0);
	}
}
