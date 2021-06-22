/**
 * A {@link SeqFrame} contains the {@link SeqSkeleton} and <code>x,y,z</code> parameters to transform a {@link Model}.
 *
 * @see Model#applySequenceFrame(int)
 * @see Model#applySequenceFrames(int, int, int[])
 */
public class SeqFrame {

	public static SeqFrame[] instances;

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
	public static void unload() {
		instances = null;
	}

	/**
	 * Gets the {@link SeqFrame}
	 *
	 * @param id the frame id.
	 * @return the {@link SeqFrame} or <code>null</code> if it does not exist.
	 */
	public static SeqFrame get(int id) {
		if ((instances == null) || (id < 0) || (id >= instances.length)) {
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
	public static void unpack(byte[] src) {
		Buffer offsets = new Buffer(src);
		offsets.position = src.length - 8;

		Buffer header = new Buffer(src);
		Buffer tran1 = new Buffer(src);
		Buffer tran2 = new Buffer(src);
		Buffer del = new Buffer(src);
		Buffer skel = new Buffer(src);

		int offset = 0;
		header.position = offset;
		offset += offsets.get2U() + 2;

		tran1.position = offset;
		offset += offsets.get2U();

		tran2.position = offset;
		offset += offsets.get2U();

		del.position = offset;
		offset += offsets.get2U();

		skel.position = offset;

		SeqSkeleton skeleton = new SeqSkeleton(skel);

		int frameCount = header.get2U();
		int[] bases = new int[500];
		int[] x = new int[500];
		int[] y = new int[500];
		int[] z = new int[500];

		for (int i = 0; i < frameCount; i++) {
			SeqFrame frame = instances[header.get2U()] = new SeqFrame();
			frame.delay = del.get1U();
			frame.skeleton = skeleton;

			int baseCount = header.get1U();
			int lastBase = -1;
			int length = 0;
			for (int base = 0; base < baseCount; base++) {
				int flags = tran1.get1U();

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
					x[length] = tran2.getSmart();
				} else {
					x[length] = defaultValue;
				}

				if ((flags & 2) != 0) {
					y[length] = tran2.getSmart();
				} else {
					y[length] = defaultValue;
				}

				if ((flags & 4) != 0) {
					z[length] = tran2.getSmart();
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

}
