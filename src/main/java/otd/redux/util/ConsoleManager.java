package otd.redux.util;

import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class ConsoleManager {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m"; // &2
    private static final String ANSI_GRAY = "\u001B[90m"; // &8
    private static final String ANSI_LIGHT_GRAY = "\u001B[37m";
    private static final String ANSI_LIGHT_YELLOW = "\u001B[93m"; // &e

    private static final String PREFIX = ANSI_GRAY + "[" + ANSI_GREEN + "OTD-" + ANSI_LIGHT_YELLOW + "REDUX" + ANSI_GRAY + "] " + ANSI_GRAY + "| " + ANSI_RESET;

    public static void log(String message) {
        Bukkit.getLogger().info(PREFIX + message + ANSI_RESET);
    }

    public static void logLogo(String message) {
        Bukkit.getLogger().info(ANSI_LIGHT_GRAY + message + ANSI_RESET);
    }

    public static void logInfo(String message) {
        Bukkit.getLogger().info(PREFIX + ANSI_BLUE + message + ANSI_RESET);
    }

    public static void logWarning(String message) {
        Bukkit.getLogger().warning(PREFIX + ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void logError(String message) {
        Bukkit.getLogger().severe(PREFIX + ANSI_RED + message + ANSI_RESET);
    }
}
