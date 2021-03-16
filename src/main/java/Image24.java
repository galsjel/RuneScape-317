// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.IOException;

public class Image24 extends Draw2D {

	public int[] anIntArray1439;
	public int anInt1440;
	public int anInt1441;
	public int anInt1442;
	public int anInt1443;
	public int anInt1444;
	public int anInt1445;

	public Image24(int i, int j) {
		anIntArray1439 = new int[i * j];
		anInt1440 = anInt1444 = i;
		anInt1441 = anInt1445 = j;
		anInt1442 = anInt1443 = 0;
	}

	public Image24(byte[] abyte0, java.awt.Component component) {
		try {
			java.awt.Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
			MediaTracker mediatracker = new MediaTracker(component);
			mediatracker.addImage(image, 0);
			mediatracker.waitForAll();
			anInt1440 = image.getWidth(component);
			anInt1441 = image.getHeight(component);
			anInt1444 = anInt1440;
			anInt1445 = anInt1441;
			anInt1442 = 0;
			anInt1443 = 0;
			anIntArray1439 = new int[anInt1440 * anInt1441];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, anInt1440, anInt1441, anIntArray1439, 0, anInt1440);
			pixelgrabber.grabPixels();
		} catch (Exception _ex) {
			System.out.println("Error converting jpg");
		}
	}

	public Image24(FileArchive archive, String s, int i) throws IOException {
		Buffer buffer = new Buffer(archive.read(s + ".dat"));
		Buffer buffer_1 = new Buffer(archive.read("index.dat"));
		buffer_1.position = buffer.get2U();
		anInt1444 = buffer_1.get2U();
		anInt1445 = buffer_1.get2U();
		int j = buffer_1.get1U();
		int[] ai = new int[j];
		for (int k = 0; k < (j - 1); k++) {
			ai[k + 1] = buffer_1.get3();
			if (ai[k + 1] == 0) {
				ai[k + 1] = 1;
			}
		}
		for (int l = 0; l < i; l++) {
			buffer_1.position += 2;
			buffer.position += buffer_1.get2U() * buffer_1.get2U();
			buffer_1.position++;
		}
		anInt1442 = buffer_1.get1U();
		anInt1443 = buffer_1.get1U();
		anInt1440 = buffer_1.get2U();
		anInt1441 = buffer_1.get2U();
		int i1 = buffer_1.get1U();
		int j1 = anInt1440 * anInt1441;
		anIntArray1439 = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				anIntArray1439[k1] = ai[buffer.get1U()];
			}
			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < anInt1440; l1++) {
				for (int i2 = 0; i2 < anInt1441; i2++) {
					anIntArray1439[l1 + (i2 * anInt1440)] = ai[buffer.get1U()];
				}
			}
		}
	}

	public void method343() {
		Draw2D.bind(anIntArray1439, anInt1440, anInt1441);
	}

	public void method344(int i, int j, int k) {
		for (int i1 = 0; i1 < anIntArray1439.length; i1++) {
			int j1 = anIntArray1439[i1];
			if (j1 != 0) {
				int k1 = (j1 >> 16) & 0xff;
				k1 += i;
				if (k1 < 1) {
					k1 = 1;
				} else if (k1 > 255) {
					k1 = 255;
				}
				int l1 = (j1 >> 8) & 0xff;
				l1 += j;
				if (l1 < 1) {
					l1 = 1;
				} else if (l1 > 255) {
					l1 = 255;
				}
				int i2 = j1 & 0xff;
				i2 += k;
				if (i2 < 1) {
					i2 = 1;
				} else if (i2 > 255) {
					i2 = 255;
				}
				anIntArray1439[i1] = (k1 << 16) + (l1 << 8) + i2;
			}
		}
	}

	public void method345() {
		int[] ai = new int[anInt1444 * anInt1445];
		for (int j = 0; j < anInt1441; j++) {
			for (int k = 0; k < anInt1440; k++) {
				ai[((j + anInt1443) * anInt1444) + (k + anInt1442)] = anIntArray1439[(j * anInt1440) + k];
			}
		}
		anIntArray1439 = ai;
		anInt1440 = anInt1444;
		anInt1441 = anInt1445;
		anInt1442 = 0;
		anInt1443 = 0;
	}

	public void method346(int i, int j) {
		i += anInt1442;
		j += anInt1443;
		int l = i + (j * Draw2D.width);
		int i1 = 0;
		int j1 = anInt1441;
		int k1 = anInt1440;
		int l1 = Draw2D.width - k1;
		int i2 = 0;
		if (j < Draw2D.top) {
			int j2 = Draw2D.top - j;
			j1 -= j2;
			j = Draw2D.top;
			i1 += j2 * k1;
			l += j2 * Draw2D.width;
		}
		if ((j + j1) > Draw2D.bottom) {
			j1 -= (j + j1) - Draw2D.bottom;
		}
		if (i < Draw2D.left) {
			int k2 = Draw2D.left - i;
			k1 -= k2;
			i = Draw2D.left;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if ((i + k1) > Draw2D.right) {
			int l2 = (i + k1) - Draw2D.right;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if ((k1 <= 0) || (j1 <= 0)) {
		} else {
			method347(l, k1, j1, i2, i1, l1, anIntArray1439, Draw2D.pixels);
		}
	}

	public void method347(int i, int j, int k, int l, int i1, int k1, int[] ai, int[] ai1) {
		int l1 = -(j >> 2);
		j = -(j & 3);
		for (int i2 = -k; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
			}
			for (int k2 = j; k2 < 0; k2++) {
				ai1[i++] = ai[i1++];
			}
			i += k1;
			i1 += l;
		}
	}

	public void method348(int i, int k) {
		i += anInt1442;
		k += anInt1443;
		int l = i + (k * Draw2D.width);
		int i1 = 0;
		int j1 = anInt1441;
		int k1 = anInt1440;
		int l1 = Draw2D.width - k1;
		int i2 = 0;
		if (k < Draw2D.top) {
			int j2 = Draw2D.top - k;
			j1 -= j2;
			k = Draw2D.top;
			i1 += j2 * k1;
			l += j2 * Draw2D.width;
		}
		if ((k + j1) > Draw2D.bottom) {
			j1 -= (k + j1) - Draw2D.bottom;
		}
		if (i < Draw2D.left) {
			int k2 = Draw2D.left - i;
			k1 -= k2;
			i = Draw2D.left;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if ((i + k1) > Draw2D.right) {
			int l2 = (i + k1) - Draw2D.right;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if ((k1 <= 0) || (j1 <= 0)) {
		} else {
			method349(Draw2D.pixels, anIntArray1439, i1, l, k1, j1, l1, i2);
		}
	}

	public void method349(int[] ai, int[] ai1, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			int i;
			for (int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			for (int k2 = l; k2 < 0; k2++) {
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			k += j1;
			j += k1;
		}
	}

	public void method350(int i, int j, int k) {
		i += anInt1442;
		j += anInt1443;
		int i1 = i + (j * Draw2D.width);
		int j1 = 0;
		int k1 = anInt1441;
		int l1 = anInt1440;
		int i2 = Draw2D.width - l1;
		int j2 = 0;
		if (j < Draw2D.top) {
			int k2 = Draw2D.top - j;
			k1 -= k2;
			j = Draw2D.top;
			j1 += k2 * l1;
			i1 += k2 * Draw2D.width;
		}
		if ((j + k1) > Draw2D.bottom) {
			k1 -= (j + k1) - Draw2D.bottom;
		}
		if (i < Draw2D.left) {
			int l2 = Draw2D.left - i;
			l1 -= l2;
			i = Draw2D.left;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if ((i + l1) > Draw2D.right) {
			int i3 = (i + l1) - Draw2D.right;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if ((l1 <= 0) || (k1 <= 0)) {
		} else {
			method351(j1, l1, Draw2D.pixels, anIntArray1439, j2, k1, i2, k, i1);
		}
	}

	public void method351(int i, int j, int[] ai, int[] ai1, int l, int i1, int j1, int k1, int l1) {
		int j2 = 256 - k1;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				int k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = (((((k & 0xff00ff) * k1) + ((i3 & 0xff00ff) * j2)) & 0xff00ff00) + ((((k & 0xff00) * k1) + ((i3 & 0xff00) * j2)) & 0xff0000)) >> 8;
				} else {
					l1++;
				}
			}
			l1 += j1;
			i += l;
		}
	}

	public void method352(int i, int j, int[] ai, int k, int[] ai1, int i1, int j1, int k1, int l1, int i2) {
		try {
			int j2 = -l1 / 2;
			int k2 = -i / 2;
			int l2 = (int) (Math.sin((double) j / 326.11000000000001D) * 65536D);
			int i3 = (int) (Math.cos((double) j / 326.11000000000001D) * 65536D);
			l2 = (l2 * k) >> 8;
			i3 = (i3 * k) >> 8;
			int j3 = (i2 << 16) + ((k2 * l2) + (j2 * i3));
			int k3 = (i1 << 16) + ((k2 * i3) - (j2 * l2));
			int l3 = k1 + (j1 * Draw2D.width);
			for (j1 = 0; j1 < i; j1++) {
				int i4 = ai1[j1];
				int j4 = l3 + i4;
				int k4 = j3 + (i3 * i4);
				int l4 = k3 - (l2 * i4);
				for (k1 = -ai[j1]; k1 < 0; k1++) {
					Draw2D.pixels[j4++] = anIntArray1439[(k4 >> 16) + ((l4 >> 16) * anInt1440)];
					k4 += i3;
					l4 -= l2;
				}
				j3 += l2;
				k3 += i3;
				l3 += Draw2D.width;
			}
		} catch (Exception ignored) {
		}
	}

	public void method353(int i, int j, int k, int l, int j1, int k1, double d, int l1) {
		try {
			int i2 = -k / 2;
			int j2 = -k1 / 2;
			int k2 = (int) (Math.sin(d) * 65536D);
			int l2 = (int) (Math.cos(d) * 65536D);
			k2 = (k2 * j1) >> 8;
			l2 = (l2 * j1) >> 8;
			int i3 = (l << 16) + ((j2 * k2) + (i2 * l2));
			int j3 = (j << 16) + ((j2 * l2) - (i2 * k2));
			int k3 = l1 + (i * Draw2D.width);
			for (i = 0; i < k1; i++) {
				int l3 = k3;
				int i4 = i3;
				int j4 = j3;
				for (l1 = -k; l1 < 0; l1++) {
					int k4 = anIntArray1439[(i4 >> 16) + ((j4 >> 16) * anInt1440)];
					if (k4 != 0) {
						Draw2D.pixels[l3++] = k4;
					} else {
						l3++;
					}
					i4 += l2;
					j4 -= k2;
				}
				i3 += k2;
				j3 += l2;
				k3 += Draw2D.width;
			}
		} catch (Exception ignored) {
		}
	}

	public void method354(Image8 image, int i, int j) {
		j += anInt1442;
		i += anInt1443;
		int k = j + (i * Draw2D.width);
		int l = 0;
		int i1 = anInt1441;
		int j1 = anInt1440;
		int k1 = Draw2D.width - j1;
		int l1 = 0;
		if (i < Draw2D.top) {
			int i2 = Draw2D.top - i;
			i1 -= i2;
			i = Draw2D.top;
			l += i2 * j1;
			k += i2 * Draw2D.width;
		}
		if ((i + i1) > Draw2D.bottom) {
			i1 -= (i + i1) - Draw2D.bottom;
		}
		if (j < Draw2D.left) {
			int j2 = Draw2D.left - j;
			j1 -= j2;
			j = Draw2D.left;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if ((j + j1) > Draw2D.right) {
			int k2 = (j + j1) - Draw2D.right;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if ((j1 <= 0) || (i1 <= 0)) {
		} else {
			method355(anIntArray1439, j1, image.pixels, i1, Draw2D.pixels, k1, k, l1, l);
		}
	}

	public void method355(int[] ai, int i, byte[] abyte0, int j, int[] ai1, int l, int i1, int j1, int k1) {
		int l1 = -(i >> 2);
		i = -(i & 3);
		for (int j2 = -j; j2 < 0; j2++) {
			int k;
			for (int k2 = l1; k2 < 0; k2++) {
				k = ai[k1++];
				if ((k != 0) && (abyte0[i1] == 0)) {
					ai1[i1++] = k;
				} else {
					i1++;
				}
				k = ai[k1++];
				if ((k != 0) && (abyte0[i1] == 0)) {
					ai1[i1++] = k;
				} else {
					i1++;
				}
				k = ai[k1++];
				if ((k != 0) && (abyte0[i1] == 0)) {
					ai1[i1++] = k;
				} else {
					i1++;
				}
				k = ai[k1++];
				if ((k != 0) && (abyte0[i1] == 0)) {
					ai1[i1++] = k;
				} else {
					i1++;
				}
			}
			for (int l2 = i; l2 < 0; l2++) {
				k = ai[k1++];
				if ((k != 0) && (abyte0[i1] == 0)) {
					ai1[i1++] = k;
				} else {
					i1++;
				}
			}
			i1 += l;
			k1 += j1;
		}
	}

}
