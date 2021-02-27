// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class2 {

	public final Class30_Sub2 aClass30_Sub2_43;
	public Class30_Sub2 aClass30_Sub2_44;

	public Class2() {
		aClass30_Sub2_43 = new Class30_Sub2();
		aClass30_Sub2_43.aClass30_Sub2_1303 = aClass30_Sub2_43;
		aClass30_Sub2_43.aClass30_Sub2_1304 = aClass30_Sub2_43;
	}

	public void method150(Class30_Sub2 class30_sub2) {
		if (class30_sub2.aClass30_Sub2_1304 != null) {
			class30_sub2.method330();
		}
		class30_sub2.aClass30_Sub2_1304 = aClass30_Sub2_43.aClass30_Sub2_1304;
		class30_sub2.aClass30_Sub2_1303 = aClass30_Sub2_43;
		class30_sub2.aClass30_Sub2_1304.aClass30_Sub2_1303 = class30_sub2;
		class30_sub2.aClass30_Sub2_1303.aClass30_Sub2_1304 = class30_sub2;
	}

	public Class30_Sub2 method151() {
		Class30_Sub2 class30_sub2 = aClass30_Sub2_43.aClass30_Sub2_1303;
		if (class30_sub2 == aClass30_Sub2_43) {
			return null;
		} else {
			class30_sub2.method330();
			return class30_sub2;
		}
	}

	public Class30_Sub2 method152() {
		Class30_Sub2 class30_sub2 = aClass30_Sub2_43.aClass30_Sub2_1303;
		if (class30_sub2 == aClass30_Sub2_43) {
			aClass30_Sub2_44 = null;
			return null;
		} else {
			aClass30_Sub2_44 = class30_sub2.aClass30_Sub2_1303;
			return class30_sub2;
		}
	}

	public Class30_Sub2 method153() {
		Class30_Sub2 class30_sub2 = aClass30_Sub2_44;
		if (class30_sub2 == aClass30_Sub2_43) {
			aClass30_Sub2_44 = null;
			return null;
		} else {
			aClass30_Sub2_44 = class30_sub2.aClass30_Sub2_1303;
			return class30_sub2;
		}
	}

	public int method154() {
		int i = 0;
		for (Class30_Sub2 class30_sub2 = aClass30_Sub2_43.aClass30_Sub2_1303; class30_sub2 != aClass30_Sub2_43; class30_sub2 = class30_sub2.aClass30_Sub2_1303) {
			i++;
		}

		return i;
	}

}
