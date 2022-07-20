// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

public class PlayerEntity extends PathingEntity {

	public static LRUMap<Long, Model> modelCache = new LRUMap<>(260);
	public final int[] colors = new int[5];
	public final int[] appearances = new int[12];
	public long modelUID = -1L;
	public NPCType npcType;
	public boolean lowmem = false;
	public int team;
	public int gender;
	public String name;
	public int combatLevel;
	public int headicons;
	public int locStartCycle;
	public int locStopCycle;
	public int y;
	public boolean visible = false;
	public int locOffsetX;
	public int locOffsetY;
	public int locOffsetZ;
	public Model locModel;
	public long appearanceHashcode;
	public int minSceneTileX;
	public int minSceneTileZ;
	public int maxSceneTileX;
	public int maxSceneTileZ;
	public int skillLevel;

	public PlayerEntity() {
	}

	@Override
	public Model getModel() {
		if (!visible) {
			return null;
		}

		Model model = getSequencedModel();

		if (model == null) {
			return null;
		}

		super.height = model.minY;
		model.pickable = true;

		if (lowmem) {
			return model;
		}

		if ((super.spotanim != -1) && (super.spotanimFrame != -1)) {
			SpotAnimType spot = SpotAnimType.instances[super.spotanim];
			Model spotModel1 = spot.getModel();

			if (spotModel1 != null) {
				Model spotModel2 = new Model(true, SeqFrame.isNull(super.spotanimFrame), false, spotModel1);
				spotModel2.translate(0, -super.spotanimY, 0);
				spotModel2.createLabelReferences();
				spotModel2.applySequenceFrame(spot.seq.primaryFrames[super.spotanimFrame]);
				spotModel2.labelFaces = null;
				spotModel2.labelVertices = null;
				if ((spot.scaleXY != 128) || (spot.scaleZ != 128)) {
					spotModel2.scale(spot.scaleXY, spot.scaleXY, spot.scaleZ);
				}
				spotModel2.calculateNormals(64 + spot.lightAmbient, 850 + spot.lightAttenuation, -30, -50, -30, true);
				model = new Model(2, -819, new Model[]{model, spotModel2});
			}
		}

		if (this.locModel != null) {
			if (Game.loopCycle >= locStopCycle) {
				this.locModel = null;
			}

			if ((Game.loopCycle >= locStartCycle) && (Game.loopCycle < locStopCycle)) {
				Model model1 = this.locModel;
				model1.translate(locOffsetX - super.x, locOffsetY - y, locOffsetZ - super.z);

				if (super.dstYaw == 512) {
					model1.rotateY90();
					model1.rotateY90();
					model1.rotateY90();
				} else if (super.dstYaw == 1024) {
					model1.rotateY90();
					model1.rotateY90();
				} else if (super.dstYaw == 1536) {
					model1.rotateY90();
				}

				model = new Model(2, -819, new Model[]{model, model1});

				if (super.dstYaw == 512) {
					model1.rotateY90();
				} else if (super.dstYaw == 1024) {
					model1.rotateY90();
					model1.rotateY90();
				} else if (super.dstYaw == 1536) {
					model1.rotateY90();
					model1.rotateY90();
					model1.rotateY90();
				}

				model1.translate(super.x - locOffsetX, y - locOffsetY, super.z - locOffsetZ);
			}
		}
		
		model.pickable = true;
		return model;
	}

	public void method451(Buffer buffer) {
		buffer.position = 0;
		gender = buffer.get1U();
		headicons = buffer.get1U();
		npcType = null;
		team = 0;

		for (int part = 0; part < 12; part++) {
			int msb = buffer.get1U();

			if (msb == 0) {
				appearances[part] = 0;
				continue;
			}

			int lsb = buffer.get1U();
			appearances[part] = (msb << 8) + lsb;

			if ((part == 0) && (appearances[0] == 65535)) {
				npcType = NPCType.get(buffer.get2U());
				break;
			}

			if ((appearances[part] >= 512) && ((appearances[part] - 512) < ObjType.count)) {
				int team = ObjType.get(appearances[part] - 512).team;

				if (team != 0) {
					this.team = team;
				}
			}
		}

		for (int part = 0; part < 5; part++) {
			int color = buffer.get1U();

			if ((color < 0) || (color >= Game.designPartColor[part].length)) {
				color = 0;
			}

			colors[part] = color;
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
		visible = true;

		appearanceHashcode = 0L;

		for (int part = 0; part < 12; part++) {
			appearanceHashcode <<= 4;
			if (appearances[part] >= 256) {
				appearanceHashcode += appearances[part] - 256;
			}
		}

		if (appearances[0] >= 256) {
			appearanceHashcode += (appearances[0] - 256) >> 4;
		}

		if (appearances[1] >= 256) {
			appearanceHashcode += (appearances[1] - 256) >> 8;
		}

		for (int i2 = 0; i2 < 5; i2++) {
			appearanceHashcode <<= 3;
			appearanceHashcode += colors[i2];
		}

		appearanceHashcode <<= 1;
		appearanceHashcode += gender;
	}

	public Model getSequencedModel() {
		if (npcType != null) {
			int frame = -1;
			if ((super.seqId1 >= 0) && (super.anInt1529 == 0)) {
				frame = SeqType.instances[super.seqId1].primaryFrames[super.seqFrame1];
			} else if (super.seqId2 >= 0) {
				frame = SeqType.instances[super.seqId2].primaryFrames[super.seqFrame2];
			}
			return npcType.getSequencedModel(-1, frame, null);
		}

		long hashCode = this.appearanceHashcode;
		int frame1 = -1;
		int frame2 = -1;
		int rightHandValue = -1;
		int leftHandValue = -1;

		if ((super.seqId1 >= 0) && (super.anInt1529 == 0)) {
			SeqType type = SeqType.instances[super.seqId1];
			frame1 = type.primaryFrames[super.seqFrame1];

			if ((super.seqId2 >= 0) && (super.seqId2 != super.seqStand)) {
				frame2 = SeqType.instances[super.seqId2].primaryFrames[super.seqFrame2];
			}

			if (type.rightHandOverride >= 0) {
				rightHandValue = type.rightHandOverride;
				hashCode += ((long) rightHandValue - appearances[5]) << 8;
			}

			if (type.leftHandOverride >= 0) {
				leftHandValue = type.leftHandOverride;
				hashCode += ((long) leftHandValue - appearances[3]) << 16;
			}
		} else if (super.seqId2 >= 0) {
			frame1 = SeqType.instances[super.seqId2].primaryFrames[super.seqFrame2];
		}

		Model model = modelCache.get(hashCode);

		if (model == null) {
			boolean invalid = false;

			for (int part = 0; part < 12; part++) {
				int value = appearances[part];

				if ((leftHandValue >= 0) && (part == 3)) {
					value = leftHandValue;
				}

				if ((rightHandValue >= 0) && (part == 5)) {
					value = rightHandValue;
				}

				if ((value >= 256) && (value < 512) && !IDKType.instances[value - 256].validateModel()) {
					invalid = true;
				}

				if ((value >= 512) && !ObjType.get(value - 512).validateWornModel(gender)) {
					invalid = true;
				}
			}

			if (invalid) {
				if (modelUID != -1L) {
					model = modelCache.get(modelUID);
				}
				if (model == null) {
					return null;
				}
			}
		}

		if (model == null) {
			Model[] models = new Model[12];
			int modelCount = 0;

			for (int part = 0; part < 12; part++) {
				int value = appearances[part];

				if ((leftHandValue >= 0) && (part == 3)) {
					value = leftHandValue;
				}

				if ((rightHandValue >= 0) && (part == 5)) {
					value = rightHandValue;
				}

				if ((value >= 256) && (value < 512)) {
					Model kitModel = IDKType.instances[value - 256].getModel();
					if (kitModel != null) {
						models[modelCount++] = kitModel;
					}
				}

				if (value >= 512) {
					Model objModel = ObjType.get(value - 512).getWornModel(gender);

					if (objModel != null) {
						models[modelCount++] = objModel;
					}
				}
			}

			model = new Model(modelCount, models);
			for (int part = 0; part < 5; part++) {
				if (colors[part] != 0) {
					model.recolor(Game.designPartColor[part][0], Game.designPartColor[part][colors[part]]);
					if (part == 1) {
						model.recolor(Game.designHairColor[0], Game.designHairColor[colors[part]]);
					}
				}
			}
			model.createLabelReferences();
			model.calculateNormals(64, 850, -30, -50, -30, true);
			modelCache.put(hashCode, model);
			modelUID = hashCode;
		}

		if (lowmem) {
			return model;
		}

		Model animated = Model.EMPTY;
		animated.set(model, SeqFrame.isNull(frame1) & SeqFrame.isNull(frame2));

		if ((frame1 != -1) && (frame2 != -1)) {
			animated.applySequenceFrames(frame1, frame2, SeqType.instances[super.seqId1].anIntArray357);
		} else if (frame1 != -1) {
			animated.applySequenceFrame(frame1);
		}

		animated.calculateBoundsCylinder();
		animated.labelFaces = null;
		animated.labelVertices = null;
		return animated;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public Model getUnlitHeadModel() {
		if (!visible) {
			return null;
		}

		if (npcType != null) {
			return npcType.getUnlitHeadModel();
		}

		boolean invalid = false;

		for (int part = 0; part < 12; part++) {
			int value = appearances[part];

			if ((value >= 256) && (value < 512) && !IDKType.instances[value - 256].validateHeadModel()) {
				invalid = true;
			}

			if ((value >= 512) && !ObjType.get(value - 512).validateHeadModel(gender)) {
				invalid = true;
			}
		}

		if (invalid) {
			return null;
		}

		Model[] models = new Model[12];
		int modelCount = 0;
		for (int part = 0; part < 12; part++) {
			int value = appearances[part];

			if ((value >= 256) && (value < 512)) {
				Model model = IDKType.instances[value - 256].method540();
				if (model != null) {
					models[modelCount++] = model;
				}
			}
			if (value >= 512) {
				Model model = ObjType.get(value - 512).getHeadModel(gender);
				if (model != null) {
					models[modelCount++] = model;
				}
			}
		}
		Model model = new Model(modelCount, models);
		for (int j1 = 0; j1 < 5; j1++) {
			if (colors[j1] != 0) {
				model.recolor(Game.designPartColor[j1][0], Game.designPartColor[j1][colors[j1]]);
				if (j1 == 1) {
					model.recolor(Game.designHairColor[0], Game.designHairColor[colors[j1]]);
				}
			}
		}
		return model;
	}

}
