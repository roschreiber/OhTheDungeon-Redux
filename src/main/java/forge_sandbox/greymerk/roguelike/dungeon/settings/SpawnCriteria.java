package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import otd.lib.BiomeDictionary;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.BiomeDictionary;

public class SpawnCriteria {

	int weight;
	List<Biome> biomes;
	List<BiomeDictionary.Type> biomeTypes;
	boolean everywhere;

	public SpawnCriteria() {
		this.weight = 1;
		this.biomes = new ArrayList<>();
		this.biomeTypes = new ArrayList<>();
		this.everywhere = false;
	}

	private static Biome nameToBiome(String name) {
		String parts[] = name.split(":");
		Biome biome;
		try {
			biome = Biome.valueOf(parts[1]);
		} catch (Exception ex) {
			biome = Biome.PLAINS;
			Bukkit.getLogger().log(Level.SEVERE, "Unknown Biome: " + name);
		}
		return biome;
	}

	public SpawnCriteria(JsonObject data) {
		this();

		this.weight = data.has("weight") ? data.get("weight").getAsInt() : 1;

		if (data.has("biomes")) {
			JsonArray biomeList = data.get("biomes").getAsJsonArray();
			this.biomes = new ArrayList<>();
			for (JsonElement e : biomeList) {
				String name = e.getAsString();
				this.biomes.add(nameToBiome(name));
			}
		}

		if (data.has("biomeTypes")) {
			JsonArray biomeTypeList = data.get("biomeTypes").getAsJsonArray();
			this.biomeTypes = new ArrayList<BiomeDictionary.Type>();
			for (JsonElement e : biomeTypeList) {
				String type = e.getAsString().toUpperCase();
				BiomeDictionary.Type t = BiomeDictionary.Type.getType(type, new BiomeDictionary.Type[0]);
				if (BiomeDictionary.getBiomes(t).size() > 0)
					this.biomeTypes.add(t);
			}
		}

		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty();
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

//	public void setbiomes(List<ResourceLocation> biomes){
//		this.biomes = biomes;
//		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty();
//	}

	public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes) {
		this.biomeTypes = biomeTypes;
		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty();
	}

	public boolean isValid(ISpawnContext context) {

		if (this.everywhere)
			return true;

		boolean biomeFound = false;

		if (this.biomes != null)
			biomeFound = context.includesBiome(biomes);

		if (this.biomeTypes != null)
			biomeFound = context.includesBiomeType(this.biomeTypes);

		return biomeFound;
	}

	public static boolean isValidDimension(String dim, List<String> wl, List<String> bl) {
		if (bl.contains(dim))
			return false;
		if (wl.isEmpty())
			return true;
		return wl.contains(dim);
	}

}