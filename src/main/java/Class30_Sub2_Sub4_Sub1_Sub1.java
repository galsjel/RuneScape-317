// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class30_Sub2_Sub4_Sub1_Sub1 extends Class30_Sub2_Sub4_Sub1 {

	public int anInt1693;
	public boolean aBoolean1694 = false;
	public Class5 aClass5_1696;

	public Class30_Sub2_Sub4_Sub1_Sub1() {
	}

	public Class30_Sub2_Sub4_Sub6 method450() {
		if (super.anInt1526 >= 0 && super.anInt1529 == 0) {
			int k = Class20.aClass20Array351[super.anInt1526].anIntArray353[super.anInt1527];
			int i1 = -1;
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = Class20.aClass20Array351[super.anInt1517].anIntArray353[super.anInt1518];
			}
			return aClass5_1696.method164(i1, k, Class20.aClass20Array351[super.anInt1526].anIntArray357);
		}
		int l = -1;
		if (super.anInt1517 >= 0) {
			l = Class20.aClass20Array351[super.anInt1517].anIntArray353[super.anInt1518];
		}
		return aClass5_1696.method164(-1, l, null);
	}

	public Class30_Sub2_Sub4_Sub6 method444(int i) {
		if (aClass5_1696 == null) {
			return null;
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = method450();
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		super.anInt1507 = class30_sub2_sub4_sub6.anInt1426;
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			Class23 class23 = Class23.aClass23Array403[super.anInt1520];
			Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = class23.method266();
			if (class30_sub2_sub4_sub6_1 != null) {
				int j = class23.aClass20_407.anIntArray353[super.anInt1521];
				Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_2 = new Class30_Sub2_Sub4_Sub6(true, Class36.method532(j), false, class30_sub2_sub4_sub6_1);
				class30_sub2_sub4_sub6_2.method475(0, -super.anInt1524, 0);
				class30_sub2_sub4_sub6_2.method469();
				class30_sub2_sub4_sub6_2.method470(j);
				class30_sub2_sub4_sub6_2.anIntArrayArray1658 = null;
				class30_sub2_sub4_sub6_2.anIntArrayArray1657 = null;
				if (class23.anInt410 != 128 || class23.anInt411 != 128) {
					class30_sub2_sub4_sub6_2.method478(class23.anInt410, class23.anInt410, class23.anInt411);
				}
				class30_sub2_sub4_sub6_2.method479(64 + class23.anInt413, 850 + class23.anInt414, -30, -50, -30, true);
				Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = {class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_2};
				class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(2, -819, aclass30_sub2_sub4_sub6);
			}
		}
		if (aClass5_1696.aByte68 == 1) {
			class30_sub2_sub4_sub6.aBoolean1659 = true;
		}
		return class30_sub2_sub4_sub6;
	}

	public boolean method449(boolean flag) {
		return aClass5_1696 != null;
	}

}
