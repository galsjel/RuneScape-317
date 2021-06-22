import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A simple file archive format.
 */
public class FileArchive {

	public byte[] data;
	public int fileCount;
	public int[] fileHash;
	public int[] fileSizeInflated;
	public int[] fileSizeDeflated;
	public int[] fileOffset;
	public boolean unpacked;

	public FileArchive(byte[] src) throws IOException {
		load(src);
	}

	public void load(byte[] src) throws IOException {
		Buffer buffer = new Buffer(src);

		int unpackedSize = buffer.get3();
		int packedSize = buffer.get3();

		if (packedSize != unpackedSize) {
			data = new byte[unpackedSize];
			BZip2.decompress(src, 6, packedSize, data);
			buffer = new Buffer(data);
			unpacked = true;
		} else {
			data = src;
			unpacked = false;
		}

		fileCount = buffer.get2U();
		fileHash = new int[fileCount];
		fileSizeInflated = new int[fileCount];
		fileSizeDeflated = new int[fileCount];
		fileOffset = new int[fileCount];

		int offset = buffer.position + (fileCount * 10);

		for (int file = 0; file < fileCount; file++) {
			fileHash[file] = buffer.get4();
			fileSizeInflated[file] = buffer.get3();
			fileSizeDeflated[file] = buffer.get3();
			fileOffset[file] = offset;
			offset += fileSizeDeflated[file];
		}
	}

	public void export(String s, Path dst) throws IOException {
		byte[] data = read(s);
		if (data == null) {
			throw new FileNotFoundException(s);
		}
		try (OutputStream out = Files.newOutputStream(dst)) {
			out.write(data);
		}
	}

	public byte[] read(String s) throws IOException {
		int hash = 0;
		s = s.toUpperCase();
		for (int j = 0; j < s.length(); j++) {
			hash = ((hash * 61) + s.charAt(j)) - 32;
		}

		for (int file = 0; file < fileCount; file++) {
			if (fileHash[file] != hash) {
				continue;
			}

			byte[] dst = new byte[fileSizeInflated[file]];

			if (!unpacked) {
				BZip2.decompress(data, fileOffset[file], fileSizeDeflated[file], dst);
			} else {
				System.arraycopy(data, fileOffset[file], dst, 0, fileSizeInflated[file]);
			}
			return dst;
		}
		return null;
	}

}
