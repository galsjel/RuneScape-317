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

	public static void unpack(FileArchive config, BitmapFont[] fonts, FileArchive media) throws IOException {
		imageCache = new LRUMap<>(500);
		Buffer in = new Buffer(config.read("data"));

		int parentID = -1;
		int count = in.read16U();

		instances = new IfType[count];

		while (in.position < in.data.length) {
			int id = in.read16U();

			if (id == 65535) {
				parentID = in.read16U();
				id = in.read16U();
			}

			IfType iface = instances[id] = new IfType();
			iface.id = id;
			iface.parentID = parentID;
			iface.type = in.read8U();
			iface.optionType = in.read8U();
			iface.contentType = in.read16U();
			iface.width = in.read16U();
			iface.height = in.read16U();
			iface.transparency = (byte) in.read8U();
			iface.delegateHover = in.read8U();

			if (iface.delegateHover != 0) {
				iface.delegateHover = ((iface.delegateHover - 1) << 8) + in.read8U();
			} else {
				iface.delegateHover = -1;
			}

			int comparatorCount = in.read8U();
			if (comparatorCount > 0) {
				iface.scriptComparator = new int[comparatorCount];
				iface.scriptOperand = new int[comparatorCount];
				for (int i = 0; i < comparatorCount; i++) {
					iface.scriptComparator[i] = in.read8U();
					iface.scriptOperand[i] = in.read16U();
				}
			}

			int scriptCount = in.read8U();
			if (scriptCount > 0) {
				iface.scripts = new int[scriptCount][];
				for (int scriptID = 0; scriptID < scriptCount; scriptID++) {
					int length = in.read16U();
					iface.scripts[scriptID] = new int[length];
					for (int i = 0; i < length; i++) {
						iface.scripts[scriptID][i] = in.read16U();
					}
				}
			}

			if (iface.type == TYPE_PARENT) {
				iface.scrollableHeight = in.read16U();
				iface.hide = in.read8U() == 1;
				int childCount = in.read16U();
				iface.childID = new int[childCount];
				iface.childX = new int[childCount];
				iface.childY = new int[childCount];
				for (int i = 0; i < childCount; i++) {
					iface.childID[i] = in.read16U();
					iface.childX[i] = in.read16();
					iface.childY[i] = in.read16();
				}
			}

			if (iface.type == TYPE_UNUSED) {
				iface.unusedInt = in.read16U();
				iface.unusedBool = in.read8U() == 1;
			}

			if (iface.type == TYPE_INVENTORY) {
				iface.inventorySlotObjID = new int[iface.width * iface.height];
				iface.inventorySlotObjCount = new int[iface.width * iface.height];
				iface.inventoryDraggable = in.read8U() == 1;
				iface.inventoryInteractable = in.read8U() == 1;
				iface.inventoryUsable = in.read8U() == 1;
				iface.inventoryMoveReplaces = in.read8U() == 1;
				iface.inventoryMarginX = in.read8U();
				iface.inventoryMarginY = in.read8U();
				iface.inventorySlotOffsetX = new int[20];
				iface.inventorySlotOffsetY = new int[20];
				iface.inventorySlotImage = new Image24[20];

				for (int slot = 0; slot < 20; slot++) {
					if (in.read8U() == 1) {
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
				iface.fill = in.read8U() == 1;
			}

			if ((iface.type == TYPE_TEXT) || (iface.type == TYPE_UNUSED)) {
				iface.center = in.read8U() == 1;
				int fontID = in.read8U();
				if (fonts != null) {
					iface.font = fonts[fontID];
				}
				iface.shadow = in.read8U() == 1;
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
				int tmp = in.read8U();
				if (tmp != 0) {
					iface.modelCategory = 1;
					iface.modelID = ((tmp - 1) << 8) + in.read8U();
				}

				tmp = in.read8U();
				if (tmp != 0) {
					iface.activeModelCategory = 1;
					iface.activeModelID = ((tmp - 1) << 8) + in.read8U();
				}

				tmp = in.read8U();
				if (tmp != 0) {
					iface.seqID = ((tmp - 1) << 8) + in.read8U();
				} else {
					iface.seqID = -1;
				}

				tmp = in.read8U();
				if (tmp != 0) {
					iface.activeSeqID = ((tmp - 1) << 8) + in.read8U();
				} else {
					iface.activeSeqID = -1;
				}

				iface.modelZoom = in.read16U();
				iface.modelPitch = in.read16U();
				iface.modelYaw = in.read16U();
			}

			if (iface.type == TYPE_INVENTORY_TEXT) {
				iface.inventorySlotObjID = new int[iface.width * iface.height];
				iface.inventorySlotObjCount = new int[iface.width * iface.height];
				iface.center = in.read8U() == 1;
				int fontID = in.read8U();
				if (fonts != null) {
					iface.font = fonts[fontID];
				}
				iface.shadow = in.read8U() == 1;
				iface.color = in.read32();
				iface.inventoryMarginX = in.read16();
				iface.inventoryMarginY = in.read16();
				iface.inventoryInteractable = in.read8U() == 1;
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
				iface.spellFlags = in.read16U();
			}

			if (iface.optionType == OPTION_TYPE_STANDARD || iface.optionType == OPTION_TYPE_TOGGLE || iface.optionType == OPTION_TYPE_SELECT || iface.optionType == OPTION_TYPE_CONTINUE) {
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
		if ((model != null) && (type != 4)) {
			modelCache.put(((long) type << 16) + id, model);
		}
	}

	public boolean inventoryInteractable;
	public int activeColor;
	public int activeHoverColor;
	public Image24 activeImage;
	public int activeModelCategory;
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
	public boolean unusedBool;
	public int unusedInt;
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
