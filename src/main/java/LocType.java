// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LocType {

	public static final Model[] A_MODEL_ARRAY_741 = new Model[4];
	public static boolean aBoolean752;
	public static Buffer aBuffer_753;
	public static int[] anIntArray755;
	public static int anInt756;
	public static Game aGame765;
	public static int anInt771;
	public static LRUCache aCache_780 = new LRUCache(30);
	public static LocType[] aTypeArray782;
	public static LRUCache aCache_785 = new LRUCache(500);

	public boolean aBoolean736;
	public byte aByte737;
	public int anInt738;
	public String aString739;
	public int anInt740;
	public byte aByte742;
	public int anInt744;
	public int anInt745;
	public int anInt746;
	public int[] anIntArray747;
	public int anInt748;
	public int anInt749;
	public boolean aBoolean751;
	public int anInt754 = -1;
	public boolean aBoolean757;
	public int anInt758;
	public int[] anIntArray759;
	public int anInt760;
	public int anInt761;
	public boolean aBoolean762;
	public boolean aBoolean764;
	public boolean aBoolean766;
	public boolean aBoolean767;
	public int anInt768;
	public boolean aBoolean769;
	public int anInt772;
	public int[] anIntArray773;
	public int anInt774;
	public int anInt775;
	public int[] anIntArray776;
	public byte[] aByteArray777;
	public boolean aBoolean778;
	public boolean aBoolean779;
	public int anInt781;
	public int anInt783;
	public int[] anIntArray784;
	public String[] aStringArray786;

	public LocType() {
	}

	public static LocType method572(int i) {
		for (int j = 0; j < 20; j++) {
			if (aTypeArray782[j].anInt754 == i) {
				return aTypeArray782[j];
			}
		}
		anInt771 = (anInt771 + 1) % 20;
		LocType type = aTypeArray782[anInt771];
		aBuffer_753.position = anIntArray755[i];
		type.anInt754 = i;
		type.method573();
		type.method582(aBuffer_753);
		return type;
	}

	public static void method575() {
		aCache_785 = null;
		aCache_780 = null;
		anIntArray755 = null;
		aTypeArray782 = null;
		aBuffer_753 = null;
	}

	public static void method576(FileArchive archive) {
		aBuffer_753 = new Buffer(archive.method571("loc.dat", null));
		Buffer buffer = new Buffer(archive.method571("loc.idx", null));
		anInt756 = buffer.method410();
		anIntArray755 = new int[anInt756];
		int i = 2;
		for (int j = 0; j < anInt756; j++) {
			anIntArray755[j] = i;
			i += buffer.method410();
		}
		aTypeArray782 = new LocType[20];
		for (int k = 0; k < 20; k++) {
			aTypeArray782[k] = new LocType();
		}
	}

	public void method573() {
		anIntArray773 = null;
		anIntArray776 = null;
		aString739 = null;
		aByteArray777 = null;
		anIntArray784 = null;
		anIntArray747 = null;
		anInt744 = 1;
		anInt761 = 1;
		aBoolean767 = true;
		aBoolean757 = true;
		aBoolean778 = false;
		aBoolean762 = false;
		aBoolean769 = false;
		aBoolean764 = false;
		anInt781 = -1;
		anInt775 = 16;
		aByte737 = 0;
		aByte742 = 0;
		aStringArray786 = null;
		anInt746 = -1;
		anInt758 = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		anInt748 = 128;
		anInt772 = 128;
		anInt740 = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		anInt760 = -1;
		anInt774 = -1;
		anInt749 = -1;
		anIntArray759 = null;
	}

	public void method574(OnDemand onDemand) {
		if (anIntArray773 == null) {
			return;
		}
		for (int k : anIntArray773) {
			onDemand.method560(k & 0xffff, 0);
		}
	}

	public boolean method577(int i) {
		if (anIntArray776 == null) {
			if (anIntArray773 == null) {
				return true;
			}
			if (i != 10) {
				return true;
			}
			boolean flag1 = true;
			for (int j : anIntArray773) {
				flag1 &= Model.validate(j & 0xffff);
			}
			return flag1;
		}
		for (int j = 0; j < anIntArray776.length; j++) {
			if (anIntArray776[j] == i) {
				return Model.validate(anIntArray773[j] & 0xffff);
			}
		}
		return true;
	}

	public Model method578(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = method581(i, k1, j);
		if (model == null) {
			return null;
		}
		if (aBoolean762 || aBoolean769) {
			model = new Model(aBoolean762, aBoolean769, model);
		}
		if (aBoolean762) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.vertexCount; i2++) {
				int j2 = model.vertexX[i2];
				int k2 = model.vertexZ[i2];
				int l2 = k + (((l - k) * (j2 + 64)) / 128);
				int i3 = j1 + (((i1 - j1) * (j2 + 64)) / 128);
				int j3 = l2 + (((i3 - l2) * (k2 + 64)) / 128);
				model.vertexY[i2] += j3 - l1;
			}
			model.calculateBoundsY();
		}
		return model;
	}

	public boolean method579() {
		if (anIntArray773 == null) {
			return true;
		}
		boolean flag1 = true;
		for (int j : anIntArray773) {
			flag1 &= Model.validate(j & 0xffff);
		}
		return flag1;
	}

	public LocType method580() {
		int i = -1;
		if (anInt774 != -1) {
			VarbitType varbit = VarbitType.aVarbitArray646[anInt774];
			int j = varbit.anInt648;
			int k = varbit.anInt649;
			int l = varbit.anInt650;
			int i1 = Game.anIntArray1232[l - k];
			i = (aGame765.anIntArray971[j] >> k) & i1;
		} else if (anInt749 != -1) {
			i = aGame765.anIntArray971[anInt749];
		}
		if ((i < 0) || (i >= anIntArray759.length) || (anIntArray759[i] == -1)) {
			return null;
		} else {
			return method572(anIntArray759[i]);
		}
	}

	public Model method581(int j, int k, int l) {
		Model model = null;
		long l1;
		if (anIntArray776 == null) {
			if (j != 10) {
				return null;
			}
			l1 = (long) ((anInt754 << 6) + l) + ((long) (k + 1) << 32);
			Model model_1 = (Model) aCache_780.method222(l1);
			if (model_1 != null) {
				return model_1;
			}
			if (anIntArray773 == null) {
				return null;
			}
			boolean flag1 = aBoolean751 ^ (l > 3);
			int k1 = anIntArray773.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = anIntArray773[i2];
				if (flag1) {
					l2 += 0x10000;
				}
				model = (Model) aCache_785.method222(l2);
				if (model == null) {
					model = Model.tryGet(l2 & 0xffff);
					if (model == null) {
						return null;
					}
					if (flag1) {
						model.rotateY180();
					}
					aCache_785.method223(model, l2);
				}
				if (k1 > 1) {
					A_MODEL_ARRAY_741[i2] = model;
				}
			}
			if (k1 > 1) {
				model = new Model(k1, A_MODEL_ARRAY_741);
			}
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < anIntArray776.length; j1++) {
				if (anIntArray776[j1] != j) {
					continue;
				}
				i1 = j1;
				break;
			}
			if (i1 == -1) {
				return null;
			}
			l1 = (long) ((anInt754 << 6) + (i1 << 3) + l) + ((long) (k + 1) << 32);
			Model class30_sub2_sub4_sub6_2 = (Model) aCache_780.method222(l1);
			if (class30_sub2_sub4_sub6_2 != null) {
				return class30_sub2_sub4_sub6_2;
			}
			int j2 = anIntArray773[i1];
			boolean flag3 = aBoolean751 ^ (l > 3);
			if (flag3) {
				j2 += 0x10000;
			}
			model = (Model) aCache_785.method222(j2);
			if (model == null) {
				model = Model.tryGet(j2 & 0xffff);
				if (model == null) {
					return null;
				}
				if (flag3) {
					model.rotateY180();
				}
				aCache_785.method223(model, j2);
			}
		}
		boolean flag;
		flag = (anInt748 != 128) || (anInt772 != 128) || (anInt740 != 128);
		boolean flag2;
		flag2 = (anInt738 != 0) || (anInt745 != 0) || (anInt783 != 0);
		Model model_3 = new Model(anIntArray784 == null, SeqFrame.isNull(k), (l == 0) && (k == -1) && !flag && !flag2, model);
		if (k != -1) {
			model_3.createLabelReferences();
			model_3.applySequenceFrame(k);
			model_3.labelFaces = null;
			model_3.labelVertices = null;
		}
		while (l-- > 0) {
			model_3.rotateY90();
		}
		if (anIntArray784 != null) {
			for (int k2 = 0; k2 < anIntArray784.length; k2++) {
				model_3.replaceColor(anIntArray784[k2], anIntArray747[k2]);
			}
		}
		if (flag) {
			model_3.scale(anInt748, anInt740, anInt772);
		}
		if (flag2) {
			model_3.translate(anInt738, anInt745, anInt783);
		}
		model_3.calculateNormals(64 + aByte737, 768 + (aByte742 * 5), -50, -10, -50, !aBoolean769);
		if (anInt760 == 1) {
			model_3.anInt1654 = model_3.minY;
		}
		aCache_780.method223(model_3, l1);
		return model_3;
	}

	public void method582(Buffer buffer) {
		int i = -1;
		label0:
		do {
			int j;
			do {
				j = buffer.method408();
				if (j == 0) {
					break label0;
				}
				if (j == 1) {
					int k = buffer.method408();
					if (k > 0) {
						if ((anIntArray773 == null) || aBoolean752) {
							anIntArray776 = new int[k];
							anIntArray773 = new int[k];
							for (int k1 = 0; k1 < k; k1++) {
								anIntArray773[k1] = buffer.method410();
								anIntArray776[k1] = buffer.method408();
							}
						} else {
							buffer.position += k * 3;
						}
					}
				} else if (j == 2) {
					aString739 = buffer.method415();
				} else if (j == 3) {
					aByteArray777 = buffer.method416();
				} else if (j == 5) {
					int l = buffer.method408();
					if (l > 0) {
						if ((anIntArray773 == null) || aBoolean752) {
							anIntArray776 = null;
							anIntArray773 = new int[l];
							for (int l1 = 0; l1 < l; l1++) {
								anIntArray773[l1] = buffer.method410();
							}
						} else {
							buffer.position += l * 2;
						}
					}
				} else if (j == 14) {
					anInt744 = buffer.method408();
				} else if (j == 15) {
					anInt761 = buffer.method408();
				} else if (j == 17) {
					aBoolean767 = false;
				} else if (j == 18) {
					aBoolean757 = false;
				} else if (j == 19) {
					i = buffer.method408();
					if (i == 1) {
						aBoolean778 = true;
					}
				} else if (j == 21) {
					aBoolean762 = true;
				} else if (j == 22) {
					aBoolean769 = true;
				} else if (j == 23) {
					aBoolean764 = true;
				} else if (j == 24) {
					anInt781 = buffer.method410();
					if (anInt781 == 65535) {
						anInt781 = -1;
					}
				} else if (j == 28) {
					anInt775 = buffer.method408();
				} else if (j == 29) {
					aByte737 = buffer.method409();
				} else if (j == 39) {
					aByte742 = buffer.method409();
				} else if ((j >= 30) && (j < 39)) {
					if (aStringArray786 == null) {
						aStringArray786 = new String[5];
					}
					aStringArray786[j - 30] = buffer.method415();
					if (aStringArray786[j - 30].equalsIgnoreCase("hidden")) {
						aStringArray786[j - 30] = null;
					}
				} else if (j == 40) {
					int i1 = buffer.method408();
					anIntArray784 = new int[i1];
					anIntArray747 = new int[i1];
					for (int i2 = 0; i2 < i1; i2++) {
						anIntArray784[i2] = buffer.method410();
						anIntArray747[i2] = buffer.method410();
					}
				} else if (j == 60) {
					anInt746 = buffer.method410();
				} else if (j == 62) {
					aBoolean751 = true;
				} else if (j == 64) {
					aBoolean779 = false;
				} else if (j == 65) {
					anInt748 = buffer.method410();
				} else if (j == 66) {
					anInt772 = buffer.method410();
				} else if (j == 67) {
					anInt740 = buffer.method410();
				} else if (j == 68) {
					anInt758 = buffer.method410();
				} else if (j == 69) {
					anInt768 = buffer.method408();
				} else if (j == 70) {
					anInt738 = buffer.method411();
				} else if (j == 71) {
					anInt745 = buffer.method411();
				} else if (j == 72) {
					anInt783 = buffer.method411();
				} else if (j == 73) {
					aBoolean736 = true;
				} else if (j == 74) {
					aBoolean766 = true;
				} else {
					if (j != 75) {
						continue;
					}
					anInt760 = buffer.method408();
				}
				continue label0;
			} while (j != 77);
			anInt774 = buffer.method410();
			if (anInt774 == 65535) {
				anInt774 = -1;
			}
			anInt749 = buffer.method410();
			if (anInt749 == 65535) {
				anInt749 = -1;
			}
			int j1 = buffer.method408();
			anIntArray759 = new int[j1 + 1];
			for (int j2 = 0; j2 <= j1; j2++) {
				anIntArray759[j2] = buffer.method410();
				if (anIntArray759[j2] == 65535) {
					anIntArray759[j2] = -1;
				}
			}
		} while (true);
		if (i == -1) {
			aBoolean778 = (anIntArray773 != null) && ((anIntArray776 == null) || (anIntArray776[0] == 10));
			if (aStringArray786 != null) {
				aBoolean778 = true;
			}
		}
		if (aBoolean766) {
			aBoolean767 = false;
			aBoolean757 = false;
		}
		if (anInt760 == -1) {
			anInt760 = aBoolean767 ? 1 : 0;
		}
	}

}
