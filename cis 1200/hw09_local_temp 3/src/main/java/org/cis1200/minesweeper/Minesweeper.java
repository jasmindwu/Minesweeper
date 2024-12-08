package org.cis1200.minesweeper;

import javax.swing.*;
import java.io.*;

/*
 * CIS 120 HW09 - MineSweeper
 * (c) University of Pennsylvania
 * Created by Jasmin Wu in Fall 2024.
 */

/**
 * This is Minesweeper!
 * <p>
 * Your objective is to uncover all the squares without mines hidden underneath.
 * Be careful not to detonate any of the 10 mines in the process, or the game will end.
 * You can place flags where you think there are mines to keep track.
 * Win by avoiding all mines and uncovering all non-mines!
 * <p>
 * You can save your game progress anytime and restore your most recent save anytime.
 */
public class Minesweeper {

    /**
     * I use a 2D array displayedBoard here to represent my Minesweeper board with
     * 10 columns and 8 rows. This is justified because it visually resembles
     * my minesweeper board, and I can easily traverse through the 2D array
     * and access the squares using row and column indices.
     * <p>
     * The type contained in the 2D array is Square. This is justified because each square
     * of the grid has multiple pieces of information and states that change and need to be
     * accessed. By storing objects in this array, I can store many pieces of information
     * about each square that I can access, like if it is covered, has a mine, has been checked,
     * and the number of adjacent mines.
     */
    private Square[][] displayedBoard;
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
                displayedBoard[r][c] = new Square(true, NumAdjBombs.ZERO, false, false, false);
            }
        }
    }

    /**
     * populateMines initializes displayedBoard with mines
     * in random places.
     */
    public void populateMines() {
        int placedMines = 0;

        while (placedMines < numBombs) {
            int r = (int) (Math.random() * 8);
            int c = (int) (Math.random() * 10);
            if (!displayedBoard[r][c].isMine()) {
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
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                int numBombs = 0;

                //check upper diagonal left if possible
                if (r > 0 && c > 0 && displayedBoard[r - 1][c - 1].isMine()) {
                    numBombs++;
                }

                //check directly above if possible
                if (r > 0 && displayedBoard[r - 1][c].isMine()) {
                    numBombs++;
                }

                //check upper diagonal right if possible
                if (r > 0 && c < 9 && displayedBoard[r - 1][c + 1].isMine()) {
                    numBombs++;
                }

                //check directly right if possible
                if (c < 9 && displayedBoard[r][c + 1].isMine()) {
                    numBombs++;
                }

                //check lower diagonal right if possible
                if (r < 7 && c < 9 && displayedBoard[r + 1][c + 1].isMine()) {
                    numBombs++;
                }

                //check directly below if possible
                if (r < 7 && displayedBoard[r + 1][c].isMine()) {
                    numBombs++;
                }

                //check lower diagonal left if possible
                if (r < 7 && c > 0 && displayedBoard[r + 1][c - 1].isMine()) {
                    numBombs++;
                }

                //check directly left if possible
                if (c > 0 && displayedBoard[r][c - 1].isMine()) {
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
            if (displayedBoard[r][c].isCovered() && !gameOver
                    && !displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining--;
            } else if (displayedBoard[r][c].isCovered() && !gameOver
                    && displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining++;
            }
        }

    }

    /**
     * RECURSION COMPONENT:
     * If the current square has zero adjacent bombs, this algorithm uncovers the square.
     * It also uncovers all adjacent squares, and if any of those adjacent squares
     * also have zero adjacent bombs, the algorithm uncovers all adjacent squares
     * for that square as well.
     *
     * Why recursion?
     *
     * Recursion is useful instead of iteration here because then I do not
     * need to manually check every single square of the board each time the
     * user selects a square. This is helpful with scaling, and it also simplifies
     * the code. If one square has zero adjacent bombs, then it triggers the uncovering
     * of its adjacent squares, and this triggering continues until all squares
     * with zero adjacent bombs that were adjacent to any uncovered squares in the
     * process are dealt with. It would be difficult with iteration because I would
     * need to iterate over the whole grid each time even if the number of squares
     * needed to be uncovered are very localized and small. Also, the uncovering is
     * outward spreading, so it would also be difficult to write the logic for
     * squares that need to be uncovered that are far away from the clicked square.
     */

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

            //Base case: stops recursing here if the square does not have zero adjacent bombs.
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
            if (displayedBoard[r][c].isCovered()
                    && !displayedBoard[r][c].isFlagged() && !gameOver) {
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
     * <p>
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     * <p>
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper();

        m.flag(1, 2);
        System.out.println("" + m.getSquare(2, 1).isCovered());


        m.printGameState();
    }
}
