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
	public static Buffer buf;
	public static int[] typeOffset;
	public static int anInt203;

	public static void unload() {
		modelCache = null;
		iconCache = null;
		typeOffset = null;
		cached = null;
		buf = null;
	}

	public static void unpack(FileArchive archive) throws IOException {
		buf = new Buffer(archive.read("obj.dat"));
		Buffer buffer = new Buffer(archive.read("obj.idx"));
		anInt203 = buffer.get2U();
		typeOffset = new int[anInt203];
		int i = 2;
		for (int j = 0; j < anInt203; j++) {
			typeOffset[j] = i;
			i += buffer.get2U();
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
		buf.position = typeOffset[id];
		type.id = id;
		type.reset();
		type.read(buf);
		if (type.linkedId != -1) {
			type.link();
		}
		if (!Game.members && type.members) {
			type.name = "Members Object";
			type.description = "Login to a members' server to use this object.".getBytes();
			type.aStringArray168 = null;
			type.aStringArray189 = null;
			type.anInt202 = 0;
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
		Image24 linkedIcon = null;

		if (type.linkedId != -1) {
			linkedIcon = getIcon(type.anInt179, 10, -1);

			if (linkedIcon == null) {
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

		if (type.linkedId != -1) {
			int w = linkedIcon.cropW;
			int h = linkedIcon.cropH;
			linkedIcon.cropW = 32;
			linkedIcon.cropH = 32;
			linkedIcon.draw(0, 0);
			linkedIcon.cropW = w;
			linkedIcon.cropH = h;
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

		if (type.aBoolean176) {
			icon.cropW = 33;
		} else {
			icon.cropW = 32;
		}

		icon.cropH = amount;
		return icon;
	}

	public byte aByte154;
	public int anInt155;
	public int[] srcColor;
	public int id = -1;
	public int[] dstColor;
	public boolean members;
	public int anInt162;
	public int linkedId;
	public int anInt164;
	public int anInt165;
	public int anInt166;
	public int scaleX;
	public String[] aStringArray168;
	public int iconOffsetX;
	public String name;
	public int anInt173;
	public int anInt174;
	public int anInt175;
	public boolean aBoolean176;
	public byte[] description;
	public int anInt179;
	public int iconZoom;
	public int anInt184;
	public int anInt185;
	public int anInt188;
	public String[] aStringArray189;
	public int iconPitch;
	public int scaleY;
	public int scaleZ;
	public int[] stackId;
	public int iconOffsetY;
	public int anInt196;
	public int anInt197;
	public int iconYaw;
	public int unusedInt;
	public int anInt200;
	public int[] stackAmount;
	public int anInt202;
	public int iconRoll;
	public byte aByte205;

	public ObjType() {
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.validate(k)) {
			flag = false;
		}
		if ((l != -1) && !Model.validate(l)) {
			flag = false;
		}
		return flag;
	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1) {
			return null;
		}
		Model model = Model.tryGet(k);
		if (l != -1) {
			Model model_1 = Model.tryGet(l);
			Model[] aclass30_sub2_sub4_sub6 = {model, model_1};
			model = new Model(2, aclass30_sub2_sub4_sub6);
		}
		if (srcColor != null) {
			for (int i1 = 0; i1 < srcColor.length; i1++) {
				model.recolor(srcColor[i1], dstColor[i1]);
			}
		}
		return model;
	}

	public boolean method195(int j) {
		int k = anInt165;
		int l = anInt188;
		int i1 = anInt185;
		if (j == 1) {
			k = anInt200;
			l = anInt164;
			i1 = anInt162;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.validate(k)) {
			flag = false;
		}
		if ((l != -1) && !Model.validate(l)) {
			flag = false;
		}
		if ((i1 != -1) && !Model.validate(i1)) {
			flag = false;
		}
		return flag;
	}

	public Model method196(int i) {
		int j = anInt165;
		int k = anInt188;
		int l = anInt185;
		if (i == 1) {
			j = anInt200;
			k = anInt164;
			l = anInt162;
		}
		if (j == -1) {
			return null;
		}
		Model model = Model.tryGet(j);
		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.tryGet(k);
				Model model_3 = Model.tryGet(l);
				Model[] aclass30_sub2_sub4_sub6_1 = {model, model_1, model_3};
				model = new Model(3, aclass30_sub2_sub4_sub6_1);
			} else {
				Model class30_sub2_sub4_sub6_2 = Model.tryGet(k);
				Model[] aclass30_sub2_sub4_sub6 = {model, class30_sub2_sub4_sub6_2};
				model = new Model(2, aclass30_sub2_sub4_sub6);
			}
		}
		if ((i == 0) && (aByte205 != 0)) {
			model.translate(0, aByte205, 0);
		}
		if ((i == 1) && (aByte154 != 0)) {
			model.translate(0, aByte154, 0);
		}
		if (srcColor != null) {
			for (int i1 = 0; i1 < srcColor.length; i1++) {
				model.recolor(srcColor[i1], dstColor[i1]);
			}
		}
		return model;
	}

	public void reset() {
		anInt174 = 0;
		name = null;
		description = null;
		srcColor = null;
		dstColor = null;
		iconZoom = 2000;
		iconPitch = 0;
		iconYaw = 0;
		iconRoll = 0;
		iconOffsetX = 0;
		iconOffsetY = 0;
		unusedInt = -1;
		aBoolean176 = false;
		anInt155 = 1;
		members = false;
		aStringArray168 = null;
		aStringArray189 = null;
		anInt165 = -1;
		anInt188 = -1;
		aByte205 = 0;
		anInt200 = -1;
		anInt164 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackId = null;
		stackAmount = null;
		anInt179 = -1;
		linkedId = -1;
		scaleX = 128;
		scaleZ = 128;
		scaleY = 128;
		anInt196 = 0;
		anInt184 = 0;
		anInt202 = 0;
	}

	public void link() {
		ObjType type = get(linkedId);
		anInt174 = type.anInt174;
		iconZoom = type.iconZoom;
		iconPitch = type.iconPitch;
		iconYaw = type.iconYaw;
		iconRoll = type.iconRoll;
		iconOffsetX = type.iconOffsetX;
		iconOffsetY = type.iconOffsetY;
		srcColor = type.srcColor;
		dstColor = type.dstColor;
		ObjType type_1 = get(anInt179);
		name = type_1.name;
		members = type_1.members;
		anInt155 = type_1.anInt155;
		String s = "a";
		char c = type_1.name.charAt(0);
		if ((c == 'A') || (c == 'E') || (c == 'I') || (c == 'O') || (c == 'U')) {
			s = "an";
		}
		description = ("Swap this note at any bank for " + s + " " + type_1.name + ".").getBytes();
		aBoolean176 = true;
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

		model = Model.tryGet(anInt174);

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

		model.calculateNormals(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.pickBounds = true;
		modelCache.put(id, model);
		return model;
	}

	public Model method202(int i) {
		if ((stackId != null) && (i > 1)) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if ((i >= stackAmount[k]) && (stackAmount[k] != 0)) {
					j = stackId[k];
				}
			}
			if (j != -1) {
				return get(j).method202(1);
			}
		}
		Model model = Model.tryGet(anInt174);
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
				anInt174 = buffer.get2U();
			} else if (i == 2) {
				name = buffer.getString();
			} else if (i == 3) {
				description = buffer.getStringRaw();
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
				aBoolean176 = true;
			} else if (i == 12) {
				anInt155 = buffer.get4();
			} else if (i == 16) {
				members = true;
			} else if (i == 23) {
				anInt165 = buffer.get2U();
				aByte205 = buffer.get1();
			} else if (i == 24) {
				anInt188 = buffer.get2U();
			} else if (i == 25) {
				anInt200 = buffer.get2U();
				aByte154 = buffer.get1();
			} else if (i == 26) {
				anInt164 = buffer.get2U();
			} else if ((i >= 30) && (i < 35)) {
				if (aStringArray168 == null) {
					aStringArray168 = new String[5];
				}
				aStringArray168[i - 30] = buffer.getString();
				if (aStringArray168[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray168[i - 30] = null;
				}
			} else if ((i >= 35) && (i < 40)) {
				if (aStringArray189 == null) {
					aStringArray189 = new String[5];
				}
				aStringArray189[i - 35] = buffer.getString();
			} else if (i == 40) {
				int j = buffer.get1U();
				srcColor = new int[j];
				dstColor = new int[j];
				for (int k = 0; k < j; k++) {
					srcColor[k] = buffer.get2U();
					dstColor[k] = buffer.get2U();
				}
			} else if (i == 78) {
				anInt185 = buffer.get2U();
			} else if (i == 79) {
				anInt162 = buffer.get2U();
			} else if (i == 90) {
				anInt175 = buffer.get2U();
			} else if (i == 91) {
				anInt197 = buffer.get2U();
			} else if (i == 92) {
				anInt166 = buffer.get2U();
			} else if (i == 93) {
				anInt173 = buffer.get2U();
			} else if (i == 95) {
				iconRoll = buffer.get2U();
			} else if (i == 97) {
				anInt179 = buffer.get2U();
			} else if (i == 98) {
				linkedId = buffer.get2U();
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
				anInt196 = buffer.get1();
			} else if (i == 114) {
				anInt184 = buffer.get1() * 5;
			} else if (i == 115) {
				anInt202 = buffer.get1U();
			}
		} while (true);
	}

}
