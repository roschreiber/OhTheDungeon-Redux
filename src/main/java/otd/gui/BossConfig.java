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

import net.md_5.bungee.api.ChatColor;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
//import otd.integration.BossImpl;
import otd.integration.EcoBossesImpl;
import otd.integration.MythicMobsImpl;
import otd.util.I18n;
import otd.util.Wrap;

public class BossConfig extends Content {
	private final static int SLOT = 36;
	public static BossConfig instance = new BossConfig();

	private final String world;
	private final Content parent;

	private BossConfig() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public BossConfig(String world, Content parent) {
		super(I18n.instance.Boss_Config, SLOT);
		this.world = world;
		this.parent = parent;
	}

	@Override
	public void init() {
		if (WorldConfig.wc.dict.get(world) == null) {
			SimpleWorldConfig swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world, swc);
			WorldConfig.save();
		}
		show();
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof BossConfig)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		BossConfig holder = (BossConfig) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world == null)
			return;

		SimpleWorldConfig swc = WorldConfig.wc.dict.get(holder.world);

		if (slot == 13) {
			if (e.getClick().equals(ClickType.LEFT)) {
				swc.chance++;
				if (swc.chance > 100)
					swc.chance = 100;
				WorldConfig.save();
			}
			if (e.getClick().equals(ClickType.RIGHT)) {
				swc.chance--;
				if (swc.chance < 0)
					swc.chance = 0;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 20) {
			if (swc.boss != SimpleWorldConfig.BossType.Vanilla) {
				swc.boss = SimpleWorldConfig.BossType.Vanilla;
				WorldConfig.save();
				holder.init();
			}
		}
		/*if (slot == 21) {
			if (BossImpl.isBossReady()) {
				if (swc.boss != SimpleWorldConfig.BossType.Boss) {
					swc.boss = SimpleWorldConfig.BossType.Boss;
					WorldConfig.save();
					holder.init();
				}
			}
		}*/
		if (slot == 22) {
			if (MythicMobsImpl.isMythicMobsReady()) {
				if (swc.boss != SimpleWorldConfig.BossType.MythicMobs) {
					swc.boss = SimpleWorldConfig.BossType.MythicMobs;
					WorldConfig.save();
					holder.init();
				}
			}
		}
		if (slot == 23) {
			if (EcoBossesImpl.isEcoBossesReady()) {
				if (swc.boss != SimpleWorldConfig.BossType.EcoBosses) {
					swc.boss = SimpleWorldConfig.BossType.EcoBosses;
					WorldConfig.save();
					holder.init();
				}
			}
		}
		if (slot == 35) {
			holder.parent.openInventory(p);
		}
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		{
			ItemStack icon = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			for (int i = 0; i < SLOT; i++)
				addItem(i, icon);
		}
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world);
		{
			ItemStack icon = new ItemStack(Material.MUSIC_DISC_FAR);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Small_Boss_Rate);
			List<String> lores = new ArrayList<>();
			{
				for (String text : Wrap.wordWrap(I18n.instance.Small_Boss_Lore, 30)) {
					lores.add(ChatColor.AQUA + text);
				}
			}
			lores.add(ChatColor.AQUA + I18n.instance.Current_Chance + " : " + Integer.toString(swc.chance) + "%");
			lores.add(ChatColor.YELLOW + I18n.instance.Amount_Item_Tip1);
			lores.add(ChatColor.YELLOW + I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(1, 4, icon);
		}
		{
			Material type;
			if (swc.boss == SimpleWorldConfig.BossType.Vanilla)
				type = Material.ARROW;
			else
				type = Material.BARRIER;
			ItemStack icon = new ItemStack(type);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Use_Vanilla);
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.AQUA + I18n.instance.Use_Vanilla_Lore);
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(2, 2, icon);
		}
		{
			Material type;
			if (swc.boss == SimpleWorldConfig.BossType.Boss)
				type = Material.ARROW;
			else
				type = Material.BARRIER;
			ItemStack icon = new ItemStack(type);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Use_BossPlugin);
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.RED + I18n.instance.Require_BossPlugin);
			lores.add(ChatColor.AQUA + I18n.instance.Use_BossPlugin_Lore);
			{
				StringBuilder sb = new StringBuilder();
				for (String lore : I18n.instance.Use_BossPlugin_Lores) {
					sb.append(lore);
					sb.append("\n");
				}
				for (String text : Wrap.wordWrap(sb.toString(), 30)) {
					lores.add(ChatColor.AQUA + text);
				}
			}
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(2, 3, icon);
		}
		{
			Material type;
			if (swc.boss == SimpleWorldConfig.BossType.MythicMobs)
				type = Material.ARROW;
			else
				type = Material.BARRIER;
			ItemStack icon = new ItemStack(type);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Use_MythicMobs);
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.RED + I18n.instance.Require_MythicMobs);
			lores.add(ChatColor.AQUA + I18n.instance.Use_MythicMobs_Lore);
			{
				StringBuilder sb = new StringBuilder();
				for (String lore : I18n.instance.Use_MythicMobs_Lores) {
					sb.append(lore);
					sb.append("\n");
				}
				for (String text : Wrap.wordWrap(sb.toString(), 30)) {
					lores.add(ChatColor.AQUA + text);
				}
			}
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(2, 4, icon);
		}
		{
			Material type;
			if (swc.boss == SimpleWorldConfig.BossType.EcoBosses)
				type = Material.ARROW;
			else
				type = Material.BARRIER;
			ItemStack icon = new ItemStack(type);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Use_EcoBosses);
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.RED + I18n.instance.Require_EcoBosses);
			lores.add(ChatColor.AQUA + I18n.instance.Use_EcoBosses_Lore);
			{
				StringBuilder sb = new StringBuilder();
				for (String lore : I18n.instance.Use_EcoBosses_Lores) {
					sb.append(lore);
					sb.append("\n");
				}
				for (String text : Wrap.wordWrap(sb.toString(), 30)) {
					lores.add(ChatColor.AQUA + text);
				}
			}
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(2, 5, icon);
		}
		{
			ItemStack icon = new ItemStack(Material.LEVER);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(I18n.instance.Apply);
			icon.setItemMeta(im);
			addItem(3, 8, icon);
		}
	}
}
