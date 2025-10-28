package modelo;

import javax.swing.JTextArea;

import vista.EjercicioPanel;

/**
 * Cronómetro de descanso que cuenta de forma descendente. Se ejecuta entre
 * series.
 */
public class CronometroDescansoThread implements Runnable {

	private JTextArea textArea;
	private int segundos;
	private boolean enEjecucion;
	private boolean enPausa;
	private EjercicioPanel panel;

	/**
	 * Constructor del cronómetro de descanso
	 * 
	 * @param textArea        JTextArea donde se mostrará el tiempo
	 * @param segundosInicial Tiempo inicial de descanso en segundos
	 */
	public CronometroDescansoThread(JTextArea textArea, int segundosInicial) {
		this.textArea = textArea;
		this.segundos = segundosInicial;
		this.enEjecucion = true;
		this.enPausa = false;
	}
	
	/**
	 * Establece el panel para notificar cuando finalice el descanso
	 * @param panel Panel de ejercicio
	 */
	public void setPanel(EjercicioPanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {

	}

	
	public void pausar() {
		this.enPausa = true;
	}

	
	public void reanudar() {
		this.enPausa = false;
	}

	
	public void detener() {
		this.enEjecucion = false;
	}

	//True si está en pausa, false si no
	public boolean isPausado() {
		return enPausa;
	}

	
	public int getSegundos() {
		return segundos;
	}

}
