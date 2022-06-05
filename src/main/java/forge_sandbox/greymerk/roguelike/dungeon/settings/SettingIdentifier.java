package forge_sandbox.greymerk.roguelike.dungeon.settings;

//import net.minecraft.util.ResourceLocation;

public class SettingIdentifier {

//	private ResourceLocation identifier;
	private String namespace, name;

	public SettingIdentifier(String namespace, String name) {
//		this.identifier = new ResourceLocation(namespace, name);
		this.name = name;
		this.namespace = namespace;
	}

	public SettingIdentifier(String name) {
		String[] parts;
		parts = name.split(":");
		if (parts.length > 1) {
//			this.identifier = new ResourceLocation(parts[0], parts[1]);
			this.namespace = parts[0];
			this.name = parts[1];
			return;
		}

		this.namespace = SettingsContainer.DEFAULT_NAMESPACE;
		this.name = name;

//		this.identifier = new ResourceLocation(SettingsContainer.DEFAULT_NAMESPACE, name);
	}

	public String getNamespace() {
//		return this.identifier.getResourceDomain();
		return this.namespace;
	}

	public String getName() {
//		return this.identifier.getResourcePath();
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SettingIdentifier))
			return false;
		return (this.namespace == null ? ((SettingIdentifier) o).namespace == null
				: this.namespace.equals(((SettingIdentifier) o).namespace))
				&& (this.name == null ? ((SettingIdentifier) o).name == null
						: this.name.equals(((SettingIdentifier) o).name));
//		return this.identifier.equals(((SettingIdentifier)o).identifier);
	}

	@Override
	public String toString() {
		return this.getNamespace() + ":" + this.getName();
	}
}
