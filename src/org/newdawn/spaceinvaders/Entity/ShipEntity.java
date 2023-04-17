package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Game;

/**
 * The entity that represents the players ship
 *
 * @author Kevin Glass
 */
public class ShipEntity extends Entity {
    /**
     * The game in which the ship exists
     */
    private Game game;
    private boolean collided;
    private int health = 3;

    /**
     * Create a new entity to represent the players ship
     *
     * @param game The game in which the ship is being created
     * @param ref  The reference to the sprite to show for the ship
     * @param x    The initial x location of the player's ship
     * @param y    The initial y location of the player's ship
     */
    public ShipEntity(Game game, String ref, int x, int y) {
        super(ref, x, y);

        this.game = game;
    }

    /**
     * Request that the ship move itself based on an elapsed ammount of
     * time
     *
     * @param delta The time that has elapsed since last move (ms) // 마지막 이동 이후 경과한 시간
     */
    public void move(long delta) {
        // if we're moving left and have reached the left hand side
        // of the screen, don't move
        if ((dx < 0) && (x < 18)) {
            return;
        }
        // if we're moving right and have reached the right hand side
        // of the screen, don't move
        if ((dx > 0) && (x > 747)) {
            return;
        }
        // if we're moving up and have reached the top of the screen, don't move
        if ((dy < 0) && (y < 23)) {
            return;
        }
        // if we're moving down and have reached the bottom of the screen, don't move
        if ((dy > 0) && (y > 542)) {
            return;
        }

        super.move(delta);
    }

    /**
     * Notification that the player's ship has collided with something
     *
     * @param other The entity with which the ship has collided
     */
    public void collidedWith(Entity other) {
        if(other instanceof AlienEntity || other instanceof ShotAlienEntity){
            collided = true;
            if (health <= 0 && collided == true){
                game.notifyDeath();
            }
            else if(health > 0 && collided == true){
                health -= 1;
                collided = false;
            }
        }
    }

    public void increaseMaxHealth() {
        health = 4;
    }

    public int returnNowHealth() {
        return this.health;
    }
}