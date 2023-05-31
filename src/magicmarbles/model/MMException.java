package magicmarbles.model;

import javax.swing.*;

/**
 * The exception class for exceptions with the magic marble game
 */
public class MMException extends Exception {

	private static final long serialVersionUID = 8614515858833371347L;


	public MMException(String message) {
		switch (message) {
			case "f":
				String errorMessage = "Nur eine Murmel!";
				JOptionPane.showMessageDialog(null, errorMessage, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				break;
			case "l":
				errorMessage = "Es wurde auf ein leeres Feld geklickt!";
				JOptionPane.showMessageDialog(null, errorMessage, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				break;
			default:
				errorMessage = "Fehler!";
				JOptionPane.showMessageDialog(null, errorMessage, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				break;
		}

	}
}
