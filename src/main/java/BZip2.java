import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link BZip2} itself is an implementation of {@link InputStream} to feed the header magic to a {@link BZip2CompressorInputStream}.
 *
 * @see #decompress(byte[], int, int, byte[])
 */
public class BZip2 extends InputStream {

	private static final byte[] MAGIC = {'B', 'Z', 'h', '1'};

	/**
	 * @see #decompress(byte[], int, int, byte[])
	 */
	public static byte[] decompress(byte[] src) throws IOException {
		return decompress(src, 0, src.length, null);
	}

	/**
	 * @see #decompress(byte[], int, int, byte[])
	 */
	public static byte[] decompress(byte[] src, int off, int len) throws IOException {
		return decompress(src, off, len, null);
	}

	/**
	 * Decompresses the source.
	 *
	 * @param src the source data.
	 * @param off the source offset.
	 * @param len the source length.
	 * @param dst the destination or <code>null</code> to create a new one.
	 * @throws IOException if the stream content is malformed or an I/O error occurs.
	 */
	public static byte[] decompress(byte[] src, int off, int len, byte[] dst) throws IOException {
		ByteArrayOutputStream tmp = null;

		if (dst == null) {
			tmp = new ByteArrayOutputStream();
		}

		try (BZip2CompressorInputStream in = new BZip2CompressorInputStream(new BZip2(src, off, len))) {
			if (tmp != null) {
				IOUtils.copy(in, tmp);
			} else {
				IOUtils.readFully(in, dst);
			}
		}

		if (tmp != null) {
			return tmp.toByteArray();
		}

		return dst;
	}

	/**
	 * The input.
	 */
	private final ByteArrayInputStream in;
	/**
	 * The position.
	 */
	private int position;

	/**
	 * Constructs a new helper class to fool that silly compressor.
	 *
	 * @param src the source.
	 * @param off the source offset.
	 * @param len the source length.
	 */
	private BZip2(byte[] src, int off, int len) {
		this.in = new ByteArrayInputStream(src, off, len);
	}

	@Override
	public int read() throws IOException {
		if (position < 4) {
			return MAGIC[position++];
		}
		return in.read();
	}

}
