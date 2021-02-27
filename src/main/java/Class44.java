// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class44 {

	public byte[] aByteArray726;
	public int anInt727;
	public int[] anIntArray728;
	public int[] anIntArray729;
	public int[] anIntArray730;
	public int[] anIntArray731;
	public boolean aBoolean732;

	public Class44(byte[] abyte0) {
		method570(abyte0);
	}

	public void method570(byte[] abyte0) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(abyte0);
		int i = class30_sub2_sub2.method412();
		int j = class30_sub2_sub2.method412();
		if (j != i) {
			byte[] abyte1 = new byte[i];
			Class13.method225(abyte1, i, abyte0, j, 6);
			aByteArray726 = abyte1;
			class30_sub2_sub2 = new Class30_Sub2_Sub2(aByteArray726);
			aBoolean732 = true;
		} else {
			aByteArray726 = abyte0;
			aBoolean732 = false;
		}
		anInt727 = class30_sub2_sub2.method410();
		anIntArray728 = new int[anInt727];
		anIntArray729 = new int[anInt727];
		anIntArray730 = new int[anInt727];
		anIntArray731 = new int[anInt727];
		int k = class30_sub2_sub2.anInt1406 + anInt727 * 10;
		for (int l = 0; l < anInt727; l++) {
			anIntArray728[l] = class30_sub2_sub2.method413();
			anIntArray729[l] = class30_sub2_sub2.method412();
			anIntArray730[l] = class30_sub2_sub2.method412();
			anIntArray731[l] = k;
			k += anIntArray730[l];
		}
	}

	public byte[] method571(String s, byte[] abyte0) {
		int i = 0;
		s = s.toUpperCase();
		for (int j = 0; j < s.length(); j++) {
			i = (i * 61 + s.charAt(j)) - 32;
		}
		for (int k = 0; k < anInt727; k++) {
			if (anIntArray728[k] == i) {
				if (abyte0 == null) {
					abyte0 = new byte[anIntArray729[k]];
				}
				if (!aBoolean732) {
					Class13.method225(abyte0, anIntArray729[k], aByteArray726, anIntArray730[k], anIntArray731[k]);
				} else {
					for (int l = 0; l < anIntArray729[k]; l++) {
						abyte0[l] = aByteArray726[anIntArray731[k] + l];
					}
				}
				return abyte0;
			}
		}

		return null;
	}

}
