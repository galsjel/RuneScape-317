// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

public class IdkType {

    public static int count;
    public static IdkType[] instances;

    public static void unpack(FileArchive archive) throws IOException {
        Buffer buffer = new Buffer(archive.read("idk.dat"));
        count = buffer.readU16();
        if (instances == null) {
            instances = new IdkType[count];
        }
        for (int j = 0; j < count; j++) {
            if (instances[j] == null) {
                instances[j] = new IdkType();
            }
            instances[j].read(buffer);
        }
    }

    public transient final int[] colorSrc = new int[6];
    public transient final int[] colorDst = new int[6];

    public transient final Integer[] chatModelIDs = {-1, -1, -1, -1, -1};
    public transient Integer[] modelIDs;

    @Expose
    @SerializedName("type")
    public int type = -1;
    @Expose
    @SerializedName("disabled")
    public Boolean disabled;
    @Expose
    @SerializedName("model")
    public Model.Ref model;
    @Expose
    @SerializedName("chat_model")
    public Model.Ref chat_model;
    @Expose
    @SerializedName("recolors")
    public Model.Recolor[] recolors;

    public IdkType() {
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();
            if (code == 0) {
                model = Model.ref(modelIDs);
                chat_model = Model.ref(modelIDs);
                recolors = Model.Recolor.make(colorSrc, colorDst, i -> i != 0);
                return;
            } else if (code == 1) {
                type = in.readU8();
            } else if (code == 2) {
                int j = in.readU8();
                modelIDs = new Integer[j];
                for (int k = 0; k < j; k++) {
                    modelIDs[k] = in.readU16();
                }
            } else if (code == 3) {
                disabled = true;
            } else if ((code >= 40) && (code < 50)) {
                colorSrc[code - 40] = in.readU16();
            } else if ((code >= 50) && (code < 60)) {
                colorDst[code - 50] = in.readU16();
            } else if ((code >= 60) && (code < 70)) {
                chatModelIDs[code - 60] = in.readU16();
            } else {
                System.out.println("Error unrecognised identikit config code: " + code);
            }
        }
    }

    public boolean validateModel() {
        if (modelIDs == null) {
            return true;
        }
        boolean loaded = true;
        for (int modelID : modelIDs) {
            if (!Model.loaded(modelID)) {
                loaded = false;
            }
        }
        return loaded;
    }

    public Model getModel() {
        if (modelIDs == null) {
            return null;
        }
        Model[] models = new Model[modelIDs.length];
        for (int i = 0; i < modelIDs.length; i++) {
            models[i] = Model.tryGet(modelIDs[i]);
        }

        Model model;

        if (models.length == 1) {
            model = models[0];
        } else {
            model = Model.join_prebuilt(models.length, models);
        }

        for (int i = 0; i < 6; i++) {
            if (colorSrc[i] == 0) {
                break;
            }
            model.recolor(colorSrc[i], colorDst[i]);
        }

        return model;
    }

    public boolean validateHeadModel() {
        boolean loaded = true;
        for (int i = 0; i < 5; i++) {
            if ((chatModelIDs[i] != -1) && !Model.loaded(chatModelIDs[i])) {
                loaded = false;
            }
        }
        return loaded;
    }

    public Model getHeadModel() {
        Model[] models = new Model[5];
        int i = 0;
        for (int j = 0; j < 5; j++) {
            if (chatModelIDs[j] != -1) {
                models[i++] = Model.tryGet(chatModelIDs[j]);
            }
        }
        Model model = Model.join_prebuilt(i, models);
        for (int k = 0; k < 6; k++) {
            if (colorSrc[k] == 0) {
                break;
            }
            model.recolor(colorSrc[k], colorDst[k]);
        }
        return model;
    }

}
