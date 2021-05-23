/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator.noise;

import java.util.Random;

public class FMB_RMF {

    private static final int[] ms_p = new int[512];

    public FMB_RMF(final long seed) {
        final Random rand = new Random(seed);
        final int nbVals = (1 << 8);
        final int[] ms_perm = new int[nbVals];

        for (int i = 0; i < nbVals; i++) {
            ms_perm[i] = -1;
        }
        for (int i = 0; i < nbVals; i++) {
            while (true) {
                final int p = rand.nextInt(256);
                if (ms_perm[p] == -1) {
                    ms_perm[p] = i;
                    break;
                }
            }
        }

        for (int i = 0; i < nbVals; i++) {
            ms_p[nbVals + i] = ms_p[i] = ms_perm[i];
        }

    }

    private static double noise(double x, double y, double z) {
        final int X = (int) x & 255;
        final int Y = (int) y & 255;
        final int Z = (int) z & 255;
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);
        final double u = fade(x);
        final double v = fade(y);
        final double w = fade(z);
        final int A = ms_p[X] + Y;
        final int AA = ms_p[A] + Z;
        final int AB = ms_p[A + 1] + Z;
        final int B = ms_p[X + 1] + Y;
        final int BA = ms_p[B] + Z;
        final int BB = ms_p[B + 1] + Z;
        return lerp(w, lerp(v, lerp(u, grad(ms_p[AA], x, y, z), grad(ms_p[BA], x - 1, y, z)), lerp(u, grad(ms_p[AB], x, y - 1, z), grad(ms_p[BB], x - 1, y - 1, z))), lerp(v, lerp(u, grad(ms_p[AA + 1], x, y, z - 1), grad(ms_p[BA + 1], x - 1, y, z - 1)), lerp(u, grad(ms_p[AB + 1], x, y - 1, z - 1), grad(ms_p[BB + 1], x - 1, y - 1, z - 1))));
    }

    private static double fade(final double t) {
        return (t * t * t * (t * (t * 6 - 15) + 10));
    }

    private static double lerp(final double t, final double a, final double b) {
        return (a + t * (b - a));
    }

    private static double grad(final int hash, final double x, final double y, final double z) {
        final int h = hash & 15;
        final double u = h < 8 ? x : y;
        final double v = h < 4 ? y : h == 12 || h == 14 ? x : z;

        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public static double ridge(double h, final float offset) {
        h = Math.abs(h);
        h = offset - h;
        h = h * h;
        return h;
    }

    public double noise_RidgedMultiFractal(final double x, final double y, final double z, final int octaves, final float lacunarity, final float gain, final float offset) {
        double sum = 0;
        float amplitude = 0.5f;
        float frequency = 1.0f;
        double prev = 1.0f;

        for (int i = 0; i < octaves; i++) {
            final double n = ridge(noise(x * frequency, y * frequency, z * frequency), offset);
            sum += n * amplitude * prev;
            prev = n;
            frequency *= lacunarity;
            amplitude *= gain;
        }
        return sum;
    }

    public double noise_FractionalBrownianMotion(final double x, final double y, final double z, final int octaves, final float lacunarity, final float gain) {
        double frequency = 1.0f;
        double amplitude = 0.5f;
        double sum = 0.0f;

        for (int i = 0; i < octaves; i++) {
            sum += noise(x * frequency, y * frequency, z * frequency) * amplitude;
            frequency *= lacunarity;
            amplitude *= gain;
        }
        return sum;
    }

}
