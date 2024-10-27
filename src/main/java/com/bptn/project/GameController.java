package com.bptn.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameController {

	private GameView gameView;
	private HumanPlayer humanPlayer;
	private AIPlayer aiPlayer;
	private boolean gameOver;
	private Ship currentShip;
	private long startTime;
	private long endTime;

	public GameController(GameView gameView) {
		this.gameView = gameView;
		humanPlayer = new HumanPlayer();
		aiPlayer = new AIPlayer();
		gameOver = false;
	}

	public void startGame() {
		aiPlayer.placeShips();
		startTime = System.currentTimeMillis();
	}

	public void handlePlayerAttack(int row, int col) {
		if (gameOver) {
			return;
		}
		boolean validAttack = aiPlayer.getBoard().receiveAttack(row, col);
		if (validAttack) {
			humanPlayer.incrementGuesses();
			gameView.updateOpponentBoard(aiPlayer.getBoard().getGrid());

			if (aiPlayer.getBoard().getGrid()[row][col].hasShip()) {
				humanPlayer.incrementHits();
				Ship hitShip = aiPlayer.getBoard().getGrid()[row][col].getShip();
				if (hitShip.isSunk()) {
					humanPlayer.incrementSunkShips();
					gameView.updateGameMessage("You sunk the AI's " + hitShip.getName() + "!");
				} else {
					gameView.updateGameMessage("You hit the AI's ship!");
				}
			} else {
				humanPlayer.incrementMisses();
				gameView.updateGameMessage("You missed.");
			}

			gameView.updatePlayerStats(humanPlayer);
			gameView.updateAIStats(aiPlayer);

			if (aiPlayer.allShipsSunk()) {
				gameOver = true;
				endTime = System.currentTimeMillis();
				saveGameStatistics(true);
				gameView.showAlert("You win!");
				return;
			}
			aiTurn();
		}
	}

	private void aiTurn() {
		if (gameOver) {
			return;
		}
		int[] coordinates = aiPlayer.generateAttackCoordinates();
		int row = coordinates[0];
		int col = coordinates[1];
		boolean validAttack = humanPlayer.getBoard().receiveAttack(row, col);
		if (validAttack) {
			aiPlayer.incrementGuesses();
			boolean hit = humanPlayer.getBoard().getGrid()[row][col].hasShip();
			aiPlayer.updateStrategy(row, col, hit);

			if (hit) {
				aiPlayer.incrementHits();
				Ship hitShip = humanPlayer.getBoard().getGrid()[row][col].getShip();
				if (hitShip.isSunk()) {
					aiPlayer.incrementSunkShips();
					gameView.updateGameMessage("AI sunk your " + hitShip.getName() + "!");
				} else {
					gameView.updateGameMessage("AI hit your ship!");
				}
			} else {
				aiPlayer.incrementMisses();
				gameView.updateGameMessage("AI missed.");
			}

			gameView.updatePlayerBoard(humanPlayer.getBoard().getGrid());
			gameView.updatePlayerStats(humanPlayer);
			gameView.updateAIStats(aiPlayer);

			if (humanPlayer.allShipsSunk()) {
				gameOver = true;
				endTime = System.currentTimeMillis();
				saveGameStatistics(false);
				gameView.showAlert("AI wins!");
			}
		}
	}

	public void setCurrentShip(Ship ship) {
		this.currentShip = ship;
	}

	public boolean placeHumanShip(int row, int col, boolean horizontal) {
		if (currentShip == null) {
			return false;
		}
		currentShip.setHorizontal(horizontal);
		boolean placed = humanPlayer.getBoard().placeShip(currentShip, row, col);
		if (placed) {
			currentShip.setPlaced(true);
			currentShip = null;
			return true;
		} else {
			return false;
		}
	}

	public boolean allShipsPlaced() {
		return humanPlayer.allShipsPlaced();
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public AIPlayer getAIPlayer() {
		return aiPlayer;
	}

	private void saveGameStatistics(boolean playerWon) {
		long duration = endTime - startTime;
		String winner = playerWon ? "Human" : "AI";
		String stats = "Winner: " + winner + "\n" + "Game Duration: " + duration / 1000 + " seconds\n"
				+ "Human Guesses: " + humanPlayer.getNumberOfGuesses() + "\n" + "Human Hits: "
				+ humanPlayer.getNumberOfHits() + "\n" + "Human Misses: " + humanPlayer.getNumberOfMisses() + "\n"
				+ "Human Ships Sunk: " + humanPlayer.getNumberOfSunkShips() + "\n" + "AI Guesses: "
				+ aiPlayer.getNumberOfGuesses() + "\n" + "AI Hits: " + aiPlayer.getNumberOfHits() + "\n" + "AI Misses: "
				+ aiPlayer.getNumberOfMisses() + "\n" + "AI Ships Sunk: " + aiPlayer.getNumberOfSunkShips() + "\n"
				+ "----\n";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameStatistics.txt", true))) {
			writer.write(stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
