package otd.gui;

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
        super(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Language, 36);
        parent = null;
    }

    public LanguageGUI(Content parent) {
        super(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Language, 36);
        this.parent = parent;
    }

    @SuppressWarnings("deprecation")
    private void addLanguage(int row, int col, Skull.HeadData flag, String code, String nativeName, String contributors, boolean unfinished) {
        ItemStack is = flag.getItem();
        ItemMeta im = is.getItemMeta();
        boolean selected = WorldConfig.wc.language.equals(code);
        if (selected) {
            im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + nativeName + " "
                    + MenuHelper.color(MenuHelper.MUTED) + "[" + I18n.instance.Currently_Selected + "]");
        } else {
            im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + nativeName);
        }
        List<String> lores = new ArrayList<>();
        lores.add(MenuHelper.separator());
        lores.add(MenuHelper.desc("Contributors: " + contributors));
        if (unfinished) {
            lores.add(MenuHelper.color(MenuHelper.WARNING) + "* This translation is unfinished! *");
        }
        lores.add(MenuHelper.separator());
        if (selected) {
            lores.add(MenuHelper.color(MenuHelper.MUTED) + I18n.instance.Currently_Selected);
        } else {
            lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Select));
        }
        im.setLore(lores);
        is.setItemMeta(im);
        addItem(row, col, is);
    }

    @Override
    public void init() {
        inv.clear();
        MenuHelper.fillBorder(this, 4);

        addLanguage(1, 2, FLAG_US, "en", "English", "shadow_wind, roschreiber", false);
        addLanguage(1, 4, FLAG_CN, "zh-CN", "简体中文 (Chinese)", "shadow_wind", true);
        addLanguage(1, 6, FLAG_DE, "de-DE", "Deutsch (German)", "roschreiber", true);
        addLanguage(2, 3, FLAG_RU, "ru-RU", "Русский (Russian)", "none", true);
        addLanguage(2, 5, FLAG_ES, "es-ES", "Español (Spanish)", "mananite", true);

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
            ChatManager.getInstance().sendSuccess(p, I18n.instance.Language_Set + " " + picked);
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
