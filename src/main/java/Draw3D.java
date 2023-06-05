// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
    public static int transparency;
    public static int center_x;
    public static int center_y;
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
     * Instantiates {@link #lineOffset} and sets {@link #center_x} and {@link #center_y} using the width and height values
     * stored in {@link Draw2D}.
     */
    public static void init2D() {
        Draw3D.lineOffset = new int[Draw2D.height];
        for (int y = 0; y < Draw2D.height; y++) {
            Draw3D.lineOffset[y] = Draw2D.width * y;
        }
        Draw3D.center_x = Draw2D.width / 2;
        Draw3D.center_y = Draw2D.height / 2;
    }

    /**
     * Instantiates {@link #lineOffset} and sets {@link #center_x} and {@link #center_y} using the width and height values
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
        Draw3D.center_x = width / 2;
        Draw3D.center_y = height / 2;
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
            Draw3D.texelPool = new int[Draw3D.poolSize][128 * 128 * 4];
            for (int k = 0; k < 50; k++) {
                Draw3D.activeTexels[k] = null;
            }
        }

        for (int i = 0; i < 50; i++) {
            BufferedImage image = new BufferedImage(128, 512, BufferedImage.TYPE_INT_RGB);
            int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(getTexels(i), 0, pixels, 0, pixels.length);
            try {
                ImageIO.write(image, "png", Paths.get("out/texels/" + i + ".png").toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void unpackTextures(FileArchive archive) {
        Draw3D.textureCount = 0;

        for (int textureID = 0; textureID < 50; textureID++) {
            try {
                Draw3D.textures[textureID] = new Image8(archive, String.valueOf(textureID), 0);
                Draw3D.textures[textureID].crop();
                Draw3D.textureCount++;

                int size = textures[textureID].width;
                BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                for (int i = 0; i < size * size; i++) {
                    pixels[i] = Draw3D.textures[textureID].palette[Draw3D.textures[textureID].pixels[i]];
                }
                ImageIO.write(image, "png", Paths.get("out/textures/" + textureID + ".png").toFile());
            } catch (Exception e) {
                e.printStackTrace();
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
        return texels;
    }

    /**
     * Sets the gamma.
     *
     * @param gamma the gamma.
     */
    public static void buildPalette(double gamma) throws RuntimeException {

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

                Draw3D.palette[offset++] = rgb;
            }
        }

        BufferedImage img = new BufferedImage(128, 512, BufferedImage.TYPE_INT_RGB);
        System.arraycopy(Draw3D.palette, 0, ((DataBufferInt) img.getRaster().getDataBuffer()).getData(), 0, 128 * 512);

        try {
            ImageIO.write(img, "png", new File("palette_raw.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 128 * 512; i++) {
            int rgb = Draw3D.palette[i];
            rgb = Draw3D.setGamma(rgb, gamma);

            if (rgb == 0) {
                rgb = 1;
            }
            Draw3D.palette[i] = rgb;
        }

        System.arraycopy(Draw3D.palette, 0, ((DataBufferInt) img.getRaster().getDataBuffer()).getData(), 0, 128 * 512);

        try {
            ImageIO.write(img, "png", new File("palette_gamma.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int textureID = 0; textureID < 50; textureID++) {
            if (Draw3D.textures[textureID] == null) {
                continue;
            }

            int[] palette = Draw3D.textures[textureID].palette;
            Draw3D.texturePalette[textureID] = new int[palette.length];

            for (int i = 0; i < palette.length; i++) {
                Draw3D.texturePalette[textureID][i] = Draw3D.setGamma(palette[i], gamma);

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

    public static void main(String[] args) throws IOException {
        // build palette first since it flushes texels
        buildPalette(1.0);

        BufferedImage tex = ImageIO.read(new File("texture.png"));
        int[] texels = new int[128 * 128 * 4];
        int offset = 0;
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                texels[offset++] = tex.getRGB(x, y);
            }
        }

        activeTexels[0] = texels;

        for (int i = 0; i < 16384; i++) {
            int rgb = texels[i] & 0xF8F8FF;
            texels[i] = rgb;
            texels[16384 + i] = (rgb - (rgb >>> 3)) & 0xf8f8ff;
            texels[32768 + i] = (rgb - (rgb >>> 2)) & 0xf8f8ff;
            texels[49152 + i] = (rgb - (rgb >>> 2) - (rgb >>> 3)) & 0xf8f8ff;
        }

        final int imageSize = 512;
        BufferedImage img0 = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        int[] pix0 = ((DataBufferInt) img0.getRaster().getDataBuffer()).getData();

        BufferedImage img1 = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        int[] pix1 = ((DataBufferInt) img1.getRaster().getDataBuffer()).getData();

        BufferedImage img2 = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        int[] pix2 = ((DataBufferInt) img2.getRaster().getDataBuffer()).getData();

        Draw3D.init3D(imageSize, imageSize);
        Draw3D.jagged = true;

        int splits = 3;
        int segmentSize = imageSize / splits;
        int radius = segmentSize / 2;
        double radsPerSegment = (2 * Math.PI) / (splits * splits);
        double turn = (2 * Math.PI) / 3;

        for (int row = 0; row < splits; row++) {
            for (int col = 0; col < splits; col++) {
                int x = (col * segmentSize) + radius;
                int y = (row * segmentSize) + radius;
                int index = col + (row * splits);
                double angle = index * radsPerSegment;

                int x0 = x + (int) (Math.cos(angle) * radius);
                int y0 = y + (int) (Math.sin(angle) * radius);

                int x1 = x + (int) (Math.cos(angle + turn) * radius);
                int y1 = y + (int) (Math.sin(angle + turn) * radius);

                int x2 = x + (int) (Math.cos(angle + turn * 2) * radius);
                int y2 = y + (int) (Math.sin(angle + turn * 2) * radius);

                Draw2D.bind(pix0, imageSize, imageSize);
                fillGouraudTriangle(x0, y0, x1, y1, x2, y2, 1, 64, 127);

                Draw2D.bind(pix1, imageSize, imageSize);
                fillTriangle(y0, y1, y2, x0, x1, x2, 0xFFFFFF);

                Draw2D.bind(pix2, imageSize, imageSize);

                fillTexturedTriangle(y0, y1, y2, x0, x1, x2, 127, 64, 0, x0 - 256, x1 - 256, x2 - 256, y0 - 256, y1 - 256, y2 - 256, 512, 512, 512, 0);
            }
        }

        ImageIO.write(img0, "png", new File("d3d_jagged_gouraud_triangles.png"));
        ImageIO.write(img1, "png", new File("d3d_flat_triangles.png"));
        ImageIO.write(img2, "png", new File("d3d_jagged_textured_triangles.png"));

        Draw3D.jagged = false;

        for (int row = 0; row < splits; row++) {
            for (int col = 0; col < splits; col++) {
                int x = (col * segmentSize) + radius;
                int y = (row * segmentSize) + radius;
                int index = col + (row * splits);
                double angle = index * radsPerSegment;

                int x0 = x + (int) (Math.cos(angle) * radius);
                int y0 = y + (int) (Math.sin(angle) * radius);

                int x1 = x + (int) (Math.cos(angle + turn) * radius);
                int y1 = y + (int) (Math.sin(angle + turn) * radius);

                int x2 = x + (int) (Math.cos(angle + turn * 2) * radius);
                int y2 = y + (int) (Math.sin(angle + turn * 2) * radius);

                Draw2D.bind(pix0, imageSize, imageSize);
                fillGouraudTriangle(x0, y0, x1, y1, x2, y2, 1, 64, 127);

            }
        }

        ImageIO.write(img0, "png", new File("d3d_smooth_gouraud_triangles.png"));

        tex = new BufferedImage(128, 512, BufferedImage.TYPE_INT_RGB);
        System.arraycopy(texels, 0, ((DataBufferInt) tex.getRaster().getDataBuffer()).getData(), 0, 128 * 128 * 4);
        ImageIO.write(tex, "png", new File("texels.png"));
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
    public static boolean debug = false;

    public static void fillGouraudTriangle(int xA, int yA, int xB, int yB, int xC, int yC, int colorA, int colorB, int colorC) {
        if (debug) {
            System.out.println("fillGouraudTriangle xA = " + xA + ", yA = " + yA + ", xB = " + xB + ", yB = " + yB + ", xC = " + xC + ", yC = " + yC + ", colorA = " + colorA + ", colorB = " + colorB + ", colorC = " + colorC);
        }
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

            if (Draw3D.transparency == 0) {
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
                int trans = Draw3D.transparency;
                int inv_trans = 256 - Draw3D.transparency;

                while (--length >= 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    color0 += colorStep;
                    rgb = ((((rgb & 0xff00ff) * inv_trans) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * inv_trans) >> 8) & 0xff00);
                    dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * trans) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * trans) >> 8) & 0xff00);
                    offset++;
                    dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * trans) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * trans) >> 8) & 0xff00);
                    offset++;
                    dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * trans) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * trans) >> 8) & 0xff00);
                    offset++;
                    dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * trans) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * trans) >> 8) & 0xff00);
                    offset++;
                }

                length = (x1 - x0) & 3;

                if (length > 0) {
                    rgb = Draw3D.palette[color0 >> 8];
                    rgb = ((((rgb & 0xff00ff) * inv_trans) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * inv_trans) >> 8) & 0xff00);
                    do {
                        dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * trans) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * trans) >> 8) & 0xff00);
                        offset++;
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

        if (Draw3D.transparency == 0) {
            do {
                dst[offset++] = Draw3D.palette[color0 >> 8];
                color0 += colorStep;
            } while (--length > 0);
            return;
        }

        int alpha = Draw3D.transparency;
        int invAlpha = 256 - Draw3D.transparency;

        do {
            rgb = Draw3D.palette[color0 >> 8];
            color0 += colorStep;
            rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);
            // If you want to fix the lines in transparent models like ghostly or bank booths, change dst[offset++] to
            // dst[offset] and on the next line below put offset++
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
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

        if (Draw3D.transparency == 0) {
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

        int alpha = Draw3D.transparency;
        int invAlpha = 256 - Draw3D.transparency;

        rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);

        while (--length >= 0) {
            // to fix lines in transparent things: change index operand to 'offset' and add 'offset++' below each line
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
        }

        for (length = (x1 - x0) & 3; --length >= 0; ) {
            dst[offset] = rgb + ((((dst[offset] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[offset] & 0xff00) * alpha) >> 8) & 0xff00);
            offset++;
        }
    }

    /**
     * Long story short, this defines a plane in view space and traverses it to determine uv values. It's an impl of
     * this algorithm: <a href="https://www.gamers.org/dEngine/rsc/pcgpe-1.0/texture.txt">texture.txt</a>
     */
    public static int printCycle = 0;

    public static void fillTexturedTriangle(int y0, int y1, int y2, int x0, int x1, int x2, int shade0, int shade1, int shade2, int tx0, int tx1, int tx2, int ty0, int ty1, int ty2, int tz0, int tz1, int tz2, int texture) {
        int[] texels = getTexels(texture);
        opaque = !textureTranslucent[texture];

        // t1 becomes a normal
        tx1 = tx0 - tx1;
        ty1 = ty0 - ty1;
        tz1 = tz0 - tz1;

        // t2 becomes a normal
        tx2 -= tx0;
        ty2 -= ty0;
        tz2 -= tz0;

        // https://www.gamers.org/dEngine/rsc/pcgpe-1.0/texture.txt

        // hStep/vStep = horizontal & vertical steps

        int u = ((tx2 * ty0) - (ty2 * tx0)) << 14; // 18.14 fixed int
        int u_step_x = ((ty2 * tz0) - (tz2 * ty0)) << 5; // 27.5 fixed int
        int u_step_y = ((tz2 * tx0) - (tx2 * tz0)) << 5; // 27.5 fixed int

        int v = ((tx1 * ty0) - (ty1 * tx0)) << 14;
        int v_step_x = ((ty1 * tz0) - (tz1 * ty0)) << 5;
        int v_step_y = ((tz1 * tx0) - (tx1 * tz0)) << 5;

        int w = ((ty1 * tx2) - (tx1 * ty2)) << 14;
        int w_step_x = ((tz1 * ty2) - (ty1 * tz2)) << 5;
        int w_step_y = ((tx1 * tz2) - (tz1 * tx2)) << 5;

        if (y0 > y2) {
            int tmp = x0;
            x0 = x2;
            x2 = tmp;

            tmp = y0;
            y0 = y2;
            y2 = tmp;

            tmp = shade0;
            shade0 = shade2;
            shade2 = tmp;
        }

        // A below B
        if (y0 > y1) {
            int tmp = x0;
            x0 = x1;
            x1 = tmp;

            tmp = y0;
            y0 = y1;
            y1 = tmp;

            tmp = shade0;
            shade0 = shade1;
            shade1 = tmp;
        }

        // B below C
        if (y1 > y2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;

            tmp = y1;
            y1 = y2;
            y2 = tmp;

            tmp = shade1;
            shade1 = shade2;
            shade2 = tmp;
        }

        int xStepAB = 0;
        int xStepBC = 0;
        int xStepAC = 0;

        int shadeStepAB = 0;
        int shadeStepBC = 0;
        int shadeStepAC = 0;

        if (y1 != y0) {
            xStepAB = ((x1 - x0) << 16) / (y1 - y0);
            shadeStepAB = ((shade1 - shade0) << 16) / (y1 - y0);
        }

        if (y2 != y1) {
            xStepBC = ((x2 - x1) << 16) / (y2 - y1);
            shadeStepBC = ((shade2 - shade1) << 16) / (y2 - y1);
        }

        if (y2 != y0) {
            xStepAC = ((x0 - x2) << 16) / (y0 - y2);
            shadeStepAC = ((shade0 - shade2) << 16) / (y0 - y2);
        }

        if (y0 >= Draw2D.bottom) {
            return;
        }

        if (y1 > Draw2D.bottom) {
            y1 = Draw2D.bottom;
        }

        if (y2 > Draw2D.bottom) {
            y2 = Draw2D.bottom;
        }

        x0 <<= 16;
        x2 = x0;
        x1 <<= 16;

        shade0 <<= 16;
        shade2 = shade0;
        shade1 <<= 16;

        // trim upper
        if (y0 < 0) {
            x2 -= xStepAC * y0;
            x0 -= xStepAB * y0;
            shade2 -= shadeStepAC * y0;
            shade0 -= shadeStepAB * y0;
            y0 = 0;
        }

        // trim lower
        if (y1 < 0) {
            x1 -= xStepBC * y1;
            shade1 -= shadeStepBC * y1;
            y1 = 0;
        }

        int dy = y0 - center_y;

        u += u_step_y * dy;
        v += v_step_y * dy;
        w += w_step_y * dy;

        int remaining0 = y1 - y0;
        int remaining1 = y2 - y1;
        int offset = lineOffset[y0];

        while (--remaining0 >= 0) {
            drawTexturedScanline(Draw2D.pixels, texels, offset, y0, x2 >> 16, x0 >> 16, shade2 >> 8, shade0 >> 8, u, v, w, u_step_x, v_step_x, w_step_x);
            x2 += xStepAC;
            x0 += xStepAB;
            shade2 += shadeStepAC;
            shade0 += shadeStepAB;
            offset += Draw2D.width;
            u += u_step_y;
            v += v_step_y;
            w += w_step_y;
            y0++;
        }

        while (--remaining1 >= 0) {
            drawTexturedScanline(Draw2D.pixels, texels, offset, y1, x2 >> 16, x1 >> 16, shade2 >> 8, shade1 >> 8, u, v, w, u_step_x, v_step_x, w_step_x);
            x2 += xStepAC;
            x1 += xStepBC;
            shade2 += shadeStepAC;
            shade1 += shadeStepBC;
            offset += Draw2D.width;
            u += u_step_y;
            v += v_step_y;
            w += w_step_y;
            y1++;
        }
    }

    /**
     * @param dst    the destination pixels
     * @param texels the source texels
     * @param offset the destination offset
     * @param x0     left x boundary as whole numbers
     * @param x1     right x boundary as whole numbers
     * @param shade0 left darkness value as a 24.8 fixed int
     * @param shade1 right darkness value as a 24.8 fixed int
     * @param u      the u coordinate on the texture plane (x) as a 18.14 fixed int
     * @param v      the v coordinate on the texture plane (y) as a 18.14 fixed int
     * @param w      the w coordinate on the texture plane (z) as a 18.14 fixed int
     * @param uStep  the horizontal stride for the u texture plane coordinate as a 24.8 fixed int
     * @param vStep  the horizontal stride for the v texture plane coordinate as a 24.8 fixed int
     * @param wStep  the horizontal stride for the w texture plane coordinate as a 24.8 fixed int
     */
    private static void drawTexturedScanline(int[] dst, int[] texels, int offset, int y, int x0, int x1, int shade0, int shade1, int u, int v, int w, int uStep, int vStep, int wStep) {
        // Ensure the scanline is in A->B order
        if (x0 == x1) {
            return;
        } else if (x0 > x1) {
            int tmp = x0;
            x0 = x1;
            x1 = tmp;

            tmp = shade0;
            shade0 = shade1;
            shade1 = tmp;
        }

        int shadeStep = (shade1 - shade0) / (x1 - x0);

        if (x1 > Draw2D.boundX) {
            x1 = Draw2D.boundX;
        }

        if (x0 < 0) {
            shade0 -= x0 * shadeStep;
            x0 = 0;
        }

        int length = x1 - x0;

        // convert shade0 and shadeStep to 15.17 fixed ints
        shadeStep <<= 9;
        shade0 <<= 9;

        offset += x0;

        int dx = x0 - center_x;

        u += uStep * dx;
        v += vStep * dx;
        w += wStep * dx;

        if (opaque) {
            drawOpaqueTexturedScanlineHighmem(dst, texels, offset, x0, y, shade0, u, v, w, uStep, vStep, wStep, shadeStep, length);
        } else {
            drawTransparentTexturedScanlineHighmem(dst, texels, offset, x0, y, shade0, u, v, w, uStep, vStep, wStep, shadeStep, length);
        }
    }

    private static void drawOpaqueTexturedScanlineHighmem(int[] dst, int[] texels, int offset, int x, int y, int shade, int u, int v, int w, int uStep, int vStep, int wStep, int shadeStep, int length) {
        while (length-- > 0) {
            int w1 = w >> 14;
            int u1 = 0;
            int v1 = 0;

            if (w1 != 0) {
                u1 = u / w1; // recalculate destination uv for next iteration
                v1 = v / w1;

                if (u1 < 7) {
                    u1 = 7;
                } else if (u1 > 16256) {
                    u1 = 16256;
                }
            }

            // shift texture to the given shade
            u1 += (shade & 0x600000);

            dst[offset++] = texels[(v1 & 0x3f80) + (u1 >> 7)] >>> (shade >>> 23);

            x++;
            u += uStep;
            v += vStep;
            w += wStep;
            shade += shadeStep;
        }
    }

    private static void drawTransparentTexturedScanlineHighmem(int[] dst, int[] texels, int offset, int x, int y, int shade, int u, int v, int w, int uStep, int vStep, int wStep, int shadeStep, int length) {
        while (length-- > 0) {
            int w1 = w >> 14;
            int u1 = 0;
            int v1 = 0;

            if (w1 != 0) {
                u1 = u / w1; // recalculate destination uv for next iteration
                v1 = v / w1;

                if (u1 < 7) {
                    u1 = 7;
                } else if (u1 > 16256) {
                    u1 = 16256;
                }
            }

            // shift texture to the given shade
            u1 += (shade & 0x600000);

            int texel;
            if ((texel = texels[(v1 & 0x3f80) + (u1 >> 7)] >>> (shade >>> 23)) != 0) {
                dst[offset] = texel;
            }

            offset++;
            x++;
            u += uStep;
            v += vStep;
            w += wStep;
            shade += shadeStep;
        }
    }

}
