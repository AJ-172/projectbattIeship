package com.bptn.project;

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
	}

	@Override
	public int[] generateAttackCoordinates() {
		return null;
	}
}
