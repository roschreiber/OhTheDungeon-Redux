/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;

public class FarLand2 extends BaseGenerator {
   public static int chunkStart = 784427;
   final BaseGenerator generator = new VanillaGeneratorFarLandHelper();
   final int mode = 0;
   final int finalX = chunkStart;
   final int finalZ = 0;


   public int movePosX(int pos, boolean chunk) {
      int coef = (chunk ? 1 : 16) * (pos >= 0 ? 1 : -1);
      int offs = this.finalX * coef;
      return pos + offs;
   }

   public int movePosZ(int pos, boolean chunk) {
      int coef = (chunk ? 1 : 16) * (pos >= 0 ? 1 : -1);
      int offs = this.finalZ * coef;
      return pos + offs;
   }

   @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int x, int z) {
      x = this.movePosX(x, true);
      z = this.movePosZ(z, true);
      return this.generator.asyncGenerateChunkData(seed, random, x, z);
   }

}
