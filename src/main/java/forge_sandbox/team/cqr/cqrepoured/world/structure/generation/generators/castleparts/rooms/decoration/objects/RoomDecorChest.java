package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBase;
import otd.lib.async.AsyncWorldEditor;
import otd.util.OTDLoottables;

public class RoomDecorChest extends RoomDecorBlocksBase {
	public RoomDecorChest() {
		super();
	}

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockRotating(0, 0, 0, Bukkit.createBlockData(Material.CHEST), BlockFace.SOUTH,
				BlockStateGenArray.GenerationPhase.MAIN));
	}

	@Override
	public void build(AsyncWorldEditor world, BlockStateGenArray genArray, CastleRoomBase room,
			DungeonRandomizedCastle dungeon, BlockPos start, BlockFace side, Set<BlockPos> decoMap) {
		super.build(world, genArray, room, dungeon, start, side, decoMap);
		OTDLoottables loot_table = room.getChestIDs();
		BlockData bd = this.schematic.get(0).getState(side).clone();
		Directional dir = (Directional) bd;
		genArray.addRelativePosChest(world, start, dir.getFacing(), loot_table);
		decoMap.add(start);
		// TODO
//		ResourceLocation[] chestIDs = room.getChestIDs();
//		if (chestIDs != null && chestIDs.length > 0) {
//			Block chestBlock = Blocks.CHEST;
//			IBlockState state = this.schematic.get(0).getState(side);
//			TileEntityChest chest = (TileEntityChest) chestBlock.createTileEntity(world, state);
//			if (chest != null) {
//				ResourceLocation resLoc = chestIDs[genArray.getRandom().nextInt(chestIDs.length)];
//				if (resLoc != null) {
//					long seed = WorldDungeonGenerator.getSeed(world, start.getX() + start.getY(), start.getZ() + start.getY());
//					chest.setLootTable(resLoc, seed);
//				}
//				NBTTagCompound nbt = chest.writeToNBT(new NBTTagCompound());
//				genArray.addBlockState(start, state, nbt, BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.HIGH);
//				decoMap.add(start);
//			}
//		} else {
//			// CQRMain.logger.warn("Placed a chest but could not find a loot table for Room Type {}", room.getRoomType());
//			// TODO fix rooms having no chests (or is this intended?)
//		}
	}
}
