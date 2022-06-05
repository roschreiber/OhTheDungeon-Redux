/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeonsengine.config;

import java.util.List;

import forge_sandbox.com.someguyssoftware.gottschcore.Quantity;

/**
 * @author Mark Gottschling on Dec 18, 2018
 *
 */
@SuppressWarnings("deprecation")
public interface IDungeonConfig {

	public Quantity getNumLevels();

	public void setNumLevels(Quantity num);

	public int getTopLimit();

	public void setTopLimit(int limit);

	public int getBottomLimit(int bottom);

	public void setBottomLimit(int limit);

	public ILevelConfig[] getLevelConfigs();

	public void setLevelConfigs(ILevelConfig[] configs);

	Integer getSurfaceBuffer();

	void setSurfaceBuffer(Integer surfaceBuffer);

	String getName();

	void setName(String name);

	DungeonSize getSize();

	void setSize(DungeonSize size);

	List<String> getBiomeWhiteList();

	void setBiomeWhiteList(List<String> biomeWhiteList);

	List<String> getBiomeBlackList();

	void setBiomeBlackList(List<String> biomeBlackList);

	double getBoundaryFactor();

	void setBoundaryFactor(double factor);

	String getVersion();

	void setVersion(String version);

	ILevelConfig getSurfaceConfig();

	void setSurfaceConfig(ILevelConfig surfaceConfig);

	boolean isMinecraftConstraints();

	void setMinecraftConstraints(boolean minecraftConstraints);

}
