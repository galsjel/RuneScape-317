// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class ObjType {

    public static LRUMap<Integer, Image24> iconCache = new LRUMap<>(100);
    public static LRUMap<Integer, Model> modelCache = new LRUMap<>(50);
    public static ObjType[] cached;
    public static int cachePos;
    public static Buffer dat;
    public static int[] typeOffset;
    public static int count;

    public static void unload() {
        modelCache = null;
        iconCache = null;
        typeOffset = null;
        cached = null;
        dat = null;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("obj.dat"));
        Buffer idx = new Buffer(archive.read("obj.idx"));
        count = idx.read16U();
        typeOffset = new int[count];
        int offset = 2;
        for (int j = 0; j < count; j++) {
            typeOffset[j] = offset;
            offset += idx.read16U();
        }
        cached = new ObjType[10];
        for (int k = 0; k < 10; k++) {
            cached[k] = new ObjType();
        }
    }

    public static ObjType get(int id) {
        for (int j = 0; j < 10; j++) {
            if (cached[j].id == id) {
                return cached[j];
            }
        }
        cachePos = (cachePos + 1) % 10;
        ObjType type = cached[cachePos];
        dat.position = typeOffset[id];
        type.id = id;
        type.reset();
        type.read(dat);
        if (type.certificateID != -1) {
            type.toCertificate();
        }
        if (!Game.members && type.members) {
            type.name = "Members Object";
            type.examine = "Login to a members' server to use this object.".getBytes();
            type.options = null;
            type.inventoryOptions = null;
            type.team = 0;
        }
        return type;
    }

    public static Image24 getIcon(int id, int amount, int outlineColor) {
        if (outlineColor == 0) {
            Image24 icon = iconCache.get(id);

            if ((icon != null) && (icon.cropH != amount) && (icon.cropH != -1)) {
                icon.unlink();
                icon = null;
            }

            if (icon != null) {
                return icon;
            }
        }

        ObjType type = get(id);

        if (type.stackID == null) {
            amount = -1;
        }

        if (amount > 1) {
            int newID = -1;
            for (int stack = 0; stack < 10; stack++) {
                if ((amount >= type.stackCount[stack]) && (type.stackCount[stack] != 0)) {
                    newID = type.stackID[stack];
                }
            }
            if (newID != -1) {
                type = get(newID);
            }
        }

        Model model = type.getModel(1);

        if (model == null) {
            return null;
        }

        // this will be the original item icon to draw over the certificate icon (if present)
        Image24 linkedIcon = null;

        if (type.certificateID != -1) {
            linkedIcon = getIcon(type.linkedID, 10, -1);

            if (linkedIcon == null) {
                return null;
            }
        }

        Image24 icon = new Image24(32, 32);

        // store state
        int _cx = Draw3D.centerX;
        int _cy = Draw3D.centerY;
        int[] _loff = Draw3D.lineOffset;
        int[] _pix = Draw2D.pixels;
        int _w = Draw2D.width;
        int _h = Draw2D.height;
        int _l = Draw2D.left;
        int _r = Draw2D.right;
        int _t = Draw2D.top;
        int _b = Draw2D.bottom;

        // set up drawing area
        Draw3D.jagged = false;
        Draw2D.bind(icon.pixels, 32, 32);
        Draw2D.fillRect(0, 0, 32, 32, 0);
        Draw3D.init2D();

        int zoom = type.iconZoom;

        if (outlineColor == -1) {
            zoom = (int) ((double) zoom * 1.5D);
        }

        if (outlineColor > 0) {
            zoom = (int) ((double) zoom * 1.04D);
        }

        int sinPitch = (Draw3D.sin[type.iconPitch] * zoom) >> 16;
        int cosPitch = (Draw3D.cos[type.iconPitch] * zoom) >> 16;

        model.drawSimple(0, type.iconYaw, type.iconRoll, type.iconPitch, type.iconOffsetX, sinPitch + (model.minY / 2) + type.iconOffsetY, cosPitch + type.iconOffsetY);

        // define outline
        for (int x = 31; x >= 0; x--) {
            for (int y = 31; y >= 0; y--) {
                if (icon.pixels[x + (y * 32)] != 0) {
                    continue;
                }
                if ((x > 0) && (icon.pixels[(x - 1) + (y * 32)] > 1)) {
                    icon.pixels[x + (y * 32)] = 1;
                } else if ((y > 0) && (icon.pixels[x + ((y - 1) * 32)] > 1)) {
                    icon.pixels[x + (y * 32)] = 1;
                } else if ((x < 31) && (icon.pixels[x + 1 + (y * 32)] > 1)) {
                    icon.pixels[x + (y * 32)] = 1;
                } else if ((y < 31) && (icon.pixels[x + ((y + 1) * 32)] > 1)) {
                    icon.pixels[x + (y * 32)] = 1;
                }
            }
        }

        // color outline
        if (outlineColor > 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if (icon.pixels[x + (y * 32)] == 0) {
                        if ((x > 0) && (icon.pixels[(x - 1) + (y * 32)] == 1)) {
                            icon.pixels[x + (y * 32)] = outlineColor;
                        } else if ((y > 0) && (icon.pixels[x + ((y - 1) * 32)] == 1)) {
                            icon.pixels[x + (y * 32)] = outlineColor;
                        } else if ((x < 31) && (icon.pixels[x + 1 + (y * 32)] == 1)) {
                            icon.pixels[x + (y * 32)] = outlineColor;
                        } else if ((y < 31) && (icon.pixels[x + ((y + 1) * 32)] == 1)) {
                            icon.pixels[x + (y * 32)] = outlineColor;
                        }
                    }
                }
            }
        }
        // default outline color
        else if (outlineColor == 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if ((icon.pixels[x + (y * 32)] == 0) && (x > 0) && (y > 0) && (icon.pixels[(x - 1) + ((y - 1) * 32)] > 0)) {
                        icon.pixels[x + (y * 32)] = 0x302020;
                    }
                }
            }
        }

        if (type.certificateID != -1) {
            int w = linkedIcon.cropW;
            int h = linkedIcon.cropH;
            linkedIcon.cropW = 32;
            linkedIcon.cropH = 32;
            linkedIcon.draw(0, 0);
            linkedIcon.cropW = w;
            linkedIcon.cropH = h;
        }

        if (outlineColor == 0) {
            iconCache.put(id, icon);
        }

        // restore state
        Draw2D.bind(_pix, _w, _h);
        Draw2D.setBounds(_l, _t, _r, _b);
        Draw3D.centerX = _cx;
        Draw3D.centerY = _cy;
        Draw3D.lineOffset = _loff;
        Draw3D.jagged = true;

        if (type.stackable) {
            icon.cropW = 33;
        } else {
            icon.cropW = 32;
        }

        icon.cropH = amount;
        return icon;
    }

    public byte femaleOffsetY;
    public int cost;
    public int[] srcColor;
    public int id = -1;
    public int[] dstColor;
    public boolean members;
    public int femaleModelID2;
    public int certificateID;
    public int femaleModelID1;
    public int maleModelID0;
    public int maleHeadModelID1;
    public int scaleX;
    public String[] options;
    public int iconOffsetX;
    public String name;
    public int femaleHeadModelID1;
    public int modelID;
    public int maleHeadModelID0;
    public boolean stackable;
    public byte[] examine;
    public int linkedID;
    public int iconZoom;
    public int lightAttenuation;
    public int maleModelID2;
    public int maleModelID1;
    public String[] inventoryOptions;
    public int iconPitch;
    public int scaleY;
    public int scaleZ;
    public int[] stackID;
    public int iconOffsetY;
    public int lightAmbient;
    public int femaleHeadModelID0;
    public int iconYaw;
    public int femaleModelID0;
    public int[] stackCount;
    public int team;
    public int iconRoll;
    public byte maleOffsetY;

    public ObjType() {
    }

    public boolean validateHeadModel(int gender) {
        int modelID0 = maleHeadModelID0;
        int modelID1 = maleHeadModelID1;

        if (gender == 1) {
            modelID0 = femaleHeadModelID0;
            modelID1 = femaleHeadModelID1;
        }

        if (modelID0 == -1) {
            return true;
        }

        boolean valid = Model.validate(modelID0);

        if ((modelID1 != -1) && !Model.validate(modelID1)) {
            valid = false;
        }
        return valid;
    }

    public Model getHeadModel(int gender) {
        int modelID0 = maleHeadModelID0;
        int modelID1 = maleHeadModelID1;

        if (gender == 1) {
            modelID0 = femaleHeadModelID0;
            modelID1 = femaleHeadModelID1;
        }

        if (modelID0 == -1) {
            return null;
        }

        Model model = Model.tryGet(modelID0);

        if (model == null) {
            return null;
        }

        if (modelID1 != -1) {
            model = new Model(2, new Model[]{model, Model.tryGet(modelID1)});
        }

        if (srcColor != null) {
            for (int i = 0; i < srcColor.length; i++) {
                model.recolor(srcColor[i], dstColor[i]);
            }
        }
        return model;
    }

    public boolean validateWornModel(int gender) {
        int modelID0 = maleModelID0;
        int modelID1 = maleModelID1;
        int modelID2 = maleModelID2;

        if (gender == 1) {
            modelID0 = femaleModelID0;
            modelID1 = femaleModelID1;
            modelID2 = femaleModelID2;
        }

        if (modelID0 == -1) {
            return true;
        }

        boolean valid = Model.validate(modelID0);

        if ((modelID1 != -1) && !Model.validate(modelID1)) {
            valid = false;
        }

        if ((modelID2 != -1) && !Model.validate(modelID2)) {
            valid = false;
        }

        return valid;
    }

    public Model getWornModel(int gender) {
        int modelID0 = maleModelID0;
        int modelID1 = maleModelID1;
        int modelID2 = maleModelID2;

        if (gender == 1) {
            modelID0 = femaleModelID0;
            modelID1 = femaleModelID1;
            modelID2 = femaleModelID2;
        }

        if (modelID0 == -1) {
            return null;
        }

        Model model = Model.tryGet(modelID0);

        if (model == null) {
            return null;
        }

        if (modelID1 != -1) {
            if (modelID2 != -1) {
                model = new Model(3, new Model[]{model, Model.tryGet(modelID1), Model.tryGet(modelID2)});
            } else {
                model = new Model(2, new Model[]{model, Model.tryGet(modelID1)});
            }
        }

        if ((gender == 0) && (maleOffsetY != 0)) {
            model.translate(0, maleOffsetY, 0);
        }

        if ((gender == 1) && (femaleOffsetY != 0)) {
            model.translate(0, femaleOffsetY, 0);
        }

        if (srcColor != null) {
            for (int i = 0; i < srcColor.length; i++) {
                model.recolor(srcColor[i], dstColor[i]);
            }
        }

        return model;
    }

    public void reset() {
        modelID = 0;
        name = null;
        examine = null;
        srcColor = null;
        dstColor = null;
        iconZoom = 2000;
        iconPitch = 0;
        iconYaw = 0;
        iconRoll = 0;
        iconOffsetX = 0;
        iconOffsetY = 0;
        stackable = false;
        cost = 1;
        members = false;
        options = null;
        inventoryOptions = null;
        maleModelID0 = -1;
        maleModelID1 = -1;
        maleOffsetY = 0;
        femaleModelID0 = -1;
        femaleModelID1 = -1;
        femaleOffsetY = 0;
        maleModelID2 = -1;
        femaleModelID2 = -1;
        maleHeadModelID0 = -1;
        maleHeadModelID1 = -1;
        femaleHeadModelID0 = -1;
        femaleHeadModelID1 = -1;
        stackID = null;
        stackCount = null;
        linkedID = -1;
        certificateID = -1;
        scaleX = 128;
        scaleZ = 128;
        scaleY = 128;
        lightAmbient = 0;
        lightAttenuation = 0;
        team = 0;
    }

    public void toCertificate() {
        ObjType cert = get(certificateID);
        modelID = cert.modelID;
        iconZoom = cert.iconZoom;
        iconPitch = cert.iconPitch;
        iconYaw = cert.iconYaw;
        iconRoll = cert.iconRoll;
        iconOffsetX = cert.iconOffsetX;
        iconOffsetY = cert.iconOffsetY;
        srcColor = cert.srcColor;
        dstColor = cert.dstColor;
        ObjType linked = get(linkedID);
        name = linked.name;
        members = linked.members;
        cost = linked.cost;

        String s = "a";
        char c = linked.name.charAt(0);

        if ((c == 'A') || (c == 'E') || (c == 'I') || (c == 'O') || (c == 'U')) {
            s = "an";
        }

        examine = ("Swap this note at any bank for " + s + " " + linked.name + ".").getBytes();
        stackable = true;
    }

    /**
     * Retrieves a fully built ground model of this {@link ObjType}.
     *
     * @param count the stack count.
     * @return the model or <code>null</code> if unavailable.
     */
    public Model getModel(int count) {
        if ((stackID != null) && (count > 1)) {
            int id = -1;

            for (int i = 0; i < 10; i++) {
                if ((count >= stackCount[i]) && (stackCount[i] != 0)) {
                    id = stackID[i];
                }
            }

            if (id != -1) {
                return get(id).getModel(1);
            }
        }

        Model model = modelCache.get(id);

        if (model != null) {
            return model;
        }

        model = Model.tryGet(modelID);

        if (model == null) {
            return null;
        }

        if ((scaleX != 128) || (scaleZ != 128) || (scaleY != 128)) {
            model.scale(scaleX, scaleY, scaleZ);
        }

        if (srcColor != null) {
            for (int i = 0; i < srcColor.length; i++) {
                model.recolor(srcColor[i], dstColor[i]);
            }
        }

        model.calculateNormals(64 + lightAmbient, 768 + lightAttenuation, -50, -10, -50, true);
        model.pickable = true;
        modelCache.put(id, model);
        return model;
    }

    /**
     * Retrieves the ground model of this {@link ObjType} without caching, rescaling, calculating normals, applying lighting, or making pickable. As opposed to {@link #getModel(int)}.
     *
     * @param count the stack count.
     * @return the model or <code>null</code> if unavailable.
     */
    public Model getInterfaceModel(int count) {
        if ((stackID != null) && (count > 1)) {
            int id = -1;

            for (int i = 0; i < 10; i++) {
                if ((count >= stackCount[i]) && (stackCount[i] != 0)) {
                    id = stackID[i];
                }
            }

            if (id != -1) {
                return get(id).getInterfaceModel(1);
            }
        }

        Model model = Model.tryGet(modelID);

        if (model == null) {
            return null;
        }

        if (srcColor != null) {
            for (int i = 0; i < srcColor.length; i++) {
                model.recolor(srcColor[i], dstColor[i]);
            }
        }
        return model;
    }

    public void read(Buffer buffer) {
        while (true) {
            int code = buffer.read8U();

            if (code == 0) {
                return;
            } else if (code == 1) {
                modelID = buffer.read16U();
            } else if (code == 2) {
                name = buffer.readString();
            } else if (code == 3) {
                examine = buffer.readStringRaw();
            } else if (code == 4) {
                iconZoom = buffer.read16U();
            } else if (code == 5) {
                iconPitch = buffer.read16U();
            } else if (code == 6) {
                iconYaw = buffer.read16U();
            } else if (code == 7) {
                iconOffsetX = buffer.read16U();
                if (iconOffsetX > 32767) {
                    iconOffsetX -= 0x10000;
                }
            } else if (code == 8) {
                iconOffsetY = buffer.read16U();
                if (iconOffsetY > 32767) {
                    iconOffsetY -= 0x10000;
                }
            } else if (code == 10) {
                buffer.read16U();
            } else if (code == 11) {
                stackable = true;
            } else if (code == 12) {
                cost = buffer.read32();
            } else if (code == 16) {
                members = true;
            } else if (code == 23) {
                maleModelID0 = buffer.read16U();
                maleOffsetY = buffer.read();
            } else if (code == 24) {
                maleModelID1 = buffer.read16U();
            } else if (code == 25) {
                femaleModelID0 = buffer.read16U();
                femaleOffsetY = buffer.read();
            } else if (code == 26) {
                femaleModelID1 = buffer.read16U();
            } else if ((code >= 30) && (code < 35)) {
                if (options == null) {
                    options = new String[5];
                }
                options[code - 30] = buffer.readString();
                if (options[code - 30].equalsIgnoreCase("hidden")) {
                    options[code - 30] = null;
                }
            } else if ((code >= 35) && (code < 40)) {
                if (inventoryOptions == null) {
                    inventoryOptions = new String[5];
                }
                inventoryOptions[code - 35] = buffer.readString();
            } else if (code == 40) {
                int recolorCount = buffer.read8U();
                srcColor = new int[recolorCount];
                dstColor = new int[recolorCount];
                for (int i = 0; i < recolorCount; i++) {
                    srcColor[i] = buffer.read16U();
                    dstColor[i] = buffer.read16U();
                }
            } else if (code == 78) {
                maleModelID2 = buffer.read16U();
            } else if (code == 79) {
                femaleModelID2 = buffer.read16U();
            } else if (code == 90) {
                maleHeadModelID0 = buffer.read16U();
            } else if (code == 91) {
                femaleHeadModelID0 = buffer.read16U();
            } else if (code == 92) {
                maleHeadModelID1 = buffer.read16U();
            } else if (code == 93) {
                femaleHeadModelID1 = buffer.read16U();
            } else if (code == 95) {
                iconRoll = buffer.read16U();
            } else if (code == 97) {
                linkedID = buffer.read16U();
            } else if (code == 98) {
                certificateID = buffer.read16U();
            } else if ((code >= 100) && (code < 110)) {
                if (stackID == null) {
                    stackID = new int[10];
                    stackCount = new int[10];
                }
                stackID[code - 100] = buffer.read16U();
                stackCount[code - 100] = buffer.read16U();
            } else if (code == 110) {
                scaleX = buffer.read16U();
            } else if (code == 111) {
                scaleZ = buffer.read16U();
            } else if (code == 112) {
                scaleY = buffer.read16U();
            } else if (code == 113) {
                lightAmbient = buffer.read();
            } else if (code == 114) {
                lightAttenuation = buffer.read() * 5;
            } else if (code == 115) {
                team = buffer.read8U();
            }
        }
    }

}
