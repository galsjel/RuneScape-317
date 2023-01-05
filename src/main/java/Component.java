// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class Component {

	public static final LRUMap<Long, Model> modelCache = new LRUMap<>(30);
	public static LRUMap<Long, Image24> imageCache;
	public static Component[] instances;

	public static void unpack(FileArchive config, BitmapFont[] fonts, FileArchive media) throws IOException {
		imageCache = new LRUMap<>(500);
		Buffer in = new Buffer(config.read("data"));

		int parentID = -1;
		int count = in.read16U();

		instances = new Component[count];

		while (in.position < in.data.length) {
			int id = in.read16U();

			if (id == 65535) {
				parentID = in.read16U();
				id = in.read16U();
			}

			Component c = instances[id] = new Component();
			c.id = id;
			c.parentID = parentID;
			c.type = in.read8U();
			c.optionType = in.read8U();
			c.contentType = in.read16U();
			c.width = in.read16U();
			c.height = in.read16U();
			c.transparency = (byte) in.read8U();
			c.delegateHover = in.read8U();

			if (c.delegateHover != 0) {
				c.delegateHover = ((c.delegateHover - 1) << 8) + in.read8U();
			} else {
				c.delegateHover = -1;
			}

			int comparatorCount = in.read8U();
			if (comparatorCount > 0) {
				c.scriptComparator = new int[comparatorCount];
				c.scriptOperand = new int[comparatorCount];
				for (int i = 0; i < comparatorCount; i++) {
					c.scriptComparator[i] = in.read8U();
					c.scriptOperand[i] = in.read16U();
				}
			}

			int scriptCount = in.read8U();
			if (scriptCount > 0) {
				c.scripts = new int[scriptCount][];
				for (int scriptID = 0; scriptID < scriptCount; scriptID++) {
					int length = in.read16U();
					c.scripts[scriptID] = new int[length];
					for (int i = 0; i < length; i++) {
						c.scripts[scriptID][i] = in.read16U();
					}
				}
			}

			if (c.type == 0) {
				c.scrollableHeight = in.read16U();
				c.hide = in.read8U() == 1;
				int childCount = in.read16U();
				c.children = new int[childCount];
				c.childX = new int[childCount];
				c.childY = new int[childCount];
				for (int i = 0; i < childCount; i++) {
					c.children[i] = in.read16U();
					c.childX[i] = in.read16();
					c.childY[i] = in.read16();
				}
			}

			if (c.type == 1) {
				c.unusedInt = in.read16U();
				c.unusedBool = in.read8U() == 1;
			}

			if (c.type == 2) {
				c.invSlotObjID = new int[c.width * c.height];
				c.invSlotAmount = new int[c.width * c.height];
				c.invDraggable = in.read8U() == 1;
				c.aBoolean249 = in.read8U() == 1;
				c.invUsable = in.read8U() == 1;
				c.invMoveReplaces = in.read8U() == 1;
				c.invMarginX = in.read8U();
				c.invMarginY = in.read8U();
				c.invSlotX = new int[20];
				c.invSlotY = new int[20];
				c.invSlotImage = new Image24[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = in.read8U();
					if (k3 == 1) {
						c.invSlotX[j2] = in.read16();
						c.invSlotY[j2] = in.read16();
						String s1 = in.readString();
						if ((media != null) && (s1.length() > 0)) {
							int i5 = s1.lastIndexOf(",");
							c.invSlotImage[j2] = getImage(Integer.parseInt(s1.substring(i5 + 1)), media, s1.substring(0, i5));
						}
					}
				}
				c.invOptions = new String[5];
				for (int i = 0; i < 5; i++) {
					c.invOptions[i] = in.readString();
					if (c.invOptions[i].length() == 0) {
						c.invOptions[i] = null;
					}
				}
			}

			if (c.type == 3) {
				c.fill = in.read8U() == 1;
			}

			if ((c.type == 4) || (c.type == 1)) {
				c.center = in.read8U() == 1;
				int fontID = in.read8U();
				if (fonts != null) {
					c.font = fonts[fontID];
				}
				c.shadow = in.read8U() == 1;
			}

			if (c.type == 4) {
				c.text = in.readString();
				c.activeText = in.readString();
			}

			if ((c.type == 1) || (c.type == 3) || (c.type == 4)) {
				c.color = in.read32();
			}

			if ((c.type == 3) || (c.type == 4)) {
				c.activeColor = in.read32();
				c.hoverColor = in.read32();
				c.activeHoverColor = in.read32();
			}

			if (c.type == 5) {
				String s = in.readString();
				if ((media != null) && (s.length() > 0)) {
					int comma = s.lastIndexOf(",");
					c.image = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
				}
				s = in.readString();
				if ((media != null) && (s.length() > 0)) {
					int comma = s.lastIndexOf(",");
					c.activeImage = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
				}
			}

			if (c.type == 6) {
				int tmp = in.read8U();
				if (tmp != 0) {
					c.modelCategory = 1;
					c.modelID = ((tmp - 1) << 8) + in.read8U();
				}

				tmp = in.read8U();
				if (tmp != 0) {
					c.activeModelCategory = 1;
					c.activeModelID = ((tmp - 1) << 8) + in.read8U();
				}

				tmp = in.read8U();
				if (tmp != 0) {
					c.seqID = ((tmp - 1) << 8) + in.read8U();
				} else {
					c.seqID = -1;
				}

				tmp = in.read8U();
				if (tmp != 0) {
					c.activeSeqID = ((tmp - 1) << 8) + in.read8U();
				} else {
					c.activeSeqID = -1;
				}

				c.modelZoom = in.read16U();
				c.modelPitch = in.read16U();
				c.modelYaw = in.read16U();
			}

			if (c.type == 7) {
				c.invSlotObjID = new int[c.width * c.height];
				c.invSlotAmount = new int[c.width * c.height];
				c.center = in.read8U() == 1;
				int fontID = in.read8U();
				if (fonts != null) {
					c.font = fonts[fontID];
				}
				c.shadow = in.read8U() == 1;
				c.color = in.read32();
				c.invMarginX = in.read16();
				c.invMarginY = in.read16();
				c.aBoolean249 = in.read8U() == 1;
				c.invOptions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					c.invOptions[k4] = in.readString();
					if (c.invOptions[k4].length() == 0) {
						c.invOptions[k4] = null;
					}
				}
			}

			if (c.type == 8) {
				c.spellAction = in.readString();
			}

			if ((c.optionType == 2) || (c.type == 2)) {
				c.spellAction = in.readString();
				c.spellName = in.readString();
				c.spellFlags = in.read16U();
			}

			if ((c.optionType == 1) || (c.optionType == 4) || (c.optionType == 5) || (c.optionType == 6)) {
				c.option = in.readString();
				if (c.option.length() == 0) {
					if (c.optionType == 1) {
						c.option = "Ok";
					} else if (c.optionType == 4) {
						c.option = "Select";
					} else if (c.optionType == 5) {
						c.option = "Select";
					} else if (c.optionType == 6) {
						c.option = "Continue";
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
		if ((model != null) && (type != 4)) {
			modelCache.put(((long) type << 16) + id, model);
		}
	}

	public boolean aBoolean249;
	public int activeColor;
	public int activeHoverColor;
	public Image24 activeImage;
	public int activeModelCategory;
	public int activeModelID;
	public int activeSeqID;
	public String activeText;
	public boolean center;
	public int[] children;
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
	 * @see Game#drawParentComponent(Component, int, int, int)
	 */
	public boolean hide;
	public int hoverColor;
	public int id;
	public Image24 image;
	public boolean invDraggable;
	public int invMarginX;
	public int invMarginY;
	public boolean invMoveReplaces;
	public String[] invOptions;
	public int[] invSlotAmount;
	public Image24[] invSlotImage;
	public int[] invSlotObjID;
	public int[] invSlotX;
	public int[] invSlotY;
	public boolean invUsable;
	public int modelPitch;
	public int modelCategory;
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
	public int scrollPos;
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
	public boolean unusedBool;
	public int unusedInt;
	public int width;
	public int x;
	public int y;


	public Component() {
	}

	public void swapSlots(int src, int dst) {
		int tmp = invSlotObjID[src];
		invSlotObjID[src] = invSlotObjID[dst];
		invSlotObjID[dst] = tmp;

		tmp = invSlotAmount[src];
		invSlotAmount[src] = invSlotAmount[dst];
		invSlotAmount[dst] = tmp;
	}

	public Model getModel(int category, int id) {
		Model model = modelCache.get(((long) category << 16) + id);
		if (model != null) {
			return model;
		}
		if (category == 1) {
			model = Model.tryGet(id);
		}
		if (category == 2) {
			model = NPCType.get(id).getUnlitHeadModel();
		}
		if (category == 3) {
			model = Game.localPlayer.getUnlitHeadModel();
		}
		if (category == 4) {
			model = ObjType.get(id).getUnlitModel(50);
		}
		if (model != null) {
			modelCache.put((long) ((category << 16) + id), model);
		}
		return model;
	}

	public Model getModel(int secondaryTransformID, int primaryTransformID, boolean active) {
		Model model;

		if (active) {
			model = getModel(activeModelCategory, activeModelID);
		} else {
			model = getModel(modelCategory, modelID);
		}

		if (model == null) {
			return null;
		}

		if ((primaryTransformID == -1) && (secondaryTransformID == -1) && (model.faceColor == null)) {
			return model;
		}

		Model animatedModel = new Model(true, SeqTransform.isNull(primaryTransformID) & SeqTransform.isNull(secondaryTransformID), false, model);

		if ((primaryTransformID != -1) || (secondaryTransformID != -1)) {
			animatedModel.createLabelReferences();
		}

		if (primaryTransformID != -1) {
			animatedModel.applyTransform(primaryTransformID);
		}

		if (secondaryTransformID != -1) {
			animatedModel.applyTransform(secondaryTransformID);
		}

		animatedModel.calculateNormals(64, 768, -50, -10, -50, true);
		return animatedModel;
	}

}
