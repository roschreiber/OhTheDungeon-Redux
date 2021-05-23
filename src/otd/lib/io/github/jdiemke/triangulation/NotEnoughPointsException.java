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
 * Exception thrown by the Delaunay triangulator when it is initialized with
 * less than three points.
 * 
 * @author Johannes Diemke
 */
public class NotEnoughPointsException extends Exception {

    private static final long serialVersionUID = 7061712854155625067L;

    public NotEnoughPointsException() {
    }

    public NotEnoughPointsException(String s) {
        super(s);
    }

}