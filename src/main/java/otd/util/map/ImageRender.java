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
package otd.util.map;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/**
 *
 * @author shadow
 */
public class ImageRender extends MapRenderer {

	private BufferedImage image;

	public static ImageRender bunker = new ImageRender("/image/bunker.png");
	public static ImageRender cactus = new ImageRender("/image/cactus.png");
	public static ImageRender desert = new ImageRender("/image/desert.png");
	public static ImageRender forest = new ImageRender("/image/forest.png");
	public static ImageRender house = new ImageRender("/image/house.png");
	public static ImageRender ice = new ImageRender("/image/ice.png");
	public static ImageRender jungle = new ImageRender("/image/jungle.png");
	public static ImageRender mesa = new ImageRender("/image/mesa.png");
	public static ImageRender mountain = new ImageRender("/image/mountain.png");
	public static ImageRender ruin = new ImageRender("/image/ruin.png");
	public static ImageRender swamp = new ImageRender("/image/swamp.png");

	private ImageRender(String file) {
		InputStream stream = MapManager.class.getResourceAsStream(file);
		try {
			image = ImageIO.read(stream);
			image = resize(image, 128);
		} catch (IOException ex) {
			image = null;
		}
	}

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		if (image != null)
			mapCanvas.drawImage(0, 0, image);
	}

	private static BufferedImage resize(BufferedImage src, int targetSize) {
		if (targetSize <= 0) {
			return src; // this can't be resized
		}
		int targetWidth = targetSize;
		int targetHeight = targetSize;
		float ratio = ((float) src.getHeight() / (float) src.getWidth());
		if (ratio <= 1) { // square or landscape-oriented image
			targetHeight = (int) Math.ceil((float) targetWidth * ratio);
		} else { // portrait image
			targetWidth = Math.round((float) targetHeight / ratio);
		}
		BufferedImage bi = new BufferedImage(targetWidth, targetHeight,
				src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
						: BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // produces
																												// a
																												// balanced
																												// resizing
																												// (fast
																												// and
																												// decent
																												// quality)
		g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
		g2d.dispose();
		return bi;
	}

}