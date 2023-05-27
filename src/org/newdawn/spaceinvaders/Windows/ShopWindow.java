package org.newdawn.spaceinvaders.Windows;

import org.newdawn.spaceinvaders.Item;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class ShopWindow extends JFrame {
    private JPanel panel;

    //shopShip1, shopShip2, shopShip3, shopShip4, shopShip5
    private ArrayList<JButton> btnList = new ArrayList<>();
    private int clicked = 0;

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
        Integer[] xList = new Integer[]{100, 360, 620, 230, 490};
        Integer[] yList = new Integer[]{200, 200, 200, 400, 400};

        for (int i=0;i<5;i+=1){
            btnList.add(drawButton("src/sprites/ShopShip"+i+".png", xList[i], yList[i]));
            panel.add(btnList.get(i));
            this.btnMouseListener(btnList.get(i), i);
        }
    }

    private JButton drawButton(String ref, int x, int y) {
        ImageIcon buttonIcon = new ImageIcon(ref);
        Image buttonimg = buttonIcon.getImage();
        Image buttonimgchange = buttonimg.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        ImageIcon buttonchange = new ImageIcon(buttonimgchange);
        JButton button = new JButton(buttonchange);
        button.setName(ref);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setSize(90, 90);
        button.setBounds(x, y, 90, 90);

        return button;
    }

    private void btnMouseListener(JButton button, int index) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                Integer[] priceList = new Integer[]{200, 300, 500, 700, 1100};
                if (index == 4) { clicked += 1; }

                if (index == 4 && clicked == 5) { launchEasterEgg(); }
                else{
                    if (Item.purchasedSkins[index]){
                            int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                                    "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                                    JOptionPane.YES_NO_OPTION);
                            if(response == JOptionPane.YES_OPTION){ Item.activeSkin.set(index);}
                    } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                                priceList[index] + " money를 주고 이 스킨을 구매할까요?", "구매 확인",
                                JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (Item.money.get() >= priceList[index]) {
                                Item.money.set(Item.money.get() - priceList[index]);
                                Item.purchasedSkins[index] = true;
                            } else {
                                JOptionPane.showMessageDialog(ShopWindow.this,
                                        "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
    }

    private void launchEasterEgg() {
        String response = JOptionPane.showInputDialog(ShopWindow.this,
                "Write the name of professor who teachs this class");
        if (response.equals("김순태")|| response.equals("김순태 교수님")){
            JOptionPane.showMessageDialog(ShopWindow.this,
                    "넌 강해졌다. 돌격해! (!وريهم قوتك)", "내면의 힘을 폭발시켜라!",
                    JOptionPane.INFORMATION_MESSAGE);
            clicked = 0;
            Item.gainedItems[4] = true;
        } else {
            JOptionPane.showMessageDialog(ShopWindow.this,
                    "대체 그게 무슨 소리니...", "",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}