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
    }

    public void shopLoop(){
        while(true){
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Money: " + Integer.toString(this.money), 10, 20);

            BufferedImage shopShip1;
            try{
                shopShip1 = ImageIO.read(new File("src/sprites/Shopship1.png"));
                g.drawImage(shopShip1,25,255,this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 200",43,375);
                addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    // Check if the click event occurred within the bounds of shopShip1
                    if (e.getX() >= 25 && e.getX() <= 25 + shopShip1.getWidth() &&
                            e.getY() >= 255 && e.getY() <= 255 + shopShip1.getHeight()) {
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
                        } else{
                            
                        }
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

            BufferedImage shopShip2;
            try{
                shopShip2 = ImageIO.read(new File("src/sprites/Shopship2.png"));
                g.drawImage(shopShip2,190,255,this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 300",208,375);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip3;
            try{
                shopShip3 = ImageIO.read(new File("src/sprites/Shopship3.png"));
                g.drawImage(shopShip3,355,255,this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 500",373,375);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip4;
            try{
                shopShip4 = ImageIO.read(new File("src/sprites/Shopship4.png"));
                g.drawImage(shopShip4,520,255,this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 700",538,375);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip5;
            try{
                shopShip5 = ImageIO.read(new File("src/sprites/Shopship5.png"));
                g.drawImage(shopShip5,685,255,this);

                g.setColor(Color.WHITE);
                g.drawString("Price: 1100",700,375);
            }catch (IOException e){
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
