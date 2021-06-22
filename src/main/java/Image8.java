import java.io.IOException;

public class Image8 {

	public final int[] palette;
	public byte[] pixels;
	public int width;
	public int height;
	public int cropX;
	public int cropY;
	public int cropW;
	public int cropH;

	public Image8(FileArchive archive, String s, int i) throws IOException {
		Buffer buffer = new Buffer(archive.read(s + ".dat"));
		Buffer buffer_1 = new Buffer(archive.read("index.dat"));
		buffer_1.position = buffer.get2U();
		cropW = buffer_1.get2U();
		cropH = buffer_1.get2U();
		int j = buffer_1.get1U();
		palette = new int[j];
		for (int k = 0; k < (j - 1); k++) {
			palette[k + 1] = buffer_1.get3();
		}
		for (int l = 0; l < i; l++) {
			buffer_1.position += 2;
			buffer.position += buffer_1.get2U() * buffer_1.get2U();
			buffer_1.position++;
		}
		cropX = buffer_1.get1U();
		cropY = buffer_1.get1U();
		width = buffer_1.get2U();
		height = buffer_1.get2U();
		int i1 = buffer_1.get1U();
		int j1 = width * height;
		pixels = new byte[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				pixels[k1] = buffer.get1();
			}
			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < width; l1++) {
				for (int i2 = 0; i2 < height; i2++) {
					pixels[l1 + (i2 * width)] = buffer.get1();
				}
			}
		}
	}

	public void shrink() {
		cropW /= 2;
		cropH /= 2;
		byte[] pixels = new byte[cropW * cropH];
		int i = 0;
		for (int j = 0; j < height; j++) {
			for (int k = 0; k < width; k++) {
				pixels[((k + cropX) >> 1) + (((j + cropY) >> 1) * cropW)] = this.pixels[i++];
			}
		}
		this.pixels = pixels;
		width = cropW;
		height = cropH;
		cropX = 0;
		cropY = 0;
	}

	public void crop() {
		if ((width == cropW) && (height == cropH)) {
			return;
		}
		byte[] pixels = new byte[cropW * cropH];
		int i = 0;
		for (int j = 0; j < height; j++) {
			for (int k = 0; k < width; k++) {
				pixels[k + cropX + ((j + cropY) * cropW)] = this.pixels[i++];
			}
		}
		this.pixels = pixels;
		width = cropW;
		height = cropH;
		cropX = 0;
		cropY = 0;
	}

	public void flipH() {
		byte[] pixels = new byte[width * height];
		int i = 0;
		for (int y = 0; y < height; y++) {
			for (int x = width - 1; x >= 0; x--) {
				pixels[i++] = this.pixels[x + (y * width)];
			}
		}
		this.pixels = pixels;
		cropX = cropW - width - cropX;
	}

	public void flipV() {
		byte[] pixels = new byte[width * height];
		int i = 0;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				pixels[i++] = this.pixels[x + (y * width)];
			}
		}
		this.pixels = pixels;
		cropY = cropH - height - cropY;
	}

	public void translate(int r, int g, int b) {
		for (int i = 0; i < palette.length; i++) {
			int red = (palette[i] >> 16) & 0xff;
			red += r;
			if (red < 0) {
				red = 0;
			} else if (red > 255) {
				red = 255;
			}
			int green = (palette[i] >> 8) & 0xff;
			green += g;
			if (green < 0) {
				green = 0;
			} else if (green > 255) {
				green = 255;
			}
			int blue = palette[i] & 0xff;
			blue += b;
			if (blue < 0) {
				blue = 0;
			} else if (blue > 255) {
				blue = 255;
			}
			palette[i] = (red << 16) + (green << 8) + blue;
		}
	}

	public void blit(int x, int y) {
		x += cropX;
		y += cropY;
		int l = x + (y * Draw2D.width);
		int i1 = 0;
		int j1 = height;
		int k1 = width;
		int l1 = Draw2D.width - k1;
		int i2 = 0;
		if (y < Draw2D.top) {
			int j2 = Draw2D.top - y;
			j1 -= j2;
			y = Draw2D.top;
			i1 += j2 * k1;
			l += j2 * Draw2D.width;
		}
		if ((y + j1) > Draw2D.bottom) {
			j1 -= (y + j1) - Draw2D.bottom;
		}
		if (x < Draw2D.left) {
			int k2 = Draw2D.left - x;
			k1 -= k2;
			x = Draw2D.left;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if ((x + k1) > Draw2D.right) {
			int l2 = (x + k1) - Draw2D.right;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if ((k1 > 0) && (j1 > 0)) {
			blit(j1, Draw2D.pixels, pixels, l1, l, k1, i1, palette, i2);
		}
	}

	public void blit(int i, int[] ai, byte[] abyte0, int j, int k, int l, int i1, int[] ai1, int j1) {
		int k1 = -(l >> 2);
		l = -(l & 3);
		for (int l1 = -i; l1 < 0; l1++) {
			for (int i2 = k1; i2 < 0; i2++) {
				byte byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
			}
			for (int j2 = l; j2 < 0; j2++) {
				byte byte2 = abyte0[i1++];
				if (byte2 != 0) {
					ai[k++] = ai1[byte2 & 0xff];
				} else {
					k++;
				}
			}
			k += j;
			i1 += j1;
		}
	}

}
