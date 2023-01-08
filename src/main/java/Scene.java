// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.util.Arrays;

public class Scene {

    public static final int[] WALL_DECORATION_INSET_X = {53, -53, -53, 53};
    public static final int[] WALL_DECORATION_INSET_Z = {-53, -53, 53, 53};
    public static final int[] WALL_DECORATION_OUTSET_X = {-45, 45, 45, -45};
    public static final int[] WALL_DECORATION_OUTSET_Z = {45, 45, -45, -45};
    /**
     * Occlusion flags for walls of kind 0, 2 and decorations 4, 5,
     */
    public static final int[] ROTATION_WALL_TYPE = {
            1 << 0,
            1 << 1,
            1 << 2,
            1 << 3,
    };
    /**
     * Occlusion flags for walls of type 1 and 3.
     */
    public static final int[] ROTATION_WALL_CORNER_TYPE = {
            1 << 4,
            1 << 5,
            1 << 6,
            1 << 7,
    };
    public static final int[] FRONT_WALL_TYPES = {
            0b00010011, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b00110111, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b00100110, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b10011011, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b11111111, // eyeTileX == tileX  &&  eyeTileZ == tileZ
            0b01101110, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b10001001, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b11001101, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b01001100, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] DIRECTION_ALLOW_WALL_CORNER_TYPE = {
            0b10100000, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b11000000, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b01010000, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b01100000, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b00000000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b10010000, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b01010000, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b00110000, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b10100000, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] BACK_WALL_TYPES = {
            0b01001100, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b00001000, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b10001001, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b00000100, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b00000000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b00000001, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b00100110, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b00000010, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b00010011, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] WALL_CORNER_TYPE_16_BLOCK_LOC_SPANS = {
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b0010, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b0010, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b0001, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b0001, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] WALL_CORNER_TYPE_32_BLOCK_LOC_SPANS = {
            0b0010, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b0010, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b0100, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b0100, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] WALL_CORNER_TYPE_64_BLOCK_LOC_SPANS = {
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b0100, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b0100, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b1000, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b1000, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] WALL_CORNER_TYPE_128_BLOCK_LOC_SPANS = {
            0b0001, // eyeTileX >  tileX  &&  eyeTileZ <  tileZ
            0b0001, // eyeTileX == tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX <  tileX  &&  eyeTileZ <  tileZ
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ == tileZ
            0b1000, // eyeTileX <  tileX  &&  eyeTileZ == tileZ
            0b0000, // eyeTileX >  tileX  &&  eyeTileZ >  tileZ
            0b0000, // eyeTileX =  tileX  &&  eyeTileZ >  tileZ
            0b1000, // eyeTileX <  tileX  &&  eyeTileZ >  tileZ
    };
    public static final int[] TEXTURE_HSL = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
    public static final int LEVEL_COUNT = 4;
    public static final SceneOccluder[] activeOccluders = new SceneOccluder[500];
    public static boolean lowmem = true;
    public static int tilesRemaining;
    public static int topLevel;
    public static int cycle;
    public static int minDrawTileX;
    public static int maxDrawTileX;
    public static int minDrawTileZ;
    public static int maxDrawTileZ;
    public static int eyeTileX;
    public static int eyeTileZ;
    public static int eyeX;
    public static int eyeY;
    public static int eyeZ;
    public static int sinEyePitch;
    public static int cosEyePitch;
    public static int sinEyeYaw;
    public static int cosEyeYaw;
    /**
     * Used to track locs being sorted for drawing on a per-tile basis.
     */
    public static SceneLoc[] locBuffer = new SceneLoc[100];
    public static boolean takingInput;
    public static int mouseX;
    public static int mouseY;
    public static int clickTileX = -1;
    public static int clickTileZ = -1;
    public static int[] levelOccluderCount = new int[LEVEL_COUNT];
    public static SceneOccluder[][] levelOccluders = new SceneOccluder[LEVEL_COUNT][1000];
    public static int activeOccluderCount;
    public static int activeWallOccluderCount;
    public static int activeGroundOccluderCount;
    public static int tilesCulled;
    public static DoublyLinkedList drawTileQueue = new DoublyLinkedList();
    public static boolean[][][][] visibilityMatrix = new boolean[8][32][51][51];
    public static boolean[][] visibilityMap;
    public static int viewportCenterX;
    public static int viewportCenterY;
    public static int viewportLeft;
    public static int viewportTop;
    public static int viewportRight;
    public static int viewportBottom;

    public static void unload() {
        locBuffer = null;
        levelOccluderCount = null;
        levelOccluders = null;
        drawTileQueue = null;
        visibilityMatrix = null;
        visibilityMap = null;
    }

    public static void addOccluder(int level, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
        SceneOccluder occluder = new SceneOccluder();
        occluder.minTileX = minX / 128;
        occluder.maxTileX = maxX / 128;
        occluder.minTileZ = minZ / 128;
        occluder.maxTileZ = maxZ / 128;
        occluder.type = type;
        occluder.minX = minX;
        occluder.maxX = maxX;
        occluder.minZ = minZ;
        occluder.maxZ = maxZ;
        occluder.minY = minY;
        occluder.maxY = maxY;
        levelOccluders[level][levelOccluderCount[level]++] = occluder;
    }

    public static void init(int viewportWidth, int viewportHeight) {
        viewportLeft = 0;
        viewportTop = 0;
        Scene.viewportRight = viewportWidth;
        Scene.viewportBottom = viewportHeight;
        viewportCenterX = viewportWidth / 2;
        viewportCenterY = viewportHeight / 2;
        initVisibilityMatrix();
    }

    /**
     * Populates the {@link Scene#visibilityMatrix} lookup table which provides a rough approximation for relative tile
     * visibility within a specific range of pitches and yaws.
     */
    private static void initVisibilityMatrix() {
        int[] pitchDistance = new int[9];
        for (int pitchLevel = 0; pitchLevel < 9; pitchLevel++) {
            int angle = 128 + (pitchLevel * 32) + 15;
            int distance = 600 + (angle * 3);
            pitchDistance[pitchLevel] = (distance * Draw3D.sin[angle]) >> 16;
        }

        boolean[][][][] matrix = new boolean[9][32][53][53];

        for (int pitch = 128; pitch <= 384; pitch += 32) {
            for (int yaw = 0; yaw < 2048; yaw += 64) {
                sinEyePitch = Model.sin[pitch];
                cosEyePitch = Model.cos[pitch];
                sinEyeYaw = Model.sin[yaw];
                cosEyeYaw = Model.cos[yaw];

                int pitchLevel = (pitch - 128) / 32;
                int yawLevel = yaw / 64;

                for (int dx = -26; dx <= 26; dx++) {
                    for (int dz = -26; dz <= 26; dz++) {
                        int x = dx * 128;
                        int z = dz * 128;
                        boolean visible = false;
                        for (int y = -500; y <= 800; y += 128) {
                            if (testPoint(pitchDistance[pitchLevel] + y, z, x)) {
                                visible = true;
                                break;
                            }
                        }
                        matrix[pitchLevel][yawLevel][dx + 25 + 1][dz + 25 + 1] = visible;
                    }
                }
            }
        }

        // One final pass to extend the visibility map up to 1 tile in any direction.
        for (int pitchLevel = 0; pitchLevel < 8; pitchLevel++) {
            for (int yawLevel = 0; yawLevel < 32; yawLevel++) {
                for (int x = -25; x < 25; x++) {
                    for (int z = -25; z < 25; z++) {
                        boolean visible = false;

                        check_area:
                        for (int dx = -1; dx <= 1; dx++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                if (matrix[pitchLevel][yawLevel][x + dx + 25 + 1][z + dz + 25 + 1]) {
                                    visible = true;
                                } else if (matrix[pitchLevel][(yawLevel + 1) % 31][x + dx + 25 + 1][z + dz + 25 + 1]) {
                                    visible = true;
                                } else if (matrix[pitchLevel + 1][yawLevel][x + dx + 25 + 1][z + dz + 25 + 1]) {
                                    visible = true;
                                } else {
                                    if (!matrix[pitchLevel + 1][(yawLevel + 1) % 31][x + dx + 25 + 1][z + dz + 25 + 1]) {
                                        continue;
                                    }
                                    visible = true;
                                }
                                break check_area;
                            }
                        }
                        Scene.visibilityMatrix[pitchLevel][yawLevel][x + 25][z + 25] = visible;
                    }
                }
            }
        }
    }

    public static boolean testPoint(int y, int z, int x) {
        int px = ((z * sinEyeYaw) + (x * cosEyeYaw)) >> 16;
        int tmp = ((z * cosEyeYaw) - (x * sinEyeYaw)) >> 16;
        int pz = ((y * sinEyePitch) + (tmp * cosEyePitch)) >> 16;
        int py = ((y * cosEyePitch) - (tmp * sinEyePitch)) >> 16;
        if ((pz < 50) || (pz > 3500)) {
            return false;
        }
        int viewportX = viewportCenterX + ((px << 9) / pz);
        int viewportY = viewportCenterY + ((py << 9) / pz);
        return (viewportX >= viewportLeft) && (viewportX <= viewportRight) && (viewportY >= viewportTop) && (viewportY <= viewportBottom);
    }

    public final int maxLevel;
    public final int maxTileX;
    public final int maxTileZ;
    public final int[][][] levelHeightmaps;
    public final SceneTile[][][] levelTiles;
    public final SceneLoc[] temporaryLocs = new SceneLoc[5000];
    public final int[][][] levelTileOcclusionCycles;
    public final int[] mergeIndexA = new int[10000];
    public final int[] mergeIndexB = new int[10000];
    public final int[][] MINIMAP_TILE_MASK = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
    public final int[][] MINIMAP_TILE_ROTATION_MAP = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
    public int minLevel;
    public int temporaryLocCount;
    public int tmpMergeIndex;

    public Scene(int maxTileZ, int maxTileX, int[][][] levelHeightmaps, int maxLevel) {
        this.maxLevel = maxLevel;
        this.maxTileX = maxTileX;
        this.maxTileZ = maxTileZ;
        levelTiles = new SceneTile[maxLevel][maxTileX][maxTileZ];
        levelTileOcclusionCycles = new int[maxLevel][maxTileX + 1][maxTileZ + 1];
        this.levelHeightmaps = levelHeightmaps;
        reset();
    }

    public void reset() {
        for (int level = 0; level < maxLevel; level++) {
            for (int stx = 0; stx < maxTileX; stx++) {
                for (int stz = 0; stz < maxTileZ; stz++) {
                    levelTiles[level][stx][stz] = null;
                }
            }
        }

        for (int l = 0; l < LEVEL_COUNT; l++) {
            for (int j1 = 0; j1 < levelOccluderCount[l]; j1++) {
                levelOccluders[l][j1] = null;
            }
            levelOccluderCount[l] = 0;
        }

        for (int i = 0; i < temporaryLocCount; i++) {
            temporaryLocs[i] = null;
        }

        temporaryLocCount = 0;
        Arrays.fill(locBuffer, null);
    }

    public void setMinLevel(int level) {
        minLevel = level;
        for (int stx = 0; stx < maxTileX; stx++) {
            for (int stz = 0; stz < maxTileZ; stz++) {
                if (levelTiles[level][stx][stz] == null) {
                    levelTiles[level][stx][stz] = new SceneTile(level, stx, stz);
                }
            }
        }
    }

    public void setBridge(int stx, int stz) {
        SceneTile ground = levelTiles[0][stx][stz];

        for (int level = 0; level < 3; level++) {
            SceneTile above = levelTiles[level][stx][stz] = levelTiles[level + 1][stx][stz];

            if (above == null) {
                continue;
            }

            above.level--;

            for (int i = 0; i < above.locCount; i++) {
                SceneLoc loc = above.locs[i];

                if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == stx) && (loc.minSceneTileZ == stz)) {
                    loc.level--;
                }
            }
        }

        if (levelTiles[0][stx][stz] == null) {
            levelTiles[0][stx][stz] = new SceneTile(0, stx, stz);
        }

        levelTiles[0][stx][stz].bridge = ground;
        levelTiles[3][stx][stz] = null;
    }

    public void setDrawLevel(int level, int stx, int stz, int drawLevel) {
        SceneTile tile = levelTiles[level][stx][stz];
        if (tile != null) {
            levelTiles[level][stx][stz].drawLevel = drawLevel;
        }
    }

    public void setTile(int level, int x, int z, int shape, int rotation, int textureID, int southwestY, int southeastY, int northeastY, int northwestY, int southwestColor1, int southeastColor1, int northeastColor1, int northwestColor1, int southwestColor2, int southeastColor2, int northeastColor2, int northwestColor2, int backgroundRGB, int foregroundRGB) {
        if (shape == 0) {
            SceneTileUnderlay underlay = new SceneTileUnderlay(southwestColor1, southeastColor1, northeastColor1, northwestColor1, -1, backgroundRGB, false);
            for (int l = level; l >= 0; l--) {
                if (levelTiles[l][x][z] == null) {
                    levelTiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            levelTiles[level][x][z].underlay = underlay;
        } else if (shape == 1) {
            SceneTileUnderlay underlay = new SceneTileUnderlay(southwestColor2, southeastColor2, northeastColor2, northwestColor2, textureID, foregroundRGB, (southwestY == southeastY) && (southwestY == northeastY) && (southwestY == northwestY));
            for (int l = level; l >= 0; l--) {
                if (levelTiles[l][x][z] == null) {
                    levelTiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            levelTiles[level][x][z].underlay = underlay;
        } else {
            SceneTileOverlay overlay = new SceneTileOverlay(z, southwestColor2, northwestColor1, northeastY, textureID, northeastColor2, rotation, southwestColor1, backgroundRGB, northeastColor1, northwestY, southeastY, southwestY, shape, northwestColor2, southeastColor2, southeastColor1, x, foregroundRGB);
            for (int l = level; l >= 0; l--) {
                if (levelTiles[l][x][z] == null) {
                    levelTiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            levelTiles[level][x][z].overlay = overlay;
        }
    }

    public void addGroundDecoration(Entity entity, int tileLevel, int tileX, int tileZ, int y, int bitset, byte info) {
        if (entity == null) {
            return;
        }
        SceneGroundDecoration decor = new SceneGroundDecoration();
        decor.entity = entity;
        decor.x = (tileX * 128) + 64;
        decor.z = (tileZ * 128) + 64;
        decor.y = y;
        decor.bitset = bitset;
        decor.info = info;
        if (levelTiles[tileLevel][tileX][tileZ] == null) {
            levelTiles[tileLevel][tileX][tileZ] = new SceneTile(tileLevel, tileX, tileZ);
        }
        levelTiles[tileLevel][tileX][tileZ].groundDecoration = decor;
    }

    public void addObjStack(Entity topObj, Entity bottomObj, Entity middleObj, int level, int stx, int stz, int y, int bitset) {
        SceneObjStack stack = new SceneObjStack();
        stack.x = (stx * 128) + 64;
        stack.z = (stz * 128) + 64;
        stack.y = y;
        stack.bitset = bitset;
        stack.topObj = topObj;
        stack.bottomObj = bottomObj;
        stack.middleObj = middleObj;

        int stackOffset = 0;

        SceneTile tile = levelTiles[level][stx][stz];

        if (tile != null) {
            for (int l = 0; l < tile.locCount; l++) {
                if (!(tile.locs[l].entity instanceof Model)) {
                    continue;
                }
                int height = ((Model) tile.locs[l].entity).objRaise;
                if (height > stackOffset) {
                    stackOffset = height;
                }
            }
        }

        stack.offset = stackOffset;

        if (levelTiles[level][stx][stz] == null) {
            levelTiles[level][stx][stz] = new SceneTile(level, stx, stz);
        }
        levelTiles[level][stx][stz].objStack = stack;
    }

    public void setWall(int typeA, Entity entityA, int typeB, Entity entityB, int level, int tileX, int tileZ, int y, int bitset, byte info) {
        if ((entityA == null) && (entityB == null)) {
            return;
        }
        SceneWall wall = new SceneWall();
        wall.bitset = bitset;
        wall.info = info;
        wall.x = (tileX * 128) + 64;
        wall.z = (tileZ * 128) + 64;
        wall.y = y;
        wall.entityA = entityA;
        wall.entityB = entityB;
        wall.typeA = typeA;
        wall.typeB = typeB;
        for (int l = level; l >= 0; l--) {
            if (levelTiles[l][tileX][tileZ] == null) {
                levelTiles[l][tileX][tileZ] = new SceneTile(l, tileX, tileZ);
            }
        }
        levelTiles[level][tileX][tileZ].wall = wall;
    }

    public void setWallDecoration(int type, Entity entity, int level, int tileX, int tileZ, int y, int rotation, int offsetX, int offsetZ, int bitset, byte info) {
        if (entity == null) {
            return;
        }
        SceneWallDecoration decor = new SceneWallDecoration();
        decor.bitset = bitset;
        decor.info = info;
        decor.x = (tileX * 128) + 64 + offsetX;
        decor.z = (tileZ * 128) + 64 + offsetZ;
        decor.y = y;
        decor.entity = entity;
        decor.type = type;
        decor.rotation = rotation;
        for (int p = level; p >= 0; p--) {
            if (levelTiles[p][tileX][tileZ] == null) {
                levelTiles[p][tileX][tileZ] = new SceneTile(p, tileX, tileZ);
            }
        }
        levelTiles[level][tileX][tileZ].wallDecoration = decor;
    }

    public boolean add(Entity entity, int level, int tileX, int tileZ, int y, int width, int length, int yaw, int bitset, byte info) {
        if (entity == null) {
            return true;
        } else {
            int sceneX = (tileX * 128) + (64 * width);
            int sceneZ = (tileZ * 128) + (64 * length);
            return add(entity, level, tileX, tileZ, width, length, sceneX, sceneZ, y, yaw, bitset, info, false);
        }
    }

    public boolean addTemporary(Entity entity, int level, int x, int z, int y, int yaw, int bitset, boolean forwardPadding, int padding) {
        if (entity == null) {
            return true;
        }

        int x0 = x - padding;
        int z0 = z - padding;
        int x1 = x + padding;
        int z1 = z + padding;

        if (forwardPadding) {
            if ((yaw > 640) && (yaw < 1408)) {
                z1 += 128;
            }
            if ((yaw > 1152) && (yaw < 1920)) {
                x1 += 128;
            }
            if ((yaw > 1664) || (yaw < 384)) {
                z0 -= 128;
            }
            if ((yaw > 128) && (yaw < 896)) {
                x0 -= 128;
            }
        }

        x0 /= 128;
        z0 /= 128;
        x1 /= 128;
        z1 /= 128;
        return add(entity, level, x0, z0, (x1 - x0) + 1, (z1 - z0) + 1, x, z, y, yaw, bitset, (byte) 0, true);
    }

    public boolean addTemporary(Entity entity, int level, int minTileX, int minTileZ, int maxTileX, int maxTileZ, int x, int z, int y, int yaw, int bitset) {
        if (entity == null) {
            return true;
        } else {
            return add(entity, level, minTileX, minTileZ, (maxTileX - minTileX) + 1, (maxTileZ - minTileZ) + 1, x, z, y, yaw, bitset, (byte) 0, true);
        }
    }

    public boolean add(Entity entity, int level, int tileX, int tileZ, int tileSizeX, int tileSizeZ, int x, int z, int y, int yaw, int bitset, byte info, boolean temporary) {
        for (int tx = tileX; tx < (tileX + tileSizeX); tx++) {
            for (int tz = tileZ; tz < (tileZ + tileSizeZ); tz++) {
                if ((tx < 0) || (tz < 0) || (tx >= maxTileX) || (tz >= maxTileZ)) {
                    return false;
                }
                SceneTile tile = levelTiles[level][tx][tz];
                if ((tile != null) && (tile.locCount >= 5)) {
                    return false;
                }
            }
        }
        SceneLoc loc = new SceneLoc();
        loc.bitset = bitset;
        loc.info = info;
        loc.level = level;
        loc.x = x;
        loc.z = z;
        loc.y = y;
        loc.entity = entity;
        loc.yaw = yaw;
        loc.minSceneTileX = tileX;
        loc.minSceneTileZ = tileZ;
        loc.maxSceneTileX = (tileX + tileSizeX) - 1;
        loc.maxSceneTileZ = (tileZ + tileSizeZ) - 1;

        for (int tx = tileX; tx < (tileX + tileSizeX); tx++) {
            for (int tz = tileZ; tz < (tileZ + tileSizeZ); tz++) {
                int spans = 0;

                if (tx > tileX) {
                    spans |= 0b0001;
                }

                if (tx < ((tileX + tileSizeX) - 1)) {
                    spans |= 0b0100;
                }

                if (tz > tileZ) {
                    spans |= 0b1000;
                }

                if (tz < ((tileZ + tileSizeZ) - 1)) {
                    spans |= 0b0010;
                }

                for (int p = level; p >= 0; p--) {
                    if (levelTiles[p][tx][tz] == null) {
                        levelTiles[p][tx][tz] = new SceneTile(p, tx, tz);
                    }
                }

                SceneTile tile = levelTiles[level][tx][tz];
                tile.locs[tile.locCount] = loc;
                tile.locSpan[tile.locCount] = spans;
                tile.locSpans |= spans;
                tile.locCount++;
            }
        }

        if (temporary) {
            temporaryLocs[temporaryLocCount++] = loc;
        }

        return true;
    }

    public void clearTemporaryLocs() {
        for (int i = 0; i < temporaryLocCount; i++) {
            SceneLoc loc = temporaryLocs[i];
            removeLoc(loc);
            temporaryLocs[i] = null;
        }
        temporaryLocCount = 0;
    }

    public void removeLoc(SceneLoc loc) {
        for (int tx = loc.minSceneTileX; tx <= loc.maxSceneTileX; tx++) {
            for (int tz = loc.minSceneTileZ; tz <= loc.maxSceneTileZ; tz++) {
                SceneTile tile = levelTiles[loc.level][tx][tz];

                if (tile == null) {
                    continue;
                }

                for (int i = 0; i < tile.locCount; i++) {
                    if (tile.locs[i] != loc) {
                        continue;
                    }

                    tile.locCount--;

                    for (int j = i; j < tile.locCount; j++) {
                        tile.locs[j] = tile.locs[j + 1];
                        tile.locSpan[j] = tile.locSpan[j + 1];
                    }
                    tile.locs[tile.locCount] = null;
                    break;
                }

                tile.locSpans = 0;

                for (int j1 = 0; j1 < tile.locCount; j1++) {
                    tile.locSpans |= tile.locSpan[j1];
                }
            }
        }
    }

    public void setWallDecorationOffset(int level, int stx, int stz, int offset) {
        SceneTile tile = levelTiles[level][stx][stz];
        if (tile == null) {
            return;
        }
        SceneWallDecoration decor = tile.wallDecoration;
        if (decor != null) {
            int sx = (stx * 128) + 64;
            int sz = (stz * 128) + 64;
            decor.x = sx + (((decor.x - sx) * offset) / 16);
            decor.z = sz + (((decor.z - sz) * offset) / 16);
        }
    }

    public void removeWall(int x, int level, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            tile.wall = null;
        }
    }

    public void removeWallDecoration(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            tile.wallDecoration = null;
        }
    }

    public void removeLoc(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile == null) {
            return;
        }
        for (int j1 = 0; j1 < tile.locCount; j1++) {
            SceneLoc loc = tile.locs[j1];
            if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == x) && (loc.minSceneTileZ == z)) {
                removeLoc(loc);
                return;
            }
        }
    }

    public void removeGroundDecoration(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            tile.groundDecoration = null;
        }
    }

    public void removeObjStack(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            tile.objStack = null;
        }
    }

    public SceneWall getWall(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            return tile.wall;
        } else {
            return null;
        }
    }

    public SceneWallDecoration getWallDecoration(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile != null) {
            return tile.wallDecoration;
        } else {
            return null;
        }
    }

    public SceneLoc getLoc(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile == null) {
            return null;
        }
        for (int l = 0; l < tile.locCount; l++) {
            SceneLoc loc = tile.locs[l];
            if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == x) && (loc.minSceneTileZ == z)) {
                return loc;
            }
        }
        return null;
    }

    public SceneGroundDecoration getGroundDecoration(int z, int x, int level) {
        SceneTile tile = levelTiles[level][x][z];
        if ((tile != null) && (tile.groundDecoration != null)) {
            return tile.groundDecoration;
        } else {
            return null;
        }
    }

    public int getWallBitset(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if ((tile != null) && (tile.wall != null)) {
            return tile.wall.bitset;
        } else {
            return 0;
        }
    }

    public int getWallDecorationBitset(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if ((tile == null) || (tile.wallDecoration == null)) {
            return 0;
        } else {
            return tile.wallDecoration.bitset;
        }
    }

    public int getLocBitset(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if (tile == null) {
            return 0;
        }
        for (int l = 0; l < tile.locCount; l++) {
            SceneLoc loc = tile.locs[l];
            if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == x) && (loc.minSceneTileZ == z)) {
                return loc.bitset;
            }
        }
        return 0;
    }

    public int getGroundDecorationBitset(int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];
        if ((tile == null) || (tile.groundDecoration == null)) {
            return 0;
        } else {
            return tile.groundDecoration.bitset;
        }
    }

    public int getInfo(int level, int x, int z, int bitset) {
        SceneTile tile = levelTiles[level][x][z];

        if (tile == null) {
            return -1;
        }

        if ((tile.wall != null) && (tile.wall.bitset == bitset)) {
            return tile.wall.info & 0xff;
        }

        if ((tile.wallDecoration != null) && (tile.wallDecoration.bitset == bitset)) {
            return tile.wallDecoration.info & 0xff;
        }

        if ((tile.groundDecoration != null) && (tile.groundDecoration.bitset == bitset)) {
            return tile.groundDecoration.info & 0xff;
        }

        for (int i = 0; i < tile.locCount; i++) {
            if (tile.locs[i].bitset == bitset) {
                return tile.locs[i].info & 0xff;
            }
        }
        return -1;
    }

    /**
     * Merges touching normals of all Locs (Walls, Ground Decorations, etc) and then reapplies their lighting.
     */
    public void buildModels(int lightAmbient, int lightAttenuation, int lightSrcX, int lightSrcY, int lightSrcZ) {
        int lightMagnitude = (int) Math.sqrt((lightSrcX * lightSrcX) + (lightSrcY * lightSrcY) + (lightSrcZ * lightSrcZ));
        int attenuation = (lightAttenuation * lightMagnitude) >> 8;
        for (int level = 0; level < maxLevel; level++) {
            for (int tileX = 0; tileX < maxTileX; tileX++) {
                for (int tileZ = 0; tileZ < maxTileZ; tileZ++) {
                    SceneTile tile = levelTiles[level][tileX][tileZ];

                    if (tile == null) {
                        continue;
                    }

                    SceneWall wall = tile.wall;

                    if ((wall != null) && (wall.entityA != null) && (wall.entityA.vertexNormal != null)) {
                        mergeLocNormals(level, 1, 1, tileX, tileZ, (Model) wall.entityA);

                        if ((wall.entityB != null) && (wall.entityB.vertexNormal != null)) {
                            mergeLocNormals(level, 1, 1, tileX, tileZ, (Model) wall.entityB);
                            mergeNormals((Model) wall.entityA, (Model) wall.entityB, 0, 0, 0, false);
                            ((Model) wall.entityB).applyLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                        }

                        ((Model) wall.entityA).applyLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                    }

                    for (int i = 0; i < tile.locCount; i++) {
                        SceneLoc loc = tile.locs[i];

                        if ((loc != null) && (loc.entity != null) && (loc.entity.vertexNormal != null)) {
                            mergeLocNormals(level, (loc.maxSceneTileX - loc.minSceneTileX) + 1, (loc.maxSceneTileZ - loc.minSceneTileZ) + 1, tileX, tileZ, (Model) loc.entity);
                            ((Model) loc.entity).applyLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                        }
                    }

                    SceneGroundDecoration decoration = tile.groundDecoration;
                    if ((decoration != null) && (decoration.entity.vertexNormal != null)) {
                        mergeGroundDecorationNormals(tileX, level, (Model) decoration.entity, tileZ);
                        ((Model) decoration.entity).applyLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                    }
                }
            }
        }
    }

    public void mergeGroundDecorationNormals(int tileX, int level, Model model, int tileZ) {
        if (tileX < maxTileX) {
            SceneTile tile = levelTiles[level][tileX + 1][tileZ];
            if ((tile != null) && (tile.groundDecoration != null) && (tile.groundDecoration.entity.vertexNormal != null)) {
                mergeNormals(model, (Model) tile.groundDecoration.entity, 128, 0, 0, true);
            }
        }

        if (tileZ < maxTileX) {
            SceneTile tile = levelTiles[level][tileX][tileZ + 1];
            if ((tile != null) && (tile.groundDecoration != null) && (tile.groundDecoration.entity.vertexNormal != null)) {
                mergeNormals(model, (Model) tile.groundDecoration.entity, 0, 0, 128, true);
            }
        }

        if ((tileX < maxTileX) && (tileZ < maxTileZ)) {
            SceneTile tile = levelTiles[level][tileX + 1][tileZ + 1];
            if ((tile != null) && (tile.groundDecoration != null) && (tile.groundDecoration.entity.vertexNormal != null)) {
                mergeNormals(model, (Model) tile.groundDecoration.entity, 128, 0, 128, true);
            }
        }

        if ((tileX < maxTileX) && (tileZ > 0)) {
            SceneTile tile = levelTiles[level][tileX + 1][tileZ - 1];
            if ((tile != null) && (tile.groundDecoration != null) && (tile.groundDecoration.entity.vertexNormal != null)) {
                mergeNormals(model, (Model) tile.groundDecoration.entity, 128, 0, -128, true);
            }
        }
    }

    public void mergeLocNormals(int level, int tileSizeX, int tileSizeZ, int tileX, int tileZ, Model model) {
        boolean allowFaceRemoval = true;
        int minTileX = tileX;
        int maxTileX = tileX + tileSizeX;
        int minTileZ = tileZ - 1;
        int maxTileZ = tileZ + tileSizeZ;

        for (int l = level; l <= (level + 1); l++) {
            if (l == maxLevel) {
                continue;
            }

            for (int x = minTileX; x <= maxTileX; x++) {
                if ((x < 0) || (x >= this.maxTileX)) {
                    continue;
                }

                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if ((z < 0) || (z >= this.maxTileZ) || (allowFaceRemoval && (x < maxTileX) && (z < maxTileZ) && ((z >= tileZ) || (x == tileX)))) {
                        continue;
                    }

                    SceneTile tile = levelTiles[l][x][z];

                    if (tile == null) {
                        continue;
                    }

                    int offsetY = ((levelHeightmaps[l][x][z] + levelHeightmaps[l][x + 1][z] + levelHeightmaps[l][x][z + 1] + levelHeightmaps[l][x + 1][z + 1]) / 4) - ((levelHeightmaps[level][tileX][tileZ] + levelHeightmaps[level][tileX + 1][tileZ] + levelHeightmaps[level][tileX][tileZ + 1] + levelHeightmaps[level][tileX + 1][tileZ + 1]) / 4);
                    SceneWall wall = tile.wall;

                    int offsetX = ((x - tileX) * 128) + ((1 - tileSizeX) * 64);
                    int offsetZ = ((z - tileZ) * 128) + ((1 - tileSizeZ) * 64);

                    if ((wall != null) && (wall.entityA != null) && (wall.entityA.vertexNormal != null)) {
                        mergeNormals(model, (Model) wall.entityA, offsetX, offsetY, offsetZ, allowFaceRemoval);
                    }

                    if ((wall != null) && (wall.entityB != null) && (wall.entityB.vertexNormal != null)) {
                        mergeNormals(model, (Model) wall.entityB, offsetX, offsetY, offsetZ, allowFaceRemoval);
                    }

                    for (int i = 0; i < tile.locCount; i++) {
                        SceneLoc loc = tile.locs[i];

                        if ((loc != null) && (loc.entity != null) && (loc.entity.vertexNormal != null)) {
                            int locTileSizeX = (loc.maxSceneTileX - loc.minSceneTileX) + 1;
                            int locTileSizeZ = (loc.maxSceneTileZ - loc.minSceneTileZ) + 1;
                            mergeNormals(model, (Model) loc.entity, ((loc.minSceneTileX - tileX) * 128) + ((locTileSizeX - tileSizeX) * 64), offsetY, ((loc.minSceneTileZ - tileZ) * 128) + ((locTileSizeZ - tileSizeZ) * 64), allowFaceRemoval);
                        }
                    }
                }
            }

            minTileX--;
            allowFaceRemoval = false;
        }
    }

    public void mergeNormals(Model modelA, Model modelB, int offsetX, int offsetY, int offsetZ, boolean allowFaceRemoval) {
        tmpMergeIndex++;
        int merged = 0;
        for (int vertexA = 0; vertexA < modelA.vertexCount; vertexA++) {
            VertexNormal normalA = modelA.vertexNormal[vertexA];
            VertexNormal originalNormalA = modelA.vertexNormalOriginal[vertexA];

            // undefined normal
            if (originalNormalA.w == 0) {
                continue;
            }

            int y = modelA.vertexY[vertexA] - offsetY;

            if (y > modelB.maxY) {
                continue;
            }

            int x = modelA.vertexX[vertexA] - offsetX;

            if ((x < modelB.minX) || (x > modelB.maxX)) {
                continue;
            }

            int z = modelA.vertexZ[vertexA] - offsetZ;

            if ((z < modelB.minZ) || (z > modelB.maxZ)) {
                continue;
            }

            for (int vertexB = 0; vertexB < modelB.vertexCount; vertexB++) {
                VertexNormal normalB = modelB.vertexNormal[vertexB];
                VertexNormal originalNormalB = modelB.vertexNormalOriginal[vertexB];

                if ((x == modelB.vertexX[vertexB]) && (z == modelB.vertexZ[vertexB]) && (y == modelB.vertexY[vertexB]) && (originalNormalB.w != 0)) {
                    normalA.x += originalNormalB.x;
                    normalA.y += originalNormalB.y;
                    normalA.z += originalNormalB.z;
                    normalA.w += originalNormalB.w;

                    normalB.x += originalNormalA.x;
                    normalB.y += originalNormalA.y;
                    normalB.z += originalNormalA.z;
                    normalB.w += originalNormalA.w;

                    merged++;
                    mergeIndexA[vertexA] = tmpMergeIndex;
                    mergeIndexB[vertexB] = tmpMergeIndex;
                }
            }
        }

        if ((merged < 3) || !allowFaceRemoval) {
            return;
        }

        // if every vertex of a given face had their normals merged, clear the face info causing that face not to draw.
        for (int i = 0; i < modelA.faceCount; i++) {
            if ((mergeIndexA[modelA.faceVertexA[i]] == tmpMergeIndex) && (mergeIndexA[modelA.faceVertexB[i]] == tmpMergeIndex) && (mergeIndexA[modelA.faceVertexC[i]] == tmpMergeIndex)) {
                modelA.faceInfo[i] = -1;
            }
        }

        // same as above but for model B
        for (int i = 0; i < modelB.faceCount; i++) {
            if ((mergeIndexB[modelB.faceVertexA[i]] == tmpMergeIndex) && (mergeIndexB[modelB.faceVertexB[i]] == tmpMergeIndex) && (mergeIndexB[modelB.faceVertexC[i]] == tmpMergeIndex)) {
                modelB.faceInfo[i] = -1;
            }
        }
    }

    public void drawMinimapTile(int[] dst, int offset, int step, int level, int x, int z) {
        SceneTile tile = levelTiles[level][x][z];

        if (tile == null) {
            return;
        }

        SceneTileUnderlay underlay = tile.underlay;
        if (underlay != null) {
            int rgb = underlay.rgb;

            if (rgb == 0) {
                return;
            }

            for (int k1 = 0; k1 < 4; k1++) {
                dst[offset] = rgb;
                dst[offset + 1] = rgb;
                dst[offset + 2] = rgb;
                dst[offset + 3] = rgb;
                offset += step;
            }
            return;
        }

        SceneTileOverlay overlay = tile.overlay;

        if (overlay == null) {
            return;
        }

        int shape = overlay.shape;
        int angle = overlay.rotation;
        int background = overlay.backgroundRGB;
        int foreground = overlay.foregroundRGB;
        int[] mask = MINIMAP_TILE_MASK[shape];
        int[] rotation = MINIMAP_TILE_ROTATION_MAP[angle];
        int off = 0;

        if (background != 0) {
            for (int i = 0; i < 4; i++) {
                dst[offset] = (mask[rotation[off++]] != 0) ? foreground : background;
                dst[offset + 1] = (mask[rotation[off++]] != 0) ? foreground : background;
                dst[offset + 2] = (mask[rotation[off++]] != 0) ? foreground : background;
                dst[offset + 3] = (mask[rotation[off++]] != 0) ? foreground : background;
                offset += step;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (mask[rotation[off++]] != 0) {
                dst[offset] = foreground;
            }
            if (mask[rotation[off++]] != 0) {
                dst[offset + 1] = foreground;
            }
            if (mask[rotation[off++]] != 0) {
                dst[offset + 2] = foreground;
            }
            if (mask[rotation[off++]] != 0) {
                dst[offset + 3] = foreground;
            }
            offset += step;
        }
    }

    public void click(int mouseY, int mouseX) {
        takingInput = true;
        Scene.mouseX = mouseX;
        Scene.mouseY = mouseY;
        clickTileX = -1;
        clickTileZ = -1;
    }

    public void draw(int eyeX, int eyeZ, int eyeYaw, int eyeY, int topLevel, int eyePitch) {
        cycle++;

        sinEyePitch = Model.sin[eyePitch];
        cosEyePitch = Model.cos[eyePitch];
        sinEyeYaw = Model.sin[eyeYaw];
        cosEyeYaw = Model.cos[eyeYaw];

        visibilityMap = visibilityMatrix[(eyePitch - 128) / 32][eyeYaw / 64];

        if (eyeX < 0) {
            eyeX = 0;
        } else if (eyeX >= (maxTileX * 128)) {
            eyeX = (maxTileX * 128) - 1;
        }
        if (eyeZ < 0) {
            eyeZ = 0;
        } else if (eyeZ >= (maxTileZ * 128)) {
            eyeZ = (maxTileZ * 128) - 1;
        }

        Scene.eyeX = eyeX;
        Scene.eyeY = eyeY;
        Scene.eyeZ = eyeZ;

        eyeTileX = eyeX / 128;
        eyeTileZ = eyeZ / 128;

        Scene.topLevel = topLevel;

        minDrawTileX = eyeTileX - 25;
        minDrawTileZ = eyeTileZ - 25;
        maxDrawTileX = eyeTileX + 25;
        maxDrawTileZ = eyeTileZ + 25;

        if (minDrawTileX < 0) {
            minDrawTileX = 0;
        }

        if (minDrawTileZ < 0) {
            minDrawTileZ = 0;
        }

        if (maxDrawTileX > maxTileX) {
            maxDrawTileX = maxTileX;
        }

        if (maxDrawTileZ > maxTileZ) {
            maxDrawTileZ = maxTileZ;
        }

        updateActiveOccluders();

        tilesRemaining = 0;
        for (int level = minLevel; level < maxLevel; level++) {
            SceneTile[][] tiles = levelTiles[level];
            for (int x = minDrawTileX; x < maxDrawTileX; x++) {
                for (int z = minDrawTileZ; z < maxDrawTileZ; z++) {
                    SceneTile tile = tiles[x][z];

                    if (tile == null) {
                        continue;
                    }

                    if ((tile.drawLevel > topLevel) || (!visibilityMap[(x - eyeTileX) + 25][(z - eyeTileZ) + 25] && ((levelHeightmaps[level][x][z] - eyeY) < 2000))) {
                        tile.visible = false;
                        tile.update = false;
                        tile.checkLocSpans = 0;
                    } else {
                        tile.visible = true;
                        tile.update = true;
                        tile.containsLocs = tile.locCount > 0;
                        tilesRemaining++;
                    }
                }
            }
        }

        for (int level = minLevel; level < maxLevel; level++) {
            SceneTile[][] tiles = levelTiles[level];

            for (int dx = -25; dx <= 0; dx++) {
                int rightTileX = eyeTileX + dx;
                int leftTileX = eyeTileX - dx;

                if ((rightTileX < minDrawTileX) && (leftTileX >= maxDrawTileX)) {
                    continue;
                }

                for (int dz = -25; dz <= 0; dz++) {
                    int forwardTileZ = eyeTileZ + dz;
                    int backwardTileZ = eyeTileZ - dz;

                    if (rightTileX >= minDrawTileX) {
                        if (forwardTileZ >= minDrawTileZ) {
                            SceneTile tile = tiles[rightTileX][forwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, true);
                            }
                        }

                        if (backwardTileZ < maxDrawTileZ) {
                            SceneTile tile = tiles[rightTileX][backwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, true);
                            }
                        }
                    }

                    if (leftTileX < maxDrawTileX) {
                        if (forwardTileZ >= minDrawTileZ) {
                            SceneTile tile = tiles[leftTileX][forwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, true);
                            }
                        }

                        if (backwardTileZ < maxDrawTileZ) {
                            SceneTile tile = tiles[leftTileX][backwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, true);
                            }
                        }
                    }

                    if (tilesRemaining == 0) {
                        takingInput = false;
                        return;
                    }
                }
            }
        }

        for (int level = minLevel; level < maxLevel; level++) {
            SceneTile[][] tiles = levelTiles[level];

            for (int deltaX = -25; deltaX <= 0; deltaX++) {
                int rightTileX = eyeTileX + deltaX;
                int leftTileX = eyeTileX - deltaX;

                if ((rightTileX < minDrawTileX) && (leftTileX >= maxDrawTileX)) {
                    continue;
                }

                for (int deltaZ = -25; deltaZ <= 0; deltaZ++) {
                    int forwardTileZ = eyeTileZ + deltaZ;
                    int backwardTileZ = eyeTileZ - deltaZ;

                    if (rightTileX >= minDrawTileX) {
                        if (forwardTileZ >= minDrawTileZ) {
                            SceneTile tile = tiles[rightTileX][forwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, false);
                            }
                        }

                        if (backwardTileZ < maxDrawTileZ) {
                            SceneTile tile = tiles[rightTileX][backwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, false);
                            }
                        }
                    }
                    if (leftTileX < maxDrawTileX) {
                        if (forwardTileZ >= minDrawTileZ) {
                            SceneTile tile = tiles[leftTileX][forwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, false);
                            }
                        }

                        if (backwardTileZ < maxDrawTileZ) {
                            SceneTile tile = tiles[leftTileX][backwardTileZ];
                            if ((tile != null) && tile.visible) {
                                drawTile(tile, false);
                            }
                        }
                    }

                    if (tilesRemaining == 0) {
                        takingInput = false;
                        return;
                    }
                }
            }
        }
        takingInput = false;
    }

    public void drawTile(SceneTile next, boolean checkAdjacent) {
        drawTileQueue.pushBack(next);

        while (true) {
            SceneTile tile;

            do {
                tile = (SceneTile) drawTileQueue.pollFront();

                if (tile == null) {
                    return;
                }
            } while (!tile.update);

            int tileX = tile.x;
            int tileZ = tile.z;
            int level = tile.level;
            int occludeLevel = tile.occludeLevel;
            SceneTile[][] tiles = levelTiles[level];

            if (tile.visible) {
                if (checkAdjacent) {
                    if (level > 0) {
                        SceneTile other = levelTiles[level - 1][tileX][tileZ];

                        if ((other != null) && other.update) {
                            continue;
                        }
                    }

                    if ((tileX <= eyeTileX) && (tileX > minDrawTileX)) {
                        SceneTile other = tiles[tileX - 1][tileZ];

                        if ((other != null) && other.update && (other.visible || ((tile.locSpans & 1) == 0))) {
                            continue;
                        }
                    }

                    if ((tileX >= eyeTileX) && (tileX < (maxDrawTileX - 1))) {
                        SceneTile other = tiles[tileX + 1][tileZ];

                        if ((other != null) && other.update && (other.visible || ((tile.locSpans & 4) == 0))) {
                            continue;
                        }
                    }

                    if ((tileZ <= eyeTileZ) && (tileZ > minDrawTileZ)) {
                        SceneTile other = tiles[tileX][tileZ - 1];

                        if ((other != null) && other.update && (other.visible || ((tile.locSpans & 8) == 0))) {
                            continue;
                        }
                    }

                    if ((tileZ >= eyeTileZ) && (tileZ < (maxDrawTileZ - 1))) {
                        SceneTile other = tiles[tileX][tileZ + 1];

                        if ((other != null) && other.update && (other.visible || ((tile.locSpans & 2) == 0))) {
                            continue;
                        }
                    }
                } else {
                    checkAdjacent = true;
                }

                tile.visible = false;

                if (tile.bridge != null) {
                    drawBridgeTile(tileX, tileZ, tile.bridge);
                }

                boolean tileDrawn = drawTileUnderlayOrOverlay(tile, tileX, tileZ, occludeLevel);

                int direction = 0;
                int frontWallTypes = 0;

                SceneWall wall = tile.wall;
                SceneWallDecoration decor = tile.wallDecoration;

                if ((wall != null) || (decor != null)) {
                    if (eyeTileX == tileX) {
                        direction++;
                    } else if (eyeTileX < tileX) {
                        direction += 2;
                    }

                    if (eyeTileZ == tileZ) {
                        direction += 3;
                    } else if (eyeTileZ > tileZ) {
                        direction += 6;
                    }

                    frontWallTypes = FRONT_WALL_TYPES[direction];
                    tile.backWallTypes = BACK_WALL_TYPES[direction];
                }

                if (wall != null) {
                    if ((wall.typeA & DIRECTION_ALLOW_WALL_CORNER_TYPE[direction]) != 0) {
                        switch (wall.typeA) {
                            case 16:
                                tile.checkLocSpans = 0b0011;
                                tile.blockLocSpans = WALL_CORNER_TYPE_16_BLOCK_LOC_SPANS[direction];
                                tile.inverseBlockLocSpans = 0b0011 - tile.blockLocSpans;
                                break;
                            case 32:
                                tile.checkLocSpans = 0b0110;
                                tile.blockLocSpans = WALL_CORNER_TYPE_32_BLOCK_LOC_SPANS[direction];
                                tile.inverseBlockLocSpans = 0b0110 - tile.blockLocSpans;
                                break;
                            case 64:
                                tile.checkLocSpans = 0b1100;
                                tile.blockLocSpans = WALL_CORNER_TYPE_64_BLOCK_LOC_SPANS[direction];
                                tile.inverseBlockLocSpans = 0b1100 - tile.blockLocSpans;
                                break;
                            case 128:
                                tile.checkLocSpans = 0b1001;
                                tile.blockLocSpans = WALL_CORNER_TYPE_128_BLOCK_LOC_SPANS[direction];
                                tile.inverseBlockLocSpans = 0b1001 - tile.blockLocSpans;
                                break;
                        }
                    } else {
                        tile.checkLocSpans = 0;
                    }

                    if (((wall.typeA & frontWallTypes) != 0) && wallVisible(occludeLevel, tileX, tileZ, wall.typeA)) {
                        wall.entityA.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
                    }

                    if (((wall.typeB & frontWallTypes) != 0) && wallVisible(occludeLevel, tileX, tileZ, wall.typeB)) {
                        wall.entityB.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
                    }
                }

                if ((decor != null) && visible(occludeLevel, tileX, tileZ, decor.entity.minY)) {
                    drawWallDecor(frontWallTypes, decor, true);
                }

                if (tileDrawn) {
                    SceneGroundDecoration groundDecor = tile.groundDecoration;

                    if (groundDecor != null) {
                        groundDecor.entity.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, groundDecor.x - eyeX, groundDecor.y - eyeY, groundDecor.z - eyeZ, groundDecor.bitset);
                    }

                    SceneObjStack stack = tile.objStack;

                    if ((stack != null) && (stack.offset == 0)) {
                        drawObjStack(stack, 0);
                    }
                }

                int spans = tile.locSpans;

                if (spans != 0) {
                    if ((tileX < eyeTileX) && ((spans & 0x4) != 0)) {
                        SceneTile adjacent = tiles[tileX + 1][tileZ];
                        if ((adjacent != null) && adjacent.update) {
                            drawTileQueue.pushBack(adjacent);
                        }
                    }

                    if ((tileZ < eyeTileZ) && ((spans & 0x2) != 0)) {
                        SceneTile adjacent = tiles[tileX][tileZ + 1];
                        if ((adjacent != null) && adjacent.update) {
                            drawTileQueue.pushBack(adjacent);
                        }
                    }

                    if ((tileX > eyeTileX) && ((spans & 0x1) != 0)) {
                        SceneTile adjacent = tiles[tileX - 1][tileZ];
                        if ((adjacent != null) && adjacent.update) {
                            drawTileQueue.pushBack(adjacent);
                        }
                    }

                    if ((tileZ > eyeTileZ) && ((spans & 0x8) != 0)) {
                        SceneTile adjacent = tiles[tileX][tileZ - 1];
                        if ((adjacent != null) && adjacent.update) {
                            drawTileQueue.pushBack(adjacent);
                        }
                    }
                }
            }

            if (tile.checkLocSpans != 0) {
                boolean draw = true;
                for (int i = 0; i < tile.locCount; i++) {
                    if (!tile.locs[i].drawn() && ((tile.locSpan[i] & tile.checkLocSpans) == tile.blockLocSpans)) {
                        draw = false;
                        break;
                    }
                }

                if (draw) {
                    SceneWall wall = tile.wall;

                    if (wallVisible(occludeLevel, tileX, tileZ, wall.typeA)) {
                        wall.entityA.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
                    }

                    tile.checkLocSpans = 0;
                }
            }

            if (tile.containsLocs) {
                try {
                    int locCount = tile.locCount;
                    tile.containsLocs = false;
                    int locBufferSize = 0;

                    iterate_locs:
                    for (int i = 0; i < locCount; i++) {
                        SceneLoc loc = tile.locs[i];

                        if (loc.drawn()) {
                            continue;
                        }

                        for (int x = loc.minSceneTileX; x <= loc.maxSceneTileX; x++) {
                            for (int z = loc.minSceneTileZ; z <= loc.maxSceneTileZ; z++) {
                                SceneTile other = tiles[x][z];

                                if (!other.visible) {
                                    if (other.checkLocSpans == 0) {
                                        continue;
                                    }

                                    int spans = 0;

                                    if (x > loc.minSceneTileX) {
                                        spans |= 0b0001;
                                    }

                                    if (x < loc.maxSceneTileX) {
                                        spans |= 0b0100;
                                    }

                                    if (z > loc.minSceneTileZ) {
                                        spans |= 0b1000;
                                    }

                                    if (z < loc.maxSceneTileZ) {
                                        spans |= 0b0010;
                                    }

                                    if ((spans & other.checkLocSpans) != tile.inverseBlockLocSpans) {
                                        continue;
                                    }
                                }

                                tile.containsLocs = true;
                                continue iterate_locs;
                            }
                        }

                        locBuffer[locBufferSize++] = loc;

                        int minTileDistanceX = eyeTileX - loc.minSceneTileX;
                        int maxTileDistanceX = loc.maxSceneTileX - eyeTileX;

                        if (maxTileDistanceX > minTileDistanceX) {
                            minTileDistanceX = maxTileDistanceX;
                        }

                        int minTileDistanceZ = eyeTileZ - loc.minSceneTileZ;
                        int maxTileDistanceZ = loc.maxSceneTileZ - eyeTileZ;

                        if (maxTileDistanceZ > minTileDistanceZ) {
                            loc.distance = minTileDistanceX + maxTileDistanceZ;
                        } else {
                            loc.distance = minTileDistanceX + minTileDistanceZ;
                        }
                    }

                    while (true) {
                        int farthestDistance = -50;
                        int farthestIndex = -1;

                        for (int index = 0; index < locBufferSize; index++) {
                            SceneLoc loc = locBuffer[index];

                            if (!loc.drawn()) {
                                if (loc.distance > farthestDistance) {
                                    farthestDistance = loc.distance;
                                    farthestIndex = index;
                                } else if (loc.distance == farthestDistance) {
                                    int dx0 = loc.x - eyeX;
                                    int dz0 = loc.z - eyeZ;
                                    int dx1 = locBuffer[farthestIndex].x - eyeX;
                                    int dz1 = locBuffer[farthestIndex].z - eyeZ;

                                    if (((dx0 * dx0) + (dz0 * dz0)) > ((dx1 * dx1) + (dz1 * dz1))) {
                                        farthestIndex = index;
                                    }
                                }
                            }
                        }

                        if (farthestIndex == -1) {
                            break;
                        }

                        SceneLoc farthest = locBuffer[farthestIndex];
                        farthest.cycle = cycle;

                        if (locVisible(occludeLevel, farthest.minSceneTileX, farthest.maxSceneTileX, farthest.minSceneTileZ, farthest.maxSceneTileZ, farthest.entity.minY)) {
                            farthest.entity.draw(farthest.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, farthest.x - eyeX, farthest.y - eyeY, farthest.z - eyeZ, farthest.bitset);
                        }

                        for (int x = farthest.minSceneTileX; x <= farthest.maxSceneTileX; x++) {
                            for (int z = farthest.minSceneTileZ; z <= farthest.maxSceneTileZ; z++) {
                                SceneTile occupied = tiles[x][z];

                                if (occupied.checkLocSpans != 0) {
                                    drawTileQueue.pushBack(occupied);
                                } else if (((x != tileX) || (z != tileZ)) && occupied.update) {
                                    drawTileQueue.pushBack(occupied);
                                }
                            }
                        }
                    }

                    if (tile.containsLocs) {
                        continue;
                    }
                } catch (Exception ignored) {
                    tile.containsLocs = false;
                }
            }

            if (!tile.update || (tile.checkLocSpans != 0)) {
                continue;
            }

            if ((tileX <= eyeTileX) && (tileX > minDrawTileX)) {
                SceneTile adjacent = tiles[tileX - 1][tileZ];
                if ((adjacent != null) && adjacent.update) {
                    continue;
                }
            }

            if ((tileX >= eyeTileX) && (tileX < (maxDrawTileX - 1))) {
                SceneTile adjacent = tiles[tileX + 1][tileZ];
                if ((adjacent != null) && adjacent.update) {
                    continue;
                }
            }

            if ((tileZ <= eyeTileZ) && (tileZ > minDrawTileZ)) {
                SceneTile adjacent = tiles[tileX][tileZ - 1];
                if ((adjacent != null) && adjacent.update) {
                    continue;
                }
            }

            if ((tileZ >= eyeTileZ) && (tileZ < (maxDrawTileZ - 1))) {
                SceneTile adjacent = tiles[tileX][tileZ + 1];
                if ((adjacent != null) && adjacent.update) {
                    continue;
                }
            }

            tile.update = false;
            tilesRemaining--;

            SceneObjStack stack = tile.objStack;

            if ((stack != null) && (stack.offset != 0)) {
                drawObjStack(stack, stack.offset);
            }

            if (tile.backWallTypes != 0) {
                SceneWallDecoration decor = tile.wallDecoration;

                if ((decor != null) && visible(occludeLevel, tileX, tileZ, decor.entity.minY)) {
                    drawWallDecor(tile.backWallTypes, decor, false);
                }

                SceneWall wall = tile.wall;

                if (wall != null) {
                    if (((wall.typeB & tile.backWallTypes) != 0) && wallVisible(occludeLevel, tileX, tileZ, wall.typeB)) {
                        wall.entityB.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
                    }

                    if (((wall.typeA & tile.backWallTypes) != 0) && wallVisible(occludeLevel, tileX, tileZ, wall.typeA)) {
                        wall.entityA.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
                    }
                }
            }

            if (level < (maxLevel - 1)) {
                SceneTile above = levelTiles[level + 1][tileX][tileZ];
                if ((above != null) && above.update) {
                    drawTileQueue.pushBack(above);
                }
            }

            if (tileX < eyeTileX) {
                SceneTile adjacent = tiles[tileX + 1][tileZ];
                if ((adjacent != null) && adjacent.update) {
                    drawTileQueue.pushBack(adjacent);
                }
            }

            if (tileZ < eyeTileZ) {
                SceneTile adjacent = tiles[tileX][tileZ + 1];
                if ((adjacent != null) && adjacent.update) {
                    drawTileQueue.pushBack(adjacent);
                }
            }

            if (tileX > eyeTileX) {
                SceneTile adjacent = tiles[tileX - 1][tileZ];
                if ((adjacent != null) && adjacent.update) {
                    drawTileQueue.pushBack(adjacent);
                }
            }

            if (tileZ > eyeTileZ) {
                SceneTile adjacent = tiles[tileX][tileZ - 1];
                if ((adjacent != null) && adjacent.update) {
                    drawTileQueue.pushBack(adjacent);
                }
            }
        }
    }

    private static void drawObjStack(SceneObjStack stack, int offset) {
        if (stack.bottomObj != null) {
            stack.bottomObj.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, stack.x - eyeX, stack.y - eyeY - offset, stack.z - eyeZ, stack.bitset);
        }
        if (stack.middleObj != null) {
            stack.middleObj.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, stack.x - eyeX, stack.y - eyeY - offset, stack.z - eyeZ, stack.bitset);
        }
        if (stack.topObj != null) {
            stack.topObj.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, stack.x - eyeX, stack.y - eyeY - offset, stack.z - eyeZ, stack.bitset);
        }
    }

    private static void drawWallDecor(int allowWallTypes, SceneWallDecoration decor, boolean front) {
        if ((decor.type & allowWallTypes) != 0) {
            decor.entity.draw(decor.rotation, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, decor.x - eyeX, decor.y - eyeY, decor.z - eyeZ, decor.bitset);
        } else if ((decor.type & 0x300) != 0) {
            int x = decor.x - eyeX;
            int y = decor.y - eyeY;
            int z = decor.z - eyeZ;
            int rotation = decor.rotation;

            int nearestX;
            int nearestZ;

            if ((rotation == 1) || (rotation == 2)) {
                nearestX = -x;
            } else {
                nearestX = x;
            }

            if ((rotation == 2) || (rotation == 3)) {
                nearestZ = -z;
            } else {
                nearestZ = z;
            }

            if ((decor.type & 0x100) != 0) {
                boolean draw = false;

                if (front && (nearestZ < nearestX)) {
                    draw = true;
                } else if (!front && (nearestZ >= nearestX)) {
                    draw = true;
                }

                if (draw) {
                    int drawX = x + WALL_DECORATION_INSET_X[rotation];
                    int drawZ = z + WALL_DECORATION_INSET_Z[rotation];
                    decor.entity.draw((rotation * 512) + 256, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, drawX, y, drawZ, decor.bitset);
                }
            }

            if ((decor.type & 0x200) != 0) {
                boolean draw = false;

                if (front && (nearestZ > nearestX)) {
                    draw = true;
                } else if (!front && (nearestZ <= nearestX)) {
                    draw = true;
                }

                if (draw) {
                    int drawX = x + WALL_DECORATION_OUTSET_X[rotation];
                    int drawZ = z + WALL_DECORATION_OUTSET_Z[rotation];
                    decor.entity.draw(((rotation * 512) + 1280) & 0x7ff, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, drawX, y, drawZ, decor.bitset);
                }
            }
        }
    }

    private boolean drawTileUnderlayOrOverlay(SceneTile tile, int x, int z, int level) {
        if (tile.underlay != null) {
            if (tileVisible(level, x, z)) {
                drawTileUnderlay(tile.underlay, level, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, x, z);
                return true;
            }
        } else if ((tile.overlay != null) && tileVisible(level, x, z)) {
            drawTileOverlay(x, sinEyePitch, sinEyeYaw, tile.overlay, cosEyePitch, z, cosEyeYaw);
            return true;
        }
        return false;
    }

    private void drawBridgeTile(int tileX, int tileZ, SceneTile bridge) {
        drawTileUnderlayOrOverlay(bridge, tileX, tileZ, 0);

        SceneWall wall = bridge.wall;

        if (wall != null) {
            wall.entityA.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
        }

        for (int i = 0; i < bridge.locCount; i++) {
            SceneLoc loc = bridge.locs[i];

            if (loc != null) {
                loc.entity.draw(loc.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, loc.x - eyeX, loc.y - eyeY, loc.z - eyeZ, loc.bitset);
            }
        }
    }

    public void drawTileUnderlay(SceneTileUnderlay underlay, int level, int sinEyePitch, int cosEyePitch, int sinEyeYaw, int cosEyeYaw, int tileX, int tileZ) {
        int x3;
        int x0 = x3 = (tileX << 7) - eyeX;
        int z1;
        int z0 = z1 = (tileZ << 7) - eyeZ;
        int x2;
        int x1 = x2 = x0 + 128;
        int z3;
        int z2 = z3 = z0 + 128;

        int y0 = levelHeightmaps[level][tileX][tileZ] - eyeY;
        int y1 = levelHeightmaps[level][tileX + 1][tileZ] - eyeY;
        int y2 = levelHeightmaps[level][tileX + 1][tileZ + 1] - eyeY;
        int y3 = levelHeightmaps[level][tileX][tileZ + 1] - eyeY;

        int tmp = ((z0 * sinEyeYaw) + (x0 * cosEyeYaw)) >> 16;
        z0 = ((z0 * cosEyeYaw) - (x0 * sinEyeYaw)) >> 16;
        x0 = tmp;

        tmp = ((y0 * cosEyePitch) - (z0 * sinEyePitch)) >> 16;
        z0 = ((y0 * sinEyePitch) + (z0 * cosEyePitch)) >> 16;
        y0 = tmp;

        if (z0 < 50) {
            return;
        }

        tmp = ((z1 * sinEyeYaw) + (x1 * cosEyeYaw)) >> 16;
        z1 = ((z1 * cosEyeYaw) - (x1 * sinEyeYaw)) >> 16;
        x1 = tmp;

        tmp = ((y1 * cosEyePitch) - (z1 * sinEyePitch)) >> 16;
        z1 = ((y1 * sinEyePitch) + (z1 * cosEyePitch)) >> 16;
        y1 = tmp;

        if (z1 < 50) {
            return;
        }

        tmp = ((z2 * sinEyeYaw) + (x2 * cosEyeYaw)) >> 16;
        z2 = ((z2 * cosEyeYaw) - (x2 * sinEyeYaw)) >> 16;
        x2 = tmp;

        tmp = ((y2 * cosEyePitch) - (z2 * sinEyePitch)) >> 16;
        z2 = ((y2 * sinEyePitch) + (z2 * cosEyePitch)) >> 16;
        y2 = tmp;

        if (z2 < 50) {
            return;
        }

        tmp = ((z3 * sinEyeYaw) + (x3 * cosEyeYaw)) >> 16;
        z3 = ((z3 * cosEyeYaw) - (x3 * sinEyeYaw)) >> 16;
        x3 = tmp;

        tmp = ((y3 * cosEyePitch) - (z3 * sinEyePitch)) >> 16;
        z3 = ((y3 * sinEyePitch) + (z3 * cosEyePitch)) >> 16;
        y3 = tmp;

        if (z3 < 50) {
            return;
        }

        int px0 = Draw3D.centerX + ((x0 << 9) / z0);
        int py0 = Draw3D.centerY + ((y0 << 9) / z0);
        int px1 = Draw3D.centerX + ((x1 << 9) / z1);
        int py1 = Draw3D.centerY + ((y1 << 9) / z1);
        int px2 = Draw3D.centerX + ((x2 << 9) / z2);
        int py2 = Draw3D.centerY + ((y2 << 9) / z2);
        int px3 = Draw3D.centerX + ((x3 << 9) / z3);
        int py3 = Draw3D.centerY + ((y3 << 9) / z3);

        Draw3D.alpha = 0;

        if ((((px2 - px3) * (py1 - py3)) - ((py2 - py3) * (px1 - px3))) > 0) {
            Draw3D.clipX = (px2 < 0) || (px3 < 0) || (px1 < 0) || (px2 > Draw2D.boundX) || (px3 > Draw2D.boundX) || (px1 > Draw2D.boundX);

            if (takingInput && pointInsideTriangle(mouseX, mouseY, py2, py3, py1, px2, px3, px1)) {
                clickTileX = tileX;
                clickTileZ = tileZ;
            }

            if (underlay.textureID == -1) {
                if (underlay.northeastColor != 12345678) {
                    Draw3D.fillGouraudTriangle(py2, py3, py1, px2, px3, px1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor);
                }
            } else if (!lowmem) {
                if (underlay.flat) {
                    Draw3D.fillTexturedTriangle(py2, py3, py1, px2, px3, px1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor, x0, x1, x3, y0, y1, y3, z0, z1, z3, underlay.textureID);
                } else {
                    Draw3D.fillTexturedTriangle(py2, py3, py1, px2, px3, px1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor, x2, x3, x1, y2, y3, y1, z2, z3, z1, underlay.textureID);
                }
            } else {
                int color = TEXTURE_HSL[underlay.textureID];
                Draw3D.fillGouraudTriangle(py2, py3, py1, px2, px3, px1, mulLightness(color, underlay.northeastColor), mulLightness(color, underlay.northwestColor), mulLightness(color, underlay.southeastColor));
            }
        }

        if ((((px0 - px1) * (py3 - py1)) - ((py0 - py1) * (px3 - px1))) > 0) {
            Draw3D.clipX = (px0 < 0) || (px1 < 0) || (px3 < 0) || (px0 > Draw2D.boundX) || (px1 > Draw2D.boundX) || (px3 > Draw2D.boundX);

            if (takingInput && pointInsideTriangle(mouseX, mouseY, py0, py1, py3, px0, px1, px3)) {
                clickTileX = tileX;
                clickTileZ = tileZ;
            }

            if (underlay.textureID == -1) {
                if (underlay.southwestColor != 12345678) {
                    Draw3D.fillGouraudTriangle(py0, py1, py3, px0, px1, px3, underlay.southwestColor, underlay.southeastColor, underlay.northwestColor);
                }
            } else {
                if (!lowmem) {
                    Draw3D.fillTexturedTriangle(py0, py1, py3, px0, px1, px3, underlay.southwestColor, underlay.southeastColor, underlay.northwestColor, x0, x1, x3, y0, y1, y3, z0, z1, z3, underlay.textureID);
                    return;
                }
                int color = TEXTURE_HSL[underlay.textureID];
                Draw3D.fillGouraudTriangle(py0, py1, py3, px0, px1, px3, mulLightness(color, underlay.southwestColor), mulLightness(color, underlay.southeastColor), mulLightness(color, underlay.northwestColor));
            }
        }
    }

    public void drawTileOverlay(int tileX, int sinEyePitch, int sinEyeYaw, SceneTileOverlay overlay, int cosEyePitch, int tileZ, int cosEyeYaw) {
        int vertexCount = overlay.vertexX.length;
        for (int v = 0; v < vertexCount; v++) {
            int x = overlay.vertexX[v] - eyeX;
            int y = overlay.vertexY[v] - eyeY;
            int z = overlay.vertexZ[v] - eyeZ;

            int tmp = ((z * sinEyeYaw) + (x * cosEyeYaw)) >> 16;
            z = ((z * cosEyeYaw) - (x * sinEyeYaw)) >> 16;
            x = tmp;

            tmp = ((y * cosEyePitch) - (z * sinEyePitch)) >> 16;
            z = ((y * sinEyePitch) + (z * cosEyePitch)) >> 16;
            y = tmp;

            if (z < 50) {
                return;
            }

            if (overlay.triangleTextureIDs != null) {
                SceneTileOverlay.tmpViewspaceX[v] = x;
                SceneTileOverlay.tmpViewspaceY[v] = y;
                SceneTileOverlay.tmpViewspaceZ[v] = z;
            }

            SceneTileOverlay.tmpScreenX[v] = Draw3D.centerX + ((x << 9) / z);
            SceneTileOverlay.tmpScreenY[v] = Draw3D.centerY + ((y << 9) / z);
        }

        Draw3D.alpha = 0;
        vertexCount = overlay.triangleVertexA.length;
        for (int v = 0; v < vertexCount; v++) {
            int a = overlay.triangleVertexA[v];
            int b = overlay.triangleVertexB[v];
            int c = overlay.triangleVertexC[v];

            int x0 = SceneTileOverlay.tmpScreenX[a];
            int x1 = SceneTileOverlay.tmpScreenX[b];
            int x2 = SceneTileOverlay.tmpScreenX[c];

            int y0 = SceneTileOverlay.tmpScreenY[a];
            int y1 = SceneTileOverlay.tmpScreenY[b];
            int y2 = SceneTileOverlay.tmpScreenY[c];

            if ((((x0 - x1) * (y2 - y1)) - ((y0 - y1) * (x2 - x1))) > 0) {
                Draw3D.clipX = (x0 < 0) || (x1 < 0) || (x2 < 0) || (x0 > Draw2D.boundX) || (x1 > Draw2D.boundX) || (x2 > Draw2D.boundX);

                if (takingInput && pointInsideTriangle(mouseX, mouseY, y0, y1, y2, x0, x1, x2)) {
                    clickTileX = tileX;
                    clickTileZ = tileZ;
                }

                if ((overlay.triangleTextureIDs == null) || (overlay.triangleTextureIDs[v] == -1)) {
                    if (overlay.triangleColorA[v] != 12345678) {
                        Draw3D.fillGouraudTriangle(y0, y1, y2, x0, x1, x2, overlay.triangleColorA[v], overlay.triangleColorB[v], overlay.triangleColorC[v]);
                    }
                } else if (!lowmem) {
                    if (overlay.flat) {
                        Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, overlay.triangleColorA[v], overlay.triangleColorB[v], overlay.triangleColorC[v], SceneTileOverlay.tmpViewspaceX[0], SceneTileOverlay.tmpViewspaceX[1], SceneTileOverlay.tmpViewspaceX[3], SceneTileOverlay.tmpViewspaceY[0], SceneTileOverlay.tmpViewspaceY[1], SceneTileOverlay.tmpViewspaceY[3], SceneTileOverlay.tmpViewspaceZ[0], SceneTileOverlay.tmpViewspaceZ[1], SceneTileOverlay.tmpViewspaceZ[3], overlay.triangleTextureIDs[v]);
                    } else {
                        Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, overlay.triangleColorA[v], overlay.triangleColorB[v], overlay.triangleColorC[v], SceneTileOverlay.tmpViewspaceX[a], SceneTileOverlay.tmpViewspaceX[b], SceneTileOverlay.tmpViewspaceX[c], SceneTileOverlay.tmpViewspaceY[a], SceneTileOverlay.tmpViewspaceY[b], SceneTileOverlay.tmpViewspaceY[c], SceneTileOverlay.tmpViewspaceZ[a], SceneTileOverlay.tmpViewspaceZ[b], SceneTileOverlay.tmpViewspaceZ[c], overlay.triangleTextureIDs[v]);
                    }
                } else {
                    int k5 = TEXTURE_HSL[overlay.triangleTextureIDs[v]];
                    Draw3D.fillGouraudTriangle(y0, y1, y2, x0, x1, x2, mulLightness(k5, overlay.triangleColorA[v]), mulLightness(k5, overlay.triangleColorB[v]), mulLightness(k5, overlay.triangleColorC[v]));
                }
            }
        }
    }

    public int mulLightness(int hsl, int lightness) {
        lightness = 127 - lightness;
        lightness = (lightness * (hsl & 0x7f)) / 160;
        if (lightness < 2) {
            lightness = 2;
        } else if (lightness > 126) {
            lightness = 126;
        }
        return (hsl & 0xff80) + lightness;
    }

    public boolean pointInsideTriangle(int x, int y, int y0, int y1, int y2, int x0, int x1, int x2) {
        if ((y < y0) && (y < y1) && (y < y2)) {
            return false;
        }
        if ((y > y0) && (y > y1) && (y > y2)) {
            return false;
        }
        if ((x < x0) && (x < x1) && (x < x2)) {
            return false;
        }
        if ((x > x0) && (x > x1) && (x > x2)) {
            return false;
        }
        int i2 = ((y - y0) * (x1 - x0)) - ((x - x0) * (y1 - y0));
        int j2 = ((y - y2) * (x0 - x2)) - ((x - x2) * (y0 - y2));
        int k2 = ((y - y1) * (x2 - x1)) - ((x - x1) * (y2 - y1));
        return ((i2 * k2) > 0) && ((k2 * j2) > 0);
    }

    public void updateActiveOccluders() {
        activeOccluderCount = 0;
        activeGroundOccluderCount = 0;
        activeWallOccluderCount = 0;
        tilesCulled = 0;

        int count = levelOccluderCount[topLevel];
        SceneOccluder[] occluders = levelOccluders[topLevel];

        for (int i = 0; i < count; i++) {
            SceneOccluder occluder = occluders[i];

            if (occluder.type == SceneOccluder.TYPE_WALL_X) {
                int x = (occluder.minTileX - eyeTileX) + 25;

                if ((x < 0) || (x > 50)) {
                    continue;
                }

                // Think of min/maxZ as the relative Z value in our visibility map, the +25 is because
                // the visibility map origin is at 25,25
                int minZ = (occluder.minTileZ - eyeTileZ) + 25;
                int maxZ = (occluder.maxTileZ - eyeTileZ) + 25;

                if (minZ < 0) {
                    minZ = 0;
                }

                if (maxZ > 50) {
                    maxZ = 50;
                }

                boolean ok = false;

                // checks if we can at least see one tile in the forward direction starting from our occluder
                while (minZ <= maxZ) {
                    if (visibilityMap[x][minZ++]) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    continue;
                }

                int deltaMinX = eyeX - occluder.minX;

                if (deltaMinX > 32) {
                    occluder.mode = 1;
                } else {
                    if (deltaMinX >= -32) {
                        continue;
                    }
                    occluder.mode = 2;
                    deltaMinX = -deltaMinX;
                }

                occluder.minDeltaZ = ((occluder.minZ - eyeZ) << 10) / deltaMinX;
                occluder.maxDeltaZ = ((occluder.maxZ - eyeZ) << 10) / deltaMinX;
                occluder.minDeltaY = ((occluder.minY - eyeY) << 10) / deltaMinX;
                occluder.maxDeltaY = ((occluder.maxY - eyeY) << 10) / deltaMinX;
                activeOccluders[activeOccluderCount++] = occluder;
                activeWallOccluderCount++;
                continue;
            }

            if (occluder.type == SceneOccluder.TYPE_WALL_Z) {
                int distanceMinTileZ = (occluder.minTileZ - eyeTileZ) + 25;

                if ((distanceMinTileZ < 0) || (distanceMinTileZ > 50)) {
                    continue;
                }

                int distanceMinTileX = (occluder.minTileX - eyeTileX) + 25;

                if (distanceMinTileX < 0) {
                    distanceMinTileX = 0;
                }

                int distanceMaxTileX = (occluder.maxTileX - eyeTileX) + 25;

                if (distanceMaxTileX > 50) {
                    distanceMaxTileX = 50;
                }

                boolean ok = false;

                while (distanceMinTileX <= distanceMaxTileX) {
                    if (visibilityMap[distanceMinTileX++][distanceMinTileZ]) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    continue;
                }

                int deltaMinZ = eyeZ - occluder.minZ;

                if (deltaMinZ > 32) {
                    occluder.mode = 3;
                } else {
                    if (deltaMinZ >= -32) {
                        continue;
                    }
                    occluder.mode = 4;
                    deltaMinZ = -deltaMinZ;
                }

                occluder.minDeltaX = ((occluder.minX - eyeX) << 10) / deltaMinZ;
                occluder.maxDeltaX = ((occluder.maxX - eyeX) << 10) / deltaMinZ;
                occluder.minDeltaY = ((occluder.minY - eyeY) << 10) / deltaMinZ;
                occluder.maxDeltaY = ((occluder.maxY - eyeY) << 10) / deltaMinZ;
                activeOccluders[activeOccluderCount++] = occluder;
                activeWallOccluderCount++;
            } else if (occluder.type == SceneOccluder.TYPE_GROUND) {
                int deltaMaxY = occluder.minY - eyeY;

                if (deltaMaxY <= 128) {
                    continue;
                }

                int deltaMinTileZ = (occluder.minTileZ - eyeTileZ) + 25;

                if (deltaMinTileZ < 0) {
                    deltaMinTileZ = 0;
                }

                int deltaMaxTileZ = (occluder.maxTileZ - eyeTileZ) + 25;

                if (deltaMaxTileZ > 50) {
                    deltaMaxTileZ = 50;
                }

                if (deltaMinTileZ <= deltaMaxTileZ) {
                    int deltaMinTileX = (occluder.minTileX - eyeTileX) + 25;

                    if (deltaMinTileX < 0) {
                        deltaMinTileX = 0;
                    }

                    int deltaMaxTileX = (occluder.maxTileX - eyeTileX) + 25;

                    if (deltaMaxTileX > 50) {
                        deltaMaxTileX = 50;
                    }

                    boolean ok = false;

                    find_visible_tile:
                    for (int x = deltaMinTileX; x <= deltaMaxTileX; x++) {
                        for (int z = deltaMinTileZ; z <= deltaMaxTileZ; z++) {
                            if (visibilityMap[x][z]) {
                                ok = true;
                                break find_visible_tile;
                            }
                        }
                    }

                    if (ok) {
                        occluder.mode = 5;
                        occluder.minDeltaX = ((occluder.minX - eyeX) << 10) / deltaMaxY;
                        occluder.maxDeltaX = ((occluder.maxX - eyeX) << 10) / deltaMaxY;
                        occluder.minDeltaZ = ((occluder.minZ - eyeZ) << 10) / deltaMaxY;
                        occluder.maxDeltaZ = ((occluder.maxZ - eyeZ) << 10) / deltaMaxY;
                        activeOccluders[activeOccluderCount++] = occluder;
                        activeGroundOccluderCount++;
                    }
                }
            }
        }
    }

    public boolean tileVisible(int level, int x, int z) {
        int cycle = levelTileOcclusionCycles[level][x][z];

        if (cycle == -Scene.cycle) {
            return true;
        }

        if (cycle == Scene.cycle) {
            return false;
        }

        int sx = x << 7;
        int sz = z << 7;

        if (occluded(sx + 1, levelHeightmaps[level][x][z], sz + 1) && occluded((sx + 128) - 1, levelHeightmaps[level][x + 1][z], sz + 1) && occluded((sx + 128) - 1, levelHeightmaps[level][x + 1][z + 1], (sz + 128) - 1) && occluded(sx + 1, levelHeightmaps[level][x][z + 1], (sz + 128) - 1)) {
            levelTileOcclusionCycles[level][x][z] = Scene.cycle;
            tilesCulled++;
            return false;
        } else {
            levelTileOcclusionCycles[level][x][z] = -Scene.cycle;
            return true;
        }
    }

    public boolean wallVisible(int level, int tileX, int tileZ, int type) {
        if (tileVisible(level, tileX, tileZ)) {
            return true;
        }

        int sceneX = tileX << 7;
        int sceneZ = tileZ << 7;
        int sceneY = levelHeightmaps[level][tileX][tileZ] - 1;
        int y0 = sceneY - 120;
        int y1 = sceneY - 230;
        int y2 = sceneY - 238;

        if (type < 16) {
            if (type == 1) {
                if (sceneX > eyeX) {
                    if (!occluded(sceneX, sceneY, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX, sceneY, sceneZ + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(sceneX, y0, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX, y0, sceneZ + 128)) {
                        return true;
                    }
                }
                if (!occluded(sceneX, y1, sceneZ)) {
                    return true;
                }
                return !occluded(sceneX, y1, sceneZ + 128);
            } else if (type == 2) {
                if (sceneZ < eyeZ) {
                    if (!occluded(sceneX, sceneY, sceneZ + 128)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, sceneY, sceneZ + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(sceneX, y0, sceneZ + 128)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, y0, sceneZ + 128)) {
                        return true;
                    }
                }
                if (!occluded(sceneX, y1, sceneZ + 128)) {
                    return true;
                }
                return !occluded(sceneX + 128, y1, sceneZ + 128);
            } else if (type == 4) {
                if (sceneX < eyeX) {
                    if (!occluded(sceneX + 128, sceneY, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, sceneY, sceneZ + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(sceneX + 128, y0, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, y0, sceneZ + 128)) {
                        return true;
                    }
                }
                if (!occluded(sceneX + 128, y1, sceneZ)) {
                    return true;
                }
                return !occluded(sceneX + 128, y1, sceneZ + 128);
            } else if (type == 8) {
                if (sceneZ > eyeZ) {
                    if (!occluded(sceneX, sceneY, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, sceneY, sceneZ)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(sceneX, y0, sceneZ)) {
                        return true;
                    }
                    if (!occluded(sceneX + 128, y0, sceneZ)) {
                        return true;
                    }
                }
                if (!occluded(sceneX, y1, sceneZ)) {
                    return true;
                }
                return !occluded(sceneX + 128, y1, sceneZ);
            }
        }

        if (!occluded(sceneX + 64, y2, sceneZ + 64)) {
            return true;
        }

        if (type == 16) {
            return !occluded(sceneX, y1, sceneZ + 128);
        } else if (type == 32) {
            return !occluded(sceneX + 128, y1, sceneZ + 128);
        } else if (type == 64) {
            return !occluded(sceneX + 128, y1, sceneZ);
        } else if (type == 128) {
            return !occluded(sceneX, y1, sceneZ);
        } else {
            System.out.println("Warning unsupported wall type");
            return false;
        }
    }

    public boolean visible(int level, int tileX, int tileZ, int y) {
        if (tileVisible(level, tileX, tileZ)) {
            return true;
        }
        int x = tileX << 7;
        int z = tileZ << 7;
        return !occluded(x + 1, levelHeightmaps[level][tileX][tileZ] - y, z + 1) || !occluded((x + 128) - 1, levelHeightmaps[level][tileX + 1][tileZ] - y, z + 1) || !occluded((x + 128) - 1, levelHeightmaps[level][tileX + 1][tileZ + 1] - y, (z + 128) - 1) || !occluded(x + 1, levelHeightmaps[level][tileX][tileZ + 1] - y, (z + 128) - 1);
    }

    public boolean locVisible(int level, int minTileX, int maxTileX, int minTileZ, int maxTileZ, int y) {
        if ((minTileX == maxTileX) && (minTileZ == maxTileZ)) {
            if (tileVisible(level, minTileX, minTileZ)) {
                return true;
            }
            int x = minTileX << 7;
            int z = minTileZ << 7;
            return !occluded(x + 1, levelHeightmaps[level][minTileX][minTileZ] - y, z + 1) || !occluded((x + 128) - 1, levelHeightmaps[level][minTileX + 1][minTileZ] - y, z + 1) || !occluded((x + 128) - 1, levelHeightmaps[level][minTileX + 1][minTileZ + 1] - y, (z + 128) - 1) || !occluded(x + 1, levelHeightmaps[level][minTileX][minTileZ + 1] - y, (z + 128) - 1);
        }

        for (int stx = minTileX; stx <= maxTileX; stx++) {
            for (int stz = minTileZ; stz <= maxTileZ; stz++) {
                if (levelTileOcclusionCycles[level][stx][stz] == -cycle) {
                    return true;
                }
            }
        }

        int x0 = (minTileX << 7) + 1;
        int z0 = (minTileZ << 7) + 2;
        int y0 = levelHeightmaps[level][minTileX][minTileZ] - y;

        if (!occluded(x0, y0, z0)) {
            return true;
        }

        int x1 = (maxTileX << 7) - 1;

        if (!occluded(x1, y0, z0)) {
            return true;
        }

        int z1 = (maxTileZ << 7) - 1;

        if (!occluded(x0, y0, z1)) {
            return true;
        }

        return !occluded(x1, y0, z1);
    }

    public boolean occluded(int x, int y, int z) {
        for (int i = 0; i < activeOccluderCount; i++) {
            SceneOccluder occluder = activeOccluders[i];

            if (occluder.mode == 1) {
                int dx = occluder.minX - x;
                if (dx <= 0) {
                    continue;
                }

                int minZ = occluder.minZ + ((occluder.minDeltaZ * dx) >> 10);
                int maxZ = occluder.maxZ + ((occluder.maxDeltaZ * dx) >> 10);
                int minY = occluder.minY + ((occluder.minDeltaY * dx) >> 10);
                int maxY = occluder.maxY + ((occluder.maxDeltaY * dx) >> 10);
                if ((z >= minZ) && (z <= maxZ) && (y >= minY) && (y <= maxY)) {
                    return true;
                }
            } else if (occluder.mode == 2) {
                int dx = x - occluder.minX;
                if (dx <= 0) {
                    continue;
                }
                int minZ = occluder.minZ + ((occluder.minDeltaZ * dx) >> 10);
                int macZ = occluder.maxZ + ((occluder.maxDeltaZ * dx) >> 10);
                int minY = occluder.minY + ((occluder.minDeltaY * dx) >> 10);
                int maxY = occluder.maxY + ((occluder.maxDeltaY * dx) >> 10);
                if ((z >= minZ) && (z <= macZ) && (y >= minY) && (y <= maxY)) {
                    return true;
                }
            } else if (occluder.mode == 3) {
                int dz = occluder.minZ - z;
                if (dz <= 0) {
                    continue;
                }
                int minX = occluder.minX + ((occluder.minDeltaX * dz) >> 10);
                int maxX = occluder.maxX + ((occluder.maxDeltaX * dz) >> 10);
                int minY = occluder.minY + ((occluder.minDeltaY * dz) >> 10);
                int maxY = occluder.maxY + ((occluder.maxDeltaY * dz) >> 10);
                if ((x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY)) {
                    return true;
                }
            } else if (occluder.mode == 4) {
                int dz = z - occluder.minZ;
                if (dz <= 0) {
                    continue;
                }
                int minX = occluder.minX + ((occluder.minDeltaX * dz) >> 10);
                int maxX = occluder.maxX + ((occluder.maxDeltaX * dz) >> 10);
                int minY = occluder.minY + ((occluder.minDeltaY * dz) >> 10);
                int maxY = occluder.maxY + ((occluder.maxDeltaY * dz) >> 10);
                if ((x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY)) {
                    return true;
                }
            } else if (occluder.mode == 5) {
                int dy = y - occluder.minY;
                if (dy <= 0) {
                    continue;
                }
                int minX = occluder.minX + ((occluder.minDeltaX * dy) >> 10);
                int maxX = occluder.maxX + ((occluder.maxDeltaX * dy) >> 10);
                int minZ = occluder.minZ + ((occluder.minDeltaZ * dy) >> 10);
                int maxZ = occluder.maxZ + ((occluder.maxDeltaZ * dy) >> 10);
                if ((x >= minX) && (x <= maxX) && (z >= minZ) && (z <= maxZ)) {
                    return true;
                }
            }
        }
        return false;
    }

}
