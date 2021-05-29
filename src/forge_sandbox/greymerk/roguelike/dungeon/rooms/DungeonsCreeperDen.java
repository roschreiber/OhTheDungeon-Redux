package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.ChestPlacementException;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonsCreeperDen extends DungeonBase {

    @Override
    public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

        ITheme theme = settings.getTheme();
        
        Coord start;
        Coord end;
        
        MetaBlock tnt = BlockType.get(BlockType.TNT);
        
        BlockWeightedRandom mossy = new BlockWeightedRandom();
        mossy.addBlock(theme.getPrimary().getWall(), 3);
        mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
        
        BlockWeightedRandom floor = new BlockWeightedRandom();
        floor.addBlock(theme.getPrimary().getFloor(), 1);
        mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
        floor.addBlock(BlockType.get(BlockType.GRAVEL), 3);
        
        BlockWeightedRandom subfloor = new BlockWeightedRandom();
        subfloor.addBlock(floor, 3);
        subfloor.addBlock(tnt, 1);
        
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(new Coord(-4, -4, -4));
        end.add(new Coord(4, 5, 4));
        RectHollow.fill(editor, rand, start, end, mossy, false, true);
        
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(new Coord(-3, -1, -3));
        end.add(new Coord(3, -1, 3));
        RectSolid.fill(editor, rand, start, end, floor, true, true);
        
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(new Coord(-3, -3, -3));
        end.add(new Coord(3, -2, 3));
        RectSolid.fill(editor, rand, start, end, subfloor, true, true);
        
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(new Coord(-3, 0, -3));
        end.add(new Coord(3, 0, 3));
        
        List<Coord> chestSpaces = new RectSolid(start, end).get();
        Collections.shuffle(chestSpaces, rand);
        
        int counter = 0;
        for(Coord spot : chestSpaces){
            if(Treasure.isValidChestSpace(editor, spot)){
                try {
                    Treasure.generate(editor, rand, spot, Treasure.ORE, settings.getDifficulty(spot), true);
                } catch (ChestPlacementException cpe){
                    // do nothing
                }
                Coord cursor = new Coord(spot);
                cursor.add(Cardinal.DOWN, 2);
                tnt.set(editor, cursor);
                ++counter;
            }
            
            if(counter >= 2) break;
        }
        
        Spawner.generate(editor, rand, settings, new Coord(origin), Spawner.CREEPER);
        
        return true;
    }
    
    @Override
    public int getSize(){
        return 7;
    }
}
