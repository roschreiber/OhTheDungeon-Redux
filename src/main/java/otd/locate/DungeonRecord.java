package otd.locate;

public final class DungeonRecord {
    public final String type;
    public final String world;
    public final int x;
    public final int z;
    public final long time;

    public DungeonRecord(String type, String world, int x, int z, long time) {
        this.type = type;
        this.world = world;
        this.x = x;
        this.z = z;
        this.time = time;
    }
}
