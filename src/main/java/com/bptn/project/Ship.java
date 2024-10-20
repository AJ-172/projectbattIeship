package com.bptn.project;

public class Ship {
	private String name;
	private int length;
	private int hits; // Added to track hits
	private boolean isSunk;
	private boolean isHorizontal; // Added orientation

	public Ship(String name, int length) {
		this.name = name;
		this.length = length;
		this.hits = 0;
		this.isSunk = false;
		this.isHorizontal = true; // Default orientation
	}

	public void hit() {
		hits++;
		if (hits >= length) {
			isSunk = true;
		}
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public boolean isSunk() {
		return isSunk;
	}

	public void setSunk(boolean isSunk) {
		this.isSunk = isSunk;
	}

	public boolean isHorizontal() {
		// TODO Auto-generated method stub
		return false;
	}
}
