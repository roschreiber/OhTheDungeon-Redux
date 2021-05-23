package forge_sandbox.greymerk.roguelike.worldgen.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import otd.Main;
import otd.MultiVersion;
import otd.lib.ZoneWorld;
import otd.lib.api.SpawnerApi;
import otd.lib.api.SpawnerDecryAPI;
import otd.lib.async.later.roguelike.Spawnable_Later;
//import net.minecraft.init.Blocks;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.tileentity.MobSpawnerBaseLogic;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityMobSpawner;

public class Spawnable {

	public Spawner type;
	private final List<SpawnPotential> potentials;
        
        public static SpawnerApi.SpawnerHandler handler = null;
        
        static {
            ZoneWorld.registerSpecialBlock(Material.SPAWNER);
        }
		
	public Spawnable(Spawner type){
		this.potentials = new ArrayList<>();
		this.type = type;
	}
	
	public Spawnable(JsonElement data) throws Exception{
		this.potentials = new ArrayList<>();
		
		JsonArray arr = data.getAsJsonArray();
		for(JsonElement e : arr){
			SpawnPotential potential = new SpawnPotential(e.getAsJsonObject());
			this.potentials.add(potential);
		}
	}
        
        public void generate_later_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
            if(handler != null) {
                handler.generate(this, pos, editor, rand, cursor, level);
            } else {
                this.generate_later_orign_chunk(chunk, pos, editor, rand, cursor, level);
            }
        }
        
        public void generate_later(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
            if(handler != null) {
                handler.generate(this, pos, editor, rand, cursor, level);
            } else {
                this.generate_later_orign(pos, editor, rand, cursor, level);
            }
        }
        
        private static class GenerateLaterOrigin114 {
            public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                Block tileentity = editor.getBlock(pos);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_14_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_14_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_14_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_14_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_14_R1.NBTTagCompound nbt = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_14_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
            
            public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                
                int x = pos.getX() % 16;
                int y = pos.getY();
                int z = pos.getZ() % 16;
                if(x < 0) x = x + 16;
                if(z < 0) z = z + 16;  
            
            	Block tileentity = chunk.getBlock(x, y, z);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_14_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_14_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_14_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_14_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_14_R1.NBTTagCompound nbt = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_14_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
        }
        
        private static class GenerateLaterOrigin115 {
            public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                
                int x = pos.getX() % 16;
                int y = pos.getY();
                int z = pos.getZ() % 16;
                if(x < 0) x = x + 16;
                if(z < 0) z = z + 16;  
            
            	Block tileentity = chunk.getBlock(x, y, z);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_15_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_15_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_15_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_15_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_15_R1.NBTTagCompound nbt = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_15_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
            
            public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                Block tileentity = editor.getBlock(pos);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_15_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_15_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_15_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_15_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_15_R1.NBTTagCompound nbt = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_15_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
        }
        
        private static class GenerateLaterOrigin116 {
            public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                
//                Bukkit.getLogger().log(Level.SEVERE, pos.getX() + "," + pos.getY() + "," + pos.getZ());
                
                int x = pos.getX() % 16;
                int y = pos.getY();
                int z = pos.getZ() % 16;
                if(x < 0) x = x + 16;
                if(z < 0) z = z + 16;  
            
            	Block tileentity = chunk.getBlock(x, y, z);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R1.NBTTagCompound nbt = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
            
            public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                Block tileentity = editor.getBlock(pos);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R1.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R1.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R1.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R1.NBTTagCompound nbt = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R1.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
        }
        
        private static class GenerateLaterOrigin116R2 {
            public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                
//                Bukkit.getLogger().log(Level.SEVERE, pos.getX() + "," + pos.getY() + "," + pos.getZ());
                
                int x = pos.getX() % 16;
                int y = pos.getY();
                int z = pos.getZ() % 16;
                if(x < 0) x = x + 16;
                if(z < 0) z = z + 16;  
            
            	Block tileentity = chunk.getBlock(x, y, z);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R2.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R2.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R2.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R2.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R2.NBTTagCompound nbt = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R2.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
            
            public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                Block tileentity = editor.getBlock(pos);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R2.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R2.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R2.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R2.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R2.NBTTagCompound nbt = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R2.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
        }
        
        private static class GenerateLaterOrigin116R3 {
            public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                
//                Bukkit.getLogger().log(Level.SEVERE, pos.getX() + "," + pos.getY() + "," + pos.getZ());
                
                int x = pos.getX() % 16;
                int y = pos.getY();
                int z = pos.getZ() % 16;
                if(x < 0) x = x + 16;
                if(z < 0) z = z + 16;  
            
            	Block tileentity = chunk.getBlock(x, y, z);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R3.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R3.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R3.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R3.NBTTagCompound nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R3.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
            
            public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
                Block tileentity = editor.getBlock(pos);
                BlockState blockState = tileentity.getState();
                if (!(blockState instanceof CreatureSpawner)) return;
                org.bukkit.craftbukkit.v1_16_R3.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) 
                        tileentity.getWorld();
                net.minecraft.server.v1_16_R3.TileEntity te = 
                        ws.getHandle().getTileEntity(
                                new net.minecraft.server.v1_16_R3.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                if(te == null) return;

                net.minecraft.server.v1_16_R3.NBTTagCompound nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                nbt.setInt("x", pos.getX());
                nbt.setInt("y", pos.getY());
                nbt.setInt("z", pos.getZ());

                nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R3.NBTBase) s.getSpawnPotentials(rand, level));

                te.load(null, nbt);
                
                SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance);
            }
        }
        
        public void generate_later_orign(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                (new GenerateLaterOrigin114()).generate(pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                (new GenerateLaterOrigin115()).generate(pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                (new GenerateLaterOrigin116()).generate(pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                (new GenerateLaterOrigin116R2()).generate(pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                (new GenerateLaterOrigin116R3()).generate(pos, editor, rand, cursor, level, this);
            }
        }
        
        public void generate_later_orign_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                (new GenerateLaterOrigin114()).generate_chunk(chunk, pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                (new GenerateLaterOrigin115()).generate_chunk(chunk, pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                (new GenerateLaterOrigin116()).generate_chunk(chunk, pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                (new GenerateLaterOrigin116R2()).generate_chunk(chunk, pos, editor, rand, cursor, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                (new GenerateLaterOrigin116R3()).generate_chunk(chunk, pos, editor, rand, cursor, level, this);
            }
        }
        
		
	public void generate(IWorldEditor editor, Random rand, Coord cursor, int level){
		Coord pos = new Coord(cursor);
		editor.setBlock(pos, new MetaBlock(Material.SPAWNER), true, true);
		
                
                if(!editor.isFakeWorld()) generate_later(pos, editor, rand, cursor, level);
                else editor.addLater(new Spawnable_Later(this, pos, editor, rand, cursor, level));
	}
        
        private static class GetSpawnPotentials114 {
            public Object get(Random rand, int level, Spawnable s) {
                if(s.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}
		
		net.minecraft.server.v1_14_R1.NBTTagList potentials = 
                        new net.minecraft.server.v1_14_R1.NBTTagList();
		
		for(SpawnPotential potential : s.potentials){
			net.minecraft.server.v1_14_R1.NBTTagCompound nbt = 
                                (net.minecraft.server.v1_14_R1.NBTTagCompound) potential.getNBTTagCompound(level);
			potentials.add(nbt);
		}
		
		return potentials;
            }
        }
        
        private static class GetSpawnPotentials115 {
            public Object get(Random rand, int level, Spawnable s) {
                if(s.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}
		
		net.minecraft.server.v1_15_R1.NBTTagList potentials = 
                        new net.minecraft.server.v1_15_R1.NBTTagList();
		
		for(SpawnPotential potential : s.potentials){
			net.minecraft.server.v1_15_R1.NBTTagCompound nbt = 
                                (net.minecraft.server.v1_15_R1.NBTTagCompound) potential.getNBTTagCompound(level);
			potentials.add(nbt);
		}
		
		return potentials;
            }
        }
        
        private static class GetSpawnPotentials116 {
            public Object get(Random rand, int level, Spawnable s) {
                if(s.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}
		
		net.minecraft.server.v1_16_R1.NBTTagList potentials = 
                        new net.minecraft.server.v1_16_R1.NBTTagList();
		
		for(SpawnPotential potential : s.potentials){
			net.minecraft.server.v1_16_R1.NBTTagCompound nbt = 
                                (net.minecraft.server.v1_16_R1.NBTTagCompound) potential.getNBTTagCompound(level);
			potentials.add(nbt);
		}
		
		return potentials;
            }
        }
        
        private static class GetSpawnPotentials116R2 {
            public Object get(Random rand, int level, Spawnable s) {
                if(s.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}
		
		net.minecraft.server.v1_16_R2.NBTTagList potentials = 
                        new net.minecraft.server.v1_16_R2.NBTTagList();
		
		for(SpawnPotential potential : s.potentials){
			net.minecraft.server.v1_16_R2.NBTTagCompound nbt = 
                                (net.minecraft.server.v1_16_R2.NBTTagCompound) potential.getNBTTagCompound(level);
			potentials.add(nbt);
		}
		
		return potentials;
            }
        }
        
        private static class GetSpawnPotentials116R3 {
            public Object get(Random rand, int level, Spawnable s) {
                if(s.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}
		
		net.minecraft.server.v1_16_R3.NBTTagList potentials = 
                        new net.minecraft.server.v1_16_R3.NBTTagList();
		
		for(SpawnPotential potential : s.potentials){
			net.minecraft.server.v1_16_R3.NBTTagCompound nbt = 
                                (net.minecraft.server.v1_16_R3.NBTTagCompound) potential.getNBTTagCompound(level);
			potentials.add(nbt);
		}
		
		return potentials;
            }
        }
        
	private Object getSpawnPotentials(Random rand, int level){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
		return (new GetSpawnPotentials114()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
		return (new GetSpawnPotentials115()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
		return (new GetSpawnPotentials116()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
		return (new GetSpawnPotentials116R2()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
		return (new GetSpawnPotentials116R3()).get(rand, level, this);
            }
            return null;
	}
}
