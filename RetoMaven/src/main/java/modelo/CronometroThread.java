package modelo;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import java.awt.Color;

import vista.EjercicioPanel;

public class CronometroThread implements Runnable {

	private JLabel label;
	private int segundos;
	// VOLATILE cuando un thread cambie running = false, todos los demás threads
	// vean el cambio inmediatamente.
	private volatile boolean running;
	private volatile boolean pausado;
	private boolean ascendente;
	private EjercicioPanel panel;
	private JProgressBar progressBar;
	private int tiempoTotal;

	public CronometroThread(JLabel label, int segundosInicial, boolean ascendente) {
		this.label = label;
		this.segundos = segundosInicial;
		this.ascendente = ascendente;
		this.running = true;
		this.pausado = false;
		this.progressBar = null;
		this.tiempoTotal = segundosInicial;

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
					System.out.println("Cronómetro interrumpido");
					running = false;
					break;
				}

				// Verificar si sigue corriendo
				if (!running) {
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
					System.out.println("Cronómetro interrumpido en pausa");
					running = false;
					break;
				}

				// Verifica si fue detenido mientras estaba pausado
				if (!running) {
					break;
				}
			}
		}

		System.out.println("Hilo de cronómetro finalizado");
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

	private void actualizarProgressBar() {
		if (progressBar != null && !ascendente) {
			int valorActual = segundos;
			progressBar.setValue(valorActual);

			int porcentaje = (int) valorActual / tiempoTotal;

			if (porcentaje <= 0.2) {
				progressBar.setForeground(Color.RED);
			} else if (porcentaje <= 0.5) {
				progressBar.setForeground(Color.ORANGE);
			} else {
				progressBar.setForeground(new Color(70, 130, 180));
			}
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