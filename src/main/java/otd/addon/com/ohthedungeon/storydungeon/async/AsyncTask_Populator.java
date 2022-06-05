/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.Later;

/**
 *
 * @author shadow_wind
 */
public class AsyncTask_Populator implements AsyncTask {
	private final BlockPopulator bp;
	private final List<ZoneWorld.CriticalNode> cn;
	private final List<Later> cl;
	private final int chunkX;
	private final int chunkZ;

	@SuppressWarnings("deprecation")
	@Override
	public boolean doTask(World world, Random random) {
		if (bp == null)
			return true;
		Chunk pc = world.getChunkAt(chunkX, chunkZ);
		bp.populate(world, random, pc);
		{
			List<int[]> delay_pos = new ArrayList<>();
			List<Material> delay_materials = new ArrayList<>();

			for (ZoneWorld.CriticalNode node : cn) {
				int[] pos = node.pos;
				Material material;
				if (node.bd != null)
					material = node.bd.getMaterial();
				else
					material = node.material;
				boolean patch = false;
				if (material == Material.IRON_BARS || material == Material.REDSTONE_WIRE || material == Material.WATER
						|| material == Material.LAVA || material == Material.OAK_FENCE
						|| material == Material.SPRUCE_FENCE || material == Material.JUNGLE_FENCE
						|| material == Material.BIRCH_FENCE || material == Material.DARK_OAK_FENCE
						|| material == Material.ACACIA_FENCE || material == Material.NETHER_BRICK_FENCE) {
					patch = true;
				}
				if (patch) {
					delay_materials.add(material);
					delay_pos.add(pos);
				} else {
					if (node.bd != null) {
						pc.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
					} else {
						pc.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
					}
				}
			}

			int len = delay_pos.size();
			for (int x = 0; x < len; x++) {
				int[] pos = delay_pos.get(x);
				pc.getBlock(pos[0], pos[1], pos[2]).setType(delay_materials.get(x), true);
			}

			for (Later later : cl) {
				later.doSomething();
			}
		}
		return true;
	}

	public AsyncTask_Populator(BlockPopulator bp, List<ZoneWorld.CriticalNode> cn, List<Later> cl, int x, int z) {
		this.bp = bp;
		this.cn = cn;
		this.cl = cl;
		this.chunkX = x;
		this.chunkZ = z;
	}

	public BlockPopulator getBlockPopulator() {
		return this.bp;
	}

	public List<Later> getLaterTask() {
		return this.cl;
	}

	public List<ZoneWorld.CriticalNode> getCriticalNodes() {
		return this.cn;
	}
}
