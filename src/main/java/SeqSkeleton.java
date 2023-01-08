/**
 * A {@link SeqSkeleton} describes the usage and relationship between groups of vertices.
 *
 * @see Model#applyTransform(int)
 * @see Model#applyTransforms(int, int, int[])
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
    public final int[] baseTypes;
    /**
     * The labels belonging to the base.
     *
     * @see Model#createLabelReferences()
     */
    public final int[][] baseLabels;

    /**
     * Constructs a new {@link SeqSkeleton} read from the provided input.
     *
     * @param in the input
     */
    public SeqSkeleton(Buffer in) {
        final int length = in.readU8();
        baseTypes = new int[length];
        baseLabels = new int[length][];
        for (int group = 0; group < length; group++) {
            baseTypes[group] = in.readU8();
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
