package org.newdawn.spaceinvaders;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Signup extends JDialog {

    private final FirebaseTool firebaseTool;

    private final JTextField idText = new JTextField("아이디");
    private final JPasswordField pwText = new JPasswordField();
    private final JPasswordField pwCheckText = new JPasswordField();
    private final JTextField nameText = new JTextField("이름");
    private final JButton signUpbtn = new JButton("회원가입");
    private boolean membershipProgress = false;

    public Signup() {

        this.setTitle("회원가입");

        JPanel signUpPanel = new JPanel(new GridLayout(11, 0));
        JLabel idlabel = new JLabel("아이디");
        signUpPanel.add(idlabel);
        signUpPanel.add(idText);
        JLabel pwlabel = new JLabel("비밀번호");
        signUpPanel.add(pwlabel);
        signUpPanel.add(pwText);
        JLabel pwChecklabel = new JLabel("비밀번호 확인");
        signUpPanel.add(pwChecklabel);
        signUpPanel.add(pwCheckText);
        signUpPanel.add(nameText);
        signUpPanel.add(signUpbtn);

        this.setContentPane(signUpPanel);
        this.setSize(300, 500);
        this.setLocationRelativeTo(null);

        FocusEvent();
        checkValue();

        firebaseTool = FirebaseTool.getInstance();
        GlobalStorage globalStorage = GlobalStorage.getInstance();
    }

    //텍스트 필드에 있는 값을 체크하고 지우기 위한 메소드
    private void FocusEvent() {
        idText.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                if (idText.getText().trim().length() == 0) {
                    idText.setText("아이디");
                }
            }

            public void focusGained(FocusEvent e) {
                if (idText.getText().trim().equals("아이디")) {
                    idText.setText("");
                }
            }
        });

        nameText.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                if (nameText.getText().trim().length() == 0) {
                    nameText.setText("이름");
                }
            }

            public void focusGained(FocusEvent e) {
                if (nameText.getText().trim().equals("이름")) {
                    nameText.setText("");
                }
            }
        });

    }

    //회원 가입할때 모든 값이 입력되었는지 체크하기 위한 메소드
    private void checkValue() {
        signUpbtn.addActionListener(e -> {
            if (idText.getText().trim().length() == 0 || idText.getText().trim().equals("아이디")) {
                JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.", "아이디 입력", JOptionPane.WARNING_MESSAGE);
                idText.grabFocus();
                return;
            }

            if (pwText.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.", "비밀번호 입력", JOptionPane.WARNING_MESSAGE);
                pwText.grabFocus();
                return;
            }

            if (pwCheckText.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해 주세요.", "비밀번호 확인 입력", JOptionPane.WARNING_MESSAGE);
                pwCheckText.grabFocus();
                return;
            }

            if (!(pwText.getText().trim().equals(pwCheckText.getText().trim()))) {
                JOptionPane.showMessageDialog(null, "비밀번호가 같지 않습니다.!!", "비밀번호 확인", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nameText.getText().trim().length() == 0 || nameText.getText().trim().equals("이름")) {
                JOptionPane.showMessageDialog(null, "이름을 입력해 주세요.", "이름 입력", JOptionPane.WARNING_MESSAGE);
                nameText.grabFocus();
                return;
            }

            //여기까지 왔다면 모든 값을 입력하고 선택하는 것이 완료되었으니 회원가입 과정이 완료.
            membershipProgress = true;

            firebaseTool.Signup(idText.getText(), pwText.getText());

            setVisible(false);
        });
    }
}

