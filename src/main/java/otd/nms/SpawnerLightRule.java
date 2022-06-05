package otd.nms;

import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public interface SpawnerLightRule {
	public void update(Block tileentity, JavaPlugin plugin);
}