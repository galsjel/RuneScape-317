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
	public static int nodeID = 10;
	public static int portOffset;
	public static boolean members = true;
	public static boolean lowmem;
	public static boolean started;
	public static int drawCycle;
	public static PlayerEntity localPlayer;
	public static boolean aBoolean1156;
	public static int loopCycle;
	public static boolean flagged;

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

	public static String formatObjAmountTagged(int amount) {
		String s = String.valueOf(amount);
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

		nodeID = Integer.parseInt(args[0]);
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

	public static String getCombatLevelColorTag(int viewerLevel, int otherLevel) {
		int diff = viewerLevel - otherLevel;
		if (diff < -9) {
			return "@red@";
		} else if (diff < -6) {
			return "@or3@";
		} else if (diff < -3) {
			return "@or2@";
		} else if (diff < 0) {
			return "@or1@";
		} else if (diff > 9) {
			return "@gre@";
		} else if (diff > 6) {
			return "@gr3@";
		} else if (diff > 3) {
			return "@gr2@";
		} else if (diff > 0) {
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
	public final int[] adjustCameraShakeScale = new int[5];
	/**
	 *
	 */
	public final boolean[] adjustCameraComponent = new boolean[5];
	public final int anInt902 = 0x766654;
	public final long[] ignoreName37 = new long[100];
	public final int anInt927 = 0x332d25;
	public final int[] cameraWobbleSpeed = new int[5];
	public final CRC32 crc32 = new CRC32();
	public final int[] messageType = new int[100];
	public final String[] messageSender = new String[100];
	public final String[] messageText = new String[100];
	public static final int[] CHAT_COLORS = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
	public final int[] compassMaskLineOffsets = new int[33];
	public final int[] flameLineOffset = new int[256];
	public final FileStore[] filestores = new FileStore[5];
	public static final int MAX_CHATS = 50;
	public final int[] chatX = new int[MAX_CHATS];
	public final int[] chatY = new int[MAX_CHATS];
	public final int[] chatHeight = new int[MAX_CHATS];
	public final int[] chatWidth = new int[MAX_CHATS];
	public final int[] chatColors = new int[MAX_CHATS];
	public final int[] chatStyles = new int[MAX_CHATS];
	public final int[] chatTimers = new int[MAX_CHATS];
	public final String[] chats = new String[MAX_CHATS];
	public final int[] designColors = new int[5];
	public final int anInt1002 = 0x23201b;
	public final int[] cameraAdjustPhase = new int[5];
	public final int[] anIntArray1045 = new int[2000];
	public final int[] minimapMaskLineOffsets = new int[151];
	public final int[] compassMaskLineLengths = new int[33];
	/**
	 * Not an actual component, just a hacky fix (by Andy Grower) to use the same method to handle scrolling.
	 * @see #handleScrollInput(int, int, int, int, Component, int, boolean, int)
	 */
	public final Component chatComponent = new Component();
	public final int anInt1063 = 0x4d4233;
	public final int[] designIdentikits = new int[7];
	public final int[] archiveChecksum = new int[9];
	public final String[] playerOptions = new String[5];
	public final boolean[] aBooleanArray1128 = new boolean[5];
	public final int[][][] levelChunkBitset = new int[4][13][13];
	public final int[] tabComponentIDs = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	public final int[] cameraWobbleScale = new int[5];
	public final int[] waveIDs = new int[50];
	public final Image8[] imageModIcons = new Image8[2];
	public final int[] minimapMaskLineLengths = new int[151];
	public final int[] messageIDs = new int[100];
	public final int[] waveLoops = new int[50];
	public final int[] waveDelay = new int[50];
	public int ignoreCount;
	public long sceneLoadStartTime;
	public int[][] bfsCost = new int[104][104];
	public int[] friendWorld = new int[200];
	public DoublyLinkedList[][][] levelObjStacks = new DoublyLinkedList[4][104][104];
	public int[] flameBuffer3;
	public int[] flameBuffer2;
	public volatile boolean flameActive = false;
	public Socket aSocket832;
	public int titleScreenState;
	public Buffer chatBuffer = new Buffer(new byte[5000]);
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
	public int viewportComponentID = -1;
	public int cameraX;
	public int cameraY;
	public int cameraZ;
	public int cameraPitch;
	public int cameraYaw;
	public int rights;
	public Image8 imageRedstone1v;
	public Image8 imageRedstone2v;
	public Image8 imageRedstone3v;
	public Image8 imageRedstone1hv;
	public Image8 imageRedstone2hv;
	public Image24 imageMapmarker0;
	public Image24 imageMapmarker1;
	public boolean jaggrabEnabled = true; // original value: false
	public int lastWaveID = -1;
	public int weightCarried;
	public MouseRecorder mouseRecorder;
	public volatile boolean flamesThread = false;
	public String aString881 = "";
	public int localPID = -1;
	public boolean menuVisible = false;
	public int lastHoveredComponentID;
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
	public int currentLevel;
	public boolean errorLoading = false;
	public int[][] tileLastOccupiedCycle = new int[104][104];
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
	public boolean gripGrabbed = false;
	public int chatCount;
	/**
	 * Used to keep the camera from falling below the terrain.
	 */
	public int cameraPitchClamp;
	public int minimapLevel = -1;
	public Image24[] imageHitmarks = new Image24[20];
	public int objDragCycles;
	/**
	 * The max allowable distance away from the sides of a currently grabbed scrollbar grip before stopping input.
	 */
	public int gripThreshold;
	public ISAACRandom randomIn;
	public Image24 imageMapedge;
	public String chatbackInput = "";
	public int daysSinceLastLogin;
	public int psize;
	public int ptype;
	public int idleNetCycles;
	public int heartbeatTimer;
	/**
	 * Tells the client to disconnect instead of attempting to reestablish connection during a {@link #tryReconnect()}.
	 * This is typically set to 250 (5 seconds) after {@link #idleCycles} has reached 4500 (90 seconds).
	 *
	 * @see #updateIdleCycles()
	 * @see #tryReconnect()
	 */
	public int idleTimeout;
	public DoublyLinkedList projectiles = new DoublyLinkedList();
	public int orbitCameraX;
	public int orbitCameraZ;
	public int sendCameraDelay;
	public boolean sendCamera = false;
	/**
	 * A sticky component which does not leave on input.
	 */
	public int viewportOverlayComponentID = -1;
	public int minimapState;
	public int lastWriteDuplicates;
	public int sceneState;
	public Image8 imageScrollbar0;
	public Image8 imageScrollbar1;
	public int viewportHoveredComponentID;
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
	public int chatbackHoveredComponentID;
	public int flameGradientCycle0;
	public int flameGradientCycle1;
	public int stickyChatbackComponentID = -1;
	public int anInt1046;
	public boolean designGender = true;
	public int invbackHoveredComponentID;
	public String lastProgressMessage;
	public FileArchive archiveTitle;
	public int flashingTab = -1;
	public int multizone;
	public DoublyLinkedList spotanims = new DoublyLinkedList();
	public Image8[] imageMapscene = new Image8[100];
	public int waveCount;
	/**
	 * Used for adding/removing friends/ignores and sending private messages.
	 */
	public int socialAction;
	/**
	 * The current container slot id the mouse is hovered over that belongs to {@link #hoveredSlotParentID}.
	 */
	public int hoveredSlot;
	/**
	 * The current component id the mouse is hovered over that the {@link #hoveredSlot} belongs to.
	 */
	public int hoveredSlotParentID;
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
	public int objDragComponentID;
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
	public int chatScrollOffset;
	public int[] menuParamA = new int[500];
	public int[] menuParamB = new int[500];
	public int[] menuAction = new int[500];
	public int[] menuParamC = new int[500];
	public Image24[] imageHeadicons = new Image24[20];

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
	public int warnMembersInNonMembers;
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
	public int unreadMessages;
	public boolean ingame = false;
	public boolean aBoolean1158 = false;
	public boolean sceneInstanced = false;
	/**
	 * When <code>true</code> will cause the camera to be overridden by the following cinematic fields.
	 * @see #applyCinematicCamera()
	 * @see #cinematicSrcLocalTileX
	 * @see #cinematicSrcLocalTileZ
	 * @see #cinematicSrcHeight
	 * @see #cinematicMoveSpeed
	 * @see #cinematicMoveAcceleration
	 * @see #cinematicDstLocalTileX
	 * @see #cinematicDstLocalTileZ
	 * @see #cinematicDstHeight
	 * @see #cinematicRotateSpeed
	 * @see #cinematicRotateAcceleration
	 */
	public boolean cinematic = false;
	/**
	 * @see #cinematic
	 */
	public int cinematicSrcLocalTileX;
	/**
	 * @see #cinematic
	 */
	public int cinematicSrcLocalTileZ;
	/**
	 * @see #cinematic
	 */
	public int cinematicSrcHeight;
	/**
	 * @see #cinematic
	 */
	public int cinematicMoveSpeed;
	/**
	 * @see #cinematic
	 */
	public int cinematicMoveAcceleration;
	/**
	 * @see #cinematic
	 */
	public int cinematicDstLocalTileX;
	/**
	 * @see #cinematic
	 */
	public int cinematicDstLocalTileZ;
	/**
	 * @see #cinematic
	 */
	public int cinematicDstHeight;
	/**
	 * @see #cinematic
	 */
	public int cinematicRotateSpeed;
	/**
	 * @see #cinematic
	 */
	public int cinematicRotateAcceleration;
	public DrawArea areaInvback;
	public DrawArea areaMapback;
	public DrawArea areaViewport;
	public DrawArea areaChatback;
	public int daysSinceRecoveriesChanged;
	public Connection connection;
	public int messageCounter;
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
	public int orbitCameraPitch = 128;
	public int orbitCameraYaw;
	public int orbitCameraYawVelocity;
	public int orbitCameraPitchVelocity;
	public int invbackComponentID = -1;
	public int[] flameBuffer0;
	public int[] flameBuffer1;
	public Buffer out = Buffer.create(1);
	public int lastAddress;
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
	public int dragCycles;
	public int[][][] levelHeightmap;
	public long aLong1215;
	public int titleLoginField;
	public long prevMousePressTime;
	public int selectedTab = 3;
	public int hintNPC;
	public boolean redrawChatback = false;
	public int chatbackInputType;
	public int music;
	public boolean musicFading = true;
	public SceneCollisionMap[] levelCollisionMap = new SceneCollisionMap[4];
	public boolean redrawPrivacySettings = false;
	public int[] sceneMapIndex;
	public int[] sceneMapLandFile;
	public int[] sceneMapLocFile;
	public int lastWriteX;
	public int lastWriteY;
	public boolean objGrabThreshold = false;
	public int actionCycles;
	public int actionComponentID;
	public int actionInvSlot;
	public int actionArea;
	public byte[][] sceneMapLocData;
	public int tradeChatSetting;
	public int chatEffects;
	/**
	 * No effect when set to <code>0</code>, otherwise block chats.
	 */
	public int overrideChat;
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
	public byte[][][] levelTileFlags;
	public int nextMusicDelay;
	public int flagSceneTileX;
	public int flagSceneTileZ;
	public Image24 imageMinimap;
	public int tryMoveNearest;
	public int sceneCycle;
	public String loginMessage0 = "";
	public String loginMessage1 = "";
	public int baseX;
	public int baseZ;
	public BitmapFont fontPlain11;
	public BitmapFont fontPlain12;
	public BitmapFont fontBold12;
	public BitmapFont fontQuill8;
	public int flameCycle0;
	public int chatbackComponentID = -1;
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

			levelTileFlags = new byte[4][104][104];
			levelHeightmap = new int[4][105][105];
			scene = new Scene(104, 104, levelHeightmap, 4);

			for (int level = 0; level < 4; level++) {
				levelCollisionMap[level] = new SceneCollisionMap(104, 104);
			}

			imageMinimap = new Image24(512, 512);

			FileArchive archiveVersionlist = loadArchive(5, "update list", "versionlist", archiveChecksum[5], 60);

			showProgress(60, "Connecting to update server");

			ondemand = new OnDemand();
			ondemand.load(archiveVersionlist, this);

			SeqTransform.init(ondemand.getSeqFrameCount());
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
		chatBuffer = null;
		out = null;
		login = null;
		in = null;
		sceneMapIndex = null;
		sceneMapLandData = null;
		sceneMapLocData = null;
		sceneMapLandFile = null;
		sceneMapLocFile = null;
		levelHeightmap = null;
		levelTileFlags = null;
		scene = null;
		levelCollisionMap = null;
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
		tileLastOccupiedCycle = null;
		players = null;
		playerIndices = null;
		anIntArray894 = null;
		aBufferArray895 = null;
		anIntArray840 = null;
		npcs = null;
		npcIndices = null;
		levelObjStacks = null;
		temporaryLocs = null;
		projectiles = null;
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
		SeqTransform.unload();
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
			drawGame();
		}
		dragCycles = 0;
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
					archiveChecksum[i1] = buffer.read32();
				}
				int j1 = buffer.read32();
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
		} else if (chatbackComponentID != -1) {
			drawParentComponent(Component.instances[chatbackComponentID], 0, 0, 0);
		} else if (stickyChatbackComponentID != -1) {
			drawParentComponent(Component.instances[stickyChatbackComponentID], 0, 0, 0);
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
			int y = (70 - (line * 14)) + chatScrollOffset;
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

		drawScrollbar(463, 0, 77, chatScrollHeight, chatScrollHeight - chatScrollOffset - 77);

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

	public void handleMouseInput() {
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
					int componentID = menuParamB[menuSize - 1];
					Component component = Component.instances[componentID];
					if (component.invDraggable || component.invMoveReplaces) {
						objGrabThreshold = false;
						objDragCycles = 0;
						objDragComponentID = componentID;
						objDragSlot = objSlot;
						objDragArea = 2;
						objGrabX = super.mousePressX;
						objGrabY = super.mousePressY;
						if (Component.instances[componentID].parentID == viewportComponentID) {
							objDragArea = 1;
						}
						if (Component.instances[componentID].parentID == chatbackComponentID) {
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
			minimapLevel = -1;
			spotanims.clear();
			projectiles.clear();
			Draw3D.clearTexels();
			clearCaches();
			scene.clear();
			System.gc();
			for (int i = 0; i < 4; i++) {
				levelCollisionMap[i].reset();
			}
			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++) {
						levelTileFlags[l][k1][j2] = 0;
					}
				}
			}
			SceneBuilder sceneBuilder = new SceneBuilder(levelTileFlags, 104, 104, levelHeightmap);
			int k2 = sceneMapLandData.length;
			out.writeOp(0);
			if (!sceneInstanced) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = ((sceneMapIndex[i3] >> 8) * 64) - sceneBaseTileX;
					int k5 = ((sceneMapIndex[i3] & 0xff) * 64) - sceneBaseTileZ;
					byte[] abyte0 = sceneMapLandData[i3];
					if (abyte0 != null) {
						sceneBuilder.method180(abyte0, k5, i4, (sceneCenterZoneX - 6) * 8, (sceneCenterZoneZ - 6) * 8, levelCollisionMap);
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
				out.writeOp(0);
				for (int i6 = 0; i6 < k2; i6++) {
					byte[] abyte1 = sceneMapLocData[i6];
					if (abyte1 != null) {
						int l8 = ((sceneMapIndex[i6] >> 8) * 64) - sceneBaseTileX;
						int k9 = ((sceneMapIndex[i6] & 0xff) * 64) - sceneBaseTileZ;
						sceneBuilder.method190(l8, levelCollisionMap, k9, scene, abyte1);
					}
				}
			}
			if (sceneInstanced) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = levelChunkBitset[j3][k4][j6];
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
									sceneBuilder.method179(i9, l9, levelCollisionMap, k4 * 8, (j10 & 7) * 8, sceneMapLandData[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}
							}
						}
					}
				}
				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = levelChunkBitset[0][l4][k6];
						if (i8 == -1) {
							sceneBuilder.method174(k6 * 8, 8, 8, l4 * 8);
						}
					}
				}
				out.writeOp(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = levelChunkBitset[l6][j8][j9];
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
									sceneBuilder.method183(levelCollisionMap, scene, k10, j8 * 8, (i12 & 7) * 8, l6, sceneMapLocData[k12], (k11 & 7) * 8, i11, j9 * 8);
									break;
								}
							}
						}
					}
				}
			}
			out.writeOp(0);
			sceneBuilder.method171(levelCollisionMap, scene);
			areaViewport.bind();
			out.writeOp(0);
			int k3 = SceneBuilder.minLevel;
			if (k3 > currentLevel) {
				k3 = currentLevel;
			}
			if (lowmem) {
				scene.setMinLevel(SceneBuilder.minLevel);
			} else {
				scene.setMinLevel(0);
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
			out.writeOp(210);
			out.write32(0x3f008edd);
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

	public void createMinimap(int level) {
		int[] pixels = imageMinimap.pixels;

		Arrays.fill(pixels, 0);

		for (int z = 1; z < 103; z++) {
			int offset = (52 + (48 * 512)) + ((103 - z) * 512 * 4);

			for (int x = 1; x < 103; x++) {
				if ((levelTileFlags[level][x][z] & 0x18) == 0) {
					scene.drawMinimapTile(pixels, offset, 512, level, x, z);
				}

				if ((level < 3) && ((levelTileFlags[level + 1][x][z] & 8) != 0)) {
					scene.drawMinimapTile(pixels, offset, 512, level + 1, x, z);
				}

				offset += 4;
			}
		}

		int wallRGB = 0xEEEEEE;
		int doorRGB = 0xEE0000;

		imageMinimap.bind();

		for (int z = 1; z < 103; z++) {
			for (int x = 1; x < 103; x++) {
				if ((levelTileFlags[level][x][z] & 0x18) == 0) {
					drawMinimapLoc(z, wallRGB, x, doorRGB, level);
				}

				if ((level < 3) && ((levelTileFlags[level + 1][x][z] & 8) != 0)) {
					drawMinimapLoc(z, wallRGB, x, doorRGB, level + 1);
				}
			}
		}

		areaViewport.bind();
		activeMapFunctionCount = 0;

		for (int x = 0; x < 104; x++) {
			for (int z = 0; z < 104; z++) {
				int bitset = scene.getGroundDecorationBitset(currentLevel, x, z);

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
					int[][] flags = levelCollisionMap[currentLevel].flags;
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
		DoublyLinkedList list = levelObjStacks[currentLevel][x][z];
		if (list == null) {
			scene.removeObjStack(currentLevel, x, z);
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
		scene.addObjStack(stack0, stack1, stack2, currentLevel, x, z, getHeightmapY(currentLevel, (x * 128) + 64, (z * 128) + 64), bitset);
	}

	public void appendNPCs(boolean flag) {
		for (int i = 0; i < npcCount; i++) {
			NPCEntity npc = npcs[npcIndices[i]];
			int bitset = 0x20000000 + (npcIndices[i] << 14);

			if ((npc == null) || !npc.isVisible() || (npc.type.aBoolean93 != flag)) {
				continue;
			}

			int x = npc.x >> 7;
			int z = npc.z >> 7;

			if ((x < 0) || (x >= 104) || (z < 0) || (z >= 104)) {
				continue;
			}

			if ((npc.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
				if (tileLastOccupiedCycle[x][z] == sceneCycle) {
					continue;
				}
				tileLastOccupiedCycle[x][z] = sceneCycle;
			}
			if (!npc.type.aBoolean84) {
				bitset += 0x80000000;
			}
			scene.addTemporary(npc, currentLevel, npc.x, npc.z, getHeightmapY(currentLevel, npc.x, npc.z), npc.yaw, bitset, npc.aBoolean1541, ((npc.size - 1) * 64) + 60);
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
					lastHoveredComponentID = child.delegateHover;
				} else {
					lastHoveredComponentID = child.id;
				}
			}

			if (child.type == 0) {
				handleParentComponentInput(cx, child, mouseX, cy, mouseY, child.scrollY);

				if (child.scrollableHeight > child.height) {
					handleScrollInput(cx + child.width, child.height, mouseX, mouseY, child, cy, true, child.scrollableHeight);
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
								hoveredSlotParentID = child.id;
								if (child.invSlotObjID[k2] > 0) {
									ObjType type = ObjType.get(child.invSlotObjID[k2] - 1);
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

	public void handleChatSettingsInput() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 6) && (super.mousePressX <= 106) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				publicChatSetting = (publicChatSetting + 1) % 4;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.writeOp(95);
				out.write8(publicChatSetting);
				out.write8(privateChatSetting);
				out.write8(tradeChatSetting);
			}
			if ((super.mousePressX >= 135) && (super.mousePressX <= 235) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				privateChatSetting = (privateChatSetting + 1) % 3;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.writeOp(95);
				out.write8(publicChatSetting);
				out.write8(privateChatSetting);
				out.write8(tradeChatSetting);
			}
			if ((super.mousePressX >= 273) && (super.mousePressX <= 373) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				tradeChatSetting = (tradeChatSetting + 1) % 3;
				redrawPrivacySettings = true;
				redrawChatback = true;
				out.writeOp(95);
				out.write8(publicChatSetting);
				out.write8(privateChatSetting);
				out.write8(tradeChatSetting);
			}
			if ((super.mousePressX >= 412) && (super.mousePressX <= 512) && (super.mousePressY >= 467) && (super.mousePressY <= 499)) {
				if (viewportComponentID == -1) {
					closeInterfaces();
					aString881 = "";
					aBoolean1158 = false;
					for (int i = 0; i < Component.instances.length; i++) {
						if ((Component.instances[i] == null) || (Component.instances[i].contentType != 600)) {
							continue;
						}
						anInt1178 = viewportComponentID = Component.instances[i].parentID;
						break;
					}
				} else {
					addMessage(0, "", "Please close the interface you have open before using 'report abuse'");
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
				setWaveVolume(0);
			}
			if (value == 1) {
				aBoolean848 = true;
				setWaveVolume(-400);
			}
			if (value == 2) {
				aBoolean848 = true;
				setWaveVolume(-800);
			}
			if (value == 3) {
				aBoolean848 = true;
				setWaveVolume(-1200);
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

	public void draw2DEntityElements() {
		chatCount = 0;
		for (int index = -1; index < (playerCount + npcCount); index++) {
			PathingEntity entity;

			if (index == -1) {
				entity = localPlayer;
			} else if (index < playerCount) {
				entity = players[playerIndices[index]];
			} else {
				entity = npcs[npcIndices[index - playerCount]];
			}

			if ((entity == null) || !entity.isVisible()) {
				continue;
			}

			if (entity instanceof NPCEntity) {
				NPCType type = ((NPCEntity) entity).type;
				if (type.overrides != null) {
					type = type.getOverrideType();
				}
				if (type == null) {
					continue;
				}
			}

			if (index < playerCount) {
				PlayerEntity player = (PlayerEntity) entity;

				int y = 30;
				
				if (player.headicons != 0) {
					projectToScreen(player, player.height + 15);

					if (projectX > -1) {
						for (int icon = 0; icon < 8; icon++) {
							if ((player.headicons & (1 << icon)) != 0) {
								imageHeadicons[icon].draw(projectX - 12, projectY - y);
								y -= 25;
							}
						}
					}
				}

				if ((index >= 0) && (hintType == 10) && (hintPlayer == playerIndices[index])) {
					projectToScreen(player, player.height + 15);

					if (projectX > -1) {
						imageHeadicons[7].draw(projectX - 12, projectY - y);
					}
				}
			} else {
				NPCType type = ((NPCEntity) entity).type;

				if ((type.headicon >= 0) && (type.headicon < imageHeadicons.length)) {
					projectToScreen(entity, entity.height + 15);

					if (projectX > -1) {
						imageHeadicons[type.headicon].draw(projectX - 12, projectY - 30);
					}
				}
				if ((hintType == 1) && (hintNPC == npcIndices[index - playerCount]) && ((loopCycle % 20) < 10)) {
					projectToScreen(entity, entity.height + 15);
					if (projectX > -1) {
						imageHeadicons[2].draw(projectX - 12, projectY - 28);
					}
				}
			}

			if ((entity.chat != null) && ((index >= playerCount) || (publicChatSetting == 0) || (publicChatSetting == 3) || ((publicChatSetting == 1) && isFriend(((PlayerEntity) entity).name)))) {
				projectToScreen(entity, entity.height);

				if ((projectX > -1) && (chatCount < MAX_CHATS)) {
					chatWidth[chatCount] = fontBold12.stringWidth(entity.chat) / 2;
					chatHeight[chatCount] = fontBold12.height;
					chatX[chatCount] = projectX;
					chatY[chatCount] = projectY;
					chatColors[chatCount] = entity.chatColor;
					chatStyles[chatCount] = entity.chatStyle;
					chatTimers[chatCount] = entity.chatTimer;
					chats[chatCount++] = entity.chat;

					if ((chatEffects == 0) && (entity.chatStyle >= 1) && (entity.chatStyle <= 3)) {
						chatHeight[chatCount] += 10;
						chatY[chatCount] += 5;
					}

					if ((chatEffects == 0) && (entity.chatStyle == 4)) {
						chatWidth[chatCount] = 60;
					}

					if ((chatEffects == 0) && (entity.chatStyle == 5)) {
						chatHeight[chatCount] += 5;
					}
				}
			}

			drawHealth(entity);
			drawHitmarks(entity);
		}
	}

	private void drawHealth(PathingEntity entity) {
		if (entity.combatCycle > loopCycle) {
			projectToScreen(entity, entity.height + 15);

			if (projectX > -1) {
				int w = (entity.health * 30) / entity.totalHealth;

				if (w > 30) {
					w = 30;
				}

				Draw2D.fillRect(projectX - 15, projectY - 3, w, 5, 65280);
				Draw2D.fillRect((projectX - 15) + w, projectY - 3, 30 - w, 5, 0xff0000);
			}
		}
	}

	private void drawHitmarks(PathingEntity entity) {
		for (int j = 0; j < 4; j++) {
			if (entity.damageCycle[j] <= loopCycle) {
				continue;
			}

			projectToScreen(entity, entity.height / 2);

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
				imageHitmarks[entity.damageType[j]].draw(projectX - 12, projectY - 12);
				fontPlain11.drawStringCenter(String.valueOf(entity.damage[j]), projectX, projectY + 4, 0);
				fontPlain11.drawStringCenter(String.valueOf(entity.damage[j]), projectX - 1, projectY + 3, 0xffffff);
			}
		}
	}

	private void drawChats() {
		for (int i = 0; i < chatCount; i++) {
			int x = chatX[i];
			int y = chatY[i];
			int padding = chatWidth[i];
			int height = chatHeight[i];

			boolean sorting = true;

			while (sorting) {
				sorting = false;
				for (int j = 0; j < i; j++) {
					if (((y + 2) > (chatY[j] - chatHeight[j])) && ((y - height) < (chatY[j] + 2)) && ((x - padding) < (chatX[j] + chatWidth[j])) && ((x + padding) > (chatX[j] - chatWidth[j])) && ((chatY[j] - chatHeight[j]) < y)) {
						y = chatY[j] - chatHeight[j];
						sorting = true;
					}
				}
			}

			projectX = chatX[i];
			projectY = chatY[i] = y;

			String message = chats[i];

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
			out.writeOp(215);
			out.write64(name37);
			break;
		}
	}

	public void drawInvback() {
		areaInvback.bind();
		Draw3D.lineOffset = areaInvbackOffsets;
		imageInvback.blit(0, 0);

		if (invbackComponentID != -1) {
			drawParentComponent(Component.instances[invbackComponentID], 0, 0, 0);
		} else if (tabComponentIDs[selectedTab] != -1) {
			drawParentComponent(Component.instances[tabComponentIDs[selectedTab]], 0, 0, 0);
		}

		if (menuVisible && (mouseArea == 1)) {
			drawMenu();
		}

		areaInvback.draw(super.graphics, 553, 205);
		areaViewport.bind();
		Draw3D.lineOffset = areaViewportOffsets;
	}

	public void updateTextures(int cycle) {
		if (!lowmem) {
			if (Draw3D.textureCycle[17] >= cycle) {
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
			if (Draw3D.textureCycle[24] >= cycle) {
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
			if (Draw3D.textureCycle[34] >= cycle) {
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

	public void updateEntityChats() {
		for (int i = -1; i < playerCount; i++) {
			int playerID;

			if (i == -1) {
				playerID = LOCAL_PLAYER_INDEX;
			} else {
				playerID = playerIndices[i];
			}

			PlayerEntity player = players[playerID];

			if ((player != null) && (player.chatTimer > 0)) {
				player.chatTimer--;
				if (player.chatTimer == 0) {
					player.chat = null;
				}
			}
		}

		for (int i = 0; i < npcCount; i++) {
			int npcID = npcIndices[i];
			NPCEntity npc = npcs[npcID];

			if ((npc != null) && (npc.chatTimer > 0)) {
				npc.chatTimer--;
				if (npc.chatTimer == 0) {
					npc.chat = null;
				}
			}
		}
	}

	/**
	 * Applies the cinematic camera properties to the main camera.
	 * @see #cinematic
	 */
	public void applyCinematicCamera() {
		// the following code updates the position of the camera
		int x = (cinematicSrcLocalTileX * 128) + 64;
		int z = (cinematicSrcLocalTileZ * 128) + 64;
		int y = getHeightmapY(currentLevel, x, z) - cinematicSrcHeight;

		if (cameraX < x) {
			cameraX += cinematicMoveSpeed + (((x - cameraX) * cinematicMoveAcceleration) / 1000);
			if (cameraX > x) {
				cameraX = x;
			}
		}
		if (cameraX > x) {
			cameraX -= cinematicMoveSpeed + (((cameraX - x) * cinematicMoveAcceleration) / 1000);
			if (cameraX < x) {
				cameraX = x;
			}
		}
		if (cameraY < y) {
			cameraY += cinematicMoveSpeed + (((y - cameraY) * cinematicMoveAcceleration) / 1000);
			if (cameraY > y) {
				cameraY = y;
			}
		}
		if (cameraY > y) {
			cameraY -= cinematicMoveSpeed + (((cameraY - y) * cinematicMoveAcceleration) / 1000);
			if (cameraY < y) {
				cameraY = y;
			}
		}
		if (cameraZ < z) {
			cameraZ += cinematicMoveSpeed + (((z - cameraZ) * cinematicMoveAcceleration) / 1000);
			if (cameraZ > z) {
				cameraZ = z;
			}
		}
		if (cameraZ > z) {
			cameraZ -= cinematicMoveSpeed + (((cameraZ - z) * cinematicMoveAcceleration) / 1000);
			if (cameraZ < z) {
				cameraZ = z;
			}
		}

		// the following code updates the angle of the camera

		x = (cinematicDstLocalTileX * 128) + 64;
		z = (cinematicDstLocalTileZ * 128) + 64;
		y = getHeightmapY(currentLevel, x, z) - cinematicDstHeight;

		int deltaX = x - cameraX;
		int deltaY = y - cameraY;
		int deltaZ = z - cameraZ;

		int distance = (int) Math.sqrt((deltaX * deltaX) + (deltaZ * deltaZ));
		int pitch = (int) (Math.atan2(deltaY, distance) * 325.94900000000001D) & 0x7ff;
		int yaw = (int) (Math.atan2(deltaX, deltaZ) * -325.94900000000001D) & 0x7ff;

		if (pitch < 128) {
			pitch = 128;
		}

		if (pitch > 383) {
			pitch = 383;
		}

		if (cameraPitch < pitch) {
			cameraPitch += cinematicRotateSpeed + (((pitch - cameraPitch) * cinematicRotateAcceleration) / 1000);
			if (cameraPitch > pitch) {
				cameraPitch = pitch;
			}
		}

		if (cameraPitch > pitch) {
			cameraPitch -= cinematicRotateSpeed + (((cameraPitch - pitch) * cinematicRotateAcceleration) / 1000);
			if (cameraPitch < pitch) {
				cameraPitch = pitch;
			}
		}

		int deltaYaw = yaw - cameraYaw;

		if (deltaYaw > 1024) {
			deltaYaw -= 2048;
		}

		if (deltaYaw < -1024) {
			deltaYaw += 2048;
		}

		if (deltaYaw > 0) {
			cameraYaw += cinematicRotateSpeed + ((deltaYaw * cinematicRotateAcceleration) / 1000);
			cameraYaw &= 0x7ff;
		}

		if (deltaYaw < 0) {
			cameraYaw -= cinematicRotateSpeed + ((-deltaYaw * cinematicRotateAcceleration) / 1000);
			cameraYaw &= 0x7ff;
		}

		int tmp = yaw - cameraYaw;

		if (tmp > 1024) {
			tmp -= 2048;
		}

		if (tmp < -1024) {
			tmp += 2048;
		}

		if (((tmp < 0) && (deltaYaw > 0)) || ((tmp > 0) && (deltaYaw < 0))) {
			cameraYaw = yaw;
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
			addMessage(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		if (friendCount >= 200) {
			addMessage(0, "", "Your friendlist is full. Max of 100 for free users, and 200 for members");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(name37));
		for (int i = 0; i < friendCount; i++) {
			if (friendName37[i] == name37) {
				addMessage(0, "", s + " is already on your friend list");
				return;
			}
		}
		for (int j = 0; j < ignoreCount; j++) {
			if (ignoreName37[j] == name37) {
				addMessage(0, "", "Please remove " + s + " from your ignore list first");
				return;
			}
		}
		if (!s.equals(localPlayer.name)) {
			friendName[friendCount] = s;
			friendName37[friendCount] = name37;
			friendWorld[friendCount] = 0;
			friendCount++;
			redrawInvback = true;
			out.writeOp(188);
			out.write64(name37);
		}
	}

	public int getHeightmapY(int level, int sceneX, int sceneZ) {
		int tileX = sceneX >> 7;
		int tileZ = sceneZ >> 7;

		if ((tileX < 0) || (tileZ < 0) || (tileX > 103) || (tileZ > 103)) {
			return 0;
		}

		int realLevel = level;

		if ((realLevel < 3) && ((levelTileFlags[1][tileX][tileZ] & 2) == 2)) {
			realLevel++;
		}

		int tileLocalX = sceneX & 0x7f; // the coordinate within the tile 0..127
		int tileLocalZ = sceneZ & 0x7f;
		int y00 = ((levelHeightmap[realLevel][tileX][tileZ] * (128 - tileLocalX)) + (levelHeightmap[realLevel][tileX + 1][tileZ] * tileLocalX)) >> 7;
		int y11 = ((levelHeightmap[realLevel][tileX][tileZ + 1] * (128 - tileLocalX)) + (levelHeightmap[realLevel][tileX + 1][tileZ + 1] * tileLocalX)) >> 7;
		return ((y00 * (128 - tileLocalZ)) + (y11 * tileLocalZ)) >> 7;
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
			levelCollisionMap[i].reset();
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
			int k = buffer.readN(14);
			if (k == 16383) {
				break;
			}
			if (npcs[k] == null) {
				npcs[k] = new NPCEntity();
			}
			NPCEntity npc = npcs[k];
			npcIndices[npcCount++] = k;
			npc.anInt1537 = loopCycle;
			int z = buffer.readN(5);
			if (z > 15) {
				z -= 32;
			}
			int x = buffer.readN(5);
			if (x > 15) {
				x -= 32;
			}
			int j1 = buffer.readN(1);
			npc.type = NPCType.get(buffer.readN(12));
			int k1 = buffer.readN(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = k;
			}
			npc.size = npc.type.size;
			npc.turnSpeed = npc.type.turnSpeed;
			npc.seqWalkID = npc.type.seqWalkID;
			npc.seqTurnAroundID = npc.type.seqTurnAroundID;
			npc.seqTurnLeftID = npc.type.seqTurnLeftID;
			npc.seqTurnRightID = npc.type.seqTurnRightID;
			npc.seqStandID = npc.type.seqStandID;
			npc.move(localPlayer.pathTileX[0] + x, localPlayer.pathTileZ[0] + z, j1 == 1);
		}
		buffer.accessBytes();
	}

	public void appendPlayers(boolean local) {
		if (((localPlayer.x >> 7) == flagSceneTileX) && ((localPlayer.z >> 7) == flagSceneTileZ)) {
			flagSceneTileX = 0;
		}

		int count = this.playerCount;

		if (local) {
			count = 1;
		}

		for (int i = 0; i < count; i++) {
			PlayerEntity player;
			int index;

			if (local) {
				player = localPlayer;
				index = LOCAL_PLAYER_INDEX << 14;
			} else {
				player = players[playerIndices[i]];
				index = playerIndices[i] << 14;
			}

			if ((player == null) || !player.isVisible()) {
				continue;
			}

			player.lowmem = ((lowmem && (this.playerCount > 50)) || (this.playerCount > 200)) && !local && (player.secondarySeqID == player.seqStandID);

			int stx = player.x >> 7;
			int stz = player.z >> 7;

			if ((stx < 0) || (stx >= 104) || (stz < 0) || (stz >= 104)) {
				continue;
			}

			if ((player.locModel != null) && (loopCycle >= player.locStartCycle) && (loopCycle < player.locStopCycle)) {
				player.lowmem = false;
				player.y = getHeightmapY(currentLevel, player.x, player.z);
				scene.addTemporary(player, currentLevel, player.minSceneTileX, player.minSceneTileZ, player.maxSceneTileX, player.maxSceneTileZ, player.x, player.z, player.y, player.yaw, index);
				continue;
			}

			if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
				if (tileLastOccupiedCycle[stx][stz] == sceneCycle) {
					continue;
				}
				tileLastOccupiedCycle[stx][stz] = sceneCycle;
			}

			player.y = getHeightmapY(currentLevel, player.x, player.z);
			scene.addTemporary(player, currentLevel, player.x, player.z, player.y, player.yaw, index, player.aBoolean1541, 60);
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
			out.writeOp(101);
			out.write8(designGender ? 0 : 1);
			for (int i = 0; i < 7; i++) {
				out.write8(designIdentikits[i]);
			}
			for (int i = 0; i < 5; i++) {
				out.write8(designColors[i]);
			}
			return true;
		}
		if ((type >= 601) && (type <= 612)) {
			closeInterfaces();
			if (aString881.length() > 0) {
				out.writeOp(218);
				out.write64(StringUtil.toBase37(aString881));
				out.write8(type - 601);
				out.write8(aBoolean1158 ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			PlayerEntity player = players[k];
			int l = buffer.read8U();
			if ((l & 0x40) != 0) {
				l += buffer.read8U() << 8;
			}
			method107(l, k, buffer, player);
		}
	}

	public void drawMinimapLoc(int z, int wallRGB, int x, int doorRGB, int level) {
		int bitset = scene.getWallBitset(level, x, z);

		if (bitset != 0) {
			int info = scene.getInfo(level, x, z, bitset);
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

		bitset = scene.getLocBitset(level, x, z);

		if (bitset != 0) {
			int info = scene.getInfo(level, x, z, bitset);
			int rotation = (info >> 6) & 3;
			int kind = info & 0x1f;
			int locID = (bitset >> 14) & 0x7fff;
			LocType type = LocType.get(locID);

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

		bitset = scene.getGroundDecorationBitset(level, x, z);

		if (bitset != 0) {
			int locID = (bitset >> 14) & 0x7fff;
			LocType type = LocType.get(locID);

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

	public void updateSceneState() {
		if (lowmem && (sceneState == 2) && (SceneBuilder.anInt131 != currentLevel)) {
			areaViewport.bind();
			fontPlain12.drawStringCenter("Loading - please wait.", 257, 151, 0);
			fontPlain12.drawStringCenter("Loading - please wait.", 256, 150, 0xffffff);
			areaViewport.draw(super.graphics, 4, 4);
			sceneState = 1;
			sceneLoadStartTime = System.currentTimeMillis();
		}
		if (sceneState == 1) {
			int state = getDrawState();
			if ((state != 0) && ((System.currentTimeMillis() - sceneLoadStartTime) > 0x57e40L)) {
				Signlink.reporterror(username + " glcfb " + aLong1215 + "," + state + "," + lowmem + "," + filestores[0] + "," + ondemand.remaining() + "," + currentLevel + "," + sceneCenterZoneX + "," + sceneCenterZoneZ);
				sceneLoadStartTime = System.currentTimeMillis();
			}
		}
		if ((sceneState == 2) && (currentLevel != minimapLevel)) {
			minimapLevel = currentLevel;
			createMinimap(currentLevel);
		}
	}

	public int getDrawState() {
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
			SceneBuilder.anInt131 = currentLevel;
			method22();
			out.writeOp(121);
			return 0;
		}
	}

	public void appendProjectiles() {
		for (ProjectileEntity proj = (ProjectileEntity) projectiles.peekFront(); proj != null; proj = (ProjectileEntity) projectiles.prev()) {
			if ((proj.level != currentLevel) || (loopCycle > proj.lastCycle)) {
				proj.unlink();
			} else if (loopCycle >= proj.startCycle) {
				if (proj.target > 0) {
					NPCEntity npc = npcs[proj.target - 1];
					if ((npc != null) && (npc.x >= 0) && (npc.x < 13312) && (npc.z >= 0) && (npc.z < 13312)) {
						proj.updateVelocity(loopCycle, npc.z, getHeightmapY(proj.level, npc.x, npc.z) - proj.offsetY, npc.x);
					}
				}
				if (proj.target < 0) {
					int pid = -proj.target - 1;
					PlayerEntity player;
					if (pid == localPID) {
						player = localPlayer;
					} else {
						player = players[pid];
					}
					if ((player != null) && (player.x >= 0) && (player.x < 13312) && (player.z >= 0) && (player.z < 13312)) {
						proj.updateVelocity(loopCycle, player.z, getHeightmapY(proj.level, player.x, player.z) - proj.offsetY, player.x);
					}
				}
				proj.update(delta);
				scene.addTemporary(proj, currentLevel, (int) proj.x, (int) proj.z, (int) proj.y, proj.yaw, -1, false, 60);
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
						if (chatbackComponentID != -1) {
							redrawChatback = true;
						}
					}
				}

				if ((request.store == 1) && (request.data != null)) {
					SeqTransform.unpack(request.data);
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
		for (int i = 0; i < parent.children.length; i++) {
			if (parent.children[i] == -1) {
				break;
			}
			Component child = Component.instances[parent.children[i]];
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
			if (flagged) {
				if ((super.mousePressButton != 0) || (mouseRecorder.length >= 40)) {
					out.writeOp(45);
					out.write8(0);
					int startPosition = out.position;
					int written = 0;
					for (int i = 0; i < mouseRecorder.length; i++) {
						if ((startPosition - out.position) >= 240) {
							break;
						}
						written++;
						int y = mouseRecorder.y[i];
						if (y < 0) {
							y = 0;
						} else if (y > 502) {
							y = 502;
						}
						int x = mouseRecorder.x[i];
						if (x < 0) {
							x = 0;
						} else if (x > 764) {
							x = 764;
						}
						int pos = (y * 765) + x;
						if ((mouseRecorder.y[i] == -1) && (mouseRecorder.x[i] == -1)) {
							x = -1;
							y = -1;
							pos = 0x7ffff;
						}
						if ((x == lastWriteX) && (y == lastWriteY)) {
							if (lastWriteDuplicates < 2047) {
								lastWriteDuplicates++;
							}
						} else {
							int dx = x - lastWriteX;
							lastWriteX = x;
							int dy = y - lastWriteY;
							lastWriteY = y;
							if ((lastWriteDuplicates < 8) && (dx >= -32) && (dx <= 31) && (dy >= -32) && (dy <= 31)) {
								dx += 32;
								dy += 32;
								out.write16((lastWriteDuplicates << 12) + (dx << 6) + dy);
								lastWriteDuplicates = 0;
							} else if (lastWriteDuplicates < 8) {
								out.write24(0x800000 + (lastWriteDuplicates << 19) + pos);
								lastWriteDuplicates = 0;
							} else {
								out.write32(0xc0000000 + (lastWriteDuplicates << 19) + pos);
								lastWriteDuplicates = 0;
							}
						}
					}
					out.writeSize(out.position - startPosition);
					if (written >= mouseRecorder.length) {
						mouseRecorder.length = 0;
					} else {
						mouseRecorder.length -= written;
						for (int i = 0; i < mouseRecorder.length; i++) {
							mouseRecorder.x[i] = mouseRecorder.x[i + written];
							mouseRecorder.y[i] = mouseRecorder.y[i + written];
						}
					}
				}
			} else {
				mouseRecorder.length = 0;
			}
		}

		if (super.mousePressButton != 0) {
			long delta = (super.mousePressTime - prevMousePressTime) / 50L;
			if (delta > 4095L) {
				delta = 4095L;
			}
			prevMousePressTime = super.mousePressTime;
			int y = super.mousePressY;
			if (y < 0) {
				y = 0;
			} else if (y > 502) {
				y = 502;
			}
			int x = super.mousePressX;
			if (x < 0) {
				x = 0;
			} else if (x > 764) {
				x = 764;
			}
			int pos = (y * 765) + x;
			int button = 0;
			if (super.mousePressButton == 2) {
				button = 1;
			}
			int deltaInt = (int) delta;
			out.writeOp(241);
			out.write32((deltaInt << 20) + (button << 19) + pos);
		}

		if (sendCameraDelay > 0) {
			sendCameraDelay--;
		}

		if ((super.actionKey[1] == 1) || (super.actionKey[2] == 1) || (super.actionKey[3] == 1) || (super.actionKey[4] == 1)) {
			sendCamera = true;
		}

		if (sendCamera && (sendCameraDelay <= 0)) {
			sendCameraDelay = 20;
			sendCamera = false;
			out.writeOp(86);
			out.write16(orbitCameraPitch);
			out.write16A(orbitCameraYaw);
		}

		if (super.focused && !_focused) {
			_focused = true;
			out.writeOp(3);
			out.write8(1);
		}

		if (!super.focused && _focused) {
			_focused = false;
			out.writeOp(3);
			out.write8(0);
		}

		updateSceneState();
		updateTemporaryLocs();
		updateAudio();
		idleNetCycles++;
		if (idleNetCycles > 750) {
			tryReconnect();
		}
		updatePlayers();
		updateNPCs();
		updateEntityChats();
		delta++;

		if (crossMode != 0) {
			crossCycle += 20;
			if (crossCycle >= 400) {
				crossMode = 0;
			}
		}

		if (actionArea != 0) {
			actionCycles++;
			if (actionCycles >= 15) {
				if (actionArea == 2) {
					redrawInvback = true;
				}
				if (actionArea == 3) {
					redrawChatback = true;
				}
				actionArea = 0;
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
					hoveredSlotParentID = -1;
					handleInput();

					if ((hoveredSlotParentID == objDragComponentID) && (hoveredSlot != objDragSlot)) {
						Component component = Component.instances[objDragComponentID];

						// mode 0 = swap
						// mode 1 = insert
						int mode = 0;

						if ((bankArrangeMode == 1) && (component.contentType == 206)) {
							mode = 1;
						}

						if (component.invSlotObjID[hoveredSlot] <= 0) {
							mode = 0;
						}

						if (component.invMoveReplaces) {
							int src = objDragSlot;
							int dst = hoveredSlot;
							component.invSlotObjID[dst] = component.invSlotObjID[src];
							component.invSlotAmount[dst] = component.invSlotAmount[src];
							component.invSlotObjID[src] = -1;
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
						out.writeOp(214);
						out.write16LEA(objDragComponentID);
						out.write8C(mode);
						out.write16LEA(objDragSlot);
						out.write16LE(hoveredSlot);
					}
				} else if (((mouseButtonsOption == 1) || method17(menuSize - 1)) && (menuSize > 2)) {
					showContextMenu();
				} else if (menuSize > 0) {
					useMenuOption(menuSize - 1);
				}
				actionCycles = 10;
				super.mousePressButton = 0;
			}
		}

		if (Scene.clickTileX != -1) {
			int x = Scene.clickTileX;
			int z = Scene.clickTileZ;
			boolean success = tryMove(0, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, 0, 0, 0, 0, 0, true);
			Scene.clickTileX = -1;

			if (success) {
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

		handleMouseInput();
		handleMinimapInput();
		handleTabInput();
		handleChatSettingsInput();

		if ((super.mouseButton == 1) || (super.mousePressButton == 1)) {
			dragCycles++;
		}

		if (sceneState == 2) {
			updateOrbitCamera();
		}

		if ((sceneState == 2) && cinematic) {
			applyCinematicCamera();
		}

		for (int i = 0; i < 5; i++) {
			cameraAdjustPhase[i]++;
		}

		updateKeyboardInput();
		updateIdleCycles();

		heartbeatTimer++;

		if (heartbeatTimer > 50) {
			out.writeOp(0);
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
			out.writeOp(202);
		}
	}

	public void method63() {
		SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront();
		for (; loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
			if (loc.duration == -1) {
				loc.delay = 0;
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

	public void handleScrollInput(int left, int height, int mouseX, int mouseY, Component component, int top, boolean redraw, int scrollableHeight) {
		if (gripGrabbed) {
			gripThreshold = 32;
		} else {
			gripThreshold = 0;
		}

		gripGrabbed = false;

		if ((mouseX >= left) && (mouseX < (left + 16)) && (mouseY >= top) && (mouseY < (top + 16))) {
			component.scrollY -= dragCycles * 4;
			if (redraw) {
				redrawInvback = true;
			}
		} else if ((mouseX >= left) && (mouseX < (left + 16)) && (mouseY >= ((top + height) - 16)) && (mouseY < (top + height))) {
			component.scrollY += dragCycles * 4;
			if (redraw) {
				redrawInvback = true;
			}
		} else if ((mouseX >= (left - gripThreshold)) && (mouseX < (left + 16 + gripThreshold)) && (mouseY >= (top + 16)) && (mouseY < ((top + height) - 16)) && (dragCycles > 0)) {
			int gripSize = ((height - 32) * height) / scrollableHeight;
			if (gripSize < 8) {
				gripSize = 8;
			}
			int gripY = mouseY - top - 16 - (gripSize / 2);
			int maxY = height - 32 - gripSize;
			component.scrollY = ((scrollableHeight - height) * gripY) / maxY;
			if (redraw) {
				redrawInvback = true;
			}
			gripGrabbed = true;
		}
	}

	public boolean interactWithLoc(int bitset, int x, int z) {
		int locID = (bitset >> 14) & 0x7fff;
		int info = scene.getInfo(currentLevel, x, z, bitset);
		if (info == -1) {
			return false;
		}
		int type = info & 0x1f;
		int angle = (info >> 6) & 3;
		if ((type == 10) || (type == 11) || (type == 22)) {
			LocType loc = LocType.get(locID);
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
				int i2 = buffer.read24() + 6;
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
				out.writeOp(57);
				out.write16A(anInt1285);
				out.write16A(c);
				out.write16LE(anInt1283);
				out.write16A(anInt1284);
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
			out.writeOp(236);
			out.write16LE(b + sceneBaseTileZ);
			out.write16(c);
			out.write16LE(a + sceneBaseTileX);
		}
		if ((action == 62) && interactWithLoc(c, a, b)) {
			out.writeOp(192);
			out.write16(anInt1284);
			out.write16LE((c >> 14) & 0x7fff);
			out.write16LEA(b + sceneBaseTileZ);
			out.write16LE(anInt1283);
			out.write16LEA(a + sceneBaseTileX);
			out.write16(anInt1285);
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
			out.writeOp(25);
			out.write16LE(anInt1284);
			out.write16A(anInt1285);
			out.write16(c);
			out.write16A(b + sceneBaseTileZ);
			out.write16LEA(anInt1283);
			out.write16(a + sceneBaseTileX);
		}
		if (action == 74) {
			out.writeOp(122);
			out.write16LEA(b);
			out.write16A(a);
			out.write16LE(c);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 315) {
			Component component = Component.instances[b];
			boolean notify = true;
			if (component.contentType > 0) {
				notify = handleComponentAction(component);
			}
			if (notify) {
				out.writeOp(185);
				out.write16(b);
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
				out.writeOp(128);
				out.write16(c);
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
				out.writeOp(155);
				out.write16LE(c);
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
				out.writeOp(153);
				out.write16LE(c);
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
			out.writeOp(228);
			out.write16A((c >> 14) & 0x7fff);
			out.write16A(b + sceneBaseTileZ);
			out.write16(a + sceneBaseTileX);
		}
		if ((action == 679) && !aBoolean1149) {
			out.writeOp(40);
			out.write16(b);
			aBoolean1149 = true;
		}
		if (action == 431) {
			out.writeOp(129);
			out.write16A(a);
			out.write16(b);
			out.write16A(c);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
			out.writeOp(135);
			out.write16LE(a);
			out.write16A(b);
			out.write16LE(c);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 539) {
			out.writeOp(16);
			out.write16A(c);
			out.write16LEA(a);
			out.write16LEA(b);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
						out.writeOp(139);
						out.write16LE(playerIndices[j3]);
					}
					if (action == 6) {
						out.writeOp(128);
						out.write16(playerIndices[j3]);
					}
					flag9 = true;
					break;
				}
				if (!flag9) {
					addMessage(0, "", "Unable to find " + s7);
				}
			}
		}
		if (action == 870) {
			out.writeOp(53);
			out.write16(a);
			out.write16A(anInt1283);
			out.write16LEA(c);
			out.write16(anInt1284);
			out.write16LE(anInt1285);
			out.write16(b);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 847) {
			out.writeOp(87);
			out.write16A(c);
			out.write16(b);
			out.write16A(a);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
			out.writeOp(117);
			out.write16LEA(b);
			out.write16LEA(c);
			out.write16LE(a);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
				out.writeOp(73);
				out.write16LE(c);
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
			out.writeOp(79);
			out.write16LE(b + sceneBaseTileZ);
			out.write16(c);
			out.write16A(a + sceneBaseTileX);
		}
		if (action == 632) {
			out.writeOp(145);
			out.write16A(b);
			out.write16A(a);
			out.write16A(c);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 493) {
			out.writeOp(75);
			out.write16LEA(b);
			out.write16LE(a);
			out.write16A(c);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
			out.writeOp(156);
			out.write16A(a + sceneBaseTileX);
			out.write16LE(b + sceneBaseTileZ);
			out.write16LEA(c);
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
			out.writeOp(181);
			out.write16LE(b + sceneBaseTileZ);
			out.write16(c);
			out.write16LE(a + sceneBaseTileX);
			out.write16A(activeSpellComponent);
		}
		if (action == 646) {
			out.writeOp(185);
			out.write16(b);
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
				out.writeOp(17);
				out.write16LEA(c);
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
				out.writeOp(21);
				out.write16(c);
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
				out.writeOp(131);
				out.write16LEA(c);
				out.write16A(activeSpellComponent);
			}
		}
		if (action == 200) {
			closeInterfaces();
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
					addMessage(0, "", s9);
				}
			}
		}
		if (action == 900) {
			interactWithLoc(c, a, b);
			out.writeOp(252);
			out.write16LEA((c >> 14) & 0x7fff);
			out.write16LE(b + sceneBaseTileZ);
			out.write16A(a + sceneBaseTileX);
		}
		if (action == 412) {
			NPCEntity npc_6 = npcs[c];
			if (npc_6 != null) {
				tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc_6.pathTileX[0], npc_6.pathTileZ[0], 0, 1, 1, 0, 0, false);
				crossX = super.mousePressX;
				crossY = super.mousePressY;
				crossMode = 2;
				crossCycle = 0;
				out.writeOp(72);
				out.write16A(c);
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
				out.writeOp(249);
				out.write16A(c);
				out.write16LE(activeSpellComponent);
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
				out.writeOp(39);
				out.write16LE(c);
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
				out.writeOp(139);
				out.write16LE(c);
			}
		}
		if ((action == 956) && interactWithLoc(c, a, b)) {
			out.writeOp(35);
			out.write16LE(a + sceneBaseTileX);
			out.write16A(activeSpellComponent);
			out.write16A(b + sceneBaseTileZ);
			out.write16LE((c >> 14) & 0x7fff);
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
			out.writeOp(23);
			out.write16LE(b + sceneBaseTileZ);
			out.write16LE(c);
			out.write16LE(a + sceneBaseTileX);
		}
		if (action == 867) {
			out.writeOp(43);
			out.write16LE(b);
			out.write16A(c);
			out.write16A(a);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 543) {
			out.writeOp(237);
			out.write16(a);
			out.write16A(c);
			out.write16(b);
			out.write16A(activeSpellComponent);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
			}
		}
		if (action == 606) {
			String s2 = menuOption[option];
			int j2 = s2.indexOf("@whi@");
			if (j2 != -1) {
				if (viewportComponentID == -1) {
					closeInterfaces();
					aString881 = s2.substring(j2 + 5).trim();
					aBoolean1158 = false;
					for (int i3 = 0; i3 < Component.instances.length; i3++) {
						if ((Component.instances[i3] == null) || (Component.instances[i3].contentType != 600)) {
							continue;
						}
						anInt1178 = viewportComponentID = Component.instances[i3].parentID;
						break;
					}
				} else {
					addMessage(0, "", "Please close the interface you have open before using 'report abuse'");
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
				out.writeOp(14);
				out.write16A(anInt1284);
				out.write16(c);
				out.write16(anInt1285);
				out.write16LE(anInt1283);
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
			out.writeOp(41);
			out.write16(c);
			out.write16A(a);
			out.write16A(b);
			actionCycles = 0;
			actionComponentID = b;
			actionInvSlot = a;
			actionArea = 2;
			if (Component.instances[b].parentID == viewportComponentID) {
				actionArea = 1;
			}
			if (Component.instances[b].parentID == chatbackComponentID) {
				actionArea = 3;
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
				out.writeOp(18);
				out.write16LE(c);
			}
		}
		if (action == 113) {
			interactWithLoc(c, a, b);
			out.writeOp(70);
			out.write16LE(a + sceneBaseTileX);
			out.write16(b + sceneBaseTileZ);
			out.write16LEA((c >> 14) & 0x7fff);
		}
		if (action == 872) {
			interactWithLoc(c, a, b);
			out.writeOp(234);
			out.write16LEA(a + sceneBaseTileX);
			out.write16A((c >> 14) & 0x7fff);
			out.write16LEA(b + sceneBaseTileZ);
		}
		if (action == 502) {
			interactWithLoc(c, a, b);
			out.writeOp(132);
			out.write16LEA(a + sceneBaseTileX);
			out.write16((c >> 14) & 0x7fff);
			out.write16A(b + sceneBaseTileZ);
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
			addMessage(0, "", s5);
		}
		if (action == 169) {
			out.writeOp(185);
			out.write16(b);
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
			addMessage(0, "", s10);
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
			out.writeOp(253);
			out.write16LE(a + sceneBaseTileX);
			out.write16LEA(b + sceneBaseTileZ);
			out.write16A(c);
		}
		if (action == 1448) {
			ObjType type_1 = ObjType.get(c);
			String s6;
			if (type_1.examine != null) {
				s6 = new String(type_1.examine);
			} else {
				s6 = "It's a " + type_1.name + ".";
			}
			addMessage(0, "", s6);
		}
		anInt1282 = 0;
		anInt1136 = 0;
		redrawInvback = true;
	}

	public void updateChatOverride() {
		overrideChat = 0;
		int worldTileX = (localPlayer.x >> 7) + sceneBaseTileX;
		int worldTileZ = (localPlayer.z >> 7) + sceneBaseTileZ;

		if ((worldTileX >= 3053) && (worldTileX <= 3156) && (worldTileZ >= 3056) && (worldTileZ <= 3136)) {
			overrideChat = 1;
		}

		if ((worldTileX >= 3072) && (worldTileX <= 3118) && (worldTileZ >= 9492) && (worldTileZ <= 9535)) {
			overrideChat = 1;
		}

		if ((overrideChat == 1) && (worldTileX >= 3139) && (worldTileX <= 3199) && (worldTileZ >= 3008) && (worldTileZ <= 3062)) {
			overrideChat = 0;
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
			int locID = (bitset >> 14) & 0x7fff;
			if (bitset == j) {
				continue;
			}
			j = bitset;
			if ((k1 == 2) && (scene.getInfo(currentLevel, i1, j1, bitset) >= 0)) {
				LocType loc = LocType.get(locID);

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
				NPCEntity npc = npcs[locID];
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
				method87(npc.type, locID, j1, i1);
			}
			if (k1 == 0) {
				PlayerEntity player = players[locID];
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
				method88(i1, locID, player, j1);
			}
			if (k1 == 3) {
				DoublyLinkedList list = levelObjStacks[currentLevel][i1][j1];
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
		System.out.println("draw-state:" + getDrawState());
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

			if ((viewportComponentID != -1) && (viewportComponentID == anInt1178)) {
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
						out.writeOp(126);
						out.write8(0);
						int k = out.position;
						out.write64(aLong953);
						ChatCompression.pack(socialInput, out);
						out.writeSize(out.position - k);
						socialInput = ChatCompression.method527(socialInput);
						socialInput = Censor.apply(socialInput);
						addMessage(6, StringUtil.formatName(StringUtil.fromBase37(aLong953)), socialInput);
						if (privateChatSetting == 2) {
							privateChatSetting = 1;
							redrawPrivacySettings = true;
							out.writeOp(95);
							out.write8(publicChatSetting);
							out.write8(privateChatSetting);
							out.write8(tradeChatSetting);
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
						out.writeOp(208);
						out.write32(i1);
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
						out.writeOp(60);
						out.write64(StringUtil.toBase37(chatbackInput));
					}
					chatbackInputType = 0;
					redrawChatback = true;
				}
			} else if (chatbackComponentID == -1) {
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
										levelCollisionMap[k1].flags[i2][k2] = 0;
									}
								}
							}
						}
					}
					if (chatTyped.startsWith("::")) {
						out.writeOp(103);
						out.write8(chatTyped.length() - 1);
						out.writeString(chatTyped.substring(2));
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
						out.writeOp(4);
						out.write8(0);
						int j3 = out.position;
						out.write8S(i3);
						out.write8S(j2);
						chatBuffer.position = 0;
						ChatCompression.pack(chatTyped, chatBuffer);
						out.writeBytesA(chatBuffer.data, 0, chatBuffer.position);
						out.writeSize(out.position - j3);
						chatTyped = ChatCompression.method527(chatTyped);
						chatTyped = Censor.apply(chatTyped);
						localPlayer.chat = chatTyped;
						localPlayer.chatColor = j2;
						localPlayer.chatStyle = i3;
						localPlayer.chatTimer = 150;
						if (rights == 2) {
							addMessage(2, "@cr2@" + localPlayer.name, localPlayer.chat);
						} else if (rights == 1) {
							addMessage(2, "@cr1@" + localPlayer.name, localPlayer.chat);
						} else {
							addMessage(2, localPlayer.name, localPlayer.chat);
						}
						if (publicChatSetting == 2) {
							publicChatSetting = 3;
							redrawPrivacySettings = true;
							out.writeOp(95);
							out.write8(publicChatSetting);
							out.write8(privateChatSetting);
							out.write8(tradeChatSetting);
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
			int k1 = (70 - (l * 14)) + chatScrollOffset + 4;
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
			} else if (friendWorld[type] == nodeID) {
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
			component.scrollableHeight = (i1 * 15) + 20;
			if (component.scrollableHeight <= component.height) {
				component.scrollableHeight = component.height + 1;
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
			component.scrollableHeight = (ignoreCount * 15) + 20;
			if (component.scrollableHeight <= component.height) {
				component.scrollableHeight = component.height + 1;
			}
			return;
		}

		if (type == 327) {
			component.modelPitch = 150;
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
				model.applyTransform(SeqType.instances[localPlayer.seqStandID].transformIndices[0]);
				model.calculateNormals(64, 850, -30, -50, -30, true);

				component.modelCategory = 5;
				component.modelID = 0;
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
			if (lastAddress != 0) {
				String s;
				if (daysSinceLastLogin == 0) {
					s = "earlier today";
				} else if (daysSinceLastLogin == 1) {
					s = "yesterday";
				} else {
					s = daysSinceLastLogin + " days ago";
				}
				component.text = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				component.text = "";
			}
		}
		if (type == 651) {
			if (unreadMessages == 0) {
				component.text = "0 unread messages";
				component.color = 0xffff00;
			}
			if (unreadMessages == 1) {
				component.text = "1 unread message";
				component.color = 65280;
			}
			if (unreadMessages > 1) {
				component.text = unreadMessages + " unread messages";
				component.color = 65280;
			}
		}
		if (type == 652) {
			if (daysSinceRecoveriesChanged == 201) {
				if (warnMembersInNonMembers == 1) {
					component.text = "@yel@This is a non-members world: @whi@Since you are a member we";
				} else {
					component.text = "";
				}
			} else if (daysSinceRecoveriesChanged == 200) {
				component.text = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (daysSinceRecoveriesChanged == 0) {
					s1 = "Earlier today";
				} else if (daysSinceRecoveriesChanged == 1) {
					s1 = "Yesterday";
				} else {
					s1 = daysSinceRecoveriesChanged + " days ago";
				}
				component.text = s1 + " you changed your recovery questions";
			}
		}
		if (type == 653) {
			if (daysSinceRecoveriesChanged == 201) {
				if (warnMembersInNonMembers == 1) {
					component.text = "@whi@recommend you use a members world instead. You may use";
				} else {
					component.text = "";
				}
			} else if (daysSinceRecoveriesChanged == 200) {
				component.text = "We strongly recommend you do so now to secure your account.";
			} else {
				component.text = "If you do not remember making this change then cancel it immediately";
			}
		}
		if (type == 654) {
			if (daysSinceRecoveriesChanged == 201) {
				if (warnMembersInNonMembers == 1) {
					component.text = "@whi@this world but member benefits are unavailable whilst here.";
				} else {
					component.text = "";
				}
				return;
			}
			if (daysSinceRecoveriesChanged == 200) {
				component.text = "Do this from the 'account management' area on our front webpage";
				return;
			}
			component.text = "Do this from the 'account management' area on our front webpage";
		}
	}

	public void drawPrivateMessages() {
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

	public void addMessage(int type, String prefix, String message) {
		if ((type == 0) && (stickyChatbackComponentID != -1)) {
			chatbackMessage = message;
			super.mousePressButton = 0;
		}
		if (chatbackComponentID == -1) {
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

	public void handleTabInput() {
		if (super.mousePressButton == 1) {
			if ((super.mousePressX >= 539) && (super.mousePressX <= 573) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (tabComponentIDs[0] != -1)) {
				redrawInvback = true;
				selectedTab = 0;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 569) && (super.mousePressX <= 599) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentIDs[1] != -1)) {
				redrawInvback = true;
				selectedTab = 1;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 597) && (super.mousePressX <= 627) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentIDs[2] != -1)) {
				redrawInvback = true;
				selectedTab = 2;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 625) && (super.mousePressX <= 669) && (super.mousePressY >= 168) && (super.mousePressY < 203) && (tabComponentIDs[3] != -1)) {
				redrawInvback = true;
				selectedTab = 3;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 666) && (super.mousePressX <= 696) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentIDs[4] != -1)) {
				redrawInvback = true;
				selectedTab = 4;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 694) && (super.mousePressX <= 724) && (super.mousePressY >= 168) && (super.mousePressY < 205) && (tabComponentIDs[5] != -1)) {
				redrawInvback = true;
				selectedTab = 5;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 722) && (super.mousePressX <= 756) && (super.mousePressY >= 169) && (super.mousePressY < 205) && (tabComponentIDs[6] != -1)) {
				redrawInvback = true;
				selectedTab = 6;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 540) && (super.mousePressX <= 574) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (tabComponentIDs[7] != -1)) {
				redrawInvback = true;
				selectedTab = 7;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 572) && (super.mousePressX <= 602) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentIDs[8] != -1)) {
				redrawInvback = true;
				selectedTab = 8;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 599) && (super.mousePressX <= 629) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentIDs[9] != -1)) {
				redrawInvback = true;
				selectedTab = 9;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 627) && (super.mousePressX <= 671) && (super.mousePressY >= 467) && (super.mousePressY < 502) && (tabComponentIDs[10] != -1)) {
				redrawInvback = true;
				selectedTab = 10;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 669) && (super.mousePressX <= 699) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentIDs[11] != -1)) {
				redrawInvback = true;
				selectedTab = 11;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 696) && (super.mousePressX <= 726) && (super.mousePressY >= 466) && (super.mousePressY < 503) && (tabComponentIDs[12] != -1)) {
				redrawInvback = true;
				selectedTab = 12;
				redrawSideicons = true;
			}
			if ((super.mousePressX >= 724) && (super.mousePressX <= 758) && (super.mousePressY >= 466) && (super.mousePressY < 502) && (tabComponentIDs[13] != -1)) {
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
			int i1 = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
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

	public void handleInput() {
		if (objDragArea != 0) {
			return;
		}

		menuOption[0] = "Cancel";
		menuAction[0] = 1107;
		menuSize = 1;

		handlePrivateChatInput();

		lastHoveredComponentID = 0;

		if ((super.mouseX > 4) && (super.mouseY > 4) && (super.mouseX < 516) && (super.mouseY < 338)) {
			if (viewportComponentID != -1) {
				handleParentComponentInput(4, Component.instances[viewportComponentID], super.mouseX, 4, super.mouseY, 0);
			} else {
				handleViewportInput();
			}
		}

		if (lastHoveredComponentID != viewportHoveredComponentID) {
			viewportHoveredComponentID = lastHoveredComponentID;
		}

		lastHoveredComponentID = 0;
		if ((super.mouseX > 553) && (super.mouseY > 205) && (super.mouseX < 743) && (super.mouseY < 466)) {
			if (invbackComponentID != -1) {
				handleParentComponentInput(553, Component.instances[invbackComponentID], super.mouseX, 205, super.mouseY, 0);
			} else if (tabComponentIDs[selectedTab] != -1) {
				handleParentComponentInput(553, Component.instances[tabComponentIDs[selectedTab]], super.mouseX, 205, super.mouseY, 0);
			}
		}

		if (lastHoveredComponentID != invbackHoveredComponentID) {
			redrawInvback = true;
			invbackHoveredComponentID = lastHoveredComponentID;
		}

		lastHoveredComponentID = 0;

		if ((super.mouseX > 17) && (super.mouseY > 357) && (super.mouseX < 496) && (super.mouseY < 453)) {
			if (chatbackComponentID != -1) {
				handleParentComponentInput(17, Component.instances[chatbackComponentID], super.mouseX, 357, super.mouseY, 0);
			} else if ((super.mouseY < 434) && (super.mouseX < 426)) {
				handleChatInput(super.mouseY - 357);
			}
		}

		if ((chatbackComponentID != -1) && (lastHoveredComponentID != chatbackHoveredComponentID)) {
			redrawChatback = true;
			chatbackHoveredComponentID = lastHoveredComponentID;
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

	public int mix2(int src, int dst, int alpha) {
		int invAlpha = 256 - alpha;
		int sR = (src & 0xFF0000) * invAlpha;
		int sG = (src & 0x00FF00)  * invAlpha;
		int sB = (src & 0x0000FF) * invAlpha;
		int dR = (dst & 0xFF0000) * alpha;
		int dG = (dst & 0x00FF00)  * alpha;
		int dB = (dst & 0x0000FF) * alpha;
		int finalR = (sR + dR) & 0xFF000000;
		int finalG = (sG + dG) & 0x00FF0000;
		int finalB = (sB + dB) & 0x0000FF00;
		return (finalR + finalG + finalB) >> 8;
	}

	public int mix(int src, int dst, int alpha) {
		int invAlpha = 256 - alpha;

		//                 0xAARRGGBB
		int srcRB = (src & 0x00FF00FF) * invAlpha; // mul R and B (invAlpha of 256 is the same as << 8)
		//                 0xRRR0BBB0
		// This multiplication causes the channels to shift. Since it most likely shifts on the magnitude of bits and
		// not bytes, this will cause the channels to be contained within 2 bytes. You can imagine the first byte being
		// its whole value, and the second byte being its 'fractional' value. Like a fixed-integer.

		//                 0xAARRGGBB
		int srcG =  (src & 0x0000FF00) * invAlpha; // mul G
		//                 0x00GGG000
		// same as above but with only a single channel

		// same as above, just with alpha instead of invAlpha
		int dstRB = (dst & 0x00FF00FF) * alpha;
		int dstG =  (dst & 0x0000FF00) * alpha;

		// Then we add the products together and trim off the fractional bits. Now it's just the Red and Blue channels
		// left shifted by 8.           0xRRR0BBB0
		//                      becomes 0xRR00BB00
		int finalRB = (srcRB + dstRB) & 0xFF00FF00;

		//                           0x00GGG000
		//                   becomes 0x00GG0000
		int finalG = (srcG + dstG) & 0x00FF0000;

		// Now we just add the channels back to each other
		//                      0xRR00BB00
		//                    + 0x00GG0000
		//                    = 0xRRGGBB00
		// And the result is our RGB channels left shifted by 8.
		return (finalRB + finalG) >> 8;
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
			out.write8(14);
			out.write8(i);
			connection.write(out.data, 0, 2);
			for (int j = 0; j < 8; j++) {
				connection.read();
			}
			int k = connection.read();
			int i1 = k;
			if (k == 0) {
				connection.read(in.data, 0, 8);
				in.position = 0;
				aLong1215 = in.read64();

				// apache math tries to fill the remaining 1008 bytes up with random junk if we don't give it 256 ints.
				int[] seed = new int[1 << 8];

				seed[0] = (int) (Math.random() * 99999999D);
				seed[1] = (int) (Math.random() * 99999999D);
				seed[2] = (int) (aLong1215 >> 32);
				seed[3] = (int) aLong1215;
				out.position = 0;
				out.write8(10);
				out.write32(seed[0]);
				out.write32(seed[1]);
				out.write32(seed[2]);
				out.write32(seed[3]);
				out.write32(Signlink.uid);
				out.writeString(username);
				out.writeString(password);
				out.encrypt(RSA_EXPONENT, RSA_MODULUS);
				login.position = 0;
				if (reconnect) {
					login.write8(18);
				} else {
					login.write8(16);
				}
				login.write8(out.position + 36 + 1 + 1 + 2);
				login.write8(255);
				login.write16(317);
				login.write8(lowmem ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++) {
					login.write32(archiveChecksum[l1]);
				}
				login.writeBytes(out.data, 0, out.position);
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
				flagged = connection.read() == 1;
				prevMousePressTime = 0L;
				lastWriteDuplicates = 0;
				mouseRecorder.length = 0;
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
				idleNetCycles = 0;
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
				orbitCameraYaw = ((int) (Math.random() * 20D) - 10) & 0x7ff;
				minimapState = 0;
				minimapLevel = -1;
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
				projectiles.clear();
				spotanims.clear();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++) {
							levelObjStacks[l2][i3][k3] = null;
						}
					}
				}
				temporaryLocs = new DoublyLinkedList();
				socialState = 0;
				friendCount = 0;
				stickyChatbackComponentID = -1;
				chatbackComponentID = -1;
				viewportComponentID = -1;
				invbackComponentID = -1;
				viewportOverlayComponentID = -1;
				aBoolean1149 = false;
				selectedTab = 3;
				chatbackInputType = 0;
				menuVisible = false;
				showSocialInput = false;
				chatbackMessage = null;
				multizone = 0;
				flashingTab = -1;
				designGender = true;
				validateCharacterDesign();
				for (int j3 = 0; j3 < 5; j3++) {
					designColors[j3] = 0;
				}
				for (int l3 = 0; l3 < 5; l3++) {
					playerOptions[l3] = null;
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
				idleNetCycles = 0;
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
		int[][] flags = levelCollisionMap[currentLevel].flags;

		while (length != steps) {
			x = bfsStepX[length];
			z = bfsStepZ[length];
			length = (length + 1) % bufferSize;

			if ((x == dx) && (z == dz)) {
				arrived = true;
				break;
			}

			if (locType != 0) {
				if (((locType < 5) || (locType == 10)) && levelCollisionMap[currentLevel].method219(x, z, dx, dz, locAngle, locType - 1)) {
					arrived = true;
					break;
				}
				if ((locType < 10) && levelCollisionMap[currentLevel].method220(x, z, dx, dz, locType - 1, locAngle)) {
					arrived = true;
					break;
				}
			}

			if ((locWidth != 0) && (locLength != 0) && levelCollisionMap[currentLevel].method221(x, z, dx, dz, locWidth, locLength, locInteractionFlags)) {
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
				out.writeOp(164);
				out.write8(count + count + 3);
			} else if (type == 1) {
				out.writeOp(248);
				out.write8(count + count + 3 + 14);
			} else if (type == 2) {
				out.writeOp(98);
				out.write8(count + count + 3);
			}

			out.write16LEA(startX + sceneBaseTileX);

			flagSceneTileX = bfsStepX[0];
			flagSceneTileZ = bfsStepZ[0];

			for (int i = 1; i < count; i++) {
				length--;
				out.write8(bfsStepX[length] - startX);
				out.write8(bfsStepZ[length] - startZ);
			}

			out.write16LE(startZ + sceneBaseTileZ);
			out.write8C((super.actionKey[5] != 1) ? 0 : 1);
			return true;
		}

		return type != 1;
	}

	public void method86(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			NPCEntity npc = npcs[k];
			int l = buffer.read8U();

			if ((l & 0x10) != 0) {
				int seqID = buffer.read16ULE();

				if (seqID == 65535) {
					seqID = -1;
				}

				int delay = buffer.read8U();
				if ((seqID == npc.primarySeqID) && (seqID != -1)) {
					int style = SeqType.instances[seqID].replayStyle;

					if (style == 0) {
						npc.primarySeqFrame = 0;
						npc.primarySeqCycle = 0;
						npc.primarySeqDelay = delay;
						npc.primarySeqLoop = 0;
					}

					if (style == 1) {
						npc.primarySeqLoop = 0;
					}
				} else if ((seqID == -1) || (npc.primarySeqID == -1) || (SeqType.instances[seqID].priority >= SeqType.instances[npc.primarySeqID].priority)) {
					npc.primarySeqID = seqID;
					npc.primarySeqFrame = 0;
					npc.primarySeqCycle = 0;
					npc.primarySeqDelay = delay;
					npc.primarySeqLoop = 0;
					npc.seqPathLength = npc.pathLength;
				}
			}
			if ((l & 8) != 0) {
				int j1 = buffer.read8UA();
				int j2 = buffer.read8UC();
				npc.method447(j2, j1, loopCycle);
				npc.combatCycle = loopCycle + 300;
				npc.health = buffer.read8UA();
				npc.totalHealth = buffer.read8U();
			}
			if ((l & 0x80) != 0) {
				npc.spotanim = buffer.read16U();
				int k1 = buffer.read32();
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
				npc.index = buffer.read16U();
				if (npc.index == 65535) {
					npc.index = -1;
				}
			}
			if ((l & 1) != 0) {
				npc.chat = buffer.readString();
				npc.chatTimer = 100;
			}
			if ((l & 0x40) != 0) {
				int l1 = buffer.read8UC();
				int k2 = buffer.read8US();
				npc.method447(k2, l1, loopCycle);
				npc.combatCycle = loopCycle + 300;
				npc.health = buffer.read8US();
				npc.totalHealth = buffer.read8UC();
			}
			if ((l & 2) != 0) {
				npc.type = NPCType.get(buffer.read16ULEA());
				npc.size = npc.type.size;
				npc.turnSpeed = npc.type.turnSpeed;
				npc.seqWalkID = npc.type.seqWalkID;
				npc.seqTurnAroundID = npc.type.seqTurnAroundID;
				npc.seqTurnLeftID = npc.type.seqTurnLeftID;
				npc.seqTurnRightID = npc.type.seqTurnRightID;
				npc.seqStandID = npc.type.seqStandID;
			}
			if ((l & 4) != 0) {
				npc.faceTileX = buffer.read16ULE();
				npc.faceTileZ = buffer.read16ULE();
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
			s = s + getCombatLevelColorTag(localPlayer.combatLevel, type.level) + " (level-" + type.level + ")";
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

		String caption;

		if (player.skillLevel == 0) {
			caption = player.name + getCombatLevelColorTag(localPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
		} else {
			caption = player.name + " (skill-" + player.skillLevel + ")";
		}

		if (anInt1282 == 1) {
			menuOption[menuSize] = "Use " + aString1286 + " with @whi@" + caption;
			menuAction[menuSize] = 491;
			menuParamC[menuSize] = j;
			menuParamA[menuSize] = i;
			menuParamB[menuSize] = k;
			menuSize++;
		} else if (anInt1136 == 1) {
			if ((activeSpellFlags & 8) == 8) {
				menuOption[menuSize] = spellCaption + " @whi@" + caption;
				menuAction[menuSize] = 365;
				menuParamC[menuSize] = j;
				menuParamA[menuSize] = i;
				menuParamB[menuSize] = k;
				menuSize++;
			}
		} else {
			for (int l = 4; l >= 0; l--) {
				if (playerOptions[l] != null) {
					menuOption[menuSize] = playerOptions[l] + " @whi@" + caption;
					char c = '\0';
					if (playerOptions[l].equalsIgnoreCase("attack")) {
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
				menuOption[i1] = "Walk here @whi@" + caption;
				return;
			}
		}
	}

	public void storeLoc(SceneLocTemporary loc) {
		int bitset = 0;
		int locID = -1;
		int kind = 0;
		int rotation = 0;
		if (loc.classID == 0) {
			bitset = scene.getWallBitset(loc.level, loc.localX, loc.localZ);
		}
		if (loc.classID == 1) {
			bitset = scene.getWallDecorationBitset(loc.level, loc.localX, loc.localZ);
		}
		if (loc.classID == 2) {
			bitset = scene.getLocBitset(loc.level, loc.localX, loc.localZ);
		}
		if (loc.classID == 3) {
			bitset = scene.getGroundDecorationBitset(loc.level, loc.localX, loc.localZ);
		}
		if (bitset != 0) {
			int info = scene.getInfo(loc.level, loc.localX, loc.localZ, bitset);
			locID = (bitset >> 14) & 0x7fff;
			kind = info & 0x1f;
			rotation = info >> 6;
		}
		loc.previousLocID = locID;
		loc.previousKind = kind;
		loc.previousRotation = rotation;
	}

	public void updateAudio() {
		for (int i = 0; i < waveCount; i++) {
			if (waveDelay[i] > 0) {
				waveDelay[i]--;
			} else {
				boolean failed = false;

				try {
					if ((waveIDs[i] == lastWaveID) && (waveLoops[i] == lastWaveLoops)) {
						if (!wavereplay()) {
							failed = true;
						}
					} else {
						Buffer buffer = SoundTrack.generate(waveLoops[i], waveIDs[i]);

						// the sample rate is 22050Hz and sample size is 1 byte which means dividing the bytes by 22 is
						// roughly converting the bytes to time in milliseconds
						if ((System.currentTimeMillis() + (long) (buffer.position / 22)) > (lastWaveStartTime + (long) (lastWaveLength / 22))) {
							lastWaveLength = buffer.position;
							lastWaveStartTime = System.currentTimeMillis();

							if (wavesave(buffer.data, buffer.position)) {
								lastWaveID = waveIDs[i];
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
						waveIDs[j] = waveIDs[j + 1];
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
			int j = buffer.readN(11);
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
			int k = buffer.readN(1);
			if (k == 1) {
				anIntArray894[anInt893++] = j;
			}
			int l = buffer.readN(1);
			int i1 = buffer.readN(5);
			if (i1 > 15) {
				i1 -= 32;
			}
			int j1 = buffer.readN(5);
			if (j1 > 15) {
				j1 -= 32;
			}
			player.move(localPlayer.pathTileX[0] + j1, localPlayer.pathTileZ[0] + i1, l == 1);
		}
		buffer.accessBytes();
	}

	public void handleMinimapInput() {
		if (minimapState != 0) {
			return;
		}
		if (super.mousePressButton == 1) {
			int x = super.mousePressX - 25 - 550;
			int y = super.mousePressY - 5 - 4;
			if ((x >= 0) && (y >= 0) && (x < 146) && (y < 151)) {
				x -= 73;
				y -= 75;
				int yaw = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
				int i1 = Draw3D.sin[yaw];
				int j1 = Draw3D.cos[yaw];
				i1 = (i1 * (minimapZoom + 256)) >> 8;
				j1 = (j1 * (minimapZoom + 256)) >> 8;
				int k1 = ((y * i1) + (x * j1)) >> 11;
				int l1 = ((y * j1) - (x * i1)) >> 11;
				int i2 = (localPlayer.x + k1) >> 7;
				int j2 = (localPlayer.z - l1) >> 7;
				boolean flag1 = tryMove(1, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], i2, j2, 0, 0, 0, 0, 0, true);
				if (flag1) {
					out.write8(x);
					out.write8(y);
					out.write16(orbitCameraYaw);
					out.write8(57);
					out.write8(minimapAnticheatAngle);
					out.write8(minimapZoom);
					out.write8(89);
					out.write16(localPlayer.x);
					out.write16(localPlayer.z);
					out.write8(tryMoveNearest);
					out.write8(63);
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

	public void updateNPCs() {
		for (int i = 0; i < npcCount; i++) {
			int npcID = npcIndices[i];
			NPCEntity npc = npcs[npcID];
			if (npc != null) {
				updateEntity(npc);
			}
		}
	}

	public void updateEntity(PathingEntity entity) {
		if ((entity.x < 128) || (entity.z < 128) || (entity.x >= 13184) || (entity.z >= 13184)) {
			entity.primarySeqID = -1;
			entity.spotanim = -1;
			entity.forceMoveEndCycle = 0;
			entity.forceMoveStartCycle = 0;
			entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
			entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
			entity.resetPath();
		}
		if ((entity == localPlayer) && ((entity.x < 1536) || (entity.z < 1536) || (entity.x >= 11776) || (entity.z >= 11776))) {
			entity.primarySeqID = -1;
			entity.spotanim = -1;
			entity.forceMoveEndCycle = 0;
			entity.forceMoveStartCycle = 0;
			entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
			entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
			entity.resetPath();
		}
		if (entity.forceMoveEndCycle > loopCycle) {
			updateForceMovement(entity);
		} else if (entity.forceMoveStartCycle >= loopCycle) {
			startForceMovement(entity);
		} else {
			updateMovement(entity);
		}
		updateFacingDirection(entity);
		updateSequences(entity);
	}

	public void updateForceMovement(PathingEntity entity) {
		int delta = entity.forceMoveEndCycle - loopCycle;
		int dstX = (entity.forceMoveStartSceneTileX * 128) + (entity.size * 64);
		int dstZ = (entity.forceMoveStartSceneTileZ * 128) + (entity.size * 64);

		entity.x += (dstX - entity.x) / delta;
		entity.z += (dstZ - entity.z) / delta;

		entity.seqTrigger = 0;

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



	public void startForceMovement(PathingEntity entity) {
		if ((entity.forceMoveStartCycle == loopCycle) || (entity.primarySeqID == -1) || (entity.primarySeqDelay != 0) || ((entity.primarySeqCycle + 1) > SeqType.instances[entity.primarySeqID].getFrameDuration(entity.primarySeqFrame))) {
			int duration = entity.forceMoveStartCycle - entity.forceMoveEndCycle;
			int delta = loopCycle - entity.forceMoveEndCycle;
			int dx0 = (entity.forceMoveStartSceneTileX * 128) + (entity.size * 64);
			int dz0 = (entity.forceMoveStartSceneTileZ * 128) + (entity.size * 64);
			int dx1 = (entity.forceMoveEndSceneTileX * 128) + (entity.size * 64);
			int dz1 = (entity.forceMoveEndSceneTileZ * 128) + (entity.size * 64);
			entity.x = ((dx0 * (duration - delta)) + (dx1 * delta)) / duration;
			entity.z = ((dz0 * (duration - delta)) + (dz1 * delta)) / duration;
		}

		entity.seqTrigger = 0;

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

	public void updateMovement(PathingEntity entity) {
		entity.secondarySeqID = entity.seqStandID;

		if (entity.pathLength == 0) {
			entity.seqTrigger = 0;
			return;
		}

		if ((entity.primarySeqID != -1) && (entity.primarySeqDelay == 0)) {
			SeqType seq = SeqType.instances[entity.primarySeqID];

			// if we're moving, and our move style is 0:
			//      Move faster[, and look a tile/entity if applicable.] (Side effects of seqTrigger)
			if ((entity.seqPathLength > 0) && (seq.moveStyle == 0)) {
				entity.seqTrigger++;
				return;
			}

			// if we're stationary, and our idle style is 0:
			//      Look at a tile/entity if applicable. (Side effect of seqTrigger)
			if ((entity.seqPathLength <= 0) && (seq.idleStyle == 0)) {
				entity.seqTrigger++;
				return;
			}
		}

		int x = entity.x;
		int z = entity.z;
		int dstX = (entity.pathTileX[entity.pathLength - 1] * 128) + (entity.size * 64);
		int dstZ = (entity.pathTileZ[entity.pathLength - 1] * 128) + (entity.size * 64);

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

		int seqID = entity.seqTurnAroundID;

		// Since the game uses a left-handed coordinate system, an increasing angle goes clockwise.
		// See PreviewSinCos2D

		// yaw >= -45 deg && yaw <= 45 deg
		if ((remainingYaw >= -256) && (remainingYaw <= 256)) {
			seqID = entity.seqWalkID;
		}
		// yaw >= 45 deg && yaw <= 135 deg
		else if ((remainingYaw >= 256) && (remainingYaw < 768)) {
			seqID = entity.seqTurnRightID;
		}
		// yaw >= -135 deg && yaw <= -45 deg
		else if ((remainingYaw >= -768) && (remainingYaw <= -256)) {
			seqID = entity.seqTurnLeftID;
		}

		if (seqID == -1) {
			seqID = entity.seqWalkID;
		}

		entity.secondarySeqID = seqID;

		int moveSpeed = 4;

		if ((entity.yaw != entity.dstYaw) && (entity.index == -1) && (entity.turnSpeed != 0)) {
			moveSpeed = 2;
		}

		if (entity.pathLength > 2) {
			moveSpeed = 6;
		}

		if (entity.pathLength > 3) {
			moveSpeed = 8;
		}

		if ((entity.seqTrigger > 0) && (entity.pathLength > 1)) {
			moveSpeed = 8;
			entity.seqTrigger--;
		}

		if (entity.pathRunning[entity.pathLength - 1]) {
			moveSpeed <<= 1;
		}

		if ((moveSpeed >= 8) && (entity.secondarySeqID == entity.seqWalkID) && (entity.seqRunID != -1)) {
			entity.secondarySeqID = entity.seqRunID;
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
			entity.pathLength--;
			if (entity.seqPathLength > 0) {
				entity.seqPathLength--;
			}
		}
	}

	public void updateFacingDirection(PathingEntity e) {
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
			int id = e.index - 32768;

			if (id == localPID) {
				id = LOCAL_PLAYER_INDEX;
			}

			PlayerEntity player = players[id];

			if (player != null) {
				int dstX = e.x - player.x;
				int dstZ = e.z - player.z;

				if ((dstX != 0) || (dstZ != 0)) {
					e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
				}
			}
		}

		if (((e.faceTileX != 0) || (e.faceTileZ != 0)) && ((e.pathLength == 0) || (e.seqTrigger > 0))) {
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

			if ((e.secondarySeqID == e.seqStandID) && (e.yaw != e.dstYaw)) {
				if (e.seqTurnID != -1) {
					e.secondarySeqID = e.seqTurnID;
					return;
				}

				e.secondarySeqID = e.seqWalkID;
			}
		}
	}

	public void updateSequences(PathingEntity e) {
		e.aBoolean1541 = false;

		if (e.secondarySeqID != -1) {
			SeqType seq = SeqType.instances[e.secondarySeqID];
			e.secondarySeqCycle++;

			if ((e.secondarySeqFrame < seq.frameCount) && (e.secondarySeqCycle > seq.getFrameDuration(e.secondarySeqFrame))) {
				e.secondarySeqCycle = 0;
				e.secondarySeqFrame++;
			}

			if (e.secondarySeqFrame >= seq.frameCount) {
				e.secondarySeqCycle = 0;
				e.secondarySeqFrame = 0;
			}
		}

		if ((e.spotanim != -1) && (loopCycle >= e.anInt1523)) {
			if (e.spotanimFrame < 0) {
				e.spotanimFrame = 0;
			}

			SeqType seq = SpotAnimType.instances[e.spotanim].seq;

			for (e.spotanimCycle++; (e.spotanimFrame < seq.frameCount) && (e.spotanimCycle > seq.getFrameDuration(e.spotanimFrame)); e.spotanimFrame++) {
				e.spotanimCycle -= seq.getFrameDuration(e.spotanimFrame);
			}

			if ((e.spotanimFrame >= seq.frameCount) && ((e.spotanimFrame < 0) || (e.spotanimFrame >= seq.frameCount))) {
				e.spotanim = -1;
			}
		}

		if ((e.primarySeqID != -1) && (e.primarySeqDelay <= 1)) {
			SeqType seq = SeqType.instances[e.primarySeqID];

			// we're moving, and it's not due to a force move:
			//  pause primary sequence
			if ((seq.moveStyle == 1) && (e.seqPathLength > 0) && (e.forceMoveEndCycle <= loopCycle) && (e.forceMoveStartCycle < loopCycle)) {
				e.primarySeqDelay = 1;
				return;
			}
		}

		if ((e.primarySeqID != -1) && (e.primarySeqDelay == 0)) {
			SeqType seq = SeqType.instances[e.primarySeqID];

			for (e.primarySeqCycle++; (e.primarySeqFrame < seq.frameCount) && (e.primarySeqCycle > seq.getFrameDuration(e.primarySeqFrame)); e.primarySeqFrame++) {
				e.primarySeqCycle -= seq.getFrameDuration(e.primarySeqFrame);
			}

			if (e.primarySeqFrame >= seq.frameCount) {
				e.primarySeqFrame -= seq.loopFrameCount;
				e.primarySeqLoop++;

				if (e.primarySeqLoop >= seq.loopCount) {
					e.primarySeqID = -1;
				}
				if ((e.primarySeqFrame < 0) || (e.primarySeqFrame >= seq.frameCount)) {
					e.primarySeqID = -1;
				}
			}
			e.aBoolean1541 = seq.forwardRenderPadding;
		}

		if (e.primarySeqDelay > 0) {
			e.primarySeqDelay--;
		}
	}

	public void drawGame() {
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
			drawScene();
		}

		if (menuVisible && (mouseArea == 1)) {
			redrawInvback = true;
		}

		if (invbackComponentID != -1) {
			if (updateParentComponentSeq(delta, invbackComponentID)) {
				redrawInvback = true;
			}
		}

		if (actionArea == 2) {
			redrawInvback = true;
		}

		if (objDragArea == 2) {
			redrawInvback = true;
		}

		if (redrawInvback) {
			drawInvback();
			redrawInvback = false;
		}

		if (chatbackComponentID == -1) {
			chatComponent.scrollY = chatScrollHeight - chatScrollOffset - 77;
			if ((super.mouseX > 448) && (super.mouseX < 560) && (super.mouseY > 332)) {
				handleScrollInput(463, 77, super.mouseX - 17, super.mouseY - 357, chatComponent, 0, false, chatScrollHeight);
			}
			int offset = chatScrollHeight - 77 - chatComponent.scrollY;

			if (offset < 0) {
				offset = 0;
			}

			if (offset > (chatScrollHeight - 77)) {
				offset = chatScrollHeight - 77;
			}
			if (chatScrollOffset != offset) {
				chatScrollOffset = offset;
				redrawChatback = true;
			}
		}

		if (chatbackComponentID != -1) {
			if (updateParentComponentSeq(delta, chatbackComponentID)) {
				redrawChatback = true;
			}
		}

		if (actionArea == 3) {
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

		if (flashingTab != -1) {
			redrawSideicons = true;
		}

		drawSideicons();
		drawPrivacySettings();

		delta = 0;
	}

	private void drawSideicons() {
		if (!redrawSideicons) {
			return;
		}
		if ((flashingTab != -1) && (flashingTab == selectedTab)) {
			flashingTab = -1;
			out.writeOp(120);
			out.write8(selectedTab);
		}
		redrawSideicons = false;
		areaBackhmid1.bind();
		imageBackhmid1.blit(0, 0);
		if (invbackComponentID == -1) {
			if (tabComponentIDs[selectedTab] != -1) {
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
			if ((tabComponentIDs[0] != -1) && ((flashingTab != 0) || ((loopCycle % 20) < 10))) {
				imageSideicons[0].blit(29, 13);
			}
			if ((tabComponentIDs[1] != -1) && ((flashingTab != 1) || ((loopCycle % 20) < 10))) {
				imageSideicons[1].blit(53, 11);
			}
			if ((tabComponentIDs[2] != -1) && ((flashingTab != 2) || ((loopCycle % 20) < 10))) {
				imageSideicons[2].blit(82, 11);
			}
			if ((tabComponentIDs[3] != -1) && ((flashingTab != 3) || ((loopCycle % 20) < 10))) {
				imageSideicons[3].blit(115, 12);
			}
			if ((tabComponentIDs[4] != -1) && ((flashingTab != 4) || ((loopCycle % 20) < 10))) {
				imageSideicons[4].blit(153, 13);
			}
			if ((tabComponentIDs[5] != -1) && ((flashingTab != 5) || ((loopCycle % 20) < 10))) {
				imageSideicons[5].blit(180, 11);
			}
			if ((tabComponentIDs[6] != -1) && ((flashingTab != 6) || ((loopCycle % 20) < 10))) {
				imageSideicons[6].blit(208, 13);
			}
		}
		areaBackhmid1.draw(super.graphics, 516, 160);
		areaBackbase2.bind();
		imageBackbase2.blit(0, 0);
		if (invbackComponentID == -1) {
			if (tabComponentIDs[selectedTab] != -1) {
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
			if ((tabComponentIDs[8] != -1) && ((flashingTab != 8) || ((loopCycle % 20) < 10))) {
				imageSideicons[7].blit(74, 2);
			}
			if ((tabComponentIDs[9] != -1) && ((flashingTab != 9) || ((loopCycle % 20) < 10))) {
				imageSideicons[8].blit(102, 3);
			}
			if ((tabComponentIDs[10] != -1) && ((flashingTab != 10) || ((loopCycle % 20) < 10))) {
				imageSideicons[9].blit(137, 4);
			}
			if ((tabComponentIDs[11] != -1) && ((flashingTab != 11) || ((loopCycle % 20) < 10))) {
				imageSideicons[10].blit(174, 2);
			}
			if ((tabComponentIDs[12] != -1) && ((flashingTab != 12) || ((loopCycle % 20) < 10))) {
				imageSideicons[11].blit(201, 2);
			}
			if ((tabComponentIDs[13] != -1) && ((flashingTab != 13) || ((loopCycle % 20) < 10))) {
				imageSideicons[12].blit(226, 2);
			}
		}
		areaBackbase2.draw(super.graphics, 496, 466);
		areaViewport.bind();
	}

	private void drawPrivacySettings() {
		if (!redrawPrivacySettings) {
			return;
		}
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

	public void appendSpotanims() {
		SpotAnimEntity anim = (SpotAnimEntity) spotanims.peekFront();

		for (; anim != null; anim = (SpotAnimEntity) spotanims.prev()) {
			if ((anim.level != currentLevel) || anim.seqComplete) {
				anim.unlink();
			} else if (loopCycle >= anim.startCycle) {
				anim.update(delta);
				if (anim.seqComplete) {
					anim.unlink();
				} else {
					scene.addTemporary(anim, anim.level, anim.x, anim.z, anim.y, 0, -1, false, 60);
				}
			}
		}
	}

	public void drawParentComponent(Component parent, int px, int py, int scrollY) {
		if ((parent.type != 0) || (parent.children == null)) {
			return;
		}

		if (parent.hidden && (viewportHoveredComponentID != parent.id) && (invbackHoveredComponentID != parent.id) && (chatbackHoveredComponentID != parent.id)) {
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
				if (child.scrollY > (child.scrollableHeight - child.height)) {
					child.scrollY = child.scrollableHeight - child.height;
				}

				if (child.scrollY < 0) {
					child.scrollY = 0;
				}

				drawParentComponent(child, x, y, child.scrollY);

				if (child.scrollableHeight > child.height) {
					drawScrollbar(x + child.width, y, child.height, child.scrollableHeight, child.scrollY);
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

						if (child.invSlotObjID[slot] > 0) {
							int dx = 0;
							int dy = 0;
							int objID = child.invSlotObjID[slot] - 1;

							if (((slotX > (Draw2D.left - 32)) && (slotX < Draw2D.right) && (slotY > (Draw2D.top - 32)) && (slotY < Draw2D.bottom)) || ((objDragArea != 0) && (objDragSlot == slot))) {
								int outlineColor = 0;

								if ((anInt1282 == 1) && (anInt1283 == slot) && (anInt1284 == child.id)) {
									outlineColor = 0xffffff;
								}

								Image24 itemIcon = ObjType.getIcon(objID, child.invSlotAmount[slot], outlineColor);

								if (itemIcon != null) {
									if ((objDragArea != 0) && (objDragSlot == slot) && (objDragComponentID == child.id)) {
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
										if (((slotY + dy + 32) > Draw2D.bottom) && (parent.scrollY < (parent.scrollableHeight - parent.height))) {
											int scroll = (delta * ((slotY + dy + 32) - Draw2D.bottom)) / 3;

											if (scroll > (delta * 10)) {
												scroll = delta * 10;
											}

											if (scroll > (parent.scrollableHeight - parent.height - parent.scrollY)) {
												scroll = parent.scrollableHeight - parent.height - parent.scrollY;
											}
											parent.scrollY += scroll;
											objGrabY -= scroll;
										}
									} else if ((actionArea != 0) && (actionInvSlot == slot) && (actionComponentID == child.id)) {
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
				boolean hovered = (chatbackHoveredComponentID == child.id) || (invbackHoveredComponentID == child.id) || (viewportHoveredComponentID == child.id);
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

				if ((chatbackHoveredComponentID == child.id) || (invbackHoveredComponentID == child.id) || (viewportHoveredComponentID == child.id)) {
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

				int eyeY = (Draw3D.sin[child.modelPitch] * child.modelZoom) >> 16;
				int eyeZ = (Draw3D.cos[child.modelPitch] * child.modelZoom) >> 16;

				boolean active = getComponentScriptState(child);
				int seqID;

				if (active) {
					seqID = child.activeSeqID;
				} else {
					seqID = child.seqID;
				}

				Model model;

				if (seqID == -1) {
					model = child.getModel(-1, -1, active);
				} else {
					SeqType type = SeqType.instances[seqID];
					model = child.getModel(type.auxiliaryTransformIndices[child.seqFrame], type.transformIndices[child.seqFrame], active);
				}

				if (model != null) {
					model.drawSimple(0, child.modelYaw, 0, child.modelPitch, 0, eyeY, eyeZ);
				}

				Draw3D.centerX = _centerX;
				Draw3D.centerY = _centerY;
			} else if (child.type == 7) {
				BitmapFont font = child.font;
				int k4 = 0;
				for (int j5 = 0; j5 < child.height; j5++) {
					for (int i6 = 0; i6 < child.width; i6++) {
						if (child.invSlotObjID[k4] > 0) {
							ObjType type = ObjType.get(child.invSlotObjID[k4] - 1);
							String s2 = type.name;
							if (type.stackable || (child.invSlotAmount[k4] != 1)) {
								s2 = s2 + " x" + formatObjAmountTagged(child.invSlotAmount[k4]);
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

	public void method107(int masks, int j, Buffer buffer, PlayerEntity player) {
		if ((masks & 0x400) != 0) {
			player.forceMoveStartSceneTileX = buffer.read8US();
			player.forceMoveStartSceneTileZ = buffer.read8US();
			player.forceMoveEndSceneTileX = buffer.read8US();
			player.forceMoveEndSceneTileZ = buffer.read8US();
			player.forceMoveEndCycle = buffer.read16ULEA() + loopCycle;
			player.forceMoveStartCycle = buffer.read16UA() + loopCycle;
			player.forceMoveFaceDirection = buffer.read8US();
			player.resetPath();
		}

		if ((masks & 0x100) != 0) {
			player.spotanim = buffer.read16ULE();
			int k = buffer.read32();
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

		if ((masks & 8) != 0) {
			int seqID = buffer.read16ULE();

			if (seqID == 65535) {
				seqID = -1;
			}

			int delay = buffer.read8UC();

			if ((seqID == player.primarySeqID) && (seqID != -1)) {
				int style = SeqType.instances[seqID].replayStyle;

				if (style == 1) {
					player.primarySeqFrame = 0;
					player.primarySeqCycle = 0;
					player.primarySeqDelay = delay;
					player.primarySeqLoop = 0;
				}

				if (style == 2) {
					player.primarySeqLoop = 0;
				}
			} else if ((seqID == -1) || (player.primarySeqID == -1) || (SeqType.instances[seqID].priority >= SeqType.instances[player.primarySeqID].priority)) {
				player.primarySeqID = seqID;
				player.primarySeqFrame = 0;
				player.primarySeqCycle = 0;
				player.primarySeqDelay = delay;
				player.primarySeqLoop = 0;
				player.seqPathLength = player.pathLength;
			}
		}

		if ((masks & 4) != 0) {
			player.chat = buffer.readString();
			if (player.chat.charAt(0) == '~') {
				player.chat = player.chat.substring(1);
				addMessage(2, player.name, player.chat);
			} else if (player == localPlayer) {
				addMessage(2, player.name, player.chat);
			}
			player.chatColor = 0;
			player.chatStyle = 0;
			player.chatTimer = 150;
		}

		if ((masks & 0x80) != 0) {
			int colorStyle = buffer.read16ULE();
			int role = buffer.read8U();
			int length = buffer.read8UC();
			int start = buffer.position;

			if ((player.name != null) && player.visible) {
				long name37 = StringUtil.toBase37(player.name);
				boolean ignore = false;

				if (role <= 1) {
					for (int i4 = 0; i4 < ignoreCount; i4++) {
						if (ignoreName37[i4] != name37) {
							continue;
						}
						ignore = true;
						break;
					}
				}

				if (!ignore && (overrideChat == 0)) {
					try {
						chatBuffer.position = 0;
						buffer.getBytesReversed(chatBuffer.data, 0, length);
						chatBuffer.position = 0;

						String chat = ChatCompression.unpack(length, chatBuffer);
						chat = Censor.apply(chat);

						player.chat = chat;
						player.chatColor = colorStyle >> 8;
						player.chatStyle = colorStyle & 0xff;
						player.chatTimer = 150;

						if ((role == 2) || (role == 3)) {
							addMessage(1, "@cr2@" + player.name, chat);
						} else if (role == 1) {
							addMessage(1, "@cr1@" + player.name, chat);
						} else {
							addMessage(2, player.name, chat);
						}
					} catch (Exception exception) {
						Signlink.reporterror("cde2");
					}
				}
			}
			buffer.position = start + length;
		}

		if ((masks & 1) != 0) {
			player.index = buffer.read16ULE();
			if (player.index == 65535) {
				player.index = -1;
			}
		}
		if ((masks & 0x10) != 0) {
			int j1 = buffer.read8UC();
			byte[] abyte0 = new byte[j1];
			Buffer buffer_1 = new Buffer(abyte0);
			buffer.readBytes(abyte0, 0, j1);
			aBufferArray895[j] = buffer_1;
			player.method451(buffer_1);
		}
		if ((masks & 2) != 0) {
			player.faceTileX = buffer.read16ULEA();
			player.faceTileZ = buffer.read16ULE();
		}
		if ((masks & 0x20) != 0) {
			int k1 = buffer.read8U();
			int k2 = buffer.read8UA();
			player.method447(k2, k1, loopCycle);
			player.combatCycle = loopCycle + 300;
			player.health = buffer.read8UC();
			player.totalHealth = buffer.read8U();
		}
		if ((masks & 0x200) != 0) {
			int l1 = buffer.read8U();
			int l2 = buffer.read8US();
			player.method447(l2, l1, loopCycle);
			player.combatCycle = loopCycle + 300;
			player.health = buffer.read8U();
			player.totalHealth = buffer.read8UC();
		}
	}

	public void updateOrbitCamera() {
		int orbitX = localPlayer.x + cameraAnticheatOffsetX;
		int orbitZ = localPlayer.z + cameraAnticheatOffsetZ;

		if (((orbitCameraX - orbitX) < -500) || ((orbitCameraX - orbitX) > 500) || ((orbitCameraZ - orbitZ) < -500) || ((orbitCameraZ - orbitZ) > 500)) {
			orbitCameraX = orbitX;
			orbitCameraZ = orbitZ;
		}

		if (orbitCameraX != orbitX) {
			orbitCameraX += (orbitX - orbitCameraX) / 16;
		}

		if (orbitCameraZ != orbitZ) {
			orbitCameraZ += (orbitZ - orbitCameraZ) / 16;
		}

		if (super.actionKey[1] == 1) {
			orbitCameraYawVelocity += (-24 - orbitCameraYawVelocity) / 2;
		} else if (super.actionKey[2] == 1) {
			orbitCameraYawVelocity += (24 - orbitCameraYawVelocity) / 2;
		} else {
			orbitCameraYawVelocity /= 2;
		}

		if (super.actionKey[3] == 1) {
			orbitCameraPitchVelocity += (12 - orbitCameraPitchVelocity) / 2;
		} else if (super.actionKey[4] == 1) {
			orbitCameraPitchVelocity += (-12 - orbitCameraPitchVelocity) / 2;
		} else {
			orbitCameraPitchVelocity /= 2;
		}

		orbitCameraYaw = (orbitCameraYaw + (orbitCameraYawVelocity / 2)) & 0x7ff;
		orbitCameraPitch += orbitCameraPitchVelocity / 2;

		if (orbitCameraPitch < 128) {
			orbitCameraPitch = 128;
		}
		if (orbitCameraPitch > 383) {
			orbitCameraPitch = 383;
		}

		int orbitTileX = orbitCameraX >> 7;
		int orbitTileZ = orbitCameraZ >> 7;
		int orbitY = getHeightmapY(currentLevel, orbitCameraX, orbitCameraZ);
		int maxY = 0;

		if ((orbitTileX > 3) && (orbitTileZ > 3) && (orbitTileX < 100) && (orbitTileZ < 100)) {
			for (int x = orbitTileX - 4; x <= (orbitTileX + 4); x++) {
				for (int z = orbitTileZ - 4; z <= (orbitTileZ + 4); z++) {
					int level = currentLevel;
					if ((level < 3) && ((levelTileFlags[1][x][z] & 2) == 2)) {
						level++;
					}
					int y = orbitY - levelHeightmap[level][x][z];

					if (y > maxY) {
						maxY = y;
					}
				}
			}
		}

		int clamp = maxY * 192;

		if (clamp > 98048) {
			clamp = 98048;
		}

		if (clamp < 32768) {
			clamp = 32768;
		}

		if (clamp > cameraPitchClamp) {
			cameraPitchClamp += (clamp - cameraPitchClamp) / 24;
			return;
		}
		if (clamp < cameraPitchClamp) {
			cameraPitchClamp += (clamp - cameraPitchClamp) / 80;
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

	public void setWaveVolume(int volume) {
		Signlink.wavevol = volume;
	}

	private void drawMultizone() {
		if (multizone == 1) {
			imageHeadicons[1].draw(472, 296);
		}
	}

	private void drawViewportComponents() {
		if (viewportOverlayComponentID != -1) {
			updateParentComponentSeq(delta, viewportOverlayComponentID);
			drawParentComponent(Component.instances[viewportOverlayComponentID], 0, 0, 0);
		}

		if (viewportComponentID != -1) {
			updateParentComponentSeq(delta, viewportComponentID);
			drawParentComponent(Component.instances[viewportComponentID], 0, 0, 0);
		}
	}

	private void drawMouseCrosses() {
		if (crossMode == 1) {
			imageCrosses[crossCycle / 100].draw(crossX - 8 - 4, crossY - 8 - 4);
		}

		if (crossMode == 2) {
			imageCrosses[4 + (crossCycle / 100)].draw(crossX - 8 - 4, crossY - 8 - 4);
		}
	}

	private void drawDebug() {
		if (aBoolean1156) {
			int x = 507;
			int y = 20;
			int i1 = 0xffff00;
			if (super.fps < 15) {
				i1 = 0xff0000;
			}
			fontPlain12.drawStringRight("Fps:" + super.fps, x, y, i1);
			y += 15;
			Runtime runtime = Runtime.getRuntime();
			int mem = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			fontPlain12.drawStringRight("Mem:" + mem + "k", x, y, 0xffff00);
		}
	}

	private void drawSystemUpdateTimer() {
		if (systemUpdateTimer != 0) {
			int seconds = systemUpdateTimer / 50;
			int minutes = seconds / 60;
			seconds %= 60;
			if (seconds < 10) {
				fontPlain12.drawString("System update in: " + minutes + ":0" + seconds, 4, 329, 0xffff00);
			} else {
				fontPlain12.drawString("System update in: " + minutes + ":" + seconds, 4, 329, 0xffff00);
			}
		}
	}

	public void addIgnore(long name37) {
		if (name37 == 0L) {
			return;
		}
		if (ignoreCount >= 100) {
			addMessage(0, "", "Your ignore list is full. Max of 100 hit");
			return;
		}
		String s = StringUtil.formatName(StringUtil.fromBase37(name37));
		for (int j = 0; j < ignoreCount; j++) {
			if (ignoreName37[j] == name37) {
				addMessage(0, "", s + " is already on your ignore list");
				return;
			}
		}
		for (int k = 0; k < friendCount; k++) {
			if (friendName37[k] == name37) {
				addMessage(0, "", "Please remove " + s + " from your friend list first");
				return;
			}
		}
		ignoreName37[ignoreCount++] = name37;
		redrawInvback = true;
		out.writeOp(133);
		out.write64(name37);
	}

	public void updatePlayers() {
		for (int i = -1; i < playerCount; i++) {
			int playerID;
			if (i == -1) {
				playerID = LOCAL_PLAYER_INDEX;
			} else {
				playerID = playerIndices[i];
			}
			PlayerEntity player = players[playerID];
			if (player != null) {
				updateEntity(player);
			}
		}
	}

	public void updateTemporaryLocs() {
		if (sceneState == 2) {
			for (SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
				if (loc.duration > 0) {
					loc.duration--;
				}
				if (loc.duration == 0) {
					if ((loc.previousLocID < 0) || SceneBuilder.isLocReady(loc.previousLocID, loc.previousKind)) {
						addLoc(loc.localZ, loc.level, loc.previousRotation, loc.previousKind, loc.localX, loc.classID, loc.previousLocID);
						loc.unlink();
					}
				} else {
					if (loc.delay > 0) {
						loc.delay--;
					}
					if ((loc.delay == 0) && (loc.localX >= 1) && (loc.localZ >= 1) && (loc.localX <= 102) && (loc.localZ <= 102) && ((loc.id < 0) || SceneBuilder.isLocReady(loc.id, loc.kind))) {
						addLoc(loc.localZ, loc.level, loc.rotation, loc.kind, loc.localX, loc.classID, loc.id);
						loc.delay = -1;
						if ((loc.id == loc.previousLocID) && (loc.previousLocID == -1)) {
							loc.unlink();
						} else if ((loc.id == loc.previousLocID) && (loc.rotation == loc.previousRotation) && (loc.kind == loc.previousKind)) {
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
		int j = buffer.readN(1);
		if (j == 0) {
			return;
		}
		int k = buffer.readN(2);
		if (k == 0) {
			anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			return;
		}
		if (k == 1) {
			int l = buffer.readN(3);
			localPlayer.method448(false, l);
			int k1 = buffer.readN(1);
			if (k1 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			return;
		}
		if (k == 2) {
			int i1 = buffer.readN(3);
			localPlayer.method448(true, i1);
			int l1 = buffer.readN(3);
			localPlayer.method448(true, l1);
			int j2 = buffer.readN(1);
			if (j2 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			return;
		}
		if (k == 3) {
			currentLevel = buffer.readN(2);
			int j1 = buffer.readN(1);
			int i2 = buffer.readN(1);
			if (i2 == 1) {
				anIntArray894[anInt893++] = LOCAL_PLAYER_INDEX;
			}
			int k2 = buffer.readN(7);
			int l2 = buffer.readN(7);
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

			if ((child.type == 6) && ((child.seqID != -1) || (child.activeSeqID != -1))) {
				boolean enabled = getComponentScriptState(child);
				int seqID;

				if (enabled) {
					seqID = child.activeSeqID;
				} else {
					seqID = child.seqID;
				}

				if (seqID != -1) {
					SeqType type = SeqType.instances[seqID];
					for (child.seqCycle += delta; child.seqCycle > type.getFrameDuration(child.seqFrame); ) {
						child.seqCycle -= type.getFrameDuration(child.seqFrame) + 1;
						child.seqFrame++;
						if (child.seqFrame >= type.frameCount) {
							child.seqFrame -= type.loopFrameCount;
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

	public int getTopLevel() {
		int top = 3;

		if (cameraPitch < 310) {
			int cameraLocalTileX = cameraX >> 7;
			int cameraLocalTileZ = cameraZ >> 7;
			int playerLocalTileX = localPlayer.x >> 7;
			int playerLocalTileZ = localPlayer.z >> 7;

			if ((levelTileFlags[currentLevel][cameraLocalTileX][cameraLocalTileZ] & 4) != 0) {
				top = currentLevel;
			}

			int tileDeltaX;

			if (playerLocalTileX > cameraLocalTileX) {
				tileDeltaX = playerLocalTileX - cameraLocalTileX;
			} else {
				tileDeltaX = cameraLocalTileX - playerLocalTileX;
			}

			int tileDeltaZ;

			if (playerLocalTileZ > cameraLocalTileZ) {
				tileDeltaZ = playerLocalTileZ - cameraLocalTileZ;
			} else {
				tileDeltaZ = cameraLocalTileZ - playerLocalTileZ;
			}

			if (tileDeltaX > tileDeltaZ) {
				int delta = (tileDeltaZ * 0x10000) / tileDeltaX;
				int accumulator = 32768;

				while (cameraLocalTileX != playerLocalTileX) {
					if (cameraLocalTileX < playerLocalTileX) {
						cameraLocalTileX++;
					} else if (cameraLocalTileX > playerLocalTileX) {
						cameraLocalTileX--;
					}

					if ((levelTileFlags[currentLevel][cameraLocalTileX][cameraLocalTileZ] & 4) != 0) {
						top = currentLevel;
					}

					accumulator += delta;

					if (accumulator >= 0x10000) {
						accumulator -= 0x10000;

						if (cameraLocalTileZ < playerLocalTileZ) {
							cameraLocalTileZ++;
						} else if (cameraLocalTileZ > playerLocalTileZ) {
							cameraLocalTileZ--;
						}

						if ((levelTileFlags[currentLevel][cameraLocalTileX][cameraLocalTileZ] & 4) != 0) {
							top = currentLevel;
						}
					}
				}
			} else {
				int delta = (tileDeltaX * 0x10000) / tileDeltaZ;
				int accumulator = 32768;

				while (cameraLocalTileZ != playerLocalTileZ) {
					if (cameraLocalTileZ < playerLocalTileZ) {
						cameraLocalTileZ++;
					} else if (cameraLocalTileZ > playerLocalTileZ) {
						cameraLocalTileZ--;
					}

					if ((levelTileFlags[currentLevel][cameraLocalTileX][cameraLocalTileZ] & 4) != 0) {
						top = currentLevel;
					}

					accumulator += delta;

					if (accumulator >= 0x10000) {
						accumulator -= 0x10000;

						if (cameraLocalTileX < playerLocalTileX) {
							cameraLocalTileX++;
						} else if (cameraLocalTileX > playerLocalTileX) {
							cameraLocalTileX--;
						}

						if ((levelTileFlags[currentLevel][cameraLocalTileX][cameraLocalTileZ] & 4) != 0) {
							top = currentLevel;
						}
					}
				}
			}
		}

		if ((levelTileFlags[currentLevel][localPlayer.x >> 7][localPlayer.z >> 7] & 4) != 0) {
			top = currentLevel;
		}

		return top;
	}

	public int getTopLevelDefault() {
		int y = getHeightmapY(currentLevel, cameraX, cameraZ);

		if (((y - cameraY) < 800) && ((levelTileFlags[currentLevel][cameraX >> 7][cameraZ >> 7] & 4) != 0)) {
			return currentLevel;
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
				out.writeOp(74);
				out.write64(name37);
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
					int objID = script[pos++];
					if ((objID >= 0) && (objID < ObjType.count) && (!ObjType.get(objID).members || members)) {
						for (int slot = 0; slot < c.invSlotObjID.length; slot++) {
							if (c.invSlotObjID[slot] == (objID + 1)) {
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
					int objID = script[pos++] + 1;
					if ((objID >= 0) && (objID < ObjType.count) && (!ObjType.get(objID).members || members)) {
						for (int slot = 0; slot < c.invSlotObjID.length; slot++) {
							if (c.invSlotObjID[slot] != objID) {
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

	public void drawTooltip() {
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
			imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, orbitCameraYaw, compassMaskLineLengths, compassMaskLineOffsets);
			areaViewport.bind();
			return;
		}

		int angle = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
		int anchorX = 48 + (localPlayer.x / 32);
		int anchorY = 464 - (localPlayer.z / 32);

		imageMinimap.drawRotatedMasked(25, 5, 146, 151, anchorX, anchorY, 256 + minimapZoom, angle, minimapMaskLineLengths, minimapMaskLineOffsets);
		imageCompass.drawRotatedMasked(0, 0, 33, 33, 25, 25, 256, orbitCameraYaw, compassMaskLineLengths, compassMaskLineOffsets);

		for (int i = 0; i < activeMapFunctionCount; i++) {
			int x = ((activeMapFunctionX[i] * 4) + 2) - (localPlayer.x / 32);
			int y = ((activeMapFunctionZ[i] * 4) + 2) - (localPlayer.z / 32);
			drawOnMinimap(activeMapFunctions[i], x, y);
		}

		for (int ltx = 0; ltx < 104; ltx++) {
			for (int ltz = 0; ltz < 104; ltz++) {
				DoublyLinkedList stack = levelObjStacks[currentLevel][ltx][ltz];

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
		int y = getHeightmapY(currentLevel, x, z) - height;

		x -= cameraX;
		y -= cameraY;
		z -= cameraZ;

		int j1 = Model.sin[cameraPitch];
		int k1 = Model.cos[cameraPitch];
		int l1 = Model.sin[cameraYaw];
		int i2 = Model.cos[cameraYaw];

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

	public void appendLoc(int duration, int id, int rotation, int classID, int z, int kind, int level, int x, int delay) {
		SceneLocTemporary loc = null;
		for (SceneLocTemporary other = (SceneLocTemporary) temporaryLocs.peekFront(); other != null; other = (SceneLocTemporary) temporaryLocs.prev()) {
			if ((other.level != level) || (other.localX != x) || (other.localZ != z) || (other.classID != classID)) {
				continue;
			}
			loc = other;
			break;
		}
		if (loc == null) {
			loc = new SceneLocTemporary();
			loc.level = level;
			loc.classID = classID;
			loc.localX = x;
			loc.localZ = z;
			storeLoc(loc);
			temporaryLocs.pushBack(loc);
		}
		loc.id = id;
		loc.kind = kind;
		loc.rotation = rotation;
		loc.delay = delay;
		loc.duration = duration;
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
		for (int i = 0; i < component.scriptComparator.length; i++) {
			int value = executeClientscript1(component, i);
			int operand = component.scriptOperand[i];

			if (component.scriptComparator[i] == 2) {
				if (value >= operand) {
					return false;
				}
			} else if (component.scriptComparator[i] == 3) {
				if (value <= operand) {
					return false;
				}
			} else if (component.scriptComparator[i] == 4) {
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
		int j = buffer.readN(8);
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
			int j1 = buffer.readN(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = buffer.readN(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = buffer.readN(3);
					player.method448(false, l1);
					int j2 = buffer.readN(1);
					if (j2 == 1) {
						anIntArray894[anInt893++] = i1;
					}
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int i2 = buffer.readN(3);
					player.method448(true, i2);
					int k2 = buffer.readN(3);
					player.method448(true, k2);
					int l2 = buffer.readN(1);
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
			int pos = buffer.read8U();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int objID = buffer.read16U();
			int objAmount = buffer.read16U();
			int newAmount = buffer.read16U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				DoublyLinkedList stacks = levelObjStacks[currentLevel][x][z];
				if (stacks != null) {
					for (ObjStackEntity stack = (ObjStackEntity) stacks.peekFront(); stack != null; stack = (ObjStackEntity) stacks.prev()) {
						if ((stack.id != (objID & 0x7fff)) || (stack.amount != objAmount)) {
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
			int pos = buffer.read8U();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int waveID = buffer.read16U();
			int info = buffer.read8U();
			int maxDist = (info >> 4) & 0xf;
			int loopCount = info & 0b111;
			if ((localPlayer.pathTileX[0] >= (x - maxDist)) && (localPlayer.pathTileX[0] <= (x + maxDist)) && (localPlayer.pathTileZ[0] >= (z - maxDist)) && (localPlayer.pathTileZ[0] <= (z + maxDist)) && aBoolean848 && !lowmem && (waveCount < 50)) {
				waveIDs[waveCount] = waveID;
				waveLoops[waveCount] = loopCount;
				waveDelay[waveCount] = SoundTrack.delays[waveID];
				waveCount++;
			}
		}

		// reveal obj (this would already be visible to the local player if it belongs to them)
		if (opcode == 215) {
			int objID = buffer.read16UA();
			int pos = buffer.read8US();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int ownerPID = buffer.read16UA();
			int objAmount = buffer.read16U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104) && (ownerPID != localPID)) {
				ObjStackEntity obj = new ObjStackEntity();
				obj.id = objID;
				obj.amount = objAmount;
				if (levelObjStacks[currentLevel][x][z] == null) {
					levelObjStacks[currentLevel][x][z] = new DoublyLinkedList();
				}
				levelObjStacks[currentLevel][x][z].pushBack(obj);
				sortObjStacks(x, z);
			}
			return;
		}

		// remove obj
		if (opcode == 156) {
			int pos = buffer.read8UA();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int objID = buffer.read16U();
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				DoublyLinkedList list = levelObjStacks[currentLevel][x][z];
				if (list != null) {
					for (ObjStackEntity obj = (ObjStackEntity) list.peekFront(); obj != null; obj = (ObjStackEntity) list.prev()) {
						if (obj.id != (objID & 0x7fff)) {
							continue;
						}
						obj.unlink();
						break;
					}
					if (list.peekFront() == null) {
						levelObjStacks[currentLevel][x][z] = null;
					}
					sortObjStacks(x, z);
				}
			}
			return;
		}

		// update loc
		if (opcode == 160) {
			int pos = buffer.read8US();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int info = buffer.read8US();
			int kind = info >> 2;
			int rotation = info & 3;
			int classID = LOC_KIND_TO_CLASS_ID[kind];
			int seqID = buffer.read16UA();

			if ((x < 0) || (z < 0) || (x >= 103) || (z >= 103)) {
				return;
			}

			int heightmapSW = levelHeightmap[currentLevel][x][z];
			int heightmapSE = levelHeightmap[currentLevel][x + 1][z];
			int heightmapNE = levelHeightmap[currentLevel][x + 1][z + 1];
			int heightmapNW = levelHeightmap[currentLevel][x][z + 1];

			if (classID == 0) {
				SceneWall wall = scene.getWall(currentLevel, x, z);

				if (wall != null) {
					int locID = (wall.bitset >> 14) & 0x7fff;

					if (kind == 2) {
						wall.entityA = new LocEntity(locID, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
						wall.entityB = new LocEntity(locID, (rotation + 1) & 3, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
					} else {
						wall.entityA = new LocEntity(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
					}
				}
			}

			if (classID == 1) {
				SceneWallDecoration deco = scene.getWallDecoration(currentLevel, x, z);

				if (deco != null) {
					deco.entity = new LocEntity((deco.bitset >> 14) & 0x7fff, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
				}
			}

			if (classID == 2) {
				SceneLoc loc = scene.getLoc(currentLevel, x, z);

				if (kind == 11) {
					kind = 10;
				}

				if (loc != null) {
					loc.entity = new LocEntity((loc.bitset >> 14) & 0x7fff, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
				}
			}

			if (classID == 3) {
				SceneGroundDecoration deco = scene.getGroundDecoration(z, x, currentLevel);

				if (deco != null) {
					deco.entity = new LocEntity((deco.bitset >> 14) & 0x7fff, rotation, 22, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
				}
			}
			return;
		}

		// attach loc to player
		if (opcode == 147) {
			int pos = buffer.read8US();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int pid = buffer.read16U();
			byte maxX = buffer.read8S();
			int delay = buffer.read16ULE();
			byte maxZ = buffer.read8C();
			int duration = buffer.read16U();
			int info = buffer.read8US();
			int kind = info >> 2;
			int rotation = info & 3;
			int classID = LOC_KIND_TO_CLASS_ID[kind];
			byte minX = buffer.read8();
			int locID = buffer.read16U();
			byte minZ = buffer.read8C();
			PlayerEntity player;

			if (pid == localPID) {
				player = localPlayer;
			} else {
				player = players[pid];
			}

			if (player != null) {
				LocType type = LocType.get(locID);
				int heightmapSW = levelHeightmap[currentLevel][x][z];
				int heightmapSE = levelHeightmap[currentLevel][x + 1][z];
				int heightmapNE = levelHeightmap[currentLevel][x + 1][z + 1];
				int heightmapNW = levelHeightmap[currentLevel][x][z + 1];

				Model model = type.getModel(kind, rotation, heightmapSW, heightmapSE, heightmapNE, heightmapNW, -1);

				if (model != null) {
					appendLoc(duration + 1, -1, 0, classID, z, 0, currentLevel, x, delay + 1);

					player.locStartCycle = delay + loopCycle;
					player.locStopCycle = duration + loopCycle;
					player.locModel = model;
					int sizeX = type.width;
					int sizeZ = type.length;

					if ((rotation == 1) || (rotation == 3)) {
						sizeX = type.length;
						sizeZ = type.width;
					}

					player.locOffsetX = (x * 128) + (sizeX * 64);
					player.locOffsetZ = (z * 128) + (sizeZ * 64);
					player.locOffsetY = getHeightmapY(currentLevel, player.locOffsetX, player.locOffsetZ);

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

		// add loc (indefinitely)
		if (opcode == 151) {
			int pos = buffer.read8UA();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int id = buffer.read16ULE();
			int info = buffer.read8US();
			int kind = info >> 2;
			int rotation = info & 3;
			int classID = LOC_KIND_TO_CLASS_ID[kind];
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				appendLoc(-1, id, rotation, classID, z, kind, currentLevel, x, 0);
			}
			return;
		}

		// append spotanim
		if (opcode == 4) {
			int pos = buffer.read8U();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			int id = buffer.read16U();
			int y = buffer.read8U();
			int delay = buffer.read16U();

			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				x = (x * 128) + 64;
				z = (z * 128) + 64;
				SpotAnimEntity spotAnim = new SpotAnimEntity(currentLevel, loopCycle, delay, id, getHeightmapY(currentLevel, x, z) - y, z, x);
				spotanims.pushBack(spotAnim);
			}
			return;
		}

		// append item
		if (opcode == 44) {
			int id = buffer.read16ULEA();
			int amount = buffer.read16U();
			int pos = buffer.read8U();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				ObjStackEntity stack = new ObjStackEntity();
				stack.id = id;
				stack.amount = amount;
				if (levelObjStacks[currentLevel][x][z] == null) {
					levelObjStacks[currentLevel][x][z] = new DoublyLinkedList();
				}
				levelObjStacks[currentLevel][x][z].pushBack(stack);
				sortObjStacks(x, z);
			}
			return;
		}

		// remove loc (indefinitely)
		if (opcode == 101) {
			int info = buffer.read8UC();
			int kind = info >> 2;
			int rotation = info & 3;
			int classID = LOC_KIND_TO_CLASS_ID[kind];
			int pos = buffer.read8U();
			int x = baseX + ((pos >> 4) & 7);
			int z = baseZ + (pos & 7);
			if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
				appendLoc(-1, -1, rotation, classID, z, kind, currentLevel, x, 0);
			}
			return;
		}

		// append projectile
		if (opcode == 117) {
			int pos = buffer.read8U();
			int srcX = baseX + ((pos >> 4) & 7);
			int srcZ = baseZ + (pos & 7);
			int dstX = srcX + buffer.read8();
			int dstZ = srcZ + buffer.read8();
			int targetID = buffer.read16();
			int spotanimID = buffer.read16U();
			int srcY = buffer.read8U() * 4;
			int dstY = buffer.read8U() * 4;
			int delay = buffer.read16U();
			int duration = buffer.read16U();
			int peakPitch = buffer.read8U();
			int arcSize = buffer.read8U();
			if ((srcX >= 0) && (srcZ >= 0) && (srcX < 104) && (srcZ < 104) && (dstX >= 0) && (dstZ >= 0) && (dstX < 104) && (dstZ < 104) && (spotanimID != 65535)) {
				srcX = (srcX * 128) + 64;
				srcZ = (srcZ * 128) + 64;
				dstX = (dstX * 128) + 64;
				dstZ = (dstZ * 128) + 64;
				ProjectileEntity projectile = new ProjectileEntity(peakPitch, dstY, delay + loopCycle, duration + loopCycle, arcSize, currentLevel, getHeightmapY(currentLevel, srcX, srcZ) - srcY, srcZ, srcX, targetID, spotanimID);
				projectile.updateVelocity(delay + loopCycle, dstZ, getHeightmapY(currentLevel, dstX, dstZ) - dstY, dstX);
				projectiles.pushBack(projectile);
			}
		}
	}

	public void method139(Buffer buffer) {
		buffer.accessBits();
		int k = buffer.readN(8);
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
			int k1 = buffer.readN(1);
			if (k1 == 0) {
				npcIndices[npcCount++] = j1;
				npc.anInt1537 = loopCycle;
			} else {
				int l1 = buffer.readN(2);
				if (l1 == 0) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int i2 = buffer.readN(3);
					npc.method448(false, i2);
					int k2 = buffer.readN(1);
					if (k2 == 1) {
						anIntArray894[anInt893++] = j1;
					}
				} else if (l1 == 2) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int j2 = buffer.readN(3);
					npc.method448(true, j2);
					int l2 = buffer.readN(3);
					npc.method448(true, l2);
					int i3 = buffer.readN(1);
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
		int angle = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
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

	public void addLoc(int z, int level, int angle, int kind, int x, int classID, int id) {
		if ((x < 1) || (z < 1) || (x > 102) || (z > 102)) {
			return;
		}

		if (lowmem && (level != currentLevel)) {
			return;
		}

		int bitset = 0;

		if (classID == 0) {
			bitset = scene.getWallBitset(level, x, z);
		}

		if (classID == 1) {
			bitset = scene.getWallDecorationBitset(level, x, z);
		}

		if (classID == 2) {
			bitset = scene.getLocBitset(level, x, z);
		}

		if (classID == 3) {
			bitset = scene.getGroundDecorationBitset(level, x, z);
		}

		if (bitset != 0) {
			int otherInfo = scene.getInfo(level, x, z, bitset);
			int otherID = (bitset >> 14) & 0x7fff;
			int otherKind = otherInfo & 0x1f;
			int otherRotation = otherInfo >> 6;

			if (classID == 0) {
				scene.removeWall(x, level, z);
				LocType type = LocType.get(otherID);
				if (type.solid) {
					levelCollisionMap[level].remove(otherRotation, otherKind, type.blocksProjectiles, x, z);
				}
			}

			if (classID == 1) {
				scene.removeWallDecoration(level, x, z);
			}

			if (classID == 2) {
				scene.removeLoc(level, x, z);
				LocType type = LocType.get(otherID);

				if (((x + type.width) > 103) || ((z + type.width) > 103) || ((x + type.length) > 103) || ((z + type.length) > 103)) {
					return;
				}

				if (type.solid) {
					levelCollisionMap[level].remove(otherRotation, type.width, x, z, type.length, type.blocksProjectiles);
				}
			}

			if (classID == 3) {
				scene.removeGroundDecoration(level, x, z);
				LocType type = LocType.get(otherID);
				if (type.solid && type.interactable) {
					levelCollisionMap[level].method218(z, x);
				}
			}
		}

		if (id >= 0) {
			int tileLevel = level;

			// check for bridged tile
			if ((tileLevel < 3) && ((levelTileFlags[1][x][z] & 2) == 2)) {
				tileLevel++;
			}

			SceneBuilder.addLoc(scene, angle, z, kind, tileLevel, levelCollisionMap[level], levelHeightmap, x, id, level);
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

	public void orbitCamera(int distance, int pitch, int targetX, int targetY, int yaw, int targetZ) {
		int invPitch = (2048 - pitch) & 0x7ff;
		int invYaw = (2048 - yaw) & 0x7ff;
		int x = 0;
		int z = 0;
		int y = distance;
		if (invPitch != 0) {
			int sin = Model.sin[invPitch];
			int cos = Model.cos[invPitch];
			int tmp = ((z * cos) - (y * sin)) >> 16;
			y = ((z * sin) + (y * cos)) >> 16;
			z = tmp;
		}
		if (invYaw != 0) {
			int sin = Model.sin[invYaw];
			int cos = Model.cos[invYaw];
			int tmp = ((y * sin) + (x * cos)) >> 16;
			y = ((y * cos) - (x * sin)) >> 16;
			x = tmp;
		}
		cameraX = targetX - x;
		cameraY = targetY - z;
		cameraZ = targetZ - y;
		cameraPitch = pitch;
		cameraYaw = yaw;
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
					psize = in.read16U();
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
			idleNetCycles = 0;
			anInt843 = anInt842;
			anInt842 = anInt841;
			anInt841 = ptype;

			// sync players
			if (ptype == 81) {
				method143(psize, in);
				aBoolean1080 = false;
				ptype = -1;
				return true;
			}

			// login interface
			if (ptype == 176) {
				daysSinceRecoveriesChanged = in.read8UC();
				unreadMessages = in.read16UA();
				warnMembersInNonMembers = in.read8U();
				lastAddress = in.read32ME();
				daysSinceLastLogin = in.read16U();

				if ((lastAddress != 0) && (viewportComponentID == -1)) {
					Signlink.dnslookup(StringUtil.formatIPv4(lastAddress));
					closeInterfaces();
					char cmpType = 650;
					if ((daysSinceRecoveriesChanged != 201) || (warnMembersInNonMembers == 1)) {
						cmpType = 655;
					}
					aString881 = "";
					aBoolean1158 = false;
					for (int i = 0; i < Component.instances.length; i++) {
						if ((Component.instances[i] == null) || (Component.instances[i].contentType != cmpType)) {
							continue;
						}
						viewportComponentID = Component.instances[i].parentID;
						break;
					}
				}

				ptype = -1;
				return true;
			}

			// clear zone
			if (ptype == 64) {
				baseX = in.read8UC();
				baseZ = in.read8US();
				for (int x = baseX; x < (baseX + 8); x++) {
					for (int z = baseZ; z < (baseZ + 8); z++) {
						if (levelObjStacks[currentLevel][x][z] != null) {
							levelObjStacks[currentLevel][x][z] = null;
							sortObjStacks(x, z);
						}
					}
				}
				for (SceneLocTemporary loc = (SceneLocTemporary) temporaryLocs.peekFront(); loc != null; loc = (SceneLocTemporary) temporaryLocs.prev()) {
					if ((loc.localX >= baseX) && (loc.localX < (baseX + 8)) && (loc.localZ >= baseZ) && (loc.localZ < (baseZ + 8)) && (loc.level == currentLevel)) {
						loc.duration = 0;
					}
				}
				ptype = -1;
				return true;
			}

			// sets component to display player head
			if (ptype == 185) {
				int id = in.read16ULEA();
				Component.instances[id].modelCategory = 3;
				if (localPlayer.npcType == null) {
					Component.instances[id].modelID = (localPlayer.colors[0] << 25) + (localPlayer.colors[4] << 20) + (localPlayer.appearances[0] << 15) + (localPlayer.appearances[8] << 10) + (localPlayer.appearances[11] << 5) + localPlayer.appearances[1];
				} else {
					Component.instances[id].modelID = (int) (0x12345678L + localPlayer.npcType.uid);
				}
				ptype = -1;
				return true;
			}

			// disable cinematic camera
			if (ptype == 107) {
				cinematic = false;
				for (int l = 0; l < 5; l++) {
					adjustCameraComponent[l] = false;
				}
				ptype = -1;
				return true;
			}

			// clear inventory
			if (ptype == 72) {
				int id = in.read16ULE();
				Component component = Component.instances[id];
				for (int slot = 0; slot < component.invSlotObjID.length; slot++) {
					component.invSlotObjID[slot] = -1;
					component.invSlotObjID[slot] = 0;
				}
				ptype = -1;
				return true;
			}

			// ignores
			if (ptype == 214) {
				ignoreCount = psize / 8;
				for (int j1 = 0; j1 < ignoreCount; j1++) {
					ignoreName37[j1] = in.read64();
				}
				ptype = -1;
				return true;
			}

			// move camera
			if (ptype == 166) {
				cinematic = true;
				cinematicSrcLocalTileX = in.read8U();
				cinematicSrcLocalTileZ = in.read8U();
				cinematicSrcHeight = in.read16U();
				cinematicMoveSpeed = in.read8U();
				cinematicMoveAcceleration = in.read8U();

				if (cinematicMoveAcceleration >= 100) {
					cameraX = (cinematicSrcLocalTileX * 128) + 64;
					cameraZ = (cinematicSrcLocalTileZ * 128) + 64;
					cameraY = getHeightmapY(currentLevel, cameraX, cameraZ) - cinematicSrcHeight;
				}

				ptype = -1;
				return true;
			}

			// look at
			if (ptype == 177) {
				cinematic = true;
				cinematicDstLocalTileX = in.read8U();
				cinematicDstLocalTileZ = in.read8U();
				cinematicDstHeight = in.read16U();
				cinematicRotateSpeed = in.read8U();
				cinematicRotateAcceleration = in.read8U();

				if (cinematicRotateAcceleration >= 100) {
					int sceneX = (cinematicDstLocalTileX * 128) + 64;
					int sceneZ = (cinematicDstLocalTileZ * 128) + 64;
					int sceneY = getHeightmapY(currentLevel, sceneX, sceneZ) - cinematicDstHeight;
					int deltaX = sceneX - cameraX;
					int deltaY = sceneY - cameraY;
					int deltaZ = sceneZ - cameraZ;
					int distance = (int) Math.sqrt((deltaX * deltaX) + (deltaZ * deltaZ));
					cameraPitch = (int) (Math.atan2(deltaY, distance) * 325.94900000000001D) & 0x7ff;
					cameraYaw = (int) (Math.atan2(deltaX, deltaZ) * -325.94900000000001D) & 0x7ff;
					if (cameraPitch < 128) {
						cameraPitch = 128;
					}
					if (cameraPitch > 383) {
						cameraPitch = 383;
					}
				}
				ptype = -1;
				return true;
			}

			// update skill
			if (ptype == 134) {
				redrawInvback = true;
				int skill = in.read8U();
				int exp = in.read32RME();
				int level = in.read8U();
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

			// set tab component
			if (ptype == 71) {
				int component = in.read16U();
				int tab = in.read8UA();
				if (component == 65535) {
					component = -1;
				}
				tabComponentIDs[tab] = component;
				redrawInvback = true;
				redrawSideicons = true;
				ptype = -1;
				return true;
			}
			if (ptype == 74) {
				int next = in.read16ULE();
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
				int next = in.read16ULEA();
				int delay = in.read16UA();
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
				int x = in.read16();
				int z = in.read16LE();
				int id = in.read16ULE();
				Component cmp = Component.instances[id];
				cmp.x = x;
				cmp.y = z;
				ptype = -1;
				return true;
			}

			if ((ptype == 73) || (ptype == 241)) {
				int zoneX = sceneCenterZoneX;
				int zoneZ = sceneCenterZoneZ;

				if (ptype == 73) {
					zoneX = in.read16UA();
					zoneZ = in.read16U();
					sceneInstanced = false;
				}

				// instanced scenes describe the pieces that make them up.
				if (ptype == 241) {
					zoneZ = in.read16UA();
					in.accessBits();

					for (int level = 0; level < 4; level++) {
						for (int cx = 0; cx < 13; cx++) {
							for (int cz = 0; cz < 13; cz++) {
								if (in.readN(1) == 1) {
									levelChunkBitset[level][cx][cz] = in.readN(26);
								} else {
									levelChunkBitset[level][cx][cz] = -1;
								}
							}
						}
					}

					in.accessBytes();
					zoneX = in.read16U();
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

					for (int level = 0; level < 4; level++) {
						for (int cx = 0; cx < 13; cx++) {
							for (int cz = 0; cz < 13; cz++) {
								int bitset = levelChunkBitset[level][cx][cz];

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

						for (int level = 0; level < 4; level++) {
							if ((dstX >= 0) && (dstZ >= 0) && (dstX < 104) && (dstZ < 104)) {
								levelObjStacks[level][x][z] = levelObjStacks[level][dstX][dstZ];
							} else {
								levelObjStacks[level][x][z] = null;
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

				cinematic = false;
				ptype = -1;
				return true;
			}

			// set viewport overlay
			if (ptype == 208) {
				int componentID = in.read16LE();
				if (componentID >= 0) {
					resetParentComponentSeq(componentID);
				}
				viewportOverlayComponentID = componentID;
				ptype = -1;
				return true;
			}

			// set minimap state
			if (ptype == 99) {
				minimapState = in.read8U();
				ptype = -1;
				return true;
			}

			// set npc head model
			if (ptype == 75) {
				int npcID = in.read16ULEA();
				int componentID = in.read16ULEA();
				Component.instances[componentID].modelCategory = 2;
				Component.instances[componentID].modelID = npcID;
				ptype = -1;
				return true;
			}

			if (ptype == 114) {
				systemUpdateTimer = in.read16ULE() * 30;
				ptype = -1;
				return true;
			}
			if (ptype == 60) {
				baseZ = in.read8U();
				baseX = in.read8UC();
				while (in.position < psize) {
					int opcode = in.read8U();
					readZonePacket(in, opcode);
				}
				ptype = -1;
				return true;
			}
			if (ptype == 35) {
				int component = in.read8U();
				int k11 = in.read8U();
				int j17 = in.read8U();
				int k21 = in.read8U();
				adjustCameraComponent[component] = true;
				adjustCameraShakeScale[component] = k11;
				cameraWobbleScale[component] = j17;
				cameraWobbleSpeed[component] = k21;
				cameraAdjustPhase[component] = 0;
				ptype = -1;
				return true;
			}
			if (ptype == 174) {
				int i4 = in.read16U();
				int l11 = in.read8U();
				int k17 = in.read16U();
				if (aBoolean848 && !lowmem && (waveCount < 50)) {
					waveIDs[waveCount] = i4;
					waveLoops[waveCount] = l11;
					waveDelay[waveCount] = k17 + SoundTrack.delays[i4];
					waveCount++;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 104) {
				int j4 = in.read8UC();
				int i12 = in.read8UA();
				String s6 = in.readString();
				if ((j4 >= 1) && (j4 <= 5)) {
					if (s6.equalsIgnoreCase("null")) {
						s6 = null;
					}
					playerOptions[j4 - 1] = s6;
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
				String s = in.readString();

				if (s.endsWith(":tradereq:")) {
					String name = s.substring(0, s.indexOf(":"));
					long name37 = StringUtil.toBase37(name);

					boolean ignore = false;
					for (int i = 0; i < ignoreCount; i++) {
						if (ignoreName37[i] == name37) {
							ignore = true;
							break;
						}
					}

					if (!ignore && (overrideChat == 0)) {
						addMessage(4, name, "wishes to trade with you.");
					}
				} else if (s.endsWith(":duelreq:")) {
					String name = s.substring(0, s.indexOf(":"));
					long name37 = StringUtil.toBase37(name);

					boolean ignore = false;
					for (int i = 0; i < ignoreCount; i++) {
						if (ignoreName37[i] == name37) {
							ignore = true;
							break;
						}
					}

					if (!ignore && (overrideChat == 0)) {
						addMessage(8, name, "wishes to duel with you.");
					}
				} else if (s.endsWith(":chalreq:")) {
					String name = s.substring(0, s.indexOf(":"));
					long name37 = StringUtil.toBase37(name);

					boolean ignore = false;
					for (int i = 0; i < ignoreCount; i++) {
						if (ignoreName37[i] != name37) {
							continue;
						}
						ignore = true;
						break;
					}

					if (!ignore && (overrideChat == 0)) {
						String message = s.substring(s.indexOf(":") + 1, s.length() - 9);
						addMessage(8, name, message);
					}
				} else {
					addMessage(0, "", s);
				}
				ptype = -1;
				return true;
			}

			// clear player and npc sequences
			if (ptype == 1) {
				for (PlayerEntity player : players) {
					if (player != null) {
						player.primarySeqID = -1;
					}
				}
				for (NPCEntity npc : npcs) {
					if (npc != null) {
						npc.primarySeqID = -1;
					}
				}
				ptype = -1;
				return true;
			}

			if (ptype == 50) {
				long l4 = in.read64();
				int i18 = in.read8U();
				String s7 = StringUtil.formatName(StringUtil.fromBase37(l4));
				for (int k24 = 0; k24 < friendCount; k24++) {
					if (l4 != friendName37[k24]) {
						continue;
					}
					if (friendWorld[k24] != i18) {
						friendWorld[k24] = i18;
						redrawInvback = true;
						if (i18 > 0) {
							addMessage(5, "", s7 + " has logged in.");
						}
						if (i18 == 0) {
							addMessage(5, "", s7 + " has logged out.");
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
						if (((friendWorld[k29] != nodeID) && (friendWorld[k29 + 1] == nodeID)) || ((friendWorld[k29] == 0) && (friendWorld[k29 + 1] != 0))) {
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
				energy = in.read8U();
				ptype = -1;
				return true;
			}
			if (ptype == 254) {
				hintType = in.read8U();
				if (hintType == 1) {
					hintNPC = in.read16U();
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
					hintTileX = in.read16U();
					hintTileZ = in.read16U();
					hintHeight = in.read8U();
				}
				if (hintType == 10) {
					hintPlayer = in.read16U();
				}
				ptype = -1;
				return true;
			}
			if (ptype == 248) {
				int i5 = in.read16UA();
				int k12 = in.read16U();
				if (chatbackComponentID != -1) {
					chatbackComponentID = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentID = i5;
				invbackComponentID = k12;
				redrawInvback = true;
				redrawSideicons = true;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 79) {
				int j5 = in.read16ULE();
				int l12 = in.read16UA();
				Component component_3 = Component.instances[j5];
				if ((component_3 != null) && (component_3.type == 0)) {
					if (l12 < 0) {
						l12 = 0;
					}
					if (l12 > (component_3.scrollableHeight - component_3.height)) {
						l12 = component_3.scrollableHeight - component_3.height;
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

			// add chat message
			if (ptype == 196) {
				long name37 = in.read64();
				int messageID = in.read32();
				int role = in.read8U();
				boolean ignore = false;
				for (int i = 0; i < 100; i++) {
					if (messageIDs[i] == messageID) {
						ignore = true;
						break;
					}
				}

				if (role <= 1) {
					for (int i = 0; i < ignoreCount; i++) {
						if (ignoreName37[i] == name37) {
							ignore = true;
							break;
						}
					}
				}
				if (!ignore && (overrideChat == 0)) {
					try {
						messageIDs[messageCounter] = messageID;
						messageCounter = (messageCounter + 1) % 100;
						String message = ChatCompression.unpack(psize - 13, in);

						if (role != 3) {
							message = Censor.apply(message);
						}

						if ((role == 2) || (role == 3)) {
							addMessage(7, "@cr2@" + StringUtil.formatName(name37), message);
						} else if (role == 1) {
							addMessage(7, "@cr1@" + StringUtil.formatName(name37), message);
						} else {
							addMessage(3, StringUtil.formatName(name37), message);
						}
					} catch (Exception exception1) {
						Signlink.reporterror("cde1");
					}
				}
				ptype = -1;
				return true;
			}

			// set base
			if (ptype == 85) {
				baseZ = in.read8UC();
				baseX = in.read8UC();
				ptype = -1;
				return true;
			}

			// flash tab
			if (ptype == 24) {
				flashingTab = in.read8US();
				if (flashingTab == selectedTab) {
					if (flashingTab == 3) {
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
				int componentID = in.read16ULE();
				int zoom = in.read16U();
				int objID = in.read16U();
				if (objID == 65535) {
					Component.instances[componentID].modelCategory = 0;
				} else {
					ObjType type = ObjType.get(objID);
					Component.instances[componentID].modelCategory = 4;
					Component.instances[componentID].modelID = objID;
					Component.instances[componentID].modelPitch = type.iconPitch;
					Component.instances[componentID].modelYaw = type.iconYaw;
					Component.instances[componentID].modelZoom = (type.iconZoom * 100) / zoom;
				}
				ptype = -1;
				return true;
			}

			if (ptype == 171) {
				boolean hidden = in.read8U() == 1;
				int componentID = in.read16U();
				Component.instances[componentID].hidden = hidden;
				ptype = -1;
				return true;
			}
			if (ptype == 142) {
				int componentID = in.read16ULE();
				resetParentComponentSeq(componentID);
				if (chatbackComponentID != -1) {
					chatbackComponentID = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				invbackComponentID = componentID;
				redrawInvback = true;
				redrawSideicons = true;
				viewportComponentID = -1;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 126) {
				String s1 = in.readString();
				int k13 = in.read16UA();
				if ((k13 >= 0) && (k13 < Component.instances.length)) {
					Component component = Component.instances[k13];
					if (component != null) {
						component.text = s1;
						if (component.parentID == tabComponentIDs[selectedTab]) {
							redrawInvback = true;
						}
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 206) {
				publicChatSetting = in.read8U();
				privateChatSetting = in.read8U();
				tradeChatSetting = in.read8U();
				redrawPrivacySettings = true;
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 240) {
				if (selectedTab == 12) {
					redrawInvback = true;
				}
				weightCarried = in.read16();
				ptype = -1;
				return true;
			}
			if (ptype == 8) {
				int componentID = in.read16ULEA();
				int modelID = in.read16U();
				Component.instances[componentID].modelCategory = 1;
				Component.instances[componentID].modelID = modelID;
				ptype = -1;
				return true;
			}
			if (ptype == 122) {
				int componentID = in.read16ULEA();
				int rgb555 = in.read16ULEA();
				int r = (rgb555 >> 10) & 0x1f;
				int g = (rgb555 >> 5) & 0x1f;
				int b = rgb555 & 0x1f;
				Component.instances[componentID].color = (r << 19) + (g << 11) + (b << 3);
				ptype = -1;
				return true;
			}

			// set inventory
			if (ptype == 53) {
				redrawInvback = true;
				int componentID = in.read16U();
				Component component = Component.instances[componentID];
				int count = in.read16U();
				for (int slot = 0; slot < count; slot++) {
					int amount = in.read8U();

					if (amount == 255) {
						amount = in.read32ME();
					}

					if (slot >= component.invSlotObjID.length) {
						in.read16ULEA();
					} else {
						component.invSlotObjID[slot] = in.read16ULEA();
						component.invSlotAmount[slot] = amount;
					}
				}

				// clear remaining slots
				for (int slot = count; slot < component.invSlotObjID.length; slot++) {
					component.invSlotObjID[slot] = 0;
					component.invSlotAmount[slot] = 0;
				}
				ptype = -1;
				return true;
			}

			// set model rotation and zoom
			if (ptype == 230) {
				int zoom = in.read16UA();
				int componentID = in.read16U();
				int pitch = in.read16U();
				int yaw = in.read16ULEA();
				Component.instances[componentID].modelPitch = pitch;
				Component.instances[componentID].modelYaw = yaw;
				Component.instances[componentID].modelZoom = zoom;
				ptype = -1;
				return true;
			}

			// set social state
			if (ptype == 221) {
				socialState = in.read8U();
				redrawInvback = true;
				ptype = -1;
				return true;
			}

			if (ptype == 249) {
				anInt1046 = in.read8UA();
				localPID = in.read16ULEA();
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
				int componentID = in.read16U();
				resetParentComponentSeq(componentID);
				if (invbackComponentID != -1) {
					invbackComponentID = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				if (chatbackComponentID != -1) {
					chatbackComponentID = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentID = componentID;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 218) {
				stickyChatbackComponentID = in.read16LEA();
				redrawChatback = true;
				ptype = -1;
				return true;
			}
			if (ptype == 87) {
				int j8 = in.read16ULE();
				int l14 = in.read32RME();
				anIntArray1045[j8] = l14;
				if (variables[j8] != l14) {
					variables[j8] = l14;
					updateVarp(j8);
					redrawInvback = true;
					if (stickyChatbackComponentID != -1) {
						redrawChatback = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 36) {
				int k8 = in.read16ULE();
				byte byte0 = in.read8();
				anIntArray1045[k8] = byte0;
				if (variables[k8] != byte0) {
					variables[k8] = byte0;
					updateVarp(k8);
					redrawInvback = true;
					if (stickyChatbackComponentID != -1) {
						redrawChatback = true;
					}
				}
				ptype = -1;
				return true;
			}
			if (ptype == 61) {
				multizone = in.read8U();
				ptype = -1;
				return true;
			}
			if (ptype == 200) {
				int l8 = in.read16U();
				int i15 = in.read16();
				Component component_4 = Component.instances[l8];
				component_4.seqID = i15;
				if (i15 == -1) {
					component_4.seqFrame = 0;
					component_4.seqCycle = 0;
				}
				ptype = -1;
				return true;
			}
			if (ptype == 219) {
				if (invbackComponentID != -1) {
					invbackComponentID = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				if (chatbackComponentID != -1) {
					chatbackComponentID = -1;
					redrawChatback = true;
				}
				if (chatbackInputType != 0) {
					chatbackInputType = 0;
					redrawChatback = true;
				}
				viewportComponentID = -1;
				aBoolean1149 = false;
				ptype = -1;
				return true;
			}
			if (ptype == 34) {
				redrawInvback = true;
				int i9 = in.read16U();
				Component component_2 = Component.instances[i9];
				while (in.position < psize) {
					int j20 = in.readSmartU();
					int i23 = in.read16U();
					int l25 = in.read8U();
					if (l25 == 255) {
						l25 = in.read32();
					}
					if ((j20 >= 0) && (j20 < component_2.invSlotObjID.length)) {
						component_2.invSlotObjID[j20] = i23;
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
				selectedTab = in.read8UC();
				redrawInvback = true;
				redrawSideicons = true;
				ptype = -1;
				return true;
			}
			if (ptype == 164) {
				int j9 = in.read16ULE();
				resetParentComponentSeq(j9);
				if (invbackComponentID != -1) {
					invbackComponentID = -1;
					redrawInvback = true;
					redrawSideicons = true;
				}
				chatbackComponentID = j9;
				redrawChatback = true;
				viewportComponentID = -1;
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

	public void drawScene() {
		sceneCycle++;
		appendPlayers(true);
		appendNPCs(true);
		appendPlayers(false);
		appendNPCs(false);
		appendProjectiles();
		appendSpotanims();

		if (!cinematic) {
			int pitch = orbitCameraPitch;

			if ((cameraPitchClamp / 256) > pitch) {
				pitch = cameraPitchClamp / 256;
			}

			if (adjustCameraComponent[4] && ((cameraWobbleScale[4] + 128) > pitch)) {
				pitch = cameraWobbleScale[4] + 128;
			}

			int yaw = (orbitCameraYaw + cameraAnticheatAngle) & 0x7ff;
			orbitCamera(600 + (pitch * 3), pitch, orbitCameraX, getHeightmapY(currentLevel, localPlayer.x, localPlayer.z) - 50, yaw, orbitCameraZ);
		}

		int topLevel;

		if (!cinematic) {
			topLevel = getTopLevel();
		} else {
			topLevel = getTopLevelDefault();
		}

		int x = cameraX;
		int y = cameraY;
		int z = cameraZ;
		int pitch = cameraPitch;
		int yaw = cameraYaw;
		for (int c = 0; c < 5; c++) {
			if (adjustCameraComponent[c]) {
				int adjust = (int) (((Math.random() * (double) ((adjustCameraShakeScale[c] * 2) + 1)) - (double) adjustCameraShakeScale[c]) + (Math.sin((double) cameraAdjustPhase[c] * ((double) cameraWobbleSpeed[c] / 100D)) * (double) cameraWobbleScale[c]));
				if (c == 0) {
					cameraX += adjust;
				}
				if (c == 1) {
					cameraY += adjust;
				}
				if (c == 2) {
					cameraZ += adjust;
				}
				if (c == 3) {
					cameraYaw = (cameraYaw + adjust) & 0x7ff;
				}
				if (c == 4) {
					cameraPitch += adjust;
					if (cameraPitch < 128) {
						cameraPitch = 128;
					}
					if (cameraPitch > 383) {
						cameraPitch = 383;
					}
				}
			}
		}
		int cycle = Draw3D.cycle;
		Model.checkHover = true;
		Model.pickedCount = 0;
		Model.mouseX = super.mouseX - 4;
		Model.mouseY = super.mouseY - 4;
		Draw2D.clear();

		scene.draw(cameraX, cameraZ, cameraYaw, cameraY, topLevel, cameraPitch);
		scene.clearTemporaryLocs();

		draw2DEntityElements();
		drawChats();
		drawTileHint();
		updateTextures(cycle);

		drawPrivateMessages();
		drawMouseCrosses();
		drawViewportComponents();

		updateChatOverride();

		if (!menuVisible) {
			handleInput();
			drawTooltip();
		} else if (mouseArea == 0) {
			drawMenu();
		}

		drawMultizone();
		drawDebug();
		drawSystemUpdateTimer();

		areaViewport.draw(super.graphics, 4, 4);
		cameraX = x;
		cameraY = y;
		cameraZ = z;
		cameraPitch = pitch;
		cameraYaw = yaw;
	}

	public void closeInterfaces() {
		out.writeOp(130);
		if (invbackComponentID != -1) {
			invbackComponentID = -1;
			redrawInvback = true;
			aBoolean1149 = false;
			redrawSideicons = true;
		}
		if (chatbackComponentID != -1) {
			chatbackComponentID = -1;
			redrawChatback = true;
			aBoolean1149 = false;
		}
		viewportComponentID = -1;
	}

}
