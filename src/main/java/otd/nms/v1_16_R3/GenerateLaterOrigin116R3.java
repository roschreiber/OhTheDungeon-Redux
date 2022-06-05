package otd.nms.v1_16_R3;

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

public class GenerateLaterOrigin116R3 implements GenerateLaterOrigin {
	public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level,
			Spawnable s) {

		// Bukkit.getLogger().log(Level.SEVERE, pos.getX() + "," + pos.getY() + "," +
		// pos.getZ());

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
		org.bukkit.craftbukkit.v1_16_R3.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) tileentity
				.getWorld();
		net.minecraft.server.v1_16_R3.TileEntity te = ws.getHandle()
				.getTileEntity(new net.minecraft.server.v1_16_R3.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
		if (te == null)
			return;

		net.minecraft.server.v1_16_R3.NBTTagCompound nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
		nbt.setInt("x", pos.getX());
		nbt.setInt("y", pos.getY());
		nbt.setInt("z", pos.getZ());

		nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R3.NBTBase) s.getSpawnPotentials(rand, level));

		te.load(null, nbt);

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}

	public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
		Block tileentity = editor.getBlock(pos);
		BlockState blockState = tileentity.getState();
		if (!(blockState instanceof CreatureSpawner))
			return;
		org.bukkit.craftbukkit.v1_16_R3.CraftWorld ws = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) tileentity
				.getWorld();
		net.minecraft.server.v1_16_R3.TileEntity te = ws.getHandle()
				.getTileEntity(new net.minecraft.server.v1_16_R3.BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
		if (te == null)
			return;

		net.minecraft.server.v1_16_R3.NBTTagCompound nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
		nbt.setInt("x", pos.getX());
		nbt.setInt("y", pos.getY());
		nbt.setInt("z", pos.getZ());

		nbt.set("SpawnPotentials", (net.minecraft.server.v1_16_R3.NBTBase) s.getSpawnPotentials(rand, level));

		te.load(null, nbt);

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}
}
