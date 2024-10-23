package com.bptn.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class GameView extends Application {

	private GameController gameController;
	private Button[][] opponentGridButtons;
	private Button[][] playerGridButtons;

	@Override
	public void start(Stage primaryStage) {
		gameController = new GameController();
		primaryStage.setTitle("Battleship Game");

		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);

		Node shipSelectionBox = createShipSelectionBox();
		ToggleButton orientationToggle = new ToggleButton("Horizontal");
		orientationToggle.setSelected(true);

		GridPane playerGrid = createPlayerGrid();
		GridPane opponentGrid = createOpponentGrid();

		root.getChildren().addAll(shipSelectionBox, orientationToggle, playerGrid, opponentGrid);

		Scene scene = new Scene(root, 600, 900);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Node createShipSelectionBox() {
		VBox shipSelectionBox = new VBox(10);
		shipSelectionBox.setAlignment(Pos.CENTER_LEFT);
		Label selectShipLabel = new Label("Select a Ship:");
		shipSelectionBox.getChildren().add(selectShipLabel);
		return shipSelectionBox;
	}

	private GridPane createPlayerGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		playerGridButtons = new Button[Board.GRID_SIZE][Board.GRID_SIZE];

		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Button button = new Button();
				button.setPrefSize(40, 40);
				playerGridButtons[row][col] = button;
				grid.add(button, col, row);
			}
		}
		return grid;
	}

	private GridPane createOpponentGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		opponentGridButtons = new Button[Board.GRID_SIZE][Board.GRID_SIZE];

		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Button button = new Button();
				button.setPrefSize(40, 40);
				final int currentRow = row;
				final int currentCol = col;

				button.setOnAction(event -> {
					boolean hit = gameController.handlePlayerAttack(currentRow, currentCol);
					button.setStyle(hit ? "-fx-background-color: red;" : "-fx-background-color: blue;");
					button.setDisable(true);

					gameController.handleAIAttack();
					updatePlayerBoard();

					if (gameController.getHumanPlayer().getBoard().allShipsSunk()) {
						showAlert("Game Over! AI wins!");
					} else if (gameController.getAIPlayer().getBoard().allShipsSunk()) {
						showAlert("Game Over! You win!");
					}
				});

				opponentGridButtons[row][col] = button;
				grid.add(button, col, row);
			}
		}
		return grid;
	}

	private void updatePlayerBoard() {
		Cell[][] grid = gameController.getHumanPlayer().getBoard().getGrid();
		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Cell cell = grid[row][col];
				Button button = playerGridButtons[row][col];
				if (cell.isHit()) {
					button.setStyle(cell.hasShip() ? "-fx-background-color: red;" : "-fx-background-color: blue;");
				} else if (cell.hasShip()) {
					button.setStyle("-fx-background-color: gray;");
				} else {
					button.setStyle(null);
				}
			}
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}
