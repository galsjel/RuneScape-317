// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;

import java.io.IOException;

public class Varbit {

    public static Varbit[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("varbit.dat"));
        int count = buffer.readU16();

        if (instances == null) {
            instances = new Varbit[count];
        }

        for (int j = 0; j < count; j++) {
            if (instances[j] == null) {
                instances[j] = new Varbit();
            }
            instances[j].read(buffer);
        }
        if (buffer.position != buffer.data.length) {
            System.out.println("varbit load mismatch");
        }
    }

    @Expose
    public int varp;
    /**
     * The least significant bit.
     */
    @Expose
    public int lsb;
    /**
     * The most significant bit.
     */
    @Expose
    public int msb;

    public Varbit() {
    }

    public void read(Buffer in) {
        do {
            int code = in.readU8();
            if (code == 0) {
                return;
            } else if (code == 1) {
                varp = in.readU16();
                lsb = in.readU8();
                msb = in.readU8();
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
