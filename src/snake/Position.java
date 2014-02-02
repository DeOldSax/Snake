package snake;


public class Position {

	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			return ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y;
		} else {
			return super.equals(obj);
		}
	}
}
