// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.*;

public class Class15 implements ImageProducer, ImageObserver {

	public int anInt313;
	public final boolean aBoolean314;
	public final int[] anIntArray315;
	public final int anInt316;
	public final int anInt317;
	public final ColorModel aColorModel318;
	public ImageConsumer anImageConsumer319;
	public final Image anImage320;

	public Class15(int i, int j, Component component, int k) {
		aBoolean314 = true;
		anInt316 = i;
		anInt317 = j;
		anIntArray315 = new int[i * j];
		aColorModel318 = new DirectColorModel(32, 0xff0000, 65280, 255);
		anImage320 = component.createImage(this);
		method239();
		component.prepareImage(anImage320, this);
		method239();
		component.prepareImage(anImage320, this);
		method239();
		component.prepareImage(anImage320, this);
		method237();
	}

	public void method237() {
		Class30_Sub2_Sub1.method331(anInt317, anInt316, anIntArray315);
	}

	public void method238(int i, int j, Graphics g, int k) {
		method239();
		g.drawImage(anImage320, k, i, this);
	}

	public synchronized void addConsumer(ImageConsumer imageconsumer) {
		anImageConsumer319 = imageconsumer;
		imageconsumer.setDimensions(anInt316, anInt317);
		imageconsumer.setProperties(null);
		imageconsumer.setColorModel(aColorModel318);
		imageconsumer.setHints(14);
	}

	public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
		return anImageConsumer319 == imageconsumer;
	}

	public synchronized void removeConsumer(ImageConsumer imageconsumer) {
		if (anImageConsumer319 == imageconsumer) {
			anImageConsumer319 = null;
		}
	}

	public void startProduction(ImageConsumer imageconsumer) {
		addConsumer(imageconsumer);
	}

	public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
		System.out.println("TDLR");
	}

	public synchronized void method239() {
		if (anImageConsumer319 != null) {
			anImageConsumer319.setPixels(0, 0, anInt316, anInt317, aColorModel318, anIntArray315, 0, anInt316);
			anImageConsumer319.imageComplete(2);
		}
	}

	public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
		return true;
	}

}
