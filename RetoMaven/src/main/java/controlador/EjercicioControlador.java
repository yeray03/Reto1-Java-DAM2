package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import modelo.dao.EjerciciosDAO;
import pojos.Ejercicio;

public class EjercicioControlador {

	private static EjercicioControlador controlador = null;

	// instancia singleton
	public static EjercicioControlador getInstanceControlador() {

		if (null == controlador) {
			controlador = new EjercicioControlador();
		}
		return controlador;
	}

	public ArrayList<Ejercicio> getEjerciciosPorNombre(String nombre) {
		ArrayList<Ejercicio> ret = new ArrayList<Ejercicio>();
		EjerciciosDAO ejercicioDAO = new EjerciciosDAO();

		try {
			ret = ejercicioDAO.getEjercicioByNombre(nombre);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Ejercicio> getTodosEjercicios() throws IOException, InterruptedException, ExecutionException {
		ArrayList<Ejercicio> ret = new ArrayList<Ejercicio>();
		EjerciciosDAO ejercicioDAO = new EjerciciosDAO();
		ret = ejercicioDAO.getEjercicios();
		return ret;

	}
}
