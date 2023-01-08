// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneOccluder {

    /**
     * A wall occluder which runs along the X axis.
     */
    public static final int TYPE_WALL_X = 1;
    /**
     * A wall occluder which runs along the Z axis.
     */
    public static final int TYPE_WALL_Z = 2;
    /**
     * A ground occluder which covers the XZ plane.
     */
    public static final int TYPE_GROUND = 4;

    public int minTileX;
    public int maxTileX;
    public int minTileZ;
    public int maxTileZ;
    public int type;
    public int minX;
    public int maxX;
    public int minZ;
    public int maxZ;
    public int minY;
    public int maxY;
    public int mode;
    public int minDeltaX;
    public int maxDeltaX;
    public int minDeltaZ;
    public int maxDeltaZ;
    public int minDeltaY;
    public int maxDeltaY;

    public SceneOccluder() {
    }

}
