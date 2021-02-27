// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class5 {

	public static int anInt56;
	public static Class30_Sub2_Sub2 aClass30_Sub2_Sub2_60;
	public static int anInt62;
	public static int[] anIntArray72;
	public static Class5[] aClass5Array80;
	public static client aClient82;
	public static Class12 aClass12_95 = new Class12(30);
	public int anInt55 = -1;
	public int anInt57 = -1;
	public int anInt58 = -1;
	public int anInt59 = -1;
	public int anInt61 = -1;
	public String aString65;
	public String[] aStringArray66;
	public int anInt67 = -1;
	public byte aByte68 = 1;
	public int[] anIntArray70;
	public int unusedInt0 = -1;
	public int[] anIntArray73;
	public int anInt75 = -1;
	public int[] anIntArray76;
	public int anInt77 = -1;
	public long aLong78 = -1L;
	public int anInt79 = 32;
	public int anInt83 = -1;
	public boolean aBoolean84 = true;
	public int anInt85;
	public int anInt86 = 128;
	public boolean aBoolean87 = true;
	public int[] anIntArray88;
	public byte[] aByteArray89;
	public int unusedInt1 = -1;
	public int anInt91 = 128;
	public int anInt92;
	public boolean aBoolean93 = false;
	public int[] anIntArray94;
	public int unusedInt2 = -1;

	public Class5() {
	}

	public static Class5 method159(int i) {
		for (int j = 0; j < 20; j++) {
			if (aClass5Array80[j].aLong78 == (long) i) {
				return aClass5Array80[j];
			}
		}

		anInt56 = (anInt56 + 1) % 20;
		Class5 class5 = aClass5Array80[anInt56] = new Class5();
		aClass30_Sub2_Sub2_60.anInt1406 = anIntArray72[i];
		class5.aLong78 = i;
		class5.method165(aClass30_Sub2_Sub2_60);
		return class5;
	}

	public static void method162(Class44 class44) {
		aClass30_Sub2_Sub2_60 = new Class30_Sub2_Sub2(class44.method571("npc.dat", null));
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("npc.idx", null));
		anInt62 = class30_sub2_sub2.method410();
		anIntArray72 = new int[anInt62];
		int i = 2;
		for (int j = 0; j < anInt62; j++) {
			anIntArray72[j] = i;
			i += class30_sub2_sub2.method410();
		}

		aClass5Array80 = new Class5[20];
		for (int k = 0; k < 20; k++) {
			aClass5Array80[k] = new Class5();
		}
	}

	public static void method163() {
		aClass12_95 = null;
		anIntArray72 = null;
		aClass5Array80 = null;
		aClass30_Sub2_Sub2_60 = null;
	}

	public Class30_Sub2_Sub4_Sub6 method160() {
		if (anIntArray88 != null) {
			Class5 class5 = method161();
			if (class5 == null) {
				return null;
			} else {
				return class5.method160();
			}
		}
		if (anIntArray73 == null) {
			return null;
		}
		boolean flag1 = false;
		for (int value : anIntArray73) {
			if (!Class30_Sub2_Sub4_Sub6.method463(value)) {
				flag1 = true;
			}
		}

		if (flag1) {
			return null;
		}
		Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6[anIntArray73.length];
		for (int j = 0; j < anIntArray73.length; j++) {
			aclass30_sub2_sub4_sub6[j] = Class30_Sub2_Sub4_Sub6.method462(anIntArray73[j]);
		}

		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6;
		if (aclass30_sub2_sub4_sub6.length == 1) {
			class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
		} else {
			class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
		}
		if (anIntArray76 != null) {
			for (int k = 0; k < anIntArray76.length; k++) {
				class30_sub2_sub4_sub6.method476(anIntArray76[k], anIntArray70[k]);
			}
		}
		return class30_sub2_sub4_sub6;
	}

	public Class5 method161() {
		int j = -1;
		if (anInt57 != -1) {
			Class37 class37 = Class37.aClass37Array646[anInt57];
			int k = class37.anInt648;
			int l = class37.anInt649;
			int i1 = class37.anInt650;
			int j1 = client.anIntArray1232[i1 - l];
			j = aClient82.anIntArray971[k] >> l & j1;
		} else if (anInt59 != -1) {
			j = aClient82.anIntArray971[anInt59];
		}
		if (j < 0 || j >= anIntArray88.length || anIntArray88[j] == -1) {
			return null;
		} else {
			return method159(anIntArray88[j]);
		}
	}

	public Class30_Sub2_Sub4_Sub6 method164(int j, int k, int[] ai) {
		if (anIntArray88 != null) {
			Class5 class5 = method161();
			if (class5 == null) {
				return null;
			} else {
				return class5.method164(j, k, ai);
			}
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = (Class30_Sub2_Sub4_Sub6) aClass12_95.method222(aLong78);
		if (class30_sub2_sub4_sub6 == null) {
			boolean flag = false;
			for (int value : anIntArray94) {
				if (!Class30_Sub2_Sub4_Sub6.method463(value)) {
					flag = true;
				}
			}

			if (flag) {
				return null;
			}
			Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6[anIntArray94.length];
			for (int j1 = 0; j1 < anIntArray94.length; j1++) {
				aclass30_sub2_sub4_sub6[j1] = Class30_Sub2_Sub4_Sub6.method462(anIntArray94[j1]);
			}

			if (aclass30_sub2_sub4_sub6.length == 1) {
				class30_sub2_sub4_sub6 = aclass30_sub2_sub4_sub6[0];
			} else {
				class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
			}
			if (anIntArray76 != null) {
				for (int k1 = 0; k1 < anIntArray76.length; k1++) {
					class30_sub2_sub4_sub6.method476(anIntArray76[k1], anIntArray70[k1]);
				}
			}
			class30_sub2_sub4_sub6.method469();
			class30_sub2_sub4_sub6.method479(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
			aClass12_95.method223(class30_sub2_sub4_sub6, aLong78);
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = Class30_Sub2_Sub4_Sub6.aClass30_Sub2_Sub4_Sub6_1621;
		class30_sub2_sub4_sub6_1.method464(class30_sub2_sub4_sub6, Class36.method532(k) & Class36.method532(j));
		if (k != -1 && j != -1) {
			class30_sub2_sub4_sub6_1.method471(ai, j, k);
		} else if (k != -1) {
			class30_sub2_sub4_sub6_1.method470(k);
		}
		if (anInt91 != 128 || anInt86 != 128) {
			class30_sub2_sub4_sub6_1.method478(anInt91, anInt91, anInt86);
		}
		class30_sub2_sub4_sub6_1.method466();
		class30_sub2_sub4_sub6_1.anIntArrayArray1658 = null;
		class30_sub2_sub4_sub6_1.anIntArrayArray1657 = null;
		if (aByte68 == 1) {
			class30_sub2_sub4_sub6_1.aBoolean1659 = true;
		}
		return class30_sub2_sub4_sub6_1;
	}

	public void method165(Class30_Sub2_Sub2 class30_sub2_sub2) {
		do {
			int i = class30_sub2_sub2.method408();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				int j = class30_sub2_sub2.method408();
				anIntArray94 = new int[j];
				for (int j1 = 0; j1 < j; j1++) {
					anIntArray94[j1] = class30_sub2_sub2.method410();
				}
			} else if (i == 2) {
				aString65 = class30_sub2_sub2.method415();
			} else if (i == 3) {
				aByteArray89 = class30_sub2_sub2.method416();
			} else if (i == 12) {
				aByte68 = class30_sub2_sub2.method409();
			} else if (i == 13) {
				anInt77 = class30_sub2_sub2.method410();
			} else if (i == 14) {
				anInt67 = class30_sub2_sub2.method410();
			} else if (i == 17) {
				anInt67 = class30_sub2_sub2.method410();
				anInt58 = class30_sub2_sub2.method410();
				anInt83 = class30_sub2_sub2.method410();
				anInt55 = class30_sub2_sub2.method410();
			} else if (i >= 30 && i < 40) {
				if (aStringArray66 == null) {
					aStringArray66 = new String[5];
				}
				aStringArray66[i - 30] = class30_sub2_sub2.method415();
				if (aStringArray66[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray66[i - 30] = null;
				}
			} else if (i == 40) {
				int k = class30_sub2_sub2.method408();
				anIntArray76 = new int[k];
				anIntArray70 = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					anIntArray76[k1] = class30_sub2_sub2.method410();
					anIntArray70[k1] = class30_sub2_sub2.method410();
				}
			} else if (i == 60) {
				int l = class30_sub2_sub2.method408();
				anIntArray73 = new int[l];
				for (int l1 = 0; l1 < l; l1++) {
					anIntArray73[l1] = class30_sub2_sub2.method410();
				}
			} else if (i == 90) {
				unusedInt2 = class30_sub2_sub2.method410();
			} else if (i == 91) {
				unusedInt0 = class30_sub2_sub2.method410();
			} else if (i == 92) {
				unusedInt1 = class30_sub2_sub2.method410();
			} else if (i == 93) {
				aBoolean87 = false;
			} else if (i == 95) {
				anInt61 = class30_sub2_sub2.method410();
			} else if (i == 97) {
				anInt91 = class30_sub2_sub2.method410();
			} else if (i == 98) {
				anInt86 = class30_sub2_sub2.method410();
			} else if (i == 99) {
				aBoolean93 = true;
			} else if (i == 100) {
				anInt85 = class30_sub2_sub2.method409();
			} else if (i == 101) {
				anInt92 = class30_sub2_sub2.method409() * 5;
			} else if (i == 102) {
				anInt75 = class30_sub2_sub2.method410();
			} else if (i == 103) {
				anInt79 = class30_sub2_sub2.method410();
			} else if (i == 106) {
				anInt57 = class30_sub2_sub2.method410();
				if (anInt57 == 65535) {
					anInt57 = -1;
				}
				anInt59 = class30_sub2_sub2.method410();
				if (anInt59 == 65535) {
					anInt59 = -1;
				}
				int i1 = class30_sub2_sub2.method408();
				anIntArray88 = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					anIntArray88[i2] = class30_sub2_sub2.method410();
					if (anIntArray88[i2] == 65535) {
						anIntArray88[i2] = -1;
					}
				}
			} else if (i == 107) {
				aBoolean84 = false;
			}
		} while (true);
	}

}
