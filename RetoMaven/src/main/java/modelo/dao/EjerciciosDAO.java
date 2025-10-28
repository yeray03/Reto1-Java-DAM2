package modelo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import modelo.FirebaseInitialize;
import pojos.Ejercicio;

public class EjerciciosDAO extends FirebaseInitialize {

	public EjerciciosDAO() {
		super();
	}
//	public void registrarEjercicio(Usuario usuario) throws Exception {
//		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
//		db.collection("usuarios").document(usuario.getEmail()).collection("prueba").document("idprueba").set(historico);
//		//.set(usuario); // crea el documento en usuarios y usa el email como ID
//	}

	public ArrayList<Ejercicio> getEjercicios() throws IOException, InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();

		ApiFuture<QuerySnapshot> snapshot = db.collection("ejercicios").get();
		QuerySnapshot querySnapshot = snapshot.get();

		for (DocumentSnapshot documento : querySnapshot.getDocuments()) {
			Ejercicio ejercicio = documento.toObject(Ejercicio.class);
			listaEjercicios.add(ejercicio);
		}
		return listaEjercicios;
	}

	// Buscar ejercicio por nombre
	public ArrayList<Ejercicio> getEjercicioByNombre(String nombre)
			throws IOException, InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ApiFuture<QuerySnapshot> query = db.collection("ejercicios").whereEqualTo("nombre", nombre).get();
		QuerySnapshot querySnapshot = query.get();

		ArrayList<Ejercicio> workouts = new ArrayList<Ejercicio>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			workouts.add(document.toObject(Ejercicio.class));
		}
		return workouts;

	}

}
