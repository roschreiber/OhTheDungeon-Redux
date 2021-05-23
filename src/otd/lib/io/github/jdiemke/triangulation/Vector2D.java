/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.lib.io.github.jdiemke.triangulation;

/**
 * 2D vector class implementation.
 * 
 * @author Johannes Diemke
 */
public class Vector2D {

    public double x;
    public double y;

    /**
     * Constructor of the 2D vector class used to create new vector instances.
     * 
     * @param x
     *            The x coordinate of the new vector
     * @param y
     *            The y coordinate of the new vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Subtracts the given vector from this.
     * 
     * @param vector
     *            The vector to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    public Vector2D sub(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Adds the given vector to this.
     * 
     * @param vector
     *            The vector to be added to this
     * @return A new instance holding the result of the vector addition
     */
    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Multiplies this by the given scalar.
     * 
     * @param scalar
     *            The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public Vector2D mult(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Computes the magnitude or length of this.
     * 
     * @return The magnitude of this
     */
    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Computes the dot product of this and the given vector.
     * 
     * @param vector
     *            The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public double dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    /**
     * Computes the 2D pseudo cross product Dot(Perp(this), vector) of this and
     * the given vector.
     * 
     * @param vector
     *            The vector to be multiplied to the perpendicular vector of
     *            this
     * @return A new instance holding the result of the pseudo cross product
     */
    public double cross(Vector2D vector) {
        return this.y * vector.x - this.x * vector.y;
    }

    @Override
    public String toString() {
        return "Vector2D[" + x + ", " + y + "]";
    }

}