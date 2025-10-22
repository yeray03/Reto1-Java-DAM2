package pojos;

public class Ejercicio {

	private String descripcion;
	private String nombre;
	
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
		return "Ejercicio [descripcion=" + descripcion + ", nombre=" + nombre + "]";
	}

}
