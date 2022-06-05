package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeCorridorIronFence extends ComponentTFMazeCorridor {

	public ComponentTFMazeCorridorIronFence() {
		super();
	}

	public ComponentTFMazeCorridorIronFence(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		this.fillWithBlocks(world, sbb, 1, 4, 2, 4, 4, 3, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 3, 3, Blocks.chiseled_maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 2, 1, 2, 3, 3, 3, Blocks.iron_bars, AIR, false);
		return true;
	}
}
