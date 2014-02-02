package snake;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Snake implements EventHandler<KeyEvent> {
	private final Button[][] field;
	private double speed = 100;
	private List<Position> snakePosition;
	private StartPosition startPosition;
	private Direction direction;
	private boolean isDead;
	private String skinColor;

	public Snake(Button[][] field) {
		this.field = field;
		this.direction = new Direction(false, false, false, true);

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j].setOnKeyPressed(this);
			}
		}

		setStartPosition(StartPosition.LEFT);
		setColor("-fx-background-color: linear-gradient(red, orangered)");
	}

	public void moveRight() {
		if (!(snakePosition.get(snakePosition.size() - 1).getX() < field.length - 1)) {
			return;
		}
		final int newX = snakePosition.get(snakePosition.size() - 1).getX() + 1;
		final int newY = snakePosition.get(snakePosition.size() - 1).getY();
		if (appleFound()) {
			eatApple();
		}
		ParallelTransition p = createTransition(field[newX][newY]);

		final boolean biteHimself = move(1, 0);
		if (!biteHimself) {
			isDead = true;
		}
		p.play();
		p.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!direction.isRight()) {
					return;
				}
				if (snakePosition.get(snakePosition.size() - 1).getX() < field.length - 1) {
					moveRight();
				} else {
					isDead = true;
				}
			}
		});
	}

	protected void moveDown() {
		if (!(snakePosition.get(snakePosition.size() - 1).getY() < field.length - 1)) {
			return;
		}
		final int newX = snakePosition.get(snakePosition.size() - 1).getX();
		final int newY = snakePosition.get(snakePosition.size() - 1).getY() + 1;
		if (appleFound()) {
			eatApple();
		}
		ParallelTransition p = createTransition(field[newX][newY]);

		final boolean biteHimself = move(0, 1);
		if (!biteHimself) {
			isDead = true;
		}
		p.play();
		p.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!direction.isDown()) {
					return;
				}
				if (snakePosition.get(snakePosition.size() - 1).getY() < field.length - 1) {
					moveDown();
				} else {
					isDead = true;
					return;
				}
			}
		});
	}

	protected void moveLeft() {
		if (!(snakePosition.get(snakePosition.size() - 1).getX() > 0)) {
			return;
		}
		final int newX = snakePosition.get(snakePosition.size() - 1).getX() - 1;
		final int newY = snakePosition.get(snakePosition.size() - 1).getY();
		if (appleFound()) {
			eatApple();
		}
		ParallelTransition p = createTransition(field[newX][newY]);

		final boolean biteHimself = move(-1, 0);
		if (!biteHimself) {
			isDead = true;
		}
		p.play();
		p.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!direction.isLeft()) {
					return;
				}
				if (snakePosition.get(snakePosition.size() - 1).getX() > 0) {
					moveLeft();
				} else {
					isDead = true;
				}
			}
		});
	}

	protected void moveUp() {
		if (!(snakePosition.get(snakePosition.size() - 1).getY() > 0)) {
			return;
		}
		final int newX = snakePosition.get(snakePosition.size() - 1).getX();
		final int newY = snakePosition.get(snakePosition.size() - 1).getY() - 1;
		if (appleFound()) {
			eatApple();
		}
		ParallelTransition p = createTransition(field[newX][newY]);

		final boolean biteHimself = move(0, -1);
		if (!biteHimself) {
			isDead = true;
		}
		p.play();
		p.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!direction.isUp()) {
					return;
				}
				if (snakePosition.get(snakePosition.size() - 1).getY() > 0) {
					moveUp();
				} else {
					isDead = true;
				}
			}
		});
	}

	private boolean move(int x, int y) {
		final Position e = new Position(snakePosition.get(snakePosition.size() - 1).getX() + x, snakePosition.get(snakePosition.size() - 1)
				.getY() + y);
		if (snakePosition.contains(e) && !e.equals(Apple.getPosition())) {
			return false;
		}
		snakePosition.add(e);
		snakePosition.remove(snakePosition.get(0));
		return true;
	}

	private ParallelTransition createTransition(Button newField) {
		FadeTransition fade = new FadeTransition(Duration.millis(speed), newField);
		fade.setFromValue(1);
		fade.setToValue(1);

		FadeTransition fade2 = new FadeTransition(Duration.millis(speed), field[snakePosition.get(0).getX()][snakePosition.get(0).getY()]);
		fade2.setFromValue(1);
		fade2.setToValue(0);

		ParallelTransition p = new ParallelTransition();
		p.getChildren().addAll(fade, fade2);
		return p;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void handle(KeyEvent event) {
		if (isDead) {
			return;
		}
		if (event.getCode() == KeyCode.UP && !direction.isDown()) {
			direction.setUp();
			moveUp();
		} else if (event.getCode() == KeyCode.DOWN && !direction.isUp()) {
			direction.setDown();
			moveDown();
		} else if (event.getCode() == KeyCode.LEFT && !direction.isRight()) {
			direction.setLeft();
			moveLeft();
		} else if (event.getCode() == KeyCode.RIGHT && !direction.isLeft()) {
			direction.setRight();
			moveRight();
		}
	}

	private boolean appleFound() {
		if (Apple.getPosition() == null) {
			return false;
		}
		final boolean appleFound = Apple.getPosition().equals(snakePosition.get(snakePosition.size() - 1));
		if (appleFound) {
			Apple.setFound(true);
		}
		return appleFound;
	}

	private void eatApple() {
		if (speed < 10) {
			speed = speed * 0.9;
		} else {
			speed = speed - 2;
		}

		int x = 0;
		int y = 0;

		final int xFirst = snakePosition.get(snakePosition.size() - 1).getX();
		final int yFirst = snakePosition.get(snakePosition.size() - 1).getY();
		final int xSecond = snakePosition.get(snakePosition.size() - 2).getX();
		final int ySecond = snakePosition.get(snakePosition.size() - 2).getY();

		if (xFirst - 1 == xSecond) {
			x = -1;
			y = 0;
		}
		if (xFirst + 1 == xSecond) {
			x = 1;
			y = 0;
		}
		if (yFirst - 1 == ySecond) {
			x = 0;
			y = -1;
		}
		if (yFirst + 1 == ySecond) {
			x = 0;
			y = 1;
		}

		snakePosition.add(snakePosition.size() - 2, new Position(Apple.getPosition().getX() + x, Apple.getPosition().getY() + y));
		final Button newSnakePart = field[Apple.getPosition().getX()][Apple.getPosition().getY()];
		newSnakePart.setStyle(skinColor);
		newSnakePart.setOpacity(1);
	}

	public int getNumberOfEatenApples() {
		return snakePosition.size() - 3;
	}

	public boolean isDead() {
		return isDead;
	}

	public void fade(double n, double k) {
		ParallelTransition p = new ParallelTransition();
		for (Position pos : snakePosition) {
			FadeTransition f = new FadeTransition(Duration.millis(1000), field[pos.getX()][pos.getY()]);
			f.setFromValue(n);
			f.setToValue(k);
			p.getChildren().add(f);
		}
		p.play();
	}

	public void setColor(String cssColor) {
		this.skinColor = cssColor + "; -fx-background-radius: 0";
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j].setStyle(skinColor);
			}
		}
		for (Position position : snakePosition) {
			field[position.getX()][position.getY()].setStyle(skinColor);
			field[position.getX()][position.getY()].setOpacity(1);
		}
	}

	public String getColor() {
		return skinColor;
	}

	public void setStartPosition(StartPosition startPosition) {
		this.startPosition = startPosition;
		List<Position> positions = new ArrayList<Position>();
		switch (startPosition) {
		case LEFT:
			positions.add(new Position(0, field.length / 2));
			positions.add(new Position(1, field.length / 2));
			positions.add(new Position(2, field.length / 2));
			break;
		case RIGHT:
			positions.add(new Position(field.length - 3, field.length / 2));
			positions.add(new Position(field.length - 2, field.length / 2));
			positions.add(new Position(field.length - 1, field.length / 2));
			break;
		case TOP:
			positions.add(new Position(field.length / 2, 0));
			positions.add(new Position(field.length / 2, 1));
			positions.add(new Position(field.length / 2, 2));
			break;
		case BOTTOM:
			positions.add(new Position(field.length / 2, field.length - 3));
			positions.add(new Position(field.length / 2, field.length - 2));
			positions.add(new Position(field.length / 2, field.length - 1));
			break;
		}
		this.snakePosition = positions;
	}

	public List<Position> getPosition() {
		return snakePosition;
	}

	public void setAlive() {
		this.isDead = false;
		this.speed = 100;
		this.direction = new Direction(false, false, false, true);

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j].setOpacity(0);
				field[i][j].setStyle(skinColor);
			}
		}

		setStartPosition(startPosition);
		setColor(skinColor);
		fade(0.5, 1);
	}
}
