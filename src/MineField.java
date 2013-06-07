import java.util.ArrayList;
import java.util.Collections;

public class MineField {

	public static final int LOSE = 0;
	public static final int PLAYING = 1;
	public static final int WIN = 2;
	private ArrayList<Boolean> mines = new ArrayList<Boolean>();
	private int width = 40;
	private int height = 20;
	private IMineView mineView;

	public MineField(IMineView mineView) {
		this.mineView = mineView;
		mineView.initialize(width, height);
		for (int i = 0; i < width * height; i++)
			mines.add(false);
	}

	public ArrayList<Boolean> getMines() {
		return mines;
	}

	public void seedMines(double p) {
		int mineCount = (int) (mines.size() * p);
		for (int i = 0; i < mineCount; i++) {
			mines.set(i, true);
		}
		Collections.shuffle(mines);
	}

	public void setMine(int x, int y) {
		mines.set(y * width + x, true);
	}

	public int clickL(int x, int y) {
		if (isMine(x, y))
			return LOSE;

		int count = countOfAdjacentMines(x, y);
		mineView.set(x, y, count);
		if (count == 0) {
			openAdjacentCells(x, y);
		}

		return PLAYING;
	}

	public int clickR(int x, int y) {
		if (mineView.get(x, y) == IMineView.FLAG)
			mineView.set(x, y, IMineView.QUESTION);
		else if (mineView.get(x, y) == IMineView.CLOSED)
			mineView.set(x, y, IMineView.FLAG);
		else if (mineView.get(x, y) == IMineView.QUESTION)
			mineView.set(x, y, IMineView.CLOSED);

		if (isWin()) {
			return WIN;
		}

		return PLAYING;
	}

	private boolean isWin() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int state = mineView.get(x, y);
				if (state == IMineView.FLAG && !isMine(x, y)) {
					return false;
				} else if (state == IMineView.CLOSED
						|| state == IMineView.QUESTION) {
					return false;
				}
			}
		}
		return true;
	}

	private void openAdjacentCells(int x0, int y0) {
		for (int x = x0 - 1; x <= x0 + 1; x++) {
			for (int y = y0 - 1; y <= y0 + 1; y++) {
				if (x < 0 || y < 0 || x >= width || y >= height)
					continue;
				if (mineView.get(x, y) == IMineView.CLOSED)
					clickL(x, y);
			}
		}
	}

	private int countOfAdjacentMines(int x0, int y0) {
		int count = 0;
		for (int x = x0 - 1; x <= x0 + 1; x++) {
			for (int y = y0 - 1; y <= y0 + 1; y++) {
				if (x < 0 || y < 0 || x >= width || y >= height)
					continue;
				if (isMine(x, y)) {
					count++;
				}
			}
		}
		return count;
	}

	private boolean isMine(int x, int y) {
		return mines.get(x + width * y);
	}

}
