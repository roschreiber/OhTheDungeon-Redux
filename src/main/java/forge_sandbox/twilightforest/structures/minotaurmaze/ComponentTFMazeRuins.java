package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

/**
 * This component is the base for the maze/ruins combo feature. There are
 * village-like ruins above and a maze underneath.
 *
 * @author Ben
 */
public class ComponentTFMazeRuins extends StructureTFComponentOld {

	public ComponentTFMazeRuins() {
		super();
	}

	public ComponentTFMazeRuins(TFFeature feature, AsyncWorldEditor world, Random rand, int i, int x, int y, int z) {
		super(feature, i);
		this.setCoordBaseMode(BlockFace.SOUTH);

		// I have no bounding box
		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 0, 0, 0,
				BlockFace.SOUTH);

	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	@Override
	public void buildComponent(StructureTFComponent structurecomponent, List<StructureTFComponent> list,
			Random random) {
		super.buildComponent(structurecomponent, list, random);

		// add a maze
		ComponentTFMinotaurMaze maze = new ComponentTFMinotaurMaze(getFeatureType(), 1, boundingBox.minX,
				boundingBox.minY - 14, boundingBox.minZ, 1);
		list.add(maze);
		maze.buildComponent(this, list, random);

		// add maze entrance shaft
		ComponentTFMazeEntranceShaft mazeEnter = new ComponentTFMazeEntranceShaft(getFeatureType(), 2, random,
				boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1);
		list.add(mazeEnter);
		mazeEnter.buildComponent(this, list, random);

		// add aboveground maze entrance building
		ComponentTFMazeMound mazeAbove = new ComponentTFMazeMound(getFeatureType(), 2, random, boundingBox.minX - 14,
				boundingBox.minY, boundingBox.minZ - 14);
		list.add(mazeAbove);
		mazeAbove.buildComponent(this, list, random);

	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// I have no components
		return true;
	}

}
