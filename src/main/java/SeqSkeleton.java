/**
 * A {@link SeqSkeleton} describes the usage and relationship between groups.
 *
 * @see Model#applySequenceFrame(int)
 * @see Model#applySequenceFrames(int, int, int[])
 */
public class SeqSkeleton {

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
	 */
	public final int[][] baseLabels;

	/**
	 * Constructs a new {@link SeqSkeleton} read from the provided input.
	 *
	 * @param in the input
	 */
	public SeqSkeleton(Buffer in) {
		final int length = in.get1U();
		baseTypes = new int[length];
		baseLabels = new int[length][];
		for (int group = 0; group < length; group++) {
			baseTypes[group] = in.get1U();
		}
		for (int group = 0; group < length; group++) {
			int count = in.get1U();
			baseLabels[group] = new int[count];
			for (int child = 0; child < count; child++) {
				baseLabels[group][child] = in.get1U();
			}
		}
	}

}
