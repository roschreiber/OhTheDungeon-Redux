package otd.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.gui.customstruct.CustomDungeonList;
import otd.gui.storydungeon.PPDI_Config;
import otd.integration.PlaceholderAPI;
import otd.integration.WorldEdit;
import otd.util.Diagnostic;
import otd.util.I18n;

public class UtilMenu extends Content {
	public final static UtilMenu instance = new UtilMenu();

	private final Content parent;

	private UtilMenu() {
		super(I18n.instance.Util_Menu, 9);
		parent = null;
	}

	public UtilMenu(Content parent) {
		super(I18n.instance.Util_Menu, 9);
		this.parent = parent;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		{
			ItemStack is = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Automatic_Diagnostic);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Automatic_Diagnostic_Lore);

			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, is);
		}
		{
			ItemStack is = new ItemStack(Material.FEATHER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Config_Backup);

			is.setItemMeta(im);

			addItem(1, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Creative_Inventory);

			is.setItemMeta(im);

			addItem(2, is);
		}
		{
			ItemStack is = new ItemStack(WorldEdit.isReady() ? Material.STRUCTURE_BLOCK : Material.BARRIER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Custom_Dungeon);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Require_WorldEdit);

			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, is);
		}
		{
			ItemStack is = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.PAPI_Title);

			is.setItemMeta(im);
			addItem(4, is);
		}
		{
			ItemStack is = new ItemStack(Material.BOOKSHELF);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.PerPlayerDungeonInstance);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PerPlayerDungeonInstance_Lore);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(5, is);
		}
		{
			ItemStack is = new ItemStack(Material.LEVER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Back);

			is.setItemMeta(im);
			addItem(8, is);
		}
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof UtilMenu)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		UtilMenu holder = (UtilMenu) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (slot == 0) {
			p.closeInventory();
			Diagnostic.check(p);
		}
		if (slot == 1) {
			BackupGUI backup = new BackupGUI(holder);
			backup.openInventory(p);
		}
		if (slot == 2) {
			CreativeInventory ci = new CreativeInventory();
			ci.openInventory(p);
		}
		if (slot == 3) {
			if (!WorldEdit.isReady())
				return;
			CustomDungeonList ci = new CustomDungeonList(this);
			ci.openInventory(p);
		}
		if (slot == 4) {
			PlaceholderAPI.openBook(p);
		}
		if (slot == 5) {
			PPDI_Config cfg = new PPDI_Config();
			cfg.openInventory(p);
		}
		if (slot == 8) {
			holder.parent.openInventory(p);
		}
	}
}
