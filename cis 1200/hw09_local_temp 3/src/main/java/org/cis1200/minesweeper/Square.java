package org.cis1200.minesweeper;

/**
 * This class provides functionality for a square of the grid.
 * It includes state information for the square, as well as
 * getters and setters to access the information of each square.
 */
public class Square {

    private boolean covered;
    private NumAdjBombs numAdjBombs;
    private boolean flagged;
    private boolean isMine;
    private boolean checked;

    /**
     * Constructor sets up Square.
     */
    public Square(boolean covered, NumAdjBombs numAdjBombs, boolean flagged, boolean isMine, boolean checked) {
        this.covered = covered;
        this.numAdjBombs = numAdjBombs;
        this.flagged = flagged;
        this.isMine = isMine;
        this.checked = checked;
    }

    /**
     * isCovered checks whether the square is covered.
     *
     * @return true if the square is covered
     */
    public boolean isCovered() {
        return covered;
    }

    /**
     * uncover sets the square to being uncovered.
     */
    public void uncover() {
        covered = false;
    }

    /**
     * getNumAdjBombs gets the square's number of adjacent bombs.
     *
     * @return numAdjBombs of the square
     */
    public NumAdjBombs getNumAdjBombs() {
        return numAdjBombs;
    }

    /**
     * isFlagged checks whether the square is flagged.
     *
     * @return true if the square is flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * setNumAdjBombs sets the square's number of adjacent bombs.
     *
     * @param numAdjBombs number of adjacent bombs to set the square to have
     */
    public void setNumAdjBombs(NumAdjBombs numAdjBombs) {
        this.numAdjBombs = numAdjBombs;
    }

    /**
     * Flags the square if it is un-flagged and un-flags the square
     * if it is flagged.
     */
    public void changeFlagged() {
        flagged = !flagged;
    }

    /**
     * isMine returns whether the square has a mine.
     *
     * @return true if the square has a mine
     */
    public boolean isMine() {
        return isMine;
    }

    /**
     * setMine sets the square to have a mine.
     */
    public void setMine() {
        isMine = true;
    }

    /**
     * isChecked returns whether the square is checked.
     *
     * @return true if has been checked by the uncoverNonMines method
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * checked sets the square to having been checked.
     */
    public void checked() {
        checked = true;
    }

    /**
     * toString returns a visual representation of the square representing its state.
     *
     * @return a string representing if it is flagged, covered, an uncovered mine, or
     * an uncovered square with the number of its adjacent mines
     */
    @Override
    public String toString() {
        if (flagged) {
            return "f";
        } if (covered) {
            return "c";
        } if (isMine) {
            return "m";
        } if (numAdjBombs == NumAdjBombs.ZERO) {
            return "0";
        } if (numAdjBombs == NumAdjBombs.ONE) {
            return "1";
        } if (numAdjBombs == NumAdjBombs.TWO) {
            return "2";
        } if (numAdjBombs == NumAdjBombs.THREE) {
            return "3";
        } if (numAdjBombs == NumAdjBombs.FOUR) {
            return "4";
        } if (numAdjBombs == NumAdjBombs.FIVE) {
            return "5";
        } if (numAdjBombs == NumAdjBombs.SIX) {
            return "6";
        } if (numAdjBombs == NumAdjBombs.SEVEN) {
            return "7";
        } if (numAdjBombs == NumAdjBombs.EIGHT) {
            return "8";
        }
        return "";
    }
}
