package controlador;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import modelo.dao.UsuarioDAO;
import pojos.Usuario;

public class LoginControlador {

	private static LoginControlador controlador = null;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	// instancia singleton
	public static LoginControlador getInstance() {

		if (null == controlador) {
			controlador = new LoginControlador();
		}
		return controlador;
	}

	/**
	 * Intenta iniciar sesión con email y contraseña.
	 * 
	 * @param email    email introducido por el usuario (no null)
	 * @param password contraseña introducida por el usuario (no null)
	 * @return Usuario si el login es correcto, null en caso de fallo (usuario no
	 *         existe o pass incorrecta)
	 * @throws Exception si hay error técnico (conexión BBDD, etc.)
	 */
	public Usuario login(String email, String password) throws IOException, ExecutionException, InterruptedException {
		Usuario ret = usuarioDAO.buscarUsuarioPorEmail(email);
		return ret;
	}
}
