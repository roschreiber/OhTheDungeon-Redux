package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeRoomExit extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomExit() {
		super();
	}

	public ComponentTFMazeRoomExit(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);

		// shaft down
		this.fillWithBlocks(world, sbb, 5, -5, 5, 10, 0, 10, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 5, 2, 5, 10, 3, 10, Blocks.iron_bars, AIR, false);
		this.fillWithBlocks(world, sbb, 5, 4, 5, 10, 4, 10, Blocks.maze_stone, AIR, false);
		this.fillWithAir(world, sbb, 6, -5, 6, 9, 4, 9);

		return true;
	}

}
