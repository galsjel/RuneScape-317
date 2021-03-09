// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SoundEnvelope {

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

	public SoundEnvelope() {
	}

	public void method325(Buffer buffer) {
		anInt540 = buffer.getU8();
		anInt538 = buffer.get32();
		anInt539 = buffer.get32();
		method326(buffer);
	}

	public void method326(Buffer buffer) {
		anInt535 = buffer.getU8();
		anIntArray536 = new int[anInt535];
		anIntArray537 = new int[anInt535];
		for (int i = 0; i < anInt535; i++) {
			anIntArray536[i] = buffer.getU16();
			anIntArray537[i] = buffer.getU16();
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
		return (anInt544 - anInt543) >> 15;
	}

}
