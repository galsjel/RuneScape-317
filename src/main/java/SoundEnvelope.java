/**
 * A {@link SoundEnvelope} is essentially a curve and can be visualized similarly to <a href="https://docs.unity3d.com/Manual/EditingCurves.html">unity curves</a>.
 * Their main function is to provide shapes for sound related modifiers to take the form of.
 *
 * @see SoundTone
 */
public class SoundEnvelope {

	public int length;
	public int[] shapeDelta;
	public int[] shapePeak;
	public int start;
	public int end;
	public int form;
	public int threshold;
	public int position;
	public int delta;
	public int amplitude;
	public int ticks;

	public SoundEnvelope() {
	}

	public void read(Buffer buffer) {
		form = buffer.get1U();
		start = buffer.get4();
		end = buffer.get4();
		readShape(buffer);
	}

	public void readShape(Buffer buffer) {
		length = buffer.get1U();
		shapeDelta = new int[length];
		shapePeak = new int[length];
		for (int i = 0; i < length; i++) {
			shapeDelta[i] = buffer.get2U();
			shapePeak[i] = buffer.get2U();
		}
	}

	public void reset() {
		threshold = 0;
		position = 0;
		delta = 0;
		amplitude = 0;
		ticks = 0;
	}

	public int evaluate(int delta) {
		if (ticks >= threshold) {
			amplitude = shapePeak[position++] << 15;
			if (position >= length) {
				position = length - 1;
			}
			threshold = (int) (((double) shapeDelta[position] / 65536D) * (double) delta);
			if (threshold > ticks) {
				this.delta = ((shapePeak[position] << 15) - amplitude) / (threshold - ticks);
			}
		}
		amplitude += this.delta;
		ticks++;
		return (amplitude - this.delta) >> 15;
	}

}
