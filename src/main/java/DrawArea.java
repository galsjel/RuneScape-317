// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.*;

public class DrawArea implements ImageProducer, ImageObserver {

	public final int[] pixels;
	public final int width;
	public final int height;
	public final ColorModel aColorModel318;
	public final Image image;
	public ImageConsumer consumer;

	public DrawArea(int width, int height, java.awt.Component component) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		aColorModel318 = new DirectColorModel(32, 0xff0000, 65280, 255);
		image = component.createImage(this);
		method239();
		component.prepareImage(image, this);
		method239();
		component.prepareImage(image, this);
		method239();
		component.prepareImage(image, this);
		bind();
	}

	public void bind() {
		Draw2D.bind(pixels, width, height);
	}

	public void draw(Graphics g, int x, int y) {
		method239();
		g.drawImage(image, x, y, this);
	}

	@Override
	public synchronized void addConsumer(ImageConsumer consumer) {
		this.consumer = consumer;
		consumer.setDimensions(width, height);
		consumer.setProperties(null);
		consumer.setColorModel(aColorModel318);
		consumer.setHints(14);
	}

	@Override
	public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
		return consumer == imageconsumer;
	}

	@Override
	public synchronized void removeConsumer(ImageConsumer imageconsumer) {
		if (consumer == imageconsumer) {
			consumer = null;
		}
	}

	@Override
	public void startProduction(ImageConsumer imageconsumer) {
		addConsumer(imageconsumer);
	}

	@Override
	public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
		System.out.println("TDLR");
	}

	public synchronized void method239() {
		if (consumer != null) {
			consumer.setPixels(0, 0, width, height, aColorModel318, pixels, 0, width);
			consumer.imageComplete(2);
		}
	}

	@Override
	public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
		return true;
	}

}
