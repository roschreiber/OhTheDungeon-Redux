// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import org.bukkit.Material;

public class ByteChunk {
	public int chunkX;
	public int chunkZ;
	public AsyncChunk blocks;
	public int width;
	public int height;

	public ByteChunk(final int chunkX, final int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.width = 16;
		this.height = 256;
		this.blocks = new AsyncChunk();
	}

	public void setBlock(final int x, final int y, final int z, final Material materialId) {
		this.blocks.setBlock(x, y, z, materialId);
	}

	public Material getBlock(final int x, final int y, final int z) {
		return this.blocks.getType(x, y, z);
	}

	public void setBlocks(final int x, final int y1, final int y2, final int z, final Material materialId) {
		for (int y = y1; y < y2; y++)
			this.blocks.setBlock(x, y, z, materialId);
	}

	public void setBlocks(final int x1, final int x2, final int y1, final int y2, final int z1, final int z2,
			final Material materialId) {
		for (int x3 = x1; x3 < x2; ++x3) {
			for (int z3 = z1; z3 < z2; ++z3) {
				for (int y3 = y1; y3 < y2; y3++) {
					this.blocks.setBlock(x3, y3, z3, materialId);
				}
			}
		}
	}

	public void setBlocks(final int x1, final int x2, final int y1, final int y2, final int z1, final int z2,
			final Material primaryId, final Material secondaryId, final MaterialFactory maker) {
		for (int x3 = x1; x3 < x2; ++x3) {
			for (int z3 = z1; z3 < z2; ++z3) {
				for (int y3 = y1; y3 < y2; ++y3) {
					this.blocks.setBlock(x3, y3, z3, maker.pickMaterial(primaryId, secondaryId, x3, z3));
				}
			}
		}
	}

	public int setLayer(final int blocky, final Material materialId) {
		this.setBlocks(0, this.width, blocky, blocky + 1, 0, this.width, materialId);
		return blocky + 1;
	}

	public int setLayer(final int blocky, final int height, final Material materialId) {
		this.setBlocks(0, this.width, blocky, blocky + height, 0, this.width, materialId);
		return blocky + height;
	}

	public int setLayer(final int blocky, final int height, final int inset, final Material materialId) {
		this.setBlocks(inset, this.width - inset, blocky, blocky + height, inset, this.width - inset, materialId);
		return blocky + height;
	}

	private void setCircleBlocks(final int cx, final int cz, final int x, final int z, final int y,
			final Material materialId) {
		this.setBlock(cx + x, y, cz + z, materialId);
		this.setBlock(cx + z, y, cz + x, materialId);
		this.setBlock(cx - z - 1, y, cz + x, materialId);
		this.setBlock(cx - x - 1, y, cz + z, materialId);
		this.setBlock(cx - x - 1, y, cz - z - 1, materialId);
		this.setBlock(cx - z - 1, y, cz - x - 1, materialId);
		this.setBlock(cx + z, y, cz - x - 1, materialId);
		this.setBlock(cx + x, y, cz - z - 1, materialId);
	}

	public void setCircle(final int cx, final int cz, final int r, final int y, final Material materialId) {
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		for (int rError = 0; x >= z; --x, rError += xChange, xChange += 2) {
			this.setCircleBlocks(cx, cz, x, z, y, materialId);
			++z;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
			}
		}
	}

	public void setArcNorthWest(final int inset, final int y1, final int y2, final Material materialId,
			final boolean fill) {
		final int cx = inset;
		final int cz = inset;
		int x;
		final int r = x = this.width - inset * 2 - 1;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		for (int rError = 0; x >= z; --x, rError += xChange, xChange += 2) {
			if (fill) {
				this.setBlocks(cx, cx + x + 1, y1, y2, cz + z, cz + z + 1, materialId);
				this.setBlocks(cx, cx + z + 1, y1, y2, cz + x, cz + x + 1, materialId);
			} else {
				this.setBlocks(cx + x, y1, y2, cz + z, materialId);
				this.setBlocks(cx + z, y1, y2, cz + x, materialId);
			}
			++z;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
			}
		}
	}

	public void setArcSouthWest(final int inset, final int y1, final int y2, final Material materialId,
			final boolean fill) {
		final int cx = inset;
		final int cz = this.width - inset;
		int x;
		final int r = x = this.width - inset * 2 - 1;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		for (int rError = 0; x >= z; --x, rError += xChange, xChange += 2) {
			if (fill) {
				this.setBlocks(cx, cx + z + 1, y1, y2, cz - x - 1, cz - x, materialId);
				this.setBlocks(cx, cx + x + 1, y1, y2, cz - z - 1, cz - z, materialId);
			} else {
				this.setBlocks(cx + z, y1, y2, cz - x - 1, materialId);
				this.setBlocks(cx + x, y1, y2, cz - z - 1, materialId);
			}
			++z;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
			}
		}
	}

	public void setArcNorthEast(final int inset, final int y1, final int y2, final Material materialId,
			final boolean fill) {
		final int cx = this.width - inset;
		final int cz = inset;
		int x;
		final int r = x = this.width - inset * 2 - 1;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		for (int rError = 0; x >= z; --x, rError += xChange, xChange += 2) {
			if (fill) {
				this.setBlocks(cx - z - 1, cx, y1, y2, cz + x, cz + x + 1, materialId);
				this.setBlocks(cx - x - 1, cx, y1, y2, cz + z, cz + z + 1, materialId);
			} else {
				this.setBlocks(cx - z - 1, y1, y2, cz + x, materialId);
				this.setBlocks(cx - x - 1, y1, y2, cz + z, materialId);
			}
			++z;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
			}
		}
	}

	public void setArcSouthEast(final int inset, final int y1, final int y2, final Material materialId,
			final boolean fill) {
		final int cx = this.width - inset;
		final int cz = this.width - inset;
		int x;
		final int r = x = this.width - inset * 2 - 1;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		for (int rError = 0; x >= z; --x, rError += xChange, xChange += 2) {
			if (fill) {
				this.setBlocks(cx - x - 1, cx, y1, y2, cz - z - 1, cz - z, materialId);
				this.setBlocks(cx - z - 1, cx, y1, y2, cz - x - 1, cz - x, materialId);
			} else {
				this.setBlocks(cx - x - 1, y1, y2, cz - z - 1, materialId);
				this.setBlocks(cx - z - 1, y1, y2, cz - x - 1, materialId);
			}
			++z;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
			}
		}
	}
}
