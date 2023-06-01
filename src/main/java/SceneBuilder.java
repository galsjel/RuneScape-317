/* Class7 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class SceneBuilder {

    public static final int[] WALL_DECORATION_ROTATION_FORWARD_X = {1, 0, -1, 0};
    public static final int[] WALL_DECORATION_ROTATION_FORWARD_Z = {0, -1, 0, 1};
    public static int randomHueOffset = (int) (Math.random() * 17.0) - 8;
    public static int level;
    public static int randomLightnessOffset = (int) (Math.random() * 33.0) - 16;
    public static int minLevel = 99;

    public static int noise(int x, int y) {
        int n = x + (y * 57);
        n = (n << 13) ^ n;
        return ((((n * ((n * n * 15731) + 789221)) + 1376312589) & 0x7fffffff) >> 19) & 0xff;
    }

    public static int perlin(int x, int z) {
        int value = (perlin(x + 45365, z + 91923, 4) - 128) + ((perlin(x + 10294, z + 37821, 2) - 128) >> 1) + ((perlin(x, z, 1) - 128) >> 2);
        value = (int) ((double) value * 0.3) + 35;

        if (value < 10) {
            value = 10;
        } else if (value > 60) {
            value = 60;
        }

        return value;
    }

    public static void prefetchLocs(Buffer buffer, OnDemand onDemand) {
        int locID = -1;

        for (; ; ) {
            int deltaID = buffer.readUSmart();

            if (deltaID == 0) {
                break;
            }

            locID += deltaID;
            Obj type = Obj.get(locID);
            type.prefetch(onDemand);

            while (buffer.readUSmart() != 0) {
                buffer.readU8();
            }
        }
    }

    public static int perlin(int x, int z, int scale) {
        int intX = x / scale;
        int intZ = z / scale;
        int fracX = x & (scale - 1);
        int fracZ = z & (scale - 1);
        int v1 = smoothNoise(intX, intZ);
        int v2 = smoothNoise(intX + 1, intZ);
        int v3 = smoothNoise(intX, intZ + 1);
        int v4 = smoothNoise(intX + 1, intZ + 1);
        int i1 = interpolate(v1, v2, fracX, scale);
        int i2 = interpolate(v3, v4, fracX, scale);
        return interpolate(i1, i2, fracZ, scale);
    }

    /**
     * Used to determine if a LocType's models have been loaded.
     *
     * @param locID the loc type id.
     * @param kind  the loc kind.
     * @return <code>true</code> if the loc models are ready.
     */
    public static boolean isLocReady(int locID, int kind) {
        Obj type = Obj.get(locID);
        if (kind == 11) {
            kind = 10;
        }
        if ((kind >= 5) && (kind <= 8)) {
            kind = 4;
        }
        return type.validate(kind);
    }

    public static int interpolate(int a, int b, int x, int scale) {
        int f = (65536 - Draw3D.cos[(x * 1024) / scale]) >> 1;
        return ((a * (65536 - f)) >> 16) + ((b * f) >> 16);
    }

    public static int smoothNoise(int x, int y) {
        int corners = noise(x - 1, y - 1) + noise(x + 1, y - 1) + noise(x - 1, y + 1) + noise(x + 1, y + 1);
        int sides = noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(x, y + 1);
        int center = noise(x, y);
        return (corners / 16) + (sides / 8) + (center / 4);
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

    public static void add_object(Scene scene, int rotation, int z, int object_type, int tile_level, CollisionMap collision, int[][][] level_heightmap, int x, int object_id, int object_level) {
        int y_sw = level_heightmap[tile_level][x][z];
        int y_se = level_heightmap[tile_level][x + 1][z];
        int y_ne = level_heightmap[tile_level][x + 1][z + 1];
        int y_nw = level_heightmap[tile_level][x][z + 1];
        int y_avg = (y_sw + y_se + y_ne + y_nw) >> 2;

        Obj obj = Obj.get(object_id);
        int bitset = x + (z << 7) + (object_id << 14) + 0x40000000;

        if (!obj.interactable) {
            bitset += 0x80000000;
        }

        byte object_info = (byte) ((rotation << 6) + object_type);

        if (object_type == Obj.TYPE_GROUND_DECOR) {
            set_ground_decoration(scene, rotation, z, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if ((object_type == Obj.TYPE_CENTREPIECE) || (object_type == Obj.TYPE_CENTREPIECE_DIAGONAL)) {
            add_centrepiece(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type >= Obj.TYPE_ROOF_STRAIGHT) {
            add_roof_or_diagonal_wall(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == Obj.TYPE_WALL_STRAIGHT) {
            add_wall_straight(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == Obj.TYPE_WALL_CORNER_DIAGONAL) {
            add_wall_corner_diagonal(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == Obj.TYPE_WALL_L) {
            set_wall_l(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == Obj.TYPE_WALL_SQUARE_CORNER) {
            set_wall_square_corner(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == Obj.TYPE_WALL_DIAGONAL) {
            add_roof_or_diagonal_wall(scene, rotation, z, object_type, collision, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else {
            if (obj.hill_skew) {
                if (rotation == 1) {
                    int tmp = y_nw;
                    y_nw = y_ne;
                    y_ne = y_se;
                    y_se = y_sw;
                    y_sw = tmp;
                } else if (rotation == 2) {
                    int tmp = y_nw;
                    y_nw = y_se;
                    y_se = tmp;
                    tmp = y_ne;
                    y_ne = y_sw;
                    y_sw = tmp;
                } else if (rotation == 3) {
                    int tmp = y_nw;
                    y_nw = y_sw;
                    y_sw = y_se;
                    y_se = y_ne;
                    y_ne = tmp;
                }
            }

            if (object_type == Obj.TYPE_WALLDECOR_STRAIGHT) {
                set_wall_decoration(scene, Scene.ROTATION_WALL_TYPE[rotation], obj, object_id, bitset, object_info, object_level, x, z, rotation * 512, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == Obj.TYPE_WALLDECOR_STRAIGHT_OFFSET) {
                set_wall_decoration_offset(scene, rotation, z, x, object_id, object_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
            } else if (object_type == Obj.TYPE_WALLDECOR_DIAGONAL_NOOFFSET) {
                set_wall_decoration(scene, 0x100, obj, object_id, bitset, object_info, object_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == Obj.TYPE_WALLDECOR_DIAGONAL_OFFSET) {
                set_wall_decoration(scene, 0x200, obj, object_id, bitset, object_info, object_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == Obj.TYPE_WALLDECOR_DIAGONAL_BOTH) {
                set_wall_decoration(scene, 0x300, obj, object_id, bitset, object_info, object_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            }
        }
    }

    private static void set_ground_decoration(Scene scene, int rotation, int z, CollisionMap collision, int x, int locID, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        set_ground_decoration(scene, rotation, z, x, locID, level, y_sw, y_se, y_ne, y_nw, y, object, bitset, info);

        if (object.block_entity && object.interactable) {
            collision.set_solid(x, z);
        }
    }

    private static void set_ground_decoration(Scene scene, int rotation, int z, int x, int locID, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        Drawable drawable;

        if ((object.animation == -1) && (object.overrides == null)) {
            drawable = object.getModel(22, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(locID, rotation, 22, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }

        scene.set_ground_decoration(drawable, level, x, z, y, bitset, info);
    }

    private static void add_centrepiece(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int locID, int level, int heightSW, int heightSE, int heightNE, int heightNW, int y, Obj object, int bitset, byte info) {
        Drawable drawable = make_centrepiece(locID, rotation, heightSW, heightSE, heightNE, heightNW, object);

        if (drawable != null) {
            int yaw = 0;

            if (kind == 11) {
                yaw += 256;
            }

            int width;
            int length;

            if ((rotation == 1) || (rotation == 3)) {
                width = object.length;
                length = object.width;
            } else {
                width = object.width;
                length = object.length;
            }

            scene.push_static(drawable, level, x, z, y, width, length, yaw, bitset, info);
        }

        if (object.block_entity) {
            collision.add(object.block_projectile, object.width, object.length, x, z, rotation);
        }
    }

    private static void add_wall_straight(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int locID, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        set_wall_straight(scene, rotation, z, x, locID, level, y_sw, y_se, y_ne, y_nw, y, object, bitset, info);

        if (object.block_entity) {
            collision.set_wall(x, z, kind, rotation, object.block_projectile);
        }
    }

    private static void set_wall_straight(Scene scene, int rotation, int z, int x, int locID, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        Drawable drawable;

        if ((object.animation == -1) && (object.overrides == null)) {
            drawable = object.getModel(0, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(locID, rotation, 0, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }

        scene.set_wall(Scene.ROTATION_WALL_TYPE[rotation], drawable, 0, null, level, x, z, y, bitset, info);
    }

    private static void add_wall_corner_diagonal(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int locID, int level, int heightSW, int heightSE, int heightNE, int heightNW, int y, Obj loc, int bitset, byte info) {
        set_wall_corner_diagonal(scene, locID, rotation, level, x, z, heightSW, heightSE, heightNE, heightNW, y, loc, bitset, info);

        if (loc.block_entity) {
            collision.set_wall(x, z, kind, rotation, loc.block_projectile);
        }
    }

    private static void set_wall_l(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int locID, int level, int y_sw, int y_se, int y_ne, int y_nw, int y_avg, Obj object, int bitset, byte info) {
        set_wall_l(scene, locID, rotation, level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, object, bitset, info);

        if (object.block_entity) {
            collision.set_wall(x, z, kind, rotation, object.block_projectile);
        }
    }

    private static void set_wall_square_corner(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int object_id, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        set_wall_square_corner(scene, rotation, z, x, object_id, level, y_sw, y_se, y_ne, y_nw, y, object, bitset, info);

        if (object.block_entity) {
            collision.set_wall(x, z, kind, rotation, object.block_projectile);
        }
    }

    private static void set_wall_square_corner(Scene scene, int rotation, int z, int x, int object_id, int level, int y_sw, int y_se, int y_ne, int y_nw, int y, Obj object, int bitset, byte info) {
        Drawable drawable;

        if ((object.animation == -1) && (object.overrides == null)) {
            drawable = object.getModel(3, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(object_id, rotation, 3, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }

        scene.set_wall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], drawable, 0, null, level, x, z, y, bitset, info);
    }

    private static void add_roof_or_diagonal_wall(Scene scene, int rotation, int z, int kind, CollisionMap collision, int x, int locID, int level, int heightSW, int heightSE, int heightNE, int heightNW, int y, Obj loc, int bitset, byte info) {
        push_object(scene, locID, kind, rotation, level, x, z, heightSW, heightSE, heightNE, heightNW, y, loc, bitset, info);

        if (loc.block_entity) {
            collision.add(loc.block_projectile, loc.width, loc.length, x, z, rotation);
        }
    }

    private static void set_wall_decoration_offset(Scene scene, int rotation, int z, int x, int locID, int level, int heightSW, int heightSE, int heightNE, int heightNW, int y, Obj loc, int bitset, byte info) {
        int offset = 16;
        int wallBitset = scene.getWallBitset(level, x, z);

        if (wallBitset > 0) {
            offset = Obj.get((wallBitset >> 14) & 0x7fff).wall_offset;
        }

        Drawable drawable = make_wall_decoration(loc, locID, heightSW, heightSE, heightNE, heightNW);
        scene.setWallDecoration(Scene.ROTATION_WALL_TYPE[rotation], drawable, level, x, z, y, rotation * 512, WALL_DECORATION_ROTATION_FORWARD_X[rotation] * offset, WALL_DECORATION_ROTATION_FORWARD_Z[rotation] * offset, bitset, info);
    }

    /**
     * Reads the Locs from the provided data and determines if their models are available. The origin coordinate is used
     * to determine if a loc would be excluded from the scene, therefor not required to be validated. The chain of calls
     * eventually invokes {@link Model#loaded(int)} which causes the {@link OnDemand} to do its job.
     *
     * @param data    the data
     * @param originX the region origin x in the scene.
     * @param originZ the region origin z in the scene.
     * @return <code>true</code> if all locs are valid.
     */
    public static boolean validateLocs(byte[] data, int originX, int originZ) {
        boolean ok = true;
        Buffer buffer = new Buffer(data);
        int locID = -1;

        for (; ; ) {
            int deltaID = buffer.readUSmart();
            if (deltaID == 0) {
                break;
            }

            locID += deltaID;

            int pos = 0;
            boolean skip = false;

            // this loop is for the same Loc ID.
            for (; ; ) {
                if (skip) {
                    if (buffer.readUSmart() == 0) {
                        break;
                    }
                    buffer.readU8();
                } else {
                    int deltaPos = buffer.readUSmart();

                    if (deltaPos == 0) {
                        break;
                    }

                    pos += deltaPos - 1;

                    int z = pos & 0x3f;
                    int x = (pos >> 6) & 0x3f;

                    int kind = buffer.readU8() >> 2;
                    int localX = x + originX;
                    int localZ = z + originZ;

                    if ((localX > 0) && (localZ > 0) && (localX < 103) && (localZ < 103)) {
                        Obj type = Obj.get(locID);

                        if ((kind != 22) || type.interactable || type.important) {
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
    public final int[][][] level_heightmap;
    public final byte[][][] levelTileOverlayIDs;
    public final byte[][][] level_shademap;
    public final int[][][] level_occludemap;
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
        this.level_heightmap = levelHeightmap;
        this.levelTileFlags = levelTileFlags;
        levelTileUnderlayIDs = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayIDs = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayShape = new byte[4][this.maxTileX][this.maxTileZ];
        levelTileOverlayRotation = new byte[4][this.maxTileX][this.maxTileZ];
        level_occludemap = new int[4][this.maxTileX + 1][this.maxTileZ + 1];
        level_shademap = new byte[4][this.maxTileX + 1][this.maxTileZ + 1];
        levelLightmap = new int[this.maxTileX + 1][this.maxTileZ + 1];
        blendChroma = new int[this.maxTileZ];
        blendSaturation = new int[this.maxTileZ];
        blendLightness = new int[this.maxTileZ];
        blendLuminance = new int[this.maxTileZ];
        blendMagnitude = new int[this.maxTileZ];
    }

    public void build(CollisionMap[] levelCollisionMaps, Scene scene) {
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

    private void applyBlockFlags(CollisionMap[] levelCollisionMaps) {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    // solid
                    if ((levelTileFlags[level][x][z] & 0x1) == 1) {
                        int trueLevel = level;

                        // bridge
                        if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                            trueLevel--;
                        }

                        if (trueLevel >= 0) {
                            levelCollisionMaps[trueLevel].set_solid(x, z);
                        }
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
                        Flo flo = Flo.instances[floID - 1];
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
                        Flo flo = Flo.instances[floID - 1];
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

                    if ((z0 < 1) || (z0 >= (maxTileZ - 1))) {
                        continue;
                    }

                    if (level < minLevel) {
                        minLevel = level;
                    }

                    int underlayID = levelTileUnderlayIDs[level][x0][z0] & 0xff;
                    int overlayID = levelTileOverlayIDs[level][x0][z0] & 0xff;

                    if ((underlayID == 0) && (overlayID == 0)) {
                        continue;
                    }

                    int heightSW = level_heightmap[level][x0][z0];
                    int heightSE = level_heightmap[level][x0 + 1][z0];
                    int heightNE = level_heightmap[level][x0 + 1][z0 + 1];
                    int heightNW = level_heightmap[level][x0][z0 + 1];

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

                        baseColor = Flo.decimateHSL(hue, saturation, lightness);

                        hue = (hue + randomHueOffset) & 0xff;
                        lightness += randomLightnessOffset;

                        if (lightness < 0) {
                            lightness = 0;
                        } else if (lightness > 255) {
                            lightness = 255;
                        }

                        tintColor = Flo.decimateHSL(hue, saturation, lightness);
                    }

                    if (level > 0) {
                        boolean occludes = (underlayID != 0) || (levelTileOverlayShape[level][x0][z0] == 0);

                        if ((overlayID > 0) && !Flo.instances[overlayID - 1].occlude) {
                            occludes = false;
                        }

                        // occludes && flat
                        if (occludes && (heightSW == heightSE) && (heightSW == heightNE) && (heightSW == heightNW)) {
                            level_occludemap[level][x0][z0] |= 0b100_100_100_100;
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
                        Flo flo = Flo.instances[overlayID - 1];
                        int textureID = flo.texture;
                        int rgb;
                        int hsl;

                        if (textureID >= 0) {
                            rgb = Draw3D.getAverageTextureRGB(textureID);
                            hsl = -1;
                        } else if (flo.rgb == 0xFF00FF) {
                            rgb = 0;
                            hsl = -2;
                            textureID = -1;
                        } else {
                            hsl = Flo.decimateHSL(flo.hue, flo.saturation, flo.lightness);
                            rgb = Draw3D.palette[adjustLightness(flo.hsl, 96)];
                        }

                        scene.setTile(level, x0, z0, shape, rotation, textureID, heightSW, heightSE, heightNE, heightNW, mulHSL(baseColor, lightSW), mulHSL(baseColor, lightSE), mulHSL(baseColor, lightNE), mulHSL(baseColor, lightNW), adjustLightness(hsl, lightSW), adjustLightness(hsl, lightSE), adjustLightness(hsl, lightNE), adjustLightness(hsl, lightNW), shadeColor, rgb);
                    }
                }
            }
        }
    }

    private void buildLandscapeLighting(int level) {
        byte[][] shademap = level_shademap[level];
        int lightAmbient = 96;
        int lightAttenuation = 768;
        int lightX = -50;
        int lightY = -10;
        int lightZ = -50;
        int lightMagnitude = (lightAttenuation * (int) Math.sqrt((lightX * lightX) + (lightY * lightY) + (lightZ * lightZ))) >> 8;

        for (int z = 1; z < (maxTileZ - 1); z++) {
            for (int x = 1; x < (maxTileX - 1); x++) {
                int dx = level_heightmap[level][x + 1][z] - level_heightmap[level][x - 1][z];
                int dz = level_heightmap[level][x][z + 1] - level_heightmap[level][x][z - 1];
                int len = (int) Math.sqrt((dx * dx) + 65536 + (dz * dz));
                int normalX = (dx << 8) / len;
                int normalY = 65536 / len;
                int normalZ = (dz << 8) / len;
                int light = lightAmbient + (((lightX * normalX) + (lightY * normalY) + (lightZ * normalZ)) / lightMagnitude);
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

        for (int topLevel = 0; topLevel < 4; topLevel++) {
            if (topLevel > 0) {
                wall0 <<= 3;
                wall1 <<= 3;
                floor <<= 3;
            }

            for (int level = 0; level <= topLevel; level++) {
                for (int tileZ = 0; tileZ <= maxTileZ; tileZ++) {
                    for (int tileX = 0; tileX <= maxTileX; tileX++) {
                        buildWallOccludersX(wall0, topLevel, level, tileX, tileZ);
                        buildWallOccludersZ(wall1, topLevel, level, tileX, tileZ);
                        buildFloorOccluders(floor, topLevel, level, tileX, tileZ);
                    }
                }
            }
        }
    }

    private void buildWallOccludersX(int wall0, int topLevel, int level, int tileX, int tileZ) {
        if ((level_occludemap[level][tileX][tileZ] & wall0) != 0) {
            int minTileZ = tileZ;
            int maxTileZ = tileZ;
            int minLevel = level;
            int maxLevel = level;

            for (/**/; minTileZ > 0; minTileZ--) {
                if ((level_occludemap[level][tileX][minTileZ - 1] & wall0) == 0) {
                    break;
                }
            }

            for (/**/; maxTileZ < this.maxTileZ; maxTileZ++) {
                if ((level_occludemap[level][tileX][maxTileZ + 1] & wall0) == 0) {
                    break;
                }
            }

            find_min_level:
            for (/**/; minLevel > 0; minLevel--) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((level_occludemap[minLevel - 1][tileX][z] & wall0) == 0) {
                        break find_min_level;
                    }
                }
            }

            find_max_level:
            for (/**/; maxLevel < topLevel; maxLevel++) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((level_occludemap[maxLevel + 1][tileX][z] & wall0) == 0) {
                        break find_max_level;
                    }
                }
            }

            int area = ((maxLevel + 1) - minLevel) * ((maxTileZ - minTileZ) + 1);

            if (area >= 8) {
                int minY = level_heightmap[maxLevel][tileX][minTileZ] - 240;
                int maxY = level_heightmap[minLevel][tileX][minTileZ];

                Scene.addOccluder(topLevel, tileX * 128, minY, minTileZ * 128, tileX * 128, maxY, (maxTileZ * 128) + 128, SceneOccluder.TYPE_WALL_X);

                for (int l = minLevel; l <= maxLevel; l++) {
                    for (int z = minTileZ; z <= maxTileZ; z++) {
                        level_occludemap[l][tileX][z] &= ~wall0;
                    }
                }
            }
        }
    }

    private void buildWallOccludersZ(int wall1, int topLevel, int level, int tileX, int tileZ) {
        if ((level_occludemap[level][tileX][tileZ] & wall1) != 0) {
            int minTileX = tileX;
            int maxTileX = tileX;
            int minLevel = level;
            int maxLevel = level;

            for (/**/; minTileX > 0; minTileX--) {
                if ((level_occludemap[level][minTileX - 1][tileZ] & wall1) == 0) {
                    break;
                }
            }
            for (/**/; maxTileX < this.maxTileX; maxTileX++) {
                if ((level_occludemap[level][maxTileX + 1][tileZ] & wall1) == 0) {
                    break;
                }
            }

            find_min_level:
            for (/**/; minLevel > 0; minLevel--) {
                for (int x = minTileX; x <= maxTileX; x++) {
                    if ((level_occludemap[minLevel - 1][x][tileZ] & wall1) == 0) {
                        break find_min_level;
                    }
                }
            }

            find_max_level:
            for (/**/; maxLevel < topLevel; maxLevel++) {
                for (int x = minTileX; x <= maxTileX; x++) {
                    if ((level_occludemap[maxLevel + 1][x][tileZ] & wall1) == 0) {
                        break find_max_level;
                    }
                }
            }

            int area = ((maxLevel + 1) - minLevel) * ((maxTileX - minTileX) + 1);

            if (area >= 8) {
                int minY = level_heightmap[maxLevel][minTileX][tileZ] - 240;
                int maxY = level_heightmap[minLevel][minTileX][tileZ];

                Scene.addOccluder(topLevel, minTileX * 128, minY, tileZ * 128, (maxTileX * 128) + 128, maxY, tileZ * 128, SceneOccluder.TYPE_WALL_Z);

                for (int l = minLevel; l <= maxLevel; l++) {
                    for (int x = minTileX; x <= maxTileX; x++) {
                        level_occludemap[l][x][tileZ] &= ~wall1;
                    }
                }
            }
        }
    }

    private void buildFloorOccluders(int floor, int topLevel, int level, int tileX, int tileZ) {
        if ((level_occludemap[level][tileX][tileZ] & floor) != 0) {
            int minTileX = tileX;
            int maxTileX = tileX;
            int minTileZ = tileZ;
            int maxTileZ = tileZ;

            for (/**/; minTileZ > 0; minTileZ--) {
                if ((level_occludemap[level][tileX][minTileZ - 1] & floor) == 0) {
                    break;
                }
            }
            for (/**/; maxTileZ < this.maxTileZ; maxTileZ++) {
                if ((level_occludemap[level][tileX][maxTileZ + 1] & floor) == 0) {
                    break;
                }
            }

            find_min_tile_x:
            for (/**/; minTileX > 0; minTileX--) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((level_occludemap[level][minTileX - 1][z] & floor) == 0) {
                        break find_min_tile_x;
                    }
                }
            }

            find_max_tile_x:
            for (/**/; maxTileX < this.maxTileX; maxTileX++) {
                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((level_occludemap[level][maxTileX + 1][z] & floor) == 0) {
                        break find_max_tile_x;
                    }
                }
            }

            if ((((maxTileX - minTileX) + 1) * ((maxTileZ - minTileZ) + 1)) >= 8) {
                int y = level_heightmap[level][minTileX][minTileZ];

                Scene.addOccluder(topLevel, minTileX * 128, y, minTileZ * 128, (maxTileX * 128) + 128, y, (maxTileZ * 128) + 128, SceneOccluder.TYPE_GROUND);

                for (int x = minTileX; x <= maxTileX; x++) {
                    for (int z = minTileZ; z <= maxTileZ; z++) {
                        level_occludemap[level][x][z] &= ~floor;
                    }
                }
            }
        }
    }

    /**
     * Flattens the perimeter of the provided area.
     *
     * @param tileX     the area x.
     * @param tileZ     the area z.
     * @param tileSizeX the area size x.
     * @param tileSizeZ the area size z.
     */
    public void stitchHeightmap(int tileX, int tileZ, int tileSizeX, int tileSizeZ) {
        for (int z = tileZ; z <= (tileZ + tileSizeZ); z++) {
            for (int x = tileX; x <= (tileX + tileSizeX); x++) {
                if ((x < 0) || (x >= maxTileX) || (z < 0) || (z >= maxTileZ)) {
                    continue;
                }

                level_shademap[0][x][z] = (byte) 127;

                if ((x == tileX) && (x > 0)) {
                    level_heightmap[0][x][z] = level_heightmap[0][x - 1][z];
                }

                if ((x == (tileX + tileSizeX)) && (x < (maxTileX - 1))) {
                    level_heightmap[0][x][z] = level_heightmap[0][x + 1][z];
                }

                if ((z == tileZ) && (z > 0)) {
                    level_heightmap[0][x][z] = level_heightmap[0][x][z - 1];
                }

                if ((z == (tileZ + tileSizeZ)) && (z < (maxTileZ - 1))) {
                    level_heightmap[0][x][z] = level_heightmap[0][x][z + 1];
                }
            }
        }
    }

    public void add_object(Scene scene, CollisionMap collision, int object_id, int object_type, int rotation, int tile_level, int x, int z) {
        if (tile_level < minLevel) {
            minLevel = tile_level;
        }

        int y_sw = level_heightmap[tile_level][x][z];
        int y_se = level_heightmap[tile_level][x + 1][z];
        int y_ne = level_heightmap[tile_level][x + 1][z + 1];
        int y_nw = level_heightmap[tile_level][x][z + 1];
        int y_avg = (y_sw + y_se + y_ne + y_nw) >> 2;

        Obj obj = Obj.get(object_id);
        int bitset = x + (z << 7) + (object_id << 14) + 0x40000000;

        if (!obj.interactable) {
            bitset += 0x80000000;
        }

        byte object_info = (byte) ((rotation << 6) + object_type);

        if (object_type == 22) {
            set_ground_decoration(scene, collision, object_id, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if ((object_type == 10) || (object_type == 11)) {
            add_centrepiece(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type >= 12) {
            add_roof(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == 0) {
            add_wall(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == 1) {
            add_wall_corner_diagonal(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == 2) {
            add_wall_L(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == 3) {
            add_wall_square_corner(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else if (object_type == 9) {
            add_wall_diagonal(scene, collision, object_id, object_type, rotation, tile_level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
        } else {
            if (obj.hill_skew) {
                if (rotation == 1) {
                    int tmp = y_nw;
                    y_nw = y_ne;
                    y_ne = y_se;
                    y_se = y_sw;
                    y_sw = tmp;
                } else if (rotation == 2) {
                    int tmp = y_nw;
                    y_nw = y_se;
                    y_se = tmp;
                    tmp = y_ne;
                    y_ne = y_sw;
                    y_sw = tmp;
                } else if (rotation == 3) {
                    int tmp = y_nw;
                    y_nw = y_sw;
                    y_sw = y_se;
                    y_se = y_ne;
                    y_ne = tmp;
                }
            }

            if (object_type == 4) {
                set_wall_decoration(scene, Scene.ROTATION_WALL_TYPE[rotation], obj, object_id, bitset, object_info, tile_level, x, z, rotation * 512, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == 5) {
                set_wall_decoration_offset(scene, rotation, z, x, object_id, tile_level, y_sw, y_se, y_ne, y_nw, y_avg, obj, bitset, object_info);
            } else if (object_type == 6) {
                set_wall_decoration(scene, 0x100, obj, object_id, bitset, object_info, tile_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == 7) {
                set_wall_decoration(scene, 0x200, obj, object_id, bitset, object_info, tile_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            } else if (object_type == 8) {
                set_wall_decoration(scene, 0x300, obj, object_id, bitset, object_info, tile_level, x, z, rotation, y_sw, y_se, y_ne, y_nw, y_avg);
            }
        }
    }

    private static void set_ground_decoration(Scene scene, CollisionMap collision, int locID, int rotation, int level, int x, int z, int heightmapSW, int heightmapSE, int heightmapNE, int heightmapNW, int heightmapAverage, Obj type, int bitset, byte info) {
        set_ground_decoration(scene, rotation, z, x, locID, level, heightmapSW, heightmapSE, heightmapNE, heightmapNW, heightmapAverage, type, bitset, info);

        if (type.block_entity && type.interactable && (collision != null)) {
            collision.set_solid(x, z);
        }
    }

    private void add_centrepiece(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int heightmapAverage, Obj object, int bitset, byte info) {
        Drawable drawable = make_centrepiece(locID, rotation, y_sw, y_se, y_ne, y_nw, object);

        if (drawable != null) {
            int yaw = 0;

            if (kind == 11) {
                yaw += 256;
            }

            int width;
            int length;

            if ((rotation == 1) || (rotation == 3)) {
                width = object.length;
                length = object.width;
            } else {
                width = object.width;
                length = object.length;
            }

            if (scene.push_static(drawable, level, x, z, heightmapAverage, width, length, yaw, bitset, info) && object.cast_shadow) {
                Model model;

                if (drawable instanceof Model) {
                    model = (Model) drawable;
                } else {
                    model = object.getModel(10, rotation, y_sw, y_se, y_ne, y_nw, -1);
                }

                if (model != null) {
                    for (int dx = 0; dx <= width; dx++) {
                        for (int dz = 0; dz <= length; dz++) {
                            int shade = model.radius / 4;

                            if (shade > 30) {
                                shade = 30;
                            }

                            if (shade > level_shademap[level][x + dx][z + dz]) {
                                level_shademap[level][x + dx][z + dz] = (byte) shade;
                            }
                        }
                    }
                }
            }
        }

        if (object.block_entity && (collision != null)) {
            collision.add(object.block_projectile, object.width, object.length, x, z, rotation);
        }
    }

    private static Drawable make_centrepiece(int locID, int rotation, int y_sw, int y_se, int y_ne, int y_nw, Obj object) {
        Drawable drawable;

        if ((object.animation == -1) && (object.overrides == null)) {
            drawable = object.getModel(10, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(locID, rotation, 10, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }
        return drawable;
    }

    private void add_roof(Scene scene, CollisionMap collision, int object_id, int object_type, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int y_avg, Obj type, int bitset, byte info) {
        push_object(scene, object_id, object_type, rotation, level, x, z, y_sw, y_se, y_ne, y_nw, y_avg, type, bitset, info);

        if ((object_type >= 12) && (object_type <= 17) && (object_type != 13) && (level > 0)) {
            level_occludemap[level][x][z] |= 0b100_100_100_100;
        }

        if (type.block_entity && (collision != null)) {
            collision.add(type.block_projectile, type.width, type.length, x, z, rotation);
        }
    }

    private static void push_object(Scene scene, int obj_id, int kind, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int y_avg, Obj object, int bitset, byte info) {
        Drawable entity;

        if ((object.animation == -1) && (object.overrides == null)) {
            entity = object.getModel(kind, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            entity = new SceneObject(obj_id, rotation, kind, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }

        scene.push_static(entity, level, x, z, y_avg, 1, 1, 0, bitset, info);
    }

    private void add_wall(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int heightmapSW, int heightmapSE, int heightmapNE, int heightmapNW, int heightmapAverage, Obj type, int bitset, byte info) {
        set_wall_straight(scene, rotation, z, x, locID, level, heightmapSW, heightmapSE, heightmapNE, heightmapNW, heightmapAverage, type, bitset, info);

        if (rotation == 0) {
            if (type.cast_shadow) {
                level_shademap[level][x][z] = (byte) 50;
                level_shademap[level][x][z + 1] = (byte) 50;
            }
            if (type.occlude) {
                level_occludemap[level][x][z] |= 0b001_001_001_001;
            }
        } else if (rotation == 1) {
            if (type.cast_shadow) {
                level_shademap[level][x][z + 1] = (byte) 50;
                level_shademap[level][x + 1][z + 1] = (byte) 50;
            }
            if (type.occlude) {
                level_occludemap[level][x][z + 1] |= 0b010_010_010_010;
            }
        } else if (rotation == 2) {
            if (type.cast_shadow) {
                level_shademap[level][x + 1][z] = (byte) 50;
                level_shademap[level][x + 1][z + 1] = (byte) 50;
            }
            if (type.occlude) {
                level_occludemap[level][x + 1][z] |= 0b001_001_001_001;
            }
        } else if (rotation == 3) {
            if (type.cast_shadow) {
                level_shademap[level][x][z] = (byte) 50;
                level_shademap[level][x + 1][z] = (byte) 50;
            }
            if (type.occlude) {
                level_occludemap[level][x][z] |= 0b010_010_010_010;
            }
        }

        if (type.block_entity && (collision != null)) {
            collision.set_wall(x, z, kind, rotation, type.block_projectile);
        }

        if (type.wall_offset != 16) {
            scene.setWallDecorationOffset(level, x, z, type.wall_offset);
        }
    }

    private void add_wall_corner_diagonal(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int heightmapAverage, Obj type, int bitset, byte info) {
        set_wall_corner_diagonal(scene, locID, rotation, level, x, z, y_sw, y_se, y_ne, y_nw, heightmapAverage, type, bitset, info);
        apply_wall_square_corner_shadow(rotation, level, x, z, type);

        if (type.block_entity && (collision != null)) {
            collision.set_wall(x, z, kind, rotation, type.block_projectile);
        }
    }

    private static void set_wall_corner_diagonal(Scene scene, int locID, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int heightmapAverage, Obj type, int bitset, byte info) {
        Drawable drawable;

        if ((type.animation == -1) && (type.overrides == null)) {
            drawable = type.getModel(1, rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(locID, rotation, 1, y_se, y_ne, y_sw, y_nw, type.animation, true);
        }

        scene.set_wall(Scene.ROTATION_WALL_CORNER_TYPE[rotation], drawable, 0, null, level, x, z, heightmapAverage, bitset, info);
    }

    private void add_wall_L(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int heightmapAverage, Obj type, int bitset, byte info) {
        set_wall_l(scene, locID, rotation, level, x, z, y_sw, y_se, y_ne, y_nw, heightmapAverage, type, bitset, info);

        if (type.occlude) {
            if (rotation == 0) {
                level_occludemap[level][x][z] |= 0b001_001_001_001;
                level_occludemap[level][x][z + 1] |= 0b010_010_010_010;
            } else if (rotation == 1) {
                level_occludemap[level][x][z + 1] |= 0b010_010_010_010;
                level_occludemap[level][x + 1][z] |= 0b001_001_001_001;
            } else if (rotation == 2) {
                level_occludemap[level][x + 1][z] |= 0b001_001_001_001;
                level_occludemap[level][x][z] |= 0b010_010_010_010;
            } else if (rotation == 3) {
                level_occludemap[level][x][z] |= 0b010_010_010_010;
                level_occludemap[level][x][z] |= 0b001_001_001_001;
            }
        }

        if (type.block_entity && (collision != null)) {
            collision.set_wall(x, z, kind, rotation, type.block_projectile);
        }

        if (type.wall_offset != 16) {
            scene.setWallDecorationOffset(level, x, z, type.wall_offset);
        }
    }

    private static void set_wall_l(Scene scene, int locID, int rotation, int level, int x, int z, int y_sw, int y_se, int y_ne, int y_nw, int y_avg, Obj object, int bitset, byte info) {
        int next_rotation = (rotation + 1) & 0x3;

        Drawable a;
        Drawable b;

        if ((object.animation == -1) && (object.overrides == null)) {
            a = object.getModel(2, 4 + rotation, y_sw, y_se, y_ne, y_nw, -1);
            b = object.getModel(2, next_rotation, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            a = new SceneObject(locID, 4 + rotation, 2, y_se, y_ne, y_sw, y_nw, object.animation, true);
            b = new SceneObject(locID, next_rotation, 2, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }

        scene.set_wall(Scene.ROTATION_WALL_TYPE[rotation], a, Scene.ROTATION_WALL_TYPE[next_rotation], b, level, x, z, y_avg, bitset, info);
    }

    private void add_wall_square_corner(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int heightmapSW, int heightmapSE, int heightmapNE, int heightmapNW, int heightmapAverage, Obj type, int bitset, byte info) {
        set_wall_square_corner(scene, rotation, z, x, locID, level, heightmapSW, heightmapSE, heightmapNE, heightmapNW, heightmapAverage, type, bitset, info);

        apply_wall_square_corner_shadow(rotation, level, x, z, type);

        if (type.block_entity && (collision != null)) {
            collision.set_wall(x, z, kind, rotation, type.block_projectile);
        }
    }

    private void apply_wall_square_corner_shadow(int rotation, int level, int x, int z, Obj type) {
        if (type.cast_shadow) {
            if (rotation == 0) {
                level_shademap[level][x][z + 1] = (byte) 50;
            } else if (rotation == 1) {
                level_shademap[level][x + 1][z + 1] = (byte) 50;
            } else if (rotation == 2) {
                level_shademap[level][x + 1][z] = (byte) 50;
            } else if (rotation == 3) {
                level_shademap[level][x][z] = (byte) 50;
            }
        }
    }

    private static void add_wall_diagonal(Scene scene, CollisionMap collision, int locID, int kind, int rotation, int level, int x, int z, int heightmapSW, int heightmapSE, int heightmapNE, int heightmapNW, int heightmapAverage, Obj type, int bitset, byte info) {
        push_object(scene, locID, kind, rotation, level, x, z, heightmapSW, heightmapSE, heightmapNE, heightmapNW, heightmapAverage, type, bitset, info);

        if (type.block_entity && (collision != null)) {
            collision.add(type.block_projectile, type.width, type.length, x, z, rotation);
        }
    }

    private static void set_wall_decoration(Scene scene, int decoration_type, Obj object, int object_id, int object_bitset, byte object_info, int level, int x, int z, int rotation, int y_sw, int y_se, int y_ne, int y_nw, int y_avg) {
        Drawable drawable = make_wall_decoration(object, object_id, y_sw, y_se, y_ne, y_nw);
        scene.setWallDecoration(decoration_type, drawable, level, x, z, y_avg, rotation, 0, 0, object_bitset, object_info);
    }

    private static Drawable make_wall_decoration(Obj object, int object_id, int y_sw, int y_se, int y_ne, int y_nw) {
        Drawable drawable;
        if ((object.animation == -1) && (object.overrides == null)) {
            drawable = object.getModel(4, 0, y_sw, y_se, y_ne, y_nw, -1);
        } else {
            drawable = new SceneObject(object_id, 0, 4, y_se, y_ne, y_sw, y_nw, object.animation, true);
        }
        return drawable;
    }

    public void readChunkTiles(CollisionMap[] collisionMaps, byte[] data, int chunkX, int chunkZ, int mapLevel, int chunkRotation, int originX, int originZ, int level) {
        for (int dx = 0; dx < 8; dx++) {
            for (int dz = 0; dz < 8; dz++) {
                if (((originX + dx) > 0) && ((originX + dx) < 103) && ((originZ + dz) > 0) && ((originZ + dz) < 103)) {
                    collisionMaps[level].flags[originX + dx][originZ + dz] &= ~CollisionMap.FLAG_UNINITIALIZED;
                }
            }
        }
        Buffer in = new Buffer(data);
        for (int l = 0; l < 4; l++) {
            for (int x = 0; x < 64; x++) {
                for (int z = 0; z < 64; z++) {
                    if ((l == mapLevel) && (x >= chunkX) && (x < (chunkX + 8)) && (z >= chunkZ) && (z < (chunkZ + 8))) {
                        readTiles(in, 0, 0, level, originX + MapUtil.rotateX(x & 0x7, z & 0x7, chunkRotation), originZ + MapUtil.rotateZ(x & 0x7, z & 0x7, chunkRotation), chunkRotation);
                    } else {
                        readTiles(in, 0, 0, 0, -1, -1, 0);
                    }
                }
            }
        }
    }

    public void readTiles(byte[] data, int offsetZ, int offsetX, int originX, int originZ, CollisionMap[] collisionMaps) {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 64; x++) {
                for (int z = 0; z < 64; z++) {
                    if (((offsetX + x) > 0) && ((offsetX + x) < 103) && ((offsetZ + z) > 0) && ((offsetZ + z) < 103)) {
                        collisionMaps[level].flags[offsetX + x][offsetZ + z] &= ~CollisionMap.FLAG_UNINITIALIZED;
                    }
                }
            }
        }

        Buffer in = new Buffer(data);
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 64; x++) {
                for (int z = 0; z < 64; z++) {
                    readTiles(in, originX, originZ, level, x + offsetX, z + offsetZ, 0);
                }
            }
        }
    }

    public void readTiles(Buffer in, int originX, int originZ, int level, int x, int z, int mapRotation) {
        if ((x >= 0) && (x < 104) && (z >= 0) && (z < 104)) {
            levelTileFlags[level][x][z] = (byte) 0;

            for (; ; ) {
                int type = in.readU8();

                if (type == 0) {
                    if (level == 0) {
                        level_heightmap[0][x][z] = -perlin(932731 + x + originX, 556238 + z + originZ) * 8;
                    } else {
                        level_heightmap[level][x][z] = level_heightmap[level - 1][x][z] - 240;
                        break;
                    }
                    break;
                }

                if (type == 1) {
                    int height = in.readU8();

                    if (height == 1) {
                        height = 0;
                    }

                    if (level == 0) {
                        level_heightmap[0][x][z] = -height * 8;
                    } else {
                        level_heightmap[level][x][z] = level_heightmap[level - 1][x][z] - (height * 8);
                        break;
                    }
                    break;
                }

                if (type <= 49) {
                    levelTileOverlayIDs[level][x][z] = in.read8();
                    levelTileOverlayShape[level][x][z] = (byte) ((type - 2) / 4);
                    levelTileOverlayRotation[level][x][z] = (byte) (((type - 2) + mapRotation) & 0x3);
                } else if (type <= 81) {
                    levelTileFlags[level][x][z] = (byte) (type - 49);
                } else {
                    levelTileUnderlayIDs[level][x][z] = (byte) (type - 81);
                }
            }
        } else {
            for (; ; ) {
                int type = in.readU8();
                if (type == 0) {
                    break;
                }
                if (type == 1) {
                    in.readU8();
                    break;
                }
                if (type <= 49) {
                    in.readU8();
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

    public void readChunkLocs(CollisionMap[] collisionMaps, Scene scene, int mapLevel, int mapRotation, int mapChunkX, int mapChunkZ, int originX, int originZ, byte[] data, int level) {
        Buffer in = new Buffer(data);
        int locID = -1;

        while (true) {
            int deltaID = in.readUSmart();

            if (deltaID == 0) {
                break;
            }

            locID += deltaID;

            int locData = 0;

            while (true) {
                int deltaData = in.readUSmart();

                if (deltaData == 0) {
                    break;
                }

                locData += deltaData - 1;

                int locZ = locData & 0x3f;
                int locX = (locData >> 6) & 0x3f;
                int locLevel = locData >> 12;
                int locInfo = in.readU8();
                int locKind = locInfo >> 2;
                int locRotation = locInfo & 0x3;

                if ((locLevel != mapLevel) || (locX < mapChunkX) || (locX >= (mapChunkX + 8)) || (locZ < mapChunkZ) || (locZ >= (mapChunkZ + 8))) {
                    continue;
                }

                Obj loc = Obj.get(locID);
                int x = originX + MapUtil.rotateLocX(locX & 0x7, locZ & 0x7, loc.width, loc.length, mapRotation);
                int z = originZ + MapUtil.rotateLocZ(locX & 0x7, locZ & 0x7, loc.width, loc.length, mapRotation);

                if ((x <= 0) || (z <= 0) || (x >= 103) || (z >= 103)) {
                    continue;
                }

                int collisionLevel = locLevel;

                if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                    collisionLevel--;
                }

                CollisionMap collisionMap = null;

                if (collisionLevel >= 0) {
                    collisionMap = collisionMaps[collisionLevel];
                } else {
                    System.out.println("BAD? collisionLevel = " + collisionLevel);
                }

                add_object(scene, collisionMap, locID, locKind, (locRotation + mapRotation) & 0x3, level, x, z);
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

    public void readLocs(CollisionMap[] collisionMaps, Scene scene, int originX, int originZ, byte[] data) {
        Buffer in = new Buffer(data);
        int locID = -1;
        for (; ; ) {
            int deltaID = in.readUSmart();

            if (deltaID == 0) {
                break;
            }

            locID += deltaID;
            int locData = 0;

            for (; ; ) {
                int deltaData = in.readUSmart();

                if (deltaData == 0) {
                    break;
                }

                locData += deltaData - 1;
                int locZ = locData & 0x3f;
                int locX = (locData >> 6) & 0x3f;
                int locLevel = locData >> 12;
                int locInfo = in.readU8();
                int locKind = locInfo >> 2;
                int locRotation = locInfo & 0x3;
                int x = locX + originX;
                int z = locZ + originZ;

                if ((x > 0) && (z > 0) && (x < 103) && (z < 103)) {
                    int collisionLevel = locLevel;

                    if ((levelTileFlags[1][x][z] & 0x2) == 2) {
                        collisionLevel--;
                    }

                    CollisionMap collisionMap = null;

                    if (collisionLevel >= 0) {
                        collisionMap = collisionMaps[collisionLevel];
                    }

                    add_object(scene, collisionMap, locID, locKind, locRotation, locLevel, x, z);
                }
            }
        }
    }

}
