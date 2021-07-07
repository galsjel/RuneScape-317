// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.util.Arrays;

public class Scene {

	public static final int[] anIntArray463 = {53, -53, -53, 53};
	public static final int[] anIntArray464 = {-53, -53, 53, 53};
	public static final int[] anIntArray465 = {-45, 45, 45, -45};
	public static final int[] anIntArray466 = {45, 45, -45, -45};
	public static final int[] anIntArray478 = {19, 55, 38, 155, 255, 110, 137, 205, 76};
	public static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80, 48, 160};
	public static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
	public static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
	public static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
	public static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
	public static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
	public static final int[] anIntArray485 = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
	public static final int anInt472 = 4;
	public static final SceneOccluder[] activeOccluders = new SceneOccluder[500];
	public static boolean lowmem = true;
	public static int anInt446;
	public static int anInt447;
	public static int cycle;
	public static int anInt449;
	public static int anInt450;
	public static int anInt451;
	public static int anInt452;
	public static int eyeTileX;
	public static int eyeTileZ;
	public static int eyeX;
	public static int eyeY;
	public static int eyeZ;
	public static int sinEyePitch;
	public static int cosEyePitch;
	public static int sinEyeYaw;
	public static int cosEyeYaw;
	public static SceneLoc[] tmp = new SceneLoc[100];
	public static boolean aBoolean467;
	public static int anInt468;
	public static int anInt469;
	public static int anInt470 = -1;
	public static int anInt471 = -1;
	public static int[] anIntArray473 = new int[anInt472];
	public static SceneOccluder[][] aOccluderArrayArray474 = new SceneOccluder[anInt472][500];
	public static int activeOccluderCount;
	public static DoublyLinkedList aList_477 = new DoublyLinkedList();
	public static boolean[][][][] aBooleanArrayArrayArrayArray491 = new boolean[8][32][51][51];
	public static boolean[][] aBooleanArrayArray492;
	public static int anInt493;
	public static int anInt494;
	public static int anInt495;
	public static int anInt496;
	public static int anInt497;
	public static int anInt498;

	public static void unload() {
		tmp = null;
		anIntArray473 = null;
		aOccluderArrayArray474 = null;
		aList_477 = null;
		aBooleanArrayArrayArrayArray491 = null;
		aBooleanArrayArray492 = null;
	}

	public static void method277(int i, int j, int k, int l, int i1, int j1, int l1, int i2) {
		SceneOccluder occluder = new SceneOccluder();
		occluder.anInt787 = j / 128;
		occluder.anInt788 = l / 128;
		occluder.anInt789 = l1 / 128;
		occluder.anInt790 = i1 / 128;
		occluder.anInt791 = i2;
		occluder.anInt792 = j;
		occluder.anInt793 = l;
		occluder.anInt794 = l1;
		occluder.anInt795 = i1;
		occluder.anInt796 = j1;
		occluder.anInt797 = k;
		aOccluderArrayArray474[i][anIntArray473[i]++] = occluder;
	}

	public static void method310(int i, int j, int k, int l, int[] ai) {
		anInt495 = 0;
		anInt496 = 0;
		anInt497 = k;
		anInt498 = l;
		anInt493 = k / 2;
		anInt494 = l / 2;
		boolean[][][][] aflag = new boolean[9][32][53][53];
		for (int i1 = 128; i1 <= 384; i1 += 32) {
			for (int j1 = 0; j1 < 2048; j1 += 64) {
				sinEyePitch = Model.sin[i1];
				cosEyePitch = Model.cos[i1];
				sinEyeYaw = Model.sin[j1];
				cosEyeYaw = Model.cos[j1];
				int l1 = (i1 - 128) / 32;
				int j2 = j1 / 64;
				for (int l2 = -26; l2 <= 26; l2++) {
					for (int j3 = -26; j3 <= 26; j3++) {
						int k3 = l2 * 128;
						int i4 = j3 * 128;
						boolean flag2 = false;
						for (int k4 = -i; k4 <= j; k4 += 128) {
							if (!method311(ai[l1] + k4, i4, k3)) {
								continue;
							}
							flag2 = true;
							break;
						}
						aflag[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
					}
				}
			}
		}
		for (int k1 = 0; k1 < 8; k1++) {
			for (int i2 = 0; i2 < 32; i2++) {
				for (int k2 = -25; k2 < 25; k2++) {
					for (int i3 = -25; i3 < 25; i3++) {
						boolean flag1 = false;
						label0:
						for (int l3 = -1; l3 <= 1; l3++) {
							for (int j4 = -1; j4 <= 1; j4++) {
								if (aflag[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else if (aflag[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else {
									if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
										continue;
									}
									flag1 = true;
								}
								break label0;
							}
						}
						aBooleanArrayArrayArrayArray491[k1][i2][k2 + 25][i3 + 25] = flag1;
					}
				}
			}
		}
	}

	public static boolean method311(int i, int j, int k) {
		int l = ((j * sinEyeYaw) + (k * cosEyeYaw)) >> 16;
		int i1 = ((j * cosEyeYaw) - (k * sinEyeYaw)) >> 16;
		int j1 = ((i * sinEyePitch) + (i1 * cosEyePitch)) >> 16;
		int k1 = ((i * cosEyePitch) - (i1 * sinEyePitch)) >> 16;
		if ((j1 < 50) || (j1 > 3500)) {
			return false;
		}
		int l1 = anInt493 + ((l << 9) / j1);
		int i2 = anInt494 + ((k1 << 9) / j1);
		return (l1 >= anInt495) && (l1 <= anInt497) && (i2 >= anInt496) && (i2 <= anInt498);
	}

	public final int maxPlane;
	public final int maxTileX;
	public final int maxTileZ;
	public final int[][][] planeHeightmaps;
	public final SceneTile[][][] planeTiles;
	public final SceneLoc[] temporaryLocs = new SceneLoc[5000];
	public final int[][][] planeTileOcclusionCycles;
	public final int[] anIntArray486 = new int[10000];
	public final int[] anIntArray487 = new int[10000];
	public final int[][] MINIMAP_TILE_MASK = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
	public final int[][] MINIMAP_TILE_ROTATION_MAP = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
	public int minPlane;
	public int temporaryLocCount;
	public int anInt488;

	public Scene(int maxTileZ, int maxTileX, int[][][] planeHeightmaps, int maxPlane) {
		this.maxPlane = maxPlane;
		this.maxTileX = maxTileX;
		this.maxTileZ = maxTileZ;
		planeTiles = new SceneTile[maxPlane][maxTileX][maxTileZ];
		planeTileOcclusionCycles = new int[maxPlane][maxTileX + 1][maxTileZ + 1];
		this.planeHeightmaps = planeHeightmaps;
		clear();
	}

	public void clear() {
		for (int plane = 0; plane < maxPlane; plane++) {
			for (int stx = 0; stx < maxTileX; stx++) {
				for (int stz = 0; stz < maxTileZ; stz++) {
					planeTiles[plane][stx][stz] = null;
				}
			}
		}
		for (int l = 0; l < anInt472; l++) {
			for (int j1 = 0; j1 < anIntArray473[l]; j1++) {
				aOccluderArrayArray474[l][j1] = null;
			}
			anIntArray473[l] = 0;
		}
		for (int k1 = 0; k1 < temporaryLocCount; k1++) {
			temporaryLocs[k1] = null;
		}
		temporaryLocCount = 0;
		Arrays.fill(tmp, null);
	}

	public void setMinPlane(int plane) {
		minPlane = plane;
		for (int stx = 0; stx < maxTileX; stx++) {
			for (int stz = 0; stz < maxTileZ; stz++) {
				if (planeTiles[plane][stx][stz] == null) {
					planeTiles[plane][stx][stz] = new SceneTile(plane, stx, stz);
				}
			}
		}
	}

	public void setBridge(int stx, int stz) {
		SceneTile ground = planeTiles[0][stx][stz];

		for (int plane = 0; plane < 3; plane++) {
			SceneTile above = planeTiles[plane][stx][stz] = planeTiles[plane + 1][stx][stz];

			if (above == null) {
				continue;
			}

			above.anInt1307--;

			for (int i = 0; i < above.locCount; i++) {
				SceneLoc loc = above.locs[i];

				if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == stx) && (loc.minSceneTileZ == stz)) {
					loc.plane--;
				}
			}
		}

		if (planeTiles[0][stx][stz] == null) {
			planeTiles[0][stx][stz] = new SceneTile(0, stx, stz);
		}

		planeTiles[0][stx][stz].bridge = ground;
		planeTiles[3][stx][stz] = null;
	}

	public void setDrawPlane(int plane, int stx, int stz, int drawPlane) {
		SceneTile tile = planeTiles[plane][stx][stz];
		if (tile != null) {
			planeTiles[plane][stx][stz].drawPlane = drawPlane;
		}
	}

	public void method279(int plane, int stx, int stz, int type, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4) {
		if (type == 0) {
			SceneTileUnderlay underlay = new SceneTileUnderlay(k2, l2, i3, j3, -1, k4, false);
			for (int p = plane; p >= 0; p--) {
				if (planeTiles[p][stx][stz] == null) {
					planeTiles[p][stx][stz] = new SceneTile(p, stx, stz);
				}
			}
			planeTiles[plane][stx][stz].underlay = underlay;
		} else if (type == 1) {
			SceneTileUnderlay underlay = new SceneTileUnderlay(k3, l3, i4, j4, j1, l4, (k1 == l1) && (k1 == i2) && (k1 == j2));
			for (int p = plane; p >= 0; p--) {
				if (planeTiles[p][stx][stz] == null) {
					planeTiles[p][stx][stz] = new SceneTile(p, stx, stz);
				}
			}
			planeTiles[plane][stx][stz].underlay = underlay;
		} else {
			SceneTileOverlay overlay = new SceneTileOverlay(stz, k3, j3, i2, j1, i4, i1, k2, k4, i3, j2, l1, k1, type, j4, l3, l2, stx, l4);
			for (int k5 = plane; k5 >= 0; k5--) {
				if (planeTiles[k5][stx][stz] == null) {
					planeTiles[k5][stx][stz] = new SceneTile(k5, stx, stz);
				}
			}
			planeTiles[plane][stx][stz].overlay = overlay;
		}
	}

	public void addGroundDecoration(Entity entity, int plane, int stx, int stz, int y, int bitset, byte info) {
		if (entity == null) {
			return;
		}
		SceneGroundDecoration decor = new SceneGroundDecoration();
		decor.entity = entity;
		decor.x = (stx * 128) + 64;
		decor.z = (stz * 128) + 64;
		decor.y = y;
		decor.bitset = bitset;
		decor.info = info;
		if (planeTiles[plane][stx][stz] == null) {
			planeTiles[plane][stx][stz] = new SceneTile(plane, stx, stz);
		}
		planeTiles[plane][stx][stz].groundDecoration = decor;
	}

	public void addObjStack(Entity entity0, Entity entity1, Entity entity2, int plane, int stx, int stz, int y, int bitset) {
		SceneObjStack objStack = new SceneObjStack();
		objStack.x = (stx * 128) + 64;
		objStack.z = (stz * 128) + 64;
		objStack.y = y;
		objStack.bitset = bitset;
		objStack.entity0 = entity0;
		objStack.entity1 = entity1;
		objStack.entity2 = entity2;

		int stackOffset = 0;

		SceneTile tile = planeTiles[plane][stx][stz];

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

		objStack.offset = stackOffset;

		if (planeTiles[plane][stx][stz] == null) {
			planeTiles[plane][stx][stz] = new SceneTile(plane, stx, stz);
		}
		planeTiles[plane][stx][stz].objStack = objStack;
	}

	public void addWall(int type0, Entity wall0, int type1, Entity wall1, int plane, int stx, int stz, int y, int bitset, byte info) {
		if ((wall0 == null) && (wall1 == null)) {
			return;
		}
		SceneWall wall = new SceneWall();
		wall.bitset = bitset;
		wall.info = info;
		wall.x = (stx * 128) + 64;
		wall.z = (stz * 128) + 64;
		wall.y = y;
		wall.entity0 = wall0;
		wall.entity1 = wall1;
		wall.type0 = type0;
		wall.type1 = type1;
		for (int p = plane; p >= 0; p--) {
			if (planeTiles[p][stx][stz] == null) {
				planeTiles[p][stx][stz] = new SceneTile(p, stx, stz);
			}
		}
		planeTiles[plane][stx][stz].wall = wall;
	}

	public void addWallDecoration(int type, Entity entity, int plane, int stx, int stz, int y, int yaw, int offsetX, int offsetZ, int bitset, byte info) {
		if (entity == null) {
			return;
		}
		SceneWallDecoration decor = new SceneWallDecoration();
		decor.bitset = bitset;
		decor.info = info;
		decor.x = (stx * 128) + 64 + offsetX;
		decor.z = (stz * 128) + 64 + offsetZ;
		decor.y = y;
		decor.entity = entity;
		decor.type = type;
		decor.yaw = yaw;
		for (int p = plane; p >= 0; p--) {
			if (planeTiles[p][stx][stz] == null) {
				planeTiles[p][stx][stz] = new SceneTile(p, stx, stz);
			}
		}
		planeTiles[plane][stx][stz].wallDecoration = decor;
	}

	public boolean add(Entity entity, int plane, int stx, int stz, int y, int width, int length, int yaw, int bitset, byte info) {
		if (entity == null) {
			return true;
		} else {
			int x = (stx * 128) + (64 * width);
			int z = (stz * 128) + (64 * length);
			return add(entity, plane, stx, stz, width, length, x, z, y, yaw, bitset, info, false);
		}
	}

	public boolean addTemporary(Entity entity, int plane, int x, int z, int y, int yaw, int bitset, boolean forwardPadding, int padding) {
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
		return add(entity, plane, x0, z0, (x1 - x0) + 1, (z1 - z0) + 1, x, z, y, yaw, bitset, (byte) 0, true);
	}

	public boolean addTemporary(Entity entity, int plane, int stx0, int stz0, int stx1, int stz1, int x, int z, int y, int yaw, int bitset) {
		if (entity == null) {
			return true;
		} else {
			return add(entity, plane, stx0, stz0, (stx1 - stx0) + 1, (stz1 - stz0) + 1, x, z, y, yaw, bitset, (byte) 0, true);
		}
	}

	public boolean add(Entity entity, int plane, int stx, int stz, int width, int length, int x, int z, int y, int yaw, int bitset, byte info, boolean temporary) {
		for (int tx = stx; tx < (stx + width); tx++) {
			for (int tz = stz; tz < (stz + length); tz++) {
				if ((tx < 0) || (tz < 0) || (tx >= maxTileX) || (tz >= maxTileZ)) {
					return false;
				}
				SceneTile tile = planeTiles[plane][tx][tz];
				if ((tile != null) && (tile.locCount >= 5)) {
					return false;
				}
			}
		}
		SceneLoc loc = new SceneLoc();
		loc.bitset = bitset;
		loc.info = info;
		loc.plane = plane;
		loc.x = x;
		loc.z = z;
		loc.y = y;
		loc.entity = entity;
		loc.yaw = yaw;
		loc.minSceneTileX = stx;
		loc.minSceneTileZ = stz;
		loc.maxSceneTileX = (stx + width) - 1;
		loc.maxSceneTileZ = (stz + length) - 1;

		for (int tx = stx; tx < (stx + width); tx++) {
			for (int tz = stz; tz < (stz + length); tz++) {
				int flags = 0;

				if (tx > stx) {
					flags++;
				}

				if (tx < ((stx + width) - 1)) {
					flags += 4;
				}

				if (tz > stz) {
					flags += 8;
				}

				if (tz < ((stz + length) - 1)) {
					flags += 2;
				}

				for (int p = plane; p >= 0; p--) {
					if (planeTiles[p][tx][tz] == null) {
						planeTiles[p][tx][tz] = new SceneTile(p, tx, tz);
					}
				}

				SceneTile tile = planeTiles[plane][tx][tz];
				tile.locs[tile.locCount] = loc;
				tile.locFlags[tile.locCount] = flags;
				tile.flags |= flags;
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
			remove(loc);
			temporaryLocs[i] = null;
		}
		temporaryLocCount = 0;
	}

	public void remove(SceneLoc loc) {
		for (int tx = loc.minSceneTileX; tx <= loc.maxSceneTileX; tx++) {
			for (int tz = loc.minSceneTileZ; tz <= loc.maxSceneTileZ; tz++) {
				SceneTile tile = planeTiles[loc.plane][tx][tz];

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
						tile.locFlags[j] = tile.locFlags[j + 1];
					}
					tile.locs[tile.locCount] = null;
					break;
				}

				tile.flags = 0;

				for (int j1 = 0; j1 < tile.locCount; j1++) {
					tile.flags |= tile.locFlags[j1];
				}
			}
		}
	}

	public void setWallDecorationOffset(int plane, int stx, int stz, int offset) {
		SceneTile tile = planeTiles[plane][stx][stz];
		if (tile == null) {
			return;
		}
		SceneWallDecoration decoration = tile.wallDecoration;
		if (decoration != null) {
			int sx = (stx * 128) + 64;
			int sz = (stz * 128) + 64;
			decoration.x = sx + (((decoration.x - sx) * offset) / 16);
			decoration.z = sz + (((decoration.z - sz) * offset) / 16);
		}
	}

	public void removeWall(int x, int plane, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			tile.wall = null;
		}
	}

	public void removeWallDecoration(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			tile.wallDecoration = null;
		}
	}

	public void removeLoc(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile == null) {
			return;
		}
		for (int j1 = 0; j1 < tile.locCount; j1++) {
			SceneLoc loc = tile.locs[j1];
			if ((((loc.bitset >> 29) & 3) == 2) && (loc.minSceneTileX == x) && (loc.minSceneTileZ == z)) {
				remove(loc);
				return;
			}
		}
	}

	public void removeGroundDecoration(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			tile.groundDecoration = null;
		}
	}

	public void removeObjStack(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			tile.objStack = null;
		}
	}

	public SceneWall getWall(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			return tile.wall;
		} else {
			return null;
		}
	}

	public SceneWallDecoration getWallDecoration(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if (tile != null) {
			return tile.wallDecoration;
		} else {
			return null;
		}
	}

	public SceneLoc getLoc(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
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

	public SceneGroundDecoration getGroundDecoration(int z, int x, int plane) {
		SceneTile tile = planeTiles[plane][x][z];
		if ((tile != null) && (tile.groundDecoration != null)) {
			return tile.groundDecoration;
		} else {
			return null;
		}
	}

	public int getWallBitset(int i, int j, int k) {
		SceneTile tile = planeTiles[i][j][k];
		if ((tile != null) && (tile.wall != null)) {
			return tile.wall.bitset;
		} else {
			return 0;
		}
	}

	public int getWallDecorationBitset(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if ((tile == null) || (tile.wallDecoration == null)) {
			return 0;
		} else {
			return tile.wallDecoration.bitset;
		}
	}

	public int getLocBitset(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
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

	public int getGroundDecorationBitset(int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];
		if ((tile == null) || (tile.groundDecoration == null)) {
			return 0;
		} else {
			return tile.groundDecoration.bitset;
		}
	}

	public int getInfo(int plane, int x, int z, int bitset) {
		SceneTile tile = planeTiles[plane][x][z];
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
		for (int i1 = 0; i1 < tile.locCount; i1++) {
			if (tile.locs[i1].bitset == bitset) {
				return tile.locs[i1].info & 0xff;
			}
		}
		return -1;
	}

	public void method305(int i, int j, int k, int l, int i1) {
		int j1 = (int) Math.sqrt((k * k) + (i * i) + (i1 * i1));
		int k1 = (l * j1) >> 8;
		for (int l1 = 0; l1 < maxPlane; l1++) {
			for (int i2 = 0; i2 < maxTileX; i2++) {
				for (int j2 = 0; j2 < maxTileZ; j2++) {
					SceneTile tile = planeTiles[l1][i2][j2];
					if (tile != null) {
						SceneWall wall = tile.wall;
						if ((wall != null) && (wall.entity0 != null) && (wall.entity0.vertexNormal != null)) {
							method307(l1, 1, 1, i2, j2, (Model) wall.entity0);
							if ((wall.entity1 != null) && (wall.entity1.vertexNormal != null)) {
								method307(l1, 1, 1, i2, j2, (Model) wall.entity1);
								method308((Model) wall.entity0, (Model) wall.entity1, 0, 0, 0, false);
								((Model) wall.entity1).applyLighting(j, k1, k, i, i1);
							}
							((Model) wall.entity0).applyLighting(j, k1, k, i, i1);
						}
						for (int k2 = 0; k2 < tile.locCount; k2++) {
							SceneLoc loc = tile.locs[k2];
							if ((loc != null) && (loc.entity != null) && (loc.entity.vertexNormal != null)) {
								method307(l1, (loc.maxSceneTileX - loc.minSceneTileX) + 1, (loc.maxSceneTileZ - loc.minSceneTileZ) + 1, i2, j2, (Model) loc.entity);
								((Model) loc.entity).applyLighting(j, k1, k, i, i1);
							}
						}
						SceneGroundDecoration groundDecoration = tile.groundDecoration;
						if ((groundDecoration != null) && (groundDecoration.entity.vertexNormal != null)) {
							method306(i2, l1, (Model) groundDecoration.entity, j2);
							((Model) groundDecoration.entity).applyLighting(j, k1, k, i, i1);
						}
					}
				}
			}
		}
	}

	public void method306(int i, int j, Model model, int k) {
		if (i < maxTileX) {
			SceneTile tile = planeTiles[j][i + 1][k];
			if ((tile != null) && (tile.groundDecoration != null) && (tile.groundDecoration.entity.vertexNormal != null)) {
				method308(model, (Model) tile.groundDecoration.entity, 128, 0, 0, true);
			}
		}
		if (k < maxTileX) {
			SceneTile tile_1 = planeTiles[j][i][k + 1];
			if ((tile_1 != null) && (tile_1.groundDecoration != null) && (tile_1.groundDecoration.entity.vertexNormal != null)) {
				method308(model, (Model) tile_1.groundDecoration.entity, 0, 0, 128, true);
			}
		}
		if ((i < maxTileX) && (k < maxTileZ)) {
			SceneTile tile_2 = planeTiles[j][i + 1][k + 1];
			if ((tile_2 != null) && (tile_2.groundDecoration != null) && (tile_2.groundDecoration.entity.vertexNormal != null)) {
				method308(model, (Model) tile_2.groundDecoration.entity, 128, 0, 128, true);
			}
		}
		if ((i < maxTileX) && (k > 0)) {
			SceneTile tile_3 = planeTiles[j][i + 1][k - 1];
			if ((tile_3 != null) && (tile_3.groundDecoration != null) && (tile_3.groundDecoration.entity.vertexNormal != null)) {
				method308(model, (Model) tile_3.groundDecoration.entity, 128, 0, -128, true);
			}
		}
	}

	public void method307(int i, int j, int k, int l, int i1, Model model) {
		boolean flag = true;
		int j1 = l;
		int k1 = l + j;
		int l1 = i1 - 1;
		int i2 = i1 + k;
		for (int j2 = i; j2 <= (i + 1); j2++) {
			if (j2 != maxPlane) {
				for (int k2 = j1; k2 <= k1; k2++) {
					if ((k2 >= 0) && (k2 < maxTileX)) {
						for (int l2 = l1; l2 <= i2; l2++) {
							if ((l2 >= 0) && (l2 < maxTileZ) && (!flag || (k2 >= k1) || (l2 >= i2) || ((l2 < i1) && (k2 != l)))) {
								SceneTile tile = planeTiles[j2][k2][l2];
								if (tile != null) {
									int i3 = ((planeHeightmaps[j2][k2][l2] + planeHeightmaps[j2][k2 + 1][l2] + planeHeightmaps[j2][k2][l2 + 1] + planeHeightmaps[j2][k2 + 1][l2 + 1]) / 4) - ((planeHeightmaps[i][l][i1] + planeHeightmaps[i][l + 1][i1] + planeHeightmaps[i][l][i1 + 1] + planeHeightmaps[i][l + 1][i1 + 1]) / 4);
									SceneWall wall = tile.wall;
									if ((wall != null) && (wall.entity0 != null) && (wall.entity0.vertexNormal != null)) {
										method308(model, (Model) wall.entity0, ((k2 - l) * 128) + ((1 - j) * 64), i3, ((l2 - i1) * 128) + ((1 - k) * 64), flag);
									}
									if ((wall != null) && (wall.entity1 != null) && (wall.entity1.vertexNormal != null)) {
										method308(model, (Model) wall.entity1, ((k2 - l) * 128) + ((1 - j) * 64), i3, ((l2 - i1) * 128) + ((1 - k) * 64), flag);
									}
									for (int j3 = 0; j3 < tile.locCount; j3++) {
										SceneLoc loc = tile.locs[j3];
										if ((loc != null) && (loc.entity != null) && (loc.entity.vertexNormal != null)) {
											int k3 = (loc.maxSceneTileX - loc.minSceneTileX) + 1;
											int l3 = (loc.maxSceneTileZ - loc.minSceneTileZ) + 1;
											method308(model, (Model) loc.entity, ((loc.minSceneTileX - l) * 128) + ((k3 - j) * 64), i3, ((loc.minSceneTileZ - i1) * 128) + ((l3 - k) * 64), flag);
										}
									}
								}
							}
						}
					}
				}
				j1--;
				flag = false;
			}
		}
	}

	public void method308(Model model, Model model_1, int i, int j, int k, boolean flag) {
		anInt488++;
		int l = 0;
		int[] ai = model_1.vertexX;
		int i1 = model_1.vertexCount;
		for (int j1 = 0; j1 < model.vertexCount; j1++) {
			VertexNormal normal = model.vertexNormal[j1];
			VertexNormal normal_1 = model.vertexNormalOriginal[j1];

			if (normal_1.w == 0) {
				continue;
			}

			int i2 = model.vertexY[j1] - j;

			if (i2 > model_1.maxY) {
				continue;
			}

			int j2 = model.vertexX[j1] - i;

			if ((j2 < model_1.minX) || (j2 > model_1.maxX)) {
				continue;
			}

			int k2 = model.vertexZ[j1] - k;

			if ((k2 < model_1.minZ) || (k2 > model_1.maxZ)) {
				continue;
			}

			for (int l2 = 0; l2 < i1; l2++) {
				VertexNormal normal_2 = model_1.vertexNormal[l2];
				VertexNormal normal_3 = model_1.vertexNormalOriginal[l2];

				if ((j2 == ai[l2]) && (k2 == model_1.vertexZ[l2]) && (i2 == model_1.vertexY[l2]) && (normal_3.w != 0)) {
					normal.x += normal_3.x;
					normal.y += normal_3.y;
					normal.z += normal_3.z;
					normal.w += normal_3.w;
					normal_2.x += normal_1.x;
					normal_2.y += normal_1.y;
					normal_2.z += normal_1.z;
					normal_2.w += normal_1.w;
					l++;
					anIntArray486[j1] = anInt488;
					anIntArray487[l2] = anInt488;
				}
			}
		}

		if ((l < 3) || !flag) {
			return;
		}

		for (int k1 = 0; k1 < model.faceCount; k1++) {
			if ((anIntArray486[model.faceVertexA[k1]] == anInt488) && (anIntArray486[model.faceVertexB[k1]] == anInt488) && (anIntArray486[model.faceVertexC[k1]] == anInt488)) {
				model.faceInfo[k1] = -1;
			}
		}

		for (int l1 = 0; l1 < model_1.faceCount; l1++) {
			if ((anIntArray487[model_1.faceVertexA[l1]] == anInt488) && (anIntArray487[model_1.faceVertexB[l1]] == anInt488) && (anIntArray487[model_1.faceVertexC[l1]] == anInt488)) {
				model_1.faceInfo[l1] = -1;
			}
		}
	}

	public void drawMinimapTile(int[] dst, int offset, int step, int plane, int x, int z) {
		SceneTile tile = planeTiles[plane][x][z];

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
		int angle = overlay.angle;
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

	public void method312(int i, int j) {
		aBoolean467 = true;
		anInt468 = j;
		anInt469 = i;
		anInt470 = -1;
		anInt471 = -1;
	}

	public void method313(int eyeX, int eyeZ, int eyeYaw, int eyeY, int i1, int eyePitch) {
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
		cycle++;
		sinEyePitch = Model.sin[eyePitch];
		cosEyePitch = Model.cos[eyePitch];
		sinEyeYaw = Model.sin[eyeYaw];
		cosEyeYaw = Model.cos[eyeYaw];
		aBooleanArrayArray492 = aBooleanArrayArrayArrayArray491[(eyePitch - 128) / 32][eyeYaw / 64];
		Scene.eyeX = eyeX;
		Scene.eyeY = eyeY;
		Scene.eyeZ = eyeZ;
		eyeTileX = eyeX / 128;
		eyeTileZ = eyeZ / 128;
		anInt447 = i1;
		anInt449 = eyeTileX - 25;
		if (anInt449 < 0) {
			anInt449 = 0;
		}
		anInt451 = eyeTileZ - 25;
		if (anInt451 < 0) {
			anInt451 = 0;
		}
		anInt450 = eyeTileX + 25;
		if (anInt450 > maxTileX) {
			anInt450 = maxTileX;
		}
		anInt452 = eyeTileZ + 25;
		if (anInt452 > maxTileZ) {
			anInt452 = maxTileZ;
		}
		method319();
		anInt446 = 0;
		for (int k1 = minPlane; k1 < maxPlane; k1++) {
			SceneTile[][] aclass30_sub3 = planeTiles[k1];
			for (int i2 = anInt449; i2 < anInt450; i2++) {
				for (int k2 = anInt451; k2 < anInt452; k2++) {
					SceneTile tile = aclass30_sub3[i2][k2];
					if (tile != null) {
						if ((tile.drawPlane > i1) || (!aBooleanArrayArray492[(i2 - eyeTileX) + 25][(k2 - eyeTileZ) + 25] && ((planeHeightmaps[k1][i2][k2] - eyeY) < 2000))) {
							tile.aBoolean1322 = false;
							tile.aBoolean1323 = false;
							tile.anInt1325 = 0;
						} else {
							tile.aBoolean1322 = true;
							tile.aBoolean1323 = true;
							tile.aBoolean1324 = tile.locCount > 0;
							anInt446++;
						}
					}
				}
			}
		}
		for (int l1 = minPlane; l1 < maxPlane; l1++) {
			SceneTile[][] aclass30_sub3_1 = planeTiles[l1];
			for (int l2 = -25; l2 <= 0; l2++) {
				int i3 = eyeTileX + l2;
				int k3 = eyeTileX - l2;
				if ((i3 >= anInt449) || (k3 < anInt450)) {
					for (int i4 = -25; i4 <= 0; i4++) {
						int k4 = eyeTileZ + i4;
						int i5 = eyeTileZ - i4;
						if (i3 >= anInt449) {
							if (k4 >= anInt451) {
								SceneTile tile_1 = aclass30_sub3_1[i3][k4];
								if ((tile_1 != null) && tile_1.aBoolean1322) {
									method314(tile_1, true);
								}
							}
							if (i5 < anInt452) {
								SceneTile tile_2 = aclass30_sub3_1[i3][i5];
								if ((tile_2 != null) && tile_2.aBoolean1322) {
									method314(tile_2, true);
								}
							}
						}
						if (k3 < anInt450) {
							if (k4 >= anInt451) {
								SceneTile tile_3 = aclass30_sub3_1[k3][k4];
								if ((tile_3 != null) && tile_3.aBoolean1322) {
									method314(tile_3, true);
								}
							}
							if (i5 < anInt452) {
								SceneTile tile_4 = aclass30_sub3_1[k3][i5];
								if ((tile_4 != null) && tile_4.aBoolean1322) {
									method314(tile_4, true);
								}
							}
						}
						if (anInt446 == 0) {
							aBoolean467 = false;
							return;
						}
					}
				}
			}
		}
		for (int j2 = minPlane; j2 < maxPlane; j2++) {
			SceneTile[][] aclass30_sub3_2 = planeTiles[j2];
			for (int j3 = -25; j3 <= 0; j3++) {
				int l3 = eyeTileX + j3;
				int j4 = eyeTileX - j3;
				if ((l3 >= anInt449) || (j4 < anInt450)) {
					for (int l4 = -25; l4 <= 0; l4++) {
						int j5 = eyeTileZ + l4;
						int k5 = eyeTileZ - l4;
						if (l3 >= anInt449) {
							if (j5 >= anInt451) {
								SceneTile tile_5 = aclass30_sub3_2[l3][j5];
								if ((tile_5 != null) && tile_5.aBoolean1322) {
									method314(tile_5, false);
								}
							}
							if (k5 < anInt452) {
								SceneTile tile_6 = aclass30_sub3_2[l3][k5];
								if ((tile_6 != null) && tile_6.aBoolean1322) {
									method314(tile_6, false);
								}
							}
						}
						if (j4 < anInt450) {
							if (j5 >= anInt451) {
								SceneTile tile_7 = aclass30_sub3_2[j4][j5];
								if ((tile_7 != null) && tile_7.aBoolean1322) {
									method314(tile_7, false);
								}
							}
							if (k5 < anInt452) {
								SceneTile tile_8 = aclass30_sub3_2[j4][k5];
								if ((tile_8 != null) && tile_8.aBoolean1322) {
									method314(tile_8, false);
								}
							}
						}
						if (anInt446 == 0) {
							aBoolean467 = false;
							return;
						}
					}
				}
			}
		}
		aBoolean467 = false;
	}

	public void method314(SceneTile tile, boolean flag) {
		aList_477.pushBack(tile);
		do {
			SceneTile tile_1;
			do {
				tile_1 = (SceneTile) aList_477.pollFront();
				if (tile_1 == null) {
					return;
				}
			} while (!tile_1.aBoolean1323);
			int i = tile_1.anInt1308;
			int j = tile_1.anInt1309;
			int k = tile_1.anInt1307;
			int l = tile_1.anInt1310;
			SceneTile[][] tiles = planeTiles[k];
			if (tile_1.aBoolean1322) {
				if (flag) {
					if (k > 0) {
						SceneTile tile_2 = planeTiles[k - 1][i][j];
						if ((tile_2 != null) && tile_2.aBoolean1323) {
							continue;
						}
					}
					if ((i <= eyeTileX) && (i > anInt449)) {
						SceneTile tile_3 = tiles[i - 1][j];
						if ((tile_3 != null) && tile_3.aBoolean1323 && (tile_3.aBoolean1322 || ((tile_1.flags & 1) == 0))) {
							continue;
						}
					}
					if ((i >= eyeTileX) && (i < (anInt450 - 1))) {
						SceneTile tile_4 = tiles[i + 1][j];
						if ((tile_4 != null) && tile_4.aBoolean1323 && (tile_4.aBoolean1322 || ((tile_1.flags & 4) == 0))) {
							continue;
						}
					}
					if ((j <= eyeTileZ) && (j > anInt451)) {
						SceneTile tile_5 = tiles[i][j - 1];
						if ((tile_5 != null) && tile_5.aBoolean1323 && (tile_5.aBoolean1322 || ((tile_1.flags & 8) == 0))) {
							continue;
						}
					}
					if ((j >= eyeTileZ) && (j < (anInt452 - 1))) {
						SceneTile tile_6 = tiles[i][j + 1];
						if ((tile_6 != null) && tile_6.aBoolean1323 && (tile_6.aBoolean1322 || ((tile_1.flags & 2) == 0))) {
							continue;
						}
					}
				} else {
					flag = true;
				}
				tile_1.aBoolean1322 = false;
				if (tile_1.bridge != null) {
					SceneTile tile_7 = tile_1.bridge;
					if (tile_7.underlay != null) {
						if (!tileOccluded(0, i, j)) {
							method315(tile_7.underlay, 0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i, j);
						}
					} else if ((tile_7.overlay != null) && !tileOccluded(0, i, j)) {
						method316(i, sinEyePitch, sinEyeYaw, tile_7.overlay, cosEyePitch, j, cosEyeYaw);
					}
					SceneWall wall = tile_7.wall;
					if (wall != null) {
						wall.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
					}
					for (int i2 = 0; i2 < tile_7.locCount; i2++) {
						SceneLoc loc = tile_7.locs[i2];
						if (loc != null) {
							loc.entity.draw(loc.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, loc.x - eyeX, loc.y - eyeY, loc.z - eyeZ, loc.bitset);
						}
					}
				}
				boolean flag1 = false;
				if (tile_1.underlay != null) {
					if (!tileOccluded(l, i, j)) {
						flag1 = true;
						method315(tile_1.underlay, l, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i, j);
					}
				} else if ((tile_1.overlay != null) && !tileOccluded(l, i, j)) {
					flag1 = true;
					method316(i, sinEyePitch, sinEyeYaw, tile_1.overlay, cosEyePitch, j, cosEyeYaw);
				}
				int j1 = 0;
				int j2 = 0;
				SceneWall wall = tile_1.wall;
				SceneWallDecoration decor = tile_1.wallDecoration;
				if ((wall != null) || (decor != null)) {
					if (eyeTileX == i) {
						j1++;
					} else if (eyeTileX < i) {
						j1 += 2;
					}
					if (eyeTileZ == j) {
						j1 += 3;
					} else if (eyeTileZ > j) {
						j1 += 6;
					}
					j2 = anIntArray478[j1];
					tile_1.anInt1328 = anIntArray480[j1];
				}
				if (wall != null) {
					if ((wall.type0 & anIntArray479[j1]) != 0) {
						if (wall.type0 == 16) {
							tile_1.anInt1325 = 3;
							tile_1.anInt1326 = anIntArray481[j1];
							tile_1.anInt1327 = 3 - tile_1.anInt1326;
						} else if (wall.type0 == 32) {
							tile_1.anInt1325 = 6;
							tile_1.anInt1326 = anIntArray482[j1];
							tile_1.anInt1327 = 6 - tile_1.anInt1326;
						} else if (wall.type0 == 64) {
							tile_1.anInt1325 = 12;
							tile_1.anInt1326 = anIntArray483[j1];
							tile_1.anInt1327 = 12 - tile_1.anInt1326;
						} else {
							tile_1.anInt1325 = 9;
							tile_1.anInt1326 = anIntArray484[j1];
							tile_1.anInt1327 = 9 - tile_1.anInt1326;
						}
					} else {
						tile_1.anInt1325 = 0;
					}
					if (((wall.type0 & j2) != 0) && !wallOccluded(l, i, j, wall.type0)) {
						wall.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
					}
					if (((wall.type1 & j2) != 0) && !wallOccluded(l, i, j, wall.type1)) {
						wall.entity1.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.x - eyeX, wall.y - eyeY, wall.z - eyeZ, wall.bitset);
					}
				}
				if ((decor != null) && !occluded(l, i, j, decor.entity.minY)) {
					if ((decor.type & j2) != 0) {
						decor.entity.draw(decor.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, decor.x - eyeX, decor.y - eyeY, decor.z - eyeZ, decor.bitset);
					} else if ((decor.type & 0x300) != 0) {
						int j4 = decor.x - eyeX;
						int l5 = decor.y - eyeY;
						int k6 = decor.z - eyeZ;
						int i8 = decor.yaw;
						int k9;
						if ((i8 == 1) || (i8 == 2)) {
							k9 = -j4;
						} else {
							k9 = j4;
						}
						int k10;
						if ((i8 == 2) || (i8 == 3)) {
							k10 = -k6;
						} else {
							k10 = k6;
						}
						if (((decor.type & 0x100) != 0) && (k10 < k9)) {
							int i11 = j4 + anIntArray463[i8];
							int k11 = k6 + anIntArray464[i8];
							decor.entity.draw((i8 * 512) + 256, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i11, l5, k11, decor.bitset);
						}
						if (((decor.type & 0x200) != 0) && (k10 > k9)) {
							int j11 = j4 + anIntArray465[i8];
							int l11 = k6 + anIntArray466[i8];
							decor.entity.draw(((i8 * 512) + 1280) & 0x7ff, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, j11, l5, l11, decor.bitset);
						}
					}
				}
				if (flag1) {
					SceneGroundDecoration groundDecoration = tile_1.groundDecoration;
					if (groundDecoration != null) {
						groundDecoration.entity.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, groundDecoration.x - eyeX, groundDecoration.y - eyeY, groundDecoration.z - eyeZ, groundDecoration.bitset);
					}
					SceneObjStack objStack_1 = tile_1.objStack;
					if ((objStack_1 != null) && (objStack_1.offset == 0)) {
						if (objStack_1.entity1 != null) {
							objStack_1.entity1.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.x - eyeX, objStack_1.y - eyeY, objStack_1.z - eyeZ, objStack_1.bitset);
						}
						if (objStack_1.entity2 != null) {
							objStack_1.entity2.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.x - eyeX, objStack_1.y - eyeY, objStack_1.z - eyeZ, objStack_1.bitset);
						}
						if (objStack_1.entity0 != null) {
							objStack_1.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.x - eyeX, objStack_1.y - eyeY, objStack_1.z - eyeZ, objStack_1.bitset);
						}
					}
				}
				int k4 = tile_1.flags;
				if (k4 != 0) {
					if ((i < eyeTileX) && ((k4 & 4) != 0)) {
						SceneTile tile_17 = tiles[i + 1][j];
						if ((tile_17 != null) && tile_17.aBoolean1323) {
							aList_477.pushBack(tile_17);
						}
					}
					if ((j < eyeTileZ) && ((k4 & 2) != 0)) {
						SceneTile tile_18 = tiles[i][j + 1];
						if ((tile_18 != null) && tile_18.aBoolean1323) {
							aList_477.pushBack(tile_18);
						}
					}
					if ((i > eyeTileX) && ((k4 & 1) != 0)) {
						SceneTile tile_19 = tiles[i - 1][j];
						if ((tile_19 != null) && tile_19.aBoolean1323) {
							aList_477.pushBack(tile_19);
						}
					}
					if ((j > eyeTileZ) && ((k4 & 8) != 0)) {
						SceneTile tile_20 = tiles[i][j - 1];
						if ((tile_20 != null) && tile_20.aBoolean1323) {
							aList_477.pushBack(tile_20);
						}
					}
				}
			}
			if (tile_1.anInt1325 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < tile_1.locCount; k1++) {
					if ((tile_1.locs[k1].cycle == cycle) || ((tile_1.locFlags[k1] & tile_1.anInt1325) != tile_1.anInt1326)) {
						continue;
					}
					flag2 = false;
					break;
				}
				if (flag2) {
					SceneWall wall_1 = tile_1.wall;
					if (!wallOccluded(l, i, j, wall_1.type0)) {
						wall_1.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_1.x - eyeX, wall_1.y - eyeY, wall_1.z - eyeZ, wall_1.bitset);
					}
					tile_1.anInt1325 = 0;
				}
			}
			if (tile_1.aBoolean1324) {
				try {
					int locCount = tile_1.locCount;
					tile_1.aBoolean1324 = false;
					int tmpCount = 0;

					label0:
					for (int k2 = 0; k2 < locCount; k2++) {
						SceneLoc loc = tile_1.locs[k2];

						if (loc.cycle == cycle) {
							continue;
						}

						for (int stx = loc.minSceneTileX; stx <= loc.maxSceneTileX; stx++) {
							for (int stz = loc.minSceneTileZ; stz <= loc.maxSceneTileZ; stz++) {
								SceneTile ground = tiles[stx][stz];

								if (!ground.aBoolean1322) {
									if (ground.anInt1325 == 0) {
										continue;
									}
									int l6 = 0;
									if (stx > loc.minSceneTileX) {
										l6++;
									}
									if (stx < loc.maxSceneTileX) {
										l6 += 4;
									}
									if (stz > loc.minSceneTileZ) {
										l6 += 8;
									}
									if (stz < loc.maxSceneTileZ) {
										l6 += 2;
									}
									if ((l6 & ground.anInt1325) != tile_1.anInt1327) {
										continue;
									}
								}
								tile_1.aBoolean1324 = true;
								continue label0;
							}
						}

						tmp[tmpCount++] = loc;

						int dtx0 = eyeTileX - loc.minSceneTileX;
						int dtx1 = loc.maxSceneTileX - eyeTileX;

						if (dtx1 > dtx0) {
							dtx0 = dtx1;
						}

						int dtz0 = eyeTileZ - loc.minSceneTileZ;
						int dtz1 = loc.maxSceneTileZ - eyeTileZ;

						if (dtz1 > dtz0) {
							loc.distance = dtx0 + dtz1;
						} else {
							loc.distance = dtx0 + dtz0;
						}
					}

					while (true) {
						int farthestDistance = -50;
						int farthestIndex = -1;

						for (int i0 = 0; i0 < tmpCount; i0++) {
							SceneLoc loc = tmp[i0];

							if (loc.cycle != cycle) {
								if (loc.distance > farthestDistance) {
									farthestDistance = loc.distance;
									farthestIndex = i0;
								} else if (loc.distance == farthestDistance) {
									int dx0 = loc.x - eyeX;
									int dz0 = loc.z - eyeZ;
									int dx1 = tmp[farthestIndex].x - eyeX;
									int dz1 = tmp[farthestIndex].z - eyeZ;

									// dot
									if (((dx0 * dx0) + (dz0 * dz0)) > ((dx1 * dx1) + (dz1 * dz1))) {
										farthestIndex = i0;
									}
								}
							}
						}

						if (farthestIndex == -1) {
							break;
						}

						SceneLoc farthest = tmp[farthestIndex];
						farthest.cycle = cycle;

						if (!occluded(l, farthest.minSceneTileX, farthest.maxSceneTileX, farthest.minSceneTileZ, farthest.maxSceneTileZ, farthest.entity.minY)) {
							farthest.entity.draw(farthest.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, farthest.x - eyeX, farthest.y - eyeY, farthest.z - eyeZ, farthest.bitset);
						}

						for (int k7 = farthest.minSceneTileX; k7 <= farthest.maxSceneTileX; k7++) {
							for (int l8 = farthest.minSceneTileZ; l8 <= farthest.maxSceneTileZ; l8++) {
								SceneTile tile_22 = tiles[k7][l8];
								if (tile_22.anInt1325 != 0) {
									aList_477.pushBack(tile_22);
								} else if (((k7 != i) || (l8 != j)) && tile_22.aBoolean1323) {
									aList_477.pushBack(tile_22);
								}
							}
						}
					}
					if (tile_1.aBoolean1324) {
						continue;
					}
				} catch (Exception _ex) {
					tile_1.aBoolean1324 = false;
				}
			}
			if (!tile_1.aBoolean1323 || (tile_1.anInt1325 != 0)) {
				continue;
			}
			if ((i <= eyeTileX) && (i > anInt449)) {
				SceneTile tile_8 = tiles[i - 1][j];
				if ((tile_8 != null) && tile_8.aBoolean1323) {
					continue;
				}
			}
			if ((i >= eyeTileX) && (i < (anInt450 - 1))) {
				SceneTile tile_9 = tiles[i + 1][j];
				if ((tile_9 != null) && tile_9.aBoolean1323) {
					continue;
				}
			}
			if ((j <= eyeTileZ) && (j > anInt451)) {
				SceneTile tile_10 = tiles[i][j - 1];
				if ((tile_10 != null) && tile_10.aBoolean1323) {
					continue;
				}
			}
			if ((j >= eyeTileZ) && (j < (anInt452 - 1))) {
				SceneTile tile_11 = tiles[i][j + 1];
				if ((tile_11 != null) && tile_11.aBoolean1323) {
					continue;
				}
			}
			tile_1.aBoolean1323 = false;
			anInt446--;
			SceneObjStack objStack = tile_1.objStack;
			if ((objStack != null) && (objStack.offset != 0)) {
				if (objStack.entity1 != null) {
					objStack.entity1.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.x - eyeX, objStack.y - eyeY - objStack.offset, objStack.z - eyeZ, objStack.bitset);
				}
				if (objStack.entity2 != null) {
					objStack.entity2.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.x - eyeX, objStack.y - eyeY - objStack.offset, objStack.z - eyeZ, objStack.bitset);
				}
				if (objStack.entity0 != null) {
					objStack.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.x - eyeX, objStack.y - eyeY - objStack.offset, objStack.z - eyeZ, objStack.bitset);
				}
			}
			if (tile_1.anInt1328 != 0) {
				SceneWallDecoration wallDecoration = tile_1.wallDecoration;
				if ((wallDecoration != null) && !occluded(l, i, j, wallDecoration.entity.minY)) {
					if ((wallDecoration.type & tile_1.anInt1328) != 0) {
						wallDecoration.entity.draw(wallDecoration.yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wallDecoration.x - eyeX, wallDecoration.y - eyeY, wallDecoration.z - eyeZ, wallDecoration.bitset);
					} else if ((wallDecoration.type & 0x300) != 0) {
						int l2 = wallDecoration.x - eyeX;
						int j3 = wallDecoration.y - eyeY;
						int i4 = wallDecoration.z - eyeZ;
						int k5 = wallDecoration.yaw;
						int j6;
						if ((k5 == 1) || (k5 == 2)) {
							j6 = -l2;
						} else {
							j6 = l2;
						}
						int l7;
						if ((k5 == 2) || (k5 == 3)) {
							l7 = -i4;
						} else {
							l7 = i4;
						}
						if (((wallDecoration.type & 0x100) != 0) && (l7 >= j6)) {
							int i9 = l2 + anIntArray463[k5];
							int i10 = i4 + anIntArray464[k5];
							wallDecoration.entity.draw((k5 * 512) + 256, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i9, j3, i10, wallDecoration.bitset);
						}
						if (((wallDecoration.type & 0x200) != 0) && (l7 <= j6)) {
							int j9 = l2 + anIntArray465[k5];
							int j10 = i4 + anIntArray466[k5];
							wallDecoration.entity.draw(((k5 * 512) + 1280) & 0x7ff, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, j9, j3, j10, wallDecoration.bitset);
						}
					}
				}
				SceneWall wall_2 = tile_1.wall;
				if (wall_2 != null) {
					if (((wall_2.type1 & tile_1.anInt1328) != 0) && !wallOccluded(l, i, j, wall_2.type1)) {
						wall_2.entity1.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_2.x - eyeX, wall_2.y - eyeY, wall_2.z - eyeZ, wall_2.bitset);
					}
					if (((wall_2.type0 & tile_1.anInt1328) != 0) && !wallOccluded(l, i, j, wall_2.type0)) {
						wall_2.entity0.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_2.x - eyeX, wall_2.y - eyeY, wall_2.z - eyeZ, wall_2.bitset);
					}
				}
			}
			if (k < (maxPlane - 1)) {
				SceneTile tile_12 = planeTiles[k + 1][i][j];
				if ((tile_12 != null) && tile_12.aBoolean1323) {
					aList_477.pushBack(tile_12);
				}
			}
			if (i < eyeTileX) {
				SceneTile tile_13 = tiles[i + 1][j];
				if ((tile_13 != null) && tile_13.aBoolean1323) {
					aList_477.pushBack(tile_13);
				}
			}
			if (j < eyeTileZ) {
				SceneTile tile_14 = tiles[i][j + 1];
				if ((tile_14 != null) && tile_14.aBoolean1323) {
					aList_477.pushBack(tile_14);
				}
			}
			if (i > eyeTileX) {
				SceneTile tile_15 = tiles[i - 1][j];
				if ((tile_15 != null) && tile_15.aBoolean1323) {
					aList_477.pushBack(tile_15);
				}
			}
			if (j > eyeTileZ) {
				SceneTile tile_16 = tiles[i][j - 1];
				if ((tile_16 != null) && tile_16.aBoolean1323) {
					aList_477.pushBack(tile_16);
				}
			}
		} while (true);
	}

	public void method315(SceneTileUnderlay underlay, int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1;
		int i2 = l1 = (j1 << 7) - eyeX;
		int j2;
		int k2 = j2 = (k1 << 7) - eyeZ;
		int l2;
		int i3 = l2 = i2 + 128;
		int j3;
		int k3 = j3 = k2 + 128;
		int l3 = planeHeightmaps[i][j1][k1] - eyeY;
		int i4 = planeHeightmaps[i][j1 + 1][k1] - eyeY;
		int j4 = planeHeightmaps[i][j1 + 1][k1 + 1] - eyeY;
		int k4 = planeHeightmaps[i][j1][k1 + 1] - eyeY;
		int l4 = ((k2 * l) + (i2 * i1)) >> 16;
		k2 = ((k2 * i1) - (i2 * l)) >> 16;
		i2 = l4;
		l4 = ((l3 * k) - (k2 * j)) >> 16;
		k2 = ((l3 * j) + (k2 * k)) >> 16;
		l3 = l4;
		if (k2 < 50) {
			return;
		}
		l4 = ((j2 * l) + (i3 * i1)) >> 16;
		j2 = ((j2 * i1) - (i3 * l)) >> 16;
		i3 = l4;
		l4 = ((i4 * k) - (j2 * j)) >> 16;
		j2 = ((i4 * j) + (j2 * k)) >> 16;
		i4 = l4;
		if (j2 < 50) {
			return;
		}
		l4 = ((k3 * l) + (l2 * i1)) >> 16;
		k3 = ((k3 * i1) - (l2 * l)) >> 16;
		l2 = l4;
		l4 = ((j4 * k) - (k3 * j)) >> 16;
		k3 = ((j4 * j) + (k3 * k)) >> 16;
		j4 = l4;
		if (k3 < 50) {
			return;
		}
		l4 = ((j3 * l) + (l1 * i1)) >> 16;
		j3 = ((j3 * i1) - (l1 * l)) >> 16;
		l1 = l4;
		l4 = ((k4 * k) - (j3 * j)) >> 16;
		j3 = ((k4 * j) + (j3 * k)) >> 16;
		k4 = l4;
		if (j3 < 50) {
			return;
		}
		int i5 = Draw3D.centerX + ((i2 << 9) / k2);
		int j5 = Draw3D.centerY + ((l3 << 9) / k2);
		int k5 = Draw3D.centerX + ((i3 << 9) / j2);
		int l5 = Draw3D.centerY + ((i4 << 9) / j2);
		int i6 = Draw3D.centerX + ((l2 << 9) / k3);
		int j6 = Draw3D.centerY + ((j4 << 9) / k3);
		int k6 = Draw3D.centerX + ((l1 << 9) / j3);
		int l6 = Draw3D.centerY + ((k4 << 9) / j3);
		Draw3D.alpha = 0;
		if ((((i6 - k6) * (l5 - l6)) - ((j6 - l6) * (k5 - k6))) > 0) {
			Draw3D.clipX = (i6 < 0) || (k6 < 0) || (k5 < 0) || (i6 > Draw2D.boundX) || (k6 > Draw2D.boundX) || (k5 > Draw2D.boundX);
			if (aBoolean467 && method318(anInt468, anInt469, j6, l6, l5, i6, k6, k5)) {
				anInt470 = j1;
				anInt471 = k1;
			}
			if (underlay.anInt720 == -1) {
				if (underlay.anInt718 != 0xbc614e) {
					Draw3D.fillGouraudTriangle(j6, l6, l5, i6, k6, k5, underlay.anInt718, underlay.anInt719, underlay.anInt717);
				}
			} else if (!lowmem) {
				if (underlay.aBoolean721) {
					Draw3D.fillTexturedTriangle(j6, l6, l5, i6, k6, k5, underlay.anInt718, underlay.anInt719, underlay.anInt717, i2, i3, l1, l3, i4, k4, k2, j2, j3, underlay.anInt720);
				} else {
					Draw3D.fillTexturedTriangle(j6, l6, l5, i6, k6, k5, underlay.anInt718, underlay.anInt719, underlay.anInt717, l2, l1, i3, j4, k4, i4, k3, j3, j2, underlay.anInt720);
				}
			} else {
				int i7 = anIntArray485[underlay.anInt720];
				Draw3D.fillGouraudTriangle(j6, l6, l5, i6, k6, k5, method317(i7, underlay.anInt718), method317(i7, underlay.anInt719), method317(i7, underlay.anInt717));
			}
		}
		if ((((i5 - k5) * (l6 - l5)) - ((j5 - l5) * (k6 - k5))) > 0) {
			Draw3D.clipX = (i5 < 0) || (k5 < 0) || (k6 < 0) || (i5 > Draw2D.boundX) || (k5 > Draw2D.boundX) || (k6 > Draw2D.boundX);
			if (aBoolean467 && method318(anInt468, anInt469, j5, l5, l6, i5, k5, k6)) {
				anInt470 = j1;
				anInt471 = k1;
			}
			if (underlay.anInt720 == -1) {
				if (underlay.anInt716 != 0xbc614e) {
					Draw3D.fillGouraudTriangle(j5, l5, l6, i5, k5, k6, underlay.anInt716, underlay.anInt717, underlay.anInt719);
				}
			} else {
				if (!lowmem) {
					Draw3D.fillTexturedTriangle(j5, l5, l6, i5, k5, k6, underlay.anInt716, underlay.anInt717, underlay.anInt719, i2, i3, l1, l3, i4, k4, k2, j2, j3, underlay.anInt720);
					return;
				}
				int j7 = anIntArray485[underlay.anInt720];
				Draw3D.fillGouraudTriangle(j5, l5, l6, i5, k5, k6, method317(j7, underlay.anInt716), method317(j7, underlay.anInt717), method317(j7, underlay.anInt719));
			}
		}
	}

	public void method316(int i, int j, int k, SceneTileOverlay overlay, int l, int i1, int j1) {
		int k1 = overlay.anIntArray673.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = overlay.anIntArray673[l1] - eyeX;
			int k2 = overlay.anIntArray674[l1] - eyeY;
			int i3 = overlay.anIntArray675[l1] - eyeZ;
			int k3 = ((i3 * k) + (i2 * j1)) >> 16;
			i3 = ((i3 * j1) - (i2 * k)) >> 16;
			i2 = k3;
			k3 = ((k2 * l) - (i3 * j)) >> 16;
			i3 = ((k2 * j) + (i3 * l)) >> 16;
			k2 = k3;
			if (i3 < 50) {
				return;
			}
			if (overlay.anIntArray682 != null) {
				SceneTileOverlay.anIntArray690[l1] = i2;
				SceneTileOverlay.anIntArray691[l1] = k2;
				SceneTileOverlay.anIntArray692[l1] = i3;
			}
			SceneTileOverlay.anIntArray688[l1] = Draw3D.centerX + ((i2 << 9) / i3);
			SceneTileOverlay.anIntArray689[l1] = Draw3D.centerY + ((k2 << 9) / i3);
		}
		Draw3D.alpha = 0;
		k1 = overlay.anIntArray679.length;
		for (int j2 = 0; j2 < k1; j2++) {
			int l2 = overlay.anIntArray679[j2];
			int j3 = overlay.anIntArray680[j2];
			int l3 = overlay.anIntArray681[j2];
			int i4 = SceneTileOverlay.anIntArray688[l2];
			int j4 = SceneTileOverlay.anIntArray688[j3];
			int k4 = SceneTileOverlay.anIntArray688[l3];
			int l4 = SceneTileOverlay.anIntArray689[l2];
			int i5 = SceneTileOverlay.anIntArray689[j3];
			int j5 = SceneTileOverlay.anIntArray689[l3];
			if ((((i4 - j4) * (j5 - i5)) - ((l4 - i5) * (k4 - j4))) > 0) {
				Draw3D.clipX = (i4 < 0) || (j4 < 0) || (k4 < 0) || (i4 > Draw2D.boundX) || (j4 > Draw2D.boundX) || (k4 > Draw2D.boundX);
				if (aBoolean467 && method318(anInt468, anInt469, l4, i5, j5, i4, j4, k4)) {
					anInt470 = i;
					anInt471 = i1;
				}
				if ((overlay.anIntArray682 == null) || (overlay.anIntArray682[j2] == -1)) {
					if (overlay.anIntArray676[j2] != 0xbc614e) {
						Draw3D.fillGouraudTriangle(l4, i5, j5, i4, j4, k4, overlay.anIntArray676[j2], overlay.anIntArray677[j2], overlay.anIntArray678[j2]);
					}
				} else if (!lowmem) {
					if (overlay.aBoolean683) {
						Draw3D.fillTexturedTriangle(l4, i5, j5, i4, j4, k4, overlay.anIntArray676[j2], overlay.anIntArray677[j2], overlay.anIntArray678[j2], SceneTileOverlay.anIntArray690[0], SceneTileOverlay.anIntArray690[1], SceneTileOverlay.anIntArray690[3], SceneTileOverlay.anIntArray691[0], SceneTileOverlay.anIntArray691[1], SceneTileOverlay.anIntArray691[3], SceneTileOverlay.anIntArray692[0], SceneTileOverlay.anIntArray692[1], SceneTileOverlay.anIntArray692[3], overlay.anIntArray682[j2]);
					} else {
						Draw3D.fillTexturedTriangle(l4, i5, j5, i4, j4, k4, overlay.anIntArray676[j2], overlay.anIntArray677[j2], overlay.anIntArray678[j2], SceneTileOverlay.anIntArray690[l2], SceneTileOverlay.anIntArray690[j3], SceneTileOverlay.anIntArray690[l3], SceneTileOverlay.anIntArray691[l2], SceneTileOverlay.anIntArray691[j3], SceneTileOverlay.anIntArray691[l3], SceneTileOverlay.anIntArray692[l2], SceneTileOverlay.anIntArray692[j3], SceneTileOverlay.anIntArray692[l3], overlay.anIntArray682[j2]);
					}
				} else {
					int k5 = anIntArray485[overlay.anIntArray682[j2]];
					Draw3D.fillGouraudTriangle(l4, i5, j5, i4, j4, k4, method317(k5, overlay.anIntArray676[j2]), method317(k5, overlay.anIntArray677[j2]), method317(k5, overlay.anIntArray678[j2]));
				}
			}
		}
	}

	public int method317(int j, int k) {
		k = 127 - k;
		k = (k * (j & 0x7f)) / 160;
		if (k < 2) {
			k = 2;
		} else if (k > 126) {
			k = 126;
		}
		return (j & 0xff80) + k;
	}

	public boolean method318(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if ((j < k) && (j < l) && (j < i1)) {
			return false;
		}
		if ((j > k) && (j > l) && (j > i1)) {
			return false;
		}
		if ((i < j1) && (i < k1) && (i < l1)) {
			return false;
		}
		if ((i > j1) && (i > k1) && (i > l1)) {
			return false;
		}
		int i2 = ((j - k) * (k1 - j1)) - ((i - j1) * (l - k));
		int j2 = ((j - i1) * (j1 - l1)) - ((i - l1) * (k - i1));
		int k2 = ((j - l) * (l1 - k1)) - ((i - k1) * (i1 - l));
		return ((i2 * k2) > 0) && ((k2 * j2) > 0);
	}

	public void method319() {
		int j = anIntArray473[anInt447];
		SceneOccluder[] aclass47 = aOccluderArrayArray474[anInt447];
		activeOccluderCount = 0;
		for (int k = 0; k < j; k++) {
			SceneOccluder occluder = aclass47[k];
			if (occluder.anInt791 == 1) {
				int l = (occluder.anInt787 - eyeTileX) + 25;
				if ((l < 0) || (l > 50)) {
					continue;
				}
				int k1 = (occluder.anInt789 - eyeTileZ) + 25;
				if (k1 < 0) {
					k1 = 0;
				}
				int j2 = (occluder.anInt790 - eyeTileZ) + 25;
				if (j2 > 50) {
					j2 = 50;
				}
				boolean flag = false;
				while (k1 <= j2) {
					if (aBooleanArrayArray492[l][k1++]) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					continue;
				}
				int j3 = eyeX - occluder.anInt792;
				if (j3 > 32) {
					occluder.mode = 1;
				} else {
					if (j3 >= -32) {
						continue;
					}
					occluder.mode = 2;
					j3 = -j3;
				}
				occluder.anInt801 = ((occluder.anInt794 - eyeZ) << 8) / j3;
				occluder.anInt802 = ((occluder.anInt795 - eyeZ) << 8) / j3;
				occluder.anInt803 = ((occluder.anInt796 - eyeY) << 8) / j3;
				occluder.anInt804 = ((occluder.anInt797 - eyeY) << 8) / j3;
				activeOccluders[activeOccluderCount++] = occluder;
				continue;
			}
			if (occluder.anInt791 == 2) {
				int i1 = (occluder.anInt789 - eyeTileZ) + 25;
				if ((i1 < 0) || (i1 > 50)) {
					continue;
				}
				int l1 = (occluder.anInt787 - eyeTileX) + 25;
				if (l1 < 0) {
					l1 = 0;
				}
				int k2 = (occluder.anInt788 - eyeTileX) + 25;
				if (k2 > 50) {
					k2 = 50;
				}
				boolean flag1 = false;
				while (l1 <= k2) {
					if (aBooleanArrayArray492[l1++][i1]) {
						flag1 = true;
						break;
					}
				}
				if (!flag1) {
					continue;
				}
				int k3 = eyeZ - occluder.anInt794;
				if (k3 > 32) {
					occluder.mode = 3;
				} else {
					if (k3 >= -32) {
						continue;
					}
					occluder.mode = 4;
					k3 = -k3;
				}
				occluder.anInt799 = ((occluder.anInt792 - eyeX) << 8) / k3;
				occluder.anInt800 = ((occluder.anInt793 - eyeX) << 8) / k3;
				occluder.anInt803 = ((occluder.anInt796 - eyeY) << 8) / k3;
				occluder.anInt804 = ((occluder.anInt797 - eyeY) << 8) / k3;
				activeOccluders[activeOccluderCount++] = occluder;
			} else if (occluder.anInt791 == 4) {
				int j1 = occluder.anInt796 - eyeY;
				if (j1 > 128) {
					int i2 = (occluder.anInt789 - eyeTileZ) + 25;
					if (i2 < 0) {
						i2 = 0;
					}
					int l2 = (occluder.anInt790 - eyeTileZ) + 25;
					if (l2 > 50) {
						l2 = 50;
					}
					if (i2 <= l2) {
						int i3 = (occluder.anInt787 - eyeTileX) + 25;
						if (i3 < 0) {
							i3 = 0;
						}
						int l3 = (occluder.anInt788 - eyeTileX) + 25;
						if (l3 > 50) {
							l3 = 50;
						}
						boolean flag2 = false;
						label0:
						for (int i4 = i3; i4 <= l3; i4++) {
							for (int j4 = i2; j4 <= l2; j4++) {
								if (!aBooleanArrayArray492[i4][j4]) {
									continue;
								}
								flag2 = true;
								break label0;
							}
						}
						if (flag2) {
							occluder.mode = 5;
							occluder.anInt799 = ((occluder.anInt792 - eyeX) << 8) / j1;
							occluder.anInt800 = ((occluder.anInt793 - eyeX) << 8) / j1;
							occluder.anInt801 = ((occluder.anInt794 - eyeZ) << 8) / j1;
							occluder.anInt802 = ((occluder.anInt795 - eyeZ) << 8) / j1;
							activeOccluders[activeOccluderCount++] = occluder;
						}
					}
				}
			}
		}
	}

	public boolean tileOccluded(int plane, int x, int z) {
		int cycle = planeTileOcclusionCycles[plane][x][z];

		if (cycle == -Scene.cycle) {
			return false;
		}

		if (cycle == Scene.cycle) {
			return true;
		}

		int sx = x << 7;
		int sz = z << 7;

		if (occluded(sx + 1, planeHeightmaps[plane][x][z], sz + 1) && occluded((sx + 128) - 1, planeHeightmaps[plane][x + 1][z], sz + 1) && occluded((sx + 128) - 1, planeHeightmaps[plane][x + 1][z + 1], (sz + 128) - 1) && occluded(sx + 1, planeHeightmaps[plane][x][z + 1], (sz + 128) - 1)) {
			planeTileOcclusionCycles[plane][x][z] = Scene.cycle;
			return true;
		} else {
			planeTileOcclusionCycles[plane][x][z] = -Scene.cycle;
			return false;
		}
	}

	public boolean wallOccluded(int plane, int stx, int stz, int type) {
		if (!tileOccluded(plane, stx, stz)) {
			return false;
		}

		int x = stx << 7;
		int z = stz << 7;
		int y = planeHeightmaps[plane][stx][stz] - 1;
		int y0 = y - 120;
		int y1 = y - 230;
		int y2 = y - 238;

		if (type < 16) {
			if (type == 1) {
				if (x > eyeX) {
					if (!occluded(x, y, z)) {
						return false;
					}
					if (!occluded(x, y, z + 128)) {
						return false;
					}
				}
				if (plane > 0) {
					if (!occluded(x, y0, z)) {
						return false;
					}
					if (!occluded(x, y0, z + 128)) {
						return false;
					}
				}
				if (!occluded(x, y1, z)) {
					return false;
				}
				return occluded(x, y1, z + 128);
			}
			if (type == 2) {
				if (z < eyeZ) {
					if (!occluded(x, y, z + 128)) {
						return false;
					}
					if (!occluded(x + 128, y, z + 128)) {
						return false;
					}
				}
				if (plane > 0) {
					if (!occluded(x, y0, z + 128)) {
						return false;
					}
					if (!occluded(x + 128, y0, z + 128)) {
						return false;
					}
				}
				if (!occluded(x, y1, z + 128)) {
					return false;
				}
				return occluded(x + 128, y1, z + 128);
			}
			if (type == 4) {
				if (x < eyeX) {
					if (!occluded(x + 128, y, z)) {
						return false;
					}
					if (!occluded(x + 128, y, z + 128)) {
						return false;
					}
				}
				if (plane > 0) {
					if (!occluded(x + 128, y0, z)) {
						return false;
					}
					if (!occluded(x + 128, y0, z + 128)) {
						return false;
					}
				}
				if (!occluded(x + 128, y1, z)) {
					return false;
				}
				return occluded(x + 128, y1, z + 128);
			}
			if (type == 8) {
				if (z > eyeZ) {
					if (!occluded(x, y, z)) {
						return false;
					}
					if (!occluded(x + 128, y, z)) {
						return false;
					}
				}
				if (plane > 0) {
					if (!occluded(x, y0, z)) {
						return false;
					}
					if (!occluded(x + 128, y0, z)) {
						return false;
					}
				}
				if (!occluded(x, y1, z)) {
					return false;
				}
				return occluded(x + 128, y1, z);
			}
		}

		if (!occluded(x + 64, y2, z + 64)) {
			return false;
		}

		if (type == 16) {
			return occluded(x, y1, z + 128);
		}

		if (type == 32) {
			return occluded(x + 128, y1, z + 128);
		}

		if (type == 64) {
			return occluded(x + 128, y1, z);
		}

		if (type == 128) {
			return occluded(x, y1, z);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	public boolean occluded(int plane, int tx, int tz, int y) {
		if (!tileOccluded(plane, tx, tz)) {
			return false;
		}
		int x = tx << 7;
		int z = tz << 7;
		return occluded(x + 1, planeHeightmaps[plane][tx][tz] - y, z + 1) && occluded((x + 128) - 1, planeHeightmaps[plane][tx + 1][tz] - y, z + 1) && occluded((x + 128) - 1, planeHeightmaps[plane][tx + 1][tz + 1] - y, (z + 128) - 1) && occluded(x + 1, planeHeightmaps[plane][tx][tz + 1] - y, (z + 128) - 1);
	}

	public boolean occluded(int plane, int tx0, int tx1, int tz0, int tz1, int y) {
		if ((tx0 == tx1) && (tz0 == tz1)) {
			if (!tileOccluded(plane, tx0, tz0)) {
				return false;
			}
			int x = tx0 << 7;
			int z = tz0 << 7;
			return occluded(x + 1, planeHeightmaps[plane][tx0][tz0] - y, z + 1) && occluded((x + 128) - 1, planeHeightmaps[plane][tx0 + 1][tz0] - y, z + 1) && occluded((x + 128) - 1, planeHeightmaps[plane][tx0 + 1][tz0 + 1] - y, (z + 128) - 1) && occluded(x + 1, planeHeightmaps[plane][tx0][tz0 + 1] - y, (z + 128) - 1);
		}

		for (int stx = tx0; stx <= tx1; stx++) {
			for (int stz = tz0; stz <= tz1; stz++) {
				if (planeTileOcclusionCycles[plane][stx][stz] == -cycle) {
					return false;
				}
			}
		}

		int x0 = (tx0 << 7) + 1;
		int z0 = (tz0 << 7) + 2;
		int y0 = planeHeightmaps[plane][tx0][tz0] - y;

		if (!occluded(x0, y0, z0)) {
			return false;
		}

		int x1 = (tx1 << 7) - 1;

		if (!occluded(x1, y0, z0)) {
			return false;
		}

		int z1 = (tz1 << 7) - 1;

		if (!occluded(x0, y0, z1)) {
			return false;
		}

		return occluded(x1, y0, z1);
	}

	public boolean occluded(int x, int y, int z) {
		for (int i = 0; i < activeOccluderCount; i++) {
			SceneOccluder occluder = activeOccluders[i];
			if (occluder.mode == 1) {
				int i1 = occluder.anInt792 - x;
				if (i1 <= 0) {
					continue;
				}
				int j2 = occluder.anInt794 + ((occluder.anInt801 * i1) >> 8);
				int k3 = occluder.anInt795 + ((occluder.anInt802 * i1) >> 8);
				int l4 = occluder.anInt796 + ((occluder.anInt803 * i1) >> 8);
				int i6 = occluder.anInt797 + ((occluder.anInt804 * i1) >> 8);
				if ((z >= j2) && (z <= k3) && (y >= l4) && (y <= i6)) {
					return true;
				}
			} else if (occluder.mode == 2) {
				int j1 = x - occluder.anInt792;
				if (j1 <= 0) {
					continue;
				}
				int k2 = occluder.anInt794 + ((occluder.anInt801 * j1) >> 8);
				int l3 = occluder.anInt795 + ((occluder.anInt802 * j1) >> 8);
				int i5 = occluder.anInt796 + ((occluder.anInt803 * j1) >> 8);
				int j6 = occluder.anInt797 + ((occluder.anInt804 * j1) >> 8);
				if ((z >= k2) && (z <= l3) && (y >= i5) && (y <= j6)) {
					return true;
				}
			} else if (occluder.mode == 3) {
				int k1 = occluder.anInt794 - z;
				if (k1 <= 0) {
					continue;
				}
				int l2 = occluder.anInt792 + ((occluder.anInt799 * k1) >> 8);
				int i4 = occluder.anInt793 + ((occluder.anInt800 * k1) >> 8);
				int j5 = occluder.anInt796 + ((occluder.anInt803 * k1) >> 8);
				int k6 = occluder.anInt797 + ((occluder.anInt804 * k1) >> 8);
				if ((x >= l2) && (x <= i4) && (y >= j5) && (y <= k6)) {
					return true;
				}
			} else if (occluder.mode == 4) {
				int l1 = z - occluder.anInt794;
				if (l1 <= 0) {
					continue;
				}
				int i3 = occluder.anInt792 + ((occluder.anInt799 * l1) >> 8);
				int j4 = occluder.anInt793 + ((occluder.anInt800 * l1) >> 8);
				int k5 = occluder.anInt796 + ((occluder.anInt803 * l1) >> 8);
				int l6 = occluder.anInt797 + ((occluder.anInt804 * l1) >> 8);
				if ((x >= i3) && (x <= j4) && (y >= k5) && (y <= l6)) {
					return true;
				}
			} else if (occluder.mode == 5) {
				int i2 = y - occluder.anInt796;
				if (i2 <= 0) {
					continue;
				}
				int j3 = occluder.anInt792 + ((occluder.anInt799 * i2) >> 8);
				int k4 = occluder.anInt793 + ((occluder.anInt800 * i2) >> 8);
				int l5 = occluder.anInt794 + ((occluder.anInt801 * i2) >> 8);
				int i7 = occluder.anInt795 + ((occluder.anInt802 * i2) >> 8);
				if ((x >= j3) && (x <= k4) && (z >= l5) && (z <= i7)) {
					return true;
				}
			}
		}
		return false;
	}

}
