// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class SeqType {

	public static int count;
	public static SeqType[] instances;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("seq.dat"));
		count = buffer.get2U();
		if (instances == null) {
			instances = new SeqType[count];
		}

		for (int i = 0; i < count; i++) {
			if (instances[i] == null) {
				instances[i] = new SeqType();
			}
			instances[i].load(buffer);
		}
	}

	public int frameCount;
	public int[] primaryFrames;
	public int[] secondaryFrames;
	public int[] frameDelays;
	public int anInt356 = -1;
	public int[] anIntArray357;
	public boolean aBoolean358 = false;
	public int anInt359 = 5;
	public int anInt360 = -1;
	public int anInt361 = -1;
	public int anInt362 = 99;
	public int anInt363 = -1;
	public int anInt364 = -1;
	public int anInt365 = 2;
	public int unusedInt;

	public SeqType() {
	}

	public int getFrameDelay(int frame) {
		int delay = frameDelays[frame];
		if (delay == 0) {
			SeqFrame transform = SeqFrame.get(primaryFrames[frame]);

			if (transform != null) {
				delay = frameDelays[frame] = transform.delay;
			}
		}
		if (delay == 0) {
			delay = 1;
		}
		return delay;
	}

	public void load(Buffer buffer) {
		do {
			int op = buffer.get1U();
			if (op == 0) {
				break;
			} else if (op == 1) {
				frameCount = buffer.get1U();
				primaryFrames = new int[frameCount];
				secondaryFrames = new int[frameCount];
				frameDelays = new int[frameCount];
				for (int j = 0; j < frameCount; j++) {
					primaryFrames[j] = buffer.get2U();
					secondaryFrames[j] = buffer.get2U();
					if (secondaryFrames[j] == 65535) {
						secondaryFrames[j] = -1;
					}
					frameDelays[j] = buffer.get2U();
				}
			} else if (op == 2) {
				anInt356 = buffer.get2U();
			} else if (op == 3) {
				int k = buffer.get1U();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++) {
					anIntArray357[l] = buffer.get1U();
				}
				anIntArray357[k] = 9999999;
			} else if (op == 4) {
				aBoolean358 = true;
			} else if (op == 5) {
				anInt359 = buffer.get1U();
			} else if (op == 6) {
				anInt360 = buffer.get2U();
			} else if (op == 7) {
				anInt361 = buffer.get2U();
			} else if (op == 8) {
				anInt362 = buffer.get1U();
			} else if (op == 9) {
				anInt363 = buffer.get1U();
			} else if (op == 10) {
				anInt364 = buffer.get1U();
			} else if (op == 11) {
				anInt365 = buffer.get1U();
			} else if (op == 12) {
				unusedInt = buffer.get4();
			} else {
				System.out.println("Error unrecognised seq config code: " + op);
			}
		} while (true);

		if (frameCount == 0) {
			frameCount = 1;
			primaryFrames = new int[1];
			primaryFrames[0] = -1;
			secondaryFrames = new int[1];
			secondaryFrames[0] = -1;
			frameDelays = new int[1];
			frameDelays[0] = -1;
		}

		if (anInt363 == -1) {
			if (anIntArray357 != null) {
				anInt363 = 2;
			} else {
				anInt363 = 0;
			}
		}

		if (anInt364 == -1) {
			if (anIntArray357 != null) {
				anInt364 = 2;
				return;
			}
			anInt364 = 0;
		}
	}

}
