package org.newdawn.spaceinvaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Player {
    private Clip bgmClip;
    private long pausedPosition;

    public void playBgm(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            bgmClip = AudioSystem.getClip();

            bgmClip.open(audioStream);

            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-7.0f);

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);

            bgmClip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void pauseBgm() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            pausedPosition = bgmClip.getMicrosecondPosition();
        }
    }

    public void resumeBgm() {
        if (bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.setMicrosecondPosition(pausedPosition);
            bgmClip.start();
        }
    }
    public void playSuccessSound(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip successclip = AudioSystem.getClip();

            successclip.open(audioStream);


            FloatControl gainControl = (FloatControl) successclip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);

            successclip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void playFailSound(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip failClip = AudioSystem.getClip();

            failClip.open(audioStream);

            FloatControl gainControl = (FloatControl) failClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);

            failClip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void playShotSound(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip shotClip = AudioSystem.getClip();

            shotClip.open(audioStream);

            FloatControl gainControl = (FloatControl) shotClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);

            shotClip.start();

            while (shotClip.getMicrosecondLength() != shotClip.getMicrosecondPosition()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}


