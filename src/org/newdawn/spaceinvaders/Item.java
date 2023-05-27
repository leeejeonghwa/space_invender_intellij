package org.newdawn.spaceinvaders;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private Boolean[] itemList = new Boolean[]{false, false, false, false, false};
    private Boolean[] skinList = new Boolean[]{false, false, false, false, false};
    //primitive 자료형 synchronized하려면 Atomic 자료형 필요
    private AtomicInteger moneyAmount = new AtomicInteger(0);
    private AtomicInteger activatedSkinIndex = new AtomicInteger(-1);

    //setter of itemList
    public void setItemState(Boolean[] itemState){
        this.itemList = itemState;
    }

    //getter of itemList
    public Boolean[] getItemState(){
        return this.itemList;
    }

    //setter of money
    public void setMoney(AtomicInteger money){
        this.moneyAmount.set(money.get());
    }

    //getter of money
    public AtomicInteger getMoney(){
        return this.moneyAmount;
    }

    //setter of skinList
    public void setSkinState(Boolean[] skinList){
        this.skinList = skinList;
    }

    //getter of skinList
    public Boolean[] getSkinState(){
        return this.skinList;
    }

    //setter of activate skin num
    public void setActiveSkinNumber(AtomicInteger num){
        this.activatedSkinIndex.set(num.get());
    }

    public AtomicInteger getActiveSkinNumber(){
        return this.activatedSkinIndex;
    }
}
