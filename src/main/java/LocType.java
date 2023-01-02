// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class LocType {

    public static final Model[] TMP_MODELS = new Model[4];
    public static boolean lowmem;
    public static Buffer dat;
    public static int[] offsets;
    public static int count;
    public static Game game;
    public static int anInt771;
    /**
     * This is where dynamically generated models go.
     */
    public static LRUMap<Long, Model> dynamicCache = new LRUMap(30);
    public static LocType[] cache;
    /**
     * This is where basic models go.
     */
    public static LRUMap<Long, Model> staticCache = new LRUMap(500);

    public static LocType get(int i) {
        if (i >= count) {
            i = 0;
        }
        for (int j = 0; j < 20; j++) {
            if (cache[j].index == i) {
                return cache[j];
            }
        }
        anInt771 = (anInt771 + 1) % 20;
        LocType type = cache[anInt771];
        dat.position = offsets[i];
        type.index = i;
        type.reset();
        type.method582(dat);
        return type;
    }

    public static void unload() {
        staticCache = null;
        dynamicCache = null;
        offsets = null;
        cache = null;
        dat = null;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("loc.dat"));
        Buffer buffer = new Buffer(archive.read("loc.idx"));
        count = buffer.read16U();
        offsets = new int[count];
        int i = 2;
        for (int j = 0; j < count; j++) {
            offsets[j] = i;
            i += buffer.read16U();
        }
        cache = new LocType[20];
        for (int k = 0; k < 20; k++) {
            cache[k] = new LocType();
        }
    }

    public boolean important;
    public byte lightAmbient;
    public int translateX;
    public String name;
    public int scaleY;
    public byte lightAttenuation;
    public int width;
    public int translateY;
    public int mapfunctionIcon;
    public int[] dstColor;
    public int scaleX;
    public int varp;
    public boolean invert;
    public int index = -1;
    public boolean blocksProjectiles;
    public int mapsceneIcon;
    public int[] overrideTypeIDs;
    public int supportsObj;
    public int length;
    public boolean adjustToTerrain;
    public boolean occludes;
    public boolean decorative;
    public boolean solid;
    public int interactionSideFlags;
    /**
     * Models flagged as <code>dynamic</code> will <b>always</b> copy their models face information by making <code>copyFaces</code>
     * equal <code>true</code>.
     *
     * @see Model#Model(boolean, boolean, Model)
     */
    public boolean dynamic;
    public int scaleZ;
    public int[] modelIDs;
    public int varbit;
    public int decorationPadding;
    public int[] modelKinds;
    public String description;
    public boolean interactable;
    public boolean castShadow;
    public int seqID;
    public int translateZ;
    public int[] srcColor;
    public String[] actions;

    public LocType() {
    }

    public void reset() {
        modelIDs = null;
        modelKinds = null;
        name = null;
        description = null;
        srcColor = null;
        dstColor = null;
        width = 1;
        length = 1;
        solid = true;
        blocksProjectiles = true;
        interactable = false;
        adjustToTerrain = false;
        dynamic = false;
        occludes = false;
        seqID = -1;
        decorationPadding = 16;
        lightAmbient = 0;
        lightAttenuation = 0;
        actions = null;
        mapfunctionIcon = -1;
        mapsceneIcon = -1;
        invert = false;
        castShadow = true;
        scaleX = 128;
        scaleZ = 128;
        scaleY = 128;
        interactionSideFlags = 0;
        translateX = 0;
        translateY = 0;
        translateZ = 0;
        important = false;
        decorative = false;
        supportsObj = -1;
        varbit = -1;
        varp = -1;
        overrideTypeIDs = null;
    }

    public void method574(OnDemand onDemand) {
        if (modelIDs == null) {
            return;
        }
        for (int k : modelIDs) {
            onDemand.method560(k & 0xffff, 0);
        }
    }

    public boolean validate(int kind) {
        if (modelKinds == null) {
            if (modelIDs == null) {
                return true;
            }
            if (kind != 10) {
                return true;
            }
            boolean valid = true;
            for (int modelID : modelIDs) {
                valid &= Model.validate(modelID & 0xffff);
            }
            return valid;
        }
        for (int i = 0; i < modelKinds.length; i++) {
            if (modelKinds[i] == kind) {
                return Model.validate(modelIDs[i] & 0xffff);
            }
        }
        return true;
    }

    public Model getModel(int kind, int rotation, int heightmapSW, int heightmapSE, int heightmapNE, int heightmapNW, int transformID) {
        Model model = getModel(kind, transformID, rotation);

        if (model == null) {
            return null;
        }

        if (adjustToTerrain || dynamic) {
            model = new Model(adjustToTerrain, dynamic, model);
        }

        if (adjustToTerrain) {
            int groundY = (heightmapSW + heightmapSE + heightmapNE + heightmapNW) / 4;
            for (int i = 0; i < model.vertexCount; i++) {
                int x = model.vertexX[i];
                int z = model.vertexZ[i];
                int heightS = heightmapSW + (((heightmapSE - heightmapSW) * (x + 64)) / 128);
                int heightN = heightmapNW + (((heightmapNE - heightmapNW) * (x + 64)) / 128);
                int y = heightS + (((heightN - heightS) * (z + 64)) / 128);
                model.vertexY[i] += y - groundY;
            }
            model.calculateBoundsY();
        }
        return model;
    }

    public boolean method579() {
        if (modelIDs == null) {
            return true;
        }
        boolean flag1 = true;
        for (int j : modelIDs) {
            flag1 &= Model.validate(j & 0xffff);
        }
        return flag1;
    }

    public LocType getOverrideType() {
        int value = -1;

        if (varbit != -1) {
            VarbitType varbit = VarbitType.instances[this.varbit];
            int varp = varbit.varp;
            int low = varbit.lsb;
            int high = varbit.msb;
            int mask = Game.BITMASK[high - low];
            value = (game.variables[varp] >> low) & mask;
        } else if (varp != -1) {
            value = game.variables[varp];
        }

        if ((value < 0) || (value >= overrideTypeIDs.length) || (overrideTypeIDs[value] == -1)) {
            return null;
        } else {
            return get(overrideTypeIDs[value]);
        }
    }

    public Model getModel(int kind, int transformID, int rotation) {
        Model model = null;
        long bitset;

        if (modelKinds == null) {
            if (kind != 10) {
                return null;
            }

            bitset = ((long) index << 6) + rotation + ((long) (transformID + 1) << 32);
            Model cached = dynamicCache.get(bitset);

            if (cached != null) {
                return cached;
            }

            if (modelIDs == null) {
                return null;
            }

            boolean flip = invert ^ (rotation > 3);
            int modelCount = modelIDs.length;

            for (int i = 0; i < modelCount; i++) {
                int modelID = modelIDs[i];

                if (flip) {
                    modelID += 0x10000;
                }

                model = staticCache.get(modelID);
                if (model == null) {
                    model = Model.tryGet(modelID & 0xffff);
                    if (model == null) {
                        return null;
                    }
                    if (flip) {
                        model.rotateY180();
                    }
                    staticCache.put((long) modelID, model);
                }

                if (modelCount > 1) {
                    TMP_MODELS[i] = model;
                }
            }

            if (modelCount > 1) {
                model = new Model(modelCount, TMP_MODELS);
            }
        } else {
            int kindIndex = -1;

            for (int i = 0; i < modelKinds.length; i++) {
                if (modelKinds[i] != kind) {
                    continue;
                }
                kindIndex = i;
                break;
            }

            if (kindIndex == -1) {
                return null;
            }

            bitset = ((long) this.index << 6) + ((long) kindIndex << 3) + rotation + ((long) (transformID + 1) << 32);

            Model cached = dynamicCache.get(bitset);

            if (cached != null) {
                return cached;
            }

            int modelID = modelIDs[kindIndex];
            boolean flip = invert ^ (rotation > 3);

            if (flip) {
                modelID += 0x10000;
            }

            model = staticCache.get(modelID);

            if (model == null) {
                model = Model.tryGet(modelID & 0xffff);

                if (model == null) {
                    return null;
                }

                if (flip) {
                    model.rotateY180();
                }

                staticCache.put((long) modelID, model);
            }
        }

        boolean scaled = (scaleX != 128) || (scaleZ != 128) || (scaleY != 128);
        boolean translated = (translateX != 0) || (translateY != 0) || (translateZ != 0);

        Model modified = new Model(srcColor == null, SeqTransform.isNull(transformID), (rotation == 0) && (transformID == -1) && !scaled && !translated, model);

        if (transformID != -1) {
            modified.createLabelReferences();
            modified.applyTransform(transformID);
            modified.labelFaces = null;
            modified.labelVertices = null;
        }

        while (rotation-- > 0) {
            modified.rotateY90();
        }

        if (srcColor != null) {
            for (int k2 = 0; k2 < srcColor.length; k2++) {
                modified.recolor(srcColor[k2], dstColor[k2]);
            }
        }

        if (scaled) {
            modified.scale(scaleX, scaleY, scaleZ);
        }

        if (translated) {
            modified.translate(translateX, translateY, translateZ);
        }

        modified.calculateNormals(64 + lightAmbient, 768 + (lightAttenuation * 5), -50, -10, -50, !dynamic);

        if (supportsObj == 1) {
            modified.objRaise = modified.minY;
        }

        dynamicCache.put(bitset, modified);
        return modified;
    }

    public void method582(Buffer buffer) {
        int i = -1;
        label0:
        do {
            int j;
            do {
                j = buffer.read8U();
                if (j == 0) {
                    break label0;
                }
                if (j == 1) {
                    int k = buffer.read8U();
                    if (k > 0) {
                        if ((modelIDs == null) || lowmem) {
                            modelKinds = new int[k];
                            modelIDs = new int[k];
                            for (int k1 = 0; k1 < k; k1++) {
                                modelIDs[k1] = buffer.read16U();
                                modelKinds[k1] = buffer.read8U();
                            }
                        } else {
                            buffer.position += k * 3;
                        }
                    }
                } else if (j == 2) {
                    name = buffer.readString();
                } else if (j == 3) {
                    description = buffer.readString();
                } else if (j == 5) {
                    int l = buffer.read8U();
                    if (l > 0) {
                        if ((modelIDs == null) || lowmem) {
                            modelKinds = null;
                            modelIDs = new int[l];
                            for (int l1 = 0; l1 < l; l1++) {
                                modelIDs[l1] = buffer.read16U();
                            }
                        } else {
                            buffer.position += l * 2;
                        }
                    }
                } else if (j == 14) {
                    width = buffer.read8U();
                } else if (j == 15) {
                    length = buffer.read8U();
                } else if (j == 17) {
                    solid = false;
                } else if (j == 18) {
                    blocksProjectiles = false;
                } else if (j == 19) {
                    i = buffer.read8U();
                    if (i == 1) {
                        interactable = true;
                    }
                } else if (j == 21) {
                    adjustToTerrain = true;
                } else if (j == 22) {
                    dynamic = true;
                } else if (j == 23) {
                    occludes = true;
                } else if (j == 24) {
                    seqID = buffer.read16U();
                    if (seqID == 65535) {
                        seqID = -1;
                    }
                } else if (j == 28) {
                    decorationPadding = buffer.read8U();
                } else if (j == 29) {
                    lightAmbient = buffer.read8();
                } else if (j == 39) {
                    lightAttenuation = buffer.read8();
                } else if ((j >= 30) && (j < 39)) {
                    if (actions == null) {
                        actions = new String[5];
                    }
                    actions[j - 30] = buffer.readString();
                    if (actions[j - 30].equalsIgnoreCase("hidden")) {
                        actions[j - 30] = null;
                    }
                } else if (j == 40) {
                    int i1 = buffer.read8U();
                    srcColor = new int[i1];
                    dstColor = new int[i1];
                    for (int i2 = 0; i2 < i1; i2++) {
                        srcColor[i2] = buffer.read16U();
                        dstColor[i2] = buffer.read16U();
                    }
                } else if (j == 60) {
                    mapfunctionIcon = buffer.read16U();
                } else if (j == 62) {
                    invert = true;
                } else if (j == 64) {
                    castShadow = false;
                } else if (j == 65) {
                    scaleX = buffer.read16U();
                } else if (j == 66) {
                    scaleZ = buffer.read16U();
                } else if (j == 67) {
                    scaleY = buffer.read16U();
                } else if (j == 68) {
                    mapsceneIcon = buffer.read16U();
                } else if (j == 69) {
                    interactionSideFlags = buffer.read8U();
                } else if (j == 70) {
                    translateX = buffer.read16();
                } else if (j == 71) {
                    translateY = buffer.read16();
                } else if (j == 72) {
                    translateZ = buffer.read16();
                } else if (j == 73) {
                    important = true;
                } else if (j == 74) {
                    decorative = true;
                } else {
                    if (j != 75) {
                        continue;
                    }
                    supportsObj = buffer.read8U();
                }
                continue label0;
            } while (j != 77);
            varbit = buffer.read16U();
            if (varbit == 65535) {
                varbit = -1;
            }
            varp = buffer.read16U();
            if (varp == 65535) {
                varp = -1;
            }
            int j1 = buffer.read8U();
            overrideTypeIDs = new int[j1 + 1];
            for (int j2 = 0; j2 <= j1; j2++) {
                overrideTypeIDs[j2] = buffer.read16U();
                if (overrideTypeIDs[j2] == 65535) {
                    overrideTypeIDs[j2] = -1;
                }
            }
        } while (true);
        if (i == -1) {
            interactable = (modelIDs != null) && ((modelKinds == null) || (modelKinds[0] == 10));
            if (actions != null) {
                interactable = true;
            }
        }
        if (decorative) {
            solid = false;
            blocksProjectiles = false;
        }
        if (supportsObj == -1) {
            supportsObj = solid ? 1 : 0;
        }
    }

}
