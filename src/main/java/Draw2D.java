// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Draw2D extends Class30_Sub2 {

	public static int[] anIntArray1378;
	public static int anInt1379;
	public static int anInt1380;
	public static int anInt1381;
	public static int anInt1382;
	public static int anInt1383;
	public static int anInt1384;
	public static int anInt1385;
	public static int anInt1386;
	public static int anInt1387;

	public Draw2D() {
	}

	public static void method331(int i, int j, int[] ai) {
		anIntArray1378 = ai;
		anInt1379 = j;
		anInt1380 = i;
		method333(i, 0, j, 0);
	}

	public static void method332() {
		anInt1383 = 0;
		anInt1381 = 0;
		anInt1384 = anInt1379;
		anInt1382 = anInt1380;
		anInt1385 = anInt1384 - 1;
		anInt1386 = anInt1384 / 2;
	}

	public static void method333(int i, int j, int k, int l) {
		if (j < 0) {
			j = 0;
		}
		if (l < 0) {
			l = 0;
		}
		if (k > anInt1379) {
			k = anInt1379;
		}
		if (i > anInt1380) {
			i = anInt1380;
		}
		anInt1383 = j;
		anInt1381 = l;
		anInt1384 = k;
		anInt1382 = i;
		anInt1385 = anInt1384 - 1;
		anInt1386 = anInt1384 / 2;
		anInt1387 = anInt1382 / 2;
	}

	public static void method334() {
		int i = anInt1379 * anInt1380;
		for (int j = 0; j < i; j++) {
			anIntArray1378[j] = 0;
		}
	}

	public static void method335(int i, int j, int k, int l, int i1, int k1) {
		if (k1 < anInt1383) {
			k -= anInt1383 - k1;
			k1 = anInt1383;
		}
		if (j < anInt1381) {
			l -= anInt1381 - j;
			j = anInt1381;
		}
		if (k1 + k > anInt1384) {
			k = anInt1384 - k1;
		}
		if (j + l > anInt1382) {
			l = anInt1382 - j;
		}
		int l1 = 256 - i1;
		int i2 = (i >> 16 & 0xff) * i1;
		int j2 = (i >> 8 & 0xff) * i1;
		int k2 = (i & 0xff) * i1;
		int k3 = anInt1379 - k;
		int l3 = k1 + j * anInt1379;
		for (int i4 = 0; i4 < l; i4++) {
			for (int j4 = -k; j4 < 0; j4++) {
				int l2 = (anIntArray1378[l3] >> 16 & 0xff) * l1;
				int i3 = (anIntArray1378[l3] >> 8 & 0xff) * l1;
				int j3 = (anIntArray1378[l3] & 0xff) * l1;
				int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8) + (k2 + j3 >> 8);
				anIntArray1378[l3++] = k4;
			}
			l3 += k3;
		}
	}

	public static void method336(int i, int j, int k, int l, int i1) {
		if (k < anInt1383) {
			i1 -= anInt1383 - k;
			k = anInt1383;
		}
		if (j < anInt1381) {
			i -= anInt1381 - j;
			j = anInt1381;
		}
		if (k + i1 > anInt1384) {
			i1 = anInt1384 - k;
		}
		if (j + i > anInt1382) {
			i = anInt1382 - j;
		}
		int k1 = anInt1379 - i1;
		int l1 = k + j * anInt1379;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++) {
				anIntArray1378[l1++] = l;
			}
			l1 += k1;
		}
	}

	public static void method337(int i, int j, int k, int l, int i1) {
		method339(i1, l, j, i);
		method339((i1 + k) - 1, l, j, i);
		method341(i1, l, k, i);
		method341(i1, l, k, (i + j) - 1);
	}

	public static void method338(int i, int j, int k, int l, int i1, int j1) {
		method340(l, i1, i, k, j1);
		method340(l, i1, (i + j) - 1, k, j1);
		if (j >= 3) {
			method342(l, j1, k, i + 1, j - 2);
			method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
		}
	}

	public static void method339(int i, int j, int k, int l) {
		if (i < anInt1381 || i >= anInt1382) {
			return;
		}
		if (l < anInt1383) {
			k -= anInt1383 - l;
			l = anInt1383;
		}
		if (l + k > anInt1384) {
			k = anInt1384 - l;
		}
		int i1 = l + i * anInt1379;
		for (int j1 = 0; j1 < k; j1++) {
			anIntArray1378[i1 + j1] = j;
		}
	}

	public static void method340(int i, int j, int k, int l, int i1) {
		if (k < anInt1381 || k >= anInt1382) {
			return;
		}
		if (i1 < anInt1383) {
			j -= anInt1383 - i1;
			i1 = anInt1383;
		}
		if (i1 + j > anInt1384) {
			j = anInt1384 - i1;
		}
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * anInt1379;
		for (int j3 = 0; j3 < j; j3++) {
			int j2 = (anIntArray1378[i3] >> 16 & 0xff) * j1;
			int k2 = (anIntArray1378[i3] >> 8 & 0xff) * j1;
			int l2 = (anIntArray1378[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			anIntArray1378[i3++] = k3;
		}
	}

	public static void method341(int i, int j, int k, int l) {
		if (l < anInt1383 || l >= anInt1384) {
			return;
		}
		if (i < anInt1381) {
			k -= anInt1381 - i;
			i = anInt1381;
		}
		if (i + k > anInt1382) {
			k = anInt1382 - i;
		}
		int j1 = l + i * anInt1379;
		for (int k1 = 0; k1 < k; k1++) {
			anIntArray1378[j1 + k1 * anInt1379] = j;
		}
	}

	public static void method342(int i, int j, int k, int l, int i1) {
		if (j < anInt1383 || j >= anInt1384) {
			return;
		}
		if (l < anInt1381) {
			i1 -= anInt1381 - l;
			l = anInt1381;
		}
		if (l + i1 > anInt1382) {
			i1 = anInt1382 - l;
		}
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * anInt1379;
		for (int j3 = 0; j3 < i1; j3++) {
			int j2 = (anIntArray1378[i3] >> 16 & 0xff) * j1;
			int k2 = (anIntArray1378[i3] >> 8 & 0xff) * j1;
			int l2 = (anIntArray1378[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			anIntArray1378[i3] = k3;
			i3 += anInt1379;
		}
	}

}
