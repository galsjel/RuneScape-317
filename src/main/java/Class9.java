// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class9 {

	public static Class9[] aClass9Array210;
	public static Class12 aClass12_238;
	public static final Class12 aClass12_264 = new Class12(30);
	public Class30_Sub2_Sub1_Sub1 aClass30_Sub2_Sub1_Sub1_207;
	public int anInt208;
	public Class30_Sub2_Sub1_Sub1[] aClass30_Sub2_Sub1_Sub1Array209;
	public int anInt211;
	public int[] anIntArray212;
	public int anInt213 = 9;
	public int anInt214;
	public int[] anIntArray215;
	public int anInt216;
	public int anInt217;
	public String aString218;
	public int anInt219;
	public int anInt220;
	public String aString221;
	public String aString222;
	public boolean aBoolean223;
	public int anInt224;
	public String[] aStringArray225;
	public int[][] anIntArrayArray226;
	public boolean aBoolean227;
	public String aString228;
	public int anInt229 = 891;
	public int anInt230;
	public int anInt231;
	public int anInt232;
	public int anInt233;
	public int anInt234;
	public boolean aBoolean235;
	public int anInt236;
	public int anInt237;
	public int anInt239;
	public int[] anIntArray240;
	public int[] anIntArray241;
	public boolean aBoolean242;
	public Class30_Sub2_Sub1_Sub4 aClass30_Sub2_Sub1_Sub4_243;
	public int anInt244;
	public int[] anIntArray245;
	public int anInt246;
	public int[] anIntArray247;
	public String aString248;
	public boolean aBoolean249;
	public int anInt250;
	public boolean aBoolean251;
	public int[] anIntArray252;
	public int[] anIntArray253;
	public byte aByte254;
	public int anInt255;
	public int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public Class30_Sub2_Sub1_Sub1 aClass30_Sub2_Sub1_Sub1_260;
	public int anInt261;
	public int anInt262;
	public int anInt263;
	public int anInt265;
	public boolean aBoolean266;
	public int anInt267;
	public boolean aBoolean268;
	public int anInt269;
	public int anInt270;
	public int anInt271;
	public int[] anIntArray272;

	public Class9() {
	}

	public static void method205(Class44 class44, Class30_Sub2_Sub1_Sub4[] aclass30_sub2_sub1_sub4, Class44 class44_1) {
		aClass12_238 = new Class12(50000);
		Class30_Sub2_Sub2 class30_sub2_sub2 = new Class30_Sub2_Sub2(class44.method571("data", null));
		int i = -1;
		int j = class30_sub2_sub2.method410();
		aClass9Array210 = new Class9[j];
		while (class30_sub2_sub2.anInt1406 < class30_sub2_sub2.aByteArray1405.length) {
			int k = class30_sub2_sub2.method410();
			if (k == 65535) {
				i = class30_sub2_sub2.method410();
				k = class30_sub2_sub2.method410();
			}
			Class9 class9 = aClass9Array210[k] = new Class9();
			class9.anInt250 = k;
			class9.anInt236 = i;
			class9.anInt262 = class30_sub2_sub2.method408();
			class9.anInt217 = class30_sub2_sub2.method408();
			class9.anInt214 = class30_sub2_sub2.method410();
			class9.anInt220 = class30_sub2_sub2.method410();
			class9.anInt267 = class30_sub2_sub2.method410();
			class9.aByte254 = (byte) class30_sub2_sub2.method408();
			class9.anInt230 = class30_sub2_sub2.method408();
			if (class9.anInt230 != 0) {
				class9.anInt230 = (class9.anInt230 - 1 << 8) + class30_sub2_sub2.method408();
			} else {
				class9.anInt230 = -1;
			}
			int i1 = class30_sub2_sub2.method408();
			if (i1 > 0) {
				class9.anIntArray245 = new int[i1];
				class9.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					class9.anIntArray245[j1] = class30_sub2_sub2.method408();
					class9.anIntArray212[j1] = class30_sub2_sub2.method410();
				}
			}
			int k1 = class30_sub2_sub2.method408();
			if (k1 > 0) {
				class9.anIntArrayArray226 = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = class30_sub2_sub2.method410();
					class9.anIntArrayArray226[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						class9.anIntArrayArray226[l1][l4] = class30_sub2_sub2.method410();
					}
				}
			}
			if (class9.anInt262 == 0) {
				class9.anInt261 = class30_sub2_sub2.method410();
				class9.aBoolean266 = class30_sub2_sub2.method408() == 1;
				int i2 = class30_sub2_sub2.method410();
				class9.anIntArray240 = new int[i2];
				class9.anIntArray241 = new int[i2];
				class9.anIntArray272 = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					class9.anIntArray240[j3] = class30_sub2_sub2.method410();
					class9.anIntArray241[j3] = class30_sub2_sub2.method411();
					class9.anIntArray272[j3] = class30_sub2_sub2.method411();
				}
			}
			if (class9.anInt262 == 1) {
				class9.anInt211 = class30_sub2_sub2.method410();
				class9.aBoolean251 = class30_sub2_sub2.method408() == 1;
			}
			if (class9.anInt262 == 2) {
				class9.anIntArray253 = new int[class9.anInt220 * class9.anInt267];
				class9.anIntArray252 = new int[class9.anInt220 * class9.anInt267];
				class9.aBoolean259 = class30_sub2_sub2.method408() == 1;
				class9.aBoolean249 = class30_sub2_sub2.method408() == 1;
				class9.aBoolean242 = class30_sub2_sub2.method408() == 1;
				class9.aBoolean235 = class30_sub2_sub2.method408() == 1;
				class9.anInt231 = class30_sub2_sub2.method408();
				class9.anInt244 = class30_sub2_sub2.method408();
				class9.anIntArray215 = new int[20];
				class9.anIntArray247 = new int[20];
				class9.aClass30_Sub2_Sub1_Sub1Array209 = new Class30_Sub2_Sub1_Sub1[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = class30_sub2_sub2.method408();
					if (k3 == 1) {
						class9.anIntArray215[j2] = class30_sub2_sub2.method411();
						class9.anIntArray247[j2] = class30_sub2_sub2.method411();
						String s1 = class30_sub2_sub2.method415();
						if (class44_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							class9.aClass30_Sub2_Sub1_Sub1Array209[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), class44_1, s1.substring(0, i5));
						}
					}
				}

				class9.aStringArray225 = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					class9.aStringArray225[l3] = class30_sub2_sub2.method415();
					if (class9.aStringArray225[l3].length() == 0) {
						class9.aStringArray225[l3] = null;
					}
				}
			}
			if (class9.anInt262 == 3) {
				class9.aBoolean227 = class30_sub2_sub2.method408() == 1;
			}
			if (class9.anInt262 == 4 || class9.anInt262 == 1) {
				class9.aBoolean223 = class30_sub2_sub2.method408() == 1;
				int k2 = class30_sub2_sub2.method408();
				if (aclass30_sub2_sub1_sub4 != null) {
					class9.aClass30_Sub2_Sub1_Sub4_243 = aclass30_sub2_sub1_sub4[k2];
				}
				class9.aBoolean268 = class30_sub2_sub2.method408() == 1;
			}
			if (class9.anInt262 == 4) {
				class9.aString248 = class30_sub2_sub2.method415();
				class9.aString228 = class30_sub2_sub2.method415();
			}
			if (class9.anInt262 == 1 || class9.anInt262 == 3 || class9.anInt262 == 4) {
				class9.anInt232 = class30_sub2_sub2.method413();
			}
			if (class9.anInt262 == 3 || class9.anInt262 == 4) {
				class9.anInt219 = class30_sub2_sub2.method413();
				class9.anInt216 = class30_sub2_sub2.method413();
				class9.anInt239 = class30_sub2_sub2.method413();
			}
			if (class9.anInt262 == 5) {
				String s = class30_sub2_sub2.method415();
				if (class44_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					class9.aClass30_Sub2_Sub1_Sub1_207 = method207(Integer.parseInt(s.substring(i4 + 1)), class44_1, s.substring(0, i4));
				}
				s = class30_sub2_sub2.method415();
				if (class44_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					class9.aClass30_Sub2_Sub1_Sub1_260 = method207(Integer.parseInt(s.substring(j4 + 1)), class44_1, s.substring(0, j4));
				}
			}
			if (class9.anInt262 == 6) {
				int l = class30_sub2_sub2.method408();
				if (l != 0) {
					class9.anInt233 = 1;
					class9.anInt234 = (l - 1 << 8) + class30_sub2_sub2.method408();
				}
				l = class30_sub2_sub2.method408();
				if (l != 0) {
					class9.anInt255 = 1;
					class9.anInt256 = (l - 1 << 8) + class30_sub2_sub2.method408();
				}
				l = class30_sub2_sub2.method408();
				if (l != 0) {
					class9.anInt257 = (l - 1 << 8) + class30_sub2_sub2.method408();
				} else {
					class9.anInt257 = -1;
				}
				l = class30_sub2_sub2.method408();
				if (l != 0) {
					class9.anInt258 = (l - 1 << 8) + class30_sub2_sub2.method408();
				} else {
					class9.anInt258 = -1;
				}
				class9.anInt269 = class30_sub2_sub2.method410();
				class9.anInt270 = class30_sub2_sub2.method410();
				class9.anInt271 = class30_sub2_sub2.method410();
			}
			if (class9.anInt262 == 7) {
				class9.anIntArray253 = new int[class9.anInt220 * class9.anInt267];
				class9.anIntArray252 = new int[class9.anInt220 * class9.anInt267];
				class9.aBoolean223 = class30_sub2_sub2.method408() == 1;
				int l2 = class30_sub2_sub2.method408();
				if (aclass30_sub2_sub1_sub4 != null) {
					class9.aClass30_Sub2_Sub1_Sub4_243 = aclass30_sub2_sub1_sub4[l2];
				}
				class9.aBoolean268 = class30_sub2_sub2.method408() == 1;
				class9.anInt232 = class30_sub2_sub2.method413();
				class9.anInt231 = class30_sub2_sub2.method411();
				class9.anInt244 = class30_sub2_sub2.method411();
				class9.aBoolean249 = class30_sub2_sub2.method408() == 1;
				class9.aStringArray225 = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					class9.aStringArray225[k4] = class30_sub2_sub2.method415();
					if (class9.aStringArray225[k4].length() == 0) {
						class9.aStringArray225[k4] = null;
					}
				}
			}
			if (class9.anInt217 == 2 || class9.anInt262 == 2) {
				class9.aString222 = class30_sub2_sub2.method415();
				class9.aString218 = class30_sub2_sub2.method415();
				class9.anInt237 = class30_sub2_sub2.method410();
			}
			if (class9.anInt217 == 1 || class9.anInt217 == 4 || class9.anInt217 == 5 || class9.anInt217 == 6) {
				class9.aString221 = class30_sub2_sub2.method415();
				if (class9.aString221.length() == 0) {
					if (class9.anInt217 == 1) {
						class9.aString221 = "Ok";
					}
					if (class9.anInt217 == 4) {
						class9.aString221 = "Select";
					}
					if (class9.anInt217 == 5) {
						class9.aString221 = "Select";
					}
					if (class9.anInt217 == 6) {
						class9.aString221 = "Continue";
					}
				}
			}
		}
		aClass12_238 = null;
	}

	public static Class30_Sub2_Sub1_Sub1 method207(int i, Class44 class44, String s) {
		long l = (Class50.method585(s) << 8) + (long) i;
		Class30_Sub2_Sub1_Sub1 class30_sub2_sub1_sub1 = (Class30_Sub2_Sub1_Sub1) aClass12_238.method222(l);
		if (class30_sub2_sub1_sub1 != null) {
			return class30_sub2_sub1_sub1;
		}
		try {
			class30_sub2_sub1_sub1 = new Class30_Sub2_Sub1_Sub1(class44, s, i);
			aClass12_238.method223(class30_sub2_sub1_sub1, l);
		} catch (Exception _ex) {
			return null;
		}
		return class30_sub2_sub1_sub1;
	}

	public static void method208(int i, int j, Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6) {
		aClass12_264.method224();
		if (class30_sub2_sub4_sub6 != null && j != 4) {
			aClass12_264.method223(class30_sub2_sub4_sub6, (j << 16) + i);
		}
	}

	public void method204(int i, int j) {
		int k = anIntArray253[i];
		anIntArray253[i] = anIntArray253[j];
		anIntArray253[j] = k;
		k = anIntArray252[i];
		anIntArray252[i] = anIntArray252[j];
		anIntArray252[j] = k;
	}

	public Class30_Sub2_Sub4_Sub6 method206(int i, int j) {
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6 = (Class30_Sub2_Sub4_Sub6) aClass12_264.method222((i << 16) + j);
		if (class30_sub2_sub4_sub6 != null) {
			return class30_sub2_sub4_sub6;
		}
		if (i == 1) {
			class30_sub2_sub4_sub6 = Class30_Sub2_Sub4_Sub6.method462(j);
		}
		if (i == 2) {
			class30_sub2_sub4_sub6 = Class5.method159(j).method160();
		}
		if (i == 3) {
			class30_sub2_sub4_sub6 = client.aClass30_Sub2_Sub4_Sub1_Sub2_1126.method453();
		}
		if (i == 4) {
			class30_sub2_sub4_sub6 = Class8.method198(j).method202(50);
		}
		if (i == 5) {
			class30_sub2_sub4_sub6 = null;
		}
		if (class30_sub2_sub4_sub6 != null) {
			aClass12_264.method223(class30_sub2_sub4_sub6, (i << 16) + j);
		}
		return class30_sub2_sub4_sub6;
	}

	public Class30_Sub2_Sub4_Sub6 method209(int j, int k, boolean flag) {
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6;
		if (flag) {
			class30_sub2_sub4_sub6 = method206(anInt255, anInt256);
		} else {
			class30_sub2_sub4_sub6 = method206(anInt233, anInt234);
		}
		if (class30_sub2_sub4_sub6 == null) {
			return null;
		}
		if (k == -1 && j == -1 && class30_sub2_sub4_sub6.anIntArray1640 == null) {
			return class30_sub2_sub4_sub6;
		}
		Class30_Sub2_Sub4_Sub6 class30_sub2_sub4_sub6_1 = new Class30_Sub2_Sub4_Sub6(true, Class36.method532(k) & Class36.method532(j), false, class30_sub2_sub4_sub6);
		if (k != -1 || j != -1) {
			class30_sub2_sub4_sub6_1.method469();
		}
		if (k != -1) {
			class30_sub2_sub4_sub6_1.method470(k);
		}
		if (j != -1) {
			class30_sub2_sub4_sub6_1.method470(j);
		}
		class30_sub2_sub4_sub6_1.method479(64, 768, -50, -10, -50, true);
		return class30_sub2_sub4_sub6_1;
	}

}
