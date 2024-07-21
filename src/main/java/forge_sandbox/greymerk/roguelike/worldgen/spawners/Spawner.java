package forge_sandbox.greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import org.bukkit.entity.EntityType;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public enum Spawner {

	CREEPER("creeper"), CAVESPIDER("cave_spider"), SPIDER("spider"), SKELETON("skeleton"), ZOMBIE("zombie"),
	SILVERFISH("silverfish"), ENDERMAN("enderman"), WITCH("witch"), WITHERBOSS("wither"), BAT("bat"), BOGGED("bogged"),
	LAVASLIME("magma_cube"), BLAZE("blaze"), SLIME("slime"), PRIMEDTNT("tnt"), PIGZOMBIE("zombie_pigman");

	public static EntityType toEntityType(Spawner s) {
		switch (s) {
		case CREEPER:
			return EntityType.CREEPER;
		case CAVESPIDER:
			return EntityType.CAVE_SPIDER;
		case SPIDER:
			return EntityType.SPIDER;
		case SKELETON:
			return EntityType.SKELETON;
		case ZOMBIE:
			return EntityType.ZOMBIE;
		case SILVERFISH:
			return EntityType.SILVERFISH;
		case ENDERMAN:
			return EntityType.ENDERMAN;
		case WITCH:
			return EntityType.WITCH;
		case WITHERBOSS:
			return EntityType.WITHER;
		case BAT:
			return EntityType.BAT;
		case LAVASLIME:
			return EntityType.MAGMA_CUBE;
		case BLAZE:
			return EntityType.BLAZE;
		case SLIME:
			return EntityType.SLIME;
		case PRIMEDTNT:
			return EntityType.TNT;
		case PIGZOMBIE:
			return EntityType.ZOMBIFIED_PIGLIN;
		case BOGGED:
			return EntityType.BOGGED;
		default:
			return EntityType.ZOMBIE;
		}
	}

	private final String name;

	Spawner(String name) {
		this.name = name;
	}

	private static final Spawner[] common = { SPIDER, SKELETON, ZOMBIE };

	public static String getName(Spawner type) {
		return "minecraft:" + type.name;
	}

	public static String getRawName(Spawner type) {
		return type.name;
	}

	public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor) {
		Spawner type = common[rand.nextInt(common.length)];
		generate(editor, rand, settings, cursor, type);
	}

	public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor, Spawner type) {

		int difficulty = settings.getDifficulty(cursor);

		SpawnerSettings spawners = settings.getSpawners();
		if (spawners == null) {
			new Spawnable(type).generate(editor, rand, cursor, difficulty);
			return;
		}

		spawners.generate(editor, rand, cursor, type, difficulty);
	}
}
