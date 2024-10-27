package com.bptn.project;

/*
Represents the game board in the Battleship game
Manages ship placement, processing attacks, and checking if all ships are sunk
*/
public class Board {

	public static final int GRID_SIZE = 10;
	private Cell[][] grid;

	public Board() {
		grid = new Cell[GRID_SIZE][GRID_SIZE];
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				grid[row][col] = new Cell();
			}
		}
	}

	public boolean placeShip(Ship ship, int row, int col) {
		int length = ship.getLength();
		boolean isHorizontal = ship.isHorizontal();

		// Check if the ship fits within the grid bounds
		if (isHorizontal) {
			if (col + length > GRID_SIZE) {
				return false; // Ship would go out of bounds horizontally
			}
		} else {
			if (row + length > GRID_SIZE) {
				return false; // Ship would go out of bounds vertically
			}
		}

		// Check for overlap with existing ships
		for (int i = 0; i < length; i++) {
			int currentRow = row + (isHorizontal ? 0 : i);
			int currentCol = col + (isHorizontal ? i : 0);
			if (grid[currentRow][currentCol].hasShip()) {
				return false; // Cannot place ship here; another ship is in the way
			}
		}

		// Place the ship on the grid
		for (int i = 0; i < length; i++) {
			int currentRow = row + (isHorizontal ? 0 : i);
			int currentCol = col + (isHorizontal ? i : 0);
			grid[currentRow][currentCol].placeShip(ship);
		}

		ship.setPlaced(true);
		return true;
	}

	public boolean receiveAttack(int row, int col) {
		Cell cell = grid[row][col];
		if (cell.isHit()) {
			return false; // Cell has already been attacked
		}
		cell.markHit();
		if (cell.hasShip()) {
			cell.getShip().hit();
		}
		return true;
	}

	public boolean allShipsSunk() {
		// Iterate through all cells to check for any unsunk ships
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				Cell cell = grid[row][col];
				if (cell.hasShip() && !cell.getShip().isSunk()) {
					return false; // Found a ship that is not yet sunk
				}
			}
		}
		return true; // All ships have been sunk
	}

	public Cell[][] getGrid() {
		return grid;
	}
}
