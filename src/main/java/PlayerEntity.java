// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

public class PlayerEntity extends PathingEntity {

    public static LRUMap<Long, Model> modelCache = new LRUMap<>(260);
    public final int[] colors = new int[5];
    public final int[] appearances = new int[12];
    public long modelUID = -1L;
    public NPCType transmogrify;
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

        if ((super.spotanimID != -1) && (super.spotanimFrame != -1)) {
            SpotAnimType spot = SpotAnimType.instances[super.spotanimID];
            Model spotModel1 = spot.getModel();

            if (spotModel1 != null) {
                Model spotModel2 = new Model(true, SeqTransform.isNull(super.spotanimFrame), false, spotModel1);
                spotModel2.translate(0, -super.spotanimOffset, 0);
                spotModel2.createLabelReferences();
                spotModel2.applyTransform(spot.seq.transformIDs[super.spotanimFrame]);
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
                Model locModel = this.locModel;
                locModel.translate(locOffsetX - super.x, locOffsetY - y, locOffsetZ - super.z);

                if (super.dstYaw == 512) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1024) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1536) {
                    locModel.rotateY90();
                }

                model = new Model(2, -819, new Model[]{model, locModel});

                if (super.dstYaw == 512) {
                    locModel.rotateY90();
                } else if (super.dstYaw == 1024) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1536) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                    locModel.rotateY90();
                }

                locModel.translate(super.x - locOffsetX, y - locOffsetY, super.z - locOffsetZ);
            }
        }

        model.pickable = true;
        return model;
    }

    public void read(Buffer buffer) {
        buffer.position = 0;
        gender = buffer.read8U();
        headicons = buffer.read8U();
        transmogrify = null;
        team = 0;

        for (int part = 0; part < 12; part++) {
            int msb = buffer.read8U();

            if (msb == 0) {
                appearances[part] = 0;
                continue;
            }

            int lsb = buffer.read8U();
            appearances[part] = (msb << 8) + lsb;

            if ((part == 0) && (appearances[0] == 65535)) {
                transmogrify = NPCType.get(buffer.read16U());
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
            int color = buffer.read8U();

            if ((color < 0) || (color >= Game.designPartColor[part].length)) {
                color = 0;
            }

            colors[part] = color;
        }

        super.seqStandID = buffer.read16U();
        if (super.seqStandID == 65535) {
            super.seqStandID = -1;
        }
        super.seqTurnID = buffer.read16U();
        if (super.seqTurnID == 65535) {
            super.seqTurnID = -1;
        }
        super.seqWalkID = buffer.read16U();
        if (super.seqWalkID == 65535) {
            super.seqWalkID = -1;
        }
        super.seqTurnAroundID = buffer.read16U();
        if (super.seqTurnAroundID == 65535) {
            super.seqTurnAroundID = -1;
        }
        super.seqTurnLeftID = buffer.read16U();
        if (super.seqTurnLeftID == 65535) {
            super.seqTurnLeftID = -1;
        }
        super.seqTurnRightID = buffer.read16U();
        if (super.seqTurnRightID == 65535) {
            super.seqTurnRightID = -1;
        }
        super.seqRunID = buffer.read16U();
        if (super.seqRunID == 65535) {
            super.seqRunID = -1;
        }
        name = StringUtil.formatName(StringUtil.fromBase37(buffer.read64()));
        combatLevel = buffer.read8U();
        skillLevel = buffer.read16U();
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

        for (int part = 0; part < 5; part++) {
            appearanceHashcode <<= 3;
            appearanceHashcode += colors[part];
        }

        appearanceHashcode <<= 1;
        appearanceHashcode += gender;
    }

    public Model getSequencedModel() {
        if (transmogrify != null) {
            int transformID = -1;
            if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
                transformID = SeqType.instances[super.primarySeqID].transformIDs[super.primarySeqFrame];
            } else if (super.secondarySeqID >= 0) {
                transformID = SeqType.instances[super.secondarySeqID].transformIDs[super.secondarySeqFrame];
            }
            return transmogrify.getSequencedModel(-1, transformID, null);
        }

        long hashCode = this.appearanceHashcode;
        int primaryTransformID = -1;
        int secondaryTransformID = -1;
        int rightHandValue = -1;
        int leftHandValue = -1;

        if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
            SeqType type = SeqType.instances[super.primarySeqID];
            primaryTransformID = type.transformIDs[super.primarySeqFrame];

            if ((super.secondarySeqID >= 0) && (super.secondarySeqID != super.seqStandID)) {
                secondaryTransformID = SeqType.instances[super.secondarySeqID].transformIDs[super.secondarySeqFrame];
            }

            if (type.rightHandOverride >= 0) {
                rightHandValue = type.rightHandOverride;
                hashCode += ((long) rightHandValue - appearances[5]) << 8;
            }

            if (type.leftHandOverride >= 0) {
                leftHandValue = type.leftHandOverride;
                hashCode += ((long) leftHandValue - appearances[3]) << 16;
            }
        } else if (super.secondarySeqID >= 0) {
            primaryTransformID = SeqType.instances[super.secondarySeqID].transformIDs[super.secondarySeqFrame];
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

                if ((value >= 256) && (value < 512) && !IdkType.instances[value - 256].validateModel()) {
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
                    Model kitModel = IdkType.instances[value - 256].getModel();
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

        Model tmp = Model.EMPTY;
        tmp.set(model, SeqTransform.isNull(primaryTransformID) & SeqTransform.isNull(secondaryTransformID));

        if ((primaryTransformID != -1) && (secondaryTransformID != -1)) {
            tmp.applyTransforms(primaryTransformID, secondaryTransformID, SeqType.instances[super.primarySeqID].mask);
        } else if (primaryTransformID != -1) {
            tmp.applyTransform(primaryTransformID);
        }

        tmp.calculateBoundsCylinder();
        tmp.labelFaces = null;
        tmp.labelVertices = null;
        return tmp;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public Model getHeadModel() {
        if (!visible) {
            return null;
        }

        if (transmogrify != null) {
            return transmogrify.getHeadModel();
        }

        boolean invalid = false;

        for (int part = 0; part < 12; part++) {
            int value = appearances[part];

            if ((value >= 256) && (value < 512) && !IdkType.instances[value - 256].validateHeadModel()) {
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
                Model model = IdkType.instances[value - 256].getHeadModel();
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

        for (int part = 0; part < 5; part++) {
            if (colors[part] != 0) {
                model.recolor(Game.designPartColor[part][0], Game.designPartColor[part][colors[part]]);
                if (part == 1) {
                    model.recolor(Game.designHairColor[0], Game.designHairColor[colors[part]]);
                }
            }
        }

        return model;
    }

}
