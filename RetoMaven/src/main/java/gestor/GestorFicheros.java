package gestor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import controlador.EjercicioControlador;
import controlador.UsuarioControlador;
import controlador.WorkoutControlador;
import pojos.Ejercicio;
import pojos.Usuario;
import pojos.Workout;

public class GestorFicheros {

	private static GestorFicheros instancia = null;
	private Path rutaDirectorio;

	public static GestorFicheros getInstance() {
		if (instancia == null) {
			instancia = new GestorFicheros();
		}
		return instancia;
	}

	public void guardarDatos() {
		// El fichero se va a guardar en el directorio SpinningCat en la carpeta
		// Documentos del usuario (nadie usa "trastero" :P)
		rutaDirectorio = Paths.get(System.getProperty("user.home"), "Documents", "SpinningCat");
		try {
			Files.createDirectories(rutaDirectorio);
			guardarUsuarios();
			guardarWorkouts();
			guardarEjercicios();
			// PARA LEER LOS FICHEROS CREADOS (lo pongo por si hace falta luego)
//			leerUsuarios();
//			leerWorkouts();
//			leerEjercicios();
		} catch (IOException e) {
			System.err.println("Error al crear el directorio: " + rutaDirectorio);
			e.printStackTrace();
		}

	}

	// GUARDAR USUARIOS
	private void guardarUsuarios() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			// crear el fichero usuarios.dat
			file = new File(rutaDirectorio.toString(), "Usuarios.dat");
			// abrir el flujo de salida
			fos = new FileOutputStream(file);
			// abrir el flujo de objetos
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			UsuarioControlador controlador = UsuarioControlador.getInstance();
			ArrayList<Usuario> usuarios = controlador.obtenerTodosUsuarios();
			// escribimos cada usuario en el fichero
			for (Usuario usuario : usuarios) {
				oos.writeObject(usuario);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los usuarios en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	// GUARDAR WORKOUTS
	private void guardarWorkouts() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		// lo mismo que lo de arriba pero con workouts (te lo lees)
		try {
			file = new File(rutaDirectorio.toString(), "Workouts.dat");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			WorkoutControlador controlador = WorkoutControlador.getInstanceControlador();
			ArrayList<Workout> workouts = controlador.getWorkouts();

			for (Workout workout : workouts) {
				oos.writeObject(workout);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los workouts en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	// GUARDAR EJERCICIOS
	private void guardarEjercicios() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		// lo mismo que lo de arriba pero con workouts (te lo lees)
		try {
			file = new File(rutaDirectorio.toString(), "Ejercicios.dat");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			EjercicioControlador controlador = EjercicioControlador.getInstanceControlador();
			ArrayList<Ejercicio> ejercicios = controlador.getTodosEjercicios();

			for (Ejercicio ejercicio : ejercicios) {
				oos.writeObject(ejercicio);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los ejercicios en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	// LEER USUARIOS
	private void leerUsuarios() {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			file = new File(rutaDirectorio.toString(), "usuarios.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Usuario usuario = (Usuario) ois.readObject();
				System.out.println("Usuario leído: " + usuario.toString());
			}

		} catch (Exception e) {
			System.out.println("Error al leer el fichero usuarios.dat.");
			e.printStackTrace();
		}
	}

	// LEER WORKOUTS
	private void leerWorkouts() {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			file = new File(rutaDirectorio.toString(), "workouts.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Workout workout = (Workout) ois.readObject();
				System.out.println("Workout leído: " + workout.toString());
			}

		} catch (Exception e) {
			System.out.println("Error al leer el fichero workouts.dat.");
			e.printStackTrace();
		}
	}

	// LEER EJERCICIOS
	private void leerEjercicios() {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			file = new File(rutaDirectorio.toString(), "ejercicios.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Ejercicio ejercicio = (Ejercicio) ois.readObject();
				System.out.println("Ejercicio leído: " + ejercicio.toString());
			}

		} catch (Exception e) {
			System.out.println("Error al leer el fichero ejercicios.dat.");
			e.printStackTrace();
		}
	}

}
