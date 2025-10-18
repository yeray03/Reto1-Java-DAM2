package modelo.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modelo.FirebaseInitialize;
import pojos.Usuario;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

// Hacer que extienda FirebaseManagerAbstract (así usamos el patrón factory)
public class UsuarioDAO extends FirebaseInitialize {

	public UsuarioDAO() {
		super();
	}

	private CollectionReference getUsuarios() throws IOException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		if (db == null) {
			throw new IOException("Firestore is not initialized.");
		}
		return db.collection("usuarios");
	}

	// REGISTRO USUARIO
	public void registrarUsuario(Usuario usuario) throws Exception {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		db.collection("usuarios").document(usuario.getEmail()).set(usuario);
	}

	// LOGIN USUARIO
	public Usuario buscarUsuarioPorEmail(String email) throws IOException, ExecutionException, InterruptedException {
		
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		
		ApiFuture<QuerySnapshot> snapshot = db.collection("usuarios").whereEqualTo("email", email).get();	
		QuerySnapshot querySnapshot = snapshot.get();
		
		if (!querySnapshot.isEmpty()) {
			DocumentSnapshot documento = querySnapshot.getDocuments().get(0);
			return documento.toObject(Usuario.class);
		}
		return null;
	}

	public void guardarUsuario(Usuario usuario) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
		ref.child(usuario.getEmail()).setValueAsync(usuario);
		System.out.println("Usuario guardado correctamente en la BBDD");
	}
}
