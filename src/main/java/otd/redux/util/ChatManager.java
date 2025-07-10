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

/**
 * ChatManager - A simplified message handling system for OhTheDungeon-Redux
 * Provides methods to send formatted messages with consistent styling and prefixes
 */
public class ChatManager {
    
    // Default prefix as a string for config saving
    private static final String DEFAULT_PREFIX_STRING = "&8[&2OTD-&eREDUX&8] &8| ";
    
    // Message type indicators
    private static final String INFO_INDICATOR = ChatColor.BLUE + "ℹ " + ChatColor.RESET;
    private static final String SUCCESS_INDICATOR = ChatColor.GREEN + "✔ " + ChatColor.RESET;
    private static final String WARNING_INDICATOR = ChatColor.GOLD + "⚠ " + ChatColor.RESET;
    private static final String ERROR_INDICATOR = ChatColor.RED + "✘ " + ChatColor.RESET;
    private static final String DEBUG_INDICATOR = ChatColor.DARK_GREEN + "🧪 " + ChatColor.RESET;
    private static final String DUNGEON_INDICATOR = ChatColor.LIGHT_PURPLE + "⚔ " + ChatColor.RESET;
    private static final String SYSTEM_INDICATOR = ChatColor.GRAY + "⛏ " + ChatColor.RESET;
    
    // The configurable prefix that can be changed by users
    private String prefix;
    
    // Singleton instance
    private static ChatManager instance;
    
    // JavaPlugin instance
    private final JavaPlugin plugin;
    
    // Config file for storing prefix
    private File configFile;
    private FileConfiguration config;
    
    /**
     * Message types for different categories of messages, can be used to format messages
     */
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
    
    /**
     * Private constructor - use getInstance() instead
     */
    private ChatManager(JavaPlugin plugin) {
        this.plugin = plugin;
        
        // Load config
        loadConfig();
    }
    
    /**
     * Get the singleton instance
     * @return The ChatManager instance
     */
    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager(Main.instance);
        }
        return instance;
    }
    
    /**
     * Load the prefix from config
     */
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
    
    /**
     * Save the default prefix to config
     */
    private void saveDefaultPrefix() {
        config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        config.set("prefix", DEFAULT_PREFIX_STRING);
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save default chat prefix: " + e.getMessage());
        }
        
        // Set the default prefix after saving
        prefix = ChatColor.translateAlternateColorCodes('&', DEFAULT_PREFIX_STRING);
    }
    
    /**
     * Update the prefix in the config
     * @param newPrefix The new prefix to use
     */
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
    
    /**
     * Get the current raw prefix string (with color codes)
     * @return The raw prefix string
     */
    public String getRawPrefix() {
        return config.getString("prefix", DEFAULT_PREFIX_STRING);
    }
    
    /**
     * Format a message with the appropriate prefix and color based on the message type
     * @param message The message to format
     * @param type The type of message
     * @return The formatted message
     */
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
    
    /**
     * Send a message to a player
     * @param player The player to send the message to
     * @param message The message to send
     * @param type The message type
     */
    public void sendMessage(Player player, String message, MessageType type) {
        if (player != null && player.isOnline()) {
            player.sendMessage(formatMessage(message, type));
        }
    }
    
    /**
     * Send a message to a command sender (player or console)
     * @param sender The command sender to send the message to
     * @param message The message to send
     * @param type The message type
     */
    public void sendMessage(CommandSender sender, String message, MessageType type) {
        if (sender != null) {
            sender.sendMessage(formatMessage(message, type));
        }
    }
    
    /**
     * Send a normal message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendMessage(Player player, String message) {
        sendMessage(player, message, MessageType.NORMAL);
    }
    
    /**
     * Send an info message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendInfo(Player player, String message) {
        sendMessage(player, message, MessageType.INFO);
    }
    
    /**
     * Send a success message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendSuccess(Player player, String message) {
        sendMessage(player, message, MessageType.SUCCESS);
    }
    
    /**
     * Send a warning message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendWarning(Player player, String message) {
        sendMessage(player, message, MessageType.WARNING);
    }
    
    /**
     * Send an error message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendError(Player player, String message) {
        sendMessage(player, message, MessageType.ERROR);
    }
    
    /**
     * Send a debug message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendDebug(Player player, String message) {
        sendMessage(player, message, MessageType.DEBUG);
    }
    
    /**
     * Send a dungeon-related message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendDungeonMessage(Player player, String message) {
        sendMessage(player, message, MessageType.DUNGEON);
    }
    
    /**
     * Send a system message to a player
     * @param player The player to send the message to
     * @param message The message to send
     */
    public void sendSystemMessage(Player player, String message) {
        sendMessage(player, message, MessageType.SYSTEM);
    }
    
    /**
     * Send a clickable link message to a player
     * @param player The player to send the message to
     * @param message The message to send
     * @param url The URL to open when clicked
     */
    public void sendClickableLink(Player player, String message, String url) {
        if (player != null && player.isOnline()) {
            String formattedMessage = prefix + ChatColor.BLUE + message;
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
                "/tellraw " + player.getName() + " {\"text\":\"" + formattedMessage + 
                "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + url + "\"}}");
        }
    }
    
    
    /**
     * Send a message to the console
     * @param message The message to send
     * @param type The message type
     */
    public void logToConsole(String message, MessageType type) {
        Bukkit.getConsoleSender().sendMessage(formatMessage(message, type));
    }
    
    /**
     * Send a normal message to the console
     * @param message The message to send
     */
    public void logToConsole(String message) {
        logToConsole(message, MessageType.NORMAL);
    }

    /**
     * Broadcast a message to all online players
     * Only used for dungeon completion notification - might be removed soon idk
     * @param message The message to broadcast
     * @param type The message type
     */
    public void broadcastMessage(String message, MessageType type) {
        String formattedMessage = formatMessage(message, type);
        Bukkit.broadcastMessage(formattedMessage);
    }
} 