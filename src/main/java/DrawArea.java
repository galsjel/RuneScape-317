// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DrawArea {

	public final int[] pixels;
	public final int width;
	public final int height;
	public final BufferedImage image;

	public DrawArea(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
		bind();
	}

	public DrawArea(Image24 image) {
		this(image.width, image.height);
		image.blitOpaque(0, 0);
	}

	public void bind() {
		Draw2D.bind(pixels, width, height);
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

}
