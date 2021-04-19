// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LocEntity extends Entity {

	public static Game game;
	public final int[] anIntArray1600;
	public final int anInt1601;
	public final int anInt1602;
	public final int anInt1603;
	public final int anInt1604;
	public final int anInt1605;
	public final int anInt1606;
	public final int anInt1610;
	public final int anInt1611;
	public final int anInt1612;
	public int anInt1599;
	public SeqType aType_1607;
	public int anInt1608;

	public LocEntity(int i, int j, int k, int l, int i1, int j1, int k1, int l1, boolean flag) {
		anInt1610 = i;
		anInt1611 = k;
		anInt1612 = j;
		anInt1603 = j1;
		anInt1604 = l;
		anInt1605 = i1;
		anInt1606 = k1;
		if (l1 != -1) {
			aType_1607 = SeqType.instances[l1];
			anInt1599 = 0;
			anInt1608 = Game.loopCycle;
			if (flag && (aType_1607.anInt356 != -1)) {
				anInt1599 = (int) (Math.random() * (double) aType_1607.frameCount);
				anInt1608 -= (int) (Math.random() * (double) aType_1607.getFrameDelay(anInt1599));
			}
		}
		LocType type = LocType.get(anInt1610);
		anInt1601 = type.varbit;
		anInt1602 = type.varp;
		anIntArray1600 = type.overrideIds;
	}

	@Override
	public Model getModel() {
		int j = -1;
		if (aType_1607 != null) {
			int k = Game.loopCycle - anInt1608;
			if ((k > 100) && (aType_1607.anInt356 > 0)) {
				k = 100;
			}
			while (k > aType_1607.getFrameDelay(anInt1599)) {
				k -= aType_1607.getFrameDelay(anInt1599);
				anInt1599++;
				if (anInt1599 < aType_1607.frameCount) {
					continue;
				}
				anInt1599 -= aType_1607.anInt356;
				if ((anInt1599 >= 0) && (anInt1599 < aType_1607.frameCount)) {
					continue;
				}
				aType_1607 = null;
				break;
			}
			anInt1608 = Game.loopCycle - k;
			if (aType_1607 != null) {
				j = aType_1607.primaryFrames[anInt1599];
			}
		}
		LocType type;
		if (anIntArray1600 != null) {
			type = method457();
		} else {
			type = LocType.get(anInt1610);
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
			VarbitType varbit = VarbitType.instances[anInt1601];
			int k = varbit.varp;
			int l = varbit.lsb;
			int i1 = varbit.msb;
			int j1 = Game.BITMASK[i1 - l];
			i = (game.variables[k] >> l) & j1;
		} else if (anInt1602 != -1) {
			i = game.variables[anInt1602];
		}
		if ((i < 0) || (i >= anIntArray1600.length) || (anIntArray1600[i] == -1)) {
			return null;
		} else {
			return LocType.get(anIntArray1600[i]);
		}
	}

}
