package forge_sandbox.greymerk.roguelike.worldgen.blocks.door;

import com.google.gson.JsonElement;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door.Hinge;
//import net.minecraft.block.BlockDoor;
//import net.minecraft.block.state.IBlockState;

public class Door implements IDoor {

	MetaBlock block;

	public Door(MetaBlock block) {
		this.block = block;
	}

	public Door(DoorType type) {
		this.block = DoorType.get(type);
	}

	public Door(JsonElement e) throws Exception {
		this.block = new MetaBlock(e);
	}

	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir) {
		Door.generate(editor, this.block, pos, dir, false);
	}

	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open) {
		Door.generate(editor, this.block, pos, dir, open);
	}

	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, DoorType type) {
		MetaBlock door = DoorType.get(type);
		generate(editor, door, pos, dir, false);
	}

	public static void generate(IWorldEditor editor, MetaBlock door, Coord pos, Cardinal dir, boolean open) {
		Coord cursor = new Coord(pos);
		MetaBlock doorBase = setProperties(door, false, dir, open, false);
		doorBase.set(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = setProperties(door, true, dir, open, false);
		doorTop.set(editor, cursor);
	}

	private static MetaBlock setProperties(MetaBlock doorblock, boolean top, Cardinal dir, boolean open,
			boolean hingeLeft) {

		org.bukkit.block.data.type.Door door = (org.bukkit.block.data.type.Door) Bukkit
				.createBlockData(doorblock.getMaterial());
		door.setHalf(top ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
		door.setFacing(Cardinal.facing(dir));
		door.setOpen(open);
		door.setHinge(hingeLeft ? Hinge.LEFT : Hinge.RIGHT);

		return new MetaBlock(door);
	}

}
