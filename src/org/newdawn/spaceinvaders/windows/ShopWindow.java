package org.newdawn.spaceinvaders.windows;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShopWindow extends Canvas{
    private BufferStrategy strategy;

    private JFrame container;

    private AtomicInteger money;

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

        this.money = money;
    }

    public void shopLoop(){
        while(true){
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Money: " + this.money.toString(), 10, 20);

            BufferedImage shopShip1;
            try{
                shopShip1 = ImageIO.read(new File("src/sprites/Shopship1.png"));
                g.drawImage(shopShip1,25,255,this);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip2;
            try{
                shopShip2 = ImageIO.read(new File("src/sprites/Shopship2.png"));
                g.drawImage(shopShip2,115,255,this);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip3;
            try{
                shopShip3 = ImageIO.read(new File("src/sprites/Shopship3.png"));
                g.drawImage(shopShip3,215,255,this);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip4;
            try{
                shopShip4 = ImageIO.read(new File("src/sprites/Shopship4.png"));
                g.drawImage(shopShip4,405,255,this);
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage shopShip5;
            try{
                shopShip5 = ImageIO.read(new File("src/sprites/Shopship5.png"));
                g.drawImage(shopShip5,595,255,this);
            }catch (IOException e){
                e.printStackTrace();
            }

            g.dispose();
            strategy.show();
        }
    }

	public AtomicInteger recieveMoney() {
		return this.money;
	}
}
