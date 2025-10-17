package modelo.pojo;

public class Usuario {
	private String email;
	private String nombre;
	private String contrasena;
	private String fechaNacimiento;

	public Usuario() {
	}

	public Usuario(String email, String nombre, String contrasena, String fechaNacimiento) {
		this.email = email;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String password) {
		this.contrasena = password;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
