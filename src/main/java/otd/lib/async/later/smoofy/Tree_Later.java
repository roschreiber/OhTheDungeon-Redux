package otd.lib.async.later.smoofy;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;

import forge_sandbox.BlockPos;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.lib.async.later.roguelike.Later;

public class Tree_Later extends Later {
	private final BlockPos tree;
	private final TreeType treeType;

	public Tree_Later(BlockPos tree, TreeType treeType) {
		this.tree = tree;
		this.treeType = treeType;
	}

	@Override
	public Coord getPos() {
		return new Coord(tree.x, tree.y, tree.z);
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		World w = c.getWorld();
		w.generateTree(new Location(w, tree.x, tree.y + this.y, tree.z), treeType);
	}
}
