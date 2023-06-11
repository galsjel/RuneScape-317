// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTemporaryObject extends DoublyLinkedList.Node {

    public int rotation;
    public int id;
    public int kind;
    public int duration;
    public int level;
    public int category;
    public int tile_x;
    public int tile_z;
    public int previousLocID;
    public int previousRotation;
    public int previousKind;
    public int delay;

    public SceneTemporaryObject() {
        duration = -1;
    }

}
