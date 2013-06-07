public interface IMineView {
	int CLOSED = -1;
	int FLAG = -2;
	int QUESTION = -3;

	void initialize(int width, int height);

	void set(int x, int y, int state);

	int get(int x, int y);

}
