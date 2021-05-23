package otd.lib.async.io.papermc.lib.features.blockstatesnapshot;

import org.bukkit.block.Block;

public interface BlockStateSnapshot {
    BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot);
}
