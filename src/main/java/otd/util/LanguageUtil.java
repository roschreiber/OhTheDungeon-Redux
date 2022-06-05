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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import otd.Main;

public class LanguageUtil {
	public final static List<String> LANG;
	static {
		LANG = new ArrayList<>();
		LANG.add("lang_zhcn.json");
	}

	public static void init() {
		File path = new File(Main.instance.getDataFolder().getAbsolutePath() + File.separator + "lang");
		path.mkdir();
		for (String lang : LANG) {
			writeLang(lang);
		}
	}

	private static void writeLang(String filename) {
		File out = new File(Main.instance.getDataFolder().getAbsolutePath() + File.separator + "lang" + File.separator,
				filename);
		try (InputStream in = Main.instance.getResource("lang/" + filename);
				OutputStream writer = new BufferedOutputStream(new FileOutputStream(out, false))) {
			// Step 3
			byte[] buffer = new byte[1024 * 4];
			int length;
			while ((length = in.read(buffer)) >= 0) {
				writer.write(buffer, 0, length);
			}
		} catch (Exception ex) {
		}
	}
}
