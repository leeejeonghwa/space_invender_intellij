package org.newdawn.spaceinvaders.Player;

import javax.sound.sampled.Clip;
import java.io.File;

public abstract class Player {
    protected Clip clip;
    protected long pausedPosition = 0;
    protected File audioFile;
    public void pauseAudio() {
        clip.stop();
        pausedPosition = clip.getMicrosecondPosition();
        clip.close();
    }
    public boolean isPlaying(){ return clip.isActive(); }
    public abstract void playAudio();
}
