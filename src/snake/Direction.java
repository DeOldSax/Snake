package snake;
public class Direction {

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	public Direction(boolean left, boolean right, boolean up, boolean down) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public void setLeft() {
		change(true, false, false, false);
	}

	public void setRight() {
		change(false, true, false, false);
	}

	public void setUp() {
		change(false, false, true, false);
	}

	public void setDown() {
		change(false, false, false, true);
	}

	private void change(boolean left, boolean right, boolean up, boolean down) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}
}
