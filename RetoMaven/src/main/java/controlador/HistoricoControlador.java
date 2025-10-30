package controlador;

import java.util.ArrayList;
import gestor.GestorFicheros;
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

	// Obtiene el historico de un usuario offlne
	public static ArrayList<Historico> getHistoricoOffline(Usuario usuario) {
		try {
			GestorFicheros gestor = GestorFicheros.getInstance();
			ArrayList<Historico> ret = gestor.leerHistorico(usuario);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addHistorico(Usuario usuario, Historico historico) {
		HistoricoDAO historicoDAO = new HistoricoDAO();
		try {
			historicoDAO.addHistorico(usuario.getNickname(), historico);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
