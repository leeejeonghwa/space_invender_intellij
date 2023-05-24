package org.newdawn.spaceinvaders;

import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {
    private final JTextField idText = new JTextField();
    private final JPasswordField pwText = new JPasswordField();

    private final FirebaseTool firebaseTool;

    public Login() {
        super("로그인 창!");

        JPanel loginPanel = new JPanel(new GridLayout(4, 4));
        this.setContentPane(loginPanel);
        JLabel idLabel = new JLabel("아이디 ");
        loginPanel.add(idLabel);
        JLabel pwLabel = new JLabel("비밀번호 ");
        loginPanel.add(pwLabel);
        loginPanel.add(idText);
        loginPanel.add(pwText);
        JButton loginBtn = new JButton("로그인");
        loginPanel.add(loginBtn);
        JButton memberbtn = new JButton("회원가입");
        loginPanel.add(memberbtn);

        //라벨 가운데 정렬
        idLabel.setHorizontalAlignment(NORMAL);
        pwLabel.setHorizontalAlignment(NORMAL);

        //현재 프레임 창 가운데 정렬 setSize를 먼저 해주어야 정상적으로 프레임이 가운데 정렬이 됨!
        setSize(350, 150);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        firebaseTool = FirebaseTool.getInstance();
        GlobalStorage globalStorage = GlobalStorage.getInstance();

        //로그인 버튼을 눌렀을때
        loginBtn.addActionListener(e -> {
            String id = idText.getText().trim();
            String pw = pwText.getText().trim();

            if (id.length() == 0 || pw.length() == 0) {
                JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력 하셔야 됩니다.", "아이디나 비번을 입력!", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            if (firebaseTool.Login(id, pw)) {
                setVisible(false);
            }

        });
        //회원가입 버튼을 눌렀을때
        memberbtn.addActionListener(e -> {
            Signup signup = new Signup();
            signup.setVisible(true);


        });

    }
}