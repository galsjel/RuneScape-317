// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ObjType {

	public static LRUCache aCache_158 = new LRUCache(100);
	public static LRUCache aCache_159 = new LRUCache(50);
	public static ObjType[] aTypeArray172;
	public static int anInt180;
	public static boolean aBoolean182 = true;
	public static Buffer aBuffer_183;
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

	public ObjType() {
	}

	public static void method191() {
		aCache_159 = null;
		aCache_158 = null;
		anIntArray195 = null;
		aTypeArray172 = null;
		aBuffer_183 = null;
	}

	public static void method193(FileArchive archive) {
		aBuffer_183 = new Buffer(archive.method571("obj.dat", null));
		Buffer buffer = new Buffer(archive.method571("obj.idx", null));
		anInt203 = buffer.method410();
		anIntArray195 = new int[anInt203];
		int i = 2;
		for (int j = 0; j < anInt203; j++) {
			anIntArray195[j] = i;
			i += buffer.method410();
		}
		aTypeArray172 = new ObjType[10];
		for (int k = 0; k < 10; k++) {
			aTypeArray172[k] = new ObjType();
		}
	}

	public static ObjType method198(int i) {
		for (int j = 0; j < 10; j++) {
			if (aTypeArray172[j].anInt157 == i) {
				return aTypeArray172[j];
			}
		}
		anInt180 = (anInt180 + 1) % 10;
		ObjType type = aTypeArray172[anInt180];
		aBuffer_183.position = anIntArray195[i];
		type.anInt157 = i;
		type.method197();
		type.method203(aBuffer_183);
		if (type.anInt163 != -1) {
			type.method199();
		}
		if (!aBoolean182 && type.aBoolean161) {
			type.aString170 = "Members Object";
			type.aByteArray178 = "Login to a members' server to use this object.".getBytes();
			type.aStringArray168 = null;
			type.aStringArray189 = null;
			type.anInt202 = 0;
		}
		return type;
	}

	public static Image24 method200(int i, int j, int k) {
		if (k == 0) {
			Image24 image = (Image24) aCache_158.method222(i);
			if (image != null && image.anInt1445 != j && image.anInt1445 != -1) {
				image.method329();
				image = null;
			}
			if (image != null) {
				return image;
			}
		}
		ObjType type = method198(i);
		if (type.anIntArray193 == null) {
			j = -1;
		}
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= type.anIntArray201[j1] && type.anIntArray201[j1] != 0) {
					i1 = type.anIntArray193[j1];
				}
			}
			if (i1 != -1) {
				type = method198(i1);
			}
		}
		Model model = type.method201(1);
		if (model == null) {
			return null;
		}
		Image24 class30_sub2_sub1_sub1_2 = null;
		if (type.anInt163 != -1) {
			class30_sub2_sub1_sub1_2 = method200(type.anInt179, 10, -1);
			if (class30_sub2_sub1_sub1_2 == null) {
				return null;
			}
		}
		Image24 class30_sub2_sub1_sub1_1 = new Image24(32, 32);
		int k1 = Draw3D.centerX;
		int l1 = Draw3D.centerY;
		int[] ai = Draw3D.anIntArray1472;
		int[] ai1 = Draw2D.pixels;
		int i2 = Draw2D.width;
		int j2 = Draw2D.height;
		int k2 = Draw2D.left;
		int l2 = Draw2D.right;
		int i3 = Draw2D.top;
		int j3 = Draw2D.bottom;
		Draw3D.jagged = false;
		Draw2D.bind(class30_sub2_sub1_sub1_1.anIntArray1439, 32, 32);
		Draw2D.fillRect(0, 0, 32, 32, 0);
		Draw3D.method364();
		int k3 = type.anInt181;
		if (k == -1) {
			k3 = (int) ((double) k3 * 1.5D);
		}
		if (k > 0) {
			k3 = (int) ((double) k3 * 1.04D);
		}
		int l3 = Draw3D.sin[type.anInt190] * k3 >> 16;
		int i4 = Draw3D.cos[type.anInt190] * k3 >> 16;
		model.drawSimple(0, type.anInt198, type.anInt204, type.anInt190, type.anInt169, l3 + model.minY / 2 + type.anInt194, i4 + type.anInt194);
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
		if (type.anInt163 != -1) {
			int l5 = class30_sub2_sub1_sub1_2.anInt1444;
			int j6 = class30_sub2_sub1_sub1_2.anInt1445;
			class30_sub2_sub1_sub1_2.anInt1444 = 32;
			class30_sub2_sub1_sub1_2.anInt1445 = 32;
			class30_sub2_sub1_sub1_2.method348(0, 0);
			class30_sub2_sub1_sub1_2.anInt1444 = l5;
			class30_sub2_sub1_sub1_2.anInt1445 = j6;
		}
		if (k == 0) {
			aCache_158.method223(class30_sub2_sub1_sub1_1, i);
		}
		Draw2D.bind(ai1, i2, j2);
		Draw2D.setBounds(j3, k2, l2, i3);
		Draw3D.centerX = k1;
		Draw3D.centerY = l1;
		Draw3D.anIntArray1472 = ai;
		Draw3D.jagged = true;
		if (type.aBoolean176) {
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
		if (!Model.validate(k)) {
			flag = false;
		}
		if (l != -1 && !Model.validate(l)) {
			flag = false;
		}
		return flag;
	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return null;
		}
		Model model = Model.tryGet(k);
		if (l != -1) {
			Model model_1 = Model.tryGet(l);
			Model[] aclass30_sub2_sub4_sub6 = {model, model_1};
			model = new Model(2, aclass30_sub2_sub4_sub6);
		}
		if (anIntArray156 != null) {
			for (int i1 = 0; i1 < anIntArray156.length; i1++) {
				model.replaceColor(anIntArray156[i1], anIntArray160[i1]);
			}
		}
		return model;
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
		if (!Model.validate(k)) {
			flag = false;
		}
		if (l != -1 && !Model.validate(l)) {
			flag = false;
		}
		if (i1 != -1 && !Model.validate(i1)) {
			flag = false;
		}
		return flag;
	}

	public Model method196(int i) {
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
		Model model = Model.tryGet(j);
		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.tryGet(k);
				Model model_3 = Model.tryGet(l);
				Model[] aclass30_sub2_sub4_sub6_1 = {model, model_1, model_3};
				model = new Model(3, aclass30_sub2_sub4_sub6_1);
			} else {
				Model class30_sub2_sub4_sub6_2 = Model.tryGet(k);
				Model[] aclass30_sub2_sub4_sub6 = {model, class30_sub2_sub4_sub6_2};
				model = new Model(2, aclass30_sub2_sub4_sub6);
			}
		}
		if (i == 0 && aByte205 != 0) {
			model.translate(0, aByte205, 0);
		}
		if (i == 1 && aByte154 != 0) {
			model.translate(0, aByte154, 0);
		}
		if (anIntArray156 != null) {
			for (int i1 = 0; i1 < anIntArray156.length; i1++) {
				model.replaceColor(anIntArray156[i1], anIntArray160[i1]);
			}
		}
		return model;
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
		ObjType type = method198(anInt163);
		anInt174 = type.anInt174;
		anInt181 = type.anInt181;
		anInt190 = type.anInt190;
		anInt198 = type.anInt198;
		anInt204 = type.anInt204;
		anInt169 = type.anInt169;
		anInt194 = type.anInt194;
		anIntArray156 = type.anIntArray156;
		anIntArray160 = type.anIntArray160;
		ObjType type_1 = method198(anInt179);
		aString170 = type_1.aString170;
		aBoolean161 = type_1.aBoolean161;
		anInt155 = type_1.anInt155;
		String s = "a";
		char c = type_1.aString170.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		aByteArray178 = ("Swap this note at any bank for " + s + " " + type_1.aString170 + ".").getBytes();
		aBoolean176 = true;
	}

	public Model method201(int i) {
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
		Model model = (Model) aCache_159.method222(anInt157);
		if (model != null) {
			return model;
		}
		model = Model.tryGet(anInt174);
		if (model == null) {
			return null;
		}
		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128) {
			model.scale(anInt167, anInt191, anInt192);
		}
		if (anIntArray156 != null) {
			for (int l = 0; l < anIntArray156.length; l++) {
				model.replaceColor(anIntArray156[l], anIntArray160[l]);
			}
		}
		model.calculateNormals(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.pickBounds = true;
		aCache_159.method223(model, anInt157);
		return model;
	}

	public Model method202(int i) {
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
		Model model = Model.tryGet(anInt174);
		if (model == null) {
			return null;
		}
		if (anIntArray156 != null) {
			for (int l = 0; l < anIntArray156.length; l++) {
				model.replaceColor(anIntArray156[l], anIntArray160[l]);
			}
		}
		return model;
	}

	public void method203(Buffer buffer) {
		do {
			int i = buffer.method408();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt174 = buffer.method410();
			} else if (i == 2) {
				aString170 = buffer.method415();
			} else if (i == 3) {
				aByteArray178 = buffer.method416();
			} else if (i == 4) {
				anInt181 = buffer.method410();
			} else if (i == 5) {
				anInt190 = buffer.method410();
			} else if (i == 6) {
				anInt198 = buffer.method410();
			} else if (i == 7) {
				anInt169 = buffer.method410();
				if (anInt169 > 32767) {
					anInt169 -= 0x10000;
				}
			} else if (i == 8) {
				anInt194 = buffer.method410();
				if (anInt194 > 32767) {
					anInt194 -= 0x10000;
				}
			} else if (i == 10) {
				unusedInt = buffer.method410();
			} else if (i == 11) {
				aBoolean176 = true;
			} else if (i == 12) {
				anInt155 = buffer.method413();
			} else if (i == 16) {
				aBoolean161 = true;
			} else if (i == 23) {
				anInt165 = buffer.method410();
				aByte205 = buffer.method409();
			} else if (i == 24) {
				anInt188 = buffer.method410();
			} else if (i == 25) {
				anInt200 = buffer.method410();
				aByte154 = buffer.method409();
			} else if (i == 26) {
				anInt164 = buffer.method410();
			} else if (i >= 30 && i < 35) {
				if (aStringArray168 == null) {
					aStringArray168 = new String[5];
				}
				aStringArray168[i - 30] = buffer.method415();
				if (aStringArray168[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray168[i - 30] = null;
				}
			} else if (i >= 35 && i < 40) {
				if (aStringArray189 == null) {
					aStringArray189 = new String[5];
				}
				aStringArray189[i - 35] = buffer.method415();
			} else if (i == 40) {
				int j = buffer.method408();
				anIntArray156 = new int[j];
				anIntArray160 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray156[k] = buffer.method410();
					anIntArray160[k] = buffer.method410();
				}
			} else if (i == 78) {
				anInt185 = buffer.method410();
			} else if (i == 79) {
				anInt162 = buffer.method410();
			} else if (i == 90) {
				anInt175 = buffer.method410();
			} else if (i == 91) {
				anInt197 = buffer.method410();
			} else if (i == 92) {
				anInt166 = buffer.method410();
			} else if (i == 93) {
				anInt173 = buffer.method410();
			} else if (i == 95) {
				anInt204 = buffer.method410();
			} else if (i == 97) {
				anInt179 = buffer.method410();
			} else if (i == 98) {
				anInt163 = buffer.method410();
			} else if (i >= 100 && i < 110) {
				if (anIntArray193 == null) {
					anIntArray193 = new int[10];
					anIntArray201 = new int[10];
				}
				anIntArray193[i - 100] = buffer.method410();
				anIntArray201[i - 100] = buffer.method410();
			} else if (i == 110) {
				anInt167 = buffer.method410();
			} else if (i == 111) {
				anInt192 = buffer.method410();
			} else if (i == 112) {
				anInt191 = buffer.method410();
			} else if (i == 113) {
				anInt196 = buffer.method409();
			} else if (i == 114) {
				anInt184 = buffer.method409() * 5;
			} else if (i == 115) {
				anInt202 = buffer.method408();
			}
		} while (true);
	}

}
