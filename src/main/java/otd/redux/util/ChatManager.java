package otd.redux.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import otd.Main;

public class ChatManager {
    
    private static final String DEFAULT_PREFIX_STRING = "&8[&2OTD-&eREDUX&8] &8| ";
    
    private static final String INFO_INDICATOR = ChatColor.BLUE + "ℹ " + ChatColor.RESET;
    private static final String SUCCESS_INDICATOR = ChatColor.GREEN + "✔ " + ChatColor.RESET;
    private static final String WARNING_INDICATOR = ChatColor.GOLD + "⚠ " + ChatColor.RESET;
    private static final String ERROR_INDICATOR = ChatColor.RED + "✘ " + ChatColor.RESET;
    private static final String DEBUG_INDICATOR = ChatColor.DARK_GREEN + "🧪 " + ChatColor.RESET;
    private static final String DUNGEON_INDICATOR = ChatColor.LIGHT_PURPLE + "⚔ " + ChatColor.RESET;
    private static final String SYSTEM_INDICATOR = ChatColor.GRAY + "⛏ " + ChatColor.RESET;
    
    private String prefix;
    
    private static ChatManager instance;
    
    private final JavaPlugin plugin;
    
    private File configFile;
    private FileConfiguration config;
    
    public enum MessageType {
        NORMAL,
        INFO,
        SUCCESS,
        WARNING,
        ERROR,
        DEBUG,
        DUNGEON,
        SYSTEM,
        IMPORTANT
    }
    
    private ChatManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager(Main.instance);
        }
        return instance;
    }
    
    public void loadConfig() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        
        configFile = new File(dataFolder, "chat_config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                saveDefaultPrefix();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create chat_config.yml: " + e.getMessage());
            }
        } else {
            config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
            if (config.contains("prefix")) {
                String rawPrefix = config.getString("prefix", DEFAULT_PREFIX_STRING);
                prefix = ChatColor.translateAlternateColorCodes('&', rawPrefix);
            } else {
                saveDefaultPrefix();
            }
        }
    }
    
    private void saveDefaultPrefix() {
        config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        config.set("prefix", DEFAULT_PREFIX_STRING);
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save default chat prefix: " + e.getMessage());
        }
        prefix = ChatColor.translateAlternateColorCodes('&', DEFAULT_PREFIX_STRING);
    }
    
    public void updatePrefix(String newPrefix) {
        if (config == null) {
            loadConfig();
        }
        config.set("prefix", newPrefix);
        try {
            config.save(configFile);
            prefix = ChatColor.translateAlternateColorCodes('&', newPrefix);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save chat prefix: " + e.getMessage());
        }
    }
    
    public String getRawPrefix() {
        return config.getString("prefix", DEFAULT_PREFIX_STRING);
    }
    
    public String formatMessage(String message, MessageType type) {
        switch (type) {
            case INFO:
                return prefix + INFO_INDICATOR + ChatColor.BLUE + message;
            case SUCCESS:
                return prefix + SUCCESS_INDICATOR + ChatColor.GREEN + message;
            case WARNING:
                return prefix + WARNING_INDICATOR + ChatColor.GOLD + message;
            case ERROR:
                return prefix + ERROR_INDICATOR + ChatColor.RED + message;
            case DEBUG:
                return prefix + DEBUG_INDICATOR + ChatColor.GRAY + message;
            case DUNGEON:
                return prefix + DUNGEON_INDICATOR + ChatColor.LIGHT_PURPLE + message;
            case SYSTEM:
                return prefix + SYSTEM_INDICATOR + ChatColor.AQUA + message;
            case IMPORTANT:
                return prefix + ChatColor.YELLOW + "➤ " + 
                       ChatColor.WHITE + message + 
                       ChatColor.YELLOW + " ◄";
            case NORMAL:
            default:
                return prefix + message;
        }
    }
    
    public void sendMessage(Player player, String message, MessageType type) {
        if (player != null && player.isOnline()) {
            player.sendMessage(formatMessage(message, type));
        }
    }
    
    public void sendMessage(CommandSender sender, String message, MessageType type) {
        if (sender != null) {
            sender.sendMessage(formatMessage(message, type));
        }
    }
    
    public void sendMessage(Player player, String message) {
        sendMessage(player, message, MessageType.NORMAL);
    }
    
    public void sendInfo(Player player, String message) {
        sendMessage(player, message, MessageType.INFO);
    }
    
    public void sendSuccess(Player player, String message) {
        sendMessage(player, message, MessageType.SUCCESS);
    }
    
    public void sendWarning(Player player, String message) {
        sendMessage(player, message, MessageType.WARNING);
    }
    
    public void sendError(Player player, String message) {
        sendMessage(player, message, MessageType.ERROR);
    }
    
    public void sendDebug(Player player, String message) {
        sendMessage(player, message, MessageType.DEBUG);
    }
    
    public void sendDungeonMessage(Player player, String message) {
        sendMessage(player, message, MessageType.DUNGEON);
    }
    
    public void sendSystemMessage(Player player, String message) {
        sendMessage(player, message, MessageType.SYSTEM);
    }
    
    public void sendClickableLink(Player player, String message, String url) {
        if (player != null && player.isOnline()) {
            String formattedMessage = prefix + ChatColor.BLUE + message;
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
                "/tellraw " + player.getName() + " {\"text\":\"" + formattedMessage + 
                "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + url + "\"}}"
            );
        }
    }
    
    public void logToConsole(String message, MessageType type) {
        Bukkit.getConsoleSender().sendMessage(formatMessage(message, type));
    }
    
    public void logToConsole(String message) {
        logToConsole(message, MessageType.NORMAL);
    }

    public void broadcastMessage(String message, MessageType type) {
        String formattedMessage = formatMessage(message, type);
        Bukkit.broadcastMessage(formattedMessage);
    }
} 