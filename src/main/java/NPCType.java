// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class NPCType {

	public static int anInt56;
	public static Buffer aBuffer_60;
	public static int anInt62;
	public static int[] anIntArray72;
	public static NPCType[] aTypeArray80;
	public static Game aGame82;
	public static LRUCache aCache_95 = new LRUCache(30);

	public static NPCType method159(int i) {
		if (i > aTypeArray80.length) {
			i = 0;
		}

		for (int j = 0; j < 20; j++) {
			if (aTypeArray80[j].aLong78 == (long) i) {
				return aTypeArray80[j];
			}
		}
		anInt56 = (anInt56 + 1) % 20;
		NPCType type = aTypeArray80[anInt56] = new NPCType();
		aBuffer_60.position = anIntArray72[i];
		type.aLong78 = i;
		type.method165(aBuffer_60);
		return type;
	}

	public static void unpack(FileArchive archive) {
		aBuffer_60 = new Buffer(archive.read("npc.dat", null));
		Buffer buffer = new Buffer(archive.read("npc.idx", null));
		anInt62 = buffer.get2U();
		anIntArray72 = new int[anInt62];
		int i = 2;
		for (int j = 0; j < anInt62; j++) {
			anIntArray72[j] = i;
			i += buffer.get2U();
		}
		aTypeArray80 = new NPCType[20];
		for (int k = 0; k < 20; k++) {
			aTypeArray80[k] = new NPCType();
		}
	}

	public static void unload() {
		aCache_95 = null;
		anIntArray72 = null;
		aTypeArray80 = null;
		aBuffer_60 = null;
	}
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

	public NPCType() {
	}

	public Model method160() {
		if (anIntArray88 != null) {
			NPCType type = method161();
			if (type == null) {
				return null;
			} else {
				return type.method160();
			}
		}
		if (anIntArray73 == null) {
			return null;
		}
		boolean flag1 = false;
		for (int value : anIntArray73) {
			if (!Model.validate(value)) {
				flag1 = true;
			}
		}
		if (flag1) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6 = new Model[anIntArray73.length];
		for (int j = 0; j < anIntArray73.length; j++) {
			aclass30_sub2_sub4_sub6[j] = Model.tryGet(anIntArray73[j]);
		}
		Model model;
		if (aclass30_sub2_sub4_sub6.length == 1) {
			model = aclass30_sub2_sub4_sub6[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
		}
		if (anIntArray76 != null) {
			for (int k = 0; k < anIntArray76.length; k++) {
				model.replaceColor(anIntArray76[k], anIntArray70[k]);
			}
		}
		return model;
	}

	public NPCType method161() {
		int j = -1;
		if (anInt57 != -1) {
			VarbitType varbit = VarbitType.aVarbitArray646[anInt57];
			int k = varbit.anInt648;
			int l = varbit.anInt649;
			int i1 = varbit.anInt650;
			int j1 = Game.anIntArray1232[i1 - l];
			j = (aGame82.anIntArray971[k] >> l) & j1;
		} else if (anInt59 != -1) {
			j = aGame82.anIntArray971[anInt59];
		}
		if ((j < 0) || (j >= anIntArray88.length) || (anIntArray88[j] == -1)) {
			return null;
		} else {
			return method159(anIntArray88[j]);
		}
	}

	public Model method164(int j, int k, int[] ai) {
		if (anIntArray88 != null) {
			NPCType type = method161();
			if (type == null) {
				return null;
			} else {
				return type.method164(j, k, ai);
			}
		}
		Model model = (Model) aCache_95.get(aLong78);
		if (model == null) {
			boolean flag = false;
			for (int value : anIntArray94) {
				if (!Model.validate(value)) {
					flag = true;
				}
			}
			if (flag) {
				return null;
			}
			Model[] aclass30_sub2_sub4_sub6 = new Model[anIntArray94.length];
			for (int j1 = 0; j1 < anIntArray94.length; j1++) {
				aclass30_sub2_sub4_sub6[j1] = Model.tryGet(anIntArray94[j1]);
			}
			if (aclass30_sub2_sub4_sub6.length == 1) {
				model = aclass30_sub2_sub4_sub6[0];
			} else {
				model = new Model(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
			}
			if (anIntArray76 != null) {
				for (int k1 = 0; k1 < anIntArray76.length; k1++) {
					model.replaceColor(anIntArray76[k1], anIntArray70[k1]);
				}
			}
			model.createLabelReferences();
			model.calculateNormals(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
			aCache_95.put(aLong78, model);
		}
		Model model_1 = Model.EMPTY;
		model_1.set(model, SeqFrame.isNull(k) & SeqFrame.isNull(j));
		if ((k != -1) && (j != -1)) {
			model_1.applySequenceFrames(k, j, ai);
		} else if (k != -1) {
			model_1.applySequenceFrame(k);
		}
		if ((anInt91 != 128) || (anInt86 != 128)) {
			model_1.scale(anInt91, anInt91, anInt86);
		}
		model_1.calculateBoundsCylinder();
		model_1.labelFaces = null;
		model_1.labelVertices = null;
		if (aByte68 == 1) {
			model_1.pickBounds = true;
		}
		return model_1;
	}

	public void method165(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				int j = buffer.get1U();
				anIntArray94 = new int[j];
				for (int j1 = 0; j1 < j; j1++) {
					anIntArray94[j1] = buffer.get2U();
				}
			} else if (i == 2) {
				aString65 = buffer.getString();
			} else if (i == 3) {
				aByteArray89 = buffer.getStringRaw();
			} else if (i == 12) {
				aByte68 = buffer.get1();
			} else if (i == 13) {
				anInt77 = buffer.get2U();
			} else if (i == 14) {
				anInt67 = buffer.get2U();
			} else if (i == 17) {
				anInt67 = buffer.get2U();
				anInt58 = buffer.get2U();
				anInt83 = buffer.get2U();
				anInt55 = buffer.get2U();
			} else if ((i >= 30) && (i < 40)) {
				if (aStringArray66 == null) {
					aStringArray66 = new String[5];
				}
				aStringArray66[i - 30] = buffer.getString();
				if (aStringArray66[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray66[i - 30] = null;
				}
			} else if (i == 40) {
				int k = buffer.get1U();
				anIntArray76 = new int[k];
				anIntArray70 = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					anIntArray76[k1] = buffer.get2U();
					anIntArray70[k1] = buffer.get2U();
				}
			} else if (i == 60) {
				int l = buffer.get1U();
				anIntArray73 = new int[l];
				for (int l1 = 0; l1 < l; l1++) {
					anIntArray73[l1] = buffer.get2U();
				}
			} else if (i == 90) {
				unusedInt2 = buffer.get2U();
			} else if (i == 91) {
				unusedInt0 = buffer.get2U();
			} else if (i == 92) {
				unusedInt1 = buffer.get2U();
			} else if (i == 93) {
				aBoolean87 = false;
			} else if (i == 95) {
				anInt61 = buffer.get2U();
			} else if (i == 97) {
				anInt91 = buffer.get2U();
			} else if (i == 98) {
				anInt86 = buffer.get2U();
			} else if (i == 99) {
				aBoolean93 = true;
			} else if (i == 100) {
				anInt85 = buffer.get1();
			} else if (i == 101) {
				anInt92 = buffer.get1() * 5;
			} else if (i == 102) {
				anInt75 = buffer.get2U();
			} else if (i == 103) {
				anInt79 = buffer.get2U();
			} else if (i == 106) {
				anInt57 = buffer.get2U();
				if (anInt57 == 65535) {
					anInt57 = -1;
				}
				anInt59 = buffer.get2U();
				if (anInt59 == 65535) {
					anInt59 = -1;
				}
				int i1 = buffer.get1U();
				anIntArray88 = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					anIntArray88[i2] = buffer.get2U();
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
