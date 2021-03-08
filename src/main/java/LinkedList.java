// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LinkedList {

	public final LinkedListNode aNode_346;
	public LinkedListNode aNode_347;

	public LinkedList() {
		aNode_346 = new LinkedListNode();
		aNode_346.aNode_549 = aNode_346;
		aNode_346.aNode_550 = aNode_346;
	}

	public void method249(LinkedListNode node) {
		if (node.aNode_550 != null) {
			node.method329();
		}
		node.aNode_550 = aNode_346.aNode_550;
		node.aNode_549 = aNode_346;
		node.aNode_550.aNode_549 = node;
		node.aNode_549.aNode_550 = node;
	}

	public void method250(LinkedListNode node) {
		if (node.aNode_550 != null) {
			node.method329();
		}
		node.aNode_550 = aNode_346;
		node.aNode_549 = aNode_346.aNode_549;
		node.aNode_550.aNode_549 = node;
		node.aNode_549.aNode_550 = node;
	}

	public LinkedListNode method251() {
		LinkedListNode node = aNode_346.aNode_549;
		if (node == aNode_346) {
			return null;
		} else {
			node.method329();
			return node;
		}
	}

	public LinkedListNode method252() {
		LinkedListNode node = aNode_346.aNode_549;
		if (node == aNode_346) {
			aNode_347 = null;
			return null;
		} else {
			aNode_347 = node.aNode_549;
			return node;
		}
	}

	public LinkedListNode method253() {
		LinkedListNode node = aNode_346.aNode_550;
		if (node == aNode_346) {
			aNode_347 = null;
			return null;
		} else {
			aNode_347 = node.aNode_550;
			return node;
		}
	}

	public LinkedListNode method254() {
		LinkedListNode node = aNode_347;
		if (node == aNode_346) {
			aNode_347 = null;
			return null;
		} else {
			aNode_347 = node.aNode_549;
			return node;
		}
	}

	public LinkedListNode method255() {
		LinkedListNode node = aNode_347;
		if (node == aNode_346) {
			aNode_347 = null;
			return null;
		}
		aNode_347 = node.aNode_550;
		return node;
	}

	public void method256() {
		if (aNode_346.aNode_549 == aNode_346) {
			return;
		}
		do {
			LinkedListNode node = aNode_346.aNode_549;
			if (node == aNode_346) {
				return;
			}
			node.method329();
		} while (true);
	}

}
