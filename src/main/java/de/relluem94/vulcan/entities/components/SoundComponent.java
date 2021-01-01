package de.relluem94.vulcan.entities.components;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.relluem94.vulcan.toolbox.maths.Vector3f;

public class SoundComponent {

    public static void playMusic(String type, String path, boolean repeat) {
        try {
            Audio audio = AudioLoader.getAudio(type, ResourceLoader.getResourceAsStream("assets/sounds/" + path));
            audio.playAsMusic(1.0f, 1.0f, repeat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playEffect(String type, String path, Vector3f pos) {
        try {
            Audio audio = AudioLoader.getAudio(type, ResourceLoader.getResourceAsStream("assets/sounds/" + path));
            audio.playAsSoundEffect(1.0f, 1.0f, false, pos.x, pos.y, pos.z);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playEffect(String type, String path) {
        try {
            Audio audio = AudioLoader.getAudio(type, ResourceLoader.getResourceAsStream("sounds/" + path));
            audio.playAsSoundEffect(1.0f, 1.0f, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
