// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class VarpType {

	public static int anInt700;
	public static VarpType[] aVarpArray701;
	public static int anInt702;
	public static int[] anIntArray703;

	public String unusedString;
	public int unusedInt0;
	public int unusedInt1;
	public boolean unusedBool0 = false;
	public boolean unusedBool1 = true;
	public int anInt709;
	public boolean unusedBool2 = false;
	public int unusedInt2;
	public int unusedInt3;
	public boolean unusedBool3 = false;
	public int unusedInt4 = -1;

	public VarpType() {
	}

	public static void method546(FileArchive archive) {
		Buffer buffer = new Buffer(archive.read("varp.dat", null));
		anInt702 = 0;
		anInt700 = buffer.getU16();
		if (aVarpArray701 == null) {
			aVarpArray701 = new VarpType[anInt700];
		}
		if (anIntArray703 == null) {
			anIntArray703 = new int[anInt700];
		}
		for (int j = 0; j < anInt700; j++) {
			if (aVarpArray701[j] == null) {
				aVarpArray701[j] = new VarpType();
			}
			aVarpArray701[j].method547(buffer, j);
		}
		if (buffer.position != buffer.data.length) {
			System.out.println("varptype load mismatch");
		}
	}

	public void method547(Buffer buffer, int i) {
		do {
			int j = buffer.getU8();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				unusedInt0 = buffer.getU8();
			} else if (j == 2) {
				unusedInt1 = buffer.getU8();
			} else if (j == 3) {
				unusedBool0 = true;
				anIntArray703[anInt702++] = i;
			} else if (j == 4) {
				unusedBool1 = false;
			} else if (j == 5) {
				anInt709 = buffer.getU16();
			} else if (j == 6) {
				unusedBool2 = true;
			} else if (j == 7) {
				unusedInt2 = buffer.get32();
			} else if (j == 8) {
				unusedInt3 = 1;
				unusedBool3 = true;
			} else if (j == 10) {
				unusedString = buffer.getString();
			} else if (j == 11) {
				unusedBool3 = true;
			} else if (j == 12) {
				unusedInt4 = buffer.get32();
			} else if (j == 13) {
				unusedInt3 = 2;
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
