package magicmarbles.model;

import java.util.Random;

/**
 * Implementation of the magic marbles game
  */
public class MMGameImpl implements MMGame {
	private final int width;
	private final int height;
	private final MMFieldState[][] field;
	private int points;
	private int scoredpoints;

	public MMGameImpl(int width, int height) {
		this.width = width;
		this.height = height;
		field = new MMFieldState[height][width];
		fillMatrix();
		points = 0;
		scoredpoints = 0;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	private void fillMatrix() {
		Random r = new Random(3);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				field[i][j] = MMFieldState.values()[r.nextInt(3)];
		}
	}

	@Override
	public MMState getGameState() {

		// Diese Funktion überprüft, ob der Nachbar gleich ist. Wenn es keinen passenden Nachbar gibt, wird das Spiel beendet.

		MMFieldState current;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				current = field[i][j];
				if (j > 0 && field[i][j - 1] == current && field[i][j - 1] != MMFieldState.EMPTY) {
					return MMState.RUNNING;
				}
				if (j < field[i].length - 1 && field[i][j + 1] == current && field[i][j + 1] != MMFieldState.EMPTY) {
					return MMState.RUNNING;
				}
				if (i > 0 && field[i - 1][j] == current && field[i - 1][j] != MMFieldState.EMPTY) {
					return MMState.RUNNING;
				}
				if (i < field.length - 1 && field[i + 1][j] == current && field[i + 1][j] != MMFieldState.EMPTY) {
					return MMState.RUNNING;
				}
			}
		}

		// Zählt alle übergebliebenen Punkte und zieht sie von den Gesamtpunkten ab.

		int count = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				if (field[i][j] != MMFieldState.EMPTY) {
					count++;
				}
		}
		points -= (count * 10);
		scoredpoints = 0;
		return MMState.END;
	}

	@Override
	public int getGamePoints() {
		points += scoredpoints * scoredpoints;
		scoredpoints = 0;
		return points;
	}

	@Override
	public MMFieldState getFieldState(int row, int col) {
		return field[row][col];
	}

	@Override
	public void select(int row, int col) throws MMException {        // scoredpoints sind noch nicht richtig

		// Diese Funktion löscht alle Elemente, die Benachbart sind
		MMFieldState value = field[row][col];

		if (field[row][col] == value) {
			field[row][col] = MMFieldState.EMPTY;
			scoredpoints += 1;
		} else {
			return;
		}
		if (row > 0) {
			if (field[row - 1][col] == value) {
				select(row - 1, col);
			}
		}
		if (row < height - 1) {
			if (field[row + 1][col] == value) {
				select(row + 1, col);
			}
		}
		if (col > 0) {
			if (field[row][col - 1] == value) {
				select(row, col - 1);
			}
		}
		if (col < width - 1) {
			if (field[row][col + 1] == value) {
				select(row, col + 1);
			}
		}

		// Löscht alle Lücken Vertikal

		int count = height - 1;
		MMFieldState[][] tempArr = new MMFieldState[height][width];
		int tempCol = 0;
		int tempRow = 0;

		for (tempCol = 0; tempCol < width; tempCol++) {
			for (tempRow = height - 1; tempRow >= 0; tempRow--) {
				if (field[tempRow][tempCol] != MMFieldState.EMPTY) {
					tempArr[count][tempCol] = field[tempRow][tempCol];
					field[tempRow][tempCol] = MMFieldState.EMPTY;
					count--;
				}
			}
			count = height - 1;
		}

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				if (tempArr[i][j] != null) {
					field[i][j] = tempArr[i][j];
				}
		}

	}
}