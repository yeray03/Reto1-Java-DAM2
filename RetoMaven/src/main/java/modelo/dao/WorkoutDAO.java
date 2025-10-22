package modelo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
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
			Workout workout = mapDocumentToWorkout(document);
			if (workout != null) {
				workouts.add(workout);
			}
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
			Workout workout = mapDocumentToWorkout(document);
			if (workout != null) {
				workouts.add(workout);
			}
		}
		return workouts;
	}
	
	// Mapea un DocumentSnapshot a un objeto Workout, resolviendo las referencias a Ejercicio
	//OTRA FUNCION QUE FUNCIONA GRACIAS A REZARLE AL SEÑOR
	//Y A QUE FIREBASE ES LO MEJOR QUE BIEEEEN
	//ME QUIERO SUIC
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