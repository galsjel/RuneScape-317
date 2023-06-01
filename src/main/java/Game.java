// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.math3.random.ISAACRandom;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.CRC32;

public class Game extends GameShell {

    public static final int[][] designPartColor = {{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193}, {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239}, {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};
    public static final int[] designHairColor = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486};
    public static final BigInteger RSA_MODULUS = new BigInteger("93242968160874178961141117696454914926166521358383643939595601370942521521455093876700186936128124949901378336375511410217991934828200240269085956210638221495526997837548491830752932530323575801712019043394543804370633230061376050751046581208165580348020342842759208678350940524484990087619945235963501586831");
    public static final int[] levelExperience;
    public static final BigInteger RSA_EXPONENT = new BigInteger("65537");
    public static final String VALID_CHAT_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    public static final int[] BITMASK;
    public static final int MAX_PLAYER_COUNT = 2048;
    public static final int LOCAL_PLAYER_INDEX = 2047;
    public static int nodeID = 10;
    public static int portOffset;
    public static boolean members = true;
    public static boolean started;
    public static int drawCycle;
    public static ScenePlayer localPlayer;
    public static boolean showPerformance;
    public static boolean showTraffic;
    public static boolean showOccluders;
    public static int loopCycle;
    public static boolean flagged;
    public static final int[] INV_OP_ACTION = {632, 78, 867, 431, 53};
    public static final int[] OBJ_OP_ACTION = {652, 567, 234, 244, 214};
    public static final int[] OBJ_IOP_ACTION = {74, 454, 539, 493, 847};
    public static final int[] LOC_OP_ACTION = {502, 900, 113, 872, 1062};
    public static final int[] NPC_OP_ACTION = {20, 412, 225, 965, 478};

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

    public static String formatObjCountTagged(int amount) {
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

    public static String formatObjCount(int amount) {
        if (amount < 100000) {
            return String.valueOf(amount);
        }
        if (amount < 10000000) {
            return (amount / 1000) + "K";
        } else {
            return (amount / 1000000) + "M";
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("RS2 user client - release #" + 317);

        if (args.length != 5) {
            System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
            return;
        }

        nodeID = Integer.parseInt(args[0]);
        portOffset = Integer.parseInt(args[1]);

        if (args[3].equals("free")) {
            members = false;
        } else if (args[3].equals("members")) {
            members = true;
        } else {
            System.out.println("Usage: node-id, port-offset, [lowmem/highmem], [free/members], storeid");
            return;
        }

        Signlink.storeid = Integer.parseInt(args[4]);
        Signlink.startpriv();

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
    public final int[] cameraModifierJitter = new int[5];
    public final boolean[] cameraModifierEnabled = new boolean[5];
    public final long[] ignoreName37 = new long[100];
    public final int[] cameraModifierWobbleSpeed = new int[5];
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
    public final int[] cameraModifierCycle = new int[5];
    public final int[] varCache = new int[2000];
    public final int[] minimapMaskLineOffsets = new int[151];
    public final int[] compassMaskLineLengths = new int[33];
    /**
     * Not an actual component, just a hacky fix (by Andy Grower) to use the same method to handle scrolling.
     *
     * @see #handleScrollInput(int, int, int, int, Iface, int, boolean, int)
     */
    public final Iface chatInterface = new Iface();
    public final int[] designIdentikits = new int[7];
    public final int[] archiveChecksum = new int[9];
    public final String[] playerOptions = new String[5];
    public final boolean[] playerOptionPushDown = new boolean[5];
    public final int[][][] levelChunkBitset = new int[4][13][13];
    public final int[] tabInterfaceID = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public final int[] cameraModifierWobbleScale = new int[5];
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
    public Socket jaggrabSocket;
    public int titleScreenState;
    public Buffer chatBuffer = new Buffer(new byte[5000]);
    public SceneNPC[] npcs = new SceneNPC[16384];
    public int npcCount;
    public int[] npcIDs = new int[16384];
    public int entityRemovalCount;
    public int[] entityRemovalIDs = new int[1000];
    public int lastPacketType0;
    public int lastPacketType1;
    public int lastPacketType2;
    /**
     * This message is set when {@link #stickyChatInterfaceID} is not <code>-1</code> and a type <code>0</code> (system) message is received.
     *
     * @see #addMessage(int, String, String)
     */
    public String modalMessage;
    public int privateChatSetting;
    public Buffer login = new Buffer(1024);
    public boolean waveEnabled = true;
    public int[] flameGradient;
    public int[] flameGradient0;
    public int[] flameGradient1;
    public int[] flameGradient2;
    public int hintType;
    /**
     * The active viewport interface id which is affected by input.
     */
    public int viewportInterfaceID = -1;
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
    public String reportAbuseInput = "";
    public int localPID = -1;
    public boolean menuVisible = false;
    public int lastHoveredInterfaceID;
    public String chatTyped = "";
    public ScenePlayer[] players = new ScenePlayer[MAX_PLAYER_COUNT];
    public int playerCount;
    public int[] playerIDs = new int[MAX_PLAYER_COUNT];
    public int entityUpdateCount;
    public int[] entityUpdateIDs = new int[MAX_PLAYER_COUNT];
    public Buffer[] playerAppearanceBuffer = new Buffer[MAX_PLAYER_COUNT];
    public int cameraAnticheatAngle;
    public int friendCount;
    public int friendlistStatus;
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
    /**
     * @see #updateTextures(int)
     */
    public byte[] textureBuffer = new byte[16384];
    public int bankArrangeMode;
    public int crossX;
    public int crossY;
    public int crossCycle;
    public int crossMode;
    public int currentLevel;
    public boolean errorLoading = false;
    public int[][] tileLastOccupiedCycle = new int[104][104];
    public Image24 genderButtonImage0;
    public Image24 genderButtonImage1;
    public int hintPlayer;
    public int hintTileX;
    public int hintTileZ;
    public int hintHeight;
    public int hintOffsetX;
    public int hintOffsetZ;
    public int delta;
    public Scene scene;
    public Image8[] imageSideicons = new Image8[13];
    public int menuArea;
    public int menuX;
    public int menuY;
    public int menuWidth;
    public int menuHeight;
    public long inputFriendName37;
    public boolean _focused = true;
    public long[] friendName37 = new long[200];
    public int nextSong = -1;
    public volatile boolean flameThread = false;
    public int projectX = -1;
    public int projectY = -1;
    public Image8 imageTitlebox;
    public Image8 imageTitlebutton;
    public int[] varps = new int[2000];
    public boolean scrollGrabbed = false;
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
    public int scrollInputPadding;
    public ISAACRandom randomIn;
    public Image24 imageMapedge;
    public String chatbackInput = "";
    public int daysSinceLastLogin;
    public int packetSize;
    public int packetType;
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
     * A sticky interface which does not leave on input.
     */
    public int viewportOverlayInterfaceID = -1;
    public int minimapState;
    public int lastWriteDuplicates;
    public int sceneState;
    public Image8 imageScrollbar0;
    public Image8 imageScrollbar1;
    public int viewportHoveredInterfaceID;
    public Image8 imageBackbase1;
    public Image8 imageBackbase2;
    public Image8 imageBackhmid1;
    public boolean updateDesignModel = false;
    public Image24[] imageMapfunction = new Image24[100];
    public int sceneBaseTileX;
    public int sceneBaseTileZ;
    public int scenePrevBaseTileX;
    public int scenePrevBaseTileZ;
    public int loginAttempts;
    public int chatHoveredInterfaceID;
    public int flameGradientCycle0;
    public int flameGradientCycle1;
    /**
     * The sticky chatback interface, which can only be opened/closed by the server via packet 218.
     */
    public int stickyChatInterfaceID = -1;
    public int isMember;
    public boolean designGenderMale = true;
    public int sidebarHoveredInterfaceID;
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
    /**
     * Set to <code>true</code> when the change region packet is received, and set to <code>false</code> when the player
     * sync packet is received. Used by {@link #checkScene()}.
     */
    public boolean awaitingSync = false;
    public String[] friendName = new String[200];
    public Buffer in = new Buffer(5000);
    /**
     * The interface id of the container that the obj being dragged belongs to.
     */
    public int objDragInterfaceID;
    /**
     * The slot the obj being dragged resides.
     */
    public int objDragSlot;
    /**
     * The area the object being dragged resides.
     * 1 = Viewport
     * 2 = Sidebar
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
    public int spellSelected;
    public int activeSpellID;
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
    public boolean pressedContinueOption = false;
    public Image24[] imageCrosses = new Image24[8];
    public boolean midiEnabled = true;
    public Image8[] imageRunes;
    public boolean redrawSidebar = false;
    public int unreadMessages;
    public boolean ingame = false;
    public boolean reportAbuseMuteOption = false;
    public boolean sceneInstanced = false;
    /**
     * When <code>true</code> will cause the camera to be overridden by the following cutscene fields.
     *
     * @see #applyCutscene()
     * @see #cutsceneSrcLocalTileX
     * @see #cutsceneSrcLocalTileZ
     * @see #cutsceneSrcHeight
     * @see #cutsceneMoveSpeed
     * @see #cutsceneMoveAcceleration
     * @see #cutsceneDstLocalTileX
     * @see #cutsceneDstLocalTileZ
     * @see #cutsceneDstHeight
     * @see #cutsceneRotateSpeed
     * @see #cutsceneRotateAcceleration
     */
    public boolean cutscene = false;
    /**
     * @see #cutscene
     */
    public int cutsceneSrcLocalTileX;
    /**
     * @see #cutscene
     */
    public int cutsceneSrcLocalTileZ;
    /**
     * @see #cutscene
     */
    public int cutsceneSrcHeight;
    /**
     * @see #cutscene
     */
    public int cutsceneMoveSpeed;
    /**
     * @see #cutscene
     */
    public int cutsceneMoveAcceleration;
    /**
     * @see #cutscene
     */
    public int cutsceneDstLocalTileX;
    /**
     * @see #cutscene
     */
    public int cutsceneDstLocalTileZ;
    /**
     * @see #cutscene
     */
    public int cutsceneDstHeight;
    /**
     * @see #cutscene
     */
    public int cutsceneRotateSpeed;
    /**
     * @see #cutscene
     */
    public int cutsceneRotateAcceleration;
    public DrawArea areaSidebar;
    public DrawArea areaMapback;
    public DrawArea areaViewport;
    public DrawArea areaChatback;
    public int daysSinceRecoveriesChanged;
    public Connection connection;
    public int messageCounter;
    public int minimapZoom;
    public long lastWaveStartTime;
    public String username = "mad xanax";
    public String password = "qweasdzxc";
    public boolean errorHost = false;
    public int reportAbuseInterfaceID = -1;
    public DoublyLinkedList temporaryLocs = new DoublyLinkedList();
    public int[] areaChatbackOffsets;
    public int[] areaSidebarOffsets;
    public int[] areaViewportOffsets;
    public byte[][] sceneMapLandData;
    public int orbitCameraPitch = 128;
    public int orbitCameraYaw;
    public int orbitCameraYawVelocity;
    public int orbitCameraPitchVelocity;
    public int sidebarInterfaceID = -1;
    public int[] flameBuffer0;
    public int[] flameBuffer1;
    public Buffer out = new Buffer(5000);
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
    public int[][][] level_heightmap;
    public long serverSeed;
    public int titleLoginField;
    public long prevMousePressTime;
    public int selectedTab = 3;
    public int hintNPC;
    public boolean redrawChatback = false;
    public int chatbackInputType;
    public int song;
    public boolean songFading = true;
    public CollisionMap[] level_collisions = new CollisionMap[4];
    public boolean redrawPrivacySettings = false;
    public int[] sceneMapIndex;
    public int[] sceneMapLandFile;
    public int[] sceneMapLocFile;
    public int lastWriteX;
    public int lastWriteY;
    public boolean objGrabThreshold = false;
    public int actionCycles;
    public int actionInterfaceID;
    public int actionSlot;
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
    public int nextSongDelay;
    public int flagSceneTileX;
    public int flagSceneTileZ;
    public Image24 imageMinimap;
    public int tryMoveNearest;
    public int sceneCycle;
    public String loginMessage0 = "";
    public String loginMessage1 = "";
    public int baseX;
    public int baseZ;
    public int bytesIn;
    public int bytesOut;
    public BitmapFont fontPlain11;
    public BitmapFont fontPlain12;
    public BitmapFont fontBold12;
    public BitmapFont fontQuill8;
    public int flameCycle0;
    public int chatInterfaceID = -1;
    public int cameraAnticheatOffsetX;
    public int[] bfsStepX = new int[4000];
    public int[] bfsStepZ = new int[4000];
    public int objSelected;
    public int selectedObjSlot;
    public int selectedObjInterfaceID;
    public int selectedObjID;
    public String selectedObjName;
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
        drawProgress(20, "Starting up");

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
            //loadArchiveChecksums();

            archiveTitle = loadArchive(1, "title screen", "title", archiveChecksum[1], 25);
            fontPlain11 = new BitmapFont(archiveTitle, "p11_full", false);
            fontPlain12 = new BitmapFont(archiveTitle, "p12_full", false);
            fontBold12 = new BitmapFont(archiveTitle, "b12_full", false);
            fontQuill8 = new BitmapFont(archiveTitle, "q8_full", true);

            loadTitleBackground();
            loadTitleImages();

            FileArchive archiveConfig = loadArchive(2, "config", "config", archiveChecksum[2], 30);
            FileArchive archiveInterface = loadArchive(3, "interface", "interface", archiveChecksum[3], 35);
            FileArchive archiveMedia = loadArchive(4, "2d graphics", "media", archiveChecksum[4], 40);
            FileArchive archiveTextures = loadArchive(6, "textures", "texture", archiveChecksum[6], 45);
            FileArchive archiveWordenc = loadArchive(7, "chat system", "wordenc", archiveChecksum[7], 50);
            FileArchive archiveSounds = loadArchive(8, "sound effects", "sound", archiveChecksum[8], 55);

            levelTileFlags = new byte[4][104][104];
            level_heightmap = new int[4][105][105];
            scene = new Scene(104, 104, level_heightmap, 4);

            for (int level = 0; level < 4; level++) {
                level_collisions[level] = new CollisionMap(104, 104);
            }

            imageMinimap = new Image24(512, 512);

            FileArchive archiveVersionlist = loadArchive(5, "update list", "versionlist", archiveChecksum[5], 60);

            drawProgress(60, "Connecting to update server");

            ondemand = new OnDemand();
            ondemand.load(archiveVersionlist, this);

            AnimationTransform.init(ondemand.getAnimationFrameCount());
            Model.init(ondemand.getFileCount(0), ondemand);

            song = 0;

            try {
                song = Integer.parseInt(getParameter("music"));
            } catch (Exception ignored) {
            }

            songFading = true;
            ondemand.request(2, song);

            while (ondemand.remaining() > 0) {
                handleOnDemandRequests();

                try {
                    Thread.sleep(100L);
                } catch (Exception ignored) {
                }

                if (ondemand.failCount > 3) {
                    System.out.println("ondemand");
                    return;
                }
            }

            drawProgress(65, "Requesting animations");

            int total = ondemand.getFileCount(1);

            for (int i = 0; i < total; i++) {
                if (ondemand.animIndex[i] != 0) {
                    ondemand.request(1, i);
                }
            }

            while (ondemand.remaining() > 0) {
                int done = total - ondemand.remaining();

                if (done > 0) {
                    drawProgress(65, "Loading animations - " + done * 100 / total + "%");
                }

                handleOnDemandRequests();

                try {
                    Thread.sleep(100L);
                } catch (Exception ignored) {
                }

                if (ondemand.failCount > 3) {
                    System.out.println("ondemand");
                    return;
                }
            }

            drawProgress(70, "Requesting models");

            total = ondemand.getFileCount(0);

            for (int i = 0; i < total; i++) {
                ondemand.request(0, i);
            }

            total = ondemand.remaining();

            while (ondemand.remaining() > 0) {
                int done = total - ondemand.remaining();

                if (done > 0) {
                    drawProgress(70, "Loading models - " + done * 100 / total + "%");
                }

                handleOnDemandRequests();

                try {
                    Thread.sleep(100L);
                } catch (Exception ignored) {
                }
            }

            if (filestores[0] != null) {
                drawProgress(75, "Requesting maps");

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
                        drawProgress(75, "Loading maps - " + done * 100 / total + "%");
                    }

                    handleOnDemandRequests();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception ignored) {
                    }
                }
            }

            total = ondemand.getFileCount(0);

            for (int modelID = 0; modelID < total; modelID++) {
                int flags = ondemand.getModelFlags(modelID);
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
                    ondemand.prefetch(priority, 0, modelID);
                }
            }
            ondemand.prefetchMaps(members);

            int midiCount = ondemand.getFileCount(2);
            for (int midi = 1; midi < midiCount; midi++) {
                if (ondemand.hasMidi(midi)) {
                    ondemand.prefetch((byte) 1, 2, midi);
                }
            }

            drawProgress(80, "Unpacking media");
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
                for (int i = 0; i < 100; i++) {
                    imageMapscene[i] = new Image8(archiveMedia, "mapscene", i);
                }
            } catch (Exception ignored) {
            }
            try {
                for (int i = 0; i < 100; i++) {
                    imageMapfunction[i] = new Image24(archiveMedia, "mapfunction", i);
                }
            } catch (Exception ignored) {
            }
            try {
                for (int i = 0; i < 20; i++) {
                    imageHitmarks[i] = new Image24(archiveMedia, "hitmarks", i);
                }
            } catch (Exception ignored) {
            }
            try {
                for (int i = 0; i < 20; i++) {
                    imageHeadicons[i] = new Image24(archiveMedia, "headicons", i);
                }
            } catch (Exception ignored) {
            }
            imageMapmarker0 = new Image24(archiveMedia, "mapmarker", 0);
            imageMapmarker1 = new Image24(archiveMedia, "mapmarker", 1);
            for (int i = 0; i < 8; i++) {
                imageCrosses[i] = new Image24(archiveMedia, "cross", i);
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
            imageRedstone1h.flipHorizontally();
            imageRedstone2h = new Image8(archiveMedia, "redstone2", 0);
            imageRedstone2h.flipHorizontally();
            imageRedstone1v = new Image8(archiveMedia, "redstone1", 0);
            imageRedstone1v.flipVertically();
            imageRedstone2v = new Image8(archiveMedia, "redstone2", 0);
            imageRedstone2v.flipVertically();
            imageRedstone3v = new Image8(archiveMedia, "redstone3", 0);
            imageRedstone3v.flipVertically();
            imageRedstone1hv = new Image8(archiveMedia, "redstone1", 0);
            imageRedstone1hv.flipHorizontally();
            imageRedstone1hv.flipVertically();
            imageRedstone2hv = new Image8(archiveMedia, "redstone2", 0);
            imageRedstone2hv.flipHorizontally();
            imageRedstone2hv.flipVertically();
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

            drawProgress(83, "Unpacking textures");
            Draw3D.unpackTextures(archiveTextures);
            Draw3D.buildPalette(0.8);
            Draw3D.initPool(20);

            drawProgress(86, "Unpacking config");
            Animation.unpack(archiveConfig);
            Obj.unpack(archiveConfig);
            Flo.unpack(archiveConfig);
            Item.unpack(archiveConfig);
            NPC.unpack(archiveConfig);
            Identikit.unpack(archiveConfig);
            SpotAnim.unpack(archiveConfig);
            Varp.unpack(archiveConfig);
            Varbit.unpack(archiveConfig);

            Item tmp = Item.get(4653);
            Model model = Model.tryGet(tmp.modelID);
            System.out.println(model);
            System.out.println(tmp);

            try {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
                Files.writeString(Paths.get("out/animations.json"), gson.toJson(Animation.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/animation_transforms.json"), gson.toJson(AnimationTransform.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/animation_skeletons.json"), gson.toJson(AnimationTransform.skeletons.toArray()), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/spotanims.json"), gson.toJson(SpotAnim.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/identikits.json"), gson.toJson(Identikit.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/varps.json"), gson.toJson(Varp.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(Paths.get("out/floors.json"), gson.toJson(Flo.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                Obj[] locs = new Obj[Obj.count];
                for (int i = 0; i < locs.length; i++) {
                    locs[i] = Obj.get_uncached(i);
                    locs[i].prepare_export();

                    // TODO: export images
                }
                Files.writeString(Paths.get("out/objects.json"), gson.toJson(locs), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                NPC[] npcs = new NPC[NPC.count];
                for (int i = 0; i < npcs.length; i++) {
                    npcs[i] = NPC.getUncached(i);
                    npcs[i].prepare_export();

                    // TODO: export images
                    // TODO: export model json
                }
                Files.writeString(Paths.get("out/npcs.json"), gson.toJson(npcs), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);


                Files.writeString(Paths.get("out/varbits.json"), gson.toJson(Varbit.instances), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            drawProgress(90, "Unpacking sounds");
            SoundTrack.unpack(new Buffer(archiveSounds.read("sounds.dat")));

            drawProgress(95, "Unpacking interfaces");
            Iface.unpack(archiveInterface, new BitmapFont[]{fontPlain11, fontPlain12, fontBold12, fontQuill8}, archiveMedia);

            drawProgress(100, "Preparing game engine");

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
            areaSidebarOffsets = Draw3D.lineOffset;

            Draw3D.init3D(512, 334);
            areaViewportOffsets = Draw3D.lineOffset;

            Scene.init(512, 334);

            mouseRecorder = new MouseRecorder(this);
            startThread(mouseRecorder = new MouseRecorder(this), 10);

            SceneObject.game = this;
            Obj.game = this;
            NPC.game = this;
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
        stopMidi();
        if (mouseRecorder != null) {
            mouseRecorder.active = false;
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
        level_heightmap = null;
        levelTileFlags = null;
        scene = null;
        level_collisions = null;
        bfsDirection = null;
        bfsCost = null;
        bfsStepX = null;
        bfsStepZ = null;
        textureBuffer = null;
        areaSidebar = null;
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
        playerIDs = null;
        entityUpdateIDs = null;
        playerAppearanceBuffer = null;
        entityRemovalIDs = null;
        npcs = null;
        npcIDs = null;
        levelObjStacks = null;
        temporaryLocs = null;
        projectiles = null;
        spotanims = null;
        menuParamA = null;
        menuParamB = null;
        menuAction = null;
        menuParamC = null;
        menuOption = null;
        varps = null;
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
        unloadTitle();
        Obj.unload();
        NPC.unload();
        Item.unload();
        Flo.instances = null;
        Identikit.instances = null;
        Iface.instances = null;
        Animation.instances = null;
        SpotAnim.instances = null;
        SpotAnim.modelCache = null;
        Varp.instances = null;
        ScenePlayer.modelCache = null;
        Draw3D.unload();
        Scene.unload();
        Model.unload();
        AnimationTransform.unload();
        System.gc();
    }

    @Override
    public void draw() throws IOException {
        if (errorStarted || errorLoading || errorHost) {
            drawError();
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
        super.startThread(runnable, priority);
    }

    @Override
    public void drawProgress(int percent, String message) throws IOException {
        lastProgressPercent = percent;
        lastProgressMessage = message;

        loadTitle();

        if (archiveTitle == null) {
            super.drawProgress(percent, message);
            return;
        }

        imageTitle4.bind();

        int x = 360;
        int y = 200;

        byte offsetY = 20;
        fontBold12.drawStringCenter("RuneScape is loading - please wait...", x / 2, (y / 2) - 26 - offsetY, 0xffffff);
        int midY = (y / 2) - 18 - offsetY;

        Draw2D.drawRect((x / 2) - 152, midY, 304, 34, 0x8c1111);
        Draw2D.drawRect((x / 2) - 151, midY + 1, 302, 32, 0);
        Draw2D.fillRect((x / 2) - 150, midY + 2, percent * 3, 30, 0x8c1111);
        Draw2D.fillRect(((x / 2) - 150) + (percent * 3), midY + 2, 300 - (percent * 3), 30, 0);

        fontBold12.drawStringCenter(message, x / 2, ((y / 2) + 5) - offsetY, 0xffffff);
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

    public void stopMidi() {
        Signlink.midifade = 0;
        Signlink.midi = "stop";
    }

    public void loadArchiveChecksums() throws IOException {
        int wait = 5;
        int retries = 0;

        archiveChecksum[8] = 0;

        while (archiveChecksum[8] == 0) {
            String s = "Unknown problem";
            drawProgress(20, "Connecting to web server");

            try {
                DataInputStream in = openURL("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
                Buffer buffer = new Buffer(new byte[40]);
                in.readFully(buffer.data, 0, 40);
                in.close();

                for (int i = 0; i < 9; i++) {
                    archiveChecksum[i] = buffer.read32();
                }

                int expectedChecksum = buffer.read32();
                int calculatedChecksum = 1234;

                for (int i = 0; i < 9; i++) {
                    calculatedChecksum = (calculatedChecksum << 1) + archiveChecksum[i];
                }

                if (expectedChecksum != calculatedChecksum) {
                    s = "checksum problem";
                    archiveChecksum[8] = 0;
                }
            } catch (EOFException e) {
                s = "EOF problem";
                archiveChecksum[8] = 0;
            } catch (IOException e) {
                s = "connection problem";
                archiveChecksum[8] = 0;
            } catch (Exception e) {
                s = "logic problem";
                archiveChecksum[8] = 0;

                if (!Signlink.reporterror) {
                    return;
                }
            }

            if (archiveChecksum[8] == 0) {
                retries++;

                for (int remaining = wait; remaining > 0; remaining--) {
                    if (retries >= 10) {
                        drawProgress(10, "Game updated - please reload page");
                        remaining = 10;
                    } else {
                        drawProgress(10, s + " - Will retry in " + remaining + " secs.");
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

    public boolean isAddFriendOption(int option) {
        if (option < 0) {
            return false;
        }
        int action = menuAction[option];
        if (action >= 2000) {
            action -= 2000;
        }
        return action == 337;
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
        } else if (modalMessage != null) {
            fontBold12.drawStringCenter(modalMessage, 239, 40, 0);
            fontBold12.drawStringCenter("Click to continue", 239, 60, 128);
        } else if (chatInterfaceID != -1) {
            drawParentInterface(Iface.instances[chatInterfaceID], 0, 0, 0);
        } else if (stickyChatInterfaceID != -1) {
            drawParentInterface(Iface.instances[stickyChatInterfaceID], 0, 0, 0);
        } else {
            drawChat();
        }

        if (menuVisible && (menuArea == 2)) {
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

    static String server = "world53.scape05.com";

    public Socket openSocket(int port) throws IOException {
        return new Socket(InetAddress.getByName(server), port);
    }

    public void handleMouseInput() {
        if (objDragArea != 0) {
            return;
        }

        int button = super.mouseClickButton;

        if ((spellSelected == 1) && (super.mouseClickX >= 516) && (super.mouseClickY >= 160) && (super.mouseClickX <= 765) && (super.mouseClickY <= 205)) {
            button = 0;
        }

        if (menuVisible) {
            handleMenuInput(button);
        } else {
            if ((button == 1) && (menuSize > 0)) {
                int action = menuAction[menuSize - 1];
                switch (action) {
                    case 632:
                    case 78:
                    case 867:
                    case 431:
                    case 53:
                    case 74:
                    case 454:
                    case 539:
                    case 493:
                    case 847:
                    case 447:
                    case 1125: {
                        int objSlot = menuParamA[menuSize - 1];
                        int interfaceID = menuParamB[menuSize - 1];
                        Iface iface = Iface.instances[interfaceID];

                        if (iface.inventoryDraggable || iface.inventoryMoveReplaces) {
                            objGrabThreshold = false;
                            objDragCycles = 0;
                            objDragInterfaceID = interfaceID;
                            objDragSlot = objSlot;
                            objDragArea = 2;
                            objGrabX = super.mouseClickX;
                            objGrabY = super.mouseClickY;
                            if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
                                objDragArea = 1;
                            }
                            if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
                                objDragArea = 3;
                            }
                            return;
                        }
                        break;
                    }
                }
            }

            if ((button == 1) && ((mouseButtonsOption == 1) || isAddFriendOption(menuSize - 1)) && (menuSize > 2)) {
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

    private void handleMenuInput(int button) {
        if (button != 1) {
            int mouseX = super.mouseX;
            int mouseY = super.mouseY;

            if (menuArea == 0) {
                mouseX -= 4;
                mouseY -= 4;
            } else if (menuArea == 1) {
                mouseX -= 553;
                mouseY -= 205;
            } else if (menuArea == 2) {
                mouseX -= 17;
                mouseY -= 357;
            }

            if ((mouseX < (menuX - 10)) || (mouseX > (menuX + menuWidth + 10)) || (mouseY < (menuY - 10)) || (mouseY > (menuY + menuHeight + 10))) {
                menuVisible = false;
                if (menuArea == 1) {
                    redrawSidebar = true;
                }
                if (menuArea == 2) {
                    redrawChatback = true;
                }
            }
        }

        if (button == 1) {
            int menuX = this.menuX;
            int menuY = this.menuY;
            int menuWidth = this.menuWidth;
            int mouseX = super.mouseClickX;
            int mouseY = super.mouseClickY;

            if (menuArea == 0) {
                mouseX -= 4;
                mouseY -= 4;
            } else if (menuArea == 1) {
                mouseX -= 553;
                mouseY -= 205;
            } else if (menuArea == 2) {
                mouseX -= 17;
                mouseY -= 357;
            }

            int option = -1;
            for (int i = 0; i < menuSize; i++) {
                int optionY = menuY + 31 + ((menuSize - 1 - i) * 15);

                if ((mouseX > menuX) && (mouseX < (menuX + menuWidth)) && (mouseY > (optionY - 13)) && (mouseY < (optionY + 3))) {
                    option = i;
                }
            }

            if (option != -1) {
                useMenuOption(option);
            }

            menuVisible = false;

            if (menuArea == 1) {
                redrawSidebar = true;
            } else if (menuArea == 2) {
                redrawChatback = true;
            }
        }
    }

    public void midisave(boolean fade, byte[] data) {
        Signlink.midifade = fade ? 1 : 0;
        Signlink.midisave(data, data.length);
    }

    public void buildScene() {
        try {
            minimapLevel = -1;

            spotanims.clear();
            projectiles.clear();

            Draw3D.clearTexels();

            clearCaches();
            scene.reset();

            for (int i = 0; i < 4; i++) {
                level_collisions[i].reset();
            }

            clearTileFlags();

            SceneBuilder builder = new SceneBuilder(levelTileFlags, 104, 104, level_heightmap);

            out.writeOp(0);

            if (sceneInstanced) {
                buildSceneInstanced(builder);
            } else {
                buildSceneStandard(builder);
            }

            out.writeOp(0);

            builder.build(level_collisions, scene);
            areaViewport.bind();

            out.writeOp(0);

            scene.setMinLevel(0);

            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    sortObjStacks(x, z);
                }
            }

            clearTemporaryLocs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.gc();
        Draw3D.initPool(20);
        ondemand.clearPrefetches();

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

        for (int mapX = minMapX; mapX <= maxMapX; mapX++) {
            for (int mapZ = minMapZ; mapZ <= maxMapZ; mapZ++) {
                if ((mapX == minMapX) || (mapX == maxMapX) || (mapZ == minMapZ) || (mapZ == maxMapZ)) {
                    int mapFile = ondemand.getMapFile(0, mapX, mapZ);
                    if (mapFile != -1) {
                        ondemand.prefetch(mapFile, 3);
                    }
                    int locFile = ondemand.getMapFile(1, mapX, mapZ);
                    if (locFile != -1) {
                        ondemand.prefetch(locFile, 3);
                    }
                }
            }
        }
    }

    private void buildSceneInstanced(SceneBuilder builder) {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 13; x++) {
                for (int z = 0; z < 13; z++) {
                    int chunk = levelChunkBitset[level][x][z];
                    if (chunk == -1) {
                        continue;
                    }
                    int mapLevel = (chunk >> 24) & 3;
                    int chunkRotation = (chunk >> 1) & 3;
                    int mapX = (chunk >> 14) & 0x3ff;
                    int mapZ = (chunk >> 3) & 0x7ff;
                    int mapID = ((mapX / 8) << 8) + (mapZ / 8);
                    for (int i = 0; i < sceneMapIndex.length; i++) {
                        if ((sceneMapIndex[i] != mapID) || (sceneMapLandData[i] == null)) {
                            continue;
                        }
                        builder.readChunkTiles(level_collisions, sceneMapLandData[i], (mapX & 7) * 8, (mapZ & 7) * 8, mapLevel, chunkRotation, x * 8, z * 8, level);
                        break;
                    }
                }
            }
        }

        for (int chunkX = 0; chunkX < 13; chunkX++) {
            for (int chunkZ = 0; chunkZ < 13; chunkZ++) {
                int bitset = levelChunkBitset[0][chunkX][chunkZ];
                if (bitset == -1) {
                    builder.stitchHeightmap(chunkX * 8, chunkZ * 8, 8, 8);
                }
            }
        }

        out.writeOp(0);

        for (int level = 0; level < 4; level++) {
            for (int chunkX = 0; chunkX < 13; chunkX++) {
                for (int chunkZ = 0; chunkZ < 13; chunkZ++) {
                    int chunkBitset = levelChunkBitset[level][chunkX][chunkZ];
                    if (chunkBitset == -1) {
                        continue;
                    }
                    int mapLevel = (chunkBitset >> 24) & 3;
                    int mapRotation = (chunkBitset >> 1) & 3;
                    int mapX = (chunkBitset >> 14) & 0x3ff;
                    int mapZ = (chunkBitset >> 3) & 0x7ff;
                    int mapID = ((mapX / 8) << 8) + (mapZ / 8);
                    for (int i = 0; i < sceneMapIndex.length; i++) {
                        if ((sceneMapIndex[i] != mapID) || (sceneMapLocData[i] == null)) {
                            continue;
                        }
                        builder.readChunkLocs(level_collisions, scene, mapLevel, mapRotation, (mapX & 7) * 8, (mapZ & 7) * 8, chunkX * 8, chunkZ * 8, sceneMapLocData[i], level);
                        break;
                    }
                }
            }
        }
    }

    private void buildSceneStandard(SceneBuilder builder) {
        int mapCount = sceneMapLandData.length;

        for (int i = 0; i < mapCount; i++) {
            byte[] data = sceneMapLandData[i];

            if (data != null) {
                int originX = ((sceneMapIndex[i] >> 8) * 64) - sceneBaseTileX;
                int originZ = ((sceneMapIndex[i] & 0xff) * 64) - sceneBaseTileZ;
                builder.readTiles(data, originZ, originX, (sceneCenterZoneX - 6) * 8, (sceneCenterZoneZ - 6) * 8, level_collisions);
            }
        }

        for (int i = 0; i < mapCount; i++) {
            byte[] data = sceneMapLandData[i];
            if ((data == null) && (sceneCenterZoneZ < 800)) {
                int originX = ((sceneMapIndex[i] >> 8) * 64) - sceneBaseTileX;
                int originZ = ((sceneMapIndex[i] & 0xff) * 64) - sceneBaseTileZ;
                builder.stitchHeightmap(originX, originZ, 64, 64);
            }
        }

        out.writeOp(0);

        for (int i = 0; i < mapCount; i++) {
            byte[] data = sceneMapLocData[i];
            if (data != null) {
                int originX = ((sceneMapIndex[i] >> 8) * 64) - sceneBaseTileX;
                int originZ = ((sceneMapIndex[i] & 0xff) * 64) - sceneBaseTileZ;
                builder.readLocs(level_collisions, scene, originX, originZ, data);
            }
        }
    }

    private void clearTileFlags() {
        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    levelTileFlags[level][x][z] = 0;
                }
            }
        }
    }

    public void clearCaches() {
        Obj.modelCacheStatic.clear();
        Obj.modelCacheDynamic.clear();
        NPC.built_model_cache.clear();
        Item.modelCache.clear();
        Item.iconCache.clear();
        ScenePlayer.modelCache.clear();
        SpotAnim.modelCache.clear();
    }

    public void createMinimap(int level) {
        int[] pixels = imageMinimap.pixels;

        Arrays.fill(pixels, 0);

        for (int z = 1; z < 103; z++) {
            int offset = 52 + (48 * 512) + ((103 - z) * 512 * 4);

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

        for (int tileX = 0; tileX < 104; tileX++) {
            for (int tileZ = 0; tileZ < 104; tileZ++) {
                int bitset = scene.getGroundDecorationBitset(currentLevel, tileX, tileZ);

                if (bitset == 0) {
                    continue;
                }

                bitset = (bitset >> 14) & 0x7fff;

                int func = Obj.get(bitset).mapfunction;

                if (func < 0) {
                    continue;
                }

                int stx = tileX;
                int stz = tileZ;

                if ((func != 22) && (func != 29) && (func != 34) && (func != 36) && (func != 46) && (func != 47) && (func != 48)) {
                    byte byte0 = 104;
                    byte byte1 = 104;
                    int[][] flags = level_collisions[currentLevel].flags;
                    for (int i4 = 0; i4 < 10; i4++) {
                        int j4 = (int) (Math.random() * 4D);
                        if ((j4 == 0) && (stx > 0) && (stx > (tileX - 3)) && ((flags[stx - 1][stz] & 0x1280108) == 0)) {
                            stx--;
                        }
                        if ((j4 == 1) && (stx < (byte0 - 1)) && (stx < (tileX + 3)) && ((flags[stx + 1][stz] & 0x1280180) == 0)) {
                            stx++;
                        }
                        if ((j4 == 2) && (stz > 0) && (stz > (tileZ - 3)) && ((flags[stx][stz - 1] & 0x1280102) == 0)) {
                            stz--;
                        }
                        if ((j4 == 3) && (stz < (byte1 - 1)) && (stz < (tileZ + 3)) && ((flags[stx][stz + 1] & 0x1280120) == 0)) {
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

        int topCost = -99999999;

        SceneItem topObj = null;
        SceneItem bottomObj = null;
        SceneItem middleObj = null;

        for (SceneItem obj = (SceneItem) list.peekFront(); obj != null; obj = (SceneItem) list.prev()) {
            Item type = Item.get(obj.id);

            int cost = type.cost;

            if (type.stackable) {
                cost *= obj.count + 1;
            }

            if (cost > topCost) {
                topCost = cost;
                topObj = obj;
            }
        }

        list.pushFront(topObj);

        for (SceneItem obj = (SceneItem) list.peekFront(); obj != null; obj = (SceneItem) list.prev()) {
            if ((obj.id != topObj.id) && (bottomObj == null)) {
                bottomObj = obj;
            }
            if ((obj.id != topObj.id) && (obj.id != bottomObj.id) && (middleObj == null)) {
                middleObj = obj;
            }
        }

        int bitset = x + (z << 7) + 0x60000000;
        scene.setItemStack(topObj, bottomObj, middleObj, currentLevel, x, z, getHeightmapY(currentLevel, (x * 128) + 64, (z * 128) + 64), bitset);
    }

    public void pushNPCs(boolean important) {
        for (int i = 0; i < npcCount; i++) {
            SceneNPC npc = npcs[npcIDs[i]];
            int bitset = 0x20000000 + (npcIDs[i] << 14);

            if ((npc == null) || !npc.isVisible() || Objects.equals(important, npc.type.important)) {
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

            if (!npc.type._interactable) {
                bitset += 0x80000000;
            }

            scene.push_temporary(npc, currentLevel, npc.x, npc.z, getHeightmapY(currentLevel, npc.x, npc.z), npc.yaw, bitset, npc.needsForwardDrawPadding, ((npc.size - 1) * 64) + 60);
        }
    }

    public boolean wavereplay() {
        return Signlink.wavereplay();
    }

    public void handleInterfaceInput(Iface parent, int x, int y, int scrollPosition) {
        int mx = super.mouseX;
        int my = super.mouseY;

        if ((parent.type != Iface.TYPE_PARENT) || (parent.childID == null) || parent.hide) {
            return;
        }

        if ((mx < x) || (my < y) || (mx > (x + parent.width)) || (my > (y + parent.height))) {
            return;
        }

        int childCount = parent.childID.length;

        for (int i = 0; i < childCount; i++) {
            int childX = parent.childX[i] + x;
            int childZ = (parent.childY[i] + y) - scrollPosition;
            Iface child = Iface.instances[parent.childID[i]];

            childX += child.x;
            childZ += child.y;

            if (((child.delegateHover >= 0) || (child.hoverColor != 0)) && (mx >= childX) && (my >= childZ) && (mx < (childX + child.width)) && (my < (childZ + child.height))) {
                if (child.delegateHover >= 0) {
                    lastHoveredInterfaceID = child.delegateHover;
                } else {
                    lastHoveredInterfaceID = child.id;
                }
            }

            if (child.type == Iface.TYPE_PARENT) {
                handleInterfaceInput(child, childX, childZ, child.scrollPosition);

                if (child.scrollableHeight > child.height) {
                    handleScrollInput(childX + child.width, child.height, mx, my, child, childZ, true, child.scrollableHeight);
                }
            } else if (child.type == Iface.TYPE_INVENTORY) {
                handleInterfaceInventoryInput(childX, childZ, child);
            } else if ((mx >= childX) && (my >= childZ) && (mx < (childX + child.width)) && (my < (childZ + child.height))) {
                handleInterfaceOptionInput(child);
            }
        }
    }

    private void handleInterfaceOptionInput(Iface child) {
        if (child.optionType == Iface.OPTION_TYPE_STANDARD) {
            boolean override = false;

            if (child.contentType != 0) {
                override = handleSocialMenuOption(child);
            }

            if (!override) {
                addMenuOption(child.option, 315, 0, child.id, 0);
            }
        } else if (child.optionType == Iface.OPTION_TYPE_SPELL) {
            if (spellSelected == 0) {
                String prefix = child.spellAction;
                if (prefix.contains(" ")) {
                    prefix = prefix.substring(0, prefix.indexOf(" "));
                }
                addMenuOption(prefix + " @gre@" + child.spellName, 626, 0, child.id, 0);
            }
        } else if (child.optionType == Iface.OPTION_TYPE_CLOSE) {
            addMenuOption("Close", 200, 0, child.id, 0);
        } else if (child.optionType == Iface.OPTION_TYPE_TOGGLE) {
            addMenuOption(child.option, 169, 0, child.id, 0);
        } else if (child.optionType == Iface.OPTION_TYPE_SELECT) {
            addMenuOption(child.option, 646, 0, child.id, 0);
        } else if (child.optionType == Iface.OPTION_TYPE_CONTINUE) {
            if (!pressedContinueOption) {
                addMenuOption(child.option, 679, 0, child.id, 0);
            }
        }
    }

    private void handleInterfaceInventoryInput(int x, int y, Iface iface) {
        int slot = 0;

        for (int row = 0; row < iface.height; row++) {
            for (int col = 0; col < iface.width; col++) {
                int slotX = x + (col * (32 + iface.inventoryMarginX));
                int slotY = y + (row * (32 + iface.inventoryMarginY));

                if (slot < 20) {
                    slotX += iface.inventorySlotOffsetX[slot];
                    slotY += iface.inventorySlotOffsetY[slot];
                }

                if ((super.mouseX < slotX) || (super.mouseY < slotY) || (super.mouseX >= (slotX + 32)) || (super.mouseY >= (slotY + 32))) {
                    slot++;
                    continue;
                }

                hoveredSlot = slot;
                hoveredSlotParentID = iface.id;

                if (iface.inventorySlotObjID[slot] <= 0) {
                    slot++;
                    continue;
                }

                Item obj = Item.get(iface.inventorySlotObjID[slot] - 1);

                if ((objSelected == 1) && iface.inventoryInteractable) {
                    if ((iface.id != selectedObjInterfaceID) || (slot != selectedObjSlot)) {
                        addMenuOption("Use " + selectedObjName + " with @lre@" + obj.name, 870, slot, iface.id, obj.id);
                    }
                } else if ((spellSelected == 1) && iface.inventoryInteractable) {
                    if ((activeSpellFlags & 0x10) == 0x10) {
                        addMenuOption(spellCaption + " @lre@" + obj.name, 543, slot, iface.id, obj.id);
                    }
                } else {
                    if (iface.inventoryInteractable) {
                        for (int op = 4; op >= 3; op--) {
                            if ((obj.inventoryOptions != null) && (obj.inventoryOptions[op] != null)) {
                                addMenuOption(obj.inventoryOptions[op] + " @lre@" + obj.name, OBJ_IOP_ACTION[op], slot, iface.id, obj.id);
                            } else if (op == 4) {
                                addMenuOption("Drop @lre@" + obj.name, 847, slot, iface.id, obj.id);
                            }
                        }
                    }

                    if (iface.inventoryUsable) {
                        addMenuOption("Use @lre@" + obj.name, 447, slot, iface.id, obj.id);
                    }

                    if (iface.inventoryInteractable && (obj.inventoryOptions != null)) {
                        for (int op = 2; op >= 0; op--) {
                            if (obj.inventoryOptions[op] != null) {
                                addMenuOption(obj.inventoryOptions[op] + " @lre@" + obj.name, OBJ_IOP_ACTION[op], slot, iface.id, obj.id);
                            }
                        }
                    }

                    if (iface.inventoryOptions != null) {
                        for (int op = 4; op >= 0; op--) {
                            if (iface.inventoryOptions[op] != null) {
                                addMenuOption(iface.inventoryOptions[op] + " @lre@" + obj.name, INV_OP_ACTION[op], slot, iface.id, obj.id);
                            }
                        }
                    }

                    addMenuOption("Examine @lre@" + obj.name, 1125, slot, iface.id, obj.id);
                }
                slot++;
            }
        }
    }

    public void addMenuOption(String option, int action) {
        addMenuOption(option, action, 0, 0, 0);
    }

    public void addMenuOption(String option, int action, int a, int b, int c) {
        menuOption[menuSize] = option;
        menuAction[menuSize] = action;
        menuParamA[menuSize] = a;
        menuParamB[menuSize] = b;
        menuParamC[menuSize] = c;
        menuSize++;
    }

    public void drawScrollbar(int x, int y, int height, int scrollHeight, int scrollY) {
        imageScrollbar0.blit(x, y);
        imageScrollbar1.blit(x, (y + height) - 16);
        Draw2D.fillRect(x, y + 16, 16, height - 32, 0x23201b);
        int gripSize = ((height - 32) * height) / scrollHeight;
        if (gripSize < 8) {
            gripSize = 8;
        }
        int gripY = ((height - 32 - gripSize) * scrollY) / (scrollHeight - height);
        Draw2D.fillRect(x, y + 16 + gripY, 16, gripSize, 0x4d4233);
        Draw2D.drawLineY(x, y + 16 + gripY, gripSize, 0x766654);
        Draw2D.drawLineY(x + 1, y + 16 + gripY, gripSize, 0x766654);
        Draw2D.drawLineX(x, y + 16 + gripY, 16, 0x766654);
        Draw2D.drawLineX(x, y + 17 + gripY, 16, 0x766654);
        Draw2D.drawLineY(x + 15, y + 16 + gripY, gripSize, 0x332d25);
        Draw2D.drawLineY(x + 14, y + 17 + gripY, gripSize - 1, 0x332d25);
        Draw2D.drawLineX(x, y + 15 + gripY + gripSize, 16, 0x332d25);
        Draw2D.drawLineX(x + 1, y + 14 + gripY + gripSize, 15, 0x332d25);
    }

    public void readSyncNPCs() {
        entityRemovalCount = 0;
        entityUpdateCount = 0;

        readNPCs();
        readNewNPCs();
        readNPCUpdates();

        for (int k = 0; k < entityRemovalCount; k++) {
            int id = entityRemovalIDs[k];

            if (npcs[id].cycle != loopCycle) {
                npcs[id].type = null;
                npcs[id] = null;
            }
        }

        if (in.position != this.packetSize) {
            Signlink.reporterror(username + " size mismatch in getnpcpos - pos:" + in.position + " psize:" + this.packetSize);
            throw new RuntimeException("eek");
        }

        for (int i1 = 0; i1 < npcCount; i1++) {
            if (npcs[npcIDs[i1]] == null) {
                Signlink.reporterror(username + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
                throw new RuntimeException("eek");
            }
        }
    }

    public void handleChatSettingsInput() {
        if (super.mouseClickButton == 1) {
            if ((super.mouseClickX >= 6) && (super.mouseClickX <= 106) && (super.mouseClickY >= 467) && (super.mouseClickY <= 499)) {
                publicChatSetting = (publicChatSetting + 1) % 4;
                redrawPrivacySettings = true;
                redrawChatback = true;
                out.writeOp(95);
                out.write8(publicChatSetting);
                out.write8(privateChatSetting);
                out.write8(tradeChatSetting);
            }
            if ((super.mouseClickX >= 135) && (super.mouseClickX <= 235) && (super.mouseClickY >= 467) && (super.mouseClickY <= 499)) {
                privateChatSetting = (privateChatSetting + 1) % 3;
                redrawPrivacySettings = true;
                redrawChatback = true;
                out.writeOp(95);
                out.write8(publicChatSetting);
                out.write8(privateChatSetting);
                out.write8(tradeChatSetting);
            }
            if ((super.mouseClickX >= 273) && (super.mouseClickX <= 373) && (super.mouseClickY >= 467) && (super.mouseClickY <= 499)) {
                tradeChatSetting = (tradeChatSetting + 1) % 3;
                redrawPrivacySettings = true;
                redrawChatback = true;
                out.writeOp(95);
                out.write8(publicChatSetting);
                out.write8(privateChatSetting);
                out.write8(tradeChatSetting);
            }
            if ((super.mouseClickX >= 412) && (super.mouseClickX <= 512) && (super.mouseClickY >= 467) && (super.mouseClickY <= 499)) {
                useReportAbuseOption("");
            }
        }
    }

    public void updateVarp(int varpID) {
        if (varpID >= Varp.instances.length) {
            return;
        }

        int type = Objects.requireNonNullElse(Varp.instances[varpID].code,0);

        if (type == 0) {
            return;
        }

        int varp = varps[varpID];

        if (type == 1) {
            if (varp == 1) {
                Draw3D.buildPalette(0.90000000000000002D);
            }
            if (varp == 2) {
                Draw3D.buildPalette(0.80000000000000004D);
            }
            if (varp == 3) {
                Draw3D.buildPalette(0.69999999999999996D);
            }
            if (varp == 4) {
                Draw3D.buildPalette(0.59999999999999998D);
            }
            Item.iconCache.clear();
            redrawTitleBackground = true;
        }

        if (type == 3) {
            boolean active = midiEnabled;

            if (varp == 0) {
                midivol(midiEnabled, 0);
                midiEnabled = true;
            }

            if (varp == 1) {
                midivol(midiEnabled, -400);
                midiEnabled = true;
            }

            if (varp == 2) {
                midivol(midiEnabled, -800);
                midiEnabled = true;
            }

            if (varp == 3) {
                midivol(midiEnabled, -1200);
                midiEnabled = true;
            }

            if (varp == 4) {
                midiEnabled = false;
            }

            if ((midiEnabled != active)) {
                if (midiEnabled) {
                    song = nextSong;
                    songFading = true;
                    ondemand.request(2, song);
                } else {
                    stopMidi();
                }
                nextSongDelay = 0;
            }
        }

        if (type == 4) {
            if (varp == 0) {
                waveEnabled = true;
                setWaveVolume(0);
            }
            if (varp == 1) {
                waveEnabled = true;
                setWaveVolume(-400);
            }
            if (varp == 2) {
                waveEnabled = true;
                setWaveVolume(-800);
            }
            if (varp == 3) {
                waveEnabled = true;
                setWaveVolume(-1200);
            }
            if (varp == 4) {
                waveEnabled = false;
            }
        }

        if (type == 5) {
            mouseButtonsOption = varp;
        }

        if (type == 6) {
            chatEffects = varp;
        }

        if (type == 8) {
            splitPrivateChat = varp;
            redrawChatback = true;
        }

        if (type == 9) {
            bankArrangeMode = varp;
        }
    }

    public void draw2DEntityElements() {
        chatCount = 0;
        for (int index = -1; index < (playerCount + npcCount); index++) {
            SceneCharacter entity;

            if (index == -1) {
                entity = localPlayer;
            } else if (index < playerCount) {
                entity = players[playerIDs[index]];
            } else {
                entity = npcs[npcIDs[index - playerCount]];
            }

            if ((entity == null) || !entity.isVisible()) {
                continue;
            }

            if (entity instanceof SceneNPC) {
                NPC type = ((SceneNPC) entity).type;
                if (type.overrides != null) {
                    type = type.evaluate();
                }
                if (type == null) {
                    continue;
                }
            }

            if (index < playerCount) {
                ScenePlayer player = (ScenePlayer) entity;

                int y = 30;

                if (player.headicons != 0) {
                    projectFromGround(player, player.height + 15);

                    if (projectX > -1) {
                        for (int icon = 0; icon < 8; icon++) {
                            if ((player.headicons & (1 << icon)) != 0) {
                                imageHeadicons[icon].draw(projectX - 12, projectY - y);
                                y -= 25;
                            }
                        }
                    }
                }

                if ((index >= 0) && (hintType == 10) && (hintPlayer == playerIDs[index])) {
                    projectFromGround(player, player.height + 15);

                    if (projectX > -1) {
                        imageHeadicons[7].draw(projectX - 12, projectY - y);
                    }
                }
            } else {
                NPC type = ((SceneNPC) entity).type;

                if ((type._headicon >= 0) && (type._headicon < imageHeadicons.length)) {
                    projectFromGround(entity, entity.height + 15);

                    if (projectX > -1) {
                        imageHeadicons[type._headicon].draw(projectX - 12, projectY - 30);
                    }
                }
                if ((hintType == 1) && (hintNPC == npcIDs[index - playerCount]) && ((loopCycle % 20) < 10)) {
                    projectFromGround(entity, entity.height + 15);
                    if (projectX > -1) {
                        imageHeadicons[2].draw(projectX - 12, projectY - 28);
                    }
                }
            }

            if ((entity.chat != null) && ((index >= playerCount) || (publicChatSetting == 0) || (publicChatSetting == 3) || ((publicChatSetting == 1) && isFriend(((ScenePlayer) entity).name)))) {
                projectFromGround(entity, entity.height);

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

    private void drawHealth(SceneCharacter entity) {
        if (entity.combatCycle > loopCycle) {
            projectFromGround(entity, entity.height + 15);

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

    private void drawHitmarks(SceneCharacter entity) {
        for (int j = 0; j < 4; j++) {
            if (entity.damageCycle[j] <= loopCycle) {
                continue;
            }

            projectFromGround(entity, entity.height / 2);

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
            redrawSidebar = true;
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

    public void drawSidebar() {
        areaSidebar.bind();
        Draw3D.lineOffset = areaSidebarOffsets;
        imageInvback.blit(0, 0);

        if (sidebarInterfaceID != -1) {
            drawParentInterface(Iface.instances[sidebarInterfaceID], 0, 0, 0);
        } else if (tabInterfaceID[selectedTab] != -1) {
            drawParentInterface(Iface.instances[tabInterfaceID[selectedTab]], 0, 0, 0);
        }

        if (menuVisible && (menuArea == 1)) {
            drawMenu();
        }

        areaSidebar.draw(super.graphics, 553, 205);
        areaViewport.bind();
        Draw3D.lineOffset = areaViewportOffsets;
    }

    public void updateTextures(int cycle) {
        updateTexture(17, cycle);
        updateTexture(24, cycle);
        updateTexture(34, cycle);
    }

    private void updateTexture(int textureID, int cycle) {
        if (Draw3D.textureCycle[textureID] < cycle) {
            return;
        }

        Image8 texture = Draw3D.textures[textureID];

        int bottom = (texture.width * texture.height) - 1;
        int adjustment = texture.width * delta * 2; // moves texels down by 2 pixels

        byte[] buffer0 = texture.pixels;
        byte[] buffer1 = textureBuffer;

        for (int i = 0; i <= bottom; i++) {
            buffer1[i] = buffer0[(i - adjustment) & bottom];
        }

        texture.pixels = buffer1;
        textureBuffer = buffer0;

        // causes the texture to rebuild
        Draw3D.releaseTexture(textureID);
    }

    public void updateEntityChats() {
        for (int i = -1; i < playerCount; i++) {
            int playerID;

            if (i == -1) {
                playerID = LOCAL_PLAYER_INDEX;
            } else {
                playerID = playerIDs[i];
            }

            ScenePlayer player = players[playerID];

            if ((player != null) && (player.chatTimer > 0)) {
                player.chatTimer--;
                if (player.chatTimer == 0) {
                    player.chat = null;
                }
            }
        }

        for (int i = 0; i < npcCount; i++) {
            int id = npcIDs[i];
            SceneNPC npc = npcs[id];

            if ((npc != null) && (npc.chatTimer > 0)) {
                npc.chatTimer--;
                if (npc.chatTimer == 0) {
                    npc.chat = null;
                }
            }
        }
    }

    /**
     * Applies the cutscene camera properties to the main camera.
     *
     * @see #cutscene
     */
    public void applyCutscene() {
        // the following code updates the position of the camera
        int x = (cutsceneSrcLocalTileX * 128) + 64;
        int z = (cutsceneSrcLocalTileZ * 128) + 64;
        int y = getHeightmapY(currentLevel, x, z) - cutsceneSrcHeight;

        if (cameraX < x) {
            cameraX += cutsceneMoveSpeed + (((x - cameraX) * cutsceneMoveAcceleration) / 1000);
            if (cameraX > x) {
                cameraX = x;
            }
        }
        if (cameraX > x) {
            cameraX -= cutsceneMoveSpeed + (((cameraX - x) * cutsceneMoveAcceleration) / 1000);
            if (cameraX < x) {
                cameraX = x;
            }
        }
        if (cameraY < y) {
            cameraY += cutsceneMoveSpeed + (((y - cameraY) * cutsceneMoveAcceleration) / 1000);
            if (cameraY > y) {
                cameraY = y;
            }
        }
        if (cameraY > y) {
            cameraY -= cutsceneMoveSpeed + (((cameraY - y) * cutsceneMoveAcceleration) / 1000);
            if (cameraY < y) {
                cameraY = y;
            }
        }
        if (cameraZ < z) {
            cameraZ += cutsceneMoveSpeed + (((z - cameraZ) * cutsceneMoveAcceleration) / 1000);
            if (cameraZ > z) {
                cameraZ = z;
            }
        }
        if (cameraZ > z) {
            cameraZ -= cutsceneMoveSpeed + (((cameraZ - z) * cutsceneMoveAcceleration) / 1000);
            if (cameraZ < z) {
                cameraZ = z;
            }
        }

        // the following code updates the angle of the camera

        x = (cutsceneDstLocalTileX * 128) + 64;
        z = (cutsceneDstLocalTileZ * 128) + 64;
        y = getHeightmapY(currentLevel, x, z) - cutsceneDstHeight;

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
            cameraPitch += cutsceneRotateSpeed + (((pitch - cameraPitch) * cutsceneRotateAcceleration) / 1000);
            if (cameraPitch > pitch) {
                cameraPitch = pitch;
            }
        }

        if (cameraPitch > pitch) {
            cameraPitch -= cutsceneRotateSpeed + (((cameraPitch - pitch) * cutsceneRotateAcceleration) / 1000);
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
            cameraYaw += cutsceneRotateSpeed + ((deltaYaw * cutsceneRotateAcceleration) / 1000);
            cameraYaw &= 0x7ff;
        }

        if (deltaYaw < 0) {
            cameraYaw -= cutsceneRotateSpeed + ((-deltaYaw * cutsceneRotateAcceleration) / 1000);
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

        int mouseX = super.mouseX;
        int mouseY = super.mouseY;

        if (menuArea == 0) {
            mouseX -= 4;
            mouseY -= 4;
        }

        if (menuArea == 1) {
            mouseX -= 553;
            mouseY -= 205;
        }

        if (menuArea == 2) {
            mouseX -= 17;
            mouseY -= 357;
        }

        for (int i = 0; i < menuSize; i++) {
            int optionY = y + 31 + ((menuSize - 1 - i) * 15);
            int rgb = 0xffffff;

            if ((mouseX > x) && (mouseX < (x + w)) && (mouseY > (optionY - 13)) && (mouseY < (optionY + 3))) {
                rgb = 0xffff00;
            }

            fontBold12.drawStringTaggable(menuOption[i], x + 3, optionY, rgb, true);
        }
    }

    public void addFriend(long name37) {
        if (name37 == 0L) {
            return;
        }
        if ((friendCount >= 100) && (isMember != 1)) {
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
            redrawSidebar = true;
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
        int y00 = ((level_heightmap[realLevel][tileX][tileZ] * (128 - tileLocalX)) + (level_heightmap[realLevel][tileX + 1][tileZ] * tileLocalX)) >> 7;
        int y11 = ((level_heightmap[realLevel][tileX][tileZ + 1] * (128 - tileLocalX)) + (level_heightmap[realLevel][tileX + 1][tileZ + 1] * tileLocalX)) >> 7;
        return ((y00 * (128 - tileLocalZ)) + (y11 * tileLocalZ)) >> 7;
    }

    public void logout() {
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
        scene.reset();
        for (int i = 0; i < 4; i++) {
            level_collisions[i].reset();
        }
        System.gc();
        stopMidi();
        nextSong = -1;
        song = -1;
        nextSongDelay = 0;
    }

    public void validateCharacterDesign() {
        updateDesignModel = true;
        for (int part = 0; part < 7; part++) {
            designIdentikits[part] = -1;
            for (int kit = 0; kit < Identikit.count; kit++) {
                if (Boolean.TRUE.equals(Identikit.instances[kit].disabled) || (Identikit.instances[kit]._type != (part + (designGenderMale ? 0 : 7)))) {
                    continue;
                }
                designIdentikits[part] = kit;
                break;
            }
        }
    }

    public void readNewNPCs() {
        while ((in.bitPosition + 21) < (this.packetSize * 8)) {
            int id = in.readN(14);

            if (id == 16383) {
                break;
            }

            if (npcs[id] == null) {
                npcs[id] = new SceneNPC();
            }

            SceneNPC npc = npcs[id];
            npcIDs[npcCount++] = id;
            npc.cycle = loopCycle;

            int z = in.readN(5);
            int x = in.readN(5);

            if (z > 15) {
                z -= 32;
            }

            if (x > 15) {
                x -= 32;
            }

            int teleport = in.readN(1);
            npc.type = NPC.get(in.readN(12));

            if (in.readN(1) == 1) {
                entityUpdateIDs[entityUpdateCount++] = id;
            }

            npc.size = npc.type.size;
            npc.turnSpeed = npc.type._turn_speed;
            npc.seqWalkID = npc.type._animation_move;
            npc.seqTurnAroundID = npc.type._animation_turn_around;
            npc.seqTurnLeftID = npc.type._animation_turn_left;
            npc.seqTurnRightID = npc.type._animation_turn_right;
            npc.seqStandID = npc.type._animation_idle;
            npc.move(localPlayer.pathTileX[0] + x, localPlayer.pathTileZ[0] + z, teleport == 1);
        }
    }

    public void pushPlayers(boolean local) {
        if (((localPlayer.x >> 7) == flagSceneTileX) && ((localPlayer.z >> 7) == flagSceneTileZ)) {
            flagSceneTileX = 0;
        }

        int count = this.playerCount;

        if (local) {
            count = 1;
        }

        for (int i = 0; i < count; i++) {
            ScenePlayer player;
            int index;

            if (local) {
                player = localPlayer;
                index = LOCAL_PLAYER_INDEX << 14;
            } else {
                player = players[playerIDs[i]];
                index = playerIDs[i] << 14;
            }

            if ((player == null) || !player.isVisible()) {
                continue;
            }

            int stx = player.x >> 7;
            int stz = player.z >> 7;

            if ((stx < 0) || (stx >= 104) || (stz < 0) || (stz >= 104)) {
                continue;
            }

            if ((player.locModel != null) && (loopCycle >= player.locStartCycle) && (loopCycle < player.locStopCycle)) {
                player.y = getHeightmapY(currentLevel, player.x, player.z);
                scene.push(player, currentLevel, player.minSceneTileX, player.minSceneTileZ, (player.maxSceneTileX - player.minSceneTileX) + 1, (player.maxSceneTileZ - player.minSceneTileZ) + 1, player.x, player.z, player.y, player.yaw, index, (byte) 0, true);
                continue;
            }

            if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
                if (tileLastOccupiedCycle[stx][stz] == sceneCycle) {
                    continue;
                }
                tileLastOccupiedCycle[stx][stz] = sceneCycle;
            }

            player.y = getHeightmapY(currentLevel, player.x, player.z);
            scene.push_temporary(player, currentLevel, player.x, player.z, player.y, player.yaw, index, player.needsForwardDrawPadding, 60);
        }
    }

    /**
     * Handles an interface action.
     *
     * @param iface the interface.
     * @return <code>false</code> to suppress packet 185.
     */
    public boolean handleInterfaceAction(Iface iface) {
        int type = iface.contentType;

        if (friendlistStatus == 2) {
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
                        kit = Identikit.count - 1;
                    }
                    if ((direction == 1) && (++kit >= Identikit.count)) {
                        kit = 0;
                    }
                } while (Boolean.TRUE.equals(Identikit.instances[kit].disabled) || (Identikit.instances[kit]._type != (part + (designGenderMale ? 0 : 7))));

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
        if ((type == 324) && !designGenderMale) {
            designGenderMale = true;
            validateCharacterDesign();
        }
        if ((type == 325) && designGenderMale) {
            designGenderMale = false;
            validateCharacterDesign();
        }
        if (type == 326) {
            out.writeOp(101);
            out.write8(designGenderMale ? 0 : 1);
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
            if (reportAbuseInput.length() > 0) {
                out.writeOp(218);
                out.write64(StringUtil.toBase37(reportAbuseInput));
                out.write8(type - 601);
                out.write8(reportAbuseMuteOption ? 1 : 0);
            }
        }
        return false;
    }

    public void readPlayerUpdates() {
        for (int i = 0; i < entityUpdateCount; i++) {
            int playerID = entityUpdateIDs[i];
            ScenePlayer player = players[playerID];
            int updates = in.readU8();

            if ((updates & 0x40) != 0) {
                updates += in.readU8() << 8;
            }

            if ((updates & 0x400) != 0) {
                readPlayerForceMovement(player);
            }

            if ((updates & 0x100) != 0) {
                readPlayerSpotAnim(player);
            }

            if ((updates & 8) != 0) {
                readPlayerAnimation(player);
            }

            if ((updates & 4) != 0) {
                readPlayerBlurb(player);
            }

            if ((updates & 0x80) != 0) {
                readPlayerChat(player);
            }

            if ((updates & 1) != 0) {
                readPlayerTargetEntity(player);
            }

            if ((updates & 0x10) != 0) {
                readPlayerAppearance(playerID, player);
            }

            if ((updates & 2) != 0) {
                readPlayerTargetTile(player);
            }

            if ((updates & 0x20) != 0) {
                readPlayerDamage0(player);
            }

            if ((updates & 0x200) != 0) {
                readPlayerDamage1(player);
            }
        }
    }

    private void readPlayerForceMovement(ScenePlayer player) {
        player.forceMoveStartSceneTileX = in.readU8S();
        player.forceMoveStartSceneTileZ = in.readU8S();
        player.forceMoveEndSceneTileX = in.readU8S();
        player.forceMoveEndSceneTileZ = in.readU8S();
        player.forceMoveEndCycle = in.readU16LEA() + loopCycle;
        player.forceMoveStartCycle = in.readU16A() + loopCycle;
        player.forceMoveFaceDirection = in.readU8S();
        player.resetPath();
    }

    private void readPlayerSpotAnim(ScenePlayer player) {
        player.spotanimID = in.readU16LE();
        player.spotanimOffset = in.readU16();
        player.spotanimLastCycle = loopCycle + in.readU16();
        in.readU16(); // additional offset
        player.spotanimFrame = 0;
        player.spotanimCycle = 0;

        if (player.spotanimLastCycle > loopCycle) {
            player.spotanimFrame = -1;
        }

        if (player.spotanimID == 65535) {
            player.spotanimID = -1;
        }
    }

    private void readPlayerAnimation(ScenePlayer player) {
        int seqID = in.readU16LE();

        if (seqID == 65535) {
            seqID = -1;
        }
        if (seqID >= Animation.instances.length) {
            seqID = -1;
        }
        int delay = in.readU8C();

        if ((seqID == player.primarySeqID) && (seqID != -1)) {
            int style = Animation.instances[seqID]._replay_type;

            if (style == 1) {
                player.primarySeqFrame = 0;
                player.primarySeqCycle = 0;
                player.primarySeqDelay = delay;
                player.primarySeqLoop = 0;
            }

            if (style == 2) {
                player.primarySeqLoop = 0;
            }
        } else if ((seqID == -1) || (player.primarySeqID == -1) || (Animation.instances[seqID].priority >= Animation.instances[player.primarySeqID].priority)) {
            player.primarySeqID = seqID;
            player.primarySeqFrame = 0;
            player.primarySeqCycle = 0;
            player.primarySeqDelay = delay;
            player.primarySeqLoop = 0;
            player.seqPathLength = player.pathLength;
        }
    }

    private void readPlayerBlurb(ScenePlayer player) {
        player.chat = in.readString();

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

    private void readPlayerChat(ScenePlayer player) {
        int colorStyle = in.readU16LE();
        int role = in.readU8();
        int length = in.readU8();
        int start = in.position;

        if ((player.name != null) && player.visible) {
            long name37 = StringUtil.toBase37(player.name);
            boolean ignore = false;

            if (role <= 1) {
                for (int i = 0; i < ignoreCount; i++) {
                    if (ignoreName37[i] != name37) {
                        continue;
                    }
                    ignore = true;
                    break;
                }
            }

            if (!ignore && (overrideChat == 0)) {
                try {
                    String chat = in.readString();

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

        in.position = start + length;
    }

    private void readPlayerTargetEntity(ScenePlayer player) {
        player.targetID = in.readU16LE();

        if (player.targetID == 65535) {
            player.targetID = -1;
        }
    }

    private void readPlayerAppearance(int playerID, ScenePlayer player) {
        Buffer buffer = new Buffer(new byte[in.readU8C()]);
        in.read(buffer.data);
        playerAppearanceBuffer[playerID] = buffer;
        player.read(buffer);
    }

    private void readPlayerTargetTile(ScenePlayer player) {
        player.targetTileX = in.readU16LEA();
        player.targetTileZ = in.readU16LE();
    }

    private void readPlayerDamage0(ScenePlayer player) {
        int damage = in.readU8();
        int type = in.readU8A();
        player.hit(type, damage);
        player.combatCycle = loopCycle + 300;
        player.health = in.readU8C();
        player.totalHealth = in.readU8();
    }

    private void readPlayerDamage1(ScenePlayer player) {
        int damage = in.readU8();
        int type = in.readU8S();
        player.hit(type, damage);
        player.combatCycle = loopCycle + 300;
        player.health = in.readU8();
        player.totalHealth = in.readU8C();
    }

    public void drawMinimapLoc(int tileZ, int wallRGB, int tileX, int doorRGB, int level) {
        int bitset = scene.getWallBitset(level, tileX, tileZ);

        if (bitset != 0) {
            int info = scene.getInfo(level, tileX, tileZ, bitset);
            int rotation = (info >> 6) & 3;
            int kind = info & 0x1f;
            int rgb = wallRGB;

            if (bitset > 0) {
                rgb = doorRGB;
            }

            int[] dst = imageMinimap.pixels;
            int offset = 24624 + (tileX * 4) + ((103 - tileZ) * 512 * 4);

            int locID = (bitset >> 14) & 0x7fff;
            Obj type = Obj.get(locID);

            if (type.mapscene != -1) {
                Image8 icon = imageMapscene[type.mapscene];
                if (icon != null) {
                    int offsetX = ((type.width * 4) - icon.width) / 2;
                    int offsetY = ((type.length * 4) - icon.height) / 2;
                    icon.blit(48 + (tileX * 4) + offsetX, 48 + ((104 - tileZ - type.length) * 4) + offsetY);
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

        bitset = scene.getLocBitset(level, tileX, tileZ);

        if (bitset != 0) {
            int info = scene.getInfo(level, tileX, tileZ, bitset);
            int rotation = (info >> 6) & 3;
            int kind = info & 0x1f;
            int locID = (bitset >> 14) & 0x7fff;
            Obj type = Obj.get(locID);

            if (type.mapscene != -1) {
                Image8 icon = imageMapscene[type.mapscene];

                if (icon != null) {
                    int offsetX = ((type.width * 4) - icon.width) / 2;
                    int offsetY = ((type.length * 4) - icon.height) / 2;
                    icon.blit(48 + (tileX * 4) + offsetX, 48 + ((104 - tileZ - type.length) * 4) + offsetY);
                }
            } else if (kind == 9) {
                int rgb = 0xEEEEEE;

                if (bitset > 0) {
                    rgb = 0xEE0000;
                }

                int[] dst = imageMinimap.pixels;
                int offset = 24624 + (tileX * 4) + ((103 - tileZ) * 512 * 4);

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

        bitset = scene.getGroundDecorationBitset(level, tileX, tileZ);

        if (bitset != 0) {
            int locID = (bitset >> 14) & 0x7fff;
            Obj type = Obj.get(locID);

            if (type.mapscene != -1) {
                Image8 icon = imageMapscene[type.mapscene];

                if (icon != null) {
                    int offsetX = ((type.width * 4) - icon.width) / 2;
                    int offsetY = ((type.length * 4) - icon.height) / 2;
                    icon.blit(48 + (tileX * 4) + offsetX, 48 + ((104 - tileZ - type.length) * 4) + offsetY);
                }
            }
        }
    }

    public void loadTitleImages() throws IOException {
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
        drawProgress(10, "Connecting to fileserver");

        if (!flameActive) {
            flamesThread = true;
            flameActive = true;
            startThread(this, 2);
        }
    }

    public void updateSceneState() {
        if (sceneState == 1) {
            int state = checkScene();

            if ((state != 0) && ((System.currentTimeMillis() - sceneLoadStartTime) > 360000L)) {
                Signlink.reporterror(username + " glcfb " + serverSeed + "," + state + "," + filestores[0] + "," + ondemand.remaining() + "," + currentLevel + "," + sceneCenterZoneX + "," + sceneCenterZoneZ);
                sceneLoadStartTime = System.currentTimeMillis();
            }
        }

        if ((sceneState == 2) && (currentLevel != minimapLevel)) {
            minimapLevel = currentLevel;
            createMinimap(currentLevel);
        }
    }

    public int checkScene() {
        for (int i = 0; i < sceneMapLandData.length; i++) {
            if ((sceneMapLandData[i] == null) && (sceneMapLandFile[i] != -1)) {
                return -1;
            }
            if ((sceneMapLocData[i] == null) && (sceneMapLocFile[i] != -1)) {
                return -2;
            }
        }

        if (!isSceneLocsLoaded()) {
            return -3;
        }

        if (awaitingSync) {
            return -4;
        }

        sceneState = 2;
        SceneBuilder.level = currentLevel;
        buildScene();
        out.writeOp(121);
        return 0;
    }

    private boolean isSceneLocsLoaded() {
        boolean ok = true;
        for (int i = 0; i < sceneMapLocData.length; i++) {
            byte[] data = sceneMapLocData[i];

            if (data == null) {
                continue;
            }

            int originX = ((sceneMapIndex[i] >> 8) * 64) - sceneBaseTileX;
            int originZ = ((sceneMapIndex[i] & 0xff) * 64) - sceneBaseTileZ;

            if (sceneInstanced) {
                originX = 10;
                originZ = 10;
            }

            ok &= SceneBuilder.validateLocs(data, originX, originZ);
        }
        return ok;
    }

    public void pushProjectiles() {
        for (SceneProjectile proj = (SceneProjectile) projectiles.peekFront(); proj != null; proj = (SceneProjectile) projectiles.prev()) {
            if ((proj.level != currentLevel) || (loopCycle > proj.lastCycle)) {
                proj.unlink();
            } else if (loopCycle >= proj.startCycle) {
                if (proj.target > 0) {
                    SceneNPC npc = npcs[proj.target - 1];
                    if ((npc != null) && (npc.x >= 0) && (npc.x < 13312) && (npc.z >= 0) && (npc.z < 13312)) {
                        proj.updateVelocity(loopCycle, npc.z, getHeightmapY(proj.level, npc.x, npc.z) - proj.offsetY, npc.x);
                    }
                }
                if (proj.target < 0) {
                    int pid = -proj.target - 1;
                    ScenePlayer player;
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
                scene.push_temporary(proj, currentLevel, (int) proj.x, (int) proj.z, (int) proj.y, proj.yaw, -1, false, 60);
            }
        }
    }

    public void loadTitleBackground() throws IOException {
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
            OnDemandRequest request = ondemand.poll();

            if (request == null) {
                return;
            }

            if (request.store == 0) {
                Model.unpack(request.data, request.file);

                if ((ondemand.getModelFlags(request.file) & 0x62) != 0) {
                    redrawSidebar = true;
                    if (chatInterfaceID != -1) {
                        redrawChatback = true;
                    }
                }
            } else if (request.store == 1) {
                if (request.data != null) {
                    AnimationTransform.unpack(request.data);
                }
            } else if (request.store == 2) {
                if ((request.file == song) && (request.data != null)) {
                    midisave(songFading, request.data);
                }
            } else if (request.store == 3) {
                if (sceneState == 1) {
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
            } else if (request.store == 93) {
                if (ondemand.hasMapLocFile(request.file)) {
                    SceneBuilder.prefetchLocs(new Buffer(request.data), ondemand);
                }
            }
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
        }
        return Signlink.wavesave(src, len);
    }

    public void resetInterfaceAnimation(int interfaceID) {
        Iface iface = Iface.instances[interfaceID];

        for (int i = 0; i < iface.childID.length; i++) {
            if (iface.childID[i] == -1) {
                break;
            }

            Iface child = Iface.instances[iface.childID[i]];

            if (child.type == Iface.TYPE_UNUSED) {
                resetInterfaceAnimation(child.id);
            }

            child.seqFrame = 0;
            child.seqCycle = 0;
        }
    }

    public void drawTileHint() {
        if (hintType != 2) {
            return;
        }

        projectFromGround(((hintTileX - sceneBaseTileX) << 7) + hintOffsetX, hintHeight * 2, ((hintTileZ - sceneBaseTileZ) << 7) + hintOffsetZ);

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

        updateAnticheats();
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
                    redrawSidebar = true;
                }
                if (actionArea == 3) {
                    redrawChatback = true;
                }
                actionArea = 0;
            }
        }

        handleObjDragging();

        if (Scene.clickTileX != -1) {
            int x = Scene.clickTileX;
            int z = Scene.clickTileZ;
            boolean success = tryMove(0, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, 0, 0, 0, 0, 0, true);
            Scene.clickTileX = -1;

            if (success) {
                crossX = super.mouseClickX;
                crossY = super.mouseClickY;
                crossMode = 1;
                crossCycle = 0;
            }
        }

        if ((super.mouseClickButton == 1) && (modalMessage != null)) {
            modalMessage = null;
            redrawChatback = true;
            super.mouseClickButton = 0;
        }

        handleMouseInput();
        handleMinimapInput();
        handleTabInput();
        handleChatSettingsInput();

        if ((super.mouseButton == 1) || (super.mouseClickButton == 1)) {
            dragCycles++;
        }

        updateCamera();
        handleInputKey();
        updateIdleCycles();
        updateNetwork();
    }

    private void updateCamera() {
        if (sceneState == 2) {
            updateOrbitCamera();
        }

        if ((sceneState == 2) && cutscene) {
            applyCutscene();
        }

        for (int type = 0; type < 5; type++) {
            cameraModifierCycle[type]++;
        }
    }

    private void updateNetwork() {
        heartbeatTimer++;

        if (heartbeatTimer > 50) {
            out.writeOp(0);
        }

        try {
            bytesOut += out.position;

            if ((connection != null) && (out.position > 0)) {
                connection.write(out.data, 0, out.position);
                out.position = 0;
                heartbeatTimer = 0;
            }
        } catch (IOException _ex) {
            tryReconnect();
        } catch (Exception exception) {
            logout();
        }
    }

    private void handleObjDragging() {
        if (objDragArea == 0) {
            return;
        }

        objDragCycles++;

        // mouse is greater than 5px from grab point in any direction, trigger treshold
        if ((super.mouseX > (objGrabX + 5)) || (super.mouseX < (objGrabX - 5)) || (super.mouseY > (objGrabY + 5)) || (super.mouseY < (objGrabY - 5))) {
            objGrabThreshold = true;
        }

        if (super.mouseButton != 0) {
            return;
        }

        if (objDragArea == 2) {
            redrawSidebar = true;
        }

        if (objDragArea == 3) {
            redrawChatback = true;
        }

        objDragArea = 0;

        // mouse moved at least 5px and have been holding obj for 100ms or longer
        if (objGrabThreshold && (objDragCycles >= 5)) {
            hoveredSlotParentID = -1;
            handleInput();

            if ((hoveredSlotParentID == objDragInterfaceID) && (hoveredSlot != objDragSlot)) {
                Iface iface = Iface.instances[objDragInterfaceID];

                // mode 0 = swap
                // mode 1 = insert
                int mode = 0;

                if ((bankArrangeMode == 1) && (iface.contentType == 206)) {
                    mode = 1;
                }

                if (iface.inventorySlotObjID[hoveredSlot] <= 0) {
                    mode = 0;
                }

                if (iface.inventoryMoveReplaces) {
                    int src = objDragSlot;
                    int dst = hoveredSlot;
                    iface.inventorySlotObjID[dst] = iface.inventorySlotObjID[src];
                    iface.inventorySlotObjCount[dst] = iface.inventorySlotObjCount[src];
                    iface.inventorySlotObjID[src] = -1;
                    iface.inventorySlotObjCount[src] = 0;
                } else if (mode == 1) {
                    int src = objDragSlot;
                    for (int dst = hoveredSlot; src != dst; ) {
                        if (src > dst) {
                            iface.inventorySwap(src, src - 1);
                            src--;
                        } else {
                            iface.inventorySwap(src, src + 1);
                            src++;
                        }
                    }
                } else {
                    iface.inventorySwap(objDragSlot, hoveredSlot);
                }
                out.writeOp(214);
                out.write16LEA(objDragInterfaceID);
                out.write8C(mode);
                out.write16LEA(objDragSlot);
                out.write16LE(hoveredSlot);
            }
        } else if (((mouseButtonsOption == 1) || isAddFriendOption(menuSize - 1)) && (menuSize > 2)) {
            showContextMenu();
        } else if (menuSize > 0) {
            useMenuOption(menuSize - 1);
        }
        actionCycles = 10;
        super.mouseClickButton = 0;
    }

    private void updateAnticheats() {
        updateMouseRecorder();

        if (super.mouseClickButton != 0) {
            sendMouseInput();
        }

        if (sendCameraDelay > 0) {
            sendCameraDelay--;
        }

        if ((actionKey[1]) || (actionKey[2]) || (actionKey[3]) || (actionKey[4])) {
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
    }

    private void sendMouseInput() {
        long delta = (super.mouseClickTime - prevMousePressTime) / 50L;
        if (delta > 4095L) {
            delta = 4095L;
        }
        prevMousePressTime = super.mouseClickTime;
        int y = super.mouseClickY;
        if (y < 0) {
            y = 0;
        } else if (y > 502) {
            y = 502;
        }
        int x = super.mouseClickX;
        if (x < 0) {
            x = 0;
        } else if (x > 764) {
            x = 764;
        }
        int pos = (y * 765) + x;
        int button = 0;
        if (super.mouseClickButton == 2) {
            button = 1;
        }
        int deltaInt = (int) delta;
        out.writeOp(241);
        out.write32((deltaInt << 20) + (button << 19) + pos);
    }

    private void updateMouseRecorder() {
        synchronized (mouseRecorder.lock) {
            if (flagged) {
                if ((super.mouseClickButton != 0) || (mouseRecorder.length >= 40)) {
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
    }

    private void updateIdleCycles() {
        super.idleCycles++;

        if (super.idleCycles > 4500) {
            idleTimeout = 250;
            super.idleCycles -= 500;
            out.writeOp(202);
        }
    }

    public void clearTemporaryLocs() {
        SceneTemporaryObject loc = (SceneTemporaryObject) temporaryLocs.peekFront();
        for (; loc != null; loc = (SceneTemporaryObject) temporaryLocs.prev()) {
            if (loc.duration == -1) {
                loc.delay = 0;
                storeLoc(loc);
            } else {
                loc.unlink();
            }
        }
    }

    /**
     * Prepares all the title screen (flames, background, buttons, etc.) and unloads the game screen
     * components if the title screen hasn't been prepared already.
     *
     * @throws IOException
     */
    public void loadTitle() throws IOException {
        if (imageTitle2 != null) {
            return;
        }
        areaChatback = null;
        areaMapback = null;
        areaSidebar = null;
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
            loadTitleBackground();
            loadTitleImages();
        }
        redrawTitleBackground = true;
    }

    public void handleScrollInput(int left, int height, int mouseX, int mouseY, Iface iface, int top,
                                  boolean redraw, int scrollableHeight) {
        if (scrollGrabbed) {
            scrollInputPadding = 32;
        } else {
            scrollInputPadding = 0;
        }

        scrollGrabbed = false;

        if ((mouseX >= left) && (mouseX < (left + 16)) && (mouseY >= top) && (mouseY < (top + 16))) {
            iface.scrollPosition -= dragCycles * 4;
            if (redraw) {
                redrawSidebar = true;
            }
        } else if ((mouseX >= left) && (mouseX < (left + 16)) && (mouseY >= ((top + height) - 16)) && (mouseY < (top + height))) {
            iface.scrollPosition += dragCycles * 4;
            if (redraw) {
                redrawSidebar = true;
            }
        } else if ((mouseX >= (left - scrollInputPadding)) && (mouseX < (left + 16 + scrollInputPadding)) && (mouseY >= (top + 16)) && (mouseY < ((top + height) - 16)) && (dragCycles > 0)) {
            int gripSize = ((height - 32) * height) / scrollableHeight;
            if (gripSize < 8) {
                gripSize = 8;
            }
            int gripY = mouseY - top - 16 - (gripSize / 2);
            int maxY = height - 32 - gripSize;
            iface.scrollPosition = ((scrollableHeight - height) * gripY) / maxY;
            if (redraw) {
                redrawSidebar = true;
            }
            scrollGrabbed = true;
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
            Obj loc = Obj.get(locID);
            int width;
            int length;
            if ((angle == 0) || (angle == 2)) {
                width = loc.width;
                length = loc.length;
            } else {
                width = loc.length;
                length = loc.width;
            }
            int interactionFlags = loc.unreachable_flags;
            if (angle != 0) {
                interactionFlags = ((interactionFlags << angle) & 0xf) + (interactionFlags >> (4 - angle));
            }
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, 0, width, length, 0, interactionFlags, false);
        } else {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], x, z, type + 1, 0, 0, angle, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        return true;
    }

    public FileArchive loadArchive(int fileId, String caption, String fileName, int expectedChecksum,
                                   int progress) throws
            IOException {
        byte[] data = null;
        int wait = 5;

        try {
            if (filestores[0] != null) {
                data = filestores[0].read(fileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data != null) {
            crc32.reset();
            crc32.update(data);
            if ((int) crc32.getValue() != expectedChecksum) {
                //data = null;
            }
        }

        if (data != null) {
            return new FileArchive(data);
        }

        int checksumErrors = 0;
        while (data == null) {
            String error = "Unknown error";
            drawProgress(progress, "Requesting " + caption);

            try {
                int lastPercent = 0;

                DataInputStream in = openURL("archive/" + fileName);
                Buffer buffer = new Buffer(new byte[6]);
                in.readFully(buffer.data, 0, 6);
                buffer.position = 3;

                int fileSize = buffer.read24() + 6;
                int totalRead = 6;

                data = new byte[fileSize];

                System.arraycopy(buffer.data, 0, data, 0, 6);

                while (totalRead < fileSize) {
                    int remaining = fileSize - totalRead;

                    if (remaining > 1000) {
                        remaining = 1000;
                    }

                    int read = in.read(data, totalRead, remaining);

                    if (read < 0) {
                        error = "Length error: " + totalRead + "/" + fileSize;
                        throw new IOException("EOF");
                    }

                    totalRead += read;

                    int percent = (totalRead * 100) / fileSize;

                    if (percent != lastPercent) {
                        drawProgress(progress, "Loading " + caption + " - " + percent + "%");
                    }

                    lastPercent = percent;
                }

                in.close();

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
                    int calculatedChecksum = (int) crc32.getValue();

                    if (calculatedChecksum != expectedChecksum) {
                        //data = null;
                        checksumErrors++;
                        error = "Checksum error: " + calculatedChecksum;
                    }
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
                if (error.equals("Unknown error")) {
                    error = "Connection error";
                }
                data = null;
            } catch (NullPointerException _ex) {
                error = "Null error";
                data = null;
                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (ArrayIndexOutOfBoundsException _ex) {
                error = "Bounds error";
                data = null;
                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (Exception _ex) {
                error = "Unexpected error";
                data = null;
                if (!Signlink.reporterror) {
                    return null;
                }
            }

            if (data == null) {
                for (int remaining = wait; remaining > 0; remaining--) {
                    if (checksumErrors >= 3) {
                        drawProgress(progress, "Game updated - please reload page");
                        remaining = 10;
                    } else {
                        drawProgress(progress, error + " - Retrying in " + remaining);
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
            logout();
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
        ingame = false;
        loginAttempts = 0;
        login(username, password, true);

        if (!ingame) {
            logout();
        }

        try {
            this.connection.close();
        } catch (Exception ignored) {
        }
    }

    public void useMenuOption(int optionID) {
        if (optionID < 0) {
            return;
        }

        if (chatbackInputType != 0) {
            chatbackInputType = 0;
            redrawChatback = true;
        }

        int action = menuAction[optionID];
        int a = menuParamA[optionID];
        int b = menuParamB[optionID];
        int c = menuParamC[optionID];

        if (action >= 2000) {
            action -= 2000;
        }

        switch (action) {
            case 582:
                sendUseObjOnNPC(c);
                break;
            case 234:
                useGroundObjOption2(a, b, c);
                break;
            case 62:
                if (interactWithLoc(c, a, b)) {
                    useObjOnLoc(a, b, c);
                }
                break;
            case 511:
                useObjOnGroundObj(a, b, c);
                break;
            case 74:
                useObjOption0(a, b, c);
                break;
            case 315:
                useButton(b);
                break;
            case 561:
                usePlayerOption0(c);
                break;
            case 20:
                useNPCOption0(c);
                break;
            case 779:
                usePlayerOption1(c);
                break;
            case 516:
                useWalkHereOption(a, b);
                break;
            case 1062:
                useLocOption4(a, b, c);
                break;
            case 679:
                if (!pressedContinueOption) {
                    out.writeOp(40);
                    out.write16(b);
                    pressedContinueOption = true;
                }
                break;
            case 431:
                useInventoryOption3(a, b, c);
                break;
            case 337:
            case 42:
            case 792:
            case 322: {
                String ption = menuOption[optionID];
                int tag = ption.indexOf("@whi@");
                if (tag != -1) {
                    long name = StringUtil.toBase37(ption.substring(tag + 5).trim());

                    if (action == 337) {
                        addFriend(name);
                    }
                    if (action == 42) {
                        addIgnore(name);
                    }
                    if (action == 792) {
                        removeFriend(name);
                    }
                    if (action == 322) {
                        removeIgnore(name);
                    }
                }
                break;
            }
            case 53:
                useInventoryOption4(a, b, c);
                break;
            case 539:
                useObjOption2(a, b, c);
                break;
            case 484:
            case 6: {
                String option = menuOption[optionID];
                int tag = option.indexOf("@whi@");

                if (tag != -1) {
                    option = option.substring(tag + 5).trim();
                    acceptPlayerRequest(action, StringUtil.formatName(StringUtil.fromBase37(StringUtil.toBase37(option))));
                }
                break;
            }
            case 870:
                useObjOnObj(a, b, c);
                break;
            case 847:
                useObjOption4(a, b, c);
                break;
            case 626:
                selectSpell(b);
                return;
            case 78:
                useInventoryOption1(a, b, c);
                break;
            case 27:
                usePlayerOption2(c);
                break;
            case 213:
                useGroundObjOption4(a, b, c);
                break;
            case 632:
                useInventoryOption0(a, b, c);
                break;
            case 493:
                useObjOption3(a, b, c);
                break;
            case 652:
                useGroundObjOption0(a, b, c);
                break;
            case 94:
                castSpellOnGroundObj(a, b, c);
                break;
            case 646:
                useSelectOption(b);
                break;
            case 225:
                useNPCOption2(c);
                break;
            case 965:
                useNPCOption3(c);
                break;
            case 413:
                castSpellOnNPC(c);
                break;
            case 200:
                closeInterfaces();
                break;
            case 1025:
                examineNPC(c);
                break;
            case 900:
                useLocOption1(a, b, c);
                break;
            case 412:
                useNPCOption1(c);
                break;
            case 365:
                castSpellOnPlayer(c);
                break;
            case 729:
                usePlayerOption4(c);
                break;
            case 577:
                usePlayerOption3(c);
                break;
            case 956:
                if (interactWithLoc(c, a, b)) {
                    castSpellOnLoc(a, b, c);
                }
                break;
            case 567:
                useGroundObjOption1(a, b, c);
                break;
            case 867:
                useInventoryOption2(a, b, c);
                break;
            case 543:
                castSpellOnObj(a, b, c);
                break;
            case 606: {
                String option = menuOption[optionID];
                int tag = option.indexOf("@whi@");
                if (tag != -1) {
                    useReportAbuseOption(option.substring(tag + 5).trim());
                }
                break;
            }
            case 491:
                useObjOnPlayer(c);
                break;
            case 639: {
                String option = menuOption[optionID];
                int tag = option.indexOf("@whi@");
                if (tag != -1) {
                    promptMessageFriend(StringUtil.toBase37(option.substring(tag + 5).trim()));
                }
                break;
            }
            case 454:
                useObjOption1(a, b, c);
                break;
            case 478:
                useNPCOption4(c);
                break;
            case 113:
                useLocOption2(a, b, c);
                break;
            case 872:
                useLocOption3(a, b, c);
                break;
            case 502:
                useLocOption0(a, b, c);
                break;
            case 1125:
                examineInventoryObj(a, b, c);
                break;
            case 169:
                useToggleOption(b);
                break;
            case 447:
                selectObj(a, b, c);
                return;
            case 1226:
                examineLoc(c);
                break;
            case 244:
                useGroundObjOption3(a, b, c);
                break;
            case 1448:
                examineObj(c);
                break;
        }

        objSelected = 0;
        spellSelected = 0;
        redrawSidebar = true;
    }

    private void useGroundObjOption2(int tileX, int tileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(236);
        out.write16LE(tileZ + sceneBaseTileZ);
        out.write16(objID);
        out.write16LE(tileX + sceneBaseTileX);
    }

    private void useObjOnLoc(int tileX, int tileZ, int locBitset) {
        out.writeOp(192);
        out.write16(selectedObjInterfaceID);
        out.write16LE((locBitset >> 14) & 0x7fff);
        out.write16LEA(tileZ + sceneBaseTileZ);
        out.write16LE(selectedObjSlot);
        out.write16LEA(tileX + sceneBaseTileX);
        out.write16(selectedObjID);
    }

    private void useObjOnGroundObj(int tileX, int tileZ, int groundObjID) {
        boolean k = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!k) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(25);
        out.write16LE(selectedObjInterfaceID);
        out.write16A(selectedObjID);
        out.write16(groundObjID);
        out.write16A(tileZ + sceneBaseTileZ);
        out.write16LEA(selectedObjSlot);
        out.write16(tileX + sceneBaseTileX);
    }

    private void useObjOption0(int slot, int interfaceID, int objID) {
        out.writeOp(122);
        out.write16LEA(interfaceID);
        out.write16A(slot);
        out.write16LE(objID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useButton(int b) {
        Iface iface = Iface.instances[b];
        boolean notify = true;
        if (iface.contentType > 0) {
            notify = handleInterfaceAction(iface);
        }
        if (notify) {
            out.writeOp(185);
            out.write16(b);
        }
    }

    private void usePlayerOption0(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(128);
        out.write16(playerID);
    }

    private void useNPCOption0(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(155);
        out.write16LE(npcID);
    }

    private void usePlayerOption1(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(153);
        out.write16LE(playerID);
    }

    private void useWalkHereOption(int mouseX, int mouseY) {
        if (!menuVisible) {
            scene.click(super.mouseClickY - 4, super.mouseClickX - 4);
        } else {
            scene.click(mouseY - 4, mouseX - 4);
        }
    }

    private void useLocOption4(int tileX, int tileZ, int locBitset) {
        interactWithLoc(locBitset, tileX, tileZ);
        out.writeOp(228);
        out.write16A((locBitset >> 14) & 0x7fff);
        out.write16A(tileZ + sceneBaseTileZ);
        out.write16(tileX + sceneBaseTileX);
    }

    private void useInventoryOption3(int slot, int interfaceID, int objID) {
        out.writeOp(129);
        out.write16A(slot);
        out.write16(interfaceID);
        out.write16A(objID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useInventoryOption4(int a, int interfaceID, int c) {
        out.writeOp(135);
        out.write16LE(a);
        out.write16A(interfaceID);
        out.write16LE(c);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = a;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useObjOption2(int slot, int interfaceID, int c) {
        out.writeOp(16);
        out.write16A(c);
        out.write16LEA(slot);
        out.write16LEA(interfaceID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void acceptPlayerRequest(int action, String playerName) {
        boolean found = false;
        for (int i = 0; i < playerCount; i++) {
            ScenePlayer player = players[playerIDs[i]];

            if ((player == null) || (player.name == null) || !player.name.equalsIgnoreCase(playerName)) {
                continue;
            }

            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);

            // trade
            if (action == 484) {
                out.writeOp(139);
                out.write16LE(playerIDs[i]);
            }

            // challenge
            if (action == 6) {
                out.writeOp(128);
                out.write16(playerIDs[i]);
            }

            found = true;
            break;
        }

        if (!found) {
            addMessage(0, "", "Unable to find " + playerName);
        }
    }

    private void useObjOnObj(int slot, int interfaceID, int objID) {
        out.writeOp(53);
        out.write16(slot);
        out.write16A(selectedObjSlot);
        out.write16LEA(objID);
        out.write16(selectedObjInterfaceID);
        out.write16LE(selectedObjID);
        out.write16(interfaceID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useObjOption4(int slot, int interfaceID, int objID) {
        out.writeOp(87);
        out.write16A(objID);
        out.write16(interfaceID);
        out.write16A(slot);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void selectSpell(int interfaceID) {
        Iface iface = Iface.instances[interfaceID];
        spellSelected = 1;
        activeSpellID = interfaceID;
        activeSpellFlags = iface.spellFlags;
        objSelected = 0;
        redrawSidebar = true;

        String prefix = iface.spellAction;

        if (prefix.contains(" ")) {
            prefix = prefix.substring(0, prefix.indexOf(" "));
        }

        String suffix = iface.spellAction;

        if (suffix.contains(" ")) {
            suffix = suffix.substring(suffix.indexOf(" ") + 1);
        }

        spellCaption = prefix + " " + iface.spellName + " " + suffix;

        if (activeSpellFlags == 0x10) {
            redrawSidebar = true;
            selectedTab = 3;
            redrawSideicons = true;
        }
    }

    private void useInventoryOption1(int slot, int interfaceID, int objID) {
        out.writeOp(117);
        out.write16LEA(interfaceID);
        out.write16LEA(objID);
        out.write16LE(slot);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void usePlayerOption2(int c) {
        ScenePlayer player = players[c];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(73);
        out.write16LE(c);
    }

    private void useGroundObjOption4(int tileX, int tileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(79);
        out.write16LE(tileZ + sceneBaseTileZ);
        out.write16(objID);
        out.write16A(tileX + sceneBaseTileX);
    }

    private void useInventoryOption0(int slot, int interfaceID, int objID) {
        out.writeOp(145);
        out.write16A(interfaceID);
        out.write16A(slot);
        out.write16A(objID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useObjOption3(int slot, int interfaceID, int objID) {
        out.writeOp(75);
        out.write16LEA(interfaceID);
        out.write16LE(slot);
        out.write16A(objID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useGroundObjOption0(int tileX, int tileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(156);
        out.write16A(tileX + sceneBaseTileX);
        out.write16LE(tileZ + sceneBaseTileZ);
        out.write16LEA(objID);
    }

    private void castSpellOnGroundObj(int tileX, int tileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(181);
        out.write16LE(tileZ + sceneBaseTileZ);
        out.write16(objID);
        out.write16LE(tileX + sceneBaseTileX);
        out.write16A(activeSpellID);
    }

    private void useSelectOption(int interfaceID) {
        out.writeOp(185);
        out.write16(interfaceID);
        Iface iface = Iface.instances[interfaceID];
        if ((iface.scripts != null) && (iface.scripts[0][0] == 5)) {
            int varpID = iface.scripts[0][1];
            if (varps[varpID] != iface.scriptOperand[0]) {
                varps[varpID] = iface.scriptOperand[0];
                updateVarp(varpID);
                redrawSidebar = true;
            }
        }
    }

    private void useNPCOption2(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(17);
        out.write16LEA(npcID);
    }

    private void useNPCOption3(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(21);
        out.write16(npcID);
    }

    private void castSpellOnNPC(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(131);
        out.write16LEA(npcID);
        out.write16A(activeSpellID);
    }

    private void examineNPC(int npcID) {
        SceneNPC npc = npcs[npcID];

        if (npc == null) {
            return;
        }

        NPC type = npc.type;

        if (type.overrides != null) {
            type = type.evaluate();
        }

        if (type == null) {
            return;
        }

        String message;
        if (type.examine != null) {
            message = type.examine;
        } else {
            message = "It's a " + type.name + ".";
        }
        addMessage(0, "", message);
    }

    private void useLocOption1(int a, int b, int c) {
        interactWithLoc(c, a, b);
        out.writeOp(252);
        out.write16LEA((c >> 14) & 0x7fff);
        out.write16LE(b + sceneBaseTileZ);
        out.write16A(a + sceneBaseTileX);
    }

    private void useNPCOption1(int c) {
        SceneNPC npc = npcs[c];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(72);
        out.write16A(c);
    }

    private void castSpellOnPlayer(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(249);
        out.write16A(playerID);
        out.write16LE(activeSpellID);
    }

    private void usePlayerOption4(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(39);
        out.write16LE(playerID);
    }

    private void usePlayerOption3(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(139);
        out.write16LE(playerID);
    }

    private void castSpellOnLoc(int tileX, int tileZ, int locBitset) {
        out.writeOp(35);
        out.write16LE(tileX + sceneBaseTileX);
        out.write16A(activeSpellID);
        out.write16A(tileZ + sceneBaseTileZ);
        out.write16LE((locBitset >> 14) & 0x7fff);
    }

    private void useGroundObjOption1(int tileX, int tileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(23);
        out.write16LE(tileZ + sceneBaseTileZ);
        out.write16LE(objID);
        out.write16LE(tileX + sceneBaseTileX);
    }

    private void useInventoryOption2(int slot, int interfaceID, int objID) {
        out.writeOp(43);
        out.write16LE(interfaceID);
        out.write16A(objID);
        out.write16A(slot);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void castSpellOnObj(int slot, int interfaceID, int objID) {
        out.writeOp(237);
        out.write16(slot);
        out.write16A(objID);
        out.write16(interfaceID);
        out.write16A(activeSpellID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useReportAbuseOption(String input) {
        if (viewportInterfaceID == -1) {
            closeInterfaces();
            reportAbuseInput = input;
            reportAbuseMuteOption = false;
            for (int i = 0; i < Iface.instances.length; i++) {
                if ((Iface.instances[i] == null) || (Iface.instances[i].contentType != 327)) {
                    continue;
                }
                reportAbuseInterfaceID = viewportInterfaceID = Iface.instances[i].parentID;
                break;
            }
        } else {
            addMessage(0, "", "Please close the interface you have open before using 'report abuse'");
        }
    }

    private void useObjOnPlayer(int playerID) {
        ScenePlayer player = players[playerID];
        if (player == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], player.pathTileX[0], player.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(14);
        out.write16A(selectedObjInterfaceID);
        out.write16(playerID);
        out.write16(selectedObjID);
        out.write16LE(selectedObjSlot);
    }

    private void promptMessageFriend(long name37) {
        int friendSlot = -1;

        for (int i = 0; i < friendCount; i++) {
            if (friendName37[i] == name37) {
                friendSlot = i;
                break;
            }
        }

        if ((friendSlot != -1) && (friendWorld[friendSlot] > 0)) {
            redrawChatback = true;
            chatbackInputType = 0;
            showSocialInput = true;
            socialInput = "";
            socialAction = 3;
            inputFriendName37 = friendName37[friendSlot];
            socialMessage = "Enter message to send to " + friendName[friendSlot];
        }
    }

    private void useObjOption1(int slot, int interfaceID, int objID) {
        out.writeOp(41);
        out.write16(objID);
        out.write16A(slot);
        out.write16A(interfaceID);
        actionCycles = 0;
        actionInterfaceID = interfaceID;
        actionSlot = slot;
        actionArea = 2;
        if (Iface.instances[interfaceID].parentID == viewportInterfaceID) {
            actionArea = 1;
        }
        if (Iface.instances[interfaceID].parentID == chatInterfaceID) {
            actionArea = 3;
        }
    }

    private void useNPCOption4(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(18);
        out.write16LE(npcID);
    }

    private void useLocOption2(int a, int b, int c) {
        interactWithLoc(c, a, b);
        out.writeOp(70);
        out.write16LE(a + sceneBaseTileX);
        out.write16(b + sceneBaseTileZ);
        out.write16LEA((c >> 14) & 0x7fff);
    }

    private void useLocOption3(int a, int b, int c) {
        interactWithLoc(c, a, b);
        out.writeOp(234);
        out.write16LEA(a + sceneBaseTileX);
        out.write16A((c >> 14) & 0x7fff);
        out.write16LEA(b + sceneBaseTileZ);
    }

    private void useLocOption0(int a, int b, int c) {
        interactWithLoc(c, a, b);
        out.writeOp(132);
        out.write16LEA(a + sceneBaseTileX);
        out.write16((c >> 14) & 0x7fff);
        out.write16A(b + sceneBaseTileZ);
    }

    private void examineInventoryObj(int slot, int interfaceID, int objID) {
        Item type = Item.get(objID);
        Iface iface = Iface.instances[interfaceID];
        String message;
        if ((iface != null) && (iface.inventorySlotObjCount[slot] >= 0x186a0)) {
            message = iface.inventorySlotObjCount[slot] + " x " + type.name;
        } else if (type.examine != null) {
            message = type.examine;
        } else {
            message = "It's a " + type.name + ".";
        }
        addMessage(0, "", message);
    }

    private void useToggleOption(int interfaceID) {
        out.writeOp(185);
        out.write16(interfaceID);
        Iface iface = Iface.instances[interfaceID];

        if ((iface.scripts != null) && (iface.scripts[0][0] == 5)) {
            int varpID = iface.scripts[0][1];
            varps[varpID] = 1 - varps[varpID];
            updateVarp(varpID);
            redrawSidebar = true;
        }
    }

    private void selectObj(int slot, int interfaceID, int objID) {
        objSelected = 1;
        selectedObjSlot = slot;
        selectedObjInterfaceID = interfaceID;
        selectedObjID = objID;
        selectedObjName = Item.get(objID).name;
        spellSelected = 0;
        redrawSidebar = true;
    }

    private void examineLoc(int bitset) {
        int locID = (bitset >> 14) & 0x7fff;
        Obj type = Obj.get(locID);
        String message;
        if (type.examine != null) {
            message = type.examine;
        } else {
            message = "It's a " + type.name + ".";
        }
        addMessage(0, "", message);
    }

    private void useGroundObjOption3(int localTileX, int localTileZ, int objID) {
        boolean ok = tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], localTileX, localTileZ, 0, 0, 0, 0, 0, false);
        if (!ok) {
            tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], localTileX, localTileZ, 0, 1, 1, 0, 0, false);
        }
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(253);
        out.write16LE(localTileX + sceneBaseTileX);
        out.write16LEA(localTileZ + sceneBaseTileZ);
        out.write16A(objID);
    }

    private void examineObj(int objID) {
        Item obj = Item.get(objID);
        String message;
        if (obj.examine != null) {
            message = obj.examine;
        } else {
            message = "It's a " + obj.name + ".";
        }
        addMessage(0, "", message);
    }

    private void sendUseObjOnNPC(int npcID) {
        SceneNPC npc = npcs[npcID];
        if (npc == null) {
            return;
        }
        tryMove(2, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], npc.pathTileX[0], npc.pathTileZ[0], 0, 1, 1, 0, 0, false);
        crossX = super.mouseClickX;
        crossY = super.mouseClickY;
        crossMode = 2;
        crossCycle = 0;
        out.writeOp(57);
        out.write16A(selectedObjID);
        out.write16A(npcID);
        out.write16LE(selectedObjSlot);
        out.write16A(selectedObjInterfaceID);
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

    public void handleViewportOptions() {
        if ((objSelected == 0) && (spellSelected == 0)) {
            addMenuOption("Walk here", 516, super.mouseX, super.mouseY, 0);
        }

        int lastBitset = -1;
        for (int i = 0; i < Model.pickedCount; i++) {
            int bitset = Model.pickedBitsets[i];
            int x = bitset & 0x7f;
            int z = (bitset >> 7) & 0x7f;
            int type = (bitset >> 29) & 3;
            int id = (bitset >> 14) & 0x7fff;

            if (bitset == lastBitset) {
                continue;
            }

            lastBitset = bitset;

            if (type == 2) {
                handleLocOptions(bitset, x, z, id);
            } else if (type == 1) {
                handleNPCOptions(x, z, id);
            } else if (type == 0) {
                handlePlayerOptions(x, z, id);
            } else if (type == 3) {
                handleObjStackOptions(x, z);
            }
        }
    }

    private void handleLocOptions(int bitset, int x, int z, int id) {
        if (scene.getInfo(currentLevel, x, z, bitset) < 0) {
            return;
        }

        Obj loc = Obj.get(id);

        if (loc.overrides != null) {
            loc = loc.getOverrideType();
        }

        if (loc == null) {
            return;
        }

        if (objSelected == 1) {
            addMenuOption("Use " + selectedObjName + " with @cya@" + loc.name, 62, x, z, bitset);
        } else if (spellSelected == 1) {
            if ((activeSpellFlags & 4) == 4) {
                addMenuOption(spellCaption + " @cya@" + loc.name, 956, x, z, bitset);
            }
        } else {
            if (loc._options != null) {
                for (int op = 4; op >= 0; op--) {
                    if (loc._options[op] != null) {
                        addMenuOption(loc._options[op] + " @cya@" + loc.name, LOC_OP_ACTION[op], x, z, bitset);
                    }
                }
            }
            addMenuOption("Examine @cya@" + loc.name, 1226, x, z, loc.id << 14);
        }
    }

    private void handleNPCOptions(int x, int z, int d) {
        SceneNPC npc = npcs[d];

        if ((npc.type.size == 1) && ((npc.x & 0x7f) == 64) && ((npc.z & 0x7f) == 64)) {
            for (int i = 0; i < npcCount; i++) {
                SceneNPC other = npcs[npcIDs[i]];
                if ((other != null) && (other != npc) && (other.type.size == 1) && (other.x == npc.x) && (other.z == npc.z)) {
                    addNPCOptions(other.type, npcIDs[i], z, x);
                }
            }

            for (int i = 0; i < playerCount; i++) {
                ScenePlayer other = players[playerIDs[i]];
                if ((other != null) && (other.x == npc.x) && (other.z == npc.z)) {
                    addPlayerOptions(x, playerIDs[i], other, z);
                }
            }
        }

        addNPCOptions(npc.type, d, z, x);
    }

    private void handlePlayerOptions(int x, int z, int id) {
        ScenePlayer player = players[id];

        if (((player.x & 0x7f) == 64) && ((player.z & 0x7f) == 64)) {
            for (int i = 0; i < npcCount; i++) {
                SceneNPC other = npcs[npcIDs[i]];
                if ((other != null) && (other.type.size == 1) && (other.x == player.x) && (other.z == player.z)) {
                    addNPCOptions(other.type, npcIDs[i], z, x);
                }
            }

            for (int i = 0; i < playerCount; i++) {
                ScenePlayer other = players[playerIDs[i]];
                if ((other != null) && (other != player) && (other.x == player.x) && (other.z == player.z)) {
                    addPlayerOptions(x, playerIDs[i], other, z);
                }
            }
        }

        addPlayerOptions(x, id, player, z);
    }

    private void handleObjStackOptions(int x, int z) {
        DoublyLinkedList list = levelObjStacks[currentLevel][x][z];
        if (list == null) {
            return;
        }

        for (SceneItem obj = (SceneItem) list.peekBack(); obj != null; obj = (SceneItem) list.next()) {
            Item type = Item.get(obj.id);

            if (objSelected == 1) {
                addMenuOption("Use " + selectedObjName + " with @lre@" + type.name, 511, x, z, obj.id);
            } else if (spellSelected == 1) {
                if ((activeSpellFlags & 1) == 1) {
                    addMenuOption(spellCaption + " @lre@" + type.name, 94, x, z, obj.id);
                }
            } else {
                for (int op = 4; op >= 0; op--) {
                    if ((type.options != null) && (type.options[op] != null)) {
                        addMenuOption(type.options[op] + " @lre@" + type.name, OBJ_OP_ACTION[op], x, z, obj.id);
                    } else if (op == 2) {
                        addMenuOption("Take @lre@" + type.name, 234, x, z, obj.id);
                    }
                }

                addMenuOption("Examine @lre@" + type.name, 1448, x, z, obj.id);
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
        System.out.println("ptype:" + packetType);
        System.out.println("psize:" + packetSize);
        System.out.println("scene-state:" + sceneState);
        System.out.println("draw-state:" + checkScene());
        if (connection != null) {
            connection.debug();
        }
        super.debug = true;
    }

    public void handleInputKey() {
        do {
            int key = pollKey();

            if (key == -1) {
                break;
            }

            if ((viewportInterfaceID != -1) && (viewportInterfaceID == reportAbuseInterfaceID)) {
                handleInputReportAbuseKey(key);
            } else if (showSocialInput) {
                handleInputSocialKey(key);
            } else if (chatbackInputType == 1) {
                handleInputAmountKey(key);
            } else if (chatbackInputType == 2) {
                handleInputNameKey(key);
            } else if (chatInterfaceID == -1) {
                handleInputChatKey(key);
            }
        } while (true);
    }

    private void handleInputChatKey(int key) {
        // https://www.asciitable.com/
        // 32 to 122 is all numbers and symbols excluding: {}|~
        if ((key >= 32) && (key <= 122) && (chatTyped.length() < 80)) {
            chatTyped += (char) key;
            redrawChatback = true;
        }

        if ((key == KeyEvent.VK_BACK_SPACE) && (chatTyped.length() > 0)) {
            chatTyped = chatTyped.substring(0, chatTyped.length() - 1);
            redrawChatback = true;
        }

        /*Carriage Return*/
        if (((key == 13) || (key == KeyEvent.VK_ENTER)) && (chatTyped.length() > 0)) {
            if (rights == 2) {
                if (chatTyped.equals("::clientdrop")) {
                    tryReconnect();
                }
                if (chatTyped.equals("::lag")) {
                    debug();
                }
                if (chatTyped.equals("::prefetchmusic")) {
                    for (int i = 0; i < ondemand.getFileCount(2); i++) {
                        ondemand.prefetch((byte) 1, 2, i);
                    }
                }
                if (chatTyped.equals("::perf")) {
                    showPerformance = !showPerformance;
                }
                if (chatTyped.equals("::occluders")) {
                    showOccluders = !showOccluders;
                }
                if (chatTyped.equals("::traffic")) {
                    showTraffic = !showTraffic;
                }
                if (chatTyped.equals("::noclip")) {
                    for (int level = 0; level < 4; level++) {
                        for (int x = 1; x < 103; x++) {
                            for (int z = 1; z < 103; z++) {
                                level_collisions[level].flags[x][z] = 0;
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

                int color = 0;
                if (s.startsWith("yellow:")) {
                    color = 0;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("red:")) {
                    color = 1;
                    chatTyped = chatTyped.substring(4);
                } else if (s.startsWith("green:")) {
                    color = 2;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("cyan:")) {
                    color = 3;
                    chatTyped = chatTyped.substring(5);
                } else if (s.startsWith("purple:")) {
                    color = 4;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("white:")) {
                    color = 5;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("flash1:")) {
                    color = 6;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("flash2:")) {
                    color = 7;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("flash3:")) {
                    color = 8;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("glow1:")) {
                    color = 9;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("glow2:")) {
                    color = 10;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("glow3:")) {
                    color = 11;
                    chatTyped = chatTyped.substring(6);
                }

                s = chatTyped.toLowerCase();
                int style = 0;

                if (s.startsWith("wave:")) {
                    style = 1;
                    chatTyped = chatTyped.substring(5);
                } else if (s.startsWith("wave2:")) {
                    style = 2;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("shake:")) {
                    style = 3;
                    chatTyped = chatTyped.substring(6);
                } else if (s.startsWith("scroll:")) {
                    style = 4;
                    chatTyped = chatTyped.substring(7);
                } else if (s.startsWith("slide:")) {
                    style = 5;
                    chatTyped = chatTyped.substring(6);
                }

                out.writeOp(4);
                out.write8(0);
                int start = out.position;
                out.write8S(style);
                out.write8S(color);
                out.writeString(chatTyped);
                out.writeSize(out.position - start);

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

    private void handleInputNameKey(int key) {
        // https://www.asciitable.com/
        // 32 to 122 is all alpha, numbers and symbols excluding: {}|~
        if ((key >= 32) && (key <= 122) && (chatbackInput.length() < 12)) {
            chatbackInput += (char) key;
            redrawChatback = true;
        }
        if ((key == KeyEvent.VK_BACK_SPACE) && (chatbackInput.length() > 0)) {
            chatbackInput = chatbackInput.substring(0, chatbackInput.length() - 1);
            redrawChatback = true;
        }
        if ((key == 13) || (key == KeyEvent.VK_ENTER)) {
            if (chatbackInput.length() > 0) {
                out.writeOp(60);
                out.write64(StringUtil.toBase37(chatbackInput));
            }
            chatbackInputType = 0;
            redrawChatback = true;
        }
    }

    private void handleInputAmountKey(int key) {
        // https://www.asciitable.com/
        if ((key >= '0') && (key <= '9') && (chatbackInput.length() < 10)) {
            chatbackInput += (char) key;
            redrawChatback = true;
        }
        if ((key == KeyEvent.VK_BACK_SPACE) && (chatbackInput.length() > 0)) {
            chatbackInput = chatbackInput.substring(0, chatbackInput.length() - 1);
            redrawChatback = true;
        }
        if ((key == 13) || (key == KeyEvent.VK_ENTER)) {
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
    }

    private void handleInputSocialKey(int key) {
        // https://www.asciitable.com/
        // 32 to 122 is all alpha, numbers and symbols excluding: {}|~
        if ((key >= 32) && (key <= 122) && (socialInput.length() < 80)) {
            socialInput += (char) key;
            redrawChatback = true;
        }
        if ((key == KeyEvent.VK_BACK_SPACE) && (socialInput.length() > 0)) {
            socialInput = socialInput.substring(0, socialInput.length() - 1);
            redrawChatback = true;
        }
        if ((key == 13) || (key == KeyEvent.VK_ENTER)) {
            showSocialInput = false;
            redrawChatback = true;

            if (socialAction == 1) {
                addFriend(StringUtil.toBase37(socialInput));
            }

            if ((socialAction == 2) && (friendCount > 0)) {
                removeFriend(StringUtil.toBase37(socialInput));
            }

            if ((socialAction == 3) && (socialInput.length() > 0)) {
                out.writeOp(126);
                out.write8(0);
                int start = out.position;
                out.write64(inputFriendName37);
                out.writeString(socialInput);
                out.writeSize(out.position - start);
                addMessage(6, StringUtil.formatName(StringUtil.fromBase37(inputFriendName37)), socialInput);
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
                addIgnore(StringUtil.toBase37(socialInput));
            }

            if ((socialAction == 5) && (ignoreCount > 0)) {
                removeIgnore(StringUtil.toBase37(socialInput));
            }
        }
    }

    private void handleInputReportAbuseKey(int key) {
        if ((key == KeyEvent.VK_BACK_SPACE) && (reportAbuseInput.length() > 0)) {
            reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
        }
        // https://www.asciitable.com/
        if ((((key >= 'a') && (key <= 'z')) || ((key >= 'A') && (key <= 'Z')) || ((key >= '0') && (key <= '9')) || (key == ' ')) && (reportAbuseInput.length() < 12)) {
            reportAbuseInput += (char) key;
        }
    }

    public void handleChatMouseInput(int mouseY) {
        int line = 0;
        for (int i = 0; i < 100; i++) {
            if (messageText[i] == null) {
                continue;
            }

            int type = messageType[i];
            int y = (70 - (line * 14)) + chatScrollOffset + 4;

            if (y < -20) {
                break;
            }

            String s = messageSender[i];

            if ((s != null) && s.startsWith("@cr1@")) {
                s = s.substring(5);
            }

            if ((s != null) && s.startsWith("@cr2@")) {
                s = s.substring(5);
            }

            if (type == 0) {
                line++;
            }

            if (((type == 1) || (type == 2)) && ((type == 1) || (publicChatSetting == 0) || ((publicChatSetting == 1) && isFriend(s)))) {
                if ((mouseY > (y - 14)) && (mouseY <= y) && !s.equals(localPlayer.name)) {
                    if (rights >= 1) {
                        addMenuOption("Report abuse @whi@" + s, 606);
                    }
                    addMenuOption("Add ignore @whi@" + s, 42);
                    addMenuOption("Add friend @whi@" + s, 337);
                }
                line++;
            }

            if (((type == 3) || (type == 7)) && (splitPrivateChat == 0) && ((type == 7) || (privateChatSetting == 0) || ((privateChatSetting == 1) && isFriend(s)))) {
                if ((mouseY > (y - 14)) && (mouseY <= y)) {
                    if (rights >= 1) {
                        addMenuOption("Report abuse @whi@" + s, 606);
                    }
                    addMenuOption("Add ignore @whi@" + s, 42);
                    addMenuOption("Add friend @whi@" + s, 337);
                }
                line++;
            }

            if ((type == 4) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(s)))) {
                if ((mouseY > (y - 14)) && (mouseY <= y)) {
                    addMenuOption("Accept trade @whi@" + s, 484);
                }
                line++;
            }

            if (((type == 5) || (type == 6)) && (splitPrivateChat == 0) && (privateChatSetting < 2)) {
                line++;
            }

            if ((type == 8) && ((tradeChatSetting == 0) || ((tradeChatSetting == 1) && isFriend(s)))) {
                if ((mouseY > (y - 14)) && (mouseY <= y)) {
                    addMenuOption("Accept challenge @whi@" + s, 6);
                }
                line++;
            }
        }
    }

    public void updateInterfaceContent(Iface iface) {
        int type = iface.contentType;

        if (((type >= 1) && (type <= 100)) || ((type >= 701) && (type <= 800))) {
            if ((type == 1) && (friendlistStatus == 0)) {
                iface.text = "Loading friend list";
                iface.optionType = 0;
                return;
            }

            if ((type == 1) && (friendlistStatus == 1)) {
                iface.text = "Connecting to friendserver";
                iface.optionType = 0;
                return;
            }

            if ((type == 2) && (friendlistStatus != 2)) {
                iface.text = "Please wait...";
                iface.optionType = 0;
                return;
            }

            int count = friendCount;

            if (friendlistStatus != 2) {
                count = 0;
            }

            if (type > 700) {
                type -= 601;
            } else {
                type--;
            }

            if (type >= count) {
                iface.text = "";
                iface.optionType = 0;
            } else {
                iface.text = friendName[type];
                iface.optionType = 1;
            }
            return;
        }

        if (((type >= 101) && (type <= 200)) || ((type >= 801) && (type <= 900))) {
            int count = friendCount;

            if (friendlistStatus != 2) {
                count = 0;
            }

            if (type > 800) {
                type -= 701;
            } else {
                type -= 101;
            }

            if (type >= count) {
                iface.text = "";
                iface.optionType = 0;
                return;
            }

            if (friendWorld[type] == 0) {
                iface.text = "@red@Offline";
            } else if (friendWorld[type] == nodeID) {
                iface.text = "@gre@World-" + (friendWorld[type] - 9);
            } else {
                iface.text = "@yel@World-" + (friendWorld[type] - 9);
            }

            iface.optionType = 1;
            return;
        }

        if (type == 203) {
            int count = friendCount;

            if (friendlistStatus != 2) {
                count = 0;
            }

            iface.scrollableHeight = (count * 15) + 20;

            if (iface.scrollableHeight <= iface.height) {
                iface.scrollableHeight = iface.height + 1;
            }
            return;
        }

        if ((type >= 401) && (type <= 500)) {
            if ((((type -= 401)) == 0) && (friendlistStatus == 0)) {
                iface.text = "Loading ignore list";
                iface.optionType = 0;
                return;
            }

            if ((type == 1) && (friendlistStatus == 0)) {
                iface.text = "Please wait...";
                iface.optionType = 0;
                return;
            }

            int count = ignoreCount;

            if (friendlistStatus == 0) {
                count = 0;
            }

            if (type >= count) {
                iface.text = "";
                iface.optionType = 0;
            } else {
                iface.text = StringUtil.formatName(StringUtil.fromBase37(ignoreName37[type]));
                iface.optionType = Iface.OPTION_TYPE_STANDARD;
            }
            return;
        }

        if (type == 503) {
            iface.scrollableHeight = (ignoreCount * 15) + 20;

            if (iface.scrollableHeight <= iface.height) {
                iface.scrollableHeight = iface.height + 1;
            }
            return;
        }

        if (type == 327) {
            iface.modelPitch = 150;
            iface.modelYaw = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;

            if (updateDesignModel) {
                for (int part = 0; part < 7; part++) {
                    int kit = designIdentikits[part];
                    if ((kit >= 0) && !Identikit.instances[kit].validateModel()) {
                        return;
                    }
                }

                updateDesignModel = false;

                Model[] models = new Model[7];
                int modelCount = 0;
                for (int part = 0; part < 7; part++) {
                    int kit = designIdentikits[part];
                    if (kit >= 0) {
                        models[modelCount++] = Identikit.instances[kit].getModel();
                    }
                }

                Model model = Model.join_prebuilt(modelCount, models);
                for (int part = 0; part < 5; part++) {
                    if (designColors[part] != 0) {
                        model.recolor(designPartColor[part][0], designPartColor[part][designColors[part]]);
                        if (part == 1) {
                            model.recolor(designHairColor[0], designHairColor[designColors[part]]);
                        }
                    }
                }

                model.build_labels();
                model.transform(Animation.instances[localPlayer.seqStandID].primary_transforms[0]);
                model.build(64, 850, -30, -50, -30, true);

                iface.modelType = Iface.MODEL_TYPE_PLAYER_DESIGN;
                iface.modelID = 0;

                Iface.cacheModel(0, Iface.MODEL_TYPE_PLAYER_DESIGN, model);
            }
            return;
        }

        if (type == 324) {
            if (genderButtonImage0 == null) {
                genderButtonImage0 = iface.image;
                genderButtonImage1 = iface.activeImage;
            }
            if (designGenderMale) {
                iface.image = genderButtonImage1;
            } else {
                iface.image = genderButtonImage0;
            }
            return;
        }

        if (type == 325) {
            if (genderButtonImage0 == null) {
                genderButtonImage0 = iface.image;
                genderButtonImage1 = iface.activeImage;
            }
            if (designGenderMale) {
                iface.image = genderButtonImage0;
            } else {
                iface.image = genderButtonImage1;
            }
            return;
        }

        if (type == 600) {
            iface.text = reportAbuseInput;

            if ((loopCycle % 20) < 10) {
                iface.text += "|";
            } else {
                iface.text += " ";
            }
            return;
        }

        if (type == 613) {
            if (rights >= 1) {
                if (reportAbuseMuteOption) {
                    iface.color = 0xff0000;
                    iface.text = "Moderator option: Mute player for 48 hours: <ON>";
                } else {
                    iface.color = 0xffffff;
                    iface.text = "Moderator option: Mute player for 48 hours: <OFF>";
                }
            } else {
                iface.text = "";
            }
        }

        if ((type == 650) || (type == 655)) {
            if (lastAddress != 0) {
                String text;
                if (daysSinceLastLogin == 0) {
                    text = "earlier today";
                } else if (daysSinceLastLogin == 1) {
                    text = "yesterday";
                } else {
                    text = daysSinceLastLogin + " days ago";
                }
                iface.text = "You last logged in " + text + " from: " + Signlink.dns;
            } else {
                iface.text = "";
            }
        }

        if (type == 651) {
            if (unreadMessages == 0) {
                iface.text = "0 unread messages";
                iface.color = 0xffff00;
            }

            if (unreadMessages == 1) {
                iface.text = "1 unread message";
                iface.color = 65280;
            }

            if (unreadMessages > 1) {
                iface.text = unreadMessages + " unread messages";
                iface.color = 65280;
            }
        }

        if (type == 652) {
            if (daysSinceRecoveriesChanged == 201) {
                if (warnMembersInNonMembers == 1) {
                    iface.text = "@yel@This is a non-members world: @whi@Since you are a member we";
                } else {
                    iface.text = "";
                }
            } else if (daysSinceRecoveriesChanged == 200) {
                iface.text = "You have not yet set any password recovery questions.";
            } else {
                String text;

                if (daysSinceRecoveriesChanged == 0) {
                    text = "Earlier today";
                } else if (daysSinceRecoveriesChanged == 1) {
                    text = "Yesterday";
                } else {
                    text = daysSinceRecoveriesChanged + " days ago";
                }
                iface.text = text + " you changed your recovery questions";
            }
        }

        if (type == 653) {
            if (daysSinceRecoveriesChanged == 201) {
                if (warnMembersInNonMembers == 1) {
                    iface.text = "@whi@recommend you use a members world instead. You may use";
                } else {
                    iface.text = "";
                }
            } else if (daysSinceRecoveriesChanged == 200) {
                iface.text = "We strongly recommend you do so now to secure your account.";
            } else {
                iface.text = "If you do not remember making this change then cancel it immediately";
            }
        }

        if (type == 654) {
            if (daysSinceRecoveriesChanged == 201) {
                if (warnMembersInNonMembers == 1) {
                    iface.text = "@whi@this world but member benefits are unavailable whilst here.";
                } else {
                    iface.text = "";
                }
                return;
            }
            if (daysSinceRecoveriesChanged == 200) {
                iface.text = "Do this from the 'account management' area on our front webpage";
                return;
            }
            iface.text = "Do this from the 'account management' area on our front webpage";
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
                    int y = 329 - (i * 13);
                    font.drawString("To " + s + ": " + messageText[j], 4, y, 0);
                    font.drawString("To " + s + ": " + messageText[j], 4, y - 1, 65535);
                    if (++i >= 5) {
                        return;
                    }
                }
            }
        }
    }

    public void addMessage(int type, String prefix, String message) {
        if ((type == 0) && (stickyChatInterfaceID != -1)) {
            modalMessage = message;
            super.mouseClickButton = 0;
        }

        if (chatInterfaceID == -1) {
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
        if (super.mouseClickButton == 1) {
            if ((super.mouseClickX >= 539) && (super.mouseClickX <= 573) && (super.mouseClickY >= 169) && (super.mouseClickY < 205) && (tabInterfaceID[0] != -1)) {
                redrawSidebar = true;
                selectedTab = 0;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 569) && (super.mouseClickX <= 599) && (super.mouseClickY >= 168) && (super.mouseClickY < 205) && (tabInterfaceID[1] != -1)) {
                redrawSidebar = true;
                selectedTab = 1;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 597) && (super.mouseClickX <= 627) && (super.mouseClickY >= 168) && (super.mouseClickY < 205) && (tabInterfaceID[2] != -1)) {
                redrawSidebar = true;
                selectedTab = 2;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 625) && (super.mouseClickX <= 669) && (super.mouseClickY >= 168) && (super.mouseClickY < 203) && (tabInterfaceID[3] != -1)) {
                redrawSidebar = true;
                selectedTab = 3;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 666) && (super.mouseClickX <= 696) && (super.mouseClickY >= 168) && (super.mouseClickY < 205) && (tabInterfaceID[4] != -1)) {
                redrawSidebar = true;
                selectedTab = 4;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 694) && (super.mouseClickX <= 724) && (super.mouseClickY >= 168) && (super.mouseClickY < 205) && (tabInterfaceID[5] != -1)) {
                redrawSidebar = true;
                selectedTab = 5;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 722) && (super.mouseClickX <= 756) && (super.mouseClickY >= 169) && (super.mouseClickY < 205) && (tabInterfaceID[6] != -1)) {
                redrawSidebar = true;
                selectedTab = 6;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 540) && (super.mouseClickX <= 574) && (super.mouseClickY >= 466) && (super.mouseClickY < 502) && (tabInterfaceID[7] != -1)) {
                redrawSidebar = true;
                selectedTab = 7;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 572) && (super.mouseClickX <= 602) && (super.mouseClickY >= 466) && (super.mouseClickY < 503) && (tabInterfaceID[8] != -1)) {
                redrawSidebar = true;
                selectedTab = 8;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 599) && (super.mouseClickX <= 629) && (super.mouseClickY >= 466) && (super.mouseClickY < 503) && (tabInterfaceID[9] != -1)) {
                redrawSidebar = true;
                selectedTab = 9;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 627) && (super.mouseClickX <= 671) && (super.mouseClickY >= 467) && (super.mouseClickY < 502) && (tabInterfaceID[10] != -1)) {
                redrawSidebar = true;
                selectedTab = 10;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 669) && (super.mouseClickX <= 699) && (super.mouseClickY >= 466) && (super.mouseClickY < 503) && (tabInterfaceID[11] != -1)) {
                redrawSidebar = true;
                selectedTab = 11;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 696) && (super.mouseClickX <= 726) && (super.mouseClickY >= 466) && (super.mouseClickY < 503) && (tabInterfaceID[12] != -1)) {
                redrawSidebar = true;
                selectedTab = 12;
                redrawSideicons = true;
            }
            if ((super.mouseClickX >= 724) && (super.mouseClickX <= 758) && (super.mouseClickY >= 466) && (super.mouseClickY < 502) && (tabInterfaceID[13] != -1)) {
                redrawSidebar = true;
                selectedTab = 13;
                redrawSideicons = true;
            }
        }
    }

    public void prepareGameScreen() {
        if (areaChatback != null) {
            return;
        }
        unloadTitle();
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
        areaSidebar = new DrawArea(190, 261);
        areaViewport = new DrawArea(512, 334);
        Draw2D.clear();
        areaBackbase1 = new DrawArea(496, 50);
        areaBackbase2 = new DrawArea(269, 37);
        areaBackhmid1 = new DrawArea(249, 45);
        redrawTitleBackground = true;
    }

    public void drawMinimapHint(Image24 image, int x, int y) {
        int distance2 = (x * x) + (y * y);

        if ((distance2 > 4225) && (distance2 < 90000)) {
            int angle = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
            int sinAngle = Model.sin[angle];
            int cosAngle = Model.cos[angle];
            sinAngle = (sinAngle * 256) / (minimapZoom + 256);
            cosAngle = (cosAngle * 256) / (minimapZoom + 256);
            int directionX = ((y * sinAngle) + (x * cosAngle)) >> 16;
            int directionY = ((y * cosAngle) - (x * sinAngle)) >> 16;
            double directionAngle = Math.atan2(directionX, directionY);
            int hintX = (int) (Math.sin(directionAngle) * 63D);
            int hintY = (int) (Math.cos(directionAngle) * 57D);
            imageMapedge.drawRotated((94 + hintX + 4) - 10, 83 - hintY - 20, 20, 20, 15, 15, directionAngle, 256);
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
        handleViewportInput();
        handleSidebarInput();
        handleChatInput();

        sortMenuOptions();
    }

    private void handleChatInput() {
        lastHoveredInterfaceID = 0;

        if ((super.mouseX > 17) && (super.mouseY > 357) && (super.mouseX < 496) && (super.mouseY < 453)) {
            if (chatInterfaceID != -1) {
                handleInterfaceInput(Iface.instances[chatInterfaceID], 17, 357, 0);
            } else if ((super.mouseY < 434) && (super.mouseX < 426)) {
                handleChatMouseInput(super.mouseY - 357);
            }
        }

        if ((chatInterfaceID != -1) && (lastHoveredInterfaceID != chatHoveredInterfaceID)) {
            redrawChatback = true;
            chatHoveredInterfaceID = lastHoveredInterfaceID;
        }
    }

    private void handleViewportInput() {
        lastHoveredInterfaceID = 0;

        if ((super.mouseX > 4) && (super.mouseY > 4) && (super.mouseX < 516) && (super.mouseY < 338)) {
            if (viewportInterfaceID != -1) {
                handleInterfaceInput(Iface.instances[viewportInterfaceID], 4, 4, 0);
            } else {
                handleViewportOptions();
            }
        }

        if (lastHoveredInterfaceID != viewportHoveredInterfaceID) {
            viewportHoveredInterfaceID = lastHoveredInterfaceID;
        }
    }

    private void handleSidebarInput() {
        lastHoveredInterfaceID = 0;

        if ((super.mouseX > 553) && (super.mouseY > 205) && (super.mouseX < 743) && (super.mouseY < 466)) {
            if (sidebarInterfaceID != -1) {
                handleInterfaceInput(Iface.instances[sidebarInterfaceID], 553, 205, 0);
            } else if (tabInterfaceID[selectedTab] != -1) {
                handleInterfaceInput(Iface.instances[tabInterfaceID[selectedTab]], 553, 205, 0);
            }
        }

        if (lastHoveredInterfaceID != sidebarHoveredInterfaceID) {
            redrawSidebar = true;
            sidebarHoveredInterfaceID = lastHoveredInterfaceID;
        }
    }

    private void sortMenuOptions() {
        // The code below pushes menu options with an action greater than 1000 to the bottom, reducing its priority.
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < (menuSize - 1); i++) {
                if ((menuAction[i] >= 1000) || (menuAction[i + 1] <= 1000)) {
                    continue;
                }

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

    public int mix(int src, int dst, int alpha) {
        int invAlpha = 256 - alpha;

        //                 0xAARRGGBB
        int srcRB = (src & 0x00FF00FF) * invAlpha; // mul R and B (invAlpha of 256 is the same as << 8)
        //                 0xRRR0BBB0
        // This multiplication causes the channels to shift. Since it most likely shifts on the magnitude of bits and
        // not bytes, this will cause the channels to be contained within 2 bytes. You can imagine the first byte being
        // its whole value, and the second byte being its 'fractional' value. Like a fixed-integer.

        //                 0xAARRGGBB
        int srcG = (src & 0x0000FF00) * invAlpha; // mul G
        //                 0x00GGG000
        // same as above but with only a single channel

        // same as above, just with alpha instead of invAlpha
        int dstRB = (dst & 0x00FF00FF) * alpha;
        int dstG = (dst & 0x0000FF00) * alpha;

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

            connection = new Connection(this, openSocket(43590 + portOffset));

            long name37 = StringUtil.toBase37(username);
            int namePart = (int) ((name37 >> 16) & 31L);

            out.position = 0;
            out.write8(14);
            out.write8(namePart);

            connection.write(out.data, 0, 2);

            for (int j = 0; j < 8; j++) {
                connection.read();
            }

            int response = connection.read();
            int lastResponse = response;

            if (response == 0) {
                connection.read(in.data, 0, 8);
                in.position = 0;

                serverSeed = in.read64();

                // apache math tries to fill the remaining 1008 bytes up with random junk if we don't give it 256 ints.
                int[] seed = new int[1 << 8];

                seed[0] = (int) (Math.random() * 99999999D);
                seed[1] = (int) (Math.random() * 99999999D);
                seed[2] = (int) (serverSeed >> 32);
                seed[3] = (int) serverSeed;

                out.position = 0;
                out.write8(10);
                out.write32(seed[0]);
                out.write32(seed[1]);
                out.write32(seed[2]);
                out.write32(seed[3]);
                out.write32(Signlink.uid);
                out.writeString(username);
                out.writeString(password);
                out.writeString("@@@@@@@@@@@@@@@@@");
                out.encrypt(RSA_EXPONENT, RSA_MODULUS);

                login.position = 0;
                login.write8(reconnect ? 18 : 16);
                login.write8(out.position + 4);
                login.write8(111);
                login.write16(39);
                login.write8(1);

                login.write(out.data, 0, out.position);

                out.random = new ISAACRandom(seed);
                for (int i = 0; i < 4; i++) {
                    seed[i] += 50;
                }
                randomIn = new ISAACRandom(seed);

                connection.write(login.data, 0, login.position);
                response = connection.read();
            }

            switch (response) {
                case 1:
                    try {
                        Thread.sleep(2000L);
                    } catch (Exception ignored) {
                    }
                    login(username, password, reconnect);
                    return;

                case 2:
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
                    packetType = -1;
                    lastPacketType0 = -1;
                    lastPacketType1 = -1;
                    lastPacketType2 = -1;
                    packetSize = 0;
                    idleNetCycles = 0;
                    systemUpdateTimer = 0;
                    idleTimeout = 0;
                    hintType = 0;
                    menuSize = 0;
                    menuVisible = false;
                    super.idleCycles = 0;
                    Arrays.fill(messageText, null);
                    objSelected = 0;
                    spellSelected = 0;
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
                    for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
                        players[i] = null;
                        playerAppearanceBuffer[i] = null;
                    }
                    for (int i = 0; i < 16384; i++) {
                        npcs[i] = null;
                    }
                    localPlayer = players[LOCAL_PLAYER_INDEX] = new ScenePlayer();
                    projectiles.clear();
                    spotanims.clear();
                    for (int level = 0; level < 4; level++) {
                        for (int x = 0; x < 104; x++) {
                            for (int z = 0; z < 104; z++) {
                                levelObjStacks[level][x][z] = null;
                            }
                        }
                    }
                    temporaryLocs = new DoublyLinkedList();
                    friendlistStatus = 0;
                    friendCount = 0;
                    stickyChatInterfaceID = -1;
                    chatInterfaceID = -1;
                    viewportInterfaceID = -1;
                    sidebarInterfaceID = -1;
                    viewportOverlayInterfaceID = -1;
                    pressedContinueOption = false;
                    selectedTab = 3;
                    chatbackInputType = 0;
                    menuVisible = false;
                    showSocialInput = false;
                    modalMessage = null;
                    multizone = 0;
                    flashingTab = -1;
                    designGenderMale = true;
                    validateCharacterDesign();
                    for (int i = 0; i < 5; i++) {
                        designColors[i] = 0;
                    }
                    for (int i = 0; i < 5; i++) {
                        playerOptions[i] = null;
                        playerOptionPushDown[i] = false;
                    }
                    prepareGameScreen();
                    return;

                case 3:
                    loginMessage0 = "";
                    loginMessage1 = "Invalid username or password.";
                    return;

                case 4:
                    loginMessage0 = "Your account has been disabled.";
                    loginMessage1 = "Please check your message-centre for details.";
                    return;

                case 5:
                    loginMessage0 = "Your account is already logged in.";
                    loginMessage1 = "Try again in 60 secs...";
                    return;

                case 6:
                    loginMessage0 = "RuneScape has been updated!";
                    loginMessage1 = "Please reload this page.";
                    return;

                case 7:
                    loginMessage0 = "This world is full.";
                    loginMessage1 = "Please use a different world.";
                    return;

                case 8:
                    loginMessage0 = "Unable to connect.";
                    loginMessage1 = "Login server offline.";
                    return;

                case 9:
                    loginMessage0 = "Login limit exceeded.";
                    loginMessage1 = "Too many connections from your address.";
                    return;

                case 10:
                    loginMessage0 = "Unable to connect.";
                    loginMessage1 = "Bad session id.";
                    return;

                case 11:
                    loginMessage0 = "Login server rejected session.";
                    loginMessage1 = "Please try again.";
                    return;

                case 12:
                    loginMessage0 = "You need a members account to login to this world.";
                    loginMessage1 = "Please subscribe, or use a different world.";
                    return;

                case 13:
                    loginMessage0 = "Could not complete login.";
                    loginMessage1 = "Please try using a different world.";
                    return;

                case 14:
                    loginMessage0 = "The server is being updated.";
                    loginMessage1 = "Please wait 1 minute and try again.";
                    return;

                case 15:
                    ingame = true;
                    out.position = 0;
                    in.position = 0;
                    packetType = -1;
                    lastPacketType0 = -1;
                    lastPacketType1 = -1;
                    lastPacketType2 = -1;
                    packetSize = 0;
                    idleNetCycles = 0;
                    systemUpdateTimer = 0;
                    menuSize = 0;
                    menuVisible = false;
                    sceneLoadStartTime = System.currentTimeMillis();
                    return;

                case 16:
                    loginMessage0 = "Login attempts exceeded.";
                    loginMessage1 = "Please wait 1 minute and try again.";
                    return;

                case 17:
                    loginMessage0 = "You are standing in a members-only area.";
                    loginMessage1 = "To play on this world move to a free area first";
                    return;

                case 20:
                    loginMessage0 = "Invalid loginserver requested";
                    loginMessage1 = "Please try using a different world.";
                    return;

                case 21:
                    for (int remaining = connection.read(); remaining >= 0; remaining--) {
                        loginMessage0 = "You have only just left another world";
                        loginMessage1 = "Your profile will be transferred in: " + remaining + " seconds";
                        drawTitleScreen(true);

                        try {
                            Thread.sleep(1000L);
                        } catch (Exception ignored) {
                        }
                    }
                    login(username, password, reconnect);
                    return;

                case -1:
                    if (lastResponse == 0) {
                        if (loginAttempts < 2) {
                            try {
                                Thread.sleep(2000L);
                            } catch (Exception ignored) {
                            }
                            loginAttempts++;
                            login(username, password, reconnect);
                        } else {
                            loginMessage0 = "No response from loginserver";
                            loginMessage1 = "Please wait 1 minute and try again.";
                        }
                    } else {
                        loginMessage0 = "No response from server";
                        loginMessage1 = "Please try using a different world.";
                    }
                    break;

                default:
                    System.out.println("response:" + response);
                    loginMessage0 = "Unexpected server response";
                    loginMessage1 = "Please try using a different world.";
                    break;
            }
        } catch (IOException _ex) {
            loginMessage0 = "";
            loginMessage1 = "Error connecting to server.";
        }
    }

    public boolean tryMove(int type, int srcX, int srcZ, int dx, int dz, int locType, int locWidth,
                           int locLength,
                           int locAngle, int locInteractionFlags, boolean tryNearest) {
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
        int[][] flags = level_collisions[currentLevel].flags;

        while (length != steps) {
            x = bfsStepX[length];
            z = bfsStepZ[length];
            length = (length + 1) % bufferSize;

            if ((x == dx) && (z == dz)) {
                arrived = true;
                break;
            }

            if (locType != 0) {
                if (((locType < 5) || (locType == 10)) && level_collisions[currentLevel].reachedDestination(x, z, dx, dz, locAngle, locType - 1)) {
                    arrived = true;
                    break;
                }
                if ((locType < 10) && level_collisions[currentLevel].reachedWall(x, z, dx, dz, locType - 1, locAngle)) {
                    arrived = true;
                    break;
                }
            }

            if ((locWidth != 0) && (locLength != 0) && level_collisions[currentLevel].reachedLoc(x, z, dx, dz, locWidth, locLength, locInteractionFlags)) {
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
                out.write8(count + count + 3);
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
            out.write8C(actionKey[5] ? 1 : 0);
            return true;
        }

        return type != 1;
    }

    public void readNPCUpdates() {
        in.accessBytes();

        for (int i = 0; i < entityUpdateCount; i++) {
            SceneNPC npc = npcs[entityUpdateIDs[i]];
            int updates = in.readU8();

            if ((updates & 0x10) != 0) {
                readNPCAnimation(npc);
            }

            if ((updates & 0x8) != 0) {
                readNPCDamage0(npc);
            }

            if ((updates & 0x80) != 0) {
                readNPCSpotAnim(npc);
            }

            if ((updates & 0x20) != 0) {
                readNPCTargetEntity(npc);
            }

            if ((updates & 0x1) != 0) {
                readNPCChat(npc);
            }

            if ((updates & 0x40) != 0) {
                readNPCDamage1(npc);
            }

            if ((updates & 0x2) != 0) {
                readNPCTransform(npc);
            }

            if ((updates & 0x4) != 0) {
                readNPCTargetTile(npc);
            }
        }
    }

    private void readNPCSpotAnim(SceneNPC npc) {
        npc.spotanimID = in.readU16();
        npc.spotanimOffset = in.readU16();
        npc.spotanimLastCycle = loopCycle + in.readU16();
        in.readU16();
        npc.spotanimFrame = 0;
        npc.spotanimCycle = 0;
        if (npc.spotanimLastCycle > loopCycle) {
            npc.spotanimFrame = -1;
        }
        if (npc.spotanimID == 65535) {
            npc.spotanimID = -1;
        }
    }

    private void readNPCTargetEntity(SceneNPC npc) {
        npc.targetID = in.readU16();
        if (npc.targetID == 65535) {
            npc.targetID = -1;
        }
    }

    private void readNPCTargetTile(SceneNPC npc) {
        npc.targetTileX = in.readU16LE();
        npc.targetTileZ = in.readU16LE();
    }

    private void readNPCChat(SceneNPC npc) {
        npc.chat = in.readString();
        npc.chatTimer = 100;
    }

    private void readNPCDamage1(SceneNPC npc) {
        int damage = in.readU8C();
        int type = in.readU8S();
        npc.hit(type, damage);
        npc.combatCycle = loopCycle + 300;
        npc.health = in.readU8S();
        npc.totalHealth = in.readU8C();
    }

    private void readNPCTransform(SceneNPC npc) {
        npc.type = NPC.get(in.readU16LEA());
        npc.size = npc.type.size;
        npc.turnSpeed = npc.type._turn_speed;
        npc.seqWalkID = npc.type._animation_move;
        npc.seqTurnAroundID = npc.type._animation_turn_around;
        npc.seqTurnLeftID = npc.type._animation_turn_left;
        npc.seqTurnRightID = npc.type._animation_turn_right;
        npc.seqStandID = npc.type._animation_idle;
    }

    private void readNPCAnimation(SceneNPC npc) {
        int seqID = in.readU16LE();

        if (seqID == 65535) {
            seqID = -1;
        }

        if (seqID >= Animation.instances.length) {
            seqID = -1;
        }

        int delay = in.readU8();
        if ((seqID == npc.primarySeqID) && (seqID != -1)) {
            int style = Animation.instances[seqID]._replay_type;

            if (style == 1) {
                npc.primarySeqFrame = 0;
                npc.primarySeqCycle = 0;
                npc.primarySeqDelay = delay;
                npc.primarySeqLoop = 0;
            }

            if (style == 2) {
                npc.primarySeqLoop = 0;
            }
        } else if ((seqID == -1) || (npc.primarySeqID == -1) || (Animation.instances[seqID].priority >= Animation.instances[npc.primarySeqID].priority)) {
            npc.primarySeqID = seqID;
            npc.primarySeqFrame = 0;
            npc.primarySeqCycle = 0;
            npc.primarySeqDelay = delay;
            npc.primarySeqLoop = 0;
            npc.seqPathLength = npc.pathLength;
        }
    }

    private void readNPCDamage0(SceneNPC npc) {
        int damage = in.readU8A();
        int type = in.readU8C();
        npc.hit(type, damage);
        npc.combatCycle = loopCycle + 300;
        npc.health = in.readU8A();
        npc.totalHealth = in.readU8();
    }

    public void addNPCOptions(NPC type, int npcID, int tileZ, int tileX) {
        if (menuSize >= 400) {
            return;
        }

        if (type.overrides != null) {
            type = type.evaluate();
        }

        if (type == null) {
            return;
        }

        if (!type._interactable) {
            return;
        }

        String text = type.name;

        if (type._combat_level != 0) {
            text = text + getCombatLevelColorTag(localPlayer.combatLevel, type._combat_level) + " (level-" + type._combat_level + ")";
        }

        if (objSelected == 1) {
            addMenuOption("Use " + selectedObjName + " with @yel@" + text, 582, tileX, tileZ, npcID);
            return;
        }

        if (spellSelected == 1) {
            if ((activeSpellFlags & 2) == 2) {
                addMenuOption(spellCaption + " @yel@" + text, 413, tileX, tileZ, npcID);
            }
        } else {
            if (type._options != null) {
                for (int option = 4; option >= 0; option--) {
                    if ((type._options[option] != null) && !type._options[option].equalsIgnoreCase("attack")) {
                        addMenuOption(type._options[option] + " @yel@" + text, NPC_OP_ACTION[option], tileX, tileZ, npcID);
                    }
                }
            }

            if (type._options != null) {
                for (int option = 4; option >= 0; option--) {
                    if ((type._options[option] != null) && type._options[option].equalsIgnoreCase("attack")) {
                        int offset = 0;
                        if (type._combat_level > localPlayer.combatLevel) {
                            offset = 2000;
                        }
                        addMenuOption(type._options[option] + " @yel@" + text, NPC_OP_ACTION[option] + offset, tileX, tileZ, npcID);
                    }
                }
            }

            addMenuOption("Examine @yel@" + text, 1025, tileX, tileZ, npcID);
        }
    }

    public void addPlayerOptions(int tileX, int playerID, ScenePlayer player, int tileZ) {
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

        if (objSelected == 1) {
            addMenuOption("Use " + selectedObjName + " with @whi@" + caption, 491, tileX, tileZ, playerID);
        } else if (spellSelected == 1) {
            if ((activeSpellFlags & 8) == 8) {
                addMenuOption(spellCaption + " @whi@" + caption, 365, tileX, tileZ, playerID);
            }
        } else {
            for (int option = 4; option >= 0; option--) {
                if (playerOptions[option] == null) {
                    continue;
                }
                int offset = 0;

                if (playerOptions[option].equalsIgnoreCase("attack")) {
                    if (player.combatLevel > localPlayer.combatLevel) {
                        offset = 2000;
                    }

                    if ((localPlayer.team != 0) && (player.team != 0)) {
                        if (localPlayer.team == player.team) {
                            offset = 2000;
                        } else {
                            offset = 0;
                        }
                    }
                } else if (playerOptionPushDown[option]) {
                    offset = 2000;
                }

                int action = 0;

                if (option == 0) {
                    action = 561;
                } else if (option == 1) {
                    action = 779;
                } else if (option == 2) {
                    action = 27;
                } else if (option == 3) {
                    action = 577;
                } else if (option == 4) {
                    action = 729;
                }

                action += offset;

                addMenuOption(playerOptions[option] + " @whi@" + caption, action, tileX, tileZ, playerID);
            }
        }
        for (int i = 0; i < menuSize; i++) {
            if (menuAction[i] == 516) {
                menuOption[i] = "Walk here @whi@" + caption;
                return;
            }
        }
    }

    public void storeLoc(SceneTemporaryObject loc) {
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

    public static final boolean lowmem = false;

    public void updateAudio() {
        for (int wave = 0; wave < waveCount; wave++) {
            if (waveDelay[wave] > 0) {
                waveDelay[wave]--;
                continue;
            }

            boolean failed = false;

            try {
                if ((waveIDs[wave] == lastWaveID) && (waveLoops[wave] == lastWaveLoops)) {
                    if (!wavereplay()) {
                        failed = true;
                    }
                } else {
                    Buffer buffer = SoundTrack.generate(waveLoops[wave], waveIDs[wave]);

                    if (buffer == null) {
                        failed = true;
                    } else {
                        // the sample rate is 22050Hz and sample size is 1 byte which means dividing the bytes by 22 is
                        // roughly converting the bytes to time in milliseconds
                        if ((System.currentTimeMillis() + (long) (buffer.position / 22)) > (lastWaveStartTime + (long) (lastWaveLength / 22))) {
                            lastWaveLength = buffer.position;
                            lastWaveStartTime = System.currentTimeMillis();

                            if (wavesave(buffer.data, buffer.position)) {
                                lastWaveID = waveIDs[wave];
                                lastWaveLoops = waveLoops[wave];
                            } else {
                                failed = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!failed || (waveDelay[wave] == -5)) {
                waveCount--;

                for (int i = wave; i < waveCount; i++) {
                    waveIDs[i] = waveIDs[i + 1];
                    waveLoops[i] = waveLoops[i + 1];
                    waveDelay[i] = waveDelay[i + 1];
                }
                wave--;
            } else {
                waveDelay[wave] = -5;
            }
        }

        if (nextSongDelay > 0) {
            nextSongDelay -= 20;

            if (nextSongDelay < 0) {
                nextSongDelay = 0;
            }

            if ((nextSongDelay == 0) && midiEnabled) {
                song = nextSong;
                songFading = true;
                ondemand.request(2, song);
            }
        }
    }

    public void readNewPlayers() {
        while ((in.bitPosition + 10) < (packetSize * 8)) {
            int id = in.readN(11);

            if (id == 2047) {
                break;
            }

            if (players[id] == null) {
                players[id] = new ScenePlayer();
                if (playerAppearanceBuffer[id] != null) {
                    players[id].read(playerAppearanceBuffer[id]);
                }
            }

            playerIDs[playerCount++] = id;
            ScenePlayer player = players[id];
            player.cycle = loopCycle;

            if (in.readN(1) == 1) {
                entityUpdateIDs[entityUpdateCount++] = id;
            }

            int teleport = in.readN(1);
            in.readN(7); // angle
            int z = in.readN(5);
            int x = in.readN(5);

            if (z > 15) {
                z -= 32;
            }

            if (x > 15) {
                x -= 32;
            }

            player.move(localPlayer.pathTileX[0] + x, localPlayer.pathTileZ[0] + z, teleport == 1);
        }
        in.accessBytes();
    }

    public void handleMinimapInput() {
        if (minimapState != 0) {
            return;
        }

        if (super.mouseClickButton != 1) {
            return;
        }

        int x = super.mouseClickX - 25 - 550;
        int y = super.mouseClickY - 5 - 4;

        if ((x < 0) || (y < 0) || (x >= 146) || (y >= 151)) {
            return;
        }

        x -= 73;
        y -= 75;

        int yaw = (orbitCameraYaw + minimapAnticheatAngle) & 0x7ff;
        int sinYaw = Draw3D.sin[yaw];
        int cosYaw = Draw3D.cos[yaw];

        sinYaw = (sinYaw * (minimapZoom + 256)) >> 8;
        cosYaw = (cosYaw * (minimapZoom + 256)) >> 8;

        int relativeX = ((y * sinYaw) + (x * cosYaw)) >> 11;
        int relativeZ = ((y * cosYaw) - (x * sinYaw)) >> 11;

        int tileX = (localPlayer.x + relativeX) >> 7;
        int tileZ = (localPlayer.z - relativeZ) >> 7;

        boolean ok = tryMove(1, localPlayer.pathTileX[0], localPlayer.pathTileZ[0], tileX, tileZ, 0, 0, 0, 0, 0, true);
    }

    public String getIntString(int i) {
        if (i < 999999999) {
            return String.valueOf(i);
        }
        return "*";
    }

    public void drawError() {
        Graphics g = this.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 765, 503);

        setFramerate(1);

        flameActive = false; // tell flame threads to end

        int y = 35;

        if (errorLoading) {
            g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 16));
            g.setColor(Color.yellow);
            g.drawString("Sorry, an error has occured whilst loading RuneScape", 30, y);
            y += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, y);
            y += 50;
            g.setColor(Color.white);
            g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, y);
            y += 30;
            g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, y);
            y += 30;
            g.drawString("3: Try using a different game-world", 30, y);
            y += 30;
            g.drawString("4: Try rebooting your computer", 30, y);
            y += 30;
            g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, y);
        }

        if (errorHost) {
            g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString("Error - unable to load game!", 50, 50);
            g.drawString("To play RuneScape make sure you play from", 50, 100);
            g.drawString("http://www.runescape.com", 50, 150);
        }

        if (errorStarted) {
            g.setColor(Color.yellow);
            g.drawString("Error a copy of RuneScape already appears to be loaded", 30, y);
            y += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, y);
            y += 50;
            g.setColor(Color.white);
            g.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, y);
            y += 30;
            g.drawString("2: Try rebooting your computer, and reloading", 30, y);
        }
    }

    public void updateNPCs() {
        for (int i = 0; i < npcCount; i++) {
            SceneNPC npc = npcs[npcIDs[i]];
            if (npc != null) {
                updateEntity(npc);
            }
        }
    }

    public void updateEntity(SceneCharacter entity) {
        if ((entity.x < 128) || (entity.z < 128) || (entity.x >= 13184) || (entity.z >= 13184)) {
            entity.primarySeqID = -1;
            entity.spotanimID = -1;
            entity.forceMoveEndCycle = 0;
            entity.forceMoveStartCycle = 0;
            entity.x = (entity.pathTileX[0] * 128) + (entity.size * 64);
            entity.z = (entity.pathTileZ[0] * 128) + (entity.size * 64);
            entity.resetPath();
        }
        if ((entity == localPlayer) && ((entity.x < 1536) || (entity.z < 1536) || (entity.x >= 11776) || (entity.z >= 11776))) {
            entity.primarySeqID = -1;
            entity.spotanimID = -1;
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

    public void updateForceMovement(SceneCharacter entity) {
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

    public void startForceMovement(SceneCharacter entity) {
        if ((entity.forceMoveStartCycle == loopCycle) || (entity.primarySeqID == -1) || (entity.primarySeqDelay != 0) || ((entity.primarySeqCycle + 1) > Animation.instances[entity.primarySeqID].getFrameDuration(entity.primarySeqFrame))) {
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

    public void updateMovement(SceneCharacter entity) {
        entity.secondarySeqID = entity.seqStandID;

        if (entity.pathLength == 0) {
            entity.seqTrigger = 0;
            return;
        }

        if ((entity.primarySeqID != -1) && (entity.primarySeqDelay == 0)) {
            Animation seq = Animation.instances[entity.primarySeqID];

            // if we're moving, and our move style is 0:
            //      Move faster[, and look a tile/entity if applicable.] (Side effects of seqTrigger)
            if ((entity.seqPathLength > 0) && (seq._move_type == 0)) {
                entity.seqTrigger++;
                return;
            }

            // if we're stationary, and our idle style is 0:
            //      Look at a tile/entity if applicable. (Side effect of seqTrigger)
            if ((entity.seqPathLength <= 0) && (seq._idle_type == 0)) {
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

        if ((entity.yaw != entity.dstYaw) && (entity.targetID == -1) && (entity.turnSpeed != 0)) {
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

    public void updateFacingDirection(SceneCharacter e) {
        if (e.turnSpeed == 0) {
            return;
        }

        if ((e.targetID != -1) && (e.targetID < 32768)) {
            SceneNPC npc = npcs[e.targetID];

            if (npc != null) {
                int dstX = e.x - npc.x;
                int dstZ = e.z - npc.z;

                if ((dstX != 0) || (dstZ != 0)) {
                    e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
                }
            }
        }
        if (e.targetID >= 32768) {
            int id = e.targetID - 32768;

            if (id == localPID) {
                id = LOCAL_PLAYER_INDEX;
            }

            ScenePlayer player = players[id];

            if (player != null) {
                int dstX = e.x - player.x;
                int dstZ = e.z - player.z;

                if ((dstX != 0) || (dstZ != 0)) {
                    e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if (((e.targetTileX != 0) || (e.targetTileZ != 0)) && ((e.pathLength == 0) || (e.seqTrigger > 0))) {
            int dstX = e.x - ((e.targetTileX - sceneBaseTileX - sceneBaseTileX) * 64);
            int dstZ = e.z - ((e.targetTileZ - sceneBaseTileZ - sceneBaseTileZ) * 64);

            if ((dstX != 0) || (dstZ != 0)) {
                e.dstYaw = (int) (Math.atan2(dstX, dstZ) * 325.94900000000001D) & 0x7ff;
            }

            e.targetTileX = 0;
            e.targetTileZ = 0;
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

    public void updateSequences(SceneCharacter e) {
        e.needsForwardDrawPadding = false;

        if (e.secondarySeqID != -1) {
            Animation seq = Animation.instances[e.secondarySeqID];
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

        if ((e.spotanimID != -1) && (loopCycle >= e.spotanimLastCycle)) {
            if (e.spotanimFrame < 0) {
                e.spotanimFrame = 0;
            }

            Animation seq = SpotAnim.instances[e.spotanimID].seq;

            for (e.spotanimCycle++; (e.spotanimFrame < seq.frameCount) && (e.spotanimCycle > seq.getFrameDuration(e.spotanimFrame)); e.spotanimFrame++) {
                e.spotanimCycle -= seq.getFrameDuration(e.spotanimFrame);
            }

            if ((e.spotanimFrame >= seq.frameCount) && ((e.spotanimFrame < 0) || (e.spotanimFrame >= seq.frameCount))) {
                e.spotanimID = -1;
            }
        }

        if ((e.primarySeqID != -1) && (e.primarySeqDelay <= 1)) {
            Animation seq = Animation.instances[e.primarySeqID];

            // we're moving, and it's not due to a force move:
            //  pause primary sequence
            if ((seq._move_type == 1) && (e.seqPathLength > 0) && (e.forceMoveEndCycle <= loopCycle) && (e.forceMoveStartCycle < loopCycle)) {
                e.primarySeqDelay = 1;
                return;
            }
        }

        if ((e.primarySeqID != -1) && (e.primarySeqDelay == 0)) {
            Animation seq = Animation.instances[e.primarySeqID];

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
            e.needsForwardDrawPadding = seq.forwardRenderPadding;
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
            redrawSidebar = true;
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

        if (menuVisible && (menuArea == 1)) {
            redrawSidebar = true;
        }

        if (sidebarInterfaceID != -1) {
            if (updateInterfaceAnimation(delta, sidebarInterfaceID)) {
                redrawSidebar = true;
            }
        }

        if (actionArea == 2) {
            redrawSidebar = true;
        }

        if (objDragArea == 2) {
            redrawSidebar = true;
        }

        if (redrawSidebar) {
            drawSidebar();
            redrawSidebar = false;
        }

        if (chatInterfaceID == -1) {
            chatInterface.scrollPosition = chatScrollHeight - chatScrollOffset - 77;

            if ((super.mouseX > 448) && (super.mouseX < 560) && (super.mouseY > 332)) {
                handleScrollInput(463, 77, super.mouseX - 17, super.mouseY - 357, chatInterface, 0, false, chatScrollHeight);
            }

            int offset = chatScrollHeight - 77 - chatInterface.scrollPosition;

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

        if (chatInterfaceID != -1) {
            if (updateInterfaceAnimation(delta, chatInterfaceID)) {
                redrawChatback = true;
            }
        }

        if (actionArea == 3) {
            redrawChatback = true;
        }

        if (objDragArea == 3) {
            redrawChatback = true;
        }

        if (modalMessage != null) {
            redrawChatback = true;
        }

        if (menuVisible && (menuArea == 2)) {
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
        if (sidebarInterfaceID == -1) {
            if (tabInterfaceID[selectedTab] != -1) {
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
            if ((tabInterfaceID[0] != -1) && ((flashingTab != 0) || ((loopCycle % 20) < 10))) {
                imageSideicons[0].blit(29, 13);
            }
            if ((tabInterfaceID[1] != -1) && ((flashingTab != 1) || ((loopCycle % 20) < 10))) {
                imageSideicons[1].blit(53, 11);
            }
            if ((tabInterfaceID[2] != -1) && ((flashingTab != 2) || ((loopCycle % 20) < 10))) {
                imageSideicons[2].blit(82, 11);
            }
            if ((tabInterfaceID[3] != -1) && ((flashingTab != 3) || ((loopCycle % 20) < 10))) {
                imageSideicons[3].blit(115, 12);
            }
            if ((tabInterfaceID[4] != -1) && ((flashingTab != 4) || ((loopCycle % 20) < 10))) {
                imageSideicons[4].blit(153, 13);
            }
            if ((tabInterfaceID[5] != -1) && ((flashingTab != 5) || ((loopCycle % 20) < 10))) {
                imageSideicons[5].blit(180, 11);
            }
            if ((tabInterfaceID[6] != -1) && ((flashingTab != 6) || ((loopCycle % 20) < 10))) {
                imageSideicons[6].blit(208, 13);
            }
        }
        areaBackhmid1.draw(super.graphics, 516, 160);
        areaBackbase2.bind();
        imageBackbase2.blit(0, 0);
        if (sidebarInterfaceID == -1) {
            if (tabInterfaceID[selectedTab] != -1) {
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
            if ((tabInterfaceID[8] != -1) && ((flashingTab != 8) || ((loopCycle % 20) < 10))) {
                imageSideicons[7].blit(74, 2);
            }
            if ((tabInterfaceID[9] != -1) && ((flashingTab != 9) || ((loopCycle % 20) < 10))) {
                imageSideicons[8].blit(102, 3);
            }
            if ((tabInterfaceID[10] != -1) && ((flashingTab != 10) || ((loopCycle % 20) < 10))) {
                imageSideicons[9].blit(137, 4);
            }
            if ((tabInterfaceID[11] != -1) && ((flashingTab != 11) || ((loopCycle % 20) < 10))) {
                imageSideicons[10].blit(174, 2);
            }
            if ((tabInterfaceID[12] != -1) && ((flashingTab != 12) || ((loopCycle % 20) < 10))) {
                imageSideicons[11].blit(201, 2);
            }
            if ((tabInterfaceID[13] != -1) && ((flashingTab != 13) || ((loopCycle % 20) < 10))) {
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

    public boolean handleSocialMenuOption(Iface iface) {
        int type = iface.contentType;
        if (((type >= 1) && (type <= 200)) || ((type >= 701) && (type <= 900))) {
            if (type >= 801) {
                type -= 701;
            } else if (type >= 701) {
                type -= 601;
            } else if (type >= 101) {
                type -= 101;
            } else {
                type--;
            }
            addMenuOption("Remove @whi@" + friendName[type], 792);
            addMenuOption("Message @whi@" + friendName[type], 639);
            return true;
        }
        if ((type >= 401) && (type <= 500)) {
            addMenuOption("Remove @whi@" + iface.text, 322);
            return true;
        } else {
            return false;
        }
    }

    public void pushSpotanims() {
        SceneSpotAnim anim = (SceneSpotAnim) spotanims.peekFront();

        for (; anim != null; anim = (SceneSpotAnim) spotanims.prev()) {
            if ((anim.level != currentLevel) || anim.seqComplete) {
                anim.unlink();
            } else if (loopCycle >= anim.startCycle) {
                anim.update(delta);
                if (anim.seqComplete) {
                    anim.unlink();
                } else {
                    scene.push_temporary(anim, anim.level, anim.x, anim.z, anim.y, 0, -1, false, 60);
                }
            }
        }
    }

    public void drawParentInterface(Iface parent, int px, int py, int scrollY) {
        if ((parent.type != Iface.TYPE_PARENT) || (parent.childID == null)) {
            return;
        }

        if (parent.hide && (viewportHoveredInterfaceID != parent.id) && (sidebarHoveredInterfaceID != parent.id) && (chatHoveredInterfaceID != parent.id)) {
            return;
        }

        int left = Draw2D.left;
        int top = Draw2D.top;
        int right = Draw2D.right;
        int bottom = Draw2D.bottom;

        Draw2D.setBounds(px, py, px + parent.width, py + parent.height);

        for (int i = 0; i < parent.childID.length; i++) {
            int x = parent.childX[i] + px;
            int y = (parent.childY[i] + py) - scrollY;

            Iface child = Iface.instances[parent.childID[i]];

            x += child.x;
            y += child.y;

            if (child.contentType > 0) {
                updateInterfaceContent(child);
            }

            if (child.type == Iface.TYPE_PARENT) {
                if (child.scrollPosition > (child.scrollableHeight - child.height)) {
                    child.scrollPosition = child.scrollableHeight - child.height;
                }

                if (child.scrollPosition < 0) {
                    child.scrollPosition = 0;
                }

                drawParentInterface(child, x, y, child.scrollPosition);

                if (child.scrollableHeight > child.height) {
                    drawScrollbar(x + child.width, y, child.height, child.scrollableHeight, child.scrollPosition);
                }
            } else if (child.type == Iface.TYPE_INVENTORY) {
                drawInterfaceInventory(parent, x, y, child);
            } else if (child.type == Iface.TYPE_RECT) {
                drawInterfaceRect(x, y, child);
            } else if (child.type == Iface.TYPE_TEXT) {
                drawInterfaceText(x, y, child);
            } else if (child.type == Iface.TYPE_IMAGE) {
                drawInterfaceImage(x, y, child);
            } else if (child.type == Iface.TYPE_MODEL) {
                drawInterfaceModel(x, y, child);
            } else if (child.type == Iface.TYPE_INVENTORY_TEXT) {
                drawInterfaceInventoryText(x, y, child);
            }
        }
        Draw2D.setBounds(left, top, right, bottom);
    }

    private void drawInterfaceInventory(Iface parent, int x, int y, Iface iface) {
        int slot = 0;
        for (int row = 0; row < iface.height; row++) {
            for (int column = 0; column < iface.width; column++) {
                int slotX = x + (column * (32 + iface.inventoryMarginX));
                int slotY = y + (row * (32 + iface.inventoryMarginY));

                if (slot < 20) {
                    slotX += iface.inventorySlotOffsetX[slot];
                    slotY += iface.inventorySlotOffsetY[slot];
                }

                if (iface.inventorySlotObjID[slot] > 0) {
                    int dx = 0;
                    int dy = 0;
                    int objID = iface.inventorySlotObjID[slot] - 1;

                    if (((slotX > (Draw2D.left - 32)) && (slotX < Draw2D.right) && (slotY > (Draw2D.top - 32)) && (slotY < Draw2D.bottom)) || ((objDragArea != 0) && (objDragSlot == slot))) {
                        int outlineColor = 0;

                        if ((objSelected == 1) && (selectedObjSlot == slot) && (selectedObjInterfaceID == iface.id)) {
                            outlineColor = 0xffffff;
                        }

                        Image24 icon = Item.getIcon(objID, iface.inventorySlotObjCount[slot], outlineColor);

                        if (icon != null) {
                            if ((objDragArea != 0) && (objDragSlot == slot) && (objDragInterfaceID == iface.id)) {
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

                                icon.draw(slotX + dx, slotY + dy, 128);

                                // scroll component up if dragging obj near the top
                                if (((slotY + dy) < Draw2D.top) && (parent.scrollPosition > 0)) {
                                    int scroll = (delta * (Draw2D.top - slotY - dy)) / 3;

                                    if (scroll > (delta * 10)) {
                                        scroll = delta * 10;
                                    }

                                    if (scroll > parent.scrollPosition) {
                                        scroll = parent.scrollPosition;
                                    }
                                    parent.scrollPosition -= scroll;
                                    objGrabY += scroll;
                                }

                                // scroll component down if dragging obj near the bottom
                                if (((slotY + dy + 32) > Draw2D.bottom) && (parent.scrollPosition < (parent.scrollableHeight - parent.height))) {
                                    int scroll = (delta * ((slotY + dy + 32) - Draw2D.bottom)) / 3;

                                    if (scroll > (delta * 10)) {
                                        scroll = delta * 10;
                                    }

                                    if (scroll > (parent.scrollableHeight - parent.height - parent.scrollPosition)) {
                                        scroll = parent.scrollableHeight - parent.height - parent.scrollPosition;
                                    }
                                    parent.scrollPosition += scroll;
                                    objGrabY -= scroll;
                                }
                            } else if ((actionArea != 0) && (actionSlot == slot) && (actionInterfaceID == iface.id)) {
                                icon.draw(slotX, slotY, 128);
                            } else {
                                icon.draw(slotX, slotY);
                            }

                            if ((icon.cropW == 33) || (iface.inventorySlotObjCount[slot] != 1)) {
                                int count = iface.inventorySlotObjCount[slot];
                                fontPlain11.drawString(formatObjCount(count), slotX + 1 + dx, slotY + 10 + dy, 0);
                                fontPlain11.drawString(formatObjCount(count), slotX + dx, slotY + 9 + dy, 0xffff00);
                            }
                        }
                    }
                } else if ((iface.inventorySlotImage != null) && (slot < 20)) {
                    Image24 image = iface.inventorySlotImage[slot];

                    if (image != null) {
                        image.draw(slotX, slotY);
                    }
                }
                slot++;
            }
        }
    }

    private void drawInterfaceRect(int x, int y, Iface child) {
        boolean hovered = (chatHoveredInterfaceID == child.id) || (sidebarHoveredInterfaceID == child.id) || (viewportHoveredInterfaceID == child.id);
        int rgb;

        if (executeInterfaceScript(child)) {
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
    }

    private void drawInterfaceText(int x, int y, Iface iface) {
        BitmapFont font = iface.font;
        String text = iface.text;
        boolean hovered = (chatHoveredInterfaceID == iface.id) || (sidebarHoveredInterfaceID == iface.id) || (viewportHoveredInterfaceID == iface.id);

        int rgb;
        if (executeInterfaceScript(iface)) {
            rgb = iface.activeColor;

            if (hovered && (iface.activeHoverColor != 0)) {
                rgb = iface.activeHoverColor;
            }

            if (iface.activeText.length() > 0) {
                text = iface.activeText;
            }
        } else {
            rgb = iface.color;

            if (hovered && (iface.hoverColor != 0)) {
                rgb = iface.hoverColor;
            }
        }

        if ((iface.optionType == Iface.OPTION_TYPE_CONTINUE) && pressedContinueOption) {
            text = "Please wait...";
            rgb = iface.color;
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
                    text = text.substring(0, j) + getIntString(executeClientscript1(iface, 0)) + text.substring(j + 2);
                } while (true);
                do {
                    int j = text.indexOf("%2");
                    if (j == -1) {
                        break;
                    }
                    text = text.substring(0, j) + getIntString(executeClientscript1(iface, 1)) + text.substring(j + 2);
                } while (true);
                do {
                    int j = text.indexOf("%3");
                    if (j == -1) {
                        break;
                    }
                    text = text.substring(0, j) + getIntString(executeClientscript1(iface, 2)) + text.substring(j + 2);
                } while (true);
                do {
                    int j = text.indexOf("%4");
                    if (j == -1) {
                        break;
                    }
                    text = text.substring(0, j) + getIntString(executeClientscript1(iface, 3)) + text.substring(j + 2);
                } while (true);
                do {
                    int j = text.indexOf("%5");
                    if (j == -1) {
                        break;
                    }
                    text = text.substring(0, j) + getIntString(executeClientscript1(iface, 4)) + text.substring(j + 2);
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

            if (iface.center) {
                font.drawStringTaggableCenter(split, x + (iface.width / 2), lineY, rgb, iface.shadow);
            } else {
                font.drawStringTaggable(split, x, lineY, rgb, iface.shadow);
            }
        }
    }

    private void drawInterfaceImage(int x, int y, Iface iface) {
        Image24 image;
        if (executeInterfaceScript(iface)) {
            image = iface.activeImage;
        } else {
            image = iface.image;
        }
        if (image != null) {
            image.draw(x, y);
        }
    }

    private void drawInterfaceModel(int x, int y, Iface iface) {
        int tmpX = Draw3D.centerX;
        int tmpY = Draw3D.centerY;

        Draw3D.centerX = x + (iface.width / 2);
        Draw3D.centerY = y + (iface.height / 2);

        int eyeY = (Draw3D.sin[iface.modelPitch] * iface.modelZoom) >> 16;
        int eyeZ = (Draw3D.cos[iface.modelPitch] * iface.modelZoom) >> 16;

        boolean active = executeInterfaceScript(iface);
        int seqID;

        if (active) {
            seqID = iface.activeSeqID;
        } else {
            seqID = iface.seqID;
        }

        Model model;

        if (seqID == -1) {
            model = iface.getModel(-1, -1, active);
        } else {
            Animation type = Animation.instances[seqID];
            model = iface.getModel(type.primary_transforms[iface.seqFrame], type.secondary_transforms[iface.seqFrame], active);
        }

        if (model != null) {
            model.drawSimple(0, iface.modelYaw, 0, iface.modelPitch, 0, eyeY, eyeZ);
        }

        Draw3D.centerX = tmpX;
        Draw3D.centerY = tmpY;
    }

    private static void drawInterfaceInventoryText(int x, int y, Iface iface) {
        BitmapFont font = iface.font;
        int slot = 0;
        for (int row = 0; row < iface.height; row++) {
            for (int column = 0; column < iface.width; column++) {
                if (iface.inventorySlotObjID[slot] > 0) {
                    Item type = Item.get(iface.inventorySlotObjID[slot] - 1);
                    String text = type.name;

                    if (type.stackable || (iface.inventorySlotObjCount[slot] != 1)) {
                        text = text + " x" + formatObjCountTagged(iface.inventorySlotObjCount[slot]);
                    }

                    int textX = x + (column * (115 + iface.inventoryMarginX));
                    int textY = y + (row * (12 + iface.inventoryMarginY));

                    if (iface.center) {
                        font.drawStringTaggableCenter(text, textX + (iface.width / 2), textY, iface.color, iface.shadow);
                    } else {
                        font.drawStringTaggable(text, textX, textY, iface.color, iface.shadow);
                    }
                }
                slot++;
            }
        }
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

        if (actionKey[1]) {
            orbitCameraYawVelocity += (-24 - orbitCameraYawVelocity) / 2;
        } else if (actionKey[2]) {
            orbitCameraYawVelocity += (24 - orbitCameraYawVelocity) / 2;
        } else {
            orbitCameraYawVelocity /= 2;
        }

        if (actionKey[3]) {
            orbitCameraPitchVelocity += (12 - orbitCameraPitchVelocity) / 2;
        } else if (actionKey[4]) {
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
                    int y = orbitY - level_heightmap[level][x][z];

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

    private void drawViewportInterfaces() {
        if (viewportOverlayInterfaceID != -1) {
            updateInterfaceAnimation(delta, viewportOverlayInterfaceID);
            drawParentInterface(Iface.instances[viewportOverlayInterfaceID], 0, 0, 0);
        }

        if (viewportInterfaceID != -1) {
            updateInterfaceAnimation(delta, viewportInterfaceID);
            drawParentInterface(Iface.instances[viewportInterfaceID], 0, 0, 0);
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
        if (showOccluders) {
            for (int i = 0; i < Scene.levelOccluderCount[Scene.topLevel]; i++) {
                SceneOccluder occluder = Scene.levelOccluders[Scene.topLevel][i];

                boolean active = false;
                for (int j = 0; j < Scene.activeOccluderCount; j++) {
                    if (occluder == Scene.activeOccluders[j]) {
                        active = true;
                        break;
                    }
                }

                if (!active) {
                    continue;
                }

                int color = 0xFF0000;
                int x0 = -1, y0 = -1;
                int x1 = -1, y1 = -1;
                int x2 = -1, y2 = -1;
                int x3 = -1, y3 = -1;

                switch (occluder.type) {
                    case 1: {
                        color = 0x00FF00;
                        project(occluder.minX, occluder.minY, occluder.minZ);
                        x0 = projectX;
                        y0 = projectY;
                        project(occluder.minX, occluder.maxY, occluder.minZ);
                        x1 = projectX;
                        y1 = projectY;
                        project(occluder.minX, occluder.minY, occluder.maxZ);
                        x2 = projectX;
                        y2 = projectY;
                        project(occluder.minX, occluder.maxY, occluder.maxZ);
                        x3 = projectX;
                        y3 = projectY;
                        break;
                    }
                    case 2: {
                        color = 0x00FF00;
                        project(occluder.minX, occluder.minY, occluder.minZ);
                        x0 = projectX;
                        y0 = projectY;
                        project(occluder.maxX, occluder.minY, occluder.minZ);
                        x1 = projectX;
                        y1 = projectY;
                        project(occluder.minX, occluder.maxY, occluder.minZ);
                        x2 = projectX;
                        y2 = projectY;
                        project(occluder.maxX, occluder.maxY, occluder.minZ);
                        x3 = projectX;
                        y3 = projectY;
                        break;
                    }
                    case 4: {// Ground on XZ plane
                        color = 0xFFFF00;
                        project(occluder.minX, occluder.minY, occluder.minZ);
                        x0 = projectX;
                        y0 = projectY;
                        project(occluder.maxX, occluder.minY, occluder.minZ);
                        x1 = projectX;
                        y1 = projectY;
                        project(occluder.minX, occluder.minY, occluder.maxZ);
                        x2 = projectX;
                        y2 = projectY;
                        project(occluder.maxX, occluder.minY, occluder.maxZ);
                        x3 = projectX;
                        y3 = projectY;
                        break;
                    }
                }

                // one of our points failed to project
                if ((x0 == -1) || (x1 == -1) || (x2 == -1) || (x3 == -1)) {
                    continue;
                }

                Draw2D.drawLine(x0, y0, x1, y1, color);
                Draw2D.drawLine(x0, y0, x2, y2, color);
                Draw2D.drawLine(x0, y0, x3, y3, (color & 0xFEFEFE) >> 1);
                Draw2D.drawLine(x1, y1, x2, y2, (color & 0xFEFEFE) >> 1);
                Draw2D.drawLine(x1, y1, x3, y3, color);
                Draw2D.drawLine(x2, y2, x3, y3, color);
            }
        }

        int x = 507;
        int y = 20;

        if (showPerformance) {
            int color = 0xffff00;

            if (super.fps < 15) {
                color = 0xff0000;
            }

            fontPlain11.drawStringRight(String.format("%d fps", super.fps), x, y, color);
            y += 13;

            double ft = 0;
            for (double delta : super.frameTime) {
                ft += delta;
            }
            ft /= super.frameTime.length;

            fontPlain11.drawStringRight(String.format("%04.4f ms", ft), x, y, color);
            y += 13;

            Runtime runtime = Runtime.getRuntime();
            int mem = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            fontPlain11.drawStringRight(mem + " kB", x, y, 0xffff00);
            y += 13;
        }

        if (showTraffic) {
            fontPlain11.drawStringRight(bytesIn + " bytes in", x, y, 0xffff00);
            y += 13;

            fontPlain11.drawStringRight(bytesOut + " bytes out", x, y, 0xffff00);
            y += 13;

            if ((loopCycle % 50) == 0) {
                bytesIn = 0;
                bytesOut = 0;
            }
        }

        if (showOccluders) {
            fontPlain11.drawStringRight(String.format("%d/%d occluders", Scene.activeOccluderCount, Scene.levelOccluderCount[Scene.topLevel]), x, y, 0xFFFF00);
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
        redrawSidebar = true;
        out.writeOp(133);
        out.write64(name37);
    }

    public void updatePlayers() {
        for (int i = -1; i < playerCount; i++) {
            int playerID;
            if (i == -1) {
                playerID = LOCAL_PLAYER_INDEX;
            } else {
                playerID = playerIDs[i];
            }
            ScenePlayer player = players[playerID];
            if (player != null) {
                updateEntity(player);
            }
        }
    }

    public void updateTemporaryLocs() {
        if (sceneState == 2) {
            for (SceneTemporaryObject loc = (SceneTemporaryObject) temporaryLocs.peekFront(); loc != null; loc = (SceneTemporaryObject) temporaryLocs.prev()) {
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
        if ((super.mouseClickX > 4) && (super.mouseClickY > 4) && (super.mouseClickX < 516) && (super.mouseClickY < 338)) {
            int i1 = super.mouseClickX - 4 - (i / 2);
            if ((i1 + i) > 512) {
                i1 = 512 - i;
            }
            if (i1 < 0) {
                i1 = 0;
            }
            int l1 = super.mouseClickY - 4;
            if ((l1 + l) > 334) {
                l1 = 334 - l;
            }
            if (l1 < 0) {
                l1 = 0;
            }
            menuVisible = true;
            menuArea = 0;
            menuX = i1;
            menuY = l1;
            menuWidth = i;
            menuHeight = (15 * menuSize) + 22;
        }
        if ((super.mouseClickX > 553) && (super.mouseClickY > 205) && (super.mouseClickX < 743) && (super.mouseClickY < 466)) {
            int j1 = super.mouseClickX - 553 - (i / 2);
            if (j1 < 0) {
                j1 = 0;
            } else if ((j1 + i) > 190) {
                j1 = 190 - i;
            }
            int i2 = super.mouseClickY - 205;
            if (i2 < 0) {
                i2 = 0;
            } else if ((i2 + l) > 261) {
                i2 = 261 - l;
            }
            menuVisible = true;
            menuArea = 1;
            menuX = j1;
            menuY = i2;
            menuWidth = i;
            menuHeight = (15 * menuSize) + 22;
        }
        if ((super.mouseClickX > 17) && (super.mouseClickY > 357) && (super.mouseClickX < 496) && (super.mouseClickY < 453)) {
            int k1 = super.mouseClickX - 17 - (i / 2);
            if (k1 < 0) {
                k1 = 0;
            } else if ((k1 + i) > 479) {
                k1 = 479 - i;
            }
            int j2 = super.mouseClickY - 357;
            if (j2 < 0) {
                j2 = 0;
            } else if ((j2 + l) > 96) {
                j2 = 96 - l;
            }
            menuVisible = true;
            menuArea = 2;
            menuX = k1;
            menuY = j2;
            menuWidth = i;
            menuHeight = (15 * menuSize) + 22;
        }
    }

    public void readLocalPlayer() {
        in.accessBits();

        if (in.readN(1) == 0) {
            return;
        }

        int type = in.readN(2);

        if (type == 0) {
            entityUpdateIDs[entityUpdateCount++] = LOCAL_PLAYER_INDEX;
        } else if (type == 1) {
            localPlayer.step(false, in.readN(3));

            if (in.readN(1) == 1) {
                entityUpdateIDs[entityUpdateCount++] = LOCAL_PLAYER_INDEX;
            }
        } else if (type == 2) {
            localPlayer.step(true, in.readN(3));
            localPlayer.step(true, in.readN(3));

            if (in.readN(1) == 1) {
                entityUpdateIDs[entityUpdateCount++] = LOCAL_PLAYER_INDEX;
            }
        } else if (type == 3) {
            currentLevel = in.readN(2);
            int teleport = in.readN(1);

            if (in.readN(1) == 1) {
                entityUpdateIDs[entityUpdateCount++] = LOCAL_PLAYER_INDEX;
            }

            int z = in.readN(7);
            int x = in.readN(7);
            localPlayer.move(x, z, teleport == 1);
        }
    }

    public void unloadTitle() {
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
     * @param id    the parent interface id.
     * @return <code>true</code> if there was a sequence which updated.
     */
    public boolean updateInterfaceAnimation(int delta, int id) {
        boolean updated = false;
        Iface parent = Iface.instances[id];

        for (int k = 0; k < parent.childID.length; k++) {
            if (parent.childID[k] == -1) {
                break;
            }

            Iface child = Iface.instances[parent.childID[k]];

            if (child.type == Iface.TYPE_UNUSED) {
                updated |= updateInterfaceAnimation(delta, child.id);
            }

            if ((child.type == Iface.TYPE_MODEL) && ((child.seqID != -1) || (child.activeSeqID != -1))) {
                boolean active = executeInterfaceScript(child);
                int seqID;

                if (active) {
                    seqID = child.activeSeqID;
                } else {
                    seqID = child.seqID;
                }

                if (seqID != -1) {
                    Animation type = Animation.instances[seqID];
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

    public int getTopLevelCutscene() {
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
                redrawSidebar = true;
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

    public int executeClientscript1(Iface iface, int scriptId) {
        if ((iface.scripts == null) || (scriptId >= iface.scripts.length)) {
            return -2;
        }
        try {
            int[] script = iface.scripts[scriptId];
            int acc = 0;
            int pos = 0;
            int arith = 0;

            do {
                int code = script[pos++];
                int register = 0;
                byte nextArithmetic = 0;

                if (code == 0) {
                    return acc;
                } else if (code == 1) { // load_skill_level {skill}
                    register = skillLevel[script[pos++]];
                } else if (code == 2) { // load_skill_base_level {skill}
                    register = skillBaseLevel[script[pos++]];
                } else if (code == 3) { // load_skill_exp {skill}
                    register = skillExperience[script[pos++]];
                } else if (code == 4) {// load_inv_count {interface id} {obj id}
                    Iface inventory = Iface.instances[script[pos++]];
                    int objID = script[pos++];
                    if ((objID >= 0) && (objID < Item.count) && (!Item.get(objID).members || members)) {
                        for (int slot = 0; slot < inventory.inventorySlotObjID.length; slot++) {
                            if (inventory.inventorySlotObjID[slot] == (objID + 1)) {
                                register += inventory.inventorySlotObjCount[slot];
                            }
                        }
                    }
                } else if (code == 5) { // load_var {id}
                    register = varps[script[pos++]];
                } else if (code == 6) { // load_next_level_xp {skill}
                    register = levelExperience[skillBaseLevel[script[pos++]] - 1];
                } else if (code == 7) {
                    register = (varps[script[pos++]] * 100) / 46875;
                } else if (code == 8) { // load_combat_level
                    register = localPlayer.combatLevel;
                } else if (code == 9) { // load_total_level
                    for (int skill = 0; skill < Skill.COUNT; skill++) {
                        if (Skill.ENABLED[skill]) {
                            register += skillBaseLevel[skill];
                        }
                    }
                } else if (code == 10) {// load_inv_contains {interface id} {obj id}
                    Iface c = Iface.instances[script[pos++]];
                    int objID = script[pos++] + 1;
                    if ((objID >= 0) && (objID < Item.count) && (!Item.get(objID).members || members)) {
                        for (int slot = 0; slot < c.inventorySlotObjID.length; slot++) {
                            if (c.inventorySlotObjID[slot] != objID) {
                                continue;
                            }
                            register = 999999999;
                            break;
                        }
                    }
                } else if (code == 11) { // load_energy
                    register = energy;
                } else if (code == 12) { // load_weight
                    register = weightCarried;
                } else if (code == 13) {// load_bool {varp} {bit: 0..31}
                    int varp = varps[script[pos++]];
                    int bit = script[pos++];
                    register = ((varp & (1 << bit)) == 0) ? 0 : 1;
                } else if (code == 14) {// load_varbit {varbit}
                    Varbit varbit = Varbit.instances[script[pos++]];
                    int lsb = varbit.lsb;
                    register = (varps[varbit.varp] >> lsb) & BITMASK[varbit.msb - lsb];
                } else if (code == 15) { // sub
                    nextArithmetic = 1;
                } else if (code == 16) { // div
                    nextArithmetic = 2;
                } else if (code == 17) { // mul
                    nextArithmetic = 3;
                } else if (code == 18) { // load_world_x
                    register = (localPlayer.x >> 7) + sceneBaseTileX;
                } else if (code == 19) { // load_world_z
                    register = (localPlayer.z >> 7) + sceneBaseTileZ;
                } else if (code == 20) { // load {value}
                    register = script[pos++];
                }

                if (nextArithmetic == 0) {
                    if (arith == 0) {
                        acc += register;
                    }
                    if (arith == 1) {
                        acc -= register;
                    }
                    if ((arith == 2) && (register != 0)) {
                        acc /= register;
                    }
                    if (arith == 3) {
                        acc *= register;
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
        if ((menuSize < 2) && (objSelected == 0) && (spellSelected == 0)) {
            return;
        }
        String tooltip;

        if ((objSelected == 1) && (menuSize < 2)) {
            tooltip = "Use " + selectedObjName + " with...";
        } else if ((spellSelected == 1) && (menuSize < 2)) {
            tooltip = spellCaption + "...";
        } else {
            tooltip = menuOption[menuSize - 1];
        }

        if (menuSize > 2) {
            tooltip = tooltip + "@whi@ / " + (menuSize - 2) + " more options";
        }
        fontBold12.drawStringTooltip(tooltip, 4, 15, 0xffffff, true, loopCycle / 1000);
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

        drawMinimapFunctions();
        drawMinimapObjs();
        drawMinimapNPCs();
        drawMinimapPlayers();
        drawMinimapHint();
        drawMinimapFlag();

        // center dot
        Draw2D.fillRect(97, 78, 3, 3, 0xffffff);

        areaViewport.bind();
    }

    private void drawMinimapFunctions() {
        for (int i = 0; i < activeMapFunctionCount; i++) {
            int x = ((activeMapFunctionX[i] * 4) + 2) - (localPlayer.x / 32);
            int y = ((activeMapFunctionZ[i] * 4) + 2) - (localPlayer.z / 32);
            drawOnMinimap(activeMapFunctions[i], x, y);
        }
    }

    private void drawMinimapObjs() {
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
    }

    private void drawMinimapNPCs() {
        for (int i = 0; i < npcCount; i++) {
            SceneNPC npc = npcs[npcIDs[i]];
            if ((npc == null) || !npc.isVisible()) {
                continue;
            }
            NPC type = npc.type;
            if (type.overrides != null) {
                type = type.evaluate();
            }
            if ((type != null) && type._mapdot && type._interactable) {
                int x = (npc.x / 32) - (localPlayer.x / 32);
                int y = (npc.z / 32) - (localPlayer.z / 32);
                drawOnMinimap(imageMapdot1, x, y);
            }
        }
    }

    private void drawMinimapPlayers() {
        for (int i = 0; i < playerCount; i++) {
            ScenePlayer player = players[playerIDs[i]];

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
    }

    private void drawMinimapHint() {
        if ((hintType != 0) && ((loopCycle % 20) < 10)) {
            if ((hintType == 1) && (hintNPC >= 0) && (hintNPC < npcs.length)) {
                SceneNPC npc = npcs[hintNPC];

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
                ScenePlayer player = players[hintPlayer];
                if (player != null) {
                    int x = (player.x / 32) - (localPlayer.x / 32);
                    int z = (player.z / 32) - (localPlayer.z / 32);
                    drawMinimapHint(imageMapmarker1, x, z);
                }
            }
        }
    }

    private void drawMinimapFlag() {
        if (flagSceneTileX != 0) {
            int flagX = ((flagSceneTileX * 4) + 2) - (localPlayer.x / 32);
            int flagY = ((flagSceneTileZ * 4) + 2) - (localPlayer.z / 32);
            drawOnMinimap(imageMapmarker0, flagX, flagY);
        }
    }

    /**
     * @param entity the entity.
     * @param height the height off the ground.
     * @see #projectX
     * @see #projectY
     */
    public void projectFromGround(SceneCharacter entity, int height) {
        projectFromGround(entity.x, height, entity.z);
    }

    /**
     * Projects a point in the scene onto the screen and stores it in <code>projectX/Y</code> The reason the second
     * parameter is called <code>height</code> as opposed to <code>y</code> is due to the y coordinate originating from
     * ground level.
     *
     * @param x      the x coordinate in scene space.
     * @param height the height off the ground.
     * @param z      the z coordinate in scene space.
     * @see #projectX
     * @see #projectY
     */
    public void projectFromGround(int x, int height, int z) {
        if ((x < 128) || (z < 128) || (x > 13056) || (z > 13056)) {
            projectX = -1;
            projectY = -1;
            return;
        }
        project(x, getHeightmapY(currentLevel, x, z) - height, z);
    }

    private void project(int x, int y, int z) {
        x -= cameraX;
        y -= cameraY;
        z -= cameraZ;

        int sinPitch = Model.sin[cameraPitch];
        int cosPitch = Model.cos[cameraPitch];
        int sinYaw = Model.sin[cameraYaw];
        int cosYaw = Model.cos[cameraYaw];

        int tmp = ((z * sinYaw) + (x * cosYaw)) >> 16;
        z = ((z * cosYaw) - (x * sinYaw)) >> 16;
        x = tmp;

        tmp = ((y * cosPitch) - (z * sinPitch)) >> 16;
        z = ((y * sinPitch) + (z * cosPitch)) >> 16;
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
                            addMenuOption("Report abuse @whi@" + sender, 2606);
                        }
                        addMenuOption("Add ignore @whi@" + sender, 2042);
                        addMenuOption("Add friend @whi@" + sender, 2337);
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

    public void appendLoc(int duration, int id, int rotation, int classID, int z, int kind, int level, int x,
                          int delay) {
        SceneTemporaryObject loc = null;
        for (SceneTemporaryObject other = (SceneTemporaryObject) temporaryLocs.peekFront(); other != null; other = (SceneTemporaryObject) temporaryLocs.prev()) {
            if ((other.level != level) || (other.localX != x) || (other.localZ != z) || (other.classID != classID)) {
                continue;
            }
            loc = other;
            break;
        }
        if (loc == null) {
            loc = new SceneTemporaryObject();
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
     * @param iface the interface.
     * @return the state.
     */
    public boolean executeInterfaceScript(Iface iface) {
        if (iface.scriptComparator == null) {
            return false;
        }
        for (int i = 0; i < iface.scriptComparator.length; i++) {
            int value = executeClientscript1(iface, i);
            int operand = iface.scriptOperand[i];

            if (iface.scriptComparator[i] == 2) {
                if (value >= operand) {
                    return false;
                }
            } else if (iface.scriptComparator[i] == 3) {
                if (value <= operand) {
                    return false;
                }
            } else if (iface.scriptComparator[i] == 4) {
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
        return new DataInputStream(URI.create("https://cdn.scape05.com/" + s).toURL().openStream());
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

    public void readPlayers() {
        int count = in.readN(8);

        if (count < playerCount) {
            for (int i = count; i < playerCount; i++) {
                entityRemovalIDs[entityRemovalCount++] = playerIDs[i];
            }
        }

        if (count > playerCount) {
            Signlink.reporterror(username + " Too many players");
            throw new RuntimeException("eek");
        }

        playerCount = 0;

        for (int i = 0; i < count; i++) {
            int id = playerIDs[i];
            ScenePlayer player = players[id];

            if (in.readN(1) == 0) {
                playerIDs[playerCount++] = id;
                player.cycle = loopCycle;
            } else {
                int type = in.readN(2);

                if (type == 0) {
                    playerIDs[playerCount++] = id;
                    player.cycle = loopCycle;

                    entityUpdateIDs[entityUpdateCount++] = id;
                } else if (type == 1) {
                    playerIDs[playerCount++] = id;
                    player.cycle = loopCycle;

                    player.step(false, in.readN(3));

                    if (in.readN(1) == 1) {
                        entityUpdateIDs[entityUpdateCount++] = id;
                    }
                } else if (type == 2) {
                    playerIDs[playerCount++] = id;
                    player.cycle = loopCycle;

                    player.step(true, in.readN(3));
                    player.step(true, in.readN(3));

                    if (in.readN(1) == 1) {
                        entityUpdateIDs[entityUpdateCount++] = id;
                    }
                } else if (type == 3) {
                    entityRemovalIDs[entityRemovalCount++] = id;
                }
            }
        }
    }

    public void drawTitleScreen(boolean hideButtons) throws IOException {
        loadTitle();
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

            fontBold12.drawStringTaggable("Username: " + username + (titleLoginField == 0 & loopCycle % 40 < 20 ? "@yel@|" : ""), (w / 2) - 90, y, 0xffffff, true);
            y += 15;

            fontBold12.drawStringTaggable("Password: " + StringUtil.toAsterisks(password) + (titleLoginField == 1 & loopCycle % 40 < 20 ? "@yel@|" : ""), (w / 2) - 88, y, 0xffffff, true);

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

    public void readZonePacket(int code) {
        switch (code) {
            case PacketIn.OBJ_ADD:
                readObjAdd();
                break;
            case PacketIn.OBJ_REVEAL:
                readObjReveal();
                break;
            case PacketIn.OBJ_COUNT:
                readObjCount();
                break;
            case PacketIn.OBJ_DEL:
                readObjDelete();
                break;
            case PacketIn.LOC_ADD:
                readLocAdd();
                break;
            case PacketIn.LOC_CHANGE:
                readLocChange();
                break;
            case PacketIn.LOC_DEL:
                readLocDelete();
                break;
            case PacketIn.LOC_PLAYER:
                readLocPlayer();
                break;
            case PacketIn.MAP_ANIM:
                readMapAnim();
                break;
            case PacketIn.MAP_SOUND:
                readMapSound();
                break;
            case PacketIn.MAP_PROJECTILE:
                readMapProjectile();
                break;
        }
    }

    private void readObjAdd() {
        int objID = in.readU16();
        int objCount = in.readU16();
        int pos = in.readU8();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            SceneItem stack = new SceneItem();
            stack.id = objID;
            stack.count = objCount;
            if (levelObjStacks[currentLevel][x][z] == null) {
                levelObjStacks[currentLevel][x][z] = new DoublyLinkedList();
            }
            levelObjStacks[currentLevel][x][z].pushBack(stack);
            sortObjStacks(x, z);
        }
    }

    private void readObjReveal() {
        int objID = in.readU16A();
        int pos = in.readU8S();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int ownerPID = in.readU16A();
        int objCount = in.readU16();
        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104) && (ownerPID != localPID)) {
            SceneItem obj = new SceneItem();
            obj.id = objID;
            obj.count = objCount;
            if (levelObjStacks[currentLevel][x][z] == null) {
                levelObjStacks[currentLevel][x][z] = new DoublyLinkedList();
            }
            levelObjStacks[currentLevel][x][z].pushBack(obj);
            sortObjStacks(x, z);
        }
    }

    private void readObjCount() {
        int pos = in.readU8();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int objID = in.readU16();
        int oldCount = in.readU16();
        int newCount = in.readU16();

        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            DoublyLinkedList stacks = levelObjStacks[currentLevel][x][z];
            if (stacks != null) {
                for (SceneItem stack = (SceneItem) stacks.peekFront(); stack != null; stack = (SceneItem) stacks.prev()) {
                    if ((stack.id != (objID & 0x7fff)) || (stack.count != oldCount)) {
                        continue;
                    }
                    stack.count = newCount;
                    break;
                }
                sortObjStacks(x, z);
            }
        }
    }

    private void readObjDelete() {
        int pos = in.readU8A();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int objID = in.readU16();
        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            DoublyLinkedList list = levelObjStacks[currentLevel][x][z];
            if (list != null) {
                for (SceneItem obj = (SceneItem) list.peekFront(); obj != null; obj = (SceneItem) list.prev()) {
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
    }

    private void readLocAdd() {
        int pos = in.readU8A();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int id = in.readU16LE();
        int info = in.readU8S();
        int kind = info >> 2;
        int rotation = info & 3;
        int classID = LOC_KIND_TO_CLASS_ID[kind];
        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            appendLoc(-1, id, rotation, classID, z, kind, currentLevel, x, 0);
        }
    }

    private void readLocChange() {
        int pos = in.readU8S();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int info = in.readU8S();
        int kind = info >> 2;
        int rotation = info & 3;
        int classID = LOC_KIND_TO_CLASS_ID[kind];
        int seqID = in.readU16A();

        if ((x < 0) || (z < 0) || (x >= 103) || (z >= 103)) {
            return;
        }

        int heightmapSW = level_heightmap[currentLevel][x][z];
        int heightmapSE = level_heightmap[currentLevel][x + 1][z];
        int heightmapNE = level_heightmap[currentLevel][x + 1][z + 1];
        int heightmapNW = level_heightmap[currentLevel][x][z + 1];

        if (classID == 0) {
            SceneWall wall = scene.getWall(currentLevel, x, z);

            if (wall != null) {
                int locID = (wall.bitset >> 14) & 0x7fff;

                if (kind == 2) {
                    wall.entityA = new SceneObject(locID, 4 + rotation, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
                    wall.entityB = new SceneObject(locID, (rotation + 1) & 3, 2, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
                } else {
                    wall.entityA = new SceneObject(locID, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
                }
            }
        }

        if (classID == 1) {
            SceneWallDecoration deco = scene.getWallDecoration(currentLevel, x, z);

            if (deco != null) {
                deco.drawable = new SceneObject((deco.bitset >> 14) & 0x7fff, 0, 4, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
            }
        }

        if (classID == 2) {
            SceneDrawable generic = scene.getLoc(currentLevel, x, z);

            if (kind == 11) {
                kind = 10;
            }

            if (generic != null) {
                generic.drawable = new SceneObject((generic.bitset >> 14) & 0x7fff, rotation, kind, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
            }
        }

        if (classID == 3) {
            SceneGroundDecoration deco = scene.getGroundDecoration(z, x, currentLevel);

            if (deco != null) {
                deco.entity = new SceneObject((deco.bitset >> 14) & 0x7fff, rotation, 22, heightmapSE, heightmapNE, heightmapSW, heightmapNW, seqID, false);
            }
        }
    }

    private void readLocDelete() {
        int pos = in.readU8();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int info = in.readU8();
        int kind = info >> 2;
        int rotation = info & 3;
        int classID = LOC_KIND_TO_CLASS_ID[kind];
        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            appendLoc(-1, -1, rotation, classID, z, kind, currentLevel, x, 0);
        }
    }

    private void readLocPlayer() {
        int pos = in.readU8S();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int pid = in.readU16();
        byte maxX = in.read8S();
        int delay = in.readU16LE();
        byte maxZ = in.read8C();
        int duration = in.readU16();
        int info = in.readU8S();
        int kind = info >> 2;
        int rotation = info & 3;
        int classID = LOC_KIND_TO_CLASS_ID[kind];
        byte minX = in.read8();
        int locID = in.readU16();
        byte minZ = in.read8C();
        ScenePlayer player;

        if (pid == localPID) {
            player = localPlayer;
        } else {
            player = players[pid];
        }

        if (player != null) {
            Obj type = Obj.get(locID);
            int heightmapSW = level_heightmap[currentLevel][x][z];
            int heightmapSE = level_heightmap[currentLevel][x + 1][z];
            int heightmapNE = level_heightmap[currentLevel][x + 1][z + 1];
            int heightmapNW = level_heightmap[currentLevel][x][z + 1];

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

    private void readMapAnim() {
        int pos = in.readU8();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int id = in.readU16();
        int y = in.readU8();
        int delay = in.readU16();

        if ((x >= 0) && (z >= 0) && (x < 104) && (z < 104)) {
            x = (x * 128) + 64;
            z = (z * 128) + 64;
            SceneSpotAnim anim = new SceneSpotAnim(currentLevel, loopCycle, delay, id, getHeightmapY(currentLevel, x, z) - y, z, x);
            spotanims.pushBack(anim);
        }
    }

    private void readMapSound() {
        int pos = in.readU8();
        int x = baseX + ((pos >> 4) & 7);
        int z = baseZ + (pos & 7);
        int track = in.readU16();
        int delay = in.readU16();
        int info = in.readU8();
        int maxDist = (info >> 4) & 0xf;
        int loopCount = info & 0b111;
        in.read8();
        in.read8();
        if ((localPlayer.pathTileX[0] >= (x - maxDist)) && (localPlayer.pathTileX[0] <= (x + maxDist)) && (localPlayer.pathTileZ[0] >= (z - maxDist)) && (localPlayer.pathTileZ[0] <= (z + maxDist)) && waveEnabled && (waveCount < 50)) {
            waveIDs[waveCount] = track;
            waveLoops[waveCount] = loopCount;
            waveDelay[waveCount] = SoundTrack.delays[track];
            waveCount++;
        }
    }

    private void readMapProjectile() {
        int pos = in.readU8();
        int srcX = baseX + ((pos >> 4) & 7);
        int srcZ = baseZ + (pos & 7);
        int dstX = srcX + in.read8();
        int dstZ = srcZ + in.read8();
        int targetID = in.read16();
        int spotanimID = in.readU16();
        int srcY = in.readU8() * 4;
        int dstY = in.readU8() * 4;
        int delay = in.readU16();
        int duration = in.readU16();
        int peakPitch = in.readU8();
        int arcSize = in.readU8();
        if ((srcX >= 0) && (srcZ >= 0) && (srcX < 104) && (srcZ < 104) && (dstX >= 0) && (dstZ >= 0) && (dstX < 104) && (dstZ < 104) && (spotanimID != 65535)) {
            srcX = (srcX * 128) + 64;
            srcZ = (srcZ * 128) + 64;
            dstX = (dstX * 128) + 64;
            dstZ = (dstZ * 128) + 64;
            SceneProjectile projectile = new SceneProjectile(peakPitch, dstY, delay + loopCycle, duration + loopCycle, arcSize, currentLevel, getHeightmapY(currentLevel, srcX, srcZ) - srcY, srcZ, srcX, targetID, spotanimID);
            projectile.updateVelocity(delay + loopCycle, dstZ, getHeightmapY(currentLevel, dstX, dstZ) - dstY, dstX);
            projectiles.pushBack(projectile);
        }
    }

    public void readNPCs() {
        in.accessBits();
        int count = in.readN(8);

        if (count < npcCount) {
            for (int l = count; l < npcCount; l++) {
                entityRemovalIDs[entityRemovalCount++] = npcIDs[l];
            }
        }

        if (count > npcCount) {
            Signlink.reporterror(username + " Too many npcs");
            throw new RuntimeException("eek");
        }

        npcCount = 0;
        for (int i = 0; i < count; i++) {
            int id = npcIDs[i];
            SceneNPC npc = npcs[id];

            if (in.readN(1) == 0) {
                npcIDs[npcCount++] = id;
                npc.cycle = loopCycle;
            } else {
                int type = in.readN(2);

                if (type == 0) {
                    npcIDs[npcCount++] = id;
                    npc.cycle = loopCycle;
                    entityUpdateIDs[entityUpdateCount++] = id;
                } else if (type == 1) {
                    npcIDs[npcCount++] = id;
                    npc.cycle = loopCycle;

                    npc.step(false, in.readN(3));

                    if (in.readN(1) == 1) {
                        entityUpdateIDs[entityUpdateCount++] = id;
                    }
                } else if (type == 2) {
                    npcIDs[npcCount++] = id;
                    npc.cycle = loopCycle;

                    npc.step(true, in.readN(3));
                    npc.step(true, in.readN(3));

                    if (in.readN(1) == 1) {
                        entityUpdateIDs[entityUpdateCount++] = id;
                    }
                } else if (type == 3) {
                    entityRemovalIDs[entityRemovalCount++] = id;
                }
            }
        }
    }

    public void updateTitle() {
        if (titleScreenState == 0) {
            int x = (super.screenWidth / 2) - 80;
            int y = (super.screenHeight / 2) + 20;
            y += 20;

            if ((super.mouseClickButton == 1) && (super.mouseClickX >= (x - 75)) && (super.mouseClickX <= (x + 75)) && (super.mouseClickY >= (y - 20)) && (super.mouseClickY <= (y + 20))) {
                final OpenOption[] options = new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING};

                for (int id : new int[]{0, 20, 33, 135, 147, 148, 336, 2558}) {
                    try {
                        Gson gson = new Gson();
                        Model model = Model.get(id);
                        Files.write(Paths.get("out/", id + ".json"), gson.toJson(model).getBytes(), options);

                        model.calculateBoundsAABB();
                        Files.write(Paths.get("out/", id + "_1.json"), gson.toJson(model).getBytes(), options);

                        model.build_labels();
                        Files.write(Paths.get("out/", id + "_2.json"), gson.toJson(model).getBytes(), options);

                        model.build(64, 850, -30, -50, -30, false);
                        Files.write(Paths.get("out/", id + "_3.json"), gson.toJson(model).getBytes(), options);

                        model.buildLighting(64, 850, -30, -50, -30);
                        Files.write(Paths.get("out/", id + "_4.json"), gson.toJson(model).getBytes(), options);

                        model = Model.get(id);
                        model.build(64, 850, -30, -50, -30, true);
                        Files.write(Paths.get("out/", id + "_5.json"), gson.toJson(model).getBytes(), options);

                        model.rotateX(384);
                        model.rotateY90();
                        model.mirrorZ();
                        Files.write(Paths.get("out/", id + "_6.json"), gson.toJson(model).getBytes(), options);

                        model.translate(40, 69, 120);
                        Files.write(Paths.get("out/", id + "_7.json"), gson.toJson(model).getBytes(), options);

                        model.scale(64, 196, 100);
                        Files.write(Paths.get("out/", id + "_8.json"), gson.toJson(model).getBytes(), options);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            x = (super.screenWidth / 2) + 80;

            if ((super.mouseClickButton == 1) && (super.mouseClickX >= (x - 75)) && (super.mouseClickX <= (x + 75)) && (super.mouseClickY >= (y - 20)) && (super.mouseClickY <= (y + 20))) {
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
                if ((super.mouseClickButton == 1) && (super.mouseClickY >= (fieldY - 15)) && (super.mouseClickY < fieldY)) {
                    titleLoginField = 0;
                }
                fieldY += 15;
                if ((super.mouseClickButton == 1) && (super.mouseClickY >= (fieldY - 15)) && (super.mouseClickY < fieldY)) {
                    titleLoginField = 1;
                }

                int buttonX = (super.screenWidth / 2) - 80;
                int buttonY = (super.screenHeight / 2) + 50;
                buttonY += 20;

                if ((super.mouseClickButton == 1) && (super.mouseClickX >= (buttonX - 75)) && (super.mouseClickX <= (buttonX + 75)) && (super.mouseClickY >= (buttonY - 20)) && (super.mouseClickY <= (buttonY + 20))) {
                    loginAttempts = 0;
                    login(username, password, false);

                    if (ingame) {
                        return;
                    }
                }

                buttonX = (super.screenWidth / 2) + 80;

                if ((super.mouseClickButton == 1) && (super.mouseClickX >= (buttonX - 75)) && (super.mouseClickX <= (buttonX + 75)) && (super.mouseClickY >= (buttonY - 20)) && (super.mouseClickY <= (buttonY + 20))) {
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
                if ((super.mouseClickButton == 1) && (super.mouseClickX >= (x - 75)) && (super.mouseClickX <= (x + 75)) && (super.mouseClickY >= (y - 20)) && (super.mouseClickY <= (y + 20))) {
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
                Obj type = Obj.get(otherID);
                if (type.block_entity) {
                    level_collisions[level].remove(x, z, otherRotation, otherKind, type.block_projectile);
                }
            }

            if (classID == 1) {
                scene.removeWallDecoration(level, x, z);
            }

            if (classID == 2) {
                scene.removeLoc(level, x, z);
                Obj type = Obj.get(otherID);

                if (((x + type.width) > 103) || ((z + type.width) > 103) || ((x + type.length) > 103) || ((z + type.length) > 103)) {
                    return;
                }

                if (type.block_entity) {
                    level_collisions[level].remove(otherRotation, type.width, x, z, type.length, type.block_projectile);
                }
            }

            if (classID == 3) {
                scene.removeGroundDecoration(level, x, z);
                Obj type = Obj.get(otherID);

                if (type.block_entity && type.interactable) {
                    level_collisions[level].removeSolid(x, z);
                }
            }
        }

        if (id >= 0) {
            int tile_level = level;

            // check for bridged tile
            if ((tile_level < 3) && ((levelTileFlags[1][x][z] & 2) == 2)) {
                tile_level++;
            }

            SceneBuilder.add_object(scene, angle, z, kind, tile_level, level_collisions[level], level_heightmap, x, id, level);
        }
    }

    public void readSyncPlayers() {
        entityRemovalCount = 0;
        entityUpdateCount = 0;

        readLocalPlayer();
        readPlayers();
        readNewPlayers();
        readPlayerUpdates();

        for (int i = 0; i < entityRemovalCount; i++) {
            int id = entityRemovalIDs[i];
            if (players[id].cycle != loopCycle) {
                players[id] = null;
            }
        }

        if (in.position != packetSize) {
            Signlink.reporterror("Error packet size mismatch in getplayer pos:" + in.position + " psize:" + packetSize);
            throw new RuntimeException("eek");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[playerIDs[i]] == null) {
                Signlink.reporterror(username + " null entry in pl list - pos:" + i + " size:" + playerCount);
                throw new RuntimeException("eek");
            }
        }

        awaitingSync = false;
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

            if (packetType == -1) {
                connection.read(in.data, 0, 1);
                packetType = in.data[0] & 0xff;
                if (randomIn != null) {
                    packetType = (packetType - randomIn.nextInt()) & 0xff;
                }
                packetSize = PacketIn.SIZE[packetType];
                bytesIn++;
                available--;
            }

            if (packetSize == -1) {
                if (available > 0) {
                    connection.read(in.data, 0, 1);
                    packetSize = in.data[0] & 0xff;
                    available--;
                } else {
                    return false;
                }
            }

            if (packetSize == -2) {
                if (available > 1) {
                    connection.read(in.data, 0, 2);
                    in.position = 0;
                    packetSize = in.readU16();
                    bytesIn += 2;
                    available -= 2;
                } else {
                    return false;
                }
            }

            if (available < packetSize) {
                return false;
            }

            in.position = 0;
            connection.read(in.data, 0, packetSize);

            idleNetCycles = 0;
            lastPacketType2 = lastPacketType1;
            lastPacketType1 = lastPacketType0;
            lastPacketType0 = packetType;
            bytesIn += packetSize;

            switch (packetType) {
                case PacketIn.SYNC_PLAYERS:
                    readSyncPlayers();
                    break;

                case PacketIn.LAST_LOGIN_INFO:
                    readLastLoginInfo();
                    break;

                case PacketIn.ZONE_CLEAR:
                    readZoneClear();
                    break;

                case PacketIn.IF_SETPLAYERHEAD:
                    readIfSetPlayerHead();
                    break;

                case PacketIn.CAM_RESET:
                    readCameraReset();
                    break;

                case PacketIn.INV_CLEAR:
                    readInventoryClear();
                    break;

                case PacketIn.IGNORE_LIST:
                    readIgnoreList();
                    break;

                case PacketIn.CAM_SETPOS:
                    readCameraSetPos();
                    break;

                case PacketIn.CAM_LOOKAT:
                    readCameraLookAt();
                    break;

                case PacketIn.UPDATE_STAT:
                    readUpdateStat();
                    break;

                case PacketIn.IF_TAB:
                    readIfTab();
                    break;

                case PacketIn.MIDI_SONG:
                    readMidiSong();
                    break;

                case PacketIn.MIDI_JINGLE:
                    readMidiJingle();
                    break;

                case PacketIn.LOGOUT:
                    logout();
                    break;

                case PacketIn.IF_SETPOSITION:
                    readIfSetPosition();
                    break;

                case PacketIn.REBUILD_REGION:
                case PacketIn.REBUILD_REGION_INSTANCE:
                    readRebuildRegion();
                    break;

                case PacketIn.IF_VIEWPORT_OVERLAY:
                    readIfViewportOverlay();
                    break;

                case PacketIn.MINIMAP_TOGGLE:
                    minimapState = in.readU8();
                    break;

                case PacketIn.IF_SETNPCHEAD:
                    readIfSetNPCHead();
                    break;

                case PacketIn.UPDATE_REBOOT_TIMER:
                    systemUpdateTimer = in.readU16LE() * 30;
                    in.readU16LE();
                    break;

                case PacketIn.ZONE_UPDATE:
                    readZoneUpdate();
                    break;

                case PacketIn.CAM_SHAKE:
                    readCameraShake();
                    break;

                case PacketIn.SYNTH_SOUND:
                    readSynthSound();
                    break;

                case PacketIn.SET_PLAYER_OP:
                    readSetPlayerOp();
                    break;

                case PacketIn.CLEAR_MAP_FLAG:
                    flagSceneTileX = 0;
                    break;

                case PacketIn.MESSAGE_GAME:
                    readMessageGame();
                    break;

                case PacketIn.RESET_ANIMS:
                    resetAnimations();
                    break;

                case PacketIn.FRIEND_STATUS:
                    readFriendStatus();
                    break;

                case PacketIn.UPDATE_RUNENERGY:
                    readUpdateRunEnergy();
                    break;

                case PacketIn.HINT_ARROW:
                    readHintArrow();
                    break;

                case PacketIn.IF_VIEWPORT_AND_SIDEBAR:
                    readIfViewportAndSidebar();
                    break;

                case PacketIn.IF_SETSCROLLPOS:
                    readIfSetScrollPos();
                    break;

                case PacketIn.RESET_CLIENT_VARCACHE:
                    restoreVarCache();
                    break;

                case PacketIn.MESSAGE_PUBLIC:
                    readMessagePublic();
                    break;

                case PacketIn.ZONE_BASE:
                    baseZ = in.readU8C();
                    baseX = in.readU8C();
                    break;

                case PacketIn.TAB_HINT:
                    readTabHint();
                    break;

                case PacketIn.IF_SETOBJECT:
                    readIfSetObject();
                    break;

                case PacketIn.IF_SETHIDE:
                    readIfSetHide();
                    break;

                case PacketIn.IF_STOPANIM:
                    readIfStopAnim();
                    break;

                case PacketIn.IF_SETTEXT:
                    readIfSetText();
                    break;

                case PacketIn.CHAT_FILTER_SETTINGS:
                    readChatFilterSettings();
                    break;

                case PacketIn.UPDATE_RUNWEIGHT:
                    readUpdateRunWeight();
                    break;

                case PacketIn.IF_SETMODEL:
                    readIfSetModel();
                    break;

                case PacketIn.IF_SETCOLOR:
                    readIfSetColor();
                    break;

                case PacketIn.UPDATE_INV_FULL:
                    readUpdateInvFull();
                    break;

                case PacketIn.IF_SETANGLE:
                    readIfSetAngle();
                    break;

                case PacketIn.FRIENDLIST_LOADED:
                    friendlistStatus = in.readU8();
                    redrawSidebar = true;
                    break;

                case PacketIn.LOCAL_PLAYER:
                    isMember = in.readU8();
                    localPID = in.readU16();
                    break;

                case PacketIn.SYNC_NPCS:
                    readSyncNPCs();
                    break;

                case PacketIn.INPUT_AMOUNT:
                    openChatInput(1);
                    break;

                case PacketIn.INPUT_NAME:
                    openChatInput(2);
                    break;

                case PacketIn.IF_VIEWPORT:
                    readViewportInterface();
                    break;

                case PacketIn.IF_CHAT_STICKY:
                    stickyChatInterfaceID = in.read16LEA();
                    redrawChatback = true;
                    break;

                case PacketIn.VARP_LARGE:
                    readVarpLarge();
                    break;

                case PacketIn.VARP_SMALL:
                    readVarpSmall();
                    break;

                case PacketIn.MULTIZONE:
                    multizone = in.readU8();
                    break;

                case PacketIn.IF_SETANIM:
                    readIfSetAnim();
                    break;

                case PacketIn.IF_CLOSE:
                    openViewportInterface(-1);
                    break;

                case PacketIn.UPDATE_INV_PARTIAL:
                    readUpdateInvPartial();
                    break;

                case PacketIn.TAB_SELECTED:
                    readTabSelected();
                    break;

                case PacketIn.IF_CHAT:
                    readIfChat();
                    break;

                case PacketIn.OBJ_ADD:
                case PacketIn.OBJ_REVEAL:
                case PacketIn.OBJ_COUNT:
                case PacketIn.OBJ_DEL:
                case PacketIn.LOC_ADD:
                case PacketIn.LOC_CHANGE:
                case PacketIn.LOC_DEL:
                case PacketIn.MAP_SOUND:
                case PacketIn.LOC_PLAYER:
                case PacketIn.MAP_ANIM:
                case PacketIn.MAP_PROJECTILE:
                    readZonePacket(packetType);
                    break;

                default:
                    Signlink.reporterror("T1 (Unhandled Packet Type) - " + packetType + "," + packetSize + " - " + lastPacketType1 + "," + lastPacketType2);
                    logout();
                    break;
            }

            packetType = -1;
        } catch (IOException e) {
            tryReconnect();
        } catch (Exception e) {
            StringBuilder s2 = new StringBuilder("T2 (Packet Error) - " + packetType + "," + lastPacketType1 + "," + lastPacketType2 + " - " + packetSize + "," + (sceneBaseTileX + localPlayer.pathTileX[0]) + "," + (sceneBaseTileZ + localPlayer.pathTileZ[0]) + " - ");
            for (int j15 = 0; (j15 < packetSize) && (j15 < 50); j15++) {
                s2.append(in.data[j15]).append(",");
            }
            Signlink.reporterror(s2.toString());
            logout();
            e.printStackTrace();
        }

        return true;
    }

    private void readLastLoginInfo() {
        daysSinceRecoveriesChanged = in.readU8();
        unreadMessages = in.readU16();
        warnMembersInNonMembers = in.readU8();
        in.readString(); // lastAddress
        daysSinceLastLogin = in.readU16();

        if ((lastAddress != 0) && (viewportInterfaceID == -1)) {
            Signlink.dnslookup(StringUtil.formatIPv4(lastAddress));
            closeInterfaces();

            int reportAbuseContentType = 650;

            if ((daysSinceRecoveriesChanged != 201) || (warnMembersInNonMembers == 1)) {
                reportAbuseContentType = 655;
            }

            reportAbuseInput = "";
            reportAbuseMuteOption = false;

            for (int i = 0; i < Iface.instances.length; i++) {
                if ((Iface.instances[i] == null) || (Iface.instances[i].contentType != reportAbuseContentType)) {
                    continue;
                }
                viewportInterfaceID = Iface.instances[i].parentID;
                break;
            }
        }
    }

    private void readZoneClear() {
        baseX = in.readU8C();
        baseZ = in.readU8S();
        for (int x = baseX; x < (baseX + 8); x++) {
            for (int z = baseZ; z < (baseZ + 8); z++) {
                if (levelObjStacks[currentLevel][x][z] != null) {
                    levelObjStacks[currentLevel][x][z] = null;
                    sortObjStacks(x, z);
                }
            }
        }
        for (SceneTemporaryObject loc = (SceneTemporaryObject) temporaryLocs.peekFront(); loc != null; loc = (SceneTemporaryObject) temporaryLocs.prev()) {
            if ((loc.localX >= baseX) && (loc.localX < (baseX + 8)) && (loc.localZ >= baseZ) && (loc.localZ < (baseZ + 8)) && (loc.level == currentLevel)) {
                loc.duration = 0;
            }
        }
    }

    private void readIfSetPlayerHead() {
        int interfaceID = in.readU16LEA();
        Iface iface = Iface.instances[interfaceID];
        iface.modelType = Iface.MODEL_TYPE_PLAYER;

        if (localPlayer.transmogrify == null) {
            iface.modelID = (localPlayer.colors[0] << 25) + (localPlayer.colors[4] << 20) + (localPlayer.appearances[0] << 15) + (localPlayer.appearances[8] << 10) + (localPlayer.appearances[11] << 5) + localPlayer.appearances[1];
        } else {
            iface.modelID = (int) (0x12345678L + localPlayer.transmogrify.id);
        }
    }

    private void readCameraReset() {
        cutscene = false;
        for (int l = 0; l < 5; l++) {
            cameraModifierEnabled[l] = false;
        }
    }

    private void readInventoryClear() {
        int interfaceID = in.readU16LE();
        Iface iface = Iface.instances[interfaceID];
        for (int slot = 0; slot < iface.inventorySlotObjID.length; slot++) {
            iface.inventorySlotObjID[slot] = -1;
            iface.inventorySlotObjID[slot] = 0;
        }
    }

    private void readIgnoreList() {
        ignoreCount = packetSize / 8;
        for (int j1 = 0; j1 < ignoreCount; j1++) {
            ignoreName37[j1] = in.read64();
        }
    }

    private void readCameraSetPos() {
        cutscene = true;
        cutsceneSrcLocalTileX = in.readU8();
        cutsceneSrcLocalTileZ = in.readU8();
        cutsceneSrcHeight = in.readU16();
        cutsceneMoveSpeed = in.readU8();
        cutsceneMoveAcceleration = in.readU8();

        if (cutsceneMoveAcceleration >= 100) {
            cameraX = (cutsceneSrcLocalTileX * 128) + 64;
            cameraZ = (cutsceneSrcLocalTileZ * 128) + 64;
            cameraY = getHeightmapY(currentLevel, cameraX, cameraZ) - cutsceneSrcHeight;
        }
    }

    private void readCameraLookAt() {
        cutscene = true;
        cutsceneDstLocalTileX = in.readU8();
        cutsceneDstLocalTileZ = in.readU8();
        cutsceneDstHeight = in.readU16();
        cutsceneRotateSpeed = in.readU8();
        cutsceneRotateAcceleration = in.readU8();

        if (cutsceneRotateAcceleration >= 100) {
            int sceneX = (cutsceneDstLocalTileX * 128) + 64;
            int sceneZ = (cutsceneDstLocalTileZ * 128) + 64;
            int sceneY = getHeightmapY(currentLevel, sceneX, sceneZ) - cutsceneDstHeight;
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
    }

    private void readUpdateStat() {
        redrawSidebar = true;
        int skill = in.readU8();
        int experience = in.read32();
        int level = in.readU8();
        in.read8();
        skillExperience[skill] = experience;
        skillLevel[skill] = level;
        skillBaseLevel[skill] = 1;
        for (int i = 0; i < 98; i++) {
            if (experience >= levelExperience[i]) {
                skillBaseLevel[skill] = i + 2;
            }
        }
    }

    private void readIfTab() {
        int interfaceID = in.readU16();
        int tab = in.readU8A();
        if (interfaceID == 65535) {
            interfaceID = -1;
        }
        tabInterfaceID[tab] = interfaceID;
        redrawSidebar = true;
        redrawSideicons = true;
    }

    private void readMidiSong() {
        int next = in.readU16LE();
        in.read8();
        if (next == 65535) {
            next = -1;
        }
        if ((next != nextSong) && midiEnabled && (nextSongDelay == 0)) {
            song = next;
            songFading = true;
            ondemand.request(2, song);
        }
        nextSong = next;
    }

    private void readMidiJingle() {
        int next = in.readU16LEA();
        int delay = in.readU16A();
        if (midiEnabled) {
            song = next;
            songFading = false;
            ondemand.request(2, song);
            nextSongDelay = delay;
        }
    }

    private void readIfSetPosition() {
        int x = in.read16();
        int y = in.read16LE();
        int interfaceID = in.readU16LE();
        Iface iface = Iface.instances[interfaceID];
        iface.x = x;
        iface.y = y;
    }

    private void readRebuildRegion() {
        int zoneX = sceneCenterZoneX;
        int zoneZ = sceneCenterZoneZ;

        if (packetType == PacketIn.REBUILD_REGION) {
            zoneX = in.readU16A();
            zoneZ = in.readU16();
            sceneInstanced = false;
        }

        if (packetType == PacketIn.REBUILD_REGION_INSTANCE) {
            zoneZ = in.readU16A();
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
            zoneX = in.readU16();
            sceneInstanced = true;
        }

        if ((sceneCenterZoneX == zoneX) && (sceneCenterZoneZ == zoneZ) && (sceneState == 2)) {
            packetType = -1;
            return;
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

        if (packetType == PacketIn.REBUILD_REGION) {
            fetchMaps();
        }

        if (packetType == PacketIn.REBUILD_REGION_INSTANCE) {
            fetchMapsInstanced();
        }

        shiftScene();

        cutscene = false;
    }

    private void fetchMaps() {
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

    private void fetchMapsInstanced() {
        int mapCount = 0;
        int[] mapIDs = new int[676];

        for (int level = 0; level < 4; level++) {
            for (int x = 0; x < 13; x++) {
                for (int z = 0; z < 13; z++) {
                    int bitset = levelChunkBitset[level][x][z];

                    if (bitset != -1) {
                        int mapX = (bitset >> 14) & 0x3ff;
                        int mapZ = (bitset >> 3) & 0x7ff;
                        int mapID = ((mapX / 8) << 8) + (mapZ / 8);

                        for (int i = 0; i < mapCount; i++) {
                            if (mapIDs[i] != mapID) {
                                continue;
                            }
                            mapID = -1;
                            break;
                        }

                        if (mapID != -1) {
                            mapIDs[mapCount++] = mapID;
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
            int mapIndex = sceneMapIndex[i] = mapIDs[i];
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

    private void shiftScene() {
        int dtx = sceneBaseTileX - scenePrevBaseTileX;
        int dtz = sceneBaseTileZ - scenePrevBaseTileZ;
        scenePrevBaseTileX = sceneBaseTileX;
        scenePrevBaseTileZ = sceneBaseTileZ;

        for (int i = 0; i < 16384; i++) {
            SceneNPC npc = npcs[i];

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
            ScenePlayer player = players[i];

            if (player != null) {
                for (int i31 = 0; i31 < 10; i31++) {
                    player.pathTileX[i31] -= dtx;
                    player.pathTileZ[i31] -= dtz;
                }
                player.x -= dtx * 128;
                player.z -= dtz * 128;
            }
        }

        awaitingSync = true;

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

        for (SceneTemporaryObject loc = (SceneTemporaryObject) temporaryLocs.peekFront(); loc != null; loc = (SceneTemporaryObject) temporaryLocs.prev()) {
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
    }

    private void readIfViewportOverlay() {
        int interfaceID = in.read16LE();
        if (interfaceID >= 0) {
            resetInterfaceAnimation(interfaceID);
        }
        viewportOverlayInterfaceID = interfaceID;
    }

    private void readIfSetNPCHead() {
        int npcID = in.readU16();
        int interfaceID = in.readU16();
        System.out.println("extra data: " + in.readU16());
        Iface.instances[interfaceID].modelType = Iface.MODEL_TYPE_NPC;
        Iface.instances[interfaceID].modelID = npcID;
    }

    private void readZoneUpdate() {
        baseZ = in.readU8();
        baseX = in.readU8C();
        while (in.position < packetSize) {
            readZonePacket(in.readU8());
        }
    }

    private void readCameraShake() {
        int type = in.readU8();
        int jitterScale = in.readU8();
        int wobbleScale = in.readU8();
        int wobbleSpeed = in.readU8();
        cameraModifierEnabled[type] = true;
        cameraModifierJitter[type] = jitterScale;
        cameraModifierWobbleScale[type] = wobbleScale;
        cameraModifierWobbleSpeed[type] = wobbleSpeed;
        cameraModifierCycle[type] = 0;
    }

    private void readSynthSound() {
        int waveID = in.readU16();
        int loopCount = in.readU8();
        int delay = in.readU16();
        if (waveEnabled && (waveCount < 50)) {
            waveIDs[waveCount] = waveID;
            waveLoops[waveCount] = loopCount;
            waveDelay[waveCount] = delay + SoundTrack.delays[waveID];
            waveCount++;
        }
    }

    private void readSetPlayerOp() {
        int option = in.readU8C();
        int priority = in.readU8A();
        String text = in.readString();
        if ((option >= 1) && (option <= 5)) {
            if (text.equalsIgnoreCase("null")) {
                text = null;
            }
            playerOptions[option - 1] = text;
            playerOptionPushDown[option - 1] = priority == 0;
        }
    }

    private void readMessageGame() {
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
    }

    private void resetAnimations() {
        for (ScenePlayer player : players) {
            if (player != null) {
                player.primarySeqID = -1;
            }
        }
        for (SceneNPC npc : npcs) {
            if (npc != null) {
                npc.primarySeqID = -1;
            }
        }
    }

    private void readFriendStatus() {
        long name37 = in.read64();
        int world = in.readU8();
        String name = StringUtil.formatName(StringUtil.fromBase37(name37));

        for (int i = 0; i < friendCount; i++) {
            if (name37 != friendName37[i]) {
                continue;
            }
            if (friendWorld[i] != world) {
                friendWorld[i] = world;
                redrawSidebar = true;
                if (world > 0) {
                    addMessage(5, "", name + " has logged in.");
                }
                if (world == 0) {
                    addMessage(5, "", name + " has logged out.");
                }
            }
            name = null;
            break;
        }

        if ((name != null) && (friendCount < 200)) {
            friendName37[friendCount] = name37;
            friendName[friendCount] = name;
            friendWorld[friendCount] = world;
            friendCount++;
            redrawSidebar = true;
        }

        for (boolean sorted = false; !sorted; ) {
            sorted = true;
            for (int i = 0; i < (friendCount - 1); i++) {
                if (((friendWorld[i] != nodeID) && (friendWorld[i + 1] == nodeID)) || ((friendWorld[i] == 0) && (friendWorld[i + 1] != 0))) {
                    int tmp0 = friendWorld[i];
                    friendWorld[i] = friendWorld[i + 1];
                    friendWorld[i + 1] = tmp0;

                    String tmp1 = friendName[i];
                    friendName[i] = friendName[i + 1];
                    friendName[i + 1] = tmp1;

                    long tmp2 = friendName37[i];
                    friendName37[i] = friendName37[i + 1];
                    friendName37[i + 1] = tmp2;
                    redrawSidebar = true;
                    sorted = false;
                }
            }
        }
    }

    private void readUpdateRunEnergy() {
        if (selectedTab == 12) {
            redrawSidebar = true;
        }
        energy = in.readU8();
    }

    private void readHintArrow() {
        hintType = in.readU8();

        if (hintType == 1) {
            hintNPC = in.readU16();
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
            hintTileX = in.readU16();
            hintTileZ = in.readU16();
            hintHeight = in.readU8();
        }

        if (hintType == 10) {
            hintPlayer = in.readU16();
        }
    }

    private void readIfViewportAndSidebar() {
        int viewportInterfaceID = in.readU16A();
        int sidebarInterfaceID = in.readU16();

        if (chatInterfaceID != -1) {
            chatInterfaceID = -1;
            redrawChatback = true;
        }

        if (chatbackInputType != 0) {
            chatbackInputType = 0;
            redrawChatback = true;
        }

        this.viewportInterfaceID = viewportInterfaceID;
        this.sidebarInterfaceID = sidebarInterfaceID;
        redrawSidebar = true;
        redrawSideicons = true;
        pressedContinueOption = false;
    }

    private void readIfSetScrollPos() {
        int interfaceID = in.readU16LE();
        int scrollPos = in.readU16A();
        Iface iface = Iface.instances[interfaceID];
        if ((iface != null) && (iface.type == Iface.TYPE_PARENT)) {
            if (scrollPos < 0) {
                scrollPos = 0;
            }
            if (scrollPos > (iface.scrollableHeight - iface.height)) {
                scrollPos = iface.scrollableHeight - iface.height;
            }
            iface.scrollPosition = scrollPos;
        }
    }

    private void restoreVarCache() {
        for (int id = 0; id < varps.length; id++) {
            if (varps[id] != varCache[id]) {
                varps[id] = varCache[id];
                updateVarp(id);
                redrawSidebar = true;
            }
        }
    }

    private void readMessagePublic() {
        long name37 = in.read64();
        int messageID = in.read32();
        int role = in.readU8();
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
                String message = in.readString();

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
    }

    private void readTabHint() {
        flashingTab = in.readU8S();
        if (flashingTab == selectedTab) {
            if (flashingTab == 3) {
                selectedTab = 1;
            } else {
                selectedTab = 3;
            }
            redrawSidebar = true;
        }
    }

    private void readIfSetObject() {
        int interfaceID = in.readU16LE();
        int zoom = in.readU16();
        int objID = in.readU16();

        if (objID != 65535) {
            Item type = Item.get(objID);
            Iface iface = Iface.instances[interfaceID];
            iface.modelType = Iface.MODEL_TYPE_OBJ;
            iface.modelID = objID;
            iface.modelPitch = type.iconPitch;
            iface.modelYaw = type.iconYaw;
            iface.modelZoom = (type.iconDistance * 100) / zoom;
        } else {
            Iface.instances[interfaceID].modelType = Iface.MODEL_TYPE_NONE;
        }
    }

    private void readIfSetHide() {
        boolean hide = in.readU8() == 1;
        int interfaceID = in.readU16();
        Iface.instances[interfaceID].hide = hide;
    }

    private void readIfStopAnim() {
        int interfaceID = in.readU16LE();
        resetInterfaceAnimation(interfaceID);
        if (chatInterfaceID != -1) {
            chatInterfaceID = -1;
            redrawChatback = true;
        }
        if (chatbackInputType != 0) {
            chatbackInputType = 0;
            redrawChatback = true;
        }
        sidebarInterfaceID = interfaceID;
        redrawSidebar = true;
        redrawSideicons = true;
        viewportInterfaceID = -1;
        pressedContinueOption = false;
    }

    private void readIfSetText() {
        String text = in.readString();
        int interfaceID = in.readU16();
        if ((interfaceID >= 0) && (interfaceID < Iface.instances.length)) {
            Iface iface = Iface.instances[interfaceID];
            if (iface != null) {
                iface.text = text;
                if (iface.parentID == tabInterfaceID[selectedTab]) {
                    redrawSidebar = true;
                }
            }
        }
    }

    private void readChatFilterSettings() {
        publicChatSetting = in.readU8();
        privateChatSetting = in.readU8();
        tradeChatSetting = in.readU8();
        redrawPrivacySettings = true;
        redrawChatback = true;
    }

    private void readUpdateRunWeight() {
        if (selectedTab == 12) {
            redrawSidebar = true;
        }
        weightCarried = in.read16();
    }

    private void readIfSetModel() {
        int interfaceID = in.readU16LEA();
        int modelID = in.readU16();
        Iface.instances[interfaceID].modelType = Iface.MODEL_TYPE_NORMAL;
        Iface.instances[interfaceID].modelID = modelID;
    }

    private void readIfSetColor() {
        int interfaceID = in.readU16LEA();
        int rgb555 = in.readU16LEA();
        int r = (rgb555 >> 10) & 0x1f;
        int g = (rgb555 >> 5) & 0x1f;
        int b = rgb555 & 0x1f;
        Iface.instances[interfaceID].color = (r << 19) + (g << 11) + (b << 3);
    }

    private void readUpdateInvFull() {
        redrawSidebar = true;
        int interfaceID = in.readU16();
        Iface iface = Iface.instances[interfaceID];
        int lastSlot = in.readU16();

        for (int slot = 0; slot < lastSlot; slot++) {
            int objCount = in.readU8();

            if (objCount == 255) {
                objCount = in.read32ME();
            }

            if (slot >= iface.inventorySlotObjID.length) {
                in.readU16LEA();
            } else {
                iface.inventorySlotObjID[slot] = in.readU16LEA();
                iface.inventorySlotObjCount[slot] = objCount;
            }
        }

        // clear remaining slots
        for (int slot = lastSlot; slot < iface.inventorySlotObjID.length; slot++) {
            iface.inventorySlotObjID[slot] = 0;
            iface.inventorySlotObjCount[slot] = 0;
        }
    }

    private void readIfSetAngle() {
        int zoom = in.readU16A();
        int interfaceID = in.readU16();
        int pitch = in.readU16();
        int yaw = in.readU16LEA();
        Iface.instances[interfaceID].modelPitch = pitch;
        Iface.instances[interfaceID].modelYaw = yaw;
        Iface.instances[interfaceID].modelZoom = zoom;
    }

    private void openChatInput(int type) {
        showSocialInput = false;
        chatbackInputType = type;
        chatbackInput = "";
        redrawChatback = true;
    }

    private void openViewportInterface(int interfaceID) {
        if (sidebarInterfaceID != -1) {
            sidebarInterfaceID = -1;
            redrawSidebar = true;
            redrawSideicons = true;
        }
        if (chatInterfaceID != -1) {
            chatInterfaceID = -1;
            redrawChatback = true;
        }
        if (chatbackInputType != 0) {
            chatbackInputType = 0;
            redrawChatback = true;
        }
        viewportInterfaceID = interfaceID;
        pressedContinueOption = false;
    }

    private void readViewportInterface() {
        int interfaceID = in.readU16();
        resetInterfaceAnimation(interfaceID);
        openViewportInterface(interfaceID);
    }

    private void readVarpLarge() {
        int varpID = in.readU16();
        int value = in.read32();
        varCache[varpID] = value;

        if (varps[varpID] != value) {
            varps[varpID] = value;
            updateVarp(varpID);
            redrawSidebar = true;
            if (stickyChatInterfaceID != -1) {
                redrawChatback = true;
            }
        }
    }

    private void readVarpSmall() {
        int varpID = in.readU16();
        byte value = in.read8();
        varCache[varpID] = value;

        if (varps[varpID] != value) {
            varps[varpID] = value;
            updateVarp(varpID);
            redrawSidebar = true;
            if (stickyChatInterfaceID != -1) {
                redrawChatback = true;
            }
        }
    }

    private void readIfSetAnim() {
        int interfaceID = in.readU16();
        int seqID = in.read16();
        Iface iface = Iface.instances[interfaceID];
        iface.seqID = seqID;
        if (seqID == -1) {
            iface.seqFrame = 0;
            iface.seqCycle = 0;
        }
    }

    private void readUpdateInvPartial() {
        redrawSidebar = true;
        int interfaceID = in.readU16();
        Iface iface = Iface.instances[interfaceID];

        while (in.position < packetSize) {
            int slot = in.readUSmart();
            int objID = in.readU16();
            int objCount = in.readU8();
            if (objCount == 255) {
                objCount = in.read32();
            }
            if ((slot >= 0) && (slot < iface.inventorySlotObjID.length)) {
                iface.inventorySlotObjID[slot] = objID;
                iface.inventorySlotObjCount[slot] = objCount;
            }
        }
    }

    private void readTabSelected() {
        selectedTab = in.readU8C();
        redrawSidebar = true;
        redrawSideicons = true;
    }

    private void readIfChat() {
        int interfaceID = in.readU16LE();
        resetInterfaceAnimation(interfaceID);
        if (sidebarInterfaceID != -1) {
            sidebarInterfaceID = -1;
            redrawSidebar = true;
            redrawSideicons = true;
        }
        chatInterfaceID = interfaceID;
        redrawChatback = true;
        viewportInterfaceID = -1;
        pressedContinueOption = false;
    }

    public void drawScene() {
        sceneCycle++;
        pushPlayers(true);
        pushNPCs(true);
        pushPlayers(false);
        pushNPCs(false);
        pushProjectiles();
        pushSpotanims();

        if (!cutscene) {
            int pitch = orbitCameraPitch;

            if ((cameraPitchClamp / 256) > pitch) {
                pitch = cameraPitchClamp / 256;
            }

            if (cameraModifierEnabled[4] && ((cameraModifierWobbleScale[4] + 128) > pitch)) {
                pitch = cameraModifierWobbleScale[4] + 128;
            }

            int yaw = (orbitCameraYaw + cameraAnticheatAngle) & 0x7ff;
            orbitCamera(600 + (pitch * 3), pitch, orbitCameraX, getHeightmapY(currentLevel, localPlayer.x, localPlayer.z) - 50, yaw, orbitCameraZ);
        }

        int topLevel;

        if (cutscene) {
            topLevel = getTopLevelCutscene();
        } else {
            topLevel = getTopLevel();
        }

        int cameraX = this.cameraX;
        int cameraY = this.cameraY;
        int cameraZ = this.cameraZ;
        int cameraPitch = this.cameraPitch;
        int cameraYaw = this.cameraYaw;

        applyCameraAdjustments();

        int cycle = Draw3D.cycle;
        Model.pick = true;
        Model.pickedCount = 0;
        Model.mouseX = super.mouseX - 4;
        Model.mouseY = super.mouseY - 4;
        Draw2D.clear();

        scene.draw(this.cameraX, this.cameraZ, this.cameraYaw, this.cameraY, topLevel, this.cameraPitch);
        scene.clearTemporaryLocs();

        draw2DEntityElements();
        drawChats();
        drawTileHint();
        updateTextures(cycle);

        drawPrivateMessages();
        drawMouseCrosses();
        drawViewportInterfaces();

        updateChatOverride();

        if (!menuVisible) {
            handleInput();
            drawTooltip();
        } else if (menuArea == 0) {
            drawMenu();
        }

        drawMultizone();
        drawDebug();
        drawSystemUpdateTimer();

        areaViewport.draw(super.graphics, 4, 4);

        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.cameraPitch = cameraPitch;
        this.cameraYaw = cameraYaw;
    }

    private void applyCameraAdjustments() {
        for (int type = 0; type < 5; type++) {
            if (!cameraModifierEnabled[type]) {
                continue;
            }

            int adjustment = (int) (((Math.random() * (double) ((cameraModifierJitter[type] * 2) + 1)) - (double) cameraModifierJitter[type]) + (Math.sin((double) cameraModifierCycle[type] * ((double) cameraModifierWobbleSpeed[type] / 100D)) * (double) cameraModifierWobbleScale[type]));

            switch (type) {
                case 0:
                    this.cameraX += adjustment;
                    break;
                case 1:
                    this.cameraY += adjustment;
                    break;
                case 2:
                    this.cameraZ += adjustment;
                    break;
                case 3:
                    this.cameraYaw = (this.cameraYaw + adjustment) & 0x7ff;
                    break;
                case 4:
                    this.cameraPitch += adjustment;
                    if (this.cameraPitch < 128) {
                        this.cameraPitch = 128;
                    }
                    if (this.cameraPitch > 383) {
                        this.cameraPitch = 383;
                    }
                    break;
            }
        }
    }

    public void closeInterfaces() {
        out.writeOp(130);
        if (sidebarInterfaceID != -1) {
            sidebarInterfaceID = -1;
            redrawSidebar = true;
            pressedContinueOption = false;
            redrawSideicons = true;
        }
        if (chatInterfaceID != -1) {
            chatInterfaceID = -1;
            redrawChatback = true;
            pressedContinueOption = false;
        }
        viewportInterfaceID = -1;
    }

}
