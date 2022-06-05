package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.addons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleAddonRoofSpire extends CastleAddonRoofBase {
	public CastleAddonRoofSpire(BlockPos startPos, int sizeX, int sizeZ) {
		super(startPos, sizeX, sizeZ);
	}

	@Override
	public void generate(BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int roofX;
		int roofZ;
		int roofLenX;
		int roofLenZ;
		int underLenX = this.sizeX - 1;
		int underLenZ = this.sizeZ - 1;
		int x = this.startPos.getX() + 1;
		int y = this.startPos.getY();
		int z = this.startPos.getZ() + 1;

		do {
			boolean firstLayer = (y == this.startPos.getY());
			boolean stairLayer = ((y - this.startPos.getY()) % 4 == 0);

			if (stairLayer) {
				++x;
				++z;
				underLenX -= 2;
				underLenZ -= 2;
				if (((underLenX < 0) || (underLenZ < 0))) {
					break;
				}
			}
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

			if (stairLayer) {
				roofX = x - 1;
				roofZ = z - 1;
				roofLenX = underLenX + 2;
				roofLenZ = underLenZ + 2;

				// add the north row
				for (int i = 0; i < roofLenX; i++) {
					BlockData blockState = Bukkit.createBlockData(dungeon.getStairBlockState());
					Stairs stair = (Stairs) blockState;
					stair.setFacing(BlockFace.SOUTH);
					blockState = stair;

					// Apply properties to corner pieces
					if (i == 0) {
						if (firstLayer) {
							stair.setShape(Shape.INNER_LEFT);
							blockState = stair;
						} else {
							blockState = Bukkit.createBlockData(Material.AIR);
						}
					} else if (i == roofLenX - 1) {
						if (firstLayer) {
							stair.setShape(Shape.INNER_RIGHT);
							blockState = stair;
						} else {
							blockState = Bukkit.createBlockData(Material.AIR);
						}
					}

					genArray.addBlockState(new BlockPos(roofX + i, y, roofZ), blockState,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}
				// add the south row
				for (int i = 0; i < roofLenX; i++) {
					BlockData blockState = Bukkit.createBlockData(dungeon.getStairBlockState());
					Stairs stair = (Stairs) blockState;
					stair.setFacing(BlockFace.NORTH);
					blockState = stair;

					// Apply properties to corner pieces
					if (i == 0) {
						if (firstLayer) {
							stair.setShape(Shape.INNER_RIGHT);
							blockState = stair;
						} else {
							blockState = Bukkit.createBlockData(Material.AIR);
						}
					} else if (i == roofLenX - 1) {
						if (firstLayer) {
							stair.setShape(Shape.INNER_LEFT);
							blockState = stair;
						} else {
							blockState = Bukkit.createBlockData(Material.AIR);
						}
					}

					genArray.addBlockState(new BlockPos(roofX + i, y, roofZ + roofLenZ - 1), blockState,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}

				for (int i = 0; i < roofLenZ; i++) {
					BlockData blockState = Bukkit.createBlockData(dungeon.getStairBlockState());
					Stairs stair = (Stairs) blockState;
					stair.setFacing(BlockFace.EAST);
					blockState = stair;
					genArray.addBlockState(new BlockPos(roofX, y, roofZ + i), blockState,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);

					blockState = Bukkit.createBlockData(dungeon.getStairBlockState());
					stair = (Stairs) blockState;
					stair.setFacing(BlockFace.WEST);
					blockState = stair;

					genArray.addBlockState(new BlockPos(roofX + roofLenX - 1, y, roofZ + i), blockState,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}

			y++;
			if (stairLayer) {
				x++;
				z++;
				underLenX -= 2;
				underLenZ -= 2;
			}

		} while (underLenX >= 0 && underLenZ >= 0);
	}
}
