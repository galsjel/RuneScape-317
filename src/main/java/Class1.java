// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class1 {

	public final int anInt39;
	public final Class30[] aClass30Array40;

	public Class1(int j) {
		anInt39 = j;
		aClass30Array40 = new Class30[j];
		for (int k = 0; k < j; k++) {
			Class30 class30 = aClass30Array40[k] = new Class30();
			class30.aClass30_549 = class30;
			class30.aClass30_550 = class30;
		}
	}

	public Class30 method148(long l) {
		Class30 class30 = aClass30Array40[(int) (l & (long) (anInt39 - 1))];
		for (Class30 class30_1 = class30.aClass30_549; class30_1 != class30; class30_1 = class30_1.aClass30_549) {
			if (class30_1.aLong548 == l) {
				return class30_1;
			}
		}
		return null;
	}

	public void method149(Class30 class30, long l) {
		if (class30.aClass30_550 != null) {
			class30.method329();
		}
		Class30 class30_1 = aClass30Array40[(int) (l & (long) (anInt39 - 1))];
		class30.aClass30_550 = class30_1.aClass30_550;
		class30.aClass30_549 = class30_1;
		class30.aClass30_550.aClass30_549 = class30;
		class30.aClass30_549.aClass30_550 = class30;
		class30.aLong548 = l;
	}

}
