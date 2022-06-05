package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Cocoa;
//import net.minecraft.block.BlockCocoa;
//import net.minecraft.block.BlockPumpkin;
//import net.minecraft.init.Blocks;

public enum Crops {

	WHEAT, CARROTS, NETHERWART, MELON, PUMPKIN, POTATOES;

	public static MetaBlock get(Crops type) {
		switch (type) {
		case WHEAT:
			return new MetaBlock(Material.WHEAT);
		case CARROTS:
			return new MetaBlock(Material.CARROTS);
		case NETHERWART:
			return new MetaBlock(Material.NETHER_WART);
		case MELON:
			return new MetaBlock(Material.MELON_STEM);
		case PUMPKIN:
			return new MetaBlock(Material.PUMPKIN_STEM);
		case POTATOES:
			return new MetaBlock(Material.POTATOES);
		default:
			return new MetaBlock(Material.WHEAT);
		}
	}

	public static MetaBlock getCocao(Cardinal dir) {
		MetaBlock cocao = new MetaBlock(Material.COCOA);
		Cocoa state = (Cocoa) cocao.getState();
		state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
		state.setAge(2);
		cocao.setState(state);
		return cocao;
	}

	public static MetaBlock getPumpkin(Cardinal dir, boolean lit) {
		MetaBlock pumpkin = new MetaBlock(lit ? Material.JACK_O_LANTERN : Material.CARVED_PUMPKIN);
		Directional state = (Directional) pumpkin.getState();
		state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
		return pumpkin;
	}

}
