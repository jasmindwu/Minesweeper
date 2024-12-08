package org.cis1200.minesweeper;

/**
 * This enum provides the number of adjacent bombs for the squares.
 * This provides a finite number of states, as there can only possible
 * be zero to eight adjacent bombs in the grid.
 */

public enum NumAdjBombs {
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;
}
