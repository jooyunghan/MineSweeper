import java.util.ArrayList;

import org.junit.Assert;

public class MineViewMock implements IMineView {

	private ArrayList<Integer> mineViews = new ArrayList<Integer>();
	private int width;

	@Override
	public void initialize(int width, int height) {
		this.width = width;

		for (int i = 0; i < width * height; i++) {
			mineViews.add(CLOSED);
		}
	}

	@Override
	public void set(int x, int y, int state) {
		mineViews.set(x + y * width, state);
	}

	public void assertCell(int x, int y, int state) {
		Assert.assertEquals(state, (int)mineViews.get(x + y * width));
	}

	public int countOpenCells() {
		int count = 0;
		for (int state : mineViews) {
			if (state != CLOSED) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int get(int x, int y) {
		return mineViews.get(x + y * width);
	}

}
