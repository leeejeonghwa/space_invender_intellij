package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Game;

public class EasterEggEntity extends Entity{
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
    public EasterEggEntity(Game game, String sprite, int x, int y) {
        super(sprite, x, y);
        this.game = game;
        verticlaMoveSpeed = -750;
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
     * @param other The other entity with which we've collided
     */
    public void collidedWith(Entity other) {
    }
}
