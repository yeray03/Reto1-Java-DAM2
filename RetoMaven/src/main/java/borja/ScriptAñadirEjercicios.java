package borja;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class ScriptAñadirEjercicios {

    private static final String DATABASE_URL = "https://spinningcat-cac21-default-rtdb.europe-west1.firebasedatabase.app";
    private static final String CREDENTIALS_PATH = "privateKey.json";
    private static Random random = new Random();

    public static void main(String[] args) {
        try {
            // Inicializar Firebase
            initializeFirebase();
            
            Firestore db = FirestoreClient.getFirestore(FirebaseApp.getInstance());
            
            System.out.println("✅ Conexión establecida con Firestore\n");
            
            // 1. Añadir ejercicios existentes con tiempos reducidos
            System.out.println("📋 AÑADIENDO EJERCICIOS EXISTENTES...");
            añadirEjerciciosExistentes(db);
            
            // 2. Crear nuevos ejercicios
            System.out.println("\n📋 CREANDO NUEVOS EJERCICIOS...");
            List<String> nuevosEjerciciosIds = crearNuevosEjercicios(db);
            
            // 3. Crear workouts con los nuevos ejercicios
            System.out.println("\n🏋️ CREANDO NUEVOS WORKOUTS...");
            crearNuevosWorkouts(db, nuevosEjerciciosIds);
            
            System.out.println("\n✅ ¡Script ejecutado correctamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (FirebaseApp.getApps().size() > 0) {
                FirebaseApp.getInstance().delete();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = ScriptAñadirEjercicios.class
                    .getClassLoader()
                    .getResourceAsStream(CREDENTIALS_PATH);

            if (serviceAccount == null) {
                throw new IOException("No se encontró el archivo: " + CREDENTIALS_PATH);
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("🔥 Firebase inicializado");
        }
    }

    /**
     * Añade los ejercicios existentes con tiempos reducidos (5-15 segundos)
     */
    private static void añadirEjerciciosExistentes(Firestore db) throws Exception {
        String[][] ejercicios = {
            {"bicicleta", "Dale indaraín"},
            {"correr", "Correr en el sitio"},
            {"flexiones", "Flexiones de brazos"},
            {"jaca", "Postura de jaca"},
            {"mancuernas", "Levantamiento con mancuernas"},
            {"prensa", "Prensa de piernas"},
            {"pressMilitar", "Press militar"},
            {"sentadilla", "Sentadillas profundas"}
        };

        for (String[] ejercicio : ejercicios) {
            String id = ejercicio[0];
            String descripcion = ejercicio[1];
            
            Map<String, Object> ejercicioData = new HashMap<>();
            ejercicioData.put("nombre", capitalize(id));
            ejercicioData.put("descripcion", descripcion);
            
            // Crear 3 series con tiempos reducidos (5-15 segundos)
            List<Map<String, Object>> series = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                Map<String, Object> serie = new HashMap<>();
                serie.put("nombre", "Serie " + i);
                serie.put("repeticiones", 10 + random.nextInt(11)); // 10-20 reps
                serie.put("tiempoSerie", 5 + random.nextInt(11)); // 5-15 seg
                serie.put("tiempoDescanso", 5 + random.nextInt(11)); // 5-15 seg
                serie.put("fotoUrl", "");
                series.add(serie);
            }
            
            ejercicioData.put("series", series);
            
            db.collection("ejercicios").document(id).set(ejercicioData);
            System.out.println("✅ Ejercicio añadido: " + id);
        }
    }

    /**
     * Crea 15 nuevos ejercicios (5 workouts × 3 ejercicios)
     */
    private static List<String> crearNuevosEjercicios(Firestore db) throws Exception {
        List<String> ejerciciosIds = new ArrayList<>();
        
        String[][] nuevosEjercicios = {
            // Nivel 1 - Ejercicios básicos
            {"burpees", "Burpees completos"},
            {"plancha", "Plancha abdominal"},
            {"saltos", "Saltos en el sitio"},
            
            // Nivel 2 - Ejercicios intermedios
            {"lunges", "Zancadas alternas"},
            {"mountainClimbers", "Mountain climbers"},
            {"jumpingJacks", "Jumping jacks"},
            
            // Nivel 3 - Ejercicios avanzados
            {"tricesDips", "Fondos de tríceps"},
            {"squatJumps", "Sentadillas con salto"},
            {"plankJacks", "Plank jacks"},
            
            // Nivel 4 - Ejercicios intensos
            {"boxJumps", "Saltos al cajón"},
            {"russianTwists", "Giros rusos"},
            {"highKnees", "Rodillas altas"},
            
            // Nivel 5 - Ejercicios experto
            {"pistolSquats", "Sentadilla pistol"},
            {"handstandPushups", "Flexiones verticales"},
            {"dragonFlags", "Dragon flags"}
        };

        for (String[] ejercicio : nuevosEjercicios) {
            String id = ejercicio[0];
            String descripcion = ejercicio[1];
            
            Map<String, Object> ejercicioData = new HashMap<>();
            ejercicioData.put("nombre", capitalize(id));
            ejercicioData.put("descripcion", descripcion);
            
            // Crear 3 series con tiempos reducidos
            List<Map<String, Object>> series = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                Map<String, Object> serie = new HashMap<>();
                serie.put("nombre", "Serie " + i);
                serie.put("repeticiones", 8 + random.nextInt(13)); // 8-20 reps
                serie.put("tiempoSerie", 5 + random.nextInt(11)); // 5-15 seg
                serie.put("tiempoDescanso", 5 + random.nextInt(11)); // 5-15 seg
                serie.put("fotoUrl", "");
                series.add(serie);
            }
            
            ejercicioData.put("series", series);
            
            DocumentReference ref = db.collection("ejercicios").document(id);
            ref.set(ejercicioData);
            ejerciciosIds.add(id);
            
            System.out.println("✅ Nuevo ejercicio creado: " + id);
        }
        
        return ejerciciosIds;
    }

    /**
     * Crea 5 workouts nuevos (uno por nivel del 1 al 5)
     */
    private static void crearNuevosWorkouts(Firestore db, List<String> ejerciciosIds) throws Exception {
        String[] nombresWorkouts = {
            "Cardio Intenso",
            "Fuerza Total",
            "Resistencia",
            "Power Training",
            "Elite Challenge"
        };

        String[] videosUrls = {
            "https://www.youtube.com/watch?v=cardio1",
            "https://www.youtube.com/watch?v=fuerza2",
            "https://www.youtube.com/watch?v=resistencia3",
            "https://www.youtube.com/watch?v=power4",
            "https://www.youtube.com/watch?v=elite5"
        };

        // Crear un workout por cada nivel (1-5)
        for (int nivel = 1; nivel <= 5; nivel++) {
            Map<String, Object> workout = new HashMap<>();
            workout.put("nombre", nombresWorkouts[nivel - 1]);
            workout.put("nivel", nivel);
            workout.put("numEjercicios", 3);
            workout.put("videoUrl", videosUrls[nivel - 1]);
            
            // Seleccionar 3 ejercicios consecutivos para este nivel
            int startIdx = (nivel - 1) * 3;
            List<DocumentReference> ejercicios = new ArrayList<>();
            
            for (int i = startIdx; i < startIdx + 3 && i < ejerciciosIds.size(); i++) {
                DocumentReference ref = db.collection("ejercicios").document(ejerciciosIds.get(i));
                ejercicios.add(ref);
            }
            
            workout.put("ejercicios", ejercicios);
            
            db.collection("workouts").document().set(workout);
            System.out.println("✅ Workout creado: " + nombresWorkouts[nivel - 1] + " (Nivel " + nivel + ")");
        }
    }

    /**
     * Capitaliza la primera letra de un string
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}