function spawner_action(loc) {
  var x = loc.getBlockX();
  var y = loc.getBlockY();
  var z = loc.getBlockZ();

  var MobUtils = Java.type("otd.script.utils.MobUtils");
  MobUtils.spawnCustomMythicMobs(loc, 'SkeletonKing');
  var Bukkit = Java.type("org.bukkit.Bukkit");
  Bukkit.broadcastMessage("A Boss spawn at " + x + "," + y + "," + z);
}