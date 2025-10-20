package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.dao.UsuarioDAO;
import pojos.Usuario;

import javax.swing.ImageIcon;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;

	private JLabel forgotLabel;
	private JLabel logoLabel;
	private HashMap<String, String> usuarios;
	private JFrame mainFrame; // Referencia al JFrame principal

	public LoginPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame; // Guarda la referencia para poder hacer el cambio de panel

		setLayout(null);
		setBackground(Color.decode("#232637"));

		// Logito
		logoLabel = new JLabel(new ImageIcon(getClass().getResource("/Logo01.svg")));
		logoLabel.setBounds(140, 15, 100, 50);
		add(logoLabel);

		// Etiqueta de título
		titleLabel = new JLabel("<html><b>Log in to your SpinningCat<br>account</b></html>");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
		titleLabel.setBounds(35, 30, 300, 60);
		add(titleLabel);

		// Campos de texto
		emailField = new JTextField();
		emailField.setFont(new Font("Arial", Font.PLAIN, 16));
		emailField.setBounds(35, 120, 300, 35);
		emailField.setBackground(new Color(39, 42, 61));
		emailField.setForeground(Color.WHITE);
		emailField.setCaretColor(Color.WHITE);
		emailField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Email", 0, 0, null,
				new Color(180, 180, 180)));
		add(emailField);

		// Campo de contraseña
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordField.setBounds(35, 170, 300, 35);
		passwordField.setBackground(new Color(39, 42, 61));
		passwordField.setForeground(Color.WHITE);
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Password", 0, 0,
				null, new Color(180, 180, 180)));
		add(passwordField);

		// Etiqueta de "Olvidé mi contraseña"
		forgotLabel = new JLabel("Forgot password?");
		forgotLabel.setForeground(new Color(180, 180, 180));
		forgotLabel.setFont(new Font("Arial", Font.BOLD, 13));
		forgotLabel.setBounds(200, 210, 140, 20);
		forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(forgotLabel);

		// Botón de login
		loginButton = new JButton("Log in");
		loginButton.setBounds(35, 270, 300, 45);
		loginButton.setFont(new Font("Arial", Font.BOLD, 18));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(255, 98, 0));
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(loginButton);
		
		
		// Botón de registro	
		registerButton = new JButton("Register");
		registerButton.setBounds(35, 320, 300, 40); // Y aquí lo añades
		registerButton.setFont(new Font("Arial", Font.BOLD, 16));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(100, 100, 100));
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(registerButton);
		registerButton.addActionListener(e -> {
		    mainFrame.setContentPane(new RegistroPanel(mainFrame));
		    mainFrame.validate();	    
		});
		
		if (mainFrame instanceof JFrame) {
			((JFrame) mainFrame).getRootPane().setDefaultButton(loginButton);
		}

		// Imagen de fondo (está en /resources)
		ImageIcon fondo = new ImageIcon(getClass().getResource("/BackgroundLogin.png"));
		JLabel fondoLabel = new JLabel(fondo);
		fondoLabel.setBounds(0, 0, 400, 400); // Replace 400, 400 with the desired width and height
		add(fondoLabel);

		// Simulación de bbdd de usuarios
//		usuarios = new HashMap<>();
//		usuarios.put("usuario1@example.com", "clave123");
//		usuarios.put("entrenador@example.com", "pass456");

		// Evento del botón login
		loginButton.addActionListener(e -> {
			String email = emailField.getText().trim();
			String pass = new String(passwordField.getPassword());

			if (email.isEmpty() || pass.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// Aquí, consulta el usuario en la base de datos
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario;
				try {
					usuario = usuarioDAO.buscarUsuarioPorEmail(email);

					if (usuario == null) {
						JOptionPane.showMessageDialog(this, "El usuario no existe.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!usuario.getContrasena().equals(pass)) {
						JOptionPane.showMessageDialog(this, "Usuario y/o contraseña incorrectos.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Login correcto. ¡Bienvenido/a!", "Login",
								JOptionPane.INFORMATION_MESSAGE);
						// Cambiar de pantalla
						if (mainFrame instanceof SpinningCatFrame) {
							((SpinningCatFrame) mainFrame).mostrarPantallaWorkouts();
						}
					}
				} catch (IOException ex) {

					ex.printStackTrace();
				} catch (ExecutionException ee) {

					ee.printStackTrace();
				} catch (InterruptedException ie) {

					ie.printStackTrace();
				}
			}
		});

	}

}