// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LRUCache {

	public int anInt298;
	public int anInt299;
	public final DoublyLinkedListNode aNode_300 = new DoublyLinkedListNode();
	public final int anInt301;
	public int anInt302;
	public final Hashtable aHashtable_303 = new Hashtable(1024);
	public final DoublyLinkedList aDoublyLinkedList_304 = new DoublyLinkedList();

	public LRUCache(int i) {
		anInt301 = i;
		anInt302 = i;
	}

	public DoublyLinkedListNode method222(long l) {
		DoublyLinkedListNode node = (DoublyLinkedListNode) aHashtable_303.method148(l);
		if (node != null) {
			aDoublyLinkedList_304.method150(node);
			anInt299++;
		} else {
			anInt298++;
		}
		return node;
	}

	public void method223(DoublyLinkedListNode node, long l) {
		if (anInt302 == 0) {
			DoublyLinkedListNode node_1 = aDoublyLinkedList_304.method151();
			node_1.method329();
			node_1.method330();
			if (node_1 == aNode_300) {
				DoublyLinkedListNode node_2 = aDoublyLinkedList_304.method151();
				node_2.method329();
				node_2.method330();
			}
		} else {
			anInt302--;
		}
		aHashtable_303.method149(node, l);
		aDoublyLinkedList_304.method150(node);
	}

	public void method224() {
		do {
			DoublyLinkedListNode node = aDoublyLinkedList_304.method151();
			if (node != null) {
				node.method329();
				node.method330();
			} else {
				anInt302 = anInt301;
				return;
			}
		} while (true);
	}

}
