package org.newdawn.spaceinvaders.Player;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import java.io.File;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class BackgroundPlayer extends Player {
    public BackgroundPlayer(String audioFilePath) {
        try {
            this.audioFile = new File(audioFilePath);
            if (audioFile.exists()) {
                this.clip = AudioSystem.getClip();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playAudio() {
        try{
            clip.open(getAudioInputStream(audioFile));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-7.0f);

            clip.loop(-1);
            clip.start();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
