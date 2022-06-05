function on_dungeon_placed(world, centerx, centery, centerz, chunks, type, custom) {
  var DungeonType = Java.type("otd.world.DungeonType");
  if(type == DungeonType.CustomDungeon) {
     if(custom == "[Charlie724]FloatingCastle.schematic") {
        var BlockUtils = Java.type("otd.script.utils.BlockUtils");
        var Location = Java.type("org.bukkit.Location");
        var center = new Location(world, centerx, centery, centerz);
        var Material = Java.type("org.bukkit.Material");
        BlockUtils.placeBlock(center, Material.BEACON);

        var center1 = new Location(world, centerx, centery + 1, centerz);
        BlockUtils.placeScriptSpawner(center1, "spawn_boss.js");
     }

     var Bukkit = Java.type("org.bukkit.Bukkit");
     Bukkit.broadcastMessage("loc: " + centerx + "," + centery + "," + centerz);
  }
}