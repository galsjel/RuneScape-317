import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BZip2 extends InputStream {

	private static final byte[] MAGIC = {'B', 'Z', 'h', '1'};

	public static void decompress(byte[] dst, byte[] src, int srcOff, int srcLen) throws IOException {
		BZip2CompressorInputStream bz2in = new BZip2CompressorInputStream(new BZip2(src, srcOff, srcLen));
		byte[] buf = new byte[2048];
		int read;
		int written = 0;
		while ((read = bz2in.read(buf)) != -1) {
			System.arraycopy(buf, 0, dst, written, read);
			written += read;
		}
	}

	private final ByteArrayInputStream in;
	private int position;

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
