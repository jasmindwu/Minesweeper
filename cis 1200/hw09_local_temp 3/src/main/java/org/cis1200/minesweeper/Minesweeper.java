package org.cis1200.minesweeper;
import javax.swing.*;
import java.lang.Math;
import java.io.*;

/*
 * CIS 120 HW09 - MineSweeper
 * (c) University of Pennsylvania
 * Created by Jasmin Wu in Fall 2024.
 */

/**
 * This is Minesweeper!
 *
 * Your objective is to uncover all the squares without mines hidden underneath.
 * Be careful not to detonate any of the 10 mines in the process, or the game will end.
 * You can place flags where you think there are mines to keep track.
 * Win by avoiding all mines and uncovering all non-mines!
 *
 * You can save your game progress anytime and restore your most recent save anytime.
 */
public class Minesweeper {

    private Square[][] displayedBoard;
    private boolean[][] mineBoard;
    private int flagsRemaining;
    private int numBombs;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper() {
        reset();
    }

    /**
     * coverSquares initializes displayedBoard with default squares in initial state.
     */
    public void coverSquares() {
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                displayedBoard[r][c] = new Square(true, NumAdjBombs.ZERO,false, false, false);
            }
        }
    }

    /**
     * populateMines initializes mineBoard with mines
     * in random places.
     */
    public void populateMines() {
        int placedMines = 0;

        while (placedMines < numBombs) {
            int r = (int) (Math.random() * 8);;
            int c = (int) (Math.random() * 10);
            if (!mineBoard[r][c]) {
                mineBoard[r][c] = true;
                displayedBoard[r][c].setMine();
                placedMines++;
            }
        }
    }

    /**
     * calcAdjMines calculates the number of adjacent mines
     * for every square and assigns that value to them.
     */
    public void calcAdjMines() {
        for (int r = 0; r < mineBoard.length; r++) {
            for (int c = 0; c < mineBoard[r].length; c++) {
                int numBombs = 0;

                //check upper diagonal left if possible
                if (r > 0 && c > 0 && mineBoard[r - 1][c - 1]) {
                    numBombs++;
                }

                //check directly above if possible
                if (r > 0 && mineBoard[r-1][c]) {
                    numBombs++;
                }

                //check upper diagonal right if possible
                if (r > 0 && c < 9 && mineBoard[r - 1][c + 1]) {
                    numBombs++;
                }

                //check directly right if possible
                if (c < 9 && mineBoard[r][c + 1]) {
                    numBombs++;
                }

                //check lower diagonal right if possible
                if (r < 7 && c < 9 && mineBoard[r + 1][c + 1]) {
                    numBombs++;
                }

                //check directly below if possible
                if (r < 7 && mineBoard[r + 1][c]) {
                    numBombs++;
                }

                //check lower diagonal left if possible
                if (r < 7 && c > 0 && mineBoard[r + 1][c - 1]) {
                    numBombs++;
                }

                //check directly left if possible
                if (c > 0 && mineBoard[r][c - 1]) {
                    numBombs++;
                }

                //set zero enum
                if (numBombs == 0) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.ZERO);
                }

                //set one enum
                if (numBombs == 1) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.ONE);
                }

                //set two enum
                if (numBombs == 2) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.TWO);
                }

                //set three enum
                if (numBombs == 3) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.THREE);
                }

                //set four enum
                if (numBombs == 4) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.FOUR);
                }

                //set five enum
                if (numBombs == 5) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.FIVE);
                }

                //set six enum
                if (numBombs == 6) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.SIX);
                }

                //set seven enum
                if (numBombs == 7) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.SEVEN);
                }

                //set eight enum
                if (numBombs == 8) {
                    displayedBoard[r][c].setNumAdjBombs(NumAdjBombs.EIGHT);
                }
            }
        }
    }

    /**
     * flag allows players to place a flag on a square that is currently un-flagged
     * (if the game has not ended yet).
     * If the square is already flagged, the flag is removed.
     *
     * @param r row square to flag is in
     * @param c column square to flag is in
     */
    public void flag(int r, int c) {
        if (r < 8 && c < 10) {
            if (displayedBoard[r][c].isCovered() && !gameOver && !displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining--;
            } else if (displayedBoard[r][c].isCovered() && !gameOver && displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining++;
            }
        }

    }

    /**
     * uncoverNonMines recursively uncovers adjacent squares if
     * they do not have adjacent bombs. It stops uncovering
     * once it reaches the grid edge, a square that has already been uncovered,
     * or a square with adjacent bombs (after
     * uncovering that square). If the square was flagged,
     * it is no longer flagged.
     *
     * @param r row square to uncover
     * @param c column square to uncover
     */
    public void uncoverNonMines(int r, int c) {
        if (!displayedBoard[r][c].isChecked()) {
            displayedBoard[r][c].uncover();
            displayedBoard[r][c].checked();

            if (displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining++;
            }

            if (displayedBoard[r][c].getNumAdjBombs() == NumAdjBombs.ZERO) {

                //check upper diagonal left if possible
                if (r > 0 && c > 0) {
                    uncoverNonMines(r - 1, c - 1);
                }

                //check directly above if possible
                if (r > 0) {
                    uncoverNonMines(r - 1, c);
                }

                //check upper diagonal right if possible
                if (r > 0 && c < 9) {
                    uncoverNonMines(r - 1, c + 1);
                }

                //check directly right if possible
                if (c < 9) {
                    uncoverNonMines(r, c + 1);
                }

                //check lower diagonal right if possible
                if (r < 7 && c < 9) {
                    uncoverNonMines(r + 1, c + 1);
                }

                //check directly below if possible
                if (r < 7) {
                    uncoverNonMines(r + 1, c);
                }

                //check lower diagonal left if possible
                if (r < 7 && c > 0) {
                    uncoverNonMines(r + 1, c - 1);
                }

                //check directly left if possible
                if (c > 0) {
                    uncoverNonMines(r, c - 1);
                }
            }
        }
    }

    /**
     * selectSquare allows players to uncover a square if it is covered, not flagged,
     * and the game is not over. If the square is a mine, the game ends. It checks if the
     * square is the last square needed to uncover. If the square has zero adjacent bombs,
     * it calls uncoverNonMines.
     *
     * @param r row square to uncover
     * @param c column square to uncover
     */
    public void selectSquare(int r, int c) {
        if (c < 10 && r < 8) {
            if (displayedBoard[r][c].isCovered() && !displayedBoard[r][c].isFlagged() && !gameOver) {
                displayedBoard[r][c].uncover();
                if (displayedBoard[r][c].isMine()) {
                    gameOver = true;
                } else {
                    checkWin();
                    if (displayedBoard[r][c].getNumAdjBombs() == NumAdjBombs.ZERO) {
                        uncoverNonMines(r, c);
                    }
                }
            }
        }
    }

    /**
     * checkWin checks whether all the squares without mines have been uncovered.
     *
     * @return true if the player has won
     */
    public boolean checkWin() {
        for (Square[] squares : displayedBoard) {
            for (Square square : squares) {
                if (!square.isMine()) {
                    if (square.isCovered()) {
                        return false;
                    }
                }
            }
        }
        gameOver = true;
        return true;
    }

    /**
     * isGameOver checks whether the game is over.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * getNumBombs gets the number of bombs .
     *
     * @return numBombs
     */
    public int getNumBombs() {
        return numBombs;
    }

    /**
     * getNumBombs gets flagsRemaining.
     *
     * @return flagsRemaining.
     */
    public int getFlagsRemaining() {
        return flagsRemaining;
    }

    /**
     * getNumBombs gets the displayed board.
     *
     * @return displayedBoard
     */
    public Square[][] getDisplayedBoard() {
        return displayedBoard;
    }

    /**
     * getNumBombs gets the current square.
     *
     * @param r row the square to get is in
     * @param c column the square to get is in
     * @return the square at displayedBoard[r][c]
     */
    public Square getSquare(int r, int c) {
        return displayedBoard[r][c];
    }

    /**
     * setFlagsRemaining sets the number of flags remaining.
     *
     * @param flagsRemaining the number of flags to set the remaining number to
     */
    public void setFlagsRemaining(int flagsRemaining) {
        this.flagsRemaining = flagsRemaining;
    }

    /**
     * setNumBombs sets the number of bombs to set the board to have.
     *
     * @param numBombs the number of bombs to set the board to have
     */
    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    /**
     * setDisplayedBoard sets the displayed board of the game.
     *
     * @param displayedBoard the displayed board to set
     */
    public void setDisplayedBoard(Square[][] displayedBoard) {
        this.displayedBoard = displayedBoard;
    }

    /**
     * setMineBoard sets the mine board of the game.
     *
     * @param mineBoard the mine board to set
     */
    public void setMineBoard(boolean[][] mineBoard) {
        this.mineBoard = mineBoard;
    }

    /**
     * setMineBoard sets if the game is over.
     *
     * @param gameOver if the game is over
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nFlags remaining " + flagsRemaining + ":\n");
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                System.out.print(displayedBoard[r][c]);
                if (c < 9) {
                    System.out.print(" | ");
                }
            }
            if (r < 7) {
                System.out.println("\n-------------------------------------");
            }
        }
        if (gameOver) {
            if (checkWin()) {
                System.out.println("\nCongrats, you won!");
            } else {
                System.out.println("\nGame over. Try again!");
            }
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        displayedBoard = new Square[8][10];
        mineBoard = new boolean[8][10];
        gameOver = false;
        numBombs = 10;
        flagsRemaining = numBombs;

        coverSquares();
        populateMines();
        calcAdjMines();
    }


    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper();

        m.flag(1, 2);
        System.out.println(""+ m.getSquare(2, 1).isCovered());


        m.printGameState();
    }
}
