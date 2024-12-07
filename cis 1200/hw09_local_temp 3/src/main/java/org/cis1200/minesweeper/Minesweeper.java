package org.cis1200.minesweeper;
import java.lang.Math;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Minesweeper {

    private Square[][] displayedBoard;
    private boolean[][] mineBoard;
    private int flagsRemaining;
    private int numBombs;
    private boolean gameOver;
    private boolean won;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper() {
        reset();
    }

    /**
     * populateMines initializes mineBoard with mines
     * in random places.
     */
    public void coverSquares() {
        for (int r = 0; r < displayedBoard.length; r++) {
            for (int c = 0; c < displayedBoard[r].length; c++) {
                displayedBoard[r][c] = new Square(true, NumAdjBombs.ZERO, false, false);
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
     * placeFlag allows players to place a flag on a square. Returns true if the move is
     * successful and false if a player tries to select a square that is
     * already uncovered or after the game has ended.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the placing was successful
     */
    public boolean flag(int r, int c) {
        if (displayedBoard[r][c].isCovered() && !gameOver) {
            flagsRemaining--;
            displayedBoard[r][c].changeFlagged();
            return true;
        }
        return false;
    }

    /**
     * uncoverNonMines recursively uncovers adjacent squares if
     * they do not have adjacent bombs. If the square was flagged,
     * it is no longer flagged.
     */
    public void uncoverNonMines(int c, int r) {
        if (displayedBoard[r][c].getNumAdjBombs() == NumAdjBombs.ZERO && !gameOver) {
            displayedBoard[r][c].uncover();
            if (displayedBoard[r][c].isFlagged()) {
                displayedBoard[r][c].changeFlagged();
                flagsRemaining++;
            }

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

    /**
     * selectSquare allows players to select a square. Returns true if the move is
     * successful and false if a player tries to select a square that is
     * already uncovered, flagged, or after the game has ended.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the selection was successful
     */
    public boolean selectSquare(int c, int r) {
        if (displayedBoard[r][c].isCovered() && !displayedBoard[r][c].isFlagged() && !gameOver) {
            displayedBoard[r][c].uncover();
            if (displayedBoard[r][c].isMine()) {
                won = false;
                gameOver = true;
            } else {
                checkWin();
                uncoverNonMines(r, c);
            }
            return true;
        }
        return false;
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
        won = true;
        return true;
    }

    public int getFlagsRemaining() {
        return flagsRemaining;
    }

    public boolean isGameOver() {
        return gameOver;
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
        numBombs = 40;
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

        m.selectSquare(2, 1);
        m.printGameState();
    }
}
