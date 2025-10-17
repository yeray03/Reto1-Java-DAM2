package src.modelo.dao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import src.modelo.FirebaseServices;
import src.pojos.Usuario;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

// Hacer que extienda FirebaseManagerAbstract (así usamos el patrón factory)
public class UsuarioDAO {
	private CollectionReference getUsuarios() throws IOException {
	    Firestore db = (Firestore) FirebaseServices.getDB();
	    if (db == null) {
	        throw new IOException("Firestore is not initialized.");
	    }
	    return db.collection("usuarios");
	}
 
    public void registrarUsuario(Usuario usuario) throws Exception {
        Firestore db = FirebaseServices.getFirestore();
        db.collection("usuarios").document(usuario.getEmail()).set(usuario);
    }
    
    // Esto solo funciona si el email es el nombre del documento en firebase
    public Usuario buscarUsuarioPorEmail(String email) throws IOException, ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = getUsuarios().document(email).get().get();
        if (snapshot.exists()) {
            return snapshot.toObject(Usuario.class);
        }
        return null;
    }
    
   // Guarda usuario en Realtime Database
    public void guardarUsuario(Usuario usuario) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
        ref.child(usuario.getEmail()).setValueAsync(usuario);
        System.out.println("Usuario guardado correctamente en la BBDD");
    }
    
}
