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
import java.util.ArrayList;
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

		// Email en minúsculas para consistencia
		String emailNormalizado = usuario.getEmail().toLowerCase().trim();
		usuario.setEmail(emailNormalizado);

		String nickname = usuario.getNickname().trim();
		usuario.setNickname(nickname);

		db.collection("usuarios").document(nickname).set(usuario);
	}

	// Busca el usuario por nickname
	public Usuario buscarUsuarioPorNick(String nickname) throws IOException, ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());

		String nickBuscado = nickname.trim();

		ApiFuture<QuerySnapshot> snapshot = db.collection("usuarios").whereEqualTo("nickname", nickBuscado).get();
		QuerySnapshot querySnapshot = snapshot.get();

		if (!querySnapshot.isEmpty()) {
			DocumentSnapshot documento = querySnapshot.getDocuments().get(0);
			return documento.toObject(Usuario.class);
		}
		return null;
	}

	// Obtiene todos los usuarios de la base de datos
	public ArrayList<Usuario> obtenerTodosUsuarios() throws IOException, ExecutionException, InterruptedException {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();

		ApiFuture<QuerySnapshot> snapshot = db.collection("usuarios").get();
		QuerySnapshot querySnapshot = snapshot.get();

		for (DocumentSnapshot documento : querySnapshot.getDocuments()) {
			Usuario usuario = documento.toObject(Usuario.class);
			listaUsuarios.add(usuario);
		}
		return listaUsuarios;
	}

	// Guarda el usuario en la base de datos
	public void guardarUsuario(Usuario usuario) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
		ref.child(usuario.getNickname()).setValueAsync(usuario);
		System.out.println("Usuario guardado correctamente en la BBDD");
	}

	public void editarUsuario(Usuario usuarioActualizado) {
		Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
		db.collection("usuarios").document(usuarioActualizado.getNickname()).set(usuarioActualizado);
	}
}