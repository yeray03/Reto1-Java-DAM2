package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import modelo.dao.UsuarioDAO;
import pojos.Usuario;

public class UsuarioControlador {

	private static UsuarioControlador controlador = null;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	// instancia singleton
	public static UsuarioControlador getInstance() {

		if (null == controlador) {
			controlador = new UsuarioControlador();
		}
		return controlador;
	}

	/**
	 * Busca un usuario por su nickname.
	 * 
	 * @param nickname El nickname del usuario a buscar.
	 * @return El objeto Usuario si se encuentra, null en caso contrario.
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public Usuario buscarPorNick(String nickname) throws IOException, ExecutionException, InterruptedException {
		ArrayList<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		for (Usuario u : usuarios) {
			if (u.getNickname().equalsIgnoreCase(nickname.trim())) {
				return u;
			}
		}
		return null;
	}

	public ArrayList<Usuario> obtenerTodosUsuarios() throws IOException, ExecutionException, InterruptedException {
		ArrayList<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		return usuarios;
	}

}
