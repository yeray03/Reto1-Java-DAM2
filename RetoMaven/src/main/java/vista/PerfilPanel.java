package vista;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controlador.UsuarioControlador;
import controlador.RegistroControlador;
import pojos.Usuario;
import pojos.Workout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
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
	private JTextField txtNickname;
	private boolean edit = false;

	public PerfilPanel(JFrame frame, Usuario usuario) {
		this.usuario = usuario;

		setLayout(null);
		frame.setSize(648, 493);
		frame.setLocationRelativeTo(null);

		setBackground(Color.decode("#232637"));

		JLabel lblNickname = new JLabel("Nombre de usuario:");
		lblNickname.setForeground(new Color(255, 255, 255));
		lblNickname.setBounds(132, 59, 121, 14);
		add(lblNickname);

		JLabel lblIntro = new JLabel("PERFIL DE " + usuario.getNickname().toUpperCase());
		lblIntro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIntro.setForeground(new Color(255, 255, 255));
		lblIntro.setBackground(new Color(255, 255, 255));
		lblIntro.setBounds(205, 11, 234, 37);
		add(lblIntro);

		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setBounds(132, 101, 109, 14);
		add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(new Color(255, 255, 255));
		lblApellidos.setBounds(132, 141, 109, 14);
		add(lblApellidos);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(132, 180, 109, 14);
		add(lblEmail);

		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(new Color(255, 255, 255));
		lblContraseña.setBounds(132, 217, 109, 14);
		add(lblContraseña);

		JLabel lblNacimiento = new JLabel("Fecha de nacimiento:");
		lblNacimiento.setForeground(new Color(255, 255, 255));
		lblNacimiento.setBounds(132, 258, 109, 14);
		add(lblNacimiento);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(114, 385, 127, 37);
		add(btnVolver);

		JButton btnLogout = new JButton("CERRAR SESIÓN");
		btnLogout.setBounds(414, 385, 127, 37);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new LoginPanel(frame));
				frame.validate();
				frame.setSize(400, 400);
				frame.setLocationRelativeTo(null);
			}
		});
		add(btnLogout);

		txtNickname = new JTextField(usuario.getNickname());
		txtNickname.setEnabled(false);
		txtNickname.setEditable(false);
		txtNickname.setColumns(10);
		txtNickname.setBounds(258, 59, 226, 22);
		add(txtNickname);

		txtNombre = new JTextField(usuario.getNombre());
		txtNombre.setEditable(false);
		txtNombre.setBounds(258, 98, 226, 22);
		add(txtNombre);
		txtNombre.setColumns(10);

		txtApellidos = new JTextField(usuario.getApellidos());
		txtApellidos.setEditable(false);
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(258, 138, 226, 22);
		add(txtApellidos);

		txtEmail = new JTextField(usuario.getEmail());
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(258, 177, 226, 22);
		add(txtEmail);

		txtContraseña = new JTextField(usuario.getContrasena());
		txtContraseña.setEditable(false);
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(258, 214, 226, 22);
		add(txtContraseña);

		txtNacimiento = new JTextField(usuario.getFechaNacimiento());
		txtNacimiento.setEditable(false);
		txtNacimiento.setColumns(10);
		txtNacimiento.setBounds(258, 255, 226, 22);
		add(txtNacimiento);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.setVisible(false);
		btnCancelar.setBounds(414, 317, 127, 41);
		add(btnCancelar);

		JButton btnEdit_Save = new JButton("Activar Edición");
		btnEdit_Save.setBounds(114, 317, 136, 41);
		add(btnEdit_Save);

		// Acción botón cancelar
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit = false;
				resetCampos();
				btnCancelar.setEnabled(false);
				btnCancelar.setVisible(false);
				btnEdit_Save.setText("Activar Edición");
				btnLogout.setEnabled(true);
				btnLogout.setVisible(true);
				btnVolver.setEnabled(true);
				btnVolver.setVisible(true);
			}
		});

		// Acción botón editar/guardar
		btnEdit_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if (!edit) {
					edit = true;
					txtNombre.setEditable(true);
					txtApellidos.setEditable(true);
					txtEmail.setEditable(true);
					txtContraseña.setEditable(true);
					txtNacimiento.setEditable(true);
					btnCancelar.setEnabled(true);
					btnCancelar.setVisible(true);
					btnLogout.setEnabled(false);
					btnLogout.setVisible(false);
					btnVolver.setEnabled(false);
					btnVolver.setVisible(false);

					btnEdit_Save.setText("Guardar Cambios");
				} else {
					if (!isValidDate(txtNacimiento.getText())) {
						JOptionPane.showMessageDialog(frame, "Formato de fecha inválido. Use DD/MM/YYYY.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					edit = false;
					RegistroControlador controlador = RegistroControlador.getInstance();
					usuario.setNickname(txtNickname.getText());
					usuario.setNombre(txtNombre.getText());
					usuario.setApellidos(txtApellidos.getText());
					usuario.setEmail(txtEmail.getText());
					usuario.setContrasena(txtContraseña.getText());
					usuario.setFechaNacimiento(txtNacimiento.getText());

					String resultado = controlador.actualizarUsuario(usuario);
					if (resultado.equals("Usuario actualizado correctamente.")) {
						JOptionPane.showMessageDialog(frame, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
						frame.setContentPane(new LoginPanel(frame));
					}
					btnEdit_Save.setText("Activar Edición");
					txtNombre.setEditable(false);
					txtApellidos.setEditable(false);
					txtEmail.setEditable(false);
					txtContraseña.setEditable(false);
					txtNacimiento.setEditable(false);
					btnCancelar.setEnabled(false);
					btnCancelar.setVisible(false);
					btnLogout.setEnabled(true);
					btnLogout.setVisible(true);
					btnVolver.setEnabled(true);
					btnVolver.setVisible(true);
				}
			}
		});

		// Acción botón volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new WorkoutsPanel(frame, usuario));
				frame.validate();
			}
		});
	}

	private void resetCampos() {
		try {
			UsuarioControlador controlador = UsuarioControlador.getInstance();
			usuario = controlador.buscarPorNick(usuario.getNickname());
			txtNombre.setText(usuario.getNombre());
			txtApellidos.setText(usuario.getApellidos());
			txtEmail.setText(usuario.getEmail());
			txtContraseña.setText(usuario.getContrasena());
			txtNacimiento.setText(usuario.getFechaNacimiento());

			txtNombre.setEditable(false);
			txtApellidos.setEditable(false);
			txtEmail.setEditable(false);
			txtContraseña.setEditable(false);
			txtNacimiento.setEditable(false);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ExecutionException exe) {
			exe.printStackTrace();
		} catch (InterruptedException inte) {
			inte.printStackTrace();
		}
	}

	// Validar formato de fecha, importamos librería Date de java.util
	private boolean isValidDate(String dateStr) {
		if (dateStr == null)
			return false;
		dateStr = dateStr.trim();
		java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			java.time.LocalDate.parse(dateStr, fmt);
			return true;
		} catch (java.time.format.DateTimeParseException e) {
			return false;
		}
	}
}