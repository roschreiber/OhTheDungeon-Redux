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
package otd.util.map;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class MapManager {

	public static void init() {
		initMapConfig();
		initMapRender();
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getBunker() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_bunker);
		mm.setDisplayName(I18n.instance.Bunker);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getCactus() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_cactus);
		mm.setDisplayName(I18n.instance.Cactus);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getDesert() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_desert);
		mm.setDisplayName(I18n.instance.Desert);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getForest() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_forest);
		mm.setDisplayName(I18n.instance.Forest);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getHouse() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_house);
		mm.setDisplayName(I18n.instance.House);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getIce() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_ice);
		mm.setDisplayName(I18n.instance.Ice);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getJungle() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_jungle);
		mm.setDisplayName(I18n.instance.Jungle);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getMesa() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_mesa);
		mm.setDisplayName(I18n.instance.Mesa);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getMountain() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_mountain);
		mm.setDisplayName(I18n.instance.Mountain);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getRuin() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_ruin);
		mm.setDisplayName(I18n.instance.Ruin);
		is.setItemMeta(mm);

		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getSwamp() {
		ItemStack is = new ItemStack(Material.FILLED_MAP);
		MapMeta mm = (MapMeta) is.getItemMeta();
		mm.setMapId(WorldConfig.wc.map_swamp);
		mm.setDisplayName(I18n.instance.Swamp);
		is.setItemMeta(mm);

		return is;
	}

	private static void initMapRender() {
		MapView map;
		map = Bukkit.getMap(WorldConfig.wc.map_bunker);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.bunker);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_cactus);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.cactus);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_desert);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.desert);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_forest);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.forest);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_house);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.house);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_ice);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.ice);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_jungle);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.jungle);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_mesa);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.mesa);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_mountain);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.mountain);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_ruin);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.ruin);
		}
		map = Bukkit.getMap(WorldConfig.wc.map_swamp);
		if (map != null) {
			for (MapRenderer r : map.getRenderers())
				map.removeRenderer(r);
			map.addRenderer(ImageRender.swamp);
		}
	}

	private static void initMapConfig() {
		World w = Bukkit.getServer().getWorlds().get(0);
		boolean save = false;
		if (WorldConfig.wc.map_bunker == -1 || Bukkit.getMap(WorldConfig.wc.map_bunker) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_bunker = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_cactus == -1 || Bukkit.getMap(WorldConfig.wc.map_cactus) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_cactus = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_desert == -1 || Bukkit.getMap(WorldConfig.wc.map_desert) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_desert = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_forest == -1 || Bukkit.getMap(WorldConfig.wc.map_forest) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_forest = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_house == -1 || Bukkit.getMap(WorldConfig.wc.map_house) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_house = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_ice == -1 || Bukkit.getMap(WorldConfig.wc.map_ice) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_ice = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_jungle == -1 || Bukkit.getMap(WorldConfig.wc.map_jungle) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_jungle = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_mesa == -1 || Bukkit.getMap(WorldConfig.wc.map_mesa) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_mesa = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_mountain == -1 || Bukkit.getMap(WorldConfig.wc.map_mountain) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_mountain = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_ruin == -1 || Bukkit.getMap(WorldConfig.wc.map_ruin) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_ruin = map.getId();
			save = true;
		}
		if (WorldConfig.wc.map_swamp == -1 || Bukkit.getMap(WorldConfig.wc.map_swamp) == null) {
			MapView map = Bukkit.createMap(w);
			WorldConfig.wc.map_swamp = map.getId();
			save = true;
		}
		if (save)
			WorldConfig.save();
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		try {
			InputStream stream = MapManager.class.getResourceAsStream("/image/bunker.png");
			ImageIcon icon = new ImageIcon(ImageIO.read(stream));
			JLabel label = new JLabel(icon);
			frame.add(label);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		} catch (IOException ex) {

		}
	}

}
