package org.newdawn.spaceinvaders;


import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Objects;

public class MainWindow extends JFrame {

    private static final Item item = new Item();
    
    private final FirebaseTool firebaseTool;

    private final GlobalStorage globalStorage;

    private JPanel panel;

    public MainWindow() {
        // 메인 윈도우 설정
        setTitle("SPACE INVADERS");

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

    private JButton drawButton(String ref, int x, int y){
        ImageIcon buttonIcon = new ImageIcon(ref);
        Image buttonimg = buttonIcon.getImage();
        Image buttonimgchange = buttonimg.getScaledInstance(150, 65, Image.SCALE_SMOOTH);
        ImageIcon buttonchange = new ImageIcon(buttonimgchange);
        JButton button = new JButton(buttonchange);
        button.setName(ref);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setSize(150, 65);
        button.setBounds(x, y, 150, 65);

        return button;
    }

    public void btnMouseListener(JButton button){
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(button.getName(), "src/image/shop.png")){
                    button.setVisible(true);
                    setLayout(null);
                    Thread shopThread = new Thread(() -> {
                        System.out.print("shop Thread: " + Arrays.toString(item.enableItems())+ item.getMoney() + "\n");
                        ShopWindow s = new ShopWindow(item.getMoney(), item.enableItems(), item.enableSkinList(), item.getActiveNum());
                        s.shopLoop();
                        synchronized(item){
                            item.setMoney(s.recieveMoney());
                            item.setEnableSkin(s.getEnableSkin());
                            item.activateSkinNumber(s.getSelectedSkin());
                        }
                    });
                    shopThread.start();
                }
                else if (Objects.equals(button.getName(), "src/image/rule.png")){
                    button.setVisible(true);
                    setLayout(null);
                    Thread ruleThread = new Thread(() -> {
                        RuleWindow r = new RuleWindow();
                        r.ruleLoop();
                    });
                    ruleThread.start();
                }
                else if (Objects.equals(button.getName(), "src/image/level1.png") || Objects.equals(button.getName(), "src/image/level2.png") || Objects.equals(button.getName(), "src/image/level3.png") || Objects.equals(button.getName(), "src/image/level4.png") || Objects.equals(button.getName(), "src/image/level5.png")) {
                    // level 버튼 누른 경우
                    button.setVisible(true);
                    setLayout(null);
                    firebaseTool.GetUserBestScore(globalStorage.getUserID());
                    JOptionPane.showMessageDialog(null, globalStorage.getUserID() + " 님 최고점수 : " + globalStorage.getUserBestScore());
                    // 게임 루프를 실행하는 스레드 생성
                    Thread gameThread = new Thread(() -> {
                        System.out.print("game Thread: " + Arrays.toString(item.enableItems())+ item.getMoney() + "\n");
                        Game g = new Game(button.getName(), item.enableItems(), item.getMoney(), item.getActiveNum());
                        g.gameLoop();
                        synchronized(item){
                            item.clearStage(g.getItemState());
                            item.setMoney(g.recieveMoney());
                        }
                    });
                    gameThread.start();
                }

            }
        });
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

        // shop 버튼 생성
        JButton shopbtn = drawButton("src/image/shop.png", 460, 410);
        this.btnMouseListener(shopbtn);
        //설명 버튼 생성
        JButton rulebtn = drawButton("src/image/rule.png", 460, 480);
        this.btnMouseListener(rulebtn);
        //level1 버튼 생성
        JButton level1btn = drawButton("src/image/level1.png", 630, 200);
        this.btnMouseListener(level1btn);
        //level2 버튼 생성
        JButton level2btn = drawButton("src/image/level2.png", 630, 270);
        this.btnMouseListener(level2btn);
        //level3 버튼 생성
        JButton level3btn = drawButton("src/image/level3.png", 630, 340);
        this.btnMouseListener(level3btn);
        //level4 버튼 생성
        JButton level4btn = drawButton("src/image/level4.png", 630, 410);
        this.btnMouseListener(level4btn);
        //level5 버튼 생성
        JButton level5btn = drawButton("src/image/level5.png", 630, 480);
        this.btnMouseListener(level5btn);

        // 패널에 시작 버튼 추가
        panel.add(shopbtn);
        panel.add(rulebtn);
        panel.add(level1btn);
        panel.add(level2btn);
        panel.add(level3btn);
        panel.add(level4btn);
        panel.add(level5btn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
        });
    }
}
