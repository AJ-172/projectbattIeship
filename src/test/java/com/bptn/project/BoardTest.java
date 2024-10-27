package com.bptn.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testPlaceShipSuccessfully() {
        Board board = new Board();
        Ship ship = new Ship("Destroyer", 2);
        boolean result = board.placeShip(ship, 0, 0);
        assertTrue(result, "Ship should be placed successfully.");
    }

    @Test
    public void testPlaceShipOutOfBounds() {
        Board board = new Board();
        Ship ship = new Ship("Destroyer", 2);
        boolean result = board.placeShip(ship, 9, 9);
        assertFalse(result, "Ship placement should fail due to out of bounds.");
    }

    @Test
    public void testReceiveAttack() {
        Board board = new Board();
        Ship ship = new Ship("Destroyer", 2);
        boolean placed = board.placeShip(ship, 0, 0);
        assertTrue(placed, "Ship should be placed successfully.");

        boolean attackResult = board.receiveAttack(0, 0);
        assertTrue(attackResult, "Attack should be processed successfully.");
        assertTrue(board.getGrid()[0][0].isHit(), "Cell should be marked as hit.");
        assertEquals(1, ship.getHits(), "Ship should have one hit.");
    }
}
