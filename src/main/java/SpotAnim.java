// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class SpotAnim {

    public static int count;
    public static SpotAnim[] instances;
    public static LRUMap<Integer, Model> modelCache = new LRUMap<>(30);

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("spotanim.dat"));
        count = buffer.readU16();

        if (instances == null) {
            instances = new SpotAnim[count];
        }

        for (int i = 0; i < count; i++) {
            if (instances[i] == null) {
                instances[i] = new SpotAnim();
            }
            instances[i].index = i;
            instances[i].read(buffer);
        }
    }

    public final int[] colorSrc = new int[6];
    public final int[] colorDst = new int[6];
    public int modelID;
    public transient Animation seq;
    public int scaleXZ = 128;
    public int scaleY = 128;
    public int rotateY;
    public int lightAmbient;
    public int lightContrast;
    @Expose
    @SerializedName("id")
    public int index;
    @Expose
    @SerializedName("animation")
    public Integer animation;
    @Expose
    @SerializedName("light")
    public Model.Light light;

    @Expose
    @SerializedName("model")
    public Model.Ref model;
    @Expose
    @SerializedName("recolors")
    public Model.Recolor[] recolors;

    public SpotAnim() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                model = Model.ref(modelID);
                if (model != null) {
                    if (scaleXZ != 128) {
                        model.scaleX = model.scaleZ = scaleXZ;
                    }
                    if (scaleY != 128) {
                        model.scaleY = scaleY;
                    }
                    if (rotateY != 0) {
                        model.rotateY = rotateY;
                    }
                }
                light = Model.makeLight(lightAmbient, lightContrast);
                recolors = Model.Recolor.make(colorSrc,colorDst,i->i!=0);
                return;
            } else if (code == 1) {
                modelID = in.readU16();
            } else if (code == 2) {
                animation = in.readU16();
                if (Animation.instances != null) {
                    seq = Animation.instances[animation];
                }
            } else if (code == 4) {
                scaleXZ = in.readU16();
            } else if (code == 5) {
                scaleY = in.readU16();
            } else if (code == 6) {
                rotateY = in.readU16();
            } else if (code == 7) {
                lightAmbient = in.readU8();
            } else if (code == 8) {
                lightContrast = in.readU8();
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
