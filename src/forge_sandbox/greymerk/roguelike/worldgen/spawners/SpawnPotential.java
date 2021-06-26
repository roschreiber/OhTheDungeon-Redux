package forge_sandbox.greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import otd.Main;
import otd.MultiVersion;
import otd.config.WorldConfig;
import otd.util.nbt.JsonToNBT;

public class SpawnPotential {
	
	String name;
	int weight;
	boolean equip;
	Object nbt;
	
	
	public SpawnPotential(String name){
		this(name, 1);
	}
	
	public SpawnPotential(String name, int weight){
		this(name, true, weight, null);
	}
	
	public SpawnPotential(String name, boolean equip, int weight){
		this(name, equip, weight, null);
	}
	
	public SpawnPotential(String name, boolean equip, int weight, Object nbt){
		this.name = name;
		this.equip = equip;
		this.weight = weight;
		this.nbt = nbt;
	}
	
	public SpawnPotential(JsonObject entry) throws Exception{
		this.weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
		if(!entry.has("name")){
			throw new Exception("Spawn potential missing name");
		}
		
		this.name = entry.get("name").getAsString();
		this.equip = entry.has("equip") ? entry.get("equip").getAsBoolean() : false;
		
		if(entry.has("nbt")){
			String metadata = entry.get("nbt").getAsString();
			this.nbt = JsonToNBT.getTagFromJson(metadata);
		}
	}
        
        private static class GetNBTTagCompound114 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.server.v1_14_R1.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
	
        private static class GetNBTTagCompound115 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.server.v1_15_R1.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
        
        private static class GetNBTTagCompound116 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.server.v1_16_R1.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
        
        private static class GetNBTTagCompound116R2 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.server.v1_16_R2.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
        
        private static class GetNBTTagCompound116R3 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.server.v1_16_R3.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
        
        private static class GetNBTTagCompound117R1 {
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                Object nbt;
                if(inbt == null) {
                    nbt = new net.minecraft.nbt.NBTTagCompound();
                } else {
                    nbt = ((net.minecraft.nbt.NBTTagCompound) inbt).clone();
                }
                return sp.getPotential(sp.getRoguelike(level, name, nbt));
            }
        }
        
        private static class GetNBTTagCompoundUnknown {
            
            private static class NBT {
                private Object getWithNBT(int level, String name, Object inbt, SpawnPotential sp) {
                    Object nbt;
                    if(inbt == null) {
                        nbt = new net.minecraft.nbt.NBTTagCompound();
                    } else {
                        nbt = ((net.minecraft.nbt.NBTTagCompound) inbt).clone();
                    }
                    return sp.getPotential(sp.getRoguelike(level, name, nbt));
                }
            }
            
            public Object get(int level, String name, Object inbt, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return null;
                return (new NBT()).getWithNBT(level, name, inbt, sp);
            }
        }

	public Object getNBTTagCompound(int level){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                return (new GetNBTTagCompound114()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                return (new GetNBTTagCompound115()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                return (new GetNBTTagCompound116()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                return (new GetNBTTagCompound116R2()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                return (new GetNBTTagCompound116R3()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                return (new GetNBTTagCompound117R1()).get(level, name, this.nbt, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
                return (new GetNBTTagCompoundUnknown()).get(level, name, this.nbt, this);
            }
            return null;
	}
        
        private static class GetNBTTagList114 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_14_R1.NBTTagList potentials = new net.minecraft.server.v1_14_R1.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.server.v1_14_R1.NBTTagCompound mob = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.server.v1_14_R1.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.server.v1_14_R1.NBTTagCompound mob = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.server.v1_14_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.server.v1_14_R1.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.server.v1_14_R1.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_14_R1.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagList115 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_15_R1.NBTTagList potentials = new net.minecraft.server.v1_15_R1.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.server.v1_15_R1.NBTTagCompound mob = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.server.v1_15_R1.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.server.v1_15_R1.NBTTagCompound mob = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.server.v1_15_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.server.v1_15_R1.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.server.v1_15_R1.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_15_R1.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagList116 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R1.NBTTagList potentials = new net.minecraft.server.v1_16_R1.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.server.v1_16_R1.NBTTagCompound mob = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.server.v1_16_R1.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.server.v1_16_R1.NBTTagCompound mob = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.server.v1_16_R1.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.server.v1_16_R1.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.server.v1_16_R1.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_16_R1.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagList116R2 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R2.NBTTagList potentials = new net.minecraft.server.v1_16_R2.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.server.v1_16_R2.NBTTagCompound mob = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.server.v1_16_R2.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.server.v1_16_R2.NBTTagCompound mob = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.server.v1_16_R2.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.server.v1_16_R2.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.server.v1_16_R2.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_16_R2.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagList116R3 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R3.NBTTagList potentials = new net.minecraft.server.v1_16_R3.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.server.v1_16_R3.NBTTagCompound mob = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.server.v1_16_R3.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.server.v1_16_R3.NBTTagCompound mob = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.server.v1_16_R3.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.server.v1_16_R3.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_16_R3.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagList117R1 {
            public Object get(Random rand, int level, SpawnPotential sp) {
                net.minecraft.nbt.NBTTagList potentials = new net.minecraft.nbt.NBTTagList();
                if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                    for(int i = 0; i < 24; ++i){
                        net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
                        mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                        Equipment tool;
                        switch(rand.nextInt(3)){
                        case 0: tool = Equipment.SHOVEL; break;
                        case 1: tool = Equipment.AXE; break;
                        case 2: tool = Equipment.PICK; break;
                        default: tool = Equipment.PICK; break;
                        }

                        mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                        mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);

                        potentials.add((net.minecraft.nbt.NBTBase) 
                                sp.getPotential(mob));
                    }

                    return potentials;
		}
                
                if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                    for(int i = 0; i < 12; ++i){
                        net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
                        mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                        mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                        mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);
                        potentials.add((net.minecraft.nbt.NBTBase)
                                sp.getPotential(mob));
                    }

                    return potentials;
                }

                potentials.add((net.minecraft.nbt.NBTBase)
                        sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.nbt.NBTTagCompound())));
                return potentials;
            }
        }
        
        private static class GetNBTTagListUnknown {
            
            private static class NBT {
                public Object getWithNBT(Random rand, int level, SpawnPotential sp) {
                    net.minecraft.nbt.NBTTagList potentials = new net.minecraft.nbt.NBTTagList();
                    if(sp.name.equals(Spawner.getName(Spawner.ZOMBIE))){
                        for(int i = 0; i < 24; ++i){
                            net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
                            mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

                            Equipment tool;
                            switch(rand.nextInt(3)){
                            case 0: tool = Equipment.SHOVEL; break;
                            case 1: tool = Equipment.AXE; break;
                            case 2: tool = Equipment.PICK; break;
                            default: tool = Equipment.PICK; break;
                            }

                            mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
                            mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);

                            potentials.add((net.minecraft.nbt.NBTBase) 
                                    sp.getPotential(mob));
                        }

                        return potentials;
                    }

                    if(sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
                        for(int i = 0; i < 12; ++i){
                            net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
                            mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
                            mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
                            mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);
                            potentials.add((net.minecraft.nbt.NBTBase)
                                    sp.getPotential(mob));
                        }

                        return potentials;
                    }

                    potentials.add((net.minecraft.nbt.NBTBase)
                            sp.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.nbt.NBTTagCompound())));
                    return potentials;
                }
            }
            
            public Object get(Random rand, int level, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return null;
                return (new NBT()).getWithNBT(rand, level, sp);
            }
        }
	
	public Object getNBTTagList(Random rand, int level){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                return (new GetNBTTagList114()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                return (new GetNBTTagList115()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                return (new GetNBTTagList116()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                return (new GetNBTTagList116R2()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                return (new GetNBTTagList116R3()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                return (new GetNBTTagList117R1()).get(rand, level, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
                return (new GetNBTTagListUnknown()).get(rand, level, this);
            }
            return null;
	}
        
        private static class GetPotential114 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.server.v1_14_R1.NBTTagCompound potential = 
                        new net.minecraft.server.v1_14_R1.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_14_R1.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotential115 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.server.v1_15_R1.NBTTagCompound potential = 
                        new net.minecraft.server.v1_15_R1.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_15_R1.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotential116 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.server.v1_16_R1.NBTTagCompound potential = 
                        new net.minecraft.server.v1_16_R1.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_16_R1.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotential116R2 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.server.v1_16_R2.NBTTagCompound potential = 
                        new net.minecraft.server.v1_16_R2.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_16_R2.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotential116R3 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.server.v1_16_R3.NBTTagCompound potential = 
                        new net.minecraft.server.v1_16_R3.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_16_R3.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotential117R1 {
            public Object get(Object mob, SpawnPotential sp) {
                net.minecraft.nbt.NBTTagCompound potential = 
                        new net.minecraft.nbt.NBTTagCompound();
		potential.set("Entity", (net.minecraft.nbt.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
            }
        }
        
        private static class GetPotentialUnknown {
            
            private static class NBT {
                public Object getWithNBT(Object mob, SpawnPotential sp) {
                    net.minecraft.nbt.NBTTagCompound potential = 
                            new net.minecraft.nbt.NBTTagCompound();
                    potential.set("Entity", (net.minecraft.nbt.NBTBase) mob);
                    potential.setInt("Weight", sp.weight);
                    return potential;
                }
            }
            
            public Object get(Object mob, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return null;
                return (new NBT()).getWithNBT(mob, sp);
            }
        }
	
	private Object getPotential(Object mob){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
		return (new GetPotential114()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
		return (new GetPotential115()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
		return (new GetPotential116()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
		return (new GetPotential116R2()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
		return (new GetPotential116R3()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
		return (new GetPotential117R1()).get(mob, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
		return (new GetPotentialUnknown()).get(mob, this);
            }
            return null;
	}
        
        private static class EquipHands114 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.server.v1_14_R1.NBTTagList hands = 
                        new net.minecraft.server.v1_14_R1.NBTTagList();
		hands.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_14_R1.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHands115 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.server.v1_15_R1.NBTTagList hands = 
                        new net.minecraft.server.v1_15_R1.NBTTagList();
		hands.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_15_R1.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHands116 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.server.v1_16_R1.NBTTagList hands = 
                        new net.minecraft.server.v1_16_R1.NBTTagList();
		hands.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_16_R1.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHands116R2 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.server.v1_16_R2.NBTTagList hands = 
                        new net.minecraft.server.v1_16_R2.NBTTagList();
		hands.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_16_R2.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHands116R3 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.server.v1_16_R3.NBTTagList hands = 
                        new net.minecraft.server.v1_16_R3.NBTTagList();
		hands.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_16_R3.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHands117R1 {
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                net.minecraft.nbt.NBTTagList hands = 
                        new net.minecraft.nbt.NBTTagList();
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(offhand));
		((net.minecraft.nbt.NBTTagCompound) mob).set("HandItems", hands);
                return mob;
            }
        }
        
        private static class EquipHandsUnknown {
            
            private static class NBT {
                public Object getWithNBT(Object mob, String weapon, String offhand, SpawnPotential sp) {
                    net.minecraft.nbt.NBTTagList hands = 
                            new net.minecraft.nbt.NBTTagList();
                    hands.add((net.minecraft.nbt.NBTBase) sp.getItem(weapon));
                    hands.add((net.minecraft.nbt.NBTBase) sp.getItem(offhand));
                    ((net.minecraft.nbt.NBTTagCompound) mob).set("HandItems", hands);
                    return mob;
                }
            }
            
            public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return mob;
                return (new NBT()).getWithNBT(mob, weapon, offhand, sp);
            }
        }
	
	private Object equipHands(Object mob, String weapon, String offhand){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
		return (new EquipHands114()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
		return (new EquipHands115()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
		return (new EquipHands116()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
		return (new EquipHands116R2()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
		return (new EquipHands116R3()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
		return (new EquipHands117R1()).get(mob, weapon, offhand, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
		return (new EquipHandsUnknown()).get(mob, weapon, offhand, this);
            }
            return mob;
	}
        
        private static class EquipArmour114 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_14_R1.NBTTagList armour = new net.minecraft.server.v1_14_R1.NBTTagList();
		armour.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_14_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_14_R1.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmour115 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_15_R1.NBTTagList armour = new net.minecraft.server.v1_15_R1.NBTTagList();
		armour.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_15_R1.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmour116 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R1.NBTTagList armour = new net.minecraft.server.v1_16_R1.NBTTagList();
		armour.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R1.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_16_R1.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmour116R2 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R2.NBTTagList armour = new net.minecraft.server.v1_16_R2.NBTTagList();
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_16_R2.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmour116R3 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.server.v1_16_R3.NBTTagList armour = new net.minecraft.server.v1_16_R3.NBTTagList();
		armour.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_16_R3.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmour117R1 {
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                net.minecraft.nbt.NBTTagList armour = new net.minecraft.nbt.NBTTagList();
		armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.nbt.NBTTagCompound) mob).set("ArmorItems", armour);
                
                return mob;
            }
        }
        
        private static class EquipArmourUnknown {
            
            private static class NBT {
                public Object getWithNBT(Object mob, Random rand, int level, SpawnPotential sp) {
                    net.minecraft.nbt.NBTTagList armour = new net.minecraft.nbt.NBTTagList();
                    armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
                    armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
                    armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
                    armour.add((net.minecraft.nbt.NBTBase) sp.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
                    ((net.minecraft.nbt.NBTTagCompound) mob).set("ArmorItems", armour);

                    return mob;
                }
            }
            
            public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return mob;
                return (new NBT()).getWithNBT(mob, rand, level, sp);
            }
        }
	
	private Object equipArmour(Object mob, Random rand, int level){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
		return (new EquipArmour114()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
		return (new EquipArmour115()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
		return (new EquipArmour116()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
		return (new EquipArmour116R2()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
		return (new EquipArmour116R3()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
		return (new EquipArmour117R1()).get(mob, rand, level, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
		return (new EquipArmourUnknown()).get(mob, rand, level, this);
            }

            return mob;
	}
        
        private static class GetItem114 {
            public Object get(String itemName) {
                net.minecraft.server.v1_14_R1.NBTTagCompound item = 
                        new net.minecraft.server.v1_14_R1.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItem115 {
            public Object get(String itemName) {
                net.minecraft.server.v1_15_R1.NBTTagCompound item = 
                        new net.minecraft.server.v1_15_R1.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItem116 {
            public Object get(String itemName) {
                net.minecraft.server.v1_16_R1.NBTTagCompound item = 
                        new net.minecraft.server.v1_16_R1.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItem116R2 {
            public Object get(String itemName) {
                net.minecraft.server.v1_16_R2.NBTTagCompound item = 
                        new net.minecraft.server.v1_16_R2.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItem116R3 {
            public Object get(String itemName) {
                net.minecraft.server.v1_16_R3.NBTTagCompound item = 
                        new net.minecraft.server.v1_16_R3.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItem117R1 {
            public Object get(String itemName) {
                net.minecraft.nbt.NBTTagCompound item = 
                        new net.minecraft.nbt.NBTTagCompound();
		if(itemName == null) return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
            }
        }
        
        private static class GetItemUnknown {
            
            private static class NBT {
                public Object getWithNBT(String itemName) {
                    net.minecraft.nbt.NBTTagCompound item = 
                            new net.minecraft.nbt.NBTTagCompound();
                    if(itemName == null) return item;
                    item.setString("id", itemName);
                    item.setInt("Count", 1);
                    return item;
                }
            }
            
            public Object get(String itemName) {
                if(!MultiVersion.SpawnPotentialTest.result) return null;
                return (new NBT()).getWithNBT(itemName);
            }
        }
	
	private Object getItem(String itemName){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
		return (new GetItem114()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
		return (new GetItem115()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
		return (new GetItem116()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
		return (new GetItem116R2()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
		return (new GetItem116R3()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
		return (new GetItem117R1()).get(itemName);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
		return (new GetItemUnknown()).get(itemName);
            }
            return null;
	}
        
        private static class GetRoguelike114 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.server.v1_14_R1.NBTTagCompound tag = (net.minecraft.server.v1_14_R1.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.server.v1_14_R1.NBTTagList activeEffects = new net.minecraft.server.v1_14_R1.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.server.v1_14_R1.NBTTagCompound buff = new net.minecraft.server.v1_14_R1.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelike115 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.server.v1_15_R1.NBTTagCompound tag = (net.minecraft.server.v1_15_R1.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.server.v1_15_R1.NBTTagList activeEffects = new net.minecraft.server.v1_15_R1.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.server.v1_15_R1.NBTTagCompound buff = new net.minecraft.server.v1_15_R1.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelike116 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.server.v1_16_R1.NBTTagCompound tag = (net.minecraft.server.v1_16_R1.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.server.v1_16_R1.NBTTagList activeEffects = new net.minecraft.server.v1_16_R1.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.server.v1_16_R1.NBTTagCompound buff = new net.minecraft.server.v1_16_R1.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelike116R2 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.server.v1_16_R2.NBTTagCompound tag = (net.minecraft.server.v1_16_R2.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.server.v1_16_R2.NBTTagList activeEffects = new net.minecraft.server.v1_16_R2.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.server.v1_16_R2.NBTTagCompound buff = new net.minecraft.server.v1_16_R2.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelike116R3 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.server.v1_16_R3.NBTTagCompound tag = (net.minecraft.server.v1_16_R3.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.server.v1_16_R3.NBTTagList activeEffects = new net.minecraft.server.v1_16_R3.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.server.v1_16_R3.NBTTagCompound buff = new net.minecraft.server.v1_16_R3.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelike117R1 {
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                net.minecraft.nbt.NBTTagCompound tag = (net.minecraft.nbt.NBTTagCompound) otag;
                tag.setString("id", type);

                if(!(WorldConfig.wc.rogueSpawners
                        && sp.equip)) return tag;

                net.minecraft.nbt.NBTTagList activeEffects = new net.minecraft.nbt.NBTTagList();
                tag.set("ActiveEffects", activeEffects);

                net.minecraft.nbt.NBTTagCompound buff = new net.minecraft.nbt.NBTTagCompound();
                activeEffects.add(buff);

                buff.setByte("Id", (byte) 4);
                buff.setByte("Amplifier", (byte) level);
                buff.setInt("Duration", 10);
                buff.setByte("Ambient", (byte) 0);

                return tag;
            }
        }
        
        private static class GetRoguelikeUnknown {
            
            private static class NBT {
                public Object getWithNBT(int level, String type, Object otag, SpawnPotential sp) {
                    net.minecraft.nbt.NBTTagCompound tag = (net.minecraft.nbt.NBTTagCompound) otag;
                    tag.setString("id", type);

                    if(!(WorldConfig.wc.rogueSpawners
                            && sp.equip)) return tag;

                    net.minecraft.nbt.NBTTagList activeEffects = new net.minecraft.nbt.NBTTagList();
                    tag.set("ActiveEffects", activeEffects);

                    net.minecraft.nbt.NBTTagCompound buff = new net.minecraft.nbt.NBTTagCompound();
                    activeEffects.add(buff);

                    buff.setByte("Id", (byte) 4);
                    buff.setByte("Amplifier", (byte) level);
                    buff.setInt("Duration", 10);
                    buff.setByte("Ambient", (byte) 0);

                    return tag;
                }
            }
            
            public Object get(int level, String type, Object otag, SpawnPotential sp) {
                if(!MultiVersion.SpawnPotentialTest.result) return null;
                return (new NBT()).getWithNBT(level, type, otag, sp);
            }
        }
	
	private Object getRoguelike(int level, String type, Object otag){
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                return (new GetRoguelike114()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                return (new GetRoguelike115()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                return (new GetRoguelike116()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                return (new GetRoguelike116R2()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                return (new GetRoguelike116R3()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                return (new GetRoguelike117R1()).get(level, type, otag, this);
            }
            if(Main.version == MultiVersion.Version.UNKNOWN) {
                return (new GetRoguelikeUnknown()).get(level, type, otag, this);
            }
            return otag;
    }

}
