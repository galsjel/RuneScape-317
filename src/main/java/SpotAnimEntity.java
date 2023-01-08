// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SpotAnimEntity extends Entity {

    public final int level;
    public final int x;
    public final int z;
    public final int y;
    public final int startCycle;
    public final SpotAnimType type;
    public boolean seqComplete = false;
    public int seqFrame;
    public int seqCycle;

    public SpotAnimEntity(int level, int cycle, int delay, int id, int y, int z, int x) {
        type = SpotAnimType.instances[id];
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

        int transformID = type.seq.transformIDs[seqFrame];

        Model model = new Model(true, SeqTransform.isNull(transformID), false, base);

        if (!seqComplete) {
            model.createLabelReferences();
            model.applyTransform(transformID);
            model.labelFaces = null;
            model.labelVertices = null;
        }

        if ((type.scaleXY != 128) || (type.scaleZ != 128)) {
            model.scale(type.scaleXY, type.scaleXY, type.scaleZ);
        }

        if (type.rotation != 0) {
            if (type.rotation == 90) {
                model.rotateY90();
            }
            if (type.rotation == 180) {
                model.rotateY90();
                model.rotateY90();
            }
            if (type.rotation == 270) {
                model.rotateY90();
                model.rotateY90();
                model.rotateY90();
            }
        }
        model.calculateNormals(64 + type.lightAmbient, 850 + type.lightAttenuation, -30, -50, -30, true);
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
