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
package otd.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author
 */
public class UpdateChecker {

	private final JavaPlugin plugin;
	private final int resourceId;
	private final String versionId = "1.15";

	public UpdateChecker(JavaPlugin plugin, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
	}

	public void getVersionOTD(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL("https://ohthedungeon.com/updater.php?ver=" + this.versionId)
					.openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				this.plugin.getLogger().log(Level.SEVERE, "Cannot look for updates: {0}", exception.getMessage());
			}
		});
	}

	public void getVersion(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL(
					"https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
					Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				this.plugin.getLogger().log(Level.SEVERE, "Cannot look for updates: {0}", exception.getMessage());
			}
		});
	}

	public static class UpdateResult {
		public boolean update_valid = false;
		public String update_version = "";
	}

	public final static UpdateResult UPDATE_RESULT = new UpdateResult();

	public static void CheckUpdate(JavaPlugin plugin, int resourceId) {
		new UpdateChecker(plugin, resourceId).getVersion(version -> {
			synchronized (UPDATE_RESULT) {
				UPDATE_RESULT.update_valid = !plugin.getDescription().getVersion().trim()
						.equalsIgnoreCase(version.trim());
				if (UPDATE_RESULT.update_valid) {
					UPDATE_RESULT.update_version = version.trim();
				}
			}
		});
	}
}
