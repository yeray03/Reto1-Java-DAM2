package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import pojos.Usuario;
import pojos.Workout;

public class EjercicioPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel lblCronometro;
    private JLabel lblEjercicio;
    private JLabel lblWorkout;
    private JLabel lblTiempoEjercicio;
    private JTextArea txtDescanso;
    private JButton btnFoto1, btnFoto2, btnFoto3;
    private JButton btnGreen, btnSalir;
    
    public EjercicioPanel(JFrame frame, Usuario usuario, Workout workout) {
    	setLayout(null);
        setBackground(Color.decode("#232637"));

        // Cronómetro
        lblCronometro = new JLabel("Cronómetro aquí");
        lblCronometro.setForeground(Color.WHITE);
        lblCronometro.setBounds(30, 20, 200, 25);
        add(lblCronometro);

        // Ejercicio
        lblEjercicio = new JLabel("Ejercicio:");
        lblEjercicio.setForeground(Color.WHITE);
        lblEjercicio.setBounds(250, 20, 250, 25);
        add(lblEjercicio);

        // Workout
        lblWorkout = new JLabel("Workout: " + workout.getNombre());
        lblWorkout.setForeground(Color.WHITE);
        lblWorkout.setBounds(540, 20, 120, 25);
        add(lblWorkout);

        // Tiempo Ejercicio
        lblTiempoEjercicio = new JLabel("Tiempo Ejercicio 00:00");
        lblTiempoEjercicio.setForeground(Color.WHITE);
        lblTiempoEjercicio.setBounds(30, 70, 180, 25);
        add(lblTiempoEjercicio);

        // Descanso
        txtDescanso = new JTextArea("Descanso timer");
        txtDescanso.setEditable(false);
        txtDescanso.setOpaque(false);
        txtDescanso.setForeground(Color.WHITE);
        txtDescanso.setBorder(BorderFactory.createTitledBorder(
        	    BorderFactory.createLineBorder(Color.GRAY), 
        	    "Descanso",
        	    TitledBorder.DEFAULT_JUSTIFICATION,
        	    TitledBorder.DEFAULT_POSITION,
        	    null,
        	    Color.ORANGE 
        	));
        txtDescanso.setBounds(30, 110, 180, 50);
        add(txtDescanso);

        // Botones FOTO
        btnFoto1 = new JButton("Serie 1");
        btnFoto1.setBounds(250, 80, 320, 36);
        stylePhotoButton(btnFoto1);
        add(btnFoto1);

        btnFoto2 = new JButton("Serie 2");
        btnFoto2.setBounds(250, 130, 320, 36);
        stylePhotoButton(btnFoto2);
        add(btnFoto2);

        btnFoto3 = new JButton(" Serie 3");
        btnFoto3.setBounds(250, 180, 320, 36);
        stylePhotoButton(btnFoto3);
        add(btnFoto3);

        // Botón verde central
        btnGreen = new JButton();
        btnGreen.setBounds(350, 250, 36, 36);
        btnGreen.setBackground(new Color(99, 179, 92));
        btnGreen.setOpaque(true);
        btnGreen.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
        btnGreen.setFocusPainted(false);
        add(btnGreen);

        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setBounds(540, 320, 90, 28);
        btnSalir.setBackground(new Color(220, 60, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSalir);

        // Acción Salir: volver a WorkoutsPanel
        btnSalir.addActionListener((ActionEvent e) -> {
            // Aquí deberías pasar el usuario si lo necesitas
            frame.setContentPane(new WorkoutsPanel(frame, usuario));
            frame.validate();
        });
    }

    private void stylePhotoButton(JButton b) {
        b.setHorizontalAlignment(JButton.LEFT);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(60, 64, 68));
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(110, 115, 120)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
