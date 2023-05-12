// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class IfType {

    public static final LRUMap<Long, Model> modelCache = new LRUMap<>(30);
    public static LRUMap<Long, Image24> imageCache;
    public static IfType[] instances;

    public static final int TYPE_PARENT = 0;
    public static final int TYPE_UNUSED = 1;
    public static final int TYPE_INVENTORY = 2;
    public static final int TYPE_RECT = 3;
    public static final int TYPE_TEXT = 4;
    public static final int TYPE_IMAGE = 5;
    public static final int TYPE_MODEL = 6;
    public static final int TYPE_INVENTORY_TEXT = 7;
    public static final int OPTION_TYPE_STANDARD = 1;
    public static final int OPTION_TYPE_SPELL = 2;
    public static final int OPTION_TYPE_CLOSE = 3;
    public static final int OPTION_TYPE_TOGGLE = 4;
    public static final int OPTION_TYPE_SELECT = 5;
    public static final int OPTION_TYPE_CONTINUE = 6;
    public static final int MODEL_TYPE_NONE = 0;
    public static final int MODEL_TYPE_NORMAL = 1;
    public static final int MODEL_TYPE_NPC = 2;
    public static final int MODEL_TYPE_PLAYER = 3;
    public static final int MODEL_TYPE_OBJ = 4;
    public static final int MODEL_TYPE_PLAYER_DESIGN = 5;

    public static void unpack(FileArchive config, BitmapFont[] fonts, FileArchive media) throws IOException {
        imageCache = new LRUMap<>(500);
        Buffer in = new Buffer(config.read("data"));

        int parentID = -1;
        int count = in.readU16();

        instances = new IfType[count];

        while (in.position < in.data.length) {
            int id = in.readU16();

            if (id == 65535) {
                parentID = in.readU16();
                id = in.readU16();
            }

            IfType iface = instances[id] = new IfType();
            iface.id = id;
            iface.parentID = parentID;
            iface.type = in.readU8();
            iface.optionType = in.readU8();
            iface.contentType = in.readU16();
            iface.width = in.readU16();
            iface.height = in.readU16();
            iface.transparency = (byte) in.readU8();
            iface.delegateHover = in.readU8();

            if (iface.delegateHover != 0) {
                iface.delegateHover = ((iface.delegateHover - 1) << 8) + in.readU8();
            } else {
                iface.delegateHover = -1;
            }

            int comparatorCount = in.readU8();
            if (comparatorCount > 0) {
                iface.scriptComparator = new int[comparatorCount];
                iface.scriptOperand = new int[comparatorCount];
                for (int i = 0; i < comparatorCount; i++) {
                    iface.scriptComparator[i] = in.readU8();
                    iface.scriptOperand[i] = in.readU16();
                }
            }

            int scriptCount = in.readU8();
            if (scriptCount > 0) {
                iface.scripts = new int[scriptCount][];
                for (int scriptID = 0; scriptID < scriptCount; scriptID++) {
                    int length = in.readU16();
                    iface.scripts[scriptID] = new int[length];
                    for (int i = 0; i < length; i++) {
                        iface.scripts[scriptID][i] = in.readU16();
                    }
                }
            }

            if (iface.type == TYPE_PARENT) {
                iface.scrollableHeight = in.readU16();
                iface.hide = in.readU8() == 1;
                int childCount = in.readU16();
                iface.childID = new int[childCount];
                iface.childX = new int[childCount];
                iface.childY = new int[childCount];
                for (int i = 0; i < childCount; i++) {
                    iface.childID[i] = in.readU16();
                    iface.childX[i] = in.read16();
                    iface.childY[i] = in.read16();
                }
            }

            if (iface.type == TYPE_UNUSED) {
                in.readU16();
                in.readU8();
            }

            if (iface.type == TYPE_INVENTORY) {
                iface.inventorySlotObjID = new int[iface.width * iface.height];
                iface.inventorySlotObjCount = new int[iface.width * iface.height];
                iface.inventoryDraggable = in.readU8() == 1;
                iface.inventoryInteractable = in.readU8() == 1;
                iface.inventoryUsable = in.readU8() == 1;
                iface.inventoryMoveReplaces = in.readU8() == 1;
                iface.inventoryMarginX = in.readU8();
                iface.inventoryMarginY = in.readU8();
                iface.inventorySlotOffsetX = new int[20];
                iface.inventorySlotOffsetY = new int[20];
                iface.inventorySlotImage = new Image24[20];

                for (int slot = 0; slot < 20; slot++) {
                    if (in.readU8() == 1) {
                        iface.inventorySlotOffsetX[slot] = in.read16();
                        iface.inventorySlotOffsetY[slot] = in.read16();
                        String imageName = in.readString();

                        if ((media != null) && (imageName.length() > 0)) {
                            int imageID = imageName.lastIndexOf(",");
                            iface.inventorySlotImage[slot] = getImage(Integer.parseInt(imageName.substring(imageID + 1)), media, imageName.substring(0, imageID));
                        }
                    }
                }

                iface.inventoryOptions = new String[5];

                for (int i = 0; i < 5; i++) {
                    iface.inventoryOptions[i] = in.readString();
                    if (iface.inventoryOptions[i].length() == 0) {
                        iface.inventoryOptions[i] = null;
                    }
                }
            }

            if (iface.type == TYPE_RECT) {
                iface.fill = in.readU8() == 1;
            }

            if ((iface.type == TYPE_TEXT) || (iface.type == TYPE_UNUSED)) {
                iface.center = in.readU8() == 1;
                int fontID = in.readU8();
                if (fonts != null) {
                    iface.font = fonts[fontID];
                }
                iface.shadow = in.readU8() == 1;
            }

            if (iface.type == TYPE_TEXT) {
                iface.text = in.readString();
                iface.activeText = in.readString();
            }

            if ((iface.type == TYPE_UNUSED) || (iface.type == TYPE_RECT) || (iface.type == TYPE_TEXT)) {
                iface.color = in.read32();
            }

            if ((iface.type == TYPE_RECT) || (iface.type == TYPE_TEXT)) {
                iface.activeColor = in.read32();
                iface.hoverColor = in.read32();
                iface.activeHoverColor = in.read32();
            }

            if (iface.type == TYPE_IMAGE) {
                String s = in.readString();
                if ((media != null) && (s.length() > 0)) {
                    int comma = s.lastIndexOf(",");
                    iface.image = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
                }
                s = in.readString();
                if ((media != null) && (s.length() > 0)) {
                    int comma = s.lastIndexOf(",");
                    iface.activeImage = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
                }
            }

            if (iface.type == TYPE_MODEL) {
                int tmp = in.readU8();
                if (tmp != 0) {
                    iface.modelType = MODEL_TYPE_NORMAL;
                    iface.modelID = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface.activeModelType = MODEL_TYPE_NORMAL;
                    iface.activeModelID = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface.seqID = ((tmp - 1) << 8) + in.readU8();
                } else {
                    iface.seqID = -1;
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface.activeSeqID = ((tmp - 1) << 8) + in.readU8();
                } else {
                    iface.activeSeqID = -1;
                }

                iface.modelZoom = in.readU16();
                iface.modelPitch = in.readU16();
                iface.modelYaw = in.readU16();
            }

            if (iface.type == TYPE_INVENTORY_TEXT) {
                iface.inventorySlotObjID = new int[iface.width * iface.height];
                iface.inventorySlotObjCount = new int[iface.width * iface.height];
                iface.center = in.readU8() == 1;
                int fontID = in.readU8();
                if (fonts != null) {
                    iface.font = fonts[fontID];
                }
                iface.shadow = in.readU8() == 1;
                iface.color = in.read32();
                iface.inventoryMarginX = in.read16();
                iface.inventoryMarginY = in.read16();
                iface.inventoryInteractable = in.readU8() == 1;
                iface.inventoryOptions = new String[5];
                for (int option = 0; option < 5; option++) {
                    iface.inventoryOptions[option] = in.readString();
                    if (iface.inventoryOptions[option].length() == 0) {
                        iface.inventoryOptions[option] = null;
                    }
                }
            }

            if (iface.type == 8) {
                iface.spellAction = in.readString();
            }

            if ((iface.optionType == OPTION_TYPE_SPELL) || (iface.type == TYPE_INVENTORY)) {
                iface.spellAction = in.readString();
                iface.spellName = in.readString();
                iface.spellFlags = in.readU16();
            }

            if ((iface.optionType == OPTION_TYPE_STANDARD) || (iface.optionType == OPTION_TYPE_TOGGLE) || (iface.optionType == OPTION_TYPE_SELECT) || (iface.optionType == OPTION_TYPE_CONTINUE)) {
                iface.option = in.readString();

                if (iface.option.length() == 0) {
                    if (iface.optionType == OPTION_TYPE_STANDARD) {
                        iface.option = "Ok";
                    } else if (iface.optionType == OPTION_TYPE_TOGGLE) {
                        iface.option = "Select";
                    } else if (iface.optionType == OPTION_TYPE_SELECT) {
                        iface.option = "Select";
                    } else if (iface.optionType == OPTION_TYPE_CONTINUE) {
                        iface.option = "Continue";
                    }
                }
            }
        }
        imageCache = null;
    }

    public static Image24 getImage(int id, FileArchive media, String name) {
        long uid = (StringUtil.hashCode(name) << 8) + (long) id;
        Image24 image = imageCache.get(uid);

        if (image != null) {
            return image;
        }

        try {
            image = new Image24(media, name, id);
            imageCache.put(uid, image);
        } catch (Exception _ex) {
            return null;
        }
        return image;
    }

    public static void cacheModel(int id, int type, Model model) {
        modelCache.clear();

        if ((model != null) && (type != MODEL_TYPE_OBJ)) {
            modelCache.put(((long) type << 16) + id, model);
        }
    }

    public boolean inventoryInteractable;
    public int activeColor;
    public int activeHoverColor;
    public Image24 activeImage;
    public int activeModelType;
    public int activeModelID;
    public int activeSeqID;
    public String activeText;
    public boolean center;
    public int[] childID;
    public int[] childX;
    public int[] childY;
    public int color;
    public int contentType;
    public int delegateHover;
    public boolean fill;
    public BitmapFont font;
    public int height;
    /**
     * Hides this component. Only works for parent components by default.
     *
     * @see Game#drawParentInterface(IfType, int, int, int)
     */
    public boolean hide;
    public int hoverColor;
    public int id;
    public Image24 image;
    public boolean inventoryDraggable;
    public int inventoryMarginX;
    public int inventoryMarginY;
    public boolean inventoryMoveReplaces;
    public String[] inventoryOptions;
    public int[] inventorySlotObjCount;
    public Image24[] inventorySlotImage;
    public int[] inventorySlotObjID;
    public int[] inventorySlotOffsetX;
    public int[] inventorySlotOffsetY;
    public boolean inventoryUsable;
    public int modelPitch;
    public int modelType;
    public int modelID;
    public int modelYaw;
    public int modelZoom;
    public String option;
    public int optionType;
    public int parentID;
    public int[] scriptComparator;
    public int[] scriptOperand;
    public int[][] scripts;
    public int scrollableHeight;
    public int scrollPosition;
    public int seqCycle;
    public int seqFrame;
    public int seqID;
    public boolean shadow;
    public String spellAction;
    public int spellFlags;
    public String spellName;
    public String text;
    public byte transparency;
    public int type;
    public int width;
    public int x;
    public int y;

    public IfType() {
    }

    public void inventorySwap(int src, int dst) {
        int tmp = inventorySlotObjID[src];
        inventorySlotObjID[src] = inventorySlotObjID[dst];
        inventorySlotObjID[dst] = tmp;

        tmp = inventorySlotObjCount[src];
        inventorySlotObjCount[src] = inventorySlotObjCount[dst];
        inventorySlotObjCount[dst] = tmp;
    }

    public Model getModel(int category, int id) {
        Model model = modelCache.get(((long) category << 16) + id);

        if (model != null) {
            return model;
        }

        if (category == MODEL_TYPE_NORMAL) {
            model = Model.tryGet(id);
        } else if (category == MODEL_TYPE_NPC) {
            model = NPCType.get(id).getHeadModel();
        } else if (category == MODEL_TYPE_PLAYER) {
            model = Game.localPlayer.getHeadModel();
        } else if (category == MODEL_TYPE_OBJ) {
            model = ObjType.get(id).getInterfaceModel(50);
        }

        if (model != null) {
            modelCache.put((long) ((category << 16) + id), model);
        }

        return model;
    }

    public Model getModel(int primaryTransformID, int secondaryTransformID, boolean active) {
        Model model;

        if (active) {
            model = getModel(activeModelType, activeModelID);
        } else {
            model = getModel(modelType, modelID);
        }

        if (model == null) {
            return null;
        }

        if ((primaryTransformID == -1) && (secondaryTransformID == -1) && (model.faceColor == null)) {
            return model;
        }

        model = new Model(true, SeqTransform.isNull(primaryTransformID) & SeqTransform.isNull(secondaryTransformID), false, model);

        if ((primaryTransformID != -1) || (secondaryTransformID != -1)) {
            model.createLabelReferences();
        }

        if (primaryTransformID != -1) {
            model.applyTransform(primaryTransformID);
        }

        if (secondaryTransformID != -1) {
            model.applyTransform(secondaryTransformID);
        }

        model.build(64, 768, -50, -10, -50, true);
        return model;
    }

}
