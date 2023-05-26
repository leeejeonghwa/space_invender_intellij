package org.newdawn.spaceinvaders;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

public class ShopWindow extends JFrame {
    private JPanel panel;

    //shopShip1, shopShip2, shopShip3, shopShip4, shopShip5
    private ArrayList<JButton> btnList = new ArrayList<>();

    public ShopWindow(){

        setTitle("SHOP");

        setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        createPanel();
        createButtons();

        getContentPane().add(panel);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void createPanel() {
        // 패널 생성
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image shopBackground = new ImageIcon("src/sprites/shopbackground.png").getImage();
                g.drawImage(shopBackground, 0, 0, getWidth(), getHeight(), this);

                Image coin = new ImageIcon("src/sprites/coin.png").getImage();
                g.drawImage(coin, 10, 47, this);

                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(Item.money.get()), 35, 60);

                Integer[] priceList = new Integer[]{200, 300, 500, 700, 1100};
                Integer[] xList = new Integer[]{118, 378, 638, 248, 508};
                Integer[] yList = new Integer[]{320, 320, 320, 520, 520};
                for(int i=0;i<5;i+=1){
                    g.drawString("Price: " + priceList[i], xList[i], yList[i]);
                }
            }
        };
        panel.setLayout(null); // 레이아웃 매니저를 사용하지 않음
        panel.setPreferredSize(new Dimension(800, 600));
    }

    private void createButtons() {
        String[] srcList = new String[]{"Shopship1", "Shopship2", "Shopship3", "Shopship4", "Shopship5"};
        Integer[] xList = new Integer[]{100, 360, 620, 230, 490};
        Integer[] yList = new Integer[]{200, 200, 200, 400, 400};

        for (int i=0;i<5;i+=1){
            btnList.add(drawButton("src/sprites/"+srcList[i]+".png", 90, 90, xList[i], yList[i]));
            panel.add(btnList.get(i));
            this.btnMouseListener(btnList.get(i), i);
        }
    }

    private JButton drawButton(String ref, int width, int height, int x, int y) {
        ImageIcon buttonIcon = new ImageIcon(ref);
        Image buttonimg = buttonIcon.getImage();
        Image buttonimgchange = buttonimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon buttonchange = new ImageIcon(buttonimgchange);
        JButton button = new JButton(buttonchange);
        button.setName(ref);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setSize(width, height);
        button.setBounds(x, y, width, height);

        return button;
    }

    private void btnMouseListener(JButton button, int index) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                Integer[] priceList = new Integer[]{200, 300, 500, 700, 1100};
                if (Item.purchasedSkins[index]){
                    int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                    "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                    JOptionPane.YES_NO_OPTION);
                    if(response == JOptionPane.YES_OPTION){
                        Item.activeSkin.set(index);
                    } else{ return; }
                } else {
                    int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                    priceList[index] + " money를 주고 이 스킨을 구매할까요?", "구매 확인",
                    JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        // Deduct the cost of the ship from the player's money
                        if (Item.money.get() >= priceList[index]) {
                            Item.money.set(Item.money.get() - priceList[index]);
                            Item.purchasedSkins[index] = true;
                            return;
                        } else {
                            JOptionPane.showMessageDialog(ShopWindow.this,
                            "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                            JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
            }
        });
    }
}