package com.bptn.project;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
	protected Board board;
	protected List<Ship> ships;

	public Player() {
		board = new Board();
		ships = new ArrayList<>();
		initializeShips();
	}

	protected abstract void initializeShips();

	public Board getBoard() {
		return board;
	}

	public List<Ship> getShips() {
		return ships;
	}
}
