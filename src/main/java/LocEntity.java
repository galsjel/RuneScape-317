// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LocEntity extends Entity {

	public static Game aGame1609;
	public int anInt1599;
	public final int[] anIntArray1600;
	public final int anInt1601;
	public final int anInt1602;
	public final int anInt1603;
	public final int anInt1604;
	public final int anInt1605;
	public final int anInt1606;
	public SeqType aType_1607;
	public int anInt1608;
	public final int anInt1610;
	public final int anInt1611;
	public final int anInt1612;

	public LocEntity(int i, int j, int k, int l, int i1, int j1, int k1, int l1, boolean flag) {
		anInt1610 = i;
		anInt1611 = k;
		anInt1612 = j;
		anInt1603 = j1;
		anInt1604 = l;
		anInt1605 = i1;
		anInt1606 = k1;
		if (l1 != -1) {
			aType_1607 = SeqType.aTypeArray351[l1];
			anInt1599 = 0;
			anInt1608 = Game.anInt1161;
			if (flag && (aType_1607.anInt356 != -1)) {
				anInt1599 = (int) (Math.random() * (double) aType_1607.anInt352);
				anInt1608 -= (int) (Math.random() * (double) aType_1607.method258(anInt1599));
			}
		}
		LocType type = LocType.method572(anInt1610);
		anInt1601 = type.anInt774;
		anInt1602 = type.anInt749;
		anIntArray1600 = type.anIntArray759;
	}

	@Override
	public Model getModel() {
		int j = -1;
		if (aType_1607 != null) {
			int k = Game.anInt1161 - anInt1608;
			if ((k > 100) && (aType_1607.anInt356 > 0)) {
				k = 100;
			}
			while (k > aType_1607.method258(anInt1599)) {
				k -= aType_1607.method258(anInt1599);
				anInt1599++;
				if (anInt1599 < aType_1607.anInt352) {
					continue;
				}
				anInt1599 -= aType_1607.anInt356;
				if ((anInt1599 >= 0) && (anInt1599 < aType_1607.anInt352)) {
					continue;
				}
				aType_1607 = null;
				break;
			}
			anInt1608 = Game.anInt1161 - k;
			if (aType_1607 != null) {
				j = aType_1607.anIntArray353[anInt1599];
			}
		}
		LocType type;
		if (anIntArray1600 != null) {
			type = method457();
		} else {
			type = LocType.method572(anInt1610);
		}
		if (type == null) {
			return null;
		} else {
			return type.method578(anInt1611, anInt1612, anInt1603, anInt1604, anInt1605, anInt1606, j);
		}
	}

	public LocType method457() {
		int i = -1;
		if (anInt1601 != -1) {
			VarbitType varbit = VarbitType.aVarbitArray646[anInt1601];
			int k = varbit.anInt648;
			int l = varbit.anInt649;
			int i1 = varbit.anInt650;
			int j1 = Game.anIntArray1232[i1 - l];
			i = (aGame1609.anIntArray971[k] >> l) & j1;
		} else if (anInt1602 != -1) {
			i = aGame1609.anIntArray971[anInt1602];
		}
		if ((i < 0) || (i >= anIntArray1600.length) || (anIntArray1600[i] == -1)) {
			return null;
		} else {
			return LocType.method572(anIntArray1600[i]);
		}
	}

}
