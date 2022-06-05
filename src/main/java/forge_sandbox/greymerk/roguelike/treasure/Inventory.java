package forge_sandbox.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Inventory {
	private InventoryHolder chest;
	List<Integer> shuffledSlots;

	public Inventory(Random rand, InventoryHolder chest) {
		this.chest = chest;
		this.shuffledSlots = new ArrayList<>();
		for (int i = 0; i < this.getInventorySize(); ++i) {
			shuffledSlots.add(i);
		}

		Collections.shuffle(shuffledSlots, rand);
	}

	public boolean setRandomEmptySlot(ItemStack item) {
//            Bukkit.getLogger().log(Level.SEVERE, "#1");
		int slot = this.getRandomEmptySlot();
		if (slot < 0)
			return false;
//                Bukkit.getLogger().log(Level.SEVERE, "#2");
		return setInventorySlot(slot, item);
	}

	private int getRandomEmptySlot() {
		for (int slot : this.shuffledSlots) {
			if (isEmptySlot(slot))
				return slot;
		}
		return -1;
	}

	public boolean isEmptySlot(int slot) {
		try {
			ItemStack item = chest.getInventory().getItem(slot);
			return item == null || item.getType() == Material.AIR;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean setInventorySlot(int slot, ItemStack item) {
		try {
//                    Bukkit.getLogger().log(Level.SEVERE, slot + ":" + item.toString());
			chest.getInventory().setItem(slot, item);
			return true;
		} catch (NullPointerException e) {
//                    Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
			return false;
		}
	}

	public int getInventorySize() {

		if (chest == null) {
			return 0;
		}

		try {
			return chest.getInventory().getSize();
		} catch (NullPointerException e) {
			return 0;
		}
	}
}
