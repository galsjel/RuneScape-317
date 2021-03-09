// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.math.BigInteger;

public class Buffer extends DoublyLinkedListNode {

	public static final int[] anIntArray1409 = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1};
	public static final LinkedList A_LIST___1414 = new LinkedList();
	public static final LinkedList A_LIST___1415 = new LinkedList();
	public static final LinkedList A_LIST___1416 = new LinkedList();
	public static final int[] anIntArray1408;
	public static int anInt1411;
	public static int anInt1412;
	public static int anInt1413;

	static {
		anIntArray1408 = new int[256];
		for (int j = 0; j < 256; j++) {
			int i = j;
			for (int k = 0; k < 8; k++) {
				if ((i & 1) == 1) {
					i = (i >>> 1) ^ 0xedb88320;
				} else {
					i >>>= 1;
				}
			}
			anIntArray1408[j] = i;
		}
	}

	public byte[] aByteArray1405;
	public int position;
	public int anInt1407;
	public ISAACCipher aISAACCipher_1410;

	public Buffer() {
	}

	public Buffer(byte[] abyte0) {
		aByteArray1405 = abyte0;
		position = 0;
	}

	public static Buffer method396(int i) {
		synchronized (A_LIST___1415) {
			Buffer class30_sub2_sub2_2 = null;
			if ((i == 0) && (anInt1411 > 0)) {
				anInt1411--;
				class30_sub2_sub2_2 = (Buffer) A_LIST___1414.method251();
			} else if ((i == 1) && (anInt1412 > 0)) {
				anInt1412--;
				class30_sub2_sub2_2 = (Buffer) A_LIST___1415.method251();
			} else if ((i == 2) && (anInt1413 > 0)) {
				anInt1413--;
				class30_sub2_sub2_2 = (Buffer) A_LIST___1416.method251();
			}
			if (class30_sub2_sub2_2 != null) {
				class30_sub2_sub2_2.position = 0;
				return class30_sub2_sub2_2;
			}
		}
		Buffer buffer_1 = new Buffer();
		buffer_1.position = 0;
		if (i == 0) {
			buffer_1.aByteArray1405 = new byte[100];
		} else if (i == 1) {
			buffer_1.aByteArray1405 = new byte[5000];
		} else {
			buffer_1.aByteArray1405 = new byte[30000];
		}
		return buffer_1;
	}

	public void method397(int i) {
		aByteArray1405[position++] = (byte) (i + aISAACCipher_1410.method246());
	}

	public void method398(int i) {
		aByteArray1405[position++] = (byte) i;
	}

	public void method399(int i) {
		aByteArray1405[position++] = (byte) (i >> 8);
		aByteArray1405[position++] = (byte) i;
	}

	public void method400(int i) {
		aByteArray1405[position++] = (byte) i;
		aByteArray1405[position++] = (byte) (i >> 8);
	}

	public void method401(int i) {
		aByteArray1405[position++] = (byte) (i >> 16);
		aByteArray1405[position++] = (byte) (i >> 8);
		aByteArray1405[position++] = (byte) i;
	}

	public void method402(int i) {
		aByteArray1405[position++] = (byte) (i >> 24);
		aByteArray1405[position++] = (byte) (i >> 16);
		aByteArray1405[position++] = (byte) (i >> 8);
		aByteArray1405[position++] = (byte) i;
	}

	public void method403(int j) {
		aByteArray1405[position++] = (byte) j;
		aByteArray1405[position++] = (byte) (j >> 8);
		aByteArray1405[position++] = (byte) (j >> 16);
		aByteArray1405[position++] = (byte) (j >> 24);
	}

	public void method404(long l) {
		aByteArray1405[position++] = (byte) (int) (l >> 56);
		aByteArray1405[position++] = (byte) (int) (l >> 48);
		aByteArray1405[position++] = (byte) (int) (l >> 40);
		aByteArray1405[position++] = (byte) (int) (l >> 32);
		aByteArray1405[position++] = (byte) (int) (l >> 24);
		aByteArray1405[position++] = (byte) (int) (l >> 16);
		aByteArray1405[position++] = (byte) (int) (l >> 8);
		aByteArray1405[position++] = (byte) (int) l;
	}

	public void method405(String s) {
		s.getBytes(0, s.length(), aByteArray1405, position);
		position += s.length();
		aByteArray1405[position++] = 10;
	}

	public void method406(byte[] abyte0, int i, int j) {
		for (int k = j; k < (j + i); k++) {
			aByteArray1405[position++] = abyte0[k];
		}
	}

	public void method407(int i) {
		aByteArray1405[position - i - 1] = (byte) i;
	}

	public int method408() {
		return aByteArray1405[position++] & 0xff;
	}

	public byte method409() {
		return aByteArray1405[position++];
	}

	public int method410() {
		position += 2;
		return ((aByteArray1405[position - 2] & 0xff) << 8) + (aByteArray1405[position - 1] & 0xff);
	}

	public int method411() {
		position += 2;
		int i = ((aByteArray1405[position - 2] & 0xff) << 8) + (aByteArray1405[position - 1] & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int method412() {
		position += 3;
		return ((aByteArray1405[position - 3] & 0xff) << 16) + ((aByteArray1405[position - 2] & 0xff) << 8) + (aByteArray1405[position - 1] & 0xff);
	}

	public int method413() {
		position += 4;
		return ((aByteArray1405[position - 4] & 0xff) << 24) + ((aByteArray1405[position - 3] & 0xff) << 16) + ((aByteArray1405[position - 2] & 0xff) << 8) + (aByteArray1405[position - 1] & 0xff);
	}

	public long method414() {
		long l = (long) method413() & 0xffffffffL;
		long l1 = (long) method413() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public String method415() {
		int i = position;
		while (aByteArray1405[position++] != 10) {
		}
		return new String(aByteArray1405, i, position - i - 1);
	}

	public byte[] method416() {
		int i = position;
		while (aByteArray1405[position++] != 10) {
		}
		byte[] abyte0 = new byte[position - i - 1];
		for (int j = i; j < (position - 1); j++) {
			abyte0[j - i] = aByteArray1405[j];
		}
		return abyte0;
	}

	public void method417(int i, int j, byte[] abyte0) {
		for (int l = j; l < (j + i); l++) {
			abyte0[l] = aByteArray1405[position++];
		}
	}

	public void method418() {
		anInt1407 = position * 8;
	}

	public int method419(int i) {
		int k = anInt1407 >> 3;
		int l = 8 - (anInt1407 & 7);
		int i1 = 0;
		anInt1407 += i;
		for (; i > l; l = 8) {
			i1 += (aByteArray1405[k++] & anIntArray1409[l]) << (i - l);
			i -= l;
		}
		if (i == l) {
			i1 += aByteArray1405[k] & anIntArray1409[l];
		} else {
			i1 += (aByteArray1405[k] >> (l - i)) & anIntArray1409[i];
		}
		return i1;
	}

	public void method420() {
		position = (anInt1407 + 7) / 8;
	}

	public int method421() {
		int i = aByteArray1405[position] & 0xff;
		if (i < 128) {
			return method408() - 64;
		} else {
			return method410() - 49152;
		}
	}

	public int method422() {
		int i = aByteArray1405[position] & 0xff;
		if (i < 128) {
			return method408();
		} else {
			return method410() - 32768;
		}
	}

	public void method423(BigInteger biginteger, BigInteger biginteger1) {
		int i = position;
		position = 0;
		byte[] abyte0 = new byte[i];
		method417(i, 0, abyte0);
		BigInteger biginteger2 = new BigInteger(abyte0);
		BigInteger biginteger3 = biginteger2.modPow(biginteger, biginteger1);
		byte[] abyte1 = biginteger3.toByteArray();
		position = 0;
		method398(abyte1.length);
		method406(abyte1, abyte1.length, 0);
	}

	public void method424(int i) {
		aByteArray1405[position++] = (byte) (-i);
	}

	public void method425(int j) {
		aByteArray1405[position++] = (byte) (128 - j);
	}

	public int method426() {
		return (aByteArray1405[position++] - 128) & 0xff;
	}

	public int method427() {
		return -aByteArray1405[position++] & 0xff;
	}

	public int method428() {
		return (128 - aByteArray1405[position++]) & 0xff;
	}

	public byte method429() {
		return (byte) (-aByteArray1405[position++]);
	}

	public byte method430() {
		return (byte) (128 - aByteArray1405[position++]);
	}

	public void method431(int i) {
		aByteArray1405[position++] = (byte) i;
		aByteArray1405[position++] = (byte) (i >> 8);
	}

	public void method432(int j) {
		aByteArray1405[position++] = (byte) (j >> 8);
		aByteArray1405[position++] = (byte) (j + 128);
	}

	public void method433(int j) {
		aByteArray1405[position++] = (byte) (j + 128);
		aByteArray1405[position++] = (byte) (j >> 8);
	}

	public int method434() {
		position += 2;
		return ((aByteArray1405[position - 1] & 0xff) << 8) + (aByteArray1405[position - 2] & 0xff);
	}

	public int method435() {
		position += 2;
		return ((aByteArray1405[position - 2] & 0xff) << 8) + ((aByteArray1405[position - 1] - 128) & 0xff);
	}

	public int method436() {
		position += 2;
		return ((aByteArray1405[position - 1] & 0xff) << 8) + ((aByteArray1405[position - 2] - 128) & 0xff);
	}

	public int method437() {
		position += 2;
		int j = ((aByteArray1405[position - 1] & 0xff) << 8) + (aByteArray1405[position - 2] & 0xff);
		if (j > 32767) {
			j -= 0x10000;
		}
		return j;
	}

	public int method438() {
		position += 2;
		int j = ((aByteArray1405[position - 1] & 0xff) << 8) + ((aByteArray1405[position - 2] - 128) & 0xff);
		if (j > 32767) {
			j -= 0x10000;
		}
		return j;
	}

	public int method439() {
		position += 4;
		return ((aByteArray1405[position - 2] & 0xff) << 24) + ((aByteArray1405[position - 1] & 0xff) << 16) + ((aByteArray1405[position - 4] & 0xff) << 8) + (aByteArray1405[position - 3] & 0xff);
	}

	public int method440() {
		position += 4;
		return ((aByteArray1405[position - 3] & 0xff) << 24) + ((aByteArray1405[position - 4] & 0xff) << 16) + ((aByteArray1405[position - 1] & 0xff) << 8) + (aByteArray1405[position - 2] & 0xff);
	}

	public void method441(int i, byte[] abyte0, int j) {
		for (int k = (i + j) - 1; k >= i; k--) {
			aByteArray1405[position++] = (byte) (abyte0[k] + 128);
		}
	}

	public void method442(int i, int j, byte[] abyte0) {
		for (int k = (j + i) - 1; k >= j; k--) {
			abyte0[k] = aByteArray1405[position++];
		}
	}

}
