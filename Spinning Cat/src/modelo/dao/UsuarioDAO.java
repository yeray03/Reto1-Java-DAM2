package modelo.dao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
//import model.FirestoreManager;
import pojos.Usuario;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;


import modelo.FireStoreManager;

public class UsuarioDAO {
    private CollectionReference getUsuarios() throws IOException {
        return FireStoreManager.getDB().collection("usuarios");
    }

    public void registrarUsuario(Usuario usuario) throws IOException {
        getUsuarios().document(usuario.getEmail()).set(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) throws IOException, ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = getUsuarios().document(email).get().get();
        if (snapshot.exists()) {
            return snapshot.toObject(Usuario.class);
        }
        return null;
    }
}
