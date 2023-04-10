package org.newdawn.spaceinvaders;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    private JPanel panel;
    private JButton startbtn;
    private JButton rulebtn;
    private JButton level1btn;
    private JButton level2btn;
    private JButton level3btn;
    private JButton level4btn;
    private JButton level5btn;

    public MainWindow() {
        // 메인 윈도우 설정
        setTitle("SPACE INVANDERS");

        //현재 프레임 창 가운데 정렬 setSize를 먼저 해주어야 정상적으로 프레임이 가운데 정렬이 됨!
        setSize(800, 600);
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

        firebaseTool = FirebaseTool.getInstance();
        globalStorage = GlobalStorage.getInstance();

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
        ImageIcon start = new ImageIcon("src/image/start.png");
        Image startimg = start.getImage();
        Image startimagchange = startimg.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon startchange = new ImageIcon(startimagchange);
        startbtn = new JButton(startchange);
        startbtn.setFocusPainted(false);
        startbtn.setBorderPainted(false);
        startbtn.setContentAreaFilled(false);
        startbtn.setSize(100, 50);
        startbtn.setBounds(270, 200, 100, 50);

        //설명 버튼 생성
        ImageIcon rule = new ImageIcon("src/image/rule.png");
        Image ruleimg = rule.getImage();
        Image ruleimagchange = ruleimg.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon rulechange = new ImageIcon(ruleimagchange);
        rulebtn = new JButton(rulechange);
        rulebtn.setFocusPainted(false);
        rulebtn.setBorderPainted(false);
        rulebtn.setContentAreaFilled(false);
        rulebtn.setSize(100, 50);
        rulebtn.setBounds(400, 200, 100, 50);

        //level1 버튼 생성
        ImageIcon level1 = new ImageIcon("src/image/level1.png");
        Image level1img = level1.getImage();
        Image level1imagchange = level1img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon level1change = new ImageIcon(level1imagchange);
        level1btn = new JButton(level1change);
        level1btn.setFocusPainted(false);
        level1btn.setBorderPainted(false);
        level1btn.setContentAreaFilled(false);
        level1btn.setSize(100, 50);
        level1btn.setBounds(120, 400, 100, 50);

        //level2 버튼 생성
        ImageIcon level2 = new ImageIcon("src/image/level2.png");
        Image level2img = level2.getImage();
        Image level2imagchange = level2img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon level2change = new ImageIcon(level2imagchange);
        level2btn = new JButton(level2change);
        level2btn.setFocusPainted(false);
        level2btn.setBorderPainted(false);
        level2btn.setContentAreaFilled(false);
        level2btn.setSize(100, 50);
        level2btn.setBounds(230, 400, 100, 50);

        //level3 버튼 생성
        ImageIcon level3 = new ImageIcon("src/image/level3.png");
        Image level3img = level3.getImage();
        Image level3imagchange = level3img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon level3change = new ImageIcon(level3imagchange);
        level3btn = new JButton(level3change);
        level3btn.setFocusPainted(false);
        level3btn.setBorderPainted(false);
        level3btn.setContentAreaFilled(false);
        level3btn.setSize(100, 50);
        level3btn.setBounds(340, 400, 100, 50);

        //level4 버튼 생성
        ImageIcon level4 = new ImageIcon("src/image/level4.png");
        Image level4img = level4.getImage();
        Image level4imagchange = level4img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon level4change = new ImageIcon(level4imagchange);
        level4btn = new JButton(level4change);
        level4btn.setFocusPainted(false);
        level4btn.setBorderPainted(false);
        level4btn.setContentAreaFilled(false);
        level4btn.setSize(100, 50);
        level4btn.setBounds(460, 400, 100, 50);

        //level5 버튼 생성
        ImageIcon level5 = new ImageIcon("src/image/level5.png");
        Image level5img = level5.getImage();
        Image level5imagchange = level5img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon level5change = new ImageIcon(level5imagchange);
        level5btn = new JButton(level5change);
        level5btn.setFocusPainted(false);
        level5btn.setBorderPainted(false);
        level5btn.setContentAreaFilled(false);
        level5btn.setSize(100, 50);
        level5btn.setBounds(570, 400, 100, 50);


        startbtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // 버튼 및 레이아웃 관련 처리
                startbtn.setVisible(true);
                setLayout(null);

                firebaseTool.GetUserBestScore(globalStorage.getUserID());

                JOptionPane.showMessageDialog(null, globalStorage.getUserID() + " 님 최고점수 : " + globalStorage.getUserBestScore());

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
        panel.add(startbtn);
        panel.add(rulebtn);
        panel.add(level1btn);
        panel.add(level2btn);
        panel.add(level3btn);
        panel.add(level4btn);
        panel.add(level5btn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow(); // 메인 윈도우 객체 생성
                Login login = new Login();
            }
        });
    }
}
