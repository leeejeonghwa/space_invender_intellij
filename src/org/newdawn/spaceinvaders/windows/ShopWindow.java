package org.newdawn.spaceinvaders.windows;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShopWindow extends Canvas{
    private BufferStrategy strategy;

    private JFrame container;

    private int money;

    private BufferedImage shopShip1;
    private BufferedImage shopShip2;
    private BufferedImage shopShip3;
    private BufferedImage shopShip4;
    private BufferedImage shopShip5;

    public ShopWindow(AtomicInteger money){
        container = new JFrame();

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

        this.money = money.intValue();

        try{
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
        if (x >= 25 && x <= 25 + shopShip1.getWidth() &&
                y >= 255 && y <= 255 + shopShip1.getHeight()) {
            int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                    "200 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                // Deduct the cost of the ship from the player's money
                if (money >= 200) {
                    money -= 200;
                    return;
                    // TODO: Add code to actually purchase the ship
                } else {
                    JOptionPane.showMessageDialog(ShopWindow.this,
                            "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        //shopShip2 click listener
        if (x >= 190 && x <= 190 + shopShip2.getWidth() &&
                y >= 255 && y <= 255 + shopShip2.getHeight()) {
        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
            "300 money를 주고 이 스킨을 구매할까요?", "구매 확인",
            JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Deduct the cost of the ship from the player's money
            if (money >= 300) {
                money -= 300;
                return;
                // TODO: Add code to actually purchase the ship
            } else {
                JOptionPane.showMessageDialog(ShopWindow.this,
                        "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            }
        }
        
        //shopShip3 click listener
        if (x >= 355 && x <= 355 + shopShip2.getWidth() &&
                y >= 255 && y <= 255 + shopShip2.getHeight()) {
        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
            "500 money를 주고 이 스킨을 구매할까요?", "구매 확인",
            JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Deduct the cost of the ship from the player's money
            if (money >= 500) {
                money -= 500;
                return;
                // TODO: Add code to actually purchase the ship
            } else {
                JOptionPane.showMessageDialog(ShopWindow.this,
                        "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            }
        }

        //shopShip4 click listener
        if (x >= 520 && x <= 520 + shopShip2.getWidth() &&
                y >= 255 && y <= 255 + shopShip2.getHeight()) {
        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
            "700 money를 주고 이 스킨을 구매할까요?", "구매 확인",
            JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Deduct the cost of the ship from the player's money
            if (money >= 700) {
                money -= 700;
                return;
                // TODO: Add code to actually purchase the ship
            } else {
                JOptionPane.showMessageDialog(ShopWindow.this,
                        "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            }
        }

        //shopShip5 click listener
        if (x >= 685 && x <= 685 + shopShip2.getWidth() &&
                y >= 255 && y <= 255 + shopShip2.getHeight()) {
        int response = JOptionPane.showConfirmDialog(ShopWindow.this,
            "1100 money를 주고 이 스킨을 구매할까요?", "구매 확인",
            JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Deduct the cost of the ship from the player's money
            if (money >= 1100) {
                money -= 1100;
                return;
                // TODO: Add code to actually purchase the ship
            } else {
                JOptionPane.showMessageDialog(ShopWindow.this,
                        "이 스킨을 구매하기 충분한 money가 없습니다!", "잔액 부족",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            }
        }
    }

    public void shopLoop(){
        while(true){
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Money: " + Integer.toString(this.money), 10, 20);
            
            g.drawImage(this.shopShip1,25,255,this);
            g.drawImage(this.shopShip2,190,255,this);
            g.drawImage(this.shopShip3,355,255,this);
            g.drawImage(this.shopShip4,520,255,this);
            g.drawImage(this.shopShip5,685,255,this);

            g.setColor(Color.WHITE);
            g.drawString("Price: 200",43,375);
            g.drawString("Price: 300",208,375);
            g.drawString("Price: 500",373,375);
            g.drawString("Price: 700",538,375);
            g.drawString("Price: 1100",700,375);

            g.dispose();
            strategy.show();
        }
    }

	public AtomicInteger recieveMoney() {
        AtomicInteger returnValue = new AtomicInteger(this.money);
		return returnValue;
	}
}
