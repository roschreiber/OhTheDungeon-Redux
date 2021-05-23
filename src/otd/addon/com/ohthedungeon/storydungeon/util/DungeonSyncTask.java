/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.util;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncTask;

/**
 *
 * @author shadow_wind
 */
public class DungeonSyncTask {
    private final int x;
    private final int z;
    private final TaskType type;
    private final AsyncTask task;
    public DungeonSyncTask(int x, int z, TaskType type, AsyncTask task) {
        this.x = x;
        this.z = z;
        this.type = type;
        this.task = task;
    }
    public int getX() {
        return this.x;
    }
    public int getZ() {
        return this.z;
    }
    public TaskType getType() {
        return this.type;
    }
    public AsyncTask getTask() {
        return this.task;
    }
}
