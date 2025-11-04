package pojos;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Ejercicio> ejercicios;
	private String nombre;
	private int nivel;
	private int numEjercicio;
	private String videoUrl;

	public ArrayList<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getNumEjercicios() {
		return numEjercicio;
	}

	public void setNumEjercicios(int numeroEjercicios) {
		this.numEjercicio = numeroEjercicios;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setUrlVideo(String urlVideo) {
		this.videoUrl = urlVideo;
	}

	@Override
	public String toString() {
		return "Workout [ejercicios=" + ejercicios + ", nombre=" + nombre + ", nivel=" + nivel + ", numEjercicios="
				+ numEjercicio + ", videoUrl=" + videoUrl + "]";
	}

}
