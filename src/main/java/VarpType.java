// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class VarpType {

	public static int anInt700;
	public static VarpType[] instances;
	public static int anInt702;
	public static int[] anIntArray703;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("varp.dat"));
		anInt702 = 0;
		anInt700 = buffer.get2U();
		if (instances == null) {
			instances = new VarpType[anInt700];
		}
		if (anIntArray703 == null) {
			anIntArray703 = new int[anInt700];
		}
		for (int j = 0; j < anInt700; j++) {
			if (instances[j] == null) {
				instances[j] = new VarpType();
			}
			instances[j].read(buffer, j);
		}
		if (buffer.position != buffer.data.length) {
			System.out.println("varptype load mismatch");
		}
	}

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

	public void read(Buffer buffer, int i) {
		do {
			int op = buffer.get1U();
			if (op == 0) {
				return;
			} else if (op == 1) {
				unusedInt0 = buffer.get1U();
			} else if (op == 2) {
				unusedInt1 = buffer.get1U();
			} else if (op == 3) {
				unusedBool0 = true;
				anIntArray703[anInt702++] = i;
			} else if (op == 4) {
				unusedBool1 = false;
			} else if (op == 5) {
				anInt709 = buffer.get2U();
			} else if (op == 6) {
				unusedBool2 = true;
			} else if (op == 7) {
				unusedInt2 = buffer.get4();
			} else if (op == 8) {
				unusedInt3 = 1;
				unusedBool3 = true;
			} else if (op == 10) {
				unusedString = buffer.getString();
			} else if (op == 11) {
				unusedBool3 = true;
			} else if (op == 12) {
				unusedInt4 = buffer.get4();
			} else if (op == 13) {
				unusedInt3 = 2;
			} else {
				System.out.println("Error unrecognised config code: " + op);
			}
		} while (true);
	}

}
