package otd.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import otd.locate.DungeonLog;
import otd.locate.DungeonRecord;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;
import otd.util.I18n;

public class Otd_Locate implements TabExecutor {

    private static final String PERMISSION = "oh_the_dungeons.locate";

    private static final String[] TYPE_TOKENS = {
        "roguelike", "doomlike", "battletower", "smoofy", "draylar",
        "antman", "aether", "lich", "castle", "custom"
    };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> out = new ArrayList<>();
        if (args.length == 1) {
            String prefix = args[0].toLowerCase(Locale.ROOT);
            for (String token : TYPE_TOKENS) {
                if (token.startsWith(prefix)) {
                    out.add(token);
                }
            }
        }
        return out;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender == null)
            return false;
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatManager.getInstance().formatMessage(I18n.instance.Player_Only_Command, MessageType.ERROR));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission(PERMISSION)) {
            sender.sendMessage(ChatManager.getInstance().formatMessage(I18n.instance.No_Permission, MessageType.ERROR));
            return true;
        }

        String typeFilter = (args.length >= 1) ? args[0] : null;
        DungeonRecord rec = DungeonLog.nearest(
                p.getWorld().getName(),
                p.getLocation().getBlockX(),
                p.getLocation().getBlockZ(),
                typeFilter);

        if (rec == null) {
            ChatManager.getInstance().sendMessage(p, I18n.instance.Locate_None, MessageType.WARNING);
            return true;
        }

        sendResult(p, rec);
        return true;
    }

    private void sendResult(Player p, DungeonRecord rec) {
        int px = p.getLocation().getBlockX();
        int pz = p.getLocation().getBlockZ();
        double dx = rec.x - px;
        double dz = rec.z - pz;
        long distance = Math.round(Math.sqrt(dx * dx + dz * dz));

        String text = String.format(I18n.instance.Locate_Result, rec.type, distance);
        String styled = ChatManager.getInstance().formatMessage(text, MessageType.DUNGEON);

        Component component = LegacyComponentSerializer.legacySection().deserialize(styled)
                .clickEvent(ClickEvent.callback(audience -> teleport(p, rec)))
                .hoverEvent(HoverEvent.showText(Component.text(I18n.instance.Click_To_Teleport)));

        p.sendMessage(component);
    }

    private void teleport(Player p, DungeonRecord rec) {
        World world = Bukkit.getWorld(rec.world);
        int y = world.getHighestBlockYAt(rec.x, rec.z) + 1;
        p.teleport(new Location(world, rec.x + 0.5, y, rec.z + 0.5));
        ChatManager.getInstance().sendMessage(p, String.format(I18n.instance.Locate_Teleported, rec.x, rec.z, world.getName()), MessageType.SUCCESS);
    }
}
