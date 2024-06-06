package forge_sandbox.team.cqr.cqrepoured.boss;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
public class CastleKing implements Listener {
	public final static String BOSS_TAG = "otd_boss_castle_king";
	public final static String BOSS_TAG_INVALID = "otd_boss_castle_king_invalid";

	@SuppressWarnings("deprecation")
	public static ItemStack getCastleKingHead() {
		ItemStack is = Skull.CINDER.getItem();
		ItemMeta im = is.getItemMeta();

		im.setDisplayName(I18n.instance.Castle_King_Head);
		List<String> lores = new ArrayList<>();
		lores.add(ChatColor.AQUA + I18n.instance.Castle_King_Head_Lore);

		im.setLore(lores);
		is.setItemMeta(im);

		return is;
	}

	private static ItemStack skull;

	public static void init() {
		skull = getCastleKingHead();
	}

	public static Entity spawnBoss(Location loc) {
		WitherSkeleton entity = (WitherSkeleton) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
		entity.setPersistent(true);
		entity.setSilent(true);
		entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600 * 20, 25));
		entity.setCustomName(I18n.instance.Castle_King_Name);

		{
			ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.addEnchant(Enchantment.PROTECTION, 3, true);
			meta.setColor(Color.BLACK);
			item.setItemMeta(meta);

			EntityEquipment ee = entity.getEquipment();

			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta sword_meta = sword.getItemMeta();
			sword_meta.addEnchant(Enchantment.SHARPNESS, 4, true);
			sword_meta.addEnchant(Enchantment.FIRE_ASPECT, 4, true);
			sword.setItemMeta(sword_meta);

			ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemMeta armor_meta = armor.getItemMeta();
			armor_meta.addEnchant(Enchantment.PROTECTION, 3, true);
			armor_meta.addEnchant(Enchantment.THORNS, 2, true);
			armor.setItemMeta(armor_meta);

			ItemStack boots = new ItemStack(Material.IRON_BOOTS);
			ItemMeta boots_meta = boots.getItemMeta();
			boots_meta.addEnchant(Enchantment.PROTECTION, 3, true);
			boots.setItemMeta(boots_meta);

			if (ee != null) {
				ee.setLeggings(item);
				ee.setHelmet(skull.clone());
				ee.setChestplate(armor);
				ee.setBoots(boots);
				ee.setItemInMainHand(sword);
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

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		Entity damager = event.getDamager();
		if (damager instanceof WitherSkeleton) {
			Entity damagee = event.getEntity();
			if (damagee instanceof Player) {
				((Player) damagee).addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 300, 0));
				((Player) damagee).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 0));
			}
		}
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

	public static boolean isCastleKingSpawner(TileState ts) {
		PersistentDataContainer pdc = ts.getPersistentDataContainer();
		return pdc.has(key, PersistentDataType.BYTE);// && !pdc.has(invalid, PersistentDataType.BYTE);
	}

	public static void removeCastleKingSpawner(TileState ts) {
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

			block.setType(Material.AIR, true);

			SpawnerListener.spawnCastleKingLogic(block, block.getWorld());
		}
	}

	public static void createSpawner(Block b) {
		b.setType(Material.SPAWNER, true);
		CreatureSpawner tileentitymobspawner = ((CreatureSpawner) b.getState());
		tileentitymobspawner.setSpawnedType(EntityType.WITHER_SKELETON);
		tileentitymobspawner.update();

		tileentitymobspawner.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 15);
		tileentitymobspawner.update(true, false);

		if (MultiVersion.spawnerNeedLightUpdate()) {
			SpawnerDecryAPI.updateSpawnerLightRule(b, Main.instance);
		}
	}
}
