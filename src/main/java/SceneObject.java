// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneObject extends Drawable {

    public static Game game;
    public final int[] overrideTypeIDs;
    public final int varbit;
    public final int varp;
    public final int heightmapSW;
    public final int heightmapSE;
    public final int heightmapNE;
    public final int heightmapNW;
    public final int id;
    public final int kind;
    public final int rotation;
    public int seqFrame;
    public Animation seq;
    public int seqCycle;

    public SceneObject(int id, int rotation, int kind, int heightmapSE, int heightmapNE, int heightmapSW, int heightmapNW, int seqID, boolean randomFrame) {
        this.id = id;
        this.kind = kind;
        this.rotation = rotation;
        this.heightmapSW = heightmapSW;
        this.heightmapSE = heightmapSE;
        this.heightmapNE = heightmapNE;
        this.heightmapNW = heightmapNW;

        if (seqID != -1) {
            seq = Animation.instances[seqID];
            seqFrame = 0;
            seqCycle = Game.loopCycle;

            if (randomFrame && (seq.loopFrameCount != -1)) {
                seqFrame = (int) (Math.random() * (double) seq.frameCount);
                seqCycle -= (int) (Math.random() * (double) seq.getFrameDuration(seqFrame));
            }
        }
        Obj type = Obj.get(this.id);
        varbit = type.varbit;
        varp = type.varp;
        overrideTypeIDs = type.overrides;
    }

    @Override
    public Model getModel() {
        int transformID = -1;

        if (seq != null) {
            int delta = Game.loopCycle - seqCycle;

            if ((delta > 100) && (seq.loopFrameCount > 0)) {
                delta = 100;
            }

            while (delta > seq.getFrameDuration(seqFrame)) {
                delta -= seq.getFrameDuration(seqFrame);
                seqFrame++;

                if (seqFrame < seq.frameCount) {
                    continue;
                }

                seqFrame -= seq.loopFrameCount;

                if ((seqFrame >= 0) && (seqFrame < seq.frameCount)) {
                    continue;
                }

                seq = null;
                break;
            }
            seqCycle = Game.loopCycle - delta;

            if (seq != null) {
                transformID = seq.primary_transforms[seqFrame];
            }
        }

        Obj type;

        if (overrideTypeIDs != null) {
            type = getOverrideType();
        } else {
            type = Obj.get(id);
        }

        if (type == null) {
            return null;
        } else {
            return type.getModel(kind, transformID, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW);
        }
    }

    public Obj getOverrideType() {
        int value = -1;

        if (varbit != -1) {
            Varbit varbit = Varbit.instances[this.varbit];
            int varp = varbit.varp;
            int low = varbit.lsb;
            int high = varbit.msb;
            int mask = Game.BITMASK[high - low];
            value = (game.varps[varp] >> low) & mask;
        } else if (varp != -1) {
            value = game.varps[varp];
        }

        if ((value < 0) || (value >= overrideTypeIDs.length) || (overrideTypeIDs[value] == -1)) {
            return null;
        } else {
            return Obj.get(overrideTypeIDs[value]);
        }
    }

}
