package com.bptn.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*
Handles the user interface of the Battleship game using JavaFX
*/
public class GameView extends Application {

	private GameController gameController;
	private Button[][] playerGridButtons;
	private Button[][] opponentGridButtons;
	private ToggleButton orientationToggle;
	private Ship selectedShip;
	private VBox shipSelectionBox;

	private Label playerStatsLabel;
	private Label aiStatsLabel;
	private Label gameMessageLabel;

	@Override
	public void start(Stage primaryStage) {
		gameController = new GameController(this);
		primaryStage.setTitle("Battleship Game");

		displayShipPlacementScreen(primaryStage);
	}

	private void displayShipPlacementScreen(Stage primaryStage) {
		Label instructionLabel = new Label("Place your ships on the grid.");

		GridPane playerGrid = createPlayerGrid(true);

		orientationToggle = new ToggleButton("Horizontal");
		orientationToggle.setSelected(true);

		shipSelectionBox = createShipSelectionBox();

		HBox topBox = new HBox(10, shipSelectionBox, orientationToggle);
		topBox.setAlignment(Pos.CENTER);

		VBox root = new VBox(10, instructionLabel, topBox, playerGrid);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));

		Scene scene = new Scene(root, 600, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private VBox createShipSelectionBox() {
		shipSelectionBox = new VBox(10);
		shipSelectionBox.setAlignment(Pos.CENTER_LEFT);

		Label selectShipLabel = new Label("Select a Ship:");
		shipSelectionBox.getChildren().add(selectShipLabel);

		for (Ship ship : gameController.getHumanPlayer().getShips()) {
			if (!ship.isPlaced()) {
				Button shipButton = new Button(ship.getName() + " (" + ship.getLength() + ")");
				shipButton.setOnAction(event -> {
					selectedShip = ship;
					gameController.setCurrentShip(ship);
					// Highlight the selected ship button
					for (javafx.scene.Node node : shipSelectionBox.getChildren()) {
						if (node instanceof Button) {
							node.setStyle(null);
						}
					}
					shipButton.setStyle("-fx-background-color: lightblue;");
				});
				shipSelectionBox.getChildren().add(shipButton);
			}
		}

		return shipSelectionBox;
	}

	private void updateShipSelectionBox() {
		shipSelectionBox.getChildren().clear();

		Label selectShipLabel = new Label("Select a Ship:");
		shipSelectionBox.getChildren().add(selectShipLabel);

		boolean shipsRemaining = false;
		for (Ship ship : gameController.getHumanPlayer().getShips()) {
			if (!ship.isPlaced()) {
				shipsRemaining = true;
				Button shipButton = new Button(ship.getName() + " (" + ship.getLength() + ")");
				shipButton.setOnAction(event -> {
					selectedShip = ship;
					gameController.setCurrentShip(ship);
					for (javafx.scene.Node node : shipSelectionBox.getChildren()) {
						if (node instanceof Button) {
							node.setStyle(null);
						}
					}
					shipButton.setStyle("-fx-background-color: lightblue;");
				});
				shipSelectionBox.getChildren().add(shipButton);
			}
		}

		if (!shipsRemaining) {
			shipSelectionBox.getChildren().add(new Label("All ships placed."));
		}
	}

	private GridPane createPlayerGrid(boolean forPlacement) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		playerGridButtons = new Button[Board.GRID_SIZE][Board.GRID_SIZE];

		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Button cellButton = new Button();
				cellButton.setPrefSize(40, 40);

				final int currentRow = row;
				final int currentCol = col;

				cellButton.setOnAction(event -> {
					if (forPlacement) {
						if (selectedShip != null) {
							boolean isHorizontal = orientationToggle.isSelected();
							boolean placed = gameController.placeHumanShip(currentRow, currentCol, isHorizontal);
							if (placed) {
								updatePlayerBoard(gameController.getHumanPlayer().getBoard().getGrid());
								selectedShip = null;
								gameController.setCurrentShip(null);
								updateShipSelectionBox();
								if (gameController.allShipsPlaced()) {
									Stage stage = (Stage) cellButton.getScene().getWindow();
									setupGameScreen(stage);
								}
							} else {
								showAlert("Cannot place ship here.");
							}
						} else {
							showAlert("Select a ship to place.");
						}
					}
				});

				playerGridButtons[row][col] = cellButton;
				grid.add(cellButton, col, row);
			}
		}

		return grid;
	}

	private void setupGameScreen(Stage primaryStage) {
		Label opponentLabel = new Label("Opponent's Board");
		GridPane opponentGrid = createOpponentGrid();

		Label playerLabel = new Label("Your Board");
		GridPane playerGrid = createPlayerGrid(false);

		// Initialize stats labels
		playerStatsLabel = new Label("Your Stats: ");
		aiStatsLabel = new Label("AI Stats: ");
		gameMessageLabel = new Label();

		// Update stats display
		updatePlayerStats(gameController.getHumanPlayer());
		updateAIStats(gameController.getAIPlayer());

		VBox statsBox = new VBox(10, playerStatsLabel, aiStatsLabel, gameMessageLabel);
		statsBox.setAlignment(Pos.CENTER);

		VBox root = new VBox(10, opponentLabel, opponentGrid, playerLabel, playerGrid, statsBox);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));

		Scene scene = new Scene(root, 600, 850);
		primaryStage.setScene(scene);
		primaryStage.show();

		gameController.startGame();
	}

	private GridPane createOpponentGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		opponentGridButtons = new Button[Board.GRID_SIZE][Board.GRID_SIZE];

		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Button cellButton = new Button();
				cellButton.setPrefSize(40, 40);

				final int currentRow = row;
				final int currentCol = col;

				cellButton.setOnAction(event -> {
					gameController.handlePlayerAttack(currentRow, currentCol);
					cellButton.setDisable(true);
				});

				opponentGridButtons[row][col] = cellButton;
				grid.add(cellButton, col, row);
			}
		}

		return grid;
	}

	public void updatePlayerBoard(Cell[][] grid) {
		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Cell cell = grid[row][col];
				Button button = playerGridButtons[row][col];
				if (cell.isHit()) {
					if (cell.hasShip()) {
						button.setStyle("-fx-background-color: red;");
					} else {
						button.setStyle("-fx-background-color: blue;");
					}
				} else if (cell.hasShip()) {
					button.setStyle("-fx-background-color: gray;");
				} else {
					button.setStyle(null);
				}
			}
		}
	}

	public void updateOpponentBoard(Cell[][] grid) {
		for (int row = 0; row < Board.GRID_SIZE; row++) {
			for (int col = 0; col < Board.GRID_SIZE; col++) {
				Cell cell = grid[row][col];
				Button button = opponentGridButtons[row][col];
				if (cell.isHit()) {
					if (cell.hasShip()) {
						button.setStyle("-fx-background-color: red;");
					} else {
						button.setStyle("-fx-background-color: blue;");
					}
					button.setDisable(true);
				}
			}
		}
	}

	public void updatePlayerStats(HumanPlayer player) {
		playerStatsLabel.setText("Your Stats: Guesses - " + player.getNumberOfGuesses() + ", Hits - "
				+ player.getNumberOfHits() + ", Misses - " + player.getNumberOfMisses() + ", Ships Remaining - "
				+ player.getRemainingShips() + ", Ships Sunk - " + player.getNumberOfSunkShips());
	}

	public void updateAIStats(AIPlayer aiPlayer) {
		aiStatsLabel.setText("AI Stats: Guesses - " + aiPlayer.getNumberOfGuesses() + ", Hits - "
				+ aiPlayer.getNumberOfHits() + ", Misses - " + aiPlayer.getNumberOfMisses() + ", Ships Remaining - "
				+ aiPlayer.getRemainingShips() + ", Ships Sunk - " + aiPlayer.getNumberOfSunkShips());
	}

	public void updateGameMessage(String message) {
		gameMessageLabel.setText(message);
	}

	public void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}
