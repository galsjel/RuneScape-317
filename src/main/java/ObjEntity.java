// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ObjEntity extends Drawable {

    public int id;
    public int count;

    public ObjEntity() {
    }

    @Override
    public Model getModel() {
        ObjType type = ObjType.get(id);
        return type.getLitModel(count);
    }

}
