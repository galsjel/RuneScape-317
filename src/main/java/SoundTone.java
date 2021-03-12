// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SoundTone {

	public static final int[] tmpPhases = new int[5];
	public static final int[] tmpDelays = new int[5];
	public static final int[] tmpVolumes = new int[5];
	public static final int[] tmpSemitones = new int[5];
	public static final int[] tmpStarts = new int[5];
	public static int[] buffer;
	public static int[] noise;
	public static int[] sin;

	public static void init() {
		noise = new int[32768];
		for (int i = 0; i < 32768; i++) {
			if (Math.random() > 0.5D) {
				noise[i] = 1;
			} else {
				noise[i] = -1;
			}
		}
		sin = new int[32768];
		for (int j = 0; j < 32768; j++) {
			sin[j] = (int) (Math.sin((double) j / 5215.1903000000002D) * 16384D);
		}
		buffer = new int[22050 * 10]; // 10 second buffer
	}

	public final int[] harmonicVolume = new int[5];
	public final int[] harmonicSemitone = new int[5];
	public final int[] harmonicDelay = new int[5];
	public SoundEnvelope frequencyBase;
	public SoundEnvelope amplitudeBase;
	public SoundEnvelope frequencyModRate;
	public SoundEnvelope frequencyModRange;
	public SoundEnvelope amplitudeModRate;
	public SoundEnvelope amplitudeModRange;
	public SoundEnvelope release;
	public SoundEnvelope attack;
	public int reverbDelay;
	public int reverbVolume = 100;
	public SoundFilter filter;
	public SoundEnvelope filterRange;
	public int length = 500;
	public int start;

	public SoundTone() {
	}

	public int[] generate(int sampleCount, int length) {
		for (int sample = 0; sample < sampleCount; sample++) {
			buffer[sample] = 0;
		}

		if (length < 10) {
			return buffer;
		}

		double samplesPerStep = (double) sampleCount / ((double) length + 0.0D);

		frequencyBase.reset();
		amplitudeBase.reset();

		int frequencyStart = 0;
		int frequencyDuration = 0;
		int frequencyPhase = 0;

		if (frequencyModRate != null) {
			frequencyModRate.reset();
			frequencyModRange.reset();
			frequencyStart = (int) (((double) (frequencyModRate.end - frequencyModRate.start) * 32.768) / samplesPerStep);
			frequencyDuration = (int) (((double) frequencyModRate.start * 32.768) / samplesPerStep);
		}

		int amplitudeStart = 0;
		int amplitudeDuration = 0;
		int amplitudePhase = 0;

		if (amplitudeModRate != null) {
			amplitudeModRate.reset();
			amplitudeModRange.reset();
			amplitudeStart = (int) (((double) (amplitudeModRate.end - amplitudeModRate.start) * 32.768) / samplesPerStep);
			amplitudeDuration = (int) (((double) amplitudeModRate.start * 32.768) / samplesPerStep);
		}

		for (int harmonic = 0; harmonic < 5; harmonic++) {
			if (harmonicVolume[harmonic] != 0) {
				tmpPhases[harmonic] = 0;
				tmpDelays[harmonic] = (int) ((double) harmonicDelay[harmonic] * samplesPerStep);
				tmpVolumes[harmonic] = (harmonicVolume[harmonic] << 14) / 100;
				tmpSemitones[harmonic] = (int) (((double) (frequencyBase.end - frequencyBase.start) * 32.768 * Math.pow(1.0057929410678534D, harmonicSemitone[harmonic])) / samplesPerStep);
				tmpStarts[harmonic] = (int) (((double) frequencyBase.start * 32.768) / samplesPerStep);
			}
		}

		for (int sample = 0; sample < sampleCount; sample++) {
			int frequency = frequencyBase.evaluate(sampleCount);
			int amplitude = amplitudeBase.evaluate(sampleCount);

			if (frequencyModRate != null) {
				int rate = frequencyModRate.evaluate(sampleCount);
				int range = frequencyModRange.evaluate(sampleCount);
				frequency += generate(frequencyModRate.form, frequencyPhase, range) >> 1;
				frequencyPhase += ((rate * frequencyStart) >> 16) + frequencyDuration;
			}

			if (amplitudeModRate != null) {
				int rate = amplitudeModRate.evaluate(sampleCount);
				int range = amplitudeModRange.evaluate(sampleCount);
				amplitude = (amplitude * ((generate(amplitudeModRate.form, amplitudePhase, range) >> 1) + 32768)) >> 15;
				amplitudePhase += ((rate * amplitudeStart) >> 16) + amplitudeDuration;
			}

			for (int harmonic = 0; harmonic < 5; harmonic++) {
				if (harmonicVolume[harmonic] != 0) {
					int position = sample + tmpDelays[harmonic];

					if (position < sampleCount) {
						buffer[position] += generate(frequencyBase.form, tmpPhases[harmonic], (amplitude * tmpVolumes[harmonic]) >> 15);
						tmpPhases[harmonic] += ((frequency * tmpSemitones[harmonic]) >> 16) + tmpStarts[harmonic];
					}
				}
			}
		}

		if (release != null) {
			release.reset();
			attack.reset();

			int counter = 0;
			boolean muted = true;

			for (int sample = 0; sample < sampleCount; sample++) {
				int releaseValue = release.evaluate(sampleCount);
				int attackValue = attack.evaluate(sampleCount);
				int threshold;

				if (muted) {
					threshold = release.start + (((release.end - release.start) * releaseValue) >> 8);
				} else {
					threshold = release.start + (((release.end - release.start) * attackValue) >> 8);
				}

				if ((counter += 256) >= threshold) {
					counter = 0;
					muted = !muted;
				}

				if (muted) {
					buffer[sample] = 0;
				}
			}
		}

		if ((reverbDelay > 0) && (reverbVolume > 0)) {
			int start = (int) ((double) reverbDelay * samplesPerStep);

			for (int sample = start; sample < sampleCount; sample++) {
				buffer[sample] += (buffer[sample - start] * reverbVolume) / 100;
			}
		}

		if ((filter.pairs[0] > 0) || (filter.pairs[1] > 0)) {
			filterRange.reset();

			int range = filterRange.evaluate(sampleCount + 1);
			int forward = filter.evaluate(0, (float) range / 65536F);
			int backward = filter.evaluate(1, (float) range / 65536F);

			if (sampleCount >= (forward + backward)) {
				int index = 0;
				int interval = backward;

				if (interval > (sampleCount - forward)) {
					interval = sampleCount - forward;
				}

				for (; index < interval; index++) {
					int sample = (int) (((long) buffer[index + forward] * (long) SoundFilter.unity16) >> 16);

					for (int offset = 0; offset < forward; offset++) {
						sample += (int) (((long) buffer[(index + forward) - 1 - offset] * (long) SoundFilter.coefficient16[0][offset]) >> 16);
					}

					for (int offset = 0; offset < index; offset++) {
						sample -= (int) (((long) buffer[index - 1 - offset] * (long) SoundFilter.coefficient16[1][offset]) >> 16);
					}

					buffer[index] = sample;
					range = filterRange.evaluate(sampleCount + 1);
				}

				interval = 128;

				do {
					if (interval > (sampleCount - forward)) {
						interval = sampleCount - forward;
					}

					for (; index < interval; index++) {
						int sample = (int) (((long) buffer[index + forward] * (long) SoundFilter.unity16) >> 16);

						for (int offset = 0; offset < forward; offset++) {
							sample += (int) (((long) buffer[(index + forward) - 1 - offset] * (long) SoundFilter.coefficient16[0][offset]) >> 16);
						}

						for (int offset = 0; offset < backward; offset++) {
							sample -= (int) (((long) buffer[index - 1 - offset] * (long) SoundFilter.coefficient16[1][offset]) >> 16);
						}

						buffer[index] = sample;
						range = filterRange.evaluate(sampleCount + 1);
					}

					if (index >= (sampleCount - forward)) {
						break;
					}

					forward = filter.evaluate(0, (float) range / 65536F);
					backward = filter.evaluate(1, (float) range / 65536F);
					interval += 128;
				} while (true);

				for (; index < sampleCount; index++) {
					int sample = 0;

					for (int offset = (index + forward) - sampleCount; offset < forward; offset++) {
						sample += (int) (((long) buffer[(index + forward) - 1 - offset] * (long) SoundFilter.coefficient16[0][offset]) >> 16);
					}

					for (int offset = 0; offset < backward; offset++) {
						sample -= (int) (((long) buffer[index - 1 - offset] * (long) SoundFilter.coefficient16[1][offset]) >> 16);
					}

					buffer[index] = sample;
					filterRange.evaluate(sampleCount + 1);
				}
			}
		}

		for (int sample = 0; sample < sampleCount; sample++) {
			if (buffer[sample] < -32768) {
				buffer[sample] = -32768;
			}
			if (buffer[sample] > 32767) {
				buffer[sample] = 32767;
			}
		}

		return buffer;
	}

	public int generate(int form, int phase, int amplitude) {
		if (form == 1) {
			if ((phase & 0x7fff) < 16384) {
				return amplitude;
			} else {
				return -amplitude;
			}
		} else if (form == 2) {
			return (sin[phase & 0x7fff] * amplitude) >> 14;
		} else if (form == 3) {
			return (((phase & 0x7fff) * amplitude) >> 14) - amplitude;
		} else if (form == 4) {
			return noise[(phase / 2607) & 0x7fff] * amplitude;
		} else {
			return 0;
		}
	}

	public void read(Buffer buffer) {
		frequencyBase = new SoundEnvelope();
		frequencyBase.read(buffer);
		amplitudeBase = new SoundEnvelope();
		amplitudeBase.read(buffer);

		if (buffer.get1U() != 0) {
			buffer.position--;
			frequencyModRate = new SoundEnvelope();
			frequencyModRate.read(buffer);
			frequencyModRange = new SoundEnvelope();
			frequencyModRange.read(buffer);
		}

		if (buffer.get1U() != 0) {
			buffer.position--;
			amplitudeModRate = new SoundEnvelope();
			amplitudeModRate.read(buffer);
			amplitudeModRange = new SoundEnvelope();
			amplitudeModRange.read(buffer);
		}

		if (buffer.get1U() != 0) {
			buffer.position--;
			release = new SoundEnvelope();
			release.read(buffer);
			attack = new SoundEnvelope();
			attack.read(buffer);
		}

		for (int i = 0; i < 10; i++) {
			int volume = buffer.getSmartU();

			if (volume == 0) {
				break;
			}

			harmonicVolume[i] = volume;
			harmonicSemitone[i] = buffer.getSmart();
			harmonicDelay[i] = buffer.getSmartU();
		}

		reverbDelay = buffer.getSmartU();
		reverbVolume = buffer.getSmartU();

		length = buffer.get2U();
		start = buffer.get2U();

		filter = new SoundFilter();
		filterRange = new SoundEnvelope();
		filter.read(buffer, filterRange);
	}

}
