// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class SpotAnimType {

	public static int count;
	public static SpotAnimType[] instances;
	public static LRUMap<Integer, Model> modelCache = new LRUMap<>(30);

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("spotanim.dat"));
		count = buffer.get2U();
		if (instances == null) {
			instances = new SpotAnimType[count];
		}
		for (int i = 0; i < count; i++) {
			if (instances[i] == null) {
				instances[i] = new SpotAnimType();
			}
			instances[i].index = i;
			instances[i].method265(buffer);
		}
	}

	public final int[] anIntArray408 = new int[6];
	public final int[] anIntArray409 = new int[6];
	public int index;
	public int anInt405;
	public int seqId = -1;
	public SeqType seq;
	public int anInt410 = 128;
	public int anInt411 = 128;
	public int anInt412;
	public int anInt413;
	public int anInt414;

	public SpotAnimType() {
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
				seqId = buffer.get2U();
				if (SeqType.instances != null) {
					seq = SeqType.instances[seqId];
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
		Model model = modelCache.get(index);
		if (model != null) {
			return model;
		}
		model = Model.tryGet(anInt405);
		if (model == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray408[0] != 0) {
				model.recolor(anIntArray408[i], anIntArray409[i]);
			}
		}
		modelCache.put(index, model);
		return model;
	}

}
