package forge_sandbox.jaredbgreat.dldungeons.pieces;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

import forge_sandbox.jaredbgreat.dldungeons.planner.Symmetry;

import java.util.Random;

import static forge_sandbox.jaredbgreat.dldungeons.pieces.Shape.*;

/**
 * Families of shapes, each representing a single shape in each of its
 * rotations.
 * 
 * Each enumeration constant has an associates Shape area from the shape class
 * allows with minimum dimensions to hold the shape without loose details to
 * rounding.
 * 
 * This class also helps find a shape for a given symmetry class.
 * 
 * @author Jared Blackburn
 *
 */
public enum Shapes {

	X(1, 1, xgroup), L(2, 2, lgroup), O(3, 3, ogroup), T(3, 3, tgroup), F(4, 5, fgroup), E(4, 5, egroup),
	I(3, 3, igroup), C(3, 3, cgroup), U(3, 3, ugroup), S(5, 5, sgroup);

	public final int minx;
	public final int miny;
	public final Shape[] family;

	Shapes(int minx, int miny, Shape[] family) {
		this.minx = minx;
		this.miny = miny;
		this.family = family;
	}

	/**
	 * Will return a random shape that fits a given symmetry.
	 * 
	 * @param sym
	 * @param random
	 * @return
	 */
	public static Shapes wholeShape(Symmetry sym, Random random) {
		switch (sym) {
		case NONE:
			return Symmetry.allPart[random.nextInt(Symmetry.allPart.length)];
		case R:
		case SW:
			return Symmetry.rotatedPart[random.nextInt(Symmetry.rotatedPart.length)];
		case TR1:
		case TR2:
			return Symmetry.transPart[random.nextInt(Symmetry.transPart.length)];
		case X:
			return Symmetry.xsymmetricPart[random.nextInt(Symmetry.xsymmetricPart.length)];
		case XZ:
			return Symmetry.xysymmetricPart[random.nextInt(Symmetry.xysymmetricPart.length)];
		case Z:
			return Symmetry.ysymmetricPart[random.nextInt(Symmetry.ysymmetricPart.length)];
		default:
			return Symmetry.allPart[random.nextInt(Symmetry.allPart.length)];
		}
	}

}
