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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

//import com.destroystokyo.paper.profile.PlayerProfile;

/**
 *
 * @author shadow
 */
public class Skull {

	@SuppressWarnings("deprecation")
	private static PlayerProfile getProfile(String uuid, String url) {
		PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString(uuid));
		PlayerTextures textures = profile.getTextures();
		URL urlObject;
		try {
			urlObject = new URL("https://textures.minecraft.net/texture/" + url); // The URL to the skin, for example: https://textures.minecraft.net/texture/18813764b2abc94ec3c3bc67b9147c21be850cdf996679703157f4555997ea63a
		} catch (MalformedURLException exception) {
			throw new RuntimeException("Invalid URL", exception);
		}
		textures.setSkin(urlObject);   // Set the skin of the player profile to the URL
		profile.setTextures(textures); // Set the textures back to the profile

		return profile;
	}

	@SuppressWarnings("deprecation")
	private static ItemStack getHead(String uuid, String url) {
		PlayerProfile profile = getProfile(uuid, url);
		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta im = (SkullMeta) is.getItemMeta();
		im.setOwnerProfile(profile); // Set the owning player of the head to the player profile
		is.setItemMeta(im);
		return is;
	}

	public final static class HeadData {
		private final String uuid;
		private final String url;

		public HeadData(String uuid, String url) {
			this.uuid = uuid;
			this.url = url;
		}

		public String getUUID() {
			return uuid;
		}

		public String getUrl() {
			return url;
		}

		public ItemStack getItem() {
			return getHead(this.uuid, this.url);
		}
	}

	public final static HeadData EARTH = new HeadData("b8c1ed51-5698-4a3c-a062-8ffd4bb471ed", "879e54cbe87867d14b2fbdf3f1870894352048dfecd962846dea893b2154c85");

	public final static HeadData CITY = new HeadData("66203b35-31b7-4cd3-bcbb-91f953d180cc", "b25b27ce62ca88743840a95d1c39868f43ca60696a84f564fbd7dda259be00fe");

	public final static HeadData TOOL = new HeadData("c8b42654-b3e6-4fb2-8da3-419f6eeb24e5", "bdaf68259fd279d7cbfedeb3c9d8e7e3def16c09dfd84522f488be1e027fe5e3");

	public final static HeadData ROGUELIKE = new HeadData("bcccae77-0ac7-4cd0-8126-c900727c2223", "49c1832e4ef5c4ad9c519d194b1985030d257914334aaf2745c9dfd611d6d61d");

	public final static HeadData DOOMLIKE = new HeadData("b6ef6473-53e3-4e92-a04f-2b4827ad5f2f", "419fb2e49703c6cb95116be15363c9d5689ef229a75c655ef576be360ec3cbeb");

	public final static HeadData BATTLE = new HeadData("99d1db69-a107-4227-b575-cb40c9f37092", "a5d76d90b378083d14775680505ddb1e6c2c6dcf4dde7f9b1f58809bec6c65c8");

	public final static HeadData SMOOFY = new HeadData("66cdb895-9ce2-4894-9925-4b843d95d325", "7dd25a4ccba956842e9767f8d04abe87544213a94938bf5d45cf9cd6b2485898");

	public final static HeadData DRAYLAR = new HeadData("51265b44-b6d0-40d2-a2f9-1bf181e971bd", "baa95291f93a70db6f1aa1eb84824b9df3b9fc0a2546f7ff81a12c56760feaa");

	public final static HeadData ANTMAN = new HeadData("2bfd0d1c-9c96-4036-ab44-0051dfdb3dda", "9a3abef3ac9fb07ccb1035effcdab127ff27baeba6d9e87692ce318b56641b2");

	public final static HeadData AETHER = new HeadData("36faa30a-f2e4-486a-830d-a72578d0e3c3", "549a1f6feb62fdf2fc4e17f91bb1a32011e75c56205fa25869fa5dcfb35ae000");

	public final static HeadData LICH = new HeadData("fd6ba3aa-1348-42c6-9819-32e377bcfedf", "eafc9a8aa28b50ff3429d151fbd68f6f75ef49d6d483904a41ad5789e2053f7");

	public final static HeadData CASTLE = new HeadData("af081ea7-3b8e-4a24-aaac-dbec776ee903", "3288fc6f13005956853b28c9008b290f04f765c4ba50a8e9cd62c0dbe2b2ada7");

	public final static HeadData CINDER = new HeadData("9b13d10d-4703-4dfb-af23-ed0228c84eb4", "e5d607275cf883f1a99b3f435cec9dc24176b08652eaa124105652fd42d48592");

	public final static HeadData DICE = new HeadData("afbe4c67-a6a5-4559-ad06-78a6ed2ab4e9", "915f7c313bca9c2f958e68ab14ab393867d67503affff8f20cb13fbe917fd31");

	public final static HeadData CUSTOM = new HeadData("5636359e-9ac6-493f-894d-a5914f3d0a6e", "56330a4a22ff55871fc8c618e421a37733ac1dcab9c8e1a4bb73ae645a4a4e");

	public final static HeadData ENABLED = new HeadData("b4e096c1-65b2-4abd-bedc-e67842bb42b1", "ce2a530f42726fa7a31efab8e43dadee188937cf824af88ea8e4c93a49c57294");

	public final static HeadData DISABLED = new HeadData("1c71c451-e7cc-4a64-bdd1-a1c038b8c9f3", "eef0708fce5ffaa6608cbed3e78ed95803d8a89377d1d938ce0bdc61b6dc9f4f");

}
