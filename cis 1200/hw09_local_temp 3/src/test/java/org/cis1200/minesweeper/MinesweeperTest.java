package org.cis1200.minesweeper;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Below are some tests for the Minesweeper game.
 */

public class MinesweeperTest {
    Minesweeper m;

    @BeforeEach
    public void setUp() {
        m = new Minesweeper();
    }

    //tests reset conditions
    @Test
    public void checkResetConditions() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[0][1].setMine();
        m.reset();

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                Square curr = displayedBoard[r][c];
                assertFalse(curr.isFlagged());
                assertTrue(curr.isCovered());
                assertFalse(curr.isChecked());
            }
        }

        assertFalse(m.isGameOver());
        assertEquals(10, m.getNumBombs());
        assertEquals(m.getFlagsRemaining(), m.getNumBombs());
        assertFalse(m.checkWin());
    }

    //tests cover
    @Test
    public void checkCoverSquares() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();
        Square newSquare = new Square(true, NumAdjBombs.ZERO, false, false, false);

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                assertFalse(displayedBoard[r][c].isFlagged());
                assertTrue(displayedBoard[r][c].isCovered());
                assertFalse(displayedBoard[r][c].isChecked());
                assertFalse(displayedBoard[r][c].isMine());
                assertEquals(NumAdjBombs.ZERO, displayedBoard[r][c].getNumAdjBombs());
            }
        }
    }

    //test calcAdjMines when there are zero adjacent mines
    @Test
    public void testCalcZeroAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //check that all squares have zero adjacent mines when there are zero mines
        m.calcAdjMines();
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                Square curr = displayedBoard[r][c];
                assertEquals(NumAdjBombs.ZERO, curr.getNumAdjBombs());
            }
        }
    }

    //test calcAdjMines when there are eight adjacent mines
    @Test
    public void testCalcEightAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test eight adjacent mines
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.EIGHT, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are seven adjacent mines
    @Test
    public void testCalcSevenAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test seven adjacent mines (no bomb in upper left corner)
        m.coverSquares();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb directly above)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb upper right corner)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb directly to right)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb lower right corner)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb directly below)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb lower left corner)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());

        //test seven adjacent mines (no bomb directly left)
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SEVEN, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are six adjacent mines
    @Test
    public void testCalcSixAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test six adjacent mines
        m.coverSquares();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.SIX, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are five adjacent mines
    @Test
    public void testCalcFiveAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test five adjacent mines
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FIVE, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are four adjacent mines
    @Test
    public void testCalcFourAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test four adjacent mines
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FOUR, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are three adjacent mines
    @Test
    public void testCalcThreeAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test three adjacent mines
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.THREE, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there are two adjacent mines
    @Test
    public void testCalcTwoAdjMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test two adjacent mines
        m.coverSquares();
        displayedBoard[2][3].setMine();
        displayedBoard[3][3].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.TWO, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when there is one adjacent mine
    @Test
    public void testCalcOneAdjMine() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //test one adjacent mine
        m.coverSquares();
        displayedBoard[2][4].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.ONE, displayedBoard[3][4].getNumAdjBombs());
    }

    //test calcAdjMines when the currents square is in the upper left corner
    @Test
    public void testCalcAdjMinesUpperLCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[1][0].setMine();
        displayedBoard[1][1].setMine();
        displayedBoard[0][1].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.THREE, displayedBoard[0][0].getNumAdjBombs());
    }

    //test calcAdjMines mines when current square is along the top
    @Test
    public void testCalcAdjMinesTop() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[0][3].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[0][5].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FIVE, displayedBoard[0][4].getNumAdjBombs());
    }

    //test calcAdjMines when the currents square is in the upper right corner
    @Test
    public void testCalcAdjMinesUpperRCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[0][8].setMine();
        displayedBoard[1][8].setMine();
        displayedBoard[1][9].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.THREE, displayedBoard[0][9].getNumAdjBombs());
    }

    //test calcAdjMines mines when current square is along the right
    @Test
    public void testCalcAdjMinesRight() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[2][9].setMine();
        displayedBoard[2][8].setMine();
        displayedBoard[3][8].setMine();
        displayedBoard[4][8].setMine();
        displayedBoard[4][9].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FIVE, displayedBoard[3][9].getNumAdjBombs());
    }

    //test calcAdjMines when the currents square is in the lower right corner
    @Test
    public void testCalcAdjMinesLowerRCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[6][8].setMine();
        displayedBoard[6][9].setMine();
        displayedBoard[7][8].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.THREE, displayedBoard[7][9].getNumAdjBombs());
    }

    //test calcAdjMines mines when current square is along the bottom
    @Test
    public void testCalcAdjMinesBottom() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[7][3].setMine();
        displayedBoard[6][3].setMine();
        displayedBoard[6][4].setMine();
        displayedBoard[6][5].setMine();
        displayedBoard[7][5].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FIVE, displayedBoard[7][4].getNumAdjBombs());
    }

    //test calcAdjMines when the currents square is in the lower left corner
    @Test
    public void testCalcAdjMinesLowerLCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[6][0].setMine();
        displayedBoard[6][1].setMine();
        displayedBoard[7][1].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.THREE, displayedBoard[7][0].getNumAdjBombs());
    }

    //test calcAdjMines mines when current square is along the left
    @Test
    public void testCalcAdjMinesLeft() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.coverSquares();
        displayedBoard[3][0].setMine();
        displayedBoard[3][1].setMine();
        displayedBoard[4][1].setMine();
        displayedBoard[5][1].setMine();
        displayedBoard[5][0].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.FIVE, displayedBoard[4][0].getNumAdjBombs());
    }

    //test calcAdjMines mines when there are eight adjacent mines when there are mines nearby
    @Test
    public void testCalcAdjMinesSurroundedEight() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();


        //test eight adjacent mines when there are mines nearby
        displayedBoard[2][3].setMine();
        displayedBoard[2][4].setMine();
        displayedBoard[2][5].setMine();
        displayedBoard[3][5].setMine();
        displayedBoard[4][5].setMine();
        displayedBoard[4][4].setMine();
        displayedBoard[4][3].setMine();
        displayedBoard[3][3].setMine();

        //not directly adjacent
        displayedBoard[1][2].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[1][6].setMine();
        displayedBoard[2][6].setMine();
        displayedBoard[3][6].setMine();
        displayedBoard[4][6].setMine();
        displayedBoard[5][6].setMine();
        displayedBoard[5][5].setMine();
        displayedBoard[5][4].setMine();
        displayedBoard[5][3].setMine();
        displayedBoard[5][2].setMine();
        displayedBoard[4][2].setMine();
        displayedBoard[3][2].setMine();
        displayedBoard[2][2].setMine();
        displayedBoard[1][2].setMine();
        m.calcAdjMines();
        assertEquals(NumAdjBombs.EIGHT, displayedBoard[3][4].getNumAdjBombs());
    }

    //tests populateMines
    @Test
    public void testPopulateMines() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        int mineCount = 0;

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                if (displayedBoard[r][c].isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(m.getNumBombs(), mineCount);
    }

    //tests flag un-flagging and flagging
    @Test
    public void testFlag() {
        m.populateMines();
        Square[][] displayedBoard = m.getDisplayedBoard();

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                displayedBoard[r][c].changeFlagged(m);
                assertTrue(displayedBoard[r][c].isFlagged());
            }
        }

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                displayedBoard[r][c].changeFlagged(m);
                assertFalse(displayedBoard[r][c].isFlagged());
            }
        }
    }

    //tests flag does nothing when game is over
    @Test
    public void testFlagGameOver() {
        m.populateMines();
        Square[][] displayedBoard = m.getDisplayedBoard();
        m.setGameOver(true);
        m.flag(7,9);
        assertFalse(displayedBoard[7][9].isFlagged());
    }

    //tests uncoverNonMines when there are no zero squares adjacent, center of the board
    @Test
    public void testUncoverNonMines() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //not directly adjacent
        displayedBoard[1][2].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[1][6].setMine();
        displayedBoard[2][6].setMine();
        displayedBoard[3][6].setMine();
        displayedBoard[4][6].setMine();
        displayedBoard[5][6].setMine();
        displayedBoard[5][5].setMine();
        displayedBoard[5][4].setMine();
        displayedBoard[5][3].setMine();
        displayedBoard[5][2].setMine();
        displayedBoard[4][2].setMine();
        displayedBoard[3][2].setMine();
        displayedBoard[2][2].setMine();
        displayedBoard[1][2].setMine();
        m.calcAdjMines();
        m.uncoverNonMines(3, 4);

        //adjacent squares are uncovered
        assertFalse(displayedBoard[3][4].isCovered());
        assertFalse(displayedBoard[2][3].isCovered());
        assertFalse(displayedBoard[2][4].isCovered());
        assertFalse(displayedBoard[2][5].isCovered());
        assertFalse(displayedBoard[3][5].isCovered());
        assertFalse(displayedBoard[4][5].isCovered());
        assertFalse(displayedBoard[4][4].isCovered());
        assertFalse(displayedBoard[4][3].isCovered());
        assertFalse(displayedBoard[3][3].isCovered());

        //non-adjacent squares are covered still
        assertTrue(displayedBoard[1][2].isCovered());
        assertTrue(displayedBoard[1][2].isCovered());
        assertTrue(displayedBoard[1][3].isCovered());
        assertTrue(displayedBoard[1][4].isCovered());
        assertTrue(displayedBoard[1][5].isCovered());
        assertTrue(displayedBoard[1][6].isCovered());
        assertTrue(displayedBoard[2][6].isCovered());
        assertTrue(displayedBoard[3][6].isCovered());
        assertTrue(displayedBoard[4][6].isCovered());
        assertTrue(displayedBoard[5][6].isCovered());
        assertTrue(displayedBoard[5][5].isCovered());
        assertTrue(displayedBoard[5][4].isCovered());
        assertTrue(displayedBoard[5][3].isCovered());
        assertTrue(displayedBoard[5][2].isCovered());
        assertTrue(displayedBoard[4][2].isCovered());
        assertTrue(displayedBoard[3][2].isCovered());
        assertTrue(displayedBoard[2][2].isCovered());
        assertTrue(displayedBoard[1][2].isCovered());
    }

    //tests uncoverNonMines when adjacent squares are uncovered and flags are removed
    @Test
    public void testUncoverNonMinesFlags() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        //adjacent
        displayedBoard[2][3].changeFlagged(m);
        displayedBoard[2][4].changeFlagged(m);
        displayedBoard[2][5].changeFlagged(m);
        displayedBoard[3][5].changeFlagged(m);
        displayedBoard[4][5].changeFlagged(m);
        displayedBoard[4][4].changeFlagged(m);
        displayedBoard[4][3].changeFlagged(m);
        displayedBoard[3][3].changeFlagged(m);

        //not directly adjacent
        displayedBoard[1][2].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[1][6].setMine();
        displayedBoard[2][6].setMine();
        displayedBoard[3][6].setMine();
        displayedBoard[4][6].setMine();
        displayedBoard[5][6].setMine();
        displayedBoard[5][5].setMine();
        displayedBoard[5][4].setMine();
        displayedBoard[5][3].setMine();
        displayedBoard[5][2].setMine();
        displayedBoard[4][2].setMine();
        displayedBoard[3][2].setMine();
        displayedBoard[2][2].setMine();
        displayedBoard[1][2].setMine();
        m.calcAdjMines();
        m.uncoverNonMines(3, 4);

        //adjacent squares are un-flagged
        assertFalse(displayedBoard[3][4].isFlagged());
        assertFalse(displayedBoard[2][3].isFlagged());
        assertFalse(displayedBoard[2][4].isFlagged());
        assertFalse(displayedBoard[2][5].isFlagged());
        assertFalse(displayedBoard[3][5].isFlagged());
        assertFalse(displayedBoard[4][5].isFlagged());
        assertFalse(displayedBoard[4][4].isFlagged());
        assertFalse(displayedBoard[4][3].isFlagged());
        assertFalse(displayedBoard[3][3].isFlagged());
    }

    //tests uncoverNonMines when adjacent squares all have zero adjacent bombs, multiple rounds
    @Test
    public void testUncoverNonMinesAdjacentZeros() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(3, 4);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines when some squares have zero adjacent bombs and some don't
    @Test
    public void testUncoverNonMinesMixed() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(3, 4);

        displayedBoard[1][1].setMine();
        displayedBoard[1][2].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[2][1].setMine();
        displayedBoard[3][1].setMine();
        displayedBoard[4][1].setMine();
        displayedBoard[5][1].setMine();
        displayedBoard[6][1].setMine();
        displayedBoard[7][2].setMine();
        displayedBoard[7][3].setMine();
        displayedBoard[7][4].setMine();
        displayedBoard[6][5].setMine();
        displayedBoard[6][6].setMine();
        displayedBoard[5][7].setMine();

        displayedBoard[4][7].setMine();
        displayedBoard[3][7].setMine();
        displayedBoard[1][6].setMine();
        displayedBoard[1][5].setMine();
        displayedBoard[1][4].setMine();
        displayedBoard[1][3].setMine();
        displayedBoard[1][2].setMine();

        assertFalse(displayedBoard[2][2].isCovered());
        assertFalse(displayedBoard[2][3].isCovered());
        assertFalse(displayedBoard[2][4].isCovered());
        assertFalse(displayedBoard[2][5].isCovered());

        assertFalse(displayedBoard[3][2].isCovered());
        assertFalse(displayedBoard[3][3].isCovered());
        assertFalse(displayedBoard[3][4].isCovered());
        assertFalse(displayedBoard[3][5].isCovered());
        assertFalse(displayedBoard[3][6].isCovered());

        assertFalse(displayedBoard[4][2].isCovered());
        assertFalse(displayedBoard[4][3].isCovered());
        assertFalse(displayedBoard[4][4].isCovered());
        assertFalse(displayedBoard[4][5].isCovered());
        assertFalse(displayedBoard[4][6].isCovered());

        assertFalse(displayedBoard[5][2].isCovered());
        assertFalse(displayedBoard[5][3].isCovered());
        assertFalse(displayedBoard[5][4].isCovered());
        assertFalse(displayedBoard[5][5].isCovered());
        assertFalse(displayedBoard[5][6].isCovered());

        assertFalse(displayedBoard[6][2].isCovered());
        assertFalse(displayedBoard[6][3].isCovered());
        assertFalse(displayedBoard[6][4].isCovered());
    }

    //tests uncoverNonMines at upper left corner
    @Test
    public void testUncoverNonMinesUpperLCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(0, 0);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines along top of grid
    @Test
    public void testUncoverNonMinesTop() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(0, 5);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines at upper right corner
    @Test
    public void testUncoverNonMinesUpperRCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(0, 9);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines along right
    @Test
    public void testUncoverNonMinesRight() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(3, 9);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines at bottom right corner
    @Test
    public void testUncoverNonMinesLowerRCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(7, 9);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines along bottom
    @Test
    public void testUncoverNonMinesBottom() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(7, 5);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines at bottom left corner
    @Test
    public void testUncoverNonMinesLowerLCorner() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(7, 0);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests uncoverNonMines along bottom
    @Test
    public void testUncoverNonMinesLeft() {
        m.coverSquares();
        Square[][] displayedBoard = m.getDisplayedBoard();

        m.uncoverNonMines(4, 0);
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                assertFalse(displayedBoard[r][c].isCovered());
            }
        }
    }

    //tests selectSquares last square needed to win
    @Test
    public void testSelectSquaresWin() {
        Square[][] displayedBoard = m.getDisplayedBoard();

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                if (r != 3 || c !=3) {
                    displayedBoard[r][c].setMine();
                }
            }
        }
        m.selectSquare(3, 3);
        assertTrue(m.checkWin());
        assertTrue(m.isGameOver());
    }

    //tests selectSquares selecting a mine causes a loss
    @Test
    public void testSelectSquaresLose() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[3][3].setMine();
        m.selectSquare(3, 3);
        assertFalse(m.checkWin());
        assertTrue(m.isGameOver());
    }

    //tests selectSquares selecting a flagged square does nothing
    @Test
    public void testSelectSquaresFlagged() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[3][3].changeFlagged(m);
        m.selectSquare(3, 3);
        assertFalse(m.checkWin());
        assertTrue(displayedBoard[3][3].isCovered());
        assertFalse(m.isGameOver());
    }

    //tests selectSquares selecting an uncovered square does nothing
    @Test
    public void testSelectSquaresUncovered() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[3][3].uncover();
        int covered = 0;
        m.selectSquare(3, 3);

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                if (displayedBoard[r][c].isCovered()) {
                    covered++;
                }
            }
        }

        assertEquals(79, covered);
        assertFalse(m.checkWin());
        assertFalse(displayedBoard[3][3].isCovered());
        assertFalse(m.isGameOver());
    }

    //tests selectSquares selecting a square when game is over does nothing
    @Test
    public void testSelectSquaresGameOver() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        m.setGameOver(true);
        int covered = 0;
        m.selectSquare(3, 3);

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                if (displayedBoard[r][c].isCovered()) {
                    covered++;
                }
            }
        }
        assertFalse(m.checkWin());
        assertEquals(80, covered);
        assertTrue(m.isGameOver());
    }

    //tests checkWin if win
    @Test
    public void testCheckWinTrue() {
        Square[][] displayedBoard = m.getDisplayedBoard();

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                if (r != 3 || c !=3) {
                    displayedBoard[r][c].setMine();
                }
            }
        }

        m.selectSquare(3, 3);
        assertTrue(m.checkWin());
    }

    //tests checkWin if has not won yet / lost
    @Test
    public void testCheckWinFalse() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[3][3].setMine();
        m.selectSquare(3, 4);
        assertFalse(m.checkWin());

        m.selectSquare(3, 3);
        assertFalse(m.checkWin());
    }

    //tests checkWin if has not won yet / lost
    @Test
    public void testGame() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        displayedBoard[3][3].setMine();
        m.selectSquare(3, 4);
        assertFalse(m.checkWin());

        m.selectSquare(3, 3);
        assertFalse(m.checkWin());
    }

    //tests getNumBombs
    @Test
    public void testGetNumBombs() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertEquals(10, m.getNumBombs());
    }

    //tests getFlagsRemaining
    @Test
    public void testGetFlagsRemaining() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertEquals(10, m.getFlagsRemaining());
        displayedBoard[3][3].changeFlagged(m);
        assertEquals(9, m.getFlagsRemaining());
        displayedBoard[3][4].changeFlagged(m);
        displayedBoard[3][5].changeFlagged(m);
        displayedBoard[3][6].changeFlagged(m);
        displayedBoard[3][7].changeFlagged(m);
        displayedBoard[3][8].changeFlagged(m);
        displayedBoard[3][9].changeFlagged(m);
        displayedBoard[4][1].changeFlagged(m);
        displayedBoard[4][2].changeFlagged(m);
        displayedBoard[4][3].changeFlagged(m);
        assertEquals(0, m.getFlagsRemaining());
        displayedBoard[4][4].changeFlagged(m);
        assertEquals(-1, m.getFlagsRemaining());
    }

    //tests getSquare
    @Test
    public void testGetSquare() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertEquals(m.getSquare(1, 2), displayedBoard[1][2]);
    }

    //tests setFlagsRemaining
    @Test
    public void testSetFlagsRemaining() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertEquals(10, m.getFlagsRemaining());
        m.setFlagsRemaining(11);
        assertEquals(11, m.getFlagsRemaining());
        m.setFlagsRemaining(0);
        assertEquals(0, m.getFlagsRemaining());
        m.setFlagsRemaining(-1);
        assertEquals(-1, m.getFlagsRemaining());
    }

    //tests setNumBombs
    @Test
    public void testSetNumBombs() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        m.setNumBombs(11);
        assertEquals(11, m.getNumBombs());
    }

    //tests setDisplayedBoard
    @Test
    public void testSetDisplayedBoard() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        Square[][] displayedBoard2 = new Square[8][10];
        m.setDisplayedBoard(displayedBoard2);
        assertEquals(displayedBoard2, m.getDisplayedBoard());
        assertNotEquals(displayedBoard, m.getDisplayedBoard());
    }

    //tests setGameOver
    @Test
    public void testSetGameOver() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        m.setGameOver(true);
        assertTrue(m.isGameOver());
        m.setGameOver(false);
        assertFalse(m.isGameOver());
    }

    /*
     *
     * Square TESTS
     *
     */

    //tests the functionality of a square's state of being covered
    @Test
    public void testCoveredFunctionality() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertTrue(displayedBoard[1][1].isCovered());
        displayedBoard[1][1].uncover();
        assertFalse(displayedBoard[1][1].isCovered());
    }

    //tests the functionality of a square's number of adjacent bombs
    @Test
    public void testsNumAdjBombsFunctionality() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        m.coverSquares();
        assertEquals(NumAdjBombs.ZERO, displayedBoard[1][1].getNumAdjBombs());

        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[0].length; c++) {
                displayedBoard[r][c].setMine();
            }
        }
        m.calcAdjMines();
        assertEquals(NumAdjBombs.EIGHT, displayedBoard[1][1].getNumAdjBombs());
    }

    //tests the functionality of a square's state of being flagged
    @Test
    public void testsFlaggedFunctionality() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertFalse(displayedBoard[1][1].isFlagged());
        displayedBoard[1][1].changeFlagged(m);
        assertTrue(displayedBoard[1][1].isFlagged());
    }

    //tests the functionality of a square's state of checked
    @Test
    public void testsCheckedFunctionality() {
        Square[][] displayedBoard = m.getDisplayedBoard();
        assertFalse(displayedBoard[1][1].isChecked());
        displayedBoard[1][1].checked();
        assertTrue(displayedBoard[1][1].isChecked());
    }

}
