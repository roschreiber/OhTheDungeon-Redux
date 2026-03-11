package otd.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.Main;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.redux.util.ChatManager;
import otd.util.ChatUtil;
import otd.util.I18n;

import otd.redux.util.MenuHelper;

public class WorldParameter extends Content {
	private final static int SLOT = 45;
	public static WorldParameter instance = new WorldParameter();

	private final String world;
	private final Content parent;

	private int bottom;
	private int sealevel;

	private WorldParameter() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public WorldParameter(String world, Content parent) {
		super(MenuHelper.color(MenuHelper.DANGER) + I18n.instance.WorldParameter, SLOT);
		this.world = world;
		this.parent = parent;

		if (WorldConfig.wc.dict.get(world) == null) {
			SimpleWorldConfig swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world, swc);
			WorldConfig.save();
		}
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world);
		bottom = swc.worldParameter.bottom;
		sealevel = swc.worldParameter.sealevel;
	}

	private final static Material BOTTOM = Material.BEDROCK;
	private final static Material SEALEVEL = Material.WATER_BUCKET;

	@Override
	public void init() {
		show();
	}

	public final static void sendURL(Player p) {
		ChatUtil.sendMessage(p, I18n.instance.WorldParameter_Sign,
				"https://github.com/OhTheDungeon/OhTheDungeon/blob/main/docs/World_Parameter.md");
		p.closeInventory();
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof WorldParameter)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		WorldParameter holder = (WorldParameter) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world == null)
			return;

		if (slot == 0) {
			sendURL(p);
		}
		if (slot == 8) {
			holder.bottom = 5;
			holder.sealevel = 63;
			holder.init();
		}
		if (slot == 20) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.bottom++;
			}
			if (e.getClick().equals(ClickType.RIGHT)) {
				holder.bottom--;
			}
			holder.init();
		}
		if (slot == 24) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.sealevel++;
			}
			if (e.getClick().equals(ClickType.RIGHT)) {
				holder.sealevel--;
			}
			holder.init();
		}
		if (slot == 43) {
			holder.parent.openInventory(p);
		}
		if (slot == 44) {
			if (holder.sealevel - holder.bottom <= 50) {
				ChatManager.getInstance().sendError(p, I18n.instance.WorldParameterExtra);
			} else {
				SimpleWorldConfig swc = WorldConfig.wc.dict.get(holder.world);
				swc.worldParameter.bottom = holder.bottom;
				swc.worldParameter.sealevel = holder.sealevel;
				WorldConfig.save();
				Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
					holder.parent.openInventory(p);
				}, 1L);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		MenuHelper.fillBorder(this, 5);
		{
			ItemStack icon = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.INFO) + I18n.instance.WorldParameter_Sign);
			icon.setItemMeta(im);

			addItem(0, 0, icon);
		}
		addItem(0, 8, MenuHelper.reset());
		{
			ItemStack icon = new ItemStack(BOTTOM);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.DARK) + I18n.instance.World_Bottom);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.value(I18n.instance.Current_Value, this.bottom));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip1));
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip2));
			im.setLore(lores);
			icon.setItemMeta(im);

			addItem(2, 2, icon);
		}
		{
			ItemStack icon = new ItemStack(SEALEVEL);
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.World_Sealevel);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.value(I18n.instance.Current_Value, this.sealevel));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip1));
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip2));
			im.setLore(lores);
			icon.setItemMeta(im);

			addItem(2, 6, icon);
		}
		addItem(4, 7, MenuHelper.cancel());
		addItem(4, 8, MenuHelper.apply());
	}

}
