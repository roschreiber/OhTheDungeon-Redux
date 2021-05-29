package forge_sandbox.greymerk.roguelike.treasure;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;

public interface ITreasureChest {
    public Coord getPos();
    public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException;
    public boolean setSlot(int slot, ItemStack item);
    public boolean setRandomEmptySlot(ItemStack item);
    public void setLootTable(LootTable table);
    public Treasure getType();
    public int getSize();
    public int getLevel();
    public void addBufferedItems(IWorldEditor editor);
}