package org.newdawn.spaceinvaders.Entity;

import org.newdawn.spaceinvaders.Sprite;
import org.newdawn.spaceinvaders.SpriteStore;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * An entity represents any element that appears in the game. The
 * entity is responsible for resolving collisions and movement
 * based on a set of properties defined either by subclass or externally.
 * // 엔티티는 게임에 나타나는 모든 요소
 * Note that doubles are used for positions. This may seem strange
 * given that pixels locations are integers. However, using double means
 * that an entity can move a partial pixel. It doesn't of course mean that
 * they will be display halfway through a pixel but allows us not lose
 * accuracy as we move.
 *
 * @author Kevin Glass
 */
public abstract class Entity {
    /**
     * The current x location of this entity
     */ //엔티티의 현재 x 위치
    protected double x;
    /**
     * The current y location of this entity
     */ //엔티티의 현재 y 위치
    protected double y;
    /**
     * The sprite that represents this entity
     */
    protected Sprite sprite;
    /**
     * The current speed of this entity horizontally (pixels/sec)
     */ //가로 속도
    protected double dx;
    /**
     * The current speed of this entity vertically (pixels/sec)
     */ //세로 속도
    protected double dy;
    /**
     * The rectangle used for this entity during collisions  resolution
     */ //이 엔티티의 충돌 해결시 사용되는 직사각형
    private final Rectangle me = new Rectangle();
    /**
     * The rectangle used for other entities during collision resolution
     */
    private final Rectangle him = new Rectangle();

    /**
     * Construct an entity based on a sprite image and a location.
     *
     * @param ref The reference to the image to be displayed for this entity
     * @param x   The initial x location of this entity
     * @param y   The initial y location of this entity
     */
    public Entity(String ref, int x, int y) {
        this.sprite = SpriteStore.get().getSprite(ref);
        this.x = x;
        this.y = y;
    }

    /**
     * Request that this entity move itself based on a certain amount
     * of time passing.
     *
     * @param delta The amount of time that has passed in milliseconds
     */
    public void move(long delta) {
        // update the location of the entity based on move speeds
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    /**
     * Set the horizontal speed of this entity
     *
     * @param dx The horizontal speed of this entity (pixels/sec) //수평 속도
     */
    public void setHorizontalMovement(double dx) {
        this.dx = dx;
    }

    /**
     * Set the vertical speed of this entity
     *
     */
    public void setVerticalMovement(double dy) {
        this.dy = dy;
    }

    /**
     * Get the horizontal speed of this entity //수평 속도룰 가져옴
     *
     * @return The horizontal speed of this entity (pixels/sec)
     */
    public double getHorizontalMovement() {
        return dx;
    }

    /**
     * Get the vertical speed of this entity // 수직 속도를 가져옴
     *
     * @return The vertical speed of this entity (pixels/sec)
     */
    public double getVerticalMovement() {
        return dy;
    }

    /**
     * Draw this entity to the graphics context provided
     *
     * @param g The graphics context on which to draw
     */
    public void draw(Graphics g) {
        sprite.draw(g, (int) x, (int) y);
    }

    /**
     * Do the logic associated with this entity. This method
     * will be called periodically based on game events
     */
    public void doLogic() {
    }

    /**
     * Get the x location of this entity
     *
     * @return The x location of this entity
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Get the y location of this entity
     *
     * @return The y location of this entity
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Check if this entity collided with another. // 다른 엔티티와 충돌했는지 확인
     *
     * @param other The other entity to check collision against
     * @return True if the entities collide with each other
     */
    public boolean collidesWith(Entity other) {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());

        return me.intersects(him);
    }

    /**
     * Notification that this entity collided with another.
     *
     * @param other The entity with which this entity collided.
     */
    public abstract void collidedWith(Entity other);
}