package org.newdawn.spaceinvaders;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame {
    private JPanel loginPanel = new JPanel(new GridLayout(4, 4));
    private JLabel idLabel = new JLabel("아이디 ");
    private JLabel pwLabel = new JLabel("비밀번호 ");
    private JTextField idText = new JTextField();
    private JPasswordField pwText = new JPasswordField();
    private JButton loginBtn = new JButton("로그인");
    private JButton memberbtn = new JButton("회원가입");

    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    public Login() {
        super("로그인 창!");

        this.setContentPane(loginPanel);
        loginPanel.add(idLabel);
        loginPanel.add(pwLabel);
        loginPanel.add(idText);
        loginPanel.add(pwText);
        loginPanel.add(loginBtn);
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
        globalStorage = GlobalStorage.getInstance();

        //로그인 버튼을 눌렀을때
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText().trim();
                String pw = pwText.getText().trim();

                if (id.length() == 0 || pw.length() == 0) {
                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력 하셔야 됩니다.", "아이디나 비번을 입력!", JOptionPane.DEFAULT_OPTION);
                    return;
                }

                if (firebaseTool.Login(id, pw)) {
                    setVisible(false);
                }

            }
        });
        //회원가입 버튼을 눌렀을때
        memberbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Signup signup = new Signup();
                signup.setVisible(true);


            }
        });

    }
}