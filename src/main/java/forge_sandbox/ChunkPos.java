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
package forge_sandbox;

public class ChunkPos {
	public final int x;
	public final int z;

	public ChunkPos(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public ChunkPos(BlockPos pos) {
		this.x = pos.getX() >> 4;
		this.z = pos.getZ() >> 4;
	}
}