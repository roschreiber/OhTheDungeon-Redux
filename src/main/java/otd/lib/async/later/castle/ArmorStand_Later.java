package otd.lib.async.later.castle;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;

public class ArmorStand_Later extends Later {

	private double x, y, z;
	private float yaw;

	private ArmorStand_Later(double x, double y, double z, float rotationYaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = rotationYaw;
	}

	public static void generate(AsyncWorldEditor world, double x, double y, double z, float rotationYaw) {
		ArmorStand_Later later = new ArmorStand_Later(x, y, z, rotationYaw);
		world.addLater(later);
	}

	@Override
	public Coord getPos() {
		return new Coord((int) x, (int) y, (int) z);
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		// TODO Auto-generated method stub
		ArmorStand armorStand = (ArmorStand) c.getWorld().spawnEntity(new Location(c.getWorld(), x, y, z),
				EntityType.ARMOR_STAND);
		armorStand.setRotation(yaw, 0.0F);
	}

}
