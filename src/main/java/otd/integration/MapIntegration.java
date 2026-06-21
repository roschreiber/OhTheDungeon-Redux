package otd.integration;

import otd.locate.DungeonRecord;
import java.util.List;

public interface MapIntegration {
    String name();
    boolean isReady();
    boolean isEnabled();
    void enable();
    void addMarker(DungeonRecord r);
    void reload(List<DungeonRecord> records);
}
