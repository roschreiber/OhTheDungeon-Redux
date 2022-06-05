package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFMazeUpperEntrance extends StructureTFComponentOld {

	public ComponentTFMazeUpperEntrance() {
		super();
	}

	public ComponentTFMazeUpperEntrance(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i);
		this.setCoordBaseMode(HORIZONTALS[rand.nextInt(4)]);

		this.boundingBox = new StructureBoundingBox(x, y, z, x + 15, y + 4, z + 15);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	@Override
	public void buildComponent(StructureTFComponent structurecomponent, List<StructureTFComponent> list,
			Random random) {
		;
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

		// ceiling
//        this.generateMaybeBox(world, sbb, rand, 0.7F, 0, 5, 0, 15, 5, 15, TFBlocks.maze_stone.getDefaultState(), AIR, true, 0);

		this.fillWithBlocks(world, sbb, 0, 0, 0, 15, 0, 15, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 0, 1, 0, 15, 1, 15, Blocks.maze_stone, AIR, true);
		this.fillWithBlocks(world, sbb, 0, 2, 0, 15, 3, 15, Blocks.chiseled_maze_stone, AIR, true);
		this.fillWithBlocks(world, sbb, 0, 4, 0, 15, 4, 15, Blocks.maze_stone, AIR, true);
//        this.generateMaybeBox(world, sbb, rand, 0.2F, 0, 0, 0, 15, 5, 15, Blocks.gravel, AIR, true, 0);

		// doorways
		fillWithBlocks(world, sbb, 6, 1, 0, 9, 4, 0, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		fillWithAir(world, sbb, 7, 1, 0, 8, 3, 0);
		fillWithBlocks(world, sbb, 6, 1, 15, 9, 4, 15, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		fillWithAir(world, sbb, 7, 1, 15, 8, 3, 15);
		fillWithBlocks(world, sbb, 0, 1, 6, 0, 4, 9, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		fillWithAir(world, sbb, 0, 1, 7, 0, 3, 8);
		fillWithBlocks(world, sbb, 15, 1, 6, 15, 4, 9, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
		fillWithAir(world, sbb, 15, 1, 7, 15, 3, 8);

		// random holes
//        this.randomlyRareFillWithBlocks(world, sbb, 0, 1, 0, 15, 4, 15, 0, false);
//        this.randomlyRareFillWithBlocks(world, sbb, 0, 3, 0, 15, 4, 15, 0, true);
//        this.randomlyRareFillWithBlocks(world, sbb, 0, 4, 0, 15, 4, 15, 0, true);
		this.fillWithAir(world, sbb, 1, 1, 1, 14, 4, 14);

		// entrance pit
		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, Blocks.maze_stone, AIR, false);
		this.fillWithBlocks(world, sbb, 5, 4, 5, 10, 4, 10, Blocks.maze_stone, AIR, false);
//        this.generateMaybeBox(world, sbb, rand, 0.7F, 5, 2, 5, 10, 3, 10, Blocks.iron_bars, AIR, false, 0);
//        this.fillWithBlocks(world, sbb, 5, 2, 5, 10, 3, 10, Blocks.IRON_BARS, 0, AIR, false);

		this.fillWithAir(world, sbb, 6, 0, 6, 9, 4, 9);

		return true;
	}
}
