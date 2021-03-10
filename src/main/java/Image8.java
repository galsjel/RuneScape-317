// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Image8 extends Draw2D {

	public final int[] anIntArray1451;
	public byte[] aByteArray1450;
	public int anInt1452;
	public int anInt1453;
	public int anInt1454;
	public int anInt1455;
	public int anInt1456;
	public int anInt1457;

	public Image8(FileArchive archive, String s, int i) {
		Packet packet = new Packet(archive.read(s + ".dat", null));
		Packet packet_1 = new Packet(archive.read("index.dat", null));
		packet_1.position = packet.get2U();
		anInt1456 = packet_1.get2U();
		anInt1457 = packet_1.get2U();
		int j = packet_1.get1U();
		anIntArray1451 = new int[j];
		for (int k = 0; k < (j - 1); k++) {
			anIntArray1451[k + 1] = packet_1.get3();
		}
		for (int l = 0; l < i; l++) {
			packet_1.position += 2;
			packet.position += packet_1.get2U() * packet_1.get2U();
			packet_1.position++;
		}
		anInt1454 = packet_1.get1U();
		anInt1455 = packet_1.get1U();
		anInt1452 = packet_1.get2U();
		anInt1453 = packet_1.get2U();
		int i1 = packet_1.get1U();
		int j1 = anInt1452 * anInt1453;
		aByteArray1450 = new byte[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				aByteArray1450[k1] = packet.get1();
			}
			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < anInt1452; l1++) {
				for (int i2 = 0; i2 < anInt1453; i2++) {
					aByteArray1450[l1 + (i2 * anInt1452)] = packet.get1();
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
				abyte0[((k + anInt1454) >> 1) + (((j + anInt1455) >> 1) * anInt1456)] = aByteArray1450[i++];
			}
		}
		aByteArray1450 = abyte0;
		anInt1452 = anInt1456;
		anInt1453 = anInt1457;
		anInt1454 = 0;
		anInt1455 = 0;
	}

	public void method357() {
		if ((anInt1452 == anInt1456) && (anInt1453 == anInt1457)) {
			return;
		}
		byte[] abyte0 = new byte[anInt1456 * anInt1457];
		int i = 0;
		for (int j = 0; j < anInt1453; j++) {
			for (int k = 0; k < anInt1452; k++) {
				abyte0[k + anInt1454 + ((j + anInt1455) * anInt1456)] = aByteArray1450[i++];
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
				abyte0[j++] = aByteArray1450[l + (k * anInt1452)];
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
				abyte0[i++] = aByteArray1450[k + (j * anInt1452)];
			}
		}
		aByteArray1450 = abyte0;
		anInt1455 = anInt1457 - anInt1453 - anInt1455;
	}

	public void method360(int i, int j, int k) {
		for (int i1 = 0; i1 < anIntArray1451.length; i1++) {
			int j1 = (anIntArray1451[i1] >> 16) & 0xff;
			j1 += i;
			if (j1 < 0) {
				j1 = 0;
			} else if (j1 > 255) {
				j1 = 255;
			}
			int k1 = (anIntArray1451[i1] >> 8) & 0xff;
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
		int l = i + (k * Draw2D.width);
		int i1 = 0;
		int j1 = anInt1453;
		int k1 = anInt1452;
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
		if ((k1 > 0) && (j1 > 0)) {
			method362(j1, Draw2D.pixels, aByteArray1450, l1, l, k1, i1, anIntArray1451, i2);
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
