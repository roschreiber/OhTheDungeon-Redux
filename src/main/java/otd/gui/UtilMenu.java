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

import otd.redux.util.MenuHelper;

public class UtilMenu extends Content {
	public final static UtilMenu instance = new UtilMenu();

	private final Content parent;

	private UtilMenu() {
		super(MenuHelper.color(MenuHelper.PRIMARY) + I18n.instance.Util_Menu, 27);
		parent = null;
	}

	public UtilMenu(Content parent) {
		super(MenuHelper.color(MenuHelper.PRIMARY) + I18n.instance.Util_Menu, 27);
		this.parent = parent;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		MenuHelper.fillBorder(this, 3);
		{
			ItemStack is = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.INFO) + I18n.instance.Automatic_Diagnostic);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Automatic_Diagnostic_Lore);
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to run"));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(1, 1, is);
		}
		{
			ItemStack is = new ItemStack(Material.FEATHER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Config_Backup);
			
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to open"));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(1, 2, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.GOLD) + I18n.instance.Creative_Inventory);

			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to open"));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(1, 3, is);
		}
		{
			ItemStack is = new ItemStack(WorldEdit.isReady() ? Material.STRUCTURE_BLOCK : Material.BARRIER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Custom_Dungeon);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Require_WorldEdit);
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to open"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 4, is);
		}
		{
			ItemStack is = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.INFO) + I18n.instance.PAPI_Title);

			List<String> lores = new ArrayList<>();
        	lores.add(MenuHelper.separator());
        	lores.add(MenuHelper.actionHint("Click to view"));
        	im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, 5, is);
		}
		{
			ItemStack is = new ItemStack(Material.BOOKSHELF);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.PerPlayerDungeonInstance);
			
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
        	lores.add(MenuHelper.desc(I18n.instance.PerPlayerDungeonInstance_Lore));
        	lores.add(MenuHelper.separator());
        	lores.add(MenuHelper.actionHint("Click to open"));
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, 6, is);
		}
		addItem(1, 7, MenuHelper.back());
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
		if (slot == 10) {
			p.closeInventory();
			Diagnostic.check(p);
		}
		if (slot == 11) {
			BackupGUI backup = new BackupGUI(holder);
			backup.openInventory(p);
		}
		if (slot == 12) {
			CreativeInventory ci = new CreativeInventory();
			ci.openInventory(p);
		}
		if (slot == 13) {
			if (!WorldEdit.isReady())
				return;
			CustomDungeonList ci = new CustomDungeonList(this);
			ci.openInventory(p);
		}
		if (slot == 14) {
			PlaceholderAPI.openBook(p);
		}
		if (slot == 15) {
			PPDI_Config cfg = new PPDI_Config();
			cfg.openInventory(p);
		}
		if (slot == 16) {
			holder.parent.openInventory(p);
		}
	}
}
