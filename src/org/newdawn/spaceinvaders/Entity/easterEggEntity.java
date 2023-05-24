package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Game;

public class easterEggEntity extends Entity{
    /**
      * The game in which this entity exists
      */
    private final Game game;
     /*
      * True if this shot has been "used", i.e. its hit something
      */ //총알이 충돌해서 사용되었는지 여부를 나타내는 값

 
     /*
      * Create a new shot from the player
      *
      * @param game   The game in which the shot has been created
      * @param sprite The sprite representing this shot
      * @param x      The initial x location of the shot
      * @param y      The initial y location of the shot
      */
    public easterEggEntity(Game game, String sprite, int x, int y) {
        super(sprite, x, y);

        this.game = game;

        /*
         * The vertical speed at which the players shot moves
         */
        //총알이 움직이는 수직 속도
        double moveSpeed = -750;
        dy = moveSpeed;
    }

    /**
     * Request that this shot moved based on time elapsed
     *
     * @param delta The time that has elapsed since last move
     */
    public void move(long delta) {
        // proceed with normal move
        super.move(delta);

        // if we shot off the screen, remove ourselfs // 화면 밖으러 나가면 총알 제거
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
        // if we've hit an alien, kill it!
        if (other instanceof AlienEntity) {
            // remove the affected entities
            game.removeEntity(other);
            // notify the game that the alien has been killed
            game.notifyAlienKilled();
        }
    }
}
