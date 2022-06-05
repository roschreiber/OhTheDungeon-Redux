function on_dungeon_mob_killed(player, entity, location, isBoss) {
    var Bukkit = Java.type("org.bukkit.Bukkit");
    Bukkit.broadcastMessage(player.getDisplayName() + " killed a " + entity.getType().toString());
}