package otd.gui;

import static otd.redux.util.MenuHelper.color;
import static otd.redux.util.MenuHelper.separator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.config.WorldConfig;
import otd.redux.util.ChatManager;
import otd.util.Skull;

import otd.util.I18n;

import otd.redux.util.MenuHelper;

public class LanguageGUI extends Content {
    public final static LanguageGUI instance = new LanguageGUI();

    private static final Skull.HeadData FLAG_US = new Skull.HeadData(
            "f4191b3a-3a3e-4ece-b1ba-43f7c8dba502",
            "52504ecd6d0a61b32a419f208af76eebb19e9bc40f85b06bfabdbe51aed43de");
    private static final Skull.HeadData FLAG_CN = new Skull.HeadData(
            "af081ea7-3b8e-4a24-aaac-dbec776ee903",
            "7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc");

    private final Content parent;

    private LanguageGUI() {
        super(MenuHelper.color(MenuHelper.SECONDARY) + "Language", 27);
        parent = null;
    }

    public LanguageGUI(Content parent) {
        super(MenuHelper.color(MenuHelper.SECONDARY) + "Language", 27);
        this.parent = parent;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init() {
        inv.clear();
        MenuHelper.fillBorder(this, 3);

        String currentlang = WorldConfig.wc.language;

        {
            ItemStack is = FLAG_US.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("en")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "English" + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "English");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Default language"));
            lores.add(MenuHelper.desc("Contributors: shadow_wind, roschreiber"));
            lores.add(MenuHelper.separator());
            if(currentlang.equals("en")) {
                lores.add(MenuHelper.color(MenuHelper.MUTED) + "Currently selected");
            } else {
                lores.add(MenuHelper.actionHint("Click to select"));
            }
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 3, is);
        }
        {
            ItemStack is = FLAG_CN.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("zhcn")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "Chinese" + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "Chinese");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Contributors: shadow_wind"));
            lores.add(MenuHelper.separator());
            if(currentlang.equals("zhcn")) {
                lores.add(MenuHelper.color(MenuHelper.MUTED) + "Currently selected");
            } else {
                lores.add(MenuHelper.actionHint("Click to select"));
            }
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 5, is);
        }

        addItem(2, 4, MenuHelper.back());
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof LanguageGUI)) 
            return;
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            kcancel(e);
            return;
        }

        kcancel(e);
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        LanguageGUI holder = (LanguageGUI) e.getInventory().getHolder();
        if (holder == null) return;

        String picked = null;
        if (slot == 12) picked = "en";
        if (slot == 14) picked = "zhcn";

        if (picked != null) {
            WorldConfig.wc.language = picked;
            WorldConfig.actualSave();
            I18n.init();
            ChatManager.getInstance().sendSuccess(p, "Language set to: " + picked);
            p.closeInventory();
            return;
        }

        if (slot == 22) {
            if (holder.parent != null) {
                holder.parent.openInventory(p);
            } else {
                p.closeInventory();
            }
        }
    }
}
