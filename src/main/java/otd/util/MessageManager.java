package otd.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;

/**
 * Centralized message handling system for OhTheDungeon-Redux
 * This class provides methods to send formatted messages to players with consistent styling
 * 
 * This class now acts as a compatibility layer for the new ChatManager
 */
public class MessageManager {
    
    /**
     * Singleton instance
     */
    private static MessageManager instance;
    
    /**
     * Get the singleton instance
     */
    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }
    
    /**
     * Send a standard message to a player
     */
    public void sendMessage(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendMessage(player, message, MessageType.NORMAL);
        }
    }
    
    /**
     * Send a standard message to a command sender (can be player or console)
     */
    public void sendMessage(CommandSender sender, String message) {
        if (sender != null) {
            if (sender instanceof Player) {
                ChatManager.getInstance().sendMessage((Player)sender, message, MessageType.NORMAL);
            } else {
                sender.sendMessage(ChatManager.getInstance().formatMessage(message, MessageType.NORMAL));
            }
        }
    }
    
    /**
     * Send an info message to a player
     */
    public void sendInfo(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendInfo(player, message);
        }
    }
    
    /**
     * Send a success message to a player
     */
    public void sendSuccess(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendSuccess(player, message);
        }
    }
    
    /**
     * Send a warning message to a player
     */
    public void sendWarning(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendWarning(player, message);
        }
    }
    
    /**
     * Send an error message to a player
     */
    public void sendError(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendError(player, message);
        }
    }
    
    /**
     * Send a debug message to a player
     */
    public void sendDebug(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendDebug(player, message);
        }
    }
    
    /**
     * Send a dungeon-related message to a player
     */
    public void sendDungeonMessage(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendMessage(player, message, MessageType.DUNGEON);
        }
    }
    
    /**
     * Send a system message to a player
     */
    public void sendSystemMessage(Player player, String message) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendMessage(player, message, MessageType.SYSTEM);
        }
    }
    
    /**
     * Send a message with a clickable URL
     */
    public void sendClickableLink(Player player, String message, String url) {
        if (player != null && player.isOnline()) {
            ChatManager.getInstance().sendClickableLink(player, message, url);
        }
    }
    
    /**
     * Send a message to the console
     */
    public void logToConsole(String message) {
        ChatManager.getInstance().logToConsole(message, MessageType.NORMAL);
    }
    
    /**
     * Send an internationalized message to a player
     * This uses the I18n system that's already in place
     */
    public void sendI18nMessage(Player player, String i18nKey) {
        if (player != null && player.isOnline()) {
            // Get the message from I18n using reflection to handle any field
            try {
                java.lang.reflect.Field field = I18n.class.getField(i18nKey);
                Object value = field.get(I18n.instance);
                if (value != null) {
                    ChatManager.getInstance().sendMessage(player, value.toString(), MessageType.NORMAL);
                }
            } catch (Exception e) {
                ChatManager.getInstance().sendError(player, "Missing translation key: " + i18nKey);
            }
        }
    }
} 