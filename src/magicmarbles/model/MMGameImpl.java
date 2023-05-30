package magicmarbles.model;

import java.util.ArrayList;
import java.util.Random;


public class MMGameImpl implements MMGame {
	private int width;
	private int height;
	private MMFieldState[][] field;
	private int points;
	private int scoredpoints;


	public MMGameImpl(int width, int height) {
		this.width = width;
		this.height = height;
		field = new MMFieldState[width][height];
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

	public void setPoints(int points) {
		this.points = points;
	}

	private void fillMatrix() {
		Random r = new Random(3);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
				field[i][j] = MMFieldState.values()[r.nextInt(3)];
		}
	}

	@Override
	public MMState getGameState() {
		MMFieldState current;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
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
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
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
		return field[col][row];
	}

	private void setFieldState(int k, int i, MMFieldState fieldState) {
		field[i][k] = fieldState;
	}

	@Override
	public void select(int row, int col) throws MMException {
		deleteEelements(col, row);
		gravityOnMarbles();
		moveRight();
	}

	private void deleteEelements(int row,int col) {

		MMFieldState value = field[row][col];

		if (field[row][col] == value && value != MMFieldState.EMPTY) {
			field[row][col] = MMFieldState.EMPTY;
			scoredpoints += 1;
		} else {
			return;
		}
		if (row > 0) {
			if (field[row - 1][col] == value) {
				deleteEelements(row - 1, col);
			}
		}
		if (row < width - 1) {
			if (field[row + 1][col] == value) {
				deleteEelements(row + 1, col);
			}
		}
		if (col > 0) {
			if (field[row][col - 1] == value) {
				deleteEelements(row, col - 1);
			}
		}
		if (col < height - 1) {
			if (field[row][col + 1] == value) {
				deleteEelements(row, col + 1);
			}
		}
	}
	private void gravityOnMarbles() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (field[col][row] == MMFieldState.EMPTY) {
					// Verschiebe alle darüber liegenden Kugeln nach unten
					for (int i = row; i > 0; i--) {
						field[col][i] = field[col][i - 1];
					}
					field[col][0] = MMFieldState.EMPTY; // Setze die obere Kugel auf null
				}
			}
		}
	}

	private void moveRight() {
		for (int i = getWidth() - 1; i >= 0; i--) {
			if (getFieldState(getHeight() - 1, i) == MMFieldState.EMPTY) {
				for (int j = i - 1; j >= 0; j--) {
					if (getFieldState(getHeight() - 1, j) != MMFieldState.EMPTY) {
						for (int k = 0; k < getHeight(); k++) {
							setFieldState(k, i, getFieldState(k, j));
							setFieldState(k, j, MMFieldState.EMPTY);
						}
						break;
					}
				}
			}
		}
	}
}
