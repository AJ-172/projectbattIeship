package com.bptn.project;

public class Board {
	private static final int GRID_SIZE = 10;
	private Cell[][] grid;

	public Board() {
		grid = new Cell[GRID_SIZE][GRID_SIZE];
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				grid[i][j] = new Cell();
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
			grid[currentRow][currentCol].setShip(true);
		}

		return true;
	}

	public boolean receiveAttack(int row, int col) {
		Cell cell = grid[row][col];
		if (cell.isHit()) {
			return false;
		}
		cell.setHit(true);
		return cell.hasShip();
	}

	public Cell[][] getGrid() {
		return grid;
	}
}
