package org.newdawn.spaceinvaders.windows;

import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RuleWindow extends Canvas{
    private BufferStrategy strategy;

    private JFrame container;

    public RuleWindow(){
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

//        container.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });

        requestFocus();

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void ruleLoop(){
        while(true){
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Created by leeejeonghwa, Indigo_Coder, zlatjdus in Github", 240, 300);

            g.dispose();
            strategy.show();
        }
    }
}
