package controlador;

import java.util.ArrayList;

import pojos.Historico;
import pojos.Usuario;
import modelo.dao.HistoricoDAO;

public class HistoricoControlador {

	private static HistoricoControlador controlador = null;
	private HistoricoDAO historicoDAO = new HistoricoDAO();

	// instancia singleton
	public static HistoricoControlador getInstance() {
		if (null == controlador) {
			controlador = new HistoricoControlador();
		}
		return controlador;
	}

	// Obtiene el historial de un usuario
	public ArrayList<Historico> getHistoricoUsuario(Usuario usuario) {
		try {
			ArrayList<Historico> historico = historicoDAO.getHistoricoPorUsuario(usuario.getNickname());
			return historico;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
