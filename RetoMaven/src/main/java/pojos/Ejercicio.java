package pojos;

import java.io.Serializable;
import java.util.ArrayList;

public class Ejercicio implements Serializable {
	private static final long serialVersionUID = 1L;
	private String descripcion;
	private String nombre;
	private ArrayList<Serie> series;

	public String getDescripcion() {
		return descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Ejercicio [descripcion=" + descripcion + ", nombre=" + nombre + ", series=" + series + "]";
	}

	public ArrayList<Serie> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}

}
