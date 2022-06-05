package forge_sandbox;

public class ResourceLocation {
	private final String parent, path;

	public ResourceLocation(String parent, String path) {
		this.parent = parent;
		this.path = path;
	}

	public String getNBT() {
		return "/" + parent + "/" + path + ".nbt";
	}
}
