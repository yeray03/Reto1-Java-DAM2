package vista;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.Font;
import javax.swing.JButton;

public class PerfilPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel workoutModel;
	ArrayList<Workout> workouts;
	Usuario usuario;

	public PerfilPanel(JFrame frame, Usuario usuario) {
		this.usuario = usuario;

		setLayout(null);
		frame.setSize(648, 493);
		frame.setLocationRelativeTo(null);

		setBackground(Color.decode("#232637"));

		JLabel lblIntro = new JLabel(
				"PERFIL DE <DYNAMIC>");
		lblIntro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIntro.setForeground(new Color(255, 255, 255));
		lblIntro.setBackground(new Color(255, 255, 255));
		lblIntro.setBounds(205, 11, 234, 37);
		add(lblIntro);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setBounds(89, 70, 109, 14);
		add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(new Color(255, 255, 255));
		lblApellidos.setBounds(89, 110, 109, 14);
		add(lblApellidos);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(89, 149, 109, 14);
		add(lblEmail);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(new Color(255, 255, 255));
		lblContraseña.setBounds(89, 186, 109, 14);
		add(lblContraseña);
		
		JLabel lblNacimiento = new JLabel("Fecha de nacimiento:");
		lblNacimiento.setForeground(new Color(255, 255, 255));
		lblNacimiento.setBounds(89, 227, 109, 14);
		add(lblNacimiento);
		
		JButton btnNewButton = new JButton("VOLVER");
		btnNewButton.setBounds(100, 369, 127, 37);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CERRAR SESIÓN");
		btnNewButton_1.setBounds(373, 369, 127, 37);
		add(btnNewButton_1);

		ImageIcon icon = new ImageIcon(getClass().getResource("/avatar.png"));
		ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(36, 36, java.awt.Image.SCALE_SMOOTH));

	}
}