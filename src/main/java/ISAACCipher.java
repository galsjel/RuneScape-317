// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ISAACCipher {

	public int anInt334;
	public final int[] anIntArray335;
	public final int[] anIntArray336;
	public int anInt337;
	public int anInt338;
	public int anInt339;

	public ISAACCipher(int[] ai) {
		anIntArray336 = new int[256];
		anIntArray335 = new int[256];
		for (int j = 0; j < ai.length; j++) {
			anIntArray335[j] = ai[j];
		}
		method248();
	}

	public int method246() {
		if (anInt334-- == 0) {
			method247();
			anInt334 = 255;
		}
		return anIntArray335[anInt334];
	}

	public void method247() {
		anInt338 += ++anInt339;
		for (int i = 0; i < 256; i++) {
			int j = anIntArray336[i];
			if ((i & 3) == 0) {
				anInt337 ^= anInt337 << 13;
			} else if ((i & 3) == 1) {
				anInt337 ^= anInt337 >>> 6;
			} else if ((i & 3) == 2) {
				anInt337 ^= anInt337 << 2;
			} else if ((i & 3) == 3) {
				anInt337 ^= anInt337 >>> 16;
			}
			anInt337 += anIntArray336[(i + 128) & 0xff];
			int k;
			anIntArray336[i] = k = anIntArray336[(j & 0x3fc) >> 2] + anInt337 + anInt338;
			anIntArray335[i] = anInt338 = anIntArray336[((k >> 8) & 0x3fc) >> 2] + j;
		}
	}

	public void method248() {
		int i1;
		int j1;
		int k1;
		int l1;
		int i2;
		int j2;
		int k2;
		int l = i1 = j1 = k1 = l1 = i2 = j2 = k2 = 0x9e3779b9;
		for (int i = 0; i < 4; i++) {
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
		}
		for (int j = 0; j < 256; j += 8) {
			l += anIntArray335[j];
			i1 += anIntArray335[j + 1];
			j1 += anIntArray335[j + 2];
			k1 += anIntArray335[j + 3];
			l1 += anIntArray335[j + 4];
			i2 += anIntArray335[j + 5];
			j2 += anIntArray335[j + 6];
			k2 += anIntArray335[j + 7];
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
			anIntArray336[j] = l;
			anIntArray336[j + 1] = i1;
			anIntArray336[j + 2] = j1;
			anIntArray336[j + 3] = k1;
			anIntArray336[j + 4] = l1;
			anIntArray336[j + 5] = i2;
			anIntArray336[j + 6] = j2;
			anIntArray336[j + 7] = k2;
		}
		for (int k = 0; k < 256; k += 8) {
			l += anIntArray336[k];
			i1 += anIntArray336[k + 1];
			j1 += anIntArray336[k + 2];
			k1 += anIntArray336[k + 3];
			l1 += anIntArray336[k + 4];
			i2 += anIntArray336[k + 5];
			j2 += anIntArray336[k + 6];
			k2 += anIntArray336[k + 7];
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
			anIntArray336[k] = l;
			anIntArray336[k + 1] = i1;
			anIntArray336[k + 2] = j1;
			anIntArray336[k + 3] = k1;
			anIntArray336[k + 4] = l1;
			anIntArray336[k + 5] = i2;
			anIntArray336[k + 6] = j2;
			anIntArray336[k + 7] = k2;
		}
		method247();
		anInt334 = 256;
	}

}
