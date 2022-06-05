package forge_sandbox.twilightforest.structures.darktower;

import java.util.Random;
import org.bukkit.Material;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class StructureTFTowerWoods extends StructureTFComponent.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean isWall) {
		if (!isWall) {
			this.blockstate = Material.AIR;
		} else {
			float randFloat = random.nextFloat();

			if (randFloat < 0.1F) {
				this.blockstate = Material.CRACKED_STONE_BRICKS;
			} else if (randFloat < 0.2F) {
				this.blockstate = Material.MOSSY_STONE_BRICKS;
			} else if (randFloat < 0.225F) {
				this.blockstate = Material.INFESTED_STONE_BRICKS;
			} else {
				this.blockstate = Material.STONE_BRICKS;
			}
		}
	}

}
