package controlador;

import java.util.ArrayList;

import modelo.dao.WorkoutDAO;
import pojos.Workout;

public class WorkoutControlador {

	private static WorkoutControlador controlador = null;

	// instancia singleton
	public static WorkoutControlador getInstanceControlador() {

		if (null == controlador) {
			controlador = new WorkoutControlador();
		}
		return controlador;
	}

	public ArrayList<Workout> getWorkouts() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Workout> getWorkoutsHastaNivel(int nivel) {
		ArrayList<Workout> ret = new ArrayList<Workout>();
		WorkoutDAO workoutDAO = new WorkoutDAO();
		
		try {
			ret = workoutDAO.getWorkoutUntilLevel(nivel);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList<Workout> getWorkoutsPorNivel(int nivel) {
		ArrayList<Workout> ret = new ArrayList<Workout>();
		WorkoutDAO workoutDAO = new WorkoutDAO();
		
		try {
			ret = workoutDAO.getWorkoutsByLevel(nivel);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public ArrayList<Workout> getWorkoutsUntilNivel(int nivel) {
		ArrayList<Workout> ret = new ArrayList<Workout>();
		WorkoutDAO workoutDAO = new WorkoutDAO();
		
		try {
			ret = workoutDAO.getWorkoutsByLevel(nivel);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
