package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockCheckers;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeChecker extends ThemeBase {

	public ThemeChecker() {

		MetaBlock one = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock two = BlockType.get(BlockType.QUARTZ);

		IBlockFactory checks = new BlockCheckers(one, two);

		MetaStair stair = new MetaStair(StairType.QUARTZ);

		this.primary = new BlockSet(checks, stair, checks);

		this.secondary = primary;

	}
}
