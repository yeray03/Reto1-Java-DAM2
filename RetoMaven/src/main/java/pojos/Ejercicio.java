package pojos;

import java.util.ArrayList;

public class Ejercicio {

	private String descripcion;
	private int idEjercico;
	private String nombre;
	private ArrayList<Integer> seriesIds;
	private String videoUrl;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdWorkout() {
		return idEjercico;
	}
	public void setIdWorkout(int idWorkout) {
		this.idEjercico = idWorkout;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Integer> getSeriesIds() {
		return seriesIds;
	}
	public void setSeriesIds(ArrayList<Integer> seriesIds) {
		this.seriesIds = seriesIds;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
