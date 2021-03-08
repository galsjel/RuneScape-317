// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class37 {

	public static int anInt645;
	public static Class37[] aClass37Array646;
	public String unusedString;
	public int anInt648;
	public int anInt649;
	public int anInt650;
	public boolean aBoolean651 = false;
	public int unusedInt0 = -1;
	public int unusedInt1;

	public Class37() {
	}

	public static void method533(Class44 class44) {
		Buffer buffer = new Buffer(class44.method571("varbit.dat", null));
		anInt645 = buffer.method410();
		if (aClass37Array646 == null) {
			aClass37Array646 = new Class37[anInt645];
		}
		for (int j = 0; j < anInt645; j++) {
			if (aClass37Array646[j] == null) {
				aClass37Array646[j] = new Class37();
			}
			aClass37Array646[j].method534(buffer);
			if (aClass37Array646[j].aBoolean651) {
				Class41.aClass41Array701[aClass37Array646[j].anInt648].unusedBool3 = true;
			}
		}
		if (buffer.anInt1406 != buffer.aByteArray1405.length) {
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
