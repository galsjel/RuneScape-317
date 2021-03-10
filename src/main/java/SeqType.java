// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SeqType {

	public static int anInt350;
	public static SeqType[] aTypeArray351;
	public int anInt352;
	public int[] anIntArray353;
	public int[] anIntArray354;
	public int[] anIntArray355;
	public int anInt356 = -1;
	public int[] anIntArray357;
	public boolean aBoolean358 = false;
	public int anInt359 = 5;
	public int anInt360 = -1;
	public int anInt361 = -1;
	public int anInt362 = 99;
	public int anInt363 = -1;
	public int anInt364 = -1;
	public int anInt365 = 2;
	public int unusedInt;

	public SeqType() {
	}

	public static void method257(FileArchive archive) {
		Buffer buffer = new Buffer(archive.read("seq.dat", null));
		anInt350 = buffer.get2U();
		if (aTypeArray351 == null) {
			aTypeArray351 = new SeqType[anInt350];
		}
		for (int j = 0; j < anInt350; j++) {
			if (aTypeArray351[j] == null) {
				aTypeArray351[j] = new SeqType();
			}
			aTypeArray351[j].method259(buffer);
		}
	}

	public int method258(int i) {
		int j = anIntArray355[i];
		if (j == 0) {
			SeqFrame transform = SeqFrame.get(anIntArray353[i]);
			if (transform != null) {
				j = anIntArray355[i] = transform.delay;
			}
		}
		if (j == 0) {
			j = 1;
		}
		return j;
	}

	public void method259(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				break;
			}
			if (i == 1) {
				anInt352 = buffer.get1U();
				anIntArray353 = new int[anInt352];
				anIntArray354 = new int[anInt352];
				anIntArray355 = new int[anInt352];
				for (int j = 0; j < anInt352; j++) {
					anIntArray353[j] = buffer.get2U();
					anIntArray354[j] = buffer.get2U();
					if (anIntArray354[j] == 65535) {
						anIntArray354[j] = -1;
					}
					anIntArray355[j] = buffer.get2U();
				}
			} else if (i == 2) {
				anInt356 = buffer.get2U();
			} else if (i == 3) {
				int k = buffer.get1U();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++) {
					anIntArray357[l] = buffer.get1U();
				}
				anIntArray357[k] = 9999999;
			} else if (i == 4) {
				aBoolean358 = true;
			} else if (i == 5) {
				anInt359 = buffer.get1U();
			} else if (i == 6) {
				anInt360 = buffer.get2U();
			} else if (i == 7) {
				anInt361 = buffer.get2U();
			} else if (i == 8) {
				anInt362 = buffer.get1U();
			} else if (i == 9) {
				anInt363 = buffer.get1U();
			} else if (i == 10) {
				anInt364 = buffer.get1U();
			} else if (i == 11) {
				anInt365 = buffer.get1U();
			} else if (i == 12) {
				unusedInt = buffer.get4();
			} else {
				System.out.println("Error unrecognised seq config code: " + i);
			}
		} while (true);
		if (anInt352 == 0) {
			anInt352 = 1;
			anIntArray353 = new int[1];
			anIntArray353[0] = -1;
			anIntArray354 = new int[1];
			anIntArray354[0] = -1;
			anIntArray355 = new int[1];
			anIntArray355[0] = -1;
		}
		if (anInt363 == -1) {
			if (anIntArray357 != null) {
				anInt363 = 2;
			} else {
				anInt363 = 0;
			}
		}
		if (anInt364 == -1) {
			if (anIntArray357 != null) {
				anInt364 = 2;
				return;
			}
			anInt364 = 0;
		}
	}

}
