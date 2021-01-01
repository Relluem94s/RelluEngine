package de.relluem94.vulcan.toolbox;

import static de.relluem94.vulcan.main.Things.WORLDOFFSET;
import static de.relluem94.vulcan.main.Things.WORLDSIZE;
import static de.relluem94.vulcan.toolbox.Variables.ScreenshotName;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.entities.components.HealthComponent;
import de.relluem94.vulcan.entities.components.LevelComponent;
import de.relluem94.vulcan.entities.components.LightComponent;
import de.relluem94.vulcan.entities.components.PositionComponent;
import de.relluem94.vulcan.gui.font.GUIText;
import de.relluem94.vulcan.gui.font.TextMaster;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessConfig;
import de.relluem94.vulcan.toolbox.generators.Date;
import de.relluem94.vulcan.toolbox.generators.FixedSizeList;
import de.relluem94.vulcan.toolbox.generators.Store;
import de.relluem94.vulcan.toolbox.maths.Color3f;
import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class Utils {

    /**
     * @return Message in Console
     * @param message String to Print
     * @param id ID to Print<br>
     *
     * 0 = INFO<br>
     * 1 = ERROR<br>
     * 2 = CHAT<br>
     * 3 = DEBUG<br>
     * 4 = LOADING<br>
     * 5 = EXEC<br>
     * 6 = TEXTONLY <br>
     * 7 = Spacer <br>
     */
    public static void log(String message, int id) {
//		if(Main.gameIsLoaded){
//			Main.chat.setText(message);
//		}

        if (id == 0) {
            System.out.println("[INFO] " + message);
        } else if (id == 1) {
            System.out.println("[ERROR] " + message);
        } else if (id == 2) {
            System.out.println("[CHAT] " + message);
        } else if (id == 3) {
            if (Main.debug) {
                System.out.println("[DEBUG] " + message);
            }
        } else if (id == 4) {
            System.out.println("[LOADING] " + message);
        } else if (id == 5) {
            System.out.println("[EXEC] " + message);
        } else if (id == 6) {
            System.out.println(message);
        } else if (id == 7) {
            System.out.println(Variables.trennstrich);
        } else {
            System.out.println("[INFO] " + message);
        }
    }

    /**
     * @return TerrainID
     */
    public static int getTerrain(Vector2f pos) {
        int id = ((checkZ(pos.y) * WORLDSIZE)) + (checkX(pos.x));
        id = Main.world.getTerrain("Terrain_" + id);
        return id;
    }

    public static int getTerrain(float x, float z) {
        int id = ((checkZ(z) * WORLDSIZE)) + (checkX(x));
        id = Main.world.getTerrain("Terrain_" + id);
        return id;
    }

    private static int checkX(float x) {
        float size = 0;
        int i;
        for (i = 0; size < x; i++) {
            size = size + Variables.Terrain_SIZE;
        }
        return i - WORLDOFFSET;
    }

    private static int checkZ(float z) {
        float size = 0;
        int i;
        for (i = 0; size < z; i++) {
            size = size + Variables.Terrain_SIZE;
        }
        return i - WORLDOFFSET;
    }

    /**
     * @return Execute String[]
     * @throws IOException
     */
    public static void execute(String[] args) {
        String s = toString(args);
        if (commands.contains(args[0])) {
            log(s, 5);
            log(Variables.trennstrich, 0);
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    log("- help", 0);
                    log("- kill", 0);
                    log("- set", 0);
                    log("- exit", 0);
                    log("- info", 0);
                    log("- screenshot", 0);
                    log("- title", 0);
                    log("- clear", 0);
                    log("- hud", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
                } else if (args[0].equalsIgnoreCase("kill")) {
                    HealthComponent.kill(Main.player.getID());
                    log("Killed Player", 0);
                } else if (args[0].equalsIgnoreCase("hud")) {
                    Main.toggleHUD2();
                } else if (args[0].equalsIgnoreCase("screenshot")) {
                    Main.screenshot.takeScreenshot();
                } else if (args[0].equalsIgnoreCase("info")) {
                    log("- Screenshot Format:PNG,JPG,GIF", 0);

//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
//					log("- notyet", 0);
                } else if (args[0].equalsIgnoreCase("exit")) {
                    log("Exit Requested", 0);
                    Main.closeRequest = true;
                } else if (args[0].equalsIgnoreCase("set")) {
                    log("- vignette_color r,g,b", 0);
                    log("- vignette_radius float", 0);
                    log("- vignette_softness float", 0);
                    log("", 0);
                    log("- tint_color r,g,b", 0);
                    log("", 0);
                    log("- player_position x,y,z", 0);
                    log("- player_rotation x,y,z", 0);
                    log("- player_level float", 0);
                    log("- player_health float", 0);
                    log("- player_runspeed float", 0);
                    log("- player_boostspeed float", 0);
                    log("- player_turnspeed float", 0);
                    log("- player_gravity float", 0);
                    log("- player_jumppower float", 0);
                    log("", 0);
                    log("- entity_position x,y,z", 0);
                    log("- entity_rotation x,y,z", 0);
                    log("- entity_health float", 0);
                    log("", 0);
                    log("- engine_debug boolean", 0);
                    log("- engine_windowresize boolean", 0);
                    log("- engine_particle boolean", 0);
                    log("- engine_maxfps int", 0);

                    //log("- player_level float", 0);
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("kill")) {
                    if (isInt(args[1])) {
                        int i = Integer.parseInt(args[1]);
                        if (i < Main.entities.size()) {
                            HealthComponent.kill(i);
                            log("Killed Entity: " + i, 0);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("title")) {
                    log("Title Command", 0);
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args[1].startsWith("engine")) {
                        if (args[1].equalsIgnoreCase("engine_debug")) {
                            Main.debug = toBoolean(args[2]);
                            log("Debug Mode: " + Main.debug, 0);
                        } else if (args[1].equalsIgnoreCase("engine_particle")) {
                            Main.particle = toBoolean(args[2]);
                            log("Particle Mode: " + Main.particle, 0);
                        } else if (args[1].equalsIgnoreCase("engine_maxfps")) {
                            Main.FPS_CAP = toInt(args[2]);
                            log("FPS Cap: " + Main.FPS_CAP, 0);
                        } else if (args[1].equalsIgnoreCase("engine_windowresize")) {
                            Display.setResizable(toBoolean(args[2]));
                            log("Resizeable Display is now: " + Display.isResizable(), 0);
                        }
                    } // 
                    else if (args[1].startsWith("postprocess")) {
                        if (args[1].equalsIgnoreCase("postprocess_channel0")) {
                            PostProcessConfig.setPostProcess(0, toInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("postprocess_channel1")) {
                            PostProcessConfig.setPostProcess(1, toInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("postprocess_channel2")) {
                            PostProcessConfig.setPostProcess(2, toInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("postprocess_channel3")) {
                            PostProcessConfig.setPostProcess(3, toInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("postprocess_channel4")) {
                            PostProcessConfig.setPostProcess(4, toInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("postprocess_channel5")) {
                            PostProcessConfig.setPostProcess(5, toInt(args[2]));
                        }

                    } else if (args[1].startsWith("vignette")) {
                        if (args[1].equalsIgnoreCase("vignette_color")) {
                            setVignetteColor(toColor3f(args[2]));
                            log("Color changed", 0);
                        } else if (args[1].equalsIgnoreCase("vignette_radius")) {
                            setVignetteRadius(toFloat(args[2]));
                            log("Radius changed", 0);
                        } else if (args[1].equalsIgnoreCase("vignette_softness")) {
                            setVignetteSoftness(toFloat(args[2]));
                            log("Softness changed", 0);
                        }
                    } else if (args[1].startsWith("tint")) {
                        if (args[1].equalsIgnoreCase("tint_color")) {
                            setTintColor(toColor3f(args[2]));
                            log("Color changed", 0);
                        }
                    } else if (args[1].startsWith("light")) {
                        if (args[1].equalsIgnoreCase("light_0_color")) {
                            setLightColor(0, toColor3f(args[2]));
                            log("Color changed", 0);
                        } else if (args[1].equalsIgnoreCase("light_1_color")) {
                            setLightColor(1, toColor3f(args[2]));
                            log("Color changed", 0);
                        } else if (args[1].equalsIgnoreCase("light_2_color")) {
                            setLightColor(2, toColor3f(args[2]));
                            log("Color changed", 0);
                        } else if (args[1].equalsIgnoreCase("light_3_color")) {
                            setLightColor(3, toColor3f(args[2]));
                            log("Color changed", 0);
                        }
                    } else if (args[1].startsWith("player")) {
                        if (args[1].equalsIgnoreCase("player_position")) {
                            PositionComponent.setPosition(Main.player.getID(), toVector3f(args[2]));
                            log("Position changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_rotation")) {
                            PositionComponent.setRotation(Main.player.getID(), toVector3f(args[2]));
                            log("Rotation changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_health")) {
                            HealthComponent.setHealth(Main.player.getID(), toFloat(args[2]));
                            log("Health changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_level")) {
                            LevelComponent.setLevel(Main.player.getID(), toFloat(args[2]));
                            log("Level changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_runspeed")) {
                            Main.conc.setRunSpeed(toFloat(args[2]));
                            log("Runspeed changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_boostspeed")) {
                            Main.conc.setBoostSpeed(toFloat(args[2]));
                            log("Boostspeed changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_turnspeed")) {
                            Main.conc.setTurnSpeed(toFloat(args[2]));
                            log("Turnspeed changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_gravity")) {
                            Main.conc.setGravity(toFloat(args[2]));
                            log("Gravity changed", 0);
                        } else if (args[1].equalsIgnoreCase("player_jumppower")) {
                            Main.conc.setJumpPower(toFloat(args[2]));
                            log("Jumppower changed", 0);
                        }
                    }
                }
            } else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args[1].startsWith("entity")) {
                        if (args[1].equalsIgnoreCase("entity_position")) {
                            PositionComponent.setPosition(toInt(args[2]), toVector3f(args[3]));
                            log("Position changed", 0);
                        } else if (args[1].equalsIgnoreCase("entity_rotation")) {
                            PositionComponent.setRotation(toInt(args[2]), toVector3f(args[3]));
                            log("Rotation changed", 0);
                        } else if (args[1].equalsIgnoreCase("entity_health")) {
                            HealthComponent.setHealth(toInt(args[2]), toFloat(args[3]));
                            log("Health changed", 0);
                        }
                    }
                }
            } else {
                if (args[0].equalsIgnoreCase("title")) {
                    String text = toString(args).replace("title ", "");
//					if(!Main.title_text.isVisible()){
//						Main.title_text.setVisible(true);
//					}
                    //TODO title
                    if (text != null) {
                        Runnable r1 = new Runnable() {
                            @Override
                            public void run() {
                                log(text + " | Updated Text", 0);
                                updateText(Main.title_text, text);
                            }
                        };
                        Main.executorService.submit(r1);
                    }
                }

            }
        } else {
            log(Variables.trennstrich, 0);
            log("Command not found: " + s, 1);
        }
        log(Variables.trennstrich, 0);
    }

    private static List<String> commands = new ArrayList<>();

    private static HashMap<String, Integer> setVars = new HashMap<>();

    /**
     *  //TODO Liste aktualliesieren 0 = Float 1 = Color3f 2 = Vector3f
     *
     */
    public static void registerCommands() {
        commands.add("help");
        commands.add("kill");
        commands.add("title");
        commands.add("set");
        commands.add("info");
        commands.add("exit");
        commands.add("screenshot");
        commands.add("hud");

        setVars.put("vignette_color", 1);
        setVars.put("vignette_radius", 0);
        setVars.put("vignette_softness", 0);
        setVars.put("tint_color", 1);
        setVars.put("light_0_color", 1);
        setVars.put("light_1_color", 1);
        setVars.put("light_2_color", 1);
        setVars.put("light_3_color", 1);
        setVars.put("player_position", 2);
        setVars.put("player_rotation", 2);
        setVars.put("player_level", 0);
        setVars.put("player_runspeed", 0);
        setVars.put("player_boostspeed", 0);
        setVars.put("player_turnspeed", 0);
        setVars.put("player_gravity", 0);
        setVars.put("player_jumppower", 0);

    }

    public static void clearConsole() {
        //	Main.console.clear();
    }

    /**
     * @return Returns String
     */
    public static String toString(String[] input) {
        String output = "";
        for (int i = 0; i < input.length; i++) {
            output = output + " " + input[i];
        }
        return output;
    }

    public static Float toFloat(String input) {
        if (isFloat(input)) {
            float x = Float.parseFloat(input);
            return x;
        } else {
            return null;
        }
    }

    public static Integer toInt(String input) {
        if (isInt(input)) {
            int x = Integer.parseInt(input);
            return x;
        } else {
            return null;
        }
    }

    public static boolean toBoolean(String input) {
        boolean x = Boolean.parseBoolean(input);
        return x;
    }

    public static Vector3f toVector3f(String input) {
        String[] vector = input.split(",");
        if (isFloat(vector[0]) && isFloat(vector[1]) && isFloat(vector[2])) {
            float x = Float.parseFloat(vector[0]);
            float y = Float.parseFloat(vector[1]);
            float z = Float.parseFloat(vector[2]);
            return new Vector3f(x, y, z);
        } else {
            return null;
        }
    }

    public static Color3f toColor3f(String input) {
        String[] color = input.split(",");
        if (isFloat(color[0]) && isFloat(color[1]) && isFloat(color[2])) {
            float r = Float.parseFloat(color[0]);
            float g = Float.parseFloat(color[1]);
            float b = Float.parseFloat(color[2]);
            return new Color3f(r, g, b);
        } else {
            return null;
        }
    }

    public static void setLightColor(int light, Color3f color) {
        LightComponent.setColour(Main.lights.get(light), color);
    }

    public static void setTintColor(Color3f color) {
        Variables.PostProcessing_TintColor = color;
    }

    public static void setVignetteColor(Color3f color) {
        Variables.PostProcessing_VignetteColor = color;
    }

    public static void setVignetteRadius(float radius) {
        Variables.PostProcessing_VignetteRadius = radius;
    }

    public static void setVignetteSoftness(float softness) {
        Variables.PostProcessing_VignetteSoftness = softness;
    }

    public static boolean isInt(String s) {
        try {
            @SuppressWarnings("unused")
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            @SuppressWarnings("unused")
            float f = Float.parseFloat(s);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    public static String ScreenshotName() {
        String[] split = ScreenshotName.split(Variables.trenn);

        return split[0] + Date.getDate(split[1]);
    }

    public static String OSname() {
        return System.getProperty("os.name");
    }

    public static String OSversion() {
        return System.getProperty("os.version");
    }

    public static String OsArch() {
        return System.getProperty("os.arch");
    }

    public static String CPU_Identifer() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

    public static String CPU_Architecture() {
        return System.getenv("PROCESSOR_ARCHITECTURE");
    }

    public static String CPU_NumberOfCores() {
        return System.getenv("NUMBER_OF_PROCESSORS");
    }

    public static String GPU_Info() {
        return GL11.glGetString(GL11.GL_RENDERER);
    }

    public static String GL_Version() {
        return GL11.glGetString(GL11.GL_VERSION);
    }

    public static String AL_Version() {
        String version = null;
        try {
            if (AL.isCreated()) {
                version = AL10.alGetString(AL10.AL_VERSION);
            } else {
                AL.create();
                version = AL10.alGetString(AL10.AL_VERSION);
                AL.destroy();
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String GLSL_Version() {
        return GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
    }

    public static String JAVA_Name() {
        return System.getProperty("java.vm.name");
    }

    public static String JAVA_Version() {
        return System.getProperty("java.vm.version");
    }

    public static void updateTextAlign(GUIText text, boolean center) {
        TextMaster.removeText(text);
        text.setCentered(center);
        TextMaster.loadText(text);
    }

    public static void updateText(GUIText text, String message) {
        TextMaster.removeText(text);
        text.setText(message);
        TextMaster.loadText(text);
    }

    public static void updateText(GUIText text, boolean visible) {
        TextMaster.removeText(text);
        text.setVisible(visible);
        TextMaster.loadText(text);
    }

    public static Vector2f getNormalizedMouseCoordinates() {
        float normX = -1.0f + 2.0f * Mouse.getX() / Display.getWidth();
        float normY = -1.0f + 2.0f * Mouse.getY() / Display.getHeight();
        Vector2f vec = new Vector2f(normX, normY);
        return vec;
    }

    public static void writeFile(File file, List<String> content) {
        try {
            Path file2 = Paths.get(file.getPath());
            Files.write(file2, content, Charset.forName("UTF-8"));
        } catch (IOException e1) {
        }
    }

    public static void writeWorldFile(File file, List<FixedSizeList<Store>> stores) {
        List<String> batch = new ArrayList<>();
        for (int a = 0; a < stores.size(); a++) {
            batch.add("" + a + "");
            for (int b = 0; b < stores.get(a).size(); b++) {
                if (stores.get(a).get(b) != null && !(stores.get(a).get(b).getValue() instanceof TexturedModel)) {
                    batch.add(stores.get(a).get(b).getValue().toString());
                }
            }
        }
        writeFile(file, batch);
    }

    public static List<String> readFile(String path, Charset encoding) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            String output = new String(encoded, encoding);
            List<String> out = new ArrayList<>();
            String[] temp = output.split("\n");
            for (int i = 0; i < temp.length; i++) {
                out.add(temp[i]);
            }
            return out;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static void doChatUpdate() {

    }

}
