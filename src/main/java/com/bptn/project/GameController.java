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

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public AIPlayer getAIPlayer() {
		return aiPlayer;
	}
}
