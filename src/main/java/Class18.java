// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class18 {

	public final int anInt341;
	public final int[] anIntArray342;
	public final int[][] anIntArrayArray343;

	public Class18(Class30_Sub2_Sub2 class30_sub2_sub2) {
		anInt341 = class30_sub2_sub2.method408();
		anIntArray342 = new int[anInt341];
		anIntArrayArray343 = new int[anInt341][];
		for (int j = 0; j < anInt341; j++) {
			anIntArray342[j] = class30_sub2_sub2.method408();
		}

		for (int k = 0; k < anInt341; k++) {
			int l = class30_sub2_sub2.method408();
			anIntArrayArray343[k] = new int[l];
			for (int i1 = 0; i1 < l; i1++) {
				anIntArrayArray343[k][i1] = class30_sub2_sub2.method408();
			}
		}
	}

}
