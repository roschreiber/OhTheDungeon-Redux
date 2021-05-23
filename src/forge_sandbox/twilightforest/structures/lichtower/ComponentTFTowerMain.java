package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.EntityType;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.twilightforest.SpawnerBoss_Later;
import forge_sandbox.twilightforest.TFBoss;
import forge_sandbox.twilightforest.structures.RotationUtil;
import forge_sandbox.twilightforest.structures.StructureTFComponent;
import forge_sandbox.twilightforest.structures.StructureTFHelper;


public class ComponentTFTowerMain extends ComponentTFTowerWing {

    public ComponentTFTowerMain() {
        super();
    }

    public ComponentTFTowerMain(TFFeature feature, AsyncWorldEditor world, Random rand, int index, int x, int y, int z) {
        // some of these are subject to change if the ground level is > 30.
        super(feature, index, x, y, z, 15, 55 + rand.nextInt(32), BlockFace.SOUTH);
    }

    @Override
    public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
        // add a roof?
        makeARoof(parent, list, rand);

        // sub towers
        for (final Rotation rotation : RotationUtil.ROTATIONS) {
            int[] dest = getValidOpening(rand, rotation);

            // adjust height if we're too low at this point
            if (dest[1] < height / 2) {
                dest[1] += 20;
            }

            int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);

            if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, rotation)) {
                makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, rotation);
            }
        }

        // try one more time for large towers
        for (final Rotation rotation : RotationUtil.ROTATIONS) {
            int[] dest = getValidOpening(rand, rotation);

            // adjust height if we're too low at this point
            if (dest[1] < height / 2) {
                dest[1] += 10;
            }

            int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);

            if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, rotation)) {
                makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, rotation);
            }
        }

        // another set, if possible
        for (final Rotation rotation : RotationUtil.ROTATIONS) {
            int[] dest = getValidOpening(rand, rotation);

            int childHeight = Math.min(7 + rand.nextInt(6), this.height - dest[1] - 3);

            if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, rotation)) {
                makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, rotation);
            }
        }

        // outbuildings
        for (final Rotation rotation : RotationUtil.ROTATIONS) {
            int[] dest = getOutbuildingOpening(rand, rotation);

            int childHeight = 11 + rand.nextInt(10);
            int childSize = 7 + (rand.nextInt(2) * 2);

            makeTowerOutbuilding(list, rand, 1, dest[0], dest[1], dest[2], childSize, childHeight, rotation);
        }

        // TINY TOWERS!
        for (int i = 0; i < 4; i++) {
            for (final Rotation towerRotation : RotationUtil.ROTATIONS) {
                int[] dest = getValidOpening(rand, towerRotation);
                int childHeight = 6 + rand.nextInt(5);
                if (rand.nextInt(3) == 0 || !makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, towerRotation)) {
                    makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, towerRotation);
                }
            }

        }

    }

    /**
     * Gets a random position in the specified direction that we can make an outbuilding at
     */
    public int[] getOutbuildingOpening(Random rand, Rotation rotation) {

        int rx = 0;
        int ry = 1;
        int rz = 0;

        switch (rotation) {
            case NONE:
                // for directions 0 or 2, the wall lies along the z axis
                rx = size - 1;
                rz = 6 + rand.nextInt(8);
                break;
            case CLOCKWISE_90:
                // for directions 1 or 3, the wall lies along the x axis
                rx = 1 + rand.nextInt(11);
                rz = size - 1;
                break;
            case CLOCKWISE_180:
                rx = 0;
                rz = 1 + rand.nextInt(8);
                break;
            case COUNTERCLOCKWISE_90:
                rx = 3 + rand.nextInt(11);
                rz = 0;
                break;
        }

        return new int[]{rx, ry, rz};
    }


    public boolean makeTowerOutbuilding(List<StructureTFComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
        BlockFace direction = getStructureRelativeRotation(rotation);
        int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);
        ComponentTFTowerOutbuilding outbuilding = new ComponentTFTowerOutbuilding(getFeatureType(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
        // check to see if it intersects something already there
        StructureTFComponent intersect = StructureTFComponent.findIntersecting(list, outbuilding.getBoundingBox());
        if (intersect == null) {
            list.add(outbuilding);
            outbuilding.buildComponent(this, list, rand);
            addOpening(x, y, z, rotation);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        // make walls
        fillWithRandomizedBlocks(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1, false, rand, StructureTFComponentOld.getStrongholdStones());

        // clear inside
        fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);

        // nullify sky light
        nullifySkyLightForBoundingBox(world);

        // fix highestOpening parameter so we don't get a ginormous lich room
        if ((height - highestOpening) > 15) {
            highestOpening = height - 15;
        }


        // stairs!
        makeStairs(world, rand, sbb);

        // throw a bunch of opening markers in there
        //makeOpeningMarkers(world, rand, 100, sbb);

        // openings
        makeOpenings(world, sbb);

        // decorate?
        decorateStairFloor(world, rand, sbb);

        // stairway crossings
        makeStairwayCrossings(world, rand, sbb);

        // LICH TIME
        makeLichRoom(world, rand, sbb);

        // extra paintings in main tower
        makeTowerPaintings(world, rand, sbb);


        return true;
    }

    /**
     * Make 1-2 platforms joining the stairways
     */
    protected void makeStairwayCrossings(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        int flights = (this.highestOpening / 5) - 2;

        for (int i = 2 + rand.nextInt(2); i < flights; i += 1 + rand.nextInt(5)) {
            makeStairCrossing(world, rand, i, sbb);
        }
    }

    protected void makeStairCrossing(AsyncWorldEditor world, Random rand, int flight, StructureBoundingBox sbb) {
        BlockFace temp = this.getCoordBaseMode();
        if (flight % 2 == 0) {
            this.setCoordBaseMode(getStructureRelativeRotation(Rotation.CLOCKWISE_90));
        }

        // place platform
        int floorLevel = 0 + flight * 5;
        BlockData crossingfloor;
        if(rand.nextBoolean()) {
            Slab slab = (Slab) Bukkit.createBlockData(Material.SMOOTH_STONE_SLAB);
            slab.setType(Slab.Type.DOUBLE);
            crossingfloor = slab;
        } else {
            crossingfloor = Bukkit.createBlockData(Material.OAK_PLANKS);
        }
        for (int dx = 6; dx <= 8; dx++) {
            for (int dz = 4; dz <= 10; dz++) {
                setBlockState(world, crossingfloor, dx, floorLevel, dz, sbb);
            }
        }

        // put fences
        floorLevel++;
        int dx = 6;
        for (int dz = 3; dz <= 11; dz++) {
            setBlockState(world, Material.OAK_FENCE, dx, floorLevel, dz, sbb);
        }
        dx++;
        for (int dz = 3; dz <= 11; dz++) {
            setBlockState(world, AIR, dx, floorLevel, dz, sbb);
        }
        dx++;
        for (int dz = 3; dz <= 11; dz++) {
            setBlockState(world, Material.OAK_FENCE, dx, floorLevel, dz, sbb);
        }

        // we need 2 extra blocks and 2 extra fences to look good
        setBlockState(world, crossingfloor, 6, floorLevel - 1, 11, sbb);
        setBlockState(world, crossingfloor, 8, floorLevel - 1, 3, sbb);

        setBlockState(world, Material.OAK_FENCE, 5, floorLevel, 11, sbb);
        setBlockState(world, Material.OAK_FENCE, 9, floorLevel, 3, sbb);

        // place spawner in the middle
        EntityType mobID = EntityType.SKELETON;
        switch (rand.nextInt(4)) {
            case 0:
            case 1:
                mobID = EntityType.SKELETON;
                break;
            case 2:
                mobID = EntityType.ZOMBIE;
                break;
            case 3:
                mobID = EntityType.CAVE_SPIDER;
                break;
        }
        setSpawner(world, 7, floorLevel + 2, 7, sbb, mobID);

        // make a fence arch support for the spawner
        setBlockState(world, Material.OAK_FENCE, 6, floorLevel + 1, 7, sbb);
        setBlockState(world, Material.OAK_FENCE, 8, floorLevel + 1, 7, sbb);
        setBlockState(world, Material.OAK_FENCE, 6, floorLevel + 2, 7, sbb);
        setBlockState(world, Material.OAK_FENCE, 8, floorLevel + 2, 7, sbb);


        this.setCoordBaseMode(temp);
    }

    /**
     * Make a neat little room for the lich to fight in
     */
    protected void makeLichRoom(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        // figure out where the stairs end
        int floorLevel = 2 + (this.highestOpening / 5) * 5;
        // we need a floor
        final Rotation i = (this.highestOpening / 5) % 2 == 0 ? Rotation.NONE : Rotation.CLOCKWISE_90;
        makeLichFloor(world, floorLevel, i, sbb);

        // now a chandelier
        decorateLichChandelier(world, floorLevel, sbb);

        // and wall torches
        decorateTorches(world, rand, floorLevel, sbb);

        // make paintings
        decoratePaintings(world, rand, floorLevel, sbb);

        // seems like we should have a spawner
//        spawnBossLater(world, size / 2, floorLevel + 2, size / 2, TFBoss.Lich);
    }


    protected void makeTowerPaintings(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        int howMany = 10;

        // do wall 0.
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.WEST, 48, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.WEST, 32, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.WEST, 0, sbb);

        // do wall 1.
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.EAST, 48, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.EAST, 32, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.EAST, 0, sbb);
        // do wall 2.
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.NORTH, 48, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.NORTH, 32, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.NORTH, 0, sbb);
        // do wall 3.
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.SOUTH, 48, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.SOUTH, 32, sbb);
        generatePaintingsOnWall(world, rand, howMany, 0, BlockFace.SOUTH, 0, sbb);
    }

    /**
     * Make the floor for the liches room
     */
    protected void makeLichFloor(AsyncWorldEditor world, int floorLevel, Rotation rotation, StructureBoundingBox sbb) {
        BlockFace temp = this.getCoordBaseMode();
        this.setCoordBaseMode(getStructureRelativeRotation(rotation));

        BlockData birchSlab = StructureTFHelper.getSlabTop(Material.BIRCH_SLAB);
        Material birchPlank = Material.BIRCH_PLANKS;

        // place a platform there
        for (int fx = 1; fx < 14; fx++) {
            for (int fz = 1; fz < 14; fz++) {
                if ((fx == 1 || fx == 2) && (fz >= 6 && fz <= 12)) {
                    // blank, leave room for stairs
                    if (fz == 6) {
                        // upside down plank slabs
                        setBlockState(world, birchSlab, fx, floorLevel, fz, sbb);
                    }
                } else if ((fx == 12 || fx == 13) && (fz >= 3 && fz <= 8)) {
                    // blank, leave room for stairs
                    if (fz == 8) {
                        // upside down plank slabs
                        setBlockState(world, birchSlab, fx, floorLevel, fz, sbb);
                    }
                } else if ((fx >= 4 && fx <= 10) && (fz >= 4 && fz <= 10)) {
                    // glass floor in center, aside from 2 corners
                    if ((fx == 4 && fz == 4) || (fx == 10 && fz == 10)) {
                        setBlockState(world, birchPlank, fx, floorLevel, fz, sbb);
                    } else {
                        setBlockState(world, Material.GLASS, fx, floorLevel, fz, sbb);
                    }
                } else if ((fx == 2 || fx == 3) && (fz == 2 || fz == 3)) {
                    // glass blocks in the corners
                    setBlockState(world, Material.GLASS, fx, floorLevel, fz, sbb);
                } else if ((fx == 11 || fx == 12) && (fz == 11 || fz == 12)) {
                    // glass blocks in the corners
                    setBlockState(world, Material.GLASS, fx, floorLevel, fz, sbb);
                } else {
                    setBlockState(world, birchPlank, fx, floorLevel, fz, sbb);
                }
            }
        }

        // eliminate the railings
        setBlockState(world, AIR, 3, floorLevel + 1, 11, sbb);
        setBlockState(world, AIR, 3, floorLevel + 1, 10, sbb);
        setBlockState(world, AIR, 3, floorLevel + 2, 11, sbb);

        setBlockState(world, AIR, 11, floorLevel + 1, 3, sbb);
        setBlockState(world, AIR, 11, floorLevel + 1, 4, sbb);
        setBlockState(world, AIR, 11, floorLevel + 2, 3, sbb);

        this.setCoordBaseMode(temp);

    }

    /**
     * Make a fancy chandelier for the lich's room
     */
    protected void decorateLichChandelier(AsyncWorldEditor world, int floorLevel, StructureBoundingBox sbb) {
        int cx = size / 2;
        int cy = floorLevel + 4;
        int cz = size / 2;

        setBlockState(world, Material.OAK_FENCE, cx + 1, cy, cz + 0, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 2, cy, cz + 0, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 1, cy, cz + 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz + 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz + 2, sbb);
        setBlockState(world, Material.OAK_FENCE, cx - 1, cy, cz + 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx - 1, cy, cz + 0, sbb);
        setBlockState(world, Material.OAK_FENCE, cx - 2, cy, cz + 0, sbb);
        setBlockState(world, Material.OAK_FENCE, cx - 1, cy, cz - 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz - 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz - 2, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 1, cy, cz - 1, sbb);

        cy++;
        setBlockState(world, Material.OAK_FENCE, cx + 1, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx + 2, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx + 1, cy, cz + 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz + 1, sbb);
        setBlockState(world, Material.TORCH, cx + 0, cy, cz + 2, sbb);
        setBlockState(world, Material.TORCH, cx - 1, cy, cz + 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx - 1, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx - 2, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx - 1, cy, cz - 1, sbb);
        setBlockState(world, Material.OAK_FENCE, cx + 0, cy, cz - 1, sbb);
        setBlockState(world, Material.TORCH, cx + 0, cy, cz - 2, sbb);
        setBlockState(world, Material.TORCH, cx + 1, cy, cz - 1, sbb);

        cy++;
        setBlockState(world, Material.TORCH, cx + 1, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx + 0, cy, cz + 1, sbb);
        setBlockState(world, Material.TORCH, cx - 1, cy, cz + 0, sbb);
        setBlockState(world, Material.TORCH, cx + 0, cy, cz - 1, sbb);

        for (int y = floorLevel + 5; y < height - 1; y++) {
            setBlockState(world, Material.OAK_FENCE, cx + 0, y, cz + 0, sbb);
        }
        int x = cx;
        int y = floorLevel + 1;
        int z = cz;
        
        SpawnerBoss_Later.generate_later(world, new Coord(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z)), TFBoss.Lich);
        
    }
    
    /**
     * Cover the walls in the lich's room with paintings.  How is this going to work, chunk by chunk?
     */
    protected void decoratePaintings(AsyncWorldEditor world, Random rand, int floorLevel, StructureBoundingBox sbb) {
        int howMany = 100;

        for (final BlockFace horizontal : HORIZONTALS) {
            // do wall 0.
            generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 48, sbb);
            generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 32, sbb);
            generatePaintingsOnWall(world, rand, howMany, floorLevel, horizontal, 0, sbb);
        }
    }

    /**
     * Put torches on each wall
     */
    protected void decorateTorches(AsyncWorldEditor world, Random rand, int floorLevel, StructureBoundingBox sbb) {
        generateTorchesOnWall(world, rand, floorLevel, BlockFace.SOUTH, sbb);
        generateTorchesOnWall(world, rand, floorLevel, BlockFace.EAST, sbb);
        generateTorchesOnWall(world, rand, floorLevel, BlockFace.NORTH, sbb);
        generateTorchesOnWall(world, rand, floorLevel, BlockFace.WEST, sbb);
    }

    // TODO: make it work with async world editor
    /**
     * Place up to 5 torches (with fence holders) on the wall, checking that they don't overlap any paintings or other torches
     */
    protected void generateTorchesOnWall(AsyncWorldEditor world, Random rand,
                                         int floorLevel, BlockFace direction, StructureBoundingBox sbb) {
//        for (int i = 0; i < 5; i++) {
//            // get some random coordinates on the wall in the chunk
//            BlockPos wCoords = getRandomWallSpot(rand, floorLevel, direction, sbb);
//
//            // offset to see where the fence should be
//            BlockPos.MutableBlockPos tCoords = new BlockPos.MutableBlockPos(wCoords);
//
//            // is there a painting or another torch there?
//            AxisAlignedBB torchBox = new AxisAlignedBB(tCoords.getX(), tCoords.getY(), tCoords.getZ(), tCoords.getX() + 1.0, tCoords.getY() + 2.0, tCoords.getZ() + 1.0);
//            IBlockState blockState = world.getBlockState(tCoords);
//            IBlockState aboveBlockState = world.getBlockState(tCoords.up());
//            if (blockState.getMaterial() == Material.AIR &&
//                    aboveBlockState.getMaterial() == Material.AIR &&
//                    world.getEntitiesWithinAABBExcludingEntity(null, torchBox).size() == 0) {
//                // if not, place a torch
//                world.setBlockState(tCoords, Material.OAK_FENCE, 2);
//                world.setBlockState(tCoords.up(), Material.TORCH.withProperty(BlockTorch.FACING, BlockFace.UP), 2);
//            }
//        }
    }


}
