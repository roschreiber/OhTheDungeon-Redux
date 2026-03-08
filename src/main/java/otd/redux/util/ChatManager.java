package otd.redux.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import otd.Main;
import otd.config.WorldConfig;

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
        String rawPrefix = WorldConfig.wc.chat_prefix;
        prefix = ChatColor.translateAlternateColorCodes('&', rawPrefix);
    }
    
    public void updatePrefix(String newPrefix) {
        WorldConfig.wc.chat_prefix = newPrefix;
        prefix = ChatColor.translateAlternateColorCodes('&', newPrefix);
        WorldConfig.actualSave();
    }
    
    public String getRawPrefix() {
        return WorldConfig.wc.chat_prefix;
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