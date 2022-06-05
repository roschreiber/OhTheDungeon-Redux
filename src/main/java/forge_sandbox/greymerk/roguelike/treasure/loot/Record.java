package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;

public enum Record {

	THIRTEEN, CAT, BLOCKS, CHIRP, FAR, MALL, MELLOHI, STAL, STRAD, WARD, ELEVEN, WAIT;

	public static ItemStack getRecord(Record type) {
		return new ItemStack(getId(type), 1);
	}

	public static ItemStack getRandomRecord(Random rand) {
		return getRecord(Record.values()[rand.nextInt(Record.values().length)]);
	}

	public static Material getId(Record type) {

		switch (type) {
		case THIRTEEN:
			return Material.MUSIC_DISC_13;
		case CAT:
			return Material.MUSIC_DISC_CAT;
		case BLOCKS:
			return Material.MUSIC_DISC_BLOCKS;
		case CHIRP:
			return Material.MUSIC_DISC_CHIRP;
		case FAR:
			return Material.MUSIC_DISC_FAR;
		case MALL:
			return Material.MUSIC_DISC_MALL;
		case MELLOHI:
			return Material.MUSIC_DISC_MELLOHI;
		case STAL:
			return Material.MUSIC_DISC_STAL;
		case STRAD:
			return Material.MUSIC_DISC_STRAD;
		case WARD:
			return Material.MUSIC_DISC_WARD;
		case ELEVEN:
			return Material.MUSIC_DISC_11;
		case WAIT:
			return Material.MUSIC_DISC_WAIT;
		default:
			return Material.MUSIC_DISC_CAT;
		}
	}

}
