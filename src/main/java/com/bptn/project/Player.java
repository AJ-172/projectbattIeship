package com.bptn.project;

import java.util.ArrayList;
import java.util.List;

/*
Abstract class representing a player in the Battleship game
Can be extended by HumanPlayer and AIPlayer
*/
public abstract class Player {

	protected Board board;
	protected List<Ship> ships;
	protected int numberOfGuesses;
	protected int numberOfHits;
	protected int numberOfMisses;
	protected int numberOfSunkShips;

	public Player() {
		board = new Board();
		ships = new ArrayList<>();
		numberOfGuesses = 0;
		numberOfHits = 0;
		numberOfMisses = 0;
		numberOfSunkShips = 0;
	}

	protected abstract void initializeShips();

	public Board getBoard() {
		return board;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public boolean allShipsPlaced() {
		for (Ship ship : ships) {
			if (!ship.isPlaced()) {
				return false; // At least one ship not placed
			}
		}
		return true;
	}

	public boolean allShipsSunk() {
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				return false; // At least one ship not sunk
			}
		}
		return true;
	}

	public abstract void placeShips();

	public abstract int[] generateAttackCoordinates();

	public void incrementGuesses() {
		numberOfGuesses++;
	}

	public int getNumberOfGuesses() {
		return numberOfGuesses;
	}

	public void incrementHits() {
		numberOfHits++;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	public void incrementMisses() {
		numberOfMisses++;
	}

	public int getNumberOfMisses() {
		return numberOfMisses;
	}

	public void incrementSunkShips() {
		numberOfSunkShips++;
	}

	public int getNumberOfSunkShips() {
		return numberOfSunkShips;
	}

	public int getRemainingShips() {
		int remainingShips = 0;
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				remainingShips++;
			}
		}
		return remainingShips;
	}
}
