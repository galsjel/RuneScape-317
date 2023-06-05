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

    public int min_tile_x;
    public int max_tile_x;
    public int min_tile_z;
    public int max_tile_z;
    public int type;
    public int min_x;
    public int max_x;
    public int min_z;
    public int max_z;
    public int min_y;
    public int max_y;
    public int mode;
    public int min_delta_x;
    public int max_delta_x;
    public int min_delta_z;
    public int max_delta_z;
    public int min_delta_y;
    public int max_delta_y;

    public SceneOccluder() {
    }

}
