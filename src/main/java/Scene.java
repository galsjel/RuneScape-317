// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public static final int LEVEL_COUNT = 4;
    public static final SceneOccluder[] active_occluders = new SceneOccluder[500];
    public static int remaining_tiles;
    public static int top_level;
    public static int cycle;
    public static int min_draw_tile_x;
    public static int max_draw_tile_x;
    public static int min_draw_tile_z;
    public static int max_draw_tile_z;
    public static int eye_tile_x;
    public static int eye_tile_z;
    public static int eye_x;
    public static int eye_y;
    public static int eye_z;
    public static int sin_eye_pitch;
    public static int cos_eye_pitch;
    public static int sin_eye_yaw;
    public static int cos_eye_yaw;
    /**
     * Used to track locs being sorted for drawing on a per-tile basis.
     */
    public static SceneEntity[] drawable_buffer = new SceneEntity[100];
    public static boolean accept_input;
    public static int mouseX;
    public static int mouseY;
    public static int input_tile_x = -1;
    public static int input_tile_z = -1;
    public static int[] level_occluder_count = new int[LEVEL_COUNT];
    public static SceneOccluder[][] level_occluders = new SceneOccluder[LEVEL_COUNT][1000];
    public static int occluders_active;
    public static int wall_occluders_active;
    public static int ground_occluders_active;
    public static int tiles_culled;
    public static DoublyLinkedList draw_tile_queue = new DoublyLinkedList();
    public static boolean[][][][] visibility_matrix = new boolean[8][32][51][51];
    public static boolean[][] visibility_map;
    public static int viewportCenterX;
    public static int viewportCenterY;
    public static int viewportLeft;
    public static int viewportTop;
    public static int viewportRight;
    public static int viewportBottom;

    public static void unload() {
        drawable_buffer = null;
        level_occluder_count = null;
        level_occluders = null;
        draw_tile_queue = null;
        visibility_matrix = null;
        visibility_map = null;
    }

    public static void addOccluder(int level, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
        SceneOccluder occluder = new SceneOccluder();
        occluder.min_tile_x = minX / 128;
        occluder.max_tile_x = maxX / 128;
        occluder.min_tile_z = minZ / 128;
        occluder.max_tile_z = maxZ / 128;
        occluder.type = type;
        occluder.min_x = minX;
        occluder.max_x = maxX;
        occluder.min_z = minZ;
        occluder.max_z = maxZ;
        occluder.min_y = minY;
        occluder.max_y = maxY;
        level_occluders[level][level_occluder_count[level]++] = occluder;
    }

    public static void main(String[] args) {
        init(512,334);
    }

    public static void init(int viewportWidth, int viewportHeight) {
        viewportLeft = 0;
        viewportTop = 0;
        Scene.viewportRight = viewportWidth;
        Scene.viewportBottom = viewportHeight;
        viewportCenterX = viewportWidth / 2;
        viewportCenterY = viewportHeight / 2;
        initVisibilityMatrix();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("visibility_matrix.csv"))) {
            writer.write("pitch,yaw,x,z,visible");
            writer.newLine();
            for (int pitch = 0; pitch < visibility_matrix.length; pitch++) {
                for (int yaw = 0; yaw < visibility_matrix[pitch].length; yaw++) {
                    for (int x = 0; x < visibility_matrix[pitch][yaw].length; x++) {
                        for (int z = 0; z < visibility_matrix[pitch][yaw][x].length; z++) {
                            writer.write(String.format("%d,%d,%d,%d,%d",pitch,yaw,x,z,visibility_matrix[pitch][yaw][x][z] ? 1 : 0));
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the {@link Scene#visibility_matrix} lookup table which provides a rough approximation for relative tile
     * visibility within a specific range of pitches and yaws.
     */
    private static void initVisibilityMatrix() {
        int[] pitchDistance = new int[9];
        for (int pitchLevel = 0; pitchLevel < 9; pitchLevel++) {
            int angle = 128 + pitchLevel * 32 + 15;
            int distance = 600 + angle * 3;
            pitchDistance[pitchLevel] = distance * Draw3D.sin[angle] >> 16;
        }

        boolean[][][][] matrix = new boolean[9][32][53][53];

        for (int pitch = 128; pitch <= 384; pitch += 32) {
            for (int yaw = 0; yaw < 2048; yaw += 64) {
                sin_eye_pitch = Model.sin[pitch];
                cos_eye_pitch = Model.cos[pitch];
                sin_eye_yaw = Model.sin[yaw];
                cos_eye_yaw = Model.cos[yaw];

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
                        Scene.visibility_matrix[pitchLevel][yawLevel][x + 25][z + 25] = visible;
                    }
                }
            }
        }
    }

    public static boolean testPoint(int y, int z, int x) {
        int px = z * sin_eye_yaw + x * cos_eye_yaw >> 16;
        int tmp = z * cos_eye_yaw - x * sin_eye_yaw >> 16;
        int pz = y * sin_eye_pitch + tmp * cos_eye_pitch >> 16;
        int py = y * cos_eye_pitch - tmp * sin_eye_pitch >> 16;
        if (pz < 50 || pz > 3500) {
            return false;
        }
        int viewportX = viewportCenterX + (px << 9) / pz;
        int viewportY = viewportCenterY + (py << 9) / pz;
        return viewportX >= viewportLeft && viewportX <= viewportRight && viewportY >= viewportTop && viewportY <= viewportBottom;
    }

    public final int max_level;
    public final int max_tile_x;
    public final int max_tile_z;
    public final int[][][] level_heightmaps;
    public final SceneTile[][][] level_tiles;
    public final SceneEntity[] temporary_entities = new SceneEntity[5000];
    public final int[][][] level_occlusion_cycles;
    public final int[] mergeIndexA = new int[10000];
    public final int[] mergeIndexB = new int[10000];
    public final int[][] MINIMAP_TILE_MASK = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
    public final int[][] MINIMAP_TILE_ROTATION_MAP = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
    public int min_level;
    public int temporary_entity_count;
    public int tmpMergeIndex;

    public Scene(int max_tile_z, int max_tile_x, int[][][] levelHeightmaps, int max_level) {
        this.max_level = max_level;
        this.max_tile_x = max_tile_x;
        this.max_tile_z = max_tile_z;
        level_tiles = new SceneTile[max_level][max_tile_x][max_tile_z];
        level_occlusion_cycles = new int[max_level][max_tile_x + 1][max_tile_z + 1];
        this.level_heightmaps = levelHeightmaps;
        reset();
    }

    public void reset() {
        for (int level = 0; level < max_level; level++) {
            for (int stx = 0; stx < max_tile_x; stx++) {
                for (int stz = 0; stz < max_tile_z; stz++) {
                    level_tiles[level][stx][stz] = null;
                }
            }
        }

        for (int l = 0; l < LEVEL_COUNT; l++) {
            for (int j1 = 0; j1 < level_occluder_count[l]; j1++) {
                level_occluders[l][j1] = null;
            }
            level_occluder_count[l] = 0;
        }

        for (int i = 0; i < temporary_entity_count; i++) {
            temporary_entities[i] = null;
        }

        temporary_entity_count = 0;
        Arrays.fill(drawable_buffer, null);
    }

    public void set_min_level(int level) {
        min_level = level;
        for (int stx = 0; stx < max_tile_x; stx++) {
            for (int stz = 0; stz < max_tile_z; stz++) {
                if (level_tiles[level][stx][stz] == null) {
                    level_tiles[level][stx][stz] = new SceneTile(level, stx, stz);
                }
            }
        }
    }

    public void setBridge(int stx, int stz) {
        SceneTile ground = level_tiles[0][stx][stz];

        for (int level = 0; level < 3; level++) {
            SceneTile above = level_tiles[level][stx][stz] = level_tiles[level + 1][stx][stz];

            if (above == null) {
                continue;
            }

            above.level--;

            for (int i = 0; i < above.entity_count; i++) {
                SceneEntity generic = above.entities[i];

                if ((generic.bitset >> 29 & 3) == 2 && generic.min_tile_x == stx && generic.min_tile_z == stz) {
                    generic.level--;
                }
            }
        }

        if (level_tiles[0][stx][stz] == null) {
            level_tiles[0][stx][stz] = new SceneTile(0, stx, stz);
        }

        level_tiles[0][stx][stz].bridge = ground;
        level_tiles[3][stx][stz] = null;
    }

    public void setDrawLevel(int level, int stx, int stz, int drawLevel) {
        SceneTile tile = level_tiles[level][stx][stz];
        if (tile != null) {
            level_tiles[level][stx][stz].draw_level = drawLevel;
        }
    }

    public void setTile(int level, int x, int z, int shape, int rotation, int textureID, int southwestY, int southeastY, int northeastY, int northwestY, int southwestColor1, int southeastColor1, int northeastColor1, int northwestColor1, int southwestColor2, int southeastColor2, int northeastColor2, int northwestColor2, int backgroundRGB, int foregroundRGB) {
        if (shape == 0) {
            SceneTileUnderlay underlay = new SceneTileUnderlay(southwestColor1, southeastColor1, northeastColor1, northwestColor1, -1, backgroundRGB, false);
            for (int l = level; l >= 0; l--) {
                if (level_tiles[l][x][z] == null) {
                    level_tiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            level_tiles[level][x][z].underlay = underlay;
        } else if (shape == 1) {
            SceneTileUnderlay underlay = new SceneTileUnderlay(southwestColor2, southeastColor2, northeastColor2, northwestColor2, textureID, foregroundRGB, southwestY == southeastY && southwestY == northeastY && southwestY == northwestY);
            for (int l = level; l >= 0; l--) {
                if (level_tiles[l][x][z] == null) {
                    level_tiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            level_tiles[level][x][z].underlay = underlay;
        } else {
            SceneTileOverlay overlay = new SceneTileOverlay(z, southwestColor2, northwestColor1, northeastY, textureID, northeastColor2, rotation, southwestColor1, backgroundRGB, northeastColor1, northwestY, southeastY, southwestY, shape, northwestColor2, southeastColor2, southeastColor1, x, foregroundRGB);
            for (int l = level; l >= 0; l--) {
                if (level_tiles[l][x][z] == null) {
                    level_tiles[l][x][z] = new SceneTile(l, x, z);
                }
            }
            level_tiles[level][x][z].overlay = overlay;
        }
    }

    public void set_ground_decoration(Drawable entity, int tileLevel, int tileX, int tileZ, int y, int bitset, byte info) {
        if (entity == null) {
            return;
        }
        SceneGroundDecoration decoration = new SceneGroundDecoration();
        decoration.entity = entity;
        decoration.x = tileX * 128 + 64;
        decoration.z = tileZ * 128 + 64;
        decoration.y = y;
        decoration.bitset = bitset;
        decoration.info = info;
        if (level_tiles[tileLevel][tileX][tileZ] == null) {
            level_tiles[tileLevel][tileX][tileZ] = new SceneTile(tileLevel, tileX, tileZ);
        }
        level_tiles[tileLevel][tileX][tileZ].ground_decoration = decoration;
    }

    public void setItemStack(Drawable top, Drawable bottom, Drawable middle, int level, int tx, int tz, int y, int bitset) {
        SceneItemStack stack = new SceneItemStack();
        stack.x = tx * 128 + 64;
        stack.z = tz * 128 + 64;
        stack.y = y;
        stack.bitset = bitset;
        stack.top = top;
        stack.bottom = bottom;
        stack.middle = middle;

        int offset = 0;

        SceneTile tile = level_tiles[level][tx][tz];

        if (tile != null) {
            for (int l = 0; l < tile.entity_count; l++) {
                if (!(tile.entities[l].drawable instanceof Model)) {
                    continue;
                }
                int height = ((Model) tile.entities[l].drawable).objRaise;
                if (height > offset) {
                    offset = height;
                }
            }
        }

        stack.offset = offset;

        if (level_tiles[level][tx][tz] == null) {
            level_tiles[level][tx][tz] = new SceneTile(level, tx, tz);
        }

        level_tiles[level][tx][tz].item_stack = stack;
    }

    public void set_wall(int type_a, Drawable drawable_a, int type_b, Drawable drawable_b, int level, int tx, int tz, int y, int bitset, byte info) {
        if (drawable_a == null && drawable_b == null) {
            return;
        }
        SceneWall wall = new SceneWall();
        wall.bitset = bitset;
        wall.info = info;
        wall.x = tx * 128 + 64;
        wall.z = tz * 128 + 64;
        wall.y = y;
        wall.drawable_a = drawable_a;
        wall.drawable_b = drawable_b;
        wall.type_a = type_a;
        wall.type_b = type_b;
        for (int l = level; l >= 0; l--) {
            if (level_tiles[l][tx][tz] == null) {
                level_tiles[l][tx][tz] = new SceneTile(l, tx, tz);
            }
        }
        level_tiles[level][tx][tz].wall = wall;
    }

    public void set_wall_decoration(int type, Drawable entity, int level, int tileX, int tileZ, int y, int rotation, int offsetX, int offsetZ, int bitset, byte info) {
        if (entity == null) {
            return;
        }
        SceneWallDecoration decoration = new SceneWallDecoration();
        decoration.bitset = bitset;
        decoration.info = info;
        decoration.x = tileX * 128 + 64 + offsetX;
        decoration.z = tileZ * 128 + 64 + offsetZ;
        decoration.y = y;
        decoration.drawable = entity;
        decoration.type = type;
        decoration.rotation = rotation;
        for (int p = level; p >= 0; p--) {
            if (level_tiles[p][tileX][tileZ] == null) {
                level_tiles[p][tileX][tileZ] = new SceneTile(p, tileX, tileZ);
            }
        }
        level_tiles[level][tileX][tileZ].wall_decoration = decoration;
    }

    public boolean push_static(Drawable entity, int level, int tileX, int tileZ, int y, int width, int length, int yaw, int bitset, byte info) {
        if (entity == null) {
            return true;
        } else {
            int sceneX = tileX * 128 + 64 * width;
            int sceneZ = tileZ * 128 + 64 * length;
            return push(entity, level, tileX, tileZ, width, length, sceneX, sceneZ, y, yaw, bitset, info, false);
        }
    }

    public boolean push_temporary(Drawable entity, int level, int x, int z, int y, int yaw, int bitset, boolean forwardPadding, int padding) {
        if (entity == null) {
            return true;
        }

        int x0 = x - padding;
        int z0 = z - padding;
        int x1 = x + padding;
        int z1 = z + padding;

        if (forwardPadding) {
            if (yaw > 640 && yaw < 1408) {
                z1 += 128;
            }
            if (yaw > 1152 && yaw < 1920) {
                x1 += 128;
            }
            if (yaw > 1664 || yaw < 384) {
                z0 -= 128;
            }
            if (yaw > 128 && yaw < 896) {
                x0 -= 128;
            }
        }

        x0 /= 128;
        z0 /= 128;
        x1 /= 128;
        z1 /= 128;
        return push(entity, level, x0, z0, x1 - x0 + 1, z1 - z0 + 1, x, z, y, yaw, bitset, (byte) 0, true);
    }

    public boolean push(Drawable drawable, int level, int tx, int tz, int width, int length, int x, int z, int y, int yaw, int bitset, byte info, boolean temporary) {
        for (int i = tx; i < tx + width; i++) {
            for (int j = tz; j < tz + length; j++) {
                if (i < 0 || j < 0 || i >= max_tile_x || j >= max_tile_z) {
                    return false;
                }
                SceneTile tile = level_tiles[level][i][j];
                if (tile != null && tile.entity_count >= 5) {
                    return false;
                }
            }
        }

        SceneEntity entity = new SceneEntity();
        entity.bitset = bitset;
        entity.info = info;
        entity.level = level;
        entity.x = x;
        entity.z = z;
        entity.y = y;
        entity.drawable = drawable;
        entity.yaw = yaw;
        entity.min_tile_x = tx;
        entity.min_tile_z = tz;
        entity.max_tile_x = tx + width - 1;
        entity.max_tile_z = tz + length - 1;

        for (int i = tx; i < tx + width; i++) {
            for (int j = tz; j < tz + length; j++) {
                int spans = 0;

                if (i > tx) {
                    spans |= 0b0001;
                }

                if (i < tx + width - 1) {
                    spans |= 0b0100;
                }

                if (j > tz) {
                    spans |= 0b1000;
                }

                if (j < tz + length - 1) {
                    spans |= 0b0010;
                }

                for (int l = level; l >= 0; l--) {
                    if (level_tiles[l][i][j] == null) {
                        level_tiles[l][i][j] = new SceneTile(l, i, j);
                    }
                }

                SceneTile tile = level_tiles[level][i][j];
                tile.entities[tile.entity_count] = entity;
                tile.entity_spans[tile.entity_count] = spans;
                tile.entity_span |= spans;
                tile.entity_count++;
            }
        }

        if (temporary) {
            temporary_entities[temporary_entity_count++] = entity;
        }

        return true;
    }

    public void clear_temporary_entities() {
        for (int i = 0; i < temporary_entity_count; i++) {
            SceneEntity drawable = temporary_entities[i];
            remove_entity(drawable);
            temporary_entities[i] = null;
        }
        temporary_entity_count = 0;
    }

    public void remove_entity(SceneEntity entity) {
        for (int tx = entity.min_tile_x; tx <= entity.max_tile_x; tx++) {
            for (int tz = entity.min_tile_z; tz <= entity.max_tile_z; tz++) {
                SceneTile tile = level_tiles[entity.level][tx][tz];

                if (tile == null) {
                    continue;
                }

                for (int i = 0; i < tile.entity_count; i++) {
                    if (tile.entities[i] != entity) {
                        continue;
                    }

                    tile.entity_count--;

                    for (int j = i; j < tile.entity_count; j++) {
                        tile.entities[j] = tile.entities[j + 1];
                        tile.entity_spans[j] = tile.entity_spans[j + 1];
                    }
                    tile.entities[tile.entity_count] = null;
                    break;
                }

                tile.entity_span = 0;

                for (int i = 0; i < tile.entity_count; i++) {
                    tile.entity_span |= tile.entity_spans[i];
                }
            }
        }
    }

    public void set_wall_decoration_offset(int level, int stx, int stz, int offset) {
        SceneTile tile = level_tiles[level][stx][stz];
        if (tile == null) {
            return;
        }
        SceneWallDecoration wall_decoration = tile.wall_decoration;
        if (wall_decoration == null) {
            return;
        }
        int sx = stx * 128 + 64;
        int sz = stz * 128 + 64;
        wall_decoration.x = sx + (wall_decoration.x - sx) * offset / 16;
        wall_decoration.z = sz + (wall_decoration.z - sz) * offset / 16;
    }

    public void removeWall(int x, int level, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            tile.wall = null;
        }
    }

    public void removeWallDecoration(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            tile.wall_decoration = null;
        }
    }

    public void remove_centrepiece_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile == null) {
            return;
        }
        for (int i = 0; i < tile.entity_count; i++) {
            SceneEntity drawable = tile.entities[i];
            if ((drawable.bitset >> 29 & 3) == 2 && drawable.min_tile_x == x && drawable.min_tile_z == z) {
                remove_entity(drawable);
                return;
            }
        }
    }

    public void remove_ground_decoration_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            tile.ground_decoration = null;
        }
    }

    public void remove_item_stack_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            tile.item_stack = null;
        }
    }

    public SceneWall wall_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            return tile.wall;
        } else {
            return null;
        }
    }

    public SceneWallDecoration wall_decoration_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null) {
            return tile.wall_decoration;
        } else {
            return null;
        }
    }

    public SceneEntity drawable_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile == null) {
            return null;
        }
        for (int l = 0; l < tile.entity_count; l++) {
            SceneEntity loc = tile.entities[l];
            if ((loc.bitset >> 29 & 3) == 2 && loc.min_tile_x == x && loc.min_tile_z == z) {
                return loc;
            }
        }
        return null;
    }

    public SceneGroundDecoration ground_decoration_at(int z, int x, int level) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null && tile.ground_decoration != null) {
            return tile.ground_decoration;
        } else {
            return null;
        }
    }

    public int wall_uid_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile != null && tile.wall != null) {
            return tile.wall.bitset;
        } else {
            return 0;
        }
    }

    public int wall_decoration_uid_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile == null || tile.wall_decoration == null) {
            return 0;
        } else {
            return tile.wall_decoration.bitset;
        }
    }

    public int centrepiece_uid_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile == null) {
            return 0;
        }
        for (int l = 0; l < tile.entity_count; l++) {
            SceneEntity loc = tile.entities[l];
            if ((loc.bitset >> 29 & 3) == 2 && loc.min_tile_x == x && loc.min_tile_z == z) {
                return loc.bitset;
            }
        }
        return 0;
    }

    public int ground_decoration_uid_at(int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];
        if (tile == null || tile.ground_decoration == null) {
            return 0;
        } else {
            return tile.ground_decoration.bitset;
        }
    }

    public int find_info(int level, int x, int z, int bitset) {
        SceneTile tile = level_tiles[level][x][z];

        if (tile == null) {
            return -1;
        }

        if (tile.wall != null && tile.wall.bitset == bitset) {
            return tile.wall.info & 0xff;
        }

        if (tile.wall_decoration != null && tile.wall_decoration.bitset == bitset) {
            return tile.wall_decoration.info & 0xff;
        }

        if (tile.ground_decoration != null && tile.ground_decoration.bitset == bitset) {
            return tile.ground_decoration.info & 0xff;
        }

        for (int i = 0; i < tile.entity_count; i++) {
            if (tile.entities[i].bitset == bitset) {
                return tile.entities[i].info & 0xff;
            }
        }
        return -1;
    }

    /**
     * Merges touching normals of all Locs (Walls, Ground Decorations, etc) and then reapplies their lighting.
     */
    public void buildModels(int lightAmbient, int lightAttenuation, int lightSrcX, int lightSrcY, int lightSrcZ) {
        int lightMagnitude = (int) Math.sqrt(lightSrcX * lightSrcX + lightSrcY * lightSrcY + lightSrcZ * lightSrcZ);
        int attenuation = lightAttenuation * lightMagnitude >> 8;
        for (int level = 0; level < max_level; level++) {
            for (int tileX = 0; tileX < max_tile_x; tileX++) {
                for (int tileZ = 0; tileZ < max_tile_z; tileZ++) {
                    SceneTile tile = level_tiles[level][tileX][tileZ];

                    if (tile == null) {
                        continue;
                    }

                    SceneWall wall = tile.wall;

                    if (wall != null && wall.drawable_a != null && wall.drawable_a.normals != null) {
                        mergeLocNormals(level, 1, 1, tileX, tileZ, (Model) wall.drawable_a);

                        if (wall.drawable_b != null && wall.drawable_b.normals != null) {
                            mergeLocNormals(level, 1, 1, tileX, tileZ, (Model) wall.drawable_b);
                            mergeNormals((Model) wall.drawable_a, (Model) wall.drawable_b, 0, 0, 0, false);
                            ((Model) wall.drawable_b).buildLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                        }

                        ((Model) wall.drawable_a).buildLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                    }

                    for (int i = 0; i < tile.entity_count; i++) {
                        SceneEntity loc = tile.entities[i];

                        if (loc != null && loc.drawable != null && loc.drawable.normals != null) {
                            mergeLocNormals(level, loc.max_tile_x - loc.min_tile_x + 1, loc.max_tile_z - loc.min_tile_z + 1, tileX, tileZ, (Model) loc.drawable);
                            ((Model) loc.drawable).buildLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                        }
                    }

                    SceneGroundDecoration decoration = tile.ground_decoration;
                    if (decoration != null && decoration.entity.normals != null) {
                        mergeGroundDecorationNormals(tileX, level, (Model) decoration.entity, tileZ);
                        ((Model) decoration.entity).buildLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
                    }
                }
            }
        }
    }

    public void mergeGroundDecorationNormals(int tileX, int level, Model model, int tileZ) {
        if (tileX < max_tile_x) {
            SceneTile tile = level_tiles[level][tileX + 1][tileZ];
            if (tile != null && tile.ground_decoration != null && tile.ground_decoration.entity.normals != null) {
                mergeNormals(model, (Model) tile.ground_decoration.entity, 128, 0, 0, true);
            }
        }

        if (tileZ < max_tile_x) {
            SceneTile tile = level_tiles[level][tileX][tileZ + 1];
            if (tile != null && tile.ground_decoration != null && tile.ground_decoration.entity.normals != null) {
                mergeNormals(model, (Model) tile.ground_decoration.entity, 0, 0, 128, true);
            }
        }

        if (tileX < max_tile_x && tileZ < max_tile_z) {
            SceneTile tile = level_tiles[level][tileX + 1][tileZ + 1];
            if (tile != null && tile.ground_decoration != null && tile.ground_decoration.entity.normals != null) {
                mergeNormals(model, (Model) tile.ground_decoration.entity, 128, 0, 128, true);
            }
        }

        if (tileX < max_tile_x && tileZ > 0) {
            SceneTile tile = level_tiles[level][tileX + 1][tileZ - 1];
            if (tile != null && tile.ground_decoration != null && tile.ground_decoration.entity.normals != null) {
                mergeNormals(model, (Model) tile.ground_decoration.entity, 128, 0, -128, true);
            }
        }
    }

    public void mergeLocNormals(int level, int tileSizeX, int tileSizeZ, int tileX, int tileZ, Model model) {
        boolean allowFaceRemoval = true;
        int minTileX = tileX;
        int maxTileX = tileX + tileSizeX;
        int minTileZ = tileZ - 1;
        int maxTileZ = tileZ + tileSizeZ;

        for (int l = level; l <= level + 1; l++) {
            if (l == max_level) {
                continue;
            }

            for (int x = minTileX; x <= maxTileX; x++) {
                if (x < 0 || x >= this.max_tile_x) {
                    continue;
                }

                for (int z = minTileZ; z <= maxTileZ; z++) {
                    if (z < 0 || z >= this.max_tile_z || allowFaceRemoval && x < maxTileX && z < maxTileZ && (z >= tileZ || x == tileX)) {
                        continue;
                    }

                    SceneTile tile = level_tiles[l][x][z];

                    if (tile == null) {
                        continue;
                    }

                    int offsetY = (level_heightmaps[l][x][z] + level_heightmaps[l][x + 1][z] + level_heightmaps[l][x][z + 1] + level_heightmaps[l][x + 1][z + 1]) / 4 - (level_heightmaps[level][tileX][tileZ] + level_heightmaps[level][tileX + 1][tileZ] + level_heightmaps[level][tileX][tileZ + 1] + level_heightmaps[level][tileX + 1][tileZ + 1]) / 4;
                    SceneWall wall = tile.wall;

                    int offsetX = (x - tileX) * 128 + (1 - tileSizeX) * 64;
                    int offsetZ = (z - tileZ) * 128 + (1 - tileSizeZ) * 64;

                    if (wall != null && wall.drawable_a != null && wall.drawable_a.normals != null) {
                        mergeNormals(model, (Model) wall.drawable_a, offsetX, offsetY, offsetZ, allowFaceRemoval);
                    }

                    if (wall != null && wall.drawable_b != null && wall.drawable_b.normals != null) {
                        mergeNormals(model, (Model) wall.drawable_b, offsetX, offsetY, offsetZ, allowFaceRemoval);
                    }

                    for (int i = 0; i < tile.entity_count; i++) {
                        SceneEntity loc = tile.entities[i];

                        if (loc != null && loc.drawable != null && loc.drawable.normals != null) {
                            int locTileSizeX = loc.max_tile_x - loc.min_tile_x + 1;
                            int locTileSizeZ = loc.max_tile_z - loc.min_tile_z + 1;
                            mergeNormals(model, (Model) loc.drawable, (loc.min_tile_x - tileX) * 128 + (locTileSizeX - tileSizeX) * 64, offsetY, (loc.min_tile_z - tileZ) * 128 + (locTileSizeZ - tileSizeZ) * 64, allowFaceRemoval);
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
            Normal normalA = modelA.normals[vertexA];
            Normal originalNormalA = modelA.normals_copy[vertexA];

            // undefined normal
            if (originalNormalA.w == 0) {
                continue;
            }

            int y = modelA.vertexY[vertexA] - offsetY;

            if (y > modelB.maxY) {
                continue;
            }

            int x = modelA.vertexX[vertexA] - offsetX;

            if (x < modelB.minX || x > modelB.maxX) {
                continue;
            }

            int z = modelA.vertexZ[vertexA] - offsetZ;

            if (z < modelB.minZ || z > modelB.maxZ) {
                continue;
            }

            for (int vertexB = 0; vertexB < modelB.vertexCount; vertexB++) {
                Normal normalB = modelB.normals[vertexB];
                Normal originalNormalB = modelB.normals_copy[vertexB];

                if (x == modelB.vertexX[vertexB] && z == modelB.vertexZ[vertexB] && y == modelB.vertexY[vertexB] && originalNormalB.w != 0) {
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

        if (merged < 3 || !allowFaceRemoval) {
            return;
        }

        // if every vertex of a given face had their normals merged, clear the face info causing that face not to draw.
        for (int i = 0; i < modelA.fcnt; i++) {
            if (mergeIndexA[modelA.faceVertexA[i]] == tmpMergeIndex && mergeIndexA[modelA.faceVertexB[i]] == tmpMergeIndex && mergeIndexA[modelA.faceVertexC[i]] == tmpMergeIndex) {
                modelA.ftype[i] = -1;
            }
        }

        // same as above but for model B
        for (int i = 0; i < modelB.fcnt; i++) {
            if (mergeIndexB[modelB.faceVertexA[i]] == tmpMergeIndex && mergeIndexB[modelB.faceVertexB[i]] == tmpMergeIndex && mergeIndexB[modelB.faceVertexC[i]] == tmpMergeIndex) {
                modelB.ftype[i] = -1;
            }
        }
    }

    public void drawMinimapTile(int[] dst, int offset, int step, int level, int x, int z) {
        SceneTile tile = level_tiles[level][x][z];

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
        int background = overlay.background_rgb;
        int foreground = overlay.foreground_rgb;
        int[] mask = MINIMAP_TILE_MASK[shape];
        int[] rotation = MINIMAP_TILE_ROTATION_MAP[angle];
        int off = 0;

        if (background != 0) {
            for (int i = 0; i < 4; i++) {
                dst[offset] = mask[rotation[off++]] != 0 ? foreground : background;
                dst[offset + 1] = mask[rotation[off++]] != 0 ? foreground : background;
                dst[offset + 2] = mask[rotation[off++]] != 0 ? foreground : background;
                dst[offset + 3] = mask[rotation[off++]] != 0 ? foreground : background;
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
        accept_input = true;
        Scene.mouseX = mouseX;
        Scene.mouseY = mouseY;
        input_tile_x = -1;
        input_tile_z = -1;
    }

    public void draw(int eye_x, int eye_z, int eye_yaw, int eye_y, int top_level, int eye_pitch) {
        cycle++;

        sin_eye_pitch = Model.sin[eye_pitch];
        cos_eye_pitch = Model.cos[eye_pitch];
        sin_eye_yaw = Model.sin[eye_yaw];
        cos_eye_yaw = Model.cos[eye_yaw];

        visibility_map = visibility_matrix[(eye_pitch - 128) / 32][eye_yaw / 64];

        if (eye_x < 0) {
            eye_x = 0;
        } else if (eye_x >= max_tile_x * 128) {
            eye_x = max_tile_x * 128 - 1;
        }
        if (eye_z < 0) {
            eye_z = 0;
        } else if (eye_z >= max_tile_z * 128) {
            eye_z = max_tile_z * 128 - 1;
        }

        Scene.eye_x = eye_x;
        Scene.eye_y = eye_y;
        Scene.eye_z = eye_z;

        eye_tile_x = eye_x / 128;
        eye_tile_z = eye_z / 128;

        Scene.top_level = top_level;

        min_draw_tile_x = eye_tile_x - 25;
        min_draw_tile_z = eye_tile_z - 25;
        max_draw_tile_x = eye_tile_x + 25;
        max_draw_tile_z = eye_tile_z + 25;

        if (min_draw_tile_x < 0) {
            min_draw_tile_x = 0;
        }

        if (min_draw_tile_z < 0) {
            min_draw_tile_z = 0;
        }

        if (max_draw_tile_x > max_tile_x) {
            max_draw_tile_x = max_tile_x;
        }

        if (max_draw_tile_z > max_tile_z) {
            max_draw_tile_z = max_tile_z;
        }

        update_occluders();

        remaining_tiles = 0;
        for (int level = min_level; level < max_level; level++) {
            SceneTile[][] tiles = level_tiles[level];
            for (int x = min_draw_tile_x; x < max_draw_tile_x; x++) {
                for (int z = min_draw_tile_z; z < max_draw_tile_z; z++) {
                    SceneTile tile = tiles[x][z];

                    if (tile == null) {
                        continue;
                    }

                    if (tile.draw_level > top_level || !visibility_map[x - eye_tile_x + 25][z - eye_tile_z + 25] && level_heightmaps[level][x][z] - eye_y < 2000) {
                        tile.visible = false;
                        tile.update = false;
                        tile.check_entity_spans = 0;
                    } else {
                        tile.visible = true;
                        tile.update = true;
                        tile.should_draw_entities = tile.entity_count > 0;
                        remaining_tiles++;
                    }
                }
            }
        }

        for (int level = min_level; level < max_level; level++) {
            SceneTile[][] tiles = level_tiles[level];

            for (int dx = -25; dx <= 0; dx++) {
                int right_x = eye_tile_x + dx;
                int left_x = eye_tile_x - dx;

                if (right_x < min_draw_tile_x && left_x >= max_draw_tile_x) {
                    continue;
                }

                for (int dz = -25; dz <= 0; dz++) {
                    int front_z = eye_tile_z + dz;
                    int back_z = eye_tile_z - dz;

                    if (right_x >= min_draw_tile_x) {
                        if (front_z >= min_draw_tile_z) {
                            SceneTile tile = tiles[right_x][front_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, true);
                            }
                        }

                        if (back_z < max_draw_tile_z) {
                            SceneTile tile = tiles[right_x][back_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, true);
                            }
                        }
                    }

                    if (left_x < max_draw_tile_x) {
                        if (front_z >= min_draw_tile_z) {
                            SceneTile tile = tiles[left_x][front_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, true);
                            }
                        }

                        if (back_z < max_draw_tile_z) {
                            SceneTile tile = tiles[left_x][back_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, true);
                            }
                        }
                    }

                    if (remaining_tiles == 0) {
                        accept_input = false;
                        return;
                    }
                }
            }
        }

        for (int level = min_level; level < max_level; level++) {
            SceneTile[][] tiles = level_tiles[level];

            for (int dx = -25; dx <= 0; dx++) {
                int right_x = eye_tile_x + dx;
                int left_x = eye_tile_x - dx;

                if (right_x < min_draw_tile_x && left_x >= max_draw_tile_x) {
                    continue;
                }

                for (int dz = -25; dz <= 0; dz++) {
                    int front_z = eye_tile_z + dz;
                    int back_z = eye_tile_z - dz;

                    if (right_x >= min_draw_tile_x) {
                        if (front_z >= min_draw_tile_z) {
                            SceneTile tile = tiles[right_x][front_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, false);
                            }
                        }

                        if (back_z < max_draw_tile_z) {
                            SceneTile tile = tiles[right_x][back_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, false);
                            }
                        }
                    }
                    if (left_x < max_draw_tile_x) {
                        if (front_z >= min_draw_tile_z) {
                            SceneTile tile = tiles[left_x][front_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, false);
                            }
                        }

                        if (back_z < max_draw_tile_z) {
                            SceneTile tile = tiles[left_x][back_z];
                            if (tile != null && tile.visible) {
                                draw_tile(tile, false);
                            }
                        }
                    }

                    if (remaining_tiles == 0) {
                        accept_input = false;
                        return;
                    }
                }
            }
        }
        accept_input = false;
    }

    public void draw_tile(SceneTile next, boolean check_adjacent) {
        draw_tile_queue.push_back(next);

        while (true) {
            SceneTile tile;

            do {
                tile = (SceneTile) draw_tile_queue.pollFront();
                if (tile == null) {
                    return;
                }
            } while (!tile.update);

            int tile_x = tile.x;
            int tile_z = tile.z;
            int tile_level = tile.level;
            int tile_occlude_level = tile.occlude_level;
            SceneTile[][] tiles = level_tiles[tile_level];

            if (tile.visible) {
                if (check_adjacent) {
                    if (adjacent_tile_has_update_no_spans(tile, tile_x, tile_z, tile_level, tiles)) {
                        continue;
                    }
                } else {
                    check_adjacent = true;
                }

                tile.visible = false;

                if (tile.bridge != null) {
                    draw_bridge_tile(tile_x, tile_z, tile.bridge);
                }

                boolean floor_drawn = draw_tile_underlay_or_overlay(tile, tile_x, tile_z, tile_occlude_level);

                try_draw_front_walls(tile, tile_x, tile_z, tile_occlude_level);

                if (floor_drawn) {
                    try_draw_ground_decoration(tile);
                    try_draw_item_stack_no_offset(tile);
                }

                enqueue_affected_tiles(tile, tile_x, tile_z, tiles);
            }

            try_draw_wall(tile, tile_x, tile_z, tile_occlude_level);

            if (try_draw_entities(tile, tile_x, tile_z, tile_occlude_level, tiles)) {
                continue;
            }

            if (tile.update && tile.check_entity_spans == 0) {
                if (closer_tile_has_update(tile_x, tile_z, tiles)) {
                    continue;
                }
                tile.update = false;
                remaining_tiles--;

                try_draw_item_stack(tile);
                try_draw_walls_reversed(tile, tile_x, tile_z, tile_occlude_level);
                enqueue_pending_adjacent_tiles(tile_x, tile_z, tile_level, tiles);
            }
        }
    }

    private void try_draw_wall(SceneTile tile, int tile_x, int tile_z, int tile_occlude_level) {
        if (tile.check_entity_spans == 0) {
            return;
        }

        for (int i = 0; i < tile.entity_count; i++) {
            if (!tile.entities[i].drawn() && (tile.entity_spans[i] & tile.check_entity_spans) == tile.block_entity_spans) {
                return;
            }
        }

        SceneWall wall = tile.wall;

        if (wall_visible(tile_occlude_level, tile_x, tile_z, wall.type_a)) {
            wall.drawable_a.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
        }

        tile.check_entity_spans = 0;
    }

    private static void try_draw_item_stack(SceneTile tile) {
        SceneItemStack stack = tile.item_stack;

        if (stack != null && stack.offset != 0) {
            draw_item_stack(stack, stack.offset);
        }
    }

    private void enqueue_pending_adjacent_tiles(int tile_x, int tile_z, int tile_level, SceneTile[][] tiles) {
        if (tile_level < max_level - 1) {
            SceneTile above = level_tiles[tile_level + 1][tile_x][tile_z];
            if (above != null && above.update) {
                draw_tile_queue.push_back(above);
            }
        }

        if (tile_x < eye_tile_x) {
            SceneTile adjacent = tiles[tile_x + 1][tile_z];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_z < eye_tile_z) {
            SceneTile adjacent = tiles[tile_x][tile_z + 1];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_x > eye_tile_x) {
            SceneTile adjacent = tiles[tile_x - 1][tile_z];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_z > eye_tile_z) {
            SceneTile adjacent = tiles[tile_x][tile_z - 1];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }
    }

    private void try_draw_walls_reversed(SceneTile tile, int tile_x, int tile_z, int tile_occlude_level) {
        if (tile.back_wall_types == 0) {
            return;
        }

        SceneWallDecoration wall_decoration = tile.wall_decoration;

        if (wall_decoration != null && visible(tile_occlude_level, tile_x, tile_z, wall_decoration.drawable.min_y)) {
            draw_wall_decoration(tile.back_wall_types, wall_decoration, false);
        }

        SceneWall wall = tile.wall;

        if (wall == null) {
            return;
        }

        if ((wall.type_b & tile.back_wall_types) != 0 && wall_visible(tile_occlude_level, tile_x, tile_z, wall.type_b)) {
            wall.drawable_b.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
        }

        if ((wall.type_a & tile.back_wall_types) != 0 && wall_visible(tile_occlude_level, tile_x, tile_z, wall.type_a)) {
            wall.drawable_a.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
        }
    }

    private static boolean closer_tile_has_update(int tile_x, int tile_z, SceneTile[][] tiles) {
        if (tile_x <= eye_tile_x && tile_x > min_draw_tile_x) {
            SceneTile adjacent = tiles[tile_x - 1][tile_z];
            if (adjacent != null && adjacent.update) {
                return true;
            }
        }
        if (tile_x >= eye_tile_x && tile_x < max_draw_tile_x - 1) {
            SceneTile adjacent = tiles[tile_x + 1][tile_z];
            if (adjacent != null && adjacent.update) {
                return true;
            }
        }
        if (tile_z <= eye_tile_z && tile_z > min_draw_tile_z) {
            SceneTile adjacent = tiles[tile_x][tile_z - 1];
            if (adjacent != null && adjacent.update) {
                return true;
            }
        }
        if (tile_z >= eye_tile_z && tile_z < max_draw_tile_z - 1) {
            SceneTile adjacent = tiles[tile_x][tile_z + 1];
            return adjacent != null && adjacent.update;
        }
        return false;
    }

    private boolean try_draw_entities(SceneTile tile, int tile_x, int tile_z, int tile_occlude_level, SceneTile[][] tiles) {
        if (!tile.should_draw_entities) {
            return false;
        }

        try {
            int count = tile.entity_count;
            tile.should_draw_entities = false;
            int drawable_buffer_queued = 0;

            iterate_locs:
            for (int i = 0; i < count; i++) {
                SceneEntity loc = tile.entities[i];

                if (loc.drawn()) {
                    continue;
                }

                for (int x = loc.min_tile_x; x <= loc.max_tile_x; x++) {
                    for (int z = loc.min_tile_z; z <= loc.max_tile_z; z++) {
                        SceneTile other = tiles[x][z];

                        if (!other.visible) {
                            if (other.check_entity_spans == 0) {
                                continue;
                            }

                            int spans = 0;

                            if (x > loc.min_tile_x) {
                                spans |= 0b0001;
                            }

                            if (x < loc.max_tile_x) {
                                spans |= 0b0100;
                            }

                            if (z > loc.min_tile_z) {
                                spans |= 0b1000;
                            }

                            if (z < loc.max_tile_z) {
                                spans |= 0b0010;
                            }

                            if ((spans & other.check_entity_spans) != tile.block_entity_spans_inverted) {
                                continue;
                            }
                        }

                        tile.should_draw_entities = true;
                        continue iterate_locs;
                    }
                }

                drawable_buffer[drawable_buffer_queued++] = loc;

                int min_dx = eye_tile_x - loc.min_tile_x;
                int max_dx = loc.max_tile_x - eye_tile_x;

                if (max_dx > min_dx) {
                    min_dx = max_dx;
                }

                int min_dz = eye_tile_z - loc.min_tile_z;
                int max_dz = loc.max_tile_z - eye_tile_z;

                if (max_dz > min_dz) {
                    loc.distance = min_dx + max_dz;
                } else {
                    loc.distance = min_dx + min_dz;
                }
            }

            while (true) {
                int farthest_distance = -50;
                int farthest_index = -1;

                for (int index = 0; index < drawable_buffer_queued; index++) {
                    SceneEntity loc = drawable_buffer[index];

                    if (!loc.drawn()) {
                        if (loc.distance > farthest_distance) {
                            farthest_distance = loc.distance;
                            farthest_index = index;
                        } else if (loc.distance == farthest_distance) {
                            int dx0 = loc.x - eye_x;
                            int dz0 = loc.z - eye_z;
                            int dx1 = drawable_buffer[farthest_index].x - eye_x;
                            int dz1 = drawable_buffer[farthest_index].z - eye_z;

                            if (dx0 * dx0 + dz0 * dz0 > dx1 * dx1 + dz1 * dz1) {
                                farthest_index = index;
                            }
                        }
                    }
                }

                if (farthest_index == -1) {
                    break;
                }

                SceneEntity farthest = drawable_buffer[farthest_index];
                farthest.draw_cycle = cycle;

                if (area_visible(tile_occlude_level, farthest.min_tile_x, farthest.max_tile_x, farthest.min_tile_z, farthest.max_tile_z, farthest.drawable.min_y)) {
                    farthest.drawable.draw(farthest.yaw, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, farthest.x - eye_x, farthest.y - eye_y, farthest.z - eye_z, farthest.bitset);
                }

                for (int x = farthest.min_tile_x; x <= farthest.max_tile_x; x++) {
                    for (int z = farthest.min_tile_z; z <= farthest.max_tile_z; z++) {
                        SceneTile occupied = tiles[x][z];

                        if (occupied.check_entity_spans != 0) {
                            draw_tile_queue.push_back(occupied);
                        } else if ((x != tile_x || z != tile_z) && occupied.update) {
                            draw_tile_queue.push_back(occupied);
                        }
                    }
                }
            }

            if (tile.should_draw_entities) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            tile.should_draw_entities = false;
        }
        return false;
    }

    private static void enqueue_affected_tiles(SceneTile tile, int tile_x, int tile_z, SceneTile[][] tiles) {
        int spans = tile.entity_span;

        if (spans == 0) {
            return;
        }

        if (tile_x < eye_tile_x && (spans & 0x4) != 0) {
            SceneTile adjacent = tiles[tile_x + 1][tile_z];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_z < eye_tile_z && (spans & 0x2) != 0) {
            SceneTile adjacent = tiles[tile_x][tile_z + 1];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_x > eye_tile_x && (spans & 0x1) != 0) {
            SceneTile adjacent = tiles[tile_x - 1][tile_z];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }

        if (tile_z > eye_tile_z && (spans & 0x8) != 0) {
            SceneTile adjacent = tiles[tile_x][tile_z - 1];
            if (adjacent != null && adjacent.update) {
                draw_tile_queue.push_back(adjacent);
            }
        }
    }

    private static void try_draw_item_stack_no_offset(SceneTile tile) {
        SceneItemStack item_stack = tile.item_stack;

        if (item_stack != null && item_stack.offset == 0) {
            draw_item_stack(item_stack, 0);
        }
    }

    private static void try_draw_ground_decoration(SceneTile tile) {
        SceneGroundDecoration ground_decoration = tile.ground_decoration;

        if (ground_decoration != null) {
            ground_decoration.entity.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, ground_decoration.x - eye_x, ground_decoration.y - eye_y, ground_decoration.z - eye_z, ground_decoration.bitset);
        }
    }

    private void try_draw_front_walls(SceneTile tile, int tile_x, int tile_z, int tile_occlude_level) {
        int direction = 0;
        int front_wall_types = 0;

        SceneWall wall = tile.wall;
        SceneWallDecoration wall_decoration = tile.wall_decoration;

        if (wall != null || wall_decoration != null) {
            if (eye_tile_x == tile_x) {
                direction++;
            } else if (eye_tile_x < tile_x) {
                direction += 2;
            }

            if (eye_tile_z == tile_z) {
                direction += 3;
            } else if (eye_tile_z > tile_z) {
                direction += 6;
            }

            front_wall_types = FRONT_WALL_TYPES[direction];
            tile.back_wall_types = BACK_WALL_TYPES[direction];
        }

        if (wall != null) {
            update_loc_spans(tile, direction, wall);

            if ((wall.type_a & front_wall_types) != 0 && wall_visible(tile_occlude_level, tile_x, tile_z, wall.type_a)) {
                wall.drawable_a.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
            }

            if ((wall.type_b & front_wall_types) != 0 && wall_visible(tile_occlude_level, tile_x, tile_z, wall.type_b)) {
                wall.drawable_b.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
            }
        }

        if (wall_decoration != null && visible(tile_occlude_level, tile_x, tile_z, wall_decoration.drawable.min_y)) {
            draw_wall_decoration(front_wall_types, wall_decoration, true);
        }
    }

    private static void update_loc_spans(SceneTile tile, int direction, SceneWall wall) {
        if ((wall.type_a & DIRECTION_ALLOW_WALL_CORNER_TYPE[direction]) != 0) {
            switch (wall.type_a) {
                case 16 -> {
                    tile.check_entity_spans = 0b0011;
                    tile.block_entity_spans = WALL_CORNER_TYPE_16_BLOCK_LOC_SPANS[direction];
                    tile.block_entity_spans_inverted = 0b0011 - tile.block_entity_spans;
                }
                case 32 -> {
                    tile.check_entity_spans = 0b0110;
                    tile.block_entity_spans = WALL_CORNER_TYPE_32_BLOCK_LOC_SPANS[direction];
                    tile.block_entity_spans_inverted = 0b0110 - tile.block_entity_spans;
                }
                case 64 -> {
                    tile.check_entity_spans = 0b1100;
                    tile.block_entity_spans = WALL_CORNER_TYPE_64_BLOCK_LOC_SPANS[direction];
                    tile.block_entity_spans_inverted = 0b1100 - tile.block_entity_spans;
                }
                case 128 -> {
                    tile.check_entity_spans = 0b1001;
                    tile.block_entity_spans = WALL_CORNER_TYPE_128_BLOCK_LOC_SPANS[direction];
                    tile.block_entity_spans_inverted = 0b1001 - tile.block_entity_spans;
                }
            }
        } else {
            tile.check_entity_spans = 0;
        }
    }

    private boolean adjacent_tile_has_update_no_spans(SceneTile tile, int tile_x, int tile_z, int tile_level, SceneTile[][] tiles) {
        if (tile_level > 0) {
            SceneTile adjacent = level_tiles[tile_level - 1][tile_x][tile_z];

            if (adjacent != null && adjacent.update) {
                return true;
            }
        }

        if (tile_x <= eye_tile_x && tile_x > min_draw_tile_x) {
            SceneTile adjacent = tiles[tile_x - 1][tile_z];

            if (adjacent != null && adjacent.update && (adjacent.visible || (tile.entity_span & 1) == 0)) {
                return true;
            }
        }

        if (tile_x >= eye_tile_x && tile_x < max_draw_tile_x - 1) {
            SceneTile adjacent = tiles[tile_x + 1][tile_z];

            if (adjacent != null && adjacent.update && (adjacent.visible || (tile.entity_span & 4) == 0)) {
                return true;
            }
        }

        if (tile_z <= eye_tile_z && tile_z > min_draw_tile_z) {
            SceneTile adjacent = tiles[tile_x][tile_z - 1];

            if (adjacent != null && adjacent.update && (adjacent.visible || (tile.entity_span & 8) == 0)) {
                return true;
            }
        }

        if (tile_z >= eye_tile_z && tile_z < max_draw_tile_z - 1) {
            SceneTile other = tiles[tile_x][tile_z + 1];

            return other != null && other.update && (other.visible || (tile.entity_span & 2) == 0);
        }
        return false;
    }

    private static void draw_item_stack(SceneItemStack stack, int offset) {
        if (stack.bottom != null) {
            stack.bottom.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, stack.x - eye_x, stack.y - eye_y - offset, stack.z - eye_z, stack.bitset);
        }
        if (stack.middle != null) {
            stack.middle.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, stack.x - eye_x, stack.y - eye_y - offset, stack.z - eye_z, stack.bitset);
        }
        if (stack.top != null) {
            stack.top.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, stack.x - eye_x, stack.y - eye_y - offset, stack.z - eye_z, stack.bitset);
        }
    }

    private static void draw_wall_decoration(int allowWallTypes, SceneWallDecoration decor, boolean front) {
        if ((decor.type & allowWallTypes) != 0) {
            decor.drawable.draw(decor.rotation, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, decor.x - eye_x, decor.y - eye_y, decor.z - eye_z, decor.bitset);
        } else if ((decor.type & 0x300) != 0) {
            int x = decor.x - eye_x;
            int y = decor.y - eye_y;
            int z = decor.z - eye_z;
            int rotation = decor.rotation;

            int nearest_x;
            int nearest_z;

            if (rotation == 1 || rotation == 2) {
                nearest_x = -x;
            } else {
                nearest_x = x;
            }

            if (rotation == 2 || rotation == 3) {
                nearest_z = -z;
            } else {
                nearest_z = z;
            }

            if ((decor.type & 0x100) != 0) {
                boolean draw = false;

                if (front && nearest_z < nearest_x) {
                    draw = true;
                } else if (!front && nearest_z >= nearest_x) {
                    draw = true;
                }

                if (draw) {
                    int offset_x = x + WALL_DECORATION_INSET_X[rotation];
                    int offset_z = z + WALL_DECORATION_INSET_Z[rotation];
                    decor.drawable.draw(rotation * 512 + 256, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, offset_x, y, offset_z, decor.bitset);
                }
            }

            if ((decor.type & 0x200) != 0) {
                boolean draw = false;

                if (front && nearest_z > nearest_x) {
                    draw = true;
                } else if (!front && nearest_z <= nearest_x) {
                    draw = true;
                }

                if (draw) {
                    int drawX = x + WALL_DECORATION_OUTSET_X[rotation];
                    int drawZ = z + WALL_DECORATION_OUTSET_Z[rotation];
                    decor.drawable.draw(rotation * 512 + 1280 & 0x7ff, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, drawX, y, drawZ, decor.bitset);
                }
            }
        }
    }

    private boolean draw_tile_underlay_or_overlay(SceneTile tile, int x, int z, int level) {
        if (tile.underlay != null) {
            if (tile_visible(level, x, z)) {
                draw_tile_underlay(tile.underlay, level, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, x, z);
                return true;
            }
        } else if (tile.overlay != null && tile_visible(level, x, z)) {
            draw_tile_overlay(x, sin_eye_pitch, sin_eye_yaw, tile.overlay, cos_eye_pitch, z, cos_eye_yaw);
            return true;
        }
        return false;
    }

    private void draw_bridge_tile(int tileX, int tileZ, SceneTile bridge) {
        draw_tile_underlay_or_overlay(bridge, tileX, tileZ, 0);

        SceneWall wall = bridge.wall;

        if (wall != null) {
            wall.drawable_a.draw(0, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, wall.x - eye_x, wall.y - eye_y, wall.z - eye_z, wall.bitset);
        }

        for (int i = 0; i < bridge.entity_count; i++) {
            SceneEntity loc = bridge.entities[i];

            if (loc != null) {
                loc.drawable.draw(loc.yaw, sin_eye_pitch, cos_eye_pitch, sin_eye_yaw, cos_eye_yaw, loc.x - eye_x, loc.y - eye_y, loc.z - eye_z, loc.bitset);
            }
        }
    }

    public void draw_tile_underlay(SceneTileUnderlay underlay, int level, int sin_eye_pitch, int cos_eye_pitch, int sin_eye_yaw, int cos_eye_yaw, int tile_x, int tile_z) {
        int x3;
        int x0 = x3 = (tile_x << 7) - eye_x;
        int z1;
        int z0 = z1 = (tile_z << 7) - eye_z;
        int x2;
        int x1 = x2 = x0 + 128;
        int z3;
        int z2 = z3 = z0 + 128;

        int y0 = level_heightmaps[level][tile_x][tile_z] - eye_y;
        int y1 = level_heightmaps[level][tile_x + 1][tile_z] - eye_y;
        int y2 = level_heightmaps[level][tile_x + 1][tile_z + 1] - eye_y;
        int y3 = level_heightmaps[level][tile_x][tile_z + 1] - eye_y;

        int tmp = z0 * sin_eye_yaw + x0 * cos_eye_yaw >> 16;
        z0 = z0 * cos_eye_yaw - x0 * sin_eye_yaw >> 16;
        x0 = tmp;

        tmp = y0 * cos_eye_pitch - z0 * sin_eye_pitch >> 16;
        z0 = y0 * sin_eye_pitch + z0 * cos_eye_pitch >> 16;
        y0 = tmp;

        if (z0 < 50) {
            return;
        }

        tmp = z1 * sin_eye_yaw + x1 * cos_eye_yaw >> 16;
        z1 = z1 * cos_eye_yaw - x1 * sin_eye_yaw >> 16;
        x1 = tmp;

        tmp = y1 * cos_eye_pitch - z1 * sin_eye_pitch >> 16;
        z1 = y1 * sin_eye_pitch + z1 * cos_eye_pitch >> 16;
        y1 = tmp;

        if (z1 < 50) {
            return;
        }

        tmp = z2 * sin_eye_yaw + x2 * cos_eye_yaw >> 16;
        z2 = z2 * cos_eye_yaw - x2 * sin_eye_yaw >> 16;
        x2 = tmp;

        tmp = y2 * cos_eye_pitch - z2 * sin_eye_pitch >> 16;
        z2 = y2 * sin_eye_pitch + z2 * cos_eye_pitch >> 16;
        y2 = tmp;

        if (z2 < 50) {
            return;
        }

        tmp = z3 * sin_eye_yaw + x3 * cos_eye_yaw >> 16;
        z3 = z3 * cos_eye_yaw - x3 * sin_eye_yaw >> 16;
        x3 = tmp;

        tmp = y3 * cos_eye_pitch - z3 * sin_eye_pitch >> 16;
        z3 = y3 * sin_eye_pitch + z3 * cos_eye_pitch >> 16;
        y3 = tmp;

        if (z3 < 50) {
            return;
        }

        int px0 = Draw3D.center_x + (x0 << 9) / z0;
        int py0 = Draw3D.center_y + (y0 << 9) / z0;
        int px1 = Draw3D.center_x + (x1 << 9) / z1;
        int py1 = Draw3D.center_y + (y1 << 9) / z1;
        int px2 = Draw3D.center_x + (x2 << 9) / z2;
        int py2 = Draw3D.center_y + (y2 << 9) / z2;
        int px3 = Draw3D.center_x + (x3 << 9) / z3;
        int py3 = Draw3D.center_y + (y3 << 9) / z3;

        Draw3D.transparency = 0;

        if ((px2 - px3) * (py1 - py3) - (py2 - py3) * (px1 - px3) > 0) {
            Draw3D.clipX = px2 < 0 || px3 < 0 || px1 < 0 || px2 > Draw2D.boundX || px3 > Draw2D.boundX || px1 > Draw2D.boundX;

            if (accept_input && pick_triangle(mouseX, mouseY, py2, py3, py1, px2, px3, px1)) {
                input_tile_x = tile_x;
                input_tile_z = tile_z;
            }

            if (underlay.textureID == -1) {
                if (underlay.northeastColor != 12345678) {
                    Draw3D.fillGouraudTriangle(px2, py2, px3, py3, px1, py1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor);
                }
            } else                 if (underlay.flat) {
                Draw3D.fillTexturedTriangle(py2, py3, py1, px2, px3, px1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor, x0,x1,x3,y0,y1,y3,z0,z1,z3, underlay.textureID);
            } else {
                Draw3D.fillTexturedTriangle(py2, py3, py1, px2, px3, px1, underlay.northeastColor, underlay.northwestColor, underlay.southeastColor, x2,x3,x1,y2,y3,y1,z2,z3,z1, underlay.textureID);
            }
        }

        if ((px0 - px1) * (py3 - py1) - (py0 - py1) * (px3 - px1) > 0) {
            Draw3D.clipX = px0 < 0 || px1 < 0 || px3 < 0 || px0 > Draw2D.boundX || px1 > Draw2D.boundX || px3 > Draw2D.boundX;

            if (accept_input && pick_triangle(mouseX, mouseY, py0, py1, py3, px0, px1, px3)) {
                input_tile_x = tile_x;
                input_tile_z = tile_z;
            }

            if (underlay.textureID == -1) {
                if (underlay.southwestColor != 12345678) {
                    Draw3D.fillGouraudTriangle(px0, py0, px1, py1, px3, py3, underlay.southwestColor, underlay.southeastColor, underlay.northwestColor);
                }
            } else {
                Draw3D.fillTexturedTriangle(py0, py1, py3, px0, px1, px3, underlay.southwestColor, underlay.southeastColor, underlay.northwestColor, x0,x1,x3,y0,y1,y3,z0,z1,z3, underlay.textureID);
            }
        }
    }

    public void draw_tile_overlay(int tileX, int sinEyePitch, int sinEyeYaw, SceneTileOverlay overlay, int cosEyePitch, int tileZ, int cosEyeYaw) {
        int counter = overlay.x.length;
        for (int v = 0; v < counter; v++) {
            int x = overlay.x[v] - eye_x;
            int y = overlay.y[v] - eye_y;
            int z = overlay.z[v] - eye_z;

            int tmp = z * sinEyeYaw + x * cosEyeYaw >> 16;
            z = z * cosEyeYaw - x * sinEyeYaw >> 16;
            x = tmp;

            tmp = y * cosEyePitch - z * sinEyePitch >> 16;
            z = y * sinEyePitch + z * cosEyePitch >> 16;
            y = tmp;

            if (z < 50) {
                return;
            }

            if (overlay.face_texture != null) {
                SceneTileOverlay.vx[v] = x;
                SceneTileOverlay.vy[v] = y;
                SceneTileOverlay.vz[v] = z;
            }

            SceneTileOverlay.px[v] = Draw3D.center_x + (x << 9) / z;
            SceneTileOverlay.py[v] = Draw3D.center_y + (y << 9) / z;
        }

        Draw3D.transparency = 0;
        counter = overlay.face_a.length;
        for (int f = 0; f < counter; f++) {
            int a = overlay.face_a[f];
            int b = overlay.face_b[f];
            int c = overlay.face_c[f];

            int x0 = SceneTileOverlay.px[a];
            int x1 = SceneTileOverlay.px[b];
            int x2 = SceneTileOverlay.px[c];

            int y0 = SceneTileOverlay.py[a];
            int y1 = SceneTileOverlay.py[b];
            int y2 = SceneTileOverlay.py[c];

            if ((x0 - x1) * (y2 - y1) - (y0 - y1) * (x2 - x1) > 0) {
                Draw3D.clipX = x0 < 0 || x1 < 0 || x2 < 0 || x0 > Draw2D.boundX || x1 > Draw2D.boundX || x2 > Draw2D.boundX;

                if (accept_input && pick_triangle(mouseX, mouseY, y0, y1, y2, x0, x1, x2)) {
                    input_tile_x = tileX;
                    input_tile_z = tileZ;
                }

                if (overlay.face_texture == null || overlay.face_texture[f] == -1) {
                    if (overlay.face_colors_a[f] != 12345678) {
                        Draw3D.fillGouraudTriangle(x0, y0, x1, y1, x2, y2, overlay.face_colors_a[f], overlay.face_colors_b[f], overlay.face_colors_c[f]);
                    }
                } else if (overlay.flat) {
                    Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, overlay.face_colors_a[f], overlay.face_colors_b[f], overlay.face_colors_c[f], SceneTileOverlay.vx[0],SceneTileOverlay.vx[1],SceneTileOverlay.vx[3],SceneTileOverlay.vy[0],SceneTileOverlay.vy[1],SceneTileOverlay.vy[3],SceneTileOverlay.vz[0],SceneTileOverlay.vz[1],SceneTileOverlay.vz[3],overlay.face_texture[f]);
                } else {
                    Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, overlay.face_colors_a[f], overlay.face_colors_b[f], overlay.face_colors_c[f], SceneTileOverlay.vx[a],SceneTileOverlay.vx[b],SceneTileOverlay.vx[c],SceneTileOverlay.vy[a],SceneTileOverlay.vy[b],SceneTileOverlay.vy[c],SceneTileOverlay.vz[a],SceneTileOverlay.vz[b],SceneTileOverlay.vz[c],overlay.face_texture[f]);
                }
            }
        }
    }

    public boolean pick_triangle(int x, int y, int y0, int y1, int y2, int x0, int x1, int x2) {
        if (y < y0 && y < y1 && y < y2) {
            return false;
        }
        if (y > y0 && y > y1 && y > y2) {
            return false;
        }
        if (x < x0 && x < x1 && x < x2) {
            return false;
        }
        if (x > x0 && x > x1 && x > x2) {
            return false;
        }
        int i2 = (y - y0) * (x1 - x0) - (x - x0) * (y1 - y0);
        int j2 = (y - y2) * (x0 - x2) - (x - x2) * (y0 - y2);
        int k2 = (y - y1) * (x2 - x1) - (x - x1) * (y2 - y1);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    public void update_occluders() {
        occluders_active = 0;
        ground_occluders_active = 0;
        wall_occluders_active = 0;
        tiles_culled = 0;

        int count = level_occluder_count[top_level];
        SceneOccluder[] occluders = level_occluders[top_level];

        for (int i = 0; i < count; i++) {
            SceneOccluder occluder = occluders[i];

            if (occluder.type == SceneOccluder.TYPE_WALL_X) {
                int x = occluder.min_tile_x - eye_tile_x + 25;

                if (x < 0 || x > 50) {
                    continue;
                }

                // Think of min/maxZ as the relative Z value in our visibility map, the +25 is because
                // the visibility map origin is at 25,25
                int minZ = occluder.min_tile_z - eye_tile_z + 25;
                int maxZ = occluder.max_tile_z - eye_tile_z + 25;

                if (minZ < 0) {
                    minZ = 0;
                }

                if (maxZ > 50) {
                    maxZ = 50;
                }

                boolean ok = false;

                // checks if we can at least see one tile in the forward direction starting from our occluder
                while (minZ <= maxZ) {
                    if (visibility_map[x][minZ++]) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    continue;
                }

                int deltaMinX = eye_x - occluder.min_x;

                if (deltaMinX > 32) {
                    occluder.mode = 1;
                } else {
                    if (deltaMinX >= -32) {
                        continue;
                    }
                    occluder.mode = 2;
                    deltaMinX = -deltaMinX;
                }

                occluder.min_delta_z = (occluder.min_z - eye_z << 10) / deltaMinX;
                occluder.max_delta_z = (occluder.max_z - eye_z << 10) / deltaMinX;
                occluder.min_delta_y = (occluder.min_y - eye_y << 10) / deltaMinX;
                occluder.max_delta_y = (occluder.max_y - eye_y << 10) / deltaMinX;
                active_occluders[occluders_active++] = occluder;
                wall_occluders_active++;
                continue;
            }

            if (occluder.type == SceneOccluder.TYPE_WALL_Z) {
                int distanceMinTileZ = occluder.min_tile_z - eye_tile_z + 25;

                if (distanceMinTileZ < 0 || distanceMinTileZ > 50) {
                    continue;
                }

                int distanceMinTileX = occluder.min_tile_x - eye_tile_x + 25;

                if (distanceMinTileX < 0) {
                    distanceMinTileX = 0;
                }

                int distanceMaxTileX = occluder.max_tile_x - eye_tile_x + 25;

                if (distanceMaxTileX > 50) {
                    distanceMaxTileX = 50;
                }

                boolean ok = false;

                while (distanceMinTileX <= distanceMaxTileX) {
                    if (visibility_map[distanceMinTileX++][distanceMinTileZ]) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    continue;
                }

                int deltaMinZ = eye_z - occluder.min_z;

                if (deltaMinZ > 32) {
                    occluder.mode = 3;
                } else {
                    if (deltaMinZ >= -32) {
                        continue;
                    }
                    occluder.mode = 4;
                    deltaMinZ = -deltaMinZ;
                }

                occluder.min_delta_x = (occluder.min_x - eye_x << 10) / deltaMinZ;
                occluder.max_delta_x = (occluder.max_x - eye_x << 10) / deltaMinZ;
                occluder.min_delta_y = (occluder.min_y - eye_y << 10) / deltaMinZ;
                occluder.max_delta_y = (occluder.max_y - eye_y << 10) / deltaMinZ;
                active_occluders[occluders_active++] = occluder;
                wall_occluders_active++;
            } else if (occluder.type == SceneOccluder.TYPE_GROUND) {
                int deltaMaxY = occluder.min_y - eye_y;

                if (deltaMaxY <= 128) {
                    continue;
                }

                int deltaMinTileZ = occluder.min_tile_z - eye_tile_z + 25;

                if (deltaMinTileZ < 0) {
                    deltaMinTileZ = 0;
                }

                int deltaMaxTileZ = occluder.max_tile_z - eye_tile_z + 25;

                if (deltaMaxTileZ > 50) {
                    deltaMaxTileZ = 50;
                }

                if (deltaMinTileZ <= deltaMaxTileZ) {
                    int deltaMinTileX = occluder.min_tile_x - eye_tile_x + 25;

                    if (deltaMinTileX < 0) {
                        deltaMinTileX = 0;
                    }

                    int deltaMaxTileX = occluder.max_tile_x - eye_tile_x + 25;

                    if (deltaMaxTileX > 50) {
                        deltaMaxTileX = 50;
                    }

                    boolean ok = false;

                    find_visible_tile:
                    for (int x = deltaMinTileX; x <= deltaMaxTileX; x++) {
                        for (int z = deltaMinTileZ; z <= deltaMaxTileZ; z++) {
                            if (visibility_map[x][z]) {
                                ok = true;
                                break find_visible_tile;
                            }
                        }
                    }

                    if (ok) {
                        occluder.mode = 5;
                        occluder.min_delta_x = (occluder.min_x - eye_x << 10) / deltaMaxY;
                        occluder.max_delta_x = (occluder.max_x - eye_x << 10) / deltaMaxY;
                        occluder.min_delta_z = (occluder.min_z - eye_z << 10) / deltaMaxY;
                        occluder.max_delta_z = (occluder.max_z - eye_z << 10) / deltaMaxY;
                        active_occluders[occluders_active++] = occluder;
                        ground_occluders_active++;
                    }
                }
            }
        }
    }

    public boolean tile_visible(int level, int x, int z) {
        int cycle = level_occlusion_cycles[level][x][z];

        if (cycle == -Scene.cycle) {
            return true;
        }

        if (cycle == Scene.cycle) {
            return false;
        }

        int sx = x << 7;
        int sz = z << 7;

        if (occluded(sx + 1, level_heightmaps[level][x][z], sz + 1) && occluded(sx + 128 - 1, level_heightmaps[level][x + 1][z], sz + 1) && occluded(sx + 128 - 1, level_heightmaps[level][x + 1][z + 1], sz + 128 - 1) && occluded(sx + 1, level_heightmaps[level][x][z + 1], sz + 128 - 1)) {
            level_occlusion_cycles[level][x][z] = Scene.cycle;
            tiles_culled++;
            return false;
        } else {
            level_occlusion_cycles[level][x][z] = -Scene.cycle;
            return true;
        }
    }

    public boolean wall_visible(int level, int tile_x, int tile_z, int wall_type) {
        if (tile_visible(level, tile_x, tile_z)) {
            return true;
        }

        int x = tile_x << 7;
        int z = tile_z << 7;
        int y = level_heightmaps[level][tile_x][tile_z] - 1;
        int y_level1 = y - 120;
        int y_level2 = y - 230;
        int y_level3 = y - 238;

        if (wall_type < 16) {
            if (wall_type == 1) {
                if (x > eye_x) {
                    if (!occluded(x, y, z)) {
                        return true;
                    }
                    if (!occluded(x, y, z + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(x, y_level1, z)) {
                        return true;
                    }
                    if (!occluded(x, y_level1, z + 128)) {
                        return true;
                    }
                }
                if (!occluded(x, y_level2, z)) {
                    return true;
                }
                return !occluded(x, y_level2, z + 128);
            } else if (wall_type == 2) {
                if (z < eye_z) {
                    if (!occluded(x, y, z + 128)) {
                        return true;
                    }
                    if (!occluded(x + 128, y, z + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(x, y_level1, z + 128)) {
                        return true;
                    }
                    if (!occluded(x + 128, y_level1, z + 128)) {
                        return true;
                    }
                }
                if (!occluded(x, y_level2, z + 128)) {
                    return true;
                }
                return !occluded(x + 128, y_level2, z + 128);
            } else if (wall_type == 4) {
                if (x < eye_x) {
                    if (!occluded(x + 128, y, z)) {
                        return true;
                    }
                    if (!occluded(x + 128, y, z + 128)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(x + 128, y_level1, z)) {
                        return true;
                    }
                    if (!occluded(x + 128, y_level1, z + 128)) {
                        return true;
                    }
                }
                if (!occluded(x + 128, y_level2, z)) {
                    return true;
                }
                return !occluded(x + 128, y_level2, z + 128);
            } else if (wall_type == 8) {
                if (z > eye_z) {
                    if (!occluded(x, y, z)) {
                        return true;
                    }
                    if (!occluded(x + 128, y, z)) {
                        return true;
                    }
                }
                if (level > 0) {
                    if (!occluded(x, y_level1, z)) {
                        return true;
                    }
                    if (!occluded(x + 128, y_level1, z)) {
                        return true;
                    }
                }
                if (!occluded(x, y_level2, z)) {
                    return true;
                }
                return !occluded(x + 128, y_level2, z);
            }
        }

        if (!occluded(x + 64, y_level3, z + 64)) {
            return true;
        }

        if (wall_type == 16) {
            return !occluded(x, y_level2, z + 128);
        } else if (wall_type == 32) {
            return !occluded(x + 128, y_level2, z + 128);
        } else if (wall_type == 64) {
            return !occluded(x + 128, y_level2, z);
        } else if (wall_type == 128) {
            return !occluded(x, y_level2, z);
        } else {
            System.out.println("Warning unsupported wall type");
            return false;
        }
    }

    public boolean visible(int level, int tileX, int tileZ, int y) {
        if (tile_visible(level, tileX, tileZ)) {
            return true;
        }
        int x = tileX << 7;
        int z = tileZ << 7;
        return !occluded(x + 1, level_heightmaps[level][tileX][tileZ] - y, z + 1) || !occluded(x + 128 - 1, level_heightmaps[level][tileX + 1][tileZ] - y, z + 1) || !occluded(x + 128 - 1, level_heightmaps[level][tileX + 1][tileZ + 1] - y, z + 128 - 1) || !occluded(x + 1, level_heightmaps[level][tileX][tileZ + 1] - y, z + 128 - 1);
    }

    public boolean area_visible(int level, int min_tile_x, int max_tile_x, int min_tile_z, int max_tile_z, int y) {
        if (min_tile_x == max_tile_x && min_tile_z == max_tile_z) {
            if (tile_visible(level, min_tile_x, min_tile_z)) {
                return true;
            }
            int x = min_tile_x << 7;
            int z = min_tile_z << 7;
            return !occluded(x + 1, level_heightmaps[level][min_tile_x][min_tile_z] - y, z + 1) || !occluded(x + 128 - 1, level_heightmaps[level][min_tile_x + 1][min_tile_z] - y, z + 1) || !occluded(x + 128 - 1, level_heightmaps[level][min_tile_x + 1][min_tile_z + 1] - y, z + 128 - 1) || !occluded(x + 1, level_heightmaps[level][min_tile_x][min_tile_z + 1] - y, z + 128 - 1);
        }

        for (int stx = min_tile_x; stx <= max_tile_x; stx++) {
            for (int stz = min_tile_z; stz <= max_tile_z; stz++) {
                if (level_occlusion_cycles[level][stx][stz] == -cycle) {
                    return true;
                }
            }
        }

        int x0 = (min_tile_x << 7) + 1;
        int z0 = (min_tile_z << 7) + 2;
        int y0 = level_heightmaps[level][min_tile_x][min_tile_z] - y;

        if (!occluded(x0, y0, z0)) {
            return true;
        }

        int x1 = (max_tile_x << 7) - 1;

        if (!occluded(x1, y0, z0)) {
            return true;
        }

        int z1 = (max_tile_z << 7) - 1;

        if (!occluded(x0, y0, z1)) {
            return true;
        }

        return !occluded(x1, y0, z1);
    }

    public boolean occluded(int x, int y, int z) {
        for (int i = 0; i < occluders_active; i++) {
            SceneOccluder occluder = active_occluders[i];

            if (occluder.mode == 1) {
                int dx = occluder.min_x - x;
                if (dx <= 0) {
                    continue;
                }

                int minZ = occluder.min_z + (occluder.min_delta_z * dx >> 10);
                int maxZ = occluder.max_z + (occluder.max_delta_z * dx >> 10);
                int minY = occluder.min_y + (occluder.min_delta_y * dx >> 10);
                int maxY = occluder.max_y + (occluder.max_delta_y * dx >> 10);
                if (z >= minZ && z <= maxZ && y >= minY && y <= maxY) {
                    return true;
                }
            } else if (occluder.mode == 2) {
                int dx = x - occluder.min_x;
                if (dx <= 0) {
                    continue;
                }
                int minZ = occluder.min_z + (occluder.min_delta_z * dx >> 10);
                int macZ = occluder.max_z + (occluder.max_delta_z * dx >> 10);
                int minY = occluder.min_y + (occluder.min_delta_y * dx >> 10);
                int maxY = occluder.max_y + (occluder.max_delta_y * dx >> 10);
                if (z >= minZ && z <= macZ && y >= minY && y <= maxY) {
                    return true;
                }
            } else if (occluder.mode == 3) {
                int dz = occluder.min_z - z;
                if (dz <= 0) {
                    continue;
                }
                int minX = occluder.min_x + (occluder.min_delta_x * dz >> 10);
                int maxX = occluder.max_x + (occluder.max_delta_x * dz >> 10);
                int minY = occluder.min_y + (occluder.min_delta_y * dz >> 10);
                int maxY = occluder.max_y + (occluder.max_delta_y * dz >> 10);
                if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                    return true;
                }
            } else if (occluder.mode == 4) {
                int dz = z - occluder.min_z;
                if (dz <= 0) {
                    continue;
                }
                int minX = occluder.min_x + (occluder.min_delta_x * dz >> 10);
                int maxX = occluder.max_x + (occluder.max_delta_x * dz >> 10);
                int minY = occluder.min_y + (occluder.min_delta_y * dz >> 10);
                int maxY = occluder.max_y + (occluder.max_delta_y * dz >> 10);
                if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                    return true;
                }
            } else if (occluder.mode == 5) {
                int dy = y - occluder.min_y;
                if (dy <= 0) {
                    continue;
                }
                int minX = occluder.min_x + (occluder.min_delta_x * dy >> 10);
                int maxX = occluder.max_x + (occluder.max_delta_x * dy >> 10);
                int minZ = occluder.min_z + (occluder.min_delta_z * dy >> 10);
                int maxZ = occluder.max_z + (occluder.max_delta_z * dy >> 10);
                if (x >= minX && x <= maxX && z >= minZ && z <= maxZ) {
                    return true;
                }
            }
        }
        return false;
    }

}
