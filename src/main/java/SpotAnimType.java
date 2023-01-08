// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class SpotAnimType {

    public static int count;
    public static SpotAnimType[] instances;
    public static LRUMap<Integer, Model> modelCache = new LRUMap<>(30);

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("spotanim.dat"));
        count = buffer.readU16();

        if (instances == null) {
            instances = new SpotAnimType[count];
        }

        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new SpotAnimType();
            }
            instances[i].index = i;
            instances[i].read(buffer);
        }
    }

    public final int[] colorSrc = new int[6];
    public final int[] colorDst = new int[6];
    public int index;
    public int modelID;
    public int seqID = -1;
    public SeqType seq;
    public int scaleXY = 128;
    public int scaleZ = 128;
    public int rotation;
    public int lightAmbient;
    public int lightAttenuation;

    public SpotAnimType() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                return;
            } else if (code == 1) {
                modelID = in.readU16();
            } else if (code == 2) {
                seqID = in.readU16();
                if (SeqType.instances != null) {
                    seq = SeqType.instances[seqID];
                }
            } else if (code == 4) {
                scaleXY = in.readU16();
            } else if (code == 5) {
                scaleZ = in.readU16();
            } else if (code == 6) {
                rotation = in.readU16();
            } else if (code == 7) {
                lightAmbient = in.readU8();
            } else if (code == 8) {
                lightAttenuation = in.readU8();
            } else if ((code >= 40) && (code < 50)) {
                colorSrc[code - 40] = in.readU16();
            } else if ((code >= 50) && (code < 60)) {
                colorDst[code - 50] = in.readU16();
            } else {
                System.out.println("Error unrecognised spotanim config code: " + code);
            }
        }
    }

    public Model getModel() {
        Model model = modelCache.get(index);

        if (model != null) {
            return model;
        }

        model = Model.tryGet(modelID);

        if (model == null) {
            return null;
        }

        for (int i = 0; i < 6; i++) {
            if (colorSrc[0] != 0) {
                model.recolor(colorSrc[i], colorDst[i]);
            }
        }

        modelCache.put(index, model);
        return model;
    }

}
