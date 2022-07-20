// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class NPCEntity extends PathingEntity {

	public NPCType type;

	public NPCEntity() {
	}

	public Model getSequencedModel() {
		if ((super.seqId1 >= 0) && (super.anInt1529 == 0)) {
			int frame1 = SeqType.instances[super.seqId1].primaryFrames[super.seqFrame1];
			int frame2 = -1;
			if ((super.seqId2 >= 0) && (super.seqId2 != super.seqStand)) {
				frame2 = SeqType.instances[super.seqId2].primaryFrames[super.seqFrame2];
			}
			return type.getSequencedModel(frame2, frame1, SeqType.instances[super.seqId1].anIntArray357);
		}

		int frame = -1;

		if (super.seqId2 >= 0) {
			frame = SeqType.instances[super.seqId2].primaryFrames[super.seqFrame2];
		}
		return type.getSequencedModel(-1, frame, null);
	}

	@Override
	public Model getModel() {
		if (type == null) {
			return null;
		}

		Model model = getSequencedModel();

		if (model == null) {
			return null;
		}

		super.height = model.minY;

		if ((super.spotanim != -1) && (super.spotanimFrame != -1)) {
			SpotAnimType spotanim = SpotAnimType.instances[super.spotanim];
			Model model0 = spotanim.getModel();

			if (model0 != null) {
				int frame = spotanim.seq.primaryFrames[super.spotanimFrame];

				// create a copy of the model
				Model model1 = new Model(true, SeqFrame.isNull(frame), false, model0);
				model1.translate(0, -super.spotanimY, 0);
				model1.createLabelReferences();
				model1.applySequenceFrame(frame);
				model1.labelFaces = null;
				model1.labelVertices = null;

				if ((spotanim.scaleXY != 128) || (spotanim.scaleZ != 128)) {
					model1.scale(spotanim.scaleXY, spotanim.scaleXY, spotanim.scaleZ);
				}

				model1.calculateNormals(64 + spotanim.lightAmbient, 850 + spotanim.lightAttenuation, -30, -50, -30, true);
				model = new Model(2, -819, new Model[]{model, model1});
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
