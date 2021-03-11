// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

public class OnDemand implements Runnable {

	public int anInt1330;
	public final LinkedList aList_1331 = new LinkedList();
	public int anInt1332;
	public String aString1333 = "";
	public int anInt1334;
	public long aLong1335;
	public int[] anIntArray1337;
	public final CRC32 aCRC32_1338 = new CRC32();
	public final byte[] aByteArray1339 = new byte[500];
	public int cycle;
	public final byte[][] aByteArrayArray1342 = new byte[4][];
	public Game aGame1343;
	public final LinkedList aList_1344 = new LinkedList();
	public int anInt1346;
	public int anInt1347;
	public int[] anIntArray1348;
	public int anInt1349;
	public int[] anIntArray1350;
	public int anInt1351;
	public boolean aBoolean1353 = true;
	public OutputStream anOutputStream1354;
	public int[] anIntArray1356;
	public boolean aBoolean1357 = false;
	public final LinkedList aList_1358 = new LinkedList();
	public final byte[] aByteArray1359 = new byte[65000];
	public int[] anIntArray1360;
	public final DoublyLinkedList aDoublyLinkedList_1361 = new DoublyLinkedList();
	public InputStream anInputStream1362;
	public Socket aSocket1363;
	public final int[][] anIntArrayArray1364 = new int[4][];
	public final int[][] anIntArrayArray1365 = new int[4][];
	public int anInt1366;
	public int anInt1367;
	public final LinkedList aList_1368 = new LinkedList();
	public OnDemandRequest aRequest_1369;
	public final LinkedList aList_1370 = new LinkedList();
	public int[] anIntArray1371;
	public byte[] aByteArray1372;
	public int anInt1373;

	public OnDemand() {
	}

	public boolean method549(int i, int j, byte[] abyte0) {
		if ((abyte0 == null) || (abyte0.length < 2)) {
			return false;
		}
		int k = abyte0.length - 2;
		int l = ((abyte0[k] & 0xff) << 8) + (abyte0[k + 1] & 0xff);
		aCRC32_1338.reset();
		aCRC32_1338.update(abyte0, 0, k);
		int i1 = (int) aCRC32_1338.getValue();
		if (l != i) {
			return false;
		}
		return i1 == j;
	}

	public void method550() {
		try {
			int j = anInputStream1362.available();
			if ((anInt1347 == 0) && (j >= 6)) {
				aBoolean1357 = true;
				for (int k = 0; k < 6; k += anInputStream1362.read(aByteArray1339, k, 6 - k)) {
				}
				int l = aByteArray1339[0] & 0xff;
				int j1 = ((aByteArray1339[1] & 0xff) << 8) + (aByteArray1339[2] & 0xff);
				int l1 = ((aByteArray1339[3] & 0xff) << 8) + (aByteArray1339[4] & 0xff);
				int i2 = aByteArray1339[5] & 0xff;
				aRequest_1369 = null;
				for (OnDemandRequest request = (OnDemandRequest) aList_1331.method252(); request != null; request = (OnDemandRequest) aList_1331.method254()) {
					if ((request.anInt1419 == l) && (request.anInt1421 == j1)) {
						aRequest_1369 = request;
					}
					if (aRequest_1369 != null) {
						request.anInt1423 = 0;
					}
				}
				if (aRequest_1369 != null) {
					anInt1373 = 0;
					if (l1 == 0) {
						Signlink.reporterror("Rej: " + l + "," + j1);
						aRequest_1369.aByteArray1420 = null;
						if (aRequest_1369.aBoolean1422) {
							synchronized (aList_1358) {
								aList_1358.method249(aRequest_1369);
							}
						} else {
							aRequest_1369.method329();
						}
						aRequest_1369 = null;
					} else {
						if ((aRequest_1369.aByteArray1420 == null) && (i2 == 0)) {
							aRequest_1369.aByteArray1420 = new byte[l1];
						}
						if ((aRequest_1369.aByteArray1420 == null) && (i2 != 0)) {
							throw new IOException("missing start of file");
						}
					}
				}
				anInt1346 = i2 * 500;
				anInt1347 = 500;
				if (anInt1347 > (l1 - (i2 * 500))) {
					anInt1347 = l1 - (i2 * 500);
				}
			}
			if ((anInt1347 > 0) && (j >= anInt1347)) {
				aBoolean1357 = true;
				byte[] abyte0 = aByteArray1339;
				int i1 = 0;
				if (aRequest_1369 != null) {
					abyte0 = aRequest_1369.aByteArray1420;
					i1 = anInt1346;
				}
				for (int k1 = 0; k1 < anInt1347; k1 += anInputStream1362.read(abyte0, k1 + i1, anInt1347 - k1)) {
				}
				if (((anInt1347 + anInt1346) >= abyte0.length) && (aRequest_1369 != null)) {
					if (aGame1343.aFileStoreArray970[0] != null) {
						aGame1343.aFileStoreArray970[aRequest_1369.anInt1419 + 1].method234(abyte0.length, abyte0, aRequest_1369.anInt1421);
					}
					if (!aRequest_1369.aBoolean1422 && (aRequest_1369.anInt1419 == 3)) {
						aRequest_1369.aBoolean1422 = true;
						aRequest_1369.anInt1419 = 93;
					}
					if (aRequest_1369.aBoolean1422) {
						synchronized (aList_1358) {
							aList_1358.method249(aRequest_1369);
						}
					} else {
						aRequest_1369.method329();
					}
				}
				anInt1347 = 0;
			}
		} catch (IOException ioexception) {
			try {
				aSocket1363.close();
			} catch (Exception ignored) {
			}
			aSocket1363 = null;
			anInputStream1362 = null;
			anOutputStream1354 = null;
			anInt1347 = 0;
		}
	}

	public void method551(FileArchive archive, Game game1) {
		String[] as = {"model_version", "anim_version", "midi_version", "map_version"};
		for (int i = 0; i < 4; i++) {
			byte[] abyte0 = archive.read(as[i], null);
			int j = abyte0.length / 2;
			Packet packet = new Packet(abyte0);
			anIntArrayArray1364[i] = new int[j];
			aByteArrayArray1342[i] = new byte[j];
			for (int l = 0; l < j; l++) {
				anIntArrayArray1364[i][l] = packet.get2U();
			}
		}
		String[] as1 = {"model_crc", "anim_crc", "midi_crc", "map_crc"};
		for (int k = 0; k < 4; k++) {
			byte[] abyte1 = archive.read(as1[k], null);
			int i1 = abyte1.length / 4;
			Packet packet_1 = new Packet(abyte1);
			anIntArrayArray1365[k] = new int[i1];
			for (int l1 = 0; l1 < i1; l1++) {
				anIntArrayArray1365[k][l1] = packet_1.get4();
			}
		}
		byte[] abyte2 = archive.read("model_index", null);
		int j1 = anIntArrayArray1364[0].length;
		aByteArray1372 = new byte[j1];
		for (int k1 = 0; k1 < j1; k1++) {
			if (k1 < abyte2.length) {
				aByteArray1372[k1] = abyte2[k1];
			} else {
				aByteArray1372[k1] = 0;
			}
		}
		abyte2 = archive.read("map_index", null);
		Packet class30_sub2_sub2_2 = new Packet(abyte2);
		j1 = abyte2.length / 7;
		anIntArray1371 = new int[j1];
		anIntArray1350 = new int[j1];
		anIntArray1337 = new int[j1];
		anIntArray1356 = new int[j1];
		for (int i2 = 0; i2 < j1; i2++) {
			anIntArray1371[i2] = class30_sub2_sub2_2.get2U();
			anIntArray1350[i2] = class30_sub2_sub2_2.get2U();
			anIntArray1337[i2] = class30_sub2_sub2_2.get2U();
			anIntArray1356[i2] = class30_sub2_sub2_2.get1U();
		}
		abyte2 = archive.read("anim_index", null);
		class30_sub2_sub2_2 = new Packet(abyte2);
		j1 = abyte2.length / 2;
		anIntArray1360 = new int[j1];
		for (int j2 = 0; j2 < j1; j2++) {
			anIntArray1360[j2] = class30_sub2_sub2_2.get2U();
		}
		abyte2 = archive.read("midi_index", null);
		class30_sub2_sub2_2 = new Packet(abyte2);
		j1 = abyte2.length;
		anIntArray1348 = new int[j1];
		for (int k2 = 0; k2 < j1; k2++) {
			anIntArray1348[k2] = class30_sub2_sub2_2.get1U();
		}
		aGame1343 = game1;
		aBoolean1353 = true;
		aGame1343.startThread(this, 2);
	}

	public int method552() {
		synchronized (aDoublyLinkedList_1361) {
			return aDoublyLinkedList_1361.method154();
		}
	}

	public void method553() {
		aBoolean1353 = false;
	}

	public void method554(boolean flag) {
		int j = anIntArray1371.length;
		for (int k = 0; k < j; k++) {
			if (flag || (anIntArray1356[k] != 0)) {
				method563((byte) 2, 3, anIntArray1337[k]);
				method563((byte) 2, 3, anIntArray1350[k]);
			}
		}
	}

	public int method555(int j) {
		return anIntArrayArray1364[j].length;
	}

	public void method556(OnDemandRequest request) {
		try {
			if (aSocket1363 == null) {
				long l = System.currentTimeMillis();
				if ((l - aLong1335) < 4000L) {
					return;
				}
				aLong1335 = l;
				aSocket1363 = aGame1343.method19(43594 + Game.anInt958);
				anInputStream1362 = aSocket1363.getInputStream();
				anOutputStream1354 = aSocket1363.getOutputStream();
				anOutputStream1354.write(15);
				for (int j = 0; j < 8; j++) {
					anInputStream1362.read();
				}
				anInt1373 = 0;
			}
			aByteArray1339[0] = (byte) request.anInt1419;
			aByteArray1339[1] = (byte) (request.anInt1421 >> 8);
			aByteArray1339[2] = (byte) request.anInt1421;
			if (request.aBoolean1422) {
				aByteArray1339[3] = 2;
			} else if (!aGame1343.aBoolean1157) {
				aByteArray1339[3] = 1;
			} else {
				aByteArray1339[3] = 0;
			}
			anOutputStream1354.write(aByteArray1339, 0, 4);
			anInt1334 = 0;
			anInt1349 = -10000;
			return;
		} catch (IOException ignored) {
		}
		try {
			aSocket1363.close();
		} catch (Exception ignored) {
		}
		aSocket1363 = null;
		anInputStream1362 = null;
		anOutputStream1354 = null;
		anInt1347 = 0;
		anInt1349++;
	}

	public int method557() {
		return anIntArray1360.length;
	}

	public void method558(int i, int j) {
		if ((i < 0) || (i > anIntArrayArray1364.length) || (j < 0) || (j > anIntArrayArray1364[i].length)) {
			return;
		}
		if (anIntArrayArray1364[i][j] == 0) {
			return;
		}
		synchronized (aDoublyLinkedList_1361) {
			for (OnDemandRequest request = (OnDemandRequest) aDoublyLinkedList_1361.method152(); request != null; request = (OnDemandRequest) aDoublyLinkedList_1361.method153()) {
				if ((request.anInt1419 == i) && (request.anInt1421 == j)) {
					return;
				}
			}
			OnDemandRequest request_1 = new OnDemandRequest();
			request_1.anInt1419 = i;
			request_1.anInt1421 = j;
			request_1.aBoolean1422 = true;
			synchronized (aList_1370) {
				aList_1370.method249(request_1);
			}
			aDoublyLinkedList_1361.method150(request_1);
		}
	}

	public int method559(int i) {
		return aByteArray1372[i] & 0xff;
	}

	@Override
	public void run() {
		try {
			while (aBoolean1353) {
				cycle++;
				int i = 20;
				if ((anInt1332 == 0) && (aGame1343.aFileStoreArray970[0] != null)) {
					i = 50;
				}
				try {
					Thread.sleep(i);
				} catch (Exception ignored) {
				}
				aBoolean1357 = true;
				for (int j = 0; j < 100; j++) {
					if (!aBoolean1357) {
						break;
					}
					aBoolean1357 = false;
					method567();
					method565();
					if ((anInt1366 == 0) && (j >= 5)) {
						break;
					}
					method568();
					if (anInputStream1362 != null) {
						method550();
					}
				}
				boolean flag = false;
				for (OnDemandRequest request = (OnDemandRequest) aList_1331.method252(); request != null; request = (OnDemandRequest) aList_1331.method254()) {
					if (request.aBoolean1422) {
						flag = true;
						request.anInt1423++;
						if (request.anInt1423 > 50) {
							request.anInt1423 = 0;
							method556(request);
						}
					}
				}
				if (!flag) {
					for (OnDemandRequest request_1 = (OnDemandRequest) aList_1331.method252(); request_1 != null; request_1 = (OnDemandRequest) aList_1331.method254()) {
						flag = true;
						request_1.anInt1423++;
						if (request_1.anInt1423 > 50) {
							request_1.anInt1423 = 0;
							method556(request_1);
						}
					}
				}
				if (flag) {
					anInt1373++;
					if (anInt1373 > 750) {
						try {
							aSocket1363.close();
						} catch (Exception ignored) {
						}
						aSocket1363 = null;
						anInputStream1362 = null;
						anOutputStream1354 = null;
						anInt1347 = 0;
					}
				} else {
					anInt1373 = 0;
					aString1333 = "";
				}
				if (aGame1343.aBoolean1157 && (aSocket1363 != null) && (anOutputStream1354 != null) && ((anInt1332 > 0) || (aGame1343.aFileStoreArray970[0] == null))) {
					anInt1334++;
					if (anInt1334 > 500) {
						anInt1334 = 0;
						aByteArray1339[0] = 0;
						aByteArray1339[1] = 0;
						aByteArray1339[2] = 0;
						aByteArray1339[3] = 10;
						try {
							anOutputStream1354.write(aByteArray1339, 0, 4);
						} catch (IOException _ex) {
							anInt1373 = 5000;
						}
					}
				}
			}
		} catch (Exception exception) {
			Signlink.reporterror("od_ex " + exception.getMessage());
		}
	}

	public void method560(int i, int j) {
		if (aGame1343.aFileStoreArray970[0] == null) {
			return;
		}
		if (anIntArrayArray1364[j][i] == 0) {
			return;
		}
		if (aByteArrayArray1342[j][i] == 0) {
			return;
		}
		if (anInt1332 == 0) {
			return;
		}
		OnDemandRequest request = new OnDemandRequest();
		request.anInt1419 = j;
		request.anInt1421 = i;
		request.aBoolean1422 = false;
		synchronized (aList_1344) {
			aList_1344.method249(request);
		}
	}

	public OnDemandRequest method561() {
		OnDemandRequest request;
		synchronized (aList_1358) {
			request = (OnDemandRequest) aList_1358.method251();
		}
		if (request == null) {
			return null;
		}
		synchronized (aDoublyLinkedList_1361) {
			request.method330();
		}
		if (request.aByteArray1420 == null) {
			return request;
		}
		int i = 0;
		try {
			GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(request.aByteArray1420));
			do {
				if (i == aByteArray1359.length) {
					throw new RuntimeException("buffer overflow!");
				}
				int k = gzipinputstream.read(aByteArray1359, i, aByteArray1359.length - i);
				if (k == -1) {
					break;
				}
				i += k;
			} while (true);
		} catch (IOException _ex) {
			throw new RuntimeException("error unzipping");
		}
		request.aByteArray1420 = new byte[i];
		for (int j = 0; j < i; j++) {
			request.aByteArray1420[j] = aByteArray1359[j];
		}
		return request;
	}

	public int method562(int i, int k, int l) {
		int i1 = (l << 8) + k;
		for (int j1 = 0; j1 < anIntArray1371.length; j1++) {
			if (anIntArray1371[j1] == i1) {
				if (i == 0) {
					return anIntArray1350[j1];
				} else {
					return anIntArray1337[j1];
				}
			}
		}
		return -1;
	}

	public void method548(int i) {
		method558(0, i);
	}

	public void method563(byte byte0, int i, int j) {
		if (aGame1343.aFileStoreArray970[0] == null) {
			return;
		}
		if (anIntArrayArray1364[i][j] == 0) {
			return;
		}
		byte[] abyte0 = aGame1343.aFileStoreArray970[i + 1].method233(j);
		if (method549(anIntArrayArray1364[i][j], anIntArrayArray1365[i][j], abyte0)) {
			return;
		}
		aByteArrayArray1342[i][j] = byte0;
		if (byte0 > anInt1332) {
			anInt1332 = byte0;
		}
		anInt1330++;
	}

	public boolean method564(int i) {
		for (int k = 0; k < anIntArray1371.length; k++) {
			if (anIntArray1337[k] == i) {
				return true;
			}
		}
		return false;
	}

	public void method565() {
		anInt1366 = 0;
		anInt1367 = 0;
		for (OnDemandRequest request = (OnDemandRequest) aList_1331.method252(); request != null; request = (OnDemandRequest) aList_1331.method254()) {
			if (request.aBoolean1422) {
				anInt1366++;
			} else {
				anInt1367++;
			}
		}
		while (anInt1366 < 10) {
			OnDemandRequest request_1 = (OnDemandRequest) aList_1368.method251();
			if (request_1 == null) {
				break;
			}
			if (aByteArrayArray1342[request_1.anInt1419][request_1.anInt1421] != 0) {
				anInt1351++;
			}
			aByteArrayArray1342[request_1.anInt1419][request_1.anInt1421] = 0;
			aList_1331.method249(request_1);
			anInt1366++;
			method556(request_1);
			aBoolean1357 = true;
		}
	}

	public void method566() {
		synchronized (aList_1344) {
			aList_1344.method256();
		}
	}

	public void method567() {
		OnDemandRequest request;
		synchronized (aList_1370) {
			request = (OnDemandRequest) aList_1370.method251();
		}
		while (request != null) {
			aBoolean1357 = true;
			byte[] abyte0 = null;
			if (aGame1343.aFileStoreArray970[0] != null) {
				abyte0 = aGame1343.aFileStoreArray970[request.anInt1419 + 1].method233(request.anInt1421);
			}
			if (!method549(anIntArrayArray1364[request.anInt1419][request.anInt1421], anIntArrayArray1365[request.anInt1419][request.anInt1421], abyte0)) {
				abyte0 = null;
			}
			synchronized (aList_1370) {
				if (abyte0 == null) {
					aList_1368.method249(request);
				} else {
					request.aByteArray1420 = abyte0;
					synchronized (aList_1358) {
						aList_1358.method249(request);
					}
				}
				request = (OnDemandRequest) aList_1370.method251();
			}
		}
	}

	public void method568() {
		while ((anInt1366 == 0) && (anInt1367 < 10)) {
			if (anInt1332 == 0) {
				break;
			}
			OnDemandRequest request;
			synchronized (aList_1344) {
				request = (OnDemandRequest) aList_1344.method251();
			}
			while (request != null) {
				if (aByteArrayArray1342[request.anInt1419][request.anInt1421] != 0) {
					aByteArrayArray1342[request.anInt1419][request.anInt1421] = 0;
					aList_1331.method249(request);
					method556(request);
					aBoolean1357 = true;
					if (anInt1351 < anInt1330) {
						anInt1351++;
					}
					aString1333 = "Loading extra files - " + (anInt1351 * 100) / anInt1330 + "%";
					anInt1367++;
					if (anInt1367 == 10) {
						return;
					}
				}
				synchronized (aList_1344) {
					request = (OnDemandRequest) aList_1344.method251();
				}
			}
			for (int j = 0; j < 4; j++) {
				byte[] abyte0 = aByteArrayArray1342[j];
				int k = abyte0.length;
				for (int l = 0; l < k; l++) {
					if (abyte0[l] == anInt1332) {
						abyte0[l] = 0;
						OnDemandRequest request_1 = new OnDemandRequest();
						request_1.anInt1419 = j;
						request_1.anInt1421 = l;
						request_1.aBoolean1422 = false;
						aList_1331.method249(request_1);
						method556(request_1);
						aBoolean1357 = true;
						if (anInt1351 < anInt1330) {
							anInt1351++;
						}
						aString1333 = "Loading extra files - " + (anInt1351 * 100) / anInt1330 + "%";
						anInt1367++;
						if (anInt1367 == 10) {
							return;
						}
					}
				}
			}
			anInt1332--;
		}
	}

	public boolean method569(int i) {
		return anIntArray1348[i] == 1;
	}

}
