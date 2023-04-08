package org.newdawn.spaceinvaders;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

//새로 추가 한것
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseTool {
    private static final String DOMAIN_NAME = "space-invander-member-list.firebaseapp.com";
    private static final String KEY_LOCATION = "\\resource\\key.json";

    private static FirebaseApp firebaseApp;

    //새로 추가 한것
 //   private static DatabaseReference dbRef;

    public FirebaseTool() {
        initialize();
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
            }

            //새로 추가 한거
          //  dbRef = FirebaseDatabase.getInstance().getReference("https://space-invander-member-list-default-rtdb.firebaseio.com/");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //새로 추가한거
//    public static DatabaseReference getDbRef() {
//       return dbRef;
 //   }

    public static FirebaseApp getFirebaseApp() {
        return firebaseApp;
    }
}
