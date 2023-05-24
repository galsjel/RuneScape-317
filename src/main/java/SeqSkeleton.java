import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A {@link SeqSkeleton} describes the usage and relationship between groups of vertices.
 *
 * @see Model#transform(int)
 * @see Model#transform2(int, int, int[])
 */
public class SeqSkeleton {
    /**
     * A base can be thought of as the origin of a bone. This operator comes first before any other operations occur.
     */
    public static final int OP_BASE = 0;
    public static final int OP_TRANSLATE = 1;

    public static final int OP_ROTATE = 2;
    public static final int OP_SCALE = 3;
    public static final int OP_ALPHA = 5;
    /**
     * A base type determines the operation performed on the labels belonging to it.
     *
     * @see #OP_BASE
     * @see #OP_TRANSLATE
     * @see #OP_ROTATE
     * @see #OP_SCALE
     * @see #OP_ALPHA
     */
    public final int[] _base_types;

    @Expose
    public final String[] base_types;
    /**
     * The labels belonging to the base.
     *
     * @see Model#build_labels()
     */
    @Expose
    @SerializedName("base_labels")
    public final int[][] baseLabels;

    /**
     * Constructs a new {@link SeqSkeleton} read from the provided input.
     *
     * @param in the input
     */
    public SeqSkeleton(Buffer in) {
        final int length = in.readU8();
        _base_types = new int[length];
        baseLabels = new int[length][];
        for (int group = 0; group < length; group++) {
            _base_types[group] = in.readU8();
        }
        base_types = new String[length];
        for (int i = 0; i < length; i++) {
            switch (_base_types[i]) {
                case 0 -> base_types[i] = "origin";
                case 1 -> base_types[i] = "translate";
                case 2 -> base_types[i] = "rotate";
                case 3 -> base_types[i] = "scale";
                case 5 -> base_types[i] = "transparency";
            }
        }
        for (int group = 0; group < length; group++) {
            int count = in.readU8();
            baseLabels[group] = new int[count];
            for (int child = 0; child < count; child++) {
                baseLabels[group][child] = in.readU8();
            }
        }
    }

}
