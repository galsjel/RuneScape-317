// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class VarbitType {

    public static VarbitType[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("varbit.dat"));
        int count = buffer.read16U();

        if (instances == null) {
            instances = new VarbitType[count];
        }

        for (int j = 0; j < count; j++) {
            if (instances[j] == null) {
                instances[j] = new VarbitType();
            }
            instances[j].read(buffer);
        }
        if (buffer.position != buffer.data.length) {
            System.out.println("varbit load mismatch");
        }
    }

    public int varp;
    /**
     * The least significant bit.
     */
    public int lsb;
    /**
     * The most significant bit.
     */
    public int msb;

    public VarbitType() {
    }

    public void read(Buffer in) {
        do {
            int code = in.read8U();
            if (code == 0) {
                return;
            } else if (code == 1) {
                varp = in.read16U();
                lsb = in.read8U();
                msb = in.read8U();
            } else if (code == 10) {
                in.readString();
            } else if (code == 3 || code == 4) {
                in.read32();
            } else {
                System.out.println("Error unrecognised varbit config code: " + code);
            }
        } while (true);
    }

}
