package borja;

import java.io.IOException;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Clase abstracta para la gestión de Firebase.
 */
public abstract class FirebaseManagerAbstract {

	protected FirebaseManagerAbstract() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
            	InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("privateKey.json");

                FirebaseOptions options = FirebaseOptions.builder()
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
