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
    public static int minPlane = 99;
    public static boolean lowmem = true;

    public static int method170(int i, int i_3_) {
        int i_4_ = i + (i_3_ * 57);
        i_4_ = (i_4_ << 13) ^ i_4_;
        int i_5_ = ((i_4_ * ((i_4_ * i_4_ * 15731) + 789221)) + 1376312589) & 0x7fffffff;
        return (i_5_ >> 19) & 0xff;
    }

    public static int method172(int i, int i_108_) {
        int i_109_ = ((method176(i + 45365, i_108_ + 91923, 4) - 128) + ((method176(i + 10294, i_108_ + 37821, 2) - 128) >> 1) + ((method176(i, i_108_, 1) - 128) >> 2));
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
        int i_219_ = ((65536 - Draw3D.cos[(i_217_ * 1024) / i_218_]) >> 1);
        return ((i * (65536 - i_219_)) >> 16) + ((i_216_ * i_219_) >> 16);
    }

    public static int method186(int i, int i_221_) {
        int i_222_ = (method170(i - 1, i_221_ - 1) + method170(i + 1, i_221_ - 1) + method170(i - 1, i_221_ + 1) + method170(i + 1, i_221_ + 1));
        int i_223_ = (method170(i - 1, i_221_) + method170(i + 1, i_221_) + method170(i, i_221_ - 1) + method170(i, i_221_ + 1));
        int i_224_ = method170(i, i_221_);
        return (i_222_ / 16) + (i_223_ / 8) + (i_224_ / 4);
    }

    public static int method187(int i, int i_225_) {
        if (i == -1) {
            return 12345678;
        }
        i_225_ = (i_225_ * (i & 0x7f)) / 128;
        if (i_225_ < 2) {
            i_225_ = 2;
        } else if (i_225_ > 126) {
            i_225_ = 126;
        }
        return (i & 0xff80) + i_225_;
    }

    public static void addLoc(Scene scene, int rotation, int z, int kind, int plane, SceneCollisionMap collision, int[][][] planeHeightmap, int x, int locID, int dataPlane) {
        int heightSW = planeHeightmap[plane][x][z];
        int heightSE = planeHeightmap[plane][x + 1][z];
        int heightNE = planeHeightmap[plane][x + 1][z + 1];
        int heightNW = planeHeightmap[plane][x][z + 1];
        int y = (heightSW + heightSE + heightNE + heightNW) >> 2;

        LocType loc = LocType.get(locID);
        int bitset = x + (z << 7) + (locID << 14) + 1073741824;

        if (!loc.interactable) {
            bitset += 0x80000000;
        }

        byte info = (byte) ((rotation << 6) + kind);

        if (kind == 22) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(22, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 22, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.addGroundDecoration(entity, dataPlane, x, z, y, bitset, info);

            if (loc.solid && loc.interactable) {
                collision.addSolid(z, x);
            }
        } else if ((kind == 10) || (kind == 11)) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(10, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 10, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
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

                scene.add(entity, dataPlane, x, z, y, width, length, angle, bitset, info);
            }
            if (loc.solid) {
                collision.add(loc.blocksProjectiles, loc.width, loc.length, x, z, rotation);
            }
        } else if (kind >= 12) {
            Entity entity;
            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(kind, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.add(entity, dataPlane, x, z, y, 1, 1, 0, bitset, info);

            if (loc.solid) {
                collision.add(loc.blocksProjectiles, loc.width, loc.length, x, z, rotation);
            }
        } else if (kind == 0) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(0, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 0, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, 0, null, dataPlane, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 1) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(1, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 1, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, dataPlane, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 2) {
            int nextRotation = (rotation + 1) & 0x3;
            Entity locA;
            Entity locB;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                locA = loc.getModel(2, 4 + rotation, heightSW, heightSE, heightNE, heightNW, -1);
                locB = loc.getModel(2, nextRotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                locA = new LocEntity(locID, 4 + rotation, 2, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                locB = new LocEntity(locID, nextRotation, 2, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], locA, WALL_ROTATION_OCCLUDE_TYPE[nextRotation], locB, dataPlane, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 3) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(3, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 3, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, dataPlane, x, z, y, bitset, info);

            if (loc.solid) {
                collision.addWall(z, rotation, x, kind, loc.blocksProjectiles);
            }
        } else if (kind == 9) {
            Entity entity;

            if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                entity = loc.getModel(kind, rotation, heightSW, heightSE, heightNE, heightNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
            }

            scene.add(entity, dataPlane, x, z, y, 1, 1, 0, bitset, info);

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

                if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                }

                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, dataPlane, x, z, y, rotation * 512, 0, 0, bitset, info);
            } else if (kind == 5) {
                int padding = 16;
                int wallBitset = scene.getWallBitset(dataPlane, x, z);

                if (wallBitset > 0) {
                    padding = LocType.get((wallBitset >> 14) & 0x7fff).decorationPadding;
                }

                Entity entity;

                if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                }

                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, dataPlane, x, z, y, rotation * 512, anIntArray137[rotation] * padding, anIntArray144[rotation] * padding, bitset, info);
            } else if (kind == 6) {
                Entity entity;

                if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                }

                scene.setWallDecoration(256, entity, dataPlane, x, z, y, rotation, 0, 0, bitset, info);
            } else if (kind == 7) {
                Entity entity;

                if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                }

                scene.setWallDecoration(512, entity, dataPlane, x, z, y, rotation, 0, 0, bitset, info);
            } else if (kind == 8) {
                Entity entity;

                if ((loc.seqId == -1) && (loc.overrideTypeIDs == null)) {
                    entity = loc.getModel(4, 0, heightSW, heightSE, heightNE, heightNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightSE, heightNE, heightSW, heightNW, loc.seqId, true);
                }

                scene.setWallDecoration(768, entity, dataPlane, x, z, y, rotation, 0, 0, bitset, info);
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
    public final int[][][] planeHeightmap;
    public final byte[][][] aByteArrayArrayArray130;
    public final byte[][][] planeShademap;
    public final int[][][] planeOccludemap;
    public final byte[][][] aByteArrayArrayArray136;
    public final int[][] anIntArrayArray139;
    public final byte[][][] aByteArrayArrayArray142;
    public final int maxTileX;
    public final int maxTileZ;
    public final byte[][][] aByteArrayArrayArray148;
    public final byte[][][] planeTileFlags;

    public SceneBuilder(byte[][][] planeTileFlags, int maxTileZ, int maxTileX, int[][][] planeHeightmap) {
        minPlane = 99;
        this.maxTileX = maxTileX;
        this.maxTileZ = maxTileZ;
        this.planeHeightmap = planeHeightmap;
        this.planeTileFlags = planeTileFlags;
        aByteArrayArrayArray142 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray130 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray136 = new byte[4][this.maxTileX][this.maxTileZ];
        aByteArrayArrayArray148 = new byte[4][this.maxTileX][this.maxTileZ];
        planeOccludemap = new int[4][this.maxTileX + 1][this.maxTileZ + 1];
        planeShademap = new byte[4][this.maxTileX + 1][this.maxTileZ + 1];
        anIntArrayArray139 = new int[this.maxTileX + 1][this.maxTileZ + 1];
        anIntArray124 = new int[this.maxTileZ];
        anIntArray125 = new int[this.maxTileZ];
        anIntArray126 = new int[this.maxTileZ];
        anIntArray127 = new int[this.maxTileZ];
        anIntArray128 = new int[this.maxTileZ];
    }

    public void method171(SceneCollisionMap[] collisionMaps, Scene scene) {
        for (int i_6_ = 0; i_6_ < 4; i_6_++) {
            for (int i_7_ = 0; i_7_ < 104; i_7_++) {
                for (int i_8_ = 0; i_8_ < 104; i_8_++) {
                    if ((planeTileFlags[i_6_][i_7_][i_8_] & 0x1) == 1) {
                        int i_9_ = i_6_;
                        if ((planeTileFlags[1][i_7_][i_8_] & 0x2) == 2) {
                            i_9_--;
                        }
                        if (i_9_ >= 0) {
                            collisionMaps[i_9_].addSolid(i_8_, i_7_);
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
        for (int plane = 0; plane < 4; plane++) {
            byte[][] is = planeShademap[plane];
            int i_11_ = 96;
            int i_12_ = 768;
            int i_13_ = -50;
            int i_14_ = -10;
            int i_15_ = -50;
            int i_16_ = (int) Math.sqrt((i_13_ * i_13_) + (i_14_ * i_14_) + (i_15_ * i_15_));
            int i_17_ = (i_12_ * i_16_) >> 8;
            for (int i_18_ = 1; i_18_ < (maxTileZ - 1); i_18_++) {
                for (int i_19_ = 1; i_19_ < (maxTileX - 1); i_19_++) {
                    int i_20_ = (planeHeightmap[plane][i_19_ + 1][i_18_] - planeHeightmap[plane][i_19_ - 1][i_18_]);
                    int i_21_ = (planeHeightmap[plane][i_19_][i_18_ + 1] - planeHeightmap[plane][i_19_][i_18_ - 1]);
                    int i_22_ = (int) Math.sqrt((i_20_ * i_20_) + 65536 + (i_21_ * i_21_));
                    int i_23_ = (i_20_ << 8) / i_22_;
                    int i_24_ = 65536 / i_22_;
                    int i_25_ = (i_21_ << 8) / i_22_;
                    int i_26_ = i_11_ + (((i_13_ * i_23_) + (i_14_ * i_24_) + (i_15_ * i_25_)) / i_17_);
                    int i_27_ = ((is[i_19_ - 1][i_18_] >> 2) + (is[i_19_ + 1][i_18_] >> 3) + (is[i_19_][i_18_ - 1] >> 2) + (is[i_19_][i_18_ + 1] >> 3) + (is[i_19_][i_18_] >> 1));
                    anIntArrayArray139[i_19_][i_18_] = i_26_ - i_27_;
                }
            }
            for (int i_28_ = 0; i_28_ < maxTileZ; i_28_++) {
                anIntArray124[i_28_] = 0;
                anIntArray125[i_28_] = 0;
                anIntArray126[i_28_] = 0;
                anIntArray127[i_28_] = 0;
                anIntArray128[i_28_] = 0;
            }
            for (int i_29_ = -5; i_29_ < (maxTileX + 5); i_29_++) {
                for (int i_30_ = 0; i_30_ < maxTileZ; i_30_++) {
                    int i_31_ = i_29_ + 5;
                    if ((i_31_ >= 0) && (i_31_ < maxTileX)) {
                        int i_32_ = aByteArrayArrayArray142[plane][i_31_][i_30_] & 0xff;
                        if (i_32_ > 0) {
                            FloType type = FloType.instances[i_32_ - 1];
                            anIntArray124[i_30_] += type.anInt397;
                            anIntArray125[i_30_] += type.anInt395;
                            anIntArray126[i_30_] += type.anInt396;
                            anIntArray127[i_30_] += type.anInt398;
                            anIntArray128[i_30_]++;
                        }
                    }
                    int i_33_ = i_29_ - 5;
                    if ((i_33_ >= 0) && (i_33_ < maxTileX)) {
                        int i_34_ = aByteArrayArrayArray142[plane][i_33_][i_30_] & 0xff;
                        if (i_34_ > 0) {
                            FloType type = FloType.instances[i_34_ - 1];
                            anIntArray124[i_30_] -= type.anInt397;
                            anIntArray125[i_30_] -= type.anInt395;
                            anIntArray126[i_30_] -= type.anInt396;
                            anIntArray127[i_30_] -= type.anInt398;
                            anIntArray128[i_30_]--;
                        }
                    }
                }
                if ((i_29_ >= 1) && (i_29_ < (maxTileX - 1))) {
                    int i_35_ = 0;
                    int i_36_ = 0;
                    int i_37_ = 0;
                    int i_38_ = 0;
                    int i_39_ = 0;
                    for (int i_40_ = -5; i_40_ < (maxTileZ + 5); i_40_++) {
                        int i_41_ = i_40_ + 5;
                        if ((i_41_ >= 0) && (i_41_ < maxTileZ)) {
                            i_35_ += anIntArray124[i_41_];
                            i_36_ += anIntArray125[i_41_];
                            i_37_ += anIntArray126[i_41_];
                            i_38_ += anIntArray127[i_41_];
                            i_39_ += anIntArray128[i_41_];
                        }
                        int i_42_ = i_40_ - 5;
                        if ((i_42_ >= 0) && (i_42_ < maxTileZ)) {
                            i_35_ -= anIntArray124[i_42_];
                            i_36_ -= anIntArray125[i_42_];
                            i_37_ -= anIntArray126[i_42_];
                            i_38_ -= anIntArray127[i_42_];
                            i_39_ -= anIntArray128[i_42_];
                        }
                        if ((i_40_ >= 1) && (i_40_ < (maxTileZ - 1)) && (!lowmem || ((planeTileFlags[0][i_29_][i_40_] & 0x2) != 0) || (((planeTileFlags[plane][i_29_][i_40_] & 0x10) == 0) && (getDrawPlane(plane, i_29_, i_40_) == anInt131)))) {
                            if (plane < minPlane) {
                                minPlane = plane;
                            }
                            int i_43_ = (aByteArrayArrayArray142[plane][i_29_][i_40_] & 0xff);
                            int i_44_ = (aByteArrayArrayArray130[plane][i_29_][i_40_] & 0xff);
                            if ((i_43_ > 0) || (i_44_ > 0)) {
                                int i_45_ = planeHeightmap[plane][i_29_][i_40_];
                                int i_46_ = (planeHeightmap[plane][i_29_ + 1][i_40_]);
                                int i_47_ = (planeHeightmap[plane][i_29_ + 1][i_40_ + 1]);
                                int i_48_ = (planeHeightmap[plane][i_29_][i_40_ + 1]);
                                int i_49_ = anIntArrayArray139[i_29_][i_40_];
                                int i_50_ = anIntArrayArray139[i_29_ + 1][i_40_];
                                int i_51_ = anIntArrayArray139[i_29_ + 1][i_40_ + 1];
                                int i_52_ = anIntArrayArray139[i_29_][i_40_ + 1];
                                int i_53_ = -1;
                                int i_54_ = -1;
                                if (i_43_ > 0) {
                                    int i_55_ = (i_35_ * 256) / i_38_;
                                    int i_56_ = i_36_ / i_39_;
                                    int i_57_ = i_37_ / i_39_;
                                    i_53_ = method177(i_55_, i_56_, i_57_);
                                    i_55_ = (i_55_ + anInt123) & 0xff;
                                    i_57_ += anInt133;
                                    if (i_57_ < 0) {
                                        i_57_ = 0;
                                    } else if (i_57_ > 255) {
                                        i_57_ = 255;
                                    }
                                    i_54_ = method177(i_55_, i_56_, i_57_);
                                }
                                if (plane > 0) {
                                    boolean bool = true;
                                    if ((i_43_ == 0) && ((aByteArrayArrayArray136[plane][i_29_][i_40_]) != 0)) {
                                        bool = false;
                                    }
                                    if ((i_44_ > 0) && !(FloType.instances[i_44_ - 1].aBoolean393)) {
                                        bool = false;
                                    }
                                    if (bool && (i_45_ == i_46_) && (i_45_ == i_47_) && (i_45_ == i_48_)) {
                                        planeOccludemap[plane][i_29_][i_40_] |= 0x924;
                                    }
                                }
                                int i_58_ = 0;
                                if (i_53_ != -1) {
                                    i_58_ = (Draw3D.palette[method187(i_54_, 96)]);
                                }
                                if (i_44_ == 0) {
                                    scene.method279(plane, i_29_, i_40_, 0, 0, -1, i_45_, i_46_, i_47_, i_48_, method187(i_53_, i_49_), method187(i_53_, i_50_), method187(i_53_, i_51_), method187(i_53_, i_52_), 0, 0, 0, 0, i_58_, 0);
                                } else {
                                    int i_59_ = ((aByteArrayArrayArray136[plane][i_29_][i_40_]) + 1);
                                    byte i_60_ = (aByteArrayArrayArray148[plane][i_29_][i_40_]);
                                    FloType type = FloType.instances[i_44_ - 1];
                                    int textureId = type.textureId;
                                    int i_62_;
                                    int i_63_;
                                    if (textureId >= 0) {
                                        i_62_ = Draw3D.getAverageTextureRGB(textureId);
                                        i_63_ = -1;
                                    } else if (type.anInt390 == 16711935) {
                                        i_62_ = 0;
                                        i_63_ = -2;
                                        textureId = -1;
                                    } else {
                                        i_63_ = method177(type.anInt394, type.anInt395, type.anInt396);
                                        i_62_ = (Draw3D.palette[method185(type.anInt399, 96)]);
                                    }
                                    scene.method279(plane, i_29_, i_40_, i_59_, i_60_, textureId, i_45_, i_46_, i_47_, i_48_, method187(i_53_, i_49_), method187(i_53_, i_50_), method187(i_53_, i_51_), method187(i_53_, i_52_), method185(i_63_, i_49_), method185(i_63_, i_50_), method185(i_63_, i_51_), method185(i_63_, i_52_), i_58_, i_62_);
                                }
                            }
                        }
                    }
                }
            }
            for (int stz = 1; stz < (maxTileZ - 1); stz++) {
                for (int stx = 1; stx < (maxTileX - 1); stx++) {
                    scene.setDrawPlane(plane, stx, stz, getDrawPlane(plane, stx, stz));
                }
            }
        }
        scene.method305(-10, 64, -50, 768, -50);
        for (int i_66_ = 0; i_66_ < maxTileX; i_66_++) {
            for (int i_67_ = 0; i_67_ < maxTileZ; i_67_++) {
                if ((planeTileFlags[1][i_66_][i_67_] & 0x2) == 2) {
                    scene.setBridge(i_66_, i_67_);
                }
            }
        }
        int i_68_ = 1;
        int i_69_ = 2;
        int i_70_ = 4;
        for (int i_71_ = 0; i_71_ < 4; i_71_++) {
            if (i_71_ > 0) {
                i_68_ <<= 3;
                i_69_ <<= 3;
                i_70_ <<= 3;
            }
            for (int plane = 0; plane <= i_71_; plane++) {
                for (int z = 0; z <= maxTileZ; z++) {
                    for (int x = 0; x <= maxTileX; x++) {
                        if ((planeOccludemap[plane][x][z] & i_68_) != 0) {
                            int i_75_ = z;
                            int i_76_ = z;
                            int i_77_ = plane;
                            int i_78_ = plane;
                            for (/**/; i_75_ > 0; i_75_--) {
                                if (((planeOccludemap[plane][x][i_75_ - 1]) & i_68_) == 0) {
                                    break;
                                }
                            }
                            for (/**/; i_76_ < maxTileZ; i_76_++) {
                                if (((planeOccludemap[plane][x][i_76_ + 1]) & i_68_) == 0) {
                                    break;
                                }
                            }
                            while_0_:
                            for (/**/; i_77_ > 0; i_77_--) {
                                for (int i_79_ = i_75_; i_79_ <= i_76_; i_79_++) {
                                    if (((planeOccludemap[i_77_ - 1][x][i_79_]) & i_68_) == 0) {
                                        break while_0_;
                                    }
                                }
                            }
                            while_1_:
                            for (/**/; i_78_ < i_71_; i_78_++) {
                                for (int i_80_ = i_75_; i_80_ <= i_76_; i_80_++) {
                                    if (((planeOccludemap[i_78_ + 1][x][i_80_]) & i_68_) == 0) {
                                        break while_1_;
                                    }
                                }
                            }
                            int i_81_ = ((i_78_ + 1) - i_77_) * ((i_76_ - i_75_) + 1);
                            if (i_81_ >= 8) {
                                int i_82_ = 240;
                                int i_83_ = (planeHeightmap[i_78_][x][i_75_] - i_82_);
                                int i_84_ = planeHeightmap[i_77_][x][i_75_];
                                Scene.method277(i_71_, x * 128, i_84_, x * 128, (i_76_ * 128) + 128, i_83_, i_75_ * 128, 1);
                                for (int i_85_ = i_77_; i_85_ <= i_78_; i_85_++) {
                                    for (int i_86_ = i_75_; i_86_ <= i_76_; i_86_++) {
                                        planeOccludemap[i_85_][x][i_86_] &= ~i_68_;
                                    }
                                }
                            }
                        }
                        if ((planeOccludemap[plane][x][z] & i_69_) != 0) {
                            int i_87_ = x;
                            int i_88_ = x;
                            int i_89_ = plane;
                            int i_90_ = plane;
                            for (/**/; i_87_ > 0; i_87_--) {
                                if (((planeOccludemap[plane][i_87_ - 1][z]) & i_69_) == 0) {
                                    break;
                                }
                            }
                            for (/**/; i_88_ < maxTileX; i_88_++) {
                                if (((planeOccludemap[plane][i_88_ + 1][z]) & i_69_) == 0) {
                                    break;
                                }
                            }
                            while_2_:
                            for (/**/; i_89_ > 0; i_89_--) {
                                for (int i_91_ = i_87_; i_91_ <= i_88_; i_91_++) {
                                    if (((planeOccludemap[i_89_ - 1][i_91_][z]) & i_69_) == 0) {
                                        break while_2_;
                                    }
                                }
                            }
                            while_3_:
                            for (/**/; i_90_ < i_71_; i_90_++) {
                                for (int i_92_ = i_87_; i_92_ <= i_88_; i_92_++) {
                                    if (((planeOccludemap[i_90_ + 1][i_92_][z]) & i_69_) == 0) {
                                        break while_3_;
                                    }
                                }
                            }
                            int i_93_ = ((i_90_ + 1) - i_89_) * ((i_88_ - i_87_) + 1);
                            if (i_93_ >= 8) {
                                int i_94_ = 240;
                                int i_95_ = (planeHeightmap[i_90_][i_87_][z] - i_94_);
                                int i_96_ = planeHeightmap[i_89_][i_87_][z];
                                Scene.method277(i_71_, i_87_ * 128, i_96_, (i_88_ * 128) + 128, z * 128, i_95_, z * 128, 2);
                                for (int i_97_ = i_89_; i_97_ <= i_90_; i_97_++) {
                                    for (int i_98_ = i_87_; i_98_ <= i_88_; i_98_++) {
                                        planeOccludemap[i_97_][i_98_][z] &= ~i_69_;
                                    }
                                }
                            }
                        }
                        if ((planeOccludemap[plane][x][z] & i_70_) != 0) {
                            int i_99_ = x;
                            int i_100_ = x;
                            int i_101_ = z;
                            int i_102_ = z;
                            for (/**/; i_101_ > 0; i_101_--) {
                                if (((planeOccludemap[plane][x][i_101_ - 1]) & i_70_) == 0) {
                                    break;
                                }
                            }
                            for (/**/; i_102_ < maxTileZ; i_102_++) {
                                if (((planeOccludemap[plane][x][i_102_ + 1]) & i_70_) == 0) {
                                    break;
                                }
                            }
                            while_4_:
                            for (/**/; i_99_ > 0; i_99_--) {
                                for (int i_103_ = i_101_; i_103_ <= i_102_; i_103_++) {
                                    if (((planeOccludemap[plane][i_99_ - 1][i_103_]) & i_70_) == 0) {
                                        break while_4_;
                                    }
                                }
                            }
                            while_5_:
                            for (/**/; i_100_ < maxTileX; i_100_++) {
                                for (int i_104_ = i_101_; i_104_ <= i_102_; i_104_++) {
                                    if (((planeOccludemap[plane][i_100_ + 1][i_104_]) & i_70_) == 0) {
                                        break while_5_;
                                    }
                                }
                            }
                            if ((((i_100_ - i_99_) + 1) * ((i_102_ - i_101_) + 1)) >= 4) {
                                int i_105_ = planeHeightmap[plane][i_99_][i_101_];
                                Scene.method277(i_71_, i_99_ * 128, i_105_, (i_100_ * 128) + 128, (i_102_ * 128) + 128, i_105_, i_101_ * 128, 4);
                                for (int i_106_ = i_99_; i_106_ <= i_100_; i_106_++) {
                                    for (int i_107_ = i_101_; i_107_ <= i_102_; i_107_++) {
                                        planeOccludemap[plane][i_106_][i_107_] &= ~i_70_;
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
                    planeShademap[0][i_118_][i_117_] = (byte) 127;
                    if ((i_118_ == i_116_) && (i_118_ > 0)) {
                        planeHeightmap[0][i_118_][i_117_] = planeHeightmap[0][i_118_ - 1][i_117_];
                    }
                    if ((i_118_ == (i_116_ + i_115_)) && (i_118_ < (maxTileX - 1))) {
                        planeHeightmap[0][i_118_][i_117_] = planeHeightmap[0][i_118_ + 1][i_117_];
                    }
                    if ((i_117_ == i) && (i_117_ > 0)) {
                        planeHeightmap[0][i_118_][i_117_] = planeHeightmap[0][i_118_][i_117_ - 1];
                    }
                    if ((i_117_ == (i + i_113_)) && (i_117_ < (maxTileZ - 1))) {
                        planeHeightmap[0][i_118_][i_117_] = planeHeightmap[0][i_118_][i_117_ + 1];
                    }
                }
            }
        }
    }

    public void method175(int z, Scene scene, SceneCollisionMap collision, int kind, int plane, int x, int locID, int rotation) {
        if (lowmem && ((planeTileFlags[0][x][z] & 0x2) == 0) && (((planeTileFlags[plane][x][z] & 0x10) != 0) || (getDrawPlane(plane, x, z) != anInt131))) {
            return;
        }
        if (plane < minPlane) {
            minPlane = plane;
        }
        int heightmapSW = planeHeightmap[plane][x][z];
        int heightmapSE = planeHeightmap[plane][x + 1][z];
        int heightmapNE = planeHeightmap[plane][x + 1][z + 1];
        int heightmapNW = planeHeightmap[plane][x][z + 1];
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

                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(22, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, rotation, 22, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }

                scene.addGroundDecoration(entity, plane, x, z, heightmapAverage, bitset, info);

                if (type.solid && type.interactable && (collision != null)) {
                    collision.addSolid(z, x);
                }
            }
        } else if ((kind == 10) || (kind == 11)) {
            Entity entity;

            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(10, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 10, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
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

                if (scene.add(entity, plane, x, z, heightmapAverage, width, height, yaw, bitset, info) && type.castShadow) {
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

                                if (shade > (planeShademap[plane][x + dx][z + dz])) {
                                    planeShademap[plane][x + dx][z + dz] = (byte) shade;
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

            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }

            scene.add(entity, plane, x, z, heightmapAverage, 1, 1, 0, bitset, info);

            if ((kind >= 12) && (kind <= 17) && (kind != 13) && (plane > 0)) {
                planeOccludemap[plane][x][z] |= 0x924;
            }

            if (type.solid && (collision != null)) {
                collision.add(type.blocksProjectiles, type.width, type.length, x, z, rotation);
            }
        } else if (kind == 0) {
            Entity entity;

            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(0, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 0, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, 0, null, plane, x, z, heightmapAverage, bitset, info);

            if (rotation == 0) {
                if (type.castShadow) {
                    planeShademap[plane][x][z] = (byte) 50;
                    planeShademap[plane][x][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    planeOccludemap[plane][x][z] |= 0x249;
                }
            } else if (rotation == 1) {
                if (type.castShadow) {
                    planeShademap[plane][x][z + 1] = (byte) 50;
                    planeShademap[plane][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    planeOccludemap[plane][x][z + 1] |= 0x492;
                }
            } else if (rotation == 2) {
                if (type.castShadow) {
                    planeShademap[plane][x + 1][z] = (byte) 50;
                    planeShademap[plane][x + 1][z + 1] = (byte) 50;
                }
                if (type.occludes) {
                    planeOccludemap[plane][x + 1][z] |= 0x249;
                }
            } else if (rotation == 3) {
                if (type.castShadow) {
                    planeShademap[plane][x][z] = (byte) 50;
                    planeShademap[plane][x + 1][z] = (byte) 50;
                }
                if (type.occludes) {
                    planeOccludemap[plane][x][z] |= 0x492;
                }
            }

            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }

            if (type.decorationPadding != 16) {
                scene.setWallDecorationOffset(plane, x, z, type.decorationPadding);
            }
        } else if (kind == 1) {
            Entity entity;

            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(1, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 1, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }

            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, plane, x, z, heightmapAverage, bitset, info);

            if (type.castShadow) {
                if (rotation == 0) {
                    planeShademap[plane][x][z + 1] = (byte) 50;
                } else if (rotation == 1) {
                    planeShademap[plane][x + 1][z + 1] = (byte) 50;
                } else if (rotation == 2) {
                    planeShademap[plane][x + 1][z] = (byte) 50;
                } else if (rotation == 3) {
                    planeShademap[plane][x][z] = (byte) 50;
                }
            }

            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
        } else if (kind == 2) {
            int i_137_ = (rotation + 1) & 0x3;
            Entity entity;
            Entity entity_138_;
            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(2, 4 + rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                entity_138_ = type.getModel(2, i_137_, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                entity_138_ = new LocEntity(locID, i_137_, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }
            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, WALL_ROTATION_OCCLUDE_TYPE[i_137_], entity_138_, plane, x, z, heightmapAverage, bitset, info);
            if (type.occludes) {
                if (rotation == 0) {
                    planeOccludemap[plane][x][z] |= 0x249;
                    planeOccludemap[plane][x][z + 1] |= 0x492;
                } else if (rotation == 1) {
                    planeOccludemap[plane][x][z + 1] |= 0x492;
                    planeOccludemap[plane][x + 1][z] |= 0x249;
                } else if (rotation == 2) {
                    planeOccludemap[plane][x + 1][z] |= 0x249;
                    planeOccludemap[plane][x][z] |= 0x492;
                } else if (rotation == 3) {
                    planeOccludemap[plane][x][z] |= 0x492;
                    planeOccludemap[plane][x][z] |= 0x249;
                }
            }
            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
            if (type.decorationPadding != 16) {
                scene.setWallDecorationOffset(plane, x, z, type.decorationPadding);
            }
        } else if (kind == 3) {
            Entity entity;
            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(3, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, 3, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }
            scene.setWall(WALL_ROTATION_OCCLUDE_TYPE_1[rotation], entity, 0, null, plane, x, z, heightmapAverage, bitset, info);
            if (type.castShadow) {
                if (rotation == 0) {
                    planeShademap[plane][x][z + 1] = (byte) 50;
                } else if (rotation == 1) {
                    planeShademap[plane][x + 1][z + 1] = (byte) 50;
                } else if (rotation == 2) {
                    planeShademap[plane][x + 1][z] = (byte) 50;
                } else if (rotation == 3) {
                    planeShademap[plane][x][z] = (byte) 50;
                }
            }
            if (type.solid && (collision != null)) {
                collision.addWall(z, rotation, x, kind, type.blocksProjectiles);
            }
        } else if (kind == 9) {
            Entity entity;
            if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                entity = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
            } else {
                entity = new LocEntity(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
            }
            scene.add(entity, plane, x, z, heightmapAverage, 1, 1, 0, bitset, info);
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
                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }
                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, plane, x, z, heightmapAverage, rotation * 512, 0, 0, bitset, info);
            } else if (kind == 5) {
                int i_142_ = 16;
                int i_143_ = scene.getWallBitset(plane, x, z);
                if (i_143_ > 0) {
                    i_142_ = LocType.get((i_143_ >> 14) & 0x7fff).decorationPadding;
                }
                Entity entity;
                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }
                scene.setWallDecoration(WALL_ROTATION_OCCLUDE_TYPE[rotation], entity, plane, x, z, heightmapAverage, rotation * 512, anIntArray137[rotation] * i_142_, anIntArray144[rotation] * i_142_, bitset, info);
            } else if (kind == 6) {
                Entity entity;
                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }
                scene.setWallDecoration(256, entity, plane, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            } else if (kind == 7) {
                Entity entity;
                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }
                scene.setWallDecoration(512, entity, plane, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            } else if (kind == 8) {
                Entity entity;
                if ((type.seqId == -1) && (type.overrideTypeIDs == null)) {
                    entity = type.getModel(4, 0, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);
                } else {
                    entity = new LocEntity(locID, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, type.seqId, true);
                }
                scene.setWallDecoration(768, entity, plane, x, z, heightmapAverage, rotation, 0, 0, bitset, info);
            }
        }
    }

    public int method177(int i, int i_156_, int i_157_) {
        if (i_157_ > 179) {
            i_156_ /= 2;
        }
        if (i_157_ > 192) {
            i_156_ /= 2;
        }
        if (i_157_ > 217) {
            i_156_ /= 2;
        }
        if (i_157_ > 243) {
            i_156_ /= 2;
        }
        return ((i / 4) << 10) + ((i_156_ / 32) << 7) + (i_157_ / 2);
    }

    public void method179(int i, int i_162_, SceneCollisionMap[] collisionMaps, int i_164_, int i_165_, byte[] is, int i_166_, int i_167_, int i_168_) {
        for (int i_169_ = 0; i_169_ < 8; i_169_++) {
            for (int i_170_ = 0; i_170_ < 8; i_170_++) {
                if (((i_164_ + i_169_) > 0) && ((i_164_ + i_169_) < 103) && ((i_168_ + i_170_) > 0) && ((i_168_ + i_170_) < 103)) {
                    collisionMaps[i_167_].flags[i_164_ + i_169_][(i_168_ + i_170_)] &= ~0x1000000;
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
            planeTileFlags[i_187_][i_186_][i] = (byte) 0;
            for (; ; ) {
                int i_191_ = buffer.get1U();
                if (i_191_ == 0) {
                    if (i_187_ == 0) {
                        planeHeightmap[0][i_186_][i] = -method172(932731 + i_186_ + i_190_, 556238 + i + i_185_) * 8;
                    } else {
                        planeHeightmap[i_187_][i_186_][i] = planeHeightmap[i_187_ - 1][i_186_][i] - 240;
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
                        planeHeightmap[0][i_186_][i] = -i_192_ * 8;
                    } else {
                        planeHeightmap[i_187_][i_186_][i] = (planeHeightmap[i_187_ - 1][i_186_][i] - (i_192_ * 8));
                        break;
                    }
                    break;
                }
                if (i_191_ <= 49) {
                    aByteArrayArrayArray130[i_187_][i_186_][i] = buffer.get1();
                    aByteArrayArrayArray136[i_187_][i_186_][i] = (byte) ((i_191_ - 2) / 4);
                    aByteArrayArrayArray148[i_187_][i_186_][i] = (byte) (((i_191_ - 2) + i_188_) & 0x3);
                } else if (i_191_ <= 81) {
                    planeTileFlags[i_187_][i_186_][i] = (byte) (i_191_ - 49);
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

    public int getDrawPlane(int plane, int stx, int stz) {
        if ((planeTileFlags[plane][stx][stz] & 0x8) != 0) {
            return 0;
        }
        if ((plane > 0) && ((planeTileFlags[1][stx][stz] & 0x2) != 0)) {
            return plane - 1;
        }
        return plane;
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
                        if ((planeTileFlags[1][i_213_][i_214_] & 0x2) == 2) {
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

    public int method185(int i, int i_220_) {
        if (i == -2) {
            return 12345678;
        }
        if (i == -1) {
            if (i_220_ < 0) {
                i_220_ = 0;
            } else if (i_220_ > 127) {
                i_220_ = 127;
            }
            i_220_ = 127 - i_220_;
            return i_220_;
        }
        i_220_ = (i_220_ * (i & 0x7f)) / 128;
        if (i_220_ < 2) {
            i_220_ = 2;
        } else if (i_220_ > 126) {
            i_220_ = 126;
        }
        return (i & 0xff80) + i_220_;
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
                    if ((planeTileFlags[1][i_275_][i_276_] & 0x2) == 2) {
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
