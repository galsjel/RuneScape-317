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
        count = buffer.read16U();
        offsets = new int[count];

        int offset = 2;
        for (int i = 0; i < count; i++) {
            offsets[i] = offset;
            offset += buffer.read16U();
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

    public int seqTurnRightID = -1;
    public int varbitID = -1;
    public int seqTurnAroundID = -1;
    public int varpID = -1;
    public int level = -1;
    public String name;
    public String[] options;
    public int seqWalkID = -1;
    public byte size = 1;
    public int[] colorDst;
    public int[] headModelIDs;
    public int headicon = -1;
    public int[] colorSrc;
    public int seqStandID = -1;
    public long uid = -1L;
    public int turnSpeed = 32;
    public int seqTurnLeftID = -1;
    public boolean interactable = true;
    public int lightAmbient;
    public int scaleZ = 128;
    public boolean showOnMinimap = true;
    public int[] overrides;
    public byte[] desc;
    public int scaleXY = 128;
    public int lightAttenuation;
    /**
     * Causes the npc to render on top of other npcs.
     */
    public boolean important = false;
    public int[] modelIDs;

    public NPCType() {
    }

    public Model getHeadModel() {
        if (overrides != null) {
            NPCType type = getOverrideType();
            if (type == null) {
                return null;
            } else {
                return type.getHeadModel();
            }
        }

        if (headModelIDs == null) {
            return null;
        }

        boolean loaded = false;

        for (int value : headModelIDs) {
            if (!Model.validate(value)) {
                loaded = true;
            }
        }

        if (loaded) {
            return null;
        }

        Model[] models = new Model[headModelIDs.length];

        for (int i = 0; i < headModelIDs.length; i++) {
            models[i] = Model.tryGet(headModelIDs[i]);
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

        if (varbitID != -1) {
            VarbitType vb = VarbitType.instances[this.varbitID];
            int varp = vb.varp;
            int lsb = vb.lsb;
            int msb = vb.msb;
            int mask = Game.BITMASK[msb - lsb];
            value = (game.varps[varp] >> lsb) & mask;
        } else if (varpID != -1) {
            value = game.varps[varpID];
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

            for (int value : modelIDs) {
                if (!Model.validate(value)) {
                    invalid = true;
                }
            }

            if (invalid) {
                return null;
            }

            Model[] models = new Model[modelIDs.length];

            for (int i = 0; i < modelIDs.length; i++) {
                models[i] = Model.tryGet(modelIDs[i]);
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
            model.calculateNormals(64 + lightAmbient, 850 + lightAttenuation, -30, -50, -30, true);
            modelCache.put(uid, model);
        }

        Model tmp = Model.EMPTY;
        tmp.set(model, SeqTransform.isNull(primaryTransformID) & SeqTransform.isNull(secondaryTransformID));

        if ((primaryTransformID != -1) && (secondaryTransformID != -1)) {
            tmp.applyTransforms(primaryTransformID, secondaryTransformID, seqMask);
        } else if (primaryTransformID != -1) {
            tmp.applyTransform(primaryTransformID);
        }

        if ((scaleXY != 128) || (scaleZ != 128)) {
            tmp.scale(scaleXY, scaleXY, scaleZ);
        }

        tmp.calculateBoundsCylinder();
        tmp.labelFaces = null;
        tmp.labelVertices = null;

        if (size == 1) {
            tmp.pickable = true;
        }

        return tmp;
    }

    public void read(Buffer buffer) {
        while (true) {
            int code = buffer.read8U();

            if (code == 0) {
                return;
            } else if (code == 1) {
                int modelCount = buffer.read8U();
                modelIDs = new int[modelCount];
                for (int i = 0; i < modelCount; i++) {
                    modelIDs[i] = buffer.read16U();
                }
            } else if (code == 2) {
                name = buffer.readString();
            } else if (code == 3) {
                desc = buffer.readStringRaw();
            } else if (code == 12) {
                size = buffer.read();
            } else if (code == 13) {
                seqStandID = buffer.read16U();
            } else if (code == 14) {
                seqWalkID = buffer.read16U();
            } else if (code == 17) {
                seqWalkID = buffer.read16U();
                seqTurnAroundID = buffer.read16U();
                seqTurnLeftID = buffer.read16U();
                seqTurnRightID = buffer.read16U();
            } else if ((code >= 30) && (code < 40)) {
                if (options == null) {
                    options = new String[5];
                }

                options[code - 30] = buffer.readString();

                if (options[code - 30].equalsIgnoreCase("hidden")) {
                    options[code - 30] = null;
                }
            } else if (code == 40) {
                int count = buffer.read8U();
                colorSrc = new int[count];
                colorDst = new int[count];

                for (int i = 0; i < count; i++) {
                    colorSrc[i] = buffer.read16U();
                    colorDst[i] = buffer.read16U();
                }
            } else if (code == 60) {
                int count = buffer.read8U();
                headModelIDs = new int[count];
                for (int l1 = 0; l1 < count; l1++) {
                    headModelIDs[l1] = buffer.read16U();
                }
            } else if ((code == 90) || (code == 91) || (code == 92)) {
                buffer.read16U();
            } else if (code == 93) {
                showOnMinimap = false;
            } else if (code == 95) {
                level = buffer.read16U();
            } else if (code == 97) {
                scaleXY = buffer.read16U();
            } else if (code == 98) {
                scaleZ = buffer.read16U();
            } else if (code == 99) {
                important = true;
            } else if (code == 100) {
                lightAmbient = buffer.read();
            } else if (code == 101) {
                lightAttenuation = buffer.read() * 5;
            } else if (code == 102) {
                headicon = buffer.read16U();
            } else if (code == 103) {
                turnSpeed = buffer.read16U();
            } else if (code == 106) {
                varbitID = buffer.read16U();

                if (varbitID == 65535) {
                    varbitID = -1;
                }

                varpID = buffer.read16U();

                if (varpID == 65535) {
                    varpID = -1;
                }

                int overrideCount = buffer.read8U();
                overrides = new int[overrideCount + 1];

                for (int i = 0; i <= overrideCount; i++) {
                    overrides[i] = buffer.read16U();
                    
                    if (overrides[i] == 65535) {
                        overrides[i] = -1;
                    }
                }
            } else if (code == 107) {
                interactable = false;
            }
        }
    }

}
