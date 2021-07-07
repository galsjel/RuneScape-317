// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class PathingEntity extends Entity {

	public final int[] pathTileX = new int[10];
	public final int[] pathTileZ = new int[10];
	public final int[] damage = new int[4];
	public final int[] damageType = new int[4];
	public final int[] damageCycle = new int[4];
	public final boolean[] pathRunning = new boolean[10];
	public int index = -1;
	public int anInt1503;
	public int turnSpeed = 32;
	public int seqRun = -1;
	public String chat;
	public int height = 200;
	public int dstYaw;
	public int seqStand = -1;
	public int seqTurn = -1;
	public int chatColor;
	public int seqCurrent = -1;
	public int seqFrame;
	public int seqCycle;
	public int spotanim = -1;
	public int spotanimFrame;
	public int spotanimCycle;
	public int anInt1523;
	public int anInt1524;
	public int pathRemaining;
	public int anInt1526 = -1;
	public int anInt1527;
	public int anInt1528;
	public int anInt1529;
	public int anInt1530;
	public int chatStyle;
	public int combatCycle = -1000;
	public int health;
	public int totalHealth;
	public int chatTimer = 100;
	public int anInt1537;
	public int faceTileX;
	public int faceTileZ;
	public int size = 1;
	public boolean aBoolean1541 = false;
	public int anInt1542;
	public int forceMoveStartSceneTileX;
	public int forceMoveEndSceneTileX;
	public int forceMoveStartSceneTileZ;
	public int forceMoveEndSceneTileZ;
	public int forceMoveEndCycle;
	public int forceMoveStartCycle;
	public int forceMoveFaceDirection;
	public int x;
	public int z;
	public int yaw;
	public int seqWalk = -1;
	public int seqTurnAround = -1;
	public int seqTurnLeft = -1;
	public int seqTurnRight = -1;

	public PathingEntity() {
	}

	public void move(int x, int z, boolean teleport) {
		if ((anInt1526 != -1) && (SeqType.instances[anInt1526].anInt364 == 1)) {
			anInt1526 = -1;
		}
		if (!teleport) {
			int dx = x - pathTileX[0];
			int dz = z - pathTileZ[0];

			if ((dx >= -8) && (dx <= 8) && (dz >= -8) && (dz <= 8)) {
				if (pathRemaining < 9) {
					pathRemaining++;
				}

				for (int i = pathRemaining; i > 0; i--) {
					pathTileX[i] = pathTileX[i - 1];
					pathTileZ[i] = pathTileZ[i - 1];
					pathRunning[i] = pathRunning[i - 1];
				}

				pathTileX[0] = x;
				pathTileZ[0] = z;
				pathRunning[0] = false;
				return;
			}
		}
		pathRemaining = 0;
		anInt1542 = 0;
		anInt1503 = 0;
		pathTileX[0] = x;
		pathTileZ[0] = z;
		this.x = (pathTileX[0] * 128) + (size * 64);
		this.z = (pathTileZ[0] * 128) + (size * 64);
	}

	public void method446() {
		pathRemaining = 0;
		anInt1542 = 0;
	}

	public void method447(int j, int k, int l) {
		for (int i1 = 0; i1 < 4; i1++) {
			if (damageCycle[i1] <= l) {
				damage[i1] = k;
				damageType[i1] = j;
				damageCycle[i1] = l + 70;
				return;
			}
		}
	}

	public void method448(boolean flag, int i) {
		int j = pathTileX[0];
		int k = pathTileZ[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1) {
			k++;
		}
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3) {
			j--;
		}
		if (i == 4) {
			j++;
		}
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6) {
			k--;
		}
		if (i == 7) {
			j++;
			k--;
		}
		if ((anInt1526 != -1) && (SeqType.instances[anInt1526].anInt364 == 1)) {
			anInt1526 = -1;
		}
		if (pathRemaining < 9) {
			pathRemaining++;
		}
		for (int l = pathRemaining; l > 0; l--) {
			pathTileX[l] = pathTileX[l - 1];
			pathTileZ[l] = pathTileZ[l - 1];
			pathRunning[l] = pathRunning[l - 1];
		}
		pathTileX[0] = j;
		pathTileZ[0] = k;
		pathRunning[0] = flag;
	}

	public boolean isVisible() {
		return false;
	}

}
