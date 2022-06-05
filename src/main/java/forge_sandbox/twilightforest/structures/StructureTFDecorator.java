/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

/**
 * Stores information about what blocks to use in constructing this structure
 *
 * @author Ben
 */
public class StructureTFDecorator {
	public BlockData blockState = Bukkit.createBlockData(Material.STONE);
	public BlockData accentState = Bukkit.createBlockData(Material.COBBLESTONE);
	public BlockData stairState = Bukkit.createBlockData(Material.STONE_STAIRS);
	public BlockData fenceState = Bukkit.createBlockData(Material.OAK_FENCE);
	public BlockData pillarState = Bukkit.createBlockData(Material.STONE_BRICKS);
	public BlockData platformState = Bukkit.createBlockData(Material.STONE_SLAB);
	public BlockData floorState = Bukkit.createBlockData(Material.STONE_BRICKS);
	public BlockData roofState = Bukkit.createBlockData(Material.STONE_BRICKS);

	public StructureTFComponent.BlockSelector randomBlocks = new StructureTFStrongholdStones();
}
