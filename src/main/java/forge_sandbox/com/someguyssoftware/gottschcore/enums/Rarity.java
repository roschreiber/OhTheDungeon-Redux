/**
 * 
 */
package forge_sandbox.com.someguyssoftware.gottschcore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Mark Gottschling on Jan 2, 2019
 * @since 1.7.0
 */
public enum Rarity implements IRarity {
	COMMON(0, "common"), UNCOMMON(1, "uncommon"), SCARCE(2, "scarce"), RARE(3, "rare"), EPIC(4, "epic"),
	BOSS(5, "boss"), UNIQUE(99, "unique");

	private static final Map<Integer, IEnum> codes = new HashMap<Integer, IEnum>();
	private static final Map<String, IEnum> values = new HashMap<String, IEnum>();
	private Integer code;
	private String value;

	/**
	 * 
	 * @param value
	 */
	Rarity(int code, String value) {
		this.code = code;
		this.value = value;
	}

	/**
	 * 
	 */
	public String toString() {
		return name();
	}

	// setup reverse lookup
	static {
		for (Rarity ps : EnumSet.allOf(Rarity.class)) {
			codes.put(ps.getCode(), ps);
			values.put(ps.getValue(), ps);
		}
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Map<Integer, IEnum> getCodes() {
		return codes;
	}

	@Override
	public Map<String, IEnum> getValues() {
		return values;
	}
}
