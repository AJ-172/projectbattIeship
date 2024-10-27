package com.bptn.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Represents the AI opponent in the Battleship game
Extends the Player class and implements ship placement and attack logic
*/
public class AIPlayer extends Player {

	private boolean[][] attackedPositions; // Tracks positions already attacked
	private Random randomGenerator;
	private List<int[]> hitPositions; // Positions where a ship was hit

	public AIPlayer() {
		super();
		attackedPositions = new boolean[Board.GRID_SIZE][Board.GRID_SIZE];
		randomGenerator = new Random();
		hitPositions = new ArrayList<>();
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
		for (Ship ship : ships) {
			boolean placedSuccessfully = false;
			while (!placedSuccessfully) {
				int row = randomGenerator.nextInt(Board.GRID_SIZE);
				int col = randomGenerator.nextInt(Board.GRID_SIZE);
				boolean isHorizontal = randomGenerator.nextBoolean();
				ship.setHorizontal(isHorizontal);

				placedSuccessfully = board.placeShip(ship, row, col);
				// Retry until the ship is placed successfully
			}
		}
	}

	@Override
	public int[] generateAttackCoordinates() {
		int[] coords;
		if (!hitPositions.isEmpty()) {
			coords = getAdjacentCoordinates();
			if (coords != null) {
				attackedPositions[coords[0]][coords[1]] = true;
				return coords;
			}
		}
		// Randomly select a coordinate that hasn't been attacked yet
		int row, col;
		do {
			row = randomGenerator.nextInt(Board.GRID_SIZE);
			col = randomGenerator.nextInt(Board.GRID_SIZE);
		} while (attackedPositions[row][col]);
		attackedPositions[row][col] = true;
		return new int[] { row, col };
	}

	private int[] getAdjacentCoordinates() {
		for (int[] hitPos : hitPositions) {
			int row = hitPos[0];
			int col = hitPos[1];
			int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right
			for (int[] dir : directions) {
				int newRow = row + dir[0];
				int newCol = col + dir[1];
				if (isValidCoordinate(newRow, newCol) && !attackedPositions[newRow][newCol]) {
					return new int[] { newRow, newCol };
				}
			}
		}
		return null; // No valid adjacent coordinates found
	}

	private boolean isValidCoordinate(int row, int col) {
		return row >= 0 && row < Board.GRID_SIZE && col >= 0 && col < Board.GRID_SIZE;
	}

	public void updateStrategy(int row, int col, boolean hit) {
		if (hit) {
			hitPositions.add(new int[] { row, col });
		}
	}
}
