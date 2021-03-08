// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Hashtable {

	public final int anInt39;
	public final LinkedListNode[] aNodeArray40;

	public Hashtable(int j) {
		anInt39 = j;
		aNodeArray40 = new LinkedListNode[j];
		for (int k = 0; k < j; k++) {
			LinkedListNode node = aNodeArray40[k] = new LinkedListNode();
			node.aNode_549 = node;
			node.aNode_550 = node;
		}
	}

	public LinkedListNode method148(long l) {
		LinkedListNode node = aNodeArray40[(int) (l & (long) (anInt39 - 1))];
		for (LinkedListNode node_1 = node.aNode_549; node_1 != node; node_1 = node_1.aNode_549) {
			if (node_1.aLong548 == l) {
				return node_1;
			}
		}
		return null;
	}

	public void method149(LinkedListNode node, long l) {
		if (node.aNode_550 != null) {
			node.method329();
		}
		LinkedListNode node_1 = aNodeArray40[(int) (l & (long) (anInt39 - 1))];
		node.aNode_550 = node_1.aNode_550;
		node.aNode_549 = node_1;
		node.aNode_550.aNode_549 = node;
		node.aNode_549.aNode_550 = node;
		node.aLong548 = l;
	}

}
