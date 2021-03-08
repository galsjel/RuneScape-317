// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LinkedListNode {

	public long aLong548;
	public LinkedListNode aNode_549;
	public LinkedListNode aNode_550;

	public LinkedListNode() {
	}

	public void method329() {
		if (aNode_550 != null) {
			aNode_550.aNode_549 = aNode_549;
			aNode_549.aNode_550 = aNode_550;
			aNode_549 = null;
			aNode_550 = null;
		}
	}

}
