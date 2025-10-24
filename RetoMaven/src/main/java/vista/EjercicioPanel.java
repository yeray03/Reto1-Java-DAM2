package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import modelo.dao.HistoricoDAO;
import pojos.Ejercicio;
import pojos.Historico;
import pojos.Serie;
import pojos.Usuario;
import pojos.Workout;

public class EjercicioPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblCronometroTotal;
	private JLabel lblEjercicio;
	private JLabel lblDescripcion;
	private JLabel lblWorkout;
	private JLabel lblTiempoSerie;
	private JTextArea txtDescanso;
	private JButton btnSerie1, btnSerie2, btnSerie3;
	private JButton btnControl, btnSalir;

	// private CronometroTread cronometroTotal;
	// private CronometroTread cronometroSerie;
	// private CronometroDescansoThread descansoThread;

	private Usuario usuario;
	private Workout workout;
	private int indiceEjercicioActual = 0;
	private Ejercicio ejercicioActual;

	private int indiceSerieActual = 0;
	private boolean enPausa = true;
	private boolean ejercicioCompletado = false;

	public EjercicioPanel(JFrame frame, Usuario usuario, Workout workout, Ejercicio primerEjercicio) {
		this.usuario = usuario;
		this.workout = workout;
		this.ejercicioActual = primerEjercicio;

		setBackground(Color.decode("#232637"));
		setLayout(null);

		lblCronometroTotal = new JLabel("00:00");
		lblCronometroTotal.setForeground(Color.WHITE);
		lblCronometroTotal.setFont(new Font("Arial", Font.BOLD, 24));
		lblCronometroTotal.setBounds(24, 32, 84, 36);
		add(lblCronometroTotal);

		lblEjercicio = new JLabel("Ejercicio: " + ejercicioActual.getNombre());
		lblEjercicio.setForeground(Color.WHITE);
		lblEjercicio.setFont(new Font("Arial", Font.BOLD, 16));
		lblEjercicio.setBounds(163, 36, 212, 32);
		add(lblEjercicio);

		lblWorkout = new JLabel("Workout: " + workout.getNombre());
		lblWorkout.setFont(new Font("Arial", Font.BOLD, 16));
		lblWorkout.setForeground(Color.WHITE);
		lblWorkout.setBounds(402, 34, 256, 36);
		add(lblWorkout);

		lblDescripcion = new JLabel(ejercicioActual.getDescripcion());
		lblDescripcion.setForeground(new Color(180, 180, 180));
		lblDescripcion.setBounds(207, 75, 168, 14);
		add(lblDescripcion);

		lblTiempoSerie = new JLabel("Serie: --:--");
		lblTiempoSerie.setForeground(Color.WHITE);
		lblTiempoSerie.setFont(new Font("Arial", Font.BOLD, 18));
		lblTiempoSerie.setBounds(24, 94, 89, 22);
		add(lblTiempoSerie);

		txtDescanso = new JTextArea("--:--");
		txtDescanso.setEditable(false);
		txtDescanso.setOpaque(false);
		txtDescanso.setForeground(Color.ORANGE);
		txtDescanso.setFont(new Font("Arial", Font.BOLD, 24));
		txtDescanso.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Descanso",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.ORANGE));
		txtDescanso.setBounds(24, 138, 129, 49);
		add(txtDescanso);

		btnSerie1 = new JButton();
		btnSerie1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSerie1.setBounds(234, 112, 205, 36);
		styleSerieButton(btnSerie1, false);
		add(btnSerie1);

		btnSerie2 = new JButton();
		btnSerie2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSerie2.setBounds(234, 159, 205, 36);
		styleSerieButton(btnSerie2, false);
		add(btnSerie2);

		btnSerie3 = new JButton();
		btnSerie3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSerie3.setBounds(234, 206, 205, 36);
		styleSerieButton(btnSerie3, false);
		add(btnSerie3);

		btnControl = new JButton("INICIAR");
		btnControl.setBounds(179, 293, 129, 49);
		btnControl.setBackground(new Color(99, 179, 92));
		btnControl.setForeground(Color.WHITE);
		btnControl.setFont(new Font("Arial", Font.BOLD, 18));
		btnControl.setOpaque(true);
		btnControl.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
		btnControl.setFocusPainted(false);
		btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(btnControl);

		btnControl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manejarBotonControl();
			}
		});

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(365, 290, 129, 55);
		btnSalir.setBackground(new Color(220, 60, 60));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
		btnSalir.setFocusPainted(false);
		btnSalir.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
		btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(btnSalir);

		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salirDelEjercicio(frame);
			}
		});
		cargarSeriesDelEjercicio();
	}

	// Manejar el botón de control (iniciar/pausar/reanudar)
	// Actualizar el texto del botón según el estado actual
	// Ejecutando = PAUSA , Pausa = REANUDAR , Completado = SIGUIENTE, Ultimo =
	// TERMINAR
	private void manejarBotonControl() {
		if (ejercicioCompletado) {
			siguienteEjercicio();
		} else if (enPausa) {
			iniciarEjercicio();
		} else {
			pausarEjercicio();
		}
	}

	// Inicia el ejercicio :P
	private void iniciarEjercicio() {
		enPausa = false;

		if (indiceSerieActual == 0) {
			iniciarSiguienteSerie();
		}

	}

	private void iniciarSiguienteSerie() {
		indiceSerieActual++;
		System.out.println("Iniciando serie: " + indiceSerieActual);

		SwingUtilities.invokeLater(() -> {
			btnControl.setText("PAUSAR");
			btnControl.setBackground(new Color(220, 60, 60));
		});
	}

	// Pausa el ejercicio :P
	private void pausarEjercicio() {
		enPausa = true;

		SwingUtilities.invokeLater(() -> {
			btnControl.setText("REANUDAR");
			btnControl.setBackground(new Color(99, 179, 92));
		});

	}

	// Pasa al siguiente ejercicio :P
	private void siguienteEjercicio() {
		indiceEjercicioActual++;
		System.out.println("Siguiente ejercicio: " + indiceEjercicioActual);
	}

	// Carga las series del ejercicio actual en los campos correspondientes
	// No se por qué son botones, me hacia gracia
	private void cargarSeriesDelEjercicio() {
		ArrayList<Serie> series = ejercicioActual.getSeries();

		if (series == null || series.isEmpty()) {
		}

		if (series.size() > 0) {
			Serie serie1 = series.get(0);
			btnSerie1.setText(serie1.getNombre() + " - " + serie1.getRepeticiones() + " reps");
			btnSerie1.setVisible(true);
		} else {
			btnSerie1.setVisible(false);
		}

		if (series.size() > 1) {
			Serie serie2 = series.get(1);
			btnSerie2.setText(serie2.getNombre() + " - " + serie2.getRepeticiones() + " reps");
			btnSerie2.setVisible(true);
		} else {
			btnSerie2.setVisible(false);
		}

		if (series.size() > 2) {
			Serie serie3 = series.get(2);
			btnSerie3.setText(serie3.getNombre() + " - " + serie3.getRepeticiones() + " reps");
			btnSerie3.setVisible(true);
		} else {
			btnSerie3.setVisible(false);
		}
	}

	// Esto lo estaba usando de prueba, dejenlo comentado
//    private ArrayList<Serie> generarSeriesDefault() {
//        ArrayList<Serie> series = new ArrayList<>();
//        series.add(new Serie("Serie 1", 10, 30, 15, ""));
//        series.add(new Serie("Serie 2", 10, 30, 15, ""));
//        series.add(new Serie("Serie 3", 10, 30, 0, ""));
//        ejercicioActual.setSeries(series);
//        return series;
//    }

	// Voy a usar esto para parar todos los cronometros cuando salga del ejercicio
	// En un futuro
	// Lejano
	// Y creo que para guardar el historico
	private void salirDelEjercicio(JFrame frame) {
		String mensaje = mensajeMotivacional();
		frame.setContentPane(new WorkoutsPanel(frame, usuario));
		frame.revalidate();

		JOptionPane.showMessageDialog(frame, mensaje, "Resumen del workaut", JOptionPane.INFORMATION_MESSAGE);
	}

	// Mensaje motivacional al salir del ejercicio
	private String mensajeMotivacional() {
		String[] mensajes = { "Mensaje motivacional 1", "Mensaje motivacional 2", "Mensaje motivacional 3", };
		int indice = (int) (Math.random() * mensajes.length);
		return mensajes[indice];

	}

	private void styleSerieButton(JButton boton, boolean activa) {
		boton.setHorizontalAlignment(JButton.LEFT);
		boton.setForeground(Color.WHITE);
		if (activa) {
			boton.setBackground(new Color(70, 130, 180));
		} else {
			boton.setBackground(new Color(60, 64, 68));
		}
		boton.setOpaque(true);
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createLineBorder(new Color(110, 115, 120)));
		boton.setEnabled(false);
	}
}