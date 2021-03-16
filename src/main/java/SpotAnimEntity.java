// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SpotAnimEntity extends Entity {

	public final int anInt1560;
	public final int anInt1561;
	public final int anInt1562;
	public final int anInt1563;
	public final int anInt1564;
	public final SpotAnimType aType_1568;
	public boolean aBoolean1567 = false;
	public int anInt1569;
	public int anInt1570;

	public SpotAnimEntity(int i, int j, int l, int i1, int j1, int k1, int l1) {
		aType_1568 = SpotAnimType.instances[i1];
		anInt1560 = i;
		anInt1561 = l1;
		anInt1562 = k1;
		anInt1563 = j1;
		anInt1564 = j + l;
	}

	@Override
	public Model getModel() {
		Model model = aType_1568.method266();
		if (model == null) {
			return null;
		}
		int j = aType_1568.seq.primaryFrames[anInt1569];
		Model model_1 = new Model(true, SeqFrame.isNull(j), false, model);
		if (!aBoolean1567) {
			model_1.createLabelReferences();
			model_1.applySequenceFrame(j);
			model_1.labelFaces = null;
			model_1.labelVertices = null;
		}
		if ((aType_1568.anInt410 != 128) || (aType_1568.anInt411 != 128)) {
			model_1.scale(aType_1568.anInt410, aType_1568.anInt410, aType_1568.anInt411);
		}
		if (aType_1568.anInt412 != 0) {
			if (aType_1568.anInt412 == 90) {
				model_1.rotateY90();
			}
			if (aType_1568.anInt412 == 180) {
				model_1.rotateY90();
				model_1.rotateY90();
			}
			if (aType_1568.anInt412 == 270) {
				model_1.rotateY90();
				model_1.rotateY90();
				model_1.rotateY90();
			}
		}
		model_1.calculateNormals(64 + aType_1568.anInt413, 850 + aType_1568.anInt414, -30, -50, -30, true);
		return model_1;
	}

	public void method454(int i) {
		for (anInt1570 += i; anInt1570 > aType_1568.seq.getFrameDelay(anInt1569); ) {
			anInt1570 -= aType_1568.seq.getFrameDelay(anInt1569) + 1;
			anInt1569++;
			if ((anInt1569 >= aType_1568.seq.frameCount) && ((anInt1569 < 0) || (anInt1569 >= aType_1568.seq.frameCount))) {
				anInt1569 = 0;
				aBoolean1567 = true;
			}
		}
	}

}
