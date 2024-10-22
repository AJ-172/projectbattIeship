package com.bptn.project;

import java.util.Random;

public class AIPlayer extends Player {
	private Random random;

	public AIPlayer() {
		super();
		random = new Random();
		placeShips();
	}

	@Override
	protected void initializeShips() {
		ships.add(new Ship("Carrier", 5));
		ships.add(new Ship("Battleship", 4));
		ships.add(new Ship("Cruiser", 3));
		ships.add(new Ship("Submarine", 3));
	}

	public void placeShips() {
		for (Ship ship : ships) {
			boolean placed = false;
			while (!placed) {
				int row = random.nextInt(Board.GRID_SIZE);
				int col = random.nextInt(Board.GRID_SIZE);
				ship.setHorizontal(random.nextBoolean());
				placed = board.placeShip(ship, row, col);
			}
		}
	}

	public int[] generateAttackCoordinates() {
		int row = random.nextInt(Board.GRID_SIZE);
		int col = random.nextInt(Board.GRID_SIZE);
		return new int[] { row, col };
	}
}
