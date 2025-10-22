package pojos;

import java.util.Objects;

public class Usuario {
<<<<<<< HEAD
	private String apellidos;
	private String contrasena;
	private String email;
	private String fechaNacimiento;
	private int nivel;
	private String nombre;
	private int tipoUsuario;
=======
	private String apellidos = "";
	private String contrasena = "";
	private String email = "";
	private String fechaNacimiento = "";
	private int nivel = 0;
	private String nombre = "";
	private int tipoUsuario = 0;
>>>>>>> a3d384751dbe27c511ff5a684966942adf10c53c

	public Usuario() {
	}
	
	public Usuario(String email, String nombre, String apellidos, String contrasena, String fechaNacimiento) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Usuario(String email, String nombre, String contrasena, String fechaNacimiento) {
		this.email = email;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.nivel = 0;
		this.tipoUsuario = 0;
	}

	public String getApellidos() {
		return apellidos != null ? apellidos : "";
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos != null ? apellidos : "";
	}

	public String getEmail() {
		return email != null ? email : "";
	}

	public void setEmail(String email) {
		this.email = email != null ? email : "";
	}

	public String getNombre() {
		return nombre != null ? nombre : "";
	}

	public void setNombre(String nombre) {
		this.nombre = nombre != null ? nombre : "";
	}

	public String getContrasena() {
		return contrasena != null ? contrasena : "";
	}

	public void setContrasena(String password) {
		this.contrasena = password != null ? password : "";
	}

	public String getFechaNacimiento() {
		return fechaNacimiento != null ? fechaNacimiento : "";
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento != null ? fechaNacimiento : "";
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		if (nivel >= 0 && nivel <= 5) {
			this.nivel = nivel;
		} else {
			this.nivel = 0;
		}
	}

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, contrasena, email, fechaNacimiento, nombre, nivel, tipoUsuario);
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
				&& Objects.equals(nombre, other.nombre) && nivel == other.nivel && tipoUsuario == other.tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [apellidos=" + apellidos + ", contrasena=" + contrasena + ", email=" + email
				+ ", fechaNacimiento=" + fechaNacimiento + ", nivel=" + nivel + ", nombre="
				+ nombre + ", tipoUsuario=" + tipoUsuario + "]";
	}
}