package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.treasure.ChestPlacementException;
import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.treasure.loot.PotionMixture;
import forge_sandbox.greymerk.roguelike.treasure.loot.Record;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockCheckers;
import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BrewingStand;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Crops;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Slab;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Trapdoor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import otd.config.WorldConfig;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
			Coord origin) {

		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = new MetaStair(StairType.SPRUCE);
		MetaBlock log = Log.get(Wood.OAK, Cardinal.UP);
		MetaBlock stonebrick = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		MetaBlock slab = Slab.get(Slab.STONE, false, true, true);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP);

		Cardinal dir = entrances[0];

		Coord start;
		Coord end;
		Coord cursor;

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(dir, 5);
		end.add(Cardinal.reverse(dir), 4);
		start.add(Cardinal.left(dir), 6);
		end.add(Cardinal.right(dir), 6);
		end.add(Cardinal.UP, 5);
		start.add(Cardinal.DOWN);
		RectHollow.fill(editor, rand, start, end, stonebrick);

		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 4);
		end.add(Cardinal.reverse(dir), 3);
		start.add(Cardinal.left(dir), 5);
		end.add(Cardinal.right(dir), 5);
		RectSolid.fill(editor, rand, start, end, cobble);

		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 3);
		end.add(Cardinal.reverse(dir), 2);
		start.add(Cardinal.left(dir), 4);
		end.add(Cardinal.right(dir), 4);
		RectSolid.fill(editor, rand, start, end, cyan);

		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 2);
		end.add(Cardinal.reverse(dir), 1);
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 3);
		RectSolid.fill(editor, rand, start, end, slab);

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 4);
		logWall(editor, rand, dir, cursor);
		cursor.add(dir, 9);
		logWall(editor, rand, Cardinal.reverse(dir), cursor);

		cursor = new Coord(origin);
		cursor.add(Cardinal.left(dir), 6);
		tvWall(editor, rand, Cardinal.left(dir), cursor);

		cursor = new Coord(origin);
		cursor.add(Cardinal.right(dir), 6);
		bWall(editor, rand, Cardinal.right(dir), cursor);

		table(editor, rand, dir, origin);

		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 4);
		end = new Coord(start);
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, cobble);

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 4);
		air.set(editor, cursor);
		cursor.add(Cardinal.UP);
		air.set(editor, cursor);

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.left(dir), 3);
		cursor.add(Cardinal.UP, 5);
		log.set(editor, cursor);
		cursor.add(dir, 3);
		log.set(editor, cursor);
		cursor.add(Cardinal.right(dir), 6);
		log.set(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 3);
		log.set(editor, cursor);

		start = new Coord(origin);
		start.add(Cardinal.reverse(dir));
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
		start.add(dir, 3);
		end.add(dir, 3);
		stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));

		for (Cardinal d : Cardinal.orthogonal(dir)) {
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(d, 3);
			end = new Coord(start);
			end.add(dir);
			stair.setOrientation(Cardinal.reverse(d), true).fill(editor, rand, new RectSolid(start, end));
		}

		start = new Coord(origin);
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(dir);
		RectSolid.fill(editor, rand, start, end, lamp);

		cursor = new Coord(origin);
		cursor.add(dir, 4);
		cursor.add(Cardinal.right(dir), 5);
		BlockType.get(BlockType.SHELF).set(editor, cursor);
		cursor.add(Cardinal.UP);
		BrewingStand.generate(editor, cursor);
		BrewingStand.add(editor, new Coord(cursor), BrewingStand.MIDDLE,
				PotionMixture.getPotion(rand, PotionMixture.MOONSHINE));

		cursor = new Coord(origin);
		cursor.add(dir, 4);
		cursor.add(Cardinal.left(dir), 4);
		BlockType.get(BlockType.JUKEBOX).set(editor, cursor);
		cursor.add(Cardinal.left(dir));
		try {
			ITreasureChest stal = Treasure.generate(editor, rand, cursor, Treasure.EMPTY,
					settings.getDifficulty(cursor));
			stal.setSlot(stal.getSize() / 2, Record.getRecord(Record.STAL));
		} catch (ChestPlacementException cpe) {
			// do nothing
		}

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.left(dir), 4);
		try {
			ITreasureChest bdub = Treasure.generate(editor, rand, cursor, Treasure.EMPTY,
					settings.getDifficulty(cursor));
			bdub.setSlot((bdub.getSize() / 2) - 2, ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
			ItemStack shirt = new ItemStack(Material.LEATHER_CHESTPLATE);
			shirt = Loot.setItemNameNew(shirt, "Pink Sweater", null);
			shirt = Loot.setItemLoreNew(shirt, "\"It's chinese red!\"");
			ItemArmour.dyeArmor(shirt, 250, 96, 128);
			bdub.setSlot((bdub.getSize() / 2) + 2, shirt);
		} catch (ChestPlacementException cpe) {
			// do nothing
		}

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.right(dir), 4);
		try {
			ITreasureChest genny = Treasure.generate(editor, rand, cursor, Treasure.EMPTY,
					settings.getDifficulty(cursor));
			genny.setSlot(genny.getSize() / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));
		} catch (ChestPlacementException cpe) {
			// do nothing
		}

		return true;
	}

	private void table(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {

		IStair stair = new MetaStair(StairType.SPRUCE);
		IStair chair = new MetaStair(StairType.NETHERBRICK);
		MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);

		Coord start;
		Coord end;
		Coord cursor;

		start = new Coord(origin);
		start.add(Cardinal.left(dir));
		end = new Coord(origin);
		end.add(Cardinal.right(dir));
		end.add(dir);
		RectSolid.fill(editor, rand, start, end, slab);

		for (Cardinal d : Cardinal.orthogonal(dir)) {
			start = new Coord(origin);
			start.add(d, 2);
			end = new Coord(start);
			end.add(dir);
			stair.setOrientation(d, true).fill(editor, rand, new RectSolid(start, end));
		}

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 2);
		for (Cardinal d : Cardinal.orthogonal(dir)) {
			Coord c = new Coord(cursor);
			c.add(d);
			chair.setOrientation(dir, false).set(editor, c);
		}

		cursor.add(dir, 5);
		for (Cardinal d : Cardinal.orthogonal(dir)) {
			Coord c = new Coord(cursor);
			c.add(d);
			chair.setOrientation(Cardinal.reverse(dir), false).set(editor, c);
		}
	}

	private void lamp(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {
		MetaBlock fence = BlockType.get(BlockType.FENCE);
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);

		Coord cursor;

		cursor = new Coord(origin);
		plank.set(editor, cursor);
		cursor.add(Cardinal.UP);
		fence.set(editor, cursor);
		cursor.add(Cardinal.UP);
		BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);
		for (Cardinal d : Cardinal.directions) {
			if (d == Cardinal.reverse(dir))
				continue;

			Coord c = new Coord(cursor);
			c.add(d);
			Trapdoor.get(Trapdoor.OAK, Cardinal.reverse(d), false, true).set(editor, c);
		}
		cursor.add(Cardinal.UP);
		fence.set(editor, cursor);
		cursor.add(Cardinal.UP);
		plank.set(editor, cursor);
		cursor.add(Cardinal.UP);
		plank.set(editor, cursor);
	}

	private void logWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {

		Coord start;
		Coord end;
		Coord cursor;

		IStair stair = new MetaStair(StairType.SPRUCE);
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		BlockCheckers checkers = new BlockCheckers(Log.get(Wood.SPRUCE, Cardinal.UP),
				Log.get(Wood.SPRUCE, Cardinal.left(dir)));

		start = new Coord(origin);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 4);
		end.add(Cardinal.right(dir), 4);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, checkers);

		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 5);
		end.add(Cardinal.right(dir), 5);
		RectSolid.fill(editor, rand, start, end, plank);
		start.add(dir);
		end.add(dir);
		start.add(Cardinal.UP, 4);
		end.add(Cardinal.UP, 4);
		stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));

		for (Cardinal d : Cardinal.orthogonal(dir)) {
			start = new Coord(origin);
			start.add(d, 5);
			start.add(Cardinal.UP);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			Log.get(Wood.SPRUCE, Cardinal.UP).fill(editor, rand, new RectSolid(start, end));

			cursor = new Coord(origin);
			cursor.add(dir);
			cursor.add(d, 3);
			lamp(editor, rand, dir, cursor);
		}

	}

	private void bWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {
		BlockJumble bricks = new BlockJumble();
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK));
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		boolean generous = false;
		String world_name = editor.getWorldName();
		if (WorldConfig.wc.dict.containsKey(world_name) && WorldConfig.wc.dict.get(world_name).roguelike.generous)
			generous = true;
		MetaBlock b = generous ? BlockType.get(BlockType.EMERALD_BLOCK)
				: ColorBlock.get(ColorBlock.GLASS, DyeColor.LIME);

		Coord start;
		Coord end;
		Coord cursor;

		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.right(dir), 3);
		end.add(Cardinal.left(dir), 4);
		RectSolid.fill(editor, rand, start, end, plank);

		start = new Coord(origin);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(Cardinal.right(dir), 3);
		end.add(Cardinal.left(dir), 4);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, bricks);

		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		for (int i = 0; i < 5; ++i) {

			if (i % 2 == 0) {
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.left(dir), 2);
				RectSolid.fill(editor, rand, start, end, b);
			} else {
				Coord c = new Coord(cursor);
				c.add(Cardinal.right(dir));
				b.set(editor, c);
				c.add(Cardinal.left(dir), 3);
				b.set(editor, c);
			}

			cursor.add(Cardinal.UP);
		}

	}

	private void tvWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {

		Coord start;
		Coord end;
		Coord cursor;

		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		MetaBlock shelf = BlockType.get(BlockType.SHELF);
		MetaBlock jungle = Log.get(Wood.JUNGLE, dir);
		MetaBlock note = BlockType.get(BlockType.NOTEBLOCK);
		MetaBlock black = ColorBlock.get(ColorBlock.WOOL, DyeColor.BLACK);
		MetaBlock bean = Crops.getCocao(dir);
		MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);

		start = new Coord(origin);
		start.add(Cardinal.reverse(dir));
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 3);
		RectSolid.fill(editor, rand, start, end, slab);

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 4);
		RectSolid.fill(editor, rand, start, end, plank);
		start.add(Cardinal.right(dir), 2);
		end.add(Cardinal.left(dir), 2);
		RectSolid.fill(editor, rand, start, end, note);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, black);

		start = new Coord(origin);
		start.add(Cardinal.left(dir), 2);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(Cardinal.left(dir));
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, shelf);
		cursor = new Coord(start);
		cursor.add(Cardinal.UP);
		jungle.set(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		bean.set(editor, cursor);

		start = new Coord(origin);
		start.add(Cardinal.right(dir), 3);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, shelf);
		cursor = new Coord(start);
		cursor.add(Cardinal.UP);
		jungle.set(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		bean.set(editor, cursor);

	}

	@Override
	public int getSize() {
		return 8;
	}

	public boolean validLocation(IWorldEditor editor, Cardinal dir, int x, int y, int z) {

		for (Coord pos : new RectHollow(new Coord(x - 7, y - 2, z - 7), new Coord(x + 7, y + 5, z + 7))) {
			if (!editor.getMaterial(pos).isSolid())
				return false;
		}

		return true;
	}

}
