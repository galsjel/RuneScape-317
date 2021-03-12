/**
 * https://www.rune-server.ee/runescape-development/rs2-client/snippets/421977-sound-effects.html
 * https://ccrma.stanford.edu/~jos/filters/Direct_Form_I.html
 * https://en.wikipedia.org/wiki/Digital_biquad_filter
 */
public class SoundFilter {

	public static final float[][] coefficient = new float[2][8];
	public static final int[][] coefficient16 = new int[2][8];
	public static float unity;
	public static int unity16;
	public final int[] pairs = new int[2];
	public final int[][][] frequencies = new int[2][2][4];
	public final int[][][] ranges = new int[2][2][4];
	public final int[] unities = new int[2];

	public SoundFilter() {
	}

	public float gain(int direction, int pair, float delta) {
		float g = (float) ranges[direction][0][pair] + (delta * (float) (ranges[direction][1][pair] - ranges[direction][0][pair]));
		g *= 0.001525879F;
		return 1.0F - (float) Math.pow(10D, -g / 20F);
	}

	public float normalize(float f) {
		return (32.7032F * (float) Math.pow(2D, f) * 3.141593F) / 11025F;
	}

	public float phase(int direction, int pair, float delta) {
		float f1 = (float) frequencies[direction][0][pair] + (delta * (float) (frequencies[direction][1][pair] - frequencies[direction][0][pair]));
		f1 *= 0.0001220703F;
		return normalize(f1);
	}

	public int evaluate(int direction, float delta) {
		if (direction == 0) {
			float u = (float) unities[0] + ((float) (unities[1] - unities[0]) * delta);
			u *= 0.003051758F;
			unity = (float) Math.pow(0.10000000000000001D, u / 20F);
			unity16 = (int) (unity * 65536F);
		}

		if (pairs[direction] == 0) {
			return 0;
		}

		float u = gain(direction, 0, delta);

		coefficient[direction][0] = -2F * u * (float) Math.cos(phase(direction, 0, delta));
		coefficient[direction][1] = u * u;

		for (int pair = 1; pair < pairs[direction]; pair++) {
			float g = gain(direction, pair, delta);
			float a = -2F * g * (float) Math.cos(phase(direction, pair, delta));
			float b = g * g;

			coefficient[direction][(pair * 2) + 1] = coefficient[direction][(pair * 2) - 1] * b;
			coefficient[direction][pair * 2] = (coefficient[direction][(pair * 2) - 1] * a) + (coefficient[direction][(pair * 2) - 2] * b);

			for (int j = (pair * 2) - 1; j >= 2; j--) {
				coefficient[direction][j] += (coefficient[direction][j - 1] * a) + (coefficient[direction][j - 2] * b);
			}

			coefficient[direction][1] += (coefficient[direction][0] * a) + b;
			coefficient[direction][0] += a;
		}

		if (direction == 0) {
			for (int l = 0; l < (pairs[0] * 2); l++) {
				coefficient[0][l] *= unity;
			}
		}

		for (int pair = 0; pair < (pairs[direction] * 2); pair++) {
			coefficient16[direction][pair] = (int) (coefficient[direction][pair] * 65536F);
		}

		return pairs[direction] * 2;
	}

	public void read(Buffer buffer, SoundEnvelope envelope) {
		int count = buffer.get1U();
		pairs[0] = count >> 4;
		pairs[1] = count & 0xf;

		if (count != 0) {
			unities[0] = buffer.get2U();
			unities[1] = buffer.get2U();

			int migration = buffer.get1U();

			for (int direction = 0; direction < 2; direction++) {
				for (int pair = 0; pair < pairs[direction]; pair++) {
					frequencies[direction][0][pair] = buffer.get2U();
					ranges[direction][0][pair] = buffer.get2U();
				}
			}

			for (int direction = 0; direction < 2; direction++) {
				for (int pair = 0; pair < pairs[direction]; pair++) {
					if ((migration & (1 << (direction * 4) << pair)) != 0) {
						frequencies[direction][1][pair] = buffer.get2U();
						ranges[direction][1][pair] = buffer.get2U();
					} else {
						frequencies[direction][1][pair] = frequencies[direction][0][pair];
						ranges[direction][1][pair] = ranges[direction][0][pair];
					}
				}
			}

			if ((migration != 0) || (unities[1] != unities[0])) {
				envelope.readShape(buffer);
			}
		} else {
			unities[0] = unities[1] = 0;
		}
	}

}
