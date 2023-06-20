// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import com.google.gson.annotations.Expose;
import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Iface {

    public static final class Script {
        public int _comparator;
        @Expose
        public String comparator;
        @Expose
        public Integer operand;
        @Expose
        public int[] code;
    }

    public static final class Child {
        @Expose
        public int id;
        @Expose
        public int x;
        @Expose
        public int y;
    }

    public static final LRUMap<Long, Model> modelCache = new LRUMap<>(30);
    public static LRUMap<Long, Image24> imageCache;
    public static Iface[] instances;

    public static final int TYPE_LAYER = 0;
    public static final int TYPE_UNUSED = 1;
    public static final int TYPE_INVENTORY = 2;
    public static final int TYPE_RECT = 3;
    public static final int TYPE_TEXT = 4;
    public static final int TYPE_IMAGE = 5;
    public static final int TYPE_MODEL = 6;
    public static final int TYPE_INVENTORY_TEXT = 7;
    public static final int OPTION_TYPE_OK = 1;
    public static final int OPTION_TYPE_SPELL = 2;
    public static final int OPTION_TYPE_CLOSE = 3;
    public static final int OPTION_TYPE_TOGGLE = 4;
    public static final int OPTION_TYPE_SELECT = 5;
    public static final int OPTION_TYPE_CONTINUE = 6;
    public static final int MODEL_TYPE_NONE = 0;
    public static final int MODEL_TYPE_NORMAL = 1;
    public static final int MODEL_TYPE_NPC = 2;
    public static final int MODEL_TYPE_PLAYER = 3;
    public static final int MODEL_TYPE_ITEM = 4;
    public static final int MODEL_TYPE_PLAYER_DESIGN = 5;

    public static final int CMP_EQ = 1;
    public static final int CMP_LS = 2;
    public static final int CMP_GT = 3;
    public static final int CMP_NE = 4;

    public static void unpack(FileArchive config, BitmapFont[] fonts, FileArchive media) throws IOException {
        imageCache = new LRUMap<>(500);
        Buffer in = new Buffer(config.read("data"));

        int parent = -1;
        int count = in.readU16();

        if (count > 13901) {
            count = 13901;
        }

        instances = new Iface[count];

        while (in.position < in.data.length) {
            int id = in.readU16();

            if (id == 65535) {
                parent = in.readU16();
                id = in.readU16();
            }

            if (id > 13900) {
                return;
            }

            Iface iface = instances[id] = new Iface();
            iface.id = id;
            if (parent != -1) {
                iface.parent = parent;
            }
            iface._type = in.readU8();
            int option_type = in.readU8();
            if (option_type != 0) {
                iface._option_type = option_type;
            }
            int content_type = in.readU16();
            if (content_type != 0) {
                iface.content_type = content_type;
            }
            iface.width = in.readU16();
            iface.height = in.readU16();
            int transparency = in.readU8();
            if (transparency != 0) {
                iface.transparency = transparency;
            }
            int delegate = in.readU8();
            if (delegate != 0) {
                iface.hover_delegate = ((delegate - 1) << 8) + in.readU8();
            }
            int comparatorCount = in.readU8();
            if (comparatorCount > 0) {
                iface._script_comparator = new int[comparatorCount];
                iface._script_operand = new int[comparatorCount];
                for (int i = 0; i < comparatorCount; i++) {
                    iface._script_comparator[i] = in.readU8();
                    iface._script_operand[i] = in.readU16();
                }
            }

            int scriptCount = in.readU8();
            if (scriptCount > 0) {
                iface._script_code = new int[scriptCount][];
                for (int scriptID = 0; scriptID < scriptCount; scriptID++) {
                    int length = in.readU16();
                    iface._script_code[scriptID] = new int[length];
                    for (int i = 0; i < length; i++) {
                        iface._script_code[scriptID][i] = in.readU16();
                    }
                }
            }

            if (iface._type == TYPE_LAYER) {
                int scroll_height = in.readU16();
                if (scroll_height > iface.height) {
                    iface.scroll_height = scroll_height;
                }
                if (in.readU8() == 1) {
                    iface.hide = true;
                }
                int childCount = in.readU16();
                iface.child_id = new int[childCount];
                iface.child_x = new int[childCount];
                iface.child_y = new int[childCount];
                for (int i = 0; i < childCount; i++) {
                    iface.child_id[i] = in.readU16();
                    iface.child_x[i] = in.read16();
                    iface.child_y[i] = in.read16();
                }
            }

            if (iface._type == TYPE_UNUSED) {
                in.readU16();
                in.readU8();
            }

            if (iface._type == TYPE_INVENTORY) {
                iface.inv_slot_item_id = new int[iface.width * iface.height];
                iface.inv_slot_item_count = new int[iface.width * iface.height];
                if (in.readU8() == 1) {
                    iface.inv_draggable = true;
                }
                if (in.readU8() == 1) {
                    iface.inv_interactable = true;
                }
                if (in.readU8() == 1) {
                    iface.inv_usable = true;
                }
                if (in.readU8() == 1) {
                    iface.inv_move_replaces = true;
                }
                int margin_x = in.readU8();
                int margin_y = in.readU8();
                if (margin_x != 0) {
                    iface.inv_margin_x = margin_x;
                }
                if (margin_y != 0) {
                    iface.inv_margin_y = margin_y;
                }
                iface.inv_slot_offset_x = new int[20];
                iface.inv_slot_offset_y = new int[20];
                iface._inv_slot_image = new Image24[20];

                for (int slot = 0; slot < 20; slot++) {
                    if (in.readU8() == 1) {
                        iface.inv_slot_offset_x[slot] = in.read16();
                        iface.inv_slot_offset_y[slot] = in.read16();
                        String image_name = in.readString();

                        if ((media != null) && (image_name.length() > 0)) {
                            int image_id = image_name.lastIndexOf(",");
                            iface._inv_slot_image[slot] = getImage(Integer.parseInt(image_name.substring(image_id + 1)), media, image_name.substring(0, image_id));
                        }
                    }
                }

                iface.inv_options = new String[5];

                for (int i = 0; i < 5; i++) {
                    iface.inv_options[i] = in.readString();
                    if (iface.inv_options[i].length() == 0) {
                        iface.inv_options[i] = null;
                    }
                }
            }

            if (iface._type == TYPE_RECT) {
                if (in.readU8() == 1) {
                    iface.fill = true;
                }
            }

            if ((iface._type == TYPE_TEXT) || (iface._type == TYPE_UNUSED)) {
                if (in.readU8() == 1) {
                    iface.center = true;
                }
                iface._font_id = in.readU8();
                if (fonts != null) {
                    iface._font = fonts[iface._font_id];
                }
                if (in.readU8() == 1) {
                    iface.shadow = true;
                }
            }

            if (iface._type == TYPE_TEXT) {
                String text = in.readString();
                if (text.length() > 0) {
                    iface.text = text;
                }
                text = in.readString();
                if (text.length() > 0) {
                    iface.active_text = text;
                }
            }

            if ((iface._type == TYPE_UNUSED) || (iface._type == TYPE_RECT) || (iface._type == TYPE_TEXT)) {
                int color = in.read32();
                if (color != 0) {
                    iface.color = color;
                }
            }

            if ((iface._type == TYPE_RECT) || (iface._type == TYPE_TEXT)) {
                int color = in.read32();
                if (color != 0) {
                    iface.active_color = color;
                }
                color = in.read32();
                if (color != 0) {
                    iface.hover_color = color;
                }
                color = in.read32();
                if (color != 0) {
                    iface.active_hover_color = color;

                }
            }

            if (iface._type == TYPE_IMAGE) {
                String s = in.readString();
                if ((media != null) && (s.length() > 0)) {
                    iface.image = s;
                    int comma = s.lastIndexOf(",");
                    iface._image = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
                }
                s = in.readString();
                if ((media != null) && (s.length() > 0)) {
                    iface.active_image = s;
                    int comma = s.lastIndexOf(",");
                    iface._active_image = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
                }
            }

            if (iface._type == TYPE_MODEL) {
                int tmp = in.readU8();
                if (tmp != 0) {
                    iface._model_type = MODEL_TYPE_NORMAL;
                    iface.model = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface._active_model_type = MODEL_TYPE_NORMAL;
                    iface.active_model = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface.animation = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU8();
                if (tmp != 0) {
                    iface.active_animation = ((tmp - 1) << 8) + in.readU8();
                }

                tmp = in.readU16();
                if (tmp != 0) {
                    iface.model_zoom = tmp;
                }
                tmp = in.readU16();
                if (tmp != 0) {
                    iface.model_pitch = tmp;
                }
                tmp = in.readU16();
                if (tmp != 0) {
                    iface.model_yaw = tmp;
                }
            }

            if (iface._type == TYPE_INVENTORY_TEXT) {
                iface.inv_slot_item_id = new int[iface.width * iface.height];
                iface.inv_slot_item_count = new int[iface.width * iface.height];
                if (in.readU8() == 1) {
                    iface.center = true;
                }
                iface._font_id = in.readU8();
                if (fonts != null) {
                    iface._font = fonts[iface._font_id];
                }
                iface.shadow = in.readU8() == 1;
                iface.color = in.read32();
                int margin_x = in.read16();
                int margin_y = in.read16();
                if (margin_x != 0) {
                    iface.inv_margin_x = margin_x;
                }
                if (margin_y != 0) {
                    iface.inv_margin_y = margin_y;
                }
                if (in.readU8() == 1) {
                    iface.inv_interactable = true;
                }
                iface.inv_options = new String[5];
                for (int option = 0; option < 5; option++) {
                    iface.inv_options[option] = in.readString();
                    if (iface.inv_options[option].length() == 0) {
                        iface.inv_options[option] = null;
                    }
                }
            }

            if (iface._type == 8) {
                String action = in.readString();

                if (action.length() > 0) {
                    iface.spell_action = action;
                }
            }

            if ((iface._option_type == OPTION_TYPE_SPELL) || (iface._type == TYPE_INVENTORY)) {
                String s = in.readString();
                if (s.length() > 0) {
                    iface.spell_action = s;
                }
                s = in.readString();
                if (s.length() > 0) {
                    iface.spell_name = s;
                }
                iface._spell_targets = in.readU16();
            }

            if ((iface._option_type == OPTION_TYPE_OK) || (iface._option_type == OPTION_TYPE_TOGGLE) || (iface._option_type == OPTION_TYPE_SELECT) || (iface._option_type == OPTION_TYPE_CONTINUE)) {
                iface.option = in.readString();

                if (iface.option.length() == 0) {
                    if (iface._option_type == OPTION_TYPE_OK) {
                        iface.option = "Ok";
                    } else if (iface._option_type == OPTION_TYPE_TOGGLE) {
                        iface.option = "Select";
                    } else if (iface._option_type == OPTION_TYPE_SELECT) {
                        iface.option = "Select";
                    } else if (iface._option_type == OPTION_TYPE_CONTINUE) {
                        iface.option = "Continue";
                    }
                }
            }

            iface.apply_values();
        }
        imageCache = null;
    }

    public void apply_values() {
        if (_font != null) {
            switch (_font_id) {
                case 0 -> font = "p11";
                case 1 -> font = "p12";
                case 2 -> font = "b12";
                case 3 -> font = "q8";
                default -> font = "invalid";
            }
        }
        switch (_type) {
            case TYPE_LAYER -> type = "layer";
            case TYPE_UNUSED -> type = "unused";
            case TYPE_INVENTORY -> type = "inventory";
            case TYPE_RECT -> type = "rect";
            case TYPE_TEXT -> type = "text";
            case TYPE_IMAGE -> type = "image";
            case TYPE_MODEL -> type = "model";
            case TYPE_INVENTORY_TEXT -> type = "inventory_text";
            default -> type = String.format("invalid:%d", _type);
        }
        if (_option_type != 0) {
            switch (_option_type) {
                case OPTION_TYPE_OK -> option_type = "ok";
                case OPTION_TYPE_SPELL -> option_type = "spell";
                case OPTION_TYPE_CLOSE -> option_type = "close";
                case OPTION_TYPE_TOGGLE -> option_type = "toggle";
                case OPTION_TYPE_SELECT -> option_type = "select";
                case OPTION_TYPE_CONTINUE -> option_type = "continue";
                default -> option_type = String.format("invalid:%d", _option_type);
            }
        }
        if (_spell_targets != 0) {
            String[] names = {"ground_item", "npc", "object", "player", "inventory_item"};
            List<String> targets = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if ((_spell_targets & (1 << i)) != 0) {
                    targets.add(names[i]);
                }
            }
            spell_targets = targets.toArray(new String[0]);
        }
        if (_model_type != null) {
            switch (_model_type) {
                case MODEL_TYPE_NORMAL -> model_type = "normal";
                case MODEL_TYPE_NPC -> model_type = "npc";
                case MODEL_TYPE_PLAYER -> model_type = "player";
                case MODEL_TYPE_ITEM -> model_type = "item";
                case MODEL_TYPE_PLAYER_DESIGN -> model_type = "player_design";
                default -> model_type = String.format("invalid:%d", _model_type);
            }
        }
        if (_active_model_type != null) {
            switch (_active_model_type) {
                case MODEL_TYPE_NORMAL -> active_model_type = "normal";
                case MODEL_TYPE_NPC -> active_model_type = "npc";
                case MODEL_TYPE_PLAYER -> active_model_type = "player";
                case MODEL_TYPE_ITEM -> active_model_type = "item";
                case MODEL_TYPE_PLAYER_DESIGN -> active_model_type = "player_design";
                default -> active_model_type = String.format("invalid:%d", _active_model_type);
            }
        }

        if (_script_code != null && _script_code.length > 0) {
            scripts = new Script[_script_code.length];
            for (int i = 0; i < scripts.length; i++) {
                Script script = new Script();
                if (_script_comparator != null && i < _script_comparator.length) {
                    script._comparator = _script_comparator[i];
                    script.operand = _script_operand[i];
                    switch (script._comparator) {
                        case CMP_EQ -> script.comparator = "==";
                        case CMP_NE -> script.comparator = "!=";
                        case CMP_GT -> script.comparator = ">";
                        case CMP_LS -> script.comparator = "<";
                        default -> script.comparator = String.format("invalid:%d", script._comparator);
                    }
                }
                script.code = _script_code[i];
                scripts[i] = script;
            }
        }
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

        if ((model != null) && (type != MODEL_TYPE_ITEM)) {
            modelCache.put(((long) type << 16) + id, model);
        }
    }

    public BitmapFont _font;
    public int _font_id;
    public int _type;
    @Expose
    public String type;
    @Expose
    public int id;
    @Expose
    public Integer content_type;
    @Expose
    public int width;
    @Expose
    public int height;
    public int offset_x;
    public int offset_y;
    @Expose
    public Integer scroll_height;
    public int scroll_pos;
    public int parent;
    @Expose
    public int[] child_id;
    @Expose
    public int[] child_x;
    @Expose
    public int[] child_y;
    @Expose
    public String option;
    public int _option_type;
    @Expose
    public String option_type;
    @Expose
    public String spell_action;
    @Expose
    public String spell_name;
    public int _spell_targets;
    @Expose
    public String[] spell_targets;
    @Expose
    public String text;
    @Expose
    public String active_text;
    @Expose
    public String font;
    @Expose
    public Integer animation;
    @Expose
    public Integer active_animation;
    @Expose
    public Integer color;
    @Expose
    public Integer active_color;
    @Expose
    public Integer hover_delegate;
    @Expose
    public Integer hover_color;
    @Expose
    public Integer active_hover_color;
    public Image24 _image;
    @Expose
    public String image;
    public Image24 _active_image;
    @Expose
    public String active_image;
    @Expose
    public Boolean center;
    @Expose
    public Boolean fill;
    @Expose
    public Boolean hide;
    @Expose
    public Boolean shadow;
    @Expose
    public Integer transparency;

    @Expose
    public Integer model;
    @Expose
    public Integer active_model;
    public Integer _model_type;
    @Expose
    public String model_type;
    public Integer _active_model_type;
    @Expose
    public String active_model_type;
    @Expose
    public Integer model_pitch;
    @Expose
    public Integer model_yaw;
    @Expose
    public Integer model_zoom;

    public int[] _script_comparator;
    public int[] _script_operand;
    public int[][] _script_code;
    @Expose
    public Script[] scripts;
    @Expose
    public Boolean inv_draggable;
    @Expose
    public Boolean inv_interactable;
    @Expose
    public Boolean inv_move_replaces;
    @Expose
    public Boolean inv_usable;
    @Expose
    public String[] inv_options;
    @Expose
    public Integer inv_margin_x;
    @Expose
    public Integer inv_margin_y;
    public Image24[] _inv_slot_image;
    @Expose
    public String[] inv_slot_image;
    @Expose
    public int[] inv_slot_offset_x;
    @Expose
    public int[] inv_slot_offset_y;

    public int[] inv_slot_item_id;
    public int[] inv_slot_item_count;
    public int animation_cycle;
    public int animation_frame;


    public Iface() {
    }

    public void inventorySwap(int src, int dst) {
        int tmp = inv_slot_item_id[src];
        inv_slot_item_id[src] = inv_slot_item_id[dst];
        inv_slot_item_id[dst] = tmp;

        tmp = inv_slot_item_count[src];
        inv_slot_item_count[src] = inv_slot_item_count[dst];
        inv_slot_item_count[dst] = tmp;
    }

    public Model getModel(int category, int id) {
        Model model = modelCache.get(((long) category << 16) + id);

        if (model != null) {
            return model;
        }

        if (category == MODEL_TYPE_NORMAL) {
            model = Model.tryGet(id);
        } else if (category == MODEL_TYPE_NPC) {
            model = NPC.get(id).get_chat_model();
        } else if (category == MODEL_TYPE_PLAYER) {
            model = Game.local_player.getHeadModel();
        } else if (category == MODEL_TYPE_ITEM) {
            model = Item.get(id).getInterfaceModel(50);
        }

        if (model != null) {
            modelCache.put((long) ((category << 16) + id), model);
        }

        return model;
    }

    public Model getModel(int primaryTransformID, int secondaryTransformID, boolean active) {
        Model model;

        if (active) {
            model = getModel(_active_model_type, active_model);
        } else {
            model = getModel(_model_type, this.model);
        }

        if (model == null) {
            return null;
        }

        if ((primaryTransformID == -1) && (secondaryTransformID == -1) && (model.faceColor == null)) {
            return model;
        }

        model = Model.clone(true, AnimationTransform.isNull(primaryTransformID) & AnimationTransform.isNull(secondaryTransformID), false, model);

        if ((primaryTransformID != -1) || (secondaryTransformID != -1)) {
            model.build_labels();
        }

        if (primaryTransformID != -1) {
            model.transform(primaryTransformID);
        }

        if (secondaryTransformID != -1) {
            model.transform(secondaryTransformID);
        }

        model.build(64, 768, -50, -10, -50, true);
        return model;
    }

}
