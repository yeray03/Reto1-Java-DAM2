package src.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WorkoutsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkoutsPanel() {
		setBackground(Color.decode("#232637"));
		setLayout(new BorderLayout());
		JLabel label = new JLabel("Pantalla de Workouts", JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.WHITE);
		add(label, BorderLayout.CENTER);
	}
}