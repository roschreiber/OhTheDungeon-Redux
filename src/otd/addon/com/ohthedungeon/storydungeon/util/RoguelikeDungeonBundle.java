/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.util;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import otd.lib.ZoneWorldEditor;

public class RoguelikeDungeonBundle {
    private final ZoneWorldEditor zwe;
    private final IDungeon dungeon;
    private final ISettings settings;
    public RoguelikeDungeonBundle(ZoneWorldEditor zwe, IDungeon dungeon, ISettings settings) {
        this.zwe = zwe;
        this.dungeon = dungeon;
        this.settings = settings;
    }
    
    public ZoneWorldEditor getZoneWorldEditor() {
        return this.zwe;
    }
    public IDungeon getDungeon() {
        return this.dungeon;
    }
    public ISettings getSettings() {
        return this.settings;
    }
}
