// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class41 {

	public static int anInt700;
	public static Class41[] aClass41Array701;
	public static int anInt702;
	public static int[] anIntArray703;

	public String unusedString;
	public int unusedInt0;
	public int unusedInt1;
	public boolean unusedBool0 = false;
	public boolean unusedBool1 = true;
	public int anInt709;
	public boolean unusedBool2 = false;
	public int unusedInt2;
	public int unusedInt3;
	public boolean unusedBool3 = false;
	public int unusedInt4 = -1;

	public Class41() {
	}

	public static void method546(Class44 class44) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("varp.dat", null));
		anInt702 = 0;
		anInt700 = class30_sub2_sub2.method410();
		if (aClass41Array701 == null) {
			aClass41Array701 = new Class41[anInt700];
		}
		if (anIntArray703 == null) {
			anIntArray703 = new int[anInt700];
		}
		for (int j = 0; j < anInt700; j++) {
			if (aClass41Array701[j] == null) {
				aClass41Array701[j] = new Class41();
			}
			aClass41Array701[j].method547(class30_sub2_sub2, j);
		}

		if (class30_sub2_sub2.anInt1406 != class30_sub2_sub2.aByteArray1405.length) {
			System.out.println("varptype load mismatch");
		}
	}

	public void method547(Class30_Sub2_Sub2 class30_sub2_sub2, int i) {
		do {
			int j = class30_sub2_sub2.method408();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				unusedInt0 = class30_sub2_sub2.method408();
			} else if (j == 2) {
				unusedInt1 = class30_sub2_sub2.method408();
			} else if (j == 3) {
				unusedBool0 = true;
				anIntArray703[anInt702++] = i;
			} else if (j == 4) {
				unusedBool1 = false;
			} else if (j == 5) {
				anInt709 = class30_sub2_sub2.method410();
			} else if (j == 6) {
				unusedBool2 = true;
			} else if (j == 7) {
				unusedInt2 = class30_sub2_sub2.method413();
			} else if (j == 8) {
				unusedInt3 = 1;
				unusedBool3 = true;
			} else if (j == 10) {
				unusedString = class30_sub2_sub2.method415();
			} else if (j == 11) {
				unusedBool3 = true;
			} else if (j == 12) {
				unusedInt4 = class30_sub2_sub2.method413();
			} else if (j == 13) {
				unusedInt3 = 2;
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
