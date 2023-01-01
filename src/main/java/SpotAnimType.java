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
			instances[i].read(buffer);
		}
	}

	public final int[] colorSrc = new int[6];
	public final int[] colorDst = new int[6];
	public int index;
	public int modelID;
	public int seqID = -1;
	public SeqType seq;
	public int scaleXY = 128;
	public int scaleZ = 128;
	public int rotation;
	public int lightAmbient;
	public int lightAttenuation;

	public SpotAnimType() {
	}

	public void read(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelID = buffer.get2U();
			} else if (i == 2) {
				seqID = buffer.get2U();
				if (SeqType.instances != null) {
					seq = SeqType.instances[seqID];
				}
			} else if (i == 4) {
				scaleXY = buffer.get2U();
			} else if (i == 5) {
				scaleZ = buffer.get2U();
			} else if (i == 6) {
				rotation = buffer.get2U();
			} else if (i == 7) {
				lightAmbient = buffer.get1U();
			} else if (i == 8) {
				lightAttenuation = buffer.get1U();
			} else if ((i >= 40) && (i < 50)) {
				colorSrc[i - 40] = buffer.get2U();
			} else if ((i >= 50) && (i < 60)) {
				colorDst[i - 50] = buffer.get2U();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model getModel() {
		Model model = modelCache.get(index);

		if (model != null) {
			return model;
		}

		model = Model.tryGet(modelID);

		if (model == null) {
			return null;
		}

		for (int i = 0; i < 6; i++) {
			if (colorSrc[0] != 0) {
				model.recolor(colorSrc[i], colorDst[i]);
			}
		}

		modelCache.put(index, model);
		return model;
	}

}
