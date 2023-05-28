package org.newdawn.spaceinvaders.Entity;


import org.newdawn.spaceinvaders.Game;

/**
     * An entity representing a shot fired by the player's ship //총알
     *
     * @author Kevin Glass
     */
    public class ShotAlienEntity extends Entity {
        /**
         * The game in which this entity exists
         */
        private Game game;
        /**
         * True if this shot has been "used", i.e. its hit something
         */ //총알이 충돌해서 사용되었는지 여부를 나타내는 값
        private boolean used = false;

        /**
         * Create a new shot from the player
         *
         * @param game   The game in which the shot has been created
         * @param sprite The sprite representing this shot
         * @param x      The initial x location of the shot
         * @param y      The initial y location of the shot
         */
        public ShotAlienEntity(Game game, String sprite, int x, int y) {
            super(sprite, x, y);

            this.game = game;

            dy = 300;
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
            if (y > 780) {
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
            // prevents double kills, if we've already hit something,
            // don't collide
            if (used) {
                return;
            }

            // if we've hit a ship, kill it!
            if (other instanceof ShipEntity) {
                // remove the affected entities
                game.removeEntity(this);

                // notify the game that the ship has been killed
                used = true;
            }

        }
    }

