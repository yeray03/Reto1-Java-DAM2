//package modelo.dao;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.cloud.FirestoreClient;
//
//import modelo.FirebaseInitialize;
//
//public class ActualizarEjerciciosScript extends FirebaseInitialize {
//
//    public ActualizarEjerciciosScript() {
//        super();
//        System.out.println("Constructor ejecutado");
//    }
//
//    public void actualizarTodosLosEjercicios() throws Exception {
//        System.out.println("Iniciando actualizacion de ejercicios...");
//        
//        Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
//        
//        if (db == null) {
//            System.err.println("ERROR: Firestore es null");
//            return;
//        }
//        
//        System.out.println("Firestore conectado correctamente");
//        
//        System.out.println("Actualizando bicicleta...");
//        actualizarBicicleta(db);
//        
//        System.out.println("Actualizando correr...");
//        actualizarCorrer(db);
//        
//        System.out.println("Actualizando flexiones...");
//        actualizarFlexiones(db);
//        
//        System.out.println("Actualizando jaca...");
//        actualizarJaca(db);
//        
//        System.out.println("Actualizando mancuernas...");
//        actualizarMancuernas(db);
//        
//        System.out.println("Actualizando prensa...");
//        actualizarPrensas(db);
//        
//        System.out.println("Actualizando pressMilitar...");
//        actualizarPressMilitar(db);
//        
//        System.out.println("Actualizando sentadilla...");
//        actualizarSentadilla(db);
//        
//        System.out.println("TODOS LOS EJERCICIOS ACTUALIZADOS CORRECTAMENTE");
//    }
//    
//    private void actualizarBicicleta(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "bicicleta");
//        ejercicio.put("descripcion", "Dale indurain");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 15, 45, 20));
//        series.add(crearSerie("Serie 2", 15, 45, 20));
//        series.add(crearSerie("Serie 3", 15, 45, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("bicicleta").set(ejercicio).get();
//        System.out.println("  OK: bicicleta");
//    }
//    
//    private void actualizarCorrer(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "correr");
//        ejercicio.put("descripcion", "Carrera continua");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 10, 60, 30));
//        series.add(crearSerie("Serie 2", 10, 60, 30));
//        series.add(crearSerie("Serie 3", 10, 60, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("correr").set(ejercicio).get();
//        System.out.println("  OK: correr");
//    }
//    
//    private void actualizarFlexiones(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "flexiones");
//        ejercicio.put("descripcion", "Flexiones de pecho");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 15, 35, 20));
//        series.add(crearSerie("Serie 2", 15, 35, 20));
//        series.add(crearSerie("Serie 3", 15, 35, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("flexiones").set(ejercicio).get();
//        System.out.println("  OK: flexiones");
//    }
//    
//    private void actualizarJaca(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "jaca");
//        ejercicio.put("descripcion", "Ejercicio de fuerza");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 12, 40, 25));
//        series.add(crearSerie("Serie 2", 12, 40, 25));
//        series.add(crearSerie("Serie 3", 12, 40, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("jaca").set(ejercicio).get();
//        System.out.println("  OK: jaca");
//    }
//    
//    private void actualizarMancuernas(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "mancuernas");
//        ejercicio.put("descripcion", "Curl de biceps");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 12, 40, 25));
//        series.add(crearSerie("Serie 2", 12, 40, 25));
//        series.add(crearSerie("Serie 3", 12, 40, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("mancuernas").set(ejercicio).get();
//        System.out.println("  OK: mancuernas");
//    }
//    
//    private void actualizarPrensas(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "prensa");
//        ejercicio.put("descripcion", "Prensa de piernas");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 10, 50, 30));
//        series.add(crearSerie("Serie 2", 10, 50, 30));
//        series.add(crearSerie("Serie 3", 10, 50, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("prensa").set(ejercicio).get();
//        System.out.println("  OK: prensa");
//    }
//    
//    private void actualizarPressMilitar(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "pressMilitar");
//        ejercicio.put("descripcion", "Press militar");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 10, 45, 25));
//        series.add(crearSerie("Serie 2", 10, 45, 25));
//        series.add(crearSerie("Serie 3", 10, 45, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("pressMilitar").set(ejercicio).get();
//        System.out.println("  OK: pressMilitar");
//    }
//    
//    private void actualizarSentadilla(Firestore db) throws Exception {
//        Map<String, Object> ejercicio = new HashMap<>();
//        ejercicio.put("nombre", "sentadilla");
//        ejercicio.put("descripcion", "Sentadillas profundas");
//        
//        List<Map<String, Object>> series = new ArrayList<>();
//        series.add(crearSerie("Serie 1", 12, 40, 25));
//        series.add(crearSerie("Serie 2", 12, 40, 25));
//        series.add(crearSerie("Serie 3", 12, 40, 0));
//        ejercicio.put("series", series);
//        
//        db.collection("ejercicios").document("sentadilla").set(ejercicio).get();
//        System.out.println("  OK: sentadilla");
//    }
//    
//    private Map<String, Object> crearSerie(String nombre, int reps, int tiempoSerie, int tiempoDescanso) {
//        Map<String, Object> serie = new HashMap<>();
//        serie.put("nombre", nombre);
//        serie.put("repeticiones", reps);
//        serie.put("tiempoSerie", tiempoSerie);
//        serie.put("tiempoDescanso", tiempoDescanso);
//        serie.put("fotoUrl", "");
//        return serie;
//    }
//    
//    public static void main(String[] args) {
//        System.out.println("=== INICIANDO SCRIPT ===");
//        try {
//            ActualizarEjerciciosScript script = new ActualizarEjerciciosScript();
//            System.out.println("Objeto script creado");
//            
//            script.actualizarTodosLosEjercicios();
//            
//            System.out.println("=== SCRIPT COMPLETADO EXITOSAMENTE ===");
//            System.exit(0);
//        } catch (Exception e) {
//            System.err.println("=== ERROR EN EL SCRIPT ===");
//            System.err.println("Mensaje: " + e.getMessage());
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
//}