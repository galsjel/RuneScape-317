import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.IOException;

public class Image24 extends DoublyLinkedList.Node {

	public int[] pixels;
	public int width;
	public int height;
	public int cropX;
	public int cropY;
	public int cropW;
	public int cropH;

	public Image24(int i, int j) {
		pixels = new int[i * j];
		width = cropW = i;
		height = cropH = j;
		cropX = cropY = 0;
	}

	public Image24(byte[] abyte0, java.awt.Component component) {
		try {
			java.awt.Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
			MediaTracker mediatracker = new MediaTracker(component);
			mediatracker.addImage(image, 0);
			mediatracker.waitForAll();
			width = image.getWidth(component);
			height = image.getHeight(component);
			cropW = width;
			cropH = height;
			cropX = 0;
			cropY = 0;
			pixels = new int[width * height];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
			pixelgrabber.grabPixels();
		} catch (Exception _ex) {
			System.out.println("Error converting jpg");
		}
	}

	public Image24(FileArchive archive, String file, int index) throws IOException {
		Buffer buffer = new Buffer(archive.read(file + ".dat"));
		Buffer buffer_1 = new Buffer(archive.read("index.dat"));
		buffer_1.position = buffer.get2U();
		cropW = buffer_1.get2U();
		cropH = buffer_1.get2U();
		int j = buffer_1.get1U();
		int[] ai = new int[j];
		for (int k = 0; k < (j - 1); k++) {
			ai[k + 1] = buffer_1.get3();
			if (ai[k + 1] == 0) {
				ai[k + 1] = 1;
			}
		}
		for (int l = 0; l < index; l++) {
			buffer_1.position += 2;
			buffer.position += buffer_1.get2U() * buffer_1.get2U();
			buffer_1.position++;
		}
		cropX = buffer_1.get1U();
		cropY = buffer_1.get1U();
		width = buffer_1.get2U();
		height = buffer_1.get2U();
		int layout = buffer_1.get1U();
		int pixelLen = width * height;
		pixels = new int[pixelLen];
		if (layout == 0) {
			for (int k1 = 0; k1 < pixelLen; k1++) {
				pixels[k1] = ai[buffer.get1U()];
			}
			return;
		}
		if (layout == 1) {
			for (int l1 = 0; l1 < width; l1++) {
				for (int i2 = 0; i2 < height; i2++) {
					pixels[l1 + (i2 * width)] = ai[buffer.get1U()];
				}
			}
		}
	}

	public void bind() {
		Draw2D.bind(pixels, width, height);
	}

	public void translate(int r, int g, int b) {
		for (int i = 0; i < pixels.length; i++) {
			int rgb = pixels[i];
			if (rgb != 0) {
				int red = (rgb >> 16) & 0xff;
				red += r;
				if (red < 1) {
					red = 1;
				} else if (red > 255) {
					red = 255;
				}
				int green = (rgb >> 8) & 0xff;
				green += g;
				if (green < 1) {
					green = 1;
				} else if (green > 255) {
					green = 255;
				}
				int blue = rgb & 0xff;
				blue += b;
				if (blue < 1) {
					blue = 1;
				} else if (blue > 255) {
					blue = 255;
				}
				pixels[i] = (red << 16) + (green << 8) + blue;
			}
		}
	}

	public void crop() {
		int[] pixels = new int[cropW * cropH];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[((y + cropY) * cropW) + (x + cropX)] = this.pixels[(y * width) + x];
			}
		}
		this.pixels = pixels;
		width = cropW;
		height = cropH;
		cropX = 0;
		cropY = 0;
	}

	public void blitOpaque(int x, int y) {
		x += cropX;
		y += cropY;
		int dstOff = x + (y * Draw2D.width);
		int srcOff = 0;
		int h = height;
		int w = width;
		int dstStep = Draw2D.width - w;
		int srcStep = 0;
		if (y < Draw2D.top) {
			int trim = Draw2D.top - y;
			h -= trim;
			y = Draw2D.top;
			srcOff += trim * w;
			dstOff += trim * Draw2D.width;
		}
		if ((y + h) > Draw2D.bottom) {
			h -= (y + h) - Draw2D.bottom;
		}
		if (x < Draw2D.left) {
			int trim = Draw2D.left - x;
			w -= trim;
			x = Draw2D.left;
			srcOff += trim;
			dstOff += trim;
			srcStep += trim;
			dstStep += trim;
		}
		if ((x + w) > Draw2D.right) {
			int trim = (x + w) - Draw2D.right;
			w -= trim;
			srcStep += trim;
			dstStep += trim;
		}
		if ((w > 0) && (h > 0)) {
			copyPixels(dstOff, w, h, srcStep, srcOff, dstStep, pixels, Draw2D.pixels);
		}
	}

	public void copyPixels(int dstOff, int w, int h, int srcStep, int srcOff, int dstStep, int[] src, int[] dst) {
		int quarterWidth = -(w >> 2);
		w = -(w & 3);
		for (int i2 = -h; i2 < 0; i2++) {
			for (int j2 = quarterWidth; j2 < 0; j2++) {
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
			}
			for (int k2 = w; k2 < 0; k2++) {
				dst[dstOff++] = src[srcOff++];
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	public void draw(int x, int y) {
		x += cropX;
		y += cropY;
		int dstOff = x + (y * Draw2D.width);
		int srcOff = 0;
		int h = height;
		int w = width;
		int dstStep = Draw2D.width - w;
		int srcStep = 0;
		if (y < Draw2D.top) {
			int j2 = Draw2D.top - y;
			h -= j2;
			y = Draw2D.top;
			srcOff += j2 * w;
			dstOff += j2 * Draw2D.width;
		}
		if ((y + h) > Draw2D.bottom) {
			h -= (y + h) - Draw2D.bottom;
		}
		if (x < Draw2D.left) {
			int k2 = Draw2D.left - x;
			w -= k2;
			x = Draw2D.left;
			srcOff += k2;
			dstOff += k2;
			srcStep += k2;
			dstStep += k2;
		}
		if ((x + w) > Draw2D.right) {
			int trim = (x + w) - Draw2D.right;
			w -= trim;
			srcStep += trim;
			dstStep += trim;
		}
		if ((w > 0) && (h > 0)) {
			copyPixels(Draw2D.pixels, pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	public void copyPixels(int[] dst, int[] src, int srcOff, int dstOff, int w, int h, int dstStep, int srcstep) {
		int quarterW = -(w >> 2);
		w = -(w & 3);
		for (int y = -h; y < 0; y++) {
			int rgb;
			for (int x = quarterW; x < 0; x++) {
				rgb = src[srcOff++];
				if (rgb != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if (rgb != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if (rgb != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if (rgb != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			for (int k2 = w; k2 < 0; k2++) {
				rgb = src[srcOff++];
				if (rgb != 0) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			dstOff += dstStep;
			srcOff += srcstep;
		}
	}

	public void draw(int x, int y, int alpha) {
		x += cropX;
		y += cropY;
		int i1 = x + (y * Draw2D.width);
		int j1 = 0;
		int k1 = height;
		int w = width;
		int i2 = Draw2D.width - w;
		int j2 = 0;
		if (y < Draw2D.top) {
			int k2 = Draw2D.top - y;
			k1 -= k2;
			y = Draw2D.top;
			j1 += k2 * w;
			i1 += k2 * Draw2D.width;
		}
		if ((y + k1) > Draw2D.bottom) {
			k1 -= (y + k1) - Draw2D.bottom;
		}
		if (x < Draw2D.left) {
			int l2 = Draw2D.left - x;
			w -= l2;
			x = Draw2D.left;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if ((x + w) > Draw2D.right) {
			int i3 = (x + w) - Draw2D.right;
			w -= i3;
			j2 += i3;
			i2 += i3;
		}
		if ((w > 0) && (k1 > 0)) {
			copyPixelsAlpha(j1, w, Draw2D.pixels, pixels, j2, k1, i2, alpha, i1);
		}
	}

	public void copyPixelsAlpha(int srcOff, int w, int[] dst, int[] src, int srcstep, int h, int dstStep, int alpha, int dstOff) {
		int invAlpha = 256 - alpha;
		for (int k2 = -h; k2 < 0; k2++) {
			for (int l2 = -w; l2 < 0; l2++) {
				int srcRGB = src[srcOff++];
				if (srcRGB != 0) {
					int dstRGB = dst[dstOff];
					dst[dstOff++] = (((((srcRGB & 0xff00ff) * alpha) + ((dstRGB & 0xff00ff) * invAlpha)) & 0xff00ff00) + ((((srcRGB & 0xff00) * alpha) + ((dstRGB & 0xff00) * invAlpha)) & 0xff0000)) >> 8;
				} else {
					dstOff++;
				}
			}
			dstOff += dstStep;
			srcOff += srcstep;
		}
	}

	public void drawRotatedMasked(int x, int y, int width, int height, int anchorX, int anchorY, int zoom, int angle, int[] lineLengths, int[] lineOffsets) {
		try {
			int midX = -width / 2;
			int midY = -height / 2;
			int sin = (int) (Math.sin((double) angle / 326.11000000000001D) * 65536D);
			int cos = (int) (Math.cos((double) angle / 326.11000000000001D) * 65536D);
			sin = (sin * zoom) >> 8;
			cos = (cos * zoom) >> 8;
			int leftX = (anchorX << 16) + ((midY * sin) + (midX * cos));
			int lefty = (anchorY << 16) + ((midY * cos) - (midX * sin));
			int leftOff = x + (y * Draw2D.width);
			for (y = 0; y < height; y++) {
				int lineOffset = lineOffsets[y];
				int dstOff = leftOff + lineOffset;
				int srcX = leftX + (cos * lineOffset);
				int srcY = lefty - (sin * lineOffset);
				for (x = -lineLengths[y]; x < 0; x++) {
					Draw2D.pixels[dstOff++] = pixels[(srcX >> 16) + ((srcY >> 16) * this.width)];
					srcX += cos;
					srcY -= sin;
				}
				leftX += sin;
				lefty += cos;
				leftOff += Draw2D.width;
			}
		} catch (Exception ignored) {
		}
	}

	public void drawRotated(int x, int y, int width, int height, int anchorX, int anchorY, double radians, int zoom) {
		try {
			int centerX = -width / 2;
			int centerY = -height / 2;
			int sin = (int) (Math.sin(radians) * 65536D);
			int cos = (int) (Math.cos(radians) * 65536D);
			sin = (sin * zoom) >> 8;
			cos = (cos * zoom) >> 8;
			int leftX = (anchorX << 16) + ((centerY * sin) + (centerX * cos));
			int leftY = (anchorY << 16) + ((centerY * cos) - (centerX * sin));
			int leftOff = x + (y * Draw2D.width);

			for (y = 0; y < height; y++) {
				int dstOff = leftOff;
				int dstX = leftX;
				int dstY = leftY;
				for (x = -width; x < 0; x++) {
					int rgb = pixels[(dstX >> 16) + ((dstY >> 16) * this.width)];
					if (rgb != 0) {
						Draw2D.pixels[dstOff++] = rgb;
					} else {
						dstOff++;
					}
					dstX += cos;
					dstY -= sin;
				}
				leftX += sin;
				leftY += cos;
				leftOff += Draw2D.width;
			}
		} catch (Exception ignored) {
		}
	}

	public void drawMasked(Image8 mask, int y, int x) {
		x += cropX;
		y += cropY;
		int dstOff = x + (y * Draw2D.width);
		int srcOff = 0;
		int h = height;
		int w = width;
		int dstStep = Draw2D.width - w;
		int srcStep = 0;
		if (y < Draw2D.top) {
			int trim = Draw2D.top - y;
			h -= trim;
			y = Draw2D.top;
			srcOff += trim * w;
			dstOff += trim * Draw2D.width;
		}
		if ((y + h) > Draw2D.bottom) {
			h -= (y + h) - Draw2D.bottom;
		}
		if (x < Draw2D.left) {
			int trim = Draw2D.left - x;
			w -= trim;
			x = Draw2D.left;
			srcOff += trim;
			dstOff += trim;
			srcStep += trim;
			dstStep += trim;
		}
		if ((x + w) > Draw2D.right) {
			int trim = (x + w) - Draw2D.right;
			w -= trim;
			srcStep += trim;
			dstStep += trim;
		}
		if ((w > 0) && (h > 0)) {
			copyPixelsMasked(pixels, srcOff, srcStep, mask.pixels, w, h, Draw2D.pixels, dstOff, dstStep);
		}
	}

	public void copyPixelsMasked(int[] src, int srcOff, int srcStep, byte[] mask, int w, int h, int[] dst, int dstOff, int dstStep) {
		int quarterW = -(w >> 2);
		w = -(w & 3);
		for (int y = -h; y < 0; y++) {
			int rgb;
			for (int k2 = quarterW; k2 < 0; k2++) {
				rgb = src[srcOff++];
				if ((rgb != 0) && (mask[dstOff] == 0)) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if ((rgb != 0) && (mask[dstOff] == 0)) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if ((rgb != 0) && (mask[dstOff] == 0)) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
				rgb = src[srcOff++];
				if ((rgb != 0) && (mask[dstOff] == 0)) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			for (int l2 = w; l2 < 0; l2++) {
				rgb = src[srcOff++];
				if ((rgb != 0) && (mask[dstOff] == 0)) {
					dst[dstOff++] = rgb;
				} else {
					dstOff++;
				}
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

}
