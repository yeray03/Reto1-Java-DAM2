package vista;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controlador.RegistroControlador;
import pojos.Usuario;
import pojos.Workout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PerfilPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	ArrayList<Workout> workouts;
	Usuario usuario;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtEmail;
	private JTextField txtContraseña;
	private JTextField txtNacimiento;

	public PerfilPanel(JFrame frame, Usuario usuario) {
		this.usuario = usuario;

		setLayout(null);
		frame.setSize(648, 493);
		frame.setLocationRelativeTo(null);

		setBackground(Color.decode("#232637"));

		JLabel lblIntro = new JLabel("PERFIL DE <DYNAMIC>");
		lblIntro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIntro.setForeground(new Color(255, 255, 255));
		lblIntro.setBackground(new Color(255, 255, 255));
		lblIntro.setBounds(205, 11, 234, 37);
		add(lblIntro);

		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setBounds(139, 74, 109, 14);
		add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(new Color(255, 255, 255));
		lblApellidos.setBounds(139, 114, 109, 14);
		add(lblApellidos);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(139, 153, 109, 14);
		add(lblEmail);

		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(new Color(255, 255, 255));
		lblContraseña.setBounds(139, 190, 109, 14);
		add(lblContraseña);

		JLabel lblNacimiento = new JLabel("Fecha de nacimiento:");
		lblNacimiento.setForeground(new Color(255, 255, 255));
		lblNacimiento.setBounds(139, 231, 109, 14);
		add(lblNacimiento);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(100, 369, 127, 37);
		add(btnVolver);

		JButton btnNewButton_1 = new JButton("CERRAR SESIÓN");
		btnNewButton_1.setBounds(421, 369, 127, 37);
		add(btnNewButton_1);

		txtNombre = new JTextField(usuario.getNombre());
		txtNombre.setEditable(false);
		txtNombre.setBounds(265, 71, 160, 20);
		add(txtNombre);
		txtNombre.setColumns(10);

		txtApellidos = new JTextField(usuario.getApellidos());
		txtApellidos.setEditable(false);
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(265, 111, 160, 20);
		add(txtApellidos);

		txtEmail = new JTextField(usuario.getEmail());
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(265, 150, 160, 20);
		add(txtEmail);

		txtContraseña = new JTextField(usuario.getContrasena());
		txtContraseña.setEditable(false);
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(265, 187, 160, 20);
		add(txtContraseña);

		txtNacimiento = new JTextField(usuario.getFechaNacimiento());
		txtNacimiento.setEditable(false);
		txtNacimiento.setColumns(10);
		txtNacimiento.setBounds(265, 228, 160, 20);
		add(txtNacimiento);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.setVisible(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetCampos();
				btnCancelar.setEnabled(false);
				btnCancelar.setVisible(false);
			}
		});
		btnCancelar.setBounds(421, 294, 99, 37);
		add(btnCancelar);

		JButton btnEdit_Save = new JButton("Activar Edición");
		btnEdit_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				txtNombre.setEditable(true);
				txtApellidos.setEditable(true);
				txtEmail.setEditable(true);
				txtContraseña.setEditable(true);
				txtNacimiento.setEditable(true);
				btnCancelar.setEnabled(true);
				btnCancelar.setVisible(true);

				btnEdit_Save.setText("Guardar Cambios");
				btnEdit_Save.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent b) {
						RegistroControlador controlador = RegistroControlador.getInstance();
						
						Usuario usuarioActualizado = new Usuario(txtNombre.getText(), txtApellidos.getText(), txtEmail.getText(),
								txtContraseña.getText(), txtNacimiento.getText());
						
						controlador.actualizarUsuario(usuarioActualizado, usuario.getEmail());

					}
				});

			}
		});
		btnEdit_Save.setBounds(117, 294, 109, 37);
		add(btnEdit_Save);

	}

	private void resetCampos() {
		txtNombre.setText(usuario.getNombre());
		txtApellidos.setText(usuario.getApellidos());
		txtEmail.setText(usuario.getEmail());
		txtContraseña.setText(usuario.getContrasena());
		txtNacimiento.setText(usuario.getFechaNacimiento());

	}

}