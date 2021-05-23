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
 * Edge distance pack class implementation used to describe the distance to a
 * given edge.
 * 
 * @author Johannes Diemke
 */
public class EdgeDistancePack implements Comparable<EdgeDistancePack> {

    public Edge2D edge;
    public double distance;

    /**
     * Constructor of the edge distance pack class used to create a new edge
     * distance pack instance from a 2D edge and a scalar value describing a
     * distance.
     * 
     * @param edge
     *            The edge
     * @param distance
     *            The distance of the edge to some point
     */
    public EdgeDistancePack(Edge2D edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(EdgeDistancePack o) {
        return Double.compare(this.distance, o.distance);
    }

}