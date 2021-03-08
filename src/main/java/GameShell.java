// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameShell extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

	public final long[] aLongArray7 = new long[10];
	public final int[] anIntArray30 = new int[128];
	public final int[] anIntArray31 = new int[128];
	public int anInt4;
	public int anInt5 = 20;
	public int anInt6 = 1;
	public int anInt8;
	public boolean aBoolean9 = false;
	public int anInt10;
	public int anInt11;
	public Graphics aGraphics12;
	public GameFrame aGameFrame__15;
	public boolean aBoolean16 = true;
	public boolean aBoolean17 = true;
	public int anInt18;
	public int anInt19;
	public int anInt20;
	public int anInt21;
	public int anInt22;
	public int anInt23;
	public int anInt24;
	public long aLong25;
	public int anInt26;
	public int anInt27;
	public int anInt28;
	public long aLong29;
	public int anInt32;
	public int anInt33;

	public GameShell() {
	}

	public void method1(int i, int j) {
		anInt10 = j;
		anInt11 = i;
		aGameFrame__15 = new GameFrame(this, anInt10, anInt11);
		aGraphics12 = method11().getGraphics();
		method12(this, 1);
	}

	public void method2(int i, int j) {
		anInt10 = j;
		anInt11 = i;
		aGraphics12 = method11().getGraphics();
		method12(this, 1);
	}

	public void run() {
		method11().addMouseListener(this);
		method11().addMouseMotionListener(this);
		method11().addKeyListener(this);
		method11().addFocusListener(this);
		if (aGameFrame__15 != null) {
			aGameFrame__15.addWindowListener(this);
		}
		method13(0, "Loading...");
		method6();
		int i = 0;
		int j = 256;
		int k = 1;
		int i1 = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < 10; k1++) {
			aLongArray7[k1] = System.currentTimeMillis();
		}
		while (anInt4 >= 0) {
			if (anInt4 > 0) {
				anInt4--;
				if (anInt4 == 0) {
					method3();
					return;
				}
			}
			int i2 = j;
			int j2 = k;
			j = 300;
			k = 1;
			long l1 = System.currentTimeMillis();
			if (aLongArray7[i] == 0L) {
				j = i2;
				k = j2;
			} else if (l1 > aLongArray7[i]) {
				j = (int) ((long) (2560 * anInt5) / (l1 - aLongArray7[i]));
			}
			if (j < 25) {
				j = 25;
			}
			if (j > 256) {
				j = 256;
				k = (int) ((long) anInt5 - (l1 - aLongArray7[i]) / 10L);
			}
			if (k > anInt5) {
				k = anInt5;
			}
			aLongArray7[i] = l1;
			i = (i + 1) % 10;
			if (k > 1) {
				for (int k2 = 0; k2 < 10; k2++) {
					if (aLongArray7[k2] != 0L) {
						aLongArray7[k2] += k;
					}
				}
			}
			if (k < anInt6) {
				k = anInt6;
			}
			try {
				Thread.sleep(k);
			} catch (InterruptedException _ex) {
				j1++;
			}
			for (; i1 < 256; i1 += j) {
				anInt26 = anInt22;
				anInt27 = anInt23;
				anInt28 = anInt24;
				aLong29 = aLong25;
				anInt22 = 0;
				method7();
				anInt32 = anInt33;
			}
			i1 &= 0xff;
			if (anInt5 > 0) {
				anInt8 = (1000 * j) / (anInt5 * 256);
			}
			method9();
			if (aBoolean9) {
				System.out.println("ntime:" + l1);
				for (int l2 = 0; l2 < 10; l2++) {
					int i3 = ((i - l2 - 1) + 20) % 10;
					System.out.println("otim" + i3 + ":" + aLongArray7[i3]);
				}
				System.out.println("fps:" + anInt8 + " ratio:" + j + " count:" + i1);
				System.out.println("del:" + k + " deltime:" + anInt5 + " mindel:" + anInt6);
				System.out.println("intex:" + j1 + " opos:" + i);
				aBoolean9 = false;
				j1 = 0;
			}
		}
		if (anInt4 == -1) {
			method3();
		}
	}

	public void method3() {
		anInt4 = -2;
		method8();
		if (aGameFrame__15 != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception ignored) {
			}
			try {
				System.exit(0);
			} catch (Throwable ignored) {
			}
		}
	}

	public void method4(int i) {
		anInt5 = 1000 / i;
	}

	@Override
	public void start() {
		if (anInt4 >= 0) {
			anInt4 = 0;
		}
	}

	@Override
	public void stop() {
		if (anInt4 >= 0) {
			anInt4 = 4000 / anInt5;
		}
	}

	@Override
	public void destroy() {
		anInt4 = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception ignored) {
		}
		if (anInt4 == -1) {
			method3();
		}
	}

	@Override
	public void update(Graphics g) {
		if (aGraphics12 == null) {
			aGraphics12 = g;
		}
		aBoolean16 = true;
		method10();
	}

	@Override
	public void paint(Graphics g) {
		if (aGraphics12 == null) {
			aGraphics12 = g;
		}
		aBoolean16 = true;
		method10();
	}

	public void mousePressed(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aGameFrame__15 != null) {
			i -= 4;
			j -= 22;
		}
		anInt18 = 0;
		anInt23 = i;
		anInt24 = j;
		aLong25 = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			anInt22 = 2;
			anInt19 = 2;
		} else {
			anInt22 = 1;
			anInt19 = 1;
		}
	}

	public void mouseReleased(MouseEvent mouseevent) {
		anInt18 = 0;
		anInt19 = 0;
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
		anInt18 = 0;
		anInt20 = -1;
		anInt21 = -1;
	}

	public void mouseDragged(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aGameFrame__15 != null) {
			i -= 4;
			j -= 22;
		}
		anInt18 = 0;
		anInt20 = i;
		anInt21 = j;
	}

	public void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (aGameFrame__15 != null) {
			i -= 4;
			j -= 22;
		}
		anInt18 = 0;
		anInt20 = i;
		anInt21 = j;
	}

	public void keyPressed(KeyEvent keyevent) {
		anInt18 = 0;
		int i = keyevent.getKeyCode();
		int j = keyevent.getKeyChar();
		if (j < 30) {
			j = 0;
		}
		if (i == 37) {
			j = 1;
		}
		if (i == 39) {
			j = 2;
		}
		if (i == 38) {
			j = 3;
		}
		if (i == 40) {
			j = 4;
		}
		if (i == 17) {
			j = 5;
		}
		if (i == 8) {
			j = 8;
		}
		if (i == 127) {
			j = 8;
		}
		if (i == 9) {
			j = 9;
		}
		if (i == 10) {
			j = 10;
		}
		if (i >= 112 && i <= 123) {
			j = (1008 + i) - 112;
		}
		if (i == 36) {
			j = 1000;
		}
		if (i == 35) {
			j = 1001;
		}
		if (i == 33) {
			j = 1002;
		}
		if (i == 34) {
			j = 1003;
		}
		if (j > 0 && j < 128) {
			anIntArray30[j] = 1;
		}
		if (j > 4) {
			anIntArray31[anInt33] = j;
			anInt33 = anInt33 + 1 & 0x7f;
		}
	}

	public void keyReleased(KeyEvent keyevent) {
		anInt18 = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036') {
			c = '\0';
		}
		if (i == 37) {
			c = '\001';
		}
		if (i == 39) {
			c = '\002';
		}
		if (i == 38) {
			c = '\003';
		}
		if (i == 40) {
			c = '\004';
		}
		if (i == 17) {
			c = '\005';
		}
		if (i == 8) {
			c = '\b';
		}
		if (i == 127) {
			c = '\b';
		}
		if (i == 9) {
			c = '\t';
		}
		if (i == 10) {
			c = '\n';
		}
		if (c > 0 && c < '\200') {
			anIntArray30[c] = 0;
		}
	}

	public void keyTyped(KeyEvent keyevent) {
	}

	public int method5() {
		int k = -1;
		if (anInt33 != anInt32) {
			k = anIntArray31[anInt32];
			anInt32 = anInt32 + 1 & 0x7f;
		}
		return k;
	}

	public void focusGained(FocusEvent focusevent) {
		aBoolean17 = true;
		aBoolean16 = true;
		method10();
	}

	public void focusLost(FocusEvent focusevent) {
		aBoolean17 = false;
		for (int i = 0; i < 128; i++) {
			anIntArray30[i] = 0;
		}
	}

	public void windowActivated(WindowEvent windowevent) {
	}

	public void windowClosed(WindowEvent windowevent) {
	}

	public void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	public void windowDeactivated(WindowEvent windowevent) {
	}

	public void windowDeiconified(WindowEvent windowevent) {
	}

	public void windowIconified(WindowEvent windowevent) {
	}

	public void windowOpened(WindowEvent windowevent) {
	}

	public void method6() {
	}

	public void method7() {
	}

	public void method8() {
	}

	public void method9() {
	}

	public void method10() {
	}

	public java.awt.Component method11() {
		if (aGameFrame__15 != null) {
			return aGameFrame__15;
		} else {
			return this;
		}
	}

	public void method12(Runnable runnable, int i) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(i);
	}

	public void method13(int i, String s) {
		while (aGraphics12 == null) {
			aGraphics12 = method11().getGraphics();
			try {
				method11().repaint();
			} catch (Exception ignored) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception ignored) {
			}
		}
		java.awt.Font font = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 13);
		FontMetrics fontmetrics = method11().getFontMetrics(font);
		java.awt.Font font1 = new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 13);
		method11().getFontMetrics(font1);
		if (aBoolean16) {
			aGraphics12.setColor(Color.black);
			aGraphics12.fillRect(0, 0, anInt10, anInt11);
			aBoolean16 = false;
		}
		Color color = new Color(140, 17, 17);
		int j = anInt11 / 2 - 18;
		aGraphics12.setColor(color);
		aGraphics12.drawRect(anInt10 / 2 - 152, j, 304, 34);
		aGraphics12.fillRect(anInt10 / 2 - 150, j + 2, i * 3, 30);
		aGraphics12.setColor(Color.black);
		aGraphics12.fillRect((anInt10 / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30);
		aGraphics12.setFont(font);
		aGraphics12.setColor(Color.white);
		aGraphics12.drawString(s, (anInt10 - fontmetrics.stringWidth(s)) / 2, j + 22);
	}

}
