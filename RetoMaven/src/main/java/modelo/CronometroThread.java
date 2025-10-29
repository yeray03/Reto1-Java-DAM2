package modelo;

import javax.swing.JLabel;
import java.awt.Color;

import vista.EjercicioPanel;

//1. Empieza a contar desde "segundosInicial"
//2. Cada segundo:
//   - Actualiza el JLabel con el tiempo
//   - Si va hacia arriba → suma 1
//   - Si va hacia abajo → resta 1
//3. Si llega a 0 llama a "alFinalizarSerie()"
//4. Se puede pausar, reanudar y detener

public class CronometroThread implements Runnable {

	private JLabel label;
	private int segundos;
	private boolean running;
	private boolean pausado;
	private boolean ascendente;
	private EjercicioPanel panel;

	public CronometroThread(JLabel label, int segundosInicial, boolean ascendente) {
		this.label = label;
		this.segundos = segundosInicial;
		this.ascendente = ascendente;
		this.running = true;
		this.pausado = false;
	}

	public void setPanel(EjercicioPanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		while (running) {
			if (!pausado) {
				// Actualizar el texto del label
				actualizarLabel();

				// Esperar 1 segundo
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					running = false;
					break;
				}

				// Sumar o restar según el tipo
				if (ascendente) {
					segundos++;
				} else {
					segundos--;

					// Si llega a 0, terminar
					if (segundos <= 0) {
						actualizarLabel();
						finalizarCronometro();
						break;
					}
				}
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
	}

	private void actualizarLabel() {
		String tiempo = formatearTiempo(segundos);
		label.setText(tiempo);

		// Cambiar a rojo si quedan 5 segundos o menos
		if (!ascendente && segundos <= 5) {
			label.setForeground(Color.RED);
		} else {
			label.setForeground(Color.WHITE);
		}
	}

	private void finalizarCronometro() {
		running = false;
		if (panel != null) {
			panel.alFinalizarSerie();
		}
		System.out.println("Cronómetro finalizado");
	}

	private String formatearTiempo(int seg) {
		int minutos = seg / 60;
		int segs = seg % 60;
		return String.format("%02d:%02d", minutos, segs);
	}

	public void pausar() {
		this.pausado = true;
		System.out.println("Cronómetro pausado");
	}

	public void reanudar() {
		this.pausado = false;
		System.out.println("Cronómetro reanudado");
	}

	public void detener() {
		this.running = false;
		System.out.println("Cronómetro detenido");
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

	public boolean isAscendente() {
		return ascendente;
	}
}