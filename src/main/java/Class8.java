// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class8 {

	public static Class12 aClass12_158 = new Class12(100);
	public static Class12 aClass12_159 = new Class12(50);
	public static Class8[] aClass8Array172;
	public static int anInt180;
	public static boolean aBoolean182 = true;
	public static Class30_Sub2_Sub2 aClass30_Sub2_Sub2_183;
	public static int[] anIntArray195;
	public static int anInt203;
	public byte aByte154;
	public int anInt155;
	public int[] anIntArray156;
	public int anInt157 = -1;
	public int[] anIntArray160;
	public boolean aBoolean161;
	public int anInt162;
	public int anInt163;
	public int anInt164;
	public int anInt165;
	public int anInt166;
	public int anInt167;
	public String[] aStringArray168;
	public int anInt169;
	public String aString170;
	public int anInt173;
	public int anInt174;
	public int anInt175;
	public boolean aBoolean176;
	public byte[] aByteArray178;
	public int anInt179;
	public int anInt181;
	public int anInt184;
	public int anInt185;
	public int anInt188;
	public String[] aStringArray189;
	public int anInt190;
	public int anInt191;
	public int anInt192;
	public int[] anIntArray193;
	public int anInt194;
	public int anInt196;
	public int anInt197;
	public int anInt198;
	public int unusedInt;
	public int anInt200;
	public int[] anIntArray201;
	public int anInt202;
	public int anInt204;
	public byte aByte205;

	public Class8() {
	}

	public static void method191() {
		aClass12_159 = null;
		aClass12_158 = null;
		anIntArray195 = null;
		aClass8Array172 = null;
		aClass30_Sub2_Sub2_183 = null;
	}

	public static void method193(Class44 class44) {
		aClass30_Sub2_Sub2_183 = new Class30_Sub2_Sub2(class44.method571("obj.dat", null));
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("obj.idx", null));
		anInt203 = class30_sub2_sub2.method410();
		anIntArray195 = new int[anInt203];
		int i = 2;
		for (int j = 0; j < anInt203; j++) {
			anIntArray195[j] = i;
			i += class30_sub2_sub2.method410();
		}

		aClass8Array172 = new Class8[10];
		for (int k = 0; k < 10; k++) {
			aClass8Array172[k] = new Class8();
		}
	}

	public static Class8 method198(int i) {
		for (int j = 0; j < 10; j++) {
			if (aClass8Array172[j].anInt157 == i) {
				return aClass8Array172[j];
			}
		}

		anInt180 = (anInt180 + 1) % 10;
		Class8 class8 = aClass8Array172[anInt180];
		aClass30_Sub2_Sub2_183.anInt1406 = anIntArray195[i];
		class8.anInt157 = i;
		class8.method197();
		class8.method203(aClass30_Sub2_Sub2_183);
		if (class8.anInt163 != -1) {
			class8.method199();
		}
		if (!aBoolean182 && class8.aBoolean161) {
			class8.aString170 = "Members Object";
			class8.aByteArray178 = "Login to a members' server to use this object.".getBytes();
			class8.aStringArray168 = null;
			class8.aStringArray189 = null;
			class8.anInt202 = 0;
		}
		return class8;
	}

	public static Class30_Sub2_Sub1_Sub1 method200(int i, int j, int k) {
		if (k == 0) {
			Class30_Sub2_Sub1_Sub1 class30_sub2_sub1_sub1 = (Class30_Sub2_Sub1_Sub1) aClass12_158.method222(i);
			if (class30_sub2_sub1_sub1 != null && class30_sub2_sub1_sub1.anInt1445 != j && class30_sub2_sub1_sub1.anInt1445 != -1) {
				class30_sub2_sub1_sub1.method329();
				class30_sub2_sub1_sub1 = null;
			}
			if (class30_sub2_sub1_sub1 != null) {
				return class30_sub2_sub1_sub1;
			}
		}
		Class8 class8 = method198(i);
		if (class8.anIntArray193 == null) {
			j = -1;
		}
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= class8.anIntArray201[j1] && class8.anIntArray201[j1] != 0) {
					i1 = class8.anIntArray193[j1];
				}
			}

			if (i1 != -1) {
				class8 = method198(i1);
			}
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = class8.method201(1);
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		Class30_Sub2_Sub1_Sub1 class30_sub2_sub1_sub1_2 = null;
		if (class8.anInt163 != -1) {
			class30_sub2_sub1_sub1_2 = method200(class8.anInt179, 10, -1);
			if (class30_sub2_sub1_sub1_2 == null) {
				return null;
			}
		}
		Class30_Sub2_Sub1_Sub1 class30_sub2_sub1_sub1_1 = new Class30_Sub2_Sub1_Sub1(32, 32);
		int k1 = Class30_Sub2_Sub1_Sub3.anInt1466;
		int l1 = Class30_Sub2_Sub1_Sub3.anInt1467;
		int[] ai = Class30_Sub2_Sub1_Sub3.anIntArray1472;
		int[] ai1 = Class30_Sub2_Sub1.anIntArray1378;
		int i2 = Class30_Sub2_Sub1.anInt1379;
		int j2 = Class30_Sub2_Sub1.anInt1380;
		int k2 = Class30_Sub2_Sub1.anInt1383;
		int l2 = Class30_Sub2_Sub1.anInt1384;
		int i3 = Class30_Sub2_Sub1.anInt1381;
		int j3 = Class30_Sub2_Sub1.anInt1382;
		Class30_Sub2_Sub1_Sub3.aBoolean1464 = false;
		Class30_Sub2_Sub1.method331(32, 32, class30_sub2_sub1_sub1_1.anIntArray1439);
		Class30_Sub2_Sub1.method336(32, 0, 0, 0, 32);
		Class30_Sub2_Sub1_Sub3.method364();
		int k3 = class8.anInt181;
		if (k == -1) {
			k3 = (int) ((double) k3 * 1.5D);
		}
		if (k > 0) {
			k3 = (int) ((double) k3 * 1.04D);
		}
		int l3 = Class30_Sub2_Sub1_Sub3.anIntArray1470[class8.anInt190] * k3 >> 16;
		int i4 = Class30_Sub2_Sub1_Sub3.anIntArray1471[class8.anInt190] * k3 >> 16;
		class30_sub2_sub4_sub6.method482(0, class8.anInt198, class8.anInt204, class8.anInt190, class8.anInt169, l3 + class30_sub2_sub4_sub6.anInt1426 / 2 + class8.anInt194, i4 + class8.anInt194);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (class30_sub2_sub1_sub1_1.anIntArray1439[i5 + j4 * 32] == 0) {
					if (i5 > 0 && class30_sub2_sub1_sub1_1.anIntArray1439[(i5 - 1) + j4 * 32] > 1) {
						class30_sub2_sub1_sub1_1.anIntArray1439[i5 + j4 * 32] = 1;
					} else if (j4 > 0 && class30_sub2_sub1_sub1_1.anIntArray1439[i5 + (j4 - 1) * 32] > 1) {
						class30_sub2_sub1_sub1_1.anIntArray1439[i5 + j4 * 32] = 1;
					} else if (i5 < 31 && class30_sub2_sub1_sub1_1.anIntArray1439[i5 + 1 + j4 * 32] > 1) {
						class30_sub2_sub1_sub1_1.anIntArray1439[i5 + j4 * 32] = 1;
					} else if (j4 < 31 && class30_sub2_sub1_sub1_1.anIntArray1439[i5 + (j4 + 1) * 32] > 1) {
						class30_sub2_sub1_sub1_1.anIntArray1439[i5 + j4 * 32] = 1;
					}
				}
			}
		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (class30_sub2_sub1_sub1_1.anIntArray1439[j5 + k4 * 32] == 0) {
						if (j5 > 0 && class30_sub2_sub1_sub1_1.anIntArray1439[(j5 - 1) + k4 * 32] == 1) {
							class30_sub2_sub1_sub1_1.anIntArray1439[j5 + k4 * 32] = k;
						} else if (k4 > 0 && class30_sub2_sub1_sub1_1.anIntArray1439[j5 + (k4 - 1) * 32] == 1) {
							class30_sub2_sub1_sub1_1.anIntArray1439[j5 + k4 * 32] = k;
						} else if (j5 < 31 && class30_sub2_sub1_sub1_1.anIntArray1439[j5 + 1 + k4 * 32] == 1) {
							class30_sub2_sub1_sub1_1.anIntArray1439[j5 + k4 * 32] = k;
						} else if (k4 < 31 && class30_sub2_sub1_sub1_1.anIntArray1439[j5 + (k4 + 1) * 32] == 1) {
							class30_sub2_sub1_sub1_1.anIntArray1439[j5 + k4 * 32] = k;
						}
					}
				}
			}
		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (class30_sub2_sub1_sub1_1.anIntArray1439[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && class30_sub2_sub1_sub1_1.anIntArray1439[(k5 - 1) + (l4 - 1) * 32] > 0) {
						class30_sub2_sub1_sub1_1.anIntArray1439[k5 + l4 * 32] = 0x302020;
					}
				}
			}
		}
		if (class8.anInt163 != -1) {
			int l5 = class30_sub2_sub1_sub1_2.anInt1444;
			int j6 = class30_sub2_sub1_sub1_2.anInt1445;
			class30_sub2_sub1_sub1_2.anInt1444 = 32;
			class30_sub2_sub1_sub1_2.anInt1445 = 32;
			class30_sub2_sub1_sub1_2.method348(0, 0);
			class30_sub2_sub1_sub1_2.anInt1444 = l5;
			class30_sub2_sub1_sub1_2.anInt1445 = j6;
		}
		if (k == 0) {
			aClass12_158.method223(class30_sub2_sub1_sub1_1, i);
		}
		Class30_Sub2_Sub1.method331(j2, i2, ai1);
		Class30_Sub2_Sub1.method333(j3, k2, l2, i3);
		Class30_Sub2_Sub1_Sub3.anInt1466 = k1;
		Class30_Sub2_Sub1_Sub3.anInt1467 = l1;
		Class30_Sub2_Sub1_Sub3.anIntArray1472 = ai;
		Class30_Sub2_Sub1_Sub3.aBoolean1464 = true;
		if (class8.aBoolean176) {
			class30_sub2_sub1_sub1_1.anInt1444 = 33;
		} else {
			class30_sub2_sub1_sub1_1.anInt1444 = 32;
		}
		class30_sub2_sub1_sub1_1.anInt1445 = j;
		return class30_sub2_sub1_sub1_1;
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Class30_Sub2_Sub4_Sub6.method463(k)) {
			flag = false;
		}
		if (l != -1 && !Class30_Sub2_Sub4_Sub6.method463(l)) {
			flag = false;
		}
		return flag;
	}

	public Class30_Sub2_Sub4_Sub6 method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return null;
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(k);
		if (l != -1) {
			Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = Class30_Sub2_Sub4_Sub6.method462(l);
			Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = {class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_1};
			class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(2, aclass30_sub2_sub4_sub6);
		}
		if (anIntArray156 != null) {
			for (int i1 = 0; i1 < anIntArray156.length; i1++) {
				class30_sub2_sub4_sub6.method476(anIntArray156[i1], anIntArray160[i1]);
			}
		}
		return class30_sub2_sub4_sub6;
	}

	public boolean method195(int j) {
		int k = anInt165;
		int l = anInt188;
		int i1 = anInt185;
		if (j == 1) {
			k = anInt200;
			l = anInt164;
			i1 = anInt162;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Class30_Sub2_Sub4_Sub6.method463(k)) {
			flag = false;
		}
		if (l != -1 && !Class30_Sub2_Sub4_Sub6.method463(l)) {
			flag = false;
		}
		if (i1 != -1 && !Class30_Sub2_Sub4_Sub6.method463(i1)) {
			flag = false;
		}
		return flag;
	}

	public Class30_Sub2_Sub4_Sub6 method196(int i) {
		int j = anInt165;
		int k = anInt188;
		int l = anInt185;
		if (i == 1) {
			j = anInt200;
			k = anInt164;
			l = anInt162;
		}
		if (j == -1) {
			return null;
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(j);
		if (k != -1) {
			if (l != -1) {
				Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = Class30_Sub2_Sub4_Sub6.method462(k);
				Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_3 = Class30_Sub2_Sub4_Sub6.method462(l);
				Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6_1 = {class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_1, class30_sub2_sub4_sub6_3};
				class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(3, aclass30_sub2_sub4_sub6_1);
			} else {
				Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_2 = Class30_Sub2_Sub4_Sub6.method462(k);
				Class30_Sub2_Sub4_Sub6[] aclass30_sub2_sub4_sub6 = {class30_sub2_sub4_sub6, class30_sub2_sub4_sub6_2};
				class30_sub2_sub4_sub6 = new Class30_Sub2_Sub4_Sub6(2, aclass30_sub2_sub4_sub6);
			}
		}
		if (i == 0 && aByte205 != 0) {
			class30_sub2_sub4_sub6.method475(0, aByte205, 0);
		}
		if (i == 1 && aByte154 != 0) {
			class30_sub2_sub4_sub6.method475(0, aByte154, 0);
		}
		if (anIntArray156 != null) {
			for (int i1 = 0; i1 < anIntArray156.length; i1++) {
				class30_sub2_sub4_sub6.method476(anIntArray156[i1], anIntArray160[i1]);
			}
		}
		return class30_sub2_sub4_sub6;
	}

	public void method197() {
		anInt174 = 0;
		aString170 = null;
		aByteArray178 = null;
		anIntArray156 = null;
		anIntArray160 = null;
		anInt181 = 2000;
		anInt190 = 0;
		anInt198 = 0;
		anInt204 = 0;
		anInt169 = 0;
		anInt194 = 0;
		unusedInt = -1;
		aBoolean176 = false;
		anInt155 = 1;
		aBoolean161 = false;
		aStringArray168 = null;
		aStringArray189 = null;
		anInt165 = -1;
		anInt188 = -1;
		aByte205 = 0;
		anInt200 = -1;
		anInt164 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		anIntArray193 = null;
		anIntArray201 = null;
		anInt179 = -1;
		anInt163 = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		anInt202 = 0;
	}

	public void method199() {
		Class8 class8 = method198(anInt163);
		anInt174 = class8.anInt174;
		anInt181 = class8.anInt181;
		anInt190 = class8.anInt190;
		anInt198 = class8.anInt198;
		anInt204 = class8.anInt204;
		anInt169 = class8.anInt169;
		anInt194 = class8.anInt194;
		anIntArray156 = class8.anIntArray156;
		anIntArray160 = class8.anIntArray160;
		Class8 class8_1 = method198(anInt179);
		aString170 = class8_1.aString170;
		aBoolean161 = class8_1.aBoolean161;
		anInt155 = class8_1.anInt155;
		String s = "a";
		char c = class8_1.aString170.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		aByteArray178 = ("Swap this note at any bank for " + s + " " + class8_1.aString170 + ".").getBytes();
		aBoolean176 = true;
	}

	public Class30_Sub2_Sub4_Sub6 method201(int i) {
		if (anIntArray193 != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= anIntArray201[k] && anIntArray201[k] != 0) {
					j = anIntArray193[k];
				}
			}

			if (j != -1) {
				return method198(j).method201(1);
			}
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = (Class30_Sub2_Sub4_Sub6) aClass12_159.method222(anInt157);
		if (class30_sub2_sub4_sub6 != null) {
			return class30_sub2_sub4_sub6;
		}
		class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(anInt174);
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128) {
			class30_sub2_sub4_sub6.method478(anInt167, anInt191, anInt192);
		}
		if (anIntArray156 != null) {
			for (int l = 0; l < anIntArray156.length; l++) {
				class30_sub2_sub4_sub6.method476(anIntArray156[l], anIntArray160[l]);
			}
		}
		class30_sub2_sub4_sub6.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		class30_sub2_sub4_sub6.aBoolean1659 = true;
		aClass12_159.method223(class30_sub2_sub4_sub6, anInt157);
		return class30_sub2_sub4_sub6;
	}

	public Class30_Sub2_Sub4_Sub6 method202(int i) {
		if (anIntArray193 != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= anIntArray201[k] && anIntArray201[k] != 0) {
					j = anIntArray193[k];
				}
			}

			if (j != -1) {
				return method198(j).method202(1);
			}
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(anInt174);
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		if (anIntArray156 != null) {
			for (int l = 0; l < anIntArray156.length; l++) {
				class30_sub2_sub4_sub6.method476(anIntArray156[l], anIntArray160[l]);
			}
		}
		return class30_sub2_sub4_sub6;
	}

	public void method203(Class30_Sub2_Sub2 class30_sub2_sub2) {
		do {
			int i = class30_sub2_sub2.method408();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt174 = class30_sub2_sub2.method410();
			} else if (i == 2) {
				aString170 = class30_sub2_sub2.method415();
			} else if (i == 3) {
				aByteArray178 = class30_sub2_sub2.method416();
			} else if (i == 4) {
				anInt181 = class30_sub2_sub2.method410();
			} else if (i == 5) {
				anInt190 = class30_sub2_sub2.method410();
			} else if (i == 6) {
				anInt198 = class30_sub2_sub2.method410();
			} else if (i == 7) {
				anInt169 = class30_sub2_sub2.method410();
				if (anInt169 > 32767) {
					anInt169 -= 0x10000;
				}
			} else if (i == 8) {
				anInt194 = class30_sub2_sub2.method410();
				if (anInt194 > 32767) {
					anInt194 -= 0x10000;
				}
			} else if (i == 10) {
				unusedInt = class30_sub2_sub2.method410();
			} else if (i == 11) {
				aBoolean176 = true;
			} else if (i == 12) {
				anInt155 = class30_sub2_sub2.method413();
			} else if (i == 16) {
				aBoolean161 = true;
			} else if (i == 23) {
				anInt165 = class30_sub2_sub2.method410();
				aByte205 = class30_sub2_sub2.method409();
			} else if (i == 24) {
				anInt188 = class30_sub2_sub2.method410();
			} else if (i == 25) {
				anInt200 = class30_sub2_sub2.method410();
				aByte154 = class30_sub2_sub2.method409();
			} else if (i == 26) {
				anInt164 = class30_sub2_sub2.method410();
			} else if (i >= 30 && i < 35) {
				if (aStringArray168 == null) {
					aStringArray168 = new String[5];
				}
				aStringArray168[i - 30] = class30_sub2_sub2.method415();
				if (aStringArray168[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray168[i - 30] = null;
				}
			} else if (i >= 35 && i < 40) {
				if (aStringArray189 == null) {
					aStringArray189 = new String[5];
				}
				aStringArray189[i - 35] = class30_sub2_sub2.method415();
			} else if (i == 40) {
				int j = class30_sub2_sub2.method408();
				anIntArray156 = new int[j];
				anIntArray160 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray156[k] = class30_sub2_sub2.method410();
					anIntArray160[k] = class30_sub2_sub2.method410();
				}
			} else if (i == 78) {
				anInt185 = class30_sub2_sub2.method410();
			} else if (i == 79) {
				anInt162 = class30_sub2_sub2.method410();
			} else if (i == 90) {
				anInt175 = class30_sub2_sub2.method410();
			} else if (i == 91) {
				anInt197 = class30_sub2_sub2.method410();
			} else if (i == 92) {
				anInt166 = class30_sub2_sub2.method410();
			} else if (i == 93) {
				anInt173 = class30_sub2_sub2.method410();
			} else if (i == 95) {
				anInt204 = class30_sub2_sub2.method410();
			} else if (i == 97) {
				anInt179 = class30_sub2_sub2.method410();
			} else if (i == 98) {
				anInt163 = class30_sub2_sub2.method410();
			} else if (i >= 100 && i < 110) {
				if (anIntArray193 == null) {
					anIntArray193 = new int[10];
					anIntArray201 = new int[10];
				}
				anIntArray193[i - 100] = class30_sub2_sub2.method410();
				anIntArray201[i - 100] = class30_sub2_sub2.method410();
			} else if (i == 110) {
				anInt167 = class30_sub2_sub2.method410();
			} else if (i == 111) {
				anInt192 = class30_sub2_sub2.method410();
			} else if (i == 112) {
				anInt191 = class30_sub2_sub2.method410();
			} else if (i == 113) {
				anInt196 = class30_sub2_sub2.method409();
			} else if (i == 114) {
				anInt184 = class30_sub2_sub2.method409() * 5;
			} else if (i == 115) {
				anInt202 = class30_sub2_sub2.method408();
			}
		} while (true);
	}

}
