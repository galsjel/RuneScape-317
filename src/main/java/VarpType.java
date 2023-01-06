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
		anInt700 = buffer.read16U();
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
	public int type;
	public boolean unusedBool2 = false;
	public int unusedInt2;
	public int unusedInt3;
	public boolean unusedBool3 = false;
	public int unusedInt4 = -1;

	public VarpType() {
	}

	public void read(Buffer buffer, int i) {
		do {
			int opcode = buffer.read8U();
			if (opcode == 0) {
				return;
			} else if (opcode == 1) {
				unusedInt0 = buffer.read8U();
			} else if (opcode == 2) {
				unusedInt1 = buffer.read8U();
			} else if (opcode == 3) {
				unusedBool0 = true;
				anIntArray703[anInt702++] = i;
			} else if (opcode == 4) {
				unusedBool1 = false;
			} else if (opcode == 5) {
				type = buffer.read16U();
			} else if (opcode == 6) {
				unusedBool2 = true;
			} else if (opcode == 7) {
				unusedInt2 = buffer.read32();
			} else if (opcode == 8) {
				unusedInt3 = 1;
				unusedBool3 = true;
			} else if (opcode == 10) {
				unusedString = buffer.readString();
			} else if (opcode == 11) {
				unusedBool3 = true;
			} else if (opcode == 12) {
				unusedInt4 = buffer.read32();
			} else if (opcode == 13) {
				unusedInt3 = 2;
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		} while (true);
	}

}
