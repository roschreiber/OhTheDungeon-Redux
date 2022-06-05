package forge_sandbox.greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import com.google.gson.JsonObject;

import otd.MultiVersion;
import otd.util.nbt.JsonToNBT;

public class SpawnPotential {

	public String name;
	public int weight;
	public boolean equip;
	Object nbt;

	public SpawnPotential(String name) {
		this(name, 1);
	}

	public SpawnPotential(String name, int weight) {
		this(name, true, weight, null);
	}

	public SpawnPotential(String name, boolean equip, int weight) {
		this(name, equip, weight, null);
	}

	public SpawnPotential(String name, boolean equip, int weight, Object nbt) {
		this.name = name;
		this.equip = equip;
		this.weight = weight;
		this.nbt = nbt;
	}

	public SpawnPotential(JsonObject entry) throws Exception {
		this.weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
		if (!entry.has("name")) {
			throw new Exception("Spawn potential missing name");
		}

		this.name = entry.get("name").getAsString();
		this.equip = entry.has("equip") ? entry.get("equip").getAsBoolean() : false;

		if (entry.has("nbt")) {
			String metadata = entry.get("nbt").getAsString();
			this.nbt = JsonToNBT.getTagFromJson(metadata);
		}
	}

	public Object getNBTTagCompound(int level) {
		return MultiVersion.getNBTTagCompound == null ? null
				: MultiVersion.getNBTTagCompound.get(level, name, this.nbt, this);
	}

	public Object getNBTTagList(Random rand, int level) {
		return MultiVersion.getNBTTagList == null ? null : MultiVersion.getNBTTagList.get(rand, level, this);
	}

	public Object getPotential(Object mob) {
		return MultiVersion.getPotential == null ? null : MultiVersion.getPotential.get(mob, this);
	}

	public Object equipHands(Object mob, String weapon, String offhand) {
		return MultiVersion.equipHands == null ? mob : MultiVersion.equipHands.get(mob, weapon, offhand, this);
	}

	public Object equipArmour(Object mob, Random rand, int level) {
		return MultiVersion.equipArmour == null ? mob : MultiVersion.equipArmour.get(mob, rand, level, this);
	}

	public Object getItem(String itemName) {
		return MultiVersion.getItem == null ? null : MultiVersion.getItem.get(itemName);
	}

	public Object getRoguelike(int level, String type, Object otag) {
		return MultiVersion.getRoguelike == null ? otag : MultiVersion.getRoguelike.get(level, type, otag, this);
	}

}
