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

	public static void unpack(FileArchive archive) {
		Packet packet = new Packet(archive.read("varbit.dat", null));
		anInt645 = packet.get2U();
		if (aVarbitArray646 == null) {
			aVarbitArray646 = new VarbitType[anInt645];
		}
		for (int j = 0; j < anInt645; j++) {
			if (aVarbitArray646[j] == null) {
				aVarbitArray646[j] = new VarbitType();
			}
			aVarbitArray646[j].method534(packet);
			if (aVarbitArray646[j].aBoolean651) {
				VarpType.instances[aVarbitArray646[j].anInt648].unusedBool3 = true;
			}
		}
		if (packet.position != packet.data.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public void method534(Packet packet) {
		do {
			int j = packet.get1U();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				anInt648 = packet.get2U();
				anInt649 = packet.get1U();
				anInt650 = packet.get1U();
			} else if (j == 10) {
				unusedString = packet.getString();
			} else if (j == 2) {
				aBoolean651 = true;
			} else if (j == 3) {
				unusedInt0 = packet.get4();
			} else if (j == 4) {
				unusedInt1 = packet.get4();
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

}
