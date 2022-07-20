// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class IDKType {

	public static int count;
	public static IDKType[] instances;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("idk.dat"));
		count = buffer.get2U();
		if (instances == null) {
			instances = new IDKType[count];
		}
		for (int j = 0; j < count; j++) {
			if (instances[j] == null) {
				instances[j] = new IDKType();
			}
			instances[j].method536(buffer);
		}
	}

	public final int[] colorSrc = new int[6];
	public final int[] colorDst = new int[6];
	public final int[] headModelIds = {-1, -1, -1, -1, -1};
	public int type = -1;
	public int[] modelIds;
	public boolean selectable = false;

	public IDKType() {
	}

	public void method536(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				type = buffer.get1U();
			} else if (i == 2) {
				int j = buffer.get1U();
				modelIds = new int[j];
				for (int k = 0; k < j; k++) {
					modelIds[k] = buffer.get2U();
				}
			} else if (i == 3) {
				selectable = true;
			} else if ((i >= 40) && (i < 50)) {
				colorSrc[i - 40] = buffer.get2U();
			} else if ((i >= 50) && (i < 60)) {
				colorDst[i - 50] = buffer.get2U();
			} else if ((i >= 60) && (i < 70)) {
				headModelIds[i - 60] = buffer.get2U();
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	public boolean validateModel() {
		if (modelIds == null) {
			return true;
		}
		boolean loaded = true;
		for (int modelId : modelIds) {
			if (!Model.validate(modelId)) {
				loaded = false;
			}
		}
		return loaded;
	}

	public Model getModel() {
		if (modelIds == null) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6 = new Model[modelIds.length];
		for (int i = 0; i < modelIds.length; i++) {
			aclass30_sub2_sub4_sub6[i] = Model.tryGet(modelIds[i]);
		}
		Model model;
		if (aclass30_sub2_sub4_sub6.length == 1) {
			model = aclass30_sub2_sub4_sub6[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
		}
		for (int j = 0; j < 6; j++) {
			if (colorSrc[j] == 0) {
				break;
			}
			model.recolor(colorSrc[j], colorDst[j]);
		}
		return model;
	}

	public boolean validateHeadModel() {
		boolean loaded = true;
		for (int i = 0; i < 5; i++) {
			if ((headModelIds[i] != -1) && !Model.validate(headModelIds[i])) {
				loaded = false;
			}
		}
		return loaded;
	}

	public Model method540() {
		Model[] aclass30_sub2_sub4_sub6 = new Model[5];
		int j = 0;
		for (int k = 0; k < 5; k++) {
			if (headModelIds[k] != -1) {
				aclass30_sub2_sub4_sub6[j++] = Model.tryGet(headModelIds[k]);
			}
		}
		Model model = new Model(j, aclass30_sub2_sub4_sub6);
		for (int l = 0; l < 6; l++) {
			if (colorSrc[l] == 0) {
				break;
			}
			model.recolor(colorSrc[l], colorDst[l]);
		}
		return model;
	}

}
