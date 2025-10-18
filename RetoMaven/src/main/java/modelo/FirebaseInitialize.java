package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Clase abstracta para la gestión de Firebase. Todos los DAOs que accedan a
 * Firestore deben heredar de esta clase. Así aseguramos que la conexión siempre
 * está inicializada antes de acceder.
 */
public abstract class FirebaseInitialize {

	private static final String DATABASE_URL = "https://spinningcat-cac21-default-rtdb.europe-west1.firebasedatabase.app";
	private static final String CREDENTIALS_PATH = "privateKey.json";

	protected FirebaseInitialize() {
		initializeFirebase();
	}

	/**
	 * Inicializa Firebase si aún no está inicializado. Se asegura que SOLO se
	 * inicialice una vez.
	 */
	private void initializeFirebase() {
		if (FirebaseApp.getApps().isEmpty()) {
			InputStream serviceAccount = null;
			try {
				serviceAccount = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_PATH);

				if (serviceAccount == null) {
					throw new IOException("No se encontró el archivo de credenciales: " + CREDENTIALS_PATH);
				}

				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl(DATABASE_URL)
						.build();

				FirebaseApp.initializeApp(options);
				System.out.println("Firebase inicializado correctamente");
			} catch (IOException e) {
				System.err.println("Error al inicializar Firebase: " + e.getMessage());
			} finally {
				if (serviceAccount != null) {
					try {
						serviceAccount.close();
					} catch (IOException ignore) {
					}
				}
			}
		}
	}
}

/*
 * 
 * Primera versión de la clase FirebaseInitialize
 * 
 * Clase de inicialización de Firebase para Firestore.
 */
//public class FirebaseInitialize {
//
//    // Este método debe llamarse una sola vez al iniciar la app
//    @SuppressWarnings("deprecation")
//	public static void initialize() {
//        if (FirebaseApp.getApps().isEmpty()) {
//            try {
//                FileInputStream serviceAccount = new FileInputStream("privateKey.json");
//
//                FirebaseOptions options = new FirebaseOptions.Builder()
//                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                        .setDatabaseUrl("https://spinningcat-cac21-default-rtdb.europe-west1.firebasedatabase.app")
//                        .build();
//
//                FirebaseApp.initializeApp(options);
//                System.out.println("Firebase inicializado correctamente");
//            } catch (IOException e) {
//                System.err.println("Error al inicializar Firebase: " + e.getMessage());
//            }
//        }
//    }
//
//}