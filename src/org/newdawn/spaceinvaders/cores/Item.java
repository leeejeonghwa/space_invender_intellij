package org.newdawn.spaceinvaders.cores;

import org.newdawn.spaceinvaders.entity.ShipEntity;

public class Item {
    private Boolean[] itemList = new Boolean[]{false, false, false, false, false};
    private Game game;
    private ShipEntity ship;

    public Item(Game g, ShipEntity s) {
        this.game = g;
        this.ship = s;
    }

    public void clearStage(int stage) {
        this.itemList[stage] = true;
    }

    public void increaseFireSpeed() {
        if (this.itemList[0] == true) {
            this.game.increaseFireSpeed();
        } else {
            showUnableMessage();
        }
    }

    public void increaseMaxHealth() {
        if (this.itemList[1] == true) {
            this.ship.increaseMaxHealth();
        } else {
            showUnableMessage();
        }
    }

    public void increaseMoveSpeed() {
        if (this.itemList[2] == true) {
            this.game.increaseMoveSpeed();
        } else {
            showUnableMessage();
        }
    }

    public void enableShield() {
        if (this.itemList[3] == true) {
            this.game.enableShield();
        } else {
            showUnableMessage();
        }
    }

    public void increaseFireNum() {
        if (this.itemList[4] == true) {
            this.game.increaseFireNum();
        } else {
            showUnableMessage();
        }
    }

    private void showUnableMessage() {
        /*Need to implement drawString("You didn't clear stage yet.")*/
    }
}

}
