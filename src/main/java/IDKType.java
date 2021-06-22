// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.io.IOException;

public class IDKType {

	public static int anInt655;
	public static IDKType[] instances;

	public static void unpack(FileArchive archive) throws IOException {
		Buffer buffer = new Buffer(archive.read("idk.dat"));
		anInt655 = buffer.get2U();
		if (instances == null) {
			instances = new IDKType[anInt655];
		}
		for (int j = 0; j < anInt655; j++) {
			if (instances[j] == null) {
				instances[j] = new IDKType();
			}
			instances[j].method536(buffer);
		}
	}

	public final int[] anIntArray659 = new int[6];
	public final int[] anIntArray660 = new int[6];
	public final int[] anIntArray661 = {-1, -1, -1, -1, -1};
	public int anInt657 = -1;
	public int[] anIntArray658;
	public boolean aBoolean662 = false;

	public IDKType() {
	}

	public void method536(Buffer buffer) {
		do {
			int i = buffer.get1U();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt657 = buffer.get1U();
			} else if (i == 2) {
				int j = buffer.get1U();
				anIntArray658 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray658[k] = buffer.get2U();
				}
			} else if (i == 3) {
				aBoolean662 = true;
			} else if ((i >= 40) && (i < 50)) {
				anIntArray659[i - 40] = buffer.get2U();
			} else if ((i >= 50) && (i < 60)) {
				anIntArray660[i - 50] = buffer.get2U();
			} else if ((i >= 60) && (i < 70)) {
				anIntArray661[i - 60] = buffer.get2U();
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	public boolean method537() {
		if (anIntArray658 == null) {
			return true;
		}
		boolean flag = true;
		for (int i : anIntArray658) {
			if (!Model.validate(i)) {
				flag = false;
			}
		}
		return flag;
	}

	public Model method538() {
		if (anIntArray658 == null) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6 = new Model[anIntArray658.length];
		for (int i = 0; i < anIntArray658.length; i++) {
			aclass30_sub2_sub4_sub6[i] = Model.tryGet(anIntArray658[i]);
		}
		Model model;
		if (aclass30_sub2_sub4_sub6.length == 1) {
			model = aclass30_sub2_sub4_sub6[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6.length, aclass30_sub2_sub4_sub6);
		}
		for (int j = 0; j < 6; j++) {
			if (anIntArray659[j] == 0) {
				break;
			}
			model.recolor(anIntArray659[j], anIntArray660[j]);
		}
		return model;
	}

	public boolean method539() {
		boolean flag1 = true;
		for (int i = 0; i < 5; i++) {
			if ((anIntArray661[i] != -1) && !Model.validate(anIntArray661[i])) {
				flag1 = false;
			}
		}
		return flag1;
	}

	public Model method540() {
		Model[] aclass30_sub2_sub4_sub6 = new Model[5];
		int j = 0;
		for (int k = 0; k < 5; k++) {
			if (anIntArray661[k] != -1) {
				aclass30_sub2_sub4_sub6[j++] = Model.tryGet(anIntArray661[k]);
			}
		}
		Model model = new Model(j, aclass30_sub2_sub4_sub6);
		for (int l = 0; l < 6; l++) {
			if (anIntArray659[l] == 0) {
				break;
			}
			model.recolor(anIntArray659[l], anIntArray660[l]);
		}
		return model;
	}

}
