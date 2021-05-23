package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.util.List;
import org.bukkit.block.Biome;
import otd.lib.BiomeDictionary;

//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;

public interface ISpawnContext {

	public boolean biomeHasType(BiomeDictionary.Type type);

	public Biome getBiome();

	public boolean includesBiome(List<Biome> biomes);

	public boolean includesBiomeType(List<BiomeDictionary.Type> biomeTypes);
	
	public String getDimension();
	
}
