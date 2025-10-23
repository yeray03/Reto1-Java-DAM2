package modelo.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;

import modelo.FirebaseInitialize;
import pojos.Usuario;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class UsuarioDAO extends FirebaseInitialize {

	public UsuarioDAO() {
		super();
	}

	public void registrarUsuario(Usuario usuario) throws Exception {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());

		if (usuario.getNivel() < 0 || usuario.getNivel() > 5) {
			usuario.setNivel(0);
		}

		String emailNormalizado = usuario.getEmail().toLowerCase().trim();
		usuario.setEmail(emailNormalizado);

		db.collection("usuarios").document(emailNormalizado).set(usuario);
	}

	public Usuario buscarUsuarioPorNick(String nickname) throws IOException, ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());

		String nickNormalizado = nickname.trim();

		ApiFuture<QuerySnapshot> snapshot = db.collection("usuarios").whereEqualTo("nickname", nickNormalizado).get();
		QuerySnapshot querySnapshot = snapshot.get();

		if (!querySnapshot.isEmpty()) {
			DocumentSnapshot documento = querySnapshot.getDocuments().get(0);
			return documento.toObject(Usuario.class);
		}
		return null;
	}

	public void guardarUsuario(Usuario usuario) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
		ref.child(usuario.getNickname()).setValueAsync(usuario);
		System.out.println("Usuario guardado correctamente en la BBDD");
	}

	public void editarUsuario(Usuario usuarioActualizado) {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		db.collection("usuarios").document(usuarioActualizado.getNickname()).set(usuarioActualizado);
		
		
//		ref.child(usuarioActualizado.getEmail()).setValueAsync(usuarioActualizado);
//		System.out.println("Usuario actualizado correctamente en la BBDD");
		
	}
}

