package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JComboBox<String> tipoUsuarioCombo;
	private JButton registerButton, backButton;
	private boolean placeholder = true;

	public RegistroPanel(JFrame frame) {
		setLayout(new GridLayout(9, 2, 10, 10));
		setBackground(new Color(32, 32, 32));
		// Inicializar componentes
		nameField = new JTextField();
		surnameField = new JTextField();
		emailField = new JTextField();
		passwordField = new JPasswordField();
		repeatPasswordField = new JPasswordField();

		birthDateField = new JTextField("YYYY-MM-DD");
		birthDateField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (placeholder) {
					birthDateField.setText("");
					placeholder = false;
				}
			}

		});
		tipoUsuarioCombo = new JComboBox<>(new String[] { "Cliente", "Entrenador" });
		registerButton = new JButton("Registrar");
		backButton = new JButton("Volver");

		// Etiquetas
		add(new JLabel("Nombre:"));
		add(nameField);
		add(new JLabel("Apellidos:"));
		add(surnameField);
		add(new JLabel("Email:"));
		add(emailField);
		add(new JLabel("Contraseña:"));
		add(passwordField);
		add(new JLabel("Repetir Contraseña:"));
		add(repeatPasswordField);
		add(new JLabel("Fecha de nacimiento:"));
		add(birthDateField);
		add(new JLabel("Tipo de usuario:"));
		add(tipoUsuarioCombo);

		add(registerButton);
		add(backButton);

		for (Component c : getComponents()) {
			if (c instanceof JLabel) {
				c.setForeground(new Color(220, 220, 220));
			}
		}

		// Acción botón registro
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = nameField.getText().trim();
				String apellidos = surnameField.getText().trim();
				String email = emailField.getText().trim();
				String contrasena = new String(passwordField.getPassword());
				String repPass = new String(repeatPasswordField.getPassword());
				String fechaNacimiento = birthDateField.getText().trim();
				String tipoUsuarioStr = (String) tipoUsuarioCombo.getSelectedItem();
				int tipoUsuario = tipoUsuarioStr.equals("Entrenador") ? 1 : 0; // 1=Entrenador, 0=Cliente

				if (!contrasena.equals(repPass)) {
					JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				if (!isValidDate(fechaNacimiento)) {
					JOptionPane.showMessageDialog(frame, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				Usuario usuario = new Usuario();
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

	// Validar formato de fecha, importamos librería Date de java.util
	private boolean isValidDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			sdf.parse(dateStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}