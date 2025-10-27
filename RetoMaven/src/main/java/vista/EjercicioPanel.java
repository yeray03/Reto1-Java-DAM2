package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import modelo.CronometroDescansoThread;
import modelo.CronometroThread;
import pojos.Ejercicio;
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

	private CronometroThread cronometroTotal;
	private CronometroThread cronometroSerie;
	private Thread threadCronometroSerie;
	private CronometroDescansoThread cronometroDescanso;

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

	// Codigou de Borja
//	// private void botonIniciarActionPerformed(java.awt.event.ActionEvent evt)
//	// {//GEN-FIRST:event_btnIniciarActionPerformed
//
//	this.botonIniciar.setEnabled(false);
//
//	RelojCronometro relojCronometro = new RelojCronometro(23, 59, 50);
//	relojCronometro.addObserver(this);
//	Thread thread = new Thread(relojCronometro);
//	thread.start();
//

	// Inicia el ejercicio :P
	private void iniciarEjercicio() {
		enPausa = false;

		// Crea el hilo si no existe
		if (cronometroTotal == null) {
			CronometroThread cronometroTotalRunnable = new CronometroThread(lblCronometroTotal, 0, true);
			Thread thread = new Thread(cronometroTotalRunnable);
			thread.start();
			System.out.println("Iniciando cronómetro total");
			cronometroTotal = cronometroTotalRunnable;
		} else {
			cronometroTotal.reanudar();
		}

		// Inicia la primera serie si es la primera vez
		if (indiceSerieActual == 0) {
			iniciarSiguienteSerie();
		} else {
			// Reanuda la serie actual
			cronometroSerie.reanudar();
			cronometroDescanso.reanudar();
		}

	}

	// Fiesta
	private void iniciarSiguienteSerie() {
		ArrayList<Serie> series = ejercicioActual.getSeries();

		// Verifica si hay más series
		if (indiceSerieActual >= series.size()) {
			System.out.println("Todas las series completadas del ejercicio: " + ejercicioActual.getNombre());
			ejercicioCompletado = true;
			manejarBotonControl();
			return;
		}

		Serie serieActual = series.get(indiceSerieActual);
		System.out.println("Iniciando serie: " + serieActual.getNombre());

		// Marcar la serie actual como activa en la UI
		marcarSerieActiva(indiceSerieActual);

		// Crear e iniciar el cronómetro de la serie (DESCENDENTE)
		cronometroSerie = new CronometroThread(lblTiempoSerie, serieActual.getTiempoSerie(), false);
		cronometroSerie.setPanel(this);
		threadCronometroSerie = new Thread(cronometroSerie);
		threadCronometroSerie.start();

		SwingUtilities.invokeLater(() -> {
			btnControl.setText("PAUSAR");
			btnControl.setBackground(new Color(220, 60, 60));
		});
	}

	// Marca la serie activa en la UI
	private void marcarSerieActiva(int indiceSerieActual2) {
		// Todos a falso al empezar el ejercicio
		styleSerieButton(btnSerie1, false);
		styleSerieButton(btnSerie2, false);
		styleSerieButton(btnSerie3, false);

		// Marcar la serie activa
		if (indiceSerieActual2 == 0) {
			styleSerieButton(btnSerie1, true);
		} else if (indiceSerieActual2 == 1) {
			styleSerieButton(btnSerie2, true);
		} else if (indiceSerieActual2 == 2) {
			styleSerieButton(btnSerie3, true);
		}

	}

	// Pausa el ejercicio :P
	private void pausarEjercicio() {
		enPausa = true;
		cronometroTotal.pausar();
		cronometroSerie.pausar();
		cronometroDescanso.pausar();

		SwingUtilities.invokeLater(() -> {
			btnControl.setText("REANUDAR");
			btnControl.setBackground(new Color(99, 179, 92));
		});

	}

	public void alFinalizarSerie() {
		ArrayList<Serie> series = ejercicioActual.getSeries();
		Serie serieActual = series.get(indiceSerieActual);

		System.out.println("Serie finalizada: " + serieActual.getNombre());

		// Marcar la serie como completada VISUALMENTE
		marcarSerieComoCompletada(serieActual);

		// Aquí hay que manejar lo que sucede al finalizar una serie
		indiceSerieActual++;

		// Verificar si hay más series
		if (indiceSerieActual < series.size()) {
			//Hay mas series, iniciar descanso
			iniciarDescanso(serieActual.getTiempoDescanso());
		} else {
			//No hay mas series, ejercicio completado
			System.out.println("Exerxixio completatedo: " + ejercicioActual.getNombre());
			ejercicioCompletado = true;
			manejarBotonControl();
		}
	}

	private void iniciarDescanso(int tiempoDescanso) {
		if (tiempoDescanso <= 0)
			iniciarSiguienteSerie();

		System.out.println("Iniciando descanso de " + tiempoDescanso + " segundos");

		// TODO: Meter aqui el cronometro de descanso
	}

	private void marcarSerieComoCompletada(Serie serieActual) {
		JButton boton = null;

		boton.setBackground(new Color(34, 139, 34));
		boton.setForeground(Color.WHITE);
	}
	
	//Actualizar el boton de los ejercicios
	private void actualizarBotonControl() {
		
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
			return;
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

	// Formatear segundos a mm:ss
	private String formatearTiempo(int segundos) {
		int minutos = segundos / 60;
		int segundosRestantes = segundos % 60;
		return String.format("%02d:%02d", minutos, segundosRestantes);
	}

	// Voy a usar esto para parar todos los cronometros cuando salga del ejercicio
	// En un futuro
	// Lejano
	// Y creo que para guardar el historico
	private void salirDelEjercicio(JFrame frame) {
		// Parar los cronometros
		cronometroTotal.detener();
		cronometroSerie.detener();

		// Guardar el historico y muestrar mensaje motivacional
		String mensaje = mensajeMotivacional();
		frame.setContentPane(new WorkoutsPanel(frame, usuario));
		frame.revalidate();

		JOptionPane.showMessageDialog(frame, mensaje, "Resumen del workaut", JOptionPane.INFORMATION_MESSAGE);
	}

	// Mensaje motivacional al salir del ejercicio
	private String mensajeMotivacional() {
		String[] mensajes = { "Mensaje motivacional 1", "Mensaje motivacional 2", "Mensaje motivacional 3", };
		int mensaje = (int) (Math.random() * mensajes.length);
		return mensajes[mensaje];

	}

	// Estilo para las series
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