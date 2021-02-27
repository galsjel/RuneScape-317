// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class30_Sub2_Sub4_Sub3 extends Class30_Sub2_Sub4 {

	public final int anInt1560;
	public final int anInt1561;
	public final int anInt1562;
	public final int anInt1563;
	public final int anInt1564;
	public boolean aBoolean1567 = false;
	public final Class23 aClass23_1568;
	public int anInt1569;
	public int anInt1570;

	public Class30_Sub2_Sub4_Sub3(int i, int j, int l, int i1, int j1, int k1, int l1) {
		aClass23_1568 = Class23.aClass23Array403[i1];
		anInt1560 = i;
		anInt1561 = l1;
		anInt1562 = k1;
		anInt1563 = j1;
		anInt1564 = j + l;
	}

	public Class30_Sub2_Sub4_Sub6 method444(int i) {
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = aClass23_1568.method266();
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		int j = aClass23_1568.aClass20_407.anIntArray353[anInt1569];
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = new Class30_Sub2_Sub4_Sub6(true, Class36.method532(j), false, class30_sub2_sub4_sub6);
		if (!aBoolean1567) {
			class30_sub2_sub4_sub6_1.method469();
			class30_sub2_sub4_sub6_1.method470(j);
			class30_sub2_sub4_sub6_1.anIntArrayArray1658 = null;
			class30_sub2_sub4_sub6_1.anIntArrayArray1657 = null;
		}
		if (aClass23_1568.anInt410 != 128 || aClass23_1568.anInt411 != 128) {
			class30_sub2_sub4_sub6_1.method478(aClass23_1568.anInt410, aClass23_1568.anInt410, aClass23_1568.anInt411);
		}
		if (aClass23_1568.anInt412 != 0) {
			if (aClass23_1568.anInt412 == 90) {
				class30_sub2_sub4_sub6_1.method473();
			}
			if (aClass23_1568.anInt412 == 180) {
				class30_sub2_sub4_sub6_1.method473();
				class30_sub2_sub4_sub6_1.method473();
			}
			if (aClass23_1568.anInt412 == 270) {
				class30_sub2_sub4_sub6_1.method473();
				class30_sub2_sub4_sub6_1.method473();
				class30_sub2_sub4_sub6_1.method473();
			}
		}
		class30_sub2_sub4_sub6_1.method479(64 + aClass23_1568.anInt413, 850 + aClass23_1568.anInt414, -30, -50, -30, true);
		return class30_sub2_sub4_sub6_1;
	}

	public void method454(int i) {
		for (anInt1570 += i; anInt1570 > aClass23_1568.aClass20_407.method258(anInt1569); ) {
			anInt1570 -= aClass23_1568.aClass20_407.method258(anInt1569) + 1;
			anInt1569++;
			if (anInt1569 >= aClass23_1568.aClass20_407.anInt352 && (anInt1569 < 0 || anInt1569 >= aClass23_1568.aClass20_407.anInt352)) {
				anInt1569 = 0;
				aBoolean1567 = true;
			}
		}
	}

}
