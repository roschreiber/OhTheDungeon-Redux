package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import otd.config.WorldConfig;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockBone;
//import net.minecraft.block.BlockDirt;
//import net.minecraft.block.BlockPrismarine;
//import net.minecraft.block.BlockRedSandstone;
//import net.minecraft.block.BlockSand;
//import net.minecraft.block.BlockSandStone;
//import net.minecraft.block.BlockStone;
//import net.minecraft.block.BlockStoneBrick;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumFacing;

public enum BlockType {

	AIR, WATER_STILL, WATER_FLOWING, LAVA_STILL, LAVA_FLOWING, FIRE, IRON_BAR, STONE_SMOOTH, GRANITE, GRANITE_POLISHED,
	DIORITE, DIORITE_POLISHED, ANDESITE, ANDESITE_POLISHED, GRASS, DIRT, DIRT_COARSE, DIRT_PODZOL, COBBLESTONE,
	COBBLESTONE_WALL, BEDROCK, SAND, SAND_RED, GRAVEL, ORE_GOLD, ORE_IRON, ORE_COAL, GLASS, ORE_LAPIS, ORE_EMERALD,
	LAPIS_BLOCK, SANDSTONE, SANDSTONE_CHISELED, SANDSTONE_SMOOTH, GOLD_BLOCK, IRON_BLOCK, BRICK, COBBLESTONE_MOSSY,
	OBSIDIAN, ORE_DIAMOND, DIAMOND_BLOCK, FARMLAND, ORE_REDSTONE, ICE, SNOW, CLAY, NETHERRACK, SOUL_SAND, GLOWSTONE,
	STONE_BRICK, STONE_BRICK_MOSSY, STONE_BRICK_CRACKED, STONE_BRICK_CHISELED, MYCELIUM, NETHERBRICK, END_STONE,
	EMERALD_BLOCK, ORE_QUARTZ, PRISMITE, PRISMARINE, PRISMARINE_DARK, SEA_LANTERN, COAL_BLOCK, ICE_PACKED,
	SANDSTONE_RED, SANDSTONE_RED_CHISELED, SANDSTONE_RED_SMOOTH, QUARTZ, REDSTONE_BLOCK, PRESSURE_PLATE_STONE,
	PRESSURE_PLATE_WOODEN, SHELF, REDSTONE_WIRE, COCAO, REEDS, CRAFTING_TABLE, NOTEBLOCK, REDSTONE_LAMP,
	REDSTONE_LAMP_LIT, JUKEBOX, FENCE, TNT, ENCHANTING_TABLE, FENCE_NETHER_BRICK, WEB, PUMPKIN_LIT, VINE, PURPUR_BLOCK,
	PURPUR_PILLAR, PURPUR_STAIR, PURPUR_DOUBLE_SLAB, PURPUR_SLAB, ENDER_BRICK, LIME_TERRACOTTA, LIGHT_BLUE_TERRACOTTA,
	WHITE_TERRACOTTA, YELLOW_TERRACOTTA, MAGMA, RED_NETHERBRICK, NETHER_WART_BLOCK, BONE_BLOCK, WHITE_CONCRETE,
	PINK_CONCRETE, PINK_TERRACOTTA;

	public static MetaBlock get(BlockType type) {

		MetaBlock block;

		switch (type) {
		case AIR:
			return new MetaBlock(Material.AIR);
		case WATER_STILL:
			return new MetaBlock(Material.WATER);
		case WATER_FLOWING:
			return new MetaBlock(Material.WATER);
		case LAVA_STILL:
			return new MetaBlock(Material.LAVA);
		case LAVA_FLOWING:
			return new MetaBlock(Material.LAVA);
		case FIRE:
			return new MetaBlock(Material.FIRE);
		case IRON_BAR:
			return new MetaBlock(Material.IRON_BARS);
		case STONE_SMOOTH:
			return new MetaBlock(Material.STONE);
		case GRANITE:
			block = new MetaBlock(Material.GRANITE);
			return block;
		case GRANITE_POLISHED:
			block = new MetaBlock(Material.POLISHED_GRANITE);
			return block;
		case DIORITE:
			block = new MetaBlock(Material.DIORITE);
			return block;
		case DIORITE_POLISHED:
			return new MetaBlock(Material.POLISHED_DIORITE);
		case ANDESITE:
			return new MetaBlock(Material.ANDESITE);
		case ANDESITE_POLISHED:
			return new MetaBlock(Material.POLISHED_ANDESITE);
		case GRASS:
			return new MetaBlock(Material.GRASS_BLOCK);
		case DIRT:
			return new MetaBlock(Material.DIRT);
		case DIRT_COARSE:
			block = new MetaBlock(Material.COARSE_DIRT);
			return block;
		case DIRT_PODZOL:
			block = new MetaBlock(Material.PODZOL);
			return block;
		case COBBLESTONE:
			return new MetaBlock(Material.COBBLESTONE);
		case COBBLESTONE_WALL:
			return new MetaBlock(Material.COBBLESTONE_WALL);
		case BEDROCK:
			return new MetaBlock(Material.BEDROCK);
		case SAND:
			return new MetaBlock(Material.SAND);
		case SAND_RED:
			block = new MetaBlock(Material.RED_SAND);
			return block;
		case GRAVEL:
			return new MetaBlock(Material.GRAVEL);
		case ORE_GOLD:
			return new MetaBlock(Material.GOLD_ORE);
		case ORE_IRON:
			return new MetaBlock(Material.IRON_ORE);
		case GLASS:
			return new MetaBlock(Material.GLASS);
		case ORE_LAPIS:
			return new MetaBlock(Material.LAPIS_ORE);
		case LAPIS_BLOCK:
			return new MetaBlock(Material.LAPIS_BLOCK);
		case ORE_EMERALD:
			return new MetaBlock(Material.EMERALD_ORE);
		case SANDSTONE:
			return new MetaBlock(Material.SANDSTONE);
		case SANDSTONE_CHISELED:
			block = new MetaBlock(Material.CHISELED_SANDSTONE);
			return block;
		case SANDSTONE_SMOOTH:
			block = new MetaBlock(Material.SMOOTH_SANDSTONE);
			return block;
		case GOLD_BLOCK:
			return new MetaBlock(Material.GOLD_BLOCK);
		case IRON_BLOCK:
			return new MetaBlock(Material.IRON_BLOCK);
		case BRICK:
			return new MetaBlock(Material.BRICKS);
		case COBBLESTONE_MOSSY:
			return new MetaBlock(Material.MOSSY_COBBLESTONE);
		case OBSIDIAN:
			return new MetaBlock(Material.OBSIDIAN);
		case ORE_DIAMOND:
			return new MetaBlock(Material.DIAMOND_ORE);
		case DIAMOND_BLOCK:
			return new MetaBlock(Material.DIAMOND_BLOCK);
		case FARMLAND:
			return new MetaBlock(Material.FARMLAND);
		case ORE_REDSTONE:
			return new MetaBlock(Material.REDSTONE_ORE);
		case ICE:
			return new MetaBlock(Material.ICE);
		case SNOW:
			return new MetaBlock(Material.SNOW_BLOCK);
		case CLAY:
			return new MetaBlock(Material.CLAY);
		case NETHERRACK:
			return new MetaBlock(Material.NETHERRACK);
		case SOUL_SAND:
			return new MetaBlock(Material.SOUL_SAND);
		case GLOWSTONE:
			return new MetaBlock(Material.GLOWSTONE);
		case STONE_BRICK:
			return new MetaBlock(Material.STONE_BRICKS);
		case STONE_BRICK_MOSSY:
			return new MetaBlock(Material.MOSSY_STONE_BRICKS);
		case STONE_BRICK_CRACKED:
			return new MetaBlock(Material.CRACKED_STONE_BRICKS);
		case STONE_BRICK_CHISELED:
			return new MetaBlock(Material.CHISELED_STONE_BRICKS);
		case MYCELIUM:
			return new MetaBlock(Material.MYCELIUM);
		case NETHERBRICK:
			return new MetaBlock(Material.NETHER_BRICKS);
		case END_STONE:
			return new MetaBlock(Material.END_STONE);
		case EMERALD_BLOCK:
			return new MetaBlock(Material.EMERALD_BLOCK);
		case ORE_QUARTZ:
			return new MetaBlock(Material.NETHER_QUARTZ_ORE);
		case PRISMITE:
			return new MetaBlock(Material.PRISMARINE);
		case PRISMARINE:
			return new MetaBlock(Material.PRISMARINE_BRICKS);
		case PRISMARINE_DARK:
			return new MetaBlock(Material.DARK_PRISMARINE);
		case SEA_LANTERN:
			return new MetaBlock(Material.SEA_LANTERN);
		case COAL_BLOCK:
			return new MetaBlock(Material.COAL_BLOCK);
		case ICE_PACKED:
			return new MetaBlock(Material.PACKED_ICE);
		case SANDSTONE_RED:
			return new MetaBlock(Material.RED_SANDSTONE);
		case SANDSTONE_RED_CHISELED:
			return new MetaBlock(Material.CHISELED_RED_SANDSTONE);
		case SANDSTONE_RED_SMOOTH:
			return new MetaBlock(Material.SMOOTH_RED_SANDSTONE);
		case QUARTZ:
			return new MetaBlock(Material.QUARTZ_BLOCK);
		case REDSTONE_BLOCK:
			return new MetaBlock(Material.REDSTONE_BLOCK);
		case PRESSURE_PLATE_STONE:
			return new MetaBlock(Material.STONE_PRESSURE_PLATE);
		case PRESSURE_PLATE_WOODEN:
			return new MetaBlock(Material.OAK_PRESSURE_PLATE);
		case SHELF:
			return new MetaBlock(Material.BOOKSHELF);
		case REDSTONE_WIRE:
			return new MetaBlock(Material.REDSTONE_WIRE);
		case COCAO:
			return new MetaBlock(Material.COCOA);
		case REEDS:
			return new MetaBlock(Material.SUGAR_CANE);
		case CRAFTING_TABLE:
			if (!WorldConfig.wc.furniture)
				return Wood.getPlank(Wood.OAK);
			return new MetaBlock(Material.CRAFTING_TABLE);
		case NOTEBLOCK:
			return new MetaBlock(Material.NOTE_BLOCK);
		case REDSTONE_LAMP:
			return new MetaBlock(Material.REDSTONE_LAMP);
		case REDSTONE_LAMP_LIT:
			return new MetaBlock(Bukkit.createBlockData("minecraft:redstone_lamp[lit=true]"));
		case JUKEBOX:
			return new MetaBlock(Material.JUKEBOX);
		case FENCE:
			return new MetaBlock(Material.OAK_FENCE);
		case TNT:
			return new MetaBlock(Material.TNT);
		case ENCHANTING_TABLE:
			return new MetaBlock(Material.ENCHANTING_TABLE);
		case FENCE_NETHER_BRICK:
			return new MetaBlock(Material.NETHER_BRICK_FENCE);
		case WEB:
			return new MetaBlock(Material.COBWEB);
		case PUMPKIN_LIT:
			return new MetaBlock(Material.JACK_O_LANTERN);
		case VINE:
			return new MetaBlock(Material.VINE);
		case PURPUR_BLOCK:
			return new MetaBlock(Material.PURPUR_BLOCK);
		case PURPUR_PILLAR:
			return new MetaBlock(Material.PURPUR_PILLAR);
		case PURPUR_STAIR:
			return new MetaBlock(Material.PURPUR_STAIRS);
		case PURPUR_DOUBLE_SLAB:
			return new MetaBlock(Material.PURPUR_SLAB);
		case PURPUR_SLAB:
			return new MetaBlock(Material.PURPUR_SLAB);
		case ENDER_BRICK:
			return new MetaBlock(Material.END_STONE_BRICKS);
		case MAGMA:
			return new MetaBlock(Material.MAGMA_BLOCK);
		case RED_NETHERBRICK:
			return new MetaBlock(Material.RED_NETHER_BRICKS);
		case NETHER_WART_BLOCK:
			return new MetaBlock(Material.NETHER_WART_BLOCK);
		case BONE_BLOCK:
			return new MetaBlock(Material.BONE_BLOCK);
		case LIME_TERRACOTTA:
			return new MetaBlock(Material.LIME_TERRACOTTA);
		case LIGHT_BLUE_TERRACOTTA:
			return new MetaBlock(Material.LIGHT_BLUE_TERRACOTTA);
		case WHITE_TERRACOTTA:
			return new MetaBlock(Material.WHITE_TERRACOTTA);
		case YELLOW_TERRACOTTA:
			return new MetaBlock(Material.YELLOW_TERRACOTTA);
		case WHITE_CONCRETE:
			return new MetaBlock(Material.WHITE_CONCRETE);
		case PINK_CONCRETE:
			return new MetaBlock(Material.PINK_CONCRETE);
		case PINK_TERRACOTTA:
			return new MetaBlock(Material.PINK_TERRACOTTA);
		default:
			return new MetaBlock(Material.AIR);
		}
	}

	public static ItemStack getItem(BlockType type) {

		MetaBlock block = BlockType.get(type);
		Material b = block.getBlock();
		ItemStack item = new ItemStack(b);

		return item;
	}
}
