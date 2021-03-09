// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.AppletContext;
import java.awt.*;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.CRC32;

public class Game extends GameShell {

	public static final int[][] anIntArrayArray1003 = {{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193}, {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239}, {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};
	public static final int[] anIntArray1204 = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486};
	public static final BigInteger aBigInteger856 = new BigInteger("7162900525229798032761816791230527296329313291232324290237849263501208207972894053929065636522363163621000728841182238772712427862772219676577293600221789");
	public static int anInt957 = 10;
	public static int anInt958;
	public static boolean aBoolean959 = true;
	public static boolean aBoolean960;
	public static boolean aBoolean993;
	public static final int[] anIntArray1019;
	public static final BigInteger aBigInteger1032 = new BigInteger("58778699976184461502525193738213253649000149147835990136706041084440742975821");
	public static int anInt1061;
	public static PlayerEntity aPlayer_1126;
	public static boolean aBoolean1156;
	public static int anInt1161;
	public static final String aString1162 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	public static boolean aBoolean1205;
	public static final int[] anIntArray1232;

	static {
		anIntArray1019 = new int[99];
		int i = 0;
		for (int j = 0; j < 99; j++) {
			int l = j + 1;
			int i1 = (int) ((double) l + (300D * Math.pow(2D, (double) l / 7D)));
			i += i1;
			anIntArray1019[j] = i / 4;
		}
		anIntArray1232 = new int[32];
		i = 2;
		for (int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}
	}

	public final int[] anIntArray1177 = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
	public int anInt822;
	public long aLong824;
	public int[][] anIntArrayArray825 = new int[104][104];
	public int[] anIntArray826 = new int[200];
	public LinkedList[][][] aListArrayArrayArray827 = new LinkedList[4][104][104];
	public int[] anIntArray828;
	public int[] anIntArray829;
	public volatile boolean aBoolean831 = false;
	public Socket aSocket832;
	public int anInt833;
	public Buffer aBuffer_834 = new Buffer(new byte[5000]);
	public NPCEntity[] aNpcArray835 = new NPCEntity[16384];
	public int anInt836;
	public int[] anIntArray837 = new int[16384];
	public int anInt839;
	public int[] anIntArray840 = new int[1000];
	public int anInt841;
	public int anInt842;
	public int anInt843;
	public String aString844;
	public int anInt845;
	public Buffer aBuffer_847 = Buffer.create(1);
	public boolean aBoolean848 = true;
	public int[] anIntArray850;
	public int[] anIntArray851;
	public int[] anIntArray852;
	public int[] anIntArray853;
	public int anInt855;
	public int anInt857 = -1;
	public int anInt858;
	public int anInt859;
	public int anInt860;
	public int anInt861;
	public int anInt862;
	public int anInt863;
	public final int[] anIntArray864 = new int[SkillConstants.anInt733];
	public Image8 aImage_865;
	public Image8 aImage_866;
	public Image8 aImage_867;
	public Image8 aImage_868;
	public Image8 aImage_869;
	public Image24 aImage_870;
	public Image24 aImage_871;
	public boolean aBoolean872 = false;
	public final int[] anIntArray873 = new int[5];
	public int anInt874 = -1;
	public final boolean[] aBooleanArray876 = new boolean[5];
	public int anInt878;
	public MouseRecorder aMouseRecorder_879;
	public volatile boolean aBoolean880 = false;
	public String aString881 = "";
	public int anInt884 = -1;
	public boolean aBoolean885 = false;
	public int anInt886;
	public String aString887 = "";
	public final int anInt888 = 2048;
	public final int anInt889 = 2047;
	public PlayerEntity[] aPlayerArray890 = new PlayerEntity[anInt888];
	public int anInt891;
	public int[] anIntArray892 = new int[anInt888];
	public int anInt893;
	public int[] anIntArray894 = new int[anInt888];
	public Buffer[] aBufferArray895 = new Buffer[anInt888];
	public int anInt896;
	public int anInt899;
	public int anInt900;
	public int[][] anIntArrayArray901 = new int[104][104];
	public final int anInt902 = 0x766654;
	public DrawArea aArea_903;
	public DrawArea aArea_904;
	public DrawArea aArea_905;
	public DrawArea aArea_906;
	public DrawArea aArea_907;
	public DrawArea aArea_908;
	public DrawArea aArea_909;
	public DrawArea aArea_910;
	public DrawArea aArea_911;
	public byte[] aByteArray912 = new byte[16384];
	public int anInt913;
	public int anInt914;
	public int anInt915;
	public int anInt916;
	public int anInt917;
	public int anInt918;
	public final int[] anIntArray922 = new int[SkillConstants.anInt733];
	public final long[] aLongArray925 = new long[100];
	public boolean aBoolean926 = false;
	public final int anInt927 = 0x332d25;
	public final int[] anIntArray928 = new int[5];
	public int[][] anIntArrayArray929 = new int[104][104];
	public final CRC32 aCRC32_930 = new CRC32();
	public Image24 aImage_931;
	public Image24 aImage_932;
	public int anInt933;
	public int anInt934;
	public int anInt935;
	public int anInt936;
	public int anInt937;
	public int anInt938;
	public final int[] anIntArray942 = new int[100];
	public final String[] aStringArray943 = new String[100];
	public final String[] aStringArray944 = new String[100];
	public int anInt945;
	public SceneGraph aGraph_946;
	public Image8[] aImageArray947 = new Image8[13];
	public int anInt948;
	public int anInt949;
	public int anInt950;
	public int anInt951;
	public int anInt952;
	public long aLong953;
	public boolean aBoolean954 = true;
	public long[] aLongArray955 = new long[200];
	public int anInt956 = -1;
	public volatile boolean aBoolean962 = false;
	public int anInt963 = -1;
	public int anInt964 = -1;
	public final int[] anIntArray965 = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
	public Image8 aImage_966;
	public Image8 aImage_967;
	public final int[] anIntArray968 = new int[33];
	public final int[] anIntArray969 = new int[256];
	public final FileStore[] aFileStoreArray970 = new FileStore[5];
	public int[] anIntArray971 = new int[2000];
	public boolean aBoolean972 = false;
	public int anInt974;
	public final int anInt975 = 50;
	public final int[] anIntArray976 = new int[anInt975];
	public final int[] anIntArray977 = new int[anInt975];
	public final int[] anIntArray978 = new int[anInt975];
	public final int[] anIntArray979 = new int[anInt975];
	public final int[] anIntArray980 = new int[anInt975];
	public final int[] anIntArray981 = new int[anInt975];
	public final int[] anIntArray982 = new int[anInt975];
	public final String[] aStringArray983 = new String[anInt975];
	public int anInt984;
	public int anInt985 = -1;
	public Image24[] aImageArray987 = new Image24[20];
	public int anInt989;
	public final int[] anIntArray990 = new int[5];
	public int anInt992;
	public int anInt995;
	public int anInt996;
	public int anInt997;
	public int anInt998;
	public int anInt999;
	public ISAACCipher aISAACCipher_1000;
	public Image24 aImage_1001;
	public final int anInt1002 = 0x23201b;
	public String aString1004 = "";
	public int anInt1006;
	public int anInt1007;
	public int anInt1008;
	public int anInt1009;
	public int anInt1010;
	public int anInt1011;
	public LinkedList aList_1013 = new LinkedList();
	public int anInt1014;
	public int anInt1015;
	public int anInt1016;
	public boolean aBoolean1017 = false;
	public int anInt1018 = -1;
	public int anInt1021;
	public int anInt1022;
	public int anInt1023;
	public Image8 aImage_1024;
	public Image8 aImage_1025;
	public int anInt1026;
	public Image8 aImage_1027;
	public Image8 aImage_1028;
	public Image8 aImage_1029;
	public final int[] anIntArray1030 = new int[5];
	public boolean aBoolean1031 = false;
	public Image24[] aImageArray1033 = new Image24[100];
	public int anInt1034;
	public int anInt1035;
	public int anInt1036;
	public int anInt1037;
	public int anInt1038;
	public int anInt1039;
	public int anInt1040;
	public int anInt1041;
	public int anInt1042 = -1;
	public final int[] anIntArray1044 = new int[SkillConstants.anInt733];
	public final int[] anIntArray1045 = new int[2000];
	public int anInt1046;
	public boolean aBoolean1047 = true;
	public int anInt1048;
	public String aString1049;
	public final int[] anIntArray1052 = new int[151];
	public FileArchive aArchive_1053;
	public int anInt1054 = -1;
	public int anInt1055;
	public LinkedList aList_1056 = new LinkedList();
	public final int[] anIntArray1057 = new int[33];
	public final Component aComponent_1059 = new Component();
	public Image8[] aImageArray1060 = new Image8[100];
	public int anInt1062;
	public final int anInt1063 = 0x4d4233;
	public int anInt1064;
	public final int[] anIntArray1065 = new int[7];
	public int anInt1066;
	public int anInt1067;
	public OnDemand aOnDemand_1068;
	public int anInt1069;
	public int anInt1070;
	public int anInt1071;
	public int[] anIntArray1072 = new int[1000];
	public int[] anIntArray1073 = new int[1000];
	public Image24 aImage_1074;
	public Image24 aImage_1075;
	public Image24 aImage_1076;
	public Image24 aImage_1077;
	public Image24 aImage_1078;
	public int anInt1079;
	public boolean aBoolean1080 = false;
	public String[] aStringArray1082 = new String[200];
	public Buffer aBuffer_1083 = Buffer.create(1);
	public int anInt1084;
	public int anInt1085;
	public int anInt1086;
	public int anInt1087;
	public int anInt1088;
	public int anInt1089;
	public final int[] anIntArray1090 = new int[9];
	public int[] anIntArray1091 = new int[500];
	public int[] anIntArray1092 = new int[500];
	public int[] anIntArray1093 = new int[500];
	public int[] anIntArray1094 = new int[500];
	public Image24[] aImageArray1095 = new Image24[20];
	public int anInt1098;
	public int anInt1099;
	public int anInt1100;
	public int anInt1101;
	public int anInt1102;
	public boolean aBoolean1103 = false;
	public int anInt1104;
	public DrawArea aArea_1107;
	public DrawArea aArea_1108;
	public DrawArea aArea_1109;
	public DrawArea aArea_1110;
	public DrawArea aArea_1111;
	public DrawArea aArea_1112;
	public DrawArea aArea_1113;
	public DrawArea aArea_1114;
	public DrawArea aArea_1115;
	public int anInt1120;
	public String aString1121 = "";
	public Image24 aImage_1122;
	public DrawArea aArea_1123;
	public DrawArea aArea_1124;
	public DrawArea aArea_1125;
	public final String[] aStringArray1127 = new String[5];
	public final boolean[] aBooleanArray1128 = new boolean[5];
	public final int[][][] anIntArrayArrayArray1129 = new int[4][13][13];
	public final int[] anIntArray1130 = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	public int anInt1131;
	public int anInt1133;
	public int anInt1136;
	public int anInt1137;
	public int anInt1138;
	public String aString1139;
	public Image24[] aImageArray1140 = new Image24[1000];
	public boolean aBoolean1141 = false;
	public Image8 aImage_1143;
	public Image8 aImage_1144;
	public Image8 aImage_1145;
	public Image8 aImage_1146;
	public Image8 aImage_1147;
	public int anInt1148;
	public boolean aBoolean1149 = false;
	public Image24[] aImageArray1150 = new Image24[8];
	public boolean aBoolean1151 = true;
	public Image8[] aImageArray1152;
	public boolean aBoolean1153 = false;
	public int anInt1154;
	public boolean aBoolean1157 = false;
	public boolean aBoolean1158 = false;
	public boolean aBoolean1159 = false;
	public boolean aBoolean1160 = false;
	public DrawArea aArea_1163;
	public DrawArea aArea_1164;
	public DrawArea aArea_1165;
	public DrawArea aArea_1166;
	public int anInt1167;
	public Connection aConnection_1168;
	public int anInt1169;
	public int anInt1170;
	public long aLong1172;
	public String aString1173 = "";
	public String aString1174 = "";
	public boolean aBoolean1176 = false;
	public int anInt1178 = -1;
	public LinkedList aList_1179 = new LinkedList();
	public int[] anIntArray1180;
	public int[] anIntArray1181;
	public int[] anIntArray1182;
	public byte[][] aByteArrayArray1183;
	public int anInt1184 = 128;
	public int anInt1185;
	public int anInt1186;
	public int anInt1187;
	public int anInt1189 = -1;
	public int[] anIntArray1190;
	public int[] anIntArray1191;
	public Buffer aBuffer_1192 = Buffer.create(1);
	public int anInt1193;
	public int anInt1195;
	public Image8 aImage_1196;
	public Image8 aImage_1197;
	public Image8 aImage_1198;
	public String[] aStringArray1199 = new String[500];
	public Image24 aImage_1201;
	public Image24 aImage_1202;
	public final int[] anIntArray1203 = new int[5];
	public final int[] anIntArray1207 = new int[50];
	public int anInt1208;
	public int anInt1209;
	public int anInt1211 = 78;
	public String aString1212 = "";
	public int anInt1213;
	public int[][][] anIntArrayArrayArray1214;
	public long aLong1215;
	public int anInt1216;
	public final Image8[] aImageArray1219 = new Image8[2];
	public long aLong1220;
	public int anInt1221 = 3;
	public int anInt1222;
	public boolean aBoolean1223 = false;
	public int anInt1225;
	public int anInt1227;
	public boolean aBoolean1228 = true;
	public final int[] anIntArray1229 = new int[151];
	public SceneCollisionMap[] aCollisionMapArray1230 = new SceneCollisionMap[4];
	public boolean aBoolean1233 = false;
	public int[] anIntArray1234;
	public int[] anIntArray1235;
	public int[] anIntArray1236;
	public int anInt1237;
	public int anInt1238;
	public final int[] anIntArray1240 = new int[100];
	public final int[] anIntArray1241 = new int[50];
	public boolean aBoolean1242 = false;
	public int anInt1243;
	public int anInt1244;
	public int anInt1245;
	public int anInt1246;
	public byte[][] aByteArrayArray1247;
	public int anInt1248;
	public int anInt1249;
	public final int[] anIntArray1250 = new int[50];
	public int anInt1251;
	public boolean aBoolean1252 = false;
	public int anInt1253;
	public boolean aBoolean1255 = false;
	public boolean aBoolean1256 = false;
	public int anInt1257;
	public byte[][][] aByteArrayArrayArray1258;
	public int anInt1259;
	public int anInt1261;
	public int anInt1262;
	public Image24 aImage_1263;
	public int anInt1264;
	public int anInt1265;
	public String aString1266 = "";
	public String aString1267 = "";
	public int anInt1268;
	public int anInt1269;
	public BitmapFont aFont_1270;
	public BitmapFont aFont_1271;
	public BitmapFont aFont_1272;
	public BitmapFont aFont_1273;
	public int anInt1275;
	public int anInt1276 = -1;
	public int anInt1278;
	public int[] anIntArray1280 = new int[4000];
	public int[] anIntArray1281 = new int[4000];
	public int anInt1282;
	public int anInt1283;
	public int anInt1284;
	public int anInt1285;
	public String aString1286;
	public int anInt1287;
	public int anInt1289 = -1;

	public Game() {
	}

	@Override
	public void init() {
		anInt957 = Integer.parseInt(getParameter("nodeid"));
		anInt958 = Integer.parseInt(getParameter("portoff"));
		String s = getParameter("lowmem");
		if ((s != null) && s.equals("1")) {
			method138();
		} else {
			method52();
		}
		String s1 = getParameter("free");
		aBoolean959 = (s1 == null) || !s1.equals("1");
		method2(503, 765);
	}

	@Override
	public void run() {
		if (aBoolean880) {
			method136();
		} else {
			super.run();
		}
	}

	@Override
	public AppletContext getAppletContext() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getAppletContext();
		} else {
			return super.getAppletContext();
		}
	}

	@Override
	public URL getCodeBase() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getCodeBase();
		}
		try {
			if (super.aGameFrame__15 != null) {
				return new URL("http://127.0.0.1:" + (80 + anInt958));
			}
		} catch (Exception ignored) {
		}
		return super.getCodeBase();
	}

	@Override
	public String getParameter(String s) {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getParameter(s);
		} else {
			return super.getParameter(s);
		}
	}

	@Override
	public void method6() {
		method13(20, "Starting up");
		if (Signlink.sunjava) {
			super.anInt6 = 5;
		}
		if (aBoolean993) {
			aBoolean1252 = true;
			return;
		}
		aBoolean993 = true;
		boolean flag = false;
		String s = method80();
		if (s.endsWith("jagex.com")) {
			flag = true;
		}
		if (s.endsWith("runescape.com")) {
			flag = true;
		}
		if (s.endsWith("192.168.1.2")) {
			flag = true;
		}
		if (s.endsWith("192.168.1.229")) {
			flag = true;
		}
		if (s.endsWith("192.168.1.228")) {
			flag = true;
		}
		if (s.endsWith("192.168.1.227")) {
			flag = true;
		}
		if (s.endsWith("192.168.1.226")) {
			flag = true;
		}
		if (s.endsWith("127.0.0.1")) {
			flag = true;
		}
		if (!flag) {
			aBoolean1176 = true;
			return;
		}
		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++) {
				aFileStoreArray970[i] = new FileStore(0x7a120, Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
			}
		}
		try {
			method16();
			aArchive_1053 = method67(1, "title screen", "title", anIntArray1090[1], 25);
			aFont_1270 = new BitmapFont(false, "p11_full", aArchive_1053);
			aFont_1271 = new BitmapFont(false, "p12_full", aArchive_1053);
			aFont_1272 = new BitmapFont(false, "b12_full", aArchive_1053);
			aFont_1273 = new BitmapFont(true, "q8_full", aArchive_1053);
			method56();
			method51();
			FileArchive archive = method67(2, "config", "config", anIntArray1090[2], 30);
			FileArchive archive_1 = method67(3, "interface", "interface", anIntArray1090[3], 35);
			FileArchive archive_2 = method67(4, "2d graphics", "media", anIntArray1090[4], 40);
			FileArchive archive_3 = method67(6, "textures", "textures", anIntArray1090[6], 45);
			FileArchive archive_4 = method67(7, "chat system", "wordenc", anIntArray1090[7], 50);
			FileArchive archive_5 = method67(8, "sound effects", "sounds", anIntArray1090[8], 55);
			aByteArrayArrayArray1258 = new byte[4][104][104];
			anIntArrayArrayArray1214 = new int[4][105][105];
			aGraph_946 = new SceneGraph(104, 104, anIntArrayArrayArray1214, 4);
			for (int j = 0; j < 4; j++) {
				aCollisionMapArray1230[j] = new SceneCollisionMap(104, 104);
			}
			aImage_1263 = new Image24(512, 512);
			FileArchive archive_6 = method67(5, "update list", "versionlist", anIntArray1090[5], 60);
			method13(60, "Connecting to update server");
			aOnDemand_1068 = new OnDemand();
			aOnDemand_1068.method551(archive_6, this);
			SeqFrame.init(aOnDemand_1068.method557());
			Model.init(aOnDemand_1068.method555(0), aOnDemand_1068);
			if (!aBoolean960) {
				anInt1227 = 0;
				try {
					anInt1227 = Integer.parseInt(getParameter("music"));
				} catch (Exception ignored) {
				}
				aBoolean1228 = true;
				aOnDemand_1068.method558(2, anInt1227);
				while (aOnDemand_1068.method552() > 0) {
					method57();
					try {
						Thread.sleep(100L);
					} catch (Exception ignored) {
					}
					if (aOnDemand_1068.anInt1349 > 3) {
						method28("ondemand");
						return;
					}
				}
			}
			method13(65, "Requesting animations");
			int k = aOnDemand_1068.method555(1);
			for (int i1 = 0; i1 < k; i1++) {
				aOnDemand_1068.method558(1, i1);
			}
			while (aOnDemand_1068.method552() > 0) {
				int j1 = k - aOnDemand_1068.method552();
				if (j1 > 0) {
					method13(65, "Loading animations - " + (j1 * 100) / k + "%");
				}
				method57();
				try {
					Thread.sleep(100L);
				} catch (Exception ignored) {
				}
				if (aOnDemand_1068.anInt1349 > 3) {
					method28("ondemand");
					return;
				}
			}
			method13(70, "Requesting models");
			k = aOnDemand_1068.method555(0);
			for (int k1 = 0; k1 < k; k1++) {
				int l1 = aOnDemand_1068.method559(k1);
				if ((l1 & 1) != 0) {
					aOnDemand_1068.method558(0, k1);
				}
			}
			k = aOnDemand_1068.method552();
			while (aOnDemand_1068.method552() > 0) {
				int i2 = k - aOnDemand_1068.method552();
				if (i2 > 0) {
					method13(70, "Loading models - " + (i2 * 100) / k + "%");
				}
				method57();
				try {
					Thread.sleep(100L);
				} catch (Exception ignored) {
				}
			}
			if (aFileStoreArray970[0] != null) {
				method13(75, "Requesting maps");
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 48, 47));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 48, 47));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 48, 48));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 48, 48));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 48, 49));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 48, 49));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 47, 47));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 47, 47));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 47, 48));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 47, 48));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(0, 148, 48));
				aOnDemand_1068.method558(3, aOnDemand_1068.method562(1, 148, 48));
				k = aOnDemand_1068.method552();
				while (aOnDemand_1068.method552() > 0) {
					int j2 = k - aOnDemand_1068.method552();
					if (j2 > 0) {
						method13(75, "Loading maps - " + (j2 * 100) / k + "%");
					}
					method57();
					try {
						Thread.sleep(100L);
					} catch (Exception ignored) {
					}
				}
			}
			k = aOnDemand_1068.method555(0);
			for (int k2 = 0; k2 < k; k2++) {
				int l2 = aOnDemand_1068.method559(k2);
				byte byte0 = 0;
				if ((l2 & 8) != 0) {
					byte0 = 10;
				} else if ((l2 & 0x20) != 0) {
					byte0 = 9;
				} else if ((l2 & 0x10) != 0) {
					byte0 = 8;
				} else if ((l2 & 0x40) != 0) {
					byte0 = 7;
				} else if ((l2 & 0x80) != 0) {
					byte0 = 6;
				} else if ((l2 & 2) != 0) {
					byte0 = 5;
				} else if ((l2 & 4) != 0) {
					byte0 = 4;
				}
				if ((l2 & 1) != 0) {
					byte0 = 3;
				}
				if (byte0 != 0) {
					aOnDemand_1068.method563(byte0, 0, k2);
				}
			}
			aOnDemand_1068.method554(aBoolean959);
			if (!aBoolean960) {
				int l = aOnDemand_1068.method555(2);
				for (int i3 = 1; i3 < l; i3++) {
					if (aOnDemand_1068.method569(i3)) {
						aOnDemand_1068.method563((byte) 1, 2, i3);
					}
				}
			}
			method13(80, "Unpacking media");
			aImage_1196 = new Image8(archive_2, "invback", 0);
			aImage_1198 = new Image8(archive_2, "chatback", 0);
			aImage_1197 = new Image8(archive_2, "mapback", 0);
			aImage_1027 = new Image8(archive_2, "backbase1", 0);
			aImage_1028 = new Image8(archive_2, "backbase2", 0);
			aImage_1029 = new Image8(archive_2, "backhmid1", 0);
			for (int j3 = 0; j3 < 13; j3++) {
				aImageArray947[j3] = new Image8(archive_2, "sideicons", j3);
			}
			aImage_1122 = new Image24(archive_2, "compass", 0);
			aImage_1001 = new Image24(archive_2, "mapedge", 0);
			aImage_1001.method345();
			try {
				for (int k3 = 0; k3 < 100; k3++) {
					aImageArray1060[k3] = new Image8(archive_2, "mapscene", k3);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int l3 = 0; l3 < 100; l3++) {
					aImageArray1033[l3] = new Image24(archive_2, "mapfunction", l3);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int i4 = 0; i4 < 20; i4++) {
					aImageArray987[i4] = new Image24(archive_2, "hitmarks", i4);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int j4 = 0; j4 < 20; j4++) {
					aImageArray1095[j4] = new Image24(archive_2, "headicons", j4);
				}
			} catch (Exception ignored) {
			}
			aImage_870 = new Image24(archive_2, "mapmarker", 0);
			aImage_871 = new Image24(archive_2, "mapmarker", 1);
			for (int k4 = 0; k4 < 8; k4++) {
				aImageArray1150[k4] = new Image24(archive_2, "cross", k4);
			}
			aImage_1074 = new Image24(archive_2, "mapdots", 0);
			aImage_1075 = new Image24(archive_2, "mapdots", 1);
			aImage_1076 = new Image24(archive_2, "mapdots", 2);
			aImage_1077 = new Image24(archive_2, "mapdots", 3);
			aImage_1078 = new Image24(archive_2, "mapdots", 4);
			aImage_1024 = new Image8(archive_2, "scrollbar", 0);
			aImage_1025 = new Image8(archive_2, "scrollbar", 1);
			aImage_1143 = new Image8(archive_2, "redstone1", 0);
			aImage_1144 = new Image8(archive_2, "redstone2", 0);
			aImage_1145 = new Image8(archive_2, "redstone3", 0);
			aImage_1146 = new Image8(archive_2, "redstone1", 0);
			aImage_1146.method358();
			aImage_1147 = new Image8(archive_2, "redstone2", 0);
			aImage_1147.method358();
			aImage_865 = new Image8(archive_2, "redstone1", 0);
			aImage_865.method359();
			aImage_866 = new Image8(archive_2, "redstone2", 0);
			aImage_866.method359();
			aImage_867 = new Image8(archive_2, "redstone3", 0);
			aImage_867.method359();
			aImage_868 = new Image8(archive_2, "redstone1", 0);
			aImage_868.method358();
			aImage_868.method359();
			aImage_869 = new Image8(archive_2, "redstone2", 0);
			aImage_869.method358();
			aImage_869.method359();
			for (int l4 = 0; l4 < 2; l4++) {
				aImageArray1219[l4] = new Image8(archive_2, "mod_icons", l4);
			}
			Image24 image = new Image24(archive_2, "backleft1", 0);
			aArea_903 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backleft2", 0);
			aArea_904 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backright1", 0);
			aArea_905 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backright2", 0);
			aArea_906 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backtop1", 0);
			aArea_907 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backvmid1", 0);
			aArea_908 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backvmid2", 0);
			aArea_909 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backvmid3", 0);
			aArea_910 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			image = new Image24(archive_2, "backhmid2", 0);
			aArea_911 = new DrawArea(image.anInt1440, image.anInt1441, method11());
			image.method346(0, 0);
			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;
			for (int i6 = 0; i6 < 100; i6++) {
				if (aImageArray1033[i6] != null) {
					aImageArray1033[i6].method344(i5 + l5, j5 + l5, k5 + l5);
				}
				if (aImageArray1060[i6] != null) {
					aImageArray1060[i6].method360(i5 + l5, j5 + l5, k5 + l5);
				}
			}
			method13(83, "Unpacking textures");
			Draw3D.method368(archive_3);
			Draw3D.method372(0.80000000000000004D);
			Draw3D.method367(20);
			method13(86, "Unpacking config");
			SeqType.method257(archive);
			LocType.method576(archive);
			FloType.method260(archive);
			ObjType.method193(archive);
			NPCType.method162(archive);
			IDKType.method535(archive);
			SpotAnimType.method264(archive);
			VarpType.method546(archive);
			VarbitType.method533(archive);
			ObjType.aBoolean182 = aBoolean959;
			if (!aBoolean960) {
				method13(90, "Unpacking sounds");
				byte[] abyte0 = archive_5.read("sounds.dat", null);
				Buffer buffer = new Buffer(abyte0);
				SoundTrack.method240(buffer);
			}
			method13(95, "Unpacking interfaces");
			BitmapFont[] aclass30_sub2_sub1_sub4 = {aFont_1270, aFont_1271, aFont_1272, aFont_1273};
			Component.method205(archive_1, aclass30_sub2_sub1_sub4, archive_2);
			method13(100, "Preparing game engine");
			for (int j6 = 0; j6 < 33; j6++) {
				int k6 = 999;
				int i7 = 0;
				for (int k7 = 0; k7 < 34; k7++) {
					if (aImage_1197.aByteArray1450[k7 + (j6 * aImage_1197.anInt1452)] == 0) {
						if (k6 == 999) {
							k6 = k7;
						}
						continue;
					}
					if (k6 == 999) {
						continue;
					}
					i7 = k7;
					break;
				}
				anIntArray968[j6] = k6;
				anIntArray1057[j6] = i7 - k6;
			}
			for (int l6 = 5; l6 < 156; l6++) {
				int j7 = 999;
				int l7 = 0;
				for (int j8 = 25; j8 < 172; j8++) {
					if ((aImage_1197.aByteArray1450[j8 + (l6 * aImage_1197.anInt1452)] == 0) && ((j8 > 34) || (l6 > 34))) {
						if (j7 == 999) {
							j7 = j8;
						}
						continue;
					}
					if (j7 == 999) {
						continue;
					}
					l7 = j8;
					break;
				}
				anIntArray1052[l6 - 5] = j7 - 25;
				anIntArray1229[l6 - 5] = l7 - j7;
			}
			Draw3D.method365(479, 96);
			anIntArray1180 = Draw3D.anIntArray1472;
			Draw3D.method365(190, 261);
			anIntArray1181 = Draw3D.anIntArray1472;
			Draw3D.method365(512, 334);
			anIntArray1182 = Draw3D.anIntArray1472;
			int[] ai = new int[9];
			for (int i8 = 0; i8 < 9; i8++) {
				int k8 = 128 + (i8 * 32) + 15;
				int l8 = 600 + (k8 * 3);
				int i9 = Draw3D.sin[k8];
				ai[i8] = (l8 * i9) >> 16;
			}
			SceneGraph.method310(500, 800, 512, 334, ai);
			Censor.method487(archive_4);
			aMouseRecorder_879 = new MouseRecorder(this);
			method12(aMouseRecorder_879, 10);
			LocEntity.aGame1609 = this;
			LocType.aGame765 = this;
			NPCType.aGame82 = this;
			return;
		} catch (Exception exception) {
			Signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
		}
		aBoolean926 = true;
	}

	@Override
	public void method7() {
		if (aBoolean1252 || aBoolean926 || aBoolean1176) {
			return;
		}
		anInt1161++;
		if (!aBoolean1157) {
			method140();
		} else {
			method62();
		}
		method57();
	}

	@Override
	public void method8() {
		Signlink.reporterror = false;
		try {
			if (aConnection_1168 != null) {
				aConnection_1168.method267();
			}
		} catch (Exception ignored) {
		}
		aConnection_1168 = null;
		method15();
		if (aMouseRecorder_879 != null) {
			aMouseRecorder_879.aBoolean808 = false;
		}
		aMouseRecorder_879 = null;
		aOnDemand_1068.method553();
		aOnDemand_1068 = null;
		aBuffer_834 = null;
		aBuffer_1192 = null;
		aBuffer_847 = null;
		aBuffer_1083 = null;
		anIntArray1234 = null;
		aByteArrayArray1183 = null;
		aByteArrayArray1247 = null;
		anIntArray1235 = null;
		anIntArray1236 = null;
		anIntArrayArrayArray1214 = null;
		aByteArrayArrayArray1258 = null;
		aGraph_946 = null;
		aCollisionMapArray1230 = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		anIntArray1280 = null;
		anIntArray1281 = null;
		aByteArray912 = null;
		aArea_1163 = null;
		aArea_1164 = null;
		aArea_1165 = null;
		aArea_1166 = null;
		aArea_1123 = null;
		aArea_1124 = null;
		aArea_1125 = null;
		aArea_903 = null;
		aArea_904 = null;
		aArea_905 = null;
		aArea_906 = null;
		aArea_907 = null;
		aArea_908 = null;
		aArea_909 = null;
		aArea_910 = null;
		aArea_911 = null;
		aImage_1196 = null;
		aImage_1197 = null;
		aImage_1198 = null;
		aImage_1027 = null;
		aImage_1028 = null;
		aImage_1029 = null;
		aImageArray947 = null;
		aImage_1143 = null;
		aImage_1144 = null;
		aImage_1145 = null;
		aImage_1146 = null;
		aImage_1147 = null;
		aImage_865 = null;
		aImage_866 = null;
		aImage_867 = null;
		aImage_868 = null;
		aImage_869 = null;
		aImage_1122 = null;
		aImageArray987 = null;
		aImageArray1095 = null;
		aImageArray1150 = null;
		aImage_1074 = null;
		aImage_1075 = null;
		aImage_1076 = null;
		aImage_1077 = null;
		aImage_1078 = null;
		aImageArray1060 = null;
		aImageArray1033 = null;
		anIntArrayArray929 = null;
		aPlayerArray890 = null;
		anIntArray892 = null;
		anIntArray894 = null;
		aBufferArray895 = null;
		anIntArray840 = null;
		aNpcArray835 = null;
		anIntArray837 = null;
		aListArrayArrayArray827 = null;
		aList_1179 = null;
		aList_1013 = null;
		aList_1056 = null;
		anIntArray1091 = null;
		anIntArray1092 = null;
		anIntArray1093 = null;
		anIntArray1094 = null;
		aStringArray1199 = null;
		anIntArray971 = null;
		anIntArray1072 = null;
		anIntArray1073 = null;
		aImageArray1140 = null;
		aImage_1263 = null;
		aStringArray1082 = null;
		aLongArray955 = null;
		anIntArray826 = null;
		aArea_1110 = null;
		aArea_1111 = null;
		aArea_1107 = null;
		aArea_1108 = null;
		aArea_1109 = null;
		aArea_1112 = null;
		aArea_1113 = null;
		aArea_1114 = null;
		aArea_1115 = null;
		method118();
		LocType.method575();
		NPCType.method163();
		ObjType.method191();
		FloType.aTypeArray388 = null;
		IDKType.aIDKTypeArray656 = null;
		Component.aComponentArray210 = null;
		SeqType.aTypeArray351 = null;
		SpotAnimType.aTypeArray403 = null;
		SpotAnimType.aCache_415 = null;
		VarpType.aVarpArray701 = null;
		PlayerEntity.aCache_1704 = null;
		Draw3D.method363();
		SceneGraph.method273();
		Model.clear();
		SeqFrame.clear();
		System.gc();
	}

	@Override
	public void method9() {
		if (aBoolean1252 || aBoolean926 || aBoolean1176) {
			method94();
			return;
		}
		anInt1061++;
		if (!aBoolean1157) {
			method135(false);
		} else {
			method102();
		}
		anInt1213 = 0;
	}

	@Override
	public void method10() {
		aBoolean1255 = true;
	}

	@Override
	public java.awt.Component method11() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp;
		}
		if (super.aGameFrame__15 != null) {
			return super.aGameFrame__15;
		} else {
			return this;
		}
	}

	@Override
	public void method12(Runnable runnable, int i) {
		if (i > 10) {
			i = 10;
		}
		if (Signlink.mainapp != null) {
			Signlink.startthread(runnable, i);
		} else {
			super.method12(runnable, i);
		}
	}

	@Override
	public void method13(int i, String s) {
		anInt1079 = i;
		aString1049 = s;
		method64();
		if (aArchive_1053 == null) {
			super.method13(i, s);
			return;
		}
		aArea_1109.method237();
		char c = '\u0168';
		char c1 = '\310';
		byte byte1 = 20;
		aFont_1272.method381(0xffffff, "RuneScape is loading - please wait...", (c1 / 2) - 26 - byte1, c / 2);
		int j = (c1 / 2) - 18 - byte1;
		Draw2D.drawRect((c / 2) - 152, j, 304, 34, 0x8c1111);
		Draw2D.drawRect((c / 2) - 151, j + 1, 302, 32, 0);
		Draw2D.fillRect((c / 2) - 150, j + 2, i * 3, 30, 0x8c1111);
		Draw2D.fillRect(((c / 2) - 150) + (i * 3), j + 2, 300 - (i * 3), 30, 0);
		aFont_1272.method381(0xffffff, s, ((c1 / 2) + 5) - byte1, c / 2);
		aArea_1109.method238(171, super.aGraphics12, 202);
		if (aBoolean1255) {
			aBoolean1255 = false;
			if (!aBoolean831) {
				aArea_1110.method238(0, super.aGraphics12, 0);
				aArea_1111.method238(0, super.aGraphics12, 637);
			}
			aArea_1107.method238(0, super.aGraphics12, 128);
			aArea_1108.method238(371, super.aGraphics12, 202);
			aArea_1112.method238(265, super.aGraphics12, 0);
			aArea_1113.method238(265, super.aGraphics12, 562);
			aArea_1114.method238(171, super.aGraphics12, 128);
			aArea_1115.method238(171, super.aGraphics12, 562);
		}
	}

	public static String method14(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3) {
			s = s.substring(0, k) + "," + s.substring(k);
		}
		if (s.length() > 8) {
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
		} else if (s.length() > 4) {
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		}
		return " " + s;
	}

	public static String method43(int j) {
		if (j < 0x186a0) {
			return String.valueOf(j);
		}
		if (j < 0x989680) {
			return (j / 1000) + "K";
		} else {
			return (j / 0xf4240) + "M";
		}
	}

	public static void method52() {
		SceneGraph.aBoolean436 = false;
		Draw3D.aBoolean1461 = false;
		aBoolean960 = false;
		SceneBuilder.aBoolean151 = false;
		LocType.aBoolean752 = false;
	}

	public static void main(String[] args) {
		try {
			System.out.println("RS2 user client - release #" + 317);
			if (args.length != 5) {
				System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
				return;
			}
			anInt957 = Integer.parseInt(args[0]);
			anInt958 = Integer.parseInt(args[1]);
			if (args[2].equals("lowmem")) {
				method138();
			} else if (args[2].equals("highmem")) {
				method52();
			} else {
				System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
				return;
			}
			if (args[3].equals("free")) {
				aBoolean959 = false;
			} else if (args[3].equals("members")) {
				aBoolean959 = true;
			} else {
				System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
				return;
			}
			Signlink.storeid = Integer.parseInt(args[4]);
			Signlink.startpriv(InetAddress.getLocalHost());
			Game game1 = new Game();
			game1.method1(503, 765);
		} catch (Exception ignored) {
		}
	}

	public static String method110(int i, int j) {
		int k = i - j;
		if (k < -9) {
			return "@red@";
		}
		if (k < -6) {
			return "@or3@";
		}
		if (k < -3) {
			return "@or2@";
		}
		if (k < 0) {
			return "@or1@";
		}
		if (k > 9) {
			return "@gre@";
		}
		if (k > 6) {
			return "@gr3@";
		}
		if (k > 3) {
			return "@gr2@";
		}
		if (k > 0) {
			return "@gr1@";
		} else {
			return "@yel@";
		}
	}

	public static void method138() {
		SceneGraph.aBoolean436 = true;
		Draw3D.aBoolean1461 = true;
		aBoolean960 = true;
		SceneBuilder.aBoolean151 = true;
		LocType.aBoolean752 = true;
	}

	public void method15() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	public void method16() {
		int j = 5;
		anIntArray1090[8] = 0;
		int k = 0;
		while (anIntArray1090[8] == 0) {
			String s = "Unknown problem";
			method13(20, "Connecting to web server");
			try {
				DataInputStream datainputstream = method132("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
				Buffer buffer = new Buffer(new byte[40]);
				datainputstream.readFully(buffer.data, 0, 40);
				datainputstream.close();
				for (int i1 = 0; i1 < 9; i1++) {
					anIntArray1090[i1] = buffer.get32();
				}
				int j1 = buffer.get32();
				int k1 = 1234;
				for (int l1 = 0; l1 < 9; l1++) {
					k1 = (k1 << 1) + anIntArray1090[l1];
				}
				if (j1 != k1) {
					s = "checksum problem";
					anIntArray1090[8] = 0;
				}
			} catch (EOFException _ex) {
				s = "EOF problem";
				anIntArray1090[8] = 0;
			} catch (IOException _ex) {
				s = "connection problem";
				anIntArray1090[8] = 0;
			} catch (Exception _ex) {
				s = "logic problem";
				anIntArray1090[8] = 0;
				if (!Signlink.reporterror) {
					return;
				}
			}
			if (anIntArray1090[8] == 0) {
				k++;
				for (int l = j; l > 0; l--) {
					if (k >= 10) {
						method13(10, "Game updated - please reload page");
						l = 10;
					} else {
						method13(10, s + " - Will retry in " + l + " secs.");
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception ignored) {
					}
				}
				j *= 2;
				if (j > 60) {
					j = 60;
				}
				aBoolean872 = !aBoolean872;
			}
		}
	}

	public boolean method17(int j) {
		if (j < 0) {
			return false;
		}
		int k = anIntArray1093[j];
		if (k >= 2000) {
			k -= 2000;
		}
		return k == 337;
	}

	public void method18() {
		aArea_1166.method237();
		Draw3D.anIntArray1472 = anIntArray1180;
		aImage_1198.method361(0, 0);
		if (aBoolean1256) {
			aFont_1272.method381(0, aString1121, 40, 239);
			aFont_1272.method381(128, aString1212 + "*", 60, 239);
		} else if (anInt1225 == 1) {
			aFont_1272.method381(0, "Enter amount:", 40, 239);
			aFont_1272.method381(128, aString1004 + "*", 60, 239);
		} else if (anInt1225 == 2) {
			aFont_1272.method381(0, "Enter name:", 40, 239);
			aFont_1272.method381(128, aString1004 + "*", 60, 239);
		} else if (aString844 != null) {
			aFont_1272.method381(0, aString844, 40, 239);
			aFont_1272.method381(128, "Click to continue", 60, 239);
		} else if (anInt1276 != -1) {
			method105(0, 0, Component.aComponentArray210[anInt1276], 0);
		} else if (anInt1042 != -1) {
			method105(0, 0, Component.aComponentArray210[anInt1042], 0);
		} else {
			BitmapFont font = aFont_1271;
			int j = 0;
			Draw2D.setBounds(77, 0, 463, 0);
			for (int k = 0; k < 100; k++) {
				if (aStringArray944[k] != null) {
					int l = anIntArray942[k];
					int i1 = (70 - (j * 14)) + anInt1089;
					String s1 = aStringArray943[k];
					byte byte0 = 0;
					if ((s1 != null) && s1.startsWith("@cr1@")) {
						s1 = s1.substring(5);
						byte0 = 1;
					}
					if ((s1 != null) && s1.startsWith("@cr2@")) {
						s1 = s1.substring(5);
						byte0 = 2;
					}
					if (l == 0) {
						if ((i1 > 0) && (i1 < 110)) {
							font.method385(0, aStringArray944[k], i1, 4);
						}
						j++;
					}
					if (((l == 1) || (l == 2)) && ((l == 1) || (anInt1287 == 0) || ((anInt1287 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							int j1 = 4;
							if (byte0 == 1) {
								aImageArray1219[0].method361(j1, i1 - 12);
								j1 += 14;
							}
							if (byte0 == 2) {
								aImageArray1219[1].method361(j1, i1 - 12);
								j1 += 14;
							}
							font.method385(0, s1 + ":", i1, j1);
							j1 += font.method383(s1) + 8;
							font.method385(255, aStringArray944[k], i1, j1);
						}
						j++;
					}
					if (((l == 3) || (l == 7)) && (anInt1195 == 0) && ((l == 7) || (anInt845 == 0) || ((anInt845 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							int k1 = 4;
							font.method385(0, "From", i1, k1);
							k1 += font.method383("From ");
							if (byte0 == 1) {
								aImageArray1219[0].method361(k1, i1 - 12);
								k1 += 14;
							}
							if (byte0 == 2) {
								aImageArray1219[1].method361(k1, i1 - 12);
								k1 += 14;
							}
							font.method385(0, s1 + ":", i1, k1);
							k1 += font.method383(s1) + 8;
							font.method385(0x800000, aStringArray944[k], i1, k1);
						}
						j++;
					}
					if ((l == 4) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							font.method385(0x800080, s1 + " " + aStringArray944[k], i1, 4);
						}
						j++;
					}
					if ((l == 5) && (anInt1195 == 0) && (anInt845 < 2)) {
						if ((i1 > 0) && (i1 < 110)) {
							font.method385(0x800000, aStringArray944[k], i1, 4);
						}
						j++;
					}
					if ((l == 6) && (anInt1195 == 0) && (anInt845 < 2)) {
						if ((i1 > 0) && (i1 < 110)) {
							font.method385(0, "To " + s1 + ":", i1, 4);
							font.method385(0x800000, aStringArray944[k], i1, 12 + font.method383("To " + s1));
						}
						j++;
					}
					if ((l == 8) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							font.method385(0x7e3200, s1 + " " + aStringArray944[k], i1, 4);
						}
						j++;
					}
				}
			}
			Draw2D.resetBounds();
			anInt1211 = (j * 14) + 7;
			if (anInt1211 < 78) {
				anInt1211 = 78;
			}
			method30(77, anInt1211 - anInt1089 - 77, 0, 463, anInt1211);
			String s;
			if ((aPlayer_1126 != null) && (aPlayer_1126.aString1703 != null)) {
				s = aPlayer_1126.aString1703;
			} else {
				s = StringUtil.formatName(aString1173);
			}
			font.method385(0, s + ":", 90, 4);
			font.method385(255, aString887 + "*", 90, 6 + font.method383(s + ": "));
			Draw2D.drawLineX(0, 77, 479, 0);
		}
		if (aBoolean885 && (anInt948 == 2)) {
			method40();
		}
		aArea_1166.method238(357, super.aGraphics12, 17);
		aArea_1165.method237();
		Draw3D.anIntArray1472 = anIntArray1182;
	}

	public Socket method19(int i) throws IOException {
		if (Signlink.mainapp != null) {
			return Signlink.opensocket(i);
		} else {
			return new Socket(InetAddress.getByName(getCodeBase().getHost()), i);
		}
	}

	public void method20() {
		if (anInt1086 != 0) {
			return;
		}
		int j = super.anInt26;
		if ((anInt1136 == 1) && (super.anInt27 >= 516) && (super.anInt28 >= 160) && (super.anInt27 <= 765) && (super.anInt28 <= 205)) {
			j = 0;
		}
		if (aBoolean885) {
			if (j != 1) {
				int k = super.anInt20;
				int j1 = super.anInt21;
				if (anInt948 == 0) {
					k -= 4;
					j1 -= 4;
				}
				if (anInt948 == 1) {
					k -= 553;
					j1 -= 205;
				}
				if (anInt948 == 2) {
					k -= 17;
					j1 -= 357;
				}
				if ((k < (anInt949 - 10)) || (k > (anInt949 + anInt951 + 10)) || (j1 < (anInt950 - 10)) || (j1 > (anInt950 + anInt952 + 10))) {
					aBoolean885 = false;
					if (anInt948 == 1) {
						aBoolean1153 = true;
					}
					if (anInt948 == 2) {
						aBoolean1223 = true;
					}
				}
			}
			if (j == 1) {
				int l = anInt949;
				int k1 = anInt950;
				int i2 = anInt951;
				int k2 = super.anInt27;
				int l2 = super.anInt28;
				if (anInt948 == 0) {
					k2 -= 4;
					l2 -= 4;
				}
				if (anInt948 == 1) {
					k2 -= 553;
					l2 -= 205;
				}
				if (anInt948 == 2) {
					k2 -= 17;
					l2 -= 357;
				}
				int i3 = -1;
				for (int j3 = 0; j3 < anInt1133; j3++) {
					int k3 = k1 + 31 + ((anInt1133 - 1 - j3) * 15);
					if ((k2 > l) && (k2 < (l + i2)) && (l2 > (k3 - 13)) && (l2 < (k3 + 3))) {
						i3 = j3;
					}
				}
				if (i3 != -1) {
					method69(i3);
				}
				aBoolean885 = false;
				if (anInt948 == 1) {
					aBoolean1153 = true;
				}
				if (anInt948 == 2) {
					aBoolean1223 = true;
				}
			}
		} else {
			if ((j == 1) && (anInt1133 > 0)) {
				int i1 = anIntArray1093[anInt1133 - 1];
				if ((i1 == 632) || (i1 == 78) || (i1 == 867) || (i1 == 431) || (i1 == 53) || (i1 == 74) || (i1 == 454) || (i1 == 539) || (i1 == 493) || (i1 == 847) || (i1 == 447) || (i1 == 1125)) {
					int l1 = anIntArray1091[anInt1133 - 1];
					int j2 = anIntArray1092[anInt1133 - 1];
					Component component = Component.aComponentArray210[j2];
					if (component.aBoolean259 || component.aBoolean235) {
						aBoolean1242 = false;
						anInt989 = 0;
						anInt1084 = j2;
						anInt1085 = l1;
						anInt1086 = 2;
						anInt1087 = super.anInt27;
						anInt1088 = super.anInt28;
						if (Component.aComponentArray210[j2].anInt236 == anInt857) {
							anInt1086 = 1;
						}
						if (Component.aComponentArray210[j2].anInt236 == anInt1276) {
							anInt1086 = 3;
						}
						return;
					}
				}
			}
			if ((j == 1) && ((anInt1253 == 1) || method17(anInt1133 - 1)) && (anInt1133 > 2)) {
				j = 2;
			}
			if ((j == 1) && (anInt1133 > 0)) {
				method69(anInt1133 - 1);
			}
			if ((j == 2) && (anInt1133 > 0)) {
				method116();
			}
		}
	}

	public void method21(boolean flag, byte[] abyte0) {
		Signlink.midifade = flag ? 1 : 0;
		Signlink.midisave(abyte0, abyte0.length);
	}

	public void method22() {
		try {
			anInt985 = -1;
			aList_1056.method256();
			aList_1013.method256();
			Draw3D.method366();
			method23();
			aGraph_946.method274();
			System.gc();
			for (int i = 0; i < 4; i++) {
				aCollisionMapArray1230[i].method210();
			}
			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++) {
						aByteArrayArrayArray1258[l][k1][j2] = 0;
					}
				}
			}
			SceneBuilder sceneBuilder = new SceneBuilder(aByteArrayArrayArray1258, 104, 104, anIntArrayArrayArray1214);
			int k2 = aByteArrayArray1183.length;
			aBuffer_1192.putOp(0);
			if (!aBoolean1159) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = ((anIntArray1234[i3] >> 8) * 64) - anInt1034;
					int k5 = ((anIntArray1234[i3] & 0xff) * 64) - anInt1035;
					byte[] abyte0 = aByteArrayArray1183[i3];
					if (abyte0 != null) {
						sceneBuilder.method180(abyte0, k5, i4, (anInt1069 - 6) * 8, (anInt1070 - 6) * 8, aCollisionMapArray1230);
					}
				}
				for (int j4 = 0; j4 < k2; j4++) {
					int l5 = ((anIntArray1234[j4] >> 8) * 64) - anInt1034;
					int k7 = ((anIntArray1234[j4] & 0xff) * 64) - anInt1035;
					byte[] abyte2 = aByteArrayArray1183[j4];
					if ((abyte2 == null) && (anInt1070 < 800)) {
						sceneBuilder.method174(k7, 64, 64, l5);
					}
				}
				aBuffer_1192.putOp(0);
				for (int i6 = 0; i6 < k2; i6++) {
					byte[] abyte1 = aByteArrayArray1247[i6];
					if (abyte1 != null) {
						int l8 = ((anIntArray1234[i6] >> 8) * 64) - anInt1034;
						int k9 = ((anIntArray1234[i6] & 0xff) * 64) - anInt1035;
						sceneBuilder.method190(l8, aCollisionMapArray1230, k9, aGraph_946, abyte1);
					}
				}
			}
			if (aBoolean1159) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = anIntArrayArrayArray1129[j3][k4][j6];
							if (l7 != -1) {
								int i9 = (l7 >> 24) & 3;
								int l9 = (l7 >> 1) & 3;
								int j10 = (l7 >> 14) & 0x3ff;
								int l10 = (l7 >> 3) & 0x7ff;
								int j11 = ((j10 / 8) << 8) + (l10 / 8);
								for (int l11 = 0; l11 < anIntArray1234.length; l11++) {
									if ((anIntArray1234[l11] != j11) || (aByteArrayArray1183[l11] == null)) {
										continue;
									}
									sceneBuilder.method179(i9, l9, aCollisionMapArray1230, k4 * 8, (j10 & 7) * 8, aByteArrayArray1183[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}
							}
						}
					}
				}
				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = anIntArrayArrayArray1129[0][l4][k6];
						if (i8 == -1) {
							sceneBuilder.method174(k6 * 8, 8, 8, l4 * 8);
						}
					}
				}
				aBuffer_1192.putOp(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = anIntArrayArrayArray1129[l6][j8][j9];
							if (i10 != -1) {
								int k10 = (i10 >> 24) & 3;
								int i11 = (i10 >> 1) & 3;
								int k11 = (i10 >> 14) & 0x3ff;
								int i12 = (i10 >> 3) & 0x7ff;
								int j12 = ((k11 / 8) << 8) + (i12 / 8);
								for (int k12 = 0; k12 < anIntArray1234.length; k12++) {
									if ((anIntArray1234[k12] != j12) || (aByteArrayArray1247[k12] == null)) {
										continue;
									}
									sceneBuilder.method183(aCollisionMapArray1230, aGraph_946, k10, j8 * 8, (i12 & 7) * 8, l6, aByteArrayArray1247[k12], (k11 & 7) * 8, i11, j9 * 8);
									break;
								}
							}
						}
					}
				}
			}
			aBuffer_1192.putOp(0);
			sceneBuilder.method171(aCollisionMapArray1230, aGraph_946);
			aArea_1165.method237();
			aBuffer_1192.putOp(0);
			int k3 = SceneBuilder.anInt145;
			if (k3 > anInt918) {
				k3 = anInt918;
			}
			if (k3 < (anInt918 - 1)) {
			}
			if (aBoolean960) {
				aGraph_946.method275(SceneBuilder.anInt145);
			} else {
				aGraph_946.method275(0);
			}
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++) {
					method25(i5, i7);
				}
			}
			method63();
		} catch (Exception ignored) {
		}
		LocType.aCache_785.method224();
		if (super.aGameFrame__15 != null) {
			aBuffer_1192.putOp(210);
			aBuffer_1192.put32(0x3f008edd);
		}
		if (aBoolean960 && (Signlink.cache_dat != null)) {
			int j = aOnDemand_1068.method555(0);
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = aOnDemand_1068.method559(i1);
				if ((l1 & 0x79) == 0) {
					Model.unload(i1);
				}
			}
		}
		System.gc();
		Draw3D.method367(20);
		aOnDemand_1068.method566();
		int k = ((anInt1069 - 6) / 8) - 1;
		int j1 = ((anInt1069 + 6) / 8) + 1;
		int i2 = ((anInt1070 - 6) / 8) - 1;
		int l2 = ((anInt1070 + 6) / 8) + 1;
		if (aBoolean1141) {
			k = 49;
			j1 = 50;
			i2 = 49;
			l2 = 50;
		}
		for (int l3 = k; l3 <= j1; l3++) {
			for (int j5 = i2; j5 <= l2; j5++) {
				if ((l3 == k) || (l3 == j1) || (j5 == i2) || (j5 == l2)) {
					int j7 = aOnDemand_1068.method562(0, j5, l3);
					if (j7 != -1) {
						aOnDemand_1068.method560(j7, 3);
					}
					int k8 = aOnDemand_1068.method562(1, j5, l3);
					if (k8 != -1) {
						aOnDemand_1068.method560(k8, 3);
					}
				}
			}
		}
	}

	public void method23() {
		LocType.aCache_785.method224();
		LocType.aCache_780.method224();
		NPCType.aCache_95.method224();
		ObjType.aCache_159.method224();
		ObjType.aCache_158.method224();
		PlayerEntity.aCache_1704.method224();
		SpotAnimType.aCache_415.method224();
	}

	public void method24(int i) {
		int[] ai = aImage_1263.anIntArray1439;
		int j = ai.length;
		for (int k = 0; k < j; k++) {
			ai[k] = 0;
		}
		for (int l = 1; l < 103; l++) {
			int i1 = 24628 + ((103 - l) * 512 * 4);
			for (int k1 = 1; k1 < 103; k1++) {
				if ((aByteArrayArrayArray1258[i][k1][l] & 0x18) == 0) {
					aGraph_946.method309(ai, i1, 512, i, k1, l);
				}
				if ((i < 3) && ((aByteArrayArrayArray1258[i + 1][k1][l] & 8) != 0)) {
					aGraph_946.method309(ai, i1, 512, i + 1, k1, l);
				}
				i1 += 4;
			}
		}
		int j1 = (((238 + (int) (Math.random() * 20D)) - 10) << 16) + (((238 + (int) (Math.random() * 20D)) - 10) << 8) + ((238 + (int) (Math.random() * 20D)) - 10);
		int l1 = ((238 + (int) (Math.random() * 20D)) - 10) << 16;
		aImage_1263.method343();
		for (int i2 = 1; i2 < 103; i2++) {
			for (int j2 = 1; j2 < 103; j2++) {
				if ((aByteArrayArrayArray1258[i][j2][i2] & 0x18) == 0) {
					method50(i2, j1, j2, l1, i);
				}
				if ((i < 3) && ((aByteArrayArrayArray1258[i + 1][j2][i2] & 8) != 0)) {
					method50(i2, j1, j2, l1, i + 1);
				}
			}
		}
		aArea_1165.method237();
		anInt1071 = 0;
		for (int k2 = 0; k2 < 104; k2++) {
			for (int l2 = 0; l2 < 104; l2++) {
				int i3 = aGraph_946.method303(anInt918, k2, l2);
				if (i3 != 0) {
					i3 = (i3 >> 14) & 0x7fff;
					int j3 = LocType.method572(i3).anInt746;
					if (j3 >= 0) {
						int k3 = k2;
						int l3 = l2;
						if ((j3 != 22) && (j3 != 29) && (j3 != 34) && (j3 != 36) && (j3 != 46) && (j3 != 47) && (j3 != 48)) {
							byte byte0 = 104;
							byte byte1 = 104;
							int[][] ai1 = aCollisionMapArray1230[anInt918].anIntArrayArray294;
							for (int i4 = 0; i4 < 10; i4++) {
								int j4 = (int) (Math.random() * 4D);
								if ((j4 == 0) && (k3 > 0) && (k3 > (k2 - 3)) && ((ai1[k3 - 1][l3] & 0x1280108) == 0)) {
									k3--;
								}
								if ((j4 == 1) && (k3 < (byte0 - 1)) && (k3 < (k2 + 3)) && ((ai1[k3 + 1][l3] & 0x1280180) == 0)) {
									k3++;
								}
								if ((j4 == 2) && (l3 > 0) && (l3 > (l2 - 3)) && ((ai1[k3][l3 - 1] & 0x1280102) == 0)) {
									l3--;
								}
								if ((j4 == 3) && (l3 < (byte1 - 1)) && (l3 < (l2 + 3)) && ((ai1[k3][l3 + 1] & 0x1280120) == 0)) {
									l3++;
								}
							}
						}
						aImageArray1140[anInt1071] = aImageArray1033[j3];
						anIntArray1072[anInt1071] = k3;
						anIntArray1073[anInt1071] = l3;
						anInt1071++;
					}
				}
			}
		}
	}

	public void method25(int i, int j) {
		LinkedList list = aListArrayArrayArray827[anInt918][i][j];
		if (list == null) {
			aGraph_946.method295(anInt918, i, j);
			return;
		}
		int k = 0xfa0a1f01;
		ObjStackEntity obj = null;
		for (ObjStackEntity objStack = (ObjStackEntity) list.method252(); objStack != null; objStack = (ObjStackEntity) list.method254()) {
			ObjType type = ObjType.method198(objStack.anInt1558);
			int l = type.anInt155;
			if (type.aBoolean176) {
				l *= objStack.anInt1559 + 1;
			}
			if (l > k) {
				k = l;
				obj = objStack;
			}
		}
		list.method250(obj);
		ObjStackEntity obj1 = null;
		Entity obj2 = null;
		for (ObjStackEntity objStack_1 = (ObjStackEntity) list.method252(); objStack_1 != null; objStack_1 = (ObjStackEntity) list.method254()) {
			if ((objStack_1.anInt1558 != obj.anInt1558) && (obj1 == null)) {
				obj1 = objStack_1;
			}
			if ((objStack_1.anInt1558 != obj.anInt1558) && (objStack_1.anInt1558 != obj1.anInt1558) && (obj2 == null)) {
				obj2 = objStack_1;
			}
		}
		int i1 = i + (j << 7) + 0x60000000;
		aGraph_946.method281(i, i1, obj1, method42(anInt918, (j * 128) + 64, (i * 128) + 64), obj2, obj, anInt918, j);
	}

	public void method26(boolean flag) {
		for (int j = 0; j < anInt836; j++) {
			NPCEntity npc = aNpcArray835[anIntArray837[j]];
			int k = 0x20000000 + (anIntArray837[j] << 14);
			if ((npc == null) || !npc.method449() || (npc.aType_1696.aBoolean93 != flag)) {
				continue;
			}
			int l = npc.anInt1550 >> 7;
			int i1 = npc.anInt1551 >> 7;
			if ((l < 0) || (l >= 104) || (i1 < 0) || (i1 >= 104)) {
				continue;
			}
			if ((npc.anInt1540 == 1) && ((npc.anInt1550 & 0x7f) == 64) && ((npc.anInt1551 & 0x7f) == 64)) {
				if (anIntArrayArray929[l][i1] == anInt1265) {
					continue;
				}
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if (!npc.aType_1696.aBoolean84) {
				k += 0x80000000;
			}
			aGraph_946.method285(anInt918, npc.anInt1552, method42(anInt918, npc.anInt1551, npc.anInt1550), k, npc.anInt1551, ((npc.anInt1540 - 1) * 64) + 60, npc.anInt1550, npc, npc.aBoolean1541);
		}
	}

	public boolean method27() {
		return Signlink.wavereplay();
	}

	public void method28(String s) {
		System.out.println(s);
		try {
			getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		//noinspection InfiniteLoopStatement
		while (true) {
			try {
				Thread.sleep(1000L);
			} catch (Exception ignored) {
			}
		}
	}

	public void method29(int i, Component component, int k, int l, int i1, int j1) {
		if ((component.anInt262 != 0) || (component.anIntArray240 == null) || component.aBoolean266) {
			return;
		}
		if ((k < i) || (i1 < l) || (k > (i + component.anInt220)) || (i1 > (l + component.anInt267))) {
			return;
		}
		int k1 = component.anIntArray240.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = component.anIntArray241[l1] + i;
			int j2 = (component.anIntArray272[l1] + l) - j1;
			Component component_1 = Component.aComponentArray210[component.anIntArray240[l1]];
			i2 += component_1.anInt263;
			j2 += component_1.anInt265;
			if (((component_1.anInt230 >= 0) || (component_1.anInt216 != 0)) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
				if (component_1.anInt230 >= 0) {
					anInt886 = component_1.anInt230;
				} else {
					anInt886 = component_1.anInt250;
				}
			}
			if (component_1.anInt262 == 0) {
				method29(i2, component_1, k, j2, i1, component_1.anInt224);
				if (component_1.anInt261 > component_1.anInt267) {
					method65(i2 + component_1.anInt220, component_1.anInt267, k, i1, component_1, j2, true, component_1.anInt261);
				}
			} else {
				if ((component_1.anInt217 == 1) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					boolean flag = false;
					if (component_1.anInt214 != 0) {
						flag = method103(component_1);
					}
					if (!flag) {
						aStringArray1199[anInt1133] = component_1.aString221;
						anIntArray1093[anInt1133] = 315;
						anIntArray1092[anInt1133] = component_1.anInt250;
						anInt1133++;
					}
				}
				if ((component_1.anInt217 == 2) && (anInt1136 == 0) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					String s = component_1.aString222;
					if (s.contains(" ")) {
						s = s.substring(0, s.indexOf(" "));
					}
					aStringArray1199[anInt1133] = s + " @gre@" + component_1.aString218;
					anIntArray1093[anInt1133] = 626;
					anIntArray1092[anInt1133] = component_1.anInt250;
					anInt1133++;
				}
				if ((component_1.anInt217 == 3) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					aStringArray1199[anInt1133] = "Close";
					anIntArray1093[anInt1133] = 200;
					anIntArray1092[anInt1133] = component_1.anInt250;
					anInt1133++;
				}
				if ((component_1.anInt217 == 4) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					aStringArray1199[anInt1133] = component_1.aString221;
					anIntArray1093[anInt1133] = 169;
					anIntArray1092[anInt1133] = component_1.anInt250;
					anInt1133++;
				}
				if ((component_1.anInt217 == 5) && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					aStringArray1199[anInt1133] = component_1.aString221;
					anIntArray1093[anInt1133] = 646;
					anIntArray1092[anInt1133] = component_1.anInt250;
					anInt1133++;
				}
				if ((component_1.anInt217 == 6) && !aBoolean1149 && (k >= i2) && (i1 >= j2) && (k < (i2 + component_1.anInt220)) && (i1 < (j2 + component_1.anInt267))) {
					aStringArray1199[anInt1133] = component_1.aString221;
					anIntArray1093[anInt1133] = 679;
					anIntArray1092[anInt1133] = component_1.anInt250;
					anInt1133++;
				}
				if (component_1.anInt262 == 2) {
					int k2 = 0;
					for (int l2 = 0; l2 < component_1.anInt267; l2++) {
						for (int i3 = 0; i3 < component_1.anInt220; i3++) {
							int j3 = i2 + (i3 * (32 + component_1.anInt231));
							int k3 = j2 + (l2 * (32 + component_1.anInt244));
							if (k2 < 20) {
								j3 += component_1.anIntArray215[k2];
								k3 += component_1.anIntArray247[k2];
							}
							if ((k >= j3) && (i1 >= k3) && (k < (j3 + 32)) && (i1 < (k3 + 32))) {
								anInt1066 = k2;
								anInt1067 = component_1.anInt250;
								if (component_1.anIntArray253[k2] > 0) {
									ObjType type = ObjType.method198(component_1.anIntArray253[k2] - 1);
									if ((anInt1282 == 1) && component_1.aBoolean249) {
										if ((component_1.anInt250 != anInt1284) || (k2 != anInt1283)) {
											aStringArray1199[anInt1133] = "Use " + aString1286 + " with @lre@" + type.aString170;
											anIntArray1093[anInt1133] = 870;
											anIntArray1094[anInt1133] = type.anInt157;
											anIntArray1091[anInt1133] = k2;
											anIntArray1092[anInt1133] = component_1.anInt250;
											anInt1133++;
										}
									} else if ((anInt1136 == 1) && component_1.aBoolean249) {
										if ((anInt1138 & 0x10) == 16) {
											aStringArray1199[anInt1133] = aString1139 + " @lre@" + type.aString170;
											anIntArray1093[anInt1133] = 543;
											anIntArray1094[anInt1133] = type.anInt157;
											anIntArray1091[anInt1133] = k2;
											anIntArray1092[anInt1133] = component_1.anInt250;
											anInt1133++;
										}
									} else {
										if (component_1.aBoolean249) {
											for (int l3 = 4; l3 >= 3; l3--) {
												if ((type.aStringArray189 != null) && (type.aStringArray189[l3] != null)) {
													aStringArray1199[anInt1133] = type.aStringArray189[l3] + " @lre@" + type.aString170;
													if (l3 == 3) {
														anIntArray1093[anInt1133] = 493;
													}
													if (l3 == 4) {
														anIntArray1093[anInt1133] = 847;
													}
													anIntArray1094[anInt1133] = type.anInt157;
													anIntArray1091[anInt1133] = k2;
													anIntArray1092[anInt1133] = component_1.anInt250;
													anInt1133++;
												} else if (l3 == 4) {
													aStringArray1199[anInt1133] = "Drop @lre@" + type.aString170;
													anIntArray1093[anInt1133] = 847;
													anIntArray1094[anInt1133] = type.anInt157;
													anIntArray1091[anInt1133] = k2;
													anIntArray1092[anInt1133] = component_1.anInt250;
													anInt1133++;
												}
											}
										}
										if (component_1.aBoolean242) {
											aStringArray1199[anInt1133] = "Use @lre@" + type.aString170;
											anIntArray1093[anInt1133] = 447;
											anIntArray1094[anInt1133] = type.anInt157;
											anIntArray1091[anInt1133] = k2;
											anIntArray1092[anInt1133] = component_1.anInt250;
											anInt1133++;
										}
										if (component_1.aBoolean249 && (type.aStringArray189 != null)) {
											for (int i4 = 2; i4 >= 0; i4--) {
												if (type.aStringArray189[i4] != null) {
													aStringArray1199[anInt1133] = type.aStringArray189[i4] + " @lre@" + type.aString170;
													if (i4 == 0) {
														anIntArray1093[anInt1133] = 74;
													}
													if (i4 == 1) {
														anIntArray1093[anInt1133] = 454;
													}
													if (i4 == 2) {
														anIntArray1093[anInt1133] = 539;
													}
													anIntArray1094[anInt1133] = type.anInt157;
													anIntArray1091[anInt1133] = k2;
													anIntArray1092[anInt1133] = component_1.anInt250;
													anInt1133++;
												}
											}
										}
										if (component_1.aStringArray225 != null) {
											for (int j4 = 4; j4 >= 0; j4--) {
												if (component_1.aStringArray225[j4] != null) {
													aStringArray1199[anInt1133] = component_1.aStringArray225[j4] + " @lre@" + type.aString170;
													if (j4 == 0) {
														anIntArray1093[anInt1133] = 632;
													}
													if (j4 == 1) {
														anIntArray1093[anInt1133] = 78;
													}
													if (j4 == 2) {
														anIntArray1093[anInt1133] = 867;
													}
													if (j4 == 3) {
														anIntArray1093[anInt1133] = 431;
													}
													if (j4 == 4) {
														anIntArray1093[anInt1133] = 53;
													}
													anIntArray1094[anInt1133] = type.anInt157;
													anIntArray1091[anInt1133] = k2;
													anIntArray1092[anInt1133] = component_1.anInt250;
													anInt1133++;
												}
											}
										}
										aStringArray1199[anInt1133] = "Examine @lre@" + type.aString170;
										anIntArray1093[anInt1133] = 1125;
										anIntArray1094[anInt1133] = type.anInt157;
										anIntArray1091[anInt1133] = k2;
										anIntArray1092[anInt1133] = component_1.anInt250;
										anInt1133++;
									}
								}
							}
							k2++;
						}
					}
				}
			}
		}
	}

	public void method30(int j, int k, int l, int i1, int j1) {
		aImage_1024.method361(i1, l);
		aImage_1025.method361(i1, (l + j) - 16);
		Draw2D.fillRect(i1, l + 16, 16, j - 32, anInt1002);
		int k1 = ((j - 32) * j) / j1;
		if (k1 < 8) {
			k1 = 8;
		}
		int l1 = ((j - 32 - k1) * k) / (j1 - j);
		Draw2D.fillRect(i1, l + 16 + l1, 16, k1, anInt1063);
		Draw2D.drawLineY(l + 16 + l1, anInt902, k1, i1);
		Draw2D.drawLineY(l + 16 + l1, anInt902, k1, i1 + 1);
		Draw2D.drawLineX(i1, l + 16 + l1, 16, anInt902);
		Draw2D.drawLineX(i1, l + 17 + l1, 16, anInt902);
		Draw2D.drawLineY(l + 16 + l1, anInt927, k1, i1 + 15);
		Draw2D.drawLineY(l + 17 + l1, anInt927, k1 - 1, i1 + 14);
		Draw2D.drawLineX(i1, l + 15 + l1 + k1, 16, anInt927);
		Draw2D.drawLineX(i1 + 1, l + 14 + l1 + k1, 15, anInt927);
	}

	public void method31(Buffer buffer, int i) {
		anInt839 = 0;
		anInt893 = 0;
		method139(buffer);
		method46(i, buffer);
		method86(buffer);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (aNpcArray835[l].anInt1537 != anInt1161) {
				aNpcArray835[l].aType_1696 = null;
				aNpcArray835[l] = null;
			}
		}
		if (buffer.position != i) {
			Signlink.reporterror(aString1173 + " size mismatch in getnpcpos - pos:" + buffer.position + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < anInt836; i1++) {
			if (aNpcArray835[anIntArray837[i1]] == null) {
				Signlink.reporterror(aString1173 + " null entry in npc list - pos:" + i1 + " size:" + anInt836);
				throw new RuntimeException("eek");
			}
		}
	}

	public void method32() {
		if (super.anInt26 == 1) {
			if ((super.anInt27 >= 6) && (super.anInt27 <= 106) && (super.anInt28 >= 467) && (super.anInt28 <= 499)) {
				anInt1287 = (anInt1287 + 1) % 4;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put8(anInt1287);
				aBuffer_1192.put8(anInt845);
				aBuffer_1192.put8(anInt1248);
			}
			if ((super.anInt27 >= 135) && (super.anInt27 <= 235) && (super.anInt28 >= 467) && (super.anInt28 <= 499)) {
				anInt845 = (anInt845 + 1) % 3;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put8(anInt1287);
				aBuffer_1192.put8(anInt845);
				aBuffer_1192.put8(anInt1248);
			}
			if ((super.anInt27 >= 273) && (super.anInt27 <= 373) && (super.anInt28 >= 467) && (super.anInt28 <= 499)) {
				anInt1248 = (anInt1248 + 1) % 3;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put8(anInt1287);
				aBuffer_1192.put8(anInt845);
				aBuffer_1192.put8(anInt1248);
			}
			if ((super.anInt27 >= 412) && (super.anInt27 <= 512) && (super.anInt28 >= 467) && (super.anInt28 <= 499)) {
				if (anInt857 == -1) {
					method147();
					aString881 = "";
					aBoolean1158 = false;
					for (int i = 0; i < Component.aComponentArray210.length; i++) {
						if ((Component.aComponentArray210[i] == null) || (Component.aComponentArray210[i].anInt214 != 600)) {
							continue;
						}
						anInt1178 = anInt857 = Component.aComponentArray210[i].anInt236;
						break;
					}
				} else {
					method77("Please close the interface you have open before using 'report abuse'", 0, "");
				}
			}
		}
	}

	public void method33(int i) {
		int j = VarpType.aVarpArray701[i].anInt709;
		if (j == 0) {
			return;
		}
		int k = anIntArray971[i];
		if (j == 1) {
			if (k == 1) {
				Draw3D.method372(0.90000000000000002D);
			}
			if (k == 2) {
				Draw3D.method372(0.80000000000000004D);
			}
			if (k == 3) {
				Draw3D.method372(0.69999999999999996D);
			}
			if (k == 4) {
				Draw3D.method372(0.59999999999999998D);
			}
			ObjType.aCache_158.method224();
			aBoolean1255 = true;
		}
		if (j == 3) {
			boolean flag1 = aBoolean1151;
			if (k == 0) {
				method123(aBoolean1151, 0);
				aBoolean1151 = true;
			}
			if (k == 1) {
				method123(aBoolean1151, -400);
				aBoolean1151 = true;
			}
			if (k == 2) {
				method123(aBoolean1151, -800);
				aBoolean1151 = true;
			}
			if (k == 3) {
				method123(aBoolean1151, -1200);
				aBoolean1151 = true;
			}
			if (k == 4) {
				aBoolean1151 = false;
			}
			if ((aBoolean1151 != flag1) && !aBoolean960) {
				if (aBoolean1151) {
					anInt1227 = anInt956;
					aBoolean1228 = true;
					aOnDemand_1068.method558(2, anInt1227);
				} else {
					method15();
				}
				anInt1259 = 0;
			}
		}
		if (j == 4) {
			if (k == 0) {
				aBoolean848 = true;
				method111(0);
			}
			if (k == 1) {
				aBoolean848 = true;
				method111(-400);
			}
			if (k == 2) {
				aBoolean848 = true;
				method111(-800);
			}
			if (k == 3) {
				aBoolean848 = true;
				method111(-1200);
			}
			if (k == 4) {
				aBoolean848 = false;
			}
		}
		if (j == 5) {
			anInt1253 = k;
		}
		if (j == 6) {
			anInt1249 = k;
		}
		if (j == 8) {
			anInt1195 = k;
			aBoolean1223 = true;
		}
		if (j == 9) {
			anInt913 = k;
		}
	}

	public void method34() {
		anInt974 = 0;
		for (int j = -1; j < (anInt891 + anInt836); j++) {
			PathingEntity obj;
			if (j == -1) {
				obj = aPlayer_1126;
			} else if (j < anInt891) {
				obj = aPlayerArray890[anIntArray892[j]];
			} else {
				obj = aNpcArray835[anIntArray837[j - anInt891]];
			}
			if ((obj == null) || !obj.method449()) {
				continue;
			}
			if (obj instanceof NPCEntity) {
				NPCType type = ((NPCEntity) obj).aType_1696;
				if (type.anIntArray88 != null) {
					type = type.method161();
				}
				if (type == null) {
					continue;
				}
			}
			if (j < anInt891) {
				int l = 30;
				PlayerEntity player = (PlayerEntity) obj;
				if (player.anInt1706 != 0) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						for (int i2 = 0; i2 < 8; i2++) {
							if ((player.anInt1706 & (1 << i2)) != 0) {
								aImageArray1095[i2].method348(anInt963 - 12, anInt964 - l);
								l -= 25;
							}
						}
					}
				}
				if ((j >= 0) && (anInt855 == 10) && (anInt933 == anIntArray892[j])) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						aImageArray1095[7].method348(anInt963 - 12, anInt964 - l);
					}
				}
			} else {
				NPCType type_1 = ((NPCEntity) obj).aType_1696;
				if ((type_1.anInt75 >= 0) && (type_1.anInt75 < aImageArray1095.length)) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						aImageArray1095[type_1.anInt75].method348(anInt963 - 12, anInt964 - 30);
					}
				}
				if ((anInt855 == 1) && (anInt1222 == anIntArray837[j - anInt891]) && ((anInt1161 % 20) < 10)) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						aImageArray1095[2].method348(anInt963 - 12, anInt964 - 28);
					}
				}
			}
			if ((obj.aString1506 != null) && ((j >= anInt891) || (anInt1287 == 0) || (anInt1287 == 3) || ((anInt1287 == 1) && method109(((PlayerEntity) obj).aString1703)))) {
				method127(obj, obj.anInt1507);
				if ((anInt963 > -1) && (anInt974 < anInt975)) {
					anIntArray979[anInt974] = aFont_1272.method384(obj.aString1506) / 2;
					anIntArray978[anInt974] = aFont_1272.anInt1497;
					anIntArray976[anInt974] = anInt963;
					anIntArray977[anInt974] = anInt964;
					anIntArray980[anInt974] = obj.anInt1513;
					anIntArray981[anInt974] = obj.anInt1531;
					anIntArray982[anInt974] = obj.anInt1535;
					aStringArray983[anInt974++] = obj.aString1506;
					if ((anInt1249 == 0) && (obj.anInt1531 >= 1) && (obj.anInt1531 <= 3)) {
						anIntArray978[anInt974] += 10;
						anIntArray977[anInt974] += 5;
					}
					if ((anInt1249 == 0) && (obj.anInt1531 == 4)) {
						anIntArray979[anInt974] = 60;
					}
					if ((anInt1249 == 0) && (obj.anInt1531 == 5)) {
						anIntArray978[anInt974] += 5;
					}
				}
			}
			if (obj.anInt1532 > anInt1161) {
				method127(obj, obj.anInt1507 + 15);
				if (anInt963 > -1) {
					int i1 = (obj.anInt1533 * 30) / obj.anInt1534;
					if (i1 > 30) {
						i1 = 30;
					}
					Draw2D.fillRect(anInt963 - 15, anInt964 - 3, i1, 5, 65280);
					Draw2D.fillRect((anInt963 - 15) + i1, anInt964 - 3, 30 - i1, 5, 0xff0000);
				}
			}
			for (int j1 = 0; j1 < 4; j1++) {
				if (obj.anIntArray1516[j1] > anInt1161) {
					method127(obj, obj.anInt1507 / 2);
					if (anInt963 > -1) {
						if (j1 == 1) {
							anInt964 -= 20;
						}
						if (j1 == 2) {
							anInt963 -= 15;
							anInt964 -= 10;
						}
						if (j1 == 3) {
							anInt963 += 15;
							anInt964 -= 10;
						}
						aImageArray987[obj.anIntArray1515[j1]].method348(anInt963 - 12, anInt964 - 12);
						aFont_1270.method381(0, String.valueOf(obj.anIntArray1514[j1]), anInt964 + 4, anInt963);
						aFont_1270.method381(0xffffff, String.valueOf(obj.anIntArray1514[j1]), anInt964 + 3, anInt963 - 1);
					}
				}
			}
		}
		for (int k = 0; k < anInt974; k++) {
			int k1 = anIntArray976[k];
			int l1 = anIntArray977[k];
			int j2 = anIntArray979[k];
			int k2 = anIntArray978[k];
			boolean flag = true;
			while (flag) {
				flag = false;
				for (int l2 = 0; l2 < k; l2++) {
					if (((l1 + 2) > (anIntArray977[l2] - anIntArray978[l2])) && ((l1 - k2) < (anIntArray977[l2] + 2)) && ((k1 - j2) < (anIntArray976[l2] + anIntArray979[l2])) && ((k1 + j2) > (anIntArray976[l2] - anIntArray979[l2])) && ((anIntArray977[l2] - anIntArray978[l2]) < l1)) {
						l1 = anIntArray977[l2] - anIntArray978[l2];
						flag = true;
					}
				}
			}
			anInt963 = anIntArray976[k];
			anInt964 = anIntArray977[k] = l1;
			String s = aStringArray983[k];
			if (anInt1249 == 0) {
				int i3 = 0xffff00;
				if (anIntArray980[k] < 6) {
					i3 = anIntArray965[anIntArray980[k]];
				}
				if (anIntArray980[k] == 6) {
					i3 = ((anInt1265 % 20) >= 10) ? 0xffff00 : 0xff0000;
				}
				if (anIntArray980[k] == 7) {
					i3 = ((anInt1265 % 20) >= 10) ? 65535 : 255;
				}
				if (anIntArray980[k] == 8) {
					i3 = ((anInt1265 % 20) >= 10) ? 0x80ff80 : 45056;
				}
				if (anIntArray980[k] == 9) {
					int j3 = 150 - anIntArray982[k];
					if (j3 < 50) {
						i3 = 0xff0000 + (1280 * j3);
					} else if (j3 < 100) {
						i3 = 0xffff00 - (0x50000 * (j3 - 50));
					} else if (j3 < 150) {
						i3 = 65280 + (5 * (j3 - 100));
					}
				}
				if (anIntArray980[k] == 10) {
					int k3 = 150 - anIntArray982[k];
					if (k3 < 50) {
						i3 = 0xff0000 + (5 * k3);
					} else if (k3 < 100) {
						i3 = 0xff00ff - (0x50000 * (k3 - 50));
					} else if (k3 < 150) {
						i3 = (255 + (0x50000 * (k3 - 100))) - (5 * (k3 - 100));
					}
				}
				if (anIntArray980[k] == 11) {
					int l3 = 150 - anIntArray982[k];
					if (l3 < 50) {
						i3 = 0xffffff - (0x50005 * l3);
					} else if (l3 < 100) {
						i3 = 65280 + (0x50005 * (l3 - 50));
					} else if (l3 < 150) {
						i3 = 0xffffff - (0x50000 * (l3 - 100));
					}
				}
				if (anIntArray981[k] == 0) {
					aFont_1272.method381(0, s, anInt964 + 1, anInt963);
					aFont_1272.method381(i3, s, anInt964, anInt963);
				}
				if (anIntArray981[k] == 1) {
					aFont_1272.method386(0, s, anInt963, anInt1265, anInt964 + 1);
					aFont_1272.method386(i3, s, anInt963, anInt1265, anInt964);
				}
				if (anIntArray981[k] == 2) {
					aFont_1272.method387(anInt963, s, anInt1265, anInt964 + 1, 0);
					aFont_1272.method387(anInt963, s, anInt1265, anInt964, i3);
				}
				if (anIntArray981[k] == 3) {
					aFont_1272.method388(150 - anIntArray982[k], s, anInt1265, anInt964 + 1, anInt963, 0);
					aFont_1272.method388(150 - anIntArray982[k], s, anInt1265, anInt964, anInt963, i3);
				}
				if (anIntArray981[k] == 4) {
					int i4 = aFont_1272.method384(s);
					int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
					Draw2D.setBounds(334, anInt963 - 50, anInt963 + 50, 0);
					aFont_1272.method385(0, s, anInt964 + 1, (anInt963 + 50) - k4);
					aFont_1272.method385(i3, s, anInt964, (anInt963 + 50) - k4);
					Draw2D.resetBounds();
				}
				if (anIntArray981[k] == 5) {
					int j4 = 150 - anIntArray982[k];
					int l4 = 0;
					if (j4 < 25) {
						l4 = j4 - 25;
					} else if (j4 > 125) {
						l4 = j4 - 125;
					}
					Draw2D.setBounds(anInt964 + 5, 0, 512, anInt964 - aFont_1272.anInt1497 - 1);
					aFont_1272.method381(0, s, anInt964 + 1 + l4, anInt963);
					aFont_1272.method381(i3, s, anInt964 + l4, anInt963);
					Draw2D.resetBounds();
				}
			} else {
				aFont_1272.method381(0, s, anInt964 + 1, anInt963);
				aFont_1272.method381(0xffff00, s, anInt964, anInt963);
			}
		}
	}

	public void method35(long l) {
		if (l == 0L) {
			return;
		}
		for (int i = 0; i < anInt899; i++) {
			if (aLongArray955[i] != l) {
				continue;
			}
			anInt899--;
			aBoolean1153 = true;
			for (int j = i; j < anInt899; j++) {
				aStringArray1082[j] = aStringArray1082[j + 1];
				anIntArray826[j] = anIntArray826[j + 1];
				aLongArray955[j] = aLongArray955[j + 1];
			}
			aBuffer_1192.putOp(215);
			aBuffer_1192.put64(l);
			break;
		}
	}

	public void method36() {
		aArea_1163.method237();
		Draw3D.anIntArray1472 = anIntArray1181;
		aImage_1196.method361(0, 0);
		if (anInt1189 != -1) {
			method105(0, 0, Component.aComponentArray210[anInt1189], 0);
		} else if (anIntArray1130[anInt1221] != -1) {
			method105(0, 0, Component.aComponentArray210[anIntArray1130[anInt1221]], 0);
		}
		if (aBoolean885 && (anInt948 == 1)) {
			method40();
		}
		aArea_1163.method238(205, super.aGraphics12, 553);
		aArea_1165.method237();
		Draw3D.anIntArray1472 = anIntArray1182;
	}

	public void method37(int j) {
		if (!aBoolean960) {
			if (Draw3D.anIntArray1480[17] >= j) {
				Image8 image = Draw3D.aImageArray1474[17];
				int k = (image.anInt1452 * image.anInt1453) - 1;
				int j1 = image.anInt1452 * anInt945 * 2;
				byte[] abyte0 = image.aByteArray1450;
				byte[] abyte3 = aByteArray912;
				for (int i2 = 0; i2 <= k; i2++) {
					abyte3[i2] = abyte0[(i2 - j1) & k];
				}
				image.aByteArray1450 = abyte3;
				aByteArray912 = abyte0;
				Draw3D.method370(17);
			}
			if (Draw3D.anIntArray1480[24] >= j) {
				Image8 class30_sub2_sub1_sub2_1 = Draw3D.aImageArray1474[24];
				int l = (class30_sub2_sub1_sub2_1.anInt1452 * class30_sub2_sub1_sub2_1.anInt1453) - 1;
				int k1 = class30_sub2_sub1_sub2_1.anInt1452 * anInt945 * 2;
				byte[] abyte1 = class30_sub2_sub1_sub2_1.aByteArray1450;
				byte[] abyte4 = aByteArray912;
				for (int j2 = 0; j2 <= l; j2++) {
					abyte4[j2] = abyte1[(j2 - k1) & l];
				}
				class30_sub2_sub1_sub2_1.aByteArray1450 = abyte4;
				aByteArray912 = abyte1;
				Draw3D.method370(24);
			}
			if (Draw3D.anIntArray1480[34] >= j) {
				Image8 class30_sub2_sub1_sub2_2 = Draw3D.aImageArray1474[34];
				int i1 = (class30_sub2_sub1_sub2_2.anInt1452 * class30_sub2_sub1_sub2_2.anInt1453) - 1;
				int l1 = class30_sub2_sub1_sub2_2.anInt1452 * anInt945 * 2;
				byte[] abyte2 = class30_sub2_sub1_sub2_2.aByteArray1450;
				byte[] abyte5 = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++) {
					abyte5[k2] = abyte2[(k2 - l1) & i1];
				}
				class30_sub2_sub1_sub2_2.aByteArray1450 = abyte5;
				aByteArray912 = abyte2;
				Draw3D.method370(34);
			}
		}
	}

	public void method38() {
		for (int i = -1; i < anInt891; i++) {
			int j;
			if (i == -1) {
				j = anInt889;
			} else {
				j = anIntArray892[i];
			}
			PlayerEntity player = aPlayerArray890[j];
			if ((player != null) && (player.anInt1535 > 0)) {
				player.anInt1535--;
				if (player.anInt1535 == 0) {
					player.aString1506 = null;
				}
			}
		}
		for (int k = 0; k < anInt836; k++) {
			int l = anIntArray837[k];
			NPCEntity npc = aNpcArray835[l];
			if ((npc != null) && (npc.anInt1535 > 0)) {
				npc.anInt1535--;
				if (npc.anInt1535 == 0) {
					npc.aString1506 = null;
				}
			}
		}
	}

	public void method39() {
		int i = (anInt1098 * 128) + 64;
		int j = (anInt1099 * 128) + 64;
		int k = method42(anInt918, j, i) - anInt1100;
		if (anInt858 < i) {
			anInt858 += anInt1101 + (((i - anInt858) * anInt1102) / 1000);
			if (anInt858 > i) {
				anInt858 = i;
			}
		}
		if (anInt858 > i) {
			anInt858 -= anInt1101 + (((anInt858 - i) * anInt1102) / 1000);
			if (anInt858 < i) {
				anInt858 = i;
			}
		}
		if (anInt859 < k) {
			anInt859 += anInt1101 + (((k - anInt859) * anInt1102) / 1000);
			if (anInt859 > k) {
				anInt859 = k;
			}
		}
		if (anInt859 > k) {
			anInt859 -= anInt1101 + (((anInt859 - k) * anInt1102) / 1000);
			if (anInt859 < k) {
				anInt859 = k;
			}
		}
		if (anInt860 < j) {
			anInt860 += anInt1101 + (((j - anInt860) * anInt1102) / 1000);
			if (anInt860 > j) {
				anInt860 = j;
			}
		}
		if (anInt860 > j) {
			anInt860 -= anInt1101 + (((anInt860 - j) * anInt1102) / 1000);
			if (anInt860 < j) {
				anInt860 = j;
			}
		}
		i = (anInt995 * 128) + 64;
		j = (anInt996 * 128) + 64;
		k = method42(anInt918, j, i) - anInt997;
		int l = i - anInt858;
		int i1 = k - anInt859;
		int j1 = j - anInt860;
		int k1 = (int) Math.sqrt((l * l) + (j1 * j1));
		int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
		int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
		if (l1 < 128) {
			l1 = 128;
		}
		if (l1 > 383) {
			l1 = 383;
		}
		if (anInt861 < l1) {
			anInt861 += anInt998 + (((l1 - anInt861) * anInt999) / 1000);
			if (anInt861 > l1) {
				anInt861 = l1;
			}
		}
		if (anInt861 > l1) {
			anInt861 -= anInt998 + (((anInt861 - l1) * anInt999) / 1000);
			if (anInt861 < l1) {
				anInt861 = l1;
			}
		}
		int j2 = i2 - anInt862;
		if (j2 > 1024) {
			j2 -= 2048;
		}
		if (j2 < -1024) {
			j2 += 2048;
		}
		if (j2 > 0) {
			anInt862 += anInt998 + ((j2 * anInt999) / 1000);
			anInt862 &= 0x7ff;
		}
		if (j2 < 0) {
			anInt862 -= anInt998 + ((-j2 * anInt999) / 1000);
			anInt862 &= 0x7ff;
		}
		int k2 = i2 - anInt862;
		if (k2 > 1024) {
			k2 -= 2048;
		}
		if (k2 < -1024) {
			k2 += 2048;
		}
		if (((k2 < 0) && (j2 > 0)) || ((k2 > 0) && (j2 < 0))) {
			anInt862 = i2;
		}
	}

	public void method40() {
		int i = anInt949;
		int j = anInt950;
		int k = anInt951;
		int l = anInt952;
		int i1 = 0x5d5447;
		Draw2D.fillRect(i, j, k, l, i1);
		Draw2D.fillRect(i + 1, j + 1, k - 2, 16, 0);
		Draw2D.drawRect(i + 1, j + 18, k - 2, l - 19, 0);
		aFont_1272.method385(i1, "Choose Option", j + 14, i + 3);
		int j1 = super.anInt20;
		int k1 = super.anInt21;
		if (anInt948 == 0) {
			j1 -= 4;
			k1 -= 4;
		}
		if (anInt948 == 1) {
			j1 -= 553;
			k1 -= 205;
		}
		if (anInt948 == 2) {
			j1 -= 17;
			k1 -= 357;
		}
		for (int l1 = 0; l1 < anInt1133; l1++) {
			int i2 = j + 31 + ((anInt1133 - 1 - l1) * 15);
			int j2 = 0xffffff;
			if ((j1 > i) && (j1 < (i + k)) && (k1 > (i2 - 13)) && (k1 < (i2 + 3))) {
				j2 = 0xffff00;
			}
			aFont_1272.method389(true, i + 3, j2, aStringArray1199[l1], i2);
		}
	}

	public void method41(long l) {
		if (l == 0L) {
			return;
		}
		if ((anInt899 >= 100) && (anInt1046 != 1)) {
			method77("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
			return;
		}
		if (anInt899 >= 200) {
			method77("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(l));
		for (int i = 0; i < anInt899; i++) {
			if (aLongArray955[i] == l) {
				method77(s + " is already on your friend list", 0, "");
				return;
			}
		}
		for (int j = 0; j < anInt822; j++) {
			if (aLongArray925[j] == l) {
				method77("Please remove " + s + " from your ignore list first", 0, "");
				return;
			}
		}
		if (!s.equals(aPlayer_1126.aString1703)) {
			aStringArray1082[anInt899] = s;
			aLongArray955[anInt899] = l;
			anIntArray826[anInt899] = 0;
			anInt899++;
			aBoolean1153 = true;
			aBuffer_1192.putOp(188);
			aBuffer_1192.put64(l);
		}
	}

	public int method42(int i, int j, int k) {
		int l = k >> 7;
		int i1 = j >> 7;
		if ((l < 0) || (i1 < 0) || (l > 103) || (i1 > 103)) {
			return 0;
		}
		int j1 = i;
		if ((j1 < 3) && ((aByteArrayArrayArray1258[1][l][i1] & 2) == 2)) {
			j1++;
		}
		int k1 = k & 0x7f;
		int l1 = j & 0x7f;
		int i2 = ((anIntArrayArrayArray1214[j1][l][i1] * (128 - k1)) + (anIntArrayArrayArray1214[j1][l + 1][i1] * k1)) >> 7;
		int j2 = ((anIntArrayArrayArray1214[j1][l][i1 + 1] * (128 - k1)) + (anIntArrayArrayArray1214[j1][l + 1][i1 + 1] * k1)) >> 7;
		return ((i2 * (128 - l1)) + (j2 * l1)) >> 7;
	}

	public void method44() {
		try {
			if (aConnection_1168 != null) {
				aConnection_1168.method267();
			}
		} catch (Exception ignored) {
		}
		aConnection_1168 = null;
		aBoolean1157 = false;
		anInt833 = 0;
		aString1173 = "";
		aString1174 = "";
		method23();
		aGraph_946.method274();
		for (int i = 0; i < 4; i++) {
			aCollisionMapArray1230[i].method210();
		}
		System.gc();
		method15();
		anInt956 = -1;
		anInt1227 = -1;
		anInt1259 = 0;
	}

	public void method45() {
		aBoolean1031 = true;
		for (int j = 0; j < 7; j++) {
			anIntArray1065[j] = -1;
			for (int k = 0; k < IDKType.anInt655; k++) {
				if (IDKType.aIDKTypeArray656[k].aBoolean662 || (IDKType.aIDKTypeArray656[k].anInt657 != (j + (aBoolean1047 ? 0 : 7)))) {
					continue;
				}
				anIntArray1065[j] = k;
				break;
			}
		}
	}

	public void method46(int i, Buffer buffer) {
		while ((buffer.bitPosition + 21) < (i * 8)) {
			int k = buffer.getN(14);
			if (k == 16383) {
				break;
			}
			if (aNpcArray835[k] == null) {
				aNpcArray835[k] = new NPCEntity();
			}
			NPCEntity npc = aNpcArray835[k];
			anIntArray837[anInt836++] = k;
			npc.anInt1537 = anInt1161;
			int l = buffer.getN(5);
			if (l > 15) {
				l -= 32;
			}
			int i1 = buffer.getN(5);
			if (i1 > 15) {
				i1 -= 32;
			}
			int j1 = buffer.getN(1);
			npc.aType_1696 = NPCType.method159(buffer.getN(12));
			int k1 = buffer.getN(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = k;
			}
			npc.anInt1540 = npc.aType_1696.aByte68;
			npc.anInt1504 = npc.aType_1696.anInt79;
			npc.anInt1554 = npc.aType_1696.anInt67;
			npc.anInt1555 = npc.aType_1696.anInt58;
			npc.anInt1556 = npc.aType_1696.anInt83;
			npc.anInt1557 = npc.aType_1696.anInt55;
			npc.anInt1511 = npc.aType_1696.anInt77;
			npc.method445(aPlayer_1126.anIntArray1500[0] + i1, aPlayer_1126.anIntArray1501[0] + l, j1 == 1);
		}
		buffer.accessBytes();
	}

	public void method47(boolean flag) {
		if (((aPlayer_1126.anInt1550 >> 7) == anInt1261) && ((aPlayer_1126.anInt1551 >> 7) == anInt1262)) {
			anInt1261 = 0;
		}
		int j = anInt891;
		if (flag) {
			j = 1;
		}
		for (int l = 0; l < j; l++) {
			PlayerEntity player;
			int i1;
			if (flag) {
				player = aPlayer_1126;
				i1 = anInt889 << 14;
			} else {
				player = aPlayerArray890[anIntArray892[l]];
				i1 = anIntArray892[l] << 14;
			}
			if ((player == null) || !player.method449()) {
				continue;
			}
			player.aBoolean1699 = ((aBoolean960 && (anInt891 > 50)) || (anInt891 > 200)) && !flag && (player.anInt1517 == player.anInt1511);
			int j1 = player.anInt1550 >> 7;
			int k1 = player.anInt1551 >> 7;
			if ((j1 < 0) || (j1 >= 104) || (k1 < 0) || (k1 >= 104)) {
				continue;
			}
			if ((player.aModel_1714 != null) && (anInt1161 >= player.anInt1707) && (anInt1161 < player.anInt1708)) {
				player.aBoolean1699 = false;
				player.anInt1709 = method42(anInt918, player.anInt1551, player.anInt1550);
				aGraph_946.method286(anInt918, player.anInt1551, player, player.anInt1552, player.anInt1722, player.anInt1550, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
				continue;
			}
			if (((player.anInt1550 & 0x7f) == 64) && ((player.anInt1551 & 0x7f) == 64)) {
				if (anIntArrayArray929[j1][k1] == anInt1265) {
					continue;
				}
				anIntArrayArray929[j1][k1] = anInt1265;
			}
			player.anInt1709 = method42(anInt918, player.anInt1551, player.anInt1550);
			aGraph_946.method285(anInt918, player.anInt1552, player.anInt1709, i1, player.anInt1551, 60, player.anInt1550, player, player.aBoolean1541);
		}
	}

	public boolean method48(Component component) {
		int j = component.anInt214;
		if (anInt900 == 2) {
			if (j == 201) {
				aBoolean1223 = true;
				anInt1225 = 0;
				aBoolean1256 = true;
				aString1212 = "";
				anInt1064 = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if (j == 202) {
				aBoolean1223 = true;
				anInt1225 = 0;
				aBoolean1256 = true;
				aString1212 = "";
				anInt1064 = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if (j == 205) {
			anInt1011 = 250;
			return true;
		}
		if (j == 501) {
			aBoolean1223 = true;
			anInt1225 = 0;
			aBoolean1256 = true;
			aString1212 = "";
			anInt1064 = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if (j == 502) {
			aBoolean1223 = true;
			anInt1225 = 0;
			aBoolean1256 = true;
			aString1212 = "";
			anInt1064 = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if ((j >= 300) && (j <= 313)) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = anIntArray1065[k];
			if (i2 != -1) {
				do {
					if ((j1 == 0) && (--i2 < 0)) {
						i2 = IDKType.anInt655 - 1;
					}
					if ((j1 == 1) && (++i2 >= IDKType.anInt655)) {
						i2 = 0;
					}
				} while (IDKType.aIDKTypeArray656[i2].aBoolean662 || (IDKType.aIDKTypeArray656[i2].anInt657 != (k + (aBoolean1047 ? 0 : 7))));
				anIntArray1065[k] = i2;
				aBoolean1031 = true;
			}
		}
		if ((j >= 314) && (j <= 323)) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if ((k1 == 0) && (--j2 < 0)) {
				j2 = anIntArrayArray1003[l].length - 1;
			}
			if ((k1 == 1) && (++j2 >= anIntArrayArray1003[l].length)) {
				j2 = 0;
			}
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if ((j == 324) && !aBoolean1047) {
			aBoolean1047 = true;
			method45();
		}
		if ((j == 325) && aBoolean1047) {
			aBoolean1047 = false;
			method45();
		}
		if (j == 326) {
			aBuffer_1192.putOp(101);
			aBuffer_1192.put8(aBoolean1047 ? 0 : 1);
			for (int i1 = 0; i1 < 7; i1++) {
				aBuffer_1192.put8(anIntArray1065[i1]);
			}
			for (int l1 = 0; l1 < 5; l1++) {
				aBuffer_1192.put8(anIntArray990[l1]);
			}
			return true;
		}
		if ((j >= 601) && (j <= 612)) {
			method147();
			if (aString881.length() > 0) {
				aBuffer_1192.putOp(218);
				aBuffer_1192.put64(StringUtil.toBase37(aString881));
				aBuffer_1192.put8(j - 601);
				aBuffer_1192.put8(aBoolean1158 ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			PlayerEntity player = aPlayerArray890[k];
			int l = buffer.getU8();
			if ((l & 0x40) != 0) {
				l += buffer.getU8() << 8;
			}
			method107(l, k, buffer, player);
		}
	}

	public void method50(int i, int k, int l, int i1, int j1) {
		int k1 = aGraph_946.method300(j1, l, i);
		if (k1 != 0) {
			int l1 = aGraph_946.method304(j1, l, i, k1);
			int k2 = (l1 >> 6) & 3;
			int i3 = l1 & 0x1f;
			int k3 = k;
			if (k1 > 0) {
				k3 = i1;
			}
			int[] ai = aImage_1263.anIntArray1439;
			int k4 = 24624 + (l * 4) + ((103 - i) * 512 * 4);
			int i5 = (k1 >> 14) & 0x7fff;
			LocType type_2 = LocType.method572(i5);
			if (type_2.anInt758 != -1) {
				Image8 class30_sub2_sub1_sub2_2 = aImageArray1060[type_2.anInt758];
				if (class30_sub2_sub1_sub2_2 != null) {
					int i6 = ((type_2.anInt744 * 4) - class30_sub2_sub1_sub2_2.anInt1452) / 2;
					int j6 = ((type_2.anInt761 * 4) - class30_sub2_sub1_sub2_2.anInt1453) / 2;
					class30_sub2_sub1_sub2_2.method361(48 + (l * 4) + i6, 48 + ((104 - i - type_2.anInt761) * 4) + j6);
				}
			} else {
				if ((i3 == 0) || (i3 == 2)) {
					if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 1) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 2) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
				}
				if (i3 == 3) {
					if (k2 == 0) {
						ai[k4] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
					} else if (k2 == 2) {
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
					}
				}
				if (i3 == 2) {
					if (k2 == 3) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 2) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
				}
			}
		}
		k1 = aGraph_946.method302(j1, l, i);
		if (k1 != 0) {
			int i2 = aGraph_946.method304(j1, l, i, k1);
			int l2 = (i2 >> 6) & 3;
			int j3 = i2 & 0x1f;
			int l3 = (k1 >> 14) & 0x7fff;
			LocType type_1 = LocType.method572(l3);
			if (type_1.anInt758 != -1) {
				Image8 class30_sub2_sub1_sub2_1 = aImageArray1060[type_1.anInt758];
				if (class30_sub2_sub1_sub2_1 != null) {
					int j5 = ((type_1.anInt744 * 4) - class30_sub2_sub1_sub2_1.anInt1452) / 2;
					int k5 = ((type_1.anInt761 * 4) - class30_sub2_sub1_sub2_1.anInt1453) / 2;
					class30_sub2_sub1_sub2_1.method361(48 + (l * 4) + j5, 48 + ((104 - i - type_1.anInt761) * 4) + k5);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (k1 > 0) {
					l4 = 0xee0000;
				}
				int[] ai1 = aImage_1263.anIntArray1439;
				int l5 = 24624 + (l * 4) + ((103 - i) * 512 * 4);
				if ((l2 == 0) || (l2 == 2)) {
					ai1[l5 + 1536] = l4;
					ai1[l5 + 1024 + 1] = l4;
					ai1[l5 + 512 + 2] = l4;
					ai1[l5 + 3] = l4;
				} else {
					ai1[l5] = l4;
					ai1[l5 + 512 + 1] = l4;
					ai1[l5 + 1024 + 2] = l4;
					ai1[l5 + 1536 + 3] = l4;
				}
			}
		}
		k1 = aGraph_946.method303(j1, l, i);
		if (k1 != 0) {
			int j2 = (k1 >> 14) & 0x7fff;
			LocType type = LocType.method572(j2);
			if (type.anInt758 != -1) {
				Image8 image = aImageArray1060[type.anInt758];
				if (image != null) {
					int i4 = ((type.anInt744 * 4) - image.anInt1452) / 2;
					int j4 = ((type.anInt761 * 4) - image.anInt1453) / 2;
					image.method361(48 + (l * 4) + i4, 48 + ((104 - i - type.anInt761) * 4) + j4);
				}
			}
		}
	}

	public void method51() {
		aImage_966 = new Image8(aArchive_1053, "titlebox", 0);
		aImage_967 = new Image8(aArchive_1053, "titlebutton", 0);
		aImageArray1152 = new Image8[12];
		int j = 0;
		try {
			j = Integer.parseInt(getParameter("fl_icon"));
		} catch (Exception ignored) {
		}
		if (j == 0) {
			for (int k = 0; k < 12; k++) {
				aImageArray1152[k] = new Image8(aArchive_1053, "runes", k);
			}
		} else {
			for (int l = 0; l < 12; l++) {
				aImageArray1152[l] = new Image8(aArchive_1053, "runes", 12 + (l & 3));
			}
		}
		aImage_1201 = new Image24(128, 265);
		aImage_1202 = new Image24(128, 265);
		for (int i1 = 0; i1 < 33920; i1++) {
			aImage_1201.anIntArray1439[i1] = aArea_1110.anIntArray315[i1];
		}
		for (int j1 = 0; j1 < 33920; j1++) {
			aImage_1202.anIntArray1439[j1] = aArea_1111.anIntArray315[j1];
		}
		anIntArray851 = new int[256];
		for (int k1 = 0; k1 < 64; k1++) {
			anIntArray851[k1] = k1 * 0x40000;
		}
		for (int l1 = 0; l1 < 64; l1++) {
			anIntArray851[l1 + 64] = 0xff0000 + (1024 * l1);
		}
		for (int i2 = 0; i2 < 64; i2++) {
			anIntArray851[i2 + 128] = 0xffff00 + (4 * i2);
		}
		for (int j2 = 0; j2 < 64; j2++) {
			anIntArray851[j2 + 192] = 0xffffff;
		}
		anIntArray852 = new int[256];
		for (int k2 = 0; k2 < 64; k2++) {
			anIntArray852[k2] = k2 * 1024;
		}
		for (int l2 = 0; l2 < 64; l2++) {
			anIntArray852[l2 + 64] = 65280 + (4 * l2);
		}
		for (int i3 = 0; i3 < 64; i3++) {
			anIntArray852[i3 + 128] = 65535 + (0x40000 * i3);
		}
		for (int j3 = 0; j3 < 64; j3++) {
			anIntArray852[j3 + 192] = 0xffffff;
		}
		anIntArray853 = new int[256];
		for (int k3 = 0; k3 < 64; k3++) {
			anIntArray853[k3] = k3 * 4;
		}
		for (int l3 = 0; l3 < 64; l3++) {
			anIntArray853[l3 + 64] = 255 + (0x40000 * l3);
		}
		for (int i4 = 0; i4 < 64; i4++) {
			anIntArray853[i4 + 128] = 0xff00ff + (1024 * i4);
		}
		for (int j4 = 0; j4 < 64; j4++) {
			anIntArray853[j4 + 192] = 0xffffff;
		}
		anIntArray850 = new int[256];
		anIntArray1190 = new int[32768];
		anIntArray1191 = new int[32768];
		method106(null);
		anIntArray828 = new int[32768];
		anIntArray829 = new int[32768];
		method13(10, "Connecting to fileserver");
		if (!aBoolean831) {
			aBoolean880 = true;
			aBoolean831 = true;
			method12(this, 2);
		}
	}

	public void method53() {
		if (aBoolean960 && (anInt1023 == 2) && (SceneBuilder.anInt131 != anInt918)) {
			aArea_1165.method237();
			aFont_1271.method381(0, "Loading - please wait.", 151, 257);
			aFont_1271.method381(0xffffff, "Loading - please wait.", 150, 256);
			aArea_1165.method238(4, super.aGraphics12, 4);
			anInt1023 = 1;
			aLong824 = System.currentTimeMillis();
		}
		if (anInt1023 == 1) {
			int j = method54();
			if ((j != 0) && ((System.currentTimeMillis() - aLong824) > 0x57e40L)) {
				Signlink.reporterror(aString1173 + " glcfb " + aLong1215 + "," + j + "," + aBoolean960 + "," + aFileStoreArray970[0] + "," + aOnDemand_1068.method552() + "," + anInt918 + "," + anInt1069 + "," + anInt1070);
				aLong824 = System.currentTimeMillis();
			}
		}
		if ((anInt1023 == 2) && (anInt918 != anInt985)) {
			anInt985 = anInt918;
			method24(anInt918);
		}
	}

	public int method54() {
		for (int i = 0; i < aByteArrayArray1183.length; i++) {
			if ((aByteArrayArray1183[i] == null) && (anIntArray1235[i] != -1)) {
				return -1;
			}
			if ((aByteArrayArray1247[i] == null) && (anIntArray1236[i] != -1)) {
				return -2;
			}
		}
		boolean flag = true;
		for (int j = 0; j < aByteArrayArray1183.length; j++) {
			byte[] abyte0 = aByteArrayArray1247[j];
			if (abyte0 != null) {
				int k = ((anIntArray1234[j] >> 8) * 64) - anInt1034;
				int l = ((anIntArray1234[j] & 0xff) * 64) - anInt1035;
				if (aBoolean1159) {
					k = 10;
					l = 10;
				}
				flag &= SceneBuilder.method189(k, abyte0, l);
			}
		}
		if (!flag) {
			return -3;
		}
		if (aBoolean1080) {
			return -4;
		} else {
			anInt1023 = 2;
			SceneBuilder.anInt131 = anInt918;
			method22();
			aBuffer_1192.putOp(121);
			return 0;
		}
	}

	public void method55() {
		for (ProjectileEntity projectile = (ProjectileEntity) aList_1013.method252(); projectile != null; projectile = (ProjectileEntity) aList_1013.method254()) {
			if ((projectile.anInt1597 != anInt918) || (anInt1161 > projectile.anInt1572)) {
				projectile.method329();
			} else if (anInt1161 >= projectile.anInt1571) {
				if (projectile.anInt1590 > 0) {
					NPCEntity npc = aNpcArray835[projectile.anInt1590 - 1];
					if ((npc != null) && (npc.anInt1550 >= 0) && (npc.anInt1550 < 13312) && (npc.anInt1551 >= 0) && (npc.anInt1551 < 13312)) {
						projectile.method455(anInt1161, npc.anInt1551, method42(projectile.anInt1597, npc.anInt1551, npc.anInt1550) - projectile.anInt1583, npc.anInt1550);
					}
				}
				if (projectile.anInt1590 < 0) {
					int j = -projectile.anInt1590 - 1;
					PlayerEntity player;
					if (j == anInt884) {
						player = aPlayer_1126;
					} else {
						player = aPlayerArray890[j];
					}
					if ((player != null) && (player.anInt1550 >= 0) && (player.anInt1550 < 13312) && (player.anInt1551 >= 0) && (player.anInt1551 < 13312)) {
						projectile.method455(anInt1161, player.anInt1551, method42(projectile.anInt1597, player.anInt1551, player.anInt1550) - projectile.anInt1583, player.anInt1550);
					}
				}
				projectile.method456(anInt945);
				aGraph_946.method285(anInt918, projectile.anInt1595, (int) projectile.aDouble1587, -1, (int) projectile.aDouble1586, 60, (int) projectile.aDouble1585, projectile, false);
			}
		}
	}

	public void method56() {
		byte[] abyte0 = aArchive_1053.read("title.dat", null);
		Image24 image = new Image24(abyte0, this);
		aArea_1110.method237();
		image.method346(0, 0);
		aArea_1111.method237();
		image.method346(-637, 0);
		aArea_1107.method237();
		image.method346(-128, 0);
		aArea_1108.method237();
		image.method346(-202, -371);
		aArea_1109.method237();
		image.method346(-202, -171);
		aArea_1112.method237();
		image.method346(0, -265);
		aArea_1113.method237();
		image.method346(-562, -265);
		aArea_1114.method237();
		image.method346(-128, -171);
		aArea_1115.method237();
		image.method346(-562, -171);
		int[] ai = new int[image.anInt1440];
		for (int j = 0; j < image.anInt1441; j++) {
			for (int k = 0; k < image.anInt1440; k++) {
				ai[k] = image.anIntArray1439[(image.anInt1440 - k - 1) + (image.anInt1440 * j)];
			}
			for (int l = 0; l < image.anInt1440; l++) {
				image.anIntArray1439[l + (image.anInt1440 * j)] = ai[l];
			}
		}
		aArea_1110.method237();
		image.method346(382, 0);
		aArea_1111.method237();
		image.method346(-255, 0);
		aArea_1107.method237();
		image.method346(254, 0);
		aArea_1108.method237();
		image.method346(180, -371);
		aArea_1109.method237();
		image.method346(180, -171);
		aArea_1112.method237();
		image.method346(382, -265);
		aArea_1113.method237();
		image.method346(-180, -265);
		aArea_1114.method237();
		image.method346(254, -171);
		aArea_1115.method237();
		image.method346(-180, -171);
		image = new Image24(aArchive_1053, "logo", 0);
		aArea_1107.method237();
		image.method348(382 - (image.anInt1440 / 2) - 128, 18);
		System.gc();
	}

	public void method57() {
		do {
			OnDemandRequest request;
			do {
				request = aOnDemand_1068.method561();
				if (request == null) {
					return;
				}
				if (request.anInt1419 == 0) {
					Model.load(request.aByteArray1420, request.anInt1421);
					if ((aOnDemand_1068.method559(request.anInt1421) & 0x62) != 0) {
						aBoolean1153 = true;
						if (anInt1276 != -1) {
							aBoolean1223 = true;
						}
					}
				}
				if ((request.anInt1419 == 1) && (request.aByteArray1420 != null)) {
					SeqFrame.readAnimation(request.aByteArray1420);
				}
				if ((request.anInt1419 == 2) && (request.anInt1421 == anInt1227) && (request.aByteArray1420 != null)) {
					method21(aBoolean1228, request.aByteArray1420);
				}
				if ((request.anInt1419 == 3) && (anInt1023 == 1)) {
					for (int i = 0; i < aByteArrayArray1183.length; i++) {
						if (anIntArray1235[i] == request.anInt1421) {
							aByteArrayArray1183[i] = request.aByteArray1420;
							if (request.aByteArray1420 == null) {
								anIntArray1235[i] = -1;
							}
							break;
						}
						if (anIntArray1236[i] != request.anInt1421) {
							continue;
						}
						aByteArrayArray1247[i] = request.aByteArray1420;
						if (request.aByteArray1420 == null) {
							anIntArray1236[i] = -1;
						}
						break;
					}
				}
			} while ((request.anInt1419 != 93) || !aOnDemand_1068.method564(request.anInt1421));
			SceneBuilder.method173(new Buffer(request.aByteArray1420), aOnDemand_1068);
		} while (true);
	}

	public void method58() {
		char c = '\u0100';
		for (int j = 10; j < 117; j++) {
			int k = (int) (Math.random() * 100D);
			if (k < 50) {
				anIntArray828[j + ((c - 2) << 7)] = 255;
			}
		}
		for (int l = 0; l < 100; l++) {
			int i1 = (int) (Math.random() * 124D) + 2;
			int k1 = (int) (Math.random() * 128D) + 128;
			int k2 = i1 + (k1 << 7);
			anIntArray828[k2] = 192;
		}
		for (int j1 = 1; j1 < (c - 1); j1++) {
			for (int l1 = 1; l1 < 127; l1++) {
				int l2 = l1 + (j1 << 7);
				anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128] + anIntArray828[l2 + 128]) / 4;
			}
		}
		anInt1275 += 128;
		if (anInt1275 > anIntArray1190.length) {
			anInt1275 -= anIntArray1190.length;
			int i2 = (int) (Math.random() * 12D);
			method106(aImageArray1152[i2]);
		}
		for (int j2 = 1; j2 < (c - 1); j2++) {
			for (int i3 = 1; i3 < 127; i3++) {
				int k3 = i3 + (j2 << 7);
				int i4 = anIntArray829[k3 + 128] - (anIntArray1190[(k3 + anInt1275) & (anIntArray1190.length - 1)] / 5);
				if (i4 < 0) {
					i4 = 0;
				}
				anIntArray828[k3] = i4;
			}
		}
		for (int j3 = 0; j3 < (c - 1); j3++) {
			anIntArray969[j3] = anIntArray969[j3 + 1];
		}
		anIntArray969[c - 1] = (int) ((Math.sin((double) anInt1161 / 14D) * 16D) + (Math.sin((double) anInt1161 / 15D) * 14D) + (Math.sin((double) anInt1161 / 16D) * 12D));
		if (anInt1040 > 0) {
			anInt1040 -= 4;
		}
		if (anInt1041 > 0) {
			anInt1041 -= 4;
		}
		if ((anInt1040 == 0) && (anInt1041 == 0)) {
			int l3 = (int) (Math.random() * 2000D);
			if (l3 == 0) {
				anInt1040 = 1024;
			}
			if (l3 == 1) {
				anInt1041 = 1024;
			}
		}
	}

	public boolean method59(byte[] abyte0, int i) {
		if (abyte0 == null) {
			return true;
		} else {
			return Signlink.wavesave(abyte0, i);
		}
	}

	public void method60(int i) {
		Component component = Component.aComponentArray210[i];
		for (int j = 0; j < component.anIntArray240.length; j++) {
			if (component.anIntArray240[j] == -1) {
				break;
			}
			Component component_1 = Component.aComponentArray210[component.anIntArray240[j]];
			if (component_1.anInt262 == 1) {
				method60(component_1.anInt250);
			}
			component_1.anInt246 = 0;
			component_1.anInt208 = 0;
		}
	}

	public void method61() {
		if (anInt855 != 2) {
			return;
		}
		method128(((anInt934 - anInt1034) << 7) + anInt937, anInt936 * 2, ((anInt935 - anInt1035) << 7) + anInt938);
		if ((anInt963 > -1) && ((anInt1161 % 20) < 10)) {
			aImageArray1095[2].method348(anInt963 - 12, anInt964 - 28);
		}
	}

	public void method62() {
		if (anInt1104 > 1) {
			anInt1104--;
		}
		if (anInt1011 > 0) {
			anInt1011--;
		}
		for (int j = 0; j < 5; j++) {
			if (!method145()) {
				break;
			}
		}
		if (!aBoolean1157) {
			return;
		}
		synchronized (aMouseRecorder_879.lock) {
			if (aBoolean1205) {
				if ((super.anInt26 != 0) || (aMouseRecorder_879.anInt810 >= 40)) {
					aBuffer_1192.putOp(45);
					aBuffer_1192.put8(0);
					int j2 = aBuffer_1192.position;
					int j3 = 0;
					for (int j4 = 0; j4 < aMouseRecorder_879.anInt810; j4++) {
						if ((j2 - aBuffer_1192.position) >= 240) {
							break;
						}
						j3++;
						int l4 = aMouseRecorder_879.anIntArray807[j4];
						if (l4 < 0) {
							l4 = 0;
						} else if (l4 > 502) {
							l4 = 502;
						}
						int k5 = aMouseRecorder_879.anIntArray809[j4];
						if (k5 < 0) {
							k5 = 0;
						} else if (k5 > 764) {
							k5 = 764;
						}
						int i6 = (l4 * 765) + k5;
						if ((aMouseRecorder_879.anIntArray807[j4] == -1) && (aMouseRecorder_879.anIntArray809[j4] == -1)) {
							k5 = -1;
							l4 = -1;
							i6 = 0x7ffff;
						}
						if ((k5 == anInt1237) && (l4 == anInt1238)) {
							if (anInt1022 < 2047) {
								anInt1022++;
							}
						} else {
							int j6 = k5 - anInt1237;
							anInt1237 = k5;
							int k6 = l4 - anInt1238;
							anInt1238 = l4;
							if ((anInt1022 < 8) && (j6 >= -32) && (j6 <= 31) && (k6 >= -32) && (k6 <= 31)) {
								j6 += 32;
								k6 += 32;
								aBuffer_1192.put16((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else if (anInt1022 < 8) {
								aBuffer_1192.put24(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else {
								aBuffer_1192.put32(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}
					aBuffer_1192.putSize8(aBuffer_1192.position - j2);
					if (j3 >= aMouseRecorder_879.anInt810) {
						aMouseRecorder_879.anInt810 = 0;
					} else {
						aMouseRecorder_879.anInt810 -= j3;
						for (int i5 = 0; i5 < aMouseRecorder_879.anInt810; i5++) {
							aMouseRecorder_879.anIntArray809[i5] = aMouseRecorder_879.anIntArray809[i5 + j3];
							aMouseRecorder_879.anIntArray807[i5] = aMouseRecorder_879.anIntArray807[i5 + j3];
						}
					}
				}
			} else {
				aMouseRecorder_879.anInt810 = 0;
			}
		}
		if (super.anInt26 != 0) {
			long l = (super.aLong29 - aLong1220) / 50L;
			if (l > 4095L) {
				l = 4095L;
			}
			aLong1220 = super.aLong29;
			int k2 = super.anInt28;
			if (k2 < 0) {
				k2 = 0;
			} else if (k2 > 502) {
				k2 = 502;
			}
			int k3 = super.anInt27;
			if (k3 < 0) {
				k3 = 0;
			} else if (k3 > 764) {
				k3 = 764;
			}
			int k4 = (k2 * 765) + k3;
			int j5 = 0;
			if (super.anInt26 == 2) {
				j5 = 1;
			}
			int l5 = (int) l;
			aBuffer_1192.putOp(241);
			aBuffer_1192.put32((l5 << 20) + (j5 << 19) + k4);
		}
		if (anInt1016 > 0) {
			anInt1016--;
		}
		if ((super.anIntArray30[1] == 1) || (super.anIntArray30[2] == 1) || (super.anIntArray30[3] == 1) || (super.anIntArray30[4] == 1)) {
			aBoolean1017 = true;
		}
		if (aBoolean1017 && (anInt1016 <= 0)) {
			anInt1016 = 20;
			aBoolean1017 = false;
			aBuffer_1192.putOp(86);
			aBuffer_1192.put16(anInt1184);
			aBuffer_1192.put16A(anInt1185);
		}
		if (super.aBoolean17 && !aBoolean954) {
			aBoolean954 = true;
			aBuffer_1192.putOp(3);
			aBuffer_1192.put8(1);
		}
		if (!super.aBoolean17 && aBoolean954) {
			aBoolean954 = false;
			aBuffer_1192.putOp(3);
			aBuffer_1192.put8(0);
		}
		method53();
		method115();
		method90();
		anInt1009++;
		if (anInt1009 > 750) {
			method68();
		}
		method114();
		method95();
		method38();
		anInt945++;
		if (anInt917 != 0) {
			anInt916 += 20;
			if (anInt916 >= 400) {
				anInt917 = 0;
			}
		}
		if (anInt1246 != 0) {
			anInt1243++;
			if (anInt1243 >= 15) {
				if (anInt1246 == 2) {
					aBoolean1153 = true;
				}
				if (anInt1246 == 3) {
					aBoolean1223 = true;
				}
				anInt1246 = 0;
			}
		}
		if (anInt1086 != 0) {
			anInt989++;
			if ((super.anInt20 > (anInt1087 + 5)) || (super.anInt20 < (anInt1087 - 5)) || (super.anInt21 > (anInt1088 + 5)) || (super.anInt21 < (anInt1088 - 5))) {
				aBoolean1242 = true;
			}
			if (super.anInt19 == 0) {
				if (anInt1086 == 2) {
					aBoolean1153 = true;
				}
				if (anInt1086 == 3) {
					aBoolean1223 = true;
				}
				anInt1086 = 0;
				if (aBoolean1242 && (anInt989 >= 5)) {
					anInt1067 = -1;
					method82();
					if ((anInt1067 == anInt1084) && (anInt1066 != anInt1085)) {
						Component component = Component.aComponentArray210[anInt1084];
						int j1 = 0;
						if ((anInt913 == 1) && (component.anInt214 == 206)) {
							j1 = 1;
						}
						if (component.anIntArray253[anInt1066] <= 0) {
							j1 = 0;
						}
						if (component.aBoolean235) {
							int l2 = anInt1085;
							int l3 = anInt1066;
							component.anIntArray253[l3] = component.anIntArray253[l2];
							component.anIntArray252[l3] = component.anIntArray252[l2];
							component.anIntArray253[l2] = -1;
							component.anIntArray252[l2] = 0;
						} else if (j1 == 1) {
							int i3 = anInt1085;
							for (int i4 = anInt1066; i3 != i4; ) {
								if (i3 > i4) {
									component.method204(i3, i3 - 1);
									i3--;
								} else if (i3 < i4) {
									component.method204(i3, i3 + 1);
									i3++;
								}
							}
						} else {
							component.method204(anInt1085, anInt1066);
						}
						aBuffer_1192.putOp(214);
						aBuffer_1192.put16LEA(anInt1084);
						aBuffer_1192.put8C(j1);
						aBuffer_1192.put16LEA(anInt1085);
						aBuffer_1192.put16LE(anInt1066);
					}
				} else if (((anInt1253 == 1) || method17(anInt1133 - 1)) && (anInt1133 > 2)) {
					method116();
				} else if (anInt1133 > 0) {
					method69(anInt1133 - 1);
				}
				anInt1243 = 10;
				super.anInt26 = 0;
			}
		}
		if (SceneGraph.anInt470 != -1) {
			int k = SceneGraph.anInt470;
			int k1 = SceneGraph.anInt471;
			boolean flag = method85(0, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k1, aPlayer_1126.anIntArray1500[0], true, k);
			SceneGraph.anInt470 = -1;
			if (flag) {
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 1;
				anInt916 = 0;
			}
		}
		if ((super.anInt26 == 1) && (aString844 != null)) {
			aString844 = null;
			aBoolean1223 = true;
			super.anInt26 = 0;
		}
		method20();
		method92();
		method78();
		method32();
		if ((super.anInt19 == 1) || (super.anInt26 == 1)) {
			anInt1213++;
		}
		if (anInt1023 == 2) {
			method108();
		}
		if ((anInt1023 == 2) && aBoolean1160) {
			method39();
		}
		for (int i1 = 0; i1 < 5; i1++) {
			anIntArray1030[i1]++;
		}
		method73();
		super.anInt18++;
		if (super.anInt18 > 4500) {
			anInt1011 = 250;
			super.anInt18 -= 500;
			aBuffer_1192.putOp(202);
		}
		anInt1010++;
		if (anInt1010 > 50) {
			aBuffer_1192.putOp(0);
		}
		try {
			if ((aConnection_1168 != null) && (aBuffer_1192.position > 0)) {
				aConnection_1168.method271(aBuffer_1192.position, aBuffer_1192.data, 0);
				aBuffer_1192.position = 0;
				anInt1010 = 0;
			}
		} catch (IOException _ex) {
			method68();
		} catch (Exception exception) {
			method44();
		}
	}

	public void method63() {
		SceneLocTemporary loc = (SceneLocTemporary) aList_1179.method252();
		for (; loc != null; loc = (SceneLocTemporary) aList_1179.method254()) {
			if (loc.anInt1294 == -1) {
				loc.anInt1302 = 0;
				method89(loc);
			} else {
				loc.method329();
			}
		}
	}

	public void method64() {
		if (aArea_1107 != null) {
			return;
		}
		aArea_1166 = null;
		aArea_1164 = null;
		aArea_1163 = null;
		aArea_1165 = null;
		aArea_1123 = null;
		aArea_1124 = null;
		aArea_1125 = null;
		aArea_1110 = new DrawArea(128, 265, method11());
		Draw2D.clear();
		aArea_1111 = new DrawArea(128, 265, method11());
		Draw2D.clear();
		aArea_1107 = new DrawArea(509, 171, method11());
		Draw2D.clear();
		aArea_1108 = new DrawArea(360, 132, method11());
		Draw2D.clear();
		aArea_1109 = new DrawArea(360, 200, method11());
		Draw2D.clear();
		aArea_1112 = new DrawArea(202, 238, method11());
		Draw2D.clear();
		aArea_1113 = new DrawArea(203, 238, method11());
		Draw2D.clear();
		aArea_1114 = new DrawArea(74, 94, method11());
		Draw2D.clear();
		aArea_1115 = new DrawArea(75, 94, method11());
		Draw2D.clear();
		if (aArchive_1053 != null) {
			method56();
			method51();
		}
		aBoolean1255 = true;
	}

	public void method65(int i, int j, int k, int l, Component component, int i1, boolean flag, int j1) {
		if (aBoolean972) {
			anInt992 = 32;
		} else {
			anInt992 = 0;
		}
		aBoolean972 = false;
		if ((k >= i) && (k < (i + 16)) && (l >= i1) && (l < (i1 + 16))) {
			component.anInt224 -= anInt1213 * 4;
			if (flag) {
				aBoolean1153 = true;
			}
		} else if ((k >= i) && (k < (i + 16)) && (l >= ((i1 + j) - 16)) && (l < (i1 + j))) {
			component.anInt224 += anInt1213 * 4;
			if (flag) {
				aBoolean1153 = true;
			}
		} else if ((k >= (i - anInt992)) && (k < (i + 16 + anInt992)) && (l >= (i1 + 16)) && (l < ((i1 + j) - 16)) && (anInt1213 > 0)) {
			int l1 = ((j - 32) * j) / j1;
			if (l1 < 8) {
				l1 = 8;
			}
			int i2 = l - i1 - 16 - (l1 / 2);
			int j2 = j - 32 - l1;
			component.anInt224 = ((j1 - j) * i2) / j2;
			if (flag) {
				aBoolean1153 = true;
			}
			aBoolean972 = true;
		}
	}

	public boolean method66(int i, int j, int k) {
		int i1 = (i >> 14) & 0x7fff;
		int j1 = aGraph_946.method304(anInt918, k, j, i);
		if (j1 == -1) {
			return false;
		}
		int k1 = j1 & 0x1f;
		int l1 = (j1 >> 6) & 3;
		if ((k1 == 10) || (k1 == 11) || (k1 == 22)) {
			LocType type = LocType.method572(i1);
			int i2;
			int j2;
			if ((l1 == 0) || (l1 == 2)) {
				i2 = type.anInt744;
				j2 = type.anInt761;
			} else {
				i2 = type.anInt761;
				j2 = type.anInt744;
			}
			int k2 = type.anInt768;
			if (l1 != 0) {
				k2 = ((k2 << l1) & 0xf) + (k2 >> (4 - l1));
			}
			method85(2, 0, j2, 0, aPlayer_1126.anIntArray1501[0], i2, k2, j, aPlayer_1126.anIntArray1500[0], false, k);
		} else {
			method85(2, l1, 0, k1 + 1, aPlayer_1126.anIntArray1501[0], 0, 0, j, aPlayer_1126.anIntArray1500[0], false, k);
		}
		anInt914 = super.anInt27;
		anInt915 = super.anInt28;
		anInt917 = 2;
		anInt916 = 0;
		return true;
	}

	public FileArchive method67(int i, String s, String s1, int j, int k) {
		byte[] abyte0 = null;
		int l = 5;
		try {
			if (aFileStoreArray970[0] != null) {
				abyte0 = aFileStoreArray970[0].method233(i);
			}
		} catch (Exception ignored) {
		}
		if (abyte0 != null) {
			aCRC32_930.reset();
			aCRC32_930.update(abyte0);
			int i1 = (int) aCRC32_930.getValue();
			if (i1 != j) {
				abyte0 = null;
			}
		}
		if (abyte0 != null) {
			return new FileArchive(abyte0);
		}
		int j1 = 0;
		while (abyte0 == null) {
			String s2 = "Unknown error";
			method13(k, "Requesting " + s);
			try {
				int k1 = 0;
				DataInputStream datainputstream = method132(s1 + j);
				byte[] abyte1 = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Buffer buffer = new Buffer(abyte1);
				buffer.position = 3;
				int i2 = buffer.get24() + 6;
				int j2 = 6;
				abyte0 = new byte[i2];
				for (int k2 = 0; k2 < 6; k2++) {
					abyte0[k2] = abyte1[k2];
				}
				while (j2 < i2) {
					int l2 = i2 - j2;
					if (l2 > 1000) {
						l2 = 1000;
					}
					int j3 = datainputstream.read(abyte0, j2, l2);
					if (j3 < 0) {
						s2 = "Length error: " + j2 + "/" + i2;
						throw new IOException("EOF");
					}
					j2 += j3;
					int k3 = (j2 * 100) / i2;
					if (k3 != k1) {
						method13(k, "Loading " + s + " - " + k3 + "%");
					}
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (aFileStoreArray970[0] != null) {
						aFileStoreArray970[0].method234(abyte0.length, abyte0, i);
					}
				} catch (Exception _ex) {
					aFileStoreArray970[0] = null;
				}
				if (abyte0 != null) {
					aCRC32_930.reset();
					aCRC32_930.update(abyte0);
					int i3 = (int) aCRC32_930.getValue();
					if (i3 != j) {
						abyte0 = null;
						j1++;
						s2 = "Checksum error: " + i3;
					}
				}
			} catch (IOException ioexception) {
				if (s2.equals("Unknown error")) {
					s2 = "Connection error";
				}
				abyte0 = null;
			} catch (NullPointerException _ex) {
				s2 = "Null error";
				abyte0 = null;
				if (!Signlink.reporterror) {
					return null;
				}
			} catch (ArrayIndexOutOfBoundsException _ex) {
				s2 = "Bounds error";
				abyte0 = null;
				if (!Signlink.reporterror) {
					return null;
				}
			} catch (Exception _ex) {
				s2 = "Unexpected error";
				abyte0 = null;
				if (!Signlink.reporterror) {
					return null;
				}
			}
			if (abyte0 == null) {
				for (int l1 = l; l1 > 0; l1--) {
					if (j1 >= 3) {
						method13(k, "Game updated - please reload page");
						l1 = 10;
					} else {
						method13(k, s2 + " - Retrying in " + l1);
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception ignored) {
					}
				}
				l *= 2;
				if (l > 60) {
					l = 60;
				}
				aBoolean872 = !aBoolean872;
			}
		}
		return new FileArchive(abyte0);
	}

	public void method68() {
		if (anInt1011 > 0) {
			method44();
			return;
		}
		aArea_1165.method237();
		aFont_1271.method381(0, "Connection lost", 144, 257);
		aFont_1271.method381(0xffffff, "Connection lost", 143, 256);
		aFont_1271.method381(0, "Please wait - attempting to reestablish", 159, 257);
		aFont_1271.method381(0xffffff, "Please wait - attempting to reestablish", 158, 256);
		aArea_1165.method238(4, super.aGraphics12, 4);
		anInt1021 = 0;
		anInt1261 = 0;
		Connection connection = aConnection_1168;
		aBoolean1157 = false;
		anInt1038 = 0;
		method84(aString1173, aString1174, true);
		if (!aBoolean1157) {
			method44();
		}
		try {
			connection.method267();
		} catch (Exception ignored) {
		}
	}

	public void method69(int i) {
		if (i < 0) {
			return;
		}
		if (anInt1225 != 0) {
			anInt1225 = 0;
			aBoolean1223 = true;
		}
		int j = anIntArray1091[i];
		int k = anIntArray1092[i];
		int l = anIntArray1093[i];
		int i1 = anIntArray1094[i];
		if (l >= 2000) {
			l -= 2000;
		}
		if (l == 582) {
			NPCEntity npc = aNpcArray835[i1];
			if (npc != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, npc.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, npc.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(57);
				aBuffer_1192.put16A(anInt1285);
				aBuffer_1192.put16A(i1);
				aBuffer_1192.put16LE(anInt1283);
				aBuffer_1192.put16A(anInt1284);
			}
		}
		if (l == 234) {
			boolean flag1 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag1) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(236);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16(i1);
			aBuffer_1192.put16LE(j + anInt1034);
		}
		if ((l == 62) && method66(i1, k, j)) {
			aBuffer_1192.putOp(192);
			aBuffer_1192.put16(anInt1284);
			aBuffer_1192.put16LE((i1 >> 14) & 0x7fff);
			aBuffer_1192.put16LEA(k + anInt1035);
			aBuffer_1192.put16LE(anInt1283);
			aBuffer_1192.put16LEA(j + anInt1034);
			aBuffer_1192.put16(anInt1285);
		}
		if (l == 511) {
			boolean flag2 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag2) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(25);
			aBuffer_1192.put16LE(anInt1284);
			aBuffer_1192.put16A(anInt1285);
			aBuffer_1192.put16(i1);
			aBuffer_1192.put16A(k + anInt1035);
			aBuffer_1192.put16LEA(anInt1283);
			aBuffer_1192.put16(j + anInt1034);
		}
		if (l == 74) {
			aBuffer_1192.putOp(122);
			aBuffer_1192.put16LEA(k);
			aBuffer_1192.put16A(j);
			aBuffer_1192.put16LE(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 315) {
			Component component = Component.aComponentArray210[k];
			boolean flag8 = true;
			if (component.anInt214 > 0) {
				flag8 = method48(component);
			}
			if (flag8) {
				aBuffer_1192.putOp(185);
				aBuffer_1192.put16(k);
			}
		}
		if (l == 561) {
			PlayerEntity player = aPlayerArray890[i1];
			if (player != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, player.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, player.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(128);
				aBuffer_1192.put16(i1);
			}
		}
		if (l == 20) {
			NPCEntity class30_sub2_sub4_sub1_sub1_1 = aNpcArray835[i1];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub1_1.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(155);
				aBuffer_1192.put16LE(i1);
			}
		}
		if (l == 779) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_1 = aPlayerArray890[i1];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub2_1.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(153);
				aBuffer_1192.put16LE(i1);
			}
		}
		if (l == 516) {
			if (!aBoolean885) {
				aGraph_946.method312(super.anInt28 - 4, super.anInt27 - 4);
			} else {
				aGraph_946.method312(k - 4, j - 4);
			}
		}
		if (l == 1062) {
			method66(i1, k, j);
			aBuffer_1192.putOp(228);
			aBuffer_1192.put16A((i1 >> 14) & 0x7fff);
			aBuffer_1192.put16A(k + anInt1035);
			aBuffer_1192.put16(j + anInt1034);
		}
		if ((l == 679) && !aBoolean1149) {
			aBuffer_1192.putOp(40);
			aBuffer_1192.put16(k);
			aBoolean1149 = true;
		}
		if (l == 431) {
			aBuffer_1192.putOp(129);
			aBuffer_1192.put16A(j);
			aBuffer_1192.put16(k);
			aBuffer_1192.put16A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if ((l == 337) || (l == 42) || (l == 792) || (l == 322)) {
			String s = aStringArray1199[i];
			int k1 = s.indexOf("@whi@");
			if (k1 != -1) {
				long l3 = StringUtil.toBase37(s.substring(k1 + 5).trim());
				if (l == 337) {
					method41(l3);
				}
				if (l == 42) {
					method113(l3);
				}
				if (l == 792) {
					method35(l3);
				}
				if (l == 322) {
					method122(l3);
				}
			}
		}
		if (l == 53) {
			aBuffer_1192.putOp(135);
			aBuffer_1192.put16LE(j);
			aBuffer_1192.put16A(k);
			aBuffer_1192.put16LE(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 539) {
			aBuffer_1192.putOp(16);
			aBuffer_1192.put16A(i1);
			aBuffer_1192.put16LEA(j);
			aBuffer_1192.put16LEA(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if ((l == 484) || (l == 6)) {
			String s1 = aStringArray1199[i];
			int l1 = s1.indexOf("@whi@");
			if (l1 != -1) {
				s1 = s1.substring(l1 + 5).trim();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(StringUtil.toBase37(s1)));
				boolean flag9 = false;
				for (int j3 = 0; j3 < anInt891; j3++) {
					PlayerEntity player_7 = aPlayerArray890[anIntArray892[j3]];
					if ((player_7 == null) || (player_7.aString1703 == null) || !player_7.aString1703.equalsIgnoreCase(s7)) {
						continue;
					}
					method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, player_7.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, player_7.anIntArray1500[0]);
					if (l == 484) {
						aBuffer_1192.putOp(139);
						aBuffer_1192.put16LE(anIntArray892[j3]);
					}
					if (l == 6) {
						aBuffer_1192.putOp(128);
						aBuffer_1192.put16(anIntArray892[j3]);
					}
					flag9 = true;
					break;
				}
				if (!flag9) {
					method77("Unable to find " + s7, 0, "");
				}
			}
		}
		if (l == 870) {
			aBuffer_1192.putOp(53);
			aBuffer_1192.put16(j);
			aBuffer_1192.put16A(anInt1283);
			aBuffer_1192.put16LEA(i1);
			aBuffer_1192.put16(anInt1284);
			aBuffer_1192.put16LE(anInt1285);
			aBuffer_1192.put16(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 847) {
			aBuffer_1192.putOp(87);
			aBuffer_1192.put16A(i1);
			aBuffer_1192.put16(k);
			aBuffer_1192.put16A(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 626) {
			Component component_1 = Component.aComponentArray210[k];
			anInt1136 = 1;
			anInt1137 = k;
			anInt1138 = component_1.anInt237;
			anInt1282 = 0;
			aBoolean1153 = true;
			String s4 = component_1.aString222;
			if (s4.contains(" ")) {
				s4 = s4.substring(0, s4.indexOf(" "));
			}
			String s8 = component_1.aString222;
			if (s8.contains(" ")) {
				s8 = s8.substring(s8.indexOf(" ") + 1);
			}
			aString1139 = s4 + " " + component_1.aString218 + " " + s8;
			if (anInt1138 == 16) {
				aBoolean1153 = true;
				anInt1221 = 3;
				aBoolean1103 = true;
			}
			return;
		}
		if (l == 78) {
			aBuffer_1192.putOp(117);
			aBuffer_1192.put16LEA(k);
			aBuffer_1192.put16LEA(i1);
			aBuffer_1192.put16LE(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 27) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_2 = aPlayerArray890[i1];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub2_2.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(73);
				aBuffer_1192.put16LE(i1);
			}
		}
		if (l == 213) {
			boolean flag3 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag3) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(79);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16(i1);
			aBuffer_1192.put16A(j + anInt1034);
		}
		if (l == 632) {
			aBuffer_1192.putOp(145);
			aBuffer_1192.put16A(k);
			aBuffer_1192.put16A(j);
			aBuffer_1192.put16A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 493) {
			aBuffer_1192.putOp(75);
			aBuffer_1192.put16LEA(k);
			aBuffer_1192.put16LE(j);
			aBuffer_1192.put16A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 652) {
			boolean flag4 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag4) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(156);
			aBuffer_1192.put16A(j + anInt1034);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16LEA(i1);
		}
		if (l == 94) {
			boolean flag5 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag5) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(181);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16(i1);
			aBuffer_1192.put16LE(j + anInt1034);
			aBuffer_1192.put16A(anInt1137);
		}
		if (l == 646) {
			aBuffer_1192.putOp(185);
			aBuffer_1192.put16(k);
			Component component_2 = Component.aComponentArray210[k];
			if ((component_2.anIntArrayArray226 != null) && (component_2.anIntArrayArray226[0][0] == 5)) {
				int i2 = component_2.anIntArrayArray226[0][1];
				if (anIntArray971[i2] != component_2.anIntArray212[0]) {
					anIntArray971[i2] = component_2.anIntArray212[0];
					method33(i2);
					aBoolean1153 = true;
				}
			}
		}
		if (l == 225) {
			NPCEntity class30_sub2_sub4_sub1_sub1_2 = aNpcArray835[i1];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub1_2.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub1_2.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(17);
				aBuffer_1192.put16LEA(i1);
			}
		}
		if (l == 965) {
			NPCEntity npc_3 = aNpcArray835[i1];
			if (npc_3 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, npc_3.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, npc_3.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(21);
				aBuffer_1192.put16(i1);
			}
		}
		if (l == 413) {
			NPCEntity class30_sub2_sub4_sub1_sub1_4 = aNpcArray835[i1];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub1_4.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub1_4.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(131);
				aBuffer_1192.put16LEA(i1);
				aBuffer_1192.put16A(anInt1137);
			}
		}
		if (l == 200) {
			method147();
		}
		if (l == 1025) {
			NPCEntity npc_5 = aNpcArray835[i1];
			if (npc_5 != null) {
				NPCType type = npc_5.aType_1696;
				if (type.anIntArray88 != null) {
					type = type.method161();
				}
				if (type != null) {
					String s9;
					if (type.aByteArray89 != null) {
						s9 = new String(type.aByteArray89);
					} else {
						s9 = "It's a " + type.aString65 + ".";
					}
					method77(s9, 0, "");
				}
			}
		}
		if (l == 900) {
			method66(i1, k, j);
			aBuffer_1192.putOp(252);
			aBuffer_1192.put16LEA((i1 >> 14) & 0x7fff);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16A(j + anInt1034);
		}
		if (l == 412) {
			NPCEntity npc_6 = aNpcArray835[i1];
			if (npc_6 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, npc_6.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, npc_6.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(72);
				aBuffer_1192.put16A(i1);
			}
		}
		if (l == 365) {
			PlayerEntity player_3 = aPlayerArray890[i1];
			if (player_3 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, player_3.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, player_3.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(249);
				aBuffer_1192.put16A(i1);
				aBuffer_1192.put16LE(anInt1137);
			}
		}
		if (l == 729) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_4 = aPlayerArray890[i1];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, class30_sub2_sub4_sub1_sub2_4.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, class30_sub2_sub4_sub1_sub2_4.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(39);
				aBuffer_1192.put16LE(i1);
			}
		}
		if (l == 577) {
			PlayerEntity player_5 = aPlayerArray890[i1];
			if (player_5 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, player_5.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, player_5.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(139);
				aBuffer_1192.put16LE(i1);
			}
		}
		if ((l == 956) && method66(i1, k, j)) {
			aBuffer_1192.putOp(35);
			aBuffer_1192.put16LE(j + anInt1034);
			aBuffer_1192.put16A(anInt1137);
			aBuffer_1192.put16A(k + anInt1035);
			aBuffer_1192.put16LE((i1 >> 14) & 0x7fff);
		}
		if (l == 567) {
			boolean flag6 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag6) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(23);
			aBuffer_1192.put16LE(k + anInt1035);
			aBuffer_1192.put16LE(i1);
			aBuffer_1192.put16LE(j + anInt1034);
		}
		if (l == 867) {
			aBuffer_1192.putOp(43);
			aBuffer_1192.put16LE(k);
			aBuffer_1192.put16A(i1);
			aBuffer_1192.put16A(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 543) {
			aBuffer_1192.putOp(237);
			aBuffer_1192.put16(j);
			aBuffer_1192.put16A(i1);
			aBuffer_1192.put16(k);
			aBuffer_1192.put16A(anInt1137);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 606) {
			String s2 = aStringArray1199[i];
			int j2 = s2.indexOf("@whi@");
			if (j2 != -1) {
				if (anInt857 == -1) {
					method147();
					aString881 = s2.substring(j2 + 5).trim();
					aBoolean1158 = false;
					for (int i3 = 0; i3 < Component.aComponentArray210.length; i3++) {
						if ((Component.aComponentArray210[i3] == null) || (Component.aComponentArray210[i3].anInt214 != 600)) {
							continue;
						}
						anInt1178 = anInt857 = Component.aComponentArray210[i3].anInt236;
						break;
					}
				} else {
					method77("Please close the interface you have open before using 'report abuse'", 0, "");
				}
			}
		}
		if (l == 491) {
			PlayerEntity player_6 = aPlayerArray890[i1];
			if (player_6 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, player_6.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, player_6.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(14);
				aBuffer_1192.put16A(anInt1284);
				aBuffer_1192.put16(i1);
				aBuffer_1192.put16(anInt1285);
				aBuffer_1192.put16LE(anInt1283);
			}
		}
		if (l == 639) {
			String s3 = aStringArray1199[i];
			int k2 = s3.indexOf("@whi@");
			if (k2 != -1) {
				long l4 = StringUtil.toBase37(s3.substring(k2 + 5).trim());
				int k3 = -1;
				for (int i4 = 0; i4 < anInt899; i4++) {
					if (aLongArray955[i4] != l4) {
						continue;
					}
					k3 = i4;
					break;
				}
				if ((k3 != -1) && (anIntArray826[k3] > 0)) {
					aBoolean1223 = true;
					anInt1225 = 0;
					aBoolean1256 = true;
					aString1212 = "";
					anInt1064 = 3;
					aLong953 = aLongArray955[k3];
					aString1121 = "Enter message to send to " + aStringArray1082[k3];
				}
			}
		}
		if (l == 454) {
			aBuffer_1192.putOp(41);
			aBuffer_1192.put16(i1);
			aBuffer_1192.put16A(j);
			aBuffer_1192.put16A(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.aComponentArray210[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.aComponentArray210[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 478) {
			NPCEntity npc_7 = aNpcArray835[i1];
			if (npc_7 != null) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, npc_7.anIntArray1501[0], aPlayer_1126.anIntArray1500[0], false, npc_7.anIntArray1500[0]);
				anInt914 = super.anInt27;
				anInt915 = super.anInt28;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(18);
				aBuffer_1192.put16LE(i1);
			}
		}
		if (l == 113) {
			method66(i1, k, j);
			aBuffer_1192.putOp(70);
			aBuffer_1192.put16LE(j + anInt1034);
			aBuffer_1192.put16(k + anInt1035);
			aBuffer_1192.put16LEA((i1 >> 14) & 0x7fff);
		}
		if (l == 872) {
			method66(i1, k, j);
			aBuffer_1192.putOp(234);
			aBuffer_1192.put16LEA(j + anInt1034);
			aBuffer_1192.put16A((i1 >> 14) & 0x7fff);
			aBuffer_1192.put16LEA(k + anInt1035);
		}
		if (l == 502) {
			method66(i1, k, j);
			aBuffer_1192.putOp(132);
			aBuffer_1192.put16LEA(j + anInt1034);
			aBuffer_1192.put16((i1 >> 14) & 0x7fff);
			aBuffer_1192.put16A(k + anInt1035);
		}
		if (l == 1125) {
			ObjType type = ObjType.method198(i1);
			Component component_4 = Component.aComponentArray210[k];
			String s5;
			if ((component_4 != null) && (component_4.anIntArray252[j] >= 0x186a0)) {
				s5 = component_4.anIntArray252[j] + " x " + type.aString170;
			} else if (type.aByteArray178 != null) {
				s5 = new String(type.aByteArray178);
			} else {
				s5 = "It's a " + type.aString170 + ".";
			}
			method77(s5, 0, "");
		}
		if (l == 169) {
			aBuffer_1192.putOp(185);
			aBuffer_1192.put16(k);
			Component component_3 = Component.aComponentArray210[k];
			if ((component_3.anIntArrayArray226 != null) && (component_3.anIntArrayArray226[0][0] == 5)) {
				int l2 = component_3.anIntArrayArray226[0][1];
				anIntArray971[l2] = 1 - anIntArray971[l2];
				method33(l2);
				aBoolean1153 = true;
			}
		}
		if (l == 447) {
			anInt1282 = 1;
			anInt1283 = j;
			anInt1284 = k;
			anInt1285 = i1;
			aString1286 = ObjType.method198(i1).aString170;
			anInt1136 = 0;
			aBoolean1153 = true;
			return;
		}
		if (l == 1226) {
			int j1 = (i1 >> 14) & 0x7fff;
			LocType type = LocType.method572(j1);
			String s10;
			if (type.aByteArray777 != null) {
				s10 = new String(type.aByteArray777);
			} else {
				s10 = "It's a " + type.aString739 + ".";
			}
			method77(s10, 0, "");
		}
		if (l == 244) {
			boolean flag7 = method85(2, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			if (!flag7) {
				method85(2, 0, 1, 0, aPlayer_1126.anIntArray1501[0], 1, 0, k, aPlayer_1126.anIntArray1500[0], false, j);
			}
			anInt914 = super.anInt27;
			anInt915 = super.anInt28;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(253);
			aBuffer_1192.put16LE(j + anInt1034);
			aBuffer_1192.put16LEA(k + anInt1035);
			aBuffer_1192.put16A(i1);
		}
		if (l == 1448) {
			ObjType type_1 = ObjType.method198(i1);
			String s6;
			if (type_1.aByteArray178 != null) {
				s6 = new String(type_1.aByteArray178);
			} else {
				s6 = "It's a " + type_1.aString170 + ".";
			}
			method77(s6, 0, "");
		}
		anInt1282 = 0;
		anInt1136 = 0;
		aBoolean1153 = true;
	}

	public void method70() {
		anInt1251 = 0;
		int j = (aPlayer_1126.anInt1550 >> 7) + anInt1034;
		int k = (aPlayer_1126.anInt1551 >> 7) + anInt1035;
		if ((j >= 3053) && (j <= 3156) && (k >= 3056) && (k <= 3136)) {
			anInt1251 = 1;
		}
		if ((j >= 3072) && (j <= 3118) && (k >= 9492) && (k <= 9535)) {
			anInt1251 = 1;
		}
		if ((anInt1251 == 1) && (j >= 3139) && (j <= 3199) && (k >= 3008) && (k <= 3062)) {
			anInt1251 = 0;
		}
	}

	public void method71() {
		if ((anInt1282 == 0) && (anInt1136 == 0)) {
			aStringArray1199[anInt1133] = "Walk here";
			anIntArray1093[anInt1133] = 516;
			anIntArray1091[anInt1133] = super.anInt20;
			anIntArray1092[anInt1133] = super.anInt21;
			anInt1133++;
		}
		int j = -1;
		for (int k = 0; k < Model.pickedCount; k++) {
			int l = Model.pickedBitsets[k];
			int i1 = l & 0x7f;
			int j1 = (l >> 7) & 0x7f;
			int k1 = (l >> 29) & 3;
			int l1 = (l >> 14) & 0x7fff;
			if (l == j) {
				continue;
			}
			j = l;
			if ((k1 == 2) && (aGraph_946.method304(anInt918, i1, j1, l) >= 0)) {
				LocType type = LocType.method572(l1);
				if (type.anIntArray759 != null) {
					type = type.method580();
				}
				if (type == null) {
					continue;
				}
				if (anInt1282 == 1) {
					aStringArray1199[anInt1133] = "Use " + aString1286 + " with @cya@" + type.aString739;
					anIntArray1093[anInt1133] = 62;
					anIntArray1094[anInt1133] = l;
					anIntArray1091[anInt1133] = i1;
					anIntArray1092[anInt1133] = j1;
					anInt1133++;
				} else if (anInt1136 == 1) {
					if ((anInt1138 & 4) == 4) {
						aStringArray1199[anInt1133] = aString1139 + " @cya@" + type.aString739;
						anIntArray1093[anInt1133] = 956;
						anIntArray1094[anInt1133] = l;
						anIntArray1091[anInt1133] = i1;
						anIntArray1092[anInt1133] = j1;
						anInt1133++;
					}
				} else {
					if (type.aStringArray786 != null) {
						for (int i2 = 4; i2 >= 0; i2--) {
							if (type.aStringArray786[i2] != null) {
								aStringArray1199[anInt1133] = type.aStringArray786[i2] + " @cya@" + type.aString739;
								if (i2 == 0) {
									anIntArray1093[anInt1133] = 502;
								}
								if (i2 == 1) {
									anIntArray1093[anInt1133] = 900;
								}
								if (i2 == 2) {
									anIntArray1093[anInt1133] = 113;
								}
								if (i2 == 3) {
									anIntArray1093[anInt1133] = 872;
								}
								if (i2 == 4) {
									anIntArray1093[anInt1133] = 1062;
								}
								anIntArray1094[anInt1133] = l;
								anIntArray1091[anInt1133] = i1;
								anIntArray1092[anInt1133] = j1;
								anInt1133++;
							}
						}
					}
					aStringArray1199[anInt1133] = "Examine @cya@" + type.aString739;
					anIntArray1093[anInt1133] = 1226;
					anIntArray1094[anInt1133] = type.anInt754 << 14;
					anIntArray1091[anInt1133] = i1;
					anIntArray1092[anInt1133] = j1;
					anInt1133++;
				}
			}
			if (k1 == 1) {
				NPCEntity npc = aNpcArray835[l1];
				if ((npc.aType_1696.aByte68 == 1) && ((npc.anInt1550 & 0x7f) == 64) && ((npc.anInt1551 & 0x7f) == 64)) {
					for (int j2 = 0; j2 < anInt836; j2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_1 = aNpcArray835[anIntArray837[j2]];
						if ((class30_sub2_sub4_sub1_sub1_1 != null) && (class30_sub2_sub4_sub1_sub1_1 != npc) && (class30_sub2_sub4_sub1_sub1_1.aType_1696.aByte68 == 1) && (class30_sub2_sub4_sub1_sub1_1.anInt1550 == npc.anInt1550) && (class30_sub2_sub4_sub1_sub1_1.anInt1551 == npc.anInt1551)) {
							method87(class30_sub2_sub4_sub1_sub1_1.aType_1696, anIntArray837[j2], j1, i1);
						}
					}
					for (int l2 = 0; l2 < anInt891; l2++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_1 = aPlayerArray890[anIntArray892[l2]];
						if ((class30_sub2_sub4_sub1_sub2_1 != null) && (class30_sub2_sub4_sub1_sub2_1.anInt1550 == npc.anInt1550) && (class30_sub2_sub4_sub1_sub2_1.anInt1551 == npc.anInt1551)) {
							method88(i1, anIntArray892[l2], class30_sub2_sub4_sub1_sub2_1, j1);
						}
					}
				}
				method87(npc.aType_1696, l1, j1, i1);
			}
			if (k1 == 0) {
				PlayerEntity player = aPlayerArray890[l1];
				if (((player.anInt1550 & 0x7f) == 64) && ((player.anInt1551 & 0x7f) == 64)) {
					for (int k2 = 0; k2 < anInt836; k2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_2 = aNpcArray835[anIntArray837[k2]];
						if ((class30_sub2_sub4_sub1_sub1_2 != null) && (class30_sub2_sub4_sub1_sub1_2.aType_1696.aByte68 == 1) && (class30_sub2_sub4_sub1_sub1_2.anInt1550 == player.anInt1550) && (class30_sub2_sub4_sub1_sub1_2.anInt1551 == player.anInt1551)) {
							method87(class30_sub2_sub4_sub1_sub1_2.aType_1696, anIntArray837[k2], j1, i1);
						}
					}
					for (int i3 = 0; i3 < anInt891; i3++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_2 = aPlayerArray890[anIntArray892[i3]];
						if ((class30_sub2_sub4_sub1_sub2_2 != null) && (class30_sub2_sub4_sub1_sub2_2 != player) && (class30_sub2_sub4_sub1_sub2_2.anInt1550 == player.anInt1550) && (class30_sub2_sub4_sub1_sub2_2.anInt1551 == player.anInt1551)) {
							method88(i1, anIntArray892[i3], class30_sub2_sub4_sub1_sub2_2, j1);
						}
					}
				}
				method88(i1, l1, player, j1);
			}
			if (k1 == 3) {
				LinkedList list = aListArrayArrayArray827[anInt918][i1][j1];
				if (list != null) {
					for (ObjStackEntity objStack = (ObjStackEntity) list.method253(); objStack != null; objStack = (ObjStackEntity) list.method255()) {
						ObjType type = ObjType.method198(objStack.anInt1558);
						if (anInt1282 == 1) {
							aStringArray1199[anInt1133] = "Use " + aString1286 + " with @lre@" + type.aString170;
							anIntArray1093[anInt1133] = 511;
							anIntArray1094[anInt1133] = objStack.anInt1558;
							anIntArray1091[anInt1133] = i1;
							anIntArray1092[anInt1133] = j1;
							anInt1133++;
						} else if (anInt1136 == 1) {
							if ((anInt1138 & 1) == 1) {
								aStringArray1199[anInt1133] = aString1139 + " @lre@" + type.aString170;
								anIntArray1093[anInt1133] = 94;
								anIntArray1094[anInt1133] = objStack.anInt1558;
								anIntArray1091[anInt1133] = i1;
								anIntArray1092[anInt1133] = j1;
								anInt1133++;
							}
						} else {
							for (int j3 = 4; j3 >= 0; j3--) {
								if ((type.aStringArray168 != null) && (type.aStringArray168[j3] != null)) {
									aStringArray1199[anInt1133] = type.aStringArray168[j3] + " @lre@" + type.aString170;
									if (j3 == 0) {
										anIntArray1093[anInt1133] = 652;
									}
									if (j3 == 1) {
										anIntArray1093[anInt1133] = 567;
									}
									if (j3 == 2) {
										anIntArray1093[anInt1133] = 234;
									}
									if (j3 == 3) {
										anIntArray1093[anInt1133] = 244;
									}
									if (j3 == 4) {
										anIntArray1093[anInt1133] = 213;
									}
									anIntArray1094[anInt1133] = objStack.anInt1558;
									anIntArray1091[anInt1133] = i1;
									anIntArray1092[anInt1133] = j1;
									anInt1133++;
								} else if (j3 == 2) {
									aStringArray1199[anInt1133] = "Take @lre@" + type.aString170;
									anIntArray1093[anInt1133] = 234;
									anIntArray1094[anInt1133] = objStack.anInt1558;
									anIntArray1091[anInt1133] = i1;
									anIntArray1092[anInt1133] = j1;
									anInt1133++;
								}
							}
							aStringArray1199[anInt1133] = "Examine @lre@" + type.aString170;
							anIntArray1093[anInt1133] = 1448;
							anIntArray1094[anInt1133] = objStack.anInt1558;
							anIntArray1091[anInt1133] = i1;
							anIntArray1092[anInt1133] = j1;
							anInt1133++;
						}
					}
				}
			}
		}
	}

	public void method72() {
		System.out.println("============");
		System.out.println("flame-cycle:" + anInt1208);
		if (aOnDemand_1068 != null) {
			System.out.println("Od-cycle:" + aOnDemand_1068.anInt1341);
		}
		System.out.println("loop-cycle:" + anInt1161);
		System.out.println("draw-cycle:" + anInt1061);
		System.out.println("ptype:" + anInt1008);
		System.out.println("psize:" + anInt1007);
		if (aConnection_1168 != null) {
			aConnection_1168.method272();
		}
		super.aBoolean9 = true;
	}

	@SuppressWarnings("StringConcatenationInLoop")
	public void method73() {
		do {
			int j = method5();
			if (j == -1) {
				break;
			}
			if ((anInt857 != -1) && (anInt857 == anInt1178)) {
				if ((j == 8) && (aString881.length() > 0)) {
					aString881 = aString881.substring(0, aString881.length() - 1);
				}
				if ((((j >= 97) && (j <= 122)) || ((j >= 65) && (j <= 90)) || ((j >= 48) && (j <= 57)) || (j == 32)) && (aString881.length() < 12)) {
					aString881 += (char) j;
				}
			} else if (aBoolean1256) {
				if ((j >= 32) && (j <= 122) && (aString1212.length() < 80)) {
					aString1212 += (char) j;
					aBoolean1223 = true;
				}
				if ((j == 8) && (aString1212.length() > 0)) {
					aString1212 = aString1212.substring(0, aString1212.length() - 1);
					aBoolean1223 = true;
				}
				if ((j == 13) || (j == 10)) {
					aBoolean1256 = false;
					aBoolean1223 = true;
					if (anInt1064 == 1) {
						long l = StringUtil.toBase37(aString1212);
						method41(l);
					}
					if ((anInt1064 == 2) && (anInt899 > 0)) {
						long l1 = StringUtil.toBase37(aString1212);
						method35(l1);
					}
					if ((anInt1064 == 3) && (aString1212.length() > 0)) {
						aBuffer_1192.putOp(126);
						aBuffer_1192.put8(0);
						int k = aBuffer_1192.position;
						aBuffer_1192.put64(aLong953);
						Huffman.method526(aString1212, aBuffer_1192);
						aBuffer_1192.putSize8(aBuffer_1192.position - k);
						aString1212 = Huffman.method527(aString1212);
						aString1212 = Censor.method497(aString1212, 0);
						method77(aString1212, 6, StringUtil.formatName(StringUtil.fromBase37(aLong953)));
						if (anInt845 == 2) {
							anInt845 = 1;
							aBoolean1233 = true;
							aBuffer_1192.putOp(95);
							aBuffer_1192.put8(anInt1287);
							aBuffer_1192.put8(anInt845);
							aBuffer_1192.put8(anInt1248);
						}
					}
					if ((anInt1064 == 4) && (anInt822 < 100)) {
						long l2 = StringUtil.toBase37(aString1212);
						method113(l2);
					}
					if ((anInt1064 == 5) && (anInt822 > 0)) {
						long l3 = StringUtil.toBase37(aString1212);
						method122(l3);
					}
				}
			} else if (anInt1225 == 1) {
				if ((j >= 48) && (j <= 57) && (aString1004.length() < 10)) {
					aString1004 += (char) j;
					aBoolean1223 = true;
				}
				if ((j == 8) && (aString1004.length() > 0)) {
					aString1004 = aString1004.substring(0, aString1004.length() - 1);
					aBoolean1223 = true;
				}
				if ((j == 13) || (j == 10)) {
					if (aString1004.length() > 0) {
						int i1 = 0;
						try {
							i1 = Integer.parseInt(aString1004);
						} catch (Exception ignored) {
						}
						aBuffer_1192.putOp(208);
						aBuffer_1192.put32(i1);
					}
					anInt1225 = 0;
					aBoolean1223 = true;
				}
			} else if (anInt1225 == 2) {
				if ((j >= 32) && (j <= 122) && (aString1004.length() < 12)) {
					aString1004 += (char) j;
					aBoolean1223 = true;
				}
				if ((j == 8) && (aString1004.length() > 0)) {
					aString1004 = aString1004.substring(0, aString1004.length() - 1);
					aBoolean1223 = true;
				}
				if ((j == 13) || (j == 10)) {
					if (aString1004.length() > 0) {
						aBuffer_1192.putOp(60);
						aBuffer_1192.put64(StringUtil.toBase37(aString1004));
					}
					anInt1225 = 0;
					aBoolean1223 = true;
				}
			} else if (anInt1276 == -1) {
				if ((j >= 32) && (j <= 122) && (aString887.length() < 80)) {
					aString887 += (char) j;
					aBoolean1223 = true;
				}
				if ((j == 8) && (aString887.length() > 0)) {
					aString887 = aString887.substring(0, aString887.length() - 1);
					aBoolean1223 = true;
				}
				if (((j == 13) || (j == 10)) && (aString887.length() > 0)) {
					if (anInt863 == 2) {
						if (aString887.equals("::clientdrop")) {
							method68();
						}
						if (aString887.equals("::lag")) {
							method72();
						}
						if (aString887.equals("::prefetchmusic")) {
							for (int j1 = 0; j1 < aOnDemand_1068.method555(2); j1++) {
								aOnDemand_1068.method563((byte) 1, 2, j1);
							}
						}
						if (aString887.equals("::fpson")) {
							aBoolean1156 = true;
						}
						if (aString887.equals("::fpsoff")) {
							aBoolean1156 = false;
						}
						if (aString887.equals("::noclip")) {
							for (int k1 = 0; k1 < 4; k1++) {
								for (int i2 = 1; i2 < 103; i2++) {
									for (int k2 = 1; k2 < 103; k2++) {
										aCollisionMapArray1230[k1].anIntArrayArray294[i2][k2] = 0;
									}
								}
							}
						}
					}
					if (aString887.startsWith("::")) {
						aBuffer_1192.putOp(103);
						aBuffer_1192.put8(aString887.length() - 1);
						aBuffer_1192.putString(aString887.substring(2));
					} else {
						String s = aString887.toLowerCase();
						int j2 = 0;
						if (s.startsWith("yellow:")) {
							j2 = 0;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("red:")) {
							j2 = 1;
							aString887 = aString887.substring(4);
						} else if (s.startsWith("green:")) {
							j2 = 2;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("cyan:")) {
							j2 = 3;
							aString887 = aString887.substring(5);
						} else if (s.startsWith("purple:")) {
							j2 = 4;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("white:")) {
							j2 = 5;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("flash1:")) {
							j2 = 6;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("flash2:")) {
							j2 = 7;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("flash3:")) {
							j2 = 8;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("glow1:")) {
							j2 = 9;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("glow2:")) {
							j2 = 10;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("glow3:")) {
							j2 = 11;
							aString887 = aString887.substring(6);
						}
						s = aString887.toLowerCase();
						int i3 = 0;
						if (s.startsWith("wave:")) {
							i3 = 1;
							aString887 = aString887.substring(5);
						} else if (s.startsWith("wave2:")) {
							i3 = 2;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("shake:")) {
							i3 = 3;
							aString887 = aString887.substring(6);
						} else if (s.startsWith("scroll:")) {
							i3 = 4;
							aString887 = aString887.substring(7);
						} else if (s.startsWith("slide:")) {
							i3 = 5;
							aString887 = aString887.substring(6);
						}
						aBuffer_1192.putOp(4);
						aBuffer_1192.put8(0);
						int j3 = aBuffer_1192.position;
						aBuffer_1192.put8S(i3);
						aBuffer_1192.put8S(j2);
						aBuffer_834.position = 0;
						Huffman.method526(aString887, aBuffer_834);
						aBuffer_1192.putA(aBuffer_834.data, 0, aBuffer_834.position);
						aBuffer_1192.putSize8(aBuffer_1192.position - j3);
						aString887 = Huffman.method527(aString887);
						aString887 = Censor.method497(aString887, 0);
						aPlayer_1126.aString1506 = aString887;
						aPlayer_1126.anInt1513 = j2;
						aPlayer_1126.anInt1531 = i3;
						aPlayer_1126.anInt1535 = 150;
						if (anInt863 == 2) {
							method77(aPlayer_1126.aString1506, 2, "@cr2@" + aPlayer_1126.aString1703);
						} else if (anInt863 == 1) {
							method77(aPlayer_1126.aString1506, 2, "@cr1@" + aPlayer_1126.aString1703);
						} else {
							method77(aPlayer_1126.aString1506, 2, aPlayer_1126.aString1703);
						}
						if (anInt1287 == 2) {
							anInt1287 = 3;
							aBoolean1233 = true;
							aBuffer_1192.putOp(95);
							aBuffer_1192.put8(anInt1287);
							aBuffer_1192.put8(anInt845);
							aBuffer_1192.put8(anInt1248);
						}
					}
					aString887 = "";
					aBoolean1223 = true;
				}
			}
		} while (true);
	}

	public void method74(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 100; i1++) {
			if (aStringArray944[i1] == null) {
				continue;
			}
			int j1 = anIntArray942[i1];
			int k1 = (70 - (l * 14)) + anInt1089 + 4;
			if (k1 < -20) {
				break;
			}
			String s = aStringArray943[i1];
			if ((s != null) && s.startsWith("@cr1@")) {
				s = s.substring(5);
			}
			if ((s != null) && s.startsWith("@cr2@")) {
				s = s.substring(5);
			}
			if (j1 == 0) {
				l++;
			}
			if (((j1 == 1) || (j1 == 2)) && ((j1 == 1) || (anInt1287 == 0) || ((anInt1287 == 1) && method109(s)))) {
				if ((j > (k1 - 14)) && (j <= k1) && !s.equals(aPlayer_1126.aString1703)) {
					if (anInt863 >= 1) {
						aStringArray1199[anInt1133] = "Report abuse @whi@" + s;
						anIntArray1093[anInt1133] = 606;
						anInt1133++;
					}
					aStringArray1199[anInt1133] = "Add ignore @whi@" + s;
					anIntArray1093[anInt1133] = 42;
					anInt1133++;
					aStringArray1199[anInt1133] = "Add friend @whi@" + s;
					anIntArray1093[anInt1133] = 337;
					anInt1133++;
				}
				l++;
			}
			if (((j1 == 3) || (j1 == 7)) && (anInt1195 == 0) && ((j1 == 7) || (anInt845 == 0) || ((anInt845 == 1) && method109(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					if (anInt863 >= 1) {
						aStringArray1199[anInt1133] = "Report abuse @whi@" + s;
						anIntArray1093[anInt1133] = 606;
						anInt1133++;
					}
					aStringArray1199[anInt1133] = "Add ignore @whi@" + s;
					anIntArray1093[anInt1133] = 42;
					anInt1133++;
					aStringArray1199[anInt1133] = "Add friend @whi@" + s;
					anIntArray1093[anInt1133] = 337;
					anInt1133++;
				}
				l++;
			}
			if ((j1 == 4) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					aStringArray1199[anInt1133] = "Accept trade @whi@" + s;
					anIntArray1093[anInt1133] = 484;
					anInt1133++;
				}
				l++;
			}
			if (((j1 == 5) || (j1 == 6)) && (anInt1195 == 0) && (anInt845 < 2)) {
				l++;
			}
			if ((j1 == 8) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					aStringArray1199[anInt1133] = "Accept challenge @whi@" + s;
					anIntArray1093[anInt1133] = 6;
					anInt1133++;
				}
				l++;
			}
		}
	}

	public void method75(Component component) {
		int j = component.anInt214;
		if (((j >= 1) && (j <= 100)) || ((j >= 701) && (j <= 800))) {
			if ((j == 1) && (anInt900 == 0)) {
				component.aString248 = "Loading friend list";
				component.anInt217 = 0;
				return;
			}
			if ((j == 1) && (anInt900 == 1)) {
				component.aString248 = "Connecting to friendserver";
				component.anInt217 = 0;
				return;
			}
			if ((j == 2) && (anInt900 != 2)) {
				component.aString248 = "Please wait...";
				component.anInt217 = 0;
				return;
			}
			int k = anInt899;
			if (anInt900 != 2) {
				k = 0;
			}
			if (j > 700) {
				j -= 601;
			} else {
				j--;
			}
			if (j >= k) {
				component.aString248 = "";
				component.anInt217 = 0;
			} else {
				component.aString248 = aStringArray1082[j];
				component.anInt217 = 1;
			}
			return;
		}
		if (((j >= 101) && (j <= 200)) || ((j >= 801) && (j <= 900))) {
			int l = anInt899;
			if (anInt900 != 2) {
				l = 0;
			}
			if (j > 800) {
				j -= 701;
			} else {
				j -= 101;
			}
			if (j >= l) {
				component.aString248 = "";
				component.anInt217 = 0;
				return;
			}
			if (anIntArray826[j] == 0) {
				component.aString248 = "@red@Offline";
			} else if (anIntArray826[j] == anInt957) {
				component.aString248 = "@gre@World-" + (anIntArray826[j] - 9);
			} else {
				component.aString248 = "@yel@World-" + (anIntArray826[j] - 9);
			}
			component.anInt217 = 1;
			return;
		}
		if (j == 203) {
			int i1 = anInt899;
			if (anInt900 != 2) {
				i1 = 0;
			}
			component.anInt261 = (i1 * 15) + 20;
			if (component.anInt261 <= component.anInt267) {
				component.anInt261 = component.anInt267 + 1;
			}
			return;
		}
		if ((j >= 401) && (j <= 500)) {
			if ((((j -= 401)) == 0) && (anInt900 == 0)) {
				component.aString248 = "Loading ignore list";
				component.anInt217 = 0;
				return;
			}
			if ((j == 1) && (anInt900 == 0)) {
				component.aString248 = "Please wait...";
				component.anInt217 = 0;
				return;
			}
			int j1 = anInt822;
			if (anInt900 == 0) {
				j1 = 0;
			}
			if (j >= j1) {
				component.aString248 = "";
				component.anInt217 = 0;
			} else {
				component.aString248 = StringUtil.formatName(StringUtil.fromBase37(aLongArray925[j]));
				component.anInt217 = 1;
			}
			return;
		}
		if (j == 503) {
			component.anInt261 = (anInt822 * 15) + 20;
			if (component.anInt261 <= component.anInt267) {
				component.anInt261 = component.anInt267 + 1;
			}
			return;
		}
		if (j == 327) {
			component.anInt270 = 150;
			component.anInt271 = (int) (Math.sin((double) anInt1161 / 40D) * 256D) & 0x7ff;
			if (aBoolean1031) {
				for (int k1 = 0; k1 < 7; k1++) {
					int l1 = anIntArray1065[k1];
					if ((l1 >= 0) && !IDKType.aIDKTypeArray656[l1].method537()) {
						return;
					}
				}
				aBoolean1031 = false;
				Model[] aclass30_sub2_sub4_sub6 = new Model[7];
				int i2 = 0;
				for (int j2 = 0; j2 < 7; j2++) {
					int k2 = anIntArray1065[j2];
					if (k2 >= 0) {
						aclass30_sub2_sub4_sub6[i2++] = IDKType.aIDKTypeArray656[k2].method538();
					}
				}
				Model model = new Model(i2, aclass30_sub2_sub4_sub6);
				for (int l2 = 0; l2 < 5; l2++) {
					if (anIntArray990[l2] != 0) {
						model.replaceColor(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1) {
							model.replaceColor(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
						}
					}
				}
				model.createLabelReferences();
				model.applySequenceFrame(SeqType.aTypeArray351[aPlayer_1126.anInt1511].anIntArray353[0]);
				model.calculateNormals(64, 850, -30, -50, -30, true);
				component.anInt233 = 5;
				component.anInt234 = 0;
				Component.method208(0, 5, model);
			}
			return;
		}
		if (j == 324) {
			if (aImage_931 == null) {
				aImage_931 = component.aImage_207;
				aImage_932 = component.aImage_260;
			}
			if (aBoolean1047) {
				component.aImage_207 = aImage_932;
			} else {
				component.aImage_207 = aImage_931;
			}
			return;
		}
		if (j == 325) {
			if (aImage_931 == null) {
				aImage_931 = component.aImage_207;
				aImage_932 = component.aImage_260;
			}
			if (aBoolean1047) {
				component.aImage_207 = aImage_931;
			} else {
				component.aImage_207 = aImage_932;
			}
			return;
		}
		if (j == 600) {
			component.aString248 = aString881;
			if ((anInt1161 % 20) < 10) {
				component.aString248 += "|";
			} else {
				component.aString248 += " ";
			}
			return;
		}
		if (j == 613) {
			if (anInt863 >= 1) {
				if (aBoolean1158) {
					component.anInt232 = 0xff0000;
					component.aString248 = "Moderator option: Mute player for 48 hours: <ON>";
				} else {
					component.anInt232 = 0xffffff;
					component.aString248 = "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else {
				component.aString248 = "";
			}
		}
		if ((j == 650) || (j == 655)) {
			if (anInt1193 != 0) {
				String s;
				if (anInt1006 == 0) {
					s = "earlier today";
				} else if (anInt1006 == 1) {
					s = "yesterday";
				} else {
					s = anInt1006 + " days ago";
				}
				component.aString248 = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				component.aString248 = "";
			}
		}
		if (j == 651) {
			if (anInt1154 == 0) {
				component.aString248 = "0 unread messages";
				component.anInt232 = 0xffff00;
			}
			if (anInt1154 == 1) {
				component.aString248 = "1 unread message";
				component.anInt232 = 65280;
			}
			if (anInt1154 > 1) {
				component.aString248 = anInt1154 + " unread messages";
				component.anInt232 = 65280;
			}
		}
		if (j == 652) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.aString248 = "@yel@This is a non-members world: @whi@Since you are a member we";
				} else {
					component.aString248 = "";
				}
			} else if (anInt1167 == 200) {
				component.aString248 = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (anInt1167 == 0) {
					s1 = "Earlier today";
				} else if (anInt1167 == 1) {
					s1 = "Yesterday";
				} else {
					s1 = anInt1167 + " days ago";
				}
				component.aString248 = s1 + " you changed your recovery questions";
			}
		}
		if (j == 653) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.aString248 = "@whi@recommend you use a members world instead. You may use";
				} else {
					component.aString248 = "";
				}
			} else if (anInt1167 == 200) {
				component.aString248 = "We strongly recommend you do so now to secure your account.";
			} else {
				component.aString248 = "If you do not remember making this change then cancel it immediately";
			}
		}
		if (j == 654) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.aString248 = "@whi@this world but member benefits are unavailable whilst here.";
				} else {
					component.aString248 = "";
				}
				return;
			}
			if (anInt1167 == 200) {
				component.aString248 = "Do this from the 'account management' area on our front webpage";
				return;
			}
			component.aString248 = "Do this from the 'account management' area on our front webpage";
		}
	}

	public void method76() {
		if (anInt1195 == 0) {
			return;
		}
		BitmapFont font = aFont_1271;
		int i = 0;
		if (anInt1104 != 0) {
			i = 1;
		}
		for (int j = 0; j < 100; j++) {
			if (aStringArray944[j] != null) {
				int k = anIntArray942[j];
				String s = aStringArray943[j];
				byte byte1 = 0;
				if ((s != null) && s.startsWith("@cr1@")) {
					s = s.substring(5);
					byte1 = 1;
				}
				if ((s != null) && s.startsWith("@cr2@")) {
					s = s.substring(5);
					byte1 = 2;
				}
				if (((k == 3) || (k == 7)) && ((k == 7) || (anInt845 == 0) || ((anInt845 == 1) && method109(s)))) {
					int l = 329 - (i * 13);
					int k1 = 4;
					font.method385(0, "From", l, k1);
					font.method385(65535, "From", l - 1, k1);
					k1 += font.method383("From ");
					if (byte1 == 1) {
						aImageArray1219[0].method361(k1, l - 12);
						k1 += 14;
					}
					if (byte1 == 2) {
						aImageArray1219[1].method361(k1, l - 12);
						k1 += 14;
					}
					font.method385(0, s + ": " + aStringArray944[j], l, k1);
					font.method385(65535, s + ": " + aStringArray944[j], l - 1, k1);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 5) && (anInt845 < 2)) {
					int i1 = 329 - (i * 13);
					font.method385(0, aStringArray944[j], i1, 4);
					font.method385(65535, aStringArray944[j], i1 - 1, 4);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 6) && (anInt845 < 2)) {
					int j1 = 329 - (i * 13);
					font.method385(0, "To " + s + ": " + aStringArray944[j], j1, 4);
					font.method385(65535, "To " + s + ": " + aStringArray944[j], j1 - 1, 4);
					if (++i >= 5) {
						return;
					}
				}
			}
		}
	}

	public void method77(String s, int i, String s1) {
		if ((i == 0) && (anInt1042 != -1)) {
			aString844 = s;
			super.anInt26 = 0;
		}
		if (anInt1276 == -1) {
			aBoolean1223 = true;
		}
		for (int j = 99; j > 0; j--) {
			anIntArray942[j] = anIntArray942[j - 1];
			aStringArray943[j] = aStringArray943[j - 1];
			aStringArray944[j] = aStringArray944[j - 1];
		}
		anIntArray942[0] = i;
		aStringArray943[0] = s1;
		aStringArray944[0] = s;
	}

	public void method78() {
		if (super.anInt26 == 1) {
			if ((super.anInt27 >= 539) && (super.anInt27 <= 573) && (super.anInt28 >= 169) && (super.anInt28 < 205) && (anIntArray1130[0] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 0;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 569) && (super.anInt27 <= 599) && (super.anInt28 >= 168) && (super.anInt28 < 205) && (anIntArray1130[1] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 1;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 597) && (super.anInt27 <= 627) && (super.anInt28 >= 168) && (super.anInt28 < 205) && (anIntArray1130[2] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 2;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 625) && (super.anInt27 <= 669) && (super.anInt28 >= 168) && (super.anInt28 < 203) && (anIntArray1130[3] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 3;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 666) && (super.anInt27 <= 696) && (super.anInt28 >= 168) && (super.anInt28 < 205) && (anIntArray1130[4] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 4;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 694) && (super.anInt27 <= 724) && (super.anInt28 >= 168) && (super.anInt28 < 205) && (anIntArray1130[5] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 5;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 722) && (super.anInt27 <= 756) && (super.anInt28 >= 169) && (super.anInt28 < 205) && (anIntArray1130[6] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 6;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 540) && (super.anInt27 <= 574) && (super.anInt28 >= 466) && (super.anInt28 < 502) && (anIntArray1130[7] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 7;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 572) && (super.anInt27 <= 602) && (super.anInt28 >= 466) && (super.anInt28 < 503) && (anIntArray1130[8] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 8;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 599) && (super.anInt27 <= 629) && (super.anInt28 >= 466) && (super.anInt28 < 503) && (anIntArray1130[9] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 9;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 627) && (super.anInt27 <= 671) && (super.anInt28 >= 467) && (super.anInt28 < 502) && (anIntArray1130[10] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 10;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 669) && (super.anInt27 <= 699) && (super.anInt28 >= 466) && (super.anInt28 < 503) && (anIntArray1130[11] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 11;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 696) && (super.anInt27 <= 726) && (super.anInt28 >= 466) && (super.anInt28 < 503) && (anIntArray1130[12] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 12;
				aBoolean1103 = true;
			}
			if ((super.anInt27 >= 724) && (super.anInt27 <= 758) && (super.anInt28 >= 466) && (super.anInt28 < 502) && (anIntArray1130[13] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 13;
				aBoolean1103 = true;
			}
		}
	}

	public void method79() {
		if (aArea_1166 != null) {
			return;
		}
		method118();
		aArea_1107 = null;
		aArea_1108 = null;
		aArea_1109 = null;
		aArea_1110 = null;
		aArea_1111 = null;
		aArea_1112 = null;
		aArea_1113 = null;
		aArea_1114 = null;
		aArea_1115 = null;
		aArea_1166 = new DrawArea(479, 96, method11());
		aArea_1164 = new DrawArea(172, 156, method11());
		Draw2D.clear();
		aImage_1197.method361(0, 0);
		aArea_1163 = new DrawArea(190, 261, method11());
		aArea_1165 = new DrawArea(512, 334, method11());
		Draw2D.clear();
		aArea_1123 = new DrawArea(496, 50, method11());
		aArea_1124 = new DrawArea(269, 37, method11());
		aArea_1125 = new DrawArea(249, 45, method11());
		aBoolean1255 = true;
	}

	public String method80() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getDocumentBase().getHost().toLowerCase();
		}
		if (super.aGameFrame__15 != null) {
			return "runescape.com";
		} else {
			return super.getDocumentBase().getHost().toLowerCase();
		}
	}

	public void method81(Image24 image, int j, int k) {
		int l = (k * k) + (j * j);
		if ((l > 4225) && (l < 90000)) {
			int i1 = (anInt1185 + anInt1209) & 0x7ff;
			int j1 = Model.sin[i1];
			int k1 = Model.cos[i1];
			j1 = (j1 * 256) / (anInt1170 + 256);
			k1 = (k1 * 256) / (anInt1170 + 256);
			int l1 = ((j * j1) + (k * k1)) >> 16;
			int i2 = ((j * k1) - (k * j1)) >> 16;
			double d = Math.atan2(l1, i2);
			int j2 = (int) (Math.sin(d) * 63D);
			int k2 = (int) (Math.cos(d) * 57D);
			aImage_1001.method353(83 - k2 - 20, 15, 20, 15, 256, 20, d, (94 + j2 + 4) - 10);
		} else {
			method141(image, k, j);
		}
	}

	public void method82() {
		if (anInt1086 != 0) {
			return;
		}
		aStringArray1199[0] = "Cancel";
		anIntArray1093[0] = 1107;
		anInt1133 = 1;
		method129();
		anInt886 = 0;
		if ((super.anInt20 > 4) && (super.anInt21 > 4) && (super.anInt20 < 516) && (super.anInt21 < 338)) {
			if (anInt857 != -1) {
				method29(4, Component.aComponentArray210[anInt857], super.anInt20, 4, super.anInt21, 0);
			} else {
				method71();
			}
		}
		if (anInt886 != anInt1026) {
			anInt1026 = anInt886;
		}
		anInt886 = 0;
		if ((super.anInt20 > 553) && (super.anInt21 > 205) && (super.anInt20 < 743) && (super.anInt21 < 466)) {
			if (anInt1189 != -1) {
				method29(553, Component.aComponentArray210[anInt1189], super.anInt20, 205, super.anInt21, 0);
			} else if (anIntArray1130[anInt1221] != -1) {
				method29(553, Component.aComponentArray210[anIntArray1130[anInt1221]], super.anInt20, 205, super.anInt21, 0);
			}
		}
		if (anInt886 != anInt1048) {
			aBoolean1153 = true;
			anInt1048 = anInt886;
		}
		anInt886 = 0;
		if ((super.anInt20 > 17) && (super.anInt21 > 357) && (super.anInt20 < 496) && (super.anInt21 < 453)) {
			if (anInt1276 != -1) {
				method29(17, Component.aComponentArray210[anInt1276], super.anInt20, 357, super.anInt21, 0);
			} else if ((super.anInt21 < 434) && (super.anInt20 < 426)) {
				method74(super.anInt21 - 357);
			}
		}
		if ((anInt1276 != -1) && (anInt886 != anInt1039)) {
			aBoolean1223 = true;
			anInt1039 = anInt886;
		}
		boolean flag = false;
		while (!flag) {
			flag = true;
			for (int j = 0; j < (anInt1133 - 1); j++) {
				if ((anIntArray1093[j] < 1000) && (anIntArray1093[j + 1] > 1000)) {
					String s = aStringArray1199[j];
					aStringArray1199[j] = aStringArray1199[j + 1];
					aStringArray1199[j + 1] = s;
					int k = anIntArray1093[j];
					anIntArray1093[j] = anIntArray1093[j + 1];
					anIntArray1093[j + 1] = k;
					k = anIntArray1091[j];
					anIntArray1091[j] = anIntArray1091[j + 1];
					anIntArray1091[j + 1] = k;
					k = anIntArray1092[j];
					anIntArray1092[j] = anIntArray1092[j + 1];
					anIntArray1092[j + 1] = k;
					k = anIntArray1094[j];
					anIntArray1094[j] = anIntArray1094[j + 1];
					anIntArray1094[j + 1] = k;
					flag = false;
				}
			}
		}
	}

	public int method83(int i, int j, int k) {
		int l = 256 - k;
		return (((((i & 0xff00ff) * l) + ((j & 0xff00ff) * k)) & 0xff00ff00) + ((((i & 0xff00) * l) + ((j & 0xff00) * k)) & 0xff0000)) >> 8;
	}

	public void method84(String s, String s1, boolean flag) {
		try {
			if (!flag) {
				aString1266 = "";
				aString1267 = "Connecting to server...";
				method135(true);
			}
			aConnection_1168 = new Connection(this, method19(43594 + anInt958));
			long l = StringUtil.toBase37(s);
			int i = (int) ((l >> 16) & 31L);
			aBuffer_1192.position = 0;
			aBuffer_1192.put8(14);
			aBuffer_1192.put8(i);
			aConnection_1168.method271(2, aBuffer_1192.data, 0);
			for (int j = 0; j < 8; j++) {
				aConnection_1168.method268();
			}
			int k = aConnection_1168.method268();
			int i1 = k;
			if (k == 0) {
				aConnection_1168.method270(aBuffer_1083.data, 0, 8);
				aBuffer_1083.position = 0;
				aLong1215 = aBuffer_1083.get64();
				int[] ai = new int[4];
				ai[0] = (int) (Math.random() * 99999999D);
				ai[1] = (int) (Math.random() * 99999999D);
				ai[2] = (int) (aLong1215 >> 32);
				ai[3] = (int) aLong1215;
				aBuffer_1192.position = 0;
				aBuffer_1192.put8(10);
				aBuffer_1192.put32(ai[0]);
				aBuffer_1192.put32(ai[1]);
				aBuffer_1192.put32(ai[2]);
				aBuffer_1192.put32(ai[3]);
				aBuffer_1192.put32(Signlink.uid);
				aBuffer_1192.putString(s);
				aBuffer_1192.putString(s1);
				aBuffer_1192.encrypt(aBigInteger1032, aBigInteger856);
				aBuffer_847.position = 0;
				if (flag) {
					aBuffer_847.put8(18);
				} else {
					aBuffer_847.put8(16);
				}
				aBuffer_847.put8(aBuffer_1192.position + 36 + 1 + 1 + 2);
				aBuffer_847.put8(255);
				aBuffer_847.put16(317);
				aBuffer_847.put8(aBoolean960 ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++) {
					aBuffer_847.put32(anIntArray1090[l1]);
				}
				aBuffer_847.put(aBuffer_1192.data, aBuffer_1192.position, 0);
				aBuffer_1192.cipher = new ISAACCipher(ai);
				for (int j2 = 0; j2 < 4; j2++) {
					ai[j2] += 50;
				}
				aISAACCipher_1000 = new ISAACCipher(ai);
				aConnection_1168.method271(aBuffer_847.position, aBuffer_847.data, 0);
				k = aConnection_1168.method268();
			}
			if (k == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception ignored) {
				}
				method84(s, s1, flag);
				return;
			}
			if (k == 2) {
				anInt863 = aConnection_1168.method268();
				aBoolean1205 = aConnection_1168.method268() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				aMouseRecorder_879.anInt810 = 0;
				super.aBoolean17 = true;
				aBoolean954 = true;
				aBoolean1157 = true;
				aBuffer_1192.position = 0;
				aBuffer_1083.position = 0;
				anInt1008 = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				anInt1007 = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1011 = 0;
				anInt855 = 0;
				anInt1133 = 0;
				aBoolean885 = false;
				super.anInt18 = 0;
				for (int j1 = 0; j1 < 100; j1++) {
					aStringArray944[j1] = null;
				}
				anInt1282 = 0;
				anInt1136 = 0;
				anInt1023 = 0;
				anInt1062 = 0;
				anInt1278 = (int) (Math.random() * 100D) - 50;
				anInt1131 = (int) (Math.random() * 110D) - 55;
				anInt896 = (int) (Math.random() * 80D) - 40;
				anInt1209 = (int) (Math.random() * 120D) - 60;
				anInt1170 = (int) (Math.random() * 30D) - 20;
				anInt1185 = ((int) (Math.random() * 20D) - 10) & 0x7ff;
				anInt1021 = 0;
				anInt985 = -1;
				anInt1261 = 0;
				anInt1262 = 0;
				anInt891 = 0;
				anInt836 = 0;
				for (int i2 = 0; i2 < anInt888; i2++) {
					aPlayerArray890[i2] = null;
					aBufferArray895[i2] = null;
				}
				for (int k2 = 0; k2 < 16384; k2++) {
					aNpcArray835[k2] = null;
				}
				aPlayer_1126 = aPlayerArray890[anInt889] = new PlayerEntity();
				aList_1013.method256();
				aList_1056.method256();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++) {
							aListArrayArrayArray827[l2][i3][k3] = null;
						}
					}
				}
				aList_1179 = new LinkedList();
				anInt900 = 0;
				anInt899 = 0;
				anInt1042 = -1;
				anInt1276 = -1;
				anInt857 = -1;
				anInt1189 = -1;
				anInt1018 = -1;
				aBoolean1149 = false;
				anInt1221 = 3;
				anInt1225 = 0;
				aBoolean885 = false;
				aBoolean1256 = false;
				aString844 = null;
				anInt1055 = 0;
				anInt1054 = -1;
				aBoolean1047 = true;
				method45();
				for (int j3 = 0; j3 < 5; j3++) {
					anIntArray990[j3] = 0;
				}
				for (int l3 = 0; l3 < 5; l3++) {
					aStringArray1127[l3] = null;
					aBooleanArray1128[l3] = false;
				}
				method79();
				return;
			}
			if (k == 3) {
				aString1266 = "";
				aString1267 = "Invalid username or password.";
				return;
			}
			if (k == 4) {
				aString1266 = "Your account has been disabled.";
				aString1267 = "Please check your message-centre for details.";
				return;
			}
			if (k == 5) {
				aString1266 = "Your account is already logged in.";
				aString1267 = "Try again in 60 secs...";
				return;
			}
			if (k == 6) {
				aString1266 = "RuneScape has been updated!";
				aString1267 = "Please reload this page.";
				return;
			}
			if (k == 7) {
				aString1266 = "This world is full.";
				aString1267 = "Please use a different world.";
				return;
			}
			if (k == 8) {
				aString1266 = "Unable to connect.";
				aString1267 = "Login server offline.";
				return;
			}
			if (k == 9) {
				aString1266 = "Login limit exceeded.";
				aString1267 = "Too many connections from your address.";
				return;
			}
			if (k == 10) {
				aString1266 = "Unable to connect.";
				aString1267 = "Bad session id.";
				return;
			}
			if (k == 11) {
				aString1267 = "Login server rejected session.";
				aString1267 = "Please try again.";
				return;
			}
			if (k == 12) {
				aString1266 = "You need a members account to login to this world.";
				aString1267 = "Please subscribe, or use a different world.";
				return;
			}
			if (k == 13) {
				aString1266 = "Could not complete login.";
				aString1267 = "Please try using a different world.";
				return;
			}
			if (k == 14) {
				aString1266 = "The server is being updated.";
				aString1267 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 15) {
				aBoolean1157 = true;
				aBuffer_1192.position = 0;
				aBuffer_1083.position = 0;
				anInt1008 = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				anInt1007 = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1133 = 0;
				aBoolean885 = false;
				aLong824 = System.currentTimeMillis();
				return;
			}
			if (k == 16) {
				aString1266 = "Login attempts exceeded.";
				aString1267 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 17) {
				aString1266 = "You are standing in a members-only area.";
				aString1267 = "To play on this world move to a free area first";
				return;
			}
			if (k == 20) {
				aString1266 = "Invalid loginserver requested";
				aString1267 = "Please try using a different world.";
				return;
			}
			if (k == 21) {
				for (int k1 = aConnection_1168.method268(); k1 >= 0; k1--) {
					aString1266 = "You have only just left another world";
					aString1267 = "Your profile will be transferred in: " + k1 + " seconds";
					method135(true);
					try {
						Thread.sleep(1000L);
					} catch (Exception ignored) {
					}
				}
				method84(s, s1, flag);
				return;
			}
			if (k == -1) {
				if (i1 == 0) {
					if (anInt1038 < 2) {
						try {
							Thread.sleep(2000L);
						} catch (Exception ignored) {
						}
						anInt1038++;
						method84(s, s1, flag);
					} else {
						aString1266 = "No response from loginserver";
						aString1267 = "Please wait 1 minute and try again.";
					}
				} else {
					aString1266 = "No response from server";
					aString1267 = "Please try using a different world.";
				}
			} else {
				System.out.println("response:" + k);
				aString1266 = "Unexpected server response";
				aString1267 = "Please try using a different world.";
			}
			return;
		} catch (IOException _ex) {
			aString1266 = "";
		}
		aString1267 = "Error connecting to server.";
	}

	public boolean method85(int i, int j, int k, int i1, int j1, int k1, int l1, int i2, int j2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for (int l2 = 0; l2 < byte0; l2++) {
			for (int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				anIntArrayArray825[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = j2;
		int k3 = j1;
		anIntArrayArray901[j2][j1] = 99;
		anIntArrayArray825[j2][j1] = 0;
		int l3 = 0;
		int i4 = 0;
		anIntArray1280[l3] = j2;
		anIntArray1281[l3++] = j1;
		boolean flag1 = false;
		int j4 = anIntArray1280.length;
		int[][] ai = aCollisionMapArray1230[anInt918].anIntArrayArray294;
		while (i4 != l3) {
			j3 = anIntArray1280[i4];
			k3 = anIntArray1281[i4];
			i4 = (i4 + 1) % j4;
			if ((j3 == k2) && (k3 == i2)) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if (((i1 < 5) || (i1 == 10)) && aCollisionMapArray1230[anInt918].method219(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if ((i1 < 10) && aCollisionMapArray1230[anInt918].method220(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if ((k1 != 0) && (k != 0) && aCollisionMapArray1230[anInt918].method221(i2, k2, j3, k, l1, k1, k3)) {
				flag1 = true;
				break;
			}
			int l4 = anIntArrayArray825[j3][k3] + 1;
			if ((j3 > 0) && (anIntArrayArray901[j3 - 1][k3] == 0) && ((ai[j3 - 1][k3] & 0x1280108) == 0)) {
				anIntArray1280[l3] = j3 - 1;
				anIntArray1281[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				anIntArrayArray825[j3 - 1][k3] = l4;
			}
			if ((j3 < (byte0 - 1)) && (anIntArrayArray901[j3 + 1][k3] == 0) && ((ai[j3 + 1][k3] & 0x1280180) == 0)) {
				anIntArray1280[l3] = j3 + 1;
				anIntArray1281[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				anIntArrayArray825[j3 + 1][k3] = l4;
			}
			if ((k3 > 0) && (anIntArrayArray901[j3][k3 - 1] == 0) && ((ai[j3][k3 - 1] & 0x1280102) == 0)) {
				anIntArray1280[l3] = j3;
				anIntArray1281[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				anIntArrayArray825[j3][k3 - 1] = l4;
			}
			if ((k3 < (byte1 - 1)) && (anIntArrayArray901[j3][k3 + 1] == 0) && ((ai[j3][k3 + 1] & 0x1280120) == 0)) {
				anIntArray1280[l3] = j3;
				anIntArray1281[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				anIntArrayArray825[j3][k3 + 1] = l4;
			}
			if ((j3 > 0) && (k3 > 0) && (anIntArrayArray901[j3 - 1][k3 - 1] == 0) && ((ai[j3 - 1][k3 - 1] & 0x128010e) == 0) && ((ai[j3 - 1][k3] & 0x1280108) == 0) && ((ai[j3][k3 - 1] & 0x1280102) == 0)) {
				anIntArray1280[l3] = j3 - 1;
				anIntArray1281[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				anIntArrayArray825[j3 - 1][k3 - 1] = l4;
			}
			if ((j3 < (byte0 - 1)) && (k3 > 0) && (anIntArrayArray901[j3 + 1][k3 - 1] == 0) && ((ai[j3 + 1][k3 - 1] & 0x1280183) == 0) && ((ai[j3 + 1][k3] & 0x1280180) == 0) && ((ai[j3][k3 - 1] & 0x1280102) == 0)) {
				anIntArray1280[l3] = j3 + 1;
				anIntArray1281[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				anIntArrayArray825[j3 + 1][k3 - 1] = l4;
			}
			if ((j3 > 0) && (k3 < (byte1 - 1)) && (anIntArrayArray901[j3 - 1][k3 + 1] == 0) && ((ai[j3 - 1][k3 + 1] & 0x1280138) == 0) && ((ai[j3 - 1][k3] & 0x1280108) == 0) && ((ai[j3][k3 + 1] & 0x1280120) == 0)) {
				anIntArray1280[l3] = j3 - 1;
				anIntArray1281[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				anIntArrayArray825[j3 - 1][k3 + 1] = l4;
			}
			if ((j3 < (byte0 - 1)) && (k3 < (byte1 - 1)) && (anIntArrayArray901[j3 + 1][k3 + 1] == 0) && ((ai[j3 + 1][k3 + 1] & 0x12801e0) == 0) && ((ai[j3 + 1][k3] & 0x1280180) == 0) && ((ai[j3][k3 + 1] & 0x1280120) == 0)) {
				anIntArray1280[l3] = j3 + 1;
				anIntArray1281[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				anIntArrayArray825[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if (!flag1) {
			if (flag) {
				int i5 = 100;
				for (int k5 = 1; k5 < 2; k5++) {
					for (int i6 = k2 - k5; i6 <= (k2 + k5); i6++) {
						for (int l6 = i2 - k5; l6 <= (i2 + k5); l6++) {
							if ((i6 < 0) || (l6 < 0) || (i6 >= 104) || (l6 >= 104) || (anIntArrayArray825[i6][l6] >= i5)) {
								continue;
							}
							i5 = anIntArrayArray825[i6][l6];
							j3 = i6;
							k3 = l6;
							anInt1264 = 1;
							flag1 = true;
						}
					}
					if (flag1) {
						break;
					}
				}
			}
			if (!flag1) {
				return false;
			}
		}
		i4 = 0;
		anIntArray1280[i4] = j3;
		anIntArray1281[i4++] = k3;
		int l5;
		for (int j5 = l5 = anIntArrayArray901[j3][k3]; (j3 != j2) || (k3 != j1); j5 = anIntArrayArray901[j3][k3]) {
			if (j5 != l5) {
				l5 = j5;
				anIntArray1280[i4] = j3;
				anIntArray1281[i4++] = k3;
			}
			if ((j5 & 2) != 0) {
				j3++;
			} else if ((j5 & 8) != 0) {
				j3--;
			}
			if ((j5 & 1) != 0) {
				k3++;
			} else if ((j5 & 4) != 0) {
				k3--;
			}
		}
		if (i4 > 0) {
			int k4 = i4;
			if (k4 > 25) {
				k4 = 25;
			}
			i4--;
			int k6 = anIntArray1280[i4];
			int i7 = anIntArray1281[i4];
			if (i == 0) {
				aBuffer_1192.putOp(164);
				aBuffer_1192.put8(k4 + k4 + 3);
			}
			if (i == 1) {
				aBuffer_1192.putOp(248);
				aBuffer_1192.put8(k4 + k4 + 3 + 14);
			}
			if (i == 2) {
				aBuffer_1192.putOp(98);
				aBuffer_1192.put8(k4 + k4 + 3);
			}
			aBuffer_1192.put16LEA(k6 + anInt1034);
			anInt1261 = anIntArray1280[0];
			anInt1262 = anIntArray1281[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				aBuffer_1192.put8(anIntArray1280[i4] - k6);
				aBuffer_1192.put8(anIntArray1281[i4] - i7);
			}
			aBuffer_1192.put16LE(i7 + anInt1035);
			aBuffer_1192.put8C((super.anIntArray30[5] != 1) ? 0 : 1);
			return true;
		}
		return i != 1;
	}

	public void method86(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			NPCEntity npc = aNpcArray835[k];
			int l = buffer.getU8();
			if ((l & 0x10) != 0) {
				int i1 = buffer.getU16LE();
				if (i1 == 65535) {
					i1 = -1;
				}
				int i2 = buffer.getU8();
				if ((i1 == npc.anInt1526) && (i1 != -1)) {
					int l2 = SeqType.aTypeArray351[i1].anInt365;
					if (l2 == 1) {
						npc.anInt1527 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if (l2 == 2) {
						npc.anInt1530 = 0;
					}
				} else if ((i1 == -1) || (npc.anInt1526 == -1) || (SeqType.aTypeArray351[i1].anInt359 >= SeqType.aTypeArray351[npc.anInt1526].anInt359)) {
					npc.anInt1526 = i1;
					npc.anInt1527 = 0;
					npc.anInt1528 = 0;
					npc.anInt1529 = i2;
					npc.anInt1530 = 0;
					npc.anInt1542 = npc.anInt1525;
				}
			}
			if ((l & 8) != 0) {
				int j1 = buffer.getU8A();
				int j2 = buffer.getU8C();
				npc.method447(j2, j1, anInt1161);
				npc.anInt1532 = anInt1161 + 300;
				npc.anInt1533 = buffer.getU8A();
				npc.anInt1534 = buffer.getU8();
			}
			if ((l & 0x80) != 0) {
				npc.anInt1520 = buffer.getU16();
				int k1 = buffer.get32();
				npc.anInt1524 = k1 >> 16;
				npc.anInt1523 = anInt1161 + (k1 & 0xffff);
				npc.anInt1521 = 0;
				npc.anInt1522 = 0;
				if (npc.anInt1523 > anInt1161) {
					npc.anInt1521 = -1;
				}
				if (npc.anInt1520 == 65535) {
					npc.anInt1520 = -1;
				}
			}
			if ((l & 0x20) != 0) {
				npc.anInt1502 = buffer.getU16();
				if (npc.anInt1502 == 65535) {
					npc.anInt1502 = -1;
				}
			}
			if ((l & 1) != 0) {
				npc.aString1506 = buffer.getString();
				npc.anInt1535 = 100;
			}
			if ((l & 0x40) != 0) {
				int l1 = buffer.getU8C();
				int k2 = buffer.getU8S();
				npc.method447(k2, l1, anInt1161);
				npc.anInt1532 = anInt1161 + 300;
				npc.anInt1533 = buffer.getU8S();
				npc.anInt1534 = buffer.getU8C();
			}
			if ((l & 2) != 0) {
				npc.aType_1696 = NPCType.method159(buffer.getU16LEA());
				npc.anInt1540 = npc.aType_1696.aByte68;
				npc.anInt1504 = npc.aType_1696.anInt79;
				npc.anInt1554 = npc.aType_1696.anInt67;
				npc.anInt1555 = npc.aType_1696.anInt58;
				npc.anInt1556 = npc.aType_1696.anInt83;
				npc.anInt1557 = npc.aType_1696.anInt55;
				npc.anInt1511 = npc.aType_1696.anInt77;
			}
			if ((l & 4) != 0) {
				npc.anInt1538 = buffer.getU16LE();
				npc.anInt1539 = buffer.getU16LE();
			}
		}
	}

	public void method87(NPCType type, int i, int j, int k) {
		if (anInt1133 >= 400) {
			return;
		}
		if (type.anIntArray88 != null) {
			type = type.method161();
		}
		if (type == null) {
			return;
		}
		if (!type.aBoolean84) {
			return;
		}
		String s = type.aString65;
		if (type.anInt61 != 0) {
			s = s + method110(aPlayer_1126.anInt1705, type.anInt61) + " (level-" + type.anInt61 + ")";
		}
		if (anInt1282 == 1) {
			aStringArray1199[anInt1133] = "Use " + aString1286 + " with @yel@" + s;
			anIntArray1093[anInt1133] = 582;
			anIntArray1094[anInt1133] = i;
			anIntArray1091[anInt1133] = k;
			anIntArray1092[anInt1133] = j;
			anInt1133++;
			return;
		}
		if (anInt1136 == 1) {
			if ((anInt1138 & 2) == 2) {
				aStringArray1199[anInt1133] = aString1139 + " @yel@" + s;
				anIntArray1093[anInt1133] = 413;
				anIntArray1094[anInt1133] = i;
				anIntArray1091[anInt1133] = k;
				anIntArray1092[anInt1133] = j;
				anInt1133++;
			}
		} else {
			if (type.aStringArray66 != null) {
				for (int l = 4; l >= 0; l--) {
					if ((type.aStringArray66[l] != null) && !type.aStringArray66[l].equalsIgnoreCase("attack")) {
						aStringArray1199[anInt1133] = type.aStringArray66[l] + " @yel@" + s;
						if (l == 0) {
							anIntArray1093[anInt1133] = 20;
						}
						if (l == 1) {
							anIntArray1093[anInt1133] = 412;
						}
						if (l == 2) {
							anIntArray1093[anInt1133] = 225;
						}
						if (l == 3) {
							anIntArray1093[anInt1133] = 965;
						}
						if (l == 4) {
							anIntArray1093[anInt1133] = 478;
						}
						anIntArray1094[anInt1133] = i;
						anIntArray1091[anInt1133] = k;
						anIntArray1092[anInt1133] = j;
						anInt1133++;
					}
				}
			}
			if (type.aStringArray66 != null) {
				for (int i1 = 4; i1 >= 0; i1--) {
					if ((type.aStringArray66[i1] != null) && type.aStringArray66[i1].equalsIgnoreCase("attack")) {
						char c = '\0';
						if (type.anInt61 > aPlayer_1126.anInt1705) {
							c = '\u07D0';
						}
						aStringArray1199[anInt1133] = type.aStringArray66[i1] + " @yel@" + s;
						if (i1 == 0) {
							anIntArray1093[anInt1133] = 20 + c;
						}
						if (i1 == 1) {
							anIntArray1093[anInt1133] = 412 + c;
						}
						if (i1 == 2) {
							anIntArray1093[anInt1133] = 225 + c;
						}
						if (i1 == 3) {
							anIntArray1093[anInt1133] = 965 + c;
						}
						if (i1 == 4) {
							anIntArray1093[anInt1133] = 478 + c;
						}
						anIntArray1094[anInt1133] = i;
						anIntArray1091[anInt1133] = k;
						anIntArray1092[anInt1133] = j;
						anInt1133++;
					}
				}
			}
			aStringArray1199[anInt1133] = "Examine @yel@" + s;
			anIntArray1093[anInt1133] = 1025;
			anIntArray1094[anInt1133] = i;
			anIntArray1091[anInt1133] = k;
			anIntArray1092[anInt1133] = j;
			anInt1133++;
		}
	}

	public void method88(int i, int j, PlayerEntity player, int k) {
		if (player == aPlayer_1126) {
			return;
		}
		if (anInt1133 >= 400) {
			return;
		}
		String s;
		if (player.anInt1723 == 0) {
			s = player.aString1703 + method110(aPlayer_1126.anInt1705, player.anInt1705) + " (level-" + player.anInt1705 + ")";
		} else {
			s = player.aString1703 + " (skill-" + player.anInt1723 + ")";
		}
		if (anInt1282 == 1) {
			aStringArray1199[anInt1133] = "Use " + aString1286 + " with @whi@" + s;
			anIntArray1093[anInt1133] = 491;
			anIntArray1094[anInt1133] = j;
			anIntArray1091[anInt1133] = i;
			anIntArray1092[anInt1133] = k;
			anInt1133++;
		} else if (anInt1136 == 1) {
			if ((anInt1138 & 8) == 8) {
				aStringArray1199[anInt1133] = aString1139 + " @whi@" + s;
				anIntArray1093[anInt1133] = 365;
				anIntArray1094[anInt1133] = j;
				anIntArray1091[anInt1133] = i;
				anIntArray1092[anInt1133] = k;
				anInt1133++;
			}
		} else {
			for (int l = 4; l >= 0; l--) {
				if (aStringArray1127[l] != null) {
					aStringArray1199[anInt1133] = aStringArray1127[l] + " @whi@" + s;
					char c = '\0';
					if (aStringArray1127[l].equalsIgnoreCase("attack")) {
						if (player.anInt1705 > aPlayer_1126.anInt1705) {
							c = '\u07D0';
						}
						if ((aPlayer_1126.anInt1701 != 0) && (player.anInt1701 != 0)) {
							if (aPlayer_1126.anInt1701 == player.anInt1701) {
								c = '\u07D0';
							} else {
								c = '\0';
							}
						}
					} else if (aBooleanArray1128[l]) {
						c = '\u07D0';
					}
					if (l == 0) {
						anIntArray1093[anInt1133] = 561 + c;
					}
					if (l == 1) {
						anIntArray1093[anInt1133] = 779 + c;
					}
					if (l == 2) {
						anIntArray1093[anInt1133] = 27 + c;
					}
					if (l == 3) {
						anIntArray1093[anInt1133] = 577 + c;
					}
					if (l == 4) {
						anIntArray1093[anInt1133] = 729 + c;
					}
					anIntArray1094[anInt1133] = j;
					anIntArray1091[anInt1133] = i;
					anIntArray1092[anInt1133] = k;
					anInt1133++;
				}
			}
		}
		for (int i1 = 0; i1 < anInt1133; i1++) {
			if (anIntArray1093[i1] == 516) {
				aStringArray1199[i1] = "Walk here @whi@" + s;
				return;
			}
		}
	}

	public void method89(SceneLocTemporary loc) {
		int i = 0;
		int j = -1;
		int k = 0;
		int l = 0;
		if (loc.anInt1296 == 0) {
			i = aGraph_946.method300(loc.anInt1295, loc.anInt1297, loc.anInt1298);
		}
		if (loc.anInt1296 == 1) {
			i = aGraph_946.method301(loc.anInt1295, loc.anInt1297, loc.anInt1298);
		}
		if (loc.anInt1296 == 2) {
			i = aGraph_946.method302(loc.anInt1295, loc.anInt1297, loc.anInt1298);
		}
		if (loc.anInt1296 == 3) {
			i = aGraph_946.method303(loc.anInt1295, loc.anInt1297, loc.anInt1298);
		}
		if (i != 0) {
			int i1 = aGraph_946.method304(loc.anInt1295, loc.anInt1297, loc.anInt1298, i);
			j = (i >> 14) & 0x7fff;
			k = i1 & 0x1f;
			l = i1 >> 6;
		}
		loc.anInt1299 = j;
		loc.anInt1301 = k;
		loc.anInt1300 = l;
	}

	public void method90() {
		for (int i = 0; i < anInt1062; i++) {
			if (anIntArray1250[i] <= 0) {
				boolean flag1 = false;
				try {
					if ((anIntArray1207[i] == anInt874) && (anIntArray1241[i] == anInt1289)) {
						if (!method27()) {
							flag1 = true;
						}
					} else {
						Buffer buffer = SoundTrack.method241(anIntArray1241[i], anIntArray1207[i]);
						if ((System.currentTimeMillis() + (long) (buffer.position / 22)) > (aLong1172 + (long) (anInt1257 / 22))) {
							anInt1257 = buffer.position;
							aLong1172 = System.currentTimeMillis();
							if (method59(buffer.data, buffer.position)) {
								anInt874 = anIntArray1207[i];
								anInt1289 = anIntArray1241[i];
							} else {
								flag1 = true;
							}
						}
					}
				} catch (Exception ignored) {
				}
				if (!flag1 || (anIntArray1250[i] == -5)) {
					anInt1062--;
					for (int j = i; j < anInt1062; j++) {
						anIntArray1207[j] = anIntArray1207[j + 1];
						anIntArray1241[j] = anIntArray1241[j + 1];
						anIntArray1250[j] = anIntArray1250[j + 1];
					}
					i--;
				} else {
					anIntArray1250[i] = -5;
				}
			} else {
				anIntArray1250[i]--;
			}
		}
		if (anInt1259 > 0) {
			anInt1259 -= 20;
			if (anInt1259 < 0) {
				anInt1259 = 0;
			}
			if ((anInt1259 == 0) && aBoolean1151 && !aBoolean960) {
				anInt1227 = anInt956;
				aBoolean1228 = true;
				aOnDemand_1068.method558(2, anInt1227);
			}
		}
	}

	public void method91(Buffer buffer, int i) {
		while ((buffer.bitPosition + 10) < (i * 8)) {
			int j = buffer.getN(11);
			if (j == 2047) {
				break;
			}
			if (aPlayerArray890[j] == null) {
				aPlayerArray890[j] = new PlayerEntity();
				if (aBufferArray895[j] != null) {
					aPlayerArray890[j].method451(aBufferArray895[j]);
				}
			}
			anIntArray892[anInt891++] = j;
			PlayerEntity player = aPlayerArray890[j];
			player.anInt1537 = anInt1161;
			int k = buffer.getN(1);
			if (k == 1) {
				anIntArray894[anInt893++] = j;
			}
			int l = buffer.getN(1);
			int i1 = buffer.getN(5);
			if (i1 > 15) {
				i1 -= 32;
			}
			int j1 = buffer.getN(5);
			if (j1 > 15) {
				j1 -= 32;
			}
			player.method445(aPlayer_1126.anIntArray1500[0] + j1, aPlayer_1126.anIntArray1501[0] + i1, l == 1);
		}
		buffer.accessBytes();
	}

	public void method92() {
		if (anInt1021 != 0) {
			return;
		}
		if (super.anInt26 == 1) {
			int i = super.anInt27 - 25 - 550;
			int j = super.anInt28 - 5 - 4;
			if ((i >= 0) && (j >= 0) && (i < 146) && (j < 151)) {
				i -= 73;
				j -= 75;
				int k = (anInt1185 + anInt1209) & 0x7ff;
				int i1 = Draw3D.sin[k];
				int j1 = Draw3D.cos[k];
				i1 = (i1 * (anInt1170 + 256)) >> 8;
				j1 = (j1 * (anInt1170 + 256)) >> 8;
				int k1 = ((j * i1) + (i * j1)) >> 11;
				int l1 = ((j * j1) - (i * i1)) >> 11;
				int i2 = (aPlayer_1126.anInt1550 + k1) >> 7;
				int j2 = (aPlayer_1126.anInt1551 - l1) >> 7;
				boolean flag1 = method85(1, 0, 0, 0, aPlayer_1126.anIntArray1501[0], 0, 0, j2, aPlayer_1126.anIntArray1500[0], true, i2);
				if (flag1) {
					aBuffer_1192.put8(i);
					aBuffer_1192.put8(j);
					aBuffer_1192.put16(anInt1185);
					aBuffer_1192.put8(57);
					aBuffer_1192.put8(anInt1209);
					aBuffer_1192.put8(anInt1170);
					aBuffer_1192.put8(89);
					aBuffer_1192.put16(aPlayer_1126.anInt1550);
					aBuffer_1192.put16(aPlayer_1126.anInt1551);
					aBuffer_1192.put8(anInt1264);
					aBuffer_1192.put8(63);
				}
			}
		}
	}

	public String method93(int j) {
		if (j < 999999999) {
			return String.valueOf(j);
		} else {
			return "*";
		}
	}

	public void method94() {
		Graphics g = method11().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		method4(1);
		if (aBoolean926) {
			aBoolean831 = false;
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 16));
			g.setColor(Color.yellow);
			int k = 35;
			g.drawString("Sorry, an error has occured whilst loading RuneScape", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
			k += 30;
			g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
			k += 30;
			g.drawString("3: Try using a different game-world", 30, k);
			k += 30;
			g.drawString("4: Try rebooting your computer", 30, k);
			k += 30;
			g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
		}
		if (aBoolean1176) {
			aBoolean831 = false;
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play RuneScape make sure you play from", 50, 100);
			g.drawString("http://www.runescape.com", 50, 150);
		}
		if (aBoolean1252) {
			aBoolean831 = false;
			g.setColor(Color.yellow);
			int l = 35;
			g.drawString("Error a copy of RuneScape already appears to be loaded", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
			l += 30;
			g.drawString("2: Try rebooting your computer, and reloading", 30, l);
		}
	}

	public void method95() {
		for (int j = 0; j < anInt836; j++) {
			int k = anIntArray837[j];
			NPCEntity npc = aNpcArray835[k];
			if (npc != null) {
				method96(npc);
			}
		}
	}

	public void method96(PathingEntity entity) {
		if ((entity.anInt1550 < 128) || (entity.anInt1551 < 128) || (entity.anInt1550 >= 13184) || (entity.anInt1551 >= 13184)) {
			entity.anInt1526 = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.anInt1550 = (entity.anIntArray1500[0] * 128) + (entity.anInt1540 * 64);
			entity.anInt1551 = (entity.anIntArray1501[0] * 128) + (entity.anInt1540 * 64);
			entity.method446();
		}
		if ((entity == aPlayer_1126) && ((entity.anInt1550 < 1536) || (entity.anInt1551 < 1536) || (entity.anInt1550 >= 11776) || (entity.anInt1551 >= 11776))) {
			entity.anInt1526 = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.anInt1550 = (entity.anIntArray1500[0] * 128) + (entity.anInt1540 * 64);
			entity.anInt1551 = (entity.anIntArray1501[0] * 128) + (entity.anInt1540 * 64);
			entity.method446();
		}
		if (entity.anInt1547 > anInt1161) {
			method97(entity);
		} else if (entity.anInt1548 >= anInt1161) {
			method98(entity);
		} else {
			method99(entity);
		}
		method100(entity);
		method101(entity);
	}

	public void method97(PathingEntity entity) {
		int i = entity.anInt1547 - anInt1161;
		int j = (entity.anInt1543 * 128) + (entity.anInt1540 * 64);
		int k = (entity.anInt1545 * 128) + (entity.anInt1540 * 64);
		entity.anInt1550 += (j - entity.anInt1550) / i;
		entity.anInt1551 += (k - entity.anInt1551) / i;
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0) {
			entity.anInt1510 = 1024;
		}
		if (entity.anInt1549 == 1) {
			entity.anInt1510 = 1536;
		}
		if (entity.anInt1549 == 2) {
			entity.anInt1510 = 0;
		}
		if (entity.anInt1549 == 3) {
			entity.anInt1510 = 512;
		}
	}

	public void method98(PathingEntity entity) {
		if ((entity.anInt1548 == anInt1161) || (entity.anInt1526 == -1) || (entity.anInt1529 != 0) || ((entity.anInt1528 + 1) > SeqType.aTypeArray351[entity.anInt1526].method258(entity.anInt1527))) {
			int i = entity.anInt1548 - entity.anInt1547;
			int j = anInt1161 - entity.anInt1547;
			int k = (entity.anInt1543 * 128) + (entity.anInt1540 * 64);
			int l = (entity.anInt1545 * 128) + (entity.anInt1540 * 64);
			int i1 = (entity.anInt1544 * 128) + (entity.anInt1540 * 64);
			int j1 = (entity.anInt1546 * 128) + (entity.anInt1540 * 64);
			entity.anInt1550 = ((k * (i - j)) + (i1 * j)) / i;
			entity.anInt1551 = ((l * (i - j)) + (j1 * j)) / i;
		}
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0) {
			entity.anInt1510 = 1024;
		}
		if (entity.anInt1549 == 1) {
			entity.anInt1510 = 1536;
		}
		if (entity.anInt1549 == 2) {
			entity.anInt1510 = 0;
		}
		if (entity.anInt1549 == 3) {
			entity.anInt1510 = 512;
		}
		entity.anInt1552 = entity.anInt1510;
	}

	public void method99(PathingEntity entity) {
		entity.anInt1517 = entity.anInt1511;
		if (entity.anInt1525 == 0) {
			entity.anInt1503 = 0;
			return;
		}
		if ((entity.anInt1526 != -1) && (entity.anInt1529 == 0)) {
			SeqType type = SeqType.aTypeArray351[entity.anInt1526];
			if ((entity.anInt1542 > 0) && (type.anInt363 == 0)) {
				entity.anInt1503++;
				return;
			}
			if ((entity.anInt1542 <= 0) && (type.anInt364 == 0)) {
				entity.anInt1503++;
				return;
			}
		}
		int i = entity.anInt1550;
		int j = entity.anInt1551;
		int k = (entity.anIntArray1500[entity.anInt1525 - 1] * 128) + (entity.anInt1540 * 64);
		int l = (entity.anIntArray1501[entity.anInt1525 - 1] * 128) + (entity.anInt1540 * 64);
		if (((k - i) > 256) || ((k - i) < -256) || ((l - j) > 256) || ((l - j) < -256)) {
			entity.anInt1550 = k;
			entity.anInt1551 = l;
			return;
		}
		if (i < k) {
			if (j < l) {
				entity.anInt1510 = 1280;
			} else if (j > l) {
				entity.anInt1510 = 1792;
			} else {
				entity.anInt1510 = 1536;
			}
		} else if (i > k) {
			if (j < l) {
				entity.anInt1510 = 768;
			} else if (j > l) {
				entity.anInt1510 = 256;
			} else {
				entity.anInt1510 = 512;
			}
		} else if (j < l) {
			entity.anInt1510 = 1024;
		} else {
			entity.anInt1510 = 0;
		}
		int i1 = (entity.anInt1510 - entity.anInt1552) & 0x7ff;
		if (i1 > 1024) {
			i1 -= 2048;
		}
		int j1 = entity.anInt1555;
		if ((i1 >= -256) && (i1 <= 256)) {
			j1 = entity.anInt1554;
		} else if ((i1 >= 256) && (i1 < 768)) {
			j1 = entity.anInt1557;
		} else if ((i1 >= -768) && (i1 <= -256)) {
			j1 = entity.anInt1556;
		}
		if (j1 == -1) {
			j1 = entity.anInt1554;
		}
		entity.anInt1517 = j1;
		int k1 = 4;
		if ((entity.anInt1552 != entity.anInt1510) && (entity.anInt1502 == -1) && (entity.anInt1504 != 0)) {
			k1 = 2;
		}
		if (entity.anInt1525 > 2) {
			k1 = 6;
		}
		if (entity.anInt1525 > 3) {
			k1 = 8;
		}
		if ((entity.anInt1503 > 0) && (entity.anInt1525 > 1)) {
			k1 = 8;
			entity.anInt1503--;
		}
		if (entity.aBooleanArray1553[entity.anInt1525 - 1]) {
			k1 <<= 1;
		}
		if ((k1 >= 8) && (entity.anInt1517 == entity.anInt1554) && (entity.anInt1505 != -1)) {
			entity.anInt1517 = entity.anInt1505;
		}
		if (i < k) {
			entity.anInt1550 += k1;
			if (entity.anInt1550 > k) {
				entity.anInt1550 = k;
			}
		} else if (i > k) {
			entity.anInt1550 -= k1;
			if (entity.anInt1550 < k) {
				entity.anInt1550 = k;
			}
		}
		if (j < l) {
			entity.anInt1551 += k1;
			if (entity.anInt1551 > l) {
				entity.anInt1551 = l;
			}
		} else if (j > l) {
			entity.anInt1551 -= k1;
			if (entity.anInt1551 < l) {
				entity.anInt1551 = l;
			}
		}
		if ((entity.anInt1550 == k) && (entity.anInt1551 == l)) {
			entity.anInt1525--;
			if (entity.anInt1542 > 0) {
				entity.anInt1542--;
			}
		}
	}

	public void method100(PathingEntity entity) {
		if (entity.anInt1504 == 0) {
			return;
		}
		if ((entity.anInt1502 != -1) && (entity.anInt1502 < 32768)) {
			NPCEntity npc = aNpcArray835[entity.anInt1502];
			if (npc != null) {
				int i1 = entity.anInt1550 - npc.anInt1550;
				int k1 = entity.anInt1551 - npc.anInt1551;
				if ((i1 != 0) || (k1 != 0)) {
					entity.anInt1510 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
				}
			}
		}
		if (entity.anInt1502 >= 32768) {
			int j = entity.anInt1502 - 32768;
			if (j == anInt884) {
				j = anInt889;
			}
			PlayerEntity player = aPlayerArray890[j];
			if (player != null) {
				int l1 = entity.anInt1550 - player.anInt1550;
				int i2 = entity.anInt1551 - player.anInt1551;
				if ((l1 != 0) || (i2 != 0)) {
					entity.anInt1510 = (int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
				}
			}
		}
		if (((entity.anInt1538 != 0) || (entity.anInt1539 != 0)) && ((entity.anInt1525 == 0) || (entity.anInt1503 > 0))) {
			int k = entity.anInt1550 - ((entity.anInt1538 - anInt1034 - anInt1034) * 64);
			int j1 = entity.anInt1551 - ((entity.anInt1539 - anInt1035 - anInt1035) * 64);
			if ((k != 0) || (j1 != 0)) {
				entity.anInt1510 = (int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
			}
			entity.anInt1538 = 0;
			entity.anInt1539 = 0;
		}
		int l = (entity.anInt1510 - entity.anInt1552) & 0x7ff;
		if (l != 0) {
			if ((l < entity.anInt1504) || (l > (2048 - entity.anInt1504))) {
				entity.anInt1552 = entity.anInt1510;
			} else if (l > 1024) {
				entity.anInt1552 -= entity.anInt1504;
			} else {
				entity.anInt1552 += entity.anInt1504;
			}
			entity.anInt1552 &= 0x7ff;
			if ((entity.anInt1517 == entity.anInt1511) && (entity.anInt1552 != entity.anInt1510)) {
				if (entity.anInt1512 != -1) {
					entity.anInt1517 = entity.anInt1512;
					return;
				}
				entity.anInt1517 = entity.anInt1554;
			}
		}
	}

	public void method101(PathingEntity entity) {
		entity.aBoolean1541 = false;
		if (entity.anInt1517 != -1) {
			SeqType type = SeqType.aTypeArray351[entity.anInt1517];
			entity.anInt1519++;
			if ((entity.anInt1518 < type.anInt352) && (entity.anInt1519 > type.method258(entity.anInt1518))) {
				entity.anInt1519 = 0;
				entity.anInt1518++;
			}
			if (entity.anInt1518 >= type.anInt352) {
				entity.anInt1519 = 0;
				entity.anInt1518 = 0;
			}
		}
		if ((entity.anInt1520 != -1) && (anInt1161 >= entity.anInt1523)) {
			if (entity.anInt1521 < 0) {
				entity.anInt1521 = 0;
			}
			SeqType type_1 = SpotAnimType.aTypeArray403[entity.anInt1520].aType_407;
			for (entity.anInt1522++; (entity.anInt1521 < type_1.anInt352) && (entity.anInt1522 > type_1.method258(entity.anInt1521)); entity.anInt1521++) {
				entity.anInt1522 -= type_1.method258(entity.anInt1521);
			}
			if ((entity.anInt1521 >= type_1.anInt352) && ((entity.anInt1521 < 0) || (entity.anInt1521 >= type_1.anInt352))) {
				entity.anInt1520 = -1;
			}
		}
		if ((entity.anInt1526 != -1) && (entity.anInt1529 <= 1)) {
			SeqType type_2 = SeqType.aTypeArray351[entity.anInt1526];
			if ((type_2.anInt363 == 1) && (entity.anInt1542 > 0) && (entity.anInt1547 <= anInt1161) && (entity.anInt1548 < anInt1161)) {
				entity.anInt1529 = 1;
				return;
			}
		}
		if ((entity.anInt1526 != -1) && (entity.anInt1529 == 0)) {
			SeqType type_3 = SeqType.aTypeArray351[entity.anInt1526];
			for (entity.anInt1528++; (entity.anInt1527 < type_3.anInt352) && (entity.anInt1528 > type_3.method258(entity.anInt1527)); entity.anInt1527++) {
				entity.anInt1528 -= type_3.method258(entity.anInt1527);
			}
			if (entity.anInt1527 >= type_3.anInt352) {
				entity.anInt1527 -= type_3.anInt356;
				entity.anInt1530++;
				if (entity.anInt1530 >= type_3.anInt362) {
					entity.anInt1526 = -1;
				}
				if ((entity.anInt1527 < 0) || (entity.anInt1527 >= type_3.anInt352)) {
					entity.anInt1526 = -1;
				}
			}
			entity.aBoolean1541 = type_3.aBoolean358;
		}
		if (entity.anInt1529 > 0) {
			entity.anInt1529--;
		}
	}

	public void method102() {
		if (aBoolean1255) {
			aBoolean1255 = false;
			aArea_903.method238(4, super.aGraphics12, 0);
			aArea_904.method238(357, super.aGraphics12, 0);
			aArea_905.method238(4, super.aGraphics12, 722);
			aArea_906.method238(205, super.aGraphics12, 743);
			aArea_907.method238(0, super.aGraphics12, 0);
			aArea_908.method238(4, super.aGraphics12, 516);
			aArea_909.method238(205, super.aGraphics12, 516);
			aArea_910.method238(357, super.aGraphics12, 496);
			aArea_911.method238(338, super.aGraphics12, 0);
			aBoolean1153 = true;
			aBoolean1223 = true;
			aBoolean1103 = true;
			aBoolean1233 = true;
			if (anInt1023 != 2) {
				aArea_1165.method238(4, super.aGraphics12, 4);
				aArea_1164.method238(4, super.aGraphics12, 550);
			}
		}
		if (anInt1023 == 2) {
			method146();
		}
		if (aBoolean885 && (anInt948 == 1)) {
			aBoolean1153 = true;
		}
		if (anInt1189 != -1) {
			boolean flag1 = method119(anInt945, anInt1189);
			if (flag1) {
				aBoolean1153 = true;
			}
		}
		if (anInt1246 == 2) {
			aBoolean1153 = true;
		}
		if (anInt1086 == 2) {
			aBoolean1153 = true;
		}
		if (aBoolean1153) {
			method36();
			aBoolean1153 = false;
		}
		if (anInt1276 == -1) {
			aComponent_1059.anInt224 = anInt1211 - anInt1089 - 77;
			if ((super.anInt20 > 448) && (super.anInt20 < 560) && (super.anInt21 > 332)) {
				method65(463, 77, super.anInt20 - 17, super.anInt21 - 357, aComponent_1059, 0, false, anInt1211);
			}
			int i = anInt1211 - 77 - aComponent_1059.anInt224;
			if (i < 0) {
				i = 0;
			}
			if (i > (anInt1211 - 77)) {
				i = anInt1211 - 77;
			}
			if (anInt1089 != i) {
				anInt1089 = i;
				aBoolean1223 = true;
			}
		}
		if (anInt1276 != -1) {
			boolean flag2 = method119(anInt945, anInt1276);
			if (flag2) {
				aBoolean1223 = true;
			}
		}
		if (anInt1246 == 3) {
			aBoolean1223 = true;
		}
		if (anInt1086 == 3) {
			aBoolean1223 = true;
		}
		if (aString844 != null) {
			aBoolean1223 = true;
		}
		if (aBoolean885 && (anInt948 == 2)) {
			aBoolean1223 = true;
		}
		if (aBoolean1223) {
			method18();
			aBoolean1223 = false;
		}
		if (anInt1023 == 2) {
			method126();
			aArea_1164.method238(4, super.aGraphics12, 550);
		}
		if (anInt1054 != -1) {
			aBoolean1103 = true;
		}
		if (aBoolean1103) {
			if ((anInt1054 != -1) && (anInt1054 == anInt1221)) {
				anInt1054 = -1;
				aBuffer_1192.putOp(120);
				aBuffer_1192.put8(anInt1221);
			}
			aBoolean1103 = false;
			aArea_1125.method237();
			aImage_1029.method361(0, 0);
			if (anInt1189 == -1) {
				if (anIntArray1130[anInt1221] != -1) {
					if (anInt1221 == 0) {
						aImage_1143.method361(22, 10);
					}
					if (anInt1221 == 1) {
						aImage_1144.method361(54, 8);
					}
					if (anInt1221 == 2) {
						aImage_1144.method361(82, 8);
					}
					if (anInt1221 == 3) {
						aImage_1145.method361(110, 8);
					}
					if (anInt1221 == 4) {
						aImage_1147.method361(153, 8);
					}
					if (anInt1221 == 5) {
						aImage_1147.method361(181, 8);
					}
					if (anInt1221 == 6) {
						aImage_1146.method361(209, 9);
					}
				}
				if ((anIntArray1130[0] != -1) && ((anInt1054 != 0) || ((anInt1161 % 20) < 10))) {
					aImageArray947[0].method361(29, 13);
				}
				if ((anIntArray1130[1] != -1) && ((anInt1054 != 1) || ((anInt1161 % 20) < 10))) {
					aImageArray947[1].method361(53, 11);
				}
				if ((anIntArray1130[2] != -1) && ((anInt1054 != 2) || ((anInt1161 % 20) < 10))) {
					aImageArray947[2].method361(82, 11);
				}
				if ((anIntArray1130[3] != -1) && ((anInt1054 != 3) || ((anInt1161 % 20) < 10))) {
					aImageArray947[3].method361(115, 12);
				}
				if ((anIntArray1130[4] != -1) && ((anInt1054 != 4) || ((anInt1161 % 20) < 10))) {
					aImageArray947[4].method361(153, 13);
				}
				if ((anIntArray1130[5] != -1) && ((anInt1054 != 5) || ((anInt1161 % 20) < 10))) {
					aImageArray947[5].method361(180, 11);
				}
				if ((anIntArray1130[6] != -1) && ((anInt1054 != 6) || ((anInt1161 % 20) < 10))) {
					aImageArray947[6].method361(208, 13);
				}
			}
			aArea_1125.method238(160, super.aGraphics12, 516);
			aArea_1124.method237();
			aImage_1028.method361(0, 0);
			if (anInt1189 == -1) {
				if (anIntArray1130[anInt1221] != -1) {
					if (anInt1221 == 7) {
						aImage_865.method361(42, 0);
					}
					if (anInt1221 == 8) {
						aImage_866.method361(74, 0);
					}
					if (anInt1221 == 9) {
						aImage_866.method361(102, 0);
					}
					if (anInt1221 == 10) {
						aImage_867.method361(130, 1);
					}
					if (anInt1221 == 11) {
						aImage_869.method361(173, 0);
					}
					if (anInt1221 == 12) {
						aImage_869.method361(201, 0);
					}
					if (anInt1221 == 13) {
						aImage_868.method361(229, 0);
					}
				}
				if ((anIntArray1130[8] != -1) && ((anInt1054 != 8) || ((anInt1161 % 20) < 10))) {
					aImageArray947[7].method361(74, 2);
				}
				if ((anIntArray1130[9] != -1) && ((anInt1054 != 9) || ((anInt1161 % 20) < 10))) {
					aImageArray947[8].method361(102, 3);
				}
				if ((anIntArray1130[10] != -1) && ((anInt1054 != 10) || ((anInt1161 % 20) < 10))) {
					aImageArray947[9].method361(137, 4);
				}
				if ((anIntArray1130[11] != -1) && ((anInt1054 != 11) || ((anInt1161 % 20) < 10))) {
					aImageArray947[10].method361(174, 2);
				}
				if ((anIntArray1130[12] != -1) && ((anInt1054 != 12) || ((anInt1161 % 20) < 10))) {
					aImageArray947[11].method361(201, 2);
				}
				if ((anIntArray1130[13] != -1) && ((anInt1054 != 13) || ((anInt1161 % 20) < 10))) {
					aImageArray947[12].method361(226, 2);
				}
			}
			aArea_1124.method238(466, super.aGraphics12, 496);
			aArea_1165.method237();
		}
		if (aBoolean1233) {
			aBoolean1233 = false;
			aArea_1123.method237();
			aImage_1027.method361(0, 0);
			aFont_1271.method382(0xffffff, 55, "Public chat", 28, true);
			if (anInt1287 == 0) {
				aFont_1271.method382(65280, 55, "On", 41, true);
			}
			if (anInt1287 == 1) {
				aFont_1271.method382(0xffff00, 55, "Friends", 41, true);
			}
			if (anInt1287 == 2) {
				aFont_1271.method382(0xff0000, 55, "Off", 41, true);
			}
			if (anInt1287 == 3) {
				aFont_1271.method382(65535, 55, "Hide", 41, true);
			}
			aFont_1271.method382(0xffffff, 184, "Private chat", 28, true);
			if (anInt845 == 0) {
				aFont_1271.method382(65280, 184, "On", 41, true);
			}
			if (anInt845 == 1) {
				aFont_1271.method382(0xffff00, 184, "Friends", 41, true);
			}
			if (anInt845 == 2) {
				aFont_1271.method382(0xff0000, 184, "Off", 41, true);
			}
			aFont_1271.method382(0xffffff, 324, "Trade/compete", 28, true);
			if (anInt1248 == 0) {
				aFont_1271.method382(65280, 324, "On", 41, true);
			}
			if (anInt1248 == 1) {
				aFont_1271.method382(0xffff00, 324, "Friends", 41, true);
			}
			if (anInt1248 == 2) {
				aFont_1271.method382(0xff0000, 324, "Off", 41, true);
			}
			aFont_1271.method382(0xffffff, 458, "Report abuse", 33, true);
			aArea_1123.method238(453, super.aGraphics12, 0);
			aArea_1165.method237();
		}
		anInt945 = 0;
	}

	public boolean method103(Component component) {
		int i = component.anInt214;
		if (((i >= 1) && (i <= 200)) || ((i >= 701) && (i <= 900))) {
			if (i >= 801) {
				i -= 701;
			} else if (i >= 701) {
				i -= 601;
			} else if (i >= 101) {
				i -= 101;
			} else {
				i--;
			}
			aStringArray1199[anInt1133] = "Remove @whi@" + aStringArray1082[i];
			anIntArray1093[anInt1133] = 792;
			anInt1133++;
			aStringArray1199[anInt1133] = "Message @whi@" + aStringArray1082[i];
			anIntArray1093[anInt1133] = 639;
			anInt1133++;
			return true;
		}
		if ((i >= 401) && (i <= 500)) {
			aStringArray1199[anInt1133] = "Remove @whi@" + component.aString248;
			anIntArray1093[anInt1133] = 322;
			anInt1133++;
			return true;
		} else {
			return false;
		}
	}

	public void method104() {
		SpotAnimEntity spotAnim = (SpotAnimEntity) aList_1056.method252();
		for (; spotAnim != null; spotAnim = (SpotAnimEntity) aList_1056.method254()) {
			if ((spotAnim.anInt1560 != anInt918) || spotAnim.aBoolean1567) {
				spotAnim.method329();
			} else if (anInt1161 >= spotAnim.anInt1564) {
				spotAnim.method454(anInt945);
				if (spotAnim.aBoolean1567) {
					spotAnim.method329();
				} else {
					aGraph_946.method285(spotAnim.anInt1560, 0, spotAnim.anInt1563, -1, spotAnim.anInt1562, 60, spotAnim.anInt1561, spotAnim, false);
				}
			}
		}
	}

	public void method105(int j, int k, Component component, int l) {
		if ((component.anInt262 != 0) || (component.anIntArray240 == null)) {
			return;
		}
		if (component.aBoolean266 && (anInt1026 != component.anInt250) && (anInt1048 != component.anInt250) && (anInt1039 != component.anInt250)) {
			return;
		}
		int i1 = Draw2D.left;
		int j1 = Draw2D.top;
		int k1 = Draw2D.right;
		int l1 = Draw2D.bottom;
		Draw2D.setBounds(l + component.anInt267, k, k + component.anInt220, l);
		int i2 = component.anIntArray240.length;
		for (int j2 = 0; j2 < i2; j2++) {
			int k2 = component.anIntArray241[j2] + k;
			int l2 = (component.anIntArray272[j2] + l) - j;
			Component component_1 = Component.aComponentArray210[component.anIntArray240[j2]];
			k2 += component_1.anInt263;
			l2 += component_1.anInt265;
			if (component_1.anInt214 > 0) {
				method75(component_1);
			}
			if (component_1.anInt262 == 0) {
				if (component_1.anInt224 > (component_1.anInt261 - component_1.anInt267)) {
					component_1.anInt224 = component_1.anInt261 - component_1.anInt267;
				}
				if (component_1.anInt224 < 0) {
					component_1.anInt224 = 0;
				}
				method105(component_1.anInt224, k2, component_1, l2);
				if (component_1.anInt261 > component_1.anInt267) {
					method30(component_1.anInt267, component_1.anInt224, l2, k2 + component_1.anInt220, component_1.anInt261);
				}
			} else if (component_1.anInt262 != 1) {
				if (component_1.anInt262 == 2) {
					int i3 = 0;
					for (int l3 = 0; l3 < component_1.anInt267; l3++) {
						for (int l4 = 0; l4 < component_1.anInt220; l4++) {
							int k5 = k2 + (l4 * (32 + component_1.anInt231));
							int j6 = l2 + (l3 * (32 + component_1.anInt244));
							if (i3 < 20) {
								k5 += component_1.anIntArray215[i3];
								j6 += component_1.anIntArray247[i3];
							}
							if (component_1.anIntArray253[i3] > 0) {
								int k6 = 0;
								int j7 = 0;
								int j9 = component_1.anIntArray253[i3] - 1;
								if (((k5 > (Draw2D.left - 32)) && (k5 < Draw2D.right) && (j6 > (Draw2D.top - 32)) && (j6 < Draw2D.bottom)) || ((anInt1086 != 0) && (anInt1085 == i3))) {
									int l9 = 0;
									if ((anInt1282 == 1) && (anInt1283 == i3) && (anInt1284 == component_1.anInt250)) {
										l9 = 0xffffff;
									}
									Image24 class30_sub2_sub1_sub1_2 = ObjType.method200(j9, component_1.anIntArray252[i3], l9);
									if (class30_sub2_sub1_sub1_2 != null) {
										if ((anInt1086 != 0) && (anInt1085 == i3) && (anInt1084 == component_1.anInt250)) {
											k6 = super.anInt20 - anInt1087;
											j7 = super.anInt21 - anInt1088;
											if ((k6 < 5) && (k6 > -5)) {
												k6 = 0;
											}
											if ((j7 < 5) && (j7 > -5)) {
												j7 = 0;
											}
											if (anInt989 < 5) {
												k6 = 0;
												j7 = 0;
											}
											class30_sub2_sub1_sub1_2.method350(k5 + k6, j6 + j7, 128);
											if (((j6 + j7) < Draw2D.top) && (component.anInt224 > 0)) {
												int i10 = (anInt945 * (Draw2D.top - j6 - j7)) / 3;
												if (i10 > (anInt945 * 10)) {
													i10 = anInt945 * 10;
												}
												if (i10 > component.anInt224) {
													i10 = component.anInt224;
												}
												component.anInt224 -= i10;
												anInt1088 += i10;
											}
											if (((j6 + j7 + 32) > Draw2D.bottom) && (component.anInt224 < (component.anInt261 - component.anInt267))) {
												int j10 = (anInt945 * ((j6 + j7 + 32) - Draw2D.bottom)) / 3;
												if (j10 > (anInt945 * 10)) {
													j10 = anInt945 * 10;
												}
												if (j10 > (component.anInt261 - component.anInt267 - component.anInt224)) {
													j10 = component.anInt261 - component.anInt267 - component.anInt224;
												}
												component.anInt224 += j10;
												anInt1088 -= j10;
											}
										} else if ((anInt1246 != 0) && (anInt1245 == i3) && (anInt1244 == component_1.anInt250)) {
											class30_sub2_sub1_sub1_2.method350(k5, j6, 128);
										} else {
											class30_sub2_sub1_sub1_2.method348(k5, j6);
										}
										if ((class30_sub2_sub1_sub1_2.anInt1444 == 33) || (component_1.anIntArray252[i3] != 1)) {
											int k10 = component_1.anIntArray252[i3];
											aFont_1270.method385(0, method43(k10), j6 + 10 + j7, k5 + 1 + k6);
											aFont_1270.method385(0xffff00, method43(k10), j6 + 9 + j7, k5 + k6);
										}
									}
								}
							} else if ((component_1.aImageArray209 != null) && (i3 < 20)) {
								Image24 class30_sub2_sub1_sub1_1 = component_1.aImageArray209[i3];
								if (class30_sub2_sub1_sub1_1 != null) {
									class30_sub2_sub1_sub1_1.method348(k5, j6);
								}
							}
							i3++;
						}
					}
				} else if (component_1.anInt262 == 3) {
					boolean flag = false;
					if ((anInt1039 == component_1.anInt250) || (anInt1048 == component_1.anInt250) || (anInt1026 == component_1.anInt250)) {
						flag = true;
					}
					int j3;
					if (method131(component_1)) {
						j3 = component_1.anInt219;
						if (flag && (component_1.anInt239 != 0)) {
							j3 = component_1.anInt239;
						}
					} else {
						j3 = component_1.anInt232;
						if (flag && (component_1.anInt216 != 0)) {
							j3 = component_1.anInt216;
						}
					}
					if (component_1.aByte254 == 0) {
						if (component_1.aBoolean227) {
							Draw2D.fillRect(k2, l2, component_1.anInt220, component_1.anInt267, j3);
						} else {
							Draw2D.drawRect(k2, l2, component_1.anInt220, component_1.anInt267, j3);
						}
					} else if (component_1.aBoolean227) {
						Draw2D.fillRect(k2, l2, component_1.anInt220, component_1.anInt267, j3, 256 - (component_1.aByte254 & 0xff));
					} else {
						Draw2D.drawRect(k2, l2, component_1.anInt220, component_1.anInt267, j3, 256 - (component_1.aByte254 & 0xff));
					}
				} else if (component_1.anInt262 == 4) {
					BitmapFont font = component_1.aFont_243;
					String s = component_1.aString248;
					boolean flag1 = false;
					if ((anInt1039 == component_1.anInt250) || (anInt1048 == component_1.anInt250) || (anInt1026 == component_1.anInt250)) {
						flag1 = true;
					}
					int i4;
					if (method131(component_1)) {
						i4 = component_1.anInt219;
						if (flag1 && (component_1.anInt239 != 0)) {
							i4 = component_1.anInt239;
						}
						if (component_1.aString228.length() > 0) {
							s = component_1.aString228;
						}
					} else {
						i4 = component_1.anInt232;
						if (flag1 && (component_1.anInt216 != 0)) {
							i4 = component_1.anInt216;
						}
					}
					if ((component_1.anInt217 == 6) && aBoolean1149) {
						s = "Please wait...";
						i4 = component_1.anInt232;
					}
					if (Draw2D.width == 479) {
						if (i4 == 0xffff00) {
							i4 = 255;
						}
						if (i4 == 49152) {
							i4 = 0xffffff;
						}
					}
					for (int l6 = l2 + font.anInt1497; s.length() > 0; l6 += font.anInt1497) {
						if (s.contains("%")) {
							do {
								int k7 = s.indexOf("%1");
								if (k7 == -1) {
									break;
								}
								s = s.substring(0, k7) + method93(method124(component_1, 0)) + s.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s.indexOf("%2");
								if (l7 == -1) {
									break;
								}
								s = s.substring(0, l7) + method93(method124(component_1, 1)) + s.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s.indexOf("%3");
								if (i8 == -1) {
									break;
								}
								s = s.substring(0, i8) + method93(method124(component_1, 2)) + s.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s.indexOf("%4");
								if (j8 == -1) {
									break;
								}
								s = s.substring(0, j8) + method93(method124(component_1, 3)) + s.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s.indexOf("%5");
								if (k8 == -1) {
									break;
								}
								s = s.substring(0, k8) + method93(method124(component_1, 4)) + s.substring(k8 + 2);
							} while (true);
						}
						int l8 = s.indexOf("\\n");
						String s1;
						if (l8 != -1) {
							s1 = s.substring(0, l8);
							s = s.substring(l8 + 2);
						} else {
							s1 = s;
							s = "";
						}
						if (component_1.aBoolean223) {
							font.method382(i4, k2 + (component_1.anInt220 / 2), s1, l6, component_1.aBoolean268);
						} else {
							font.method389(component_1.aBoolean268, k2, i4, s1, l6);
						}
					}
				} else if (component_1.anInt262 == 5) {
					Image24 image;
					if (method131(component_1)) {
						image = component_1.aImage_260;
					} else {
						image = component_1.aImage_207;
					}
					if (image != null) {
						image.method348(k2, l2);
					}
				} else if (component_1.anInt262 == 6) {
					int k3 = Draw3D.centerX;
					int j4 = Draw3D.centerY;
					Draw3D.centerX = k2 + (component_1.anInt220 / 2);
					Draw3D.centerY = l2 + (component_1.anInt267 / 2);
					int i5 = (Draw3D.sin[component_1.anInt270] * component_1.anInt269) >> 16;
					int l5 = (Draw3D.cos[component_1.anInt270] * component_1.anInt269) >> 16;
					boolean flag2 = method131(component_1);
					int i7;
					if (flag2) {
						i7 = component_1.anInt258;
					} else {
						i7 = component_1.anInt257;
					}
					Model model;
					if (i7 == -1) {
						model = component_1.method209(-1, -1, flag2);
					} else {
						SeqType type = SeqType.aTypeArray351[i7];
						model = component_1.method209(type.anIntArray354[component_1.anInt246], type.anIntArray353[component_1.anInt246], flag2);
					}
					if (model != null) {
						model.drawSimple(0, component_1.anInt271, 0, component_1.anInt270, 0, i5, l5);
					}
					Draw3D.centerX = k3;
					Draw3D.centerY = j4;
				} else if (component_1.anInt262 == 7) {
					BitmapFont class30_sub2_sub1_sub4_1 = component_1.aFont_243;
					int k4 = 0;
					for (int j5 = 0; j5 < component_1.anInt267; j5++) {
						for (int i6 = 0; i6 < component_1.anInt220; i6++) {
							if (component_1.anIntArray253[k4] > 0) {
								ObjType type = ObjType.method198(component_1.anIntArray253[k4] - 1);
								String s2 = type.aString170;
								if (type.aBoolean176 || (component_1.anIntArray252[k4] != 1)) {
									s2 = s2 + " x" + method14(component_1.anIntArray252[k4]);
								}
								int i9 = k2 + (i6 * (115 + component_1.anInt231));
								int k9 = l2 + (j5 * (12 + component_1.anInt244));
								if (component_1.aBoolean223) {
									class30_sub2_sub1_sub4_1.method382(component_1.anInt232, i9 + (component_1.anInt220 / 2), s2, k9, component_1.aBoolean268);
								} else {
									class30_sub2_sub1_sub4_1.method389(component_1.aBoolean268, i9, component_1.anInt232, s2, k9);
								}
							}
							k4++;
						}
					}
				}
			}
		}
		Draw2D.setBounds(l1, i1, k1, j1);
	}

	public void method106(Image8 image) {
		int j = 256;
		Arrays.fill(anIntArray1190, 0);
		for (int l = 0; l < 5000; l++) {
			int i1 = (int) (Math.random() * 128D * (double) j);
			anIntArray1190[i1] = (int) (Math.random() * 256D);
		}
		for (int j1 = 0; j1 < 20; j1++) {
			for (int k1 = 1; k1 < (j - 1); k1++) {
				for (int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
				}
			}
			int[] ai = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if (image != null) {
			int l1 = 0;
			for (int j2 = 0; j2 < image.anInt1453; j2++) {
				for (int l2 = 0; l2 < image.anInt1452; l2++) {
					if (image.aByteArray1450[l1++] != 0) {
						int i3 = l2 + 16 + image.anInt1454;
						int j3 = j2 + 16 + image.anInt1455;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
				}
			}
		}
	}

	public void method107(int i, int j, Buffer buffer, PlayerEntity player) {
		if ((i & 0x400) != 0) {
			player.anInt1543 = buffer.getU8S();
			player.anInt1545 = buffer.getU8S();
			player.anInt1544 = buffer.getU8S();
			player.anInt1546 = buffer.getU8S();
			player.anInt1547 = buffer.getU16LEA() + anInt1161;
			player.anInt1548 = buffer.getU16A() + anInt1161;
			player.anInt1549 = buffer.getU8S();
			player.method446();
		}
		if ((i & 0x100) != 0) {
			player.anInt1520 = buffer.getU16LE();
			int k = buffer.get32();
			player.anInt1524 = k >> 16;
			player.anInt1523 = anInt1161 + (k & 0xffff);
			player.anInt1521 = 0;
			player.anInt1522 = 0;
			if (player.anInt1523 > anInt1161) {
				player.anInt1521 = -1;
			}
			if (player.anInt1520 == 65535) {
				player.anInt1520 = -1;
			}
		}
		if ((i & 8) != 0) {
			int l = buffer.getU16LE();
			if (l == 65535) {
				l = -1;
			}
			int i2 = buffer.getU8C();
			if ((l == player.anInt1526) && (l != -1)) {
				int i3 = SeqType.aTypeArray351[l].anInt365;
				if (i3 == 1) {
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if (i3 == 2) {
					player.anInt1530 = 0;
				}
			} else if ((l == -1) || (player.anInt1526 == -1) || (SeqType.aTypeArray351[l].anInt359 >= SeqType.aTypeArray351[player.anInt1526].anInt359)) {
				player.anInt1526 = l;
				player.anInt1527 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.anInt1525;
			}
		}
		if ((i & 4) != 0) {
			player.aString1506 = buffer.getString();
			if (player.aString1506.charAt(0) == '~') {
				player.aString1506 = player.aString1506.substring(1);
				method77(player.aString1506, 2, player.aString1703);
			} else if (player == aPlayer_1126) {
				method77(player.aString1506, 2, player.aString1703);
			}
			player.anInt1513 = 0;
			player.anInt1531 = 0;
			player.anInt1535 = 150;
		}
		if ((i & 0x80) != 0) {
			int i1 = buffer.getU16LE();
			int j2 = buffer.getU8();
			int j3 = buffer.getU8C();
			int k3 = buffer.position;
			if ((player.aString1703 != null) && player.aBoolean1710) {
				long l3 = StringUtil.toBase37(player.aString1703);
				boolean flag = false;
				if (j2 <= 1) {
					for (int i4 = 0; i4 < anInt822; i4++) {
						if (aLongArray925[i4] != l3) {
							continue;
						}
						flag = true;
						break;
					}
				}
				if (!flag && (anInt1251 == 0)) {
					try {
						aBuffer_834.position = 0;
						buffer.getReversed(aBuffer_834.data, 0, j3);
						aBuffer_834.position = 0;
						String s = Huffman.method525(j3, aBuffer_834);
						s = Censor.method497(s, 0);
						player.aString1506 = s;
						player.anInt1513 = i1 >> 8;
						player.anInt1531 = i1 & 0xff;
						player.anInt1535 = 150;
						if ((j2 == 2) || (j2 == 3)) {
							method77(s, 1, "@cr2@" + player.aString1703);
						} else if (j2 == 1) {
							method77(s, 1, "@cr1@" + player.aString1703);
						} else {
							method77(s, 2, player.aString1703);
						}
					} catch (Exception exception) {
						Signlink.reporterror("cde2");
					}
				}
			}
			buffer.position = k3 + j3;
		}
		if ((i & 1) != 0) {
			player.anInt1502 = buffer.getU16LE();
			if (player.anInt1502 == 65535) {
				player.anInt1502 = -1;
			}
		}
		if ((i & 0x10) != 0) {
			int j1 = buffer.getU8C();
			byte[] abyte0 = new byte[j1];
			Buffer buffer_1 = new Buffer(abyte0);
			buffer.get(abyte0, 0, j1);
			aBufferArray895[j] = buffer_1;
			player.method451(buffer_1);
		}
		if ((i & 2) != 0) {
			player.anInt1538 = buffer.getU16LEA();
			player.anInt1539 = buffer.getU16LE();
		}
		if ((i & 0x20) != 0) {
			int k1 = buffer.getU8();
			int k2 = buffer.getU8A();
			player.method447(k2, k1, anInt1161);
			player.anInt1532 = anInt1161 + 300;
			player.anInt1533 = buffer.getU8C();
			player.anInt1534 = buffer.getU8();
		}
		if ((i & 0x200) != 0) {
			int l1 = buffer.getU8();
			int l2 = buffer.getU8S();
			player.method447(l2, l1, anInt1161);
			player.anInt1532 = anInt1161 + 300;
			player.anInt1533 = buffer.getU8();
			player.anInt1534 = buffer.getU8C();
		}
	}

	public void method108() {
		int j = aPlayer_1126.anInt1550 + anInt1278;
		int k = aPlayer_1126.anInt1551 + anInt1131;
		if (((anInt1014 - j) < -500) || ((anInt1014 - j) > 500) || ((anInt1015 - k) < -500) || ((anInt1015 - k) > 500)) {
			anInt1014 = j;
			anInt1015 = k;
		}
		if (anInt1014 != j) {
			anInt1014 += (j - anInt1014) / 16;
		}
		if (anInt1015 != k) {
			anInt1015 += (k - anInt1015) / 16;
		}
		if (super.anIntArray30[1] == 1) {
			anInt1186 += (-24 - anInt1186) / 2;
		} else if (super.anIntArray30[2] == 1) {
			anInt1186 += (24 - anInt1186) / 2;
		} else {
			anInt1186 /= 2;
		}
		if (super.anIntArray30[3] == 1) {
			anInt1187 += (12 - anInt1187) / 2;
		} else if (super.anIntArray30[4] == 1) {
			anInt1187 += (-12 - anInt1187) / 2;
		} else {
			anInt1187 /= 2;
		}
		anInt1185 = (anInt1185 + (anInt1186 / 2)) & 0x7ff;
		anInt1184 += anInt1187 / 2;
		if (anInt1184 < 128) {
			anInt1184 = 128;
		}
		if (anInt1184 > 383) {
			anInt1184 = 383;
		}
		int l = anInt1014 >> 7;
		int i1 = anInt1015 >> 7;
		int j1 = method42(anInt918, anInt1015, anInt1014);
		int k1 = 0;
		if ((l > 3) && (i1 > 3) && (l < 100) && (i1 < 100)) {
			for (int l1 = l - 4; l1 <= (l + 4); l1++) {
				for (int k2 = i1 - 4; k2 <= (i1 + 4); k2++) {
					int l2 = anInt918;
					if ((l2 < 3) && ((aByteArrayArrayArray1258[1][l1][k2] & 2) == 2)) {
						l2++;
					}
					int i3 = j1 - anIntArrayArrayArray1214[l2][l1][k2];
					if (i3 > k1) {
						k1 = i3;
					}
				}
			}
		}
		int j2 = k1 * 192;
		if (j2 > 0x17f00) {
			j2 = 0x17f00;
		}
		if (j2 < 32768) {
			j2 = 32768;
		}
		if (j2 > anInt984) {
			anInt984 += (j2 - anInt984) / 24;
			return;
		}
		if (j2 < anInt984) {
			anInt984 += (j2 - anInt984) / 80;
		}
	}

	public boolean method109(String s) {
		if (s == null) {
			return false;
		}
		for (int i = 0; i < anInt899; i++) {
			if (s.equalsIgnoreCase(aStringArray1082[i])) {
				return true;
			}
		}
		return s.equalsIgnoreCase(aPlayer_1126.aString1703);
	}

	public void method111(int i) {
		Signlink.wavevol = i;
	}

	public void method112() {
		method76();
		if (anInt917 == 1) {
			aImageArray1150[anInt916 / 100].method348(anInt914 - 8 - 4, anInt915 - 8 - 4);
		}
		if (anInt917 == 2) {
			aImageArray1150[4 + (anInt916 / 100)].method348(anInt914 - 8 - 4, anInt915 - 8 - 4);
		}
		if (anInt1018 != -1) {
			method119(anInt945, anInt1018);
			method105(0, 0, Component.aComponentArray210[anInt1018], 0);
		}
		if (anInt857 != -1) {
			method119(anInt945, anInt857);
			method105(0, 0, Component.aComponentArray210[anInt857], 0);
		}
		method70();
		if (!aBoolean885) {
			method82();
			method125();
		} else if (anInt948 == 0) {
			method40();
		}
		if (anInt1055 == 1) {
			aImageArray1095[1].method348(472, 296);
		}
		if (aBoolean1156) {
			char c = '\u01FB';
			int k = 20;
			int i1 = 0xffff00;
			if (super.anInt8 < 15) {
				i1 = 0xff0000;
			}
			aFont_1271.method380("Fps:" + super.anInt8, c, i1, k);
			k += 15;
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			if ((j1 > 0x2000000) && aBoolean960) {
			}
			aFont_1271.method380("Mem:" + j1 + "k", c, 0xffff00, k);
		}
		if (anInt1104 != 0) {
			int j = anInt1104 / 50;
			int l = j / 60;
			j %= 60;
			if (j < 10) {
				aFont_1271.method385(0xffff00, "System update in: " + l + ":0" + j, 329, 4);
			} else {
				aFont_1271.method385(0xffff00, "System update in: " + l + ":" + j, 329, 4);
			}
		}
	}

	public void method113(long l) {
		if (l == 0L) {
			return;
		}
		if (anInt822 >= 100) {
			method77("Your ignore list is full. Max of 100 hit", 0, "");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(l));
		for (int j = 0; j < anInt822; j++) {
			if (aLongArray925[j] == l) {
				method77(s + " is already on your ignore list", 0, "");
				return;
			}
		}
		for (int k = 0; k < anInt899; k++) {
			if (aLongArray955[k] == l) {
				method77("Please remove " + s + " from your friend list first", 0, "");
				return;
			}
		}
		aLongArray925[anInt822++] = l;
		aBoolean1153 = true;
		aBuffer_1192.putOp(133);
		aBuffer_1192.put64(l);
	}

	public void method114() {
		for (int i = -1; i < anInt891; i++) {
			int j;
			if (i == -1) {
				j = anInt889;
			} else {
				j = anIntArray892[i];
			}
			PlayerEntity player = aPlayerArray890[j];
			if (player != null) {
				method96(player);
			}
		}
	}

	public void method115() {
		if (anInt1023 == 2) {
			for (SceneLocTemporary loc = (SceneLocTemporary) aList_1179.method252(); loc != null; loc = (SceneLocTemporary) aList_1179.method254()) {
				if (loc.anInt1294 > 0) {
					loc.anInt1294--;
				}
				if (loc.anInt1294 == 0) {
					if ((loc.anInt1299 < 0) || SceneBuilder.method178(loc.anInt1299, loc.anInt1301)) {
						method142(loc.anInt1298, loc.anInt1295, loc.anInt1300, loc.anInt1301, loc.anInt1297, loc.anInt1296, loc.anInt1299);
						loc.method329();
					}
				} else {
					if (loc.anInt1302 > 0) {
						loc.anInt1302--;
					}
					if ((loc.anInt1302 == 0) && (loc.anInt1297 >= 1) && (loc.anInt1298 >= 1) && (loc.anInt1297 <= 102) && (loc.anInt1298 <= 102) && ((loc.anInt1291 < 0) || SceneBuilder.method178(loc.anInt1291, loc.anInt1293))) {
						method142(loc.anInt1298, loc.anInt1295, loc.anInt1292, loc.anInt1293, loc.anInt1297, loc.anInt1296, loc.anInt1291);
						loc.anInt1302 = -1;
						if ((loc.anInt1291 == loc.anInt1299) && (loc.anInt1299 == -1)) {
							loc.method329();
						} else if ((loc.anInt1291 == loc.anInt1299) && (loc.anInt1292 == loc.anInt1300) && (loc.anInt1293 == loc.anInt1301)) {
							loc.method329();
						}
					}
				}
			}
		}
	}

	public void method116() {
		int i = aFont_1272.method383("Choose Option");
		for (int j = 0; j < anInt1133; j++) {
			int k = aFont_1272.method383(aStringArray1199[j]);
			if (k > i) {
				i = k;
			}
		}
		i += 8;
		int l = (15 * anInt1133) + 21;
		if ((super.anInt27 > 4) && (super.anInt28 > 4) && (super.anInt27 < 516) && (super.anInt28 < 338)) {
			int i1 = super.anInt27 - 4 - (i / 2);
			if ((i1 + i) > 512) {
				i1 = 512 - i;
			}
			if (i1 < 0) {
				i1 = 0;
			}
			int l1 = super.anInt28 - 4;
			if ((l1 + l) > 334) {
				l1 = 334 - l;
			}
			if (l1 < 0) {
				l1 = 0;
			}
			aBoolean885 = true;
			anInt948 = 0;
			anInt949 = i1;
			anInt950 = l1;
			anInt951 = i;
			anInt952 = (15 * anInt1133) + 22;
		}
		if ((super.anInt27 > 553) && (super.anInt28 > 205) && (super.anInt27 < 743) && (super.anInt28 < 466)) {
			int j1 = super.anInt27 - 553 - (i / 2);
			if (j1 < 0) {
				j1 = 0;
			} else if ((j1 + i) > 190) {
				j1 = 190 - i;
			}
			int i2 = super.anInt28 - 205;
			if (i2 < 0) {
				i2 = 0;
			} else if ((i2 + l) > 261) {
				i2 = 261 - l;
			}
			aBoolean885 = true;
			anInt948 = 1;
			anInt949 = j1;
			anInt950 = i2;
			anInt951 = i;
			anInt952 = (15 * anInt1133) + 22;
		}
		if ((super.anInt27 > 17) && (super.anInt28 > 357) && (super.anInt27 < 496) && (super.anInt28 < 453)) {
			int k1 = super.anInt27 - 17 - (i / 2);
			if (k1 < 0) {
				k1 = 0;
			} else if ((k1 + i) > 479) {
				k1 = 479 - i;
			}
			int j2 = super.anInt28 - 357;
			if (j2 < 0) {
				j2 = 0;
			} else if ((j2 + l) > 96) {
				j2 = 96 - l;
			}
			aBoolean885 = true;
			anInt948 = 2;
			anInt949 = k1;
			anInt950 = j2;
			anInt951 = i;
			anInt952 = (15 * anInt1133) + 22;
		}
	}

	public void method117(Buffer buffer) {
		buffer.accessBits();
		int j = buffer.getN(1);
		if (j == 0) {
			return;
		}
		int k = buffer.getN(2);
		if (k == 0) {
			anIntArray894[anInt893++] = anInt889;
			return;
		}
		if (k == 1) {
			int l = buffer.getN(3);
			aPlayer_1126.method448(false, l);
			int k1 = buffer.getN(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = anInt889;
			}
			return;
		}
		if (k == 2) {
			int i1 = buffer.getN(3);
			aPlayer_1126.method448(true, i1);
			int l1 = buffer.getN(3);
			aPlayer_1126.method448(true, l1);
			int j2 = buffer.getN(1);
			if (j2 == 1) {
				anIntArray894[anInt893++] = anInt889;
			}
			return;
		}
		if (k == 3) {
			anInt918 = buffer.getN(2);
			int j1 = buffer.getN(1);
			int i2 = buffer.getN(1);
			if (i2 == 1) {
				anIntArray894[anInt893++] = anInt889;
			}
			int k2 = buffer.getN(7);
			int l2 = buffer.getN(7);
			aPlayer_1126.method445(l2, k2, j1 == 1);
		}
	}

	public void method118() {
		aBoolean831 = false;
		while (aBoolean962) {
			aBoolean831 = false;
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
		aImage_966 = null;
		aImage_967 = null;
		aImageArray1152 = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
		aImage_1201 = null;
		aImage_1202 = null;
	}

	public boolean method119(int i, int j) {
		boolean flag1 = false;
		Component component = Component.aComponentArray210[j];
		for (int k = 0; k < component.anIntArray240.length; k++) {
			if (component.anIntArray240[k] == -1) {
				break;
			}
			Component component_1 = Component.aComponentArray210[component.anIntArray240[k]];
			if (component_1.anInt262 == 1) {
				flag1 |= method119(i, component_1.anInt250);
			}
			if ((component_1.anInt262 == 6) && ((component_1.anInt257 != -1) || (component_1.anInt258 != -1))) {
				boolean flag2 = method131(component_1);
				int l;
				if (flag2) {
					l = component_1.anInt258;
				} else {
					l = component_1.anInt257;
				}
				if (l != -1) {
					SeqType type = SeqType.aTypeArray351[l];
					for (component_1.anInt208 += i; component_1.anInt208 > type.method258(component_1.anInt246); ) {
						component_1.anInt208 -= type.method258(component_1.anInt246) + 1;
						component_1.anInt246++;
						if (component_1.anInt246 >= type.anInt352) {
							component_1.anInt246 -= type.anInt356;
							if ((component_1.anInt246 < 0) || (component_1.anInt246 >= type.anInt352)) {
								component_1.anInt246 = 0;
							}
						}
						flag1 = true;
					}
				}
			}
		}
		return flag1;
	}

	public int method120() {
		int j = 3;
		if (anInt861 < 310) {
			int k = anInt858 >> 7;
			int l = anInt860 >> 7;
			int i1 = aPlayer_1126.anInt1550 >> 7;
			int j1 = aPlayer_1126.anInt1551 >> 7;
			if ((aByteArrayArrayArray1258[anInt918][k][l] & 4) != 0) {
				j = anInt918;
			}
			int k1;
			if (i1 > k) {
				k1 = i1 - k;
			} else {
				k1 = k - i1;
			}
			int l1;
			if (j1 > l) {
				l1 = j1 - l;
			} else {
				l1 = l - j1;
			}
			if (k1 > l1) {
				int i2 = (l1 * 0x10000) / k1;
				int k2 = 32768;
				while (k != i1) {
					if (k < i1) {
						k++;
					} else if (k > i1) {
						k--;
					}
					if ((aByteArrayArrayArray1258[anInt918][k][l] & 4) != 0) {
						j = anInt918;
					}
					k2 += i2;
					if (k2 >= 0x10000) {
						k2 -= 0x10000;
						if (l < j1) {
							l++;
						} else if (l > j1) {
							l--;
						}
						if ((aByteArrayArrayArray1258[anInt918][k][l] & 4) != 0) {
							j = anInt918;
						}
					}
				}
			} else {
				int j2 = (k1 * 0x10000) / l1;
				int l2 = 32768;
				while (l != j1) {
					if (l < j1) {
						l++;
					} else if (l > j1) {
						l--;
					}
					if ((aByteArrayArrayArray1258[anInt918][k][l] & 4) != 0) {
						j = anInt918;
					}
					l2 += j2;
					if (l2 >= 0x10000) {
						l2 -= 0x10000;
						if (k < i1) {
							k++;
						} else if (k > i1) {
							k--;
						}
						if ((aByteArrayArrayArray1258[anInt918][k][l] & 4) != 0) {
							j = anInt918;
						}
					}
				}
			}
		}
		if ((aByteArrayArrayArray1258[anInt918][aPlayer_1126.anInt1550 >> 7][aPlayer_1126.anInt1551 >> 7] & 4) != 0) {
			j = anInt918;
		}
		return j;
	}

	public int method121() {
		int j = method42(anInt918, anInt860, anInt858);
		if (((j - anInt859) < 800) && ((aByteArrayArrayArray1258[anInt918][anInt858 >> 7][anInt860 >> 7] & 4) != 0)) {
			return anInt918;
		} else {
			return 3;
		}
	}

	public void method122(long l) {
		if (l == 0L) {
			return;
		}
		for (int j = 0; j < anInt822; j++) {
			if (aLongArray925[j] == l) {
				anInt822--;
				aBoolean1153 = true;
				for (int k = j; k < anInt822; k++) {
					aLongArray925[k] = aLongArray925[k + 1];
				}
				aBuffer_1192.putOp(74);
				aBuffer_1192.put64(l);
				return;
			}
		}
	}

	public void method123(boolean flag, int i) {
		Signlink.midivol = i;
		if (flag) {
			Signlink.midi = "voladjust";
		}
	}

	public int method124(Component component, int j) {
		if ((component.anIntArrayArray226 == null) || (j >= component.anIntArrayArray226.length)) {
			return -2;
		}
		try {
			int[] ai = component.anIntArrayArray226[j];
			int k = 0;
			int l = 0;
			int i1 = 0;
			do {
				int j1 = ai[l++];
				int k1 = 0;
				byte byte0 = 0;
				if (j1 == 0) {
					return k;
				}
				if (j1 == 1) {
					k1 = anIntArray922[ai[l++]];
				}
				if (j1 == 2) {
					k1 = anIntArray1044[ai[l++]];
				}
				if (j1 == 3) {
					k1 = anIntArray864[ai[l++]];
				}
				if (j1 == 4) {
					Component component_1 = Component.aComponentArray210[ai[l++]];
					int k2 = ai[l++];
					if ((k2 >= 0) && (k2 < ObjType.anInt203) && (!ObjType.method198(k2).aBoolean161 || aBoolean959)) {
						for (int j3 = 0; j3 < component_1.anIntArray253.length; j3++) {
							if (component_1.anIntArray253[j3] == (k2 + 1)) {
								k1 += component_1.anIntArray252[j3];
							}
						}
					}
				}
				if (j1 == 5) {
					k1 = anIntArray971[ai[l++]];
				}
				if (j1 == 6) {
					k1 = anIntArray1019[anIntArray1044[ai[l++]] - 1];
				}
				if (j1 == 7) {
					k1 = (anIntArray971[ai[l++]] * 100) / 46875;
				}
				if (j1 == 8) {
					k1 = aPlayer_1126.anInt1705;
				}
				if (j1 == 9) {
					for (int l1 = 0; l1 < SkillConstants.anInt733; l1++) {
						if (SkillConstants.aBooleanArray735[l1]) {
							k1 += anIntArray1044[l1];
						}
					}
				}
				if (j1 == 10) {
					Component component_2 = Component.aComponentArray210[ai[l++]];
					int l2 = ai[l++] + 1;
					if ((l2 >= 0) && (l2 < ObjType.anInt203) && (!ObjType.method198(l2).aBoolean161 || aBoolean959)) {
						for (int k3 = 0; k3 < component_2.anIntArray253.length; k3++) {
							if (component_2.anIntArray253[k3] != l2) {
								continue;
							}
							k1 = 0x3b9ac9ff;
							break;
						}
					}
				}
				if (j1 == 11) {
					k1 = anInt1148;
				}
				if (j1 == 12) {
					k1 = anInt878;
				}
				if (j1 == 13) {
					int i2 = anIntArray971[ai[l++]];
					int i3 = ai[l++];
					k1 = ((i2 & (1 << i3)) == 0) ? 0 : 1;
				}
				if (j1 == 14) {
					int j2 = ai[l++];
					VarbitType varbit = VarbitType.aVarbitArray646[j2];
					int l3 = varbit.anInt648;
					int i4 = varbit.anInt649;
					int j4 = varbit.anInt650;
					int k4 = anIntArray1232[j4 - i4];
					k1 = (anIntArray971[l3] >> i4) & k4;
				}
				if (j1 == 15) {
					byte0 = 1;
				}
				if (j1 == 16) {
					byte0 = 2;
				}
				if (j1 == 17) {
					byte0 = 3;
				}
				if (j1 == 18) {
					k1 = (aPlayer_1126.anInt1550 >> 7) + anInt1034;
				}
				if (j1 == 19) {
					k1 = (aPlayer_1126.anInt1551 >> 7) + anInt1035;
				}
				if (j1 == 20) {
					k1 = ai[l++];
				}
				if (byte0 == 0) {
					if (i1 == 0) {
						k += k1;
					}
					if (i1 == 1) {
						k -= k1;
					}
					if ((i1 == 2) && (k1 != 0)) {
						k /= k1;
					}
					if (i1 == 3) {
						k *= k1;
					}
					i1 = 0;
				} else {
					i1 = byte0;
				}
			} while (true);
		} catch (Exception _ex) {
			return -1;
		}
	}

	public void method125() {
		if ((anInt1133 < 2) && (anInt1282 == 0) && (anInt1136 == 0)) {
			return;
		}
		String s;
		if ((anInt1282 == 1) && (anInt1133 < 2)) {
			s = "Use " + aString1286 + " with...";
		} else if ((anInt1136 == 1) && (anInt1133 < 2)) {
			s = aString1139 + "...";
		} else {
			s = aStringArray1199[anInt1133 - 1];
		}
		if (anInt1133 > 2) {
			s = s + "@whi@ / " + (anInt1133 - 2) + " more options";
		}
		aFont_1272.method390(true, 4, 0xffffff, s, anInt1161 / 1000, 15);
	}

	public void method126() {
		aArea_1164.method237();
		if (anInt1021 == 2) {
			byte[] abyte0 = aImage_1197.aByteArray1450;
			int[] ai = Draw2D.pixels;
			int k2 = abyte0.length;
			for (int i5 = 0; i5 < k2; i5++) {
				if (abyte0[i5] == 0) {
					ai[i5] = 0;
				}
			}
			aImage_1122.method352(33, anInt1185, anIntArray1057, 256, anIntArray968, 25, 0, 0, 33, 25);
			aArea_1165.method237();
			return;
		}
		int i = (anInt1185 + anInt1209) & 0x7ff;
		int j = 48 + (aPlayer_1126.anInt1550 / 32);
		int l2 = 464 - (aPlayer_1126.anInt1551 / 32);
		aImage_1263.method352(151, i, anIntArray1229, 256 + anInt1170, anIntArray1052, l2, 5, 25, 146, j);
		aImage_1122.method352(33, anInt1185, anIntArray1057, 256, anIntArray968, 25, 0, 0, 33, 25);
		for (int j5 = 0; j5 < anInt1071; j5++) {
			int k = ((anIntArray1072[j5] * 4) + 2) - (aPlayer_1126.anInt1550 / 32);
			int i3 = ((anIntArray1073[j5] * 4) + 2) - (aPlayer_1126.anInt1551 / 32);
			method141(aImageArray1140[j5], k, i3);
		}
		for (int k5 = 0; k5 < 104; k5++) {
			for (int l5 = 0; l5 < 104; l5++) {
				LinkedList list = aListArrayArrayArray827[anInt918][k5][l5];
				if (list != null) {
					int l = ((k5 * 4) + 2) - (aPlayer_1126.anInt1550 / 32);
					int j3 = ((l5 * 4) + 2) - (aPlayer_1126.anInt1551 / 32);
					method141(aImage_1074, l, j3);
				}
			}
		}
		for (int i6 = 0; i6 < anInt836; i6++) {
			NPCEntity npc = aNpcArray835[anIntArray837[i6]];
			if ((npc != null) && npc.method449()) {
				NPCType type = npc.aType_1696;
				if (type.anIntArray88 != null) {
					type = type.method161();
				}
				if ((type != null) && type.aBoolean87 && type.aBoolean84) {
					int i1 = (npc.anInt1550 / 32) - (aPlayer_1126.anInt1550 / 32);
					int k3 = (npc.anInt1551 / 32) - (aPlayer_1126.anInt1551 / 32);
					method141(aImage_1075, i1, k3);
				}
			}
		}
		for (int j6 = 0; j6 < anInt891; j6++) {
			PlayerEntity player = aPlayerArray890[anIntArray892[j6]];
			if ((player != null) && player.method449()) {
				int j1 = (player.anInt1550 / 32) - (aPlayer_1126.anInt1550 / 32);
				int l3 = (player.anInt1551 / 32) - (aPlayer_1126.anInt1551 / 32);
				boolean flag1 = false;
				long l6 = StringUtil.toBase37(player.aString1703);
				for (int k6 = 0; k6 < anInt899; k6++) {
					if ((l6 != aLongArray955[k6]) || (anIntArray826[k6] == 0)) {
						continue;
					}
					flag1 = true;
					break;
				}
				boolean flag2 = false;
				if ((player.anInt1701 != 0) && (aPlayer_1126.anInt1701 == player.anInt1701)) {
					flag2 = true;
				}
				if (flag1) {
					method141(aImage_1077, j1, l3);
				} else if (flag2) {
					method141(aImage_1078, j1, l3);
				} else {
					method141(aImage_1076, j1, l3);
				}
			}
		}
		if ((anInt855 != 0) && ((anInt1161 % 20) < 10)) {
			if ((anInt855 == 1) && (anInt1222 >= 0) && (anInt1222 < aNpcArray835.length)) {
				NPCEntity class30_sub2_sub4_sub1_sub1_1 = aNpcArray835[anInt1222];
				if (class30_sub2_sub4_sub1_sub1_1 != null) {
					int k1 = (class30_sub2_sub4_sub1_sub1_1.anInt1550 / 32) - (aPlayer_1126.anInt1550 / 32);
					int i4 = (class30_sub2_sub4_sub1_sub1_1.anInt1551 / 32) - (aPlayer_1126.anInt1551 / 32);
					method81(aImage_871, i4, k1);
				}
			}
			if (anInt855 == 2) {
				int l1 = (((anInt934 - anInt1034) * 4) + 2) - (aPlayer_1126.anInt1550 / 32);
				int j4 = (((anInt935 - anInt1035) * 4) + 2) - (aPlayer_1126.anInt1551 / 32);
				method81(aImage_871, j4, l1);
			}
			if ((anInt855 == 10) && (anInt933 >= 0) && (anInt933 < aPlayerArray890.length)) {
				PlayerEntity class30_sub2_sub4_sub1_sub2_1 = aPlayerArray890[anInt933];
				if (class30_sub2_sub4_sub1_sub2_1 != null) {
					int i2 = (class30_sub2_sub4_sub1_sub2_1.anInt1550 / 32) - (aPlayer_1126.anInt1550 / 32);
					int k4 = (class30_sub2_sub4_sub1_sub2_1.anInt1551 / 32) - (aPlayer_1126.anInt1551 / 32);
					method81(aImage_871, k4, i2);
				}
			}
		}
		if (anInt1261 != 0) {
			int j2 = ((anInt1261 * 4) + 2) - (aPlayer_1126.anInt1550 / 32);
			int l4 = ((anInt1262 * 4) + 2) - (aPlayer_1126.anInt1551 / 32);
			method141(aImage_870, j2, l4);
		}
		Draw2D.fillRect(97, 78, 3, 3, 0xffffff);
		aArea_1165.method237();
	}

	public void method127(PathingEntity entity, int i) {
		method128(entity.anInt1550, i, entity.anInt1551);
	}

	public void method128(int i, int j, int l) {
		if ((i < 128) || (l < 128) || (i > 13056) || (l > 13056)) {
			anInt963 = -1;
			anInt964 = -1;
			return;
		}
		int i1 = method42(anInt918, l, i) - j;
		i -= anInt858;
		i1 -= anInt859;
		l -= anInt860;
		int j1 = Model.sin[anInt861];
		int k1 = Model.cos[anInt861];
		int l1 = Model.sin[anInt862];
		int i2 = Model.cos[anInt862];
		int j2 = ((l * l1) + (i * i2)) >> 16;
		l = ((l * i2) - (i * l1)) >> 16;
		i = j2;
		j2 = ((i1 * k1) - (l * j1)) >> 16;
		l = ((i1 * j1) + (l * k1)) >> 16;
		i1 = j2;
		if (l >= 50) {
			anInt963 = Draw3D.centerX + ((i << 9) / l);
			anInt964 = Draw3D.centerY + ((i1 << 9) / l);
		} else {
			anInt963 = -1;
			anInt964 = -1;
		}
	}

	public void method129() {
		if (anInt1195 == 0) {
			return;
		}
		int i = 0;
		if (anInt1104 != 0) {
			i = 1;
		}
		for (int j = 0; j < 100; j++) {
			if (aStringArray944[j] != null) {
				int k = anIntArray942[j];
				String s = aStringArray943[j];
				if ((s != null) && s.startsWith("@cr1@")) {
					s = s.substring(5);
				}
				if ((s != null) && s.startsWith("@cr2@")) {
					s = s.substring(5);
				}
				if (((k == 3) || (k == 7)) && ((k == 7) || (anInt845 == 0) || ((anInt845 == 1) && method109(s)))) {
					int l = 329 - (i * 13);
					if ((super.anInt20 > 4) && ((super.anInt21 - 4) > (l - 10)) && ((super.anInt21 - 4) <= (l + 3))) {
						int i1 = aFont_1271.method383("From:  " + s + aStringArray944[j]) + 25;
						if (i1 > 450) {
							i1 = 450;
						}
						if (super.anInt20 < (4 + i1)) {
							if (anInt863 >= 1) {
								aStringArray1199[anInt1133] = "Report abuse @whi@" + s;
								anIntArray1093[anInt1133] = 2606;
								anInt1133++;
							}
							aStringArray1199[anInt1133] = "Add ignore @whi@" + s;
							anIntArray1093[anInt1133] = 2042;
							anInt1133++;
							aStringArray1199[anInt1133] = "Add friend @whi@" + s;
							anIntArray1093[anInt1133] = 2337;
							anInt1133++;
						}
					}
					if (++i >= 5) {
						return;
					}
				}
				if (((k == 5) || (k == 6)) && (anInt845 < 2) && (++i >= 5)) {
					return;
				}
			}
		}
	}

	public void method130(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
		SceneLocTemporary loc = null;
		for (SceneLocTemporary loc_1 = (SceneLocTemporary) aList_1179.method252(); loc_1 != null; loc_1 = (SceneLocTemporary) aList_1179.method254()) {
			if ((loc_1.anInt1295 != l1) || (loc_1.anInt1297 != i2) || (loc_1.anInt1298 != j1) || (loc_1.anInt1296 != i1)) {
				continue;
			}
			loc = loc_1;
			break;
		}
		if (loc == null) {
			loc = new SceneLocTemporary();
			loc.anInt1295 = l1;
			loc.anInt1296 = i1;
			loc.anInt1297 = i2;
			loc.anInt1298 = j1;
			method89(loc);
			aList_1179.method249(loc);
		}
		loc.anInt1291 = k;
		loc.anInt1293 = k1;
		loc.anInt1292 = l;
		loc.anInt1302 = j2;
		loc.anInt1294 = j;
	}

	public boolean method131(Component component) {
		if (component.anIntArray245 == null) {
			return false;
		}
		for (int i = 0; i < component.anIntArray245.length; i++) {
			int j = method124(component, i);
			int k = component.anIntArray212[i];
			if (component.anIntArray245[i] == 2) {
				if (j >= k) {
					return false;
				}
			} else if (component.anIntArray245[i] == 3) {
				if (j <= k) {
					return false;
				}
			} else if (component.anIntArray245[i] == 4) {
				if (j == k) {
					return false;
				}
			} else if (j != k) {
				return false;
			}
		}
		return true;
	}

	public DataInputStream method132(String s) throws IOException {
		if (!aBoolean872) {
			if (Signlink.mainapp != null) {
				return Signlink.openurl(s);
			} else {
				return new DataInputStream((new URL(getCodeBase(), s)).openStream());
			}
		}
		if (aSocket832 != null) {
			try {
				aSocket832.close();
			} catch (Exception ignored) {
			}
			aSocket832 = null;
		}
		aSocket832 = method19(43595);
		aSocket832.setSoTimeout(10000);
		java.io.InputStream inputstream = aSocket832.getInputStream();
		OutputStream outputstream = aSocket832.getOutputStream();
		outputstream.write(("JAGGRAB /" + s + "\n\n").getBytes());
		return new DataInputStream(inputstream);
	}

	public void method133() {
		char c = '\u0100';
		if (anInt1040 > 0) {
			for (int i = 0; i < 256; i++) {
				if (anInt1040 > 768) {
					anIntArray850[i] = method83(anIntArray851[i], anIntArray852[i], 1024 - anInt1040);
				} else if (anInt1040 > 256) {
					anIntArray850[i] = anIntArray852[i];
				} else {
					anIntArray850[i] = method83(anIntArray852[i], anIntArray851[i], 256 - anInt1040);
				}
			}
		} else if (anInt1041 > 0) {
			for (int j = 0; j < 256; j++) {
				if (anInt1041 > 768) {
					anIntArray850[j] = method83(anIntArray851[j], anIntArray853[j], 1024 - anInt1041);
				} else if (anInt1041 > 256) {
					anIntArray850[j] = anIntArray853[j];
				} else {
					anIntArray850[j] = method83(anIntArray853[j], anIntArray851[j], 256 - anInt1041);
				}
			}
		} else {
			for (int k = 0; k < 256; k++) {
				anIntArray850[k] = anIntArray851[k];
			}
		}
		for (int l = 0; l < 33920; l++) {
			aArea_1110.anIntArray315[l] = aImage_1201.anIntArray1439[l];
		}
		int i1 = 0;
		int j1 = 1152;
		for (int k1 = 1; k1 < (c - 1); k1++) {
			int l1 = (anIntArray969[k1] * (c - k1)) / c;
			int j2 = 22 + l1;
			if (j2 < 0) {
				j2 = 0;
			}
			i1 += j2;
			for (int l2 = j2; l2 < 128; l2++) {
				int j3 = anIntArray828[i1++];
				if (j3 != 0) {
					int l3 = j3;
					int j4 = 256 - j3;
					j3 = anIntArray850[j3];
					int l4 = aArea_1110.anIntArray315[j1];
					aArea_1110.anIntArray315[j1++] = (((((j3 & 0xff00ff) * l3) + ((l4 & 0xff00ff) * j4)) & 0xff00ff00) + ((((j3 & 0xff00) * l3) + ((l4 & 0xff00) * j4)) & 0xff0000)) >> 8;
				} else {
					j1++;
				}
			}
			j1 += j2;
		}
		aArea_1110.method238(0, super.aGraphics12, 0);
		for (int i2 = 0; i2 < 33920; i2++) {
			aArea_1111.anIntArray315[i2] = aImage_1202.anIntArray1439[i2];
		}
		i1 = 0;
		j1 = 1176;
		for (int k2 = 1; k2 < (c - 1); k2++) {
			int i3 = (anIntArray969[k2] * (c - k2)) / c;
			int k3 = 103 - i3;
			j1 += i3;
			for (int i4 = 0; i4 < k3; i4++) {
				int k4 = anIntArray828[i1++];
				if (k4 != 0) {
					int i5 = k4;
					int j5 = 256 - k4;
					k4 = anIntArray850[k4];
					int k5 = aArea_1111.anIntArray315[j1];
					aArea_1111.anIntArray315[j1++] = (((((k4 & 0xff00ff) * i5) + ((k5 & 0xff00ff) * j5)) & 0xff00ff00) + ((((k4 & 0xff00) * i5) + ((k5 & 0xff00) * j5)) & 0xff0000)) >> 8;
				} else {
					j1++;
				}
			}
			i1 += 128 - k3;
			j1 += 128 - k3 - i3;
		}
		aArea_1111.method238(0, super.aGraphics12, 637);
	}

	public void method134(Buffer buffer) {
		int j = buffer.getN(8);
		if (j < anInt891) {
			for (int k = j; k < anInt891; k++) {
				anIntArray840[anInt839++] = anIntArray892[k];
			}
		}
		if (j > anInt891) {
			Signlink.reporterror(aString1173 + " Too many players");
			throw new RuntimeException("eek");
		}
		anInt891 = 0;
		for (int l = 0; l < j; l++) {
			int i1 = anIntArray892[l];
			PlayerEntity player = aPlayerArray890[i1];
			int j1 = buffer.getN(1);
			if (j1 == 0) {
				anIntArray892[anInt891++] = i1;
				player.anInt1537 = anInt1161;
			} else {
				int k1 = buffer.getN(2);
				if (k1 == 0) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = anInt1161;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = anInt1161;
					int l1 = buffer.getN(3);
					player.method448(false, l1);
					int j2 = buffer.getN(1);
					if (j2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 2) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = anInt1161;
					int i2 = buffer.getN(3);
					player.method448(true, i2);
					int k2 = buffer.getN(3);
					player.method448(true, k2);
					int l2 = buffer.getN(1);
					if (l2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 3) {
					anIntArray840[anInt839++] = i1;
				}
			}
		}
	}

	public void method135(boolean flag) {
		method64();
		aArea_1109.method237();
		aImage_966.method361(0, 0);
		char c = '\u0168';
		char c1 = '\310';
		if (anInt833 == 0) {
			int i = (c1 / 2) + 80;
			aFont_1270.method382(0x75a9a9, c / 2, aOnDemand_1068.aString1333, i, true);
			i = (c1 / 2) - 20;
			aFont_1272.method382(0xffff00, c / 2, "Welcome to RuneScape", i, true);
			int l = (c / 2) - 80;
			int k1 = (c1 / 2) + 20;
			aImage_967.method361(l - 73, k1 - 20);
			aFont_1272.method382(0xffffff, l, "New User", k1 + 5, true);
			l = (c / 2) + 80;
			aImage_967.method361(l - 73, k1 - 20);
			aFont_1272.method382(0xffffff, l, "Existing User", k1 + 5, true);
		}
		if (anInt833 == 2) {
			int j = (c1 / 2) - 40;
			if (aString1266.length() > 0) {
				aFont_1272.method382(0xffff00, c / 2, aString1266, j - 15, true);
				aFont_1272.method382(0xffff00, c / 2, aString1267, j, true);
			} else {
				aFont_1272.method382(0xffff00, c / 2, aString1267, j - 7, true);
			}
			j += 30;
			aFont_1272.method389(true, (c / 2) - 90, 0xffffff, "Username: " + aString1173 + ((anInt1216 == 0) & (anInt1161 % 40 < 20) ? "@yel@|" : ""), j);
			j += 15;
			aFont_1272.method389(true, (c / 2) - 88, 0xffffff, "Password: " + StringUtil.toAsterisks(aString1174) + ((anInt1216 == 1) & (anInt1161 % 40 < 20) ? "@yel@|" : ""), j);
			if (!flag) {
				int i1 = (c / 2) - 80;
				int l1 = (c1 / 2) + 50;
				aImage_967.method361(i1 - 73, l1 - 20);
				aFont_1272.method382(0xffffff, i1, "Login", l1 + 5, true);
				i1 = (c / 2) + 80;
				aImage_967.method361(i1 - 73, l1 - 20);
				aFont_1272.method382(0xffffff, i1, "Cancel", l1 + 5, true);
			}
		}
		if (anInt833 == 3) {
			aFont_1272.method382(0xffff00, c / 2, "Create a free account", (c1 / 2) - 60, true);
			int k = (c1 / 2) - 35;
			aFont_1272.method382(0xffffff, c / 2, "To create a new account you need to", k, true);
			k += 15;
			aFont_1272.method382(0xffffff, c / 2, "go back to the main RuneScape webpage", k, true);
			k += 15;
			aFont_1272.method382(0xffffff, c / 2, "and choose the red 'create account'", k, true);
			k += 15;
			aFont_1272.method382(0xffffff, c / 2, "button at the top right of that page.", k, true);
			int j1 = c / 2;
			int i2 = (c1 / 2) + 50;
			aImage_967.method361(j1 - 73, i2 - 20);
			aFont_1272.method382(0xffffff, j1, "Cancel", i2 + 5, true);
		}
		aArea_1109.method238(171, super.aGraphics12, 202);
		if (aBoolean1255) {
			aBoolean1255 = false;
			aArea_1107.method238(0, super.aGraphics12, 128);
			aArea_1108.method238(371, super.aGraphics12, 202);
			aArea_1112.method238(265, super.aGraphics12, 0);
			aArea_1113.method238(265, super.aGraphics12, 562);
			aArea_1114.method238(171, super.aGraphics12, 128);
			aArea_1115.method238(171, super.aGraphics12, 562);
		}
	}

	public void method136() {
		aBoolean962 = true;
		try {
			long l = System.currentTimeMillis();
			int i = 0;
			int j = 20;
			while (aBoolean831) {
				anInt1208++;
				method58();
				method58();
				method133();
				if (++i > 10) {
					long l1 = System.currentTimeMillis();
					int k = ((int) (l1 - l) / 10) - j;
					j = 40 - k;
					if (j < 5) {
						j = 5;
					}
					i = 0;
					l = l1;
				}
				try {
					Thread.sleep(j);
				} catch (Exception ignored) {
				}
			}
		} catch (Exception ignored) {
		}
		aBoolean962 = false;
	}

	public void method137(Buffer buffer, int j) {
		if (j == 84) {
			int k = buffer.getU8();
			int j3 = anInt1268 + ((k >> 4) & 7);
			int i6 = anInt1269 + (k & 7);
			int l8 = buffer.getU16();
			int k11 = buffer.getU16();
			int l13 = buffer.getU16();
			if ((j3 >= 0) && (i6 >= 0) && (j3 < 104) && (i6 < 104)) {
				LinkedList list_1 = aListArrayArrayArray827[anInt918][j3][i6];
				if (list_1 != null) {
					for (ObjStackEntity objStack_3 = (ObjStackEntity) list_1.method252(); objStack_3 != null; objStack_3 = (ObjStackEntity) list_1.method254()) {
						if ((objStack_3.anInt1558 != (l8 & 0x7fff)) || (objStack_3.anInt1559 != k11)) {
							continue;
						}
						objStack_3.anInt1559 = l13;
						break;
					}
					method25(j3, i6);
				}
			}
			return;
		}
		if (j == 105) {
			int l = buffer.getU8();
			int k3 = anInt1268 + ((l >> 4) & 7);
			int j6 = anInt1269 + (l & 7);
			int i9 = buffer.getU16();
			int l11 = buffer.getU8();
			int i14 = (l11 >> 4) & 0xf;
			int i16 = l11 & 7;
			if ((aPlayer_1126.anIntArray1500[0] >= (k3 - i14)) && (aPlayer_1126.anIntArray1500[0] <= (k3 + i14)) && (aPlayer_1126.anIntArray1501[0] >= (j6 - i14)) && (aPlayer_1126.anIntArray1501[0] <= (j6 + i14)) && aBoolean848 && !aBoolean960 && (anInt1062 < 50)) {
				anIntArray1207[anInt1062] = i9;
				anIntArray1241[anInt1062] = i16;
				anIntArray1250[anInt1062] = SoundTrack.anIntArray326[i9];
				anInt1062++;
			}
		}
		if (j == 215) {
			int i1 = buffer.getU16A();
			int l3 = buffer.getU8S();
			int k6 = anInt1268 + ((l3 >> 4) & 7);
			int j9 = anInt1269 + (l3 & 7);
			int i12 = buffer.getU16A();
			int j14 = buffer.getU16();
			if ((k6 >= 0) && (j9 >= 0) && (k6 < 104) && (j9 < 104) && (i12 != anInt884)) {
				ObjStackEntity class30_sub2_sub4_sub2_2 = new ObjStackEntity();
				class30_sub2_sub4_sub2_2.anInt1558 = i1;
				class30_sub2_sub4_sub2_2.anInt1559 = j14;
				if (aListArrayArrayArray827[anInt918][k6][j9] == null) {
					aListArrayArrayArray827[anInt918][k6][j9] = new LinkedList();
				}
				aListArrayArrayArray827[anInt918][k6][j9].method249(class30_sub2_sub4_sub2_2);
				method25(k6, j9);
			}
			return;
		}
		if (j == 156) {
			int j1 = buffer.getU8A();
			int i4 = anInt1268 + ((j1 >> 4) & 7);
			int l6 = anInt1269 + (j1 & 7);
			int k9 = buffer.getU16();
			if ((i4 >= 0) && (l6 >= 0) && (i4 < 104) && (l6 < 104)) {
				LinkedList list = aListArrayArrayArray827[anInt918][i4][l6];
				if (list != null) {
					for (ObjStackEntity objStack = (ObjStackEntity) list.method252(); objStack != null; objStack = (ObjStackEntity) list.method254()) {
						if (objStack.anInt1558 != (k9 & 0x7fff)) {
							continue;
						}
						objStack.method329();
						break;
					}
					if (list.method252() == null) {
						aListArrayArrayArray827[anInt918][i4][l6] = null;
					}
					method25(i4, l6);
				}
			}
			return;
		}
		if (j == 160) {
			int k1 = buffer.getU8S();
			int j4 = anInt1268 + ((k1 >> 4) & 7);
			int i7 = anInt1269 + (k1 & 7);
			int l9 = buffer.getU8S();
			int j12 = l9 >> 2;
			int k14 = l9 & 3;
			int j16 = anIntArray1177[j12];
			int j17 = buffer.getU16A();
			if ((j4 >= 0) && (i7 >= 0) && (j4 < 103) && (i7 < 103)) {
				int j18 = anIntArrayArrayArray1214[anInt918][j4][i7];
				int i19 = anIntArrayArrayArray1214[anInt918][j4 + 1][i7];
				int l19 = anIntArrayArrayArray1214[anInt918][j4 + 1][i7 + 1];
				int k20 = anIntArrayArrayArray1214[anInt918][j4][i7 + 1];
				if (j16 == 0) {
					SceneWall wall = aGraph_946.method296(anInt918, j4, i7);
					if (wall != null) {
						int k21 = (wall.anInt280 >> 14) & 0x7fff;
						if (j12 == 2) {
							wall.aEntity_278 = new LocEntity(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
							wall.aEntity_279 = new LocEntity(k21, (k14 + 1) & 3, 2, i19, l19, j18, k20, j17, false);
						} else {
							wall.aEntity_278 = new LocEntity(k21, k14, j12, i19, l19, j18, k20, j17, false);
						}
					}
				}
				if (j16 == 1) {
					SceneWallDecoration wallDecoration = aGraph_946.method297(j4, i7, anInt918);
					if (wallDecoration != null) {
						wallDecoration.aEntity_504 = new LocEntity((wallDecoration.anInt505 >> 14) & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
					}
				}
				if (j16 == 2) {
					SceneLoc loc = aGraph_946.method298(j4, i7, anInt918);
					if (j12 == 11) {
						j12 = 10;
					}
					if (loc != null) {
						loc.aEntity_521 = new LocEntity((loc.anInt529 >> 14) & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
					}
				}
				if (j16 == 3) {
					SceneGroundDecoration groundDecoration = aGraph_946.method299(i7, j4, anInt918);
					if (groundDecoration != null) {
						groundDecoration.aEntity_814 = new LocEntity((groundDecoration.anInt815 >> 14) & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
					}
				}
			}
			return;
		}
		if (j == 147) {
			int l1 = buffer.getU8S();
			int k4 = anInt1268 + ((l1 >> 4) & 7);
			int j7 = anInt1269 + (l1 & 7);
			int i10 = buffer.getU16();
			byte byte0 = buffer.get8S();
			int l14 = buffer.getU16LE();
			byte byte1 = buffer.get8C();
			int k17 = buffer.getU16();
			int k18 = buffer.getU8S();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = anIntArray1177[j19];
			byte byte2 = buffer.get8();
			int l21 = buffer.getU16();
			byte byte3 = buffer.get8C();
			PlayerEntity player;
			if (i10 == anInt884) {
				player = aPlayer_1126;
			} else {
				player = aPlayerArray890[i10];
			}
			if (player != null) {
				LocType type = LocType.method572(l21);
				int i22 = anIntArrayArrayArray1214[anInt918][k4][j7];
				int j22 = anIntArrayArrayArray1214[anInt918][k4 + 1][j7];
				int k22 = anIntArrayArrayArray1214[anInt918][k4 + 1][j7 + 1];
				int l22 = anIntArrayArrayArray1214[anInt918][k4][j7 + 1];
				Model model = type.method578(j19, i20, i22, j22, k22, l22, -1);
				if (model != null) {
					method130(k17 + 1, -1, 0, l20, j7, 0, anInt918, k4, l14 + 1);
					player.anInt1707 = l14 + anInt1161;
					player.anInt1708 = k17 + anInt1161;
					player.aModel_1714 = model;
					int i23 = type.anInt744;
					int j23 = type.anInt761;
					if ((i20 == 1) || (i20 == 3)) {
						i23 = type.anInt761;
						j23 = type.anInt744;
					}
					player.anInt1711 = (k4 * 128) + (i23 * 64);
					player.anInt1713 = (j7 * 128) + (j23 * 64);
					player.anInt1712 = method42(anInt918, player.anInt1713, player.anInt1711);
					if (byte2 > byte0) {
						byte byte4 = byte2;
						byte2 = byte0;
						byte0 = byte4;
					}
					if (byte3 > byte1) {
						byte byte5 = byte3;
						byte3 = byte1;
						byte1 = byte5;
					}
					player.anInt1719 = k4 + byte2;
					player.anInt1721 = k4 + byte0;
					player.anInt1720 = j7 + byte3;
					player.anInt1722 = j7 + byte1;
				}
			}
		}
		if (j == 151) {
			int i2 = buffer.getU8A();
			int l4 = anInt1268 + ((i2 >> 4) & 7);
			int k7 = anInt1269 + (i2 & 7);
			int j10 = buffer.getU16LE();
			int k12 = buffer.getU8S();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = anIntArray1177[i15];
			if ((l4 >= 0) && (k7 >= 0) && (l4 < 104) && (k7 < 104)) {
				method130(-1, j10, k16, l17, k7, i15, anInt918, l4, 0);
			}
			return;
		}
		if (j == 4) {
			int j2 = buffer.getU8();
			int i5 = anInt1268 + ((j2 >> 4) & 7);
			int l7 = anInt1269 + (j2 & 7);
			int k10 = buffer.getU16();
			int l12 = buffer.getU8();
			int j15 = buffer.getU16();
			if ((i5 >= 0) && (l7 >= 0) && (i5 < 104) && (l7 < 104)) {
				i5 = (i5 * 128) + 64;
				l7 = (l7 * 128) + 64;
				SpotAnimEntity spotAnim = new SpotAnimEntity(anInt918, anInt1161, j15, k10, method42(anInt918, l7, i5) - l12, l7, i5);
				aList_1056.method249(spotAnim);
			}
			return;
		}
		if (j == 44) {
			int k2 = buffer.getU16LEA();
			int j5 = buffer.getU16();
			int i8 = buffer.getU8();
			int l10 = anInt1268 + ((i8 >> 4) & 7);
			int i13 = anInt1269 + (i8 & 7);
			if ((l10 >= 0) && (i13 >= 0) && (l10 < 104) && (i13 < 104)) {
				ObjStackEntity objStack_1 = new ObjStackEntity();
				objStack_1.anInt1558 = k2;
				objStack_1.anInt1559 = j5;
				if (aListArrayArrayArray827[anInt918][l10][i13] == null) {
					aListArrayArrayArray827[anInt918][l10][i13] = new LinkedList();
				}
				aListArrayArrayArray827[anInt918][l10][i13].method249(objStack_1);
				method25(l10, i13);
			}
			return;
		}
		if (j == 101) {
			int l2 = buffer.getU8C();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = anIntArray1177[k5];
			int j13 = buffer.getU8();
			int k15 = anInt1268 + ((j13 >> 4) & 7);
			int l16 = anInt1269 + (j13 & 7);
			if ((k15 >= 0) && (l16 >= 0) && (k15 < 104) && (l16 < 104)) {
				method130(-1, -1, j8, i11, l16, k5, anInt918, k15, 0);
			}
			return;
		}
		if (j == 117) {
			int i3 = buffer.getU8();
			int l5 = anInt1268 + ((i3 >> 4) & 7);
			int k8 = anInt1269 + (i3 & 7);
			int j11 = l5 + buffer.get8();
			int k13 = k8 + buffer.get8();
			int l15 = buffer.get16();
			int i17 = buffer.getU16();
			int i18 = buffer.getU8() * 4;
			int l18 = buffer.getU8() * 4;
			int k19 = buffer.getU16();
			int j20 = buffer.getU16();
			int i21 = buffer.getU8();
			int j21 = buffer.getU8();
			if ((l5 >= 0) && (k8 >= 0) && (l5 < 104) && (k8 < 104) && (j11 >= 0) && (k13 >= 0) && (j11 < 104) && (k13 < 104) && (i17 != 65535)) {
				l5 = (l5 * 128) + 64;
				k8 = (k8 * 128) + 64;
				j11 = (j11 * 128) + 64;
				k13 = (k13 * 128) + 64;
				ProjectileEntity projectile = new ProjectileEntity(i21, l18, k19 + anInt1161, j20 + anInt1161, j21, anInt918, method42(anInt918, k8, l5) - i18, k8, l5, l15, i17);
				projectile.method455(k19 + anInt1161, k13, method42(anInt918, k13, j11) - l18, j11);
				aList_1013.method249(projectile);
			}
		}
	}

	public void method139(Buffer buffer) {
		buffer.accessBits();
		int k = buffer.getN(8);
		if (k < anInt836) {
			for (int l = k; l < anInt836; l++) {
				anIntArray840[anInt839++] = anIntArray837[l];
			}
		}
		if (k > anInt836) {
			Signlink.reporterror(aString1173 + " Too many npcs");
			throw new RuntimeException("eek");
		}
		anInt836 = 0;
		for (int i1 = 0; i1 < k; i1++) {
			int j1 = anIntArray837[i1];
			NPCEntity npc = aNpcArray835[j1];
			int k1 = buffer.getN(1);
			if (k1 == 0) {
				anIntArray837[anInt836++] = j1;
				npc.anInt1537 = anInt1161;
			} else {
				int l1 = buffer.getN(2);
				if (l1 == 0) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = anInt1161;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = anInt1161;
					int i2 = buffer.getN(3);
					npc.method448(false, i2);
					int k2 = buffer.getN(1);
					if (k2 == 1) {
						anIntArray894[anInt893++] = j1;
					}
				} else if (l1 == 2) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = anInt1161;
					int j2 = buffer.getN(3);
					npc.method448(true, j2);
					int l2 = buffer.getN(3);
					npc.method448(true, l2);
					int i3 = buffer.getN(1);
					if (i3 == 1) {
						anIntArray894[anInt893++] = j1;
					}
				} else if (l1 == 3) {
					anIntArray840[anInt839++] = j1;
				}
			}
		}
	}

	@SuppressWarnings("StringConcatenationInLoop")
	public void method140() {
		if (anInt833 == 0) {
			int i = (super.anInt10 / 2) - 80;
			int l = (super.anInt11 / 2) + 20;
			l += 20;
			if ((super.anInt26 == 1) && (super.anInt27 >= (i - 75)) && (super.anInt27 <= (i + 75)) && (super.anInt28 >= (l - 20)) && (super.anInt28 <= (l + 20))) {
				anInt833 = 3;
				anInt1216 = 0;
			}
			i = (super.anInt10 / 2) + 80;
			if ((super.anInt26 == 1) && (super.anInt27 >= (i - 75)) && (super.anInt27 <= (i + 75)) && (super.anInt28 >= (l - 20)) && (super.anInt28 <= (l + 20))) {
				aString1266 = "";
				aString1267 = "Enter your username & password.";
				anInt833 = 2;
				anInt1216 = 0;
			}
		} else {
			if (anInt833 == 2) {
				int j = (super.anInt11 / 2) - 40;
				j += 30;
				j += 25;
				if ((super.anInt26 == 1) && (super.anInt28 >= (j - 15)) && (super.anInt28 < j)) {
					anInt1216 = 0;
				}
				j += 15;
				if ((super.anInt26 == 1) && (super.anInt28 >= (j - 15)) && (super.anInt28 < j)) {
					anInt1216 = 1;
				}
				int i1 = (super.anInt10 / 2) - 80;
				int k1 = (super.anInt11 / 2) + 50;
				k1 += 20;
				if ((super.anInt26 == 1) && (super.anInt27 >= (i1 - 75)) && (super.anInt27 <= (i1 + 75)) && (super.anInt28 >= (k1 - 20)) && (super.anInt28 <= (k1 + 20))) {
					anInt1038 = 0;
					method84(aString1173, aString1174, false);
					if (aBoolean1157) {
						return;
					}
				}
				i1 = (super.anInt10 / 2) + 80;
				if ((super.anInt26 == 1) && (super.anInt27 >= (i1 - 75)) && (super.anInt27 <= (i1 + 75)) && (super.anInt28 >= (k1 - 20)) && (super.anInt28 <= (k1 + 20))) {
					anInt833 = 0;
					aString1173 = "";
					aString1174 = "";
				}
				do {
					int l1 = method5();
					if (l1 == -1) {
						break;
					}
					boolean flag1 = false;
					for (int i2 = 0; i2 < aString1162.length(); i2++) {
						if (l1 != aString1162.charAt(i2)) {
							continue;
						}
						flag1 = true;
						break;
					}
					if (anInt1216 == 0) {
						if ((l1 == 8) && (aString1173.length() > 0)) {
							aString1173 = aString1173.substring(0, aString1173.length() - 1);
						}
						if ((l1 == 9) || (l1 == 10) || (l1 == 13)) {
							anInt1216 = 1;
						}
						if (flag1) {
							aString1173 += (char) l1;
						}
						if (aString1173.length() > 12) {
							aString1173 = aString1173.substring(0, 12);
						}
					} else if (anInt1216 == 1) {
						if ((l1 == 8) && (aString1174.length() > 0)) {
							aString1174 = aString1174.substring(0, aString1174.length() - 1);
						}
						if ((l1 == 9) || (l1 == 10) || (l1 == 13)) {
							anInt1216 = 0;
						}
						if (flag1) {
							aString1174 += (char) l1;
						}
						if (aString1174.length() > 20) {
							aString1174 = aString1174.substring(0, 20);
						}
					}
				} while (true);
				return;
			}
			if (anInt833 == 3) {
				int k = super.anInt10 / 2;
				int j1 = (super.anInt11 / 2) + 50;
				j1 += 20;
				if ((super.anInt26 == 1) && (super.anInt27 >= (k - 75)) && (super.anInt27 <= (k + 75)) && (super.anInt28 >= (j1 - 20)) && (super.anInt28 <= (j1 + 20))) {
					anInt833 = 0;
				}
			}
		}
	}

	public void method141(Image24 image, int i, int j) {
		int k = (anInt1185 + anInt1209) & 0x7ff;
		int l = (i * i) + (j * j);
		if (l > 6400) {
			return;
		}
		int i1 = Model.sin[k];
		int j1 = Model.cos[k];
		i1 = (i1 * 256) / (anInt1170 + 256);
		j1 = (j1 * 256) / (anInt1170 + 256);
		int k1 = ((j * i1) + (i * j1)) >> 16;
		int l1 = ((j * j1) - (i * i1)) >> 16;
		if (l > 2500) {
			image.method354(aImage_1197, 83 - l1 - (image.anInt1445 / 2) - 4, ((94 + k1) - (image.anInt1444 / 2)) + 4);
		} else {
			image.method348(((94 + k1) - (image.anInt1444 / 2)) + 4, 83 - l1 - (image.anInt1445 / 2) - 4);
		}
	}

	public void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
		if ((i1 < 1) || (i < 1) || (i1 > 102) || (i > 102)) {
			return;
		}
		if (aBoolean960 && (j != anInt918)) {
			return;
		}
		int i2 = 0;
		if (j1 == 0) {
			i2 = aGraph_946.method300(j, i1, i);
		}
		if (j1 == 1) {
			i2 = aGraph_946.method301(j, i1, i);
		}
		if (j1 == 2) {
			i2 = aGraph_946.method302(j, i1, i);
		}
		if (j1 == 3) {
			i2 = aGraph_946.method303(j, i1, i);
		}
		if (i2 != 0) {
			int i3 = aGraph_946.method304(j, i1, i, i2);
			int j2 = (i2 >> 14) & 0x7fff;
			int k2 = i3 & 0x1f;
			int l2 = i3 >> 6;
			if (j1 == 0) {
				aGraph_946.method291(i1, j, i);
				LocType type = LocType.method572(j2);
				if (type.aBoolean767) {
					aCollisionMapArray1230[j].method215(l2, k2, type.aBoolean757, i1, i);
				}
			}
			if (j1 == 1) {
				aGraph_946.method292(i, j, i1);
			}
			if (j1 == 2) {
				aGraph_946.method293(j, i1, i);
				LocType type_1 = LocType.method572(j2);
				if (((i1 + type_1.anInt744) > 103) || ((i + type_1.anInt744) > 103) || ((i1 + type_1.anInt761) > 103) || ((i + type_1.anInt761) > 103)) {
					return;
				}
				if (type_1.aBoolean767) {
					aCollisionMapArray1230[j].method216(l2, type_1.anInt744, i1, i, type_1.anInt761, type_1.aBoolean757);
				}
			}
			if (j1 == 3) {
				aGraph_946.method294(j, i, i1);
				LocType type_2 = LocType.method572(j2);
				if (type_2.aBoolean767 && type_2.aBoolean778) {
					aCollisionMapArray1230[j].method218(i, i1);
				}
			}
		}
		if (k1 >= 0) {
			int j3 = j;
			if ((j3 < 3) && ((aByteArrayArrayArray1258[1][i1][i] & 2) == 2)) {
				j3++;
			}
			SceneBuilder.method188(aGraph_946, k, i, l, j3, aCollisionMapArray1230[j], anIntArrayArrayArray1214, i1, k1, j);
		}
	}

	public void method143(int i, Buffer buffer) {
		anInt839 = 0;
		anInt893 = 0;
		method117(buffer);
		method134(buffer);
		method91(buffer, i);
		method49(buffer);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (aPlayerArray890[l].anInt1537 != anInt1161) {
				aPlayerArray890[l] = null;
			}
		}
		if (buffer.position != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + buffer.position + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < anInt891; i1++) {
			if (aPlayerArray890[anIntArray892[i1]] == null) {
				Signlink.reporterror(aString1173 + " null entry in pl list - pos:" + i1 + " size:" + anInt891);
				throw new RuntimeException("eek");
			}
		}
	}

	public void method144(int j, int k, int l, int i1, int j1, int k1) {
		int l1 = (2048 - k) & 0x7ff;
		int i2 = (2048 - j1) & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if (l1 != 0) {
			int i3 = Model.sin[l1];
			int k3 = Model.cos[l1];
			int i4 = ((k2 * k3) - (l2 * i3)) >> 16;
			l2 = ((k2 * i3) + (l2 * k3)) >> 16;
			k2 = i4;
		}
		if (i2 != 0) {
			int j3 = Model.sin[i2];
			int l3 = Model.cos[i2];
			int j4 = ((l2 * j3) + (j2 * l3)) >> 16;
			l2 = ((l2 * l3) - (j2 * j3)) >> 16;
			j2 = j4;
		}
		anInt858 = l - j2;
		anInt859 = i1 - k2;
		anInt860 = k1 - l2;
		anInt861 = k;
		anInt862 = j1;
	}

	public boolean method145() {
		if (aConnection_1168 == null) {
			return false;
		}
		try {
			int i = aConnection_1168.method269();
			if (i == 0) {
				return false;
			}
			if (anInt1008 == -1) {
				aConnection_1168.method270(aBuffer_1083.data, 0, 1);
				anInt1008 = aBuffer_1083.data[0] & 0xff;
				if (aISAACCipher_1000 != null) {
					anInt1008 = (anInt1008 - aISAACCipher_1000.method246()) & 0xff;
				}
				anInt1007 = PacketConstants.anIntArray553[anInt1008];
				i--;
			}
			if (anInt1007 == -1) {
				if (i > 0) {
					aConnection_1168.method270(aBuffer_1083.data, 0, 1);
					anInt1007 = aBuffer_1083.data[0] & 0xff;
					i--;
				} else {
					return false;
				}
			}
			if (anInt1007 == -2) {
				if (i > 1) {
					aConnection_1168.method270(aBuffer_1083.data, 0, 2);
					aBuffer_1083.position = 0;
					anInt1007 = aBuffer_1083.getU16();
					i -= 2;
				} else {
					return false;
				}
			}
			if (i < anInt1007) {
				return false;
			}
			aBuffer_1083.position = 0;
			aConnection_1168.method270(aBuffer_1083.data, 0, anInt1007);
			anInt1009 = 0;
			anInt843 = anInt842;
			anInt842 = anInt841;
			anInt841 = anInt1008;
			if (anInt1008 == 81) {
				method143(anInt1007, aBuffer_1083);
				aBoolean1080 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 176) {
				anInt1167 = aBuffer_1083.getU8C();
				anInt1154 = aBuffer_1083.getU16A();
				anInt1120 = aBuffer_1083.getU8();
				anInt1193 = aBuffer_1083.get32ME();
				anInt1006 = aBuffer_1083.getU16();
				if ((anInt1193 != 0) && (anInt857 == -1)) {
					Signlink.dnslookup(StringUtil.formatIPv4(anInt1193));
					method147();
					char c = '\u028A';
					if ((anInt1167 != 201) || (anInt1120 == 1)) {
						c = '\u028F';
					}
					aString881 = "";
					aBoolean1158 = false;
					for (int k9 = 0; k9 < Component.aComponentArray210.length; k9++) {
						if ((Component.aComponentArray210[k9] == null) || (Component.aComponentArray210[k9].anInt214 != c)) {
							continue;
						}
						anInt857 = Component.aComponentArray210[k9].anInt236;
						break;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 64) {
				anInt1268 = aBuffer_1083.getU8C();
				anInt1269 = aBuffer_1083.getU8S();
				for (int j = anInt1268; j < (anInt1268 + 8); j++) {
					for (int l9 = anInt1269; l9 < (anInt1269 + 8); l9++) {
						if (aListArrayArrayArray827[anInt918][j][l9] != null) {
							aListArrayArrayArray827[anInt918][j][l9] = null;
							method25(j, l9);
						}
					}
				}
				for (SceneLocTemporary loc = (SceneLocTemporary) aList_1179.method252(); loc != null; loc = (SceneLocTemporary) aList_1179.method254()) {
					if ((loc.anInt1297 >= anInt1268) && (loc.anInt1297 < (anInt1268 + 8)) && (loc.anInt1298 >= anInt1269) && (loc.anInt1298 < (anInt1269 + 8)) && (loc.anInt1295 == anInt918)) {
						loc.anInt1294 = 0;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 185) {
				int k = aBuffer_1083.getU16LEA();
				Component.aComponentArray210[k].anInt233 = 3;
				if (aPlayer_1126.aType_1698 == null) {
					Component.aComponentArray210[k].anInt234 = (aPlayer_1126.anIntArray1700[0] << 25) + (aPlayer_1126.anIntArray1700[4] << 20) + (aPlayer_1126.anIntArray1717[0] << 15) + (aPlayer_1126.anIntArray1717[8] << 10) + (aPlayer_1126.anIntArray1717[11] << 5) + aPlayer_1126.anIntArray1717[1];
				} else {
					Component.aComponentArray210[k].anInt234 = (int) (0x12345678L + aPlayer_1126.aType_1698.aLong78);
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 107) {
				aBoolean1160 = false;
				for (int l = 0; l < 5; l++) {
					aBooleanArray876[l] = false;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 72) {
				int i1 = aBuffer_1083.getU16LE();
				Component component = Component.aComponentArray210[i1];
				for (int k15 = 0; k15 < component.anIntArray253.length; k15++) {
					component.anIntArray253[k15] = -1;
					component.anIntArray253[k15] = 0;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 214) {
				anInt822 = anInt1007 / 8;
				for (int j1 = 0; j1 < anInt822; j1++) {
					aLongArray925[j1] = aBuffer_1083.get64();
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 166) {
				aBoolean1160 = true;
				anInt1098 = aBuffer_1083.getU8();
				anInt1099 = aBuffer_1083.getU8();
				anInt1100 = aBuffer_1083.getU16();
				anInt1101 = aBuffer_1083.getU8();
				anInt1102 = aBuffer_1083.getU8();
				if (anInt1102 >= 100) {
					anInt858 = (anInt1098 * 128) + 64;
					anInt860 = (anInt1099 * 128) + 64;
					anInt859 = method42(anInt918, anInt860, anInt858) - anInt1100;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 134) {
				aBoolean1153 = true;
				int k1 = aBuffer_1083.getU8();
				int i10 = aBuffer_1083.get32RME();
				int l15 = aBuffer_1083.getU8();
				anIntArray864[k1] = i10;
				anIntArray922[k1] = l15;
				anIntArray1044[k1] = 1;
				for (int k20 = 0; k20 < 98; k20++) {
					if (i10 >= anIntArray1019[k20]) {
						anIntArray1044[k1] = k20 + 2;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 71) {
				int l1 = aBuffer_1083.getU16();
				int j10 = aBuffer_1083.getU8A();
				if (l1 == 65535) {
					l1 = -1;
				}
				anIntArray1130[j10] = l1;
				aBoolean1153 = true;
				aBoolean1103 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 74) {
				int i2 = aBuffer_1083.getU16LE();
				if (i2 == 65535) {
					i2 = -1;
				}
				if ((i2 != anInt956) && aBoolean1151 && !aBoolean960 && (anInt1259 == 0)) {
					anInt1227 = i2;
					aBoolean1228 = true;
					aOnDemand_1068.method558(2, anInt1227);
				}
				anInt956 = i2;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 121) {
				int j2 = aBuffer_1083.getU16LEA();
				int k10 = aBuffer_1083.getU16A();
				if (aBoolean1151 && !aBoolean960) {
					anInt1227 = j2;
					aBoolean1228 = false;
					aOnDemand_1068.method558(2, anInt1227);
					anInt1259 = k10;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 109) {
				method44();
				anInt1008 = -1;
				return false;
			}
			if (anInt1008 == 70) {
				int k2 = aBuffer_1083.get16();
				int l10 = aBuffer_1083.get16LE();
				int i16 = aBuffer_1083.getU16LE();
				Component component_5 = Component.aComponentArray210[i16];
				component_5.anInt263 = k2;
				component_5.anInt265 = l10;
				anInt1008 = -1;
				return true;
			}
			if ((anInt1008 == 73) || (anInt1008 == 241)) {
				int l2 = anInt1069;
				int i11 = anInt1070;
				if (anInt1008 == 73) {
					l2 = aBuffer_1083.getU16A();
					i11 = aBuffer_1083.getU16();
					aBoolean1159 = false;
				}
				if (anInt1008 == 241) {
					i11 = aBuffer_1083.getU16A();
					aBuffer_1083.accessBits();
					for (int j16 = 0; j16 < 4; j16++) {
						for (int l20 = 0; l20 < 13; l20++) {
							for (int j23 = 0; j23 < 13; j23++) {
								int i26 = aBuffer_1083.getN(1);
								if (i26 == 1) {
									anIntArrayArrayArray1129[j16][l20][j23] = aBuffer_1083.getN(26);
								} else {
									anIntArrayArrayArray1129[j16][l20][j23] = -1;
								}
							}
						}
					}
					aBuffer_1083.accessBytes();
					l2 = aBuffer_1083.getU16();
					aBoolean1159 = true;
				}
				if ((anInt1069 == l2) && (anInt1070 == i11) && (anInt1023 == 2)) {
					anInt1008 = -1;
					return true;
				}
				anInt1069 = l2;
				anInt1070 = i11;
				anInt1034 = (anInt1069 - 6) * 8;
				anInt1035 = (anInt1070 - 6) * 8;
				aBoolean1141 = (((anInt1069 / 8) == 48) || ((anInt1069 / 8) == 49)) && ((anInt1070 / 8) == 48);
				if (((anInt1069 / 8) == 48) && ((anInt1070 / 8) == 148)) {
					aBoolean1141 = true;
				}
				anInt1023 = 1;
				aLong824 = System.currentTimeMillis();
				aArea_1165.method237();
				aFont_1271.method381(0, "Loading - please wait.", 151, 257);
				aFont_1271.method381(0xffffff, "Loading - please wait.", 150, 256);
				aArea_1165.method238(4, super.aGraphics12, 4);
				if (anInt1008 == 73) {
					int k16 = 0;
					for (int i21 = (anInt1069 - 6) / 8; i21 <= ((anInt1069 + 6) / 8); i21++) {
						for (int k23 = (anInt1070 - 6) / 8; k23 <= ((anInt1070 + 6) / 8); k23++) {
							k16++;
						}
					}
					aByteArrayArray1183 = new byte[k16][];
					aByteArrayArray1247 = new byte[k16][];
					anIntArray1234 = new int[k16];
					anIntArray1235 = new int[k16];
					anIntArray1236 = new int[k16];
					k16 = 0;
					for (int l23 = (anInt1069 - 6) / 8; l23 <= ((anInt1069 + 6) / 8); l23++) {
						for (int j26 = (anInt1070 - 6) / 8; j26 <= ((anInt1070 + 6) / 8); j26++) {
							anIntArray1234[k16] = (l23 << 8) + j26;
							if (aBoolean1141 && ((j26 == 49) || (j26 == 149) || (j26 == 147) || (l23 == 50) || ((l23 == 49) && (j26 == 47)))) {
								anIntArray1235[k16] = -1;
								anIntArray1236[k16] = -1;
							} else {
								int k28 = anIntArray1235[k16] = aOnDemand_1068.method562(0, j26, l23);
								if (k28 != -1) {
									aOnDemand_1068.method558(3, k28);
								}
								int j30 = anIntArray1236[k16] = aOnDemand_1068.method562(1, j26, l23);
								if (j30 != -1) {
									aOnDemand_1068.method558(3, j30);
								}
							}
							k16++;
						}
					}
				}
				if (anInt1008 == 241) {
					int l16 = 0;
					int[] ai = new int[676];
					for (int i24 = 0; i24 < 4; i24++) {
						for (int k26 = 0; k26 < 13; k26++) {
							for (int l28 = 0; l28 < 13; l28++) {
								int k30 = anIntArrayArrayArray1129[i24][k26][l28];
								if (k30 != -1) {
									int k31 = (k30 >> 14) & 0x3ff;
									int i32 = (k30 >> 3) & 0x7ff;
									int k32 = ((k31 / 8) << 8) + (i32 / 8);
									for (int j33 = 0; j33 < l16; j33++) {
										if (ai[j33] != k32) {
											continue;
										}
										k32 = -1;
										break;
									}
									if (k32 != -1) {
										ai[l16++] = k32;
									}
								}
							}
						}
					}
					aByteArrayArray1183 = new byte[l16][];
					aByteArrayArray1247 = new byte[l16][];
					anIntArray1234 = new int[l16];
					anIntArray1235 = new int[l16];
					anIntArray1236 = new int[l16];
					for (int l26 = 0; l26 < l16; l26++) {
						int i29 = anIntArray1234[l26] = ai[l26];
						int l30 = (i29 >> 8) & 0xff;
						int l31 = i29 & 0xff;
						int j32 = anIntArray1235[l26] = aOnDemand_1068.method562(0, l31, l30);
						if (j32 != -1) {
							aOnDemand_1068.method558(3, j32);
						}
						int i33 = anIntArray1236[l26] = aOnDemand_1068.method562(1, l31, l30);
						if (i33 != -1) {
							aOnDemand_1068.method558(3, i33);
						}
					}
				}
				int i17 = anInt1034 - anInt1036;
				int j21 = anInt1035 - anInt1037;
				anInt1036 = anInt1034;
				anInt1037 = anInt1035;
				for (int j24 = 0; j24 < 16384; j24++) {
					NPCEntity npc = aNpcArray835[j24];
					if (npc != null) {
						for (int j29 = 0; j29 < 10; j29++) {
							npc.anIntArray1500[j29] -= i17;
							npc.anIntArray1501[j29] -= j21;
						}
						npc.anInt1550 -= i17 * 128;
						npc.anInt1551 -= j21 * 128;
					}
				}
				for (int i27 = 0; i27 < anInt888; i27++) {
					PlayerEntity player = aPlayerArray890[i27];
					if (player != null) {
						for (int i31 = 0; i31 < 10; i31++) {
							player.anIntArray1500[i31] -= i17;
							player.anIntArray1501[i31] -= j21;
						}
						player.anInt1550 -= i17 * 128;
						player.anInt1551 -= j21 * 128;
					}
				}
				aBoolean1080 = true;
				byte byte1 = 0;
				byte byte2 = 104;
				byte byte3 = 1;
				if (i17 < 0) {
					byte1 = 103;
					byte2 = -1;
					byte3 = -1;
				}
				byte byte4 = 0;
				byte byte5 = 104;
				byte byte6 = 1;
				if (j21 < 0) {
					byte4 = 103;
					byte5 = -1;
					byte6 = -1;
				}
				for (int k33 = byte1; k33 != byte2; k33 += byte3) {
					for (int l33 = byte4; l33 != byte5; l33 += byte6) {
						int i34 = k33 + i17;
						int j34 = l33 + j21;
						for (int k34 = 0; k34 < 4; k34++) {
							if ((i34 >= 0) && (j34 >= 0) && (i34 < 104) && (j34 < 104)) {
								aListArrayArrayArray827[k34][k33][l33] = aListArrayArrayArray827[k34][i34][j34];
							} else {
								aListArrayArrayArray827[k34][k33][l33] = null;
							}
						}
					}
				}
				for (SceneLocTemporary loc_1 = (SceneLocTemporary) aList_1179.method252(); loc_1 != null; loc_1 = (SceneLocTemporary) aList_1179.method254()) {
					loc_1.anInt1297 -= i17;
					loc_1.anInt1298 -= j21;
					if ((loc_1.anInt1297 < 0) || (loc_1.anInt1298 < 0) || (loc_1.anInt1297 >= 104) || (loc_1.anInt1298 >= 104)) {
						loc_1.method329();
					}
				}
				if (anInt1261 != 0) {
					anInt1261 -= i17;
					anInt1262 -= j21;
				}
				aBoolean1160 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 208) {
				int i3 = aBuffer_1083.get16LE();
				if (i3 >= 0) {
					method60(i3);
				}
				anInt1018 = i3;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 99) {
				anInt1021 = aBuffer_1083.getU8();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 75) {
				int j3 = aBuffer_1083.getU16LEA();
				int j11 = aBuffer_1083.getU16LEA();
				Component.aComponentArray210[j11].anInt233 = 2;
				Component.aComponentArray210[j11].anInt234 = j3;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 114) {
				anInt1104 = aBuffer_1083.getU16LE() * 30;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 60) {
				anInt1269 = aBuffer_1083.getU8();
				anInt1268 = aBuffer_1083.getU8C();
				while (aBuffer_1083.position < anInt1007) {
					int k3 = aBuffer_1083.getU8();
					method137(aBuffer_1083, k3);
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 35) {
				int l3 = aBuffer_1083.getU8();
				int k11 = aBuffer_1083.getU8();
				int j17 = aBuffer_1083.getU8();
				int k21 = aBuffer_1083.getU8();
				aBooleanArray876[l3] = true;
				anIntArray873[l3] = k11;
				anIntArray1203[l3] = j17;
				anIntArray928[l3] = k21;
				anIntArray1030[l3] = 0;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 174) {
				int i4 = aBuffer_1083.getU16();
				int l11 = aBuffer_1083.getU8();
				int k17 = aBuffer_1083.getU16();
				if (aBoolean848 && !aBoolean960 && (anInt1062 < 50)) {
					anIntArray1207[anInt1062] = i4;
					anIntArray1241[anInt1062] = l11;
					anIntArray1250[anInt1062] = k17 + SoundTrack.anIntArray326[i4];
					anInt1062++;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 104) {
				int j4 = aBuffer_1083.getU8C();
				int i12 = aBuffer_1083.getU8A();
				String s6 = aBuffer_1083.getString();
				if ((j4 >= 1) && (j4 <= 5)) {
					if (s6.equalsIgnoreCase("null")) {
						s6 = null;
					}
					aStringArray1127[j4 - 1] = s6;
					aBooleanArray1128[j4 - 1] = i12 == 0;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 78) {
				anInt1261 = 0;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 253) {
				String s = aBuffer_1083.getString();
				if (s.endsWith(":tradereq:")) {
					String s3 = s.substring(0, s.indexOf(":"));
					long l17 = StringUtil.toBase37(s3);
					boolean flag2 = false;
					for (int j27 = 0; j27 < anInt822; j27++) {
						if (aLongArray925[j27] != l17) {
							continue;
						}
						flag2 = true;
						break;
					}
					if (!flag2 && (anInt1251 == 0)) {
						method77("wishes to trade with you.", 4, s3);
					}
				} else if (s.endsWith(":duelreq:")) {
					String s4 = s.substring(0, s.indexOf(":"));
					long l18 = StringUtil.toBase37(s4);
					boolean flag3 = false;
					for (int k27 = 0; k27 < anInt822; k27++) {
						if (aLongArray925[k27] != l18) {
							continue;
						}
						flag3 = true;
						break;
					}
					if (!flag3 && (anInt1251 == 0)) {
						method77("wishes to duel with you.", 8, s4);
					}
				} else if (s.endsWith(":chalreq:")) {
					String s5 = s.substring(0, s.indexOf(":"));
					long l19 = StringUtil.toBase37(s5);
					boolean flag4 = false;
					for (int l27 = 0; l27 < anInt822; l27++) {
						if (aLongArray925[l27] != l19) {
							continue;
						}
						flag4 = true;
						break;
					}
					if (!flag4 && (anInt1251 == 0)) {
						String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
						method77(s8, 8, s5);
					}
				} else {
					method77(s, 0, "");
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 1) {
				for (PlayerEntity player : aPlayerArray890) {
					if (player != null) {
						player.anInt1526 = -1;
					}
				}
				for (NPCEntity npc : aNpcArray835) {
					if (npc != null) {
						npc.anInt1526 = -1;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 50) {
				long l4 = aBuffer_1083.get64();
				int i18 = aBuffer_1083.getU8();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(l4));
				for (int k24 = 0; k24 < anInt899; k24++) {
					if (l4 != aLongArray955[k24]) {
						continue;
					}
					if (anIntArray826[k24] != i18) {
						anIntArray826[k24] = i18;
						aBoolean1153 = true;
						if (i18 > 0) {
							method77(s7 + " has logged in.", 5, "");
						}
						if (i18 == 0) {
							method77(s7 + " has logged out.", 5, "");
						}
					}
					s7 = null;
					break;
				}
				if ((s7 != null) && (anInt899 < 200)) {
					aLongArray955[anInt899] = l4;
					aStringArray1082[anInt899] = s7;
					anIntArray826[anInt899] = i18;
					anInt899++;
					aBoolean1153 = true;
				}
				for (boolean flag6 = false; !flag6; ) {
					flag6 = true;
					for (int k29 = 0; k29 < (anInt899 - 1); k29++) {
						if (((anIntArray826[k29] != anInt957) && (anIntArray826[k29 + 1] == anInt957)) || ((anIntArray826[k29] == 0) && (anIntArray826[k29 + 1] != 0))) {
							int j31 = anIntArray826[k29];
							anIntArray826[k29] = anIntArray826[k29 + 1];
							anIntArray826[k29 + 1] = j31;
							String s10 = aStringArray1082[k29];
							aStringArray1082[k29] = aStringArray1082[k29 + 1];
							aStringArray1082[k29 + 1] = s10;
							long l32 = aLongArray955[k29];
							aLongArray955[k29] = aLongArray955[k29 + 1];
							aLongArray955[k29 + 1] = l32;
							aBoolean1153 = true;
							flag6 = false;
						}
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 110) {
				if (anInt1221 == 12) {
					aBoolean1153 = true;
				}
				anInt1148 = aBuffer_1083.getU8();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 254) {
				anInt855 = aBuffer_1083.getU8();
				if (anInt855 == 1) {
					anInt1222 = aBuffer_1083.getU16();
				}
				if ((anInt855 >= 2) && (anInt855 <= 6)) {
					if (anInt855 == 2) {
						anInt937 = 64;
						anInt938 = 64;
					}
					if (anInt855 == 3) {
						anInt937 = 0;
						anInt938 = 64;
					}
					if (anInt855 == 4) {
						anInt937 = 128;
						anInt938 = 64;
					}
					if (anInt855 == 5) {
						anInt937 = 64;
						anInt938 = 0;
					}
					if (anInt855 == 6) {
						anInt937 = 64;
						anInt938 = 128;
					}
					anInt855 = 2;
					anInt934 = aBuffer_1083.getU16();
					anInt935 = aBuffer_1083.getU16();
					anInt936 = aBuffer_1083.getU8();
				}
				if (anInt855 == 10) {
					anInt933 = aBuffer_1083.getU16();
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 248) {
				int i5 = aBuffer_1083.getU16A();
				int k12 = aBuffer_1083.getU16();
				if (anInt1276 != -1) {
					anInt1276 = -1;
					aBoolean1223 = true;
				}
				if (anInt1225 != 0) {
					anInt1225 = 0;
					aBoolean1223 = true;
				}
				anInt857 = i5;
				anInt1189 = k12;
				aBoolean1153 = true;
				aBoolean1103 = true;
				aBoolean1149 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 79) {
				int j5 = aBuffer_1083.getU16LE();
				int l12 = aBuffer_1083.getU16A();
				Component component_3 = Component.aComponentArray210[j5];
				if ((component_3 != null) && (component_3.anInt262 == 0)) {
					if (l12 < 0) {
						l12 = 0;
					}
					if (l12 > (component_3.anInt261 - component_3.anInt267)) {
						l12 = component_3.anInt261 - component_3.anInt267;
					}
					component_3.anInt224 = l12;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 68) {
				for (int k5 = 0; k5 < anIntArray971.length; k5++) {
					if (anIntArray971[k5] != anIntArray1045[k5]) {
						anIntArray971[k5] = anIntArray1045[k5];
						method33(k5);
						aBoolean1153 = true;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 196) {
				long l5 = aBuffer_1083.get64();
				int j18 = aBuffer_1083.get32();
				int l21 = aBuffer_1083.getU8();
				boolean flag5 = false;
				for (int i28 = 0; i28 < 100; i28++) {
					if (anIntArray1240[i28] != j18) {
						continue;
					}
					flag5 = true;
					break;
				}
				if (l21 <= 1) {
					for (int l29 = 0; l29 < anInt822; l29++) {
						if (aLongArray925[l29] != l5) {
							continue;
						}
						flag5 = true;
						break;
					}
				}
				if (!flag5 && (anInt1251 == 0)) {
					try {
						anIntArray1240[anInt1169] = j18;
						anInt1169 = (anInt1169 + 1) % 100;
						String s9 = Huffman.method525(anInt1007 - 13, aBuffer_1083);
						if (l21 != 3) {
							s9 = Censor.method497(s9, 0);
						}
						if ((l21 == 2) || (l21 == 3)) {
							method77(s9, 7, "@cr2@" + StringUtil.formatName(StringUtil.fromBase37(l5)));
						} else if (l21 == 1) {
							method77(s9, 7, "@cr1@" + StringUtil.formatName(StringUtil.fromBase37(l5)));
						} else {
							method77(s9, 3, StringUtil.formatName(StringUtil.fromBase37(l5)));
						}
					} catch (Exception exception1) {
						Signlink.reporterror("cde1");
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 85) {
				anInt1269 = aBuffer_1083.getU8C();
				anInt1268 = aBuffer_1083.getU8C();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 24) {
				anInt1054 = aBuffer_1083.getU8S();
				if (anInt1054 == anInt1221) {
					if (anInt1054 == 3) {
						anInt1221 = 1;
					} else {
						anInt1221 = 3;
					}
					aBoolean1153 = true;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 246) {
				int i6 = aBuffer_1083.getU16LE();
				int i13 = aBuffer_1083.getU16();
				int k18 = aBuffer_1083.getU16();
				if (k18 == 65535) {
					Component.aComponentArray210[i6].anInt233 = 0;
				} else {
					ObjType type = ObjType.method198(k18);
					Component.aComponentArray210[i6].anInt233 = 4;
					Component.aComponentArray210[i6].anInt234 = k18;
					Component.aComponentArray210[i6].anInt270 = type.anInt190;
					Component.aComponentArray210[i6].anInt271 = type.anInt198;
					Component.aComponentArray210[i6].anInt269 = (type.anInt181 * 100) / i13;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 171) {
				boolean flag1 = aBuffer_1083.getU8() == 1;
				int j13 = aBuffer_1083.getU16();
				Component.aComponentArray210[j13].aBoolean266 = flag1;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 142) {
				int j6 = aBuffer_1083.getU16LE();
				method60(j6);
				if (anInt1276 != -1) {
					anInt1276 = -1;
					aBoolean1223 = true;
				}
				if (anInt1225 != 0) {
					anInt1225 = 0;
					aBoolean1223 = true;
				}
				anInt1189 = j6;
				aBoolean1153 = true;
				aBoolean1103 = true;
				anInt857 = -1;
				aBoolean1149 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 126) {
				String s1 = aBuffer_1083.getString();
				int k13 = aBuffer_1083.getU16A();
				if ((k13 >= 0) && (k13 < Component.aComponentArray210.length)) {
					Component component = Component.aComponentArray210[k13];
					if (component != null) {
						component.aString248 = s1;
						if (component.anInt236 == anIntArray1130[anInt1221]) {
							aBoolean1153 = true;
						}
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 206) {
				anInt1287 = aBuffer_1083.getU8();
				anInt845 = aBuffer_1083.getU8();
				anInt1248 = aBuffer_1083.getU8();
				aBoolean1233 = true;
				aBoolean1223 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 240) {
				if (anInt1221 == 12) {
					aBoolean1153 = true;
				}
				anInt878 = aBuffer_1083.get16();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 8) {
				int k6 = aBuffer_1083.getU16LEA();
				int l13 = aBuffer_1083.getU16();
				Component.aComponentArray210[k6].anInt233 = 1;
				Component.aComponentArray210[k6].anInt234 = l13;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 122) {
				int l6 = aBuffer_1083.getU16LEA();
				int i14 = aBuffer_1083.getU16LEA();
				int i19 = (i14 >> 10) & 0x1f;
				int i22 = (i14 >> 5) & 0x1f;
				int l24 = i14 & 0x1f;
				Component.aComponentArray210[l6].anInt232 = (i19 << 19) + (i22 << 11) + (l24 << 3);
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 53) {
				aBoolean1153 = true;
				int i7 = aBuffer_1083.getU16();
				Component component_1 = Component.aComponentArray210[i7];
				int j19 = aBuffer_1083.getU16();
				for (int j22 = 0; j22 < j19; j22++) {
					int i25 = aBuffer_1083.getU8();
					if (i25 == 255) {
						i25 = aBuffer_1083.get32ME();
					}
					component_1.anIntArray253[j22] = aBuffer_1083.getU16LEA();
					component_1.anIntArray252[j22] = i25;
				}
				for (int j25 = j19; j25 < component_1.anIntArray253.length; j25++) {
					component_1.anIntArray253[j25] = 0;
					component_1.anIntArray252[j25] = 0;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 230) {
				int j7 = aBuffer_1083.getU16A();
				int j14 = aBuffer_1083.getU16();
				int k19 = aBuffer_1083.getU16();
				int k22 = aBuffer_1083.getU16LEA();
				Component.aComponentArray210[j14].anInt270 = k19;
				Component.aComponentArray210[j14].anInt271 = k22;
				Component.aComponentArray210[j14].anInt269 = j7;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 221) {
				anInt900 = aBuffer_1083.getU8();
				aBoolean1153 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 177) {
				aBoolean1160 = true;
				anInt995 = aBuffer_1083.getU8();
				anInt996 = aBuffer_1083.getU8();
				anInt997 = aBuffer_1083.getU16();
				anInt998 = aBuffer_1083.getU8();
				anInt999 = aBuffer_1083.getU8();
				if (anInt999 >= 100) {
					int k7 = (anInt995 * 128) + 64;
					int k14 = (anInt996 * 128) + 64;
					int i20 = method42(anInt918, k14, k7) - anInt997;
					int l22 = k7 - anInt858;
					int k25 = i20 - anInt859;
					int j28 = k14 - anInt860;
					int i30 = (int) Math.sqrt((l22 * l22) + (j28 * j28));
					anInt861 = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
					anInt862 = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
					if (anInt861 < 128) {
						anInt861 = 128;
					}
					if (anInt861 > 383) {
						anInt861 = 383;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 249) {
				anInt1046 = aBuffer_1083.getU8A();
				anInt884 = aBuffer_1083.getU16LEA();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 65) {
				method31(aBuffer_1083, anInt1007);
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 27) {
				aBoolean1256 = false;
				anInt1225 = 1;
				aString1004 = "";
				aBoolean1223 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 187) {
				aBoolean1256 = false;
				anInt1225 = 2;
				aString1004 = "";
				aBoolean1223 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 97) {
				int l7 = aBuffer_1083.getU16();
				method60(l7);
				if (anInt1189 != -1) {
					anInt1189 = -1;
					aBoolean1153 = true;
					aBoolean1103 = true;
				}
				if (anInt1276 != -1) {
					anInt1276 = -1;
					aBoolean1223 = true;
				}
				if (anInt1225 != 0) {
					anInt1225 = 0;
					aBoolean1223 = true;
				}
				anInt857 = l7;
				aBoolean1149 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 218) {
				anInt1042 = aBuffer_1083.get16LEA();
				aBoolean1223 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 87) {
				int j8 = aBuffer_1083.getU16LE();
				int l14 = aBuffer_1083.get32RME();
				anIntArray1045[j8] = l14;
				if (anIntArray971[j8] != l14) {
					anIntArray971[j8] = l14;
					method33(j8);
					aBoolean1153 = true;
					if (anInt1042 != -1) {
						aBoolean1223 = true;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 36) {
				int k8 = aBuffer_1083.getU16LE();
				byte byte0 = aBuffer_1083.get8();
				anIntArray1045[k8] = byte0;
				if (anIntArray971[k8] != byte0) {
					anIntArray971[k8] = byte0;
					method33(k8);
					aBoolean1153 = true;
					if (anInt1042 != -1) {
						aBoolean1223 = true;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 61) {
				anInt1055 = aBuffer_1083.getU8();
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 200) {
				int l8 = aBuffer_1083.getU16();
				int i15 = aBuffer_1083.get16();
				Component component_4 = Component.aComponentArray210[l8];
				component_4.anInt257 = i15;
				if (i15 == -1) {
					component_4.anInt246 = 0;
					component_4.anInt208 = 0;
				}
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 219) {
				if (anInt1189 != -1) {
					anInt1189 = -1;
					aBoolean1153 = true;
					aBoolean1103 = true;
				}
				if (anInt1276 != -1) {
					anInt1276 = -1;
					aBoolean1223 = true;
				}
				if (anInt1225 != 0) {
					anInt1225 = 0;
					aBoolean1223 = true;
				}
				anInt857 = -1;
				aBoolean1149 = false;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 34) {
				aBoolean1153 = true;
				int i9 = aBuffer_1083.getU16();
				Component component_2 = Component.aComponentArray210[i9];
				while (aBuffer_1083.position < anInt1007) {
					int j20 = aBuffer_1083.getUSmart();
					int i23 = aBuffer_1083.getU16();
					int l25 = aBuffer_1083.getU8();
					if (l25 == 255) {
						l25 = aBuffer_1083.get32();
					}
					if ((j20 >= 0) && (j20 < component_2.anIntArray253.length)) {
						component_2.anIntArray253[j20] = i23;
						component_2.anIntArray252[j20] = l25;
					}
				}
				anInt1008 = -1;
				return true;
			}
			if ((anInt1008 == 105) || (anInt1008 == 84) || (anInt1008 == 147) || (anInt1008 == 215) || (anInt1008 == 4) || (anInt1008 == 117) || (anInt1008 == 156) || (anInt1008 == 44) || (anInt1008 == 160) || (anInt1008 == 101) || (anInt1008 == 151)) {
				method137(aBuffer_1083, anInt1008);
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 106) {
				anInt1221 = aBuffer_1083.getU8C();
				aBoolean1153 = true;
				aBoolean1103 = true;
				anInt1008 = -1;
				return true;
			}
			if (anInt1008 == 164) {
				int j9 = aBuffer_1083.getU16LE();
				method60(j9);
				if (anInt1189 != -1) {
					anInt1189 = -1;
					aBoolean1153 = true;
					aBoolean1103 = true;
				}
				anInt1276 = j9;
				aBoolean1223 = true;
				anInt857 = -1;
				aBoolean1149 = false;
				anInt1008 = -1;
				return true;
			}
			Signlink.reporterror("T1 - " + anInt1008 + "," + anInt1007 + " - " + anInt842 + "," + anInt843);
			method44();
		} catch (IOException _ex) {
			method68();
		} catch (Exception exception) {
			StringBuilder s2 = new StringBuilder("T2 - " + anInt1008 + "," + anInt842 + "," + anInt843 + " - " + anInt1007 + "," + (anInt1034 + aPlayer_1126.anIntArray1500[0]) + "," + (anInt1035 + aPlayer_1126.anIntArray1501[0]) + " - ");
			for (int j15 = 0; (j15 < anInt1007) && (j15 < 50); j15++) {
				s2.append(aBuffer_1083.data[j15]).append(",");
			}
			Signlink.reporterror(s2.toString());
			method44();
			exception.printStackTrace();
		}
		return true;
	}

	public void method146() {
		anInt1265++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		method104();
		if (!aBoolean1160) {
			int i = anInt1184;
			if ((anInt984 / 256) > i) {
				i = anInt984 / 256;
			}
			if (aBooleanArray876[4] && ((anIntArray1203[4] + 128) > i)) {
				i = anIntArray1203[4] + 128;
			}
			int k = (anInt1185 + anInt896) & 0x7ff;
			method144(600 + (i * 3), i, anInt1014, method42(anInt918, aPlayer_1126.anInt1551, aPlayer_1126.anInt1550) - 50, k, anInt1015);
		}
		int j;
		if (!aBoolean1160) {
			j = method120();
		} else {
			j = method121();
		}
		int l = anInt858;
		int i1 = anInt859;
		int j1 = anInt860;
		int k1 = anInt861;
		int l1 = anInt862;
		for (int i2 = 0; i2 < 5; i2++) {
			if (aBooleanArray876[i2]) {
				int j2 = (int) (((Math.random() * (double) ((anIntArray873[i2] * 2) + 1)) - (double) anIntArray873[i2]) + (Math.sin((double) anIntArray1030[i2] * ((double) anIntArray928[i2] / 100D)) * (double) anIntArray1203[i2]));
				if (i2 == 0) {
					anInt858 += j2;
				}
				if (i2 == 1) {
					anInt859 += j2;
				}
				if (i2 == 2) {
					anInt860 += j2;
				}
				if (i2 == 3) {
					anInt862 = (anInt862 + j2) & 0x7ff;
				}
				if (i2 == 4) {
					anInt861 += j2;
					if (anInt861 < 128) {
						anInt861 = 128;
					}
					if (anInt861 > 383) {
						anInt861 = 383;
					}
				}
			}
		}
		int k2 = Draw3D.anInt1481;
		Model.checkHover = true;
		Model.pickedCount = 0;
		Model.mouseX = super.anInt20 - 4;
		Model.mouseY = super.anInt21 - 4;
		Draw2D.clear();
		aGraph_946.method313(anInt858, anInt860, anInt862, anInt859, j, anInt861);
		aGraph_946.method288();
		method34();
		method61();
		method37(k2);
		method112();
		aArea_1165.method238(4, super.aGraphics12, 4);
		anInt858 = l;
		anInt859 = i1;
		anInt860 = j1;
		anInt861 = k1;
		anInt862 = l1;
	}

	public void method147() {
		aBuffer_1192.putOp(130);
		if (anInt1189 != -1) {
			anInt1189 = -1;
			aBoolean1153 = true;
			aBoolean1149 = false;
			aBoolean1103 = true;
		}
		if (anInt1276 != -1) {
			anInt1276 = -1;
			aBoolean1223 = true;
			aBoolean1149 = false;
		}
		anInt857 = -1;
	}

}
