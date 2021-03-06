package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import snake.SnakeGame;

public class Game extends Application {
	private static String appleColor;
	private static String snakeColor;

	public static void main(String[] args) {
		if (args.length == 0) {
			snakeColor = "-fx-background-color:linear-gradient(lime,limegreen)";
			appleColor = "-fx-background-color:linear-gradient(green,forestgreen)";
		} else {
			snakeColor = args[0];
			appleColor = args[1];
		}
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			};
		});
		SnakeGame snake = new SnakeGame(30);
		Scene scene = new Scene(snake);
		stage.setScene(scene);
		stage.show();

		snake.setAppleColor(appleColor);
		snake.setSnakeColor(snakeColor);

		snake.startGame();
	}
}
