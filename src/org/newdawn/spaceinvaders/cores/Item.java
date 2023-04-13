package org.newdawn.spaceinvaders.cores;

public class Item {
    private Boolean[] itemList = new Boolean[]{false, false, false, false, false};

    public void clearStage(Boolean[] itemState) {
        this.itemList = itemState;
    }

    public Boolean[] enableItems(){
        return this.itemList;
    }
}
