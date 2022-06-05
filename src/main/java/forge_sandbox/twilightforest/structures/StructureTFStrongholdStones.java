/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures;

import java.util.Random;
import org.bukkit.Material;

public class StructureTFStrongholdStones extends StructureTFComponent.BlockSelector {
	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			blockstate = Material.AIR;
		} else {
			float f = random.nextFloat();

			if (f < 0.2F) {
				blockstate = Material.CRACKED_STONE_BRICKS;
			} else if (f < 0.5F) {
				blockstate = Material.MOSSY_STONE_BRICKS;
			} else if (f < 0.55F) {
				blockstate = Material.INFESTED_STONE_BRICKS;
			} else {
				blockstate = Material.STONE_BRICKS;
			}
		}
	}

}
