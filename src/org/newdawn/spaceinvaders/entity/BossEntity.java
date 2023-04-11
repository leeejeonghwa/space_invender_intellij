package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.cores.Game;

public class BossEntity extends Entity{

    private Game game;
    private int health = 50;

    public BossEntity(Game game, int x, int y) {
        super("sprites/boss.gif", x, y);
        
        this.game = game;
    }

    @Override
    public void collidedWith(Entity other) {
        if (other instanceof ShotEntity){
            health--;
        }
        if (health <= 0){
            game.removeEntity(this);
        }
    }
    
}
