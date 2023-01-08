// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneLocTemporary extends DoublyLinkedList.Node {

    public int id;
    public int rotation;
    public int kind;
    public int duration;
    public int level;
    public int classID;
    public int localX;
    public int localZ;
    public int previousLocID;
    public int previousRotation;
    public int previousKind;
    public int delay;

    public SceneLocTemporary() {
        duration = -1;
    }

}
