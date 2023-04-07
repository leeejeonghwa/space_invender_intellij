package org.newdawn.spaceinvaders;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//새로 추가 한것
import com.google.firebase.database.*;
import com.google.firebase.database.ValueEventListener;
public class Login extends JFrame{
    private JPanel loginPanel = new JPanel(new GridLayout(4,4));
    private JLabel idLabel = new JLabel("아이디 ");
    private JLabel pwLabel = new JLabel("비밀번호 ");
    private JTextField idText = new JTextField();
    private JPasswordField pwText = new JPasswordField();
    private JButton loginBtn = new JButton("로그인");
    private JButton memberbtn = new JButton("회원가입");

    //새로 추가 한것
    private FirebaseDatabase database;
    private DatabaseReference usersRef;


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
        setSize(350,150);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //새로한 것
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        //로그인 버튼을 눌렀을때
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String id = idText.getText().trim();
                String pw = pwText.getText().trim();
                String login = "";

                if(id.length()==0 || pw.length()==0) {
                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력 하셔야 됩니다.", "아이디나 비번을 입력!", JOptionPane.DEFAULT_OPTION);
                    return;
                }

                //새로운 것
                usersRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // 사용자 정보가 존재하는 경우 비밀번호를 비교함
                            User user = dataSnapshot.getValue(User.class);
                            if (user.getPassword().equals(pw)) {
                                // 로그인 성공
                                JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 확인!", JOptionPane.DEFAULT_OPTION);
                                return;
                            }
                        }

                        JOptionPane.showMessageDialog(null, "로그인 실패", "계정 정보를 확인해 주세요.", JOptionPane.DEFAULT_OPTION);
                    }
                    public void onCancelled(DatabaseError databaseError) {
                        // 오류 처리
                        JOptionPane.showMessageDialog(null, "데이터베이스 오류", "데이터베이스 오류 발생!", JOptionPane.DEFAULT_OPTION);
                    }
                });
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


//    public static void main(String[] args) {
//        new Login();
//    }
