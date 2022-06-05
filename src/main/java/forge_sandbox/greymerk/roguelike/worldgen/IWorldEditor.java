package forge_sandbox.greymerk.roguelike.worldgen;

import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.treasure.TreasureManager;
import otd.lib.async.later.roguelike.Later;
//import net.minecraft.block.Block;
//import net.minecraft.tileentity.TileEntity;

public interface IWorldEditor {

	public String getWorldName();

	boolean setBlock(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);

	Block getBlock(Coord pos);

	MetaBlock getMetaBlock(Coord pos);

	Material getMaterial(Coord pos);

	boolean isAirBlock(Coord pos);

	boolean commit(int count);

//	TileEntity getTileEntity(Coord pos);

	long getSeed();

//	Random getSeededRandom(int m, int n, int i);

	void fillDown(Random rand, Coord pos, IBlockFactory pillar);

	boolean canPlace(MetaBlock block, Coord pos, Cardinal dir);

	boolean validGroundBlock(Coord pos);

	void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

	int getStat(Block block);

	Map<Material, Integer> getStats();

	TreasureManager getTreasure();

	void addChest(ITreasureChest chest);

	IPositionInfo getInfo(Coord pos);

	Biome getBiome(Coord pos);

	public World getWorld();

	public boolean isFakeWorld();

	public void addLater(Later later);

	public void setSeaLevel(int seaLevel);

	public int getSeaLevel();

	public void setBottom(int bottom);

	public int getBottom();

//        public void setDataDelay(Coord pos, BlockData data);

//	Coord findNearestStructure(VanillaStructure type, Coord pos);

}
