package src.spinningCat;

import src.vista.LoginPanel;

public class Main {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(() -> {
			new LoginPanel(null);
		});
	}

}
