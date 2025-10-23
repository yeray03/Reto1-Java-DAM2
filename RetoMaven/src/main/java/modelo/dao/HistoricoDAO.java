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
     * @param email Email del usuario (ID del documento en usuarios)
     * @return Lista de registros históricos
     */
    public ArrayList<Historico> getHistoricoPorUsuario(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
        
        // Normalizar email
        String emailNormalizado = email.toLowerCase().trim();
        
        // Acceder a la subcolección historico del usuario
        CollectionReference historicoRef = db.collection("usuarios")
                                             .document(emailNormalizado)
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
        
        System.out.println("✅ Históricos recuperados para " + emailNormalizado + ": " + historicos.size());
        return historicos;
    }
    
    /**
     * Añade un registro al histórico del usuario
     * @param email Email del usuario
     * @param historico Objeto Historico a guardar
     */
    public void addHistorico(String email, Historico historico) throws Exception {
        Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
        
        // ✅ Normalizar email
        String emailNormalizado = email.toLowerCase().trim();
        
        // Añadir documento con ID autogenerado en la subcolección historico
        db.collection("usuarios")
          .document(emailNormalizado)
          .collection("historico")
          .document() // ID autogenerado
          .set(historico);
        
        System.out.println("✅ Registro añadido al histórico de " + emailNormalizado);
    }
}