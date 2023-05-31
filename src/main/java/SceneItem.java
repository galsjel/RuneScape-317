// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneItem extends Drawable {

    public int id;
    public int count;

    public SceneItem() {
    }

    @Override
    public Model getModel() {
        Item type = Item.get(id);
        return type.getLitModel(count);
    }

}
