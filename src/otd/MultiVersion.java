/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

/**
 *
 * @author
 */
public class MultiVersion {
    public static enum Version {
        V1_17_R1,
        V1_16_R3,
        V1_16_R2,
        V1_16_R1,
        V1_15_R1,
        V1_14_R1,
        UNKNOWN,
    };
    
//    public static class JsonToNBTTest {
//        public static boolean result = false;
//        public static Class NBTTagCompound;
//        public static Class NBTBase;
//        public static Class NBTTagList;
//    }
    
    public static class SpawnableTest {
        public static boolean result = false;
        public static Class CraftWorld;
        public static Class TileEntity;
        public static Class WorldServer;
        public static Method getHandle;
        public static Method getTileEntity;
        public static Class BlockPosition;
        public static Constructor NewBlockPosition;
        public static Class NBTTagCompound;
        public static Method load;
    }
    
    static {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            SpawnableTest.CraftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            SpawnableTest.TileEntity = Class.forName("net.minecraft.world.level.block.entity.TileEntity");
            SpawnableTest.WorldServer = Class.forName("net.minecraft.server.level.WorldServer");
            SpawnableTest.getHandle = SpawnableTest.CraftWorld.getMethod("getHandle");
            SpawnableTest.BlockPosition = Class.forName("net.minecraft.core.BlockPosition");
            SpawnableTest.NewBlockPosition = SpawnableTest.BlockPosition.getConstructor(int.class, int.class, int.class);
            SpawnableTest.NewBlockPosition.newInstance(0, 0, 0);
            SpawnableTest.getTileEntity = SpawnableTest.WorldServer.getMethod("getTileEntity", SpawnableTest.BlockPosition);
            SpawnableTest.NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");
            SpawnableTest.load = SpawnableTest.TileEntity.getMethod("load", SpawnableTest.NBTTagCompound);
            SpawnableTest.result = true;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException 
                | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            SpawnableTest.result = false;
        }
    }
    
    public static class SpawnPotentialTest {
        public static boolean result = false;
        public static Class NBTTagCompound;
        public static Class NBTBase;
        public static Class NBTTagList;
    }
    
    static {
        try {
            SpawnPotentialTest.NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");
            SpawnPotentialTest.NBTTagCompound.getConstructor().newInstance();
            SpawnPotentialTest.NBTBase = Class.forName("net.minecraft.nbt.NBTBase");
            SpawnPotentialTest.NBTTagList = Class.forName("net.minecraft.nbt.NBTTagList");
            SpawnPotentialTest.NBTTagList.getConstructor().newInstance();
            
            SpawnPotentialTest.NBTTagCompound.getMethod("set", String.class, SpawnPotentialTest.NBTBase);
            SpawnPotentialTest.NBTTagCompound.getMethod("setInt", String.class, int.class);
            SpawnPotentialTest.NBTTagCompound.getMethod("setString", String.class, String.class);
            SpawnPotentialTest.NBTTagCompound.getMethod("setByte", String.class, byte.class);
            SpawnPotentialTest.result = true;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | 
                InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            SpawnPotentialTest.result = false;
        }
    }
    
    public static class WeightedRandomLootTest {
        public static boolean result = false;
        public static Class ItemStack;
        public static Class NBTTagCompound;
        public static Method setTag;
        public static Class CraftItemStack;
        public static Method asBukkitCopy;
        public static Method asNMSCopy;
    }
    
    static {
        try {
            WeightedRandomLootTest.ItemStack = Class.forName("net.minecraft.world.item.ItemStack");
            WeightedRandomLootTest.NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");
            WeightedRandomLootTest.NBTTagCompound.getConstructor().newInstance();
            WeightedRandomLootTest.setTag = WeightedRandomLootTest.ItemStack.getMethod("setTag", WeightedRandomLootTest.NBTTagCompound);
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            WeightedRandomLootTest.CraftItemStack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            WeightedRandomLootTest.asBukkitCopy = WeightedRandomLootTest.CraftItemStack.getMethod("asBukkitCopy", WeightedRandomLootTest.ItemStack);
            WeightedRandomLootTest.asNMSCopy = WeightedRandomLootTest.CraftItemStack.getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class);
            WeightedRandomLootTest.result = true;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | 
                InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            WeightedRandomLootTest.result = false;
        }
    }
    
    private static EntityType PIGZOMBIE = null;
    
    public static EntityType getPigZombieForUnknownVersion() {
        if(PIGZOMBIE == null) {
            try {
                PIGZOMBIE = EntityType.valueOf("ZOMBIFIED_PIGLIN");
            } catch (IllegalArgumentException ex) {
                PIGZOMBIE = EntityType.valueOf("PIG_ZOMBIE");
            }
        }
        return PIGZOMBIE;
    }
    
    public static void checkForUnknownVersion() {
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Unknown Minecraft Version", ChatColor.GREEN);
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] OTD is now running in compatibility mode", ChatColor.GREEN);
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Compatibility Mode List:", ChatColor.GREEN);
        String tmp;
        tmp = forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable.class.getName();
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] {1} : {2}", new Object[]{ChatColor.GREEN, tmp, SpawnableTest.result ? "Good" : "Bad"});
        tmp = forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential.class.getName();
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] {1} : {2}", new Object[]{ChatColor.GREEN, tmp, SpawnPotentialTest.result ? "Good" : "Bad"});
        tmp = forge_sandbox.greymerk.roguelike.treasure.loot.WeightedRandomLoot.class.getName();
        Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] {1} : {2}", new Object[]{ChatColor.GREEN, tmp, WeightedRandomLootTest.result ? "Good" : "Bad"});
        
        if(!SpawnableTest.result || !SpawnPotentialTest.result || !WeightedRandomLootTest.result) {
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Some tests are failed. Don't worry, OTD could still work", ChatColor.GREEN);
        } else {
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] All tests are passed. OTD could work with all functions", ChatColor.GREEN);
        }
    }
    
    public static boolean is117R1() {
        try {
            Class clazz = Class.forName("org.bukkit.craftbukkit.v1_17_R1.CraftWorld");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
    
    public static boolean is116R3() {
        try {
            Class clazz = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
    public static boolean is116R2() {
        try {
            Class clazz = Class.forName("net.minecraft.server.v1_16_R2.NBTTagCompound");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
    public static boolean is116R1() {
        try {
            Class clazz = Class.forName("net.minecraft.server.v1_16_R1.NBTTagCompound");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
    public static boolean is115() {
        try {
            Class clazz = Class.forName("net.minecraft.server.v1_15_R1.NBTTagCompound");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
    public static boolean is114() {
        try {
            Class clazz = Class.forName("net.minecraft.server.v1_14_R1.NBTTagCompound");
            return clazz != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
