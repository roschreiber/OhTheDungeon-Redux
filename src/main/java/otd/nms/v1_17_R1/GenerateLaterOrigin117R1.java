package otd.nms.v1_17_R1;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import otd.Main;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.nms.GenerateLaterOrigin;
import otd.world.DungeonType;

public class GenerateLaterOrigin117R1 implements GenerateLaterOrigin {
	public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level,
			Spawnable s) {
		int x = pos.getX() % 16;
		int y = pos.getY();
		int z = pos.getZ() % 16;
		if (x < 0)
			x = x + 16;
		if (z < 0)
			z = z + 16;

		Block tileentity = chunk.getBlock(x, y, z);
		BlockState blockState = tileentity.getState();

		if (!(blockState instanceof CreatureSpawner))
			return;
		org.bukkit.craftbukkit.v1_17_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_17_R1.CraftWorld) tileentity
				.getWorld();

		net.minecraft.world.level.block.entity.TileEntity te;

		te = ws.getHandle().c_(new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
		if (te == null)
			return;

		net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
		nbt.a("x", pos.getX());
		nbt.a("y", pos.getY());
		nbt.a("z", pos.getZ());
		net.minecraft.nbt.NBTBase csp = (net.minecraft.nbt.NBTBase) s.getSpawnPotentials(rand, level);
		nbt.a("SpawnPotentials", csp);

		te.a(nbt);

//		try {
//			te = (net.minecraft.world.level.block.entity.TileEntity) MultiVersion.NMS_1_17.World_getTileEntity
//					.invoke(ws.getHandle(),
//							new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
//
//			// net.minecraft.world.level.block.entity.TileEntity te =
//			// ws.getHandle().getTileEntity(
//			// new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
//			if (te == null)
//				return;
//
//			net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "x", pos.getX());
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "y", pos.getY());
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "z", pos.getZ());
//			// nbt.setInt("x", pos.getX());
//			// nbt.setInt("y", pos.getY());
//			// nbt.setInt("z", pos.getZ());
//
//			MultiVersion.NMS_1_17.NBTTagCompound_Set.invoke(nbt, "SpawnPotentials",
//					(net.minecraft.nbt.NBTBase) s.getSpawnPotentials(rand, level));
//			// nbt.set("SpawnPotentials", (net.minecraft.nbt.NBTBase)
//			// s.getSpawnPotentials(rand, level));
//
//			 MultiVersion.NMS_1_17.TileEntity_Load.invoke(te, nbt);
//			// te.load(nbt);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().info(ExceptionRepoter.exceptionToString(e));
//			}
//		}

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}

	public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
		Block tileentity = editor.getBlock(pos);
		BlockState blockState = tileentity.getState();
		if (!(blockState instanceof CreatureSpawner))
			return;
		org.bukkit.craftbukkit.v1_17_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_17_R1.CraftWorld) tileentity
				.getWorld();

		net.minecraft.world.level.block.entity.TileEntity te = ws.getHandle()
				.c_(new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
		if (te == null)
			return;

		net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
		nbt.a("x", pos.getX());
		nbt.a("y", pos.getY());
		nbt.a("z", pos.getZ());

		nbt.a("SpawnPotentials", (net.minecraft.nbt.NBTBase) s.getSpawnPotentials(rand, level));

		te.a(nbt);

//		try {
//			net.minecraft.world.level.block.entity.TileEntity te = (net.minecraft.world.level.block.entity.TileEntity) MultiVersion.NMS_1_17.World_getTileEntity
//					.invoke(ws.getHandle(),
//							new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
//			// net.minecraft.world.level.block.entity.TileEntity te =
//			// ws.getHandle().getTileEntity(
//			// new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
//			if (te == null)
//				return;
//
//			net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "x", pos.getX());
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "y", pos.getY());
//			MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(nbt, "z", pos.getZ());
//			// nbt.setInt("x", pos.getX());
//			// nbt.setInt("y", pos.getY());
//			// nbt.setInt("z", pos.getZ());
//
//			MultiVersion.NMS_1_17.NBTTagCompound_Set.invoke(nbt, "SpawnPotentials",
//					(net.minecraft.nbt.NBTBase) s.getSpawnPotentials(rand, level));
//			// nbt.set("SpawnPotentials", (net.minecraft.nbt.NBTBase)
//			// s.getSpawnPotentials(rand, level));
//
//			MultiVersion.NMS_1_17.TileEntity_Load.invoke(te, nbt);
//			// te.load(nbt);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//			}
//		}

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}
}
