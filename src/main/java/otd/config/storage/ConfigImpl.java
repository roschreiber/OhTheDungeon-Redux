package otd.config.storage;

public interface ConfigImpl {
	public void load();

	public String getValue(String string);

	public void setValue(String key, String value);

	public void close();
}
