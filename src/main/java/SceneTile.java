// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTile extends DoublyLinkedList.Node {

	public final int x;
	public final int z;
	public final int level;
	public final SceneLoc[] locs = new SceneLoc[5];
	public final int[] locSpan = new int[5];
	public int dataLevel;
	public SceneTileUnderlay underlay;
	public SceneTileOverlay overlay;
	public SceneWall wall;
	public SceneWallDecoration wallDecoration;
	public SceneGroundDecoration groundDecoration;
	public SceneObjStack objStack;
	public int locCount;
	public int spans;
	/**
	 * Used as a short circuit to prevent drawing tiles above the top level. Mostly applies to lowmem.
	 * @see Scene#draw(int, int, int, int, int, int)
	 */
	public int drawLevel;
	public boolean drawable;
	public boolean update;
	public boolean containsLocs;
	public int anInt1325;
	public int anInt1326;
	public int anInt1327;
	public int anInt1328;
	public SceneTile bridge;

	public SceneTile(int level, int x, int z) {
		this.level = dataLevel = level;
		this.x = x;
		this.z = z;
	}

}
