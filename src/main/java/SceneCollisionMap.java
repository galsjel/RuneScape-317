// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneCollisionMap {

	public final int anInt290 = 0;
	public final int anInt291 = 0;
	public final int anInt292;
	public final int anInt293;
	public final int[][] flags;

	public SceneCollisionMap(int i, int j) {
		anInt292 = i;
		anInt293 = j;
		flags = new int[anInt292][anInt293];
		reset();
	}

	public void reset() {
		for (int i = 0; i < anInt292; i++) {
			for (int j = 0; j < anInt293; j++) {
				if ((i == 0) || (j == 0) || (i == (anInt292 - 1)) || (j == (anInt293 - 1))) {
					flags[i][j] = 0xffffff;
				} else {
					flags[i][j] = 0x1000000;
				}
			}
		}
	}

	public void addWall(int i, int rotation, int k, int kind, boolean blocksProjectiles) {
		if (kind == 0) {
			if (rotation == 0) {
				add(k, i, 128);
				add(k - 1, i, 8);
			}
			if (rotation == 1) {
				add(k, i, 2);
				add(k, i + 1, 32);
			}
			if (rotation == 2) {
				add(k, i, 8);
				add(k + 1, i, 128);
			}
			if (rotation == 3) {
				add(k, i, 32);
				add(k, i - 1, 2);
			}
		}
		if ((kind == 1) || (kind == 3)) {
			if (rotation == 0) {
				add(k, i, 1);
				add(k - 1, i + 1, 16);
			}
			if (rotation == 1) {
				add(k, i, 4);
				add(k + 1, i + 1, 64);
			}
			if (rotation == 2) {
				add(k, i, 16);
				add(k + 1, i - 1, 1);
			}
			if (rotation == 3) {
				add(k, i, 64);
				add(k - 1, i - 1, 4);
			}
		}
		if (kind == 2) {
			if (rotation == 0) {
				add(k, i, 130);
				add(k - 1, i, 8);
				add(k, i + 1, 32);
			}
			if (rotation == 1) {
				add(k, i, 10);
				add(k, i + 1, 32);
				add(k + 1, i, 128);
			}
			if (rotation == 2) {
				add(k, i, 40);
				add(k + 1, i, 128);
				add(k, i - 1, 2);
			}
			if (rotation == 3) {
				add(k, i, 160);
				add(k, i - 1, 2);
				add(k - 1, i, 8);
			}
		}
		if (blocksProjectiles) {
			if (kind == 0) {
				if (rotation == 0) {
					add(k, i, 0x10000);
					add(k - 1, i, 4096);
				}
				if (rotation == 1) {
					add(k, i, 1024);
					add(k, i + 1, 16384);
				}
				if (rotation == 2) {
					add(k, i, 4096);
					add(k + 1, i, 0x10000);
				}
				if (rotation == 3) {
					add(k, i, 16384);
					add(k, i - 1, 1024);
				}
			}
			if ((kind == 1) || (kind == 3)) {
				if (rotation == 0) {
					add(k, i, 512);
					add(k - 1, i + 1, 8192);
				}
				if (rotation == 1) {
					add(k, i, 2048);
					add(k + 1, i + 1, 32768);
				}
				if (rotation == 2) {
					add(k, i, 8192);
					add(k + 1, i - 1, 512);
				}
				if (rotation == 3) {
					add(k, i, 32768);
					add(k - 1, i - 1, 2048);
				}
			}
			if (kind == 2) {
				if (rotation == 0) {
					add(k, i, 0x10400);
					add(k - 1, i, 4096);
					add(k, i + 1, 16384);
				}
				if (rotation == 1) {
					add(k, i, 5120);
					add(k, i + 1, 16384);
					add(k + 1, i, 0x10000);
				}
				if (rotation == 2) {
					add(k, i, 20480);
					add(k + 1, i, 0x10000);
					add(k, i - 1, 1024);
				}
				if (rotation == 3) {
					add(k, i, 0x14000);
					add(k, i - 1, 1024);
					add(k - 1, i, 4096);
				}
			}
		}
	}

	public void add(boolean blocksProjectiles, int width, int length, int x, int z, int rotation) {
		int flags = 256;

		if (blocksProjectiles) {
			flags += 0x20000;
		}

		if ((rotation == 1) || (rotation == 3)) {
			int tmp = width;
			width = length;
			length = tmp;
		}

		for (int tx = x; tx < (x + width); tx++) {
			if ((tx >= 0) && (tx < anInt292)) {
				for (int tz = z; tz < (z + length); tz++) {
					if ((tz >= 0) && (tz < anInt293)) {
						add(tx, tz, flags);
					}
				}
			}
		}
	}

	public void addSolid(int z, int x) {
		flags[x][z] |= 0x200000;
	}

	public void add(int x, int z, int k) {
		flags[x][z] |= k;
	}

	public void remove(int i, int j, boolean flag, int k, int l) {
		if (j == 0) {
			if (i == 0) {
				method217(128, k, l);
				method217(8, k - 1, l);
			}
			if (i == 1) {
				method217(2, k, l);
				method217(32, k, l + 1);
			}
			if (i == 2) {
				method217(8, k, l);
				method217(128, k + 1, l);
			}
			if (i == 3) {
				method217(32, k, l);
				method217(2, k, l - 1);
			}
		}
		if ((j == 1) || (j == 3)) {
			if (i == 0) {
				method217(1, k, l);
				method217(16, k - 1, l + 1);
			}
			if (i == 1) {
				method217(4, k, l);
				method217(64, k + 1, l + 1);
			}
			if (i == 2) {
				method217(16, k, l);
				method217(1, k + 1, l - 1);
			}
			if (i == 3) {
				method217(64, k, l);
				method217(4, k - 1, l - 1);
			}
		}
		if (j == 2) {
			if (i == 0) {
				method217(130, k, l);
				method217(8, k - 1, l);
				method217(32, k, l + 1);
			}
			if (i == 1) {
				method217(10, k, l);
				method217(32, k, l + 1);
				method217(128, k + 1, l);
			}
			if (i == 2) {
				method217(40, k, l);
				method217(128, k + 1, l);
				method217(2, k, l - 1);
			}
			if (i == 3) {
				method217(160, k, l);
				method217(2, k, l - 1);
				method217(8, k - 1, l);
			}
		}
		if (flag) {
			if (j == 0) {
				if (i == 0) {
					method217(0x10000, k, l);
					method217(4096, k - 1, l);
				}
				if (i == 1) {
					method217(1024, k, l);
					method217(16384, k, l + 1);
				}
				if (i == 2) {
					method217(4096, k, l);
					method217(0x10000, k + 1, l);
				}
				if (i == 3) {
					method217(16384, k, l);
					method217(1024, k, l - 1);
				}
			}
			if ((j == 1) || (j == 3)) {
				if (i == 0) {
					method217(512, k, l);
					method217(8192, k - 1, l + 1);
				}
				if (i == 1) {
					method217(2048, k, l);
					method217(32768, k + 1, l + 1);
				}
				if (i == 2) {
					method217(8192, k, l);
					method217(512, k + 1, l - 1);
				}
				if (i == 3) {
					method217(32768, k, l);
					method217(2048, k - 1, l - 1);
				}
			}
			if (j == 2) {
				if (i == 0) {
					method217(0x10400, k, l);
					method217(4096, k - 1, l);
					method217(16384, k, l + 1);
				}
				if (i == 1) {
					method217(5120, k, l);
					method217(16384, k, l + 1);
					method217(0x10000, k + 1, l);
				}
				if (i == 2) {
					method217(20480, k, l);
					method217(0x10000, k + 1, l);
					method217(1024, k, l - 1);
				}
				if (i == 3) {
					method217(0x14000, k, l);
					method217(1024, k, l - 1);
					method217(4096, k - 1, l);
				}
			}
		}
	}

	public void remove(int rotation, int width, int x0, int z0, int length, boolean flag) {
		int flags = 256;
		if (flag) {
			flags += 0x20000;
		}
		if ((rotation == 1) || (rotation == 3)) {
			int tmp = width;
			width = length;
			length = tmp;
		}
		for (int x = x0; x < (x0 + width); x++) {
			if ((x >= 0) && (x < anInt292)) {
				for (int z = z0; z < (z0 + length); z++) {
					if ((z >= 0) && (z < anInt293)) {
						method217(flags, x, z);
					}
				}
			}
		}
	}

	public void method217(int flag, int x, int z) {
		flags[x][z] &= 0xffffff - flag;
	}

	public void method218(int j, int k) {
		flags[k][j] &= 0xdfffff;
	}

	public boolean method219(int sx, int sz, int dx, int dz, int locAngle, int locType) {
		if ((sx == dx) && (sz == dz)) {
			return true;
		}
		if (locType == 0) {
			if (locAngle == 0) {
				if ((sx == (dx - 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0)) {
					return true;
				}
			} else if (locAngle == 1) {
				if ((sx == dx) && (sz == (dz + 1))) {
					return true;
				}
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
					return true;
				}
			} else if (locAngle == 2) {
				if ((sx == (dx + 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0)) {
					return true;
				}
			} else if (locAngle == 3) {
				if ((sx == dx) && (sz == (dz - 1))) {
					return true;
				}
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
					return true;
				}
			}
		}
		if (locType == 2) {
			if (locAngle == 0) {
				if ((sx == (dx - 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1))) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0)) {
					return true;
				}
			} else if (locAngle == 1) {
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1))) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0)) {
					return true;
				}
			} else if (locAngle == 2) {
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1))) {
					return true;
				}
			} else if (locAngle == 3) {
				if ((sx == (dx - 1)) && (sz == dz)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
					return true;
				}
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1))) {
					return true;
				}
			}
		}
		if (locType == 9) {
			if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x20) == 0)) {
				return true;
			}
			if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 2) == 0)) {
				return true;
			}
			if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 8) == 0)) {
				return true;
			}
			return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x80) == 0);
		}
		return false;
	}

	public boolean method220(int sx, int sz, int dx, int dz, int locType, int locAngle) {
		if ((sx == dx) && (sz == dz)) {
			return true;
		}
		if ((locType == 6) || (locType == 7)) {
			if (locType == 7) {
				locAngle = (locAngle + 2) & 3;
			}
			if (locAngle == 0) {
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x80) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 2) == 0)) {
					return true;
				}
			} else if (locAngle == 1) {
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 8) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 2) == 0)) {
					return true;
				}
			} else if (locAngle == 2) {
				if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 8) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x20) == 0)) {
					return true;
				}
			} else if (locAngle == 3) {
				if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x80) == 0)) {
					return true;
				}
				if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x20) == 0)) {
					return true;
				}
			}
		}
		if (locType == 8) {
			if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x20) == 0)) {
				return true;
			}
			if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 2) == 0)) {
				return true;
			}
			if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 8) == 0)) {
				return true;
			}
			return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x80) == 0);
		}
		return false;
	}

	public boolean method221(int x, int z, int dx, int dz, int locWidth, int locLength, int locInteractionFlags) {
		int x1 = (dx + locWidth) - 1;
		int z1 = (dz + locLength) - 1;
		if ((x >= dx) && (x <= x1) && (z >= dz) && (z <= z1)) {
			return true;
		}
		if ((x == (dx - 1)) && (z >= dz) && (z <= z1) && ((this.flags[x - anInt290][z - anInt291] & 8) == 0) && ((locInteractionFlags & 8) == 0)) {
			return true;
		}
		if ((x == (x1 + 1)) && (z >= dz) && (z <= z1) && ((this.flags[x - anInt290][z - anInt291] & 0x80) == 0) && ((locInteractionFlags & 2) == 0)) {
			return true;
		}
		if ((z == (dz - 1)) && (x >= dx) && (x <= x1) && ((this.flags[x - anInt290][z - anInt291] & 2) == 0) && ((locInteractionFlags & 4) == 0)) {
			return true;
		}
		return (z == (z1 + 1)) && (x >= dx) && (x <= x1) && ((this.flags[x - anInt290][z - anInt291] & 0x20) == 0) && ((locInteractionFlags & 1) == 0);
	}

}
