package de.relluem94.vulcan.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import javax.swing.AbstractButton;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.openal.Audio;

import de.relluem94.vulcan.entities.BaseEntity;
import de.relluem94.vulcan.entities.components.CameraComponent;
import de.relluem94.vulcan.entities.components.CollisionComponent;
import de.relluem94.vulcan.entities.components.ControlComponent;
import de.relluem94.vulcan.gui.ClickListener;
import de.relluem94.vulcan.gui.GuiElement;
import de.relluem94.vulcan.gui.GuiRenderer;
import de.relluem94.vulcan.gui.GuiTexture;
import de.relluem94.vulcan.gui.font.FontType;
import de.relluem94.vulcan.gui.font.GUIText;
import de.relluem94.vulcan.models.ModelTexture;
import de.relluem94.vulcan.models.RawModel;
import de.relluem94.vulcan.models.TerrainTexture;
import de.relluem94.vulcan.models.TerrainTexturePack;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.particles.ParticleTexture;
import de.relluem94.vulcan.renderEngine.Fbo;
import de.relluem94.vulcan.renderEngine.MasterRenderer;
import de.relluem94.vulcan.renderEngine.loader.Loader;
import de.relluem94.vulcan.toolbox.MousePicker;
import de.relluem94.vulcan.toolbox.SplashScreen;
import de.relluem94.vulcan.toolbox.TerrainLoader;
import de.relluem94.vulcan.toolbox.Variables;
import de.relluem94.vulcan.toolbox.generators.FixedSizeList;
import de.relluem94.vulcan.toolbox.generators.ScreenShot;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.generators.TextureGen;
import de.relluem94.vulcan.toolbox.maths.Color3i;
import de.relluem94.vulcan.toolbox.maths.Vector3f;
import de.relluem94.vulcan.world.World;

public class Things extends Variables {

    public static List<FixedSizeList<Store>> stores = new ArrayList<>();
    public static List<FixedSizeList<Store>> terrain_stores = new ArrayList<>();

    public static List<Integer> entities = new ArrayList<>();
    public static List<Integer> terrains = new ArrayList<>();
    public static List<Integer> physics = new ArrayList<>();
    public static List<Integer> normalMapEntities = new ArrayList<>();
    public static List<Integer> lights = new ArrayList<>();

    public static List<RawModel> models = new ArrayList<>();
    public static List<ModelTexture> textures = new ArrayList<>();
    public static HashMap<TexturedModel, List<Integer>> texmodels = new HashMap<>();

    public static Audio gui_audio;
    public static float gui_audio_pitch = 1.0f;
    public static float gui_audio_volume = 1.0f;

    public static Audio player_audio;
    public static float player_audio_pitch = 1.0f;
    public static float player_audio_volume = 0.7f;

    public static Audio music_audio;
    public static float music_audio_pitch = 1.0f;
    public static float music_audio_volume = 0.5f;

    public static List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
    public static List<GuiElement> guiElements = new ArrayList<GuiElement>();
    public static List<GUIText> guiText;

    public static List<String> options;

    public static String[] tester = {"Relluem94, ", "Stellachen, ", "Windbeutel57"};

    public static List<String> default_options = Arrays.asList(
            "version:" + version,
            "",
            "WIDTH:" + 1600, "HEIGHT:" + 900, "FPS_CAP:" + 120, "ANTIALASING_FILTERING:8", "mipmapping:true", "vsync:false",
            "",
            "WORLDSIZE:32", "WORLDOFFSET:4", "SEELEVEL:0",
            "",
            "particle:true", "debug:true", "SCREENSHOT_FORMAT:PNG"
    //				"", "", "", "", "", ""
    );

    // Kann alles eingestellt werden bis 
    /*  */ public static boolean mipmapping;
    /*  */ public static boolean debug;
    /*  */ public static boolean particle;
    /*  */ public static boolean vsync;
    /*  */ public static int ANTIALASING_FILTERING;
    /*  */ public static int WIDTH;
    /*  */ public static int HEIGHT;
    /*  */ public static int FPS_CAP;
    /*  */ public static int SEELEVEL;
    /*  */ public static int WORLDSIZE;
    /*  */ public static int WORLDOFFSET;
    /*  */ public static boolean fullscreen = false; // upcomming
    /*  */ public static String screenshot_format;
    // hier hin

    public static DisplayMode display_normal;
    public static DisplayMode display_full;
    public static float ANISOTROPIC_FILTERING;
    public static int perspective = 0;
    public static Vector3f spawnpoint;
    public static final String STARTTERRAIN = "Terrain_400"; //66
    public static final int SEED = new Random().nextInt(1000000000); // zb 530211011
    public static Random SEED2 = new Random();
    public static World world;

    public static boolean closeRequest = false;
    public static boolean gameIsLoaded = false;

    public static GUIText vers;
    public static GUIText fps;
    public static GUIText pos;
    public static GUIText play;

    public static GUIText player_health; //TODO Player hud mit % anzeige je nach Prozent die farbe �ndern..
    public static GUIText player_level;

    public static GUIText title_text;

    public static boolean control = true;
    public static boolean main_menu;
    public static boolean setup_menu = true;
    public static boolean credit_menu = true;
    public static boolean isChatOpen = false;
    public static boolean saves_menu = true;
    public static boolean ui_menu = true;
    public static int buttoncooldown = -1;
    public static GuiElement start;
    public static GuiElement exit;
    public static GuiElement setup;
    public static GuiElement setup_gui;
    public static GuiElement setup_gui_ok;
    public static GuiElement saves;
    public static GuiElement saves_gui;
    public static GuiElement saves_gui_ok;
    public static GuiElement credit;
    public static GuiElement credit_gui;
    public static GuiElement credit_gui_ok;
    public static GuiElement logo;
    public static GuiElement ui_gui;
    public static GuiElement ui_gui_ok;
    public static GuiElement chat;

    public static AbstractButton button;
    public static FontType font;
    public static TexturedModel playerModel;
    public static TexturedModel obsidian;
    public static TexturedModel stone;
    public static TexturedModel lamp;
    public static TexturedModel tempModel;
    public static TerrainTexture backgroundTexture;
    public static TerrainTexture rTexture;
    public static TerrainTexture gTexture;
    public static TerrainTexture bTexture;
    public static TerrainTexturePack texturePack;

    public static BaseEntity player = new BaseEntity("Player");
    public static CollisionComponent colc = new CollisionComponent(player.getID());
    public static ControlComponent conc = new ControlComponent(player.getID());
    public static BaseEntity camera = new BaseEntity("Camera");
    public static CameraComponent camc = new CameraComponent(camera.getID());
    public static BaseEntity sun = new BaseEntity("Sun");
    public static BaseEntity lampe_red = new BaseEntity("Lampe Rot");
    public static BaseEntity lampe_green = new BaseEntity("Lampe Gr�n");
    public static BaseEntity lampe_blue = new BaseEntity("Lampe Blau");

    public static BaseEntity tempentity = new BaseEntity("TempEntity");

    public static ParticleTexture fireParticle;
    public static ClickListener clistner = new ClickListener();

    public static MousePicker picker;

    public static Loader loader;
    public static TerrainLoader terrainloader;
    public static MasterRenderer renderer;
    public static GuiRenderer guiRenderer;
    public static Fbo fbo_a;
    public static Fbo fbo_b;
    public static SplashScreen sc;
    //public static Console console;

    public static long lastFrameTime;
    public static float delta;

    public static long lastFrame;
    public static int fps_count = 0;

    public static int fpscount;
    public static long lastFPS;

    public static Random random = new Random();

    public static ExecutorService executorService;
    public static ScreenShot screenshot = new ScreenShot();

    public static TextureGen tgen = new TextureGen();
    public static BufferedImage iconX16 = tgen.generateStripesImage(Color3i.RELLU_RED, Color3i.RELLU_GRAY, 16);
    public static BufferedImage iconX32 = tgen.generateStripesImage(Color3i.RELLU_RED, Color3i.RELLU_GRAY, 32);
    public static BufferedImage iconX64 = tgen.generateStripesImage(Color3i.RELLU_RED, Color3i.RELLU_GRAY, 64);
}
