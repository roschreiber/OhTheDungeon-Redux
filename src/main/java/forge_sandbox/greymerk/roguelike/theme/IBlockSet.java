package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;

public interface IBlockSet {

	public IBlockFactory getFloor();

	public IBlockFactory getWall();

	public IStair getStair();

	public IBlockFactory getPillar();

	public IDoor getDoor();

	public IBlockFactory getLightBlock();

	public IBlockFactory getLiquid();

}
