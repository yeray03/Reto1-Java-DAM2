package controlador;

import modelo.dao.UsuarioDAO;
import pojos.Usuario;
import java.util.ArrayList;

public class RegistroControlador {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	private static RegistroControlador controlador = null;

	// instancia singleton
	public static RegistroControlador getInstance() {
		if (null == controlador) {
			controlador = new RegistroControlador();
		}
		return controlador;
	}

	/**
	 * Registra un usuario si no existe ya en la base de datos.
	 * 
	 * @param usuario El objeto Usuario con todos los datos (nombre, apellidos,
	 *                email, contraseña, fechaNacimiento, tipoUsuario)
	 * @return String con mensaje de estado para la vista: - "Usuario registrado
	 *         correctamente." - "El usuario ya estaba registrado." - "Error en el
	 *         registro: <mensaje de error>"
	 */
	public String registrarUsuario(Usuario usuario) {
		try {
			// Validación básica
			if (usuario.getNickname() == null || usuario.getNickname().trim().isEmpty() || usuario.getEmail() == null
					|| usuario.getEmail().trim().isEmpty() || usuario.getContrasena() == null
					|| usuario.getContrasena().isEmpty() || usuario.getNombre() == null
					|| usuario.getNombre().trim().isEmpty() || usuario.getApellidos() == null
					|| usuario.getApellidos().trim().isEmpty() || usuario.getFechaNacimiento() == null
					|| usuario.getFechaNacimiento().trim().isEmpty()) {
				return "Por favor, rellena todos los campos.";
			}

			// Comprobamos si el usuario ya existe
			UsuarioControlador usuarioControlador = UsuarioControlador.getInstance();
			ArrayList<Usuario> usuarios = usuarioControlador.obtenerTodosUsuarios();
			for (Usuario u : usuarios) {
				if (u.getEmail().equalsIgnoreCase(usuario.getEmail().trim())) {
					return "El email ya estaba registrado.";
				}
			}

			// Registrar usuario en Firestore
			usuarioDAO.registrarUsuario(usuario);

			return "Usuario registrado correctamente.";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error en el registro: " + e.getMessage();
		}
	}

	public String actualizarUsuario(Usuario usuarioActualizado) {
		if (usuarioActualizado.getNickname() == null || usuarioActualizado.getNickname().trim().isEmpty() || usuarioActualizado.getEmail() == null
				|| usuarioActualizado.getEmail().trim().isEmpty() || usuarioActualizado.getContrasena() == null
				|| usuarioActualizado.getContrasena().isEmpty() || usuarioActualizado.getNombre() == null
				|| usuarioActualizado.getNombre().trim().isEmpty() || usuarioActualizado.getApellidos() == null
				|| usuarioActualizado.getApellidos().trim().isEmpty() || usuarioActualizado.getFechaNacimiento() == null
				|| usuarioActualizado.getFechaNacimiento().trim().isEmpty()) {
			return "Por favor, rellena todos los campos.";
		}else {
			usuarioDAO.editarUsuario(usuarioActualizado);
			return "Usuario actualizado correctamente.";
		}

	}
}
