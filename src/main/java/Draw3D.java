// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Draw3D {

    /**
     * A lookup table for 1 / n in 16.16 fixed integer form for values [0...2048]. See below for usages.
     *
     * @see Model#drawNearClippedFace(int)
     */
    public static final int[] reciprocal16 = new int[2048];
    /**
     * A lookup table for (1 / n) in 17.15 fixed integer form for values [0...512]. See below for usages.
     *
     * @see #drawGouraudScanline(int[], int, int, int, int, int)
     * @see #drawTexturedScanline(int[], int[], int, int, int, int, int, int, int, int, int, int, int, int, int)
     */
    public static final int[] reciprocal15 = new int[512];
    public static boolean lowmem = true;
    /**
     * Setting this to <code>true</code> enables horizontal clipping for scanlines.
     */
    public static boolean clipX;

    /**
     * Used with {@link #fillTexturedTriangle(int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int)} to
     * avoid branching.
     */
    public static boolean opaque;
    /**
     * Unrolls the loops for drawing gouraud triangles to produce a jagged look.
     *
     * @see #drawGouraudScanline(int[], int, int, int, int, int)
     */
    public static boolean jagged = true;
    public static int alpha;
    public static int centerX;
    public static int centerY;
    /**
     * A lookup table for the sine function using 16.16 fixed integers. The unit of measurement for angles using this
     * table are [0...2047], 1024 being pi.
     */
    public static int[] sin = new int[2048];
    /**
     * @see #sin
     */
    public static int[] cos = new int[2048];
    /**
     * A lookup table for (y * width).
     */
    public static int[] lineOffset;

    public static int textureCount;
    public static Image8[] textures = new Image8[50];
    public static boolean[] textureTranslucent = new boolean[50];
    public static int[] averageTextureRGB = new int[50];

    public static int poolSize;
    public static int[][] texelPool;
    public static int[][] activeTexels = new int[50][];
    public static int[] textureCycle = new int[50];
    public static int cycle;

    /**
     * A lookup table for HSL->RGB. A one dimensional representation of a 128x512 image. The structure of the HSL
     * colors is as follows:
     * <p>
     * (hue << 10) | (saturation << 7) | lightness
     * <p>
     * Interpolation of colors is possible since the lightness component is the least significant, allowing any
     * difference values to be the difference in lightness between two colors.
     *
     * @see #drawGouraudScanline(int[], int, int, int, int, int)
     */
    public static int[] palette = new int[0x10000];
    public static int[][] texturePalette = new int[50][];

    static {
        for (int i = 1; i < 512; i++) {
            Draw3D.reciprocal15[i] = (1 << 15) / i;
        }
        for (int j = 1; j < 2048; j++) {
            Draw3D.reciprocal16[j] = (1 << 16) / j;
        }
        for (int k = 0; k < 2048; k++) {
            Draw3D.sin[k] = (int) (65536D * Math.sin((double) k * 0.0030679614999999999D));
            Draw3D.cos[k] = (int) (65536D * Math.cos((double) k * 0.0030679614999999999D));
        }
    }

    public static void unload() {
        Draw3D.sin = null;
        Draw3D.cos = null;
        Draw3D.lineOffset = null;
        Draw3D.textures = null;
        Draw3D.textureTranslucent = null;
        Draw3D.averageTextureRGB = null;
        Draw3D.texelPool = null;
        Draw3D.activeTexels = null;
        Draw3D.textureCycle = null;
        Draw3D.palette = null;
        Draw3D.texturePalette = null;
    }

    /**
     * Instantiates {@link #lineOffset} and sets {@link #centerX} and {@link #centerY} using the width and height values
     * stored in {@link Draw2D}.
     */
    public static void init2D() {
        Draw3D.lineOffset = new int[Draw2D.height];
        for (int y = 0; y < Draw2D.height; y++) {
            Draw3D.lineOffset[y] = Draw2D.width * y;
        }
        Draw3D.centerX = Draw2D.width / 2;
        Draw3D.centerY = Draw2D.height / 2;
    }

    /**
     * Instantiates {@link #lineOffset} and sets {@link #centerX} and {@link #centerY} using the width and height values
     * provided.
     *
     * @param width  the width.
     * @param height the height.
     */
    public static void init3D(int width, int height) {
        Draw3D.lineOffset = new int[height];
        for (int y = 0; y < height; y++) {
            Draw3D.lineOffset[y] = width * y;
        }
        Draw3D.centerX = width / 2;
        Draw3D.centerY = height / 2;
    }

    /**
     * Nullifies the texel pool. {@link #initPool(int)} must be called again if textured triangles are drawn.
     */
    public static void clearTexels() {
        Draw3D.texelPool = null;
        for (int j = 0; j < 50; j++) {
            Draw3D.activeTexels[j] = null;
        }
    }

    /**
     * Initializes the texel pool.
     *
     * @param poolSize the initial pool size.
     */
    public static void initPool(int poolSize) {
        if (Draw3D.texelPool == null) {
            Draw3D.poolSize = poolSize;
            if (Draw3D.lowmem) {
                Draw3D.texelPool = new int[Draw3D.poolSize][64 * 64 * 4];
            } else {
                Draw3D.texelPool = new int[Draw3D.poolSize][128 * 128 * 4];
            }
            for (int k = 0; k < 50; k++) {
                Draw3D.activeTexels[k] = null;
            }
        }
    }

    public static void unpackTextures(FileArchive archive) {
        Draw3D.textureCount = 0;

        for (int textureID = 0; textureID < 50; textureID++) {
            try {
                Draw3D.textures[textureID] = new Image8(archive, String.valueOf(textureID), 0);

                if (Draw3D.lowmem && (Draw3D.textures[textureID].cropW == 128)) {
                    Draw3D.textures[textureID].shrink();
                } else {
                    Draw3D.textures[textureID].crop();
                }
                Draw3D.textureCount++;
            } catch (Exception ignored) {
            }
        }
    }

    public static int getAverageTextureRGB(int textureID) {
        if (Draw3D.averageTextureRGB[textureID] != 0) {
            return Draw3D.averageTextureRGB[textureID];
        }
        int r = 0;
        int g = 0;
        int b = 0;
        int length = Draw3D.texturePalette[textureID].length;
        for (int i = 0; i < length; i++) {
            r += (Draw3D.texturePalette[textureID][i] >> 16) & 0xff;
            g += (Draw3D.texturePalette[textureID][i] >> 8) & 0xff;
            b += Draw3D.texturePalette[textureID][i] & 0xff;
        }
        int rgb = ((r / length) << 16) + ((g / length) << 8) + (b / length);
        rgb = Draw3D.setGamma(rgb, 1.3999999999999999D);
        if (rgb == 0) {
            rgb = 1;
        }
        Draw3D.averageTextureRGB[textureID] = rgb;
        return rgb;
    }

    /**
     * Pushes the texels of the provided texture id back into the pool. This causes the texture to be regenerated the
     * next time {@link #getTexels(int)} is called for that <code>textureID</code>. This method is actively used for
     * scrolling textures by {@link Game#updateTextures(int)}
     *
     * @param textureID the texture id.
     */
    public static void releaseTexture(int textureID) {
        if (Draw3D.activeTexels[textureID] != null) {
            Draw3D.texelPool[Draw3D.poolSize++] = Draw3D.activeTexels[textureID];
            Draw3D.activeTexels[textureID] = null;
        }
    }

    public static int[] getTexels(int textureID) {
        Draw3D.textureCycle[textureID] = Draw3D.cycle++;

        if (Draw3D.activeTexels[textureID] != null) {
            return Draw3D.activeTexels[textureID];
        }

        int[] texels;

        if (Draw3D.poolSize > 0) {
            texels = Draw3D.texelPool[--Draw3D.poolSize];
            Draw3D.texelPool[Draw3D.poolSize] = null;
        } else {
            int cycle = 0;
            int selected = -1;

            for (int t = 0; t < Draw3D.textureCount; t++) {
                if ((Draw3D.activeTexels[t] != null) && ((Draw3D.textureCycle[t] < cycle) || (selected == -1))) {
                    cycle = Draw3D.textureCycle[t];
                    selected = t;
                }
            }

            texels = Draw3D.activeTexels[selected];
            Draw3D.activeTexels[selected] = null;
        }

        Draw3D.activeTexels[textureID] = texels;
        Image8 texture = Draw3D.textures[textureID];
        int[] palette = Draw3D.texturePalette[textureID];

        if (Draw3D.lowmem) {
            Draw3D.textureTranslucent[textureID] = false;

            for (int i = 0; i < 4096; i++) {
                int rgb = texels[i] = palette[texture.pixels[i]] & 0xf8f8ff;

                if (rgb == 0) {
                    Draw3D.textureTranslucent[textureID] = true;
                }

                texels[4096 + i] = (rgb - (rgb >>> 3)) & 0xf8f8ff;
                texels[8192 + i] = (rgb - (rgb >>> 2)) & 0xf8f8ff;
                texels[12288 + i] = (rgb - (rgb >>> 2) - (rgb >>> 3)) & 0xf8f8ff;
            }
        } else {
            // scale 64x64 textures up to 128x128
            if (texture.width == 64) {
                for (int y = 0; y < 128; y++) {
                    for (int x = 0; x < 128; x++) {
                        texels[x + (y << 7)] = palette[texture.pixels[(x >> 1) + ((y >> 1) << 6)]];
                    }
                }
            } else {
                for (int i = 0; i < 16384; i++) {
                    texels[i] = palette[texture.pixels[i]];
                }
            }

            Draw3D.textureTranslucent[textureID] = false;

            for (int i = 0; i < 16384; i++) {
                texels[i] &= 0xf8f8ff;

                int rgb = texels[i];

                if (rgb == 0) {
                    Draw3D.textureTranslucent[textureID] = true;
                }

                texels[16384 + i] = (rgb - (rgb >>> 3)) & 0xf8f8ff;
                texels[32768 + i] = (rgb - (rgb >>> 2)) & 0xf8f8ff;
                texels[49152 + i] = (rgb - (rgb >>> 2) - (rgb >>> 3)) & 0xf8f8ff;
            }
        }
        return texels;
    }

    /**
     * Sets the brightness.
     *
     * @param brightness the brightness.
     */
    public static void setBrightness(double brightness) {
        brightness += (Math.random() * 0.03) - 0.015;

        int offset = 0;
        for (int y = 0; y < 512; y++) {
            double hue = ((double) (y / 8) / 64D) + 0.0078125D;
            double saturation = ((double) (y & 7) / 8D) + 0.0625D;

            for (int x = 0; x < 128; x++) {
                double lightness = (double) x / 128D;
                double r = lightness;
                double g = lightness;
                double b = lightness;

                if (saturation != 0.0D) {
                    double q;

                    if (lightness < 0.5D) {
                        q = lightness * (1.0D + saturation);
                    } else {
                        q = (lightness + saturation) - (lightness * saturation);
                    }

                    double p = (2D * lightness) - q;
                    double t = hue + (1.0 / 3.0);

                    if (t > 1.0D) {
                        t--;
                    }

                    double d11 = hue - (1.0 / 3.0);

                    if (d11 < 0.0D) {
                        d11++;
                    }

                    if ((6D * t) < 1.0D) {
                        r = p + ((q - p) * 6D * t);
                    } else if ((2D * t) < 1.0D) {
                        r = q;
                    } else if ((3D * t) < 2D) {
                        r = p + ((q - p) * ((2.0 / 3.0) - t) * 6D);
                    } else {
                        r = p;
                    }

                    if ((6D * hue) < 1.0D) {
                        g = p + ((q - p) * 6D * hue);
                    } else if ((2D * hue) < 1.0D) {
                        g = q;
                    } else if ((3D * hue) < 2D) {
                        g = p + ((q - p) * ((2.0 / 3.0) - hue) * 6D);
                    } else {
                        g = p;
                    }

                    if ((6D * d11) < 1.0D) {
                        b = p + ((q - p) * 6D * d11);
                    } else if ((2D * d11) < 1.0D) {
                        b = q;
                    } else if ((3D * d11) < 2D) {
                        b = p + ((q - p) * ((2.0 / 3.0) - d11) * 6D);
                    } else {
                        b = p;
                    }
                }

                int intR = (int) (r * 256D);
                int intG = (int) (g * 256D);
                int intB = (int) (b * 256D);
                int rgb = (intR << 16) + (intG << 8) + intB;

                rgb = Draw3D.setGamma(rgb, brightness);

                if (rgb == 0) {
                    rgb = 1;
                }

                Draw3D.palette[offset++] = rgb;
            }
        }

        for (int textureID = 0; textureID < 50; textureID++) {
            if (Draw3D.textures[textureID] == null) {
                continue;
            }

            int[] palette = Draw3D.textures[textureID].palette;
            Draw3D.texturePalette[textureID] = new int[palette.length];

            for (int i = 0; i < palette.length; i++) {
                Draw3D.texturePalette[textureID][i] = Draw3D.setGamma(palette[i], brightness);

                if (((Draw3D.texturePalette[textureID][i] & 0xf8f8ff) == 0) && (i != 0)) {
                    Draw3D.texturePalette[textureID][i] = 1;
                }
            }
        }

        for (int textureID = 0; textureID < 50; textureID++) {
            Draw3D.releaseTexture(textureID);
        }
    }

    /**
     * Returns the <code>rgb</code> with each component raised to the power of <code>gamma</code>
     *
     * @param rgb   the input rgb.
     * @param gamma the gamma.
     * @return the result.
     */
    private static int setGamma(int rgb, double gamma) {
        double r = (double) (rgb >> 16) / 256D;
        double g = (double) ((rgb >> 8) & 0xff) / 256D;
        double b = (double) (rgb & 0xff) / 256D;
        r = Math.pow(r, gamma);
        g = Math.pow(g, gamma);
        b = Math.pow(b, gamma);
        int intR = (int) (r * 256D);
        int intG = (int) (g * 256D);
        int intB = (int) (b * 256D);
        return (intR << 16) + (intG << 8) + intB;
    }

    /**
     * Fills a gouraud triangle using {@link #palette} colors.
     *
     * @param yA     the Y for corner A.
     * @param yB     the Y for corner B.
     * @param yC     the Y for corner C.
     * @param xA     the X for corner A.
     * @param xB     the X for corner B.
     * @param xC     the X for corner C.
     * @param colorA the color for corner A.
     * @param colorB the color for corner B.
     * @param colorC the color for corner C.
     */
    public static void fillGouraudTriangle(int yA, int yB, int yC, int xA, int xB, int xC, int colorA, int colorB, int colorC) {
        int xStepAB = 0;
        int xStepBC = 0;
        int xStepAC = 0;

        int colorStepAB = 0;
        int colorStepBC = 0;
        int colorStepAC = 0;

        if (yB != yA) {
            xStepAB = ((xB - xA) << 16) / (yB - yA);
            colorStepAB = ((colorB - colorA) << 15) / (yB - yA);
        }

        if (yC != yB) {
            xStepBC = ((xC - xB) << 16) / (yC - yB);
            colorStepBC = ((colorC - colorB) << 15) / (yC - yB);
        }

        if (yC != yA) {
            xStepAC = ((xA - xC) << 16) / (yA - yC);
            colorStepAC = ((colorA - colorC) << 15) / (yA - yC);
        }

        if ((yA <= yB) && (yA <= yC)) {
            if (yA >= Draw2D.bottom) {
                return;
            }
            if (yB > Draw2D.bottom) {
                yB = Draw2D.bottom;
            }
            if (yC > Draw2D.bottom) {
                yC = Draw2D.bottom;
            }
            if (yB < yC) {
                xC = (xA <<= 16);
                colorC = (colorA <<= 15);
                if (yA < 0) {
                    xC -= xStepAC * yA;
                    xA -= xStepAB * yA;
                    colorC -= colorStepAC * yA;
                    colorA -= colorStepAB * yA;
                    yA = 0;
                }
                xB <<= 16;
                colorB <<= 15;
                if (yB < 0) {
                    xB -= xStepBC * yB;
                    colorB -= colorStepBC * yB;
                    yB = 0;
                }
                if (((yA != yB) && (xStepAC < xStepAB)) || ((yA == yB) && (xStepAC > xStepBC))) {
                    yC -= yB;
                    yB -= yA;
                    for (yA = Draw3D.lineOffset[yA]; --yB >= 0; yA += Draw2D.width) {
                        Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
                        xC += xStepAC;
                        xA += xStepAB;
                        colorC += colorStepAC;
                        colorA += colorStepAB;
                    }
                    while (--yC >= 0) {
                        Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
                        xC += xStepAC;
                        xB += xStepBC;
                        colorC += colorStepAC;
                        colorB += colorStepBC;
                        yA += Draw2D.width;
                    }
                    return;
                }
                yC -= yB;
                yB -= yA;
                for (yA = Draw3D.lineOffset[yA]; --yB >= 0; yA += Draw2D.width) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
                    xC += xStepAC;
                    xA += xStepAB;
                    colorC += colorStepAC;
                    colorA += colorStepAB;
                }
                while (--yC >= 0) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
                    xC += xStepAC;
                    xB += xStepBC;
                    colorC += colorStepAC;
                    colorB += colorStepBC;
                    yA += Draw2D.width;
                }
                return;
            }
            xB = (xA <<= 16);
            colorB = (colorA <<= 15);
            if (yA < 0) {
                xB -= xStepAC * yA;
                xA -= xStepAB * yA;
                colorB -= colorStepAC * yA;
                colorA -= colorStepAB * yA;
                yA = 0;
            }
            xC <<= 16;
            colorC <<= 15;
            if (yC < 0) {
                xC -= xStepBC * yC;
                colorC -= colorStepBC * yC;
                yC = 0;
            }
            if (((yA != yC) && (xStepAC < xStepAB)) || ((yA == yC) && (xStepBC > xStepAB))) {
                yB -= yC;
                yC -= yA;
                for (yA = Draw3D.lineOffset[yA]; --yC >= 0; yA += Draw2D.width) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
                    xB += xStepAC;
                    xA += xStepAB;
                    colorB += colorStepAC;
                    colorA += colorStepAB;
                }
                while (--yB >= 0) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
                    xC += xStepBC;
                    xA += xStepAB;
                    colorC += colorStepBC;
                    colorA += colorStepAB;
                    yA += Draw2D.width;
                }
                return;
            }
            yB -= yC;
            yC -= yA;
            for (yA = Draw3D.lineOffset[yA]; --yC >= 0; yA += Draw2D.width) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
                xB += xStepAC;
                xA += xStepAB;
                colorB += colorStepAC;
                colorA += colorStepAB;
            }
            while (--yB >= 0) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
                xC += xStepBC;
                xA += xStepAB;
                colorC += colorStepBC;
                colorA += colorStepAB;
                yA += Draw2D.width;
            }
            return;
        }
        if (yB <= yC) {
            if (yB >= Draw2D.bottom) {
                return;
            }
            if (yC > Draw2D.bottom) {
                yC = Draw2D.bottom;
            }
            if (yA > Draw2D.bottom) {
                yA = Draw2D.bottom;
            }
            if (yC < yA) {
                xA = (xB <<= 16);
                colorA = (colorB <<= 15);
                if (yB < 0) {
                    xA -= xStepAB * yB;
                    xB -= xStepBC * yB;
                    colorA -= colorStepAB * yB;
                    colorB -= colorStepBC * yB;
                    yB = 0;
                }
                xC <<= 16;
                colorC <<= 15;
                if (yC < 0) {
                    xC -= xStepAC * yC;
                    colorC -= colorStepAC * yC;
                    yC = 0;
                }
                if (((yB != yC) && (xStepAB < xStepBC)) || ((yB == yC) && (xStepAB > xStepAC))) {
                    yA -= yC;
                    yC -= yB;
                    for (yB = Draw3D.lineOffset[yB]; --yC >= 0; yB += Draw2D.width) {
                        Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
                        xA += xStepAB;
                        xB += xStepBC;
                        colorA += colorStepAB;
                        colorB += colorStepBC;
                    }
                    while (--yA >= 0) {
                        Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
                        xA += xStepAB;
                        xC += xStepAC;
                        colorA += colorStepAB;
                        colorC += colorStepAC;
                        yB += Draw2D.width;
                    }
                    return;
                }
                yA -= yC;
                yC -= yB;
                for (yB = Draw3D.lineOffset[yB]; --yC >= 0; yB += Draw2D.width) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
                    xA += xStepAB;
                    xB += xStepBC;
                    colorA += colorStepAB;
                    colorB += colorStepBC;
                }
                while (--yA >= 0) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
                    xA += xStepAB;
                    xC += xStepAC;
                    colorA += colorStepAB;
                    colorC += colorStepAC;
                    yB += Draw2D.width;
                }
                return;
            }
            xC = (xB <<= 16);
            colorC = (colorB <<= 15);
            if (yB < 0) {
                xC -= xStepAB * yB;
                xB -= xStepBC * yB;
                colorC -= colorStepAB * yB;
                colorB -= colorStepBC * yB;
                yB = 0;
            }
            xA <<= 16;
            colorA <<= 15;
            if (yA < 0) {
                xA -= xStepAC * yA;
                colorA -= colorStepAC * yA;
                yA = 0;
            }
            if (xStepAB < xStepBC) {
                yC -= yA;
                yA -= yB;
                for (yB = Draw3D.lineOffset[yB]; --yA >= 0; yB += Draw2D.width) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
                    xC += xStepAB;
                    xB += xStepBC;
                    colorC += colorStepAB;
                    colorB += colorStepBC;
                }
                while (--yC >= 0) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
                    xA += xStepAC;
                    xB += xStepBC;
                    colorA += colorStepAC;
                    colorB += colorStepBC;
                    yB += Draw2D.width;
                }
                return;
            }
            yC -= yA;
            yA -= yB;
            for (yB = Draw3D.lineOffset[yB]; --yA >= 0; yB += Draw2D.width) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
                xC += xStepAB;
                xB += xStepBC;
                colorC += colorStepAB;
                colorB += colorStepBC;
            }
            while (--yC >= 0) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
                xA += xStepAC;
                xB += xStepBC;
                colorA += colorStepAC;
                colorB += colorStepBC;
                yB += Draw2D.width;
            }
            return;
        }
        if (yC >= Draw2D.bottom) {
            return;
        }
        if (yA > Draw2D.bottom) {
            yA = Draw2D.bottom;
        }
        if (yB > Draw2D.bottom) {
            yB = Draw2D.bottom;
        }
        if (yA < yB) {
            xB = (xC <<= 16);
            colorB = (colorC <<= 15);
            if (yC < 0) {
                xB -= xStepBC * yC;
                xC -= xStepAC * yC;
                colorB -= colorStepBC * yC;
                colorC -= colorStepAC * yC;
                yC = 0;
            }
            xA <<= 16;
            colorA <<= 15;
            if (yA < 0) {
                xA -= xStepAB * yA;
                colorA -= colorStepAB * yA;
                yA = 0;
            }
            if (xStepBC < xStepAC) {
                yB -= yA;
                yA -= yC;
                for (yC = Draw3D.lineOffset[yC]; --yA >= 0; yC += Draw2D.width) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
                    xB += xStepBC;
                    xC += xStepAC;
                    colorB += colorStepBC;
                    colorC += colorStepAC;
                }
                while (--yB >= 0) {
                    Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
                    xB += xStepBC;
                    xA += xStepAB;
                    colorB += colorStepBC;
                    colorA += colorStepAB;
                    yC += Draw2D.width;
                }
                return;
            }
            yB -= yA;
            yA -= yC;
            for (yC = Draw3D.lineOffset[yC]; --yA >= 0; yC += Draw2D.width) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
                xB += xStepBC;
                xC += xStepAC;
                colorB += colorStepBC;
                colorC += colorStepAC;
            }
            while (--yB >= 0) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
                xB += xStepBC;
                xA += xStepAB;
                colorB += colorStepBC;
                colorA += colorStepAB;
                yC += Draw2D.width;
            }
            return;
        }
        xA = (xC <<= 16);
        colorA = (colorC <<= 15);
        if (yC < 0) {
            xA -= xStepBC * yC;
            xC -= xStepAC * yC;
            colorA -= colorStepBC * yC;
            colorC -= colorStepAC * yC;
            yC = 0;
        }
        xB <<= 16;
        colorB <<= 15;
        if (yB < 0) {
            xB -= xStepAB * yB;
            colorB -= colorStepAB * yB;
            yB = 0;
        }
        if (xStepBC < xStepAC) {
            yA -= yB;
            yB -= yC;
            for (yC = Draw3D.lineOffset[yC]; --yB >= 0; yC += Draw2D.width) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
                xA += xStepBC;
                xC += xStepAC;
                colorA += colorStepBC;
                colorC += colorStepAC;
            }
            while (--yA >= 0) {
                Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
                xB += xStepAB;
                xC += xStepAC;
                colorB += colorStepAB;
                colorC += colorStepAC;
                yC += Draw2D.width;
            }
            return;
        }
        yA -= yB;
        yB -= yC;
        for (yC = Draw3D.lineOffset[yC]; --yB >= 0; yC += Draw2D.width) {
            Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
            xA += xStepBC;
            xC += xStepAC;
            colorA += colorStepBC;
            colorC += colorStepAC;
        }
        while (--yA >= 0) {
            Draw3D.drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
            xB += xStepAB;
            xC += xStepAC;
            colorB += colorStepAB;
            colorC += colorStepAC;
            yC += Draw2D.width;
        }
    }

    /**
     * Draws a gouraud scanline using {@link #palette} colors.
     *
     * @param dst    the destination.
     * @param offset the destination offset.
     * @param x0     the left x.
     * @param x1     the right x.
     * @param color0 the left color index.
     * @param color1 the right color index.
     * @see #fillGouraudTriangle(int, int, int, int, int, int, int, int, int) e
     */
    public static void drawGouraudScanline(int[] dst, int offset, int x0, int x1, int color0, int color1) {
        int rgb;
        int length;

        if (Draw3D.jagged) {
            int colorStep;

            if (Draw3D.clipX) {
                if ((x1 - x0) > 3) {
                    colorStep = (color1 - color0) / (x1 - x0);
                } else {
                    colorStep = 0;
                }

                if (x1 > Draw2D.boundX) {
                    x1 = Draw2D.boundX;
                }

                if (x0 < 0) {
                    color0 -= x0 * colorStep;
                    x0 = 0;
                }

                if (x0 >= x1) {
                    return;
                }

                offset += x0;
                length = (x1 - x0) >> 2;
                colorStep <<= 2;
            } else {
                if (x0 >= x1) {
                    return;
                }

                offset += x0;
                length = (x1 - x0) >> 2;

                if (length > 0) {
                    colorStep = ((color1 - color0) * Draw3D.reciprocal15[length]) >> 15;
                } else {
                    colorStep = 0;
                }
            }

            if (Draw3D.alpha == 0) {
                while (--length >= 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    color0 += colorStep;
                    dst[offset++] = rgb;
                    dst[offset++] = rgb;
                    dst[offset++] = rgb;
                    dst[offset++] = rgb;
                }

                length = (x1 - x0) & 3;

                if (length > 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    do {
                        dst[offset++] = rgb;
                    } while (--length > 0);
                    return;
                }
            } else {
                int alpha = Draw3D.alpha;
                int invAlpha = 256 - Draw3D.alpha;

                while (--length >= 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    color0 += colorStep;
                    rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);
                    dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
                    dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
                    dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
                    dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
                }

                length = (x1 - x0) & 3;

                if (length > 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);
                    do {
                        dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
                    } while (--length > 0);
                }
            }
            return;
        }

        if (x0 >= x1) {
            return;
        }

        int colorStep = (color1 - color0) / (x1 - x0);

        if (Draw3D.clipX) {
            if (x1 > Draw2D.boundX) {
                x1 = Draw2D.boundX;
            }
            if (x0 < 0) {
                color0 -= x0 * colorStep;
                x0 = 0;
            }
            if (x0 >= x1) {
                return;
            }
        }

        offset += x0;
        length = x1 - x0;

        if (Draw3D.alpha == 0) {
            do {
                dst[offset++] = Draw3D.palette[color0 >> 8];
                color0 += colorStep;
            } while (--length > 0);
            return;
        }

        int alpha = Draw3D.alpha;
        int invAlpha = 256 - Draw3D.alpha;

        do {
            rgb = Draw3D.palette[color0 >> 8];
            color0 += colorStep;
            rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);
            // If you want to fix the lines in transparent models like ghostly or bank booths, change dst[offset++] to
            // dst[offset] and on the next line below put offset++
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
        } while (--length > 0);
    }

    public static void fillTriangle(int y0, int y1, int y2, int x0, int x1, int x2, int color) {
        int xStepAB = 0;
        if (y1 != y0) {
            xStepAB = ((x1 - x0) << 16) / (y1 - y0);
        }
        int xStepBC = 0;
        if (y2 != y1) {
            xStepBC = ((x2 - x1) << 16) / (y2 - y1);
        }
        int xStepAC = 0;
        if (y2 != y0) {
            xStepAC = ((x0 - x2) << 16) / (y0 - y2);
        }
        if ((y0 <= y1) && (y0 <= y2)) {
            if (y0 >= Draw2D.bottom) {
                return;
            }
            if (y1 > Draw2D.bottom) {
                y1 = Draw2D.bottom;
            }
            if (y2 > Draw2D.bottom) {
                y2 = Draw2D.bottom;
            }
            if (y1 < y2) {
                x2 = (x0 <<= 16);
                if (y0 < 0) {
                    x2 -= xStepAC * y0;
                    x0 -= xStepAB * y0;
                    y0 = 0;
                }
                x1 <<= 16;
                if (y1 < 0) {
                    x1 -= xStepBC * y1;
                    y1 = 0;
                }
                if (((y0 != y1) && (xStepAC < xStepAB)) || ((y0 == y1) && (xStepAC > xStepBC))) {
                    y2 -= y1;
                    y1 -= y0;
                    for (y0 = Draw3D.lineOffset[y0]; --y1 >= 0; y0 += Draw2D.width) {
                        Draw3D.drawScanline(Draw2D.pixels, y0, color, x2 >> 16, x0 >> 16);
                        x2 += xStepAC;
                        x0 += xStepAB;
                    }
                    while (--y2 >= 0) {
                        Draw3D.drawScanline(Draw2D.pixels, y0, color, x2 >> 16, x1 >> 16);
                        x2 += xStepAC;
                        x1 += xStepBC;
                        y0 += Draw2D.width;
                    }
                    return;
                }
                y2 -= y1;
                y1 -= y0;
                for (y0 = Draw3D.lineOffset[y0]; --y1 >= 0; y0 += Draw2D.width) {
                    Draw3D.drawScanline(Draw2D.pixels, y0, color, x0 >> 16, x2 >> 16);
                    x2 += xStepAC;
                    x0 += xStepAB;
                }
                while (--y2 >= 0) {
                    Draw3D.drawScanline(Draw2D.pixels, y0, color, x1 >> 16, x2 >> 16);
                    x2 += xStepAC;
                    x1 += xStepBC;
                    y0 += Draw2D.width;
                }
                return;
            }
            x1 = (x0 <<= 16);
            if (y0 < 0) {
                x1 -= xStepAC * y0;
                x0 -= xStepAB * y0;
                y0 = 0;
            }
            x2 <<= 16;
            if (y2 < 0) {
                x2 -= xStepBC * y2;
                y2 = 0;
            }
            if (((y0 != y2) && (xStepAC < xStepAB)) || ((y0 == y2) && (xStepBC > xStepAB))) {
                y1 -= y2;
                y2 -= y0;
                for (y0 = Draw3D.lineOffset[y0]; --y2 >= 0; y0 += Draw2D.width) {
                    Draw3D.drawScanline(Draw2D.pixels, y0, color, x1 >> 16, x0 >> 16);
                    x1 += xStepAC;
                    x0 += xStepAB;
                }
                while (--y1 >= 0) {
                    Draw3D.drawScanline(Draw2D.pixels, y0, color, x2 >> 16, x0 >> 16);
                    x2 += xStepBC;
                    x0 += xStepAB;
                    y0 += Draw2D.width;
                }
                return;
            }
            y1 -= y2;
            y2 -= y0;
            for (y0 = Draw3D.lineOffset[y0]; --y2 >= 0; y0 += Draw2D.width) {
                Draw3D.drawScanline(Draw2D.pixels, y0, color, x0 >> 16, x1 >> 16);
                x1 += xStepAC;
                x0 += xStepAB;
            }
            while (--y1 >= 0) {
                Draw3D.drawScanline(Draw2D.pixels, y0, color, x0 >> 16, x2 >> 16);
                x2 += xStepBC;
                x0 += xStepAB;
                y0 += Draw2D.width;
            }
            return;
        }
        if (y1 <= y2) {
            if (y1 >= Draw2D.bottom) {
                return;
            }
            if (y2 > Draw2D.bottom) {
                y2 = Draw2D.bottom;
            }
            if (y0 > Draw2D.bottom) {
                y0 = Draw2D.bottom;
            }
            if (y2 < y0) {
                x0 = (x1 <<= 16);
                if (y1 < 0) {
                    x0 -= xStepAB * y1;
                    x1 -= xStepBC * y1;
                    y1 = 0;
                }
                x2 <<= 16;
                if (y2 < 0) {
                    x2 -= xStepAC * y2;
                    y2 = 0;
                }
                if (((y1 != y2) && (xStepAB < xStepBC)) || ((y1 == y2) && (xStepAB > xStepAC))) {
                    y0 -= y2;
                    y2 -= y1;
                    for (y1 = Draw3D.lineOffset[y1]; --y2 >= 0; y1 += Draw2D.width) {
                        Draw3D.drawScanline(Draw2D.pixels, y1, color, x0 >> 16, x1 >> 16);
                        x0 += xStepAB;
                        x1 += xStepBC;
                    }
                    while (--y0 >= 0) {
                        Draw3D.drawScanline(Draw2D.pixels, y1, color, x0 >> 16, x2 >> 16);
                        x0 += xStepAB;
                        x2 += xStepAC;
                        y1 += Draw2D.width;
                    }
                    return;
                }
                y0 -= y2;
                y2 -= y1;
                for (y1 = Draw3D.lineOffset[y1]; --y2 >= 0; y1 += Draw2D.width) {
                    Draw3D.drawScanline(Draw2D.pixels, y1, color, x1 >> 16, x0 >> 16);
                    x0 += xStepAB;
                    x1 += xStepBC;
                }
                while (--y0 >= 0) {
                    Draw3D.drawScanline(Draw2D.pixels, y1, color, x2 >> 16, x0 >> 16);
                    x0 += xStepAB;
                    x2 += xStepAC;
                    y1 += Draw2D.width;
                }
                return;
            }
            x2 = (x1 <<= 16);
            if (y1 < 0) {
                x2 -= xStepAB * y1;
                x1 -= xStepBC * y1;
                y1 = 0;
            }
            x0 <<= 16;
            if (y0 < 0) {
                x0 -= xStepAC * y0;
                y0 = 0;
            }
            if (xStepAB < xStepBC) {
                y2 -= y0;
                y0 -= y1;
                for (y1 = Draw3D.lineOffset[y1]; --y0 >= 0; y1 += Draw2D.width) {
                    Draw3D.drawScanline(Draw2D.pixels, y1, color, x2 >> 16, x1 >> 16);
                    x2 += xStepAB;
                    x1 += xStepBC;
                }
                while (--y2 >= 0) {
                    Draw3D.drawScanline(Draw2D.pixels, y1, color, x0 >> 16, x1 >> 16);
                    x0 += xStepAC;
                    x1 += xStepBC;
                    y1 += Draw2D.width;
                }
                return;
            }
            y2 -= y0;
            y0 -= y1;
            for (y1 = Draw3D.lineOffset[y1]; --y0 >= 0; y1 += Draw2D.width) {
                Draw3D.drawScanline(Draw2D.pixels, y1, color, x1 >> 16, x2 >> 16);
                x2 += xStepAB;
                x1 += xStepBC;
            }
            while (--y2 >= 0) {
                Draw3D.drawScanline(Draw2D.pixels, y1, color, x1 >> 16, x0 >> 16);
                x0 += xStepAC;
                x1 += xStepBC;
                y1 += Draw2D.width;
            }
            return;
        }
        if (y2 >= Draw2D.bottom) {
            return;
        }
        if (y0 > Draw2D.bottom) {
            y0 = Draw2D.bottom;
        }
        if (y1 > Draw2D.bottom) {
            y1 = Draw2D.bottom;
        }
        if (y0 < y1) {
            x1 = (x2 <<= 16);
            if (y2 < 0) {
                x1 -= xStepBC * y2;
                x2 -= xStepAC * y2;
                y2 = 0;
            }
            x0 <<= 16;
            if (y0 < 0) {
                x0 -= xStepAB * y0;
                y0 = 0;
            }
            if (xStepBC < xStepAC) {
                y1 -= y0;
                y0 -= y2;
                for (y2 = Draw3D.lineOffset[y2]; --y0 >= 0; y2 += Draw2D.width) {
                    Draw3D.drawScanline(Draw2D.pixels, y2, color, x1 >> 16, x2 >> 16);
                    x1 += xStepBC;
                    x2 += xStepAC;
                }
                while (--y1 >= 0) {
                    Draw3D.drawScanline(Draw2D.pixels, y2, color, x1 >> 16, x0 >> 16);
                    x1 += xStepBC;
                    x0 += xStepAB;
                    y2 += Draw2D.width;
                }
                return;
            }
            y1 -= y0;
            y0 -= y2;
            for (y2 = Draw3D.lineOffset[y2]; --y0 >= 0; y2 += Draw2D.width) {
                Draw3D.drawScanline(Draw2D.pixels, y2, color, x2 >> 16, x1 >> 16);
                x1 += xStepBC;
                x2 += xStepAC;
            }
            while (--y1 >= 0) {
                Draw3D.drawScanline(Draw2D.pixels, y2, color, x0 >> 16, x1 >> 16);
                x1 += xStepBC;
                x0 += xStepAB;
                y2 += Draw2D.width;
            }
            return;
        }
        x0 = (x2 <<= 16);
        if (y2 < 0) {
            x0 -= xStepBC * y2;
            x2 -= xStepAC * y2;
            y2 = 0;
        }
        x1 <<= 16;
        if (y1 < 0) {
            x1 -= xStepAB * y1;
            y1 = 0;
        }
        if (xStepBC < xStepAC) {
            y0 -= y1;
            y1 -= y2;
            for (y2 = Draw3D.lineOffset[y2]; --y1 >= 0; y2 += Draw2D.width) {
                Draw3D.drawScanline(Draw2D.pixels, y2, color, x0 >> 16, x2 >> 16);
                x0 += xStepBC;
                x2 += xStepAC;
            }
            while (--y0 >= 0) {
                Draw3D.drawScanline(Draw2D.pixels, y2, color, x1 >> 16, x2 >> 16);
                x1 += xStepAB;
                x2 += xStepAC;
                y2 += Draw2D.width;
            }
            return;
        }
        y0 -= y1;
        y1 -= y2;
        for (y2 = Draw3D.lineOffset[y2]; --y1 >= 0; y2 += Draw2D.width) {
            Draw3D.drawScanline(Draw2D.pixels, y2, color, x2 >> 16, x0 >> 16);
            x0 += xStepBC;
            x2 += xStepAC;
        }
        while (--y0 >= 0) {
            Draw3D.drawScanline(Draw2D.pixels, y2, color, x2 >> 16, x1 >> 16);
            x1 += xStepAB;
            x2 += xStepAC;
            y2 += Draw2D.width;
        }
    }

    public static void drawScanline(int[] dst, int offset, int rgb, int x0, int x1) {
        if (Draw3D.clipX) {
            if (x1 > Draw2D.boundX) {
                x1 = Draw2D.boundX;
            }
            if (x0 < 0) {
                x0 = 0;
            }
        }

        if (x0 >= x1) {
            return;
        }

        offset += x0;
        int length = (x1 - x0) >> 2;

        if (Draw3D.alpha == 0) {
            while (--length >= 0) {
                dst[offset++] = rgb;
                dst[offset++] = rgb;
                dst[offset++] = rgb;
                dst[offset++] = rgb;
            }
            for (length = (x1 - x0) & 3; --length >= 0; ) {
                dst[offset++] = rgb;
            }
            return;
        }

        int alpha = Draw3D.alpha;
        int invAlpha = 256 - Draw3D.alpha;

        rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);

        while (--length >= 0) {
            // to fix lines in transparent things: change index operand to 'offset' and add 'offset++' below each line
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
        }

        for (length = (x1 - x0) & 3; --length >= 0; ) {
            dst[offset++] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
        }
    }

    /**
     * Long story short, this defines a plane in view space and traverses it to determine uv values. It's an impl of
     * this algorithm: <a href="https://www.gamers.org/dEngine/rsc/pcgpe-1.0/texture.txt">texture.txt</a>
     */
    public static void fillTexturedTriangle(int yA, int yB, int yC, int xA, int xB, int xC, int shadeA, int shadeB, int shadeC, int txA, int txB, int txC, int tyA, int tyB, int tyC, int tzA, int tzB, int tzC, int texture) {
        int[] texels = Draw3D.getTexels(texture);
        Draw3D.opaque = !Draw3D.textureTranslucent[texture];

        int originX = txA;
        int originY = tyA;
        int originZ = tzA;

        int verticalX = originX - txB;
        int verticalY = originY - tyB;
        int verticalZ = originZ - tzB;

        int horizontalX = txC - originX;
        int horizontalY = tyC - originY;
        int horizontalZ = tzC - originZ;

        // ! It's important to know the document referenced above assumes the following coordinate system:
        // +X = Right
        // +Y = Forward
        // +Z = Up

        // RS2 coordinate system is as follows:
        // +X = Right
        // +Y = Down
        // +Z = Forward

        // Which means we must swap Y and Z for our code to coincide.

        // The reason I called horizontals 'stride' and vertical 'step' is because the drawTexturedScanline is unrolled
        // and does 8 pixels per 'stride' as an optimization. If you were to roll the loops in drawTexturedScanline then
        // you can name these StepHorizontal and change the bitshift to << 5 like its vertical sibling.

        // (a << 3) is the same as (a * 8)

        int u = ((horizontalX * originY) - (horizontalY * originX)) << 14;
        int uStrideHorizontal = ((horizontalY * originZ) - (horizontalZ * originY)) << 8;
        int uStepVertical = ((horizontalZ * originX) - (horizontalX * originZ)) << 5;

        int v = ((verticalX * originY) - (verticalY * originX)) << 14;
        int vStrideHorizontal = ((verticalY * originZ) - (verticalZ * originY)) << 8;
        int vStepVertical = ((verticalZ * originX) - (verticalX * originZ)) << 5;

        int w = ((verticalY * horizontalX) - (verticalX * horizontalY)) << 14;
        int wStrideHorizontal = ((verticalZ * horizontalY) - (verticalY * horizontalZ)) << 8;
        int wStepVertical = ((verticalX * horizontalZ) - (verticalZ * horizontalX)) << 5;

        int xStepAB = 0;
        int xStepBC = 0;
        int xStepAC = 0;

        int shadeStepAB = 0;
        int shadeStepBC = 0;
        int shadeStepAC = 0;

        // Simplified/rolled methods here:
        // https://gist.githubusercontent.com/thedaneeffect/557750c7d4b6138c539b5e3e9d934946/raw/c5c119bf3b3a330066f9264668ba706c6f837728/triangular.java

        if (yB != yA) {
            xStepAB = ((xB - xA) << 16) / (yB - yA);
            shadeStepAB = ((shadeB - shadeA) << 16) / (yB - yA);
        }

        if (yC != yB) {
            xStepBC = ((xC - xB) << 16) / (yC - yB);
            shadeStepBC = ((shadeC - shadeB) << 16) / (yC - yB);
        }

        if (yC != yA) {
            xStepAC = ((xA - xC) << 16) / (yA - yC);
            shadeStepAC = ((shadeA - shadeC) << 16) / (yA - yC);
        }

        if ((yA <= yB) && (yA <= yC)) {
            if (yA >= Draw2D.bottom) {
                return;
            }
            if (yB > Draw2D.bottom) {
                yB = Draw2D.bottom;
            }
            if (yC > Draw2D.bottom) {
                yC = Draw2D.bottom;
            }
            if (yB < yC) {
                xC = (xA <<= 16);
                shadeC = (shadeA <<= 16);
                if (yA < 0) {
                    xC -= xStepAC * yA;
                    xA -= xStepAB * yA;
                    shadeC -= shadeStepAC * yA;
                    shadeA -= shadeStepAB * yA;
                    yA = 0;
                }
                xB <<= 16;
                shadeB <<= 16;
                if (yB < 0) {
                    xB -= xStepBC * yB;
                    shadeB -= shadeStepBC * yB;
                    yB = 0;
                }
                int dy = yA - Draw3D.centerY;
                u += uStepVertical * dy;
                v += vStepVertical * dy;
                w += wStepVertical * dy;
                if (((yA != yB) && (xStepAC < xStepAB)) || ((yA == yB) && (xStepAC > xStepBC))) {
                    yC -= yB;
                    yB -= yA;
                    yA = Draw3D.lineOffset[yA];
                    while (--yB >= 0) {
                        Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xA >> 16, shadeC >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                        xC += xStepAC;
                        xA += xStepAB;
                        shadeC += shadeStepAC;
                        shadeA += shadeStepAB;
                        yA += Draw2D.width;
                        u += uStepVertical;
                        v += vStepVertical;
                        w += wStepVertical;
                    }
                    while (--yC >= 0) {
                        Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xB >> 16, shadeC >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                        xC += xStepAC;
                        xB += xStepBC;
                        shadeC += shadeStepAC;
                        shadeB += shadeStepBC;
                        yA += Draw2D.width;
                        u += uStepVertical;
                        v += vStepVertical;
                        w += wStepVertical;
                    }
                    return;
                }
                yC -= yB;
                yB -= yA;
                yA = Draw3D.lineOffset[yA];
                while (--yB >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xC >> 16, shadeA >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xC += xStepAC;
                    xA += xStepAB;
                    shadeC += shadeStepAC;
                    shadeA += shadeStepAB;
                    yA += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                while (--yC >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xB >> 16, xC >> 16, shadeB >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xC += xStepAC;
                    xB += xStepBC;
                    shadeC += shadeStepAC;
                    shadeB += shadeStepBC;
                    yA += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                return;
            }
            xB = (xA <<= 16);
            shadeB = (shadeA <<= 16);
            if (yA < 0) {
                xB -= xStepAC * yA;
                xA -= xStepAB * yA;
                shadeB -= shadeStepAC * yA;
                shadeA -= shadeStepAB * yA;
                yA = 0;
            }
            xC <<= 16;
            shadeC <<= 16;
            if (yC < 0) {
                xC -= xStepBC * yC;
                shadeC -= shadeStepBC * yC;
                yC = 0;
            }
            int dy = yA - Draw3D.centerY;
            u += uStepVertical * dy;
            v += vStepVertical * dy;
            w += wStepVertical * dy;
            if (((yA != yC) && (xStepAC < xStepAB)) || ((yA == yC) && (xStepBC > xStepAB))) {
                yB -= yC;
                yC -= yA;
                yA = Draw3D.lineOffset[yA];
                while (--yC >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xB >> 16, xA >> 16, shadeB >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xB += xStepAC;
                    xA += xStepAB;
                    shadeB += shadeStepAC;
                    shadeA += shadeStepAB;
                    yA += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                while (--yB >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xA >> 16, shadeC >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xC += xStepBC;
                    xA += xStepAB;
                    shadeC += shadeStepBC;
                    shadeA += shadeStepAB;
                    yA += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                return;
            }
            yB -= yC;
            yC -= yA;
            yA = Draw3D.lineOffset[yA];
            while (--yC >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xB >> 16, shadeA >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xB += xStepAC;
                xA += xStepAB;
                shadeB += shadeStepAC;
                shadeA += shadeStepAB;
                yA += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            while (--yB >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xC >> 16, shadeA >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xC += xStepBC;
                xA += xStepAB;
                shadeC += shadeStepBC;
                shadeA += shadeStepAB;
                yA += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            return;
        }
        if (yB <= yC) {
            if (yB >= Draw2D.bottom) {
                return;
            }
            if (yC > Draw2D.bottom) {
                yC = Draw2D.bottom;
            }
            if (yA > Draw2D.bottom) {
                yA = Draw2D.bottom;
            }
            if (yC < yA) {
                xA = (xB <<= 16);
                shadeA = (shadeB <<= 16);
                if (yB < 0) {
                    xA -= xStepAB * yB;
                    xB -= xStepBC * yB;
                    shadeA -= shadeStepAB * yB;
                    shadeB -= shadeStepBC * yB;
                    yB = 0;
                }
                xC <<= 16;
                shadeC <<= 16;
                if (yC < 0) {
                    xC -= xStepAC * yC;
                    shadeC -= shadeStepAC * yC;
                    yC = 0;
                }
                int dy = yB - Draw3D.centerY;
                u += uStepVertical * dy;
                v += vStepVertical * dy;
                w += wStepVertical * dy;
                if (((yB != yC) && (xStepAB < xStepBC)) || ((yB == yC) && (xStepAB > xStepAC))) {
                    yA -= yC;
                    yC -= yB;
                    yB = Draw3D.lineOffset[yB];
                    while (--yC >= 0) {
                        Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xB >> 16, shadeA >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                        xA += xStepAB;
                        xB += xStepBC;
                        shadeA += shadeStepAB;
                        shadeB += shadeStepBC;
                        yB += Draw2D.width;
                        u += uStepVertical;
                        v += vStepVertical;
                        w += wStepVertical;
                    }
                    while (--yA >= 0) {
                        Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xC >> 16, shadeA >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                        xA += xStepAB;
                        xC += xStepAC;
                        shadeA += shadeStepAB;
                        shadeC += shadeStepAC;
                        yB += Draw2D.width;
                        u += uStepVertical;
                        v += vStepVertical;
                        w += wStepVertical;
                    }
                    return;
                }
                yA -= yC;
                yC -= yB;
                yB = Draw3D.lineOffset[yB];
                while (--yC >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xA >> 16, shadeB >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xA += xStepAB;
                    xB += xStepBC;
                    shadeA += shadeStepAB;
                    shadeB += shadeStepBC;
                    yB += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                while (--yA >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xC >> 16, xA >> 16, shadeC >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xA += xStepAB;
                    xC += xStepAC;
                    shadeA += shadeStepAB;
                    shadeC += shadeStepAC;
                    yB += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                return;
            }
            xC = (xB <<= 16);
            shadeC = (shadeB <<= 16);
            if (yB < 0) {
                xC -= xStepAB * yB;
                xB -= xStepBC * yB;
                shadeC -= shadeStepAB * yB;
                shadeB -= shadeStepBC * yB;
                yB = 0;
            }
            xA <<= 16;
            shadeA <<= 16;
            if (yA < 0) {
                xA -= xStepAC * yA;
                shadeA -= shadeStepAC * yA;
                yA = 0;
            }
            int dy = yB - Draw3D.centerY;
            u += uStepVertical * dy;
            v += vStepVertical * dy;
            w += wStepVertical * dy;
            if (xStepAB < xStepBC) {
                yC -= yA;
                yA -= yB;
                yB = Draw3D.lineOffset[yB];
                while (--yA >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xC >> 16, xB >> 16, shadeC >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xC += xStepAB;
                    xB += xStepBC;
                    shadeC += shadeStepAB;
                    shadeB += shadeStepBC;
                    yB += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                while (--yC >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xB >> 16, shadeA >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xA += xStepAC;
                    xB += xStepBC;
                    shadeA += shadeStepAC;
                    shadeB += shadeStepBC;
                    yB += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                return;
            }
            yC -= yA;
            yA -= yB;
            yB = Draw3D.lineOffset[yB];
            while (--yA >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xC >> 16, shadeB >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xC += xStepAB;
                xB += xStepBC;
                shadeC += shadeStepAB;
                shadeB += shadeStepBC;
                yB += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            while (--yC >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xA >> 16, shadeB >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xA += xStepAC;
                xB += xStepBC;
                shadeA += shadeStepAC;
                shadeB += shadeStepBC;
                yB += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            return;
        }
        if (yC >= Draw2D.bottom) {
            return;
        }
        if (yA > Draw2D.bottom) {
            yA = Draw2D.bottom;
        }
        if (yB > Draw2D.bottom) {
            yB = Draw2D.bottom;
        }
        if (yA < yB) {
            xB = (xC <<= 16);
            shadeB = (shadeC <<= 16);
            if (yC < 0) {
                xB -= xStepBC * yC;
                xC -= xStepAC * yC;
                shadeB -= shadeStepBC * yC;
                shadeC -= shadeStepAC * yC;
                yC = 0;
            }
            xA <<= 16;
            shadeA <<= 16;
            if (yA < 0) {
                xA -= xStepAB * yA;
                shadeA -= shadeStepAB * yA;
                yA = 0;
            }
            int dy = yC - Draw3D.centerY;
            u += uStepVertical * dy;
            v += vStepVertical * dy;
            w += wStepVertical * dy;
            if (xStepBC < xStepAC) {
                yB -= yA;
                yA -= yC;
                yC = Draw3D.lineOffset[yC];
                while (--yA >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xC >> 16, shadeB >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xB += xStepBC;
                    xC += xStepAC;
                    shadeB += shadeStepBC;
                    shadeC += shadeStepAC;
                    yC += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                while (--yB >= 0) {
                    Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xA >> 16, shadeB >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                    xB += xStepBC;
                    xA += xStepAB;
                    shadeB += shadeStepBC;
                    shadeA += shadeStepAB;
                    yC += Draw2D.width;
                    u += uStepVertical;
                    v += vStepVertical;
                    w += wStepVertical;
                }
                return;
            }
            yB -= yA;
            yA -= yC;
            yC = Draw3D.lineOffset[yC];
            while (--yA >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xB >> 16, shadeC >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xB += xStepBC;
                xC += xStepAC;
                shadeB += shadeStepBC;
                shadeC += shadeStepAC;
                yC += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            while (--yB >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xA >> 16, xB >> 16, shadeA >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xB += xStepBC;
                xA += xStepAB;
                shadeB += shadeStepBC;
                shadeA += shadeStepAB;
                yC += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            return;
        }
        xA = (xC <<= 16);
        shadeA = (shadeC <<= 16);
        if (yC < 0) {
            xA -= xStepBC * yC;
            xC -= xStepAC * yC;
            shadeA -= shadeStepBC * yC;
            shadeC -= shadeStepAC * yC;
            yC = 0;
        }
        xB <<= 16;
        shadeB <<= 16;
        if (yB < 0) {
            xB -= xStepAB * yB;
            shadeB -= shadeStepAB * yB;
            yB = 0;
        }
        int l9 = yC - Draw3D.centerY;
        u += uStepVertical * l9;
        v += vStepVertical * l9;
        w += wStepVertical * l9;
        if (xStepBC < xStepAC) {
            yA -= yB;
            yB -= yC;
            yC = Draw3D.lineOffset[yC];
            while (--yB >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xA >> 16, xC >> 16, shadeA >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xA += xStepBC;
                xC += xStepAC;
                shadeA += shadeStepBC;
                shadeC += shadeStepAC;
                yC += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            while (--yA >= 0) {
                Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xC >> 16, shadeB >> 8, shadeC >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
                xB += xStepAB;
                xC += xStepAC;
                shadeB += shadeStepAB;
                shadeC += shadeStepAC;
                yC += Draw2D.width;
                u += uStepVertical;
                v += vStepVertical;
                w += wStepVertical;
            }
            return;
        }
        yA -= yB;
        yB -= yC;
        yC = Draw3D.lineOffset[yC];
        while (--yB >= 0) {
            Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xA >> 16, shadeC >> 8, shadeA >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
            xA += xStepBC;
            xC += xStepAC;
            shadeA += shadeStepBC;
            shadeC += shadeStepAC;
            yC += Draw2D.width;
            u += uStepVertical;
            v += vStepVertical;
            w += wStepVertical;
        }
        while (--yA >= 0) {
            Draw3D.drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xB >> 16, shadeC >> 8, shadeB >> 8, u, v, w, uStrideHorizontal, vStrideHorizontal, wStrideHorizontal);
            xB += xStepAB;
            xC += xStepAC;
            shadeB += shadeStepAB;
            shadeC += shadeStepAC;
            yC += Draw2D.width;
            u += uStepVertical;
            v += vStepVertical;
            w += wStepVertical;
        }
    }

    public static void drawTexturedScanline(int[] dst, int[] texels, int curU, int curV, int offset, int xA, int xB, int shadeA, int shadeB, int u, int v, int w, int uStride, int vStride, int wStride) {
        if (xA >= xB) {
            return;
        }

        int shadeStride;
        int strides;

        if (Draw3D.clipX) {
            shadeStride = (shadeB - shadeA) / (xB - xA); // in this form, it's a 'shadeStep'

            if (xB > Draw2D.boundX) {
                xB = Draw2D.boundX;
            }

            if (xA < 0) {
                shadeA -= xA * shadeStride;
                xA = 0;
            }

            if (xA >= xB) {
                return;
            }

            strides = (xB - xA) >> 3;
            shadeStride <<= 12; // this is what transforms it to a stride. it's a (<<9) + (<<3)
        } else {
            if ((xB - xA) > 7) {
                strides = (xB - xA) >> 3;
                shadeStride = ((shadeB - shadeA) * Draw3D.reciprocal15[strides]) >> 6;
            } else {
                strides = 0;
                shadeStride = 0;
            }
        }

        shadeA <<= 9;
        offset += xA;

        if (Draw3D.lowmem) {
            int nextU = 0;
            int nextV = 0;

            int dx = xA - Draw3D.centerX;
            u += (uStride >> 3) * dx;
            v += (vStride >> 3) * dx;
            w += (wStride >> 3) * dx;

            int curW = w >> 12;

            if (curW != 0) {
                curU = u / curW;
                curV = v / curW;
                if (curU < 0) {
                    curU = 0;
                } else if (curU > 4032) {
                    curU = 4032;
                }
            }

            u += uStride;
            v += vStride;
            w += wStride;
            curW = w >> 12;

            if (curW != 0) {
                nextU = u / curW;
                nextV = v / curW;
                if (nextU < 7) {
                    nextU = 7;
                } else if (nextU > 4032) {
                    nextU = 4032;
                }
            }

            int stepU = (nextU - curU) >> 3;
            int stepV = (nextV - curV) >> 3;

            curU += (shadeA & 0x600000) >> 3; // treat curU like the offset and move to the correct tile in our atlas

            int shadeShift = shadeA >> 23; // always a value 0..3 inclusive

            // If you look @ getTexels() you'll notice that the texels are slightly decimated by performing & 0xF8F8FF
            // on them, this was to clear the lower 3 bits of R,G channels so that u can divide the whole number by 2
            // (or right shift 1) to achieve half values. It's a cheep hax to further darken the color value with 1 op.
            // technically the atlas is 1x4 textures but this trick allows 16 total different shades for each texture.

            if (Draw3D.opaque) {
                while (strides-- > 0) {
                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;

                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU = nextU; // last op just sets to the u/v we expected to end with to avoid accuracy loss
                    curV = nextV;

                    u += uStride;
                    v += vStride;
                    w += wStride;

                    int nextW = w >> 12;

                    if (nextW != 0) { // calculate u/v values for the end of our next stride
                        nextU = u / nextW;
                        nextV = v / nextW;

                        if (nextU < 7) {
                            nextU = 7;
                        } else if (nextU > 4032) {
                            nextU = 4032;
                        }
                    }

                    stepU = (nextU - curU) >> 3; // update our step size
                    stepV = (nextV - curV) >> 3;
                    shadeA += shadeStride;
                    curU += (shadeA & 0x600000) >> 3; // update tile in atlas
                    shadeShift = shadeA >> 23; // update shade
                }

                // handles the remaining pixels if the scanline wasn't divisible by 8
                for (strides = (xB - xA) & 7; strides-- > 0; ) {
                    dst[offset++] = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift;
                    curU += stepU;
                    curV += stepV;
                }
                return;
            }

            while (strides-- > 0) {
                int rgb;
                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU += stepU;
                curV += stepV;

                if ((rgb = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = rgb;
                }
                offset++;
                curU = nextU;
                curV = nextV;

                u += uStride;
                v += vStride;
                w += wStride;

                int nextW = w >> 12;

                if (nextW != 0) {
                    nextU = u / nextW;
                    nextV = v / nextW;
                    if (nextU < 7) {
                        nextU = 7;
                    } else if (nextU > 4032) {
                        nextU = 4032;
                    }
                }

                stepU = (nextU - curU) >> 3;
                stepV = (nextV - curV) >> 3;
                shadeA += shadeStride;
                curU += (shadeA & 0x600000) >> 3;
                shadeShift = shadeA >> 23;
            }
            for (strides = (xB - xA) & 7; strides-- > 0; ) {
                int l8;
                if ((l8 = texels[(curV & 0xfc0) + (curU >> 6)] >>> shadeShift) != 0) {
                    dst[offset] = l8;
                }
                offset++;
                curU += stepU;
                curV += stepV;
            }
            return;
        }

        int nextU = 0;
        int nextV = 0;
        int dx = xA - Draw3D.centerX;
        u += (uStride >> 3) * dx;
        v += (vStride >> 3) * dx;
        w += (wStride >> 3) * dx;
        int curW = w >> 14;
        if (curW != 0) {
            curU = u / curW;
            curV = v / curW;
            if (curU < 0) {
                curU = 0;
            } else if (curU > 16256) {
                curU = 16256;
            }
        }
        u += uStride;
        v += vStride;
        w += wStride;
        curW = w >> 14;
        if (curW != 0) {
            nextU = u / curW;
            nextV = v / curW;
            if (nextU < 7) {
                nextU = 7;
            } else if (nextU > 16256) {
                nextU = 16256;
            }
        }
        int uStep = (nextU - curU) >> 3;
        int vStep = (nextV - curV) >> 3;
        curU += shadeA & 0x600000;
        int shadeShift = shadeA >> 23;
        if (Draw3D.opaque) {
            while (strides-- > 0) {
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU = nextU;
                curV = nextV;
                u += uStride;
                v += vStride;
                w += wStride;
                int i6 = w >> 14;
                if (i6 != 0) {
                    nextU = u / i6;
                    nextV = v / i6;
                    if (nextU < 7) {
                        nextU = 7;
                    } else if (nextU > 16256) {
                        nextU = 16256;
                    }
                }
                uStep = (nextU - curU) >> 3;
                vStep = (nextV - curV) >> 3;
                shadeA += shadeStride;
                curU += shadeA & 0x600000;
                shadeShift = shadeA >> 23;
            }
            for (strides = (xB - xA) & 7; strides-- > 0; ) {
                dst[offset++] = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift;
                curU += uStep;
                curV += vStep;
            }
            return;
        }
        while (strides-- > 0) {
            int rgb;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU = nextU;
            curV = nextV;
            u += uStride;
            v += vStride;
            w += wStride;
            int nextW = w >> 14;
            if (nextW != 0) {
                nextU = u / nextW;
                nextV = v / nextW;
                if (nextU < 7) {
                    nextU = 7;
                } else if (nextU > 16256) {
                    nextU = 16256;
                }
            }
            uStep = (nextU - curU) >> 3;
            vStep = (nextV - curV) >> 3;
            shadeA += shadeStride;
            curU += shadeA & 0x600000;
            shadeShift = shadeA >> 23;
        }
        for (int len = (xB - xA) & 7; len-- > 0; ) {
            int rgb;
            if ((rgb = texels[(curV & 0x3f80) + (curU >> 7)] >>> shadeShift) != 0) {
                dst[offset] = rgb;
            }
            offset++;
            curU += uStep;
            curV += vStep;
        }
    }

}
