package Presentation.Model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
    public void playSound(String fileName) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start(); // Se reproduce una vez
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

