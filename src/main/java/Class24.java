// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Class24 implements Runnable {

	public InputStream anInputStream419;
	public OutputStream anOutputStream420;
	public final Socket aSocket421;
	public boolean aBoolean422 = false;
	public final Applet_Sub1 anApplet_Sub1_423;
	public byte[] aByteArray424;
	public int anInt425;
	public int anInt426;
	public boolean aBoolean427 = false;
	public boolean aBoolean428 = false;

	public Class24(Applet_Sub1 applet_sub1, Socket socket) throws IOException {
		anApplet_Sub1_423 = applet_sub1;
		aSocket421 = socket;
		aSocket421.setSoTimeout(30000);
		aSocket421.setTcpNoDelay(true);
		anInputStream419 = aSocket421.getInputStream();
		anOutputStream420 = aSocket421.getOutputStream();
	}

	public void method267() {
		aBoolean422 = true;
		try {
			if (anInputStream419 != null) {
				anInputStream419.close();
			}
			if (anOutputStream420 != null) {
				anOutputStream420.close();
			}
			if (aSocket421 != null) {
				aSocket421.close();
			}
		} catch (IOException _ex) {
			System.out.println("Error closing stream");
		}
		aBoolean427 = false;
		synchronized (this) {
			notify();
		}
		aByteArray424 = null;
	}

	public int method268() throws IOException {
		if (aBoolean422) {
			return 0;
		} else {
			return anInputStream419.read();
		}
	}

	public int method269() throws IOException {
		if (aBoolean422) {
			return 0;
		} else {
			return anInputStream419.available();
		}
	}

	public void method270(byte[] abyte0, int i, int j) throws IOException {
		if (aBoolean422) {
			return;
		}
		int k;
		for (; j > 0; j -= k) {
			k = anInputStream419.read(abyte0, i, j);
			if (k <= 0) {
				throw new IOException("EOF");
			}
			i += k;
		}
	}

	public void method271(int i, byte[] abyte0, int k) throws IOException {
		if (aBoolean422) {
			return;
		}
		if (aBoolean428) {
			aBoolean428 = false;
			throw new IOException("Error in writer thread");
		}
		if (aByteArray424 == null) {
			aByteArray424 = new byte[5000];
		}
		synchronized (this) {
			for (int l = 0; l < i; l++) {
				aByteArray424[anInt426] = abyte0[l + k];
				anInt426 = (anInt426 + 1) % 5000;
				if (anInt426 == (anInt425 + 4900) % 5000) {
					throw new IOException("buffer overflow");
				}
			}
			if (!aBoolean427) {
				aBoolean427 = true;
				anApplet_Sub1_423.method12(this, 3);
			}
			notify();
		}
	}

	public void run() {
		while (aBoolean427) {
			int i;
			int j;
			synchronized (this) {
				if (anInt426 == anInt425) {
					try {
						wait();
					} catch (InterruptedException ignored) {
					}
				}
				if (!aBoolean427) {
					return;
				}
				j = anInt425;
				if (anInt426 >= anInt425) {
					i = anInt426 - anInt425;
				} else {
					i = 5000 - anInt425;
				}
			}
			if (i > 0) {
				try {
					anOutputStream420.write(aByteArray424, j, i);
				} catch (IOException _ex) {
					aBoolean428 = true;
				}
				anInt425 = (anInt425 + i) % 5000;
				try {
					if (anInt426 == anInt425) {
						anOutputStream420.flush();
					}
				} catch (IOException _ex) {
					aBoolean428 = true;
				}
			}
		}
	}

	public void method272() {
		System.out.println("dummy:" + aBoolean422);
		System.out.println("tcycl:" + anInt425);
		System.out.println("tnum:" + anInt426);
		System.out.println("writer:" + aBoolean427);
		System.out.println("ioerror:" + aBoolean428);
		try {
			System.out.println("available:" + method269());
		} catch (IOException ignored) {
		}
	}

}
