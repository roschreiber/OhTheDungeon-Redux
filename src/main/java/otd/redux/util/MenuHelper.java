package otd.redux.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import otd.gui.Content;
import otd.util.I18n;

public class MenuHelper {

	public static final String PRIMARY    = "#9B59B6"; // Purple
	public static final String SECONDARY  = "#3498DB"; // Blue
	public static final String ACCENT     = "#E67E22"; // Orange
	public static final String SUCCESS    = "#2ECC71"; // Green
	public static final String DANGER     = "#E74C3C"; // Red
	public static final String WARNING    = "#F1C40F"; // Yellow
	public static final String INFO       = "#1ABC9C"; // Teal
	public static final String MUTED      = "#7F8C8D"; // Gray
	public static final String LIGHT      = "#BDC3C7"; // Light gray
	public static final String VALUE_CLR  = "#00D2FF"; // Cyan
	public static final String DARK       = "#34495E"; // Dark blue-gray
	public static final String GOLD       = "#FFD700"; // Gold
	public static final String PINK       = "#FF69B4"; // Pink

	public static final Material FILLER_DARK   = Material.BLACK_STAINED_GLASS_PANE;
	public static final Material FILLER_ACCENT = Material.PURPLE_STAINED_GLASS_PANE;
	public static final Material FILLER_BORDER = Material.GRAY_STAINED_GLASS_PANE;

	public static final Material TOGGLE_ON  = Material.LIME_DYE;
	public static final Material TOGGLE_OFF = Material.GRAY_DYE;

	// for color gradients, taken from the web
	public static String gradient(String text, String hexStart, String hexEnd) {
		if (text == null || text.isEmpty()) return "";
		int[] start = hexToRgb(hexStart);
		int[] end = hexToRgb(hexEnd);
		StringBuilder sb = new StringBuilder();
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			if (c == ' ') {
				sb.append(' ');
				continue;
			}
			float ratio = len == 1 ? 0f : (float) i / (len - 1);
			int r = Math.round(start[0] + ratio * (end[0] - start[0]));
			int g = Math.round(start[1] + ratio * (end[1] - start[1]));
			int b = Math.round(start[2] + ratio * (end[2] - start[2]));
			sb.append(ChatColor.of(String.format("#%02x%02x%02x", r, g, b)));
			sb.append(c);
		}
		return sb.toString();
	}

	public static String color(String hex) {
		return ChatColor.of(hex).toString();
	}

	private static int[] hexToRgb(String hex) {
		hex = hex.replace("#", "");
		return new int[] {
			Integer.parseInt(hex.substring(0, 2), 16),
			Integer.parseInt(hex.substring(2, 4), 16),
			Integer.parseInt(hex.substring(4, 6), 16)
		};
	}

	public static ItemStack filler() {
		return filler(FILLER_DARK);
	}

	public static ItemStack filler(Material material) {
		ItemStack is = new ItemStack(material);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(" ");
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack back() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(DANGER) + "← " + I18n.instance.Back);
		List<String> lore = new ArrayList<>();
		lore.add(muted("Click to return"));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack prev(int page) {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(WARNING) + "« " + I18n.instance.Previous);
		List<String> lore = new ArrayList<>();
		lore.add(value(I18n.instance.Current_Page, page));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack next(int page) {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(WARNING) + I18n.instance.Next + " »");
		List<String> lore = new ArrayList<>();
		lore.add(value(I18n.instance.Current_Page, page));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack apply() {
		ItemStack is = new ItemStack(Material.EMERALD);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(SUCCESS) + "✔ " + I18n.instance.Apply);
		List<String> lore = new ArrayList<>();
		lore.add(muted("Click to save changes"));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack cancel() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(DANGER) + "✘ " + I18n.instance.Cancel);
		List<String> lore = new ArrayList<>();
		lore.add(muted("Click to cancel"));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack reset() {
		ItemStack is = new ItemStack(Material.WATER_BUCKET);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(WARNING) + "⟳ " + I18n.instance.Reset);
		List<String> lore = new ArrayList<>();
		lore.add(muted("Click to reset to defaults"));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}


	public static Material toggleMaterial(boolean enabled) {
		return enabled ? TOGGLE_ON : TOGGLE_OFF;
	}

	public static String status(boolean enabled) {
		if (enabled) {
			return color(MUTED) + I18n.instance.Status + ": " + color(SUCCESS) + "✔ " + I18n.instance.Enable;
		} else {
			return color(MUTED) + I18n.instance.Status + ": " + color(DANGER) + "✘ " + I18n.instance.Disable;
		}
	}

	public static String separator() {
		return color("#3d3d3d") + "━━━━━━━━━━━━━━━━━━━━━━";
	}

	public static String muted(String text) {
		return color(MUTED) + text;
	}

	public static String info(String text) {
		return color(INFO) + text;
	}
	public static String accent(String text) {
		return color(ACCENT) + text;
	}

	public static String desc(String text) {
		return color(LIGHT) + text;
	}

	public static String actionHint(String text) {
		return color(MUTED) + "▸ " + color(LIGHT) + text;
	}

	public static String value(String label, String val) {
		return color(MUTED) + label + ": " + color(VALUE_CLR) + val;
	}

	public static String value(String label, int val) {
		return value(label, String.valueOf(val));
	}

	public static String value(String label, double val) {
		return value(label, String.format("%.1f", val));
	}

	public static String fraction(int value, int total) {
		return color(VALUE_CLR) + value + color(MUTED) + " / " + color(VALUE_CLR) + total;
	}

	public static void fillAll(Content content, int size) {
		ItemStack dark = filler();
		for (int i = 0; i < size; i++) {
			content.addItem(i, dark);
		}
	}

	public static void fillBorder(Content content, int rows) {
		int size = rows * 9;
		ItemStack dark = filler();
		ItemStack accentPane = filler(FILLER_ACCENT);

		for (int i = 0; i < size; i++) {
			content.addItem(i, dark);
		}

		content.addItem(0, accentPane);
		content.addItem(8, accentPane);
		content.addItem(size - 9, accentPane);
		content.addItem(size - 1, accentPane);
	}

	public static void fillRow(Content content, int row) {
		ItemStack dark = filler();
		for (int col = 0; col < 9; col++) {
			content.addItem(row * 9 + col, dark);
		}
	}

	public static void fillSeparatorRow(Content content, int row) {
		ItemStack sep = filler(FILLER_BORDER);
		for (int col = 0; col < 9; col++) {
			content.addItem(row * 9 + col, sep);
		}
	}
}
