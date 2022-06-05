package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndTorches extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndTorches() {
		super();
	}

	public ComponentTFMazeDeadEndTorches(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// normal doorway
		super.addComponentParts(world, rand, sbb);

		// torches!
		{
			Directional dir = (Directional) Bukkit.createBlockData(Material.WALL_TORCH);
			dir.setFacing(BlockFace.SOUTH);
			this.fillWithBlocks(world, sbb, 2, 1, 4, 3, 4, 4, dir, AIR, false);
		}
		{
			Directional dir = (Directional) Bukkit.createBlockData(Material.WALL_TORCH);
			dir.setFacing(BlockFace.WEST);
			this.fillWithBlocks(world, sbb, 1, 1, 1, 1, 4, 4, dir, AIR, false);
		}
		{
			Directional dir = (Directional) Bukkit.createBlockData(Material.WALL_TORCH);
			dir.setFacing(BlockFace.EAST);
			this.fillWithBlocks(world, sbb, 4, 1, 1, 4, 4, 4, dir, AIR, false);
		}

		return true;
	}
}
