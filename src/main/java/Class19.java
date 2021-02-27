// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class19 {

	public final Class30 aClass30_346;
	public Class30 aClass30_347;

	public Class19() {
		aClass30_346 = new Class30();
		aClass30_346.aClass30_549 = aClass30_346;
		aClass30_346.aClass30_550 = aClass30_346;
	}

	public void method249(Class30 class30) {
		if (class30.aClass30_550 != null) {
			class30.method329();
		}
		class30.aClass30_550 = aClass30_346.aClass30_550;
		class30.aClass30_549 = aClass30_346;
		class30.aClass30_550.aClass30_549 = class30;
		class30.aClass30_549.aClass30_550 = class30;
	}

	public void method250(Class30 class30) {
		if (class30.aClass30_550 != null) {
			class30.method329();
		}
		class30.aClass30_550 = aClass30_346;
		class30.aClass30_549 = aClass30_346.aClass30_549;
		class30.aClass30_550.aClass30_549 = class30;
		class30.aClass30_549.aClass30_550 = class30;
	}

	public Class30 method251() {
		Class30 class30 = aClass30_346.aClass30_549;
		if (class30 == aClass30_346) {
			return null;
		} else {
			class30.method329();
			return class30;
		}
	}

	public Class30 method252() {
		Class30 class30 = aClass30_346.aClass30_549;
		if (class30 == aClass30_346) {
			aClass30_347 = null;
			return null;
		} else {
			aClass30_347 = class30.aClass30_549;
			return class30;
		}
	}

	public Class30 method253() {
		Class30 class30 = aClass30_346.aClass30_550;
		if (class30 == aClass30_346) {
			aClass30_347 = null;
			return null;
		} else {
			aClass30_347 = class30.aClass30_550;
			return class30;
		}
	}

	public Class30 method254() {
		Class30 class30 = aClass30_347;
		if (class30 == aClass30_346) {
			aClass30_347 = null;
			return null;
		} else {
			aClass30_347 = class30.aClass30_549;
			return class30;
		}
	}

	public Class30 method255() {
		Class30 class30 = aClass30_347;
		if (class30 == aClass30_346) {
			aClass30_347 = null;
			return null;
		}
		aClass30_347 = class30.aClass30_550;
		return class30;
	}

	public void method256() {
		if (aClass30_346.aClass30_549 == aClass30_346) {
			return;
		}
		do {
			Class30 class30 = aClass30_346.aClass30_549;
			if (class30 == aClass30_346) {
				return;
			}
			class30.method329();
		} while (true);
	}

}
