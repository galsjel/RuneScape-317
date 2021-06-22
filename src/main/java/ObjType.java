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
		count = idx.get2U();
		typeOffset = new int[count];
		int i = 2;
		for (int j = 0; j < count; j++) {
			typeOffset[j] = i;
			i += idx.get2U();
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
		if (type.certificateId != -1) {
			type.toCertificate();
		}
		if (!Game.members && type.members) {
			type.name = "Members Object";
			type.examine = "Login to a members' server to use this object.".getBytes();
			type.groundOptions = null;
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

		if (type.stackId == null) {
			amount = -1;
		}

		if (amount > 1) {
			int newId = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if ((amount >= type.stackAmount[j1]) && (type.stackAmount[j1] != 0)) {
					newId = type.stackId[j1];
				}
			}
			if (newId != -1) {
				type = get(newId);
			}
		}

		Model model = type.getModel(1);

		if (model == null) {
			return null;
		}

		// this will typically be the certificate item for noted stuff
		Image24 originalIcon = null;

		if (type.certificateId != -1) {
			originalIcon = getIcon(type.linkedId, 10, -1);

			if (originalIcon == null) {
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

		if (type.certificateId != -1) {
			int w = originalIcon.cropW;
			int h = originalIcon.cropH;
			originalIcon.cropW = 32;
			originalIcon.cropH = 32;
			originalIcon.draw(0, 0);
			originalIcon.cropW = w;
			originalIcon.cropH = h;
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
	public int femaleModelId2;
	public int certificateId;
	public int femaleModelId1;
	public int maleModelId0;
	public int maleHeadModelId1;
	public int scaleX;
	public String[] groundOptions;
	public int iconOffsetX;
	public String name;
	public int femaleHeadModel1;
	public int modelId;
	public int maleHeadModelId0;
	public boolean stackable;
	public byte[] examine;
	public int linkedId;
	public int iconZoom;
	public int lightAttenuation;
	public int maleModelId2;
	public int maleModelId1;
	public String[] inventoryOptions;
	public int iconPitch;
	public int scaleY;
	public int scaleZ;
	public int[] stackId;
	public int iconOffsetY;
	public int lightAmbient;
	public int femaleHeadModel0;
	public int iconYaw;
	public int unusedInt;
	public int femaleModelId0;
	public int[] stackAmount;
	public int team;
	public int iconRoll;
	public byte maleOffsetY;

	public ObjType() {
	}

	public boolean validateHeadModel(int gender) {
		int m0 = maleHeadModelId0;
		int m1 = maleHeadModelId1;
		if (gender == 1) {
			m0 = femaleHeadModel0;
			m1 = femaleHeadModel1;
		}
		if (m0 == -1) {
			return true;
		}
		boolean valid = Model.validate(m0);
		if ((m1 != -1) && !Model.validate(m1)) {
			valid = false;
		}
		return valid;
	}

	public Model getHeadModel(int gender) {
		int m0 = maleHeadModelId0;
		int m1 = maleHeadModelId1;
		if (gender == 1) {
			m0 = femaleHeadModel0;
			m1 = femaleHeadModel1;
		}
		if (m0 == -1) {
			return null;
		}
		Model model = Model.tryGet(m0);
		if (m1 != -1) {
			model = new Model(2, new Model[]{model, Model.tryGet(m1)});
		}
		if (srcColor != null) {
			for (int i = 0; i < srcColor.length; i++) {
				model.recolor(srcColor[i], dstColor[i]);
			}
		}
		return model;
	}

	public boolean validateWornModel(int gender) {
		int m0 = maleModelId0;
		int m1 = maleModelId1;
		int m2 = maleModelId2;
		if (gender == 1) {
			m0 = femaleModelId0;
			m1 = femaleModelId1;
			m2 = femaleModelId2;
		}
		if (m0 == -1) {
			return true;
		}
		boolean valid = Model.validate(m0);
		if ((m1 != -1) && !Model.validate(m1)) {
			valid = false;
		}
		if ((m2 != -1) && !Model.validate(m2)) {
			valid = false;
		}
		return valid;
	}

	public Model getWornModel(int gender) {
		int m0 = maleModelId0;
		int m1 = maleModelId1;
		int m2 = maleModelId2;

		if (gender == 1) {
			m0 = femaleModelId0;
			m1 = femaleModelId1;
			m2 = femaleModelId2;
		}

		if (m0 == -1) {
			return null;
		}

		Model model = Model.tryGet(m0);

		if (m1 != -1) {
			if (m2 != -1) {
				model = new Model(3, new Model[]{model, Model.tryGet(m1), Model.tryGet(m2)});
			} else {
				model = new Model(2, new Model[]{model, Model.tryGet(m1)});
			}
		}

		if ((gender == 0) && (maleOffsetY != 0)) {
			model.translate(0, maleOffsetY, 0);
		}

		if ((gender == 1) && (femaleOffsetY != 0)) {
			model.translate(0, femaleOffsetY, 0);
		}

		if (srcColor != null) {
			for (int i1 = 0; i1 < srcColor.length; i1++) {
				model.recolor(srcColor[i1], dstColor[i1]);
			}
		}

		return model;
	}

	public void reset() {
		modelId = 0;
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
		unusedInt = -1;
		stackable = false;
		cost = 1;
		members = false;
		groundOptions = null;
		inventoryOptions = null;
		maleModelId0 = -1;
		maleModelId1 = -1;
		maleOffsetY = 0;
		femaleModelId0 = -1;
		femaleModelId1 = -1;
		femaleOffsetY = 0;
		maleModelId2 = -1;
		femaleModelId2 = -1;
		maleHeadModelId0 = -1;
		maleHeadModelId1 = -1;
		femaleHeadModel0 = -1;
		femaleHeadModel1 = -1;
		stackId = null;
		stackAmount = null;
		linkedId = -1;
		certificateId = -1;
		scaleX = 128;
		scaleZ = 128;
		scaleY = 128;
		lightAmbient = 0;
		lightAttenuation = 0;
		team = 0;
	}

	public void toCertificate() {
		ObjType cert = get(certificateId);
		modelId = cert.modelId;
		iconZoom = cert.iconZoom;
		iconPitch = cert.iconPitch;
		iconYaw = cert.iconYaw;
		iconRoll = cert.iconRoll;
		iconOffsetX = cert.iconOffsetX;
		iconOffsetY = cert.iconOffsetY;
		srcColor = cert.srcColor;
		dstColor = cert.dstColor;
		ObjType linked = get(linkedId);
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

	public Model getModel(int amount) {
		if ((stackId != null) && (amount > 1)) {
			int newId = -1;
			for (int k = 0; k < 10; k++) {
				if ((amount >= stackAmount[k]) && (stackAmount[k] != 0)) {
					newId = stackId[k];
				}
			}
			if (newId != -1) {
				return get(newId).getModel(1);
			}
		}

		Model model = modelCache.get(id);

		if (model != null) {
			return model;
		}

		model = Model.tryGet(modelId);

		if (model == null) {
			return null;
		}

		if ((scaleX != 128) || (scaleZ != 128) || (scaleY != 128)) {
			model.scale(scaleX, scaleY, scaleZ);
		}

		if (srcColor != null) {
			for (int l = 0; l < srcColor.length; l++) {
				model.recolor(srcColor[l], dstColor[l]);
			}
		}

		model.calculateNormals(64 + lightAmbient, 768 + lightAttenuation, -50, -10, -50, true);
		model.pickable = true;
		modelCache.put(id, model);
		return model;
	}

	public Model getModelUnlit(int amount) {
		if ((stackId != null) && (amount > 1)) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if ((amount >= stackAmount[k]) && (stackAmount[k] != 0)) {
					j = stackId[k];
				}
			}
			if (j != -1) {
				return get(j).getModelUnlit(1);
			}
		}
		Model model = Model.tryGet(modelId);
		if (model == null) {
			return null;
		}
		if (srcColor != null) {
			for (int l = 0; l < srcColor.length; l++) {
				model.recolor(srcColor[l], dstColor[l]);
			}
		}
		return model;
	}

	public void read(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelId = buffer.get2U();
			} else if (i == 2) {
				name = buffer.getString();
			} else if (i == 3) {
				examine = buffer.getStringRaw();
			} else if (i == 4) {
				iconZoom = buffer.get2U();
			} else if (i == 5) {
				iconPitch = buffer.get2U();
			} else if (i == 6) {
				iconYaw = buffer.get2U();
			} else if (i == 7) {
				iconOffsetX = buffer.get2U();
				if (iconOffsetX > 32767) {
					iconOffsetX -= 0x10000;
				}
			} else if (i == 8) {
				iconOffsetY = buffer.get2U();
				if (iconOffsetY > 32767) {
					iconOffsetY -= 0x10000;
				}
			} else if (i == 10) {
				unusedInt = buffer.get2U();
			} else if (i == 11) {
				stackable = true;
			} else if (i == 12) {
				cost = buffer.get4();
			} else if (i == 16) {
				members = true;
			} else if (i == 23) {
				maleModelId0 = buffer.get2U();
				maleOffsetY = buffer.get1();
			} else if (i == 24) {
				maleModelId1 = buffer.get2U();
			} else if (i == 25) {
				femaleModelId0 = buffer.get2U();
				femaleOffsetY = buffer.get1();
			} else if (i == 26) {
				femaleModelId1 = buffer.get2U();
			} else if ((i >= 30) && (i < 35)) {
				if (groundOptions == null) {
					groundOptions = new String[5];
				}
				groundOptions[i - 30] = buffer.getString();
				if (groundOptions[i - 30].equalsIgnoreCase("hidden")) {
					groundOptions[i - 30] = null;
				}
			} else if ((i >= 35) && (i < 40)) {
				if (inventoryOptions == null) {
					inventoryOptions = new String[5];
				}
				inventoryOptions[i - 35] = buffer.getString();
			} else if (i == 40) {
				int j = buffer.get1U();
				srcColor = new int[j];
				dstColor = new int[j];
				for (int k = 0; k < j; k++) {
					srcColor[k] = buffer.get2U();
					dstColor[k] = buffer.get2U();
				}
			} else if (i == 78) {
				maleModelId2 = buffer.get2U();
			} else if (i == 79) {
				femaleModelId2 = buffer.get2U();
			} else if (i == 90) {
				maleHeadModelId0 = buffer.get2U();
			} else if (i == 91) {
				femaleHeadModel0 = buffer.get2U();
			} else if (i == 92) {
				maleHeadModelId1 = buffer.get2U();
			} else if (i == 93) {
				femaleHeadModel1 = buffer.get2U();
			} else if (i == 95) {
				iconRoll = buffer.get2U();
			} else if (i == 97) {
				linkedId = buffer.get2U();
			} else if (i == 98) {
				certificateId = buffer.get2U();
			} else if ((i >= 100) && (i < 110)) {
				if (stackId == null) {
					stackId = new int[10];
					stackAmount = new int[10];
				}
				stackId[i - 100] = buffer.get2U();
				stackAmount[i - 100] = buffer.get2U();
			} else if (i == 110) {
				scaleX = buffer.get2U();
			} else if (i == 111) {
				scaleZ = buffer.get2U();
			} else if (i == 112) {
				scaleY = buffer.get2U();
			} else if (i == 113) {
				lightAmbient = buffer.get1();
			} else if (i == 114) {
				lightAttenuation = buffer.get1() * 5;
			} else if (i == 115) {
				team = buffer.get1U();
			}
		} while (true);
	}

}
