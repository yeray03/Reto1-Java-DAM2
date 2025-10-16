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


public class UsuarioDAO {
    private CollectionReference getUsuarios() throws IOException {
        return FirebaseServices.getDB().collection("usuarios");
    }

//    public void registrarUsuario(Usuario usuario) throws IOException {
//        getUsuarios().document(usuario.getEmail()).set(usuario);
//    }
    
    public void registrarUsuario(Usuario usuario) throws Exception {
        Firestore db = FirebaseServices.getFirestore();
        db.collection("usuarios").document(usuario.getEmail()).set(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) throws IOException, ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = getUsuarios().document(email).get().get();
        if (snapshot.exists()) {
            return snapshot.toObject(Usuario.class);
        }
        return null;
    }
    
   
    public void guardarUsuario(Usuario usuario) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
        ref.child(usuario.getEmail()).setValueAsync(usuario);
        System.out.println("Usuario guardado correctamente en la BBDD");
    }
    
}
