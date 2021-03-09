// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class BZip2 {

	public static final BZip2Context context = new BZip2Context();

	public static void inflate(byte[] abyte0, int i, byte[] abyte1, int j, int k) {
		synchronized (context) {
			context.aByteArray563 = abyte1;
			context.anInt564 = k;
			context.aByteArray568 = abyte0;
			context.anInt569 = 0;
			context.anInt565 = j;
			context.anInt570 = i;
			context.anInt577 = 0;
			context.anInt576 = 0;
			context.anInt566 = 0;
			context.anInt567 = 0;
			context.anInt571 = 0;
			context.anInt572 = 0;
			context.anInt579 = 0;
			method227(context);
		}
	}

	public static void method226(BZip2Context block) {
		byte byte4 = block.aByte573;
		int i = block.anInt574;
		int j = block.anInt584;
		int k = block.anInt582;
		int[] ai = BZip2Context.anIntArray587;
		int l = block.anInt581;
		byte[] abyte0 = block.aByteArray568;
		int i1 = block.anInt569;
		int j1 = block.anInt570;
		int k1 = j1;
		int l1 = block.anInt601 + 1;
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
		int i2 = block.anInt571;
		block.anInt571 += k1 - j1;
		if (block.anInt571 < i2) {
			block.anInt572++;
		}
		block.aByte573 = byte4;
		block.anInt574 = i;
		block.anInt584 = j;
		block.anInt582 = k;
		BZip2Context.anIntArray587 = ai;
		block.anInt581 = l;
		block.aByteArray568 = abyte0;
		block.anInt569 = i1;
		block.anInt570 = j1;
	}

	public static void method227(BZip2Context block) {
		int k8 = 0;
		int[] ai = null;
		int[] ai1 = null;
		int[] ai2 = null;
		block.anInt578 = 1;
		if (BZip2Context.anIntArray587 == null) {
			BZip2Context.anIntArray587 = new int[block.anInt578 * 0x186a0];
		}
		boolean flag19 = true;
		while (flag19) {
			byte byte0 = method228(block);
			if (byte0 == 23) {
				return;
			}
			method228(block);
			method228(block);
			method228(block);
			method228(block);
			method228(block);
			block.anInt579++;
			method228(block);
			method228(block);
			method228(block);
			method228(block);
			byte0 = method229(block);
			block.aBoolean575 = byte0 != 0;
			if (block.aBoolean575) {
				System.out.println("PANIC! RANDOMISED BLOCK!");
			}
			block.anInt580 = 0;
			byte0 = method228(block);
			block.anInt580 = (block.anInt580 << 8) | (byte0 & 0xff);
			byte0 = method228(block);
			block.anInt580 = (block.anInt580 << 8) | (byte0 & 0xff);
			byte0 = method228(block);
			block.anInt580 = (block.anInt580 << 8) | (byte0 & 0xff);
			for (int j = 0; j < 16; j++) {
				byte byte1 = method229(block);
				block.aBooleanArray590[j] = byte1 == 1;
			}
			for (int k = 0; k < 256; k++) {
				block.aBooleanArray589[k] = false;
			}
			for (int l = 0; l < 16; l++) {
				if (block.aBooleanArray590[l]) {
					for (int i3 = 0; i3 < 16; i3++) {
						byte byte2 = method229(block);
						if (byte2 == 1) {
							block.aBooleanArray589[(l * 16) + i3] = true;
						}
					}
				}
			}
			method231(block);
			int i4 = block.anInt588 + 2;
			int j4 = method230(3, block);
			int k4 = method230(15, block);
			for (int i1 = 0; i1 < k4; i1++) {
				int j3 = 0;
				do {
					byte byte3 = method229(block);
					if (byte3 == 0) {
						break;
					}
					j3++;
				} while (true);
				block.aByteArray595[i1] = (byte) j3;
			}
			byte[] abyte0 = new byte[6];
			for (byte byte16 = 0; byte16 < j4; byte16++) {
				abyte0[byte16] = byte16;
			}
			for (int j1 = 0; j1 < k4; j1++) {
				byte byte17 = block.aByteArray595[j1];
				byte byte15 = abyte0[byte17];
				for (; byte17 > 0; byte17--) {
					abyte0[byte17] = abyte0[byte17 - 1];
				}
				abyte0[0] = byte15;
				block.aByteArray594[j1] = byte15;
			}
			for (int k3 = 0; k3 < j4; k3++) {
				int l6 = method230(5, block);
				for (int k1 = 0; k1 < i4; k1++) {
					do {
						byte byte4 = method229(block);
						if (byte4 == 0) {
							break;
						}
						byte4 = method229(block);
						if (byte4 == 0) {
							l6++;
						} else {
							l6--;
						}
					} while (true);
					block.aByteArrayArray596[k3][k1] = (byte) l6;
				}
			}
			for (int l3 = 0; l3 < j4; l3++) {
				byte byte8 = 32;
				int i = 0;
				for (int l1 = 0; l1 < i4; l1++) {
					if (block.aByteArrayArray596[l3][l1] > i) {
						i = block.aByteArrayArray596[l3][l1];
					}
					if (block.aByteArrayArray596[l3][l1] < byte8) {
						byte8 = block.aByteArrayArray596[l3][l1];
					}
				}
				method232(block.anIntArrayArray597[l3], block.anIntArrayArray598[l3], block.anIntArrayArray599[l3], block.aByteArrayArray596[l3], byte8, i, i4);
				block.anIntArray600[l3] = byte8;
			}
			int l4 = block.anInt588 + 1;
			int i5 = -1;
			int j5 = 0;
			for (int i2 = 0; i2 <= 255; i2++) {
				block.anIntArray583[i2] = 0;
			}
			int j9 = 4095;
			for (int l8 = 15; l8 >= 0; l8--) {
				for (int i9 = 15; i9 >= 0; i9--) {
					block.aByteArray592[j9] = (byte) ((l8 * 16) + i9);
					j9--;
				}
				block.anIntArray593[l8] = j9 + 1;
			}
			int i6 = 0;
			if (j5 == 0) {
				i5++;
				j5 = 50;
				byte byte12 = block.aByteArray594[i5];
				k8 = block.anIntArray600[byte12];
				ai = block.anIntArrayArray597[byte12];
				ai2 = block.anIntArrayArray599[byte12];
				ai1 = block.anIntArrayArray598[byte12];
			}
			j5--;
			int i7 = k8;
			int l7;
			byte byte9;
			for (l7 = method230(i7, block); l7 > ai[i7]; l7 = (l7 << 1) | byte9) {
				i7++;
				byte9 = method229(block);
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
							byte byte13 = block.aByteArray594[i5];
							k8 = block.anIntArray600[byte13];
							ai = block.anIntArrayArray597[byte13];
							ai2 = block.anIntArrayArray599[byte13];
							ai1 = block.anIntArrayArray598[byte13];
						}
						j5--;
						int j7 = k8;
						int i8;
						byte byte10;
						for (i8 = method230(j7, block); i8 > ai[j7]; i8 = (i8 << 1) | byte10) {
							j7++;
							byte10 = method229(block);
						}
						k5 = ai2[i8 - ai1[j7]];
					} while ((k5 == 0) || (k5 == 1));
					j6++;
					byte byte5 = block.aByteArray591[block.aByteArray592[block.anIntArray593[0]] & 0xff];
					block.anIntArray583[byte5 & 0xff] += j6;
					for (; j6 > 0; j6--) {
						BZip2Context.anIntArray587[i6] = byte5 & 0xff;
						i6++;
					}
				} else {
					int j11 = k5 - 1;
					byte byte6;
					if (j11 < 16) {
						int j10 = block.anIntArray593[0];
						byte6 = block.aByteArray592[j10 + j11];
						for (; j11 > 3; j11 -= 4) {
							int k11 = j10 + j11;
							block.aByteArray592[k11] = block.aByteArray592[k11 - 1];
							block.aByteArray592[k11 - 1] = block.aByteArray592[k11 - 2];
							block.aByteArray592[k11 - 2] = block.aByteArray592[k11 - 3];
							block.aByteArray592[k11 - 3] = block.aByteArray592[k11 - 4];
						}
						for (; j11 > 0; j11--) {
							block.aByteArray592[j10 + j11] = block.aByteArray592[(j10 + j11) - 1];
						}
						block.aByteArray592[j10] = byte6;
					} else {
						int l10 = j11 / 16;
						int i11 = j11 % 16;
						int k10 = block.anIntArray593[l10] + i11;
						byte6 = block.aByteArray592[k10];
						for (; k10 > block.anIntArray593[l10]; k10--) {
							block.aByteArray592[k10] = block.aByteArray592[k10 - 1];
						}
						block.anIntArray593[l10]++;
						for (; l10 > 0; l10--) {
							block.anIntArray593[l10]--;
							block.aByteArray592[block.anIntArray593[l10]] = block.aByteArray592[(block.anIntArray593[l10 - 1] + 16) - 1];
						}
						block.anIntArray593[0]--;
						block.aByteArray592[block.anIntArray593[0]] = byte6;
						if (block.anIntArray593[0] == 0) {
							int i10 = 4095;
							for (int k9 = 15; k9 >= 0; k9--) {
								for (int l9 = 15; l9 >= 0; l9--) {
									block.aByteArray592[i10] = block.aByteArray592[block.anIntArray593[k9] + l9];
									i10--;
								}
								block.anIntArray593[k9] = i10 + 1;
							}
						}
					}
					block.anIntArray583[block.aByteArray591[byte6 & 0xff] & 0xff]++;
					BZip2Context.anIntArray587[i6] = block.aByteArray591[byte6 & 0xff] & 0xff;
					i6++;
					if (j5 == 0) {
						i5++;
						j5 = 50;
						byte byte14 = block.aByteArray594[i5];
						k8 = block.anIntArray600[byte14];
						ai = block.anIntArrayArray597[byte14];
						ai2 = block.anIntArrayArray599[byte14];
						ai1 = block.anIntArrayArray598[byte14];
					}
					j5--;
					int k7 = k8;
					int j8;
					byte byte11;
					for (j8 = method230(k7, block); j8 > ai[k7]; j8 = (j8 << 1) | byte11) {
						k7++;
						byte11 = method229(block);
					}
					k5 = ai2[j8 - ai1[k7]];
				}
			}
			block.anInt574 = 0;
			block.aByte573 = 0;
			block.anIntArray585[0] = 0;
			for (int j2 = 1; j2 <= 256; j2++) {
				block.anIntArray585[j2] = block.anIntArray583[j2 - 1];
			}
			for (int k2 = 1; k2 <= 256; k2++) {
				block.anIntArray585[k2] += block.anIntArray585[k2 - 1];
			}
			for (int l2 = 0; l2 < i6; l2++) {
				byte byte7 = (byte) (BZip2Context.anIntArray587[l2] & 0xff);
				BZip2Context.anIntArray587[block.anIntArray585[byte7 & 0xff]] |= l2 << 8;
				block.anIntArray585[byte7 & 0xff]++;
			}
			block.anInt581 = BZip2Context.anIntArray587[block.anInt580] >> 8;
			block.anInt584 = 0;
			block.anInt581 = BZip2Context.anIntArray587[block.anInt581];
			block.anInt582 = (byte) (block.anInt581 & 0xff);
			block.anInt581 >>= 8;
			block.anInt584++;
			block.anInt601 = i6;
			method226(block);
			flag19 = (block.anInt584 == (block.anInt601 + 1)) && (block.anInt574 == 0);
		}
	}

	public static byte method228(BZip2Context block) {
		return (byte) method230(8, block);
	}

	public static byte method229(BZip2Context block) {
		return (byte) method230(1, block);
	}

	public static int method230(int i, BZip2Context block) {
		int j;
		do {
			if (block.anInt577 >= i) {
				int k = (block.anInt576 >> (block.anInt577 - i)) & ((1 << i) - 1);
				block.anInt577 -= i;
				j = k;
				break;
			}
			block.anInt576 = (block.anInt576 << 8) | (block.aByteArray563[block.anInt564] & 0xff);
			block.anInt577 += 8;
			block.anInt564++;
			block.anInt565--;
			block.anInt566++;
			if (block.anInt566 == 0) {
				block.anInt567++;
			}
		} while (true);
		return j;
	}

	public static void method231(BZip2Context block) {
		block.anInt588 = 0;
		for (int i = 0; i < 256; i++) {
			if (block.aBooleanArray589[i]) {
				block.aByteArray591[block.anInt588] = (byte) i;
				block.anInt588++;
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
