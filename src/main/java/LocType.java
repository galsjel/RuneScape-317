// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class LocType {

    public static final int TYPE_WALL_STRAIGHT = 0;
    public static final int TYPE_WALL_CORNER_DIAGONAL = 1;
    public static final int TYPE_WALL_L = 2;
    public static final int TYPE_WALL_SQUARE_CORNER = 3;
    public static final int TYPE_WALLDECOR_STRAIGHT = 4;
    public static final int TYPE_WALLDECOR_STRAIGHT_OFFSET = 5;
    public static final int TYPE_WALLDECOR_DIAGONAL_NOOFFSET = 6;
    public static final int TYPE_WALLDECOR_DIAGONAL_OFFSET = 7;
    public static final int TYPE_WALLDECOR_DIAGONAL_BOTH = 8;
    public static final int TYPE_WALL_DIAGONAL = 9;
    public static final int TYPE_CENTREPIECE = 10;
    public static final int TYPE_CENTREPIECE_DIAGONAL = 11;
    public static final int TYPE_ROOF_STRAIGHT = 12;
    public static final int TYPE_ROOF_DIAGONAL = 13;
    public static final int TYPE_ROOF_DIAGONAL_WITH_ROOFEDGE = 14;
    public static final int TYPE_ROOF_L_CONCAVE = 15;
    public static final int TYPE_ROOF_L_CONVEX = 16;
    public static final int TYPE_ROOF_FLAT = 17;
    public static final int TYPE_ROOFEDGE_STRAIGHT = 18;
    public static final int TYPE_ROOFEDGE_DIAGONALCORNER = 19;
    public static final int TYPE_ROOFEDGE_L = 20;
    public static final int TYPE_ROOFEDGE_SQUARECORNER = 21;
    public static final int TYPE_GROUND_DECOR = 22;

    public static final Model[] TMP_MODELS = new Model[4];
    public static boolean lowmem;
    public static Buffer dat;
    public static int[] offsets;
    public static int count;
    public static Game game;
    public static int cachePos;
    /**
     * This is where dynamically generated models go.
     */
    public static LRUMap<Long, Model> modelCacheDynamic = new LRUMap<>(30);
    public static LocType[] cache;
    /**
     * This is where basic models go.
     */
    public static LRUMap<Long, Model> modelCacheStatic = new LRUMap<>(500);

    public static LocType get(int locID) {
        if (locID >= count) {
            locID = 0;
        }
        for (int i = 0; i < 20; i++) {
            if (cache[i].index == locID) {
                return cache[i];
            }
        }
        cachePos = (cachePos + 1) % 20;
        LocType type = cache[cachePos];
        dat.position = offsets[locID];
        type.index = locID;
        type.reset();
        type.read(dat);
        return type;
    }

    public static void unload() {
        modelCacheStatic = null;
        modelCacheDynamic = null;
        offsets = null;
        cache = null;
        dat = null;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("loc.dat"));
        Buffer buffer = new Buffer(archive.read("loc.idx"));
        count = buffer.readU16();
        offsets = new int[count];
        int offset = 2;
        for (int j = 0; j < count; j++) {
            offsets[j] = offset;
            offset += buffer.readU16();
        }
        cache = new LocType[20];
        for (int i = 0; i < 20; i++) {
            cache[i] = new LocType();
        }
    }

    public boolean important;
    public byte lightAmbient;
    public int translateX;
    public String name;
    public int scaleY;
    public byte lightAttenuation;
    public int sizeX;
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
    public int sizeZ;
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
    public int decorOffset;
    public int[] modelKinds;
    public String examine;
    public boolean interactable;
    public boolean castShadow;
    public int seqID;
    public int translateZ;
    public int[] srcColor;
    public String[] options;

    public LocType() {
    }

    public void reset() {
        modelIDs = null;
        modelKinds = null;
        name = null;
        examine = null;
        srcColor = null;
        dstColor = null;
        sizeX = 1;
        sizeZ = 1;
        solid = true;
        blocksProjectiles = true;
        interactable = false;
        adjustToTerrain = false;
        dynamic = false;
        occludes = false;
        seqID = -1;
        decorOffset = 16;
        lightAmbient = 0;
        lightAttenuation = 0;
        options = null;
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

    public void prefetch(OnDemand onDemand) {
        if (modelIDs == null) {
            return;
        }
        for (int modelID : modelIDs) {
            onDemand.prefetch(modelID & 0xffff, 0);
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

    public boolean validate() {
        if (modelIDs == null) {
            return true;
        }
        boolean ok = true;
        for (int modelID : modelIDs) {
            ok &= Model.validate(modelID & 0xffff);
        }
        return ok;
    }

    public LocType getOverrideType() {
        int value = -1;

        if (varbit != -1) {
            VarbitType varbit = VarbitType.instances[this.varbit];
            int varp = varbit.varp;
            int low = varbit.lsb;
            int high = varbit.msb;
            int mask = Game.BITMASK[high - low];
            value = (game.varps[varp] >> low) & mask;
        } else if (varp != -1) {
            value = game.varps[varp];
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
            Model cached = modelCacheDynamic.get(bitset);

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

                model = modelCacheStatic.get((long) modelID);
                if (model == null) {
                    model = Model.tryGet(modelID & 0xffff);
                    if (model == null) {
                        return null;
                    }
                    if (flip) {
                        model.rotateY180();
                    }
                    modelCacheStatic.put((long) modelID, model);
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

            Model cached = modelCacheDynamic.get(bitset);

            if (cached != null) {
                return cached;
            }

            int modelID = modelIDs[kindIndex];
            boolean flip = invert ^ (rotation > 3);

            if (flip) {
                modelID += 0x10000;
            }

            model = modelCacheStatic.get((long) modelID);

            if (model == null) {
                model = Model.tryGet(modelID & 0xffff);

                if (model == null) {
                    return null;
                }

                if (flip) {
                    model.rotateY180();
                }

                modelCacheStatic.put((long) modelID, model);
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

        modelCacheDynamic.put(bitset, modified);
        return modified;
    }

    public void read(Buffer buffer) {
        int interactable = -1;

        while (true) {
            int code = buffer.readU8();

            if (code == 0) {
                break;
            } else if (code == 1) {
                int k = buffer.readU8();
                if (k > 0) {
                    if ((modelIDs == null) || lowmem) {
                        modelKinds = new int[k];
                        modelIDs = new int[k];
                        for (int k1 = 0; k1 < k; k1++) {
                            modelIDs[k1] = buffer.readU16();
                            modelKinds[k1] = buffer.readU8();
                        }
                    } else {
                        buffer.position += k * 3;
                    }
                }
            } else if (code == 2) {
                name = buffer.readString();
            } else if (code == 3) {
                examine = buffer.readString();
            } else if (code == 5) {
                int modelCount = buffer.readU8();
                if (modelCount > 0) {
                    if ((modelIDs == null) || lowmem) {
                        modelKinds = null;
                        modelIDs = new int[modelCount];
                        for (int l1 = 0; l1 < modelCount; l1++) {
                            modelIDs[l1] = buffer.readU16();
                        }
                    } else {
                        buffer.position += modelCount * 2;
                    }
                }
            } else if (code == 14) {
                sizeX = buffer.readU8();
            } else if (code == 15) {
                sizeZ = buffer.readU8();
            } else if (code == 17) {
                solid = false;
            } else if (code == 18) {
                blocksProjectiles = false;
            } else if (code == 19) {
                interactable = buffer.readU8();

                if (interactable == 1) {
                    this.interactable = true;
                }
            } else if (code == 21) {
                adjustToTerrain = true;
            } else if (code == 22) {
                dynamic = true;
            } else if (code == 23) {
                occludes = true;
            } else if (code == 24) {
                seqID = buffer.readU16();
                if (seqID == 65535) {
                    seqID = -1;
                }
            } else if (code == 28) {
                decorOffset = buffer.readU8();
            } else if (code == 29) {
                lightAmbient = buffer.read8();
            } else if (code == 39) {
                lightAttenuation = buffer.read8();
            } else if ((code >= 30) && (code < 39)) {
                if (options == null) {
                    options = new String[5];
                }
                options[code - 30] = buffer.readString();
                if (options[code - 30].equalsIgnoreCase("hidden")) {
                    options[code - 30] = null;
                }
            } else if (code == 40) {
                int recolorCount = buffer.readU8();
                srcColor = new int[recolorCount];
                dstColor = new int[recolorCount];
                for (int i = 0; i < recolorCount; i++) {
                    srcColor[i] = buffer.readU16();
                    dstColor[i] = buffer.readU16();
                }
            } else if (code == 60) {
                mapfunctionIcon = buffer.readU16();
            } else if (code == 62) {
                invert = true;
            } else if (code == 64) {
                castShadow = false;
            } else if (code == 65) {
                scaleX = buffer.readU16();
            } else if (code == 66) {
                scaleZ = buffer.readU16();
            } else if (code == 67) {
                scaleY = buffer.readU16();
            } else if (code == 68) {
                mapsceneIcon = buffer.readU16();
            } else if (code == 69) {
                interactionSideFlags = buffer.readU8();
            } else if (code == 70) {
                translateX = buffer.read16();
            } else if (code == 71) {
                translateY = buffer.read16();
            } else if (code == 72) {
                translateZ = buffer.read16();
            } else if (code == 73) {
                important = true;
            } else if (code == 74) {
                decorative = true;
            } else if (code == 75) {
                supportsObj = buffer.readU8();
            } else if (code == 77) {
                varbit = buffer.readU16();

                if (varbit == 65535) {
                    varbit = -1;
                }

                varp = buffer.readU16();

                if (varp == 65535) {
                    varp = -1;
                }

                int overrideCount = buffer.readU8();
                overrideTypeIDs = new int[overrideCount + 1];

                for (int i = 0; i <= overrideCount; i++) {
                    overrideTypeIDs[i] = buffer.readU16();

                    if (overrideTypeIDs[i] == 65535) {
                        overrideTypeIDs[i] = -1;
                    }
                }
            }
        }

        // no code 19
        if (interactable == -1) {
            this.interactable = (modelIDs != null) && ((modelKinds == null) || (modelKinds[0] == 10));

            if (options != null) {
                this.interactable = true;
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
