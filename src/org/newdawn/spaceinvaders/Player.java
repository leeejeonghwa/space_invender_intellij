package org.newdawn.spaceinvaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class Player {
    private Clip bgmclip;
    private Clip successclip;
    private Clip failclip;
    private Clip shotclip;
    private long pausedPosition;


    public void play(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            bgmclip = AudioSystem.getClip();

            bgmclip.open(audioStream);
            //소리설정
            FloatControl gainControl = (FloatControl) bgmclip.getControl(FloatControl.Type.MASTER_GAIN);

            //볼륨조정
            gainControl.setValue(-7.0f);

            bgmclip.loop(Clip.LOOP_CONTINUOUSLY);

            bgmclip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void pause() {
        if (bgmclip != null && bgmclip.isRunning()) {
            bgmclip.stop();
            pausedPosition = bgmclip.getMicrosecondPosition();
        }
    }

    public void resume() {
        if (bgmclip != null && !bgmclip.isRunning()) {
            bgmclip.setMicrosecondPosition(pausedPosition);
            bgmclip.start();
        }
    }
    public void successPlay(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            successclip = AudioSystem.getClip();

            successclip.open(audioStream);

            //소리설정
            FloatControl gainControl = (FloatControl) successclip.getControl(FloatControl.Type.MASTER_GAIN);

            //볼륨조정
            gainControl.setValue(-15.0f);

            successclip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void failPlay(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            failclip = AudioSystem.getClip();

            failclip.open(audioStream);

            //소리설정
            FloatControl gainControl = (FloatControl) failclip.getControl(FloatControl.Type.MASTER_GAIN);

            //볼륨조정
            gainControl.setValue(-15.0f);

            failclip.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void shootPlay(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + audioFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            shotclip = AudioSystem.getClip();

            shotclip.open(audioStream);
            //소리설정
            FloatControl gainControl = (FloatControl) shotclip.getControl(FloatControl.Type.MASTER_GAIN);

            //볼륨조정
            gainControl.setValue(-20.0f);

            shotclip.start();

            while (shotclip.getMicrosecondLength() != shotclip.getMicrosecondPosition()) {
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


