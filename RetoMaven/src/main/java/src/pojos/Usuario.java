package src.pojos;

import java.util.Objects;

public class Usuario {
	private String email;
	private String nombre;
	private String contrasena;
	private String fechaNacimiento;
	private String apellidos;

	public Usuario() {
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, contrasena, email, fechaNacimiento, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(email, other.email) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(nombre, other.nombre);
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nombre=" + nombre + ", contrasena=" + contrasena + ", fechaNacimiento="
				+ fechaNacimiento + ", apellidos=" + apellidos + "]";
	}

	
}
