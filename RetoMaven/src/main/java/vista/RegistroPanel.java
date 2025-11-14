package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controlador.RegistroControlador;
import pojos.Usuario;

public class RegistroPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField nameField, surnameField, emailField;
	private JPasswordField passwordField, repeatPasswordField;
	private JTextField birthDateField;
	private JButton registerButton, backButton;
	private boolean placeholder = true;
	private JLabel lblNickname;
	private JTextField txtNick;

	public RegistroPanel(JFrame frame) {
		frame.setSize(530, 350);
		setBackground(new Color(32, 32, 32));
		// Inicializar componentes
		nameField = new JTextField();
		nameField.setBounds(262, 68, 252, 24);
		surnameField = new JTextField();
		surnameField.setBounds(262, 102, 252, 24);
		emailField = new JTextField();
		emailField.setBounds(262, 136, 252, 24);
		passwordField = new JPasswordField();
		passwordField.setBounds(262, 170, 252, 24);
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBounds(262, 204, 252, 24);

		birthDateField = new JTextField("DD/MM/AAAA");
		birthDateField.setBounds(262, 238, 252, 24);
		birthDateField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (placeholder) {
					birthDateField.setText("");
					placeholder = false;
				}
			}

		});
		registerButton = new JButton("Registrar");
		registerButton.setBounds(0, 273, 252, 24);
		backButton = new JButton("Volver");
		backButton.setBounds(262, 273, 252, 24);
		setLayout(null);

		// Etiquetas
		JLabel label = new JLabel("Nombre:");
		label.setBounds(0, 68, 252, 24);
		add(label);
		add(nameField);
		JLabel label_1 = new JLabel("Apellidos:");
		label_1.setBounds(0, 102, 252, 24);
		add(label_1);
		add(surnameField);
		JLabel label_2 = new JLabel("Email:");
		label_2.setBounds(0, 136, 252, 24);
		add(label_2);
		add(emailField);
		JLabel label_3 = new JLabel("Contraseña:");
		label_3.setBounds(0, 170, 252, 24);
		add(label_3);
		add(passwordField);
		JLabel label_4 = new JLabel("Repetir Contraseña:");
		label_4.setBounds(0, 204, 252, 24);
		add(label_4);
		add(repeatPasswordField);
		JLabel label_5 = new JLabel("Fecha de nacimiento:");
		label_5.setBounds(0, 238, 252, 24);
		add(label_5);
		add(birthDateField);

		add(registerButton);
		add(backButton);

		lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(0, 33, 252, 24);
		add(lblNickname);

		txtNick = new JTextField();
		txtNick.setBounds(262, 33, 252, 24);
		add(txtNick);

		for (Component c : getComponents()) {
			if (c instanceof JLabel) {
				c.setForeground(new Color(220, 220, 220));
			}
		}

		// Acción botón registro
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickname = txtNick.getText().trim();
				String nombre = nameField.getText().trim();
				String apellidos = surnameField.getText().trim();
				String email = emailField.getText().trim();
				String contrasena = new String(passwordField.getPassword());
				String repPass = new String(repeatPasswordField.getPassword());
				String fechaNacimiento = birthDateField.getText().trim();
				int tipoUsuario = 0; // Usuario estándar

				if (!contrasena.equals(repPass)) {
					JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				if (!isValidDate(fechaNacimiento)) {
					JOptionPane.showMessageDialog(frame, "Formato de fecha inválido. Use aaaa/MM/dd.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				Usuario usuario = new Usuario();
				usuario.setNickname(nickname);
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setEmail(email);
				usuario.setContrasena(contrasena);
				usuario.setFechaNacimiento(fechaNacimiento);
				usuario.setTipoUsuario(tipoUsuario);
				RegistroControlador controlador = RegistroControlador.getInstance();
				String resultado = controlador.registrarUsuario(usuario);
				if (resultado.equals("Usuario registrado correctamente.")) {
					JOptionPane.showMessageDialog(frame, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
					frame.setContentPane(new LoginPanel(frame));
				} else {
					JOptionPane.showMessageDialog(frame, resultado, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Acción botón volver
		backButton.addActionListener(e -> {
			frame.setContentPane(new LoginPanel(frame));
			frame.validate(); // O lo que toque para volver al login
		});
	}

	// Validar formato de fecha
	private boolean isValidDate(String dateStr) {
		if (dateStr == null)
			return false;
		dateStr = dateStr.trim();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			java.time.LocalDate.parse(dateStr, formato);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}