/* Class7 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class SceneBuilder {

    public static final int[] anIntArray137 = {1, 0, -1, 0};
    public static final int[] anIntArray144 = {0, -1, 0, 1};
    public static int randomHueOffset = (int) (Math.random() * 17.0) - 8;
    public static int level;
    public static int randomLightnessOffset = (int) (Math.random() * 33.0) - 16;
    public static int minLevel = 99;
    public static boolean lowmem = true;

    public static int method170(int i, int i_3_) {
        int i_4_ = i + (i_3_ * 57);
        i_4_ = (i_4_ << 13) ^ i_4_;
        int i_5_ = ((i_4_ * ((i_4_ * i_4_ * 15731) + 789221)) + 1376312589) & 0x7fffffff;
        return (i_5_ >> 19) & 0xff;
    }

    public static int method172(int i, int i_108_) {
        int i_109_ = (method176(i + 45365, i_108_ + 91923, 4) - 128) + ((method176(i + 10294, i_108_ + 37821, 2) - 128) >> 1) + ((method176(i, i_108_, 1) - 128) >> 2);
        i_109_ = (int) ((double) i_109_ * 0.3) + 35;
        if (i_109_ < 10) {
            i_109_ = 10;
        } else if (i_109_ > 60) {
            i_109_ = 60;
        }
        return i_109_;
    }

    public static void method173(Buffer buffer, OnDemand onDemand) {
        int i_110_ = -1;
        for (; ; ) {
            int i_111_ = buffer.readSmartU();
            if (i_111_ == 0) {
                break;
            }
            i_110_ += i_111_;
            LocType type = LocType.get(i_110_);
            type.method574(onDemand);
            for (; ; ) {
                int i_112_ = buffer.readSmartU();
                if (i_112_ == 0) {
                    break;
                }
                buffer.read8U();
            }
        }
    }

    public static int method176(int i, int i_144_, int i_145_) {
        int i_146_ = i / i_145_;
        int i_147_ = i & (i_145_ - 1);
        int i_148_ = i_144_ / i_145_;
        int i_149_ = i_144_ & (i_145_ - 1);
        int i_150_ = method186(i_146_, i_148_);
        int i_151_ = method186(i_146_ + 1, i_148_);
        int i_152_ = method186(i_146_, i_148_ + 1);
        int i_153_ = method186(i_146_ + 1, i_148_ + 1);
        int i_154_ = method184(i_150_, i_151_, i_147_, i_145_);
        int i_155_ = method184(i_152_, i_153_, i_147_, i_145_);
        return method184(i_154_, i_155_, i_149_, i_145_);
    }

    /**
     * Used to determine if a LocType's models have been loaded.
     *
     * @param locID the loc type id.
     * @param kind   the loc kind.
     * @return <code>true</code> if the loc models are ready.
     */
    public static boolean isLocReady(int locID, int kind) {
        LocType type = LocType.get(locID);
        if (kind == 11) {
            kind = 10;
        }
        if ((kind >= 5) && (kind <= 8)) {
            kind = 4;
        }
        return type.validate(kind);
    }

    public static int method184(int i, int i_216_, int i_217_, int i_218_) {
        int i_219_ = (65536 - Draw3D.cos[(i_217_ * 1024) / i_218_]) >> 1;
        return ((i * (65536 - i_219_)) >> 16) + ((i_216_ * i_219_) >> 16);
    }

    public static int method186(int i, int i_221_) {
        int i_222_ = method170(i - 1, i_221_ - 1) + method170(i + 1, i_221_ - 1) + method170(i - 1, i_221_ + 1) + method170(i + 1, i_221_ + 1);
        int i_223_ = method170(i - 1, i_221_) + method170(i + 1, i_221_) + method170(i, i_221_ - 1) + method170(i, i_221_ + 1);
        int i_224_ = method170(i, i_221_);
        return (i_222_ / 16) + (i_223_ / 8) + (i_224_ / 4);
    }

    public static int mulHSL(int hsl, int lightness) {
        if (hsl == -1) {
            return 12345678;
        }
        lightness = (lightness * (hsl & 0x7f)) / 128;

        if (lightness < 2) {
            lightness = 2;
        } else if (lightness > 126) {
            lightness = 126;
        }
        return (hsl & 0xff80) + lightness;
    }

    public static void addLoc(Scene scene, int rotation, int z, int kind, int tileLevel, SceneCollisionMap collision, int[][][] levelHeightmap, int x, int locID, int dataLevel) {
        int heightSW = levelHeightmap[tileLevel][x][z];
        int heightSE = levelHeightmap[tileLevel][x + 1][z];
        int heightNE = levelHeightmap[tileLevel][x + 1][z + 1];
        int heightNW = levelHeightmap[tileLevel][x][z + 1];
        int y = (heightSW + heightSE + heightNE + heightNW) >> 2;

        LocType loc = LocType.get(locID);
        int bitset = x + (z << 7) + (locID << 14) + 1073741824;

        if (!loc.interactable) {
            bitset += 0x80000000;
        }

        byte info = (byte) ((rotation << 6) + kind);

        if (kind == 22) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(22, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 22, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.addGroundDecoration(entity, dataLevel, x, z, y, bitset, info);

            if (loc.solid && loc.interactable) {
                collision.addSolid(z, x);
            }
        } else if ((kind == 10) || (kind == 11)) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(10, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 10, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            if (entity != null) {
                int angle = 0;

                if (kind == 11) {
                    angle += 256;
                }

                int width;
                int length;

                if ((rotation == 1) || (rotation == 3)) {
                    width = loc.length;
                    length = loc.width;
                } else {
                    width = loc.width;
                    length = loc.length;
                }

                scene.add(entity, dataLevel, x, z, y, width, length, angle, bitset, info);
            }
            if (loc.solid) {
                collision.add(loc.blocksProjectiles, loc.width, loc.length, x, z, rotation);
            }
        } else if (kind >= 12) {
            Entity entity;
            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(kind, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.add(entity, dataLevel, x, z, y, 1, 1, 0, bitset, info);

            if (loc.solid) {
                collision.add(loc.blocksProjectiles, loc.width, loc.length, x, z, rotation);
            }
        } else if (kind == 0) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(0, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 0, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_TYPE[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 1) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(1, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 1, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 2) {
            int nextRotation = (rotation + 1) & 0x3;
            Entity locA;
            Entity locB;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                locA = loc.getModel(2, 4 + rotation, heightSW, heightSE, heightNE, heightNW, -1);
                locB = loc.getModel(2, nextRotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                locA = new LocEntity(locID, 4 + rotation, 2, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                locB = new LocEntity(locID, nextRotation, 2, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_TYPE[rotation], locA, Scene.ROTATION_WALL_TYPE[nextRotation], locB, dataLevel, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 3) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(3, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 3, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 9) {
            Entity entity;

            if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(kind, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
            }

            scene.add(entity, dataLevel, x, z, y, 1, 1, 0, bitset, info);

            if (loc.solid) {
                collision.add(loc.blocksProjectiles, loc.width, loc.length, x, z, rotation);
            }
        } else {
            if (loc.adjustToTerrain) {
                if (rotation == 1) {
                    int tmp = heightNW;
                    heightNW = heightNE;
                    heightNE = heightSE;
                    heightSE = heightSW;
                    heightSW = tmp;
                } else if (rotation == 2) {
                    int tmp = heightNW;
                    heightNW = heightSE;
                    heightSE = tmp;
                    tmp = heightNE;
                    heightNE = heightSW;
                    heightSW = tmp;
                } else if (rotation == 3) {
                    int tmp = heightNW;
                    heightNW = heightSW;
                    heightSW = heightSE;
                    heightSE = heightNE;
                    heightNE = tmp;
                }
            }

            if (kind == 4) {
                Entity entity;

                if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                }

                scene.setWallDecoration(Scene.ROTATION_WALL_TYPE[rotation], entity, dataLevel, x, z, y, rotation * 512, 0, 0, bitset, info);
            } else if (kind == 5) {
                int padding = 16;
                int wallBitset = scene.getWallBitset(dataLevel, x, z);

                if (wallBitset > 0) {
                    padding = LocType.get((wallBitset >> 14) & 0x7fff).decorationPadding;
                }

                Entity entity;

                if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                }

                scene.setWallDecoration(Scene.ROTATION_WALL_TYPE[rotation], entity, dataLevel, x, z, y, rotation * 512, anIntArray137[rotation] * padding, anIntArray144[rotation] * padding, bitset, info);
            } else if (kind == 6) {
                Entity entity;

                if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                }

                scene.setWallDecoration(256, entity, dataLevel, x, z, y, rotation, 0, 0, bitset, info);
            } else if (kind == 7) {
                Entity entity;

                if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                }

                scene.setWallDecoration(512, entity, dataLevel, x, z, y, rotation, 0, 0, bitset, info);
            } else if (kind == 8) {
                Entity entity;

                if ((loc.seqID == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqID, true);
                }

                scene.setWallDecoration(768, entity, dataLevel, x, z, y, rotation, 0, 0, bitset, info);
            }
        }
    }

    /**
     * Reads the Locs from the provided data and determines if their models are available. The origin coordinate is used
     * to determine if a loc would be excluded from the scene, therefor not required to be validated. The chain of calls
     * eventually invokes {@link Model#validate(int)} which causes the {@link OnDemand} to do its job.
     * @param data the data
     * @param originX the region origin x in the scene.
     * @param originZ the region origin z in the scene.
     * @return <code>true</code> if all locs are valid.
     */
    public static boolean validateLocs(byte[] data, int originX, int originZ) {
        boolean ok = true;
        Buffer buffer = new Buffer(data);
        int locID = -1;

        for (; ; ) {
            int idDelta = buffer.readSmartU();
            if (idDelta == 0) {
                break;
            }

            locID += idDelta;

            int coordinate = 0;
            boolean skip = false;

            // this loop is for the same Loc ID.
            for (; ; ) {
                if (skip) {
                    if (buffer.readSmartU() == 0) {
                        break;
                    }
                    buffer.read8U();
                } else {
                    int coordinateDelta = buffer.readSmartU();

                    if (coordinateDelta == 0) {
                        break;
                    }

                    coordinate += coordinateDelta - 1;

                    int z = coordinate & 0x3f;
                    int x = (coordinate >> 6) & 0x3f;

                    int kind = buffer.read8U() >> 2;
                    int localX = x + originX;
                    int localZ = z + originZ;

                    if ((localX > 0) && (localZ > 0) && (localX < 103) && (localZ < 103)) {
                        LocType type = LocType.get(locID);

                        if ((kind != 22) || !lowmem || type.interactable || type.important) {
                            ok &= type.validate();
                            skip = true; // Skip the remaining locs of this ID because we only need to validate the model of one.
                        }
                    }
                }
            }
        }
        return ok;
    }

    public final int[] blendChroma;
    public final int[] blendSaturation;
    public final int[] blendLightness;
    public final int[] blendLuminance;
    public final int[] blendMagnitude;
    public final int[][][] levelHeightmap;
    public final byte[][][] levelTileOverlayIDs;
    public final byte[][][] levelShademap;
    public final int[][][] levelOccludemap;
    public final byte[][][] levelTileOverlayShape;
    public final int[][] levelLightmap;
    public final byte[][][] levelTileUnderlayIDs;
    public final int maxTileX;
    public final int maxTileZ;
    public final byte[][][] levelTileOverlayRotation;
    public final byte[][][] levelTileFlags;

    public SceneBuilder(byte[][][] levelTileFlags, int maxTileZ, int maxTileX, int[][][] levelHeightmap) {
        minLevel = 99;
        this.maxTileX = maxTileX;
        this.maxTileZ = maxTileZ;
        this.levelHeightmap = levelHeightmap;
        this.levelTileFlags = levelTileFlags;
        levelTileUnderlayIDs = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayIDs = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayShape = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayRotation = new byte[4][this.maxTileX][this.maxTileZ];
        levelOccludemap = new int[4][this.maxTileX + 1][this.maxTileZ + 1];
        levelShademap = new byte[4][this.maxTileX + 1][this.maxTileZ + 1];
        levelLightmap = new int[this.maxTileX + 1][this.maxTileZ + 1];
        blendChroma = new int[this.maxTileZ];
        blendSaturation = new int[this.maxTileZ];
        blendLightness = new int[this.maxTileZ];
        blendLuminance = new int[this.maxTileZ];
        blendMagnitude = new int[this.maxTileZ];
    }

    public void build(SceneCollisionMap[] levelCollisionMaps, Scene scene) {
        applyBlockFlags(levelCollisionMaps);
        randomize();

        for (int level = 0; level < 4; level++) {
            buildLandscapeLighting(level);
            buildTiles(scene, level);
            updateDrawLevels(scene, level);
        }

        scene.buildModels(64, 768, -50, -10, -50);

        buildBridges(scene);
        buildOccluders();
    }

    private void applyBlockFlags(SceneCollisionMap[] levelCollisionMaps) {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    // solid?
                    if ((levelTileFlags[level][x][z] & 0x1) != 1) {
                        continue;
                    }

                    int trueLevel = level;

                    // bridge
                    if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                        trueLevel--;
                    }

                    if (trueLevel >= 0) {
                        levelCollisionMaps[trueLevel].addSolid(z, x);
                    }

                }
            }
        }
    }

    private static void randomize() {
        randomHueOffset += (int) (Math.random() * 5.0) - 2;

        if (randomHueOffset < -8) {
            randomHueOffset = -8;
        }

        if (randomHueOffset > 8) {
            randomHueOffset = 8;
        }

        randomLightnessOffset += (int) (Math.random() * 5.0) - 2;

        if (randomLightnessOffset < -16) {
            randomLightnessOffset = -16;
        }

        if (randomLightnessOffset > 16) {
            randomLightnessOffset = 16;
        }
    }

    private void updateDrawLevels(Scene scene, int level) {
        for (int stz = 1; stz < (maxTileZ - 1); stz++) {
            for (int stx = 1; stx < (maxTileX - 1); stx++) {
                scene.setDrawLevel(level, stx, stz, getDrawLevel(level, stx, stz));
            }
        }
    }

    private void buildTiles(Scene scene, int level) {
        for (int z = 0; z < maxTileZ; z++) {
            blendChroma[z] = 0;
            blendSaturation[z] = 0;
            blendLightness[z] = 0;
            blendLuminance[z] = 0;
            blendMagnitude[z] = 0;
        }

        for (int x0 = -5; x0 < (maxTileX + 5); x0++) {
            for (int z0 = 0; z0 < maxTileZ; z0++) {
                int x1 = x0 + 5;

                if ((x1 >= 0) && (x1 < maxTileX)) {
                    int floID = levelTileUnderlayIDs[level][x1][z0] & 0xff;

                    if (floID > 0) {
                        FloType flo = FloType.instances[floID - 1];
                        blendChroma[z0] += flo.chroma;
                        blendSaturation[z0] += flo.saturation;
                        blendLightness[z0] += flo.lightness;
                        blendLuminance[z0] += flo.luminance;
                        blendMagnitude[z0]++;
                    }
                }

                int x2 = x0 - 5;

                if ((x2 >= 0) && (x2 < maxTileX)) {
                    int floID = levelTileUnderlayIDs[level][x2][z0] & 0xff;

                    if (floID > 0) {
                        FloType flo = FloType.instances[floID - 1];
                        blendChroma[z0] -= flo.chroma;
                        blendSaturation[z0] -= flo.saturation;
                        blendLightness[z0] -= flo.lightness;
                        blendLuminance[z0] -= flo.luminance;
                        blendMagnitude[z0]--;
                    }
                }
            }

            if ((x0 >= 1) && (x0 < (maxTileX - 1))) {
                int hueAccumulator = 0;
                int saturationAccumulator = 0;
                int lightnessAccumulator = 0;
                int luminanceAccumulator = 0;
                int magnitudeAccumulator = 0;

                for (int z0 = -5; z0 < (maxTileZ + 5); z0++) {
                    int dz1 = z0 + 5;

                    if ((dz1 >= 0) && (dz1 < maxTileZ)) {
                        hueAccumulator += blendChroma[dz1];
                        saturationAccumulator += blendSaturation[dz1];
                        lightnessAccumulator += blendLightness[dz1];
                        luminanceAccumulator += blendLuminance[dz1];
                        magnitudeAccumulator += blendMagnitude[dz1];
                    }

                    int dz2 = z0 - 5;

                    if ((dz2 >= 0) && (dz2 < maxTileZ)) {
                        hueAccumulator -= blendChroma[dz2];
                        saturationAccumulator -= blendSaturation[dz2];
                        lightnessAccumulator -= blendLightness[dz2];
                        luminanceAccumulator -= blendLuminance[dz2];
                        magnitudeAccumulator -= blendMagnitude[dz2];
                    }

                    if ((z0 < 1) || (z0 >= (maxTileZ - 1)) || (lowmem && ((levelTileFlags[0][x0][z0] & 0x2) == 0) && (((levelTileFlags[level][x0][z0] & 0x10) != 0) || (getDrawLevel(level, x0, z0) != SceneBuilder.level)))) {
                        continue;
                    }

                    if (level < minLevel) {
                        minLevel = level;
                    }

                    int underlayID = levelTileUnderlayIDs[level][x0][z0] & 0xff;
                    int overlayID = levelTileOverlayIDs[level][x0][z0] & 0xff;

                    if ((underlayID <= 0) && (overlayID <= 0)) {
                        continue;
                    }

                    int heightSW = levelHeightmap[level][x0][z0];
                    int heightSE = levelHeightmap[level][x0 + 1][z0];
                    int heightNE = levelHeightmap[level][x0 + 1][z0 + 1];
                    int heightNW = levelHeightmap[level][x0][z0 + 1];

                    int lightSW = levelLightmap[x0][z0];
                    int lightSE = levelLightmap[x0 + 1][z0];
                    int lightNE = levelLightmap[x0 + 1][z0 + 1];
                    int lightNW = levelLightmap[x0][z0 + 1];

                    int baseColor = -1;
                    int tintColor = -1;

                    if (underlayID > 0) {
                        int hue = (hueAccumulator * 256) / luminanceAccumulator;
                        int saturation = saturationAccumulator / magnitudeAccumulator;
                        int lightness = lightnessAccumulator / magnitudeAccumulator;

                        baseColor = decimateHSL(hue, saturation, lightness);

                        hue = (hue + randomHueOffset) & 0xff;
                        lightness += randomLightnessOffset;

                        if (lightness < 0) {
                            lightness = 0;
                        } else if (lightness > 255) {
                            lightness = 255;
                        }

                        tintColor = decimateHSL(hue, saturation, lightness);
                    }

                    if (level > 0) {
                        boolean occludes = true;

                        if ((underlayID == 0) && (levelTileOverlayShape[level][x0][z0] != 0)) {
                            occludes = false;
                        }

                        if ((overlayID > 0) && !FloType.instances[overlayID - 1].occludes) {
                            occludes = false;
                        }

                        // occludes && flat
                        if (occludes && (heightSW == heightSE) && (heightSW == heightNE) && (heightSW == heightNW)) {
                            levelOccludemap[level][x0][z0] |= 0b100_100_100_100;
                        }
                    }

                    int shadeColor = 0;

                    if (baseColor != -1) {
                        shadeColor = Draw3D.palette[mulHSL(tintColor, 96)];
                    }

                    if (overlayID == 0) {
                        scene.setTile(level, x0, z0, 0, 0, -1, heightSW, heightSE, heightNE, heightNW, mulHSL(baseColor, lightSW), mulHSL(baseColor, lightSE), mulHSL(baseColor, lightNE), mulHSL(baseColor, lightNW), 0, 0, 0, 0, shadeColor, 0);
                    } else {
                        int shape = levelTileOverlayShape[level][x0][z0] + 1;
                        byte rotation = levelTileOverlayRotation[level][x0][z0];
                        FloType flo = FloType.instances[overlayID - 1];
                        int textureID = flo.textureID;
                        int rgb;
                        int hsl;

                        if (textureID >= 0) {
                            rgb = Draw3D.getAverageTextureRGB(textureID);
                            hsl = -1;
                        } else if (flo.rgb == 16711935) {
                            rgb = 0;
                            hsl = -2;
                            textureID = -1;
                        } else {
                            hsl = decimateHSL(flo.hue, flo.saturation, flo.lightness);
                            rgb = Draw3D.palette[adjustLightness(flo.hsl, 96)];
                        }

                        scene.setTile(level, x0, z0, shape, rotation, textureID, heightSW, heightSE, heightNE, heightNW, mulHSL(baseColor, lightSW), mulHSL(baseColor, lightSE), mulHSL(baseColor, lightNE), mulHSL(baseColor, lightNW), adjustLightness(hsl, lightSW), adjustLightness(hsl, lightSE), adjustLightness(hsl, lightNE), adjustLightness(hsl, lightNW), shadeColor, rgb);
                    }
                }
            }
        }
    }

    private void buildLandscapeLighting(int level) {
        byte[][] shademap = levelShademap[level];
        int lightAmbient = 96;
        int lightAttenuation = 768;
        int lightX = -50;
        int lightY = -10;
        int lightZ = -50;
        int lightMagnitude = (lightAttenuation * (int) Math.sqrt((lightX * lightX) + (lightY * lightY) + (lightZ * lightZ))) >> 8;

        for (int z = 1; z < (maxTileZ - 1); z++) {
            for (int x = 1; x < (maxTileX - 1); x++) {
                int dx = levelHeightmap[level][x + 1][z] - levelHeightmap[level][x - 1][z];
                int dz = levelHeightmap[level][x][z + 1] - levelHeightmap[level][x][z - 1];
                int len = (int) Math.sqrt((dx * dx) + 65536 + (dz * dz));
                int nx = (dx << 8) / len;
                int ny = 65536 / len;
                int nz = (dz << 8) / len;
                int light = lightAmbient + (((lightX * nx) + (lightY * ny) + (lightZ * nz)) / lightMagnitude);
                int shade = (shademap[x - 1][z] >> 2) + (shademap[x + 1][z] >> 3) + (shademap[x][z - 1] >> 2) + (shademap[x][z + 1] >> 3) + (shademap[x][z] >> 1);
                levelLightmap[x][z] = light - shade;
            }
        }
    }

    private void buildBridges(Scene scene) {
        for (int x = 0; x < maxTileX; x++) {
            for (int z = 0; z < maxTileZ; z++) {
                if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                    scene.setBridge(x, z);
                }
            }
        }
    }

    private void buildOccluders() {
        int wall0 = 0b001; // this flag is set by walls with rotation 0 or 2
        int wall1 = 0b010; // this flag is set by walls with rotation 1 or 3
        int floor = 0b100; // this flag is set by floors which are flat

        for (int top = 0; top < 4; top++) {
            if (top > 0) {
                wall0 <<= 3;
                wall1 <<= 3;
                floor <<= 3;
            }

            for (int level = 0; level <= top; level++) {
                for (int tileZ = 0; tileZ <= maxTileZ; tileZ++) {
                    for (int tileX = 0; tileX <= maxTileX; tileX++) {
                        buildWallOccluders0(wall0, top, level, tileX, tileZ);
                        buildWallOccluders1(wall1, top, level, tileX, tileZ);
                        buildFloorOccluders(floor, top, level, tileX, tileZ);
                    }
                }
            }
        }
    }

    private void buildFloorOccluders(int floor, int top, int level, int tileX, int tileZ) {
        if ((levelOccludemap[level][tileX][tileZ] & floor) != 0) {
            int minTileX = tileX;
            int maxTileX = tileX;
            int minTileZ = tileZ;
            int maxTileZ = tileZ;

            for (/**/; minTileZ > 0; minTileZ--) {
                if ((levelOccludemap[level][tileX][minTileZ - 1] & floor) == 0) {
                    break;
                }
            }
            for (/**/; maxTileZ < this.maxTileZ; maxTileZ++) {
                if ((levelOccludemap[level][tileX][maxTileZ + 1] & floor) == 0) {
                    break;
                }
            }

            find_min_tile_x:
            for (/**/; minTileX > 0; minTileX--) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((levelOccludemap[level][minTileX - 1][z] & floor) == 0) {
                        break find_min_tile_x;
                    }
                }
            }

            find_max_tile_x:
            for (/**/; maxTileX < this.maxTileX; maxTileX++) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((levelOccludemap[level][maxTileX + 1][z] & floor) == 0) {
                        break find_max_tile_x;
                    }
                }
            }

            if ((((maxTileX - minTileX) + 1) * ((maxTileZ - minTileZ) + 1)) >= 4) {
                int y = levelHeightmap[level][minTileX][minTileZ];

                Scene.addOccluder(top, minTileX * 128, y, minTileZ * 128, (maxTileX * 128) + 128, y, (maxTileZ * 128) + 128, 4);

                for (int x = minTileX; x <= maxTileX; x++) {
                    for (int z = minTileZ; z <= maxTileZ; z++) {
                        levelOccludemap[level][x][z] &= ~floor;
                    }
                }
            }
        }
    }

    private void buildWallOccluders1(int wall1, int top, int level, int tileX, int tileZ) {
        if ((levelOccludemap[level][tileX][tileZ] & wall1) != 0) {
            int minTileX = tileX;
            int maxTileX = tileX;
            int minLevel = level;
            int maxLevel = level;

            for (/**/; minTileX > 0; minTileX--) {
                if ((levelOccludemap[level][minTileX - 1][tileZ] & wall1) == 0) {
                    break;
                }
            }
            for (/**/; maxTileX < this.maxTileX; maxTileX++) {
                if ((levelOccludemap[level][maxTileX + 1][tileZ] & wall1) == 0) {
                    break;
                }
            }

            find_min_level:
            for (/**/; minLevel > 0; minLevel--) {
                for (int x = minTileX; x <= maxTileX; x++) {
                    if ((levelOccludemap[minLevel - 1][x][tileZ] & wall1) == 0) {
                        break find_min_level;
                    }
                }
            }

            find_max_level:
            for (/**/; maxLevel < top; maxLevel++) {
                for (int x = minTileX; x <= maxTileX; x++) {
                    if ((levelOccludemap[maxLevel + 1][x][tileZ] & wall1) == 0) {
                        break find_max_level;
                    }
                }
            }

            int area = ((maxLevel + 1) - minLevel) * ((maxTileX - minTileX) + 1);

            if (area >= 2) {
                int minY = levelHeightmap[maxLevel][minTileX][tileZ] - 240;
                int maxY = levelHeightmap[minLevel][minTileX][tileZ];

                Scene.addOccluder(top, minTileX * 128, minY, tileZ * 128, (maxTileX * 128) + 128, maxY, tileZ * 128, 2);

                for (int l = minLevel; l <= maxLevel; l++) {
                    for (int x = minTileX; x <= maxTileX; x++) {
                        levelOccludemap[l][x][tileZ] &= ~wall1;
                    }
                }
            }
        }
    }

    private void buildWallOccluders0(int wall0, int top, int level, int tileX, int tileZ) {
        if ((levelOccludemap[level][tileX][tileZ] & wall0) != 0) {
            int minTileZ = tileZ;
            int maxTileZ = tileZ;
            int minLevel = level;
            int maxLevel = level;

            for (/**/; minTileZ > 0; minTileZ--) {
                if ((levelOccludemap[level][tileX][minTileZ - 1] & wall0) == 0) {
                    break;
                }
            }

            for (/**/; maxTileZ < this.maxTileZ; maxTileZ++) {
                if ((levelOccludemap[level][tileX][maxTileZ + 1] & wall0) == 0) {
                    break;
                }
            }

            find_min_level:
            for (/**/; minLevel > 0; minLevel--) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((levelOccludemap[minLevel - 1][tileX][z] & wall0) == 0) {
                        break find_min_level;
                    }
                }
            }

            find_max_level:
            for (/**/; maxLevel < top; maxLevel++) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((levelOccludemap[maxLevel + 1][tileX][z] & wall0) == 0) {
                        break find_max_level;
                    }
                }
            }

            int area = ((maxLevel + 1) - minLevel) * ((maxTileZ - minTileZ) + 1);

            if (area >= 2) {
                int minY = levelHeightmap[maxLevel][tileX][minTileZ] - 240;
                int maxY = levelHeightmap[minLevel][tileX][minTileZ];

                Scene.addOccluder(top, tileX * 128, minY, minTileZ * 128, tileX * 128, maxY, (maxTileZ * 128) + 128, 1);

                for (int l = minLevel; l <= maxLevel; l++) {
                    for (int z = minTileZ; z <= maxTileZ; z++) {
                        levelOccludemap[l][tileX][z] &= ~wall0;
                    }
                }
            }
        }
    }

    public void method174(int i, int i_113_, int i_115_, int i_116_) {
        for (int i_117_ = i; i_117_ <= (i + i_113_); i_117_++) {
            for (int i_118_ = i_116_; i_118_ <= (i_116_ + i_115_); i_118_++) {
                if ((i_118_ >= 0) && (i_118_ < maxTileX) && (i_117_ >= 0) && (i_117_ < maxTileZ)) {
                    levelShademap[0][i_118_][i_117_] = (byte) 127;
                    if ((i_118_ == i_116_) && (i_118_ > 0)) {
                        levelHeightmap[0][i_118_][i_117_] = levelHeightmap[0][i_118_ - 1][i_117_];
                    }
                    if ((i_118_ == (i_116_ + i_115_)) && (i_118_ < (maxTileX - 1))) {
                        levelHeightmap[0][i_118_][i_117_] = levelHeightmap[0][i_118_ + 1][i_117_];
                    }
                    if ((i_117_ == i) && (i_117_ > 0)) {
                        levelHeightmap[0][i_118_][i_117_] = levelHeightmap[0][i_118_][i_117_ - 1];
                    }
                    if ((i_117_ == (i + i_113_)) && (i_117_ < (maxTileZ - 1))) {
                        levelHeightmap[0][i_118_][i_117_] = levelHeightmap[0][i_118_][i_117_ + 1];
                    }
                }
            }
        }
    }

    public void method175(int z, Scene scene, SceneCollisionMap collision, int kind, int level, int x, int locID, int rotation) {
        if (lowmem && ((levelTileFlags[0][x][z] & 0x2) == 0) && (((levelTileFlags[level][x][z] & 0x10) != 0) || (getDrawLevel(level, x, z) != SceneBuilder.level))) {
            return;
        }
        if (level < minLevel) {
            minLevel = level;
        }
        int heightmapSW = levelHeightmap[level][x][z];
        int heightmapSE = levelHeightmap[level][x + 1][z];
        int heightmapNE = levelHeightmap[level][x + 1][z + 1];
        int heightmapNW = levelHeightmap[level][x][z + 1];
        int heightmapAverage = (heightmapSW + heightmapSE + heightmapNE + heightmapNW) >> 2;
        LocType type = LocType.get(locID);
        int bitset = x + (z << 7) + (locID << 14) + 0x40000000;

        if (!type.interactable) {
            bitset += 0x80000000;
        }

        byte info = (byte) ((rotation << 6) + kind);

        if (kind == 22) {
            if (!lowmem || type.interactable || type.important) {
                Entity entity;

                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(22, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, rotation, 22, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }

                scene.addGroundDecoration(entity, level, x, z, heightmapAverage, bitset, info);

                if (type.solid && type.interactable && (collision != null)) {
                    collision.addSolid(z, x);
                }
            }
        } else if ((kind == 10) || (kind == 11)) {
            Entity entity;

            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(10, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 10, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }

            if (entity != null) {
                int yaw = 0;

                if (kind == 11) {
                    yaw += 256;
                }

                int width;
                int height;

                if ((rotation == 1) || (rotation == 3)) {
                    width = type.length;
                    height = type.width;
                } else {
                    width = type.width;
                    height = type.length;
                }

                if (scene.add(entity, level, x, z, heightmapAverage, width, height, yaw, bitset, info) && type.castShadow) {
                    Model model;

                    if (entity instanceof Model) {
                        model = (Model) entity;
                    } else {
                        model = type.getModel(10, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                    }

                    if (model != null) {
                        for (int dx = 0; dx <= width; dx++) {
                            for (int dz = 0; dz <= height; dz++) {
                                int shade = model.radius / 4;

                                if (shade > 30) {
                                    shade = 30;
                                }

                                if (shade > levelShademap[level][x + dx][z + dz]) {
                                    levelShademap[level][x + dx][z + dz] = (byte) shade;
                                }
                            }
                        }
                    }
                }
            }

            if (type.solid && (collision != null)) {
                collision.add(type.blocksProjectiles, type.width, type.length, x, z, rotation);
            }
        } else if (kind >= 12) {
            Entity entity;

            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }

            scene.add(entity, level, x, z, heightmapAverage, 1, 1, 0, bitset, info);

            if ((kind >= 12) && (kind <= 17) && (kind != 13) && (level > 0)) {
                levelOccludemap[level][x][z] |= 0b100_100_100_100;
            }

            if (type.solid && (collision != null)) {
                collision.add(type.blocksProjectiles, type.width, type.length, x, z, rotation);
            }
        } else if (kind == 0) {
            Entity entity;

            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(0, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 0, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_TYPE[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);

            if (rotation == 0) {
                if (type.castShadow) {
                    levelShademap[level][x][z] = (byte) 50;
                    levelShademap[level][x][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z] |= 0b001_001_001_001;
                }
            } else if (rotation == 1) {
                if (type.castShadow) {
                    levelShademap[level][x][z + 1] = (byte) 50;
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z + 1] |= 0b010_010_010_010;
                }
            } else if (rotation == 2) {
                if (type.castShadow) {
                    levelShademap[level][x + 1][z] = (byte) 50;
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x + 1][z] |= 0b001_001_001_001;
                }
            } else if (rotation == 3) {
                if (type.castShadow) {
                    levelShademap[level][x][z] = (byte) 50;
                    levelShademap[level][x + 1][z] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z] |= 0b010_010_010_010;
                }
            }

            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }

            if (type.decorationPadding != 16) {
                scene.setWallDecorationOffset(level, x, z, type.decorationPadding);
            }
        } else if (kind == 1) {
            Entity entity;

            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(1, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 1, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }

            scene.setWall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);

            if (type.castShadow) {
                if (rotation == 0) {
                    levelShademap[level][x][z + 1] = (byte) 50;
                } else if (rotation == 1) {
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                } else if (rotation == 2) {
                    levelShademap[level][x + 1][z] = (byte) 50;
                } else if (rotation == 3) {
                    levelShademap[level][x][z] = (byte) 50;
                }
            }

            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
        } else if (kind == 2) {
            int nextRotation = (rotation + 1) & 0x3;
            Entity entityA;
            Entity entityB;
            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entityA = type.getModel(2, 4 + rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                entityB = type.getModel(2, nextRotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entityA = new LocEntity(locID, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                entityB = new LocEntity(locID, nextRotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }
            scene.setWall(Scene.ROTATION_WALL_TYPE[rotation], entityA, Scene.ROTATION_WALL_TYPE[nextRotation], entityB, level, x, z, heightmapAverage, bitset, info);

            if (type.occludes) {
                if (rotation == 0) {
                    levelOccludemap[level][x][z] |= 0b001_001_001_001;
                    levelOccludemap[level][x][z + 1] |= 0b010_010_010_010;
                } else if (rotation == 1) {
                    levelOccludemap[level][x][z + 1] |= 0b010_010_010_010;
                    levelOccludemap[level][x + 1][z] |= 0b001_001_001_001;
                } else if (rotation == 2) {
                    levelOccludemap[level][x + 1][z] |= 0b001_001_001_001;
                    levelOccludemap[level][x][z] |= 0b010_010_010_010;
                } else if (rotation == 3) {
                    levelOccludemap[level][x][z] |= 0b010_010_010_010;
                    levelOccludemap[level][x][z] |= 0b001_001_001_001;
                }
            }
            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
            if (type.decorationPadding != 16) {
                scene.setWallDecorationOffset(level, x, z, type.decorationPadding);
            }
        } else if (kind == 3) {
            Entity entity;
            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(3, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 3, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }
            scene.setWall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);
            if (type.castShadow) {
                if (rotation == 0) {
                    levelShademap[level][x][z + 1] = (byte) 50;
                } else if (rotation == 1) {
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                } else if (rotation == 2) {
                    levelShademap[level][x + 1][z] = (byte) 50;
                } else if (rotation == 3) {
                    levelShademap[level][x][z] = (byte) 50;
                }
            }
            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
        } else if (kind == 9) {
            Entity entity;
            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }
            scene.add(entity, level, x, z, heightmapAverage, 1, 1, 0, bitset, info);
            if (type.solid && (collision != null)) {
                collision.add(type.blocksProjectiles, type.width, type.length, x, z, rotation);
            }
        } else {
            if (type.adjustToTerrain) {
                if (rotation == 1) {
                    int i_139_ = heightmapNW;
                    heightmapNW = heightmapNE;
                    heightmapNE = heightmapSE;
                    heightmapSE = heightmapSW;
                    heightmapSW = i_139_;
                } else if (rotation == 2) {
                    int i_140_ = heightmapNW;
                    heightmapNW = heightmapSE;
                    heightmapSE = i_140_;
                    i_140_ = heightmapNE;
                    heightmapNE = heightmapSW;
                    heightmapSW = i_140_;
                } else if (rotation == 3) {
                    int i_141_ = heightmapNW;
                    heightmapNW = heightmapSW;
                    heightmapSW = heightmapSE;
                    heightmapSE = heightmapNE;
                    heightmapNE = i_141_;
                }
            }
            if (kind == 4) {
                Entity entity;
                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }
                scene.setWallDecoration(Scene.ROTATION_WALL_TYPE[rotation], entity, level, x, z, heightmapAverage, rotation * 512, 0, 0, bitset, info);
            } else if (kind == 5) {
                int i_142_ = 16;
                int i_143_ = scene.getWallBitset(level, x, z);
                if (i_143_ > 0) {
                    i_142_ = LocType.get((i_143_ >> 14) & 0x7fff).decorationPadding;
                }
                Entity entity;
                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }
                scene.setWallDecoration(Scene.ROTATION_WALL_TYPE[rotation], entity, level, x, z, heightmapAverage, rotation * 512, anIntArray137[rotation] * i_142_, anIntArray144[rotation] * i_142_, bitset, info);
            } else if (kind == 6) {
                Entity entity;
                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }
                scene.setWallDecoration(256, entity, level, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            } else if (kind == 7) {
                Entity entity;
                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }
                scene.setWallDecoration(512, entity, level, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            } else if (kind == 8) {
                Entity entity;
                if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                }
                scene.setWallDecoration(768, entity, level, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            }
        }
    }

    public int decimateHSL(int hue, int saturation, int lightness) {
        if (lightness > 179) {
            saturation /= 2;
        }
        if (lightness > 192) {
            saturation /= 2;
        }
        if (lightness > 217) {
            saturation /= 2;
        }
        if (lightness > 243) {
            saturation /= 2;
        }
        return ((hue / 4) << 10) + ((saturation / 32) << 7) + (lightness / 2);
    }

    public void method179(int i, int i_162_, SceneCollisionMap[] collisionMaps, int i_164_, int i_165_, byte[] is, int i_166_, int i_167_, int i_168_) {
        for (int i_169_ = 0; i_169_ < 8; i_169_++) {
            for (int i_170_ = 0; i_170_ < 8; i_170_++) {
                if (((i_164_ + i_169_) > 0) && ((i_164_ + i_169_) < 103) && ((i_168_ + i_170_) > 0) && ((i_168_ + i_170_) < 103)) {
                    collisionMaps[i_167_].flags[i_164_ + i_169_][i_168_ + i_170_] &= ~0x1000000;
                }
            }
        }
        Buffer buffer = new Buffer(is);
        for (int i_172_ = 0; i_172_ < 4; i_172_++) {
            for (int i_173_ = 0; i_173_ < 64; i_173_++) {
                for (int i_174_ = 0; i_174_ < 64; i_174_++) {
                    if ((i_172_ == i) && (i_173_ >= i_165_) && (i_173_ < (i_165_ + 8)) && (i_174_ >= i_166_) && (i_174_ < (i_166_ + 8))) {
                        method181(i_168_ + ZoneUtil.method156(i_174_ & 0x7, i_162_, i_173_ & 0x7), 0, buffer, i_164_ + ZoneUtil.method155(i_162_, i_174_ & 0x7, i_173_ & 0x7), i_167_, i_162_, 0);
                    } else {
                        method181(-1, 0, buffer, -1, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void method180(byte[] is, int i, int i_175_, int i_176_, int i_177_, SceneCollisionMap[] collisionMaps) {
        for (int i_179_ = 0; i_179_ < 4; i_179_++) {
            for (int i_180_ = 0; i_180_ < 64; i_180_++) {
                for (int i_181_ = 0; i_181_ < 64; i_181_++) {
                    if (((i_175_ + i_180_) > 0) && ((i_175_ + i_180_) < 103) && ((i + i_181_) > 0) && ((i + i_181_) < 103)) {
                        collisionMaps[i_179_].flags[i_175_ + i_180_][i + i_181_] &= ~0x1000000;
                    }
                }
            }
        }
        Buffer buffer = new Buffer(is);
        for (int i_182_ = 0; i_182_ < 4; i_182_++) {
            for (int i_183_ = 0; i_183_ < 64; i_183_++) {
                for (int i_184_ = 0; i_184_ < 64; i_184_++) {
                    method181(i_184_ + i, i_177_, buffer, i_183_ + i_175_, i_182_, 0, i_176_);
                }
            }
        }
    }

    public void method181(int i, int i_185_, Buffer buffer, int i_186_, int i_187_, int i_188_, int i_190_) {
        if ((i_186_ >= 0) && (i_186_ < 104) && (i >= 0) && (i < 104)) {
            levelTileFlags[i_187_][i_186_][i] = (byte) 0;
            for (; ; ) {
                int i_191_ = buffer.read8U();
                if (i_191_ == 0) {
                    if (i_187_ == 0) {
                        levelHeightmap[0][i_186_][i] = -method172(932731 + i_186_ + i_190_, 556238 + i + i_185_) * 8;
                    } else {
                        levelHeightmap[i_187_][i_186_][i] = levelHeightmap[i_187_ - 1][i_186_][i] - 240;
                        break;
                    }
                    break;
                }
                if (i_191_ == 1) {
                    int i_192_ = buffer.read8U();
                    if (i_192_ == 1) {
                        i_192_ = 0;
                    }
                    if (i_187_ == 0) {
                        levelHeightmap[0][i_186_][i] = -i_192_ * 8;
                    } else {
                        levelHeightmap[i_187_][i_186_][i] = levelHeightmap[i_187_ - 1][i_186_][i] - (i_192_ * 8);
                        break;
                    }
                    break;
                }
                if (i_191_ <= 49) {
                    levelTileOverlayIDs[i_187_][i_186_][i] = buffer.read();
                    levelTileOverlayShape[i_187_][i_186_][i] = (byte) ((i_191_ - 2) / 4);
                    levelTileOverlayRotation[i_187_][i_186_][i] = (byte) (((i_191_ - 2) + i_188_) & 0x3);
                } else if (i_191_ <= 81) {
                    levelTileFlags[i_187_][i_186_][i] = (byte) (i_191_ - 49);
                } else {
                    levelTileUnderlayIDs[i_187_][i_186_][i] = (byte) (i_191_ - 81);
                }
            }
        } else {
            for (; ; ) {
                int i_193_ = buffer.read8U();
                if (i_193_ == 0) {
                    break;
                }
                if (i_193_ == 1) {
                    buffer.read8U();
                    break;
                }
                if (i_193_ <= 49) {
                    buffer.read8U();
                }
            }
        }
    }

    public int getDrawLevel(int level, int stx, int stz) {
        if ((levelTileFlags[level][stx][stz] & 0x8) != 0) {
            return 0;
        }
        if ((level > 0) && ((levelTileFlags[1][stx][stz] & 0x2) != 0)) {
            return level - 1;
        }
        return level;
    }

    public void method183(SceneCollisionMap[] collisionMaps, Scene scene, int i, int i_197_, int i_198_, int i_199_, byte[] is, int i_200_, int i_201_, int i_202_) {
        Buffer buffer = new Buffer(is);
        int i_203_ = -1;
        for (; ; ) {
            int i_204_ = buffer.readSmartU();
            if (i_204_ == 0) {
                break;
            }
            i_203_ += i_204_;
            int i_205_ = 0;
            for (; ; ) {
                int i_206_ = buffer.readSmartU();
                if (i_206_ == 0) {
                    break;
                }
                i_205_ += i_206_ - 1;
                int i_207_ = i_205_ & 0x3f;
                int i_208_ = (i_205_ >> 6) & 0x3f;
                int i_209_ = i_205_ >> 12;
                int i_210_ = buffer.read8U();
                int i_211_ = i_210_ >> 2;
                int i_212_ = i_210_ & 0x3;
                if ((i_209_ == i) && (i_208_ >= i_200_) && (i_208_ < (i_200_ + 8)) && (i_207_ >= i_198_) && (i_207_ < (i_198_ + 8))) {
                    LocType type = LocType.get(i_203_);
                    int i_213_ = i_197_ + ZoneUtil.method157(i_201_, type.length, i_208_ & 0x7, i_207_ & 0x7, type.width);
                    int i_214_ = i_202_ + ZoneUtil.method158(i_207_ & 0x7, type.length, i_201_, type.width, i_208_ & 0x7);
                    if ((i_213_ > 0) && (i_214_ > 0) && (i_213_ < 103) && (i_214_ < 103)) {
                        int i_215_ = i_209_;
                        if ((levelTileFlags[1][i_213_][i_214_] & 0x2) == 2) {
                            i_215_--;
                        }
                        SceneCollisionMap collisionMap = null;
                        if (i_215_ >= 0) {
                            collisionMap = collisionMaps[i_215_];
                        }
                        method175(i_214_, scene, collisionMap, i_211_, i_199_, i_213_, i_203_, (i_212_ + i_201_) & 0x3);
                    }
                }
            }
        }
    }

    public int adjustLightness(int hsl, int scalar) {
        if (hsl == -2) {
            return 12345678;
        }

        if (hsl == -1) {
            if (scalar < 0) {
                scalar = 0;
            } else if (scalar > 127) {
                scalar = 127;
            }
            scalar = 127 - scalar;
            return scalar;
        }

        scalar = (scalar * (hsl & 0x7f)) / 128;

        if (scalar < 2) {
            scalar = 2;
        } else if (scalar > 126) {
            scalar = 126;
        }

        return (hsl & 0xff80) + scalar;
    }

    public void method190(int i, SceneCollisionMap[] collisionMaps, int i_263_, Scene scene, byte[] is) {
        Buffer buffer = new Buffer(is);
        int i_265_ = -1;
        for (; ; ) {
            int i_266_ = buffer.readSmartU();
            if (i_266_ == 0) {
                break;
            }
            i_265_ += i_266_;
            int i_267_ = 0;
            for (; ; ) {
                int i_268_ = buffer.readSmartU();
                if (i_268_ == 0) {
                    break;
                }
                i_267_ += i_268_ - 1;
                int i_269_ = i_267_ & 0x3f;
                int i_270_ = (i_267_ >> 6) & 0x3f;
                int i_271_ = i_267_ >> 12;
                int i_272_ = buffer.read8U();
                int i_273_ = i_272_ >> 2;
                int i_274_ = i_272_ & 0x3;
                int i_275_ = i_270_ + i;
                int i_276_ = i_269_ + i_263_;
                if ((i_275_ > 0) && (i_276_ > 0) && (i_275_ < 103) && (i_276_ < 103)) {
                    int i_277_ = i_271_;
                    if ((levelTileFlags[1][i_275_][i_276_] & 0x2) == 2) {
                        i_277_--;
                    }
                    SceneCollisionMap collisionMap = null;
                    if (i_277_ >= 0) {
                        collisionMap = collisionMaps[i_277_];
                    }
                    method175(i_276_, scene, collisionMap, i_273_, i_271_, i_275_, i_265_, i_274_);
                }
            }
        }
    }

}
