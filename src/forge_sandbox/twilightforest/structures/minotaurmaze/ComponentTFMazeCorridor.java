package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeCorridor extends StructureTFComponentOld {

    public ComponentTFMazeCorridor() {
        super();
    }

    public ComponentTFMazeCorridor(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
        super(feature, i);
        this.setCoordBaseMode(rotation);
        this.boundingBox = new StructureBoundingBox(x, y, z, x + 5, y + 5, z + 5);
    }

    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

        //arch
        this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 4, 3, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
        this.fillWithAir(world, sbb, 2, 1, 2, 3, 3, 3);

        return true;
    }

}
