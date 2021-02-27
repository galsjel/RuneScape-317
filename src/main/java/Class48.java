// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class48 implements Runnable {

	public final Object lock = new Object();
	public final client aClient805;
	public final int[] anIntArray807 = new int[500];
	public boolean aBoolean808 = true;
	public final int[] anIntArray809 = new int[500];
	public int anInt810;

	public Class48(client client1) {
		aClient805 = client1;
	}

	public void run() {
		while (aBoolean808) {
			synchronized (lock) {
				if (anInt810 < 500) {
					anIntArray809[anInt810] = aClient805.anInt20;
					anIntArray807[anInt810] = aClient805.anInt21;
					anInt810++;
				}
			}
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
	}

}
