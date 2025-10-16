package vista;

import javax.swing.*;

public class SpinningCatFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel;
    private WorkoutsPanel workoutsPanel;

    public SpinningCatFrame() {
        setTitle("SpinningCat - App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        loginPanel = new LoginPanel(this); // Le pasamos la referencia
        setContentPane(loginPanel);
        setVisible(true);
    }

    public void mostrarPantallaWorkouts() {
        if (workoutsPanel == null) {
            workoutsPanel = new WorkoutsPanel();
        }
        setContentPane(workoutsPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpinningCatFrame();
        });
    }
}