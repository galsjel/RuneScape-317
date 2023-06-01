// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.google.gson.Gson;
import org.apache.commons.collections4.map.LRUMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class ScenePlayer extends SceneCharacter {

    public static LRUMap<Long, Model> modelCache = new LRUMap<>(260);
    public final int[] colors = new int[5];
    public final int[] appearances = new int[12];
    public long modelUID = -1L;
    public NPC transmogrify;
    public int team;
    public int gender;
    public String name;
    public int combatLevel;
    public int headicons;
    public int locStartCycle;
    public int locStopCycle;
    public int y;
    public boolean visible = false;
    public int locOffsetX;
    public int locOffsetY;
    public int locOffsetZ;
    public Model locModel;
    public long appearanceHashcode;
    public int minSceneTileX;
    public int minSceneTileZ;
    public int maxSceneTileX;
    public int maxSceneTileZ;
    public int skillLevel;

    public ScenePlayer() {
    }

    public static int max_animated_frame = -1;
    @Override
    public Model getModel() {
        if (!visible) {
            return null;
        }

        Model model = getSequencedModel();

        if (model == null) {
            return null;
        }

        super.height = model.minY;
        model.pickable = true;

        if ((super.spotanimID != -1) && (super.spotanimFrame != -1)) {
            SpotAnim spot = SpotAnim.instances[super.spotanimID];
            Model spotModel1 = spot.getModel();

            if (spotModel1 != null) {
                Model spotModel2 = Model.clone(true, AnimationTransform.isNull(super.spotanimFrame), false, spotModel1);
                spotModel2.translate(0, -super.spotanimOffset, 0);
                spotModel2.build_labels();
                spotModel2.transform(spot.seq.primary_transforms[super.spotanimFrame]);
                spotModel2.label_faces = null;
                spotModel2.label_vertices = null;
                if ((spot.scaleXZ != 128) || (spot.scaleY != 128)) {
                    spotModel2.scale(spot.scaleXZ, spot.scaleY, spot.scaleXZ);
                }
                spotModel2.build(64 + spot.lightAmbient, 850 + spot.lightContrast, -30, -50, -30, true);
                model = Model.join_lit(2, -819, new Model[]{model, spotModel2});

                if (this == Game.localPlayer && spotanimFrame > max_animated_frame) {
                    System.out.println("spotanimFrame = " + spotanimFrame + ", offset: " + -super.spotanimOffset);
                    System.out.println("primarySeqFrame = " + primarySeqFrame);

                    BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
                    int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


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

                    Draw2D.bind(pixels, 512, 512);
                    Draw3D.init3D(512, 512);
                    Draw3D.jagged = false;

                    model.drawSimple(0,256,0,196,20,250,200);

                    // restore state
                    Draw2D.bind(_pix, _w, _h);
                    Draw2D.setBounds(_l, _t, _r, _b);
                    Draw3D.centerX = _cx;
                    Draw3D.centerY = _cy;
                    Draw3D.lineOffset = _loff;
                    Draw3D.jagged = true;

                    try {
                        for (int i = 0; i  <512*512; i++) {
                            if (pixels[i]>0) {
                                pixels[i] |= 255<<24;
                            }
                        }
                        ImageIO.write(image, "png", new File("out/animated" + spotanimFrame + ".png"));

                        Gson gson = new Gson();
                        Files.writeString(Paths.get("out/animated_"+spotanimFrame+".json"), gson.toJson(model), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    max_animated_frame = spotanimFrame;
                }
            }
        }

        if (this.locModel != null) {
            if (Game.loopCycle >= locStopCycle) {
                this.locModel = null;
            }

            if ((Game.loopCycle >= locStartCycle) && (Game.loopCycle < locStopCycle)) {
                Model locModel = this.locModel;
                locModel.translate(locOffsetX - super.x, locOffsetY - y, locOffsetZ - super.z);

                if (super.dstYaw == 512) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1024) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1536) {
                    locModel.rotateY90();
                }

                model = Model.join_lit(2, -819, new Model[]{model, locModel});

                if (super.dstYaw == 512) {
                    locModel.rotateY90();
                } else if (super.dstYaw == 1024) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                } else if (super.dstYaw == 1536) {
                    locModel.rotateY90();
                    locModel.rotateY90();
                    locModel.rotateY90();
                }

                locModel.translate(super.x - locOffsetX, y - locOffsetY, super.z - locOffsetZ);
            }
        }

        model.pickable = true;
        return model;
    }

    public void read(Buffer in) {
        in.position = 0;
        gender = in.readU8();
        headicons = in.readU8();
        transmogrify = null;
        team = 0;

        for (int part = 0; part < 12; part++) {
            int msb = in.readU8();

            if (msb == 0) {
                appearances[part] = 0;
                continue;
            }

            int lsb = in.readU8();
            appearances[part] = (msb << 8) + lsb;

            if ((part == 0) && (appearances[0] == 65535)) {
                transmogrify = NPC.get(in.readU16());
                break;
            }

            if ((appearances[part] >= 512) && ((appearances[part] - 512) < Item.count)) {
                int team = Item.get(appearances[part] - 512).team;

                if (team != 0) {
                    this.team = team;
                }
            }
        }

        for (int part = 0; part < 5; part++) {
            int color = in.readU8();

            if ((color < 0) || (color >= Game.designPartColor[part].length)) {
                color = 0;
            }

            colors[part] = color;
        }

        System.out.println("appearances = " + Arrays.toString(appearances));
        System.out.println("colors = " + Arrays.toString(colors));

        super.seqStandID = in.readU16();
        if (super.seqStandID == 65535) {
            super.seqStandID = -1;
        }
        super.seqTurnID = in.readU16();
        if (super.seqTurnID == 65535) {
            super.seqTurnID = -1;
        }
        super.seqWalkID = in.readU16();
        if (super.seqWalkID == 65535) {
            super.seqWalkID = -1;
        }
        super.seqTurnAroundID = in.readU16();
        if (super.seqTurnAroundID == 65535) {
            super.seqTurnAroundID = -1;
        }
        super.seqTurnLeftID = in.readU16();
        if (super.seqTurnLeftID == 65535) {
            super.seqTurnLeftID = -1;
        }
        super.seqTurnRightID = in.readU16();
        if (super.seqTurnRightID == 65535) {
            super.seqTurnRightID = -1;
        }
        super.seqRunID = in.readU16();
        if (super.seqRunID == 65535) {
            super.seqRunID = -1;
        }
        name = StringUtil.formatName(StringUtil.fromBase37(in.read64()));
        combatLevel = in.readU8();
        skillLevel = in.readU16();
        visible = true;

        appearanceHashcode = 0L;

        for (int part = 0; part < 12; part++) {
            appearanceHashcode <<= 4;
            if (appearances[part] >= 256) {
                appearanceHashcode += appearances[part] - 256;
            }
        }

        if (appearances[0] >= 256) {
            appearanceHashcode += (appearances[0] - 256) >> 4;
        }

        if (appearances[1] >= 256) {
            appearanceHashcode += (appearances[1] - 256) >> 8;
        }

        for (int part = 0; part < 5; part++) {
            appearanceHashcode <<= 3;
            appearanceHashcode += colors[part];
        }

        appearanceHashcode <<= 1;
        appearanceHashcode += gender;
    }

    public Model getSequencedModel() {
        if (transmogrify != null) {
            int transformID = -1;
            if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
                transformID = Animation.instances[super.primarySeqID].primary_transforms[super.primarySeqFrame];
            } else if (super.secondarySeqID >= 0) {
                transformID = Animation.instances[super.secondarySeqID].primary_transforms[super.secondarySeqFrame];
            }
            return transmogrify.built_model(transformID, -1, null);
        }

        long hashCode = this.appearanceHashcode;
        int primaryTransformID = -1;
        int secondaryTransformID = -1;
        int rightHandValue = -1;
        int leftHandValue = -1;

        if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
            Animation type = Animation.instances[super.primarySeqID];
            primaryTransformID = type.primary_transforms[super.primarySeqFrame];

            if ((super.secondarySeqID >= 0) && (super.secondarySeqID != super.seqStandID)) {
                secondaryTransformID = Animation.instances[super.secondarySeqID].primary_transforms[super.secondarySeqFrame];
            }

            if (type.rightHandOverride >= 0) {
                rightHandValue = type.rightHandOverride;
                hashCode += ((long) rightHandValue - appearances[5]) << 8;
            }

            if (type.leftHandOverride >= 0) {
                leftHandValue = type.leftHandOverride;
                hashCode += ((long) leftHandValue - appearances[3]) << 16;
            }
        } else if (super.secondarySeqID >= 0) {
            primaryTransformID = Animation.instances[super.secondarySeqID].primary_transforms[super.secondarySeqFrame];
        }

        Model model = modelCache.get(hashCode);

        if (model == null) {
            boolean invalid = false;

            for (int part = 0; part < 12; part++) {
                int value = appearances[part];

                if ((leftHandValue >= 0) && (part == 3)) {
                    value = leftHandValue;
                }

                if ((rightHandValue >= 0) && (part == 5)) {
                    value = rightHandValue;
                }

                if ((value >= 256) && (value < 512) && !Identikit.instances[value - 256].validateModel()) {
                    invalid = true;
                }

                if ((value >= 512) && !Item.get(value - 512).validateWornModel(gender)) {
                    invalid = true;
                }
            }

            if (invalid) {
                if (modelUID != -1L) {
                    model = modelCache.get(modelUID);
                }
                if (model == null) {
                    return null;
                }
            }
        }

        if (model == null) {
            Model[] models = new Model[12];
            int modelCount = 0;

            for (int part = 0; part < 12; part++) {
                int value = appearances[part];

                if ((leftHandValue >= 0) && (part == 3)) {
                    value = leftHandValue;
                }

                if ((rightHandValue >= 0) && (part == 5)) {
                    value = rightHandValue;
                }

                if ((value >= 256) && (value < 512)) {
                    Model kitModel = Identikit.instances[value - 256].getModel();
                    if (kitModel != null) {
                        models[modelCount++] = kitModel;
                    }
                }

                if (value >= 512) {
                    Model objModel = Item.get(value - 512).getWornModel(gender);

                    if (objModel != null) {
                        models[modelCount++] = objModel;
                    }
                }
            }

            model = Model.join_prebuilt(modelCount, models);
            for (int part = 0; part < 5; part++) {
                if (colors[part] != 0) {
                    model.recolor(Game.designPartColor[part][0], Game.designPartColor[part][colors[part]]);
                    if (part == 1) {
                        model.recolor(Game.designHairColor[0], Game.designHairColor[colors[part]]);
                    }
                }
            }
            model.build_labels();
            model.build(64, 850, -30, -50, -30, true);
            modelCache.put(hashCode, model);
            modelUID = hashCode;
        }

        Model tmp = Model.EMPTY;
        tmp.set(model, AnimationTransform.isNull(primaryTransformID) & AnimationTransform.isNull(secondaryTransformID));

        if ((primaryTransformID != -1) && (secondaryTransformID != -1)) {
            tmp.transform2(primaryTransformID, secondaryTransformID, Animation.instances[super.primarySeqID].secondary_transform_mask);
        } else if (primaryTransformID != -1) {
            tmp.transform(primaryTransformID);
        }

        tmp.calc_bounds_cylinder();
        tmp.label_faces = null;
        tmp.label_vertices = null;
        return tmp;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public Model getHeadModel() {
        if (!visible) {
            return null;
        }

        if (transmogrify != null) {
            return transmogrify.get_chat_model();
        }

        boolean invalid = false;

        for (int part = 0; part < 12; part++) {
            int value = appearances[part];

            if ((value >= 256) && (value < 512) && !Identikit.instances[value - 256].validateHeadModel()) {
                invalid = true;
            }

            if ((value >= 512) && !Item.get(value - 512).validateHeadModel(gender)) {
                invalid = true;
            }
        }

        if (invalid) {
            return null;
        }

        Model[] models = new Model[12];
        int modelCount = 0;
        for (int part = 0; part < 12; part++) {
            int value = appearances[part];

            if ((value >= 256) && (value < 512)) {
                Model model = Identikit.instances[value - 256].getHeadModel();
                if (model != null) {
                    models[modelCount++] = model;
                }
            }
            if (value >= 512) {
                Model model = Item.get(value - 512).getHeadModel(gender);
                if (model != null) {
                    models[modelCount++] = model;
                }
            }
        }

        Model model = Model.join_prebuilt(modelCount, models);

        for (int part = 0; part < 5; part++) {
            if (colors[part] != 0) {
                model.recolor(Game.designPartColor[part][0], Game.designPartColor[part][colors[part]]);
                if (part == 1) {
                    model.recolor(Game.designHairColor[0], Game.designHairColor[colors[part]]);
                }
            }
        }

        return model;
    }

}
