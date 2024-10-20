package com.bptn.project;

public class Cell {
	private boolean hasShip;
	private boolean isHit;

	public Cell() {
		this.hasShip = false;
		this.isHit = false;
	}

	public boolean hasShip() {
		return hasShip;
	}

	public void setShip(boolean hasShip) {
		this.hasShip = hasShip;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}
}
