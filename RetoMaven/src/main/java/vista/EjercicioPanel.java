package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controlador.ConexionControlador;
import gestor.GestorFicheros;
import modelo.CronometroDescansoThread;
import modelo.CronometroThread;
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

	private JFrame JFrame;

	public EjercicioPanel(JFrame frame, Usuario usuario, Workout workout, Ejercicio primerEjercicio) {
		this.JFrame = frame;
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
		lblTiempoSerie.setBounds(24, 94, 120, 22);
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

	private void manejarBotonControl() {
		if (ejercicioCompletado) {
			siguienteEjercicio();
		} else if (enPausa) {
			iniciarEjercicio();
		} else {
			pausarEjercicio();
		}
	}

	private void iniciarEjercicio() {
		enPausa = false;

		// Crea el cronómetro total si no existe
		if (cronometroTotal == null) {
			CronometroThread cronometroTotalRunnable = new CronometroThread(lblCronometroTotal, 0, true);
			Thread thread = new Thread(cronometroTotalRunnable);
			thread.start();
			cronometroTotal = cronometroTotalRunnable;
		} else {
			cronometroTotal.reanudar();
		}

		// Reanuda o inicia según estado actual
		if (cronometroSerie != null && cronometroSerie.isRunning()) {
			cronometroSerie.reanudar();
		} else if (cronometroDescanso != null && cronometroDescanso.isRunning()) {
			cronometroDescanso.reanudar();
		} else {
			iniciarSiguienteSerie();
		}

		btnControl.setText("PAUSAR");
		btnControl.setBackground(new Color(220, 60, 60));
	}

	private void iniciarSiguienteSerie() {
		ArrayList<Serie> series = ejercicioActual.getSeries();

		if (indiceSerieActual >= series.size()) {
			ejercicioCompletado = true;
			manejarBotonControl();
			return;
		}

		Serie serieActual = series.get(indiceSerieActual);
		marcarSerieActiva(indiceSerieActual);

		cronometroSerie = new CronometroThread(lblTiempoSerie, serieActual.getTiempoSerie(), false);
		cronometroSerie.setPanel(this);
		threadCronometroSerie = new Thread(cronometroSerie);
		threadCronometroSerie.start();

		btnControl.setText("PAUSAR");
		btnControl.setBackground(new Color(220, 60, 60));
	}

	private void marcarSerieActiva(int serieActiva) {
		styleSerieButton(btnSerie1, false);
		styleSerieButton(btnSerie2, false);
		styleSerieButton(btnSerie3, false);

		if (serieActiva == 0)
			styleSerieButton(btnSerie1, true);
		else if (serieActiva == 1)
			styleSerieButton(btnSerie2, true);
		else if (serieActiva == 2)
			styleSerieButton(btnSerie3, true);
	}

	private void pausarEjercicio() {
		enPausa = true;
		pausarCronometros();
		btnControl.setText("REANUDAR");
		btnControl.setBackground(new Color(99, 179, 92));
	}

	public void alFinalizarSerie() {
		ArrayList<Serie> series = ejercicioActual.getSeries();
		Serie serieActual = series.get(indiceSerieActual);

		marcarSerieComoCompletada(indiceSerieActual);
		indiceSerieActual++;

		if (indiceSerieActual < series.size()) {
			iniciarDescanso(serieActual.getTiempoDescanso());
		} else {
			ejercicioCompletado = true;
			actualizarBotonControl();
		}
	}

	private void iniciarDescanso(int tiempoDescanso) {
		if (tiempoDescanso <= 0) {
			iniciarSiguienteSerie();
			return;
		}

		cronometroDescanso = new CronometroDescansoThread(txtDescanso, tiempoDescanso);
		cronometroDescanso.setPanel(this);
		Thread threadCronometroDescanso = new Thread(cronometroDescanso);
		threadCronometroDescanso.start();
	}

	private void marcarSerieComoCompletada(int serieActual) {
		JButton boton = null;
		if (serieActual == 0)
			boton = btnSerie1;
		else if (serieActual == 1)
			boton = btnSerie2;
		else if (serieActual == 2)
			boton = btnSerie3;

		if (boton != null) {
			boton.setBackground(new Color(34, 139, 34));
			boton.setForeground(Color.WHITE);
		}
	}

	private void actualizarBotonControl() {
		if (!ejercicioCompletado)
			return;

		if (indiceEjercicioActual >= workout.getEjercicios().size() - 1) {
			btnControl.setText("TERMINAR");
		} else {
			btnControl.setText("SIGUIENTE");
		}
		btnControl.setBackground(new Color(70, 130, 180));
	}

	private void siguienteEjercicio() {
		indiceEjercicioActual++;

		if (indiceEjercicioActual >= workout.getEjercicios().size()) {
			int tiempoTotal = 0;
			if (cronometroTotal != null) {
				tiempoTotal = cronometroTotal.getSegundos();
			}

			detenerCronometros();
			mostrarResumenFinal(true, tiempoTotal);
			return;
		}

		ejercicioActual = workout.getEjercicios().get(indiceEjercicioActual);
		indiceSerieActual = 0;
		ejercicioCompletado = false;

		lblEjercicio.setText("Ejercicio: " + ejercicioActual.getNombre());
		lblDescripcion.setText(ejercicioActual.getDescripcion());
		lblTiempoSerie.setText("Serie: --:--");

		cargarSeriesDelEjercicio();
		iniciarSiguienteSerie();
	}

	public void alFinalizarDescanso() {
		iniciarSiguienteSerie();
	}

	private void cargarSeriesDelEjercicio() {
		ArrayList<Serie> series = ejercicioActual.getSeries();
		if (series == null || series.isEmpty())
			return;

		if (series.size() > 0) {
			Serie s1 = series.get(0);
			btnSerie1.setText(s1.getNombre() + " - " + s1.getRepeticiones() + " reps");
			btnSerie1.setVisible(true);
		} else
			btnSerie1.setVisible(false);

		if (series.size() > 1) {
			Serie s2 = series.get(1);
			btnSerie2.setText(s2.getNombre() + " - " + s2.getRepeticiones() + " reps");
			btnSerie2.setVisible(true);
		} else
			btnSerie2.setVisible(false);

		if (series.size() > 2) {
			Serie s3 = series.get(2);
			btnSerie3.setText(s3.getNombre() + " - " + s3.getRepeticiones() + " reps");
			btnSerie3.setVisible(true);
		} else
			btnSerie3.setVisible(false);
	}

	private void salirDelEjercicio(JFrame frame) {
		int tiempoTotalGuardado = 0;
		if (cronometroTotal != null) {
			tiempoTotalGuardado = cronometroTotal.getSegundos();
		}

		detenerCronometros();

		boolean workoutCompletado = indiceEjercicioActual >= workout.getEjercicios().size();
		mostrarResumenFinal(workoutCompletado, tiempoTotalGuardado);
	}

	private void mostrarResumenFinal(boolean completado, int tiempoTotal) {
		guardarHistorico(completado, tiempoTotal);

		// Calcular tiempo previsto
		int tiempoPrevisto = calcularTiempoPrevisto();

		// Crear y mostrar diálogo de resumen
		ResumenWorkout dialogo = new ResumenWorkout(JFrame, completado, workout.getNombre(), tiempoTotal,
				tiempoPrevisto, indiceEjercicioActual, workout.getNumEjercicios());
		dialogo.setVisible(true);

		// Volver a la pantalla de workouts
		JFrame.setContentPane(new WorkoutsPanel(JFrame, usuario));
		JFrame.revalidate();
	}

	private void guardarHistorico(boolean completado, int tiempoTotal) {
		try {
			if (ConexionControlador.getInstance().comprobarConexion()) { // si hay conexion
				HistoricoDAO historicoDAO = new HistoricoDAO();

				LocalDateTime fechaActual = LocalDateTime.now();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String fecha = fechaActual.format(formato);

				int tiempoPrevisto = calcularTiempoPrevisto();

				Historico historico = new Historico(workout.getNombre(), workout.getNivel(), fecha, tiempoTotal,
						tiempoPrevisto, indiceEjercicioActual, workout.getNumEjercicios());

				historicoDAO.addHistorico(usuario.getNickname(), historico);
				GestorFicheros.getInstance().guardarHistoricoUsuario(usuario, historico);

				if (completado) {
					System.out.println("Histórico guardado: Workout completado.");
				} else {
					System.out.println("Histórico guardado: Workout interrumpido con progreso " + indiceEjercicioActual
							+ "/" + workout.getNumEjercicios());
				}
			} else {
				LocalDateTime fechaActual = LocalDateTime.now();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String fecha = fechaActual.format(formato);
				int tiempoPrevisto = calcularTiempoPrevisto();
				Historico historico = new Historico(workout.getNombre(), workout.getNivel(), fecha, tiempoTotal,
						tiempoPrevisto, indiceEjercicioActual, workout.getNumEjercicios());
				GestorFicheros.getInstance().guardarHistoricoUsuario(usuario, historico);
				if (completado) {
					System.out.println("Histórico guardado: Workout completado.");
				} else {
					System.out.println("Histórico guardado: Workout interrumpido con progreso " + indiceEjercicioActual
							+ "/" + workout.getNumEjercicios());
				}
			}
		} catch (Exception e) {
			System.out.println("Error al guardar el historico: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private int calcularTiempoPrevisto() {
		int total = 0;
		ArrayList<Ejercicio> ejercicios = workout.getEjercicios();
		for (Ejercicio ejercicio : ejercicios) {
			total += calcularTiempoEjercicio(ejercicio);
		}
		return total;
	}

	private int calcularTiempoEjercicio(Ejercicio ejercicio) {
		int tiempo = 0;
		ArrayList<Serie> series = ejercicio.getSeries();
		if (series == null)
			return 0;
		for (Serie serie : series) {
			tiempo += serie.getTiempoSerie();
			tiempo += serie.getTiempoDescanso();
		}
		return tiempo;
	}

	private void pausarCronometros() {
		if (cronometroTotal != null)
			cronometroTotal.pausar();

		if (cronometroSerie != null)
			cronometroSerie.pausar();

		if (cronometroDescanso != null)
			cronometroDescanso.pausar();
	}

	private void detenerCronometros() {
		if (cronometroTotal != null) {
			cronometroTotal.detener();
			cronometroTotal = null;
		}
		if (cronometroSerie != null) {
			cronometroSerie.detener();
			cronometroSerie = null;
		}
		if (threadCronometroSerie != null && threadCronometroSerie.isAlive()) {
			threadCronometroSerie.interrupt();
			threadCronometroSerie = null;
		}
		if (cronometroDescanso != null) {
			cronometroDescanso.detener();
			cronometroDescanso = null;
		}
	}

	private void styleSerieButton(JButton boton, boolean activa) {
		boton.setHorizontalAlignment(JButton.LEFT);
		boton.setForeground(Color.WHITE);
		boton.setBackground(activa ? new Color(70, 130, 180) : new Color(60, 64, 68));
		boton.setOpaque(true);
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createLineBorder(new Color(110, 115, 120)));
		boton.setEnabled(false);
	}
}