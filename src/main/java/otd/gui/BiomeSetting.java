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
package otd.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.util.I18n;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class BiomeSetting extends Content {
	public final Content parent;
	private final static int SLOT = 54;
	private final Set<String> biomes;
	private int offset;
	private int i;

	private BiomeSetting() {
		super("", SLOT);
		this.parent = null;
		this.biomes = null;
		this.offset = 0;
	}

	public static BiomeSetting instance = new BiomeSetting();

	public BiomeSetting(String world, Content parent, Set<String> biomes) {
		super(I18n.instance.Biome_Setting, SLOT);
		this.parent = parent;
		this.biomes = biomes;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof BiomeSetting)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		BiomeSetting holder = (BiomeSetting) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 8) {
			WorldConfig.save();
			holder.parent.openInventory(p);
			return;
		}
		if (slot == 0) {
			holder.offset--;
			holder.init();
			return;
		}
		if (slot == 1) {
			holder.offset++;
			holder.init();
			return;
		}

		if (slot < 9 || slot > 53)
			return;

		ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;

		ItemMeta im = clickedItem.getItemMeta();
		if (im == null)
			return;
		String name = im.getDisplayName();
		if (name.isEmpty())
			return;

		if (holder.biomes.contains(name))
			holder.biomes.remove(name);
		else
			holder.biomes.add(name);
		WorldConfig.save();

		holder.init();
	}

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		{
			ItemStack is = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
			for (int i = 2; i < 8; i++)
				addItem(0, i, is);
		}
		{
			ItemStack is = new ItemStack(Material.END_CRYSTAL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Previous);
			is.setItemMeta(im);
			addItem(0, 0, is);
		}
		{
			ItemStack is = new ItemStack(Material.END_CRYSTAL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Next);
			is.setItemMeta(im);
			addItem(0, 1, is);
		}
		{
			ItemStack is = new ItemStack(Material.STONE_BUTTON);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Apply);
			is.setItemMeta(im);
			addItem(0, 8, is);
		}
		{

			List<String> all_biomes = new ArrayList<>();
			Registry.BIOME.forEach(biome -> {
				all_biomes.add(biome.toString());
			});
			Collections.sort(all_biomes);

			if (offset < 0)
				offset = 0;
			int index = offset * 45;
			i = 0;
			while (i + 9 < 54 && index + i < all_biomes.size()) {
				String biome_name = all_biomes.get(index + i).toString();
				Material material = biomes.contains(biome_name) ? DISABLE : ENABLE;

				ItemStack is = new ItemStack(material);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(biome_name);
				String status = material == DISABLE ? I18n.instance.Disable : I18n.instance.Enable;

				List<String> lores = new ArrayList<>();
				lores.add(status);
				im.setLore(lores);
				is.setItemMeta(im);

				addItem(i + 9, is);
				i++;
			}
	}
	}
}
