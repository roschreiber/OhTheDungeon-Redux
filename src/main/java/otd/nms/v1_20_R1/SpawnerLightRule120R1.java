package otd.nms.v1_20_R1;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import otd.nms.SpawnerLightRule;

public class SpawnerLightRule120R1 implements SpawnerLightRule {
	public void update(Block tileentity, JavaPlugin plugin) {
		org.bukkit.craftbukkit.v1_20_R1.CraftWorld ws = (org.bukkit.craftbukkit.v1_20_R1.CraftWorld) tileentity
				.getWorld();
		net.minecraft.world.level.block.entity.TileEntity te;
		Location pos = tileentity.getLocation();

		te = (net.minecraft.world.level.block.entity.TileEntity) ws.getHandle()
				.c_(new net.minecraft.core.BlockPosition(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ()));

		if (te == null)
			return;

		net.minecraft.nbt.NBTTagCompound nbt = te.o();
		if (nbt.e("SpawnData")) {
			net.minecraft.nbt.NBTTagCompound spawnData = (net.minecraft.nbt.NBTTagCompound) nbt.c("SpawnData");
			net.minecraft.nbt.NBTTagCompound custom_spawn_rules = new net.minecraft.nbt.NBTTagCompound();
			net.minecraft.nbt.NBTTagCompound sky_light_limit = new net.minecraft.nbt.NBTTagCompound();
			sky_light_limit.a("min_inclusive", 0);
			sky_light_limit.a("max_exclusive", 15);
			net.minecraft.nbt.NBTTagCompound block_light_limit = new net.minecraft.nbt.NBTTagCompound();
			block_light_limit.a("min_inclusive", 0);
			block_light_limit.a("max_exclusive", 15);
			custom_spawn_rules.a("sky_light_limit", sky_light_limit);
			custom_spawn_rules.a("block_light_limit", block_light_limit);
			spawnData.a("custom_spawn_rules", custom_spawn_rules);
			nbt.a("SpawnData", spawnData);
		}
		if (nbt.e("SpawnPotentials")) {
			net.minecraft.nbt.NBTTagList spawnPotentials = (net.minecraft.nbt.NBTTagList) nbt.c("SpawnPotentials");
			net.minecraft.nbt.NBTTagList newList = new net.minecraft.nbt.NBTTagList();
			for (net.minecraft.nbt.NBTBase base : spawnPotentials) {
				net.minecraft.nbt.NBTTagCompound node = (net.minecraft.nbt.NBTTagCompound) base;
				if (node.e("data")) {
					net.minecraft.nbt.NBTTagCompound data = (net.minecraft.nbt.NBTTagCompound) node.c("data");
					net.minecraft.nbt.NBTTagCompound custom_spawn_rules = new net.minecraft.nbt.NBTTagCompound();
					net.minecraft.nbt.NBTTagCompound sky_light_limit = new net.minecraft.nbt.NBTTagCompound();
					sky_light_limit.a("min_inclusive", 0);
					sky_light_limit.a("max_exclusive", 15);
					net.minecraft.nbt.NBTTagCompound block_light_limit = new net.minecraft.nbt.NBTTagCompound();
					block_light_limit.a("min_inclusive", 0);
					block_light_limit.a("max_exclusive", 15);
					custom_spawn_rules.a("sky_light_limit", sky_light_limit);
					custom_spawn_rules.a("block_light_limit", block_light_limit);
					data.a("custom_spawn_rules", custom_spawn_rules);
					node.a("data", data);
				}
				newList.add(node);
			}
			nbt.a("SpawnPotentials", newList);
		}
		te.a(nbt);
	}
}
