// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneLoc {

    public int level;
    public int y;
    public int x;
    public int z;
    public Entity entity;
    public int yaw;
    public int minSceneTileX;
    public int maxSceneTileX;
    public int minSceneTileZ;
    public int maxSceneTileZ;
    public int distance;
    public int cycle;
    public int bitset;
    public byte info;

    public boolean drawn() {
        return cycle == Scene.cycle;
    }

    public SceneLoc() {
    }

}
