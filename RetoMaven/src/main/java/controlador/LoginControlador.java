package controlador;

import modelo.dao.UsuarioDAO;
import pojos.Usuario;

public class LoginControlador {
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public void loginControlador() {
		this.usuarioDAO = new UsuarioDAO();
	}
	
    /**
     * Intenta iniciar sesión con email y contraseña.
     * @param email email introducido por el usuario (no null)
     * @param password contraseña introducida por el usuario (no null)
     * @return Usuario si el login es correcto, null en caso de fallo (usuario no existe o pass incorrecta)
     * @throws Exception si hay error técnico (conexión BBDD, etc.)
     */
    public Usuario login(String email, String password) throws Exception {
        if (email == null || email.trim().isEmpty() || password == null) {
            return null; // o lanzar IllegalArgumentException según tu política
        }

        // Buscar usuario en la BBDD (DAO). DAO devuelve pojo Usuario o null.
        Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email.trim().toLowerCase());

        if (usuario == null) {
            // Usuario no existe
            return null;
        }

        // Comprueba la contraseña (para proyecto de estudiante: texto plano; en producción: hash)
        if (!usuario.getContrasena().equals(password)) {
            // Contraseña incorrecta
            return null;
        }

        // Login correcto
        return usuario;
    }
}
