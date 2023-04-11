package org.newdawn.spaceinvaders.cores;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;


public class Player implements LineListener {
    private Clip bgmclip;
    private Clip shotclip;
    private Clip successclip;
    private boolean playCompleted;

    public void play(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            bgmclip = AudioSystem.getClip();

            bgmclip.addLineListener(this);

            bgmclip.open(audioStream);

            bgmclip.loop(Clip.LOOP_CONTINUOUSLY);

            bgmclip.start();

            while (!playCompleted) {
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

    public void stop() {
        if (bgmclip != null && bgmclip.isRunning()) {
            bgmclip.stop();
            bgmclip.setFramePosition(0);
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

            successclip = AudioSystem.getClip();

            successclip.addLineListener(this);

            successclip.open(audioStream);

            successclip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void stopSuccessSound() {
        if (successclip != null && successclip.isRunning()) {
            successclip.stop();
            successclip.removeLineListener(this);
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

            shotclip = AudioSystem.getClip();

            shotclip.addLineListener(this);

            shotclip.open(audioStream);

            shotclip.start();

            while (shotclip.getMicrosecondLength() != shotclip.getMicrosecondPosition()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            shotclip.stop();
            shotclip.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            playCompleted = true;
        }
    }
}

