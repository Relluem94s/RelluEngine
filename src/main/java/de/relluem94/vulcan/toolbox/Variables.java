package de.relluem94.vulcan.toolbox;

import java.io.File;

import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessing;
import de.relluem94.vulcan.toolbox.maths.Color3f;

public class Variables {

    public static final String state = "ALPHA";
    public static final String version = "0.0.49";
    public static final String title = "Projekt Vulcan";
    public static final String trenn = ":_:";
    public static final String trennstrich = "---------------------------------------------------------------------------------------------------";

    public static final String Pfad = System.getProperty("user.home") + "/RelluEngine/";
    public static final File ScreenshotDir = new File(Pfad + "screenshots/");
    public static final File MusicDir = new File(Pfad + "music/");
    public static final File SavesDir = new File(Pfad + "saves/");
    public static final File OptionsFile = new File(Pfad, "options.txt");

    // Shader Vars Start
    private static final String SHADER_LOC = "assets/shaders";
    private static final String Post_LOC = "/postProcessing/";
    private static final String Vertex_LOC = "/Vertex.stoy";
    private static final String Fragment_LOC = "/Fragment.stoy";

    public static final String PostProcessing_Normal_VERTEX_FILE = SHADER_LOC + Post_LOC + "Normal" + Vertex_LOC;
    public static final String PostProcessing_Normal_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Normal" + Fragment_LOC;

    public static final String PostProcessing_Contrast_VERTEX_FILE = SHADER_LOC + Post_LOC + "Contrast" + Vertex_LOC;
    public static final String PostProcessing_Contrast_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Contrast" + Fragment_LOC;

    public static final String PostProcessing_Vignette_VERTEX_FILE = SHADER_LOC + Post_LOC + "Vignette" + Vertex_LOC;
    public static final String PostProcessing_Vignette_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Vignette" + Fragment_LOC;

    public static final String PostProcessing_Color_VERTEX_FILE = SHADER_LOC + Post_LOC + "Color" + Vertex_LOC;
    public static final String PostProcessing_Color_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Color" + Fragment_LOC;

    public static final String PostProcessing_Underwater_VERTEX_FILE = SHADER_LOC + Post_LOC + "Underwater" + Vertex_LOC;
    public static final String PostProcessing_Underwater_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Underwater" + Fragment_LOC;

    public static final String PostProcessing_Greyscale_VERTEX_FILE = SHADER_LOC + Post_LOC + "Greyscale" + Vertex_LOC;
    public static final String PostProcessing_Greyscale_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Greyscale" + Fragment_LOC;

    public static final String PostProcessing_Sepia_VERTEX_FILE = SHADER_LOC + Post_LOC + "Sepia" + Vertex_LOC;
    public static final String PostProcessing_Sepia_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Sepia" + Fragment_LOC;

    public static final String PostProcessing_Blur_VERTEX_FILE = SHADER_LOC + Post_LOC + "Blur" + Vertex_LOC;
    public static final String PostProcessing_Blur_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "Blur" + Fragment_LOC;

    public static final String PostProcessing_GaussianBlur_Horizontal_VERTEX_FILE = SHADER_LOC + Post_LOC + "GaussianBlur/h_Vertex.stoy";
    public static final String PostProcessing_GaussianBlur_Vertical_VERTEX_FILE = SHADER_LOC + Post_LOC + "GaussianBlur/v_Vertex.stoy";
    public static final String PostProcessing_GaussianBlur_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "GaussianBlur" + Fragment_LOC;

    public static final String PostProcessing_DOF_VERTEX_FILE = SHADER_LOC + Post_LOC + "DOF" + Vertex_LOC;
    public static final String PostProcessing_DOF_FRAGMENT_FILE = SHADER_LOC + Post_LOC + "DOF" + Fragment_LOC;

    // Normale Shader
    public static final String LiquidShader_VERTEX_FILE = SHADER_LOC + "/liquid" + Vertex_LOC;
    public static final String LiquidShader_FRAGMENT_FILE = SHADER_LOC + "/liquid" + Fragment_LOC;

    public static final String NormalMappingShader_VERTEX_FILE = SHADER_LOC + "/normal" + Vertex_LOC;
    public static final String NormalMappingShader_FRAGMENT_FILE = SHADER_LOC + "/normal" + Fragment_LOC;

    public static final String ShadowShader_VERTEX_FILE = SHADER_LOC + "/shadow" + Vertex_LOC;
    public static final String ShadowShader_FRAGMENT_FILE = SHADER_LOC + "/shadow" + Fragment_LOC;

    public static final String TerrainShader_VERTEX_FILE = SHADER_LOC + "/terrain" + Vertex_LOC;
    public static final String TerrainShader_FRAGMENT_FILE = SHADER_LOC + "/terrain" + Fragment_LOC;

    public static final String StaticShader_VERTEX_FILE = SHADER_LOC + "/static" + Vertex_LOC;
    public static final String StaticShader_FRAGMENT_FILE = SHADER_LOC + "/static" + Fragment_LOC;

    public static final String FontShader_VERTEX_FILE = SHADER_LOC + "/font" + Vertex_LOC;
    public static final String FontShader_FRAGMENT_FILE = SHADER_LOC + "/font" + Fragment_LOC;

    public static final String GuiShader_VERTEX_FILE = SHADER_LOC + "/gui" + Vertex_LOC;
    public static final String GuiShader_FRAGMENT_FILE = SHADER_LOC + "/gui" + Fragment_LOC;

    public static final String ParticleShader_VERTEX_FILE = SHADER_LOC + "/particle" + Vertex_LOC;
    public static final String ParticleShader_FRAGMENT_FILE = SHADER_LOC + "/particle" + Fragment_LOC;

    public static final int MAX_LIGHTS = 4;

    // Shader Vars End
    public static Color3f PostProcessing_TintColor = new Color3f(0.69f, 0.9f, 0.9f);
    public static Color3f PostProcessing_VignetteColor = new Color3f(0f, 0.0f, 0.0f);

    public static float PostProcessing_VignetteRadius = 0.75f;
    public static float PostProcessing_VignetteSoftness = 0.45f;

    // Terrain Stuff
    public static final int Terrain_SIZE = 256;
    public static final int Terrain_VERTX_COUNT = 32;

    public static final String Terrain_Environment_01_Name = "Obsidian_";
    public static final String Terrain_Environment_02_Name = "Stone_";
    public static final int Terrain_Environment_01_Count = Terrain_SIZE / 16;
    public static final int Terrain_Environment_02_Count = Terrain_SIZE / 16;
    public static final boolean Terrain_Environment_01_Physics = true;
    public static final boolean Terrain_Environment_02_Physics = true;

    // Title
    public static int Title_default_duration = 5;
    public static final Color3f Title_default_color = Color3f.WHITE;

    //Chat
    public static int chatlength = 100;

    /**
     * <b>ScreenshotName</b><br>
     * This String must be splitted by ":_:" First part is the name second part
     * is the date after the name
     */
    public static final String ScreenshotName = "screenshot_" + trenn + "yyyy-MM-dd_HH.mm.ss.SSS";

    public static final float RUN_SPEED = 40;
    public static final float BOOST_SPEED = 40;
    public static final float TURN_SPEED = 160;
    public static final float GRAVITY = -50;
    public static final float JUMP_POWER = 28;

    public static final float RADIUS = 140;

    public static final int[] PostProcessing_Normal() {
        int[] pospro = new int[PostProcessing.length];
        pospro[0] = 0;
        pospro[1] = 0;
        pospro[2] = 0;
        pospro[3] = 0;
        pospro[4] = 0;
        pospro[5] = 1;
        return pospro;
    }

    public static final int[] PostProcessing_Hurt() {
        int[] pospro = new int[PostProcessing.length];
        pospro[0] = 8;
        pospro[1] = 7;
        pospro[2] = 0;
        pospro[3] = 0;
        pospro[4] = 0;
        pospro[5] = 0;
        return pospro;
    }

    public static final int[] PostProcessing_Dead() {
        int[] pospro = new int[PostProcessing.length];
        pospro[0] = 8;
        pospro[1] = 5;
        pospro[2] = 7;
        pospro[3] = 0;
        pospro[4] = 0;
        pospro[5] = 0;
        return pospro;
    }

    public static final int[] PostProcessing_Menu() {
        int[] pospro = new int[PostProcessing.length];
        pospro[0] = 7;
        pospro[1] = 7;
        pospro[2] = 7;
        pospro[3] = 7;
        pospro[4] = 7;
        pospro[5] = 1;
        return pospro;
    }
}
