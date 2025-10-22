package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTML;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;

import controlador.WorkoutControlador;
import pojos.Usuario;
import pojos.Workout;

public class WorkoutsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel workoutModel;
	ArrayList<Workout> workouts;
	Usuario usuario;

	public WorkoutsPanel(JFrame frame, Usuario usuario) {
		this.usuario = usuario;

		JOptionPane.showMessageDialog(this, "Login correcto. ¡Bienvenido/a " + usuario.getNombre(), "Login",
				JOptionPane.INFORMATION_MESSAGE);
		setLayout(null);
		frame.setSize(648, 493);
		frame.setLocationRelativeTo(null);

		setBackground(Color.decode("#232637"));

		JLabel lblNewLabel = new JLabel(
				"BIENVENIDO/A " + usuario.getNombre().toUpperCase() + "  Nivel: " + usuario.getNivel());
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(20, 11, 228, 21);
		add(lblNewLabel);

		JLabel lblPerfil = new JLabel("");
		lblPerfil.setForeground(new Color(255, 255, 255));
		lblPerfil.setBounds(579, 11, 36, 36);
		add(lblPerfil);

		ImageIcon icon = new ImageIcon(getClass().getResource("/avatar.png"));
		ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(36, 36, java.awt.Image.SCALE_SMOOTH));
		lblPerfil.setIcon(scaledIcon);

		JLabel lblWorkouts = new JLabel("WORKOUTS");
		lblWorkouts.setForeground(new Color(255, 255, 255));
		lblWorkouts.setBounds(20, 82, 76, 21);
		add(lblWorkouts);

		JLabel lblNewLabel_1 = new JLabel("FILTRAR POR NIVEL: ");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(161, 83, 119, 21);
		add(lblNewLabel_1);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(286, 82, 88, 22);
		add(comboBox);

		comboBox.addItem("Default");

		String nivel = "";
		for (int i = 0; i <= usuario.getNivel(); i++) {
			nivel = String.valueOf(i);
			comboBox.addItem(nivel);
		}

		WorkoutControlador controlador = WorkoutControlador.getInstanceControlador();
		workouts = controlador.getWorkoutsHastaNivel(usuario.getNivel());

		// Initialize travelModel and travelTable
//
		workoutModel = new DefaultTableModel(new Object[] { "Nombre", "Nivel", "Numero de ejercicios", "Video" }, 0) {
			private static final long serialVersionUID = 1L; // si no lo pongo da warning

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Ninguna celda será editable
			}
		};
//
		table = new JTable(workoutModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFocusable(false); // asi no muestra el cuadrado del focus al pulsar una celda
		JScrollPane scrollPane = new JScrollPane(table);
		if (workouts != null) {
			for (Workout workout : workouts) {
				Object[] newRow = { workout.getNombre(), workout.getNivel(), workout.getNumEjercicios(),
						workout.getVideoUrl() };
				workoutModel.addRow(newRow);

			}
		}

		scrollPane.setBounds(20, 114, 595, 300);
		add(scrollPane);

		comboBox.addActionListener(e -> actualizarTablaPorNivel(controlador, comboBox));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row >= 0) {
					Workout workoutSelect = workouts.get(row);
					ArrayList<String> ejercicios = workoutSelect.getEjercicios();
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					panel.add(new JLabel("Ejercicios:"));
					for (String nombre : ejercicios) {
						panel.add(new JLabel(nombre));
					}

					int opcion = JOptionPane.showOptionDialog(WorkoutsPanel.this, panel,
							"Ejercicios de " + workoutSelect.getNombre(), JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Comenzar Ejercicio", "Cancelar" },
							"Comenzar Ejercicio");

					if (opcion == JOptionPane.YES_OPTION) {
						// INICIAR EJERCICIO
						frame.setContentPane(new EjercicioPanel(frame, usuario, workoutSelect));
						frame.validate();

					}
				}

			}

		});

	}

	private void actualizarTablaPorNivel(WorkoutControlador controlador, JComboBox<String> comboBox) {

		if (comboBox.getSelectedItem().toString() != "Default") {
			// cargar todos los workouts
			workoutModel.setRowCount(0); // limpiar tabla
			workouts = controlador.getWorkoutsPorNivel(Integer.parseInt(comboBox.getSelectedItem().toString()));
			if (workouts != null) {
				for (Workout workout : workouts) {
					Object[] newRow = { workout.getNombre(), workout.getNivel(), workout.getNumEjercicios(),
							workout.getVideoUrl() };
					workoutModel.addRow(newRow);
				}
			}

		} else {
			// cargar todos los workouts
			workoutModel.setRowCount(0); // limpiar tabla
			workouts = controlador.getWorkoutsHastaNivel(usuario.getNivel());
			if (workouts != null) {
				for (Workout workout : workouts) {
					Object[] newRow = { workout.getNombre(), workout.getNivel(), workout.getNumEjercicios(),
							workout.getVideoUrl() };
					workoutModel.addRow(newRow);
				}
			}
		}

	}

}