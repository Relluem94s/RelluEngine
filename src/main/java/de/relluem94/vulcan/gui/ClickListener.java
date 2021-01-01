package de.relluem94.vulcan.gui;

import static de.relluem94.vulcan.main.Things.*;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;

public class ClickListener {

//	private int id;
    public ClickListener() {
        try {
            gui_audio = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/sounds/gui/gui.ogg"));
        } catch (IOException e1) {

        }
    }

    //TODO Clicklistener 3D und z-index fuer guiElement je nach hoehe wird dann ein anderer Button geklickt
    public void update() {
        Vector2f pos = Utils.getNormalizedMouseCoordinates();
        Vector2f scale;
        Vector2f position;

        float a = pos.x;
        float d = pos.y;

        for (int i = 0; i < Main.guiElements.size(); i++) {
            if (Main.guiElements.get(i).isClickable() && Main.guiElements.get(i).isVisible()) {

                scale = Main.guiElements.get(i).getScale();
                position = Main.guiElements.get(i).getPosition();

                float b = position.x - scale.x;
                float c = position.x + scale.x;

                float e = position.y - scale.y;
                float f = position.y + scale.y;

                if (checkClick(a, b, c, d, e, f)) {
                    Main.guiElements.get(i).onMouseOver();
                    if (Mouse.isButtonDown(0)) {
                        if (Main.guiElements.get(i).getId() != 99 && Main.guiElements.get(i).getId() != 98) {
                            gui_audio.playAsSoundEffect(gui_audio_pitch, gui_audio_volume, false);
                        }
                        Main.guiElements.get(i).onClick();
                    }
                } else {
                    Main.guiElements.get(i).onMouseOut();
                }
            }
        }
    }

    public boolean checkClick(float a, float b, float c, float d, float e, float f) {
        boolean isclicked = false;
        if (a >= b && a <= c && d >= e && d <= f) {
            isclicked = true;
        }
        return isclicked;
    }

}
