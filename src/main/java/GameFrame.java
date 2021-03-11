// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

public class GameFrame extends Frame {

	public final GameShell shell;

	public GameFrame(GameShell shell, int width, int height) {
		this.shell = shell;
		setTitle("Jagex");
		setResizable(false);
		show();
		toFront();
		resize(width + 8, height + 28);
	}

	@Override
	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
	}

	@Override
	public void update(Graphics g) {
		shell.update(g);
	}

	@Override
	public void paint(Graphics g) {
		shell.paint(g);
	}

}
