package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;


public class ComponentTFTowerBridge extends ComponentTFTowerWing {


    int dSize;
    int dHeight;

    public ComponentTFTowerBridge() {
        super();
    }


    protected ComponentTFTowerBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, BlockFace direction) {
        super(feature, i, x, y, z, 3, 3, direction);

        this.dSize = pSize;
        this.dHeight = pHeight;
    }

    @Override
    public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
        int[] dest = new int[]{2, 1, 1};//getValidOpening(rand, 0);
        makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], dSize, dHeight, Rotation.NONE);
    }

    /**
     * Gets the bounding box of the tower wing we would like to make.
     *
     * @return
     */
    public StructureBoundingBox getWingBB() {
        int[] dest = offsetTowerCoords(2, 1, 1, dSize, this.getCoordBaseMode());
        return StructureTFComponentOld.getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getCoordBaseMode());
    }


    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

        // make walls
        for (int x = 0; x < 3; x++) {
            setBlockState(world, Bukkit.createBlockData(Material.OAK_FENCE), x, 2, 0, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.OAK_FENCE), x, 2, 2, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, 1, 0, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, 1, 2, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, 0, 0, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, 0, 1, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, 0, 2, sbb);
            setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), x, -1, 1, sbb);
        }

        // try two blocks outside the boundries
        setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), -1, -1, 1, sbb);
        setBlockState(world, Bukkit.createBlockData(Material.STONE_BRICKS), 3, -1, 1, sbb);

        // clear bridge walkway
        this.fillWithAir(world, sbb, 0, 1, 1, 2, 2, 1);


        // marker blocks
//        setBlockState(world, Blocks.WOOL, this.coordBaseMode, size / 2, 2, size / 2, sbb);
//        setBlockState(world, Blocks.GOLD_BLOCK, 0, 0, 0, 0, sbb);

        // door opening?
//        makeDoorOpening(world, sbb);


        return true;
    }
}
