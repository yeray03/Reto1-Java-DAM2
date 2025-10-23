package modelo.dao;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import modelo.FirebaseInitialize;
import pojos.Historico;

public class HistoricoDAO extends FirebaseInitialize {

    public HistoricoDAO() {
        super();
    }

    /**
     * Obtiene el histórico de un usuario
     * @param nickname Nickname del usuario (ID del documento en usuarios)
     * @return Lista de registros históricos
     */
    public ArrayList<Historico> getHistoricoPorUsuario(String nickname) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
        
        CollectionReference historicoRef = db.collection("usuarios")
                                             .document(nickname)
                                             .collection("historico");
        
        ApiFuture<QuerySnapshot> query = historicoRef.get();
        QuerySnapshot querySnapshot = query.get();
        
        ArrayList<Historico> historicos = new ArrayList<>();
        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
            Historico h = doc.toObject(Historico.class);
            if (h != null) {
                historicos.add(h);
            }
        }
        
        System.out.println("Históricos recuperados para " + nickname + ": " + historicos.size());
        return historicos;
    }
    
    /**
     * Añade un registro al histórico del usuario
     * @param nickname Nickname del usuario
     * @param historico Objeto Historico a guardar
     */
    public void addHistorico(String nickname, Historico historico) throws Exception {
        Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
        
        //Debug guapisimo
        System.out.println("Guardando histórico para usuario: " + nickname);
        System.out.println(" Workout: " + historico.getWorkoutNombre());
        System.out.println(" Ruta: usuarios/" + nickname + "/historico");
        
        db.collection("usuarios")
          .document(nickname)
          .collection("historico")
          .document()
          .set(historico);
        
        System.out.println("Registro añadido al histórico de " + nickname);
    }
}