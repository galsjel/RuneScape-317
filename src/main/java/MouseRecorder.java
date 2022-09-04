// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class MouseRecorder implements Runnable {

	public final Object lock = new Object();
	public final Game aGame805;
	public final int[] y = new int[500];
	public final int[] x = new int[500];
	public boolean aBoolean808 = true;
	public int length;

	public MouseRecorder(Game game1) {
		aGame805 = game1;
	}

	@Override
	public void run() {
		while (aBoolean808) {
			synchronized (lock) {
				if (length < 500) {
					x[length] = aGame805.mouseX;
					y[length] = aGame805.mouseY;
					length++;
				}
			}
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
	}

}
