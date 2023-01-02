/* Class7 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class SceneBuilder {

    public static final int[] anIntArray137 = {1, 0, -1, 0};
    public static final int[] WALL_ROTATION_OCCLUDE_TYPE_1 = {16, 32, 64, 128};
    public static final int[] anIntArray144 = {0, -1, 0, 1};
    public static final int[] WALL_ROTATION_OCCLUDE_TYPE = {1, 2, 4, 8};
    public static int anInt123 = (int) (Math.random() * 17.0) - 8;
    public static int anInt131;
    public static int anInt133 = (int) (Math.random() * 33.0) - 16;
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
            int i_111_ = buffer.getSmartU();
            if (i_111_ == 0) {
                break;
            }
            i_110_ += i_111_;
            LocType type = LocType.get(i_110_);
            type.method574(onDemand);
            for (; ; ) {
                int i_112_ = buffer.getSmartU();
                if (i_112_ == 0) {
                    break;
                }
                buffer.get1U();
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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], locA, WALL_ROTATION_OCCLUDE_TYPE[nextRotation], locB, dataLevel, x, z, y, bitset, info);

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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, dataLevel, x, z, y, bitset, info);

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

                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, dataLevel, x, z, y, rotation * 512, 0, 0, bitset, info);
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

                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, dataLevel, x, z, y, rotation * 512, anIntArray137[rotation] * padding, anIntArray144[rotation] * padding, bitset, info);
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

    public static boolean method189(int i, byte[] is, int i_250_) {
        boolean bool = true;
        Buffer buffer = new Buffer(is);
        int i_252_ = -1;
        for (; ; ) {
            int i_253_ = buffer.getSmartU();
            if (i_253_ == 0) {
                break;
            }
            i_252_ += i_253_;
            int i_254_ = 0;
            boolean bool_255_ = false;
            for (; ; ) {
                if (bool_255_) {
                    int i_256_ = buffer.getSmartU();
                    if (i_256_ == 0) {
                        break;
                    }
                    buffer.get1U();
                } else {
                    int i_257_ = buffer.getSmartU();
                    if (i_257_ == 0) {
                        break;
                    }
                    i_254_ += i_257_ - 1;
                    int i_258_ = i_254_ & 0x3f;
                    int i_259_ = (i_254_ >> 6) & 0x3f;
                    int i_260_ = buffer.get1U() >> 2;
                    int i_261_ = i_259_ + i;
                    int i_262_ = i_258_ + i_250_;
                    if ((i_261_ > 0) && (i_262_ > 0) && (i_261_ < 103) && (i_262_ < 103)) {
                        LocType type = LocType.get(i_252_);
                        if ((i_260_ != 22) || !lowmem || type.interactable || type.important) {
                            bool &= type.method579();
                            bool_255_ = true;
                        }
                    }
                }
            }
        }
        return bool;
    }

    public final int[] anIntArray124;
    public final int[] anIntArray125;
    public final int[] anIntArray126;
    public final int[] anIntArray127;
    public final int[] anIntArray128;
    public final int[][][] levelHeightmap;
    public final byte[][][] aByteArrayArrayArray130;
    public final byte[][][] levelShademap;
    public final int[][][] levelOccludemap;
    public final byte[][][] aByteArrayArrayArray136;
    public final int[][] levelLightmap;
    public final byte[][][] aByteArrayArrayArray142;
    public final int maxTileX;
    public final int maxTileZ;
    public final byte[][][] aByteArrayArrayArray148;
    public final byte[][][] levelTileFlags;

    public SceneBuilder(byte[][][] levelTileFlags, int maxTileZ, int maxTileX, int[][][] levelHeightmap) {
        minLevel = 99;
        this.maxTileX = maxTileX;
        this.maxTileZ = maxTileZ;
        this.levelHeightmap = levelHeightmap;
        this.levelTileFlags = levelTileFlags;
        aByteArrayArrayArray142 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray130 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray136 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray148 = new byte[4][this.maxTileX][this.maxTileZ];
        levelOccludemap = new int[4][this.maxTileX + 1][this.maxTileZ + 1];
        levelShademap = new byte[4][this.maxTileX + 1][this.maxTileZ + 1];
        levelLightmap = new int[this.maxTileX + 1][this.maxTileZ + 1];
        anIntArray124 = new int[this.maxTileZ];
        anIntArray125 = new int[this.maxTileZ];
        anIntArray126 = new int[this.maxTileZ];
        anIntArray127 = new int[this.maxTileZ];
        anIntArray128 = new int[this.maxTileZ];
    }

    public void method171(SceneCollisionMap[] levelCollisionMaps, Scene scene) {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    if ((levelTileFlags[level][x][z] & 0x1) == 1) {
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
        anInt123 += (int) (Math.random() * 5.0) - 2;

        if (anInt123 < -8) {
            anInt123 = -8;
        }

        if (anInt123 > 8) {
            anInt123 = 8;
        }

        anInt133 += (int) (Math.random() * 5.0) - 2;

        if (anInt133 < -16) {
            anInt133 = -16;
        }

        if (anInt133 > 16) {
            anInt133 = 16;
        }

        for (int level = 0; level < 4; level++) {
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

            for (int z = 0; z < maxTileZ; z++) {
                anIntArray124[z] = 0;
                anIntArray125[z] = 0;
                anIntArray126[z] = 0;
                anIntArray127[z] = 0;
                anIntArray128[z] = 0;
            }

            for (int x0 = -5; x0 < (maxTileX + 5); x0++) {
                for (int z0 = 0; z0 < maxTileZ; z0++) {
                    int x1 = x0 + 5;

                    if ((x1 >= 0) && (x1 < maxTileX)) {
                        int floID = aByteArrayArrayArray142[level][x1][z0] & 0xff;

                        if (floID > 0) {
                            FloType flo = FloType.instances[floID - 1];
                            anIntArray124[z0] += flo.chroma;
                            anIntArray125[z0] += flo.saturation;
                            anIntArray126[z0] += flo.lightness;
                            anIntArray127[z0] += flo.luminance;
                            anIntArray128[z0]++;
                        }
                    }

                    int x2 = x0 - 5;

                    if ((x2 >= 0) && (x2 < maxTileX)) {
                        int floID = aByteArrayArrayArray142[level][x2][z0] & 0xff;

                        if (floID > 0) {
                            FloType flo = FloType.instances[floID - 1];
                            anIntArray124[z0] -= flo.chroma;
                            anIntArray125[z0] -= flo.saturation;
                            anIntArray126[z0] -= flo.lightness;
                            anIntArray127[z0] -= flo.luminance;
                            anIntArray128[z0]--;
                        }
                    }
                }

                if ((x0 >= 1) && (x0 < (maxTileX - 1))) {
                    int i_35_ = 0;
                    int i_36_ = 0;
                    int i_37_ = 0;
                    int i_38_ = 0;
                    int i_39_ = 0;

                    for (int z0 = -5; z0 < (maxTileZ + 5); z0++) {
                        int dz1 = z0 + 5;

                        if ((dz1 >= 0) && (dz1 < maxTileZ)) {
                            i_35_ += anIntArray124[dz1];
                            i_36_ += anIntArray125[dz1];
                            i_37_ += anIntArray126[dz1];
                            i_38_ += anIntArray127[dz1];
                            i_39_ += anIntArray128[dz1];
                        }

                        int dz2 = z0 - 5;

                        if ((dz2 >= 0) && (dz2 < maxTileZ)) {
                            i_35_ -= anIntArray124[dz2];
                            i_36_ -= anIntArray125[dz2];
                            i_37_ -= anIntArray126[dz2];
                            i_38_ -= anIntArray127[dz2];
                            i_39_ -= anIntArray128[dz2];
                        }

                        if ((z0 >= 1) && (z0 < (maxTileZ - 1)) && (!lowmem || ((levelTileFlags[0][x0][z0] & 0x2) != 0) || (((levelTileFlags[level][x0][z0] & 0x10) == 0) && (getDrawLevel(level, x0, z0) == anInt131)))) {
                            if (level < minLevel) {
                                minLevel = level;
                            }

                            int i_43_ = aByteArrayArrayArray142[level][x0][z0] & 0xff;
                            int floID = aByteArrayArrayArray130[level][x0][z0] & 0xff;

                            if ((i_43_ > 0) || (floID > 0)) {
                                int heightSW = levelHeightmap[level][x0][z0];
                                int heightSE = levelHeightmap[level][x0 + 1][z0];
                                int heightNE = levelHeightmap[level][x0 + 1][z0 + 1];
                                int heightNW = levelHeightmap[level][x0][z0 + 1];

                                int tileHeight00 = levelLightmap[x0][z0];
                                int tileHeight10 = levelLightmap[x0 + 1][z0];
                                int tileHeight11 = levelLightmap[x0 + 1][z0 + 1];
                                int tileHeight01 = levelLightmap[x0][z0 + 1];

                                int i_53_ = -1;
                                int i_54_ = -1;

                                if (i_43_ > 0) {
                                    int hue = (i_35_ * 256) / i_38_;
                                    int saturation = i_36_ / i_39_;
                                    int lightness = i_37_ / i_39_;

                                    i_53_ = decimateHSL(hue, saturation, lightness);

                                    hue = (hue + anInt123) & 0xff;
                                    lightness += anInt133;

                                    if (lightness < 0) {
                                        lightness = 0;
                                    } else if (lightness > 255) {
                                        lightness = 255;
                                    }

                                    i_54_ = decimateHSL(hue, saturation, lightness);
                                }

                                if (level > 0) {
                                    boolean bool = true;
                                    if ((i_43_ == 0) && (aByteArrayArrayArray136[level][x0][z0] != 0)) {
                                        bool = false;
                                    }
                                    if ((floID > 0) && !FloType.instances[floID - 1].aBoolean393) {
                                        bool = false;
                                    }
                                    if (bool && (heightSW == heightSE) && (heightSW == heightNE) && (heightSW == heightNW)) {
                                        levelOccludemap[level][x0][z0] |= 0x924;
                                    }
                                }

                                int i_58_ = 0;

                                if (i_53_ != -1) {
                                    i_58_ = Draw3D.palette[mulHSL(i_54_, 96)];
                                }

                                if (floID == 0) {
                                    scene.setTile(level, x0, z0, 0, 0, -1, heightSW, heightSE, heightNE, heightNW, mulHSL(i_53_, tileHeight00), mulHSL(i_53_, tileHeight10), mulHSL(i_53_, tileHeight11), mulHSL(i_53_, tileHeight01), 0, 0, 0, 0, i_58_, 0);
                                } else {
                                    int i_59_ = aByteArrayArrayArray136[level][x0][z0] + 1;
                                    byte i_60_ = aByteArrayArrayArray148[level][x0][z0];
                                    FloType flo = FloType.instances[floID - 1];
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

                                    scene.setTile(level, x0, z0, i_59_, i_60_, textureID, heightSW, heightSE, heightNE, heightNW, mulHSL(i_53_, tileHeight00), mulHSL(i_53_, tileHeight10), mulHSL(i_53_, tileHeight11), mulHSL(i_53_, tileHeight01), adjustLightness(hsl, tileHeight00), adjustLightness(hsl, tileHeight10), adjustLightness(hsl, tileHeight11), adjustLightness(hsl, tileHeight01), i_58_, rgb);
                                }
                            }
                        }
                    }
                }
            }

            for (int stz = 1; stz < (maxTileZ - 1); stz++) {
                for (int stx = 1; stx < (maxTileX - 1); stx++) {
                    scene.setDrawLevel(level, stx, stz, getDrawLevel(level, stx, stz));
                }
            }
        }

        scene.applyLighting(-10, 64, -50, 768, -50);

        for (int x = 0; x < maxTileX; x++) {
            for (int z = 0; z < maxTileZ; z++) {
                if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                    scene.setBridge(x, z);
                }
            }
        }

        // TODO: Find a reasonable name for these.
        int maskA = 0b001;
        int maskB = 0b010;
        int maskC = 0b100;

        for (int current = 0; current < 4; current++) {
            if (current > 0) {
                maskA <<= 3;
                maskB <<= 3;
                maskC <<= 3;
            }

            for (int level = 0; level <= current; level++) {
                for (int tileZ = 0; tileZ <= maxTileZ; tileZ++) {
                    for (int tileX = 0; tileX <= maxTileX; tileX++) {
                        if ((levelOccludemap[level][tileX][tileZ] & maskA) != 0) {
                            int minZ = tileZ;
                            int maxZ = tileZ;
                            int minLevel = level;
                            int maxLevel = level;
                            for (/**/; minZ > 0; minZ--) {
                                if ((levelOccludemap[level][tileX][minZ - 1] & maskA) == 0) {
                                    break;
                                }
                            }
                            for (/**/; maxZ < maxTileZ; maxZ++) {
                                if ((levelOccludemap[level][tileX][maxZ + 1] & maskA) == 0) {
                                    break;
                                }
                            }

                            find_min_level:
                            for (/**/; minLevel > 0; minLevel--) {
                                for (int z = minZ; z <= maxZ; z++) {
                                    if ((levelOccludemap[minLevel - 1][tileX][z] & maskA) == 0) {
                                        break find_min_level;
                                    }
                                }
                            }

                            find_max_level:
                            for (/**/; maxLevel < current; maxLevel++) {
                                for (int z = minZ; z <= maxZ; z++) {
                                    if ((levelOccludemap[maxLevel + 1][tileX][z] & maskA) == 0) {
                                        break find_max_level;
                                    }
                                }
                            }

                            int area = ((maxLevel + 1) - minLevel) * ((maxZ - minZ) + 1);

                            if (area >= 8) {
                                int maxY = levelHeightmap[maxLevel][tileX][minZ] - 240;
                                int minY = levelHeightmap[minLevel][tileX][minZ];

                                Scene.method277(current, tileX * 128, minY, tileX * 128, (maxZ * 128) + 128, maxY, minZ * 128, 1);

                                for (int l = minLevel; l <= maxLevel; l++) {
                                    for (int z = minZ; z <= maxZ; z++) {
                                        levelOccludemap[l][tileX][z] &= ~maskA;
                                    }
                                }
                            }
                        }

                        if ((levelOccludemap[level][tileX][tileZ] & maskB) != 0) {
                            int minX = tileX;
                            int maxX = tileX;
                            int minLevel = level;
                            int maxLevel = level;

                            for (/**/; minX > 0; minX--) {
                                if ((levelOccludemap[level][minX - 1][tileZ] & maskB) == 0) {
                                    break;
                                }
                            }
                            for (/**/; maxX < maxTileX; maxX++) {
                                if ((levelOccludemap[level][maxX + 1][tileZ] & maskB) == 0) {
                                    break;
                                }
                            }

                            find_min_level:
                            for (/**/; minLevel > 0; minLevel--) {
                                for (int x = minX; x <= maxX; x++) {
                                    if ((levelOccludemap[minLevel - 1][x][tileZ] & maskB) == 0) {
                                        break find_min_level;
                                    }
                                }
                            }

                            find_max_level:
                            for (/**/; maxLevel < current; maxLevel++) {
                                for (int x = minX; x <= maxX; x++) {
                                    if ((levelOccludemap[maxLevel + 1][x][tileZ] & maskB) == 0) {
                                        break find_max_level;
                                    }
                                }
                            }

                            int area = ((maxLevel + 1) - minLevel) * ((maxX - minX) + 1);

                            if (area >= 8) {
                                int maxY = levelHeightmap[maxLevel][minX][tileZ] - 240;
                                int minY = levelHeightmap[minLevel][minX][tileZ];
                                Scene.method277(current, minX * 128, minY, (maxX * 128) + 128, tileZ * 128, maxY, tileZ * 128, 2);
                                for (int i_97_ = minLevel; i_97_ <= maxLevel; i_97_++) {
                                    for (int i_98_ = minX; i_98_ <= maxX; i_98_++) {
                                        levelOccludemap[i_97_][i_98_][tileZ] &= ~maskB;
                                    }
                                }
                            }
                        }

                        if ((levelOccludemap[level][tileX][tileZ] & maskC) != 0) {
                            int i_99_ = tileX;
                            int i_100_ = tileX;
                            int i_101_ = tileZ;
                            int i_102_ = tileZ;
                            for (/**/; i_101_ > 0; i_101_--) {
                                if ((levelOccludemap[level][tileX][i_101_ - 1] & maskC) == 0) {
                                    break;
                                }
                            }
                            for (/**/; i_102_ < maxTileZ; i_102_++) {
                                if ((levelOccludemap[level][tileX][i_102_ + 1] & maskC) == 0) {
                                    break;
                                }
                            }
                            while_4_:
                            for (/**/; i_99_ > 0; i_99_--) {
                                for (int i_103_ = i_101_; i_103_ <= i_102_; i_103_++) {
                                    if ((levelOccludemap[level][i_99_ - 1][i_103_] & maskC) == 0) {
                                        break while_4_;
                                    }
                                }
                            }
                            while_5_:
                            for (/**/; i_100_ < maxTileX; i_100_++) {
                                for (int i_104_ = i_101_; i_104_ <= i_102_; i_104_++) {
                                    if ((levelOccludemap[level][i_100_ + 1][i_104_] & maskC) == 0) {
                                        break while_5_;
                                    }
                                }
                            }
                            if ((((i_100_ - i_99_) + 1) * ((i_102_ - i_101_) + 1)) >= 4) {
                                int i_105_ = levelHeightmap[level][i_99_][i_101_];
                                Scene.method277(current, i_99_ * 128, i_105_, (i_100_ * 128) + 128, (i_102_ * 128) + 128, i_105_, i_101_ * 128, 4);
                                for (int i_106_ = i_99_; i_106_ <= i_100_; i_106_++) {
                                    for (int i_107_ = i_101_; i_107_ <= i_102_; i_107_++) {
                                        levelOccludemap[level][i_106_][i_107_] &= ~maskC;
                                    }
                                }
                            }
                        }
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
        if (lowmem && ((levelTileFlags[0][x][z] & 0x2) == 0) && (((levelTileFlags[level][x][z] & 0x10) != 0) || (getDrawLevel(level, x, z) != anInt131))) {
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
                levelOccludemap[level][x][z] |= 0x924;
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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);

            if (rotation == 0) {
                if (type.castShadow) {
                    levelShademap[level][x][z] = (byte) 50;
                    levelShademap[level][x][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z] |= 0x249;
                }
            } else if (rotation == 1) {
                if (type.castShadow) {
                    levelShademap[level][x][z + 1] = (byte) 50;
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z + 1] |= 0x492;
                }
            } else if (rotation == 2) {
                if (type.castShadow) {
                    levelShademap[level][x + 1][z] = (byte) 50;
                    levelShademap[level][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x + 1][z] |= 0x249;
                }
            } else if (rotation == 3) {
                if (type.castShadow) {
                    levelShademap[level][x][z] = (byte) 50;
                    levelShademap[level][x + 1][z] = (byte) 50;
                }
                if (type.occludes) {
                    levelOccludemap[level][x][z] |= 0x492;
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

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);

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
            int i_137_ = (rotation + 1) & 0x3;
            Entity entity;
            Entity entity_138_;
            if ((type.seqID == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(2, 4 + rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                entity_138_ = type.getModel(2, i_137_, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
                entity_138_ = new LocEntity(locID, i_137_, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqID, true);
            }
            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, WALL_ROTATION_OCCLUDE_TYPE[i_137_], entity_138_, level, x, z, heightmapAverage, bitset, info);
            if (type.occludes) {
                if (rotation == 0) {
                    levelOccludemap[level][x][z] |= 0x249;
                    levelOccludemap[level][x][z + 1] |= 0x492;
                } else if (rotation == 1) {
                    levelOccludemap[level][x][z + 1] |= 0x492;
                    levelOccludemap[level][x + 1][z] |= 0x249;
                } else if (rotation == 2) {
                    levelOccludemap[level][x + 1][z] |= 0x249;
                    levelOccludemap[level][x][z] |= 0x492;
                } else if (rotation == 3) {
                    levelOccludemap[level][x][z] |= 0x492;
                    levelOccludemap[level][x][z] |= 0x249;
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
            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, level, x, z, heightmapAverage, bitset, info);
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
                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, level, x, z, heightmapAverage, rotation * 512, 0, 0, bitset, info);
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
                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, level, x, z, heightmapAverage, rotation * 512, anIntArray137[rotation] * i_142_, anIntArray144[rotation] * i_142_, bitset, info);
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
                int i_191_ = buffer.get1U();
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
                    int i_192_ = buffer.get1U();
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
                    aByteArrayArrayArray130[i_187_][i_186_][i] = buffer.get1();
                    aByteArrayArrayArray136[i_187_][i_186_][i] = (byte) ((i_191_ - 2) / 4);
                    aByteArrayArrayArray148[i_187_][i_186_][i] = (byte) (((i_191_ - 2) + i_188_) & 0x3);
                } else if (i_191_ <= 81) {
                    levelTileFlags[i_187_][i_186_][i] = (byte) (i_191_ - 49);
                } else {
                    aByteArrayArrayArray142[i_187_][i_186_][i] = (byte) (i_191_ - 81);
                }
            }
        } else {
            for (; ; ) {
                int i_193_ = buffer.get1U();
                if (i_193_ == 0) {
                    break;
                }
                if (i_193_ == 1) {
                    buffer.get1U();
                    break;
                }
                if (i_193_ <= 49) {
                    buffer.get1U();
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
            int i_204_ = buffer.getSmartU();
            if (i_204_ == 0) {
                break;
            }
            i_203_ += i_204_;
            int i_205_ = 0;
            for (; ; ) {
                int i_206_ = buffer.getSmartU();
                if (i_206_ == 0) {
                    break;
                }
                i_205_ += i_206_ - 1;
                int i_207_ = i_205_ & 0x3f;
                int i_208_ = (i_205_ >> 6) & 0x3f;
                int i_209_ = i_205_ >> 12;
                int i_210_ = buffer.get1U();
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
            int i_266_ = buffer.getSmartU();
            if (i_266_ == 0) {
                break;
            }
            i_265_ += i_266_;
            int i_267_ = 0;
            for (; ; ) {
                int i_268_ = buffer.getSmartU();
                if (i_268_ == 0) {
                    break;
                }
                i_267_ += i_268_ - 1;
                int i_269_ = i_267_ & 0x3f;
                int i_270_ = (i_267_ >> 6) & 0x3f;
                int i_271_ = i_267_ >> 12;
                int i_272_ = buffer.get1U();
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
