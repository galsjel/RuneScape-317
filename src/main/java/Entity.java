// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Entity extends DoublyLinkedListNode {

	public VertexNormal[] aNormalArray1425;
	public int anInt1426 = 1000;

	public Entity() {
	}

	public void method443(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		Model model = method444();
		if (model != null) {
			anInt1426 = model.anInt1426;
			model.method443(i, j, k, l, i1, j1, k1, l1, i2);
		}
	}

	public Model method444() {
		return null;
	}

}
