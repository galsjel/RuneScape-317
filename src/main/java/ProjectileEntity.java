// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ProjectileEntity extends Entity {

	public final int anInt1571;
	public final int anInt1572;
	public final int anInt1580;
	public final int anInt1581;
	public final int anInt1582;
	public final int anInt1583;
	public final int anInt1588;
	public final int anInt1589;
	public final int anInt1590;
	public final SpotAnimType aType_1592;
	public final int anInt1597;
	public double aDouble1574;
	public double aDouble1575;
	public double aDouble1576;
	public double aDouble1577;
	public double aDouble1578;
	public boolean aBoolean1579 = false;
	public double aDouble1585;
	public double aDouble1586;
	public double aDouble1587;
	public int anInt1593;
	public int anInt1594;
	public int anInt1595;
	public int anInt1596;

	public ProjectileEntity(int i, int j, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2) {
		aType_1592 = SpotAnimType.instances[l2];
		anInt1597 = k1;
		anInt1580 = j2;
		anInt1581 = i2;
		anInt1582 = l1;
		anInt1571 = l;
		anInt1572 = i1;
		anInt1588 = i;
		anInt1589 = j1;
		anInt1590 = k2;
		anInt1583 = j;
	}

	public void method455(int i, int j, int k, int l) {
		if (!aBoolean1579) {
			double d = l - anInt1580;
			double d2 = j - anInt1581;
			double d3 = Math.sqrt((d * d) + (d2 * d2));
			aDouble1585 = (double) anInt1580 + ((d * (double) anInt1589) / d3);
			aDouble1586 = (double) anInt1581 + ((d2 * (double) anInt1589) / d3);
			aDouble1587 = anInt1582;
		}
		double d1 = (anInt1572 + 1) - i;
		aDouble1574 = ((double) l - aDouble1585) / d1;
		aDouble1575 = ((double) j - aDouble1586) / d1;
		aDouble1576 = Math.sqrt((aDouble1574 * aDouble1574) + (aDouble1575 * aDouble1575));
		if (!aBoolean1579) {
			aDouble1577 = -aDouble1576 * Math.tan((double) anInt1588 * 0.02454369D);
		}
		aDouble1578 = (2D * ((double) k - aDouble1587 - (aDouble1577 * d1))) / (d1 * d1);
	}

	@Override
	public Model getModel() {
		Model model = aType_1592.method266();
		if (model == null) {
			return null;
		}
		int j = -1;
		if (aType_1592.seq != null) {
			j = aType_1592.seq.primaryFrames[anInt1593];
		}
		Model model_1 = new Model(true, SeqFrame.isNull(j), false, model);
		if (j != -1) {
			model_1.createLabelReferences();
			model_1.applySequenceFrame(j);
			model_1.labelFaces = null;
			model_1.labelVertices = null;
		}
		if ((aType_1592.anInt410 != 128) || (aType_1592.anInt411 != 128)) {
			model_1.scale(aType_1592.anInt410, aType_1592.anInt410, aType_1592.anInt411);
		}
		model_1.rotateX(anInt1596);
		model_1.calculateNormals(64 + aType_1592.anInt413, 850 + aType_1592.anInt414, -30, -50, -30, true);
		return model_1;
	}

	public void method456(int i) {
		aBoolean1579 = true;
		aDouble1585 += aDouble1574 * (double) i;
		aDouble1586 += aDouble1575 * (double) i;
		aDouble1587 += (aDouble1577 * (double) i) + (0.5D * aDouble1578 * (double) i * (double) i);
		aDouble1577 += aDouble1578 * (double) i;
		anInt1595 = ((int) (Math.atan2(aDouble1574, aDouble1575) * 325.94900000000001D) + 1024) & 0x7ff;
		anInt1596 = (int) (Math.atan2(aDouble1577, aDouble1576) * 325.94900000000001D) & 0x7ff;
		if (aType_1592.seq != null) {
			for (anInt1594 += i; anInt1594 > aType_1592.seq.getFrameDelay(anInt1593); ) {
				anInt1594 -= aType_1592.seq.getFrameDelay(anInt1593) + 1;
				anInt1593++;
				if (anInt1593 >= aType_1592.seq.frameCount) {
					anInt1593 = 0;
				}
			}
		}
	}

}
