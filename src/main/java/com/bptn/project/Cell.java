package com.bptn.project;

public class Cell {

	private boolean isHit;
	private Ship ship;

	public Cell() {
		this.isHit = false;
		this.ship = null;
	}

	public boolean hasShip() {
		return ship != null;
	}

	public void placeShip(Ship ship) {
		this.ship = ship;
	}

	public boolean isHit() {
		return isHit;
	}

	public void markHit() {
		this.isHit = true;
	}

	public Ship getShip() {
		return ship;
	}
}
