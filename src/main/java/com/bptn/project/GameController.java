package com.bptn.project;

public class GameController {
	private HumanPlayer humanPlayer;
	private AIPlayer aiPlayer;
	private boolean gameOver;

	public GameController() {
		humanPlayer = new HumanPlayer();
		aiPlayer = new AIPlayer();
		gameOver = false;
	}

	public boolean handlePlayerAttack(int row, int col) {
		if (gameOver) {
			return false;
		}
		boolean hit = aiPlayer.getBoard().receiveAttack(row, col);
		if (aiPlayer.getBoard().allShipsSunk()) {
			gameOver = true;
		}
		return hit;
	}

	public boolean handleAIAttack() {
		if (gameOver) {
			return false;
		}
		int[] coordinates = aiPlayer.generateAttackCoordinates();
		boolean hit = humanPlayer.getBoard().receiveAttack(coordinates[0], coordinates[1]);
		if (humanPlayer.getBoard().allShipsSunk()) {
			gameOver = true;
		}
		return hit;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public AIPlayer getAIPlayer() {
		return aiPlayer;
	}
}
