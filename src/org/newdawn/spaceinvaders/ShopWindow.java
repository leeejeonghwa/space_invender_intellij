package org.newdawn.spaceinvaders;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShopWindow extends Canvas {
    private final BufferStrategy strategy;

    private final AtomicInteger money;
    private final AtomicInteger activeSkin;
    private final Boolean[] enableSkins;

    private BufferedImage shopImg;
    private BufferedImage shopShip1;
    private BufferedImage shopShip2;
    private BufferedImage shopShip3;
    private BufferedImage shopShip4;
    private BufferedImage shopShip5;

    private int easterEggClicked;
    private final Boolean[] enableItem;

    public ShopWindow(AtomicInteger money, Boolean[] enableItem, Boolean[] enableSkins, AtomicInteger activeSkin){
        JFrame container = new JFrame();

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - container.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - container.getHeight()) / 2);
        container.setLocation(centerX, centerY);

        panel.setLayout(null);

        setBounds(0, 0, 800, 600);
        panel.add(this);

        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        requestFocus();

        createBufferStrategy(2);
        strategy = getBufferStrategy();

        this.money = money;
        this.enableSkins = enableSkins;
        this.activeSkin = activeSkin;
        this.enableItem = enableItem;
        
        try{
            shopImg = ImageIO.read(new File("src/sprites/shop.png"));
            shopShip1 = ImageIO.read(new File("src/sprites/Shopship1.png"));
            shopShip2 = ImageIO.read(new File("src/sprites/Shopship2.png"));
            shopShip3 = ImageIO.read(new File("src/sprites/Shopship3.png"));
            shopShip4 = ImageIO.read(new File("src/sprites/Shopship4.png"));
            shopShip5 = ImageIO.read(new File("src/sprites/Shopship5.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                onShopClick (e.getX(), e.getY());
            }
        });
    }

    private void onShopClick(int x, int y){
        //shopShip1 click listener
        if (x >= 100 && x <= 100 + shopShip1.getWidth() &&
                y >= 200 && y <= 200 + shopShip1.getHeight()) {
                    if (this.enableSkins[0]){
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                        JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            this.activeSkin.set(0);
                        } else{ return; }
                    } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "200 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                        JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (this.money.get() >= 200) {
                                this.money.set(money.get() - 200);
                                this.enableSkins[0] = true;
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

        //shopShip2 click listener
        if (x >= 360 && x <= 360 + shopShip2.getWidth() &&
                y >= 200 && y <= 200 + shopShip2.getHeight()) {
                    if (this.enableSkins[1]){
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                        JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            this.activeSkin.set(1);
                        } else{ return; }
                    } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "300 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                        JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (this.money.get() >= 300) {
                                this.money.set(money.get() - 300);
                                this.enableSkins[1] = true;
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
        
        //shopShip3 click listener
        if (x >= 620 && x <= 620 + shopShip3.getWidth() &&
                y >= 200 && y <= 200 + shopShip3.getHeight()) {
                    if (this.enableSkins[2]){
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                        JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            this.activeSkin.set(2);
                        } else{ return; }
                    } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "500 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                        JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (this.money.get() >= 500) {
                                this.money.set(money.get() - 500);
                                this.enableSkins[2] = true;
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

        //shopShip4 click listener
        if (x >= 230 && x <= 230 + shopShip4.getWidth() &&
                y >= 400 && y <= 400 + shopShip4.getHeight()) {
                    if (this.enableSkins[3]){
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                        JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            this.activeSkin.set(3);
                        } else{ return; }
                    } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "700 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                        JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (this.money.get() >= 700) {
                                this.money.set(money.get() - 700);
                                this.enableSkins[3] = true;
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

        //shopShip5 click listener
        if (x >= 490 && x <= 490 + shopShip5.getWidth() &&
                y >= 400 && y <= 400 + shopShip5.getHeight()) {
                    easterEggClicked++;
                    if (easterEggClicked == 5){
                        String response = JOptionPane.showInputDialog(ShopWindow.this,
                        "Write the name of professor who teachs this class");
                        if (response.equals("김순태")|| response.equals("김순태 교수님")){
                            JOptionPane.showMessageDialog(ShopWindow.this,
                            "넌 강해졌다. 돌격해! (!وريهم قوتك)", "뽕 활성화",
                            JOptionPane.INFORMATION_MESSAGE);
                            easterEggClicked = 0;
                            this.enableItem[4] = true;
                            //TO DO: implement easterEgg
                        } else {
                            JOptionPane.showMessageDialog(ShopWindow.this,
                            "재수강...해야겠지...?", "세구세구야 그게 무슨 소리니...",
                            JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    } else {
                        if (this.enableSkins[4]){
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "해당 스킨을 보유중이에요. 이 스킨으로 바꿀까요?", "스킨 변경 확인",
                        JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            this.activeSkin.set(4);
                        }
                        } else {
                        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                        "1100 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                        JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Deduct the cost of the ship from the player's money
                            if (this.money.get() >= 1100) {
                                this.money.set(money.get() - 1100);
                                this.enableSkins[4] = true;
                            } else {
                                JOptionPane.showMessageDialog(ShopWindow.this,
                                "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                                JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        }

    public void shopLoop(){
        while(true){
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

            BufferedImage shopBackground;
            try {
                shopBackground = ImageIO.read(new File("src/sprites/shopbackground.png"));
                g.drawImage(shopBackground, 0, 0, this);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                BufferedImage coin = ImageIO.read(new File("src/sprites/coin.png"));
                g.drawImage(coin, 10, 47, this);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(this.money.get()), 20 + coin.getWidth(), 60);
            } catch (IOException e){
                e.printStackTrace();
            }
            
            g.drawImage(this.shopImg,400-this.shopImg.getWidth()/2,40,this);
            g.drawImage(this.shopShip1,100,200,this);
            g.drawImage(this.shopShip2,360,200,this);
            g.drawImage(this.shopShip3,620,200,this);
            g.drawImage(this.shopShip4,230,400,this);
            g.drawImage(this.shopShip5,490,400,this);

            g.setColor(Color.WHITE);
            g.drawString("Price: 200",118,320);
            g.drawString("Price: 300",378,320);
            g.drawString("Price: 500",638,320);
            g.drawString("Price: 700",248,520);
            g.drawString("Price: 1100",508,520);

            g.dispose();
            strategy.show();
        }
    }

	public AtomicInteger recieveMoney() {
        return this.money;
	}

	public Boolean[] getEnableSkin() {
		return this.enableSkins;
	}

	public AtomicInteger getSelectedSkin() {
		return this.activeSkin;
	}

    public Boolean[] getItemState(){
        return this.enableItem;
    }
}