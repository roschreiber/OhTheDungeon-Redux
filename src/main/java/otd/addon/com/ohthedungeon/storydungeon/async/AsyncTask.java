/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import java.util.Random;
import org.bukkit.World;

/**
 *
 * @author shadow_wind
 */
public interface AsyncTask {
	public boolean doTask(World world, Random random);
}
