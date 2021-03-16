import java.io.IOException;
import java.util.Random;

public class BitmapFont {

	public final byte[][] charMask = new byte[256][];
	public final int[] charMaskWidth = new int[256];
	public final int[] charMaskHeight = new int[256];
	public final int[] charOffsetX = new int[256];
	public final int[] charOffsetY = new int[256];
	public final int[] charAdvance = new int[256];
	public final Random random = new Random();
	public int height;
	public boolean strikethrough = false;

	public BitmapFont(FileArchive archive, String name, boolean quill) throws IOException {
		Buffer dat = new Buffer(archive.read(name + ".dat"));
		Buffer idx = new Buffer(archive.read("index.dat"));
		idx.position = dat.get2U() + 4;

		int k = idx.get1U();

		if (k > 0) {
			idx.position += 3 * (k - 1);
		}

		for (int c = 0; c < 256; c++) {
			charOffsetX[c] = idx.get1U();
			charOffsetY[c] = idx.get1U();

			int w = charMaskWidth[c] = idx.get2U();
			int h = charMaskHeight[c] = idx.get2U();
			int storeOrder = idx.get1U();

			int len = w * h;
			charMask[c] = new byte[len];

			if (storeOrder == 0) {
				for (int i = 0; i < len; i++) {
					charMask[c][i] = dat.get1();
				}
			} else if (storeOrder == 1) {
				for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						charMask[c][x + (y * w)] = dat.get1();
					}
				}
			}

			if ((h > height) && (c < 128)) {
				height = h;
			}

			// some simple kerning
			// https://en.wikipedia.org/wiki/Kerning

			charOffsetX[c] = 1;
			charAdvance[c] = w + 2;

			int acc = 0;

			for (int y = h / 7; y < h; y++) {
				acc += charMask[c][y * w];
			}

			if (acc <= (h / 7)) {
				charAdvance[c]--;
				charOffsetX[c] = 0;
			}

			acc = 0;

			for (int y = h / 7; y < h; y++) {
				acc += charMask[c][(w - 1) + (y * w)];
			}

			if (acc <= (h / 7)) {
				charAdvance[c]--;
			}
		}

		// only q8_full uses this flag.
		if (quill) {
			charAdvance[' '] = charAdvance['I'];
		} else {
			charAdvance[' '] = charAdvance['i'];
		}
	}

	/**
	 * Draws a right aligned string. <b>Note:</b> This method is not taggable.
	 *
	 * @param s   the string.
	 * @param x   the x.
	 * @param y   the y.
	 * @param rgb the rgb.
	 */
	public void drawStringRight(String s, int x, int y, int rgb) {
		drawString(s, x - stringWidth(s), y, rgb);
	}

	/**
	 * Draws a centered string. <b>Note:</b> This method is not taggable.
	 *
	 * @param s   the string.
	 * @param x   the x.
	 * @param y   the y.
	 * @param rgb the rgb.
	 */
	public void drawStringCenter(String s, int x, int y, int rgb) {
		drawString(s, x - (stringWidth(s) / 2), y, rgb);
	}

	/**
	 * Draws a centered and taggable string.
	 *
	 * @param s      the string.
	 * @param x      the center x.
	 * @param y      the y.
	 * @param rgb    the rgb.
	 * @param shadow <code>true</code> to draw with a shadow.
	 */
	public void drawStringTaggableCenter(String s, int x, int y, int rgb, boolean shadow) {
		drawStringTaggable(s, x - (stringWidthTaggable(s) / 2), y, rgb, shadow);
	}

	/**
	 * Calculates the string width.
	 *
	 * @param s the string.
	 * @return the string width.
	 */
	public int stringWidthTaggable(String s) {
		if (s == null) {
			return 0;
		}
		int w = 0;
		for (int k = 0; k < s.length(); k++) {
			if ((s.charAt(k) == '@') && ((k + 4) < s.length()) && (s.charAt(k + 4) == '@')) {
				k += 4;
			} else {
				w += charAdvance[s.charAt(k)];
			}
		}
		return w;
	}

	/**
	 * Calculates the string width. <b>Note:</b> This method is not taggable.
	 *
	 * @param s the string.
	 * @return the string width.
	 */
	public int stringWidth(String s) {
		if (s == null) {
			return 0;
		}
		int w = 0;
		for (int k = 0; k < s.length(); k++) {
			w += charAdvance[s.charAt(k)];
		}
		return w;
	}

	/**
	 * Standard draw string method. <b>Note:</b> This method is not taggable.
	 *
	 * @param s   the s.
	 * @param x   the x.
	 * @param y   the y.
	 * @param rgb the rgb.
	 */
	public void drawString(String s, int x, int y, int rgb) {
		if (s == null) {
			return;
		}
		y -= height;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				fillMaskedRect(charMask[c], x + charOffsetX[c], y + charOffsetY[c], charMaskWidth[c], charMaskHeight[c], rgb);
			}
			x += charAdvance[c];
		}
	}

	/**
	 * Draws a string with the wave effect. <b>Note:</b> This method is not taggable.
	 *
	 * @param s     the string.
	 * @param x     the x.
	 * @param y     the y.
	 * @param rgb   the rgb.
	 * @param cycle the cycle.
	 */
	public void drawStringWave(String s, int x, int y, int rgb, int cycle) {
		if (s == null) {
			return;
		}
		x -= stringWidth(s) / 2;
		y -= height;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				fillMaskedRect(charMask[c], x + charOffsetX[c], y + charOffsetY[c] + (int) (Math.sin(((double) i / 2D) + ((double) cycle / 5D)) * 5D), charMaskWidth[c], charMaskHeight[c], rgb);
			}
			x += charAdvance[c];
		}
	}

	/**
	 * Draws a string with the wave2 effect. <b>Note:</b> This method is not taggable.
	 *
	 * @param s     the string.
	 * @param x     the x.
	 * @param y     the y.
	 * @param rgb   the rgb.
	 * @param cycle the wave cycle.
	 */
	public void drawStringWave2(String s, int x, int y, int rgb, int cycle) {
		if (s == null) {
			return;
		}
		x -= stringWidth(s) / 2;
		y -= height;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				fillMaskedRect(charMask[c], x + charOffsetX[c] + (int) (Math.sin(((double) i / 5D) + ((double) cycle / 5D)) * 5D), y + charOffsetY[c] + (int) (Math.sin(((double) i / 3D) + ((double) cycle / 5D)) * 5D), charMaskWidth[c], charMaskHeight[c], rgb);
			}
			x += charAdvance[c];
		}
	}

	/**
	 * Draws a string with the shake effect. <b>Note:</b> This method is not taggable.
	 *
	 * @param s     the string.
	 * @param x     the x.
	 * @param y     the y.
	 * @param rgb   the rgb.
	 * @param cycle the shake cycle.
	 * @param phase the shake phase.
	 */
	public void drawStringShake(String s, int x, int y, int rgb, int cycle, int phase) {
		if (s == null) {
			return;
		}

		double amplitude = 7D - ((double) phase / 8D);

		if (amplitude < 0.0D) {
			amplitude = 0.0D;
		}

		x -= stringWidth(s) / 2;
		y -= height;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c != ' ') {
				fillMaskedRect(charMask[c], x + charOffsetX[c], y + charOffsetY[c] + (int) (Math.sin(((double) i / 1.5D) + (double) cycle) * amplitude), charMaskWidth[c], charMaskHeight[c], rgb);
			}

			x += charAdvance[c];
		}
	}

	/**
	 * Draws a taggable string.
	 *
	 * @param s        the string.
	 * @param x        the x.
	 * @param y        the y.
	 * @param rgb      the rgb.
	 * @param shadowed <code>true</code> to draw with a shadow.
	 * @see #evaluateTag(String)
	 */
	public void drawStringTaggable(String s, int x, int y, int rgb, boolean shadowed) {
		strikethrough = false;

		int leftX = x;

		if (s == null) {
			return;
		}

		y -= height;

		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) == '@') && ((i + 4) < s.length()) && (s.charAt(i + 4) == '@')) {
				int value = evaluateTag(s.substring(i + 1, i + 4));

				if (value != -1) {
					rgb = value;
				}

				i += 4;
			} else {
				char c = s.charAt(i);

				if (c != ' ') {
					if (shadowed) {
						fillMaskedRect(charMask[c], x + charOffsetX[c] + 1, y + charOffsetY[c] + 1, charMaskWidth[c], charMaskHeight[c], 0);
					}
					fillMaskedRect(charMask[c], x + charOffsetX[c], y + charOffsetY[c], charMaskWidth[c], charMaskHeight[c], rgb);
				}

				x += charAdvance[c];
			}
		}

		if (strikethrough) {
			Draw2D.drawLineX(leftX, y + (int) ((double) height * 0.7), x - leftX, 0x800000);
		}
	}

	/**
	 * Identical to {@link #drawStringTaggable(String, int, int, int, boolean)} with the exception of a random chance to
	 * advance a character by an additional pixel to prevent macro clients from detecting tooltip text easily.
	 *
	 * @param s        the string.
	 * @param x        the x.
	 * @param y        the y.
	 * @param rgb      the rgb.
	 * @param shadowed <code>true</code> to draw with a shadow.
	 * @param seed     the seed.
	 * @see #evaluateTag(String)
	 */
	public void drawStringTooltip(String s, int x, int y, int rgb, boolean shadowed, int seed) {
		if (s == null) {
			return;
		}
		random.setSeed(seed);
		int alpha = 192 + (random.nextInt() & 0x1f);
		y -= height;
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) == '@') && ((i + 4) < s.length()) && (s.charAt(i + 4) == '@')) {
				int value = evaluateTag(s.substring(i + 1, i + 4));
				if (value != -1) {
					rgb = value;
				}
				i += 4;
			} else {
				char c = s.charAt(i);
				if (c != ' ') {
					if (shadowed) {
						fillMaskedRect(charMask[c], x + charOffsetX[c] + 1, y + charOffsetY[c] + 1, charMaskWidth[c], charMaskHeight[c], 0, 192);
					}
					fillMaskedRect(charMask[c], x + charOffsetX[c], y + charOffsetY[c], charMaskWidth[c], charMaskHeight[c], rgb, alpha);
				}
				x += charAdvance[c];
				if ((random.nextInt() & 3) == 0) {
					x++;
				}
			}
		}
	}

	public int evaluateTag(String s) {
		switch (s) {
			case "red":
				return 0xff0000;
			case "gre":
				return 0xff00;
			case "blu":
				return 0xff;
			case "yel":
				return 0xffff00;
			case "cya":
				return 0xffff;
			case "mag":
				return 0xff00ff;
			case "whi":
				return 0xffffff;
			case "bla":
				return 0;
			case "lre":
				return 0xff9040;
			case "dre":
				return 0x800000;
			case "dbl":
				return 0x80;
			case "or1":
				return 0xffb000;
			case "or2":
				return 0xff7000;
			case "or3":
				return 0xff3000;
			case "gr1":
				return 0xc0ff00;
			case "gr2":
				return 0x80ff00;
			case "gr3":
				return 0x40ff00;
			case "str":
				strikethrough = true;
				break;
			case "end":
				strikethrough = false;
				break;
		}
		return -1;
	}

	public void fillMaskedRect(byte[] mask, int x, int y, int w, int h, int rgb) {
		int dstOff = x + (y * Draw2D.width);
		int dstStep = Draw2D.width - w;
		int maskStep = 0;
		int maskOff = 0;
		if (y < Draw2D.top) {
			int trim = Draw2D.top - y;
			h -= trim;
			y = Draw2D.top;
			maskOff += trim * w;
			dstOff += trim * Draw2D.width;
		}
		if ((y + h) >= Draw2D.bottom) {
			h -= ((y + h) - Draw2D.bottom) + 1;
		}
		if (x < Draw2D.left) {
			int trim = Draw2D.left - x;
			w -= trim;
			x = Draw2D.left;
			maskOff += trim;
			dstOff += trim;
			maskStep += trim;
			dstStep += trim;
		}
		if ((x + w) >= Draw2D.right) {
			int trim = ((x + w) - Draw2D.right) + 1;
			w -= trim;
			maskStep += trim;
			dstStep += trim;
		}
		if ((w > 0) && (h > 0)) {
			fillMaskedRect(mask, maskOff, maskStep, Draw2D.pixels, dstOff, dstStep, w, h, rgb);
		}
	}

	public void fillMaskedRect(byte[] mask, int x, int y, int w, int h, int rgb, int alpha) {
		int dstOff = x + (y * Draw2D.width);
		int dstStep = Draw2D.width - w;
		int maskStep = 0;
		int maskOff = 0;
		if (y < Draw2D.top) {
			int trim = Draw2D.top - y;
			h -= trim;
			y = Draw2D.top;
			maskOff += trim * w;
			dstOff += trim * Draw2D.width;
		}
		if ((y + h) >= Draw2D.bottom) {
			h -= ((y + h) - Draw2D.bottom) + 1;
		}
		if (x < Draw2D.left) {
			int trim = Draw2D.left - x;
			w -= trim;
			x = Draw2D.left;
			maskOff += trim;
			dstOff += trim;
			maskStep += trim;
			dstStep += trim;
		}
		if ((x + w) >= Draw2D.right) {
			int trim = ((x + w) - Draw2D.right) + 1;
			w -= trim;
			maskStep += trim;
			dstStep += trim;
		}
		if ((w <= 0) || (h <= 0)) {
			return;
		}
		fillMaskedRect(mask, maskOff, maskStep, Draw2D.pixels, dstOff, dstStep, w, h, rgb, alpha);
	}

	public void fillMaskedRect(byte[] mask, int maskOff, int maskStep, int[] dst, int dstOff, int dstStep, int w, int h, int rgb) {
		int halfW = -(w >> 2);
		w = -(w & 3);
		for (int y = -h; y < 0; y++) {
			for (int x = halfW; x < 0; x++) {
				if (mask[maskOff++] != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				if (mask[maskOff++] != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				if (mask[maskOff++] != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				if (mask[maskOff++] != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			for (int x = w; x < 0; x++) {
				if (mask[maskOff++] != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			dstOff += dstStep;
			maskOff += maskStep;
		}
	}

	public void fillMaskedRect(byte[] mask, int maskOff, int maskStep, int[] dst, int dstOff, int dstStep, int w, int h, int rgb, int alpha) {
		rgb = ((((rgb & 0xff00ff) * alpha) & 0xff00ff00) + (((rgb & 0xff00) * alpha) & 0xff0000)) >> 8;
		alpha = 256 - alpha;
		for (int y = -h; y < 0; y++) {
			for (int x = -w; x < 0; x++) {
				if (mask[maskOff++] != 0) {
					int dstRGB = dst[dstOff];
					dst[dstOff++] = (((((dstRGB & 0xff00ff) * alpha) & 0xff00ff00) + (((dstRGB & 0xff00) * alpha) & 0xff0000)) >> 8) + rgb;
				} else {
					dstOff++;
				}
			}
			dstOff += dstStep;
			maskOff += maskStep;
		}
	}

}
