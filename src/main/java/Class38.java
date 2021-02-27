// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class38 {

	public static int anInt655;
	public static Class38[] aClass38Array656;
	public int anInt657 = -1;
	public int[] anIntArray658;
	public final int[] anIntArray659 = new int[6];
	public final int[] anIntArray660 = new int[6];
	public final int[] anIntArray661 = {-1, -1, -1, -1, -1};
	public boolean aBoolean662 = false;

	public Class38() {
	}

	public static void method535(Class44 class44) {
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("idk.dat", null));
		anInt655 = class30_sub2_sub2.method410();
		if (aClass38Array656 == null) {
			aClass38Array656 = new Class38[anInt655];
		}
		for (int j = 0; j < anInt655; j++) {
			if (aClass38Array656[j] == null) {
				aClass38Array656[j] = new Class38();
			}
			aClass38Array656[j].method536(class30_sub2_sub2);
		}
	}

	public void method536(Class30_Sub2_Sub2 class30_sub2_sub2) {
		do {
			int i = class30_sub2_sub2.method408();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt657 = class30_sub2_sub2.method408();
			} else if (i == 2) {
				int j = class30_sub2_sub2.method408();
				anIntArray658 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray658[k] = class30_sub2_sub2.method410();
				}
			} else if (i == 3) {
				aBoolean662 = true;
			} else if (i >= 40 && i < 50) {
				anIntArray659[i - 40] = class30_sub2_sub2.method410();
			} else if (i >= 50 && i < 60) {
				anIntArray660[i - 50] = class30_sub2_sub2.method410();
			} else if (i >= 60 && i < 70) {
				anIntArray661[i - 60] = class30_sub2_sub2.method410();
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	public boolean method537() {
		if (anIntArray658 == null) {
			return true;
		}
		boolean flag = true;
		for (int i : anIntArray658) {
			if (!Class30_Sub2_Sub4_Sub6.method463(i)) {
				flag = false;
			}
		}

		return flag;
	}

	public Class30_Sub2_Sub4_Sub6 method538() {
		if (anIntArray658 == null) {
			return null;
		}
		Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6[anIntArray658.length];
		for (int i = 0; i < anIntArray658.length; i++) {
			aclass30_sub2_sub4_sub6[i] = Class30_Sub2_Sub4_Sub6.method462(anIntArray658[i]);
		}

		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6;
		if (aclass30_sub2_sub4_sub6.length == 1) {
			class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
		} else {
			class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
		}
		for (int j = 0; j < 6; j++) {
			if (anIntArray659[j] == 0) {
				break;
			}
			class30_sub2_sub4_sub6.method476(anIntArray659[j], anIntArray660[j]);
		}

		return class30_sub2_sub4_sub6;
	}

	public boolean method539() {
		boolean flag1 = true;
		for (int i = 0; i < 5; i++) {
			if (anIntArray661[i] != -1 && !Class30_Sub2_Sub4_Sub6.method463(anIntArray661[i])) {
				flag1 = false;
			}
		}
		return flag1;
	}

	public Class30_Sub2_Sub4_Sub6 method540() {
		Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6[5];
		int j = 0;
		for (int k = 0; k < 5; k++) {
			if (anIntArray661[k] != -1) {
				aclass30_sub2_sub4_sub6[j++] = Class30_Sub2_Sub4_Sub6.method462(anIntArray661[k]);
			}
		}

		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(j, aclass30_sub2_sub4_sub6);
		for (int l = 0; l < 6; l++) {
			if (anIntArray659[l] == 0) {
				break;
			}
			class30_sub2_sub4_sub6.method476(anIntArray659[l], anIntArray660[l]);
		}

		return class30_sub2_sub4_sub6;
	}

}
