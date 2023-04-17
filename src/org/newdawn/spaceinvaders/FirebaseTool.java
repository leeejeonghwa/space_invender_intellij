package org.newdawn.spaceinvaders;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

//새로 추가 한것
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseTool {
    private static final String DOMAIN_NAME = "space-invander-member-list.firebaseapp.com";
    private static final String KEY_LOCATION = "\\resource\\key.json";
    private static FirebaseTool firebaseTool = null;
    private FirebaseApp firebaseApp;
    private DatabaseReference databaseReference;

    private GlobalStorage globalStorage;

    public static FirebaseTool getInstance() {
        if (firebaseTool == null) {
            firebaseTool = new FirebaseTool();
        }

        return firebaseTool;
    }

    private FirebaseTool() {
        initialize();
        globalStorage = GlobalStorage.getInstance();
    }

    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream(System.getProperty("user.dir") + KEY_LOCATION);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://space-invander-member-list-default-rtdb.firebaseio.com/")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options, "space-invander-member-list");

            if (firebaseApp != null) {
                System.out.println(firebaseApp.getName());

                databaseReference = FirebaseDatabase.getInstance(firebaseApp).getReference();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean Login(String id, String password) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);

            UserRecord userRecord = firebaseAuth.getUserByEmail(id);

            if (userRecord != null) {
                if (userRecord.getEmail().equals(id)) {
                    globalStorage.setUserID(id);
                    GetUserBestScore(id);
                    JOptionPane.showMessageDialog(null, "로그인이 정상적으로 처리되었습니다.");
                    MainWindow mainWindow = new MainWindow(); // 메인 윈도우 객체 생성
                    return true;
                }
            }

        } catch (NullPointerException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확입하세요.");
            return false;
        } catch (FirebaseAuthException e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요.");
            return false;
        }

        return false;
    }

    public void Signup(String id, String password) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
            firebaseAuth.createUser(new UserRecord.CreateRequest()
                    .setEmail(id)
                    .setEmailVerified(false)
                    .setPassword(password)
                    .setDisplayName(id));

            JOptionPane.showMessageDialog(null, "회원가입에 정상적으로 처리되었습니다.");

        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "회원가입에 문제가 생겼습니다.");
        } catch (FirebaseAuthException e) {
            JOptionPane.showMessageDialog(null, "회원가입에 문제가 생겼습니다.");
            throw new RuntimeException(e);
        }
    }

    public String GetUserBestScore(String id) {
        String userBestScore = "";

        try {
            DatabaseReference userScoreDatabase = FirebaseDatabase.getInstance(firebaseApp).getReference();

            userScoreDatabase.child("user").child(id.split("@")[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String score = dataSnapshot.getValue(String.class);
                    System.out.println("Data Receivced " + score);
                    globalStorage.setUserBestScore(score);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Data Error " + databaseError);

                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return userBestScore;
    }

    public void SetUserBestScore(String id, String bestscore) {
        try {
            DatabaseReference userScoreDatabase = FirebaseDatabase.getInstance(firebaseApp).getReference();

            userScoreDatabase.child("user").child(id.split("@")[0]).setValue(bestscore, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }


}