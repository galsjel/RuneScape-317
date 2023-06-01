// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;

import java.io.IOException;

public class Varp {

    public static int count;
    public static Varp[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("varp.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new Varp[count];
        }
        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new Varp();
                instances[i].id = i;
            }
            instances[i].read(buffer);
        }
        if (buffer.position != buffer.data.length) {
            System.out.println("varptype load mismatch");
        }
    }

    @Expose
    public int id;
    @Expose
    public Integer code;


    public Varp() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                return;
            } else if (code == 1 || code == 2) {
                System.out.println("in.readU8() = " + in.readU8());
            } else if (code == 5) {
                this.code = in.readU16();
            } else if (code == 7 || code == 12) {
                System.out.println("in.read32() = " + in.read32());
            } else if (code == 10) {
                System.out.println("in.readString() = " + in.readString());
            } else {
                System.out.println("Error unrecognised varp config code: " + code);
            }
        }
    }

}
