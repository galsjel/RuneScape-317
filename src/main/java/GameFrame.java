// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

public class GameFrame extends Frame {

	public final GameShell anShell_36;

	public GameFrame(GameShell shell, int i, int j) {
		anShell_36 = shell;
		setTitle("Jagex");
		setResizable(false);
		show();
		toFront();
		resize(i + 8, j + 28);
	}

	@Override
	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
	}

	@Override
	public void update(Graphics g) {
		anShell_36.update(g);
	}

	@Override
	public void paint(Graphics g) {
		anShell_36.paint(g);
	}

}
