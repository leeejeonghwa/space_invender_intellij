package org.newdawn.spaceinvaders;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class mainwindow extends JFrame {
    private JPanel panel;

    public mainwindow() {


        // 메인 윈도우 설정
        setTitle("SPACE INVANDERS");

        //현재 프레임 창 가운데 정렬 setSize를 먼저 해주어야 정상적으로 프레임이 가운데 정렬이 됨!
        setSize(800,600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 패널 생성 및 시작 버튼 추가
        createPanel();

        // 메인 윈도우에 패널 추가
        getContentPane().add(panel);

        // 메인 윈도우 크기 조정 및 표시
        pack();
        setVisible(true);
    }

    private void createPanel() {
        // 패널 생성
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("src/image/background.png"); // 이미지 파일
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // 레이아웃 매니저를 사용하지 않음
        panel.setPreferredSize(new Dimension(800, 600));

        // 시작 버튼 생성

        ImageIcon start = new ImageIcon();
        ImageIcon rule = new ImageIcon();
        ImageIcon level1 = new ImageIcon();
        ImageIcon level2 = new ImageIcon();
        ImageIcon level3 = new ImageIcon();
        ImageIcon level4 = new ImageIcon();


        JButton startButton = new JButton("시작");
        startButton.setBounds(350, 250, 100, 50);
        startButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // 버튼 및 레이아웃 관련 처리
                startButton.setVisible(false);
                setLayout(null);

                // 게임 루프를 실행하는 스레드 생성
                Thread gameThread = new Thread(new Runnable() {
                    public void run() {
                        Game g = new Game();
                        g.gameLoop();
                    }
                });
                gameThread.start();
            }

        });


        // 패널에 시작 버튼 추가
        panel.add(startButton);
    }

//    public JPanel getPanel() {
//        return panel;
//    }

    public static void main(String[] args) {

        mainwindow mainWindow = new mainwindow(); // 메인 윈도우 객체 생성
//        new Login();
//
//        FirebaseTool firebaseTool = new FirebaseTool();

    }
}
