package spinningCat;


import javax.swing.*;
import vista.LoginPanel;

public class SpinningCat extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel;

    public SpinningCat() {
        setTitle("SpinningCat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        loginPanel = new LoginPanel(this); // Le pasamos la referencia
        setContentPane(loginPanel);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpinningCat();
        });
    }
    
    
}