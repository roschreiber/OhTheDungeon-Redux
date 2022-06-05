package forge_sandbox.greymerk.roguelike.worldgen;

import forge_sandbox.greymerk.roguelike.worldgen.shapes.IShape;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.Shape;

public interface IBounded {

	public BoundingBox getBoundingBox();

	public boolean collide(IBounded other);

	public IShape getShape(Shape type);

	public Coord getStart();

	public Coord getEnd();

}
