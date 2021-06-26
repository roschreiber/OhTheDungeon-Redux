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
package otd.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import otd.Main;
import otd.MultiVersion;
/*
public class EntityCreator {
    
    private static class EntityCreator114 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_14_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_14_R1.CraftWorld) location.getWorld();
            net.minecraft.server.v1_14_R1.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_14_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_14_R1.CraftWorld) world;
            org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    private static class EntityCreator115 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_15_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_15_R1.CraftWorld) location.getWorld();
            net.minecraft.server.v1_15_R1.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_15_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_15_R1.CraftWorld) world;
            org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    private static class EntityCreator116R1 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_16_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R1.CraftWorld) location.getWorld();
            net.minecraft.server.v1_16_R1.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_16_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R1.CraftWorld) world;
            org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    private static class EntityCreator116R2 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_16_R2.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R2.CraftWorld) location.getWorld();
            net.minecraft.server.v1_16_R2.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_16_R2.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R2.CraftWorld) world;
            org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    private static class EntityCreator116R3 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_16_R3.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) location.getWorld();
            net.minecraft.server.v1_16_R3.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_16_R3.CraftWorld cw = (org.bukkit.craftbukkit.v1_16_R3.CraftWorld) world;
            org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    private static class EntityCreator117R1 {
        public Entity create(EntityType entityType, Location location) {
            org.bukkit.craftbukkit.v1_17_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_17_R1.CraftWorld) location.getWorld();
            net.minecraft.world.entity.Entity entity = cw.createEntity(location, entityType.getEntityClass());
            
            return (Entity) entity.getBukkitEntity();
        }
        public void spawn(World world, Entity entity) {
            org.bukkit.craftbukkit.v1_17_R1.CraftWorld cw = (org.bukkit.craftbukkit.v1_17_R1.CraftWorld) world;
            org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity ce = (org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity) entity;
            cw.addEntity(ce.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
    
    public static Entity create(EntityType entityType, Location location) {
        if(Main.version == MultiVersion.Version.V1_14_R1) {
            return (new EntityCreator114()).create(entityType, location);
        }
        if(Main.version == MultiVersion.Version.V1_15_R1) {
            return (new EntityCreator115()).create(entityType, location);
        }
        if(Main.version == MultiVersion.Version.V1_16_R1) {
            return (new EntityCreator116R1()).create(entityType, location);
        }
        if(Main.version == MultiVersion.Version.V1_16_R2) {
            return (new EntityCreator116R2()).create(entityType, location);
        }
        if(Main.version == MultiVersion.Version.V1_16_R3) {
            return (new EntityCreator116R3()).create(entityType, location);
        }
        if(Main.version == MultiVersion.Version.V1_17_R1) {
            return (new EntityCreator117R1()).create(entityType, location);
        }
        return null;
    }
    
    public static void spawn(World world, Entity entity) {
        if(Main.version == MultiVersion.Version.V1_14_R1) {
            (new EntityCreator114()).spawn(world, entity);
        }
        if(Main.version == MultiVersion.Version.V1_15_R1) {
            (new EntityCreator115()).spawn(world, entity);
        }
        if(Main.version == MultiVersion.Version.V1_16_R1) {
            (new EntityCreator116R1()).spawn(world, entity);
        }
        if(Main.version == MultiVersion.Version.V1_16_R2) {
            (new EntityCreator116R2()).spawn(world, entity);
        }
        if(Main.version == MultiVersion.Version.V1_16_R3) {
            (new EntityCreator116R3()).spawn(world, entity);
        }
        if(Main.version == MultiVersion.Version.V1_17_R1) {
            (new EntityCreator117R1()).spawn(world, entity);
        }
    }
}
*/