package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

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
        
    private Usuario usuario;
    private Workout workout;
    private Ejercicio ejercicioActual;
    
    
    
    public EjercicioPanel(JFrame frame, Usuario usuario, Workout workout, Ejercicio primerEjercicio) {
        this.usuario = usuario;
        this.workout = workout;
        this.ejercicioActual = primerEjercicio;
        
        setLayout(null);
        setBackground(Color.decode("#232637"));

        lblCronometroTotal = new JLabel("00:00");
        lblCronometroTotal.setForeground(Color.WHITE);
        lblCronometroTotal.setFont(new Font("Arial", Font.BOLD, 24));
        lblCronometroTotal.setBounds(30, 20, 150, 35);
        add(lblCronometroTotal);

        lblEjercicio = new JLabel("Ejercicio: " + ejercicioActual.getNombre());
        lblEjercicio.setForeground(Color.WHITE);
        lblEjercicio.setFont(new Font("Arial", Font.BOLD, 16));
        lblEjercicio.setBounds(200, 20, 300, 25);
        add(lblEjercicio);

        lblWorkout = new JLabel("Workout: " + workout.getNombre());
        lblWorkout.setForeground(Color.WHITE);
        lblWorkout.setBounds(540, 20, 120, 25);
        add(lblWorkout);

        lblDescripcion = new JLabel("<html>" + ejercicioActual.getDescripcion() + "</html>");
        lblDescripcion.setForeground(new Color(180, 180, 180));
        lblDescripcion.setBounds(30, 60, 600, 40);
        add(lblDescripcion);

        lblTiempoSerie = new JLabel("Serie: --:--");
        lblTiempoSerie.setForeground(Color.WHITE);
        lblTiempoSerie.setFont(new Font("Arial", Font.BOLD, 18));
        lblTiempoSerie.setBounds(30, 110, 180, 25);
        add(lblTiempoSerie);

        txtDescanso = new JTextArea("--:--");
        txtDescanso.setEditable(false);
        txtDescanso.setOpaque(false);
        txtDescanso.setForeground(Color.ORANGE);
        txtDescanso.setFont(new Font("Arial", Font.BOLD, 24));
        txtDescanso.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Descanso",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            null,
            Color.ORANGE 
        ));
        txtDescanso.setBounds(30, 150, 180, 70);
        add(txtDescanso);

        btnSerie1 = new JButton();
        btnSerie1.setBounds(250, 110, 370, 36);
        styleSerieButton(btnSerie1, false);
        add(btnSerie1);

        btnSerie2 = new JButton();
        btnSerie2.setBounds(250, 160, 370, 36);
        styleSerieButton(btnSerie2, false);
        add(btnSerie2);

        btnSerie3 = new JButton();
        btnSerie3.setBounds(250, 210, 370, 36);
        styleSerieButton(btnSerie3, false);
        add(btnSerie3);

        btnControl = new JButton("INICIAR");
        btnControl.setBounds(270, 270, 150, 50);
        btnControl.setBackground(new Color(99, 179, 92));
        btnControl.setForeground(Color.WHITE);
        btnControl.setFont(new Font("Arial", Font.BOLD, 18));
        btnControl.setOpaque(true);
        btnControl.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
        btnControl.setFocusPainted(false);
        btnControl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnControl);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(440, 270, 150, 50);
        btnSalir.setBackground(new Color(220, 60, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createLineBorder(new Color(90, 95, 100)));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSalir);

        cargarSeriesDelEjercicio();

        btnControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    private void cargarSeriesDelEjercicio() {
        ArrayList<Serie> series = ejercicioActual.getSeries();
        
        if (series == null || series.isEmpty()) {       
        }
        
        if (series.size() > 0) {
            Serie s1 = series.get(0);
            btnSerie1.setText(s1.getNombre() + " - " + s1.getRepeticiones() + " reps");
            btnSerie1.setVisible(true);
        } else {
            btnSerie1.setVisible(false);
        }
        
        if (series.size() > 1) {
            Serie s2 = series.get(1);
            btnSerie2.setText(s2.getNombre() + " - " + s2.getRepeticiones() + " reps");
            btnSerie2.setVisible(true);
        } else {
            btnSerie2.setVisible(false);
        }
        
        if (series.size() > 2) {
            Serie s3 = series.get(2);
            btnSerie3.setText(s3.getNombre() + " - " + s3.getRepeticiones() + " reps");
            btnSerie3.setVisible(true);
        } else {
            btnSerie3.setVisible(false);
        }
    }
    //Esto lo estaba usando de prueba, dejenlo comentado
//    private ArrayList<Serie> generarSeriesDefault() {
//        ArrayList<Serie> series = new ArrayList<>();
//        series.add(new Serie("Serie 1", 10, 30, 15, ""));
//        series.add(new Serie("Serie 2", 10, 30, 15, ""));
//        series.add(new Serie("Serie 3", 10, 30, 0, ""));
//        ejercicioActual.setSeries(series);
//        return series;
//    }


    private void styleSerieButton(JButton boton, boolean activa) {
    	boton.setHorizontalAlignment(JButton.LEFT);
    	boton.setForeground(Color.WHITE);
        if (activa) {
        	boton.setBackground(new Color(70, 130, 180));
        } else {
        	boton.setBackground(new Color(60, 64, 68));
        }
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(110, 115, 120)));
        boton.setEnabled(false);
    }
}