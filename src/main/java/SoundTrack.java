public class SoundTrack {

	public static final SoundTrack[] tracks = new SoundTrack[5000];
	public static final int[] delays = new int[5000];
	public static byte[] buffer;
	public static Packet packet;

	public static void method240(Packet src) {
		buffer = new byte[441000];

		SoundTrack.packet = new Packet(buffer);
		SoundTone.init();

		do {
			int id = src.get2U();
			if (id == 65535) {
				return;
			}
			tracks[id] = new SoundTrack();
			tracks[id].read(src);
			delays[id] = tracks[id].trim();
		} while (true);
	}

	public static Packet generate(int loopCount, int id) {
		if (tracks[id] != null) {
			SoundTrack track = tracks[id];
			return track.getWaveform(loopCount);
		} else {
			return null;
		}
	}

	public final SoundTone[] tones = new SoundTone[10];
	public int loopBegin;
	public int loopEnd;

	public SoundTrack() {
	}

	public void read(Packet packet) {
		for (int tone = 0; tone < 10; tone++) {
			if (packet.get1U() != 0) {
				packet.position--;
				tones[tone] = new SoundTone();
				tones[tone].read(packet);
			}
		}
		loopBegin = packet.get2U();
		loopEnd = packet.get2U();
	}

	public int trim() {
		int start = 9999999;
		for (int tone = 0; tone < 10; tone++) {
			if ((tones[tone] != null) && ((tones[tone].start / 20) < start)) {
				start = tones[tone].start / 20;
			}
		}

		if ((loopBegin < loopEnd) && ((loopBegin / 20) < start)) {
			start = loopBegin / 20;
		}

		if ((start == 9999999) || (start == 0)) {
			return 0;
		}

		for (int tone = 0; tone < 10; tone++) {
			if (tones[tone] != null) {
				tones[tone].start -= start * 20;
			}
		}
		if (loopBegin < loopEnd) {
			loopBegin -= start * 20;
			loopEnd -= start * 20;
		}
		return start;
	}

	/**
	 * <a href="http://soundfile.sapp.org/doc/WaveFormat/">WaveFormat</a>
	 *
	 * @param loopCount the loop count.
	 * @return the {@link Packet} containing the waveform data.
	 */
	public Packet getWaveform(int loopCount) {
		int length = generate(loopCount);
		packet.position = 0;
		packet.put4(0x52494646); // "RIFF" ChunkID
		packet.put4LE(36 + length); // ChunkSize
		packet.put4(0x57415645); // "WAVE" format
		packet.put4(0x666d7420); // "fmt " chunk id
		packet.put4LE(16); // chunk size
		packet.put2LE(1); // audio format
		packet.put2LE(1);  // num channels
		packet.put4LE(22050); // sample rate
		packet.put4LE(22050); // byte rate
		packet.put2LE(1); // block align
		packet.put2LE(8); // bits per sample
		packet.put4(0x64617461); // "data"
		packet.put4LE(length);
		packet.position += length;
		return packet;
	}

	public int generate(int loopCount) {
		int duration = 0;

		for (int tone = 0; tone < 10; tone++) {
			if ((tones[tone] != null) && ((tones[tone].length + tones[tone].start) > duration)) {
				duration = tones[tone].length + tones[tone].start;
			}
		}

		if (duration == 0) {
			return 0;
		}

		int sampleCount = (22050 * duration) / 1000;
		int loopStart = (22050 * loopBegin) / 1000;
		int loopStop = (22050 * loopEnd) / 1000;

		if ((loopStart < 0) || (loopStop < 0) || (loopStop > sampleCount) || (loopStart >= loopStop)) {
			loopCount = 0;
		}

		int totalSampleCount = sampleCount + ((loopStop - loopStart) * (loopCount - 1));

		for (int sample = 44; sample < (totalSampleCount + 44); sample++) {
			buffer[sample] = -128;
		}

		for (int tone = 0; tone < 10; tone++) {
			if (tones[tone] == null) {
				continue;
			}

			int toneSampleCount = (tones[tone].length * 22050) / 1000;
			int start = (tones[tone].start * 22050) / 1000;
			int[] samples = tones[tone].generate(toneSampleCount, tones[tone].length);

			for (int sample = 0; sample < toneSampleCount; sample++) {
				buffer[sample + start + 44] += (byte) (samples[sample] >> 8);
			}
		}

		if (loopCount > 1) {
			// All of these 44's are because we're avoiding the area where the WAV header will be written.
			loopStart += 44;
			loopStop += 44;
			sampleCount += 44;

			// Moves the end of the sound (after the loops) to the true end of the buffer.
			int endOffset = (totalSampleCount += 44) - sampleCount;
			for (int sample = sampleCount - 1; sample >= loopStop; sample--) {
				buffer[sample + endOffset] = buffer[sample];
			}

			// Duplicates loop area.
			for (int loop = 1; loop < loopCount; loop++) {
				int offset = (loopStop - loopStart) * loop;
				for (int sample = loopStart; sample < loopStop; sample++) {
					buffer[sample + offset] = buffer[sample];
				}
			}

			totalSampleCount -= 44;
		}
		return totalSampleCount;
	}

}
