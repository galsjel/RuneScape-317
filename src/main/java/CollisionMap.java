// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class CollisionMap {

    public static final int FLAG_BLOCK_ENTITY_NW = 0x1;
    public static final int FLAG_BLOCK_ENTITY_N = 0x2;
    public static final int FLAG_BLOCK_ENTITY_NE = 0x4;
    public static final int FLAG_BLOCK_ENTITY_E = 0x8;
    public static final int FLAG_BLOCK_ENTITY_SE = 0x10;
    public static final int FLAG_BLOCK_ENTITY_S = 0x20;
    public static final int FLAG_BLOCK_ENTITY_SW = 0x40;
    public static final int FLAG_BLOCK_ENTITY_W = 0x80;
    public static final int FLAG_BLOCK_ENTITY = 0x100;
    public static final int FLAG_BLOCK_PROJECTILE_NW = 0x200;
    public static final int FLAG_BLOCK_PROJECTILE_N = 0x400;
    public static final int FLAG_BLOCK_PROJECTILE_NE = 0x800;
    public static final int FLAG_BLOCK_PROJECTILE_E = 0x1000;
    public static final int FLAG_BLOCK_PROJECTILE_SE = 0x2000;
    public static final int FLAG_BLOCK_PROJECTILE_S = 0x4000;
    public static final int FLAG_BLOCK_PROJECTILE_SW = 0x8000;
    public static final int FLAG_BLOCK_PROJECTILE_W = 0x10000;
    public static final int FLAG_BLOCK_PROJECTILE = 0x20000;
    public static final int FLAG_UNINITIALIZED = 0x1000000;
    public static final int FLAG_CLOSED = 0xFFFFFF;

    public final int sizeX;
    public final int sizeZ;
    public final int[][] flags;

    public CollisionMap(int sizeX, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        flags = new int[this.sizeX][this.sizeZ];
        reset();
    }

    public void reset() {
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                if ((x == 0) || (z == 0) || (x == (sizeX - 1)) || (z == (sizeZ - 1))) {
                    flags[x][z] = FLAG_CLOSED;
                } else {
                    flags[x][z] = FLAG_UNINITIALIZED;
                }
            }
        }
    }

    public void addWall(int x, int z, int type, int rotation, boolean projectiles) {
        if (type == 0) {
            if (rotation == 0) {
                add(x, z, FLAG_BLOCK_ENTITY_W);
                add(x - 1, z, FLAG_BLOCK_ENTITY_E);
            } else if (rotation == 1) {
                add(x, z, FLAG_BLOCK_ENTITY_N);
                add(x, z + 1, FLAG_BLOCK_ENTITY_S);
            } else if (rotation == 2) {
                add(x, z, FLAG_BLOCK_ENTITY_E);
                add(x + 1, z, FLAG_BLOCK_ENTITY_W);
            } else if (rotation == 3) {
                add(x, z, FLAG_BLOCK_ENTITY_S);
                add(x, z - 1, FLAG_BLOCK_ENTITY_N);
            }
        } else if ((type == 1) || (type == 3)) {
            if (rotation == 0) {
                add(x, z, FLAG_BLOCK_ENTITY_NW);
                add(x - 1, z + 1, FLAG_BLOCK_ENTITY_SE);
            } else if (rotation == 1) {
                add(x, z, FLAG_BLOCK_ENTITY_NE);
                add(x + 1, z + 1, FLAG_BLOCK_ENTITY_SW);
            } else if (rotation == 2) {
                add(x, z, FLAG_BLOCK_ENTITY_SE);
                add(x + 1, z - 1, FLAG_BLOCK_ENTITY_NW);
            } else if (rotation == 3) {
                add(x, z, FLAG_BLOCK_ENTITY_SW);
                add(x - 1, z - 1, FLAG_BLOCK_ENTITY_NE);
            }
        } else if (type == 2) {
            if (rotation == 0) {
                add(x, z, FLAG_BLOCK_ENTITY_W | FLAG_BLOCK_ENTITY_N);
                add(x - 1, z, FLAG_BLOCK_ENTITY_E);
                add(x, z + 1, FLAG_BLOCK_ENTITY_S);
            } else if (rotation == 1) {
                add(x, z, FLAG_BLOCK_ENTITY_E | FLAG_BLOCK_ENTITY_N);
                add(x, z + 1, FLAG_BLOCK_ENTITY_S);
                add(x + 1, z, FLAG_BLOCK_ENTITY_W);
            } else if (rotation == 2) {
                add(x, z, FLAG_BLOCK_ENTITY_E | FLAG_BLOCK_ENTITY_S);
                add(x + 1, z, FLAG_BLOCK_ENTITY_W);
                add(x, z - 1, FLAG_BLOCK_ENTITY_N);
            } else if (rotation == 3) {
                add(x, z, FLAG_BLOCK_ENTITY_W | FLAG_BLOCK_ENTITY_S);
                add(x, z - 1, FLAG_BLOCK_ENTITY_N);
                add(x - 1, z, FLAG_BLOCK_ENTITY_E);
            }
        }

        if (projectiles) {
            if (type == 0) {
                if (rotation == 0) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_W);
                    add(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                } else if (rotation == 1) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_N);
                    add(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                } else if (rotation == 2) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_E);
                    add(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                } else if (rotation == 3) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_S);
                    add(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                }
            } else if ((type == 1) || (type == 3)) {
                if (rotation == 0) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_NW);
                    add(x - 1, z + 1, FLAG_BLOCK_PROJECTILE_SE);
                } else if (rotation == 1) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_NE);
                    add(x + 1, z + 1, FLAG_BLOCK_PROJECTILE_SW);
                } else if (rotation == 2) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_SE);
                    add(x + 1, z - 1, FLAG_BLOCK_PROJECTILE_NW);
                } else if (rotation == 3) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_SW);
                    add(x - 1, z - 1, FLAG_BLOCK_PROJECTILE_NE);
                }
            } else if (type == 2) {
                if (rotation == 0) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_W | FLAG_BLOCK_PROJECTILE_N);
                    add(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                    add(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                } else if (rotation == 1) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_E | FLAG_BLOCK_PROJECTILE_N);
                    add(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                    add(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                } else if (rotation == 2) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_E | FLAG_BLOCK_PROJECTILE_S);
                    add(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                    add(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                } else if (rotation == 3) {
                    add(x, z, FLAG_BLOCK_PROJECTILE_W | FLAG_BLOCK_PROJECTILE_S);
                    add(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                    add(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                }
            }
        }
    }

    public void add(boolean blocksProjectiles, int sizeX, int sizeZ, int x, int z, int rotation) {
        int flags = FLAG_BLOCK_ENTITY;

        if (blocksProjectiles) {
            flags += FLAG_BLOCK_PROJECTILE;
        }

        if ((rotation == 1) || (rotation == 3)) {
            int tmp = sizeX;
            sizeX = sizeZ;
            sizeZ = tmp;
        }

        for (int tx = x; tx < (x + sizeX); tx++) {
            if ((tx >= 0) && (tx < this.sizeX)) {
                for (int tz = z; tz < (z + sizeZ); tz++) {
                    if ((tz >= 0) && (tz < this.sizeZ)) {
                        add(tx, tz, flags);
                    }
                }
            }
        }
    }

    public void addSolid(int x, int z) {
        flags[x][z] |= FLAG_UNINITIALIZED;
    }

    public void add(int x, int z, int flags) {
        this.flags[x][z] |= flags;
    }

    public void remove(int x, int z, int rotation, int type, boolean projectiles) {
        if (type == 0) {
            if (rotation == 0) {
                remove(x, z, FLAG_BLOCK_ENTITY_W);
                remove(x - 1, z, FLAG_BLOCK_ENTITY_E);
            } else if (rotation == 1) {
                remove(x, z, FLAG_BLOCK_ENTITY_N);
                remove(x, z + 1, FLAG_BLOCK_ENTITY_S);
            } else if (rotation == 2) {
                remove(x, z, FLAG_BLOCK_ENTITY_E);
                remove(x + 1, z, FLAG_BLOCK_ENTITY_W);
            } else if (rotation == 3) {
                remove(x, z, FLAG_BLOCK_ENTITY_S);
                remove(x, z - 1, FLAG_BLOCK_ENTITY_N);
            }
        } else if ((type == 1) || (type == 3)) {
            if (rotation == 0) {
                remove(x, z, FLAG_BLOCK_ENTITY_NW);
                remove(x - 1, z + 1, FLAG_BLOCK_ENTITY_SE);
            } else if (rotation == 1) {
                remove(x, z, FLAG_BLOCK_ENTITY_NE);
                remove(x + 1, z + 1, FLAG_BLOCK_ENTITY_SW);
            } else if (rotation == 2) {
                remove(x, z, FLAG_BLOCK_ENTITY_SE);
                remove(x + 1, z - 1, FLAG_BLOCK_ENTITY_NW);
            } else if (rotation == 3) {
                remove(x, z, FLAG_BLOCK_ENTITY_SW);
                remove(x - 1, z - 1, FLAG_BLOCK_ENTITY_NE);
            }
        } else if (type == 2) {
            if (rotation == 0) {
                remove(x, z, FLAG_BLOCK_ENTITY_W | FLAG_BLOCK_ENTITY_N);
                remove(x - 1, z, FLAG_BLOCK_ENTITY_E);
                remove(x, z + 1, FLAG_BLOCK_ENTITY_S);
            } else if (rotation == 1) {
                remove(x, z, FLAG_BLOCK_ENTITY_E | FLAG_BLOCK_ENTITY_N);
                remove(x, z + 1, FLAG_BLOCK_ENTITY_S);
                remove(x + 1, z, FLAG_BLOCK_ENTITY_W);
            } else if (rotation == 2) {
                remove(x, z, FLAG_BLOCK_ENTITY_E | FLAG_BLOCK_ENTITY_S);
                remove(x + 1, z, FLAG_BLOCK_ENTITY_W);
                remove(x, z - 1, FLAG_BLOCK_ENTITY_N);
            } else if (rotation == 3) {
                remove(x, z, FLAG_BLOCK_ENTITY_W | FLAG_BLOCK_ENTITY_S);
                remove(x, z - 1, FLAG_BLOCK_ENTITY_N);
                remove(x - 1, z, FLAG_BLOCK_ENTITY_E);
            }
        }
        if (projectiles) {
            if (type == 0) {
                if (rotation == 0) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_W);
                    remove(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                } else if (rotation == 1) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_N);
                    remove(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                } else if (rotation == 2) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_E);
                    remove(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                } else if (rotation == 3) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_S);
                    remove(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                }
            } else if ((type == 1) || (type == 3)) {
                if (rotation == 0) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_NW);
                    remove(x - 1, z + 1, FLAG_BLOCK_PROJECTILE_SE);
                } else if (rotation == 1) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_NE);
                    remove(x + 1, z + 1, FLAG_BLOCK_PROJECTILE_SW);
                } else if (rotation == 2) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_SE);
                    remove(x + 1, z - 1, FLAG_BLOCK_PROJECTILE_NW);
                } else if (rotation == 3) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_SW);
                    remove(x - 1, z - 1, FLAG_BLOCK_PROJECTILE_NE);
                }
            } else if (type == 2) {
                if (rotation == 0) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_W | FLAG_BLOCK_PROJECTILE_N);
                    remove(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                    remove(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                } else if (rotation == 1) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_E | FLAG_BLOCK_PROJECTILE_N);
                    remove(x, z + 1, FLAG_BLOCK_PROJECTILE_S);
                    remove(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                } else if (rotation == 2) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_E | FLAG_BLOCK_PROJECTILE_S);
                    remove(x + 1, z, FLAG_BLOCK_PROJECTILE_W);
                    remove(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                } else if (rotation == 3) {
                    remove(x, z, FLAG_BLOCK_PROJECTILE_W | FLAG_BLOCK_PROJECTILE_S);
                    remove(x, z - 1, FLAG_BLOCK_PROJECTILE_N);
                    remove(x - 1, z, FLAG_BLOCK_PROJECTILE_E);
                }
            }
        }
    }

    public void remove(int rotation, int sizeX, int x0, int z0, int sizeZ, boolean projectiles) {
        int flags = FLAG_BLOCK_ENTITY;

        if (projectiles) {
            flags += FLAG_BLOCK_PROJECTILE;
        }

        if ((rotation == 1) || (rotation == 3)) {
            int tmp = sizeX;
            sizeX = sizeZ;
            sizeZ = tmp;
        }
        for (int x = x0; x < (x0 + sizeX); x++) {
            if ((x >= 0) && (x < this.sizeX)) {
                for (int z = z0; z < (z0 + sizeZ); z++) {
                    if ((z >= 0) && (z < this.sizeZ)) {
                        remove(x, z, flags);
                    }
                }
            }
        }
    }

    public void remove(int x, int z, int flags) {
        this.flags[x][z] &= 0xffffff - flags;
    }

    public void removeSolid(int x, int z) {
        flags[x][z] &= 0xdfffff;
    }

    public boolean reachedDestination(int sx, int sz, int dx, int dz, int rotation, int type) {
        if ((sx == dx) && (sz == dz)) {
            return true;
        }

        if (type == 0) {
            if (rotation == 0) {
                if ((sx == (dx - 1)) && (sz == dz)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0);
                }
            } else if (rotation == 1) {
                if ((sx == dx) && (sz == (dz + 1))) {
                    return true;
                } else if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
                    return true;
                } else {
                    return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0);
                }
            } else if (rotation == 2) {
                if ((sx == (dx + 1)) && (sz == dz)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0);
                }
            } else if (rotation == 3) {
                if ((sx == dx) && (sz == (dz - 1))) {
                    return true;
                } else if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
                    return true;
                } else {
                    return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0);
                }
            }
        } else if (type == 2) {
            if (rotation == 0) {
                if ((sx == (dx - 1)) && (sz == dz)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1))) {
                    return true;
                } else if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0);
                }
            } else if (rotation == 1) {
                if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1))) {
                    return true;
                } else if ((sx == (dx + 1)) && (sz == dz)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & 0x1280102) == 0);
                }
            } else if (rotation == 2) {
                if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280108) == 0)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
                    return true;
                } else if ((sx == (dx + 1)) && (sz == dz)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1));
                }
            } else if (rotation == 3) {
                if ((sx == (dx - 1)) && (sz == dz)) {
                    return true;
                } else if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & 0x1280120) == 0)) {
                    return true;
                } else if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & 0x1280180) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1));
                }
            }
        } else if (type == 9) {
            if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_S) == 0)) {
                return true;
            } else if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_N) == 0)) {
                return true;
            } else if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_E) == 0)) {
                return true;
            }
            return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_W) == 0);
        }
        return false;
    }

    public boolean reachedWall(int sx, int sz, int dx, int dz, int type, int rotation) {
        if ((sx == dx) && (sz == dz)) {
            return true;
        }
        if ((type == 6) || (type == 7)) {
            if (type == 7) {
                rotation = (rotation + 2) & 3;
            }
            if (rotation == 0) {
                if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_W) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_N) == 0);
                }
            } else if (rotation == 1) {
                if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_E) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_N) == 0);
                }
            } else if (rotation == 2) {
                if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_E) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_S) == 0);
                }
            } else if (rotation == 3) {
                if ((sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_W) == 0)) {
                    return true;
                } else {
                    return (sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_S) == 0);
                }
            }
        } else if (type == 8) {
            if ((sx == dx) && (sz == (dz + 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_S) == 0)) {
                return true;
            } else if ((sx == dx) && (sz == (dz - 1)) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_N) == 0)) {
                return true;
            } else if ((sx == (dx - 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_E) == 0)) {
                return true;
            }
            return (sx == (dx + 1)) && (sz == dz) && ((flags[sx][sz] & FLAG_BLOCK_ENTITY_W) == 0);
        }
        return false;
    }

    public boolean reachedLoc(int srcX, int srcZ, int dstX, int dstZ, int dstSizeX, int dstSizeZ, int interactionSides) {
        int maxX = (dstX + dstSizeX) - 1;
        int maxZ = (dstZ + dstSizeZ) - 1;

        if ((srcX >= dstX) && (srcX <= maxX) && (srcZ >= dstZ) && (srcZ <= maxZ)) {
            return true;
        } else if ((srcX == (dstX - 1)) && (srcZ >= dstZ) && (srcZ <= maxZ) && ((this.flags[srcX][srcZ] & FLAG_BLOCK_ENTITY_E) == 0) && ((interactionSides & 8) == 0)) {
            return true;
        } else if ((srcX == (maxX + 1)) && (srcZ >= dstZ) && (srcZ <= maxZ) && ((this.flags[srcX][srcZ] & FLAG_BLOCK_ENTITY_W) == 0) && ((interactionSides & 2) == 0)) {
            return true;
        } else if ((srcZ == (dstZ - 1)) && (srcX >= dstX) && (srcX <= maxX) && ((this.flags[srcX][srcZ] & FLAG_BLOCK_ENTITY_N) == 0) && ((interactionSides & 4) == 0)) {
            return true;
        }
        return (srcZ == (maxZ + 1)) && (srcX >= dstX) && (srcX <= maxX) && ((this.flags[srcX][srcZ] & FLAG_BLOCK_ENTITY_S) == 0) && ((interactionSides & 1) == 0);
    }

}
