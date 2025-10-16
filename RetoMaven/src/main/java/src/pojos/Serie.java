package src.pojos;

public class Serie {

	private String nombre;
	private Ejercicio[] ejercicios;

	public Serie(String nombre, Ejercicio[] ejercicios) {
		super();
		this.nombre = nombre;
		this.ejercicios = ejercicios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ejercicio[] getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(Ejercicio[] ejercicios) {
		this.ejercicios = ejercicios;
	}
}
