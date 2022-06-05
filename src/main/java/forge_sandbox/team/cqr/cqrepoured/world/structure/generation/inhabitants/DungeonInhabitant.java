package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants;

import org.bukkit.entity.EntityType;

import otd.util.BossInfo;

public class DungeonInhabitant {
	public String getName() {
		return "default";
	}

	public EntityType getEntityID() {
		return EntityType.ZOMBIE;
	}

	public BossInfo getBossInfo() {
		return null;
	}
}
