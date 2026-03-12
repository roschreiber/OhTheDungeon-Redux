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
import java.util.List;
import otd.redux.util.ChatManager;
import otd.redux.util.MenuHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class RogueLikeDungeonTower extends Content {
	private final static int SLOT = 18;

	public static RogueLikeDungeonTower instance = new RogueLikeDungeonTower();

	private final String world_name;
	private final Content parent;

	private SimpleWorldConfig swc;

	private RogueLikeDungeonTower() {
		super(MenuHelper.color(MenuHelper.PRIMARY) + I18n.instance.Roguelike_Dungeon_Tower, SLOT);
		world_name = null;
		parent = null;
	}

	public RogueLikeDungeonTower(String world_name, Content parent) {
		super(MenuHelper.color(MenuHelper.PRIMARY) + I18n.instance.Roguelike_Dungeon_Tower, SLOT);
		this.world_name = world_name;

		if (!WorldConfig.wc.dict.containsKey(world_name)) {
			WorldConfig.wc.dict.put(world_name, new SimpleWorldConfig());
		}

		swc = WorldConfig.wc.dict.get(world_name);

		this.parent = parent;
	}

	private boolean isAllDisabled() {
		return !(swc.roguelike.themes.bunker || swc.roguelike.themes.desert || swc.roguelike.themes.forest
				|| swc.roguelike.themes.house || swc.roguelike.themes.ice || swc.roguelike.themes.jungle
				|| swc.roguelike.themes.mesa || swc.roguelike.themes.mountain || swc.roguelike.themes.rare
				|| swc.roguelike.themes.ruin || swc.roguelike.themes.swamp);
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof RogueLikeDungeonTower)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		RogueLikeDungeonTower holder = (RogueLikeDungeonTower) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world_name == null)
			return;
		// String key = holder.world_name;
		// SimpleWorldConfig swc = WorldConfig.wc.dict.get(key);

		if (slot == 0) {
			holder.swc.roguelike.themes.bunker = !holder.swc.roguelike.themes.bunker;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.bunker = !holder.swc.roguelike.themes.bunker;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 1) {
			holder.swc.roguelike.themes.desert = !holder.swc.roguelike.themes.desert;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.desert = !holder.swc.roguelike.themes.desert;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 2) {
			holder.swc.roguelike.themes.forest = !holder.swc.roguelike.themes.forest;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.forest = !holder.swc.roguelike.themes.forest;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 3) {
			holder.swc.roguelike.themes.house = !holder.swc.roguelike.themes.house;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.house = !holder.swc.roguelike.themes.house;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 4) {
			holder.swc.roguelike.themes.ice = !holder.swc.roguelike.themes.ice;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.ice = !holder.swc.roguelike.themes.ice;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 5) {
			holder.swc.roguelike.themes.jungle = !holder.swc.roguelike.themes.jungle;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.jungle = !holder.swc.roguelike.themes.jungle;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 6) {
			holder.swc.roguelike.themes.mesa = !holder.swc.roguelike.themes.mesa;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.mesa = !holder.swc.roguelike.themes.mesa;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 7) {
			holder.swc.roguelike.themes.mountain = !holder.swc.roguelike.themes.mountain;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.mountain = !holder.swc.roguelike.themes.mountain;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 8) {
			holder.swc.roguelike.themes.ruin = !holder.swc.roguelike.themes.ruin;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.ruin = !holder.swc.roguelike.themes.ruin;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 9) {
			holder.swc.roguelike.themes.rare = !holder.swc.roguelike.themes.rare;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.rare = !holder.swc.roguelike.themes.rare;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 10) {
			holder.swc.roguelike.themes.swamp = !holder.swc.roguelike.themes.swamp;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.swamp = !holder.swc.roguelike.themes.swamp;
				ChatManager.getInstance().sendWarning(p, I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 17) {
			holder.parent.openInventory(p);
		}
	}

	private void addThemeItem(int slot, String name, boolean enabled) {
		ItemStack is = new ItemStack(MenuHelper.toggleMaterial(enabled));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + name);
		List<String> lores = new ArrayList<>();
		lores.add(MenuHelper.status(enabled));
		lores.add(MenuHelper.separator());
		lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
		im.setLore(lores);
		is.setItemMeta(im);
		addItem(slot, is);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		addThemeItem(0, I18n.instance.Bunker, swc.roguelike.themes.bunker);
		addThemeItem(1, I18n.instance.Desert, swc.roguelike.themes.desert);
		addThemeItem(2, I18n.instance.Forest, swc.roguelike.themes.forest);
		addThemeItem(3, I18n.instance.House, swc.roguelike.themes.house);
		addThemeItem(4, I18n.instance.Ice, swc.roguelike.themes.ice);
		addThemeItem(5, I18n.instance.Jungle, swc.roguelike.themes.jungle);
		addThemeItem(6, I18n.instance.Mesa, swc.roguelike.themes.mesa);
		addThemeItem(7, I18n.instance.Mountain, swc.roguelike.themes.mountain);
		addThemeItem(8, I18n.instance.Ruin, swc.roguelike.themes.ruin);
		addThemeItem(9, I18n.instance.Cactus, swc.roguelike.themes.rare);
		addThemeItem(10, I18n.instance.Swamp, swc.roguelike.themes.swamp);
		addItem(1, 8, MenuHelper.back());
	}
}
