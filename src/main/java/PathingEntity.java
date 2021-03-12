// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class PathingEntity extends Entity {

	public final int[] anIntArray1500 = new int[10];
	public final int[] anIntArray1501 = new int[10];
	public int anInt1502 = -1;
	public int anInt1503;
	public int anInt1504 = 32;
	public int anInt1505 = -1;
	public String aString1506;
	public int anInt1507 = 200;
	public int anInt1510;
	public int anInt1511 = -1;
	public int anInt1512 = -1;
	public int anInt1513;
	public final int[] anIntArray1514 = new int[4];
	public final int[] anIntArray1515 = new int[4];
	public final int[] anIntArray1516 = new int[4];
	public int anInt1517 = -1;
	public int anInt1518;
	public int anInt1519;
	public int anInt1520 = -1;
	public int anInt1521;
	public int anInt1522;
	public int anInt1523;
	public int anInt1524;
	public int anInt1525;
	public int anInt1526 = -1;
	public int anInt1527;
	public int anInt1528;
	public int anInt1529;
	public int anInt1530;
	public int anInt1531;
	public int anInt1532 = -1000;
	public int anInt1533;
	public int anInt1534;
	public int anInt1535 = 100;
	public int anInt1537;
	public int anInt1538;
	public int anInt1539;
	public int anInt1540 = 1;
	public boolean aBoolean1541 = false;
	public int anInt1542;
	public int anInt1543;
	public int anInt1544;
	public int anInt1545;
	public int anInt1546;
	public int anInt1547;
	public int anInt1548;
	public int anInt1549;
	public int anInt1550;
	public int anInt1551;
	public int anInt1552;
	public final boolean[] aBooleanArray1553 = new boolean[10];
	public int anInt1554 = -1;
	public int anInt1555 = -1;
	public int anInt1556 = -1;
	public int anInt1557 = -1;

	public PathingEntity() {
	}

	public void method445(int i, int j, boolean flag) {
		if ((anInt1526 != -1) && (SeqType.instances[anInt1526].anInt364 == 1)) {
			anInt1526 = -1;
		}
		if (!flag) {
			int k = i - anIntArray1500[0];
			int l = j - anIntArray1501[0];
			if ((k >= -8) && (k <= 8) && (l >= -8) && (l <= 8)) {
				if (anInt1525 < 9) {
					anInt1525++;
				}
				for (int i1 = anInt1525; i1 > 0; i1--) {
					anIntArray1500[i1] = anIntArray1500[i1 - 1];
					anIntArray1501[i1] = anIntArray1501[i1 - 1];
					aBooleanArray1553[i1] = aBooleanArray1553[i1 - 1];
				}
				anIntArray1500[0] = i;
				anIntArray1501[0] = j;
				aBooleanArray1553[0] = false;
				return;
			}
		}
		anInt1525 = 0;
		anInt1542 = 0;
		anInt1503 = 0;
		anIntArray1500[0] = i;
		anIntArray1501[0] = j;
		anInt1550 = (anIntArray1500[0] * 128) + (anInt1540 * 64);
		anInt1551 = (anIntArray1501[0] * 128) + (anInt1540 * 64);
	}

	public void method446() {
		anInt1525 = 0;
		anInt1542 = 0;
	}

	public void method447(int j, int k, int l) {
		for (int i1 = 0; i1 < 4; i1++) {
			if (anIntArray1516[i1] <= l) {
				anIntArray1514[i1] = k;
				anIntArray1515[i1] = j;
				anIntArray1516[i1] = l + 70;
				return;
			}
		}
	}

	public void method448(boolean flag, int i) {
		int j = anIntArray1500[0];
		int k = anIntArray1501[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1) {
			k++;
		}
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3) {
			j--;
		}
		if (i == 4) {
			j++;
		}
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6) {
			k--;
		}
		if (i == 7) {
			j++;
			k--;
		}
		if ((anInt1526 != -1) && (SeqType.instances[anInt1526].anInt364 == 1)) {
			anInt1526 = -1;
		}
		if (anInt1525 < 9) {
			anInt1525++;
		}
		for (int l = anInt1525; l > 0; l--) {
			anIntArray1500[l] = anIntArray1500[l - 1];
			anIntArray1501[l] = anIntArray1501[l - 1];
			aBooleanArray1553[l] = aBooleanArray1553[l - 1];
		}
		anIntArray1500[0] = j;
		anIntArray1501[0] = k;
		aBooleanArray1553[0] = flag;
	}

	public boolean method449() {
		return false;
	}

}
