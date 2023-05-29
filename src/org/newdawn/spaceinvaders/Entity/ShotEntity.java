package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Game;
import org.newdawn.spaceinvaders.Player.AudioPlayer;
import org.newdawn.spaceinvaders.Player.Player;

/**
 * An entity representing a shot fired by the player's ship //총알
 *
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
    /**
     * The game in which this entity exists
     */
    private Game game;

    /**
     * Create a new shot from the player
     *
     * @param game   The game in which the shot has been created
     * @param sprite The sprite representing this shot
     * @param x      The initial x location of the shot
     * @param y      The initial y location of the shot
     */
    public ShotEntity(Game game, String sprite, int x, int y) {
        super(sprite, x, y);
        this.game = game;

        Player shotAudioPlayer = new AudioPlayer("src/sound/shot.wav");
        shotAudioPlayer.playAudio();

        dy = -300;
    }

    /**
     * Request that this shot moved based on time elapsed
     *
     * @param delta The time that has elapsed since last move
     */
    public void move(long delta) {
        // proceed with normal move
        super.move(delta);

        // if we shot off the screen, remove ourselves // 화면 밖으러 나가면 총알 제거
        if (y < -100) {
            game.removeEntity(this);
        }
    }

    /**
     * Notification that this shot has collided with another
     * entity
     *
     * @parma other The other entity with which we've collided
     */
    public void collidedWith(Entity other) {
    }
}