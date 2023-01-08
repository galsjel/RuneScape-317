// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class VarpType {

    public static int count;
    public static VarpType[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("varp.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new VarpType[count];
        }
        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new VarpType();
            }
            instances[i].read(buffer);
        }
        if (buffer.position != buffer.data.length) {
            System.out.println("varptype load mismatch");
        }
    }

    public int type;

    public VarpType() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                return;
            } else if (code == 1 || code == 2) {
                in.readU8();
            } else if (code == 5) {
                type = in.readU16();
            } else if (code == 7 || code == 12) {
                in.read32();
            } else if (code == 10) {
                in.readString();
            } else {
                System.out.println("Error unrecognised varp config code: " + code);
            }
        }
    }

}
