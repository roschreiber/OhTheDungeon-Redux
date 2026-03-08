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
    private static final Skull.HeadData FLAG_DE = new Skull.HeadData(
            "2afe241c-e6c4-482f-aa68-9feb676e1ad1",
            "5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f");
    private static final Skull.HeadData FLAG_RU = new Skull.HeadData(
            "fbe62a93-e7f7-46d2-ba24-b3e1c08d9ad3",
            "16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad");
    private static final Skull.HeadData FLAG_ES = new Skull.HeadData(
            "d44b2ac7-561e-42e1-ade7-399f8d4d192b",
            "c2d730b6dda16b584783b63d082a80049b5fa70228aba4ae884c2c1fc0c3a8bc");


    private final Content parent;

    private LanguageGUI() {
        super(MenuHelper.color(MenuHelper.SECONDARY) + "Language", 36);
        parent = null;
    }

    public LanguageGUI(Content parent) {
        super(MenuHelper.color(MenuHelper.SECONDARY) + "Language", 36);
        this.parent = parent;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init() {
        inv.clear();
        MenuHelper.fillBorder(this, 4);

        String currentlang = WorldConfig.wc.language;

        {
            ItemStack is = FLAG_US.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("en")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "English " + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
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
            addItem(1, 2, is);
        }
        {
            ItemStack is = FLAG_CN.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("zh-CN")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "Chinese " + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "Chinese");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Contributors: shadow_wind"));
            lores.add(MenuHelper.color(MenuHelper.WARNING) + "* This translation is unfinished! *");
            lores.add(MenuHelper.separator());
            if(currentlang.equals("zh-CN")) {
                lores.add(MenuHelper.color(MenuHelper.MUTED) + "Currently selected");
            } else {
                lores.add(MenuHelper.actionHint("Click to select"));
            }
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 4, is);
        }
        {
            ItemStack is = FLAG_DE.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("de-DE")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "German " + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "German");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Contributors: roschreiber"));
            lores.add(MenuHelper.color(MenuHelper.WARNING) + "* This translation is unfinished! *");
            lores.add(MenuHelper.separator());
            if(currentlang.equals("de-DE")) {
                lores.add(MenuHelper.color(MenuHelper.MUTED) + "Currently selected");
            } else {
                lores.add(MenuHelper.actionHint("Click to select"));
            }
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 6, is);
        }
        {
            ItemStack is = FLAG_RU.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("ru-RU")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "Russian " + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "Russian");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Contributors: none"));
            lores.add(MenuHelper.color(MenuHelper.WARNING) + "* This language is unfinished! *");
            lores.add(MenuHelper.separator());
            lores.add(currentlang.equals("ru-RU") ? MenuHelper.color(MenuHelper.MUTED) + "Currently selected" : MenuHelper.actionHint("Click to select"));
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(2, 3, is);
        }
        {
            ItemStack is = FLAG_ES.getItem();
            ItemMeta im = is.getItemMeta();
            if (currentlang.equals("es-ES")) {
                im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + "Spanish " + MenuHelper.color(MenuHelper.MUTED) + "[Selected]");
            } else {
                im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + "Spanish");
            }
            List<String> lores = new ArrayList<>();
            lores.add(MenuHelper.separator());
            lores.add(MenuHelper.desc("Contributors: none"));
            lores.add(MenuHelper.color(MenuHelper.WARNING) + "* This language is unfinished! *");
            lores.add(MenuHelper.separator());
            lores.add(currentlang.equals("es-ES") ? MenuHelper.color(MenuHelper.MUTED) + "Currently selected" : MenuHelper.actionHint("Click to select"));
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(2, 5, is);
        }


        addItem(3, 4, MenuHelper.back());
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
        if (slot == 11) picked = "en";
        if (slot == 13) picked = "zh-CN";
        if (slot == 15) picked = "de-DE";
        if (slot == 21) picked = "ru-RU";
        if (slot == 23) picked = "es-ES";

        if (picked != null) {
            WorldConfig.wc.language = picked;
            WorldConfig.actualSave();
            I18n.init();
            ChatManager.getInstance().sendSuccess(p, "Language set to: " + picked);
            p.closeInventory();
            return;
        }

        if (slot == 31) {
            if (holder.parent != null) {
                holder.parent.openInventory(p);
            } else {
                p.closeInventory();
            }
        }
    }
}
