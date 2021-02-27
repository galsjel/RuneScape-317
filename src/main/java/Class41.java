// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class41 {

	public static int anInt700;
	public static Class41[] aClass41Array701;
	public static int anInt702;
	public static int[] anIntArray703;
	public String aString704;
	public int anInt705;
	public int anInt706;
	public boolean aBoolean707 = false;
	public boolean aBoolean708 = true;
	public int anInt709;
	public boolean aBoolean710 = false;
	public int anInt711;
	public int anInt712;
	public boolean aBoolean713 = false;
	public int anInt714 = -1;

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
				anInt705 = class30_sub2_sub2.method408();
			} else if (j == 2) {
				anInt706 = class30_sub2_sub2.method408();
			} else if (j == 3) {
				aBoolean707 = true;
				anIntArray703[anInt702++] = i;
			} else if (j == 4) {
				aBoolean708 = false;
			} else if (j == 5) {
				anInt709 = class30_sub2_sub2.method410();
			} else if (j == 6) {
				aBoolean710 = true;
			} else if (j == 7) {
				anInt711 = class30_sub2_sub2.method413();
			} else if (j == 8) {
				anInt712 = 1;
				aBoolean713 = true;
			} else if (j == 10) {
				aString704 = class30_sub2_sub2.method415();
			} else if (j == 11) {
				aBoolean713 = true;
			} else if (j == 12) {
				anInt714 = class30_sub2_sub2.method413();
			} else if (j == 13) {
				anInt712 = 2;
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
