package org.newdawn.spaceinvaders;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RuleWindow extends JFrame{

    private JPanel panel;

    public RuleWindow(){
        setTitle("RULE");

        setSize(830, 650);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        createPanel();

        getContentPane().add(panel);
        setVisible(true);
    }

    private void createPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                Image ruleImage = new ImageIcon("src/sprites/rule.png").getImage();
                g.drawImage(ruleImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // 레이아웃 매니저를 사용하지 않음
        panel.setPreferredSize(new Dimension(800, 600));
    }
}
