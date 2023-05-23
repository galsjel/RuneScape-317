// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

public class SeqType {

    public static int count;
    public static SeqType[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("seq.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new SeqType[count];
        }

        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new SeqType();
            }
            instances[i].load(buffer);
        }
    }

    /**
     * The amount of frames in this {@link SeqType}.
     */
    @Expose
    @SerializedName("frame_count")
    public int frameCount;

    /**
     * The list of {@link SeqTransform} indices indexed by Frame ID.
     *
     * @see SeqTransform
     */
    @Expose
    @SerializedName("transforms")
    public int[] transforms;

    /**
     * Auxiliary transform indices appear to only be used by a {@link IfType} of type <code>6</code> as seen in {@link Game#drawParentInterface(IfType, int, int, int)}.
     */
    @Expose
    @SerializedName("aux_transforms")
    public int[] aux_transforms;

    /**
     * A list of durations indexed by Frame ID.
     */
    @Expose
    @SerializedName("frame_duration")
    public int[] frameDuration;

    /**
     * The number of frames from the end of this {@link SeqType} used for looping.
     */
    @Expose
    @SerializedName("loop_frame_count")
    public int loopFrameCount = -1;

    /**
     * Used to determine which transform bases a primary frame is allowed to use, and secondary not.
     *
     * @see Model#transform2(int, int, int[])
     */
    @Expose
    @SerializedName("mask")
    public int[] mask;

    /**
     * Adds additional space to the render bounds.
     *
     * @see Scene#addTemporary(Drawable, int, int, int, int, int, int, boolean, int)
     */
    @Expose
    @SerializedName("forward_render_padding")
    public Boolean forwardRenderPadding = false;

    /**
     * The priority.
     */
    @Expose
    @SerializedName("priority")
    public int priority = 5;

    /**
     * Allows this {@link SeqType} to override the right hand of a {@link PlayerEntity}.
     *
     * @see PlayerEntity#getSequencedModel()
     */
    @Expose
    @SerializedName("override_right_hand")
    public Integer rightHandOverride = -1;

    /**
     * Allows this {@link SeqType} to override the left hand of a {@link PlayerEntity}.
     *
     * @see PlayerEntity#getSequencedModel()
     */
    @Expose
    @SerializedName("override_left_hand")
    public Integer leftHandOverride = -1;

    /**
     * How many times this seq is allowed to loop before stopping.
     */
    @Expose
    @SerializedName("loop_count")
    public int loopCount = 99;

    /**
     * If 0, causes faster movement, allows looking at target
     * If 1, pause while moving
     * If 2, does not look at target, continues playing during movement
     *
     * @see Game#updateMovement(PathingEntity)
     */
    @Expose
    @SerializedName("move_type")
    public int moveStyle = -1;

    /**
     * If 0, allows looking at a target
     * If 1, stops playing on move
     * If 2, does not look at target, continues playing during movement
     *
     * @see Game#updateMovement(PathingEntity)
     */
    @Expose
    @SerializedName("idle_type")
    public int idleStyle = -1;

    /**
     * If 0, restarts the sequence if already playing
     * If 1, does not restart if already playing
     *
     * @see Game#readNPCUpdates()
     */
    @Expose
    @SerializedName("replay_type")
    public int replayStyle = 1;

    public SeqType() {
    }

    public int getFrameDuration(int frame) {
        int duration = frameDuration[frame];

        if (duration == 0) {
            SeqTransform transform = SeqTransform.get(transforms[frame]);
            if (transform != null) {
                duration = frameDuration[frame] = transform.delay;
            }
        }

        if (duration == 0) {
            duration = 1;
        }

        return duration;
    }

    public void load(Buffer buffer) {
        while (true) {
            int code = buffer.readU8();

            if (code == 0) {
                break;
            } else if (code == 1) {
                frameCount = buffer.readU8();
                transforms = new int[frameCount];
                aux_transforms = new int[frameCount];
                frameDuration = new int[frameCount];
                for (int f = 0; f < frameCount; f++) {
                    transforms[f] = buffer.readU16();
                    aux_transforms[f] = buffer.readU16();
                    if (aux_transforms[f] == 65535) {
                        aux_transforms[f] = -1;
                    }
                    frameDuration[f] = buffer.readU16();
                }
            } else if (code == 2) {
                loopFrameCount = buffer.readU16();
            } else if (code == 3) {
                int count = buffer.readU8();
                mask = new int[count + 1];
                for (int l = 0; l < count; l++) {
                    mask[l] = buffer.readU8();
                }
                mask[count] = 9999999;
            } else if (code == 4) {
                forwardRenderPadding = true;
            } else if (code == 5) {
                priority = buffer.readU8();
            } else if (code == 6) {
                rightHandOverride = buffer.readU16();
            } else if (code == 7) {
                leftHandOverride = buffer.readU16();
            } else if (code == 8) {
                loopCount = buffer.readU8();
            } else if (code == 9) {
                moveStyle = buffer.readU8();
            } else if (code == 10) {
                idleStyle = buffer.readU8();
            } else if (code == 11) {
                replayStyle = buffer.readU8();
            } else if (code == 12) {
                buffer.read32();
            } else {
                System.out.println("Error unrecognised seq config code: " + code);
            }
        }

        if (frameCount == 0) {
            frameCount = 1;
            transforms = new int[1];
            transforms[0] = -1;
            aux_transforms = new int[1];
            aux_transforms[0] = -1;
            frameDuration = new int[1];
            frameDuration[0] = -1;
        }

        if (moveStyle == -1) {
            if (mask != null) {
                moveStyle = 2;
            } else {
                moveStyle = 0;
            }
        }

        if (idleStyle == -1) {
            if (mask != null) {
                idleStyle = 2;
            } else {
                idleStyle = 0;
            }
        }
    }

}
