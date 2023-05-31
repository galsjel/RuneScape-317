// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

public class Animation {

    public static int count;
    public static Animation[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("seq.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new Animation[count];
        }

        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new Animation();
            }
            instances[i].load(buffer);
        }
    }

    /**
     * The amount of frames in this {@link Animation}.
     */
    @Expose
    @SerializedName("frame_count")
    public int frameCount;

    /**
     * The list of {@link AnimationTransform} indices indexed by Frame ID.
     *
     * @see AnimationTransform
     */
    @Expose
    @SerializedName("primary_transforms")
    public int[] primary_transforms;

    /**
     * Auxiliary transform indices appear to only be used by a {@link Iface} of type <code>6</code> as seen in {@link Game#drawParentInterface(Iface, int, int, int)}.
     */
    @Expose
    @SerializedName("secondary_transforms")
    public int[] secondary_transforms;
    /**
     * Used to determine which transform bases a primary frame is allowed to use, and secondary not.
     *
     * @see Model#transform2(int, int, int[])
     */
    @Expose
    @SerializedName("secondary_transform_mask")
    public int[] secondary_transform_mask;

    /**
     * A list of durations indexed by Frame ID.
     */
    @Expose
    @SerializedName("frame_duration")
    public int[] frame_duration;

    /**
     * The number of frames from the end of this {@link Animation} used for looping.
     */
    @Expose
    @SerializedName("loop_frame_count")
    public int loopFrameCount = -1;

    /**
     * Adds additional space to the render bounds.
     *
     * @see Scene#push_temporary(Drawable, int, int, int, int, int, int, boolean, int)
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
     * Allows this {@link Animation} to override the right hand of a {@link ScenePlayer}.
     *
     * @see ScenePlayer#getSequencedModel()
     */
    @Expose
    @SerializedName("rhand_override")
    public Integer rightHandOverride;

    /**
     * Allows this {@link Animation} to override the left hand of a {@link ScenePlayer}.
     *
     * @see ScenePlayer#getSequencedModel()
     */
    @Expose
    @SerializedName("lhand_override")
    public Integer leftHandOverride;

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
     * @see Game#updateMovement(SceneCharacter)
     */

    public int _move_type = -1;

    @Expose
    @SerializedName("move_type")
    public String move_type;
    /**
     * If 0, allows looking at a target
     * If 1, stops playing on move
     * If 2, does not look at target, continues playing during movement
     *
     * @see Game#updateMovement(SceneCharacter)
     */
    public int _idle_type = -1;

    @Expose
    @SerializedName("idle_type")
    public String idle_type;

    /**
     * If 0, restarts the sequence if already playing
     * If 1, does not restart if already playing
     *
     * @see Game#readNPCUpdates()
     */
    @Expose
    @SerializedName("replay_type")
    public String replay_type;

    public int _replay_type = 1;

    public Animation() {
    }

    public int getFrameDuration(int frame) {
        int duration = frame_duration[frame];

        if (duration == 0) {
            AnimationTransform transform = AnimationTransform.get(primary_transforms[frame]);
            if (transform != null) {
                duration = frame_duration[frame] = transform.delay;
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
                primary_transforms = new int[frameCount];
                secondary_transforms = new int[frameCount];
                frame_duration = new int[frameCount];
                for (int f = 0; f < frameCount; f++) {
                    primary_transforms[f] = buffer.readU16();
                    secondary_transforms[f] = buffer.readU16();
                    if (secondary_transforms[f] == 65535) {
                        secondary_transforms[f] = -1;
                    }
                    frame_duration[f] = buffer.readU16();
                }
            } else if (code == 2) {
                loopFrameCount = buffer.readU16();
            } else if (code == 3) {
                int count = buffer.readU8();
                secondary_transform_mask = new int[count + 1];
                for (int l = 0; l < count; l++) {
                    secondary_transform_mask[l] = buffer.readU8();
                }
                secondary_transform_mask[count] = 9999999;
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
                _move_type = buffer.readU8();

            } else if (code == 10) {
                _idle_type = buffer.readU8();
            } else if (code == 11) {
                _replay_type = buffer.readU8();
            } else if (code == 12) {
                buffer.read32();
            } else {
                System.out.println("Error unrecognised seq config code: " + code);
            }
        }

        if (frameCount == 0) {
            frameCount = 1;
            primary_transforms = new int[1];
            primary_transforms[0] = -1;
            secondary_transforms = new int[1];
            secondary_transforms[0] = -1;
            frame_duration = new int[1];
            frame_duration[0] = -1;
        }

        if (_move_type == -1) {
            if (secondary_transform_mask != null) {
                _move_type = 2;
            } else {
                _move_type = 0;
            }
        }

        if (_idle_type == -1) {
            if (secondary_transform_mask != null) {
                _idle_type = 2;
            } else {
                _idle_type = 0;
            }
        }

        switch (_move_type) {
            case 0 -> move_type = "default";
            case 1 -> move_type = "pause";
            case 2 -> move_type = "no_target";
            default -> move_type = "invalid:"+_move_type;
        }
        switch (_idle_type) {
            case 0 -> idle_type = "default";
            case 1 -> idle_type = "stop_on_move";
            case 2 -> idle_type = "no_target";
            default -> idle_type = "invalid:"+_idle_type;
        }
        switch(_replay_type) {
            case 1 -> replay_type = "restart";
            case 2 -> replay_type = "continue";
            default -> replay_type = "invalid:"+_replay_type;
        }
    }

}
