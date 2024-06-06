/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures.lichtower.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import otd.Main;
import otd.MultiVersion;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.listener.SpawnerListener;
import otd.util.I18n;
import otd.util.Skull;

/**
 *
 * @author shadow
 */
public class Lich implements Listener {
	public final static String BOSS_TAG = "otd_boss_lich";
	public final static String BOSS_TAG_INVALID = "otd_boss_lich_invalid";

	@SuppressWarnings("deprecation")
	public static ItemStack getLichHead() {
		ItemStack is = Skull.LICH.getItem();
		ItemMeta im = is.getItemMeta();

		im.setDisplayName(I18n.instance.Lich_Head);
		List<String> lores = new ArrayList<>();
		lores.add(ChatColor.AQUA + I18n.instance.Lich_Head_Lore);

		im.setLore(lores);
		is.setItemMeta(im);

		return is;
	}

	private static ItemStack skull;

	public static void init() {
		skull = getLichHead();
	}

	public static Entity spawnBoss(Location loc) {
		Skeleton entity = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		entity.setPersistent(true);
		entity.setSilent(true);
		entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600 * 20, 25));

		entity.setCustomName(I18n.instance.Lich_Name);

		{
			ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(Color.BLUE);
			item.setItemMeta(meta);
			EntityEquipment ee = entity.getEquipment();
			if (ee != null) {
				ee.setLeggings(item);
				ee.setHelmet(skull.clone());
			}
		}

		setBossTag(entity);

		return entity;
	}

	private final static NamespacedKey key = new NamespacedKey(Main.instance, BOSS_TAG);
	private final static NamespacedKey invalid = new NamespacedKey(Main.instance, BOSS_TAG_INVALID);

	public static void setBossTag(Entity entity) {
		entity.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
	}

	public static boolean isBoss(Entity entity) {
		return entity.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
	}

//    @EventHandler
//    public void onShootBow(EntityShootBowEvent event) {
//        Entity entity = event.getEntity();
//        if(!isBoss(entity)) return;
//        
//        Location loc = event.getEntity().getLocation();
//        
//        Entity e = loc.getWorld().spawnEntity(loc, EntityType.SPECTRAL_ARROW);
//        e.setVelocity(event.getProjectile().getVelocity());
//        
//        event.setProjectile(e);
//    }

	@EventHandler
	public void onDamage(ProjectileHitEvent event) {
		Projectile entity = event.getEntity();
		if (event.getHitEntity() == null)
			return;
		ProjectileSource shooter = entity.getShooter();

		if (shooter instanceof Skeleton) {
			Skeleton skeleton = (Skeleton) shooter;
			if (!isBoss(skeleton))
				return;
			Entity hit = event.getHitEntity();

			if (hit instanceof Player) {
				((Player) hit).damage(6.0D, hit);
				applyKnockback(((Player) hit), entity);
				((Player) hit).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 5 * 20, 1, true));
			}
		}
	}

	private final static Random random = new Random();

	private static void applyKnockback(LivingEntity livingEntity, Entity attacker) {
		double dist = (attacker instanceof Player && ((Player) attacker).isSprinting()) ? 1.5 : 1;
		dist += random.nextDouble() * 0.4 - 0.2; // adds or subtract 0.2 to the distance
		int knockBackLevel = 2;
		dist += 3 * knockBackLevel;
		double mag = distanceToMagnitude(dist);
		Location location = getLocation(attacker);
		location.setPitch(location.getPitch() - 15);
		Vector velocity = setMag(location.getDirection(), mag);
		livingEntity.setVelocity(velocity);
	}

	private static Vector setMag(Vector vector, double mag) {
		double x = vector.getX();
		double y = vector.getY();
		double z = vector.getZ();
		double denominator = Math.sqrt(x * x + y * y + z * z);
		if (denominator != 0) {
			return vector.multiply(mag / denominator);
		} else {
			return vector;
		}
	}

	private static Location getLocation(Entity entity) {
		if (entity instanceof Projectile) {
			Projectile projectile = (Projectile) entity;
			Location location = entity.getLocation();
			location.setDirection(projectile.getVelocity());
			return location;
		} else {
			return entity.getLocation();
		}
	}

	private static double distanceToMagnitude(double distance) {
		return ((distance + 1.5) / 5d);
	}

	@EventHandler
	public void onMobDrop(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (!isBoss(entity))
			return;

		List<ItemStack> drops = event.getDrops();
		drops.clear();
		drops.add(skull.clone());
	}

	public static boolean isLichSpawner(TileState ts) {
		PersistentDataContainer pdc = ts.getPersistentDataContainer();
		return pdc.has(key, PersistentDataType.BYTE);// && !pdc.has(invalid, PersistentDataType.BYTE);
	}

	public static void removeLichSpawner(TileState ts) {
		ts.getPersistentDataContainer().set(invalid, PersistentDataType.BYTE, (byte) 1);
	}

	@EventHandler
	public void onBreakSpawner(BlockBreakEvent event) {
		if (event.isCancelled())
			return;
		Block block = event.getBlock();
		if (block.getType() != Material.SPAWNER)
			return;
		TileState ts = (TileState) block.getState();

		if (ts.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
			event.setCancelled(true);
			SpawnerListener.spawnLichLogic(block, block.getWorld());
		}
	}

	public static void createSpawner(Block b) {
		b.setType(Material.SPAWNER, true);
		CreatureSpawner tileentitymobspawner = ((CreatureSpawner) b.getState());
		tileentitymobspawner.setSpawnedType(EntityType.SKELETON);
		tileentitymobspawner.update();

		tileentitymobspawner.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 15);
		tileentitymobspawner.update(true, false);

		if (MultiVersion.spawnerNeedLightUpdate()) {
			SpawnerDecryAPI.updateSpawnerLightRule(b, Main.instance);
		}
	}
}
