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
package otd.dungeon.dungeonmaze.util;

public class NumberUtils {

	/**
	 * Calculate the distance between two 2D points. Note: For the x and y
	 * coordinate of the first point, zero is used.
	 *
	 * @param x X coordinate of second point.
	 * @param y Y coordinate of second point.
	 *
	 * @return Distance between the two points.
	 */
	public static double distanceFromZero(int x, int y) {
		return distance(0, 0, x, y);
	}

	/**
	 * Calculate the distance between two 2D points.
	 *
	 * @param x1 X coordinate of first point.
	 * @param y1 Y coordinate of first point.
	 * @param x2 X coordinate of second point.
	 * @param y2 Y coordinate of second point.
	 *
	 * @return Distance between the two points.
	 */
	public static double distance(int x1, int y1, int x2, int y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}
}
