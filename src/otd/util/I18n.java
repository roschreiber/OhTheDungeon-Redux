/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import otd.Main;
import org.bukkit.Bukkit;

/**
 *
 * @author
 */
public class I18n {
    public static I18n instance = new I18n();
    private I18n() {
        
    }
    public static class ArmourPrefix {
        public String Surplus = "Surplus";
        public String Riveted = "Riveted";
        public String Gothic = "Gothic";
        public String Jewelled = "Jewelled";
        public String Crystal = "Crystal";
        public String Strange = "Strange";
    }
    public static class SpecialItem {
        public String Name;
        public String Lore;
        public SpecialItem(String Name, String Lore) {
            this.Name = Name;
            this.Lore = Lore;
        }
    }
    
    public int lang_version = 17;
    
    public SpecialItem GREYMERK = new SpecialItem("Greymerk's Hatchet","Pointlessly sharp");
    public SpecialItem NEBRISCROWN = new SpecialItem("Nebrian Crown of Justice", "Adorned with precious gemstones");
    public SpecialItem NULL = new SpecialItem("Null Pointer", "Exceptional");
    public SpecialItem MANPANTS = new SpecialItem("Man Pants", "Yessss, Manpants!");
    public SpecialItem ZISTEAUSIGN = new SpecialItem("Battle Sign", "\"That's what you get!\"");
    public SpecialItem AVIDYA = new SpecialItem("White Russian", "The dude's favourite");
    public SpecialItem ASHLEA = new SpecialItem("Ashlea's Oatmeal Cookie", "Perfect for elevensies");
    public SpecialItem KURT = new SpecialItem("Farland Travellers", "Indeed!");
    public SpecialItem AMLP = new SpecialItem("Lascerator", "The wool collector");
    public SpecialItem CLEO = new SpecialItem("Cleophian Digging Feesh", "Feesh are not efeeshent for digging");
    public SpecialItem BDOUBLEO = new SpecialItem("Dig Job", "Recovered from hell's blazes");
    public SpecialItem GUUDE = new SpecialItem("Boulderfistian Golden Record", "\"You're Watching Guude Boulderfist...\"");
    public SpecialItem RLEAHY = new SpecialItem("Rleahian battle sub", "With extra pastrami");
    public SpecialItem ETHO = new SpecialItem("Your Mum", "The original");
    public SpecialItem ENIKOBOW = new SpecialItem("Eniko's String Theory", "For Science!");
    public SpecialItem ENIKOSWORD = new SpecialItem("Eniko's Earring", "\"She do the loot take boogie\"");
    public SpecialItem BAJ = new SpecialItem("Baj's Last Resort", "\"Starvation could be fatal\"");
    public SpecialItem DOCM = new SpecialItem("Rod of Command", "\"Get to the dang land!\"");
    public SpecialItem GINGER = new SpecialItem("Spice Chicken", "\"Kung Pao!\"");
    public SpecialItem VECHS = new SpecialItem("Legendary Stick", "\"Really?!\"");
    public SpecialItem NOTCH = new SpecialItem("Notch's apple", "Imbued with the creator's power");
    public SpecialItem QUANTUMLEAP = new SpecialItem("QuantumLeap's Swiss Cheese", "\"Oh boy\"");
    public SpecialItem GENERIKB = new SpecialItem("Hot Potato", "All a hermit needs");
    public SpecialItem FOURLES = new SpecialItem("Fourles Darkroast Beans", "\"Mmmm... Dark Roast\"");
    public SpecialItem DINNERBONE = new SpecialItem("Old Dinnerbone", "\"Dang Skellies!\"");
    public SpecialItem GRIM = new SpecialItem("Grim chew-toy", "\"Come on Grim, let's do this!\"");
    public SpecialItem MMILLSS = new SpecialItem("MMillssian spider bane", "\"I really don't need anymore string...\"");
    public SpecialItem VALANDRAH = new SpecialItem("Valandrah's Kiss", "\"Feel the kiss of my blade\"");
    
    public SpecialItem Eldritch_Blade_of_Plundering = new SpecialItem("Eldritch Blade of Plundering", "The loot taker");
    public SpecialItem Eldritch_Blade_of_the_Inferno = new SpecialItem("Eldritch Blade of the Inferno", "From the fiery depths");
    public SpecialItem Eldritch_Blade = new SpecialItem("Eldritch Blade", "Rune Etched");
    public SpecialItem Tempered_Blade = new SpecialItem("Tempered Blade", "Highly Durable");
    public SpecialItem Yew_Longbow = new SpecialItem("Yew Longbow", "Superior craftsmanship");
    public SpecialItem Laminated_Bow = new SpecialItem("Laminated Bow", "Highly polished");
    public SpecialItem Recurve_Bow = new SpecialItem("Recurve Bow", "Beautifully crafted");
    public SpecialItem Eldritch_Bow = new SpecialItem("Eldritch Bow", "Warm to the touch");
    
    public SpecialItem TEQUILA = new SpecialItem("Tequila", "");
    public SpecialItem LAUDANUM = new SpecialItem("Laudanum", "A medicinal tincture.");
    public SpecialItem MOONSHINE = new SpecialItem("Moonshine", "");
    public SpecialItem ABSINTHE = new SpecialItem("Absinthe", "");
    public SpecialItem VILE = new SpecialItem("Vile Mixture", "");
    public SpecialItem RAGE = new SpecialItem("Animus", "An unstable mixture.");
    public SpecialItem STAMINA = new SpecialItem("Vitae", "Essence of life.");
    public SpecialItem STOUT = new SpecialItem("Stout", "\"It's Good for You\"");
    public SpecialItem NECTAR = new SpecialItem("Nectar", "A Floral extract.");
    public SpecialItem COFFEE = new SpecialItem("Coffee", "A darkroast bean brew.");
    public SpecialItem AURA = new SpecialItem("Luma", "A glowstone extract.");
    
    public ArmourPrefix armour_prefix = new ArmourPrefix();
    
    public String Nerf_Msg = "Spawner cannot work properly in your server due to your config. To solve that, check 'nerf-spawner-mobs' in https://www.spigotmc.org/wiki/spigot-configuration/";
    
    public String Soul_Spade = "Soul Spade";
    public String Grave_Spade = "Grave Spade";
    public String Crystal_Head_Axe = "Crystal Head Axe";
    public String Woodland_Hatchet = "Woodland Hatchet";
    public String Crystal_Pick_of_Precision = "Crystal Pick of Precision";
    public String Crystal_Pick_of_Prospecting = "Crystal Pick of Prospecting";
    public String Crystal_Pick = "Crystal Pick";
    public String Case_Hardened_Pick_of_Precision = "Case Hardened Pick of Precision";
    public String Case_Hardened_Pick_of_Prospecting = "Case Hardened Pick of Prospecting";
    public String Case_Hardened_Pick = "Case Hardened Pick";
    
    public String Bonnet = "Bonnet";
    public String Coif = "Coif";
    public String Sallet = "Sallet";
    public String Helm = "Helm";
    
    public String Of_Diving = "of Diving";
    public String Of_Deflection = "of Deflection";
    public String Of_Defense = "of Defense";
    
    public String Shoes = "Shoes";
    public String Greaves = "Greaves";
    public String Sabatons = "Sabatons";
    public String Boots = "Boots";
    
    public String Of_Warding = "of Warding";
    public String Of_Lightness = "of Lightness";
    
    public String Pantaloons = "Pantaloons";
    public String Chausses = "Chausses";
    public String Leg_plates = "Leg-plates";
    public String Leggings = "Leggings";
    
    public String Of_Integrity = "of Integrity";
    
    public String Tunic = "Tunic";
    public String Hauberk = "Hauberk";
    public String Cuirass = "Cuirass";
    public String Plate = "Plate";
    
    public String Of_Flamewarding = "of Flamewarding";
    
    public String World_Manager = "World Manager";
    public String World_List = "World List";
    public String Previous = "Previous";
    public String Next = "Next";
    public String Click_To_Edit = "Click To Edit";
//    public List<String> World_Tip = Arrays.asList(new String[]
//            {
//                "If you want to create your world",
//                "later, please use /otd worldname",
//            }
//    );
    
    public String DraylarBattleTower_Config = "Draylar Battle Tower Cfg";
    
    public String AntManDungeon_Config = "Ant Man Dungeon Cfg";
    public String Ant_Man_Dungeon = "Ant Man Dungeon";
    
    public String AetherDungeon_Config = "Aether Dungeon Cfg";
    public String Aether_Dungeon = "Aether Dungeon";
    public String Aether_Dungeon_Cloud = "Aether Dungeon Cloud";
    public String Aether_Dungeon_Cloud_Lore = "Does the dungeon have cloud structure";
    public String Aether_Dungeon_Cloud_Material = "Aether Dungeon Cloud Material";
    public String Aether_Dungeon_Height = "Height to generation";
    public String Aether_Dungeon_Natural_Spawn = "Aether Dungeon Natural Spawn";
    
    public String Prevent_Breaking_Of_Dungeon_Spawner = "Prevent the breaking of dungeon spawner";
    public String Prevent_Breaking_Of_Dungeon_Spawner_Lore = "Make player cannot break the dungeon spawner";
    public String Prevent_Dropping_Of_Dungeon_Spawner = "Prevent the dropping of dungeon spawner";
    public String Prevent_Dropping_Of_Dungeon_Spawner_Lore = "For plugins like [SlikSpawner, Slimefun]";
    
    public String World_Editor = "World Dungeon Cfg";
    public String Roguelike_Config = "Roguelike Cfg";
    public String Doomlike_Config = "Doomlike Cfg";
    public String BattleTower_Config = "Battle Tower Cfg";
    public String Smoofy_Config = "Smoofy Cfg";
    
    public String Biome_Setting = "Biome Setting";
    
    public String Roguelike_Dungeon = "Roguelike Dungeon";
    public String Doomlike_Dungeon = "Doomlike Dungeon";
    public String Battle_Tower = "Battle Tower";
    public String Smoofy_Dungeon = "Smoofy Dungeon";
    public String Draylar_Battle_Tower = "Draylar Battle Tower";
    
    public String Roguelike_Dungeon_Natural_Spawn = "Roguelike Dungeon Natural Spawn";
    public String Doomlike_Dungeon_Natural_Spawn = "Doomlike Dungeon Natural Spawn";
    public String Battle_Tower_Natural_Spawn = "Battle Tower Natural Spawn";
    public String Smoofy_Dungeon_Natural_Spawn = "Smoofy Dungeon Natural Spawn";
    public String Draylar_Battle_Tower_Natural_Spawn = "Draylar Battle Tower Natural Spawn";
    public String Ant_Man_Dungeon_Natural_Spawn = "Ant Man Dungeon Natural Spawn";
    
    public String Load_Config_Err = "Error while saving world config file";
    
    public String Click_To_Toggle = "Click to toggle";
    public String World_Config_Save = "World config is saved";
    
    public String Builtin_Loot = "Add builtin loots to treasure chests";
    
    public String Natural_Spawn = "Natural Spawn";
    public List<String> NaturalSpawnStr = Arrays.asList(
        new String[] {
            "----------------------------------",
            "If set to false dungeons will not ",
            "naturally spawn, however may still",
            "be spawned in using a command.",
        }
    );
    
    public String Average_Dungeon_Chunk_Distance = "Average Chunk Distance Between Dungeon";
    
    public String Easy_Find = "Easy Find";
    public List<String> EasyFindStr = Arrays.asList(
        new String[] {
            "---------------------------------------",
            "If true, all dungeons that can normally",
            "have an entrance will have at least one",
            "and all entrances will have a building",
            "or ruin around them."
        }
    );
    
    public String Single_Entrance = "Single Entrance";
    
    public String Thin_Spawners = "Thin Spawners";
    public List<String> ThinSpawnersStr = Arrays.asList(
        new String[] {
            "----------------------------------",
            "If true smaller dungeons will have",
            "some of there spawners removed to",
            "make them more like larger dungeons."
        }
    );
    
    public String Dungeon_Spawn_Setting = "Dungeon Spawn";
    public String Dungeon_Spawn_Tip = "Set the proportion of each dungeon";
    
    public String Encase = "Encase The Dungeon";
    public String Generous = "Helpful Features";
    public List<String> GenerousStr = Arrays.asList(
        new String[] {
            "-----------------------------",
            "Whether helpful features like",
            "brewing stands, ender chests,",
            "enchanting stations, anvils",
            "should generate."
        }
    );
    
    public String[] Difficulty = {
        "Difficulty",
        "NONE",
        "BABY",
        "NOOB",
        "NORM",
        "HARD",
        "NUTS",
    };
    
    public String Dungeon_Level = "Level";
    public String Level_Tip = "Whether this loot will show up in dungeon level";
    public String Beginner_Level = "Beginner Level";
    public String Deepest_Level = "Deepest Level";
    public String Each = "In Each Chest";
    public List<String> EachTip = Arrays.asList(
        new String[] {
            "----------------------------------",
            "If true, then this rule will add ",
            "'quantity' loot to each chest of ",
            "this type. otherwise the specified",
            "'quantity' will be distributed among",
            "all the chests of the type specified"
        }
    );
    
    public String Weight = "Weight";
    
    public String Random_Dungeon = "Random Theme";
    public String Random_Dungeon_Content = "Use random dungeon theme (ignore biome)";
    public String Loot_Config = "Loot Config";
    public String Reduce_Loot_Chance = "Reduce Loot Chance";
    public String Increase_Loot_Chance = "Increase Loot Chance";
    public String Current_Chance = "Current Chance";
    public String Apply = "Apply";
    public String Cancel = "Cancel";
    public String Remove = "Remove";
    public String Back = "Back";
    public String Loot_Chance = "Loot Chance";
    public String Loot_Weight = "Loot Weight";
    public String Current_Page = "Current Page";
    public String Max_Item = "Item Max Amount";
    public String Min_Item = "Item Min Amount";
    public String Amount_Item_Tip1 = "Left Click to increase";
    public String Amount_Item_Tip2 = "Right Click to decrease";
    public String Chest_Type = "Chest Type";
    public String ChestTypeStr = "Chest type on top level";
    
    public String Loot_Manager = "Loot Manager";
    
    public String Add_New_Loot = "Add new Loot";
    
    public String Tip = "Tip";
    
    public String Status = "Status";
    
    public String Enable = "Enabled";
    public String Disable = "Disabled";
    
    public String Advancement1 = "Enter a server with dungeons";
    public String Advancement2 = "Visit a world with Battle Tower";
    public String Advancement3 = "Visit a world with Doomlike Dungeon";
    public String Advancement4 = "Visit a world with Roguelike Dungeon";
    public String Advancement5 = "Visit a world with Smoofy Dungeon";
    
    public String Dice = "Dice";
    public String DiceContent = "Right click to roll";
    public String ChangeSpawner = "Spawn eggs can change type of spawner";
    public String ChangeSpawnerMessage = "Spawner cannot be changed by eggs";
    
    public String PPDI_WORLD = "PerPlayerDungeonInstance World";
    public String PPDI_WORLD_LORE = "Managed by PerPlayerDungeonInstance";
    
    public String World_Spawner_Manager = "World Spawner Manager";
    public String Silk_Vanilla_Spawner = "Silk Touch on Vanilla Spawner";
    public String Silk_Dungeon_Spawner = "Silk Touch on OhTheDungeon Spawner";
    public String Dungeon_Spawner_Lore1 = "Only work on dungeons created by [OTD version>=7.0]";
    public String Dungeon_Spawner_Lore2 = "Dungeons which are created by old OTD version";
    public String Dungeon_Spawner_Lore3 = "will be treated as vanilla spawners";
    public String Mob_Drop_In_Vanilla_Spawner = "Mob Drop In Vanilla Spawner";
    public String Mob_Drop_In_Dungeon_Spawner = "Mob Drop In OhTheDungeon Spawner";
    public String Spawner_Disappear_Vanilla = "Chance of disappearance of vanilla spawners in each spawning";
    public String Spawner_Disappear_Dungeon = "Chance of disappearance of OhTheDungeon spawners in each spawning";
    
    public String Addon_Not_Installed = "Addon not installed";
    public String Click_To_Install = "Click to get the URL of this addon";
    
    public String Lich_Name = "Lich";
    public String Lich_Head = "Head of Lich";
    public String Lich_Head_Lore = "I will await my eternal reward.";
    public String LichTower_Config = "Lich Tower Cfg";
    public String LichTower = "Lich Tower";
    public String LichTower_Art = "Generate Paintings in Lich Tower";
    public String LichTower_Natural_Spawn = "Lich Tower Natural Spawn";
    
    public String Main_Menu = "Main Menu";
    public String Menu1 = "Dungeon in Normal World";
    public String Menu1_Lore1 = "If you want to config natural_spawn";
    public String Menu1_Lore2 = "or manually place dungeons in world";
    public String Menu1_Lore3 = "Advantage:";
    public String Menu1_Lore4 = "1. Work with all world generation plugin";
    
    public String Menu2 = "Dungeon Plot World";
    public String Menu2_Lore1 = "If you want to create / remove";
    public String Menu2_Lore2 = "a dungeon plot world";
    public String Menu2_Lore3 = "Advantage:";
    public String Menu2_Lore4 = "1. Pregeneration, good performance";
    public String Menu2_Lore5 = "2. Player could use menu to teleport to every dungeon";
    public String Menu2_Lore6 = "3. Easy to reset like resource world";
    
    public String Menu_Remove_World = "Dungeon Plot";
    public String Menu_remove = "Remove Dungeon Plot";
    public String Menu_remove_lore = "If you want to regenerate world";
    public String Menu_remove_click = "Dungeon plot is removed";
    
    public String Dungeon_Plot_Finish = "Finish to generate dungeon plot world";
    
    public String Menu_Create_World = "Dungeon Plot";
    public String Menu_create_world_config = "Dungeon Config";
    public String Menu_create_world_config_lore = "Config the dungeon you want to create";
    public String Menu_create_dungeon_count = "Total Dungeon Count";
    public String Menu_create = "Create Dungeon Plot";
    public String Menu_create_lore = "If you want to create dungeon plot world";
    public String Current_Dungeon_Count = "Current Dungeon Count";
    public String Creating_World = "World creating... You may see tps drop. Please standby";
    
    public String Must_Natural_Spawn = "You must open at least one dungeon's Natural Spawn";
    
    public String Dungeon_Plot_Not_Working = "Dungeon plot worker is not working";
    public String Dungeon_Plot_Breaking = "Dungeon plot generation will break in 30s";
    
    public String Dungeon_Plot_In_Progress = "Dungeon plot is creating. It would finish in 10 minutes. You'll get the message once it's finished";
    
    public String Dungeon_Plot_User_Teleport = "Choose dungeon to visit:";
    public String Click_To_Teleport = "Click to teleport";
    public String Dungeon_Plot = "Dungeon Plot";
    public String Back_To_Normal_World = "Back to main world";
    
    public String Dungeon_Plot_Not_Ready = "Dungeon Plot World is not actived or it's still under generation. Contact the OPs for help";
    
    public String No_Permission = "You don't have permission to do that";
    
    public String Automatic_Diagnostic = "Automatic Diagnostic";
    
    public String User_TP_Suggest = "Use /otd_tp to explore the dungeon world!";
    public String User_TP_Suggest2 = "Use /otd_tp, and choose the bed to back to normal world!";
    public String User_PI_Suggest = "Use /otd_pi to create your own dungeon universe with money/exp";
    
    public String Remove_All = "Remove all loots";
    public String Remove_All_Warn = "Make sure you want to do that!";
    public String Import = "Import loot config from buffer";
    public String Export = "Export loot config to buffer";
    public String Config_Backup = "Config Backup/Restore";
    public String Click_To_Restore = "Click To Restore";
    
    public String Create_New_Backup = "Create New Backup";
    public String Fail_To_Create_Backup = "Failed to create backup";
    public String Fail_To_Restore_Backup = "Failed to restore backup";
    
    public String Creative_Inventory = "Creative Inventory";
    public String Bunker = "Bunker";
    public String Cactus = "Cactus";
    public String Desert = "Desert";
    public String Forest = "Forest";
    public String House = "House";
    public String Ice = "Ice";
    public String Jungle = "Jungle";
    public String Mesa = "Mesa";
    public String Mountain = "Mountain";
    public String Ruin = "Ruin";
    public String Swamp = "Swamp";
    
    public String Spawner_Rejection_Rate = "Spawner Rejection Rate";
    public String Spawner_Rejection_Rate_Lore = "The higher the value, the lower the spawner count";
    
    public String Roguelike_Dungeon_Tower = "Roguelike Dungeon Tower";
    public String Roguelike_Dungeon_Tower_Warn = "At least one theme should be enabled";
    public String Roguelike_Dungeon_Tower_Lore = "Select the theme of dungeon tower";
    
    public String Schematic_Select = "Select Schematic";
    public String Schematic_Select_Lore = "Support *.schem/*.schematic/*.nbt";
    public String Schematic_File = "Current file";
    public String Get_Schematic = "Get some sample schematics";
    public String Custom_Dungeon = "Custom Dungeon";
    public String Custom_Dungeon_Editor = "Custom Dungeon Editor";
    public String Dungeon_Status = "Dungeon Status";
    public String Custom_Dungeon_Chest_Lore = "Chests in schematic will be set to these content";
    public String Custom_Dungeon_Type = "Custom Dungeon Type";
    public String Custom_Dungeon_Spawn_Offset = "Custom Dungeon Spawn Offset";
    public String Mob_Select = "Mob Select";
    public String Mob_Select_Lore = "Spawners in schematic will be set to these mobs";
    public String Custom_Dungeon_List = "Custom Dungeon List";
    public String World_Custom_Dungeon = "World Custom Dungeon Cfg";
    public String Require_WorldEdit = "Require WorldEdit";
    public String Add_Custom_Dungeon = "Add Custom Dungeon";
    public String Custom_Dungeon_Err_Msg = "You must select a schematic file";
    
    public static final transient String configFileName = "lang.json";
    
    public static void init() {
        String configDirName = Main.instance.getDataFolder().toString();
        File directory = new File(configDirName);
        if(!directory.exists()) {
            directory.mkdir();
        }
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        if(file.exists()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "UTF8"))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                I18n lang = (new Gson()).fromJson(sb.toString(), I18n.class);
                instance = lang;
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
            }
        }
        check_update();
        save();
    }
    
    public static void check_update() {
        if(I18n.instance.lang_version == 1) {
            update1();
        }
        if(I18n.instance.lang_version == 2) {
            update2();
        }
        if(I18n.instance.lang_version == 3) {
            update3();
        }
        if(I18n.instance.lang_version == 4) {
            update4();
        }
        if(I18n.instance.lang_version == 5) {
            update5();
        }
        if(I18n.instance.lang_version == 6) {
            update6();
        }
        if(I18n.instance.lang_version == 7) {
            update7();
        }
        if(I18n.instance.lang_version == 8) {
            update8();
        }
        if(I18n.instance.lang_version == 9) {
            update9();
        }
        if(I18n.instance.lang_version == 10) {
            update10();
        }
        if(I18n.instance.lang_version == 11) {
            update11();
        }
        if(I18n.instance.lang_version == 12) {
            update12();
        }
        if(I18n.instance.lang_version == 13) {
            update13();
        }
        if(I18n.instance.lang_version == 14) {
            update14();
        }
        if(I18n.instance.lang_version == 15) {
            update15();
        }
        if(I18n.instance.lang_version == 16) {
            update16();
        }
    }
    
    public static void update16() {
        I18n.instance.User_PI_Suggest = "Use /otd_pi to create your own dungeon universe with money/exp";
        
        I18n.instance.lang_version = 17;
    }
    
    public static void update15() {
        I18n.instance.Schematic_Select = "Select Schematic";
        I18n.instance.Schematic_Select_Lore = "Support *.schem/*.schematic/*.nbt";
        I18n.instance.Schematic_File = "Current file";
        I18n.instance.Get_Schematic = "Get some sample schematics";
        I18n.instance.Custom_Dungeon = "Custom Dungeon";
        I18n.instance.Custom_Dungeon_Editor = "Custom Dungeon Editor";
        I18n.instance.Dungeon_Status = "Dungeon Status";
        I18n.instance.Custom_Dungeon_Chest_Lore = "Chests in schematic will be set to these content";
        I18n.instance.Custom_Dungeon_Type = "Custom Dungeon Type";
        I18n.instance.Custom_Dungeon_Spawn_Offset = "Custom Dungeon Spawn Offset";
        I18n.instance.Mob_Select = "Mob Select";
        I18n.instance.Mob_Select_Lore = "Spawners in schematic will be set to these mobs";
        I18n.instance.Custom_Dungeon_List = "Custom Dungeon List";
        I18n.instance.World_Custom_Dungeon = "World Custom Dungeon Cfg";
        I18n.instance.Require_WorldEdit = "Require WorldEdit";
        I18n.instance.Add_Custom_Dungeon = "Add Custom Dungeon";
        I18n.instance.Custom_Dungeon_Err_Msg = "You must select a schematic file";

        I18n.instance.lang_version = 16;
    }
    
    public static void update14() {
        I18n.instance.Remove_All = "Remove all loots";
        I18n.instance.Remove_All_Warn = "Make sure you want to do that!";
        I18n.instance.Import = "Import loot config from buffer";
        I18n.instance.Export = "Export loot config to buffer";
        I18n.instance.Config_Backup = "Config Backup/Restore";
        I18n.instance.Click_To_Restore = "Click To Restore";
        I18n.instance.Create_New_Backup = "Create New Backup";
        I18n.instance.Fail_To_Create_Backup = "Failed to create backup";
        I18n.instance.Fail_To_Restore_Backup = "Failed to restore backup";
        I18n.instance.Creative_Inventory = "Creative Inventory";
        I18n.instance.Bunker = "Bunker";
        I18n.instance.Cactus = "Cactus";
        I18n.instance.Desert = "Desert";
        I18n.instance.Forest = "Forest";
        I18n.instance.House = "House";
        I18n.instance.Ice = "Ice";
        I18n.instance.Jungle = "Jungle";
        I18n.instance.Mesa = "Mesa";
        I18n.instance.Mountain = "Mountain";
        I18n.instance.Ruin = "Ruin";
        I18n.instance.Swamp = "Swamp";
        
        I18n.instance.Spawner_Rejection_Rate = "Spawner Rejection Rate";
        I18n.instance.Spawner_Rejection_Rate_Lore = "The higher the value, the lower the spawner count";
        I18n.instance.Roguelike_Dungeon_Tower = "Roguelike Dungeon Tower";
        I18n.instance.Roguelike_Dungeon_Tower_Warn = "At least one theme should be enabled";
        I18n.instance.Roguelike_Dungeon_Tower_Lore = "Select the theme of dungeon tower";

        I18n.instance.lang_version = 15;
    }
    
    public static void update13() {
        I18n.instance.Main_Menu = "Main Menu";
        I18n.instance.Menu1 = "Dungeon in Normal World";
        I18n.instance.Menu1_Lore1 = "If you want to config natural_spawn";
        I18n.instance.Menu1_Lore2 = "or manually place dungeons in world";
        I18n.instance.Menu1_Lore3 = "Advantage:";
        I18n.instance.Menu1_Lore4 = "1. Work with all world generation plugin";
        I18n.instance.Menu2 = "Dungeon Plot World";
        I18n.instance.Menu2_Lore1 = "If you want to create / remove";
        I18n.instance.Menu2_Lore2 = "a dungeon plot world";
        I18n.instance.Menu2_Lore3 = "Advantage:";
        I18n.instance.Menu2_Lore4 = "1. Pregeneration, good performance";
        I18n.instance.Menu2_Lore5 = "2. Player could use menu to teleport to every dungeon";
        I18n.instance.Menu2_Lore6 = "3. Easy to reset like resource world";
        
        I18n.instance.Menu_Remove_World = "Dungeon Plot";
        I18n.instance.Menu_remove = "Remove Dungeon Plot";
        I18n.instance.Menu_remove_lore = "If you want to regenerate world";
        I18n.instance.Menu_remove_click = "Dungeon plot is removed";
        
        I18n.instance.Dungeon_Plot_Finish = "Finish to generate dungeon plot world";
        
        I18n.instance.Current_Dungeon_Count = "Current Dungeon Count";
        
        I18n.instance.Must_Natural_Spawn = "You must open at least one dungeon's Natural Spawn";
        
        I18n.instance.Dungeon_Plot_Not_Working = "Dungeon plot worker is not working";
        I18n.instance.Dungeon_Plot_Breaking = "Dungeon plot generation will break in 30s";

        I18n.instance.Dungeon_Plot_In_Progress = "Dungeon plot is creating. Please wait for its finishing";
        
        I18n.instance.Creating_World = "World creating... You may see tps drop. Please standby";
        
        I18n.instance.Dungeon_Plot_User_Teleport = "Choose dungeon to visit:";
        I18n.instance.Click_To_Teleport = "Click to teleport";
        
        I18n.instance.Dungeon_Plot = "Dungeon Plot";
        I18n.instance.No_Permission = "You don't have permission to do that";
        I18n.instance.Back_To_Normal_World = "Back to main world";
        
        I18n.instance.User_TP_Suggest = "Use /otd_tp to explore the dungeon world!";
        I18n.instance.User_TP_Suggest2 = "Use /otd_tp to back to normal world!";
        
        I18n.instance.Dungeon_Plot_Not_Ready = "Dungeon Plot World is not actived or it's still under generation. Contact the OPs for help";
        
        I18n.instance.Automatic_Diagnostic = "Automatic Diagnostic";
        
        I18n.instance.lang_version = 14;
    }
    
    public static void update12() {
        I18n.instance.Lich_Name = "Lich";
        I18n.instance.Lich_Head = "Head of Lich";
        I18n.instance.Lich_Head_Lore = "I will await my eternal reward.";
        I18n.instance.LichTower_Config = "Lich Tower Cfg";
        I18n.instance.LichTower = "Lich Tower";
        I18n.instance.LichTower_Art = "Generate Paintings in Lich Tower";
        I18n.instance.LichTower_Natural_Spawn = "Lich Tower Natural Spawn";
        
        I18n.instance.lang_version = 13;
    }
    
    public static void update11() {
        I18n.instance.AetherDungeon_Config = "Aether Dungeon Cfg";
        I18n.instance.Aether_Dungeon = "Aether Dungeon";
        I18n.instance.Aether_Dungeon_Cloud = "Aether Dungeon Cloud";
        I18n.instance.Aether_Dungeon_Cloud_Lore = "Does the dungeon have cloud structure";
        I18n.instance.Aether_Dungeon_Cloud_Material = "Aether Dungeon Cloud Material";
        I18n.instance.Aether_Dungeon_Height = "Height to generation";
        I18n.instance.Aether_Dungeon_Natural_Spawn = "Aether Dungeon Natural Spawn";
        
        I18n.instance.Prevent_Breaking_Of_Dungeon_Spawner = "Prevent the breaking of dungeon spawner";
        I18n.instance.Prevent_Breaking_Of_Dungeon_Spawner_Lore = "Make player cannot break the dungeon spawner";
        I18n.instance.Prevent_Dropping_Of_Dungeon_Spawner = "Prevent the dropping of dungeon spawner";
        I18n.instance.Prevent_Dropping_Of_Dungeon_Spawner_Lore = "For plugins like [SlikSpawner, Slimefun]";
        
        I18n.instance.lang_version = 12;
    }
    
    public static void update10() {
        I18n.instance.AntManDungeon_Config = "Ant Man Dungeon Cfg";
        I18n.instance.Ant_Man_Dungeon = "Ant Man Dungeon";
        I18n.instance.Ant_Man_Dungeon_Natural_Spawn = "Ant Man Dungeon Natural Spawn";
        
        I18n.instance.lang_version = 11;
    }
    
    public static void update9() {
        I18n.instance.Addon_Not_Installed = "Addon not installed";
        I18n.instance.Click_To_Install = "Click to install";

        I18n.instance.lang_version = 10;
    }
    
    public static void update8() {
        I18n.instance.World_Spawner_Manager = "World Spawner Manager";
        I18n.instance.Silk_Vanilla_Spawner = "Silk Touch on Vanilla Spawner";
        I18n.instance.Silk_Dungeon_Spawner = "Silk Touch on OhTheDungeon Spawner";
        I18n.instance.Dungeon_Spawner_Lore1 = "Only work on dungeons created by [OTD version>=7.0]";
        I18n.instance.Dungeon_Spawner_Lore2 = "Dungeons which are created by old OTD version ";
        I18n.instance.Dungeon_Spawner_Lore3 = "will be treated as vanilla spawners";
        I18n.instance.Mob_Drop_In_Vanilla_Spawner = "Mob Drop In Vanilla Spawner";
        I18n.instance.Mob_Drop_In_Dungeon_Spawner = "Mob Drop In OhTheDungeon Spawner";
        I18n.instance.Spawner_Disappear_Vanilla = "Chance of disappearance of vanilla spawners in each spawning";
        I18n.instance.Spawner_Disappear_Dungeon = "Chance of disappearance of OhTheDungeon spawners in each spawning";

        I18n.instance.lang_version = 9;
    }
    
    public static void update7() {
        I18n.instance.DraylarBattleTower_Config = "Draylar Battle Tower Cfg";
        I18n.instance.Draylar_Battle_Tower_Natural_Spawn = "Draylar Battle Tower Natural Spawn";
        I18n.instance.Draylar_Battle_Tower = "Draylar Battle Tower";
        I18n.instance.Nerf_Msg = "Spawner cannot work properly in your server due to your config. To solve that, check 'nerf-spawner-mobs' in https://www.spigotmc.org/wiki/spigot-configuration/";

        I18n.instance.lang_version = 8;
    }
    
    public static void update6() {
        I18n.instance.PPDI_WORLD = "PerPlayerDungeonInstance World";
        I18n.instance.PPDI_WORLD_LORE = "Managed by PerPlayerDungeonInstance";
        I18n.instance.lang_version = 7;
    }
    
    public static void update5() {
        I18n.instance.Loot_Weight = "Loot Weight";
        I18n.instance.lang_version = 6;
    }
    
    public static void update4() {
        I18n.instance.TEQUILA = new SpecialItem("Tequila", "");
        I18n.instance.LAUDANUM = new SpecialItem("Laudanum", "A medicinal tincture.");
        I18n.instance.MOONSHINE = new SpecialItem("Moonshine", "");
        I18n.instance.ABSINTHE = new SpecialItem("Absinthe", "");
        I18n.instance.VILE = new SpecialItem("Vile Mixture", "");
        I18n.instance.RAGE = new SpecialItem("Animus", "An unstable mixture.");
        I18n.instance.STAMINA = new SpecialItem("Vitae", "Essence of life.");
        I18n.instance.STOUT = new SpecialItem("Stout", "\"It's Good for You\"");
        I18n.instance.NECTAR = new SpecialItem("Nectar", "A Floral extract.");
        I18n.instance.COFFEE = new SpecialItem("Coffee", "A darkroast bean brew.");
        I18n.instance.AURA = new SpecialItem("Luma", "A glowstone extract.");
        I18n.instance.lang_version = 5;
    }
    
    public static void update3() {
        I18n.instance.Dice = "Dice";
        I18n.instance.DiceContent = "Right click to roll";
        I18n.instance.ChangeSpawner = "Spawn eggs can change type of spawner";
        I18n.instance.ChangeSpawnerMessage = "Spawner cannot be changed by eggs";
        I18n.instance.lang_version = 4;
    }
    
    public static void update2() {
        I18n.instance.GREYMERK = new SpecialItem("Greymerk's Hatchet","Pointlessly sharp");
        I18n.instance.NEBRISCROWN = new SpecialItem("Nebrian Crown of Justice", "Adorned with precious gemstones");
        I18n.instance.NULL = new SpecialItem("Null Pointer", "Exceptional");
        I18n.instance.MANPANTS = new SpecialItem("Man Pants", "Yessss, Manpants!");
        I18n.instance.ZISTEAUSIGN = new SpecialItem("Battle Sign", "\"That's what you get!\"");
        I18n.instance.AVIDYA = new SpecialItem("White Russian", "The dude's favourite");
        I18n.instance.ASHLEA = new SpecialItem("Ashlea's Oatmeal Cookie", "Perfect for elevensies");
        I18n.instance.KURT = new SpecialItem("Farland Travellers", "Indeed!");
        I18n.instance.AMLP = new SpecialItem("Lascerator", "The wool collector");
        I18n.instance.CLEO = new SpecialItem("Cleophian Digging Feesh", "Feesh are not efeeshent for digging");
        I18n.instance.BDOUBLEO = new SpecialItem("Dig Job", "Recovered from hell's blazes");
        I18n.instance.GUUDE = new SpecialItem("Boulderfistian Golden Record", "\"You're Watching Guude Boulderfist...\"");
        I18n.instance.RLEAHY = new SpecialItem("Rleahian battle sub", "With extra pastrami");
        I18n.instance.ETHO = new SpecialItem("Your Mum", "The original");
        I18n.instance.ENIKOBOW = new SpecialItem("Eniko's String Theory", "For Science!");
        I18n.instance.ENIKOSWORD = new SpecialItem("Eniko's Earring", "\"She do the loot take boogie\"");
        I18n.instance.BAJ = new SpecialItem("Baj's Last Resort", "\"Starvation could be fatal\"");
        I18n.instance.DOCM = new SpecialItem("Rod of Command", "\"Get to the dang land!\"");
        I18n.instance.GINGER = new SpecialItem("Spice Chicken", "\"Kung Pao!\"");
        I18n.instance.VECHS = new SpecialItem("Legendary Stick", "\"Really?!\"");
        I18n.instance.NOTCH = new SpecialItem("Notch's apple", "Imbued with the creator's power");
        I18n.instance.QUANTUMLEAP = new SpecialItem("QuantumLeap's Swiss Cheese", "\"Oh boy\"");
        I18n.instance.GENERIKB = new SpecialItem("Hot Potato", "All a hermit needs");
        I18n.instance.FOURLES = new SpecialItem("Fourles Darkroast Beans", "\"Mmmm... Dark Roast\"");
        I18n.instance.DINNERBONE = new SpecialItem("Old Dinnerbone", "\"Dang Skellies!\"");
        I18n.instance.GRIM = new SpecialItem("Grim chew-toy", "\"Come on Grim, let's do this!\"");
        I18n.instance.MMILLSS = new SpecialItem("MMillssian spider bane", "\"I really don't need anymore string...\"");
        I18n.instance.VALANDRAH = new SpecialItem("Valandrah's Kiss", "\"Feel the kiss of my blade\"");
        
        I18n.instance.Smoofy_Dungeon_Natural_Spawn = "Smoofy Dungeon Natural Spawn";
        I18n.instance.Smoofy_Config = "Smoofy Cfg";
        I18n.instance.lang_version = 3;
    }
    
    public static void update1() {
        I18n.instance.Advancement1 = "Enter a server with dungeons";
        I18n.instance.Advancement2 = "Visit a world with Battle Tower";
        I18n.instance.Advancement3 = "Visit a world with Doomlike Dungeon";
        I18n.instance.Advancement4 = "Visit a world with Roguelike Dungeon";
        I18n.instance.Advancement5 = "Visit a world with Smoofy Dungeon";
        I18n.instance.Smoofy_Dungeon = "Smoofy Dungeon";
        I18n.instance.Chest_Type = "Chest Type";
        I18n.instance.ChestTypeStr = "Chest type on top level";
        I18n.instance.lang_version = 2;
    }
    
    public static void save() {
        String configDirName = Main.instance.getDataFolder().toString();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(instance);
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while saving lang file.");
        }
    }
}
