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
        Draw2D.setBounds(0, 0, width, height);
    }

    public static void resetBounds() {
        Draw2D.left = 0;
        Draw2D.top = 0;
        Draw2D.right = Draw2D.width;
        Draw2D.bottom = Draw2D.height;
        Draw2D.boundX = Draw2D.right - 1;
        Draw2D.centerX = Draw2D.right / 2;
    }

    public static void setBounds(int left, int top, int right, int bottom) {
        if (left < 0) {
            left = 0;
        }
        if (top < 0) {
            top = 0;
        }
        if (right > Draw2D.width) {
            right = Draw2D.width;
        }
        if (bottom > Draw2D.height) {
            bottom = Draw2D.height;
        }
        Draw2D.left = left;
        Draw2D.top = top;
        Draw2D.right = right;
        Draw2D.bottom = bottom;
        Draw2D.boundX = Draw2D.right - 1;
        Draw2D.centerX = Draw2D.right / 2;
        Draw2D.centerY = Draw2D.bottom / 2;
    }

    public static void clear() {
        Arrays.fill(Draw2D.pixels, 0);
    }

    public static void drawLine(int x1, int y1, int x2, int y2, int rgb) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            if ((x1 >= Draw2D.left) && (x1 < Draw2D.right) && (y1 >= Draw2D.top) && (y1 < Draw2D.bottom)) {
                Draw2D.pixels[x1 + (y1 * Draw2D.width)] = rgb;
            }

            if ((x1 == x2) && (y1 == y2)) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }

    }

    public static void fillRect(int x, int y, int width, int height, int rgb, int alpha) {
        if (x < Draw2D.left) {
            width -= Draw2D.left - x;
            x = Draw2D.left;
        }

        if (y < Draw2D.top) {
            height -= Draw2D.top - y;
            y = Draw2D.top;
        }

        if ((x + width) > Draw2D.right) {
            width = Draw2D.right - x;
        }

        if ((y + height) > Draw2D.bottom) {
            height = Draw2D.bottom - y;
        }

        int invAlpha = 256 - alpha;
        int r0 = ((rgb >> 16) & 0xff) * alpha;
        int g0 = ((rgb >> 8) & 0xff) * alpha;
        int b0 = (rgb & 0xff) * alpha;

        int step = Draw2D.width - width;
        int offset = x + (y * Draw2D.width);

        for (int i = 0; i < height; i++) {
            for (int j = -width; j < 0; j++) {
                int r1 = ((Draw2D.pixels[offset] >> 16) & 0xff) * invAlpha;
                int g1 = ((Draw2D.pixels[offset] >> 8) & 0xff) * invAlpha;
                int b1 = (Draw2D.pixels[offset] & 0xff) * invAlpha;
                Draw2D.pixels[offset++] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
            }
            offset += step;
        }
    }

    public static void fillRect(int x, int y, int width, int height, int rgb) {
        if (x < Draw2D.left) {
            width -= Draw2D.left - x;
            x = Draw2D.left;
        }

        if (y < Draw2D.top) {
            height -= Draw2D.top - y;
            y = Draw2D.top;
        }

        if ((x + width) > Draw2D.right) {
            width = Draw2D.right - x;
        }

        if ((y + height) > Draw2D.bottom) {
            height = Draw2D.bottom - y;
        }

        int step = Draw2D.width - width;
        int offset = x + (y * Draw2D.width);

        for (int i = -height; i < 0; i++) {
            for (int j = -width; j < 0; j++) {
                Draw2D.pixels[offset++] = rgb;
            }
            offset += step;
        }
    }

    public static void drawRect(int x, int y, int width, int height, int rgb) {
        Draw2D.drawLineX(x, y, width, rgb);
        Draw2D.drawLineX(x, (y + height) - 1, width, rgb);
        Draw2D.drawLineY(x, y, height, rgb);
        Draw2D.drawLineY((x + width) - 1, y, height, rgb);
    }

    public static void drawRect(int x, int y, int width, int height, int rgb, int alpha) {
        Draw2D.drawLineX(x, y, width, rgb, alpha);
        Draw2D.drawLineX(x, (y + height) - 1, width, rgb, alpha);
        if (height >= 3) {
            Draw2D.drawLineY(rgb, x, alpha, y + 1, height - 2);
            Draw2D.drawLineY(rgb, (x + width) - 1, alpha, y + 1, height - 2);
        }
    }

    public static void drawLineX(int x, int y, int length, int rgb) {
        if ((y < Draw2D.top) || (y >= Draw2D.bottom)) {
            return;
        }

        if (x < Draw2D.left) {
            length -= Draw2D.left - x;
            x = Draw2D.left;
        }

        if ((x + length) > Draw2D.right) {
            length = Draw2D.right - x;
        }

        int offset = x + (y * Draw2D.width);

        for (int i = 0; i < length; i++) {
            Draw2D.pixels[offset + i] = rgb;
        }
    }

    public static void drawLineX(int x, int y, int length, int rgb, int alpha) {
        if ((y < Draw2D.top) || (y >= Draw2D.bottom)) {
            return;
        }

        if (x < Draw2D.left) {
            length -= Draw2D.left - x;
            x = Draw2D.left;
        }

        if ((x + length) > Draw2D.right) {
            length = Draw2D.right - x;
        }

        int invAlpha = 256 - alpha;
        int r0 = ((rgb >> 16) & 0xff) * alpha;
        int g0 = ((rgb >> 8) & 0xff) * alpha;
        int b0 = (rgb & 0xff) * alpha;

        int offset = x + (y * Draw2D.width);

        for (int i = 0; i < length; i++) {
            int r1 = ((Draw2D.pixels[offset] >> 16) & 0xff) * invAlpha;
            int g1 = ((Draw2D.pixels[offset] >> 8) & 0xff) * invAlpha;
            int b1 = (Draw2D.pixels[offset] & 0xff) * invAlpha;
            Draw2D.pixels[offset++] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
        }
    }

    public static void drawLineY(int x, int y, int length, int rgb) {
        if ((x < Draw2D.left) || (x >= Draw2D.right)) {
            return;
        }

        if (y < Draw2D.top) {
            length -= Draw2D.top - y;
            y = Draw2D.top;
        }

        if ((y + length) > Draw2D.bottom) {
            length = Draw2D.bottom - y;
        }

        int offset = x + (y * Draw2D.width);

        for (int i = 0; i < length; i++) {
            Draw2D.pixels[offset + (i * Draw2D.width)] = rgb;
        }
    }

    public static void drawLineY(int rgb, int x, int alpha, int y, int length) {
        if ((x < Draw2D.left) || (x >= Draw2D.right)) {
            return;
        }

        if (y < Draw2D.top) {
            length -= Draw2D.top - y;
            y = Draw2D.top;
        }

        if ((y + length) > Draw2D.bottom) {
            length = Draw2D.bottom - y;
        }

        int invAlpha = 256 - alpha;
        int r0 = ((rgb >> 16) & 0xff) * alpha;
        int g0 = ((rgb >> 8) & 0xff) * alpha;
        int b0 = (rgb & 0xff) * alpha;

        int offset = x + (y * Draw2D.width);

        for (int i = 0; i < length; i++) {
            int r1 = ((Draw2D.pixels[offset] >> 16) & 0xff) * invAlpha;
            int g1 = ((Draw2D.pixels[offset] >> 8) & 0xff) * invAlpha;
            int b1 = (Draw2D.pixels[offset] & 0xff) * invAlpha;
            Draw2D.pixels[offset] = (((r0 + r1) >> 8) << 16) + (((g0 + g1) >> 8) << 8) + ((b0 + b1) >> 8);
            offset += Draw2D.width;
        }
    }

    public Draw2D() {
    }

}
