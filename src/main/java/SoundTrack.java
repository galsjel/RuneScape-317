public class SoundTrack {

    public static final SoundTrack[] tracks = new SoundTrack[5000];
    public static final int[] delays = new int[5000];
    public static byte[] waveBytes = new byte[441000];
    public static Buffer waveBuffer = new Buffer(waveBytes);

    public static void unpack(Buffer in) {
        SoundTone.init();

        do {
            int id = in.read16U();

            if (id == 65535) {
                return;
            }

            tracks[id] = new SoundTrack();
            tracks[id].read(in);
            delays[id] = tracks[id].trim();
        } while (true);
    }

    public static Buffer generate(int loopCount, int id) {
        if (tracks[id] != null) {
            SoundTrack track = tracks[id];
            return track.getWave(loopCount);
        } else {
            return null;
        }
    }

    public final SoundTone[] tones = new SoundTone[10];
    public int loopBegin;
    public int loopEnd;

    public SoundTrack() {
    }

    public void read(Buffer buffer) {
        for (int tone = 0; tone < 10; tone++) {
            if (buffer.read8U() != 0) {
                buffer.position--;
                tones[tone] = new SoundTone();
                tones[tone].read(buffer);
            }
        }
        loopBegin = buffer.read16U();
        loopEnd = buffer.read16U();
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
     * @return the {@link Buffer} containing the waveform data.
     */
    public Buffer getWave(int loopCount) {
        int length = generate(loopCount);
        waveBuffer.position = 0;
        waveBuffer.write32(0x52494646); // "RIFF" ChunkID
        waveBuffer.write32LE(36 + length); // ChunkSize
        waveBuffer.write32(0x57415645); // "WAVE" format
        waveBuffer.write32(0x666d7420); // "fmt " chunk id
        waveBuffer.write32LE(16); // chunk size
        waveBuffer.write16LE(1); // audio format
        waveBuffer.write16LE(1);  // num channels
        waveBuffer.write32LE(22050); // sample rate
        waveBuffer.write32LE(22050); // byte rate
        waveBuffer.write16LE(1); // block align
        waveBuffer.write16LE(8); // bits per sample
        waveBuffer.write32(0x64617461); // "data"
        waveBuffer.write32LE(length);
        waveBuffer.position += length;
        return waveBuffer;
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
            waveBytes[sample] = -128;
        }

        for (int tone = 0; tone < 10; tone++) {
            if (tones[tone] == null) {
                continue;
            }

            int toneSampleCount = (tones[tone].length * 22050) / 1000;
            int start = (tones[tone].start * 22050) / 1000;
            int[] samples = tones[tone].generate(toneSampleCount, tones[tone].length);

            for (int sample = 0; sample < toneSampleCount; sample++) {
                waveBytes[sample + start + 44] += (byte) (samples[sample] >> 8);
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
                waveBytes[sample + endOffset] = waveBytes[sample];
            }

            // Duplicates loop area.
            for (int loop = 1; loop < loopCount; loop++) {
                int offset = (loopStop - loopStart) * loop;
                for (int sample = loopStart; sample < loopStop; sample++) {
                    waveBytes[sample + offset] = waveBytes[sample];
                }
            }

            totalSampleCount -= 44;
        }
        return totalSampleCount;
    }

}
