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
		Buffer buffer = new Buffer(archive.method571("varbit.dat", null));
		anInt645 = buffer.method410();
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
		if (buffer.position != buffer.aByteArray1405.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public void method534(Buffer buffer) {
		do {
			int j = buffer.method408();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				anInt648 = buffer.method410();
				anInt649 = buffer.method408();
				anInt650 = buffer.method408();
			} else if (j == 10) {
				unusedString = buffer.method415();
			} else if (j == 2) {
				aBoolean651 = true;
			} else if (j == 3) {
				unusedInt0 = buffer.method413();
			} else if (j == 4) {
				unusedInt1 = buffer.method413();
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
