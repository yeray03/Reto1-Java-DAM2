package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ResumenWorkout extends JDialog {
	private static final long serialVersionUID = 1L;

	public ResumenWorkout(JFrame parent, boolean completado, String workoutNombre, int tiempoTotal, int tiempoPrevisto,
			int ejerciciosCompletados, int ejerciciosTotales) {

		// Inicializa el diálogo con los parámetros necesarios
		super(parent, "Resumen del Workout", true);

		setSize(450, 400);
		setLocationRelativeTo(parent);
		setResizable(false);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.decode("#232637"));

		// Título
		JLabel lblTitulo = new JLabel();
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(20, 20, 410, 40);

		if (completado) {
			lblTitulo.setText("¡Workout Completado! 🎉");
			lblTitulo.setForeground(new Color(99, 179, 92));
		} else {
			lblTitulo.setText("Workout Interrumpido");
			lblTitulo.setForeground(new Color(255, 165, 0));
		}
		getContentPane().add(lblTitulo);

		// Nombre del workout
		JLabel lblWorkout = new JLabel("Workout: " + workoutNombre);
		lblWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkout.setFont(new Font("Arial", Font.BOLD, 18));
		lblWorkout.setForeground(Color.WHITE);
		lblWorkout.setBounds(20, 70, 410, 30);
		getContentPane().add(lblWorkout);

		// Tiempo total
		JLabel lblTiempoTotal = new JLabel("Tiempo Total: " + formatearTiempo(tiempoTotal));
		lblTiempoTotal.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTiempoTotal.setForeground(Color.WHITE);
		lblTiempoTotal.setBounds(60, 120, 350, 25);
		getContentPane().add(lblTiempoTotal);

		// Tiempo previsto (solo si completó)
		if (completado) {
			JLabel lblTiempoPrevisto = new JLabel("Tiempo Previsto: " + formatearTiempo(tiempoPrevisto));
			lblTiempoPrevisto.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTiempoPrevisto.setForeground(Color.WHITE);
			lblTiempoPrevisto.setBounds(60, 155, 350, 25);
			getContentPane().add(lblTiempoPrevisto);

			// Diferencia de tiempo
			int diferencia = tiempoTotal - tiempoPrevisto;
			String textoTiempo = diferencia > 0
					? "Has tardado " + formatearTiempo(Math.abs(diferencia)) + " más de lo previsto."
					: "Has terminado" + formatearTiempo(Math.abs(diferencia)) + " más rápido!";

			JLabel lblDiferencia = new JLabel(textoTiempo);
			lblDiferencia.setFont(new Font("Arial", Font.ITALIC, 14));
			lblDiferencia.setForeground(diferencia > 0 ? new Color(255, 165, 0) : new Color(99, 179, 92));
			lblDiferencia.setBounds(60, 190, 350, 25);
			getContentPane().add(lblDiferencia);
		}

		// Ejercicios completados
		int porcentaje = ejerciciosTotales > 0 ? (ejerciciosCompletados * 100 / ejerciciosTotales) : 0;

		JLabel lblEjercicios = new JLabel(
				String.format("Ejercicios: %d/%d (%d%%)", ejerciciosCompletados, ejerciciosTotales, porcentaje));

		lblEjercicios.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEjercicios.setForeground(Color.WHITE);
		lblEjercicios.setBounds(60, completado ? 225 : 155, 350, 25);
		getContentPane().add(lblEjercicios);

		// Mensaje motivacional
		JLabel lblMensaje = new JLabel();
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("Arial", Font.ITALIC, 20));
		lblMensaje.setForeground(new Color(180, 180, 180));
		lblMensaje.setBounds(20, completado ? 270 : 210, 410, 50);

		lblMensaje.setText("<html><center>" + mensajeMotivacional() + "</center></html>");

		getContentPane().add(lblMensaje);

		// Botón Aceptar
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(150, 310, 150, 40);
		btnAceptar.setFont(new Font("Arial", Font.BOLD, 16));
		btnAceptar.setBackground(completado ? new Color(99, 179, 92) : new Color(70, 130, 180));
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFocusPainted(false);
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnAceptar);
	}

	private String formatearTiempo(int segundos) {
		int minutos = segundos / 60;
		int segs = segundos % 60;
		return String.format("%02d:%02d", minutos, segs);
	}

	private String mensajeMotivacional() {
		String[] mensajes = { "Mensaje Motivacional 37", "Al menos no estás jugando al LOL.",
				"Despues de esto unas cervecitas o que", "Si puedes leer esto ayuda estoy secuestrado",
				"Bipbipbopbip soy un robot bipbupbip", "¿Te has acordado de hacer el worlde hoy?",
				"Che un matesito wacho", "01010011 01001111 01010011", "ASCENSOOOOOOOOOR",
				"2 choclo, 3 de carne, 1 de queso, 2 de chocho, 4 de choclo, 2 de carne",
				"¿Sabías que los pulpos tienen tres corazones?", "Paraclorobenzilpirrolidina", "Ayuda", "OIIA OIIA",
				"Adivina que me encontrado en este ejercicio, el chocobollo",
				"Metodo pomodoro 50 minutos con el movil y 10 de ejercicio",
				"Giro 2 de mana azul, te tiro counterspell", "Balatro Balatrez", "¿Has bebido awita hoy?",
				"WOP WOP WOP", "Esto podría haber sido un sonic girando", "Kachaw", };
		return mensajes[(int) (Math.random() * mensajes.length)];
	}
}