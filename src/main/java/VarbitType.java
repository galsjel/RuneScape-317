// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class VarbitType {

	public static int anInt645;
	public static VarbitType[] aVarbitArray646;
	public String unusedString;
	public int anInt648;
	public int anInt649;
	public int anInt650;
	public boolean aBoolean651 = false;
	public int unusedInt0 = -1;
	public int unusedInt1;

	public VarbitType() {
	}

	public static void method533(FileArchive archive) {
		Buffer buffer = new Buffer(archive.read("varbit.dat", null));
		anInt645 = buffer.get2U();
		if (aVarbitArray646 == null) {
			aVarbitArray646 = new VarbitType[anInt645];
		}
		for (int j = 0; j < anInt645; j++) {
			if (aVarbitArray646[j] == null) {
				aVarbitArray646[j] = new VarbitType();
			}
			aVarbitArray646[j].method534(buffer);
			if (aVarbitArray646[j].aBoolean651) {
				VarpType.aVarpArray701[aVarbitArray646[j].anInt648].unusedBool3 = true;
			}
		}
		if (buffer.position != buffer.data.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public void method534(Buffer buffer) {
		do {
			int j = buffer.get1U();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				anInt648 = buffer.get2U();
				anInt649 = buffer.get1U();
				anInt650 = buffer.get1U();
			} else if (j == 10) {
				unusedString = buffer.getString();
			} else if (j == 2) {
				aBoolean651 = true;
			} else if (j == 3) {
				unusedInt0 = buffer.get4();
			} else if (j == 4) {
				unusedInt1 = buffer.get4();
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
