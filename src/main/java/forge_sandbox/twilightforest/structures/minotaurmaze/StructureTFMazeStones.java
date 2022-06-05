package forge_sandbox.twilightforest.structures.minotaurmaze;

import java.util.Random;
import org.bukkit.Material;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class StructureTFMazeStones extends StructureTFComponent.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			this.blockstate = Material.AIR;
		} else {
			this.blockstate = Blocks.maze_stone.getMaterial();
			float rf = random.nextFloat();

			if (rf < 0.2F) {
				this.blockstate = Blocks.mossy_maze_stone.getMaterial();
			} else if (rf < 0.5F) {
				this.blockstate = Blocks.cracked_maze_stone.getMaterial();
			} else {
				this.blockstate = Blocks.maze_stone.getMaterial();
			}
		}
	}

}
