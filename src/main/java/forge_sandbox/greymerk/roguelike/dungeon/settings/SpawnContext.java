package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.util.List;

import forge_sandbox.greymerk.roguelike.worldgen.IPositionInfo;
import org.bukkit.block.Biome;
import otd.lib.BiomeDictionary;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;

public class SpawnContext implements ISpawnContext {

	private IPositionInfo info;

	public SpawnContext(IPositionInfo info) {
		this.info = info;
	}

	@Override
	public boolean biomeHasType(BiomeDictionary.Type type) {
		return BiomeDictionary.hasType(info.getBiome(), type);
	}

	@Override
	public Biome getBiome() {
		return info.getBiome();
	}

	@Override
	public boolean includesBiome(List<Biome> biomeNames) {
		return biomeNames.contains(info.getBiome());
	}

	@Override
	public boolean includesBiomeType(List<BiomeDictionary.Type> biomeTypes) {

		for (BiomeDictionary.Type type : biomeTypes) {
			if (biomeHasType(type))
				return true;
		}

		return false;
	}

	@Override
	public String getDimension() {
		return this.info.getDimension();
	}

}
