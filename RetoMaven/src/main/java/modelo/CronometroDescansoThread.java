package modelo;

import javax.swing.JTextArea;
import java.awt.Color;

import vista.EjercicioPanel;

public class CronometroDescansoThread implements Runnable {

	private JTextArea textArea;
	private int segundos;
	private volatile boolean running;
	private volatile boolean pausado;
	private EjercicioPanel panel;

	public CronometroDescansoThread(JTextArea textArea, int segundosDescanso) {
		this.textArea = textArea;
		this.segundos = segundosDescanso;
		this.running = true;
		this.pausado = false;
	}

	public void setPanel(EjercicioPanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		while (running && segundos > 0) {
			if (!pausado) {
				// Actualizar el texto
				actualizarTextArea();

				// Esperar 1 segundo
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Cronómetro descanso interrumpido");
					running = false;  // ⬅️ Detener si es interrumpido
					break;
				}

				// Verificar si sigue corriendo después del sleep
				if (!running) {
					break;
				}

				// Restar 1 segundo
				segundos--;
			} else {
				// Si está en pausa, esperar un poquito
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Cronómetro descanso interrumpido en pausa");
					running = false;  // ⬅️ Detener si es interrumpido
					break;
				}

				// Verifica si fue detenido mientras estaba pausado
				if (!running) {
					break;
				}
			}
		}

		// Si terminó el tiempo Y sigue corriendo, avisar al panel
		if (segundos <= 0 && running) {
			finalizarDescanso();
		}
		
		System.out.println("Hilo de descanso finalizado");
	}

	private void actualizarTextArea() {
		String tiempo = formatearTiempo(segundos);
		textArea.setText(tiempo);

		// Cambiar a rojo si quedan 5 segundos o menos
		if (segundos <= 5) {
			textArea.setForeground(Color.RED);
		} else {
			textArea.setForeground(Color.ORANGE);
		}
	}

	private void finalizarDescanso() {
		resetearTextArea();
		if (panel != null) {
			panel.alFinalizarDescanso();
		}
	}

	public void pausar() {
		this.pausado = true;
		System.out.println("Descanso pausado");
	}

	public void reanudar() {
		this.pausado = false;
		System.out.println("Descanso reanudado");
	}

	public void detener() {
		this.running = false;
		resetearTextArea();
		System.out.println("Descanso detenido");
	}

	private void resetearTextArea() {
		textArea.setText("--:--");
		textArea.setForeground(Color.ORANGE);
	}

	private String formatearTiempo(int seg) {
		int minutos = seg / 60;
		int segs = seg % 60;
		return String.format("%02d:%02d", minutos, segs);
	}

	public boolean isPausado() {
		return pausado;
	}

	public boolean isRunning() {
		return running;
	}

	public int getSegundos() {
		return segundos;
	}
}