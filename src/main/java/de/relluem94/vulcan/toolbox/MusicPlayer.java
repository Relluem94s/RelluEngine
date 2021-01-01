package de.relluem94.vulcan.toolbox;

import static de.relluem94.vulcan.main.Things.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.relluem94.vulcan.main.Main;

public class MusicPlayer {

    private File[] files;
    private int index = 0;
    public boolean isLoading = false;

    public MusicPlayer() {
        if (Variables.MusicDir.listFiles() != null) {
            files = Variables.MusicDir.listFiles();
        }

    }

    private String nothingtoplay = "Playing: Nothing found to Play";
    private String unknown = "Now Playing: Unknown Title";

    private static ExecutorService executorService;

    public void stop() {
        if (player_audio.isPlaying()) {
            player_audio.stop();
            index--;
            player_audio = null;
        }
    }

    public void play() {
        if (files != null) {
            if (executorService == null) {
                executorService = Executors.newFixedThreadPool(1);
            }
            final String name = files[index].getName();
            if (name.toLowerCase().endsWith("ogg")) {
                Runnable r1 = new Runnable() {
                    @Override
                    public void run() {

                        try {
                            isLoading = true;
                            player_audio = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource(Variables.MusicDir + "/" + name));
                            player_audio.playAsSoundEffect(player_audio_pitch, player_audio_volume, false);
                            isLoading = false;
                        } catch (IOException e) {
                        }
                    }
                };
                executorService.submit(r1);

                try {
                    Utils.log("Playing: " + Variables.MusicDir + "/" + name, 0);
                    Main.play.setVisible(true);
                    Main.music_audio.stop();
                    Utils.updateText(Main.play, "Now Playing: " + name.replace(".ogg", ""));

                } catch (NullPointerException e) {
                    Main.play.setVisible(true);
                    Utils.updateText(Main.play, unknown);
                }

                index++;
                if (index >= files.length) {
                    index = 0;
                }
            } else {
                Utils.log(nothingtoplay, 0);
            }
        } else {
            Utils.log(nothingtoplay, 0);
        }
    }
}
