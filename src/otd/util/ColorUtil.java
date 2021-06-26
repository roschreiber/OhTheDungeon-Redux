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
package otd.util;

/**
 *
 * @author shadow
 */
public class ColorUtil {
     /**
    * Converts an HSL color value to RGB. Conversion formula
    * adapted from http://en.wikipedia.org/wiki/HSL_color_space.
    * Assumes h, s, and l are contained in the set [0, 1] and
    * returns r, g, and b in the set [0, 255].
    *
    * @param h       The hue
    * @param s       The saturation
    * @param l       The lightness
    * @return int array, the RGB representation
    */
   public static int[] hslToRgb(float h, float s, float l){
       float r, g, b;

       if (s == 0f) {
           r = g = b = l; // achromatic
       } else {
           float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
           float p = 2 * l - q;
           r = hueToRgb(p, q, h + 1f/3f);
           g = hueToRgb(p, q, h);
           b = hueToRgb(p, q, h - 1f/3f);
       }
       int[] rgb = {to255(r), to255(g), to255(b)};
       return rgb;
   }
   public static int to255(float v) { return (int)Math.min(255,256*v); }

   /** Helper method that converts hue to rgb */
   public static float hueToRgb(float p, float q, float t) {
       if (t < 0f)
           t += 1f;
       if (t > 1f)
           t -= 1f;
       if (t < 1f/6f)
           return p + (q - p) * 6f * t;
       if (t < 1f/2f)
           return q;
       if (t < 2f/3f)
           return p + (q - p) * (2f/3f - t) * 6f;
       return p;
   }
}
