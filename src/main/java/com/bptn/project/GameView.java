package com.bptn.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameView extends Application {
	private GameController gameController;

	@Override
	public void start(Stage primaryStage) {
		gameController = new GameController();
		primaryStage.setTitle("Battleship Game");

		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
