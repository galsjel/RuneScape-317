// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class30_Sub2_Sub1_Sub2 extends Class30_Sub2_Sub1 {

	public final int[] anIntArray1451;
	public byte[] aByteArray1450;
	public int anInt1452;
	public int anInt1453;
	public int anInt1454;
	public int anInt1455;
	public int anInt1456;
	public int anInt1457;

	public Class30_Sub2_Sub1_Sub2(Class44 class44, String s, int i) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571(s + ".dat", null));
		Class30_Sub2_Sub2 class30_sub2_sub2_1 = new Class30_Sub2_Sub2(class44.method571("index.dat", null));
		class30_sub2_sub2_1.anInt1406 = class30_sub2_sub2.method410();
		anInt1456 = class30_sub2_sub2_1.method410();
		anInt1457 = class30_sub2_sub2_1.method410();
		int j = class30_sub2_sub2_1.method408();
		anIntArray1451 = new int[j];
		for (int k = 0; k < j - 1; k++) {
			anIntArray1451[k + 1] = class30_sub2_sub2_1.method412();
		}

		for (int l = 0; l < i; l++) {
			class30_sub2_sub2_1.anInt1406 += 2;
			class30_sub2_sub2.anInt1406 += class30_sub2_sub2_1.method410() * class30_sub2_sub2_1.method410();
			class30_sub2_sub2_1.anInt1406++;
		}

		anInt1454 = class30_sub2_sub2_1.method408();
		anInt1455 = class30_sub2_sub2_1.method408();
		anInt1452 = class30_sub2_sub2_1.method410();
		anInt1453 = class30_sub2_sub2_1.method410();
		int i1 = class30_sub2_sub2_1.method408();
		int j1 = anInt1452 * anInt1453;
		aByteArray1450 = new byte[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				aByteArray1450[k1] = class30_sub2_sub2.method409();
			}

			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < anInt1452; l1++) {
				for (int i2 = 0; i2 < anInt1453; i2++) {
					aByteArray1450[l1 + i2 * anInt1452] = class30_sub2_sub2.method409();
				}
			}
		}
	}

	public void method356() {
		anInt1456 /= 2;
		anInt1457 /= 2;
		byte[] abyte0 = new byte[anInt1456 * anInt1457];
		int i = 0;
		for (int j = 0; j < anInt1453; j++) {
			for (int k = 0; k < anInt1452; k++) {
				abyte0[(k + anInt1454 >> 1) + (j + anInt1455 >> 1) * anInt1456] = aByteArray1450[i++];
			}
		}

		aByteArray1450 = abyte0;
		anInt1452 = anInt1456;
		anInt1453 = anInt1457;
		anInt1454 = 0;
		anInt1455 = 0;
	}

	public void method357() {
		if (anInt1452 == anInt1456 && anInt1453 == anInt1457) {
			return;
		}
		byte[] abyte0 = new byte[anInt1456 * anInt1457];
		int i = 0;
		for (int j = 0; j < anInt1453; j++) {
			for (int k = 0; k < anInt1452; k++) {
				abyte0[k + anInt1454 + (j + anInt1455) * anInt1456] = aByteArray1450[i++];
			}
		}

		aByteArray1450 = abyte0;
		anInt1452 = anInt1456;
		anInt1453 = anInt1457;
		anInt1454 = 0;
		anInt1455 = 0;
	}

	public void method358() {
		byte[] abyte0 = new byte[anInt1452 * anInt1453];
		int j = 0;
		for (int k = 0; k < anInt1453; k++) {
			for (int l = anInt1452 - 1; l >= 0; l--) {
				abyte0[j++] = aByteArray1450[l + k * anInt1452];
			}
		}

		aByteArray1450 = abyte0;
		anInt1454 = anInt1456 - anInt1452 - anInt1454;
	}

	public void method359() {
		byte[] abyte0 = new byte[anInt1452 * anInt1453];
		int i = 0;
		for (int j = anInt1453 - 1; j >= 0; j--) {
			for (int k = 0; k < anInt1452; k++) {
				abyte0[i++] = aByteArray1450[k + j * anInt1452];
			}
		}

		aByteArray1450 = abyte0;
		anInt1455 = anInt1457 - anInt1453 - anInt1455;
	}

	public void method360(int i, int j, int k) {
		for (int i1 = 0; i1 < anIntArray1451.length; i1++) {
			int j1 = anIntArray1451[i1] >> 16 & 0xff;
			j1 += i;
			if (j1 < 0) {
				j1 = 0;
			} else if (j1 > 255) {
				j1 = 255;
			}
			int k1 = anIntArray1451[i1] >> 8 & 0xff;
			k1 += j;
			if (k1 < 0) {
				k1 = 0;
			} else if (k1 > 255) {
				k1 = 255;
			}
			int l1 = anIntArray1451[i1] & 0xff;
			l1 += k;
			if (l1 < 0) {
				l1 = 0;
			} else if (l1 > 255) {
				l1 = 255;
			}
			anIntArray1451[i1] = (j1 << 16) + (k1 << 8) + l1;
		}
	}

	public void method361(int i, int k) {
		i += anInt1454;
		k += anInt1455;
		int l = i + k * Class30_Sub2_Sub1.anInt1379;
		int i1 = 0;
		int j1 = anInt1453;
		int k1 = anInt1452;
		int l1 = Class30_Sub2_Sub1.anInt1379 - k1;
		int i2 = 0;
		if (k < Class30_Sub2_Sub1.anInt1381) {
			int j2 = Class30_Sub2_Sub1.anInt1381 - k;
			j1 -= j2;
			k = Class30_Sub2_Sub1.anInt1381;
			i1 += j2 * k1;
			l += j2 * Class30_Sub2_Sub1.anInt1379;
		}
		if (k + j1 > Class30_Sub2_Sub1.anInt1382) {
			j1 -= (k + j1) - Class30_Sub2_Sub1.anInt1382;
		}
		if (i < Class30_Sub2_Sub1.anInt1383) {
			int k2 = Class30_Sub2_Sub1.anInt1383 - i;
			k1 -= k2;
			i = Class30_Sub2_Sub1.anInt1383;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Class30_Sub2_Sub1.anInt1384) {
			int l2 = (i + k1) - Class30_Sub2_Sub1.anInt1384;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 > 0 && j1 > 0) {
			method362(j1, Class30_Sub2_Sub1.anIntArray1378, aByteArray1450, l1, l, k1, i1, anIntArray1451, i2);
		}
	}

	public void method362(int i, int[] ai, byte[] abyte0, int j, int k, int l, int i1, int[] ai1, int j1) {
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
