import Control.UserData;
import view.StartFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import model.MusicPlayer;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main extends UserData {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try{
            ReadFromFile();
        }catch(NoSuchElementException e){
            System.out.println("Can not read the user data!");
        }
        SwingUtilities.invokeLater(() -> {
            StartFrame startFrame=new StartFrame();
            startFrame.setVisible(true);
            MusicPlayer music=new MusicPlayer();
            try {
                music.playBackgroundMusic("resource\\bgm.wav");
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
    }
}