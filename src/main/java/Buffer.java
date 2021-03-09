// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Buffer extends DoublyLinkedListNode {

	public static final LinkedList bufferPool0 = new LinkedList();
	public static final LinkedList bufferPool1 = new LinkedList();
	public static final LinkedList bufferPool2 = new LinkedList();
	private static final int[] BITMASK = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1};
	public static int bufferPoolSize0;
	public static int bufferPoolSize1;
	public static int bufferPoolSize2;

	public static Buffer create(int sizeType) {
		synchronized (bufferPool1) {
			Buffer buffer = null;

			if ((sizeType == 0) && (bufferPoolSize0 > 0)) {
				bufferPoolSize0--;
				buffer = (Buffer) bufferPool0.method251();
			} else if ((sizeType == 1) && (bufferPoolSize1 > 0)) {
				bufferPoolSize1--;
				buffer = (Buffer) bufferPool1.method251();
			} else if ((sizeType == 2) && (bufferPoolSize2 > 0)) {
				bufferPoolSize2--;
				buffer = (Buffer) bufferPool2.method251();
			}

			if (buffer != null) {
				buffer.position = 0;
				return buffer;
			}
		}

		Buffer buffer = new Buffer();
		buffer.position = 0;

		if (sizeType == 0) {
			buffer.data = new byte[100];
		} else if (sizeType == 1) {
			buffer.data = new byte[5000];
		} else {
			buffer.data = new byte[30000];
		}

		return buffer;
	}

	public byte[] data;
	public int position;
	public int bitPosition;
	public ISAACCipher cipher;

	public Buffer() {
	}

	public Buffer(byte[] src) {
		data = src;
		position = 0;
	}

	public void putOp(int i) {
		data[position++] = (byte) (i + cipher.method246());
	}

	public void putSize8(int i) {
		data[position - i - 1] = (byte) i;
	}

	public void put8(int i) {
		data[position++] = (byte) i;
	}

	public void put8C(int i) {
		data[position++] = (byte) (-i);
	}

	public void put8S(int j) {
		data[position++] = (byte) (128 - j);
	}

	public void put16(int i) {
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put16A(int j) {
		data[position++] = (byte) (j >> 8);
		data[position++] = (byte) (j + 128);
	}

	public void put16LE(int i) {
		data[position++] = (byte) i;
		data[position++] = (byte) (i >> 8);
	}

	public void put16LEA(int j) {
		data[position++] = (byte) (j + 128);
		data[position++] = (byte) (j >> 8);
	}

	public void put24(int i) {
		data[position++] = (byte) (i >> 16);
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put32(int i) {
		data[position++] = (byte) (i >> 24);
		data[position++] = (byte) (i >> 16);
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put32LE(int j) {
		data[position++] = (byte) j;
		data[position++] = (byte) (j >> 8);
		data[position++] = (byte) (j >> 16);
		data[position++] = (byte) (j >> 24);
	}

	public void put64(long l) {
		data[position++] = (byte) (int) (l >> 56);
		data[position++] = (byte) (int) (l >> 48);
		data[position++] = (byte) (int) (l >> 40);
		data[position++] = (byte) (int) (l >> 32);
		data[position++] = (byte) (int) (l >> 24);
		data[position++] = (byte) (int) (l >> 16);
		data[position++] = (byte) (int) (l >> 8);
		data[position++] = (byte) (int) l;
	}

	public void putString(String s) {
		put(s.getBytes(StandardCharsets.US_ASCII));
		put8('\n');
	}

	public void put(byte[] src, int len, int off) {
		System.arraycopy(src, off, data, position, len);
		position += len;
	}

	public void put(byte[] src) {
		put(src, 0, src.length);
	}

	public void putA(byte[] src, int off, int len) {
		for (int i = (off + len) - 1; i >= off; i--) {
			data[position++] = (byte) (src[i] + 128);
		}
	}

	public byte get8() {
		return data[position++];
	}

	public byte get8C() {
		return (byte) (-data[position++]);
	}

	public byte get8S() {
		return (byte) (128 - data[position++]);
	}

	public int getU8() {
		return data[position++] & 0xff;
	}

	public int getU8A() {
		return (data[position++] - 128) & 0xff;
	}

	public int getU8C() {
		return -data[position++] & 0xff;
	}

	public int getU8S() {
		return (128 - data[position++]) & 0xff;
	}

	public int getSmart() {
		int i = data[position] & 0xff;
		if (i < 0x80) {
			return getU8() - 0x40;
		} else {
			return getU16() - 0xc000;
		}
	}

	public int getUSmart() {
		int i = data[position] & 0xff;
		if (i < 0x80) {
			return getU8();
		} else {
			return getU16() - 0x7fff;
		}
	}

	public int getU16() {
		position += 2;
		return ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int getU16A() {
		position += 2;
		return ((data[position - 2] & 0xff) << 8) + ((data[position - 1] - 128) & 0xff);
	}

	public int getU16LE() {
		position += 2;
		return ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
	}

	public int getU16LEA() {
		position += 2;
		return ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
	}

	public int get16() {
		position += 2;
		int i = ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
		if (i > 0x7fff) {
			i -= 0x10000;
		}
		return i;
	}

	public int get16LE() {
		position += 2;
		int j = ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
		if (j > 0x7fff) {
			j -= 0x10000;
		}
		return j;
	}

	public int get16LEA() {
		position += 2;
		int j = ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
		if (j > 0x7fff) {
			j -= 0x10000;
		}
		return j;
	}

	public int get24() {
		position += 3;
		return ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int get32() {
		position += 4;
		return ((data[position - 4] & 0xff) << 24) + ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int get32ME() {
		position += 4;
		return ((data[position - 3] & 0xff) << 24) + ((data[position - 4] & 0xff) << 16) + ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
	}

	public int get32RME() {
		position += 4;
		return ((data[position - 2] & 0xff) << 24) + ((data[position - 1] & 0xff) << 16) + ((data[position - 4] & 0xff) << 8) + (data[position - 3] & 0xff);
	}

	public long get64() {
		long l = (long) get32() & 0xffffffffL;
		long l1 = (long) get32() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public String getString() {
		int start = position;
		while (data[position++] != '\n') {
		}
		return new String(data, start, position - start - 1);
	}

	public byte[] getStringBytes() {
		int start = position;
		while (data[position++] != '\n') {
		}
		byte[] raw = new byte[position - start - 1];
		for (int j = start; j < (position - 1); j++) {
			raw[j - start] = data[j];
		}
		return raw;
	}

	public void get(byte[] dst, int off, int len) {
		System.arraycopy(data, position, dst, off, len);
		position += len;
	}

	public void getReversed(byte[] dst, int off, int len) {
		for (int i = (off + len) - 1; i >= off; i--) {
			dst[i] = data[position++];
		}
	}

	public void accessBits() {
		bitPosition = position * 8;
	}

	public int getN(int n) {
		int bytePos = bitPosition >> 3;
		int remaining = 8 - (bitPosition & 7);
		int value = 0;
		bitPosition += n;
		for (; n > remaining; remaining = 8) {
			value += (data[bytePos++] & BITMASK[remaining]) << (n - remaining);
			n -= remaining;
		}
		if (n == remaining) {
			value += data[bytePos] & BITMASK[remaining];
		} else {
			value += (data[bytePos] >> (remaining - n)) & BITMASK[n];
		}
		return value;
	}

	public void accessBytes() {
		position = (bitPosition + 7) / 8;
	}

	public void encrypt(BigInteger exponent, BigInteger modulus) {
		int length = position;
		position = 0;
		byte[] raw = new byte[length];
		get(raw, 0, length);
		byte[] encrypted = new BigInteger(raw).modPow(exponent, modulus).toByteArray();
		position = 0;
		put8(encrypted.length);
		put(encrypted, encrypted.length, 0);
	}

}
