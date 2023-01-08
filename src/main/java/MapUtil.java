// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class MapUtil {

    public static int rotateX(int x, int z, int rotation) {
        rotation &= 3;
        if (rotation == 0) {
            return x;
        }
        if (rotation == 1) {
            return z;
        }
        if (rotation == 2) {
            return 7 - x;
        } else {
            return 7 - z;
        }
    }

    public static int rotateZ(int x, int z, int rotation) {
        rotation &= 3;
        if (rotation == 0) {
            return z;
        }
        if (rotation == 1) {
            return 7 - x;
        }
        if (rotation == 2) {
            return 7 - z;
        } else {
            return x;
        }
    }

    public static int rotateLocX(int x, int z, int sizeX, int sizeZ, int rotation) {
        rotation &= 3;
        if (rotation == 0) {
            return x;
        }
        if (rotation == 1) {
            return z;
        }
        if (rotation == 2) {
            return 7 - x - (sizeX - 1);
        } else {
            return 7 - z - (sizeZ - 1);
        }
    }

    public static int rotateLocZ(int x, int z, int sizeX, int sizeZ, int rotation) {
        rotation &= 3;
        if (rotation == 0) {
            return z;
        }
        if (rotation == 1) {
            return 7 - x - (sizeX - 1);
        }
        if (rotation == 2) {
            return 7 - z - (sizeZ - 1);
        } else {
            return x;
        }
    }

}
