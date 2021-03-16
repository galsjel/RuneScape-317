// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import org.apache.commons.math3.random.ISAACRandom;

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
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.zip.CRC32;

public class Game extends GameShell {

	public static final int[][] anIntArrayArray1003 = {{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193}, {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239}, {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};
	public static final int[] anIntArray1204 = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486};
	public static final BigInteger RSA_MODULUS = new BigInteger("7162900525229798032761816791230527296329313291232324290237849263501208207972894053929065636522363163621000728841182238772712427862772219676577293600221789");
	public static final int[] levelExperience;
	public static final BigInteger RSA_PUBLIC_KEY = new BigInteger("58778699976184461502525193738213253649000149147835990136706041084440742975821");
	public static final String aString1162 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	public static final int[] BITMASK;
	public static final int MAX_PLAYER_COUNT = 2048;
	public static final int LOCAL_PLAYER_INDEX = 2047;
	public static int nodeId = 10;
	public static int portOffset;
	public static boolean members = true;
	public static boolean lowmem;
	public static boolean started;
	public static int drawCycle;
	public static PlayerEntity self;
	public static boolean aBoolean1156;
	public static int loopCycle;
	public static boolean aBoolean1205;

	static {
		levelExperience = new int[99];
		int i = 0;
		for (int j = 0; j < 99; j++) {
			int l = j + 1;
			int i1 = (int) ((double) l + (300D * Math.pow(2D, (double) l / 7D)));
			i += i1;
			levelExperience[j] = i / 4;
		}
		BITMASK = new int[32];
		i = 2;
		for (int k = 0; k < 32; k++) {
			BITMASK[k] = i - 1;
			i += i;
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

	public static void setHighmem() {
		Scene.lowmem = false;
		Draw3D.lowmem = false;
		lowmem = false;
		SceneBuilder.lowmem = false;
		LocType.lowmem = false;
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("RS2 user client - release #" + 317);

		if (args.length != 5) {
			System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
			return;
		}

		nodeId = Integer.parseInt(args[0]);
		portOffset = Integer.parseInt(args[1]);

		if (args[2].equals("lowmem")) {
			setLowmem();
		} else if (args[2].equals("highmem")) {
			setHighmem();
		} else {
			System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
			return;
		}

		if (args[3].equals("free")) {
			members = false;
		} else if (args[3].equals("members")) {
			members = true;
		} else {
			System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
			return;
		}

		Signlink.storeid = Integer.parseInt(args[4]);
		Signlink.startpriv(InetAddress.getLocalHost());

		Game game = new Game();
		game.init(765, 503);
	}

	public static String method110(int i, int j) {
		int d = i - j;
		if (d < -9) {
			return "@red@";
		} else if (d < -6) {
			return "@or3@";
		} else if (d < -3) {
			return "@or2@";
		} else if (d < 0) {
			return "@or1@";
		} else if (d > 9) {
			return "@gre@";
		} else if (d > 6) {
			return "@gr3@";
		} else if (d > 3) {
			return "@gr2@";
		} else if (d > 0) {
			return "@gr1@";
		} else {
			return "@yel@";
		}
	}

	public static void setLowmem() {
		Scene.lowmem = true;
		Draw3D.lowmem = true;
		lowmem = true;
		SceneBuilder.lowmem = true;
		LocType.lowmem = true;
	}

	public final int[] anIntArray1177 = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
	public final int[] anIntArray864 = new int[SkillConstants.anInt733];
	public final int[] anIntArray873 = new int[5];
	public final boolean[] aBooleanArray876 = new boolean[5];
	public final int anInt902 = 0x766654;
	public final int[] anIntArray922 = new int[SkillConstants.anInt733];
	public final long[] aLongArray925 = new long[100];
	public final int anInt927 = 0x332d25;
	public final int[] anIntArray928 = new int[5];
	public final CRC32 aCRC32_930 = new CRC32();
	public final int[] anIntArray942 = new int[100];
	public final String[] aStringArray943 = new String[100];
	public final String[] aStringArray944 = new String[100];
	public final int[] anIntArray965 = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
	public final int[] compassMaskLineOffsets = new int[33];
	public final int[] flameLineOffset = new int[256];
	public final FileStore[] filestores = new FileStore[5];
	public final int anInt975 = 50;
	public final int[] anIntArray976 = new int[anInt975];
	public final int[] anIntArray977 = new int[anInt975];
	public final int[] anIntArray978 = new int[anInt975];
	public final int[] anIntArray979 = new int[anInt975];
	public final int[] anIntArray980 = new int[anInt975];
	public final int[] anIntArray981 = new int[anInt975];
	public final int[] anIntArray982 = new int[anInt975];
	public final String[] aStringArray983 = new String[anInt975];
	public final int[] anIntArray990 = new int[5];
	public final int anInt1002 = 0x23201b;
	public final int[] anIntArray1030 = new int[5];
	public final int[] anIntArray1044 = new int[SkillConstants.anInt733];
	public final int[] anIntArray1045 = new int[2000];
	public final int[] minimapMaskLineOffsets = new int[151];
	public final int[] compassMaskLineLengths = new int[33];
	public final Component aComponent_1059 = new Component();
	public final int anInt1063 = 0x4d4233;
	public final int[] anIntArray1065 = new int[7];
	public final int[] archiveChecksum = new int[9];
	public final String[] aStringArray1127 = new String[5];
	public final boolean[] aBooleanArray1128 = new boolean[5];
	public final int[][][] sceneInstancedChunkBitset = new int[4][13][13];
	public final int[] anIntArray1130 = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	public final int[] anIntArray1203 = new int[5];
	public final int[] anIntArray1207 = new int[50];
	public final Image8[] aImageArray1219 = new Image8[2];
	public final int[] minimapMaskLineLengths = new int[151];
	public final int[] anIntArray1240 = new int[100];
	public final int[] anIntArray1241 = new int[50];
	public final int[] anIntArray1250 = new int[50];
	public int anInt822;
	public long sceneLoadStartTime;
	public int[][] anIntArrayArray825 = new int[104][104];
	public int[] anIntArray826 = new int[200];
	public DoublyLinkedList[][][] aListArrayArrayArray827 = new DoublyLinkedList[4][104][104];
	public int[] flameBuffer3;
	public int[] flameBuffer2;
	public volatile boolean flameActive = false;
	public Socket aSocket832;
	public int anInt833;
	public Buffer aBuffer_834 = new Buffer(new byte[5000]);
	public NPCEntity[] npcs = new NPCEntity[16384];
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
	public int[] flameGradient;
	public int[] flameGradient0;
	public int[] flameGradient1;
	public int[] flameGradient2;
	public int anInt855;
	public int anInt857 = -1;
	public int anInt858;
	public int anInt859;
	public int anInt860;
	public int anInt861;
	public int anInt862;
	public int rights;
	public Image8 aImage_865;
	public Image8 aImage_866;
	public Image8 aImage_867;
	public Image8 aImage_868;
	public Image8 aImage_869;
	public Image24 imageMapmarker0;
	public Image24 imageMapmarker1;
	public boolean aBoolean872 = false;
	public int anInt874 = -1;
	public int anInt878;
	public MouseRecorder aMouseRecorder_879;
	public volatile boolean aBoolean880 = false;
	public String aString881 = "";
	public int selfPlayerId = -1;
	public boolean aBoolean885 = false;
	public int anInt886;
	public String aString887 = "";
	public PlayerEntity[] players = new PlayerEntity[MAX_PLAYER_COUNT];
	public int anInt891;
	public int[] anIntArray892 = new int[MAX_PLAYER_COUNT];
	public int anInt893;
	public int[] anIntArray894 = new int[MAX_PLAYER_COUNT];
	public Buffer[] aBufferArray895 = new Buffer[MAX_PLAYER_COUNT];
	public int anInt896;
	public int anInt899;
	public int anInt900;
	public int[][] anIntArrayArray901 = new int[104][104];
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
	public int currentPlane;
	public boolean aBoolean926 = false;
	public int[][] anIntArrayArray929 = new int[104][104];
	public Image24 aImage_931;
	public Image24 aImage_932;
	public int anInt933;
	public int anInt934;
	public int anInt935;
	public int anInt936;
	public int anInt937;
	public int anInt938;
	public int anInt945;
	public Scene scene;
	public Image8[] imageSideicons = new Image8[13];
	public int anInt948;
	public int anInt949;
	public int anInt950;
	public int anInt951;
	public int anInt952;
	public long aLong953;
	public boolean aBoolean954 = true;
	public long[] aLongArray955 = new long[200];
	public int anInt956 = -1;
	public volatile boolean flameThread = false;
	public int anInt963 = -1;
	public int anInt964 = -1;
	public Image8 imageTitlebox;
	public Image8 imageTitlebutton;
	public int[] anIntArray971 = new int[2000];
	public boolean aBoolean972 = false;
	public int anInt974;
	public int anInt984;
	public int minimapPlane = -1;
	public Image24[] imageHitmarks = new Image24[20];
	public int anInt989;
	public int anInt992;
	public int anInt995;
	public int anInt996;
	public int anInt997;
	public int anInt998;
	public int anInt999;
	public ISAACRandom randomIn;
	public Image24 imageMapedge;
	public String aString1004 = "";
	public int anInt1006;
	public int psize;
	public int ptype;
	public int anInt1009;
	public int anInt1010;
	public int anInt1011;
	public DoublyLinkedList aList_1013 = new DoublyLinkedList();
	public int anInt1014;
	public int anInt1015;
	public int anInt1016;
	public boolean aBoolean1017 = false;
	public int anInt1018 = -1;
	public int minimapState;
	public int anInt1022;
	public int sceneState;
	public Image8 imageScrollbar0;
	public Image8 imageScrollbar1;
	public int anInt1026;
	public Image8 imageBackbase1;
	public Image8 imageBackbase2;
	public Image8 imageBackhmid1;
	public boolean aBoolean1031 = false;
	public Image24[] imageMapfunction = new Image24[100];
	public int sceneBaseTileX;
	public int sceneBaseTileZ;
	public int scenePrevBaseTileX;
	public int scenePrevBaseTileZ;
	public int anInt1038;
	public int anInt1039;
	public int flameGradientCycle0;
	public int flameGradientCycle1;
	public int anInt1042 = -1;
	public int anInt1046;
	public boolean aBoolean1047 = true;
	public int anInt1048;
	public String aString1049;
	public FileArchive archiveTitle;
	public int anInt1054 = -1;
	public int anInt1055;
	public DoublyLinkedList aList_1056 = new DoublyLinkedList();
	public Image8[] imageMapscene = new Image8[100];
	public int anInt1062;
	public int anInt1064;
	public int anInt1066;
	public int anInt1067;
	public OnDemand ondemand;
	public int sceneCenterZoneX;
	public int sceneCenterZoneZ;
	public int activeMapFunctionCount;
	public int[] activeMapFunctionX = new int[1000];
	public int[] activeMapFunctionZ = new int[1000];
	public Image24 imageMapdot0;
	public Image24 imageMapdot1;
	public Image24 imageMapdot2;
	public Image24 imageMapdot3;
	public Image24 imageMapdot4;
	public int anInt1079;
	public boolean aBoolean1080 = false;
	public String[] aStringArray1082 = new String[200];
	public Buffer in = Buffer.create(1);
	public int anInt1084;
	public int anInt1085;
	public int anInt1086;
	public int anInt1087;
	public int anInt1088;
	public int anInt1089;
	public int[] anIntArray1091 = new int[500];
	public int[] anIntArray1092 = new int[500];
	public int[] anIntArray1093 = new int[500];
	public int[] anIntArray1094 = new int[500];
	public Image24[] imageHeadicons = new Image24[20];
	public int anInt1098;
	public int anInt1099;
	public int anInt1100;
	public int anInt1101;
	public int anInt1102;
	public boolean aBoolean1103 = false;
	public int anInt1104;
	public DrawArea imageTitle2;
	public DrawArea imageTitle3;
	public DrawArea imageTitle4;
	public DrawArea imageTitle0;
	public DrawArea imageTitle1;
	public DrawArea imageTitle5;
	public DrawArea imageTitle6;
	public DrawArea imageTitle7;
	public DrawArea imageTitle8;
	public int anInt1120;
	public String aString1121 = "";
	public Image24 imageCompass;
	public DrawArea aArea_1123;
	public DrawArea aArea_1124;
	public DrawArea aArea_1125;
	public int anInt1131;
	public int anInt1133;
	public int anInt1136;
	public int anInt1137;
	public int anInt1138;
	public String aString1139;
	public Image24[] activeMapFunctions = new Image24[1000];
	public boolean withinTutorialIsland = false;
	public Image8 imageRedstone1;
	public Image8 imageRedstone2;
	public Image8 imageRedstone3;
	public Image8 aImage_1146;
	public Image8 aImage_1147;
	public int anInt1148;
	public boolean aBoolean1149 = false;
	public Image24[] imageCrosses = new Image24[8];
	public boolean aBoolean1151 = true;
	public Image8[] imageRunes;
	public boolean aBoolean1153 = false;
	public int anInt1154;
	public boolean ingame = false;
	public boolean aBoolean1158 = false;
	public boolean sceneInstanced = false;
	public boolean aBoolean1160 = false;
	public DrawArea aArea_1163;
	public DrawArea aArea_1164;
	public DrawArea areaViewport;
	public DrawArea aArea_1166;
	public int anInt1167;
	public Connection connection;
	public int anInt1169;
	public int minimapZoom;
	public long aLong1172;
	public String aString1173 = "";
	public String aString1174 = "";
	public boolean errInvalidHost = false;
	public int anInt1178 = -1;
	public DoublyLinkedList listTemporaryLocs = new DoublyLinkedList();
	public int[] anIntArray1180;
	public int[] anIntArray1181;
	public int[] anIntArray1182;
	public byte[][] sceneMapLandData;
	public int anInt1184 = 128;
	public int anInt1185;
	public int anInt1186;
	public int anInt1187;
	public int anInt1189 = -1;
	public int[] flameBuffer0;
	public int[] flameBuffer1;
	public Buffer aBuffer_1192 = Buffer.create(1);
	public int anInt1193;
	public int anInt1195;
	public Image8 imageInvback;
	public Image8 imageMapback;
	public Image8 imageChatback;
	public String[] aStringArray1199 = new String[500];
	public Image24 imageFlamesLeft;
	public Image24 imageFlamesRight;
	public int flameCycle;
	public int anInt1209;
	public int anInt1211 = 78;
	public String aString1212 = "";
	public int anInt1213;
	public int[][][] anIntArrayArrayArray1214;
	public long aLong1215;
	public int anInt1216;
	public long aLong1220;
	public int anInt1221 = 3;
	public int anInt1222;
	public boolean aBoolean1223 = false;
	public int anInt1225;
	public int music;
	public boolean musicFade = true;
	public SceneCollisionMap[] collisions = new SceneCollisionMap[4];
	public boolean aBoolean1233 = false;
	public int[] sceneMapIndex;
	public int[] sceneMapLandFile;
	public int[] sceneMapLocFile;
	public int anInt1237;
	public int anInt1238;
	public boolean aBoolean1242 = false;
	public int anInt1243;
	public int anInt1244;
	public int anInt1245;
	public int anInt1246;
	public byte[][] sceneMapLocData;
	public int anInt1248;
	public int anInt1249;
	public int anInt1251;
	public boolean errAlreadyStarted = false;
	public int anInt1253;
	public boolean aBoolean1255 = false;
	public boolean aBoolean1256 = false;
	public int anInt1257;
	public byte[][][] planeTileFlags;
	public int anInt1259;
	public int anInt1261;
	public int anInt1262;
	public Image24 imageMinimap;
	public int anInt1264;
	public int anInt1265;
	public String aString1266 = "";
	public String aString1267 = "";
	public int anInt1268;
	public int anInt1269;
	public BitmapFont fontPlain11;
	public BitmapFont fontPlain12;
	public BitmapFont fontBold12;
	public BitmapFont fontQuill8;
	public int flameCycle0;
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
		nodeId = Integer.parseInt(getParameter("nodeid"));
		portOffset = Integer.parseInt(getParameter("portoff"));
		String s = getParameter("lowmem");
		if ((s != null) && s.equals("1")) {
			setLowmem();
		} else {
			setHighmem();
		}
		String s1 = getParameter("free");
		members = (s1 == null) || !s1.equals("1");
		initApplet(765, 503);
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
			if (super.frame != null) {
				return new URL("http://127.0.0.1:" + (80 + portOffset));
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
	public void load() throws IOException {
		showProgress(20, "Starting up");

		if (Signlink.sunjava) {
			super.mindel = 5;
		}

		if (started) {
			errAlreadyStarted = true;
			return;
		}

		started = true;

		boolean hostValid = false;
		String host = getHost();

		if (host.endsWith("jagex.com")) {
			hostValid = true;
		}
		if (host.endsWith("runescape.com")) {
			hostValid = true;
		}
		if (host.endsWith("192.168.1.2")) {
			hostValid = true;
		}
		if (host.endsWith("192.168.1.229")) {
			hostValid = true;
		}
		if (host.endsWith("192.168.1.228")) {
			hostValid = true;
		}
		if (host.endsWith("192.168.1.227")) {
			hostValid = true;
		}
		if (host.endsWith("192.168.1.226")) {
			hostValid = true;
		}
		if (host.endsWith("127.0.0.1")) {
			hostValid = true;
		}

		if (!hostValid) {
			errInvalidHost = true;
			return;
		}

		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++) {
				filestores[i] = new FileStore(500000, Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
			}
		}

		try {
			//loadArchiveChecksums();

			archiveTitle = loadArchive(1, "title screen", "title", archiveChecksum[1], 25);
			fontPlain11 = new BitmapFont(archiveTitle, "p11_full", false);
			fontPlain12 = new BitmapFont(archiveTitle, "p12_full", false);
			fontBold12 = new BitmapFont(archiveTitle, "b12_full", false);
			fontQuill8 = new BitmapFont(archiveTitle, "q8_full", true);

			createTitleBackground();
			createTitleImages();

			FileArchive archiveConfig = loadArchive(2, "config", "config", archiveChecksum[2], 30);
			FileArchive archiveInterface = loadArchive(3, "interface", "interface", archiveChecksum[3], 35);
			FileArchive archiveMedia = loadArchive(4, "2d graphics", "media", archiveChecksum[4], 40);
			FileArchive archiveTextures = loadArchive(6, "textures", "textures", archiveChecksum[6], 45);
			FileArchive archiveWordenc = loadArchive(7, "chat system", "wordenc", archiveChecksum[7], 50);
			FileArchive archiveSounds = loadArchive(8, "sound effects", "sounds", archiveChecksum[8], 55);

			planeTileFlags = new byte[4][104][104];
			anIntArrayArrayArray1214 = new int[4][105][105];
			scene = new Scene(104, 104, anIntArrayArrayArray1214, 4);

			for (int j = 0; j < 4; j++) {
				collisions[j] = new SceneCollisionMap(104, 104);
			}

			imageMinimap = new Image24(512, 512);

			FileArchive archiveVersionlist = loadArchive(5, "update list", "versionlist", archiveChecksum[5], 60);

			showProgress(60, "Connecting to update server");

			ondemand = new OnDemand();
			ondemand.load(archiveVersionlist, this);

			SeqFrame.init(ondemand.getSeqFrameCount());
			Model.init(ondemand.getFileCount(0), ondemand);

			if (!lowmem) {
				music = 0;

				try {
					music = Integer.parseInt(getParameter("music"));
				} catch (Exception ignored) {
				}

				musicFade = true;
				ondemand.request(2, music);

				while (ondemand.remaining() > 0) {
					handleOnDemandRequests();

					try {
						Thread.sleep(100L);
					} catch (Exception ignored) {
					}

					if (ondemand.anInt1349 > 3) {
						method28("ondemand");
						return;
					}
				}
			}

			showProgress(65, "Requesting animations");

			int total = ondemand.getFileCount(1);

			for (int i = 0; i < total; i++) {
				ondemand.request(1, i);
			}

			while (ondemand.remaining() > 0) {
				int done = total - ondemand.remaining();

				if (done > 0) {
					showProgress(65, "Loading animations - " + (done * 100) / total + "%");
				}

				handleOnDemandRequests();

				try {
					Thread.sleep(100L);
				} catch (Exception ignored) {
				}

				if (ondemand.anInt1349 > 3) {
					method28("ondemand");
					return;
				}
			}

			showProgress(70, "Requesting models");

			total = ondemand.getFileCount(0);

			for (int i = 0; i < total; i++) {
				int index = ondemand.getModelIndex(i);

				if ((index & 1) != 0) {
					ondemand.request(0, i);
				}
			}

			total = ondemand.remaining();

			while (ondemand.remaining() > 0) {
				int done = total - ondemand.remaining();

				if (done > 0) {
					showProgress(70, "Loading models - " + (done * 100) / total + "%");
				}

				handleOnDemandRequests();

				try {
					Thread.sleep(100L);
				} catch (Exception ignored) {
				}
			}

			if (filestores[0] != null) {
				showProgress(75, "Requesting maps");

				ondemand.request(3, ondemand.getMapFile(0, 47, 48));
				ondemand.request(3, ondemand.getMapFile(1, 47, 48));
				ondemand.request(3, ondemand.getMapFile(0, 48, 48));
				ondemand.request(3, ondemand.getMapFile(1, 48, 48));
				ondemand.request(3, ondemand.getMapFile(0, 49, 48));
				ondemand.request(3, ondemand.getMapFile(1, 49, 48));
				ondemand.request(3, ondemand.getMapFile(0, 47, 47));
				ondemand.request(3, ondemand.getMapFile(1, 47, 47));
				ondemand.request(3, ondemand.getMapFile(0, 48, 47));
				ondemand.request(3, ondemand.getMapFile(1, 48, 47));
				ondemand.request(3, ondemand.getMapFile(0, 48, 148));
				ondemand.request(3, ondemand.getMapFile(1, 48, 148));

				total = ondemand.remaining();

				while (ondemand.remaining() > 0) {
					int done = total - ondemand.remaining();

					if (done > 0) {
						showProgress(75, "Loading maps - " + (done * 100) / total + "%");
					}

					handleOnDemandRequests();

					try {
						Thread.sleep(100L);
					} catch (Exception ignored) {
					}
				}
			}

			total = ondemand.getFileCount(0);
			for (int k2 = 0; k2 < total; k2++) {
				int l2 = ondemand.getModelIndex(k2);
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
					ondemand.prefetch(byte0, 0, k2);
				}
			}
			ondemand.prefetchMaps(members);
			if (!lowmem) {
				int l = ondemand.getFileCount(2);
				for (int i3 = 1; i3 < l; i3++) {
					if (ondemand.method569(i3)) {
						ondemand.prefetch((byte) 1, 2, i3);
					}
				}
			}
			showProgress(80, "Unpacking media");
			imageInvback = new Image8(archiveMedia, "invback", 0);
			imageChatback = new Image8(archiveMedia, "chatback", 0);
			imageMapback = new Image8(archiveMedia, "mapback", 0);
			imageBackbase1 = new Image8(archiveMedia, "backbase1", 0);
			imageBackbase2 = new Image8(archiveMedia, "backbase2", 0);
			imageBackhmid1 = new Image8(archiveMedia, "backhmid1", 0);
			for (int i = 0; i < 13; i++) {
				imageSideicons[i] = new Image8(archiveMedia, "sideicons", i);
			}
			imageCompass = new Image24(archiveMedia, "compass", 0);
			imageMapedge = new Image24(archiveMedia, "mapedge", 0);
			imageMapedge.method345();
			try {
				for (int k3 = 0; k3 < 100; k3++) {
					imageMapscene[k3] = new Image8(archiveMedia, "mapscene", k3);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int l3 = 0; l3 < 100; l3++) {
					imageMapfunction[l3] = new Image24(archiveMedia, "mapfunction", l3);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int i4 = 0; i4 < 20; i4++) {
					imageHitmarks[i4] = new Image24(archiveMedia, "hitmarks", i4);
				}
			} catch (Exception ignored) {
			}
			try {
				for (int j4 = 0; j4 < 20; j4++) {
					imageHeadicons[j4] = new Image24(archiveMedia, "headicons", j4);
				}
			} catch (Exception ignored) {
			}
			imageMapmarker0 = new Image24(archiveMedia, "mapmarker", 0);
			imageMapmarker1 = new Image24(archiveMedia, "mapmarker", 1);
			for (int k4 = 0; k4 < 8; k4++) {
				imageCrosses[k4] = new Image24(archiveMedia, "cross", k4);
			}
			imageMapdot0 = new Image24(archiveMedia, "mapdots", 0);
			imageMapdot1 = new Image24(archiveMedia, "mapdots", 1);
			imageMapdot2 = new Image24(archiveMedia, "mapdots", 2);
			imageMapdot3 = new Image24(archiveMedia, "mapdots", 3);
			imageMapdot4 = new Image24(archiveMedia, "mapdots", 4);
			imageScrollbar0 = new Image8(archiveMedia, "scrollbar", 0);
			imageScrollbar1 = new Image8(archiveMedia, "scrollbar", 1);
			imageRedstone1 = new Image8(archiveMedia, "redstone1", 0);
			imageRedstone2 = new Image8(archiveMedia, "redstone2", 0);
			imageRedstone3 = new Image8(archiveMedia, "redstone3", 0);
			aImage_1146 = new Image8(archiveMedia, "redstone1", 0);
			aImage_1146.method358();
			aImage_1147 = new Image8(archiveMedia, "redstone2", 0);
			aImage_1147.method358();
			aImage_865 = new Image8(archiveMedia, "redstone1", 0);
			aImage_865.method359();
			aImage_866 = new Image8(archiveMedia, "redstone2", 0);
			aImage_866.method359();
			aImage_867 = new Image8(archiveMedia, "redstone3", 0);
			aImage_867.method359();
			aImage_868 = new Image8(archiveMedia, "redstone1", 0);
			aImage_868.method358();
			aImage_868.method359();
			aImage_869 = new Image8(archiveMedia, "redstone2", 0);
			aImage_869.method358();
			aImage_869.method359();
			for (int l4 = 0; l4 < 2; l4++) {
				aImageArray1219[l4] = new Image8(archiveMedia, "mod_icons", l4);
			}
			Image24 image = new Image24(archiveMedia, "backleft1", 0);
			aArea_903 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backleft2", 0);
			aArea_904 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backright1", 0);
			aArea_905 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backright2", 0);
			aArea_906 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backtop1", 0);
			aArea_907 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backvmid1", 0);
			aArea_908 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backvmid2", 0);
			aArea_909 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backvmid3", 0);
			aArea_910 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			image = new Image24(archiveMedia, "backhmid2", 0);
			aArea_911 = new DrawArea(image.width, image.height, getComponent());
			image.blitOpaque(0, 0);
			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;
			for (int i6 = 0; i6 < 100; i6++) {
				if (imageMapfunction[i6] != null) {
					imageMapfunction[i6].method344(i5 + l5, j5 + l5, k5 + l5);
				}
				if (imageMapscene[i6] != null) {
					imageMapscene[i6].method360(i5 + l5, j5 + l5, k5 + l5);
				}
			}
			showProgress(83, "Unpacking textures");
			Draw3D.unpackTextures(archiveTextures);
			Draw3D.setBrightness(0.80000000000000004D);
			Draw3D.initPool(20);
			showProgress(86, "Unpacking config");
			SeqType.unpack(archiveConfig);
			LocType.unpack(archiveConfig);
			FloType.unpack(archiveConfig);
			ObjType.unpack(archiveConfig);
			NPCType.unpack(archiveConfig);
			IDKType.unpack(archiveConfig);
			SpotAnimType.unpack(archiveConfig);
			VarpType.unpack(archiveConfig);
			VarbitType.unpack(archiveConfig);
			ObjType.aBoolean182 = members;
			if (!lowmem) {
				showProgress(90, "Unpacking sounds");
				byte[] abyte0 = archiveSounds.read("sounds.dat");
				Buffer buffer = new Buffer(abyte0);
				SoundTrack.method240(buffer);
			}
			showProgress(95, "Unpacking interfaces");
			BitmapFont[] aclass30_sub2_sub1_sub4 = {fontPlain11, fontPlain12, fontBold12, fontQuill8};
			Component.unpack(archiveInterface, aclass30_sub2_sub1_sub4, archiveMedia);
			showProgress(100, "Preparing game engine");
			for (int j6 = 0; j6 < 33; j6++) {
				int k6 = 999;
				int i7 = 0;
				for (int k7 = 0; k7 < 34; k7++) {
					if (imageMapback.pixels[k7 + (j6 * imageMapback.width)] == 0) {
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
				compassMaskLineOffsets[j6] = k6;
				compassMaskLineLengths[j6] = i7 - k6;
			}
			for (int l6 = 5; l6 < 156; l6++) {
				int j7 = 999;
				int l7 = 0;
				for (int j8 = 25; j8 < 172; j8++) {
					if ((imageMapback.pixels[j8 + (l6 * imageMapback.width)] == 0) && ((j8 > 34) || (l6 > 34))) {
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
				minimapMaskLineOffsets[l6 - 5] = j7 - 25;
				minimapMaskLineLengths[l6 - 5] = l7 - j7;
			}
			Draw3D.init3D(479, 96);
			anIntArray1180 = Draw3D.lineOffset;
			Draw3D.init3D(190, 261);
			anIntArray1181 = Draw3D.lineOffset;
			Draw3D.init3D(512, 334);
			anIntArray1182 = Draw3D.lineOffset;
			int[] ai = new int[9];
			for (int i8 = 0; i8 < 9; i8++) {
				int k8 = 128 + (i8 * 32) + 15;
				int l8 = 600 + (k8 * 3);
				int i9 = Draw3D.sin[k8];
				ai[i8] = (l8 * i9) >> 16;
			}
			Scene.method310(500, 800, 512, 334, ai);
			Censor.method487(archiveWordenc);
			aMouseRecorder_879 = new MouseRecorder(this);
			startThread(aMouseRecorder_879, 10);
			LocEntity.game = this;
			LocType.game = this;
			NPCType.game = this;
			return;
		} catch (Exception exception) {
			Signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
			exception.printStackTrace();
		}
		aBoolean926 = true;
	}

	@Override
	public void update() {
		if (errAlreadyStarted || aBoolean926 || errInvalidHost) {
			return;
		}
		loopCycle++;
		if (!ingame) {
			method140();
		} else {
			method62();
		}
		handleOnDemandRequests();
	}

	@Override
	public void unload() {
		Signlink.reporterror = false;
		try {
			if (connection != null) {
				connection.method267();
			}
		} catch (Exception ignored) {
		}
		connection = null;
		method15();
		if (aMouseRecorder_879 != null) {
			aMouseRecorder_879.aBoolean808 = false;
			aMouseRecorder_879 = null;
		}
		if (ondemand != null) {
			ondemand.stop();
			ondemand = null;
		}
		aBuffer_834 = null;
		aBuffer_1192 = null;
		aBuffer_847 = null;
		in = null;
		sceneMapIndex = null;
		sceneMapLandData = null;
		sceneMapLocData = null;
		sceneMapLandFile = null;
		sceneMapLocFile = null;
		anIntArrayArrayArray1214 = null;
		planeTileFlags = null;
		scene = null;
		collisions = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		anIntArray1280 = null;
		anIntArray1281 = null;
		aByteArray912 = null;
		aArea_1163 = null;
		aArea_1164 = null;
		areaViewport = null;
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
		imageInvback = null;
		imageMapback = null;
		imageChatback = null;
		imageBackbase1 = null;
		imageBackbase2 = null;
		imageBackhmid1 = null;
		imageSideicons = null;
		imageRedstone1 = null;
		imageRedstone2 = null;
		imageRedstone3 = null;
		aImage_1146 = null;
		aImage_1147 = null;
		aImage_865 = null;
		aImage_866 = null;
		aImage_867 = null;
		aImage_868 = null;
		aImage_869 = null;
		imageCompass = null;
		imageHitmarks = null;
		imageHeadicons = null;
		imageCrosses = null;
		imageMapdot0 = null;
		imageMapdot1 = null;
		imageMapdot2 = null;
		imageMapdot3 = null;
		imageMapdot4 = null;
		imageMapscene = null;
		imageMapfunction = null;
		anIntArrayArray929 = null;
		players = null;
		anIntArray892 = null;
		anIntArray894 = null;
		aBufferArray895 = null;
		anIntArray840 = null;
		npcs = null;
		anIntArray837 = null;
		aListArrayArrayArray827 = null;
		listTemporaryLocs = null;
		aList_1013 = null;
		aList_1056 = null;
		anIntArray1091 = null;
		anIntArray1092 = null;
		anIntArray1093 = null;
		anIntArray1094 = null;
		aStringArray1199 = null;
		anIntArray971 = null;
		activeMapFunctionX = null;
		activeMapFunctionZ = null;
		activeMapFunctions = null;
		imageMinimap = null;
		aStringArray1082 = null;
		aLongArray955 = null;
		anIntArray826 = null;
		imageTitle0 = null;
		imageTitle1 = null;
		imageTitle2 = null;
		imageTitle3 = null;
		imageTitle4 = null;
		imageTitle5 = null;
		imageTitle6 = null;
		imageTitle7 = null;
		imageTitle8 = null;
		method118();
		LocType.unload();
		NPCType.unload();
		ObjType.unload();
		FloType.instances = null;
		IDKType.instances = null;
		Component.instances = null;
		SeqType.instances = null;
		SpotAnimType.instances = null;
		SpotAnimType.modelCache = null;
		VarpType.instances = null;
		PlayerEntity.modelCache = null;
		Draw3D.unload();
		Scene.unload();
		Model.unload();
		SeqFrame.unload();
		System.gc();
	}

	@Override
	public void draw() throws IOException {
		if (errAlreadyStarted || aBoolean926 || errInvalidHost) {
			method94();
			return;
		}
		drawCycle++;
		if (!ingame) {
			method135(false);
		} else {
			method102();
		}
		anInt1213 = 0;
	}

	@Override
	public void refresh() {
		aBoolean1255 = true;
	}

	@Override
	public java.awt.Component getComponent() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp;
		}
		if (super.frame != null) {
			return super.frame;
		} else {
			return this;
		}
	}

	@Override
	public void startThread(Runnable runnable, int priority) {
		if (priority > 10) {
			priority = 10;
		}
		if (Signlink.mainapp != null) {
			Signlink.startthread(runnable, priority);
		} else {
			super.startThread(runnable, priority);
		}
	}

	@Override
	public void showProgress(int percent, String message) throws IOException {
		anInt1079 = percent;
		aString1049 = message;
		method64();
		if (archiveTitle == null) {
			super.showProgress(percent, message);
			return;
		}
		imageTitle4.bind();
		char c = '\u0168';
		char c1 = '\310';
		byte byte1 = 20;
		fontBold12.drawStringCenter("RuneScape is loading - please wait...", c / 2, (c1 / 2) - 26 - byte1, 0xffffff);
		int j = (c1 / 2) - 18 - byte1;
		Draw2D.drawRect((c / 2) - 152, j, 304, 34, 0x8c1111);
		Draw2D.drawRect((c / 2) - 151, j + 1, 302, 32, 0);
		Draw2D.fillRect((c / 2) - 150, j + 2, percent * 3, 30, 0x8c1111);
		Draw2D.fillRect(((c / 2) - 150) + (percent * 3), j + 2, 300 - (percent * 3), 30, 0);
		fontBold12.drawStringCenter(message, c / 2, ((c1 / 2) + 5) - byte1, 0xffffff);
		imageTitle4.draw(super.graphics, 202, 171);
		if (aBoolean1255) {
			aBoolean1255 = false;
			if (!flameActive) {
				imageTitle0.draw(super.graphics, 0, 0);
				imageTitle1.draw(super.graphics, 637, 0);
			}
			imageTitle2.draw(super.graphics, 128, 0);
			imageTitle3.draw(super.graphics, 202, 371);
			imageTitle5.draw(super.graphics, 0, 265);
			imageTitle6.draw(super.graphics, 562, 265);
			imageTitle7.draw(super.graphics, 128, 171);
			imageTitle8.draw(super.graphics, 562, 171);
		}
	}

	public void method15() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	public void loadArchiveChecksums() throws IOException {
		int j = 5;
		archiveChecksum[8] = 0;
		int k = 0;
		while (archiveChecksum[8] == 0) {
			String s = "Unknown problem";
			showProgress(20, "Connecting to web server");
			try {
				DataInputStream in = openURL("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
				Buffer buffer = new Buffer(new byte[40]);
				in.readFully(buffer.data, 0, 40);
				in.close();
				for (int i1 = 0; i1 < 9; i1++) {
					archiveChecksum[i1] = buffer.get4();
				}
				int j1 = buffer.get4();
				int k1 = 1234;
				for (int l1 = 0; l1 < 9; l1++) {
					k1 = (k1 << 1) + archiveChecksum[l1];
				}
				if (j1 != k1) {
					s = "checksum problem";
					archiveChecksum[8] = 0;
				}
			} catch (EOFException _ex) {
				s = "EOF problem";
				archiveChecksum[8] = 0;
			} catch (IOException _ex) {
				s = "connection problem";
				archiveChecksum[8] = 0;
			} catch (Exception _ex) {
				s = "logic problem";
				archiveChecksum[8] = 0;
				if (!Signlink.reporterror) {
					return;
				}
			}
			if (archiveChecksum[8] == 0) {
				k++;
				for (int l = j; l > 0; l--) {
					if (k >= 10) {
						showProgress(10, "Game updated - please reload page");
						l = 10;
					} else {
						showProgress(10, s + " - Will retry in " + l + " secs.");
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
		aArea_1166.bind();
		Draw3D.lineOffset = anIntArray1180;
		imageChatback.blit(0, 0);

		if (aBoolean1256) {
			fontBold12.drawStringCenter(aString1121, 239, 40, 0);
			fontBold12.drawStringCenter(aString1212 + "*", 239, 60, 128);
		} else if (anInt1225 == 1) {
			fontBold12.drawStringCenter("Enter amount:", 239, 40, 0);
			fontBold12.drawStringCenter(aString1004 + "*", 239, 60, 128);
		} else if (anInt1225 == 2) {
			fontBold12.drawStringCenter("Enter name:", 239, 40, 0);
			fontBold12.drawStringCenter(aString1004 + "*", 239, 60, 128);
		} else if (aString844 != null) {
			fontBold12.drawStringCenter(aString844, 239, 40, 0);
			fontBold12.drawStringCenter("Click to continue", 239, 60, 128);
		} else if (anInt1276 != -1) {
			method105(0, 0, Component.instances[anInt1276], 0);
		} else if (anInt1042 != -1) {
			method105(0, 0, Component.instances[anInt1042], 0);
		} else {
			BitmapFont font = fontPlain12;
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
							font.drawString(aStringArray944[k], 4, i1, 0);
						}
						j++;
					}
					if (((l == 1) || (l == 2)) && ((l == 1) || (anInt1287 == 0) || ((anInt1287 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							int j1 = 4;
							if (byte0 == 1) {
								aImageArray1219[0].blit(j1, i1 - 12);
								j1 += 14;
							}
							if (byte0 == 2) {
								aImageArray1219[1].blit(j1, i1 - 12);
								j1 += 14;
							}
							font.drawString(s1 + ":", j1, i1, 0);
							j1 += font.stringWidthTaggable(s1) + 8;
							font.drawString(aStringArray944[k], j1, i1, 255);
						}
						j++;
					}
					if (((l == 3) || (l == 7)) && (anInt1195 == 0) && ((l == 7) || (anInt845 == 0) || ((anInt845 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							int k1 = 4;
							font.drawString("From", k1, i1, 0);
							k1 += font.stringWidthTaggable("From ");
							if (byte0 == 1) {
								aImageArray1219[0].blit(k1, i1 - 12);
								k1 += 14;
							}
							if (byte0 == 2) {
								aImageArray1219[1].blit(k1, i1 - 12);
								k1 += 14;
							}
							font.drawString(s1 + ":", k1, i1, 0);
							k1 += font.stringWidthTaggable(s1) + 8;
							font.drawString(aStringArray944[k], k1, i1, 0x800000);
						}
						j++;
					}
					if ((l == 4) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							font.drawString(s1 + " " + aStringArray944[k], 4, i1, 0x800080);
						}
						j++;
					}
					if ((l == 5) && (anInt1195 == 0) && (anInt845 < 2)) {
						if ((i1 > 0) && (i1 < 110)) {
							font.drawString(aStringArray944[k], 4, i1, 0x800000);
						}
						j++;
					}
					if ((l == 6) && (anInt1195 == 0) && (anInt845 < 2)) {
						if ((i1 > 0) && (i1 < 110)) {
							font.drawString("To " + s1 + ":", 4, i1, 0);
							font.drawString(aStringArray944[k], 12 + font.stringWidthTaggable("To " + s1), i1, 0x800000);
						}
						j++;
					}
					if ((l == 8) && ((anInt1248 == 0) || ((anInt1248 == 1) && method109(s1)))) {
						if ((i1 > 0) && (i1 < 110)) {
							font.drawString(s1 + " " + aStringArray944[k], 4, i1, 0x7e3200);
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
			if ((self != null) && (self.aString1703 != null)) {
				s = self.aString1703;
			} else {
				s = StringUtil.formatName(aString1173);
			}
			font.drawString(s + ":", 4, 90, 0);
			font.drawString(aString887 + "*", 6 + font.stringWidthTaggable(s + ": "), 90, 255);
			Draw2D.drawLineX(0, 77, 479, 0);
		}
		if (aBoolean885 && (anInt948 == 2)) {
			method40();
		}
		aArea_1166.draw(super.graphics, 17, 357);
		areaViewport.bind();
		Draw3D.lineOffset = anIntArray1182;
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
		int j = super.mousePressButton;
		if ((anInt1136 == 1) && (super.mousePressX >= 516) && (super.mousePressY >= 160) && (super.mousePressX <= 765) && (super.mousePressY <= 205)) {
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
				int k2 = super.mousePressX;
				int l2 = super.mousePressY;
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
					Component component = Component.instances[j2];
					if (component.aBoolean259 || component.aBoolean235) {
						aBoolean1242 = false;
						anInt989 = 0;
						anInt1084 = j2;
						anInt1085 = l1;
						anInt1086 = 2;
						anInt1087 = super.mousePressX;
						anInt1088 = super.mousePressY;
						if (Component.instances[j2].anInt236 == anInt857) {
							anInt1086 = 1;
						}
						if (Component.instances[j2].anInt236 == anInt1276) {
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

	public void midisave(boolean fade, byte[] data) {
		Signlink.midifade = fade ? 1 : 0;
		Signlink.midisave(data, data.length);
	}

	public void method22() {
		try {
			minimapPlane = -1;
			aList_1056.clear();
			aList_1013.clear();
			Draw3D.clearTexels();
			clearCaches();
			scene.clear();
			System.gc();
			for (int i = 0; i < 4; i++) {
				collisions[i].method210();
			}
			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++) {
						planeTileFlags[l][k1][j2] = 0;
					}
				}
			}
			SceneBuilder sceneBuilder = new SceneBuilder(planeTileFlags, 104, 104, anIntArrayArrayArray1214);
			int k2 = sceneMapLandData.length;
			aBuffer_1192.putOp(0);
			if (!sceneInstanced) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = ((sceneMapIndex[i3] >> 8) * 64) - sceneBaseTileX;
					int k5 = ((sceneMapIndex[i3] & 0xff) * 64) - sceneBaseTileZ;
					byte[] abyte0 = sceneMapLandData[i3];
					if (abyte0 != null) {
						sceneBuilder.method180(abyte0, k5, i4, (sceneCenterZoneX - 6) * 8, (sceneCenterZoneZ - 6) * 8, collisions);
					}
				}
				for (int j4 = 0; j4 < k2; j4++) {
					int l5 = ((sceneMapIndex[j4] >> 8) * 64) - sceneBaseTileX;
					int k7 = ((sceneMapIndex[j4] & 0xff) * 64) - sceneBaseTileZ;
					byte[] abyte2 = sceneMapLandData[j4];
					if ((abyte2 == null) && (sceneCenterZoneZ < 800)) {
						sceneBuilder.method174(k7, 64, 64, l5);
					}
				}
				aBuffer_1192.putOp(0);
				for (int i6 = 0; i6 < k2; i6++) {
					byte[] abyte1 = sceneMapLocData[i6];
					if (abyte1 != null) {
						int l8 = ((sceneMapIndex[i6] >> 8) * 64) - sceneBaseTileX;
						int k9 = ((sceneMapIndex[i6] & 0xff) * 64) - sceneBaseTileZ;
						sceneBuilder.method190(l8, collisions, k9, scene, abyte1);
					}
				}
			}
			if (sceneInstanced) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = sceneInstancedChunkBitset[j3][k4][j6];
							if (l7 != -1) {
								int i9 = (l7 >> 24) & 3;
								int l9 = (l7 >> 1) & 3;
								int j10 = (l7 >> 14) & 0x3ff;
								int l10 = (l7 >> 3) & 0x7ff;
								int j11 = ((j10 / 8) << 8) + (l10 / 8);
								for (int l11 = 0; l11 < sceneMapIndex.length; l11++) {
									if ((sceneMapIndex[l11] != j11) || (sceneMapLandData[l11] == null)) {
										continue;
									}
									sceneBuilder.method179(i9, l9, collisions, k4 * 8, (j10 & 7) * 8, sceneMapLandData[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}
							}
						}
					}
				}
				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = sceneInstancedChunkBitset[0][l4][k6];
						if (i8 == -1) {
							sceneBuilder.method174(k6 * 8, 8, 8, l4 * 8);
						}
					}
				}
				aBuffer_1192.putOp(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = sceneInstancedChunkBitset[l6][j8][j9];
							if (i10 != -1) {
								int k10 = (i10 >> 24) & 3;
								int i11 = (i10 >> 1) & 3;
								int k11 = (i10 >> 14) & 0x3ff;
								int i12 = (i10 >> 3) & 0x7ff;
								int j12 = ((k11 / 8) << 8) + (i12 / 8);
								for (int k12 = 0; k12 < sceneMapIndex.length; k12++) {
									if ((sceneMapIndex[k12] != j12) || (sceneMapLocData[k12] == null)) {
										continue;
									}
									sceneBuilder.method183(collisions, scene, k10, j8 * 8, (i12 & 7) * 8, l6, sceneMapLocData[k12], (k11 & 7) * 8, i11, j9 * 8);
									break;
								}
							}
						}
					}
				}
			}
			aBuffer_1192.putOp(0);
			sceneBuilder.method171(collisions, scene);
			areaViewport.bind();
			aBuffer_1192.putOp(0);
			int k3 = SceneBuilder.anInt145;
			if (k3 > currentPlane) {
				k3 = currentPlane;
			}
			if (lowmem) {
				scene.setMinPlane(SceneBuilder.anInt145);
			} else {
				scene.setMinPlane(0);
			}
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++) {
					method25(i5, i7);
				}
			}
			method63();
		} catch (Exception ignored) {
		}
		LocType.aCache_785.clear();
		if (super.frame != null) {
			aBuffer_1192.putOp(210);
			aBuffer_1192.put4(0x3f008edd);
		}
		if (lowmem && (Signlink.cache_dat != null)) {
			int j = ondemand.getFileCount(0);
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = ondemand.getModelIndex(i1);
				if ((l1 & 0x79) == 0) {
					Model.unload(i1);
				}
			}
		}

		System.gc();
		Draw3D.initPool(20);
		ondemand.method566();

		int minMapX = ((sceneCenterZoneX - 6) / 8) - 1;
		int maxMapX = ((sceneCenterZoneX + 6) / 8) + 1;
		int minMapZ = ((sceneCenterZoneZ - 6) / 8) - 1;
		int maxMapZ = ((sceneCenterZoneZ + 6) / 8) + 1;

		if (withinTutorialIsland) {
			minMapX = 49;
			maxMapX = 50;
			minMapZ = 49;
			maxMapZ = 50;
		}

		for (int mx = minMapX; mx <= maxMapX; mx++) {
			for (int mz = minMapZ; mz <= maxMapZ; mz++) {
				if ((mx == minMapX) || (mx == maxMapX) || (mz == minMapZ) || (mz == maxMapZ)) {
					int j7 = ondemand.getMapFile(0, mx, mz);
					if (j7 != -1) {
						ondemand.method560(j7, 3);
					}
					int k8 = ondemand.getMapFile(1, mx, mz);
					if (k8 != -1) {
						ondemand.method560(k8, 3);
					}
				}
			}
		}
	}

	public void clearCaches() {
		LocType.aCache_785.clear();
		LocType.aCache_780.clear();
		NPCType.modelCache.clear();
		ObjType.modelCache.clear();
		ObjType.iconCache.clear();
		PlayerEntity.modelCache.clear();
		SpotAnimType.modelCache.clear();
	}

	public void createMinimap(int plane) {
		int[] pixels = imageMinimap.pixels;

		Arrays.fill(pixels,0);

		for (int z = 1; z < 103; z++) {
			int offset = 24628 + ((103 - z) * 512 * 4);

			for (int x = 1; x < 103; x++) {
				if ((planeTileFlags[plane][x][z] & 0x18) == 0) {
					scene.drawMinimapTile(pixels, offset, 512, plane, x, z);
				}

				if ((plane < 3) && ((planeTileFlags[plane + 1][x][z] & 8) != 0)) {
					scene.drawMinimapTile(pixels, offset, 512, plane + 1, x, z);
				}

				offset += 4;
			}
		}

		int j1 = (((238 + (int) (Math.random() * 20D)) - 10) << 16) + (((238 + (int) (Math.random() * 20D)) - 10) << 8) + ((238 + (int) (Math.random() * 20D)) - 10);
		int l1 = ((238 + (int) (Math.random() * 20D)) - 10) << 16;

		imageMinimap.bind();

		for (int z = 1; z < 103; z++) {
			for (int x = 1; x < 103; x++) {
				if ((planeTileFlags[plane][x][z] & 0x18) == 0) {
					drawMinimapLoc(z, j1, x, l1, plane);
				}

				if ((plane < 3) && ((planeTileFlags[plane + 1][x][z] & 8) != 0)) {
					drawMinimapLoc(z, j1, x, l1, plane + 1);
				}
			}
		}

		areaViewport.bind();
		activeMapFunctionCount = 0;

		for (int x = 0; x < 104; x++) {
			for (int z = 0; z < 104; z++) {
				int bitset = scene.getGroundDecorationBitset(currentPlane, x, z);

				if (bitset == 0) {
					continue;
				}

				bitset = (bitset >> 14) & 0x7fff;

				int j3 = LocType.method572(bitset).anInt746;

				if (j3 < 0) {
					continue;
				}

				int stx = x;
				int stz = z;

				if ((j3 != 22) && (j3 != 29) && (j3 != 34) && (j3 != 36) && (j3 != 46) && (j3 != 47) && (j3 != 48)) {
					byte byte0 = 104;
					byte byte1 = 104;
					int[][] flags = collisions[currentPlane].flags;
					for (int i4 = 0; i4 < 10; i4++) {
						int j4 = (int) (Math.random() * 4D);
						if ((j4 == 0) && (stx > 0) && (stx > (x - 3)) && ((flags[stx - 1][stz] & 0x1280108) == 0)) {
							stx--;
						}
						if ((j4 == 1) && (stx < (byte0 - 1)) && (stx < (x + 3)) && ((flags[stx + 1][stz] & 0x1280180) == 0)) {
							stx++;
						}
						if ((j4 == 2) && (stz > 0) && (stz > (z - 3)) && ((flags[stx][stz - 1] & 0x1280102) == 0)) {
							stz--;
						}
						if ((j4 == 3) && (stz < (byte1 - 1)) && (stz < (z + 3)) && ((flags[stx][stz + 1] & 0x1280120) == 0)) {
							stz++;
						}
					}
				}
				activeMapFunctions[activeMapFunctionCount] = imageMapfunction[j3];
				activeMapFunctionX[activeMapFunctionCount] = stx;
				activeMapFunctionZ[activeMapFunctionCount] = stz;
				activeMapFunctionCount++;
			}
		}
	}

	public void method25(int x, int z) {
		DoublyLinkedList list = aListArrayArrayArray827[currentPlane][x][z];
		if (list == null) {
			scene.removeObjStack(currentPlane, x, z);
			return;
		}

		int k = -99999999;

		ObjStackEntity stack0 = null;
		ObjStackEntity stack1 = null;
		ObjStackEntity stack2 = null;

		for (ObjStackEntity stack = (ObjStackEntity) list.peekFront(); stack != null; stack = (ObjStackEntity) list.prev()) {
			ObjType type = ObjType.method198(stack.anInt1558);

			int l = type.anInt155;

			if (type.aBoolean176) {
				l *= stack.anInt1559 + 1;
			}

			if (l > k) {
				k = l;
				stack0 = stack;
			}
		}

		list.pushFront(stack0);

		for (ObjStackEntity stack = (ObjStackEntity) list.peekFront(); stack != null; stack = (ObjStackEntity) list.prev()) {
			if ((stack.anInt1558 != stack0.anInt1558) && (stack1 == null)) {
				stack1 = stack;
			}
			if ((stack.anInt1558 != stack0.anInt1558) && (stack.anInt1558 != stack1.anInt1558) && (stack2 == null)) {
				stack2 = stack;
			}
		}

		int bitset = x + (z << 7) + 0x60000000;
		scene.addObjStack(stack0, stack1, stack2, currentPlane, x, z, getHeightmapY(currentPlane, (x * 128) + 64, (z * 128) + 64), bitset);
	}

	public void method26(boolean flag) {
		for (int j = 0; j < anInt836; j++) {
			NPCEntity npc = npcs[anIntArray837[j]];
			int k = 0x20000000 + (anIntArray837[j] << 14);
			if ((npc == null) || !npc.method449() || (npc.type.aBoolean93 != flag)) {
				continue;
			}
			int l = npc.x >> 7;
			int i1 = npc.z >> 7;
			if ((l < 0) || (l >= 104) || (i1 < 0) || (i1 >= 104)) {
				continue;
			}
			if ((npc.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
				if (anIntArrayArray929[l][i1] == anInt1265) {
					continue;
				}
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if (!npc.type.aBoolean84) {
				k += 0x80000000;
			}
			scene.addTemporary(npc, currentPlane, npc.x, npc.z, getHeightmapY(currentPlane, npc.x, npc.z), npc.yaw, k, npc.aBoolean1541, ((npc.size - 1) * 64) + 60);
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
			Component component_1 = Component.instances[component.anIntArray240[l1]];
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
		imageScrollbar0.blit(i1, l);
		imageScrollbar1.blit(i1, (l + j) - 16);
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
			if (npcs[l].anInt1537 != loopCycle) {
				npcs[l].type = null;
				npcs[l] = null;
			}
		}
		if (buffer.position != i) {
			Signlink.reporterror(aString1173 + " size mismatch in getnpcpos - pos:" + buffer.position + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < anInt836; i1++) {
			if (npcs[anIntArray837[i1]] == null) {
				Signlink.reporterror(aString1173 + " null entry in npc list - pos:" + i1 + " size:" + anInt836);
				throw new RuntimeException("eek");
			}
		}
	}

	public void method32() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 6) && (super.mousePressX <= 106) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				anInt1287 = (anInt1287 + 1) % 4;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put1(anInt1287);
				aBuffer_1192.put1(anInt845);
				aBuffer_1192.put1(anInt1248);
			}
			if ((super.mousePressX >= 135) && (super.mousePressX <= 235) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				anInt845 = (anInt845 + 1) % 3;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put1(anInt1287);
				aBuffer_1192.put1(anInt845);
				aBuffer_1192.put1(anInt1248);
			}
			if ((super.mousePressX >= 273) && (super.mousePressX <= 373) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				anInt1248 = (anInt1248 + 1) % 3;
				aBoolean1233 = true;
				aBoolean1223 = true;
				aBuffer_1192.putOp(95);
				aBuffer_1192.put1(anInt1287);
				aBuffer_1192.put1(anInt845);
				aBuffer_1192.put1(anInt1248);
			}
			if ((super.mousePressX >= 412) && (super.mousePressX <= 512) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				if (anInt857 == -1) {
					method147();
					aString881 = "";
					aBoolean1158 = false;
					for (int i = 0; i < Component.instances.length; i++) {
						if ((Component.instances[i] == null) || (Component.instances[i].anInt214 != 600)) {
							continue;
						}
						anInt1178 = anInt857 = Component.instances[i].anInt236;
						break;
					}
				} else {
					method77(0, "", "Please close the interface you have open before using 'report abuse'");
				}
			}
		}
	}

	public void method33(int i) {
		int j = VarpType.instances[i].anInt709;
		if (j == 0) {
			return;
		}
		int k = anIntArray971[i];
		if (j == 1) {
			if (k == 1) {
				Draw3D.setBrightness(0.90000000000000002D);
			}
			if (k == 2) {
				Draw3D.setBrightness(0.80000000000000004D);
			}
			if (k == 3) {
				Draw3D.setBrightness(0.69999999999999996D);
			}
			if (k == 4) {
				Draw3D.setBrightness(0.59999999999999998D);
			}
			ObjType.iconCache.clear();
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
			if ((aBoolean1151 != flag1) && !lowmem) {
				if (aBoolean1151) {
					music = anInt956;
					musicFade = true;
					ondemand.request(2, music);
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
				obj = self;
			} else if (j < anInt891) {
				obj = players[anIntArray892[j]];
			} else {
				obj = npcs[anIntArray837[j - anInt891]];
			}
			if ((obj == null) || !obj.method449()) {
				continue;
			}
			if (obj instanceof NPCEntity) {
				NPCType type = ((NPCEntity) obj).type;
				if (type.overrides != null) {
					type = type.getOverrideType();
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
								imageHeadicons[i2].draw(anInt963 - 12, anInt964 - l);
								l -= 25;
							}
						}
					}
				}
				if ((j >= 0) && (anInt855 == 10) && (anInt933 == anIntArray892[j])) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						imageHeadicons[7].draw(anInt963 - 12, anInt964 - l);
					}
				}
			} else {
				NPCType type_1 = ((NPCEntity) obj).type;
				if ((type_1.anInt75 >= 0) && (type_1.anInt75 < imageHeadicons.length)) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						imageHeadicons[type_1.anInt75].draw(anInt963 - 12, anInt964 - 30);
					}
				}
				if ((anInt855 == 1) && (anInt1222 == anIntArray837[j - anInt891]) && ((loopCycle % 20) < 10)) {
					method127(obj, obj.anInt1507 + 15);
					if (anInt963 > -1) {
						imageHeadicons[2].draw(anInt963 - 12, anInt964 - 28);
					}
				}
			}
			if ((obj.aString1506 != null) && ((j >= anInt891) || (anInt1287 == 0) || (anInt1287 == 3) || ((anInt1287 == 1) && method109(((PlayerEntity) obj).aString1703)))) {
				method127(obj, obj.anInt1507);
				if ((anInt963 > -1) && (anInt974 < anInt975)) {
					anIntArray979[anInt974] = fontBold12.stringWidth(obj.aString1506) / 2;
					anIntArray978[anInt974] = fontBold12.height;
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
			if (obj.anInt1532 > loopCycle) {
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
				if (obj.anIntArray1516[j1] > loopCycle) {
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
						imageHitmarks[obj.anIntArray1515[j1]].draw(anInt963 - 12, anInt964 - 12);
						fontPlain11.drawStringCenter(String.valueOf(obj.anIntArray1514[j1]), anInt963, anInt964 + 4, 0);
						fontPlain11.drawStringCenter(String.valueOf(obj.anIntArray1514[j1]), anInt963 - 1, anInt964 + 3, 0xffffff);
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
					fontBold12.drawStringCenter(s, anInt963, anInt964 + 1, 0);
					fontBold12.drawStringCenter(s, anInt963, anInt964, i3);
				}
				if (anIntArray981[k] == 1) {
					fontBold12.drawStringWave(s, anInt963, anInt964 + 1, 0, anInt1265);
					fontBold12.drawStringWave(s, anInt963, anInt964, i3, anInt1265);
				}
				if (anIntArray981[k] == 2) {
					fontBold12.drawStringWave2(s, anInt963, anInt964 + 1, 0, anInt1265);
					fontBold12.drawStringWave2(s, anInt963, anInt964, i3, anInt1265);
				}
				if (anIntArray981[k] == 3) {
					fontBold12.drawStringShake(s, anInt963, anInt964 + 1, 0, anInt1265, 150 - anIntArray982[k]);
					fontBold12.drawStringShake(s, anInt963, anInt964, i3, anInt1265, 150 - anIntArray982[k]);
				}
				if (anIntArray981[k] == 4) {
					int i4 = fontBold12.stringWidth(s);
					int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
					Draw2D.setBounds(334, anInt963 - 50, anInt963 + 50, 0);
					fontBold12.drawString(s, (anInt963 + 50) - k4, anInt964 + 1, 0);
					fontBold12.drawString(s, (anInt963 + 50) - k4, anInt964, i3);
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
					Draw2D.setBounds(anInt964 + 5, 0, 512, anInt964 - fontBold12.height - 1);
					fontBold12.drawStringCenter(s, anInt963, anInt964 + 1 + l4, 0);
					fontBold12.drawStringCenter(s, anInt963, anInt964 + l4, i3);
					Draw2D.resetBounds();
				}
			} else {
				fontBold12.drawStringCenter(s, anInt963, anInt964 + 1, 0);
				fontBold12.drawStringCenter(s, anInt963, anInt964, 0xffff00);
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
			aBuffer_1192.put8(l);
			break;
		}
	}

	public void method36() {
		aArea_1163.bind();
		Draw3D.lineOffset = anIntArray1181;
		imageInvback.blit(0, 0);
		if (anInt1189 != -1) {
			method105(0, 0, Component.instances[anInt1189], 0);
		} else if (anIntArray1130[anInt1221] != -1) {
			method105(0, 0, Component.instances[anIntArray1130[anInt1221]], 0);
		}
		if (aBoolean885 && (anInt948 == 1)) {
			method40();
		}
		aArea_1163.draw(super.graphics, 553, 205);
		areaViewport.bind();
		Draw3D.lineOffset = anIntArray1182;
	}

	public void method37(int j) {
		if (!lowmem) {
			if (Draw3D.textureCycle[17] >= j) {
				Image8 image = Draw3D.textures[17];
				int k = (image.width * image.height) - 1;
				int j1 = image.width * anInt945 * 2;
				byte[] abyte0 = image.pixels;
				byte[] abyte3 = aByteArray912;
				for (int i2 = 0; i2 <= k; i2++) {
					abyte3[i2] = abyte0[(i2 - j1) & k];
				}
				image.pixels = abyte3;
				aByteArray912 = abyte0;
				Draw3D.unloadTexture(17);
			}
			if (Draw3D.textureCycle[24] >= j) {
				Image8 class30_sub2_sub1_sub2_1 = Draw3D.textures[24];
				int l = (class30_sub2_sub1_sub2_1.width * class30_sub2_sub1_sub2_1.height) - 1;
				int k1 = class30_sub2_sub1_sub2_1.width * anInt945 * 2;
				byte[] abyte1 = class30_sub2_sub1_sub2_1.pixels;
				byte[] abyte4 = aByteArray912;
				for (int j2 = 0; j2 <= l; j2++) {
					abyte4[j2] = abyte1[(j2 - k1) & l];
				}
				class30_sub2_sub1_sub2_1.pixels = abyte4;
				aByteArray912 = abyte1;
				Draw3D.unloadTexture(24);
			}
			if (Draw3D.textureCycle[34] >= j) {
				Image8 class30_sub2_sub1_sub2_2 = Draw3D.textures[34];
				int i1 = (class30_sub2_sub1_sub2_2.width * class30_sub2_sub1_sub2_2.height) - 1;
				int l1 = class30_sub2_sub1_sub2_2.width * anInt945 * 2;
				byte[] abyte2 = class30_sub2_sub1_sub2_2.pixels;
				byte[] abyte5 = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++) {
					abyte5[k2] = abyte2[(k2 - l1) & i1];
				}
				class30_sub2_sub1_sub2_2.pixels = abyte5;
				aByteArray912 = abyte2;
				Draw3D.unloadTexture(34);
			}
		}
	}

	public void method38() {
		for (int i = -1; i < anInt891; i++) {
			int j;
			if (i == -1) {
				j = LOCAL_PLAYER_INDEX;
			} else {
				j = anIntArray892[i];
			}
			PlayerEntity player = players[j];
			if ((player != null) && (player.anInt1535 > 0)) {
				player.anInt1535--;
				if (player.anInt1535 == 0) {
					player.aString1506 = null;
				}
			}
		}
		for (int k = 0; k < anInt836; k++) {
			int l = anIntArray837[k];
			NPCEntity npc = npcs[l];
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
		int k = getHeightmapY(currentPlane, i, j) - anInt1100;
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
		k = getHeightmapY(currentPlane, i, j) - anInt997;
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
		fontBold12.drawString("Choose Option", i + 3, j + 14, i1);
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
			fontBold12.drawStringTaggable(aStringArray1199[l1], i + 3, i2, j2, true);
		}
	}

	public void method41(long l) {
		if (l == 0L) {
			return;
		}
		if ((anInt899 >= 100) && (anInt1046 != 1)) {
			method77(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		if (anInt899 >= 200) {
			method77(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(l));
		for (int i = 0; i < anInt899; i++) {
			if (aLongArray955[i] == l) {
				method77(0, "", s + " is already on your friend list");
				return;
			}
		}
		for (int j = 0; j < anInt822; j++) {
			if (aLongArray925[j] == l) {
				method77(0, "", "Please remove " + s + " from your ignore list first");
				return;
			}
		}
		if (!s.equals(self.aString1703)) {
			aStringArray1082[anInt899] = s;
			aLongArray955[anInt899] = l;
			anIntArray826[anInt899] = 0;
			anInt899++;
			aBoolean1153 = true;
			aBuffer_1192.putOp(188);
			aBuffer_1192.put8(l);
		}
	}

	public int getHeightmapY(int plane, int x, int z) {
		int stx = x >> 7;
		int stz = z >> 7;
		if ((stx < 0) || (stz < 0) || (stx > 103) || (stz > 103)) {
			return 0;
		}
		int p = plane;
		if ((p < 3) && ((planeTileFlags[1][stx][stz] & 2) == 2)) {
			p++;
		}
		int ltx = x & 0x7f;
		int ltz = z & 0x7f;
		int y00 = ((anIntArrayArrayArray1214[p][stx][stz] * (128 - ltx)) + (anIntArrayArrayArray1214[p][stx + 1][stz] * ltx)) >> 7;
		int y11 = ((anIntArrayArrayArray1214[p][stx][stz + 1] * (128 - ltx)) + (anIntArrayArrayArray1214[p][stx + 1][stz + 1] * ltx)) >> 7;
		return ((y00 * (128 - ltz)) + (y11 * ltz)) >> 7;
	}

	public void method44() {
		try {
			if (connection != null) {
				connection.method267();
			}
		} catch (Exception ignored) {
		}
		connection = null;
		ingame = false;
		anInt833 = 0;
		aString1173 = "";
		aString1174 = "";
		clearCaches();
		scene.clear();
		for (int i = 0; i < 4; i++) {
			collisions[i].method210();
		}
		System.gc();
		method15();
		anInt956 = -1;
		music = -1;
		anInt1259 = 0;
	}

	public void method45() {
		aBoolean1031 = true;
		for (int j = 0; j < 7; j++) {
			anIntArray1065[j] = -1;
			for (int k = 0; k < IDKType.anInt655; k++) {
				if (IDKType.instances[k].aBoolean662 || (IDKType.instances[k].anInt657 != (j + (aBoolean1047 ? 0 : 7)))) {
					continue;
				}
				anIntArray1065[j] = k;
				break;
			}
		}
	}

	public void method46(int i, Buffer buffer) {
		while ((buffer.bitPosition + 21) < (i * 8)) {
			int k = buffer.getBits(14);
			if (k == 16383) {
				break;
			}
			if (npcs[k] == null) {
				npcs[k] = new NPCEntity();
			}
			NPCEntity npc = npcs[k];
			anIntArray837[anInt836++] = k;
			npc.anInt1537 = loopCycle;
			int z = buffer.getBits(5);
			if (z > 15) {
				z -= 32;
			}
			int x = buffer.getBits(5);
			if (x > 15) {
				x -= 32;
			}
			int j1 = buffer.getBits(1);
			npc.type = NPCType.get(buffer.getBits(12));
			int k1 = buffer.getBits(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = k;
			}
			npc.size = npc.type.size;
			npc.turnSpeed = npc.type.turnSpeed;
			npc.seqWalk = npc.type.seqWalk;
			npc.seqTurnAround = npc.type.seqTurnAround;
			npc.seqTurnLeft = npc.type.seqTurnLeft;
			npc.seqTurnRight = npc.type.seqTurnRight;
			npc.seqStand = npc.type.seqStand;
			npc.move(self.pathTileX[0] + x, self.pathTileZ[0] + z, j1 == 1);
		}
		buffer.accessBytes();
	}

	public void method47(boolean local) {
		if (((self.x >> 7) == anInt1261) && ((self.z >> 7) == anInt1262)) {
			anInt1261 = 0;
		}

		int j = anInt891;

		if (local) {
			j = 1;
		}

		for (int l = 0; l < j; l++) {
			PlayerEntity player;
			int index;

			if (local) {
				player = self;
				index = LOCAL_PLAYER_INDEX << 14;
			} else {
				player = players[anIntArray892[l]];
				index = anIntArray892[l] << 14;
			}

			if ((player == null) || !player.method449()) {
				continue;
			}

			player.aBoolean1699 = ((lowmem && (anInt891 > 50)) || (anInt891 > 200)) && !local && (player.seqCurrent == player.seqStand);

			int stx = player.x >> 7;
			int stz = player.z >> 7;

			if ((stx < 0) || (stx >= 104) || (stz < 0) || (stz >= 104)) {
				continue;
			}

			if ((player.model != null) && (loopCycle >= player.anInt1707) && (loopCycle < player.anInt1708)) {
				player.aBoolean1699 = false;
				player.y = getHeightmapY(currentPlane, player.x, player.z);
				scene.addTemporary(player, currentPlane, player.minSceneTileX, player.minSceneTileZ, player.maxSceneTileX, player.maxSceneTileZ, player.x, player.z, player.y, player.yaw, index);
				continue;
			}

			if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
				if (anIntArrayArray929[stx][stz] == anInt1265) {
					continue;
				}
				anIntArrayArray929[stx][stz] = anInt1265;
			}

			player.y = getHeightmapY(currentPlane, player.x, player.z);
			scene.addTemporary(player, currentPlane, player.x, player.z, player.y, player.yaw, index, player.aBoolean1541, 60);
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
				} while (IDKType.instances[i2].aBoolean662 || (IDKType.instances[i2].anInt657 != (k + (aBoolean1047 ? 0 : 7))));
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
			aBuffer_1192.put1(aBoolean1047 ? 0 : 1);
			for (int i1 = 0; i1 < 7; i1++) {
				aBuffer_1192.put1(anIntArray1065[i1]);
			}
			for (int l1 = 0; l1 < 5; l1++) {
				aBuffer_1192.put1(anIntArray990[l1]);
			}
			return true;
		}
		if ((j >= 601) && (j <= 612)) {
			method147();
			if (aString881.length() > 0) {
				aBuffer_1192.putOp(218);
				aBuffer_1192.put8(StringUtil.toBase37(aString881));
				aBuffer_1192.put1(j - 601);
				aBuffer_1192.put1(aBoolean1158 ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			PlayerEntity player = players[k];
			int l = buffer.get1U();
			if ((l & 0x40) != 0) {
				l += buffer.get1U() << 8;
			}
			method107(l, k, buffer, player);
		}
	}

	public void drawMinimapLoc(int i, int k, int l, int i1, int j1) {
		int k1 = scene.getWallBitset(j1, l, i);
		if (k1 != 0) {
			int l1 = scene.getInfo(j1, l, i, k1);
			int k2 = (l1 >> 6) & 3;
			int i3 = l1 & 0x1f;
			int k3 = k;
			if (k1 > 0) {
				k3 = i1;
			}
			int[] ai = imageMinimap.pixels;
			int k4 = 24624 + (l * 4) + ((103 - i) * 512 * 4);
			int i5 = (k1 >> 14) & 0x7fff;
			LocType type_2 = LocType.method572(i5);
			if (type_2.anInt758 != -1) {
				Image8 class30_sub2_sub1_sub2_2 = imageMapscene[type_2.anInt758];
				if (class30_sub2_sub1_sub2_2 != null) {
					int i6 = ((type_2.anInt744 * 4) - class30_sub2_sub1_sub2_2.width) / 2;
					int j6 = ((type_2.anInt761 * 4) - class30_sub2_sub1_sub2_2.height) / 2;
					class30_sub2_sub1_sub2_2.blit(48 + (l * 4) + i6, 48 + ((104 - i - type_2.anInt761) * 4) + j6);
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
		k1 = scene.getLocBitset(j1, l, i);
		if (k1 != 0) {
			int i2 = scene.getInfo(j1, l, i, k1);
			int l2 = (i2 >> 6) & 3;
			int j3 = i2 & 0x1f;
			int l3 = (k1 >> 14) & 0x7fff;
			LocType type_1 = LocType.method572(l3);
			if (type_1.anInt758 != -1) {
				Image8 class30_sub2_sub1_sub2_1 = imageMapscene[type_1.anInt758];
				if (class30_sub2_sub1_sub2_1 != null) {
					int j5 = ((type_1.anInt744 * 4) - class30_sub2_sub1_sub2_1.width) / 2;
					int k5 = ((type_1.anInt761 * 4) - class30_sub2_sub1_sub2_1.height) / 2;
					class30_sub2_sub1_sub2_1.blit(48 + (l * 4) + j5, 48 + ((104 - i - type_1.anInt761) * 4) + k5);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (k1 > 0) {
					l4 = 0xee0000;
				}
				int[] ai1 = imageMinimap.pixels;
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
		k1 = scene.getGroundDecorationBitset(j1, l, i);
		if (k1 != 0) {
			int j2 = (k1 >> 14) & 0x7fff;
			LocType type = LocType.method572(j2);
			if (type.anInt758 != -1) {
				Image8 image = imageMapscene[type.anInt758];
				if (image != null) {
					int i4 = ((type.anInt744 * 4) - image.width) / 2;
					int j4 = ((type.anInt761 * 4) - image.height) / 2;
					image.blit(48 + (l * 4) + i4, 48 + ((104 - i - type.anInt761) * 4) + j4);
				}
			}
		}
	}

	public void createTitleImages() throws IOException {
		imageTitlebox = new Image8(archiveTitle, "titlebox", 0);
		imageTitlebutton = new Image8(archiveTitle, "titlebutton", 0);
		imageRunes = new Image8[12];
		int icon = 0;

		try {
			icon = Integer.parseInt(getParameter("fl_icon"));
		} catch (Exception ignored) {
		}

		if (icon == 0) {
			for (int i = 0; i < 12; i++) {
				imageRunes[i] = new Image8(archiveTitle, "runes", i);
			}
		} else {
			for (int i = 0; i < 12; i++) {
				imageRunes[i] = new Image8(archiveTitle, "runes", 12 + (i & 3));
			}
		}

		imageFlamesLeft = new Image24(128, 265);
		imageFlamesRight = new Image24(128, 265);

		System.arraycopy(imageTitle0.pixels, 0, imageFlamesLeft.pixels, 0, 33920);
		System.arraycopy(imageTitle1.pixels, 0, imageFlamesRight.pixels, 0, 33920);

		flameGradient0 = new int[256];

		for (int i = 0; i < 64; i++) {
			flameGradient0[i] = i * 0x40000;
		}
		for (int i = 0; i < 64; i++) {
			flameGradient0[i + 64] = 0xff0000 + (0x400 * i);
		}
		for (int i = 0; i < 64; i++) {
			flameGradient0[i + 128] = 0xffff00 + (0x4 * i);
		}
		for (int i = 0; i < 64; i++) {
			flameGradient0[i + 192] = 0xffffff;
		}

		flameGradient1 = new int[256];

		for (int i = 0; i < 64; i++) {
			flameGradient1[i] = i * 1024;
		}
		for (int i = 0; i < 64; i++) {
			flameGradient1[i + 64] = 65280 + (4 * i);
		}
		for (int i = 0; i < 64; i++) {
			flameGradient1[i + 128] = 65535 + (0x40000 * i);
		}
		for (int i = 0; i < 64; i++) {
			flameGradient1[i + 192] = 0xffffff;
		}

		flameGradient2 = new int[256];

		for (int k3 = 0; k3 < 64; k3++) {
			flameGradient2[k3] = k3 * 4;
		}
		for (int l3 = 0; l3 < 64; l3++) {
			flameGradient2[l3 + 64] = 255 + (0x40000 * l3);
		}
		for (int i4 = 0; i4 < 64; i4++) {
			flameGradient2[i4 + 128] = 0xff00ff + (1024 * i4);
		}
		for (int j4 = 0; j4 < 64; j4++) {
			flameGradient2[j4 + 192] = 0xffffff;
		}

		flameGradient = new int[256];
		flameBuffer0 = new int[32768];
		flameBuffer1 = new int[32768];
		method106(null);
		flameBuffer3 = new int[32768];
		flameBuffer2 = new int[32768];
		showProgress(10, "Connecting to fileserver");

		if (!flameActive) {
			aBoolean880 = true;
			flameActive = true;
			startThread(this, 2);
		}
	}

	public void method53() {
		if (lowmem && (sceneState == 2) && (SceneBuilder.anInt131 != currentPlane)) {
			areaViewport.bind();
			fontPlain12.drawStringCenter("Loading - please wait.", 257, 151, 0);
			fontPlain12.drawStringCenter("Loading - please wait.", 256, 150, 0xffffff);
			areaViewport.draw(super.graphics, 4, 4);
			sceneState = 1;
			sceneLoadStartTime = System.currentTimeMillis();
		}
		if (sceneState == 1) {
			int j = method54();
			if ((j != 0) && ((System.currentTimeMillis() - sceneLoadStartTime) > 0x57e40L)) {
				Signlink.reporterror(aString1173 + " glcfb " + aLong1215 + "," + j + "," + lowmem + "," + filestores[0] + "," + ondemand.remaining() + "," + currentPlane + "," + sceneCenterZoneX + "," + sceneCenterZoneZ);
				sceneLoadStartTime = System.currentTimeMillis();
			}
		}
		if ((sceneState == 2) && (currentPlane != minimapPlane)) {
			minimapPlane = currentPlane;
			createMinimap(currentPlane);
		}
	}

	public int method54() {
		for (int i = 0; i < sceneMapLandData.length; i++) {
			if ((sceneMapLandData[i] == null) && (sceneMapLandFile[i] != -1)) {
				return -1;
			}
			if ((sceneMapLocData[i] == null) && (sceneMapLocFile[i] != -1)) {
				return -2;
			}
		}
		boolean flag = true;
		for (int j = 0; j < sceneMapLandData.length; j++) {
			byte[] abyte0 = sceneMapLocData[j];
			if (abyte0 != null) {
				int k = ((sceneMapIndex[j] >> 8) * 64) - sceneBaseTileX;
				int l = ((sceneMapIndex[j] & 0xff) * 64) - sceneBaseTileZ;
				if (sceneInstanced) {
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
			sceneState = 2;
			SceneBuilder.anInt131 = currentPlane;
			method22();
			aBuffer_1192.putOp(121);
			return 0;
		}
	}

	public void method55() {
		for (ProjectileEntity projectile = (ProjectileEntity) aList_1013.peekFront(); projectile != null; projectile = (ProjectileEntity) aList_1013.prev()) {
			if ((projectile.anInt1597 != currentPlane) || (loopCycle > projectile.anInt1572)) {
				projectile.unlink();
			} else if (loopCycle >= projectile.anInt1571) {
				if (projectile.anInt1590 > 0) {
					NPCEntity npc = npcs[projectile.anInt1590 - 1];
					if ((npc != null) && (npc.x >= 0) && (npc.x < 13312) && (npc.z >= 0) && (npc.z < 13312)) {
						projectile.method455(loopCycle, npc.z, getHeightmapY(projectile.anInt1597, npc.x, npc.z) - projectile.anInt1583, npc.x);
					}
				}
				if (projectile.anInt1590 < 0) {
					int j = -projectile.anInt1590 - 1;
					PlayerEntity player;
					if (j == selfPlayerId) {
						player = self;
					} else {
						player = players[j];
					}
					if ((player != null) && (player.x >= 0) && (player.x < 13312) && (player.z >= 0) && (player.z < 13312)) {
						projectile.method455(loopCycle, player.z, getHeightmapY(projectile.anInt1597, player.x, player.z) - projectile.anInt1583, player.x);
					}
				}
				projectile.method456(anInt945);
				scene.addTemporary(projectile, currentPlane, (int) projectile.aDouble1585, (int) projectile.aDouble1586, (int) projectile.aDouble1587, projectile.anInt1595, -1, false, 60);
			}
		}
	}

	public void createTitleBackground() throws IOException {
		Image24 image = new Image24(archiveTitle.read("title.dat"), this);

		imageTitle0.bind();
		image.blitOpaque(0, 0);

		imageTitle1.bind();
		image.blitOpaque(-637, 0);

		imageTitle2.bind();
		image.blitOpaque(-128, 0);

		imageTitle3.bind();
		image.blitOpaque(-202, -371);

		imageTitle4.bind();
		image.blitOpaque(-202, -171);

		imageTitle5.bind();
		image.blitOpaque(0, -265);

		imageTitle6.bind();
		image.blitOpaque(-562, -265);

		imageTitle7.bind();
		image.blitOpaque(-128, -171);

		imageTitle8.bind();
		image.blitOpaque(-562, -171);

		// Flips the title background horizontally
		int[] tmp = new int[image.width];
		for (int y = 0; y < image.height; y++) {
			for (int x = 0; x < image.width; x++) {
				tmp[x] = image.pixels[(image.width - x - 1) + (image.width * y)];
			}
			System.arraycopy(tmp, 0, image.pixels, image.width * y, image.width);
		}

		imageTitle0.bind();
		image.blitOpaque(382, 0);

		imageTitle1.bind();
		image.blitOpaque(-255, 0);

		imageTitle2.bind();
		image.blitOpaque(254, 0);

		imageTitle3.bind();
		image.blitOpaque(180, -371);

		imageTitle4.bind();
		image.blitOpaque(180, -171);

		imageTitle5.bind();
		image.blitOpaque(382, -265);

		imageTitle6.bind();
		image.blitOpaque(-180, -265);

		imageTitle7.bind();
		image.blitOpaque(254, -171);

		imageTitle8.bind();
		image.blitOpaque(-180, -171);

		image = new Image24(archiveTitle, "logo", 0);
		imageTitle2.bind();
		image.draw(382 - (image.width / 2) - 128, 18);

		System.gc();
	}

	public void handleOnDemandRequests() {
		do {
			OnDemandRequest request;
			do {
				request = ondemand.poll();

				if (request == null) {
					return;
				}

				if (request.store == 0) {
					Model.unpack(request.data, request.file);
					if ((ondemand.getModelIndex(request.file) & 0x62) != 0) {
						aBoolean1153 = true;
						if (anInt1276 != -1) {
							aBoolean1223 = true;
						}
					}
				}

				if ((request.store == 1) && (request.data != null)) {
					SeqFrame.unpack(request.data);
				}

				if ((request.store == 2) && (request.file == music) && (request.data != null)) {
					midisave(musicFade, request.data);
				}

				if ((request.store == 3) && (sceneState == 1)) {
					for (int i = 0; i < sceneMapLandData.length; i++) {
						if (sceneMapLandFile[i] == request.file) {
							sceneMapLandData[i] = request.data;
							if (request.data == null) {
								sceneMapLandFile[i] = -1;
							}
							break;
						}
						if (sceneMapLocFile[i] == request.file) {
							sceneMapLocData[i] = request.data;
							if (request.data == null) {
								sceneMapLocFile[i] = -1;
							}
							break;
						}
					}
				}
			} while ((request.store != 93) || !ondemand.method564(request.file));
			SceneBuilder.method173(new Buffer(request.data), ondemand);
		} while (true);
	}

	public void updateFlames() {
		int height = 256;

		// add fuel to the bottom
		for (int x = 10; x < 117; x++) {
			if ((int) (Math.random() * 100.0) < 50) {
				flameBuffer3[x + ((height - 2) << 7)] = 255;
			}
		}

		// add sparkles of fuel everywhere
		for (int l = 0; l < 100; l++) {
			int x = (int) (Math.random() * 124D) + 2;
			int y = (int) (Math.random() * 128D) + 128;
			flameBuffer3[x + (y << 7)] = 192;
		}

		// blur that fuel
		for (int y = 1; y < (height - 1); y++) {
			for (int x = 1; x < 127; x++) {
				int pos = x + (y << 7);
				flameBuffer2[pos] = (flameBuffer3[pos - 1] + flameBuffer3[pos + 1] + flameBuffer3[pos - 128] + flameBuffer3[pos + 128]) / 4;
			}
		}

		flameCycle0 += 128;

		if (flameCycle0 > flameBuffer0.length) {
			flameCycle0 -= flameBuffer0.length;
			method106(imageRunes[(int) (Math.random() * 12D)]);
		}

		// flamebuffer0 is being used to dilute the fuel
		for (int y = 1; y < (height - 1); y++) {
			for (int x = 1; x < 127; x++) {
				int pos = x + (y << 7);
				int intensity = flameBuffer2[pos + 128] - (flameBuffer0[(pos + flameCycle0) & (flameBuffer0.length - 1)] / 5);

				if (intensity < 0) {
					intensity = 0;
				}

				flameBuffer3[pos] = intensity;
			}
		}

		for (int y = 0; y < (height - 1); y++) {
			flameLineOffset[y] = flameLineOffset[y + 1];
		}

		flameLineOffset[height - 1] = (int) ((Math.sin((double) loopCycle / 14D) * 16D) + (Math.sin((double) loopCycle / 15D) * 14D) + (Math.sin((double) loopCycle / 16D) * 12D));

		if (flameGradientCycle0 > 0) {
			flameGradientCycle0 -= 4;
		}

		if (flameGradientCycle1 > 0) {
			flameGradientCycle1 -= 4;
		}

		if ((flameGradientCycle0 == 0) && (flameGradientCycle1 == 0)) {
			int rng = (int) (Math.random() * 2000D);
			if (rng == 0) {
				flameGradientCycle0 = 1024;
			} else if (rng == 1) {
				flameGradientCycle1 = 1024;
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
		Component component = Component.instances[i];
		for (int j = 0; j < component.anIntArray240.length; j++) {
			if (component.anIntArray240[j] == -1) {
				break;
			}
			Component component_1 = Component.instances[component.anIntArray240[j]];
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
		method128(((anInt934 - sceneBaseTileX) << 7) + anInt937, anInt936 * 2, ((anInt935 - sceneBaseTileZ) << 7) + anInt938);
		if ((anInt963 > -1) && ((loopCycle % 20) < 10)) {
			imageHeadicons[2].draw(anInt963 - 12, anInt964 - 28);
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
		if (!ingame) {
			return;
		}
		synchronized (aMouseRecorder_879.lock) {
			if (aBoolean1205) {
				if ((super.mousePressButton != 0) || (aMouseRecorder_879.anInt810 >= 40)) {
					aBuffer_1192.putOp(45);
					aBuffer_1192.put1(0);
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
								aBuffer_1192.put2((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else if (anInt1022 < 8) {
								aBuffer_1192.put3(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else {
								aBuffer_1192.put4(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}
					aBuffer_1192.putSize1(aBuffer_1192.position - j2);
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
		if (super.mousePressButton != 0) {
			long l = (super.mousePressTime - aLong1220) / 50L;
			if (l > 4095L) {
				l = 4095L;
			}
			aLong1220 = super.mousePressTime;
			int k2 = super.mousePressY;
			if (k2 < 0) {
				k2 = 0;
			} else if (k2 > 502) {
				k2 = 502;
			}
			int k3 = super.mousePressX;
			if (k3 < 0) {
				k3 = 0;
			} else if (k3 > 764) {
				k3 = 764;
			}
			int k4 = (k2 * 765) + k3;
			int j5 = 0;
			if (super.mousePressButton == 2) {
				j5 = 1;
			}
			int l5 = (int) l;
			aBuffer_1192.putOp(241);
			aBuffer_1192.put4((l5 << 20) + (j5 << 19) + k4);
		}
		if (anInt1016 > 0) {
			anInt1016--;
		}
		if ((super.actionKey[1] == 1) || (super.actionKey[2] == 1) || (super.actionKey[3] == 1) || (super.actionKey[4] == 1)) {
			aBoolean1017 = true;
		}
		if (aBoolean1017 && (anInt1016 <= 0)) {
			anInt1016 = 20;
			aBoolean1017 = false;
			aBuffer_1192.putOp(86);
			aBuffer_1192.put2(anInt1184);
			aBuffer_1192.put2A(anInt1185);
		}
		if (super.aBoolean17 && !aBoolean954) {
			aBoolean954 = true;
			aBuffer_1192.putOp(3);
			aBuffer_1192.put1(1);
		}
		if (!super.aBoolean17 && aBoolean954) {
			aBoolean954 = false;
			aBuffer_1192.putOp(3);
			aBuffer_1192.put1(0);
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
			if (super.mouseButton == 0) {
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
						Component component = Component.instances[anInt1084];
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
						aBuffer_1192.put2LEA(anInt1084);
						aBuffer_1192.put1C(j1);
						aBuffer_1192.put2LEA(anInt1085);
						aBuffer_1192.put2LE(anInt1066);
					}
				} else if (((anInt1253 == 1) || method17(anInt1133 - 1)) && (anInt1133 > 2)) {
					method116();
				} else if (anInt1133 > 0) {
					method69(anInt1133 - 1);
				}
				anInt1243 = 10;
				super.mousePressButton = 0;
			}
		}
		if (Scene.anInt470 != -1) {
			int k = Scene.anInt470;
			int k1 = Scene.anInt471;
			boolean flag = method85(0, 0, 0, 0, self.pathTileZ[0], 0, 0, k1, self.pathTileX[0], true, k);
			Scene.anInt470 = -1;
			if (flag) {
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 1;
				anInt916 = 0;
			}
		}
		if ((super.mousePressButton == 1) && (aString844 != null)) {
			aString844 = null;
			aBoolean1223 = true;
			super.mousePressButton = 0;
		}
		method20();
		method92();
		method78();
		method32();
		if ((super.mouseButton == 1) || (super.mousePressButton == 1)) {
			anInt1213++;
		}
		if (sceneState == 2) {
			method108();
		}
		if ((sceneState == 2) && aBoolean1160) {
			method39();
		}
		for (int i1 = 0; i1 < 5; i1++) {
			anIntArray1030[i1]++;
		}
		method73();
		super.idleCycles++;
		if (super.idleCycles > 4500) {
			anInt1011 = 250;
			super.idleCycles -= 500;
			aBuffer_1192.putOp(202);
		}
		anInt1010++;
		if (anInt1010 > 50) {
			aBuffer_1192.putOp(0);
		}
		try {
			if ((connection != null) && (aBuffer_1192.position > 0)) {
				connection.method271(aBuffer_1192.position, aBuffer_1192.data, 0);
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
		SceneLocTemporary loc = (SceneLocTemporary) listTemporaryLocs.peekFront();
		for (; loc != null; loc = (SceneLocTemporary) listTemporaryLocs.prev()) {
			if (loc.anInt1294 == -1) {
				loc.anInt1302 = 0;
				method89(loc);
			} else {
				loc.unlink();
			}
		}
	}

	public void method64() throws IOException {
		if (imageTitle2 != null) {
			return;
		}
		aArea_1166 = null;
		aArea_1164 = null;
		aArea_1163 = null;
		areaViewport = null;
		aArea_1123 = null;
		aArea_1124 = null;
		aArea_1125 = null;
		imageTitle0 = new DrawArea(128, 265, getComponent());
		Draw2D.clear();
		imageTitle1 = new DrawArea(128, 265, getComponent());
		Draw2D.clear();
		imageTitle2 = new DrawArea(509, 171, getComponent());
		Draw2D.clear();
		imageTitle3 = new DrawArea(360, 132, getComponent());
		Draw2D.clear();
		imageTitle4 = new DrawArea(360, 200, getComponent());
		Draw2D.clear();
		imageTitle5 = new DrawArea(202, 238, getComponent());
		Draw2D.clear();
		imageTitle6 = new DrawArea(203, 238, getComponent());
		Draw2D.clear();
		imageTitle7 = new DrawArea(74, 94, getComponent());
		Draw2D.clear();
		imageTitle8 = new DrawArea(75, 94, getComponent());
		Draw2D.clear();
		if (archiveTitle != null) {
			createTitleBackground();
			createTitleImages();
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
		int j1 = scene.getInfo(currentPlane, k, j, i);
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
			method85(2, 0, j2, 0, self.pathTileZ[0], i2, k2, j, self.pathTileX[0], false, k);
		} else {
			method85(2, l1, 0, k1 + 1, self.pathTileZ[0], 0, 0, j, self.pathTileX[0], false, k);
		}
		anInt914 = super.mousePressX;
		anInt915 = super.mousePressY;
		anInt917 = 2;
		anInt916 = 0;
		return true;
	}

	public FileArchive loadArchive(int i, String s, String s1, int j, int k) throws IOException {
		byte[] abyte0 = null;
		int l = 5;
		try {
			if (filestores[0] != null) {
				abyte0 = filestores[0].read(i);
			}
		} catch (Exception ignored) {
		}
		if (abyte0 != null) {
			aCRC32_930.reset();
			aCRC32_930.update(abyte0);
			int i1 = (int) aCRC32_930.getValue();
			if (i1 != j) {
				//abyte0 = null;
			}
		}
		if (abyte0 != null) {
			return new FileArchive(abyte0);
		}
		int j1 = 0;
		while (abyte0 == null) {
			String s2 = "Unknown error";
			showProgress(k, "Requesting " + s);
			try {
				int k1 = 0;
				DataInputStream datainputstream = openURL(s1 + j);
				byte[] abyte1 = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Buffer buffer = new Buffer(abyte1);
				buffer.position = 3;
				int i2 = buffer.get3() + 6;
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
						showProgress(k, "Loading " + s + " - " + k3 + "%");
					}
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (filestores[0] != null) {
						filestores[0].write(abyte0, i, abyte0.length);
					}
				} catch (Exception _ex) {
					filestores[0] = null;
				}
				if (abyte0 != null) {
					aCRC32_930.reset();
					aCRC32_930.update(abyte0);
					int i3 = (int) aCRC32_930.getValue();
					if (i3 != j) {
						//abyte0 = null;
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
						showProgress(k, "Game updated - please reload page");
						l1 = 10;
					} else {
						showProgress(k, s2 + " - Retrying in " + l1);
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
		areaViewport.bind();
		fontPlain12.drawStringCenter("Connection lost", 257, 144, 0);
		fontPlain12.drawStringCenter("Connection lost", 256, 143, 0xffffff);
		fontPlain12.drawStringCenter("Please wait - attempting to reestablish", 257, 159, 0);
		fontPlain12.drawStringCenter("Please wait - attempting to reestablish", 256, 158, 0xffffff);
		areaViewport.draw(super.graphics, 4, 4);
		minimapState = 0;
		anInt1261 = 0;
		Connection connection = this.connection;
		ingame = false;
		anInt1038 = 0;
		method84(aString1173, aString1174, true);
		if (!ingame) {
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
			NPCEntity npc = npcs[i1];
			if (npc != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, npc.pathTileZ[0], self.pathTileX[0], false, npc.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(57);
				aBuffer_1192.put2A(anInt1285);
				aBuffer_1192.put2A(i1);
				aBuffer_1192.put2LE(anInt1283);
				aBuffer_1192.put2A(anInt1284);
			}
		}
		if (l == 234) {
			boolean flag1 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag1) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(236);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2(i1);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
		}
		if ((l == 62) && method66(i1, k, j)) {
			aBuffer_1192.putOp(192);
			aBuffer_1192.put2(anInt1284);
			aBuffer_1192.put2LE((i1 >> 14) & 0x7fff);
			aBuffer_1192.put2LEA(k + sceneBaseTileZ);
			aBuffer_1192.put2LE(anInt1283);
			aBuffer_1192.put2LEA(j + sceneBaseTileX);
			aBuffer_1192.put2(anInt1285);
		}
		if (l == 511) {
			boolean flag2 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag2) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(25);
			aBuffer_1192.put2LE(anInt1284);
			aBuffer_1192.put2A(anInt1285);
			aBuffer_1192.put2(i1);
			aBuffer_1192.put2A(k + sceneBaseTileZ);
			aBuffer_1192.put2LEA(anInt1283);
			aBuffer_1192.put2(j + sceneBaseTileX);
		}
		if (l == 74) {
			aBuffer_1192.putOp(122);
			aBuffer_1192.put2LEA(k);
			aBuffer_1192.put2A(j);
			aBuffer_1192.put2LE(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 315) {
			Component component = Component.instances[k];
			boolean flag8 = true;
			if (component.anInt214 > 0) {
				flag8 = method48(component);
			}
			if (flag8) {
				aBuffer_1192.putOp(185);
				aBuffer_1192.put2(k);
			}
		}
		if (l == 561) {
			PlayerEntity player = players[i1];
			if (player != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, player.pathTileZ[0], self.pathTileX[0], false, player.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(128);
				aBuffer_1192.put2(i1);
			}
		}
		if (l == 20) {
			NPCEntity class30_sub2_sub4_sub1_sub1_1 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub1_1.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(155);
				aBuffer_1192.put2LE(i1);
			}
		}
		if (l == 779) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_1 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub2_1.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(153);
				aBuffer_1192.put2LE(i1);
			}
		}
		if (l == 516) {
			if (!aBoolean885) {
				scene.method312(super.mousePressY - 4, super.mousePressX - 4);
			} else {
				scene.method312(k - 4, j - 4);
			}
		}
		if (l == 1062) {
			method66(i1, k, j);
			aBuffer_1192.putOp(228);
			aBuffer_1192.put2A((i1 >> 14) & 0x7fff);
			aBuffer_1192.put2A(k + sceneBaseTileZ);
			aBuffer_1192.put2(j + sceneBaseTileX);
		}
		if ((l == 679) && !aBoolean1149) {
			aBuffer_1192.putOp(40);
			aBuffer_1192.put2(k);
			aBoolean1149 = true;
		}
		if (l == 431) {
			aBuffer_1192.putOp(129);
			aBuffer_1192.put2A(j);
			aBuffer_1192.put2(k);
			aBuffer_1192.put2A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
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
			aBuffer_1192.put2LE(j);
			aBuffer_1192.put2A(k);
			aBuffer_1192.put2LE(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 539) {
			aBuffer_1192.putOp(16);
			aBuffer_1192.put2A(i1);
			aBuffer_1192.put2LEA(j);
			aBuffer_1192.put2LEA(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
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
					PlayerEntity player_7 = players[anIntArray892[j3]];
					if ((player_7 == null) || (player_7.aString1703 == null) || !player_7.aString1703.equalsIgnoreCase(s7)) {
						continue;
					}
					method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, player_7.pathTileZ[0], self.pathTileX[0], false, player_7.pathTileX[0]);
					if (l == 484) {
						aBuffer_1192.putOp(139);
						aBuffer_1192.put2LE(anIntArray892[j3]);
					}
					if (l == 6) {
						aBuffer_1192.putOp(128);
						aBuffer_1192.put2(anIntArray892[j3]);
					}
					flag9 = true;
					break;
				}
				if (!flag9) {
					method77(0, "", "Unable to find " + s7);
				}
			}
		}
		if (l == 870) {
			aBuffer_1192.putOp(53);
			aBuffer_1192.put2(j);
			aBuffer_1192.put2A(anInt1283);
			aBuffer_1192.put2LEA(i1);
			aBuffer_1192.put2(anInt1284);
			aBuffer_1192.put2LE(anInt1285);
			aBuffer_1192.put2(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 847) {
			aBuffer_1192.putOp(87);
			aBuffer_1192.put2A(i1);
			aBuffer_1192.put2(k);
			aBuffer_1192.put2A(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 626) {
			Component component_1 = Component.instances[k];
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
			aBuffer_1192.put2LEA(k);
			aBuffer_1192.put2LEA(i1);
			aBuffer_1192.put2LE(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 27) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_2 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub2_2.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(73);
				aBuffer_1192.put2LE(i1);
			}
		}
		if (l == 213) {
			boolean flag3 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag3) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(79);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2(i1);
			aBuffer_1192.put2A(j + sceneBaseTileX);
		}
		if (l == 632) {
			aBuffer_1192.putOp(145);
			aBuffer_1192.put2A(k);
			aBuffer_1192.put2A(j);
			aBuffer_1192.put2A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 493) {
			aBuffer_1192.putOp(75);
			aBuffer_1192.put2LEA(k);
			aBuffer_1192.put2LE(j);
			aBuffer_1192.put2A(i1);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 652) {
			boolean flag4 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag4) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(156);
			aBuffer_1192.put2A(j + sceneBaseTileX);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2LEA(i1);
		}
		if (l == 94) {
			boolean flag5 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag5) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(181);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2(i1);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
			aBuffer_1192.put2A(anInt1137);
		}
		if (l == 646) {
			aBuffer_1192.putOp(185);
			aBuffer_1192.put2(k);
			Component component_2 = Component.instances[k];
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
			NPCEntity class30_sub2_sub4_sub1_sub1_2 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub1_2.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub1_2.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(17);
				aBuffer_1192.put2LEA(i1);
			}
		}
		if (l == 965) {
			NPCEntity npc_3 = npcs[i1];
			if (npc_3 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, npc_3.pathTileZ[0], self.pathTileX[0], false, npc_3.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(21);
				aBuffer_1192.put2(i1);
			}
		}
		if (l == 413) {
			NPCEntity class30_sub2_sub4_sub1_sub1_4 = npcs[i1];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub1_4.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub1_4.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(131);
				aBuffer_1192.put2LEA(i1);
				aBuffer_1192.put2A(anInt1137);
			}
		}
		if (l == 200) {
			method147();
		}
		if (l == 1025) {
			NPCEntity npc_5 = npcs[i1];
			if (npc_5 != null) {
				NPCType type = npc_5.type;
				if (type.overrides != null) {
					type = type.getOverrideType();
				}
				if (type != null) {
					String s9;
					if (type.aByteArray89 != null) {
						s9 = new String(type.aByteArray89);
					} else {
						s9 = "It's a " + type.aString65 + ".";
					}
					method77(0, "", s9);
				}
			}
		}
		if (l == 900) {
			method66(i1, k, j);
			aBuffer_1192.putOp(252);
			aBuffer_1192.put2LEA((i1 >> 14) & 0x7fff);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2A(j + sceneBaseTileX);
		}
		if (l == 412) {
			NPCEntity npc_6 = npcs[i1];
			if (npc_6 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, npc_6.pathTileZ[0], self.pathTileX[0], false, npc_6.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(72);
				aBuffer_1192.put2A(i1);
			}
		}
		if (l == 365) {
			PlayerEntity player_3 = players[i1];
			if (player_3 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, player_3.pathTileZ[0], self.pathTileX[0], false, player_3.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(249);
				aBuffer_1192.put2A(i1);
				aBuffer_1192.put2LE(anInt1137);
			}
		}
		if (l == 729) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_4 = players[i1];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, class30_sub2_sub4_sub1_sub2_4.pathTileZ[0], self.pathTileX[0], false, class30_sub2_sub4_sub1_sub2_4.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(39);
				aBuffer_1192.put2LE(i1);
			}
		}
		if (l == 577) {
			PlayerEntity player_5 = players[i1];
			if (player_5 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, player_5.pathTileZ[0], self.pathTileX[0], false, player_5.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(139);
				aBuffer_1192.put2LE(i1);
			}
		}
		if ((l == 956) && method66(i1, k, j)) {
			aBuffer_1192.putOp(35);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
			aBuffer_1192.put2A(anInt1137);
			aBuffer_1192.put2A(k + sceneBaseTileZ);
			aBuffer_1192.put2LE((i1 >> 14) & 0x7fff);
		}
		if (l == 567) {
			boolean flag6 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag6) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(23);
			aBuffer_1192.put2LE(k + sceneBaseTileZ);
			aBuffer_1192.put2LE(i1);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
		}
		if (l == 867) {
			aBuffer_1192.putOp(43);
			aBuffer_1192.put2LE(k);
			aBuffer_1192.put2A(i1);
			aBuffer_1192.put2A(j);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 543) {
			aBuffer_1192.putOp(237);
			aBuffer_1192.put2(j);
			aBuffer_1192.put2A(i1);
			aBuffer_1192.put2(k);
			aBuffer_1192.put2A(anInt1137);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
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
					for (int i3 = 0; i3 < Component.instances.length; i3++) {
						if ((Component.instances[i3] == null) || (Component.instances[i3].anInt214 != 600)) {
							continue;
						}
						anInt1178 = anInt857 = Component.instances[i3].anInt236;
						break;
					}
				} else {
					method77(0, "", "Please close the interface you have open before using 'report abuse'");
				}
			}
		}
		if (l == 491) {
			PlayerEntity player_6 = players[i1];
			if (player_6 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, player_6.pathTileZ[0], self.pathTileX[0], false, player_6.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(14);
				aBuffer_1192.put2A(anInt1284);
				aBuffer_1192.put2(i1);
				aBuffer_1192.put2(anInt1285);
				aBuffer_1192.put2LE(anInt1283);
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
			aBuffer_1192.put2(i1);
			aBuffer_1192.put2A(j);
			aBuffer_1192.put2A(k);
			anInt1243 = 0;
			anInt1244 = k;
			anInt1245 = j;
			anInt1246 = 2;
			if (Component.instances[k].anInt236 == anInt857) {
				anInt1246 = 1;
			}
			if (Component.instances[k].anInt236 == anInt1276) {
				anInt1246 = 3;
			}
		}
		if (l == 478) {
			NPCEntity npc_7 = npcs[i1];
			if (npc_7 != null) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, npc_7.pathTileZ[0], self.pathTileX[0], false, npc_7.pathTileX[0]);
				anInt914 = super.mousePressX;
				anInt915 = super.mousePressY;
				anInt917 = 2;
				anInt916 = 0;
				aBuffer_1192.putOp(18);
				aBuffer_1192.put2LE(i1);
			}
		}
		if (l == 113) {
			method66(i1, k, j);
			aBuffer_1192.putOp(70);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
			aBuffer_1192.put2(k + sceneBaseTileZ);
			aBuffer_1192.put2LEA((i1 >> 14) & 0x7fff);
		}
		if (l == 872) {
			method66(i1, k, j);
			aBuffer_1192.putOp(234);
			aBuffer_1192.put2LEA(j + sceneBaseTileX);
			aBuffer_1192.put2A((i1 >> 14) & 0x7fff);
			aBuffer_1192.put2LEA(k + sceneBaseTileZ);
		}
		if (l == 502) {
			method66(i1, k, j);
			aBuffer_1192.putOp(132);
			aBuffer_1192.put2LEA(j + sceneBaseTileX);
			aBuffer_1192.put2((i1 >> 14) & 0x7fff);
			aBuffer_1192.put2A(k + sceneBaseTileZ);
		}
		if (l == 1125) {
			ObjType type = ObjType.method198(i1);
			Component component_4 = Component.instances[k];
			String s5;
			if ((component_4 != null) && (component_4.anIntArray252[j] >= 0x186a0)) {
				s5 = component_4.anIntArray252[j] + " x " + type.aString170;
			} else if (type.aByteArray178 != null) {
				s5 = new String(type.aByteArray178);
			} else {
				s5 = "It's a " + type.aString170 + ".";
			}
			method77(0, "", s5);
		}
		if (l == 169) {
			aBuffer_1192.putOp(185);
			aBuffer_1192.put2(k);
			Component component_3 = Component.instances[k];
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
			method77(0, "", s10);
		}
		if (l == 244) {
			boolean flag7 = method85(2, 0, 0, 0, self.pathTileZ[0], 0, 0, k, self.pathTileX[0], false, j);
			if (!flag7) {
				method85(2, 0, 1, 0, self.pathTileZ[0], 1, 0, k, self.pathTileX[0], false, j);
			}
			anInt914 = super.mousePressX;
			anInt915 = super.mousePressY;
			anInt917 = 2;
			anInt916 = 0;
			aBuffer_1192.putOp(253);
			aBuffer_1192.put2LE(j + sceneBaseTileX);
			aBuffer_1192.put2LEA(k + sceneBaseTileZ);
			aBuffer_1192.put2A(i1);
		}
		if (l == 1448) {
			ObjType type_1 = ObjType.method198(i1);
			String s6;
			if (type_1.aByteArray178 != null) {
				s6 = new String(type_1.aByteArray178);
			} else {
				s6 = "It's a " + type_1.aString170 + ".";
			}
			method77(0, "", s6);
		}
		anInt1282 = 0;
		anInt1136 = 0;
		aBoolean1153 = true;
	}

	public void method70() {
		anInt1251 = 0;
		int j = (self.x >> 7) + sceneBaseTileX;
		int k = (self.z >> 7) + sceneBaseTileZ;
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
			if ((k1 == 2) && (scene.getInfo(currentPlane, i1, j1, l) >= 0)) {
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
				NPCEntity npc = npcs[l1];
				if ((npc.type.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
					for (int j2 = 0; j2 < anInt836; j2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_1 = npcs[anIntArray837[j2]];
						if ((class30_sub2_sub4_sub1_sub1_1 != null) && (class30_sub2_sub4_sub1_sub1_1 != npc) && (class30_sub2_sub4_sub1_sub1_1.type.size == 1) && (class30_sub2_sub4_sub1_sub1_1.x == npc.x) && (class30_sub2_sub4_sub1_sub1_1.z == npc.z)) {
							method87(class30_sub2_sub4_sub1_sub1_1.type, anIntArray837[j2], j1, i1);
						}
					}
					for (int l2 = 0; l2 < anInt891; l2++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_1 = players[anIntArray892[l2]];
						if ((class30_sub2_sub4_sub1_sub2_1 != null) && (class30_sub2_sub4_sub1_sub2_1.x == npc.x) && (class30_sub2_sub4_sub1_sub2_1.z == npc.z)) {
							method88(i1, anIntArray892[l2], class30_sub2_sub4_sub1_sub2_1, j1);
						}
					}
				}
				method87(npc.type, l1, j1, i1);
			}
			if (k1 == 0) {
				PlayerEntity player = players[l1];
				if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
					for (int k2 = 0; k2 < anInt836; k2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_2 = npcs[anIntArray837[k2]];
						if ((class30_sub2_sub4_sub1_sub1_2 != null) && (class30_sub2_sub4_sub1_sub1_2.type.size == 1) && (class30_sub2_sub4_sub1_sub1_2.x == player.x) && (class30_sub2_sub4_sub1_sub1_2.z == player.z)) {
							method87(class30_sub2_sub4_sub1_sub1_2.type, anIntArray837[k2], j1, i1);
						}
					}
					for (int i3 = 0; i3 < anInt891; i3++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_2 = players[anIntArray892[i3]];
						if ((class30_sub2_sub4_sub1_sub2_2 != null) && (class30_sub2_sub4_sub1_sub2_2 != player) && (class30_sub2_sub4_sub1_sub2_2.x == player.x) && (class30_sub2_sub4_sub1_sub2_2.z == player.z)) {
							method88(i1, anIntArray892[i3], class30_sub2_sub4_sub1_sub2_2, j1);
						}
					}
				}
				method88(i1, l1, player, j1);
			}
			if (k1 == 3) {
				DoublyLinkedList list = aListArrayArrayArray827[currentPlane][i1][j1];
				if (list != null) {
					for (ObjStackEntity objStack = (ObjStackEntity) list.peekBack(); objStack != null; objStack = (ObjStackEntity) list.next()) {
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

	public void debug() {
		System.out.println("============");
		System.out.println("flame-cycle:" + flameCycle);
		if (ondemand != null) {
			System.out.println("Od-cycle:" + ondemand.cycle);
			System.out.println("Od-remaining:" + ondemand.remaining());
			for (OnDemandRequest request : ondemand.requests) {
				System.out.println(request);
			}
		}
		System.out.println("loop-cycle:" + loopCycle);
		System.out.println("draw-cycle:" + drawCycle);
		System.out.println("ptype:" + ptype);
		System.out.println("psize:" + psize);
		System.out.println("scene-state:" + sceneState);
		System.out.println("draw-state:" + method54());
		if (connection != null) {
			connection.method272();
		}
		super.debug = true;
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
						aBuffer_1192.put1(0);
						int k = aBuffer_1192.position;
						aBuffer_1192.put8(aLong953);
						ChatCompression.pack(aString1212, aBuffer_1192);
						aBuffer_1192.putSize1(aBuffer_1192.position - k);
						aString1212 = ChatCompression.method527(aString1212);
						aString1212 = Censor.method497(aString1212, 0);
						method77(6, StringUtil.formatName(StringUtil.fromBase37(aLong953)), aString1212);
						if (anInt845 == 2) {
							anInt845 = 1;
							aBoolean1233 = true;
							aBuffer_1192.putOp(95);
							aBuffer_1192.put1(anInt1287);
							aBuffer_1192.put1(anInt845);
							aBuffer_1192.put1(anInt1248);
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
						aBuffer_1192.put4(i1);
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
						aBuffer_1192.put8(StringUtil.toBase37(aString1004));
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
					if (rights == 0) {
						if (aString887.equals("::clientdrop")) {
							method68();
						}
						if (aString887.equals("::lag")) {
							debug();
						}
						if (aString887.equals("::prefetchmusic")) {
							for (int j1 = 0; j1 < ondemand.getFileCount(2); j1++) {
								ondemand.prefetch((byte) 1, 2, j1);
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
										collisions[k1].flags[i2][k2] = 0;
									}
								}
							}
						}
					}
					if (aString887.startsWith("::")) {
						aBuffer_1192.putOp(103);
						aBuffer_1192.put1(aString887.length() - 1);
						aBuffer_1192.put(aString887.substring(2));
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
						aBuffer_1192.put1(0);
						int j3 = aBuffer_1192.position;
						aBuffer_1192.put1S(i3);
						aBuffer_1192.put1S(j2);
						aBuffer_834.position = 0;
						ChatCompression.pack(aString887, aBuffer_834);
						aBuffer_1192.putA(aBuffer_834.data, 0, aBuffer_834.position);
						aBuffer_1192.putSize1(aBuffer_1192.position - j3);
						aString887 = ChatCompression.method527(aString887);
						aString887 = Censor.method497(aString887, 0);
						self.aString1506 = aString887;
						self.anInt1513 = j2;
						self.anInt1531 = i3;
						self.anInt1535 = 150;
						if (rights == 2) {
							method77(2, "@cr2@" + self.aString1703, self.aString1506);
						} else if (rights == 1) {
							method77(2, "@cr1@" + self.aString1703, self.aString1506);
						} else {
							method77(2, self.aString1703, self.aString1506);
						}
						if (anInt1287 == 2) {
							anInt1287 = 3;
							aBoolean1233 = true;
							aBuffer_1192.putOp(95);
							aBuffer_1192.put1(anInt1287);
							aBuffer_1192.put1(anInt845);
							aBuffer_1192.put1(anInt1248);
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
				if ((j > (k1 - 14)) && (j <= k1) && !s.equals(self.aString1703)) {
					if (rights >= 1) {
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
					if (rights >= 1) {
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
			} else if (anIntArray826[j] == nodeId) {
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
			component.anInt271 = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			if (aBoolean1031) {
				for (int k1 = 0; k1 < 7; k1++) {
					int l1 = anIntArray1065[k1];
					if ((l1 >= 0) && !IDKType.instances[l1].method537()) {
						return;
					}
				}
				aBoolean1031 = false;
				Model[] aclass30_sub2_sub4_sub6 = new Model[7];
				int i2 = 0;
				for (int j2 = 0; j2 < 7; j2++) {
					int k2 = anIntArray1065[j2];
					if (k2 >= 0) {
						aclass30_sub2_sub4_sub6[i2++] = IDKType.instances[k2].method538();
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
				model.applySequenceFrame(SeqType.instances[self.seqStand].primaryFrames[0]);
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
			if ((loopCycle % 20) < 10) {
				component.aString248 += "|";
			} else {
				component.aString248 += " ";
			}
			return;
		}
		if (j == 613) {
			if (rights >= 1) {
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
		BitmapFont font = fontPlain12;
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
					font.drawString("From", k1, l, 0);
					font.drawString("From", k1, l - 1, 65535);
					k1 += font.stringWidthTaggable("From ");
					if (byte1 == 1) {
						aImageArray1219[0].blit(k1, l - 12);
						k1 += 14;
					}
					if (byte1 == 2) {
						aImageArray1219[1].blit(k1, l - 12);
						k1 += 14;
					}
					font.drawString(s + ": " + aStringArray944[j], k1, l, 0);
					font.drawString(s + ": " + aStringArray944[j], k1, l - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 5) && (anInt845 < 2)) {
					int i1 = 329 - (i * 13);
					font.drawString(aStringArray944[j], 4, i1, 0);
					font.drawString(aStringArray944[j], 4, i1 - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 6) && (anInt845 < 2)) {
					int j1 = 329 - (i * 13);
					font.drawString("To " + s + ": " + aStringArray944[j], 4, j1, 0);
					font.drawString("To " + s + ": " + aStringArray944[j], 4, j1 - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
			}
		}
	}

	public void method77(int type, String prefix, String message) {
		if ((type == 0) && (anInt1042 != -1)) {
			aString844 = message;
			super.mousePressButton = 0;
		}
		if (anInt1276 == -1) {
			aBoolean1223 = true;
		}
		for (int j = 99; j > 0; j--) {
			anIntArray942[j] = anIntArray942[j - 1];
			aStringArray943[j] = aStringArray943[j - 1];
			aStringArray944[j] = aStringArray944[j - 1];
		}
		anIntArray942[0] = type;
		aStringArray943[0] = prefix;
		aStringArray944[0] = message;
	}

	public void method78() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 539) && (super.mousePressX <= 573) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (anIntArray1130[0] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 0;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 569) && (super.mousePressX <= 599) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (anIntArray1130[1] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 1;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 597) && (super.mousePressX <= 627) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (anIntArray1130[2] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 2;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 625) && (super.mousePressX <= 669) && (super.mousePressY >= 168) && (super.mousePressY < 203) && (anIntArray1130[3] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 3;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 666) && (super.mousePressX <= 696) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (anIntArray1130[4] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 4;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 694) && (super.mousePressX <= 724) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (anIntArray1130[5] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 5;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 722) && (super.mousePressX <= 756) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (anIntArray1130[6] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 6;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 540) && (super.mousePressX <= 574) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (anIntArray1130[7] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 7;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 572) && (super.mousePressX <= 602) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (anIntArray1130[8] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 8;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 599) && (super.mousePressX <= 629) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (anIntArray1130[9] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 9;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 627) && (super.mousePressX <= 671) && (super.mousePressY >= 467) && (super.mousePressY < 502) && (anIntArray1130[10] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 10;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 669) && (super.mousePressX <= 699) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (anIntArray1130[11] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 11;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 696) && (super.mousePressX <= 726) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (anIntArray1130[12] != -1)) {
				aBoolean1153 = true;
				anInt1221 = 12;
				aBoolean1103 = true;
			}
			if ((super.mousePressX >= 724) && (super.mousePressX <= 758) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (anIntArray1130[13] != -1)) {
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
		imageTitle2 = null;
		imageTitle3 = null;
		imageTitle4 = null;
		imageTitle0 = null;
		imageTitle1 = null;
		imageTitle5 = null;
		imageTitle6 = null;
		imageTitle7 = null;
		imageTitle8 = null;
		aArea_1166 = new DrawArea(479, 96, getComponent());
		aArea_1164 = new DrawArea(172, 156, getComponent());
		Draw2D.clear();
		imageMapback.blit(0, 0);
		aArea_1163 = new DrawArea(190, 261, getComponent());
		areaViewport = new DrawArea(512, 334, getComponent());
		Draw2D.clear();
		aArea_1123 = new DrawArea(496, 50, getComponent());
		aArea_1124 = new DrawArea(269, 37, getComponent());
		aArea_1125 = new DrawArea(249, 45, getComponent());
		aBoolean1255 = true;
	}

	public String getHost() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getDocumentBase().getHost().toLowerCase();
		}
		if (super.frame != null) {
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
			j1 = (j1 * 256) / (minimapZoom + 256);
			k1 = (k1 * 256) / (minimapZoom + 256);
			int l1 = ((j * j1) + (k * k1)) >> 16;
			int i2 = ((j * k1) - (k * j1)) >> 16;
			double d = Math.atan2(l1, i2);
			int j2 = (int) (Math.sin(d) * 63D);
			int k2 = (int) (Math.cos(d) * 57D);
			imageMapedge.drawRotated((94 + j2 + 4) - 10, 83 - k2 - 20, 20, 20, 15, 15, d, 256);
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
				method29(4, Component.instances[anInt857], super.anInt20, 4, super.anInt21, 0);
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
				method29(553, Component.instances[anInt1189], super.anInt20, 205, super.anInt21, 0);
			} else if (anIntArray1130[anInt1221] != -1) {
				method29(553, Component.instances[anIntArray1130[anInt1221]], super.anInt20, 205, super.anInt21, 0);
			}
		}
		if (anInt886 != anInt1048) {
			aBoolean1153 = true;
			anInt1048 = anInt886;
		}
		anInt886 = 0;
		if ((super.anInt20 > 17) && (super.anInt21 > 357) && (super.anInt20 < 496) && (super.anInt21 < 453)) {
			if (anInt1276 != -1) {
				method29(17, Component.instances[anInt1276], super.anInt20, 357, super.anInt21, 0);
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

	public int mix(int src, int dst, int alpha) {
		int invAlpha = 256 - alpha;
		return (((((src & 0xff00ff) * invAlpha) + ((dst & 0xff00ff) * alpha)) & 0xff00ff00) + ((((src & 0xff00) * invAlpha) + ((dst & 0xff00) * alpha)) & 0xff0000)) >> 8;
	}

	public void method84(String s, String s1, boolean flag) {
		try {
			if (!flag) {
				aString1266 = "";
				aString1267 = "Connecting to server...";
				method135(true);
			}
			connection = new Connection(this, method19(43594 + portOffset));
			long l = StringUtil.toBase37(s);
			int i = (int) ((l >> 16) & 31L);
			aBuffer_1192.position = 0;
			aBuffer_1192.put1(14);
			aBuffer_1192.put1(i);
			connection.method271(2, aBuffer_1192.data, 0);
			for (int j = 0; j < 8; j++) {
				connection.method268();
			}
			int k = connection.method268();
			int i1 = k;
			if (k == 0) {
				connection.method270(in.data, 0, 8);
				in.position = 0;
				aLong1215 = in.get8();

				// apache math tries to fill the remaining 1008 bytes up with random junk if we don't give it 256 ints.
				int[] seed = new int[1 << 8];

				seed[0] = (int) (Math.random() * 99999999D);
				seed[1] = (int) (Math.random() * 99999999D);
				seed[2] = (int) (aLong1215 >> 32);
				seed[3] = (int) aLong1215;
				aBuffer_1192.position = 0;
				aBuffer_1192.put1(10);
				aBuffer_1192.put4(seed[0]);
				aBuffer_1192.put4(seed[1]);
				aBuffer_1192.put4(seed[2]);
				aBuffer_1192.put4(seed[3]);
				aBuffer_1192.put4(Signlink.uid);
				aBuffer_1192.put(s);
				aBuffer_1192.put(s1);
				aBuffer_1192.encrypt(RSA_PUBLIC_KEY, RSA_MODULUS);
				aBuffer_847.position = 0;
				if (flag) {
					aBuffer_847.put1(18);
				} else {
					aBuffer_847.put1(16);
				}
				aBuffer_847.put1(aBuffer_1192.position + 36 + 1 + 1 + 2);
				aBuffer_847.put1(255);
				aBuffer_847.put2(317);
				aBuffer_847.put1(lowmem ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++) {
					aBuffer_847.put4(archiveChecksum[l1]);
				}
				aBuffer_847.put(aBuffer_1192.data, aBuffer_1192.position, 0);
				aBuffer_1192.random = new ISAACRandom(seed);
				for (int j2 = 0; j2 < 4; j2++) {
					seed[j2] += 50;
				}
				randomIn = new ISAACRandom(seed);
				connection.method271(aBuffer_847.position, aBuffer_847.data, 0);
				k = connection.method268();
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
				rights = connection.method268();
				aBoolean1205 = connection.method268() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				aMouseRecorder_879.anInt810 = 0;
				super.aBoolean17 = true;
				aBoolean954 = true;
				ingame = true;
				aBuffer_1192.position = 0;
				in.position = 0;
				ptype = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				psize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1011 = 0;
				anInt855 = 0;
				anInt1133 = 0;
				aBoolean885 = false;
				super.idleCycles = 0;
				for (int j1 = 0; j1 < 100; j1++) {
					aStringArray944[j1] = null;
				}
				anInt1282 = 0;
				anInt1136 = 0;
				sceneState = 0;
				anInt1062 = 0;
				anInt1278 = (int) (Math.random() * 100D) - 50;
				anInt1131 = (int) (Math.random() * 110D) - 55;
				anInt896 = (int) (Math.random() * 80D) - 40;
				anInt1209 = (int) (Math.random() * 120D) - 60;
				minimapZoom = (int) (Math.random() * 30D) - 20;
				anInt1185 = ((int) (Math.random() * 20D) - 10) & 0x7ff;
				minimapState = 0;
				minimapPlane = -1;
				anInt1261 = 0;
				anInt1262 = 0;
				anInt891 = 0;
				anInt836 = 0;
				for (int i2 = 0; i2 < MAX_PLAYER_COUNT; i2++) {
					players[i2] = null;
					aBufferArray895[i2] = null;
				}
				for (int k2 = 0; k2 < 16384; k2++) {
					npcs[k2] = null;
				}
				self = players[LOCAL_PLAYER_INDEX] = new PlayerEntity();
				aList_1013.clear();
				aList_1056.clear();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++) {
							aListArrayArrayArray827[l2][i3][k3] = null;
						}
					}
				}
				listTemporaryLocs = new DoublyLinkedList();
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
				ingame = true;
				aBuffer_1192.position = 0;
				in.position = 0;
				ptype = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				psize = 0;
				anInt1009 = 0;
				anInt1104 = 0;
				anInt1133 = 0;
				aBoolean885 = false;
				sceneLoadStartTime = System.currentTimeMillis();
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
				for (int k1 = connection.method268(); k1 >= 0; k1--) {
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
		int[][] ai = collisions[currentPlane].flags;
		while (i4 != l3) {
			j3 = anIntArray1280[i4];
			k3 = anIntArray1281[i4];
			i4 = (i4 + 1) % j4;
			if ((j3 == k2) && (k3 == i2)) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if (((i1 < 5) || (i1 == 10)) && collisions[currentPlane].method219(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if ((i1 < 10) && collisions[currentPlane].method220(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if ((k1 != 0) && (k != 0) && collisions[currentPlane].method221(i2, k2, j3, k, l1, k1, k3)) {
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
				aBuffer_1192.put1(k4 + k4 + 3);
			}
			if (i == 1) {
				aBuffer_1192.putOp(248);
				aBuffer_1192.put1(k4 + k4 + 3 + 14);
			}
			if (i == 2) {
				aBuffer_1192.putOp(98);
				aBuffer_1192.put1(k4 + k4 + 3);
			}
			aBuffer_1192.put2LEA(k6 + sceneBaseTileX);
			anInt1261 = anIntArray1280[0];
			anInt1262 = anIntArray1281[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				aBuffer_1192.put1(anIntArray1280[i4] - k6);
				aBuffer_1192.put1(anIntArray1281[i4] - i7);
			}
			aBuffer_1192.put2LE(i7 + sceneBaseTileZ);
			aBuffer_1192.put1C((super.actionKey[5] != 1) ? 0 : 1);
			return true;
		}
		return i != 1;
	}

	public void method86(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			NPCEntity npc = npcs[k];
			int l = buffer.get1U();
			if ((l & 0x10) != 0) {
				int i1 = buffer.get2ULE();
				if (i1 == 65535) {
					i1 = -1;
				}
				int i2 = buffer.get1U();
				if ((i1 == npc.anInt1526) && (i1 != -1)) {
					int l2 = SeqType.instances[i1].anInt365;
					if (l2 == 1) {
						npc.anInt1527 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if (l2 == 2) {
						npc.anInt1530 = 0;
					}
				} else if ((i1 == -1) || (npc.anInt1526 == -1) || (SeqType.instances[i1].anInt359 >= SeqType.instances[npc.anInt1526].anInt359)) {
					npc.anInt1526 = i1;
					npc.anInt1527 = 0;
					npc.anInt1528 = 0;
					npc.anInt1529 = i2;
					npc.anInt1530 = 0;
					npc.anInt1542 = npc.pathRemaining;
				}
			}
			if ((l & 8) != 0) {
				int j1 = buffer.get1UA();
				int j2 = buffer.get1UC();
				npc.method447(j2, j1, loopCycle);
				npc.anInt1532 = loopCycle + 300;
				npc.anInt1533 = buffer.get1UA();
				npc.anInt1534 = buffer.get1U();
			}
			if ((l & 0x80) != 0) {
				npc.spotanim = buffer.get2U();
				int k1 = buffer.get4();
				npc.anInt1524 = k1 >> 16;
				npc.anInt1523 = loopCycle + (k1 & 0xffff);
				npc.spotanimFrame = 0;
				npc.spotanimCycle = 0;
				if (npc.anInt1523 > loopCycle) {
					npc.spotanimFrame = -1;
				}
				if (npc.spotanim == 65535) {
					npc.spotanim = -1;
				}
			}
			if ((l & 0x20) != 0) {
				npc.index = buffer.get2U();
				if (npc.index == 65535) {
					npc.index = -1;
				}
			}
			if ((l & 1) != 0) {
				npc.aString1506 = buffer.getString();
				npc.anInt1535 = 100;
			}
			if ((l & 0x40) != 0) {
				int l1 = buffer.get1UC();
				int k2 = buffer.get1US();
				npc.method447(k2, l1, loopCycle);
				npc.anInt1532 = loopCycle + 300;
				npc.anInt1533 = buffer.get1US();
				npc.anInt1534 = buffer.get1UC();
			}
			if ((l & 2) != 0) {
				npc.type = NPCType.get(buffer.get2ULEA());
				npc.size = npc.type.size;
				npc.turnSpeed = npc.type.turnSpeed;
				npc.seqWalk = npc.type.seqWalk;
				npc.seqTurnAround = npc.type.seqTurnAround;
				npc.seqTurnLeft = npc.type.seqTurnLeft;
				npc.seqTurnRight = npc.type.seqTurnRight;
				npc.seqStand = npc.type.seqStand;
			}
			if ((l & 4) != 0) {
				npc.faceTileX = buffer.get2ULE();
				npc.faceTileZ = buffer.get2ULE();
			}
		}
	}

	public void method87(NPCType type, int i, int j, int k) {
		if (anInt1133 >= 400) {
			return;
		}
		if (type.overrides != null) {
			type = type.getOverrideType();
		}
		if (type == null) {
			return;
		}
		if (!type.aBoolean84) {
			return;
		}
		String s = type.aString65;
		if (type.anInt61 != 0) {
			s = s + method110(self.anInt1705, type.anInt61) + " (level-" + type.anInt61 + ")";
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
						if (type.anInt61 > self.anInt1705) {
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
		if (player == self) {
			return;
		}
		if (anInt1133 >= 400) {
			return;
		}
		String s;
		if (player.anInt1723 == 0) {
			s = player.aString1703 + method110(self.anInt1705, player.anInt1705) + " (level-" + player.anInt1705 + ")";
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
						if (player.anInt1705 > self.anInt1705) {
							c = '\u07D0';
						}
						if ((self.anInt1701 != 0) && (player.anInt1701 != 0)) {
							if (self.anInt1701 == player.anInt1701) {
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
			i = scene.getWallBitset(loc.anInt1295, loc.sceneTileX, loc.sceneTileZ);
		}
		if (loc.anInt1296 == 1) {
			i = scene.getWallDecorationBitset(loc.anInt1295, loc.sceneTileX, loc.sceneTileZ);
		}
		if (loc.anInt1296 == 2) {
			i = scene.getLocBitset(loc.anInt1295, loc.sceneTileX, loc.sceneTileZ);
		}
		if (loc.anInt1296 == 3) {
			i = scene.getGroundDecorationBitset(loc.anInt1295, loc.sceneTileX, loc.sceneTileZ);
		}
		if (i != 0) {
			int i1 = scene.getInfo(loc.anInt1295, loc.sceneTileX, loc.sceneTileZ, i);
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
						Buffer buffer = SoundTrack.generate(anIntArray1241[i], anIntArray1207[i]);
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
			if ((anInt1259 == 0) && aBoolean1151 && !lowmem) {
				music = anInt956;
				musicFade = true;
				ondemand.request(2, music);
			}
		}
	}

	public void method91(Buffer buffer, int i) {
		while ((buffer.bitPosition + 10) < (i * 8)) {
			int j = buffer.getBits(11);
			if (j == 2047) {
				break;
			}
			if (players[j] == null) {
				players[j] = new PlayerEntity();
				if (aBufferArray895[j] != null) {
					players[j].method451(aBufferArray895[j]);
				}
			}
			anIntArray892[anInt891++] = j;
			PlayerEntity player = players[j];
			player.anInt1537 = loopCycle;
			int k = buffer.getBits(1);
			if (k == 1) {
				anIntArray894[anInt893++] = j;
			}
			int l = buffer.getBits(1);
			int i1 = buffer.getBits(5);
			if (i1 > 15) {
				i1 -= 32;
			}
			int j1 = buffer.getBits(5);
			if (j1 > 15) {
				j1 -= 32;
			}
			player.move(self.pathTileX[0] + j1, self.pathTileZ[0] + i1, l == 1);
		}
		buffer.accessBytes();
	}

	public void method92() {
		if (minimapState != 0) {
			return;
		}
		if (super.mousePressButton == 1) {
			int i = super.mousePressX - 25 - 550;
			int j = super.mousePressY - 5 - 4;
			if ((i >= 0) && (j >= 0) && (i < 146) && (j < 151)) {
				i -= 73;
				j -= 75;
				int k = (anInt1185 + anInt1209) & 0x7ff;
				int i1 = Draw3D.sin[k];
				int j1 = Draw3D.cos[k];
				i1 = (i1 * (minimapZoom + 256)) >> 8;
				j1 = (j1 * (minimapZoom + 256)) >> 8;
				int k1 = ((j * i1) + (i * j1)) >> 11;
				int l1 = ((j * j1) - (i * i1)) >> 11;
				int i2 = (self.x + k1) >> 7;
				int j2 = (self.z - l1) >> 7;
				boolean flag1 = method85(1, 0, 0, 0, self.pathTileZ[0], 0, 0, j2, self.pathTileX[0], true, i2);
				if (flag1) {
					aBuffer_1192.put1(i);
					aBuffer_1192.put1(j);
					aBuffer_1192.put2(anInt1185);
					aBuffer_1192.put1(57);
					aBuffer_1192.put1(anInt1209);
					aBuffer_1192.put1(minimapZoom);
					aBuffer_1192.put1(89);
					aBuffer_1192.put2(self.x);
					aBuffer_1192.put2(self.z);
					aBuffer_1192.put1(anInt1264);
					aBuffer_1192.put1(63);
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
		Graphics g = getComponent().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		setFrameRate(1);
		if (aBoolean926) {
			flameActive = false;
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
		if (errInvalidHost) {
			flameActive = false;
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play RuneScape make sure you play from", 50, 100);
			g.drawString("http://www.runescape.com", 50, 150);
		}
		if (errAlreadyStarted) {
			flameActive = false;
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
			NPCEntity npc = npcs[k];
			if (npc != null) {
				method96(npc);
			}
		}
	}

	public void method96(PathingEntity entity) {
		if ((entity.x < 128) || (entity.z < 128) || (entity.x >= 13184) || (entity.z >= 13184)) {
			entity.anInt1526 = -1;
			entity.spotanim = -1;
			entity.forceMoveEndCycle = 0;
			entity.forceMoveStartCycle = 0;
			entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
			entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
			entity.method446();
		}
		if ((entity == self) && ((entity.x < 1536) || (entity.z < 1536) || (entity.x >= 11776) || (entity.z >= 11776))) {
			entity.anInt1526 = -1;
			entity.spotanim = -1;
			entity.forceMoveEndCycle = 0;
			entity.forceMoveStartCycle = 0;
			entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
			entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
			entity.method446();
		}
		if (entity.forceMoveEndCycle > loopCycle) {
			method97(entity);
		} else if (entity.forceMoveStartCycle >= loopCycle) {
			method98(entity);
		} else {
			method99(entity);
		}
		method100(entity);
		method101(entity);
	}

	public void method97(PathingEntity entity) {
		int delta = entity.forceMoveEndCycle - loopCycle;
		int dstX = (entity.forceMoveStartSceneTileX * 128) + (entity.size * 64);
		int dstZ = (entity.forceMoveStartSceneTileZ * 128) + (entity.size * 64);

		entity.x += (dstX - entity.x) / delta;
		entity.z += (dstZ - entity.z) / delta;

		entity.anInt1503 = 0;

		if (entity.forceMoveFaceDirection == 0) {
			entity.dstYaw = 1024;
		}
		if (entity.forceMoveFaceDirection == 1) {
			entity.dstYaw = 1536;
		}
		if (entity.forceMoveFaceDirection == 2) {
			entity.dstYaw = 0;
		}
		if (entity.forceMoveFaceDirection == 3) {
			entity.dstYaw = 512;
		}
	}

	public void method98(PathingEntity entity) {
		if ((entity.forceMoveStartCycle == loopCycle) || (entity.anInt1526 == -1) || (entity.anInt1529 != 0) || ((entity.anInt1528 + 1) > SeqType.instances[entity.anInt1526].getFrameDelay(entity.anInt1527))) {
			int duration = entity.forceMoveStartCycle - entity.forceMoveEndCycle;
			int delta = loopCycle - entity.forceMoveEndCycle;
			int dx0 = (entity.forceMoveStartSceneTileX * 128) + (entity.size * 64);
			int dz0 = (entity.forceMoveStartSceneTileZ * 128) + (entity.size * 64);
			int dx1 = (entity.forceMoveEndSceneTileX * 128) + (entity.size * 64);
			int dz1 = (entity.forceMoveEndSceneTileZ * 128) + (entity.size * 64);
			entity.x = ((dx0 * (duration - delta)) + (dx1 * delta)) / duration;
			entity.z = ((dz0 * (duration - delta)) + (dz1 * delta)) / duration;
		}

		entity.anInt1503 = 0;

		if (entity.forceMoveFaceDirection == 0) {
			entity.dstYaw = 1024;
		}
		if (entity.forceMoveFaceDirection == 1) {
			entity.dstYaw = 1536;
		}
		if (entity.forceMoveFaceDirection == 2) {
			entity.dstYaw = 0;
		}
		if (entity.forceMoveFaceDirection == 3) {
			entity.dstYaw = 512;
		}
		entity.yaw = entity.dstYaw;
	}

	public void method99(PathingEntity entity) {
		entity.seqCurrent = entity.seqStand;

		if (entity.pathRemaining == 0) {
			entity.anInt1503 = 0;
			return;
		}

		if ((entity.anInt1526 != -1) && (entity.anInt1529 == 0)) {
			SeqType type = SeqType.instances[entity.anInt1526];

			if ((entity.anInt1542 > 0) && (type.anInt363 == 0)) {
				entity.anInt1503++;
				return;
			}

			if ((entity.anInt1542 <= 0) && (type.anInt364 == 0)) {
				entity.anInt1503++;
				return;
			}
		}

		int x = entity.x;
		int z = entity.z;
		int dstX = (entity.pathTileX[entity.pathRemaining - 1] * 128) + (entity.size * 64);
		int dstZ = (entity.pathTileZ[entity.pathRemaining - 1] * 128) + (entity.size * 64);

		if (((dstX - x) > 256) || ((dstX - x) < -256) || ((dstZ - z) > 256) || ((dstZ - z) < -256)) {
			entity.x = dstX;
			entity.z = dstZ;
			return;
		}

		if (x < dstX) {
			if (z < dstZ) {
				entity.dstYaw = 1280;
			} else if (z > dstZ) {
				entity.dstYaw = 1792;
			} else {
				entity.dstYaw = 1536;
			}
		} else if (x > dstX) {
			if (z < dstZ) {
				entity.dstYaw = 768;
			} else if (z > dstZ) {
				entity.dstYaw = 256;
			} else {
				entity.dstYaw = 512;
			}
		} else if (z < dstZ) {
			entity.dstYaw = 1024;
		} else {
			entity.dstYaw = 0;
		}

		int remainingYaw = (entity.dstYaw - entity.yaw) & 0x7ff;

		if (remainingYaw > 1024) {
			remainingYaw -= 2048;
		}

		int seq = entity.seqTurnAround;

		// Since the game uses a left-handed coordinate system, an increasing angle goes clockwise.

		// yaw >= -45 deg && yaw <= 45 deg
		if ((remainingYaw >= -256) && (remainingYaw <= 256)) {
			seq = entity.seqWalk;
		}
		// yaw >= 45 deg && yaw <= 135 deg
		else if ((remainingYaw >= 256) && (remainingYaw < 768)) {
			seq = entity.seqTurnRight;
		}
		// yaw >= -135 deg && yaw <= -45 deg
		else if ((remainingYaw >= -768) && (remainingYaw <= -256)) {
			seq = entity.seqTurnLeft;
		}

		if (seq == -1) {
			seq = entity.seqWalk;
		}

		entity.seqCurrent = seq;

		int moveSpeed = 4;

		if ((entity.yaw != entity.dstYaw) && (entity.index == -1) && (entity.turnSpeed != 0)) {
			moveSpeed = 2;
		}

		if (entity.pathRemaining > 2) {
			moveSpeed = 6;
		}

		if (entity.pathRemaining > 3) {
			moveSpeed = 8;
		}

		if ((entity.anInt1503 > 0) && (entity.pathRemaining > 1)) {
			moveSpeed = 8;
			entity.anInt1503--;
		}

		if (entity.pathRunning[entity.pathRemaining - 1]) {
			moveSpeed <<= 1;
		}

		if ((moveSpeed >= 8) && (entity.seqCurrent == entity.seqWalk) && (entity.seqRun != -1)) {
			entity.seqCurrent = entity.seqRun;
		}

		if (x < dstX) {
			entity.x += moveSpeed;
			if (entity.x > dstX) {
				entity.x = dstX;
			}
		} else if (x > dstX) {
			entity.x -= moveSpeed;
			if (entity.x < dstX) {
				entity.x = dstX;
			}
		}
		if (z < dstZ) {
			entity.z += moveSpeed;
			if (entity.z > dstZ) {
				entity.z = dstZ;
			}
		} else if (z > dstZ) {
			entity.z -= moveSpeed;
			if (entity.z < dstZ) {
				entity.z = dstZ;
			}
		}
		if ((entity.x == dstX) && (entity.z == dstZ)) {
			entity.pathRemaining--;
			if (entity.anInt1542 > 0) {
				entity.anInt1542--;
			}
		}
	}

	public void method100(PathingEntity e) {
		if (e.turnSpeed == 0) {
			return;
		}

		if ((e.index != -1) && (e.index < 32768)) {
			NPCEntity npc = npcs[e.index];

			if (npc != null) {
				int dstX = e.x - npc.x;
				int dstZ = e.z - npc.z;

				if ((dstX != 0) || (dstZ != 0)) {
					e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
				}
			}
		}
		if (e.index >= 32768) {
			int index = e.index - 32768;

			if (index == selfPlayerId) {
				index = LOCAL_PLAYER_INDEX;
			}

			PlayerEntity player = players[index];

			if (player != null) {
				int dstX = e.x - player.x;
				int dstZ = e.z - player.z;

				if ((dstX != 0) || (dstZ != 0)) {
					e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
				}
			}
		}

		if (((e.faceTileX != 0) || (e.faceTileZ != 0)) && ((e.pathRemaining == 0) || (e.anInt1503 > 0))) {
			int dstX = e.x - ((e.faceTileX - sceneBaseTileX - sceneBaseTileX) * 64);
			int dstZ = e.z - ((e.faceTileZ - sceneBaseTileZ - sceneBaseTileZ) * 64);

			if ((dstX != 0) || (dstZ != 0)) {
				e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
			}

			e.faceTileX = 0;
			e.faceTileZ = 0;
		}

		int remainingYaw = (e.dstYaw - e.yaw) & 0x7ff;

		if (remainingYaw != 0) {
			if ((remainingYaw < e.turnSpeed) || (remainingYaw > (2048 - e.turnSpeed))) {
				e.yaw = e.dstYaw;
			} else if (remainingYaw > 1024) {
				e.yaw -= e.turnSpeed;
			} else {
				e.yaw += e.turnSpeed;
			}

			e.yaw &= 0x7ff;

			if ((e.seqCurrent == e.seqStand) && (e.yaw != e.dstYaw)) {
				if (e.seqTurn != -1) {
					e.seqCurrent = e.seqTurn;
					return;
				}

				e.seqCurrent = e.seqWalk;
			}
		}
	}

	public void method101(PathingEntity e) {
		e.aBoolean1541 = false;

		if (e.seqCurrent != -1) {
			SeqType seq = SeqType.instances[e.seqCurrent];
			e.seqCycle++;

			if ((e.seqFrame < seq.frameCount) && (e.seqCycle > seq.getFrameDelay(e.seqFrame))) {
				e.seqCycle = 0;
				e.seqFrame++;
			}

			if (e.seqFrame >= seq.frameCount) {
				e.seqCycle = 0;
				e.seqFrame = 0;
			}
		}

		if ((e.spotanim != -1) && (loopCycle >= e.anInt1523)) {
			if (e.spotanimFrame < 0) {
				e.spotanimFrame = 0;
			}

			SeqType seq = SpotAnimType.instances[e.spotanim].seq;

			for (e.spotanimCycle++; (e.spotanimFrame < seq.frameCount) && (e.spotanimCycle > seq.getFrameDelay(e.spotanimFrame)); e.spotanimFrame++) {
				e.spotanimCycle -= seq.getFrameDelay(e.spotanimFrame);
			}

			if ((e.spotanimFrame >= seq.frameCount) && ((e.spotanimFrame < 0) || (e.spotanimFrame >= seq.frameCount))) {
				e.spotanim = -1;
			}
		}

		if ((e.anInt1526 != -1) && (e.anInt1529 <= 1)) {
			SeqType seq = SeqType.instances[e.anInt1526];

			if ((seq.anInt363 == 1) && (e.anInt1542 > 0) && (e.forceMoveEndCycle <= loopCycle) && (e.forceMoveStartCycle < loopCycle)) {
				e.anInt1529 = 1;
				return;
			}
		}

		if ((e.anInt1526 != -1) && (e.anInt1529 == 0)) {
			SeqType seq = SeqType.instances[e.anInt1526];

			for (e.anInt1528++; (e.anInt1527 < seq.frameCount) && (e.anInt1528 > seq.getFrameDelay(e.anInt1527)); e.anInt1527++) {
				e.anInt1528 -= seq.getFrameDelay(e.anInt1527);
			}

			if (e.anInt1527 >= seq.frameCount) {
				e.anInt1527 -= seq.anInt356;
				e.anInt1530++;

				if (e.anInt1530 >= seq.anInt362) {
					e.anInt1526 = -1;
				}
				if ((e.anInt1527 < 0) || (e.anInt1527 >= seq.frameCount)) {
					e.anInt1526 = -1;
				}
			}
			e.aBoolean1541 = seq.aBoolean358;
		}

		if (e.anInt1529 > 0) {
			e.anInt1529--;
		}
	}

	public void method102() {
		if (aBoolean1255) {
			aBoolean1255 = false;
			aArea_903.draw(super.graphics, 0, 4);
			aArea_904.draw(super.graphics, 0, 357);
			aArea_905.draw(super.graphics, 722, 4);
			aArea_906.draw(super.graphics, 743, 205);
			aArea_907.draw(super.graphics, 0, 0);
			aArea_908.draw(super.graphics, 516, 4);
			aArea_909.draw(super.graphics, 516, 205);
			aArea_910.draw(super.graphics, 496, 357);
			aArea_911.draw(super.graphics, 0, 338);
			aBoolean1153 = true;
			aBoolean1223 = true;
			aBoolean1103 = true;
			aBoolean1233 = true;
			if (sceneState != 2) {
				areaViewport.draw(super.graphics, 4, 4);
				aArea_1164.draw(super.graphics, 550, 4);
			}
		}
		if (sceneState == 2) {
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
		if (sceneState == 2) {
			method126();
			aArea_1164.draw(super.graphics, 550, 4);
		}
		if (anInt1054 != -1) {
			aBoolean1103 = true;
		}
		if (aBoolean1103) {
			if ((anInt1054 != -1) && (anInt1054 == anInt1221)) {
				anInt1054 = -1;
				aBuffer_1192.putOp(120);
				aBuffer_1192.put1(anInt1221);
			}
			aBoolean1103 = false;
			aArea_1125.bind();
			imageBackhmid1.blit(0, 0);
			if (anInt1189 == -1) {
				if (anIntArray1130[anInt1221] != -1) {
					if (anInt1221 == 0) {
						imageRedstone1.blit(22, 10);
					}
					if (anInt1221 == 1) {
						imageRedstone2.blit(54, 8);
					}
					if (anInt1221 == 2) {
						imageRedstone2.blit(82, 8);
					}
					if (anInt1221 == 3) {
						imageRedstone3.blit(110, 8);
					}
					if (anInt1221 == 4) {
						aImage_1147.blit(153, 8);
					}
					if (anInt1221 == 5) {
						aImage_1147.blit(181, 8);
					}
					if (anInt1221 == 6) {
						aImage_1146.blit(209, 9);
					}
				}
				if ((anIntArray1130[0] != -1) && ((anInt1054 != 0) || ((loopCycle % 20) < 10))) {
					imageSideicons[0].blit(29, 13);
				}
				if ((anIntArray1130[1] != -1) && ((anInt1054 != 1) || ((loopCycle % 20) < 10))) {
					imageSideicons[1].blit(53, 11);
				}
				if ((anIntArray1130[2] != -1) && ((anInt1054 != 2) || ((loopCycle % 20) < 10))) {
					imageSideicons[2].blit(82, 11);
				}
				if ((anIntArray1130[3] != -1) && ((anInt1054 != 3) || ((loopCycle % 20) < 10))) {
					imageSideicons[3].blit(115, 12);
				}
				if ((anIntArray1130[4] != -1) && ((anInt1054 != 4) || ((loopCycle % 20) < 10))) {
					imageSideicons[4].blit(153, 13);
				}
				if ((anIntArray1130[5] != -1) && ((anInt1054 != 5) || ((loopCycle % 20) < 10))) {
					imageSideicons[5].blit(180, 11);
				}
				if ((anIntArray1130[6] != -1) && ((anInt1054 != 6) || ((loopCycle % 20) < 10))) {
					imageSideicons[6].blit(208, 13);
				}
			}
			aArea_1125.draw(super.graphics, 516, 160);
			aArea_1124.bind();
			imageBackbase2.blit(0, 0);
			if (anInt1189 == -1) {
				if (anIntArray1130[anInt1221] != -1) {
					if (anInt1221 == 7) {
						aImage_865.blit(42, 0);
					}
					if (anInt1221 == 8) {
						aImage_866.blit(74, 0);
					}
					if (anInt1221 == 9) {
						aImage_866.blit(102, 0);
					}
					if (anInt1221 == 10) {
						aImage_867.blit(130, 1);
					}
					if (anInt1221 == 11) {
						aImage_869.blit(173, 0);
					}
					if (anInt1221 == 12) {
						aImage_869.blit(201, 0);
					}
					if (anInt1221 == 13) {
						aImage_868.blit(229, 0);
					}
				}
				if ((anIntArray1130[8] != -1) && ((anInt1054 != 8) || ((loopCycle % 20) < 10))) {
					imageSideicons[7].blit(74, 2);
				}
				if ((anIntArray1130[9] != -1) && ((anInt1054 != 9) || ((loopCycle % 20) < 10))) {
					imageSideicons[8].blit(102, 3);
				}
				if ((anIntArray1130[10] != -1) && ((anInt1054 != 10) || ((loopCycle % 20) < 10))) {
					imageSideicons[9].blit(137, 4);
				}
				if ((anIntArray1130[11] != -1) && ((anInt1054 != 11) || ((loopCycle % 20) < 10))) {
					imageSideicons[10].blit(174, 2);
				}
				if ((anIntArray1130[12] != -1) && ((anInt1054 != 12) || ((loopCycle % 20) < 10))) {
					imageSideicons[11].blit(201, 2);
				}
				if ((anIntArray1130[13] != -1) && ((anInt1054 != 13) || ((loopCycle % 20) < 10))) {
					imageSideicons[12].blit(226, 2);
				}
			}
			aArea_1124.draw(super.graphics, 496, 466);
			areaViewport.bind();
		}
		if (aBoolean1233) {
			aBoolean1233 = false;
			aArea_1123.bind();
			imageBackbase1.blit(0, 0);
			fontPlain12.drawStringTaggableCenter("Public chat", 55, 28, 0xffffff, true);
			if (anInt1287 == 0) {
				fontPlain12.drawStringTaggableCenter("On", 55, 41, 65280, true);
			}
			if (anInt1287 == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 55, 41, 0xffff00, true);
			}
			if (anInt1287 == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 55, 41, 0xff0000, true);
			}
			if (anInt1287 == 3) {
				fontPlain12.drawStringTaggableCenter("Hide", 55, 41, 65535, true);
			}
			fontPlain12.drawStringTaggableCenter("Private chat", 184, 28, 0xffffff, true);
			if (anInt845 == 0) {
				fontPlain12.drawStringTaggableCenter("On", 184, 41, 65280, true);
			}
			if (anInt845 == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 184, 41, 0xffff00, true);
			}
			if (anInt845 == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 184, 41, 0xff0000, true);
			}
			fontPlain12.drawStringTaggableCenter("Trade/compete", 324, 28, 0xffffff, true);
			if (anInt1248 == 0) {
				fontPlain12.drawStringTaggableCenter("On", 324, 41, 65280, true);
			}
			if (anInt1248 == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 324, 41, 0xffff00, true);
			}
			if (anInt1248 == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 324, 41, 0xff0000, true);
			}
			fontPlain12.drawStringTaggableCenter("Report abuse", 458, 33, 0xffffff, true);
			aArea_1123.draw(super.graphics, 0, 453);
			areaViewport.bind();
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
		SpotAnimEntity spotAnim = (SpotAnimEntity) aList_1056.peekFront();
		for (; spotAnim != null; spotAnim = (SpotAnimEntity) aList_1056.prev()) {
			if ((spotAnim.anInt1560 != currentPlane) || spotAnim.aBoolean1567) {
				spotAnim.unlink();
			} else if (loopCycle >= spotAnim.anInt1564) {
				spotAnim.method454(anInt945);
				if (spotAnim.aBoolean1567) {
					spotAnim.unlink();
				} else {
					scene.addTemporary(spotAnim, spotAnim.anInt1560, spotAnim.anInt1561, spotAnim.anInt1562, spotAnim.anInt1563, 0, -1, false, 60);
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
			Component component_1 = Component.instances[component.anIntArray240[j2]];
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
											class30_sub2_sub1_sub1_2.draw(k5 + k6, j6 + j7, 128);
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
											class30_sub2_sub1_sub1_2.draw(k5, j6, 128);
										} else {
											class30_sub2_sub1_sub1_2.draw(k5, j6);
										}
										if ((class30_sub2_sub1_sub1_2.cropW == 33) || (component_1.anIntArray252[i3] != 1)) {
											int k10 = component_1.anIntArray252[i3];
											fontPlain11.drawString(method43(k10), k5 + 1 + k6, j6 + 10 + j7, 0);
											fontPlain11.drawString(method43(k10), k5 + k6, j6 + 9 + j7, 0xffff00);
										}
									}
								}
							} else if ((component_1.aImageArray209 != null) && (i3 < 20)) {
								Image24 class30_sub2_sub1_sub1_1 = component_1.aImageArray209[i3];
								if (class30_sub2_sub1_sub1_1 != null) {
									class30_sub2_sub1_sub1_1.draw(k5, j6);
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
					for (int l6 = l2 + font.height; s.length() > 0; l6 += font.height) {
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
							font.drawStringTaggableCenter(s1, k2 + (component_1.anInt220 / 2), l6, i4, component_1.aBoolean268);
						} else {
							font.drawStringTaggable(s1, k2, l6, i4, component_1.aBoolean268);
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
						image.draw(k2, l2);
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
						SeqType type = SeqType.instances[i7];
						model = component_1.method209(type.secondaryFrames[component_1.anInt246], type.primaryFrames[component_1.anInt246], flag2);
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
									class30_sub2_sub1_sub4_1.drawStringTaggableCenter(s2, i9 + (component_1.anInt220 / 2), k9, component_1.anInt232, component_1.aBoolean268);
								} else {
									class30_sub2_sub1_sub4_1.drawStringTaggable(s2, i9, k9, component_1.anInt232, component_1.aBoolean268);
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
		Arrays.fill(flameBuffer0, 0);

		for (int l = 0; l < 5000; l++) {
			flameBuffer0[(int) (Math.random() * 128.0 * 256.0)] = (int) (Math.random() * 256D);
		}

		for (int i = 0; i < 20; i++) {
			for (int y = 1; y < 255; y++) {
				for (int x = 1; x < 127; x++) {
					int pos = x + (y << 7);
					flameBuffer1[pos] = (flameBuffer0[pos - 1] + flameBuffer0[pos + 1] + flameBuffer0[pos - 128] + flameBuffer0[pos + 128]) / 4;
				}
			}

			int[] tmp = flameBuffer0;
			flameBuffer0 = flameBuffer1;
			flameBuffer1 = tmp;
		}

		if (image != null) {
			int offset = 0;
			for (int y = 0; y < image.height; y++) {
				for (int x = 0; x < image.width; x++) {
					if (image.pixels[offset++] != 0) {
						int dstX = x + 16 + image.anInt1454;
						int dstY = y + 16 + image.anInt1455;
						flameBuffer0[dstX + (dstY << 7)] = 0;
					}
				}
			}
		}
	}

	public void method107(int i, int j, Buffer buffer, PlayerEntity player) {
		if ((i & 0x400) != 0) {
			player.forceMoveStartSceneTileX = buffer.get1US();
			player.forceMoveStartSceneTileZ = buffer.get1US();
			player.forceMoveEndSceneTileX = buffer.get1US();
			player.forceMoveEndSceneTileZ = buffer.get1US();
			player.forceMoveEndCycle = buffer.get2ULEA() + loopCycle;
			player.forceMoveStartCycle = buffer.get2UA() + loopCycle;
			player.forceMoveFaceDirection = buffer.get1US();
			player.method446();
		}
		if ((i & 0x100) != 0) {
			player.spotanim = buffer.get2ULE();
			int k = buffer.get4();
			player.anInt1524 = k >> 16;
			player.anInt1523 = loopCycle + (k & 0xffff);
			player.spotanimFrame = 0;
			player.spotanimCycle = 0;
			if (player.anInt1523 > loopCycle) {
				player.spotanimFrame = -1;
			}
			if (player.spotanim == 65535) {
				player.spotanim = -1;
			}
		}
		if ((i & 8) != 0) {
			int l = buffer.get2ULE();
			if (l == 65535) {
				l = -1;
			}
			int i2 = buffer.get1UC();
			if ((l == player.anInt1526) && (l != -1)) {
				int i3 = SeqType.instances[l].anInt365;
				if (i3 == 1) {
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if (i3 == 2) {
					player.anInt1530 = 0;
				}
			} else if ((l == -1) || (player.anInt1526 == -1) || (SeqType.instances[l].anInt359 >= SeqType.instances[player.anInt1526].anInt359)) {
				player.anInt1526 = l;
				player.anInt1527 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.pathRemaining;
			}
		}
		if ((i & 4) != 0) {
			player.aString1506 = buffer.getString();
			if (player.aString1506.charAt(0) == '~') {
				player.aString1506 = player.aString1506.substring(1);
				method77(2, player.aString1703, player.aString1506);
			} else if (player == self) {
				method77(2, player.aString1703, player.aString1506);
			}
			player.anInt1513 = 0;
			player.anInt1531 = 0;
			player.anInt1535 = 150;
		}
		if ((i & 0x80) != 0) {
			int i1 = buffer.get2ULE();
			int j2 = buffer.get1U();
			int j3 = buffer.get1UC();
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
						String s = ChatCompression.unpack(j3, aBuffer_834);
						s = Censor.method497(s, 0);
						player.aString1506 = s;
						player.anInt1513 = i1 >> 8;
						player.anInt1531 = i1 & 0xff;
						player.anInt1535 = 150;
						if ((j2 == 2) || (j2 == 3)) {
							method77(1, "@cr2@" + player.aString1703, s);
						} else if (j2 == 1) {
							method77(1, "@cr1@" + player.aString1703, s);
						} else {
							method77(2, player.aString1703, s);
						}
					} catch (Exception exception) {
						Signlink.reporterror("cde2");
					}
				}
			}
			buffer.position = k3 + j3;
		}
		if ((i & 1) != 0) {
			player.index = buffer.get2ULE();
			if (player.index == 65535) {
				player.index = -1;
			}
		}
		if ((i & 0x10) != 0) {
			int j1 = buffer.get1UC();
			byte[] abyte0 = new byte[j1];
			Buffer buffer_1 = new Buffer(abyte0);
			buffer.get(abyte0, 0, j1);
			aBufferArray895[j] = buffer_1;
			player.method451(buffer_1);
		}
		if ((i & 2) != 0) {
			player.faceTileX = buffer.get2ULEA();
			player.faceTileZ = buffer.get2ULE();
		}
		if ((i & 0x20) != 0) {
			int k1 = buffer.get1U();
			int k2 = buffer.get1UA();
			player.method447(k2, k1, loopCycle);
			player.anInt1532 = loopCycle + 300;
			player.anInt1533 = buffer.get1UC();
			player.anInt1534 = buffer.get1U();
		}
		if ((i & 0x200) != 0) {
			int l1 = buffer.get1U();
			int l2 = buffer.get1US();
			player.method447(l2, l1, loopCycle);
			player.anInt1532 = loopCycle + 300;
			player.anInt1533 = buffer.get1U();
			player.anInt1534 = buffer.get1UC();
		}
	}

	public void method108() {
		int j = self.x + anInt1278;
		int k = self.z + anInt1131;
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
		if (super.actionKey[1] == 1) {
			anInt1186 += (-24 - anInt1186) / 2;
		} else if (super.actionKey[2] == 1) {
			anInt1186 += (24 - anInt1186) / 2;
		} else {
			anInt1186 /= 2;
		}
		if (super.actionKey[3] == 1) {
			anInt1187 += (12 - anInt1187) / 2;
		} else if (super.actionKey[4] == 1) {
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
		int j1 = getHeightmapY(currentPlane, anInt1014, anInt1015);
		int k1 = 0;
		if ((l > 3) && (i1 > 3) && (l < 100) && (i1 < 100)) {
			for (int l1 = l - 4; l1 <= (l + 4); l1++) {
				for (int k2 = i1 - 4; k2 <= (i1 + 4); k2++) {
					int l2 = currentPlane;
					if ((l2 < 3) && ((planeTileFlags[1][l1][k2] & 2) == 2)) {
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
		return s.equalsIgnoreCase(self.aString1703);
	}

	public void method111(int i) {
		Signlink.wavevol = i;
	}

	public void method112() {
		method76();
		if (anInt917 == 1) {
			imageCrosses[anInt916 / 100].draw(anInt914 - 8 - 4, anInt915 - 8 - 4);
		}
		if (anInt917 == 2) {
			imageCrosses[4 + (anInt916 / 100)].draw(anInt914 - 8 - 4, anInt915 - 8 - 4);
		}
		if (anInt1018 != -1) {
			method119(anInt945, anInt1018);
			method105(0, 0, Component.instances[anInt1018], 0);
		}
		if (anInt857 != -1) {
			method119(anInt945, anInt857);
			method105(0, 0, Component.instances[anInt857], 0);
		}
		method70();
		if (!aBoolean885) {
			method82();
			method125();
		} else if (anInt948 == 0) {
			method40();
		}
		if (anInt1055 == 1) {
			imageHeadicons[1].draw(472, 296);
		}
		if (aBoolean1156) {
			char c = '\u01FB';
			int k = 20;
			int i1 = 0xffff00;
			if (super.fps < 15) {
				i1 = 0xff0000;
			}
			fontPlain12.drawStringRight("Fps:" + super.fps, c, k, i1);
			k += 15;
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			if ((j1 > 0x2000000) && lowmem) {
			}
			fontPlain12.drawStringRight("Mem:" + j1 + "k", c, k, 0xffff00);
		}
		if (anInt1104 != 0) {
			int j = anInt1104 / 50;
			int l = j / 60;
			j %= 60;
			if (j < 10) {
				fontPlain12.drawString("System update in: " + l + ":0" + j, 4, 329, 0xffff00);
			} else {
				fontPlain12.drawString("System update in: " + l + ":" + j, 4, 329, 0xffff00);
			}
		}
	}

	public void method113(long l) {
		if (l == 0L) {
			return;
		}
		if (anInt822 >= 100) {
			method77(0, "", "Your ignore list is full. Max of 100 hit");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(l));
		for (int j = 0; j < anInt822; j++) {
			if (aLongArray925[j] == l) {
				method77(0, "", s + " is already on your ignore list");
				return;
			}
		}
		for (int k = 0; k < anInt899; k++) {
			if (aLongArray955[k] == l) {
				method77(0, "", "Please remove " + s + " from your friend list first");
				return;
			}
		}
		aLongArray925[anInt822++] = l;
		aBoolean1153 = true;
		aBuffer_1192.putOp(133);
		aBuffer_1192.put8(l);
	}

	public void method114() {
		for (int i = -1; i < anInt891; i++) {
			int j;
			if (i == -1) {
				j = LOCAL_PLAYER_INDEX;
			} else {
				j = anIntArray892[i];
			}
			PlayerEntity player = players[j];
			if (player != null) {
				method96(player);
			}
		}
	}

	public void method115() {
		if (sceneState == 2) {
			for (SceneLocTemporary loc = (SceneLocTemporary) listTemporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) listTemporaryLocs.prev()) {
				if (loc.anInt1294 > 0) {
					loc.anInt1294--;
				}
				if (loc.anInt1294 == 0) {
					if ((loc.anInt1299 < 0) || SceneBuilder.method178(loc.anInt1299, loc.anInt1301)) {
						method142(loc.sceneTileZ, loc.anInt1295, loc.anInt1300, loc.anInt1301, loc.sceneTileX, loc.anInt1296, loc.anInt1299);
						loc.unlink();
					}
				} else {
					if (loc.anInt1302 > 0) {
						loc.anInt1302--;
					}
					if ((loc.anInt1302 == 0) && (loc.sceneTileX >= 1) && (loc.sceneTileZ >= 1) && (loc.sceneTileX <= 102) && (loc.sceneTileZ <= 102) && ((loc.anInt1291 < 0) || SceneBuilder.method178(loc.anInt1291, loc.anInt1293))) {
						method142(loc.sceneTileZ, loc.anInt1295, loc.anInt1292, loc.anInt1293, loc.sceneTileX, loc.anInt1296, loc.anInt1291);
						loc.anInt1302 = -1;
						if ((loc.anInt1291 == loc.anInt1299) && (loc.anInt1299 == -1)) {
							loc.unlink();
						} else if ((loc.anInt1291 == loc.anInt1299) && (loc.anInt1292 == loc.anInt1300) && (loc.anInt1293 == loc.anInt1301)) {
							loc.unlink();
						}
					}
				}
			}
		}
	}

	public void method116() {
		int i = fontBold12.stringWidthTaggable("Choose Option");
		for (int j = 0; j < anInt1133; j++) {
			int k = fontBold12.stringWidthTaggable(aStringArray1199[j]);
			if (k > i) {
				i = k;
			}
		}
		i += 8;
		int l = (15 * anInt1133) + 21;
		if ((super.mousePressX > 4) && (super.mousePressY > 4) && (super.mousePressX < 516) && (super.mousePressY < 338)) {
			int i1 = super.mousePressX - 4 - (i / 2);
			if ((i1 + i) > 512) {
				i1 = 512 - i;
			}
			if (i1 < 0) {
				i1 = 0;
			}
			int l1 = super.mousePressY - 4;
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
		if ((super.mousePressX > 553) && (super.mousePressY > 205) && (super.mousePressX < 743) && (super.mousePressY < 466)) {
			int j1 = super.mousePressX - 553 - (i / 2);
			if (j1 < 0) {
				j1 = 0;
			} else if ((j1 + i) > 190) {
				j1 = 190 - i;
			}
			int i2 = super.mousePressY - 205;
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
		if ((super.mousePressX > 17) && (super.mousePressY > 357) && (super.mousePressX < 496) && (super.mousePressY < 453)) {
			int k1 = super.mousePressX - 17 - (i / 2);
			if (k1 < 0) {
				k1 = 0;
			} else if ((k1 + i) > 479) {
				k1 = 479 - i;
			}
			int j2 = super.mousePressY - 357;
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
		int j = buffer.getBits(1);
		if (j == 0) {
			return;
		}
		int k = buffer.getBits(2);
		if (k == 0) {
			anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			return;
		}
		if (k == 1) {
			int l = buffer.getBits(3);
			self.method448(false, l);
			int k1 = buffer.getBits(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			return;
		}
		if (k == 2) {
			int i1 = buffer.getBits(3);
			self.method448(true, i1);
			int l1 = buffer.getBits(3);
			self.method448(true, l1);
			int j2 = buffer.getBits(1);
			if (j2 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			return;
		}
		if (k == 3) {
			currentPlane = buffer.getBits(2);
			int j1 = buffer.getBits(1);
			int i2 = buffer.getBits(1);
			if (i2 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			int k2 = buffer.getBits(7);
			int l2 = buffer.getBits(7);
			self.move(l2, k2, j1 == 1);
		}
	}

	public void method118() {
		flameActive = false;
		while (flameThread) {
			flameActive = false;
			try {
				Thread.sleep(50L);
			} catch (Exception ignored) {
			}
		}
		imageTitlebox = null;
		imageTitlebutton = null;
		imageRunes = null;
		flameGradient = null;
		flameGradient0 = null;
		flameGradient1 = null;
		flameGradient2 = null;
		flameBuffer0 = null;
		flameBuffer1 = null;
		flameBuffer3 = null;
		flameBuffer2 = null;
		imageFlamesLeft = null;
		imageFlamesRight = null;
	}

	public boolean method119(int i, int j) {
		boolean flag1 = false;
		Component component = Component.instances[j];
		for (int k = 0; k < component.anIntArray240.length; k++) {
			if (component.anIntArray240[k] == -1) {
				break;
			}
			Component component_1 = Component.instances[component.anIntArray240[k]];
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
					SeqType type = SeqType.instances[l];
					for (component_1.anInt208 += i; component_1.anInt208 > type.getFrameDelay(component_1.anInt246); ) {
						component_1.anInt208 -= type.getFrameDelay(component_1.anInt246) + 1;
						component_1.anInt246++;
						if (component_1.anInt246 >= type.frameCount) {
							component_1.anInt246 -= type.anInt356;
							if ((component_1.anInt246 < 0) || (component_1.anInt246 >= type.frameCount)) {
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
			int i1 = self.x >> 7;
			int j1 = self.z >> 7;
			if ((planeTileFlags[currentPlane][k][l] & 4) != 0) {
				j = currentPlane;
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
					if ((planeTileFlags[currentPlane][k][l] & 4) != 0) {
						j = currentPlane;
					}
					k2 += i2;
					if (k2 >= 0x10000) {
						k2 -= 0x10000;
						if (l < j1) {
							l++;
						} else if (l > j1) {
							l--;
						}
						if ((planeTileFlags[currentPlane][k][l] & 4) != 0) {
							j = currentPlane;
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
					if ((planeTileFlags[currentPlane][k][l] & 4) != 0) {
						j = currentPlane;
					}
					l2 += j2;
					if (l2 >= 0x10000) {
						l2 -= 0x10000;
						if (k < i1) {
							k++;
						} else if (k > i1) {
							k--;
						}
						if ((planeTileFlags[currentPlane][k][l] & 4) != 0) {
							j = currentPlane;
						}
					}
				}
			}
		}
		if ((planeTileFlags[currentPlane][self.x >> 7][self.z >> 7] & 4) != 0) {
			j = currentPlane;
		}
		return j;
	}

	public int method121() {
		int j = getHeightmapY(currentPlane, anInt858, anInt860);
		if (((j - anInt859) < 800) && ((planeTileFlags[currentPlane][anInt858 >> 7][anInt860 >> 7] & 4) != 0)) {
			return currentPlane;
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
				aBuffer_1192.put8(l);
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
					Component component_1 = Component.instances[ai[l++]];
					int k2 = ai[l++];
					if ((k2 >= 0) && (k2 < ObjType.anInt203) && (!ObjType.method198(k2).aBoolean161 || members)) {
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
					k1 = levelExperience[anIntArray1044[ai[l++]] - 1];
				}
				if (j1 == 7) {
					k1 = (anIntArray971[ai[l++]] * 100) / 46875;
				}
				if (j1 == 8) {
					k1 = self.anInt1705;
				}
				if (j1 == 9) {
					for (int l1 = 0; l1 < SkillConstants.anInt733; l1++) {
						if (SkillConstants.aBooleanArray735[l1]) {
							k1 += anIntArray1044[l1];
						}
					}
				}
				if (j1 == 10) {
					Component component_2 = Component.instances[ai[l++]];
					int l2 = ai[l++] + 1;
					if ((l2 >= 0) && (l2 < ObjType.anInt203) && (!ObjType.method198(l2).aBoolean161 || members)) {
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
					VarbitType varbit = VarbitType.instances[j2];
					int l3 = varbit.varp;
					int i4 = varbit.lsb;
					int j4 = varbit.msb;
					int k4 = BITMASK[j4 - i4];
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
					k1 = (self.x >> 7) + sceneBaseTileX;
				}
				if (j1 == 19) {
					k1 = (self.z >> 7) + sceneBaseTileZ;
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
		fontBold12.drawStringTooltip(s, 4, 15, 0xffffff, true, loopCycle / 1000);
	}

	public void method126() {
		aArea_1164.bind();

		if (minimapState == 2) {
			byte[] mapback = imageMapback.pixels;
			int[] pixels = Draw2D.pixels;
			for (int i = 0; i < mapback.length; i++) {
				if (mapback[i] == 0) {
					pixels[i] = 0;
				}
			}
			imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, anInt1185, compassMaskLineLengths, compassMaskLineOffsets);
			areaViewport.bind();
			return;
		}

		int i = (anInt1185 + anInt1209) & 0x7ff;
		int j = 48 + (self.x / 32);
		int l2 = 464 - (self.z / 32);

		imageMinimap.drawRotatedMasked(25, 5, 146, 151, j, l2, 256 + minimapZoom, i, minimapMaskLineLengths, minimapMaskLineOffsets);
		imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, anInt1185, compassMaskLineLengths, compassMaskLineOffsets);

		for (int j5 = 0; j5 < activeMapFunctionCount; j5++) {
			int k = ((activeMapFunctionX[j5] * 4) + 2) - (self.x / 32);
			int i3 = ((activeMapFunctionZ[j5] * 4) + 2) - (self.z / 32);
			method141(activeMapFunctions[j5], k, i3);
		}

		for (int k5 = 0; k5 < 104; k5++) {
			for (int l5 = 0; l5 < 104; l5++) {
				DoublyLinkedList list = aListArrayArrayArray827[currentPlane][k5][l5];
				if (list != null) {
					int l = ((k5 * 4) + 2) - (self.x / 32);
					int j3 = ((l5 * 4) + 2) - (self.z / 32);
					method141(imageMapdot0, l, j3);
				}
			}
		}
		for (int i6 = 0; i6 < anInt836; i6++) {
			NPCEntity npc = npcs[anIntArray837[i6]];
			if ((npc != null) && npc.method449()) {
				NPCType type = npc.type;
				if (type.overrides != null) {
					type = type.getOverrideType();
				}
				if ((type != null) && type.aBoolean87 && type.aBoolean84) {
					int i1 = (npc.x / 32) - (self.x / 32);
					int k3 = (npc.z / 32) - (self.z / 32);
					method141(imageMapdot1, i1, k3);
				}
			}
		}
		for (int j6 = 0; j6 < anInt891; j6++) {
			PlayerEntity player = players[anIntArray892[j6]];
			if ((player != null) && player.method449()) {
				int j1 = (player.x / 32) - (self.x / 32);
				int l3 = (player.z / 32) - (self.z / 32);
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
				if ((player.anInt1701 != 0) && (self.anInt1701 == player.anInt1701)) {
					flag2 = true;
				}
				if (flag1) {
					method141(imageMapdot3, j1, l3);
				} else if (flag2) {
					method141(imageMapdot4, j1, l3);
				} else {
					method141(imageMapdot2, j1, l3);
				}
			}
		}
		if ((anInt855 != 0) && ((loopCycle % 20) < 10)) {
			if ((anInt855 == 1) && (anInt1222 >= 0) && (anInt1222 < npcs.length)) {
				NPCEntity class30_sub2_sub4_sub1_sub1_1 = npcs[anInt1222];
				if (class30_sub2_sub4_sub1_sub1_1 != null) {
					int k1 = (class30_sub2_sub4_sub1_sub1_1.x / 32) - (self.x / 32);
					int i4 = (class30_sub2_sub4_sub1_sub1_1.z / 32) - (self.z / 32);
					method81(imageMapmarker1, i4, k1);
				}
			}
			if (anInt855 == 2) {
				int l1 = (((anInt934 - sceneBaseTileX) * 4) + 2) - (self.x / 32);
				int j4 = (((anInt935 - sceneBaseTileZ) * 4) + 2) - (self.z / 32);
				method81(imageMapmarker1, j4, l1);
			}
			if ((anInt855 == 10) && (anInt933 >= 0) && (anInt933 < players.length)) {
				PlayerEntity class30_sub2_sub4_sub1_sub2_1 = players[anInt933];
				if (class30_sub2_sub4_sub1_sub2_1 != null) {
					int i2 = (class30_sub2_sub4_sub1_sub2_1.x / 32) - (self.x / 32);
					int k4 = (class30_sub2_sub4_sub1_sub2_1.z / 32) - (self.z / 32);
					method81(imageMapmarker1, k4, i2);
				}
			}
		}
		if (anInt1261 != 0) {
			int j2 = ((anInt1261 * 4) + 2) - (self.x / 32);
			int l4 = ((anInt1262 * 4) + 2) - (self.z / 32);
			method141(imageMapmarker0, j2, l4);
		}
		Draw2D.fillRect(97, 78, 3, 3, 0xffffff);
		areaViewport.bind();
	}

	public void method127(PathingEntity entity, int i) {
		method128(entity.x, i, entity.z);
	}

	public void method128(int i, int j, int l) {
		if ((i < 128) || (l < 128) || (i > 13056) || (l > 13056)) {
			anInt963 = -1;
			anInt964 = -1;
			return;
		}
		int i1 = getHeightmapY(currentPlane, i, l) - j;
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
						int i1 = fontPlain12.stringWidthTaggable("From:  " + s + aStringArray944[j]) + 25;
						if (i1 > 450) {
							i1 = 450;
						}
						if (super.anInt20 < (4 + i1)) {
							if (rights >= 1) {
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
		for (SceneLocTemporary loc_1 = (SceneLocTemporary) listTemporaryLocs.peekFront(); loc_1 != null; loc_1 = (SceneLocTemporary) listTemporaryLocs.prev()) {
			if ((loc_1.anInt1295 != l1) || (loc_1.sceneTileX != i2) || (loc_1.sceneTileZ != j1) || (loc_1.anInt1296 != i1)) {
				continue;
			}
			loc = loc_1;
			break;
		}
		if (loc == null) {
			loc = new SceneLocTemporary();
			loc.anInt1295 = l1;
			loc.anInt1296 = i1;
			loc.sceneTileX = i2;
			loc.sceneTileZ = j1;
			method89(loc);
			listTemporaryLocs.pushBack(loc);
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

	public DataInputStream openURL(String s) throws IOException {
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

	public void drawFlames() {
		int height = 256;

		if (flameGradientCycle0 > 0) {
			for (int i = 0; i < 256; i++) {
				if (flameGradientCycle0 > 768) {
					flameGradient[i] = mix(flameGradient0[i], flameGradient1[i], 1024 - flameGradientCycle0);
				} else if (flameGradientCycle0 > 256) {
					flameGradient[i] = flameGradient1[i];
				} else {
					flameGradient[i] = mix(flameGradient1[i], flameGradient0[i], 256 - flameGradientCycle0);
				}
			}
		} else if (flameGradientCycle1 > 0) {
			for (int j = 0; j < 256; j++) {
				if (flameGradientCycle1 > 768) {
					flameGradient[j] = mix(flameGradient0[j], flameGradient2[j], 1024 - flameGradientCycle1);
				} else if (flameGradientCycle1 > 256) {
					flameGradient[j] = flameGradient2[j];
				} else {
					flameGradient[j] = mix(flameGradient2[j], flameGradient0[j], 256 - flameGradientCycle1);
				}
			}
		} else {
			System.arraycopy(flameGradient0, 0, flameGradient, 0, 256);
		}

		System.arraycopy(imageFlamesLeft.pixels, 0, imageTitle0.pixels, 0, 33920);

		int srcOffset = 0;
		int dstOffset = 1152;

		for (int y = 1; y < (height - 1); y++) {
			int offset = (flameLineOffset[y] * (height - y)) / height;
			int step = 22 + offset;

			if (step < 0) {
				step = 0;
			}

			srcOffset += step;

			for (int x = step; x < 128; x++) {
				int value = flameBuffer3[srcOffset++];

				if (value != 0) {
					int alpha = value;
					int invAlpha = 256 - value;

					value = flameGradient[value];

					int background = imageTitle0.pixels[dstOffset];

					imageTitle0.pixels[dstOffset++] = (((((value & 0xff00ff) * alpha) + ((background & 0xff00ff) * invAlpha)) & 0xff00ff00) + ((((value & 0xff00) * alpha) + ((background & 0xff00) * invAlpha)) & 0xff0000)) >> 8;
				} else {
					dstOffset++;
				}
			}

			dstOffset += step;
		}

		imageTitle0.draw(super.graphics, 0, 0);
		System.arraycopy(imageFlamesRight.pixels, 0, imageTitle1.pixels, 0, 33920);

		srcOffset = 0;
		dstOffset = 1176;

		for (int y = 1; y < (height - 1); y++) {
			int offset = (flameLineOffset[y] * (height - y)) / height;
			int step = 103 - offset;

			dstOffset += offset;

			for (int i4 = 0; i4 < step; i4++) {
				int value = flameBuffer3[srcOffset++];

				if (value != 0) {
					int alpha = value;
					int invAlpha = 256 - value;
					value = flameGradient[value];

					int background = imageTitle1.pixels[dstOffset];

					imageTitle1.pixels[dstOffset++] = (((((value & 0xff00ff) * alpha) + ((background & 0xff00ff) * invAlpha)) & 0xff00ff00) + ((((value & 0xff00) * alpha) + ((background & 0xff00) * invAlpha)) & 0xff0000)) >> 8;
				} else {
					dstOffset++;
				}
			}
			srcOffset += 128 - step;
			dstOffset += 128 - step - offset;
		}

		imageTitle1.draw(super.graphics, 637, 0);
	}

	public void method134(Buffer buffer) {
		int j = buffer.getBits(8);
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
			PlayerEntity player = players[i1];
			int j1 = buffer.getBits(1);
			if (j1 == 0) {
				anIntArray892[anInt891++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = buffer.getBits(2);
				if (k1 == 0) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = buffer.getBits(3);
					player.method448(false, l1);
					int j2 = buffer.getBits(1);
					if (j2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 2) {
					anIntArray892[anInt891++] = i1;
					player.anInt1537 = loopCycle;
					int i2 = buffer.getBits(3);
					player.method448(true, i2);
					int k2 = buffer.getBits(3);
					player.method448(true, k2);
					int l2 = buffer.getBits(1);
					if (l2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 3) {
					anIntArray840[anInt839++] = i1;
				}
			}
		}
	}

	public void method135(boolean flag) throws IOException {
		method64();
		imageTitle4.bind();
		imageTitlebox.blit(0, 0);
		char c = '\u0168';
		char c1 = '\310';
		if (anInt833 == 0) {
			int i = (c1 / 2) + 80;
			fontPlain11.drawStringTaggableCenter(ondemand.message, c / 2, i, 0x75a9a9, true);
			i = (c1 / 2) - 20;
			fontBold12.drawStringTaggableCenter("Welcome to RuneScape", c / 2, i, 0xffff00, true);
			int l = (c / 2) - 80;
			int k1 = (c1 / 2) + 20;
			imageTitlebutton.blit(l - 73, k1 - 20);
			fontBold12.drawStringTaggableCenter("New User", l, k1 + 5, 0xffffff, true);
			l = (c / 2) + 80;
			imageTitlebutton.blit(l - 73, k1 - 20);
			fontBold12.drawStringTaggableCenter("Existing User", l, k1 + 5, 0xffffff, true);
		}
		if (anInt833 == 2) {
			int j = (c1 / 2) - 40;
			if (aString1266.length() > 0) {
				fontBold12.drawStringTaggableCenter(aString1266, c / 2, j - 15, 0xffff00, true);
				fontBold12.drawStringTaggableCenter(aString1267, c / 2, j, 0xffff00, true);
			} else {
				fontBold12.drawStringTaggableCenter(aString1267, c / 2, j - 7, 0xffff00, true);
			}
			j += 30;
			fontBold12.drawStringTaggable("Username: " + aString1173 + ((anInt1216 == 0) & (loopCycle % 40 < 20) ? "@yel@|" : ""), (c / 2) - 90, j, 0xffffff, true);
			j += 15;
			fontBold12.drawStringTaggable("Password: " + StringUtil.toAsterisks(aString1174) + ((anInt1216 == 1) & (loopCycle % 40 < 20) ? "@yel@|" : ""), (c / 2) - 88, j, 0xffffff, true);
			if (!flag) {
				int i1 = (c / 2) - 80;
				int l1 = (c1 / 2) + 50;
				imageTitlebutton.blit(i1 - 73, l1 - 20);
				fontBold12.drawStringTaggableCenter("Login", i1, l1 + 5, 0xffffff, true);
				i1 = (c / 2) + 80;
				imageTitlebutton.blit(i1 - 73, l1 - 20);
				fontBold12.drawStringTaggableCenter("Cancel", i1, l1 + 5, 0xffffff, true);
			}
		}
		if (anInt833 == 3) {
			fontBold12.drawStringTaggableCenter("Create a free account", c / 2, (c1 / 2) - 60, 0xffff00, true);
			int k = (c1 / 2) - 35;
			fontBold12.drawStringTaggableCenter("To create a new account you need to", c / 2, k, 0xffffff, true);
			k += 15;
			fontBold12.drawStringTaggableCenter("go back to the main RuneScape webpage", c / 2, k, 0xffffff, true);
			k += 15;
			fontBold12.drawStringTaggableCenter("and choose the red 'create account'", c / 2, k, 0xffffff, true);
			k += 15;
			fontBold12.drawStringTaggableCenter("button at the top right of that page.", c / 2, k, 0xffffff, true);
			int j1 = c / 2;
			int i2 = (c1 / 2) + 50;
			imageTitlebutton.blit(j1 - 73, i2 - 20);
			fontBold12.drawStringTaggableCenter("Cancel", j1, i2 + 5, 0xffffff, true);
		}
		imageTitle4.draw(super.graphics, 202, 171);
		if (aBoolean1255) {
			aBoolean1255 = false;
			imageTitle2.draw(super.graphics, 128, 0);
			imageTitle3.draw(super.graphics, 202, 371);
			imageTitle5.draw(super.graphics, 0, 265);
			imageTitle6.draw(super.graphics, 562, 265);
			imageTitle7.draw(super.graphics, 128, 171);
			imageTitle8.draw(super.graphics, 562, 171);
		}
	}

	public void method136() {
		flameThread = true;

			while (flameActive) {
				flameCycle++;

				updateFlames();
				drawFlames();

				// the code here originally was some sort of lag detection code. It's not very useful anymore, and
				// made the flames look choppier.

				try {
					Thread.sleep(20); // 50fps seems fine
				} catch (Exception ignored) {
				}
			}

		flameThread = false;
	}

	public void method137(Buffer buffer, int j) {
		if (j == 84) {
			int k = buffer.get1U();
			int j3 = anInt1268 + ((k >> 4) & 7);
			int i6 = anInt1269 + (k & 7);
			int l8 = buffer.get2U();
			int k11 = buffer.get2U();
			int l13 = buffer.get2U();
			if ((j3 >= 0) && (i6 >= 0) && (j3 < 104) && (i6 < 104)) {
				DoublyLinkedList list_1 = aListArrayArrayArray827[currentPlane][j3][i6];
				if (list_1 != null) {
					for (ObjStackEntity objStack_3 = (ObjStackEntity) list_1.peekFront(); objStack_3 != null; objStack_3 = (ObjStackEntity) list_1.prev()) {
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
			int l = buffer.get1U();
			int k3 = anInt1268 + ((l >> 4) & 7);
			int j6 = anInt1269 + (l & 7);
			int i9 = buffer.get2U();
			int l11 = buffer.get1U();
			int i14 = (l11 >> 4) & 0xf;
			int i16 = l11 & 7;
			if ((self.pathTileX[0] >= (k3 - i14)) && (self.pathTileX[0] <= (k3 + i14)) && (self.pathTileZ[0] >= (j6 - i14)) && (self.pathTileZ[0] <= (j6 + i14)) && aBoolean848 && !lowmem && (anInt1062 < 50)) {
				anIntArray1207[anInt1062] = i9;
				anIntArray1241[anInt1062] = i16;
				anIntArray1250[anInt1062] = SoundTrack.delays[i9];
				anInt1062++;
			}
		}
		if (j == 215) {
			int i1 = buffer.get2UA();
			int l3 = buffer.get1US();
			int k6 = anInt1268 + ((l3 >> 4) & 7);
			int j9 = anInt1269 + (l3 & 7);
			int i12 = buffer.get2UA();
			int j14 = buffer.get2U();
			if ((k6 >= 0) && (j9 >= 0) && (k6 < 104) && (j9 < 104) && (i12 != selfPlayerId)) {
				ObjStackEntity class30_sub2_sub4_sub2_2 = new ObjStackEntity();
				class30_sub2_sub4_sub2_2.anInt1558 = i1;
				class30_sub2_sub4_sub2_2.anInt1559 = j14;
				if (aListArrayArrayArray827[currentPlane][k6][j9] == null) {
					aListArrayArrayArray827[currentPlane][k6][j9] = new DoublyLinkedList();
				}
				aListArrayArrayArray827[currentPlane][k6][j9].pushBack(class30_sub2_sub4_sub2_2);
				method25(k6, j9);
			}
			return;
		}
		if (j == 156) {
			int j1 = buffer.get1UA();
			int i4 = anInt1268 + ((j1 >> 4) & 7);
			int l6 = anInt1269 + (j1 & 7);
			int k9 = buffer.get2U();
			if ((i4 >= 0) && (l6 >= 0) && (i4 < 104) && (l6 < 104)) {
				DoublyLinkedList list = aListArrayArrayArray827[currentPlane][i4][l6];
				if (list != null) {
					for (ObjStackEntity objStack = (ObjStackEntity) list.peekFront(); objStack != null; objStack = (ObjStackEntity) list.prev()) {
						if (objStack.anInt1558 != (k9 & 0x7fff)) {
							continue;
						}
						objStack.unlink();
						break;
					}
					if (list.peekFront() == null) {
						aListArrayArrayArray827[currentPlane][i4][l6] = null;
					}
					method25(i4, l6);
				}
			}
			return;
		}
		if (j == 160) {
			int k1 = buffer.get1US();
			int j4 = anInt1268 + ((k1 >> 4) & 7);
			int i7 = anInt1269 + (k1 & 7);
			int l9 = buffer.get1US();
			int j12 = l9 >> 2;
			int k14 = l9 & 3;
			int j16 = anIntArray1177[j12];
			int j17 = buffer.get2UA();
			if ((j4 >= 0) && (i7 >= 0) && (j4 < 103) && (i7 < 103)) {
				int j18 = anIntArrayArrayArray1214[currentPlane][j4][i7];
				int i19 = anIntArrayArrayArray1214[currentPlane][j4 + 1][i7];
				int l19 = anIntArrayArrayArray1214[currentPlane][j4 + 1][i7 + 1];
				int k20 = anIntArrayArrayArray1214[currentPlane][j4][i7 + 1];
				if (j16 == 0) {
					SceneWall wall = scene.getWall(currentPlane, j4, i7);
					if (wall != null) {
						int k21 = (wall.bitset >> 14) & 0x7fff;
						if (j12 == 2) {
							wall.entity0 = new LocEntity(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
							wall.entity1 = new LocEntity(k21, (k14 + 1) & 3, 2, i19, l19, j18, k20, j17, false);
						} else {
							wall.entity0 = new LocEntity(k21, k14, j12, i19, l19, j18, k20, j17, false);
						}
					}
				}
				if (j16 == 1) {
					SceneWallDecoration wallDecoration = scene.getWallDecoration(currentPlane, j4, i7);
					if (wallDecoration != null) {
						wallDecoration.entity = new LocEntity((wallDecoration.bitset >> 14) & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
					}
				}
				if (j16 == 2) {
					SceneLoc loc = scene.getLoc(currentPlane, j4, i7);
					if (j12 == 11) {
						j12 = 10;
					}
					if (loc != null) {
						loc.entity = new LocEntity((loc.bitset >> 14) & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
					}
				}
				if (j16 == 3) {
					SceneGroundDecoration groundDecoration = scene.getGroundDecoration(i7, j4, currentPlane);
					if (groundDecoration != null) {
						groundDecoration.entity = new LocEntity((groundDecoration.bitset >> 14) & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
					}
				}
			}
			return;
		}
		if (j == 147) {
			int l1 = buffer.get1US();
			int k4 = anInt1268 + ((l1 >> 4) & 7);
			int j7 = anInt1269 + (l1 & 7);
			int i10 = buffer.get2U();
			byte byte0 = buffer.get1S();
			int l14 = buffer.get2ULE();
			byte byte1 = buffer.get1C();
			int k17 = buffer.get2U();
			int k18 = buffer.get1US();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = anIntArray1177[j19];
			byte byte2 = buffer.get1();
			int l21 = buffer.get2U();
			byte byte3 = buffer.get1C();
			PlayerEntity player;
			if (i10 == selfPlayerId) {
				player = self;
			} else {
				player = players[i10];
			}
			if (player != null) {
				LocType type = LocType.method572(l21);
				int i22 = anIntArrayArrayArray1214[currentPlane][k4][j7];
				int j22 = anIntArrayArrayArray1214[currentPlane][k4 + 1][j7];
				int k22 = anIntArrayArrayArray1214[currentPlane][k4 + 1][j7 + 1];
				int l22 = anIntArrayArrayArray1214[currentPlane][k4][j7 + 1];
				Model model = type.method578(j19, i20, i22, j22, k22, l22, -1);
				if (model != null) {
					method130(k17 + 1, -1, 0, l20, j7, 0, currentPlane, k4, l14 + 1);
					player.anInt1707 = l14 + loopCycle;
					player.anInt1708 = k17 + loopCycle;
					player.model = model;
					int i23 = type.anInt744;
					int j23 = type.anInt761;
					if ((i20 == 1) || (i20 == 3)) {
						i23 = type.anInt761;
						j23 = type.anInt744;
					}
					player.anInt1711 = (k4 * 128) + (i23 * 64);
					player.anInt1713 = (j7 * 128) + (j23 * 64);
					player.anInt1712 = getHeightmapY(currentPlane, player.anInt1711, player.anInt1713);
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
					player.minSceneTileX = k4 + byte2;
					player.maxSceneTileX = k4 + byte0;
					player.minSceneTileZ = j7 + byte3;
					player.maxSceneTileZ = j7 + byte1;
				}
			}
		}
		if (j == 151) {
			int i2 = buffer.get1UA();
			int l4 = anInt1268 + ((i2 >> 4) & 7);
			int k7 = anInt1269 + (i2 & 7);
			int j10 = buffer.get2ULE();
			int k12 = buffer.get1US();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = anIntArray1177[i15];
			if ((l4 >= 0) && (k7 >= 0) && (l4 < 104) && (k7 < 104)) {
				method130(-1, j10, k16, l17, k7, i15, currentPlane, l4, 0);
			}
			return;
		}
		if (j == 4) {
			int j2 = buffer.get1U();
			int i5 = anInt1268 + ((j2 >> 4) & 7);
			int l7 = anInt1269 + (j2 & 7);
			int k10 = buffer.get2U();
			int l12 = buffer.get1U();
			int j15 = buffer.get2U();
			if ((i5 >= 0) && (l7 >= 0) && (i5 < 104) && (l7 < 104)) {
				i5 = (i5 * 128) + 64;
				l7 = (l7 * 128) + 64;
				SpotAnimEntity spotAnim = new SpotAnimEntity(currentPlane, loopCycle, j15, k10, getHeightmapY(currentPlane, i5, l7) - l12, l7, i5);
				aList_1056.pushBack(spotAnim);
			}
			return;
		}
		if (j == 44) {
			int k2 = buffer.get2ULEA();
			int j5 = buffer.get2U();
			int i8 = buffer.get1U();
			int l10 = anInt1268 + ((i8 >> 4) & 7);
			int i13 = anInt1269 + (i8 & 7);
			if ((l10 >= 0) && (i13 >= 0) && (l10 < 104) && (i13 < 104)) {
				ObjStackEntity objStack_1 = new ObjStackEntity();
				objStack_1.anInt1558 = k2;
				objStack_1.anInt1559 = j5;
				if (aListArrayArrayArray827[currentPlane][l10][i13] == null) {
					aListArrayArrayArray827[currentPlane][l10][i13] = new DoublyLinkedList();
				}
				aListArrayArrayArray827[currentPlane][l10][i13].pushBack(objStack_1);
				method25(l10, i13);
			}
			return;
		}
		if (j == 101) {
			int l2 = buffer.get1UC();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = anIntArray1177[k5];
			int j13 = buffer.get1U();
			int k15 = anInt1268 + ((j13 >> 4) & 7);
			int l16 = anInt1269 + (j13 & 7);
			if ((k15 >= 0) && (l16 >= 0) && (k15 < 104) && (l16 < 104)) {
				method130(-1, -1, j8, i11, l16, k5, currentPlane, k15, 0);
			}
			return;
		}
		if (j == 117) {
			int i3 = buffer.get1U();
			int l5 = anInt1268 + ((i3 >> 4) & 7);
			int k8 = anInt1269 + (i3 & 7);
			int j11 = l5 + buffer.get1();
			int k13 = k8 + buffer.get1();
			int l15 = buffer.get2();
			int i17 = buffer.get2U();
			int i18 = buffer.get1U() * 4;
			int l18 = buffer.get1U() * 4;
			int k19 = buffer.get2U();
			int j20 = buffer.get2U();
			int i21 = buffer.get1U();
			int j21 = buffer.get1U();
			if ((l5 >= 0) && (k8 >= 0) && (l5 < 104) && (k8 < 104) && (j11 >= 0) && (k13 >= 0) && (j11 < 104) && (k13 < 104) && (i17 != 65535)) {
				l5 = (l5 * 128) + 64;
				k8 = (k8 * 128) + 64;
				j11 = (j11 * 128) + 64;
				k13 = (k13 * 128) + 64;
				ProjectileEntity projectile = new ProjectileEntity(i21, l18, k19 + loopCycle, j20 + loopCycle, j21, currentPlane, getHeightmapY(currentPlane, l5, k8) - i18, k8, l5, l15, i17);
				projectile.method455(k19 + loopCycle, k13, getHeightmapY(currentPlane, j11, k13) - l18, j11);
				aList_1013.pushBack(projectile);
			}
		}
	}

	public void method139(Buffer buffer) {
		buffer.accessBits();
		int k = buffer.getBits(8);
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
			NPCEntity npc = npcs[j1];
			int k1 = buffer.getBits(1);
			if (k1 == 0) {
				anIntArray837[anInt836++] = j1;
				npc.anInt1537 = loopCycle;
			} else {
				int l1 = buffer.getBits(2);
				if (l1 == 0) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = loopCycle;
					int i2 = buffer.getBits(3);
					npc.method448(false, i2);
					int k2 = buffer.getBits(1);
					if (k2 == 1) {
						anIntArray894[anInt893++] = j1;
					}
				} else if (l1 == 2) {
					anIntArray837[anInt836++] = j1;
					npc.anInt1537 = loopCycle;
					int j2 = buffer.getBits(3);
					npc.method448(true, j2);
					int l2 = buffer.getBits(3);
					npc.method448(true, l2);
					int i3 = buffer.getBits(1);
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
			int i = (super.screenWidth / 2) - 80;
			int l = (super.screenHeight / 2) + 20;
			l += 20;
			if ((super.mousePressButton == 1) && (super.mousePressX >= (i - 75)) && (super.mousePressX <= (i + 75)) && (super.mousePressY >= (l - 20)) && (super.mousePressY <= (l + 20))) {
				anInt833 = 3;
				anInt1216 = 0;
			}
			i = (super.screenWidth / 2) + 80;
			if ((super.mousePressButton == 1) && (super.mousePressX >= (i - 75)) && (super.mousePressX <= (i + 75)) && (super.mousePressY >= (l - 20)) && (super.mousePressY <= (l + 20))) {
				aString1266 = "";
				aString1267 = "Enter your username & password.";
				anInt833 = 2;
				anInt1216 = 0;
			}
		} else {
			if (anInt833 == 2) {
				int j = (super.screenHeight / 2) - 40;
				j += 30;
				j += 25;
				if ((super.mousePressButton == 1) && (super.mousePressY >= (j - 15)) && (super.mousePressY < j)) {
					anInt1216 = 0;
				}
				j += 15;
				if ((super.mousePressButton == 1) && (super.mousePressY >= (j - 15)) && (super.mousePressY < j)) {
					anInt1216 = 1;
				}
				int i1 = (super.screenWidth / 2) - 80;
				int k1 = (super.screenHeight / 2) + 50;
				k1 += 20;
				if ((super.mousePressButton == 1) && (super.mousePressX >= (i1 - 75)) && (super.mousePressX <= (i1 + 75)) && (super.mousePressY >= (k1 - 20)) && (super.mousePressY <= (k1 + 20))) {
					anInt1038 = 0;
					method84(aString1173, aString1174, false);
					if (ingame) {
						return;
					}
				}
				i1 = (super.screenWidth / 2) + 80;
				if ((super.mousePressButton == 1) && (super.mousePressX >= (i1 - 75)) && (super.mousePressX <= (i1 + 75)) && (super.mousePressY >= (k1 - 20)) && (super.mousePressY <= (k1 + 20))) {
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
				int k = super.screenWidth / 2;
				int j1 = (super.screenHeight / 2) + 50;
				j1 += 20;
				if ((super.mousePressButton == 1) && (super.mousePressX >= (k - 75)) && (super.mousePressX <= (k + 75)) && (super.mousePressY >= (j1 - 20)) && (super.mousePressY <= (j1 + 20))) {
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
		i1 = (i1 * 256) / (minimapZoom + 256);
		j1 = (j1 * 256) / (minimapZoom + 256);
		int k1 = ((j * i1) + (i * j1)) >> 16;
		int l1 = ((j * j1) - (i * i1)) >> 16;
		if (l > 2500) {
			image.drawMasked(imageMapback, 83 - l1 - (image.cropH / 2) - 4, ((94 + k1) - (image.cropW / 2)) + 4);
		} else {
			image.draw(((94 + k1) - (image.cropW / 2)) + 4, 83 - l1 - (image.cropH / 2) - 4);
		}
	}

	public void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
		if ((i1 < 1) || (i < 1) || (i1 > 102) || (i > 102)) {
			return;
		}
		if (lowmem && (j != currentPlane)) {
			return;
		}
		int i2 = 0;
		if (j1 == 0) {
			i2 = scene.getWallBitset(j, i1, i);
		}
		if (j1 == 1) {
			i2 = scene.getWallDecorationBitset(j, i1, i);
		}
		if (j1 == 2) {
			i2 = scene.getLocBitset(j, i1, i);
		}
		if (j1 == 3) {
			i2 = scene.getGroundDecorationBitset(j, i1, i);
		}
		if (i2 != 0) {
			int i3 = scene.getInfo(j, i1, i, i2);
			int j2 = (i2 >> 14) & 0x7fff;
			int k2 = i3 & 0x1f;
			int l2 = i3 >> 6;
			if (j1 == 0) {
				scene.removeWall(i1, j, i);
				LocType type = LocType.method572(j2);
				if (type.aBoolean767) {
					collisions[j].method215(l2, k2, type.aBoolean757, i1, i);
				}
			}
			if (j1 == 1) {
				scene.removeWallDecoration(j, i1, i);
			}
			if (j1 == 2) {
				scene.removeLoc(j, i1, i);
				LocType type_1 = LocType.method572(j2);
				if (((i1 + type_1.anInt744) > 103) || ((i + type_1.anInt744) > 103) || ((i1 + type_1.anInt761) > 103) || ((i + type_1.anInt761) > 103)) {
					return;
				}
				if (type_1.aBoolean767) {
					collisions[j].method216(l2, type_1.anInt744, i1, i, type_1.anInt761, type_1.aBoolean757);
				}
			}
			if (j1 == 3) {
				scene.removeGroundDecoration(j, i1, i);
				LocType type_2 = LocType.method572(j2);
				if (type_2.aBoolean767 && type_2.aBoolean778) {
					collisions[j].method218(i, i1);
				}
			}
		}
		if (k1 >= 0) {
			int j3 = j;
			if ((j3 < 3) && ((planeTileFlags[1][i1][i] & 2) == 2)) {
				j3++;
			}
			SceneBuilder.method188(scene, k, i, l, j3, collisions[j], anIntArrayArrayArray1214, i1, k1, j);
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
			if (players[l].anInt1537 != loopCycle) {
				players[l] = null;
			}
		}
		if (buffer.position != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + buffer.position + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < anInt891; i1++) {
			if (players[anIntArray892[i1]] == null) {
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
		if (connection == null) {
			return false;
		}
		try {
			int available = connection.available();

			if (available == 0) {
				return false;
			}

			if (ptype == -1) {
				connection.method270(in.data, 0, 1);
				ptype = in.data[0] & 0xff;
				if (randomIn != null) {
					ptype = (ptype - randomIn.nextInt()) & 0xff;
				}
				psize = PacketConstants.SIZE[ptype];
				available--;
			}

			if (psize == -1) {
				if (available > 0) {
					connection.method270(in.data, 0, 1);
					psize = in.data[0] & 0xff;
					available--;
				} else {
					return false;
				}
			}

			if (psize == -2) {
				if (available > 1) {
					connection.method270(in.data, 0, 2);
					in.position = 0;
					psize = in.get2U();
					available -= 2;
				} else {
					return false;
				}
			}

			if (available < psize) {
				return false;
			}

			in.position = 0;
			connection.method270(in.data, 0, psize);
			anInt1009 = 0;
			anInt843 = anInt842;
			anInt842 = anInt841;
			anInt841 = ptype;

			if (ptype == 81) {
				method143(psize, in);
				aBoolean1080 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 176) {
				anInt1167 = in.get1UC();
				anInt1154 = in.get2UA();
				anInt1120 = in.get1U();
				anInt1193 = in.get4ME();
				anInt1006 = in.get2U();
				if ((anInt1193 != 0) && (anInt857 == -1)) {
					Signlink.dnslookup(StringUtil.formatIPv4(anInt1193));
					method147();
					char c = '\u028A';
					if ((anInt1167 != 201) || (anInt1120 == 1)) {
						c = '\u028F';
					}
					aString881 = "";
					aBoolean1158 = false;
					for (int k9 = 0; k9 < Component.instances.length; k9++) {
						if ((Component.instances[k9] == null) || (Component.instances[k9].anInt214 != c)) {
							continue;
						}
						anInt857 = Component.instances[k9].anInt236;
						break;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 64) {
				anInt1268 = in.get1UC();
				anInt1269 = in.get1US();
				for (int j = anInt1268; j < (anInt1268 + 8); j++) {
					for (int l9 = anInt1269; l9 < (anInt1269 + 8); l9++) {
						if (aListArrayArrayArray827[currentPlane][j][l9] != null) {
							aListArrayArrayArray827[currentPlane][j][l9] = null;
							method25(j, l9);
						}
					}
				}
				for (SceneLocTemporary loc = (SceneLocTemporary) listTemporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) listTemporaryLocs.prev()) {
					if ((loc.sceneTileX >= anInt1268) && (loc.sceneTileX < (anInt1268 + 8)) && (loc.sceneTileZ >= anInt1269) && (loc.sceneTileZ < (anInt1269 + 8)) && (loc.anInt1295 == currentPlane)) {
						loc.anInt1294 = 0;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 185) {
				int k = in.get2ULEA();
				Component.instances[k].anInt233 = 3;
				if (self.aType_1698 == null) {
					Component.instances[k].anInt234 = (self.anIntArray1700[0] << 25) + (self.anIntArray1700[4] << 20) + (self.anIntArray1717[0] << 15) + (self.anIntArray1717[8] << 10) + (self.anIntArray1717[11] << 5) + self.anIntArray1717[1];
				} else {
					Component.instances[k].anInt234 = (int) (0x12345678L + self.aType_1698.uid);
				}
				ptype = -1;
				return true;
			}
			if (ptype == 107) {
				aBoolean1160 = false;
				for (int l = 0; l < 5; l++) {
					aBooleanArray876[l] = false;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 72) {
				int i1 = in.get2ULE();
				Component component = Component.instances[i1];
				for (int k15 = 0; k15 < component.anIntArray253.length; k15++) {
					component.anIntArray253[k15] = -1;
					component.anIntArray253[k15] = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 214) {
				anInt822 = psize / 8;
				for (int j1 = 0; j1 < anInt822; j1++) {
					aLongArray925[j1] = in.get8();
				}
				ptype = -1;
				return true;
			}
			if (ptype == 166) {
				aBoolean1160 = true;
				anInt1098 = in.get1U();
				anInt1099 = in.get1U();
				anInt1100 = in.get2U();
				anInt1101 = in.get1U();
				anInt1102 = in.get1U();
				if (anInt1102 >= 100) {
					anInt858 = (anInt1098 * 128) + 64;
					anInt860 = (anInt1099 * 128) + 64;
					anInt859 = getHeightmapY(currentPlane, anInt858, anInt860) - anInt1100;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 134) {
				aBoolean1153 = true;
				int k1 = in.get1U();
				int i10 = in.get4RME();
				int l15 = in.get1U();
				anIntArray864[k1] = i10;
				anIntArray922[k1] = l15;
				anIntArray1044[k1] = 1;
				for (int k20 = 0; k20 < 98; k20++) {
					if (i10 >= levelExperience[k20]) {
						anIntArray1044[k1] = k20 + 2;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 71) {
				int l1 = in.get2U();
				int j10 = in.get1UA();
				if (l1 == 65535) {
					l1 = -1;
				}
				anIntArray1130[j10] = l1;
				aBoolean1153 = true;
				aBoolean1103 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 74) {
				int i2 = in.get2ULE();
				if (i2 == 65535) {
					i2 = -1;
				}
				if ((i2 != anInt956) && aBoolean1151 && !lowmem && (anInt1259 == 0)) {
					music = i2;
					musicFade = true;
					ondemand.request(2, music);
				}
				anInt956 = i2;
				ptype = -1;
				return true;
			}
			if (ptype == 121) {
				int j2 = in.get2ULEA();
				int k10 = in.get2UA();
				if (aBoolean1151 && !lowmem) {
					music = j2;
					musicFade = false;
					ondemand.request(2, music);
					anInt1259 = k10;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 109) {
				method44();
				ptype = -1;
				return false;
			}

			if (ptype == 70) {
				int k2 = in.get2();
				int l10 = in.get2LE();
				int i16 = in.get2ULE();
				Component component_5 = Component.instances[i16];
				component_5.anInt263 = k2;
				component_5.anInt265 = l10;
				ptype = -1;
				return true;
			}

			if ((ptype == 73) || (ptype == 241)) {
				int zoneX = sceneCenterZoneX;
				int zoneZ = sceneCenterZoneZ;

				if (ptype == 73) {
					zoneX = in.get2UA();
					zoneZ = in.get2U();
					sceneInstanced = false;
				}

				// instanced scenes describe the pieces that make them up.
				if (ptype == 241) {
					zoneZ = in.get2UA();
					in.accessBits();

					for (int plane = 0; plane < 4; plane++) {
						for (int cx = 0; cx < 13; cx++) {
							for (int cz = 0; cz < 13; cz++) {
								if (in.getBits(1) == 1) {
									sceneInstancedChunkBitset[plane][cx][cz] = in.getBits(26);
								} else {
									sceneInstancedChunkBitset[plane][cx][cz] = -1;
								}
							}
						}
					}

					in.accessBytes();
					zoneX = in.get2U();
					sceneInstanced = true;
				}

				if ((sceneCenterZoneX == zoneX) && (sceneCenterZoneZ == zoneZ) && (sceneState == 2)) {
					ptype = -1;
					return true;
				}

				sceneCenterZoneX = zoneX;
				sceneCenterZoneZ = zoneZ;
				sceneBaseTileX = (sceneCenterZoneX - 6) * 8;
				sceneBaseTileZ = (sceneCenterZoneZ - 6) * 8;
				withinTutorialIsland = (((sceneCenterZoneX / 8) == 48) || ((sceneCenterZoneX / 8) == 49)) && ((sceneCenterZoneZ / 8) == 48);

				if (((sceneCenterZoneX / 8) == 48) && ((sceneCenterZoneZ / 8) == 148)) {
					withinTutorialIsland = true;
				}

				sceneState = 1;
				sceneLoadStartTime = System.currentTimeMillis();

				areaViewport.bind();
				fontPlain12.drawStringCenter("Loading - please wait.", 257, 151, 0);
				fontPlain12.drawStringCenter("Loading - please wait.", 256, 150, 0xffffff);
				areaViewport.draw(super.graphics, 4, 4);

				if (ptype == 73) {
					int mapCount = 0;

					for (int x = (sceneCenterZoneX - 6) / 8; x <= ((sceneCenterZoneX + 6) / 8); x++) {
						for (int z = (sceneCenterZoneZ - 6) / 8; z <= ((sceneCenterZoneZ + 6) / 8); z++) {
							mapCount++;
						}
					}

					sceneMapLandData = new byte[mapCount][];
					sceneMapLocData = new byte[mapCount][];
					sceneMapIndex = new int[mapCount];
					sceneMapLandFile = new int[mapCount];
					sceneMapLocFile = new int[mapCount];

					mapCount = 0;

					for (int mx = (sceneCenterZoneX - 6) / 8; mx <= ((sceneCenterZoneX + 6) / 8); mx++) {
						for (int mz = (sceneCenterZoneZ - 6) / 8; mz <= ((sceneCenterZoneZ + 6) / 8); mz++) {
							sceneMapIndex[mapCount] = (mx << 8) + mz;

							if (withinTutorialIsland && ((mz == 49) || (mz == 149) || (mz == 147) || (mx == 50) || ((mx == 49) && (mz == 47)))) {
								sceneMapLandFile[mapCount] = -1;
								sceneMapLocFile[mapCount] = -1;
							} else {
								int landFile = sceneMapLandFile[mapCount] = ondemand.getMapFile(0, mx, mz);

								if (landFile != -1) {
									ondemand.request(3, landFile);
								}

								int locFile = sceneMapLocFile[mapCount] = ondemand.getMapFile(1, mx, mz);

								if (locFile != -1) {
									ondemand.request(3, locFile);
								}
							}
							mapCount++;
						}
					}
				}

				if (ptype == 241) {
					int mapCount = 0;
					int[] mapIndices = new int[676];

					for (int plane = 0; plane < 4; plane++) {
						for (int cx = 0; cx < 13; cx++) {
							for (int cz = 0; cz < 13; cz++) {
								int bitset = sceneInstancedChunkBitset[plane][cx][cz];

								if (bitset != -1) {
									int k31 = (bitset >> 14) & 0x3ff;
									int i32 = (bitset >> 3) & 0x7ff;
									int mapIndex = ((k31 / 8) << 8) + (i32 / 8);

									for (int j = 0; j < mapCount; j++) {
										if (mapIndices[j] != mapIndex) {
											continue;
										}
										mapIndex = -1;
										break;
									}

									if (mapIndex != -1) {
										mapIndices[mapCount++] = mapIndex;
									}
								}
							}
						}
					}

					sceneMapLandData = new byte[mapCount][];
					sceneMapLocData = new byte[mapCount][];
					sceneMapIndex = new int[mapCount];
					sceneMapLandFile = new int[mapCount];
					sceneMapLocFile = new int[mapCount];

					for (int i = 0; i < mapCount; i++) {
						int mapIndex = sceneMapIndex[i] = mapIndices[i];
						int mapX = (mapIndex >> 8) & 0xff;
						int mapZ = mapIndex & 0xff;

						int mapLandFile = sceneMapLandFile[i] = ondemand.getMapFile(0, mapX, mapZ);

						if (mapLandFile != -1) {
							ondemand.request(3, mapLandFile);
						}

						int mapLocFile = sceneMapLocFile[i] = ondemand.getMapFile(1, mapX, mapZ);

						if (mapLocFile != -1) {
							ondemand.request(3, mapLocFile);
						}
					}
				}

				int dtx = sceneBaseTileX - scenePrevBaseTileX;
				int dtz = sceneBaseTileZ - scenePrevBaseTileZ;
				scenePrevBaseTileX = sceneBaseTileX;
				scenePrevBaseTileZ = sceneBaseTileZ;

				for (int i = 0; i < 16384; i++) {
					NPCEntity npc = npcs[i];

					if (npc != null) {
						for (int j = 0; j < 10; j++) {
							npc.pathTileX[j] -= dtx;
							npc.pathTileZ[j] -= dtz;
						}
						npc.x -= dtx * 128;
						npc.z -= dtz * 128;
					}
				}

				for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
					PlayerEntity player = players[i];

					if (player != null) {
						for (int i31 = 0; i31 < 10; i31++) {
							player.pathTileX[i31] -= dtx;
							player.pathTileZ[i31] -= dtz;
						}
						player.x -= dtx * 128;
						player.z -= dtz * 128;
					}
				}

				aBoolean1080 = true;

				byte x0 = 0;
				byte x1 = 104;
				byte dirX = 1;

				if (dtx < 0) {
					x0 = 103;
					x1 = -1;
					dirX = -1;
				}

				byte z0 = 0;
				byte z1 = 104;
				byte dirZ = 1;

				if (dtz < 0) {
					z0 = 103;
					z1 = -1;
					dirZ = -1;
				}

				for (int x = x0; x != x1; x += dirX) {
					for (int z = z0; z != z1; z += dirZ) {
						int dstX = x + dtx;
						int dstZ = z + dtz;

						for (int plane = 0; plane < 4; plane++) {
							if ((dstX >= 0) && (dstZ >= 0) && (dstX < 104) && (dstZ < 104)) {
								aListArrayArrayArray827[plane][x][z] = aListArrayArrayArray827[plane][dstX][dstZ];
							} else {
								aListArrayArrayArray827[plane][x][z] = null;
							}
						}
					}
				}

				for (SceneLocTemporary loc = (SceneLocTemporary) listTemporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) listTemporaryLocs.prev()) {
					loc.sceneTileX -= dtx;
					loc.sceneTileZ -= dtz;
					if ((loc.sceneTileX < 0) || (loc.sceneTileZ < 0) || (loc.sceneTileX >= 104) || (loc.sceneTileZ >= 104)) {
						loc.unlink();
					}
				}

				if (anInt1261 != 0) {
					anInt1261 -= dtx;
					anInt1262 -= dtz;
				}

				aBoolean1160 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 208) {
				int i3 = in.get2LE();
				if (i3 >= 0) {
					method60(i3);
				}
				anInt1018 = i3;
				ptype = -1;
				return true;
			}
			if (ptype == 99) {
				minimapState = in.get1U();
				ptype = -1;
				return true;
			}
			if (ptype == 75) {
				int j3 = in.get2ULEA();
				int j11 = in.get2ULEA();
				Component.instances[j11].anInt233 = 2;
				Component.instances[j11].anInt234 = j3;
				ptype = -1;
				return true;
			}
			if (ptype == 114) {
				anInt1104 = in.get2ULE() * 30;
				ptype = -1;
				return true;
			}
			if (ptype == 60) {
				anInt1269 = in.get1U();
				anInt1268 = in.get1UC();
				while (in.position < psize) {
					int k3 = in.get1U();
					method137(in, k3);
				}
				ptype = -1;
				return true;
			}
			if (ptype == 35) {
				int l3 = in.get1U();
				int k11 = in.get1U();
				int j17 = in.get1U();
				int k21 = in.get1U();
				aBooleanArray876[l3] = true;
				anIntArray873[l3] = k11;
				anIntArray1203[l3] = j17;
				anIntArray928[l3] = k21;
				anIntArray1030[l3] = 0;
				ptype = -1;
				return true;
			}
			if (ptype == 174) {
				int i4 = in.get2U();
				int l11 = in.get1U();
				int k17 = in.get2U();
				if (aBoolean848 && !lowmem && (anInt1062 < 50)) {
					anIntArray1207[anInt1062] = i4;
					anIntArray1241[anInt1062] = l11;
					anIntArray1250[anInt1062] = k17 + SoundTrack.delays[i4];
					anInt1062++;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 104) {
				int j4 = in.get1UC();
				int i12 = in.get1UA();
				String s6 = in.getString();
				if ((j4 >= 1) && (j4 <= 5)) {
					if (s6.equalsIgnoreCase("null")) {
						s6 = null;
					}
					aStringArray1127[j4 - 1] = s6;
					aBooleanArray1128[j4 - 1] = i12 == 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 78) {
				anInt1261 = 0;
				ptype = -1;
				return true;
			}
			if (ptype == 253) {
				String s = in.getString();
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
						method77(4, s3, "wishes to trade with you.");
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
						method77(8, s4, "wishes to duel with you.");
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
						method77(8, s5, s8);
					}
				} else {
					method77(0, "", s);
				}
				ptype = -1;
				return true;
			}
			if (ptype == 1) {
				for (PlayerEntity player : players) {
					if (player != null) {
						player.anInt1526 = -1;
					}
				}
				for (NPCEntity npc : npcs) {
					if (npc != null) {
						npc.anInt1526 = -1;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 50) {
				long l4 = in.get8();
				int i18 = in.get1U();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(l4));
				for (int k24 = 0; k24 < anInt899; k24++) {
					if (l4 != aLongArray955[k24]) {
						continue;
					}
					if (anIntArray826[k24] != i18) {
						anIntArray826[k24] = i18;
						aBoolean1153 = true;
						if (i18 > 0) {
							method77(5, "", s7 + " has logged in.");
						}
						if (i18 == 0) {
							method77(5, "", s7 + " has logged out.");
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
						if (((anIntArray826[k29] != nodeId) && (anIntArray826[k29 + 1] == nodeId)) || ((anIntArray826[k29] == 0) && (anIntArray826[k29 + 1] != 0))) {
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
				ptype = -1;
				return true;
			}
			if (ptype == 110) {
				if (anInt1221 == 12) {
					aBoolean1153 = true;
				}
				anInt1148 = in.get1U();
				ptype = -1;
				return true;
			}
			if (ptype == 254) {
				anInt855 = in.get1U();
				if (anInt855 == 1) {
					anInt1222 = in.get2U();
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
					anInt934 = in.get2U();
					anInt935 = in.get2U();
					anInt936 = in.get1U();
				}
				if (anInt855 == 10) {
					anInt933 = in.get2U();
				}
				ptype = -1;
				return true;
			}
			if (ptype == 248) {
				int i5 = in.get2UA();
				int k12 = in.get2U();
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
				ptype = -1;
				return true;
			}
			if (ptype == 79) {
				int j5 = in.get2ULE();
				int l12 = in.get2UA();
				Component component_3 = Component.instances[j5];
				if ((component_3 != null) && (component_3.anInt262 == 0)) {
					if (l12 < 0) {
						l12 = 0;
					}
					if (l12 > (component_3.anInt261 - component_3.anInt267)) {
						l12 = component_3.anInt261 - component_3.anInt267;
					}
					component_3.anInt224 = l12;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 68) {
				for (int k5 = 0; k5 < anIntArray971.length; k5++) {
					if (anIntArray971[k5] != anIntArray1045[k5]) {
						anIntArray971[k5] = anIntArray1045[k5];
						method33(k5);
						aBoolean1153 = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 196) {
				long l5 = in.get8();
				int j18 = in.get4();
				int l21 = in.get1U();
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
						String s9 = ChatCompression.unpack(psize - 13, in);
						if (l21 != 3) {
							s9 = Censor.method497(s9, 0);
						}
						if ((l21 == 2) || (l21 == 3)) {
							method77(7, "@cr2@" + StringUtil.formatName(StringUtil.fromBase37(l5)), s9);
						} else if (l21 == 1) {
							method77(7, "@cr1@" + StringUtil.formatName(StringUtil.fromBase37(l5)), s9);
						} else {
							method77(3, StringUtil.formatName(StringUtil.fromBase37(l5)), s9);
						}
					} catch (Exception exception1) {
						Signlink.reporterror("cde1");
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 85) {
				anInt1269 = in.get1UC();
				anInt1268 = in.get1UC();
				ptype = -1;
				return true;
			}
			if (ptype == 24) {
				anInt1054 = in.get1US();
				if (anInt1054 == anInt1221) {
					if (anInt1054 == 3) {
						anInt1221 = 1;
					} else {
						anInt1221 = 3;
					}
					aBoolean1153 = true;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 246) {
				int i6 = in.get2ULE();
				int i13 = in.get2U();
				int k18 = in.get2U();
				if (k18 == 65535) {
					Component.instances[i6].anInt233 = 0;
				} else {
					ObjType type = ObjType.method198(k18);
					Component.instances[i6].anInt233 = 4;
					Component.instances[i6].anInt234 = k18;
					Component.instances[i6].anInt270 = type.anInt190;
					Component.instances[i6].anInt271 = type.anInt198;
					Component.instances[i6].anInt269 = (type.anInt181 * 100) / i13;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 171) {
				boolean flag1 = in.get1U() == 1;
				int j13 = in.get2U();
				Component.instances[j13].aBoolean266 = flag1;
				ptype = -1;
				return true;
			}
			if (ptype == 142) {
				int j6 = in.get2ULE();
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
				ptype = -1;
				return true;
			}
			if (ptype == 126) {
				String s1 = in.getString();
				int k13 = in.get2UA();
				if ((k13 >= 0) && (k13 < Component.instances.length)) {
					Component component = Component.instances[k13];
					if (component != null) {
						component.aString248 = s1;
						if (component.anInt236 == anIntArray1130[anInt1221]) {
							aBoolean1153 = true;
						}
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 206) {
				anInt1287 = in.get1U();
				anInt845 = in.get1U();
				anInt1248 = in.get1U();
				aBoolean1233 = true;
				aBoolean1223 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 240) {
				if (anInt1221 == 12) {
					aBoolean1153 = true;
				}
				anInt878 = in.get2();
				ptype = -1;
				return true;
			}
			if (ptype == 8) {
				int k6 = in.get2ULEA();
				int l13 = in.get2U();
				Component.instances[k6].anInt233 = 1;
				Component.instances[k6].anInt234 = l13;
				ptype = -1;
				return true;
			}
			if (ptype == 122) {
				int l6 = in.get2ULEA();
				int i14 = in.get2ULEA();
				int i19 = (i14 >> 10) & 0x1f;
				int i22 = (i14 >> 5) & 0x1f;
				int l24 = i14 & 0x1f;
				Component.instances[l6].anInt232 = (i19 << 19) + (i22 << 11) + (l24 << 3);
				ptype = -1;
				return true;
			}
			if (ptype == 53) {
				aBoolean1153 = true;
				int i7 = in.get2U();
				Component component_1 = Component.instances[i7];
				int j19 = in.get2U();
				for (int j22 = 0; j22 < j19; j22++) {
					int i25 = in.get1U();
					if (i25 == 255) {
						i25 = in.get4ME();
					}
					if (j22 >= component_1.anIntArray253.length) {
						in.get2ULEA();
					} else {
						component_1.anIntArray253[j22] = in.get2ULEA();
						component_1.anIntArray252[j22] = i25;
					}
				}
				for (int j25 = j19; j25 < component_1.anIntArray253.length; j25++) {
					component_1.anIntArray253[j25] = 0;
					component_1.anIntArray252[j25] = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 230) {
				int j7 = in.get2UA();
				int j14 = in.get2U();
				int k19 = in.get2U();
				int k22 = in.get2ULEA();
				Component.instances[j14].anInt270 = k19;
				Component.instances[j14].anInt271 = k22;
				Component.instances[j14].anInt269 = j7;
				ptype = -1;
				return true;
			}
			if (ptype == 221) {
				anInt900 = in.get1U();
				aBoolean1153 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 177) {
				aBoolean1160 = true;
				anInt995 = in.get1U();
				anInt996 = in.get1U();
				anInt997 = in.get2U();
				anInt998 = in.get1U();
				anInt999 = in.get1U();
				if (anInt999 >= 100) {
					int k7 = (anInt995 * 128) + 64;
					int k14 = (anInt996 * 128) + 64;
					int i20 = getHeightmapY(currentPlane, k7, k14) - anInt997;
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
				ptype = -1;
				return true;
			}
			if (ptype == 249) {
				anInt1046 = in.get1UA();
				selfPlayerId = in.get2ULEA();
				ptype = -1;
				return true;
			}
			if (ptype == 65) {
				method31(in, psize);
				ptype = -1;
				return true;
			}
			if (ptype == 27) {
				aBoolean1256 = false;
				anInt1225 = 1;
				aString1004 = "";
				aBoolean1223 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 187) {
				aBoolean1256 = false;
				anInt1225 = 2;
				aString1004 = "";
				aBoolean1223 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 97) {
				int l7 = in.get2U();
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
				ptype = -1;
				return true;
			}
			if (ptype == 218) {
				anInt1042 = in.get2LEA();
				aBoolean1223 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 87) {
				int j8 = in.get2ULE();
				int l14 = in.get4RME();
				anIntArray1045[j8] = l14;
				if (anIntArray971[j8] != l14) {
					anIntArray971[j8] = l14;
					method33(j8);
					aBoolean1153 = true;
					if (anInt1042 != -1) {
						aBoolean1223 = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 36) {
				int k8 = in.get2ULE();
				byte byte0 = in.get1();
				anIntArray1045[k8] = byte0;
				if (anIntArray971[k8] != byte0) {
					anIntArray971[k8] = byte0;
					method33(k8);
					aBoolean1153 = true;
					if (anInt1042 != -1) {
						aBoolean1223 = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 61) {
				anInt1055 = in.get1U();
				ptype = -1;
				return true;
			}
			if (ptype == 200) {
				int l8 = in.get2U();
				int i15 = in.get2();
				Component component_4 = Component.instances[l8];
				component_4.anInt257 = i15;
				if (i15 == -1) {
					component_4.anInt246 = 0;
					component_4.anInt208 = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 219) {
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
				ptype = -1;
				return true;
			}
			if (ptype == 34) {
				aBoolean1153 = true;
				int i9 = in.get2U();
				Component component_2 = Component.instances[i9];
				while (in.position < psize) {
					int j20 = in.getSmartU();
					int i23 = in.get2U();
					int l25 = in.get1U();
					if (l25 == 255) {
						l25 = in.get4();
					}
					if ((j20 >= 0) && (j20 < component_2.anIntArray253.length)) {
						component_2.anIntArray253[j20] = i23;
						component_2.anIntArray252[j20] = l25;
					}
				}
				ptype = -1;
				return true;
			}
			if ((ptype == 105) || (ptype == 84) || (ptype == 147) || (ptype == 215) || (ptype == 4) || (ptype == 117) || (ptype == 156) || (ptype == 44) || (ptype == 160) || (ptype == 101) || (ptype == 151)) {
				method137(in, ptype);
				ptype = -1;
				return true;
			}
			if (ptype == 106) {
				anInt1221 = in.get1UC();
				aBoolean1153 = true;
				aBoolean1103 = true;
				ptype = -1;
				return true;
			}
			if (ptype == 164) {
				int j9 = in.get2ULE();
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
				ptype = -1;
				return true;
			}
			Signlink.reporterror("T1 - " + ptype + "," + psize + " - " + anInt842 + "," + anInt843);
			method44();
		} catch (IOException _ex) {
			method68();
		} catch (Exception exception) {
			StringBuilder s2 = new StringBuilder("T2 - " + ptype + "," + anInt842 + "," + anInt843 + " - " + psize + "," + (sceneBaseTileX + self.pathTileX[0]) + "," + (sceneBaseTileZ + self.pathTileZ[0]) + " - ");
			for (int j15 = 0; (j15 < psize) && (j15 < 50); j15++) {
				s2.append(in.data[j15]).append(",");
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
			method144(600 + (i * 3), i, anInt1014, getHeightmapY(currentPlane, self.x, self.z) - 50, k, anInt1015);
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
		int k2 = Draw3D.cycle;
		Model.checkHover = true;
		Model.pickedCount = 0;
		Model.mouseX = super.anInt20 - 4;
		Model.mouseY = super.anInt21 - 4;
		Draw2D.clear();
		scene.method313(anInt858, anInt860, anInt862, anInt859, j, anInt861);
		scene.clearTemporaryLocs();
		method34();
		method61();
		method37(k2);
		method112();
		areaViewport.draw(super.graphics, 4, 4);
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
