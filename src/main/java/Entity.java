// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Entity extends DoublyLinkedListNode {

	public VertexNormal[] vertexNormal;
	public int minY = 1000;

	public Entity() {
	}

	public void draw(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		Model model = method444();
		if (model != null) {
			minY = model.minY;
			model.draw(i, j, k, l, i1, j1, k1, l1, i2);
		}
	}

	public Model method444() {
		return null;
	}

}
