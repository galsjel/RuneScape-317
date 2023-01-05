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
    public int targetID = -1;
    public int seqTrigger;
    public int turnSpeed = 32;
    public int seqRunID = -1;
    public String chat;
    public int height = 200;
    public int dstYaw;
    public int seqStandID = -1;
    public int seqTurnID = -1;
    public int chatColor;
    public int secondarySeqID = -1;
    public int secondarySeqFrame;
    public int secondarySeqCycle;
    public int spotanimID = -1;
    public int spotanimFrame;
    public int spotanimCycle;
    public int spotanimLastCycle;
    public int spotanimOffset;
    public int pathLength;
    public int primarySeqID = -1;
    public int primarySeqFrame;
    public int primarySeqCycle;
    public int primarySeqDelay;
    public int primarySeqLoop;
    public int chatStyle;
    public int combatCycle = -1000;
    public int health;
    public int totalHealth;
    public int chatTimer = 100;
    public int cycle;
    public int targetTileX;
    public int targetTileZ;
    public int size = 1;
    /**
     * Passed to {@link Scene#addTemporary(Entity, int, int, int, int, int, int, boolean, int)} to provide an additional
     * tile worth of draw padding ahead of this entity for things like animations that extend past the normal boundary.
     */
    public boolean needsForwardDrawPadding = false;
    public int seqPathLength;
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
    public int seqWalkID = -1;
    public int seqTurnAroundID = -1;
    public int seqTurnLeftID = -1;
    public int seqTurnRightID = -1;

    public PathingEntity() {
    }

    public void move(int x, int z, boolean teleport) {
        if ((primarySeqID != -1) && (SeqType.instances[primarySeqID].idleStyle == 1)) {
            primarySeqID = -1;
        }
        if (!teleport) {
            int dx = x - pathTileX[0];
            int dz = z - pathTileZ[0];

            if ((dx >= -8) && (dx <= 8) && (dz >= -8) && (dz <= 8)) {
                if (pathLength < 9) {
                    pathLength++;
                }

                for (int i = pathLength; i > 0; i--) {
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
        pathLength = 0;
        seqPathLength = 0;
        seqTrigger = 0;
        pathTileX[0] = x;
        pathTileZ[0] = z;
        this.x = (pathTileX[0] * 128) + (size * 64);
        this.z = (pathTileZ[0] * 128) + (size * 64);
    }

    public void resetPath() {
        pathLength = 0;
        seqPathLength = 0;
    }

    public void hit(int type, int damage) {
        for (int i = 0; i < 4; i++) {
            if (damageCycle[i] <= Game.loopCycle) {
                this.damage[i] = damage;
                damageType[i] = type;
                damageCycle[i] = Game.loopCycle + 70;
                return;
            }
        }
    }

    public void step(boolean running, int direction) {
        int nextX = pathTileX[0];
        int nextZ = pathTileZ[0];

        if (direction == 0) {
            nextX--;
            nextZ++;
        } else if (direction == 1) {
            nextZ++;
        } else if (direction == 2) {
            nextX++;
            nextZ++;
        } else if (direction == 3) {
            nextX--;
        } else if (direction == 4) {
            nextX++;
        } else if (direction == 5) {
            nextX--;
            nextZ--;
        } else if (direction == 6) {
            nextZ--;
        } else if (direction == 7) {
            nextX++;
            nextZ--;
        }

        if ((primarySeqID != -1) && (SeqType.instances[primarySeqID].idleStyle == 1)) {
            primarySeqID = -1;
        }

        if (pathLength < 9) {
            pathLength++;
        }

        for (int i = pathLength; i > 0; i--) {
            pathTileX[i] = pathTileX[i - 1];
            pathTileZ[i] = pathTileZ[i - 1];
            pathRunning[i] = pathRunning[i - 1];
        }

        pathTileX[0] = nextX;
        pathTileZ[0] = nextZ;
        pathRunning[0] = running;
    }

    public boolean isVisible() {
        return false;
    }

}
