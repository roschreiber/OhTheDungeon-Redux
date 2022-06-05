package forge_sandbox.greymerk.roguelike.dungeon.towers;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Vine;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class HoleTower implements ITower {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

		IBlockFactory air = BlockType.get(BlockType.AIR);
		IBlockFactory blocks = theme.getPrimary().getWall();
		Coord floor = Tower.getBaseCoord(editor, origin);

		Coord start;
		Coord end;

		start = new Coord(floor);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.EAST);
		start.add(Cardinal.UP, 3);
		end = new Coord(origin);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.WEST);

		RectSolid.fill(editor, rand, start, end, air);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.EAST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.WEST, 2);
		end.add(Cardinal.UP);

		BlockJumble rubble = new BlockJumble();
		rubble.addBlock(blocks);
		rubble.addBlock(BlockType.get(BlockType.AIR));
		rubble.addBlock(BlockType.get(BlockType.DIRT));
		rubble.addBlock(BlockType.get(BlockType.DIRT_COARSE));
		rubble.addBlock(BlockType.get(BlockType.STONE_SMOOTH));

		RectSolid.fill(editor, rand, start, end, rubble, false, true);
		Vine.fill(editor, rand, start, end);
	}

}
