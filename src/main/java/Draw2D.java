import java.util.Arrays;

public class Draw2D {

	public static int[] pixels;
	public static int width;
	public static int height;
	public static int top;
	public static int bottom;
	public static int left;
	public static int right;
	public static int boundX;
	public static int centerX;
	public static int centerY;

	public static void bind(int[] pixels, int width, int height) {
		Draw2D.pixels = pixels;
		Draw2D.width = width;
		Draw2D.height = height;
		setBounds(0, 0, width, height);
	}

	public static void resetBounds() {
		left = 0;
		top = 0;
		right = width;
		bottom = height;
		boundX = right - 1;
		centerX = right / 2;
	}

	public static void setBounds(int left, int top, int right, int bottom) {
		if (left < 0) {
			left = 0;
		}
		if (top < 0) {
			top = 0;
		}
		if (right > width) {
			right = width;
		}
		if (bottom > height) {
			bottom = height;
		}
		Draw2D.left = left;
		Draw2D.top = top;
		Draw2D.right = right;
		Draw2D.bottom = bottom;
		boundX = Draw2D.right - 1;
		centerX = Draw2D.right / 2;
		centerY = Draw2D.bottom / 2;
	}

	public static void clear() {
		Arrays.fill(pixels, 0);
	}

	public static void fillRect(int x, int y, int width, int height, int rgb, int alpha) {
		if (x < left) {
			width -= left - x;
			x = left;
		}

		if (y < top) {
			height -= top - y;
			y = top;
		}

		if ((x + width) > right) {
			width = right - x;
		}

		if ((y + height) > bottom) {
			height = bottom - y;
		}

		int invAlpha = 256 - alpha;
		int r0 = ((rgb >> 16) & 0xff) * alpha;
		int g0 = ((rgb >> 8) & 0xff) * alpha;
		int b0 = (rgb & 0xff) * alpha;

		int step = Draw2D.width - width;
		int offset = x + (y * Draw2D.width);

		for (int i = 0; i < height; i++) {
			for (int j = -width; j < 0; j++) {
				int r1 = ((pixels[offset] >> 16) & 0xff) * invAlpha;
				int g1 = ((pixels[offset] >> 8) & 0xff) * invAlpha;
				int b1 = (pixels[offset] & 0xff) * invAlpha;
				pixels[offset++] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
			}
			offset += step;
		}
	}

	public static void fillRect(int x, int y, int width, int height, int rgb) {
		if (x < left) {
			width -= left - x;
			x = left;
		}

		if (y < top) {
			height -= top - y;
			y = top;
		}

		if ((x + width) > right) {
			width = right - x;
		}

		if ((y + height) > bottom) {
			height = bottom - y;
		}

		int step = Draw2D.width - width;
		int offset = x + (y * Draw2D.width);

		for (int i = -height; i < 0; i++) {
			for (int j = -width; j < 0; j++) {
				pixels[offset++] = rgb;
			}
			offset += step;
		}
	}

	public static void drawRect(int x, int y, int width, int height, int rgb) {
		drawLineX(x, y, width, rgb);
		drawLineX(x, (y + height) - 1, width, rgb);
		drawLineY(x, y, height, rgb);
		drawLineY((x + width) - 1, y, height, rgb);
	}

	public static void drawRect(int x, int y, int width, int height, int rgb, int alpha) {
		drawLineX(x, y, width, rgb, alpha);
		drawLineX(x, (y + height) - 1, width, rgb, alpha);
		if (height >= 3) {
			drawLineY(rgb, x, alpha, y + 1, height - 2);
			drawLineY(rgb, (x + width) - 1, alpha, y + 1, height - 2);
		}
	}

	public static void drawLineX(int x, int y, int length, int rgb) {
		if ((y < top) || (y >= bottom)) {
			return;
		}

		if (x < left) {
			length -= left - x;
			x = left;
		}

		if ((x + length) > right) {
			length = right - x;
		}

		int offset = x + (y * width);

		for (int i = 0; i < length; i++) {
			pixels[offset + i] = rgb;
		}
	}

	public static void drawLineX(int x, int y, int length, int rgb, int alpha) {
		if ((y < top) || (y >= bottom)) {
			return;
		}

		if (x < left) {
			length -= left - x;
			x = left;
		}

		if ((x + length) > right) {
			length = right - x;
		}

		int invAlpha = 256 - alpha;
		int r0 = ((rgb >> 16) & 0xff) * alpha;
		int g0 = ((rgb >> 8) & 0xff) * alpha;
		int b0 = (rgb & 0xff) * alpha;

		int offset = x + (y * width);

		for (int i = 0; i < length; i++) {
			int r1 = ((pixels[offset] >> 16) & 0xff) * invAlpha;
			int g1 = ((pixels[offset] >> 8) & 0xff) * invAlpha;
			int b1 = (pixels[offset] & 0xff) * invAlpha;
			pixels[offset++] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
		}
	}

	public static void drawLineY(int x, int y, int length, int rgb) {
		if ((x < left) || (x >= right)) {
			return;
		}

		if (y < top) {
			length -= top - y;
			y = top;
		}

		if ((y + length) > bottom) {
			length = bottom - y;
		}

		int offset = x + (y * width);

		for (int i = 0; i < length; i++) {
			pixels[offset + (i * width)] = rgb;
		}
	}

	public static void drawLineY(int rgb, int x, int alpha, int y, int length) {
		if ((x < left) || (x >= right)) {
			return;
		}

		if (y < top) {
			length -= top - y;
			y = top;
		}

		if ((y + length) > bottom) {
			length = bottom - y;
		}

		int invAlpha = 256 - alpha;
		int r0 = ((rgb >> 16) & 0xff) * alpha;
		int g0 = ((rgb >> 8) & 0xff) * alpha;
		int b0 = (rgb & 0xff) * alpha;

		int offset = x + (y * width);

		for (int i = 0; i < length; i++) {
			int r1 = ((pixels[offset] >> 16) & 0xff) * invAlpha;
			int g1 = ((pixels[offset] >> 8) & 0xff) * invAlpha;
			int b1 = (pixels[offset] & 0xff) * invAlpha;
			pixels[offset] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
			offset += width;
		}
	}

	public Draw2D() {
	}

}
