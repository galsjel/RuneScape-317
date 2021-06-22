// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Runnable {

	public final Socket socket;
	public final GameShell shell;
	public InputStream in;
	public OutputStream out;
	public boolean closed = false;
	public byte[] buf;
	public int tcycl;
	public int tnum;
	public boolean writer = false;
	public boolean ioerror = false;

	public Connection(GameShell shell, Socket socket) throws IOException {
		this.shell = shell;
		this.socket = socket;
		this.socket.setSoTimeout(30000);
		this.socket.setTcpNoDelay(true);
		in = this.socket.getInputStream();
		out = this.socket.getOutputStream();
	}

	public void close() {
		closed = true;
		try {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException _ex) {
			System.out.println("Error closing stream");
		}
		writer = false;
		synchronized (this) {
			notify();
		}
		buf = null;
	}

	public int read() throws IOException {
		if (closed) {
			return 0;
		} else {
			return in.read();
		}
	}

	public int available() throws IOException {
		if (closed) {
			return 0;
		} else {
			return in.available();
		}
	}

	public void read(byte[] dst, int off, int len) throws IOException {
		if (closed) {
			return;
		}
		int k;
		for (; len > 0; len -= k) {
			k = in.read(dst, off, len);
			if (k <= 0) {
				throw new IOException("EOF");
			}
			off += k;
		}
	}

	public void write(byte[] src, int off, int len) throws IOException {
		if (closed) {
			return;
		}
		if (ioerror) {
			ioerror = false;
			throw new IOException("Error in writer thread");
		}
		if (buf == null) {
			buf = new byte[5000];
		}
		synchronized (this) {
			for (int i = 0; i < len; i++) {
				buf[tnum] = src[i + off];
				tnum = (tnum + 1) % 5000;
				if (tnum == ((tcycl + 4900) % 5000)) {
					throw new IOException("buffer overflow");
				}
			}
			if (!writer) {
				writer = true;
				shell.startThread(this, 3);
			}
			notify();
		}
	}

	@Override
	public void run() {
		while (writer) {
			int i;
			int j;
			synchronized (this) {
				if (tnum == tcycl) {
					try {
						wait();
					} catch (InterruptedException ignored) {
					}
				}
				if (!writer) {
					return;
				}
				j = tcycl;
				if (tnum >= tcycl) {
					i = tnum - tcycl;
				} else {
					i = 5000 - tcycl;
				}
			}
			if (i > 0) {
				try {
					out.write(buf, j, i);
				} catch (IOException _ex) {
					ioerror = true;
				}
				tcycl = (tcycl + i) % 5000;
				try {
					if (tnum == tcycl) {
						out.flush();
					}
				} catch (IOException _ex) {
					ioerror = true;
				}
			}
		}
	}

	public void debug() {
		System.out.println("closed:" + closed);
		System.out.println("tcycl:" + tcycl);
		System.out.println("tnum:" + tnum);
		System.out.println("writer:" + writer);
		System.out.println("ioerror:" + ioerror);
		try {
			System.out.println("available:" + available());
		} catch (IOException ignored) {
		}
	}

}
