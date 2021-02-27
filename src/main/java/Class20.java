// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class20 {

	public static int anInt350;
	public static Class20[] aClass20Array351;
	public int anInt352;
	public int[] anIntArray353;
	public int[] anIntArray354;
	public int[] anIntArray355;
	public int anInt356 = -1;
	public int[] anIntArray357;
	public boolean aBoolean358 = false;
	public int anInt359 = 5;
	public int anInt360 = -1;
	public int anInt361 = -1;
	public int anInt362 = 99;
	public int anInt363 = -1;
	public int anInt364 = -1;
	public int anInt365 = 2;
	public int anInt366;

	public Class20() {
	}

	public static void method257(Class44 class44) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("seq.dat", null));
		anInt350 = class30_sub2_sub2.method410();
		if (aClass20Array351 == null) {
			aClass20Array351 = new Class20[anInt350];
		}
		for (int j = 0; j < anInt350; j++) {
			if (aClass20Array351[j] == null) {
				aClass20Array351[j] = new Class20();
			}
			aClass20Array351[j].method259(class30_sub2_sub2);
		}
	}

	public int method258(int i) {
		int j = anIntArray355[i];
		if (j == 0) {
			Class36 class36 = Class36.method531(anIntArray353[i]);
			if (class36 != null) {
				j = anIntArray355[i] = class36.anInt636;
			}
		}
		if (j == 0) {
			j = 1;
		}
		return j;
	}

	public void method259(Class30_Sub2_Sub2 class30_sub2_sub2) {
		do {
			int i = class30_sub2_sub2.method408();
			if (i == 0) {
				break;
			}
			if (i == 1) {
				anInt352 = class30_sub2_sub2.method408();
				anIntArray353 = new int[anInt352];
				anIntArray354 = new int[anInt352];
				anIntArray355 = new int[anInt352];
				for (int j = 0; j < anInt352; j++) {
					anIntArray353[j] = class30_sub2_sub2.method410();
					anIntArray354[j] = class30_sub2_sub2.method410();
					if (anIntArray354[j] == 65535) {
						anIntArray354[j] = -1;
					}
					anIntArray355[j] = class30_sub2_sub2.method410();
				}
			} else if (i == 2) {
				anInt356 = class30_sub2_sub2.method410();
			} else if (i == 3) {
				int k = class30_sub2_sub2.method408();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++) {
					anIntArray357[l] = class30_sub2_sub2.method408();
				}

				anIntArray357[k] = 0x98967f;
			} else if (i == 4) {
				aBoolean358 = true;
			} else if (i == 5) {
				anInt359 = class30_sub2_sub2.method408();
			} else if (i == 6) {
				anInt360 = class30_sub2_sub2.method410();
			} else if (i == 7) {
				anInt361 = class30_sub2_sub2.method410();
			} else if (i == 8) {
				anInt362 = class30_sub2_sub2.method408();
			} else if (i == 9) {
				anInt363 = class30_sub2_sub2.method408();
			} else if (i == 10) {
				anInt364 = class30_sub2_sub2.method408();
			} else if (i == 11) {
				anInt365 = class30_sub2_sub2.method408();
			} else if (i == 12) {
				anInt366 = class30_sub2_sub2.method413();
			} else {
				System.out.println("Error unrecognised seq config code: " + i);
			}
		} while (true);
		if (anInt352 == 0) {
			anInt352 = 1;
			anIntArray353 = new int[1];
			anIntArray353[0] = -1;
			anIntArray354 = new int[1];
			anIntArray354[0] = -1;
			anIntArray355 = new int[1];
			anIntArray355[0] = -1;
		}
		if (anInt363 == -1) {
			if (anIntArray357 != null) {
				anInt363 = 2;
			} else {
				anInt363 = 0;
			}
		}
		if (anInt364 == -1) {
			if (anIntArray357 != null) {
				anInt364 = 2;
				return;
			}
			anInt364 = 0;
		}
	}

}
