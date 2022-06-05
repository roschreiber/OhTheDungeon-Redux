package forge_sandbox.twilightforest.structures.darktower;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import forge_sandbox.twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorDarkTower extends StructureTFDecorator {

	public StructureDecoratorDarkTower() {
		this.blockState = Bukkit.createBlockData(Material.STONE_BRICKS);
		this.accentState = Bukkit.createBlockData(Material.CHISELED_STONE_BRICKS);
		this.fenceState = Bukkit.createBlockData(Material.COBBLESTONE_WALL);
		this.stairState = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
		this.pillarState = Bukkit.createBlockData(Material.CHISELED_STONE_BRICKS);
		this.platformState = Bukkit.createBlockData(Material.CHISELED_STONE_BRICKS);
		this.randomBlocks = new StructureTFTowerWoods();
	}

}
