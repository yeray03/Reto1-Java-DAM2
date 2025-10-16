package src.modelo;

import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

/**
 * Clase de acceso a los servicios de Firebase, en especial Firestore.
 */
public class FirebaseServices {

	/**
	 * Obtiene la instancia de Firestore ya inicializada.
	 * 
	 * @return Firestore
	 */
	public static Firestore getFirestore() {
		return FirestoreClient.getFirestore(FirebaseApp.getInstance());
	}

	public static DocumentReference getDB() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	// Aquí podrías añadir métodos para otros servicios: Storage, Auth, etc.
	
	
}