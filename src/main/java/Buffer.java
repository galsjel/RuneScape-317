import org.apache.commons.math3.random.ISAACRandom;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * A {@link Buffer} encapsulates a finite amount of data and provides methods for reading and writing to that data.
 * The <code>write()</code> and <code>read()</code> methods describe the type of data they use by attributes in their
 * suffix. The naming convention is <code>(read|write)[type][U:unsigned][endianness][modifier]</code>.
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
 * A — put(v + 128) — read returns (v - 128)<br/>
 * C — put(-v) — read returns (-v)<br/>
 * S — put(128 - v) — read returns (128 - v)<br/>
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

    public void writeOp(int i) {
        data[position++] = (byte) (i + random.nextInt());
    }

    public void writeSize(int i) {
        data[position - i - 1] = (byte) i;
    }

    public void write8(int i) {
        data[position++] = (byte) i;
    }

    public void write8C(int i) {
        data[position++] = (byte) -i;
    }

    public void write8S(int j) {
        data[position++] = (byte) (128 - j);
    }

    public void write16(int i) {
        data[position++] = (byte) (i >> 8);
        data[position++] = (byte) i;
    }

    public void write16A(int j) {
        data[position++] = (byte) (j >> 8);
        data[position++] = (byte) (j + 128);
    }

    public void write16LE(int i) {
        data[position++] = (byte) i;
        data[position++] = (byte) (i >> 8);
    }

    public void write16LEA(int j) {
        data[position++] = (byte) (j + 128);
        data[position++] = (byte) (j >> 8);
    }

    public void write24(int i) {
        data[position++] = (byte) (i >> 16);
        data[position++] = (byte) (i >> 8);
        data[position++] = (byte) i;
    }

    public void write32(int i) {
        data[position++] = (byte) (i >> 24);
        data[position++] = (byte) (i >> 16);
        data[position++] = (byte) (i >> 8);
        data[position++] = (byte) i;
    }

    public void write32LE(int j) {
        data[position++] = (byte) j;
        data[position++] = (byte) (j >> 8);
        data[position++] = (byte) (j >> 16);
        data[position++] = (byte) (j >> 24);
    }

    public void write64(long l) {
        data[position++] = (byte) (int) (l >> 56);
        data[position++] = (byte) (int) (l >> 48);
        data[position++] = (byte) (int) (l >> 40);
        data[position++] = (byte) (int) (l >> 32);
        data[position++] = (byte) (int) (l >> 24);
        data[position++] = (byte) (int) (l >> 16);
        data[position++] = (byte) (int) (l >> 8);
        data[position++] = (byte) (int) l;
    }

    public void writeString(String s) {
        write(s.getBytes(StandardCharsets.ISO_8859_1));
        write8('\n');
    }

    public void write(byte[] src, int off, int len) {
        System.arraycopy(src, off, data, position, len);
        position += len;
    }

    public void write(byte[] src) {
        write(src, 0, src.length);
    }

    public void writeA(byte[] src, int off, int len) {
        for (int i = (off + len) - 1; i >= off; i--) {
            data[position++] = (byte) (src[i] + 128);
        }
    }

    public byte read() {
        return data[position++];
    }

    public byte read8C() {
        return (byte) -data[position++];
    }

    public byte read8S() {
        return (byte) (128 - data[position++]);
    }

    public int read8U() {
        return data[position++] & 0xff;
    }

    public int read8UA() {
        return (data[position++] - 128) & 0xff;
    }

    public int read8UC() {
        return -data[position++] & 0xff;
    }

    public int read8US() {
        return (128 - data[position++]) & 0xff;
    }

    /**
     * Gets a 1 or 2 byte varint which has the range [-16384...16383].
     *
     * @return the value.
     */
    public int readSmart() {
        int i = data[position] & 0xff;
        if (i < 128) {
            return read8U() - 64;
        } else {
            return read16U() - 49152;
        }
    }

    /**
     * Gets a 1 or 2 byte varint which has the range [0...32768].
     *
     * @return the value.
     */
    public int readSmartU() {
        int i = data[position] & 0xff;
        if (i < 128) {
            return read8U();
        } else {
            return read16U() - 32768;
        }
    }

    public int read16U() {
        position += 2;
        return ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
    }

    public int read16UA() {
        position += 2;
        return ((data[position - 2] & 0xff) << 8) + ((data[position - 1] - 128) & 0xff);
    }

    public int read16ULE() {
        position += 2;
        return ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
    }

    public int read16ULEA() {
        position += 2;
        return ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
    }

    public int read16() {
        position += 2;
        int i = ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int read16LE() {
        position += 2;
        int j = ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
        if (j > 0x7fff) {
            j -= 0x10000;
        }
        return j;
    }

    public int read16LEA() {
        position += 2;
        int j = ((data[position - 1] & 0xff) << 8) + ((data[position - 2] - 128) & 0xff);
        if (j > 0x7fff) {
            j -= 0x10000;
        }
        return j;
    }

    public int read24() {
        position += 3;
        return ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
    }

    public int read32() {
        position += 4;
        return ((data[position - 4] & 0xff) << 24) + ((data[position - 3] & 0xff) << 16) + ((data[position - 2] & 0xff) << 8) + (data[position - 1] & 0xff);
    }

    public int read32ME() {
        position += 4;
        return ((data[position - 3] & 0xff) << 24) + ((data[position - 4] & 0xff) << 16) + ((data[position - 1] & 0xff) << 8) + (data[position - 2] & 0xff);
    }

    public int read32RME() {
        position += 4;
        return ((data[position - 2] & 0xff) << 24) + ((data[position - 1] & 0xff) << 16) + ((data[position - 4] & 0xff) << 8) + (data[position - 3] & 0xff);
    }

    public long read64() {
        long l = (long) read32() & 0xffffffffL;
        long l1 = (long) read32() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public String readString() {
        int start = position;
        while (data[position++] != '\n') {
        }
        return new String(data, start, position - start - 1);
    }

    public byte[] readStringRaw() {
        int start = position;
        while (data[position++] != '\n') {
        }
        byte[] raw = new byte[position - start - 1];
        for (int j = start; j < (position - 1); j++) {
            raw[j - start] = data[j];
        }
        return raw;
    }

    public void read(byte[] dst, int off, int len) {
        System.arraycopy(data, position, dst, off, len);
        position += len;
    }

    public void read(byte[] dst) {
        read(dst, 0, dst.length);
    }

    public void readReversed(byte[] dst, int off, int len) {
        for (int i = (off + len) - 1; i >= off; i--) {
            dst[i] = data[position++];
        }
    }

    public void accessBits() {
        bitPosition = position * 8;
    }

    public int readN(int n) {
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
        read(raw, 0, length);
        byte[] encrypted = new BigInteger(raw).modPow(exponent, modulus).toByteArray();
        position = 0;
        write8(encrypted.length);
        write(encrypted, 0, encrypted.length);
    }

}
