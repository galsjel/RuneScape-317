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
	public boolean inflated;

	public FileArchive(byte[] src) {
		load(src);
	}

	public void load(byte[] src) {
		Buffer buffer = new Buffer(src);

		int inflatedSize = buffer.get24();
		int deflatedSize = buffer.get24();

		if (deflatedSize != inflatedSize) {
			byte[] inflated = new byte[inflatedSize];
			BZip2.inflate(inflated, inflatedSize, src, deflatedSize, 6);
			data = inflated;
			buffer = new Buffer(data);
			this.inflated = true;
		} else {
			data = src;
			inflated = false;
		}

		fileCount = buffer.getU16();
		fileHash = new int[fileCount];
		fileSizeInflated = new int[fileCount];
		fileSizeDeflated = new int[fileCount];
		fileOffset = new int[fileCount];

		int offset = buffer.position + (fileCount * 10);

		for (int file = 0; file < fileCount; file++) {
			fileHash[file] = buffer.get32();
			fileSizeInflated[file] = buffer.get24();
			fileSizeDeflated[file] = buffer.get24();
			fileOffset[file] = offset;
			offset += fileSizeDeflated[file];
		}
	}

	public byte[] read(String s, byte[] dst) {
		int hash = 0;
		s = s.toUpperCase();
		for (int j = 0; j < s.length(); j++) {
			hash = ((hash * 61) + s.charAt(j)) - 32;
		}

		for (int file = 0; file < fileCount; file++) {
			if (fileHash[file] != hash) {
				continue;
			}
			if (dst == null) {
				dst = new byte[fileSizeInflated[file]];
			}

			if (!inflated) {
				BZip2.inflate(dst, fileSizeInflated[file], data, fileSizeDeflated[file], fileOffset[file]);
			} else {
				for (int i = 0; i < fileSizeInflated[file]; i++) {
					dst[i] = data[fileOffset[file] + i];
				}
			}
			return dst;
		}
		return null;
	}

}
