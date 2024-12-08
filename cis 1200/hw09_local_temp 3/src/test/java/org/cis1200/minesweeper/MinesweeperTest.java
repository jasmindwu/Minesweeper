package org.cis1200.minesweeper;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Below are some example tests for the TicTacToe game.
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
        m.reset();
        Square[][] displayedBoard = m.getDisplayedBoard();
        Square newSquare = new Square(true, NumAdjBombs.ZERO, false, false, false);

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

    /*
    *
    * I/O TESTS
    *
    */

    //tests save
    @Test
    public void checkSave() {
        m.reset();
        Square[][] displayedBoard = m.getDisplayedBoard();
        Square newSquare = new Square(true, NumAdjBombs.ZERO, false, false, false);

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

    @Test
    public void turnsAlternateOnlyValid() {
        assertTrue(t.getCurrentPlayer()); // Player 1's turn
        assertTrue(t.playTurn(0, 0));

        assertFalse(t.getCurrentPlayer()); // Player 2's turn
        assertTrue(t.playTurn(0, 1));

        assertTrue(t.getCurrentPlayer());
        assertFalse(t.playTurn(0, 0)); // Invalid: square is already taken

        assertTrue(t.getCurrentPlayer());
        assertTrue(t.playTurn(1, 0));

        assertFalse(t.getCurrentPlayer());
        assertTrue(t.playTurn(2, 2));

        assertTrue(t.getCurrentPlayer());
        assertTrue(t.playTurn(2, 0)); // Player 1 wins across the first row

        assertFalse(t.playTurn(1, 1)); // Invalid: game is over
    }

    @Test
    public void checkWinConditionsWin() {
        assertEquals(0, t.checkWinner());

        t.playTurn(0, 0);
        t.playTurn(1, 0);
        t.playTurn(2, 0);

        assertEquals(0, t.checkWinner());

        t.playTurn(1, 1);
        t.playTurn(2, 2);
        t.playTurn(1, 2);

        assertEquals(2, t.checkWinner());
    }

    @Test
    public void checkWinConditionsDraw() {
        assertEquals(0, t.checkWinner());
        t.playTurn(1, 1);
        t.playTurn(1, 2);
        t.playTurn(2, 2);
        t.playTurn(0, 0);
        t.playTurn(0, 1);
        t.playTurn(2, 1);
        t.playTurn(1, 0);
        t.playTurn(0, 2);

        assertEquals(0, t.checkWinner());
        t.playTurn(2, 0);
        assertEquals(3, t.checkWinner());
    }

}
