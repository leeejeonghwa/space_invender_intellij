package org.newdawn.spaceinvaders.cores;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Signup extends JDialog {

    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    private JPanel signUpPanel = new JPanel(new GridLayout(11, 0));
    private JTextField idText = new JTextField("아이디");
    private JPasswordField pwText = new JPasswordField();
    private JPasswordField pwCheckText = new JPasswordField();
    private JTextField nameText = new JTextField("이름");
    private JButton signUpbtn = new JButton("회원가입");
    private JLabel idlabel = new JLabel("아이디");
    private JLabel pwlabel = new JLabel("비밀번호");
    private JLabel pwChecklabel = new JLabel("비밀번호 확인");
    private boolean membershipProgress = false;

    public Signup() {

        this.setTitle("회원가입");

        this.signUpPanel.add(idlabel);
        this.signUpPanel.add(idText);
        this.signUpPanel.add(pwlabel);
        this.signUpPanel.add(pwText);
        this.signUpPanel.add(pwChecklabel);
        this.signUpPanel.add(pwCheckText);
        this.signUpPanel.add(nameText);
        this.signUpPanel.add(signUpbtn);

        this.setContentPane(signUpPanel);
        this.setSize(300, 500);
        this.setLocationRelativeTo(null);

        FocusEvent();
        checkValue();

        firebaseTool = FirebaseTool.getInstance();
        globalStorage = GlobalStorage.getInstance();
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
        signUpbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }
}

