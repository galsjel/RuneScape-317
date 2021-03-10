/**
 * A {@link SoundEnvelope} is essentially a curve and can be visualized similarly to <a href="https://docs.unity3d.com/Manual/EditingCurves.html">unity curves</a>.
 * Their main function is to provide shapes for sound related modifiers to take the form of.
 *
 * @see SoundTone
 */
public class SoundEnvelope {

	public int length;
	public int[] durations;
	public int[] peaks;
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

	public void read(Packet packet) {
		form = packet.get1U();
		start = packet.get4();
		end = packet.get4();
		readShape(packet);
	}

	public void readShape(Packet packet) {
		length = packet.get1U();
		durations = new int[length];
		peaks = new int[length];
		for (int i = 0; i < length; i++) {
			durations[i] = packet.get2U();
			peaks[i] = packet.get2U();
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
			amplitude = peaks[position++] << 15;
			if (position >= length) {
				position = length - 1;
			}
			threshold = (int) (((double) durations[position] / 65536D) * (double) delta);
			if (threshold > ticks) {
				this.delta = ((peaks[position] << 15) - amplitude) / (threshold - ticks);
			}
		}
		amplitude += this.delta;
		ticks++;
		return (amplitude - this.delta) >> 15;
	}

}
