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
		int count = in.get2U();

		instances = new Component[count];

		while (in.position < in.data.length) {
			int id = in.get2U();

			if (id == 65535) {
				parentID = in.get2U();
				id = in.get2U();
			}

			Component c = instances[id] = new Component();
			c.id = id;
			c.parentID = parentID;
			c.type = in.get1U();
			c.optionType = in.get1U();
			c.contentType = in.get2U();
			c.width = in.get2U();
			c.height = in.get2U();
			c.transparency = (byte) in.get1U();
			c.delegateHover = in.get1U();

			if (c.delegateHover != 0) {
				c.delegateHover = ((c.delegateHover - 1) << 8) + in.get1U();
			} else {
				c.delegateHover = -1;
			}

			int comparatorCount = in.get1U();
			if (comparatorCount > 0) {
				c.scriptComparator = new int[comparatorCount];
				c.scriptOperand = new int[comparatorCount];
				for (int i = 0; i < comparatorCount; i++) {
					c.scriptComparator[i] = in.get1U();
					c.scriptOperand[i] = in.get2U();
				}
			}

			int scriptCount = in.get1U();
			if (scriptCount > 0) {
				c.scripts = new int[scriptCount][];
				for (int scriptID = 0; scriptID < scriptCount; scriptID++) {
					int length = in.get2U();
					c.scripts[scriptID] = new int[length];
					for (int i = 0; i < length; i++) {
						c.scripts[scriptID][i] = in.get2U();
					}
				}
			}

			if (c.type == 0) {
				c.scrollableHeight = in.get2U();
				c.hidden = in.get1U() == 1;
				int childCount = in.get2U();
				c.children = new int[childCount];
				c.childX = new int[childCount];
				c.childY = new int[childCount];
				for (int i = 0; i < childCount; i++) {
					c.children[i] = in.get2U();
					c.childX[i] = in.get2();
					c.childY[i] = in.get2();
				}
			}

			if (c.type == 1) {
				c.unusedInt = in.get2U();
				c.unusedBool = in.get1U() == 1;
			}

			if (c.type == 2) {
				c.invSlotObjID = new int[c.width * c.height];
				c.invSlotAmount = new int[c.width * c.height];
				c.invDraggable = in.get1U() == 1;
				c.aBoolean249 = in.get1U() == 1;
				c.invUsable = in.get1U() == 1;
				c.invMoveReplaces = in.get1U() == 1;
				c.invMarginX = in.get1U();
				c.invMarginY = in.get1U();
				c.invSlotX = new int[20];
				c.invSlotY = new int[20];
				c.invSlotImage = new Image24[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = in.get1U();
					if (k3 == 1) {
						c.invSlotX[j2] = in.get2();
						c.invSlotY[j2] = in.get2();
						String s1 = in.getString();
						if ((media != null) && (s1.length() > 0)) {
							int i5 = s1.lastIndexOf(",");
							c.invSlotImage[j2] = getImage(Integer.parseInt(s1.substring(i5 + 1)), media, s1.substring(0, i5));
						}
					}
				}
				c.invOptions = new String[5];
				for (int i = 0; i < 5; i++) {
					c.invOptions[i] = in.getString();
					if (c.invOptions[i].length() == 0) {
						c.invOptions[i] = null;
					}
				}
			}

			if (c.type == 3) {
				c.fill = in.get1U() == 1;
			}

			if ((c.type == 4) || (c.type == 1)) {
				c.center = in.get1U() == 1;
				int fontID = in.get1U();
				if (fonts != null) {
					c.font = fonts[fontID];
				}
				c.shadow = in.get1U() == 1;
			}

			if (c.type == 4) {
				c.text = in.getString();
				c.activeText = in.getString();
			}

			if ((c.type == 1) || (c.type == 3) || (c.type == 4)) {
				c.color = in.get4();
			}

			if ((c.type == 3) || (c.type == 4)) {
				c.activeColor = in.get4();
				c.hoverColor = in.get4();
				c.activeHoverColor = in.get4();
			}

			if (c.type == 5) {
				String s = in.getString();
				if ((media != null) && (s.length() > 0)) {
					int comma = s.lastIndexOf(",");
					c.image = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
				}
				s = in.getString();
				if ((media != null) && (s.length() > 0)) {
					int comma = s.lastIndexOf(",");
					c.activeImage = getImage(Integer.parseInt(s.substring(comma + 1)), media, s.substring(0, comma));
				}
			}

			if (c.type == 6) {
				int tmp = in.get1U();
				if (tmp != 0) {
					c.modelCategory = 1;
					c.modelID = ((tmp - 1) << 8) + in.get1U();
				}

				tmp = in.get1U();
				if (tmp != 0) {
					c.activeModelCategory = 1;
					c.activeModelID = ((tmp - 1) << 8) + in.get1U();
				}

				tmp = in.get1U();
				if (tmp != 0) {
					c.seqID = ((tmp - 1) << 8) + in.get1U();
				} else {
					c.seqID = -1;
				}

				tmp = in.get1U();
				if (tmp != 0) {
					c.activeSeqID = ((tmp - 1) << 8) + in.get1U();
				} else {
					c.activeSeqID = -1;
				}

				c.modelZoom = in.get2U();
				c.modelPitch = in.get2U();
				c.modelYaw = in.get2U();
			}

			if (c.type == 7) {
				c.invSlotObjID = new int[c.width * c.height];
				c.invSlotAmount = new int[c.width * c.height];
				c.center = in.get1U() == 1;
				int fontID = in.get1U();
				if (fonts != null) {
					c.font = fonts[fontID];
				}
				c.shadow = in.get1U() == 1;
				c.color = in.get4();
				c.invMarginX = in.get2();
				c.invMarginY = in.get2();
				c.aBoolean249 = in.get1U() == 1;
				c.invOptions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					c.invOptions[k4] = in.getString();
					if (c.invOptions[k4].length() == 0) {
						c.invOptions[k4] = null;
					}
				}
			}

			if (c.type == 8) {
				c.spellAction = in.getString();
			}

			if ((c.optionType == 2) || (c.type == 2)) {
				c.spellAction = in.getString();
				c.spellName = in.getString();
				c.spellFlags = in.get2U();
			}

			if ((c.optionType == 1) || (c.optionType == 4) || (c.optionType == 5) || (c.optionType == 6)) {
				c.option = in.getString();
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
	public boolean hidden;
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
	public int scrollY;
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
