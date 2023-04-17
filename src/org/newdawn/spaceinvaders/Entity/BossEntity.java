package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Game;
import org.newdawn.spaceinvaders.Sprite;
import org.newdawn.spaceinvaders.SpriteStore;

/**
 * An entity which represents one of our space invader aliens.
 *
 * @author Kevin Glass
 */
public class BossEntity extends Entity {
    /**
     * The speed at which the alient moves horizontally
     */ // 에일리언이 수평으로 이동하는 속도
    private double moveSpeed = 75;
    /**
     * The game in which the entity exists
     */
    private Game game;

    private int shotCooldown = 0;
    /**
     * The animation frames
     */
    private Sprite[] frames = new Sprite[4];
    /**
     * The time since the last frame change took place
     */
    private long lastFrameChange;
    /**
     * The frame duration in milliseconds, i.e. how long any given frame of animation lasts
     */ //각 프레임이 얼마 동안 보여질지를 결정하는 프레임 지속 시간 (밀리초)
    private long frameDuration = 250;
    /**
     * The current frame of animation being displayed
     */
    private int frameNumber;

    private int health = 50;

    /**
     * Create a new alien entity
     *
     * @param game The game in which this entity is being created
     * @param x    The intial x location of this alien
     * @param y    The intial y location of this alient
     */
    public BossEntity(Game game, int x, int y) {
        super("sprites/boss.png", x, y);

        frames[0] = sprite;
        frames[1] = SpriteStore.get().getSprite("sprites/boss1.png");
        frames[2] = sprite;
        frames[3] = SpriteStore.get().getSprite("sprites/boss2.png");

        this.game = game;
        dx = -moveSpeed;

    }

    /**
     * Request that this alien moved based on time elapsed
     *
     * @param delta The time that has elapsed since last move //지난 이동 이후 경과한 시간입니다.
     */
    public void move(long delta) {
        // since the move tells us how much time has passed
        // by we can use it to drive the animation, however
        // its the not the prettiest solution
        lastFrameChange += delta;

        // if we need to change the frame, update the frame number
        // and flip over the sprite in use
        if (lastFrameChange > frameDuration) {
            // reset our frame change time counter
            lastFrameChange = 0;

            // update the frame
            frameNumber++;
            if (frameNumber >= frames.length) {
                frameNumber = 0;
            }

            sprite = frames[frameNumber];
        }

        // if we have reached the left hand side of the screen and
        // are moving left then request a logic update  //왼쪽 끝
        if ((dx < 0) && (x < 10)) {
            game.updateLogic();
        }
        // and vice vesa, if we have reached the right hand side of
        // the screen and are moving right, request a logic update //오른쪽 끝
        if ((dx > 0) && (x > 750 - sprite.getWidth() + 30)) {
            game.updateLogic();
        }

        // proceed with normal move
        super.move(delta);
    }

    /**
     * Update the game logic related to aliens
     */
    public void doLogic() {
        // swap over horizontal movement and move down the
        // screen a bit
        dx = -dx;
        y += 10;

        // if we've reached the bottom of the screen then the player
        // dies
        if (y > 570) {
            game.notifyDeath();
        }
    }

    public int gethealth(){
        return health;
    }
    /**
     * Notification that this alien has collided with another entity
     *
     * @param other The other entity
     */
    public void collidedWith(Entity other) {
        // collisions with aliens are handled elsewhere
        if (other instanceof ShotEntity){
            health--;
        }
        if (health <= 0){
            game.removeEntity(this);
            game.notifyAlienKilled();}
    }


}
