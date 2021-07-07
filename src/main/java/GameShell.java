// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class GameShell extends JComponent implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

	public final long[] otim = new long[10];
	public final int[] actionKey = new int[128];
	public final int[] anIntArray31 = new int[128];
	public int state;
	public int deltime = 20;
	public int mindel = 1;
	public int fps;
	public boolean debug = false;
	public int screenWidth;
	public int screenHeight;
	public Graphics graphics;
	public GameFrame frame;
	public boolean refresh = true;
	public boolean aBoolean17 = true;
	public int idleCycles;
	public int mouseButton;
	public int mouseX;
	public int mouseY;
	public int lastMousePressButton;
	public int lastMousePressX;
	public int lastMousePressY;
	public long lastMousePressTime;
	public int mousePressButton;
	public int mousePressX;
	public int mousePressY;
	public long mousePressTime;
	public int anInt32;
	public int anInt33;

	public void init(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		setSize(width, height);
		setPreferredSize(getSize());
		frame = new GameFrame(this);
		graphics = this.getGraphics();
		startThread(this, 1);
		requestFocus();
	}

	public void initApplet(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		graphics = this.getGraphics();
		startThread(this, 1);
	}

	@Override
	public void run() {
		try {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.addKeyListener(this);
			this.addFocusListener(this);

			if (frame != null) {
				frame.addWindowListener(this);
			}

			showProgress(0, "Loading...");
			load();

			int opos = 0;
			int ratio = 256;
			int delta = 1;
			int count = 0;
			int intex = 0; // interrupt exceptions

			for (int k1 = 0; k1 < 10; k1++) {
				otim[k1] = System.currentTimeMillis();
			}

			while (state >= 0) {
				if (state > 0) {
					state--;
					if (state == 0) {
						shutdown();
						return;
					}
				}

				int lastRatio = ratio;
				int lastDelta = delta;

				ratio = 300;
				delta = 1;

				long ntime = System.currentTimeMillis();

				if (otim[opos] == 0L) {
					ratio = lastRatio;
					delta = lastDelta;
				} else if (ntime > otim[opos]) {
					ratio = (int) ((2560L * deltime) / (ntime - otim[opos]));
				}

				if (ratio < 25) {
					ratio = 25;
				}

				if (ratio > 256) {
					ratio = 256;
					delta = (int) ((long) deltime - ((ntime - otim[opos]) / 10L));
				}

				if (delta > deltime) {
					delta = deltime;
				}

				otim[opos] = ntime;
				opos = (opos + 1) % 10;

				if (delta > 1) {
					for (int k2 = 0; k2 < 10; k2++) {
						if (otim[k2] != 0L) {
							otim[k2] += delta;
						}
					}
				}

				if (delta < mindel) {
					delta = mindel;
				}

				try {
					Thread.sleep(delta);
				} catch (InterruptedException e) {
					intex++;
				}

				for (; count < 256; count += ratio) {
					mousePressButton = lastMousePressButton;
					mousePressX = lastMousePressX;
					mousePressY = lastMousePressY;
					mousePressTime = lastMousePressTime;
					lastMousePressButton = 0;
					update();
					anInt32 = anInt33;
				}

				count &= 0xff;

				if (deltime > 0) {
					fps = (1000 * ratio) / (deltime * 256);
				}

				draw();

				if (debug) {
					System.out.println("ntime:" + ntime);
					for (int i = 0; i < 10; i++) {
						int o = ((opos - i - 1) + 20) % 10;
						System.out.println("otim" + o + ":" + otim[o]);
					}
					System.out.println("fps:" + fps + " ratio:" + ratio + " count:" + count);
					System.out.println("del:" + delta + " deltime:" + deltime + " mindel:" + mindel);
					System.out.println("intex:" + intex + " opos:" + opos);
					debug = false;
					intex = 0;
				}
			}
		} catch (Exception e) {
			state = -1;
			e.printStackTrace();
		}

		if (state == -1) {
			shutdown();
		}
	}

	public void shutdown() {
		state = -2;
		unload();

		if (frame != null) {
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

	public void setFrameRate(int fps) {
		deltime = 1000 / fps;
	}

	public void destroy() {
		state = -1;

		try {
			Thread.sleep(5000L);
		} catch (Exception ignored) {
		}

		if (state == -1) {
			shutdown();
		}
	}

	@Override
	public void update(Graphics g) {
		if (graphics == null) {
			graphics = g;
		}
		refresh = true;
		refresh();
	}

	@Override
	public void paint(Graphics g) {
		if (graphics == null) {
			graphics = g;
		}
		refresh = true;
		refresh();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		idleCycles = 0;
		lastMousePressX = x;
		lastMousePressY = y;
		lastMousePressTime = System.currentTimeMillis();

		if (SwingUtilities.isRightMouseButton(e)) {
			lastMousePressButton = 2;
			mouseButton = 2;
		} else {
			lastMousePressButton = 1;
			mouseButton = 1;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		idleCycles = 0;
		mouseButton = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		idleCycles = 0;
		mouseX = -1;
		mouseY = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		idleCycles = 0;
		mouseX = x;
		mouseY = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		idleCycles = 0;
		mouseX = x;
		mouseY = y;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		idleCycles = 0;
		int code = e.getKeyCode();
		int ch = e.getKeyChar();

		if (ch < 30) {
			ch = 0;
		}

		if (code == KeyEvent.VK_LEFT) {
			ch = 1;
		} else if (code == KeyEvent.VK_RIGHT) {
			ch = 2;
		} else if (code == KeyEvent.VK_UP) {
			ch = 3;
		} else if (code == KeyEvent.VK_DOWN) {
			ch = 4;
		} else if (code == KeyEvent.VK_CONTROL) {
			ch = 5;
		} else if (code == KeyEvent.VK_BACK_SPACE) {
			ch = 8;
		} else if (code == KeyEvent.VK_DELETE) {
			ch = 8;
		} else if (code == KeyEvent.VK_TAB) {
			ch = 9;
		} else if (code == KeyEvent.VK_ENTER) {
			ch = 10;
		} else if ((code >= KeyEvent.VK_F1) && (code <= KeyEvent.VK_F12)) {
			ch = (1008 + code) - KeyEvent.VK_F1;
		} else if (code == KeyEvent.VK_HOME) {
			ch = 1000;
		} else if (code == KeyEvent.VK_END) {
			ch = 1001;
		} else if (code == KeyEvent.VK_PAGE_UP) {
			ch = 1002;
		} else if (code == KeyEvent.VK_PAGE_DOWN) {
			ch = 1003;
		}

		if ((ch > 0) && (ch < 128)) {
			actionKey[ch] = 1;
		}

		if (ch > 4) {
			anIntArray31[anInt33] = ch;
			anInt33 = (anInt33 + 1) & 0x7f;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		idleCycles = 0;

		int code = e.getKeyCode();
		char action = e.getKeyChar();

		if (action < 30) {
			action = 0;
		}

		if (code == KeyEvent.VK_LEFT) {
			action = 1;
		} else if (code == KeyEvent.VK_RIGHT) {
			action = 2;
		} else if (code == KeyEvent.VK_UP) {
			action = 3;
		} else if (code == KeyEvent.VK_DOWN) {
			action = 4;
		} else if (code == KeyEvent.VK_CONTROL) {
			action = 5;
		} else if (code == KeyEvent.VK_BACK_SPACE) {
			action = 8;
		} else if (code == KeyEvent.VK_DELETE) {
			action = 8;
		} else if (code == KeyEvent.VK_TAB) {
			action = 9;
		} else if (code == KeyEvent.VK_ENTER) {
			action = 10;
		}

		if ((action > 0) && (action < 128)) {
			actionKey[action] = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public int pollKey() {
		int key = -1;
		if (anInt33 != anInt32) {
			key = anIntArray31[anInt32];
			anInt32 = (anInt32 + 1) & 0x7f;
		}
		return key;
	}

	@Override
	public void focusGained(FocusEvent e) {
		aBoolean17 = true;
		refresh = true;
		refresh();
	}

	@Override
	public void focusLost(FocusEvent e) {
		aBoolean17 = false;
		for (int i = 0; i < 128; i++) {
			actionKey[i] = 0;
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		destroy();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Called when the {@link GameShell} thread starts.
	 *
	 * @see #run()
	 */
	public abstract void load() throws IOException;

	/**
	 * @see #run()
	 */
	public abstract void update() throws IOException;

	/**
	 * @see #run()
	 */
	public abstract void unload();

	/**
	 * @see #run()
	 */
	public abstract void draw() throws IOException;

	/**
	 * Refresh is invoked whenever the shell is expected to potentially lost its display image.
	 *
	 * @see #update(Graphics)
	 * @see #paint(Graphics)
	 * @see #focusGained(FocusEvent)
	 */
	public abstract void refresh();

	public void startThread(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	public void showProgress(int percent, String message) throws IOException {
		while (graphics == null) {
			graphics = this.getGraphics();
			try {
				this.repaint();
			} catch (Exception ignored) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception ignored) {
			}
		}

		java.awt.Font helvetica = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 13);
		FontMetrics metrics = this.getFontMetrics(helvetica);

		if (refresh) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, screenWidth, screenHeight);
			refresh = false;
		}

		int y = (screenHeight / 2) - 18;

		graphics.setColor(new Color(140, 17, 17));
		graphics.drawRect((screenWidth / 2) - 152, y, 304, 34);
		graphics.fillRect((screenWidth / 2) - 150, y + 2, percent * 3, 30);

		graphics.setColor(Color.black);
		graphics.fillRect(((screenWidth / 2) - 150) + (percent * 3), y + 2, 300 - (percent * 3), 30);

		graphics.setFont(helvetica);
		graphics.setColor(Color.white);
		graphics.drawString(message, (screenWidth - metrics.stringWidth(message)) / 2, y + 22);
	}

}
