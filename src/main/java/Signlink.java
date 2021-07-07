// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   signlink.java

import java.applet.Applet;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Signlink implements Runnable {

	public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
	public static final Applet mainapp = null;
	public static int uid;
	public static int storeid = 32;
	public static RandomAccessFile cache_dat = null;
	public static boolean sunjava;
	public static boolean active;
	public static int threadliveid;
	public static InetAddress socketip;
	public static int socketreq;
	public static Socket socket = null;
	public static int threadreqpri = 1;
	public static Runnable threadreq = null;
	public static String dnsreq = null;
	public static String dns = null;
	public static String urlreq = null;
	public static DataInputStream urlstream = null;
	public static int savelen;
	public static String savereq = null;
	public static byte[] savebuf = null;
	public static boolean midiplay;
	public static int midipos;
	public static String midi = null;
	public static int midivol;
	public static int midifade;
	public static boolean waveplay;
	public static int wavepos;
	public static String wave = null;
	public static int wavevol;
	public static boolean reporterror = true;

	public static void startpriv(InetAddress inetaddress) {
		threadliveid = (int) (Math.random() * 99999999D);
		if (active) {
			try {
				Thread.sleep(500L);
			} catch (Exception ignored) {
			}
			active = false;
		}
		socketreq = 0;
		threadreq = null;
		dnsreq = null;
		savereq = null;
		urlreq = null;
		socketip = inetaddress;
		Thread thread = new Thread(new Signlink());
		thread.setDaemon(true);
		thread.start();
		while (!active) {
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
	}

	public static String findcachedir() {
		String[] as = {"c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/", "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/", "~/", "/tmp/", "", "c:/rscache", "/rscache"};
		if ((storeid < 32) || (storeid > 34)) {
			storeid = 32;
		}
		String s = ".file_store_" + storeid;
		for (String a : as) {
			try {
				if (a.length() > 0) {
					File file = new File(a);
					if (!file.exists()) {
						continue;
					}
				}
				File file1 = new File(a + s);
				if (file1.exists() || file1.mkdir()) {
					return a + s + "/";
				}
			} catch (Exception ignored) {
			}
		}
		return null;
	}

	public static int getuid(String s) {
		try {
			File file = new File(s + "uid.dat");
			if (!file.exists() || (file.length() < 4L)) {
				DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(s + "uid.dat"));
				dataoutputstream.writeInt((int) (Math.random() * 99999999D));
				dataoutputstream.close();
			}
		} catch (Exception ignored) {
		}
		try {
			DataInputStream datainputstream = new DataInputStream(new FileInputStream(s + "uid.dat"));
			int i = datainputstream.readInt();
			datainputstream.close();
			return i + 1;
		} catch (Exception _ex) {
			return 0;
		}
	}

	public static synchronized Socket opensocket(int i) throws IOException {
		for (socketreq = i; socketreq != 0; ) {
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
		if (socket == null) {
			throw new IOException("could not open socket");
		} else {
			return socket;
		}
	}

	public static synchronized DataInputStream openurl(String s) throws IOException {
		for (urlreq = s; urlreq != null; ) {
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
		if (urlstream == null) {
			throw new IOException("could not open: " + s);
		} else {
			return urlstream;
		}
	}

	public static synchronized void dnslookup(String s) {
		dns = s;
		dnsreq = s;
	}

	public static synchronized void startthread(Runnable runnable, int i) {
		threadreqpri = i;
		threadreq = runnable;
	}

	public static synchronized boolean wavesave(byte[] src, int len) {
		if (len > 0x1e8480) {
			return false;
		}
		if (savereq != null) {
			return false;
		} else {
			wavepos = (wavepos + 1) % 5;
			savelen = len;
			savebuf = src;
			waveplay = true;
			savereq = "sound" + wavepos + ".wav";
			return true;
		}
	}

	public static synchronized boolean wavereplay() {
		if (savereq != null) {
			return false;
		} else {
			savebuf = null;
			waveplay = true;
			savereq = "sound" + wavepos + ".wav";
			return true;
		}
	}

	public static synchronized void midisave(byte[] data, int len) {
		if (len > 0x1e8480) {
			return;
		}
		if (savereq != null) {
		} else {
			midipos = (midipos + 1) % 5;
			savelen = len;
			savebuf = data;
			midiplay = true;
			savereq = "jingle" + midipos + ".mid";
		}
	}

	public static void reporterror(String s) {
		if (!reporterror) {
			return;
		}
		if (!active) {
			return;
		}
		System.out.println("Error: " + s);
	}

	public Signlink() {
	}

	@Override
	public void run() {
		active = true;
		String s = findcachedir();
		System.out.println(s);
		uid = getuid(s);
		try {
			File file = new File(s + "main_file_cache.dat");
			if (file.exists() && (file.length() > 0x3200000L)) {
				file.delete();
			}
			cache_dat = new RandomAccessFile(s + "main_file_cache.dat", "rw");
			for (int j = 0; j < 5; j++) {
				cache_idx[j] = new RandomAccessFile(s + "main_file_cache.idx" + j, "rw");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		for (int i = threadliveid; threadliveid == i; ) {
			if (socketreq != 0) {
				try {
					socket = new Socket(socketip, socketreq);
				} catch (Exception _ex) {
					socket = null;
				}
				socketreq = 0;
			} else if (threadreq != null) {
				Thread thread = new Thread(threadreq);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(threadreqpri);
				threadreq = null;
			} else if (dnsreq != null) {
				try {
					dns = InetAddress.getByName(dnsreq).getHostName();
				} catch (Exception _ex) {
					dns = "unknown";
				}
				dnsreq = null;
			} else if (savereq != null) {
				if (savebuf != null) {
					try {
						FileOutputStream fileoutputstream = new FileOutputStream(s + savereq);
						fileoutputstream.write(savebuf, 0, savelen);
						fileoutputstream.close();
					} catch (Exception ignored) {
					}
				}
				if (waveplay) {
					wave = s + savereq;
					waveplay = false;
				}
				if (midiplay) {
					midi = s + savereq;
					midiplay = false;
				}
				savereq = null;
			} else if (urlreq != null) {
				try {
					urlstream = new DataInputStream((new URL(mainapp.getCodeBase(), urlreq)).openStream());
				} catch (Exception _ex) {
					urlstream = null;
				}
				urlreq = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
	}

}
