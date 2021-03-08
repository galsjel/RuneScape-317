// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class DoublyLinkedListNode extends LinkedListNode {

	public DoublyLinkedListNode aNode_1303;
	public DoublyLinkedListNode aNode_1304;

	public DoublyLinkedListNode() {
	}

	public void method330() {
		if (aNode_1304 != null) {
			aNode_1304.aNode_1303 = aNode_1303;
			aNode_1303.aNode_1304 = aNode_1304;
			aNode_1303 = null;
			aNode_1304 = null;
		}
	}

}
