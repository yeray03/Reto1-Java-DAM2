package spinningCat;

import vista.LoginPanel;

public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			new LoginPanel();
		});
	}

}
