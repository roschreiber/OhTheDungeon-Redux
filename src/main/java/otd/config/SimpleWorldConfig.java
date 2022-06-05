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
package otd.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import forge_sandbox.jaredbgreat.dldungeons.Difficulty;
import otd.config.EnumType.ChestType;

/**
 *
 * @author
 */
public class SimpleWorldConfig {
	public class Roguelike {
		public Set<String> biomeExclusions = new HashSet<>();
//        public boolean doBuiltinSpawn = true;
		public boolean doNaturalSpawn = false;
		public boolean encase = true;
		public boolean generous = true;
		public double looting = 0.0;
		public int lowerLimit = 60;
		public boolean random = false;
		public int upperLimit = 100;
		public boolean builtinLoot = true;

		public class Themes {
			public boolean forest = true;
			public boolean house = true;
			public boolean desert = true;
			public boolean bunker = true;
			public boolean jungle = true;
			public boolean swamp = true;
			public boolean mountain = true;
			public boolean mesa = true;
			public boolean ice = true;
			public boolean ruin = true;
			public boolean rare = true;
		}

		public void initThemes() {
			this.themes = new Themes();
		}

		public Themes themes = new Themes();

		public List<RoguelikeLootNode> loots = new ArrayList<>();
	}

	public Roguelike roguelike = new Roguelike();

	public class Doomlike {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean builtinLoot = true;
		public boolean doNaturalSpawn = false;
		public boolean easyFind = true;
		public boolean singleEntrance = true;
		public boolean thinSpawners = true;
		public Difficulty difficulty = Difficulty.NORM;

		public List<LootNode> loots = new ArrayList<>();
	}

	public Doomlike doomlike = new Doomlike();

	public class BattleTower {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;

		public List<LootNode> loots = new ArrayList<>();
		public ChestType chest = ChestType.BOX;
	}

	public BattleTower battletower = new BattleTower();

	public class SmoofyDungeon {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;

		public List<LootNode> loots = new ArrayList<>();

		public boolean Skeleton = true;
		public boolean Blaze = true;
		public boolean Spider = true;
		public boolean PigZombie = true;
		public boolean Zombie = true;
		public boolean Ghast = true;
		public boolean Creeper = true;
	}

	public SmoofyDungeon smoofydungeon = new SmoofyDungeon();

	public int roguelike_weight = 3;
	public int doomlike_weight = 3;
	public int battle_tower_weight = 2;
	public int smoofy_weight = 3;
	public int draylar_weight = 3;
	public int antman_weight = 3;
	public int aether_weight = 3;
	public int lich_weight = 3;
	public int castle_weight = 3;
	public int custom_dungeon_weight = 3;

	public int distance = 62;

	public double spawner_rejection_rate = 0;

	public boolean egg_change_spawner = true;
	public boolean silk_vanilla_spawner = false;
	public boolean silk_dungeon_spawner = false;
	public boolean mob_drop_in_vanilla_spawner = true;
	public boolean mob_drop_in_dungeon_spawner = false;
	public boolean prevent_spawner_dropping = false;
	public boolean prevent_spawner_breaking = false;
	public float disappearance_rate_vanilla = 0;
	public float disappearance_rate_dungeon = 0;

	public void initSmoofyDungeon() {
		this.smoofydungeon = new SmoofyDungeon();
	}

	public class DraylarBattleTower {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;

		public List<LootNode> loots = new ArrayList<>();
	}

	public DraylarBattleTower draylar_battletower = new DraylarBattleTower();

	public void initDraylarBattleTower() {
		this.draylar_battletower = new DraylarBattleTower();
	}

	public class AntManDungeon {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;

		public List<LootNode> loots = new ArrayList<>();
	}

	public AntManDungeon ant_man_dungeon = new AntManDungeon();

	public void initAntManDungeon() {
		this.ant_man_dungeon = new AntManDungeon();
	}

	public class AetherDungeon {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;
		public int height = 180;
		public boolean cloud = true;

		public List<LootNode> loots = new ArrayList<>();
	}

	public AetherDungeon aether_dungeon = new AetherDungeon();

	public void initAetherDungeon() {
		this.aether_dungeon = new AetherDungeon();
	}

	public class LichTower {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;
		public boolean doArt = true;

		public List<LootNode> loots = new ArrayList<>();
	}

	public LichTower lich_tower = new LichTower();

	public void initLichTower() {
		this.lich_tower = new LichTower();
	}

	public Set<UUID> custom_dungeons = Collections.synchronizedSet(new HashSet<>());

	public void initCustomDungeon() {
		custom_dungeons = Collections.synchronizedSet(new HashSet<>());
	}

	public class Castle {
		public Set<String> biomeExclusions = new HashSet<>();
		public boolean doNaturalSpawn = false;
		public boolean builtinLoot = true;

		public List<LootNode> loots = new ArrayList<>();
	}

	public Castle castle = new Castle();

	public void initCastle() {
		this.castle = new Castle();
	}

	public enum BossType {
		Vanilla, MythicMobs, Boss, EcoBosses
	}

	public BossType boss = BossType.Vanilla;
	public int chance = 5;

	public class WorldParameter {
		public int bottom = 5;
		public int sealevel = 63;
	}

	public WorldParameter worldParameter = new WorldParameter();

	public void initWorldParameter() {
		worldParameter = new WorldParameter();
	}
}
