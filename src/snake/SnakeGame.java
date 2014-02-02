package snake;

import java.util.Calendar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SnakeGame extends StackPane {
	private final Snake snake;
	private final Button[][] field;

	public SnakeGame() {
		this(20);
	}

	public SnakeGame(int length) {
		GridPane pane = new GridPane();
		pane.setHgap(1);
		pane.setVgap(2);
		field = new Button[length][length];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				field[i][j] = new Button();
				field[i][j].setOpacity(0);
				field[i][j].setMouseTransparent(true);
				pane.add(field[i][j], i, j);
			}
		}
		snake = new Snake(field);
		getChildren().add(pane);
	}

	public void startGame() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				start();
			}
		}).start();
	}

	private void start() {
		while (!snake.isDead()) {
			final int x = (int) (Math.random() * (field.length - 1) + 1);
			final int y = (int) (Math.random() * (field.length - 1) + 1);
			final Position newApplePosition = new Position(x, y);
			if (snake.getPosition().contains(newApplePosition)) {
				start();
				break;
			}
			Apple.setPosition(newApplePosition);
			Apple.setFound(false);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					field[x][y].setStyle(Apple.getColor());
					field[x][y].setOpacity(1);
				}
			});

			final long startTime = Calendar.getInstance().getTimeInMillis();

			while (Calendar.getInstance().getTimeInMillis() - startTime < 5000 && !Apple.wasFound()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (!Apple.wasFound()) {
				// Platform.runLater(new Runnable() {
				// @Override
				// public void run() {
				field[x][y].setStyle(snake.getColor());
				field[x][y].setOpacity(0);
				// }
				// });
			}
		}
		snake.fade(1, 0.5);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				askForNewGame();
			}
		});
	}

	private void askForNewGame() {
		final BorderPane gameOverPane = new BorderPane();
		final Button button = new Button("Again");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getChildren().remove(gameOverPane);
				snake.setAlive();
				startGame();
			}
		});
		gameOverPane.setCenter(button);

		getChildren().add(gameOverPane);
	}

	public void setSnakeStartPosition(StartPosition startPosition) {
		snake.setStartPosition(startPosition);
	}

	public void setSnakeColor(String cssColor) {
		snake.setColor(cssColor);
	}

	public void setAppleColor(String cssColor) {
		Apple.setColor(cssColor);
	}
}
