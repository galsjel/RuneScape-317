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

    public Image24(byte[] src, java.awt.Component component) {
        try {
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage(src);
            MediaTracker tracker = new MediaTracker(component);
            tracker.addImage(image, 0);
            tracker.waitForAll();
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
        Buffer dat = new Buffer(archive.read(file + ".dat"));
        Buffer idx = new Buffer(archive.read("index.dat"));
        idx.position = dat.readU16();
        cropW = idx.readU16();
        cropH = idx.readU16();
        int paletteSize = idx.readU8();
        int[] palette = new int[paletteSize];
        for (int k = 0; k < (paletteSize - 1); k++) {
            palette[k + 1] = idx.read24();
            if (palette[k + 1] == 0) {
                palette[k + 1] = 1;
            }
        }
        for (int i = 0; i < index; i++) {
            idx.position += 2;
            dat.position += idx.readU16() * idx.readU16();
            idx.position++;
        }
        cropX = idx.readU8();
        cropY = idx.readU8();
        width = idx.readU16();
        height = idx.readU16();
        int layout = idx.readU8();
        int pixelLen = width * height;
        pixels = new int[pixelLen];
        if (layout == 0) {
            for (int i = 0; i < pixelLen; i++) {
                pixels[i] = palette[dat.readU8()];
            }
            return;
        }
        if (layout == 1) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    pixels[x + (y * width)] = palette[dat.readU8()];
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
                pixels[((y + cropY) * cropW) + x + cropX] = this.pixels[(y * width) + x];
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
        for (int y = -h; y < 0; y++) {
            for (int i = quarterWidth; i < 0; i++) {
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
                dst[dstOff++] = src[srcOff++];
            }
            for (int i = w; i < 0; i++) {
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
            copyPixelsAlpha(srcOff, w, Draw2D.pixels, pixels, srcStep, h, dstStep, alpha, dstOff);
        }
    }

    public void copyPixelsAlpha(int srcOff, int w, int[] dst, int[] src, int srcStep, int h, int dstStep, int alpha, int dstOff) {
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
            srcOff += srcStep;
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
            int leftX = (anchorX << 16) + (midY * sin) + (midX * cos);
            int lefty = ((anchorY << 16) + (midY * cos)) - (midX * sin);
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
            int leftX = (anchorX << 16) + (centerY * sin) + (centerX * cos);
            int leftY = ((anchorY << 16) + (centerY * cos)) - (centerX * sin);
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
            for (int i = quarterW; i < 0; i++) {
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
            for (int i = w; i < 0; i++) {
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
