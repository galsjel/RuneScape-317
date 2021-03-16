// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class VarbitType {

	public static VarbitType[] instances;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("varbit.dat"));
		int count = buffer.get2U();

		if (instances == null) {
			instances = new VarbitType[count];
		}

		for (int j = 0; j < count; j++) {
			if (instances[j] == null) {
				instances[j] = new VarbitType();
			}
			instances[j].read(buffer);
			if (instances[j].unusedBool) {
				VarpType.instances[instances[j].varp].unusedBool3 = true;
			}
		}
		if (buffer.position != buffer.data.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public String unusedString;
	public int varp;
	/**
	 * The least significant bit.
	 */
	public int lsb;
	/**
	 * The most significant bit.
	 */
	public int msb;
	public boolean unusedBool = false;
	public int unusedInt0 = -1;
	public int unusedInt1;

	public VarbitType() {
	}

	public void read(Buffer buffer) {
		do {
			int op = buffer.get1U();
			if (op == 0) {
				return;
			} else if (op == 1) {
				varp = buffer.get2U();
				lsb = buffer.get1U();
				msb = buffer.get1U();
			} else if (op == 10) {
				unusedString = buffer.getString();
			} else if (op == 2) {
				unusedBool = true;
			} else if (op == 3) {
				unusedInt0 = buffer.get4();
			} else if (op == 4) {
				unusedInt1 = buffer.get4();
			} else {
				System.out.println("Error unrecognised config code: " + op);
			}
		} while (true);
	}

}
