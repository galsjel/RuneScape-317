// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileStore {

	public static final byte[] buf = new byte[520];
	public final RandomAccessFile dat;
	public final RandomAccessFile idx;
	public final int store;
	public int maxFileSize;

	public FileStore(int maxFileSize, RandomAccessFile dat, RandomAccessFile idx, int store) {
		this.maxFileSize = 65000;
		this.store = store;
		this.dat = dat;
		this.idx = idx;
		this.maxFileSize = maxFileSize;
	}

	public synchronized long getFileCount() throws IOException {
		return idx.length() / 6L;
	}

	public synchronized byte[] read(int file) {
		try {
			idx.seek(file * 6L);

			idx.read(buf, 0, 6);

			int size = ((buf[0] & 0xff) << 16) + ((buf[1] & 0xff) << 8) + (buf[2] & 0xff);
			int sector = ((buf[3] & 0xff) << 16) + ((buf[4] & 0xff) << 8) + (buf[5] & 0xff);

			if (size > maxFileSize) {
				return null;
			}

			if ((sector <= 0) || ((long) sector > (dat.length() / 520L))) {
				return null;
			}

			byte[] data = new byte[size];
			int position = 0;

			for (int part = 0; position < size; part++) {
				if (sector == 0) {
					return null;
				}

				dat.seek(sector * 520L);

				int available = size - position;

				if (available > 512) {
					available = 512;
				}

				dat.read(buf, 0, available + 8);

				int sectorFile = ((buf[0] & 0xff) << 8) + (buf[1] & 0xff);
				int sectorPart = ((buf[2] & 0xff) << 8) + (buf[3] & 0xff);
				int nextSector = ((buf[4] & 0xff) << 16) + ((buf[5] & 0xff) << 8) + (buf[6] & 0xff);
				int sectorStore = buf[7] & 0xff;

				if ((sectorFile != file) || (sectorPart != part) || (sectorStore != store)) {
					return null;
				}

				if ((nextSector < 0) || ((long) nextSector > (dat.length() / 520L))) {
					return null;
				}

				for (int i = 0; i < available; i++) {
					data[position++] = buf[i + 8];
				}

				sector = nextSector;
			}
			return data;
		} catch (IOException e) {
			return null;
		}
	}

	public synchronized void write(byte[] src, int file, int size) {
		boolean written = write(src, file, size, true);
		if (!written) {
			write(src, file, size, false);
		}
	}

	public synchronized boolean write(byte[] data, int file, int size, boolean overwrite) {
		try {
			int sector;

			if (overwrite) {
				idx.seek(file * 6L);
				idx.read(buf, 0, 6);

				sector = ((buf[3] & 0xff) << 16) + ((buf[4] & 0xff) << 8) + (buf[5] & 0xff);

				if ((sector <= 0) || ((long) sector > (dat.length() / 520L))) {
					return false;
				}
			} else {
				sector = (int) ((dat.length() + 519L) / 520L);

				if (sector == 0) {
					sector = 1;
				}
			}

			buf[0] = (byte) (size >> 16);
			buf[1] = (byte) (size >> 8);
			buf[2] = (byte) size;
			buf[3] = (byte) (sector >> 16);
			buf[4] = (byte) (sector >> 8);
			buf[5] = (byte) sector;

			idx.seek(file * 6L);
			idx.write(buf, 0, 6);

			int written = 0;
			for (int part = 0; written < size; part++) {
				int nextSector = 0;

				if (overwrite) {
					dat.seek(sector * 520L);

					if (dat.read(buf, 0, 8) == 8) {
						int sectorFile = ((buf[0] & 0xff) << 8) + (buf[1] & 0xff);
						int sectorPart = ((buf[2] & 0xff) << 8) + (buf[3] & 0xff);
						nextSector = ((buf[4] & 0xff) << 16) + ((buf[5] & 0xff) << 8) + (buf[6] & 0xff);
						int sectorStore = buf[7] & 0xff;
						if ((sectorFile != file) || (sectorPart != part) || (sectorStore != store)) {
							return false;
						}
						if ((nextSector < 0) || ((long) nextSector > (dat.length() / 520L))) {
							return false;
						}
					}
				}

				if (nextSector == 0) {
					overwrite = false;
					nextSector = (int) ((dat.length() + 519L) / 520L);

					if (nextSector == 0) {
						nextSector++;
					}

					if (nextSector == sector) {
						nextSector++;
					}
				}

				if ((size - written) <= 512) {
					nextSector = 0;
				}

				buf[0] = (byte) (file >> 8);
				buf[1] = (byte) file;
				buf[2] = (byte) (part >> 8);
				buf[3] = (byte) part;
				buf[4] = (byte) (nextSector >> 16);
				buf[5] = (byte) (nextSector >> 8);
				buf[6] = (byte) nextSector;
				buf[7] = (byte) store;

				dat.seek(sector * 520L);
				dat.write(buf, 0, 8);

				int available = size - written;

				if (available > 512) {
					available = 512;
				}

				dat.write(data, written, available);
				written += available;
				sector = nextSector;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
