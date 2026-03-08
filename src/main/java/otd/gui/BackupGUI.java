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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.Main;
import otd.config.WorldConfig;
import otd.redux.util.ChatManager;
import otd.util.I18n;
import otd.redux.util.MenuHelper;

/**
 *
 * @author shadow
 */
public class BackupGUI extends Content {
	public final static BackupGUI instance = new BackupGUI();
	private final static int SLOT = 54;
	private final static Material BACKUP = Material.PAPER;
	public int offset;
	public final Content parent;
	public List<File> backupList = new ArrayList<>();

	private BackupGUI() {
		super(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Config_Backup, SLOT);
		parent = null;
		offset = 0;
	}

	public BackupGUI(Content parent) {
		super(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Config_Backup, SLOT);
		this.parent = parent;
		offset = 0;
	}

	public static void initBackupFolder() {
		File backup = new File(Main.instance.getDataFolder(), "backups");
		if (!backup.exists()) {
			backup.mkdir();
		}
	}

	private static String getFolderName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return "Backup_" + sdf.format(new Date());
	}

	private static void createBackup(Player p) {
		WorldConfig.actualSave();

		String folderName = getFolderName();
		File backupDir = new File(Main.instance.getDataFolder(), "backups" + File.separator + folderName);
		backupDir.mkdirs();

		try {
			File globalFile = new File(Main.instance.getDataFolder(), "global.yml");
			if (globalFile.exists()) {
				Files.copy(globalFile.toPath(), new File(backupDir, "global.yml").toPath(), StandardCopyOption.REPLACE_EXISTING);
			}

			File worldsDir = new File(Main.instance.getDataFolder(), "worlds");
			if (worldsDir.exists()) {
				File worldsBackup = new File(backupDir, "worlds");
				worldsBackup.mkdirs();

				File[] worldFiles = worldsDir.listFiles();
				if (worldFiles != null) {
					for (File f : worldFiles) {
						if (f.getName().endsWith(".yml")) {
							Files.copy(f.toPath(), new File(worldsBackup, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}
			}

			ChatManager.getInstance().sendSuccess(p, I18n.instance.Create_New_Backup + ": " + folderName);
		} catch (IOException e) {
			ChatManager.getInstance().sendError(p, I18n.instance.Fail_To_Create_Backup);
		}
	}

	private static void restoreBackup(File backupDir, Player p) {
		if (!backupDir.exists()) {
			ChatManager.getInstance().sendError(p, I18n.instance.Fail_To_Restore_Backup);
			return;
		}

		try {
			File backupGlobal = new File(backupDir, "global.yml");
			if (backupGlobal.exists()) {
				File target = new File(Main.instance.getDataFolder(), "global.yml");
				Files.copy(backupGlobal.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}

			File backupWorlds = new File(backupDir, "worlds");
			if (backupWorlds.exists()) {
				File worldsDir = new File(Main.instance.getDataFolder(), "worlds");
				if (!worldsDir.exists()) worldsDir.mkdirs();

				File[] worldFiles = backupWorlds.listFiles();
				if (worldFiles != null) {
					for (File f : worldFiles) {
						if (f.getName().endsWith(".yml")) {
							Files.copy(f.toPath(), new File(worldsDir, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}
			}

			WorldConfig loaded = WorldConfig.yamlStorage.loadAll();
			loaded.dungeon_world = WorldConfig.wc.dungeon_world;
			WorldConfig.wc = loaded;

			ChatManager.getInstance().sendSuccess(p, "Restored: " + backupDir.getName());
		} catch (Exception e) {
			ChatManager.getInstance().sendError(p, I18n.instance.Fail_To_Restore_Backup);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof BackupGUI)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);

		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;

		int slot = e.getRawSlot();
		BackupGUI holder = (BackupGUI) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 0) {
			createBackup(p);
			holder.init();
		}
		if (slot == 6) {
			holder.offset--;
			if (holder.offset < 0)
				holder.offset = 0;
			holder.init();
		}
		if (slot == 7) {
			holder.offset++;
			holder.init();
		}
		if (slot == 8) {
			holder.parent.openInventory(p);
		}
		if (slot >= 18 && slot <= 53) {
			int index = holder.offset * 36 + (slot - 18);
			if (index >= 0 && index < holder.backupList.size()) {
				restoreBackup(holder.backupList.get(index), p);
				p.closeInventory();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		backupList.clear();

		File backupsDir = new File(Main.instance.getDataFolder(), "backups");
		File[] folders = backupsDir.listFiles();
		if (folders != null) {
			for (int i = 0; i < folders.length; i++) {
				if (folders[i].isDirectory()) {
					backupList.add(folders[i]);
				}
			}
		}

		MenuHelper.fillRow(this, 0);

		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.SUCCESS) + I18n.instance.Create_New_Backup);
			is.setItemMeta(im);
			addItem(0, is);
		}

		addItem(6, MenuHelper.prev(offset + 1));
		addItem(7, MenuHelper.next(offset + 1));
		addItem(8, MenuHelper.back());

		MenuHelper.fillSeparatorRow(this, 1);

		int index = 18;
		int i = offset * 36;
		while (index < SLOT && i < backupList.size()) {
			File f = backupList.get(i);

			ItemStack is = new ItemStack(BACKUP);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + f.getName());
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.muted(I18n.instance.Click_To_Restore));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(index, is);

			index++;
			i++;
		}
	}
}
