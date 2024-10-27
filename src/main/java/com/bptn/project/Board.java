package com.bptn.project;

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

		if (isHorizontal) {
			if (col + length > GRID_SIZE) {
				return false;
			}
		} else {
			if (row + length > GRID_SIZE) {
				return false;
			}
		}

		for (int i = 0; i < length; i++) {
			int currentRow = row + (isHorizontal ? 0 : i);
			int currentCol = col + (isHorizontal ? i : 0);
			if (grid[currentRow][currentCol].hasShip()) {
				return false;
			}
		}

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
			return false;
		}
		cell.markHit();
		if (cell.hasShip()) {
			cell.getShip().hit();
		}
		return true;
	}

	public boolean allShipsSunk() {
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				Cell cell = grid[row][col];
				if (cell.hasShip() && !cell.getShip().isSunk()) {
					return false;
				}
			}
		}
		return true;
	}

	public Cell[][] getGrid() {
		return grid;
	}
}
