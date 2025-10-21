package modelo.dao;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import pojos.Workout;
import java.util.ArrayList;

import modelo.FirebaseInitialize;

public class WorkoutDAO extends FirebaseInitialize {

	public WorkoutDAO() {
		super();
	}

	private CollectionReference getWorkouts() throws IOException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		if (db == null) {
			throw new IOException("Firestore is not initialized.");
		}
		return db.collection("workouts");
	}

	public ArrayList<Workout> getWorkoutsByLevel(int level)
			throws IOException, InterruptedException, ExecutionException {
		CollectionReference workoutsRef = getWorkouts();
		ApiFuture<QuerySnapshot> query = workoutsRef.whereEqualTo("nivel", level).get();
		QuerySnapshot querySnapshot = query.get();
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			workouts.add(document.toObject(Workout.class));
		}
		return workouts;

	}

	public ArrayList<Workout> getWorkoutUntilLevel(int level)
			throws IOException, InterruptedException, ExecutionException {
		CollectionReference workoutsRef = getWorkouts();
		ApiFuture<QuerySnapshot> query = workoutsRef.whereLessThanOrEqualTo("nivel", level).get();
		QuerySnapshot querySnapshot = query.get();
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			workouts.add(document.toObject(Workout.class));
		}
		return workouts;
	}
}
//	// Añadir Workout
//	public void anyadirWorkout(Usuario usuario) throws Exception {
//		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
//		db.collection("usuarios").document(usuario.getEmail()).set(usuario); // crea el documento en usuarios y usa el email como ID
//	}
//
//	// LOGIN USUARIO
//	public Usuario buscarUsuarioPorEmail(String email) throws IOException, ExecutionException, InterruptedException {
//		
//		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
//		
//		ApiFuture<QuerySnapshot> snapshot = db.collection("usuarios").whereEqualTo("email", email).get();	
//		QuerySnapshot querySnapshot = snapshot.get();
//		
//		if (!querySnapshot.isEmpty()) {
//			DocumentSnapshot documento = querySnapshot.getDocuments().get(0);
//			return documento.toObject(Usuario.class);
//		}
//		return null;
//	}
//
//	public void guardarUsuario(Usuario usuario) {
//		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
//		ref.child(usuario.getEmail()).setValueAsync(usuario);
//		System.out.println("Usuario guardado correctamente en la BBDD");
//	}
