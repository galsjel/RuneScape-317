// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneDrawable {

    public int level;
    public int y;
    public int x;
    public int z;
    public Drawable drawable;
    public int yaw;
    public int min_tile_x;
    public int max_tile_x;
    public int min_tile_z;
    public int max_tile_z;
    public int distance;
    public int draw_cycle;
    public int bitset;
    public byte info;

    public boolean drawn() {
        return draw_cycle == Scene.cycle;
    }

    public SceneDrawable() {
    }

}
