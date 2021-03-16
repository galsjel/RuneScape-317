import java.io.IOException;

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
			BZip2.decompress(data, src, 6, packedSize);
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

			if (!unpacked) {
				byte[] dst = new byte[fileSizeInflated[file]];



				BZip2.decompress(dst, data, fileOffset[file], fileSizeDeflated[file]);
				return dst;
			} else {
				byte[] dst = new byte[fileSizeInflated[file]];
				System.arraycopy(data, fileOffset[file], dst, 0, fileSizeInflated[file]);
				return dst;
			}
		}
		return null;
	}

}
