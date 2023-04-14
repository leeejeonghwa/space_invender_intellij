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
import javax.swing.*;

public class ShopWindow extends Canvas {
    private BufferStrategy strategy;

    private JFrame container;

    private int money;


    public ShopWindow(AtomicInteger money) {

        container = new JFrame();

        ShopWindowPanel Panel = new ShopWindowPanel(); // 새로운 클래스의 인스턴스를 만든다.
        container.add(Panel); // JFrame에 추가한다.

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - container.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - container.getHeight()) / 2);
        container.setLocation(centerX, centerY);

        JLabel shopimg = new JLabel();
        ImageIcon shop = new ImageIcon("src/sprites/shop.png");
        shopimg.setIcon(shop);
        shopimg.setBounds(300, 40, 226, 73);
        container.getContentPane().add(shopimg);

//        JLabel background = new JLabel();
//        ImageIcon backgroundImage = new ImageIcon("src/sprites/shopbackground.png");
//        background.setIcon(backgroundImage);
//        background.setBounds(0, 0, 800, 600);
//        container.getContentPane().add(background);


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
    }

    public void shopLoop() {
        while (true) {
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
//            g.setColor(Color.gray);
//            g.fillRect(0, 0, 800, 600);
            BufferedImage shopbackground;
            try {
                shopbackground = ImageIO.read(new File("src/sprites/shopbackground.png"));
                g.drawImage(shopbackground, 0, 0, this);

            } catch (IOException e) {
                e.printStackTrace();
            }

            g.setColor(Color.WHITE);
            g.drawString("Money: " + Integer.toString(this.money), 10, 20);


            BufferedImage shopShip1;
            try {
                shopShip1 = ImageIO.read(new File("src/sprites/Shopship1.png"));
                g.drawImage(shopShip1, 100, 200, this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 200", 118, 320);
                addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        // Check if the click event occurred within the bounds of shopShip1
                        if (e.getX() >= 100 && e.getX() <= 100 + shopShip1.getWidth() &&
                                e.getY() >= 200 && e.getY() <= 200 + shopShip1.getHeight()) {
                            int response = JOptionPane.showConfirmDialog(ShopWindow.this,
                                    "200 money를 주고 이 스킨을 구매할까요?", "구매 확인",
                                    JOptionPane.YES_NO_OPTION);
                            if (response == JOptionPane.YES_OPTION) {
                                // Deduct the cost of the ship from the player's money
                                if (money >= 200) {
                                    money -= 200;
                                    // TODO: Add code to actually purchase the ship
                                } else {
                                    JOptionPane.showMessageDialog(ShopWindow.this,
                                            "이 스킨을 사기에 충분한 money가 없습니다!", "잔액 부족",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {

                            }
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedImage shopShip2;
            try {
                shopShip2 = ImageIO.read(new File("src/sprites/Shopship2.png"));
                g.drawImage(shopShip2, 360, 200, this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 300", 378, 320);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedImage shopShip3;
            try {
                shopShip3 = ImageIO.read(new File("src/sprites/Shopship3.png"));
                g.drawImage(shopShip3, 620, 200, this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 500", 638, 320);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedImage shopShip4;
            try {
                shopShip4 = ImageIO.read(new File("src/sprites/Shopship4.png"));
                g.drawImage(shopShip4, 230, 400, this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 700", 248, 520);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedImage shopShip5;
            try {
                shopShip5 = ImageIO.read(new File("src/sprites/Shopship5.png"));
                g.drawImage(shopShip5, 490, 400, this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 1100", 508, 520);
            } catch (IOException e) {
                e.printStackTrace();
            }

            g.dispose();
            strategy.show();
        }
    }

    public AtomicInteger recieveMoney() {
        AtomicInteger returnValue = new AtomicInteger(this.money);
        return returnValue;
    }

}