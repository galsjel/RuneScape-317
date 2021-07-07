// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

public class PlayerEntity extends PathingEntity {

	public static LRUMap<Long, Model> modelCache = new LRUMap<>(260);
	public final int[] anIntArray1700 = new int[5];
	public final int[] appearance = new int[12];
	public long aLong1697 = -1L;
	public NPCType aType_1698;
	public boolean aBoolean1699 = false;
	public int team;
	public int gender;
	public String name;
	public int combatLevel;
	public int headicons;
	public int anInt1707;
	public int anInt1708;
	public int y;
	public boolean aBoolean1710 = false;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public Model model;
	public long aLong1718;
	public int minSceneTileX;
	public int minSceneTileZ;
	public int maxSceneTileX;
	public int maxSceneTileZ;
	public int skillLevel;

	public PlayerEntity() {
	}

	@Override
	public Model getModel() {
		if (!aBoolean1710) {
			return null;
		}
		Model model = method452();
		if (model == null) {
			return null;
		}
		super.height = model.minY;
		model.pickable = true;
		if (aBoolean1699) {
			return model;
		}
		if ((super.spotanim != -1) && (super.spotanimFrame != -1)) {
			SpotAnimType type = SpotAnimType.instances[super.spotanim];
			Model class30_sub2_sub4_sub6_2 = type.method266();
			if (class30_sub2_sub4_sub6_2 != null) {
				Model model_3 = new Model(true, SeqFrame.isNull(super.spotanimFrame), false, class30_sub2_sub4_sub6_2);
				model_3.translate(0, -super.anInt1524, 0);
				model_3.createLabelReferences();
				model_3.applySequenceFrame(type.seq.primaryFrames[super.spotanimFrame]);
				model_3.labelFaces = null;
				model_3.labelVertices = null;
				if ((type.anInt410 != 128) || (type.anInt411 != 128)) {
					model_3.scale(type.anInt410, type.anInt410, type.anInt411);
				}
				model_3.calculateNormals(64 + type.anInt413, 850 + type.anInt414, -30, -50, -30, true);
				Model[] aclass30_sub2_sub4_sub6_1 = {model, model_3};
				model = new Model(2, -819, aclass30_sub2_sub4_sub6_1);
			}
		}
		if (this.model != null) {
			if (Game.loopCycle >= anInt1708) {
				this.model = null;
			}
			if ((Game.loopCycle >= anInt1707) && (Game.loopCycle < anInt1708)) {
				Model model_1 = this.model;
				model_1.translate(anInt1711 - super.x, anInt1712 - y, anInt1713 - super.z);
				if (super.dstYaw == 512) {
					model_1.rotateY90();
					model_1.rotateY90();
					model_1.rotateY90();
				} else if (super.dstYaw == 1024) {
					model_1.rotateY90();
					model_1.rotateY90();
				} else if (super.dstYaw == 1536) {
					model_1.rotateY90();
				}
				Model[] aclass30_sub2_sub4_sub6 = {model, model_1};
				model = new Model(2, -819, aclass30_sub2_sub4_sub6);
				if (super.dstYaw == 512) {
					model_1.rotateY90();
				} else if (super.dstYaw == 1024) {
					model_1.rotateY90();
					model_1.rotateY90();
				} else if (super.dstYaw == 1536) {
					model_1.rotateY90();
					model_1.rotateY90();
					model_1.rotateY90();
				}
				model_1.translate(super.x - anInt1711, y - anInt1712, super.z - anInt1713);
			}
		}
		model.pickable = true;
		return model;
	}

	public void method451(Buffer buffer) {
		buffer.position = 0;
		gender = buffer.get1U();
		headicons = buffer.get1U();
		aType_1698 = null;
		team = 0;
		for (int j = 0; j < 12; j++) {
			int k = buffer.get1U();
			if (k == 0) {
				appearance[j] = 0;
				continue;
			}
			int i1 = buffer.get1U();
			appearance[j] = (k << 8) + i1;
			if ((j == 0) && (appearance[0] == 65535)) {
				aType_1698 = NPCType.get(buffer.get2U());
				break;
			}
			if ((appearance[j] >= 512) && ((appearance[j] - 512) < ObjType.count)) {
				int l1 = ObjType.get(appearance[j] - 512).team;
				if (l1 != 0) {
					team = l1;
				}
			}
		}
		for (int l = 0; l < 5; l++) {
			int j1 = buffer.get1U();
			if ((j1 < 0) || (j1 >= Game.anIntArrayArray1003[l].length)) {
				j1 = 0;
			}
			anIntArray1700[l] = j1;
		}
		super.seqStand = buffer.get2U();
		if (super.seqStand == 65535) {
			super.seqStand = -1;
		}
		super.seqTurn = buffer.get2U();
		if (super.seqTurn == 65535) {
			super.seqTurn = -1;
		}
		super.seqWalk = buffer.get2U();
		if (super.seqWalk == 65535) {
			super.seqWalk = -1;
		}
		super.seqTurnAround = buffer.get2U();
		if (super.seqTurnAround == 65535) {
			super.seqTurnAround = -1;
		}
		super.seqTurnLeft = buffer.get2U();
		if (super.seqTurnLeft == 65535) {
			super.seqTurnLeft = -1;
		}
		super.seqTurnRight = buffer.get2U();
		if (super.seqTurnRight == 65535) {
			super.seqTurnRight = -1;
		}
		super.seqRun = buffer.get2U();
		if (super.seqRun == 65535) {
			super.seqRun = -1;
		}
		name = StringUtil.formatName(StringUtil.fromBase37(buffer.get8()));
		combatLevel = buffer.get1U();
		skillLevel = buffer.get2U();
		aBoolean1710 = true;
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (appearance[k1] >= 256) {
				aLong1718 += appearance[k1] - 256;
			}
		}
		if (appearance[0] >= 256) {
			aLong1718 += (appearance[0] - 256) >> 4;
		}
		if (appearance[1] >= 256) {
			aLong1718 += (appearance[1] - 256) >> 8;
		}
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}
		aLong1718 <<= 1;
		aLong1718 += gender;
	}

	public Model method452() {
		if (aType_1698 != null) {
			int j = -1;
			if ((super.anInt1526 >= 0) && (super.anInt1529 == 0)) {
				j = SeqType.instances[super.anInt1526].primaryFrames[super.anInt1527];
			} else if (super.seqCurrent >= 0) {
				j = SeqType.instances[super.seqCurrent].primaryFrames[super.seqFrame];
			}
			return aType_1698.method164(-1, j, null);
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if ((super.anInt1526 >= 0) && (super.anInt1529 == 0)) {
			SeqType type = SeqType.instances[super.anInt1526];
			k = type.primaryFrames[super.anInt1527];
			if ((super.seqCurrent >= 0) && (super.seqCurrent != super.seqStand)) {
				i1 = SeqType.instances[super.seqCurrent].primaryFrames[super.seqFrame];
			}
			if (type.anInt360 >= 0) {
				j1 = type.anInt360;
				l += ((long) j1 - appearance[5]) << 8;
			}
			if (type.anInt361 >= 0) {
				k1 = type.anInt361;
				l += ((long) k1 - appearance[3]) << 16;
			}
		} else if (super.seqCurrent >= 0) {
			k = SeqType.instances[super.seqCurrent].primaryFrames[super.seqFrame];
		}

		Model model_1 = modelCache.get(l);

		if (model_1 == null) {
			boolean flag = false;
			for (int part = 0; part < 12; part++) {
				int value = appearance[part];
				if ((k1 >= 0) && (part == 3)) {
					value = k1;
				}
				if ((j1 >= 0) && (part == 5)) {
					value = j1;
				}
				if ((value >= 256) && (value < 512) && !IDKType.instances[value - 256].method537()) {
					flag = true;
				}
				if ((value >= 512) && !ObjType.get(value - 512).validateWornModel(gender)) {
					flag = true;
				}
			}
			if (flag) {
				if (aLong1697 != -1L) {
					model_1 = modelCache.get(aLong1697);
				}
				if (model_1 == null) {
					return null;
				}
			}
		}

		if (model_1 == null) {
			Model[] models = new Model[12];
			int modelCount = 0;
			for (int part = 0; part < 12; part++) {
				int value = appearance[part];
				if ((k1 >= 0) && (part == 3)) {
					value = k1;
				}
				if ((j1 >= 0) && (part == 5)) {
					value = j1;
				}
				if ((value >= 256) && (value < 512)) {
					Model model_3 = IDKType.instances[value - 256].method538();
					if (model_3 != null) {
						models[modelCount++] = model_3;
					}
				}
				if (value >= 512) {
					Model model = ObjType.get(value - 512).getWornModel(gender);
					if (model != null) {
						models[modelCount++] = model;
					}
				}
			}
			model_1 = new Model(modelCount, models);
			for (int j3 = 0; j3 < 5; j3++) {
				if (anIntArray1700[j3] != 0) {
					model_1.recolor(Game.anIntArrayArray1003[j3][0], Game.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if (j3 == 1) {
						model_1.recolor(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j3]]);
					}
				}
			}
			model_1.createLabelReferences();
			model_1.calculateNormals(64, 850, -30, -50, -30, true);
			modelCache.put(l, model_1);
			aLong1697 = l;
		}
		if (aBoolean1699) {
			return model_1;
		}
		Model class30_sub2_sub4_sub6_2 = Model.EMPTY;
		class30_sub2_sub4_sub6_2.set(model_1, SeqFrame.isNull(k) & SeqFrame.isNull(i1));
		if ((k != -1) && (i1 != -1)) {
			class30_sub2_sub4_sub6_2.applySequenceFrames(k, i1, SeqType.instances[super.anInt1526].anIntArray357);
		} else if (k != -1) {
			class30_sub2_sub4_sub6_2.applySequenceFrame(k);
		}
		class30_sub2_sub4_sub6_2.calculateBoundsCylinder();
		class30_sub2_sub4_sub6_2.labelFaces = null;
		class30_sub2_sub4_sub6_2.labelVertices = null;
		return class30_sub2_sub4_sub6_2;
	}

	@Override
	public boolean isVisible() {
		return aBoolean1710;
	}

	public Model method453() {
		if (!aBoolean1710) {
			return null;
		}
		if (aType_1698 != null) {
			return aType_1698.method160();
		}
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = appearance[i];
			if ((j >= 256) && (j < 512) && !IDKType.instances[j - 256].method539()) {
				flag = true;
			}
			if ((j >= 512) && !ObjType.get(j - 512).validateHeadModel(gender)) {
				flag = true;
			}
		}
		if (flag) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6 = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = appearance[l];
			if ((i1 >= 256) && (i1 < 512)) {
				Model model_1 = IDKType.instances[i1 - 256].method540();
				if (model_1 != null) {
					aclass30_sub2_sub4_sub6[k++] = model_1;
				}
			}
			if (i1 >= 512) {
				Model class30_sub2_sub4_sub6_2 = ObjType.get(i1 - 512).getHeadModel(gender);
				if (class30_sub2_sub4_sub6_2 != null) {
					aclass30_sub2_sub4_sub6[k++] = class30_sub2_sub4_sub6_2;
				}
			}
		}
		Model model = new Model(k, aclass30_sub2_sub4_sub6);
		for (int j1 = 0; j1 < 5; j1++) {
			if (anIntArray1700[j1] != 0) {
				model.recolor(Game.anIntArrayArray1003[j1][0], Game.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1) {
					model.recolor(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j1]]);
				}
			}
		}
		return model;
	}

}
