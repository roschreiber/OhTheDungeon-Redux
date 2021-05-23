// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

public class Direction
{
    public enum Chest
    {
        NORTH("NORTH", 0, 2), 
        SOUTH("SOUTH", 1, 3), 
        WEST("WEST", 2, 4), 
        EAST("EAST", 3, 5);
        
        private byte data;
        
        private Chest(final String name, final int ordinal, final int d) {
            this.data = (byte)d;
        }
        
        public byte getData() {
            return this.data;
        }
    }
    
    public enum Door
    {
        WESTBYNORTHWEST("WESTBYNORTHWEST", 0), 
        NORTHBYNORTHEAST("NORTHBYNORTHEAST", 1), 
        EASTBYSOUTHEAST("EASTBYSOUTHEAST", 2), 
        SOUTHBYSOUTHWEST("SOUTHBYSOUTHWEST", 3), 
        NORTHBYNORTHWEST("NORTHBYNORTHWEST", 4), 
        EASTBYNORTHEAST("EASTBYNORTHEAST", 5), 
        SOUTHBYSOUTHEAST("SOUTHBYSOUTHEAST", 6), 
        WESTBYSOUTHWEST("WESTBYSOUTHWEST", 7);
        
        private Door(final String name, final int ordinal) {
        }
        
        public byte getData() {
            return (byte)this.ordinal();
        }
    }
    
    public enum Facing
    {
        SOUTH("SOUTH", 0), 
        WEST("WEST", 1), 
        NORTH("NORTH", 2), 
        EAST("EAST", 3);
        
        private Facing(final String name, final int ordinal) {
        }
    }
    
    public enum Ladder
    {
        NORTH("NORTH", 0, 2), 
        SOUTH("SOUTH", 1, 3), 
        WEST("WEST", 2, 4), 
        EAST("EAST", 3, 5);
        
        private byte data;
        
        private Ladder(final String name, final int ordinal, final int d) {
            this.data = (byte)d;
        }
        
        public byte getData() {
            return this.data;
        }
    }
    
    public enum Ordinal
    {
        SOUTHWEST("SOUTHWEST", 0), 
        NORTHWEST("NORTHWEST", 1), 
        NORTHEAST("NORTHEAST", 2), 
        SOUTHEAST("SOUTHEAST", 3);
        
        private Ordinal(final String name, final int ordinal) {
        }
    }
    
    public enum Stair
    {
        EAST("EAST", 0), 
        WEST("WEST", 1), 
        SOUTH("SOUTH", 2), 
        NORTH("NORTH", 3);
        
        private Stair(final String name, final int ordinal) {
        }
        
        public byte getData() {
            return (byte)this.ordinal();
        }
    }
    
    public enum StairWell
    {
        CENTER("CENTER", 0), 
        NORTHWEST("NORTHWEST", 1), 
        NORTHEAST("NORTHEAST", 2), 
        SOUTHWEST("SOUTHWEST", 3), 
        SOUTHEAST("SOUTHEAST", 4);
        
        private StairWell(final String name, final int ordinal) {
        }
    }
    
    public enum Torch
    {
        EAST("EAST", 0, 1), 
        WEST("WEST", 1, 2), 
        SOUTH("SOUTH", 2, 3), 
        NORTH("NORTH", 3, 4), 
        FLOOR("FLOOR", 4, 5);
        
        private byte data;
        
        private Torch(final String name, final int ordinal, final int d) {
            this.data = (byte)d;
        }
        
        public byte getData() {
            return this.data;
        }
    }
    
    public enum TrapDoor
    {
        SOUTH("SOUTH", 0), 
        NORTH("NORTH", 1), 
        EAST("EAST", 2), 
        WEST("WEST", 3);
        
        private TrapDoor(final String name, final int ordinal) {
        }
        
        public byte getData() {
            return (byte)this.ordinal();
        }
    }
    
    public enum Vine
    {
        SOUTH("SOUTH", 0, 1), 
        WEST("WEST", 1, 2), 
        NORTH("NORTH", 2, 4), 
        EAST("EAST", 3, 8);
        
        private byte data;
        
        private Vine(final String name, final int ordinal, final int d) {
            this.data = (byte)d;
        }
        
        public byte getData() {
            return this.data;
        }
    }
}
