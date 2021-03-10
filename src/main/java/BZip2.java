// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class BZip2 {

	private static final BZip2Stream stream = new BZip2Stream();

	public static void decompress(byte[] dst, int dstLen, byte[] src, int srcOff, int srcLen) {
		synchronized (stream) {
			stream.aByteArray563 = src;
			stream.anInt564 = srcOff;
			stream.aByteArray568 = dst;
			stream.anInt569 = 0;
			stream.anInt565 = srcLen;
			stream.anInt570 = dstLen;
			stream.anInt577 = 0;
			stream.anInt576 = 0;
			stream.anInt566 = 0;
			stream.anInt567 = 0;
			stream.anInt571 = 0;
			stream.anInt572 = 0;
			stream.anInt579 = 0;
			decompress(stream);
		}
	}

	public static void method226(BZip2Stream stream) {
		byte byte4 = stream.aByte573;
		int i = stream.anInt574;
		int j = stream.anInt584;
		int k = stream.anInt582;
		int[] ai = BZip2Stream.anIntArray587;
		int l = stream.anInt581;
		byte[] abyte0 = stream.aByteArray568;
		int i1 = stream.anInt569;
		int j1 = stream.anInt570;
		int k1 = j1;
		int l1 = stream.anInt601 + 1;
		label0:
		do {
			if (i > 0) {
				do {
					if (j1 == 0) {
						break label0;
					}
					if (i == 1) {
						break;
					}
					abyte0[i1] = byte4;
					i--;
					i1++;
					j1--;
				} while (true);
				if (j1 == 0) {
					i = 1;
					break;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
			}
			boolean flag = true;
			while (flag) {
				flag = false;
				if (j == l1) {
					i = 0;
					break label0;
				}
				byte4 = (byte) k;
				l = ai[l];
				byte byte0 = (byte) (l & 0xff);
				l >>= 8;
				j++;
				if (byte0 != k) {
					k = byte0;
					if (j1 == 0) {
						i = 1;
					} else {
						abyte0[i1] = byte4;
						i1++;
						j1--;
						flag = true;
						continue;
					}
					break label0;
				}
				if (j != l1) {
					continue;
				}
				if (j1 == 0) {
					i = 1;
					break label0;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
				flag = true;
			}
			i = 2;
			l = ai[l];
			byte byte1 = (byte) (l & 0xff);
			l >>= 8;
			if (++j != l1) {
				if (byte1 != k) {
					k = byte1;
				} else {
					i = 3;
					l = ai[l];
					byte byte2 = (byte) (l & 0xff);
					l >>= 8;
					if (++j != l1) {
						if (byte2 != k) {
							k = byte2;
						} else {
							l = ai[l];
							byte byte3 = (byte) (l & 0xff);
							l >>= 8;
							j++;
							i = (byte3 & 0xff) + 4;
							l = ai[l];
							k = (byte) (l & 0xff);
							l >>= 8;
							j++;
						}
					}
				}
			}
		} while (true);
		int i2 = stream.anInt571;
		stream.anInt571 += k1 - j1;
		if (stream.anInt571 < i2) {
			stream.anInt572++;
		}
		stream.aByte573 = byte4;
		stream.anInt574 = i;
		stream.anInt584 = j;
		stream.anInt582 = k;
		BZip2Stream.anIntArray587 = ai;
		stream.anInt581 = l;
		stream.aByteArray568 = abyte0;
		stream.anInt569 = i1;
		stream.anInt570 = j1;
	}

	public static void decompress(BZip2Stream stream) {
		int k8 = 0;
		int[] ai = null;
		int[] ai1 = null;
		int[] ai2 = null;
		stream.anInt578 = 1;
		if (BZip2Stream.anIntArray587 == null) {
			BZip2Stream.anIntArray587 = new int[stream.anInt578 * 100000];
		}
		boolean flag19 = true;
		while (flag19) {
			byte byte0 = method228(stream);
			if (byte0 == 23) {
				return;
			}
			method228(stream);
			method228(stream);
			method228(stream);
			method228(stream);
			method228(stream);
			stream.anInt579++;
			method228(stream);
			method228(stream);
			method228(stream);
			method228(stream);
			byte0 = method229(stream);
			stream.aBoolean575 = byte0 != 0;
			if (stream.aBoolean575) {
				System.out.println("PANIC! RANDOMISED BLOCK!");
			}
			stream.anInt580 = 0;
			byte0 = method228(stream);
			stream.anInt580 = (stream.anInt580 << 8) | (byte0 & 0xff);
			byte0 = method228(stream);
			stream.anInt580 = (stream.anInt580 << 8) | (byte0 & 0xff);
			byte0 = method228(stream);
			stream.anInt580 = (stream.anInt580 << 8) | (byte0 & 0xff);
			for (int j = 0; j < 16; j++) {
				byte byte1 = method229(stream);
				stream.aBooleanArray590[j] = byte1 == 1;
			}
			for (int k = 0; k < 256; k++) {
				stream.aBooleanArray589[k] = false;
			}
			for (int l = 0; l < 16; l++) {
				if (stream.aBooleanArray590[l]) {
					for (int i3 = 0; i3 < 16; i3++) {
						byte byte2 = method229(stream);
						if (byte2 == 1) {
							stream.aBooleanArray589[(l * 16) + i3] = true;
						}
					}
				}
			}
			method231(stream);
			int i4 = stream.anInt588 + 2;
			int j4 = method230(3, stream);
			int k4 = method230(15, stream);
			for (int i1 = 0; i1 < k4; i1++) {
				int j3 = 0;
				do {
					byte byte3 = method229(stream);
					if (byte3 == 0) {
						break;
					}
					j3++;
				} while (true);
				stream.aByteArray595[i1] = (byte) j3;
			}
			byte[] abyte0 = new byte[6];
			for (byte byte16 = 0; byte16 < j4; byte16++) {
				abyte0[byte16] = byte16;
			}
			for (int j1 = 0; j1 < k4; j1++) {
				byte byte17 = stream.aByteArray595[j1];
				byte byte15 = abyte0[byte17];
				for (; byte17 > 0; byte17--) {
					abyte0[byte17] = abyte0[byte17 - 1];
				}
				abyte0[0] = byte15;
				stream.aByteArray594[j1] = byte15;
			}
			for (int k3 = 0; k3 < j4; k3++) {
				int l6 = method230(5, stream);
				for (int k1 = 0; k1 < i4; k1++) {
					do {
						byte byte4 = method229(stream);
						if (byte4 == 0) {
							break;
						}
						byte4 = method229(stream);
						if (byte4 == 0) {
							l6++;
						} else {
							l6--;
						}
					} while (true);
					stream.aByteArrayArray596[k3][k1] = (byte) l6;
				}
			}
			for (int l3 = 0; l3 < j4; l3++) {
				byte byte8 = 32;
				int i = 0;
				for (int l1 = 0; l1 < i4; l1++) {
					if (stream.aByteArrayArray596[l3][l1] > i) {
						i = stream.aByteArrayArray596[l3][l1];
					}
					if (stream.aByteArrayArray596[l3][l1] < byte8) {
						byte8 = stream.aByteArrayArray596[l3][l1];
					}
				}
				method232(stream.anIntArrayArray597[l3], stream.anIntArrayArray598[l3], stream.anIntArrayArray599[l3], stream.aByteArrayArray596[l3], byte8, i, i4);
				stream.anIntArray600[l3] = byte8;
			}
			int l4 = stream.anInt588 + 1;
			int i5 = -1;
			int j5 = 0;
			for (int i2 = 0; i2 <= 255; i2++) {
				stream.anIntArray583[i2] = 0;
			}
			int j9 = 4095;
			for (int l8 = 15; l8 >= 0; l8--) {
				for (int i9 = 15; i9 >= 0; i9--) {
					stream.aByteArray592[j9] = (byte) ((l8 * 16) + i9);
					j9--;
				}
				stream.anIntArray593[l8] = j9 + 1;
			}
			int i6 = 0;
			if (j5 == 0) {
				i5++;
				j5 = 50;
				byte byte12 = stream.aByteArray594[i5];
				k8 = stream.anIntArray600[byte12];
				ai = stream.anIntArrayArray597[byte12];
				ai2 = stream.anIntArrayArray599[byte12];
				ai1 = stream.anIntArrayArray598[byte12];
			}
			j5--;
			int i7 = k8;
			int l7;
			byte byte9;
			for (l7 = method230(i7, stream); l7 > ai[i7]; l7 = (l7 << 1) | byte9) {
				i7++;
				byte9 = method229(stream);
			}
			for (int k5 = ai2[l7 - ai1[i7]]; k5 != l4; ) {
				if ((k5 == 0) || (k5 == 1)) {
					int j6 = -1;
					int k6 = 1;
					do {
						if (k5 == 0) {
							j6 += k6;
						} else if (k5 == 1) {
							j6 += 2 * k6;
						}
						k6 *= 2;
						if (j5 == 0) {
							i5++;
							j5 = 50;
							byte byte13 = stream.aByteArray594[i5];
							k8 = stream.anIntArray600[byte13];
							ai = stream.anIntArrayArray597[byte13];
							ai2 = stream.anIntArrayArray599[byte13];
							ai1 = stream.anIntArrayArray598[byte13];
						}
						j5--;
						int j7 = k8;
						int i8;
						byte byte10;
						for (i8 = method230(j7, stream); i8 > ai[j7]; i8 = (i8 << 1) | byte10) {
							j7++;
							byte10 = method229(stream);
						}
						k5 = ai2[i8 - ai1[j7]];
					} while ((k5 == 0) || (k5 == 1));
					j6++;
					byte byte5 = stream.aByteArray591[stream.aByteArray592[stream.anIntArray593[0]] & 0xff];
					stream.anIntArray583[byte5 & 0xff] += j6;
					for (; j6 > 0; j6--) {
						BZip2Stream.anIntArray587[i6] = byte5 & 0xff;
						i6++;
					}
				} else {
					int j11 = k5 - 1;
					byte byte6;
					if (j11 < 16) {
						int j10 = stream.anIntArray593[0];
						byte6 = stream.aByteArray592[j10 + j11];
						for (; j11 > 3; j11 -= 4) {
							int k11 = j10 + j11;
							stream.aByteArray592[k11] = stream.aByteArray592[k11 - 1];
							stream.aByteArray592[k11 - 1] = stream.aByteArray592[k11 - 2];
							stream.aByteArray592[k11 - 2] = stream.aByteArray592[k11 - 3];
							stream.aByteArray592[k11 - 3] = stream.aByteArray592[k11 - 4];
						}
						for (; j11 > 0; j11--) {
							stream.aByteArray592[j10 + j11] = stream.aByteArray592[(j10 + j11) - 1];
						}
						stream.aByteArray592[j10] = byte6;
					} else {
						int l10 = j11 / 16;
						int i11 = j11 % 16;
						int k10 = stream.anIntArray593[l10] + i11;
						byte6 = stream.aByteArray592[k10];
						for (; k10 > stream.anIntArray593[l10]; k10--) {
							stream.aByteArray592[k10] = stream.aByteArray592[k10 - 1];
						}
						stream.anIntArray593[l10]++;
						for (; l10 > 0; l10--) {
							stream.anIntArray593[l10]--;
							stream.aByteArray592[stream.anIntArray593[l10]] = stream.aByteArray592[(stream.anIntArray593[l10 - 1] + 16) - 1];
						}
						stream.anIntArray593[0]--;
						stream.aByteArray592[stream.anIntArray593[0]] = byte6;
						if (stream.anIntArray593[0] == 0) {
							int i10 = 4095;
							for (int k9 = 15; k9 >= 0; k9--) {
								for (int l9 = 15; l9 >= 0; l9--) {
									stream.aByteArray592[i10] = stream.aByteArray592[stream.anIntArray593[k9] + l9];
									i10--;
								}
								stream.anIntArray593[k9] = i10 + 1;
							}
						}
					}
					stream.anIntArray583[stream.aByteArray591[byte6 & 0xff] & 0xff]++;
					BZip2Stream.anIntArray587[i6] = stream.aByteArray591[byte6 & 0xff] & 0xff;
					i6++;
					if (j5 == 0) {
						i5++;
						j5 = 50;
						byte byte14 = stream.aByteArray594[i5];
						k8 = stream.anIntArray600[byte14];
						ai = stream.anIntArrayArray597[byte14];
						ai2 = stream.anIntArrayArray599[byte14];
						ai1 = stream.anIntArrayArray598[byte14];
					}
					j5--;
					int k7 = k8;
					int j8;
					byte byte11;
					for (j8 = method230(k7, stream); j8 > ai[k7]; j8 = (j8 << 1) | byte11) {
						k7++;
						byte11 = method229(stream);
					}
					k5 = ai2[j8 - ai1[k7]];
				}
			}
			stream.anInt574 = 0;
			stream.aByte573 = 0;
			stream.anIntArray585[0] = 0;
			for (int j2 = 1; j2 <= 256; j2++) {
				stream.anIntArray585[j2] = stream.anIntArray583[j2 - 1];
			}
			for (int k2 = 1; k2 <= 256; k2++) {
				stream.anIntArray585[k2] += stream.anIntArray585[k2 - 1];
			}
			for (int l2 = 0; l2 < i6; l2++) {
				byte byte7 = (byte) (BZip2Stream.anIntArray587[l2] & 0xff);
				BZip2Stream.anIntArray587[stream.anIntArray585[byte7 & 0xff]] |= l2 << 8;
				stream.anIntArray585[byte7 & 0xff]++;
			}
			stream.anInt581 = BZip2Stream.anIntArray587[stream.anInt580] >> 8;
			stream.anInt584 = 0;
			stream.anInt581 = BZip2Stream.anIntArray587[stream.anInt581];
			stream.anInt582 = (byte) (stream.anInt581 & 0xff);
			stream.anInt581 >>= 8;
			stream.anInt584++;
			stream.anInt601 = i6;
			method226(stream);
			flag19 = (stream.anInt584 == (stream.anInt601 + 1)) && (stream.anInt574 == 0);
		}
	}

	public static byte method228(BZip2Stream stream) {
		return (byte) method230(8, stream);
	}

	public static byte method229(BZip2Stream stream) {
		return (byte) method230(1, stream);
	}

	public static int method230(int i, BZip2Stream stream) {
		int j;
		do {
			if (stream.anInt577 >= i) {
				int k = (stream.anInt576 >> (stream.anInt577 - i)) & ((1 << i) - 1);
				stream.anInt577 -= i;
				j = k;
				break;
			}
			stream.anInt576 = (stream.anInt576 << 8) | (stream.aByteArray563[stream.anInt564] & 0xff);
			stream.anInt577 += 8;
			stream.anInt564++;
			stream.anInt565--;
			stream.anInt566++;
			if (stream.anInt566 == 0) {
				stream.anInt567++;
			}
		} while (true);
		return j;
	}

	public static void method231(BZip2Stream stream) {
		stream.anInt588 = 0;
		for (int i = 0; i < 256; i++) {
			if (stream.aBooleanArray589[i]) {
				stream.aByteArray591[stream.anInt588] = (byte) i;
				stream.anInt588++;
			}
		}
	}

	public static void method232(int[] ai, int[] ai1, int[] ai2, byte[] abyte0, int i, int j, int k) {
		int l = 0;
		for (int i1 = i; i1 <= j; i1++) {
			for (int l2 = 0; l2 < k; l2++) {
				if (abyte0[l2] == i1) {
					ai2[l] = l2;
					l++;
				}
			}
		}
		for (int j1 = 0; j1 < 23; j1++) {
			ai1[j1] = 0;
		}
		for (int k1 = 0; k1 < k; k1++) {
			ai1[abyte0[k1] + 1]++;
		}
		for (int l1 = 1; l1 < 23; l1++) {
			ai1[l1] += ai1[l1 - 1];
		}
		for (int i2 = 0; i2 < 23; i2++) {
			ai[i2] = 0;
		}
		int i3 = 0;
		for (int j2 = i; j2 <= j; j2++) {
			i3 += ai1[j2 + 1] - ai1[j2];
			ai[j2] = i3 - 1;
			i3 <<= 1;
		}
		for (int k2 = i + 1; k2 <= j; k2++) {
			ai1[k2] = ((ai[k2 - 1] + 1) << 1) - ai1[k2];
		}
	}

}
