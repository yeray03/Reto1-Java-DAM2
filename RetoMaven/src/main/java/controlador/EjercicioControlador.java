package controlador;

import java.util.ArrayList;

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
	
//	public Ejercicio buscarEjercicioPorNombre(String nombre) {
//		EjerciciosDAO ejercicioDAO = new EjerciciosDAO();
//		Ejercicio ejercicio = null;
//		try {
//			ejercicio = ejercicioDAO.buscarEjercicioPorNombre(nombre);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ejercicio;
//	}
}
