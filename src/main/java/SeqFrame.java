// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

/**
 * A {@link SeqFrame} contains the {@link SeqSkeleton} and <code>x,y,z</code> parameters to transform a {@link Model}.
 *
 * @see Model#applySequenceFrame(int)
 * @see Model#applySequenceFrames(int, int, int[])
 */
public class SeqFrame {

	private static SeqFrame[] instances;
	/**
	 * The skeleton associated to this frame.
	 */
	public SeqSkeleton skeleton;
	/**
	 * The delay in <code>ticks</code>.
	 */
	public int delay;
	/**
	 * The number of operations the frame performs.
	 */
	public int length;
	/**
	 * The list of bases the frame uses.
	 */
	public int[] bases;
	/**
	 * The frame parameters.
	 */

	public int[] x, y, z;

	/**
	 * Syntax sugar.
	 *
	 * @param id the id
	 * @return <code>id == -1</code>
	 */
	public static boolean isNull(int id) {
		return id == -1;
	}

	/**
	 * Initializes the array of instances.
	 *
	 * @param count the count
	 */
	public static void init(int count) {
		instances = new SeqFrame[count + 1];
	}

	/**
	 * Nullifies the array of instances.
	 */
	public static void clear() {
		instances = null;
	}

	/**
	 * Gets the {@link SeqFrame}
	 *
	 * @param id the frame id.
	 * @return the {@link SeqFrame} or <code>null</code> if it does not exist.
	 */
	public static SeqFrame get(int id) {
		if (instances == null || id < 0 || id >= instances.length) {
			return null;
		} else {
			return instances[id];
		}
	}

	/**
	 * Reads the animation data and stores its frames in {@link #instances}.
	 * <p>
	 * <b>Note:</b> This method can read <i>multiple</i> {@link SeqFrame} which means the input data can be an entire working
	 * animation.
	 *
	 * @param src the animation data.
	 * @see #get(int)
	 */
	public static void readAnimation(byte[] src) {
		Buffer offsets = new Buffer(src);
		offsets.position = src.length - 8;

		int offset = 0;
		Buffer header = new Buffer(src);
		header.position = offset;
		offset += offsets.method410() + 2;

		Buffer tran1 = new Buffer(src);
		tran1.position = offset;
		offset += offsets.method410();

		Buffer tran2 = new Buffer(src);
		tran2.position = offset;
		offset += offsets.method410();

		Buffer del = new Buffer(src);
		del.position = offset;
		offset += offsets.method410();

		Buffer skel = new Buffer(src);
		skel.position = offset;

		SeqSkeleton skeleton = new SeqSkeleton(skel);

		int frameCount = header.method410();
		int[] bases = new int[500];
		int[] x = new int[500];
		int[] y = new int[500];
		int[] z = new int[500];

		for (int i = 0; i < frameCount; i++) {
			SeqFrame frame = instances[header.method410()] = new SeqFrame();
			frame.delay = del.method408();
			frame.skeleton = skeleton;

			int baseCount = header.method408();
			int lastBase = -1;
			int length = 0;
			for (int base = 0; base < baseCount; base++) {
				int flags = tran1.method408();

				if (flags <= 0) {
					continue;
				}

				if (skeleton.baseTypes[base] != SeqSkeleton.OP_BASE) {
					for (int cur = base - 1; cur > lastBase; cur--) {
						if (skeleton.baseTypes[cur] == SeqSkeleton.OP_BASE) {
							bases[length] = cur;
							x[length] = 0;
							y[length] = 0;
							z[length] = 0;
							length++;
							break;
						}
					}
				}

				bases[length] = base;

				int defaultValue = 0;

				if (skeleton.baseTypes[base] == SeqSkeleton.OP_SCALE) {
					defaultValue = 128;
				}

				if ((flags & 1) != 0) {
					x[length] = tran2.method421();
				} else {
					x[length] = defaultValue;
				}

				if ((flags & 2) != 0) {
					y[length] = tran2.method421();
				} else {
					y[length] = defaultValue;
				}

				if ((flags & 4) != 0) {
					z[length] = tran2.method421();
				} else {
					z[length] = defaultValue;
				}

				lastBase = base;
				length++;
			}

			frame.length = length;
			frame.bases = new int[length];
			frame.x = new int[length];
			frame.y = new int[length];
			frame.z = new int[length];

			for (int j = 0; j < length; j++) {
				frame.bases[j] = bases[j];
				frame.x[j] = x[j];
				frame.y[j] = y[j];
				frame.z[j] = z[j];
			}
		}
	}

}
