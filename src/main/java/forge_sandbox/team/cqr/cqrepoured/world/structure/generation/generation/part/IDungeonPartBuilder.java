package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.part;

import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonPlacement;
import otd.lib.async.AsyncWorldEditor;

public interface IDungeonPartBuilder {

	IDungeonPart build(AsyncWorldEditor world, DungeonPlacement placement);

}
