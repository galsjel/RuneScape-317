// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneSpotAnim extends Drawable {

    public final int level;
    public final int x;
    public final int z;
    public final int y;
    public final int startCycle;
    public final SpotAnim type;
    public boolean seqComplete = false;
    public int seqFrame;
    public int seqCycle;

    public SceneSpotAnim(int level, int cycle, int delay, int id, int y, int z, int x) {
        type = SpotAnim.instances[id];
        this.level = level;
        this.x = x;
        this.z = z;
        this.y = y;
        startCycle = cycle + delay;
    }

    @Override
    public Model getModel() {
        Model base = type.getModel();

        if (base == null) {
            return null;
        }

        int transformID = type.seq.primary_transforms[seqFrame];

        Model model = Model.clone(true, AnimationTransform.isNull(transformID), false, base);

        if (!seqComplete) {
            model.build_labels();
            model.transform(transformID);
            model.labelFaces = null;
            model.labelVertices = null;
        }

        if ((type.scaleXZ != 128) || (type.scaleY != 128)) {
            model.scale(type.scaleXZ, type.scaleY, type.scaleXZ);
        }

        if (type.rotateY != 0) {
            if (type.rotateY == 90) {
                model.rotateY90();
            }
            if (type.rotateY == 180) {
                model.rotateY90();
                model.rotateY90();
            }
            if (type.rotateY == 270) {
                model.rotateY90();
                model.rotateY90();
                model.rotateY90();
            }
        }
        model.build(64 + type.lightAmbient, 850 + type.lightContrast, -30, -50, -30, true);
        return model;
    }

    public void update(int delta) {
        for (seqCycle += delta; seqCycle > type.seq.getFrameDuration(seqFrame); ) {
            seqCycle -= type.seq.getFrameDuration(seqFrame) + 1;
            seqFrame++;

            if (seqFrame >= type.seq.frameCount) {
                seqFrame = 0;
                seqComplete = true;
            }
        }
    }

}
