package magicmarbles.model;

import javax.swing.*;

/**
 * The exception class for exceptions with the magic marble game
 */
public class MMException extends Exception {

	private static final long serialVersionUID = 8614515858833371347L;


	public MMException() {
		String errorMessage = "Es wurde ein leeres Feld ausgewählt!";
		JOptionPane.showMessageDialog(null, errorMessage, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
	}
}
