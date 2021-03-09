// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.util.Arrays;

public class SceneGraph {

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
	public static boolean aBoolean436 = true;
	public static int anInt446;
	public static int anInt447;
	public static int anInt448;
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
	public static SceneLoc[] aLocArray462 = new SceneLoc[100];
	public static boolean aBoolean467;
	public static int anInt468;
	public static int anInt469;
	public static int anInt470 = -1;
	public static int anInt471 = -1;
	public static final int anInt472 = 4;
	public static int[] anIntArray473 = new int[anInt472];
	public static SceneOccluder[][] aOccluderArrayArray474 = new SceneOccluder[anInt472][500];
	public static int anInt475;
	public static final SceneOccluder[] A_OCCLUDER_ARRAY_476 = new SceneOccluder[500];
	public static LinkedList aList_477 = new LinkedList();
	public static boolean[][][][] aBooleanArrayArrayArrayArray491 = new boolean[8][32][51][51];
	public static boolean[][] aBooleanArrayArray492;
	public static int anInt493;
	public static int anInt494;
	public static int anInt495;
	public static int anInt496;
	public static int anInt497;
	public static int anInt498;

	public final int anInt437;
	public final int anInt438;
	public final int anInt439;
	public final int[][][] anIntArrayArrayArray440;
	public final SceneTile[][][] aTileArrayArrayArray441;
	public int anInt442;
	public int anInt443;
	public final SceneLoc[] aLocArray444 = new SceneLoc[5000];
	public final int[][][] anIntArrayArrayArray445;
	public final int[] anIntArray486 = new int[10000];
	public final int[] anIntArray487 = new int[10000];
	public int anInt488;
	public final int[][] anIntArrayArray489 = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
	public final int[][] anIntArrayArray490 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};

	public SceneGraph(int i, int j, int[][][] ai, int k) {
		anInt437 = k;
		anInt438 = j;
		anInt439 = i;
		aTileArrayArrayArray441 = new SceneTile[k][j][i];
		anIntArrayArrayArray445 = new int[k][j + 1][i + 1];
		anIntArrayArrayArray440 = ai;
		method274();
	}

	public static void method273() {
		aLocArray462 = null;
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

	public void method274() {
		for (int j = 0; j < anInt437; j++) {
			for (int k = 0; k < anInt438; k++) {
				for (int i1 = 0; i1 < anInt439; i1++) {
					aTileArrayArrayArray441[j][k][i1] = null;
				}
			}
		}
		for (int l = 0; l < anInt472; l++) {
			for (int j1 = 0; j1 < anIntArray473[l]; j1++) {
				aOccluderArrayArray474[l][j1] = null;
			}
			anIntArray473[l] = 0;
		}
		for (int k1 = 0; k1 < anInt443; k1++) {
			aLocArray444[k1] = null;
		}
		anInt443 = 0;
		Arrays.fill(aLocArray462, null);
	}

	public void method275(int i) {
		anInt442 = i;
		for (int k = 0; k < anInt438; k++) {
			for (int l = 0; l < anInt439; l++) {
				if (aTileArrayArrayArray441[i][k][l] == null) {
					aTileArrayArrayArray441[i][k][l] = new SceneTile(i, k, l);
				}
			}
		}
	}

	public void method276(int i, int j) {
		SceneTile tile = aTileArrayArrayArray441[0][j][i];
		for (int l = 0; l < 3; l++) {
			SceneTile tile_1 = aTileArrayArrayArray441[l][j][i] = aTileArrayArrayArray441[l + 1][j][i];
			if (tile_1 != null) {
				tile_1.anInt1307--;
				for (int j1 = 0; j1 < tile_1.anInt1317; j1++) {
					SceneLoc loc = tile_1.aLocArray1318[j1];
					if ((((loc.anInt529 >> 29) & 3) == 2) && (loc.anInt523 == j) && (loc.anInt525 == i)) {
						loc.anInt517--;
					}
				}
			}
		}
		if (aTileArrayArrayArray441[0][j][i] == null) {
			aTileArrayArrayArray441[0][j][i] = new SceneTile(0, j, i);
		}
		aTileArrayArrayArray441[0][j][i].aTile_1329 = tile;
		aTileArrayArrayArray441[3][j][i] = null;
	}

	public void method278(int i, int j, int k, int l) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if (tile == null) {
		} else {
			aTileArrayArrayArray441[i][j][k].anInt1321 = l;
		}
	}

	public void method279(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4) {
		if (l == 0) {
			SceneTileUnderlay underlay = new SceneTileUnderlay(k2, l2, i3, j3, -1, k4, false);
			for (int i5 = i; i5 >= 0; i5--) {
				if (aTileArrayArrayArray441[i5][j][k] == null) {
					aTileArrayArrayArray441[i5][j][k] = new SceneTile(i5, j, k);
				}
			}
			aTileArrayArrayArray441[i][j][k].aUnderlay_1311 = underlay;
			return;
		}
		if (l == 1) {
			SceneTileUnderlay underlay_1 = new SceneTileUnderlay(k3, l3, i4, j4, j1, l4, (k1 == l1) && (k1 == i2) && (k1 == j2));
			for (int j5 = i; j5 >= 0; j5--) {
				if (aTileArrayArrayArray441[j5][j][k] == null) {
					aTileArrayArrayArray441[j5][j][k] = new SceneTile(j5, j, k);
				}
			}
			aTileArrayArrayArray441[i][j][k].aUnderlay_1311 = underlay_1;
			return;
		}
		SceneTileOverlay overlay = new SceneTileOverlay(k, k3, j3, i2, j1, i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4);
		for (int k5 = i; k5 >= 0; k5--) {
			if (aTileArrayArrayArray441[k5][j][k] == null) {
				aTileArrayArrayArray441[k5][j][k] = new SceneTile(k5, j, k);
			}
		}
		aTileArrayArrayArray441[i][j][k].aOverlay_1312 = overlay;
	}

	public void method280(int i, int j, int k, Entity entity, byte byte0, int i1, int j1) {
		if (entity == null) {
			return;
		}
		SceneGroundDecoration groundDecoration = new SceneGroundDecoration();
		groundDecoration.aEntity_814 = entity;
		groundDecoration.anInt812 = (j1 * 128) + 64;
		groundDecoration.anInt813 = (k * 128) + 64;
		groundDecoration.anInt811 = j;
		groundDecoration.anInt815 = i1;
		groundDecoration.aByte816 = byte0;
		if (aTileArrayArrayArray441[i][j1][k] == null) {
			aTileArrayArrayArray441[i][j1][k] = new SceneTile(i, j1, k);
		}
		aTileArrayArrayArray441[i][j1][k].aGroundDecoration_1315 = groundDecoration;
	}

	public void method281(int i, int j, Entity entity, int k, Entity entity_1, Entity class30_sub2_sub4_2, int l, int i1) {
		SceneObjStack objStack = new SceneObjStack();
		objStack.aEntity_48 = class30_sub2_sub4_2;
		objStack.anInt46 = (i * 128) + 64;
		objStack.anInt47 = (i1 * 128) + 64;
		objStack.anInt45 = k;
		objStack.anInt51 = j;
		objStack.aEntity_49 = entity;
		objStack.aEntity_50 = entity_1;
		int j1 = 0;
		SceneTile tile = aTileArrayArrayArray441[l][i][i1];
		if (tile != null) {
			for (int k1 = 0; k1 < tile.anInt1317; k1++) {
				if (tile.aLocArray1318[k1].aEntity_521 instanceof Model) {
					int l1 = ((Model) tile.aLocArray1318[k1].aEntity_521).anInt1654;
					if (l1 > j1) {
						j1 = l1;
					}
				}
			}
		}
		objStack.anInt52 = j1;
		if (aTileArrayArrayArray441[l][i][i1] == null) {
			aTileArrayArrayArray441[l][i][i1] = new SceneTile(l, i, i1);
		}
		aTileArrayArrayArray441[l][i][i1].aObjStack_1316 = objStack;
	}

	public void method282(int i, Entity entity, int j, int k, byte byte0, int l, Entity entity_1, int i1, int j1, int k1) {
		if ((entity == null) && (entity_1 == null)) {
			return;
		}
		SceneWall wall = new SceneWall();
		wall.anInt280 = j;
		wall.aByte281 = byte0;
		wall.anInt274 = (l * 128) + 64;
		wall.anInt275 = (k * 128) + 64;
		wall.anInt273 = i1;
		wall.aEntity_278 = entity;
		wall.aEntity_279 = entity_1;
		wall.anInt276 = i;
		wall.anInt277 = j1;
		for (int l1 = k1; l1 >= 0; l1--) {
			if (aTileArrayArrayArray441[l1][l][k] == null) {
				aTileArrayArrayArray441[l1][l][k] = new SceneTile(l1, l, k);
			}
		}
		aTileArrayArrayArray441[k1][l][k].aWall_1313 = wall;
	}

	public void method283(int i, int j, int k, int i1, int j1, int k1, Entity entity, int l1, byte byte0, int i2, int j2) {
		if (entity == null) {
			return;
		}
		SceneWallDecoration wallDecoration = new SceneWallDecoration();
		wallDecoration.anInt505 = i;
		wallDecoration.aByte506 = byte0;
		wallDecoration.anInt500 = (l1 * 128) + 64 + j1;
		wallDecoration.anInt501 = (j * 128) + 64 + i2;
		wallDecoration.anInt499 = k1;
		wallDecoration.aEntity_504 = entity;
		wallDecoration.anInt502 = j2;
		wallDecoration.anInt503 = k;
		for (int k2 = i1; k2 >= 0; k2--) {
			if (aTileArrayArrayArray441[k2][l1][j] == null) {
				aTileArrayArrayArray441[k2][l1][j] = new SceneTile(k2, l1, j);
			}
		}
		aTileArrayArrayArray441[i1][l1][j].aWallDecoration_1314 = wallDecoration;
	}

	public boolean method284(int i, byte byte0, int j, int k, Entity entity, int l, int i1, int j1, int k1, int l1) {
		if (entity == null) {
			return true;
		} else {
			int i2 = (l1 * 128) + (64 * l);
			int j2 = (k1 * 128) + (64 * k);
			return method287(i1, l1, k1, l, k, i2, j2, j, entity, j1, false, i, byte0);
		}
	}

	public boolean method285(int i, int j, int k, int l, int i1, int j1, int k1, Entity entity, boolean flag) {
		if (entity == null) {
			return true;
		}
		int l1 = k1 - j1;
		int i2 = i1 - j1;
		int j2 = k1 + j1;
		int k2 = i1 + j1;
		if (flag) {
			if ((j > 640) && (j < 1408)) {
				k2 += 128;
			}
			if ((j > 1152) && (j < 1920)) {
				j2 += 128;
			}
			if ((j > 1664) || (j < 384)) {
				i2 -= 128;
			}
			if ((j > 128) && (j < 896)) {
				l1 -= 128;
			}
		}
		l1 /= 128;
		i2 /= 128;
		j2 /= 128;
		k2 /= 128;
		return method287(i, l1, i2, (j2 - l1) + 1, (k2 - i2) + 1, k1, i1, k, entity, j, true, l, (byte) 0);
	}

	public boolean method286(int j, int k, Entity entity, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2) {
		if (entity == null) {
			return true;
		} else {
			return method287(j, l1, k2, (i2 - l1) + 1, (i1 - k2) + 1, j1, k, k1, entity, l, true, j2, (byte) 0);
		}
	}

	public boolean method287(int i, int j, int k, int l, int i1, int j1, int k1, int l1, Entity entity, int i2, boolean flag, int j2, byte byte0) {
		for (int k2 = j; k2 < (j + l); k2++) {
			for (int l2 = k; l2 < (k + i1); l2++) {
				if ((k2 < 0) || (l2 < 0) || (k2 >= anInt438) || (l2 >= anInt439)) {
					return false;
				}
				SceneTile tile = aTileArrayArrayArray441[i][k2][l2];
				if ((tile != null) && (tile.anInt1317 >= 5)) {
					return false;
				}
			}
		}
		SceneLoc loc = new SceneLoc();
		loc.anInt529 = j2;
		loc.aByte530 = byte0;
		loc.anInt517 = i;
		loc.anInt519 = j1;
		loc.anInt520 = k1;
		loc.anInt518 = l1;
		loc.aEntity_521 = entity;
		loc.anInt522 = i2;
		loc.anInt523 = j;
		loc.anInt525 = k;
		loc.anInt524 = (j + l) - 1;
		loc.anInt526 = (k + i1) - 1;
		for (int i3 = j; i3 < (j + l); i3++) {
			for (int j3 = k; j3 < (k + i1); j3++) {
				int k3 = 0;
				if (i3 > j) {
					k3++;
				}
				if (i3 < ((j + l) - 1)) {
					k3 += 4;
				}
				if (j3 > k) {
					k3 += 8;
				}
				if (j3 < ((k + i1) - 1)) {
					k3 += 2;
				}
				for (int l3 = i; l3 >= 0; l3--) {
					if (aTileArrayArrayArray441[l3][i3][j3] == null) {
						aTileArrayArrayArray441[l3][i3][j3] = new SceneTile(l3, i3, j3);
					}
				}
				SceneTile tile_1 = aTileArrayArrayArray441[i][i3][j3];
				tile_1.aLocArray1318[tile_1.anInt1317] = loc;
				tile_1.anIntArray1319[tile_1.anInt1317] = k3;
				tile_1.anInt1320 |= k3;
				tile_1.anInt1317++;
			}
		}
		if (flag) {
			aLocArray444[anInt443++] = loc;
		}
		return true;
	}

	public void method288() {
		for (int i = 0; i < anInt443; i++) {
			SceneLoc loc = aLocArray444[i];
			method289(loc);
			aLocArray444[i] = null;
		}
		anInt443 = 0;
	}

	public void method289(SceneLoc loc) {
		for (int j = loc.anInt523; j <= loc.anInt524; j++) {
			for (int k = loc.anInt525; k <= loc.anInt526; k++) {
				SceneTile tile = aTileArrayArrayArray441[loc.anInt517][j][k];
				if (tile != null) {
					for (int l = 0; l < tile.anInt1317; l++) {
						if (tile.aLocArray1318[l] != loc) {
							continue;
						}
						tile.anInt1317--;
						for (int i1 = l; i1 < tile.anInt1317; i1++) {
							tile.aLocArray1318[i1] = tile.aLocArray1318[i1 + 1];
							tile.anIntArray1319[i1] = tile.anIntArray1319[i1 + 1];
						}
						tile.aLocArray1318[tile.anInt1317] = null;
						break;
					}
					tile.anInt1320 = 0;
					for (int j1 = 0; j1 < tile.anInt1317; j1++) {
						tile.anInt1320 |= tile.anIntArray1319[j1];
					}
				}
			}
		}
	}

	public void method290(int i, int k, int l, int i1) {
		SceneTile tile = aTileArrayArrayArray441[i1][l][i];
		if (tile == null) {
			return;
		}
		SceneWallDecoration wallDecoration = tile.aWallDecoration_1314;
		if (wallDecoration != null) {
			int j1 = (l * 128) + 64;
			int k1 = (i * 128) + 64;
			wallDecoration.anInt500 = j1 + (((wallDecoration.anInt500 - j1) * k) / 16);
			wallDecoration.anInt501 = k1 + (((wallDecoration.anInt501 - k1) * k) / 16);
		}
	}

	public void method291(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[j][i][k];
		if (tile != null) {
			tile.aWall_1313 = null;
		}
	}

	public void method292(int j, int k, int l) {
		SceneTile tile = aTileArrayArrayArray441[k][l][j];
		if (tile != null) {
			tile.aWallDecoration_1314 = null;
		}
	}

	public void method293(int i, int k, int l) {
		SceneTile tile = aTileArrayArrayArray441[i][k][l];
		if (tile == null) {
			return;
		}
		for (int j1 = 0; j1 < tile.anInt1317; j1++) {
			SceneLoc loc = tile.aLocArray1318[j1];
			if ((((loc.anInt529 >> 29) & 3) == 2) && (loc.anInt523 == k) && (loc.anInt525 == l)) {
				method289(loc);
				return;
			}
		}
	}

	public void method294(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][k][j];
		if (tile != null) {
			tile.aGroundDecoration_1315 = null;
		}
	}

	public void method295(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if (tile != null) {
			tile.aObjStack_1316 = null;
		}
	}

	public SceneWall method296(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if (tile != null) {
			return tile.aWall_1313;
		} else {
			return null;
		}
	}

	public SceneWallDecoration method297(int i, int k, int l) {
		SceneTile tile = aTileArrayArrayArray441[l][i][k];
		if (tile != null) {
			return tile.aWallDecoration_1314;
		} else {
			return null;
		}
	}

	public SceneLoc method298(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[k][i][j];
		if (tile == null) {
			return null;
		}
		for (int l = 0; l < tile.anInt1317; l++) {
			SceneLoc loc = tile.aLocArray1318[l];
			if ((((loc.anInt529 >> 29) & 3) == 2) && (loc.anInt523 == i) && (loc.anInt525 == j)) {
				return loc;
			}
		}
		return null;
	}

	public SceneGroundDecoration method299(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[k][j][i];
		if ((tile != null) && (tile.aGroundDecoration_1315 != null)) {
			return tile.aGroundDecoration_1315;
		} else {
			return null;
		}
	}

	public int method300(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if ((tile != null) && (tile.aWall_1313 != null)) {
			return tile.aWall_1313.anInt280;
		} else {
			return 0;
		}
	}

	public int method301(int i, int j, int l) {
		SceneTile tile = aTileArrayArrayArray441[i][j][l];
		if ((tile == null) || (tile.aWallDecoration_1314 == null)) {
			return 0;
		} else {
			return tile.aWallDecoration_1314.anInt505;
		}
	}

	public int method302(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if (tile == null) {
			return 0;
		}
		for (int l = 0; l < tile.anInt1317; l++) {
			SceneLoc loc = tile.aLocArray1318[l];
			if ((((loc.anInt529 >> 29) & 3) == 2) && (loc.anInt523 == j) && (loc.anInt525 == k)) {
				return loc.anInt529;
			}
		}
		return 0;
	}

	public int method303(int i, int j, int k) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if ((tile == null) || (tile.aGroundDecoration_1315 == null)) {
			return 0;
		} else {
			return tile.aGroundDecoration_1315.anInt815;
		}
	}

	public int method304(int i, int j, int k, int l) {
		SceneTile tile = aTileArrayArrayArray441[i][j][k];
		if (tile == null) {
			return -1;
		}
		if ((tile.aWall_1313 != null) && (tile.aWall_1313.anInt280 == l)) {
			return tile.aWall_1313.aByte281 & 0xff;
		}
		if ((tile.aWallDecoration_1314 != null) && (tile.aWallDecoration_1314.anInt505 == l)) {
			return tile.aWallDecoration_1314.aByte506 & 0xff;
		}
		if ((tile.aGroundDecoration_1315 != null) && (tile.aGroundDecoration_1315.anInt815 == l)) {
			return tile.aGroundDecoration_1315.aByte816 & 0xff;
		}
		for (int i1 = 0; i1 < tile.anInt1317; i1++) {
			if (tile.aLocArray1318[i1].anInt529 == l) {
				return tile.aLocArray1318[i1].aByte530 & 0xff;
			}
		}
		return -1;
	}

	public void method305(int i, int j, int k, int l, int i1) {
		int j1 = (int) Math.sqrt((k * k) + (i * i) + (i1 * i1));
		int k1 = (l * j1) >> 8;
		for (int l1 = 0; l1 < anInt437; l1++) {
			for (int i2 = 0; i2 < anInt438; i2++) {
				for (int j2 = 0; j2 < anInt439; j2++) {
					SceneTile tile = aTileArrayArrayArray441[l1][i2][j2];
					if (tile != null) {
						SceneWall wall = tile.aWall_1313;
						if ((wall != null) && (wall.aEntity_278 != null) && (wall.aEntity_278.vertexNormal != null)) {
							method307(l1, 1, 1, i2, j2, (Model) wall.aEntity_278);
							if ((wall.aEntity_279 != null) && (wall.aEntity_279.vertexNormal != null)) {
								method307(l1, 1, 1, i2, j2, (Model) wall.aEntity_279);
								method308((Model) wall.aEntity_278, (Model) wall.aEntity_279, 0, 0, 0, false);
								((Model) wall.aEntity_279).applyLighting(j, k1, k, i, i1);
							}
							((Model) wall.aEntity_278).applyLighting(j, k1, k, i, i1);
						}
						for (int k2 = 0; k2 < tile.anInt1317; k2++) {
							SceneLoc loc = tile.aLocArray1318[k2];
							if ((loc != null) && (loc.aEntity_521 != null) && (loc.aEntity_521.vertexNormal != null)) {
								method307(l1, (loc.anInt524 - loc.anInt523) + 1, (loc.anInt526 - loc.anInt525) + 1, i2, j2, (Model) loc.aEntity_521);
								((Model) loc.aEntity_521).applyLighting(j, k1, k, i, i1);
							}
						}
						SceneGroundDecoration groundDecoration = tile.aGroundDecoration_1315;
						if ((groundDecoration != null) && (groundDecoration.aEntity_814.vertexNormal != null)) {
							method306(i2, l1, (Model) groundDecoration.aEntity_814, j2);
							((Model) groundDecoration.aEntity_814).applyLighting(j, k1, k, i, i1);
						}
					}
				}
			}
		}
	}

	public void method306(int i, int j, Model model, int k) {
		if (i < anInt438) {
			SceneTile tile = aTileArrayArrayArray441[j][i + 1][k];
			if ((tile != null) && (tile.aGroundDecoration_1315 != null) && (tile.aGroundDecoration_1315.aEntity_814.vertexNormal != null)) {
				method308(model, (Model) tile.aGroundDecoration_1315.aEntity_814, 128, 0, 0, true);
			}
		}
		if (k < anInt438) {
			SceneTile tile_1 = aTileArrayArrayArray441[j][i][k + 1];
			if ((tile_1 != null) && (tile_1.aGroundDecoration_1315 != null) && (tile_1.aGroundDecoration_1315.aEntity_814.vertexNormal != null)) {
				method308(model, (Model) tile_1.aGroundDecoration_1315.aEntity_814, 0, 0, 128, true);
			}
		}
		if ((i < anInt438) && (k < anInt439)) {
			SceneTile tile_2 = aTileArrayArrayArray441[j][i + 1][k + 1];
			if ((tile_2 != null) && (tile_2.aGroundDecoration_1315 != null) && (tile_2.aGroundDecoration_1315.aEntity_814.vertexNormal != null)) {
				method308(model, (Model) tile_2.aGroundDecoration_1315.aEntity_814, 128, 0, 128, true);
			}
		}
		if ((i < anInt438) && (k > 0)) {
			SceneTile tile_3 = aTileArrayArrayArray441[j][i + 1][k - 1];
			if ((tile_3 != null) && (tile_3.aGroundDecoration_1315 != null) && (tile_3.aGroundDecoration_1315.aEntity_814.vertexNormal != null)) {
				method308(model, (Model) tile_3.aGroundDecoration_1315.aEntity_814, 128, 0, -128, true);
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
			if (j2 != anInt437) {
				for (int k2 = j1; k2 <= k1; k2++) {
					if ((k2 >= 0) && (k2 < anInt438)) {
						for (int l2 = l1; l2 <= i2; l2++) {
							if ((l2 >= 0) && (l2 < anInt439) && (!flag || (k2 >= k1) || (l2 >= i2) || ((l2 < i1) && (k2 != l)))) {
								SceneTile tile = aTileArrayArrayArray441[j2][k2][l2];
								if (tile != null) {
									int i3 = ((anIntArrayArrayArray440[j2][k2][l2] + anIntArrayArrayArray440[j2][k2 + 1][l2] + anIntArrayArrayArray440[j2][k2][l2 + 1] + anIntArrayArrayArray440[j2][k2 + 1][l2 + 1]) / 4) - ((anIntArrayArrayArray440[i][l][i1] + anIntArrayArrayArray440[i][l + 1][i1] + anIntArrayArrayArray440[i][l][i1 + 1] + anIntArrayArrayArray440[i][l + 1][i1 + 1]) / 4);
									SceneWall wall = tile.aWall_1313;
									if ((wall != null) && (wall.aEntity_278 != null) && (wall.aEntity_278.vertexNormal != null)) {
										method308(model, (Model) wall.aEntity_278, ((k2 - l) * 128) + ((1 - j) * 64), i3, ((l2 - i1) * 128) + ((1 - k) * 64), flag);
									}
									if ((wall != null) && (wall.aEntity_279 != null) && (wall.aEntity_279.vertexNormal != null)) {
										method308(model, (Model) wall.aEntity_279, ((k2 - l) * 128) + ((1 - j) * 64), i3, ((l2 - i1) * 128) + ((1 - k) * 64), flag);
									}
									for (int j3 = 0; j3 < tile.anInt1317; j3++) {
										SceneLoc loc = tile.aLocArray1318[j3];
										if ((loc != null) && (loc.aEntity_521 != null) && (loc.aEntity_521.vertexNormal != null)) {
											int k3 = (loc.anInt524 - loc.anInt523) + 1;
											int l3 = (loc.anInt526 - loc.anInt525) + 1;
											method308(model, (Model) loc.aEntity_521, ((loc.anInt523 - l) * 128) + ((k3 - j) * 64), i3, ((loc.anInt525 - i1) * 128) + ((l3 - k) * 64), flag);
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
			if (normal_1.w != 0) {
				int i2 = model.vertexY[j1] - j;
				if (i2 <= model_1.maxY) {
					int j2 = model.vertexX[j1] - i;
					if ((j2 >= model_1.minX) && (j2 <= model_1.maxX)) {
						int k2 = model.vertexZ[j1] - k;
						if ((k2 >= model_1.minZ) && (k2 <= model_1.maxZ)) {
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
					}
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

	public void method309(int[] ai, int i, int j, int k, int l, int i1) {
		SceneTile tile = aTileArrayArrayArray441[k][l][i1];
		if (tile == null) {
			return;
		}
		SceneTileUnderlay underlay = tile.aUnderlay_1311;
		if (underlay != null) {
			int j1 = underlay.anInt722;
			if (j1 == 0) {
				return;
			}
			for (int k1 = 0; k1 < 4; k1++) {
				ai[i] = j1;
				ai[i + 1] = j1;
				ai[i + 2] = j1;
				ai[i + 3] = j1;
				i += j;
			}
			return;
		}
		SceneTileOverlay overlay = tile.aOverlay_1312;
		if (overlay == null) {
			return;
		}
		int l1 = overlay.anInt684;
		int i2 = overlay.anInt685;
		int j2 = overlay.anInt686;
		int k2 = overlay.anInt687;
		int[] ai1 = anIntArrayArray489[l1];
		int[] ai2 = anIntArrayArray490[i2];
		int l2 = 0;
		if (j2 != 0) {
			for (int i3 = 0; i3 < 4; i3++) {
				ai[i] = (ai1[ai2[l2++]] != 0) ? k2 : j2;
				ai[i + 1] = (ai1[ai2[l2++]] != 0) ? k2 : j2;
				ai[i + 2] = (ai1[ai2[l2++]] != 0) ? k2 : j2;
				ai[i + 3] = (ai1[ai2[l2++]] != 0) ? k2 : j2;
				i += j;
			}
			return;
		}
		for (int j3 = 0; j3 < 4; j3++) {
			if (ai1[ai2[l2++]] != 0) {
				ai[i] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 1] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 2] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 3] = k2;
			}
			i += j;
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
		} else if (eyeX >= (anInt438 * 128)) {
			eyeX = (anInt438 * 128) - 1;
		}
		if (eyeZ < 0) {
			eyeZ = 0;
		} else if (eyeZ >= (anInt439 * 128)) {
			eyeZ = (anInt439 * 128) - 1;
		}
		anInt448++;
		sinEyePitch = Model.sin[eyePitch];
		cosEyePitch = Model.cos[eyePitch];
		sinEyeYaw = Model.sin[eyeYaw];
		cosEyeYaw = Model.cos[eyeYaw];
		aBooleanArrayArray492 = aBooleanArrayArrayArrayArray491[(eyePitch - 128) / 32][eyeYaw / 64];
		SceneGraph.eyeX = eyeX;
		SceneGraph.eyeY = eyeY;
		SceneGraph.eyeZ = eyeZ;
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
		if (anInt450 > anInt438) {
			anInt450 = anInt438;
		}
		anInt452 = eyeTileZ + 25;
		if (anInt452 > anInt439) {
			anInt452 = anInt439;
		}
		method319();
		anInt446 = 0;
		for (int k1 = anInt442; k1 < anInt437; k1++) {
			SceneTile[][] aclass30_sub3 = aTileArrayArrayArray441[k1];
			for (int i2 = anInt449; i2 < anInt450; i2++) {
				for (int k2 = anInt451; k2 < anInt452; k2++) {
					SceneTile tile = aclass30_sub3[i2][k2];
					if (tile != null) {
						if ((tile.anInt1321 > i1) || (!aBooleanArrayArray492[(i2 - eyeTileX) + 25][(k2 - eyeTileZ) + 25] && ((anIntArrayArrayArray440[k1][i2][k2] - eyeY) < 2000))) {
							tile.aBoolean1322 = false;
							tile.aBoolean1323 = false;
							tile.anInt1325 = 0;
						} else {
							tile.aBoolean1322 = true;
							tile.aBoolean1323 = true;
							tile.aBoolean1324 = tile.anInt1317 > 0;
							anInt446++;
						}
					}
				}
			}
		}
		for (int l1 = anInt442; l1 < anInt437; l1++) {
			SceneTile[][] aclass30_sub3_1 = aTileArrayArrayArray441[l1];
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
		for (int j2 = anInt442; j2 < anInt437; j2++) {
			SceneTile[][] aclass30_sub3_2 = aTileArrayArrayArray441[j2];
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
		aList_477.method249(tile);
		do {
			SceneTile tile_1;
			do {
				tile_1 = (SceneTile) aList_477.method251();
				if (tile_1 == null) {
					return;
				}
			} while (!tile_1.aBoolean1323);
			int i = tile_1.anInt1308;
			int j = tile_1.anInt1309;
			int k = tile_1.anInt1307;
			int l = tile_1.anInt1310;
			SceneTile[][] aclass30_sub3 = aTileArrayArrayArray441[k];
			if (tile_1.aBoolean1322) {
				if (flag) {
					if (k > 0) {
						SceneTile tile_2 = aTileArrayArrayArray441[k - 1][i][j];
						if ((tile_2 != null) && tile_2.aBoolean1323) {
							continue;
						}
					}
					if ((i <= eyeTileX) && (i > anInt449)) {
						SceneTile tile_3 = aclass30_sub3[i - 1][j];
						if ((tile_3 != null) && tile_3.aBoolean1323 && (tile_3.aBoolean1322 || ((tile_1.anInt1320 & 1) == 0))) {
							continue;
						}
					}
					if ((i >= eyeTileX) && (i < (anInt450 - 1))) {
						SceneTile tile_4 = aclass30_sub3[i + 1][j];
						if ((tile_4 != null) && tile_4.aBoolean1323 && (tile_4.aBoolean1322 || ((tile_1.anInt1320 & 4) == 0))) {
							continue;
						}
					}
					if ((j <= eyeTileZ) && (j > anInt451)) {
						SceneTile tile_5 = aclass30_sub3[i][j - 1];
						if ((tile_5 != null) && tile_5.aBoolean1323 && (tile_5.aBoolean1322 || ((tile_1.anInt1320 & 8) == 0))) {
							continue;
						}
					}
					if ((j >= eyeTileZ) && (j < (anInt452 - 1))) {
						SceneTile tile_6 = aclass30_sub3[i][j + 1];
						if ((tile_6 != null) && tile_6.aBoolean1323 && (tile_6.aBoolean1322 || ((tile_1.anInt1320 & 2) == 0))) {
							continue;
						}
					}
				} else {
					flag = true;
				}
				tile_1.aBoolean1322 = false;
				if (tile_1.aTile_1329 != null) {
					SceneTile tile_7 = tile_1.aTile_1329;
					if (tile_7.aUnderlay_1311 != null) {
						if (!method320(0, i, j)) {
							method315(tile_7.aUnderlay_1311, 0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i, j);
						}
					} else if ((tile_7.aOverlay_1312 != null) && !method320(0, i, j)) {
						method316(i, sinEyePitch, sinEyeYaw, tile_7.aOverlay_1312, cosEyePitch, j, cosEyeYaw);
					}
					SceneWall wall = tile_7.aWall_1313;
					if (wall != null) {
						wall.aEntity_278.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall.anInt274 - eyeX, wall.anInt273 - eyeY, wall.anInt275 - eyeZ, wall.anInt280);
					}
					for (int i2 = 0; i2 < tile_7.anInt1317; i2++) {
						SceneLoc loc = tile_7.aLocArray1318[i2];
						if (loc != null) {
							loc.aEntity_521.draw(loc.anInt522, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, loc.anInt519 - eyeX, loc.anInt518 - eyeY, loc.anInt520 - eyeZ, loc.anInt529);
						}
					}
				}
				boolean flag1 = false;
				if (tile_1.aUnderlay_1311 != null) {
					if (!method320(l, i, j)) {
						flag1 = true;
						method315(tile_1.aUnderlay_1311, l, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i, j);
					}
				} else if ((tile_1.aOverlay_1312 != null) && !method320(l, i, j)) {
					flag1 = true;
					method316(i, sinEyePitch, sinEyeYaw, tile_1.aOverlay_1312, cosEyePitch, j, cosEyeYaw);
				}
				int j1 = 0;
				int j2 = 0;
				SceneWall wall_3 = tile_1.aWall_1313;
				SceneWallDecoration wallDecoration_1 = tile_1.aWallDecoration_1314;
				if ((wall_3 != null) || (wallDecoration_1 != null)) {
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
				if (wall_3 != null) {
					if ((wall_3.anInt276 & anIntArray479[j1]) != 0) {
						if (wall_3.anInt276 == 16) {
							tile_1.anInt1325 = 3;
							tile_1.anInt1326 = anIntArray481[j1];
							tile_1.anInt1327 = 3 - tile_1.anInt1326;
						} else if (wall_3.anInt276 == 32) {
							tile_1.anInt1325 = 6;
							tile_1.anInt1326 = anIntArray482[j1];
							tile_1.anInt1327 = 6 - tile_1.anInt1326;
						} else if (wall_3.anInt276 == 64) {
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
					if (((wall_3.anInt276 & j2) != 0) && !method321(l, i, j, wall_3.anInt276)) {
						wall_3.aEntity_278.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_3.anInt274 - eyeX, wall_3.anInt273 - eyeY, wall_3.anInt275 - eyeZ, wall_3.anInt280);
					}
					if (((wall_3.anInt277 & j2) != 0) && !method321(l, i, j, wall_3.anInt277)) {
						wall_3.aEntity_279.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_3.anInt274 - eyeX, wall_3.anInt273 - eyeY, wall_3.anInt275 - eyeZ, wall_3.anInt280);
					}
				}
				if ((wallDecoration_1 != null) && !method322(l, i, j, wallDecoration_1.aEntity_504.minY)) {
					if ((wallDecoration_1.anInt502 & j2) != 0) {
						wallDecoration_1.aEntity_504.draw(wallDecoration_1.anInt503, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wallDecoration_1.anInt500 - eyeX, wallDecoration_1.anInt499 - eyeY, wallDecoration_1.anInt501 - eyeZ, wallDecoration_1.anInt505);
					} else if ((wallDecoration_1.anInt502 & 0x300) != 0) {
						int j4 = wallDecoration_1.anInt500 - eyeX;
						int l5 = wallDecoration_1.anInt499 - eyeY;
						int k6 = wallDecoration_1.anInt501 - eyeZ;
						int i8 = wallDecoration_1.anInt503;
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
						if (((wallDecoration_1.anInt502 & 0x100) != 0) && (k10 < k9)) {
							int i11 = j4 + anIntArray463[i8];
							int k11 = k6 + anIntArray464[i8];
							wallDecoration_1.aEntity_504.draw((i8 * 512) + 256, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i11, l5, k11, wallDecoration_1.anInt505);
						}
						if (((wallDecoration_1.anInt502 & 0x200) != 0) && (k10 > k9)) {
							int j11 = j4 + anIntArray465[i8];
							int l11 = k6 + anIntArray466[i8];
							wallDecoration_1.aEntity_504.draw(((i8 * 512) + 1280) & 0x7ff, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, j11, l5, l11, wallDecoration_1.anInt505);
						}
					}
				}
				if (flag1) {
					SceneGroundDecoration groundDecoration = tile_1.aGroundDecoration_1315;
					if (groundDecoration != null) {
						groundDecoration.aEntity_814.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, groundDecoration.anInt812 - eyeX, groundDecoration.anInt811 - eyeY, groundDecoration.anInt813 - eyeZ, groundDecoration.anInt815);
					}
					SceneObjStack objStack_1 = tile_1.aObjStack_1316;
					if ((objStack_1 != null) && (objStack_1.anInt52 == 0)) {
						if (objStack_1.aEntity_49 != null) {
							objStack_1.aEntity_49.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.anInt46 - eyeX, objStack_1.anInt45 - eyeY, objStack_1.anInt47 - eyeZ, objStack_1.anInt51);
						}
						if (objStack_1.aEntity_50 != null) {
							objStack_1.aEntity_50.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.anInt46 - eyeX, objStack_1.anInt45 - eyeY, objStack_1.anInt47 - eyeZ, objStack_1.anInt51);
						}
						if (objStack_1.aEntity_48 != null) {
							objStack_1.aEntity_48.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack_1.anInt46 - eyeX, objStack_1.anInt45 - eyeY, objStack_1.anInt47 - eyeZ, objStack_1.anInt51);
						}
					}
				}
				int k4 = tile_1.anInt1320;
				if (k4 != 0) {
					if ((i < eyeTileX) && ((k4 & 4) != 0)) {
						SceneTile tile_17 = aclass30_sub3[i + 1][j];
						if ((tile_17 != null) && tile_17.aBoolean1323) {
							aList_477.method249(tile_17);
						}
					}
					if ((j < eyeTileZ) && ((k4 & 2) != 0)) {
						SceneTile tile_18 = aclass30_sub3[i][j + 1];
						if ((tile_18 != null) && tile_18.aBoolean1323) {
							aList_477.method249(tile_18);
						}
					}
					if ((i > eyeTileX) && ((k4 & 1) != 0)) {
						SceneTile tile_19 = aclass30_sub3[i - 1][j];
						if ((tile_19 != null) && tile_19.aBoolean1323) {
							aList_477.method249(tile_19);
						}
					}
					if ((j > eyeTileZ) && ((k4 & 8) != 0)) {
						SceneTile tile_20 = aclass30_sub3[i][j - 1];
						if ((tile_20 != null) && tile_20.aBoolean1323) {
							aList_477.method249(tile_20);
						}
					}
				}
			}
			if (tile_1.anInt1325 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < tile_1.anInt1317; k1++) {
					if ((tile_1.aLocArray1318[k1].anInt528 == anInt448) || ((tile_1.anIntArray1319[k1] & tile_1.anInt1325) != tile_1.anInt1326)) {
						continue;
					}
					flag2 = false;
					break;
				}
				if (flag2) {
					SceneWall wall_1 = tile_1.aWall_1313;
					if (!method321(l, i, j, wall_1.anInt276)) {
						wall_1.aEntity_278.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_1.anInt274 - eyeX, wall_1.anInt273 - eyeY, wall_1.anInt275 - eyeZ, wall_1.anInt280);
					}
					tile_1.anInt1325 = 0;
				}
			}
			if (tile_1.aBoolean1324) {
				try {
					int i1 = tile_1.anInt1317;
					tile_1.aBoolean1324 = false;
					int l1 = 0;
					label0:
					for (int k2 = 0; k2 < i1; k2++) {
						SceneLoc loc_1 = tile_1.aLocArray1318[k2];
						if (loc_1.anInt528 == anInt448) {
							continue;
						}
						for (int k3 = loc_1.anInt523; k3 <= loc_1.anInt524; k3++) {
							for (int l4 = loc_1.anInt525; l4 <= loc_1.anInt526; l4++) {
								SceneTile tile_21 = aclass30_sub3[k3][l4];
								if (!tile_21.aBoolean1322) {
									if (tile_21.anInt1325 == 0) {
										continue;
									}
									int l6 = 0;
									if (k3 > loc_1.anInt523) {
										l6++;
									}
									if (k3 < loc_1.anInt524) {
										l6 += 4;
									}
									if (l4 > loc_1.anInt525) {
										l6 += 8;
									}
									if (l4 < loc_1.anInt526) {
										l6 += 2;
									}
									if ((l6 & tile_21.anInt1325) != tile_1.anInt1327) {
										continue;
									}
								}
								tile_1.aBoolean1324 = true;
								continue label0;
							}
						}
						aLocArray462[l1++] = loc_1;
						int i5 = eyeTileX - loc_1.anInt523;
						int i6 = loc_1.anInt524 - eyeTileX;
						if (i6 > i5) {
							i5 = i6;
						}
						int i7 = eyeTileZ - loc_1.anInt525;
						int j8 = loc_1.anInt526 - eyeTileZ;
						if (j8 > i7) {
							loc_1.anInt527 = i5 + j8;
						} else {
							loc_1.anInt527 = i5 + i7;
						}
					}
					while (true) {
						int i3 = -50;
						int l3 = -1;
						for (int j5 = 0; j5 < l1; j5++) {
							SceneLoc loc_2 = aLocArray462[j5];
							if (loc_2.anInt528 != anInt448) {
								if (loc_2.anInt527 > i3) {
									i3 = loc_2.anInt527;
									l3 = j5;
								} else if (loc_2.anInt527 == i3) {
									int j7 = loc_2.anInt519 - eyeX;
									int k8 = loc_2.anInt520 - eyeZ;
									int l9 = aLocArray462[l3].anInt519 - eyeX;
									int l10 = aLocArray462[l3].anInt520 - eyeZ;
									if (((j7 * j7) + (k8 * k8)) > ((l9 * l9) + (l10 * l10))) {
										l3 = j5;
									}
								}
							}
						}
						if (l3 == -1) {
							break;
						}
						SceneLoc loc_3 = aLocArray462[l3];
						loc_3.anInt528 = anInt448;
						if (!method323(l, loc_3.anInt523, loc_3.anInt524, loc_3.anInt525, loc_3.anInt526, loc_3.aEntity_521.minY)) {
							loc_3.aEntity_521.draw(loc_3.anInt522, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, loc_3.anInt519 - eyeX, loc_3.anInt518 - eyeY, loc_3.anInt520 - eyeZ, loc_3.anInt529);
						}
						for (int k7 = loc_3.anInt523; k7 <= loc_3.anInt524; k7++) {
							for (int l8 = loc_3.anInt525; l8 <= loc_3.anInt526; l8++) {
								SceneTile tile_22 = aclass30_sub3[k7][l8];
								if (tile_22.anInt1325 != 0) {
									aList_477.method249(tile_22);
								} else if (((k7 != i) || (l8 != j)) && tile_22.aBoolean1323) {
									aList_477.method249(tile_22);
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
				SceneTile tile_8 = aclass30_sub3[i - 1][j];
				if ((tile_8 != null) && tile_8.aBoolean1323) {
					continue;
				}
			}
			if ((i >= eyeTileX) && (i < (anInt450 - 1))) {
				SceneTile tile_9 = aclass30_sub3[i + 1][j];
				if ((tile_9 != null) && tile_9.aBoolean1323) {
					continue;
				}
			}
			if ((j <= eyeTileZ) && (j > anInt451)) {
				SceneTile tile_10 = aclass30_sub3[i][j - 1];
				if ((tile_10 != null) && tile_10.aBoolean1323) {
					continue;
				}
			}
			if ((j >= eyeTileZ) && (j < (anInt452 - 1))) {
				SceneTile tile_11 = aclass30_sub3[i][j + 1];
				if ((tile_11 != null) && tile_11.aBoolean1323) {
					continue;
				}
			}
			tile_1.aBoolean1323 = false;
			anInt446--;
			SceneObjStack objStack = tile_1.aObjStack_1316;
			if ((objStack != null) && (objStack.anInt52 != 0)) {
				if (objStack.aEntity_49 != null) {
					objStack.aEntity_49.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.anInt46 - eyeX, objStack.anInt45 - eyeY - objStack.anInt52, objStack.anInt47 - eyeZ, objStack.anInt51);
				}
				if (objStack.aEntity_50 != null) {
					objStack.aEntity_50.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.anInt46 - eyeX, objStack.anInt45 - eyeY - objStack.anInt52, objStack.anInt47 - eyeZ, objStack.anInt51);
				}
				if (objStack.aEntity_48 != null) {
					objStack.aEntity_48.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, objStack.anInt46 - eyeX, objStack.anInt45 - eyeY - objStack.anInt52, objStack.anInt47 - eyeZ, objStack.anInt51);
				}
			}
			if (tile_1.anInt1328 != 0) {
				SceneWallDecoration wallDecoration = tile_1.aWallDecoration_1314;
				if ((wallDecoration != null) && !method322(l, i, j, wallDecoration.aEntity_504.minY)) {
					if ((wallDecoration.anInt502 & tile_1.anInt1328) != 0) {
						wallDecoration.aEntity_504.draw(wallDecoration.anInt503, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wallDecoration.anInt500 - eyeX, wallDecoration.anInt499 - eyeY, wallDecoration.anInt501 - eyeZ, wallDecoration.anInt505);
					} else if ((wallDecoration.anInt502 & 0x300) != 0) {
						int l2 = wallDecoration.anInt500 - eyeX;
						int j3 = wallDecoration.anInt499 - eyeY;
						int i4 = wallDecoration.anInt501 - eyeZ;
						int k5 = wallDecoration.anInt503;
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
						if (((wallDecoration.anInt502 & 0x100) != 0) && (l7 >= j6)) {
							int i9 = l2 + anIntArray463[k5];
							int i10 = i4 + anIntArray464[k5];
							wallDecoration.aEntity_504.draw((k5 * 512) + 256, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, i9, j3, i10, wallDecoration.anInt505);
						}
						if (((wallDecoration.anInt502 & 0x200) != 0) && (l7 <= j6)) {
							int j9 = l2 + anIntArray465[k5];
							int j10 = i4 + anIntArray466[k5];
							wallDecoration.aEntity_504.draw(((k5 * 512) + 1280) & 0x7ff, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, j9, j3, j10, wallDecoration.anInt505);
						}
					}
				}
				SceneWall wall_2 = tile_1.aWall_1313;
				if (wall_2 != null) {
					if (((wall_2.anInt277 & tile_1.anInt1328) != 0) && !method321(l, i, j, wall_2.anInt277)) {
						wall_2.aEntity_279.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_2.anInt274 - eyeX, wall_2.anInt273 - eyeY, wall_2.anInt275 - eyeZ, wall_2.anInt280);
					}
					if (((wall_2.anInt276 & tile_1.anInt1328) != 0) && !method321(l, i, j, wall_2.anInt276)) {
						wall_2.aEntity_278.draw(0, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, wall_2.anInt274 - eyeX, wall_2.anInt273 - eyeY, wall_2.anInt275 - eyeZ, wall_2.anInt280);
					}
				}
			}
			if (k < (anInt437 - 1)) {
				SceneTile tile_12 = aTileArrayArrayArray441[k + 1][i][j];
				if ((tile_12 != null) && tile_12.aBoolean1323) {
					aList_477.method249(tile_12);
				}
			}
			if (i < eyeTileX) {
				SceneTile tile_13 = aclass30_sub3[i + 1][j];
				if ((tile_13 != null) && tile_13.aBoolean1323) {
					aList_477.method249(tile_13);
				}
			}
			if (j < eyeTileZ) {
				SceneTile tile_14 = aclass30_sub3[i][j + 1];
				if ((tile_14 != null) && tile_14.aBoolean1323) {
					aList_477.method249(tile_14);
				}
			}
			if (i > eyeTileX) {
				SceneTile tile_15 = aclass30_sub3[i - 1][j];
				if ((tile_15 != null) && tile_15.aBoolean1323) {
					aList_477.method249(tile_15);
				}
			}
			if (j > eyeTileZ) {
				SceneTile tile_16 = aclass30_sub3[i][j - 1];
				if ((tile_16 != null) && tile_16.aBoolean1323) {
					aList_477.method249(tile_16);
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
		int l3 = anIntArrayArrayArray440[i][j1][k1] - eyeY;
		int i4 = anIntArrayArrayArray440[i][j1 + 1][k1] - eyeY;
		int j4 = anIntArrayArrayArray440[i][j1 + 1][k1 + 1] - eyeY;
		int k4 = anIntArrayArrayArray440[i][j1][k1 + 1] - eyeY;
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
			} else if (!aBoolean436) {
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
				if (!aBoolean436) {
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
				} else if (!aBoolean436) {
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
		anInt475 = 0;
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
					occluder.anInt798 = 1;
				} else {
					if (j3 >= -32) {
						continue;
					}
					occluder.anInt798 = 2;
					j3 = -j3;
				}
				occluder.anInt801 = ((occluder.anInt794 - eyeZ) << 8) / j3;
				occluder.anInt802 = ((occluder.anInt795 - eyeZ) << 8) / j3;
				occluder.anInt803 = ((occluder.anInt796 - eyeY) << 8) / j3;
				occluder.anInt804 = ((occluder.anInt797 - eyeY) << 8) / j3;
				A_OCCLUDER_ARRAY_476[anInt475++] = occluder;
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
					occluder.anInt798 = 3;
				} else {
					if (k3 >= -32) {
						continue;
					}
					occluder.anInt798 = 4;
					k3 = -k3;
				}
				occluder.anInt799 = ((occluder.anInt792 - eyeX) << 8) / k3;
				occluder.anInt800 = ((occluder.anInt793 - eyeX) << 8) / k3;
				occluder.anInt803 = ((occluder.anInt796 - eyeY) << 8) / k3;
				occluder.anInt804 = ((occluder.anInt797 - eyeY) << 8) / k3;
				A_OCCLUDER_ARRAY_476[anInt475++] = occluder;
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
							occluder.anInt798 = 5;
							occluder.anInt799 = ((occluder.anInt792 - eyeX) << 8) / j1;
							occluder.anInt800 = ((occluder.anInt793 - eyeX) << 8) / j1;
							occluder.anInt801 = ((occluder.anInt794 - eyeZ) << 8) / j1;
							occluder.anInt802 = ((occluder.anInt795 - eyeZ) << 8) / j1;
							A_OCCLUDER_ARRAY_476[anInt475++] = occluder;
						}
					}
				}
			}
		}
	}

	public boolean method320(int i, int j, int k) {
		int l = anIntArrayArrayArray445[i][j][k];
		if (l == -anInt448) {
			return false;
		}
		if (l == anInt448) {
			return true;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		if (method324(i1 + 1, anIntArrayArrayArray440[i][j][k], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k + 1], (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1], (j1 + 128) - 1)) {
			anIntArrayArrayArray445[i][j][k] = anInt448;
			return true;
		} else {
			anIntArrayArrayArray445[i][j][k] = -anInt448;
			return false;
		}
	}

	public boolean method321(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		int k1 = anIntArrayArrayArray440[i][j][k] - 1;
		int l1 = k1 - 120;
		int i2 = k1 - 230;
		int j2 = k1 - 238;
		if (l < 16) {
			if (l == 1) {
				if (i1 > eyeX) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method324(i1, i2, j1)) {
					return false;
				}
				return method324(i1, i2, j1 + 128);
			}
			if (l == 2) {
				if (j1 < eyeZ) {
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method324(i1, i2, j1 + 128)) {
					return false;
				}
				return method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 4) {
				if (i1 < eyeX) {
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method324(i1 + 128, i2, j1)) {
					return false;
				}
				return method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 8) {
				if (j1 > eyeZ) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
				}
				if (!method324(i1, i2, j1)) {
					return false;
				}
				return method324(i1 + 128, i2, j1);
			}
		}
		if (!method324(i1 + 64, j2, j1 + 64)) {
			return false;
		}
		if (l == 16) {
			return method324(i1, i2, j1 + 128);
		}
		if (l == 32) {
			return method324(i1 + 128, i2, j1 + 128);
		}
		if (l == 64) {
			return method324(i1 + 128, i2, j1);
		}
		if (l == 128) {
			return method324(i1, i2, j1);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	public boolean method322(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		return method324(i1 + 1, anIntArrayArrayArray440[i][j][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k + 1] - l, (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1] - l, (j1 + 128) - 1);
	}

	public boolean method323(int i, int j, int k, int l, int i1, int j1) {
		if ((j == k) && (l == i1)) {
			if (!method320(i, j, l)) {
				return false;
			}
			int k1 = j << 7;
			int i2 = l << 7;
			return method324(k1 + 1, anIntArrayArrayArray440[i][j][l] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][l] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][l + 1] - j1, (i2 + 128) - 1) && method324(k1 + 1, anIntArrayArrayArray440[i][j][l + 1] - j1, (i2 + 128) - 1);
		}
		for (int l1 = j; l1 <= k; l1++) {
			for (int j2 = l; j2 <= i1; j2++) {
				if (anIntArrayArrayArray445[i][l1][j2] == -anInt448) {
					return false;
				}
			}
		}
		int k2 = (j << 7) + 1;
		int l2 = (l << 7) + 2;
		int i3 = anIntArrayArrayArray440[i][j][l] - j1;
		if (!method324(k2, i3, l2)) {
			return false;
		}
		int j3 = (k << 7) - 1;
		if (!method324(j3, i3, l2)) {
			return false;
		}
		int k3 = (i1 << 7) - 1;
		if (!method324(k2, i3, k3)) {
			return false;
		}
		return method324(j3, i3, k3);
	}

	public boolean method324(int i, int j, int k) {
		for (int l = 0; l < anInt475; l++) {
			SceneOccluder occluder = A_OCCLUDER_ARRAY_476[l];
			if (occluder.anInt798 == 1) {
				int i1 = occluder.anInt792 - i;
				if (i1 > 0) {
					int j2 = occluder.anInt794 + ((occluder.anInt801 * i1) >> 8);
					int k3 = occluder.anInt795 + ((occluder.anInt802 * i1) >> 8);
					int l4 = occluder.anInt796 + ((occluder.anInt803 * i1) >> 8);
					int i6 = occluder.anInt797 + ((occluder.anInt804 * i1) >> 8);
					if ((k >= j2) && (k <= k3) && (j >= l4) && (j <= i6)) {
						return true;
					}
				}
			} else if (occluder.anInt798 == 2) {
				int j1 = i - occluder.anInt792;
				if (j1 > 0) {
					int k2 = occluder.anInt794 + ((occluder.anInt801 * j1) >> 8);
					int l3 = occluder.anInt795 + ((occluder.anInt802 * j1) >> 8);
					int i5 = occluder.anInt796 + ((occluder.anInt803 * j1) >> 8);
					int j6 = occluder.anInt797 + ((occluder.anInt804 * j1) >> 8);
					if ((k >= k2) && (k <= l3) && (j >= i5) && (j <= j6)) {
						return true;
					}
				}
			} else if (occluder.anInt798 == 3) {
				int k1 = occluder.anInt794 - k;
				if (k1 > 0) {
					int l2 = occluder.anInt792 + ((occluder.anInt799 * k1) >> 8);
					int i4 = occluder.anInt793 + ((occluder.anInt800 * k1) >> 8);
					int j5 = occluder.anInt796 + ((occluder.anInt803 * k1) >> 8);
					int k6 = occluder.anInt797 + ((occluder.anInt804 * k1) >> 8);
					if ((i >= l2) && (i <= i4) && (j >= j5) && (j <= k6)) {
						return true;
					}
				}
			} else if (occluder.anInt798 == 4) {
				int l1 = k - occluder.anInt794;
				if (l1 > 0) {
					int i3 = occluder.anInt792 + ((occluder.anInt799 * l1) >> 8);
					int j4 = occluder.anInt793 + ((occluder.anInt800 * l1) >> 8);
					int k5 = occluder.anInt796 + ((occluder.anInt803 * l1) >> 8);
					int l6 = occluder.anInt797 + ((occluder.anInt804 * l1) >> 8);
					if ((i >= i3) && (i <= j4) && (j >= k5) && (j <= l6)) {
						return true;
					}
				}
			} else if (occluder.anInt798 == 5) {
				int i2 = j - occluder.anInt796;
				if (i2 > 0) {
					int j3 = occluder.anInt792 + ((occluder.anInt799 * i2) >> 8);
					int k4 = occluder.anInt793 + ((occluder.anInt800 * i2) >> 8);
					int l5 = occluder.anInt794 + ((occluder.anInt801 * i2) >> 8);
					int i7 = occluder.anInt795 + ((occluder.anInt802 * i2) >> 8);
					if ((i >= j3) && (i <= k4) && (k >= l5) && (k <= i7)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
