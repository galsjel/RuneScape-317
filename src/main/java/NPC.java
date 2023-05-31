// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class NPC {

    private static int cachePosition;
    private static Buffer dat;
    public static int[] offsets;
    private static NPC[] cache;
    public static Game game;
    public static int count;
    public static LRUMap<Long, Model> modelCache = new LRUMap<>(30);

    public static NPC get(int id) {
        for (int j = 0; j < 20; j++) {
            if (cache[j].uid == (long) id) {
                return cache[j];
            }
        }
        cachePosition = (cachePosition + 1) % 20;
        NPC type = cache[cachePosition] = new NPC();
        dat.position = offsets[id];
        type.uid = id;
        type.read(dat);
        return type;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("npc.dat"));
        Buffer buffer = new Buffer(archive.read("npc.idx"));
        count = buffer.readU16();
        offsets = new int[count];

        int offset = 2;
        for (int i = 0; i < count; i++) {
            offsets[i] = offset;
            offset += buffer.readU16();
        }

        cache = new NPC[20];

        for (int k = 0; k < 20; k++) {
            cache[k] = new NPC();
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
    public int scaleY = 128;
    public boolean showOnMinimap = true;
    public int[] overrides;
    public String examine;
    public int scaleXZ = 128;
    public int lightAttenuation;
    /**
     * Causes the npc to render on top of other npcs.
     */
    public boolean important = false;
    public int[] modelIDs;

    public NPC() {
    }

    public Model getHeadModel() {
        if (overrides != null) {
            NPC type = getOverrideType();
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
            if (!Model.loaded(value)) {
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
            model = Model.join_prebuilt(models.length, models);
        }

        if (colorSrc != null) {
            for (int i = 0; i < colorSrc.length; i++) {
                model.recolor(colorSrc[i], colorDst[i]);
            }
        }
        return model;
    }

    public NPC getOverrideType() {
        int value = -1;

        if (varbitID != -1) {
            Varbit vb = Varbit.instances[this.varbitID];
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
            NPC override = getOverrideType();
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
                if (!Model.loaded(value)) {
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
                model = Model.join_prebuilt(models.length, models);
            }

            if (colorSrc != null) {
                for (int i = 0; i < colorSrc.length; i++) {
                    model.recolor(colorSrc[i], colorDst[i]);
                }
            }

            model.build_labels();
            model.build(64 + lightAmbient, 850 + lightAttenuation, -30, -50, -30, true);
            modelCache.put(uid, model);
        }

        Model tmp = Model.EMPTY;
        tmp.set(model, AnimationTransform.isNull(primaryTransformID) & AnimationTransform.isNull(secondaryTransformID));

        if ((primaryTransformID != -1) && (secondaryTransformID != -1)) {
            tmp.transform2(primaryTransformID, secondaryTransformID, seqMask);
        } else if (primaryTransformID != -1) {
            tmp.transform(primaryTransformID);
        }

        if ((scaleXZ != 128) || (scaleY != 128)) {
            tmp.scale(scaleXZ, scaleY, scaleXZ);
        }

        tmp.calculateBoundsCylinder();
        tmp.labelFaces = null;
        tmp.labelVertices = null;

        if (size == 1) {
            tmp.pickable = true;
        }

        return tmp;
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();

            if (code == 0) {
                return;
            } else if (code == 1) {
                int modelCount = in.readU8();
                modelIDs = new int[modelCount];
                for (int i = 0; i < modelCount; i++) {
                    modelIDs[i] = in.readU16();
                }
            } else if (code == 2) {
                name = in.readString();
            } else if (code == 3) {
                examine = in.readString();
            } else if (code == 12) {
                size = in.read8();
            } else if (code == 13) {
                seqStandID = in.readU16();
            } else if (code == 14) {
                seqWalkID = in.readU16();
            } else if (code == 17) {
                seqWalkID = in.readU16();
                seqTurnAroundID = in.readU16();
                seqTurnLeftID = in.readU16();
                seqTurnRightID = in.readU16();
            } else if ((code >= 30) && (code < 40)) {
                if (options == null) {
                    options = new String[5];
                }

                options[code - 30] = in.readString();

                if (options[code - 30].equalsIgnoreCase("hidden")) {
                    options[code - 30] = null;
                }
            } else if (code == 40) {
                int count = in.readU8();
                colorSrc = new int[count];
                colorDst = new int[count];

                for (int i = 0; i < count; i++) {
                    colorSrc[i] = in.readU16();
                    colorDst[i] = in.readU16();
                }
            } else if (code == 60) {
                int count = in.readU8();
                headModelIDs = new int[count];
                for (int l1 = 0; l1 < count; l1++) {
                    headModelIDs[l1] = in.readU16();
                }
            } else if ((code == 90) || (code == 91) || (code == 92)) {
                in.readU16();
            } else if (code == 93) {
                showOnMinimap = false;
            } else if (code == 95) {
                level = in.readU16();
            } else if (code == 97) {
                scaleXZ = in.readU16();
            } else if (code == 98) {
                scaleY = in.readU16();
            } else if (code == 99) {
                important = true;
            } else if (code == 100) {
                lightAmbient = in.read8();
            } else if (code == 101) {
                lightAttenuation = in.read8() * 5;
            } else if (code == 102) {
                headicon = in.readU16();
            } else if (code == 103) {
                turnSpeed = in.readU16();
            } else if (code == 106) {
                varbitID = in.readU16();

                if (varbitID == 65535) {
                    varbitID = -1;
                }

                varpID = in.readU16();

                if (varpID == 65535) {
                    varpID = -1;
                }

                int overrideCount = in.readU8();
                overrides = new int[overrideCount + 1];

                for (int i = 0; i <= overrideCount; i++) {
                    overrides[i] = in.readU16();

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
