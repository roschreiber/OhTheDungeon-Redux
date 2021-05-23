/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.dungeon.dungeonmaze;

import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulator;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.structure.OasisChunkPopulator;
import java.util.HashSet;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.util.RandomCollection;

/**
 *
 * @author
 */
public class SmoofyDungeonPopulator {
    private static final int MIN_LAYER = 1;
    private static final int MAX_LAYER = 7;
    private static final int LAYER_COUNT = 7;
    
    private static class DungeonChunk {
        public boolean whole_chunk = false;
        public ChunkBlockPopulator chunk = null;
        public int chunkx, chunkz;
        
        public ChunkBlockPopulator[][] map;
        
        public DungeonChunk(int layer) {
            map = new ChunkBlockPopulator[layer][4];
        }
        
        public void addLayer(int layer, MazeLayerBlockPopulator p) {
            if(layer >= map.length) return;
            map[layer][1] = null;
            map[layer][2] = null;
            map[layer][3] = null;
            map[layer][0] = p;
        }
        
        public void addRoom(int layer, int pos, MazeRoomBlockPopulator p) {
            if(layer >= map.length) return;
            if(pos >= 4) return;
            map[layer][pos] = p;
        }
    }
    
    public static class SmoofyDungeonInstance {
        DungeonChunk chunks[][];
        final int layerMin = MIN_LAYER;
        final int layerMax =  MAX_LAYER;
        public void placePiece(World world, Random rand, int i, int j) {
            int ymin = 18 + ((layerMin - 1) * 6);
            int ymax = 18 + ((layerMax - 1 + 1) * 6);

            {
                PaperLib.getChunkAtAsync(world, chunks[i][j].chunkx, chunks[i][j].chunkz, true).thenAccept( (Chunk cc) ->
                {
//                    Chunk cc = world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz);
                    for(int x = 0; x < 16; x++) {
                        for(int y = 0; y < 16; y++) {
                            for(int z = ymin; z < ymax; z++) {
                                if((z - 18) % 6 == 0) {
                                    cc.getBlock(x, z, y).setType(Material.COBBLESTONE);
                                }
                                else cc.getBlock(x, z, y).setType(Material.AIR);
                            }
                        }
                    }

                    if(chunks[i][j].whole_chunk) {
                        ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, 
                                cc, new HashSet<>());
                        chunks[i][j].chunk.populateChunk(args);
                        if(chunks[i][j].chunk instanceof OasisChunkPopulator) {
                            OasisChunkPopulator t = (OasisChunkPopulator) chunks[i][j].chunk;
                            int x = chunks[i][j].chunkx * 16 + 8;
                            int z = chunks[i][j].chunkz * 16 + 8;
                            t.apply_glass(ymax, world, x, z);
                        }
                        return;
                    }
                    for(int x = 0; x < 16; x++) {
                        for(int z = 0; z < 16; z++) {
                            cc.getBlock(x, ymax, z).setType(Material.GLASS);
                        }
                    }
                    for(int l = layerMin - 1; l < layerMax; l++) {
                        // Calculate the Y coordinate based on the current layer
                        int y = 18 + ((l - 1) * 6);
                        if(chunks[i][j].map[l][1] == null && chunks[i][j].map[l][2] == null && chunks[i][j].map[l][3] == null) {
                            MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(world, rand, 
                                    world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz), new HashSet<>(), l, y);
                            MazeLayerBlockPopulator p = (MazeLayerBlockPopulator) chunks[i][j].map[l][0];
                            p.populateLayer(newArgs);
                        } else {
                            Chunk chunk = world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz);
                            for(int chunkX = 0; chunkX < 2; chunkX++) {
                                for(int chunkZ = 0; chunkZ < 2; chunkZ++) {
                                    // Calculate the global X and Y coordinates
                                    int x = (chunk.getX() * 16) + chunkX * 8;
                                    int z = (chunk.getZ() * 16) + chunkZ * 8;

                                    int index = chunkX * 2 + chunkZ;
                                    MazeRoomBlockPopulator p = (MazeRoomBlockPopulator) chunks[i][j].map[l][index];
                                    int floorOffset = p.getFloorOffset(chunkX * 8, y, chunkZ * 8, chunk);
                                    int ceilingOffset = p.getCeilingOffset(chunkX * 8, y, chunkZ * 8, chunk);

                                    MazeRoomBlockPopulatorArgs newArgs = new MazeRoomBlockPopulatorArgs(world, rand, chunk, 
                                            new HashSet<>(), l, x, y, z, floorOffset, ceilingOffset);
                                    p.populateRoom(newArgs);

                                    if(!p.getConstRoom()) {
                                        for(MazeRoomBlockPopulator pp : SmoofyDungeon.decoration)
                                            pp.populateRoom(newArgs);
                                    }
                                }
                            }
                        }
                    }
                    for(int m = ymin; m < ymax; m++) {
                        cc.getBlock(0, m, 7).setType(Material.STONE_BRICKS);
                        cc.getBlock(0, m, 8).setType(Material.STONE_BRICKS);
                        cc.getBlock(15, m, 7).setType(Material.STONE_BRICKS);
                        cc.getBlock(15, m, 8).setType(Material.STONE_BRICKS);
                        cc.getBlock(7, m, 0).setType(Material.STONE_BRICKS);
                        cc.getBlock(8, m, 0).setType(Material.STONE_BRICKS);
                        cc.getBlock(7, m, 15).setType(Material.STONE_BRICKS);
                        cc.getBlock(8, m, 15).setType(Material.STONE_BRICKS);
                    }
                });
            }

        }
        public boolean placeDungeon(World world, Random rand, int chunkx, int chunkz, int step, int w, int h, float oasis, float entry) {
            if(step == 0) {
                chunks = new DungeonChunk[w][h];
                for(int i = 0; i < w; i++) {
                    for(int j = 0; j < h; j++) {
                        chunks[i][j] = new DungeonChunk(MAX_LAYER);
                    }
                }

                if(rand.nextFloat() < oasis) {
                    int sizew = w - 2;
                    int sizeh = h - 2;
                    if(sizew > 0 && sizeh > 0) {
                        int a = rand.nextInt(sizew) + 1;
                        int b = rand.nextInt(sizeh) + 1;
                        chunks[a][b].whole_chunk = true;
                        chunks[a][b].chunk = SmoofyDungeon.oasis;
                    }
                }

                {
                    int count = rand.nextInt(2) + 1;
                    for(int i = 0; i < count; i++) {
                        int sizew = w - 2;
                        int sizeh = h - 2;
                        if(sizew > 0 && sizeh > 0) {
                            int a = rand.nextInt(sizew) + 1;
                            int b = rand.nextInt(sizeh) + 1;
                            MazeLayerBlockPopulator p = SmoofyDungeon.layer.next();
                            int max = p.getMaximumLayer();
                            int min = p.getMinimumLayer();
                            int layer = rand.nextInt(max - min) + min;
                            chunks[a][b].addLayer(layer, p);
                        }
                    }
                }

                for(int i = 0; i < w; i++) {
                    for(int j = 0; j < h; j++) {
                        if(chunks[i][j].whole_chunk) continue;
                        for(int k = layerMin - 1; k < layerMax; k++) {
                            RandomCollection<MazeRoomBlockPopulator> r;
                            if(chunks[i][j].map[k][0] != null) continue;
                            r = SmoofyDungeon.ROOMS.get(k + 1);
                            chunks[i][j].addRoom(k, 0, r.next());
                            if((i + k) % 2 == 0 && (j + k) % 2 == 0) {
                                chunks[i][j].addRoom(k, 1, SmoofyDungeon.empty);
                                chunks[i][j].addRoom(k, 2, SmoofyDungeon.empty);
                                chunks[i][j].addRoom(k, 3, SmoofyDungeon.empty);
                            } else {
                                chunks[i][j].addRoom(k, 1, r.next());
                                chunks[i][j].addRoom(k, 2, r.next());
                                chunks[i][j].addRoom(k, 3, r.next());
                            }
                        }
                    }
                }

                if(rand.nextFloat() < entry) {
                    //add entry
                    int sizew = w;
                    int sizeh = h;
                    if(sizew > 0 && sizeh > 0) {
                        int a = rand.nextInt(sizew);
                        int b = rand.nextInt(sizeh);
                        int c = rand.nextInt(4);
                        chunks[a][b].addRoom(MAX_LAYER - 1, c, SmoofyDungeon.entry); 
                    }
                }


                int midx = w / 2;
                int midz = h / 2;
                for(int i = 0; i < w; i++) {
                    for(int j = 0; j < h; j++) {
                        chunks[i][j].chunkx = i - midx + chunkx;
                        chunks[i][j].chunkz = j - midz + chunkz;
                    }
                }
                return false;
            }
            //w h
            step = step - 1;
            int i = step % w;
            int j = step / h;
            placePiece(world, rand, i, j);
            if(step + 1 >= w * h) return true;
            return false;
        }
    }
    
    public static void populateChunk(World world, Random rand, int chunkx, int chunkz, int w, int h, float oasis, float entry) {

	final int layerMin = MIN_LAYER;
        final int layerMax =  MAX_LAYER;
        
        DungeonChunk chunks[][] = new DungeonChunk[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                chunks[i][j] = new DungeonChunk(MAX_LAYER);
            }
        }
        
        if(rand.nextFloat() < oasis) {
            int sizew = w - 2;
            int sizeh = h - 2;
            if(sizew > 0 && sizeh > 0) {
                int a = rand.nextInt(sizew) + 1;
                int b = rand.nextInt(sizeh) + 1;
                chunks[a][b].whole_chunk = true;
                chunks[a][b].chunk = SmoofyDungeon.oasis;
            }
        }
        
        {
            int count = rand.nextInt(2) + 1;
            for(int i = 0; i < count; i++) {
                int sizew = w - 2;
                int sizeh = h - 2;
                if(sizew > 0 && sizeh > 0) {
                    int a = rand.nextInt(sizew) + 1;
                    int b = rand.nextInt(sizeh) + 1;
                    MazeLayerBlockPopulator p = SmoofyDungeon.layer.next();
                    int max = p.getMaximumLayer();
                    int min = p.getMinimumLayer();
                    int layer = rand.nextInt(max - min) + min;
                    chunks[a][b].addLayer(layer, p);
                }
            }
        }
        
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if(chunks[i][j].whole_chunk) continue;
                for(int k = layerMin - 1; k < layerMax; k++) {
                    RandomCollection<MazeRoomBlockPopulator> r;
                    if(chunks[i][j].map[k][0] != null) continue;
                    r = SmoofyDungeon.ROOMS.get(k + 1);
                    chunks[i][j].addRoom(k, 0, r.next());
                    if((i + k) % 2 == 0 && (j + k) % 2 == 0) {
                        chunks[i][j].addRoom(k, 1, SmoofyDungeon.empty);
                        chunks[i][j].addRoom(k, 2, SmoofyDungeon.empty);
                        chunks[i][j].addRoom(k, 3, SmoofyDungeon.empty);
                    } else {
                        chunks[i][j].addRoom(k, 1, r.next());
                        chunks[i][j].addRoom(k, 2, r.next());
                        chunks[i][j].addRoom(k, 3, r.next());
                    }
                }
            }
        }
        
        if(rand.nextFloat() < entry) {
            //add entry
            int sizew = w - 2;
            int sizeh = h - 2;
            if(sizew > 0 && sizeh > 0) {
                int a = rand.nextInt(sizew) + 1;
                int b = rand.nextInt(sizeh) + 1;
                int c = rand.nextInt(4);
                chunks[a][b].addRoom(MAX_LAYER - 1, c, SmoofyDungeon.entry); 
            }
        }
        
        
        int midx = w / 2;
        int midz = h / 2;
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                chunks[i][j].chunkx = i - midx + chunkx;
                chunks[i][j].chunkz = j - midz + chunkz;
            }
        }
        int ymin = 18 + ((layerMin - 1) * 6);
        int ymax = 18 + ((layerMax - 1 + 1) * 6);
                
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                Chunk cc = world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz);
                for(int x = 0; x < 16; x++) {
                    for(int y = 0; y < 16; y++) {
                        for(int z = ymin; z < ymax; z++) {
                            if((z - 18) % 6 == 0) {
                                cc.getBlock(x, z, y).setType(Material.COBBLESTONE);
                            }
                            else cc.getBlock(x, z, y).setType(Material.AIR);
                        }
                    }
                }
                
                if(chunks[i][j].whole_chunk) {
                    ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, 
                            cc, new HashSet<>());
                    chunks[i][j].chunk.populateChunk(args);
                    if(chunks[i][j].chunk instanceof OasisChunkPopulator) {
                        OasisChunkPopulator t = (OasisChunkPopulator) chunks[i][j].chunk;
                        int x = chunks[i][j].chunkx * 16 + 8;
                        int z = chunks[i][j].chunkz * 16 + 8;
                        t.apply_glass(ymax, world, x, z);
                    }
                    continue;
                }
                for(int x = 0; x < 16; x++) {
                    for(int z = 0; z < 16; z++) {
                        cc.getBlock(x, ymax, z).setType(Material.GLASS);
                    }
                }
                for(int l = layerMin - 1; l < layerMax; l++) {
                    // Calculate the Y coordinate based on the current layer
                    int y = 18 + ((l - 1) * 6);
                    if(chunks[i][j].map[l][1] == null && chunks[i][j].map[l][2] == null && chunks[i][j].map[l][3] == null) {
                        MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(world, rand, 
                                world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz), new HashSet<>(), l, y);
                        MazeLayerBlockPopulator p = (MazeLayerBlockPopulator) chunks[i][j].map[l][0];
                        p.populateLayer(newArgs);
                    } else {
                        Chunk chunk = world.getChunkAt(chunks[i][j].chunkx, chunks[i][j].chunkz);
                        for(int chunkX = 0; chunkX < 2; chunkX++) {
                            for(int chunkZ = 0; chunkZ < 2; chunkZ++) {
                                // Calculate the global X and Y coordinates
                                int x = (chunk.getX() * 16) + chunkX * 8;
                                int z = (chunk.getZ() * 16) + chunkZ * 8;
                                                               
                                int index = chunkX * 2 + chunkZ;
                                MazeRoomBlockPopulator p = (MazeRoomBlockPopulator) chunks[i][j].map[l][index];
                                int floorOffset = p.getFloorOffset(chunkX * 8, y, chunkZ * 8, chunk);
                                int ceilingOffset = p.getCeilingOffset(chunkX * 8, y, chunkZ * 8, chunk);
                                
                                MazeRoomBlockPopulatorArgs newArgs = new MazeRoomBlockPopulatorArgs(world, rand, chunk, 
                                        new HashSet<>(), l, x, y, z, floorOffset, ceilingOffset);
                                p.populateRoom(newArgs);
                                
                                if(!p.getConstRoom()) {
                                    for(MazeRoomBlockPopulator pp : SmoofyDungeon.decoration)
                                        pp.populateRoom(newArgs);
                                }
                            }
                        }
                    }
                }
                for(int m = ymin; m < ymax; m++) {
                    cc.getBlock(0, m, 7).setType(Material.STONE_BRICKS);
                    cc.getBlock(0, m, 8).setType(Material.STONE_BRICKS);
                    cc.getBlock(15, m, 7).setType(Material.STONE_BRICKS);
                    cc.getBlock(15, m, 8).setType(Material.STONE_BRICKS);
                    cc.getBlock(7, m, 0).setType(Material.STONE_BRICKS);
                    cc.getBlock(8, m, 0).setType(Material.STONE_BRICKS);
                    cc.getBlock(7, m, 15).setType(Material.STONE_BRICKS);
                    cc.getBlock(8, m, 15).setType(Material.STONE_BRICKS);
                }
            }
        }
    }

}
