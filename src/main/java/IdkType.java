// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class IdkType {

	public static int count;
	public static IdkType[] instances;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("idk.dat"));
		count = buffer.read16U();
		if (instances == null) {
			instances = new IdkType[count];
		}
		for (int j = 0; j < count; j++) {
			if (instances[j] == null) {
				instances[j] = new IdkType();
			}
			instances[j].read(buffer);
		}
	}

	public final int[] colorSrc = new int[6];
	public final int[] colorDst = new int[6];
	public final int[] headModelIDs = {-1, -1, -1, -1, -1};
	public int type = -1;
	public int[] modelIDs;
	public boolean selectable = false;

	public IdkType() {
	}

	public void read(Buffer in) {
		do {
			int opcode = in.read8U();
			if (opcode == 0) {
				return;
			}

			if (opcode == 1) {
				type = in.read8U();
			} else if (opcode == 2) {
				int j = in.read8U();
				modelIDs = new int[j];
				for (int k = 0; k < j; k++) {
					modelIDs[k] = in.read16U();
				}
			} else if (opcode == 3) {
				selectable = true;
			} else if ((opcode >= 40) && (opcode < 50)) {
				colorSrc[opcode - 40] = in.read16U();
			} else if ((opcode >= 50) && (opcode < 60)) {
				colorDst[opcode - 50] = in.read16U();
			} else if ((opcode >= 60) && (opcode < 70)) {
				headModelIDs[opcode - 60] = in.read16U();
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		} while (true);
	}

	public boolean validateModel() {
		if (modelIDs == null) {
			return true;
		}
		boolean loaded = true;
		for (int modelId : modelIDs) {
			if (!Model.validate(modelId)) {
				loaded = false;
			}
		}
		return loaded;
	}

	public Model getModel() {
		if (modelIDs == null) {
			return null;
		}
		Model[] models = new Model[modelIDs.length];
		for (int i = 0; i < modelIDs.length; i++) {
			models[i] = Model.tryGet(modelIDs[i]);
		}

		Model model;

		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}

		for (int i = 0; i < 6; i++) {
			if (colorSrc[i] == 0) {
				break;
			}
			model.recolor(colorSrc[i], colorDst[i]);
		}

		return model;
	}

	public boolean validateHeadModel() {
		boolean loaded = true;
		for (int i = 0; i < 5; i++) {
			if ((headModelIDs[i] != -1) && !Model.validate(headModelIDs[i])) {
				loaded = false;
			}
		}
		return loaded;
	}

	public Model getHeadModel() {
		Model[] models = new Model[5];
		int i = 0;
		for (int j = 0; j < 5; j++) {
			if (headModelIDs[j] != -1) {
				models[i++] = Model.tryGet(headModelIDs[j]);
			}
		}
		Model model = new Model(i, models);
		for (int k = 0; k < 6; k++) {
			if (colorSrc[k] == 0) {
				break;
			}
			model.recolor(colorSrc[k], colorDst[k]);
		}
		return model;
	}

}
