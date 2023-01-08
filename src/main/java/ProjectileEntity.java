// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class ProjectileEntity extends Entity {

    public final int startCycle;
    public final int lastCycle;
    public final int srcX;
    public final int srcZ;
    public final int srcY;
    public final int offsetY;
    public final int peakPitch;
    public final int arc;
    public final int target;
    public final SpotAnimType spotanim;
    public final int level;
    public double velocityX;
    public double velocityZ;
    public double velocity;
    public double velocityY;
    public double accelerationY;
    public boolean mobile = false;
    public double x;
    public double z;
    public double y;
    public int seqFrame;
    public int seqCycle;
    public int yaw;
    public int pitch;

    public ProjectileEntity(int peakPitch, int offsetY, int startCycle, int lastCycle, int arc, int level, int srcY, int srcZ, int srcX, int target, int spotanim) {
        this.spotanim = SpotAnimType.instances[spotanim];
        this.level = level;
        this.srcX = srcX;
        this.srcZ = srcZ;
        this.srcY = srcY;
        this.startCycle = startCycle;
        this.lastCycle = lastCycle;
        this.peakPitch = peakPitch;
        this.arc = arc;
        this.target = target;
        this.offsetY = offsetY;
    }

    public void updateVelocity(int cycle, int dstZ, int dstY, int dstX) {
        if (!mobile) {
            double dx = dstX - srcX;
            double dz = dstZ - srcZ;
            double d = Math.sqrt((dx * dx) + (dz * dz));
            x = (double) srcX + ((dx * (double) arc) / d);
            z = (double) srcZ + ((dz * (double) arc) / d);
            y = srcY;
        }
        double dt = (lastCycle + 1) - cycle;
        velocityX = ((double) dstX - x) / dt;
        velocityZ = ((double) dstZ - z) / dt;
        velocity = Math.sqrt((velocityX * velocityX) + (velocityZ * velocityZ));
        if (!mobile) {
            velocityY = -velocity * Math.tan((double) peakPitch * 0.02454369D);
        }
        accelerationY = (2D * ((double) dstY - y - (velocityY * dt))) / (dt * dt);
    }

    @Override
    public Model getModel() {
        Model tmp = spotanim.getModel();
        if (tmp == null) {
            return null;
        }
        int transformID = -1;

        if (spotanim.seq != null) {
            transformID = spotanim.seq.transformIDs[seqFrame];
        }

        Model model = new Model(true, SeqTransform.isNull(transformID), false, tmp);
        if (transformID != -1) {
            model.createLabelReferences();
            model.applyTransform(transformID);
            model.labelFaces = null;
            model.labelVertices = null;
        }
        if ((spotanim.scaleXY != 128) || (spotanim.scaleZ != 128)) {
            model.scale(spotanim.scaleXY, spotanim.scaleXY, spotanim.scaleZ);
        }
        model.rotateX(pitch);
        model.calculateNormals(64 + spotanim.lightAmbient, 850 + spotanim.lightAttenuation, -30, -50, -30, true);
        return model;
    }

    public void update(int delta) {
        mobile = true;
        x += velocityX * (double) delta;
        z += velocityZ * (double) delta;
        y += (velocityY * (double) delta) + (0.5D * accelerationY * (double) delta * (double) delta);
        velocityY += accelerationY * (double) delta;
        yaw = ((int) (Math.atan2(velocityX, velocityZ) * 325.94900000000001D) + 1024) & 0x7ff;
        pitch = (int) (Math.atan2(velocityY, velocity) * 325.94900000000001D) & 0x7ff;

        if (spotanim.seq != null) {
            for (seqCycle += delta; seqCycle > spotanim.seq.getFrameDuration(seqFrame); ) {
                seqCycle -= spotanim.seq.getFrameDuration(seqFrame) + 1;
                seqFrame++;
                if (seqFrame >= spotanim.seq.frameCount) {
                    seqFrame = 0;
                }
            }
        }
    }

}
