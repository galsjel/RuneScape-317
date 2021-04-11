// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;

public class Component {

	public static final LRUMap<Long, Model> modelCache = new LRUMap<>(30);
	public static LRUMap<Long, Image24> imageCache;
	public static Component[] instances;

	public static void unpack(FileArchive archive, BitmapFont[] aclass30_sub2_sub1_sub4, FileArchive archive_1) throws IOException {
		imageCache = new LRUMap<>(500);
		Buffer buffer = new Buffer(archive.read("data"));
		int parentId = -1;
		int j = buffer.get2U();
		instances = new Component[j];
		while (buffer.position < buffer.data.length) {
			int id = buffer.get2U();
			if (id == 65535) {
				parentId = buffer.get2U();
				id = buffer.get2U();
			}
			Component component = instances[id] = new Component();
			component.id = id;
			component.parentId = parentId;
			component.type = buffer.get1U();
			component.optionType = buffer.get1U();
			component.contentType = buffer.get2U();
			component.width = buffer.get2U();
			component.height = buffer.get2U();
			component.aByte254 = (byte) buffer.get1U();
			component.anInt230 = buffer.get1U();
			if (component.anInt230 != 0) {
				component.anInt230 = ((component.anInt230 - 1) << 8) + buffer.get1U();
			} else {
				component.anInt230 = -1;
			}
			int i1 = buffer.get1U();
			if (i1 > 0) {
				component.anIntArray245 = new int[i1];
				component.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					component.anIntArray245[j1] = buffer.get1U();
					component.anIntArray212[j1] = buffer.get2U();
				}
			}
			int k1 = buffer.get1U();
			if (k1 > 0) {
				component.anIntArrayArray226 = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = buffer.get2U();
					component.anIntArrayArray226[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						component.anIntArrayArray226[l1][l4] = buffer.get2U();
					}
				}
			}
			if (component.type == 0) {
				component.innerHeight = buffer.get2U();
				component.aBoolean266 = buffer.get1U() == 1;
				int i2 = buffer.get2U();
				component.children = new int[i2];
				component.childX = new int[i2];
				component.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					component.children[j3] = buffer.get2U();
					component.childX[j3] = buffer.get2();
					component.childY[j3] = buffer.get2();
				}
			}
			if (component.type == 1) {
				component.unusedInt = buffer.get2U();
				component.unusedBool = buffer.get1U() == 1;
			}
			if (component.type == 2) {
				component.slotObjId = new int[component.width * component.height];
				component.slotAmount = new int[component.width * component.height];
				component.aBoolean259 = buffer.get1U() == 1;
				component.aBoolean249 = buffer.get1U() == 1;
				component.aBoolean242 = buffer.get1U() == 1;
				component.objMoveReplaces = buffer.get1U() == 1;
				component.slotMarginX = buffer.get1U();
				component.slotMarginY = buffer.get1U();
				component.slotX = new int[20];
				component.slotY = new int[20];
				component.aImageArray209 = new Image24[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = buffer.get1U();
					if (k3 == 1) {
						component.slotX[j2] = buffer.get2();
						component.slotY[j2] = buffer.get2();
						String s1 = buffer.getString();
						if ((archive_1 != null) && (s1.length() > 0)) {
							int i5 = s1.lastIndexOf(",");
							component.aImageArray209[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), archive_1, s1.substring(0, i5));
						}
					}
				}
				component.aStringArray225 = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					component.aStringArray225[l3] = buffer.getString();
					if (component.aStringArray225[l3].length() == 0) {
						component.aStringArray225[l3] = null;
					}
				}
			}
			if (component.type == 3) {
				component.aBoolean227 = buffer.get1U() == 1;
			}
			if ((component.type == 4) || (component.type == 1)) {
				component.aBoolean223 = buffer.get1U() == 1;
				int k2 = buffer.get1U();
				if (aclass30_sub2_sub1_sub4 != null) {
					component.aFont_243 = aclass30_sub2_sub1_sub4[k2];
				}
				component.aBoolean268 = buffer.get1U() == 1;
			}
			if (component.type == 4) {
				component.aString248 = buffer.getString();
				component.aString228 = buffer.getString();
			}
			if ((component.type == 1) || (component.type == 3) || (component.type == 4)) {
				component.anInt232 = buffer.get4();
			}
			if ((component.type == 3) || (component.type == 4)) {
				component.anInt219 = buffer.get4();
				component.anInt216 = buffer.get4();
				component.anInt239 = buffer.get4();
			}
			if (component.type == 5) {
				String s = buffer.getString();
				if ((archive_1 != null) && (s.length() > 0)) {
					int i4 = s.lastIndexOf(",");
					component.aImage_207 = method207(Integer.parseInt(s.substring(i4 + 1)), archive_1, s.substring(0, i4));
				}
				s = buffer.getString();
				if ((archive_1 != null) && (s.length() > 0)) {
					int j4 = s.lastIndexOf(",");
					component.aImage_260 = method207(Integer.parseInt(s.substring(j4 + 1)), archive_1, s.substring(0, j4));
				}
			}
			if (component.type == 6) {
				int l = buffer.get1U();
				if (l != 0) {
					component.anInt233 = 1;
					component.anInt234 = ((l - 1) << 8) + buffer.get1U();
				}
				l = buffer.get1U();
				if (l != 0) {
					component.anInt255 = 1;
					component.anInt256 = ((l - 1) << 8) + buffer.get1U();
				}
				l = buffer.get1U();
				if (l != 0) {
					component.seqId = ((l - 1) << 8) + buffer.get1U();
				} else {
					component.seqId = -1;
				}
				l = buffer.get1U();
				if (l != 0) {
					component.scriptSeqId = ((l - 1) << 8) + buffer.get1U();
				} else {
					component.scriptSeqId = -1;
				}
				component.anInt269 = buffer.get2U();
				component.anInt270 = buffer.get2U();
				component.anInt271 = buffer.get2U();
			}
			if (component.type == 7) {
				component.slotObjId = new int[component.width * component.height];
				component.slotAmount = new int[component.width * component.height];
				component.aBoolean223 = buffer.get1U() == 1;
				int l2 = buffer.get1U();
				if (aclass30_sub2_sub1_sub4 != null) {
					component.aFont_243 = aclass30_sub2_sub1_sub4[l2];
				}
				component.aBoolean268 = buffer.get1U() == 1;
				component.anInt232 = buffer.get4();
				component.slotMarginX = buffer.get2();
				component.slotMarginY = buffer.get2();
				component.aBoolean249 = buffer.get1U() == 1;
				component.aStringArray225 = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					component.aStringArray225[k4] = buffer.getString();
					if (component.aStringArray225[k4].length() == 0) {
						component.aStringArray225[k4] = null;
					}
				}
			}
			if ((component.optionType == 2) || (component.type == 2)) {
				component.aString222 = buffer.getString();
				component.aString218 = buffer.getString();
				component.anInt237 = buffer.get2U();
			}
			if ((component.optionType == 1) || (component.optionType == 4) || (component.optionType == 5) || (component.optionType == 6)) {
				component.aString221 = buffer.getString();
				if (component.aString221.length() == 0) {
					if (component.optionType == 1) {
						component.aString221 = "Ok";
					}
					if (component.optionType == 4) {
						component.aString221 = "Select";
					}
					if (component.optionType == 5) {
						component.aString221 = "Select";
					}
					if (component.optionType == 6) {
						component.aString221 = "Continue";
					}
				}
			}
		}
		imageCache = null;
	}

	public static Image24 method207(int i, FileArchive archive, String s) {
		long l = (StringUtil.hashCode(s) << 8) + (long) i;
		Image24 image = imageCache.get(l);
		if (image != null) {
			return image;
		}
		try {
			image = new Image24(archive, s, i);
			imageCache.put(l, image);
		} catch (Exception _ex) {
			return null;
		}
		return image;
	}

	public static void method208(int i, int j, Model model) {
		modelCache.clear();
		if ((model != null) && (j != 4)) {
			modelCache.put(((long) j << 16) + i, model);
		}
	}

	public Image24 aImage_207;
	public int seqCycle;
	public Image24[] aImageArray209;
	public int unusedInt;
	public int[] anIntArray212;
	public int contentType;
	public int[] slotX;
	public int anInt216;
	public int optionType;
	public String aString218;
	public int anInt219;
	public int width;
	public String aString221;
	public String aString222;
	public boolean aBoolean223;
	public int scrollY;
	public String[] aStringArray225;
	public int[][] anIntArrayArray226;
	public boolean aBoolean227;
	public String aString228;
	public int anInt230;
	public int slotMarginX;
	public int anInt232;
	public int anInt233;
	public int anInt234;
	/**
	 * When <code>true</code> moving an <code>Obj</code> from one slot to another will simply erase the item from the
	 * destination slot.
	 */
	public boolean objMoveReplaces;
	public int parentId;
	public int anInt237;
	public int anInt239;
	public int[] children;
	public int[] childX;
	public boolean aBoolean242;
	public BitmapFont aFont_243;
	public int slotMarginY;
	public int[] anIntArray245;
	public int seqFrame;
	public int[] slotY;
	public String aString248;
	public boolean aBoolean249;
	public int id;
	public boolean unusedBool;
	public int[] slotAmount;
	public int[] slotObjId;
	public byte aByte254;
	public int anInt255;
	public int anInt256;
	public int seqId;
	public int scriptSeqId;
	public boolean aBoolean259;
	public Image24 aImage_260;
	public int innerHeight;
	public int type;
	public int x;
	public int y;
	public boolean aBoolean266;
	public int height;
	public boolean aBoolean268;
	public int anInt269;
	public int anInt270;
	public int anInt271;
	public int[] childY;

	public Component() {
	}

	public void swapSlots(int src, int dst) {
		int tmp = slotObjId[src];
		slotObjId[src] = slotObjId[dst];
		slotObjId[dst] = tmp;

		tmp = slotAmount[src];
		slotAmount[src] = slotAmount[dst];
		slotAmount[dst] = tmp;
	}

	public Model method206(int i, int j) {
		Model model = modelCache.get(((long) i << 16) + j);
		if (model != null) {
			return model;
		}
		if (i == 1) {
			model = Model.tryGet(j);
		}
		if (i == 2) {
			model = NPCType.get(j).method160();
		}
		if (i == 3) {
			model = Game.self.method453();
		}
		if (i == 4) {
			model = ObjType.get(j).method202(50);
		}
		if (i == 5) {
			model = null;
		}
		if (model != null) {
			modelCache.put((long) ((i << 16) + j), model);
		}
		return model;
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag) {
			model = method206(anInt255, anInt256);
		} else {
			model = method206(anInt233, anInt234);
		}
		if (model == null) {
			return null;
		}
		if ((k == -1) && (j == -1) && (model.faceColor == null)) {
			return model;
		}
		Model model_1 = new Model(true, SeqFrame.isNull(k) & SeqFrame.isNull(j), false, model);
		if ((k != -1) || (j != -1)) {
			model_1.createLabelReferences();
		}
		if (k != -1) {
			model_1.applySequenceFrame(k);
		}
		if (j != -1) {
			model_1.applySequenceFrame(j);
		}
		model_1.calculateNormals(64, 768, -50, -10, -50, true);
		return model_1;
	}

}
