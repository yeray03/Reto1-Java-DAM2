package modelo;

//import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FireStoreManager {
    private static Firestore db;
    public static Firestore getDB() throws IOException {
        if (db == null) {
            FileInputStream serviceAccount = new FileInputStream("serviceAccount.json");
            FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId("Spinnningcat")
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            db = firestoreOptions.getService();
        }
        return db;
    }
}
