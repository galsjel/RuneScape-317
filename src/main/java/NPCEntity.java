// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class NPCEntity extends PathingEntity {

    public NPCType type;

    public NPCEntity() {
    }

    public Model getSequencedModel() {
        if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
            int primaryTransformID = SeqType.instances[super.primarySeqID].transformIDs[super.primarySeqFrame];
            int secondaryTransformID = -1;
            if ((super.secondarySeqID >= 0) && (super.secondarySeqID != super.seqStandID)) {
                secondaryTransformID = SeqType.instances[super.secondarySeqID].transformIDs[super.secondarySeqFrame];
            }
            return type.getSequencedModel(secondaryTransformID, primaryTransformID, SeqType.instances[super.primarySeqID].mask);
        }

        int transformID = -1;

        if (super.secondarySeqID >= 0) {
            transformID = SeqType.instances[super.secondarySeqID].transformIDs[super.secondarySeqFrame];
        }

        return type.getSequencedModel(-1, transformID, null);
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

        if ((super.spotanimID != -1) && (super.spotanimFrame != -1)) {
            SpotAnimType spotanim = SpotAnimType.instances[super.spotanimID];
            Model model0 = spotanim.getModel();

            if (model0 != null) {
                int transformID = spotanim.seq.transformIDs[super.spotanimFrame];

                // create a copy of the model
                Model model1 = new Model(true, SeqTransform.isNull(transformID), false, model0);
                model1.translate(0, -super.spotanimOffset, 0);
                model1.createLabelReferences();
                model1.applyTransform(transformID);
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
