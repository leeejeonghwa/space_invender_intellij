package org.newdawn.spaceinvaders;


import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private static Item item = new Item();
    
    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    private JPanel panel;

    //shopbtn, rulebtn, level1, level2, level3, level4, levle5
    private ArrayList<JButton> btnList = new ArrayList<>();

    public MainWindow() {
        // 메인 윈도우 설정
        setTitle("SPACE INVADERS");

        //현재 프레임 창 가운데 정렬 setSize를 먼저 해주어야 정상적으로 프레임이 가운데 정렬이 됨!
        setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 패널 생성, 버튼생성
        createPanel();
        createButtons();

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
                Image backgroundImage = new ImageIcon("src/image/background.png").getImage(); // 이미지 파일
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // 레이아웃 매니저를 사용하지 않음
        panel.setPreferredSize(new Dimension(800, 600));
    }

    private void createButtons() {
        String[] srcList = new String[]{"shop", "rule", "level1", "level2", "level3", "level4", "level5"};
        Integer[] xList = new Integer[]{460, 460, 630, 630, 630, 630, 630};
        Integer[] yList = new Integer[]{410, 480, 200, 270, 340, 410, 480};

        for (int i=0;i<7;i+=1){
            btnList.add(drawButton("src/image/"+srcList[i]+".png", 150, 65, xList[i], yList[i]));
            panel.add(btnList.get(i));
            this.btnMouseListener(btnList.get(i));
        }
    }

    private JButton drawButton(String ref, int width, int height, int x, int y){
        ImageIcon buttonIcon = new ImageIcon(ref);
        Image buttonimg = buttonIcon.getImage();
        Image buttonimgchange = buttonimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon buttonchange = new ImageIcon(buttonimgchange);
        JButton button = new JButton(buttonchange);
        button.setName(ref);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setSize(width, height);
        button.setBounds(x, y, width, height);

        return button;
    }

    public void btnMouseListener(JButton button){
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (button.getName().equals("src/image/shop.png")){
                    button.setVisible(true);
                    setLayout(null);
                    Thread shopThread = new Thread(new Runnable() {
                        public void run() {
                            ShopWindow s = new ShopWindow(item.getMoney(), item.enableItems(), item.enableSkinList(), item.getActiveNum());
                            synchronized(item){
                                item.setMoney(s.recieveMoney());
                                item.setEnableSkin(s.getEnableSkin());
                                item.activateSkinNumber(s.getSelectedSkin());
                            }
                        }
                    }); shopThread.start();
                }
                else if (button.getName().equals("src/image/rule.png")){
                    button.setVisible(true);
                    setLayout(null);
                    Thread ruleThread = new Thread(new Runnable() {
                        public void run() {
                            RuleWindow r = new RuleWindow();
                        }
                    }); ruleThread.start();
                }
                else{
                    // level 버튼 누른 경우
                    button.setVisible(true);
                    setLayout(null);
                    firebaseTool.GetUserBestScore(globalStorage.getUserID());
                    JOptionPane.showMessageDialog(null, globalStorage.getUserID() + " 님 최고점수 : " + globalStorage.getUserBestScore());
                    // 게임 루프를 실행하는 스레드 생성
                    Thread gameThread = new Thread(new Runnable() {
                        public void run() {
                            Game g = new Game(button.getName(), item.enableItems(), item.getMoney(), item.getActiveNum());
                            g.gameLoop();
                            synchronized(item){
                                item.clearStage(g.getItemState());
                                item.setMoney(g.recieveMoney());
                            }
                        }
                    }); gameThread.start();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login login = new Login();
            }
        });
    }
}
