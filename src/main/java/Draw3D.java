// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Draw3D {

	/**
	 * A lookup table for 1 / n in 16.16 fixed integer form for values [0...2048]. See below for usages.
	 *
	 * @see Model#drawNearClippedFace(int)
	 */
	public static final int[] reciprical16 = new int[2048];
	/**
	 * A lookup table for (1 / n) in 17.15 fixed integer form for values [0...512]. See below for usages.
	 *
	 * @see #drawGouraudScanline(int[], int, int, int, int, int)
	 * @see #drawTexturedScanline(int[], int[], int, int, int, int, int, int, int, int, int, int, int, int, int)
	 */
	public static final int[] reciprical15 = new int[512];
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
			reciprical15[i] = (1 << 15) / i;
		}
		for (int j = 1; j < 2048; j++) {
			reciprical16[j] = (1 << 16) / j;
		}
		for (int k = 0; k < 2048; k++) {
			sin[k] = (int) (65536D * Math.sin((double) k * 0.0030679614999999999D));
			cos[k] = (int) (65536D * Math.cos((double) k * 0.0030679614999999999D));
		}
	}

	public static void unload() {
		sin = null;
		cos = null;
		lineOffset = null;
		textures = null;
		textureTranslucent = null;
		averageTextureRGB = null;
		texelPool = null;
		activeTexels = null;
		textureCycle = null;
		palette = null;
		texturePalette = null;
	}

	/**
	 * Instantiates {@link #lineOffset} and sets {@link #centerX} and {@link #centerY} using the width and height values
	 * stored in {@link Draw2D}.
	 */
	public static void init2D() {
		lineOffset = new int[Draw2D.height];
		for (int y = 0; y < Draw2D.height; y++) {
			lineOffset[y] = Draw2D.width * y;
		}
		centerX = Draw2D.width / 2;
		centerY = Draw2D.height / 2;
	}

	/**
	 * Instantiates {@link #lineOffset} and sets {@link #centerX} and {@link #centerY} using the width and height values
	 * provided.
	 *
	 * @param width  the width.
	 * @param height the height.
	 */
	public static void init3D(int width, int height) {
		lineOffset = new int[height];
		for (int y = 0; y < height; y++) {
			lineOffset[y] = width * y;
		}
		centerX = width / 2;
		centerY = height / 2;
	}

	/**
	 * Nullifies the texel pool. {@link #initPool(int)} must be called again if textured triangles are drawn.
	 */
	public static void clearTexels() {
		texelPool = null;
		for (int j = 0; j < 50; j++) {
			activeTexels[j] = null;
		}
	}

	/**
	 * Initializes the texel pool.
	 *
	 * @param poolSize the initial pool size.
	 */
	public static void initPool(int poolSize) {
		if (texelPool == null) {
			Draw3D.poolSize = poolSize;
			if (lowmem) {
				texelPool = new int[Draw3D.poolSize][64 * 64 * 4];
			} else {
				texelPool = new int[Draw3D.poolSize][128 * 128 * 4];
			}
			for (int k = 0; k < 50; k++) {
				activeTexels[k] = null;
			}
		}
	}

	public static void unpackTextures(FileArchive archive) {
		textureCount = 0;

		for (int textureId = 0; textureId < 50; textureId++) {
			try {
				textures[textureId] = new Image8(archive, String.valueOf(textureId), 0);

				if (lowmem && (textures[textureId].cropW == 128)) {
					textures[textureId].shrink();
				} else {
					textures[textureId].crop();
				}
				textureCount++;
			} catch (Exception ignored) {
			}
		}
	}

	public static int getAverageTextureRGB(int textureId) {
		if (averageTextureRGB[textureId] != 0) {
			return averageTextureRGB[textureId];
		}
		int r = 0;
		int g = 0;
		int b = 0;
		int length = texturePalette[textureId].length;
		for (int i = 0; i < length; i++) {
			r += (texturePalette[textureId][i] >> 16) & 0xff;
			g += (texturePalette[textureId][i] >> 8) & 0xff;
			b += texturePalette[textureId][i] & 0xff;
		}
		int rgb = ((r / length) << 16) + ((g / length) << 8) + (b / length);
		rgb = powRGB(rgb, 1.3999999999999999D);
		if (rgb == 0) {
			rgb = 1;
		}
		averageTextureRGB[textureId] = rgb;
		return rgb;
	}

	/**
	 * Pushes the texels of the provided texture id back into the pool.
	 *
	 * @param textureId the texture id.
	 */
	public static void unloadTexture(int textureId) {
		if (activeTexels[textureId] == null) {
			return;
		}
		texelPool[poolSize++] = activeTexels[textureId];
		activeTexels[textureId] = null;
	}

	public static int[] getTexels(int textureId) {
		textureCycle[textureId] = cycle++;

		if (activeTexels[textureId] != null) {
			return activeTexels[textureId];
		}

		int[] texels;

		if (poolSize > 0) {
			texels = texelPool[--poolSize];
			texelPool[poolSize] = null;
		} else {
			int cycle = 0;
			int selected = -1;

			for (int t = 0; t < textureCount; t++) {
				if ((activeTexels[t] != null) && ((textureCycle[t] < cycle) || (selected == -1))) {
					cycle = textureCycle[t];
					selected = t;
				}
			}

			texels = activeTexels[selected];
			activeTexels[selected] = null;
		}

		activeTexels[textureId] = texels;
		Image8 texture = textures[textureId];
		int[] palette = texturePalette[textureId];

		if (lowmem) {
			textureTranslucent[textureId] = false;

			for (int i = 0; i < 4096; i++) {
				int rgb = texels[i] = palette[texture.pixels[i]] & 0xf8f8ff;

				if (rgb == 0) {
					textureTranslucent[textureId] = true;
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

			textureTranslucent[textureId] = false;

			for (int i = 0; i < 16384; i++) {
				texels[i] &= 0xf8f8ff;

				int rgb = texels[i];

				if (rgb == 0) {
					textureTranslucent[textureId] = true;
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

				rgb = powRGB(rgb, brightness);

				if (rgb == 0) {
					rgb = 1;
				}

				palette[offset++] = rgb;
			}
		}

		for (int textureId = 0; textureId < 50; textureId++) {
			if (textures[textureId] == null) {
				continue;
			}

			int[] palette = textures[textureId].palette;
			texturePalette[textureId] = new int[palette.length];

			for (int i = 0; i < palette.length; i++) {
				texturePalette[textureId][i] = powRGB(palette[i], brightness);

				if (((texturePalette[textureId][i] & 0xf8f8ff) == 0) && (i != 0)) {
					texturePalette[textureId][i] = 1;
				}
			}
		}

		for (int textureId = 0; textureId < 50; textureId++) {
			unloadTexture(textureId);
		}
	}

	/**
	 * Returns the <code>rgb</code> with each component raised to the power of <code>brightness</code>
	 *
	 * @param rgb        the input rgb.
	 * @param brightness the brightness.
	 * @return the result.
	 */
	private static int powRGB(int rgb, double brightness) {
		double r = (double) (rgb >> 16) / 256D;
		double g = (double) ((rgb >> 8) & 0xff) / 256D;
		double b = (double) (rgb & 0xff) / 256D;
		r = Math.pow(r, brightness);
		g = Math.pow(g, brightness);
		b = Math.pow(b, brightness);
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
					for (yA = lineOffset[yA]; --yB >= 0; yA += Draw2D.width) {
						drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
						xC += xStepAC;
						xA += xStepAB;
						colorC += colorStepAC;
						colorA += colorStepAB;
					}
					while (--yC >= 0) {
						drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
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
				for (yA = lineOffset[yA]; --yB >= 0; yA += Draw2D.width) {
					drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
					xC += xStepAC;
					xA += xStepAB;
					colorC += colorStepAC;
					colorA += colorStepAB;
				}
				while (--yC >= 0) {
					drawGouraudScanline(Draw2D.pixels, yA, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
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
				for (yA = lineOffset[yA]; --yC >= 0; yA += Draw2D.width) {
					drawGouraudScanline(Draw2D.pixels, yA, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
					xB += xStepAC;
					xA += xStepAB;
					colorB += colorStepAC;
					colorA += colorStepAB;
				}
				while (--yB >= 0) {
					drawGouraudScanline(Draw2D.pixels, yA, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
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
			for (yA = lineOffset[yA]; --yC >= 0; yA += Draw2D.width) {
				drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
				xB += xStepAC;
				xA += xStepAB;
				colorB += colorStepAC;
				colorA += colorStepAB;
			}
			while (--yB >= 0) {
				drawGouraudScanline(Draw2D.pixels, yA, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
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
					for (yB = lineOffset[yB]; --yC >= 0; yB += Draw2D.width) {
						drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
						xA += xStepAB;
						xB += xStepBC;
						colorA += colorStepAB;
						colorB += colorStepBC;
					}
					while (--yA >= 0) {
						drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
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
				for (yB = lineOffset[yB]; --yC >= 0; yB += Draw2D.width) {
					drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
					xA += xStepAB;
					xB += xStepBC;
					colorA += colorStepAB;
					colorB += colorStepBC;
				}
				while (--yA >= 0) {
					drawGouraudScanline(Draw2D.pixels, yB, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
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
				for (yB = lineOffset[yB]; --yA >= 0; yB += Draw2D.width) {
					drawGouraudScanline(Draw2D.pixels, yB, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
					xC += xStepAB;
					xB += xStepBC;
					colorC += colorStepAB;
					colorB += colorStepBC;
				}
				while (--yC >= 0) {
					drawGouraudScanline(Draw2D.pixels, yB, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
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
			for (yB = lineOffset[yB]; --yA >= 0; yB += Draw2D.width) {
				drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
				xC += xStepAB;
				xB += xStepBC;
				colorC += colorStepAB;
				colorB += colorStepBC;
			}
			while (--yC >= 0) {
				drawGouraudScanline(Draw2D.pixels, yB, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
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
				for (yC = lineOffset[yC]; --yA >= 0; yC += Draw2D.width) {
					drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
					xB += xStepBC;
					xC += xStepAC;
					colorB += colorStepBC;
					colorC += colorStepAC;
				}
				while (--yB >= 0) {
					drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xA >> 16, colorB >> 7, colorA >> 7);
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
			for (yC = lineOffset[yC]; --yA >= 0; yC += Draw2D.width) {
				drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
				xB += xStepBC;
				xC += xStepAC;
				colorB += colorStepBC;
				colorC += colorStepAC;
			}
			while (--yB >= 0) {
				drawGouraudScanline(Draw2D.pixels, yC, xA >> 16, xB >> 16, colorA >> 7, colorB >> 7);
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
			for (yC = lineOffset[yC]; --yB >= 0; yC += Draw2D.width) {
				drawGouraudScanline(Draw2D.pixels, yC, xA >> 16, xC >> 16, colorA >> 7, colorC >> 7);
				xA += xStepBC;
				xC += xStepAC;
				colorA += colorStepBC;
				colorC += colorStepAC;
			}
			while (--yA >= 0) {
				drawGouraudScanline(Draw2D.pixels, yC, xB >> 16, xC >> 16, colorB >> 7, colorC >> 7);
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
		for (yC = lineOffset[yC]; --yB >= 0; yC += Draw2D.width) {
			drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xA >> 16, colorC >> 7, colorA >> 7);
			xA += xStepBC;
			xC += xStepAC;
			colorA += colorStepBC;
			colorC += colorStepAC;
		}
		while (--yA >= 0) {
			drawGouraudScanline(Draw2D.pixels, yC, xC >> 16, xB >> 16, colorC >> 7, colorB >> 7);
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
	 * @param dst        the destination.
	 * @param dstOff     the destination offset.
	 * @param leftX      the left x.
	 * @param rightX     the right x.
	 * @param leftColor  the left color.
	 * @param rightColor the right color.
	 * @see #fillGouraudTriangle(int, int, int, int, int, int, int, int, int) e
	 */
	public static void drawGouraudScanline(int[] dst, int dstOff, int leftX, int rightX, int leftColor, int rightColor) {
		int color;
		int length;

		if (jagged) {
			int colorStep;

			if (clipX) {
				if ((rightX - leftX) > 3) {
					colorStep = (rightColor - leftColor) / (rightX - leftX);
				} else {
					colorStep = 0;
				}

				if (rightX > Draw2D.boundX) {
					rightX = Draw2D.boundX;
				}

				if (leftX < 0) {
					leftColor -= leftX * colorStep;
					leftX = 0;
				}

				if (leftX >= rightX) {
					return;
				}

				dstOff += leftX;
				length = (rightX - leftX) >> 2;
				colorStep <<= 2;
			} else {
				if (leftX >= rightX) {
					return;
				}

				dstOff += leftX;
				length = (rightX - leftX) >> 2;

				if (length > 0) {
					colorStep = ((rightColor - leftColor) * reciprical15[length]) >> 15;
				} else {
					colorStep = 0;
				}
			}

			if (Draw3D.alpha == 0) {
				while (--length >= 0) {
					color = palette[leftColor >> 8];
					leftColor += colorStep;
					dst[dstOff++] = color;
					dst[dstOff++] = color;
					dst[dstOff++] = color;
					dst[dstOff++] = color;
				}

				length = (rightX - leftX) & 3;

				if (length > 0) {
					color = palette[leftColor >> 8];
					do {
						dst[dstOff++] = color;
					} while (--length > 0);
					return;
				}
			} else {
				int alpha = Draw3D.alpha;
				int invAlpha = 256 - Draw3D.alpha;

				while (--length >= 0) {
					color = palette[leftColor >> 8];
					leftColor += colorStep;
					color = ((((color & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((color & 0xff00) * invAlpha) >> 8) & 0xff00);
					dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
					dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
					dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
					dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
				}

				length = (rightX - leftX) & 3;

				if (length > 0) {
					color = palette[leftColor >> 8];
					color = ((((color & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((color & 0xff00) * invAlpha) >> 8) & 0xff00);
					do {
						dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
					} while (--length > 0);
				}
			}
			return;
		}

		if (leftX >= rightX) {
			return;
		}

		int colorStep = (rightColor - leftColor) / (rightX - leftX);

		if (clipX) {
			if (rightX > Draw2D.boundX) {
				rightX = Draw2D.boundX;
			}
			if (leftX < 0) {
				leftColor -= leftX * colorStep;
				leftX = 0;
			}
			if (leftX >= rightX) {
				return;
			}
		}

		dstOff += leftX;
		length = rightX - leftX;

		if (Draw3D.alpha == 0) {
			do {
				dst[dstOff++] = palette[leftColor >> 8];
				leftColor += colorStep;
			} while (--length > 0);
			return;
		}

		int alpha = Draw3D.alpha;
		int invAlpha = 256 - Draw3D.alpha;

		do {
			color = palette[leftColor >> 8];
			leftColor += colorStep;
			color = ((((color & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((color & 0xff00) * invAlpha) >> 8) & 0xff00);
			dst[dstOff++] = color + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
		} while (--length > 0);
	}

	public static void fillTriangle(int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = 0;
		if (j != i) {
			l1 = ((i1 - l) << 16) / (j - i);
		}
		int i2 = 0;
		if (k != j) {
			i2 = ((j1 - i1) << 16) / (k - j);
		}
		int j2 = 0;
		if (k != i) {
			j2 = ((l - j1) << 16) / (i - k);
		}
		if ((i <= j) && (i <= k)) {
			if (i >= Draw2D.bottom) {
				return;
			}
			if (j > Draw2D.bottom) {
				j = Draw2D.bottom;
			}
			if (k > Draw2D.bottom) {
				k = Draw2D.bottom;
			}
			if (j < k) {
				j1 = (l <<= 16);
				if (i < 0) {
					j1 -= j2 * i;
					l -= l1 * i;
					i = 0;
				}
				i1 <<= 16;
				if (j < 0) {
					i1 -= i2 * j;
					j = 0;
				}
				if (((i != j) && (j2 < l1)) || ((i == j) && (j2 > i2))) {
					k -= j;
					j -= i;
					for (i = lineOffset[i]; --j >= 0; i += Draw2D.width) {
						method377(Draw2D.pixels, i, k1, j1 >> 16, l >> 16);
						j1 += j2;
						l += l1;
					}
					while (--k >= 0) {
						method377(Draw2D.pixels, i, k1, j1 >> 16, i1 >> 16);
						j1 += j2;
						i1 += i2;
						i += Draw2D.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for (i = lineOffset[i]; --j >= 0; i += Draw2D.width) {
					method377(Draw2D.pixels, i, k1, l >> 16, j1 >> 16);
					j1 += j2;
					l += l1;
				}
				while (--k >= 0) {
					method377(Draw2D.pixels, i, k1, i1 >> 16, j1 >> 16);
					j1 += j2;
					i1 += i2;
					i += Draw2D.width;
				}
				return;
			}
			i1 = (l <<= 16);
			if (i < 0) {
				i1 -= j2 * i;
				l -= l1 * i;
				i = 0;
			}
			j1 <<= 16;
			if (k < 0) {
				j1 -= i2 * k;
				k = 0;
			}
			if (((i != k) && (j2 < l1)) || ((i == k) && (i2 > l1))) {
				j -= k;
				k -= i;
				for (i = lineOffset[i]; --k >= 0; i += Draw2D.width) {
					method377(Draw2D.pixels, i, k1, i1 >> 16, l >> 16);
					i1 += j2;
					l += l1;
				}
				while (--j >= 0) {
					method377(Draw2D.pixels, i, k1, j1 >> 16, l >> 16);
					j1 += i2;
					l += l1;
					i += Draw2D.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for (i = lineOffset[i]; --k >= 0; i += Draw2D.width) {
				method377(Draw2D.pixels, i, k1, l >> 16, i1 >> 16);
				i1 += j2;
				l += l1;
			}
			while (--j >= 0) {
				method377(Draw2D.pixels, i, k1, l >> 16, j1 >> 16);
				j1 += i2;
				l += l1;
				i += Draw2D.width;
			}
			return;
		}
		if (j <= k) {
			if (j >= Draw2D.bottom) {
				return;
			}
			if (k > Draw2D.bottom) {
				k = Draw2D.bottom;
			}
			if (i > Draw2D.bottom) {
				i = Draw2D.bottom;
			}
			if (k < i) {
				l = (i1 <<= 16);
				if (j < 0) {
					l -= l1 * j;
					i1 -= i2 * j;
					j = 0;
				}
				j1 <<= 16;
				if (k < 0) {
					j1 -= j2 * k;
					k = 0;
				}
				if (((j != k) && (l1 < i2)) || ((j == k) && (l1 > j2))) {
					i -= k;
					k -= j;
					for (j = lineOffset[j]; --k >= 0; j += Draw2D.width) {
						method377(Draw2D.pixels, j, k1, l >> 16, i1 >> 16);
						l += l1;
						i1 += i2;
					}
					while (--i >= 0) {
						method377(Draw2D.pixels, j, k1, l >> 16, j1 >> 16);
						l += l1;
						j1 += j2;
						j += Draw2D.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for (j = lineOffset[j]; --k >= 0; j += Draw2D.width) {
					method377(Draw2D.pixels, j, k1, i1 >> 16, l >> 16);
					l += l1;
					i1 += i2;
				}
				while (--i >= 0) {
					method377(Draw2D.pixels, j, k1, j1 >> 16, l >> 16);
					l += l1;
					j1 += j2;
					j += Draw2D.width;
				}
				return;
			}
			j1 = (i1 <<= 16);
			if (j < 0) {
				j1 -= l1 * j;
				i1 -= i2 * j;
				j = 0;
			}
			l <<= 16;
			if (i < 0) {
				l -= j2 * i;
				i = 0;
			}
			if (l1 < i2) {
				k -= i;
				i -= j;
				for (j = lineOffset[j]; --i >= 0; j += Draw2D.width) {
					method377(Draw2D.pixels, j, k1, j1 >> 16, i1 >> 16);
					j1 += l1;
					i1 += i2;
				}
				while (--k >= 0) {
					method377(Draw2D.pixels, j, k1, l >> 16, i1 >> 16);
					l += j2;
					i1 += i2;
					j += Draw2D.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for (j = lineOffset[j]; --i >= 0; j += Draw2D.width) {
				method377(Draw2D.pixels, j, k1, i1 >> 16, j1 >> 16);
				j1 += l1;
				i1 += i2;
			}
			while (--k >= 0) {
				method377(Draw2D.pixels, j, k1, i1 >> 16, l >> 16);
				l += j2;
				i1 += i2;
				j += Draw2D.width;
			}
			return;
		}
		if (k >= Draw2D.bottom) {
			return;
		}
		if (i > Draw2D.bottom) {
			i = Draw2D.bottom;
		}
		if (j > Draw2D.bottom) {
			j = Draw2D.bottom;
		}
		if (i < j) {
			i1 = (j1 <<= 16);
			if (k < 0) {
				i1 -= i2 * k;
				j1 -= j2 * k;
				k = 0;
			}
			l <<= 16;
			if (i < 0) {
				l -= l1 * i;
				i = 0;
			}
			if (i2 < j2) {
				j -= i;
				i -= k;
				for (k = lineOffset[k]; --i >= 0; k += Draw2D.width) {
					method377(Draw2D.pixels, k, k1, i1 >> 16, j1 >> 16);
					i1 += i2;
					j1 += j2;
				}
				while (--j >= 0) {
					method377(Draw2D.pixels, k, k1, i1 >> 16, l >> 16);
					i1 += i2;
					l += l1;
					k += Draw2D.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for (k = lineOffset[k]; --i >= 0; k += Draw2D.width) {
				method377(Draw2D.pixels, k, k1, j1 >> 16, i1 >> 16);
				i1 += i2;
				j1 += j2;
			}
			while (--j >= 0) {
				method377(Draw2D.pixels, k, k1, l >> 16, i1 >> 16);
				i1 += i2;
				l += l1;
				k += Draw2D.width;
			}
			return;
		}
		l = (j1 <<= 16);
		if (k < 0) {
			l -= i2 * k;
			j1 -= j2 * k;
			k = 0;
		}
		i1 <<= 16;
		if (j < 0) {
			i1 -= l1 * j;
			j = 0;
		}
		if (i2 < j2) {
			i -= j;
			j -= k;
			for (k = lineOffset[k]; --j >= 0; k += Draw2D.width) {
				method377(Draw2D.pixels, k, k1, l >> 16, j1 >> 16);
				l += i2;
				j1 += j2;
			}
			while (--i >= 0) {
				method377(Draw2D.pixels, k, k1, i1 >> 16, j1 >> 16);
				i1 += l1;
				j1 += j2;
				k += Draw2D.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for (k = lineOffset[k]; --j >= 0; k += Draw2D.width) {
			method377(Draw2D.pixels, k, k1, j1 >> 16, l >> 16);
			l += i2;
			j1 += j2;
		}
		while (--i >= 0) {
			method377(Draw2D.pixels, k, k1, j1 >> 16, i1 >> 16);
			i1 += l1;
			j1 += j2;
			k += Draw2D.width;
		}
	}

	public static void method377(int[] dst, int dstOff, int rgb, int leftX, int rightX) {
		if (clipX) {
			if (rightX > Draw2D.boundX) {
				rightX = Draw2D.boundX;
			}
			if (leftX < 0) {
				leftX = 0;
			}
		}

		if (leftX >= rightX) {
			return;
		}

		dstOff += leftX;
		int length = (rightX - leftX) >> 2;

		if (Draw3D.alpha == 0) {
			while (--length >= 0) {
				dst[dstOff++] = rgb;
				dst[dstOff++] = rgb;
				dst[dstOff++] = rgb;
				dst[dstOff++] = rgb;
			}
			for (length = (rightX - leftX) & 3; --length >= 0; ) {
				dst[dstOff++] = rgb;
			}
			return;
		}

		int alpha = Draw3D.alpha;
		int invAlpha = 256 - Draw3D.alpha;

		rgb = ((((rgb & 0xff00ff) * invAlpha) >> 8) & 0xff00ff) + ((((rgb & 0xff00) * invAlpha) >> 8) & 0xff00);

		while (--length >= 0) {
			dst[dstOff++] = rgb + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
			dst[dstOff++] = rgb + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
			dst[dstOff++] = rgb + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
			dst[dstOff++] = rgb + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
		}

		for (length = (rightX - leftX) & 3; --length >= 0; ) {
			dst[dstOff++] = rgb + ((((dst[dstOff] & 0xff00ff) * alpha) >> 8) & 0xff00ff) + ((((dst[dstOff] & 0xff00) * alpha) >> 8) & 0xff00);
		}
	}

	public static void fillTexturedTriangle(int yA, int yB, int yC, int xA, int xB, int xC, int lightnessA, int lightnessB, int lightnessC, int viewXA, int viewXB, int viewXC, int viewYA, int viewYB, int viewYC, int viewZA, int viewZB, int viewZC, int textureId) {
		int[] texels = getTexels(textureId);
		opaque = !textureTranslucent[textureId];

		viewXB = viewXA - viewXB;
		viewYB = viewYA - viewYB;
		viewZB = viewZA - viewZB;

		viewXC -= viewXA;
		viewYC -= viewYA;
		viewZC -= viewZA;

		int l4 = ((viewXC * viewYA) - (viewYC * viewXA)) << 14;
		int i5 = ((viewYC * viewZA) - (viewZC * viewYA)) << 8;
		int j5 = ((viewZC * viewXA) - (viewXC * viewZA)) << 5;

		int k5 = ((viewXB * viewYA) - (viewYB * viewXA)) << 14;
		int l5 = ((viewYB * viewZA) - (viewZB * viewYA)) << 8;
		int i6 = ((viewZB * viewXA) - (viewXB * viewZA)) << 5;

		int j6 = ((viewYB * viewXC) - (viewXB * viewYC)) << 14;
		int k6 = ((viewZB * viewYC) - (viewYB * viewZC)) << 8;
		int l6 = ((viewXB * viewZC) - (viewZB * viewXC)) << 5;

		int xStepAB = 0;
		int xStepBC = 0;
		int xStepAC = 0;

		int lightnessStepAB = 0;
		int lightnessStepBC = 0;
		int lightnessStepAC = 0;

		if (yB != yA) {
			xStepAB = ((xB - xA) << 16) / (yB - yA);
			lightnessStepAB = ((lightnessB - lightnessA) << 16) / (yB - yA);
		}

		if (yC != yB) {
			xStepBC = ((xC - xB) << 16) / (yC - yB);
			lightnessStepBC = ((lightnessC - lightnessB) << 16) / (yC - yB);
		}

		if (yC != yA) {
			xStepAC = ((xA - xC) << 16) / (yA - yC);
			lightnessStepAC = ((lightnessA - lightnessC) << 16) / (yA - yC);
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
				lightnessC = (lightnessA <<= 16);
				if (yA < 0) {
					xC -= xStepAC * yA;
					xA -= xStepAB * yA;
					lightnessC -= lightnessStepAC * yA;
					lightnessA -= lightnessStepAB * yA;
					yA = 0;
				}
				xB <<= 16;
				lightnessB <<= 16;
				if (yB < 0) {
					xB -= xStepBC * yB;
					lightnessB -= lightnessStepBC * yB;
					yB = 0;
				}
				int k8 = yA - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (((yA != yB) && (xStepAC < xStepAB)) || ((yA == yB) && (xStepAC > xStepBC))) {
					yC -= yB;
					yB -= yA;
					yA = lineOffset[yA];
					while (--yB >= 0) {
						drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xA >> 16, lightnessC >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
						xC += xStepAC;
						xA += xStepAB;
						lightnessC += lightnessStepAC;
						lightnessA += lightnessStepAB;
						yA += Draw2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--yC >= 0) {
						drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xB >> 16, lightnessC >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
						xC += xStepAC;
						xB += xStepBC;
						lightnessC += lightnessStepAC;
						lightnessB += lightnessStepBC;
						yA += Draw2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				yC -= yB;
				yB -= yA;
				yA = lineOffset[yA];
				while (--yB >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xC >> 16, lightnessA >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
					xC += xStepAC;
					xA += xStepAB;
					lightnessC += lightnessStepAC;
					lightnessA += lightnessStepAB;
					yA += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--yC >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xB >> 16, xC >> 16, lightnessB >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
					xC += xStepAC;
					xB += xStepBC;
					lightnessC += lightnessStepAC;
					lightnessB += lightnessStepBC;
					yA += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			xB = (xA <<= 16);
			lightnessB = (lightnessA <<= 16);
			if (yA < 0) {
				xB -= xStepAC * yA;
				xA -= xStepAB * yA;
				lightnessB -= lightnessStepAC * yA;
				lightnessA -= lightnessStepAB * yA;
				yA = 0;
			}
			xC <<= 16;
			lightnessC <<= 16;
			if (yC < 0) {
				xC -= xStepBC * yC;
				lightnessC -= lightnessStepBC * yC;
				yC = 0;
			}
			int l8 = yA - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (((yA != yC) && (xStepAC < xStepAB)) || ((yA == yC) && (xStepBC > xStepAB))) {
				yB -= yC;
				yC -= yA;
				yA = lineOffset[yA];
				while (--yC >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xB >> 16, xA >> 16, lightnessB >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
					xB += xStepAC;
					xA += xStepAB;
					lightnessB += lightnessStepAC;
					lightnessA += lightnessStepAB;
					yA += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--yB >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xC >> 16, xA >> 16, lightnessC >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
					xC += xStepBC;
					xA += xStepAB;
					lightnessC += lightnessStepBC;
					lightnessA += lightnessStepAB;
					yA += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			yB -= yC;
			yC -= yA;
			yA = lineOffset[yA];
			while (--yC >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xB >> 16, lightnessA >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
				xB += xStepAC;
				xA += xStepAB;
				lightnessB += lightnessStepAC;
				lightnessA += lightnessStepAB;
				yA += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--yB >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yA, xA >> 16, xC >> 16, lightnessA >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
				xC += xStepBC;
				xA += xStepAB;
				lightnessC += lightnessStepBC;
				lightnessA += lightnessStepAB;
				yA += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
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
				lightnessA = (lightnessB <<= 16);
				if (yB < 0) {
					xA -= xStepAB * yB;
					xB -= xStepBC * yB;
					lightnessA -= lightnessStepAB * yB;
					lightnessB -= lightnessStepBC * yB;
					yB = 0;
				}
				xC <<= 16;
				lightnessC <<= 16;
				if (yC < 0) {
					xC -= xStepAC * yC;
					lightnessC -= lightnessStepAC * yC;
					yC = 0;
				}
				int i9 = yB - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (((yB != yC) && (xStepAB < xStepBC)) || ((yB == yC) && (xStepAB > xStepAC))) {
					yA -= yC;
					yC -= yB;
					yB = lineOffset[yB];
					while (--yC >= 0) {
						drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xB >> 16, lightnessA >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
						xA += xStepAB;
						xB += xStepBC;
						lightnessA += lightnessStepAB;
						lightnessB += lightnessStepBC;
						yB += Draw2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--yA >= 0) {
						drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xC >> 16, lightnessA >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
						xA += xStepAB;
						xC += xStepAC;
						lightnessA += lightnessStepAB;
						lightnessC += lightnessStepAC;
						yB += Draw2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				yA -= yC;
				yC -= yB;
				yB = lineOffset[yB];
				while (--yC >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xA >> 16, lightnessB >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
					xA += xStepAB;
					xB += xStepBC;
					lightnessA += lightnessStepAB;
					lightnessB += lightnessStepBC;
					yB += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--yA >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xC >> 16, xA >> 16, lightnessC >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
					xA += xStepAB;
					xC += xStepAC;
					lightnessA += lightnessStepAB;
					lightnessC += lightnessStepAC;
					yB += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			xC = (xB <<= 16);
			lightnessC = (lightnessB <<= 16);
			if (yB < 0) {
				xC -= xStepAB * yB;
				xB -= xStepBC * yB;
				lightnessC -= lightnessStepAB * yB;
				lightnessB -= lightnessStepBC * yB;
				yB = 0;
			}
			xA <<= 16;
			lightnessA <<= 16;
			if (yA < 0) {
				xA -= xStepAC * yA;
				lightnessA -= lightnessStepAC * yA;
				yA = 0;
			}
			int j9 = yB - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (xStepAB < xStepBC) {
				yC -= yA;
				yA -= yB;
				yB = lineOffset[yB];
				while (--yA >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xC >> 16, xB >> 16, lightnessC >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
					xC += xStepAB;
					xB += xStepBC;
					lightnessC += lightnessStepAB;
					lightnessB += lightnessStepBC;
					yB += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--yC >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xA >> 16, xB >> 16, lightnessA >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
					xA += xStepAC;
					xB += xStepBC;
					lightnessA += lightnessStepAC;
					lightnessB += lightnessStepBC;
					yB += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			yC -= yA;
			yA -= yB;
			yB = lineOffset[yB];
			while (--yA >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xC >> 16, lightnessB >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
				xC += xStepAB;
				xB += xStepBC;
				lightnessC += lightnessStepAB;
				lightnessB += lightnessStepBC;
				yB += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--yC >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yB, xB >> 16, xA >> 16, lightnessB >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
				xA += xStepAC;
				xB += xStepBC;
				lightnessA += lightnessStepAC;
				lightnessB += lightnessStepBC;
				yB += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
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
			lightnessB = (lightnessC <<= 16);
			if (yC < 0) {
				xB -= xStepBC * yC;
				xC -= xStepAC * yC;
				lightnessB -= lightnessStepBC * yC;
				lightnessC -= lightnessStepAC * yC;
				yC = 0;
			}
			xA <<= 16;
			lightnessA <<= 16;
			if (yA < 0) {
				xA -= xStepAB * yA;
				lightnessA -= lightnessStepAB * yA;
				yA = 0;
			}
			int k9 = yC - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (xStepBC < xStepAC) {
				yB -= yA;
				yA -= yC;
				yC = lineOffset[yC];
				while (--yA >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xC >> 16, lightnessB >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
					xB += xStepBC;
					xC += xStepAC;
					lightnessB += lightnessStepBC;
					lightnessC += lightnessStepAC;
					yC += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--yB >= 0) {
					drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xA >> 16, lightnessB >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
					xB += xStepBC;
					xA += xStepAB;
					lightnessB += lightnessStepBC;
					lightnessA += lightnessStepAB;
					yC += Draw2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			yB -= yA;
			yA -= yC;
			yC = lineOffset[yC];
			while (--yA >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xB >> 16, lightnessC >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
				xB += xStepBC;
				xC += xStepAC;
				lightnessB += lightnessStepBC;
				lightnessC += lightnessStepAC;
				yC += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--yB >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xA >> 16, xB >> 16, lightnessA >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
				xB += xStepBC;
				xA += xStepAB;
				lightnessB += lightnessStepBC;
				lightnessA += lightnessStepAB;
				yC += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		xA = (xC <<= 16);
		lightnessA = (lightnessC <<= 16);
		if (yC < 0) {
			xA -= xStepBC * yC;
			xC -= xStepAC * yC;
			lightnessA -= lightnessStepBC * yC;
			lightnessC -= lightnessStepAC * yC;
			yC = 0;
		}
		xB <<= 16;
		lightnessB <<= 16;
		if (yB < 0) {
			xB -= xStepAB * yB;
			lightnessB -= lightnessStepAB * yB;
			yB = 0;
		}
		int l9 = yC - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (xStepBC < xStepAC) {
			yA -= yB;
			yB -= yC;
			yC = lineOffset[yC];
			while (--yB >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xA >> 16, xC >> 16, lightnessA >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
				xA += xStepBC;
				xC += xStepAC;
				lightnessA += lightnessStepBC;
				lightnessC += lightnessStepAC;
				yC += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--yA >= 0) {
				drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xB >> 16, xC >> 16, lightnessB >> 8, lightnessC >> 8, l4, k5, j6, i5, l5, k6);
				xB += xStepAB;
				xC += xStepAC;
				lightnessB += lightnessStepAB;
				lightnessC += lightnessStepAC;
				yC += Draw2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		yA -= yB;
		yB -= yC;
		yC = lineOffset[yC];
		while (--yB >= 0) {
			drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xA >> 16, lightnessC >> 8, lightnessA >> 8, l4, k5, j6, i5, l5, k6);
			xA += xStepBC;
			xC += xStepAC;
			lightnessA += lightnessStepBC;
			lightnessC += lightnessStepAC;
			yC += Draw2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--yA >= 0) {
			drawTexturedScanline(Draw2D.pixels, texels, 0, 0, yC, xC >> 16, xB >> 16, lightnessC >> 8, lightnessB >> 8, l4, k5, j6, i5, l5, k6);
			xB += xStepAB;
			xC += xStepAC;
			lightnessB += lightnessStepAB;
			lightnessC += lightnessStepAC;
			yC += Draw2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	public static void drawTexturedScanline(int[] ai, int[] ai1, int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3) {
		if (l >= i1) {
			return;
		}
		int j3;
		int k3;
		if (clipX) {
			j3 = (k1 - j1) / (i1 - l);
			if (i1 > Draw2D.boundX) {
				i1 = Draw2D.boundX;
			}
			if (l < 0) {
				j1 -= l * j3;
				l = 0;
			}
			if (l >= i1) {
				return;
			}
			k3 = (i1 - l) >> 3;
			j3 <<= 12;
		} else {
			if ((i1 - l) > 7) {
				k3 = (i1 - l) >> 3;
				j3 = ((k1 - j1) * reciprical15[k3]) >> 6;
			} else {
				k3 = 0;
				j3 = 0;
			}
		}
		j1 <<= 9;
		k += l;
		if (lowmem) {
			int i4 = 0;
			int k4 = 0;
			int k6 = l - centerX;
			l1 += (k2 >> 3) * k6;
			i2 += (l2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if (i5 != 0) {
				i = l1 / i5;
				j = i2 / i5;
				if (i < 0) {
					i = 0;
				} else if (i > 4032) {
					i = 4032;
				}
			}
			l1 += k2;
			i2 += l2;
			j2 += i3;
			i5 = j2 >> 12;
			if (i5 != 0) {
				i4 = l1 / i5;
				k4 = i2 / i5;
				if (i4 < 7) {
					i4 = 7;
				} else if (i4 > 4032) {
					i4 = 4032;
				}
			}
			int i7 = (i4 - i) >> 3;
			int k7 = (k4 - j) >> 3;
			i += (j1 & 0x600000) >> 3;
			int i8 = j1 >> 23;
			if (opaque) {
				while (k3-- > 0) {
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i = i4;
					j = k4;
					l1 += k2;
					i2 += l2;
					j2 += i3;
					int j5 = j2 >> 12;
					if (j5 != 0) {
						i4 = l1 / j5;
						k4 = i2 / j5;
						if (i4 < 7) {
							i4 = 7;
						} else if (i4 > 4032) {
							i4 = 4032;
						}
					}
					i7 = (i4 - i) >> 3;
					k7 = (k4 - j) >> 3;
					j1 += j3;
					i += (j1 & 0x600000) >> 3;
					i8 = j1 >> 23;
				}
				for (k3 = (i1 - l) & 7; k3-- > 0; ) {
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
				}
				return;
			}
			while (k3-- > 0) {
				int k8;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = k8;
				}
				k++;
				i = i4;
				j = k4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int k5 = j2 >> 12;
				if (k5 != 0) {
					i4 = l1 / k5;
					k4 = i2 / k5;
					if (i4 < 7) {
						i4 = 7;
					} else if (i4 > 4032) {
						i4 = 4032;
					}
				}
				i7 = (i4 - i) >> 3;
				k7 = (k4 - j) >> 3;
				j1 += j3;
				i += (j1 & 0x600000) >> 3;
				i8 = j1 >> 23;
			}
			for (k3 = (i1 - l) & 7; k3-- > 0; ) {
				int l8;
				if ((l8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0) {
					ai[k] = l8;
				}
				k++;
				i += i7;
				j += k7;
			}
			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = l - centerX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0) {
				i = 0;
			} else if (i > 16256) {
				i = 16256;
			}
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7) {
				j4 = 7;
			} else if (j4 > 16256) {
				j4 = 16256;
			}
		}
		int j7 = (j4 - i) >> 3;
		int l7 = (l4 - j) >> 3;
		i += j1 & 0x600000;
		int j8 = j1 >> 23;
		if (opaque) {
			while (k3-- > 0) {
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i = j4;
				j = l4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int i6 = j2 >> 14;
				if (i6 != 0) {
					j4 = l1 / i6;
					l4 = i2 / i6;
					if (j4 < 7) {
						j4 = 7;
					} else if (j4 > 16256) {
						j4 = 16256;
					}
				}
				j7 = (j4 - i) >> 3;
				l7 = (l4 - j) >> 3;
				j1 += j3;
				i += j1 & 0x600000;
				j8 = j1 >> 23;
			}
			for (k3 = (i1 - l) & 7; k3-- > 0; ) {
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
			}
			return;
		}
		while (k3-- > 0) {
			int i9;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = i9;
			}
			k++;
			i = j4;
			j = l4;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = l1 / j6;
				l4 = i2 / j6;
				if (j4 < 7) {
					j4 = 7;
				} else if (j4 > 16256) {
					j4 = 16256;
				}
			}
			j7 = (j4 - i) >> 3;
			l7 = (l4 - j) >> 3;
			j1 += j3;
			i += j1 & 0x600000;
			j8 = j1 >> 23;
		}
		for (int l3 = (i1 - l) & 7; l3-- > 0; ) {
			int j9;
			if ((j9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0) {
				ai[k] = j9;
			}
			k++;
			i += j7;
			j += l7;
		}
	}

}
