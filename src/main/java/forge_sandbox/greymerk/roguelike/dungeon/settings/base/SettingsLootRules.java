package forge_sandbox.greymerk.roguelike.dungeon.settings.base;

import forge_sandbox.Sandbox;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.treasure.loot.Book;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.ILoot;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.treasure.loot.LootRuleManager;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class SettingsLootRules extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "loot");

//	private static String generateLuaHead() {
//		String TREASURE = "local TREASURE = luajava.bindClass('" + Treasure.STARTER.getClass().getName() + "')\r\n";
//		String loot = "local Loot = luajava.bindClass('" + Loot.ARMOUR.getClass().getName() + "')\r\n";
//		String book = "local Book = luajava.bindClass('" + Book.CREDITS.getClass().getName() + "')\r\n";
//		String equipment = "local Equipment = luajava.bindClass('" + Equipment.LEGS.getClass().getName() + "')\r\n";
//		String quality = "local Quality = luajava.bindClass('" + Quality.WOOD.getClass().getName() + "')\r\n";
//		String items = "local ItemSpecialty = luajava.bindClass('" + ItemSpecialty.class.getName() + "')\r\n";
//		return TREASURE + loot + book + equipment + quality + items;
//	}

//	public static void main(String[] args) {
//		System.out.println(generateLuaHead());
////        String s ="local TREASURE = luajava.bindClass('forge_sandbox.greymerk.roguelike.treasure.Treasure')\n" +
////"local lootRules = luajava.newInstance('forge_sandbox.greymerk.roguelike.treasure.loot.LootRuleManager')\n" +
////"local Loot = luajava.bindClass('forge_sandbox.greymerk.roguelike.treasure.loot.Loot')\n" +
////"local loot = Loot:getLoot()\n" +
////"lootRules:add(TREASURE.STARTER, loot:get(Loot.WEAPON, 0), 0, true, 2)";
////        String luaStr = "local f = luajava.bindClass('forge_sandbox.greymerk.roguelike.treasure.Treasure')\r\nprint (f.STARTER)";
////        
////        Globals globals = JsePlatform.standardGlobals();
////        LuaValue lv = globals.load(s);
////        lv.call();
//	}

	public SettingsLootRules() {
		this.id = ID;
		this.lootRules = new LootRuleManager();
		ILoot loot = Loot.getLoot();
		lootRules.add(Treasure.STARTER, Book.get(Book.CREDITS), 0, true, 1);
		lootRules.add(Treasure.STARTER, loot.get(Loot.WEAPON, 0), 0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.FOOD, 0), 0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.TOOL, 0), 0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.SUPPLY, 0), 0, true, 2);
		if (Sandbox.set.extra_ItemSpecialty)
			lootRules.add(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, true, 2);
		for (int i = 0; i < 5; ++i) {
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.POTION, i), i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.ARMOUR, i), i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.FOOD, i), i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.POTION, i), i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.WEAPON, i), i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i), i, true, 1);
			lootRules.add(Treasure.BLOCKS, loot.get(Loot.BLOCK, i), i, true, 6);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i), i, true, 1);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i), i, true, 2);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i), i, true, 1);
			lootRules.add(Treasure.FOOD, loot.get(Loot.FOOD, i), i, true, 8);
			lootRules.add(Treasure.ORE, loot.get(Loot.ORE, i), i, true, 5);
			lootRules.add(Treasure.POTIONS, loot.get(Loot.POTION, i), i, true, 6);
			lootRules.add(Treasure.BREWING, loot.get(Loot.BREWING, i), i, true, 8);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.ORE, i), i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.TOOL, i), i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.BLOCK, i), i, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, i), i, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.ORE, i), i, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.SMITHY, i), i, true, 1);
			lootRules.add(Treasure.MUSIC, loot.get(Loot.MUSIC, i), i, true, 1);
			lootRules.add(Treasure.REWARD, loot.get(Loot.REWARD, i), i, true, 1);
			lootRules.add(null, loot.get(Loot.JUNK, i), i, true, 6);
			if (Sandbox.set.extra_ItemSpecialty)
				lootRules.add(null, new ItemSpecialty(0, i, Quality.get(i)), i, false, 3);
			if (Sandbox.set.extra_ItemEnchBook)
				lootRules.add(null, new ItemEnchBook(0, i), i, false, i * 2 + 5);
		}
	}
}
