package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.castle.ArmorStand_Later;

public class RoomDecorArmorStand extends RoomDecorEntityBase {
	public RoomDecorArmorStand() {
		super();
		this.footprint.add(new Vec3i(0, 0, 0));
		this.footprint.add(new Vec3i(0, 1, 0));
	}

	@Override
	protected void createEntityDecoration(AsyncWorldEditor world, BlockPos pos, BlockStateGenArray genArray,
			BlockFace side) {
		float rotation = EnumFacingConstant.getHorizontalAngle(side);
		ArmorStand_Later.generate(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, rotation);
		// Need to add 0.5 to each position amount so it spawns in the middle of the
		// tile
		// TODO
//		EntityArmorStand stand = new EntityArmorStand(world);
//		float rotation = side.getHorizontalAngle();
//		stand.setPosition((pos.getX() + 0.5), (pos.getY() + 0.5), (pos.getZ() + 0.5));
//		stand.rotationYaw = rotation;
//		genArray.addEntity(BlockPos.ORIGIN, stand);
	}
}
