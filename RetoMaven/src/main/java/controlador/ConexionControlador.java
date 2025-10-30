package controlador;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConexionControlador {
	private static ConexionControlador controlador = null;

	// instancia singleton
	public static ConexionControlador getInstance() {
		if (null == controlador) {
			controlador = new ConexionControlador();
		}
		return controlador;
	}

	/**
	 * Comprueba si hay conexión a Internet intentando conectar a un servidor DNS
	 * público.
	 * 
	 * @return true si hay conexión a Internet, false en caso contrario.
	 */
	public boolean comprobarConexion() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("8.8.8.8", 53), 2000);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
