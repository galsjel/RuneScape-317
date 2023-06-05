// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneNPC extends SceneCharacter {

    public NPC type;

    public SceneNPC() {
    }

    public Model getSequencedModel() {
        if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
            int primaryTransformID = Animation.instances[super.primarySeqID].primary_transforms[super.primarySeqFrame];
            int secondaryTransformID = -1;
            if ((super.secondarySeqID >= 0) && (super.secondarySeqID != super.seqStandID)) {
                secondaryTransformID = Animation.instances[super.secondarySeqID].primary_transforms[super.secondarySeqFrame];
            }
            return type.built_model(primaryTransformID, secondaryTransformID, Animation.instances[super.primarySeqID].secondary_transform_mask);
        }

        int transformID = -1;

        if (super.secondarySeqID >= 0) {
            transformID = Animation.instances[super.secondarySeqID].primary_transforms[super.secondarySeqFrame];
        }

        return type.built_model(transformID, -1, null);
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

        super.height = model.min_y;

        if ((super.spotanimID != -1) && (super.spotanimFrame != -1)) {
            SpotAnim spotanim = SpotAnim.instances[super.spotanimID];
            Model model0 = spotanim.getModel();

            if (model0 != null) {
                int transformID = spotanim.seq.primary_transforms[super.spotanimFrame];

                // create a copy of the model
                Model model1 = Model.clone(true, AnimationTransform.isNull(transformID), false, model0);
                model1.translate(0, -super.spotanimOffset, 0);
                model1.build_labels();
                model1.transform(transformID);
                model1.label_faces = null;
                model1.label_vertices = null;

                if ((spotanim.scaleXZ != 128) || (spotanim.scaleY != 128)) {
                    model1.scale(spotanim.scaleXZ, spotanim.scaleY, spotanim.scaleXZ);
                }

                model1.build(64 + spotanim.lightAmbient, 850 + spotanim.lightContrast, -30, -50, -30, true);
                model = Model.join_lit(2, -819, new Model[]{model, model1});
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
