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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import otd.Main;
import org.bukkit.Bukkit;

/**
 *
 * @author
 */
public class AsyncLog {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String logfile = Main.instance.getDataFolder().toString() + File.separator + "log.txt";

	public static void logMessage(String message) {
//        Bukkit.getLogger().log(Level.SEVERE, message);
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try (FileWriter writer = new FileWriter(logfile, true)) {
				writer.write(dateFormat.format(new Date()) + " " + message);
				writer.write("\n");
			} catch (IOException e) {
			}
		});
	}
}
