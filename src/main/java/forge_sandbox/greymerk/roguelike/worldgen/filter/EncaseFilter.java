package forge_sandbox.greymerk.roguelike.worldgen.filter;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.IBounded;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.Shape;

public class EncaseFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		box.getShape(Shape.RECTSOLID).fill(editor, rand, theme.getPrimary().getWall());
	}
}
