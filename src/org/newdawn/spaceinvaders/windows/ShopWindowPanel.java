package org.newdawn.spaceinvaders.windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ShopWindowPanel extends JPanel {
    private Image backgroundImage;

    public ShopWindowPanel() {
        super();
        setPreferredSize(new Dimension(800, 600));
        try {
            backgroundImage = ImageIO.read(new File("src/sprites/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }
}
