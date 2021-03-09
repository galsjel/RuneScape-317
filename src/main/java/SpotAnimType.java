// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SpotAnimType {

	public static int anInt402;
	public static SpotAnimType[] aTypeArray403;
	public static LRUCache aCache_415 = new LRUCache(30);
	public int anInt404;
	public int anInt405;
	public int anInt406 = -1;
	public SeqType aType_407;
	public final int[] anIntArray408 = new int[6];
	public final int[] anIntArray409 = new int[6];
	public int anInt410 = 128;
	public int anInt411 = 128;
	public int anInt412;
	public int anInt413;
	public int anInt414;

	public SpotAnimType() {
	}

	public static void method264(FileArchive archive) {
		Buffer buffer = new Buffer(archive.read("spotanim.dat", null));
		anInt402 = buffer.getU16();
		if (aTypeArray403 == null) {
			aTypeArray403 = new SpotAnimType[anInt402];
		}
		for (int j = 0; j < anInt402; j++) {
			if (aTypeArray403[j] == null) {
				aTypeArray403[j] = new SpotAnimType();
			}
			aTypeArray403[j].anInt404 = j;
			aTypeArray403[j].method265(buffer);
		}
	}

	public void method265(Buffer buffer) {
		do {
			int i = buffer.getU8();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt405 = buffer.getU16();
			} else if (i == 2) {
				anInt406 = buffer.getU16();
				if (SeqType.aTypeArray351 != null) {
					aType_407 = SeqType.aTypeArray351[anInt406];
				}
			} else if (i == 4) {
				anInt410 = buffer.getU16();
			} else if (i == 5) {
				anInt411 = buffer.getU16();
			} else if (i == 6) {
				anInt412 = buffer.getU16();
			} else if (i == 7) {
				anInt413 = buffer.getU8();
			} else if (i == 8) {
				anInt414 = buffer.getU8();
			} else if ((i >= 40) && (i < 50)) {
				anIntArray408[i - 40] = buffer.getU16();
			} else if ((i >= 50) && (i < 60)) {
				anIntArray409[i - 50] = buffer.getU16();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model method266() {
		Model model = (Model) aCache_415.method222(anInt404);
		if (model != null) {
			return model;
		}
		model = Model.tryGet(anInt405);
		if (model == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray408[0] != 0) {
				model.replaceColor(anIntArray408[i], anIntArray409[i]);
			}
		}
		aCache_415.method223(model, anInt404);
		return model;
	}

}
