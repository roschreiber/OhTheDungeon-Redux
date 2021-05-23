/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.populator.cherry;

import org.bukkit.Material;
import org.bukkit.World;

/**
 *
 * @author shadow_wind
 */
public class IWorld {
    private World world;
    public IWorld(World world) {
        this.world = world;
    }
    
    public Material getBlockId(int x, int y, int z) {
        return world.getBlockAt(x, y, z).getType();
    }
    
    public IWorld setBlockAt(int x, int y, int z, Material material) {
        world.getBlockAt(x, y, z).setType(material, false);
        return this;
    }
}
