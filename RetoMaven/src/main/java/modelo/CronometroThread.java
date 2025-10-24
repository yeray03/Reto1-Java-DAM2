package modelo;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;

import vista.EjercicioPanel;

/**
 * Cronómetro que puede contar ascendente (tiempo total) o descendente (tiempo
 * de serie). Implementa Runnable para ejecutarse en un hilo.
 */
public class CronometroThread implements Runnable {

	private JLabel label;
	private int segundos;
	private boolean enEjecucion;
	private boolean enPausa;
	private boolean ascendente;
	private EjercicioPanel panel;
	private String tiempo;

	public CronometroThread(JLabel label, int segundosInicial, boolean ascendente) {
		this.label = label;
		this.segundos = segundosInicial;
		this.ascendente = ascendente;
		this.enEjecucion = true;
		this.enPausa = false;
	}

	public void setPanel(EjercicioPanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		try {
			// Mientras esté en ejecución
			while (enEjecucion) {
				// Si no está en pausa
				if (!enPausa) {
					// Formatear el tiempo como 00:00
					tiempo = formatearTiempo(segundos);

					// Actualizar el JLabel en EjercicioPanel
					SwingUtilities.invokeLater(() -> {
						label.setText(tiempo);

						// Cambiar color si quedan 5 segundos o menos
						if (!ascendente && segundos <= 5) {
							label.setForeground(Color.RED);
						} else {
							label.setForeground(Color.WHITE);
						}
					});

					// El hilo se actualiza cada segundo
					Thread.sleep(1000);

					// Incrementar o incrementarn't según el tipo
					if (ascendente) {
						segundos++;
					} else {
						segundos--;

						// Si llega a 0, notificar al alguien, o llamar a la policia según vea lo
						// criminal que es esto
						if (segundos <= 0) {
							enEjecucion = false;
							if (panel != null) {
								//Una flecha?->->->-> enserio? ->->->->
								//Funciona, no se hacen preguntas
								SwingUtilities.invokeLater(() -> panel.alFinalizarSerie());
							}
						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String formatearTiempo(int seg) {
		String tiempo = "";
		int minutos = seg / 60;
		int segs = seg % 60;

		if (minutos < 10) {
			tiempo += "0" + minutos;
		} else {
			tiempo += minutos;
		}

		tiempo += ":";

		if (segs < 10) {
			tiempo += "0" + segs;
		} else {
			tiempo += segs;
		}

		return tiempo;
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

	public boolean isPausado() {
		return enPausa;
	}

	public int getSegundos() {
		return segundos;
	}

}