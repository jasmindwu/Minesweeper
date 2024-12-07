package org.cis1200.minesweeper;

public class Square {

    private boolean covered;
    private NumAdjBombs numAdjBombs;
    private boolean flagged;
    private boolean isMine;
    private boolean checked;

    public Square(boolean covered, NumAdjBombs numAdjBombs, boolean flagged, boolean isMine, boolean checked) {
        this.covered = covered;
        this.numAdjBombs = numAdjBombs;
        this.flagged = flagged;
        this.isMine = isMine;
        this.checked = checked;
    }

    public boolean isCovered() {
        return covered;
    }

    public void cover() {
        covered = true;
    }

    public void uncover() {
        covered = false;
    }

    public NumAdjBombs getNumAdjBombs() {
        return numAdjBombs;
    }

    public void setNumAdjBombs(NumAdjBombs numAdjBombs) {
        this.numAdjBombs = numAdjBombs;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void changeFlagged() {
        flagged = !flagged;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void checked() {
        checked = true;
    }

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
