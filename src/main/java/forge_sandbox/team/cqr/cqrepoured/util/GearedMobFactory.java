package forge_sandbox.team.cqr.cqrepoured.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldgen.MathHelper;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.EntitySnapshot;

public class GearedMobFactory {

	private static final List<ItemStack> DEBUFF_ARROW_LIST = new ArrayList<>();
	private static final PotionEffect[] EFFECTS = { new PotionEffect(PotionEffectType.HUNGER, 600, 1),
			new PotionEffect(PotionEffectType.LEVITATION, 40, 0), new PotionEffect(PotionEffectType.POISON, 200, 0),
			new PotionEffect(PotionEffectType.SLOWNESS, 600, 0), };

	static {
		for (PotionEffect effect : EFFECTS) {
			ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
			PotionMeta arrow_meta = (PotionMeta) arrow.getItemMeta();
			arrow_meta.addCustomEffect(effect, true);
			arrow.setItemMeta(arrow_meta);
			DEBUFF_ARROW_LIST.add(arrow);
		}
	}

	private int floorCount = 1;
	private EntityType entityID;
	private Random random;

	public GearedMobFactory(int floorCount, EntityType entityID, Random rng) {
		this.floorCount = floorCount;
		this.entityID = entityID;
		this.random = rng;
	}

	public EntitySnapshot getGearedEntityByFloor(int floor, AsyncWorldEditor world) {
		EntitySnapshot entity = new EntitySnapshot();
		entity.id = this.entityID;

		EArmorType armorType = this.getGearTier(floor);
//		EWeaponType weaponType = EWeaponType.MELEE;
//		boolean enchant = this.enchantGear(floor);

		ItemStack mainHand = null;
		ItemStack offHand = null;
		ItemStack head = null;
		ItemStack chest = null;
		ItemStack legs = null;
		ItemStack feet = null;

		switch (armorType) {
		case LEATHER:
			mainHand = new ItemStack(Material.WOODEN_SWORD);
			break;
		case GOLD:
			mainHand = new ItemStack(Material.GOLDEN_SWORD);
			break;
		case CHAIN:
			mainHand = new ItemStack(Material.STONE_SWORD);
			break;
		case IRON:
			if (this.random.nextDouble() < 0.6D) {
				mainHand = new ItemStack(Material.IRON_SWORD);
			} else {
				switch (this.random.nextInt(3)) {
				case 0:
					mainHand = new ItemStack(Material.IRON_PICKAXE);
					break;
				case 1:
					mainHand = new ItemStack(Material.IRON_AXE);
					break;
				case 2:
					mainHand = new ItemStack(Material.IRON_SHOVEL);
					break;
				}
			}
			break;
		case DIAMOND:
			if (this.random.nextDouble() < 0.6D) {
				mainHand = new ItemStack(Material.DIAMOND_SWORD);
			} else {
				switch (this.random.nextInt(3)) {
				case 0:
					mainHand = new ItemStack(Material.DIAMOND_PICKAXE);
					break;
				case 1:
					mainHand = new ItemStack(Material.DIAMOND_AXE);
					break;
				case 2:
					mainHand = new ItemStack(Material.DIAMOND_SHOVEL);
					break;
				}
			}
			break;
		}
		offHand = new ItemStack(Material.SHIELD);

		switch (armorType) {
		case LEATHER:
			head = new ItemStack(Material.LEATHER_HELMET);
			chest = new ItemStack(Material.LEATHER_CHESTPLATE);
			legs = new ItemStack(Material.LEATHER_LEGGINGS);
			feet = new ItemStack(Material.LEATHER_BOOTS);
			break;
		case GOLD:
			head = new ItemStack(Material.GOLDEN_HELMET);
			chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
			legs = new ItemStack(Material.GOLDEN_LEGGINGS);
			feet = new ItemStack(Material.GOLDEN_BOOTS);
			break;
		case CHAIN:
			head = new ItemStack(Material.CHAINMAIL_HELMET);
			chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			feet = new ItemStack(Material.CHAINMAIL_BOOTS);
			break;
		case IRON:

			head = new ItemStack(Material.IRON_HELMET);
			chest = new ItemStack(Material.IRON_CHESTPLATE);
			legs = new ItemStack(Material.IRON_LEGGINGS);
			feet = new ItemStack(Material.IRON_BOOTS);

			break;
		case DIAMOND:
			head = new ItemStack(Material.DIAMOND_HELMET);
			chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			legs = new ItemStack(Material.DIAMOND_LEGGINGS);
			feet = new ItemStack(Material.DIAMOND_BOOTS);
			break;
		}

		entity.main_hand = mainHand;
		entity.off_hand = offHand;
		entity.head = head;
		entity.chest = chest;
		entity.legs = legs;
		entity.feet = feet;

		return entity;
	}

//	public Entity getGearedEntity(World world) {
//		return this.getGearedEntityByFloor(this.random.nextInt(this.floorCount + 1), world);
//	}

//	private boolean enchantGear(int floor) {
//		double chance = 0.1D + 0.2D * floor / this.floorCount;
//		return this.random.nextDouble() <= chance;
//	}

	private EArmorType getGearTier(int floor) {
		int index = MathHelper.clamp((int) ((double) floor / (double) this.floorCount * 5.0D), 0,
				EArmorType.values().length - 1);
		return EArmorType.values()[index];
	}

//	private EWeaponType getHandEquipment() {
//		List<EWeaponType> weaponTypes = new LinkedList<>();
//		int maxWeight = 0;
//		for (EWeaponType weaponType : EWeaponType.values()) {
//			if (weaponType.weight > 0) {
//				weaponTypes.add(weaponType);
//				maxWeight += weaponType.weight;
//			}
//		}
//		int i = this.random.nextInt(maxWeight);
//		for (EWeaponType weaponType : weaponTypes) {
//			i -= weaponType.weight;
//			if (i <= 0) {
//				return weaponType;
//			}
//		}
//		return EWeaponType.MELEE;
//	}

//	public enum EWeaponType {
//		MELEE(40), MAGIC_STAFF(10), HEALING_STAFF(10), BOW(10);
//
//		public int weight;
//
//		EWeaponType(int weight) {
//			this.weight = weight;
//		}
//	}

	public enum EArmorType {
		LEATHER, GOLD, CHAIN, IRON, DIAMOND;
	}

}