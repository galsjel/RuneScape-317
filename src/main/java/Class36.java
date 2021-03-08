// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Class36 {

	public static Class36[] aClass36Array635;
	public static boolean[] aBooleanArray643;
	public int anInt636;
	public SeqSkeleton aSkeleton_637;
	public int anInt638;
	public int[] anIntArray639;
	public int[] anIntArray640;
	public int[] anIntArray641;
	public int[] anIntArray642;

	public Class36() {
	}

	public static void method528(int i) {
		aClass36Array635 = new Class36[i + 1];
		aBooleanArray643 = new boolean[i + 1];
		for (int j = 0; j < i + 1; j++) {
			aBooleanArray643[j] = true;
		}
	}

	public static void method529(byte[] abyte0) {
		Buffer buffer = new Buffer(abyte0);
		buffer.anInt1406 = abyte0.length - 8;
		int i = buffer.method410();
		int j = buffer.method410();
		int k = buffer.method410();
		int l = buffer.method410();
		int i1 = 0;
		Buffer buffer_1 = new Buffer(abyte0);
		buffer_1.anInt1406 = i1;
		i1 += i + 2;
		Buffer class30_sub2_sub2_2 = new Buffer(abyte0);
		class30_sub2_sub2_2.anInt1406 = i1;
		i1 += j;
		Buffer buffer_3 = new Buffer(abyte0);
		buffer_3.anInt1406 = i1;
		i1 += k;
		Buffer buffer_4 = new Buffer(abyte0);
		buffer_4.anInt1406 = i1;
		i1 += l;
		Buffer buffer_5 = new Buffer(abyte0);
		buffer_5.anInt1406 = i1;
		SeqSkeleton skeleton = new SeqSkeleton(buffer_5);
		int k1 = buffer_1.method410();
		int[] ai = new int[500];
		int[] ai1 = new int[500];
		int[] ai2 = new int[500];
		int[] ai3 = new int[500];
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = buffer_1.method410();
			Class36 class36 = aClass36Array635[i2] = new Class36();
			class36.anInt636 = buffer_4.method408();
			class36.aSkeleton_637 = skeleton;
			int j2 = buffer_1.method408();
			int k2 = -1;
			int l2 = 0;
			for (int i3 = 0; i3 < j2; i3++) {
				int j3 = class30_sub2_sub2_2.method408();
				if (j3 > 0) {
					if (skeleton.anIntArray342[i3] != 0) {
						for (int l3 = i3 - 1; l3 > k2; l3--) {
							if (skeleton.anIntArray342[l3] != 0) {
								continue;
							}
							ai[l2] = l3;
							ai1[l2] = 0;
							ai2[l2] = 0;
							ai3[l2] = 0;
							l2++;
							break;
						}
					}
					ai[l2] = i3;
					char c = '\0';
					if (skeleton.anIntArray342[i3] == 3) {
						c = '\200';
					}
					if ((j3 & 1) != 0) {
						ai1[l2] = buffer_3.method421();
					} else {
						ai1[l2] = c;
					}
					if ((j3 & 2) != 0) {
						ai2[l2] = buffer_3.method421();
					} else {
						ai2[l2] = c;
					}
					if ((j3 & 4) != 0) {
						ai3[l2] = buffer_3.method421();
					} else {
						ai3[l2] = c;
					}
					k2 = i3;
					l2++;
					if (skeleton.anIntArray342[i3] == 5) {
						aBooleanArray643[i2] = false;
					}
				}
			}
			class36.anInt638 = l2;
			class36.anIntArray639 = new int[l2];
			class36.anIntArray640 = new int[l2];
			class36.anIntArray641 = new int[l2];
			class36.anIntArray642 = new int[l2];
			for (int k3 = 0; k3 < l2; k3++) {
				class36.anIntArray639[k3] = ai[k3];
				class36.anIntArray640[k3] = ai1[k3];
				class36.anIntArray641[k3] = ai2[k3];
				class36.anIntArray642[k3] = ai3[k3];
			}
		}
	}

	public static void method530() {
		aClass36Array635 = null;
	}

	public static Class36 method531(int j) {
		if (aClass36Array635 == null) {
			return null;
		} else {
			return aClass36Array635[j];
		}
	}

	public static boolean method532(int i) {
		return i == -1;
	}

}
