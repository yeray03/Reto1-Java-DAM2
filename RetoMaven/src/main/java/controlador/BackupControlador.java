package controlador;

public class BackupControlador {

	private static BackupControlador instancia = null;

	public static BackupControlador getInstancia() {
		if (instancia == null) {
			instancia = new BackupControlador();
		}
		return instancia;
	}
	
	public void realizarBackup() {
		
	}
}
