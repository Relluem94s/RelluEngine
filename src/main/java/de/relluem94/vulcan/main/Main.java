package de.relluem94.vulcan.main;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.ImageIOImageData;

import de.relluem94.vulcan.entities.BaseEntity;
import de.relluem94.vulcan.entities.components.CollisionComponent;
import de.relluem94.vulcan.entities.components.HealthComponent;
import de.relluem94.vulcan.entities.components.LevelComponent;
import de.relluem94.vulcan.entities.components.LightComponent;
import de.relluem94.vulcan.entities.components.ModelComponent;
import de.relluem94.vulcan.entities.components.ParticleComponent;
import de.relluem94.vulcan.entities.components.PhysicsComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.entities.components.TerrainComponent;
import de.relluem94.vulcan.gui.GuiElement;
import de.relluem94.vulcan.gui.GuiRenderer;
import de.relluem94.vulcan.gui.font.FontType;
import de.relluem94.vulcan.gui.font.GUIText;
import de.relluem94.vulcan.gui.font.TextMaster;
import de.relluem94.vulcan.models.ModelTexture;
import de.relluem94.vulcan.models.TerrainTexture;
import de.relluem94.vulcan.models.TerrainTexturePack;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.particles.ParticleMaster;
import de.relluem94.vulcan.particles.ParticleTexture;
import de.relluem94.vulcan.renderEngine.Fbo;
import de.relluem94.vulcan.renderEngine.MasterRenderer;
import de.relluem94.vulcan.renderEngine.loader.Loader;
import de.relluem94.vulcan.renderEngine.loader.NormalMappedObjLoader;
import de.relluem94.vulcan.renderEngine.loader.OBJLoader;
import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessConfig;
import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessing;
import de.relluem94.vulcan.toolbox.MousePicker;
import de.relluem94.vulcan.toolbox.SplashScreen;
import de.relluem94.vulcan.toolbox.TerrainLoader;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.generators.InitDir;
import de.relluem94.vulcan.toolbox.generators.TextureGen;
import de.relluem94.vulcan.toolbox.maths.Color3f;
import de.relluem94.vulcan.toolbox.maths.Color3i;
import de.relluem94.vulcan.toolbox.maths.Vector3f;
import de.relluem94.vulcan.toolbox.maths.Vector4f;
import de.relluem94.vulcan.world.World;

public class Main extends Things {

    // ************* INIT STUFF *************//
    //TODO ArrayOutOfBoundsException TerrainLoader / Store Weltende?
    //TODO MemoryLeak Executor m�glicherwei�e. (Screenshots! und Ingame..)
    //TODO CPU Usage beim reingezoomten und im Men� sehr hoch. Clock/Tick und auch MouseListener verbessern.
    //NOLONGERTODO Konsole und Chat ingame (nicht als 2. Fenster) 
    //TODO Konsole ingame (nicht als 2. Fenster) 
    //NOLONGERTODO Thread Context OpenGL Error.. fixxen
    //TODO Obsidian Stone 3&4 zu einer Textur und diese in verschiedene Farbt�ne �ndern
    //TODO Stone schwebt wegen scale fixxen
    public static void init() throws IOException {
        sc = new SplashScreen();
        //console = new Console();
        sc.setVisible(true);
        sc.setAlwaysOnTop(true);
        sc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InitDir.checkDirs();
        InitDir.loadOptions();
        display_normal = new DisplayMode(WIDTH, HEIGHT);
        display_full = new DisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        createDisplay();
        loader = new Loader();
        renderer = new MasterRenderer();
        guiRenderer = new GuiRenderer();
        terrainloader = new TerrainLoader();
        TextMaster.init();
        ParticleMaster.init(MasterRenderer.getProjectionMatrix());
        fbo_a = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
        fbo_b = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
        PostProcessing.init(PostProcessing_Menu());
        PostProcessConfig.init();
    }

    // ************* DISPLAY STUFF *************//
    public static void resize() {
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        MasterRenderer.updateProjectionMatrix();
        ParticleMaster.updateProjectionMatrix();
        Utils.log("Display Resized to: " + Display.getWidth() + "x" + Display.getHeight() + " Pixel", 0);
    }

    private static DisplayMode findDisplayMode(int width, int height, int bpp) throws LWJGLException {
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        DisplayMode mode1 = null;

        for (int i = 0; i < modes.length; i++) {
            if ((modes[i].getBitsPerPixel() == bpp) || (mode1 == null)) {
                if ((modes[i].getWidth() == width) && (modes[i].getHeight() == height)) {
                    mode1 = modes[i];
                }
            }
        }

        return mode1;
    }

    private static DisplayMode mode;

    public static void setDisplayMode(int width, int height, int bpp) {
        try {
            mode = findDisplayMode(width, height, bpp);

            if (mode == null) {
                Utils.log(width + "x" + height + "x" + bpp + " display mode unavailable", 1);
                return;
            }

            Display.setDisplayMode(mode);

        } catch (LWJGLException e) {

            e.printStackTrace();
            Utils.log("Failed: " + e.getMessage(), 1);
        }
    }

    public static void setFullScreen() {
        if (isFullScreen()) {
            try {
                setDisplayMode(display_normal.getWidth(), display_normal.getHeight(), 32);
                resize();
                Display.setFullscreen(false);
                Display.update();

            } catch (LWJGLException e) {
            }

        } else {
            try {
                setDisplayMode(display_full.getWidth(), display_full.getHeight(), 32);
                resize();
                Display.setFullscreen(true);
                Display.update();

            } catch (LWJGLException e) {
                e.printStackTrace();
            }

        }
    }

    public static boolean isFullScreen() {
        return Display.isFullscreen();
    }

    public static void setDisplayColor(float r, float g, float b) {
        Display.setInitialBackground(r, g, b);
    }

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
        lastFPS = getCurrentTime();

        ByteBuffer[] icon = new ByteBuffer[]{
            new ImageIOImageData().imageToByteBuffer(iconX16, false, false, null),
            new ImageIOImageData().imageToByteBuffer(iconX32, false, false, null),
            new ImageIOImageData().imageToByteBuffer(iconX64, false, false, null)
        };

        Display.setIcon(icon);
        Display.setTitle(title);

        try {

            Display.setFullscreen(fullscreen);
            // 0.753f, 0.753f, 0.753f = Silver // 
            setDisplayColor(0, 0, 0);
            Display.setDisplayMode(display_normal);
            Display.setVSyncEnabled(vsync);
            Display.setResizable(true);

            Display.create(new PixelFormat().withDepthBits(24).withSamples(Main.ANTIALASING_FILTERING), attribs);

            GL11.glEnable(GL13.GL_MULTISAMPLE);
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            lastFrameTime = getCurrentTime();
        } catch (LWJGLException e1) {
            Utils.log("" + e1.getMessage(), 1);
        }
    }

    public static boolean wasResized() {
        return Display.wasResized();
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
        if (wasResized()) {
            resize();
        }
        if (Display.isCloseRequested()) {
            Main.closeRequest = true;
        }
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static Vector2f getResolution() {
        return new Vector2f(Display.getWidth(), Display.getHeight());
    }

    public static void toggleMenu() {
        start.setVisible(main_menu);
        start.setClickable(main_menu);
        exit.setVisible(main_menu);
        exit.setClickable(main_menu);
        setup.setVisible(main_menu);
        setup.setClickable(main_menu);
        credit.setClickable(main_menu);
        credit.setVisible(main_menu);
        saves.setClickable(main_menu);
        saves.setVisible(main_menu);
        if (isChatOpen) {
            toggleChat();
        }

        main_menu = !main_menu;

    }

    /**
     * Toggle with Main menu check
     */
    public static void toggleHUD() {
        if (!main_menu) {
            pos.setVisible(false);
            fps.setVisible(false);
            vers.setVisible(false);
            player_health.setVisible(false);
            player_level.setVisible(false);
        } else {
            pos.setVisible(true);
            fps.setVisible(true);
            vers.setVisible(true);
            player_health.setVisible(true);
            player_level.setVisible(true);
        }
    }

    /**
     * Toggle without Main menu check
     */
    public static void toggleHUD2() {
        if (pos.isVisible()) {
            pos.setVisible(false);
            fps.setVisible(false);
            vers.setVisible(false);
            player_health.setVisible(false);
            player_level.setVisible(false);
        } else {
            pos.setVisible(true);
            fps.setVisible(true);
            vers.setVisible(true);
            player_health.setVisible(true);
            player_level.setVisible(true);
        }
    }

    public static void toggleOptions() {
        setup_gui.setVisible(setup_menu);
        setup_gui.setClickable(false);
        setup_gui_ok.setVisible(setup_menu);
        setup_gui_ok.setClickable(setup_menu);
        setup_menu = !setup_menu;
    }

    public static void toggleSaves() {
        saves_gui.setVisible(saves_menu);
        saves_gui.setClickable(false);
        saves_gui_ok.setVisible(saves_menu);
        saves_gui_ok.setClickable(saves_menu);
        saves_menu = !saves_menu;
    }

    public static void toggleCredits() {
        credit_gui.setVisible(credit_menu);
        credit_gui.setClickable(false);
        credit_gui_ok.setVisible(credit_menu);
        credit_gui_ok.setClickable(credit_menu);
        credit_menu = !credit_menu;
    }

    public static void toggleChat() {
        isChatOpen = !isChatOpen;
        chat.setVisible(isChatOpen);
        if (isChatOpen) {
//			chat.setScale(new Vector2f(1f, 0.04f));
//			chat.setPosition(new Vector2f(-0f, -0.96f));
            control = false;
        } else {
            control = true;
        }

    }

    public static void closeDisplay() {
        AL.destroy();
        Display.destroy();
        System.exit(0);
    }

    // ************* TEXT STUFF *************//
    public static void defineText() {
        sc.increaseProgress();
        Vector4f border = new Vector4f(0.5f, 0.5f, 0.3f, 0.0f);
        float fontsize = 0.85f;
        boolean center = false;
        guiText = new ArrayList<GUIText>();
        font = new FontType(loader.loadTexture("fonts/agencydf"), new File("assets/textures/fonts/agencydf.fnt"));
        vers = new GUIText("Rellu Engine " + state + " v" + version, fontsize, font, new Vector2f(0.003f, 0.880f), 0.5f, center);
        vers.set(border, Color3f.LIGHT_GREY, Color3f.LIGHT_GREY);
        fps = new GUIText("FPS: " + fps_count, fontsize, font, new Vector2f(0.003f, 0.905f), 0.5f, center);
        fps.set(border, Color3f.LIGHT_GREY, Color3f.LIGHT_GREY);
        pos = new GUIText("POS: x y z", fontsize, font, new Vector2f(0.003f, 0.930f), 0.5f, center);
        pos.set(border, Color3f.LIGHT_GREY, Color3f.LIGHT_GREY);

        play = new GUIText("", fontsize, font, new Vector2f(0.4f, 0.005f), 0.5f, center);
        play.set(border, Color3f.LIGHT_GREY, Color3f.LIGHT_GREY);

        title_text = new GUIText("Test Title", 4f, font, new Vector2f(0.24f, 0.44f), 0.5f, true);
        title_text.set(new Vector4f(0.01f, 0.01f, 0.5f, 0.1f), Color3f.RED, Color3f.RED);

        player_level = new GUIText("Level", fontsize, font, new Vector2f(0.003f, 0.03f), 0.5f, center);
        player_level.set(border, Color3f.BLUE, Color3f.BLUE);

        player_health = new GUIText("Leben", fontsize, font, new Vector2f(0.003f, 0.005f), 0.5f, center);
        player_health.set(border, Color3f.GREEN, Color3f.GREEN);

        sc.increaseProgress();
    }

    // ************* MODEL & TEXTURE STUFF *************//
    public static void loadModelsAndTextures() {
        sc.increaseProgress();

        playerModel = new TexturedModel(OBJLoader.loadObjModel("entities/vehicle", loader), new ModelTexture(loader.loadTexture("entities/vehicle")));
        lamp = new TexturedModel(OBJLoader.loadObjModel("objects/lamp", loader), new ModelTexture(loader.loadTexture("objects/lamp")));

//		obsidian = new TexturedModel(OBJLoader.loadObjModel("terrain/obsidian", loader), new ModelTexture(Main.loader.loadTexture(TextureGen.SolidColorTexture,new Color3i(139,69,19), 64)));
//		stone = new TexturedModel(OBJLoader.loadObjModel("terrain/stone", loader), new ModelTexture(Main.loader.loadTexture(TextureGen.SolidColorTexture,new Color3i(128,128,128), 64)));
        obsidian = new TexturedModel(OBJLoader.loadObjModel("terrain/obsidian", loader), new ModelTexture(loader.loadTexture("terrain/obsidian")));
        stone = new TexturedModel(OBJLoader.loadObjModel("terrain/stone", loader), new ModelTexture(loader.loadTexture("terrain/stone5")));

        //tempModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("objects/cube", loader), new ModelTexture(loader.loadTexture("objects/brick")));
        tempModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("objects/cube", loader), new ModelTexture(loader.loadTexture(TextureGen.ChessColorTexture, Color3i.PURPLE, Color3i.LIME, 256)));
        MasterRenderer.getTexture(tempModel.getTextureID()).setNormalMap(loader.loadTexture("objects/NormalMap"));
        MasterRenderer.getTexture(tempModel.getTextureID()).setUseFakeLighting(true);
        MasterRenderer.getTexture(tempModel.getTextureID()).setShineDamper(20);
        MasterRenderer.getTexture(tempModel.getTextureID()).setReflectivity(0.2f);

        MasterRenderer.getTexture(obsidian.getTextureID()).setTextureMulti(5);
        MasterRenderer.getTexture(stone.getTextureID()).setTextureMulti(5);

        sc.increaseProgress();

        backgroundTexture = new TerrainTexture(Main.loader.loadTexture(TextureGen.SolidColorTexture, new Color3i(222, 184, 135), 64));
        //backgroundTexture = new TerrainTexture(loader.loadTexture("terrain/stone"));

        rTexture = new TerrainTexture(loader.loadTexture("terrain/stone5"));
        gTexture = new TerrainTexture(loader.loadTexture("terrain/stone4"));
        bTexture = new TerrainTexture(loader.loadTexture("terrain/dirt"));

        texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
                gTexture, bTexture);

        fireParticle = new ParticleTexture(loader.loadTexture("particles/cosmic"), 4, true);
    }

    // ************* TERRAIN STUFF *************//
    public static void createTerrain() {
        sc.increaseProgress();
        world = new World(WORLDSIZE, WORLDOFFSET);
        sc.increaseProgress();
        world.generateWorld();
        sc.increaseProgress();
        world.loadTerrain(STARTTERRAIN);
        //world.loadTerrain(STARTTERRAIN);
        sc.increaseProgress();

        spawnpoint = new Vector3f(TerrainComponent.getTerrainMid(world.getTerrain(STARTTERRAIN)));
    }

    // ************* ENTITIES STUFF *************//
    public static void createEntities() {
        sc.increaseProgress();
        // Customize Components
        PositionComponent.set(player.getID(), spawnpoint, new Vector3f(1, 0, 1), 0.5f);
        conc.set(RUN_SPEED / 2, BOOST_SPEED / 2, GRAVITY, JUMP_POWER, TURN_SPEED);
        ModelComponent.init(player.getID());
        ModelComponent.setRender(player.getID(), true);
        ModelComponent.set(player.getID(), playerModel, 10, 0f);
        ModelComponent.setRender(player.getID(), true);
        HealthComponent.setMaxHealth(player.getID(), 400);
        HealthComponent.setHealth(player.getID(), 400);
        LevelComponent.setMaxLevel(player.getID(), 100);
        LevelComponent.setLevel(player.getID(), 0);
        ParticleComponent.init(player.getID());
        ParticleComponent.set(player.getID(), fireParticle, 250, 3, 0.1f, 1, 4, 0.2f, 4, 0.2f, true);
        ParticleComponent.setDirection(player.getID(), new Vector3f(0, 2, 0), 3);
        camc.init();
        sc.increaseProgress();
        // Temp Components
        CollisionComponent cc;

        PositionComponent.set(sun.getID(), new Vector3f(100000000, 1000000000, 1000000000), new Vector3f(0, 0, 0), 1);
        LightComponent.init(sun.getID());
        LightComponent.set(sun.getID(), new Color3f(1.0f, 0.95f, 0.905f), new Vector3f(1, 0, 0), new Vector3f(0, 0, 0));
        lights.add(sun.getID());

        Main.sc.increaseProgress();

        ModelComponent.init(tempentity.getID());
        ModelComponent.set(tempentity.getID(), tempModel);
        ModelComponent.setRender(tempentity.getID(), true);
        PositionComponent.set(tempentity.getID(), new Vector3f(spawnpoint.x + 60, spawnpoint.y + 50, spawnpoint.z + 60), new Vector3f(0, 0, 0), 3f);
        ModelComponent.setShadow(tempentity.getID(), false);
        PhysicsComponent.setWeight(tempentity.getID(), 10);
        PhysicsComponent.set(tempentity.getID(), true);

        normalMapEntities.add(tempentity.getID());
        Main.sc.increaseProgress();

        cc = new CollisionComponent(lampe_red.getID());
        cc.init();
        ModelComponent.init(lampe_red.getID());
        ModelComponent.set(lampe_red.getID(), lamp, 1, 0.001f);
        ModelComponent.setRender(lampe_red.getID(), true);
        PositionComponent.set(lampe_red.getID(), new Vector3f(spawnpoint.x + 40, spawnpoint.y + 15, spawnpoint.z + 15), new Vector3f(0, 0, 0), 1);
        LightComponent.init(lampe_red.getID());
        LightComponent.set(lampe_red.getID(), Color3f.RED, new Vector3f(0.1f, 0.01f, 0.01f), new Vector3f(0, 8f, 1));
        //SoundComponent.playEffect("OGG", "entities/footstep.ogg", (Vector3f) Main.stores.get(lampe_red.getID()).get(0).getValue());
        PhysicsComponent.setWeight(lampe_red.getID(), 10);
        PhysicsComponent.set(lampe_red.getID(), true);

        entities.add(lampe_red.getID());
        lights.add(lampe_red.getID());

        cc = new CollisionComponent(lampe_green.getID());
        cc.init();
        ModelComponent.init(lampe_green.getID());
        ModelComponent.set(lampe_green.getID(), lamp, 1, 0.001f);
        ModelComponent.setRender(lampe_green.getID(), true);
        PositionComponent.set(lampe_green.getID(), new Vector3f(spawnpoint.x + 35, spawnpoint.y + 15, spawnpoint.z + 15), new Vector3f(0, 0, 0), 1);
        LightComponent.init(lampe_green.getID());
        LightComponent.set(lampe_green.getID(), Color3f.GREEN, new Vector3f(0.1f, 0.01f, 0.01f), new Vector3f(0, 8f, 1));
        PhysicsComponent.setWeight(lampe_green.getID(), 10);
        PhysicsComponent.set(lampe_green.getID(), true);
        entities.add(lampe_green.getID());
        lights.add(lampe_green.getID());

        cc = new CollisionComponent(lampe_blue.getID());
        cc.init();
        ModelComponent.init(lampe_blue.getID());
        ModelComponent.setRender(lampe_blue.getID(), true);
        ModelComponent.set(lampe_blue.getID(), lamp, 1, 0.001f);
        PositionComponent.set(lampe_blue.getID(), new Vector3f(spawnpoint.x + 30, spawnpoint.y + 15, spawnpoint.z + 15), new Vector3f(0, 0, 0), 1);
        LightComponent.init(lampe_blue.getID());
        LightComponent.set(lampe_blue.getID(), Color3f.BLUE, new Vector3f(0.1f, 0.01f, 0.01f), new Vector3f(0, 8f, 1));

        PhysicsComponent.setWeight(lampe_blue.getID(), 10);
        PhysicsComponent.set(lampe_blue.getID(), true);

        entities.add(lampe_blue.getID());
        lights.add(lampe_blue.getID());

        Main.sc.increaseProgress();
    }

    // ************* PYSICS STUFF *************//
    public static void initPhysics() {
        if (physics.size() == 0) {
            for (Integer id : entities) {
                if (BaseEntity.hasPhysicsComponent(id) && BaseEntity.hasPositionComponent(id)) {
                    physics.add(id);
                }
            }
            for (Integer id : normalMapEntities) {
                if (BaseEntity.hasPhysicsComponent(id) && BaseEntity.hasPositionComponent(id)) {
                    physics.add(id);
                }
            }
        } else {
            physics.clear();
        }
    }

    public static void doPhysics() {
        for (Integer id : physics) {
            if (PhysicsComponent.isInAir(id)) {
                PhysicsComponent.check(id);
            }
        }
    }

    // ************* DEBUG STUFF *************//
    public static void showDebugInfo() {
        if (!gameIsLoaded) {
            sc.increaseProgress();
        }

        if (!debug) {
            Utils.log("Debug in Config is false", 0);
        } else {
            Utils.log("// ************* DebugInfo *************//", 3);
            Utils.log("OS Name: " + Utils.OSname(), 3);
            Utils.log("OS Version: " + Utils.OSversion(), 3);
            Utils.log("OS Architecture: " + Utils.OsArch(), 3);
            Utils.log("Java Name : " + Utils.JAVA_Name(), 3);
            Utils.log("Java Version : " + Utils.JAVA_Version(), 3);
            Utils.log("GPU : " + Utils.GPU_Info(), 3);
            Utils.log("OpenGL Version : " + Utils.GL_Version(), 3);
            Utils.log("GLSL Version : " + Utils.GLSL_Version(), 3);
            Utils.log("OpenAL Version : " + Utils.AL_Version(), 3);
            Utils.log("CPU Cores: " + Utils.CPU_NumberOfCores(), 3);
            Utils.log("Map Seed: " + SEED, 3);
            int obsidians = 0;
            int stones = 0;
            int render = 0;
            for (Integer id : entities) {
                if (BaseEntity.hasPositionComponent(id)) {
                    if (ModelComponent.getRender(id)) {
                        render++;
                    }
                    if (BaseEntity.getName(id).startsWith("Obsidian_")) {
                        obsidians++;
                    } else if (BaseEntity.getName(id).startsWith("Stone_")) {
                        stones++;
                    } else if (!BaseEntity.hasLightComponent(id)) {
                        Utils.log("Name: " + BaseEntity.getName(id) + ", Pos: " + PositionComponent.getPosition(id).toString() + ", ID: " + id, 3);
                    }
                } else {
                    Utils.log("Name: " + BaseEntity.getName(id) + ", ID: " + id, 3);
                }
            }
            Utils.log("Name: " + BaseEntity.getName(player.getID()) + ", ID: " + player.getID() + ", POS: " + PositionComponent.getPosition(player.getID()).toString(), 3);
            Utils.log("Name: " + BaseEntity.getName(camera.getID()) + ", ID: " + camera.getID(), 3);
            for (int i = 0; i < lights.size(); i++) {
                Utils.log("Name: " + BaseEntity.getName(lights.get(i)) + ", ID: " + PositionComponent.getPosition(lights.get(i)).toString() + ", ID: " + lights.get(i), 3);
            }
            Utils.log(terrains.size() + " Terrains loaded ", 3);
            Utils.log(obsidians + " Obsidans total", 3);
            Utils.log(stones + " Stones total", 3);
            //Utils.log(liquids.size() + " Liquids loaded", 3);
            Utils.log(world.getTerrainsSize() + " Terrains total", 3);
            int fullsize = entities.size() + world.getTerrainsSize() + 1; // + liquids.size() // +1 (Camera)
            Utils.log(fullsize + " Entities total", 3);
            Utils.log(render + " Entities renderd", 3);
            Utils.log("// ************* DebugInfo End *************//", 3);
        }

    }

    // ************* GUI STUFF *************//
    public static void initGUI() {
        sc.increaseProgress();
        int button = loader.loadTexture("gui/button");
        int background = loader.loadTexture("gui/background");
        int chat_bg = loader.loadTexture("gui/chat");

        Vector2f scale = new Vector2f(0.5f, 0.5f);
        Vector2f offset = new Vector2f(0.1402f, 0.13f);
        Vector2f position = new Vector2f(0.0f, 0.0f);

        Vector2f ok_position = new Vector2f(0.244f, -0.6f);
        Vector2f button_scale = new Vector2f(0.1f, 0.1f);
        Vector2f button_offset = new Vector2f(0.025f, 0.030f);

        chat = new GuiElement(chat_bg, new Vector2f(-0f, -0.96f), new Vector2f(1f, 0.04f), new Vector2f(0.489f, 0.02f), true, true, "", 0.98f) {
            @Override
            public void onClick() {
//				chat.setScale(new Vector2f(1f, 0.4f));
//				chat.setPosition(new Vector2f(-0f, -0.6f));
            }
        };
        chat.setCetered(false);
        chat.setVisible(false);
        chat.setClickable(false);
        chat.setId(98);

        //MainMenu
        start = new GuiElement(button, new Vector2f(0.0f, 0.4f), button_scale, new Vector2f(0.025f, 0.030f), true, true, "Start") {
            @Override
            public void onClick() {
                Main.control = true;
                PostProcessing.update(Main.PostProcessing_Normal());
                Main.toggleMenu();
                Main.toggleHUD();
            }
        };
        start.setId(0);

        saves = new GuiElement(button, new Vector2f(0.0f, 0.2f), button_scale, new Vector2f(0.025f, 0.030f), true, true, "Saves") {
            @Override
            public void onClick() {
                Main.toggleMenu();
                Main.toggleSaves();
            }
        };
        saves.setId(6);

        setup = new GuiElement(button, new Vector2f(0.0f, 0.0f), button_scale, new Vector2f(0.025f, 0.030f), true, true, "Settings") {
            @Override
            public void onClick() {
                Main.toggleMenu();
                Main.toggleOptions();
            }
        };
        setup.setId(2);

        credit = new GuiElement(button, new Vector2f(0.0f, -0.2f), button_scale, new Vector2f(0.025f, 0.030f), true, true, "Credits") {
            @Override
            public void onClick() {
                Main.toggleMenu();
                Main.toggleCredits();
            }
        };
        credit.setId(3);

        exit = new GuiElement(button, new Vector2f(0.0f, -0.4f), button_scale, new Vector2f(0.025f, 0.030f), true, true, "Exit") {
            @Override
            public void onClick() {
                Main.closeRequest = true;
            }
        };
        exit.setId(1);

        //CreditsMenu
        String credit_gui_text = "Programmer: " + "Relluem94                                        " + "Tester: " + Utils.toString(tester);
        credit_gui = new GuiElement(background, position, scale, offset, true, false, credit_gui_text, "Credits") {
            @Override
            public void onClick() {

            }
        };
        credit_gui.setId(-1);
        credit_gui.setVisible(false);
        credit_gui.setClickable(false);

        credit_gui_ok = new GuiElement(button, ok_position, button_scale, new Vector2f(0.025f, 0.030f), true, false, "OK") {
            @Override
            public void onClick() {
                Main.toggleCredits();
                Main.toggleMenu();
            }
        };
        credit_gui_ok.setVisible(false);
        credit_gui_ok.setClickable(false);
        credit_gui_ok.setId(4);

        //SettingsMenu
        String setup_text = "Not Implemented yet";
        setup_gui = new GuiElement(background, position, scale, offset, true, false, setup_text, "Settings") {
            @Override
            public void onClick() {

            }
        };
        setup_gui.setId(-1);
        setup_gui.setVisible(false);
        setup_gui.setClickable(false);

        setup_gui_ok = new GuiElement(button, ok_position, button_scale, button_offset, true, false, "OK") {
            @Override
            public void onClick() {
                Main.toggleOptions();
                Main.toggleMenu();
            }
        };
        setup_gui_ok.setVisible(false);
        setup_gui_ok.setClickable(false);
        setup_gui_ok.setId(5);

        //SavesMenu
        String saves_text = "Saves not Implemented yet";
        saves_gui = new GuiElement(background, position, scale, offset, true, false, saves_text, "Saves") {
            @Override
            public void onClick() {

            }
        };
        saves_gui.setId(-1);
        saves_gui.setVisible(false);
        saves_gui.setClickable(false);

        saves_gui_ok = new GuiElement(button, ok_position, button_scale, button_offset, true, false, "OK") {
            @Override
            public void onClick() {
                Main.toggleSaves();
                Main.toggleMenu();
            }
        };
        saves_gui_ok.setVisible(false);
        saves_gui_ok.setClickable(false);
        saves_gui_ok.setId(7);

        //UIMenu
        ui_gui = new GuiElement(background, position, scale, offset, true, false, "", "Info") {
            @Override
            public void onClick() {

            }
        };
        ui_gui.setId(-1);
        ui_gui.setVisible(false);
        ui_gui.setClickable(false);

        ui_gui_ok = new GuiElement(button, ok_position, button_scale, button_offset, true, false, "OK") {
            @Override
            public void onClick() {

            }
        };
        ui_gui_ok.setVisible(false);
        ui_gui_ok.setClickable(false);
        ui_gui_ok.setId(8);

        //Other
        logo = new GuiElement(loader.loadTexture("gui/relluengine"), new Vector2f(0.87f, 0.965f),
                new Vector2f(0.128f, 0.184f), new Vector2f(0.025f, 0.030f), true, true, "") {
            @Override
            public void onClick() {
                if (this.getId() != buttoncooldown) {
                    Main.debug = !Main.debug;
                    Utils.log("DebugMode is: " + Main.debug, 0);
                    Main.buttoncooldown = this.getId();
                }
            }
        };
        logo.setId(99);

        guiElements.add(chat);
        guiElements.add(start);
        guiElements.add(exit);

        guiElements.add(saves);
        guiElements.add(saves_gui);
        guiElements.add(saves_gui_ok);

        guiElements.add(setup);
        guiElements.add(setup_gui);
        guiElements.add(setup_gui_ok);

        guiElements.add(credit);
        guiElements.add(credit_gui);
        guiElements.add(credit_gui_ok);
        guiElements.add(logo);

        TextMaster.loadText(vers);
        TextMaster.loadText(fps);
        TextMaster.loadText(pos);

        TextMaster.loadText(player_health);
        TextMaster.loadText(player_level);

        //title_text.setVisible(true);
        TextMaster.loadText(title_text);
        sc.increaseProgress();
    }

    // ************* MOUSE PICKER STUFF *************//
    public static void initMousePicker() {
        picker = new MousePicker(MasterRenderer.getProjectionMatrix());
        sc.dispose();
        gameIsLoaded = true;
        control = false;

        PostProcessing.update(PostProcessing_Menu());

        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(2);
        }

        Utils.registerCommands();

        Utils.log("Game is started.. Welcome!", 0);
    }

    // ************* LOOP STUFF *************//
    public static void update() throws IOException {
        conc.move();
        updateHealth(player.getID());
        updateLevel(player.getID());
        camc.move(player.getID());
        //picker.update();  //momentan keinen Nutzen..
        clistner.update();
        if (particle) {
            ParticleComponent.update(player.getID(), PositionComponent.getPosition(tempentity.getID()));
            ParticleMaster.update();
        }
        if (player_audio != null) {
            if (player_audio.isPlaying()) {
                SoundStore.get().poll(0);
            }

        }
    }

    private static float old_health = 0;
    private static float old_level = 0;

    private static void updateHealth(int id) {
        HealthComponent.update(id);
        float new_health = HealthComponent.getHealth(id);
        if (new_health != old_health) {
            old_health = new_health;
            Utils.updateText(player_health, "Health: " + new_health);
            Utils.log("Updated Health", 0);
        }
    }

    private static void updateLevel(int id) {
        float new_level = LevelComponent.getLevel(id);
        if (new_level != old_level) {
            old_level = new_level;
            Utils.updateText(player_level, "Level: " + new_level);
            Utils.log("Updated Level", 0);
        }
    }

    public static void render3D() {
        fbo_a.bindFrameBuffer();
        renderer.renderScene();
        if (particle) {
            ParticleMaster.renderParticles();
        }
        fbo_a.unbindFrameBuffer();
        PostProcessing.doPostProcessing(fbo_a.getColourTexture(), fbo_a.getDepthTexture());
    }

    public static void render2D() {
        guiRenderer.render();
        TextMaster.render();
        if (control) {
            Utils.updateText(pos, "POS: " + PositionComponent.getPosition(player.getID()).toString());
        }
    }

    public static void prepare() {
        updateDisplay();
        updateFPS();
    }

    /**
     * Updates Every Second
     */
    public static void updateInSeconds() {
        //Utils.log("FPS: " + fps_count, 0);
        if (buttoncooldown != -1) {
            buttoncooldown = -1;
        }
        if (control || isChatOpen) {
            if (fps.isVisible()) {
                Utils.updateText(fps, "FPS: " + fps_count);
            }
        }
    }

    /**
     * executes updateInSeconds
     */
    public static void updateFPS() {
        if (getCurrentTime() - lastFPS > 1000) {
            fps_count = fpscount;
            fpscount = 0;
            lastFPS += 1000;

            updateInSeconds();
        }
        fpscount++;
    }

    public static void cleanUp() {
        Utils.log("Game is closed ... Cleaning up", 0);

        PostProcessing.cleanUp();
        fbo_a.cleanUp();
        fbo_b.cleanUp();
        entities.clear();
        stores.clear();
        ParticleMaster.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();
        renderer.cleanUp();
        closeDisplay();
    }
}
