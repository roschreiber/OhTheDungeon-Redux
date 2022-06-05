package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.preparable;

import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonPlacement;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable.IGeneratable;
import otd.lib.async.AsyncWorldEditor;

public interface IPreparable<T extends IGeneratable> {

	default T prepare(AsyncWorldEditor world, DungeonPlacement placement) {
		return this.prepareNormal(world, placement);
	}

	T prepareNormal(AsyncWorldEditor world, DungeonPlacement placement);

	T prepareDebug(AsyncWorldEditor world, DungeonPlacement placement);

}
