// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class LRUCache {

	public int anInt298;
	public int anInt299;
	public final Class30_Sub2 aClass30_Sub2_300 = new Class30_Sub2();
	public final int anInt301;
	public int anInt302;
	public final Hashtable aHashtable_303 = new Hashtable(1024);
	public final LinkedList aLinkedList_304 = new LinkedList();

	public LRUCache(int i) {
		anInt301 = i;
		anInt302 = i;
	}

	public Class30_Sub2 method222(long l) {
		Class30_Sub2 class30_sub2 = (Class30_Sub2) aHashtable_303.method148(l);
		if (class30_sub2 != null) {
			aLinkedList_304.method150(class30_sub2);
			anInt299++;
		} else {
			anInt298++;
		}
		return class30_sub2;
	}

	public void method223(Class30_Sub2 class30_sub2, long l) {
		if (anInt302 == 0) {
			Class30_Sub2 class30_sub2_1 = aLinkedList_304.method151();
			class30_sub2_1.method329();
			class30_sub2_1.method330();
			if (class30_sub2_1 == aClass30_Sub2_300) {
				Class30_Sub2 class30_sub2_2 = aLinkedList_304.method151();
				class30_sub2_2.method329();
				class30_sub2_2.method330();
			}
		} else {
			anInt302--;
		}
		aHashtable_303.method149(class30_sub2, l);
		aLinkedList_304.method150(class30_sub2);
	}

	public void method224() {
		do {
			Class30_Sub2 class30_sub2 = aLinkedList_304.method151();
			if (class30_sub2 != null) {
				class30_sub2.method329();
				class30_sub2.method330();
			} else {
				anInt302 = anInt301;
				return;
			}
		} while (true);
	}

}
