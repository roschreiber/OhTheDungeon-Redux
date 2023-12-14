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
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import forge_sandbox.greymerk.roguelike.treasure.loot.PotionMixture;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import otd.util.I18n;
import otd.util.Roll;

/**
 *
 * @author shadow
 */
public class CreativeInventory extends Content {

	private final static int SLOT = 54;

	public CreativeInventory() {
		super(I18n.instance.Creative_Inventory, SLOT);
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof CreativeInventory)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		int slot = e.getRawSlot();
		if (slot < SLOT) {
			kcancel(e);
			Player p = (Player) e.getWhoClicked();
			CreativeInventory holder = (CreativeInventory) e.getInventory().getHolder();
			if (holder == null)
				return;

			ItemStack is = this.inv.getItem(slot);
			if (is != null && is.getType() != Material.AIR) {
				p.setItemOnCursor(is.clone());
			}
		}
	}

	@SuppressWarnings("serial")
	private static class MaxRandom extends Random {
		@Override
		public int nextInt(int i) {
			return i - 1;
		}
	}

	private static final MaxRandom random = new MaxRandom();

	@Override
	public void init() {
		List<ItemStack> list = new ArrayList<>();
		list.add(Roll.getDice());
		list.add(Lich.getLichHead());
		list.add(CastleKing.getCastleKingHead());
		list.add(ItemNovelty.getItem(ItemNovelty.AMLP));
		list.add(ItemNovelty.getItem(ItemNovelty.ASHLEA));
		list.add(ItemNovelty.getItem(ItemNovelty.AVIDYA));
		list.add(ItemNovelty.getItem(ItemNovelty.BAJ));
		list.add(ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
		list.add(ItemNovelty.getItem(ItemNovelty.CLEO));
		list.add(ItemNovelty.getItem(ItemNovelty.DINNERBONE));
		list.add(ItemNovelty.getItem(ItemNovelty.DOCM));
		list.add(ItemNovelty.getItem(ItemNovelty.ENIKOBOW));
		list.add(ItemNovelty.getItem(ItemNovelty.ENIKOSWORD));
		list.add(ItemNovelty.getItem(ItemNovelty.ETHO));
		list.add(ItemNovelty.getItem(ItemNovelty.FOURLES));
		list.add(ItemNovelty.getItem(ItemNovelty.GENERIKB));
		list.add(ItemNovelty.getItem(ItemNovelty.GINGER));
		list.add(ItemNovelty.getItem(ItemNovelty.GREYMERK));
		list.add(ItemNovelty.getItem(ItemNovelty.GRIM));
		list.add(ItemNovelty.getItem(ItemNovelty.GUUDE));
		list.add(ItemNovelty.getItem(ItemNovelty.KURT));
		list.add(ItemNovelty.getItem(ItemNovelty.MANPANTS));
		list.add(ItemNovelty.getItem(ItemNovelty.MMILLSS));
		list.add(ItemNovelty.getItem(ItemNovelty.NEBRISCROWN));
		list.add(ItemNovelty.getItem(ItemNovelty.NOTCH));
		list.add(ItemNovelty.getItem(ItemNovelty.NULL));
		list.add(ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP));
		list.add(ItemNovelty.getItem(ItemNovelty.RLEAHY));
		list.add(ItemNovelty.getItem(ItemNovelty.VALANDRAH));
		list.add(ItemNovelty.getItem(ItemNovelty.VECHS));
		list.add(ItemNovelty.getItem(ItemNovelty.ZISTEAUSIGN));
		list.add(PotionMixture.getPotion(random, PotionMixture.ABSINTHE));
		list.add(PotionMixture.getPotion(random, PotionMixture.AURA));
		list.add(PotionMixture.getPotion(random, PotionMixture.COFFEE));
		list.add(PotionMixture.getPotion(random, PotionMixture.LAUDANUM));
		list.add(PotionMixture.getPotion(random, PotionMixture.MOONSHINE));
		list.add(PotionMixture.getPotion(random, PotionMixture.NECTAR));
		list.add(PotionMixture.getPotion(random, PotionMixture.RAGE));
		list.add(PotionMixture.getPotion(random, PotionMixture.STAMINA));
		list.add(PotionMixture.getPotion(random, PotionMixture.STOUT));
		list.add(PotionMixture.getPotion(random, PotionMixture.TEQUILA));
		list.add(PotionMixture.getPotion(random, PotionMixture.VILE));

		int i = 0;
		for (ItemStack is : list) {
			addItem(i, is);
			i++;
		}
	}
}
