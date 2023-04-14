package org.newdawn.spaceinvaders.cores;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private Boolean[] itemList = new Boolean[]{false, false, false, false};
    //primitive 자료형 synchronized하려면 Atomic 자료형 필요
    private AtomicInteger money = new AtomicInteger(0);

    public void clearStage(Boolean[] itemState, AtomicInteger money){
        this.itemList = itemState;
        this.money.addAndGet(money.intValue());
    }

    public Boolean[] enableItems(){
        return this.itemList;
    }

    public AtomicInteger getMoney(){
        return this.money;
    }
}
