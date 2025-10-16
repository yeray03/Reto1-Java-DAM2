package src.controlador;

import src.modelo.dao.UsuarioDAO;
import src.pojos.Usuario;

public class LoginControlador {
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email);
}
