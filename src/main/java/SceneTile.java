// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTile extends DoublyLinkedList.Node {

	public final int anInt1308;
	public final int anInt1309;
	public final int anInt1310;
	public final SceneLoc[] locs = new SceneLoc[5];
	public final int[] locFlags = new int[5];
	public int anInt1307;
	public SceneTileUnderlay underlay;
	public SceneTileOverlay overlay;
	public SceneWall wall;
	public SceneWallDecoration wallDecoration;
	public SceneGroundDecoration groundDecoration;
	public SceneObjStack objStack;
	public int locCount;
	public int flags;
	public int drawPlane;
	public boolean aBoolean1322;
	public boolean aBoolean1323;
	public boolean aBoolean1324;
	public int anInt1325;
	public int anInt1326;
	public int anInt1327;
	public int anInt1328;
	public SceneTile bridge;

	public SceneTile(int i, int j, int k) {
		anInt1310 = anInt1307 = i;
		anInt1308 = j;
		anInt1309 = k;
	}

}
