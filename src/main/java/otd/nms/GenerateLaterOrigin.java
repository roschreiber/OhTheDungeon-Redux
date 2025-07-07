package otd.nms;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTTileEntity;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
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

		NBTTileEntity nbt = new NBTTileEntity(blockState);
		net.minecraft.nbt.Tag base = (net.minecraft.nbt.Tag) s.getSpawnPotentials(rand, level);

		if (base != null) {
			net.minecraft.nbt.CompoundTag temp = new net.minecraft.nbt.CompoundTag();
			temp.put("SpawnPotentials", base);
			nbt.mergeCompound(new NBTContainer(temp));
		}

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}

	public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s) {
		Block tileentity = editor.getBlock(pos);
		BlockState blockState = tileentity.getState();
		if (!(blockState instanceof CreatureSpawner))
			return;

		NBTTileEntity nbt = new NBTTileEntity(blockState);
		net.minecraft.nbt.Tag base = (net.minecraft.nbt.Tag) s.getSpawnPotentials(rand, level);

		if (base != null) {
			net.minecraft.nbt.CompoundTag temp = new net.minecraft.nbt.CompoundTag();
			temp.put("SpawnPotentials", base);
			nbt.mergeCompound(new NBTContainer(temp));
		}

		SpawnerDecryAPI.setSpawnerDecry(tileentity, Main.instance, DungeonType.Roguelike, false);
	}
}
