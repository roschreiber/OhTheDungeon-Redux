package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndPainting extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndPainting() {
		super();
	}

	public ComponentTFMazeDeadEndPainting(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// normal doorway
		super.addComponentParts(world, rand, sbb);

		// torches
		{
			Directional dir = (Directional) Bukkit.createBlockData(Material.WALL_TORCH);
			dir.setFacing(BlockFace.WEST);
			this.setBlockState(world, dir, 1, 3, 3, sbb);
		}
		{
			Directional dir = (Directional) Bukkit.createBlockData(Material.WALL_TORCH);
			dir.setFacing(BlockFace.EAST);
			this.setBlockState(world, dir, 4, 3, 3, sbb);
		}

//        // painting
//        EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, this.get); 
//        painting.art = getPaintingOfSize(rand, minSize);
//        painting.setDirection(direction);
//        
//        world.spawnEntity(painting);

		return true;
	}
}
