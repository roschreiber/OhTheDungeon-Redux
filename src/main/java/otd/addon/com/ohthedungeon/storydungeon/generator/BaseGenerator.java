/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import java.util.Random;
import org.bukkit.generator.ChunkGenerator;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;

/**
 *
 * @author shadow_wind
 */
public class BaseGenerator extends ChunkGenerator {
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkx, int chunkz) {
		return new AsyncChunk();
	}
}
