// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class MouseRecorder implements Runnable {

	public final Object lock = new Object();
	public final Game aGame805;
	public final int[] anIntArray807 = new int[500];
	public final int[] anIntArray809 = new int[500];
	public boolean aBoolean808 = true;
	public int anInt810;

	public MouseRecorder(Game game1) {
		aGame805 = game1;
	}

	@Override
	public void run() {
		while (aBoolean808) {
			synchronized (lock) {
				if (anInt810 < 500) {
					anIntArray809[anInt810] = aGame805.mouseX;
					anIntArray807[anInt810] = aGame805.mouseY;
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
