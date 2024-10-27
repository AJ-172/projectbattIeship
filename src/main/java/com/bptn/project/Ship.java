package com.bptn.project;

public class Ship {

	private String name;
	private int length;
	private int hits;
	private boolean isHorizontal;
	private boolean isPlaced;

	public Ship(String name, int length) {
		this.name = name;
		this.length = length;
		this.hits = 0;
		this.isHorizontal = true;
		this.isPlaced = false;
	}

	public void hit() {
		hits++;
	}

	public boolean isSunk() {
		return hits >= length;
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public int getHits() {
		return hits;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public boolean isPlaced() {
		return isPlaced;
	}

	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
}
