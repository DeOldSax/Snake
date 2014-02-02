package snake;

public class Apple {
	private static Position position;
	private static boolean wasFound;
	private static String color = "-fx-background-color: linear-gradient(lime, limegreen);-fx-background-radius: 0";

	private Apple() {

	}

	public static Position getPosition() {
		return position;
	}

	public static void setPosition(Position p) {
		position = p;
	}

	public static boolean wasFound() {
		return wasFound;
	}

	public static void setFound(boolean found) {
		wasFound = found;
	}

	public static String getColor() {
		return color;
	}

	public static void setColor(String c) {
		color = c + ";-fx-background-radius: 0";
	}
}
