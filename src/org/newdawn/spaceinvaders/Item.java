package org.newdawn.spaceinvaders;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private Boolean[] itemList = new Boolean[]{false, false, false, false, false};
    private Boolean[] skinList = new Boolean[]{false, false, false, false, false};
    //primitive 자료형 synchronized하려면 Atomic 자료형 필요
    private AtomicInteger money = new AtomicInteger(0);
    private AtomicInteger activeSkin = new AtomicInteger(-1);

    //setter of itemList
    public void clearStage(Boolean[] itemState){
        this.itemList = itemState;
    }

    //getter of itemList
    public Boolean[] enableItems(){
        return this.itemList;
    }

    //setter of money
    public void setMoney(AtomicInteger money){
        this.money.set(money.get());
    }

    //getter of money
    public AtomicInteger getMoney(){
        return this.money;
    }

    //setter of skinList
    public void setEnableSkin(Boolean[] skinList){
        this.skinList = skinList;
    }

    //getter of skinList
    public Boolean[] enableSkinList(){
        return this.skinList;
    }

    //setter of activate skin num
    public void activateSkinNumber(AtomicInteger num){
        this.activeSkin.set(num.get());
    }

    public AtomicInteger getActiveNum(){
        return this.activeSkin;
    }
}
