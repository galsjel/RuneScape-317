// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NPC {


    public static class Combat {
        @Expose
        public Integer level;
        @Expose
        public String headicon;
    }

    public static class Animations {
        @Expose
        public Integer idle;
        @Expose
        public Integer move;
        @Expose
        public Integer turn_around;
        @Expose
        public Integer turn_left;
        @Expose
        public Integer turn_right;
    }

    private static int cache_pos;
    private static Buffer dat;
    public static int[] offsets;
    private static NPC[] cache;
    public static Game game;
    public static int count;
    public static LRUMap<Long, Model> built_model_cache = new LRUMap<>(30);

    public static NPC get(int id) {
        for (int j = 0; j < 20; j++) {
            if (cache[j].id == (long) id) {
                return cache[j];
            }
        }
        cache_pos = (cache_pos + 1) % 20;
        NPC type = cache[cache_pos] = new NPC();
        dat.position = offsets[id];
        type.id = id;
        type.read(dat);
        return type;
    }

    public static NPC getUncached(int id) {
        NPC npc = new NPC();
        dat.position = offsets[id];
        npc.id = id;
        npc.read(dat);
        return npc;
    }

    public static NPC get_uncached(int id) {
        NPC type = new NPC();
        dat.position = offsets[id];
        type.id = id;
        type.read(dat);
        return type;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("npc.dat"));
        Buffer buffer = new Buffer(archive.read("npc.idx"));
        count = buffer.readU16();
        offsets = new int[count];

        int offset = 2;
        for (int i = 0; i < count; i++) {
            offsets[i] = offset;
            offset += buffer.readU16();
        }

        cache = new NPC[20];

        for (int k = 0; k < 20; k++) {
            cache[k] = new NPC();
        }
    }

    public static void unload() {
        built_model_cache = null;
        offsets = null;
        cache = null;
        dat = null;
    }

    public String[] _options;
    public int _ambient;
    public int _contrast;
    public int _headicon = -1;
    public int _scalexz = 128;
    public int _scaley = 128;
    public Integer[] _models;
    public Integer[] _chat_models;
    public int[] _color_src;
    public int[] _color_dst;
    public int _animation_idle = -1;
    public int _animation_move = -1;
    public int _animation_turn_around = -1;
    public int _animation_turn_left = -1;
    public int _animation_turn_right = -1;
    public int _combat_level = -1;
    public int _varbit = -1;
    public int _varp = -1;
    public int _turn_speed = 32;

    public void prepare_export() {
        if (_options != null) {
            options = Obj.trim(Arrays.stream(_options).map(a -> a == null ? "hidden" : a).collect(Collectors.toList()), s -> s.equals("hidden"));
        }
        if (_ambient != 0) {
            light = new Model.Light();
            light.ambient = _ambient;
        }
        if (_contrast != 0) {
            if (light == null) {
                light = new Model.Light();
            }
            light.contrast = _contrast;
        }
        if (_models != null) {
            model = Model.ref(_models);
            if (_scalexz != 128) {
                model.scaleX = _scalexz;
                model.scaleZ = _scalexz;
            }
            if (_scaley != 128) {
                model.scaleY = _scaley;
            }
        }
        if (_chat_models != null) {
            chat_model = Model.ref(_chat_models);
        }
        if (_combat_level > 0) {
            combat = new Combat();
            combat.level = _combat_level;
        }
        if (_headicon >= 0) {
            if (combat == null) {
                combat = new Combat();
            }
            combat.headicon = switch (_headicon) {
                case 0 -> "skull";
                case 1 -> "multizone";
                case 2 -> "hint";
                case 3 -> "protect_melee";
                case 4 -> "protect_ranged";
                case 5 -> "protect_magic";
                case 6 -> "duel";
                case 7 -> "hint_outline";
                case 8 -> "protect_kalphite_queen";
                default -> throw new IllegalStateException("Unexpected value: " + _headicon);
            };
        }
        if (_varbit != -1) {
            varbit = _varbit;
        }
        if (_varp != -1) {
            varp = _varp;
        }
        if (!_interactable) {
            interactable = false;
        }
        if (!_mapdot) {
            mapdot = false;
        }
        if (_turn_speed != 32) {
            turn_speed = _turn_speed;
        }

        if (_animation_idle != -1) {
            animations = new Animations();
            animations.idle = _animation_idle;
        }
        if (_animation_move != -1) {
            if (animations == null) {
                animations = new Animations();
            }
            animations.move = _animation_move;
        }
        if (_animation_turn_around != -1) {
            if (animations == null) {
                animations = new Animations();
            }

            animations.turn_around = _animation_turn_around;
        }
        if (_animation_turn_left != -1) {
            if (animations == null) {
                animations = new Animations();
            }
            animations.turn_left = _animation_turn_left;
        }
        if (_animation_turn_right != -1) {
            if (animations == null) {
                animations = new Animations();
            }
            animations.turn_right = _animation_turn_right;
        }
        recolors = Model.Recolor.make(_color_src, _color_dst);
    }

    @Expose
    public long id = -1L;
    @Expose
    public String name;
    @Expose
    public String examine;
    @Expose
    public List<String> options;
    @Expose
    public byte size = 1;
    @Expose
    public Boolean important;
    public boolean _interactable = true;
    @Expose
    public Boolean interactable;
    public boolean _mapdot = true;
    @Expose
    public Boolean mapdot;
    @Expose
    public Combat combat;
    @Expose
    public Model.Ref model;
    @Expose
    public Model.Ref chat_model;
    @Expose
    public Model.Light light;
    @Expose
    public Model.Recolor[] recolors;
    @Expose
    public Animations animations;
    @Expose
    public Integer turn_speed;
    @Expose
    public Integer varbit;
    @Expose
    public Integer varp;
    @Expose
    public int[] overrides;


    public NPC() {
    }

    public Model get_chat_model() {
        if (overrides != null) {
            NPC npc = evaluate();
            if (npc == null) {
                 return null;
            } else {
                return npc.get_chat_model();
            }
        }

        if (_chat_models == null) {
            return null;
        }

        boolean loaded = false;

        for (int value : _chat_models) {
            if (!Model.loaded(value)) {
                loaded = true;
            }
        }

        if (loaded) {
            return null;
        }

        Model[] models = new Model[_chat_models.length];

        for (int i = 0; i < _chat_models.length; i++) {
            models[i] = Model.tryGet(_chat_models[i]);
        }

        Model model;

        if (models.length == 1) {
            model = models[0];
        } else {
            model = Model.join_prebuilt(models.length, models);
        }

        if (_color_src != null) {
            for (int i = 0; i < _color_src.length; i++) {
                model.recolor(_color_src[i], _color_dst[i]);
            }
        }
        return model;
    }

    public NPC evaluate() {
        int value = -1;

        if (_varbit != -1) {
            Varbit vb = Varbit.instances[this._varbit];
            int varp = vb.varp;
            int lsb = vb.lsb;
            int msb = vb.msb;
            int mask = Game.BITMASK[msb - lsb];
            value = (game.varps[varp] >> lsb) & mask;
        } else if (_varp != -1) {
            value = game.varps[_varp];
        }

        if ((value < 0) || (value >= overrides.length) || (overrides[value] == -1)) {
            return null;
        } else {
            return get(overrides[value]);
        }
    }

    public Model built_model(int primary_transform, int secondary_transform, int[] secondary_transform_mask) {
        if (overrides != null) {
            NPC override = evaluate();
            if (override == null) {
                return null;
            } else {
                return override.built_model(primary_transform, secondary_transform, secondary_transform_mask);
            }
        }

        Model model = built_model_cache.get(id);

        if (model == null) {
            boolean invalid = false;

            for (int value : _models) {
                if (!Model.loaded(value)) {
                    invalid = true;
                }
            }

            if (invalid) {
                return null;
            }

            Model[] models = new Model[this._models.length];

            for (int i = 0; i < this._models.length; i++) {
                models[i] = Model.tryGet(this._models[i]);
            }

            if (models.length == 1) {
                model = models[0];
            } else {
                model = Model.join_prebuilt(models.length, models);
            }

            if (_color_src != null) {
                for (int i = 0; i < _color_src.length; i++) {
                    model.recolor(_color_src[i], _color_dst[i]);
                }
            }

            model.build_labels();
            model.build(64 + _ambient, 850 + _contrast, -30, -50, -30, true);
            built_model_cache.put(id, model);
        }

        Model tmp = Model.EMPTY;
        tmp.set(model, AnimationTransform.isNull(primary_transform) & AnimationTransform.isNull(secondary_transform));

        if ((primary_transform != -1) && (secondary_transform != -1)) {
            tmp.transform2(primary_transform, secondary_transform, secondary_transform_mask);
        } else if (primary_transform != -1) {
            tmp.transform(primary_transform);
        }

        if ((_scalexz != 128) || (_scaley != 128)) {
            tmp.scale(_scalexz, _scaley, _scalexz);
        }

        tmp.calc_bounds_cylinder();
        tmp.label_faces = null;
        tmp.label_vertices = null;

        if (size == 1) {
            tmp.pickable = true;
        }

        return tmp;
    }

    public void read(Buffer in) {
        while (true) {
            int code = in.readU8();

            if (code == 0) {
                return;
            } else if (code == 1) {
                int modelCount = in.readU8();
                _models = new Integer[modelCount];
                for (int i = 0; i < modelCount; i++) {
                    _models[i] = in.readU16();
                }
            } else if (code == 2) {
                name = in.readString();
            } else if (code == 3) {
                examine = in.readString();
            } else if (code == 12) {
                size = in.read8();
            } else if (code == 13) {
                _animation_idle = in.readU16();
            } else if (code == 14) {
                _animation_move = in.readU16();
            } else if (code == 17) {
                _animation_move = in.readU16();
                _animation_turn_around = in.readU16();
                _animation_turn_left = in.readU16();
                _animation_turn_right = in.readU16();
            } else if ((code >= 30) && (code < 40)) {
                if (_options == null) {
                    _options = new String[5];
                }

                _options[code - 30] = in.readString();

                if (_options[code - 30].equalsIgnoreCase("hidden")) {
                    _options[code - 30] = null;
                }
            } else if (code == 40) {
                int count = in.readU8();
                _color_src = new int[count];
                _color_dst = new int[count];

                for (int i = 0; i < count; i++) {
                    _color_src[i] = in.readU16();
                    _color_dst[i] = in.readU16();
                }
            } else if (code == 60) {
                int count = in.readU8();
                _chat_models = new Integer[count];
                for (int l1 = 0; l1 < count; l1++) {
                    _chat_models[l1] = in.readU16();
                }
            } else if ((code == 90) || (code == 91) || (code == 92)) {
                in.readU16();
            } else if (code == 93) {
                _mapdot = false;
            } else if (code == 95) {
                _combat_level = in.readU16();
            } else if (code == 97) {
                _scalexz = in.readU16();
            } else if (code == 98) {
                _scaley = in.readU16();
            } else if (code == 99) {
                important = true;
            } else if (code == 100) {
                _ambient = in.read8();
            } else if (code == 101) {
                _contrast = in.read8() * 5;
            } else if (code == 102) {
                _headicon = in.readU16();
            } else if (code == 103) {
                _turn_speed = in.readU16();
            } else if (code == 106) {
                _varbit = in.readU16();

                if (_varbit == 65535) {
                    _varbit = -1;
                }

                _varp = in.readU16();

                if (_varp == 65535) {
                    _varp = -1;
                }

                int overrideCount = in.readU8();
                overrides = new int[overrideCount + 1];

                for (int i = 0; i <= overrideCount; i++) {
                    overrides[i] = in.readU16();

                    if (overrides[i] == 65535) {
                        overrides[i] = -1;
                    }
                }
            } else if (code == 107) {
                _interactable = false;
            }
        }
    }

}
