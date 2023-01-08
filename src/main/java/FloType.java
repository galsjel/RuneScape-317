// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class FloType {

    public static int count;
    public static FloType[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("flo.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new FloType[count];
        }
        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new FloType();
            }
            instances[i].read(buffer);
        }
    }

    public int rgb;
    public int textureID = -1;
    public boolean occludes = true;
    public int hue;
    public int saturation;
    public int lightness;
    public int chroma;
    public int luminance;
    public int hsl;

    public FloType() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                return;
            } else if (code == 1) {
                rgb = in.read24();
                setColor(rgb);
            } else if (code == 2) {
                textureID = in.readU8();
            } else if (code == 3) {
            } else if (code == 5) {
                occludes = false;
            } else if (code == 6) {
                in.readString(); // name
            } else if (code == 7) {
                int hue = this.hue;
                int saturation = this.saturation;
                int lightness = this.lightness;
                int chroma = this.chroma;
                int rgb = in.read24();
                setColor(rgb);
                this.hue = hue;
                this.saturation = saturation;
                this.lightness = lightness;
                this.chroma = chroma;
                luminance = chroma;
            } else {
                System.out.println("Error unrecognised flo config code: " + code);
            }
        }
    }

    public void setColor(int rgb) {
        double red = (double) ((rgb >> 16) & 0xff) / 256D;
        double green = (double) ((rgb >> 8) & 0xff) / 256D;
        double blue = (double) (rgb & 0xff) / 256D;

        double min = red;

        if (green < min) {
            min = green;
        }

        if (blue < min) {
            min = blue;
        }

        double max = red;

        if (green > max) {
            max = green;
        }

        if (blue > max) {
            max = blue;
        }

        double h = 0.0D;
        double s = 0.0D;
        double l = (min + max) / 2D;

        if (min != max) {
            if (l < 0.5D) {
                s = (max - min) / (max + min);
            }

            if (l >= 0.5D) {
                s = (max - min) / (2D - max - min);
            }

            if (red == max) {
                h = (green - blue) / (max - min);
            } else if (green == max) {
                h = 2D + ((blue - red) / (max - min));
            } else if (blue == max) {
                h = 4D + ((red - green) / (max - min));
            }
        }

        h /= 6D;

        this.hue = (int) (h * 256D);
        this.saturation = (int) (s * 256D);
        this.lightness = (int) (l * 256D);

        if (this.saturation < 0) {
            this.saturation = 0;
        } else if (this.saturation > 255) {
            this.saturation = 255;
        }

        if (this.lightness < 0) {
            this.lightness = 0;
        } else if (this.lightness > 255) {
            this.lightness = 255;
        }

        if (l > 0.5D) {
            luminance = (int) ((1.0D - l) * s * 512D);
        } else {
            luminance = (int) (l * s * 512D);
        }

        if (luminance < 1) {
            luminance = 1;
        }

        chroma = (int) (h * (double) luminance);

        int hue = (this.hue + (int) (Math.random() * 16D)) - 8;

        if (hue < 0) {
            hue = 0;
        } else if (hue > 255) {
            hue = 255;
        }

        int saturation = (this.saturation + (int) (Math.random() * 48D)) - 24;

        if (saturation < 0) {
            saturation = 0;
        } else if (saturation > 255) {
            saturation = 255;
        }

        int lightness = (this.lightness + (int) (Math.random() * 48D)) - 24;

        if (lightness < 0) {
            lightness = 0;
        } else if (lightness > 255) {
            lightness = 255;
        }

        hsl = decimateHSL(hue, saturation, lightness);
    }

    public int decimateHSL(int hue, int saturation, int lightness) {
        if (lightness > 179) {
            saturation /= 2;
        }
        if (lightness > 192) {
            saturation /= 2;
        }
        if (lightness > 217) {
            saturation /= 2;
        }
        if (lightness > 243) {
            saturation /= 2;
        }
        return ((hue / 4) << 10) + ((saturation / 32) << 7) + (lightness / 2);
    }

}
