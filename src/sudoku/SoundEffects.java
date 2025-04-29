package sudoku;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
    // Method to play sound files
    public void playSound(String soundFileName) {
        try {
            // Use this path format for reliability
            String soundPath = new File("sounds/" + soundFileName).getAbsolutePath();
            System.out.println("Trying to load: " + soundPath);  // Debug line
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                new File(soundPath).getAbsoluteFile());
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();  // Full error details
        }
    }
}
