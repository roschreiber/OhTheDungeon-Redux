package forge_sandbox.greymerk.roguelike.util;

import java.util.Random;

public interface IWeighted<T> {

	public int getWeight();

	public T get(Random rand);

}
