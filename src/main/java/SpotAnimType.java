// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class SpotAnimType {

	public static int anInt402;
	public static SpotAnimType[] instances;
	public static LRUCache modelCache = new LRUCache(30);
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

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("spotanim.dat"));
		anInt402 = buffer.get2U();
		if (instances == null) {
			instances = new SpotAnimType[anInt402];
		}
		for (int j = 0; j < anInt402; j++) {
			if (instances[j] == null) {
				instances[j] = new SpotAnimType();
			}
			instances[j].anInt404 = j;
			instances[j].method265(buffer);
		}
	}

	public void method265(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt405 = buffer.get2U();
			} else if (i == 2) {
				anInt406 = buffer.get2U();
				if (SeqType.instances != null) {
					aType_407 = SeqType.instances[anInt406];
				}
			} else if (i == 4) {
				anInt410 = buffer.get2U();
			} else if (i == 5) {
				anInt411 = buffer.get2U();
			} else if (i == 6) {
				anInt412 = buffer.get2U();
			} else if (i == 7) {
				anInt413 = buffer.get1U();
			} else if (i == 8) {
				anInt414 = buffer.get1U();
			} else if ((i >= 40) && (i < 50)) {
				anIntArray408[i - 40] = buffer.get2U();
			} else if ((i >= 50) && (i < 60)) {
				anIntArray409[i - 50] = buffer.get2U();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model method266() {
		Model model = (Model) modelCache.get(anInt404);
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
		modelCache.put(anInt404, model);
		return model;
	}

}
