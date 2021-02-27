// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class29 {

	public int anInt535;
	public int[] anIntArray536;
	public int[] anIntArray537;
	public int anInt538;
	public int anInt539;
	public int anInt540;
	public int anInt541;
	public int anInt542;
	public int anInt543;
	public int anInt544;
	public int anInt545;

	public Class29() {
	}

	public void method325(Class30_Sub2_Sub2 class30_sub2_sub2) {
		anInt540 = class30_sub2_sub2.method408();
		anInt538 = class30_sub2_sub2.method413();
		anInt539 = class30_sub2_sub2.method413();
		method326(class30_sub2_sub2);
	}

	public void method326(Class30_Sub2_Sub2 class30_sub2_sub2) {
		anInt535 = class30_sub2_sub2.method408();
		anIntArray536 = new int[anInt535];
		anIntArray537 = new int[anInt535];
		for (int i = 0; i < anInt535; i++) {
			anIntArray536[i] = class30_sub2_sub2.method410();
			anIntArray537[i] = class30_sub2_sub2.method410();
		}
	}

	public void method327() {
		anInt541 = 0;
		anInt542 = 0;
		anInt543 = 0;
		anInt544 = 0;
		anInt545 = 0;
	}

	public int method328(int i) {
		if (anInt545 >= anInt541) {
			anInt544 = anIntArray537[anInt542++] << 15;
			if (anInt542 >= anInt535) {
				anInt542 = anInt535 - 1;
			}
			anInt541 = (int) (((double) anIntArray536[anInt542] / 65536D) * (double) i);
			if (anInt541 > anInt545) {
				anInt543 = ((anIntArray537[anInt542] << 15) - anInt544) / (anInt541 - anInt545);
			}
		}
		anInt544 += anInt543;
		anInt545++;
		return anInt544 - anInt543 >> 15;
	}

}
