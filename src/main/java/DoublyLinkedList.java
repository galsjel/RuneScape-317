// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class DoublyLinkedList {

	public final DoublyLinkedListNode aNode_43;
	public DoublyLinkedListNode aNode_44;

	public DoublyLinkedList() {
		aNode_43 = new DoublyLinkedListNode();
		aNode_43.aNode_1303 = aNode_43;
		aNode_43.aNode_1304 = aNode_43;
	}

	public void method150(DoublyLinkedListNode node) {
		if (node.aNode_1304 != null) {
			node.method330();
		}
		node.aNode_1304 = aNode_43.aNode_1304;
		node.aNode_1303 = aNode_43;
		node.aNode_1304.aNode_1303 = node;
		node.aNode_1303.aNode_1304 = node;
	}

	public DoublyLinkedListNode method151() {
		DoublyLinkedListNode node = aNode_43.aNode_1303;
		if (node == aNode_43) {
			return null;
		} else {
			node.method330();
			return node;
		}
	}

	public DoublyLinkedListNode method152() {
		DoublyLinkedListNode node = aNode_43.aNode_1303;
		if (node == aNode_43) {
			aNode_44 = null;
			return null;
		} else {
			aNode_44 = node.aNode_1303;
			return node;
		}
	}

	public DoublyLinkedListNode method153() {
		DoublyLinkedListNode node = aNode_44;
		if (node == aNode_43) {
			aNode_44 = null;
			return null;
		} else {
			aNode_44 = node.aNode_1303;
			return node;
		}
	}

	public int method154() {
		int i = 0;
		for (DoublyLinkedListNode node = aNode_43.aNode_1303; node != aNode_43; node = node.aNode_1303) {
			i++;
		}
		return i;
	}

}
