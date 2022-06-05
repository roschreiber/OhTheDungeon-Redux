package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.List;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

//import net.minecraft.init.Items;
//import net.minecraft.item.EnumDyeColor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.tileentity.BannerPattern;

public class Banner {

	public static ItemStack get(Random rand) {

		ItemStack banner = new ItemStack(Material.WHITE_BANNER);
		int n = rand.nextInt(8) + 1;
		for (int i = 0; i < n; ++i) {
			addPattern(banner, rand);
		}

		return banner;
	}

	public static ItemStack addPattern(ItemStack banner, Random rand) {
		// TODO

		ItemMeta im = banner.getItemMeta();
		if (im instanceof BannerMeta) {
			BannerMeta bm = (BannerMeta) im;
			List<Pattern> patterns = bm.getPatterns();
			DyeColor[] dyes = DyeColor.values();
			PatternType[] types = PatternType.values();
			PatternType pt = types[rand.nextInt(types.length)];
			DyeColor color = dyes[rand.nextInt(dyes.length)];
			patterns.add(new Pattern(color, pt));
			bm.setPatterns(patterns);
			banner.setItemMeta(bm);
			return banner;
		} else {
			return banner;
		}

//            return banner;
//		BannerPattern pattern = BannerPattern.values()[rand.nextInt(BannerPattern.values().length)];
//		EnumDyeColor color = EnumDyeColor.values()[rand.nextInt(EnumDyeColor.values().length)];
//		
//		return addPattern(banner, pattern, color);
	}

//	public static ItemStack addPattern(ItemStack banner, BannerPattern pattern, EnumDyeColor color){
//		
//		NBTTagCompound nbt = banner.getTagCompound();
//		if(nbt == null){
//			banner.setTagCompound(new NBTTagCompound());
//			nbt = banner.getTagCompound();
//		}
//		
//		NBTTagCompound tag;
//		
//		if(nbt.hasKey("BlockEntityTag")){
//			tag = nbt.getCompoundTag("BlockEntityTag");
//		} else {
//			tag = new NBTTagCompound();
//			nbt.setTag("BlockEntityTag", tag);
//		}
//		
//		NBTTagList patterns;
//		
//		if(tag.hasKey("Patterns")){
//			patterns = tag.getTagList("Patterns", 10);
//		} else {
//			patterns = new NBTTagList();
//			tag.setTag("Patterns", patterns);
//		}
//				
//		NBTTagCompound toAdd = new NBTTagCompound();
//		toAdd.setInteger("Color", color.getDyeDamage());
//		toAdd.setString("Pattern", pattern.getHashname());
//		patterns.appendTag(toAdd);
//		
//		return banner;
//	}

}
