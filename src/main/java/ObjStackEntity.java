// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ObjStackEntity extends Entity {

	public int anInt1558;
	public int anInt1559;

	public ObjStackEntity() {
	}

	@Override
	public Model getModel() {
		ObjType type = ObjType.method198(anInt1558);
		return type.method201(anInt1559);
	}

}
