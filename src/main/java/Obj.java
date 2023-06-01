// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Obj {

    public static final int TYPE_WALL_STRAIGHT = 0;
    public static final int TYPE_WALL_CORNER_DIAGONAL = 1;
    public static final int TYPE_WALL_L = 2;
    public static final int TYPE_WALL_SQUARE_CORNER = 3;
    public static final int TYPE_WALLDECOR_STRAIGHT = 4;
    public static final int TYPE_WALLDECOR_STRAIGHT_OFFSET = 5;
    public static final int TYPE_WALLDECOR_DIAGONAL_NOOFFSET = 6;
    public static final int TYPE_WALLDECOR_DIAGONAL_OFFSET = 7;
    public static final int TYPE_WALLDECOR_DIAGONAL_BOTH = 8;
    public static final int TYPE_WALL_DIAGONAL = 9;
    public static final int TYPE_CENTREPIECE = 10;
    public static final int TYPE_CENTREPIECE_DIAGONAL = 11;
    public static final int TYPE_ROOF_STRAIGHT = 12;
    public static final int TYPE_ROOF_DIAGONAL = 13;
    public static final int TYPE_ROOF_DIAGONAL_WITH_ROOFEDGE = 14;
    public static final int TYPE_ROOF_L_CONCAVE = 15;
    public static final int TYPE_ROOF_L_CONVEX = 16;
    public static final int TYPE_ROOF_FLAT = 17;
    public static final int TYPE_ROOFEDGE_STRAIGHT = 18;
    public static final int TYPE_ROOFEDGE_DIAGONALCORNER = 19;
    public static final int TYPE_ROOFEDGE_L = 20;
    public static final int TYPE_ROOFEDGE_SQUARECORNER = 21;
    public static final int TYPE_GROUND_DECOR = 22;

    public static final String[] TYPE_NAMES = {
            "wall_straight",
            "wall_corner_diagonal",
            "wall_l",
            "wall_square_corner",
            "wall_decoration_straight",
            "wall_decoration_straight_offset",
            "wall_decoration_diagonal_no_offset",
            "wall_decoration_diagonal_offset",
            "wall_decoration_diagonal_both",
            "wall_diagonal",
            "centrepiece",
            "centrepiece_diagonal",
            "roof_straight",
            "roof_diagonal",
            "roof_diagonal_with_roofedge",
            "roof_l_concave",
            "roof_l_convex",
            "roof_flat",
            "roof_edge_straight",
            "roof_edge_diagonal_corner",
            "roof_edge_l",
            "roof_edge_square_corner",
            "ground_decoration",
    };

    public static final Model[] TMP_MODELS = new Model[4];
    public static Buffer dat;
    public static int[] offsets;
    public static int count;
    public static Game game;
    public static int cachePos;
    /**
     * This is where dynamically generated models go.
     */
    public static LRUMap<Long, Model> modelCacheDynamic = new LRUMap(30);
    public static Obj[] cache;
    /**
     * This is where basic models go.
     */
    public static LRUMap<Long, Model> modelCacheStatic = new LRUMap(500);

    public static Obj get(int locID) {
        if (locID >= count) {
            locID = 0;
        }
        for (int i = 0; i < 20; i++) {
            if (cache[i].id == locID) {
                return cache[i];
            }
        }
        cachePos = (cachePos + 1) % 20;
        Obj type = cache[cachePos];
        dat.position = offsets[locID];
        type.id = locID;
        type.reset();
        type.read(dat);
        return type;
    }

    public static Obj get_uncached(int id) {
        Obj type = new Obj();
        dat.position = offsets[id];
        type.id = id;
        type.reset();
        type.read(dat);
        return type;
    }

    public static void unload() {
        modelCacheStatic = null;
        modelCacheDynamic = null;
        offsets = null;
        cache = null;
        dat = null;
    }

    public static void unpack(FileArchive archive) throws IOException {
        dat = new Buffer(archive.read("loc.dat"));
        Buffer buffer = new Buffer(archive.read("loc.idx"));
        count = buffer.readU16();
        offsets = new int[count];
        int offset = 2;
        for (int j = 0; j < count; j++) {
            offsets[j] = offset;
            offset += buffer.readU16();
        }
        cache = new Obj[20];
        for (int i = 0; i < 20; i++) {
            cache[i] = new Obj();
        }
    }

    @Expose
    public int id = -1;
    @Expose
    public String name;
    public String[] _options;
    @Expose
    public List<String> options;
    @Expose
    public String examine;
    @Expose
    public int width;
    @Expose
    public int length;
    @Expose
    public Integer animation;
    public Integer unreachable_flags;
    @Expose
    public String[] unreachable_sides;
    @Expose
    public Boolean block_projectile;
    @Expose
    public Boolean block_entity;
    @Expose
    public Boolean hill_skew;
    @Expose
    public Boolean cast_shadow;
    @Expose
    public Boolean dynamic;
    @Expose
    public Boolean important;
    @Expose
    public Boolean interactable;
    @Expose
    public Boolean mirror_z;
    @Expose
    public Boolean occlude;
    @Expose
    public Integer wall_offset;
    @Expose
    public Integer mapfunction;
    @Expose
    public Integer mapscene;
    @Expose
    public Boolean support_items;
    @Expose
    public Integer varbit;
    @Expose
    public Integer varp;
    @Expose
    public int[] overrides;
    public int scaleX;
    public int scaleY;
    public int scaleZ;
    public int translateX;
    public int translateY;
    public int translateZ;
    public int[] color_dst;
    public Integer[] model_ids;
    public int[] model_types;
    public int[] color_src;
    public boolean _decorative;
    public byte _ambient;
    public byte _contrast;
    @Expose
    public Model.Light light;
    @Expose
    @SerializedName("model")
    public Map<String, Model.Ref> models;
    @Expose
    public Model.Recolor[] recolors;

    public Obj() {
    }

    public static <T> List<T> trim(List<T> list, Predicate<T> isEmpty) {
        ListIterator<T> it = list.listIterator();
        while (it.hasNext() && isEmpty.test(it.next())) {
            it.remove();
        }
        it = list.listIterator(list.size());
        while (it.hasPrevious() && isEmpty.test(it.previous())) {
            it.remove();
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    public void prepare_export() {
        if (_options != null) {
            options = trim(Arrays.stream(_options).map(a->a == null ? "hidden" : a).collect(Collectors.toList()), s -> s.equals("hidden"));
        }
        if (_ambient != 0 || _contrast != 0) {
            light = new Model.Light();
            if (_ambient != 0) {
                light.ambient = (int) _ambient;
            }
            if (_contrast != 0) {
                light.contrast = (int) _contrast;
            }
        }

        if (model_ids != null && model_ids.length > 0) {
            models = new HashMap<>();

            if (model_types != null) {
                for (int i = 0; i < model_types.length; i++) {
                    int type = model_types[i];
                    Model.Ref ref = Model.ref(model_ids[i]);
                    if (translateX != 0) ref.translateX = translateX;
                    if (translateY != 0) ref.translateY = translateY;
                    if (translateZ != 0) ref.translateZ = translateZ;
                    if (scaleX != 128) ref.scaleX = scaleX;
                    if (scaleY != 128) ref.scaleY = scaleY;
                    if (scaleZ != 128) ref.scaleZ = scaleZ;
                    models.put(TYPE_NAMES[type], ref);
                }
            } else {
                Model.Ref ref = Model.ref(model_ids);
                if (translateX != 0) ref.translateX = translateX;
                if (translateY != 0) ref.translateY = translateY;
                if (translateZ != 0) ref.translateZ = translateZ;
                if (scaleX != 128) ref.scaleX = scaleX;
                if (scaleY != 128) ref.scaleY = scaleY;
                if (scaleZ != 128) ref.scaleZ = scaleZ;
                models.put(TYPE_NAMES[10], ref);
            }
        }

        if (unreachable_flags != 0) {
            int count = Integer.bitCount(unreachable_flags);
            unreachable_sides = new String[count];
            int added = 0;
            final String[] sides = {"back", "left", "front", "right"};
            for (int i = 0; i < sides.length; i++) {
                if ((unreachable_flags&(1<<i))!=0) {
                    unreachable_sides[added++] = sides[i];
                }
            }
        }

        recolors = Model.Recolor.make(color_src, color_dst);

        if (animation == -1) {
            animation = null;
        }
        if (wall_offset == 16) {
            wall_offset = null;
        }
        if (mapfunction == -1) {
            mapfunction = null;
        }
        if (mapscene == -1) {
            mapscene = null;
        }
        if (!mirror_z) {
            mirror_z = null;
        }
        if (unreachable_flags == 0) {
            unreachable_flags = null;
        }
        if (!important) {
            important = null;
        }
        if (varbit == -1) {
            varbit = null;
        }
        if (varp == -1) {
            varp = null;
        }
    }

    public void reset() {
        model_ids = null;
        model_types = null;
        name = null;
        examine = null;
        color_src = null;
        color_dst = null;
        width = 1;
        length = 1;
        block_entity = true;
        block_projectile = true;
        interactable = false;
        hill_skew = false;
        dynamic = false;
        occlude = false;
        animation = -1;
        wall_offset = 16;
        _ambient = 0;
        _contrast = 0;
        _options = null;
        mapfunction = -1;
        mapscene = -1;
        mirror_z = false;
        cast_shadow = true;
        scaleX = 128;
        scaleY = 128;
        scaleZ = 128;
        unreachable_flags = 0;
        translateX = 0;
        translateY = 0;
        translateZ = 0;
        important = false;
        _decorative = false;
        varbit = -1;
        varp = -1;
        overrides = null;
    }

    public void prefetch(OnDemand onDemand) {
        if (model_ids == null) {
            return;
        }
        for (int modelID : model_ids) {
            onDemand.prefetch(modelID & 0xffff, 0);
        }
    }

    public boolean validate(int kind) {
        if (model_types == null) {
            if (model_ids == null) {
                return true;
            }
            if (kind != 10) {
                return true;
            }
            boolean valid = true;
            for (int modelID : model_ids) {
                valid &= Model.loaded(modelID & 0xffff);
            }
            return valid;
        }
        for (int i = 0; i < model_types.length; i++) {
            if (model_types[i] == kind) {
                return Model.loaded(model_ids[i] & 0xffff);
            }
        }
        return true;
    }

    public Model getModel(int kind, int rotation, int y_sw, int y_se, int y_ne, int y_nw, int transform_id) {
        Model model = getModel(kind, transform_id, rotation);

        if (model == null) {
            return null;
        }

        if (hill_skew || dynamic) {
            model = Model.clone_object(hill_skew, dynamic, model);
        }

        if (hill_skew) {
            int groundY = (y_sw + y_se + y_ne + y_nw) / 4;
            for (int i = 0; i < model.vertexCount; i++) {
                int x = model.vertexX[i];
                int z = model.vertexZ[i];
                int heightS = y_sw + (((y_se - y_sw) * (x + 64)) / 128);
                int heightN = y_nw + (((y_ne - y_nw) * (x + 64)) / 128);
                int y = heightS + (((heightN - heightS) * (z + 64)) / 128);
                model.vertexY[i] += y - groundY;
            }
            model.calculateBoundsY();
        }
        return model;
    }

    public boolean validate() {
        if (model_ids == null) {
            return true;
        }
        boolean ok = true;
        for (int modelID : model_ids) {
            ok &= Model.loaded(modelID & 0xffff);
        }
        return ok;
    }

    public Obj getOverrideType() {
        int value = -1;

        if (varbit != -1) {
            Varbit varbit = Varbit.instances[this.varbit];
            int varp = varbit.varp;
            int low = varbit.lsb;
            int high = varbit.msb;
            int mask = Game.BITMASK[high - low];
            value = (game.varps[varp] >> low) & mask;
        } else if (varp != -1) {
            value = game.varps[varp];
        }

        if ((value < 0) || (value >= overrides.length) || (overrides[value] == -1)) {
            return null;
        } else {
            return get(overrides[value]);
        }
    }

    public Model getModel(int kind, int transformID, int rotation) {
        Model model = null;
        long bitset;

        if (model_types == null) {
            if (kind != 10) {
                return null;
            }

            bitset = ((long) id << 6) + rotation + ((long) (transformID + 1) << 32);
            Model cached = modelCacheDynamic.get(bitset);

            if (cached != null) {
                return cached;
            }

            if (model_ids == null) {
                return null;
            }

            boolean mirror = Boolean.TRUE.equals(this.mirror_z) ^ (rotation > 3);
            int modelCount = model_ids.length;

            for (int i = 0; i < modelCount; i++) {
                int modelID = model_ids[i];

                if (mirror) {
                    modelID += 0x10000;
                }

                model = modelCacheStatic.get(modelID);
                if (model == null) {
                    model = Model.tryGet(modelID & 0xffff);
                    if (model == null) {
                        return null;
                    }
                    if (mirror) {
                        model.mirrorZ();
                    }
                    modelCacheStatic.put((long) modelID, model);
                }

                if (modelCount > 1) {
                    TMP_MODELS[i] = model;
                }
            }

            if (modelCount > 1) {
                model = Model.join_prebuilt(modelCount, TMP_MODELS);
            }
        } else {
            int kindIndex = -1;

            for (int i = 0; i < model_types.length; i++) {
                if (model_types[i] != kind) {
                    continue;
                }
                kindIndex = i;
                break;
            }

            if (kindIndex == -1) {
                return null;
            }

            bitset = ((long) this.id << 6) + ((long) kindIndex << 3) + rotation + ((long) (transformID + 1) << 32);

            Model cached = modelCacheDynamic.get(bitset);

            if (cached != null) {
                return cached;
            }

            int modelID = model_ids[kindIndex];
            boolean mirror = Boolean.TRUE.equals(this.mirror_z) ^ (rotation > 3);

            if (mirror) {
                modelID += 0x10000;
            }

            model = modelCacheStatic.get(modelID);

            if (model == null) {
                model = Model.tryGet(modelID & 0xffff);

                if (model == null) {
                    return null;
                }

                if (mirror) {
                    model.mirrorZ();
                }

                modelCacheStatic.put((long) modelID, model);
            }
        }

        boolean scaled = (scaleX != 128) || (scaleY != 128) || (scaleZ != 128);
        boolean translated = (translateX != 0) || (translateY != 0) || (translateZ != 0);

        Model modified = Model.clone(color_src == null, AnimationTransform.isNull(transformID), (rotation == 0) && (transformID == -1) && !scaled && !translated, model);

        if (transformID != -1) {
            modified.build_labels();
            modified.transform(transformID);
            modified.label_faces = null;
            modified.label_vertices = null;
        }

        while (rotation-- > 0) {
            modified.rotateY90();
        }

        if (color_src != null) {
            for (int k2 = 0; k2 < color_src.length; k2++) {
                modified.recolor(color_src[k2], color_dst[k2]);
            }
        }

        if (scaled) {
            modified.scale(scaleX, scaleY, scaleZ);
        }

        if (translated) {
            modified.translate(translateX, translateY, translateZ);
        }

        modified.build(64 + _ambient, 768 + (_contrast * 5), -50, -10, -50, !dynamic);

        if (support_items) {
            modified.objRaise = modified.minY;
        }

        modelCacheDynamic.put(bitset, modified);
        return modified;
    }

    public void read(Buffer buffer) {
        int interactable = -1;

        while (true) {
            int code = buffer.readU8();

            if (code == 0) {
                break;
            } else if (code == 1) {
                int k = buffer.readU8();
                if (k > 0) {
                    if ((model_ids == null)) {
                        model_types = new int[k];
                        model_ids = new Integer[k];
                        for (int k1 = 0; k1 < k; k1++) {
                            model_ids[k1] = buffer.readU16();
                            model_types[k1] = buffer.readU8();
                        }
                    } else {
                        buffer.position += k * 3;
                    }
                }
            } else if (code == 2) {
                name = buffer.readString();
            } else if (code == 3) {
                examine = buffer.readString();
            } else if (code == 5) {
                int modelCount = buffer.readU8();
                if (modelCount > 0) {
                    if ((model_ids == null)) {
                        model_types = null;
                        model_ids = new Integer[modelCount];
                        for (int l1 = 0; l1 < modelCount; l1++) {
                            model_ids[l1] = buffer.readU16();
                        }
                    } else {
                        buffer.position += modelCount * 2;
                    }
                }
            } else if (code == 14) {
                width = buffer.readU8();
            } else if (code == 15) {
                length = buffer.readU8();
            } else if (code == 17) {
                block_entity = false;
            } else if (code == 18) {
                block_projectile = false;
            } else if (code == 19) {
                interactable = buffer.readU8();

                if (interactable == 1) {
                    this.interactable = true;
                }
            } else if (code == 21) {
                hill_skew = true;
            } else if (code == 22) {
                dynamic = true;
            } else if (code == 23) {
                occlude = true;
            } else if (code == 24) {
                animation = buffer.readU16();
                if (animation == 65535) {
                    animation = -1;
                }
            } else if (code == 28) {
                wall_offset = buffer.readU8();
            } else if (code == 29) {
                _ambient = buffer.read8();
            } else if (code == 39) {
                _contrast = buffer.read8();
            } else if ((code >= 30) && (code < 39)) {
                if (_options == null) {
                    _options = new String[5];
                }
                _options[code - 30] = buffer.readString();
                if (_options[code - 30].equalsIgnoreCase("hidden")) {
                    _options[code - 30] = null;
                }
            } else if (code == 40) {
                int recolorCount = buffer.readU8();
                color_src = new int[recolorCount];
                color_dst = new int[recolorCount];
                for (int i = 0; i < recolorCount; i++) {
                    color_src[i] = buffer.readU16();
                    color_dst[i] = buffer.readU16();
                }
            } else if (code == 60) {
                mapfunction = buffer.readU16();
            } else if (code == 62) {
                mirror_z = true;
            } else if (code == 64) {
                cast_shadow = false;
            } else if (code == 65) {
                scaleX = buffer.readU16();
            } else if (code == 66) {
                scaleY = buffer.readU16();
            } else if (code == 67) {
                scaleZ = buffer.readU16();
            } else if (code == 68) {
                mapscene = buffer.readU16();
            } else if (code == 69) {
                unreachable_flags = buffer.readU8();
            } else if (code == 70) {
                translateX = buffer.read16();
            } else if (code == 71) {
                translateY = buffer.read16();
            } else if (code == 72) {
                translateZ = buffer.read16();
            } else if (code == 73) {
                important = true;
            } else if (code == 74) {
                _decorative = true;
            } else if (code == 75) {
                support_items = buffer.readU8() == 1;
            } else if (code == 77) {
                varbit = buffer.readU16();

                if (varbit == 65535) {
                    varbit = -1;
                }

                varp = buffer.readU16();

                if (varp == 65535) {
                    varp = -1;
                }

                int overrideCount = buffer.readU8();
                overrides = new int[overrideCount + 1];

                for (int i = 0; i <= overrideCount; i++) {
                    overrides[i] = buffer.readU16();

                    if (overrides[i] == 65535) {
                        overrides[i] = -1;
                    }
                }
            }
        }

        // no code 19
        if (interactable == -1) {
            this.interactable = (model_ids != null) && ((model_types == null) || (model_types[0] == 10));

            if (_options != null) {
                this.interactable = true;
            }
        }

        if (_decorative) {
            block_entity = false;
            block_projectile = false;
        }

        if (support_items == null) {
            support_items = block_entity;
        }
    }

}
