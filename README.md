<p align="center">
  <img src="media/otdredux.png" alt="Oh The Dungeons You'll Go - REDUX" width="703" height="395">
</p>

<h1 align="center">Oh The Dungeons You'll Go<br>REDUX</h1>

---

### What is this plugin?

This plugin is a fork of Oh The Dungeons You'll Go, which was a minecraft plugin that added various, procedurally generated dungeons to the game. It was abandoned by its original creator in 2023, and this is a fork which aims to update it and make it more user friendly.

### Can I use this on my server right now?

No. Well, yes, you can but I don't recommend that. OTD-R is currently very buggy since there have been massive changes to the way NBT is handled in Minecraft. I have not been able to fix most of these yet, so you may get bugs like dungeons not generating properly or chests being empty.

### So, why do I need to install Item-NBT-API as a dependency now?

Because Minecraft changed how ItemStack NBT is serialized in 1.21.5+. Item-NBT-API bridges this gap temporarily, but I will remove it in the future, making sure that there are only soft dependencies for OTD-R.

### How to manually place a dungeon?

\(1\) Login as OP.

\(2\) Goto the location you want

\(3\) type `/otd_place type`, where type can be `battletower`,
`doomlike`, `roguelike`, `smoofy` or `draylar`.
<br><br>

### How to turn on the \`Natural_Spawn\` of dungeons?

\(1\) Login as OP, input `/otd` and a GUI will open. Choose "Dungeon in
Normal World" -\> world and enable the dungeon type you want to spawn.

\(2\) No need to restart the server. The dungeons will natural spawn in
new-gen chunks. It will work with all other world generator plugins.

\(3\) The `plugins\Oh_the_dungeons_youll_go\log.txt` will show the
location of each dungeon.
<br><br>

## Addon

### Player Dungeon Instance

![alt text](https://github.com/user-attachments/assets/9d46a4a6-328c-47c5-95d7-edfba846cf9d "Player Dungeon Instance")

\(1\) This plugin allows normal players to create their own little
universe with a roguelike dungeon instance inside by using money or exp.

\(2\) All small universes are placed in one Bukkit World (world name:
otd_dungeon), separated by VOID.

\(3\) For players, they can build or live there, or just explore the
dungeon and leave **Config** **Path:**
**plugins/PerPlayerDungeonInstance**
<br><br>
### How to use (For normal players)
\(1\) Input `/otd_pi` and a GUI will open.

![alt text](https://github.com/user-attachments/assets/c5c84f1e-dd09-4da1-8bd3-449f4c4624cd "Private Dungeon Menu")

\(2\) Choose `Terrain`, `Decoration` and `Dungeon` from this GUI. 

\(3\) Click one of 2 icons in the last row. You'll get a dungeon book.

\(4\) Put your dungeon book on a lectern. Right click it and it will tell you
your dungeon status.

![alt text](https://github.com/user-attachments/assets/149d5f80-7597-484f-8075-eb1ee28b3391 "Lectern")

\(5\) Wait for little universe construction. It will usually take 5 mins
for each player. May be longer due to queuing mechanism.

\(6\) When it's done, you'll hear a sound as well as a message
notification.

\(7\) Put your dungeon book on a lectern. Right click it and it will teleport
you to your private little universe.

![alt text](https://github.com/user-attachments/assets/ef3b04bd-f211-42cf-9d79-d2d23d01c893 "Private World")

## Dungeon Plot

Dungeon Plot serves as a resource world with dungeons.

\(1\) An independentworld, easy to create or recreate.

\(2\) Pregeneration, tps friendly (after generation).

\(3\) User can choose which one to teleport to (using `/otd_tp`).

![alt text](https://github.com/user-attachments/assets/e8868c56-313e-42e7-ac61-97700d3bdbd6 "Dungeon Plot")

### How to use (For OP)

\(1\) Login as OP, input `/otd` and a GUI will open. Choose "Dungeon
Plot World".

\(2\) You can then create or remove the world in this GUI.  
<br>
### How to use (For Normal Player)

\(1\) Use `/otd_tp` and choose dungeon in GUI.
<br><br>
## Custom Structure

This addon allows users to add schematic files as a dungeon.

### How to use

\(1\) Put schematics into
`plugins\Oh_the_dungeons_youll_go\schematics`.

\(2\) Login as OP, input `/otd`, choose `Custom Structure` then
click the add button.

\(3\) Now go to the world dungeon settings GUI, enable the structures
you want to spawn.
<br><br>

## MythicMobs/Boss support

This addon allows user to spawn boss in the dungeons.

In World setting, there's a \`Boss Cfg\` which will provide support for
bosses spawning in dungeons.

![alt text](https://github.com/user-attachments/assets/f3976be0-14ef-4da9-96df-6ec55cc30995 "Mythic Mobs")

For now it supports this plugin:

● ⚔ MythicMobs \[Free Version\] ►The \#1 Custom Mob Creator◄
[<u>https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1</u>](https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1-custom-mob-creator%E2%97%84.5702/)
[<u>-custom-mob-creator%E2%97%84.5702/</u>](https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1-custom-mob-creator%E2%97%84.5702/)


## Commands & Permissions

oh_the_dungeons.admin - For /otd command. Default for op.
oh_the_dungeons.teleport - For /otd_tp command. Default for players.
perplayerdungeoninstance.menu - For /otd_pi command.
perplayerdungeoninstance.back - For /otd_pi_back command. Default for
players.

perplayerdungeoninstance.admin - Default for op

/otd: Open OhTheDungeon Config GUI  
/otd_tp: Open Dungeon Plot teleport GUI  
/otd_pi: Open PerPlayerDungeonInstance GUI  
/otd_pi_back: Return from PerPlayerDungeonInstance World  
/otd_pi_create \[player\] \[terrain\] \[decoration\] \[tower\]
