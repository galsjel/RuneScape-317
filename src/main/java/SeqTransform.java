/**
 * A {@link SeqTransform} contains the {@link SeqSkeleton} and <code>x,y,z</code> parameters to transform a {@link Model}.
 *
 * @see Model#applyTransform(int)
 * @see Model#applyTransforms(int, int, int[])
 */
public class SeqTransform {

    public static SeqTransform[] instances;

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
        instances = new SeqTransform[count + 1];
    }

    /**
     * Nullifies the array of instances.
     */
    public static void unload() {
        instances = null;
    }

    /**
     * Gets the {@link SeqTransform}
     *
     * @param id the transform id.
     * @return the {@link SeqTransform} or <code>null</code> if it does not exist.
     */
    public static SeqTransform get(int id) {
        if ((instances == null) || (id < 0) || (id >= instances.length)) {
            return null;
        } else {
            return instances[id];
        }
    }

    /**
     * This method can read <i>multiple</i> {@link SeqTransform}s which means the input data can be all the transforms
     * related to the loaded skeleton.
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
        offset += offsets.readU16() + 2;

        tran1.position = offset;
        offset += offsets.readU16();

        tran2.position = offset;
        offset += offsets.readU16();

        del.position = offset;
        offset += offsets.readU16();

        skel.position = offset;

        SeqSkeleton skeleton = new SeqSkeleton(skel);

        int frameCount = header.readU16();
        int[] bases = new int[500];
        int[] x = new int[500];
        int[] y = new int[500];
        int[] z = new int[500];

        for (int i = 0; i < frameCount; i++) {
            SeqTransform transform = instances[header.readU16()] = new SeqTransform();
            transform.delay = del.readU8();
            transform.skeleton = skeleton;

            int baseCount = header.readU8();
            int lastBase = -1;
            int length = 0;

            for (int base = 0; base < baseCount; base++) {
                int flags = tran1.readU8();

                if (flags <= 0) {
                    continue;
                }

                if (skeleton.baseTypes[base] != SeqSkeleton.OP_BASE) {
                    // Look for any skipped ORIGIN bases and insert them into this transform.
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
                    x[length] = tran2.readSmart();
                } else {
                    x[length] = defaultValue;
                }

                if ((flags & 2) != 0) {
                    y[length] = tran2.readSmart();
                } else {
                    y[length] = defaultValue;
                }

                if ((flags & 4) != 0) {
                    z[length] = tran2.readSmart();
                } else {
                    z[length] = defaultValue;
                }

                lastBase = base;
                length++;
            }

            transform.length = length;
            transform.bases = new int[length];
            transform.x = new int[length];
            transform.y = new int[length];
            transform.z = new int[length];

            for (int j = 0; j < length; j++) {
                transform.bases[j] = bases[j];
                transform.x[j] = x[j];
                transform.y[j] = y[j];
                transform.z[j] = z[j];
            }
        }
    }

    /**
     * The skeleton associated to this transform.
     */
    public SeqSkeleton skeleton;
    /**
     * The delay in <code>ticks</code>.
     */
    public int delay;
    /**
     * The number of operations this transform performs.
     */
    public int length;
    /**
     * The list of bases this transform uses.
     */
    public int[] bases;
    /**
     * This transforms parameters.
     */
    public int[] x, y, z;

}
