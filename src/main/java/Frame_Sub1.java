// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

public class Frame_Sub1 extends Frame {

	public final Applet_Sub1 anApplet_Sub1_36;

	public Frame_Sub1(Applet_Sub1 applet_sub1, int i, int j) {
		anApplet_Sub1_36 = applet_sub1;
		setTitle("Jagex");
		setResizable(false);
		show();
		toFront();
		resize(i + 8, j + 28);
	}

	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
	}

	public void update(Graphics g) {
		anApplet_Sub1_36.update(g);
	}

	public void paint(Graphics g) {
		anApplet_Sub1_36.paint(g);
	}

}
