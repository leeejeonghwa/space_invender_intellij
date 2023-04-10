package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.Game;

public class ShieldEntity extends Entity {
    private Game game;

    public ShieldEntity(Game game, String ref, int x, int y) {
        super(ref, x, y);

        this.game = game;
    }

    public void move(long delta) {
        // if we're moving left and have reached the left hand side
        // of the screen, don't move
        if ((dx < 0) && (x < 10)) {
            return;
        }
        // if we're moving right and have reached the right hand side
        // of the screen, don't move
        if ((dx > 0) && (x > 750)) {
            return;
        }
        // if we're moving up and have reached the top of the screen, don't move
        if ((dy < 0) && (y < 10)) {
            return;
        }
        // if we're moving down and have reached the bottom of the screen, don't move
        if ((dy > 0) && (y > 570)) {
            return;
        }

        super.move(delta);
    }

    public void collidedWith(Entity other) {
        if (other instanceof AlienEntity) {
            game.removeEntity(this);
        }
    }
}