// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import org.apache.commons.math3.random.ISAACRandom;

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

	public static final int[][] designPartColor = {{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193}, {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239}, {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};
	public static final int[] designHairColor = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486};
	public static final BigInteger RSA_MODULUS = new BigInteger("115021795079507343952614936197913546438580135096169635842480712252120509788529535203161526625251797553017433341968661761641695154871087245548928967487006467485383337651405009623296611208539069524887502151216922299338355736930449024798579974392565651281911869750633089962840628929607415810272098208925607905239");
	public static final int[] levelExperience;
	public static final BigInteger RSA_EXPONENT = new BigInteger("65537");
	public static final String VALID_CHAT_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	public static final int[] BITMASK;
	public static final int MAX_PLAYER_COUNT = 2048;
	public static final int LOCAL_PLAYER_INDEX = 2047;
	public static int nodeId = 10;
	public static int portOffset;
	public static boolean members = true;
	public static boolean lowmem;
	public static boolean started;
	public static int drawCycle;
	public static PlayerEntity localPlayer;
	public static boolean aBoolean1156;
	public static int loopCycle;
	public static boolean aBoolean1205;

	static {
		levelExperience = new int[99];
		int acc = 0;
		for (int i = 0; i < 99; i++) {
			int level = i + 1;
			int delta = (int) ((double) level + (300D * Math.pow(2D, (double) level / 7D)));
			acc += delta;
			levelExperience[i] = acc / 4;
		}

		BITMASK = new int[32];
		acc = 2;
		for (int k = 0; k < 32; k++) {
			BITMASK[k] = acc - 1;
			acc += acc;
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

	public static String formatObjAmount(int amount) {
		if (amount < 100000) {
			return String.valueOf(amount);
		}
		if (amount < 10000000) {
			return (amount / 1000) + "K";
		} else {
			return (amount / 1000000) + "M";
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

	public final int[] LOC_KIND_TO_CLASS_ID = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
	/**
	 * A skill's base level is determined by the skill experience and is not [de]buffable.
	 */
	public final int[] skillBaseLevel = new int[Skill.COUNT];
	/**
	 * A skill level is [de]buffable.
	 */
	public final int[] skillLevel = new int[Skill.COUNT];
	public final int[] skillExperience = new int[Skill.COUNT];
	public final int[] anIntArray873 = new int[5];
	public final boolean[] aBooleanArray876 = new boolean[5];
	public final int anInt902 = 0x766654;
	public final long[] ignoreName37 = new long[100];
	public final int anInt927 = 0x332d25;
	public final int[] anIntArray928 = new int[5];
	public final CRC32 crc32 = new CRC32();
	public final int[] messageType = new int[100];
	public final String[] messageSender = new String[100];
	public final String[] messageText = new String[100];
	public static final int[] CHAT_COLORS = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
	public final int[] compassMaskLineOffsets = new int[33];
	public final int[] flameLineOffset = new int[256];
	public final FileStore[] filestores = new FileStore[5];
	public final int anInt975 = 50;
	public final int[] chatScreenX = new int[anInt975];
	public final int[] chatScreenY = new int[anInt975];
	public final int[] chatHeight = new int[anInt975];
	public final int[] chatPadding = new int[anInt975];
	public final int[] chatColors = new int[anInt975];
	public final int[] chatStyles = new int[anInt975];
	public final int[] chatTimers = new int[anInt975];
	public final String[] chatMessages = new String[anInt975];
	public final int[] designColors = new int[5];
	public final int anInt1002 = 0x23201b;
	public final int[] anIntArray1030 = new int[5];
	public final int[] anIntArray1045 = new int[2000];
	public final int[] minimapMaskLineOffsets = new int[151];
	public final int[] compassMaskLineLengths = new int[33];
	public final Component aComponent_1059 = new Component();
	public final int anInt1063 = 0x4d4233;
	public final int[] designIdentikits = new int[7];
	public final int[] archiveChecksum = new int[9];
	public final String[] aStringArray1127 = new String[5];
	public final boolean[] aBooleanArray1128 = new boolean[5];
	public final int[][][] sceneInstancedChunkBitset = new int[4][13][13];
	public final int[] tabComponentId = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	public final int[] anIntArray1203 = new int[5];
	public final int[] waveIds = new int[50];
	public final Image8[] imageModIcons = new Image8[2];
	public final int[] minimapMaskLineLengths = new int[151];
	public final int[] anIntArray1240 = new int[100];
	public final int[] waveLoops = new int[50];
	public final int[] waveDelay = new int[50];
	public int ignoreCount;
	public long sceneLoadStartTime;
	public int[][] bfsCost = new int[104][104];
	public int[] friendWorld = new int[200];
	public DoublyLinkedList[][][] planeObjStacks = new DoublyLinkedList[4][104][104];
	public int[] flameBuffer3;
	public int[] flameBuffer2;
	public volatile boolean flameActive = false;
	public Socket aSocket832;
	public int titleScreenState;
	public Buffer aBuffer_834 = new Buffer(new byte[5000]);
	public NPCEntity[] npcs = new NPCEntity[16384];
	public int npcCount;
	public int[] npcIndices = new int[16384];
	public int anInt839;
	public int[] anIntArray840 = new int[1000];
	public int anInt841;
	public int anInt842;
	public int anInt843;
	public String chatbackMessage;
	public int privateChatSetting;
	public Buffer login = Buffer.create(1);
	public boolean aBoolean848 = true;
	public int[] flameGradient;
	public int[] flameGradient0;
	public int[] flameGradient1;
	public int[] flameGradient2;
	public int hintType;
	/**
	 * The active viewport component id which is affected by input.
	 */
	public int viewportComponentId = -1;
	public int anInt858;
	public int anInt859;
	public int anInt860;
	public int anInt861;
	public int anInt862;
	public int rights;
	public Image8 imageRedstone1v;
	public Image8 imageRedstone2v;
	public Image8 imageRedstone3v;
	public Image8 imageRedstone1hv;
	public Image8 imageRedstone2hv;
	public Image24 imageMapmarker0;
	public Image24 imageMapmarker1;
	public boolean jaggrabEnabled = true; // original value: false
	public int lastWaveId = -1;
	public int weightCarried;
	public MouseRecorder mouseRecorder;
	public volatile boolean flamesThread = false;
	public String aString881 = "";
	public int localPlayerId = -1;
	public boolean menuVisible = false;
	public int lastHoveredComponentId;
	public String chatTyped = "";
	public PlayerEntity[] players = new PlayerEntity[MAX_PLAYER_COUNT];
	public int playerCount;
	public int[] playerIndices = new int[MAX_PLAYER_COUNT];
	public int anInt893;
	public int[] anIntArray894 = new int[MAX_PLAYER_COUNT];
	public Buffer[] aBufferArray895 = new Buffer[MAX_PLAYER_COUNT];
	public int cameraAnticheatAngle;
	public int friendCount;
	public int socialState;
	public int[][] bfsDirection = new int[104][104];
	public DrawArea areaBackleft1;
	public DrawArea areaBackleft2;
	public DrawArea areaBackright1;
	public DrawArea areaBackright2;
	public DrawArea areaBacktop1;
	public DrawArea areaBackvmid1;
	public DrawArea areaBackvmid2;
	public DrawArea areaBackvmid3;
	public DrawArea areaBackhmid2;
	public byte[] aByteArray912 = new byte[16384];
	public int bankArrangeMode;
	public int crossX;
	public int crossY;
	public int crossCycle;
	public int crossMode;
	public int currentPlane;
	public boolean errorLoading = false;
	public int[][] anIntArrayArray929 = new int[104][104];
	public Image24 aImage_931;
	public Image24 aImage_932;
	public int hintPlayer;
	public int hintTileX;
	public int hintTileZ;
	public int hintHeight;
	public int hintOffsetX;
	public int hintOffsetZ;
	public int delta;
	public Scene scene;
	public Image8[] imageSideicons = new Image8[13];
	public int mouseArea;
	public int menuX;
	public int menuY;
	public int menuWidth;
	public int menuHeight;
	public long aLong953;
	public boolean _focused = true;
	public long[] friendName37 = new long[200];
	public int nextMusic = -1;
	public volatile boolean flameThread = false;
	public int projectX = -1;
	public int projectY = -1;
	public Image8 imageTitlebox;
	public Image8 imageTitlebutton;
	public int[] variables = new int[2000];
	public boolean aBoolean972 = false;
	public int chatCount;
	public int anInt984;
	public int minimapPlane = -1;
	public Image24[] imageHitmarks = new Image24[20];
	public int objDragCycles;
	public int anInt992;
	public int anInt995;
	public int anInt996;
	public int anInt997;
	public int anInt998;
	public int anInt999;
	public ISAACRandom randomIn;
	public Image24 imageMapedge;
	public String chatbackInput = "";
	public int anInt1006;
	public int psize;
	public int ptype;
	public int anInt1009;
	public int heartbeatTimer;
	/**
	 * Tells the client to disconnect instead of attempting to reestablish connection during a {@link #tryReconnect()}.
	 * This is typically set to 250 (5 seconds) after {@link #idleCycles} has reached 4500 (90 seconds).
	 *
	 * @see #updateIdleCycles()
	 * @see #tryReconnect()
	 */
	public int idleTimeout;
	public DoublyLinkedList aList_1013 = new DoublyLinkedList();
	public int cameraFocusX;
	public int cameraFocusZ;
	public int anInt1016;
	public boolean aBoolean1017 = false;
	/**
	 * A sticky component which does not leave on input.
	 */
	public int viewportOverlayComponentId = -1;
	public int minimapState;
	public int anInt1022;
	public int sceneState;
	public Image8 imageScrollbar0;
	public Image8 imageScrollbar1;
	public int viewportHoveredComponentId;
	public Image8 imageBackbase1;
	public Image8 imageBackbase2;
	public Image8 imageBackhmid1;
	public boolean updateDesignModel = false;
	public Image24[] imageMapfunction = new Image24[100];
	public int sceneBaseTileX;
	public int sceneBaseTileZ;
	public int scenePrevBaseTileX;
	public int scenePrevBaseTileZ;
	public int anInt1038;
	public int chatbackHoveredComponentId;
	public int flameGradientCycle0;
	public int flameGradientCycle1;
	public int stickyChatbackComponentId = -1;
	public int anInt1046;
	public boolean designGender = true;
	public int invbackHoveredComponentId;
	public String lastProgressMessage;
	public FileArchive archiveTitle;
	public int anInt1054 = -1;
	public int anInt1055;
	public DoublyLinkedList spotanims = new DoublyLinkedList();
	public Image8[] imageMapscene = new Image8[100];
	public int waveCount;
	/**
	 * Used for adding/removing friends/ignores and sending private messages.
	 */
	public int socialAction;
	/**
	 * The current container slot id the mouse is hovered over that belongs to {@link #hoveredSlotParentId}.
	 */
	public int hoveredSlot;
	/**
	 * The current component id the mouse is hovered over that the {@link #hoveredSlot} belongs to.
	 */
	public int hoveredSlotParentId;
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
	public int lastProgressPercent;
	public boolean aBoolean1080 = false;
	public String[] friendName = new String[200];
	public Buffer in = Buffer.create(1);
	/**
	 * The component id of the container that the obj being dragged belongs to.
	 */
	public int objDragComponentId;
	/**
	 * The slot the obj being dragged resides.
	 */
	public int objDragSlot;
	/**
	 * The area the object being dragged resides.
	 * 1 = Viewport
	 * 2 = Invback
	 * 3 = Chatback
	 */
	public int objDragArea;
	/**
	 * The mouse x when an obj was pressed in a container.
	 */
	public int objGrabX;
	/**
	 * The mouse y when an obj was pressed in a container.
	 */
	public int objGrabY;
	public int anInt1089;
	public int[] menuParamA = new int[500];
	public int[] menuParamB = new int[500];
	public int[] menuAction = new int[500];
	public int[] menuParamC = new int[500];
	public Image24[] imageHeadicons = new Image24[20];
	public int anInt1098;
	public int anInt1099;
	public int anInt1100;
	public int anInt1101;
	public int anInt1102;
	public boolean redrawSideicons = false;
	public int systemUpdateTimer;
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
	public String socialMessage = "";
	public Image24 imageCompass;
	public DrawArea areaBackbase1;
	public DrawArea areaBackbase2;
	public DrawArea areaBackhmid1;
	public int cameraAnticheatOffsetZ;
	public int menuSize;
	public int anInt1136;
	public int activeSpellComponent;
	public int activeSpellFlags;
	public String spellCaption;
	public Image24[] activeMapFunctions = new Image24[1000];
	public boolean withinTutorialIsland = false;
	public Image8 imageRedstone1;
	public Image8 imageRedstone2;
	public Image8 imageRedstone3;
	public Image8 imageRedstone1h;
	public Image8 imageRedstone2h;
	public int energy;
	public boolean aBoolean1149 = false;
	public Image24[] imageCrosses = new Image24[8];
	public boolean midiActive = true;
	public Image8[] imageRunes;
	public boolean redrawInvback = false;
	public int anInt1154;
	public boolean ingame = false;
	public boolean aBoolean1158 = false;
	public boolean sceneInstanced = false;
	public boolean aBoolean1160 = false;
	public DrawArea areaInvback;
	public DrawArea areaMapback;
	public DrawArea areaViewport;
	public DrawArea areaChatback;
	public int anInt1167;
	public Connection connection;
	public int anInt1169;
	public int minimapZoom;
	public long lastWaveStartTime;
	public String username = "";
	public String password = "";
	public boolean errorHost = false;
	public int anInt1178 = -1;
	public DoublyLinkedList temporaryLocs = new DoublyLinkedList();
	public int[] areaChatbackOffsets;
	public int[] areaInvbackOffsets;
	public int[] areaViewportOffsets;
	public byte[][] sceneMapLandData;
	public int cameraPitch = 128;
	public int cameraYaw;
	public int cameraYawTranslate;
	public int cameraPitchTranslate;
	public int invbackComponentId = -1;
	public int[] flameBuffer0;
	public int[] flameBuffer1;
	public Buffer out = Buffer.create(1);
	public int anInt1193;
	public int splitPrivateChat;
	public Image8 imageInvback;
	public Image8 imageMapback;
	public Image8 imageChatback;
	public String[] menuOption = new String[500];
	public Image24 imageFlamesLeft;
	public Image24 imageFlamesRight;
	public int flameCycle;
	public int minimapAnticheatAngle;
	public int chatScrollHeight = 78;
	public String socialInput = "";
	public int anInt1213;
	public int[][][] planeHeightmap;
	public long aLong1215;
	public int titleLoginField;
	public long aLong1220;
	public int selectedTab = 3;
	public int hintNPC;
	public boolean redrawChatback = false;
	public int chatbackInputType;
	public int music;
	public boolean musicFading = true;
	public SceneCollisionMap[] collisions = new SceneCollisionMap[4];
	public boolean redrawPrivacySettings = false;
	public int[] sceneMapIndex;
	public int[] sceneMapLandFile;
	public int[] sceneMapLocFile;
	public int anInt1237;
	public int anInt1238;
	public boolean objGrabThreshold = false;
	public int anInt1243;
	public int anInt1244;
	public int anInt1245;
	public int anInt1246;
	public byte[][] sceneMapLocData;
	public int tradeChatSetting;
	public int chatEffects;
	public int anInt1251;
	public boolean errorStarted = false;
	/**
	 * Game mouse buttons option.
	 * 0 = TWO
	 * 1 = ONE
	 */
	public int mouseButtonsOption;
	public boolean redrawTitleBackground = false;
	public boolean showSocialInput = false;
	public int lastWaveLength;
	public byte[][][] planeTileFlags;
	public int nextMusicDelay;
	public int flagSceneTileX;
	public int flagSceneTileZ;
	public Image24 imageMinimap;
	public int tryMoveNearest;
	public int sceneCycle;
	public String loginMessage0 = "";
	public String loginMessage1 = "";
	public int zoneX;
	public int zoneZ;
	public BitmapFont fontPlain11;
	public BitmapFont fontPlain12;
	public BitmapFont fontBold12;
	public BitmapFont fontQuill8;
	public int flameCycle0;
	public int chatbackComponentId = -1;
	public int cameraAnticheatOffsetX;
	public int[] bfsStepX = new int[4000];
	public int[] bfsStepZ = new int[4000];
	public int anInt1282;
	public int anInt1283;
	public int anInt1284;
	public int anInt1285;
	public String aString1286;
	public int publicChatSetting;
	public int lastWaveLoops = -1;

	public Game() {
	}

	@Override
	public void run() {
		if (flamesThread) {
			runFlames();
		} else {
			super.run();
		}
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + server + ":" + (80 + portOffset));
		} catch (Exception ignored) {
		}
		return null;
	}

	public String getParameter(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void load() throws IOException {
		showProgress(20, "Starting up");

		if (Signlink.sunjava) {
			super.mindel = 5;
		}

		if (started) {
			errorStarted = true;
			return;
		}

		started = true;

		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++) {
				filestores[i] = new FileStore(500000, Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
			}
		}

		try {
			loadArchiveChecksums();

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
			planeHeightmap = new int[4][105][105];
			scene = new Scene(104, 104, planeHeightmap, 4);

			for (int plane = 0; plane < 4; plane++) {
				collisions[plane] = new SceneCollisionMap(104, 104);
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

				musicFading = true;
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
				if (ondemand.animIndex[i] != 0) {
					ondemand.request(1, i);
				}
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
				if ((ondemand.getModelFlags(i) & 1) != 0) {
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

			for (int i = 0; i < total; i++) {
				int flags = ondemand.getModelFlags(i);
				byte priority = 0;
				if ((flags & 8) != 0) {
					priority = 10;
				} else if ((flags & 0x20) != 0) {
					priority = 9;
				} else if ((flags & 0x10) != 0) {
					priority = 8;
				} else if ((flags & 0x40) != 0) {
					priority = 7;
				} else if ((flags & 0x80) != 0) {
					priority = 6;
				} else if ((flags & 2) != 0) {
					priority = 5;
				} else if ((flags & 4) != 0) {
					priority = 4;
				}
				if ((flags & 1) != 0) {
					priority = 3;
				}
				if (priority != 0) {
					ondemand.prefetch(priority, 0, i);
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
			imageMapedge.crop();
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
			imageRedstone1h = new Image8(archiveMedia, "redstone1", 0);
			imageRedstone1h.flipH();
			imageRedstone2h = new Image8(archiveMedia, "redstone2", 0);
			imageRedstone2h.flipH();
			imageRedstone1v = new Image8(archiveMedia, "redstone1", 0);
			imageRedstone1v.flipV();
			imageRedstone2v = new Image8(archiveMedia, "redstone2", 0);
			imageRedstone2v.flipV();
			imageRedstone3v = new Image8(archiveMedia, "redstone3", 0);
			imageRedstone3v.flipV();
			imageRedstone1hv = new Image8(archiveMedia, "redstone1", 0);
			imageRedstone1hv.flipH();
			imageRedstone1hv.flipV();
			imageRedstone2hv = new Image8(archiveMedia, "redstone2", 0);
			imageRedstone2hv.flipH();
			imageRedstone2hv.flipV();
			for (int i = 0; i < 2; i++) {
				imageModIcons[i] = new Image8(archiveMedia, "mod_icons", i);
			}
			areaBackleft1 = new DrawArea(new Image24(archiveMedia, "backleft1", 0));
			areaBackleft2 = new DrawArea(new Image24(archiveMedia, "backleft2", 0));
			areaBackright1 = new DrawArea(new Image24(archiveMedia, "backright1", 0));
			areaBackright2 = new DrawArea(new Image24(archiveMedia, "backright2", 0));
			areaBacktop1 = new DrawArea(new Image24(archiveMedia, "backtop1", 0));
			areaBackvmid1 = new DrawArea(new Image24(archiveMedia, "backvmid1", 0));
			areaBackvmid2 = new DrawArea(new Image24(archiveMedia, "backvmid2", 0));
			areaBackvmid3 = new DrawArea(new Image24(archiveMedia, "backvmid3", 0));
			areaBackhmid2 = new DrawArea(new Image24(archiveMedia, "backhmid2", 0));

			int red = (int) (Math.random() * 21D) - 10;
			int green = (int) (Math.random() * 21D) - 10;
			int blue = (int) (Math.random() * 21D) - 10;
			int value = (int) (Math.random() * 41D) - 20;

			for (int i = 0; i < 100; i++) {
				if (imageMapfunction[i] != null) {
					imageMapfunction[i].translate(red + value, green + value, blue + value);
				}
				if (imageMapscene[i] != null) {
					imageMapscene[i].translate(red + value, green + value, blue + value);
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

			if (!lowmem) {
				showProgress(90, "Unpacking sounds");
				SoundTrack.method240(new Buffer(archiveSounds.read("sounds.dat")));
			}

			showProgress(95, "Unpacking interfaces");
			Component.unpack(archiveInterface, new BitmapFont[]{fontPlain11, fontPlain12, fontBold12, fontQuill8}, archiveMedia);

			showProgress(100, "Preparing game engine");

			for (int y = 0; y < 33; y++) {
				int left = 999;
				int right = 0;
				for (int x = 0; x < 34; x++) {
					if (imageMapback.pixels[x + (y * imageMapback.width)] == 0) {
						if (left == 999) {
							left = x;
						}
						continue;
					}
					if (left == 999) {
						continue;
					}
					right = x;
					break;
				}
				compassMaskLineOffsets[y] = left;
				compassMaskLineLengths[y] = right - left;
			}

			for (int y = 5; y < 156; y++) {
				int left = 999;
				int right = 0;
				for (int x = 25; x < 172; x++) {
					if ((imageMapback.pixels[x + (y * imageMapback.width)] == 0) && ((x > 34) || (y > 34))) {
						if (left == 999) {
							left = x;
						}
						continue;
					}
					if (left == 999) {
						continue;
					}
					right = x;
					break;
				}
				minimapMaskLineOffsets[y - 5] = left - 25;
				minimapMaskLineLengths[y - 5] = right - left;
			}

			Draw3D.init3D(479, 96);
			areaChatbackOffsets = Draw3D.lineOffset;

			Draw3D.init3D(190, 261);
			areaInvbackOffsets = Draw3D.lineOffset;

			Draw3D.init3D(512, 334);
			areaViewportOffsets = Draw3D.lineOffset;

			int[] ai = new int[9];
			for (int i = 0; i < 9; i++) {
				int angle = 128 + (i * 32) + 15;
				int l8 = 600 + (angle * 3);
				ai[i] = (l8 * Draw3D.sin[angle]) >> 16;
			}

			Scene.method310(500, 800, 512, 334, ai);
			Censor.method487(archiveWordenc);

			mouseRecorder = new MouseRecorder(this);
			startThread(mouseRecorder = new MouseRecorder(this), 10);

			LocEntity.game = this;
			LocType.game = this;
			NPCType.game = this;
			return;
		} catch (Exception exception) {
			Signlink.reporterror("loaderror " + lastProgressMessage + " " + lastProgressPercent);
			exception.printStackTrace();
		}
		errorLoading = true;
	}

	@Override
	public void update() throws IOException {
		if (errorStarted || errorLoading || errorHost) {
			return;
		}
		loopCycle++;
		if (!ingame) {
			updateTitle();
		} else {
			updateGame();
		}
		handleOnDemandRequests();
	}

	@Override
	public void unload() {
		Signlink.reporterror = false;
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ignored) {
		}
		connection = null;
		midistop();
		if (mouseRecorder != null) {
			mouseRecorder.aBoolean808 = false;
			mouseRecorder = null;
		}
		if (ondemand != null) {
			ondemand.stop();
			ondemand = null;
		}
		aBuffer_834 = null;
		out = null;
		login = null;
		in = null;
		sceneMapIndex = null;
		sceneMapLandData = null;
		sceneMapLocData = null;
		sceneMapLandFile = null;
		sceneMapLocFile = null;
		planeHeightmap = null;
		planeTileFlags = null;
		scene = null;
		collisions = null;
		bfsDirection = null;
		bfsCost = null;
		bfsStepX = null;
		bfsStepZ = null;
		aByteArray912 = null;
		areaInvback = null;
		areaMapback = null;
		areaViewport = null;
		areaChatback = null;
		areaBackbase1 = null;
		areaBackbase2 = null;
		areaBackhmid1 = null;
		areaBackleft1 = null;
		areaBackleft2 = null;
		areaBackright1 = null;
		areaBackright2 = null;
		areaBacktop1 = null;
		areaBackvmid1 = null;
		areaBackvmid2 = null;
		areaBackvmid3 = null;
		areaBackhmid2 = null;
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
		imageRedstone1h = null;
		imageRedstone2h = null;
		imageRedstone1v = null;
		imageRedstone2v = null;
		imageRedstone3v = null;
		imageRedstone1hv = null;
		imageRedstone2hv = null;
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
		playerIndices = null;
		anIntArray894 = null;
		aBufferArray895 = null;
		anIntArray840 = null;
		npcs = null;
		npcIndices = null;
		planeObjStacks = null;
		temporaryLocs = null;
		aList_1013 = null;
		spotanims = null;
		menuParamA = null;
		menuParamB = null;
		menuAction = null;
		menuParamC = null;
		menuOption = null;
		variables = null;
		activeMapFunctionX = null;
		activeMapFunctionZ = null;
		activeMapFunctions = null;
		imageMinimap = null;
		friendName = null;
		friendName37 = null;
		friendWorld = null;
		imageTitle0 = null;
		imageTitle1 = null;
		imageTitle2 = null;
		imageTitle3 = null;
		imageTitle4 = null;
		imageTitle5 = null;
		imageTitle6 = null;
		imageTitle7 = null;
		imageTitle8 = null;
		disposeTitleComponents();
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
		if (errorStarted || errorLoading || errorHost) {
			method94();
			return;
		}
		drawCycle++;
		if (!ingame) {
			drawTitleScreen(false);
		} else {
			method102();
		}
		anInt1213 = 0;
	}

	@Override
	public void refresh() {
		redrawTitleBackground = true;
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
		lastProgressPercent = percent;
		lastProgressMessage = message;
		prepareTitleScreen();
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
		if (redrawTitleBackground) {
			redrawTitleBackground = false;
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

	public void midistop() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	public void loadArchiveChecksums() throws IOException {
		int wait = 5;
		archiveChecksum[8] = 0;
		int retries = 0;
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
				retries++;
				for (int l = wait; l > 0; l--) {
					if (retries >= 10) {
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
				wait *= 2;
				if (wait > 60) {
					wait = 60;
				}
				jaggrabEnabled = !jaggrabEnabled;
			}
		}
	}

	public boolean method17(int j) {
		if (j < 0) {
			return false;
		}
		int k = menuAction[j];
		if (k >= 2000) {
			k -= 2000;
		}
		return k == 337;
	}

	public void drawChatback() {
		areaChatback.bind();
		Draw3D.lineOffset = areaChatbackOffsets;
		imageChatback.blit(0, 0);

		if (showSocialInput) {
			fontBold12.drawStringCenter(socialMessage, 239, 40, 0);
			fontBold12.drawStringCenter(socialInput + "*", 239, 60, 128);
		} else if (chatbackInputType == 1) {
			fontBold12.drawStringCenter("Enter amount:", 239, 40, 0);
			fontBold12.drawStringCenter(chatbackInput + "*", 239, 60, 128);
		} else if (chatbackInputType == 2) {
			fontBold12.drawStringCenter("Enter name:", 239, 40, 0);
			fontBold12.drawStringCenter(chatbackInput + "*", 239, 60, 128);
		} else if (chatbackMessage != null) {
			fontBold12.drawStringCenter(chatbackMessage, 239, 40, 0);
			fontBold12.drawStringCenter("Click to continue", 239, 60, 128);
		} else if (chatbackComponentId != -1) {
			drawParentComponent(Component.instances[chatbackComponentId], 0, 0, 0);
		} else if (stickyChatbackComponentId != -1) {
			drawParentComponent(Component.instances[stickyChatbackComponentId], 0, 0, 0);
		} else {
			drawChat();
		}

		if (menuVisible && (mouseArea == 2)) {
			drawMenu();
		}

		areaChatback.draw(super.graphics, 17, 357);

		areaViewport.bind();
		Draw3D.lineOffset = areaViewportOffsets;
	}

	private void drawChat() {
		BitmapFont font = fontPlain12;
		int line = 0;
		Draw2D.setBounds(0, 0, 463, 77);

		for (int i = 0; i < 100; i++) {
			if (messageText[i] == null) {
				continue;
			}

			int type = messageType[i];
			int y = (70 - (line * 14)) + anInt1089;
			String sender = messageSender[i];
			byte icon = 0;

			if ((sender != null) && sender.startsWith("@cr1@")) {
				sender = sender.substring(5);
				icon = 1;
			}

			if ((sender != null) && sender.startsWith("@cr2@")) {
				sender = sender.substring(5);
				icon = 2;
			}

			if (type == 0) {
				if ((y > 0) && (y < 110)) {
					font.drawString(messageText[i], 4, y, 0);
				}
				line++;
			}

			if (((type == 1) || (type == 2)) && ((type == 1) || (publicChatSetting == 0) || ((publicChatSetting == 1) && isFriend(sender)))) {
				if ((y > 0) && (y < 110)) {
					int x = 4;

					if (icon == 1) {
						imageModIcons[0].blit(x, y - 12);
						x += 14;
					}

					if (icon == 2) {
						imageModIcons[1].blit(x, y - 12);
						x += 14;
					}

					font.drawString(sender + ":", x, y, 0);
					x += font.stringWidthTaggable(sender) + 8;

					font.drawString(messageText[i], x, y, 255);
				}
				line++;
			}

			if (((type == 3) || (type == 7)) && (splitPrivateChat == 0) && ((type == 7) || (privateChatSetting == 0) || ((privateChatSetting == 1) && isFriend(sender)))) {
				if ((y > 0) && (y < 110)) {
					int x = 4;

					font.drawString("From", x, y, 0);
					x += font.stringWidthTaggable("From ");

					if (icon == 1) {
						imageModIcons[0].blit(x, y - 12);
						x += 14;
					}

					if (icon == 2) {
						imageModIcons[1].blit(x, y - 12);
						x += 14;
					}

					font.drawString(sender + ":", x, y, 0);
					x += font.stringWidthTaggable(sender) + 8;

					font.drawString(messageText[i], x, y, 0x800000);
				}
				line++;
			}

			if ((type == 4) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(sender)))) {
				if ((y > 0) && (y < 110)) {
					font.drawString(sender + " " + messageText[i], 4, y, 0x800080);
				}
				line++;
			}

			if ((type == 5) && (splitPrivateChat == 0) && (privateChatSetting < 2)) {
				if ((y > 0) && (y < 110)) {
					font.drawString(messageText[i], 4, y, 0x800000);
				}
				line++;
			}

			if ((type == 6) && (splitPrivateChat == 0) && (privateChatSetting < 2)) {
				if ((y > 0) && (y < 110)) {
					font.drawString("To " + sender + ":", 4, y, 0);
					font.drawString(messageText[i], 12 + font.stringWidthTaggable("To " + sender), y, 0x800000);
				}
				line++;
			}

			if ((type == 8) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(sender)))) {
				if ((y > 0) && (y < 110)) {
					font.drawString(sender + " " + messageText[i], 4, y, 0x7e3200);
				}
				line++;
			}
		}

		Draw2D.resetBounds();

		chatScrollHeight = (line * 14) + 7;

		if (chatScrollHeight < 78) {
			chatScrollHeight = 78;
		}

		drawScrollbar(463, 0, 77, chatScrollHeight, chatScrollHeight - anInt1089 - 77);

		String name;

		if ((localPlayer != null) && (localPlayer.name != null)) {
			name = localPlayer.name;
		} else {
			name = StringUtil.formatName(username);
		}

		font.drawString(name + ":", 4, 90, 0);
		font.drawString(chatTyped + "*", 6 + font.stringWidthTaggable(name + ": "), 90, 255);
		Draw2D.drawLineX(0, 77, 479, 0);
	}

	static String server = "lucas.xenorune.com";

	public Socket openSocket(int port) throws IOException {
		return new Socket(InetAddress.getByName(server), port);
	}

	public void updateMouseInput() {
		if (objDragArea != 0) {
			return;
		}

		int button = super.mousePressButton;

		if ((anInt1136 == 1) && (super.mousePressX >= 516) && (super.mousePressY >= 160) && (super.mousePressX <= 765) && (super.mousePressY <= 205)) {
			button = 0;
		}

		if (menuVisible) {
			if (button != 1) {
				int x = super.mouseX;
				int y = super.mouseY;
				if (mouseArea == 0) {
					x -= 4;
					y -= 4;
				} else if (mouseArea == 1) {
					x -= 553;
					y -= 205;
				} else if (mouseArea == 2) {
					x -= 17;
					y -= 357;
				}
				if ((x < (menuX - 10)) || (x > (menuX + menuWidth + 10)) || (y < (menuY - 10)) || (y > (menuY + menuHeight + 10))) {
					menuVisible = false;
					if (mouseArea == 1) {
						redrawInvback = true;
					}
					if (mouseArea == 2) {
						redrawChatback = true;
					}
				}
			}

			if (button == 1) {
				int l = menuX;
				int k1 = menuY;
				int i2 = menuWidth;
				int x = super.mousePressX;
				int y = super.mousePressY;
				if (mouseArea == 0) {
					x -= 4;
					y -= 4;
				} else if (mouseArea == 1) {
					x -= 553;
					y -= 205;
				} else if (mouseArea == 2) {
					x -= 17;
					y -= 357;
				}
				int i3 = -1;
				for (int j3 = 0; j3 < menuSize; j3++) {
					int k3 = k1 + 31 + ((menuSize - 1 - j3) * 15);
					if ((x > l) && (x < (l + i2)) && (y > (k3 - 13)) && (y < (k3 + 3))) {
						i3 = j3;
					}
				}
				if (i3 != -1) {
					useMenuOption(i3);
				}
				menuVisible = false;
				if (mouseArea == 1) {
					redrawInvback = true;
				}
				if (mouseArea == 2) {
					redrawChatback = true;
				}
			}
		} else {
			if ((button == 1) && (menuSize > 0)) {
				int i1 = menuAction[menuSize - 1];
				if ((i1 == 632) || (i1 == 78) || (i1 == 867) || (i1 == 431) || (i1 == 53) || (i1 == 74) || (i1 == 454) || (i1 == 539) || (i1 == 493) || (i1 == 847) || (i1 == 447) || (i1 == 1125)) {
					int objSlot = menuParamA[menuSize - 1];
					int componentId = menuParamB[menuSize - 1];
					Component component = Component.instances[componentId];
					if (component.invDraggable || component.invMoveReplaces) {
						objGrabThreshold = false;
						objDragCycles = 0;
						objDragComponentId = componentId;
						objDragSlot = objSlot;
						objDragArea = 2;
						objGrabX = super.mousePressX;
						objGrabY = super.mousePressY;
						if (Component.instances[componentId].parentId == viewportComponentId) {
							objDragArea = 1;
						}
						if (Component.instances[componentId].parentId == chatbackComponentId) {
							objDragArea = 3;
						}
						return;
					}
				}
			}

			if ((button == 1) && ((mouseButtonsOption == 1) || method17(menuSize - 1)) && (menuSize > 2)) {
				button = 2;
			}

			if ((button == 1) && (menuSize > 0)) {
				useMenuOption(menuSize - 1);
			}

			if ((button == 2) && (menuSize > 0)) {
				showContextMenu();
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
			spotanims.clear();
			aList_1013.clear();
			Draw3D.clearTexels();
			clearCaches();
			scene.clear();
			System.gc();
			for (int i = 0; i < 4; i++) {
				collisions[i].reset();
			}
			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++) {
						planeTileFlags[l][k1][j2] = 0;
					}
				}
			}
			SceneBuilder sceneBuilder = new SceneBuilder(planeTileFlags, 104, 104, planeHeightmap);
			int k2 = sceneMapLandData.length;
			out.putOp(0);
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
				out.putOp(0);
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
				out.putOp(0);
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
			out.putOp(0);
			sceneBuilder.method171(collisions, scene);
			areaViewport.bind();
			out.putOp(0);
			int k3 = SceneBuilder.minPlane;
			if (k3 > currentPlane) {
				k3 = currentPlane;
			}
			if (lowmem) {
				scene.setMinPlane(SceneBuilder.minPlane);
			} else {
				scene.setMinPlane(0);
			}
			for (int x = 0; x < 104; x++) {
				for (int z = 0; z < 104; z++) {
					sortObjStacks(x, z);
				}
			}
			method63();
		} catch (Exception ignored) {
		}

		LocType.staticCache.clear();

		if (super.frame != null) {
			out.putOp(210);
			out.put4(0x3f008edd);
		}

		if (lowmem && (Signlink.cache_dat != null)) {
			int count = ondemand.getFileCount(0);
			for (int i = 0; i < count; i++) {
				if ((ondemand.getModelFlags(i) & 0x79) == 0) {
					Model.unload(i);
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
		LocType.staticCache.clear();
		LocType.dynamicCache.clear();
		NPCType.modelCache.clear();
		ObjType.modelCache.clear();
		ObjType.iconCache.clear();
		PlayerEntity.modelCache.clear();
		SpotAnimType.modelCache.clear();
	}

	public void createMinimap(int plane) {
		int[] pixels = imageMinimap.pixels;

		Arrays.fill(pixels, 0);

		for (int z = 1; z < 103; z++) {
			int offset = (52 + (48 * 512)) + ((103 - z) * 512 * 4);

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

		int wallRGB = 0xEEEEEE;
		int doorRGB = 0xEE0000;

		imageMinimap.bind();

		for (int z = 1; z < 103; z++) {
			for (int x = 1; x < 103; x++) {
				if ((planeTileFlags[plane][x][z] & 0x18) == 0) {
					drawMinimapLoc(z, wallRGB, x, doorRGB, plane);
				}

				if ((plane < 3) && ((planeTileFlags[plane + 1][x][z] & 8) != 0)) {
					drawMinimapLoc(z, wallRGB, x, doorRGB, plane + 1);
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

				int func = LocType.get(bitset).mapfunctionIcon;

				if (func < 0) {
					continue;
				}

				int stx = x;
				int stz = z;

				if ((func != 22) && (func != 29) && (func != 34) && (func != 36) && (func != 46) && (func != 47) && (func != 48)) {
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
				activeMapFunctions[activeMapFunctionCount] = imageMapfunction[func];
				activeMapFunctionX[activeMapFunctionCount] = stx;
				activeMapFunctionZ[activeMapFunctionCount] = stz;
				activeMapFunctionCount++;
			}
		}
	}

	public void sortObjStacks(int x, int z) {
		DoublyLinkedList list = planeObjStacks[currentPlane][x][z];
		if (list == null) {
			scene.removeObjStack(currentPlane, x, z);
			return;
		}

		int k = -99999999;

		ObjStackEntity stack0 = null;
		ObjStackEntity stack1 = null;
		ObjStackEntity stack2 = null;

		for (ObjStackEntity stack = (ObjStackEntity) list.peekFront(); stack != null; stack = (ObjStackEntity) list.prev()) {
			ObjType type = ObjType.get(stack.id);

			int l = type.cost;

			if (type.stackable) {
				l *= stack.amount + 1;
			}

			if (l > k) {
				k = l;
				stack0 = stack;
			}
		}

		list.pushFront(stack0);

		for (ObjStackEntity stack = (ObjStackEntity) list.peekFront(); stack != null; stack = (ObjStackEntity) list.prev()) {
			if ((stack.id != stack0.id) && (stack1 == null)) {
				stack1 = stack;
			}
			if ((stack.id != stack0.id) && (stack.id != stack1.id) && (stack2 == null)) {
				stack2 = stack;
			}
		}

		int bitset = x + (z << 7) + 0x60000000;
		scene.addObjStack(stack0, stack1, stack2, currentPlane, x, z, getHeightmapY(currentPlane, (x * 128) + 64, (z * 128) + 64), bitset);
	}

	public void method26(boolean flag) {
		for (int j = 0; j < npcCount; j++) {
			NPCEntity npc = npcs[npcIndices[j]];
			int k = 0x20000000 + (npcIndices[j] << 14);
			if ((npc == null) || !npc.isVisible() || (npc.type.aBoolean93 != flag)) {
				continue;
			}
			int l = npc.x >> 7;
			int i1 = npc.z >> 7;
			if ((l < 0) || (l >= 104) || (i1 < 0) || (i1 >= 104)) {
				continue;
			}
			if ((npc.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
				if (anIntArrayArray929[l][i1] == sceneCycle) {
					continue;
				}
				anIntArrayArray929[l][i1] = sceneCycle;
			}
			if (!npc.type.aBoolean84) {
				k += 0x80000000;
			}
			scene.addTemporary(npc, currentPlane, npc.x, npc.z, getHeightmapY(currentPlane, npc.x, npc.z), npc.yaw, k, npc.aBoolean1541, ((npc.size - 1) * 64) + 60);
		}
	}

	public boolean wavereplay() {
		return Signlink.wavereplay();
	}

	public void method28(String s) {
		System.out.println(s);
	}

	public void handleParentComponentInput(int x, Component parent, int mouseX, int y, int mouseY, int scrollY) {
		if ((parent.type != 0) || (parent.children == null) || parent.hidden) {
			return;
		}

		if ((mouseX < x) || (mouseY < y) || (mouseX > (x + parent.width)) || (mouseY > (y + parent.height))) {
			return;
		}

		int childCount = parent.children.length;

		for (int childIndex = 0; childIndex < childCount; childIndex++) {
			int cx = parent.childX[childIndex] + x;
			int cy = (parent.childY[childIndex] + y) - scrollY;
			Component child = Component.instances[parent.children[childIndex]];

			cx += child.x;
			cy += child.y;

			if (((child.delegateHover >= 0) || (child.hoverColor != 0)) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
				if (child.delegateHover >= 0) {
					lastHoveredComponentId = child.delegateHover;
				} else {
					lastHoveredComponentId = child.id;
				}
			}

			if (child.type == 0) {
				handleParentComponentInput(cx, child, mouseX, cy, mouseY, child.scrollY);

				if (child.scrollHeight > child.height) {
					method65(cx + child.width, child.height, mouseX, mouseY, child, cy, true, child.scrollHeight);
				}
			} else {
				if ((child.optionType == 1) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					boolean flag = false;
					if (child.contentType != 0) {
						flag = method103(child);
					}
					if (!flag) {
						menuOption[menuSize] = child.option;
						menuAction[menuSize] = 315;
						menuParamB[menuSize] = child.id;
						menuSize++;
					}
				}
				if ((child.optionType == 2) && (anInt1136 == 0) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					String prefix = child.spellAction;
					if (prefix.contains(" ")) {
						prefix = prefix.substring(0, prefix.indexOf(" "));
					}
					menuOption[menuSize] = prefix + " @gre@" + child.spellName;
					menuAction[menuSize] = 626;
					menuParamB[menuSize] = child.id;
					menuSize++;
				}
				if ((child.optionType == 3) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					menuOption[menuSize] = "Close";
					menuAction[menuSize] = 200;
					menuParamB[menuSize] = child.id;
					menuSize++;
				}
				if ((child.optionType == 4) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					menuOption[menuSize] = child.option;
					menuAction[menuSize] = 169;
					menuParamB[menuSize] = child.id;
					menuSize++;
				}
				if ((child.optionType == 5) && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					menuOption[menuSize] = child.option;
					menuAction[menuSize] = 646;
					menuParamB[menuSize] = child.id;
					menuSize++;
				}
				if ((child.optionType == 6) && !aBoolean1149 && (mouseX >= cx) && (mouseY >= cy) && (mouseX < (cx + child.width)) && (mouseY < (cy + child.height))) {
					menuOption[menuSize] = child.option;
					menuAction[menuSize] = 679;
					menuParamB[menuSize] = child.id;
					menuSize++;
				}
				if (child.type == 2) {
					int k2 = 0;
					for (int l2 = 0; l2 < child.height; l2++) {
						for (int i3 = 0; i3 < child.width; i3++) {
							int j3 = cx + (i3 * (32 + child.invMarginX));
							int k3 = cy + (l2 * (32 + child.invMarginY));
							if (k2 < 20) {
								j3 += child.invSlotX[k2];
								k3 += child.invSlotY[k2];
							}
							if ((mouseX >= j3) && (mouseY >= k3) && (mouseX < (j3 + 32)) && (mouseY < (k3 + 32))) {
								hoveredSlot = k2;
								hoveredSlotParentId = child.id;
								if (child.invSlotObjId[k2] > 0) {
									ObjType type = ObjType.get(child.invSlotObjId[k2] - 1);
									if ((anInt1282 == 1) && child.aBoolean249) {
										if ((child.id != anInt1284) || (k2 != anInt1283)) {
											menuOption[menuSize] = "Use " + aString1286 + " with @lre@" + type.name;
											menuAction[menuSize] = 870;
											menuParamC[menuSize] = type.id;
											menuParamA[menuSize] = k2;
											menuParamB[menuSize] = child.id;
											menuSize++;
										}
									} else if ((anInt1136 == 1) && child.aBoolean249) {
										if ((activeSpellFlags & 0x10) == 16) {
											menuOption[menuSize] = spellCaption + " @lre@" + type.name;
											menuAction[menuSize] = 543;
											menuParamC[menuSize] = type.id;
											menuParamA[menuSize] = k2;
											menuParamB[menuSize] = child.id;
											menuSize++;
										}
									} else {
										if (child.aBoolean249) {
											for (int l3 = 4; l3 >= 3; l3--) {
												if ((type.inventoryOptions != null) && (type.inventoryOptions[l3] != null)) {
													menuOption[menuSize] = type.inventoryOptions[l3] + " @lre@" + type.name;
													if (l3 == 3) {
														menuAction[menuSize] = 493;
													}
													if (l3 == 4) {
														menuAction[menuSize] = 847;
													}
													menuParamC[menuSize] = type.id;
													menuParamA[menuSize] = k2;
													menuParamB[menuSize] = child.id;
													menuSize++;
												} else if (l3 == 4) {
													menuOption[menuSize] = "Drop @lre@" + type.name;
													menuAction[menuSize] = 847;
													menuParamC[menuSize] = type.id;
													menuParamA[menuSize] = k2;
													menuParamB[menuSize] = child.id;
													menuSize++;
												}
											}
										}
										if (child.invUsable) {
											menuOption[menuSize] = "Use @lre@" + type.name;
											menuAction[menuSize] = 447;
											menuParamC[menuSize] = type.id;
											menuParamA[menuSize] = k2;
											menuParamB[menuSize] = child.id;
											menuSize++;
										}
										if (child.aBoolean249 && (type.inventoryOptions != null)) {
											for (int i4 = 2; i4 >= 0; i4--) {
												if (type.inventoryOptions[i4] != null) {
													menuOption[menuSize] = type.inventoryOptions[i4] + " @lre@" + type.name;
													if (i4 == 0) {
														menuAction[menuSize] = 74;
													}
													if (i4 == 1) {
														menuAction[menuSize] = 454;
													}
													if (i4 == 2) {
														menuAction[menuSize] = 539;
													}
													menuParamC[menuSize] = type.id;
													menuParamA[menuSize] = k2;
													menuParamB[menuSize] = child.id;
													menuSize++;
												}
											}
										}
										if (child.invOptions != null) {
											for (int j4 = 4; j4 >= 0; j4--) {
												if (child.invOptions[j4] != null) {
													menuOption[menuSize] = child.invOptions[j4] + " @lre@" + type.name;
													if (j4 == 0) {
														menuAction[menuSize] = 632;
													}
													if (j4 == 1) {
														menuAction[menuSize] = 78;
													}
													if (j4 == 2) {
														menuAction[menuSize] = 867;
													}
													if (j4 == 3) {
														menuAction[menuSize] = 431;
													}
													if (j4 == 4) {
														menuAction[menuSize] = 53;
													}
													menuParamC[menuSize] = type.id;
													menuParamA[menuSize] = k2;
													menuParamB[menuSize] = child.id;
													menuSize++;
												}
											}
										}
										menuOption[menuSize] = "Examine @lre@" + type.name;
										menuAction[menuSize] = 1125;
										menuParamC[menuSize] = type.id;
										menuParamA[menuSize] = k2;
										menuParamB[menuSize] = child.id;
										menuSize++;
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

	public void drawScrollbar(int x, int y, int height, int scrollHeight, int scrollY) {
		imageScrollbar0.blit(x, y);
		imageScrollbar1.blit(x, (y + height) - 16);
		Draw2D.fillRect(x, y + 16, 16, height - 32, anInt1002);
		int gripSize = ((height - 32) * height) / scrollHeight;
		if (gripSize < 8) {
			gripSize = 8;
		}
		int gripY = ((height - 32 - gripSize) * scrollY) / (scrollHeight - height);
		Draw2D.fillRect(x, y + 16 + gripY, 16, gripSize, anInt1063);
		Draw2D.drawLineY(x, y + 16 + gripY, gripSize, anInt902);
		Draw2D.drawLineY(x + 1, y + 16 + gripY, gripSize, anInt902);
		Draw2D.drawLineX(x, y + 16 + gripY, 16, anInt902);
		Draw2D.drawLineX(x, y + 17 + gripY, 16, anInt902);
		Draw2D.drawLineY(x + 15, y + 16 + gripY, gripSize, anInt927);
		Draw2D.drawLineY(x + 14, y + 17 + gripY, gripSize - 1, anInt927);
		Draw2D.drawLineX(x, y + 15 + gripY + gripSize, 16, anInt927);
		Draw2D.drawLineX(x + 1, y + 14 + gripY + gripSize, 15, anInt927);
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
			Signlink.reporterror(username + " size mismatch in getnpcpos - pos:" + buffer.position + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < npcCount; i1++) {
			if (npcs[npcIndices[i1]] == null) {
				Signlink.reporterror(username + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}
		}
	}

	public void method32() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 6) && (super.mousePressX <= 106) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				publicChatSetting = (publicChatSetting + 1) % 4;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.putOp(95);
				out.put1(publicChatSetting);
				out.put1(privateChatSetting);
				out.put1(tradeChatSetting);
			}
			if ((super.mousePressX >= 135) && (super.mousePressX <= 235) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				privateChatSetting = (privateChatSetting + 1) % 3;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.putOp(95);
				out.put1(publicChatSetting);
				out.put1(privateChatSetting);
				out.put1(tradeChatSetting);
			}
			if ((super.mousePressX >= 273) && (super.mousePressX <= 373) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				tradeChatSetting = (tradeChatSetting + 1) % 3;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.putOp(95);
				out.put1(publicChatSetting);
				out.put1(privateChatSetting);
				out.put1(tradeChatSetting);
			}
			if ((super.mousePressX >= 412) && (super.mousePressX <= 512) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				if (viewportComponentId == -1) {
					method147();
					aString881 = "";
					aBoolean1158 = false;
					for (int i = 0; i < Component.instances.length; i++) {
						if ((Component.instances[i] == null) || (Component.instances[i].contentType != 600)) {
							continue;
						}
						anInt1178 = viewportComponentId = Component.instances[i].parentId;
						break;
					}
				} else {
					method77(0, "", "Please close the interface you have open before using 'report abuse'");
				}
			}
		}
	}

	public void updateVarp(int varp) {
		int key = VarpType.instances[varp].anInt709;

		if (key == 0) {
			return;
		}

		int value = variables[varp];

		if (key == 1) {
			if (value == 1) {
				Draw3D.setBrightness(0.90000000000000002D);
			}
			if (value == 2) {
				Draw3D.setBrightness(0.80000000000000004D);
			}
			if (value == 3) {
				Draw3D.setBrightness(0.69999999999999996D);
			}
			if (value == 4) {
				Draw3D.setBrightness(0.59999999999999998D);
			}
			ObjType.iconCache.clear();
			redrawTitleBackground = true;
		}
		if (key == 3) {
			boolean active = midiActive;

			if (value == 0) {
				midivol(midiActive, 0);
				midiActive = true;
			}

			if (value == 1) {
				midivol(midiActive, -400);
				midiActive = true;
			}

			if (value == 2) {
				midivol(midiActive, -800);
				midiActive = true;
			}

			if (value == 3) {
				midivol(midiActive, -1200);
				midiActive = true;
			}

			if (value == 4) {
				midiActive = false;
			}

			if ((midiActive != active) && !lowmem) {
				if (midiActive) {
					music = nextMusic;
					musicFading = true;
					ondemand.request(2, music);
				} else {
					midistop();
				}
				nextMusicDelay = 0;
			}
		}
		if (key == 4) {
			if (value == 0) {
				aBoolean848 = true;
				method111(0);
			}
			if (value == 1) {
				aBoolean848 = true;
				method111(-400);
			}
			if (value == 2) {
				aBoolean848 = true;
				method111(-800);
			}
			if (value == 3) {
				aBoolean848 = true;
				method111(-1200);
			}
			if (value == 4) {
				aBoolean848 = false;
			}
		}
		if (key == 5) {
			mouseButtonsOption = value;
		}
		if (key == 6) {
			chatEffects = value;
		}
		if (key == 8) {
			splitPrivateChat = value;
			redrawChatback = true;
		}
		if (key == 9) {
			bankArrangeMode = value;
		}
	}

	public void method34() {
		chatCount = 0;
		for (int i = -1; i < (playerCount + npcCount); i++) {
			PathingEntity e;

			if (i == -1) {
				e = localPlayer;
			} else if (i < playerCount) {
				e = players[playerIndices[i]];
			} else {
				e = npcs[npcIndices[i - playerCount]];
			}

			if ((e == null) || !e.isVisible()) {
				continue;
			}

			if (e instanceof NPCEntity) {
				NPCType type = ((NPCEntity) e).type;
				if (type.overrides != null) {
					type = type.getOverrideType();
				}
				if (type == null) {
					continue;
				}
			}

			if (i < playerCount) {
				int l = 30;
				PlayerEntity player = (PlayerEntity) e;

				if (player.headicons != 0) {
					projectToScreen(e, e.height + 15);

					if (projectX > -1) {
						for (int i2 = 0; i2 < 8; i2++) {
							if ((player.headicons & (1 << i2)) != 0) {
								imageHeadicons[i2].draw(projectX - 12, projectY - l);
								l -= 25;
							}
						}
					}
				}

				if ((i >= 0) && (hintType == 10) && (hintPlayer == playerIndices[i])) {
					projectToScreen(e, e.height + 15);

					if (projectX > -1) {
						imageHeadicons[7].draw(projectX - 12, projectY - l);
					}
				}
			} else {
				NPCType type = ((NPCEntity) e).type;

				if ((type.headicon >= 0) && (type.headicon < imageHeadicons.length)) {
					projectToScreen(e, e.height + 15);
					if (projectX > -1) {
						imageHeadicons[type.headicon].draw(projectX - 12, projectY - 30);
					}
				}
				if ((hintType == 1) && (hintNPC == npcIndices[i - playerCount]) && ((loopCycle % 20) < 10)) {
					projectToScreen(e, e.height + 15);
					if (projectX > -1) {
						imageHeadicons[2].draw(projectX - 12, projectY - 28);
					}
				}
			}

			if ((e.chat != null) && ((i >= playerCount) || (publicChatSetting == 0) || (publicChatSetting == 3) || ((publicChatSetting == 1) && isFriend(((PlayerEntity) e).name)))) {
				projectToScreen(e, e.height);

				if ((projectX > -1) && (chatCount < anInt975)) {
					chatPadding[chatCount] = fontBold12.stringWidth(e.chat) / 2;
					chatHeight[chatCount] = fontBold12.height;
					chatScreenX[chatCount] = projectX;
					chatScreenY[chatCount] = projectY;
					chatColors[chatCount] = e.chatColor;
					chatStyles[chatCount] = e.chatStyle;
					chatTimers[chatCount] = e.chatTimer;
					chatMessages[chatCount++] = e.chat;

					if ((chatEffects == 0) && (e.chatStyle >= 1) && (e.chatStyle <= 3)) {
						chatHeight[chatCount] += 10;
						chatScreenY[chatCount] += 5;
					}

					if ((chatEffects == 0) && (e.chatStyle == 4)) {
						chatPadding[chatCount] = 60;
					}

					if ((chatEffects == 0) && (e.chatStyle == 5)) {
						chatHeight[chatCount] += 5;
					}
				}
			}

			if (e.combatCycle > loopCycle) {
				projectToScreen(e, e.height + 15);

				if (projectX > -1) {
					int w = (e.health * 30) / e.totalHealth;

					if (w > 30) {
						w = 30;
					}

					Draw2D.fillRect(projectX - 15, projectY - 3, w, 5, 65280);
					Draw2D.fillRect((projectX - 15) + w, projectY - 3, 30 - w, 5, 0xff0000);
				}
			}

			for (int j = 0; j < 4; j++) {
				if (e.damageCycle[j] <= loopCycle) {
					continue;
				}

				projectToScreen(e, e.height / 2);

				if (projectX > -1) {
					if (j == 1) {
						projectY -= 20;
					}
					if (j == 2) {
						projectX -= 15;
						projectY -= 10;
					}
					if (j == 3) {
						projectX += 15;
						projectY -= 10;
					}
					imageHitmarks[e.damageType[j]].draw(projectX - 12, projectY - 12);
					fontPlain11.drawStringCenter(String.valueOf(e.damage[j]), projectX, projectY + 4, 0);
					fontPlain11.drawStringCenter(String.valueOf(e.damage[j]), projectX - 1, projectY + 3, 0xffffff);
				}
			}
		}

		for (int i = 0; i < chatCount; i++) {
			int x = chatScreenX[i];
			int y = chatScreenY[i];
			int padding = chatPadding[i];
			int height = chatHeight[i];

			boolean flag = true;

			while (flag) {
				flag = false;
				for (int l2 = 0; l2 < i; l2++) {
					if (((y + 2) > (chatScreenY[l2] - chatHeight[l2])) && ((y - height) < (chatScreenY[l2] + 2)) && ((x - padding) < (chatScreenX[l2] + chatPadding[l2])) && ((x + padding) > (chatScreenX[l2] - chatPadding[l2])) && ((chatScreenY[l2] - chatHeight[l2]) < y)) {
						y = chatScreenY[l2] - chatHeight[l2];
						flag = true;
					}
				}
			}

			projectX = chatScreenX[i];
			projectY = chatScreenY[i] = y;

			String message = chatMessages[i];

			if (chatEffects == 0) {
				int color = 0xffff00;

				if (chatColors[i] < 6) {
					color = CHAT_COLORS[chatColors[i]];
				}

				if (chatColors[i] == 6) {
					color = ((sceneCycle % 20) >= 10) ? 0xffff00 : 0xff0000;
				}

				if (chatColors[i] == 7) {
					color = ((sceneCycle % 20) >= 10) ? 65535 : 255;
				}

				if (chatColors[i] == 8) {
					color = ((sceneCycle % 20) >= 10) ? 0x80ff80 : 45056;
				}

				if (chatColors[i] == 9) {
					int delta = 150 - chatTimers[i];

					if (delta < 50) {
						color = 0xff0000 + (1280 * delta);
					} else if (delta < 100) {
						color = 0xffff00 - (0x50000 * (delta - 50));
					} else if (delta < 150) {
						color = 65280 + (5 * (delta - 100));
					}
				}

				if (chatColors[i] == 10) {
					int delta = 150 - chatTimers[i];

					if (delta < 50) {
						color = 0xff0000 + (5 * delta);
					} else if (delta < 100) {
						color = 0xff00ff - (0x50000 * (delta - 50));
					} else if (delta < 150) {
						color = (255 + (0x50000 * (delta - 100))) - (5 * (delta - 100));
					}
				}
				if (chatColors[i] == 11) {
					int delta = 150 - chatTimers[i];

					if (delta < 50) {
						color = 0xffffff - (0x50005 * delta);
					} else if (delta < 100) {
						color = 65280 + (0x50005 * (delta - 50));
					} else if (delta < 150) {
						color = 0xffffff - (0x50000 * (delta - 100));
					}
				}

				if (chatStyles[i] == 0) {
					fontBold12.drawStringCenter(message, projectX, projectY + 1, 0);
					fontBold12.drawStringCenter(message, projectX, projectY, color);
				}

				if (chatStyles[i] == 1) {
					fontBold12.drawStringWave(message, projectX, projectY + 1, 0, sceneCycle);
					fontBold12.drawStringWave(message, projectX, projectY, color, sceneCycle);
				}

				if (chatStyles[i] == 2) {
					fontBold12.drawStringWave2(message, projectX, projectY + 1, 0, sceneCycle);
					fontBold12.drawStringWave2(message, projectX, projectY, color, sceneCycle);
				}

				if (chatStyles[i] == 3) {
					fontBold12.drawStringShake(message, projectX, projectY + 1, 0, sceneCycle, 150 - chatTimers[i]);
					fontBold12.drawStringShake(message, projectX, projectY, color, sceneCycle, 150 - chatTimers[i]);
				}

				if (chatStyles[i] == 4) {
					int w = fontBold12.stringWidth(message);
					int offsetX = ((150 - chatTimers[i]) * (w + 100)) / 150;
					Draw2D.setBounds(projectX - 50, 0, projectX + 50, 334);
					fontBold12.drawString(message, (projectX + 50) - offsetX, projectY + 1, 0);
					fontBold12.drawString(message, (projectX + 50) - offsetX, projectY, color);
					Draw2D.resetBounds();
				}

				if (chatStyles[i] == 5) {
					int delta = 150 - chatTimers[i];
					int slide = 0;
					if (delta < 25) {
						slide = delta - 25;
					} else if (delta > 125) {
						slide = delta - 125;
					}
					Draw2D.setBounds(0, projectY - fontBold12.height - 1, 512, projectY + 5);
					fontBold12.drawStringCenter(message, projectX, projectY + 1 + slide, 0);
					fontBold12.drawStringCenter(message, projectX, projectY + slide, color);
					Draw2D.resetBounds();
				}
			} else {
				fontBold12.drawStringCenter(message, projectX, projectY + 1, 0);
				fontBold12.drawStringCenter(message, projectX, projectY, 0xffff00);
			}
		}
	}

	public void removeFriend(long name37) {
		if (name37 == 0L) {
			return;
		}
		for (int i = 0; i < friendCount; i++) {
			if (friendName37[i] != name37) {
				continue;
			}
			friendCount--;
			redrawInvback = true;
			for (int j = i; j < friendCount; j++) {
				friendName[j] = friendName[j + 1];
				friendWorld[j] = friendWorld[j + 1];
				friendName37[j] = friendName37[j + 1];
			}
			out.putOp(215);
			out.put8(name37);
			break;
		}
	}

	public void drawInvback() {
		areaInvback.bind();
		Draw3D.lineOffset = areaInvbackOffsets;
		imageInvback.blit(0, 0);

		if (invbackComponentId != -1) {
			drawParentComponent(Component.instances[invbackComponentId], 0, 0, 0);
		} else if (tabComponentId[selectedTab] != -1) {
			drawParentComponent(Component.instances[tabComponentId[selectedTab]], 0, 0, 0);
		}

		if (menuVisible && (mouseArea == 1)) {
			drawMenu();
		}

		areaInvback.draw(super.graphics, 553, 205);
		areaViewport.bind();
		Draw3D.lineOffset = areaViewportOffsets;
	}

	public void method37(int j) {
		if (!lowmem) {
			if (Draw3D.textureCycle[17] >= j) {
				Image8 image = Draw3D.textures[17];
				int k = (image.width * image.height) - 1;
				int j1 = image.width * delta * 2;
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
				int k1 = class30_sub2_sub1_sub2_1.width * delta * 2;
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
				int l1 = class30_sub2_sub1_sub2_2.width * delta * 2;
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

	public void updateChatTimers() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1) {
				j = LOCAL_PLAYER_INDEX;
			} else {
				j = playerIndices[i];
			}
			PlayerEntity player = players[j];
			if ((player != null) && (player.chatTimer > 0)) {
				player.chatTimer--;
				if (player.chatTimer == 0) {
					player.chat = null;
				}
			}
		}

		for (int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			NPCEntity npc = npcs[l];
			if ((npc != null) && (npc.chatTimer > 0)) {
				npc.chatTimer--;
				if (npc.chatTimer == 0) {
					npc.chat = null;
				}
			}
		}
	}

	public void method39() {
		int x = (anInt1098 * 128) + 64;
		int z = (anInt1099 * 128) + 64;
		int y = getHeightmapY(currentPlane, x, z) - anInt1100;

		if (anInt858 < x) {
			anInt858 += anInt1101 + (((x - anInt858) * anInt1102) / 1000);
			if (anInt858 > x) {
				anInt858 = x;
			}
		}
		if (anInt858 > x) {
			anInt858 -= anInt1101 + (((anInt858 - x) * anInt1102) / 1000);
			if (anInt858 < x) {
				anInt858 = x;
			}
		}
		if (anInt859 < y) {
			anInt859 += anInt1101 + (((y - anInt859) * anInt1102) / 1000);
			if (anInt859 > y) {
				anInt859 = y;
			}
		}
		if (anInt859 > y) {
			anInt859 -= anInt1101 + (((anInt859 - y) * anInt1102) / 1000);
			if (anInt859 < y) {
				anInt859 = y;
			}
		}
		if (anInt860 < z) {
			anInt860 += anInt1101 + (((z - anInt860) * anInt1102) / 1000);
			if (anInt860 > z) {
				anInt860 = z;
			}
		}
		if (anInt860 > z) {
			anInt860 -= anInt1101 + (((anInt860 - z) * anInt1102) / 1000);
			if (anInt860 < z) {
				anInt860 = z;
			}
		}
		x = (anInt995 * 128) + 64;
		z = (anInt996 * 128) + 64;
		y = getHeightmapY(currentPlane, x, z) - anInt997;
		int l = x - anInt858;
		int i1 = y - anInt859;
		int j1 = z - anInt860;
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

	public void drawMenu() {
		int x = menuX;
		int y = menuY;
		int w = menuWidth;
		int h = menuHeight;
		int background = 0x5d5447;

		Draw2D.fillRect(x, y, w, h, background);
		Draw2D.fillRect(x + 1, y + 1, w - 2, 16, 0);
		Draw2D.drawRect(x + 1, y + 18, w - 2, h - 19, 0);
		fontBold12.drawString("Choose Option", x + 3, y + 14, background);

		int mx = super.mouseX;
		int my = super.mouseY;

		if (mouseArea == 0) {
			mx -= 4;
			my -= 4;
		}

		if (mouseArea == 1) {
			mx -= 553;
			my -= 205;
		}

		if (mouseArea == 2) {
			mx -= 17;
			my -= 357;
		}

		for (int i = 0; i < menuSize; i++) {
			int optionY = y + 31 + ((menuSize - 1 - i) * 15);
			int rgb = 0xffffff;

			if ((mx > x) && (mx < (x + w)) && (my > (optionY - 13)) && (my < (optionY + 3))) {
				rgb = 0xffff00;
			}

			fontBold12.drawStringTaggable(menuOption[i], x + 3, optionY, rgb, true);
		}
	}

	public void addFriend(long name37) {
		if (name37 == 0L) {
			return;
		}
		if ((friendCount >= 100) && (anInt1046 != 1)) {
			method77(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		if (friendCount >= 200) {
			method77(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(name37));
		for (int i = 0; i < friendCount; i++) {
			if (friendName37[i] == name37) {
				method77(0, "", s + " is already on your friend list");
				return;
			}
		}
		for (int j = 0; j < ignoreCount; j++) {
			if (ignoreName37[j] == name37) {
				method77(0, "", "Please remove " + s + " from your ignore list first");
				return;
			}
		}
		if (!s.equals(localPlayer.name)) {
			friendName[friendCount] = s;
			friendName37[friendCount] = name37;
			friendWorld[friendCount] = 0;
			friendCount++;
			redrawInvback = true;
			out.putOp(188);
			out.put8(name37);
		}
	}

	public int getHeightmapY(int plane, int sceneX, int sceneZ) {
		int stx = sceneX >> 7;
		int stz = sceneZ >> 7;
		if ((stx < 0) || (stz < 0) || (stx > 103) || (stz > 103)) {
			return 0;
		}
		int p = plane;
		if ((p < 3) && ((planeTileFlags[1][stx][stz] & 2) == 2)) {
			p++;
		}
		int ltx = sceneX & 0x7f;
		int ltz = sceneZ & 0x7f;
		int y00 = ((planeHeightmap[p][stx][stz] * (128 - ltx)) + (planeHeightmap[p][stx + 1][stz] * ltx)) >> 7;
		int y11 = ((planeHeightmap[p][stx][stz + 1] * (128 - ltx)) + (planeHeightmap[p][stx + 1][stz + 1] * ltx)) >> 7;
		return ((y00 * (128 - ltz)) + (y11 * ltz)) >> 7;
	}

	public void disconnect() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ignored) {
		}
		connection = null;
		ingame = false;
		titleScreenState = 0;
		username = "";
		password = "";
		clearCaches();
		scene.clear();
		for (int i = 0; i < 4; i++) {
			collisions[i].reset();
		}
		System.gc();
		midistop();
		nextMusic = -1;
		music = -1;
		nextMusicDelay = 0;
	}

	public void validateCharacterDesign() {
		updateDesignModel = true;
		for (int part = 0; part < 7; part++) {
			designIdentikits[part] = -1;
			for (int kit = 0; kit < IDKType.count; kit++) {
				if (IDKType.instances[kit].selectable || (IDKType.instances[kit].type != (part + (designGender ? 0 : 7)))) {
					continue;
				}
				designIdentikits[part] = kit;
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
			npcIndices[npcCount++] = k;
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
			npc.seqWalk = npc.type.seqWalkId;
			npc.seqTurnAround = npc.type.seqTurnAroundId;
			npc.seqTurnLeft = npc.type.seqTurnLeft;
			npc.seqTurnRight = npc.type.seqTurnRightId;
			npc.seqStand = npc.type.seqStand;
			npc.move(localPlayer.pathTileX[0] + x, localPlayer.pathTileZ[0] + z, j1 == 1);
		}
		buffer.accessBytes();
	}

	public void method47(boolean local) {
		if (((localPlayer.x >> 7) == flagSceneTileX) && ((localPlayer.z >> 7) == flagSceneTileZ)) {
			flagSceneTileX = 0;
		}

		int j = playerCount;

		if (local) {
			j = 1;
		}

		for (int l = 0; l < j; l++) {
			PlayerEntity player;
			int index;

			if (local) {
				player = localPlayer;
				index = LOCAL_PLAYER_INDEX << 14;
			} else {
				player = players[playerIndices[l]];
				index = playerIndices[l] << 14;
			}

			if ((player == null) || !player.isVisible()) {
				continue;
			}

			player.lowmem = ((lowmem && (playerCount > 50)) || (playerCount > 200)) && !local && (player.seqId2 == player.seqStand);

			int stx = player.x >> 7;
			int stz = player.z >> 7;

			if ((stx < 0) || (stx >= 104) || (stz < 0) || (stz >= 104)) {
				continue;
			}

			if ((player.locModel != null) && (loopCycle >= player.locStartCycle) && (loopCycle < player.locStopCycle)) {
				player.lowmem = false;
				player.y = getHeightmapY(currentPlane, player.x, player.z);
				scene.addTemporary(player, currentPlane, player.minSceneTileX, player.minSceneTileZ, player.maxSceneTileX, player.maxSceneTileZ, player.x, player.z, player.y, player.yaw, index);
				continue;
			}

			if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
				if (anIntArrayArray929[stx][stz] == sceneCycle) {
					continue;
				}
				anIntArrayArray929[stx][stz] = sceneCycle;
			}

			player.y = getHeightmapY(currentPlane, player.x, player.z);
			scene.addTemporary(player, currentPlane, player.x, player.z, player.y, player.yaw, index, player.aBoolean1541, 60);
		}
	}

	/**
	 * Handles a components action.
	 *
	 * @param component the component.
	 * @return <code>false</code> to suppress packet 185.
	 */
	public boolean handleComponentAction(Component component) {
		int type = component.contentType;

		if (socialState == 2) {
			if (type == 201) {
				redrawChatback = true;
				chatbackInputType = 0;
				showSocialInput = true;
				socialInput = "";
				socialAction = 1;
				socialMessage = "Enter name of friend to add to list";
			}
			if (type == 202) {
				redrawChatback = true;
				chatbackInputType = 0;
				showSocialInput = true;
				socialInput = "";
				socialAction = 2;
				socialMessage = "Enter name of friend to delete from list";
			}
		}
		if (type == 205) {
			idleTimeout = 250;
			return true;
		}
		if (type == 501) {
			redrawChatback = true;
			chatbackInputType = 0;
			showSocialInput = true;
			socialInput = "";
			socialAction = 4;
			socialMessage = "Enter name of player to add to list";
		}
		if (type == 502) {
			redrawChatback = true;
			chatbackInputType = 0;
			showSocialInput = true;
			socialInput = "";
			socialAction = 5;
			socialMessage = "Enter name of player to delete from list";
		}
		if ((type >= 300) && (type <= 313)) {
			int part = (type - 300) / 2;
			int direction = type & 1;
			int kit = designIdentikits[part];

			if (kit != -1) {
				do {
					if ((direction == 0) && (--kit < 0)) {
						kit = IDKType.count - 1;
					}
					if ((direction == 1) && (++kit >= IDKType.count)) {
						kit = 0;
					}
				} while (IDKType.instances[kit].selectable || (IDKType.instances[kit].type != (part + (designGender ? 0 : 7))));

				designIdentikits[part] = kit;
				updateDesignModel = true;
			}
		}
		if ((type >= 314) && (type <= 323)) {
			int part = (type - 314) / 2;
			int direction = type & 1;
			int color = designColors[part];

			if ((direction == 0) && (--color < 0)) {
				color = designPartColor[part].length - 1;
			}

			if ((direction == 1) && (++color >= designPartColor[part].length)) {
				color = 0;
			}

			designColors[part] = color;
			updateDesignModel = true;
		}
		if ((type == 324) && !designGender) {
			designGender = true;
			validateCharacterDesign();
		}
		if ((type == 325) && designGender) {
			designGender = false;
			validateCharacterDesign();
		}
		if (type == 326) {
			out.putOp(101);
			out.put1(designGender ? 0 : 1);
			for (int i = 0; i < 7; i++) {
				out.put1(designIdentikits[i]);
			}
			for (int i = 0; i < 5; i++) {
				out.put1(designColors[i]);
			}
			return true;
		}
		if ((type >= 601) && (type <= 612)) {
			method147();
			if (aString881.length() > 0) {
				out.putOp(218);
				out.put8(StringUtil.toBase37(aString881));
				out.put1(type - 601);
				out.put1(aBoolean1158 ? 1 : 0);
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

	public void drawMinimapLoc(int z, int wallRGB, int x, int doorRGB, int plane) {
		int bitset = scene.getWallBitset(plane, x, z);

		if (bitset != 0) {
			int info = scene.getInfo(plane, x, z, bitset);
			int rotation = (info >> 6) & 3;
			int kind = info & 0x1f;
			int rgb = wallRGB;

			if (bitset > 0) {
				rgb = doorRGB;
			}

			int[] dst = imageMinimap.pixels;
			int offset = 24624 + (x * 4) + ((103 - z) * 512 * 4);

			int locID= (bitset >> 14) & 0x7fff;
			LocType type = LocType.get(locID);

			if (type.mapsceneIcon != -1) {
				Image8 icon = imageMapscene[type.mapsceneIcon];
				if (icon != null) {
					int offsetX = ((type.width * 4) - icon.width) / 2;
					int offsetY = ((type.length * 4) - icon.height) / 2;
					icon.blit(48 + (x * 4) + offsetX, 48 + ((104 - z - type.length) * 4) + offsetY);
				}
			} else {
				if ((kind == 0) || (kind == 2)) {
					if (rotation == 0) {
						dst[offset] = rgb;
						dst[offset + 512] = rgb;
						dst[offset + 1024] = rgb;
						dst[offset + 1536] = rgb;
					} else if (rotation == 1) {
						dst[offset] = rgb;
						dst[offset + 1] = rgb;
						dst[offset + 2] = rgb;
						dst[offset + 3] = rgb;
					} else if (rotation == 2) {
						dst[offset + 3] = rgb;
						dst[offset + 3 + 512] = rgb;
						dst[offset + 3 + 1024] = rgb;
						dst[offset + 3 + 1536] = rgb;
					} else if (rotation == 3) {
						dst[offset + 1536] = rgb;
						dst[offset + 1536 + 1] = rgb;
						dst[offset + 1536 + 2] = rgb;
						dst[offset + 1536 + 3] = rgb;
					}
				}
				if (kind == 3) {
					if (rotation == 0) {
						dst[offset] = rgb;
					} else if (rotation == 1) {
						dst[offset + 3] = rgb;
					} else if (rotation == 2) {
						dst[offset + 3 + 1536] = rgb;
					} else if (rotation == 3) {
						dst[offset + 1536] = rgb;
					}
				}
				if (kind == 2) {
					if (rotation == 3) {
						dst[offset] = rgb;
						dst[offset + 512] = rgb;
						dst[offset + 1024] = rgb;
						dst[offset + 1536] = rgb;
					} else if (rotation == 0) {
						dst[offset] = rgb;
						dst[offset + 1] = rgb;
						dst[offset + 2] = rgb;
						dst[offset + 3] = rgb;
					} else if (rotation == 1) {
						dst[offset + 3] = rgb;
						dst[offset + 3 + 512] = rgb;
						dst[offset + 3 + 1024] = rgb;
						dst[offset + 3 + 1536] = rgb;
					} else if (rotation == 2) {
						dst[offset + 1536] = rgb;
						dst[offset + 1536 + 1] = rgb;
						dst[offset + 1536 + 2] = rgb;
						dst[offset + 1536 + 3] = rgb;
					}
				}
			}
		}

		bitset = scene.getLocBitset(plane, x, z);

		if (bitset != 0) {
			int info = scene.getInfo(plane, x, z, bitset);
			int rotation = (info >> 6) & 3;
			int kind = info & 0x1f;
			int locId = (bitset >> 14) & 0x7fff;
			LocType type = LocType.get(locId);

			if (type.mapsceneIcon != -1) {
				Image8 icon = imageMapscene[type.mapsceneIcon];

				if (icon != null) {
					int offsetX = ((type.width * 4) - icon.width) / 2;
					int offsetY = ((type.length * 4) - icon.height) / 2;
					icon.blit(48 + (x * 4) + offsetX, 48 + ((104 - z - type.length) * 4) + offsetY);
				}
			} else if (kind == 9) {
				int rgb = 0xEEEEEE;

				if (bitset > 0) {
					rgb = 0xEE0000;
				}

				int[] dst = imageMinimap.pixels;
				int offset = 24624 + (x * 4) + ((103 - z) * 512 * 4);

				if ((rotation == 0) || (rotation == 2)) {
					dst[offset + 1536] = rgb;
					dst[offset + 1024 + 1] = rgb;
					dst[offset + 512 + 2] = rgb;
					dst[offset + 3] = rgb;
				} else {
					dst[offset] = rgb;
					dst[offset + 512 + 1] = rgb;
					dst[offset + 1024 + 2] = rgb;
					dst[offset + 1536 + 3] = rgb;
				}
			}
		}

		bitset = scene.getGroundDecorationBitset(plane, x, z);

		if (bitset != 0) {
			int locId = (bitset >> 14) & 0x7fff;
			LocType type = LocType.get(locId);

			if (type.mapsceneIcon != -1) {
				Image8 icon = imageMapscene[type.mapsceneIcon];

				if (icon != null) {
					int offsetX = ((type.width * 4) - icon.width) / 2;
					int offsetY = ((type.length * 4) - icon.height) / 2;
					icon.blit(48 + (x * 4) + offsetX, 48 + ((104 - z - type.length) * 4) + offsetY);
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
		updateFlameBuffer(null);
		flameBuffer3 = new int[32768];
		flameBuffer2 = new int[32768];
		showProgress(10, "Connecting to fileserver");

		if (!flameActive) {
			flamesThread = true;
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
				Signlink.reporterror(username + " glcfb " + aLong1215 + "," + j + "," + lowmem + "," + filestores[0] + "," + ondemand.remaining() + "," + currentPlane + "," + sceneCenterZoneX + "," + sceneCenterZoneZ);
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
			out.putOp(121);
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
					if (j == localPlayerId) {
						player = localPlayer;
					} else {
						player = players[j];
					}
					if ((player != null) && (player.x >= 0) && (player.x < 13312) && (player.z >= 0) && (player.z < 13312)) {
						projectile.method455(loopCycle, player.z, getHeightmapY(projectile.anInt1597, player.x, player.z) - projectile.anInt1583, player.x);
					}
				}
				projectile.method456(delta);
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

	public void handleOnDemandRequests() throws IOException {
		do {
			OnDemandRequest request;
			do {
				request = ondemand.poll();

				if (request == null) {
					return;
				}

				if (request.store == 0) {
					Model.unpack(request.data, request.file);
					if ((ondemand.getModelFlags(request.file) & 0x62) != 0) {
						redrawInvback = true;
						if (chatbackComponentId != -1) {
							redrawChatback = true;
						}
					}
				}

				if ((request.store == 1) && (request.data != null)) {
					SeqFrame.unpack(request.data);
				}

				if ((request.store == 2) && (request.file == music) && (request.data != null)) {
					midisave(musicFading, request.data);
				}

				if ((request.store == 3) && sceneState == 1) {
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
			updateFlameBuffer(imageRunes[(int) (Math.random() * 12D)]);
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

	public boolean wavesave(byte[] src, int len) {
		if (src == null) {
			return true;
		} else {
			return Signlink.wavesave(src, len);
		}
	}

	public void resetParentComponentSeq(int parentId) {
		Component parent = Component.instances[parentId];
		for (int childId = 0; childId < parent.children.length; childId++) {
			if (parent.children[childId] == -1) {
				break;
			}
			Component child = Component.instances[parent.children[childId]];
			if (child.type == 1) {
				resetParentComponentSeq(child.id);
			}
			child.seqFrame = 0;
			child.seqCycle = 0;
		}
	}

	public void drawTileHint() {
		if (hintType != 2) {
			return;
		}
		projectToScreen(((hintTileX - sceneBaseTileX) << 7) + hintOffsetX, hintHeight * 2, ((hintTileZ - sceneBaseTileZ) << 7) + hintOffsetZ);
		if ((projectX > -1) && ((loopCycle % 20) < 10)) {
			imageHeadicons[2].draw(projectX - 12, projectY - 28);
		}
	}

	public void updateGame() {
		if (systemUpdateTimer > 1) {
			systemUpdateTimer--;
		}
		if (idleTimeout > 0) {
			idleTimeout--;
		}
		for (int j = 0; j < 5; j++) {
			if (!read()) {
				break;
			}
		}
		if (!ingame) {
			return;
		}
		synchronized (mouseRecorder.lock) {
			if (aBoolean1205) {
				if ((super.mousePressButton != 0) || (mouseRecorder.anInt810 >= 40)) {
					out.putOp(45);
					out.put1(0);
					int j2 = out.position;
					int j3 = 0;
					for (int j4 = 0; j4 < mouseRecorder.anInt810; j4++) {
						if ((j2 - out.position) >= 240) {
							break;
						}
						j3++;
						int l4 = mouseRecorder.anIntArray807[j4];
						if (l4 < 0) {
							l4 = 0;
						} else if (l4 > 502) {
							l4 = 502;
						}
						int k5 = mouseRecorder.anIntArray809[j4];
						if (k5 < 0) {
							k5 = 0;
						} else if (k5 > 764) {
							k5 = 764;
						}
						int i6 = (l4 * 765) + k5;
						if ((mouseRecorder.anIntArray807[j4] == -1) && (mouseRecorder.anIntArray809[j4] == -1)) {
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
								out.put2((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else if (anInt1022 < 8) {
								out.put3(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else {
								out.put4(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}
					out.putSize1(out.position - j2);
					if (j3 >= mouseRecorder.anInt810) {
						mouseRecorder.anInt810 = 0;
					} else {
						mouseRecorder.anInt810 -= j3;
						for (int i5 = 0; i5 < mouseRecorder.anInt810; i5++) {
							mouseRecorder.anIntArray809[i5] = mouseRecorder.anIntArray809[i5 + j3];
							mouseRecorder.anIntArray807[i5] = mouseRecorder.anIntArray807[i5 + j3];
						}
					}
				}
			} else {
				mouseRecorder.anInt810 = 0;
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
			out.putOp(241);
			out.put4((l5 << 20) + (j5 << 19) + k4);
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
			out.putOp(86);
			out.put2(cameraPitch);
			out.put2A(cameraYaw);
		}
		if (super.focused && !_focused) {
			_focused = true;
			out.putOp(3);
			out.put1(1);
		}
		if (!super.focused && _focused) {
			_focused = false;
			out.putOp(3);
			out.put1(0);
		}
		method53();
		method115();
		updateAudio();
		anInt1009++;
		if (anInt1009 > 750) {
			tryReconnect();
		}
		method114();
		method95();
		updateChatTimers();
		delta++;
		if (crossMode != 0) {
			crossCycle += 20;
			if (crossCycle >= 400) {
				crossMode = 0;
			}
		}
		if (anInt1246 != 0) {
			anInt1243++;
			if (anInt1243 >= 15) {
				if (anInt1246 == 2) {
					redrawInvback = true;
				}
				if (anInt1246 == 3) {
					redrawChatback = true;
				}
				anInt1246 = 0;
			}
		}
		if (objDragArea != 0) {
			objDragCycles++;

			// mouse is greater than 5px from grab point in any direction, trigger treshold
			if ((super.mouseX > (objGrabX + 5)) || (super.mouseX < (objGrabX - 5)) || (super.mouseY > (objGrabY + 5)) || (super.mouseY < (objGrabY - 5))) {
				objGrabThreshold = true;
			}

			if (super.mouseButton == 0) {
				if (objDragArea == 2) {
					redrawInvback = true;
				}

				if (objDragArea == 3) {
					redrawChatback = true;
				}

				objDragArea = 0;

				// mouse moved at least 5px and have been holding obj for 100ms or longer
				if (objGrabThreshold && (objDragCycles >= 5)) {
					hoveredSlotParentId = -1;
					method82();

					if ((hoveredSlotParentId == objDragComponentId) && (hoveredSlot != objDragSlot)) {
						Component component = Component.instances[objDragComponentId];

						// mode 0 = swap
						// mode 1 = insert
						int mode = 0;

						if ((bankArrangeMode == 1) && (component.contentType == 206)) {
							mode = 1;
						}

						if (component.invSlotObjId[hoveredSlot] <= 0) {
							mode = 0;
						}

						if (component.invMoveReplaces) {
							int src = objDragSlot;
							int dst = hoveredSlot;
							component.invSlotObjId[dst] = component.invSlotObjId[src];
							component.invSlotAmount[dst] = component.invSlotAmount[src];
							component.invSlotObjId[src] = -1;
							component.invSlotAmount[src] = 0;
						} else if (mode == 1) {
							int src = objDragSlot;
							for (int dst = hoveredSlot; src != dst; ) {
								if (src > dst) {
									component.swapSlots(src, src - 1);
									src--;
								} else {
									component.swapSlots(src, src + 1);
									src++;
								}
							}
						} else {
							component.swapSlots(objDragSlot, hoveredSlot);
						}
						out.putOp(214);
						out.put2LEA(objDragComponentId);
						out.put1C(mode);
						out.put2LEA(objDragSlot);
						out.put2LE(hoveredSlot);
					}
				} else if (((mouseButtonsOption == 1) || method17(menuSize - 1)) && (menuSize > 2)) {
					showContextMenu();
				} else if (menuSize > 0) {
					useMenuOption(menuSize - 1);
				}
				anInt1243 = 10;
				super.mousePressButton = 0;
			}
		}
		if (Scene.anInt470 != -1) {
			int k = Scene.anInt470;
			int k1 = Scene.anInt471;
			boolean flag = tryMove(0, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], k, k1, 0, 0, 0, 0, 0, true);
			Scene.anInt470 = -1;
			if (flag) {
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 1;
				crossCycle = 0;
			}
		}
		if ((super.mousePressButton == 1) && (chatbackMessage != null)) {
			chatbackMessage = null;
			redrawChatback = true;
			super.mousePressButton = 0;
		}
		updateMouseInput();
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

		updateKeyboardInput();
		updateIdleCycles();

		heartbeatTimer++;

		if (heartbeatTimer > 50) {
			out.putOp(0);
		}

		try {
			if ((connection != null) && (out.position > 0)) {
				connection.write(out.data, 0, out.position);
				out.position = 0;
				heartbeatTimer = 0;
			}
		} catch (IOException _ex) {
			tryReconnect();
		} catch (Exception exception) {
			disconnect();
		}
	}

	private void updateIdleCycles() {
		super.idleCycles++;

		if (super.idleCycles > 4500) {
			idleTimeout = 250;
			super.idleCycles -= 500;
			out.putOp(202);
		}
	}

	public void method63() {
		SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront();
		for (; loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
			if (loc.anInt1294 == -1) {
				loc.anInt1302 = 0;
				storeLoc(loc);
			} else {
				loc.unlink();
			}
		}
	}

	/**
	 * Prepares all the title screen components (flames, background, buttons, etc.) and unloads the game screen
	 * components if the title screen hasn't been prepared already.
	 *
	 * @throws IOException
	 */
	public void prepareTitleScreen() throws IOException {
		if (imageTitle2 != null) {
			return;
		}
		areaChatback = null;
		areaMapback = null;
		areaInvback = null;
		areaViewport = null;
		areaBackbase1 = null;
		areaBackbase2 = null;
		areaBackhmid1 = null;
		imageTitle0 = new DrawArea(128, 265);
		Draw2D.clear();
		imageTitle1 = new DrawArea(128, 265);
		Draw2D.clear();
		imageTitle2 = new DrawArea(509, 171);
		Draw2D.clear();
		imageTitle3 = new DrawArea(360, 132);
		Draw2D.clear();
		imageTitle4 = new DrawArea(360, 200);
		Draw2D.clear();
		imageTitle5 = new DrawArea(202, 238);
		Draw2D.clear();
		imageTitle6 = new DrawArea(203, 238);
		Draw2D.clear();
		imageTitle7 = new DrawArea(74, 94);
		Draw2D.clear();
		imageTitle8 = new DrawArea(75, 94);
		Draw2D.clear();
		if (archiveTitle != null) {
			createTitleBackground();
			createTitleImages();
		}
		redrawTitleBackground = true;
	}

	public void method65(int i, int j, int k, int l, Component component, int i1, boolean flag, int j1) {
		if (aBoolean972) {
			anInt992 = 32;
		} else {
			anInt992 = 0;
		}

		aBoolean972 = false;

		if ((k >= i) && (k < (i + 16)) && (l >= i1) && (l < (i1 + 16))) {
			component.scrollY -= anInt1213 * 4;
			if (flag) {
				redrawInvback = true;
			}
		} else if ((k >= i) && (k < (i + 16)) && (l >= ((i1 + j) - 16)) && (l < (i1 + j))) {
			component.scrollY += anInt1213 * 4;
			if (flag) {
				redrawInvback = true;
			}
		} else if ((k >= (i - anInt992)) && (k < (i + 16 + anInt992)) && (l >= (i1 + 16)) && (l < ((i1 + j) - 16)) && (anInt1213 > 0)) {
			int l1 = ((j - 32) * j) / j1;
			if (l1 < 8) {
				l1 = 8;
			}
			int i2 = l - i1 - 16 - (l1 / 2);
			int j2 = j - 32 - l1;
			component.scrollY = ((j1 - j) * i2) / j2;
			if (flag) {
				redrawInvback = true;
			}
			aBoolean972 = true;
		}
	}

	public boolean interactWithLoc(int bitset, int x, int z) {
		int locId = (bitset >> 14) & 0x7fff;
		int info = scene.getInfo(currentPlane, x, z, bitset);
		if (info == -1) {
			return false;
		}
		int type = info & 0x1f;
		int angle = (info >> 6) & 3;
		if ((type == 10) || (type == 11) || (type == 22)) {
			LocType loc = LocType.get(locId);
			int width;
			int length;
			if ((angle == 0) || (angle == 2)) {
				width = loc.width;
				length = loc.length;
			} else {
				width = loc.length;
				length = loc.width;
			}
			int interactionFlags = loc.interactionSideFlags;
			if (angle != 0) {
				interactionFlags = ((interactionFlags << angle) & 0xf) + (interactionFlags >> (4 - angle));
			}
			tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, 0, width, length, 0, interactionFlags, false);
		} else {
			tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, type + 1, 0, 0, angle, 0, false);
		}
		crossX = super.mousePressX;
		crossY = super.mousePressY;
		crossMode = 2;
		crossCycle = 0;
		return true;
	}

	public FileArchive loadArchive(int fileId, String caption, String fileName, int checksum, int progress) throws IOException {
		byte[] data = null;
		int wait = 5;
		try {
			if (filestores[0] != null) {
				data = filestores[0].read(fileId);
			}
		} catch (Exception ignored) {
		}
		if (data != null) {
			crc32.reset();
			crc32.update(data);
			if ((int) crc32.getValue() != checksum) {
				data = null;
			}
		}
		if (data != null) {
			return new FileArchive(data);
		}
		int j1 = 0;
		while (data == null) {
			String s2 = "Unknown error";
			showProgress(progress, "Requesting " + caption);
			try {
				int k1 = 0;
				DataInputStream datainputstream = openURL(fileName + checksum);
				byte[] abyte1 = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Buffer buffer = new Buffer(abyte1);
				buffer.position = 3;
				int i2 = buffer.get3() + 6;
				int j2 = 6;
				data = new byte[i2];
				for (int k2 = 0; k2 < 6; k2++) {
					data[k2] = abyte1[k2];
				}
				while (j2 < i2) {
					int l2 = i2 - j2;
					if (l2 > 1000) {
						l2 = 1000;
					}
					int j3 = datainputstream.read(data, j2, l2);
					if (j3 < 0) {
						s2 = "Length error: " + j2 + "/" + i2;
						throw new IOException("EOF");
					}
					j2 += j3;
					int k3 = (j2 * 100) / i2;
					if (k3 != k1) {
						showProgress(progress, "Loading " + caption + " - " + k3 + "%");
					}
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (filestores[0] != null) {
						filestores[0].write(data, fileId, data.length);
					}
				} catch (Exception _ex) {
					filestores[0] = null;
				}
				if (data != null) {
					crc32.reset();
					crc32.update(data);
					int i3 = (int) crc32.getValue();
					if (i3 != checksum) {
						//data = null;
						j1++;
						s2 = "Checksum error: " + i3;
					}
				}
			} catch (IOException ioexception) {
				if (s2.equals("Unknown error")) {
					s2 = "Connection error";
				}
				data = null;
			} catch (NullPointerException _ex) {
				s2 = "Null error";
				data = null;
				if (!Signlink.reporterror) {
					return null;
				}
			} catch (ArrayIndexOutOfBoundsException _ex) {
				s2 = "Bounds error";
				data = null;
				if (!Signlink.reporterror) {
					return null;
				}
			} catch (Exception _ex) {
				s2 = "Unexpected error";
				data = null;
				if (!Signlink.reporterror) {
					return null;
				}
			}
			if (data == null) {
				for (int l1 = wait; l1 > 0; l1--) {
					if (j1 >= 3) {
						showProgress(progress, "Game updated - please reload page");
						l1 = 10;
					} else {
						showProgress(progress, s2 + " - Retrying in " + l1);
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception ignored) {
					}
				}
				wait *= 2;
				if (wait > 60) {
					wait = 60;
				}
				jaggrabEnabled = !jaggrabEnabled;
			}
		}
		return new FileArchive(data);
	}

	public void tryReconnect() {
		if (idleTimeout > 0) {
			disconnect();
			return;
		}
		areaViewport.bind();
		fontPlain12.drawStringCenter("Connection lost", 257, 144, 0);
		fontPlain12.drawStringCenter("Connection lost", 256, 143, 0xffffff);
		fontPlain12.drawStringCenter("Please wait - attempting to reestablish", 257, 159, 0);
		fontPlain12.drawStringCenter("Please wait - attempting to reestablish", 256, 158, 0xffffff);
		areaViewport.draw(super.graphics, 4, 4);
		minimapState = 0;
		flagSceneTileX = 0;
		Connection connection = this.connection;
		ingame = false;
		anInt1038 = 0;
		login(username, password, true);
		if (!ingame) {
			disconnect();
		}
		try {
			connection.close();
		} catch (Exception ignored) {
		}
	}

	public void useMenuOption(int option) {
		if (option < 0) {
			return;
		}
		if (chatbackInputType != 0) {
			chatbackInputType = 0;
			redrawChatback = true;
		}

		int action = menuAction[option];
		int a = menuParamA[option];
		int b = menuParamB[option];
		int c = menuParamC[option];

		if (action >= 2000) {
			action -= 2000;
		}

		if (action == 582) {
			NPCEntity npc = npcs[c];
			if (npc != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(57);
				out.put2A(anInt1285);
				out.put2A(c);
				out.put2LE(anInt1283);
				out.put2A(anInt1284);
			}
		}
		if (action == 234) {
			boolean flag1 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag1) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(236);
			out.put2LE(b + sceneBaseTileZ);
			out.put2(c);
			out.put2LE(a + sceneBaseTileX);
		}
		if ((action == 62) && interactWithLoc(c, a, b)) {
			out.putOp(192);
			out.put2(anInt1284);
			out.put2LE((c >> 14) & 0x7fff);
			out.put2LEA(b + sceneBaseTileZ);
			out.put2LE(anInt1283);
			out.put2LEA(a + sceneBaseTileX);
			out.put2(anInt1285);
		}
		if (action == 511) {
			boolean flag2 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag2) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(25);
			out.put2LE(anInt1284);
			out.put2A(anInt1285);
			out.put2(c);
			out.put2A(b + sceneBaseTileZ);
			out.put2LEA(anInt1283);
			out.put2(a + sceneBaseTileX);
		}
		if (action == 74) {
			out.putOp(122);
			out.put2LEA(b);
			out.put2A(a);
			out.put2LE(c);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 315) {
			Component component = Component.instances[b];
			boolean notify = true;
			if (component.contentType > 0) {
				notify = handleComponentAction(component);
			}
			if (notify) {
				out.putOp(185);
				out.put2(b);
			}
		}
		if (action == 561) {
			PlayerEntity player = players[c];
			if (player != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(128);
				out.put2(c);
			}
		}
		if (action == 20) {
			NPCEntity class30_sub2_sub4_sub1_sub1_1 = npcs[c];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub1_1.pathTileX[0], class30_sub2_sub4_sub1_sub1_1.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(155);
				out.put2LE(c);
			}
		}
		if (action == 779) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_1 = players[c];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub2_1.pathTileX[0], class30_sub2_sub4_sub1_sub2_1.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(153);
				out.put2LE(c);
			}
		}
		if (action == 516) {
			if (!menuVisible) {
				scene.method312(super.mousePressY - 4, super.mousePressX - 4);
			} else {
				scene.method312(b - 4, a - 4);
			}
		}
		if (action == 1062) {
			interactWithLoc(c, a, b);
			out.putOp(228);
			out.put2A((c >> 14) & 0x7fff);
			out.put2A(b + sceneBaseTileZ);
			out.put2(a + sceneBaseTileX);
		}
		if ((action == 679) && !aBoolean1149) {
			out.putOp(40);
			out.put2(b);
			aBoolean1149 = true;
		}
		if (action == 431) {
			out.putOp(129);
			out.put2A(a);
			out.put2(b);
			out.put2A(c);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if ((action == 337) || (action == 42) || (action == 792) || (action == 322)) {
			String s = menuOption[option];
			int k1 = s.indexOf("@whi@");
			if (k1 != -1) {
				long l3 = StringUtil.toBase37(s.substring(k1 + 5).trim());
				if (action == 337) {
					addFriend(l3);
				}
				if (action == 42) {
					addIgnore(l3);
				}
				if (action == 792) {
					removeFriend(l3);
				}
				if (action == 322) {
					removeIgnore(l3);
				}
			}
		}
		if (action == 53) {
			out.putOp(135);
			out.put2LE(a);
			out.put2A(b);
			out.put2LE(c);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 539) {
			out.putOp(16);
			out.put2A(c);
			out.put2LEA(a);
			out.put2LEA(b);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if ((action == 484) || (action == 6)) {
			String s1 = menuOption[option];
			int l1 = s1.indexOf("@whi@");
			if (l1 != -1) {
				s1 = s1.substring(l1 + 5).trim();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(StringUtil.toBase37(s1)));
				boolean flag9 = false;
				for (int j3 = 0; j3 < playerCount; j3++) {
					PlayerEntity player_7 = players[playerIndices[j3]];
					if ((player_7 == null) || (player_7.name == null) || !player_7.name.equalsIgnoreCase(s7)) {
						continue;
					}
					tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player_7.pathTileX[0], player_7.pathTileZ[0], 0, 1, 1, 0, 0, false);
					if (action == 484) {
						out.putOp(139);
						out.put2LE(playerIndices[j3]);
					}
					if (action == 6) {
						out.putOp(128);
						out.put2(playerIndices[j3]);
					}
					flag9 = true;
					break;
				}
				if (!flag9) {
					method77(0, "", "Unable to find " + s7);
				}
			}
		}
		if (action == 870) {
			out.putOp(53);
			out.put2(a);
			out.put2A(anInt1283);
			out.put2LEA(c);
			out.put2(anInt1284);
			out.put2LE(anInt1285);
			out.put2(b);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 847) {
			out.putOp(87);
			out.put2A(c);
			out.put2(b);
			out.put2A(a);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}

		if (action == 626) {
			Component component_1 = Component.instances[b];
			anInt1136 = 1;
			activeSpellComponent = b;
			activeSpellFlags = component_1.spellFlags;
			anInt1282 = 0;
			redrawInvback = true;

			String prefix = component_1.spellAction;

			if (prefix.contains(" ")) {
				prefix = prefix.substring(0, prefix.indexOf(" "));
			}

			String suffix = component_1.spellAction;

			if (suffix.contains(" ")) {
				suffix = suffix.substring(suffix.indexOf(" ") + 1);
			}

			spellCaption = prefix + " " + component_1.spellName + " " + suffix;

			if (activeSpellFlags == 0x10) {
				redrawInvback = true;
				selectedTab = 3;
				redrawSideicons = true;
			}
			return;
		}
		if (action == 78) {
			out.putOp(117);
			out.put2LEA(b);
			out.put2LEA(c);
			out.put2LE(a);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 27) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_2 = players[c];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub2_2.pathTileX[0], class30_sub2_sub4_sub1_sub2_2.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(73);
				out.put2LE(c);
			}
		}
		if (action == 213) {
			boolean flag3 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag3) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(79);
			out.put2LE(b + sceneBaseTileZ);
			out.put2(c);
			out.put2A(a + sceneBaseTileX);
		}
		if (action == 632) {
			out.putOp(145);
			out.put2A(b);
			out.put2A(a);
			out.put2A(c);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 493) {
			out.putOp(75);
			out.put2LEA(b);
			out.put2LE(a);
			out.put2A(c);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 652) {
			boolean flag4 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag4) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(156);
			out.put2A(a + sceneBaseTileX);
			out.put2LE(b + sceneBaseTileZ);
			out.put2LEA(c);
		}
		if (action == 94) {
			boolean flag5 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag5) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(181);
			out.put2LE(b + sceneBaseTileZ);
			out.put2(c);
			out.put2LE(a + sceneBaseTileX);
			out.put2A(activeSpellComponent);
		}
		if (action == 646) {
			out.putOp(185);
			out.put2(b);
			Component component_2 = Component.instances[b];
			if ((component_2.scripts != null) && (component_2.scripts[0][0] == 5)) {
				int i2 = component_2.scripts[0][1];
				if (variables[i2] != component_2.scriptOperand[0]) {
					variables[i2] = component_2.scriptOperand[0];
					updateVarp(i2);
					redrawInvback = true;
				}
			}
		}
		if (action == 225) {
			NPCEntity class30_sub2_sub4_sub1_sub1_2 = npcs[c];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub1_2.pathTileX[0], class30_sub2_sub4_sub1_sub1_2.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(17);
				out.put2LEA(c);
			}
		}
		if (action == 965) {
			NPCEntity npc_3 = npcs[c];
			if (npc_3 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc_3.pathTileX[0], npc_3.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(21);
				out.put2(c);
			}
		}
		if (action == 413) {
			NPCEntity class30_sub2_sub4_sub1_sub1_4 = npcs[c];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub1_4.pathTileX[0], class30_sub2_sub4_sub1_sub1_4.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(131);
				out.put2LEA(c);
				out.put2A(activeSpellComponent);
			}
		}
		if (action == 200) {
			method147();
		}
		if (action == 1025) {
			NPCEntity npc_5 = npcs[c];
			if (npc_5 != null) {
				NPCType type = npc_5.type;
				if (type.overrides != null) {
					type = type.getOverrideType();
				}
				if (type != null) {
					String s9;
					if (type.desc != null) {
						s9 = new String(type.desc);
					} else {
						s9 = "It's a " + type.name + ".";
					}
					method77(0, "", s9);
				}
			}
		}
		if (action == 900) {
			interactWithLoc(c, a, b);
			out.putOp(252);
			out.put2LEA((c >> 14) & 0x7fff);
			out.put2LE(b + sceneBaseTileZ);
			out.put2A(a + sceneBaseTileX);
		}
		if (action == 412) {
			NPCEntity npc_6 = npcs[c];
			if (npc_6 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc_6.pathTileX[0], npc_6.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(72);
				out.put2A(c);
			}
		}
		if (action == 365) {
			PlayerEntity player_3 = players[c];
			if (player_3 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player_3.pathTileX[0], player_3.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(249);
				out.put2A(c);
				out.put2LE(activeSpellComponent);
			}
		}
		if (action == 729) {
			PlayerEntity class30_sub2_sub4_sub1_sub2_4 = players[c];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], class30_sub2_sub4_sub1_sub2_4.pathTileX[0], class30_sub2_sub4_sub1_sub2_4.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(39);
				out.put2LE(c);
			}
		}
		if (action == 577) {
			PlayerEntity player_5 = players[c];
			if (player_5 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player_5.pathTileX[0], player_5.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(139);
				out.put2LE(c);
			}
		}
		if ((action == 956) && interactWithLoc(c, a, b)) {
			out.putOp(35);
			out.put2LE(a + sceneBaseTileX);
			out.put2A(activeSpellComponent);
			out.put2A(b + sceneBaseTileZ);
			out.put2LE((c >> 14) & 0x7fff);
		}
		if (action == 567) {
			boolean flag6 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag6) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(23);
			out.put2LE(b + sceneBaseTileZ);
			out.put2LE(c);
			out.put2LE(a + sceneBaseTileX);
		}
		if (action == 867) {
			out.putOp(43);
			out.put2LE(b);
			out.put2A(c);
			out.put2A(a);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 543) {
			out.putOp(237);
			out.put2(a);
			out.put2A(c);
			out.put2(b);
			out.put2A(activeSpellComponent);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 606) {
			String s2 = menuOption[option];
			int j2 = s2.indexOf("@whi@");
			if (j2 != -1) {
				if (viewportComponentId == -1) {
					method147();
					aString881 = s2.substring(j2 + 5).trim();
					aBoolean1158 = false;
					for (int i3 = 0; i3 < Component.instances.length; i3++) {
						if ((Component.instances[i3] == null) || (Component.instances[i3].contentType != 600)) {
							continue;
						}
						anInt1178 = viewportComponentId = Component.instances[i3].parentId;
						break;
					}
				} else {
					method77(0, "", "Please close the interface you have open before using 'report abuse'");
				}
			}
		}
		if (action == 491) {
			PlayerEntity player_6 = players[c];
			if (player_6 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player_6.pathTileX[0], player_6.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(14);
				out.put2A(anInt1284);
				out.put2(c);
				out.put2(anInt1285);
				out.put2LE(anInt1283);
			}
		}
		if (action == 639) {
			String s3 = menuOption[option];
			int k2 = s3.indexOf("@whi@");
			if (k2 != -1) {
				long l4 = StringUtil.toBase37(s3.substring(k2 + 5).trim());
				int k3 = -1;
				for (int i4 = 0; i4 < friendCount; i4++) {
					if (friendName37[i4] != l4) {
						continue;
					}
					k3 = i4;
					break;
				}
				if ((k3 != -1) && (friendWorld[k3] > 0)) {
					redrawChatback = true;
					chatbackInputType = 0;
					showSocialInput = true;
					socialInput = "";
					socialAction = 3;
					aLong953 = friendName37[k3];
					socialMessage = "Enter message to send to " + friendName[k3];
				}
			}
		}
		if (action == 454) {
			out.putOp(41);
			out.put2(c);
			out.put2A(a);
			out.put2A(b);
			anInt1243 = 0;
			anInt1244 = b;
			anInt1245 = a;
			anInt1246 = 2;
			if (Component.instances[b].parentId == viewportComponentId) {
				anInt1246 = 1;
			}
			if (Component.instances[b].parentId == chatbackComponentId) {
				anInt1246 = 3;
			}
		}
		if (action == 478) {
			NPCEntity npc_7 = npcs[c];
			if (npc_7 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc_7.pathTileX[0], npc_7.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.putOp(18);
				out.put2LE(c);
			}
		}
		if (action == 113) {
			interactWithLoc(c, a, b);
			out.putOp(70);
			out.put2LE(a + sceneBaseTileX);
			out.put2(b + sceneBaseTileZ);
			out.put2LEA((c >> 14) & 0x7fff);
		}
		if (action == 872) {
			interactWithLoc(c, a, b);
			out.putOp(234);
			out.put2LEA(a + sceneBaseTileX);
			out.put2A((c >> 14) & 0x7fff);
			out.put2LEA(b + sceneBaseTileZ);
		}
		if (action == 502) {
			interactWithLoc(c, a, b);
			out.putOp(132);
			out.put2LEA(a + sceneBaseTileX);
			out.put2((c >> 14) & 0x7fff);
			out.put2A(b + sceneBaseTileZ);
		}
		if (action == 1125) {
			ObjType type = ObjType.get(c);
			Component component_4 = Component.instances[b];
			String s5;
			if ((component_4 != null) && (component_4.invSlotAmount[a] >= 0x186a0)) {
				s5 = component_4.invSlotAmount[a] + " x " + type.name;
			} else if (type.examine != null) {
				s5 = new String(type.examine);
			} else {
				s5 = "It's a " + type.name + ".";
			}
			method77(0, "", s5);
		}
		if (action == 169) {
			out.putOp(185);
			out.put2(b);
			Component component_3 = Component.instances[b];
			if ((component_3.scripts != null) && (component_3.scripts[0][0] == 5)) {
				int l2 = component_3.scripts[0][1];
				variables[l2] = 1 - variables[l2];
				updateVarp(l2);
				redrawInvback = true;
			}
		}
		if (action == 447) {
			anInt1282 = 1;
			anInt1283 = a;
			anInt1284 = b;
			anInt1285 = c;
			aString1286 = ObjType.get(c).name;
			anInt1136 = 0;
			redrawInvback = true;
			return;
		}
		if (action == 1226) {
			int j1 = (c >> 14) & 0x7fff;
			LocType type = LocType.get(j1);
			String s10;
			if (type.description != null) {
				s10 = type.description;
			} else {
				s10 = "It's a " + type.name + ".";
			}
			method77(0, "", s10);
		}
		if (action == 244) {
			boolean flag7 = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 0, 0, 0, 0, false);
			if (!flag7) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], a, b, 0, 1, 1, 0, 0, false);
			}
			crossX = super.mousePressX;
			crossY = super.mousePressY;
			crossMode = 2;
			crossCycle = 0;
			out.putOp(253);
			out.put2LE(a + sceneBaseTileX);
			out.put2LEA(b + sceneBaseTileZ);
			out.put2A(c);
		}
		if (action == 1448) {
			ObjType type_1 = ObjType.get(c);
			String s6;
			if (type_1.examine != null) {
				s6 = new String(type_1.examine);
			} else {
				s6 = "It's a " + type_1.name + ".";
			}
			method77(0, "", s6);
		}
		anInt1282 = 0;
		anInt1136 = 0;
		redrawInvback = true;
	}

	public void method70() {
		anInt1251 = 0;
		int j = (localPlayer.x >> 7) + sceneBaseTileX;
		int k = (localPlayer.z >> 7) + sceneBaseTileZ;
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

	public void handleViewportInput() {
		if ((anInt1282 == 0) && (anInt1136 == 0)) {
			menuOption[menuSize] = "Walk here";
			menuAction[menuSize] = 516;
			menuParamA[menuSize] = super.mouseX;
			menuParamB[menuSize] = super.mouseY;
			menuSize++;
		}
		int j = -1;
		for (int i = 0; i < Model.pickedCount; i++) {
			int bitset = Model.pickedBitsets[i];
			int i1 = bitset & 0x7f;
			int j1 = (bitset >> 7) & 0x7f;
			int k1 = (bitset >> 29) & 3;
			int locId = (bitset >> 14) & 0x7fff;
			if (bitset == j) {
				continue;
			}
			j = bitset;
			if ((k1 == 2) && (scene.getInfo(currentPlane, i1, j1, bitset) >= 0)) {
				LocType loc = LocType.get(locId);

				if (loc.overrideTypeIDs != null) {
					loc = loc.getOverrideType();
				}

				if (loc == null) {
					continue;
				}
				if (anInt1282 == 1) {
					menuOption[menuSize] = "Use " + aString1286 + " with @cya@" + loc.name;
					menuAction[menuSize] = 62;
					menuParamC[menuSize] = bitset;
					menuParamA[menuSize] = i1;
					menuParamB[menuSize] = j1;
					menuSize++;
				} else if (anInt1136 == 1) {
					if ((activeSpellFlags & 4) == 4) {
						menuOption[menuSize] = spellCaption + " @cya@" + loc.name;
						menuAction[menuSize] = 956;
						menuParamC[menuSize] = bitset;
						menuParamA[menuSize] = i1;
						menuParamB[menuSize] = j1;
						menuSize++;
					}
				} else {
					if (loc.actions != null) {
						for (int i2 = 4; i2 >= 0; i2--) {
							if (loc.actions[i2] != null) {
								menuOption[menuSize] = loc.actions[i2] + " @cya@" + loc.name;
								if (i2 == 0) {
									menuAction[menuSize] = 502;
								}
								if (i2 == 1) {
									menuAction[menuSize] = 900;
								}
								if (i2 == 2) {
									menuAction[menuSize] = 113;
								}
								if (i2 == 3) {
									menuAction[menuSize] = 872;
								}
								if (i2 == 4) {
									menuAction[menuSize] = 1062;
								}
								menuParamC[menuSize] = bitset;
								menuParamA[menuSize] = i1;
								menuParamB[menuSize] = j1;
								menuSize++;
							}
						}
					}
					menuOption[menuSize] = "Examine @cya@" + loc.name;
					menuAction[menuSize] = 1226;
					menuParamC[menuSize] = loc.index << 14;
					menuParamA[menuSize] = i1;
					menuParamB[menuSize] = j1;
					menuSize++;
				}
			}
			if (k1 == 1) {
				NPCEntity npc = npcs[locId];
				if ((npc.type.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
					for (int j2 = 0; j2 < npcCount; j2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_1 = npcs[npcIndices[j2]];
						if ((class30_sub2_sub4_sub1_sub1_1 != null) && (class30_sub2_sub4_sub1_sub1_1 != npc) && (class30_sub2_sub4_sub1_sub1_1.type.size == 1) && (class30_sub2_sub4_sub1_sub1_1.x == npc.x) && (class30_sub2_sub4_sub1_sub1_1.z == npc.z)) {
							method87(class30_sub2_sub4_sub1_sub1_1.type, npcIndices[j2], j1, i1);
						}
					}
					for (int l2 = 0; l2 < playerCount; l2++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_1 = players[playerIndices[l2]];
						if ((class30_sub2_sub4_sub1_sub2_1 != null) && (class30_sub2_sub4_sub1_sub2_1.x == npc.x) && (class30_sub2_sub4_sub1_sub2_1.z == npc.z)) {
							method88(i1, playerIndices[l2], class30_sub2_sub4_sub1_sub2_1, j1);
						}
					}
				}
				method87(npc.type, locId, j1, i1);
			}
			if (k1 == 0) {
				PlayerEntity player = players[locId];
				if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
					for (int k2 = 0; k2 < npcCount; k2++) {
						NPCEntity class30_sub2_sub4_sub1_sub1_2 = npcs[npcIndices[k2]];
						if ((class30_sub2_sub4_sub1_sub1_2 != null) && (class30_sub2_sub4_sub1_sub1_2.type.size == 1) && (class30_sub2_sub4_sub1_sub1_2.x == player.x) && (class30_sub2_sub4_sub1_sub1_2.z == player.z)) {
							method87(class30_sub2_sub4_sub1_sub1_2.type, npcIndices[k2], j1, i1);
						}
					}
					for (int i3 = 0; i3 < playerCount; i3++) {
						PlayerEntity class30_sub2_sub4_sub1_sub2_2 = players[playerIndices[i3]];
						if ((class30_sub2_sub4_sub1_sub2_2 != null) && (class30_sub2_sub4_sub1_sub2_2 != player) && (class30_sub2_sub4_sub1_sub2_2.x == player.x) && (class30_sub2_sub4_sub1_sub2_2.z == player.z)) {
							method88(i1, playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, j1);
						}
					}
				}
				method88(i1, locId, player, j1);
			}
			if (k1 == 3) {
				DoublyLinkedList list = planeObjStacks[currentPlane][i1][j1];
				if (list != null) {
					for (ObjStackEntity objStack = (ObjStackEntity) list.peekBack(); objStack != null; objStack = (ObjStackEntity) list.next()) {
						ObjType type = ObjType.get(objStack.id);
						if (anInt1282 == 1) {
							menuOption[menuSize] = "Use " + aString1286 + " with @lre@" + type.name;
							menuAction[menuSize] = 511;
							menuParamC[menuSize] = objStack.id;
							menuParamA[menuSize] = i1;
							menuParamB[menuSize] = j1;
							menuSize++;
						} else if (anInt1136 == 1) {
							if ((activeSpellFlags & 1) == 1) {
								menuOption[menuSize] = spellCaption + " @lre@" + type.name;
								menuAction[menuSize] = 94;
								menuParamC[menuSize] = objStack.id;
								menuParamA[menuSize] = i1;
								menuParamB[menuSize] = j1;
								menuSize++;
							}
						} else {
							for (int j3 = 4; j3 >= 0; j3--) {
								if ((type.groundOptions != null) && (type.groundOptions[j3] != null)) {
									menuOption[menuSize] = type.groundOptions[j3] + " @lre@" + type.name;
									if (j3 == 0) {
										menuAction[menuSize] = 652;
									}
									if (j3 == 1) {
										menuAction[menuSize] = 567;
									}
									if (j3 == 2) {
										menuAction[menuSize] = 234;
									}
									if (j3 == 3) {
										menuAction[menuSize] = 244;
									}
									if (j3 == 4) {
										menuAction[menuSize] = 213;
									}
									menuParamC[menuSize] = objStack.id;
									menuParamA[menuSize] = i1;
									menuParamB[menuSize] = j1;
									menuSize++;
								} else if (j3 == 2) {
									menuOption[menuSize] = "Take @lre@" + type.name;
									menuAction[menuSize] = 234;
									menuParamC[menuSize] = objStack.id;
									menuParamA[menuSize] = i1;
									menuParamB[menuSize] = j1;
									menuSize++;
								}
							}
							menuOption[menuSize] = "Examine @lre@" + type.name;
							menuAction[menuSize] = 1448;
							menuParamC[menuSize] = objStack.id;
							menuParamA[menuSize] = i1;
							menuParamB[menuSize] = j1;
							menuSize++;
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
			connection.debug();
		}
		super.debug = true;
	}

	public void updateKeyboardInput() {
		do {
			int key = pollKey();

			if (key == -1) {
				break;
			}

			if ((viewportComponentId != -1) && (viewportComponentId == anInt1178)) {
				if ((key == 8) && (aString881.length() > 0)) {
					aString881 = aString881.substring(0, aString881.length() - 1);
				}
				if ((((key >= 97) && (key <= 122)) || ((key >= 65) && (key <= 90)) || ((key >= 48) && (key <= 57)) || (key == 32)) && (aString881.length() < 12)) {
					aString881 += (char) key;
				}
			} else if (showSocialInput) {
				if ((key >= 32) && (key <= 122) && (socialInput.length() < 80)) {
					socialInput += (char) key;
					redrawChatback = true;
				}
				if ((key == 8) && (socialInput.length() > 0)) {
					socialInput = socialInput.substring(0, socialInput.length() - 1);
					redrawChatback = true;
				}
				if ((key == 13) || (key == 10)) {
					showSocialInput = false;
					redrawChatback = true;
					if (socialAction == 1) {
						long l = StringUtil.toBase37(socialInput);
						addFriend(l);
					}
					if ((socialAction == 2) && (friendCount > 0)) {
						long l1 = StringUtil.toBase37(socialInput);
						removeFriend(l1);
					}
					if ((socialAction == 3) && (socialInput.length() > 0)) {
						out.putOp(126);
						out.put1(0);
						int k = out.position;
						out.put8(aLong953);
						ChatCompression.pack(socialInput, out);
						out.putSize1(out.position - k);
						socialInput = ChatCompression.method527(socialInput);
						socialInput = Censor.method497(socialInput, 0);
						method77(6, StringUtil.formatName(StringUtil.fromBase37(aLong953)), socialInput);
						if (privateChatSetting == 2) {
							privateChatSetting = 1;
							redrawPrivacySettings = true;
							out.putOp(95);
							out.put1(publicChatSetting);
							out.put1(privateChatSetting);
							out.put1(tradeChatSetting);
						}
					}
					if ((socialAction == 4) && (ignoreCount < 100)) {
						long l2 = StringUtil.toBase37(socialInput);
						addIgnore(l2);
					}
					if ((socialAction == 5) && (ignoreCount > 0)) {
						long l3 = StringUtil.toBase37(socialInput);
						removeIgnore(l3);
					}
				}
			} else if (chatbackInputType == 1) {
				if ((key >= 48) && (key <= 57) && (chatbackInput.length() < 10)) {
					chatbackInput += (char) key;
					redrawChatback = true;
				}
				if ((key == 8) && (chatbackInput.length() > 0)) {
					chatbackInput = chatbackInput.substring(0, chatbackInput.length() - 1);
					redrawChatback = true;
				}
				if ((key == 13) || (key == 10)) {
					if (chatbackInput.length() > 0) {
						int i1 = 0;
						try {
							i1 = Integer.parseInt(chatbackInput);
						} catch (Exception ignored) {
						}
						out.putOp(208);
						out.put4(i1);
					}
					chatbackInputType = 0;
					redrawChatback = true;
				}
			} else if (chatbackInputType == 2) {
				if ((key >= 32) && (key <= 122) && (chatbackInput.length() < 12)) {
					chatbackInput += (char) key;
					redrawChatback = true;
				}
				if ((key == 8) && (chatbackInput.length() > 0)) {
					chatbackInput = chatbackInput.substring(0, chatbackInput.length() - 1);
					redrawChatback = true;
				}
				if ((key == 13) || (key == 10)) {
					if (chatbackInput.length() > 0) {
						out.putOp(60);
						out.put8(StringUtil.toBase37(chatbackInput));
					}
					chatbackInputType = 0;
					redrawChatback = true;
				}
			} else if (chatbackComponentId == -1) {
				if ((key >= 32) && (key <= 122) && (chatTyped.length() < 80)) {
					chatTyped += (char) key;
					redrawChatback = true;
				}
				if ((key == 8) && (chatTyped.length() > 0)) {
					chatTyped = chatTyped.substring(0, chatTyped.length() - 1);
					redrawChatback = true;
				}
				if (((key == 13) || (key == 10)) && (chatTyped.length() > 0)) {
					if (rights == 2) {
						if (chatTyped.equals("::clientdrop")) {
							tryReconnect();
						}
						if (chatTyped.equals("::lag")) {
							debug();
						}
						if (chatTyped.equals("::prefetchmusic")) {
							for (int j1 = 0; j1 < ondemand.getFileCount(2); j1++) {
								ondemand.prefetch((byte) 1, 2, j1);
							}
						}
						if (chatTyped.equals("::fpson")) {
							aBoolean1156 = true;
						}
						if (chatTyped.equals("::fpsoff")) {
							aBoolean1156 = false;
						}
						if (chatTyped.equals("::noclip")) {
							for (int k1 = 0; k1 < 4; k1++) {
								for (int i2 = 1; i2 < 103; i2++) {
									for (int k2 = 1; k2 < 103; k2++) {
										collisions[k1].flags[i2][k2] = 0;
									}
								}
							}
						}
					}
					if (chatTyped.startsWith("::")) {
						out.putOp(103);
						out.put1(chatTyped.length() - 1);
						out.put(chatTyped.substring(2));
					} else {
						String s = chatTyped.toLowerCase();
						int j2 = 0;
						if (s.startsWith("yellow:")) {
							j2 = 0;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("red:")) {
							j2 = 1;
							chatTyped = chatTyped.substring(4);
						} else if (s.startsWith("green:")) {
							j2 = 2;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("cyan:")) {
							j2 = 3;
							chatTyped = chatTyped.substring(5);
						} else if (s.startsWith("purple:")) {
							j2 = 4;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("white:")) {
							j2 = 5;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("flash1:")) {
							j2 = 6;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("flash2:")) {
							j2 = 7;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("flash3:")) {
							j2 = 8;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("glow1:")) {
							j2 = 9;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("glow2:")) {
							j2 = 10;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("glow3:")) {
							j2 = 11;
							chatTyped = chatTyped.substring(6);
						}
						s = chatTyped.toLowerCase();
						int i3 = 0;
						if (s.startsWith("wave:")) {
							i3 = 1;
							chatTyped = chatTyped.substring(5);
						} else if (s.startsWith("wave2:")) {
							i3 = 2;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("shake:")) {
							i3 = 3;
							chatTyped = chatTyped.substring(6);
						} else if (s.startsWith("scroll:")) {
							i3 = 4;
							chatTyped = chatTyped.substring(7);
						} else if (s.startsWith("slide:")) {
							i3 = 5;
							chatTyped = chatTyped.substring(6);
						}
						out.putOp(4);
						out.put1(0);
						int j3 = out.position;
						out.put1S(i3);
						out.put1S(j2);
						aBuffer_834.position = 0;
						ChatCompression.pack(chatTyped, aBuffer_834);
						out.putA(aBuffer_834.data, 0, aBuffer_834.position);
						out.putSize1(out.position - j3);
						chatTyped = ChatCompression.method527(chatTyped);
						chatTyped = Censor.method497(chatTyped, 0);
						localPlayer.chat = chatTyped;
						localPlayer.chatColor = j2;
						localPlayer.chatStyle = i3;
						localPlayer.chatTimer = 150;
						if (rights == 2) {
							method77(2, "@cr2@" + localPlayer.name, localPlayer.chat);
						} else if (rights == 1) {
							method77(2, "@cr1@" + localPlayer.name, localPlayer.chat);
						} else {
							method77(2, localPlayer.name, localPlayer.chat);
						}
						if (publicChatSetting == 2) {
							publicChatSetting = 3;
							redrawPrivacySettings = true;
							out.putOp(95);
							out.put1(publicChatSetting);
							out.put1(privateChatSetting);
							out.put1(tradeChatSetting);
						}
					}
					chatTyped = "";
					redrawChatback = true;
				}
			}
		} while (true);
	}

	public void handleChatInput(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 100; i1++) {
			if (messageText[i1] == null) {
				continue;
			}
			int j1 = messageType[i1];
			int k1 = (70 - (l * 14)) + anInt1089 + 4;
			if (k1 < -20) {
				break;
			}
			String s = messageSender[i1];
			if ((s != null) && s.startsWith("@cr1@")) {
				s = s.substring(5);
			}
			if ((s != null) && s.startsWith("@cr2@")) {
				s = s.substring(5);
			}
			if (j1 == 0) {
				l++;
			}
			if (((j1 == 1) || (j1 == 2)) && ((j1 == 1) || (publicChatSetting == 0) || ((publicChatSetting == 1) && isFriend(s)))) {
				if ((j > (k1 - 14)) && (j <= k1) && !s.equals(localPlayer.name)) {
					if (rights >= 1) {
						menuOption[menuSize] = "Report abuse @whi@" + s;
						menuAction[menuSize] = 606;
						menuSize++;
					}
					menuOption[menuSize] = "Add ignore @whi@" + s;
					menuAction[menuSize] = 42;
					menuSize++;
					menuOption[menuSize] = "Add friend @whi@" + s;
					menuAction[menuSize] = 337;
					menuSize++;
				}
				l++;
			}
			if (((j1 == 3) || (j1 == 7)) && (splitPrivateChat == 0) && ((j1 == 7) || (privateChatSetting == 0) || ((privateChatSetting == 1) && isFriend(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					if (rights >= 1) {
						menuOption[menuSize] = "Report abuse @whi@" + s;
						menuAction[menuSize] = 606;
						menuSize++;
					}
					menuOption[menuSize] = "Add ignore @whi@" + s;
					menuAction[menuSize] = 42;
					menuSize++;
					menuOption[menuSize] = "Add friend @whi@" + s;
					menuAction[menuSize] = 337;
					menuSize++;
				}
				l++;
			}
			if ((j1 == 4) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					menuOption[menuSize] = "Accept trade @whi@" + s;
					menuAction[menuSize] = 484;
					menuSize++;
				}
				l++;
			}
			if (((j1 == 5) || (j1 == 6)) && (splitPrivateChat == 0) && (privateChatSetting < 2)) {
				l++;
			}
			if ((j1 == 8) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(s)))) {
				if ((j > (k1 - 14)) && (j <= k1)) {
					menuOption[menuSize] = "Accept challenge @whi@" + s;
					menuAction[menuSize] = 6;
					menuSize++;
				}
				l++;
			}
		}
	}

	public void updateComponentContent(Component component) {
		int type = component.contentType;

		if (((type >= 1) && (type <= 100)) || ((type >= 701) && (type <= 800))) {
			if ((type == 1) && (socialState == 0)) {
				component.text = "Loading friend list";
				component.optionType = 0;
				return;
			}
			if ((type == 1) && (socialState == 1)) {
				component.text = "Connecting to friendserver";
				component.optionType = 0;
				return;
			}
			if ((type == 2) && (socialState != 2)) {
				component.text = "Please wait...";
				component.optionType = 0;
				return;
			}
			int k = friendCount;
			if (socialState != 2) {
				k = 0;
			}
			if (type > 700) {
				type -= 601;
			} else {
				type--;
			}
			if (type >= k) {
				component.text = "";
				component.optionType = 0;
			} else {
				component.text = friendName[type];
				component.optionType = 1;
			}
			return;
		}

		if (((type >= 101) && (type <= 200)) || ((type >= 801) && (type <= 900))) {
			int l = friendCount;
			if (socialState != 2) {
				l = 0;
			}
			if (type > 800) {
				type -= 701;
			} else {
				type -= 101;
			}
			if (type >= l) {
				component.text = "";
				component.optionType = 0;
				return;
			}
			if (friendWorld[type] == 0) {
				component.text = "@red@Offline";
			} else if (friendWorld[type] == nodeId) {
				component.text = "@gre@World-" + (friendWorld[type] - 9);
			} else {
				component.text = "@yel@World-" + (friendWorld[type] - 9);
			}
			component.optionType = 1;
			return;
		}

		if (type == 203) {
			int i1 = friendCount;
			if (socialState != 2) {
				i1 = 0;
			}
			component.scrollHeight = (i1 * 15) + 20;
			if (component.scrollHeight <= component.height) {
				component.scrollHeight = component.height + 1;
			}
			return;
		}

		if ((type >= 401) && (type <= 500)) {
			if ((((type -= 401)) == 0) && (socialState == 0)) {
				component.text = "Loading ignore list";
				component.optionType = 0;
				return;
			}
			if ((type == 1) && (socialState == 0)) {
				component.text = "Please wait...";
				component.optionType = 0;
				return;
			}
			int j1 = ignoreCount;
			if (socialState == 0) {
				j1 = 0;
			}
			if (type >= j1) {
				component.text = "";
				component.optionType = 0;
			} else {
				component.text = StringUtil.formatName(StringUtil.fromBase37(ignoreName37[type]));
				component.optionType = 1;
			}
			return;
		}

		if (type == 503) {
			component.scrollHeight = (ignoreCount * 15) + 20;
			if (component.scrollHeight <= component.height) {
				component.scrollHeight = component.height + 1;
			}
			return;
		}

		if (type == 327) {
			component.modelEyePitch = 150;
			component.modelYaw = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;

			if (updateDesignModel) {
				for (int i = 0; i < 7; i++) {
					int kit = designIdentikits[i];
					if ((kit >= 0) && !IDKType.instances[kit].validateModel()) {
						return;
					}
				}

				updateDesignModel = false;

				Model[] models = new Model[7];
				int modelCount = 0;
				for (int part = 0; part < 7; part++) {
					int kit = designIdentikits[part];

					if (kit >= 0) {
						models[modelCount++] = IDKType.instances[kit].getModel();
					}
				}

				Model model = new Model(modelCount, models);
				for (int part = 0; part < 5; part++) {
					if (designColors[part] != 0) {
						model.recolor(designPartColor[part][0], designPartColor[part][designColors[part]]);
						if (part == 1) {
							model.recolor(designHairColor[0], designHairColor[designColors[part]]);
						}
					}
				}

				model.createLabelReferences();
				model.applySequenceFrame(SeqType.instances[localPlayer.seqStand].primaryFrames[0]);
				model.calculateNormals(64, 850, -30, -50, -30, true);

				component.modelCategory = 5;
				component.modelId = 0;
				Component.cacheModel(0, 5, model);
			}
			return;
		}
		if (type == 324) {
			if (aImage_931 == null) {
				aImage_931 = component.image;
				aImage_932 = component.activeImage;
			}
			if (designGender) {
				component.image = aImage_932;
			} else {
				component.image = aImage_931;
			}
			return;
		}
		if (type == 325) {
			if (aImage_931 == null) {
				aImage_931 = component.image;
				aImage_932 = component.activeImage;
			}
			if (designGender) {
				component.image = aImage_931;
			} else {
				component.image = aImage_932;
			}
			return;
		}
		if (type == 600) {
			component.text = aString881;
			if ((loopCycle % 20) < 10) {
				component.text += "|";
			} else {
				component.text += " ";
			}
			return;
		}
		if (type == 613) {
			if (rights >= 1) {
				if (aBoolean1158) {
					component.color = 0xff0000;
					component.text = "Moderator option: Mute player for 48 hours: <ON>";
				} else {
					component.color = 0xffffff;
					component.text = "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else {
				component.text = "";
			}
		}
		if ((type == 650) || (type == 655)) {
			if (anInt1193 != 0) {
				String s;
				if (anInt1006 == 0) {
					s = "earlier today";
				} else if (anInt1006 == 1) {
					s = "yesterday";
				} else {
					s = anInt1006 + " days ago";
				}
				component.text = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				component.text = "";
			}
		}
		if (type == 651) {
			if (anInt1154 == 0) {
				component.text = "0 unread messages";
				component.color = 0xffff00;
			}
			if (anInt1154 == 1) {
				component.text = "1 unread message";
				component.color = 65280;
			}
			if (anInt1154 > 1) {
				component.text = anInt1154 + " unread messages";
				component.color = 65280;
			}
		}
		if (type == 652) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.text = "@yel@This is a non-members world: @whi@Since you are a member we";
				} else {
					component.text = "";
				}
			} else if (anInt1167 == 200) {
				component.text = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (anInt1167 == 0) {
					s1 = "Earlier today";
				} else if (anInt1167 == 1) {
					s1 = "Yesterday";
				} else {
					s1 = anInt1167 + " days ago";
				}
				component.text = s1 + " you changed your recovery questions";
			}
		}
		if (type == 653) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.text = "@whi@recommend you use a members world instead. You may use";
				} else {
					component.text = "";
				}
			} else if (anInt1167 == 200) {
				component.text = "We strongly recommend you do so now to secure your account.";
			} else {
				component.text = "If you do not remember making this change then cancel it immediately";
			}
		}
		if (type == 654) {
			if (anInt1167 == 201) {
				if (anInt1120 == 1) {
					component.text = "@whi@this world but member benefits are unavailable whilst here.";
				} else {
					component.text = "";
				}
				return;
			}
			if (anInt1167 == 200) {
				component.text = "Do this from the 'account management' area on our front webpage";
				return;
			}
			component.text = "Do this from the 'account management' area on our front webpage";
		}
	}

	public void method76() {
		if (splitPrivateChat == 0) {
			return;
		}
		BitmapFont font = fontPlain12;
		int i = 0;
		if (systemUpdateTimer != 0) {
			i = 1;
		}
		for (int j = 0; j < 100; j++) {
			if (messageText[j] != null) {
				int k = messageType[j];
				String s = messageSender[j];
				byte byte1 = 0;
				if ((s != null) && s.startsWith("@cr1@")) {
					s = s.substring(5);
					byte1 = 1;
				}
				if ((s != null) && s.startsWith("@cr2@")) {
					s = s.substring(5);
					byte1 = 2;
				}
				if (((k == 3) || (k == 7)) && ((k == 7) || (privateChatSetting == 0) || ((privateChatSetting == 1) && isFriend(s)))) {
					int l = 329 - (i * 13);
					int k1 = 4;
					font.drawString("From", k1, l, 0);
					font.drawString("From", k1, l - 1, 65535);
					k1 += font.stringWidthTaggable("From ");
					if (byte1 == 1) {
						imageModIcons[0].blit(k1, l - 12);
						k1 += 14;
					}
					if (byte1 == 2) {
						imageModIcons[1].blit(k1, l - 12);
						k1 += 14;
					}
					font.drawString(s + ": " + messageText[j], k1, l, 0);
					font.drawString(s + ": " + messageText[j], k1, l - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 5) && (privateChatSetting < 2)) {
					int i1 = 329 - (i * 13);
					font.drawString(messageText[j], 4, i1, 0);
					font.drawString(messageText[j], 4, i1 - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
				if ((k == 6) && (privateChatSetting < 2)) {
					int j1 = 329 - (i * 13);
					font.drawString("To " + s + ": " + messageText[j], 4, j1, 0);
					font.drawString("To " + s + ": " + messageText[j], 4, j1 - 1, 65535);
					if (++i >= 5) {
						return;
					}
				}
			}
		}
	}

	public void method77(int type, String prefix, String message) {
		if ((type == 0) && (stickyChatbackComponentId != -1)) {
			chatbackMessage = message;
			super.mousePressButton = 0;
		}
		if (chatbackComponentId == -1) {
			redrawChatback = true;
		}
		for (int j = 99; j > 0; j--) {
			messageType[j] = messageType[j - 1];
			messageSender[j] = messageSender[j - 1];
			messageText[j] = messageText[j - 1];
		}
		messageType[0] = type;
		messageSender[0] = prefix;
		messageText[0] = message;
	}

	public void method78() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 539) && (super.mousePressX <= 573) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (tabComponentId[0] != -1)) {
				redrawInvback = true;
				selectedTab = 0;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 569) && (super.mousePressX <= 599) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentId[1] != -1)) {
				redrawInvback = true;
				selectedTab = 1;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 597) && (super.mousePressX <= 627) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentId[2] != -1)) {
				redrawInvback = true;
				selectedTab = 2;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 625) && (super.mousePressX <= 669) && (super.mousePressY >= 168) && (super.mousePressY < 203) && (tabComponentId[3] != -1)) {
				redrawInvback = true;
				selectedTab = 3;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 666) && (super.mousePressX <= 696) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentId[4] != -1)) {
				redrawInvback = true;
				selectedTab = 4;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 694) && (super.mousePressX <= 724) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentId[5] != -1)) {
				redrawInvback = true;
				selectedTab = 5;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 722) && (super.mousePressX <= 756) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (tabComponentId[6] != -1)) {
				redrawInvback = true;
				selectedTab = 6;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 540) && (super.mousePressX <= 574) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (tabComponentId[7] != -1)) {
				redrawInvback = true;
				selectedTab = 7;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 572) && (super.mousePressX <= 602) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentId[8] != -1)) {
				redrawInvback = true;
				selectedTab = 8;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 599) && (super.mousePressX <= 629) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentId[9] != -1)) {
				redrawInvback = true;
				selectedTab = 9;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 627) && (super.mousePressX <= 671) && (super.mousePressY >= 467) && (super.mousePressY < 502) && (tabComponentId[10] != -1)) {
				redrawInvback = true;
				selectedTab = 10;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 669) && (super.mousePressX <= 699) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentId[11] != -1)) {
				redrawInvback = true;
				selectedTab = 11;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 696) && (super.mousePressX <= 726) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentId[12] != -1)) {
				redrawInvback = true;
				selectedTab = 12;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 724) && (super.mousePressX <= 758) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (tabComponentId[13] != -1)) {
				redrawInvback = true;
				selectedTab = 13;
				redrawSideicons = true;
			}
		}
	}

	public void prepareGameScreen() {
		if (areaChatback != null) {
			return;
		}
		disposeTitleComponents();
		imageTitle2 = null;
		imageTitle3 = null;
		imageTitle4 = null;
		imageTitle0 = null;
		imageTitle1 = null;
		imageTitle5 = null;
		imageTitle6 = null;
		imageTitle7 = null;
		imageTitle8 = null;
		areaChatback = new DrawArea(479, 96);
		areaMapback = new DrawArea(172, 156);
		Draw2D.clear();
		imageMapback.blit(0, 0);
		areaInvback = new DrawArea(190, 261);
		areaViewport = new DrawArea(512, 334);
		Draw2D.clear();
		areaBackbase1 = new DrawArea(496, 50);
		areaBackbase2 = new DrawArea(269, 37);
		areaBackhmid1 = new DrawArea(249, 45);
		redrawTitleBackground = true;
	}

	public void drawMinimapHint(Image24 image, int x, int y) {
		int l = (x * x) + (y * y);
		if ((l > 4225) && (l < 90000)) {
			int i1 = (cameraYaw + minimapAnticheatAngle) & 0x7ff;
			int j1 = Model.sin[i1];
			int k1 = Model.cos[i1];
			j1 = (j1 * 256) / (minimapZoom + 256);
			k1 = (k1 * 256) / (minimapZoom + 256);
			int l1 = ((y * j1) + (x * k1)) >> 16;
			int i2 = ((y * k1) - (x * j1)) >> 16;
			double d = Math.atan2(l1, i2);
			int j2 = (int) (Math.sin(d) * 63D);
			int k2 = (int) (Math.cos(d) * 57D);
			imageMapedge.drawRotated((94 + j2 + 4) - 10, 83 - k2 - 20, 20, 20, 15, 15, d, 256);
		} else {
			drawOnMinimap(image, x, y);
		}
	}

	public void method82() {
		if (objDragArea != 0) {
			return;
		}

		menuOption[0] = "Cancel";
		menuAction[0] = 1107;
		menuSize = 1;

		handlePrivateChatInput();

		lastHoveredComponentId = 0;

		if ((super.mouseX > 4) && (super.mouseY > 4) && (super.mouseX < 516) && (super.mouseY < 338)) {
			if (viewportComponentId != -1) {
				handleParentComponentInput(4, Component.instances[viewportComponentId], super.mouseX, 4, super.mouseY, 0);
			} else {
				handleViewportInput();
			}
		}

		if (lastHoveredComponentId != viewportHoveredComponentId) {
			viewportHoveredComponentId = lastHoveredComponentId;
		}

		lastHoveredComponentId = 0;
		if ((super.mouseX > 553) && (super.mouseY > 205) && (super.mouseX < 743) && (super.mouseY < 466)) {
			if (invbackComponentId != -1) {
				handleParentComponentInput(553, Component.instances[invbackComponentId], super.mouseX, 205, super.mouseY, 0);
			} else if (tabComponentId[selectedTab] != -1) {
				handleParentComponentInput(553, Component.instances[tabComponentId[selectedTab]], super.mouseX, 205, super.mouseY, 0);
			}
		}

		if (lastHoveredComponentId != invbackHoveredComponentId) {
			redrawInvback = true;
			invbackHoveredComponentId = lastHoveredComponentId;
		}

		lastHoveredComponentId = 0;

		if ((super.mouseX > 17) && (super.mouseY > 357) && (super.mouseX < 496) && (super.mouseY < 453)) {
			if (chatbackComponentId != -1) {
				handleParentComponentInput(17, Component.instances[chatbackComponentId], super.mouseX, 357, super.mouseY, 0);
			} else if ((super.mouseY < 434) && (super.mouseX < 426)) {
				handleChatInput(super.mouseY - 357);
			}
		}

		if ((chatbackComponentId != -1) && (lastHoveredComponentId != chatbackHoveredComponentId)) {
			redrawChatback = true;
			chatbackHoveredComponentId = lastHoveredComponentId;
		}

		// The code below pushes menu options with an action greater than 1000 to the top.
		boolean done = false;
		while (!done) {
			done = true;
			for (int i = 0; i < (menuSize - 1); i++) {
				if ((menuAction[i] < 1000) && (menuAction[i + 1] > 1000)) {
					String tmp0 = menuOption[i];
					menuOption[i] = menuOption[i + 1];
					menuOption[i + 1] = tmp0;

					int tmp1 = menuAction[i];
					menuAction[i] = menuAction[i + 1];
					menuAction[i + 1] = tmp1;

					tmp1 = menuParamA[i];
					menuParamA[i] = menuParamA[i + 1];
					menuParamA[i + 1] = tmp1;

					tmp1 = menuParamB[i];
					menuParamB[i] = menuParamB[i + 1];
					menuParamB[i + 1] = tmp1;

					tmp1 = menuParamC[i];
					menuParamC[i] = menuParamC[i + 1];
					menuParamC[i + 1] = tmp1;

					done = false;
				}
			}
		}
	}

	public int mix(int src, int dst, int alpha) {
		int invAlpha = 256 - alpha;
		return (((((src & 0xff00ff) * invAlpha) + ((dst & 0xff00ff) * alpha)) & 0xff00ff00) + ((((src & 0xff00) * invAlpha) + ((dst & 0xff00) * alpha)) & 0xff0000)) >> 8;
	}

	public void login(String username, String password, boolean reconnect) {
		try {
			if (!reconnect) {
				loginMessage0 = "";
				loginMessage1 = "Connecting to server...";
				drawTitleScreen(true);
			}
			connection = new Connection(this, openSocket(43594 + portOffset));
			long l = StringUtil.toBase37(username);
			int i = (int) ((l >> 16) & 31L);
			out.position = 0;
			out.put1(14);
			out.put1(i);
			connection.write(out.data, 0, 2);
			for (int j = 0; j < 8; j++) {
				connection.read();
			}
			int k = connection.read();
			int i1 = k;
			if (k == 0) {
				connection.read(in.data, 0, 8);
				in.position = 0;
				aLong1215 = in.get8();

				// apache math tries to fill the remaining 1008 bytes up with random junk if we don't give it 256 ints.
				int[] seed = new int[1 << 8];

				seed[0] = (int) (Math.random() * 99999999D);
				seed[1] = (int) (Math.random() * 99999999D);
				seed[2] = (int) (aLong1215 >> 32);
				seed[3] = (int) aLong1215;
				out.position = 0;
				out.put1(10);
				out.put4(seed[0]);
				out.put4(seed[1]);
				out.put4(seed[2]);
				out.put4(seed[3]);
				out.put4(Signlink.uid);
				out.put(username);
				out.put(password);
				out.encrypt(RSA_EXPONENT, RSA_MODULUS);
				login.position = 0;
				if (reconnect) {
					login.put1(18);
				} else {
					login.put1(16);
				}
				login.put1(out.position + 36 + 1 + 1 + 2);
				login.put1(255);
				login.put2(317);
				login.put1(lowmem ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++) {
					login.put4(archiveChecksum[l1]);
				}
				login.put(out.data, 0, out.position);
				out.random = new ISAACRandom(seed);
				for (int j2 = 0; j2 < 4; j2++) {
					seed[j2] += 50;
				}
				randomIn = new ISAACRandom(seed);
				connection.write(login.data, 0, login.position);
				k = connection.read();
			}
			if (k == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception ignored) {
				}
				login(username, password, reconnect);
				return;
			}
			if (k == 2) {
				rights = connection.read();
				aBoolean1205 = connection.read() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				mouseRecorder.anInt810 = 0;
				super.focused = true;
				_focused = true;
				ingame = true;
				out.position = 0;
				in.position = 0;
				ptype = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				psize = 0;
				anInt1009 = 0;
				systemUpdateTimer = 0;
				idleTimeout = 0;
				hintType = 0;
				menuSize = 0;
				menuVisible = false;
				super.idleCycles = 0;
				for (int j1 = 0; j1 < 100; j1++) {
					messageText[j1] = null;
				}
				anInt1282 = 0;
				anInt1136 = 0;
				sceneState = 0;
				waveCount = 0;
				cameraAnticheatOffsetX = (int) (Math.random() * 100D) - 50;
				cameraAnticheatOffsetZ = (int) (Math.random() * 110D) - 55;
				cameraAnticheatAngle = (int) (Math.random() * 80D) - 40;
				minimapAnticheatAngle = (int) (Math.random() * 120D) - 60;
				minimapZoom = (int) (Math.random() * 30D) - 20;
				cameraYaw = ((int) (Math.random() * 20D) - 10) & 0x7ff;
				minimapState = 0;
				minimapPlane = -1;
				flagSceneTileX = 0;
				flagSceneTileZ = 0;
				playerCount = 0;
				npcCount = 0;
				for (int i2 = 0; i2 < MAX_PLAYER_COUNT; i2++) {
					players[i2] = null;
					aBufferArray895[i2] = null;
				}
				for (int k2 = 0; k2 < 16384; k2++) {
					npcs[k2] = null;
				}
				localPlayer = players[LOCAL_PLAYER_INDEX] = new PlayerEntity();
				aList_1013.clear();
				spotanims.clear();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++) {
							planeObjStacks[l2][i3][k3] = null;
						}
					}
				}
				temporaryLocs = new DoublyLinkedList();
				socialState = 0;
				friendCount = 0;
				stickyChatbackComponentId = -1;
				chatbackComponentId = -1;
				viewportComponentId = -1;
				invbackComponentId = -1;
				viewportOverlayComponentId = -1;
				aBoolean1149 = false;
				selectedTab = 3;
				chatbackInputType = 0;
				menuVisible = false;
				showSocialInput = false;
				chatbackMessage = null;
				anInt1055 = 0;
				anInt1054 = -1;
				designGender = true;
				validateCharacterDesign();
				for (int j3 = 0; j3 < 5; j3++) {
					designColors[j3] = 0;
				}
				for (int l3 = 0; l3 < 5; l3++) {
					aStringArray1127[l3] = null;
					aBooleanArray1128[l3] = false;
				}
				prepareGameScreen();
				return;
			}
			if (k == 3) {
				loginMessage0 = "";
				loginMessage1 = "Invalid username or password.";
				return;
			}
			if (k == 4) {
				loginMessage0 = "Your account has been disabled.";
				loginMessage1 = "Please check your message-centre for details.";
				return;
			}
			if (k == 5) {
				loginMessage0 = "Your account is already logged in.";
				loginMessage1 = "Try again in 60 secs...";
				return;
			}
			if (k == 6) {
				loginMessage0 = "RuneScape has been updated!";
				loginMessage1 = "Please reload this page.";
				return;
			}
			if (k == 7) {
				loginMessage0 = "This world is full.";
				loginMessage1 = "Please use a different world.";
				return;
			}
			if (k == 8) {
				loginMessage0 = "Unable to connect.";
				loginMessage1 = "Login server offline.";
				return;
			}
			if (k == 9) {
				loginMessage0 = "Login limit exceeded.";
				loginMessage1 = "Too many connections from your address.";
				return;
			}
			if (k == 10) {
				loginMessage0 = "Unable to connect.";
				loginMessage1 = "Bad session id.";
				return;
			}
			if (k == 11) {
				loginMessage1 = "Login server rejected session.";
				loginMessage1 = "Please try again.";
				return;
			}
			if (k == 12) {
				loginMessage0 = "You need a members account to login to this world.";
				loginMessage1 = "Please subscribe, or use a different world.";
				return;
			}
			if (k == 13) {
				loginMessage0 = "Could not complete login.";
				loginMessage1 = "Please try using a different world.";
				return;
			}
			if (k == 14) {
				loginMessage0 = "The server is being updated.";
				loginMessage1 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 15) {
				ingame = true;
				out.position = 0;
				in.position = 0;
				ptype = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				psize = 0;
				anInt1009 = 0;
				systemUpdateTimer = 0;
				menuSize = 0;
				menuVisible = false;
				sceneLoadStartTime = System.currentTimeMillis();
				return;
			}
			if (k == 16) {
				loginMessage0 = "Login attempts exceeded.";
				loginMessage1 = "Please wait 1 minute and try again.";
				return;
			}
			if (k == 17) {
				loginMessage0 = "You are standing in a members-only area.";
				loginMessage1 = "To play on this world move to a free area first";
				return;
			}
			if (k == 20) {
				loginMessage0 = "Invalid loginserver requested";
				loginMessage1 = "Please try using a different world.";
				return;
			}
			if (k == 21) {
				for (int k1 = connection.read(); k1 >= 0; k1--) {
					loginMessage0 = "You have only just left another world";
					loginMessage1 = "Your profile will be transferred in: " + k1 + " seconds";
					drawTitleScreen(true);
					try {
						Thread.sleep(1000L);
					} catch (Exception ignored) {
					}
				}
				login(username, password, reconnect);
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
						login(username, password, reconnect);
					} else {
						loginMessage0 = "No response from loginserver";
						loginMessage1 = "Please wait 1 minute and try again.";
					}
				} else {
					loginMessage0 = "No response from server";
					loginMessage1 = "Please try using a different world.";
				}
			} else {
				System.out.println("response:" + k);
				loginMessage0 = "Unexpected server response";
				loginMessage1 = "Please try using a different world.";
			}
			return;
		} catch (IOException _ex) {
			loginMessage0 = "";
		}
		loginMessage1 = "Error connecting to server.";
	}

	public boolean tryMove(int type, int srcX, int srcZ, int dx, int dz, int locType, int locWidth, int locLength, int locAngle, int locInteractionFlags, boolean tryNearest) {
		byte sceneWidth = 104;
		byte sceneLength = 104;
		for (int x = 0; x < sceneWidth; x++) {
			for (int z = 0; z < sceneLength; z++) {
				bfsDirection[x][z] = 0;
				bfsCost[x][z] = 99999999;
			}
		}

		int x = srcX;
		int z = srcZ;

		bfsDirection[srcX][srcZ] = 99;
		bfsCost[srcX][srcZ] = 0;

		int steps = 0;
		int length = 0;

		bfsStepX[steps] = srcX;
		bfsStepZ[steps++] = srcZ;

		boolean arrived = false;
		int bufferSize = bfsStepX.length;
		int[][] flags = collisions[currentPlane].flags;

		while (length != steps) {
			x = bfsStepX[length];
			z = bfsStepZ[length];
			length = (length + 1) % bufferSize;

			if ((x == dx) && (z == dz)) {
				arrived = true;
				break;
			}

			if (locType != 0) {
				if (((locType < 5) || (locType == 10)) && collisions[currentPlane].method219(x, z, dx, dz, locAngle, locType - 1)) {
					arrived = true;
					break;
				}
				if ((locType < 10) && collisions[currentPlane].method220(x, z, dx, dz, locType - 1, locAngle)) {
					arrived = true;
					break;
				}
			}

			if ((locWidth != 0) && (locLength != 0) && collisions[currentPlane].method221(x, z, dx, dz, locWidth, locLength, locInteractionFlags)) {
				arrived = true;
				break;
			}

			int nextCost = bfsCost[x][z] + 1;

			if ((x > 0) && (bfsDirection[x - 1][z] == 0) && ((flags[x - 1][z] & 0x1280108) == 0)) {
				bfsStepX[steps] = x - 1;
				bfsStepZ[steps] = z;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x - 1][z] = 2;
				bfsCost[x - 1][z] = nextCost;
			}

			if ((x < (sceneWidth - 1)) && (bfsDirection[x + 1][z] == 0) && ((flags[x + 1][z] & 0x1280180) == 0)) {
				bfsStepX[steps] = x + 1;
				bfsStepZ[steps] = z;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x + 1][z] = 8;
				bfsCost[x + 1][z] = nextCost;
			}

			if ((z > 0) && (bfsDirection[x][z - 1] == 0) && ((flags[x][z - 1] & 0x1280102) == 0)) {
				bfsStepX[steps] = x;
				bfsStepZ[steps] = z - 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x][z - 1] = 1;
				bfsCost[x][z - 1] = nextCost;
			}

			if ((z < (sceneLength - 1)) && (bfsDirection[x][z + 1] == 0) && ((flags[x][z + 1] & 0x1280120) == 0)) {
				bfsStepX[steps] = x;
				bfsStepZ[steps] = z + 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x][z + 1] = 4;
				bfsCost[x][z + 1] = nextCost;
			}

			if ((x > 0) && (z > 0) && (bfsDirection[x - 1][z - 1] == 0) && ((flags[x - 1][z - 1] & 0x128010e) == 0) && ((flags[x - 1][z] & 0x1280108) == 0) && ((flags[x][z - 1] & 0x1280102) == 0)) {
				bfsStepX[steps] = x - 1;
				bfsStepZ[steps] = z - 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x - 1][z - 1] = 3;
				bfsCost[x - 1][z - 1] = nextCost;
			}

			if ((x < (sceneWidth - 1)) && (z > 0) && (bfsDirection[x + 1][z - 1] == 0) && ((flags[x + 1][z - 1] & 0x1280183) == 0) && ((flags[x + 1][z] & 0x1280180) == 0) && ((flags[x][z - 1] & 0x1280102) == 0)) {
				bfsStepX[steps] = x + 1;
				bfsStepZ[steps] = z - 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x + 1][z - 1] = 9;
				bfsCost[x + 1][z - 1] = nextCost;
			}

			if ((x > 0) && (z < (sceneLength - 1)) && (bfsDirection[x - 1][z + 1] == 0) && ((flags[x - 1][z + 1] & 0x1280138) == 0) && ((flags[x - 1][z] & 0x1280108) == 0) && ((flags[x][z + 1] & 0x1280120) == 0)) {
				bfsStepX[steps] = x - 1;
				bfsStepZ[steps] = z + 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x - 1][z + 1] = 6;
				bfsCost[x - 1][z + 1] = nextCost;
			}

			if ((x < (sceneWidth - 1)) && (z < (sceneLength - 1)) && (bfsDirection[x + 1][z + 1] == 0) && ((flags[x + 1][z + 1] & 0x12801e0) == 0) && ((flags[x + 1][z] & 0x1280180) == 0) && ((flags[x][z + 1] & 0x1280120) == 0)) {
				bfsStepX[steps] = x + 1;
				bfsStepZ[steps] = z + 1;
				steps = (steps + 1) % bufferSize;
				bfsDirection[x + 1][z + 1] = 12;
				bfsCost[x + 1][z + 1] = nextCost;
			}
		}

		tryMoveNearest = 0;

		if (!arrived) {
			if (tryNearest) {
				int min = 100;
				for (int padding = 1; padding < 2; padding++) {
					for (int px = dx - padding; px <= (dx + padding); px++) {
						for (int pz = dz - padding; pz <= (dz + padding); pz++) {
							if ((px >= 0) && (pz >= 0) && (px < 104) && (pz < 104) && (bfsCost[px][pz] < min)) {
								min = bfsCost[px][pz];
								x = px;
								z = pz;
								tryMoveNearest = 1;
								arrived = true;
							}
						}
					}
					if (arrived) {
						break;
					}
				}
			}
			if (!arrived) {
				return false;
			}
		}

		length = 0;
		bfsStepX[length] = x;
		bfsStepZ[length++] = z;

		int dir = bfsDirection[x][z];
		int next = dir;

		// build our path into bfsStepX/Z starting from our destination.
		// bfsStep[0->n] (dst->src)
		while ((x != srcX) || (z != srcZ)) {
			if (next != dir) {
				dir = next;
				bfsStepX[length] = x;
				bfsStepZ[length++] = z;
			}

			if ((next & 2) != 0) {
				x++;
			} else if ((next & 8) != 0) {
				x--;
			}

			if ((next & 1) != 0) {
				z++;
			} else if ((next & 4) != 0) {
				z--;
			}

			next = bfsDirection[x][z];
		}

		if (length > 0) {
			int count = length;

			// a move packet is limited to 25 steps
			if (count > 25) {
				count = 25;
			}

			length--;

			int startX = bfsStepX[length];
			int startZ = bfsStepZ[length];

			if (type == 0) {
				out.putOp(164);
				out.put1(count + count + 3);
			} else if (type == 1) {
				out.putOp(248);
				out.put1(count + count + 3 + 14);
			} else if (type == 2) {
				out.putOp(98);
				out.put1(count + count + 3);
			}

			out.put2LEA(startX + sceneBaseTileX);

			flagSceneTileX = bfsStepX[0];
			flagSceneTileZ = bfsStepZ[0];

			for (int i = 1; i < count; i++) {
				length--;
				out.put1(bfsStepX[length] - startX);
				out.put1(bfsStepZ[length] - startZ);
			}

			out.put2LE(startZ + sceneBaseTileZ);
			out.put1C((super.actionKey[5] != 1) ? 0 : 1);
			return true;
		}

		return type != 1;
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
				if ((i1 == npc.seqId1) && (i1 != -1)) {
					int l2 = SeqType.instances[i1].anInt365;
					if (l2 == 1) {
						npc.seqFrame1 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if (l2 == 2) {
						npc.anInt1530 = 0;
					}
				} else if ((i1 == -1) || (npc.seqId1 == -1) || (SeqType.instances[i1].priority >= SeqType.instances[npc.seqId1].priority)) {
					npc.seqId1 = i1;
					npc.seqFrame1 = 0;
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
				npc.combatCycle = loopCycle + 300;
				npc.health = buffer.get1UA();
				npc.totalHealth = buffer.get1U();
			}
			if ((l & 0x80) != 0) {
				npc.spotanim = buffer.get2U();
				int k1 = buffer.get4();
				npc.spotanimY = k1 >> 16;
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
				npc.chat = buffer.getString();
				npc.chatTimer = 100;
			}
			if ((l & 0x40) != 0) {
				int l1 = buffer.get1UC();
				int k2 = buffer.get1US();
				npc.method447(k2, l1, loopCycle);
				npc.combatCycle = loopCycle + 300;
				npc.health = buffer.get1US();
				npc.totalHealth = buffer.get1UC();
			}
			if ((l & 2) != 0) {
				npc.type = NPCType.get(buffer.get2ULEA());
				npc.size = npc.type.size;
				npc.turnSpeed = npc.type.turnSpeed;
				npc.seqWalk = npc.type.seqWalkId;
				npc.seqTurnAround = npc.type.seqTurnAroundId;
				npc.seqTurnLeft = npc.type.seqTurnLeft;
				npc.seqTurnRight = npc.type.seqTurnRightId;
				npc.seqStand = npc.type.seqStand;
			}
			if ((l & 4) != 0) {
				npc.faceTileX = buffer.get2ULE();
				npc.faceTileZ = buffer.get2ULE();
			}
		}
	}

	public void method87(NPCType type, int i, int j, int k) {
		if (menuSize >= 400) {
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
		String s = type.name;
		if (type.level != 0) {
			s = s + method110(localPlayer.combatLevel, type.level) + " (level-" + type.level + ")";
		}
		if (anInt1282 == 1) {
			menuOption[menuSize] = "Use " + aString1286 + " with @yel@" + s;
			menuAction[menuSize] = 582;
			menuParamC[menuSize] = i;
			menuParamA[menuSize] = k;
			menuParamB[menuSize] = j;
			menuSize++;
			return;
		}
		if (anInt1136 == 1) {
			if ((activeSpellFlags & 2) == 2) {
				menuOption[menuSize] = spellCaption + " @yel@" + s;
				menuAction[menuSize] = 413;
				menuParamC[menuSize] = i;
				menuParamA[menuSize] = k;
				menuParamB[menuSize] = j;
				menuSize++;
			}
		} else {
			if (type.op != null) {
				for (int l = 4; l >= 0; l--) {
					if ((type.op[l] != null) && !type.op[l].equalsIgnoreCase("attack")) {
						menuOption[menuSize] = type.op[l] + " @yel@" + s;
						if (l == 0) {
							menuAction[menuSize] = 20;
						}
						if (l == 1) {
							menuAction[menuSize] = 412;
						}
						if (l == 2) {
							menuAction[menuSize] = 225;
						}
						if (l == 3) {
							menuAction[menuSize] = 965;
						}
						if (l == 4) {
							menuAction[menuSize] = 478;
						}
						menuParamC[menuSize] = i;
						menuParamA[menuSize] = k;
						menuParamB[menuSize] = j;
						menuSize++;
					}
				}
			}
			if (type.op != null) {
				for (int i1 = 4; i1 >= 0; i1--) {
					if ((type.op[i1] != null) && type.op[i1].equalsIgnoreCase("attack")) {
						char c = '\0';
						if (type.level > localPlayer.combatLevel) {
							c = '\u07D0';
						}
						menuOption[menuSize] = type.op[i1] + " @yel@" + s;
						if (i1 == 0) {
							menuAction[menuSize] = 20 + c;
						}
						if (i1 == 1) {
							menuAction[menuSize] = 412 + c;
						}
						if (i1 == 2) {
							menuAction[menuSize] = 225 + c;
						}
						if (i1 == 3) {
							menuAction[menuSize] = 965 + c;
						}
						if (i1 == 4) {
							menuAction[menuSize] = 478 + c;
						}
						menuParamC[menuSize] = i;
						menuParamA[menuSize] = k;
						menuParamB[menuSize] = j;
						menuSize++;
					}
				}
			}
			menuOption[menuSize] = "Examine @yel@" + s;
			menuAction[menuSize] = 1025;
			menuParamC[menuSize] = i;
			menuParamA[menuSize] = k;
			menuParamB[menuSize] = j;
			menuSize++;
		}
	}

	public void method88(int i, int j, PlayerEntity player, int k) {
		if (player == localPlayer) {
			return;
		}
		if (menuSize >= 400) {
			return;
		}
		String s;
		if (player.skillLevel == 0) {
			s = player.name + method110(localPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
		} else {
			s = player.name + " (skill-" + player.skillLevel + ")";
		}
		if (anInt1282 == 1) {
			menuOption[menuSize] = "Use " + aString1286 + " with @whi@" + s;
			menuAction[menuSize] = 491;
			menuParamC[menuSize] = j;
			menuParamA[menuSize] = i;
			menuParamB[menuSize] = k;
			menuSize++;
		} else if (anInt1136 == 1) {
			if ((activeSpellFlags & 8) == 8) {
				menuOption[menuSize] = spellCaption + " @whi@" + s;
				menuAction[menuSize] = 365;
				menuParamC[menuSize] = j;
				menuParamA[menuSize] = i;
				menuParamB[menuSize] = k;
				menuSize++;
			}
		} else {
			for (int l = 4; l >= 0; l--) {
				if (aStringArray1127[l] != null) {
					menuOption[menuSize] = aStringArray1127[l] + " @whi@" + s;
					char c = '\0';
					if (aStringArray1127[l].equalsIgnoreCase("attack")) {
						if (player.combatLevel > localPlayer.combatLevel) {
							c = '\u07D0';
						}
						if ((localPlayer.team != 0) && (player.team != 0)) {
							if (localPlayer.team == player.team) {
								c = '\u07D0';
							} else {
								c = '\0';
							}
						}
					} else if (aBooleanArray1128[l]) {
						c = '\u07D0';
					}
					if (l == 0) {
						menuAction[menuSize] = 561 + c;
					}
					if (l == 1) {
						menuAction[menuSize] = 779 + c;
					}
					if (l == 2) {
						menuAction[menuSize] = 27 + c;
					}
					if (l == 3) {
						menuAction[menuSize] = 577 + c;
					}
					if (l == 4) {
						menuAction[menuSize] = 729 + c;
					}
					menuParamC[menuSize] = j;
					menuParamA[menuSize] = i;
					menuParamB[menuSize] = k;
					menuSize++;
				}
			}
		}
		for (int i1 = 0; i1 < menuSize; i1++) {
			if (menuAction[i1] == 516) {
				menuOption[i1] = "Walk here @whi@" + s;
				return;
			}
		}
	}

	public void storeLoc(SceneLocTemporary loc) {
		int bitset = 0;
		int locId = -1;
		int kind = 0;
		int rotation = 0;
		if (loc.classId == 0) {
			bitset = scene.getWallBitset(loc.plane, loc.localX, loc.localZ);
		}
		if (loc.classId == 1) {
			bitset = scene.getWallDecorationBitset(loc.plane, loc.localX, loc.localZ);
		}
		if (loc.classId == 2) {
			bitset = scene.getLocBitset(loc.plane, loc.localX, loc.localZ);
		}
		if (loc.classId == 3) {
			bitset = scene.getGroundDecorationBitset(loc.plane, loc.localX, loc.localZ);
		}
		if (bitset != 0) {
			int info = scene.getInfo(loc.plane, loc.localX, loc.localZ, bitset);
			locId = (bitset >> 14) & 0x7fff;
			kind = info & 0x1f;
			rotation = info >> 6;
		}
		loc.savedLocId = locId;
		loc.savedKind = kind;
		loc.savedRotation = rotation;
	}

	public void updateAudio() {
		for (int i = 0; i < waveCount; i++) {
			if (waveDelay[i] > 0) {
				waveDelay[i]--;
			} else {
				boolean failed = false;

				try {
					if ((waveIds[i] == lastWaveId) && (waveLoops[i] == lastWaveLoops)) {
						if (!wavereplay()) {
							failed = true;
						}
					} else {
						Buffer buffer = SoundTrack.generate(waveLoops[i], waveIds[i]);

						// the sample rate is 22050Hz and sample size is 1 byte which means dividing the bytes by 22 is
						// roughly converting the bytes to time in milliseconds
						if ((System.currentTimeMillis() + (long) (buffer.position / 22)) > (lastWaveStartTime + (long) (lastWaveLength / 22))) {
							lastWaveLength = buffer.position;
							lastWaveStartTime = System.currentTimeMillis();

							if (wavesave(buffer.data, buffer.position)) {
								lastWaveId = waveIds[i];
								lastWaveLoops = waveLoops[i];
							} else {
								failed = true;
							}
						}
					}
				} catch (Exception ignored) {
				}

				if (!failed || (waveDelay[i] == -5)) {
					waveCount--;
					for (int j = i; j < waveCount; j++) {
						waveIds[j] = waveIds[j + 1];
						waveLoops[j] = waveLoops[j + 1];
						waveDelay[j] = waveDelay[j + 1];
					}
					i--;
				} else {
					waveDelay[i] = -5;
				}
			}
		}

		if (nextMusicDelay > 0) {
			nextMusicDelay -= 20;
			if (nextMusicDelay < 0) {
				nextMusicDelay = 0;
			}
			if ((nextMusicDelay == 0) && midiActive && !lowmem) {
				music = nextMusic;
				musicFading = true;
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
			playerIndices[playerCount++] = j;
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
			player.move(localPlayer.pathTileX[0] + j1, localPlayer.pathTileZ[0] + i1, l == 1);
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
				int k = (cameraYaw + minimapAnticheatAngle) & 0x7ff;
				int i1 = Draw3D.sin[k];
				int j1 = Draw3D.cos[k];
				i1 = (i1 * (minimapZoom + 256)) >> 8;
				j1 = (j1 * (minimapZoom + 256)) >> 8;
				int k1 = ((j * i1) + (i * j1)) >> 11;
				int l1 = ((j * j1) - (i * i1)) >> 11;
				int i2 = (localPlayer.x + k1) >> 7;
				int j2 = (localPlayer.z - l1) >> 7;
				boolean flag1 = tryMove(1, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], i2, j2, 0, 0, 0, 0, 0, true);
				if (flag1) {
					out.put1(i);
					out.put1(j);
					out.put2(cameraYaw);
					out.put1(57);
					out.put1(minimapAnticheatAngle);
					out.put1(minimapZoom);
					out.put1(89);
					out.put2(localPlayer.x);
					out.put2(localPlayer.z);
					out.put1(tryMoveNearest);
					out.put1(63);
				}
			}
		}
	}

	public String getIntString(int i) {
		if (i < 999999999) {
			return String.valueOf(i);
		} else {
			return "*";
		}
	}

	public void method94() {
		Graphics g = this.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		setFrameRate(1);
		if (errorLoading) {
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
		if (errorHost) {
			flameActive = false;
			g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play RuneScape make sure you play from", 50, 100);
			g.drawString("http://www.runescape.com", 50, 150);
		}
		if (errorStarted) {
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
		for (int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			NPCEntity npc = npcs[k];
			if (npc != null) {
				method96(npc);
			}
		}
	}

	public void method96(PathingEntity entity) {
		if ((entity.x < 128) || (entity.z < 128) || (entity.x >= 13184) || (entity.z >= 13184)) {
			entity.seqId1 = -1;
			entity.spotanim = -1;
			entity.forceMoveEndCycle = 0;
			entity.forceMoveStartCycle = 0;
			entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
			entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
			entity.method446();
		}
		if ((entity == localPlayer) && ((entity.x < 1536) || (entity.z < 1536) || (entity.x >= 11776) || (entity.z >= 11776))) {
			entity.seqId1 = -1;
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
		if ((entity.forceMoveStartCycle == loopCycle) || (entity.seqId1 == -1) || (entity.anInt1529 != 0) || ((entity.anInt1528 + 1) > SeqType.instances[entity.seqId1].getFrameDelay(entity.seqFrame1))) {
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
		entity.seqId2 = entity.seqStand;

		if (entity.pathRemaining == 0) {
			entity.anInt1503 = 0;
			return;
		}

		if ((entity.seqId1 != -1) && (entity.anInt1529 == 0)) {
			SeqType type = SeqType.instances[entity.seqId1];

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
		// See PreviewSinCos2D

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

		entity.seqId2 = seq;

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

		if ((moveSpeed >= 8) && (entity.seqId2 == entity.seqWalk) && (entity.seqRun != -1)) {
			entity.seqId2 = entity.seqRun;
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

			if (index == localPlayerId) {
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

			if ((e.seqId2 == e.seqStand) && (e.yaw != e.dstYaw)) {
				if (e.seqTurn != -1) {
					e.seqId2 = e.seqTurn;
					return;
				}

				e.seqId2 = e.seqWalk;
			}
		}
	}

	public void method101(PathingEntity e) {
		e.aBoolean1541 = false;

		if (e.seqId2 != -1) {
			SeqType seq = SeqType.instances[e.seqId2];
			e.seqCycle++;

			if ((e.seqFrame2 < seq.frameCount) && (e.seqCycle > seq.getFrameDelay(e.seqFrame2))) {
				e.seqCycle = 0;
				e.seqFrame2++;
			}

			if (e.seqFrame2 >= seq.frameCount) {
				e.seqCycle = 0;
				e.seqFrame2 = 0;
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

		if ((e.seqId1 != -1) && (e.anInt1529 <= 1)) {
			SeqType seq = SeqType.instances[e.seqId1];

			if ((seq.anInt363 == 1) && (e.anInt1542 > 0) && (e.forceMoveEndCycle <= loopCycle) && (e.forceMoveStartCycle < loopCycle)) {
				e.anInt1529 = 1;
				return;
			}
		}

		if ((e.seqId1 != -1) && (e.anInt1529 == 0)) {
			SeqType seq = SeqType.instances[e.seqId1];

			for (e.anInt1528++; (e.seqFrame1 < seq.frameCount) && (e.anInt1528 > seq.getFrameDelay(e.seqFrame1)); e.seqFrame1++) {
				e.anInt1528 -= seq.getFrameDelay(e.seqFrame1);
			}

			if (e.seqFrame1 >= seq.frameCount) {
				e.seqFrame1 -= seq.speed;
				e.anInt1530++;

				if (e.anInt1530 >= seq.anInt362) {
					e.seqId1 = -1;
				}
				if ((e.seqFrame1 < 0) || (e.seqFrame1 >= seq.frameCount)) {
					e.seqId1 = -1;
				}
			}
			e.aBoolean1541 = seq.aBoolean358;
		}

		if (e.anInt1529 > 0) {
			e.anInt1529--;
		}
	}

	public void method102() {
		if (redrawTitleBackground) {
			redrawTitleBackground = false;
			areaBackleft1.draw(super.graphics, 0, 4);
			areaBackleft2.draw(super.graphics, 0, 357);
			areaBackright1.draw(super.graphics, 722, 4);
			areaBackright2.draw(super.graphics, 743, 205);
			areaBacktop1.draw(super.graphics, 0, 0);
			areaBackvmid1.draw(super.graphics, 516, 4);
			areaBackvmid2.draw(super.graphics, 516, 205);
			areaBackvmid3.draw(super.graphics, 496, 357);
			areaBackhmid2.draw(super.graphics, 0, 338);
			redrawInvback = true;
			redrawChatback = true;
			redrawSideicons = true;
			redrawPrivacySettings = true;
			if (sceneState != 2) {
				areaViewport.draw(super.graphics, 4, 4);
				areaMapback.draw(super.graphics, 550, 4);
			}
		}
		if (sceneState == 2) {
			method146();
		}
		if (menuVisible && (mouseArea == 1)) {
			redrawInvback = true;
		}
		if (invbackComponentId != -1) {
			if (updateParentComponentSeq(delta, invbackComponentId)) {
				redrawInvback = true;
			}
		}
		if (anInt1246 == 2) {
			redrawInvback = true;
		}
		if (objDragArea == 2) {
			redrawInvback = true;
		}
		if (redrawInvback) {
			drawInvback();
			redrawInvback = false;
		}
		if (chatbackComponentId == -1) {
			aComponent_1059.scrollY = chatScrollHeight - anInt1089 - 77;
			if ((super.mouseX > 448) && (super.mouseX < 560) && (super.mouseY > 332)) {
				method65(463, 77, super.mouseX - 17, super.mouseY - 357, aComponent_1059, 0, false, chatScrollHeight);
			}
			int i = chatScrollHeight - 77 - aComponent_1059.scrollY;
			if (i < 0) {
				i = 0;
			}
			if (i > (chatScrollHeight - 77)) {
				i = chatScrollHeight - 77;
			}
			if (anInt1089 != i) {
				anInt1089 = i;
				redrawChatback = true;
			}
		}
		if (chatbackComponentId != -1) {
			if (updateParentComponentSeq(delta, chatbackComponentId)) {
				redrawChatback = true;
			}
		}
		if (anInt1246 == 3) {
			redrawChatback = true;
		}
		if (objDragArea == 3) {
			redrawChatback = true;
		}
		if (chatbackMessage != null) {
			redrawChatback = true;
		}
		if (menuVisible && (mouseArea == 2)) {
			redrawChatback = true;
		}

		if (redrawChatback) {
			drawChatback();
			redrawChatback = false;
		}

		if (sceneState == 2) {
			drawMinimap();
			areaMapback.draw(super.graphics, 550, 4);
		}

		if (anInt1054 != -1) {
			redrawSideicons = true;
		}

		if (redrawSideicons) {
			if ((anInt1054 != -1) && (anInt1054 == selectedTab)) {
				anInt1054 = -1;
				out.putOp(120);
				out.put1(selectedTab);
			}
			redrawSideicons = false;
			areaBackhmid1.bind();
			imageBackhmid1.blit(0, 0);
			if (invbackComponentId == -1) {
				if (tabComponentId[selectedTab] != -1) {
					if (selectedTab == 0) {
						imageRedstone1.blit(22, 10);
					}
					if (selectedTab == 1) {
						imageRedstone2.blit(54, 8);
					}
					if (selectedTab == 2) {
						imageRedstone2.blit(82, 8);
					}
					if (selectedTab == 3) {
						imageRedstone3.blit(110, 8);
					}
					if (selectedTab == 4) {
						imageRedstone2h.blit(153, 8);
					}
					if (selectedTab == 5) {
						imageRedstone2h.blit(181, 8);
					}
					if (selectedTab == 6) {
						imageRedstone1h.blit(209, 9);
					}
				}
				if ((tabComponentId[0] != -1) && ((anInt1054 != 0) || ((loopCycle % 20) < 10))) {
					imageSideicons[0].blit(29, 13);
				}
				if ((tabComponentId[1] != -1) && ((anInt1054 != 1) || ((loopCycle % 20) < 10))) {
					imageSideicons[1].blit(53, 11);
				}
				if ((tabComponentId[2] != -1) && ((anInt1054 != 2) || ((loopCycle % 20) < 10))) {
					imageSideicons[2].blit(82, 11);
				}
				if ((tabComponentId[3] != -1) && ((anInt1054 != 3) || ((loopCycle % 20) < 10))) {
					imageSideicons[3].blit(115, 12);
				}
				if ((tabComponentId[4] != -1) && ((anInt1054 != 4) || ((loopCycle % 20) < 10))) {
					imageSideicons[4].blit(153, 13);
				}
				if ((tabComponentId[5] != -1) && ((anInt1054 != 5) || ((loopCycle % 20) < 10))) {
					imageSideicons[5].blit(180, 11);
				}
				if ((tabComponentId[6] != -1) && ((anInt1054 != 6) || ((loopCycle % 20) < 10))) {
					imageSideicons[6].blit(208, 13);
				}
			}
			areaBackhmid1.draw(super.graphics, 516, 160);
			areaBackbase2.bind();
			imageBackbase2.blit(0, 0);
			if (invbackComponentId == -1) {
				if (tabComponentId[selectedTab] != -1) {
					if (selectedTab == 7) {
						imageRedstone1v.blit(42, 0);
					}
					if (selectedTab == 8) {
						imageRedstone2v.blit(74, 0);
					}
					if (selectedTab == 9) {
						imageRedstone2v.blit(102, 0);
					}
					if (selectedTab == 10) {
						imageRedstone3v.blit(130, 1);
					}
					if (selectedTab == 11) {
						imageRedstone2hv.blit(173, 0);
					}
					if (selectedTab == 12) {
						imageRedstone2hv.blit(201, 0);
					}
					if (selectedTab == 13) {
						imageRedstone1hv.blit(229, 0);
					}
				}
				if ((tabComponentId[8] != -1) && ((anInt1054 != 8) || ((loopCycle % 20) < 10))) {
					imageSideicons[7].blit(74, 2);
				}
				if ((tabComponentId[9] != -1) && ((anInt1054 != 9) || ((loopCycle % 20) < 10))) {
					imageSideicons[8].blit(102, 3);
				}
				if ((tabComponentId[10] != -1) && ((anInt1054 != 10) || ((loopCycle % 20) < 10))) {
					imageSideicons[9].blit(137, 4);
				}
				if ((tabComponentId[11] != -1) && ((anInt1054 != 11) || ((loopCycle % 20) < 10))) {
					imageSideicons[10].blit(174, 2);
				}
				if ((tabComponentId[12] != -1) && ((anInt1054 != 12) || ((loopCycle % 20) < 10))) {
					imageSideicons[11].blit(201, 2);
				}
				if ((tabComponentId[13] != -1) && ((anInt1054 != 13) || ((loopCycle % 20) < 10))) {
					imageSideicons[12].blit(226, 2);
				}
			}
			areaBackbase2.draw(super.graphics, 496, 466);
			areaViewport.bind();
		}

		if (redrawPrivacySettings) {
			redrawPrivacySettings = false;
			areaBackbase1.bind();
			imageBackbase1.blit(0, 0);
			fontPlain12.drawStringTaggableCenter("Public chat", 55, 28, 0xffffff, true);
			if (publicChatSetting == 0) {
				fontPlain12.drawStringTaggableCenter("On", 55, 41, 65280, true);
			}
			if (publicChatSetting == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 55, 41, 0xffff00, true);
			}
			if (publicChatSetting == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 55, 41, 0xff0000, true);
			}
			if (publicChatSetting == 3) {
				fontPlain12.drawStringTaggableCenter("Hide", 55, 41, 65535, true);
			}
			fontPlain12.drawStringTaggableCenter("Private chat", 184, 28, 0xffffff, true);
			if (privateChatSetting == 0) {
				fontPlain12.drawStringTaggableCenter("On", 184, 41, 65280, true);
			}
			if (privateChatSetting == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 184, 41, 0xffff00, true);
			}
			if (privateChatSetting == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 184, 41, 0xff0000, true);
			}
			fontPlain12.drawStringTaggableCenter("Trade/compete", 324, 28, 0xffffff, true);
			if (tradeChatSetting == 0) {
				fontPlain12.drawStringTaggableCenter("On", 324, 41, 65280, true);
			}
			if (tradeChatSetting == 1) {
				fontPlain12.drawStringTaggableCenter("Friends", 324, 41, 0xffff00, true);
			}
			if (tradeChatSetting == 2) {
				fontPlain12.drawStringTaggableCenter("Off", 324, 41, 0xff0000, true);
			}
			fontPlain12.drawStringTaggableCenter("Report abuse", 458, 33, 0xffffff, true);
			areaBackbase1.draw(super.graphics, 0, 453);
			areaViewport.bind();
		}

		delta = 0;
	}

	public boolean method103(Component component) {
		int i = component.contentType;
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
			menuOption[menuSize] = "Remove @whi@" + friendName[i];
			menuAction[menuSize] = 792;
			menuSize++;
			menuOption[menuSize] = "Message @whi@" + friendName[i];
			menuAction[menuSize] = 639;
			menuSize++;
			return true;
		}
		if ((i >= 401) && (i <= 500)) {
			menuOption[menuSize] = "Remove @whi@" + component.text;
			menuAction[menuSize] = 322;
			menuSize++;
			return true;
		} else {
			return false;
		}
	}

	public void updateSpotAnims() {
		SpotAnimEntity anim = (SpotAnimEntity) spotanims.peekFront();

		for (; anim != null; anim = (SpotAnimEntity) spotanims.prev()) {
			if ((anim.plane != currentPlane) || anim.seqComplete) {
				anim.unlink();
			} else if (loopCycle >= anim.startCycle) {
				anim.update(delta);
				if (anim.seqComplete) {
					anim.unlink();
				} else {
					scene.addTemporary(anim, anim.plane, anim.x, anim.z, anim.y, 0, -1, false, 60);
				}
			}
		}
	}

	public void drawParentComponent(Component parent, int px, int py, int scrollY) {
		if ((parent.type != 0) || (parent.children == null)) {
			return;
		}

		if (parent.hidden && (viewportHoveredComponentId != parent.id) && (invbackHoveredComponentId != parent.id) && (chatbackHoveredComponentId != parent.id)) {
			return;
		}

		int left = Draw2D.left;
		int top = Draw2D.top;
		int right = Draw2D.right;
		int bottom = Draw2D.bottom;

		Draw2D.setBounds(px, py, px + parent.width, py + parent.height);

		for (int i = 0; i < parent.children.length; i++) {
			int x = parent.childX[i] + px;
			int y = (parent.childY[i] + py) - scrollY;

			Component child = Component.instances[parent.children[i]];

			x += child.x;
			y += child.y;

			if (child.contentType > 0) {
				updateComponentContent(child);
			}

			if (child.type == 0) {
				if (child.scrollY > (child.scrollHeight - child.height)) {
					child.scrollY = child.scrollHeight - child.height;
				}

				if (child.scrollY < 0) {
					child.scrollY = 0;
				}

				drawParentComponent(child, x, y, child.scrollY);

				if (child.scrollHeight > child.height) {
					drawScrollbar(x + child.width, y, child.height, child.scrollHeight, child.scrollY);
				}
			} else if (child.type == 1) {
				// unused
			} else if (child.type == 2) {
				int slot = 0;
				for (int row = 0; row < child.height; row++) {
					for (int column = 0; column < child.width; column++) {
						int slotX = x + (column * (32 + child.invMarginX));
						int slotY = y + (row * (32 + child.invMarginY));

						if (slot < 20) {
							slotX += child.invSlotX[slot];
							slotY += child.invSlotY[slot];
						}

						if (child.invSlotObjId[slot] > 0) {
							int dx = 0;
							int dy = 0;
							int objId = child.invSlotObjId[slot] - 1;

							if (((slotX > (Draw2D.left - 32)) && (slotX < Draw2D.right) && (slotY > (Draw2D.top - 32)) && (slotY < Draw2D.bottom)) || ((objDragArea != 0) && (objDragSlot == slot))) {
								int outlineColor = 0;

								if ((anInt1282 == 1) && (anInt1283 == slot) && (anInt1284 == child.id)) {
									outlineColor = 0xffffff;
								}

								Image24 itemIcon = ObjType.getIcon(objId, child.invSlotAmount[slot], outlineColor);

								if (itemIcon != null) {
									if ((objDragArea != 0) && (objDragSlot == slot) && (objDragComponentId == child.id)) {
										dx = super.mouseX - objGrabX;
										dy = super.mouseY - objGrabY;

										if ((dx < 5) && (dx > -5)) {
											dx = 0;
										}

										if ((dy < 5) && (dy > -5)) {
											dy = 0;
										}

										if (objDragCycles < 5) {
											dx = 0;
											dy = 0;
										}

										itemIcon.draw(slotX + dx, slotY + dy, 128);

										// scroll component up if dragging obj near the top
										if (((slotY + dy) < Draw2D.top) && (parent.scrollY > 0)) {
											int scroll = (delta * (Draw2D.top - slotY - dy)) / 3;

											if (scroll > (delta * 10)) {
												scroll = delta * 10;
											}

											if (scroll > parent.scrollY) {
												scroll = parent.scrollY;
											}
											parent.scrollY -= scroll;
											objGrabY += scroll;
										}

										// scroll component down if dragging obj near the bottom
										if (((slotY + dy + 32) > Draw2D.bottom) && (parent.scrollY < (parent.scrollHeight - parent.height))) {
											int scroll = (delta * ((slotY + dy + 32) - Draw2D.bottom)) / 3;

											if (scroll > (delta * 10)) {
												scroll = delta * 10;
											}

											if (scroll > (parent.scrollHeight - parent.height - parent.scrollY)) {
												scroll = parent.scrollHeight - parent.height - parent.scrollY;
											}
											parent.scrollY += scroll;
											objGrabY -= scroll;
										}
									} else if ((anInt1246 != 0) && (anInt1245 == slot) && (anInt1244 == child.id)) {
										itemIcon.draw(slotX, slotY, 128);
									} else {
										itemIcon.draw(slotX, slotY);
									}

									// draw item amount
									if ((itemIcon.cropW == 33) || (child.invSlotAmount[slot] != 1)) {
										int amount = child.invSlotAmount[slot];
										fontPlain11.drawString(formatObjAmount(amount), slotX + 1 + dx, slotY + 10 + dy, 0);
										fontPlain11.drawString(formatObjAmount(amount), slotX + dx, slotY + 9 + dy, 0xffff00);
									}
								}
							}
						} else if ((child.invSlotImage != null) && (slot < 20)) {
							Image24 class30_sub2_sub1_sub1_1 = child.invSlotImage[slot];
							if (class30_sub2_sub1_sub1_1 != null) {
								class30_sub2_sub1_sub1_1.draw(slotX, slotY);
							}
						}
						slot++;
					}
				}
			} else if (child.type == 3) {
				boolean hovered = (chatbackHoveredComponentId == child.id) || (invbackHoveredComponentId == child.id) || (viewportHoveredComponentId == child.id);
				int rgb;

				if (getComponentScriptState(child)) {
					rgb = child.activeColor;
					if (hovered && (child.activeHoverColor != 0)) {
						rgb = child.activeHoverColor;
					}
				} else {
					rgb = child.color;
					if (hovered && (child.hoverColor != 0)) {
						rgb = child.hoverColor;
					}
				}

				if (child.transparency == 0) {
					if (child.fill) {
						Draw2D.fillRect(x, y, child.width, child.height, rgb);
					} else {
						Draw2D.drawRect(x, y, child.width, child.height, rgb);
					}
				} else if (child.fill) {
					Draw2D.fillRect(x, y, child.width, child.height, rgb, 256 - (child.transparency & 0xff));
				} else {
					Draw2D.drawRect(x, y, child.width, child.height, rgb, 256 - (child.transparency & 0xff));
				}
			} else if (child.type == 4) {
				BitmapFont font = child.font;
				String text = child.text;
				boolean hovered = false;

				if ((chatbackHoveredComponentId == child.id) || (invbackHoveredComponentId == child.id) || (viewportHoveredComponentId == child.id)) {
					hovered = true;
				}

				int rgb;
				if (getComponentScriptState(child)) {
					rgb = child.activeColor;

					if (hovered && (child.activeHoverColor != 0)) {
						rgb = child.activeHoverColor;
					}

					if (child.activeText.length() > 0) {
						text = child.activeText;
					}
				} else {
					rgb = child.color;

					if (hovered && (child.hoverColor != 0)) {
						rgb = child.hoverColor;
					}
				}

				if ((child.optionType == 6) && aBoolean1149) {
					text = "Please wait...";
					rgb = child.color;
				}

				if (Draw2D.width == 479) {
					if (rgb == 0xffff00) {
						rgb = 0xFF;
					}
					if (rgb == 0xC000) {
						rgb = 0xffffff;
					}
				}

				for (int lineY = y + font.height; text.length() > 0; lineY += font.height) {
					if (text.contains("%")) {
						do {
							int j = text.indexOf("%1");
							if (j == -1) {
								break;
							}
							text = text.substring(0, j) + getIntString(executeClientscript1(child, 0)) + text.substring(j + 2);
						} while (true);
						do {
							int j = text.indexOf("%2");
							if (j == -1) {
								break;
							}
							text = text.substring(0, j) + getIntString(executeClientscript1(child, 1)) + text.substring(j + 2);
						} while (true);
						do {
							int j = text.indexOf("%3");
							if (j == -1) {
								break;
							}
							text = text.substring(0, j) + getIntString(executeClientscript1(child, 2)) + text.substring(j + 2);
						} while (true);
						do {
							int j = text.indexOf("%4");
							if (j == -1) {
								break;
							}
							text = text.substring(0, j) + getIntString(executeClientscript1(child, 3)) + text.substring(j + 2);
						} while (true);
						do {
							int j = text.indexOf("%5");
							if (j == -1) {
								break;
							}
							text = text.substring(0, j) + getIntString(executeClientscript1(child, 4)) + text.substring(j + 2);
						} while (true);
					}

					int newline = text.indexOf("\\n");
					String split;
					if (newline != -1) {
						split = text.substring(0, newline);
						text = text.substring(newline + 2);
					} else {
						split = text;
						text = "";
					}

					if (child.center) {
						font.drawStringTaggableCenter(split, x + (child.width / 2), lineY, rgb, child.shadow);
					} else {
						font.drawStringTaggable(split, x, lineY, rgb, child.shadow);
					}
				}
			} else if (child.type == 5) {
				Image24 image;
				if (getComponentScriptState(child)) {
					image = child.activeImage;
				} else {
					image = child.image;
				}
				if (image != null) {
					image.draw(x, y);
				}
			} else if (child.type == 6) {
				int _centerX = Draw3D.centerX;
				int _centerY = Draw3D.centerY;

				Draw3D.centerX = x + (child.width / 2);
				Draw3D.centerY = y + (child.height / 2);

				int eyeY = (Draw3D.sin[child.modelEyePitch] * child.modelZoom) >> 16;
				int eyeZ = (Draw3D.cos[child.modelEyePitch] * child.modelZoom) >> 16;

				boolean active = getComponentScriptState(child);
				int seqId;

				if (active) {
					seqId = child.activeSeqId;
				} else {
					seqId = child.seqId;
				}

				Model model;

				if (seqId == -1) {
					model = child.getModel(-1, -1, active);
				} else {
					SeqType type = SeqType.instances[seqId];
					model = child.getModel(type.secondaryFrames[child.seqFrame], type.primaryFrames[child.seqFrame], active);
				}

				if (model != null) {
					model.drawSimple(0, child.modelYaw, 0, child.modelEyePitch, 0, eyeY, eyeZ);
				}

				Draw3D.centerX = _centerX;
				Draw3D.centerY = _centerY;
			} else if (child.type == 7) {
				BitmapFont font = child.font;
				int k4 = 0;
				for (int j5 = 0; j5 < child.height; j5++) {
					for (int i6 = 0; i6 < child.width; i6++) {
						if (child.invSlotObjId[k4] > 0) {
							ObjType type = ObjType.get(child.invSlotObjId[k4] - 1);
							String s2 = type.name;
							if (type.stackable || (child.invSlotAmount[k4] != 1)) {
								s2 = s2 + " x" + method14(child.invSlotAmount[k4]);
							}
							int i9 = x + (i6 * (115 + child.invMarginX));
							int k9 = y + (j5 * (12 + child.invMarginY));
							if (child.center) {
								font.drawStringTaggableCenter(s2, i9 + (child.width / 2), k9, child.color, child.shadow);
							} else {
								font.drawStringTaggable(s2, i9, k9, child.color, child.shadow);
							}
						}
						k4++;
					}
				}
			}
		}
		Draw2D.setBounds(left, top, right, bottom);
	}

	public void updateFlameBuffer(Image8 image) {
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
						int dstX = x + 16 + image.cropX;
						int dstY = y + 16 + image.cropY;
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
			player.spotanimY = k >> 16;
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
			int seqId = buffer.get2ULE();
			if (seqId == 65535) {
				seqId = -1;
			}
			int i2 = buffer.get1UC();
			if ((seqId == player.seqId1) && (seqId != -1)) {
				int i3 = SeqType.instances[seqId].anInt365;
				if (i3 == 1) {
					player.seqFrame1 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if (i3 == 2) {
					player.anInt1530 = 0;
				}
			} else if ((seqId == -1) || (player.seqId1 == -1) || (SeqType.instances[seqId].priority >= SeqType.instances[player.seqId1].priority)) {
				player.seqId1 = seqId;
				player.seqFrame1 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.pathRemaining;
			}
		}
		if ((i & 4) != 0) {
			player.chat = buffer.getString();
			if (player.chat.charAt(0) == '~') {
				player.chat = player.chat.substring(1);
				method77(2, player.name, player.chat);
			} else if (player == localPlayer) {
				method77(2, player.name, player.chat);
			}
			player.chatColor = 0;
			player.chatStyle = 0;
			player.chatTimer = 150;
		}
		if ((i & 0x80) != 0) {
			int i1 = buffer.get2ULE();
			int j2 = buffer.get1U();
			int j3 = buffer.get1UC();
			int k3 = buffer.position;
			if ((player.name != null) && player.visible) {
				long l3 = StringUtil.toBase37(player.name);
				boolean flag = false;
				if (j2 <= 1) {
					for (int i4 = 0; i4 < ignoreCount; i4++) {
						if (ignoreName37[i4] != l3) {
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
						String chat = ChatCompression.unpack(j3, aBuffer_834);
						chat = Censor.method497(chat, 0);
						player.chat = chat;
						player.chatColor = i1 >> 8;
						player.chatStyle = i1 & 0xff;
						player.chatTimer = 150;
						if ((j2 == 2) || (j2 == 3)) {
							method77(1, "@cr2@" + player.name, chat);
						} else if (j2 == 1) {
							method77(1, "@cr1@" + player.name, chat);
						} else {
							method77(2, player.name, chat);
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
			player.combatCycle = loopCycle + 300;
			player.health = buffer.get1UC();
			player.totalHealth = buffer.get1U();
		}
		if ((i & 0x200) != 0) {
			int l1 = buffer.get1U();
			int l2 = buffer.get1US();
			player.method447(l2, l1, loopCycle);
			player.combatCycle = loopCycle + 300;
			player.health = buffer.get1U();
			player.totalHealth = buffer.get1UC();
		}
	}

	public void method108() {
		int focusX = localPlayer.x + cameraAnticheatOffsetX;
		int focusZ = localPlayer.z + cameraAnticheatOffsetZ;

		if (((cameraFocusX - focusX) < -500) || ((cameraFocusX - focusX) > 500) || ((cameraFocusZ - focusZ) < -500) || ((cameraFocusZ - focusZ) > 500)) {
			cameraFocusX = focusX;
			cameraFocusZ = focusZ;
		}

		if (cameraFocusX != focusX) {
			cameraFocusX += (focusX - cameraFocusX) / 16;
		}

		if (cameraFocusZ != focusZ) {
			cameraFocusZ += (focusZ - cameraFocusZ) / 16;
		}

		if (super.actionKey[1] == 1) {
			cameraYawTranslate += (-24 - cameraYawTranslate) / 2;
		} else if (super.actionKey[2] == 1) {
			cameraYawTranslate += (24 - cameraYawTranslate) / 2;
		} else {
			cameraYawTranslate /= 2;
		}

		if (super.actionKey[3] == 1) {
			cameraPitchTranslate += (12 - cameraPitchTranslate) / 2;
		} else if (super.actionKey[4] == 1) {
			cameraPitchTranslate += (-12 - cameraPitchTranslate) / 2;
		} else {
			cameraPitchTranslate /= 2;
		}

		cameraYaw = (cameraYaw + (cameraYawTranslate / 2)) & 0x7ff;
		cameraPitch += cameraPitchTranslate / 2;

		if (cameraPitch < 128) {
			cameraPitch = 128;
		}
		if (cameraPitch > 383) {
			cameraPitch = 383;
		}

		int focusTileX = cameraFocusX >> 7;
		int focusTileZ = cameraFocusZ >> 7;
		int focusY = getHeightmapY(currentPlane, cameraFocusX, cameraFocusZ);
		int maxY = 0;
		if ((focusTileX > 3) && (focusTileZ > 3) && (focusTileX < 100) && (focusTileZ < 100)) {
			for (int x = focusTileX - 4; x <= (focusTileX + 4); x++) {
				for (int z = focusTileZ - 4; z <= (focusTileZ + 4); z++) {
					int plane = currentPlane;
					if ((plane < 3) && ((planeTileFlags[1][x][z] & 2) == 2)) {
						plane++;
					}
					int y = focusY - planeHeightmap[plane][x][z];

					if (y > maxY) {
						maxY = y;
					}
				}
			}
		}

		int j2 = maxY * 192;

		if (j2 > 0x17f00) {
			j2 = 0x17f00;
		}

		if (j2 < 0x8000) {
			j2 = 0x8000;
		}

		if (j2 > anInt984) {
			anInt984 += (j2 - anInt984) / 24;
			return;
		}
		if (j2 < anInt984) {
			anInt984 += (j2 - anInt984) / 80;
		}
	}

	public boolean isFriend(String name) {
		if (name == null) {
			return false;
		}
		for (int i = 0; i < friendCount; i++) {
			if (name.equalsIgnoreCase(friendName[i])) {
				return true;
			}
		}
		return name.equalsIgnoreCase(localPlayer.name);
	}

	public void method111(int i) {
		Signlink.wavevol = i;
	}

	public void method112() {
		method76();

		if (crossMode == 1) {
			imageCrosses[crossCycle / 100].draw(crossX - 8 - 4, crossY - 8 - 4);
		}

		if (crossMode == 2) {
			imageCrosses[4 + (crossCycle / 100)].draw(crossX - 8 - 4, crossY - 8 - 4);
		}

		if (viewportOverlayComponentId != -1) {
			updateParentComponentSeq(delta, viewportOverlayComponentId);
			drawParentComponent(Component.instances[viewportOverlayComponentId], 0, 0, 0);
		}

		if (viewportComponentId != -1) {
			updateParentComponentSeq(delta, viewportComponentId);
			drawParentComponent(Component.instances[viewportComponentId], 0, 0, 0);
		}

		method70();
		if (!menuVisible) {
			method82();
			method125();
		} else if (mouseArea == 0) {
			drawMenu();
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
		if (systemUpdateTimer != 0) {
			int j = systemUpdateTimer / 50;
			int l = j / 60;
			j %= 60;
			if (j < 10) {
				fontPlain12.drawString("System update in: " + l + ":0" + j, 4, 329, 0xffff00);
			} else {
				fontPlain12.drawString("System update in: " + l + ":" + j, 4, 329, 0xffff00);
			}
		}
	}

	public void addIgnore(long name37) {
		if (name37 == 0L) {
			return;
		}
		if (ignoreCount >= 100) {
			method77(0, "", "Your ignore list is full. Max of 100 hit");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(name37));
		for (int j = 0; j < ignoreCount; j++) {
			if (ignoreName37[j] == name37) {
				method77(0, "", s + " is already on your ignore list");
				return;
			}
		}
		for (int k = 0; k < friendCount; k++) {
			if (friendName37[k] == name37) {
				method77(0, "", "Please remove " + s + " from your friend list first");
				return;
			}
		}
		ignoreName37[ignoreCount++] = name37;
		redrawInvback = true;
		out.putOp(133);
		out.put8(name37);
	}

	public void method114() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1) {
				j = LOCAL_PLAYER_INDEX;
			} else {
				j = playerIndices[i];
			}
			PlayerEntity player = players[j];
			if (player != null) {
				method96(player);
			}
		}
	}

	public void method115() {
		if (sceneState == 2) {
			for (SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
				if (loc.anInt1294 > 0) {
					loc.anInt1294--;
				}
				if (loc.anInt1294 == 0) {
					if ((loc.savedLocId < 0) || SceneBuilder.isLocReady(loc.savedLocId, loc.savedKind)) {
						addLoc(loc.localZ, loc.plane, loc.savedRotation, loc.savedKind, loc.localX, loc.classId, loc.savedLocId);
						loc.unlink();
					}
				} else {
					if (loc.anInt1302 > 0) {
						loc.anInt1302--;
					}
					if ((loc.anInt1302 == 0) && (loc.localX >= 1) && (loc.localZ >= 1) && (loc.localX <= 102) && (loc.localZ <= 102) && ((loc.anInt1291 < 0) || SceneBuilder.isLocReady(loc.anInt1291, loc.anInt1293))) {
						addLoc(loc.localZ, loc.plane, loc.anInt1292, loc.anInt1293, loc.localX, loc.classId, loc.anInt1291);
						loc.anInt1302 = -1;
						if ((loc.anInt1291 == loc.savedLocId) && (loc.savedLocId == -1)) {
							loc.unlink();
						} else if ((loc.anInt1291 == loc.savedLocId) && (loc.anInt1292 == loc.savedRotation) && (loc.anInt1293 == loc.savedKind)) {
							loc.unlink();
						}
					}
				}
			}
		}
	}

	public void showContextMenu() {
		int i = fontBold12.stringWidthTaggable("Choose Option");
		for (int j = 0; j < menuSize; j++) {
			int k = fontBold12.stringWidthTaggable(menuOption[j]);
			if (k > i) {
				i = k;
			}
		}
		i += 8;
		int l = (15 * menuSize) + 21;
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
			menuVisible = true;
			mouseArea = 0;
			menuX = i1;
			menuY = l1;
			menuWidth = i;
			menuHeight = (15 * menuSize) + 22;
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
			menuVisible = true;
			mouseArea = 1;
			menuX = j1;
			menuY = i2;
			menuWidth = i;
			menuHeight = (15 * menuSize) + 22;
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
			menuVisible = true;
			mouseArea = 2;
			menuX = k1;
			menuY = j2;
			menuWidth = i;
			menuHeight = (15 * menuSize) + 22;
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
			localPlayer.method448(false, l);
			int k1 = buffer.getBits(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			return;
		}
		if (k == 2) {
			int i1 = buffer.getBits(3);
			localPlayer.method448(true, i1);
			int l1 = buffer.getBits(3);
			localPlayer.method448(true, l1);
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
			localPlayer.move(l2, k2, j1 == 1);
		}
	}

	public void disposeTitleComponents() {
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

	/**
	 * Updates the components sequences if there are any. This method requires that the initial <code>id</code> belong
	 * to a component of type <code>1</code> (PARENT).
	 *
	 * @param delta the delta.
	 * @param id    the parent component id.
	 * @return <code>true</code> if there was a sequence which updated.
	 */
	public boolean updateParentComponentSeq(int delta, int id) {
		boolean updated = false;
		Component parent = Component.instances[id];

		for (int k = 0; k < parent.children.length; k++) {
			if (parent.children[k] == -1) {
				break;
			}

			Component child = Component.instances[parent.children[k]];

			if (child.type == 1) {
				updated |= updateParentComponentSeq(delta, child.id);
			}

			if ((child.type == 6) && ((child.seqId != -1) || (child.activeSeqId != -1))) {
				boolean enabled = getComponentScriptState(child);
				int seqId;

				if (enabled) {
					seqId = child.activeSeqId;
				} else {
					seqId = child.seqId;
				}

				if (seqId != -1) {
					SeqType type = SeqType.instances[seqId];
					for (child.seqCycle += delta; child.seqCycle > type.getFrameDelay(child.seqFrame); ) {
						child.seqCycle -= type.getFrameDelay(child.seqFrame) + 1;
						child.seqFrame++;
						if (child.seqFrame >= type.frameCount) {
							child.seqFrame -= type.speed;
							if ((child.seqFrame < 0) || (child.seqFrame >= type.frameCount)) {
								child.seqFrame = 0;
							}
						}
						updated = true;
					}
				}
			}
		}
		return updated;
	}

	public int method120() {
		int j = 3;
		if (anInt861 < 310) {
			int k = anInt858 >> 7;
			int l = anInt860 >> 7;
			int i1 = localPlayer.x >> 7;
			int j1 = localPlayer.z >> 7;
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
		if ((planeTileFlags[currentPlane][localPlayer.x >> 7][localPlayer.z >> 7] & 4) != 0) {
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

	public void removeIgnore(long name37) {
		if (name37 == 0L) {
			return;
		}
		for (int j = 0; j < ignoreCount; j++) {
			if (ignoreName37[j] == name37) {
				ignoreCount--;
				redrawInvback = true;
				for (int k = j; k < ignoreCount; k++) {
					ignoreName37[k] = ignoreName37[k + 1];
				}
				out.putOp(74);
				out.put8(name37);
				return;
			}
		}
	}

	public void midivol(boolean active, int vol) {
		Signlink.midivol = vol;
		if (active) {
			Signlink.midi = "voladjust";
		}
	}

	public int executeClientscript1(Component component, int scriptId) {
		if ((component.scripts == null) || (scriptId >= component.scripts.length)) {
			return -2;
		}
		try {
			int[] script = component.scripts[scriptId];
			int acc = 0;
			int pos = 0;
			int arith = 0;

			do {
				int op = script[pos++];
				int reg = 0;
				byte nextArithmetic = 0;

				if (op == 0) {
					return acc;
				} else if (op == 1) { // load_skill_level {skill}
					reg = skillLevel[script[pos++]];
				} else if (op == 2) { // load_skill_base_level {skill}
					reg = skillBaseLevel[script[pos++]];
				} else if (op == 3) { // load_skill_exp {skill}
					reg = skillExperience[script[pos++]];
				} else if (op == 4) {// load_inv_count {component id} {obj id}
					Component c = Component.instances[script[pos++]];
					int objId = script[pos++];
					if ((objId >= 0) && (objId < ObjType.count) && (!ObjType.get(objId).members || members)) {
						for (int slot = 0; slot < c.invSlotObjId.length; slot++) {
							if (c.invSlotObjId[slot] == (objId + 1)) {
								reg += c.invSlotAmount[slot];
							}
						}
					}
				} else if (op == 5) { // load_var {id}
					reg = variables[script[pos++]];
				} else if (op == 6) { // load_next_level_xp {skill}
					reg = levelExperience[skillBaseLevel[script[pos++]] - 1];
				} else if (op == 7) {
					reg = (variables[script[pos++]] * 100) / 46875;
				} else if (op == 8) { // load_combat_level
					reg = localPlayer.combatLevel;
				} else if (op == 9) { // load_total_level
					for (int skill = 0; skill < Skill.COUNT; skill++) {
						if (Skill.ENABLED[skill]) {
							reg += skillBaseLevel[skill];
						}
					}
				} else if (op == 10) {// load_inv_contains {component id} {obj id}
					Component c = Component.instances[script[pos++]];
					int objId = script[pos++] + 1;
					if ((objId >= 0) && (objId < ObjType.count) && (!ObjType.get(objId).members || members)) {
						for (int slot = 0; slot < c.invSlotObjId.length; slot++) {
							if (c.invSlotObjId[slot] != objId) {
								continue;
							}
							reg = 999999999;
							break;
						}
					}
				} else if (op == 11) { // load_energy
					reg = energy;
				} else if (op == 12) { // load_weight
					reg = weightCarried;
				} else if (op == 13) {// load_bool {varp} {bit: 0..31}
					int varp = variables[script[pos++]];
					int bit = script[pos++];
					reg = ((varp & (1 << bit)) == 0) ? 0 : 1;
				} else if (op == 14) {// load_varbit {varbit}
					VarbitType varbit = VarbitType.instances[script[pos++]];
					int lsb = varbit.lsb;
					reg = (variables[varbit.varp] >> lsb) & BITMASK[varbit.msb - lsb];
				} else if (op == 15) { // sub
					nextArithmetic = 1;
				} else if (op == 16) { // div
					nextArithmetic = 2;
				} else if (op == 17) { // mul
					nextArithmetic = 3;
				} else if (op == 18) { // load_world_x
					reg = (localPlayer.x >> 7) + sceneBaseTileX;
				} else if (op == 19) { // load_world_z
					reg = (localPlayer.z >> 7) + sceneBaseTileZ;
				} else if (op == 20) { // load {value}
					reg = script[pos++];
				}

				if (nextArithmetic == 0) {
					if (arith == 0) {
						acc += reg;
					}
					if (arith == 1) {
						acc -= reg;
					}
					if ((arith == 2) && (reg != 0)) {
						acc /= reg;
					}
					if (arith == 3) {
						acc *= reg;
					}
					arith = 0;
				} else {
					arith = nextArithmetic;
				}
			} while (true);
		} catch (Exception _ex) {
			return -1;
		}
	}

	public void method125() {
		if ((menuSize < 2) && (anInt1282 == 0) && (anInt1136 == 0)) {
			return;
		}
		String s;
		if ((anInt1282 == 1) && (menuSize < 2)) {
			s = "Use " + aString1286 + " with...";
		} else if ((anInt1136 == 1) && (menuSize < 2)) {
			s = spellCaption + "...";
		} else {
			s = menuOption[menuSize - 1];
		}
		if (menuSize > 2) {
			s = s + "@whi@ / " + (menuSize - 2) + " more options";
		}
		fontBold12.drawStringTooltip(s, 4, 15, 0xffffff, true, loopCycle / 1000);
	}

	public void drawMinimap() {
		areaMapback.bind();

		if (minimapState == 2) {
			byte[] mapback = imageMapback.pixels;
			int[] pixels = Draw2D.pixels;
			for (int i = 0; i < mapback.length; i++) {
				if (mapback[i] == 0) {
					pixels[i] = 0;
				}
			}
			imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, cameraYaw, compassMaskLineLengths, compassMaskLineOffsets);
			areaViewport.bind();
			return;
		}

		int angle = (cameraYaw + minimapAnticheatAngle) & 0x7ff;
		int anchorX = 48 + (localPlayer.x / 32);
		int anchorY = 464 - (localPlayer.z / 32);

		imageMinimap.drawRotatedMasked(25, 5, 146, 151, anchorX, anchorY, 256 + minimapZoom, angle, minimapMaskLineLengths, minimapMaskLineOffsets);
		imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, cameraYaw, compassMaskLineLengths, compassMaskLineOffsets);

		for (int i = 0; i < activeMapFunctionCount; i++) {
			int x = ((activeMapFunctionX[i] * 4) + 2) - (localPlayer.x / 32);
			int y = ((activeMapFunctionZ[i] * 4) + 2) - (localPlayer.z / 32);
			drawOnMinimap(activeMapFunctions[i], x, y);
		}

		for (int ltx = 0; ltx < 104; ltx++) {
			for (int ltz = 0; ltz < 104; ltz++) {
				DoublyLinkedList stack = planeObjStacks[currentPlane][ltx][ltz];

				if (stack != null) {
					int x = ((ltx * 4) + 2) - (localPlayer.x / 32);
					int y = ((ltz * 4) + 2) - (localPlayer.z / 32);
					drawOnMinimap(imageMapdot0, x, y);
				}
			}
		}

		for (int i = 0; i < npcCount; i++) {
			NPCEntity npc = npcs[npcIndices[i]];
			if ((npc == null) || !npc.isVisible()) {
				continue;
			}
			NPCType type = npc.type;
			if (type.overrides != null) {
				type = type.getOverrideType();
			}
			if ((type != null) && type.aBoolean87 && type.aBoolean84) {
				int x = (npc.x / 32) - (localPlayer.x / 32);
				int y = (npc.z / 32) - (localPlayer.z / 32);
				drawOnMinimap(imageMapdot1, x, y);
			}
		}

		for (int i = 0; i < playerCount; i++) {
			PlayerEntity player = players[playerIndices[i]];

			if ((player == null) || !player.isVisible()) {
				continue;
			}

			int x = (player.x / 32) - (localPlayer.x / 32);
			int y = (player.z / 32) - (localPlayer.z / 32);
			boolean friend = false;
			long name37 = StringUtil.toBase37(player.name);

			for (int j = 0; j < friendCount; j++) {
				if ((name37 != friendName37[j]) || (friendWorld[j] == 0)) {
					continue;
				}
				friend = true;
				break;
			}

			boolean team = (player.team != 0) && (localPlayer.team == player.team);

			if (friend) {
				drawOnMinimap(imageMapdot3, x, y);
			} else if (team) {
				drawOnMinimap(imageMapdot4, x, y);
			} else {
				drawOnMinimap(imageMapdot2, x, y);
			}
		}

		if ((hintType != 0) && ((loopCycle % 20) < 10)) {
			if ((hintType == 1) && (hintNPC >= 0) && (hintNPC < npcs.length)) {
				NPCEntity npc = npcs[hintNPC];
				if (npc != null) {
					int x = (npc.x / 32) - (localPlayer.x / 32);
					int y = (npc.z / 32) - (localPlayer.z / 32);
					drawMinimapHint(imageMapmarker1, x, y);
				}
			}

			if (hintType == 2) {
				int x = (((hintTileX - sceneBaseTileX) * 4) + 2) - (localPlayer.x / 32);
				int y = (((hintTileZ - sceneBaseTileZ) * 4) + 2) - (localPlayer.z / 32);
				drawMinimapHint(imageMapmarker1, x, y);
			}

			if ((hintType == 10) && (hintPlayer >= 0) && (hintPlayer < players.length)) {
				PlayerEntity player = players[hintPlayer];
				if (player != null) {
					int x = (player.x / 32) - (localPlayer.x / 32);
					int z = (player.z / 32) - (localPlayer.z / 32);
					drawMinimapHint(imageMapmarker1, x, z);
				}
			}
		}
		if (flagSceneTileX != 0) {
			int j2 = ((flagSceneTileX * 4) + 2) - (localPlayer.x / 32);
			int l4 = ((flagSceneTileZ * 4) + 2) - (localPlayer.z / 32);
			drawOnMinimap(imageMapmarker0, j2, l4);
		}
		Draw2D.fillRect(97, 78, 3, 3, 0xffffff);
		areaViewport.bind();
	}

	/**
	 * @param entity
	 * @param height
	 * @see #projectX
	 * @see #projectY
	 */
	public void projectToScreen(PathingEntity entity, int height) {
		projectToScreen(entity.x, height, entity.z);
	}

	/**
	 * @param x
	 * @param height
	 * @param z
	 * @see #projectX
	 * @see #projectY
	 */
	public void projectToScreen(int x, int height, int z) {
		if ((x < 128) || (z < 128) || (x > 13056) || (z > 13056)) {
			projectX = -1;
			projectY = -1;
			return;
		}
		int y = getHeightmapY(currentPlane, x, z) - height;

		x -= anInt858;
		y -= anInt859;
		z -= anInt860;

		int j1 = Model.sin[anInt861];
		int k1 = Model.cos[anInt861];
		int l1 = Model.sin[anInt862];
		int i2 = Model.cos[anInt862];

		int tmp = ((z * l1) + (x * i2)) >> 16;
		z = ((z * i2) - (x * l1)) >> 16;
		x = tmp;

		tmp = ((y * k1) - (z * j1)) >> 16;
		z = ((y * j1) + (z * k1)) >> 16;
		y = tmp;

		if (z >= 50) {
			projectX = Draw3D.centerX + ((x << 9) / z);
			projectY = Draw3D.centerY + ((y << 9) / z);
		} else {
			projectX = -1;
			projectY = -1;
		}
	}

	public void handlePrivateChatInput() {
		if (splitPrivateChat == 0) {
			return;
		}

		int count = 0;

		if (systemUpdateTimer != 0) {
			count = 1;
		}

		for (int j = 0; j < 100; j++) {
			if (messageText[j] == null) {
				continue;
			}

			int type = messageType[j];
			String sender = messageSender[j];

			if ((sender != null) && sender.startsWith("@cr1@")) {
				sender = sender.substring(5);
			}

			if ((sender != null) && sender.startsWith("@cr2@")) {
				sender = sender.substring(5);
			}

			if (((type == 3) || (type == 7)) && ((type == 7) || (privateChatSetting == 0) || ((privateChatSetting == 1) && isFriend(sender)))) {
				int y = 329 - (count * 13);

				if ((super.mouseX > 4) && ((super.mouseY - 4) > (y - 10)) && ((super.mouseY - 4) <= (y + 3))) {
					int w = fontPlain12.stringWidthTaggable("From:  " + sender + messageText[j]) + 25;

					if (w > 450) {
						w = 450;
					}

					if (super.mouseX < (4 + w)) {
						if (rights >= 1) {
							menuOption[menuSize] = "Report abuse @whi@" + sender;
							menuAction[menuSize] = 2606;
							menuSize++;
						}
						menuOption[menuSize] = "Add ignore @whi@" + sender;
						menuAction[menuSize] = 2042;
						menuSize++;
						menuOption[menuSize] = "Add friend @whi@" + sender;
						menuAction[menuSize] = 2337;
						menuSize++;
					}
				}
				if (++count >= 5) {
					return;
				}
			}

			if (((type == 5) || (type == 6)) && (privateChatSetting < 2) && (++count >= 5)) {
				return;
			}
		}
	}

	public void method130(int j, int k, int l, int classId, int z, int k1, int plane, int x, int j2) {
		SceneLocTemporary loc = null;
		for (SceneLocTemporary other = (SceneLocTemporary) temporaryLocs.peekFront(); other != null; other = (SceneLocTemporary) temporaryLocs.prev()) {
			if ((other.plane != plane) || (other.localX != x) || (other.localZ != z) || (other.classId != classId)) {
				continue;
			}
			loc = other;
			break;
		}
		if (loc == null) {
			loc = new SceneLocTemporary();
			loc.plane = plane;
			loc.classId = classId;
			loc.localX = x;
			loc.localZ = z;
			storeLoc(loc);
			temporaryLocs.pushBack(loc);
		}
		loc.anInt1291 = k;
		loc.anInt1293 = k1;
		loc.anInt1292 = l;
		loc.anInt1302 = j2;
		loc.anInt1294 = j;
	}

	/**
	 * Executes ClientScript 1 and returns a state value.
	 *
	 * @param component the component.
	 * @return the state.
	 */
	public boolean getComponentScriptState(Component component) {
		if (component.scriptComparator == null) {
			return false;
		}
		for (int scriptId = 0; scriptId < component.scriptComparator.length; scriptId++) {
			int value = executeClientscript1(component, scriptId);
			int operand = component.scriptOperand[scriptId];

			if (component.scriptComparator[scriptId] == 2) {
				if (value >= operand) {
					return false;
				}
			} else if (component.scriptComparator[scriptId] == 3) {
				if (value <= operand) {
					return false;
				}
			} else if (component.scriptComparator[scriptId] == 4) {
				if (value == operand) {
					return false;
				}
			} else if (value != operand) {
				return false;
			}
		}
		return true;
	}

	public DataInputStream openURL(String s) throws IOException {
		if (!jaggrabEnabled) {
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
		aSocket832 = openSocket(43595);
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
		if (j < playerCount) {
			for (int k = j; k < playerCount; k++) {
				anIntArray840[anInt839++] = playerIndices[k];
			}
		}
		if (j > playerCount) {
			Signlink.reporterror(username + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for (int l = 0; l < j; l++) {
			int i1 = playerIndices[l];
			PlayerEntity player = players[i1];
			int j1 = buffer.getBits(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = buffer.getBits(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = buffer.getBits(3);
					player.method448(false, l1);
					int j2 = buffer.getBits(1);
					if (j2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
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

	public void drawTitleScreen(boolean hideButtons) throws IOException {
		prepareTitleScreen();
		imageTitle4.bind();
		imageTitlebox.blit(0, 0);
		int w = 360;
		int h = 200;

		if (titleScreenState == 0) {
			int y = (h / 2) + 80;
			fontPlain11.drawStringTaggableCenter(ondemand.message, w / 2, y, 0x75a9a9, true);

			y = (h / 2) - 20;
			fontBold12.drawStringTaggableCenter("Welcome to RuneScape", w / 2, y, 0xffff00, true);

			int x = (w / 2) - 80;
			y = (h / 2) + 20;
			imageTitlebutton.blit(x - 73, y - 20);
			fontBold12.drawStringTaggableCenter("New User", x, y + 5, 0xffffff, true);

			x = (w / 2) + 80;
			imageTitlebutton.blit(x - 73, y - 20);
			fontBold12.drawStringTaggableCenter("Existing User", x, y + 5, 0xffffff, true);
		}

		if (titleScreenState == 2) {
			int y = (h / 2) - 40;

			if (loginMessage0.length() > 0) {
				fontBold12.drawStringTaggableCenter(loginMessage0, w / 2, y - 15, 0xffff00, true);
				fontBold12.drawStringTaggableCenter(loginMessage1, w / 2, y, 0xffff00, true);
			} else {
				fontBold12.drawStringTaggableCenter(loginMessage1, w / 2, y - 7, 0xffff00, true);
			}
			y += 30;

			fontBold12.drawStringTaggable("Username: " + username + ((titleLoginField == 0) & (loopCycle % 40 < 20) ? "@yel@|" : ""), (w / 2) - 90, y, 0xffffff, true);
			y += 15;

			fontBold12.drawStringTaggable("Password: " + StringUtil.toAsterisks(password) + ((titleLoginField == 1) & (loopCycle % 40 < 20) ? "@yel@|" : ""), (w / 2) - 88, y, 0xffffff, true);

			if (!hideButtons) {
				int x = (w / 2) - 80;
				y = (h / 2) + 50;
				imageTitlebutton.blit(x - 73, y - 20);
				fontBold12.drawStringTaggableCenter("Login", x, y + 5, 0xffffff, true);

				x = (w / 2) + 80;
				imageTitlebutton.blit(x - 73, y - 20);
				fontBold12.drawStringTaggableCenter("Cancel", x, y + 5, 0xffffff, true);
			}
		}

		if (titleScreenState == 3) {
			fontBold12.drawStringTaggableCenter("Create a free account", w / 2, (h / 2) - 60, 0xffff00, true);

			int y = (h / 2) - 35;
			fontBold12.drawStringTaggableCenter("To create a new account you need to", w / 2, y, 0xffffff, true);
			y += 15;

			fontBold12.drawStringTaggableCenter("go back to the main RuneScape webpage", w / 2, y, 0xffffff, true);
			y += 15;

			fontBold12.drawStringTaggableCenter("and choose the red 'create account'", w / 2, y, 0xffffff, true);
			y += 15;

			fontBold12.drawStringTaggableCenter("button at the top right of that page.", w / 2, y, 0xffffff, true);

			int x = w / 2;
			y = (h / 2) + 50;
			imageTitlebutton.blit(x - 73, y - 20);
			fontBold12.drawStringTaggableCenter("Cancel", x, y + 5, 0xffffff, true);
		}

		imageTitle4.draw(super.graphics, 202, 171);

		if (redrawTitleBackground) {
			redrawTitleBackground = false;
			imageTitle2.draw(super.graphics, 128, 0);
			imageTitle3.draw(super.graphics, 202, 371);
			imageTitle5.draw(super.graphics, 0, 265);
			imageTitle6.draw(super.graphics, 562, 265);
			imageTitle7.draw(super.graphics, 128, 171);
			imageTitle8.draw(super.graphics, 562, 171);
		}
	}

	public void runFlames() {
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

	public void readZonePacket(Buffer buffer, int opcode) {
		// update obj
		if (opcode == 84) {
			int pos = buffer.get1U();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int objId = buffer.get2U();
			int objAmount = buffer.get2U();
			int newAmount = buffer.get2U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				DoublyLinkedList stacks = planeObjStacks[currentPlane][x][z];
				if (stacks != null) {
					for (ObjStackEntity stack = (ObjStackEntity) stacks.peekFront(); stack != null; stack = (ObjStackEntity) stacks.prev()) {
						if ((stack.id != (objId & 0x7fff)) || (stack.amount != objAmount)) {
							continue;
						}
						stack.amount = newAmount;
						break;
					}
					sortObjStacks(x, z);
				}
			}
			return;
		}

		// emit sound
		if (opcode == 105) {
			int pos = buffer.get1U();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int waveId = buffer.get2U();
			int info = buffer.get1U();
			int maxDist = (info >> 4) & 0xf;
			int loopCount = info & 0b111;
			if ((localPlayer.pathTileX[0] >= (x - maxDist)) && (localPlayer.pathTileX[0] <= (x + maxDist)) && (localPlayer.pathTileZ[0] >= (z - maxDist)) && (localPlayer.pathTileZ[0] <= (z + maxDist)) && aBoolean848 && !lowmem && (waveCount < 50)) {
				waveIds[waveCount] = waveId;
				waveLoops[waveCount] = loopCount;
				waveDelay[waveCount] = SoundTrack.delays[waveId];
				waveCount++;
			}
		}

		// reveal obj (this would already be visible to the local player if it belongs to them)
		if (opcode == 215) {
			int objId = buffer.get2UA();
			int pos = buffer.get1US();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int ownerId = buffer.get2UA();
			int objAmount = buffer.get2U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104) && (ownerId != localPlayerId)) {
				ObjStackEntity obj = new ObjStackEntity();
				obj.id = objId;
				obj.amount = objAmount;
				if (planeObjStacks[currentPlane][x][z] == null) {
					planeObjStacks[currentPlane][x][z] = new DoublyLinkedList();
				}
				planeObjStacks[currentPlane][x][z].pushBack(obj);
				sortObjStacks(x, z);
			}
			return;
		}

		// remove obj
		if (opcode == 156) {
			int pos = buffer.get1UA();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int objId = buffer.get2U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				DoublyLinkedList list = planeObjStacks[currentPlane][x][z];
				if (list != null) {
					for (ObjStackEntity obj = (ObjStackEntity) list.peekFront(); obj != null; obj = (ObjStackEntity) list.prev()) {
						if (obj.id != (objId & 0x7fff)) {
							continue;
						}
						obj.unlink();
						break;
					}
					if (list.peekFront() == null) {
						planeObjStacks[currentPlane][x][z] = null;
					}
					sortObjStacks(x, z);
				}
			}
			return;
		}

		// update loc
		if (opcode == 160) {
			int pos = buffer.get1US();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int info = buffer.get1US();
			int kind = info >> 2;
			int rotation = info & 3;
			int classId = LOC_KIND_TO_CLASS_ID[kind];
			int seqId = buffer.get2UA();

			if ((x < 0) || (z < 0) || (x >= 103) || (z >= 103)) {
				return;
			}

			int heightmapSW = planeHeightmap[currentPlane][x][z];
			int heightmapSE = planeHeightmap[currentPlane][x + 1][z];
			int heightmapNE = planeHeightmap[currentPlane][x + 1][z + 1];
			int heightmapNW = planeHeightmap[currentPlane][x][z + 1];

			if (classId == 0) {
				SceneWall wall = scene.getWall(currentPlane, x, z);

				if (wall != null) {
					int locId = (wall.bitset >> 14) & 0x7fff;

					if (kind == 2) {
						wall.entityA = new LocEntity(locId, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
						wall.entityB = new LocEntity(locId, (rotation + 1) & 3, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
					} else {
						wall.entityA = new LocEntity(locId, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
					}
				}
			}

			if (classId == 1) {
				SceneWallDecoration deco = scene.getWallDecoration(currentPlane, x, z);

				if (deco != null) {
					deco.entity = new LocEntity((deco.bitset >> 14) & 0x7fff, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
				}
			}

			if (classId == 2) {
				SceneLoc loc = scene.getLoc(currentPlane, x, z);

				if (kind == 11) {
					kind = 10;
				}

				if (loc != null) {
					loc.entity = new LocEntity((loc.bitset >> 14) & 0x7fff, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
				}
			}

			if (classId == 3) {
				SceneGroundDecoration deco = scene.getGroundDecoration(z, x, currentPlane);

				if (deco != null) {
					deco.entity = new LocEntity((deco.bitset >> 14) & 0x7fff, rotation, 22, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqId, false);
				}
			}
			return;
		}

		if (opcode == 147) {
			int pos = buffer.get1US();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int pid = buffer.get2U();
			byte maxX = buffer.get1S();
			int startCycle = buffer.get2ULE();
			byte maxZ = buffer.get1C();
			int stopCycle = buffer.get2U();
			int info = buffer.get1US();
			int kind = info >> 2;
			int rotation = info & 3;
			int classId = LOC_KIND_TO_CLASS_ID[kind];
			byte minX = buffer.get1();
			int locId = buffer.get2U();
			byte minZ = buffer.get1C();
			PlayerEntity player;

			if (pid == localPlayerId) {
				player = localPlayer;
			} else {
				player = players[pid];
			}

			if (player != null) {
				LocType type = LocType.get(locId);
				int heightmapSW = planeHeightmap[currentPlane][x][z];
				int heightmapSE = planeHeightmap[currentPlane][x + 1][z];
				int heightmapNE = planeHeightmap[currentPlane][x + 1][z + 1];
				int heightmapNW = planeHeightmap[currentPlane][x][z + 1];

				Model model = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);

				if (model != null) {
					method130(stopCycle + 1, -1, 0, classId, z, 0, currentPlane, x, startCycle + 1);

					player.locStartCycle = startCycle + loopCycle;
					player.locStopCycle = stopCycle + loopCycle;
					player.locModel = model;
					int sizeX = type.width;
					int sizeZ = type.length;

					if ((rotation == 1) || (rotation == 3)) {
						sizeX = type.length;
						sizeZ = type.width;
					}

					player.locOffsetX = (x * 128) + (sizeX * 64);
					player.locOffsetZ = (z * 128) + (sizeZ * 64);
					player.locOffsetY = getHeightmapY(currentPlane, player.locOffsetX, player.locOffsetZ);

					if (minX > maxX) {
						byte tmp = minX;
						minX = maxX;
						maxX = tmp;
					}

					if (minZ > maxZ) {
						byte tmp = minZ;
						minZ = maxZ;
						maxZ = tmp;
					}

					player.minSceneTileX = x + minX;
					player.maxSceneTileX = x + maxX;
					player.minSceneTileZ = z + minZ;
					player.maxSceneTileZ = z + maxZ;
				}
			}
		}

		if (opcode == 151) {
			int i2 = buffer.get1UA();
			int l4 = zoneX + ((i2 >> 4) & 7);
			int k7 = zoneZ + (i2 & 7);
			int j10 = buffer.get2ULE();
			int k12 = buffer.get1US();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = LOC_KIND_TO_CLASS_ID[i15];
			if ((l4 >= 0) && (k7 >= 0) && (l4 < 104) && (k7 < 104)) {
				method130(-1, j10, k16, l17, k7, i15, currentPlane, l4, 0);
			}
			return;
		}

		if (opcode == 4) {
			int pos = buffer.get1U();
			int x = zoneX + ((pos >> 4) & 7);
			int z = zoneZ + (pos & 7);
			int id = buffer.get2U();
			int y = buffer.get1U();
			int delay = buffer.get2U();

			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				x = (x * 128) + 64;
				z = (z * 128) + 64;
				SpotAnimEntity spotAnim = new SpotAnimEntity(currentPlane, loopCycle, delay, id, getHeightmapY(currentPlane, x, z) - y, z, x);
				spotanims.pushBack(spotAnim);
			}
			return;
		}

		if (opcode == 44) {
			int k2 = buffer.get2ULEA();
			int j5 = buffer.get2U();
			int i8 = buffer.get1U();
			int l10 = zoneX + ((i8 >> 4) & 7);
			int i13 = zoneZ + (i8 & 7);
			if ((l10 >= 0) && (i13 >= 0) && (l10 < 104) && (i13 < 104)) {
				ObjStackEntity objStack_1 = new ObjStackEntity();
				objStack_1.id = k2;
				objStack_1.amount = j5;
				if (planeObjStacks[currentPlane][l10][i13] == null) {
					planeObjStacks[currentPlane][l10][i13] = new DoublyLinkedList();
				}
				planeObjStacks[currentPlane][l10][i13].pushBack(objStack_1);
				sortObjStacks(l10, i13);
			}
			return;
		}

		if (opcode == 101) {
			int l2 = buffer.get1UC();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = LOC_KIND_TO_CLASS_ID[k5];
			int j13 = buffer.get1U();
			int k15 = zoneX + ((j13 >> 4) & 7);
			int l16 = zoneZ + (j13 & 7);
			if ((k15 >= 0) && (l16 >= 0) && (k15 < 104) && (l16 < 104)) {
				method130(-1, -1, j8, i11, l16, k5, currentPlane, k15, 0);
			}
			return;
		}

		if (opcode == 117) {
			int i3 = buffer.get1U();
			int l5 = zoneX + ((i3 >> 4) & 7);
			int k8 = zoneZ + (i3 & 7);
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
		if (k < npcCount) {
			for (int l = k; l < npcCount; l++) {
				anIntArray840[anInt839++] = npcIndices[l];
			}
		}
		if (k > npcCount) {
			Signlink.reporterror(username + " Too many npcs");
			throw new RuntimeException("eek");
		}
		npcCount = 0;
		for (int i1 = 0; i1 < k; i1++) {
			int j1 = npcIndices[i1];
			NPCEntity npc = npcs[j1];
			int k1 = buffer.getBits(1);
			if (k1 == 0) {
				npcIndices[npcCount++] = j1;
				npc.anInt1537 = loopCycle;
			} else {
				int l1 = buffer.getBits(2);
				if (l1 == 0) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int i2 = buffer.getBits(3);
					npc.method448(false, i2);
					int k2 = buffer.getBits(1);
					if (k2 == 1) {
						anIntArray894[anInt893++] = j1;
					}
				} else if (l1 == 2) {
					npcIndices[npcCount++] = j1;
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

	public void updateTitle() {
		if (titleScreenState == 0) {
			int x = (super.screenWidth / 2) - 80;
			int y = (super.screenHeight / 2) + 20;
			y += 20;

			if ((super.mousePressButton == 1) && (super.mousePressX >= (x - 75)) && (super.mousePressX <= (x + 75)) && (super.mousePressY >= (y - 20)) && (super.mousePressY <= (y + 20))) {
				titleScreenState = 3;
				titleLoginField = 0;
			}

			x = (super.screenWidth / 2) + 80;

			if ((super.mousePressButton == 1) && (super.mousePressX >= (x - 75)) && (super.mousePressX <= (x + 75)) && (super.mousePressY >= (y - 20)) && (super.mousePressY <= (y + 20))) {
				loginMessage0 = "";
				loginMessage1 = "Enter your username & password.";
				titleScreenState = 2;
				titleLoginField = 0;
			}
		} else {
			if (titleScreenState == 2) {
				int fieldY = (super.screenHeight / 2) - 40;
				fieldY += 30;
				fieldY += 25;
				if ((super.mousePressButton == 1) && (super.mousePressY >= (fieldY - 15)) && (super.mousePressY < fieldY)) {
					titleLoginField = 0;
				}
				fieldY += 15;
				if ((super.mousePressButton == 1) && (super.mousePressY >= (fieldY - 15)) && (super.mousePressY < fieldY)) {
					titleLoginField = 1;
				}

				int buttonX = (super.screenWidth / 2) - 80;
				int buttonY = (super.screenHeight / 2) + 50;
				buttonY += 20;

				if ((super.mousePressButton == 1) && (super.mousePressX >= (buttonX - 75)) && (super.mousePressX <= (buttonX + 75)) && (super.mousePressY >= (buttonY - 20)) && (super.mousePressY <= (buttonY + 20))) {
					anInt1038 = 0;
					login(username, password, false);

					if (ingame) {
						return;
					}
				}

				buttonX = (super.screenWidth / 2) + 80;

				if ((super.mousePressButton == 1) && (super.mousePressX >= (buttonX - 75)) && (super.mousePressX <= (buttonX + 75)) && (super.mousePressY >= (buttonY - 20)) && (super.mousePressY <= (buttonY + 20))) {
					titleScreenState = 0;
					username = "";
					password = "";
				}

				do {
					int key = pollKey();

					if (key == -1) {
						break;
					}

					boolean valid = false;
					for (int i = 0; i < VALID_CHAT_CHARACTERS.length(); i++) {
						if (key != VALID_CHAT_CHARACTERS.charAt(i)) {
							continue;
						}
						valid = true;
						break;
					}

					if (titleLoginField == 0) {
						if ((key == 8) && (username.length() > 0)) {
							username = username.substring(0, username.length() - 1);
						}
						if ((key == 9) || (key == 10) || (key == 13)) {
							titleLoginField = 1;
						}
						if (valid) {
							username += (char) key;
						}
						if (username.length() > 12) {
							username = username.substring(0, 12);
						}
					} else if (titleLoginField == 1) {
						if ((key == 8) && (password.length() > 0)) {
							password = password.substring(0, password.length() - 1);
						}
						if ((key == 9) || (key == 10) || (key == 13)) {
							titleLoginField = 0;
						}
						if (valid) {
							password += (char) key;
						}
						if (password.length() > 20) {
							password = password.substring(0, 20);
						}
					}
				} while (true);
				return;
			}

			if (titleScreenState == 3) {
				int x = super.screenWidth / 2;
				int y = (super.screenHeight / 2) + 50;
				y += 20;
				if ((super.mousePressButton == 1) && (super.mousePressX >= (x - 75)) && (super.mousePressX <= (x + 75)) && (super.mousePressY >= (y - 20)) && (super.mousePressY <= (y + 20))) {
					titleScreenState = 0;
				}
			}
		}
	}

	public void drawOnMinimap(Image24 image, int dx, int dy) {
		int angle = (cameraYaw + minimapAnticheatAngle) & 0x7ff;
		int distance = (dx * dx) + (dy * dy);

		if (distance > 6400) {
			return;
		}

		int sinAngle = Model.sin[angle];
		int cosAngle = Model.cos[angle];

		sinAngle = (sinAngle * 256) / (minimapZoom + 256);
		cosAngle = (cosAngle * 256) / (minimapZoom + 256);

		int x = ((dy * sinAngle) + (dx * cosAngle)) >> 16;
		int y = ((dy * cosAngle) - (dx * sinAngle)) >> 16;

		if (distance > 2500) {
			image.drawMasked(imageMapback, 83 - y - (image.cropH / 2) - 4, ((94 + x) - (image.cropW / 2)) + 4);
		} else {
			image.draw(((94 + x) - (image.cropW / 2)) + 4, 83 - y - (image.cropH / 2) - 4);
		}
	}

	public void addLoc(int z, int plane, int angle, int kind, int x, int classID, int id) {
		if ((x < 1) || (z < 1) || (x > 102) || (z > 102)) {
			return;
		}

		if (lowmem && (plane != currentPlane)) {
			return;
		}

		int bitset = 0;

		if (classID == 0) {
			bitset = scene.getWallBitset(plane, x, z);
		}

		if (classID == 1) {
			bitset = scene.getWallDecorationBitset(plane, x, z);
		}

		if (classID == 2) {
			bitset = scene.getLocBitset(plane, x, z);
		}

		if (classID == 3) {
			bitset = scene.getGroundDecorationBitset(plane, x, z);
		}

		if (bitset != 0) {
			int info = scene.getInfo(plane, x, z, bitset);
			int otherId = (bitset >> 14) & 0x7fff;
			int otherKind = info & 0x1f;
			int otherRotation = info >> 6;

			if (classID == 0) {
				scene.removeWall(x, plane, z);
				LocType type = LocType.get(otherId);
				if (type.solid) {
					collisions[plane].remove(otherRotation, otherKind, type.blocksProjectiles, x, z);
				}
			}

			if (classID == 1) {
				scene.removeWallDecoration(plane, x, z);
			}

			if (classID == 2) {
				scene.removeLoc(plane, x, z);
				LocType type = LocType.get(otherId);

				if (((x + type.width) > 103) || ((z + type.width) > 103) || ((x + type.length) > 103) || ((z + type.length) > 103)) {
					return;
				}

				if (type.solid) {
					collisions[plane].remove(otherRotation, type.width, x, z, type.length, type.blocksProjectiles);
				}
			}

			if (classID == 3) {
				scene.removeGroundDecoration(plane, x, z);
				LocType type_2 = LocType.get(otherId);
				if (type_2.solid && type_2.interactable) {
					collisions[plane].method218(z, x);
				}
			}
		}

		if (id >= 0) {
			// this would be the plane the loc gets its heightmap data from
			int truePlane = plane;

			// check for bridged tile
			if ((truePlane < 3) && ((planeTileFlags[1][x][z] & 2) == 2)) {
				truePlane++;
			}

			SceneBuilder.addLoc(scene, angle, z, kind, truePlane, collisions[plane], planeHeightmap, x, id, plane);
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
		for (int i1 = 0; i1 < playerCount; i1++) {
			if (players[playerIndices[i1]] == null) {
				Signlink.reporterror(username + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
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

	public boolean read() {
		if (connection == null) {
			return false;
		}
		try {
			int available = connection.available();

			if (available == 0) {
				return false;
			}

			if (ptype == -1) {
				connection.read(in.data, 0, 1);
				ptype = in.data[0] & 0xff;
				if (randomIn != null) {
					ptype = (ptype - randomIn.nextInt()) & 0xff;
				}
				psize = PacketConstants.SIZE[ptype];
				available--;
			}

			if (psize == -1) {
				if (available > 0) {
					connection.read(in.data, 0, 1);
					psize = in.data[0] & 0xff;
					available--;
				} else {
					return false;
				}
			}

			if (psize == -2) {
				if (available > 1) {
					connection.read(in.data, 0, 2);
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
			connection.read(in.data, 0, psize);
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
				if ((anInt1193 != 0) && (viewportComponentId == -1)) {
					Signlink.dnslookup(StringUtil.formatIPv4(anInt1193));
					method147();
					char c = '\u028A';
					if ((anInt1167 != 201) || (anInt1120 == 1)) {
						c = '\u028F';
					}
					aString881 = "";
					aBoolean1158 = false;
					for (int k9 = 0; k9 < Component.instances.length; k9++) {
						if ((Component.instances[k9] == null) || (Component.instances[k9].contentType != c)) {
							continue;
						}
						viewportComponentId = Component.instances[k9].parentId;
						break;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 64) {
				zoneX = in.get1UC();
				zoneZ = in.get1US();
				for (int j = zoneX; j < (zoneX + 8); j++) {
					for (int l9 = zoneZ; l9 < (zoneZ + 8); l9++) {
						if (planeObjStacks[currentPlane][j][l9] != null) {
							planeObjStacks[currentPlane][j][l9] = null;
							sortObjStacks(j, l9);
						}
					}
				}
				for (SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
					if ((loc.localX >= zoneX) && (loc.localX < (zoneX + 8)) && (loc.localZ >= zoneZ) && (loc.localZ < (zoneZ + 8)) && (loc.plane == currentPlane)) {
						loc.anInt1294 = 0;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 185) {
				int k = in.get2ULEA();
				Component.instances[k].modelCategory = 3;
				if (localPlayer.npcType == null) {
					Component.instances[k].modelId = (localPlayer.colors[0] << 25) + (localPlayer.colors[4] << 20) + (localPlayer.appearances[0] << 15) + (localPlayer.appearances[8] << 10) + (localPlayer.appearances[11] << 5) + localPlayer.appearances[1];
				} else {
					Component.instances[k].modelId = (int) (0x12345678L + localPlayer.npcType.uid);
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
				for (int k15 = 0; k15 < component.invSlotObjId.length; k15++) {
					component.invSlotObjId[k15] = -1;
					component.invSlotObjId[k15] = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 214) {
				ignoreCount = psize / 8;
				for (int j1 = 0; j1 < ignoreCount; j1++) {
					ignoreName37[j1] = in.get8();
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
				redrawInvback = true;
				int skill = in.get1U();
				int exp = in.get4RME();
				int level = in.get1U();
				skillExperience[skill] = exp;
				skillLevel[skill] = level;
				skillBaseLevel[skill] = 1;
				for (int lv = 0; lv < 98; lv++) {
					if (exp >= levelExperience[lv]) {
						skillBaseLevel[skill] = lv + 2;
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
				tabComponentId[j10] = l1;
				redrawInvback = true;
				redrawSideicons = true;
				ptype = -1;
				return true;
			}
			if (ptype == 74) {
				int next = in.get2ULE();
				if (next == 65535) {
					next = -1;
				}
				if ((next != nextMusic) && midiActive && !lowmem && (nextMusicDelay == 0)) {
					music = next;
					musicFading = true;
					ondemand.request(2, music);
				}
				nextMusic = next;
				ptype = -1;
				return true;
			}
			if (ptype == 121) {
				int next = in.get2ULEA();
				int delay = in.get2UA();
				if (midiActive && !lowmem) {
					music = next;
					musicFading = false;
					ondemand.request(2, music);
					nextMusicDelay = delay;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 109) {
				disconnect();
				ptype = -1;
				return false;
			}

			if (ptype == 70) {
				int k2 = in.get2();
				int l10 = in.get2LE();
				int i16 = in.get2ULE();
				Component component_5 = Component.instances[i16];
				component_5.x = k2;
				component_5.y = l10;
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
								planeObjStacks[plane][x][z] = planeObjStacks[plane][dstX][dstZ];
							} else {
								planeObjStacks[plane][x][z] = null;
							}
						}
					}
				}

				for (SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
					loc.localX -= dtx;
					loc.localZ -= dtz;
					if ((loc.localX < 0) || (loc.localZ < 0) || (loc.localX >= 104) || (loc.localZ >= 104)) {
						loc.unlink();
					}
				}

				if (flagSceneTileX != 0) {
					flagSceneTileX -= dtx;
					flagSceneTileZ -= dtz;
				}

				aBoolean1160 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 208) {
				int componentId = in.get2LE();
				if (componentId >= 0) {
					resetParentComponentSeq(componentId);
				}
				viewportOverlayComponentId = componentId;
				ptype = -1;
				return true;
			}
			if (ptype == 99) {
				minimapState = in.get1U();
				ptype = -1;
				return true;
			}
			if (ptype == 75) {
				int npcId = in.get2ULEA();
				int componentId = in.get2ULEA();
				Component.instances[componentId].modelCategory = 2;
				Component.instances[componentId].modelId = npcId;
				ptype = -1;
				return true;
			}
			if (ptype == 114) {
				systemUpdateTimer = in.get2ULE() * 30;
				ptype = -1;
				return true;
			}
			if (ptype == 60) {
				zoneZ = in.get1U();
				zoneX = in.get1UC();
				while (in.position < psize) {
					int opcode = in.get1U();
					readZonePacket(in, opcode);
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
				if (aBoolean848 && !lowmem && (waveCount < 50)) {
					waveIds[waveCount] = i4;
					waveLoops[waveCount] = l11;
					waveDelay[waveCount] = k17 + SoundTrack.delays[i4];
					waveCount++;
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
				flagSceneTileX = 0;
				ptype = -1;
				return true;
			}
			if (ptype == 253) {
				String s = in.getString();
				if (s.endsWith(":tradereq:")) {
					String s3 = s.substring(0, s.indexOf(":"));
					long l17 = StringUtil.toBase37(s3);
					boolean flag2 = false;
					for (int j27 = 0; j27 < ignoreCount; j27++) {
						if (ignoreName37[j27] != l17) {
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
					for (int k27 = 0; k27 < ignoreCount; k27++) {
						if (ignoreName37[k27] != l18) {
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
					for (int l27 = 0; l27 < ignoreCount; l27++) {
						if (ignoreName37[l27] != l19) {
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
						player.seqId1 = -1;
					}
				}
				for (NPCEntity npc : npcs) {
					if (npc != null) {
						npc.seqId1 = -1;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 50) {
				long l4 = in.get8();
				int i18 = in.get1U();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(l4));
				for (int k24 = 0; k24 < friendCount; k24++) {
					if (l4 != friendName37[k24]) {
						continue;
					}
					if (friendWorld[k24] != i18) {
						friendWorld[k24] = i18;
						redrawInvback = true;
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
				if ((s7 != null) && (friendCount < 200)) {
					friendName37[friendCount] = l4;
					friendName[friendCount] = s7;
					friendWorld[friendCount] = i18;
					friendCount++;
					redrawInvback = true;
				}
				for (boolean flag6 = false; !flag6; ) {
					flag6 = true;
					for (int k29 = 0; k29 < (friendCount - 1); k29++) {
						if (((friendWorld[k29] != nodeId) && (friendWorld[k29 + 1] == nodeId)) || ((friendWorld[k29] == 0) && (friendWorld[k29 + 1] != 0))) {
							int j31 = friendWorld[k29];
							friendWorld[k29] = friendWorld[k29 + 1];
							friendWorld[k29 + 1] = j31;
							String s10 = friendName[k29];
							friendName[k29] = friendName[k29 + 1];
							friendName[k29 + 1] = s10;
							long l32 = friendName37[k29];
							friendName37[k29] = friendName37[k29 + 1];
							friendName37[k29 + 1] = l32;
							redrawInvback = true;
							flag6 = false;
						}
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 110) {
				if (selectedTab == 12) {
					redrawInvback = true;
				}
				energy = in.get1U();
				ptype = -1;
				return true;
			}
			if (ptype == 254) {
				hintType = in.get1U();
				if (hintType == 1) {
					hintNPC = in.get2U();
				}
				if ((hintType >= 2) && (hintType <= 6)) {
					if (hintType == 2) {
						hintOffsetX = 64;
						hintOffsetZ = 64;
					}
					if (hintType == 3) {
						hintOffsetX = 0;
						hintOffsetZ = 64;
					}
					if (hintType == 4) {
						hintOffsetX = 128;
						hintOffsetZ = 64;
					}
					if (hintType == 5) {
						hintOffsetX = 64;
						hintOffsetZ = 0;
					}
					if (hintType == 6) {
						hintOffsetX = 64;
						hintOffsetZ = 128;
					}
					hintType = 2;
					hintTileX = in.get2U();
					hintTileZ = in.get2U();
					hintHeight = in.get1U();
				}
				if (hintType == 10) {
					hintPlayer = in.get2U();
				}
				ptype = -1;
				return true;
			}
			if (ptype == 248) {
				int i5 = in.get2UA();
				int k12 = in.get2U();
				if (chatbackComponentId != -1) {
					chatbackComponentId = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentId = i5;
				invbackComponentId = k12;
				redrawInvback = true;
				redrawSideicons = true;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 79) {
				int j5 = in.get2ULE();
				int l12 = in.get2UA();
				Component component_3 = Component.instances[j5];
				if ((component_3 != null) && (component_3.type == 0)) {
					if (l12 < 0) {
						l12 = 0;
					}
					if (l12 > (component_3.scrollHeight - component_3.height)) {
						l12 = component_3.scrollHeight - component_3.height;
					}
					component_3.scrollY = l12;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 68) {
				for (int k5 = 0; k5 < variables.length; k5++) {
					if (variables[k5] != anIntArray1045[k5]) {
						variables[k5] = anIntArray1045[k5];
						updateVarp(k5);
						redrawInvback = true;
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
					for (int l29 = 0; l29 < ignoreCount; l29++) {
						if (ignoreName37[l29] != l5) {
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
				zoneZ = in.get1UC();
				zoneX = in.get1UC();
				ptype = -1;
				return true;
			}
			if (ptype == 24) {
				anInt1054 = in.get1US();
				if (anInt1054 == selectedTab) {
					if (anInt1054 == 3) {
						selectedTab = 1;
					} else {
						selectedTab = 3;
					}
					redrawInvback = true;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 246) {
				int componentId = in.get2ULE();
				int zoom = in.get2U();
				int objId = in.get2U();
				if (objId == 65535) {
					Component.instances[componentId].modelCategory = 0;
				} else {
					ObjType type = ObjType.get(objId);
					Component.instances[componentId].modelCategory = 4;
					Component.instances[componentId].modelId = objId;
					Component.instances[componentId].modelEyePitch = type.iconPitch;
					Component.instances[componentId].modelYaw = type.iconYaw;
					Component.instances[componentId].modelZoom = (type.iconZoom * 100) / zoom;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 171) {
				boolean hidden = in.get1U() == 1;
				int parentId = in.get2U();
				Component.instances[parentId].hidden = hidden;
				ptype = -1;
				return true;
			}
			if (ptype == 142) {
				int componentId = in.get2ULE();
				resetParentComponentSeq(componentId);
				if (chatbackComponentId != -1) {
					chatbackComponentId = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				invbackComponentId = componentId;
				redrawInvback = true;
				redrawSideicons = true;
				viewportComponentId = -1;
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
						component.text = s1;
						if (component.parentId == tabComponentId[selectedTab]) {
							redrawInvback = true;
						}
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 206) {
				publicChatSetting = in.get1U();
				privateChatSetting = in.get1U();
				tradeChatSetting = in.get1U();
				redrawPrivacySettings = true;
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 240) {
				if (selectedTab == 12) {
					redrawInvback = true;
				}
				weightCarried = in.get2();
				ptype = -1;
				return true;
			}
			if (ptype == 8) {
				int componentId = in.get2ULEA();
				int modelId = in.get2U();
				Component.instances[componentId].modelCategory = 1;
				Component.instances[componentId].modelId = modelId;
				ptype = -1;
				return true;
			}
			if (ptype == 122) {
				int componentId = in.get2ULEA();
				int rgb555 = in.get2ULEA();
				int r = (rgb555 >> 10) & 0x1f;
				int g = (rgb555 >> 5) & 0x1f;
				int b = rgb555 & 0x1f;
				Component.instances[componentId].color = (r << 19) + (g << 11) + (b << 3);
				ptype = -1;
				return true;
			}
			if (ptype == 53) {
				redrawInvback = true;
				int i7 = in.get2U();
				Component component_1 = Component.instances[i7];
				int j19 = in.get2U();
				for (int j22 = 0; j22 < j19; j22++) {
					int i25 = in.get1U();
					if (i25 == 255) {
						i25 = in.get4ME();
					}
					if (j22 >= component_1.invSlotObjId.length) {
						in.get2ULEA();
					} else {
						component_1.invSlotObjId[j22] = in.get2ULEA();
						component_1.invSlotAmount[j22] = i25;
					}
				}
				for (int j25 = j19; j25 < component_1.invSlotObjId.length; j25++) {
					component_1.invSlotObjId[j25] = 0;
					component_1.invSlotAmount[j25] = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 230) {
				int j7 = in.get2UA();
				int j14 = in.get2U();
				int k19 = in.get2U();
				int k22 = in.get2ULEA();
				Component.instances[j14].modelEyePitch = k19;
				Component.instances[j14].modelYaw = k22;
				Component.instances[j14].modelZoom = j7;
				ptype = -1;
				return true;
			}
			if (ptype == 221) {
				socialState = in.get1U();
				redrawInvback = true;
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
				localPlayerId = in.get2ULEA();
				ptype = -1;
				return true;
			}
			if (ptype == 65) {
				method31(in, psize);
				ptype = -1;
				return true;
			}
			if (ptype == 27) {
				showSocialInput = false;
				chatbackInputType = 1;
				chatbackInput = "";
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 187) {
				showSocialInput = false;
				chatbackInputType = 2;
				chatbackInput = "";
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 97) {
				int componentId = in.get2U();
				resetParentComponentSeq(componentId);
				if (invbackComponentId != -1) {
					invbackComponentId = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				if (chatbackComponentId != -1) {
					chatbackComponentId = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentId = componentId;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 218) {
				stickyChatbackComponentId = in.get2LEA();
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 87) {
				int j8 = in.get2ULE();
				int l14 = in.get4RME();
				anIntArray1045[j8] = l14;
				if (variables[j8] != l14) {
					variables[j8] = l14;
					updateVarp(j8);
					redrawInvback = true;
					if (stickyChatbackComponentId != -1) {
						redrawChatback = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 36) {
				int k8 = in.get2ULE();
				byte byte0 = in.get1();
				anIntArray1045[k8] = byte0;
				if (variables[k8] != byte0) {
					variables[k8] = byte0;
					updateVarp(k8);
					redrawInvback = true;
					if (stickyChatbackComponentId != -1) {
						redrawChatback = true;
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
				component_4.seqId = i15;
				if (i15 == -1) {
					component_4.seqFrame = 0;
					component_4.seqCycle = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 219) {
				if (invbackComponentId != -1) {
					invbackComponentId = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				if (chatbackComponentId != -1) {
					chatbackComponentId = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentId = -1;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 34) {
				redrawInvback = true;
				int i9 = in.get2U();
				Component component_2 = Component.instances[i9];
				while (in.position < psize) {
					int j20 = in.getSmartU();
					int i23 = in.get2U();
					int l25 = in.get1U();
					if (l25 == 255) {
						l25 = in.get4();
					}
					if ((j20 >= 0) && (j20 < component_2.invSlotObjId.length)) {
						component_2.invSlotObjId[j20] = i23;
						component_2.invSlotAmount[j20] = l25;
					}
				}
				ptype = -1;
				return true;
			}
			if ((ptype == 105) || (ptype == 84) || (ptype == 147) || (ptype == 215) || (ptype == 4) || (ptype == 117) || (ptype == 156) || (ptype == 44) || (ptype == 160) || (ptype == 101) || (ptype == 151)) {
				readZonePacket(in, ptype);
				ptype = -1;
				return true;
			}
			if (ptype == 106) {
				selectedTab = in.get1UC();
				redrawInvback = true;
				redrawSideicons = true;
				ptype = -1;
				return true;
			}
			if (ptype == 164) {
				int j9 = in.get2ULE();
				resetParentComponentSeq(j9);
				if (invbackComponentId != -1) {
					invbackComponentId = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				chatbackComponentId = j9;
				redrawChatback = true;
				viewportComponentId = -1;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			Signlink.reporterror("T1 (Invalid Packet ID) - " + ptype + "," + psize + " - " + anInt842 + "," + anInt843);
			disconnect();
		} catch (IOException _ex) {
			tryReconnect();
		} catch (Exception exception) {
			StringBuilder s2 = new StringBuilder("T2 (Packet Error) - " + ptype + "," + anInt842 + "," + anInt843 + " - " + psize + "," + (sceneBaseTileX + localPlayer.pathTileX[0]) + "," + (sceneBaseTileZ + localPlayer.pathTileZ[0]) + " - ");
			for (int j15 = 0; (j15 < psize) && (j15 < 50); j15++) {
				s2.append(in.data[j15]).append(",");
			}
			Signlink.reporterror(s2.toString());
			disconnect();
			exception.printStackTrace();
		}
		return true;
	}

	public void method146() {
		sceneCycle++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		updateSpotAnims();
		if (!aBoolean1160) {
			int pitch = cameraPitch;

			if ((anInt984 / 256) > pitch) {
				pitch = anInt984 / 256;
			}

			if (aBooleanArray876[4] && ((anIntArray1203[4] + 128) > pitch)) {
				pitch = anIntArray1203[4] + 128;
			}

			int yaw = (cameraYaw + cameraAnticheatAngle) & 0x7ff;
			method144(600 + (pitch * 3), pitch, cameraFocusX, getHeightmapY(currentPlane, localPlayer.x, localPlayer.z) - 50, yaw, cameraFocusZ);
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
		Model.mouseX = super.mouseX - 4;
		Model.mouseY = super.mouseY - 4;
		Draw2D.clear();
		scene.method313(anInt858, anInt860, anInt862, anInt859, j, anInt861);
		scene.clearTemporaryLocs();
		method34();
		drawTileHint();
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
		out.putOp(130);
		if (invbackComponentId != -1) {
			invbackComponentId = -1;
			redrawInvback = true;
			aBoolean1149 = false;
			redrawSideicons = true;
		}
		if (chatbackComponentId != -1) {
			chatbackComponentId = -1;
			redrawChatback = true;
			aBoolean1149 = false;
		}
		viewportComponentId = -1;
	}

}
