// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SoundTone {

	public static int[] anIntArray115;
	public static int[] anIntArray116;
	public static int[] anIntArray117;
	public static final int[] anIntArray118 = new int[5];
	public static final int[] anIntArray119 = new int[5];
	public static final int[] anIntArray120 = new int[5];
	public static final int[] anIntArray121 = new int[5];
	public static final int[] anIntArray122 = new int[5];
	public SoundEnvelope aEnvelope_98;
	public SoundEnvelope aEnvelope_99;
	public SoundEnvelope aEnvelope_100;
	public SoundEnvelope aEnvelope_101;
	public SoundEnvelope aEnvelope_102;
	public SoundEnvelope aEnvelope_103;
	public SoundEnvelope aEnvelope_104;
	public SoundEnvelope aEnvelope_105;
	public final int[] anIntArray106 = new int[5];
	public final int[] anIntArray107 = new int[5];
	public final int[] anIntArray108 = new int[5];
	public int anInt109;
	public int anInt110 = 100;
	public SoundFilter aSoundFilter_111;
	public SoundEnvelope aEnvelope_112;
	public int anInt113 = 500;
	public int anInt114;

	public SoundTone() {
	}

	public static void method166() {
		anIntArray116 = new int[32768];
		for (int i = 0; i < 32768; i++) {
			if (Math.random() > 0.5D) {
				anIntArray116[i] = 1;
			} else {
				anIntArray116[i] = -1;
			}
		}
		anIntArray117 = new int[32768];
		for (int j = 0; j < 32768; j++) {
			anIntArray117[j] = (int) (Math.sin((double) j / 5215.1903000000002D) * 16384D);
		}
		anIntArray115 = new int[0x35d54];
	}

	public int[] method167(int i, int j) {
		for (int k = 0; k < i; k++) {
			anIntArray115[k] = 0;
		}
		if (j < 10) {
			return anIntArray115;
		}
		double d = (double) i / ((double) j + 0.0D);
		aEnvelope_98.method327();
		aEnvelope_99.method327();
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		if (aEnvelope_100 != null) {
			aEnvelope_100.method327();
			aEnvelope_101.method327();
			l = (int) (((double) (aEnvelope_100.anInt539 - aEnvelope_100.anInt538) * 32.768000000000001D) / d);
			i1 = (int) (((double) aEnvelope_100.anInt538 * 32.768000000000001D) / d);
		}
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		if (aEnvelope_102 != null) {
			aEnvelope_102.method327();
			aEnvelope_103.method327();
			k1 = (int) (((double) (aEnvelope_102.anInt539 - aEnvelope_102.anInt538) * 32.768000000000001D) / d);
			l1 = (int) (((double) aEnvelope_102.anInt538 * 32.768000000000001D) / d);
		}
		for (int j2 = 0; j2 < 5; j2++) {
			if (anIntArray106[j2] != 0) {
				anIntArray118[j2] = 0;
				anIntArray119[j2] = (int) ((double) anIntArray108[j2] * d);
				anIntArray120[j2] = (anIntArray106[j2] << 14) / 100;
				anIntArray121[j2] = (int) (((double) (aEnvelope_98.anInt539 - aEnvelope_98.anInt538) * 32.768000000000001D * Math.pow(1.0057929410678534D, anIntArray107[j2])) / d);
				anIntArray122[j2] = (int) (((double) aEnvelope_98.anInt538 * 32.768000000000001D) / d);
			}
		}
		for (int k2 = 0; k2 < i; k2++) {
			int l2 = aEnvelope_98.method328(i);
			int j4 = aEnvelope_99.method328(i);
			if (aEnvelope_100 != null) {
				int j5 = aEnvelope_100.method328(i);
				int j6 = aEnvelope_101.method328(i);
				l2 += method168(j6, j1, aEnvelope_100.anInt540) >> 1;
				j1 += (j5 * l >> 16) + i1;
			}
			if (aEnvelope_102 != null) {
				int k5 = aEnvelope_102.method328(i);
				int k6 = aEnvelope_103.method328(i);
				j4 = j4 * ((method168(k6, i2, aEnvelope_102.anInt540) >> 1) + 32768) >> 15;
				i2 += (k5 * k1 >> 16) + l1;
			}
			for (int l5 = 0; l5 < 5; l5++) {
				if (anIntArray106[l5] != 0) {
					int l6 = k2 + anIntArray119[l5];
					if (l6 < i) {
						anIntArray115[l6] += method168(j4 * anIntArray120[l5] >> 15, anIntArray118[l5], aEnvelope_98.anInt540);
						anIntArray118[l5] += (l2 * anIntArray121[l5] >> 16) + anIntArray122[l5];
					}
				}
			}
		}
		if (aEnvelope_104 != null) {
			aEnvelope_104.method327();
			aEnvelope_105.method327();
			int i3 = 0;
			boolean flag1 = true;
			for (int i7 = 0; i7 < i; i7++) {
				int k7 = aEnvelope_104.method328(i);
				int i8 = aEnvelope_105.method328(i);
				int k4;
				if (flag1) {
					k4 = aEnvelope_104.anInt538 + ((aEnvelope_104.anInt539 - aEnvelope_104.anInt538) * k7 >> 8);
				} else {
					k4 = aEnvelope_104.anInt538 + ((aEnvelope_104.anInt539 - aEnvelope_104.anInt538) * i8 >> 8);
				}
				if ((i3 += 256) >= k4) {
					i3 = 0;
					flag1 = !flag1;
				}
				if (flag1) {
					anIntArray115[i7] = 0;
				}
			}
		}
		if (anInt109 > 0 && anInt110 > 0) {
			int j3 = (int) ((double) anInt109 * d);
			for (int l4 = j3; l4 < i; l4++) {
				anIntArray115[l4] += (anIntArray115[l4 - j3] * anInt110) / 100;
			}
		}
		if (aSoundFilter_111.anIntArray665[0] > 0 || aSoundFilter_111.anIntArray665[1] > 0) {
			aEnvelope_112.method327();
			int k3 = aEnvelope_112.method328(i + 1);
			int i5 = aSoundFilter_111.method544(0, (float) k3 / 65536F);
			int i6 = aSoundFilter_111.method544(1, (float) k3 / 65536F);
			if (i >= i5 + i6) {
				int j7 = 0;
				int l7 = i6;
				if (l7 > i - i5) {
					l7 = i - i5;
				}
				for (; j7 < l7; j7++) {
					int j8 = (int) ((long) anIntArray115[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
					for (int k8 = 0; k8 < i5; k8++) {
						j8 += (int) ((long) anIntArray115[(j7 + i5) - 1 - k8] * (long) SoundFilter.anIntArrayArray670[0][k8] >> 16);
					}
					for (int j9 = 0; j9 < j7; j9++) {
						j8 -= (int) ((long) anIntArray115[j7 - 1 - j9] * (long) SoundFilter.anIntArrayArray670[1][j9] >> 16);
					}
					anIntArray115[j7] = j8;
					k3 = aEnvelope_112.method328(i + 1);
				}
				char c = '\200';
				l7 = c;
				do {
					if (l7 > i - i5) {
						l7 = i - i5;
					}
					for (; j7 < l7; j7++) {
						int l8 = (int) ((long) anIntArray115[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
						for (int k9 = 0; k9 < i5; k9++) {
							l8 += (int) ((long) anIntArray115[(j7 + i5) - 1 - k9] * (long) SoundFilter.anIntArrayArray670[0][k9] >> 16);
						}
						for (int i10 = 0; i10 < i6; i10++) {
							l8 -= (int) ((long) anIntArray115[j7 - 1 - i10] * (long) SoundFilter.anIntArrayArray670[1][i10] >> 16);
						}
						anIntArray115[j7] = l8;
						k3 = aEnvelope_112.method328(i + 1);
					}
					if (j7 >= i - i5) {
						break;
					}
					i5 = aSoundFilter_111.method544(0, (float) k3 / 65536F);
					i6 = aSoundFilter_111.method544(1, (float) k3 / 65536F);
					l7 += c;
				} while (true);
				for (; j7 < i; j7++) {
					int i9 = 0;
					for (int l9 = (j7 + i5) - i; l9 < i5; l9++) {
						i9 += (int) ((long) anIntArray115[(j7 + i5) - 1 - l9] * (long) SoundFilter.anIntArrayArray670[0][l9] >> 16);
					}
					for (int j10 = 0; j10 < i6; j10++) {
						i9 -= (int) ((long) anIntArray115[j7 - 1 - j10] * (long) SoundFilter.anIntArrayArray670[1][j10] >> 16);
					}
					anIntArray115[j7] = i9;
					aEnvelope_112.method328(i + 1);
				}
			}
		}
		for (int i4 = 0; i4 < i; i4++) {
			if (anIntArray115[i4] < -32768) {
				anIntArray115[i4] = -32768;
			}
			if (anIntArray115[i4] > 32767) {
				anIntArray115[i4] = 32767;
			}
		}
		return anIntArray115;
	}

	public int method168(int i, int k, int l) {
		if (l == 1) {
			if ((k & 0x7fff) < 16384) {
				return i;
			} else {
				return -i;
			}
		}
		if (l == 2) {
			return anIntArray117[k & 0x7fff] * i >> 14;
		}
		if (l == 3) {
			return ((k & 0x7fff) * i >> 14) - i;
		}
		if (l == 4) {
			return anIntArray116[k / 2607 & 0x7fff] * i;
		} else {
			return 0;
		}
	}

	public void method169(Buffer buffer) {
		aEnvelope_98 = new SoundEnvelope();
		aEnvelope_98.method325(buffer);
		aEnvelope_99 = new SoundEnvelope();
		aEnvelope_99.method325(buffer);
		int i = buffer.method408();
		if (i != 0) {
			buffer.anInt1406--;
			aEnvelope_100 = new SoundEnvelope();
			aEnvelope_100.method325(buffer);
			aEnvelope_101 = new SoundEnvelope();
			aEnvelope_101.method325(buffer);
		}
		i = buffer.method408();
		if (i != 0) {
			buffer.anInt1406--;
			aEnvelope_102 = new SoundEnvelope();
			aEnvelope_102.method325(buffer);
			aEnvelope_103 = new SoundEnvelope();
			aEnvelope_103.method325(buffer);
		}
		i = buffer.method408();
		if (i != 0) {
			buffer.anInt1406--;
			aEnvelope_104 = new SoundEnvelope();
			aEnvelope_104.method325(buffer);
			aEnvelope_105 = new SoundEnvelope();
			aEnvelope_105.method325(buffer);
		}
		for (int j = 0; j < 10; j++) {
			int k = buffer.method422();
			if (k == 0) {
				break;
			}
			anIntArray106[j] = k;
			anIntArray107[j] = buffer.method421();
			anIntArray108[j] = buffer.method422();
		}
		anInt109 = buffer.method422();
		anInt110 = buffer.method422();
		anInt113 = buffer.method410();
		anInt114 = buffer.method410();
		aSoundFilter_111 = new SoundFilter();
		aEnvelope_112 = new SoundEnvelope();
		aSoundFilter_111.method545(buffer, aEnvelope_112);
	}

}
