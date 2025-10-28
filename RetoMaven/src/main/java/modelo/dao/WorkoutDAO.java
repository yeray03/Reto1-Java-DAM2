package modelo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import pojos.Ejercicio;
import pojos.Workout;

import modelo.FirebaseInitialize;

public class WorkoutDAO extends FirebaseInitialize {

	public WorkoutDAO() {
		super();
	}

	public ArrayList<Workout> obtenerTodosWorkouts() throws IOException, ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ArrayList<Workout> listaWorkouts = new ArrayList<>();

		ApiFuture<QuerySnapshot> snapshot = db.collection("workouts").get();
		QuerySnapshot querySnapshot = snapshot.get();

		for (DocumentSnapshot documento : querySnapshot.getDocuments()) {
			Workout workout = mapDocumentToWorkout(documento);
			listaWorkouts.add(workout);
		}
		return listaWorkouts;
	}

	public ArrayList<Workout> getWorkoutsByLevel(int level)
			throws IOException, InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ApiFuture<QuerySnapshot> query = db.collection("workouts").whereEqualTo("nivel", level).get();
		QuerySnapshot querySnapshot = query.get();

		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			Workout workout = mapDocumentToWorkout(document);
			if (workout != null) {
				workouts.add(workout);
			}
		}
		return workouts;
	}

	public ArrayList<Workout> getWorkoutUntilLevel(int level)
			throws IOException, InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ApiFuture<QuerySnapshot> query = db.collection("workouts").whereLessThanOrEqualTo("nivel", level).get();
		QuerySnapshot querySnapshot = query.get();

		ArrayList<Workout> workouts = new ArrayList<Workout>();
		for (DocumentSnapshot document : querySnapshot.getDocuments()) {
			Workout workout = mapDocumentToWorkout(document);
			if (workout != null) {
				workouts.add(workout);
			}
		}
		return workouts;
	}

	// Mapea un DocumentSnapshot a un objeto Workout, resolviendo las referencias a
	// Ejercicio
	// OTRA FUNCION QUE FUNCIONA GRACIAS A REZARLE AL SEÑOR
	// Y A QUE FIREBASE ES LO MEJOR QUE BIEEEEN
	// ME QUIERO SUIC
	// Yeray: gracias por quitarme un dolor de cabeza :3
	private Workout mapDocumentToWorkout(DocumentSnapshot document) {
		try {
			Workout workout = new Workout();

			String nombre = document.getString("nombre");
			Long nivelLong = document.getLong("nivel");
			Long numEjerciciosLong = document.getLong("numEjercicios");
			String videoUrl = document.getString("videoUrl");

			workout.setNombre(nombre != null ? nombre : "");
			workout.setNivel(nivelLong != null ? nivelLong.intValue() : 0);
			workout.setNumEjercicios(numEjerciciosLong != null ? numEjerciciosLong.intValue() : 0);
			workout.setUrlVideo(videoUrl != null ? videoUrl : "");

			@SuppressWarnings("unchecked")
			List<DocumentReference> ejerciciosRefs = (List<DocumentReference>) document.get("ejercicios");
			ArrayList<Ejercicio> ejercicios = new ArrayList<>();

			if (ejerciciosRefs != null && !ejerciciosRefs.isEmpty()) {
				for (DocumentReference ref : ejerciciosRefs) {
					try {
						ApiFuture<DocumentSnapshot> ejercicioFuture = ref.get();
						DocumentSnapshot ejercicioDoc = ejercicioFuture.get();

						if (ejercicioDoc.exists()) {
							Ejercicio ejercicio = ejercicioDoc.toObject(Ejercicio.class);
							if (ejercicio != null) {
								ejercicios.add(ejercicio);
							}
						}
					} catch (Exception e) {
						System.err.println("Error al resolver ejercicio: " + ref.getPath());
						e.printStackTrace();
					}
				}
			}

			workout.setEjercicios(ejercicios);
			return workout;

		} catch (Exception e) {
			System.err.println("Error al mapear workout del documento: " + document.getId());
			e.printStackTrace();
			return null;
		}
	}
}