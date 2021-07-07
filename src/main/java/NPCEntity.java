// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class NPCEntity extends PathingEntity {

	public NPCType type;

	public NPCEntity() {
	}

	public Model method450() {
		if ((super.anInt1526 >= 0) && (super.anInt1529 == 0)) {
			int k = SeqType.instances[super.anInt1526].primaryFrames[super.anInt1527];
			int i1 = -1;
			if ((super.seqCurrent >= 0) && (super.seqCurrent != super.seqStand)) {
				i1 = SeqType.instances[super.seqCurrent].primaryFrames[super.seqFrame];
			}
			return type.method164(i1, k, SeqType.instances[super.anInt1526].anIntArray357);
		}
		int l = -1;
		if (super.seqCurrent >= 0) {
			l = SeqType.instances[super.seqCurrent].primaryFrames[super.seqFrame];
		}
		return type.method164(-1, l, null);
	}

	@Override
	public Model getModel() {
		if (type == null) {
			return null;
		}
		Model model = method450();
		if (model == null) {
			return null;
		}
		super.height = model.minY;
		if ((super.spotanim != -1) && (super.spotanimFrame != -1)) {
			SpotAnimType type = SpotAnimType.instances[super.spotanim];
			Model model_1 = type.method266();
			if (model_1 != null) {
				int j = type.seq.primaryFrames[super.spotanimFrame];
				Model class30_sub2_sub4_sub6_2 = new Model(true, SeqFrame.isNull(j), false, model_1);
				class30_sub2_sub4_sub6_2.translate(0, -super.anInt1524, 0);
				class30_sub2_sub4_sub6_2.createLabelReferences();
				class30_sub2_sub4_sub6_2.applySequenceFrame(j);
				class30_sub2_sub4_sub6_2.labelFaces = null;
				class30_sub2_sub4_sub6_2.labelVertices = null;
				if ((type.anInt410 != 128) || (type.anInt411 != 128)) {
					class30_sub2_sub4_sub6_2.scale(type.anInt410, type.anInt410, type.anInt411);
				}
				class30_sub2_sub4_sub6_2.calculateNormals(64 + type.anInt413, 850 + type.anInt414, -30, -50, -30, true);
				Model[] aclass30_sub2_sub4_sub6 = {model, class30_sub2_sub4_sub6_2};
				model = new Model(2, -819, aclass30_sub2_sub4_sub6);
			}
		}
		if (type.size == 1) {
			model.pickable = true;
		}
		return model;
	}

	@Override
	public boolean isVisible() {
		return type != null;
	}

}
