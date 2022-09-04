// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class NPCType {

	private static int cachePosition;
	private static Buffer dat;
	public static int[] offsets;
	private static NPCType[] cache;
	public static Game game;
	public static int count;
	public static LRUMap<Long, Model> modelCache = new LRUMap<>(30);

	public static NPCType get(int id) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].uid == (long) id) {
				return cache[j];
			}
		}
		cachePosition = (cachePosition + 1) % 20;
		NPCType type = cache[cachePosition] = new NPCType();
		dat.position = offsets[id];
		type.uid = id;
		type.read(dat);
		return type;
	}

	public static void unpack(FileArchive archive) throws IOException {
		dat = new Buffer(archive.read("npc.dat"));
		Buffer buffer = new Buffer(archive.read("npc.idx"));
		count = buffer.get2U();
		offsets = new int[count];

		int offset = 2;
		for (int i = 0; i < count; i++) {
			offsets[i] = offset;
			offset += buffer.get2U();
		}

		cache = new NPCType[20];

		for (int k = 0; k < 20; k++) {
			cache[k] = new NPCType();
		}
	}

	public static void unload() {
		modelCache = null;
		offsets = null;
		cache = null;
		dat = null;
	}

	public int seqTurnRightId = -1;
	public int varbitId = -1;
	public int seqTurnAroundId = -1;
	public int varpId = -1;
	public int level = -1;
	public String name;
	public String[] op;
	public int seqWalkId = -1;
	public byte size = 1;
	public int[] colorDst;
	public int unusedInt0 = -1;
	public int[] headModelIds;
	public int headicon = -1;
	public int[] colorSrc;
	public int seqStand = -1;
	public long uid = -1L;
	public int turnSpeed = 32;
	public int seqTurnLeft = -1;
	public boolean aBoolean84 = true;
	public int anInt85;
	public int anInt86 = 128;
	public boolean aBoolean87 = true;
	public int[] overrides;
	public byte[] desc;
	public int unusedInt1 = -1;
	public int anInt91 = 128;
	public int anInt92;
	public boolean aBoolean93 = false;
	public int[] modelIds;
	public int unusedInt2 = -1;

	public NPCType() {
	}

	public Model getUnlitHeadModel() {
		if (overrides != null) {
			NPCType type = getOverrideType();
			if (type == null) {
				return null;
			} else {
				return type.getUnlitHeadModel();
			}
		}

		if (headModelIds == null) {
			return null;
		}

		boolean loaded = false;

		for (int value : headModelIds) {
			if (!Model.validate(value)) {
				loaded = true;
			}
		}

		if (loaded) {
			return null;
		}

		Model[] models = new Model[headModelIds.length];

		for (int i = 0; i < headModelIds.length; i++) {
			models[i] = Model.tryGet(headModelIds[i]);
		}

		Model model;

		if (models.length == 1) {
			model = models[0];
		} else {
			model = new Model(models.length, models);
		}

		if (colorSrc != null) {
			for (int i = 0; i < colorSrc.length; i++) {
				model.recolor(colorSrc[i], colorDst[i]);
			}
		}
		return model;
	}

	public NPCType getOverrideType() {
		int value = -1;

		if (varbitId != -1) {
			VarbitType vb = VarbitType.instances[this.varbitId];
			int varp = vb.varp;
			int lsb = vb.lsb;
			int msb = vb.msb;
			int mask = Game.BITMASK[msb - lsb];
			value = (game.variables[varp] >> lsb) & mask;
		} else if (varpId != -1) {
			value = game.variables[varpId];
		}

		if ((value < 0) || (value >= overrides.length) || (overrides[value] == -1)) {
			return null;
		} else {
			return get(overrides[value]);
		}
	}

	public Model getSequencedModel(int secondaryTransformID, int primaryTransformID, int[] seqMask) {
		if (overrides != null) {
			NPCType override = getOverrideType();
			if (override == null) {
				return null;
			} else {
				return override.getSequencedModel(secondaryTransformID, primaryTransformID, seqMask);
			}
		}

		Model model = modelCache.get(uid);

		if (model == null) {
			boolean invalid = false;

			for (int value : modelIds) {
				if (!Model.validate(value)) {
					invalid = true;
				}
			}

			if (invalid) {
				return null;
			}

			Model[] models = new Model[modelIds.length];

			for (int i = 0; i < modelIds.length; i++) {
				models[i] = Model.tryGet(modelIds[i]);
			}

			if (models.length == 1) {
				model = models[0];
			} else {
				model = new Model(models.length, models);
			}

			if (colorSrc != null) {
				for (int i = 0; i < colorSrc.length; i++) {
					model.recolor(colorSrc[i], colorDst[i]);
				}
			}

			model.createLabelReferences();
			model.calculateNormals(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
			modelCache.put(uid, model);
		}

		Model model_1 = Model.EMPTY;
		model_1.set(model, SeqTransform.isNull(primaryTransformID) & SeqTransform.isNull(secondaryTransformID));

		if ((primaryTransformID != -1) && (secondaryTransformID != -1)) {
			model_1.applyTransforms(primaryTransformID, secondaryTransformID, seqMask);
		} else if (primaryTransformID != -1) {
			model_1.applyTransform(primaryTransformID);
		}

		if ((anInt91 != 128) || (anInt86 != 128)) {
			model_1.scale(anInt91, anInt91, anInt86);
		}
		model_1.calculateBoundsCylinder();
		model_1.labelFaces = null;
		model_1.labelVertices = null;

		if (size == 1) {
			model_1.pickable = true;
		}
		return model_1;
	}

	public void read(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				int j = buffer.get1U();
				modelIds = new int[j];
				for (int j1 = 0; j1 < j; j1++) {
					modelIds[j1] = buffer.get2U();
				}
			} else if (i == 2) {
				name = buffer.getString();
			} else if (i == 3) {
				desc = buffer.getStringRaw();
			} else if (i == 12) {
				size = buffer.get1();
			} else if (i == 13) {
				seqStand = buffer.get2U();
			} else if (i == 14) {
				seqWalkId = buffer.get2U();
			} else if (i == 17) {
				seqWalkId = buffer.get2U();
				seqTurnAroundId = buffer.get2U();
				seqTurnLeft = buffer.get2U();
				seqTurnRightId = buffer.get2U();
			} else if ((i >= 30) && (i < 40)) {
				if (op == null) {
					op = new String[5];
				}
				op[i - 30] = buffer.getString();
				if (op[i - 30].equalsIgnoreCase("hidden")) {
					op[i - 30] = null;
				}
			} else if (i == 40) {
				int k = buffer.get1U();
				colorSrc = new int[k];
				colorDst = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					colorSrc[k1] = buffer.get2U();
					colorDst[k1] = buffer.get2U();
				}
			} else if (i == 60) {
				int l = buffer.get1U();
				headModelIds = new int[l];
				for (int l1 = 0; l1 < l; l1++) {
					headModelIds[l1] = buffer.get2U();
				}
			} else if (i == 90) {
				unusedInt2 = buffer.get2U();
			} else if (i == 91) {
				unusedInt0 = buffer.get2U();
			} else if (i == 92) {
				unusedInt1 = buffer.get2U();
			} else if (i == 93) {
				aBoolean87 = false;
			} else if (i == 95) {
				level = buffer.get2U();
			} else if (i == 97) {
				anInt91 = buffer.get2U();
			} else if (i == 98) {
				anInt86 = buffer.get2U();
			} else if (i == 99) {
				aBoolean93 = true;
			} else if (i == 100) {
				anInt85 = buffer.get1();
			} else if (i == 101) {
				anInt92 = buffer.get1() * 5;
			} else if (i == 102) {
				headicon = buffer.get2U();
			} else if (i == 103) {
				turnSpeed = buffer.get2U();
			} else if (i == 106) {
				varbitId = buffer.get2U();
				if (varbitId == 65535) {
					varbitId = -1;
				}
				varpId = buffer.get2U();
				if (varpId == 65535) {
					varpId = -1;
				}
				int i1 = buffer.get1U();
				overrides = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					overrides[i2] = buffer.get2U();
					if (overrides[i2] == 65535) {
						overrides[i2] = -1;
					}
				}
			} else if (i == 107) {
				aBoolean84 = false;
			}
		} while (true);
	}

}
