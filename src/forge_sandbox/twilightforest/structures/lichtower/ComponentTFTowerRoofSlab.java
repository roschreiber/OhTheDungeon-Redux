package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.util.WoodType;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import otd.lib.async.AsyncWorldEditor;


public class ComponentTFTowerRoofSlab extends ComponentTFTowerRoof {

    public ComponentTFTowerRoofSlab() {
        super();
    }

    public ComponentTFTowerRoofSlab(TFFeature feature, int i, ComponentTFTowerWing wing) {
        super(feature, i, wing);

        // same alignment
        this.setCoordBaseMode(wing.getCoordBaseMode());
        // same size
        this.size = wing.size; // assuming only square towers and roofs right now.
        this.height = size / 2;

        // just hang out at the very top of the tower
        makeCapBB(wing);

    }

    /**
     * Makes a flat, pyramid-shaped roof
     */
    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        WoodType woodType = WoodType.BIRCH;

        return makePyramidCap(world, woodType, sbb);
    }

    protected boolean makePyramidCap(AsyncWorldEditor world, WoodType woodType, StructureBoundingBox sbb) {
        BlockData woodenSlab = Bukkit.createBlockData(WoodType.getSlab(woodType));
        BlockData woodenPlanks = Bukkit.createBlockData(WoodType.getPlanks(woodType));
        
        {
            Slab slab = (Slab) woodenSlab;
            slab.setType(Slab.Type.BOTTOM);
            woodenSlab = slab;
        }
        
        for (int y = 0; y <= height; y++) {
            int min = 2 * y;
            int max = size - (2 * y) - 1;
            for (int x = min; x <= max; x++) {
                for (int z = min; z <= max; z++) {
                    if (x == min || x == max || z == min || z == max) {
                        setBlockState(world, woodenSlab, x, y, z, sbb);
                    } else {
                        setBlockState(world, woodenPlanks, x, y, z, sbb);
                    }
                }
            }
        }
        return true;
    }

    protected boolean makeConnectedCap(AsyncWorldEditor world, WoodType woodType, StructureBoundingBox sbb) {
        BlockData woodenSlab = Bukkit.createBlockData(WoodType.getSlab(woodType));
        BlockData woodenPlanks = Bukkit.createBlockData(WoodType.getPlanks(woodType));

        {
            Slab slab = (Slab) woodenSlab;
            slab.setType(Slab.Type.BOTTOM);
            woodenSlab = slab;
        }
        
        for (int y = 0; y < height; y++) {
            int min = 2 * y;
            int max = size - (2 * y) - 1;
            for (int x = 0; x <= max; x++) {
                for (int z = min; z <= max; z++) {
                    if (x == max || z == min || z == max) {
                        setBlockState(world, woodenSlab, x, y, z, sbb);
                    } else {
                        setBlockState(world, woodenPlanks, x, y, z, sbb);
                    }
                }
            }
        }
        return true;
    }

}
