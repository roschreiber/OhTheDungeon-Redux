package forge_sandbox.greymerk.roguelike.worldgen;

//import net.minecraft.world.biome.Biome;

import org.bukkit.block.Biome;

public interface IPositionInfo {

	String getDimension();

	Biome getBiome();

}
