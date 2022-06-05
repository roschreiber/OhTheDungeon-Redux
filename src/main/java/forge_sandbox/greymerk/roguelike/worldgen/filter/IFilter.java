package forge_sandbox.greymerk.roguelike.worldgen.filter;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.IBounded;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface IFilter {

	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box);

}
