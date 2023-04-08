package org.newdawn.spaceinvaders;

import org.newdawn.spaceinvaders.entity.ShipEntity;
import org.newdawn.spaceinvaders.Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Item {
    private Boolean[] itemList = new Boolean[] {false, false, false, false, false};
    private Game game;

    public Item(Game g){
        this.game = g;
    }

    public void clearStage(int stage){
        this.itemList[stage] = true;
    }

    public void increaseFireSpeed(){
        if(this.itemList[0] == true){ this.game.increaseFireSpeed(); }
        else{ showUnableMessage(); }
    }

    public void increaseMaxHealth(){
        if(this.itemList[1] == true){}
        else{ showUnableMessage(); }
    }

    public void increaseMoveSpeed(){
        if (this.itemList[2] == true){ this.game.increaseMoveSpeed(); }
        else{ showUnableMessage(); }
    }

    public void equipShiled(){
        if (this.itemList[3] == true){}
        else{ showUnableMessage(); }
    }

    public void increaseFireNum(){
        if (this.itemList[4] == true){}
        else{ showUnableMessage(); }
    }

    private void showUnableMessage(){
        /*Need to implement drawString("You didn't clear stage yet.")*/ 
    }
}
