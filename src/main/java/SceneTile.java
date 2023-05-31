// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTile extends DoublyLinkedList.Node {

    public final int x;
    public final int z;
    /**
     * Which level to sample when testing against this tile for occlusion.
     */
    public final int occludeLevel;
    public final SceneDrawable[] drawables = new SceneDrawable[5];
    public final int[] drawable_span = new int[5];
    public int level;
    public SceneTileUnderlay underlay;
    public SceneTileOverlay overlay;
    public SceneWall wall;
    public SceneWallDecoration wall_decoration;
    public SceneGroundDecoration ground_decoration;
    public SceneItemStack item_stack;
    public int drawable_count;
    /**
     * When larger than 1x1 locs reside on a tile, we have to know which part of it might be on this tile to properly
     * cull it.
     * <p>
     * 0b0001 = x > loc.minSceneTileX
     * 0b0010 = z < loc.maxSceneTileZ
     * 0b0100 = x < loc.maxSceneTileX
     * 0b1000 = z > loc.minSceneTileZ
     */
    public int drawable_spans;
    /**
     * Used as a short circuit to prevent drawing tiles above the top level. Mostly applies to lowmem.
     *
     * @see Scene#draw(int, int, int, int, int, int)
     */
    public int draw_level;
    public boolean visible;
    public boolean update;
    public boolean containsLocs;
    public int checkLocSpans;
    public int blockLocSpans;
    public int inverseBlockLocSpans;
    public int backWallTypes;
    public SceneTile bridge;

    public SceneTile(int level, int x, int z) {
        this.occludeLevel = this.level = level;
        this.x = x;
        this.z = z;
    }

}
