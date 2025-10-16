package src.modelo;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/*
 * Clase de inicialización de Firebase para Firestore.
 */
public class FirebaseInitialize {

    // Este método debe llamarse una sola vez al iniciar la app
    public static void initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                FileInputStream serviceAccount = new FileInputStream("privateKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://spinningcat-cac21-default-rtdb.europe-west1.firebasedatabase.app")
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado correctamente");
            } catch (IOException e) {
                System.err.println("Error al inicializar Firebase: " + e.getMessage());
            }
        }
    }

}