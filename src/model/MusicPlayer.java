package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class MusicPlayer implements Serializable {
    public void playClick(String MusicPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File(MusicPath);
        if (music.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
    }
    public void playBackgroundMusic(String MusicPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            File music = new File(MusicPath);
            if (music.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
    }

}
