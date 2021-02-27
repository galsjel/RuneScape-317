// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class23 {

	public static int anInt402;
	public static Class23[] aClass23Array403;
	public static Class12 aClass12_415 = new Class12(30);
	public int anInt404;
	public int anInt405;
	public int anInt406 = -1;
	public Class20 aClass20_407;
	public final int[] anIntArray408 = new int[6];
	public final int[] anIntArray409 = new int[6];
	public int anInt410 = 128;
	public int anInt411 = 128;
	public int anInt412;
	public int anInt413;
	public int anInt414;

	public Class23() {
	}

	public static void method264(Class44 class44) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("spotanim.dat", null));
		anInt402 = class30_sub2_sub2.method410();
		if (aClass23Array403 == null) {
			aClass23Array403 = new Class23[anInt402];
		}
		for (int j = 0; j < anInt402; j++) {
			if (aClass23Array403[j] == null) {
				aClass23Array403[j] = new Class23();
			}
			aClass23Array403[j].anInt404 = j;
			aClass23Array403[j].method265(class30_sub2_sub2);
		}
	}

	public void method265(Class30_Sub2_Sub2 class30_sub2_sub2) {
		do {
			int i = class30_sub2_sub2.method408();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt405 = class30_sub2_sub2.method410();
			} else if (i == 2) {
				anInt406 = class30_sub2_sub2.method410();
				if (Class20.aClass20Array351 != null) {
					aClass20_407 = Class20.aClass20Array351[anInt406];
				}
			} else if (i == 4) {
				anInt410 = class30_sub2_sub2.method410();
			} else if (i == 5) {
				anInt411 = class30_sub2_sub2.method410();
			} else if (i == 6) {
				anInt412 = class30_sub2_sub2.method410();
			} else if (i == 7) {
				anInt413 = class30_sub2_sub2.method408();
			} else if (i == 8) {
				anInt414 = class30_sub2_sub2.method408();
			} else if (i >= 40 && i < 50) {
				anIntArray408[i - 40] = class30_sub2_sub2.method410();
			} else if (i >= 50 && i < 60) {
				anIntArray409[i - 50] = class30_sub2_sub2.method410();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Class30_Sub2_Sub4_Sub6 method266() {
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = (Class30_Sub2_Sub4_Sub6) aClass12_415.method222(anInt404);
		if (class30_sub2_sub4_sub6 != null) {
			return class30_sub2_sub4_sub6;
		}
		class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(anInt405);
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray408[0] != 0) {
				class30_sub2_sub4_sub6.method476(anIntArray408[i], anIntArray409[i]);
			}
		}

		aClass12_415.method223(class30_sub2_sub4_sub6, anInt404);
		return class30_sub2_sub4_sub6;
	}

}
