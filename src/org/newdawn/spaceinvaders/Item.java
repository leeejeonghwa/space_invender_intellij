package org.newdawn.spaceinvaders;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    //체력 4개, 이동속도 증가, 쉴드 활성화, 총알 수 증가, easterEgg
    public static Boolean[] gainedItems = new Boolean[]{false, false, false, false, false};
    public static Boolean[] purchasedSkins = new Boolean[]{false, false, false, false, false};
    //primitive 자료형 synchronized하려면 Atomic 자료형 필요
    public static AtomicInteger money = new AtomicInteger(0);
    public static AtomicInteger activeSkin = new AtomicInteger(5);
}
