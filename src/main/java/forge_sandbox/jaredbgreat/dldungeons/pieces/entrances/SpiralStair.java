package forge_sandbox.jaredbgreat.dldungeons.pieces.entrances;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

import forge_sandbox.jaredbgreat.dldungeons.builder.DBlock;
import forge_sandbox.jaredbgreat.dldungeons.planner.Dungeon;
import org.bukkit.World;
import static forge_sandbox.jaredbgreat.dldungeons.Constant.*;
//import net.minecraft.block.Block;
//import net.minecraft.world.World;

public class SpiralStair extends AbstractEntrance {

	public SpiralStair(int x, int z) {
		super(x, z);
	}

	@SuppressWarnings("unused")
	@Override
	public void build(Dungeon dungeon, World world) {
		int wx = x + (dungeon.map.chunkX * 16) - (dungeon.map.room.length / 2) + 8;
		int wz = z + (dungeon.map.chunkZ * 16) - (dungeon.map.room.length / 2) + 8;
		int bottom = dungeon.map.floorY[x][z];
		int top = 255;
		// int top = world.getChunkFromChunkCoords(wx / 16, wz / 16).getHeight(wx, wz);
		while (!DBlock.isGroundBlockSync(world, wx, top, wz))
			top--;
		top++;
		int side = dungeon.random.nextInt(4);
		for (int i = bottom; i < top; i++) {
			int sx, sz;
			DBlock.placeSync(world, wx, i, wz, dungeon.wallBlock1);
			switch (side) {
			case 0:
				DBlock.placeBlockSync(world, wx + 1, i, wz, slab0);
				DBlock.placeBlockSync(world, wx + 1, i, wz + 1, slab8);
				// Empty space
				DBlock.deleteBlockSync(world, wx, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz);
				DBlock.deleteBlockSync(world, wx - 1, i, wz - 1);
				DBlock.deleteBlockSync(world, wx, i, wz - 1);
				DBlock.deleteBlockSync(world, wx + 1, i, wz - 1);
				break;
			case 1:
				DBlock.placeBlockSync(world, wx, i, wz + 1, slab0);
				DBlock.placeBlockSync(world, wx - 1, i, wz + 1, slab8);
				// Empty space
				DBlock.deleteBlockSync(world, wx + 1, i, wz);
				DBlock.deleteBlockSync(world, wx + 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz);
				DBlock.deleteBlockSync(world, wx - 1, i, wz - 1);
				DBlock.deleteBlockSync(world, wx, i, wz - 1);
				DBlock.deleteBlockSync(world, wx + 1, i, wz - 1);
				break;
			case 2:
				DBlock.placeBlockSync(world, wx - 1, i, wz, slab0);
				DBlock.placeBlockSync(world, wx - 1, i, wz - 1, slab8);
				// Empty space
				DBlock.deleteBlockSync(world, wx + 1, i, wz);
				DBlock.deleteBlockSync(world, wx + 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx, i, wz - 1);
				DBlock.deleteBlockSync(world, wx + 1, i, wz - 1);
				break;
			case 3:
				DBlock.placeBlockSync(world, wx, i, wz - 1, slab0);
				DBlock.placeBlockSync(world, wx + 1, i, wz - 1, slab8);
				// Empty space
				DBlock.deleteBlockSync(world, wx + 1, i, wz);
				DBlock.deleteBlockSync(world, wx + 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz + 1);
				DBlock.deleteBlockSync(world, wx - 1, i, wz);
				DBlock.deleteBlockSync(world, wx - 1, i, wz - 1);
				break;
			}
			side = (side + 1) % 4;
		}
	}

}
