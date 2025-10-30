package procesos;

import java.util.ArrayList;

import controlador.HistoricoControlador;
import controlador.UsuarioControlador;
import gestor.GestorFicheros;
import pojos.Historico;
import pojos.Usuario;

public class ProcesoBackup {
	public static void main(String[] args) {
		int salida = 0;
		System.out.println("Iniciando proceso de backup...");
		// subir historicos nuevos del backup a la base de datos remota
		try {
			ArrayList<Usuario> usuarios = UsuarioControlador.getInstance().obtenerTodosUsuarios();
			for (Usuario usuario : usuarios) {
				ArrayList<Historico> historicosOffline = HistoricoControlador.getHistoricoOffline(usuario);
				ArrayList<Historico> historicoRemoto = HistoricoControlador.getInstance().getHistoricoUsuario(usuario);
				for (Historico historico : historicosOffline) {
					boolean existe = false;
					for (Historico historico2 : historicoRemoto) {
						if (historico.getFecha().equals(historico2.getFecha())) {
							existe = true;
							break;
						}
					}
					if (!existe) {
						HistoricoControlador.getInstance().addHistorico(usuario, historico);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error al subir históricos al servidor remoto: " + e.getMessage());
			salida++;
		}

		// realizar backup de la base de datos remota a local
		try {
			GestorFicheros.getInstance().guardarDatos();
		}catch (Exception e) {
			salida++;
		}
		System.exit(salida);
	}
}
