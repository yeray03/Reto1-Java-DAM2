package modelo;

import javax.swing.JTextArea;
import java.awt.Color;

import vista.EjercicioPanel;

//1. Empieza a contar desde "segundosDescanso"
//2. Cada segundo:
//   - Actualiza el JTextArea con el tiempo
//   - Resta 1 segundo
//   - Si quedan menos de 5 segundos pone texto en rojo
//3. Si llega a 0 llama a "alFinalizarDescanso()"
//4. Se puede pausar, reanudar y detener

public class CronometroDescansoThread implements Runnable {

	private JTextArea textArea;
	private int segundos;
	private boolean running;
	private boolean pausado;
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
					running = false;
					break;
				}

				// Restar 1 segundo
				segundos--;
			} else {
				// Si está en pausa, esperar un poquito
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					running = false;
					break;
				}
			}
		}

		// Si terminó el tiempo, avisar al panel
		if (segundos <= 0 && running) {
			finalizarDescanso();
		}
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