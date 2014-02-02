package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import snake.SnakeGame;

public class Game extends Application {

	public static void main(String[] args) {
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

		snake.setAppleColor("-fx-background-color: linear-gradient(green, forestgreen)");
		snake.setSnakeColor("-fx-background-color: linear-gradient(yellow, black)");

		snake.startGame();
	}
}
