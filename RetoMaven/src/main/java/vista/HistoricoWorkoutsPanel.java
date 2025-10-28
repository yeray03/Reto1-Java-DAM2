package vista;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controlador.HistoricoControlador;
import pojos.Historico;
import pojos.Usuario;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HistoricoWorkoutsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel workoutModel;
	ArrayList<Historico> historicos;
	Usuario usuario;

	public HistoricoWorkoutsPanel(JFrame frame, Usuario usuario) {
		this.usuario = usuario;

		setLayout(null);
		frame.setSize(950, 493);
		frame.setLocationRelativeTo(null);

		setBackground(Color.decode("#232637"));

		JLabel lblNewLabel = new JLabel("Historial de Workouts de " + usuario.getNickname().toUpperCase());
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(20, 11, 288, 21);
		add(lblNewLabel);

		JLabel lblPerfil = new JLabel("");
		lblPerfil.setForeground(new Color(255, 255, 255));
		lblPerfil.setBounds(579, 11, 36, 36);
		add(lblPerfil);

		HistoricoControlador controlador = HistoricoControlador.getInstance();
		historicos = controlador.getHistoricoUsuario(usuario);

		// Crear el modelo de la tabla
		workoutModel = new DefaultTableModel(new Object[] { "Nombre Workout", "Nivel", "Tiempo Total",
				"Tiempo previsto", "Fecha", "% Ejercicios completados" }, 0) {
			private static final long serialVersionUID = 1L; // si no lo pongo da warning

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Ninguna celda será editable
			}
		};
		// Crear la tabla con el modelo
		table = new JTable(workoutModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFocusable(false); // asi no muestra el cuadrado del focus al pulsar una celda
		JScrollPane scrollPane = new JScrollPane(table);
		if (historicos != null) {
			for (Historico historico : historicos) {
				Object[] newRow = { historico.getWorkoutNombre(), historico.getNivel(), historico.getTiempoTotal(),
						historico.getTiempoPrevisto(), historico.getFecha(),
						historico.getPorcentajeCompletado() + "%" };
				workoutModel.addRow(newRow);

			}
		}

		scrollPane.setBounds(20, 114, 879, 301);
		add(scrollPane);

		// Botón Atrás
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new WorkoutsPanel(frame, usuario));
				frame.validate();
			}
		});
		btnAtras.setBounds(20, 59, 89, 23);
		add(btnAtras);

	}

}