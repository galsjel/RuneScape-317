import org.apache.commons.math3.random.ISAACRandom;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * A {@link Buffer} encapsulates a finite amount of data and provides methods for reading and writing to that data.
 * The <code>put()</code> and <code>get()</code> methods describe the type of data they use by attributes in their
 * suffix. The naming convention is <code>put[type][U:unsigned][endianness][modifier]</code>.
 * <p>
 * <b>Types:</b><br/>
 * # — the number of bytes<br/>
 * Op — 1 byte which is modified by the {@link #random} number generator.<br/>
 * Smart — 1 or 2 byte value [-16384...16383]<br/>
 * USmart — 1 or 2 byte value [0...32768]<br/>
 * String — {@link StandardCharsets#ISO_8859_1} encoding delimited by a newline character (\n)<br/>
 * <p>
 * <b><a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a>:</b><br/>
 * LE — little endian (DCBA)<br/>
 * ME — middle endian (CDAB) <br/>
 * RME — reversed middle endian (BADC)<br/>
 * Note: ME and RME are not standard, and are a form of obfuscation similar to the <i>modifier</i> attribute.<br/>
 * <p>
 * <b>Modifiers:</b><br/>
 * A — put(v + 128) — get returns (v - 128)<br/>
 * C — put(-v) — get returns (-v)<br/>
 * S — put(128 - v) — get returns (128 - v)<br/>
 */
public class Buffer extends DoublyLinkedList.Node {

	private static final int[] BITMASK = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1};

	public static Buffer create(int sizeType) {
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
	public ISAACRandom random;

	public Buffer() {
	}

	public Buffer(byte[] src) {
		data = src;
		position = 0;
	}

	public void putOp(int i) {
		data[position++] = (byte) (i + random.nextInt());
	}

	public void putSize1(int i) {
		data[position - i - 1] = (byte) i;
	}

	public void put1(int i) {
		data[position++] = (byte) i;
	}

	public void put1C(int i) {
		data[position++] = (byte) (-i);
	}

	public void put1S(int j) {
		data[position++] = (byte) (128 - j);
	}

	public void put2(int i) {
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put2A(int j) {
		data[position++] = (byte) (j >> 8);
		data[position++] = (byte) (j + 128);
	}

	public void put2LE(int i) {
		data[position++] = (byte) i;
		data[position++] = (byte) (i >> 8);
	}

	public void put2LEA(int j) {
		data[position++] = (byte) (j + 128);
		data[position++] = (byte) (j >> 8);
	}

	public void put3(int i) {
		data[position++] = (byte) (i >> 16);
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put4(int i) {
		data[position++] = (byte) (i >> 24);
		data[position++] = (byte) (i >> 16);
		data[position++] = (byte) (i >> 8);
		data[position++] = (byte) i;
	}

	public void put4LE(int j) {
		data[position++] = (byte) j;
		data[position++] = (byte) (j >> 8);
		data[position++] = (byte) (j >> 16);
		data[position++] = (byte) (j >> 24);
	}

	public void put8(long l) {
		data[position++] = (byte) (int) (l >> 56);
		data[position++] = (byte) (int) (l >> 48);
		data[position++] = (byte) (int) (l >> 40);
		data[position++] = (byte) (int) (l >> 32);
		data[position++] = (byte) (int) (l >> 24);
		data[position++] = (byte) (int) (l >> 16);
		data[position++] = (byte) (int) (l >> 8);
		data[position++] = (byte) (int) l;
	}

	public void put(String s) {
		put(s.getBytes(StandardCharsets.ISO_8859_1));
		put1('\n');
	}

	public void put(byte[] src, int off, int len) {
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

	public byte get1() {
		return data[position++];
	}

	public byte get1C() {
		return (byte) (-data[position++]);
	}

	public byte get1S() {
		return (byte) (128 - data[position++]);
	}

	public int get1U() {
		return data[position++] & 0xff;
	}

	public int get1UA() {
		return (data[position++] - 128) & 0xff;
	}

	public int get1UC() {
		return -data[position++] & 0xff;
	}

	public int get1US() {
		return (128 - data[position++]) & 0xff;
	}

	/**
	 * Gets a 1 or 2 byte varint which has the range [-16384...16383].
	 *
	 * @return the value.
	 */
	public int getSmart() {
		int i = data[position] & 0xff;
		if (i < 0x80) {
			return get1U() - 0x40;
		} else {
			return get2U() - 0xC000;
		}
	}

	/**
	 * Gets a 1 or 2 byte varint which has the range [0...32768].
	 *
	 * @return the value.
	 */
	public int getSmartU() {
		int i = data[position] & 0xff;
		if (i < 0x80) {
			return get1U();
		} else {
			return get2U() - 0x8000;
		}
	}

	public int get2U() {
		position += 2;
		return ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int get2UA() {
		position += 2;
		return ((data[position - 2] & 0xff) << 8) + ((data[position - 1] - 128) & 0xff);
	}

	public int get2ULE() {
		position += 2;
		return ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
	}

	public int get2ULEA() {
		position += 2;
		return ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
	}

	public int get2() {
		position += 2;
		int i = ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int get2LE() {
		position += 2;
		int j = ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
		if (j > 0x7fff) {
			j -= 0x10000;
		}
		return j;
	}

	public int get2LEA() {
		position += 2;
		int j = ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
		if (j > 0x7fff) {
			j -= 0x10000;
		}
		return j;
	}

	public int get3() {
		position += 3;
		return ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int get4() {
		position += 4;
		return ((data[position - 4] & 0xff) << 24) + ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
	}

	public int get4ME() {
		position += 4;
		return ((data[position - 3] & 0xff) << 24) + ((data[position - 4] & 0xff) << 16) + ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
	}

	public int get4RME() {
		position += 4;
		return ((data[position - 2] & 0xff) << 24) + ((data[position - 1] & 0xff) << 16) + ((data[position - 4] & 0xff) << 8) + (data[position - 3] & 0xff);
	}

	public long get8() {
		long l = (long) get4() & 0xffffffffL;
		long l1 = (long) get4() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public String getString() {
		int start = position;
		while (data[position++] != '\n') {
		}
		return new String(data, start, position - start - 1);
	}

	public byte[] getStringRaw() {
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

	public int getBits(int n) {
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
		put1(encrypted.length);
		put(encrypted, 0, encrypted.length);
	}

}
