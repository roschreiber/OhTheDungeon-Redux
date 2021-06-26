package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.Slot;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import otd.Main;
import otd.MultiVersion;

public class ItemArmour extends ItemBase {

	private Equipment type;
	private boolean enchant;
	private Quality quality;
	
	public ItemArmour(int weight, int level) {
		super(weight, level);
	}
	
	public ItemArmour(JsonObject data, int weight) throws Exception{
		super(weight);
		
		this.enchant = data.has("ench") ? data.get("ench").getAsBoolean() : true;
		if(!data.has("level")) throw new Exception("Armour requires a level");
		this.level = data.get("level").getAsInt();
		
		if(data.has("equipment")){
			try{
				this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
			} catch(Exception e) {
				throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
			}
		}
		
		if(data.has("quality")){
			this.level = data.get("level").getAsInt();
			
			try{
				this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
			} catch(Exception e){
				throw new Exception("No such Quality as: " + data.get("quality").getAsString());
			}	
		}
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(type != null || quality != null) return get(rand, level, quality, type, enchant);
		return getRandom(rand, level, true);
	}

	public static ItemStack get(Random rand, int level, Quality quality, Equipment type, boolean enchant){
		ItemStack tool = Equipment.get(type, quality == null ? Quality.get(level) : quality);
		return enchant ? Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level)) : tool;
	}
	
	public static ItemStack getRandom(Random rand, int level, boolean enchant){
		return getRandom(rand, level,
				Slot.getSlotByNumber(rand.nextInt(4) + 1),
				enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant){
		return getRandom(rand, level, slot, enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack getRandom(Random rand, int level, Slot slot, int enchantLevel){

		if(enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0){
			switch(slot){
			case HEAD: return ItemSpecialty.getRandomItem(Equipment.HELMET, rand, level); 
			case CHEST: return ItemSpecialty.getRandomItem(Equipment.CHEST, rand, level); 
			case LEGS: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, level); 
			case FEET: return ItemSpecialty.getRandomItem(Equipment.FEET, rand, level);
			}
		}

		ItemStack item = get(rand, slot, Quality.getArmourQuality(rand, level));
		
		if(enchantLevel > 0) Enchant.enchantItem(rand, item, enchantLevel);
		
		return item;
		
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack get(Random rand, Slot slot, Quality quality) {
		
		switch(slot){
		
		case HEAD:
			switch (quality) {

			case DIAMOND: return new ItemStack(Material.DIAMOND_HELMET);
			case GOLD: return new ItemStack(Material.GOLDEN_HELMET);
			case IRON: return new ItemStack(Material.IRON_HELMET);
			case STONE: return new ItemStack(Material.CHAINMAIL_HELMET);
			default:
				ItemStack item = new ItemStack(Material.LEATHER_HELMET);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case FEET:
			switch (quality) {

			case DIAMOND: return new ItemStack(Material.DIAMOND_BOOTS);
			case GOLD: return new ItemStack(Material.GOLDEN_BOOTS);
			case IRON: return new ItemStack(Material.IRON_BOOTS);
			case STONE: return new ItemStack(Material.CHAINMAIL_BOOTS);
			default:
				ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case CHEST:
			switch (quality) {

			case DIAMOND: return new ItemStack(Material.DIAMOND_CHESTPLATE);
			case GOLD: return new ItemStack(Material.GOLDEN_CHESTPLATE);
			case IRON: return new ItemStack(Material.IRON_CHESTPLATE);
			case STONE: return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			default:
				ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		case LEGS:
			switch (quality) {
	
			case DIAMOND: return new ItemStack(Material.DIAMOND_LEGGINGS);
			case GOLD: return new ItemStack(Material.GOLDEN_LEGGINGS);
			case IRON: return new ItemStack(Material.IRON_LEGGINGS);
			case STONE: return new ItemStack(Material.CHAINMAIL_LEGGINGS);
			default:
				ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		}
		return null;
	}
        
        private static class DyeArmor114 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.server.v1_14_R1.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.server.v1_14_R1.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.server.v1_14_R1.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmor115 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.server.v1_15_R1.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.server.v1_15_R1.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.server.v1_15_R1.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmor116 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.server.v1_16_R1.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.server.v1_16_R1.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.server.v1_16_R1.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmor116R2 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.server.v1_16_R2.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.server.v1_16_R2.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.server.v1_16_R2.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmor116R3 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.server.v1_16_R3.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.server.v1_16_R3.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.server.v1_16_R3.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmor117R1 {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                int color = r << 16 | g << 8 | b;
                net.minecraft.world.item.ItemStack ca = 
                        org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(armor);
                net.minecraft.nbt.NBTTagCompound nbtdata = ca.getTag();

                if (nbtdata == null)
                {
                    nbtdata = new net.minecraft.nbt.NBTTagCompound();
                    ca.setTag(nbtdata);
                    armor = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asBukkitCopy(ca);
                }

                net.minecraft.nbt.NBTTagCompound nbtDisplay = nbtdata.getCompound("display");

                if (!nbtdata.hasKey("display"))
                {
                    nbtdata.set("display", nbtDisplay);
                }

                nbtDisplay.setInt("color", color);
                return armor;
            }
        }
        
        private static class DyeArmorUnknown {
            public ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
                
                ItemMeta im = armor.getItemMeta();
                if(!(im instanceof LeatherArmorMeta)) return armor;
                Color color = Color.fromBGR(b, g, r);
                LeatherArmorMeta lam = (LeatherArmorMeta) im;
                lam.setColor(color);
                armor.setItemMeta(lam);
                return armor;
            }
        }

	
	public static ItemStack dyeArmor(ItemStack armor, int r, int g, int b) {
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                DyeArmor114 da = new DyeArmor114();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                DyeArmor115 da = new DyeArmor115();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                DyeArmor116 da = new DyeArmor116();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                DyeArmor116R2 da = new DyeArmor116R2();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                DyeArmor116R3 da = new DyeArmor116R3();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                DyeArmor117R1 da = new DyeArmor117R1();
                armor = da.dyeArmor(armor, r, g, b);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
                DyeArmorUnknown da = new DyeArmorUnknown();
                armor = da.dyeArmor(armor, r, g, b);
            }
            return armor;
	}


}
