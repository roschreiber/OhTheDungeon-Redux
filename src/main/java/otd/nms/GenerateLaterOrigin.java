package otd.nms;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import net.minecraft.nbt.Tag;
import otd.Main;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.world.DungeonType;

public class GenerateLaterOrigin {
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

		org.bukkit.craftbukkit.v1_21_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_21_R1.CraftWorld) tileentity
				.getWorld();

		net.minecraft.world.level.block.entity.BlockEntity te = ws.getHandle()
				.getBlockEntity(new net.minecraft.core.BlockPos(pos.getX(), pos.getY(), pos.getZ()));

		if (te == null)
			return;
		net.minecraft.nbt.CompoundTag nbt = new net.minecraft.nbt.CompoundTag();
		nbt.putInt("x", pos.getX());
		nbt.putInt("y", pos.getY());
		nbt.putInt("z", pos.getZ());

		net.minecraft.nbt.Tag base = (net.minecraft.nbt.Tag) s.getSpawnPotentials(rand, level);
		if (base != null) {
			nbt.put("SpawnPotentials", (Tag) base);
		}

		//te.a(nbt);
		te.loadWithComponents(nbt, ws.getHandle().getLevel().registryAccess());

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}

	public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
		Block tileentity = editor.getBlock(pos);
		BlockState blockState = tileentity.getState();
		if (!(blockState instanceof CreatureSpawner))
			return;
		org.bukkit.craftbukkit.v1_21_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_21_R1.CraftWorld) tileentity
				.getWorld();

		net.minecraft.world.level.block.entity.BlockEntity te = ws.getHandle()
				.getBlockEntity(new net.minecraft.core.BlockPos(pos.getX(), pos.getY(), pos.getZ()));
		if (te == null)
			return;
		net.minecraft.nbt.CompoundTag nbt = new net.minecraft.nbt.CompoundTag();
		nbt.putInt("x", pos.getX());
		nbt.putInt("y", pos.getY());
		nbt.putInt("z", pos.getZ());

		net.minecraft.nbt.Tag base = (net.minecraft.nbt.Tag) s.getSpawnPotentials(rand, level);
		if (base != null)
			nbt.put("SpawnPotentials", (Tag) base);

		//te.a(nbt);
		te.loadWithComponents(nbt, ws.getHandle().getLevel().registryAccess());

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}
}
