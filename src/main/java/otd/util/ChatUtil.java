package otd.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtil {
	public static void sendMessage(Player player, String message, String url) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/tellraw " + player.getName() + " {text:\""
				+ message + "\",clickEvent:{action:open_url,value:\"" + url + "\"}}");
	}
}
