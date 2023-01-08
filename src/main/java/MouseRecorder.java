// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class MouseRecorder implements Runnable {

    public final Object lock = new Object();
    public final Game game;
    public final int[] y = new int[500];
    public final int[] x = new int[500];
    public boolean active = true;
    public int length;

    public MouseRecorder(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (active) {
            synchronized (lock) {
                if (length < 500) {
                    x[length] = game.mouseX;
                    y[length] = game.mouseY;
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
