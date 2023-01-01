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

	/**
	 * The amount of frames in this {@link SeqType}.
	 */
	public int frameCount;

	/**
	 * The list of {@link SeqTransform} indices indexed by Frame ID.
	 * @see SeqTransform
	 */
	public int[] transformIndices;

	/**
	 * Auxiliary transform indices appear to only be used by a {@link Component} of type <code>6</code> as seen in {@link Game#drawParentComponent(Component, int, int, int)}.
	 */
	public int[] auxiliaryTransformIndices;

	/**
	 * A list of durations indexed by Frame ID.
	 */
	public int[] frameDuration;

	/**
	 * The number of frames from the end of this {@link SeqType} used for looping.
	 */
	public int loopFrameCount = -1;

	/**
	 * Used to determine which transform bases a primary frame is allowed to use, and secondary not.
	 * @see Model#applyTransforms(int, int, int[])
	 */
	public int[] mask;

	/**
	 * Adds additional space to the render bounds.
	 * @see Scene#addTemporary(Entity, int, int, int, int, int, int, boolean, int)
	 */
	public boolean forwardRenderPadding = false;

	/**
	 * The priority.
	 */
	public int priority = 5;

	/**
	 * Allows this {@link SeqType} to override the right hand of a {@link PlayerEntity}.
	 * @see PlayerEntity#getSequencedModel()
	 */
	public int rightHandOverride = -1;

	/**
	 * Allows this {@link SeqType} to override the left hand of a {@link PlayerEntity}.
	 * @see PlayerEntity#getSequencedModel()
	 */
	public int leftHandOverride = -1;

	/**
	 * How many times this seq is allowed to loop before stopping.
	 */
	public int loopCount = 99;

	/**
	 * If 0, causes faster movement, allows looking at target
	 * If 1, pause while moving
	 * If 2, does not look at target, continues playing during movement
	 * @see Game#updateMovement(PathingEntity)
	 */
	public int moveStyle = -1;
	
	/**
	 * If 0, allows looking at a target
	 * If 1, stops playing on move
	 * If 2, does not look at target, continues playing during movement
	 * @see Game#updateMovement(PathingEntity)
	 */
	public int idleStyle = -1;

	/**
	 * If 0, restarts the sequence if already playing
	 * If 1, does not restart if already playing
	 * @see Game#method86(Buffer)
	 */
	public int replayStyle = 1;

	public int unusedInt;

	public SeqType() {
	}

	public int getFrameDuration(int frame) {
		int duration = frameDuration[frame];

		if (duration == 0) {
			SeqTransform transform = SeqTransform.get(transformIndices[frame]);
			if (transform != null) {
				duration = frameDuration[frame] = transform.delay;
			}
		}

		if (duration == 0) {
			duration = 1;
		}

		return duration;
	}

	public void load(Buffer buffer) {
		do {
			int op = buffer.get1U();
			if (op == 0) {
				break;
			} else if (op == 1) {
				frameCount = buffer.get1U();
				transformIndices = new int[frameCount];
				auxiliaryTransformIndices = new int[frameCount];
				frameDuration = new int[frameCount];
				for (int f = 0; f < frameCount; f++) {
					transformIndices[f] = buffer.get2U();
					auxiliaryTransformIndices[f] = buffer.get2U();
					if (auxiliaryTransformIndices[f] == 65535) {
						auxiliaryTransformIndices[f] = -1;
					}
					frameDuration[f] = buffer.get2U();
				}
			} else if (op == 2) {
				loopFrameCount = buffer.get2U();
			} else if (op == 3) {
				int count = buffer.get1U();
				mask = new int[count + 1];
				for (int l = 0; l < count; l++) {
					mask[l] = buffer.get1U();
				}
				mask[count] = 9999999;
			} else if (op == 4) {
				forwardRenderPadding = true;
			} else if (op == 5) {
				priority = buffer.get1U();
			} else if (op == 6) {
				rightHandOverride = buffer.get2U();
			} else if (op == 7) {
				leftHandOverride = buffer.get2U();
			} else if (op == 8) {
				loopCount = buffer.get1U();
			} else if (op == 9) {
				moveStyle = buffer.get1U();
			} else if (op == 10) {
				idleStyle = buffer.get1U();
			} else if (op == 11) {
				replayStyle = buffer.get1U();
			} else if (op == 12) {
				unusedInt = buffer.get4();
			} else {
				System.out.println("Error unrecognised seq config code: " + op);
			}
		} while (true);

		if (frameCount == 0) {
			frameCount = 1;
			transformIndices = new int[1];
			transformIndices[0] = -1;
			auxiliaryTransformIndices = new int[1];
			auxiliaryTransformIndices[0] = -1;
			frameDuration = new int[1];
			frameDuration[0] = -1;
		}

		if (moveStyle == -1) {
			if (mask != null) {
				moveStyle = 2;
			} else {
				moveStyle = 0;
			}
		}

		if (idleStyle == -1) {
			if (mask != null) {
				idleStyle = 2;
				return;
			}
			idleStyle = 0;
		}
	}

}
