package forge_sandbox.greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class MetaBlock extends BlockBase {

	public BlockData state;
	public int flag;

	public MetaBlock(Material block) {
		this.state = Bukkit.createBlockData(block);
		flag = 2;
	}

	public MetaBlock(MetaBlock block) {
		this.state = block.getState();
		flag = 2;
	}

	public MetaBlock(BlockData state) {
		this.setState(state);
		flag = 2;
	}

	public MetaBlock(JsonElement e) throws Exception {
		JsonObject json = (JsonObject) e;
		String name = json.get("name").getAsString();

		try {
			state = Bukkit.createBlockData(name);
		} catch (Exception ex) {

		}
		if (state == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Error name in json : " + name);
		}
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
	}

	public void setState(BlockData state) {

		this.state = state;
	}

	public boolean set(IWorldEditor editor, Coord pos) {
		return editor.setBlock(pos, this, true, true);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.setBlock(pos, this, fillAir, replaceSolid);
	}

	public BlockData getState() {

		return this.state;
	}

	public Material getBlock() {
		return this.getState().getMaterial();
	}

	public int getFlag() {
		return this.flag;
	}

	@Override
	public String toString() {
		return this.state.getAsString();
	}

	public Material getMaterial() {
		return this.state.getMaterial();
	}

	public boolean isFullBlock() {
		return this.state.getMaterial().isSolid();
	}

	public boolean isOpaqueCube() {
		return isFullBlock();
	}
}
