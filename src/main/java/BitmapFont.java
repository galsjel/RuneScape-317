// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.util.Random;

public class BitmapFont extends Draw2D {

	public final byte[][] aByteArrayArray1491 = new byte[256][];
	public final int[] anIntArray1492 = new int[256];
	public final int[] anIntArray1493 = new int[256];
	public final int[] anIntArray1494 = new int[256];
	public final int[] anIntArray1495 = new int[256];
	public final int[] anIntArray1496 = new int[256];
	public int anInt1497;
	public final Random aRandom1498 = new Random();
	public boolean aBoolean1499 = false;

	public BitmapFont(boolean flag, String s, Class44 class44) {
		Buffer buffer = new Buffer(class44.method571(s + ".dat", null));
		Buffer buffer_1 = new Buffer(class44.method571("index.dat", null));
		buffer_1.anInt1406 = buffer.method410() + 4;
		int k = buffer_1.method408();
		if (k > 0) {
			buffer_1.anInt1406 += 3 * (k - 1);
		}
		for (int l = 0; l < 256; l++) {
			anIntArray1494[l] = buffer_1.method408();
			anIntArray1495[l] = buffer_1.method408();
			int i1 = anIntArray1492[l] = buffer_1.method410();
			int j1 = anIntArray1493[l] = buffer_1.method410();
			int k1 = buffer_1.method408();
			int l1 = i1 * j1;
			aByteArrayArray1491[l] = new byte[l1];
			if (k1 == 0) {
				for (int i2 = 0; i2 < l1; i2++) {
					aByteArrayArray1491[l][i2] = buffer.method409();
				}
			} else if (k1 == 1) {
				for (int j2 = 0; j2 < i1; j2++) {
					for (int l2 = 0; l2 < j1; l2++) {
						aByteArrayArray1491[l][j2 + l2 * i1] = buffer.method409();
					}
				}
			}
			if (j1 > anInt1497 && l < 128) {
				anInt1497 = j1;
			}
			anIntArray1494[l] = 1;
			anIntArray1496[l] = i1 + 2;
			int k2 = 0;
			for (int i3 = j1 / 7; i3 < j1; i3++) {
				k2 += aByteArrayArray1491[l][i3 * i1];
			}
			if (k2 <= j1 / 7) {
				anIntArray1496[l]--;
				anIntArray1494[l] = 0;
			}
			k2 = 0;
			for (int j3 = j1 / 7; j3 < j1; j3++) {
				k2 += aByteArrayArray1491[l][(i1 - 1) + j3 * i1];
			}
			if (k2 <= j1 / 7) {
				anIntArray1496[l]--;
			}
		}
		if (flag) {
			anIntArray1496[32] = anIntArray1496[73];
		} else {
			anIntArray1496[32] = anIntArray1496[105];
		}
	}

	public void method380(String s, int i, int j, int k) {
		method385(j, s, k, i - method384(s));
	}

	public void method381(int i, String s, int k, int l) {
		method385(i, s, k, l - method384(s) / 2);
	}

	public void method382(int i, int j, String s, int l, boolean flag) {
		method389(flag, j - method383(s) / 2, i, s, l);
	}

	public int method383(String s) {
		if (s == null) {
			return 0;
		}
		int j = 0;
		for (int k = 0; k < s.length(); k++) {
			if (s.charAt(k) == '@' && k + 4 < s.length() && s.charAt(k + 4) == '@') {
				k += 4;
			} else {
				j += anIntArray1496[s.charAt(k)];
			}
		}
		return j;
	}

	public int method384(String s) {
		if (s == null) {
			return 0;
		}
		int j = 0;
		for (int k = 0; k < s.length(); k++) {
			j += anIntArray1496[s.charAt(k)];
		}
		return j;
	}

	public void method385(int i, String s, int j, int l) {
		if (s == null) {
			return;
		}
		j -= anInt1497;
		for (int i1 = 0; i1 < s.length(); i1++) {
			char c = s.charAt(i1);
			if (c != ' ') {
				method392(aByteArrayArray1491[c], l + anIntArray1494[c], j + anIntArray1495[c], anIntArray1492[c], anIntArray1493[c], i);
			}
			l += anIntArray1496[c];
		}
	}

	public void method386(int i, String s, int j, int k, int l) {
		if (s == null) {
			return;
		}
		j -= method384(s) / 2;
		l -= anInt1497;
		for (int i1 = 0; i1 < s.length(); i1++) {
			char c = s.charAt(i1);
			if (c != ' ') {
				method392(aByteArrayArray1491[c], j + anIntArray1494[c], l + anIntArray1495[c] + (int) (Math.sin((double) i1 / 2D + (double) k / 5D) * 5D), anIntArray1492[c], anIntArray1493[c], i);
			}
			j += anIntArray1496[c];
		}
	}

	public void method387(int i, String s, int j, int k, int l) {
		if (s == null) {
			return;
		}
		i -= method384(s) / 2;
		k -= anInt1497;
		for (int i1 = 0; i1 < s.length(); i1++) {
			char c = s.charAt(i1);
			if (c != ' ') {
				method392(aByteArrayArray1491[c], i + anIntArray1494[c] + (int) (Math.sin((double) i1 / 5D + (double) j / 5D) * 5D), k + anIntArray1495[c] + (int) (Math.sin((double) i1 / 3D + (double) j / 5D) * 5D), anIntArray1492[c], anIntArray1493[c], l);
			}
			i += anIntArray1496[c];
		}
	}

	public void method388(int i, String s, int j, int k, int l, int i1) {
		if (s == null) {
			return;
		}
		double d = 7D - (double) i / 8D;
		if (d < 0.0D) {
			d = 0.0D;
		}
		l -= method384(s) / 2;
		k -= anInt1497;
		for (int k1 = 0; k1 < s.length(); k1++) {
			char c = s.charAt(k1);
			if (c != ' ') {
				method392(aByteArrayArray1491[c], l + anIntArray1494[c], k + anIntArray1495[c] + (int) (Math.sin((double) k1 / 1.5D + (double) j) * d), anIntArray1492[c], anIntArray1493[c], i1);
			}
			l += anIntArray1496[c];
		}
	}

	public void method389(boolean flag1, int i, int j, String s, int k) {
		aBoolean1499 = false;
		int l = i;
		if (s == null) {
			return;
		}
		k -= anInt1497;
		for (int i1 = 0; i1 < s.length(); i1++) {
			if (s.charAt(i1) == '@' && i1 + 4 < s.length() && s.charAt(i1 + 4) == '@') {
				int j1 = method391(s.substring(i1 + 1, i1 + 4));
				if (j1 != -1) {
					j = j1;
				}
				i1 += 4;
			} else {
				char c = s.charAt(i1);
				if (c != ' ') {
					if (flag1) {
						method392(aByteArrayArray1491[c], i + anIntArray1494[c] + 1, k + anIntArray1495[c] + 1, anIntArray1492[c], anIntArray1493[c], 0);
					}
					method392(aByteArrayArray1491[c], i + anIntArray1494[c], k + anIntArray1495[c], anIntArray1492[c], anIntArray1493[c], j);
				}
				i += anIntArray1496[c];
			}
		}
		if (aBoolean1499) {
			Draw2D.method339(k + (int) ((double) anInt1497 * 0.69999999999999996D), 0x800000, i - l, l);
		}
	}

	public void method390(boolean flag, int i, int j, String s, int k, int i1) {
		if (s == null) {
			return;
		}
		aRandom1498.setSeed(k);
		int j1 = 192 + (aRandom1498.nextInt() & 0x1f);
		i1 -= anInt1497;
		for (int k1 = 0; k1 < s.length(); k1++) {
			if (s.charAt(k1) == '@' && k1 + 4 < s.length() && s.charAt(k1 + 4) == '@') {
				int l1 = method391(s.substring(k1 + 1, k1 + 4));
				if (l1 != -1) {
					j = l1;
				}
				k1 += 4;
			} else {
				char c = s.charAt(k1);
				if (c != ' ') {
					if (flag) {
						method394(192, i + anIntArray1494[c] + 1, aByteArrayArray1491[c], anIntArray1492[c], i1 + anIntArray1495[c] + 1, anIntArray1493[c], 0);
					}
					method394(j1, i + anIntArray1494[c], aByteArrayArray1491[c], anIntArray1492[c], i1 + anIntArray1495[c], anIntArray1493[c], j);
				}
				i += anIntArray1496[c];
				if ((aRandom1498.nextInt() & 3) == 0) {
					i++;
				}
			}
		}
	}

	public int method391(String s) {
		if (s.equals("red")) {
			return 0xff0000;
		}
		if (s.equals("gre")) {
			return 65280;
		}
		if (s.equals("blu")) {
			return 255;
		}
		if (s.equals("yel")) {
			return 0xffff00;
		}
		if (s.equals("cya")) {
			return 65535;
		}
		if (s.equals("mag")) {
			return 0xff00ff;
		}
		if (s.equals("whi")) {
			return 0xffffff;
		}
		if (s.equals("bla")) {
			return 0;
		}
		if (s.equals("lre")) {
			return 0xff9040;
		}
		if (s.equals("dre")) {
			return 0x800000;
		}
		if (s.equals("dbl")) {
			return 128;
		}
		if (s.equals("or1")) {
			return 0xffb000;
		}
		if (s.equals("or2")) {
			return 0xff7000;
		}
		if (s.equals("or3")) {
			return 0xff3000;
		}
		if (s.equals("gr1")) {
			return 0xc0ff00;
		}
		if (s.equals("gr2")) {
			return 0x80ff00;
		}
		if (s.equals("gr3")) {
			return 0x40ff00;
		}
		if (s.equals("str")) {
			aBoolean1499 = true;
		}
		if (s.equals("end")) {
			aBoolean1499 = false;
		}
		return -1;
	}

	public void method392(byte[] abyte0, int i, int j, int k, int l, int i1) {
		int j1 = i + j * Draw2D.anInt1379;
		int k1 = Draw2D.anInt1379 - k;
		int l1 = 0;
		int i2 = 0;
		if (j < Draw2D.anInt1381) {
			int j2 = Draw2D.anInt1381 - j;
			l -= j2;
			j = Draw2D.anInt1381;
			i2 += j2 * k;
			j1 += j2 * Draw2D.anInt1379;
		}
		if (j + l >= Draw2D.anInt1382) {
			l -= ((j + l) - Draw2D.anInt1382) + 1;
		}
		if (i < Draw2D.anInt1383) {
			int k2 = Draw2D.anInt1383 - i;
			k -= k2;
			i = Draw2D.anInt1383;
			i2 += k2;
			j1 += k2;
			l1 += k2;
			k1 += k2;
		}
		if (i + k >= Draw2D.anInt1384) {
			int l2 = ((i + k) - Draw2D.anInt1384) + 1;
			k -= l2;
			l1 += l2;
			k1 += l2;
		}
		if (k <= 0 || l <= 0) {
		} else {
			method393(Draw2D.anIntArray1378, abyte0, i1, i2, j1, k, l, k1, l1);
		}
	}

	public void method393(int[] ai, byte[] abyte0, int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			for (int k2 = l; k2 < 0; k2++) {
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			k += j1;
			j += k1;
		}
	}

	public void method394(int i, int j, byte[] abyte0, int k, int l, int i1, int j1) {
		int k1 = j + l * Draw2D.anInt1379;
		int l1 = Draw2D.anInt1379 - k;
		int i2 = 0;
		int j2 = 0;
		if (l < Draw2D.anInt1381) {
			int k2 = Draw2D.anInt1381 - l;
			i1 -= k2;
			l = Draw2D.anInt1381;
			j2 += k2 * k;
			k1 += k2 * Draw2D.anInt1379;
		}
		if (l + i1 >= Draw2D.anInt1382) {
			i1 -= ((l + i1) - Draw2D.anInt1382) + 1;
		}
		if (j < Draw2D.anInt1383) {
			int l2 = Draw2D.anInt1383 - j;
			k -= l2;
			j = Draw2D.anInt1383;
			j2 += l2;
			k1 += l2;
			i2 += l2;
			l1 += l2;
		}
		if (j + k >= Draw2D.anInt1384) {
			int i3 = ((j + k) - Draw2D.anInt1384) + 1;
			k -= i3;
			i2 += i3;
			l1 += i3;
		}
		if (k <= 0 || i1 <= 0) {
			return;
		}
		method395(abyte0, i1, k1, Draw2D.anIntArray1378, j2, k, i2, l1, j1, i);
	}

	public void method395(byte[] abyte0, int i, int j, int[] ai, int l, int i1, int j1, int k1, int l1, int i2) {
		l1 = ((l1 & 0xff00ff) * i2 & 0xff00ff00) + ((l1 & 0xff00) * i2 & 0xff0000) >> 8;
		i2 = 256 - i2;
		for (int j2 = -i; j2 < 0; j2++) {
			for (int k2 = -i1; k2 < 0; k2++) {
				if (abyte0[l++] != 0) {
					int l2 = ai[j];
					ai[j++] = (((l2 & 0xff00ff) * i2 & 0xff00ff00) + ((l2 & 0xff00) * i2 & 0xff0000) >> 8) + l1;
				} else {
					j++;
				}
			}
			j += k1;
			l += j1;
		}
	}

}
