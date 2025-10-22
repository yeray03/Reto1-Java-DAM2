package modelo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import modelo.FirebaseInitialize;
import pojos.Ejercicio;
import pojos.Usuario;

public class EjerciciosDAO extends FirebaseInitialize {

	public EjerciciosDAO() {
		super();
	}
//	public void registrarEjercicio(Usuario usuario) throws Exception {
//		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
//		db.collection("usuarios").document(usuario.getEmail()).collection("prueba").document("idprueba").set(historico);
//		//.set(usuario); // crea el documento en usuarios y usa el email como ID
//	}
	
	private CollectionReference getEjercicios() throws IOException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		if (db == null) {
			throw new IOException("Firestore is not initialized.");
		}
		return db.collection("ejercicios");
	}
	
	// Buscar ejercicio por nombre
	public ArrayList<Ejercicio> getEjercicioByNombre(String nombre) throws IOException, InterruptedException, ExecutionException {
		CollectionReference ejerciciosRef = getEjercicios();
		ApiFuture<QuerySnapshot> query = ejerciciosRef.whereEqualTo("nombre", nombre).get();
		QuerySnapshot querySnapshot = query.get();
		
		ArrayList<Ejercicio> workouts = new ArrayList<Ejercicio>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			workouts.add(document.toObject(Ejercicio.class));
		}
		return workouts;

	}


}
