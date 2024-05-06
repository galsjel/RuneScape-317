// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   signlink.java

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Signlink implements Runnable {

    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
    public static int uid;
    public static int storeid = 32;
    public static RandomAccessFile cache_dat = null;
    public static boolean active;
    public static int threadliveid;
    public static String dnsreq = null;
    public static String dns = null;
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

    public static void startpriv() {
        threadliveid = (int) (Math.random() * 99999999D);

        if (active) {
            try {
                Thread.sleep(500L);
            } catch (Exception ignored) {
            }
            active = false;
        }

        dnsreq = null;
        savereq = null;
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
        String[] as = {"c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/", "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/", System.getProperty("user.home") + "/", "/tmp/", "", "c:/rscache", "/rscache"};
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
        Path path = Paths.get(s + "uid.dat");

        try {
            File file = new File(s + "uid.dat");

            if (!file.exists() || (file.length() < 4L)) {
                DataOutputStream out = new DataOutputStream(Files.newOutputStream(path));
                out.writeInt((int) (Math.random() * 99999999D));
                out.close();
            }
        } catch (Exception ignored) {
        }

        try {
            DataInputStream in = new DataInputStream(Files.newInputStream(path));
            int uid = in.readInt();
            in.close();
            return uid + 1;
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static synchronized void dnslookup(String dns) {
        Signlink.dns = dns;
        dnsreq = dns;
    }

    public static synchronized boolean wavesave(byte[] src, int len) {
        if (len > 2000000) {
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
        if (savereq == null) {
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

        String cachedir = findcachedir();
        System.out.println(cachedir);
        uid = getuid(cachedir);

        try {
            cache_dat = new RandomAccessFile(cachedir + "main_file_cache.dat", "rw");
            for (int j = 0; j < 5; j++) {
                cache_idx[j] = new RandomAccessFile(cachedir + "main_file_cache.idx" + j, "rw");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = threadliveid; threadliveid == i; ) {
            if (dnsreq != null) {
                try {
                    dns = InetAddress.getByName(dnsreq).getHostName();
                } catch (Exception _ex) {
                    dns = "unknown";
                }
                dnsreq = null;
            } else if (savereq != null) {
                if (savebuf != null) {
                    try {
                        FileOutputStream fileoutputstream = new FileOutputStream(cachedir + savereq);
                        fileoutputstream.write(savebuf, 0, savelen);
                        fileoutputstream.close();
                    } catch (Exception ignored) {
                    }
                }
                if (waveplay) {
                    wave = cachedir + savereq;
                    waveplay = false;
                }
                if (midiplay) {
                    midi = cachedir + savereq;
                    midiplay = false;
                }
                savereq = null;
            }

            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        }
    }

}
