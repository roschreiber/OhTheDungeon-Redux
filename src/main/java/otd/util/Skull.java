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

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

/**
 *
 * @author shadow
 */
public class Skull {
	public static SkullMeta applyHead(String uuid, String textures, SkullMeta headMeta) {
		GameProfile profile = new GameProfile(UUID.fromString(uuid), "");
		profile.getProperties().put("textures", new Property("textures", textures));
		Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NullPointerException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			// e1.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "[Oh The Dungeons] Dice is not working properly");
		}

		return headMeta;
	}

	public static ItemStack getHead(String uuid, String textures) {
		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta im = (SkullMeta) is.getItemMeta();
		im = applyHead(uuid, textures, im);
		is.setItemMeta(im);
		return is;
	}

	public final static class HeadData {
		private final String uuid;
		private final String texture;

		public HeadData(String uuid, String texture) {
			this.uuid = uuid;
			this.texture = texture;
		}

		public String getUUID() {
			return uuid;
		}

		public String getTexture() {
			return texture;
		}

		public ItemStack getItem() {
			return getHead(this.uuid, this.texture);
		}
	}

	public final static HeadData EARTH = new HeadData("b8c1ed51-5698-4a3c-a062-8ffd4bb471ed",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZTU0Y2JlODc4NjdkMTRiMmZiZGYzZjE4NzA4OTQzNTIwNDhkZmVjZDk2Mjg0NmRlYTg5M2IyMTU0Yzg1In19fQ==");
	public final static HeadData CITY = new HeadData("66203b35-31b7-4cd3-bcbb-91f953d180cc",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI1YjI3Y2U2MmNhODg3NDM4NDBhOTVkMWMzOTg2OGY0M2NhNjA2OTZhODRmNTY0ZmJkN2RkYTI1OWJlMDBmZSJ9fX0=");
	public final static HeadData TOOL = new HeadData("c8b42654-b3e6-4fb2-8da3-419f6eeb24e5",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRhZjY4MjU5ZmQyNzlkN2NiZmVkZWIzYzlkOGU3ZTNkZWYxNmMwOWRmZDg0NTIyZjQ4OGJlMWUwMjdmZTVlMyJ9fX0=");
	public final static HeadData ROGUELIKE = new HeadData("bcccae77-0ac7-4cd0-8126-c900727c2223",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljMTgzMmU0ZWY1YzRhZDljNTE5ZDE5NGIxOTg1MDMwZDI1NzkxNDMzNGFhZjI3NDVjOWRmZDYxMWQ2ZDYxZCJ9fX0=");
	public final static HeadData DOOMLIKE = new HeadData("b6ef6473-53e3-4e92-a04f-2b4827ad5f2f",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE5ZmIyZTQ5NzAzYzZjYjk1MTE2YmUxNTM2M2M5ZDU2ODllZjIyOWE3NWM2NTVlZjU3NmJlMzYwZWMzY2JlYiJ9fX0=");
	public final static HeadData BATTLE = new HeadData("99d1db69-a107-4227-b575-cb40c9f37092",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVkNzZkOTBiMzc4MDgzZDE0Nzc1NjgwNTA1ZGRiMWU2YzJjNmRjZjRkZGU3ZjliMWY1ODgwOWJlYzZjNjVjOCJ9fX0=");
	public final static HeadData SMOOFY = new HeadData("66cdb895-9ce2-4894-9925-4b843d95d325",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RkMjVhNGNjYmE5NTY4NDJlOTc2N2Y4ZDA0YWJlODc1NDQyMTNhOTQ5MzhiZjVkNDVjZjljZDZiMjQ4NTg5OCJ9fX0=");
	public final static HeadData DRAYLAR = new HeadData("51265b44-b6d0-40d2-a2f9-1bf181e971bd",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFhOTUyOTFmOTNhNzBkYjZmMWFhMWViODQ4MjRiOWRmM2I5ZmMwYTI1NDZmN2ZmODFhMTJjNTY3NjBmZWFhIn19fQ==");
	public final static HeadData ANTMAN = new HeadData("2bfd0d1c-9c96-4036-ab44-0051dfdb3dda",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWEzYWJlZjNhYzlmYjA3Y2NiMTAzNWVmZmNkYWIxMjdmZjI3YmFlYmE2ZDllODc2OTJjZTMxOGI1NjY0MWIyIn19fQ==");
	public final static HeadData AETHER = new HeadData("36faa30a-f2e4-486a-830d-a72578d0e3c3",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ5YTFmNmZlYjYyZmRmMmZjNGUxN2Y5MWJiMWEzMjAxMWU3NWM1NjIwNWZhMjU4NjlmYTVkY2ZiMzVhZTAwMCJ9fX0=");
	public final static HeadData LICH = new HeadData("fd6ba3aa-1348-42c6-9819-32e377bcfedf",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFmYzlhOGFhMjhiNTBmZjM0MjlkMTUxZmJkNjhmNmY3NWVmNDlkNmQ0ODM5MDRhNDFhZDU3ODllMjA1M2Y3In19fQ==");
	public final static HeadData CASTLE = new HeadData("af081ea7-3b8e-4a24-aaac-dbec776ee903",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzI4OGZjNmYxMzAwNTk1Njg1M2IyOGM5MDA4YjI5MGYwNGY3NjVjNGJhNTBhOGU5Y2Q2MmMwZGJlMmIyYWRhNyJ9fX0=");
	public final static HeadData CUSTOM = new HeadData("5636359e-9ac6-493f-894d-a5914f3d0a6e",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTYzMzBhNGEyMmZmNTU4NzFmYzhjNjE4ZTQyMWEzNzczM2FjMWRjYWI5YzhlMWE0YmI3M2FlNjQ1YTRhNGUifX19");
	public final static HeadData ENABLED = new HeadData("b4e096c1-65b2-4abd-bedc-e67842bb42b1",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyYTUzMGY0MjcyNmZhN2EzMWVmYWI4ZTQzZGFkZWUxODg5MzdjZjgyNGFmODhlYThlNGM5M2E0OWM1NzI5NCJ9fX0=");
	public final static HeadData DISABLED = new HeadData("1c71c451-e7cc-4a64-bdd1-a1c038b8c9f3",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWVmMDcwOGZjZTVmZmFhNjYwOGNiZWQzZTc4ZWQ5NTgwM2Q4YTg5Mzc3ZDFkOTM4Y2UwYmRjNjFiNmRjOWY0ZiJ9fX0=");
}
