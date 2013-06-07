import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

public class MineSweeperTest {
	MineViewMock mineView = new MineViewMock();
	MineField mineField = new MineField(mineView);

	@Test
	public void initMineArrayField() {
		ArrayList<Boolean> mines = new ArrayList<Boolean>();
		for (int i = 0; i < 40 * 20; i++) {
			mines.add(false);
		}

		assertNotNull(mineField);
		assertEquals(mines, mineField.getMines());
	}

	@Test
	public void generateMines() throws Exception {
		mineField.seedMines(0.3); // seed 30% mines

		int count = 0;
		ArrayList<Boolean> mines = mineField.getMines();
		for (Boolean mine : mines) {
			if (mine)
				count++;
		}
		double percentage = (double) count / mines.size();
		assertEquals(0.3, percentage, 0.02);
	}

	/*
	 * 
	 * 01234 0 ---* 1 --* 2 --* 3 **
	 * 
	 * click 0,0 01234 0 01-* 1 02* 2 24* 3 **
	 */
	@Test
	public void openZeroCellWillOpenAdjancentCellsRecursively()
			throws Exception {
		mineField.setMine(3, 0);
		mineField.setMine(2, 1);
		mineField.setMine(2, 2);
		mineField.setMine(0, 3);
		mineField.setMine(1, 3);

		mineField.clickL(0, 0);

		assertEquals(6, mineView.countOpenCells());

		mineView.assertCell(0, 0, 0);
		mineView.assertCell(0, 1, 0);
		mineView.assertCell(1, 0, 1);
		mineView.assertCell(1, 1, 2);
		mineView.assertCell(0, 2, 2);
		mineView.assertCell(1, 2, 4);
	}

	@Test
	public void openMineCellWillEndGame() throws Exception {
		mineField.setMine(0, 0);
		assertEquals(MineField.LOSE, mineField.clickL(0, 0));
	}

	@Test
	public void openEmptyCellResultsInPlayingStatus() throws Exception {
		mineField.setMine(3, 3);
		assertEquals(MineField.PLAYING, mineField.clickL(0, 0));
	}

	@Test
	public void flagCell() throws Exception {
		mineField.clickR(0, 0);
		assertEquals(IMineView.FLAG, mineView.get(0, 0));
	}

	@Test
	public void questionCell() throws Exception {
		mineField.clickR(0, 0);
		mineField.clickR(0, 0);
		assertEquals(IMineView.QUESTION, mineView.get(0, 0));
	}

	@Test
	public void clearMarkCell() throws Exception {
		mineField.clickR(0, 0);
		mineField.clickR(0, 0);
		mineField.clickR(0, 0);
		assertEquals(IMineView.CLOSED, mineView.get(0, 0));
	}

	@Test
	public void winTheGameByFlagging() throws Exception {
		mineField.setMine(0, 0);
		mineField.clickL(2, 0); // open all but mine
		assertEquals(MineField.WIN, (int) mineField.clickR(0, 0));
	}

}
