/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import otd.addon.com.ohthedungeon.storydungeon.util.TaskHolder;
import java.util.Random;
import org.bukkit.World;

/**
 *
 * @author shadow_wind
 */
public class AsyncTask_Loot implements AsyncTask {
	private TaskHolder holder;

	public AsyncTask_Loot(TaskHolder holder) {
		this.holder = holder;
	}

	public AsyncTask_Loot addLoot() {
		this.holder.addLoot();
		this.holder = null;
		return this;
	}

	@Override
	public boolean doTask(World world, Random random) {
		addLoot();
		return true;
	}
}
