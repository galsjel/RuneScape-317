// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class PlayerEntity extends PathingEntity {

	public static LRUCache aCache_1704 = new LRUCache(260);
	public long aLong1697 = -1L;
	public NPCType aType_1698;
	public boolean aBoolean1699 = false;
	public final int[] anIntArray1700 = new int[5];
	public int anInt1701;
	public int anInt1702;
	public String aString1703;
	public int anInt1705;
	public int anInt1706;
	public int anInt1707;
	public int anInt1708;
	public int anInt1709;
	public boolean aBoolean1710 = false;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public Model aModel_1714;
	public final int[] anIntArray1717 = new int[12];
	public long aLong1718;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public int anInt1723;

	public PlayerEntity() {
	}

	@Override
	public Model method444() {
		if (!aBoolean1710) {
			return null;
		}
		Model model = method452();
		if (model == null) {
			return null;
		}
		super.anInt1507 = model.anInt1426;
		model.aBoolean1659 = true;
		if (aBoolean1699) {
			return model;
		}
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			SpotAnimType type = SpotAnimType.aTypeArray403[super.anInt1520];
			Model class30_sub2_sub4_sub6_2 = type.method266();
			if (class30_sub2_sub4_sub6_2 != null) {
				Model model_3 = new Model(true, SeqTransform.method532(super.anInt1521), false, class30_sub2_sub4_sub6_2);
				model_3.method475(0, -super.anInt1524, 0);
				model_3.method469();
				model_3.method470(type.aType_407.anIntArray353[super.anInt1521]);
				model_3.anIntArrayArray1658 = null;
				model_3.anIntArrayArray1657 = null;
				if (type.anInt410 != 128 || type.anInt411 != 128) {
					model_3.method478(type.anInt410, type.anInt410, type.anInt411);
				}
				model_3.method479(64 + type.anInt413, 850 + type.anInt414, -30, -50, -30, true);
				Model[] aclass30_sub2_sub4_sub6_1 = {model, model_3};
				model = new Model(2, -819, aclass30_sub2_sub4_sub6_1);
			}
		}
		if (aModel_1714 != null) {
			if (Game.anInt1161 >= anInt1708) {
				aModel_1714 = null;
			}
			if (Game.anInt1161 >= anInt1707 && Game.anInt1161 < anInt1708) {
				Model model_1 = aModel_1714;
				model_1.method475(anInt1711 - super.anInt1550, anInt1712 - anInt1709, anInt1713 - super.anInt1551);
				if (super.anInt1510 == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.anInt1510 == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.anInt1510 == 1536) {
					model_1.method473();
				}
				Model[] aclass30_sub2_sub4_sub6 = {model, model_1};
				model = new Model(2, -819, aclass30_sub2_sub4_sub6);
				if (super.anInt1510 == 512) {
					model_1.method473();
				} else if (super.anInt1510 == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.anInt1510 == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.method475(super.anInt1550 - anInt1711, anInt1709 - anInt1712, super.anInt1551 - anInt1713);
			}
		}
		model.aBoolean1659 = true;
		return model;
	}

	public void method451(Buffer buffer) {
		buffer.anInt1406 = 0;
		anInt1702 = buffer.method408();
		anInt1706 = buffer.method408();
		aType_1698 = null;
		anInt1701 = 0;
		for (int j = 0; j < 12; j++) {
			int k = buffer.method408();
			if (k == 0) {
				anIntArray1717[j] = 0;
				continue;
			}
			int i1 = buffer.method408();
			anIntArray1717[j] = (k << 8) + i1;
			if (j == 0 && anIntArray1717[0] == 65535) {
				aType_1698 = NPCType.method159(buffer.method410());
				break;
			}
			if (anIntArray1717[j] >= 512 && anIntArray1717[j] - 512 < ObjType.anInt203) {
				int l1 = ObjType.method198(anIntArray1717[j] - 512).anInt202;
				if (l1 != 0) {
					anInt1701 = l1;
				}
			}
		}
		for (int l = 0; l < 5; l++) {
			int j1 = buffer.method408();
			if (j1 < 0 || j1 >= Game.anIntArrayArray1003[l].length) {
				j1 = 0;
			}
			anIntArray1700[l] = j1;
		}
		super.anInt1511 = buffer.method410();
		if (super.anInt1511 == 65535) {
			super.anInt1511 = -1;
		}
		super.anInt1512 = buffer.method410();
		if (super.anInt1512 == 65535) {
			super.anInt1512 = -1;
		}
		super.anInt1554 = buffer.method410();
		if (super.anInt1554 == 65535) {
			super.anInt1554 = -1;
		}
		super.anInt1555 = buffer.method410();
		if (super.anInt1555 == 65535) {
			super.anInt1555 = -1;
		}
		super.anInt1556 = buffer.method410();
		if (super.anInt1556 == 65535) {
			super.anInt1556 = -1;
		}
		super.anInt1557 = buffer.method410();
		if (super.anInt1557 == 65535) {
			super.anInt1557 = -1;
		}
		super.anInt1505 = buffer.method410();
		if (super.anInt1505 == 65535) {
			super.anInt1505 = -1;
		}
		aString1703 = Base37.method587(Base37.method584(buffer.method414()));
		anInt1705 = buffer.method408();
		anInt1723 = buffer.method410();
		aBoolean1710 = true;
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (anIntArray1717[k1] >= 256) {
				aLong1718 += anIntArray1717[k1] - 256;
			}
		}
		if (anIntArray1717[0] >= 256) {
			aLong1718 += anIntArray1717[0] - 256 >> 4;
		}
		if (anIntArray1717[1] >= 256) {
			aLong1718 += anIntArray1717[1] - 256 >> 8;
		}
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}
		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model method452() {
		if (aType_1698 != null) {
			int j = -1;
			if (super.anInt1526 >= 0 && super.anInt1529 == 0) {
				j = SeqType.aTypeArray351[super.anInt1526].anIntArray353[super.anInt1527];
			} else if (super.anInt1517 >= 0) {
				j = SeqType.aTypeArray351[super.anInt1517].anIntArray353[super.anInt1518];
			}
			return aType_1698.method164(-1, j, null);
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if (super.anInt1526 >= 0 && super.anInt1529 == 0) {
			SeqType type = SeqType.aTypeArray351[super.anInt1526];
			k = type.anIntArray353[super.anInt1527];
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = SeqType.aTypeArray351[super.anInt1517].anIntArray353[super.anInt1518];
			}
			if (type.anInt360 >= 0) {
				j1 = type.anInt360;
				l += j1 - anIntArray1717[5] << 8;
			}
			if (type.anInt361 >= 0) {
				k1 = type.anInt361;
				l += k1 - anIntArray1717[3] << 16;
			}
		} else if (super.anInt1517 >= 0) {
			k = SeqType.aTypeArray351[super.anInt1517].anIntArray353[super.anInt1518];
		}
		Model model_1 = (Model) aCache_1704.method222(l);
		if (model_1 == null) {
			boolean flag = false;
			for (int i2 = 0; i2 < 12; i2++) {
				int k2 = anIntArray1717[i2];
				if (k1 >= 0 && i2 == 3) {
					k2 = k1;
				}
				if (j1 >= 0 && i2 == 5) {
					k2 = j1;
				}
				if (k2 >= 256 && k2 < 512 && !IDKType.aIDKTypeArray656[k2 - 256].method537()) {
					flag = true;
				}
				if (k2 >= 512 && !ObjType.method198(k2 - 512).method195(anInt1702)) {
					flag = true;
				}
			}
			if (flag) {
				if (aLong1697 != -1L) {
					model_1 = (Model) aCache_1704.method222(aLong1697);
				}
				if (model_1 == null) {
					return null;
				}
			}
		}
		if (model_1 == null) {
			Model[] aclass30_sub2_sub4_sub6 = new Model[12];
			int j2 = 0;
			for (int l2 = 0; l2 < 12; l2++) {
				int i3 = anIntArray1717[l2];
				if (k1 >= 0 && l2 == 3) {
					i3 = k1;
				}
				if (j1 >= 0 && l2 == 5) {
					i3 = j1;
				}
				if (i3 >= 256 && i3 < 512) {
					Model model_3 = IDKType.aIDKTypeArray656[i3 - 256].method538();
					if (model_3 != null) {
						aclass30_sub2_sub4_sub6[j2++] = model_3;
					}
				}
				if (i3 >= 512) {
					Model class30_sub2_sub4_sub6_4 = ObjType.method198(i3 - 512).method196(anInt1702);
					if (class30_sub2_sub4_sub6_4 != null) {
						aclass30_sub2_sub4_sub6[j2++] = class30_sub2_sub4_sub6_4;
					}
				}
			}
			model_1 = new Model(j2, aclass30_sub2_sub4_sub6);
			for (int j3 = 0; j3 < 5; j3++) {
				if (anIntArray1700[j3] != 0) {
					model_1.method476(Game.anIntArrayArray1003[j3][0], Game.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if (j3 == 1) {
						model_1.method476(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j3]]);
					}
				}
			}
			model_1.method469();
			model_1.method479(64, 850, -30, -50, -30, true);
			aCache_1704.method223(model_1, l);
			aLong1697 = l;
		}
		if (aBoolean1699) {
			return model_1;
		}
		Model class30_sub2_sub4_sub6_2 = Model.A_MODEL___1621;
		class30_sub2_sub4_sub6_2.method464(model_1, SeqTransform.method532(k) & SeqTransform.method532(i1));
		if (k != -1 && i1 != -1) {
			class30_sub2_sub4_sub6_2.method471(SeqType.aTypeArray351[super.anInt1526].anIntArray357, i1, k);
		} else if (k != -1) {
			class30_sub2_sub4_sub6_2.method470(k);
		}
		class30_sub2_sub4_sub6_2.method466();
		class30_sub2_sub4_sub6_2.anIntArrayArray1658 = null;
		class30_sub2_sub4_sub6_2.anIntArrayArray1657 = null;
		return class30_sub2_sub4_sub6_2;
	}

	@Override
	public boolean method449() {
		return aBoolean1710;
	}

	public Model method453() {
		if (!aBoolean1710) {
			return null;
		}
		if (aType_1698 != null) {
			return aType_1698.method160();
		}
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = anIntArray1717[i];
			if (j >= 256 && j < 512 && !IDKType.aIDKTypeArray656[j - 256].method539()) {
				flag = true;
			}
			if (j >= 512 && !ObjType.method198(j - 512).method192(anInt1702)) {
				flag = true;
			}
		}
		if (flag) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6 = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = anIntArray1717[l];
			if (i1 >= 256 && i1 < 512) {
				Model model_1 = IDKType.aIDKTypeArray656[i1 - 256].method540();
				if (model_1 != null) {
					aclass30_sub2_sub4_sub6[k++] = model_1;
				}
			}
			if (i1 >= 512) {
				Model class30_sub2_sub4_sub6_2 = ObjType.method198(i1 - 512).method194(anInt1702);
				if (class30_sub2_sub4_sub6_2 != null) {
					aclass30_sub2_sub4_sub6[k++] = class30_sub2_sub4_sub6_2;
				}
			}
		}
		Model model = new Model(k, aclass30_sub2_sub4_sub6);
		for (int j1 = 0; j1 < 5; j1++) {
			if (anIntArray1700[j1] != 0) {
				model.method476(Game.anIntArrayArray1003[j1][0], Game.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1) {
					model.method476(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j1]]);
				}
			}
		}
		return model;
	}

}
