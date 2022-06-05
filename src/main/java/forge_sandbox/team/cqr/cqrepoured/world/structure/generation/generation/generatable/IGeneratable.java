package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable;

import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;
import otd.lib.async.AsyncWorldEditor;

public interface IGeneratable {

	void generate(AsyncWorldEditor world, GeneratableDungeon dungeon);

}
