package com.bptn.project;

/*
Represents the human player in the Battleship game.
Extends the Player class and handles ship initialization
*/
public class HumanPlayer extends Player {

	public HumanPlayer() {
		super();
		initializeShips();
	}

	@Override
	protected void initializeShips() {
		ships.add(new Ship("Carrier", 5));
		ships.add(new Ship("Battleship", 4));
		ships.add(new Ship("Cruiser", 3));
		ships.add(new Ship("Submarine", 3));
	}

	@Override
	public void placeShips() {
		// Ship placement is handled via GUI in GameView
	}

	@Override
	public int[] generateAttackCoordinates() {
		return null; // Not used for human player
	}
}
